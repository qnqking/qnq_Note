# Java全站课程笔记

> 来源：ProcessOn 思维导图，按课程阶段整理

---

## 第一阶段：OS（操作系统基础）

### 第一周：计算机基础

**计算机组成**
- 硬件：键盘、鼠标、主板、CPU、硬盘、内存条、显示器、散热器、电源、打印机……
- 软件：操作系统 + 应用软件
- 操作系统：Windows、Linux、Unix、Mac、iOS、Android、鸿蒙
- 应用软件：具有特定功能、服务使用者的程序

**学开发的目的：生产应用软件**

**客户端工具**：Xshell、FinalShell、WindTerm、MobaXterm

---

### 第二周：计算机网络 & 虚拟平台

#### 互联网概念
- **internet（互联网）**：描述计算机之间如何进行互联
- **Internet（因特网）**：描述全球电脑应该如何通讯
- **互联网协议**：定义计算机如何接入互联网 + 定义通讯协议

#### OSI 7层架构（从下向上）
| 层级 | 说明 |
|------|------|
| 物理层 | 网线/WiFi/路由器/交换机，传输电信号（高电压=1，低电压=0） |
| 数据链路层 | 遵从"以太网Ethernet通讯协议"，数据帧 = head（MAC地址） + data |
| 网络层 | 划分子网，IP协议，IP地址32位（前24位子网，后8位主机） |
| 传输层 | 端口到端口的通讯（TCP/UDP） |
| 会话层 | — |
| 表示层 | — |
| 应用层 | HTTP/HTTPS/FTP/AMQP/POP3…… |

> 下3层跟硬件有关，上3层跟软件有关，中间跟传输有关

#### ARP协议
- 地址解析协议，根据IP地址获取MAC地址
- MAC地址：48位，前24位=厂家，后24位=网卡序号

#### IP地址 & 子网掩码
- IP地址与子网掩码按位与运算，结果相同 = 同一子网
- 同为1才为1，其他为0

#### TCP协议（面向连接，可靠）
- **三次握手**：客户端请求 → 服务器响应 → 客户端确认
- **四次挥手**：客户端请求断开 → 服务器收到 → 服务器清除消息 → 正式断开

#### UDP协议
- 类似于寄包裹/发短信，数据+地址捆绑传输
- 不保证消息一定被接收，可靠性差

#### TCP 5层架构
物理层 → 数据链路层 → 网络层 → 传输层 → 应用层

#### 网络常用命令
| 命令 | 作用 |
|------|------|
| `ipconfig -all` | 查看IP地址 |
| `ping` | 测试网络是否通畅 |
| `netstat` | 查看端口使用情况 |
| `Ctrl+C` | 打断命令执行 |

---

### Linux 基础命令

#### 文件目录操作
| 命令 | 作用 |
|------|------|
| `cd .` | 当前目录 |
| `cd ..` | 上级目录 |
| `cd ~` | 家目录 |
| `pwd` | 查看当前工作目录 |
| `clear` / `Ctrl+L` | 清屏 |
| `ls -l -h -a` | 查看目录文件（长格式/人性化/含隐藏） |
| `history` | 查看历史记录 |
| `type` | 查看命令类型 |

#### 文件操作
| 命令 | 作用 |
|------|------|
| `mkdir -p` | 创建目录（递归） |
| `rm -r -f` | 删除目录/文件（递归/强制） |
| `cp -r` | 复制 |
| `mv` | 移动/重命名 |
| `touch` | 创建空文件 |
| `wc -l -w -c` | 统计行数/单词/字节 |

#### 关机重启
| 命令 | 作用 |
|------|------|
| `shutdown -h 5` | 5分钟后关机 |
| `shutdown -r` | 重启 |
| `poweroff` | 立即关机 |
| `reboot` | 立即重启 |

#### 磁盘相关
| 命令 | 作用 |
|------|------|
| `du -s -h` | 查看目录使用情况 |
| `lsblk` | 查看磁盘分配 |
| `df` | 查看文件系统磁盘使用 |

---

### 第三周：Linux 深入 & VIM

#### Linux操作系统
- 1991年9月17日，Linus Torvalds 开发
- 特点：免费、开源、类Unix
- 划分：内核版 / 发行版
- 与Windows区别：收费/安全/稳定/定制化（开源）

#### 文件目录结构
**核心目录**：`/`根、`/bin`系统命令、`/sbin`管理员命令、`/lib`核心库、`/boot`启动文件

**配置/用户**：`/etc`全局配置、`/home`普通用户、`/root`管理员

**设备/临时**：`/dev`硬件设备、`/tmp`临时文件、`/run`运行时文件

**存储/挂载**：`/mnt`手动挂载、`/media`自动挂载、`/opt`第三方软件

**虚拟文件**：`/proc`进程信息、`/sys`内核设备树

**数据/服务**：`/var`可变数据（日志/邮件）、`/srv`服务数据、`/usr`用户级程序

#### 文件颜色标识
- 白色 = 普通文件，蓝色 = 目录，绿色 = 可执行
- 红色 = 压缩文件，浅蓝色 = 链接文件，黄色 = 设备文件

> Linux 严格区分大小写，不依赖扩展名区分文件类型，依赖权限位控制

#### VIM 编辑器
**命令模式（最常用：i a o dd gg G）**：
- `i` 光标前插入 / `I` 行首插入 / `a` 光标后插入 / `A` 行末插入
- `o` 下一行插入 / `O` 上一行插入
- `dd` 删除当前行 / `5dd` 删除5行
- `yy` + `p` 复制粘贴 / `5yy` 复制5行
- `u` 撤销 / `gg` 首行 / `G` 末行 / `^` 行首 / `$` 行末

**编辑模式（最常用：:wq :set nu /字符串）**：
- `:w` 保存 / `:q` 退出 / `:wq` 保存退出 / `:q!` 强制退出
- `:set nu` 显示行号 / `:set nonu` 取消
- `/字符串` 从上至下搜索，`n`向下 / `N`向上
- `:s/one/two/g` 替换

**VIM异常处理**：`vim -r 文件名` 恢复 → `rm -rf .xxx.swp` 删除交换文件

#### 文件查看命令
| 命令 | 特点 |
|------|------|
| `cat` / `tac` | 一次性输出全部（正序/倒序） |
| `more` | 分页，空格翻页，回车逐行，q退出 |
| `less` | 分页，pgUp/pgDn翻页，支持搜索 |
| `head -n` | 查看前n行 |
| `tail -n` | 查看后n行 |

#### 文件查找（find）
`find 路径 [选项]`
- `-name` 按名称（精准/模糊需加引号）
- `-type f/d` 按类型
- `-size +5M/-10M` 按大小
- `-mtime +5/-5` 按时间（天以前/以内）

#### 内容分析（grep）
`grep 关键字 [选项] 文件`
- `-i` 不区分大小写
- `-n` 显示行号
- `-c` 统计次数
- `-v` 反向过滤

#### 管道符 `|`
将前面命令的输出，作为后面命令的输入
```bash
ls /dev/ | grep 'cpu'
yum list | grep 'zip'
```

#### 重定向
- `>` 覆盖式存储
- `>>` 追加式存储

#### 压缩 & 解压
**打包 tar**：
```bash
tar -cvf abc.tar a.txt b.txt c.txt    # 打包
tar -tf abc.tar                        # 查看包内容
tar -uf abc.tar d.txt                  # 追加文件
```

**压缩**：`-z`(gz) / `-j`(bz2) / `-J`(xz)
```bash
tar -zcvf abc.tar.gz a.txt b.txt c.txt
tar -zcvf tmp.tar.gz /tmp/
```

**解压**：
```bash
tar -zxvf abc.tar.gz
tar -zxvf apache-tomcat-9.0.105.tar.gz -C /usr   # 指定目录
```

**zip/unzip**：
```bash
zip abc.zip a.txt b.txt c.txt
zip -r tmp.zip /tmp/
unzip abc.zip -d /tmp
```

---

### 第四周：权限系统 & 软件安装

#### 用户 & 用户组
- `/etc/group` — 用户组信息
- `/etc/passwd` — 用户信息

**用户组管理**：
```bash
groupadd -g GID 组名        # 添加
gpasswd -a 用户名 组名      # 添加用户到组
gpasswd -d 用户名 组名      # 移除
groupdel 组名               # 删除
```

**用户管理**：
```bash
useradd -u -g -G -d -s -c 用户名   # 添加
passwd 用户名                        # 修改密码
usermod -L/-U/-G 用户              # 锁定/解锁/调整组
userdel -r -f 用户名               # 删除（-r删家目录 -f强制）
```

**用户分类**：超级用户(root)、系统用户(/sbin/nologin)、普通用户(/bin/bash)

**账号切换**：`su 用户名` / `whoami` / `logout`

#### 权限系统
**ll 输出解读**：`drwxr-xr-x`
- 第1位：d=目录 / -=文件 / l=链接
- 第2-4位：拥有者权限(user)
- 第5-7位：用户组权限(group)
- 第8-10位：其他人权限(other)

| 权限 | 文件 | 目录 |
|------|------|------|
| r(4) | 查看内容 | 查看目录中文件 |
| w(2) | 修改内容 | 创建/修改/删除 |
| x(1) | 运行 | 进入目录 |

**chmod 方式**：
```bash
chmod -R 700 sc              # 数字法
chmod u=rwx,g=rx,o=---       # 字母法
chmod u-x,g-r                # 加减法
```

**chown**：
```bash
chown userli sc               # 改拥有者
chown userli:develop sc       # 改拥有者+组
```

#### 软件安装
- **RPM**：`.rpm`（RedHat/CentOS）
- **DEB**：`.deb`（Ubuntu/Debian）

**离线安装**：
```bash
mount /dev/sr0 /mnt/iwe3      # 挂载镜像
rpm -ivh 软件包.rpm            # 安装
rpm -qa | grep vim             # 查询
```

**在线安装**：
```bash
yum list | grep 软件名         # 搜索
yum install -y 软件名          # 安装
yum remove -y 软件名           # 卸载
rpm -ql 软件名                 # 查看安装位置
```

#### 服务器管理
**Tomcat**：
```bash
systemctl start/stop/restart tomcat
```

**防火墙**：
```bash
systemctl status/start/stop firewalld
systemctl enable/disable firewalld      # 开/关自启
firewall-cmd --list-all                 # 查看端口
```

**端口查看**：
```bash
netstat -tunlp | grep 端口号
ss -tunlp | grep 8080
```

**进程管理**：
```bash
kill -9/-1 PID     # -9杀死 -1重启
top                 # 查看进程
```

#### Nginx 服务器
**作用**：Web服务器、反向代理、缓存、负载均衡、TCP/UDP代理、邮件代理

**负载均衡算法**：
1. 轮询 — 按顺序逐一分配
2. 随机 — 相对平均概率
3. 最少时间 — 响应快优先
4. 最少连接 — 任务少优先
5. IPHash — 相同IP到同一机器
6. 权重 — 按比例分配

---

## 第二阶段：JAVA 基础

### Java 发展历程
- 计算机语言：二进制 → 汇编 → 高级编程（结构化C → 面向对象C++/Java/Python/C#）
- Java之父：James Gosling（高司令）
- 起源于1991年SUN公司Green小组 → 电视机顶盒项目
- 1995年5月23日正式诞生（约30年）
- Java语言特征：**跨平台、面向对象、简单、高性能、自带GC、支持多线程、异常处理**

### 环境变量
```
JAVA_HOME = Java安装目录
Path      = Java命令所在目录
CLASSPATH = .;
```

### 注释
- `//` 单行（Ctrl+/）
- `/* */` 多行（Ctrl+Shift+/）
- `/** */` 文档注释

### Java三大平台
| 平台 | 用途 |
|------|------|
| JavaSE | 标准版，桌面程序（IDEA、Eclipse） |
| JavaEE | 企业版，互联网/企业级应用 |
| JavaME | 微型版，手机应用（Android）、POS机 |

### 核心概念
- **JVM**：Java虚拟机，对接各大OS平台
- **JRE**：Java运行环境
- **JDK**：Java开发工具

### 包结构规范
```
第1层：项目性质（com商业/org开源/edu教育/gov政府）
第2层：公司或团队（com.alibaba / org.springframework）
第3层：项目名
```

### 变量
- 语法：`数据类型 变量名 = 初始值;`
- 三要素：有初始值、不能重复、有作用范围（局部/全局）

### 数组
- 存储大量相同数据类型的数据结构
- 特点：类型一致、空间连续、长度固定

### 循环语法
```java
// for循环
for(初始条件; 循环判断; 变量改变) { 循环体 }

// 增强for（遍历）
for(数据类型 变量 : 数组) { ... }

// while
while(条件) { 循环体 }

// do-while
do { 循环体 } while(条件);
```

### 分支结构
```java
// if双分支
if(条件) { ... } else { ... }

// if多分支
if(条件1) { ... } else if(条件2) { ... } else { ... }

// switch多分支（适合离散值）
switch(变量) {
    case 值1: ...; break;
    default: ...; break;
}
```

### 运算符
| 类别 | 运算符 |
|------|--------|
| 赋值 | `=` / `+=` / `-=` / `*=` / `/=` / `%=` |
| 四则 | `+` `-` `*` `/` `%` |
| 比较 | `>` `<` `>=` `<=` `==` `!=` |
| 三元 | `(条件) ? 值1 : 值2` |
| 自增 | `++` `--`（独立/非独立使用有区别） |
| 逻辑 | `&&` `||` `!`（短路效果） |
| 按位 | `&` `|` `^`（按二进制位运算） |

### String 字符串
- 底层是字符数组
- 创建：`String s = "值";` 或 `String s = new String();`

### 基本数据类型
| 类型 | 说明 |
|------|------|
| 整数 | byte(1) / short(2) / int(4) / long(8) |
| 浮点 | float(4) / double(8) |
| 字符 | char(2) |
| 布尔 | boolean |

### ASCII编码
- 最早期的编码集，33个不可显示字符 + 95个可显示字符

### Debug调试
| 快捷键 | 功能 |
|--------|------|
| F8 | 逐行执行，不进入方法 |
| F7 | 逐行执行，进入方法 |
| Shift+F8 | 从方法内部退出 |
| F9 | 跳到下一个断点 |

### 函数/方法
```java
访问修饰符 其他修饰符 返回类型 函数名(形参列表) {
    // 函数体
}
```
- 无参/有参 × 无返回/有返回 = 4种组合
- 函数：提供特殊功能的方法，封装内部细节

### 面向对象（OOP）

#### 类与对象
- **类**：对相同/相似东西的抽象
- **对象**：根据类产生的真实存在的东西
- 类是对象的抽象，对象是类的具体实例

#### 属性和行为
- **属性**：对象有什么值（Has）
- **行为**：对象能干什么（Do）

#### static关键字
- `static` 修饰 = 类属性/类行为 → 只跟类有关，跟对象无关
- 工具类的工具方法用static修饰
- 对象行为是动态的，不用static

#### 实例成员 vs 静态成员调用规则
```
1. 静态内部不能直接调用实例成员
2. 实例内部可以直接调用静态成员
3. 静态内部可以直接调用静态成员
4. 实例内部可以直接调用实例成员
```

#### 类的加载
- 每个类只加载1次
- 静态成员在类加载期间准备好，实例成员在运行期间创建

#### 构造器
- 无参构造器：编译器在编译期间自动生成
- 执行步骤：new创建对象 → 属性分配空间 → 赋初始值 → （有参构造器）执行剩余代码
- **加有参之前，必须先加无参构造器**

#### 四大特征封装

**1. 封装**：包装 + 隐藏
- 包装：使用函数将代码包装
- 隐藏：使用访问修饰符控制可见性

| 修饰符 | 本类 | 本包 | 其他包子类 | 其他包 |
|--------|------|------|------------|--------|
| private | ✓ | ✗ | ✗ | ✗ |
| 默认 | ✓ | ✓ | ✗ | ✗ |
| protected | ✓ | ✓ | ✓ | ✗ |
| public | ✓ | ✓ | ✓ | ✓ |

> 推荐：属性用 private，行为用 public

**2. 继承**：子承父业，提升代码复用性
- **重写（Override）**：子类重新编写父类行为

**3. 多态**：相同行为，因对象不同有不同实现
- 条件：①继承 ②方法重写 ③父类引用 → 子类对象（向上转型）
- 应用：多态集合、多态参数

**4. 抽象**

#### this & super
| 用法 | 含义 |
|------|------|
| `this.` | 当前对象的属性/行为 |
| `super.` | 父类的属性/行为 |
| `this()` | 当前类的其他构造器 |
| `super()` | 父类的构造器 |

---

### Java关键字全集

| 类别 | 关键字 |
|------|--------|
| 基本数据类型 | byte, short, int, long, float, double, char, boolean, void |
| 流程控制 | if, else, switch, case, default, for, while, do, break, continue, try, catch, finally, throw, throws |
| 类方法变量 | class, interface, enum, extends, implements |
| 访问控制 | private, protected, public |
| 修饰符 | static, final, abstract, synchronized, volatile, transient, native, strictfp, default |
| 引用 | this, super, new, instanceof |
| 包导入 | package, import |
| 其他 | true, false, null, assert, var(Java10+) |
| 保留字 | goto, const（未使用） |

---

### 数组扩容 & 缩容
- **缩容**：节约内存空间
- **扩容**：扩大内存空间

### 雪花ID（Snowflake ID）
Twitter提出的分布式唯一ID生成算法：
- 最高位符号位 = 0（永远正数）
- 41位时间戳（69年内不重复）
- 10位机器ID（5位机房 + 5位机器）
- 12位序列化（1ms内4096个ID，1秒约409万个）

### 集合框架

#### List vs Set
| 特性 | List | Set |
|------|------|-----|
| 有序性 | 有序 | 可有序可无序 |
| 下标 | 有 | 无 |
| 重复 | 不去重 | 去重 |

#### ArrayList vs LinkedList
| 特性 | ArrayList | LinkedList |
|------|-----------|------------|
| 底层 | 数组 | 双向链表 |
| 查询 | 快（有下标） | 慢 |
| 插入/删除 | 慢（需移位） | 快 |
| 线程安全 | 不安全 | 不安全 |

#### HashSet去重原理（底层 = HashMap的Key）
1. 通过Key的 `hashCode()` 得hash值，按 `(n-1) & hash` 得下标
2. 位置为空 → 直接存储
3. 位置有值 → `equals()` 比较内容
4. 内容相同 → Key不存，Value覆盖
5. 内容不同 → 单向链表追加
6. Java8+：链表转红黑树（平衡树，避免"歪脖子"）

#### 红黑树规则
1. 节点要么红要么黑
2. 根一定是黑
3. 红色节点的2个儿子必须是黑
4. 任意节点到叶子的路径上，黑色节点数相同
5. 叶子一定是黑且值为NULL

#### HashMap
- Java8前：数组 + 单向链表
- Java8后：数组 + 单向链表 + 红黑二叉树
- KEY去重：hashCode() + equals()
- 线程非安全

#### Map体系特点
- 双列集合，Key-Value
- Key不允许重复，Value可重复
- Key-Value 1对1关系

---

### String & StringBuilder & StringBuffer
| 特性 | String | StringBuilder | StringBuffer |
|------|--------|---------------|--------------|
| 可变性 | 不可变 | 可变（数组扩容） | 可变 |
| 线程安全 | — | 不安全 | 安全（synchronized） |
| 性能 | 拼接产生大量对象 | 高 | 较低 |

---

### 7大设计原则
1. **单一原则**：一个类只干一件事
2. **开闭原则**：对扩展开放，对修改关闭
3. **里氏替换原则**：子类能替代父类，尽量不要重写父类已实现的方法
4. **迪米特法则**：最少知道原则
5. **依赖倒置原则**：面向接口编程
6. **接口隔离原则**：接口最小化
7. **组合聚合原则**：多用组合少用继承

---

### 泛型
```java
// 泛型接口
public interface MyList<E> { void add(E e); void remove(E e); }

// 泛型类
public class MyArrayList<E> implements MyList<E> { ... }

// 泛型方法
public static <T> String sum(T a, T b) { return "" + a + b; }
```

---

### IO流
**流分类**：
1. 按方向：输入流 / 输出流
2. 按大小：字节流 / 字符流
3. 按功能：节点流 / 功能流

| 流类型 | 适用场景 |
|--------|----------|
| 字符流（Reader/Writer） | 只能读取文本文件 |
| 字节流（InputStream/OutputStream） | 所有二进制文件（图片/音频/视频/PDF） |
| 缓冲流（Buffered） | 给节点流增加缓冲功能 |
| PrintStream | 打印输出流，输出到控制台 |

数据存储演进：变量 → 数组 → 集合（缺乏持久性） → 文件 → MYSQL数据库

---

### JDBC
- Java Database Connectivity
- SUN公司定义规范，各大数据库厂商自行实现
- 是 Hibernate / MyBatis / MyBatis-Plus / Spring-Data-Jpa 的底层

#### SQL注入攻击
```java
// 危险写法
String sql = "select * from books where author = " + author + " and category = " + category;
// category = '小说' or 1=1  → 绕过条件
```

---

### 反射
- 在运行时动态获取类的成员信息、调用成员
- **所有框架的底层**：按需加载 → 运行时通过反射动态加载类
- 作用：探查类信息、动态创建对象、探查/调用属性和行为

**获取Class对象的3种方式**：
```java
// 1. 全限定名（框架常用）
Class<?> cls = Class.forName("com.iwe3.day13.entity.CustomerEntity");
// 2. 对象.getClass()
obj.getClass();
// 3. .class属性
CustomerEntity.class;
```

---

### Lombok
```java
@Getter / @Setter    // 生成getter/setter
@NoArgsConstructor   // 无参构造器
@AllArgsConstructor  // 全参构造器
@RequiredArgsConstructor  // 有参构造器（配合@NonNull）
```

---

### Java多线程

#### 基础概念
- **进程**：运行状态下的应用程序，资源分配最小单位
- **线程**：进程内部执行任务的最小单位
- **CPU内核**：每个内核可同时处理线程（物理核/逻辑核）

#### 并发 vs 并行
- **并行**：多个任务同时执行（多核CPU）
- **并发**：多个任务交替执行（单核面临多任务）
- **高并发**：单核/单台服务器同时面临海量任务

#### start() vs run()
- `run()`：定义任务，CPU来调用
- `start()`：启动任务，程序员来调用

#### 线程创建方式
- 继承 Thread
- 实现 Runnable
- 实现 Callable（有返回值）
- 线程池

#### 线程生命周期
创建 → 就绪 → 运行 → 阻塞 → 运行 → 死亡

**CPU调度算法**：
- 分时调度：按时间片随机分配
- 优先级算法：优先级越高（1-10，默认5），分配到时间片的概率越高

#### 线程方法
| 方法 | 作用 |
|------|------|
| `sleep()` | 休眠，运行→阻塞，自动苏醒，不释放锁 |
| `wait()` | 等待，需要notify唤醒，主动释放锁 |
| `join()` | 子线程执行时，主线程阻塞等待 |
| `yield()` | 线程让步，暗示CPU还有同优先级线程 |

#### 线程安全
- **synchronized**：悲观锁，同一时刻只运行1根线程处理数据
- **Lock**：可公平/不公平，可重入，不可剥夺，互斥

#### 锁升级过程（不可降级）
无状态 → 偏向锁 → 轻量级锁 → 重量级锁

#### CAS & ABA问题
- **CAS**（Compare-And-Swap）：乐观锁
- **ABA问题**：变量从X→Y→X，另一个线程误以为没变

#### 单例模式
**饿汉模式**（天生线程安全）：
```java
private static JdbcTemplate instance = new JdbcTemplate();
private JdbcTemplate(){}
public static JdbcTemplate getInstance() { return instance; }
```

**懒汉模式**（双重检查锁）：
```java
private static JdbcTemplate instance = null;
public static JdbcTemplate getInstance() {
    if(instance == null) {
        synchronized (JdbcTemplate.class) {
            if(instance == null) {
                instance = new JdbcTemplate();
            }
        }
    }
    return instance;
}
```

#### 死锁条件
1. 使用互斥锁（synchronized）
2. 其他线程不可剥夺
3. 无限期等待
4. 一直保持死锁状态

---

### Java 8 日期 API
- **之前**：Date（方法过时、不支持国际化）、Calendar
- **之后**：LocalDate / LocalTime / LocalDateTime

---

### 匿名内部类 & Lambda & Stream

#### 匿名内部类
- 没有名字的内部类，适合抽象类/接口在项目中只使用一次的场景

#### Lambda表达式
- 替代接口的匿名内部类
- 语法：`(参数列表) -> { 业务代码 }`

#### Stream流
- 处理数据的流水线（与IO流概念不同）
- 作用：过滤、转换、统计、排序、采集……

**两种流**：
- `list.stream()` — 串行流（按顺序执行）
- `list.parallelStream()` — 并行流（数据分块同时执行）

---

### 常见注解
| 注解 | 含义 |
|------|------|
| `@Deprecated` | 废弃代码 |
| `@Override` | 重写方法 |
| `@SuppressWarnings("all")` | 忽略警告 |
| `@FunctionalInterface` | 函数式接口 |

---

### Java关键字（保留字）
- `goto` 和 `const` 是保留字，但未使用
- 所有关键字都是小写

---

### 异常
| 类型 | 继承 | 检查时机 |
|------|------|----------|
| 受检异常 | Exception | 编译时提醒 |
| 非受检异常 | RuntimeException | 运行时才可能抛出 |

- `throw`：抛出异常
- `throws`：声明方法可能抛出异常

---

### 接口演变
- Java 8前：接口只能有抽象方法 & 常量
- Java 8后：接口可以有 `default` 和 `static` 方法（不建议放真实业务逻辑）

---

### 抽象类 vs 接口
- 抽象类：可以有抽象方法 + 具体方法
- 接口（Java 8后）：default + static 方法

---

### equals() & hashCode() 联合判重原理
1. 先判 `hashCode()`（性能好，做快筛）
2. hashCode不同 → 直接判定两成员不同
3. hashCode相同 → `equals()` 做最终判断（性能不好但准确）

---

### 比较器原理
- 比较器内封装了基础排序算法（两两比对）
- 重写的方法会被触发多次
- 好处：节省写基础排序算法的精力，专注于多字段排序业务

---

## 第三阶段：MYSQL 数据库

### 数据库基本概念
- 数据库管理软件：MYSQL、SqlServer、Oracle、DB2、AliSQL、高斯……
- **关系型数据库**：关注数据&数据之间的关系，使用SQL
- **非关系型数据库（NoSQL）**：关注快速存取，无SQL

### 关系型数据库存储结构
1:1 / 1:N / N:N

### MYSQL
- 瑞典 MySQL AB 公司开发，免费开源
- SQL = Structured Query Language 结构化查询语言

### Docker 安装 MYSQL
```bash
docker pull mysql
docker run --name mysql01 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=150316 -d 镜像ID
```

---

### DDL（数据定义语言）— 建库建表

#### 数据库操作
```sql
CREATE DATABASE [IF NOT EXISTS] 数据库名 CHARACTER SET utf8mb4;
ALTER DATABASE 数据库名 CHARACTER SET 新字符集;
DROP DATABASE 数据库名;
```

#### 表操作
```sql
-- 建表
CREATE TABLE 表名(
    字段 数据类型 [约束],
    ...
);
-- 修改表
ALTER TABLE 表名 ADD 新字段 数据类型 [约束];
ALTER TABLE 表名 MODIFY 字段 新数据类型 [约束];
ALTER TABLE 表名 CHANGE 旧字段 新字段 数据类型 [约束];
ALTER TABLE 表名 DROP COLUMN 字段名;
-- 删表
DROP TABLE 表名;
```

---

### DML（数据操作语言）— 增删改

```sql
-- 新增
INSERT INTO 表名(字段列表) VALUES (值列表),(值列表)……;
-- 修改
UPDATE 表名 SET 字段=值,…… [WHERE 筛选条件];
-- 删除
DELETE FROM 表名 [WHERE 筛选条件];     -- 逐行删，可回滚
TRUNCATE TABLE 表名;                   -- 清空表，重置自增，不可回滚
```

---

### 约束类型

| 约束 | 关键字 | 说明 |
|------|--------|------|
| 主键 | PRIMARY KEY | 非空且唯一，不具备业务含义 |
| 自增 | AUTO_INCREMENT | 自动递增 |
| 唯一 | UNIQUE | 值不能重复 |
| 检查 | CHECK (条件) | 限制取值范围 |
| 非空 | NOT NULL | 必须有值 |
| 默认 | DEFAULT 值 | 未输入时使用默认值 |
| 外键 | FOREIGN KEY | 建立表关系 |

> 优秀设计者：使用外键，但不使用外键约束（由程序保证关系正确性）

---

### DQL（数据查询语言）— 查询

#### 执行顺序
```
FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT
```

#### 基本查询
```sql
SELECT [DISTINCT] 字段列表
FROM 表名
[WHERE 筛选条件]
[GROUP BY 分组]
[HAVING 聚合过滤]
[ORDER BY ASC/DESC]
[LIMIT 分页];
```

#### 条件查询（WHERE）
- `=` / `>` / `<` / `>=` / `<=` / `!=` / `<>`
- `AND` / `OR`
- `LIKE`（%匹配0-N，_匹配单个）
- `BETWEEN ... AND ...`
- `IS NULL` / `IS NOT NULL`

#### 聚合函数
| 函数 | 作用 |
|------|------|
| `COUNT()` | 统计条数 |
| `SUM()` | 求和 |
| `AVG()` | 平均数 |
| `MAX()` / `MIN()` | 最大/最小值 |

#### 字符串函数
`UPPER()` / `LOWER()` / `CONCAT()` / `TRIM()` / `SUBSTR()` / `LENGTH()` / `REPLACE()`

#### 日期函数
`YEAR()` / `MONTH()` / `DAY()` / `NOW()` / `CURDATE()`

#### 数学函数
`RAND()` / `ROUND()` / `PI()` / `MOD()`

#### 连接方式
- **内联查**（INNER JOIN）
- **左外联查**（LEFT JOIN）
- **右外联查**（RIGHT JOIN）

---

### 子查询
- 位置：SELECT后 / FROM后 / WHERE后
- **几乎所有子查询都可用 LEFT/INNER JOIN 替代**，但WHERE后的聚合函数子查询只能用子查询

#### 分类
| 类型 | 特点 |
|------|------|
| 相关子查询 | 需要外部SQL提供数据 |
| 非相关子查询 | 可独立执行 |

#### IN vs EXISTS
- `IN`：内表数据量少时用，做笛卡尔乘积+等值判断
- `EXISTS`：外表数据量少时用，逐行代入内表匹配
- 内外数据量相差不大，都可以使用

#### UNION vs UNION ALL
- `UNION`：去重
- `UNION ALL`：不去重，效率高

---

### 数据库设计范式
| 范式 | 要求 |
|------|------|
| 第1范式 | 原子性，列拆分到不可再拆 |
| 第2范式 | 相关性，所有列必须跟主键有关系 |
| 第3范式 | 直接相关性，所有列必须跟主键有直接关系 |

> 反范式：适当冗余以提升查询性能

---

### 视图
- 虚拟表，数据由底层物理表提供
- 只适合查询，不能增删改
- 作用：降低多表操作复杂性
```sql
CREATE VIEW 视图名 AS SELECT 查询语句;
```

---

### 索引
- MySQL 5.5+：只有主键ID作为聚簇索引
- **聚簇索引**：索引和数据绑定在一起
- **非聚簇索引**：索引和数据不绑定

#### 索引类型
| 类型 | 特点 |
|------|------|
| 主键索引 | 建表时 PRIMARY KEY 自动创建 |
| 唯一索引 | 列中不能有重复值 |
| 普通索引 | 列中允许重复值 |
| 组合索引 | 多字段联合，遵从最左前缀原则 |
| 外键索引 | 添加外键约束时自带 |

#### 合成索引 — 最左前缀原则
```sql
CREATE INDEX idx_name ON 表(字段1,字段2,字段3);
-- 查询必须从最左开始匹配
```

#### 索引覆盖 & 回表
- **回表**：从索引表根据数据指针回到原始表查找
- **索引覆盖**：查询内容完全被索引覆盖（底层原理：索引下推）
- 建议：不要用 `SELECT *`，应按需查找

#### 不适合加索引的场景
1. 数据量不超过几十万
2. 经常变化的字段
3. 大量重复值的字段（如性别）
4. 很少使用的列

#### 索引失效
1. `SELECT *`
2. 查询携带四则运算
3. LIKE 模糊查询，`%` 在前
4. 使用 `OR` 关键字
5. `<>` 可能失效
6. 组合索引未遵最左前缀
7. 数据库认为不需要索引

#### 执行计划
```sql
EXPLAIN SELECT ...   -- 查看索引是否生效
```

---

### 存储过程 & 函数 & 触发器

#### 存储过程
- 特殊函数，可修改数据库
- 优点：减少网络开销、执行效率高
- 缺陷：DB职责是存储不是计算、语法不通用、易受攻击

#### 触发器
```sql
CREATE TRIGGER trigger_name
{BEFORE | AFTER} {INSERT | UPDATE | DELETE} ON table_name
FOR EACH ROW
BEGIN
    -- 触发器逻辑
END;
```
- 场景：维护历史数据、统计分析

---

## 第四阶段：WEB 开发

### HTML
- HyperText Markup Language（超文本标记语言）
- HTML = 网页的身体，CSS = 装饰外观，JS = 控制行为

**标签分类**：
- 双标签：`<div></div>`
- 单标签：`<hr>`
- 块级标签：独占一行
- 行级标签：共享一行

**CSS 引入方式**：
| 方式 | 位置 | 优先级 |
|------|------|--------|
| 行内样式 | 标签style属性 | 最高 |
| 内联样式 | `<style>`标签 | 中 |
| 外联样式 | link引入CSS文件 | 低 |

> 推荐外联样式，实现HTML & CSS分离

**表单标签**：接收用户输入，替代Console控制台

**浮动**：让块级标签共享一行

**列表**：有序列表、无序列表、自定义列表

---

### JavaScript（JS）
- Java 和 JavaScript：雷锋和雷峰塔的关系
- Java：半编译半解释 → JS：浏览器解释执行

#### var vs let
| 特性 | var | let |
|------|-----|-----|
| 作用域 | 函数/全局 | 块级 `{}` |
| 变量提升 | 提升+初始化undefined | 提升但访问报错 |
| 重复声明 | 允许 | 不允许 |

#### JS作用
1. 修改网页内容
2. 修改网页样式
3. 通过事件触发函数

---

### Vue 基础
- `v-bind`：给标签属性赋值
- `computed`：计算属性
- **生命周期钩子**：beforeCreate → created → beforeMount → mounted → beforeUpdate → updated → beforeDestroy → destroyed

---

### Tomcat 服务器
- SUN公司 & Apache平台共同开发的Web服务器
- 作用：接收用户请求、运行Java后台程序
- 被称为：Web容器 / Servlet容器

**发布项目**：在web目录中创建 `index.html`

---

### Web系统架构

#### B/S vs C/S
| 架构 | 模式 | 客户端依赖 | 举例 |
|------|------|------------|------|
| B/S | 浏览器/服务器 | 浏览器 | 淘宝、京东 |
| C/S | 客户端/服务器 | 自行安装/更新 | CS2、原神 |

#### Web技术栈
1. 前端技术：HTML/CSS/JS/小程序/Android/iOS/鸿蒙
2. 前端服务器：Nginx
3. 后端技术：Spring系列框架、中间件、缓存技术
4. 数据库：MYSQL/Oracle + Redis/HBase
5. 通讯协议：HTTP

---

### Servlet

#### 概念
- 可以帮你处理前端请求的Java类
- 继承 `HttpServlet`

#### 生命周期
编译 → 加载 → 实例化 → 初始化 → 服务化 → 卸载
> 编译/加载/实例化/初始化 只执行1次；服务执行多次
> **多线程 & 单实例**：不存储数据，无线程安全问题

#### 三大组件
| 组件 | 作用 |
|------|------|
| Servlet | 接收HTTP请求 |
| Filter | 过滤HTTP请求 |
| Listener | 监听三大作用域变化 |

#### 三大作用域
| 作用域 | 生命周期 | 说明 |
|--------|----------|------|
| request | 单次HTTP请求 | 非常短 |
| session | 单次HttpSession会话 | 相对长，默认30分钟 |
| application(ServletContext) | 跟Tomcat同步 | 最长 |

#### HTTP提交方式
| 方法 | 场景 | 特点 |
|------|------|------|
| GET | 查询 | 参数在URL，有长度限制，可缓存 |
| POST | 新增 | 参数在请求体，可传二进制 |
| PUT | 修改 | — |
| DELETE | 删除 | — |

#### GET vs POST 区别
1. GET在URL传输 / POST在请求体
2. GET只能文本 / POST可传输二进制
3. GET受URL长度限制 / POST理论上无限
4. GET会被缓存 / POST不会 → POST更安全

#### 请求转发 vs 重定向
- **请求转发**：浏览器1次请求，服务器内部转发
- **重定向**：浏览器2次请求，浏览器实现

---

### JSP（Java Server Page）
- 后端页面技术，组成 = HTML + Java代码
- 九大内置对象
- 本质是Servlet

#### EL表达式
```
${属性}  // 获取顺序：pageContext → request → session → ServletContext
```

#### JSP 指令
- `include`：导入公共页面片段
- `taglib`：引入标签库（如 JSTL）

---

### MVC 架构模式

| 层 | 角色 | 职责 |
|-----|------|------|
| Model（模型） | JavaBean | 存储数据、处理业务、数据库交互 |
| View（视图） | JSP/HTML | 展示页面 |
| Controller（控制器） | Servlet | 接收请求、调用Model |

#### 对象分层
| 对象 | 全称 | 作用 |
|------|------|------|
| VO | View Object | 接收用户输入 |
| DTO | Data Transfer Object | 各层之间传输数据 |
| DAO | Data Access Object | 数据库交互 |
| BO | Business Object | 业务实现（*ServiceImpl） |
| PO | Persistent Object | 持久化（*Entity） |

---

### Cookie + Session
- **Cookie**：浏览器特有的文本存储技术
- **Session**：Tomcat中每个用户有独立Session对象
- Session默认30分钟有效期，收到请求自动重置

#### URI vs URL
- **URI**：统一资源标识符
- **URL**：统一资源定位符

---

### 过滤器（Filter）
**使用场景**：
1. 字符编码统一处理
2. 用户权限控制
3. 日志记录与请求监控
4. 敏感词过滤/数据预处理
5. 跨域请求处理

---

### ORM（Object Relational Mapping）
| 映射关系 |
|----------|
| 类 → 表 |
| 对象 → 记录 |
| 属性 → 字段 |

| 类型 | 框架 | 特点 |
|------|------|------|
| 全自动 | Hibernate、JPA、MyBatis-Plus | 不需要写SQL |
| 半自动 | MyBatis | 需要写SQL，学习难度最低 |

---

### Maven
- 项目管理和构建自动化工具
- **作用**：项目构建自动化、依赖管理、结构标准化、多模块支持、仓库管理

**生命周期**：clean → compile → test → package → install → deploy

---

### Spring 框架

#### Spring 核心
- 底层：**工厂模式 + 反射 + HashMap单例模式**
- 一站式服务框架，程序员的春天

#### IOC（控制反转）
- 创建对象的权利由主动变成被动接受
- Spring容器统一管理组件

#### Bean作用域
| 作用域 | 说明 |
|--------|------|
| singleton | 默认，每个组件唯一实例 |
| prototype | 原型，每次产生新实例 |
| request | 同一请求范围内唯一实例 |
| session | 同一会话范围内唯一实例 |
| application | 同一Tomcat范围内唯一实例 |

#### DI（依赖注入）
- IOC的实现方式
- 通过setter或构造参数维护组件关系

#### 自动装配
| 方式 | 规则 |
|------|------|
| byName | 按属性名匹配 |
| byType | 按属性类型匹配（多个报错，没有NPE） |
| constructor | 构造器装配 |

#### @Autowired vs @Resource
| 注解 | 装配顺序 | 来源 |
|------|----------|------|
| @Autowired | 先byType后byName | Spring |
| @Resource | 先byName后byType | Java自带 |

#### AOP（面向切面编程）
- 代理模式实现（JDK动态代理 / CGLIB代理）

**AOP术语**：
| 术语 | 说明 |
|------|------|
| 切面 | 放置非核心业务的地方 |
| 切入点 | 切面可切入的地方 |
| 连接点 | 切面正式和切入点连接的点 |
| 通知/增强 | 方法调用前/后增加功能 |
| 目标对象 | 真实对象，总被代理 |
| 织入 | 将切面应用到切入点的过程 |

**通知类型**：@Before / @After / @Around……

---

### Spring MVC

#### 工作流程
```
前端HTTP请求
  → 前端控制器(DispatcherServlet)
    → 处理映射器（有没有方法可处理？）
      → 处理适配器（适配参数）
        → Controller（处理器）
          → 返回 ModelAndView
            → 视图解析器（逻辑视图→真实视图）
              → 填充Model数据
                → 返回浏览器
```

#### 组件
| 组件 | 作用 |
|------|------|
| 前端控制器 | 调度HTTP请求 |
| 处理映射器 | 管理URL-类&方法关系 |
| 处理适配器 | 处理参数和返回值 |
| 视图解析器 | 逻辑视图名→真实视图资源 |

---

### Spring Boot
- **脚手架**：方便快速构建项目
- 核心：**约定优于配置**

#### 特点
1. 创建独立Spring应用
2. 内嵌Tomcat/Jetty/Undertow
3. 提供Starter简化依赖
4. 自动配置Spring和第三方库
5. 生产就绪（指标/健康检查/外部化配置）
6. 不需要XML配置

#### Starter 作用
1. 管理Maven依赖关系
2. 完成自动配置
3. 遵循约定优于配置

> Spring Boot不支持JSP，推荐 Thymeleaf 模板引擎

---

### SSM 组合
十年前的主流组合：
- **表现层**：SpringMVC（后来替代了struts2）
- **业务层**：Spring
- **持久层**：MyBatis

> struts2因严重BUG被淘汰

---

### 其他重要知识点

#### 对象存储服务（OSS）
- 存储非结构化数据（图片/视频/音频/文档）
- 平台：阿里云/腾讯云/华为云/七牛云/MinIO
- 结构化数据 → MySQL，非结构化 → OSS
- 底层：IO流

#### 文件上传
- 前端必须POST提交
- Content-Type = `multipart/form-data`

#### Excel解析
- 工具：POI、EasyExcel、Hutool

#### 全局异常处理
- 三层架构：底层不处理，层层向上抛
- 编译时异常：抓/抛
- 运行时异常：抛了再修改

#### 热部署
- 修改代码后不重启Tomcat

#### 枚举
- 将静态常量对象化
- 将有穷集合的数据逐一罗列

#### Properties 集合
- Map体系子集，K-V都是字符串
- 常用于数据库配置文件 `db.properties`

#### MVC → MVVM
- **MVC**：Model-View-Controller（后端架构）
- **MVVM**：Model-View-ViewModel（前端框架架构，如Vue）

---

> 整理时间：2026-05-29
