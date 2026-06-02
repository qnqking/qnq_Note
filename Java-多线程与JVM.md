# Java 多线程与 JVM

---

## 一、多线程基础

### 进程 vs 线程

- **进程（Process）**：运行状态下的应用程序，资源分配的最小单位（CPU/内存/磁盘/网络）
- **线程（Thread）**：进程内部执行任务的最小单位，一个进程至少需要一根线程

### 并发 vs 并行

- **并行**：多个任务同时执行（多核 CPU 之间）
- **并发**：多个任务交替执行（单核内部面临多线程任务）
- **高并发**：单核/单台服务器同时面临海量任务

### 线程创建方式

1. 继承 Thread 类（缺点：Java 单继承，占用了父类位置）
2. 实现 Runnable 接口
3. 实现 Callable 接口（有返回值）
4. 线程池（ThreadPoolExecutor）

### start() vs run()

- `run()`：定义任务，由 CPU 调用
- `start()`：启动任务，由程序员调用

### 线程生命周期

```
NEW（创建）→ RUNNABLE（就绪/运行）→ BLOCKED（阻塞）/ WAITING（等待）/ TIMED_WAITING（计时等待）→ TERMINATED（死亡）
```

### CPU 调度算法

- **分时调度**：按时间片随机分配给线程
- **优先级调度**：优先级越高（1-10，默认 5），分配到时间片的概率越高

### 核心方法

| 方法 | 作用 | 特点 |
|------|------|------|
| `sleep()` | 休眠，运行→阻塞 | 自动苏醒，**不释放锁** |
| `wait()` | 等待 | 需要 `notify()` / `notifyAll()` 唤醒，**主动释放锁** |
| `join()` | 子线程加入其他线程 | 子线程执行时主线程处于阻塞状态 |
| `yield()` | 线程让步 | 暗示 CPU 当前任务不重要，不一定成功 |

---

## 二、锁机制

### synchronized

悲观锁，同一时刻只运行 1 根线程处理数据。非公平、可重入、不可剥夺、互斥/排他。

```java
// 同步代码块
synchronized(this) { ... }        // 锁当前对象
synchronized(类.class) { ... }    // 锁类的 Class 对象
synchronized(obj) { ... }         // 锁任意对象

// 同步方法
public synchronized void method() { ... }
```

### 锁升级过程（不可降级）

```
无状态 → 偏向锁（Biased Lock）→ 轻量级锁（Lightweight Lock，自旋）→ 重量级锁（Heavyweight Lock）
```

### Lock

- 可公平/不公平，可重入，不可剥夺，互斥

### CAS（Compare-And-Swap）& ABA 问题

- **CAS**：乐观锁，乐观认为并发较低没有冲突
- **ABA 问题**：变量从 X→Y→X，另一个线程误以为值没变 → 用版本号解决

### 死锁产生的 4 个条件

1. 使用互斥锁（synchronized）
2. 其他线程不可剥夺
3. 无限期等待
4. 一直保持死锁状态

---

## 三、volatile

- 保证可见性（Visibility）
- 禁止指令重排（Ordering）
- **不保证原子性**（Atomicity，需 synchronized 或 Lock 保证）

---

## 四、单例模式（Singleton）

### 饿汉模式（天生线程安全）

```java
private static JdbcTemplate instance = new JdbcTemplate();
private JdbcTemplate() {}
public static JdbcTemplate getInstance() { return instance; }
```

### 懒汉模式（DCL，双重检查锁）

```java
private static volatile JdbcTemplate instance = null;
public static JdbcTemplate getInstance() {
    if (instance == null) {
        synchronized (JdbcTemplate.class) {
            if (instance == null) {
                instance = new JdbcTemplate();
            }
        }
    }
    return instance;
}
```

---

## 五、ThreadLocal

线程局部变量，每个线程有独立副本。应用场景：数据库连接管理、用户信息传递。

---

## 六、守护线程（Daemon Thread）

为其他线程服务（如 GC），主线程结束则守护线程自动终止。

---

## 七、线程池

- `es.execute()`：只能提交 Runnable 任务
- `es.submit()`：可提交 Runnable 和 Callable 任务

---

## 八、JVM

### 内存区域

| 区域 | 共享性 | 存储内容 |
|------|--------|----------|
| 方法区（Method Area） | 线程共享 | 类信息、常量、静态变量 |
| 堆（Heap） | 线程共享 | 对象实例和数组 |
| 虚拟机栈（VM Stack） | 线程独享 | 局部变量、方法调用（栈帧 Frame） |
| 本地方法栈（Native Method Stack） | 线程独享 | 为 Native 方法服务 |
| 程序计数器（PC Register） | 线程独享 | 记录当前线程执行位置 |

### 类加载

双亲委派模型（Parent Delegation Model）

### GC（垃圾回收）

- `finalize()` 方法在回收前可能调用（不推荐使用）

---

## 相关笔记

- [[Java-集合框架]] — ConcurrentHashMap 等线程安全集合
- [[Java-面向对象]] — 单例模式的设计
- [[注解速查表]] — synchronized、volatile 关键字说明
