# 线程创建四种方式实验代码

本目录包含Java中创建线程的四种方式的完整实验代码。

## 📁 文件说明

### 1. Method1ExtendThread.java
**方式一：继承Thread类**

- ✅ 优点：简单直接，易于理解
- ❌ 缺点：Java单继承限制，不够灵活

**示例代码：**
```java
public class MyThread extends Thread {
    @Override
    public void run() {
        // 线程执行的代码
    }
}

MyThread thread = new MyThread();
thread.start();
```

### 2. Method2ImplementRunnable.java
**方式二：实现Runnable接口（推荐）**

- ✅ 优点：可以继承其他类，更灵活；符合面向接口编程原则
- ❌ 缺点：无返回值，不能抛出受检异常

**示例代码：**
```java
// 方式1：实现类
Thread thread = new Thread(new MyRunnable());

// 方式2：匿名内部类
Thread thread = new Thread(new Runnable() {
    public void run() { }
});

// 方式3：Lambda表达式
Thread thread = new Thread(() -> { });
```

### 3. Method3ImplementCallable.java
**方式三：实现Callable接口**

- ✅ 优点：有返回值，可以抛出异常
- ❌ 缺点：使用相对复杂，需要配合FutureTask使用

**示例代码：**
```java
Callable<String> task = () -> {
    return "结果";
};

FutureTask<String> futureTask = new FutureTask<>(task);
Thread thread = new Thread(futureTask);
thread.start();

String result = futureTask.get(); // 获取返回值
```

### 4. Method4UseThreadPool.java
**方式四：使用线程池（生产环境推荐）**

- ✅ 优点：资源可控，性能好，便于管理
- ❌ 缺点：需要理解线程池参数配置

**示例代码：**
```java
// 使用Executors工厂方法
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> { });

// 使用ThreadPoolExecutor自定义（推荐）
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize,
    maximumPoolSize,
    keepAliveTime,
    unit,
    workQueue,
    threadFactory,
    handler
);
```

### 5. ThreadCreationDemo.java
**综合演示程序**

提供交互式菜单，可以：
- 单独运行每种方式的演示
- 运行所有演示
- 对比四种方式的优缺点

## 🚀 运行方式

### 方式1：运行单个示例
```bash
# 方式一
java cn.openthedoor.concurrency.thread.Method1ExtendThread

# 方式二
java cn.openthedoor.concurrency.thread.Method2ImplementRunnable

# 方式三
java cn.openthedoor.concurrency.thread.Method3ImplementCallable

# 方式四
java cn.openthedoor.concurrency.thread.Method4UseThreadPool
```

### 方式2：运行综合演示（推荐）
```bash
java cn.openthedoor.concurrency.thread.ThreadCreationDemo
```

## 📊 四种方式对比

| 特性 | 继承Thread | 实现Runnable | 实现Callable | 使用线程池 |
|------|-----------|-------------|-------------|-----------|
| 实现难度 | 简单 | 简单 | 中等 | 中等 |
| 是否有返回值 | 否 | 否 | 是 | 是 |
| 能否抛出异常 | 否 | 否 | 是 | 是 |
| 继承灵活性 | 差 | 好 | 好 | 好 |
| 资源管理 | 差 | 差 | 差 | 好 |
| 适用场景 | 简单任务 | 一般任务 | 需要返回值 | 生产环境 |
| 推荐程度 | ★ | ★★★ | ★★★ | ★★★★★ |

## 💡 最佳实践

### 1. 选择合适的方式
- **学习场景**：使用继承Thread或实现Runnable
- **需要返回值**：使用Callable接口
- **生产环境**：强烈推荐使用线程池

### 2. 线程命名
```java
Thread thread = new Thread(() -> {}, "MyThread-1");
```

### 3. 异常处理
```java
thread.setUncaughtExceptionHandler((t, e) -> {
    logger.error("线程异常", e);
});
```

### 4. 线程池配置
```java
// ❌ 不推荐：使用Executors创建无界队列
ExecutorService executor = Executors.newFixedThreadPool(10);

// ✅ 推荐：自定义ThreadPoolExecutor
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    10,                              // 核心线程数
    20,                              // 最大线程数
    60L, TimeUnit.SECONDS,          // 空闲线程存活时间
    new ArrayBlockingQueue<>(100),  // 有界队列
    new CustomThreadFactory(),      // 自定义线程工厂
    new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
);
```

### 5. 及时关闭线程池
```java
executor.shutdown(); // 温和关闭
// 或
executor.shutdownNow(); // 立即关闭
```

## 📖 相关文档

- [07-01-线程基础.md](../../../../../docs/java-core/07-01-线程基础.md)
- [Java Thread API](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)

## 🎯 学习要点

### 核心概念
1. **start() vs run()**
   - `start()` - 创建新线程，异步执行
   - `run()` - 普通方法调用，同步执行

2. **线程状态**
   - NEW → RUNNABLE → BLOCKED/WAITING/TIMED_WAITING → TERMINATED

3. **Callable vs Runnable**
   - Callable有返回值，可以抛出异常
   - Runnable无返回值，不能抛出受检异常

4. **线程池优势**
   - 资源复用：避免频繁创建和销毁线程
   - 资源可控：限制线程数量，防止资源耗尽
   - 任务管理：支持任务队列、拒绝策略等

### 常见问题

**Q1: 为什么不能多次调用start()？**
```java
Thread thread = new Thread(() -> {});
thread.start();
thread.start(); // ❌ 抛出IllegalThreadStateException
```

**Q2: 如何正确中断线程？**
```java
Thread thread = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // 执行任务
    }
});
thread.interrupt(); // 设置中断标志
```

**Q3: 线程池什么时候会创建新线程？**
1. 当前线程数 < 核心线程数：创建新线程
2. 核心线程数 ≤ 当前线程数 < 最大线程数 且队列已满：创建新线程
3. 当前线程数 ≥ 最大线程数 且队列已满：执行拒绝策略

## 🔧 实验建议

1. **逐个运行示例**：理解每种方式的特点
2. **修改参数**：观察不同配置的效果
3. **添加日志**：跟踪线程执行过程
4. **性能测试**：对比不同方式的性能差异
5. **异常处理**：测试各种异常情况

## 📝 作业练习

1. 使用四种方式分别实现一个下载任务
2. 实现一个简单的生产者-消费者模型
3. 使用线程池实现批量文件处理
4. 对比直接创建线程和使用线程池的性能差异

---

**作者**: OpenTheDoor  
**更新时间**: 2024-11
