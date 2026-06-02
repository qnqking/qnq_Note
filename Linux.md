# Linux

1991 年 9 月 17 日，芬兰大学生 Linus Torvalds 开发。特点：免费、开源、类 Unix。

与 Windows 区别：① 收费与否 ② 安全性 ③ 稳定性 ④ 是否可定制化（开源）

划分：内核版 / 发行版 | 服务器选 RHEL/CentOS/Ubuntu LTS，桌面选 Ubuntu/Fedora

---

## 文件目录结构

**核心目录**：`/`（根）、`/bin`（系统必备命令）、`/sbin`（系统管理命令）、`/lib`（核心库文件）、`/boot`（启动文件）

**配置/用户**：`/etc`（全局配置）、`/home`（普通用户主目录）、`/root`（管理员主目录）

**设备/临时**：`/dev`（硬件设备文件）、`/tmp`（临时文件）、`/run`（运行时临时文件）

**存储/挂载**：`/mnt`（手动挂载）、`/media`（自动挂载）、`/opt`（第三方软件）

**虚拟文件**：`/proc`（进程实时信息）、`/sys`（内核设备树）

**数据/服务**：`/var`（可变数据：日志/邮件/数据库）、`/srv`（服务数据）、`/usr`（用户级应用程序）

**特殊目录**：`/lost+found`（文件系统修复后恢复的文件）

---

## 文件颜色标识

- 白色 = 普通文件，蓝色 = 目录，绿色 = 可执行文件
- 红色 = 压缩文件，浅蓝色 = 链接文件，黄色 = 设备文件，灰色 = 其它文件

> Linux 严格区分大小写；不依赖扩展名区分文件类型，依赖权限位控制

---

## 文件目录操作命令

| 命令 | 作用 |
|------|------|
| `cd .` / `cd ..` / `cd ~` | 进入当前/上级/家目录 |
| `pwd` | 查看当前工作目录 |
| `clear` / `Ctrl+L` | 清屏 |
| `ls -l -h -a` | 查看目录文件（长格式/人性化/含隐藏） |
| `history` | 查看历史记录 |
| `type` | 查看命令的类型 |
| `mkdir -p` | 创建目录（递归） |
| `rm -r -f` | 删除（递归/强制） |
| `cp -r` | 复制 |
| `mv` | 移动/重命名 |
| `touch` | 创建空文件 |
| `wc -l -w -c` | 统计行数/单词数/字节数 |

---

## 关机重启

| 命令 | 作用 |
|------|------|
| `shutdown -h 5` | 5 分钟后关机 |
| `poweroff` | 立即关机 |
| `reboot` | 立即重启 |

---

## 文件查看命令

| 命令 | 特点 |
|------|------|
| `cat`（正序）/ `tac`（倒序） | 一次性输出全部内容 |
| `more` | 分页查看，空格翻页，回车逐行，q 退出 |
| `less` | 分页查看，pgUp/pgDn 翻页，支持搜索 |
| `head -n` | 查看文件前 n 行（默认 10） |
| `tail -n` | 查看文件后 n 行（默认 10） |

---

## 文件查找与内容分析

```bash
find 查找范围 [选项]
  -name 按名称  -type f/d 按类型  -size +5M 按大小  -mtime +5 按时间

grep 关键字 [选项] 文件
  -i 忽略大小写  -n 显示行号  -c 统计次数  -v 反向过滤
```

### 管道符 `|`

```bash
ls /dev/ | grep 'cpu'
```

### 重定向

- `>` 覆盖式存储
- `>>` 追加式存储

---

## 压缩 & 解压

### tar

```bash
tar -cvf abc.tar a.txt b.txt c.txt     # 打包
tar -zcvf abc.tar.gz a.txt             # 压缩（-z gz格式）
tar -zxvf abc.tar.gz                   # 解压
tar -zxvf abc.tar.gz -C /usr           # 解压到指定目录
```

### zip / unzip

```bash
zip -r tmp.zip /tmp/                   # 压缩目录
unzip abc.zip -d /tmp                  # 解压到指定目录
```

---

## VIM 编辑器

### 命令模式

- `i` 光标前插入 / `a` 光标后插入 / `o` 下一行插入
- `dd` 删除当前行 / `5dd` 删除 5 行
- `yy` + `p` 复制粘贴 / `5yy` 复制 5 行
- `u` 撤销 / `gg` 首行 / `G` 最后一行 / `^` 行首 / `$` 行末

### 编辑模式

- `:w` 保存 / `:q` 退出 / `:wq` 保存并退出 / `:q!` 强制退出
- `:set nu` 显示行号 / `:set nonu` 取消行号
- `/字符串` 从上至下搜索，`n` 向下 / `N` 向上
- `:s/one/two/g` 当前行全部替换

### 异常处理

```bash
vim -r 文件名           # 恢复
rm -rf .xxx.swp         # 删除交换文件
```

---

## 权限系统

### ll 输出解读：`drwxr-xr-x`（共 10 位）

- 第 1 位：d=目录 / -=普通文件 / l=符号链接 / b=块设备 / c=字符设备
- 第 2-4 位：拥有者权限 u（user）
- 第 5-7 位：用户组权限 g（group）
- 第 8-10 位：其他人权限 o（other）

| 权限 | 数字 | 对文件 | 对目录 |
|------|------|--------|--------|
| r（读） | 4 | 查看文件内容 | 查看目录中文件列表 |
| w（写） | 2 | 修改文件内容 | 创建/修改/删除目录中文件 |
| x（执行） | 1 | 运行文件 | 进入目录 |
| - | 0 | 无权限 | 无权限 |

```bash
chmod -R 700 sc          # 数字法（rwx=7, r-x=5, r--=4）
chmod u=rwx,g=rx,o=---   # 字母法
chmod u-x,g-r            # 加减法
chown userli:develop sc  # 改变拥有者和所属组
chown -R userli sc       # 递归修改目录
```

---

## 用户 & 用户组

```bash
groupadd -g GID 组名              # 添加用户组
gpasswd -a 用户名 组名            # 添加用户到组
useradd -u -g -G -d -s 用户名     # 添加用户
passwd 用户名                     # 修改密码
usermod -L/-U 用户                # 锁定/解锁
userdel -r -f 用户名              # 删除用户
su 用户名                         # 切换账号
```

**用户分类**：超级用户 root、系统用户（`/sbin/nologin`）、普通用户（`/bin/bash`）

---

## 软件安装

```bash
# RPM 离线安装
rpm -ivh 软件包.rpm           # 安装

# YUM 在线安装
yum install -y 软件名          # 安装
yum remove -y 软件名           # 卸载
rpm -ql 软件名                 # 查看安装位置
```

---

## 进程与系统管理

```bash
ps aux                        # 查看所有进程
ps -ef | grep java
kill -9 PID                   # 强制终止
top                           # 系统负载
systemctl start/stop/restart 服务名
systemctl enable/disable 服务名# 开启/取消开机自启
```

### 常用排查命令

`df -h`（磁盘）、`free -h`（内存）、`netstat -tlnp`（端口）、`tail -f`（日志）

---

## Shell 脚本基础

```bash
#!/bin/bash
NAME="hello"
if [ -f "/path/file" ]; then echo "文件存在"; fi
for i in {1..5}; do echo $i; done
```

---

## 相关笔记

- [[计算机网络]] — TCP/IP、HTTP 协议
- [[Docker]] — 容器化部署
- [[Kubernetes]] — 容器编排
