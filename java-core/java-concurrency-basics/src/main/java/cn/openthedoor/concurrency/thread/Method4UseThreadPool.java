package cn.openthedoor.concurrency.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 方式四：使用线程池（生产环境推荐）
 * Method 4: Use Thread Pool (Recommended for Production)
 * 
 * 优点：资源可控，性能好，便于管理
 * 缺点：需要理解线程池参数配置
 * 
 * @author OpenTheDoor
 */
public class Method4UseThreadPool {
    
    /**
     * 示例1：使用Executors工厂方法创建线程池
     */
    public static void demoExecutorsFactory() {
        System.out.println("\n--- 示例1：使用Executors工厂方法 ---");
        
        // 1. 固定大小线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        System.out.println("创建固定大小线程池(3个线程)");
        
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            fixedThreadPool.submit(() -> {
                System.out.println("FixedThreadPool - 任务" + taskId + " 执行中，线程: " 
                    + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("FixedThreadPool - 任务" + taskId + " 完成");
            });
        }
        
        fixedThreadPool.shutdown(); // 关闭线程池
        
        try {
            fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("FixedThreadPool 所有任务完成\n");
    }
    
    /**
     * 示例2：使用ThreadPoolExecutor自定义线程池（推荐）
     */
    public static void demoThreadPoolExecutor() {
        System.out.println("\n--- 示例2：使用ThreadPoolExecutor自定义线程池 ---");
        
        // 自定义线程池参数
        int corePoolSize = 2;        // 核心线程数
        int maximumPoolSize = 4;     // 最大线程数
        long keepAliveTime = 60L;    // 空闲线程存活时间
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10); // 任务队列
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 1;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "CustomThread-" + count++);
            }
        };
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy(); // 拒绝策略
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            unit,
            workQueue,
            threadFactory,
            handler
        );
        
        System.out.println("线程池配置:");
        System.out.println("- 核心线程数: " + corePoolSize);
        System.out.println("- 最大线程数: " + maximumPoolSize);
        System.out.println("- 队列容量: 10");
        
        // 提交任务
        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("任务" + taskId + " 开始执行，线程: " 
                    + Thread.currentThread().getName()
                    + "，活动线程数: " + executor.getActiveCount());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务" + taskId + " 完成");
            });
            
            // 打印线程池状态
            System.out.println("提交任务" + taskId + " 后 - 池大小: " + executor.getPoolSize() 
                + ", 队列大小: " + executor.getQueue().size());
        }
        
        executor.shutdown();
        
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("ThreadPoolExecutor 所有任务完成\n");
    }
    
    /**
     * 示例3：提交Callable任务并获取结果
     */
    public static void demoSubmitCallable() {
        System.out.println("\n--- 示例3：提交Callable任务 ---");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();
        
        // 提交多个Callable任务
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Future<Integer> future = executor.submit(() -> {
                System.out.println("Callable任务" + taskId + " 开始计算");
                Thread.sleep(1000);
                int result = taskId * taskId;
                System.out.println("Callable任务" + taskId + " 计算完成，结果: " + result);
                return result;
            });
            futures.add(future);
        }
        
        // 获取所有结果
        System.out.println("\n开始收集结果:");
        int sum = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                Integer result = futures.get(i).get();
                System.out.println("任务" + (i + 1) + " 结果: " + result);
                sum += result;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("所有任务结果总和: " + sum);
        
        executor.shutdown();
        System.out.println("Callable任务演示完成\n");
    }
    
    /**
     * 示例4：使用invokeAll批量执行任务
     */
    public static void demoInvokeAll() {
        System.out.println("\n--- 示例4：使用invokeAll批量执行 ---");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();
        
        // 创建多个Callable任务
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                Thread.sleep(taskId * 500L);
                return "任务" + taskId + "完成";
            });
        }
        
        try {
            // invokeAll会阻塞直到所有任务完成
            System.out.println("开始批量执行任务...");
            List<Future<String>> futures = executor.invokeAll(tasks);
            
            System.out.println("所有任务执行完毕，收集结果:");
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
        System.out.println("invokeAll演示完成\n");
    }
    
    /**
     * 示例5：使用invokeAny获取最快完成的任务结果
     */
    public static void demoInvokeAny() {
        System.out.println("\n--- 示例5：使用invokeAny获取最快结果 ---");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();
        
        // 创建多个Callable任务，执行时间不同
        tasks.add(() -> {
            Thread.sleep(3000);
            return "慢速任务完成";
        });
        tasks.add(() -> {
            Thread.sleep(1000);
            return "快速任务完成";
        });
        tasks.add(() -> {
            Thread.sleep(2000);
            return "中速任务完成";
        });
        
        try {
            // invokeAny返回最快完成的任务结果
            System.out.println("开始竞速执行任务...");
            String result = executor.invokeAny(tasks);
            System.out.println("最快完成的任务结果: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
        System.out.println("invokeAny演示完成\n");
    }
    
    /**
     * 示例6：定时任务线程池
     */
    public static void demoScheduledThreadPool() {
        System.out.println("\n--- 示例6：定时任务线程池 ---");
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        // 1. 延迟执行
        System.out.println("提交延迟任务（2秒后执行）");
        scheduler.schedule(() -> {
            System.out.println("延迟任务执行！时间: " + System.currentTimeMillis());
        }, 2, TimeUnit.SECONDS);
        
        // 2. 固定频率执行
        System.out.println("提交固定频率任务（初始延迟1秒，每隔1秒执行一次）");
        ScheduledFuture<?> future1 = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("固定频率任务执行，时间: " + System.currentTimeMillis());
        }, 1, 1, TimeUnit.SECONDS);
        
        // 3. 固定延迟执行
        System.out.println("提交固定延迟任务（初始延迟1秒，每次执行完延迟1秒后再执行）");
        ScheduledFuture<?> future2 = scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("固定延迟任务执行，时间: " + System.currentTimeMillis());
            try {
                Thread.sleep(500); // 模拟任务执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
        
        // 运行5秒后取消定时任务
        try {
            Thread.sleep(5000);
            future1.cancel(false);
            future2.cancel(false);
            System.out.println("取消定时任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        scheduler.shutdown();
        System.out.println("定时任务演示完成\n");
    }
    
    /**
     * 演示方法
     */
    public static void demo() {
        System.out.println("\n========== 方式四：使用线程池 演示 ==========");
        
        // 执行各个示例
        demoExecutorsFactory();
        demoThreadPoolExecutor();
        demoSubmitCallable();
        demoInvokeAll();
        demoInvokeAny();
        demoScheduledThreadPool();
        
        System.out.println("所有线程池演示完成");
    }
    
    public static void main(String[] args) {
        demo();
    }
}
