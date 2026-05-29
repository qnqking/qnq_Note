# Java全站课程笔记

> 来源：ProcessOn 思维导图，按课程阶段整理

---

## 第一阶段：OS（操作系统基础）

### 第一周：计算机基础

**计算机组成**
- 硬件：键盘、鼠标、主板、CPU（中央处理器）、硬盘、内存条、显示器、散热器、电源、打印机……
- 软件：操作系统（OS, Operating System）+ 应用软件
- 操作系统：Windows、Linux、Unix、Mac、iOS、Android、鸿蒙
- 应用软件：具有特定功能、服务使用者的程序

**客户端工具**：Xshell、FinalShell、WindTerm、MobaXterm

---

### 第二周：计算机网络 & 虚拟平台

#### OS发展史
1. 20世纪40年代：第1台计算机出现，无OS，程序员直接跟硬件交互
2. 20世纪50年代：出现"单通道批处理操作系统"
3. 20世纪60年代：出现"多通道批处理操作系统"
4. 20世纪70年代：出现"多核多通道批处理操作系统"（Windows/Unix/DOS/Mac/Linux）

#### 互联网概念
- **internet（互联网）**：描述计算机之间如何进行互联
- **Internet（因特网）**：描述全球电脑应该如何通讯
- **互联网协议**：定义计算机如何接入互联网 + 定义通讯协议

#### OSI 7层架构（从下向上）
| 层级 | 说明 |
|------|------|
| 物理层（Physical） | 网线/WiFi/路由器/交换机，传输电信号（高电压=1，低电压=0） |
| 数据链路层（Data Link） | 遵从"以太网（Ethernet）通讯协议"，数据帧（Frame）= head（MAC地址） + data |
| 网络层（Network） | 划分子网，IP协议（Internet Protocol），IP地址32位（前24位子网，后8位主机） |
| 传输层（Transport） | 端口到端口的通讯（TCP 传输控制协议 / UDP 用户数据报协议） |
| 会话层（Session） | — |
| 表示层（Presentation） | — |
| 应用层（Application） | HTTP（超文本传输协议）/ HTTPS / FTP（文件传输协议） / AMQP / POP3…… |

> 下3层跟硬件有关，上3层跟软件有关，中间跟传输有关

#### ARP协议（Address Resolution Protocol，地址解析协议）
- 根据IP地址（逻辑地址）获取目标设备的MAC地址（物理地址）
- MAC地址：48位，前24位=厂家，后24位=网卡序号

#### IP地址 & 子网掩码
- IP地址与子网掩码按位与（AND）运算，结果相同 = 同一子网
- 同为1才为1，其他为0

#### TCP协议（Transmission Control Protocol，传输控制协议）
- 特点：安全、可靠、高效，依赖连接，1问1答模式（类似于打电话）
- **三次握手（Three-way Handshake）**：客户端请求 → 服务器响应 → 客户端确认
- **四次挥手（Four-way Wavehand）**：客户端请求断开 → 服务器收到 → 服务器清除消息 → 正式断开

#### UDP协议（User Datagram Protocol，用户数据报协议）
- 类似于寄包裹/发短信，数据+地址捆绑传输
- 不保证消息一定被对方接收，可靠性相对较差

#### TCP/IP 5层架构
物理层 → 数据链路层 → 网络层 → 传输层 → 应用层

#### 网络常用命令
| 命令 | 作用 |
|------|------|
| `ipconfig -all` | 查看IP地址 |
| `ping` | 测试网络是否通畅 |
| `netstat` | 查看端口使用情况 |
| `Ctrl+C` | 打断命令执行（常用在 ping / top 命令中） |

---

### Linux 基础命令

#### 文件目录操作
| 命令 | 作用 |
|------|------|
| `cd .` | 进入当前目录 |
| `cd ..` | 进入上级目录 |
| `cd ~` | 回到家目录 |
| `pwd`（print working directory） | 查看当前工作目录 |
| `clear` / `Ctrl+L` | 清屏 |
| `ls -l -h -a` | 查看目录文件（长格式/人性化/含隐藏文件） |
| `history` | 查看历史记录 |
| `type` | 查看命令的类型 |

#### 文件操作
| 命令 | 作用 |
|------|------|
| `mkdir -p`（make directory） | 创建目录（递归） |
| `rm -r -f`（remove） | 删除目录/文件（递归/强制） |
| `cp -r`（copy） | 复制目录或文件 |
| `mv`（move） | 移动/重命名 |
| `touch` | 创建空文件 |
| `touch -d '时间' 文件名` | 创建文件并指定时间戳 |
| `wc -l -w -c`（word count） | 统计行数/单词数/字节数 |

#### 关机重启
| 命令 | 作用 |
|------|------|
| `shutdown -h 5` | 5分钟后关机（`-h` halt / `-r` reboot / `-c` cancel） |
| `poweroff` | 立即关机 |
| `reboot` | 立即重启 |

#### 磁盘相关
| 命令 | 作用 |
|------|------|
| `du -s -h`（disk usage） | 查看目录使用情况 |
| `lsblk`（list block） | 查看磁盘分配情况 |
| `df`（disk free） | 查看文件系统磁盘使用 |
| `help` | 查看本地帮助文档 |

#### dd 命令（创建指定大小文件）
```bash
# 参数：if=输入设备 of=输出位置 bs=每次大小 count=次数
dd if=/dev/zero of=a.txt bs=1M count=5    # 创建5M的文件
```

---

### 第三周：Linux 深入 & VIM

#### Linux 操作系统
- 1991年9月17日，芬兰大学生 Linus Torvalds（林纳斯·托瓦兹）开发
- 特点：免费、开源、类Unix操作系统
- 划分：内核版 / 发行版
- 与Windows区别：①收费与否 ②安全性 ③稳定性 ④是否可定制化（开源）

#### 文件目录结构
**核心目录**：`/`（根目录）、`/bin`（系统必备命令）、`/sbin`（系统管理命令）、`/lib`（核心库文件）、`/boot`（启动文件）

**配置/用户**：`/etc`（全局配置）、`/home`（普通用户主目录）、`/root`（管理员主目录）

**设备/临时**：`/dev`（硬件设备文件）、`/tmp`（临时文件）、`/run`（运行时临时文件）

**存储/挂载**：`/mnt`（手动挂载）、`/media`（自动挂载）、`/opt`（第三方软件）

**虚拟文件**：`/proc`（进程实时信息，内存动态生成）、`/sys`（内核设备树）

**数据/服务**：`/var`（可变数据：日志/邮件/数据库）、`/srv`（服务数据）、`/usr`（用户级应用程序）

**特殊目录**：`/lost+found`（文件系统修复后恢复的文件）

#### 文件颜色标识
- 白色 = 普通文件，蓝色 = 目录，绿色 = 可执行文件
- 红色 = 压缩文件，浅蓝色 = 链接文件，黄色 = 设备文件，灰色 = 其它文件
- 红色闪烁 = 链接文件出问题

> Linux 严格区分大小写；不依赖扩展名区分文件类型，依赖权限位控制

#### VIM 编辑器
**命令模式（最常用：i a o dd gg G）**：
- `i` 光标前插入 / `I` 行首插入 / `a` 光标后插入 / `A` 行末插入
- `o` 下一行插入 / `O` 上一行插入
- `dd` 删除当前行 / `5dd` 删除5行
- `yy` + `p` 复制粘贴 / `5yy` 复制5行
- `u` 撤销 / `gg` 首行 / `G` 最后一行 / `^` 行首 / `$` 行末
- `5G` 跳转到第5行

**编辑模式（最常用：:wq / :set nu / /字符串）**：
- `:w` 保存 / `:q` 退出 / `:wq` 保存并退出 / `:q!` 强制退出
- `:set nu` 显示行号 / `:set nonu` 取消行号 / `:n` 跳转到第n行
- `/字符串` 从上至下搜索，`n` 向下 / `N` 向上
- `?字符串` 从下至上搜索
- `:s/one/two/g` 当前行全部替换

**VIM异常处理**：`vim -r 文件名`（恢复） → `rm -rf .xxx.swp`（删除交换文件）

#### 文件查看命令
| 命令 | 特点 |
|------|------|
| `cat`（正序）/ `tac`（倒序） | 一次性输出全部内容 |
| `more` | 分页查看，空格翻页，回车逐行，q退出 |
| `more -10 文件` | 限制每页显示10行 |
| `more +5 -10 文件` | 从第5行开始，每页10行 |
| `less` | 分页查看，pgUp/pgDn翻页，支持搜索（`less -N` 显示行号） |
| `head -n` | 查看文件前n行（默认10行） |
| `tail -n` | 查看文件后n行（默认10行） |

#### 文件查找（find）
`find 查找范围 [选项]`
- `-name` 按文件名称（精准/模糊需加单引号或双引号）
- `-type f/d` 按文件类型（f=普通文件，d=目录）
- `-size +5M/-10M` 按文件大小
- `-mtime +5/-5` 按操作时间（+5天以前，-5天以内）

#### 内容分析（grep）
`grep 关键字 [选项] 文件`
- `-i` 不区分大小写
- `-n` 显示行号
- `-c` 统计出现次数
- `-v` 反向过滤

#### 管道符 `|`
将前面命令的输出，作为后面命令的输入：
```bash
ls /dev/ | grep 'cpu'
yum list | grep 'zip'
```

#### 重定向
- `>` 覆盖式存储
- `>>` 追加式存储

#### 压缩 & 解压
**打包（tar）**：
```bash
tar -cvf abc.tar a.txt b.txt c.txt    # 打包（-c创建 -v显示过程 -f指定包名）
tar -tf abc.tar                        # 查看包内容（-t查看）
tar -uf abc.tar d.txt                  # 追加文件到包中（-u追加）
```

**压缩**：`-z`（gz格式）/ `-j`（bz2格式）/ `-J`（xz格式）
```bash
tar -zcvf abc.tar.gz a.txt b.txt c.txt
tar -zcvf tmp.tar.gz /tmp/             # 压缩目录
```

**解压**：
```bash
tar -zxvf abc.tar.gz                   # 解压到当前目录
tar -zxvf apache-tomcat-9.0.105.tar.gz -C /usr   # 解压到指定目录（-C）
```

**zip / unzip**：
```bash
zip abc.zip a.txt b.txt c.txt
zip -r tmp.zip /tmp/                   # 压缩目录（-r递归）
unzip abc.zip -d /tmp                  # 解压到指定目录（-d）
```

---

### 第四周：权限系统 & 软件安装

#### 用户 & 用户组
- `/etc/group` — 用户组信息
- `/etc/passwd` — 用户信息

**用户组管理**：
```bash
groupadd -g GID 组名        # 添加用户组（-g指定组ID，不设则从1000递增）
gpasswd -a 用户名 组名      # 添加用户到组（-a添加 -d移除 -A管理员 -M批量添加）
groupdel 组名               # 删除用户组
```

**用户管理**：
```bash
useradd -u -g -G -d -s -c 用户名   # 添加用户（-u用户ID -g主组 -G附加组 -d家目录 -s解释器 -c备注）
passwd 用户名                        # 修改密码
usermod -L/-U/-G 用户               # 锁定/解锁/调整附加组
userdel -r -f 用户名                # 删除用户（-r删家目录 -f强制删除）
```

**用户分类**：超级用户 root（最高权限）、系统用户（`/sbin/nologin`，管理内置应用）、普通用户（`/bin/bash`）

**账号切换与查看**：
```bash
su 用户名              # 切换账号
whoami                 # 查看当前用户
users / who            # 查看在线用户
last                   # 查看历史登录记录
logout                 # 退出客户端
id 用户名              # 查看某个账号的详细信息
```

**passwd 文件格式解读**：
```
root:x:0:0:root:/root:/bin/bash
用户名:密码占位符:用户ID:用户组ID:描述信息:家目录:解释器
```
- 超级用户/普通用户解释器：`/bin/bash`
- 系统用户解释器：`/sbin/nologin`

#### 权限系统
**ll 输出解读**：`drwxr-xr-x`（共10位）
- 第1位：d=目录 / -=普通文件 / l=符号链接 / b=块设备 / c=字符设备
- 第2-4位：拥有者权限 u（user）
- 第5-7位：用户组权限 g（group）
- 第8-10位：其他人权限 o（other）

| 权限 | 数字 | 对文件 | 对目录 |
|------|------|--------|--------|
| r（读 read） | 4 | 查看文件内容 | 查看目录中文件列表 |
| w（写 write） | 2 | 修改文件内容 | 创建/修改/删除目录中文件 |
| x（执行 execute） | 1 | 运行文件 | 进入目录 |
| -（无权限） | 0 | 无权限 | 无权限 |

**chmod 三种方式**：
```bash
chmod -R 700 sc              # 数字法（-R递归，针对目录）
chmod u=rwx,g=rx,o=---       # 字母法
chmod u-x,g-r                # 加减法
# rwx=7, r-x=5, r--=4
```

**chown（change owner）**：
```bash
chown userli sc               # 改变拥有者
chown userli:develop sc       # 同时改变拥有者和所属组
```

#### 软件安装
- **RPM**：`.rpm` 后缀，适合 RedHat / CentOS
- **DEB**：`.deb` 后缀，适合 Ubuntu / Debian

**离线安装**：
```bash
mount /dev/sr0 /mnt/iwe3      # 挂载镜像文件到/mnt/iwe3
rpm -ivh 软件包.rpm            # 安装（-i install -v 进度条 -h 进度条显示为###）
rpm -qa | grep vim             # 查询是否已安装
```

**在线安装**：
```bash
yum list | grep 软件名         # 搜索软件
yum install -y 软件名          # 安装（-y自动确认）
yum remove -y 软件名           # 卸载
rpm -ql 软件名                 # 查看软件的安装位置
```

**RPM 详细选项**：
| 选项 | 作用 |
|------|------|
| `rpm -qa` | 列出所有已安装的软件 |
| `rpm -qi` | 查看软件详细信息（版本、大小、依赖等） |
| `rpm -qc` | 仅列出配置文件 |
| `rpm -qd` | 仅列出文档文件 |
| `rpm -qf 文件路径` | 查询文件属于哪个软件包 |

**YUM 源切换（CentOS 7 → 阿里云镜像）**：
```bash
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo
yum makecache
yum install -y vim
```

#### 服务器管理
**Tomcat**：
```bash
systemctl start/stop/restart tomcat
```

**防火墙**：
```bash
systemctl status/start/stop firewalld
systemctl enable/disable firewalld      # 开启/取消开机自启
firewall-cmd --list-all                 # 查看防火墙上的端口
```

**端口查看**：
```bash
# 参数：-t TCP -u UDP -n 数字方式显示 -l 运行状态 -p 进程ID
yum install -y net-tools               # 先安装网络工具
netstat -tunlp | grep 端口号
ss -tunlp | grep 8080
```

**查看系统服务**：
```bash
systemctl list-units --type service -all
```

**进程管理**：
```bash
kill -9/-1 PID     # -9强制杀死 -1重启
top                 # 查看系统进程
```

#### Nginx 服务器
**六大作用**：Web程序服务器（前端代码）、反向代理、内容缓存、负载均衡、TCP/UDP代理、邮件代理

**正向代理（Forward Proxy） vs 反向代理（Reverse Proxy）**：
- **正向代理**：从内向外代理（客户端通过代理访问外部，代理客户端）
- **反向代理**：从外向内代理（外部请求通过代理访问内部服务，隐藏内部细节，代理服务端）

**负载均衡（Load Balancing）算法**：
| 算法 | 说明 |
|------|------|
| 轮询（Round Robin） | 按顺序逐一分配任务 |
| 随机（Random） | 相对平均概率分配任务 |
| 最少时间（Least Time） | 响应时间越快的服务器接收新任务 |
| 最少连接（Least Connections） | 当前任务量较少的服务器接收新任务 |
| IPHash | 相同IP地址的所有请求都划分到同一台机器 |
| 权重（Weight） | 根据服务器权重比例分配任务 |

---

## 第二阶段：JAVA 基础

### Java 发展历程
- 计算机语言发展：二进制 → 汇编语言 → 高级编程语言（面向过程C → 面向对象C++ → Java → C#）
- Java 整合了 C++、A语言、Lisp语言
- Java之父：James Gosling（詹姆斯·高斯林，高司令）
- 1991年 SUN 公司 Green 小组开发，目标：电视机顶盒项目（智能家居）
- 1993年项目完成但无市场，团队转行互联网做软件
- 1995年5月23日 Java 正式诞生
- Java语言特征：**跨平台（Cross-Platform）、面向对象（OOP）、简单、高性能、自带GC（垃圾回收 Garbage Collection）机制、支持多线程（Multi-Threading）、支持异常处理（Exception Handling）、健壮性**

### Java 项目结构
- **src**：存放源代码文件（.java，程序员用自然语言编写的代码）
- **out**：存放字节码文件（.class，编译器翻译后的机器代码）
- 源代码 → 编译器（Compiler）翻译 → 字节码

### 环境变量
```
JAVA_HOME = Java安装目录
Path      = Java命令所在目录
CLASSPATH = .;
```

### 注释
- `//` 单行注释（快捷键 Ctrl+/）
- `/* */` 多行注释（快捷键 Ctrl+Shift+/）
- `/** */` 文档注释（Documentation Comment）

### Java 三大平台（Platform）
| 平台 | 全称 | 用途 |
|------|------|------|
| JavaSE | Java Standard Edition 标准版 | 桌面程序（如 IDEA、Eclipse） |
| JavaEE | Java Enterprise Edition 企业版 | 互联网/企业级应用 |
| JavaME | Java Micro Edition 微型版 | 手机应用（Android）、POS机 |

### 核心概念
- **JVM**（Java Virtual Machine，Java虚拟机）：对接各大OS平台
- **JRE**（Java Runtime Environment，Java运行环境）：提供运行环境
- **JDK**（Java Development Kit，Java开发工具）：给程序员提供开发环境
- 包含关系：JDK ⊃ JRE ⊃ JVM

### 包（Package）结构规范
```
第1层：项目性质（com=商业 company / org=开源 organization / edu=教育 education / gov=政府 government）
第2层：公司或团队名（com.alibaba / org.springframework）
第3层：项目名
```

### 变量
- 语法：`数据类型 变量名 = 初始值;`
- 三要素：有初始值、不能重复、有作用范围（局部变量 / 全局变量）

### 数组
- 存储大量相同数据类型的数据结构
- 特点：元素类型一致、空间连续、长度固定

### 循环语法
```java
// for循环：已知循环次数
for(初始条件; 循环判断条件; 变量改变) { 循环体 }

// 增强for（foreach）：遍历数组/集合
for(数据类型 变量名 : 数组名) { ... }

// while循环：已知退出条件
while(条件) { 循环体 }

// do-while循环：至少执行1次
do { 循环体 } while(条件);
```

### 分支结构
```java
// if双分支
if(条件) { ... } else { ... }

// if多分支（适合范围条件）
if(条件1) { ... } else if(条件2) { ... } else { ... }

// switch多分支（适合离散值条件）
switch(变量) {
    case 值1: ...; break;
    default: ...; break;
}
```

### 运算符
| 类别 | 运算符 | 说明 |
|------|--------|------|
| 赋值 | `=` `+=` `-=` `*=` `/=` `%=` | 将右边值赋给左边变量 |
| 四则 | `+` `-` `*` `/` `%` | `%` 取模（求余数） |
| 比较 | `>` `<` `>=` `<=` `==` `!=` | 结果一定是 true 或 false |
| 三元（Ternary） | `(条件) ? 值1 : 值2` | 条件满足返回值1，否则返回值2 |
| 自增/自减 | `++` `--` | 独立使用位置无所谓；非独立使用时，前置先变后用，后置先用后变 |
| 逻辑 | `&&` `||` `!` | 短路效果：`&&`前不满足则后不执行，`||`前满足则后不执行 |
| 按位 | `&` `|` `^` | 按二进制位运算，一般出现在集合底层 |

### String 字符串
- 底层是字符数组（char[]）
- 创建方式：`String s = "值";`（推荐）或 `String s = new String();`

### 基本数据类型（Primitive Types）
| 类型 | 占用字节 | 说明 |
|------|----------|------|
| byte | 1 | 整数 |
| short | 2 | 整数 |
| int | 4 | 整数（默认） |
| long | 8 | 整数（需加L后缀） |
| float | 4 | 浮点（需加F后缀） |
| double | 8 | 浮点（默认） |
| char | 2 | 字符 |
| boolean | — | 布尔（true/false） |

### ASCII编码（American Standard Code for Information Interchange）
- 美国信息交换标准代码
- 最早期的编码集：33个不可显示字符 + 95个可显示字符

### Debug 调试
| 快捷键 | 功能 |
|--------|------|
| F8 | 逐行向下执行，不进入方法内部 |
| F7 | 逐行向下执行，遇到方法进入方法内部 |
| Shift+F8 | 从方法内部直接退出 |
| F9 | 快速跳到下一个断点（Breakpoint） |

### 函数/方法（Method）
```java
[访问修饰符] [其他修饰符] 返回类型 函数名(形参列表) {
    // 函数体
}
```
- 4种组合：无参/有参 × 无返回/有返回
- 无参 = 无条件帮你做事情 / 有参 = 有条件帮你做事情
- 有返回 = 做完需要汇报结果 / 无返回 = 做完不需要汇报
- 函数 = 提供特殊功能的方法，封装内部细节，方便复用

### 栈（Stack）
- 遵循 **FILO**（First In Last Out，先进后出）的数据结构
- Java 中方法调用就是基于栈结构

### 常用 API 函数（Application Programming Interface，应用程序编程接口）
| 函数 | 作用 |
|------|------|
| `Arrays.toString()` | 打印数组内容 |
| `Integer.parseInt()` | 字符串转整数 |
| `str.equals()` | 比较字符串内容是否相同 |
| `str.contains()` | 判断是否包含子串 |
| `str.startsWith()` | 判断是否以……开头 |
| `str.endsWith()` | 判断是否以……结尾 |

### toString()
- `toString()` 是 Object（所有类的父类）的方法
- 默认返回内存地址，程序员需要重写以看到对象具体的属性值

### 面试题：基本数据类型 vs 引用数据类型
- 基本数据类型：太简单，只能存放简单数值
- 引用数据类型：相对复杂，可以存放复杂数据（对象、集合等）

---

### 面向对象（OOP, Object-Oriented Programming）

#### 类（Class）与对象（Object）
- **类**：对相同/相似东西的抽象（模板）
- **对象**：根据类产生的真实存在的东西（实例 Instance）
- 类是对象的抽象，对象是类的具体实例
- 结构化思维 = 亲力亲为；对象化思维 = 找能帮助自己的人或物

#### 属性（Property/Field）和行为（Method）
- **属性**：对象有什么值（Has）
- **行为**：对象能干什么（Do）

#### static 关键字
- `static` 修饰 = 类属性/类行为 → 只跟类有关，跟具体对象无关
- 工具类的工具方法用 static 修饰
- 对象的行为是动态的，不需要用 static

#### 实例成员 vs 静态成员 调用规则
```
1. 静态内部 → 不能直接调用实例成员（实例成员尚未创建）
2. 实例内部 → 可以直接调用静态成员
3. 静态内部 → 可以直接调用静态成员
4. 实例内部 → 可以直接调用实例成员
```
- 调用方式：`类名.静态成员` / `对象.实例成员`

#### 类的加载（Class Loading）
- 每个类只加载1次
- 静态成员在类加载期间就准备好，实例成员在运行期间创建

#### 构造器（Constructor）
- 无参构造器：编译器在编译期间自动生成
- 执行步骤：new创建对象 → 属性分配空间 → 赋初始值（0/null） → （有参构造器）执行剩余代码
- **加有参构造器之前，必须先加无参构造器**

#### 四大特征

**1. 封装（Encapsulation）**：包装 + 隐藏
- 包装：使用函数将代码包装起来
- 隐藏：使用访问修饰符（Access Modifier）控制可见性

| 修饰符 | 本类 | 本包 | 其他包的子类 | 其他包 |
|--------|------|------|------------|--------|
| private（私有） | ✓ | ✗ | ✗ | ✗ |
| 默认（default） | ✓ | ✓ | ✗ | ✗ |
| protected（受保护） | ✓ | ✓ | ✓ | ✗ |
| public（公共） | ✓ | ✓ | ✓ | ✓ |

> 企业推荐：属性用 private，行为用 public；搭配 getter（访问器）/ setter（设值器）

**2. 继承（Inheritance）**：子承父业，提升代码复用性
- **重写（Override）**：子类重新编写父类的行为（运行时多态）

**3. 多态（Polymorphism）**：相同行为，因对象不同有不同实现
- 三个必要条件：①继承 ②方法重写 ③父类引用 → 子类对象（向上转型 Upcasting）
- 应用：多态集合、多态参数

**4. 抽象（Abstraction）**

#### this & super
| 用法 | 含义 |
|------|------|
| `this.` | 代表当前对象自身，调用当前类的属性/行为 |
| `super.` | 代表父类，调用父类的属性/行为 |
| `this()` | 调用当前类的其他构造器 |
| `super()` | 调用父类的构造器 |

---

### Java 关键字全集

| 类别 | 关键字 |
|------|--------|
| 基本数据类型 | byte, short, int, long, float, double, char, boolean, void |
| 流程控制 | if, else, switch, case, default, for, while, do, break, continue, try, catch, finally, throw, throws |
| 类/方法/变量 | class, interface, enum, extends, implements |
| 访问控制 | private, protected, public |
| 修饰符 | static, final, abstract, synchronized, volatile, transient, native, strictfp, default |
| 引用相关 | this, super, new, instanceof |
| 包/导入 | package, import |
| 其他 | true, false, null, assert, var（Java 10+ 类型推断） |
| 保留字 | goto, const（未被使用） |

> 所有关键字都是小写，不能用作标识符（变量名/类名/方法名）

---

### 雪花ID（Snowflake ID）
Twitter 提出的分布式唯一 ID 生成算法：
- 最高位符号位 = 0（永远正数）
- 41位时间戳（69年范围内不重复）
- 10位机器ID（5位机房ID + 5位机器ID，进一步提升不可重复性）
- 12位序列化（1ms内产生4096个ID，1秒约409万个）
- 其它ID方案：UUID（适合高并发，但占空间大、无法排序/范围查询）、自增ID（适合中小型系统）

---

### 集合框架（Collection Framework）
- 集合 = 装东西的容器，最大好处是通过 API 封装底层的扩容/缩容操作

#### List vs Set
| 特性 | List 体系 | Set 体系 |
|------|-----------|----------|
| 有序性 | 有序 | 可有序可无序 |
| 下标 | 有 | 无 |
| 重复元素 | 不去重 | 去重 |

#### ArrayList vs LinkedList vs Vector
| 特性 | ArrayList | LinkedList | Vector |
|------|-----------|------------|--------|
| 底层 | 数组（Array） | 双向链表（Doubly Linked List） | 数组 |
| 查询 | 快（有下标） | 慢（无下标） | 快 |
| 插入/删除 | 慢（需移位） | 快 | 慢 |
| 线程安全 | 不安全 | 不安全 | 安全（synchronized，已少用） |

#### 线程安全（Thread-Safe）vs 线程非安全
- **线程安全**：同一时刻只能有1根线程操作数据 → 效率低，但数据"一致性"有保证
- **线程非安全**：允许多根线程同时操作相同数据 → 效率高，但数据可能无法保证"一致性"

#### 迭代器（Iterator）
- 集合遍历手段，特点：**边遍历边删除数据**

#### HashSet vs TreeSet
| 特性 | HashSet | TreeSet |
|------|---------|---------|
| 底层 | HashMap 的 Key（Hash表） | 红黑树（Red-Black Tree） |
| 去重规则 | hashCode() + equals() | 比较器（Comparator） |
| 排序 | 无序，不可自定义 | 有序，可通过传入比较器自定义 |

#### HashSet 去重原理（底层 = HashMap 的 Key）
1. 通过 Key 的 `hashCode()` 得 hash 值，按 `(n-1) & hash` 得下标
2. 位置为空 → 直接存储 Key-Value
3. 位置有值 → `equals()` 比较内容
4. 内容相同 → Key 不存，Value 覆盖
5. 内容不同 → 单向链表追加
6. Java 8+：链表长度超过阈值 → 转为红黑树（平衡树，避免"歪脖子"）

#### 红黑树（Red-Black Tree）规则
1. 所有节点要么红，要么黑
2. 根节点一定是黑
3. 红色节点的2个子节点必须是黑
4. 从任意节点到叶子节点的所有路径上，必须经过相同数量的黑色节点
5. 叶子节点一定是黑，且值为 NULL

#### HashMap
- Java 8 前：数组 + 单向链表
- Java 8 后：数组 + 单向链表 + 红黑树
- KEY 去重依据：hashCode() + equals()
- 线程非安全，允许 null 键值

#### HashMap vs Hashtable vs ConcurrentHashMap
| 特性 | HashMap | Hashtable | ConcurrentHashMap |
|------|---------|-----------|-------------------|
| 线程安全 | 不安全 | 安全（方法加 synchronized） | 安全 |
| null 键值 | 允许 | 不允许 | 不允许 |
| 性能 | 高 | 低（已淘汰） | 高（JDK8 使用 CAS + synchronized） |

#### Map 体系特点
- 双列集合，有 Key（键）有 Value（值）
- Key 不允许重复，Value 可以重复
- Key-Value 之间是 1 对 1 关系

---

### String & StringBuilder & StringBuffer
| 特性 | String | StringBuilder | StringBuffer |
|------|--------|---------------|--------------|
| 内容/长度可变 | 不可变（每次修改生成新对象） | 可变（底层数组扩容） | 可变 |
| 线程安全 | — | 不安全 | 安全（方法加 synchronized） |
| 性能 | 拼接产生大量对象，浪费内存 | 高 | 较低 |

---

### 7大设计原则（Design Principles）
1. **单一原则（SRP, Single Responsibility）**：一个类只干一件事
2. **开闭原则（OCP, Open-Closed）**：对扩展开放，对修改关闭
3. **里氏替换原则（LSP, Liskov Substitution）**：子类能替代父类，尽量不要重写父类已实现的方法
4. **迪米特法则（LoD, Law of Demeter）**：最少知道原则，不要和陌生人说话
5. **依赖倒置原则（DIP, Dependency Inversion）**：面向接口编程，而非面向实现类
6. **接口隔离原则（ISP, Interface Segregation）**：接口最小化，一个接口中的方法尽量只跟当前业务有关
7. **组合聚合原则（CRP, Composite Reuse）**：多用组合少用继承

> 7大设计原则 → 23种设计模式（Design Patterns）。设计原则是思想（指导意义），设计模式是方法（实战意义）。常用：工厂方法模式、单例模式、代理模式、适配器模式……

---

### 泛型（Generics）
```java
// 泛型接口
public interface MyList<E> { void add(E e); void remove(E e); }

// 泛型类
public class MyArrayList<E> implements MyList<E> { ... }

// 泛型方法
public static <T> String sum(T a, T b) { return "" + a + b; }
```

---

### IO流（Input/Output Stream）
- 流分类：①按方向→输入流/输出流 ②按大小→字节流/字符流 ③按功能→节点流/功能流

| 流类型 | 适用场景 |
|--------|----------|
| 字符流（Reader/Writer） | 只能读取文本文件 |
| 字节流（InputStream/OutputStream） | 所有二进制文件（图片/音频/视频/PDF/压缩包） |
| 缓冲流（Buffered） | 基于已有节点流，添加缓冲功能 |
| 打印输出流（PrintStream） | 以符合OS字符集的方式将内容打印到控制台 |

**数据存储演进**：变量 → 数组 → 集合（缺乏持久性） → 文件 → MySQL 数据库

---

### JDBC（Java Database Connectivity，Java数据库连接）
- SUN 公司定义规范（接口），各大数据库厂商自行实现（驱动 Driver）
- 是 Hibernate / MyBatis / MyBatis-Plus / Spring-Data-JPA 的底层

#### SQL 注入攻击（SQL Injection）
```java
// 危险写法：字符串拼接
String sql = "SELECT * FROM books WHERE author = " + author + " AND category = " + category;
// 输入 category = '小说' OR 1=1 → 绕过条件，暴露全部数据
```
- 防御：使用参数化查询（PreparedStatement / MyBatis 的 `#{}`）

---

### 反射（Reflection）
- 在程序运行时（Runtime）动态获取类信息、创建对象、调用成员
- **所有框架的底层**：配置文件存字符串 → 运行时通过反射按需动态加载类
- 作用：探查类的类信息、动态创建对象、探查/调用属性和行为

**获取 Class 对象的3种方式**：
```java
// 1. 全限定名（框架最常用）
Class<?> cls = Class.forName("com.iwe3.day13.entity.CustomerEntity");
// 2. 对象.getClass()
obj.getClass();
// 3. .class 属性
CustomerEntity.class;
```
- 每个类加载后在 JVM 方法区产生唯一的 Class 对象，一个类只加载一次

---

### Lombok
```java
@Getter                       // 生成 getter()
@Setter                       // 生成 setter()
@NoArgsConstructor            // 生成无参构造器
@AllArgsConstructor           // 生成全参构造器
@RequiredArgsConstructor      // 生成有参构造器（配合 @NonNull）
```

---

### Java 多线程（Multi-Threading）

#### 基础概念
- **进程（Process）**：运行状态下的应用程序，电脑分配资源的最小单位（CPU/内存/磁盘/网络）
- **线程（Thread）**：进程内部执行任务的最小单位，一个进程至少需要一根线程
- **CPU 内核**：每个内核可同时处理线程（物理核/逻辑核），常见2核~32核

#### 并发（Concurrency） vs 并行（Parallelism）
- **并行**：多个任务同时执行（多核CPU之间）
- **并发**：多个任务交替执行（单核内部面临多线程任务）
- **高并发（High Concurrency）**：单核/单台服务器同时面临海量任务

#### start() vs run()
- `run()`：定义任务，由 CPU 调用
- `start()`：启动任务，由程序员调用

#### 线程创建方式
1. 继承 Thread 类（缺点：Java单继承，占用了父类位置）
2. 实现 Runnable 接口
3. 实现 Callable 接口（有返回值）
4. 线程池（ThreadPoolExecutor）

#### 线程生命周期（Life Cycle）
**Java 标准状态**：NEW（创建） → RUNNABLE（就绪/运行） → BLOCKED（阻塞） / WAITING（等待） / TIMED_WAITING（计时等待） → TERMINATED（死亡）

**CPU 调度算法**：
- **分时调度**：按时间片随机分配给线程
- **优先级调度**：优先级越高（1-10，默认5），分配到时间片的概率越高

#### 线程核心方法
| 方法 | 作用 | 特点 |
|------|------|------|
| `sleep()` | 休眠，运行→阻塞 | 自动苏醒，**不释放锁** |
| `wait()` | 等待 | 需要 `notify()` / `notifyAll()` 唤醒，**主动释放锁** |
| `join()` | 子线程加入其他线程 | 子线程执行时主线程处于阻塞状态 |
| `yield()` | 线程让步 | 暗示CPU当前任务不重要，让同优先级线程优先执行（不一定成功） |

#### 线程安全（Thread-Safe）与锁机制
- **synchronized**：悲观锁（Pessimistic Lock），同一时刻只运行1根线程处理数据；非公平、可重入、不可剥夺、互斥/排他
- **Lock**（java.util.concurrent.locks）：可公平/不公平，可重入，不可剥夺，互斥

#### 锁升级过程（不可降级）
无状态 → 偏向锁（Biased Lock） → 轻量级锁（Lightweight Lock，自旋） → 重量级锁（Heavyweight Lock）

#### CAS（Compare-And-Swap，比较并交换）& ABA 问题
- **CAS**：乐观锁（Optimistic Lock），乐观认为并发较低，没有冲突
- **ABA 问题**：变量从X→Y→X，另一个线程误以为值没变 → 用版本号解决

#### 单例模式（Singleton Pattern）
**饿汉模式**（天生线程安全，未调用即创建）：
```java
private static JdbcTemplate instance = new JdbcTemplate();
private JdbcTemplate(){}
public static JdbcTemplate getInstance() { return instance; }
```

**懒汉模式**（双重检查锁 DCL, Double-Checked Locking，调用时才创建）：
```java
private static volatile JdbcTemplate instance = null;
public static JdbcTemplate getInstance() {
    if(instance == null) {                          // 提升效率，减少进入同步块的必要性
        synchronized (JdbcTemplate.class) {          // 保证同一时刻只有1个线程进入
            if(instance == null) {                   // 防止重复创建对象
                instance = new JdbcTemplate();
            }
        }
    }
    return instance;
}
```

#### 死锁（Deadlock）产生的4个条件
1. 使用互斥锁（synchronized）
2. 其他线程不可剥夺
3. 无限期等待
4. 一直保持死锁状态

#### synchronized 用法
```java
// 同步代码块
synchronized(this) { ... }        // 锁当前对象
synchronized(类.class) { ... }    // 锁类的 Class 对象
synchronized(obj) { ... }         // 锁任意对象

// 同步方法
public synchronized void method() { ... }
```

#### volatile（易变的）
- 保证可见性（Visibility）
- 禁止指令重排（Ordering）
- **不保证原子性**（Atomicity，需 synchronized 或 Lock 保证）

#### ThreadLocal
- 线程局部变量，每个线程有独立副本
- 应用场景：数据库连接管理、用户信息传递

#### 守护线程（Daemon Thread）
- 为其他线程服务（如 GC 垃圾回收线程）
- 主线程结束则守护线程自动终止

#### 线程池（Thread Pool）
- `es.execute()`：只能提交 Runnable 任务
- `es.submit()`：可提交 Runnable 和 Callable 任务

#### 面试题：主线程中有10根子线程
- 其中2根需要知道运行结果并等待执行完毕 → 使用 **Callable** 定义
- 其余8根 → 使用 **Runnable** 定义
- 统一交给 **线程池** 管理

---

### JVM（Java Virtual Machine，Java虚拟机）基础

#### 内存区域（Memory Area）
| 区域 | 共享性 | 存储内容 |
|------|--------|----------|
| 方法区（Method Area） | 线程共享 | 类信息、常量、静态变量 |
| 堆（Heap） | 线程共享 | 对象实例和数组 |
| 虚拟机栈（VM Stack） | 线程独享 | 局部变量、方法调用（栈帧 Frame） |
| 本地方法栈（Native Method Stack） | 线程独享 | 为 Native 方法服务 |
| 程序计数器（PC Register） | 线程独享 | 记录当前线程执行位置 |

#### 类加载（Class Loading）
- 双亲委派模型（Parent Delegation Model）

#### GC（Garbage Collection，垃圾回收）
- `finalize()` 方法在回收前可能调用（不推荐使用）

---

### Java 8 日期时间 API
- **之前**：`Date`（方法过时，不支持国际化）、`Calendar`
- **之后**：`LocalDate`（日期）、`LocalTime`（时间）、`LocalDateTime`（日期时间）

---

### BigDecimal（大小数）
- 对应数据库中 `DECIMAL` 数据类型
- 可精确控制小数点后的位数
- 比较用 `compareTo()`，构造推荐用 String

### SimpleDateFormat
- 非线程安全，建议用 `ThreadLocal` 包装或改用 Java 8 的 `java.time` 包

### File & Socket
- `File`：文件操作类
- `Socket`：网络编程类

---

### 匿名内部类 & Lambda & Stream

#### 匿名内部类（Anonymous Inner Class）
- 没有名字的内部类，适合抽象类/接口在项目中只使用一次的场景

#### Lambda 表达式（Lambda Expression）
- 替代接口作为匿名内部类的写法
- 语法：`(参数列表) -> { 业务代码 }`

#### Stream 流
- 处理数据的流水线（与 IO 流概念不同）
- 集合的作用是**装数据**，Stream 的作用是**处理数据**
- 常用 API：过滤（filter）、转换（map）、统计（count）、排序（sorted）、采集（collect）……

**两种流**：
- `list.stream()` — 串行流（Sequential Stream，按顺序执行）
- `list.parallelStream()` — 并行流（Parallel Stream，数据分块同时执行）

---

### 常见注解（Annotation）
| 注解 | 含义 |
|------|------|
| `@Deprecated` | 标记方法/类已废弃 |
| `@Override` | 标记为重写的方法 |
| `@SuppressWarnings("all")` | 忽略警告 |
| `@FunctionalInterface` | 标记为函数式接口 |

---

### 异常（Exception）
| 类型 | 继承 | 检查时机 |
|------|------|----------|
| 受检异常（Checked Exception） | Exception | 编译时就提醒 |
| 非受检异常（Unchecked Exception） | RuntimeException | 运行时才可能抛出 |

- `throw`：用来抛出异常
- `throws`：声明方法可能抛出异常

---

### 接口（Interface）演变
- Java 8 前：接口只能有抽象方法（Abstract Method）和常量（Constant）
- Java 8 后：接口可以有 `default` 方法和 `static` 方法（不建议放真实业务逻辑）
- 抽象类：可以有抽象方法 + 具体方法

---

### equals() & hashCode() 联合判重原理
1. 先判 `hashCode()`（性能好，做快筛）→ 不同则直接判定两成员不同
2. hashCode 相同 → `equals()` 做最终判断（性能不好但准确，真假都能判）

---

### 比较器（Comparator）原理
- 比较器内封装了基础排序算法（两两比对）
- 重写的 `compare()` 方法会被触发多次
- 好处：节省写基础排序算法的精力，专注于多字段排序业务

---

## 第三阶段：MySQL 数据库

### 数据库基本概念
- 数据库管理软件：MySQL、SqlServer、Oracle、DB2、AliSQL、高斯数据库……
- **关系型数据库（RDBMS）**：关注数据和数据之间的关系，使用 SQL 语言操作
- **非关系型数据库（NoSQL, Not Only SQL）**：关注快速存取，无 SQL 语言操作

### 关系型数据库存储结构
1:1 / 1:N / N:N

### MySQL
- 瑞典 MySQL AB 公司开发，免费、开源的关系型数据库
- SQL（Structured Query Language，结构化查询语言）：所有关系型数据库都遵从的标准

### Docker 安装 MySQL
```bash
docker pull mysql
docker run --name mysql01 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=你的密码 -d 镜像ID
```

---

### DDL（Data Definition Language，数据定义语言）

> 常见字符编码集：UTF-8、GBK、GB2312、utf8mb4

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
ALTER TABLE 表名 ADD 新字段 数据类型 [约束];       -- 新增字段
ALTER TABLE 表名 MODIFY 字段 新数据类型 [约束];   -- 修改字段类型
ALTER TABLE 表名 CHANGE 旧字段 新字段 数据类型 [约束]; -- 字段重命名
ALTER TABLE 表名 DROP COLUMN 字段名;              -- 删除字段
-- 删表
DROP TABLE 表名;
```

---

### DML（Data Manipulation Language，数据操作语言）

```sql
-- 新增
INSERT INTO 表名(字段列表) VALUES (值列表), (值列表)……;
-- 修改
UPDATE 表名 SET 字段=值,…… [WHERE 筛选条件];
-- 删除
DELETE FROM 表名 [WHERE 筛选条件];     -- 逐行删，可回滚
TRUNCATE TABLE 表名;                   -- 清空表，重置自增ID，不可回滚
```

---

### 约束（Constraint）类型

| 约束 | 关键字 | 说明 |
|------|--------|------|
| 主键（Primary Key） | PRIMARY KEY | 非空且唯一，不能具备业务含义 |
| 自增 | AUTO_INCREMENT | 自动递增 |
| 唯一（Unique） | UNIQUE | 值不能重复 |
| 检查（Check） | CHECK (条件) | 限制列的取值范围 |
| 非空（Not Null） | NOT NULL | 必须有值 |
| 默认（Default） | DEFAULT 值 | 未输入时使用默认值 |
| 外键（Foreign Key） | FOREIGN KEY | 建立表之间的关系 |

#### 外键约束语法
```sql
ALTER TABLE 子表名
ADD CONSTRAINT 外键名 FOREIGN KEY (外键列)
REFERENCES 主表名(主键)
[ON DELETE action ON UPDATE action];
```

> 优秀设计者：使用外键，但不使用外键约束（由程序保证关系正确性，降低数据库复杂性和维护成本）

---

### DQL（Data Query Language，数据查询语言）

#### 执行顺序（Execution Order）
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
[ORDER BY 排序字段 ASC/DESC]
[LIMIT 分页];
```

#### 条件查询（WHERE）
- `=` / `>` / `<` / `>=` / `<=` / `!=` / `<>`（不等于）
- `AND`（与，同时满足）/ `OR`（或，任一满足）
- `LIKE`（模糊查询，`%` 匹配0~N个字符，`_` 匹配单个字符）
- `BETWEEN ... AND ...`（区间范围）
- `IS NULL` / `IS NOT NULL`

#### 聚合函数（Aggregate Function）
| 函数 | 作用 |
|------|------|
| `COUNT(*)` / `COUNT(1)` | 统计总条数 |
| `COUNT(字段)` | 统计该字段非NULL值的条数 |
| `SUM()` | 求和 |
| `AVG()` | 求平均数 |
| `MAX()` / `MIN()` | 求最大值/最小值 |
| `IFNULL(字段, 默认值)` | 处理NULL值 |

#### 字符串函数
`UPPER()`（转大写）/ `LOWER()`（转小写）/ `CONCAT()`（拼接）/ `TRIM()`（去首尾空格）/ `SUBSTR()`（截取）/ `LENGTH()`（求长度）/ `REPLACE()`（替换）

#### 日期函数
`YEAR()` / `MONTH()` / `DAY()` / `NOW()` / `CURDATE()`

#### 数学函数
`RAND()`（0~1随机小数）/ `ROUND()`（四舍五入）/ `PI()`（圆周率）/ `MOD()`（取模）

#### 连接方式
- **内联查**（INNER JOIN）
- **左外联查**（LEFT JOIN）
- **右外联查**（RIGHT JOIN）

#### 分组查询
```sql
SELECT 字段, COUNT(*) FROM 表 GROUP BY 分组字段;    -- 基本分组
-- 复合分组：分组之后，在每个组内部再次分组
-- HAVING：将聚合后的结果再次过滤（WHERE 是分组前行级过滤，HAVING 是分组后聚合过滤）
```

---

### DCL（Data Control Language，数据控制语言）
- 管理数据库账号
- 划分用户权限

---

### 子查询（Subquery）
- 位置：SELECT 后面 / FROM 后面 / WHERE 后面
- **几乎所有子查询都可用 LEFT/INNER JOIN 替代**，但 WHERE 后的聚合函数子查询只能使用子查询

#### 分类
| 类型 | 特点 | 示例 |
|------|------|------|
| 非相关子查询 | 子查询可独立执行 | WHERE score >= (SELECT AVG(score) FROM ...) |
| 相关子查询 | 需要外部SQL提供数据 | SELECT (SELECT dept_name FROM dept WHERE id = fk_dept_id) |

#### IN vs EXISTS
- `IN`：内表数据量少时更合适，底层做笛卡尔乘积 + 等值判断
- `EXISTS`：外表数据量少时更合适，逐行代入内表进行匹配
- 内外数据量相差不大，两者都可以使用

#### UNION vs UNION ALL
- `UNION`：合并结果集并去重
- `UNION ALL`：合并结果集不去重，效率更高

---

### 数据库设计范式（Normalization）
| 范式 | 要求 |
|------|------|
| 第1范式（1NF） | 原子性（Atomicity）：列拆分到不可再拆 |
| 第2范式（2NF） | 相关性：所有列必须跟主键有关系 |
| 第3范式（3NF） | 直接相关性：所有列必须跟主键有直接关系，不能是间接关系 |

> 反范式（Denormalization）：为提升查询性能适当冗余数据

---

### 视图（View）
- 虚拟表，数据由底层物理表提供
- 只适合查询，不能增删改（改动数据需在物理表中操作）
- 作用：降低多表操作的复杂性
```sql
CREATE VIEW 视图名 AS SELECT 查询语句;
```

---

### 索引（Index）
- MySQL 5.5+：只有主键ID作为聚簇索引
- **聚簇索引（Clustered Index）**：索引和数据绑定在一起
- **非聚簇索引（Non-Clustered Index）**：索引和数据不绑定，查索引后需回表

#### 索引类型
| 类型 | 创建方式 | 特点 |
|------|----------|------|
| 主键索引 | `PRIMARY KEY` 自动创建 | 聚簇索引 |
| 唯一索引 | `UNIQUE` 或 `CREATE UNIQUE INDEX` | 列中不能有重复值 |
| 普通索引 | `CREATE INDEX` | 列中允许重复值 |
| 组合索引 | `CREATE INDEX ON 表(字段1,字段2,字段3)` | 多字段联合，遵循**最左前缀原则** |
| 外键索引 | 添加外键约束时自带 | 便于联表时快速连接 |

#### 最左前缀原则
```sql
CREATE INDEX idx_name ON 表(name, citycode, yzcode);
-- 查询必须从最左字段开始匹配，否则索引失效
```

#### 索引覆盖（Covering Index） & 回表
- **回表**：从索引表中根据数据指针回到原始表中查找完整数据的过程
- **索引覆盖**：查询内容完全被索引覆盖（底层原理：索引下推 ICP, Index Condition Pushdown）
- 建议：使用索引查找时不要用 `SELECT *`，应按需查找

#### 不适合加索引的场景
1. 数据量不超过几十万
2. 经常变化的字段
3. 大量重复值的字段（如性别）
4. 很少使用的列

#### 索引失效场景
1. `SELECT *` 查询
2. 查询条件中携带四则运算
3. LIKE 模糊查询，`%` 在最前面
4. 使用 `OR` 关键字
5. 使用 `<>`（可能失效）
6. 组合索引未遵从最左前缀原则
7. 数据库优化器认为不需要使用索引

#### 执行计划（Execution Plan）
```sql
EXPLAIN SELECT ...;   -- 查看索引是否生效
```

---

### 存储过程（Stored Procedure） & 函数 & 触发器（Trigger）

#### 存储过程
- 一种特殊的函数，可以改动数据库（而普通函数只能查询）
- 优点：减少网络开销、靠近数据执行效率更高
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

### HTML（HyperText Markup Language，超文本标记语言）
- HTML = 网页的身体（构建网页），CSS = 装饰外观，JS = 控制行为

**标签分类**：
- 双标签：`<div></div>`（有开始有结束）
- 单标签：`<hr>`（只有开始）
- 块级标签（Block-level）：独占一行
- 行级标签（Inline）：共享一行

**CSS（Cascading Style Sheets，层叠样式表）引入方式**：
| 方式 | 写法 | 优先级 |
|------|------|--------|
| 行内样式 | 标签的 style 属性 | 最高 |
| 内联样式 | `<style>` 标签 | 中 |
| 外联样式（推荐） | `<link>` 引入外部CSS文件 | 低 |

> 推荐外联样式：真正实现 HTML & CSS 分离

**常用标签**：
- 表单：`<form>` / `<input>` / `<button>`……接收用户输入
- 表格：`<table>` / `<tr>`（行）/ `<th>`（表头）/ `<td>`（单元格）
- 列表：有序列表（`<ol>`）、无序列表（`<ul>`）、自定义列表（`<dl>`）
- 头部：`<head>` 定义文档属性和链接外部资源

**浮动（Float）**：让块级标签共享一行

---

### JavaScript（JS）
- Java 和 JavaScript 的关系：雷锋和雷峰塔的关系（无直接关系）
- Java 是半编译半解释性语言 / JS 是浏览器解释性语言

#### var vs let
| 特性 | var | let |
|------|-----|-----|
| 作用域 | 函数作用域/全局作用域 | 块级作用域 `{}` |
| 变量提升 | 提升并初始化为 undefined | 提升但在声明前访问报错（ReferenceError） |
| 重复声明 | 允许 | 不允许 |

#### DOM（Document Object Model，文档对象模型）
JS 三大作用：
1. 修改网页内容
2. 修改网页样式
3. 通过事件触发函数

---

### Vue 基础
- `v-bind`：给标签属性赋值
- `computed`：计算属性
- **生命周期钩子（Lifecycle Hooks）**：beforeCreate → created → beforeMount → mounted → beforeUpdate → updated → beforeDestroy → destroyed

---

### Tomcat 服务器
- SUN 公司 & Apache 平台共同开发的 Web 程序服务器
- 作用：接收用户请求、运行 Java 后台程序
- 也被称为：Web 容器 / Servlet 容器

---

### Web 系统架构

#### B/S（Browser/Server，浏览器/服务器） vs C/S（Client/Server，客户端/服务器）
| 架构 | 模式 | 客户端依赖 | 举例 |
|------|------|------------|------|
| B/S | 浏览器/服务器 | 浏览器 | 淘宝、京东 |
| C/S | 客户端/服务器 | 自行安装/更新客户端 | CS2、原神 |

#### Web 技术栈（Tech Stack）
1. 前端技术：HTML/CSS/JS/小程序/Android/iOS/鸿蒙
2. 前端服务器：Nginx
3. 后端技术：Spring 系列框架、中间件、缓存技术
4. 数据库：MySQL/Oracle + Redis/HBase
5. 通讯协议：HTTP

---

### Servlet
- 可以帮你处理前端请求的 Java 类
- 规范：继承 `HttpServlet`

#### 生命周期
编译 → 加载 → 实例化 → 初始化 → 服务化 → 卸载
> 编译/加载/实例化/初始化 只执行1次；服务化执行多次
> **多线程 & 单实例**：不存储数据，无线程安全问题；单实例节约 JVM 内存空间

#### Web 三大组件
| 组件 | 作用 |
|------|------|
| Servlet | 接收前端的 HTTP 请求 |
| Filter（过滤器） | 过滤前端的 HTTP 请求 |
| Listener（监听器） | 监听三大作用域的变化 |

#### 三大作用域（Scope）
| 作用域 | 对应 | 生命周期 | 说明 |
|--------|------|----------|------|
| request | HttpServletRequest | 单次HTTP请求 | 非常短 |
| session | HttpSession | 单次会话 | 默认30分钟，收到请求自动重置 |
| application | ServletContext | 跟Tomcat同步 | 最长，代表应用上下文 |

#### HTTP（HyperText Transfer Protocol，超文本传输协议）

**请求协议（Request）**：
- 请求行：提交方法、请求路径、协议版本
- 请求头：浏览器给后端服务器的信息
- 空行：分隔请求头和请求体
- 请求体：携带的数据

**响应协议（Response）**：
- 响应行：协议版本、状态码、状态码的描述
- 响应头：后端服务器响应给浏览器的关键信息
- 空行：分隔响应头和响应体
- 响应体：返回前端数据或页面

**状态码（Status Code）**：
| 范围 | 含义 |
|------|------|
| 1xx（100+） | 后端收到请求，但未处理完毕 |
| 2xx（200+） | 后端已处理完毕（成功） |
| 3xx（300+） | 后端无法处理，重定向到其他路径 |
| 4xx（400+） | 前端错误（如 404 资源不存在） |
| 5xx（500+） | 后端错误（服务器内部错误） |

#### HTTP 提交方法
| 方法 | 场景 | 特点 |
|------|------|------|
| GET | 查询 | 参数在URL，有长度限制，可缓存，幂等 |
| POST | 新增/上传 | 参数在请求体，可传二进制 |
| PUT | 全量修改 | 幂等 |
| DELETE | 删除 | 幂等 |

#### GET vs POST 区别
1. GET 参数从 URL 传输 / POST 通过请求体传输
2. GET 只能传输文本 / POST 可传输二进制数据（适合文件上传下载）
3. GET 受浏览器 URL 长度限制 / POST 理论上没有限制
4. GET 请求被缓存在历史记录 / POST 不会 → POST 更安全

#### @WebServlet 注解
- JavaEE 4.0 起推荐使用注解替代 web.xml
- 约定优于配置（Convention over Configuration）
```java
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet { ... }
```

#### 请求转发（Forward）vs 重定向（Redirect）
- **请求转发**：浏览器发起1次HTTP请求，服务器内部转发
- **重定向**：浏览器分别向服务器发送2次HTTP请求，由浏览器实现

---

### JSP（Java Server Page）
- 后端页面技术，组成 = HTML + Java 代码
- 本质是 Servlet
- 九大内置对象

#### EL 表达式（Expression Language）
```
${属性}  // 获取顺序：pageContext → request → session → ServletContext
```

#### JSP 脚本程序（Scriptlet）
- 在 JSP 页面中内嵌 Java 代码

#### JSP 指令
- `include`：导入公共页面片段（如 footer.jsp）
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="footer.jsp" %>
```
- `taglib`：引入标签库（如 JSTL, JSP Standard Tag Library）
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

#### Session 执行原理
- 默认30分钟有效期
- 30分钟内收到任何HTTP请求 → 重置有效时间为30分钟
- 30分钟内未收到请求 → Tomcat 自动销毁 Session

---

### MVC 架构模式（Model-View-Controller，模型-视图-控制器）

| 层 | 角色 | 职责 |
|-----|------|------|
| Model（模型） | JavaBean | 存储数据、处理业务、数据库交互 |
| View（视图） | JSP/HTML | 展示页面给用户 |
| Controller（控制器） | Servlet | 接收请求、调用Model |

#### 对象分层
| 对象 | 全称 | 作用 |
|------|------|------|
| VO | View Object（视图对象） | 接收用户输入 |
| DTO | Data Transfer Object（数据传输对象） | 各层之间传输数据 |
| DAO | Data Access Object（数据访问对象） | 数据库交互 |
| BO | Business Object（业务对象） | 业务实现（*ServiceImpl） |
| PO | Persistent Object（持久化对象） | 持久化对象（*Entity） |

---

### Cookie + Session
- **Cookie**：浏览器特有的文本存储技术（存在客户端）
- **Session**：Tomcat 中每个用户有独立的 Session 对象（存在服务端）
- 后端通过 Cookie + Session 识别 HTTP 请求来自哪个用户

#### URI vs URL
- **URI**（Uniform Resource Identifier，统一资源标识符）
- **URL**（Uniform Resource Locator，统一资源定位符）

---

### 过滤器（Filter）
**使用场景**：
1. 字符编码统一处理
2. 用户权限控制（如登录过滤器）
3. 日志记录与请求监控
4. 敏感词过滤/数据预处理
5. 跨域请求处理（CORS）

---

### ORM（Object Relational Mapping，对象关系映射）
| 映射关系 |
|----------|
| 类（Class） → 表（Table） |
| 对象（Object） → 记录（Row/Record） |
| 属性（Field） → 字段（Column） |

| 类型 | 框架 | 特点 |
|------|------|------|
| 全自动 | Hibernate、Spring-Data-JPA、MyBatis-Plus | 不需要程序员编写SQL |
| 半自动 | MyBatis | 需要程序员编写SQL，学习难度最低 |

---

### Maven
- 项目管理和构建自动化工具
- **六大作用**：项目构建自动化、依赖管理、项目结构标准化、项目信息管理、扩展插件机制、多模块项目支持

**生命周期（Lifecycle）**：clean（清理） → compile（编译） → test（测试） → package（打包） → install（安装到本地仓库） → deploy（部署到远程仓库）

---

### Spring 框架

#### Spring 核心
- 底层：**工厂模式（Factory） + 反射（Reflection） + HashMap 单例模式（Singleton）**
- 一站式服务框架

#### Bean 生命周期
实例化（Instantiation） → 属性注入/初始化（Initialization） → 注册 Destruction 回调 → 服务化 → 销毁

#### IOC（Inversion of Control，控制反转）
- 在没有 Spring 容器之前，组件由程序自己主动创建
- 有了 Spring 容器之后，组件由 Spring 容器创建，程序被动接受
- 反转：创建对象的权利从"主动创建"变成"被动接受"

#### Bean 作用域（Scope）
| 作用域 | 说明 |
|--------|------|
| singleton（单例，默认） | 每个组件在IOC容器中只有一个唯一实例 |
| prototype（原型） | 每调用一次产生一个新实例 |
| request | 同一请求范围内唯一实例 |
| session | 同一会话范围内唯一实例 |
| application | 同一Tomcat范围内唯一实例 |

#### DI（Dependency Injection，依赖注入）
- IOC 的实现方式
- 通过 setter 方法或构造参数维护组件之间的关系

#### 自动装配（Autowiring）
| 方式 | 规则 | 异常情况 |
|------|------|----------|
| byName | 按属性名称匹配 | 不存在不报错，运行抛 NullPointerException |
| byType | 按属性类型匹配 | 超过1个抛 NoUniqueBeanDefinitionException；0个不报错 |
| constructor | 按构造器装配，参数按 byType | — |

#### @Autowired vs @Resource
| 注解 | 装配顺序 | 来源 |
|------|----------|------|
| `@Autowired` | 先 byType 后 byName | Spring 框架 |
| `@Resource` | 先 byName 后 byType | Java 自带（JSR-250） |

#### AOP（Aspect-Oriented Programming，面向切面编程）
- 通过代理模式实现（JDK 动态代理 / CGLIB 代理）

**AOP 术语**：
| 术语 | 说明 |
|------|------|
| 切面（Aspect） | 放置非核心业务（交叉业务）的地方 |
| 切入点（Pointcut） | 切面可以切入的地方，使用 execution 表达式或 @annotation |
| 连接点（JoinPoint） | 切面正式和切入点连接的点 |
| 通知/增强（Advice） | 在真实方法调用前/后增加更多功能 |
| 目标对象（Target） | 真实对象，总是被代理对象所代理 |
| 织入（Weaving） | 将切面应用到切入点的过程 |

**通知类型**：@Before（前置）/ @After（后置）/ @AfterReturning（返回后）/ @AfterThrowing（异常后）/ @Around（环绕）

---

### Spring MVC

#### 工作流程
```
前端HTTP请求
  → 前端控制器（DispatcherServlet）
    → 处理映射器（HandlerMapping）：有没有方法可处理请求？
      → 处理适配器（HandlerAdapter）：适配参数
        → Controller（处理器）：执行完毕返回 ModelAndView
          → 视图解析器（ViewResolver）：逻辑视图名 → 真实视图
            → 填充 Model 数据 → 返回浏览器
```

#### 组件
| 组件 | 作用 |
|------|------|
| 前端控制器（DispatcherServlet） | 调度HTTP请求（核心） |
| 处理映射器（HandlerMapping） | 管理 URL-类&方法 关系 |
| 处理适配器（HandlerAdapter） | 处理参数、处理返回值 |
| 视图解析器（ViewResolver） | 逻辑视图名 → 真实视图资源 |

#### 配置文件分工
- `applicationContext.xml`：放置业务层（Service）、持久层（Dao）的组件
- `spring-mvc.xml`：放置表现层（Controller）的组件

#### SpringMVC 项目搭建步骤
1. 创建基于 Maven 的 Web 项目
2. 导入依赖
3. 在 `src/main/resources` 创建 `spring-mvc.xml`
4. 在 `src/main/resources` 创建 `spring-app.xml`
5. 在 `web.xml` 中分别启动2大Spring容器
6. 启动 Tomcat
7. 释放 static 静态资源
8. 正式进入开发

#### 常用 JSON 注解
```java
@JsonSerialize(using = ToStringSerializer.class)  // 解决雪花ID前端丢失精度
@JsonFormat(pattern = "yyyy-MM-dd")                // 时间日期格式化
```

#### 自定义类型转换器
- 场景：前端发送日期格式 `2025/08/12`，后端 `LocalDate` 无法解析
- 解决：自定义类型转换器，告诉 SpringMVC 遇到这种数据应如何处理

---

### Spring Boot
- **脚手架（Scaffold）**：方便快速构建项目
- 核心：**约定优于配置（Convention over Configuration）**

#### 六大特点
1. 创建独立的 Spring 应用程序
2. 直接内嵌 Tomcat、Jetty 或 Undertow（无需部署WAR文件）
3. 提供 Starter（启动器）简化依赖管理
4. 尽可能自动配置 Spring 和第三方库
5. 提供生产就绪功能：指标（Metrics）、健康检查（Health Check）、外部化配置
6. 绝对不需要代码生成，也无需 XML 配置

#### Starter 三大作用
1. 管理 Maven 依赖关系
2. 完成自动配置（Auto Configuration）
3. 遵循约定优于配置

> Spring Boot 不支持 JSP，推荐 Thymeleaf（百里香叶）模板引擎

---

### SSM 组合
十年前主流 Java Web 开发组合：
- **表现层**：SpringMVC（后来替代了有严重BUG的 struts2）
- **业务层**：Spring
- **持久层**：MyBatis

> 组合演变：SSH（struts2 + spring + hibernate） → SSM（springmvc + spring + mybatis） → Spring Boot + MyBatis-Plus

---

### 其他重要知识点

#### 对象存储服务（OSS, Object Storage Service）
- 存储非结构化数据（图片/视频/音频/文档）
- 平台：阿里云OSS、腾讯云OSS、华为云OSS、七牛云、MinIO（本地）
- 特点：海量存储（PB/EB级）、高可靠性（多副本）、安全可靠、灵活访问
- 结构化数据 → MySQL，非结构化 → OSS
- 底层：IO流

#### 文件上传
- 前端必须使用 POST 提交
- `Content-Type: multipart/form-data`
- 工具：POI、EasyExcel、Hutool

#### 全局异常处理
- 三层架构：底层不处理，层层向上抛
- 编译时异常：抓（try-catch）/ 抛（throws）
- 运行时异常：等它抛，抛了再修改代码

#### 热部署（Hot Deployment）
- 修改代码后无需重启 Tomcat 服务器

#### 枚举（Enum）
- 将静态常量对象化，将有穷集合的数据逐一罗列

#### Properties 集合
- Map 体系子集，K-V 都是字符串
- 常用于数据库配置文件 `db.properties`

#### MVC vs MVVM
- **MVC**（Model-View-Controller）：后端软件架构模式
- **MVVM**（Model-View-ViewModel）：前端框架架构模式（如 Vue），核心是 Model 和 View 的双向绑定

---

> 整理时间：2026-05-29
