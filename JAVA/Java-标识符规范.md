# Java 标识符规范

## 一、定义规则（必须遵守，否则编译报错）

1. **组成字符**：字母（A-Z/a-z）、数字（0-9）、下划线 `_`、美元符 `$`
2. **首字符限制**：不能以数字开头
3. **大小写敏感**：`name` 和 `Name` 是两个不同的标识符
4. **不能使用关键字/保留字**：如 `class`、`int`、`public`、`static`、`void` 等
5. **不能包含空格**：用驼峰代替单词分隔
6. **无长度限制**：但建议简短有意义

## 二、命名约定（不遵守不报错，但属于不规范代码）

| 元素 | 约定 | 示例 |
|------|------|------|
| **包名** | 全小写，域名倒写 | `com.alibaba.util` |
| **类名/接口名** | 大驼峰（PascalCase） | `HelloWorld`, `UserService` |
| **方法名** | 小驼峰（camelCase） | `getUserName()`, `parseInt()` |
| **变量名** | 小驼峰 | `userName`, `maxAge` |
| **常量** | 全大写，下划线分隔 | `MAX_VALUE`, `PI` |
| **枚举** | 大驼峰（同类名） | `DayOfWeek`, `OrderStatus` |
| **泛型参数** | 单大写字母 | `E`(元素), `T`(类型), `K`(键), `V`(值) |
| **测试方法** | 下划线描述语义 | `should_ThrowException_When_AgeNegative()` |

## 三、命名的语义原则

1. **见名知意**：`age` 优于 `a`，`totalPrice` 优于 `tp`
2. **不用拼音**：`customer` 优于 `kehu`，`price` 优于 `jiage`
3. **不用中文**：标识符只能是 ASCII 字符（虽然 Java 支持 Unicode，但不要用）
4. **布尔变量加前缀**：`isDeleted`、`hasChildren`、`canExecute`
5. **集合变量用复数**：`users` 而非 `userList`
6. **方法动词开头**：`get`/`set`/`is`/`has`/`create`/`remove`/`update`/`find`

## 四、关键字一览（50个，不能用作标识符）

```
abstract   assert      boolean     break       byte
case       catch       char        class       const*
continue   default     do          double      else
enum       extends     final       finally     float
for        goto*       if          implements  import
instanceof int         interface   long        native
new        package     private     protected   public
return     short       static      strictfp    super
switch     synchronized this       throw       throws
transient  try         void        volatile    while
var        yield       record      sealed      permits
```

> `const` 和 `goto` 是保留字，Java 中未使用但也不能作为标识符。

## 五、常见不规范反例

| 坏写法 | 问题 | 应改为 |
|--------|------|--------|
| `String ClassName = "a";` | 变量用大驼峰 | `String className = "a";` |
| `int x1, x2, x3;` | 无意义命名 | `int startIndex, endIndex, step;` |
| `double 3yearAvg;` | 数字开头 | `double threeYearAvg;` |
| `public void getname(){}` | 单词间无驼峰 | `public void getName(){}` |
| `String 姓名 = "张三";` | 使用中文 | `String name = "张三";` |
