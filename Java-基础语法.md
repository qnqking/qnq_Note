# Java 基础语法

## 基本数据类型

| 类型 | 占用字节 | 说明 |
|------|----------|------|
| byte | 1 | 整数 |
| short | 2 | 整数 |
| int | 4 | 整数（默认） |
| long | 8 | 整数（需加 L 后缀） |
| float | 4 | 浮点（需加 F 后缀） |
| double | 8 | 浮点（默认） |
| char | 2 | 字符 |
| boolean | — | true / false |

### 基本类型 vs 引用类型

- **基本数据类型**：只能存放简单数值
- **引用数据类型**：可以存放复杂数据（对象、集合等）

### 包装类

解决基本类型无法参与面向对象操作（如集合），提供工具方法。

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

- 遵循 **FILO**（First In Last Out，先进后出）
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

## 注释

- `//` 单行注释（Ctrl+/）
- `/* */` 多行注释（Ctrl+Shift+/）
- `/** */` 文档注释

---

## Java 三大平台

| 平台 | 全称 | 用途 |
|------|------|------|
| JavaSE | Standard Edition 标准版 | 桌面程序 |
| JavaEE | Enterprise Edition 企业版 | 互联网/企业级应用 |
| JavaME | Micro Edition 微型版 | 手机应用、POS 机 |

---

## [JDK / JRE / JVM](Java-多线程与JVM.md)

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

语法：`数据类型 变量名 = 初始值;`

三要素：有初始值、不能重复、有作用范围（局部变量 / 全局变量）

---

## 项目结构

- **src**：存放 `.java` 源代码
- **out**：存放 `.class` 字节码
- 源代码 → 编译器翻译 → 字节码

---

## 相关笔记

- [[Java-面向对象]] — 封装、继承、多态、抽象
- [[Java-集合框架]] — Collection / Map 体系
- [[Java-泛型与反射]] — 泛型与反射机制
- [[Java-多线程与JVM]] — 并发编程与虚拟机
- [[Java-高级API]] — Lambda、Stream、IO、JDBC
- [[注解速查表]] — 所有注解的大白话解释
