###  # Java 后端开发知识图谱

文档内容分区梳理

#### 1. MySQL 数据库核心

_涉及建表、索引、事务、锁机制、SQL语法及执行原理_

- **数据类型与建表**
    
    - `CHAR(n)`：定长字符串，存固定长度，不足补空格，存取快，适合短且长度固定的数据（如性别、状态码）。
    - `VARCHAR(n)`：变长字符串，按实际长度存储，节省空间，适合长度不一的数据（如姓名、地址）。
    - 不推荐使用外键约束的原因：增加数据库复杂性，影响性能，维护成本高。
    - 浮点数存储：不建议使用 `float` & `double` 存储金额，存在精度丢失，应使用 `DECIMAL(M,D)`。
- **SQL 语法与查询**
    
    - 新增：`INSERT INTO 表名 (列1, 列2) VALUES (值1, 值2);`
    - 修改：`UPDATE 表名 SET 列1=新值 WHERE 条件;`
    - 删除：`DELETE`（逐行删除，可带WHERE，可回滚） vs `TRUNCATE`（快速清空全表，不可带条件，不可回滚，重置自增ID）。
    - 模糊查询：`LIKE`（灵活，支持%、_，可能慢） vs `REGEXP`（正则，功能强大，更慢）。
    - 聚合函数：`COUNT`, `SUM`, `AVG`, `MAX`, `MIN`。
    - 分组与排序：`GROUP BY`（分组） vs `HAVING`（分组后过滤，可使用聚合函数） vs `WHERE`（分组前行级过滤）。
    - 分页：`LIMIT offset, count`（offset起始行从0开始，count返回行数）。
    - 去重：`UNION`（去重，性能低） vs `UNION ALL`（不去重，效率高）。
- **索引机制**
    
    - 作用：加快查询速度，提升检索效率。
    - 类型：主键、唯一、普通、组合、全文、前缀索引。
    - 组合索引原则：遵循最左前缀原则。
    - 索引失效场景：未走最左、用!=、LIKE '%xx'、函数操作、类型转换。
    - 回表：先查二级索引，再根据主键去主键索引查整行。减少方法：使用覆盖索引。
- **事务与锁**
    
    - 事务四大特性（ACID）：原子性、一致性、隔离性、持久性。
    - 隔离级别及问题：
        - 读未提交：脏读（读到未提交数据）。
        - 读已提交：不可重复读（同事务内多次读结果不同，被修改）。
        - 可重复读（默认）：幻读（同事务内多次查询记录数不同，被插入/删除）。
        - 串行化：无并发问题，性能差。
    - 锁类型：
        - 共享锁（S/读锁）：可并发读，阻塞写。
        - 排他锁（X/写锁）：阻塞其他读写。
        - 悲观锁：假设冲突会发生，如 `SELECT ... FOR UPDATE`。
        - 乐观锁：假设冲突少，通过版本号或时间戳机制实现。
    - MVCC机制：快照读（读历史版本，不加锁） vs 当前读（读最新数据，加锁）。
- **存储引擎**
    
    - `InnoDB`：支持事务、行锁、外键，适合高并发，支持MVCC。
    - `MyISAM`：不支持事务，表锁，查询快但并发差，已逐步淘汰。
- **数据库设计**
    
    - 范式：1NF（原子性）、2NF（相关性）、3NF（直接相关性）。
    - 反范式：为提升查询性能，适当冗余数据，减少JOIN。
    - 外键放置：1:1（任一方加唯一约束），1:N（N方放外键），N:M（建中间表）。

---

#### 2. Java 核心编程

_涉及基础语法、面向对象、集合框架、多线程、JVM及API使用_

- **基础语法与数据类型**
    
    - 基本数据类型：`byte`(1)、`short`(2)、`int`(4)、`long`(8)、`float`(4)、`double`(8)、`char`(2)、`boolean`。
    - `String`：不可变，每次修改生成新对象。
    - `StringBuilder`：可变，线程不安全，性能高。
    - `StringBuffer`：可变，线程安全（方法同步），性能较低。
    - 包装类：为了解决基本类型无法参与面向对象操作（如集合），提供工具方法。
    - 数组：固定长度，同类型，连续内存。
    - 运算符：`i++`（先用后加） vs `++i`（先加后用）；`&&`（短路逻辑与） vs `&`（按位与/逻辑与不短路）。
- **面向对象 (OOP)**
    
    - 三大特征：封装、继承、多态。
    - 类与对象：类是模板，对象是实例。
    - 抽象类 vs 接口：抽象类单继承，可有具体方法；接口多实现，Java 8+ 可有default方法。
    - 重写（Override）：子类重写父类方法，运行时多态。
    - 重载（Overload）：同一类中同名不同参，编译时决定。
- **集合框架 (Collection/Map)**
    
    - `ArrayList`：数组实现，查询快，增删慢，线程不安全，扩容1.5倍。
    - `LinkedList`：双向链表实现，增删快，查询慢。
    - `Vector`：数组实现，线程安全，性能低（已少用）。
    - `HashSet`：基于HashMap，通过hashCode()和equals()去重。
    - `HashMap`：数组+链表/红黑树，非线程安全，允许null键值。
    - `ConcurrentHashMap`：线程安全，JDK8使用CAS + synchronized，性能好。
    - `Hashtable`：线程安全，不允许null，性能低（已淘汰）。
- **多线程与并发**
    
    - 创建方式：继承Thread、实现Runnable、实现Callable、线程池。
    - 线程状态：NEW → RUNNABLE → BLOCKED/WAITING/TIMED_WAITING → TERMINATED。
    - `synchronized`：锁升级过程（无锁→偏向锁→轻量级锁→重量级锁）。
    - `volatile`：保证可见性，禁止指令重排，不保证原子性。
    - `ThreadLocal`：线程局部变量，每个线程有独立副本，用于数据库连接、用户信息传递。
    - 守护线程：为其他线程服务（如GC），主线程结束则守护线程自动终止。
- **JVM 虚拟机**
    
    - 内存区域：方法区、堆（线程共享）、虚拟机栈、本地方法栈、程序计数器（线程独享）。
    - 垃圾回收：`finalize()`方法在回收前可能调用（不推荐使用）。
    - 类加载：双亲委派模型。
- **Java API 与 IO**
    
    - `BigDecimal`：用于精确计算，比较用`compareTo()`，构造推荐用String。
    - `SimpleDateFormat`：非线程安全，建议使用`ThreadLocal`或`java.time`包。
    - `File`：文件操作。
    - `Socket`：网络编程。

---

#### 3. Spring 框架与 ORM

_涉及Spring核心、MVC、MyBatis及AOP等_

- **Spring 核心 (IOC/AOP)**
    
    - IOC（控制反转）：将对象创建交给容器，实现解耦。
    - DI（依赖注入）：IOC的实现方式，如Setter注入、构造器注入。
    - Bean作用域：singleton（单例）、prototype（原型）、request、session、application。
    - Bean生命周期：实例化 → 属性注入 → 初始化 → 使用 → 销毁。
    - AOP（面向切面）：通过代理模式（JDK动态代理、CGLIB代理）实现横切关注点。
    - 通知类型：`@Before`、`@After`、`@AfterReturning`、`@AfterThrowing`、`@Around`。
- **Spring MVC**
    
    - 工作流程：DispatcherServlet → HandlerMapping → HandlerAdapter → Controller → ModelAndView → ViewResolver → 渲染。
    - 组件：处理器映射器、处理器适配器、视图解析器。
    - 注解：`@Controller`、`@RequestMapping`、`@ResponseBody`等。
- **MyBatis**
    
    - ORM（对象关系映射）：将Java对象映射到数据库表。
    - Mapper代理：使用JDK动态代理生成代理对象。
    - 优势：灵活的SQL，解耦。
- **事务管理**
    
    - 编程式事务：手动控制。
    - 声明式事务：基于AOP，使用`@Transactional`注解。
    - 传播机制：`REQUIRED`（默认）、`REQUIRES_NEW`、`SUPPORTS`等。

---

#### 4. 计算机基础与操作系统

_涉及网络、Linux命令及系统原理_

- **计算机网络**
    
    - OSI七层模型：物理层、数据链路层、网络层、传输层、会话层、表示层、应用层。
    - TCP/IP：TCP（面向连接、可靠、三次握手、四次挥手） vs UDP（无连接、不可靠、速度快）。
    - HTTP协议：请求/响应组成，GET（幂等、可缓存） vs POST（非幂等、安全）。
    - 三次握手：防止历史连接请求造成资源浪费。
    - 四次挥手：全双工通信，每个方向需独立关闭。
- **Linux 操作系统**
    
    - Shell：命令解释器，Bash是最常用的Shell。
    - 文件系统：树状结构，常见类型ext4、XFS。
    - 常用命令：`cat`、`less`、`ls`、`cd`、`grep`、`ps`、`top`等。
    - 目录结构：`/`（根）、`/bin`（命令）、`/etc`（配置）、`/home`（用户）、`/var`（日志）、`/tmp`（临时）。
    - 版本选择：服务器选RHEL/CentOS/Ubuntu LTS，桌面选Ubuntu/Fedora。
- **数据结构与算法**
    
    - 数组 vs 链表：连续内存vs非连续，随机访问快vs增删快。
    - 树结构：二叉树、二叉搜索树、AVL树、红黑树（自平衡，Java TreeMap/TreeSet）。
    - 常见算法：排序（冒泡、快速、归并等）。

---

#### 5. 架构与场景题

_涉及设计模式、缓存、服务器配置等_

- **设计模式**
    
    - 单例模式：保证一个类仅有一个实例。
    - 工厂模式：定义创建对象的接口。
    - 适配器模式：将不兼容接口转换为客户端期望接口。
    - 代理模式：JDK代理（接口）、CGLIB代理（子类）。
- **缓存技术 (Redis)**
    
    - 缓存击穿：热点Key过期，大量请求打到数据库。解决方案：永不过期、互斥锁。
    - 缓存穿透：查询不存在数据。解决方案：布隆过滤器、缓存空值。
    - 缓存雪崩：大量Key同时失效。解决方案：过期时间加随机值、多级缓存。
    - 清理策略：惰性删除、定期删除。
- **服务器与部署**
    
    - Tomcat：Web服务器，Servlet容器。
    - Nginx：高性能HTTP服务器，反向代理，负载均衡。
    - Docker：容器化技术，打包应用及依赖。
    - 微服务：Spring Cloud，服务拆分，分布式事务。
- **Web 技术**
    
    - JSP：本质是Servlet，九大内置对象。
    - Servlet：单例多线程，非线程安全。
    - Session & Cookie：会话跟踪技术。
    - AJAX：异步JavaScript和XML，局部刷新。

---

#### 6. Spring Boot 专题

_Spring Boot 是 Spring 生态的快速开发框架，核心是「约定大于配置」。_

- **自动配置原理**：通过 `@SpringBootApplication`（包含 `@EnableAutoConfiguration`）根据 classpath 中的依赖自动配置 Bean。
- **起步依赖 (Starter)**：一站式引入相关依赖，如 `spring-boot-starter-web`、`spring-boot-starter-data-jpa`。
- **配置文件**：`application.yml` 或 `application.properties`，支持多环境配置（`application-dev.yml`）。
- **内嵌服务器**：默认内嵌 Tomcat，也可切换为 Jetty 或 Undertow。
- **Actuator**：提供生产级监控端点（`/health`、`/metrics`、`/env`）。
- **常用注解**：`@SpringBootApplication`、`@RestController`、`@Configuration`、`@Value`。

**常见集成**：
- Spring Boot + MyBatis：`mybatis-spring-boot-starter`
- Spring Boot + Redis：`spring-boot-starter-data-redis`
- Spring Boot + JPA：`spring-boot-starter-data-jpa`
- Spring Boot + Security：`spring-boot-starter-security`

---

#### 7. Maven 基础

_Maven 是 Java 项目的主流构建和依赖管理工具。_

- **pom.xml**：Maven 项目的核心配置文件，定义坐标（groupId、artifactId、version）、依赖、插件。
- **依赖管理**：声明式引入依赖，Maven 自动下载传递依赖。
- **生命周期**：`clean` → `compile` → `test` → `package` → `install` → `deploy`。
- **常用命令**：

```bash
mvn clean           # 清理编译产物
mvn compile         # 编译源码
mvn test            # 运行测试
mvn package         # 打包（jar/war）
mvn install         # 安装到本地仓库
mvn dependency:tree # 查看依赖树
```

- **依赖范围 (scope)**：`compile`（默认）、`provided`（运行时由容器提供）、`runtime`、`test`。
- **Gradle 对比**：Gradle 基于 Groovy/Kotlin DSL，构建脚本更灵活，性能更好（增量构建、缓存），Android 项目默认使用。

---

#### 8. REST API 设计

- **HTTP 方法语义**：
  - `GET`：查询（幂等、安全）
  - `POST`：创建
  - `PUT`：全量更新（幂等）
  - `PATCH`：部分更新
  - `DELETE`：删除（幂等）
- **状态码规范**：
  - 200：成功
  - 201：创建成功
  - 204：删除成功（无返回体）
  - 400：请求参数错误
  - 401：未认证
  - 403：无权限
  - 404：资源不存在
  - 500：服务器内部错误
- **URL 设计**：资源名用复数（`/api/users`），层级关系清晰（`/api/users/{id}/orders`）。
- **请求/响应**：统一返回格式（`{"code": 200, "data": {...}, "message": "ok"}`）。
- **分页**：`/api/users?page=1&size=20`。

---

#### 9. 单元测试 (JUnit & Mockito)

```java
// JUnit 5 基本示例
@Test
void testAddition() {
    assertEquals(4, 2 + 2);
}

// Mockito 模拟依赖
@Mock
private UserRepository userRepo;

@InjectMocks
private UserService userService;

@Test
void testFindUser() {
    when(userRepo.findById(1L)).thenReturn(Optional.of(new User()));
    User user = userService.getUser(1L);
    assertNotNull(user);
    verify(userRepo).findById(1L);
}
```

- **JUnit 5 常用注解**：`@Test`、`@BeforeEach`、`@AfterEach`、`@DisplayName`、`@ParameterizedTest`。
- **Mockito**：用于 mock 依赖，核心方法 `when().thenReturn()`、`verify()`。
- **断言库**：JUnit 自带 `Assertions`，或使用 AssertJ（流式断言）。
- **覆盖率**：建议核心业务逻辑达到 80% 以上。

---

#### 10. Redis 补充

- **五种基本数据类型**：
  - `String`：字符串，可存数字/JSON，支持计数器（`INCR`）
  - `List`：链表，可做队列/栈，`LPUSH`/`RPOP`
  - `Set`：无序集合，去重，交并差集操作
  - `ZSet`：有序集合，按分数排序，适合排行榜
  - `Hash`：键值对集合，适合存对象
- **持久化机制**：
  - **RDB**：定时快照，恢复快但可能丢最后一批数据
  - **AOF**：记录每条写命令，数据更安全但文件更大
  - 生产建议：两者同时开启
- **淘汰策略**：`LRU`（最近最少使用）、`LFU`（最不经常使用）、`TTL`（过期时间）、随机。
- **集群模式**：主从复制 → 哨兵模式 → Cluster 集群（数据分片）。

---

#### 11. Linux 补充

- **文件权限**：

```bash
# 权限结构：rwx (读/写/执行) × 用户/用户组/其他
chmod 755 file    # rwxr-xr-x
chmod 644 file    # rw-r--r--
chown user:group file
```

- **进程管理**：

```bash
ps aux            # 查看所有进程
ps -ef | grep java
kill -9 <PID>     # 强制终止进程
systemctl start/stop/restart/status <服务名>
```

- **Shell 脚本基础**：

```bash
#!/bin/bash
# 变量
NAME="hello"
# 条件判断
if [ -f "/path/file" ]; then
    echo "文件存在"
fi
# 循环
for i in {1..5}; do
    echo $i
done
```

- **常用排查命令**：`top`（系统负载）、`df -h`（磁盘）、`free -h`（内存）、`netstat -tlnp`（端口）、`tail -f`（日志）。

---

#### 12. 安全与认证

- **JWT (JSON Web Token)**：
  - 结构：Header.Payload.Signature（三部分 Base64 编码）
  - 无状态，适合分布式系统
  - Access Token（短期）+ Refresh Token（长期）
- **OAuth2**：
  - 授权码模式（最安全）、密码模式、客户端模式、简化模式
  - 第三方登录的核心协议
- **Spring Security**：
  - 过滤器链 + 认证（Authentication）+ 授权（Authorization）
  - `SecurityFilterChain` 配置 URL 权限
  - `PasswordEncoder`（推荐 BCrypt）
  - `@PreAuthorize` 方法级权限控制
- **常见攻击防护**：
  - SQL 注入：使用参数化查询（MyBatis `#{}`）
  - XSS：对输出进行 HTML 转义
  - CSRF：Spring Security 默认开启 CSRF 保护
  - CORS：配置跨域白名单