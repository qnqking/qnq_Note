# Java 高级 API

## Lambda 表达式

替代接口作为匿名内部类的写法。

语法：`(参数列表) -> { 业务代码 }`

---

## Stream 流

处理数据的流水线（与 IO 流概念不同）。集合的作用是**装数据**，Stream 的作用是**处理数据**。

常用 API：过滤（filter）、转换（map）、统计（count）、排序（sorted）、采集（collect）……

### 两种流

- `list.stream()` — 串行流，按顺序执行
- `list.parallelStream()` — 并行流，数据分块同时执行

---

## IO 流

流分类：
1. 按方向 → 输入流 / 输出流
2. 按大小 → 字节流 / 字符流
3. 按功能 → 节点流 / 功能流

| 流类型 | 适用场景 |
|--------|----------|
| 字符流（Reader/Writer） | 只能读取文本文件 |
| 字节流（InputStream/OutputStream） | 所有二进制文件（图片/音频/视频/PDF/压缩包） |
| 缓冲流（Buffered） | 基于已有节点流，添加缓冲功能 |
| 打印输出流（PrintStream） | 以符合 OS 字符集方式打印到控制台 |

**数据存储演进**：变量 → 数组 → 集合（缺乏持久性） → 文件 → MySQL 数据库

---

## JDBC（Java Database Connectivity）

SUN 公司定义规范（接口），各大数据库厂商自行实现（驱动 Driver）。

是 Hibernate / MyBatis / MyBatis-Plus / Spring-Data-JPA 的底层。

### SQL 注入防御

```java
// 危险写法：字符串拼接
String sql = "SELECT * FROM books WHERE author = " + author;

// 安全写法：参数化查询（PreparedStatement / MyBatis #{}）
```

---

## 异常

| 类型 | 继承 | 检查时机 |
|------|------|----------|
| 受检异常（Checked） | Exception | 编译时就提醒 |
| 非受检异常（Unchecked） | RuntimeException | 运行时才可能抛出 |

- `throw`：用来抛出异常
- `throws`：声明方法可能抛出异常

### 全局异常处理

三层架构：底层不处理，层层向上抛。

- 编译时异常：抓（try-catch）/ 抛（throws）
- 运行时异常：等它抛，抛了再修改代码

---

## BigDecimal

- 用于精确计算（对应数据库 DECIMAL 类型）
- 比较用 `compareTo()`
- 构造推荐用 String

---

## 日期时间 API

**Java 8 之前**：`Date`（方法过时）、`Calendar`、`SimpleDateFormat`（非线程安全）

**Java 8 之后**：`LocalDate`（日期）、`LocalTime`（时间）、`LocalDateTime`（日期时间）

`SimpleDateFormat` 建议用 `ThreadLocal` 包装或改用 `java.time` 包。

---

## 常见 Java 内置注解

| 注解 | 含义 |
|------|------|
| `@Deprecated` | 标记方法/类已废弃 |
| `@Override` | 标记为重写的方法 |
| `@SuppressWarnings("all")` | 忽略警告 |
| `@FunctionalInterface` | 标记为函数式接口 |

---

## Lombok

```java
@Getter                // 生成 getter()
@Setter                // 生成 setter()
@NoArgsConstructor     // 生成无参构造器
@AllArgsConstructor    // 生成全参构造器
@RequiredArgsConstructor // 生成有参构造器（配合 @NonNull）
@Data                  // = @Getter + @Setter + @ToString + @EqualsAndHashCode
@Slf4j                 // 自动创建 log 变量
```

---

## 枚举（Enum）

将静态常量对象化，将有穷集合的数据逐一罗列。

---

## Properties 集合

Map 体系子集，K-V 都是字符串。常用于 `db.properties` 数据库配置文件。

---

## File & Socket

- `File`：文件操作类
- `Socket`：网络编程类

---

## 相关笔记

- [[Java-基础语法]] — 基本数据类型
- [[Java-多线程与JVM]] — BigDecimal 的线程安全使用
- [[注解速查表]] — 所有注解的详细说明
- [[MySQL-核心基础]] — JDBC 连接数据库
