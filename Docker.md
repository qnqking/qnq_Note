# Docker 学习笔记

#### 一、核心概念与架构

**Docker vs 虚拟机**：容器共享宿主机内核，相比虚拟机更轻量、启动速度更快。

表格

|概念|说明|
|---|---|
|**镜像 (Image)**|容器的模板，类比"模具"|
|**容器 (Container)**|镜像的运行实例，类比"糕点"|
|**仓库 (Registry)**|存放/分享镜像的地方，如 Docker Hub|
|**Repository**|命名空间 + 镜像名，同一镜像不同版本的集合|
|**Tag**|版本标签（如 :latest、:1.28.0）|

#### 二、安装指南（3 个平台）

表格

| 平台          | 关键步骤                               |
| ----------- | ---------------------------------- |
| **Linux**   | 访问 `get.docker.com` 复制命令执行即可       |
| **Windows** | 启用 WSL + 虚拟机平台 → 安装 Docker Desktop |
| **Mac**     | 下载对应芯片安装包，安装过程最简单                  |
启用Windows功能
![](attachments/Pasted%20image%2020260525104603.png)
![503](attachments/Pasted%20image%2020260525104506.png)
`wsl set default version 2`(WSL默认版本设置为2)
`wsl --update --web-download`（安装WSL）
![](attachments/Pasted%20image%2020260525145103.png)
配置Docker镜像
```
"registry-mirrors": [
    "https://docker.1ms.run",
    "https://docker.xuanyuan.me"
  ]
```
#### 三、镜像操作

bash

编辑

```
# 下载镜像（官方库+官方命名空间可省略）
docker pull nginx 

# 下载指定版本
docker pull nginx:1.28.0 

# 列出本地所有镜像
docker images 

# 删除镜像
docker rmi <镜像ID> 

# 拉取指定CPU架构镜像
docker pull --platform=arm64 

# 镜像站配置
# 国内网络需配置 registry-mirrors 解决下载慢问题。
```

#### 四、容器操作（核心命令）

| 参数                  | 说明                 | 示例                                                                                                                                      |
| ------------------- | ------------------ | --------------------------------------------------------------------------------------------------------------------------------------- |
| `-d`                | 后台运行容器（分离模式）       | `docker run -d nginx`  <br>启动 Nginx 容器并在后台运行                                                                                            |
| `-p [宿主机端口]:[容器端口]` | 端口映射（宿主机:容器）       | `docker run -p 8080:80 nginx`  <br>将宿主机 8080 端口映射到容器 80 端口  <br>`docker run -p 127.0.0.1:5432:5432 postgres`  <br>仅允许本地访问数据库            |
| `-v [宿主机路径]:[容器路径]` | 绑定挂载（目录映射）         | ` docker run -v C:/Users/QN544/data:/app/data nginx`  <br>将当前目录的 data 映射到容器内                                                            |
| `-v [卷名]:[容器路径]`    | 命名卷挂载（Docker 管理存储） | `docker volume create mydata`  <br>`docker run -v mydata:/var/lib/mysql mysql`  <br>使用命名卷持久化 MySQL 数据                                   |
| `-e KEY=VALUE`      | 传递环境变量             | `docker run -e MYSQL_ROOT_PASSWORD=secret mysql`  <br>设置 MySQL root 密码  <br>`docker run -e TZ=Asia/Shanghai nginx`  <br>设置时区            |
| `--name [自定义名]`     | 指定容器名称             | `docker run --name my-nginx -d nginx`  <br>创建名为 my-nginx 的容器                                                                            |
| `-it`               | 交互式终端（通常组合使用）      | `docker run -it ubuntu /bin/bash`  <br>进入 Ubuntu 容器的交互式 shell  <br>`docker run -it --rm alpine sh`  <br>临时运行 Alpine 并自动清理               |
| `--rm`              | 容器停止后自动删除          | `docker run --rm -it python:3.9 python -c "print('Hello')"`  <br>执行完命令后自动清理容器                                                           |
| `--restart [策略]`    | 重启策略               | `docker run -d --restart always nginx`  <br>容器崩溃/系统重启后自动恢复  <br>`docker run -d --restart unless-stopped nginx`  <br>仅意外退出时重启（手动停止后不再启动） |
| `--network [网络名]`   | 指定网络模式             | `docker network create mynet`  <br>`docker run --network mynet --name app nginx`  <br>将容器加入自定义网络                                        |

**挂载卷对比**：

表格

|方式|特点|
|---|---|
|**绑定挂载 (bind mount)**|直接指定宿主机目录，会覆盖容器内目录|
|**命名卷 (named volume)**|Docker 管理，首次会自动初始化容器内的数据到卷|

**容器启停与管理**：

bash

编辑

```
# 查看运行中的容器 / 查看所有容器
docker ps / docker ps -a

# 停止 / 启动（保留原有参数） / 重启
docker stop <容器> / docker start <容器> / docker restart <容器>

# 删除（运行中需 -f）
docker rm <容器>

# 只创建不启动
docker create ...

# 查看日志 / 滚动查看日志
docker logs <容器> / docker logs -f <容器>

# 查看容器详细信息
docker inspect <容器>

# 删除所有容器
docker container prune -f
```

**进入容器调试**：

bash

编辑

```
# 进入容器获得交互式 shell
docker exec -it <容器> /bin/bash 

# 在容器内执行命令
docker exec <容器> ps -ef
```

#### 五、Dockerfile 构建镜像

dockerfile

编辑

```
# 基础镜像
FROM python:3.13-slim 

# 工作目录
WORKDIR /app 

# 拷贝代码（主机当前→镜像工作目录）
COPY . . 

# 安装依赖
RUN pip install -r requirements.txt 

# 声明端口（非强制，文档作用）
EXPOSE 8000 

# 容器启动默认命令
CMD ["python", "main.py"]
```

**Dockerfile 最佳实践**：

- **多阶段构建**：分离构建环境和运行环境，减小最终镜像体积。
- **利用层缓存**：先拷贝依赖文件再 copy 源码，避免重复安装依赖。
- **使用 `.dockerignore`**：排除 `node_modules`、`.git` 等不需要的文件，减少构建上下文。
- **非 root 用户运行**：`USER 1000` 提高安全性。
- **最小化镜像层数**：合并 `RUN` 命令（用 `&&` 连接）。
- **选择合适的基础镜像**：优先用 `alpine` 或 `slim` 版本减小体积。

**多阶段构建示例**：

```dockerfile
# 构建阶段
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# 运行阶段
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER 1000
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

**构建与推送流程**：

bash

编辑

```
# 构建镜像
docker build -t myapp:1.0 . 

# 登录 Docker Hub
docker login 

# 打标签（加命名空间）
docker tag myapp username/myapp 

# 推送镜像
docker push username/myapp
```

#### 六、Docker 网络

表格

|模式|说明|
|---|---|
|**bridge（默认）**|容器分配独立内网 IP（172.17.x.x），需端口映射访问|
|**自定义 bridge**|同子网容器可通过 容器名 DNS 互相访问，跨子网隔离|
|**host**|直接共享宿主机网络，无需 -p 端口映射|
|**none**|不联网|

bash

编辑

```
docker network create network1
docker network list
docker network rm network1
```

#### 七、Docker Compose（容器编排）

- **核心功能**：用 YAML 文件定义多容器应用（前/后端、数据库等）。
- **优势**：自动创建共享子网，容器按 service 名互通，支持 `depends_on` 指定启动顺序。
- **适用场景**：Docker Compose 适合单机/个人，企业集群级通常使用 Kubernetes。

**compose.yml 示例**：

```yaml
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - DB_HOST=db
      - DB_PORT=5432

  db:
    image: postgres:16
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_PASSWORD: secret

volumes:
  pgdata:
```

**常用命令**：

bash

编辑

```
# 启动所有服务
docker compose up -d 

# 停止并删除容器
docker compose down 

# 仅停止/启动，不删除
docker compose stop / start 

# 指定非标准文件名
docker compose -f custom.yml up
```

#### 九、资源限制

```bash
# 限制内存和 CPU
docker run -d --memory="512m" --cpus="1.5" nginx

# 限制交换内存
docker run -d --memory="1g" --memory-swap="2g" nginx

# 查看容器资源使用情况
docker stats
```

#### 十、健康检查 (HEALTHCHECK)

```dockerfile
# 在 Dockerfile 中定义
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1
```

```bash
# 查看健康状态
docker ps  # STATUS 列显示 healthy / unhealthy
docker inspect --format='{{json .State.Health}}' <容器>
```

#### 十一、常见排错

```bash
# 磁盘空间不足 → 清理未使用的资源
docker system prune -a

# 查看容器退出原因
docker logs <容器>
docker inspect <容器> | grep -A 10 State

# 查看 Docker 磁盘占用
docker system df
```

#### 十二、命令速查表

表格

|操作|命令|
|---|---|
|**下载镜像**|`docker pull`|
|**列出镜像**|`docker images`|
|**删除镜像**|`docker rmi`|
|**运行容器**|`docker run`|
|**列出容器**|`docker ps` / `docker ps -a`|
|**启停**|`docker start` / `stop` / `restart`|
|**删除容器**|`docker rm`|
|**查看日志**|`docker logs`|
|**进入容器**|`docker exec -it`|
|**构建镜像**|`docker build`|
|**推送镜像**|`docker push`|
|**网络管理**|`docker network create` / `list` / `rm`|
|**卷管理**|`docker volume create` / `list` / `rm`|
|**多容器编排**|`docker compose up` / `down`|