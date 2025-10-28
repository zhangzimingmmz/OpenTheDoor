# 🚀 Nomad 基础入门

## 📖 什么是 Nomad？

**Nomad** 是 HashiCorp 公司开发的一个**轻量级、灵活的容器编排和任务调度工具**。

### 核心特点

- **简单易用** - 单一二进制文件，配置简洁
- **灵活调度** - 支持容器、虚拟机、Java应用、二进制程序
- **多区域支持** - 跨数据中心、跨云联邦
- **高性能** - 每秒可处理数百万次调度决策
- **HashiCorp 生态** - 与 Consul、Vault 无缝集成

---

## 🏗️ Nomad 架构

### 整体架构

```
┌─────────────────────────────────────────────────┐
│              Nomad Cluster                      │
│                                                 │
│  ┌──────────────┐      ┌──────────────┐        │
│  │ Server Node  │◄────►│ Server Node  │        │
│  │  (Leader)    │      │  (Follower)  │        │
│  └──────┬───────┘      └──────────────┘        │
│         │                                       │
│         │ RPC/HTTP                              │
│         ▼                                       │
│  ┌──────────────┐      ┌──────────────┐        │
│  │ Client Node  │      │ Client Node  │        │
│  │  (Runner)    │      │  (Runner)    │        │
│  └──────────────┘      └──────────────┘        │
└─────────────────────────────────────────────────┘
```

### 核心组件

#### 1️⃣ Server 节点（管理节点）

**职责**：
- 集群管理和协调
- 任务调度决策
- 状态存储
- API 服务

**特点**：
- 使用 Raft 协议保证一致性
- 建议部署 3 或 5 个节点
- 自动选举 Leader

#### 2️⃣ Client 节点（工作节点）

**职责**：
- 执行任务
- 资源监控
- 上报状态
- 驱动管理

**特点**：
- 可以有任意数量的 Client
- 支持多种驱动（Docker、QEMU、Java、Exec等）
- 自动注册和注销

---

## 🔧 安装部署

### 方式一：下载二进制文件

#### 1. 下载

```bash
# Linux
wget https://releases.hashicorp.com/nomad/1.7.2/nomad_1.7.2_linux_amd64.zip
unzip nomad_1.7.2_linux_amd64.zip

# macOS
brew install nomad

# 或手动下载
wget https://releases.hashicorp.com/nomad/1.7.2/nomad_1.7.2_darwin_amd64.zip
unzip nomad_1.7.2_darwin_amd64.zip
```

#### 2. 安装

```bash
# 移动到系统路径
sudo mv nomad /usr/local/bin/

# 验证安装
nomad version
```

### 方式二：使用包管理器

```bash
# Ubuntu/Debian
sudo apt-get update && sudo apt-get install nomad

# CentOS/RHEL
sudo yum install nomad

# macOS
brew install nomad
```

---

## 🎯 快速开始

### 1️⃣ 启动开发模式（单节点）

```bash
# 启动 Nomad 开发服务器
# Development mode - 用于学习和测试
nomad agent -dev

# 输出示例：
# ==> No configuration files loaded
# ==> Starting Nomad agent...
# ==> Nomad agent configuration:
#        Datacenter: dc1
#          Log Level: INFO
#            Version: 1.7.2
```

**开发模式特点**：
- ✅ Server 和 Client 合二为一
- ✅ 数据存储在内存中
- ✅ 自动启用 UI
- ⚠️ 重启后数据丢失
- ⚠️ 不适合生产环境

### 2️⃣ 访问 Web UI

打开浏览器访问：**http://localhost:4646**

你会看到：
- 📊 集群状态
- 📦 Job 列表
- 🖥️ 节点信息
- 📈 资源使用情况

### 3️⃣ 使用 CLI 查看状态

```bash
# 查看集群节点
nomad node status

# 查看所有 Job
nomad job status

# 查看 Server 成员
nomad server members
```

---

## 📝 运行第一个 Job

### 1️⃣ 创建 Job 配置文件

创建 `example.nomad.hcl`：

```hcl
# Job 定义（Job Definition）
# Job 是 Nomad 的基本调度单元
job "example" {
  # 数据中心（Datacenter）
  # 指定 Job 运行的数据中心
  datacenters = ["dc1"]
  
  # 类型（Type）
  # service - 长期运行的服务
  # batch - 批处理任务
  # system - 系统守护进程（每个节点一个）
  type = "service"
  
  # 任务组（Task Group）
  # 包含一个或多个任务
  group "cache" {
    # 实例数量（Count）
    # 指定运行多少个实例
    count = 1
    
    # 任务（Task）
    # 实际要执行的工作
    task "redis" {
      # 驱动（Driver）
      # 指定如何运行任务
      driver = "docker"
      
      # 配置（Config）
      # 驱动特定的配置
      config {
        image = "redis:7-alpine"
        ports = ["db"]
      }
      
      # 资源（Resources）
      # 指定所需的计算资源
      resources {
        cpu    = 500  # MHz
        memory = 256  # MB
      }
      
      # 服务（Service）
      # 服务注册和健康检查
      service {
        name = "redis-cache"
        port = "db"
        
        check {
          name     = "alive"
          type     = "tcp"
          interval = "10s"
          timeout  = "2s"
        }
      }
    }
    
    # 网络（Network）
    # 定义网络端口
    network {
      port "db" {
        static = 6379
      }
    }
  }
}
```

### 2️⃣ 验证 Job 配置

```bash
# 验证语法
nomad job validate example.nomad.hcl

# 查看执行计划（Dry Run）
nomad job plan example.nomad.hcl
```

### 3️⃣ 运行 Job

```bash
# 运行 Job
nomad job run example.nomad.hcl

# 输出示例：
# ==> 2025-10-28T10:00:00+08:00: Monitoring evaluation "abc123"
#     Evaluation triggered by job "example"
# ==> 2025-10-28T10:00:01+08:00: Evaluation within deployment: "def456"
# ==> 2025-10-28T10:00:01+08:00: Allocation "ghi789" created: node "xyz"
# ==> 2025-10-28T10:00:02+08:00: Evaluation status changed: "pending" -> "complete"
# ==> Evaluation "abc123" finished with status "complete"
```

### 4️⃣ 查看 Job 状态

```bash
# 查看 Job 状态
nomad job status example

# 查看详细信息
nomad job status -verbose example

# 查看 Job 分配
nomad alloc status <alloc-id>
```

### 5️⃣ 查看日志

```bash
# 查看实时日志
nomad alloc logs -f <alloc-id>

# 查看特定任务的日志
nomad alloc logs -f <alloc-id> redis

# 查看 stderr
nomad alloc logs -f -stderr <alloc-id>
```

### 6️⃣ 测试 Redis 连接

```bash
# 连接到 Redis
redis-cli -h localhost -p 6379

# 测试命令
127.0.0.1:6379> PING
PONG
127.0.0.1:6379> SET test "Hello Nomad"
OK
127.0.0.1:6379> GET test
"Hello Nomad"
```

---

## 🎛️ 常用 CLI 命令

### Job 管理

```bash
# 运行 Job
nomad job run job.nomad.hcl

# 停止 Job
nomad job stop example

# 删除 Job
nomad job stop -purge example

# 更新 Job
nomad job run -check-index <index> job.nomad.hcl

# 重启 Job
nomad job restart example
```

### 查询命令

```bash
# 查看所有 Job
nomad job status

# 查看节点列表
nomad node status

# 查看分配
nomad alloc status

# 查看 Server 成员
nomad server members
```

### 监控命令

```bash
# 监控 Evaluation
nomad eval status <eval-id>

# 监控 Deployment
nomad deployment status <deployment-id>

# 查看节点资源
nomad node status -stats <node-id>
```

---

## 📊 Web UI 功能

访问 `http://localhost:4646` 可以看到：

### 1️⃣ Jobs 页面

- 所有 Job 列表
- Job 状态（running、pending、dead）
- 资源使用情况

### 2️⃣ Clients 页面

- 所有 Client 节点
- 节点资源信息
- 节点健康状态

### 3️⃣ Servers 页面

- Server 节点列表
- Leader 信息
- Raft 状态

### 4️⃣ Topology 页面

- 集群拓扑可视化
- 节点分布
- Job 分配情况

---

## 🔑 核心概念速览

| 概念 | 说明 | 类比 |
|:---|:---|:---|
| **Job** | 调度的基本单元 | Kubernetes 的 Deployment |
| **Task Group** | 一组相关任务 | Kubernetes 的 Pod |
| **Task** | 具体的工作任务 | Kubernetes 的 Container |
| **Allocation** | Job 在节点上的实例 | Kubernetes 的 Pod 实例 |
| **Evaluation** | 调度决策过程 | Kubernetes 的 Scheduler |
| **Driver** | 任务执行器 | 容器运行时 |

---

## 💡 最佳实践

### 1️⃣ 开发环境

```bash
# 使用 -dev 模式快速测试
nomad agent -dev

# 使用 -dev-connect 启用 Consul Connect
nomad agent -dev -dev-connect
```

### 2️⃣ 配置管理

```bash
# 使用环境变量
export NOMAD_ADDR=http://localhost:4646

# 使用配置文件
nomad agent -config=/etc/nomad.d/
```

### 3️⃣ 安全考虑

```bash
# 生产环境启用 ACL
nomad acl bootstrap

# 启用 TLS
nomad agent -config=tls.hcl
```

---

## 🐛 常见问题

### Q1: 如何停止开发模式的 Nomad？

```bash
# 按 Ctrl + C 停止

# 或发送信号
kill <nomad-pid>
```

### Q2: 如何清理所有 Job？

```bash
# 停止所有 Job
nomad job stop -purge $(nomad job status | awk 'NR>1 {print $1}')
```

### Q3: 如何查看更详细的日志？

```bash
# 启动时指定日志级别
nomad agent -dev -log-level=DEBUG

# 或修改配置文件
log_level = "DEBUG"
```

---

## 📚 相关文档

- 📖 [02-Nomad核心概念](02-Nomad核心概念.md) - 深入理解 Job、Task、Allocation
- 🏗️ [03-Nomad实战部署](03-Nomad实战部署.md) - 生产级集群搭建
- 🔧 [04-Nomad作业编排](04-Nomad作业编排.md) - 复杂 Job 配置

---

## 🎯 核心要点

- ✅ Nomad 的定位和优势
- ✅ Nomad 的基本架构
- ✅ 安装和启动方式
- ✅ 运行第一个 Job
- ✅ 基本的 CLI 命令

---

## 🔗 参考资源

- [Nomad 官方文档](https://www.nomadproject.io/docs)
- [Nomad Getting Started](https://learn.hashicorp.com/collections/nomad/get-started)
- [Nomad GitHub](https://github.com/hashicorp/nomad)

---

💡 **技术提示**：开发模式适合快速测试和验证，实际操作加深理解。

🔄 持续更新中... | 最后更新：2025年10月

