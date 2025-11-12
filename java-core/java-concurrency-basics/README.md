# Java 并发编程基础 (Java Concurrency Basics)

> 掌握 Java 并发编程的核心知识，包括线程、同步机制、线程池、并发集合等

## 📚 模块内容

### 1. 线程基础 (Thread Fundamentals)

- **ThreadDemo** - 线程创建和基本操作
  - 继承Thread类
  - 实现Runnable接口
  - 实现Callable接口
  - 线程生命周期
  - 线程基本操作（start、join、sleep、interrupt）

### 2. 线程同步 (Thread Synchronization)

- **SynchronizedDemo** - synchronized关键字使用
  - 同步方法
  - 同步代码块
  - 对象锁 vs 类锁
  - 可重入性

- **VolatileDemo** - volatile关键字使用
  - 可见性问题
  - volatile解决可见性
  - volatile不保证原子性

### 3. 线程池 (Thread Pool)

- **ThreadPoolDemo** - 线程池使用
  - FixedThreadPool
  - CachedThreadPool
  - SingleThreadExecutor
  - ScheduledThreadPool
  - ThreadPoolExecutor自定义配置

### 4. 并发集合 (Concurrent Collections)

- **ConcurrentHashMapDemo** - ConcurrentHashMap使用
  - 基本操作
  - 原子操作（computeIfAbsent、computeIfPresent）
  - 遍历操作

- **BlockingQueueDemo** - BlockingQueue使用
  - ArrayBlockingQueue
  - LinkedBlockingQueue
  - SynchronousQueue
  - 生产者-消费者模式

- **CopyOnWriteArrayListDemo** - CopyOnWriteArrayList使用
  - 读多写少场景
  - 弱一致性说明

## 🚀 快速开始

### 运行示例

所有示例都包含 main 方法，可以直接运行。

```bash
# 编译项目
mvn clean compile

# 运行特定示例（在 IDE 中直接运行）
# 例如：ThreadDemo.java
```

### 包结构

```
cn.openthedoor.concurrency
├── thread/              # 线程基础
│   ├── ThreadDemo.java
│   └── CallableDemo.java
├── sync/                # 线程同步
│   ├── SynchronizedDemo.java
│   └── VolatileDemo.java
├── pool/                # 线程池
│   └── ThreadPoolDemo.java
└── collection/          # 并发集合
    ├── ConcurrentHashMapDemo.java
    ├── BlockingQueueDemo.java
    └── CopyOnWriteArrayListDemo.java
```

## 💡 学习要点

### 重点知识

1. **线程基础**
   - 线程的创建方式（推荐Runnable）
   - 线程的生命周期（6种状态）
   - 线程的基本操作（start、join、sleep、interrupt）

2. **线程同步**
   - synchronized的作用和使用
   - volatile的作用和限制
   - Java内存模型（JMM）
   - happens-before规则

3. **线程池**
   - Executor框架结构
   - ThreadPoolExecutor核心参数
   - 线程池执行流程
   - 拒绝策略

4. **并发集合**
   - ConcurrentHashMap实现原理
   - BlockingQueue的使用场景
   - CopyOnWriteArrayList的适用场景

### 易错点 ⚠️

1. **start() vs run()**
   ```java
   Thread thread = new Thread(() -> {});
   thread.start(); // ✅ 创建新线程
   thread.run();   // ❌ 普通方法调用
   ```

2. **volatile不保证原子性**
   ```java
   private volatile int count = 0;
   count++; // ❌ 不是原子操作！
   ```

3. **synchronized锁对象**
   ```java
   // 实例方法：锁this
   public synchronized void method() {}
   
   // 静态方法：锁类对象
   public static synchronized void method() {}
   ```

4. **线程池队列选择**
   ```java
   // ❌ 无界队列可能导致OOM
   new LinkedBlockingQueue<>()
   
   // ✅ 使用有界队列
   new ArrayBlockingQueue<>(1000)
   ```

## 🎯 面试高频问题

### 1. synchronized和volatile的区别？
- synchronized：保证原子性、可见性、有序性
- volatile：只保证可见性、有序性，不保证原子性

### 2. 线程池的核心参数有哪些？
- corePoolSize、maximumPoolSize、keepAliveTime、workQueue、threadFactory、handler

### 3. ConcurrentHashMap的实现原理？
- JDK 1.8：CAS + synchronized，桶为空时CAS插入，桶不为空时锁链表头节点

### 4. 线程的生命周期？
- NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED

### 5. 如何避免死锁？
- 避免嵌套锁、统一锁顺序、使用超时锁

## 📖 扩展学习

### 推荐阅读
- 《Java并发编程实战》- 并发编程经典教材
- 《Java并发编程的艺术》- 深入理解并发原理
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)

### 相关知识
- [Java 线程基础](../docs/java-core/07-01-线程基础.md)
- [Java 线程同步](../docs/java-core/07-02-线程同步与内存模型.md)
- [Java 线程池](../docs/java-core/07-05-线程池.md)
- [Java 并发集合](../docs/java-core/07-06-并发集合.md)

## ✅ 练习建议

1. **基础练习**
   - 实现多线程下载文件
   - 使用synchronized实现线程安全的计数器
   - 使用线程池处理批量任务

2. **进阶练习**
   - 实现生产者-消费者模式
   - 使用ConcurrentHashMap实现缓存
   - 实现自定义线程池

3. **实战练习**
   - 算力平台任务调度器（使用线程池）
   - 钱包结算系统（使用并发集合）
   - 节点监控系统（使用并发工具类）

---

**返回：** [Java Core 主页](../)  
**文档：** [Java 并发编程文档](../docs/java-core/07-Java并发编程.md)

