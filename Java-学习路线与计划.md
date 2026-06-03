# Java 学习路线与计划

> 目标：**6 周**内从有基础 → 能独立做 Java 后端项目。适用于已有编程基础（变量、控制流、OOP 概念）的学习者。

---

## 每日节奏（建议）

| 时间段 | 内容 |
|--------|------|
| 上午 1.5h | 看视频/读笔记，过知识点 |
| 上午 1h | 手写代码，每个知识点至少敲 3 遍 |
| 下午 1.5h | 继续新知识 |
| 下午 1h | 做当天对应的练习题 |
| 晚上 0.5h | 复习当天内容，整理遇到的问题 |

每天 5~6 小时，周末可适当加量做项目。

---

## 第一阶段：Java 核心速通（第 1 周）

> 目标：把 Java 基础语法和 OOP 吃透，能写独立的控制台程序。

### Day 1 — 环境 + 基础语法

- JDK 17/21 安装、环境变量配置、IDEA 安装与基本使用
- 参考：[[Java-基础语法]]
- 重点：8 种基本数据类型及转换规则、运算符优先级、`Scanner` 输入
- 练习：写一个命令行计算器（加减乘除，循环输入）

### Day 2 — 控制流 + 数组

- 参考：[[Java-基础语法]]
- 重点：`if/switch`、`for/while/do-while`、`break/continue` 标签
- 重点：数组声明与初始化、`Arrays` 工具类、二维数组
- 练习：冒泡排序、二分查找、杨辉三角打印

### Day 3 — 面向对象（上）

- 参考：[[Java-面向对象]]
- 重点：类与对象、`this` 关键字、构造器重载、`static` 与实例成员的区别
- 重点：`private/default/protected/public` 四种权限——用表格默写
- 练习：写一个 `Student` 类管理系统（增删查改，内存存储）

### Day 4 — 面向对象（下）

- 参考：[[Java-面向对象]]
- 重点：继承（`extends`）、`super`、方法重写 vs 重载、`final` 三种用法
- 重点：抽象类 vs 接口（语法 + 设计区别）、`default` 方法
- 重点：多态三要素——父类引用指向子类对象、编译看左边运行看右边
- 练习：用接口 `USB` 定义规范，子类 `Mouse` `Keyboard` 实现，多态调用

### Day 5 — String + 常用 API

- 参考：[[Java-基础语法]] 的 String 部分
- 重点：`String` 不可变性、字符串常量池、`StringBuilder`/`StringBuffer`
- 重点：`Object.equals()` vs `==`、`hashCode()` 约定
- 重点：包装类（`int`↔`Integer`）、自动拆装箱、`Integer` 缓存 `[-128, 127]`
- 练习：统计一个字符串中各字符出现次数、回文判断

### Day 6 — 第一周复习 + 阶段练习

- 复习本周所有笔记，画出类关系图
- 综合练习：写一个简易图书管理系统（Book 类 + Library 类，控制台交互，数组/List 存储）
- 要求：封装、继承、多态、接口至少各出现一次

---

## 第二阶段：集合 + 泛型 + 反射（第 2 周）

> 目标：掌握 Java 容器体系，理解泛型与反射的核心用法。

### Day 7 — List + Set

- 参考：[[Java-集合框架]]
- 重点：`ArrayList` vs `LinkedList`（底层结构 + 增删查效率对比）
- 重点：`HashSet` 去重原理——先 `hashCode()` 后 `equals()`
- 重点：`TreeSet` 自然排序 vs 定制排序（`Comparable` vs `Comparator`）
- 练习：用 `ArrayList` 实现一个去重工具，用 `HashSet` 统计不重复单词数

### Day 8 — Map

- 参考：[[Java-集合框架]]
- 重点：`HashMap` 底层：数组+链表+红黑树、put 流程、扩容机制（负载因子 0.75）
- 重点：`TreeMap` 按 key 排序、`LinkedHashMap` 保持插入顺序
- 重点：`Collections` 工具类常用方法
- 练习：用 `HashMap` 做单词频次统计（读文件 → 分词 → 统计 → 按频次排序输出）

### Day 9 — 泛型

- 参考：[[Java-泛型与反射]]
- 重点：为什么要有泛型（类型安全 + 消除强转）、类型擦除
- 重点：泛型类/接口/方法、泛型通配符 `? extends`（上界）vs `? super`（下界）
- 练习：写一个泛型 DAO 基类，配合 Book/User 实体子类使用

### Day 10 — 反射 + 注解

- 参考：[[Java-泛型与反射]]
- 重点：获取 `Class` 对象的三种方式、`Constructor`/`Field`/`Method` 的反射操作
- 重点：`setAccessible(true)` 暴力反射
- 注解：`@Override`/`@Deprecated`/`@SuppressWarnings` + 元注解 `@Target`/`@Retention`
- 练习：用反射解析一个类的所有方法并调用

### Day 11 — Lambda + Stream

- 参考：[[Java-高级API]]
- 重点：Lambda 语法糖、四大函数式接口（`Function`/`Consumer`/`Predicate`/`Supplier`）
- 重点：Stream 创建 → filter/map/sorted → collect（中间操作惰性求值，终止操作触发计算）
- 练习：给一个 `List<Person>` 用 Stream 做过滤、映射、排序、分组

### Day 12 — IO 流 + 异常

- 参考：[[Java-高级API]]
- 重点：字节流 vs 字符流、缓冲流包装、`try-with-resources`（实现 `AutoCloseable`）
- 重点：异常体系——`Error`/`Exception`、checked vs unchecked、`throw` vs `throws`
- 练习：用 `BufferedReader` 读取文件并统计行数/字符数

---

## 第三阶段：数据库 + JDBC（第 3 周）

> 目标：MySQL 核心操作 + Java 操作数据库。

### Day 13 — MySQL 核心基础

- 参考：[[MySQL-核心基础]]
- 安装 MySQL 8.0+，学会 `mysql -u root -p` 登录
- 重点：DDL（建库建表、约束）、DML（INSERT/UPDATE/DELETE）、DQL（SELECT 及其子句）
- 重点：`GROUP BY` + 聚合函数、`HAVING` vs `WHERE`
- 练习：创建 `student`、`course`、`score` 三张表，插入 20 条数据

### Day 14 — 多表查询 + 子查询

- 参考：[[MySQL-核心基础]]
- 重点：`INNER JOIN`、`LEFT/RIGHT JOIN`、自连接（员工-上级场景）
- 重点：标量子查询、列子查询（`IN`/`ANY`/`ALL`）、`EXISTS`
- 练习：查询"选修了所有课程的学生"（双重 NOT EXISTS）、各科最高分学生

### Day 15 — 事务 + 索引

- 参考：[[MySQL-高级]]
- 重点：事务 ACID 四大特性、`COMMIT`/`ROLLBACK`、事务隔离级别（脏读/不可重复读/幻读）
- 重点：索引结构 B+Tree、聚簇索引 vs 二级索引、最左前缀原则、覆盖索引
- 练习：用 `EXPLAIN` 分析查询计划，对比建索引前后扫描行数

### Day 16 — JDBC 基础

- 参考：[[Java-高级API]] 的 JDBC 部分
- 重点：JDBC 六步流程——注册驱动、获取连接、写 SQL、执行、处理结果集、释放资源
- 重点：**SQL 注入原理** + `PreparedStatement` 参数化查询防注入
- 练习：用 JDBC 写一个 DAO 层，完成单表的 CRUD

### Day 17 — 数据库连接池 + 小综合

- 理解连接池原理（复用 TCP 连接，减少三次握手开销）
- 重点：Druid/HikariCP 配置 + 使用
- 综合练习：用 JDBC + 连接池完成图书管理系统的数据库版（取代内存存储）

### Day 18 — 第三周复习

- 画 MySQL 核心知识思维导图
- 做 10 道复杂 SQL 题（参考 LeetCode 数据库题）
- 手写 JDBC CRUD 模板代码 3 遍

---

## 第四阶段：Web 基础 + Spring 全家桶（第 4~5 周）

> 目标：从 Servlet 到 Spring Boot，打通后端 MVC 全链路。

### Day 19 — HTTP + Servlet 入门

- 参考：[[计算机网络]] HTTP 部分 + [[Web开发基础]]
- 重点：HTTP 请求/响应格式、GET vs POST、常见状态码（200/301/302/304/400/401/403/404/500）
- 重点：Servlet 生命周期（`init` → `service`/`doGet/doPost` → `destroy`）
- 练习：启动 Tomcat，手写一个 `HelloServlet`

### Day 20 — Request/Response + Cookie/Session

- 参考：[[Web开发基础]]
- 重点：`request.getParameter()` vs `getAttribute()`、请求转发 vs 重定向
- 重点：Cookie（客户端）vs Session（服务端）原理、Session 基于 Cookie 的 JSESSIONID
- 练习：写一个登录校验 Filter（未登录跳转登录页）

### Day 21 — Spring IoC + DI

- 参考：[[Spring-IoC-DI]]
- 重点：IoC 思想（控制权交给容器）、DI 三种方式（构造器 > setter > 字段）
- 重点：`@Component`/`@Controller`/`@Service`/`@Repository` + `@Autowired`/`@Qualifier`
- 重点：Bean 作用域 `singleton` vs `prototype`、`@Configuration` + `@Bean`
- 练习：用纯注解方式配置 Spring 容器，注入 Service 到 Controller

### Day 22 — Spring MVC

- 参考：[[Spring-MVC]]
- 重点：DispatcherServlet 处理流程（前端控制器 → HandlerMapping → HandlerAdapter → ViewResolver）
- 重点：`@RestController`、`@RequestMapping`/`@GetMapping`/`@PostMapping`、`@RequestBody`/`@RequestParam`/`@PathVariable`
- 练习：写一套 RESTful 接口：`GET/POST/PUT/DELETE /api/users`

### Day 23 — MyBatis

- 参考：[[MyBatis与ORM]]
- 重点：ORM 概念、`#{}`（预编译）vs `${}`（拼接，有注入风险）
- 重点：Mapper 代理原理、`resultMap` 处理对象关系映射
- 重点：动态 SQL——`<if>`/`<where>`/`<foreach>`/`<set>`
- 练习：用 MyBatis 重写之前的图书管理系统数据层

### Day 24 — Spring Boot 快速开发

- 参考：[[Spring-Boot]]
- 重点：自动配置原理 `@SpringBootApplication` → `@EnableAutoConfiguration`、`application.yml` 配置
- 重点：Starter 机制（引入即用）、内嵌 Tomcat
- 练习：用 Spring Boot + MyBatis + MySQL 搭建一个用户管理 API

### Day 25 — Spring AOP

- 参考：[[Spring-AOP]]
- 重点：AOP 术语（JoinPoint/Pointcut/Advice/Aspect/Weaving）
- 重点：`@Around` 环绕通知实现日志记录、权限校验
- 重点：`@Transactional` 原理——AOP 代理 + `TransactionManager`
- 练习：用 AOP 给所有 Controller 方法加日志切面

### Day 26 — 异常处理 + 参数校验

- 重点：`@RestControllerAdvice` + `@ExceptionHandler` 全局异常处理
- 重点：`@Valid`/`@NotNull`/`@NotBlank`/`@Validated` 参数校验、统一响应体 `Result<T>`
- 练习：给项目加上全局异常处理 + 参数校验 + 统一返回格式

### Day 27~28 — 综合项目：个人博客后台

- CRUD：用户注册登录、文章增删改查、分类标签
- 技术栈：Spring Boot + MyBatis + MySQL
- 包含：统一响应体、全局异常处理、参数校验、AOP 日志、分页查询

### Day 29~30 — 第四阶段复习 + 查漏补缺

- 画 Spring 请求处理全链路图（从浏览器到数据库再返回）
- 10 道 LeetCode 简单算法题保持手感

---

## 第五阶段：中间件 + 进阶（第 6 周）

> 目标：掌握 Redis 和微服务核心组件，能应对面试和工作任务。

### Day 31 — Redis 核心数据类型

- 参考：[[Redis]]
- 重点：5 种数据类型及适用场景——String（缓存/计数器）、Hash（对象存储）、List（消息队列）、Set（去重/交并差）、ZSet（排行榜）
- 练习：用 Jedis/Lettuce 操作 Redis 五种类型

### Day 32 — Redis 缓存实战

- 参考：[[Redis]]
- 重点：缓存穿透（空值缓存/布隆过滤器）、缓存击穿（互斥锁/逻辑过期）、缓存雪崩（随机 TTL/多级缓存）
- 重点：缓存与数据库双写一致性（先更新 DB 再删缓存）
- 练习：给博客项目文章查询加 Redis 缓存

### Day 33 — Spring Boot 集成 Redis

- 重点：`Spring Cache` 抽象——`@Cacheable`/`@CachePut`/`@CacheEvict`
- 练习：用 Spring Cache + Redis 重构博客项目缓存层

### Day 34 — Spring Cloud 微服务入门

- 参考：[[Spring-Cloud]]
- 重点：微服务 vs 单体架构的优缺点
- 重点：Nacos 注册中心 + 配置中心、OpenFeign 远程调用、Gateway 网关
- 练习：将博客项目拆分为用户服务 + 文章服务，用 Feign 通信

### Day 35 — 微服务治理

- 参考：[[Spring-Cloud]]
- 重点：Sentinel 限流/熔断/降级、Seata 分布式事务（了解即可）
- 练习：给博客项目加 Sentinel 限流保护

### Day 36 — 面试高频复盘

- Java 基础：HashMap 原理、String 不可变、异常体系、== vs equals
- 多线程：线程生命周期、synchronized 锁升级、volatile 可见性（参考 [[Java-多线程与JVM]]）
- JVM：内存区域、类加载机制、GC 算法与收集器（参考 [[Java-多线程与JVM]]）
- Spring：IoC 原理、AOP 原理、事务传播行为、Bean 生命周期
- MySQL：索引 B+Tree、事务隔离级别、MVCC、SQL 优化
- Redis：五种类型、缓存三大问题、持久化 RDB/AOF

### Day 37~38 — 缓冲 + 项目完善

- 补漏薄弱环节
- 完善博客项目，写 README，推到 GitHub
- 刷 LeetCode 中等难度题 5 道

---

## 每天必须做的事

1. **手写代码**：每个新知识点至少敲 3 遍，形成肌肉记忆
2. **当天笔记归档**：遇到的问题和解决方案写进对应笔记文件
3. **睡前 10 分钟回顾**：闭眼回想今天学了什么，卡住的地方第二天重点看

---

## 不建议做的事

- 不要纠结原理细节（如 JVM 源码级实现、HashMap 红黑树旋转代码），先会用再深挖
- 不要只看不写——看 10 遍不如写 1 遍
- 不要同时看多个老师的课，一门跟到底
- 不要跳过练习环节
- 不要在第 1~3 周碰框架，Java 核心不熟学框架会非常痛苦

---

## 备查速查

- 所有注解 → [[注解速查表]]
- 设计模式/安全/Maven/部署 → [[架构与安全]]
- Docker/K8s → [[Docker]] / [[Kubernetes]]（第 6 周后再看）

---

> 最后更新：2026-06-03
