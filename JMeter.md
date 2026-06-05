# JMeter 性能测试笔记

---

## 一、JMeter 是什么

JMeter 是 Apache 出品的开源性能测试工具，纯 Java 编写，主要用于：
- **接口压力测试**：模拟高并发请求
- **性能基准测试**：测量 API 吞吐量和响应时间
- **负载测试**：观察系统在不同负载下的表现
- **稳定性测试**：长时间运行看是否内存泄漏、响应变慢

---

## 二、安装与环境

### 2.1 前提
- JDK 8+（建议 JDK 11 或 17）
- 配置好 `JAVA_HOME` 和 `PATH`

### 2.2 安装
```bash
# 官网下载 zip/tgz，解压即用
wget https://dlcdn.apache.org/jmeter/binaries/apache-jmeter-5.6.3.tgz
tar -xzf apache-jmeter-5.6.3.tgz
cd apache-jmeter-5.6.3/bin
./jmeter    # 启动 GUI
```

### 2.3 目录结构
```
apache-jmeter/
├── bin/           # 启动脚本、配置文件
│   ├── jmeter     # Linux/Mac 启动
│   ├── jmeter.bat # Windows 启动
│   ├── jmeter.properties  # 核心配置
│   └── user.properties    # 用户自定义配置（优先）
├── lib/           # 依赖 jar
├── lib/ext/       # 插件目录
└── extras/        # 额外工具
```

### 2.4 推荐插件（JMeter Plugins Manager）
```bash
# 下载 plugins-manager.jar 放入 lib/ext/，然后在 GUI 中安装：
# - 3 Basic Graphs        （响应时间、吞吐量、线程数图形）
# - PerfMon               （监控服务器 CPU/内存）
# - Throughput Shaping Timer （精确控制吞吐量曲线）
# - Custom Thread Groups   （更灵活的线程组）
```

---

## 三、核心概念

### 3.1 测试计划 (Test Plan)
整个测试的顶层容器，所有东西都在它下面。可在此设置全局变量、运行模式（串行/并行）。

### 3.2 线程组 (Thread Group)
模拟虚拟用户，核心参数：

| 参数 | 说明 | 示例 |
|------|------|------|
| Number of Threads | 并发用户数 | 100 |
| Ramp-up Period (s) | 启动全部线程的耗时 | 10（10秒内逐步加到100并发） |
| Loop Count | 循环次数 | Infinite（配合 Duration 使用） |
| Duration (s) | 运行时长 | 300（跑5分钟） |
| Startup Delay (s) | 延迟启动 | 5 |

**实践建议：**
- 压测起点：50并发 → 100 → 200 → 500 逐步爬坡
- Ramp-up 不要太短，给系统缓冲时间
- Loop Count 和 Duration 互斥，一般用 Duration + Infinite

### 3.3 采样器 (Sampler)
向服务器发请求的组件。常用：

| Sampler | 用途 |
|---------|------|
| HTTP Request | HTTP/HTTPS 接口请求 |
| JDBC Request | 数据库压测 |
| JMS Publisher | 消息队列压测 |
| TCP Sampler | Socket/TCP 协议 |

### 3.4 配置元件 (Config Element)
为 Sampler 提供数据支撑，作用域按树形结构继承。

- **HTTP Header Manager** → 设置 Content-Type、Authorization 等
- **HTTP Cookie Manager** → 自动管理 Session/Cookie
- **CSV Data Set Config** → 参数化，从 CSV 读测试数据
- **User Defined Variables** → 定义全局变量
- **HTTP Request Defaults** → 设置统一的协议、IP、端口

### 3.5 断言 (Assertion)
判断响应是否正确，失败则标记为错误。

- **Response Assertion** → 检查响应是否包含某文本
- **JSON Assertion** → 检查 JSON 字段值（用 JSONPath）
- **Duration Assertion** → 响应超过 N 毫秒即为失败
- **Size Assertion** → 响应体大小检查

### 3.6 定时器 (Timer)
控制请求间隔。

- **Constant Timer** → 固定等待（太死板，少用）
- **Uniform Random Timer** → 随机等待（更真实）
- **Gaussian Random Timer** → 正态分布随机等待
- **Constant Throughput Timer** → 控制每分钟请求数
- **Synchronizing Timer** → 集合点，同时爆发请求

### 3.7 监听器 (Listener)
看结果、出报告。**注意：GUI 模式下 Listener 很耗资源，压测时应该禁用，用 CLI 生成报告。**

- **View Results Tree** → 调试用，看每次请求详情
- **Aggregate Report** → 平均值、中位数、90%/95%/99% 线、吞吐量、错误率
- **Summary Report** → 简版聚合报告
- **Generate Summary Results** → 输出到 jmeter.log 的周期性统计

### 3.8 逻辑控制器 (Logic Controller)
控制 Sampler 执行顺序。

- **Simple Controller** → 纯分组，无逻辑
- **Loop Controller** → 循环执行子组件
- **If Controller** → 条件判断
- **Transaction Controller** → 把多个请求算作一个事务，统计总响应时间
- **Throughput Controller** → 按百分比分配请求量

---

## 四、工作中完整的测试流程

### ═══ 第一步：明确测试目标 ═══

先和开发/产品确认：
1. **被测接口有哪些** — 接口文档/Swagger
2. **期望 QPS/TPS 是多少** — "秒杀场景 5000 QPS"
3. **响应时间要求** — "P99 < 200ms"
4. **错误率要求** — "错误率 < 0.1%"
5. **测试环境配置** — 几核几G，和线上差异多大
6. **是否需要参数化** — 不同用户、不同商品 ID

### ═══ 第二步：编写测试脚本 ═══

**场景示例：测试一个登录 + 查询订单列表的流程**

```
Test Plan
├── User Defined Variables（定义 base_url, port 等）
├── HTTP Request Defaults（协议、IP、端口、编码统一设置）
├── HTTP Header Manager（Content-Type: application/json）
├── HTTP Cookie Manager
│
├── setUp Thread Group（登录、准备数据等一次性操作）
│   └── HTTP Request（POST /api/login）
│       ├── JSON Body: {"username":"${user}","password":"${pwd}"}
│       └── JSON Extractor（提取 token 存为变量）
│
├── Thread Group（主压测线程组）
│   ├── CSV Data Set Config（读取 users.csv，提供 user/pwd 参数）
│   ├── HTTP Request（GET /api/orders?page=1&size=20）
│   │   └── HTTP Header Manager（Authorization: Bearer ${token}）
│   ├── JSON Assertion（检查 $.code == 0）
│   ├── Duration Assertion（> 1000ms 标记失败）
│   └── Constant Throughput Timer（控制目标 QPS）
│
├── tearDown Thread Group（清理数据）
│   └── HTTP Request（POST /api/logout）
│
└── View Results Tree（仅调试时启用）
```

### ═══ 第三步：调试脚本（GUI 模式）═══

```bash
# 启动 GUI
./jmeter
```

1. 线程数设 **1**，循环 1 次
2. 打开 **View Results Tree**
3. 运行，逐条检查请求/响应是否正常
4. 断言是否通过
5. 变量是否正确传递（用 Debug Sampler + View Results Tree 检查）
6. 调通后**禁用 View Results Tree**，保存 `.jmx` 文件

> **关键原则：GUI 只用于调试，绝不用于正式压测。**

### ═══ 第四步：执行压测（CLI 模式）═══

```bash
# 基本压测命令
jmeter -n -t test_plan.jmx -l result.jtl -e -o report/

# 参数说明：
# -n          非 GUI 模式
# -t          指定 .jmx 测试计划
# -l          结果输出文件（.jtl 或 .csv）
# -e          生成 HTML 报告
# -o          报告输出目录（必须为空或不存在）
# -j          指定日志文件
# -Jprop=val  传递 Java 系统属性（配合 __P() 函数使用）

# 常用示例
jmeter -n \
  -t order-api-test.jmx \
  -l result_$(date +%Y%m%d_%H%M%S).jtl \
  -e -o report_$(date +%Y%m%d_%H%M%S)/ \
  -Jthreads=100 \
  -Jduration=300 \
  -Jrampup=30
```

### ═══ 第五步：脚本参数化（动态传参）═══

在 .jmx 中用 `${__P(threads, 50)}` 代替固定值，命令行就能覆盖：

| 函数 | 说明 | 示例 |
|------|------|------|
| `${__P(threads, 50)}` | 读 Java 属性，默认50 | `-Jthreads=200` |
| `${__property(threads, 50)}` | 同上，旧版语法 | |
| `${__time(yyyy-MM-dd HH:mm:ss)}` | 当前时间戳 | 用于 Body 中去重 |
| `${__RandomString(10, abcdef123)}` | 随机字符串 | |
| `${__Random(1, 1000)}` | 随机数 | |
| `${__UUID()}` | UUID | |
| `${__threadNum}` | 线程编号 | 第几个并发用户 |
| `${__counter(FALSE,)}` | 全局递增计数器 | |

### ═══ 第六步：分布式压测（单机压不上去时）═══

```
Master 机器：控制端，发送指令
Slave 机器：执行端，实际发请求（多台）

架构：
  Master (JMeter GUI/CLI)
    ├── Slave-1 (jmeter-server)
    ├── Slave-2 (jmeter-server)
    └── Slave-3 (jmeter-server)
```

配置步骤：
```bash
# 1. 每台 Slave 启动 jmeter-server
./jmeter-server

# 2. Master 端修改 jmeter.properties
remote_hosts=192.168.1.10:1099,192.168.1.11:1099,192.168.1.12:1099

# 3. Master 端执行
jmeter -n -t test.jmx -r -l result.jtl -e -o report/
# -r 表示启动所有 remote_hosts

# 或者指定部分 slave
jmeter -n -t test.jmx -R 192.168.1.10:1099,192.168.1.11:1099 -l result.jtl
```

**注意事项：**
- 所有机器 JMeter 版本一致
- 所有机器在同一网段，禁用防火墙
- CSV 数据文件需要在每台 Slave 的相同路径下
- 如果能用局域网，不要用云主机，网络延迟影响结果

---

## 五、如何分析结果

### 5.1 命令行实时输出
运行时 JMeter 每 30 秒在命令行输出一行统计：
```
summary +   1234 in 00:00:30 =   41.1/s Avg:   243 Min:    12 Max:  1892 Err:     0 (0.00%)
summary =  45678 in 00:05:00 =  152.3/s Avg:   256 Min:     8 Max:  2341 Err:     5 (0.01%)
summary +   1150 in 00:00:30 =   38.3/s Avg:   267 Min:    15 Max:  2103 Err:     2 (0.17%)
```

关注：**Err 数量、Avg 响应时间趋势、吞吐量趋势**。如果 Avg 和 Err 同时上升 → 系统已到瓶颈。

### 5.2 HTML 报告（最常用）

`-e -o report/` 生成的 HTML 报告，关键图表：

| 图表 | 看什么 |
|------|--------|
| **Response Times Over Time** | 响应时间随时间变化趋势 |
| **Active Threads Over Time** | 并发数变化 |
| **Response Time Percentiles** | P90/P95/P99 延迟 |
| **Transactions Per Second** | 实际 TPS |
| **Response Codes Per Second** | 各状态码数量（2xx/4xx/5xx） |
| **Latencies Over Time** | 网络延迟趋势 |
| **Connect Time Over Time** | 建立连接耗时 |

### 5.3 核心指标解读

| 指标 | 含义 | 怎么判断 |
|------|------|----------|
| **Average** | 平均响应时间 | 参考值，易被极值拉高 |
| **Median** | P50，一半请求都低于此 | 比 Average 更能反映真实体验 |
| **P90 / 95 Line** | 90%/95% 请求在此之下 | 关键！太长说明少数请求卡顿 |
| **P99 Line** | 99% 请求在此之下 | 线上 SLA 通常看这个 |
| **Min / Max** | 最快/最慢 | 看波动范围 |
| **Throughput** | 每秒处理请求数 (TPS/QPS) | 系统吞吐量核心指标 |
| **Error %** | 错误率 | 必须接近 0 |
| **Received KB/sec** | 带宽占用 | 关注是否打满网卡 |
| **Std. Dev** | 标准差 | 越大越不稳定 |

### 5.4 瓶颈定位思路

压测过程中，同时监控服务器：

```
响应变慢 + CPU 高 + 错误率低  → 计算瓶颈，加机器或优化代码
响应变慢 + CPU 低 + 错误率高  → 连接池/线程池耗尽，检查数据库连接数
响应变慢 + 磁盘 IO 高         → 日志太多或磁盘瓶颈
响应正常 + TPS 上不去          → 带宽打满或连接数不够
响应变慢 + GC 频繁             → 内存不够，堆设置太小
```

---

## 六、常见场景模板

### 6.1 接口基准测试（单接口，逐步加压）

```
Thread Group:
  Threads: ${__P(threads, 10)}
  Ramp-up: 10s
  Duration: 300s
  
HTTP Request Defaults → 统一配置 host/port

只用 1 个 HTTP Request Sampler
加 Duration Assertion (根据 SLA 设阈值)
加 Response Assertion (确保返回正确)
```

### 6.2 混合场景（模拟真实流量）

```
Thread Group（主）
├── Throughput Controller (80%)  → 浏览商品（GET /api/products）
├── Throughput Controller (15%)  → 搜索（GET /api/search?q=xxx）
├── Throughput Controller (4%)   → 加购物车（POST /api/cart）
└── Throughput Controller (1%)   → 下单（POST /api/orders）

用 Throughput Shaping Timer 控制各阶段 QPS 梯度
```

### 6.3 登录认证场景

```
setUp Thread Group:
  Login Request → JSON Extractor 提取 token
  token 存为属性：${__setProperty(token, ${token})}

主 Thread Group:
  HTTP Header Manager 中引用：Authorization: Bearer ${__property(token)}
```

### 6.4 参数化数据驱动

```
CSV Data Set Config:
  Filename: test_users.csv
  Variable Names: username,password,role
  Delimiter: ,
  Recycle on EOF: True    ← 数据用完循环
  Stop Thread on EOF: False
  Sharing Mode: All threads  ← 所有线程共享
```

test_users.csv 内容：
```
user001,pass123,admin
user002,pass456,normal
user003,pass789,normal
```

---

## 七、避坑指南 & 最佳实践

### 7.1 脚本编写
1. **GUI 只调试，压测用 CLI** — GUI 本身消耗大量 CPU/内存，影响结果准确性
2. **禁用 Listener 再压测** — View Results Tree 会把所有响应存内存，10 分钟就 OOM
3. **CSV 优于 .jmx 内的硬编码** — 改数据不用改脚本
4. **变量作用域** — 在哪个层级定义的变量，只对该层级及子级生效
5. **Regular Expression Extractor vs JSON Extractor** — JSON 响应优先用后者，更准

### 7.2 执行
1. **逐步加压，不要一把梭** — 先 50 并发 → 100 → 200 逐步爬坡，找到拐点
2. **预热** — 先跑 1-2 分钟不记录结果，让 JIT、连接池热起来
3. **多次跑取平均值** — 至少跑 3 次，排除网络抖动
4. **单机压不上去再考虑分布式** — 先看是不是本机 CPU/带宽先到了瓶颈
5. **不要在同一台机器上跑 JMeter 和被压测服务** — 资源竞争导致数据失真

### 7.3 环境
1. **测试环境配置尽量接近生产** — 至少数据库数据量要接近（或等比缩小）
2. **压测前通知相关人员** — 别把测试环境搞挂了被人找
3. **测试完成后恢复环境** — 清理测试数据
4. **关注监控** — 同时看 Grafana/Prometheus/应用日志，不要只看 JMeter 报告
5. **关闭不必要的日志** — 压测时应用端日志级别调到 WARN 以上

### 7.4 JMeter 调优

```properties
# bin/jmeter.properties 或 user.properties

# 加大堆内存（jmeter.bat/.sh 中设置）
HEAP="-Xms1g -Xmx4g -XX:MaxMetaspaceSize=256m"

# 禁用 GUI 下的图表刷新
jmeter.save.saveservice.autoflush=false

# 结果文件只保存必要字段
jmeter.save.saveservice.response_data=false
jmeter.save.saveservice.samplerData=false
jmeter.save.saveservice.assertion_results_failure_message=true

# 分布式模式下关闭 SSL
server.rmi.ssl.disable=true
```

### 7.5 断电续压

大脚本中途挂了，可以用已有 .jtl 文件继续生成报告：
```bash
jmeter -g result.jtl -o report/    # 只生成报告，不重跑测试
```

---

## 八、命令速查

```bash
# 启动 GUI
./jmeter

# 非 GUI 压测
jmeter -n -t test.jmx -l result.jtl

# 压测 + 生成报告
jmeter -n -t test.jmx -l result.jtl -e -o report/

# 传参压测
jmeter -n -t test.jmx -Jthreads=200 -Jduration=600 -l result.jtl -e -o report/

# 分布式压测（所有 slave）
jmeter -n -t test.jmx -r -l result.jtl -e -o report/

# 分布式压测（指定 slave）
jmeter -n -t test.jmx -R 192.168.1.10:1099 -l result.jtl -e -o report/

# 从已有结果生成报告
jmeter -g result.jtl -o report/

# 启动 slave 节点
./jmeter-server

# 查看版本
jmeter -v
```

---

## 九、总结：工作中拿到压测任务的 Checklist

```
□ 1. 明确目的（基准？瓶颈？容量规划？回归？）
□ 2. 拿到接口文档，确认关键接口和预期指标
□ 3. 确认测试环境配置（几核几G几节点）
□ 4. 编写 .jmx 脚本，1线程 1循环调通
□ 5. 检查断言是否生效，变量传参是否正确
□ 6. 准备参数化数据（CSV 文件）
□ 7. 关闭所有 Listener，保存脚本
□ 8. 小流量试跑（10并发 1分钟），确认无误
□ 9. 通知相关人员，开始正式压测
□ 10. 逐步加压，记录各阶段数据
□ 11. 观察服务器监控，定位瓶颈
□ 12. 生成 HTML 报告，输出测试结论
□ 13. 清理测试数据，恢复环境
```
