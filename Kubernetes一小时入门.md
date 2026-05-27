# Kubernetes 一小时轻松入门

> 📺 B站课程：GeekHour《Kubernetes一小时轻松入门》
> 🔗 https://www.bilibili.com/video/av237678655/
> 📝 官方笔记：https://geekhour.net/2023/12/23/kubernetes/
> 📦 资料下载：公众号 **GeekHour** 回复 `k8s`

---

## 一、Kubernetes 简介

Kubernetes（K8s）是 Google 开源的容器编排平台，用于**自动化部署、扩展和管理容器化应用**。

核心能力：
- **服务发现与负载均衡**：自动为容器分配 DNS 名和 IP，并进行流量分发
- **自动装箱**：根据资源需求和约束自动调度容器到节点
- **自我修复**：自动重启失败容器、替换/重新调度容器
- **水平扩缩容**：通过命令或 CPU 使用率自动扩缩
- **滚动更新与回滚**：逐步替换容器，失败时自动回滚

---

## 二、核心组件

### Master 节点（控制平面）

| 组件 | 作用 |
|------|------|
| **API Server** | 集群统一入口，所有操作必经之路；负责认证、授权、准入控制 |
| **Scheduler** | 调度器，监听新 Pod，根据资源策略将其分配到最优 Node |
| **Controller Manager** | 控制器管理器，维护集群期望状态（副本数、端点等） |
| **etcd** | 分布式键值存储，保存所有集群配置和状态数据 |

### Worker 节点

| 组件 | 作用 |
|------|------|
| **kubelet** | 节点代理，负责管理 Pod 生命周期、上报节点状态 |
| **kube-proxy** | 网络代理，维护网络规则，实现 Service 负载均衡 |
| **Container Runtime** | 容器运行时（Docker / containerd / CRI-O） |

### 组件交互流程

```
kubectl → API Server → etcd（写入期望状态）
                       ↓
                  Scheduler（绑定 Pod 到 Node）
                       ↓
                  kubelet（拉起容器）
                       ↓
                  kube-proxy（更新 iptables/IPVS 规则）
```

---

## 三、核心资源对象

### 对象层级关系

```
Deployment（控制器，管理副本与更新策略）
  └── ReplicaSet（维护副本数量）
       └── Pod（最小调度单元，一组容器的集合）
            └── Container（容器）
                 └── 应用进程

Service（为一组 Pod 提供稳定访问入口）
  └── Endpoints（后端 Pod IP 列表，随 Pod 变化自动更新）

Ingress（七层负载均衡，域名路由）
  └── Service
```

### 核心概念速查

| 概念 | 说明 |
|------|------|
| **Pod** | K8s 最小部署单元，包含一个或多个共享网络/存储的容器。Pod 是**短暂的**，IP 会变化 |
| **Service** | 为一组 Pod 提供**稳定的访问入口**（ClusterIP 不变），实现负载均衡和服务发现 |
| **Deployment** | 管理无状态应用的副本数量、滚动升级和版本回滚 |
| **StatefulSet** | 管理有状态应用（数据库等），保证 Pod 的顺序和持久标识 |
| **DaemonSet** | 确保每个 Node 运行一个 Pod 副本（日志收集、监控 Agent） |
| **ConfigMap** | 存储非敏感配置信息，实现配置与代码解耦 |
| **Secret** | 存储敏感信息（Base64 编码），如密码、Token、证书 |
| **Volume** | 持久化存储，Pod 重启后数据不丢失 |
| **PersistentVolume (PV)** | 集群级存储资源，独立于 Pod 生命周期 |
| **PersistentVolumeClaim (PVC)** | 用户对存储的请求，消耗 PV 资源 |
| **Ingress** | 管理集群外部 HTTP/HTTPS 访问，支持域名路由、SSL 终结 |
| **Namespace** | 资源逻辑隔离，适用于多环境/多租户场景 |

### Label 与 Selector（关联机制）

Label 是 Pod 的标签，Selector 是 Controller 选择 Pod 的依据，二者必须匹配：

```yaml
# Deployment 通过 selector 选择 Pod
spec:
  selector:
    matchLabels:
      app: nginx

# Pod 通过 labels 标识自己
spec:
  template:
    metadata:
      labels:
        app: nginx          # 必须与 selector 一致！
```

---

## 四、搭建 K8s 环境

### 方式一：Minikube（本地开发首选）

```bash
# 启动集群
minikube start --driver=docker

# 查看状态
minikube status

# 打开 Dashboard
minikube dashboard

# 停止/删除
minikube stop
minikube delete
```

### 方式二：K3s + Multipass（轻量级多节点）

K3s 是 Rancher 推出的轻量级 Kubernetes，适合边缘计算和开发环境。配合 Multipass 可快速创建 Ubuntu 虚拟机搭建多节点集群。

---

## 五、kubectl 常用命令

### 命令格式

```
kubectl [command] [TYPE] [NAME] [flags]
```

### 资源类型缩写

| 全名 | 缩写 | 说明 |
|------|------|------|
| `pods` | `po` | Pod |
| `services` | `svc` | Service |
| `deployments` | `deploy` | Deployment |
| `nodes` | `no` | Node |
| `namespaces` | `ns` | Namespace |
| `configmaps` | `cm` | ConfigMap |
| `replicasets` | `rs` | ReplicaSet |
| `persistentvolumes` | `pv` | PV |
| `persistentvolumeclaims` | `pvc` | PVC |
| `ingresses` | `ing` | Ingress |

### 基础 CRUD

```bash
# 创建资源
kubectl create deployment nginx --image=nginx
kubectl create -f nginx.yaml

# 查看资源
kubectl get pods
kubectl get pods -o wide              # 更多信息（IP、Node）
kubectl get pods -o yaml              # 以 YAML 格式输出
kubectl get pods -w                   # 持续监听变化
kubectl get all                       # 查看所有资源

# 查看详情
kubectl describe pod <pod-name>       # 排错首选！

# 编辑资源
kubectl edit deployment nginx

# 删除资源
kubectl delete pod <pod-name>
kubectl delete -f nginx.yaml
```

### 声明式部署（推荐）

```bash
# 应用配置（创建/更新）
kubectl apply -f nginx.yaml

# 查看发布状态
kubectl rollout status deployment/nginx

# 查看发布历史
kubectl rollout history deployment/nginx

# 回滚
kubectl rollout undo deployment/nginx
kubectl rollout undo deployment/nginx --to-revision=2

# 扩缩容
kubectl scale deployment nginx --replicas=5
```

### 排错调试

```bash
# 查看日志
kubectl logs <pod-name>
kubectl logs -f <pod-name>            # 实时跟踪
kubectl logs <pod-name> -c <container> # 多容器时指定

# 进入容器
kubectl exec -it <pod-name> -- /bin/sh

# 端口转发（本地调试）
kubectl port-forward <pod-name> 8080:80

# 资源使用
kubectl top pods
kubectl top nodes
```

### 快速创建资源

```bash
# 命令行快速启动 Pod
kubectl run nginx --image=nginx

# 创建 Deployment 并暴露服务
kubectl create deployment web --image=nginx
kubectl expose deployment web --port=80 --type=NodePort --target-port=80

# dry-run 生成 YAML 模板（不真正创建）
kubectl create deployment web --image=nginx -o yaml --dry-run=client > nginx.yaml
```

### create vs apply

| | `kubectl create` | `kubectl apply` |
|---|---|---|
| 方式 | 命令式 | 声明式 |
| 资源已存在时 | ❌ 报错 | ✅ 更新资源 |
| 资源不存在时 | 创建 | 创建 |
| 适用场景 | 一次性操作 | **生产环境首选** |

---

## 六、YAML 配置文件

### YAML 语法要点

- 大小写敏感
- 使用**空格**缩进（禁止 Tab），通常 2 空格
- 相同层级必须左对齐
- `#` 表示注释
- `---` 分隔多个 YAML 文档

### 资源清单四大必填字段

```yaml
apiVersion: apps/v1       # API 版本
kind: Deployment          # 资源类型
metadata:                 # 元数据
  name: nginx-demo
  labels:
    app: nginx
spec:                     # 期望状态（核心）
  replicas: 3
  ...
```

### Deployment + Service 完整示例

```yaml
# ===== Deployment =====
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-demo
  labels:
    app: nginx-demo
spec:
  replicas: 3                         # 副本数
  selector:                           # 选择器
    matchLabels:
      app: nginx-demo
  template:                           # Pod 模板
    metadata:
      labels:
        app: nginx-demo               # 必须与 selector 一致
    spec:
      containers:
        - name: nginx
          image: nginx:1.27           # 禁止使用 latest
          ports:
            - containerPort: 80
          resources:                  # 资源限制（生产必设）
            requests:
              cpu: "100m"
              memory: "128Mi"
            limits:
              cpu: "500m"
              memory: "256Mi"
          livenessProbe:              # 存活探针
            httpGet:
              path: /
              port: 80
          readinessProbe:             # 就绪探针
            httpGet:
              path: /
              port: 80

---
# ===== Service =====
apiVersion: v1
kind: Service
metadata:
  name: nginx-demo-svc
spec:
  type: NodePort                      # ClusterIP/NodePort/LoadBalancer
  selector:
    app: nginx-demo
  ports:
    - port: 80                        # Service 端口
      targetPort: 80                  # 容器端口
      nodePort: 30080                 # Node 端口（30000-32767）
```

### Service 类型

| 类型 | 说明 | 使用场景 |
|------|------|----------|
| **ClusterIP** | 仅集群内部访问（默认） | 内部微服务通信 |
| **NodePort** | 通过节点 IP+端口暴露 | 开发测试 |
| **LoadBalancer** | 云厂商负载均衡器 | 生产对外服务 |
| **ExternalName** | DNS CNAME 映射 | 跨命名空间/外部服务 |

### 快速生成 YAML 模板

```bash
# 方式一：dry-run 生成（推荐）
kubectl create deployment web --image=nginx -o yaml --dry-run=client > nginx.yaml

# 方式二：从已有资源导出
kubectl get deploy nginx -o yaml > nginx.yaml

# 查看字段文档
kubectl explain deployment.spec
```

---

## 七、配置与公开服务

### 暴露服务的三种方式

```bash
# 1. NodePort（节点端口映射）
kubectl expose deployment nginx --port=80 --type=NodePort --target-port=80

# 2. Port Forward（本地转发，仅调试）
kubectl port-forward deployment/nginx 8080:80

# 3. Ingress（生产推荐）
# 需要先安装 Ingress Controller（如 nginx-ingress）
kubectl apply -f ingress.yaml
```

### Ingress 示例

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: nginx-ingress
spec:
  rules:
    - host: app.example.com
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: nginx-svc
                port:
                  number: 80
```

---

## 八、Portainer 管理集群

Portainer 是一款轻量级的容器管理 UI，也支持 Kubernetes 集群管理：

```bash
# 部署 Portainer
kubectl apply -f https://downloads.portainer.io/ce2-19/portainer.yaml

# 访问
# http://<node-ip>:30777
```

---

## 九、排错指南

### 标准排错流程

```
kubectl apply -f xxx.yaml
  → kubectl get pods           # 查看 Pod 状态
  → kubectl describe pod <pod> # 查看事件详情
  → kubectl logs <pod>         # 查看日志
  → kubectl get endpoints      # 检查 Service 端点
```

### 常见问题排查

| 症状 | 可能原因 | 排查方法 |
|------|----------|----------|
| Pod Pending | 资源不足、调度失败 | `describe pod` 看 Events |
| Pod CrashLoopBackOff | 容器启动失败 | `logs` 查看启动日志 |
| Pod ImagePullBackOff | 镜像拉取失败 | 检查镜像名/仓库认证 |
| Service 不通 | selector 不匹配 | 对比 Service selector 与 Pod labels |
| PVC Pending | 没有可用 PV | `describe pvc` 查看绑定状态 |

---

## 十、避坑要点

1. ❌ **不要硬编码 Pod IP** — Pod 重建后 IP 会变，始终通过 Service 访问
2. ❌ **selector 与 labels 必须一致** — 不一致会导致 Service 找不到后端
3. ✅ **生产环境必须设置 resources** — `requests` 和 `limits` 防止资源争抢
4. ❌ **禁止使用 `latest` 标签** — 必须指定具体版本号，确保可复现
5. ✅ **Liveness ≠ Readiness** — Liveness 失败→重启容器；Readiness 失败→摘除流量，不重启
6. ⚠️ **Secret 仅是 Base64 编码** — 不是加密，生产环境配合外部密钥管理（Vault/Sealed Secrets）
7. ❌ **YAML 缩进只能用空格** — 禁止用 Tab，和 Makefile 正好相反
8. ✅ **先用 dry-run 生成 YAML** — 避免手写格式错误

---

> 💡 推荐学习路线：本课程（1 小时入门）→ 两小时进阶版 → 云原生全栈架构师系列
