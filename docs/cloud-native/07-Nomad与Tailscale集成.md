# 🔗 Nomad 与 Tailscale 集成

## 📖 概述

**Tailscale** 是一个基于 WireGuard 的零配置 VPN 解决方案，与 Nomad 结合可以实现**跨云、跨数据中心的安全互联**。

本文档详细介绍如何在 AI 算力共享平台中集成 Nomad 和 Tailscale。

---

## 🎯 为什么需要 Tailscale？

### 传统方案的痛点

❌ **传统 VPN**
- 配置复杂
- 需要中心化服务器
- 性能瓶颈
- 维护成本高

❌ **公网暴露**
- 安全风险高
- 需要防火墙规则
- IP 管理困难

### Tailscale 的优势

✅ **零配置**
- 自动建立 P2P 连接
- 无需配置路由表
- 自动 NAT 穿透

✅ **高性能**
- 点对点直连
- 基于 WireGuard
- 低延迟

✅ **安全**
- 端到端加密
- 基于身份认证
- 细粒度 ACL

✅ **跨平台**
- 支持 Linux、macOS、Windows
- 支持容器环境
- 统一的网络层

---

## 🏗️ 架构设计

### 整体架构

```
┌──────────────────────────────────────────────────────┐
│                  Tailscale Network                   │
│                  (100.64.0.0/10)                     │
│                                                      │
│  ┌────────────────┐       ┌────────────────┐        │
│  │   AWS Region   │       │   GCP Region   │        │
│  │                │       │                │        │
│  │ Nomad Server   │◄─────►│ Nomad Server   │        │
│  │ 100.64.1.10    │       │ 100.64.2.10    │        │
│  │                │       │                │        │
│  │ Nomad Client   │       │ Nomad Client   │        │
│  │ 100.64.1.20    │       │ 100.64.2.20    │        │
│  │  + GPU         │       │  + GPU         │        │
│  └────────────────┘       └────────────────┘        │
│                                                      │
│  ┌────────────────┐       ┌────────────────┐        │
│  │  On-Premise    │       │  Edge Node     │        │
│  │                │       │                │        │
│  │ Nomad Client   │       │ Nomad Client   │        │
│  │ 100.64.3.20    │       │ 100.64.4.20    │        │
│  │  + GPU         │       │  + GPU         │        │
│  └────────────────┘       └────────────────┘        │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### 网络拓扑

```
Tailscale Mesh Network
├── AWS VPC (172.31.0.0/16)
│   ├── Nomad Server: 100.64.1.10 (Tailscale IP)
│   └── Nomad Client: 100.64.1.20
├── GCP VPC (10.0.0.0/16)
│   ├── Nomad Server: 100.64.2.10
│   └── Nomad Client: 100.64.2.20
├── On-Premise (192.168.1.0/24)
│   └── Nomad Client: 100.64.3.20
└── Edge Device
    └── Nomad Client: 100.64.4.20
```

---

## 🔧 安装配置

### 1️⃣ 安装 Tailscale

#### Linux (Ubuntu/Debian)

```bash
# 添加 Tailscale APT 仓库
curl -fsSL https://tailscale.com/install.sh | sh

# 或手动安装
curl -fsSL https://pkgs.tailscale.com/stable/ubuntu/focal.gpg | sudo apt-key add -
curl -fsSL https://pkgs.tailscale.com/stable/ubuntu/focal.list | sudo tee /etc/apt/sources.list.d/tailscale.list
sudo apt-get update && sudo apt-get install tailscale
```

#### macOS

```bash
# 使用 Homebrew
brew install tailscale

# 启动服务
sudo tailscale up
```

#### 使用 Docker

```bash
# 运行 Tailscale 容器
docker run -d \
  --name=tailscaled \
  --cap-add=NET_ADMIN \
  --cap-add=NET_RAW \
  --network=host \
  -v /var/lib/tailscale:/var/lib/tailscale \
  -v /dev/net/tun:/dev/net/tun \
  tailscale/tailscale:latest
```

### 2️⃣ 认证和连接

```bash
# 启动 Tailscale 并认证
sudo tailscale up

# 输出类似：
# To authenticate, visit:
#   https://login.tailscale.com/a/xxxxx

# 访问链接完成认证

# 查看状态
tailscale status

# 输出示例：
# 100.64.1.10  nomad-server-1   myuser@     linux   -
# 100.64.1.20  nomad-client-1   myuser@     linux   -
```

### 3️⃣ 配置 Tailscale ACL

创建 `/etc/tailscale/acl.json`：

```json
{
  "acls": [
    // Nomad Server 之间互联
    {
      "action": "accept",
      "src": ["tag:nomad-server"],
      "dst": ["tag:nomad-server:*"]
    },
    
    // Client 连接 Server
    {
      "action": "accept",
      "src": ["tag:nomad-client"],
      "dst": ["tag:nomad-server:4646,4647,4648"]
    },
    
    // Client 之间互联（用于任务通信）
    {
      "action": "accept",
      "src": ["tag:nomad-client"],
      "dst": ["tag:nomad-client:*"]
    }
  ],
  
  "tagOwners": {
    "tag:nomad-server": ["autogroup:admin"],
    "tag:nomad-client": ["autogroup:admin"]
  }
}
```

### 4️⃣ 为节点打标签

```bash
# 在 Tailscale 管理后台或使用 API
# 为 Server 节点打标签
tailscale up --advertise-tags=tag:nomad-server

# 为 Client 节点打标签
tailscale up --advertise-tags=tag:nomad-client
```

---

## ⚙️ Nomad 配置

### Server 配置

`/etc/nomad.d/server.hcl`:

```hcl
# Nomad Server 配置
server {
  enabled          = true
  bootstrap_expect = 3
  
  # 使用 Tailscale IP
  # 获取 Tailscale IP: tailscale ip -4
  server_join {
    retry_join = [
      "100.64.1.10",  # Server 1
      "100.64.2.10",  # Server 2 (GCP)
      "100.64.3.10"   # Server 3 (On-Prem)
    ]
  }
}

# 使用 Tailscale 地址
bind_addr = "100.64.1.10"  # 本节点的 Tailscale IP

# 广告地址
advertise {
  http = "100.64.1.10:4646"
  rpc  = "100.64.1.10:4647"
  serf = "100.64.1.10:4648"
}
```

### Client 配置

`/etc/nomad.d/client.hcl`:

```hcl
# Nomad Client 配置
client {
  enabled = true
  
  # 连接到 Server（使用 Tailscale IP）
  servers = [
    "100.64.1.10:4647",
    "100.64.2.10:4647",
    "100.64.3.10:4647"
  ]
  
  # 节点元数据
  meta {
    "tailscale-ip" = "100.64.1.20"
    "region"       = "aws-us-east-1"
    "gpu-type"     = "nvidia-a100"
    "gpu-count"    = "8"
  }
}

# 使用 Tailscale 地址
bind_addr = "100.64.1.20"

# 广告地址
advertise {
  http = "100.64.1.20:4646"
  rpc  = "100.64.1.20:4647"
  serf = "100.64.1.20:4648"
}
```

---

## 🚀 AI 算力共享平台实战

### 场景：GPU 任务调度

#### Job 定义

`gpu-training.nomad.hcl`:

```hcl
job "ml-training" {
  datacenters = ["dc1", "dc2", "dc3"]
  type        = "batch"
  
  group "training" {
    count = 1
    
    # 约束：必须有 GPU
    constraint {
      attribute = "${meta.gpu-count}"
      operator  = ">"
      value     = "0"
    }
    
    # 亲和性：优先选择 A100
    affinity {
      attribute = "${meta.gpu-type}"
      value     = "nvidia-a100"
      weight    = 100
    }
    
    task "train" {
      driver = "docker"
      
      config {
        image = "pytorch/pytorch:latest"
        
        command = "python"
        args    = ["/app/train.py"]
        
        # GPU 设备映射
        devices = [
          {
            host_path      = "/dev/nvidia0"
            container_path = "/dev/nvidia0"
          }
        ]
        
        # 网络模式：使用 Tailscale
        network_mode = "host"
      }
      
      # 环境变量
      env {
        # 使用 Tailscale IP 进行通信
        MASTER_ADDR = "${attr.unique.network.ip-address}"
        MASTER_PORT = "29500"
        
        # GPU 配置
        CUDA_VISIBLE_DEVICES = "0"
      }
      
      # 资源需求
      resources {
        cpu    = 4000
        memory = 16384
        
        device "nvidia/gpu" {
          count = 1
        }
      }
      
      # 模板：动态配置
      template {
        data = <<EOF
# Training Config
MASTER_ADDR={{ env "attr.unique.network.ip-address" }}
WORLD_SIZE={{ env "NOMAD_ALLOC_INDEX" }}
RANK={{ env "NOMAD_ALLOC_INDEX" }}
EOF
        destination = "local/train.env"
        env         = true
      }
    }
  }
}
```

### 分布式训练

#### 多节点训练 Job

```hcl
job "distributed-training" {
  datacenters = ["dc1", "dc2"]
  type        = "service"
  
  group "workers" {
    # 4 个 Worker
    count = 4
    
    # 分散在不同节点
    spread {
      attribute = "${node.unique.id}"
      weight    = 100
    }
    
    # 网络配置
    network {
      mode = "host"
      
      port "dist" {
        static = 29500
      }
    }
    
    task "worker" {
      driver = "docker"
      
      config {
        image = "pytorch/pytorch:latest"
        
        # 使用 Tailscale 网络
        network_mode = "host"
        
        command = "python"
        args    = [
          "/app/distributed_train.py",
          "--master-addr=${MASTER_ADDR}",
          "--master-port=29500",
          "--rank=${NOMAD_ALLOC_INDEX}",
          "--world-size=4"
        ]
      }
      
      # 动态获取 Master 地址
      template {
        data = <<EOF
{{- $allocations := nomadService "ml-training" -}}
{{- range $index, $alloc := $allocations -}}
  {{- if eq $index 0 -}}
MASTER_ADDR={{ .Address }}
  {{- end -}}
{{- end -}}
EOF
        destination = "local/master.env"
        env         = true
      }
      
      resources {
        cpu    = 8000
        memory = 32768
        
        device "nvidia/gpu" {
          count = 2
        }
      }
    }
  }
}
```

---

## 🔐 安全加固

### 1️⃣ Tailscale MagicDNS

```bash
# 启用 MagicDNS
tailscale up --accept-dns=true

# 使用 DNS 名称替代 IP
nomad server join nomad-server-1
```

### 2️⃣ Nomad ACL 配置

```hcl
# server.hcl
acl {
  enabled = true
}
```

```bash
# 初始化 ACL
nomad acl bootstrap

# 保存 Token
export NOMAD_TOKEN=<your-secret-token>
```

### 3️⃣ TLS 加密

```hcl
# server.hcl
tls {
  http = true
  rpc  = true
  
  ca_file   = "/etc/nomad.d/ca.pem"
  cert_file = "/etc/nomad.d/server.pem"
  key_file  = "/etc/nomad.d/server-key.pem"
  
  verify_server_hostname = true
  verify_https_client    = true
}
```

---

## 📊 监控和可观测性

### 1️⃣ 收集指标

```hcl
task "monitoring" {
  driver = "docker"
  
  config {
    image = "prom/prometheus:latest"
    
    # 使用 Tailscale 网络
    network_mode = "host"
    
    # 配置文件
    volumes = [
      "local/prometheus.yml:/etc/prometheus/prometheus.yml"
    ]
  }
  
  # Prometheus 配置
  template {
    data = <<EOF
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'nomad'
    tailscale: true
    static_configs:
      - targets: 
        - '100.64.1.10:4646'
        - '100.64.2.10:4646'
        - '100.64.3.10:4646'
EOF
    destination = "local/prometheus.yml"
  }
}
```

### 2️⃣ 日志收集

```hcl
task "log-collector" {
  driver = "docker"
  
  config {
    image = "fluent/fluent-bit:latest"
    
    # 配置
    volumes = [
      "local/fluent-bit.conf:/fluent-bit/etc/fluent-bit.conf"
    ]
  }
  
  template {
    data = <<EOF
[INPUT]
    Name tail
    Path /var/log/nomad/*.log
    
[OUTPUT]
    Name  http
    Match *
    # 使用 Tailscale IP
    Host  100.64.5.10
    Port  9880
    URI   /logs
EOF
    destination = "local/fluent-bit.conf"
  }
}
```

---

## 💡 最佳实践

### 1️⃣ 网络规划

- 使用 Tailscale 的 `100.64.0.0/10` 子网
- 为不同区域分配不同的子网段
- 使用 MagicDNS 简化配置

### 2️⃣ 高可用

- Server 节点跨云部署
- 使用 Tailscale 的自动故障转移
- 配置健康检查

### 3️⃣ 性能优化

- 启用 Tailscale 的直连模式
- 使用就近的 DERP 服务器
- 监控网络延迟

### 4️⃣ 成本优化

- GPU 节点按需启动
- 使用 Spot 实例
- 跨云调度避免数据传输费用

---

## 🐛 故障排查

### 问题1：节点无法通信

```bash
# 检查 Tailscale 状态
tailscale status

# 检查连接
tailscale ping 100.64.1.10

# 查看路由
tailscale netcheck
```

### 问题2：任务调度失败

```bash
# 查看节点状态
nomad node status

# 查看详细日志
nomad alloc logs -stderr <alloc-id>

# 检查网络连通性
nomad alloc exec <alloc-id> ping 100.64.1.10
```

### 问题3：性能问题

```bash
# 查看 Tailscale 连接类型
tailscale netcheck

# 期望输出：direct (P2P直连)
# 如果是 relay，说明没有建立直连

# 检查 NAT 穿透
tailscale debug derp
```

---

## 🎯 核心要点

- ✅ Tailscale + Nomad 的架构设计
- ✅ Tailscale 的安装和配置
- ✅ 跨云 Nomad 集群配置
- ✅ GPU 任务调度实战
- ✅ 监控和故障排查方法

---

## 📚 参考资源

- [Tailscale 官方文档](https://tailscale.com/docs)
- [Nomad 网络配置](https://www.nomadproject.io/docs/job-specification/network)
- [WireGuard 协议](https://www.wireguard.com/)

---

💡 **技术提示**：AI 算力共享平台的核心技术栈实践总结，可直接应用于实际项目。

🔄 持续更新中... | 最后更新：2025年10月

