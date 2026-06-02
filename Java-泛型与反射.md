# Java 泛型与反射

## 泛型（Generics）

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

---

## 反射（Reflection）

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

---

## 相关笔记

- [[Java-面向对象]] — 类加载机制
- [[Java-多线程与JVM]] — JVM 方法区
- [[Java-集合框架]] — 泛型在集合中的使用
