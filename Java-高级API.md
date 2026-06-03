# Java 高级 API

## Lambda 表达式

替代接口作为匿名内部类的写法。

语法：`(参数列表) -> { 业务代码 }`

```java
// 传统匿名内部类
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello");
    }
}).start();

// Lambda 简写
new Thread(() -> System.out.println("Hello")).start();

// 各种省略写法
// a) 有参 -> 单条语句省略大括号和 return
list.forEach(s -> System.out.println(s));

// b) 有参多行
list.forEach(s -> {
    String upper = s.toUpperCase();
    System.out.println(upper);
});

// c) 方法引用
list.forEach(System.out::println);       // 实例方法引用
list.stream().map(String::toUpperCase);  // 类方法引用（参数就是调用者）
```

## Stream 流

处理数据的流水线（与 IO 流概念不同）。集合的作用是**装数据**，Stream 的作用是**处理数据**。

常用 API：过滤（filter）、转换（map）、统计（count）、排序（sorted）、采集（collect）……

```java
List<String> list = Arrays.asList("Java", "Python", "Go", "C++", "Java");

// filter — 过滤
list.stream().filter(s -> s.length() > 2)
             .forEach(System.out::println);  // Java, Python, C++, Java

// distinct — 去重
list.stream().distinct().collect(Collectors.toList());  // [Java, Python, Go, C++]

// map — 映射/转换
list.stream().map(String::toUpperCase)
             .collect(Collectors.toList());  // [JAVA, PYTHON, GO, C++, JAVA]

// sorted — 排序
list.stream().sorted().collect(Collectors.toList());   // 自然排序
list.stream().sorted((a,b) -> b.compareTo(a))          // 自定义降序
             .collect(Collectors.toList());

// limit / skip
list.stream().limit(3).collect(Collectors.toList());   // 前 3 个
list.stream().skip(2).collect(Collectors.toList());    // 跳过前 2 个

// 组合使用：找出长度 > 2 的元素，转大写，去重，排序，取前 2
List<String> result = list.stream()
    .filter(s -> s.length() > 2)
    .map(String::toUpperCase)
    .distinct()
    .sorted()
    .limit(2)
    .collect(Collectors.toList());
// → [C++, JAVA]

// 终端操作
list.stream().count();                   // 计数
list.stream().anyMatch(s -> s.equals("Java"));  // 是否有匹配
List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
nums.stream().reduce(0, Integer::sum);   // 求和 = 15
```

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

```java
// ======== 字节流 ========
// 读文件
try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.txt"))) {
    byte[] buf = new byte[1024];
    int len;
    while ((len = bis.read(buf)) != -1) {
        System.out.print(new String(buf, 0, len));
    }
}

// 写文件
try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("b.txt"))) {
    bos.write("Hello Java".getBytes());
}

// ======== 字符流（只能处理文本）========
// 逐行读 + 逐行写
try (BufferedReader br = new BufferedReader(new FileReader("a.txt"));
     BufferedWriter bw = new BufferedWriter(new FileWriter("b.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        bw.write(line);
        bw.newLine();
    }
}
// try-with-resources 自动关闭流，省去 finally 中手写 close()
```

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

### JDBC CRUD 完整示例

```java
// 查询
String sql = "SELECT id, name, age FROM student WHERE age > ?";
try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setInt(1, 18);
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            System.out.println(id + ", " + name + ", " + age);
        }
    }
}

// 增删改（用 executeUpdate）
String insert = "INSERT INTO student(name, age) VALUES(?, ?)";
try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement ps = conn.prepareStatement(insert)) {
    ps.setString(1, "小明");
    ps.setInt(2, 20);
    int rows = ps.executeUpdate();   // 返回影响行数
    System.out.println("插入 " + rows + " 行");
}
```

---

## 异常

| 类型 | 继承 | 检查时机 |
|------|------|----------|
| 受检异常（Checked） | Exception | 编译时就提醒 |
| 非受检异常（Unchecked） | RuntimeException | 运行时才可能抛出 |

- `throw`：用来抛出异常
- `throws`：声明方法可能抛出异常

```java
// try-catch-finally
try {
    int n = Integer.parseInt("abc");   // 可能抛 NumberFormatException
} catch (NumberFormatException e) {
    System.out.println("数字格式错误：" + e.getMessage());
} catch (Exception e) {
    System.out.println("其他异常：" + e);
} finally {
    System.out.println("无论是否异常都会执行");
}

// try-with-resources（自动关闭实现 AutoCloseable 的资源）
// 不需要写 finally 手动 close()
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
```

### 全局异常处理

三层架构：底层不处理，层层向上抛。

- 编译时异常：抓（try-catch）/ 抛（throws）
- 运行时异常：等它抛，抛了再修改代码

---

## BigDecimal

- 用于精确计算（对应数据库 DECIMAL 类型）
- 比较用 `compareTo()`
- 构造推荐用 String

```java
// ❌ 浮点数精度丢失
System.out.println(0.1 + 0.2);           // 0.30000000000000004

// ✅ BigDecimal 精确计算
BigDecimal a = new BigDecimal("0.1");     // 一定要用 String 构造！
BigDecimal b = new BigDecimal("0.2");
BigDecimal sum = a.add(b);                // 0.3
BigDecimal diff = a.subtract(b);          // -0.1
BigDecimal product = a.multiply(b);       // 0.02
BigDecimal quotient = a.divide(b, 2, RoundingMode.HALF_UP);  // 保留 2 位，四舍五入

// 比较 —— 必须用 compareTo，不能用 equals（equals 比较值和精度）
a.compareTo(b);        // -1（a < b）
a.compareTo(a);        // 0（相等）
b.compareTo(a);        // 1（b > a）
```

---

## 日期时间 API

**Java 8 之前**：`Date`（方法过时）、`Calendar`、`SimpleDateFormat`（非线程安全）

**Java 8 之后**：`LocalDate`（日期）、`LocalTime`（时间）、`LocalDateTime`（日期时间）

```java
// 获取当前日期时间
LocalDate now = LocalDate.now();               // 2026-06-03
LocalTime time = LocalTime.now();              // 15:30:00.123
LocalDateTime dt = LocalDateTime.now();        // 2026-06-03T15:30:00.123

// 创建指定日期
LocalDate d = LocalDate.of(2026, 6, 3);
LocalDateTime dt2 = LocalDateTime.of(2026, 6, 3, 15, 30);

// 日期运算
d.plusDays(1);           // 加 1 天
d.minusMonths(1);        // 减 1 个月
d.isAfter(now);          // 是否在 now 之后
d.isBefore(now);         // 是否在 now 之前

// 格式化
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String str = dt.format(fmt);                   // "2026-06-03 15:30:00"
dt = LocalDateTime.parse("2026-06-03 15:30:00", fmt);
```

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
