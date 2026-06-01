# Spring IoC & DI — 控制反转与依赖注入

## 传统方式的痛点

在没有 Spring 之前，一个类要用另一个类，就得自己 `new`：

```java
public class UserService {
    private UserRepository repo = new UserRepository();  // 自己动手造
    private EmailService email = new EmailService();      // 自己动手造
}
```

这有什么问题？

- **紧耦合**：UserService 死死绑定了具体的实现类
- **换不动**：想换一个 Repository 实现？改源码
- **测不了**：要对 EmailService 做单元测试？没法 mock
- **管不好**：对象多了之后，到处 new，一团乱麻

## IoC 控制反转

Spring 的核心思想就一句话：**你别自己 new，告诉我你需要什么，我给你**。

对象的创建权从你的代码转移到了 Spring 容器——这个反转就叫"控制反转"。

```java
// 传统方式：你自己控制 new 的时机和对象
UserService service = new UserService(new UserRepository(), new EmailService());

// Spring 方式：容器帮你创建，你只管拿
@Autowired
private UserService service;  // 容器已经把依赖都装好了
```

Spring 启动时扫描所有带 `@Component` 的类，把它们创建好放进一个 Map（单例池），此后任何地方需要，都从 Map 里取。

## DI 依赖注入

IoC 的具体实现方式，有三种。

### 构造器注入（推荐）

```java
@Service
public class UserService {

    private final UserRepository userRepository;  // final，一旦注入不可变
    private final EmailService emailService;

    @Autowired  // Spring 4.3+ 单构造器时可省略
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

**为什么推荐？**
- `final` 保证依赖不可变，不会被偷偷替换
- 单元测试时可以直接 `new UserService(mockRepo, mockEmail)`，不需要启动 Spring 容器
- 构造器注入能避免循环依赖

### 字段注入（不推荐）

```java
@Autowired
private UserRepository repo;  // 不能加 final，方便测试时 mock 困难
```

### Setter 注入

```java
@Autowired
public void setRepo(UserRepository repo) { this.repo = repo; }
// 适用于可选依赖，不常用
```

## 注解分层

| 注解 | 含义 | 用途 |
|------|------|------|
| `@Component` | 通用 Bean | 基类，其他都是它的子注解 |
| `@Repository` | DAO 层 Bean | 数据库操作 |
| `@Service` | 业务层 Bean | 业务逻辑 |
| `@Controller` | 控制器 Bean | MVC 控制器 |

这四个底层完全一样，分层纯粹是为了**语义清晰**，一眼看出调用链。

## @Configuration + @Bean

有些类你没法加 `@Component`——比如 `DateTimeFormatter` 是 JDK 的类，你改不了源码。这时候用 `@Bean`：

```java
@Configuration
public class AppConfig {

    @Bean
    public DateTimeFormatter defaultFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Bean
    @Scope("prototype")  // 每次获取都创建新实例
    public User prototypeUser() {
        return new User(null, "临时用户", "temp@example.com");
    }
}
```

- `@Configuration` 告诉 Spring：这是一个配置类，里面有 `@Bean` 定义
- `@Bean` 把方法返回值注册成 Bean，名称默认是方法名

## Bean 作用域

| 作用域 | 说明 | 适用场景 |
|--------|------|----------|
| **singleton**（默认） | 容器中只有一个实例 | 无状态 Bean：Service、Repository |
| **prototype** | 每次获取创建新实例 | 有状态 Bean：临时对象 |
| request | 每个 HTTP 请求一个 | Web 环境 |
| session | 每个 HTTP 会话一个 | Web 环境 |

```java
UserService s1 = context.getBean(UserService.class);
UserService s2 = context.getBean(UserService.class);
System.out.println(s1 == s2);  // true（singleton，同一个对象）

User p1 = context.getBean("prototypeUser", User.class);
User p2 = context.getBean("prototypeUser", User.class);
System.out.println(p1 == p2);  // false（prototype，不同对象）
```

## Bean 生命周期

```
实例化 → 属性赋值 → Aware 回调
→ BeanPostProcessor.beforeInitialization
→ @PostConstruct / InitializingBean
→ BeanPostProcessor.afterInitialization
→ 就绪，可供使用
→ @PreDestroy / DisposableBean
→ 销毁
```

## 常见面试题

### @Autowired vs @Resource
- `@Autowired` 是 Spring 的，**按类型**注入
- `@Resource` 是 JDK 的（javax.annotation），默认**按名称**注入

### BeanFactory vs ApplicationContext
- BeanFactory 是 IoC 底层接口，**懒加载**（用到才创建）
- ApplicationContext 是 BeanFactory 子接口，**预加载**单例 Bean（启动时就全部创建好），此外还有国际化、事件发布等功能

### 循环依赖怎么解决？
- 构造器注入**无法解决**循环依赖（抛 `BeanCurrentlyInCreationException`）
- 字段/setter 注入通过**三级缓存**解决：

```
singletonObjects（一级缓存）→ 完全创建好的 Bean
    ↓ 取不到
earlySingletonObjects（二级缓存）→ 提前暴露的 Bean（未完成属性赋值）
    ↓ 取不到
singletonFactories（三级缓存）→ 能生产早期引用的工厂
```

A 依赖 B，B 依赖 A → Spring 先创建 A 的早期引用放进三级缓存，然后去创建 B，B 需要 A 时从三级缓存拿到 A 的早期引用完成注入，B 创建完成，A 也完成。

---

## 相关笔记
- [[Spring-MVC]] — 下一个知识块：RESTful Web API
- [[Spring-AOP]] — 面向切面编程
- [[JAVA]] — Java 基础
