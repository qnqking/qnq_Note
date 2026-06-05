# Spring AOP — 面向切面编程

## 为什么要用 AOP？

看一个没有 AOP 的订单服务：

```java
public String createOrder(String product, int qty) {
    log.info("createOrder 开始, 参数: " + product);     // 日志
    long start = System.currentTimeMillis();              // 性能

    String orderId = "ORD-" + System.currentTimeMillis(); // ← 真正的业务就这一行！

    log.info("createOrder 结束, 返回值: " + orderId);    // 日志
    log.info("耗时: " + (System.currentTimeMillis() - start) + "ms"); // 性能
    return orderId;
}
```

**业务逻辑只有一行，日志和性能监控占了六行。** 而且每个方法都得写同样的样板代码。

> 日志、事务、权限、缓存这些功能，像"横切面"一样贯穿在所有方法里——AOP 就是用来把横切关注点从业务代码中分离出去的。

## AOP 核心概念

| 术语 | 含义 | 类比 |
|------|------|------|
| **JoinPoint** | 可以拦截的点（方法调用、异常抛出等） | "可能被安检的位置" |
| **Pointcut** | 切点表达式，真正要拦截哪些 | "安检规则：检查所有人" |
| **Advice** | 拦截后要执行的逻辑 | "安检动作：扫描行李" |
| **Aspect** | 切面 = 切点 + 通知 | "安检系统 = 规则 + 动作" |
| **Weaving** | 把切面织入目标对象，生成代理 | "部署安检设备到入口" |

## 五种通知类型

| 注解 | 执行时机 | 能用吗 | 类比 |
|------|---------|--------|------|
| `@Before` | 方法执行前 | ✅ | 进门之前脱鞋 |
| `@AfterReturning` | 方法正常返回后 | ✅ | 吃完饭拿到账单 |
| `@AfterThrowing` | 方法抛异常后 | ✅ | 菜里有虫子，投诉 |
| `@After` | 方法执行后（类似 finally） | ✅ | 吃完离开餐厅 |
| `@Around` | 包围整个方法 | ⭐最强 | 全程录像，随时可中断 |

## 切点表达式

```java
execution(* com.example.service.*.*(..))
    │       │                       │  │
    │       │                       │  └── (..) = 任意参数
    │       │                       └── 第二个 * = 任意方法名
    │       └── service 包下的任意类
    └── 任意返回值类型（void / String / User 都匹配）
```

翻译：**拦截 `com.example.service` 包下，所有类的所有方法，不管参数和返回值是什么。**

常用变体：

```java
execution(* com.example.service.*.*(..))         // service 包下所有方法
execution(* com.example.service.UserService.*(..)) // 只拦截 UserService
execution(* com.example.*.*.save*(..))             // 所有模块下以 save 开头的方法
@annotation(org.springframework.transaction.annotation.Transactional) // 标了@Transactional的方法
```

## 实战：日志切面

```java
@Aspect
@Component
public class LogAspect {

    private int callCount = 0;

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        callCount++;
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("[@Before] #" + callCount + " 调用: "
                + method + ", 参数: " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "execution(* com.example.service.*.*(..))",
                    returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[@AfterReturning] " + joinPoint.getSignature().getName()
                + " → 返回值: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.service.*.*(..))",
                   throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("[@AfterThrowing] " + joinPoint.getSignature().getName()
                + " → 异常: " + ex.getMessage());
    }

    @After("execution(* com.example.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[@After] " + joinPoint.getSignature().getName()
                + " 执行完毕");
    }
}
```

**关键参数：**
- `returning = "result"`：匹配方法返回值，并赋值给参数 `result`
- `throwing = "ex"`：匹配抛出的异常，并赋值给参数 `ex`

## 实战：性能监控切面（@Around）

```java
@Aspect
@Component
public class PerformanceAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // ← 这一行真正执行原始方法

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[性能] " + joinPoint.getSignature().getName()
                + " 耗时: " + elapsed + "ms");

        return result;  // 必须返回原方法的返回值
    }
}
```

`proceed()` 就是调用原始方法。**你可以选择不调**——比如缓存命中的时候，直接返回缓存值：

```java
@Around("@annotation(cached)")
public Object cache(ProceedingJoinPoint jp) throws Throwable {
    String key = buildCacheKey(jp);
    Object cached = redis.get(key);
    if (cached != null) return cached;  // 缓存命中，不调 proceed()

    Object result = jp.proceed();       // 缓存没命中，调原方法
    redis.set(key, result);
    return result;
}
```

## AOP 底层原理：动态代理

Spring AOP 不是直接给你真实的 OrderService 实例，而是给你一个**代理对象**：

```
你的代码调用 orderService.createOrder(...)
         ↓
实际上拿到的是「代理对象」（Proxy）
         ↓
代理对象：先执行 @Before 切面方法
         ↓
代理对象：调用真实的 createOrder 方法（反射）
         ↓
代理对象：执行 @AfterReturning 切面方法
         ↓
代理对象：执行 @After 切面方法
         ↓
返回结果给你
```

**两种代理方式：**
- **JDK 动态代理**：目标类实现了接口 → 代理基于接口创建
- **CGLIB 代理**：目标类没有接口 → 代理通过继承目标类创建

Spring Boot 2.x+ 默认使用 CGLIB，因为更通用。

## @Transactional 就是 AOP

```java
@Transactional
public void transfer(Account from, Account to, BigDecimal amount) {
    from.debit(amount);   // 这三行要么全成功，
    to.credit(amount);    // 要么全回滚，
    logService.record();  // 不会有中间状态
}
```

`@Transactional` 到底做了什么？

```
1. AOP 拦截到方法上有 @Transactional 注解
2. 方法执行前：开启事务（获取数据库连接，setAutoCommit(false)）
3. 调用原始方法（执行业务逻辑）
4. 如果正常返回 → 提交事务（commit）
5. 如果抛出异常 → 回滚事务（rollback）
6. 方法执行后：归还数据库连接
```

**所有这些都是 AOP 代理做的，你的代码里一行事务操作的代码都没有。**

## @Transactional 失效的五种场景

| 场景 | 原因 | 解决 |
|------|------|------|
| **同类方法调用** | `this.method()` 不走代理，而是直接调用 | 注入自己，或拆到不同类 |
| **非 public 方法** | CGLIB 只能代理 public | 改成 public |
| **异常被 catch 吃掉** | 代理没感知到异常 | 手动回滚：`TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()` |
| **rollbackFor 不匹配** | 默认只回滚 RuntimeException 和 Error | 加 `@Transactional(rollbackFor = Exception.class)` |
| **数据库引擎不支持** | MySQL MyISAM 不支持事务 | 用 InnoDB |

## 一句话总结

AOP 让你在不修改业务代码的前提下，给多个方法统一插入横切逻辑（日志、事务、权限、缓存）。核心就是**动态代理**：Spring 在运行时生成代理对象，代理对象在调用原始方法前后执行切面逻辑。

---

## 相关笔记
- [[Spring-IoC-DI]] — 前置知识：Bean 的管理是 AOP 代理的基础
- [[Spring-MVC]] — AOP 同样可以拦截 Controller 方法
- [[注解速查表]] — 所有注解的大白话解释
- [[JAVA/JAVA]] — Java 动态代理原理
