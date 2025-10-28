# 🚀 OpenTheDoor - 终身学习知识库

<div align="center">

**📖 原理优先 · 📝 实战驱动 · 💡 系统积累 · 🎯 终身成长**

*从基础到架构的系统化学习 | 计算机科学 + Java 生态 + 工程实践*

[![GitHub stars](https://img.shields.io/github/stars/zhangzimingmmz/OpenTheDoor?style=flat-square)](https://github.com/zhangzimingmmz/OpenTheDoor)
[![GitHub forks](https://img.shields.io/github/forks/zhangzimingmmz/OpenTheDoor?style=flat-square)](https://github.com/zhangzimingmmz/OpenTheDoor/fork)

</div>

---

## 📚 已完成的学习资源

> 🎉 点击查看已完成的专题内容

| 专题 | 描述 | 状态 |
|:---|:---|:---:|
| **[☕ Java 语言基础](java-core/README.md)** | Java 核心语法、面向对象、泛型、反射、函数式编程 | ✅ 已完成 |
| **[🔐 认证授权](auth/01-认证授权基础.md)** | Spring Security、OAuth2、JWT、SSO 完整教程 | ✅ 已完成 |
| **[📝 博客搭建](devops/01-博客搭建指南.md)** | MkDocs + GitHub Pages 搭建技术博客 | ✅ 已完成 |

---

## 📖 知识体系导航

> 💡 按照**面试重要程度**和**技术深度**组织，既适合系统学习，也方便面试突击

---

## ☕ 一、Java 核心技术

> ⭐⭐⭐⭐⭐ **面试高频** | 决定你的技术深度和代码质量

<details open>
<summary><strong>📘 Java 语言基础</strong> 🟢 已完成 | <a href="java-core/README.md">📖 完整导航</a></summary>

**📚 学习内容**

- **[语法基础](java-core/01-Java语法基础.md)** ✅ - 数据类型、运算符、控制流、异常处理、字符串、数组
- **[面向对象](java-core/02-面向对象编程.md)** ✅ - 类与对象、封装、继承、多态、接口、抽象类、内部类
- **[高级特性](java-core/03-Java高级特性.md)** ✅ - 泛型、注解、反射、动态代理、枚举、SPI机制
- **[函数式编程](java-core/04-函数式编程.md)** ✅ - Lambda、Stream API、Optional、方法引用
- **[新版本特性](java-core/05-Java新版本特性.md)** ✅ - Java 8~21（Record、Sealed Class、Virtual Thread）

**🎯 学习目标**

- 掌握Java核心语法和面向对象思想
- 理解泛型、反射等高级特性的原理和应用
- 熟练使用Lambda和Stream进行函数式编程
- 了解Java各版本的重要新特性
- 面试高频知识点全覆盖

**📖 快速开始**

- 📖 [Java语言基础完整导航](java-core/README.md)
- 📄 [01-Java语法基础](java-core/01-Java语法基础.md)
- 📄 [02-面向对象编程](java-core/02-面向对象编程.md)
- 📄 [03-Java高级特性](java-core/03-Java高级特性.md)
- 📄 [04-函数式编程](java-core/04-函数式编程.md)
- 📄 [05-Java新版本特性](java-core/05-Java新版本特性.md)

</details>

<details>
<summary><strong>🗂️ Java 集合框架</strong> 🔵 规划中</summary>

**📚 学习内容**

- **List 系列** - ArrayList、LinkedList、CopyOnWriteArrayList 源码
- **Set 系列** - HashSet、TreeSet、LinkedHashSet
- **Map 系列** - HashMap、TreeMap、ConcurrentHashMap、WeakHashMap
- **Queue 系列** - PriorityQueue、BlockingQueue、Deque
- **集合工具** - Collections、Arrays、Iterator、Comparator

**🎯 学习目标**

- 理解底层实现（数组、链表、红黑树、跳表）
- 掌握性能特点和使用场景
- 面试高频考点全覆盖

</details>

<details>
<summary><strong>🧵 Java 并发编程</strong> 🔵 规划中</summary>

**📚 学习内容**

- **线程基础** - Thread、Runnable、Callable、FutureTask
- **线程安全** - synchronized、volatile、final、ThreadLocal
- **显式锁** - Lock、ReentrantLock、ReadWriteLock、StampedLock
- **AQS 框架** - AbstractQueuedSynchronizer 源码分析
- **并发工具** - CountDownLatch、CyclicBarrier、Semaphore
- **线程池** - ThreadPoolExecutor、ScheduledThreadPoolExecutor
- **并发容器** - ConcurrentHashMap、BlockingQueue
- **虚拟线程** - Java 21 Virtual Threads

**🎯 学习目标**

- 深入理解 Java 内存模型（JMM）
- 掌握并发编程最佳实践
- 能排查和解决并发问题

</details>

<details>
<summary><strong>🔧 JVM 虚拟机</strong> 🔵 规划中</summary>

**📚 学习内容**

- **JVM 体系结构** - 类加载、运行时数据区、执行引擎
- **类加载机制** - 加载、链接、初始化、双亲委派模型
- **内存结构** - 堆、栈、方法区、程序计数器
- **垃圾回收** - GC 算法、垃圾回收器（Serial、CMS、G1、ZGC）
- **JVM 调优** - 参数配置、内存调优、GC 调优
- **字节码** - 字节码指令、ASM 操作
- **性能监控** - JConsole、VisualVM、Arthas

**🎯 学习目标**

- 深入理解 JVM 运行机制
- 掌握 JVM 调优技能
- 排查内存泄漏、CPU 飙高问题

</details>

<details>
<summary><strong>🌐 Java 网络编程</strong> 🔵 规划中</summary>

**📚 学习内容**

- **BIO 编程** - Socket、ServerSocket、TCP/UDP
- **NIO 编程** - Buffer、Channel、Selector、非阻塞 I/O
- **AIO 编程** - AsynchronousChannel、CompletionHandler
- **Netty 框架** - Reactor 模型、EventLoop、Pipeline
- **序列化** - Protobuf、JSON、Kryo、Hessian
- **HTTP 客户端** - HttpClient、OkHttp、Retrofit

</details>

<details open>
<summary><strong>🌱 Spring 全家桶</strong> 🟡 进行中 | <a href="auth/01-认证授权基础.md">📖 已完成：认证授权</a></summary>

**📚 学习内容**

**Spring Core**
- IoC & DI、Bean 生命周期、AOP、事件机制

**Spring MVC**
- DispatcherServlet、HandlerMapping、视图解析

**Spring Boot**
- 自动配置、Starter 机制、Actuator、外部化配置

**Spring Cloud**
- 微服务架构、Eureka、Ribbon、Feign、Gateway、Config

**Spring Security** ✅
- 认证授权、过滤器链、OAuth2、JWT、SSO
- 📖 [完整教程](auth/01-认证授权基础.md)

**Spring Data**
- JPA、JDBC、Redis、MongoDB

**ORM 框架**
- MyBatis、MyBatis-Plus、Hibernate/JPA

**其他框架**
- Lombok、MapStruct、Quartz、Netty

</details>

---

## 📊 二、数据结构与算法

> ⭐⭐⭐⭐⭐ **面试必考** | 算法是程序的灵魂

<details>
<summary><strong>📐 数据结构</strong> 🔵 规划中</summary>

**📚 学习内容**

- **线性结构** - 数组、链表、栈、队列、双端队列
- **树结构** - 二叉树、BST、AVL、红黑树、B/B+树、Trie
- **堆结构** - 二叉堆、斐波那契堆、优先队列
- **图结构** - 图的表示、遍历、最短路径、最小生成树
- **哈希表** - 哈希函数、冲突解决、一致性哈希
- **高级结构** - 并查集、跳表、布隆过滤器、LRU/LFU

</details>

<details>
<summary><strong>🧮 算法</strong> 🔵 规划中</summary>

**📚 学习内容**

- **排序** - 快排、归并、堆排、桶排、计数排序
- **查找** - 二分查找、哈希查找、树查找
- **贪心** - 活动选择、背包问题
- **分治** - 快排、归并、二分
- **动态规划** - 背包、最长子序列、编辑距离
- **回溯** - N 皇后、数独、全排列
- **图算法** - DFS、BFS、Dijkstra、Floyd

</details>

<details>
<summary><strong>💻 LeetCode 刷题</strong> 🔵 规划中</summary>

**📚 刷题计划**

- 基础 100 题、进阶 200 题、高级 100 题
- 剑指 Offer、Hot 100、周赛/双周赛

</details>

---

## 🗄️ 三、数据库

> ⭐⭐⭐⭐⭐ **性能关键** | 80% 的性能问题出在数据库

<details>
<summary><strong>🐬 MySQL</strong> 🔵 规划中</summary>

**📚 学习内容**

- **SQL 基础** - DDL、DML、DCL、查询语法
- **索引优化** - B+树、聚簇索引、覆盖索引、最左前缀
- **执行计划** - EXPLAIN 详解、优化器、查询优化
- **事务机制** - ACID、隔离级别、MVCC、undo/redo log
- **锁机制** - 表锁、行锁、间隙锁、死锁处理
- **存储引擎** - InnoDB vs MyISAM
- **高可用** - 主从复制、读写分离、MGR、MHA
- **分库分表** - ShardingSphere
- **性能调优** - 慢查询分析、参数调优

</details>

<details>
<summary><strong>📝 Redis</strong> 🔵 规划中</summary>

**📚 学习内容**

- **数据类型** - String、Hash、List、Set、ZSet、Stream
- **底层实现** - SDS、跳表、压缩列表、字典
- **持久化** - RDB、AOF、混合持久化
- **高可用** - 主从复制、哨兵模式、集群模式
- **缓存设计** - 缓存穿透、击穿、雪崩、双写一致性
- **分布式锁** - SETNX、Redisson、RedLock
- **性能优化** - Pipeline、Lua 脚本

</details>

<details>
<summary><strong>🐘 PostgreSQL</strong> 🔵 规划中</summary>

**📚 学习内容**

- 基础使用、高级特性（JSON、全文检索、GIS）
- 性能优化、高可用

</details>

<details>
<summary><strong>🍃 MongoDB</strong> 🔵 规划中</summary>

**📚 学习内容**

- 文档模型、索引、复制集、分片、性能优化

</details>

<details>
<summary><strong>🔍 Elasticsearch</strong> 🔵 规划中</summary>

**📚 学习内容**

- 基础概念、全文检索、聚合分析、集群管理

</details>

---

## 🎓 四、计算机基础

> ⭐⭐⭐⭐ **基础核心** | 打好基础才能走得更远

<details>
<summary><strong>💻 计算机组成原理</strong> 🔵 规划中</summary>

**📚 学习内容**

- 数字逻辑、冯·诺依曼结构、数据运算
- 存储系统、指令系统、CPU 原理、总线与 I/O

</details>

<details>
<summary><strong>🖥️ 操作系统</strong> 🔵 规划中</summary>

**📚 学习内容**

- 进程与线程、处理器调度、同步与互斥
- 内存管理、文件系统、I/O 管理、Linux 编程

</details>

<details>
<summary><strong>🌐 计算机网络</strong> 🔵 规划中</summary>

**📚 学习内容**

- OSI 七层模型、TCP/IP 协议栈
- TCP（三次握手、四次挥手、拥塞控制）
- HTTP/HTTPS、DNS、网络安全、Socket 编程

</details>

---

## 🔄 五、中间件

> ⭐⭐⭐⭐ **分布式基础** | 分布式系统的基础设施

<details>
<summary><strong>📬 消息队列</strong> 🔵 规划中</summary>

**📚 学习内容**

- **Kafka** - 消息模型、高可用、性能优化
- **RabbitMQ** - AMQP 协议、Exchange、Queue
- **RocketMQ** - 事务消息、延时消息

</details>

<details>
<summary><strong>🔧 服务治理</strong> 🔵 规划中</summary>

**📚 学习内容**

- Dubbo、Zookeeper、Nacos、Consul

</details>

<details>
<summary><strong>🌊 API 网关</strong> 🔵 规划中</summary>

**📚 学习内容**

- Spring Cloud Gateway、Kong、Nginx

</details>

<details>
<summary><strong>📊 监控与日志</strong> 🔵 规划中</summary>

**📚 学习内容**

- Prometheus + Grafana、ELK Stack、SkyWalking

</details>

---

## 🏗️ 六、系统架构设计

> ⭐⭐⭐⭐⭐ **架构能力** | 从工程师到架构师的分水岭

<details>
<summary><strong>🎨 设计模式</strong> 🔵 规划中</summary>

**📚 学习内容**

- 创建型、结构型、行为型模式
- Spring 中的设计模式源码分析

</details>

<details>
<summary><strong>🏛️ 架构模式</strong> 🔵 规划中</summary>

**📚 学习内容**

- 分层架构、微服务架构、SOA、事件驱动、Serverless

</details>

<details>
<summary><strong>🌐 分布式系统</strong> 🔵 规划中</summary>

**📚 学习内容**

- CAP/BASE 理论、Paxos/Raft
- 分布式事务、分布式锁、分布式 ID
- 分布式缓存、分布式存储

</details>

<details>
<summary><strong>📈 性能优化</strong> 🔵 规划中</summary>

**📚 学习内容**

- JVM 调优、数据库调优、应用调优
- 高并发方案（限流、降级、熔断）

</details>

<details>
<summary><strong>💡 系统设计案例</strong> 🔵 规划中</summary>

**📚 经典案例**

- 秒杀系统、短链系统、订单系统
- 支付系统、IM 系统

</details>

---

## 🔐 七、安全

> ⭐⭐⭐ **企业必备** | 企业级应用的必备技能

<details open>
<summary><strong>🔒 安全基础</strong> 🟡 进行中 | <a href="auth/01-认证授权基础.md">📖 完整教程</a></summary>

**📚 学习内容**

- **认证与授权** ✅ - Session、Cookie、Token、OAuth2、SAML
- **Spring Security** ✅ - 过滤器链、认证流程、授权机制
- **JWT** ✅ - 结构、使用场景、安全性
- **RBAC/ABAC** ✅ - 权限模型设计
- **SSO 单点登录** ✅ - CAS、OAuth2 实现
- **Web 安全** - XSS、CSRF、SQL 注入、HTTPS

</details>

<details>
<summary><strong>🛡️ 安全加固</strong> 🔵 规划中</summary>

**📚 学习内容**

- 渗透测试、安全审计、防护措施

</details>

---

## ☁️ 八、云原生技术

> ⭐⭐⭐ **云时代** | 云原生技术栈

<details>
<summary><strong>🐳 容器技术</strong> 🟡 进行中</summary>

**📚 学习内容**

- Docker 镜像、容器、Dockerfile、网络、存储
- 镜像优化、Docker Compose、容器原理

</details>

<details>
<summary><strong>⎈ Kubernetes</strong> 🟡 进行中</summary>

**📚 学习内容**

- Pod、Service、Deployment、ConfigMap
- 网络、存储、调度、高可用

</details>

<details>
<summary><strong>🚀 云原生工具</strong> 🟡 进行中</summary>

**📚 学习内容**

- **Nomad** ⚡ - 轻量级调度（当前项目）
- **Tailscale** ⚡ - 零配置 VPN（当前项目）
- Istio、Prometheus

</details>

---

## 🛠️ 九、DevOps 工具链

> ⭐⭐⭐ **效率提升** | 现代化开发必备

<details>
<summary><strong>🔧 版本控制</strong> 🟡 进行中</summary>

**📚 学习内容**

- Git、GitFlow、GitHub/GitLab

</details>

<details>
<summary><strong>🚀 CI/CD</strong> 🟡 进行中</summary>

**📚 学习内容**

- GitHub Actions、Jenkins、GitLab CI

</details>

<details>
<summary><strong>📦 构建工具</strong> 🟡 进行中</summary>

**📚 学习内容**

- Maven、Gradle、打包部署

</details>

<details open>
<summary><strong>📝 文档与博客</strong> 🟢 已完成 | <a href="devops/01-博客搭建指南.md">📖 搭建指南</a></summary>

**📚 学习内容**

- **MkDocs** ✅ - 技术文档、GitHub Pages
- Obsidian、Markdown、技术博客写作

</details>

---

## 🎨 十、软件工程

> ⭐⭐ **工程质量** | 代码质量和工程化

<details>
<summary><strong>📐 代码质量</strong> 🔵 规划中</summary>

**📚 学习内容**

- 代码规范、Clean Code、重构、Code Review

</details>

<details>
<summary><strong>🧪 测试</strong> 🔵 规划中</summary>

**📚 学习内容**

- 单元测试、集成测试、性能测试、TDD/BDD

</details>

---

## 🤖 十一、人工智能

> ⭐⭐ **AI 时代** | 新技术机遇

<details>
<summary><strong>🧠 机器学习</strong> 🔵 规划中</summary>

**📚 学习内容**

- 监督学习、非监督学习、深度学习
- NumPy、Pandas、PyTorch

</details>

<details>
<summary><strong>💬 大语言模型</strong> 🔵 规划中</summary>

**📚 学习内容**

- Prompt Engineering、LangChain、RAG

</details>

---

## 🐍 十二、其他编程语言

> ⭐ **拓展视野** | 多语言思维

<details>
<summary><strong>🦀 Rust / 🐹 Go / 🐍 Python</strong> 🔵 规划中</summary>

**📚 学习内容**

- Rust：所有权、并发、Web开发
- Go：Goroutine、标准库、微服务
- Python：基础语法、数据分析、Web开发

</details>

---

## 📝 十三、日常记录

<details>
<summary><strong>📔 技术博客 / 💭 思考总结 / 🐛 问题记录</strong> 🟡 进行中</summary>

**📚 内容分类**

- 技术深度文章、实战总结、学习笔记
- 周/月/年度总结、技术反思
- Bug 排查、性能问题、踩坑记录

</details>

---

## 💼 十四、职业发展

> ⭐⭐⭐ **职业规划** | 技术+软技能

<details>
<summary><strong>🎯 职业规划 / 📚 面试准备 / 💪 软技能</strong> 🟡 进行中</summary>

**📚 学习内容**

- 技术路线、能力模型、短期/长期目标
- 技术面试、项目经验、简历优化
- 沟通能力、项目管理、团队协作

</details>

---

## 📊 学习统计

<div align="center">

| 📈 维度 | 📊 数据 |
|:---:|:---|
| **知识模块** | 14 大类 70+ 小类 |
| **已完成** | Java语言基础、认证授权、博客搭建 |
| **进行中** | Spring、容器技术、云原生工具 |
| **总文档** | 15+ 篇，持续增加中 |

</div>

---

## 📞 关于作者

<table>
<tr>
<td width="50%">

### 👨‍💻 基本信息

- 💼 7年 Java 开发工程师
- 🎯 云原生 · 微服务 · 系统架构
- 📈 从外包到技术专家的成长之路
- 💪 相信系统化积累的力量

### 🔗 联系方式

- 📧 GitHub: [@zhangzimingmmz](https://github.com/zhangzimingmmz)
- 🌐 博客: [OpenTheDoor](https://zhangzimingmmz.github.io/OpenTheDoor/)

</td>
<td width="50%">

### 🎯 当前状态

**擅长领域**
- ☕ Java 生态系统
- 🐳 Docker & Kubernetes
- 🚀 微服务架构

**进行中的项目**
- 🖥️ AI 算力共享平台
- 📦 Nomad + Tailscale 实践

**学习重点**
- 📚 补齐编程基础
- 🏗️ 建立架构思维
- 💡 提升系统设计能力

</td>
</tr>
</table>

---

<div align="center">

## 💪 一起成长

**"学习不是为了应付面试，而是为了成为更好的工程师"**

⭐ 如果这个知识库对你有帮助，欢迎 Star 支持  

🔄 持续更新中... | 最后更新：2025年10月

---

**[📖 开始学习](java-core/README.md) · [🌐 在线阅读](https://zhangzimingmmz.github.io/OpenTheDoor/) · [💬 交流反馈](https://github.com/zhangzimingmmz/OpenTheDoor/issues)**

</div>
