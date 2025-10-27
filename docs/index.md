# 🚀 OpenTheDoor - 终身学习知识库

**📖 原理优先 · 📝 实战驱动 · 💡 系统积累 · 🎯 终身成长**

*从基础到架构的系统化学习 | 计算机科学 + Java 生态 + 工程实践*

---

## 📖 知识体系

> 💡 **设计理念**：按照**面试重要程度**和**技术深度**组织，既适合系统学习，也方便面试突击

### 📚 已完成的学习资源

- 📄 [认证授权完整教程](auth/01-认证授权基础.md) - Spring Security、OAuth2、JWT、SSO
- 📄 [博客搭建指南](devops/01-博客搭建指南.md) - MkDocs + GitHub Pages

---

## ☕ 一、Java 核心技术

> **面试权重**: ⭐⭐⭐⭐⭐ | **核心竞争力**：决定你的技术深度和代码质量

### 📘 Java 语言基础

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **语法基础** - 数据类型、运算符、控制流、异常处理
- **面向对象** - 类与对象、继承、多态、封装、接口与抽象类
- **高级特性** - 泛型、注解、反射、动态代理
- **函数式编程** - Lambda、Stream API、Optional
- **新版本特性** - Java 8~21 新特性（Record、Sealed Class、Virtual Thread 等）

</details>

---

### 🗂️ Java 集合框架

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **List 系列** - ArrayList、LinkedList、CopyOnWriteArrayList 源码与应用
- **Set 系列** - HashSet、TreeSet、LinkedHashSet
- **Map 系列** - HashMap、TreeMap、ConcurrentHashMap、WeakHashMap
- **Queue 系列** - PriorityQueue、BlockingQueue、Deque
- **集合工具类** - Collections、Arrays、Iterator、Comparator

**🎯 学习目标**

- 理解各集合底层实现（数组、链表、红黑树、跳表）
- 掌握性能特点和使用场景
- 面试高频考点全覆盖

</details>

---

### 🧵 Java 并发编程

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **线程基础** - Thread、Runnable、Callable、FutureTask
- **线程安全** - synchronized、volatile、final、ThreadLocal
- **显式锁** - Lock、ReentrantLock、ReadWriteLock、StampedLock
- **AQS 框架** - AbstractQueuedSynchronizer 源码分析
- **并发工具** - CountDownLatch、CyclicBarrier、Semaphore、Exchanger
- **线程池** - ThreadPoolExecutor、ScheduledThreadPoolExecutor、ForkJoinPool
- **并发容器** - ConcurrentHashMap、CopyOnWriteArrayList、BlockingQueue
- **原子类** - Atomic*、LongAdder、Unsafe
- **虚拟线程** - Java 21 Virtual Threads（Project Loom）

**🎯 学习目标**

- 深入理解 Java 内存模型（JMM）
- 掌握并发编程最佳实践
- 能排查和解决并发问题

</details>

---

### 🔧 JVM 虚拟机

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **JVM 体系结构** - 类加载子系统、运行时数据区、执行引擎
- **类加载机制** - 加载、链接、初始化、类加载器、双亲委派模型
- **内存结构** - 堆、栈、方法区、程序计数器、本地方法栈
- **垃圾回收** - GC 算法、分代收集、垃圾回收器（Serial、Parallel、CMS、G1、ZGC、Shenandoah）
- **JVM 调优** - 参数配置、内存调优、GC 调优、性能分析
- **字节码与 ASM** - 字节码指令、ASM 字节码操作
- **JIT 编译器** - 即时编译、C1/C2 编译器、逃逸分析、内联优化
- **性能监控** - JConsole、VisualVM、JProfiler、Arthas

**🎯 学习目标**

- 深入理解 JVM 运行机制
- 掌握 JVM 调优技能
- 排查内存泄漏、CPU 飙高等问题

</details>

---

### 🌐 Java 网络编程

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **BIO 编程** - Socket、ServerSocket、TCP/UDP 编程
- **NIO 编程** - Buffer、Channel、Selector、非阻塞 I/O
- **AIO 编程** - AsynchronousChannel、CompletionHandler
- **Netty 框架** - Reactor 模型、EventLoop、Pipeline、编解码器
- **序列化** - Java 序列化、Protobuf、JSON、Kryo、Hessian
- **HTTP 客户端** - HttpURLConnection、HttpClient、OkHttp、Retrofit

**🎯 学习目标**

- 理解 I/O 模型（BIO、NIO、AIO）
- 掌握 Netty 高性能网络编程
- 理解 RPC 框架底层原理

</details>

---

### 🌱 Spring 全家桶

**📊 状态**: 🟡 进行中  
**📄 已完成**: [认证授权专题](auth/01-认证授权基础.md)

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

**Spring Core**
- **IoC & DI** - 依赖注入、Bean 生命周期、容器初始化
- **AOP** - 动态代理、切面编程、事务管理
- **事件机制** - ApplicationEvent、事件监听

**Spring MVC**
- **DispatcherServlet** - 请求处理流程
- **HandlerMapping** - 请求映射
- **视图解析** - ViewResolver、模板引擎

**Spring Boot**
- **自动配置** - @EnableAutoConfiguration、条件装配
- **Starter 机制** - 依赖管理、自定义 Starter
- **Actuator** - 健康检查、指标监控
- **外部化配置** - application.yml、Profile

**Spring Cloud**
- **微服务架构** - 服务拆分、服务治理
- **服务注册与发现** - Eureka、Consul、Nacos
- **负载均衡** - Ribbon、LoadBalancer
- **服务调用** - Feign、OpenFeign
- **API 网关** - Spring Cloud Gateway
- **配置中心** - Config Server

**Spring Security** ✅
- **认证授权** - Session、Cookie、Token、OAuth2、SAML
- **过滤器链** - SecurityFilterChain、认证流程
- **授权机制** - RBAC、ABAC、权限模型
- **JWT** - 令牌生成、验证、刷新
- **SSO 单点登录** - CAS、OAuth2 实现
- 📖 [完整学习路径](auth/01-认证授权基础.md)

**Spring Data**
- **JPA** - ORM、实体映射、JPQL
- **JDBC** - JdbcTemplate
- **Redis** - RedisTemplate、缓存抽象
- **MongoDB** - MongoTemplate

**Spring WebFlux**
- **响应式编程** - Reactor、Mono、Flux
- **函数式端点** - RouterFunction、HandlerFunction

**ORM 框架**
- **MyBatis** - 动态 SQL、缓存机制、插件开发
- **MyBatis-Plus** - CRUD、代码生成、分页插件
- **Hibernate/JPA** - 实体映射、HQL、缓存、延迟加载

**其他框架**
- **Lombok** - 简化 Java 代码
- **MapStruct** - Bean 映射
- **Quartz** - 任务调度
- **Netty** - 高性能网络框架

**🎯 学习目标**

- 深入理解 Spring 源码和设计模式
- 掌握微服务架构开发
- 熟练使用主流开发框架

</details>

---

## 📊 二、数据结构与算法

> **面试权重**: ⭐⭐⭐⭐⭐ | **核心竞争力**：算法是程序的灵魂，面试必考

### 📐 数据结构

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **线性结构** - 数组、链表、栈、队列、双端队列
- **树结构** - 二叉树、BST、AVL、红黑树、B/B+树、字典树（Trie）
- **堆结构** - 二叉堆、斐波那契堆、优先队列
- **图结构** - 图的表示、遍历、最短路径、最小生成树
- **哈希表** - 哈希函数、冲突解决、一致性哈希
- **高级结构** - 并查集、跳表、布隆过滤器、LRU/LFU

</details>

---

### 🧮 算法

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **排序算法** - 冒泡、选择、插入、快排、归并、堆排、桶排、计数排序
- **查找算法** - 二分查找、哈希查找、树查找
- **贪心算法** - 活动选择、背包问题
- **分治算法** - 快排、归并、二分
- **动态规划** - 背包问题、最长子序列、编辑距离
- **回溯算法** - N 皇后、数独、全排列
- **图算法** - DFS、BFS、Dijkstra、Floyd、Prim、Kruskal
- **字符串算法** - KMP、Rabin-Karp、AC 自动机

</details>

---

### 💻 LeetCode 刷题

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 刷题计划**

- **基础 100 题** - 高频简单题
- **进阶 200 题** - 中等难度题
- **高级 100 题** - 困难题
- **剑指 Offer** - 面试经典题
- **Hot 100** - LeetCode 热题
- **周赛/双周赛** - 保持手感

</details>

---

## 🗄️ 三、数据库

> **面试权重**: ⭐⭐⭐⭐⭐ | **核心竞争力**：80% 的性能问题出在数据库

### 🐬 MySQL

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **SQL 基础** - DDL、DML、DCL、查询语法
- **索引优化** - B+树、聚簇/非聚簇索引、覆盖索引、最左前缀
- **执行计划** - EXPLAIN 详解、优化器、查询优化
- **事务机制** - ACID、隔离级别、MVCC、undo/redo log
- **锁机制** - 表锁、行锁、间隙锁、死锁检测与处理
- **存储引擎** - InnoDB vs MyISAM、列式存储
- **高可用** - 主从复制、读写分离、MGR、MHA
- **分库分表** - 水平/垂直拆分、ShardingSphere
- **性能调优** - 慢查询分析、参数调优、硬件优化

**🎯 学习目标**

- 深入理解 MySQL 内部机制
- 掌握 SQL 优化技能
- 设计高性能数据库方案

</details>

---

### 📝 Redis

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **数据类型** - String、Hash、List、Set、ZSet、Bitmap、HyperLogLog、Geo、Stream
- **底层实现** - SDS、跳表、压缩列表、字典、intset
- **持久化** - RDB、AOF、混合持久化
- **高可用** - 主从复制、哨兵模式、集群模式
- **缓存设计** - 缓存穿透、击穿、雪崩、双写一致性、热点数据
- **分布式锁** - SETNX、Redisson、RedLock
- **性能优化** - Pipeline、Lua 脚本、慢查询分析

**🎯 学习目标**

- 深入理解 Redis 底层数据结构
- 掌握缓存设计模式
- 解决高并发缓存问题

</details>

---

### 🐘 PostgreSQL

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础使用** - 安装配置、基本操作
- **高级特性** - JSON、全文检索、GIS、窗口函数
- **性能优化** - 索引、执行计划、参数调优
- **高可用** - 流复制、逻辑复制、Patroni

</details>

---

### 🍃 MongoDB

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **文档模型** - BSON、CRUD 操作、聚合管道
- **索引** - 单字段、复合、多键、文本、地理空间索引
- **复制集** - 主从同步、故障转移
- **分片** - 水平扩展、分片键选择
- **性能优化** - 查询优化、索引优化

</details>

---

### 🔍 Elasticsearch

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础概念** - Index、Document、Mapping、分词器
- **检索** - 全文检索、聚合分析、高亮显示
- **集群** - 分片、副本、路由、集群管理
- **性能优化** - 索引优化、查询优化、硬件优化

</details>

---

## 🎓 四、计算机基础

> **面试权重**: ⭐⭐⭐⭐ | **核心竞争力**：打好基础才能走得更远

### 💻 计算机组成原理

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **数字逻辑** - 逻辑门、触发器、组合/时序电路
- **系统概述** - 冯·诺依曼结构、哈佛结构
- **数据运算** - 定点数、浮点数、补码运算、ALU
- **存储系统** - Cache、主存、虚拟存储、存储层次
- **指令系统** - CISC vs RISC、指令格式、寻址方式
- **CPU 原理** - 数据通路、流水线、超标量
- **总线与 I/O** - 系统总线、I/O 控制、中断系统

**🎯 学习目标**

- 理解计算机硬件工作原理
- 为操作系统和编译原理打好基础
- 理解性能优化的硬件层面原因

</details>

---

### 🖥️ 操作系统

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **系统概述** - OS 的作用、类型、发展史
- **进程与线程** - 进程控制、线程模型、进程间通信（IPC）
- **处理器调度** - 调度算法、多级队列、实时调度
- **同步与互斥** - 信号量、管程、死锁检测与预防
- **内存管理** - 分段、分页、虚拟内存、页面置换算法
- **文件系统** - 文件组织、目录结构、磁盘管理
- **I/O 管理** - I/O 软件层次、设备驱动、缓冲技术
- **Linux 编程** - 系统调用、Shell 编程、进程管理

**🎯 学习目标**

- 深刻理解 OS 核心机制
- 为理解 JVM、并发编程打好基础
- 掌握 Linux 系统使用和调优

</details>

---

### 🌐 计算机网络

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **网络体系** - OSI 七层模型、TCP/IP 四层模型
- **物理层** - 传输介质、编码与调制、信道复用
- **数据链路层** - 差错检测、流量控制、MAC 协议、以太网
- **网络层** - IP 协议、路由算法、ICMP、NAT、IPv6
- **传输层** - TCP（三次握手、四次挥手、拥塞控制）、UDP
- **应用层** - HTTP/HTTPS、DNS、SMTP、FTP、WebSocket
- **网络安全** - 加密算法、数字签名、SSL/TLS、防火墙
- **网络编程** - Socket 编程、I/O 多路复用

**🎯 学习目标**

- 深入理解网络协议栈
- 掌握网络编程和调优
- 理解分布式系统的网络基础

</details>

---

## 🔄 五、中间件

> **面试权重**: ⭐⭐⭐⭐ | **核心竞争力**：分布式系统的基础设施

### 📬 消息队列

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

**Kafka**
- 消息模型、Topic、Partition、Consumer Group
- 高可用、副本机制、ISR
- 性能优化、消息堆积处理

**RabbitMQ**
- AMQP 协议、Exchange、Queue、Binding
- 消息确认机制、持久化
- 集群、镜像队列

**RocketMQ**
- 消息模型、事务消息、延时消息
- 顺序消息、广播消费

**🎯 学习目标**

- 理解消息队列原理和应用场景
- 掌握消息可靠性保障
- 解决消息积压、重复消费等问题

</details>

---

### 🔧 服务治理

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Dubbo** - RPC 框架、服务注册与发现、负载均衡
- **Zookeeper** - 分布式协调、配置中心、分布式锁
- **Nacos** - 服务注册、配置管理、动态 DNS
- **Consul** - 服务网格、健康检查

</details>

---

### 🌊 API 网关

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Spring Cloud Gateway** - 路由、过滤器、限流
- **Kong** - API 管理、插件机制
- **Nginx** - 反向代理、负载均衡、限流

</details>

---

### 📊 监控与日志

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Prometheus + Grafana** - 监控告警、可视化
- **ELK Stack** - 日志收集、分析、可视化
- **SkyWalking** - 分布式追踪、APM
- **Zipkin** - 链路追踪

</details>

---

## 🏗️ 六、系统架构设计

> **面试权重**: ⭐⭐⭐⭐⭐ | **核心竞争力**：从工程师到架构师的分水岭

### 🎨 设计模式

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **创建型模式** - 单例、工厂、建造者、原型
- **结构型模式** - 适配器、装饰器、代理、外观、桥接、组合、享元
- **行为型模式** - 策略、模板方法、观察者、责任链、命令、状态等
- **Spring 设计模式** - 源码分析

</details>

---

### 🏛️ 架构模式

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **分层架构** - MVC、三层架构、DDD 领域驱动设计
- **微服务架构** - 服务拆分、服务治理、服务网格
- **SOA 架构** - 面向服务、ESB
- **事件驱动架构** - EDA、CQRS、Event Sourcing
- **Serverless 架构** - FaaS、BaaS

</details>

---

### 🌐 分布式系统

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **分布式理论** - CAP、BASE、Paxos、Raft、2PC、3PC、TCC
- **分布式事务** - 本地事务、两阶段提交、补偿事务（Saga）
- **分布式锁** - 数据库锁、Redis 锁、Zookeeper 锁
- **分布式 ID** - 雪花算法、美团 Leaf、百度 UidGenerator
- **分布式缓存** - Redis 集群、一致性哈希
- **分布式存储** - HDFS、Ceph、MinIO

**🎯 学习目标**

- 理解分布式系统理论基础
- 掌握分布式场景解决方案
- 设计高可用、高并发系统

</details>

---

### 📈 性能优化

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **JVM 调优** - 内存调优、GC 调优、线程调优
- **数据库调优** - SQL 优化、索引优化、架构优化
- **应用调优** - 代码优化、缓存优化、异步化
- **系统调优** - 容量规划、压测、性能瓶颈分析
- **高并发方案** - 限流、降级、熔断、缓存

</details>

---

### 💡 系统设计案例

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 经典案例**

- **秒杀系统** - 高并发、库存扣减、防超卖
- **短链系统** - 唯一 ID 生成、跳转优化
- **订单系统** - 状态机、分布式事务
- **支付系统** - 幂等性、对账、异步通知
- **IM 系统** - 长连接、消息推送、离线消息

</details>

---

## 🔐 七、安全

> **面试权重**: ⭐⭐⭐ | **核心竞争力**：企业级应用的必备技能

### 🔒 安全基础

**📊 状态**: 🟡 进行中  
**📄 已完成**: [认证授权完整教程](auth/01-认证授权基础.md)

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **认证与授权** - Session、Cookie、Token、OAuth2、SAML ✅
- **加密算法** - 对称加密、非对称加密、哈希算法、数字签名
- **Spring Security** - 过滤器链、认证流程、授权机制 ✅
- **JWT** - 结构、使用场景、安全性 ✅
- **RBAC/ABAC** - 权限模型设计 ✅
- **SSO 单点登录** - CAS、OAuth2 实现 ✅
- **Web 安全** - XSS、CSRF、SQL 注入、HTTPS

</details>

---

### 🛡️ 安全加固

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **渗透测试** - OWASP Top 10、常见攻击手段
- **安全审计** - 代码审计、漏洞扫描
- **防护措施** - WAF、防火墙、入侵检测

</details>

---

## ☁️ 八、云原生技术

> **面试权重**: ⭐⭐⭐ | **核心竞争力**：云时代的基础设施

### 🐳 容器技术

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Docker** - 镜像、容器、Dockerfile、网络、存储
- **镜像优化** - 多阶段构建、层缓存、精简镜像
- **Docker Compose** - 容器编排、服务定义
- **容器原理** - Namespace、Cgroups、Union FS

</details>

---

### ⎈ Kubernetes

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **核心概念** - Pod、Service、Deployment、StatefulSet、DaemonSet
- **配置管理** - ConfigMap、Secret、PV/PVC
- **网络** - Service 网络、Pod 网络、Ingress
- **存储** - Volume、PV、StorageClass
- **调度** - Scheduler、Affinity、Taint/Toleration
- **高可用** - 多节点集群、故障恢复

</details>

---

### 🚀 云原生工具

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Nomad** - 轻量级调度、任务编排（当前项目）⚡
- **Tailscale** - 零配置 VPN、服务网格 ⚡
- **Istio** - 服务网格、流量管理、安全
- **Prometheus** - 监控、告警

</details>

---

## 🛠️ 九、DevOps 工具链

> **面试权重**: ⭐⭐⭐ | **核心竞争力**：提升开发效率的必备技能

### 🔧 版本控制

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Git** - 基础操作、分支策略、Rebase、Cherry-pick
- **GitFlow** - 分支模型、发布流程
- **GitHub/GitLab** - Pull Request、Code Review、CI/CD

</details>

---

### 🚀 CI/CD

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **GitHub Actions** - Workflow、自动化部署
- **Jenkins** - Pipeline、插件开发
- **GitLab CI** - .gitlab-ci.yml、Runner
- **自动化测试** - 单元测试、集成测试、E2E 测试

</details>

---

### 📦 构建工具

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Maven** - POM、依赖管理、插件开发、多模块项目
- **Gradle** - Groovy DSL、构建脚本、插件
- **打包部署** - 镜像构建、发布流程

</details>

---

### 📝 文档与博客

**📊 状态**: 🟢 已完成  
**📄 已完成**: [MkDocs 博客搭建指南](devops/01-博客搭建指南.md)

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **MkDocs** - 技术文档、GitHub Pages ✅
- **Obsidian** - 知识管理、双向链接
- **Markdown** - 语法、扩展
- **技术博客** - 写作技巧、SEO

</details>

---

## 🎨 十、软件工程

> **面试权重**: ⭐⭐ | **核心竞争力**：工程化思维和代码质量

### 📐 代码质量

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **代码规范** - 阿里巴巴 Java 开发手册、Google Java Style
- **Clean Code** - 命名、函数、注释、格式
- **重构** - 代码坏味道、重构手法
- **代码审查** - Code Review 最佳实践

</details>

---

### 🧪 测试

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **单元测试** - JUnit、Mockito、PowerMock
- **集成测试** - Spring Test、Testcontainers
- **性能测试** - JMeter、Gatling
- **测试覆盖率** - JaCoCo、Cobertura

</details>

---

## 🤖 十一、人工智能

> **面试权重**: ⭐⭐ | **核心竞争力**：AI 时代的新机遇

### 🧠 机器学习基础

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础概念** - 监督学习、非监督学习、强化学习
- **经典算法** - 线性回归、逻辑回归、决策树、SVM
- **深度学习** - 神经网络、CNN、RNN、Transformer
- **工具** - NumPy、Pandas、Scikit-learn、PyTorch

</details>

---

### 💬 大语言模型

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **Prompt Engineering** - 提示词工程、Few-shot Learning
- **LangChain** - LLM 应用开发框架
- **向量数据库** - Milvus、Pinecone、Chroma
- **RAG** - 检索增强生成

</details>

---

## 🐍 十二、其他编程语言

> **面试权重**: ⭐ | **核心竞争力**：多语言视野，拓展思维边界

### 🦀 Rust

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础语法** - 所有权、借用、生命周期
- **并发编程** - 线程、Channel、Async/Await
- **Web 开发** - Actix-web、Rocket

</details>

---

### 🐹 Go

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础语法** - Goroutine、Channel、defer
- **标准库** - net/http、database/sql
- **Web 框架** - Gin、Echo、Beego

</details>

---

### 🐍 Python

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 学习内容（点击展开）</strong></summary>

**📚 子模块**

- **基础语法** - 数据类型、控制流、函数、类
- **数据分析** - NumPy、Pandas、Matplotlib
- **Web 开发** - Django、Flask、FastAPI

</details>

---

## 📝 十三、日常记录

> **核心竞争力**：持续积累，厚积薄发

### 📔 技术博客

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 博客类型**

- **技术深度文章** - 源码分析、原理解析
- **实战总结** - 项目经验、问题排查
- **学习笔记** - 读书笔记、视频笔记
- **翻译文章** - 优秀技术文章翻译

</details>

---

### 💭 思考与总结

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 总结类型**

- **周/月总结** - 学习进度、工作总结
- **年度总结** - 成长回顾、目标规划
- **技术反思** - 架构决策、技术选型
- **读书笔记** - 技术书籍、非技术书籍

</details>

---

### 🐛 问题记录

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 问题类型**

- **Bug 排查** - 问题现象、排查过程、解决方案
- **性能问题** - 性能瓶颈、优化方案
- **踩坑记录** - 遇到的坑、避坑指南
- **最佳实践** - 工作中总结的经验

</details>

---

## 💼 十四、职业发展

> **面试权重**: ⭐⭐⭐ | **核心竞争力**：技术是手段，目标是成为更好的自己

### 🎯 职业规划

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 规划方向**

- **技术路线** - Java 后端 → 全栈 → 架构师
- **能力模型** - 技术能力、业务能力、管理能力
- **短期目标** - 1-3 年目标
- **长期目标** - 5-10 年规划

</details>

---

### 📚 面试准备

**📊 状态**: 🔵 规划中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 准备内容**

- **技术面试** - 八股文、算法题、系统设计
- **项目经验** - STAR 法则、亮点提炼
- **简历优化** - 简历模板、内容优化
- **面经总结** - 面试题目、回答思路

</details>

---

### 💪 软技能

**📊 状态**: 🟡 进行中

<details>
<summary><strong>📖 内容分类（点击展开）</strong></summary>

**📚 技能分类**

- **沟通能力** - 技术表达、文档编写
- **项目管理** - 需求管理、进度把控
- **团队协作** - Code Review、知识分享
- **时间管理** - GTD、番茄工作法
- **学习方法** - 费曼学习法、刻意练习

</details>

---

## 📊 学习统计

| 📈 指标 | 📊 数据 |
|:---:|:---:|
| **知识模块** | 14 大类 70+ 小类 |
| **已完成** | 认证授权、博客搭建 |
| **进行中** | Spring、容器技术、云原生工具 |

---

## 📞 关于作者

### 👨‍💻 基本信息

- 💼 **职位**：7年 Java 开发工程师
- 🎯 **方向**：云原生 · 微服务 · 系统架构
- 📈 **目标**：从外包程序员到技术专家
- 💪 **信念**：相信系统化积累的力量

### 🎯 技术栈

**擅长领域**
- ☕ Java 生态
- 🐳 Docker & 云原生
- 🚀 微服务架构

**当前项目**
- 🖥️ AI 算力共享平台
- 📦 Nomad + Tailscale

**学习重点**
- 📚 补齐编程基础
- 🏗️ 建立架构思维

### 🔗 联系方式

- 📧 GitHub: [@zhangzimingmmz](https://github.com/zhangzimingmmz)
- 🌐 博客: [OpenTheDoor](https://zhangzimingmmz.github.io/OpenTheDoor/)

---

## 💪 一起成长

**"学习不是为了应付面试，而是为了成为更好的工程师"**

⭐ 如果这个知识库对你有帮助，欢迎 Star 支持  

🔄 持续更新中... | 最后更新：2025年10月27日

---

**[开始学习](auth/01-认证授权基础.md) · [在线阅读](https://zhangzimingmmz.github.io/OpenTheDoor/) · [交流反馈](https://github.com/zhangzimingmmz/OpenTheDoor/issues)**
