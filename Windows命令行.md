# Windows 命令行

## 目录与文件管理

| 命令 | 功能 | 示例 |
|------|------|------|
| `D:` | 切换盘符 | `D:` 切换到 D 盘 |
| `cd 目录名` | 进入目录 | `cd project` |
| `cd ..` | 回退上级 | `cd ..` |
| `cd \` | 回到根目录 | `cd \` |
| `dir` | 列出当前目录内容 | `dir` |
| `md 文件夹名` | 创建文件夹 | `md newFolder` |
| `rd 文件夹名` | 删除空文件夹 | `rd oldFolder` |
| `del 文件名` | 删除文件 | `del test.txt` |
| `cls` | 清屏 | `cls` |

### cd 常用组合

```
cd \              → 回当前盘符根目录
cd ..             → 上一级
cd 目录1\目录2    → 进入多级目录
```

### dir 常用参数

```
dir /p            → 分页显示
dir /w            → 宽列表格式
dir *.java        → 只列出 .java 文件
```

---

## 系统进程与网络

| 命令 | 功能 | 示例 |
|------|------|------|
| `tasklist` | 查看所有运行进程 | `tasklist` |
| `taskkill` | 强制终止进程 | `taskkill /im notepad.exe` |
| `ipconfig` | 查看本机 IP 配置 | `ipconfig` / `ipconfig /all` |
| `ping` | 测试网络连通性 | `ping 192.168.1.1` |

### 常用参数

```
taskkill /im 进程名.exe         → 按名称终止
taskkill /pid 1234              → 按 PID 终止
taskkill /f /im notepad.exe     → 强制终止（/f）

ipconfig /all                   → 完整网络信息
ipconfig /flushdns              → 刷新 DNS 缓存

ping -t 目标IP                  → 持续 ping（Ctrl+C 停止）
ping -n 5 目标IP                → 发送 5 次请求
```

---

## 相关笔记

- [[Linux]] — Linux 命令行对比
- [[JAVA/Java-基础语法]] — 核心 Java 命令（javac、java、jar）
