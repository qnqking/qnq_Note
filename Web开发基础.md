# Web 开发基础

## Web 系统架构

### B/S（Browser/Server） vs C/S（Client/Server）

| 架构 | 模式 | 客户端依赖 | 举例 |
|------|------|------------|------|
| B/S | 浏览器/服务器 | 浏览器 | 淘宝、京东 |
| C/S | 客户端/服务器 | 安装客户端 | CS2、原神 |

### Web 技术栈

1. 前端技术：HTML/CSS/JS/小程序/Android/iOS/鸿蒙
2. 前端服务器：Nginx
3. 后端技术：Spring 系列框架、中间件、缓存
4. 数据库：MySQL/Oracle + Redis/HBase
5. 通讯协议：HTTP

---

## HTML

HTML = 网页的身体（构建网页），CSS = 装饰外观，JS = 控制行为。

- 双标签：`<div></div>`（有开始有结束）
- 单标签：`<hr>`（只有开始）
- 块级标签（Block-level）：独占一行
- 行级标签（Inline）：共享一行

### CSS 引入方式

| 方式 | 写法 | 优先级 |
|------|------|--------|
| 行内样式 | 标签的 style 属性 | 最高 |
| 内联样式 | `<style>` 标签 | 中 |
| 外联样式（推荐） | `<link>` 引入外部 CSS 文件 | 低 |

---

## JavaScript

Java 和 JavaScript 的关系：雷锋和雷峰塔的关系（无直接关系）。

### var vs let

| 特性 | var | let |
|------|-----|-----|
| 作用域 | 函数作用域/全局作用域 | 块级作用域 `{}` |
| 变量提升 | 提升并初始化为 undefined | 提升但声明前访问报错 |
| 重复声明 | 允许 | 不允许 |

### DOM（Document Object Model）

JS 三大作用：
1. 修改网页内容
2. 修改网页样式
3. 通过事件触发函数

---

## Vue 基础

- `v-bind`：给标签属性赋值
- `computed`：计算属性
- **生命周期钩子**：beforeCreate → created → beforeMount → mounted → beforeUpdate → updated → beforeDestroy → destroyed

---

## MVC 架构模式

| 层 | 角色 | 职责 |
|-----|------|------|
| Model（模型） | JavaBean | 存储数据、处理业务、数据库交互 |
| View（视图） | JSP/HTML | 展示页面给用户 |
| Controller（控制器） | Servlet | 接收请求、调用 Model |

### 对象分层

| 对象 | 全称 | 作用 |
|------|------|------|
| VO | View Object | 接收用户输入 |
| DTO | Data Transfer Object | 各层之间传输数据 |
| DAO | Data Access Object | 数据库交互 |
| BO | Business Object | 业务实现 |
| PO | Persistent Object | 持久化对象 |

### MVC vs MVVM

- **MVC**：后端软件架构模式
- **MVVM**：前端框架架构模式（如 Vue），核心是 Model 和 View 的双向绑定

---

## Servlet

可以帮你处理前端请求的 Java 类，规范：继承 `HttpServlet`。

### 生命周期

编译 → 加载 → 实例化 → 初始化 → 服务化 → 卸载

> 编译/加载/实例化/初始化只执行 1 次；服务化执行多次。多线程 & 单实例。

### Web 三大组件

| 组件 | 作用 |
|------|------|
| Servlet | 接收前端的 HTTP 请求 |
| Filter（过滤器） | 过滤前端的 HTTP 请求 |
| Listener（监听器） | 监听三大作用域的变化 |

### 三大作用域

| 作用域 | 对应 | 生命周期 |
|--------|------|----------|
| request | HttpServletRequest | 单次 HTTP 请求 |
| session | HttpSession | 默认 30 分钟 |
| application | ServletContext | 跟 Tomcat 同步 |

### @WebServlet

```java
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet { ... }
```

---

## JSP（Java Server Page）

后端页面技术，组成 = HTML + Java 代码。本质是 Servlet。

- EL 表达式：`${属性}` 从 pageContext → request → session → ServletContext 依次查找
- 九大内置对象

---

## Cookie + Session

- **Cookie**：浏览器特有的文本存储技术（存在客户端）
- **Session**：Tomcat 中每个用户独立的 Session 对象（存在服务端）
- 后端通过 Cookie + Session 识别 HTTP 请求来自哪个用户
- Session 默认 30 分钟有效期

---

## 请求转发 vs 重定向

- **请求转发（Forward）**：1 次请求，服务器内部转发
- **重定向（Redirect）**：2 次请求，由浏览器实现

---

## 过滤器（Filter）

使用场景：
1. 字符编码统一处理
2. 用户权限控制（登录过滤器）
3. 日志记录与请求监控
4. 敏感词过滤/数据预处理
5. 跨域请求处理（CORS）

---

## 过滤器 vs 拦截器

| 特性 | 过滤器（Filter） | 拦截器（Interceptor） |
|------|-----------------|----------------------|
| 规范 | Java Servlet | Spring MVC |
| 能访问的对象 | Request/Response | Controller 上下文、Handler |

**执行顺序**：过滤器 → 拦截器 → Controller → 拦截器 → 过滤器

---

## 相关笔记

- [[计算机网络]] — HTTP 协议详解
- [[JAVA/Spring-MVC]] — Spring MVC 实现 RESTful API
- [[JAVA/Spring-Boot]] — 内嵌 Tomcat 自动配置
- [[架构与安全]] — SSM 组合、安全认证
