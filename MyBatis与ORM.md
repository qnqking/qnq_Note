# MyBatis 与 ORM

## ORM（Object Relational Mapping）

对象关系映射：

| 映射关系 |
|----------|
| 类（Class） → 表（Table） |
| 对象（Object） → 记录（Row/Record） |
| 属性（Field） → 字段（Column） |

### ORM 框架对比

| 类型 | 框架 | 特点 |
|------|------|------|
| 全自动 | Hibernate、Spring-Data-JPA、MyBatis-Plus | 不需要编写 SQL |
| 半自动 | MyBatis | 需要编写 SQL，学习难度最低 |

---

## SSM 组合演变

```
SSH（struts2 + spring + hibernate）
  → SSM（springmvc + spring + mybatis）
    → Spring Boot + MyBatis-Plus
```

---

## MyBatis

- Mapper 代理：使用 JDK 动态代理生成代理对象
- 优势：灵活的 SQL，解耦
- `#{}`：参数化查询，防止 SQL 注入
- `${}`：字符串拼接，存在 SQL 注入风险（不建议用于用户输入）

---

## 相关笔记

- [[JAVA/Spring-IoC-DI]] — MyBatis 如何被 Spring 管理
- [[JAVA/Spring-Boot]] — Spring Boot + MyBatis 集成
- [[MySQL-核心基础]] — SQL 语法基础
- [[MySQL-高级]] — 索引与事务管理
