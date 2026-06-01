# Spring MVC — RESTful Web API

## 什么是 Spring MVC？

Spring MVC 是基于 **Servlet** 的 Web 框架，核心是一个前端控制器 `DispatcherServlet`。

你只需要写 Controller 方法和注解，Spring MVC 自动完成 URL 映射、参数提取、JSON 转换。

## 请求处理全流程

```
浏览器发起 HTTP 请求
    ↓
DispatcherServlet（前端控制器，所有请求的统一入口）
    ↓
HandlerMapping（查找哪个 Controller 方法匹配这个 URL）
    ↓
HandlerAdapter（真正执行方法，处理参数绑定和类型转换）
    ↓
你的 Controller 方法执行（业务逻辑）
    ↓
HttpMessageConverter（把返回值转成 JSON）
    ↓
响应返回浏览器
```

关键点：**你只需要写 Controller，中间每一步都是 Spring MVC 自动做的。**

## 核心注解速查

| 注解 | 作用 | 示例 |
|------|------|------|
| `@RestController` | = `@Controller` + `@ResponseBody`，返回值自动转 JSON | 标在类上 |
| `@RequestMapping("/api/users")` | 设置 URL 前缀 | 标在类上 |
| `@GetMapping` | 处理 GET 请求 | `@GetMapping("/{id}")` |
| `@PostMapping` | 处理 POST 请求 | `@PostMapping` |
| `@PutMapping` | 处理 PUT 请求 | `@PutMapping("/{id}")` |
| `@DeleteMapping` | 处理 DELETE 请求 | `@DeleteMapping("/{id}")` |
| `@PathVariable` | 从 URL 路径提取参数 | `/users/1` → id = 1 |
| `@RequestParam` | 从 URL 查询参数提取 | `?name=张` → name = "张" |
| `@RequestBody` | 把请求体 JSON → Java 对象 | POST/PUT 请求体 |

## 五个标准 REST API

以一个用户管理为例：

### 1. 查询全部 — `GET /api/users`

```java
@GetMapping
public List<User> listAll() {
    return userService.findAll();  // 返回 JSON 数组
}
```

浏览器访问 `http://localhost:8080/api/users`，返回：
```json
[
  { "id": 1, "name": "张三", "email": "zhangsan@example.com" },
  { "id": 2, "name": "李四", "email": "lisi@example.com" }
]
```

### 2. 根据 ID 查询 — `GET /api/users/1`

```java
@GetMapping("/{id}")
public ResponseEntity<User> getById(@PathVariable Long id) {
    User user = userService.findById(id);
    if (user == null)
        return ResponseEntity.notFound().build();  // 404
    return ResponseEntity.ok(user);                // 200
}
```

`@PathVariable` 把 URL 路径 `{id}` 映射到参数。`/api/users/99` → id = 99。

`ResponseEntity` 让你精确控制 HTTP 状态码和响应体。

### 3. 按名称搜索 — `GET /api/users/search?name=张`

```java
@GetMapping("/search")
public List<User> search(@RequestParam String name) {
    return userService.searchByName(name);
}
```

`@RequestParam` 提取问号后的参数。访问 `/api/users/search?name=张` → name = "张"。

> **注意**：`/search` 路由要放在 `/{id}` **前面**，否则 "search" 会被当成 id 的值来匹配。

### 4. 新增用户 — `POST /api/users`

```java
@PostMapping
public ResponseEntity<User> create(@RequestBody User user) {
    User saved = userService.save(user);
    return ResponseEntity.status(201).body(saved);  // 201 Created
}
```

`@RequestBody` 触发 Jackson 自动转换：

```
请求体 JSON {"name":"赵六","email":"zhaoliu@test.com"}
              ↓  Jackson 自动反序列化
User { name="赵六", email="zhaoliu@test.com", id=null }
```

201（Created）比 200 更语义化——表示"资源已创建"。

### 5. 更新用户 — `PUT /api/users/1`

```java
@PutMapping("/{id}")
public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
    User updated = userService.update(id, user);
    if (updated == null)
        return ResponseEntity.notFound().build();
    return ResponseEntity.ok(updated);
}
```

`@PutMapping` 只匹配 HTTP PUT 请求，RESTful 规范中 PUT 表示完整更新（PATCH 才是部分更新）。

### 6. 删除用户 — `DELETE /api/users/1`

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean deleted = userService.delete(id);
    if (!deleted)
        return ResponseEntity.notFound().build();
    return ResponseEntity.noContent().build();  // 204 No Content
}
```

204（No Content）表示"操作已成功，但没有内容需要返回"。

## RESTful API 设计约定

| HTTP 方法 | 路径 | 含义 | 响应码 |
|-----------|------|------|--------|
| GET | `/users` | 查询全部 | 200 |
| GET | `/users/1` | 查询单个 | 200 / 404 |
| POST | `/users` | 新增 | 201 |
| PUT | `/users/1` | 更新 | 200 / 404 |
| DELETE | `/users/1` | 删除 | 204 / 404 |

## 拦截器 vs 过滤器

| 特性 | 过滤器（Filter） | 拦截器（Interceptor） |
|------|-----------------|----------------------|
| 规范 | Java Servlet | Spring MVC |
| 能访问的对象 | Request/Response | Controller 上下文、Handler |
| 适用场景 | 编码、日志、CORS | 权限校验、国际化、日志 |

**执行顺序**：过滤器 → 拦截器 → Controller → 拦截器 → 过滤器

## 测试命令

```bash
# 启动
mvn spring-boot:run

# 测试
curl http://localhost:8080/api/users                    # 查全部
curl http://localhost:8080/api/users/1                  # 查单个
curl http://localhost:8080/api/users/search?name=张     # 搜索
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"赵六","email":"zhao@test.com"}'          # 新增
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"张三丰","email":"zsf@test.com"}'         # 更新
curl -X DELETE http://localhost:8080/api/users/1        # 删除
```

---

## 相关笔记
- [[Spring-IoC-DI]] — 上一节：Spring 如何管理 Controller 及其依赖
- [[Spring-AOP]] — 下一节：AOP 如何拦截 Controller 方法
- [[JAVA]] — Java 基础
