# MySQL 核心基础

MySQL 是瑞典 MySQL AB 公司开发的免费、开源关系型数据库。使用 SQL（Structured Query Language）操作。

---

## 数据库基本概念

- **关系型数据库（RDBMS）**：关注数据和数据之间的关系，使用 SQL 语言操作
- **非关系型数据库（NoSQL）**：关注快速存取，无 SQL 语言操作
- 常见 RDBMS：MySQL、SqlServer、Oracle、DB2、AliSQL

### 关系型数据库存储结构

1:1 / 1:N / N:N

---

## Docker 安装 MySQL

```bash
docker pull mysql
docker run --name mysql01 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=你的密码 -d 镜像ID
```

---

## DDL（数据定义语言）

> 常见字符编码集：UTF-8、GBK、GB2312、utf8mb4

### 数据库操作

```sql
CREATE DATABASE [IF NOT EXISTS] 数据库名 CHARACTER SET utf8mb4;
ALTER DATABASE 数据库名 CHARACTER SET 新字符集;
DROP DATABASE 数据库名;
```

### 表操作

```sql
-- 建表
CREATE TABLE 表名(字段 数据类型 [约束], ...);

-- 修改表
ALTER TABLE 表名 ADD 新字段 数据类型 [约束];
ALTER TABLE 表名 MODIFY 字段 新数据类型 [约束];
ALTER TABLE 表名 CHANGE 旧字段 新字段 数据类型 [约束];
ALTER TABLE 表名 DROP COLUMN 字段名;

-- 删表
DROP TABLE 表名;
```

---

## DML（数据操作语言）

```sql
-- 新增
INSERT INTO 表名(字段列表) VALUES (值列表), (值列表)...;

-- 修改
UPDATE 表名 SET 字段=值,... [WHERE 筛选条件];

-- 删除
DELETE FROM 表名 [WHERE 筛选条件];    -- 逐行删，可回滚
TRUNCATE TABLE 表名;                  -- 清空表，重置自增ID，不可回滚
```

---

## 约束类型

| 约束 | 关键字 | 说明 |
|------|--------|------|
| 主键 | PRIMARY KEY | 非空且唯一，不能具备业务含义 |
| 自增 | AUTO_INCREMENT | 自动递增 |
| 唯一 | UNIQUE | 值不能重复 |
| 检查 | CHECK (条件) | 限制列的取值范围 |
| 非空 | NOT NULL | 必须有值 |
| 默认 | DEFAULT 值 | 未输入时使用默认值 |
| 外键 | FOREIGN KEY | 建立表之间的关系 |

### 外键约束

```sql
ALTER TABLE 子表名
ADD CONSTRAINT 外键名 FOREIGN KEY (外键列)
REFERENCES 主表名(主键)
[ON DELETE action ON UPDATE action];
```

> 优秀设计者：使用外键，但不使用外键约束（由程序保证关系正确性，降低数据库复杂性和维护成本）

---

## DQL（数据查询语言）

### 执行顺序

```
FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT
```

### 基本查询

```sql
SELECT [DISTINCT] 字段列表
FROM 表名
[WHERE 筛选条件]
[GROUP BY 分组]
[HAVING 聚合过滤]
[ORDER BY 排序字段 ASC/DESC]
[LIMIT 分页];
```

### WHERE 条件

`=` / `>` / `<` / `>=` / `<=` / `!=` / `<>` / `AND` / `OR` / `LIKE`（`%` 匹配 0~N 个字符，`_` 匹配单个字符）/ `BETWEEN ... AND ...` / `IS NULL` / `IS NOT NULL`

### 聚合函数

| 函数 | 作用 |
|------|------|
| `COUNT(*)` / `COUNT(1)` | 统计总条数 |
| `COUNT(字段)` | 统计该字段非 NULL 值的条数 |
| `SUM()` | 求和 |
| `AVG()` | 求平均数 |
| `MAX()` / `MIN()` | 求最大值/最小值 |
| `IFNULL(字段, 默认值)` | 处理 NULL 值 |

### 字符串函数

`UPPER()` / `LOWER()` / `CONCAT()` / `TRIM()` / `SUBSTR()` / `LENGTH()` / `REPLACE()`

### 日期函数

`YEAR()` / `MONTH()` / `DAY()` / `NOW()` / `CURDATE()`

### 数学函数

`RAND()` / `ROUND()` / `PI()` / `MOD()`

### 连接方式

- 内联查（INNER JOIN）
- 左外联查（LEFT JOIN）
- 右外联查（RIGHT JOIN）

### 分组查询

```sql
SELECT 字段, COUNT(*) FROM 表 GROUP BY 分组字段;

-- HAVING：将聚合后的结果再次过滤
-- WHERE 是分组前行级过滤，HAVING 是分组后聚合过滤
```

---

## DCL（数据控制语言）

- 管理数据库账号
- 划分用户权限

---

## 子查询

### 位置

SELECT 后面 / FROM 后面 / WHERE 后面

> 几乎所有子查询都可用 LEFT/INNER JOIN 替代，但 WHERE 后的聚合函数子查询只能使用子查询

### IN vs EXISTS

- `IN`：内表数据量少时更合适，底层做笛卡尔乘积 + 等值判断
- `EXISTS`：外表数据量少时更合适，逐行代入内表进行匹配

### UNION vs UNION ALL

- `UNION`：合并结果集并去重
- `UNION ALL`：合并结果集不去重，效率更高

---

## 数据库设计范式

| 范式 | 要求 |
|------|------|
| 1NF | 原子性：列拆分到不可再拆 |
| 2NF | 相关性：所有列必须跟主键有关系 |
| 3NF | 直接相关性：所有列必须跟主键有直接关系 |

> 反范式：为提升查询性能适当冗余数据

---

## 视图（View）

- 虚拟表，数据由底层物理表提供
- 只适合查询，不能增删改
- 作用：降低多表操作的复杂性

```sql
CREATE VIEW 视图名 AS SELECT 查询语句;
```

---

## 存储过程 & 函数 & 触发器

### 存储过程

- 一种特殊的函数，可以改动数据库（普通函数只能查询）
- 优点：减少网络开销、靠近数据执行效率更高
- 缺陷：DB 职责是存储不是计算、语法不通用、易受攻击

### 触发器

```sql
CREATE TRIGGER trigger_name
{BEFORE | AFTER} {INSERT | UPDATE | DELETE} ON table_name
FOR EACH ROW
BEGIN
    -- 触发器逻辑
END;
```

---

## 数据类型

- `CHAR(n)`：定长字符串，存取快，适合短且长度固定的数据
- `VARCHAR(n)`：变长字符串，节省空间，适合长度不一的数据
- `DECIMAL(M,D)`：精确小数，用于金额
- 不推荐 `float` / `double` 存储金额（精度丢失）
- 不推荐使用外键约束（增加复杂性、影响性能、维护成本高）

---

## 相关笔记

- [[MySQL-高级]] — 索引、事务、锁、MVCC
- [[JAVA/Java-高级API]] — JDBC 连接数据库
- [[MyBatis与ORM]] — ORM 框架
