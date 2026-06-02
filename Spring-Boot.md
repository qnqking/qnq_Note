# Spring Boot

Spring Boot 是 Spring 生态的快速开发框架，核心是「约定大于配置」。

---

## 六大特点

1. 创建独立的 Spring 应用程序
2. 直接内嵌 Tomcat、Jetty 或 Undertow（无需部署 WAR 文件）
3. 提供 Starter 简化依赖管理
4. 尽可能自动配置 Spring 和第三方库
5. 提供生产就绪功能：指标、健康检查、外部化配置
6. 绝对不需要代码生成，也无需 XML 配置

---

## 自动配置原理

通过 `@SpringBootApplication`（包含 `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`）根据 classpath 中的依赖自动配置 Bean。

---

## Starter 三大作用

1. 管理 Maven 依赖关系
2. 完成自动配置
3. 遵循约定优于配置

---

## 配置文件

`application.yml` 或 `application.properties`，支持多环境配置（`application-dev.yml`、`application-prod.yml`）。

---

## 内嵌服务器

默认内嵌 Tomcat，也可切换为 Jetty 或 Undertow。

---

## Actuator

提供生产级监控端点：`/health`、`/metrics`、`/env`

---

## 常用注解

| 注解 | 作用 |
|------|------|
| `@SpringBootApplication` | 一键启动 Spring Boot |
| `@RestController` | = `@Controller` + `@ResponseBody` |
| `@Configuration` | 配置类 |
| `@Value` | 读取配置文件值 |

---

## 常见集成

- Spring Boot + MyBatis：`mybatis-spring-boot-starter`
- Spring Boot + Redis：`spring-boot-starter-data-redis`
- Spring Boot + JPA：`spring-boot-starter-data-jpa`
- Spring Boot + Security：`spring-boot-starter-security`

> Spring Boot 不支持 JSP，推荐 Thymeleaf 模板引擎

---

## 相关笔记

- [[Spring-IoC-DI]] — 控制反转与依赖注入
- [[Spring-MVC]] — RESTful Web API
- [[Spring-AOP]] — 面向切面编程
- [[MyBatis与ORM]] — ORM 框架集成
- [[注解速查表]] — Spring Boot 相关注解
