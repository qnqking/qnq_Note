# Java 基础语法

## 源文件基本规则

1. **扩展名**：源文件必须以 `.java` 为扩展名。
2. **main 方法入口**：`public static void main(String[] args)` — 固定签名，不可改名。
3. **严格区分大小写**：`System.out.println()` 不能写成 `printLn`。
4. **语句以分号结尾**：每条语句必须以 `;` 结束，缺少则编译失败。
5. **大括号成对出现**：建议先写好 `{}`，再填充代码，避免括号不匹配。
6. **最多一个 public 类**：一个 `.java` 文件中最多只能有一个 `public` 类，其他类不限。每个类编译后都会生成独立的 `.class` 文件。
7. **文件名与 public 类名一致**：包含 `public class Hello` 则文件名必须是 `Hello.java`。
8. **main 方法可写在非 public 类中**：运行时指定该类即可（如 `java Dog`），一个项目可以有多个入口。

---

## 基本数据类型

| 类型      | 占用字节 | 说明           |
| ------- | ---- | ------------ |
| byte    | 1    | 整数           |
| short   | 2    | 整数           |
| int     | 4    | 整数（默认）       |
| long    | 8    | 整数（需加 L 后缀）  |
| float   | 4    | 浮点（需加 F 后缀）  |
| double  | 8    | 浮点（默认）       |
| char    | 2    | 字符           |
| boolean | -    | true / false |

### 基本类型 vs 引用类型

- **基本数据类型**：只能存放简单数值
- **引用数据类型**：可以存放复杂数据（对象、集合等）

### 包装类

解决基本类型无法参与面向对象操作（如集合），提供工具方法。

### 基本类型转换

#### 自动类型转换（隐式 / Widening）

小范围类型 → 大范围类型，自动发生，不会丢失数据。

```
byte → short → int → long → float → double
       char  ↗
```

> `char` 和 `short` 都是 2 字节，但 `char` 是无符号的（0~65535），`short` 有符号（-32768~32767），两者不能互相自动转换。

```java
byte b = 10;
short s = b;      // ✅ byte → short
int i = s;        // ✅ short → int
long l = i;       // ✅ int → long
float f = l;      // ✅ long → float（可能丢失精度，见下文）
double d = f;     // ✅ float → double

char c = 'A';
int code = c;     // ✅ char → int，code = 65（Unicode 码点）
```

**注意**：`long`（8 字节）→ `float`（4 字节）是自动的，但因为 `float` 用科学计数法表示，范围比 `long` 大。不过**精度会丢失**——`float` 只有约 7 位有效数字。

```java
long bigNum = 123456789012345L;
float f = bigNum;  // ✅ 编译通过，但 f 的值是 1.2345679E14，末尾丢失了精度
```

#### 强制类型转换（显式 / Narrowing）

大范围类型 → 小范围类型，需要手动 `(类型)` 强转，**可能丢失数据**。

```java
double d = 9.78;
int i = (int) d;       // i = 9，小数被截断（不是四舍五入）

int big = 300;
byte small = (byte) big;  // small = 44，高位被截断，溢出

long l = 100L;
int n = (int) l;       // 安全，100 在 int 范围内
```

**溢出原理**：`300` 的二进制 `1 0010 1100`，byte 只取低 8 位 `0010 1100` = 44。

#### 表达式中的自动提升

在表达式中，`byte` / `short` / `char` 会**自动提升为 `int`** 再参与运算：

```java
byte a = 10;
byte b = 20;
// byte c = a + b;    // ❌ 编译错误，a + b 结果是 int
int c = a + b;        // ✅ 正确
byte d = (byte)(a + b); // 强转回 byte

char c1 = 'A';
char c2 = 'B';
int sum = c1 + c2;    // 65 + 66 = 131，char 运算自动提升为 int
```

#### 常量优化

如果赋值的是**编译期常量**且在目标范围内，不会报错：

```java
byte b = 10;           // ✅ 10 是常量，在 byte 范围内
// byte b2 = 128;      // ❌ 超出范围

final int x = 10;
byte b3 = x;           // ✅ x 是 final 常量，编译期确定
```

#### 转换规则总结

| 转换方向 | 方式 | 风险 |
|----------|------|------|
| 小 → 大（如 `int` → `long`） | 自动 | 无 |
| 整数 → 浮点（如 `long` → `float`） | 自动 | 可能丢精度 |
| 大 → 小（如 `double` → `int`） | 强转 `(int)` | 截断/溢出 |
| 浮点 → 整数（如 `double` → `int`） | 强转 `(int)` | 截断小数 |
| `byte`/`short`/`char` 运算 | 自动提升为 `int` | 需强转回去 |

---

## String 系列

| 类型 | 可变性 | 线程安全 | 性能 |
|------|--------|----------|------|
| String | 不可变，每次修改生成新对象 | — | 拼接产生大量对象，浪费内存 |
| StringBuilder | 可变（底层数组扩容） | 不安全 | 高 |
| StringBuffer | 可变 | 安全（方法加 synchronized） | 较低 |

- String 底层是字符数组 `char[]`
- 创建方式推荐：`String s = "值";`

---

## 运算符

| 类别 | 运算符 | 说明 |
|------|--------|------|
| 赋值 | `=` `+=` `-=` `*=` `/=` `%=` | 将右边值赋给左边 |
| 四则 | `+` `-` `*` `/` `%` | `%` 取模（求余数） |
| 比较 | `>` `<` `>=` `<=` `==` `!=` | 结果一定是 boolean |
| 三元 | `(条件) ? 值1 : 值2` | 条件满足返回值1，否则返回值2 |
| 自增/自减 | `++` `--` | 前置先变后用，后置先用后变 |
| 逻辑 | `&&` `||` `!` | 短路：`&&` 前不满足则后不执行 |
| 按位 | `&` `|` `^` | 按二进制位运算 |

### `&&` vs `&`（短路 vs 不短路）

| 区别 | `&&` | `&` |
|------|------|-----|
| 短路 | 左边为 false 时**右边不执行** | 两边都会执行 |
| 适用 | 逻辑判断（boolean） | 整数按位与 / 逻辑判断 |
| 性能 | 通常更优（省掉不必要的运算） | 需要等待两边都算完 |

```java
// 短路 &&  — 右边不执行，a++ 不触发
int a = 1;
boolean r1 = (false) && (++a == 2);
// → r1 = false, a 还是 1

// 不短路 & — 两边都执行，b++ 一定触发
int b = 1;
boolean r2 = (false) & (++b == 2);
// → r2 = false, b 变成了 2

// 整数按位与 & （整数的二进制每位做与运算）
int x = 3 & 5;   // 3=011, 5=101 → 001 → 结果 1
```

> **结论**：逻辑判断用 `&&`，只有需要"无论如何都执行右边"才用 `&`；操作整数用 `&`。

`||` vs `|` 同理——`||` 左边为 true 时右边不执行。

---

## 控制流程

### 循环

```java
// for：已知循环次数
for (初始条件; 循环判断条件; 变量改变) { 循环体 }

// 增强 for（foreach）：遍历数组/集合
for (数据类型 变量名 : 数组名) { ... }

// while：已知退出条件
while (条件) { 循环体 }

// do-while：至少执行1次
do { 循环体 } while (条件);
```

### 分支

```java
// if 双分支
if (条件) { ... } else { ... }

// if 多分支（适合范围条件）
if (条件1) { ... } else if (条件2) { ... } else { ... }

// switch 多分支（适合离散值条件）
switch (变量) {
    case 值1: ...; break;
    default: ...; break;
}
```

---

## 数组

存储大量相同数据类型的数据结构。特点：元素类型一致、空间连续、长度固定。

### 声明与初始化

```java
// 方式一：动态初始化（指定长度，元素为默认值）
int[] arr1 = new int[5];          // {0, 0, 0, 0, 0}
String[] arr2 = new String[3];    // {null, null, null}

// 方式二：静态初始化（指定元素值）
int[] arr3 = {1, 2, 3, 4, 5};
String[] arr4 = {"Java", "Python", "Go"};

// 方式三：先声明再分配
int[] arr5;
arr5 = new int[]{10, 20, 30};
```

### 遍历

```java
int[] arr = {11, 22, 33, 44, 55};

// for 循环（有索引，可修改元素）
for (int i = 0; i < arr.length; i++) {
    System.out.println("下标" + i + " → " + arr[i]);
}

// 增强 for（简洁，只读遍历）
for (int num : arr) {
    System.out.println(num);
}

// 遍历 String 数组
String[] names = {"小明", "小红", "小刚"};
for (String name : names) {
    System.out.println(name);
}

// 遍历二维数组（外层 int[]，内层 int）
int[][] matrix = {{1, 2}, {3, 4}, {5, 6}};
for (int[] row : matrix) {
    for (int val : row) {
        System.out.print(val + " ");
    }
    System.out.println();
}

// 计算所有元素的和
int[] scores = {88, 92, 76, 85, 90};
int total = 0;
for (int s : scores) {
    total += s;
}
System.out.println("总分: " + total);  // 431
```

### 常用操作

```java
int[] arr = {5, 3, 8, 1, 2};

// 排序（原地排序）
Arrays.sort(arr);                        // {1, 2, 3, 5, 8}

// 二分查找（必须先排序）
int idx = Arrays.binarySearch(arr, 5);   // 返回 3

// 拷贝
int[] copy = Arrays.copyOf(arr, arr.length);

// 比较
boolean eq = Arrays.equals(arr, copy);   // true

// 打印数组内容
System.out.println(Arrays.toString(arr)); // [1, 2, 3, 5, 8]

// 填充
Arrays.fill(arr, 0);                     // {0, 0, 0, 0, 0}
```

### 二维数组

```java
// 声明
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// 遍历
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();   // 换行
}
```

### 数组使用场景

| 场景 | 说明 |
|------|------|
| 存储固定数量数据 | 如一年 12 个月的天数 |
| 算法练习 | 排序、查找、双指针、滑动窗口 |
| 作为方法参数 | 可变参数底层就是数组 |

---

## 函数/方法

> 方法就是一段可重复调用的代码块，用于完成特定功能。

### 定义格式

```java
[访问修饰符] [static] [final] 返回类型 方法名(形参列表) {
    // 方法体
    return 值;  // 如果返回类型不是 void
}
```

### 4 种组合（完整示例）

```java
public class MethodDemo {

    // 1. 无参无返回 — 打印固定内容
    public static void sayHello() {
        System.out.println("Hello, Java!");
    }

    // 2. 有参无返回 — 打印传入内容
    public static void greet(String name) {
        System.out.println("你好, " + name + "!");
    }

    // 3. 无参有返回 — 返回固定值
    public static int getAge() {
        return 25;
    }

    // 4. 有参有返回 — 计算后返回结果
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        sayHello();                  // → Hello, Java!
        greet("小明");               // → 你好, 小明!
        int age = getAge();          // age = 25
        int sum = add(3, 5);         // sum = 8
        System.out.println("结果: " + sum);
    }
}
```

### 方法重载（Overload）

**同一类中**，方法名相同，参数列表不同（个数/类型/顺序不同），与返回值无关。

```java
// 以下三个方法互为重载
public static int max(int a, int b) {
    return a > b ? a : b;
}

public static int max(int a, int b, int c) {
    int temp = max(a, b);
    return temp > c ? temp : c;
}

public static double max(double a, double b) {
    return a > b ? a : b;
}

// 调用时会根据参数类型自动匹配
max(3, 5);       // 调用第一个
max(1, 2, 3);    // 调用第二个
max(3.5, 2.8);   // 调用第三个
```

### 递归

方法调用自身。必须有两个条件：**递归公式** + **终止条件**，否则栈溢出。

```java
// 求 n 的阶乘：n! = n × (n-1)!
public static int factorial(int n) {
    if (n == 1) return 1;           // 终止条件
    return n * factorial(n - 1);    // 递归公式
}
// factorial(5) = 5 × 4 × 3 × 2 × 1 = 120

// 求第 n 个斐波那契数：1, 1, 2, 3, 5, 8, ...
public static int fibonacci(int n) {
    if (n == 1 || n == 2) return 1;            // 终止条件
    return fibonacci(n - 1) + fibonacci(n - 2); // 递归公式
}
// fibonacci(6) = 8
```

### 可变参数

JDK 5 引入，底层是数组。一个方法最多一个可变参数，且必须放在最后。

```java
// 求和（参数个数任意）
public static int sum(int... nums) {
    int total = 0;
    for (int n : nums) {
        total += n;
    }
    return total;
}

sum();         // 0
sum(1, 2);     // 3
sum(1, 2, 3);  // 6
```

### 方法参数传递规则

| 参数类型 | 传递内容 | 方法内修改对调用处的影响 |
|----------|----------|--------------------------|
| 基本类型（int, double, ...） | 值的副本 | **无影响** |
| 引用类型（对象, 数组, ...） | 地址的副本 | **影响**（指向同一对象） |

```java
// 基本类型 — 不影响原值
public static void changeInt(int x) {
    x = 100;      // 只改了副本
}
int a = 10;
changeInt(a);     // a 仍然是 10

// 引用类型 — 会影响原对象
public static void changeArray(int[] arr) {
    arr[0] = 999; // 改了指向的对象
}
int[] nums = {1, 2, 3};
changeArray(nums);  // nums[0] 变成 999
```

---

## 栈（Stack）

- 遵循 **LIFO**（Last In First Out，后进先出，也可称 FILO）
- Java 中方法调用基于栈结构

---

## ASCII 编码

最早期的编码集：33 个不可显示字符 + 95 个可显示字符

---

## Debug 调试

| 快捷键 | 功能 |
|--------|------|
| F8 | 逐行向下执行，不进入方法内部 |
| F7 | 逐行向下执行，遇到方法进入方法内部 |
| Shift+F8 | 从方法内部直接退出 |
| F9 | 快速跳到下一个断点 |

---

## 常用 API

### String 系列

```java
String s = "Hello Java 你好";

// 长度与判空
s.length();                          // 13（空格也算）
s.isEmpty();                         // false
s.isBlank();                         // false（JDK 11+，只含空白才返回 true）

// 获取字符
s.charAt(0);                         // 'H'
s.charAt(6);                         // 'J'

// 截取
s.substring(6);                      // "Java 你好"
s.substring(0, 5);                   // "Hello"（左闭右开）

// 查找
s.indexOf("Java");                   // 6，找不到返回 -1
s.lastIndexOf("l");                  // 3（最后一次出现位置）
s.contains("llo");                   // true

// 大小写
s.toUpperCase();                     // "HELLO JAVA 你好"
s.toLowerCase();                     // "hello java 你好"

// 去除首尾空白
"   a b c   ".trim();               // "a b c"（只去半角空格）
"   a b c   ".strip();              // "a b c"（也能去全角空格，JDK 11+）

// 拆分
"a,b,c".split(",");                  // ["a", "b", "c"]

// 替换
s.replace("Java", "World");          // "Hello World 你好"
s.replaceAll("\\s+", "-");           // "Hello-Java-你好"（正则替换）

// 格式化（类似 C 的 printf）
String.format("我叫%s，今年%d岁", "小明", 20);  // "我叫小明，今年20岁"

// 字符串拼接成数组形式
String.join(", ", "A", "B", "C");    // "A, B, C"
```

### 包装类与类型转换

```java
// String → 基本类型
int n = Integer.parseInt("123");       // 123
double d = Double.parseDouble("3.14"); // 3.14
long l = Long.parseLong("999");        // 999

// 基本类型 → String
String s1 = String.valueOf(123);       // "123"
String s2 = Integer.toString(456);     // "456"
String s3 = 3.14 + "";                 // "3.14"（快捷方式）

// 包装类常量
int max = Integer.MAX_VALUE;           // 2147483647
int min = Integer.MIN_VALUE;           // -2147483648

// Character 字符判断
Character.isDigit('5');                // true
Character.isLetter('A');               // true
Character.isUpperCase('A');            // true
Character.isWhitespace(' ');           // true
```

### Math 数学运算

```java
Math.abs(-5);                          // 5（绝对值）
Math.max(3, 8);                        // 8
Math.min(3, 8);                        // 3
Math.pow(2, 10);                       // 1024.0（2 的 10 次方）
Math.sqrt(16);                         // 4.0（平方根）
Math.random();                         // [0.0, 1.0) 随机数

// 随机整数：取 [0, 100) 的整数
int rand = (int)(Math.random() * 100);

// 四舍五入
Math.round(3.4);                       // 3
Math.round(3.5);                       // 4
Math.ceil(3.1);                        // 4.0（向上取整）
Math.floor(3.9);                       // 3.0（向下取整）
```

### Scanner 键盘输入

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

System.out.print("请输入姓名：");
String name = sc.next();               // 读取字符串（遇空格/换行结束）

System.out.print("请输入年龄：");
int age = sc.nextInt();                // 读取整数

System.out.print("请输入一句话：");
sc.nextLine();                         // 吃掉上一个 nextInt 剩下的换行符 ⚠️
String line = sc.nextLine();           // 读取一整行

System.out.println("姓名：" + name + "，年龄：" + age);
sc.close();                            // 用完关闭
```

### Arrays 工具类

```java
int[] arr = {5, 3, 8, 1, 2};

Arrays.sort(arr);                      // 排序 → [1, 2, 3, 5, 8]
Arrays.toString(arr);                  // 打印 → "[1, 2, 3, 5, 8]"
Arrays.binarySearch(arr, 5);           // 二分查找 → 3（先排序）
Arrays.copyOf(arr, 3);                 // 截取前 3 个 → [1, 2, 3]
Arrays.equals(arr, arr2);              // 比较两个数组
Arrays.fill(arr, 0);                   // 全部填 0
```

### Object 三大方法

```java
public class Student {
    String name;
    int age;

    // 重写 toString → println 时不再打印内存地址
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }

    // 重写 equals → 比较内容而非地址
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return age == s.age && Objects.equals(name, s.name);
    }

    // 重写 equals 必须重写 hashCode（HashMap/HashSet 需要）
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

---

## 注释与 JavaDoc

- `//` 单行注释（Ctrl+/）
- `/* */` 多行注释（Ctrl+Shift+/）
- `/** */` 文档注释（JavaDoc）— 可生成标准 API 文档

### JavaDoc 常用标签

| 标签 | 说明 | 适用范围 |
|------|------|----------|
| `@author` | 作者 | 类、接口、包 |
| `@version` | 版本号 | 类、接口、包 |
| `@param 参数名 描述` | 方法参数说明 | 方法、构造方法 |
| `@return 描述` | 返回值说明 | 方法 |
| `@throws 异常类 描述` | 可能抛出的异常（同 `@exception`） | 方法、构造方法 |
| `@deprecated` | 已过期，不推荐使用 | 所有 |
| `@see 引用` | "参见"链接 | 所有 |
| `@since 版本` | 从哪个版本开始支持 | 所有 |
| `{@link 类#成员}` | 插入超链接 | 行内 |
| `{@inheritDoc}` | 从父类继承注释 | 重写方法 |

### 代码维护注释

单行/多行注释（`//`、`/* */`）主要给维护者看，重点说明：
- **为什么这么写**（而非"做了什么"）
- 修改时需要注意什么
- 如何修改

---

## 代码格式规范

### 缩进

- 使用 **4 个空格** 代替 Tab
- Tab 键向右缩进，Shift+Tab 向左缩进

### 空格

运算符（`+` `-` `*` `/`）和等号（`=`）两边各加一个空格：

```java
// ✅ 推荐
int n3 = 1 + 3 * 4;

// ❌ 不推荐
int n3=1+3*4;
```

### 行宽

每行不超过 **80 个字符**，过长时手动换行。

### 文件编码

源文件统一使用 **UTF-8** 编码。

### 大括号风格

**行尾风格（推荐）**：Java 设计者和大多数开源项目采用。

```java
public static void main(String[] args) {
    // 代码逻辑
}
```

**次行风格**：左大括号独占一行。

```java
public static void main(String[] args)
{
    // 代码逻辑
}
```

---

## Java 三大平台

| 平台 | 全称 | 用途 |
|------|------|------|
| JavaSE | Standard Edition 标准版 | 桌面程序 |
| JavaEE | Enterprise Edition 企业版 | 互联网/企业级应用 |
| JavaME | Micro Edition 微型版 | 手机应用、POS 机 |

---

## [JDK / JRE / JVM](JAVA/Java-多线程与JVM.md)

- **JVM**（Java Virtual Machine）：对接各大 OS 平台
- **JRE**（Java Runtime Environment）：提供运行环境
- **JDK**（Java Development Kit）：开发工具包
- 包含关系：JDK ⊃ JRE ⊃ JVM

---

## 包结构规范

```
第1层：项目性质（com=商业 / org=开源 / edu=教育 / gov=政府）
第2层：公司或团队名（com.alibaba / org.springframework）
第3层：项目名
```

---

## 环境变量

```
JAVA_HOME = Java安装目录
Path      = Java命令所在目录
CLASSPATH = .;
```

---

## 变量

变量是内存中的命名区域，用来保存数据。通过变量名存取内存中的值。

```java
int age = 18;   // int = 类型, age = 变量名, 18 = 值
```

### 声明与初始化

```java
// 先声明后初始化
int count;
count = 10;

// 声明同时初始化
int age = 25;

// ⚠️ 局部变量使用前必须显式初始化，否则编译报错
// 成员变量（实例/静态）有默认值
```

### 变量分类

#### 按类型分

**基本类型（8种）**：

| 类型 | 字节数 | 取值范围 | 默认值 | 示例 |
|------|--------|----------|--------|------|
| byte | 1 | -128 ~ 127 | 0 | `byte b = 100;` |
| short | 2 | -32768 ~ 32767 | 0 | `short s = 1000;` |
| int | 4 | 约 ±21亿 | 0 | `int i = 50000;` |
| long | 8 | 很大 | 0L | `long l = 100000L;` |
| float | 4 | 约 ±3.4e38 | 0.0f | `float f = 3.14f;` |
| double | 8 | 约 ±1.8e308 | 0.0d | `double d = 3.14159;` |
| char | 2 | 0 ~ 65535（Unicode） | `' '` | `char c = 'A';` |
| boolean | JVM 实现相关 | true / false | false | `boolean flag = true;` |

> **注意**：`float` 必须加 `f/F` 后缀，`long` 必须加 `l/L` 后缀。`boolean` 的字节数在 JVM 规范中没有精确定义，通常用 1 字节或 4 字节（数组时可能用 1 字节/元素）。

**引用类型**：指向对象（或数组、字符串等），默认值为 `null`。

```java
String message = "Hello Java";
int[] scores = new int[10];
```

#### 按作用域分

| 分类 | 声明位置 | static | 默认值 | 生命周期 |
|------|----------|--------|--------|----------|
| 局部变量 | 方法/代码块内 | 否 | 无（必须手动初始化） | 从声明到代码块结束 |
| 实例变量 | 类中方法外 | 否 | 有 | 跟随对象 |
| 静态变量 | 类中方法外 | 是 | 有 | 跟随类（程序运行期间） |

```java
public class Example {
    static int count = 0;    // 静态变量（类变量），所有对象共享
    String name;             // 实例变量，每个对象独立副本
    int age;                 // 实例变量，默认 0

    public void method() {
        int x = 10;          // 局部变量，必须手动初始化
        if (x > 5) {
            String s = "ok"; // 只在 if 块内有效
        }
        // s 在这里无法访问
    }
}
```

### 命名规则

- 由字母、数字、下划线 `_`、美元符 `$` 组成，不能以数字开头
- 不能是 Java 关键字（`int`、`class`、`public` 等）
- 严格区分大小写：`age` 和 `Age` 是两个不同变量
- **小驼峰命名**：`studentName`、`totalScore`（变量）
- **全大写下划线**：`MAX_VALUE`、`DEFAULT_COLOR`（常量 `static final`）

### 类型转换

```java
// 自动转换（小 → 大）：int → long → float → double
int i = 100;
long l = i;    // 自动转换

// 强制转换（大 → 小），可能丢失数据
double d = 9.78;
int n = (int) d;   // n = 9，丢失小数部分
```

### 常见误区

```java
// 1. 局部变量必须初始化
int a;
System.out.println(a); // ❌ 编译错误

// 2. 同一作用域不能重复声明
int x = 5;
int x = 10;   // ❌ 报错

// 3. 浮点数精度问题
double price = 2.0 - 1.1;  // 0.8999999999999999（非精确）
// 精确计算用 BigDecimal

// 4. 引用类型变量指向同一对象
Person p1 = new Person();
Person p2 = p1;   // p2 和 p1 指向同一个对象
p2.name = "李四"; // p1.name 也是 "李四"

// 5. 静态变量推荐用 类名.变量名 访问，而非 对象.变量名
```

---

## 核心 Java 命令

| 命令 | 功能 | 示例 |
|------|------|------|
| `javac` | 编译 `.java` → `.class` | `javac HelloWorld.java` |
| `java` | 运行字节码（指定主类名，不带 `.class`） | `java HelloWorld` |
| `javadoc` | 生成 API 文档（HTML） | `javadoc -d doc MyClass.java` |
| `jar` | 打包多个 `.class` 为 `.jar` | `jar cvf app.jar *.class` |

---

## 路径概念

### 绝对路径

从文件系统根目录开始的完整路径，不依赖运行环境。可移植性差。

```
Windows：C:\Users\Name\config.txt  （Java 中需转义：C:\\Users\\Name\\config.txt）
Linux：  /home/user/project/config.txt
```

### 相对路径

相对于 JVM 工作目录（`System.getProperty("user.dir")`）的路径：
- `.` 当前目录
- `..` 上级目录

可移植性强，适合跨环境部署。IDE 中默认工作目录通常是项目根目录。

### 最佳实践

| 场景 | 方式 |
|------|------|
| 读取项目内置资源 | ClassPath 相对路径：`getClass().getClassLoader().getResourceAsStream("config.properties")` |
| 读写外部文件/日志 | 基于 `user.home` 构建路径，或通过配置文件动态获取 |

> 避免硬编码绝对路径。内部资源用 ClassPath，外部文件用相对路径或配置。

---

## 项目结构

- **src**：存放 `.java` 源代码
- **out**：存放 `.class` 字节码
- 源代码 → 编译器翻译 → 字节码

---

## 相关笔记

- [[Java-面向对象]] — 封装、继承、多态、抽象
- [[Java-集合框架]] — Collection / Map 体系
- [[JAVA/Java-泛型与反射]] — 泛型与反射机制
- [[JAVA/Java-多线程与JVM]] — 并发编程与虚拟机
- [[JAVA/Java-高级API]] — Lambda、Stream、IO、JDBC
- [[注解速查表]] — 所有注解的大白话解释
