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

**常用参数说明**：

- `-d`：后台运行（分离模式）
- `-p 80:80`：端口映射（宿主机:容器）
- `-v /host/path:/container/path`：绑定挂载
- `-v volume_name:/container/path`：命名卷挂载
- `-e KEY=VALUE`：传递环境变量
- `--name xxx`：自定义容器名
- `-it`：交互式终端
- `--rm`：停止后自动删除
- `--restart always`：崩溃/断电自动重启
- `--network xxx`：指定网络

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

#### 八、命令速查表

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