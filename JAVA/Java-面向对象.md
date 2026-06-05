# Java 面向对象

## 类与对象

- **类（Class）**：对相同/相似东西的抽象（模板）
- **对象（Object）**：根据类产生的真实存在的东西（实例 Instance）
- 类是对象的抽象，对象是类的具体实例

```java
// 定义类
public class Student {
    String name;   // 成员变量（属性）
    int age;

    // 方法（行为）
    public void study() {
        System.out.println(name + " 正在学习");
    }
}

// 使用类
Student s1 = new Student();   // 创建对象
s1.name = "小明";             // 赋值
s1.age = 20;
s1.study();                   // → 小明 正在学习
```

---

## 四大特征

### 1. 封装（Encapsulation）

包装 + 隐藏：

- **包装**：使用函数将代码包装起来
- **隐藏**：使用访问修饰符控制可见性

#### 访问修饰符

| 修饰符 | 本类 | 本包 | 其他包的子类 | 其他包 |
|--------|------|------|------------|--------|
| private | ✓ | ✗ | ✗ | ✗ |
| 默认（default） | ✓ | ✓ | ✗ | ✗ |
| protected | ✓ | ✓ | ✓ | ✗ |
| public | ✓ | ✓ | ✓ | ✓ |

> 企业推荐：属性用 private，行为用 public；搭配 getter/setter

```java
public class Person {
    // 属性私有化
    private String name;
    private int age;

    // getter/setter 暴露访问
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }

    // setter 中可以加校验逻辑
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄不合法");
        }
        this.age = age;
    }
}

// 使用
Person p = new Person();
p.setName("小明");
p.setAge(20);
System.out.println(p.getName());  // 小明
```

### 2. 继承（Inheritance）

- 子承父业，提升代码复用性
- Java 为单继承
- 抽象类单继承，接口多实现

**重写（Override）**：子类重新编写父类的行为（运行时多态）

```java
// 父类
public class Animal {
    String name;

    public void eat() {
        System.out.println(name + " 在吃东西");
    }
}

// 子类继承父类
public class Dog extends Animal {

    public void bark() {
        System.out.println(name + " 汪汪叫");
    }

    // 重写父类方法
    @Override
    public void eat() {
        System.out.println(name + " 在啃骨头");
    }
}

// 使用
Dog d = new Dog();
d.name = "旺财";
d.eat();    // → 旺财 在啃骨头
d.bark();   // → 旺财 汪汪叫
```

### 3. 多态（Polymorphism）

相同行为，因对象不同有不同实现。

三个必要条件：
1. 继承
2. 方法重写
3. 父类引用 → 子类对象（向上转型 Upcasting）

应用：多态集合、多态参数

```java
// 父类
public class Animal {
    public void sound() {
        System.out.println("动物在叫...");
    }
}

// 子类各不同实现
public class Cat extends Animal {
    @Override
    public void sound() { System.out.println("喵喵喵"); }
}

public class Dog extends Animal {
    @Override
    public void sound() { System.out.println("汪汪汪"); }
}

public class Duck extends Animal {
    @Override
    public void sound() { System.out.println("嘎嘎嘎"); }
}

// 多态调用 —— 父类引用指向子类对象
Animal a1 = new Cat();
Animal a2 = new Dog();
Animal a3 = new Duck();

a1.sound();  // → 喵喵喵（编译看左边 Animal，运行看右边 Cat）
a2.sound();  // → 汪汪汪
a3.sound();  // → 嘎嘎嘎

// 多态集合 —— 统一操作不同类型的子类对象
Animal[] zoo = {new Cat(), new Dog(), new Duck()};
for (Animal a : zoo) {
    a.sound();   // 同一句代码，不同对象不同行为
}

// 多态参数 —— 父类引用当形参，可接收所有子类
public static void makeSound(Animal a) {
    a.sound();
}
makeSound(new Cat());   // 喵喵喵
makeSound(new Dog());   // 汪汪汪
```

### 4. 抽象（Abstraction）

```java
// 抽象类 —— 有共同代码，但自己不能被实例化
public abstract class Animal {
    protected String name;

    public Animal(String name) { this.name = name; }

    // 抽象方法：子类必须实现
    public abstract void sound();

    // 具体方法：所有子类共用
    public void sleep() {
        System.out.println(name + " 在睡觉");
    }
}

// 接口 —— 纯规范，定义"能干什么"
public interface Flyable {
    void fly();        // 默认 public abstract
}

public interface Swimable {
    void swim();
}

// 一个类只能继承一个抽象类，但可以实现多个接口
public class Duck extends Animal implements Flyable, Swimable {
    public Duck() { super("鸭子"); }

    @Override
    public void sound() { System.out.println("嘎嘎嘎"); }

    @Override
    public void fly() { System.out.println("鸭子在飞"); }

    @Override
    public void swim() { System.out.println("鸭子在游"); }
}
```

---

## 重写（Override）vs[方法重载（Overload）](JAVA/Java-基础语法.md#方法重载（Overload）) 

| 特性 | 重写（Override） | 重载（Overload） |
|------|-----------------|-----------------|
| 位置 | 子类重写父类方法 | 同一类中 |
| 参数 | 必须相同 | 必须不同（个数/类型/顺序） |
| 返回类型 | 相同或协变 | 可不同 |
| 决定时机 | 运行时多态 | 编译时决定 |

---

## 抽象类 vs 接口

| 特性 | 抽象类 | 接口 |
|------|--------|------|
| 继承 | 单继承 | 多实现 |
| 方法 | 可有抽象方法 + 具体方法 | Java 8+ 可有 default/static 方法 |
| 构造器 | 有 | 无 |
| 变量 | 普通成员变量 | 仅常量（public static final） |

### 使用场景对比

```java
// ===== 抽象类：is-a 关系，表示"是什么"，提取共同代码 =====
public abstract class Employee {
    protected String name;
    protected double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // 抽象方法：子类各不同
    public abstract double calculateBonus();

    // 具体方法：子类共用
    public void clockIn() {
        System.out.println(name + " 打卡上班");
    }

    public double getAnnualSalary() {
        return salary * 12 + calculateBonus();
    }
}

public class Programmer extends Employee {
    public Programmer(String name, double salary) {
        super(name, salary);
    }

    @Override
    public double calculateBonus() {
        return salary * 3;   // 程序员 3 个月年终奖
    }
}

public class Salesman extends Employee {
    public Salesman(String name, double salary) {
        super(name, salary);
    }

    @Override
    public double calculateBonus() {
        return salary * 1;   // 销售 1 个月年终奖
    }
}

// ===== 接口：can-do 关系，表示"能干什么"，定义额外能力 =====
public interface Flyable {
    void fly();
}

public interface Teachable {
    void teach();
}

public interface Codeable {
    void code();
}

// 一个类 = 一个抽象父类 + 多个接口
public class TechLead extends Employee implements Codeable, Teachable {

    public TechLead(String name, double salary) {
        super(name, salary);
    }

    @Override
    public double calculateBonus() { return salary * 4; }

    @Override
    public void code() {
        System.out.println(name + " 在写核心代码");
    }

    @Override
    public void teach() {
        System.out.println(name + " 在带新人");
    }
}

// ===== 使用 =====
TechLead tl = new TechLead("老张", 20000);
tl.clockIn();                                   // 老张 打卡上班（抽象类的具体方法）
tl.code();                                      // 老张 在写核心代码（接口实现）
tl.teach();                                     // 老张 在带新人（接口实现）
System.out.println("年薪: " + tl.getAnnualSalary());  // 抽 + 接 + 具体方法混用
```

> **选谁？** 有共同属性/代码 → 抽象类；定义纯规范/能力 → 接口。两者不冲突，各司其职。

---

## static 关键字

- `static` 修饰 = 类属性/类行为 → 跟类有关，跟具体对象无关
- 工具类的工具方法用 static 修饰

### 调用规则

```
1. 静态内部 → 不能直接调用实例成员（实例成员尚未创建）
2. 实例内部 → 可以直接调用静态成员
3. 静态内部 → 可以直接调用静态成员
4. 实例内部 → 可以直接调用实例成员
```

- 调用方式：`类名.静态成员` / `对象.实例成员`

```java
public class Counter {
    // 静态变量 —— 所有对象共享，类加载时就初始化
    public static int count = 0;

    // 实例变量 —— 每个对象各有一份
    public int id;

    public Counter() {
        count++;        // 每次 new 都 +1
        this.id = count;
    }

    // 静态方法 —— 只能访问静态成员
    public static int getCount() {
        return count;   // 可以
        // return id;   // ❌ 编译错误，静态方法不能访问实例变量
    }

    // 实例方法 —— 能访问静态和实例成员
    public void show() {
        System.out.println("id=" + id + ", count=" + count);  // 都能访问
    }
}

// 使用
System.out.println(Counter.count);    // 0，通过类名访问
Counter c1 = new Counter();           // count=1, id=1
Counter c2 = new Counter();           // count=2, id=2
System.out.println(Counter.count);    // 2（所有对象共享）
```

---

## this & super

| 用法 | 含义 |
|------|------|
| `this.` | 代表当前对象自身，调用当前类的属性/行为 |
| `super.` | 代表父类，调用父类的属性/行为 |
| `this()` | 调用当前类的其他构造器 |
| `super()` | 调用父类的构造器 |

```java
public class Parent {
    String name = "父类";

    public Parent() { System.out.println("父类无参构造"); }
    public Parent(String name) { this.name = name; }
}

public class Child extends Parent {
    String name = "子类";

    public Child() {
        super();          // 调用父类构造器（必须在第一行）
    }

    public Child(String name) {
        super(name);      // 调用父类有参构造
    }

    public void show() {
        System.out.println(this.name);   // 子类 — 当前对象
        System.out.println(super.name);  // 父类 — 父类属性
    }

    public void callOther() {
        this.show();      // 调用当前类其他方法
    }
}
```

## 构造器（Constructor）

- 无参构造器：编译器在编译期间自动生成
- 执行步骤：new 创建对象 → 属性分配空间 → 赋初始值（0/null） → 执行剩余代码
- **加有参构造器之前，必须先加无参构造器**

```java
public class Student {
    private String name;
    private int age;

    // 无参构造（Java 默认提供，一旦写了有参则需手动补充）
    public Student() {}

    // 有参构造（用于创建对象时直接赋值）
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// 使用
Student s1 = new Student();              // 无参
Student s2 = new Student("小明", 20);    // 有参
```

## [类的加载](JAVA/Java-多线程与JVM.md)

- 每个类只加载 1 次
- 静态成员在类加载期间就准备好，实例成员在运行期间创建

> 详见 [[JAVA/Java-多线程与JVM]] 的 JVM 类加载部分

---

## 关键字全集

| 关键字            | 类别      | 一句话解释                             |
| -------------- | ------- | --------------------------------- |
| `byte`         | 基本数据类型  | 占 1 字节的整数，范围 [-128, 127]          |
| `short`        | 基本数据类型  | 占 2 字节的整数，范围 [-32768, 32767]      |
| `int`          | 基本数据类型  | 占 4 字节的整数，默认整数类型                  |
| `long`         | 基本数据类型  | 占 8 字节的整数，需加 L 后缀                 |
| `float`        | 基本数据类型  | 占 4 字节的浮点数，需加 F 后缀                |
| `double`       | 基本数据类型  | 占 8 字节的浮点数，默认浮点类型                 |
| `char`         | 基本数据类型  | 占 2 字节，表示单个 Unicode 字符            |
| `boolean`      | 基本数据类型  | 取值只有 true / false                 |
| `void`         | 基本数据类型  | 表示方法无返回值                          |
| `if`           | 流程控制    | 条件判断，满足则执行                        |
| `else`         | 流程控制    | 配合 if，条件不满足时执行                    |
| `switch`       | 流程控制    | 多分支选择，适合离散值                       |
| `case`         | 流程控制    | switch 中的分支标签                     |
| `default`      | 流程控制    | switch 中无匹配时的默认分支；接口默认方法          |
| `for`          | 流程控制    | 已知次数的循环                           |
| `while`        | 流程控制    | 条件循环，先判断后执行                       |
| `do`           | 流程控制    | 配合 while，先执行后判断（至少执行 1 次）         |
| `break`        | 流程控制    | 跳出当前循环或 switch                    |
| `continue`     | 流程控制    | 跳过本次循环剩余代码，进入下一次迭代                |
| `try`          | 流程控制    | 包裹可能抛出异常的代码块                      |
| `catch`        | 流程控制    | 捕获并处理指定类型异常                       |
| `finally`      | 流程控制    | 无论是否异常都执行的代码块                     |
| `throw`        | 流程控制    | 手动抛出一个异常对象                        |
| `throws`       | 流程控制    | 声明方法可能抛出的异常类型                     |
| `class`        | 类/方法/变量 | 定义一个类                             |
| `interface`    | 类/方法/变量 | 定义一个接口                            |
| `enum`         | 类/方法/变量 | 定义一个枚举类型                          |
| `extends`      | 类/方法/变量 | 继承父类（单继承）                         |
| `implements`   | 类/方法/变量 | 实现接口（可多实现）                        |
| `private`      | 访问控制    | 仅本类可见                             |
| `protected`    | 访问控制    | 本类 + 本包 + 其他包子类可见                 |
| `public`       | 访问控制    | 所有位置可见                            |
| `static`       | 修饰符     | 修饰成员为类级别（不依赖对象）                   |
| `final`        | 修饰符     | 类不可继承 / 方法不可重写 / 变量不可修改           |
| `abstract`     | 修饰符     | 类不可实例化 / 方法无方法体等待子类实现             |
| `synchronized` | 修饰符     | 加互斥锁，同一时刻只允许一个线程进入                |
| `volatile`     | 修饰符     | 保证变量的可见性 + 禁止指令重排（不保证原子性）         |
| `transient`    | 修饰符     | 序列化时忽略该字段                         |
| `native`       | 修饰符     | 声明方法由本地代码（C/C++）实现                |
| `strictfp`     | 修饰符     | 强制浮点运算严格遵守 IEEE 754 标准            |
| `default`      | 修饰符     | 接口中定义默认实现方法（Java 8+）              |
| `this`         | 引用相关    | 代表当前对象自身                          |
| `super`        | 引用相关    | 代表父类对象的引用                         |
| `new`          | 引用相关    | 创建对象实例并分配内存                       |
| `instanceof`   | 引用相关    | 判断对象是否属于某个类/接口                    |
| `package`      | 包/导入    | 声明当前类所属的包                         |
| `import`       | 包/导入    | 导入其他包的类                           |
| `true`         | 其他      | 布尔真值，不是关键字但是保留字面量                 |
| `false`        | 其他      | 布尔假值                              |
| `null`         | 其他      | 引用类型的默认空值                         |
| `assert`       | 其他      | 断言，调试用（默认关闭，需 -ea 启用）             |
| `var`          | 其他      | 局部变量类型推断（Java 10+）                |
| `goto`         | 保留字     | 未被使用（Java 中跳转用 break/continue 标签） |
| `const`        | 保留字     | 未被使用（Java 中用 final 定义常量）          |

> 所有关键字都是小写，不能用作标识符（变量名、方法名、类名）。

---

## [7 大设计原则](架构与安全.md)

1. **单一原则（SRP）**：一个类只干一件事
2. **开闭原则（OCP）**：对扩展开放，对修改关闭
3. **里氏替换原则（LSP）**：子类能替代父类，尽量不重写父类已实现方法
4. **迪米特法则（LoD）**：最少知道原则
5. **依赖倒置原则（DIP）**：面向接口编程，而非面向实现类
6. **接口隔离原则（ISP）**：接口最小化
7. **组合聚合原则（CRP）**：多用组合少用继承

> 7 大设计原则 → 23 种设计模式。设计原则是思想，设计模式是方法。

---

## [equals() & hashCode() 联合判重](JAVA/Java-集合框架.md)

1. 先判 `hashCode()`（性能好，做快筛）→ 不同则直接判定不同
2. hashCode 相同 → `equals()` 做最终判断（性能不好但准确）

```java
public class Student {
    String name;
    int age;

    // equals: 比较两个对象内容是否相同
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;           // 地址相同，直接 true
        if (o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return age == s.age && Objects.equals(name, s.name);
    }

    // hashCode: 内容相同的对象必须返回相同 hash
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

// 验证
Student s1 = new Student("小明", 20);
Student s2 = new Student("小明", 20);
System.out.println(s1.equals(s2));     // true（内容相同）
System.out.println(s1.hashCode() == s2.hashCode());  // true
```

## [比较器（Comparator）](JAVA/Java-集合框架.md)

- 比较器内部封装了基础排序算法（两两比对）
- 重写 `compare()` 方法专注于多字段排序业务

```java
List<Student> list = new ArrayList<>();
list.add(new Student("小明", 20));
list.add(new Student("小红", 18));
list.add(new Student("小刚", 20));

// 按年龄升序
list.sort((o1, o2) -> o1.getAge() - o2.getAge());

// 多字段排序：先按年龄升序，年龄相同按名字降序
list.sort((o1, o2) -> {
    if (o1.getAge() != o2.getAge()) {
        return o1.getAge() - o2.getAge();
    }
    return o2.getName().compareTo(o1.getName());
});

// 或者用 Comparator.comparing 链式写法
list.sort(Comparator.comparingInt(Student::getAge)
                    .thenComparing(Student::getName).reversed());
```

## 雪花 ID（Snowflake ID）

Twitter 提出的分布式唯一 ID 生成算法：
- 最高位符号位 = 0（永远正数）
- 41 位时间戳（69 年范围内不重复）
- 10 位机器 ID（5 位机房 + 5 位机器）
- 12 位序列号（1ms 内产生 4096 个 ID）

---

## 相关笔记

- [[JAVA/Java-基础语法]] — 数据类型、运算符、控制流
- [[JAVA/Java-集合框架]] — Collection / Map 体系
- [[JAVA/Java-泛型与反射]] — 泛型与反射机制
- [[JAVA/Java-高级API]] — Lambda、Stream、IO、JDBC
- [[注解速查表]] — 所有注解的大白话解释
