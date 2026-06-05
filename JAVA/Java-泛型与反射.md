# Java 泛型与反射

## [泛型（Generics）](Java-集合框架.md)

### 泛型接口

```java
public interface MyList<E> {
    void add(E e);
    void remove(E e);
}
```

### 泛型类

```java
public class MyArrayList<E> implements MyList<E> { ... }
```

### 泛型方法

```java
public static <T> String sum(T a, T b) {
    return "" + a + b;
}
```

### 泛型通配符

```java
// ? extends E（上界通配符 — 只能读，不能写）
// 适用于：只想从集合中取数据
public static double sum(List<? extends Number> list) {
    double total = 0;
    for (Number n : list) total += n.doubleValue();
    return total;
}
// 可以传入 List<Integer>、List<Double>、List<Number>

// ? super E（下界通配符 — 只能写，不能读（只读为 Object））
// 适用于：只想往集合中加数据
public static void addNumbers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
}
// 可以传入 List<Integer>、List<Number>、List<Object>

// PECS 记忆口诀：Producer Extends, Consumer Super
```

---

## [反射（Reflection）](Java-面向对象.md)

在程序运行时动态获取类信息、创建对象、调用成员。

**所有框架的底层**：配置文件存字符串 → 运行时通过反射按需动态加载类。

### 作用

- 探查类的信息
- 动态创建对象
- 探查/调用属性和行为

### 获取 Class 对象的 3 种方式

```java
// 1. 全限定名（框架最常用）
Class<?> cls = Class.forName("com.iwe3.day13.entity.CustomerEntity");

// 2. 对象.getClass()
obj.getClass();

// 3. .class 属性
CustomerEntity.class;
```

- 每个类加载后在 JVM 方法区产生唯一的 Class 对象
- 一个类只加载一次

### 反射完整示例

```java
// 假设有类 User{ private String name; public User(){} public void say(String msg){...} }

// 1. 获取 Class 对象
Class<?> clz = Class.forName("com.example.User");

// 2. 获取无参构造器并创建对象
Constructor<?> ctor = clz.getConstructor();
Object obj = ctor.newInstance();

// 3. 操作私有字段
Field field = clz.getDeclaredField("name");
field.setAccessible(true);           // 暴力反射，突破 private
field.set(obj, "小明");
String name = (String) field.get(obj);
System.out.println(name);            // 小明

// 4. 调用方法
Method method = clz.getDeclaredMethod("say", String.class);
method.invoke(obj, "Hello World");   // 等价于 obj.say("Hello World")
```

> 框架正是通过"全限定名字符串 → 反射"来动态加载和操作类

## 相关笔记

- [[Java-面向对象]] — 类加载机制
- [[JAVA/Java-多线程与JVM]] — JVM 方法区
- [[Java-集合框架]] — 泛型在集合中的使用
