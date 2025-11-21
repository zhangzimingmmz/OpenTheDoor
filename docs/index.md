# 🚀 OpenTheDoor - 知识库

!!! abstract "核心理念"
    **📖 原理优先 · 📝 实战驱动 · 💡 系统积累 · 🎯 持续精进**
    
    *从基础到架构的技术总结 | 计算机科学 + Java 生态 + 工程实践*

---

## 📚 已完成的技术总结

| 专题 | 描述 | 状态 |
|:---|:---|:---:|
| **[☕ Java 语言基础](java-core/README.md)** | Java 核心语法、面向对象、泛型、反射、函数式编程、集合框架 | ✅ 已完成 |
| **[🧵 Java 并发编程](java-core/07-Java并发编程.md)** | 线程、同步、锁、线程池、并发集合、CAS、设计模式、性能优化 | ✅ 已完成 |
| **[🔐 认证授权](auth/01-认证授权基础.md)** | Spring Security、OAuth2、JWT、SSO 完整教程 | ✅ 已完成 |
| **[🚀 Nomad 容器编排](cloud-native/README.md)** | 轻量级编排、Tailscale 集成、AI 算力平台实战 | ✅ 已完成 |
| **[📬 Kafka 消息队列](middleware/01-Kafka基础入门.md)** | Kafka 架构、核心概念、Spring Boot 集成实战 | ✅ 已完成 |
| **[📝 博客搭建](devops/01-博客搭建指南.md)** | MkDocs + GitHub Pages 搭建技术博客 | ✅ 已完成 |
| **[🐹 Go 语言系列](go/README.md)** | 专为 Java 开发者设计的 Go 语言学习路径 | ✅ 已完成 |

---

## ☕ 一、Java 核心技术

!!! tip "⭐⭐⭐⭐⭐ 面试高频 | 决定你的技术深度和代码质量"

### 📘 Java 语言基础 🟢 已完成

[:material-book-open-variant: 完整导航](java-core/README.md){ .md-button .md-button--primary }

**📚 核心内容**

- **[语法基础](java-core/01-Java语法基础.md)** ✅ - 数据类型、运算符、控制流、异常处理、字符串、数组
- **[面向对象](java-core/02-面向对象编程.md)** ✅ - 类与对象、封装、继承、多态、接口、抽象类、内部类
- **[高级特性](java-core/03-Java高级特性.md)** ✅ - 泛型、注解、反射、动态代理、枚举、SPI机制
- **[函数式编程](java-core/04-函数式编程.md)** ✅ - Lambda、Stream API、Optional、方法引用
- **[新版本特性](java-core/05-Java新版本特性.md)** ✅ - Java 8~21（Record、Sealed Class、Virtual Thread）
- **[集合框架](java-core/06-Java集合框架.md)** ✅ - HashMap、ConcurrentHashMap、ArrayList源码分析

**🎯 核心要点**

- Java核心语法和面向对象思想
- 泛型、反射等高级特性的原理和应用
- Lambda和Stream函数式编程
- 集合框架底层实现与性能对比
- Java各版本的重要新特性
- 面试高频知识点

---

### 🗂️ Java 集合框架 🟢 已完成

[:material-book-open-variant: 完整教程](java-core/06-Java集合框架.md){ .md-button .md-button--primary }

**📚 核心内容**

- **List 系列** ✅ - ArrayList、LinkedList、CopyOnWriteArrayList 源码分析
- **Set 系列** ✅ - HashSet、TreeSet、LinkedHashSet
- **Map 系列** ✅ - HashMap、ConcurrentHashMap、TreeMap、LinkedHashMap 深入解析
- **Queue 系列** ✅ - PriorityQueue、ArrayDeque、BlockingQueue
- **集合工具** ✅ - Collections、Arrays 常用方法

**🎯 核心要点**

- HashMap 1.7 vs 1.8 优化（数组+链表+红黑树）
- ConcurrentHashMap 并发原理（CAS+synchronized）
- ArrayList vs LinkedList 性能对比
- 集合选择指南与面试高频题

---

### 🧵 Java 并发编程 🟢 已完成

[:material-book-open-variant: 完整教程](java-core/07-Java并发编程.md){ .md-button .md-button--primary }

**📚 核心内容**

- **[线程基础](java-core/07-01-线程基础.md)** ✅ - Thread、Runnable、Callable、线程生命周期
- **[线程同步与内存模型](java-core/07-02-线程同步与内存模型.md)** ✅ - synchronized、volatile、JMM、happens-before规则
- **[锁机制](java-core/07-03-锁机制.md)** ✅ - ReentrantLock、ReadWriteLock、StampedLock
- **[并发工具类](java-core/07-04-并发工具类.md)** ✅ - CountDownLatch、CyclicBarrier、Semaphore、Exchanger、Phaser
- **[线程池](java-core/07-05-线程池.md)** ✅ - Executor框架、ThreadPoolExecutor源码、最佳实践
- **[并发集合](java-core/07-06-并发集合.md)** ✅ - ConcurrentHashMap、BlockingQueue、CopyOnWriteArrayList
- **[原子类与CAS](java-core/07-07-原子类与CAS.md)** ✅ - Atomic类、CAS原理、ABA问题、LongAdder
- **[并发设计模式](java-core/07-08-并发设计模式.md)** ✅ - 生产者消费者、ThreadLocal、Future模式、不变模式
- **[性能优化与最佳实践](java-core/07-09-性能优化与最佳实践.md)** ✅ - 性能调优、死锁/活锁/饥饿、生产环境最佳实践

**🎯 核心要点**

- 深入理解 Java 内存模型（JMM）和 happens-before 规则
- 掌握 synchronized、volatile、显式锁的使用场景
- 理解线程池原理和最佳配置实践
- 掌握 ConcurrentHashMap、BlockingQueue 等并发集合
- 理解 CAS 原理和原子类的使用
- 掌握并发设计模式和性能优化技巧
- 能排查和解决死锁、活锁、饥饿等并发问题

---

### 🔧 JVM 虚拟机 🔵 规划中

**📚 核心内容**

- **JVM 体系结构** - 类加载、运行时数据区、执行引擎
- **类加载机制** - 加载、链接、初始化、双亲委派模型
- **内存结构** - 堆、栈、方法区、程序计数器
- **垃圾回收** - GC 算法、垃圾回收器（Serial、CMS、G1、ZGC）
- **JVM 调优** - 参数配置、内存调优、GC 调优
- **性能监控** - JConsole、VisualVM、Arthas

**🎯 核心要点**

- 深入理解 JVM 运行机制
- 掌握 JVM 调优技能
- 排查内存泄漏、CPU 飙高问题

---

### 🌱 Spring 全家桶 🟡 进行中

[:material-book-open-variant: 认证授权教程](auth/01-认证授权基础.md){ .md-button }

**📚 核心内容**

**Spring Core**

- IoC & DI、Bean 生命周期、AOP、事件机制

**Spring Boot**

- 自动配置、Starter 机制、Actuator、外部化配置

**Spring Security** ✅

- 认证授权、过滤器链、OAuth2、JWT、SSO
- 📖 [完整教程](auth/01-认证授权基础.md)

**Spring Data**

- JPA、JDBC、Redis、MongoDB

**ORM 框架**

- MyBatis、MyBatis-Plus、Hibernate/JPA

**🎯 核心要点**

- 深入理解 Spring 源码和设计模式
- 掌握微服务架构开发
- 熟练使用主流开发框架

---

---

## 🐹 Go 语言系列

!!! tip "⭐⭐⭐⭐⭐ 热门趋势 | 云原生时代的通用语言"

### 📘 Go 语言学习路径 🟢 已完成

[:material-book-open-variant: 完整导航](go/README.md){ .md-button .md-button--primary }

**📚 核心内容**

- **[快速入门](go/core/01-getting-started/README.md)** ✅ - 环境搭建、VS Code配置、Hello World
- **[基础语法](go/core/02-basics/01-variables-types.md)** ✅ - 变量、控制流、函数、数据结构
- **[进阶特性](go/core/03-intermediate/01-structs-interfaces.md)** ✅ - 接口、并发编程、错误处理
- **[实战应用](go/core/05-practical/01-web-db.md)** ✅ - Web开发、数据库、RESTful API

**🎯 核心亮点**

- 专为 Java 开发者设计，包含详细的 **Java vs Go** 对比
- 14 个完整的 **示例代码**，从 Hello World 到实战项目
- 涵盖 **并发编程** (Goroutine, Channel) 核心特性
- 包含 **Web 开发** 和 **数据库操作** 实战教程

---

## 📊 二、数据结构与算法

!!! tip "⭐⭐⭐⭐⭐ 面试必考 | 算法是程序的灵魂"

### 📐 数据结构 🔵 规划中

- **线性结构** - 数组、链表、栈、队列、双端队列
- **树结构** - 二叉树、BST、AVL、红黑树、B/B+树、Trie
- **堆结构** - 二叉堆、斐波那契堆、优先队列
- **图结构** - 图的表示、遍历、最短路径、最小生成树
- **哈希表** - 哈希函数、冲突解决、一致性哈希
- **高级结构** - 并查集、跳表、布隆过滤器、LRU/LFU

---

### 🧮 算法 🔵 规划中

- **排序** - 快排、归并、堆排、桶排、计数排序
- **查找** - 二分查找、哈希查找、树查找
- **贪心** - 活动选择、背包问题
- **分治** - 快排、归并、二分
- **动态规划** - 背包、最长子序列、编辑距离
- **回溯** - N 皇后、数独、全排列
- **图算法** - DFS、BFS、Dijkstra、Floyd

---

## 🗄️ 三、数据库

!!! tip "⭐⭐⭐⭐⭐ 性能关键 | 80% 的性能问题出在数据库"

### 🐬 MySQL 🔵 规划中

- **SQL 基础** - DDL、DML、DCL、查询语法
- **索引优化** - B+树、聚簇索引、覆盖索引、最左前缀
- **执行计划** - EXPLAIN 详解、优化器、查询优化
- **事务机制** - ACID、隔离级别、MVCC、undo/redo log
- **锁机制** - 表锁、行锁、间隙锁、死锁处理
- **高可用** - 主从复制、读写分离、MGR、MHA
- **分库分表** - ShardingSphere
- **性能调优** - 慢查询分析、参数调优

---

### 📝 Redis 🔵 规划中

- **数据类型** - String、Hash、List、Set、ZSet、Stream
- **底层实现** - SDS、跳表、压缩列表、字典
- **持久化** - RDB、AOF、混合持久化
- **高可用** - 主从复制、哨兵模式、集群模式
- **缓存设计** - 缓存穿透、击穿、雪崩、双写一致性
- **分布式锁** - SETNX、Redisson、RedLock

---

## 🎓 四、计算机基础

!!! tip "⭐⭐⭐⭐ 基础核心 | 打好基础才能走得更远"

### 💻 计算机组成原理 🔵 规划中

数字逻辑、冯·诺依曼结构、数据运算、存储系统、指令系统、CPU 原理

---

### 🖥️ 操作系统 🔵 规划中

进程与线程、处理器调度、同步与互斥、内存管理、文件系统、I/O 管理、Linux 编程

---

### 🌐 计算机网络 🔵 规划中

OSI 七层模型、TCP/IP 协议栈、TCP（三次握手、四次挥手、拥塞控制）、HTTP/HTTPS、DNS、网络安全

---

## 🔄 五、中间件

!!! tip "⭐⭐⭐⭐ 分布式基础 | 分布式系统的基础设施"

### 📬 消息队列 🟡 进行中

[:material-book-open-variant: 完整教程](middleware/README.md){ .md-button .md-button--primary }

**📚 核心内容**

- **[Kafka 基础入门](middleware/01-Kafka基础入门.md)** ✅ - Kafka 简介、架构、安装部署、快速开始
- **[Kafka 核心概念](middleware/02-Kafka核心概念.md)** ✅ - Topic、Partition、Producer、Consumer、Consumer Group
- **[Kafka 实战应用](middleware/03-Kafka实战应用.md)** ✅ - Spring Boot 集成、业务场景、最佳实践

**🎯 核心要点**

- Kafka 高吞吐量、持久化存储的核心原理
- Producer、Consumer、Consumer Group 的使用
- Spring Boot 集成 Kafka 实战
- 生产环境的最佳实践和故障排查

**其他消息队列** 🔵 规划中

- **RabbitMQ** - AMQP 协议、Exchange、Queue
- **RocketMQ** - 事务消息、延时消息

---

### 🔧 服务治理 🔵 规划中

Dubbo、Zookeeper、Nacos、Consul

---

## 🏗️ 六、系统架构设计

!!! tip "⭐⭐⭐⭐⭐ 架构能力 | 从工程师到架构师的分水岭"

### 🎨 设计模式 🔵 规划中

创建型、结构型、行为型模式、Spring 中的设计模式源码分析

---

### 🌐 分布式系统 🔵 规划中

CAP/BASE 理论、Paxos/Raft、分布式事务、分布式锁、分布式 ID、分布式缓存、分布式存储

---

### 📈 性能优化 🔵 规划中

JVM 调优、数据库调优、应用调优、高并发方案（限流、降级、熔断）

---

## 🔐 七、安全

!!! tip "⭐⭐⭐ 企业必备 | 企业级应用的必备技能"

### 🔒 安全基础 🟡 进行中

[:material-book-open-variant: 完整教程](auth/01-认证授权基础.md){ .md-button }

- **认证与授权** ✅ - Session、Cookie、Token、OAuth2、SAML
- **Spring Security** ✅ - 过滤器链、认证流程、授权机制
- **JWT** ✅ - 结构、使用场景、安全性
- **RBAC/ABAC** ✅ - 权限模型设计
- **SSO 单点登录** ✅ - CAS、OAuth2 实现
- **Web 安全** - XSS、CSRF、SQL 注入、HTTPS

---

## ☁️ 八、云原生技术

!!! tip "⭐⭐⭐ 云时代 | 云原生技术栈"

### 🚀 Nomad 容器编排 🟢 已完成

[:material-book-open-variant: Nomad 完整教程](cloud-native/README.md){ .md-button .md-button--primary }
[:material-link: Tailscale 集成](cloud-native/07-Nomad与Tailscale集成.md){ .md-button }

**📚 核心内容**

- **[Nomad 基础入门](cloud-native/01-Nomad基础入门.md)** ✅ - 架构、安装、快速开始
- **[Nomad 核心概念](cloud-native/02-Nomad核心概念.md)** ✅ - Job、Task、Allocation、Driver
- **[Nomad 与 Tailscale 集成](cloud-native/07-Nomad与Tailscale集成.md)** ✅ - 跨云互联、AI 算力平台实战

**🎯 核心要点**

- 掌握轻量级容器编排工具 Nomad
- 理解跨云、跨数据中心的资源调度
- 实战 AI 算力共享平台架构
- 学会 Nomad + Tailscale 的零配置网络方案

---

### 🐳 容器技术 🔵 规划中

Docker 镜像、容器、Dockerfile、网络、存储、镜像优化、Docker Compose、容器原理

---

### ⎈ Kubernetes 🔵 规划中

Pod、Service、Deployment、ConfigMap、网络、存储、调度、高可用

---

## 🛠️ 九、DevOps 工具链

!!! tip "⭐⭐⭐ 效率提升 | 现代化开发必备"

### 📝 文档与博客 🟢 已完成

[:material-book-open-variant: 搭建指南](devops/01-博客搭建指南.md){ .md-button }

- **MkDocs** ✅ - 技术文档、GitHub Pages
- Obsidian、Markdown、技术博客写作

---

## 📊 内容统计

| 📈 维度 | 📊 数据 |
|:---:|:---|
| **知识模块** | 14 大类 70+ 小类 |
| **已完成** | Java语言基础、Java集合框架、Java并发编程、认证授权、Nomad、Kafka、博客搭建、Go语言系列 |
| **进行中** | Spring、容器技术、云原生工具 |
| **总文档** | 35+ 篇，持续增加中 |

---

## 📞 关于作者

**基本信息**

- 💼 7年 Java 开发工程师
- 🎯 云原生 · 微服务 · 系统架构
- 📈 从外包到技术专家的成长之路
- 💪 相信系统化积累的力量

**技术方向**

- ☕ 擅长：Java 生态系统、Docker & Kubernetes、微服务架构
- 🖥️ 项目：AI 算力共享平台、Nomad + Tailscale 实践
- 📚 方向：编程基础、架构思维、系统设计

**联系方式**

- [:fontawesome-brands-github: GitHub](https://github.com/zhangzimingmmz)
- [:material-web: 博客](https://zhangzimingmmz.github.io/OpenTheDoor/)

---

!!! quote "💡 技术理念"
    **"总结不是为了应付面试，而是为了系统化沉淀技术经验"**

    ⭐ 如果这个知识库对你有帮助，欢迎 Star 支持  
        
    🔄 持续更新中... | 最后更新：2025年10月29日

---

<div align="center">

[:material-book-open-variant: 开始学习](java-core/README.md){ .md-button .md-button--primary }
[:material-github: 在线阅读](https://zhangzimingmmz.github.io/OpenTheDoor/){ .md-button }
[:material-message-text: 交流反馈](https://github.com/zhangzimingmmz/OpenTheDoor/issues){ .md-button }

</div>
