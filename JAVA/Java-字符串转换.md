# Java 字符串转换

## 一、基本类型 → 字符串

### 方式一：`String.valueOf()`（推荐，不会空指针）

```java
String.valueOf(123);        // "123"
String.valueOf(3.14);       // "3.14"
String.valueOf(true);       // "true"
String.valueOf('A');        // "A"
String.valueOf(null);       // "null"（字符串），不会报错
```

### 方式二：`+ ""`（最便捷）

```java
123 + "";       // "123"
3.14 + "";      // "3.14"
true + "";      // "true"
```

> 底层最终还是调用 `String.valueOf()`。

### 方式三：包装类 `toString()`

```java
Integer.toString(123);      // "123"
Double.toString(3.14);      // "3.14"
Boolean.toString(true);     // "true"
Character.toString('A');    // "A"
```

### 方式四：`String.format()`

```java
String.format("%d", 123);             // "123"
String.format("%.2f", 3.14159);       // "3.14"
String.format("%x", 255);             // "ff"（十六进制）
String.format("%06d", 7);             // "000007"（补零）
```

---

## 二、字符串 → 基本类型

```java
Integer.parseInt("123");        // 123
Integer.valueOf("123");         // 123（返回 Integer 对象）
Double.parseDouble("3.14");     // 3.14
Long.parseLong("100");          // 100L
Float.parseFloat("2.5");        // 2.5F
Boolean.parseBoolean("true");   // true
```

### 非十进制字符串解析

```java
Integer.parseInt("1101", 2);    // 13   二→十
Integer.parseInt("1A", 16);     // 26   十六→十
Integer.parseInt("77", 8);      // 63   八→十
```

### 捕获异常

```java
// 非数字字符串会抛 NumberFormatException
try {
    int n = Integer.parseInt("abc");  // 异常！
} catch (NumberFormatException e) {
    // 处理不可转换的字符串
}
```

---

## 三、String ↔ char[]

```java
// String → char[]
char[] chars = "hello".toCharArray();   // ['h','e','l','l','o']

// char[] → String
String s1 = new String(chars);
String s2 = String.valueOf(chars);
```

---

## 四、String ↔ byte[]（编码/解码）

```java
// String → byte[]（编码）
byte[] utf8  = "你好".getBytes();                   // 默认 UTF-8
byte[] utf8b = "你好".getBytes(StandardCharsets.UTF_8);
byte[] gbk   = "你好".getBytes("GBK");

// byte[] → String（解码）
String s1 = new String(utf8);                       // UTF-8 解码
String s2 = new String(gbk, "GBK");                  // 指定 GBK 解码
String s3 = new String(utf8, StandardCharsets.UTF_8);
```

> 编码和解码必须用同一字符集，否则出现乱码。

---

## 五、StringBuilder / StringBuffer → String

```java
StringBuilder sb = new StringBuilder();
sb.append("hello").append(" world");

String result = sb.toString();       // 最终转换
String result2 = sb + "";            // 也可以但没必要
```

---

## 六、大小写转换

```java
"Hello".toUpperCase();      // "HELLO"
"Hello".toLowerCase();      // "hello"
```

---

## 七、其他包装类 ↔ 字符串

```java
// Character
Character.toString('A');          // "A"
Character.isDigit('5');           // true
Character.isLetter('a');          // true

// BigDecimal / BigInteger
new BigDecimal("123.45").toString();
new BigInteger("9999999999").toString();
```

---

## 八、数组 ↔ 字符串

```java
int[] arr = {1, 2, 3};

Arrays.toString(arr);                          // "[1, 2, 3]"
Arrays.toString(new int[][]{{1,2},{3,4}});     // "[[1, 2], [3, 4]]"  多维用 deepToString
```

---

## 九、常用进制格式化转换

```java
Integer.toBinaryString(13);     // "1101"     十进制→二进制
Integer.toOctalString(13);      // "15"       十进制→八进制
Integer.toHexString(255);       // "ff"       十进制→十六进制
Integer.toString(42, 16);       // "2a"       十进制→任意进制(2~36)

Integer.parseInt("1101", 2);    // 13         任意进制→十进制
```

## 十、速查总表

| 源类型 | 目标类型 | 方法 |
|--------|----------|------|
| 任意 → String | String | `String.valueOf(x)` / `x + ""` |
| String → int | int | `Integer.parseInt(s)` |
| String → Integer | Integer | `Integer.valueOf(s)` |
| String → char[] | char[] | `s.toCharArray()` |
| char[] → String | String | `new String(chars)` |
| String → byte[] | byte[] | `s.getBytes(编码)` |
| byte[] → String | String | `new String(bytes, 编码)` |
| StringBuilder → String | String | `sb.toString()` |
| 小写 ↔ 大写 | String | `s.toUpperCase()` / `s.toLowerCase()` |
| 数组 → String | String | `Arrays.toString(arr)` |
