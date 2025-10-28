# 📚 Nomad 核心概念

## 📖 概述

深入理解 Nomad 的核心概念是掌握 Nomad 的关键。本章详细讲解 Job、Task Group、Task、Allocation 等核心组件。

---

## 🎯 核心概念层级

```
Job (作业)
  └── Task Group (任务组)
        ├── Task 1 (任务1)
        ├── Task 2 (任务2)
        └── Network (网络)
              ├── Allocation 1 (实例1)
              └── Allocation 2 (实例2)
```

---

## 📦 Job (作业)

### 什么是 Job？

**Job** 是 Nomad 的**基本调度单元**，定义了需要运行的工作负载。

### Job 的组成

```hcl
job "my-app" {
  # 基本属性
  datacenters = ["dc1", "dc2"]  # 数据中心
  type        = "service"        # 类型
  priority    = 50               # 优先级（1-100）
  
  # 更新策略
  update {
    max_parallel = 2
    stagger      = "30s"
  }
  
  # 任务组
  group "web" {
    # ...
  }
}
```

### Job 类型（Type）

#### 1️⃣ Service（服务型）

**特点**：
- 长期运行的服务
- 自动重启失败的任务
- 支持滚动更新

**适用场景**：
- Web 服务
- API 服务
- 数据库
- 消息队列

**示例**：

```hcl
job "web-server" {
  type = "service"
  
  group "web" {
    count = 3  # 运行 3 个实例
    
    task "nginx" {
      driver = "docker"
      
      config {
        image = "nginx:latest"
        ports = ["http"]
      }
      
      resources {
        cpu    = 500
        memory = 256
      }
    }
  }
}
```

#### 2️⃣ Batch（批处理型）

**特点**：
- 运行完成后退出
- 不自动重启
- 适合一次性任务

**适用场景**：
- 数据处理
- 报表生成
- 备份任务
- ETL 任务

**示例**：

```hcl
job "data-import" {
  type = "batch"
  
  group "import" {
    task "import-script" {
      driver = "docker"
      
      config {
        image = "python:3.9"
        command = "python"
        args = ["import.py"]
      }
      
      resources {
        cpu    = 1000
        memory = 1024
      }
    }
  }
}
```

#### 3️⃣ System（系统型）

**特点**：
- 每个 Client 节点运行一个实例
- 类似 Kubernetes 的 DaemonSet
- 节点加入时自动部署

**适用场景**：
- 日志收集
- 监控 Agent
- 网络代理
- 安全扫描

**示例**：

```hcl
job "monitoring-agent" {
  type = "system"
  
  group "agent" {
    task "node-exporter" {
      driver = "docker"
      
      config {
        image = "prom/node-exporter:latest"
        network_mode = "host"
      }
      
      resources {
        cpu    = 100
        memory = 128
      }
    }
  }
}
```

### Job 优先级（Priority）

```hcl
job "critical-service" {
  # 优先级：1-100，数值越大优先级越高
  priority = 80
  
  # 高优先级的 Job 会优先获得资源
  # 在资源紧张时，低优先级的 Job 可能被抢占
}
```

---

## 👥 Task Group (任务组)

### 什么是 Task Group？

**Task Group** 是一组**相关任务的集合**，类似 Kubernetes 的 Pod。

### 特点

- 同一 Task Group 内的任务**运行在同一节点**
- 共享网络命名空间
- 共享存储卷
- 作为一个整体进行调度

### 基本配置

```hcl
group "cache" {
  # 实例数量
  count = 3
  
  # 重启策略
  restart {
    attempts = 3
    interval = "5m"
    delay    = "25s"
    mode     = "fail"
  }
  
  # 更新策略
  update {
    max_parallel     = 1
    health_check     = "checks"
    min_healthy_time = "10s"
    healthy_deadline = "5m"
  }
  
  # 任务定义
  task "redis" {
    # ...
  }
  
  task "sidecar" {
    # ...
  }
}
```

### 约束条件（Constraints）

```hcl
group "gpu-task" {
  # 必须有 GPU
  constraint {
    attribute = "${attr.gpu.present}"
    value     = "true"
  }
  
  # 只在特定数据中心
  constraint {
    attribute = "${node.datacenter}"
    value     = "dc1"
  }
  
  # 避免特定节点类别
  constraint {
    attribute = "${node.class}"
    operator  = "!="
    value     = "spot"
  }
}
```

### 亲和性（Affinity）

```hcl
group "web" {
  # 软约束：优先选择有 SSD 的节点
  affinity {
    attribute = "${meta.storage_type}"
    value     = "ssd"
    weight    = 75  # 权重：-100 到 100
  }
  
  # 优先选择 CPU 较多的节点
  affinity {
    attribute = "${attr.cpu.numcores}"
    operator  = ">"
    value     = "4"
    weight    = 50
  }
}
```

### 反亲和性（Spread）

```hcl
group "database" {
  # 跨节点分布（高可用）
  spread {
    attribute = "${node.unique.id}"
    weight    = 100
  }
  
  # 跨机架分布
  spread {
    attribute = "${meta.rack}"
    weight    = 50
  }
}
```

---

## ⚙️ Task (任务)

### 什么是 Task？

**Task** 是 Nomad 中的**最小执行单元**，定义了具体要运行的工作。

### 基本配置

```hcl
task "web-server" {
  # 驱动类型
  driver = "docker"
  
  # 驱动配置
  config {
    image = "nginx:alpine"
    ports = ["http", "https"]
    
    volumes = [
      "/host/path:/container/path"
    ]
  }
  
  # 环境变量
  env {
    APP_ENV = "production"
    LOG_LEVEL = "info"
  }
  
  # 资源需求
  resources {
    cpu    = 500   # MHz
    memory = 256   # MB
    
    network {
      mbits = 10
      port "http" {
        static = 80
      }
      port "https" {
        static = 443
      }
    }
  }
  
  # 服务注册
  service {
    name = "nginx"
    port = "http"
  }
}
```

---

## 🚗 Driver (驱动)

### 支持的驱动类型

#### 1️⃣ Docker 驱动

```hcl
task "app" {
  driver = "docker"
  
  config {
    image = "myapp:latest"
    
    # 端口映射
    ports = ["http"]
    
    # 卷挂载
    volumes = [
      "/data:/app/data",
      "secrets:/app/secrets:ro"
    ]
    
    # 资源限制
    cpu_hard_limit = true
    memory_hard_limit = 512
    
    # 日志配置
    logging {
      type = "json-file"
      config {
        max-size = "10m"
        max-file = "3"
      }
    }
    
    # 健康检查
    healthchecks {
      disable = false
    }
  }
}
```

#### 2️⃣ Exec 驱动

```hcl
task "script" {
  driver = "exec"
  
  config {
    command = "/bin/bash"
    args    = ["-c", "echo Hello World"]
  }
}
```

#### 3️⃣ Java 驱动

```hcl
task "java-app" {
  driver = "java"
  
  config {
    jar_path    = "local/app.jar"
    jvm_options = ["-Xmx512m", "-Xms256m"]
    args        = ["--spring.profiles.active=prod"]
  }
}
```

#### 4️⃣ Raw Exec 驱动

```hcl
task "binary" {
  driver = "raw_exec"
  
  config {
    command = "/usr/local/bin/myapp"
    args    = ["--config", "/etc/myapp.conf"]
  }
}
```

---

## 📌 Allocation (分配)

### 什么是 Allocation？

**Allocation** 是 Task Group 在某个节点上的**实际运行实例**。

### 生命周期

```
Pending → Running → Complete/Failed
```

#### 状态说明

| 状态 | 说明 |
|:---|:---|
| **pending** | 等待调度 |
| **running** | 正在运行 |
| **complete** | 成功完成（batch 类型）|
| **failed** | 运行失败 |
| **lost** | 节点失联 |

### 查看 Allocation

```bash
# 列出所有 Allocation
nomad alloc status

# 查看特定 Allocation
nomad alloc status <alloc-id>

# 查看日志
nomad alloc logs <alloc-id>

# 进入容器
nomad alloc exec <alloc-id> /bin/sh
```

---

## 🔄 Evaluation (评估)

### 什么是 Evaluation？

**Evaluation** 是 Nomad 的**调度决策过程**。

### 触发场景

- 提交新 Job
- 更新现有 Job
- 节点状态变化
- Allocation 失败

### 查看 Evaluation

```bash
# 查看 Evaluation 状态
nomad eval status <eval-id>

# 输出示例：
# ID                  = abc123
# Status              = complete
# Type                = service
# TriggeredBy         = job-register
# Job ID              = my-app
# Priority            = 50
# Placement Failures  = false
```

---

## 🔁 更新策略（Update Strategy）

### 滚动更新

```hcl
job "web" {
  update {
    # 每次更新的并行数
    max_parallel = 2
    
    # 健康检查
    health_check = "checks"
    
    # 最小健康时间
    min_healthy_time = "10s"
    
    # 健康检查超时
    healthy_deadline = "5m"
    
    # 自动回滚
    auto_revert = true
    
    # 自动提升（Canary）
    auto_promote = false
    
    # Canary 数量
    canary = 1
  }
}
```

### Canary 部署

```hcl
job "api" {
  group "api-server" {
    count = 10
    
    update {
      # 先部署 2 个 Canary 实例
      canary = 2
      
      # 手动提升
      auto_promote = false
      
      # 健康检查
      health_check = "checks"
      min_healthy_time = "30s"
    }
  }
}
```

**操作流程**：

```bash
# 1. 提交更新
nomad job run app.nomad.hcl

# 2. 观察 Canary
nomad deployment status <deployment-id>

# 3. 手动提升（如果 Canary 正常）
nomad deployment promote <deployment-id>

# 4. 或者回滚
nomad deployment fail <deployment-id>
```

---

## 📊 资源调度

### 资源类型

```hcl
resources {
  # CPU（MHz）
  cpu    = 500
  
  # 内存（MB）
  memory = 256
  
  # 网络带宽（Mbps）
  network {
    mbits = 10
  }
  
  # 设备（GPU等）
  device "nvidia/gpu" {
    count = 1
    
    constraint {
      attribute = "${device.attr.memory}"
      operator  = ">"
      value     = "8GB"
    }
  }
}
```

### 资源超售（Oversubscription）

```hcl
task "app" {
  resources {
    cpu    = 500
    memory = 256
    
    # 内存超售保留
    memory_max = 512
  }
}
```

---

## 🎯 本章小结

- ✅ 理解 Job、Task Group、Task 的层级关系
- ✅ 掌握 Service、Batch、System 三种 Job 类型
- ✅ 学会使用约束和亲和性控制调度
- ✅ 了解 Allocation 和 Evaluation 的作用
- ✅ 掌握更新策略和 Canary 部署

---

## 📚 下一步学习

- 🏗️ [03-Nomad实战部署](03-Nomad实战部署.md) - 搭建生产级集群
- 🔧 [04-Nomad作业编排](04-Nomad作业编排.md) - 复杂 Job 配置
- 🌐 [05-Nomad网络与服务发现](05-Nomad网络与服务发现.md) - 网络和 Consul 集成

---

🔄 持续更新中... | 最后更新：2025年10月

