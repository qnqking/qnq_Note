# Java 集合框架

集合 = 装东西的容器，最大好处是通过 API 封装底层的扩容/缩容操作。

---

## List vs Set

| 特性 | List 体系 | Set 体系 |
|------|-----------|----------|
| 有序性 | 有序 | 可有序可无序 |
| 下标 | 有 | 无 |
| 重复元素 | 不去重 | 去重 |

---

## List 实现类

### ArrayList

- 底层：数组
- 查询快（有下标），增删慢（需移位）
- 线程不安全
- 扩容 1.5 倍

### LinkedList

- 底层：双向链表
- 增删快，查询慢
- 线程不安全

### Vector

- 底层：数组
- 线程安全（synchronized），性能低（已少用）

```java
// ArrayList —— 日常开发首选
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Python");
list.add(0, "Go");           // 指定位置插入
list.get(1);                  // "Java"（按索引取值）
list.set(1, "C++");           // 替换
list.remove(0);               // 按索引删除
list.remove("Python");        // 按对象删除
list.contains("Java");        // 是否包含
list.size();                  // 元素个数

// 三种遍历
for (int i = 0; i < list.size(); i++) { ... }           // 索引
for (String s : list) { ... }                           // 增强 for
list.forEach(s -> System.out.println(s));               // Lambda

// LinkedList —— 频繁增删场景
LinkedList<String> linked = new LinkedList<>();
linked.addFirst("头");       // 头部插入（O(1)）
linked.addLast("尾");         // 尾部插入（O(1)）
linked.removeFirst();         // 头部删除（O(1)）
```

---

## Set 实现类

### HashSet

- 底层：HashMap 的 Key（Hash 表）
- 去重规则：`hashCode()` + `equals()`
- 无序，不可自定义排序

#### HashSet 去重原理

1. 通过 `hashCode()` 得 hash 值，按 `(n-1) & hash` 得下标
2. 位置为空 → 直接存储
3. 位置有值 → `equals()` 比较内容
4. 内容相同 → Key 不存，Value 覆盖
5. 内容不同 → 单向链表追加
6. Java 8+：链表长度超过阈值 → 转为红黑树

### TreeSet

- 底层：红黑树（Red-Black Tree）
- 去重规则：比较器（Comparator）
- 有序，可通过传入比较器自定义排序

```java
// HashSet —— 去重
Set<String> set = new HashSet<>();
set.add("Java");
set.add("Python");
set.add("Java");             // 重复，不存
System.out.println(set);     // [Java, Python]（无序）

// 自定义对象去重 —— 必须重写 equals + hashCode
Set<Student> students = new HashSet<>();
students.add(new Student("小明", 20));
students.add(new Student("小明", 20));  // 视为重复
System.out.println(students.size());    // 1

// TreeSet —— 排序 + 去重
Set<Integer> sorted = new TreeSet<>();
sorted.add(5); sorted.add(2); sorted.add(8);
System.out.println(sorted);  // [2, 5, 8]（自动升序）

// TreeSet 自定义排序 —— 传入比较器
Set<Student> ts = new TreeSet<>((o1, o2) -> o2.getAge() - o1.getAge());
ts.add(new Student("小明", 20));
ts.add(new Student("小红", 25));  // 按年龄降序
```

## Map 体系

双列集合，有 Key 有 Value。Key 不允许重复，Value 可以重复。Key-Value 之间是 1 对 1 关系。

```java
Map<String, Integer> map = new HashMap<>();

// 增/改
map.put("Java", 1);
map.put("Python", 2);
map.put("Java", 3);              // Key 重复，覆盖原 Value（1→3）

// 查
map.get("Java");                 // 3
map.getOrDefault("Go", 0);       // 0（不存在时返回默认值）
map.containsKey("Java");         // true
map.containsValue(2);            // true

// 删
map.remove("Python");

// 遍历
map.forEach((k, v) -> System.out.println(k + "=" + v));

for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + "=" + entry.getValue());
}
```

### HashMap

- Java 8 前：数组 + 单向链表
- Java 8 后：数组 + 单向链表 + 红黑树
- Key 去重依据：`hashCode()` + `equals()`
- 线程**非**安全
- 允许 null 键值

### HashMap vs Hashtable vs ConcurrentHashMap

| 特性 | HashMap | Hashtable | ConcurrentHashMap |
|------|---------|-----------|-------------------|
| 线程安全 | 不安全 | 安全（方法加 synchronized） | 安全 |
| null 键值 | 允许 | 不允许 | 不允许 |
| 性能 | 高 | 低（已淘汰） | 高（JDK8 使用 CAS + synchronized） |

---

## 红黑树规则

1. 所有节点要么红，要么黑
2. 根节点一定是黑
3. 红色节点的 2 个子节点必须是黑
4. 从任意节点到叶子节点的所有路径上，必须经过相同数量的黑色节点
5. 叶子节点一定是黑，且值为 NULL

---

## 线程安全 vs 非安全

- **线程安全**：同一时刻只能有 1 根线程操作数据 → 效率低，数据一致性有保证
- **线程非安全**：允许多根线程同时操作 → 效率高，数据可能无法保证一致性

---

## 迭代器（Iterator）

集合遍历手段，特点：**边遍历边删除数据**。

```java
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Python");
list.add("Go");

Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if ("Python".equals(s)) {
        it.remove();    // 用迭代器删除，安全
    }
}
// ❌ 增强 for 中直接 list.remove() 会抛 ConcurrentModificationException
```

---

## 相关笔记

- [[Java-基础语法]] — 数据类型、运算符、控制流
- [[Java-面向对象]] — 封装、继承、多态、抽象
- [[Java-泛型与反射]] — 泛型与反射机制
- [[Java-多线程与JVM]] — 线程安全底层原理
