# Spring Cloud 微服务

> Spring Cloud 是一套分布式系统解决方案，基于 Spring Boot 构建。核心解决的是一台服务器变多台后，随之而来的服务发现、配置管理、负载均衡、熔断降级、网关路由、分布式追踪等问题。

---

## 一、架构演进：单体 → 微服务

### 单体架构

```
浏览器 → Nginx → 一台 Tomcat（所有业务代码打成一个 WAR 包）→ 一个 MySQL
```

缺点：
- 代码耦合严重，一处改处处测
- 部署慢，哪怕只改一行也得全量打包
- 扩展难，无法针对热点模块单独扩容
- 技术栈锁定，整个项目必须用同一种语言/框架

### 微服务架构

```
         ┌──────────┐
         │  Gateway  │  统一入口，路由分发
         └────┬─────┘
    ┌─────────┼─────────┐
    ▼         ▼         ▼
 订单服务   用户服务   商品服务    ← 各自独立部署、独立数据库
    │         │         │
    └─────────┼─────────┘
              ▼
      Nacos（注册中心 + 配置中心）
```

优点：
- 单一职责，每个服务只做一件事
- 独立部署，改订单只部署订单服务
- 独立扩容，哪个服务压力大就加哪个
- 技术异构，订单用 Java、推荐用 Python 都可以

代价：
- 网络调用不可靠（原本方法调用变成 RPC/HTTP）
- 分布式事务（原本一个 connection.commit() 现在跨多个数据库）
- 运维复杂度飙升

**Spring Cloud 就是来解决这些代价的。**

---

## 二、Spring Cloud 全家桶

| 组件 | 作用 | 阿里替代（推荐） |
|------|------|-----------------|
| Eureka | 服务注册与发现 | **Nacos** |
| Ribbon | 负载均衡 | **Spring Cloud LoadBalancer** |
| Hystrix | 熔断降级 | **Sentinel** |
| Feign | 声明式 HTTP 调用 | **OpenFeign** |
| Zuul | API 网关（阻塞式） | **Gateway（WebFlux，非阻塞）** |
| Config | 配置中心 | **Nacos Config** |
| Bus | 消息总线 | Nacos 自带配置刷新 |
| Sleuth + Zipkin | 分布式链路追踪 | Micrometer Tracing |

> 2020 年起 Netflix 系列（Eureka、Hystrix、Zuul、Ribbon）陆续进入维护模式。当前主流：**Spring Cloud Alibaba**（Nacos + Sentinel）。

---

## 三、版本对应关系

Spring Cloud 以伦敦地铁站命名：

| Spring Cloud | Spring Boot | Spring Cloud Alibaba |
|-------------|-------------|---------------------|
| Hoxton.SR12 | 2.3.x | 2.2.x |
| 2020.0.x | 2.4.x / 2.5.x | 2021.1 |
| 2021.0.x | 2.6.x / 2.7.x | 2021.0.x |
| 2022.0.x | 3.0.x | 2022.0.x |
| 2023.0.x | 3.1.x / 3.2.x | 2023.0.x |

> 必须严格对应版本，否则各种兼容性问题。

---

## 四、服务注册与发现（Nacos）

### 没有注册中心时

```
订单服务调用用户服务，直接在 application.yml 里写死 URL：
user-service.url: http://192.168.1.100:8081

问题：
- 用户服务扩容到 3 台 → 订单服务不知道，还是只调那 1 台
- 用户服务 IP 变了 → 改配置 + 重启订单服务
- 用户服务挂了 → 订单服务傻等超时
```

### 有了注册中心后

```
1. 用户服务启动 → 向 Nacos 注册："我叫 user-service，IP=1.1.1.1，端口=8081"
2. 订单服务调用用户服务 → 去 Nacos 查："user-service 哪里有？"
3. Nacos 返回："1.1.1.1:8081, 1.1.1.2:8081, 1.1.1.3:8081（3 台都健康）"
4. 订单服务选一台调用
5. 用户服务每 5 秒发送心跳 → Nacos 如果 15 秒没收到心跳，把该实例标记下线
```

### 核心概念

| 概念 | 说明 |
|------|------|
| Namespace | 命名空间，实现多环境隔离（dev / test / prod） |
| Group | 分组，同一环境内进一步细分 |
| Service（服务名） | 微服务名称，如 order-service |
| Instance（实例） | 服务名下的具体 IP:Port，同一服务名下可以有多个实例 |
| 临时实例 | 默认方式，靠心跳续约，断开心跳后自动剔除 |
| 持久实例 | 即使断开心跳，Nacos 也不剔除，适用于基础服务 |

### 服务端启动

```bash
# 单机模式启动（开发环境）
startup.cmd -m standalone

# 访问控制台
http://localhost:8848/nacos
默认账号密码：nacos/nacos
```

### 客户端接入

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

```yaml
# application.yml
spring:
  application:
    name: order-service          # 注册到 Nacos 的服务名
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848   # Nacos 地址
        namespace: dev                 # 命名空间 ID
        group: ORDER_GROUP             # 分组
```

```java
// 启动类
@SpringBootApplication
@EnableDiscoveryClient               // 开启服务发现（新版可省略）
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
```

### CAP 模式切换

Nacos 支持 AP 和 CP 切换（这是一个很大优势）：
- **AP 模式**（默认，临时实例）：优先保证可用性，允许短暂不一致。适合普通业务服务。
- **CP 模式**（持久实例）：优先保证一致性，Leader 选举期间不可用。适合支付、库存等一致性要求高的服务。

---

## 五、负载均衡（Spring Cloud LoadBalancer）

### 为什么需要负载均衡

注册中心返回了 user-service 的 3 台地址，选哪一个？不能让订单服务自己随机选。

### Ribbon 工作原理

```
1. 拦截 RestTemplate 的 HTTP 请求
2. 从请求 URL 中提取服务名（user-service）
3. 去注册中心查 user-service 对应的所有 IP:Port
4. 根据负载均衡规则选一台
5. 把 URL 中的 user-service 替换为选中的 IP:Port
6. 发起真实 HTTP 调用
```

### LoadBalancer（Ribbon 替代）

Spring Cloud 2020.x 开始，Ribbon 进入维护模式，官方推荐 **Spring Cloud LoadBalancer**。

```yaml
# 切换负载均衡算法（默认轮询）
spring:
  cloud:
    loadbalancer:
      ribbon:
        enabled: false    # 禁用 Ribbon（新版默认）
```

```java
// 自定义负载均衡策略
@Bean
public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(
        Environment env, LoadBalancerClientFactory factory) {
    String name = env.getProperty("loadbalancer.client.name");
    return new RandomLoadBalancer(
            factory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
}
```

### 常见负载均衡算法

| 算法 | 说明 |
|------|------|
| 轮询（Round Robin） | 逐个分配，人人有份 |
| 加权轮询（Weighted） | 性能好的机器多分配 |
| 随机（Random） | 随机选一台 |
| 最少连接（Least Connections） | 谁连接少选谁 |
| 一致性哈希（Consistent Hash） | 相同参数请求打到同一台 |
| 区域感知（Zone Avoidance） | 优先选就近区域，避开故障区 |

---

## 六、HTTP 远程调用（OpenFeign）

### 没有 Feign 时

```java
// 手动拼接 URL + 手动序列化/反序列化
String url = "http://user-service/api/users/" + userId;
User user = restTemplate.getForObject(url, User.class);
```

每个远程调用都要写这种样板代码：拼 URL、传参、解析响应、错误处理。重复、容易出错、不好维护。

### 有了 Feign 后

Feign = 声明式 HTTP 客户端，把远程调用伪装成本地方法调用：

```java
// 定义一个接口，声明想调什么
@FeignClient(name = "user-service")         // 服务名，Feign 会自动去注册中心找它的地址
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    User getById(@PathVariable Long id);     // 写法跟 Spring MVC 一模一样

    @PostMapping("/api/users")
    Result<User> create(@RequestBody User user);

    @GetMapping("/api/users/search")
    List<User> search(@RequestParam String name);
}
```

```java
// 使用时就像调用本地方法一样
@Service
public class OrderService {

    @Autowired
    private UserFeignClient userClient;    // 注入 Feign 生成的代理对象

    public OrderDetailDTO getOrderDetail(Long orderId) {
        Order order = getById(orderId);
        User user = userClient.getById(order.getUserId());  // 远程调用，但写起来像本地调用
        return new OrderDetailDTO(order, user);
    }
}
```

### Feign 底层原理

```
1. @FeignClient 声明接口
2. Spring 启动时扫描到 @FeignClient
3. Feign 用 JDK 动态代理生成接口的实现类
4. 当调用 userClient.getById() 时，实际被代理拦截
5. 代理根据 @GetMapping 里的路径 + 参数拼接出完整 HTTP 请求
6. 使用底层 HTTP 客户端（默认 URLConnection，可换成 OkHttp/Apache HttpClient）发送请求
7. 收到 JSON 响应，自动反序列化成 User 对象返回
```

### Feign 配置

```yaml
spring:
  cloud:
    openfeign:
      client:
        config:
          default:                      # 全局配置
            connect-timeout: 2000        # 连接超时 2 秒
            read-timeout: 5000           # 读取超时 5 秒
            logger-level: FULL           # 日志级别
          user-service:                  # 单独对某个服务配置
            connect-timeout: 1000
            read-timeout: 3000
```

```java
// 配置底层 HTTP 客户端为 Apache HttpClient（性能更好）
// 引入依赖：feign-httpclient
// 然后在 yml 中开启：
spring.cloud.openfeign.httpclient.enabled: true
```

### Feign 拦截器

统一处理认证 Token 传递，不用每个接口都手动传：

```java
@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 从当前请求上下文获取 Token
        String token = UserContext.getToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}
```

**服务之间调用时，Token 可以自动透传。**

---

## 七、配置中心（Nacos Config）

### 为什么需要配置中心

```
场景：100 个微服务，数据库地址从 192.168.1.100 换成 192.168.1.200

没有配置中心：改 100 个 application.yml，提交 100 次，重新部署 100 个服务
有配置中心：在 Nacos 控制台改一次，100 个服务自动刷新
```

### Nacos Config 隔离层级

```
Namespace（环境隔离，比如 dev / prod）
  └── Group（业务分组，比如 ORDER_GROUP / USER_GROUP）
        └── DataID（配置项唯一标识）
              └── 具体的键值对
```

### DataID 命名规则

```
完整格式：${prefix}-${spring.profiles.active}.${file-extension}

实例：order-service-dev.yaml
       ↑prefix       ↑profile ↑extension
```

### 核心功能

#### 1. 热更新（配置自动刷新）

方式一：`@RefreshScope` + `@Value`
```java
@RestController
@RefreshScope                         // 配置更新时自动重新创建这个 Bean
public class ConfigController {

    @Value("${app.timeout:3000}")      // 配置项，默认值 3000
    private String timeout;

    @GetMapping("/config/timeout")
    public String getTimeout() {
        return timeout;
    }
}
```

方式二：`@ConfigurationProperties`（推荐，类型安全）
```java
@Component
@Data
@ConfigurationProperties(prefix = "app")
@RefreshScope
public class AppConfig {
    private String timeout;
    private String version;
}
```

#### 2. 共享配置

多个微服务有相同的配置（如公共数据库密码），可以抽出为共享配置：

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        # 加载多个配置
        shared-configs:
          - data-id: common-db.yaml
            group: DEFAULT_GROUP
            refresh: true
          - data-id: common-redis.yaml
            group: DEFAULT_GROUP
```

#### 3. 扩展配置

```yaml
extension-configs:
  - data-id: extra-feature.yaml
    group: FEATURE_GROUP
```

### 配置优先级（从高到低）

```text
当前应用 profile 配置 > 当前应用默认配置 > 扩展配置 > 共享配置
也就是越精准的配置越先使用。
```

### Bootstrap vs Application

Spring Boot 2.4+ 废弃了 `bootstrap.yml`，改为统一的 `application.yml`：

```yaml
# application.yml
spring:
  application:
    name: order-service
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        namespace: dev
        file-extension: yaml
        group: ORDER_GROUP
  config:
    import: nacos:order-service-dev.yaml   # 引入 Nacos 配置
```

如果习惯写 `bootstrap.yml`，需引入 `spring-cloud-starter-bootstrap` 依赖。

---

## 八、服务熔断与限流（Sentinel）

### 为什么需要熔断

```
订单服务 → 用户服务 → 数据库

用户服务因为慢 SQL 变得很慢，每个请求 10 秒才响应。
订单服务一直在等用户服务返回，线程越积越多。
最终：用户服务挂了，连带着订单服务也挂了。
```

这就像连锁倒闭：
- 用户服务出问题
- 调用它的订单服务线程耗尽、CPU 打满
- 订单服务也出问题
- 调用订单服务的网关超时
- 整个系统崩溃

**必须熔断**：下游服务异常，上游立即切断调用链路，不再等待，保护自身。

### Sentinel vs Hystrix

| 对比项 | Sentinel | Hystrix |
|--------|----------|---------|
| 作者 | 阿里巴巴 | Netflix |
| 状态 | 活跃维护 | 停更，维护模式 |
| 隔离策略 | 信号量（线程数控制） | 线程池/信号量 |
| 熔断降级 | 支持平均响应时间、异常比例、异常数 | 线程池满/异常比例 |
| 实时监控 | 自带 Dashboard | 需整合 Turbine |
| 规则推送 | 控制台 → 配置中心 → 应用，规则持久化 | 仅内存，重启丢失 |
| 限流 | QPS / 并发线程数 / 调用关系链路限流 | 不支持 |

### 核心概念

| 概念 | 说明 |
|------|------|
| 资源（Resource） | 被 Sentinel 保护的一段代码，可以是一个方法或接口 |
| 规则（Rule） | 围绕资源做控制，包括流控、熔断、热点、系统等规则 |
| 流控 | 限制每秒通过多少请求（QPS）或并发线程数 |
| 熔断降级 | 响应时间过长或异常比例过高时，直接返回兜底结果 |
| 热点限流 | 对访问频率高的参数单独限流（如热门商品 ID） |

### Sentinel Dashboard

```bash
# 下载 sentinel-dashboard.jar，启动
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -jar sentinel-dashboard.jar

# 访问控制台
http://localhost:8080
默认账号密码：sentinel/sentinel
```

### 客户端接入

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

```yaml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8080   # 控制台地址
        port: 8719                   # 与控制台通信的端口
      eager: true                    # 项目启动立即注册到控制台
```

### 三类规则详解

#### 1. 流控规则（Flow Control）

```
场景：秒杀活动，某个接口 QPS 不能超过 50

规则：对资源 "/api/seckill/submit"，QPS 阈值 50
效果：超过 50 的请求直接返回 "系统繁忙，请稍后再试"
```

**流控模式**：
- **直接**：限流当前资源本身
- **关联**：关联资源达到阈值时，限流自己。例如：修改订单接口 QPS 过高，就限流查询订单接口，保证修改优先
- **链路**：指定从哪个入口进来的请求才限流

**流控效果**：
- **快速失败**：超阈值直接拒绝
- **Warm Up**：预热，从低阈值逐步升到设定阈值（防冷启动流量打崩）
- **排队等待**：请求排队，匀速通过（类似漏桶算法）

#### 2. 熔断降级规则（Circuit Breaking）

| 策略 | 说明 | 场景 |
|------|------|------|
| 慢调用比例 | 响应时间超过阈值（如 200ms）的比例超过设定值 → 熔断 | 数据库慢查询 |
| 异常比例 | 异常数占总请求数的比例超过设定值 → 熔断 | 第三方接口频繁报错 |
| 异常数 | 1 分钟内异常数超过设定值 → 熔断 | 快速发现故障 |

**熔断状态机**：

```
CLOSED（关闭，正常状态，统计请求）
    │  当异常比例超过阈值
    ▼
OPEN（打开，熔断状态，请求直接失败，不调下游）
    │  经过"熔断时长"后
    ▼
HALF-OPEN（半开，放一个请求试试）
    │
    ├── 成功 → CLOSED（恢复正常）
    └── 失败 → OPEN（继续熔断）
```

#### 3. 热点规则

```
场景：秒杀商品，商品 ID=1001 访问量极大，需要单独对 ID=1001 限流为 QPS=10

规则：热点限流 "getProduct(Long productId)"
     参数索引=0（即 productId）
     针对参数值 productId=1001，单独限流 QPS=10
     其他 productId 按默认规则 QPS=50
```

### 自定义降级处理（@SentinelResource）

```java
@RestController
public class ProductController {

    @GetMapping("/api/products/{id}")
    @SentinelResource(
        value = "getProduct",                    // 资源名称
        blockHandler = "getProductBlockHandler", // 流控/熔断时调这个方法
        fallback = "getProductFallback"          // 方法执行异常时调这个方法
    )
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // blockHandler：被 Sentinel 限制进入时调
    public Product getProductBlockHandler(Long id, BlockException ex) {
        return new Product(null, "默认商品", BigDecimal.ZERO);
    }

    // fallback：方法内部抛出异常时调
    public Product getProductFallback(Long id, Throwable ex) {
        log.error("getProduct error, id={}", id, ex);
        return new Product(null, "兜底商品", BigDecimal.ZERO);
    }
}
```

**blockHandler vs fallback**：
- blockHandler → 被 Sentinel 规则拦截了，外部限制
- fallback → 方法自身执行出错，内部异常

---

## 九、API 网关（Gateway）

### 为什么需要网关

```
没有网关：
  前端 → 订单服务 :8081
  前端 → 用户服务 :8082
  前端 → 商品服务 :8083

问题：
  - 前端要记住三套地址
  - 如果没有统一鉴权，每个服务都要各自做认证
  - 跨域 CORS 配置每个服务都要搞一遍
  - 线上暴露了多个端口，安全风险大
```

```
有网关：
  前端 → 网关 :9999 → 根据路径路由到不同服务

  /api/orders/**   → 订单服务
  /api/users/**    → 用户服务
  /api/products/** → 商品服务

一个入口、统一鉴权、统一限流、统一日志、统一跨域。
```

### Gateway vs Zuul

| 对比项 | Gateway | Zuul 1.x |
|--------|---------|----------|
| 底层 | WebFlux（Reactor，非阻塞） | Servlet（阻塞） |
| 连接方式 | Netty | Tomcat |
| 性能 | 高（异步非阻塞） | 较低（阻塞式，一个请求一个线程） |
| 长连接 | 支持 WebSocket 代理 | 较差 |

**Zuul 1.x 已淘汰，Zuul 2.x 社区不活跃。直接用 Gateway。**

### 三大核心概念

| 概念 | 说明 |
|------|------|
| **Route（路由）** | 一条路由 = 匹配规则 + 目标服务 + 过滤器 |
| **Predicate（断言）** | 匹配条件，比如请求路径是 `/api/users/**` |
| **Filter（过滤器）** | 请求被路由前/后，对请求做加工 |

### 工作流程

```
HTTP 请求
    ↓
Gateway Handler Mapping（检查请求匹配哪条路由）
    ↓
Gateway Web Handler（执行该路由的过滤器链）
    ↓
Netty 转发请求到目标服务
    ↓
收到响应 → 过滤器链后置处理 → 返回客户端
```

### 路由断言工厂（Predicate）

Spring 提供了 11 种内置断言：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service-route
          uri: lb://user-service          # lb:// = 负载均衡方式调用
          predicates:
            - Path=/api/users/**           # 路径匹配（最常用）
            - After=2026-01-01T00:00:00+08:00[Asia/Shanghai]  # 某个时间点之后
            - Before=2028-01-01T00:00:00+08:00[Asia/Shanghai]
            - Between=...,...
            - Cookie=name,regexp
            - Header=X-Request-Id,\d+
            - Host=**.somehost.org
            - Method=GET,POST
            - Query=green                # 请求参数必须包含 green
            - RemoteAddr=192.168.1.1/24  # IP 限制
            - Weight=group1, 8           # 权重路由（灰度发布）
```

### 过滤器

#### Gateway 内置过滤器

| 过滤器 | 作用 |
|--------|------|
| `AddRequestHeader` | 给转发请求加请求头 |
| `AddRequestParameter` | 给转发请求加参数 |
| `AddResponseHeader` | 给响应加头 |
| `PrefixPath` | 自动加前缀 |
| `StripPrefix` | 去掉路径前缀（最常用） |
| `RequestRateLimiter` | 限流 |
| `Retry` | 重试 |
| `CircuitBreaker` | 服务熔断（整合 Resilience4j） |

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/a/**                          # 访问 /a/api/users/1
          filters:
            - StripPrefix=1                        # 去掉 /a 变成 /api/users/1
            - AddRequestHeader=X-From-Gateway,true  # 加个请求头标记
```

#### 自定义全局过滤器

```java
@Component
@Order(-1)                                    // 数字越小优先级越高
public class AuthGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 获取请求路径
        String path = request.getURI().getPath();

        // 放行登录、注册等接口
        if (path.contains("/login") || path.contains("/register")) {
            return chain.filter(exchange);
        }

        // 验证 Token
        String token = request.getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            // 解析 JWT，把用户信息放入请求头传给下游
            String userId = JwtUtil.parseToken(token.replace("Bearer ", ""));
            ServerHttpRequest newRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .build();
            return chain.filter(exchange.mutate().request(newRequest).build());
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
```

**这样所有微服务只需要从请求头拿 X-User-Id，不用每个服务各自解析 Token。**

---

## 十、分布式事务（Seata）

### 问题场景

```java
@Service
public class OrderService {

    @Transactional                          // 这个 @Transactional 只用在自己数据库上！
    public void createOrder(Order order) {
        orderMapper.insert(order);           // 本地数据库：新增订单
        accountClient.debit(order.getMoney());  // 远程调用：扣账户余额
        storageClient.deduct(order.getItems()); // 远程调用：扣库存
    }
    // 如果扣库存远程调用失败，这里的 @Transactional 也只能回滚订单，
    // 但余额已经扣了！因为扣余额在另一个数据库，不受这里的 @Transactional 控制。
}
```

**Spring 自带的 @Transactional 只能控制自己服务的数据库，跨服务的操作无法保证一致性。**

### Seata 是什么

Seata（Simple Extensible Autonomous Transaction Architecture）是阿里巴巴开源的分布式事务解决方案。

### 四种模式

| 模式 | 原理 | 性能 | 适用场景 |
|------|------|------|----------|
| **AT**（推荐） | 自动回滚：UNDO_LOG 记录反向 SQL，成功率最高 | 中 | 基于关系型数据库，不跨库 |
| **TCC** | Try-Confirm-Cancel 三段式，需手写补偿代码 | 高 | 对一致性要求极高，银行转账 |
| **Saga** | 长事务，正向执行 + 正向补偿 | 高 | 长流程、老系统、无分布式锁 |
| **XA** | 两阶段提交（2PC），强一致 | 低 | 几乎不用 |

### AT 模式原理（最常用）

```
全局事务 = 多个本地事务 + 一个全局事务协调者（TC）

执行步骤：
1. 注册全局事务 @GlobalTransactional
2. 订单服务执行 INSERT → RM 记录 UNDO_LOG（DELETE WHERE id=?）
3. 账户服务执行 UPDATE → RM 记录 UNDO_LOG（UPDATE balance=? WHERE id=?）
4. 库存服务执行 UPDATE → RM 记录 UNDO_LOG
5. 所有服务都执行成功 → TC 通知清理 UNDO_LOG
6. 任何一步失败 → TC 通知各 RM 执行 UNDO_LOG 中的反向 SQL

一阶段：执行业务 SQL + 记录 UNDO_LOG
二阶段-提交：删除 UNDO_LOG
二阶段-回滚：执行 UNDO_LOG 中的反向 SQL
```

### 快速接入

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
</dependency>
```

```java
@Service
public class OrderService {

    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    // ↑ 替代 @Transactional，变成全局事务
    public void createOrder(Order order) {
        orderMapper.insert(order);             // 本地
        accountClient.debit(order.getMoney());   // 远程：扣余额
        storageClient.deduct(order.getItems());  // 远程：扣库存
    }
}
```

只需替换一个注解，Seata 自动管理全局事务。

### TCC 模式

AT 模式是自动的，但某些场景（如红包领取）需要 TCC 手动控制：

```
Try（尝试）：冻结用户 100 元可用余额
Confirm（确认）：从冻结中扣除 100 元
Cancel（取消）：解冻 100 元回到可用余额

如果 Try 成功，最终一定执行 Confirm 或 Cancel，保证最终一致性。
```

---

## 十一、分布式链路追踪（Micrometer Tracing）

### 问题

```
用户抱怨："订单列表页面加载特别慢"

你怎么排查？请求经过十几个微服务，到底卡在哪里了？
```

### Sleuth 如何追踪

给每个请求分配一个 `TraceId`（整个调用链唯一）+ `SpanId`（当前节点 ID）：

```
用户请求 → Gateway[SpanId=1]
  → 订单服务[SpanId=2, ParentSpanId=1]
    → 用户服务[SpanId=3, ParentSpanId=2]     ← 查用户信息，耗时 50ms
    → 库存服务[SpanId=4, ParentSpanId=2]     ← 查库存，耗时 5000ms ← 卡在这里！
```

Spring Cloud 2022.x+ 弃用了 Sleuth，改用 **Micrometer Tracing**。

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
```

无需任何代码，所有 HTTP 请求和 Feign 调用自动打上 TraceId/ SpanId 传播。

日志格式会自动带上追踪信息：`[order-service,abc123,abc123]`

---

## 十二、完整技术选型

| 场景 | 推荐方案 |
|------|----------|
| 注册中心 | Nacos |
| 配置中心 | Nacos Config |
| 远程调用 | OpenFeign |
| 负载均衡 | Spring Cloud LoadBalancer |
| 熔断降级 + 限流 | Sentinel |
| API 网关 | Spring Cloud Gateway |
| 分布式事务 | Seata（AT 模式优先） |
| 链路追踪 | Micrometer Tracing + Zipkin |
| 消息驱动 | Spring Cloud Stream（整合 RabbitMQ / RocketMQ） |

---

## 十三、微服务设计原则

1. **高内聚低耦合**：一个服务只负责一块业务，修改原因应该只有一个
2. **独立部署**：改哪个服务，只部署哪个服务
3. **独立数据库**：每个服务有自己的数据库，禁止跨服务 join
4. **轻量级通信**：服务间通过 HTTP/RESTful 或消息队列通信，不要共享内存
5. **容错设计**：每个服务都应考虑下游不可用的情况（熔断、降级、重试）
6. **无状态**：服务本身不存状态，状态放 Redis 或数据库，水平扩展才有意义
7. **自动化运维**：CI/CD、自动部署、健康检查、监控告警

---

## 相关笔记

- [[JAVA/Spring-Boot]] — 微服务的构建基础
- [[Spring-IoC-DI]] — 理解 Feign 动态代理的前提
- [[Spring-MVC]] — Feign 调用语法跟 MVC 注解一致
- [[JAVA/Spring-AOP]] — Sentinel 熔断底层也是 AOP
- [[架构与安全]] — Maven 依赖管理、REST API 设计
- [[Redis]] — 分布式缓存，配合微服务使用
- [[Docker]] — 微服务容器化部署
- [[计算机网络]] — HTTP 协议、RPC 通信基础
