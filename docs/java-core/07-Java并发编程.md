# Java 并发编程 (Java Concurrency Programming)

!!! abstract "核心内容"
    深入理解Java并发编程的核心概念、原理和实践，掌握线程、同步机制、线程池、并发集合等关键技术，能够在高并发场景下编写安全、高效的代码。

---

## 📚 文档导航

### 核心章节（已创建）

- **[07-01 - 线程基础](./07-01-线程基础.md)** - Thread、Runnable、Callable、线程生命周期
- **[07-02 - 线程同步与内存模型](./07-02-线程同步与内存模型.md)** - synchronized、volatile、JMM、happens-before规则
- **[07-05 - 线程池](./07-05-线程池.md)** - Executor框架、ThreadPoolExecutor源码、最佳实践
- **[07-06 - 并发集合](./07-06-并发集合.md)** - ConcurrentHashMap、BlockingQueue、CopyOnWriteArrayList

### 扩展章节（已创建）

- **[07-03 - 锁机制](./07-03-锁机制.md)** - ReentrantLock、ReadWriteLock、StampedLock
- **[07-04 - 并发工具类](./07-04-并发工具类.md)** - CountDownLatch、CyclicBarrier、Semaphore、Exchanger、Phaser
- **[07-07 - 原子类与CAS](./07-07-原子类与CAS.md)** - Atomic类、CAS原理、ABA问题、LongAdder
- **[07-08 - 并发设计模式](./07-08-并发设计模式.md)** - 生产者消费者、ThreadLocal、Future模式、不变模式
- **[07-09 - 性能优化与最佳实践](./07-09-性能优化与最佳实践.md)** - 性能调优、死锁/活锁/饥饿、生产环境最佳实践

---

## 🎯 学习路径

### 初级阶段（1-2周）

1. **线程基础** - 理解线程的概念、创建方式和生命周期
   - 学习 [07-01 - 线程基础](./07-01-线程基础.md)
   - 实践：创建多线程程序，观察线程执行顺序

2. **线程同步** - 掌握基本的同步机制
   - 学习 [07-02 - 线程同步与内存模型](./07-02-线程同步与内存模型.md)
   - 实践：使用synchronized解决线程安全问题

### 中级阶段（2-3周）

3. **线程池** - 理解线程池的原理和使用
   - 学习 [07-05 - 线程池](./07-05-线程池.md)
   - 实践：根据业务场景选择合适的线程池配置

4. **并发集合** - 掌握线程安全的集合类
   - 学习 [07-06 - 并发集合](./07-06-并发集合.md)
   - 实践：在高并发场景下使用ConcurrentHashMap

### 高级阶段（3-4周）

5. **锁机制** - 深入理解显式锁
   - 学习 [07-03 - 锁机制](./07-03-锁机制.md)
   - 实践：使用ReentrantLock实现可中断、可超时的锁操作

6. **并发工具类** - 掌握JUC工具类的使用
   - 学习 [07-04 - 并发工具类](./07-04-并发工具类.md)
   - 实践：使用CountDownLatch等待多个任务完成

7. **原子类与CAS** - 理解无锁编程
   - 学习 [07-07 - 原子类与CAS](./07-07-原子类与CAS.md)
   - 实践：使用AtomicInteger实现线程安全的计数器

8. **设计模式** - 掌握并发设计模式
   - 学习 [07-08 - 并发设计模式](./07-08-并发设计模式.md)
   - 实践：实现生产者-消费者模式，使用ThreadLocal传递上下文

9. **性能优化** - 并发性能调优和最佳实践
   - 学习 [07-09 - 性能优化与最佳实践](./07-09-性能优化与最佳实践.md)
   - 实践：优化线程池配置，检测和解决死锁问题

---

## 💡 核心知识点速查

### 线程创建方式对比

| 方式 | 类/接口 | 特点 | 适用场景 |
|------|---------|------|---------|
| **继承Thread** | `extends Thread` | 简单直接 | 简单任务 |
| **实现Runnable** | `implements Runnable` | 推荐方式，可复用 | 大多数场景 |
| **实现Callable** | `implements Callable<T>` | 有返回值，可抛异常 | 需要返回结果的场景 |
| **线程池** | `ExecutorService` | 资源可控，性能好 | 生产环境推荐 |

### 同步机制对比

| 机制 | 关键字/类 | 特点 | 性能 | 使用场景 |
|------|----------|------|------|---------|
| **synchronized** | `synchronized` | JVM内置，自动释放锁 | 中等 | 简单同步场景 |
| **volatile** | `volatile` | 保证可见性，不保证原子性 | 高 | 单写多读场景 |
| **ReentrantLock** | `java.util.concurrent.locks` | 可中断、可超时、公平锁 | 中等 | 复杂同步场景 |
| **CAS** | `Atomic*`类 | 无锁编程，性能高 | 很高 | 高并发计数场景 |

### 线程池类型对比

| 线程池 | 核心线程数 | 最大线程数 | 队列 | 适用场景 |
|--------|----------|----------|------|---------|
| **FixedThreadPool** | 固定 | 固定 | LinkedBlockingQueue | CPU密集型任务 |
| **CachedThreadPool** | 0 | Integer.MAX_VALUE | SynchronousQueue | 短时任务，IO密集型 |
| **SingleThreadExecutor** | 1 | 1 | LinkedBlockingQueue | 顺序执行任务 |
| **ScheduledThreadPool** | 指定 | Integer.MAX_VALUE | DelayedWorkQueue | 定时任务 |
| **ThreadPoolExecutor** | 自定义 | 自定义 | 自定义 | 生产环境推荐 |

### 并发集合对比

| 集合类 | 线程安全机制 | 性能特点 | 适用场景 |
|--------|------------|---------|---------|
| **ConcurrentHashMap** | CAS + synchronized | 分段锁，高并发性能好 | 高并发Map场景 |
| **CopyOnWriteArrayList** | 写时复制 | 读快写慢 | 读多写少场景 |
| **BlockingQueue** | 阻塞机制 | 生产者消费者模式 | 任务队列 |
| **ConcurrentLinkedQueue** | CAS | 无锁队列 | 高并发队列场景 |

---

## 🔧 在算力平台中的应用场景

### 1. 任务调度系统（Task Scheduling）

在算力平台的Nomad任务调度中，需要处理大量并发任务：

```java
// 使用线程池管理任务调度线程
ExecutorService schedulerPool = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
);

// 并发处理多个任务调度请求
schedulerPool.submit(() -> {
    // 从Nomad获取任务状态
    // 更新数据库和ES
});
```

**相关知识点：** [线程池](./07-05-线程池.md)

### 2. 节点信息采集（Node Information Collection）

定时任务需要并发采集多个节点的资源信息：

```java
// 使用CountDownLatch等待所有节点信息采集完成
CountDownLatch latch = new CountDownLatch(nodeCount);
for (Node node : nodes) {
    executorService.submit(() -> {
        try {
            // 采集节点信息
            collectNodeInfo(node);
        } finally {
            latch.countDown();
        }
    });
}
latch.await(); // 等待所有任务完成
```

**相关知识点：** [并发工具类](./07-04-并发工具类.md)（待创建）

### 3. 钱包结算系统（Billing System）

结算系统需要保证线程安全，避免重复扣费：

```java
// 使用ConcurrentHashMap存储用户钱包余额
ConcurrentHashMap<Long, AtomicLong> walletMap = new ConcurrentHashMap<>();

// 线程安全的扣费操作
public boolean deduct(Long userId, Long amount) {
    walletMap.computeIfAbsent(userId, k -> new AtomicLong(0))
             .updateAndGet(balance -> {
                 if (balance >= amount) {
                     return balance - amount;
                 }
                 throw new InsufficientBalanceException();
             });
    return true;
}
```

**相关知识点：** [并发集合](./07-06-并发集合.md)、[原子类与CAS](./07-07-原子类与CAS.md)（待创建）

### 4. 任务状态同步（Task Status Sync）

使用BlockingQueue实现任务状态的生产者-消费者模式：

```java
// 生产者：定时任务采集Nomad任务状态
BlockingQueue<TaskStatus> statusQueue = new LinkedBlockingQueue<>();

// 消费者：批量更新数据库
executorService.submit(() -> {
    while (true) {
        List<TaskStatus> batch = new ArrayList<>();
        statusQueue.drainTo(batch, 100); // 批量获取
        if (!batch.isEmpty()) {
            batchUpdateDatabase(batch);
        }
    }
});
```

**相关知识点：** [并发集合](./07-06-并发集合.md)、[并发设计模式](./07-08-并发设计模式.md)（待创建）

---

## 🎓 面试高频问题

### 必考题（⭐⭐⭐⭐⭐）

1. **synchronized和volatile的区别？**
   - synchronized：保证原子性和可见性，可重入锁
   - volatile：只保证可见性，不保证原子性
   - [详细答案](./07-02-线程同步与内存模型.md#synchronized和volatile的区别)

2. **线程池的核心参数有哪些？**
   - corePoolSize、maximumPoolSize、keepAliveTime、workQueue、threadFactory、handler
   - [详细答案](./07-05-线程池.md#线程池核心参数)

3. **ConcurrentHashMap的实现原理？**
   - JDK 1.8之前：分段锁（Segment）
   - JDK 1.8之后：CAS + synchronized
   - [详细答案](./07-06-并发集合.md#concurrenthashmap实现原理)

4. **线程的生命周期？**
   - NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED
   - [详细答案](./07-01-线程基础.md#线程生命周期)

5. **如何避免死锁？**
   - 避免嵌套锁、使用超时锁、统一锁顺序
   - [详细答案](./07-09-性能优化与最佳实践.md#死锁问题)（待创建）

### 高频题（⭐⭐⭐⭐）

1. **ThreadLocal的原理和使用场景？**
   - ThreadLocalMap、内存泄漏问题
   - [详细答案](./07-08-并发设计模式.md#threadlocal)（待创建）

2. **CAS的ABA问题如何解决？**
   - 使用版本号、AtomicStampedReference
   - [详细答案](./07-07-原子类与CAS.md#aba问题)（待创建）

3. **线程池的拒绝策略有哪些？**
   - AbortPolicy、CallerRunsPolicy、DiscardPolicy、DiscardOldestPolicy
   - [详细答案](./07-05-线程池.md#拒绝策略)

4. **BlockingQueue的实现原理？**
   - 基于ReentrantLock和Condition实现阻塞
   - [详细答案](./07-06-并发集合.md#blockingqueue)

5. **happens-before规则有哪些？**
   - 程序顺序规则、volatile规则、传递性规则等
   - [详细答案](./07-02-线程同步与内存模型.md#happens-before规则)

---

## 📖 扩展阅读

### 官方文档
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [java.util.concurrent Package](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html)
- [Java Memory Model](https://docs.oracle.com/javase/specs/jls/se17/html/jls-17.html)

### 推荐书籍
- 《Java并发编程实战》- 并发编程经典教材
- 《Java并发编程的艺术》- 深入理解并发原理
- 《深入理解Java虚拟机》- JMM内存模型详解

### 在线资源
- [Baeldung - Java Concurrency](https://www.baeldung.com/java-concurrency)
- [Java Concurrency in Practice - GitHub](https://github.com/jcip/jcip.github.com)

---

## ✅ 实践建议

### 基础练习
1. 实现多线程下载文件
2. 使用synchronized实现线程安全的计数器
3. 使用线程池处理批量任务

### 进阶练习
1. 实现生产者-消费者模式
2. 使用ConcurrentHashMap实现缓存
3. 实现自定义线程池

### 实战项目
1. **算力平台任务调度器** - 使用线程池管理任务调度
2. **钱包结算系统** - 使用并发集合保证线程安全
3. **节点监控系统** - 使用并发工具类协调多线程采集

---

## 📝 更新日志

- **2025-01** - 初始版本发布
  - 创建主文档和4个核心子文档
  - 线程基础、线程同步、线程池、并发集合
  - 后续将补充锁机制、工具类、原子类等章节

---

**开始学习：** [07-01 - 线程基础 →](./07-01-线程基础.md)

**祝你学习顺利！** 🎉

