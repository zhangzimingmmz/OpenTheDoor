# Java高级工程师 - 学习计划（从CRUD到深入原理）

> **现状**：会用框架做增删改查，对底层原理了解不深  
> **目标**：掌握核心技术原理，面试时能够自信圆场，拿到25-35K offer  
> **时间规划**：9-12个月系统学习（明年秋招）  
> **学习时间**：工作日每天2小时 + 周末每天4-6小时

---

## 📌 学习策略

### 核心原则
1. **扎实基础**：基础不牢，地动山摇，不急于求成
2. **慢慢消化**：每个知识点都要充分理解和实践，不求快只求扎实
3. **边学边练**：理论+实践+面试题，三位一体
4. **长期主义**：有将近一年时间，可以从容学习，打好基础

### ⏰ 每周学习时间安排

**工作日（周一至周五）**：
- 晚上8:00-10:00（2小时/天）
- 每周10小时

**周末（周六周日）**：
- 上午9:00-12:00（3小时）
- 下午2:00-5:00（3小时）
- 每周12小时

**合计**：每周约22小时，每月约90小时，非常充裕！

### 学习路线图（9-12个月）

```
阶段一：基础扎实（月1-3）
├── 第1个月：Java集合 + JVM
├── 第2个月：MySQL + Redis
└── 第3个月：多线程 + 并发编程

阶段二：框架原理（月4-6）
├── 第4个月：Spring IoC/AOP + MyBatis
├── 第5个月：Spring Boot + Spring Cloud
└── 第6个月：分布式技术（锁、事务、缓存）

阶段三：深度提升（月7-9）
├── 第7个月：Kafka + RocketMQ
├── 第8个月：Skywalking + 性能优化
└── 第9个月：系统设计 + 架构能力

阶段四：冲刺准备（月10-12）
├── 第10个月：面试题整理 + 查漏补缺
├── 第11个月：模拟面试 + 项目复盘
└── 第12个月：简历优化 + 秋招投递
```

---

## 🎯 阶段一：基础扎实（月1-3，共3个月）

> **目标**：扎实掌握Java基础、JVM、MySQL、Redis、多线程  
> **心态**：不急于求成，每个知识点都要充分理解

---

### 第1个月：Java集合 + JVM（30天）

#### Week 1-2：Java集合框架深入理解

**必须掌握**：
- [ ] HashMap底层实现（数组+链表+红黑树，1.8优化）
- [ ] ConcurrentHashMap原理（分段锁 → CAS+synchronized）
- [ ] ArrayList vs LinkedList使用场景
- [ ] HashSet、TreeSet、LinkedHashSet区别

**学习资源**：
- 📖 书籍：《Java核心技术 卷I》第9章 - 集合
- 🎥 视频：B站"尚硅谷HashMap源码"（完整版）
- 📝 文章：[Java集合框架源码分析](https://www.pdai.tech/md/java/collection/java-collection-all.html)

**学习计划**（每天2小时）：
- **Day 1-3**：理解HashMap基本原理（put、get、扩容）
- **Day 4-6**：深入红黑树转换、hash冲突解决
- **Day 7-9**：ConcurrentHashMap原理（1.7 vs 1.8）
- **Day 10-12**：ArrayList、LinkedList源码阅读
- **Day 13-14**：总结复习，整理笔记

**实践任务**：
```java
// 1. 手写简易版HashMap（数组+链表）
class SimpleHashMap<K, V> {
    // TODO: 实现put、get、扩容方法
}

// 2. Debug源码，观察HashMap扩容过程
// 3. 写一个高并发场景，对比HashMap vs ConcurrentHashMap
// 4. 用JMH工具测试ArrayList vs LinkedList性能差异
```

**面试话术模板**：
> "HashMap在JDK 1.8做了优化，当链表长度>8且数组长度>64时，会转换为红黑树，
> 查询时间复杂度从O(n)降至O(log n)。扩容时采用尾插法避免死循环问题。
> 
> ConcurrentHashMap在1.8放弃了分段锁，改用CAS+synchronized，锁粒度更细。
> put操作时，如果桶为空用CAS，否则用synchronized锁住链表头节点。
> 
> 我在项目中用HashMap做本地缓存，高并发场景用ConcurrentHashMap保证线程安全。"

---

#### Week 3-4：JVM内存模型 + 垃圾回收

**必须掌握**：
- [ ] JVM内存模型（堆、栈、方法区、程序计数器）
- [ ] 垃圾回收算法（标记-清除、标记-整理、复制）
- [ ] 垃圾回收器（Serial、Parallel、CMS、G1、ZGC）
- [ ] 如何定位Full GC频繁问题？
- [ ] 如何分析内存泄漏？

**学习资源**：
- 📖 书籍：《深入理解Java虚拟机》第3版 - 第2-3章（必读！）
- 🎥 视频：B站"尚硅谷JVM完整版"（前30集）
- 🔧 工具：jstat、jmap、jvisualvm、MAT

**学习计划**（每天2小时）：
- **Day 15-17**：JVM内存模型、类加载机制
- **Day 18-20**：垃圾回收算法、分代回收
- **Day 21-23**：垃圾回收器（Serial → G1 → ZGC演进）
- **Day 24-26**：JVM调优工具、参数调优
- **Day 27-28**：实战：排查Full GC问题

**实践任务**：
```bash
# 1. 启动你的项目，查看GC情况
jstat -gcutil <pid> 1000 10

# 2. 模拟内存泄漏，用jmap生成堆转储
jmap -dump:format=b,file=heap.hprof <pid>

# 3. 用MAT分析堆转储文件，找出内存泄漏原因

# 4. 调整JVM参数，观察GC变化
java -Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar
```

**面试话术模板**：
> "在贷款流程系统中，我遇到Full GC频繁的问题。排查步骤：
> 1. jstat -gcutil查看GC频率，发现老年代增长快
> 2. jmap -dump生成堆转储文件
> 3. MAT分析，发现某个List对象持有大量数据未释放
> 4. 代码优化：改为分页查询，及时释放对象引用
> 5. JVM参数调优：使用G1垃圾回收器，-Xmx8G -Xms8G
> 
> G1垃圾回收器优势是可以设置期望停顿时间（-XX:MaxGCPauseMillis），
> 并且可以并发标记和压缩，减少STW时间。"

**第1个月总结**：
- [ ] 整理Java集合框架思维导图
- [ ] 整理JVM知识点笔记
- [ ] 做一套Java基础面试题（50题）
- [ ] 回顾本月学习，查漏补缺

---

### 第2个月：MySQL + Redis（30天）

#### Week 1-2：MySQL索引优化 + SQL调优

**必须掌握**：
- [ ] B+Tree索引原理（为什么不用B树？）
- [ ] 联合索引最左匹配原则
- [ ] 索引失效场景（函数、类型转换、or、不等于、like左模糊）
- [ ] 覆盖索引 vs 回表查询
- [ ] EXPLAIN执行计划分析
- [ ] 慢查询优化

**学习资源**：
- 📖 书籍：《高性能MySQL》第3版 - 第5-6章
- 🎥 视频：B站"尚硅谷MySQL高级"（索引部分）
- 📝 文章：[MySQL索引优化实战](https://www.pdai.tech/md/db/sql-mysql/sql-mysql-index.html)

**学习计划**（每天2小时）：
- **Day 1-3**：B+Tree索引原理、索引类型
- **Day 4-6**：联合索引、索引失效场景
- **Day 7-9**：EXPLAIN分析、慢查询优化
- **Day 10-12**：分页优化、JOIN优化
- **Day 13-14**：实战：优化一个慢查询

**实践任务**：
```sql
-- 1. 创建测试表，插入100万数据
CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  order_no VARCHAR(64),
  amount DECIMAL(10,2),
  status TINYINT,
  create_time DATETIME
);

-- 2. 分析慢查询
EXPLAIN SELECT * FROM orders WHERE user_id = 123 AND create_time > '2024-01-01';

-- 3. 创建联合索引，对比性能
CREATE INDEX idx_user_time ON orders(user_id, create_time);

-- 4. 测试索引失效场景
-- 函数：WHERE DATE(create_time) = '2024-01-01'（失效）
-- 类型转换：WHERE order_no = 123（失效）
-- 不等于：WHERE status != 1（失效）
```

**面试话术模板**：
> "B+Tree索引相比B树的优势：
> 1. 非叶子节点不存数据，只存索引，一次能加载更多索引到内存
> 2. 叶子节点之间有指针，范围查询效率高
> 3. 所有数据都在叶子节点，查询性能稳定
> 
> 索引失效常见场景：
> 1. 对索引列使用函数：WHERE DATE(create_time) = ...
> 2. 隐式类型转换：WHERE phone = 123（phone是varchar）
> 3. 使用or连接：WHERE id = 1 OR name = 'xx'（name无索引）
> 4. like左模糊：WHERE name LIKE '%张三'
> 5. 联合索引不满足最左匹配
> 
> 我在项目中用EXPLAIN分析执行计划，重点看type（是否用索引）、
> key（用了哪个索引）、rows（扫描行数），针对性优化。"

---

#### Week 3：MySQL事务 + 锁机制

**必须掌握**：
- [ ] ACID特性
- [ ] 事务隔离级别（读未提交、读已提交、可重复读、串行化）
- [ ] MVCC多版本并发控制原理
- [ ] 表锁、行锁、间隙锁、临键锁
- [ ] 如何避免死锁？

**学习资源**：
- 📖 书籍：《高性能MySQL》第3版 - 第1章
- 📝 文章：[MySQL事务与锁机制](https://www.pdai.tech/md/db/sql-mysql/sql-mysql-mvcc.html)

**学习计划**：
- **Day 15-17**：事务ACID、隔离级别
- **Day 18-20**：MVCC原理、undo log、read view
- **Day 21**：锁机制、死锁排查

**面试话术模板**：
> "MySQL默认隔离级别是可重复读（RR），通过MVCC解决幻读问题。
> MVCC原理：每行记录有隐藏列（trx_id、roll_ptr），事务开始时创建Read View，
> 根据版本链判断记录可见性，避免加锁，提高并发性能。
> 
> 在贷款流程系统中，我遇到过死锁问题。解决方案：
> 1. 查看死锁日志：SHOW ENGINE INNODB STATUS
> 2. 分析持锁顺序，统一加锁顺序（先锁ID小的记录）
> 3. 减少事务持锁时间，拆分大事务"

---

#### Week 4：Redis核心数据结构 + 高可用

**必须掌握**：
- [ ] Redis 5种基础数据结构（String、List、Hash、Set、ZSet）
- [ ] Redis底层数据结构（SDS、ziplist、quicklist、skiplist）
- [ ] Redis为什么快？（内存+单线程+IO多路复用）
- [ ] 缓存三大问题（穿透、雪崩、击穿）
- [ ] 主从复制原理
- [ ] 哨兵模式 vs 集群模式

**学习资源**：
- 📖 书籍：《Redis设计与实现》 - 第1-3章、第15章
- 🎥 视频：B站"黑马Redis入门到实战"
- 📝 文章：[Redis面试题总结](https://www.pdai.tech/md/db/nosql-redis/db-redis-overview.html)

**学习计划**：
- **Day 22-24**：5种数据结构、应用场景
- **Day 25-26**：缓存三大问题解决方案
- **Day 27-28**：主从复制、哨兵模式

**实践任务**：
```bash
# 1. 搭建Redis主从复制（1主2从）
# 2. 搭建Redis哨兵集群（3哨兵）
# 3. 模拟主节点宕机，观察自动故障转移
# 4. 实现布隆过滤器解决缓存穿透
```

**面试话术模板**：
> "Redis快的原因：
> 1. 纯内存操作：数据在内存，读写速度快
> 2. 单线程模型：避免线程切换和锁竞争（6.0后引入IO多线程）
> 3. IO多路复用：epoll模型，一个线程处理多个连接
> 4. 高效数据结构：SDS、ziplist、skiplist
> 
> 缓存三大问题解决方案：
> - 缓存穿透：布隆过滤器 or 空值缓存
> - 缓存雪崩：过期时间加随机值 + 集群部署 + 限流降级
> - 缓存击穿：热点数据永不过期 + 分布式锁（Redisson）
> 
> 我在银行可观测性平台用Redis哨兵模式（1主2从+3哨兵）。
> 哨兵负责监控、故障转移、通知客户端，保证高可用。"

**第2个月总结**：
- [ ] 整理MySQL索引优化思维导图
- [ ] 整理Redis数据结构笔记
- [ ] 做一套MySQL+Redis面试题（50题）
- [ ] 回顾本月学习，查漏补缺

---

### 第3个月：多线程 + 并发编程（30天）

#### Week 1-2：Java多线程基础

**必须掌握**：
- [ ] 线程生命周期（新建、就绪、运行、阻塞、死亡）
- [ ] 线程创建方式（Thread、Runnable、Callable）
- [ ] synchronized关键字原理（对象锁、类锁、偏向锁、轻量级锁、重量级锁）
- [ ] volatile关键字（可见性、禁止重排序）
- [ ] wait/notify vs sleep vs yield
- [ ] ThreadLocal原理与内存泄漏

**学习资源**：
- 📖 书籍：《Java并发编程的艺术》 - 第1-4章
- 🎥 视频：B站"黑马程序员JUC并发编程"（前20集）
- 📝 文章：[Java并发编程面试题](https://www.pdai.tech/md/java/thread/java-thread-x-overview.html)

**学习计划**：
- **Day 1-3**：线程基础、线程创建方式
- **Day 4-6**：synchronized原理、锁升级
- **Day 7-9**：volatile、JMM内存模型
- **Day 10-12**：wait/notify、ThreadLocal
- **Day 13-14**：总结复习、写Demo

**实践任务**：
```java
// 1. 手写生产者-消费者模式（wait/notify）
// 2. 分析synchronized字节码（javap -v）
// 3. 验证volatile可见性和禁止重排序
// 4. 模拟ThreadLocal内存泄漏
```

**面试话术模板**：
> "synchronized锁升级过程：
> 无锁 → 偏向锁（只有一个线程访问）→ 轻量级锁（CAS竞争）→ 重量级锁（阻塞等待）
> JDK 1.6后优化了synchronized性能，引入锁消除、锁粗化、自旋锁等。
> 
> volatile保证可见性和有序性，但不保证原子性。原理：
> 1. 写操作：StoreStore屏障 + 写变量 + StoreLoad屏障（强制刷新到主内存）
> 2. 读操作：LoadLoad屏障 + 读变量 + LoadStore屏障（强制从主内存读取）
> 
> ThreadLocal内存泄漏：ThreadLocalMap的key是弱引用，value是强引用。
> 线程池场景下，线程不结束，value不会被回收，导致内存泄漏。
> 解决方案：使用后调用remove()方法。"

---

#### Week 3-4：JUC并发包 + 线程池

**必须掌握**：
- [ ] ReentrantLock vs synchronized
- [ ] AQS（AbstractQueuedSynchronizer）原理
- [ ] CountDownLatch、CyclicBarrier、Semaphore
- [ ] 线程池核心参数（7个参数）
- [ ] 线程池工作原理
- [ ] 拒绝策略（4种）
- [ ] CompletableFuture异步编排

**学习资源**：
- 📖 书籍：《Java并发编程的艺术》 - 第5-8章
- 🎥 视频：B站"黑马JUC"（后20集）

**学习计划**：
- **Day 15-17**：ReentrantLock、AQS原理
- **Day 18-20**：CountDownLatch等工具类
- **Day 21-23**：线程池核心参数、工作原理
- **Day 24-26**：CompletableFuture异步编排
- **Day 27-28**：实战：优化接口性能

**实践任务**：
```java
// 1. 用ReentrantLock实现生产者-消费者
// 2. Debug AQS源码，理解state变量
// 3. 自定义线程池，对比不同参数的表现
// 4. 用CompletableFuture并行查询多个接口
```

**面试话术模板**：
> "线程池7个核心参数：
> 1. corePoolSize：核心线程数
> 2. maximumPoolSize：最大线程数
> 3. keepAliveTime：空闲线程存活时间
> 4. unit：时间单位
> 5. workQueue：阻塞队列
> 6. threadFactory：线程工厂
> 7. handler：拒绝策略
> 
> 线程池执行流程：
> 1. 线程数 < corePoolSize：创建核心线程
> 2. 线程数 >= corePoolSize：任务加入队列
> 3. 队列满了 && 线程数 < maximumPoolSize：创建非核心线程
> 4. 线程数 >= maximumPoolSize：执行拒绝策略
> 
> 我在算力平台项目中，线程池参数设置：
> - 核心线程数=CPU核心数×2（IO密集型）
> - 最大线程数=核心×4
> - 队列容量=1000（有界队列，避免OOM）
> - 拒绝策略=CallerRunsPolicy（保证任务不丢失）"

**第3个月总结**：
- [ ] 整理Java并发编程思维导图
- [ ] 整理线程池笔记
- [ ] 做一套并发编程面试题（50题）
- [ ] 回顾前3个月学习，总结复习

**阶段一检查点**（3个月后）：
- [ ] 能画出HashMap、ConcurrentHashMap结构图
- [ ] 能讲清楚JVM内存模型、GC算法
- [ ] 能说出5个索引失效场景
- [ ] 能讲清楚Redis缓存三大问题
- [ ] 能说出线程池7个参数
- [ ] 能用JVM调优工具排查问题
- [ ] 做100道Java基础面试题（正确率90%+）

---

## 🔥 阶段二：框架原理（月4-6，共3个月）

> **目标**：深入理解Spring、MyBatis原理，掌握分布式核心概念

---

### 第4个月：Spring IoC/AOP + MyBatis（30天）

#### Week 1-2：Spring IoC容器

**必须掌握**：
- [ ] IoC容器原理（BeanFactory vs ApplicationContext）
- [ ] Bean生命周期（实例化 → 属性注入 → 初始化 → 销毁）
- [ ] 三级缓存解决循环依赖
- [ ] @Autowired vs @Resource
- [ ] Spring扩展点（BeanPostProcessor、BeanFactoryPostProcessor）

**学习资源**：
- 📖 书籍：《Spring源码深度解析》 - 第1-3章
- 🎥 视频：B站"尚硅谷Spring源码"（前20集）

**学习计划**：
- **Day 1-3**：IoC容器启动流程
- **Day 4-6**：Bean生命周期、循环依赖
- **Day 7-9**：依赖注入、自动装配
- **Day 10-12**：BeanPostProcessor扩展点
- **Day 13-14**：Debug源码、写笔记

**实践任务**：
```java
// 1. Debug Spring Boot启动过程，断点在refresh()方法
// 2. 手写简易版IoC容器
// 3. 分析循环依赖：A依赖B，B依赖A
// 4. 自定义BeanPostProcessor
```

**面试话术模板**：
> "Spring IoC容器启动流程：
> 1. 加载配置（XML/注解扫描）
> 2. 解析BeanDefinition（Bean元信息）
> 3. 实例化Bean（反射调用构造方法）
> 4. 属性注入（依赖注入）
> 5. 初始化（InitializingBean、@PostConstruct）
> 6. 放入单例池（一级缓存）
> 
> 三级缓存解决循环依赖：
> - 一级缓存singletonObjects：完整Bean
> - 二级缓存earlySingletonObjects：半成品Bean（已实例化，未初始化）
> - 三级缓存singletonFactories：Bean工厂（创建代理对象）
> 
> 我在Auth Boot Starter项目中，用BeanPostProcessor实现自定义注解扫描。"

---

#### Week 3：Spring AOP

**必须掌握**：
- [ ] AOP核心概念（切面、切点、通知、连接点）
- [ ] AOP实现原理（JDK动态代理 vs CGLIB）
- [ ] @Aspect、@Before、@After、@Around
- [ ] 如何选择JDK代理还是CGLIB？

**学习计划**：
- **Day 15-17**：AOP基本概念、使用
- **Day 18-20**：JDK动态代理 vs CGLIB
- **Day 21**：手写AOP框架

**实践任务**：
```java
// 1. 用JDK动态代理实现权限校验
// 2. 用CGLIB实现方法耗时统计
// 3. 对比JDK代理和CGLIB性能
// 4. 在Auth Boot Starter中用AOP实现@RequirePermission
```

---

#### Week 4：MyBatis原理

**必须掌握**：
- [ ] MyBatis架构（SqlSessionFactory、SqlSession、Mapper）
- [ ] SQL执行流程
- [ ] 一级缓存 vs 二级缓存
- [ ] 延迟加载原理
- [ ] 插件机制（Interceptor）

**学习计划**：
- **Day 22-24**：MyBatis架构、SQL执行流程
- **Day 25-26**：缓存机制、延迟加载
- **Day 27-28**：插件机制、分页插件原理

**面试话术模板**：
> "MyBatis一级缓存是SqlSession级别，同一个SqlSession内，相同查询会走缓存。
> 二级缓存是Mapper级别，多个SqlSession共享，需要手动开启。
> 
> MyBatis插件原理：通过动态代理拦截4大对象（Executor、StatementHandler、
> ParameterHandler、ResultSetHandler），实现增强功能。
> PageHelper分页插件就是拦截Executor，改写SQL加上LIMIT。"

---

### 第5个月：Spring Boot + Spring Cloud（30天）

#### Week 1-2：Spring Boot自动配置

**必须掌握**：
- [ ] 自动配置原理（@EnableAutoConfiguration）
- [ ] Starter工作原理（spring.factories）
- [ ] 条件注解（@ConditionalOnProperty、@ConditionalOnClass）
- [ ] 配置加载顺序（application.yml、环境变量）

**学习计划**：
- **Day 1-3**：自动配置原理
- **Day 4-6**：Starter机制
- **Day 7-9**：条件注解、配置加载
- **Day 10-12**：分析Auth Boot Starter
- **Day 13-14**：自定义Starter

**面试话术模板**：
> "我开发的Auth Boot Starter就是一个自定义Starter。实现步骤：
> 1. 创建META-INF/spring.factories，配置自动配置类
> 2. @Configuration + @ConditionalOnProperty实现条件加载
> 3. @EnableConfigurationProperties加载配置文件
> 4. 提供默认配置，支持自定义覆盖
> 使用者只需引入依赖+配置yml，即可自动装配认证授权功能。"

---

#### Week 3-4：Spring Cloud微服务

**必须掌握**：
- [ ] 服务注册与发现（Nacos、Eureka）
- [ ] 负载均衡（Ribbon、LoadBalancer）
- [ ] 服务调用（OpenFeign、RestTemplate）
- [ ] 熔断降级（Sentinel、Hystrix）
- [ ] API网关（Gateway、Zuul）
- [ ] 配置中心（Nacos Config）

**学习计划**：
- **Day 15-18**：Nacos注册中心、配置中心
- **Day 19-21**：OpenFeign、Ribbon
- **Day 22-24**：Sentinel熔断降级
- **Day 25-28**：Gateway网关

**面试话术模板**：
> "在教育平台项目中，我用Spring Cloud Gateway作为API网关。
> Gateway基于WebFlux（异步非阻塞），性能优于Zuul（Servlet阻塞）。
> 
> 配置了几个过滤器：
> - 全局认证过滤器：JWT Token验证
> - 限流过滤器：Redis滑动窗口限流
> - 日志过滤器：记录请求响应
> - 跨域过滤器：CORS跨域支持
> 
> 通过动态路由配置，实现灰度发布和蓝绿部署。"

---

### 第6个月：分布式技术（锁、事务、缓存）（30天）

#### Week 1-2：分布式锁

**必须掌握**：
- [ ] Redis实现（SET key value NX PX 30000）
- [ ] Redisson原理（看门狗机制）
- [ ] Zookeeper实现（临时顺序节点）
- [ ] 如何避免死锁？
- [ ] 如何解决超卖问题？

**学习计划**：
- **Day 1-3**：Redis分布式锁
- **Day 4-6**：Redisson看门狗机制
- **Day 7-9**：Zookeeper分布式锁
- **Day 10-12**：秒杀系统设计
- **Day 13-14**：实战优化

**实践任务**：
```java
// 1. 手写Redis分布式锁（SET NX PX + Lua脚本释放锁）
// 2. 用Redisson实现可重入锁
// 3. 实现一个秒杀系统（Redis预减库存 + 分布式锁）
```

**面试话术模板**：
> "我在贷款流程系统中用Redisson分布式锁解决限额产品超卖问题。
> 
> Redisson优势：
> 1. 看门狗机制：锁持有期间自动续期（默认30秒续期一次）
> 2. 可重入：同一线程可以多次获取锁（基于ThreadLocal记录重入次数）
> 3. 公平锁：按FIFO顺序获取锁
> 
> 解决超卖方案：
> 1. Redis预扣减：Lua脚本保证原子性
> 2. 数据库二次校验：乐观锁（UPDATE ... WHERE version=?）
> 3. 分布式锁控制并发：同一时间只有一个线程能下单"

---

#### Week 3-4：分布式事务

**必须掌握**：
- [ ] CAP理论、BASE理论
- [ ] 2PC（两阶段提交）
- [ ] 3PC（三阶段提交）
- [ ] TCC（Try-Confirm-Cancel）
- [ ] Saga模式
- [ ] Seata AT模式原理

**学习计划**：
- **Day 15-17**：CAP、BASE、2PC/3PC
- **Day 18-20**：TCC、Saga
- **Day 21-24**：Seata AT模式
- **Day 25-28**：实战：用Seata解决订单-库存-积分一致性

**面试话术模板**：
> "我在银行可观测性平台用Seata AT模式实现分布式事务。
> 
> Seata AT模式原理：
> 1. 阶段一：本地事务提交，生成undo_log回滚日志，注册分支事务
> 2. 阶段二：TC（事务协调器）通知所有分支 → 全局提交 or 全局回滚
> 
> 优势：对业务侵入小（自动生成回滚SQL），性能好（阶段一就提交，不长时间持锁）
> 
> 在用户开通权限场景（账号服务+权限服务+审计服务），保证数据一致性。
> 如果权限服务失败，账号服务会自动回滚。"

**阶段二检查点**（6个月后）：
- [ ] 能画出Spring Bean生命周期图
- [ ] 能讲清楚AOP实现原理
- [ ] 能说出Spring Boot自动配置原理
- [ ] 能设计一个秒杀系统
- [ ] 能讲清楚Seata AT模式
- [ ] 做100道Spring+分布式面试题（正确率90%+）

---

## 🚀 阶段三：深度提升（月7-9，共3个月）

> **目标**：展示技术深度，掌握中间件原理和性能优化

---

### 第7个月：Kafka + RocketMQ（30天）

#### Week 1-3：Kafka深入

**必须掌握**：
- [ ] Kafka架构（Broker、Partition、Consumer Group）
- [ ] 如何保证消息不丢失？
- [ ] 如何保证消息顺序性？
- [ ] 如何避免重复消费？
- [ ] 为什么Kafka高性能？（批量发送、零拷贝、顺序写）
- [ ] Rebalance机制

**学习资源**：
- 📖 书籍：《深入理解Kafka》 - 第1-5章
- 🎥 视频：B站"尚硅谷Kafka教程"

**学习计划**：
- **Day 1-5**：Kafka架构、Producer/Consumer
- **Day 6-10**：消息不丢失、顺序性、幂等性
- **Day 11-15**：高性能原理、零拷贝
- **Day 16-21**：实战：搭建Kafka集群

**实践任务**：
```bash
# 1. 搭建Kafka集群（3个Broker）
# 2. 写Producer和Consumer
# 3. 测试消息丢失场景（acks=0/1/all）
# 4. 压测：QPS、延迟
```

**面试话术模板**：
> "我在EchoWave项目中用Kafka做任务状态变更通知、计费事件传递。
> 
> Kafka高性能原因：
> 1. 批量发送：batch.size批量发送，减少网络IO
> 2. 零拷贝：sendfile系统调用，数据不经过用户态
> 3. 顺序写：磁盘顺序写比随机写快100倍
> 4. 分区并行：多个分区并行消费
> 
> 保证消息不丢失：
> - Producer：acks=all（所有副本确认）+ retries=3
> - Broker：replication.factor=3（3副本）
> - Consumer：手动提交offset（先处理消息，再提交）"

---

#### Week 4：RocketMQ

**必须掌握**：
- [ ] RocketMQ vs Kafka区别
- [ ] 延迟消息、事务消息
- [ ] 顺序消息实现

**学习计划**：
- **Day 22-28**：RocketMQ架构、事务消息

---

### 第8个月：性能优化 + Skywalking（30天）

#### Week 1-2：性能优化实战

**必须掌握**：
- [ ] 接口性能优化（SQL、缓存、异步）
- [ ] 数据库连接池优化（HikariCP）
- [ ] JVM参数调优
- [ ] 压测工具（JMeter、ab、wrk）

**学习计划**：
- **Day 1-5**：接口性能分析、优化方法
- **Day 6-10**：数据库优化、缓存优化
- **Day 11-14**：压测实战

**面试话术模板**：
> "在算力平台项目中，我做了一次接口性能优化：
> 
> 问题：查询账单接口RT从200ms优化到50ms
> 
> 优化方案：
> 1. SQL优化：添加索引(user_id, create_time)，避免全表扫描
> 2. 缓存优化：热点数据写入Redis，缓存命中率95%+
> 3. 批量查询：N+1问题改为in查询
> 4. 异步处理：非核心数据异步加载（CompletableFuture）
> 5. 连接池优化：HikariCP maximumPoolSize=20
> 
> 结果：接口RT从200ms降至50ms，QPS从100提升到500。"

---

#### Week 3-4：Skywalking链路追踪

**必须掌握**：
- [ ] 链路追踪原理（TraceId、SpanId）
- [ ] Agent字节码增强（JavaAgent、ASM）
- [ ] 数据上报（gRPC）
- [ ] 存储与查询（Elasticsearch）

**学习计划**：
- **Day 15-21**：Skywalking原理、部署
- **Day 22-28**：二次开发：TraceID注入日志

**面试话术模板**：
> "我在银行可观测性平台对Skywalking做了二次开发。
> 
> 通过修改Agent代码，在TraceSegment创建时注入TraceID到SLF4J MDC，
> 业务日志自动输出TraceID，无需改造业务代码。
> 
> 然后Logstash解析TraceID字段，Elasticsearch索引优化，Kibana定制查询面板，
> 实现从Skywalking链路详情一键跳转到ELK日志详情，问题定位效率显著提升。"

---

### 第9个月：系统设计 + 架构能力（30天）

#### 必须掌握的系统设计题

**1. 秒杀系统设计**
- 限流（网关+Redis滑动窗口）
- 缓存（Redis预减库存）
- 异步（Kafka消息队列）
- 数据库（乐观锁防超卖）

**2. 短链系统设计**
- 长链转短链（hash+62进制编码）
- 短链还原（Redis缓存+MySQL持久化）
- 统计分析（Kafka+Flink实时计算）

**3. 分布式限流系统**
- 固定窗口 vs 滑动窗口
- 令牌桶 vs 漏桶
- Redis+Lua实现

**4. 亿级数据去重系统**
- 布隆过滤器
- HyperLogLog
- BitMap

**学习资源**：
- 📝 文章：[系统设计面试题](https://github.com/donnemartin/system-design-primer)
- 🎥 视频：B站"大厂系统设计面试题"

---

## 📝 阶段四：冲刺准备（月10-12，共2-3个月）

> **目标**：查漏补缺，模拟面试，准备秋招

### 第10个月：面试题整理 + 查漏补缺

**任务清单**：
- [ ] 整理Java基础面试题100道（集合、JVM、多线程）
- [ ] 整理MySQL+Redis面试题100道
- [ ] 整理Spring+分布式面试题100道
- [ ] 整理中间件面试题50道（Kafka、Skywalking）
- [ ] 整理系统设计题10道
- [ ] 回顾9个月学习笔记，查漏补缺

### 第11个月：模拟面试 + 项目复盘

**任务清单**：
- [ ] 找朋友模拟面试（每周2次）
- [ ] 复盘简历上的3个项目（EchoWave、银行平台、Auth Starter）
- [ ] 准备项目难点、亮点话术
- [ ] 准备离职原因、职业规划等HR面问题

### 第12个月：简历优化 + 秋招投递

**任务清单**：
- [ ] 优化简历（技术栈、项目经验、个人优势）
- [ ] 准备作品集（GitHub开源项目、技术博客）
- [ ] 投递简历（Boss直聘、拉勾、猎聘）
- [ ] 持续面试，总结复盘

---

## 🎯 学习资源推荐

### 必读书籍（按优先级）

**基础必读**：
1. 《深入理解Java虚拟机》第3版 - 周志明 ⭐⭐⭐⭐⭐
2. 《高性能MySQL》第3版 - Baron Schwartz ⭐⭐⭐⭐⭐
3. 《Redis设计与实现》 - 黄健宏 ⭐⭐⭐⭐⭐
4. 《Java并发编程的艺术》 - 方腾飞 ⭐⭐⭐⭐⭐

**进阶推荐**：
5. 《Spring源码深度解析》 - 郝佳 ⭐⭐⭐⭐
6. 《深入理解Kafka》 - 朱忠华 ⭐⭐⭐⭐
7. 《Java性能权威指南》 - Scott Oaks ⭐⭐⭐⭐
8. 《设计模式》 - GoF ⭐⭐⭐

### 视频课程

**B站免费课程**：
- 尚硅谷JVM完整版
- 黑马程序员Redis入门到实战
- 尚硅谷MySQL高级
- 黑马程序员JUC并发编程
- 尚硅谷Spring源码解析
- 黑马Spring Cloud

**付费课程**（可选）：
- 极客时间《Java核心技术36讲》
- 极客时间《MySQL实战45讲》
- 极客时间《从0开始学架构》

### 技术网站

1. **Java技术栈**：https://www.pdai.tech/ （强烈推荐，面试题宝库）
2. **美团技术博客**：https://tech.meituan.com/
3. **阿里技术博客**：https://developer.aliyun.com/
4. **掘金**：https://juejin.cn/
5. **GitHub**：https://github.com/（搜索awesome-java）

---

## 📝 学习方法建议

### 高效学习技巧

**1. 费曼学习法**
- 学完一个知识点，尝试讲给别人听（或者自己讲给自己听）
- 如果讲不清楚，说明没理解，回去重学
- 建议：每周日总结本周学习，录音回放

**2. 刻意练习**
- 不要只看视频，一定要动手实践
- 每个知识点都要写代码验证
- 面试题要反复背诵和理解（最少3遍）

**3. 输出倒逼输入**
- 写技术博客（掘金、CSDN、个人博客）
- 整理面试题文档（用飞书/Notion）
- 给朋友讲解技术原理

**4. 定期复习**
- 艾宾浩斯遗忘曲线：1天、3天、7天、15天、30天复习
- 每周日复习本周内容
- 每月底复习本月内容
- 每3个月复习前面所有内容

### 避免的坑

❌ **不要陷入源码泥潭**
- 不需要把Spring/MyBatis源码全部看完
- 只需要看核心流程（IoC、AOP、SQL执行）
- 面试官不会问太细的源码问题

❌ **不要追求完美**
- 不需要每个知识点都100%掌握
- 先掌握80%的常见问题，足够应对面试
- 剩下20%可以长期学习

❌ **不要只看不练**
- 看100遍视频不如写1遍代码
- 理论+实践+面试题，三位一体

❌ **不要孤立学习**
- 加入技术社群（掘金、知识星球）
- 找学习伙伴，互相监督
- 遇到问题及时提问（Stack Overflow、掘金）

### 学习习惯养成

**工作日（周一至周五）**：
- 晚上8:00-10:00固定学习（2小时）
- 关闭手机通知，专注学习
- 番茄工作法：25分钟学习 + 5分钟休息

**周末（周六周日）**：
- 上午9:00-12:00（3小时）
- 下午2:00-5:00（3小时）
- 晚上复习总结（1小时）

**每周日晚上**：
- 回顾本周学习内容
- 整理笔记、画思维导图
- 做一套面试题测试
- 计划下周学习任务

**每月最后一周**：
- 复习本月所有内容
- 做100道面试题测试
- 总结收获，调整计划

---

## 🎤 面试话术准备

### 通用圆场话术

**当被问到不会的问题时**：
> "这个问题我了解一些基本概念，但没有深入研究过。我的理解是...[说出你知道的部分]...
> 不过我对这个方向很感兴趣，后续会深入学习。您能给我一些学习建议吗？"

**当被质疑简历内容时**：
> "这个项目我确实参与了，但主要负责[具体模块]。关于[被质疑的部分]，
> 是团队其他同事负责的，我了解整体架构和设计思路，但具体实现细节不是特别清楚。"

**当被问到没用过的技术时**：
> "这个技术我了解过但没有在生产环境使用过。我知道它的主要特点是...[说基本概念]...
> 如果项目需要，我可以快速学习并应用。"

### 项目经验话术（重要！）

**EchoWave算力平台**：
> "这个项目最大的技术挑战是节点管理和任务调度。我们采用Nomad+Consul架构，
> Nomad负责任务调度，Consul负责服务发现和健康检查。
> 
> 我主要负责：
> 1. 节点管理模块：节点注册、状态管理、资源信息上报
> 2. 计费系统：基于Redis+MySQL实现计费数据采集和账单生成
> 3. 性能优化：JVM调优（G1垃圾回收器）、Redis缓存优化、SQL索引优化
> 
> 技术难点在于如何保证计费准确性，我们通过定时对账任务（每小时）校验数据一致性。"

**银行可观测性平台**：
> "这个项目最大的挑战是打通链路追踪和日志系统。我通过修改Skywalking Agent代码，
> 注入TraceID到日志上下文，实现从链路详情一键跳转到日志详情。
> 
> 我主要负责：
> 1. Skywalking二次开发：Agent修改、TraceID注入
> 2. ELK日志处理：搭建Filebeat→Kafka→Logstash→ES链路
> 3. Redis哨兵集群：实现Session共享，保证高可用
> 
> 这个项目让我对可观测性体系有了深入理解。"

**Auth Boot Starter**：
> "这是我的个人开源项目，目的是快速搭建企业级Spring Boot应用。
> 采用模块化设计，8个独立模块，可按需引入。
> 
> 核心技术：
> 1. RBAC权限模型：用户-角色-权限-菜单四级关联
> 2. 注解驱动鉴权：@RequirePermission通过AOP实现
> 3. JWT无状态认证：Token自动验证、用户上下文注入
> 
> 这个项目让我对Spring Boot自动配置、Starter机制有了深入理解。"

---

## ⏰ 详细时间规划（12个月甘特图）

| 月份 | 学习内容 | 目标成果 | 检查点 |
|------|---------|---------|--------|
| 第1月 | Java集合 + JVM | 掌握HashMap、JVM调优 | 做50道面试题 |
| 第2月 | MySQL + Redis | 掌握索引优化、缓存方案 | 做50道面试题 |
| 第3月 | 多线程 + 并发编程 | 掌握线程池、AQS原理 | 做50道面试题 |
| 第4月 | Spring IoC/AOP + MyBatis | 掌握Spring原理 | 做50道面试题 |
| 第5月 | Spring Boot + Cloud | 掌握微服务架构 | 做50道面试题 |
| 第6月 | 分布式技术 | 掌握分布式锁、事务 | 做50道面试题 |
| 第7月 | Kafka + RocketMQ | 掌握消息队列原理 | 做30道面试题 |
| 第8月 | 性能优化 + Skywalking | 掌握性能调优方法 | 做30道面试题 |
| 第9月 | 系统设计 + 架构 | 掌握架构设计能力 | 设计10个系统 |
| 第10月 | 面试题整理 | 整理500道面试题 | 正确率90%+ |
| 第11月 | 模拟面试 | 每周2次模拟面试 | 能流畅回答 |
| 第12月 | 秋招投递 | 拿到3个offer | 薪资25K+ |

---

## ✅ 学习检查清单

### 第1个月检查点
- [ ] 能画出HashMap结构图（数组+链表+红黑树）
- [ ] 能讲清楚ConcurrentHashMap原理（1.7 vs 1.8）
- [ ] 能背出JVM内存模型（堆、栈、方法区、程序计数器）
- [ ] 能说出4种GC算法（标记-清除、标记-整理、复制、分代）
- [ ] 能用jstat、jmap工具排查问题

### 第3个月检查点
- [ ] 能画出B+Tree结构图
- [ ] 能说出5个索引失效场景
- [ ] 能讲清楚Redis缓存三大问题解决方案
- [ ] 能说出线程池7个参数
- [ ] 能用ReentrantLock实现生产者-消费者

### 第6个月检查点
- [ ] 能画出Spring Bean生命周期图
- [ ] 能讲清楚AOP实现原理（JDK vs CGLIB）
- [ ] 能说出Spring Boot自动配置原理
- [ ] 能设计一个秒杀系统
- [ ] 能讲清楚Seata AT模式原理

### 第9个月检查点
- [ ] 能讲清楚Kafka高性能原理
- [ ] 能说出Skywalking二次开发方案
- [ ] 能设计5个常见系统（秒杀、短链、限流、去重、推荐）
- [ ] 能优化一个慢接口
- [ ] 能做JVM调优、MySQL调优

### 第12个月检查点
- [ ] 做500道面试题，正确率90%+
- [ ] 能流畅讲解简历上的3个项目
- [ ] 能回答90%的Java后端面试题
- [ ] 拿到3个以上offer
- [ ] 薪资达到25K+

---

## 💡 最后的建议

### 心态调整

**不要焦虑**：
- 很多人都是从CRUD开始的，慢慢学就好
- 有将近一年时间，非常充裕
- 基础打好了，后面学习会越来越快

**不要放弃**：
- 3个月后你会感谢现在努力的自己
- 6个月后你会发现自己进步巨大
- 12个月后你会成为真正的Java高级工程师

**不要装懂**：
- 面试时实事求是，不会就说不会
- 但要展示学习能力和思考能力
- 简历上写的技术一定要能圆得回来

### 学习心法

1. **扎实基础，慢慢消化**
   - 前3个月打好基础，不急于求成
   - 每个知识点都要充分理解和实践
   - 基础不牢，地动山摇

2. **刻意练习，反复复习**
   - 理论+实践+面试题，三位一体
   - 艾宾浩斯遗忘曲线：定期复习
   - 费曼学习法：能讲给别人听

3. **输出倒逼，长期主义**
   - 写技术博客、整理笔记
   - 参与开源项目、技术社群
   - 技术学习是马拉松，不是百米冲刺

---

## 🎯 一年后的你

**掌握的技能**：
- ✅ 能回答95%的Java后端面试题
- ✅ 能讲清楚JVM、MySQL、Redis、多线程原理
- ✅ 能画出Spring、MyBatis、Kafka核心流程图
- ✅ 能设计常见的分布式系统（秒杀、短链、限流等）
- ✅ 能用JVM调优、MySQL调优、性能优化工具
- ✅ 能自信圆场简历上的技术点

**薪资目标**：
- 成都：25-30K（高级工程师）
- 杭州：30-35K（高级工程师）
- 北上深：35-45K（高级工程师）

**职业发展**：
- 技术专家方向
- 架构师方向
- 技术管理方向

---

## 🚀 立即行动（从今天开始！）

### 第1步：购买/下载学习资料
- [ ] 《深入理解Java虚拟机》（京东/当当购买）
- [ ] 《高性能MySQL》（京东/当当购买）
- [ ] 其他书籍可以先看电子版

### 第2步：设置学习环境
- [ ] 准备笔记本（推荐飞书文档或Notion）
- [ ] 安装开发工具（IDEA、MySQL、Redis、Kafka）
- [ ] 注册B站账号，收藏学习视频
- [ ] 创建学习打卡群（邀请朋友监督）

### 第3步：制定详细计划
- [ ] 打印本学习计划，贴在书桌前
- [ ] 设置手机提醒（晚上8点学习提醒）
- [ ] 制作Excel学习进度表
- [ ] 准备奖励机制（完成阶段目标奖励自己）

### 第4步：开始第1天学习（今天！）

**Day 1任务（2小时）**：
- 19:00-19:30：购买书籍、下载学习资料
- 19:30-20:00：看《深入理解Java虚拟机》第2章（JVM内存模型）
- 20:00-21:00：看B站"尚硅谷JVM"视频前2集
- 21:00-21:30：整理笔记，画JVM内存模型图

**Day 2任务（2小时）**：
- 20:00-21:00：看《深入理解Java虚拟机》第3章（垃圾回收）
- 21:00-22:00：看B站"尚硅谷JVM"视频第3-5集

**周末任务（6小时×2天）**：
- 看完JVM视频前10集
- 整理JVM笔记
- 写一个Demo模拟Full GC

---

**加油！从今天开始，每天进步一点点！**

**有将近一年时间，足够你从CRUD小白成长为Java高级工程师！**

**扎实基础，慢慢消化，不要急于求成，基础打好了，后面会越来越顺！**

**12个月后，你会感谢现在努力的自己！** 🚀💪

---

**最后更新**：2024-10-29  
**作者**：Claude (AI编程助手)  
**适用人群**：会用框架做CRUD，想深入学习原理的Java开发者  
**学习周期**：9-12个月（明年秋招）  
**每周学习时间**：22小时（工作日10小时 + 周末12小时）
