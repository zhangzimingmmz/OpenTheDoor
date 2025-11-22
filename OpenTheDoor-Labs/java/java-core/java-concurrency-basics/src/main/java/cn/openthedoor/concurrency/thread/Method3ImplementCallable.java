package cn.openthedoor.concurrency.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 方式三：实现Callable接口
 * Method 3: Implement Callable Interface
 * 
 * 优点：有返回值，可以抛出异常
 * 缺点：使用相对复杂，需要配合FutureTask使用
 * 
 * @author OpenTheDoor
 */
public class Method3ImplementCallable implements Callable<String> {
    
    private String taskName;
    private int computeTime; // 计算时间（秒）
    
    public Method3ImplementCallable(String taskName, int computeTime) {
        this.taskName = taskName;
        this.computeTime = computeTime;
    }
    
    @Override
    public String call() throws Exception {
        System.out.println("=== 方式三：实现Callable接口 ===");
        System.out.println("线程名称: " + Thread.currentThread().getName());
        System.out.println("线程ID: " + Thread.currentThread().getId());
        System.out.println("任务名称: " + taskName);
        System.out.println("预计耗时: " + computeTime + "秒");
        
        // 模拟计算过程
        int result = 0;
        for (int i = 1; i <= computeTime; i++) {
            System.out.println(taskName + " - 计算中: " + i + "/" + computeTime + "秒");
            Thread.sleep(1000); // 模拟耗时计算
            result += i * 10;
            
            // 模拟可能抛出的异常
            if (i == 3 && taskName.contains("Exception")) {
                throw new RuntimeException("计算过程中发生异常！");
            }
        }
        
        String resultMessage = taskName + " 计算完成，结果: " + result;
        System.out.println(resultMessage + "\n");
        return resultMessage;
    }
    
    /**
     * 演示方法
     */
    public static void demo() {
        System.out.println("\n========== 方式三：实现Callable接口 演示 ==========");
        
        // 示例1：基本使用
        System.out.println("\n--- 示例1：基本使用 ---");
        Method3ImplementCallable task1 = new Method3ImplementCallable("Callable-Task-1", 3);
        FutureTask<String> futureTask1 = new FutureTask<>(task1);
        Thread thread1 = new Thread(futureTask1, "Thread-1");
        thread1.start();
        
        try {
            // 获取返回值（会阻塞直到任务完成）
            String result1 = futureTask1.get();
            System.out.println("主线程获取到结果: " + result1);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("获取结果时发生异常: " + e.getMessage());
        }
        
        // 示例2：使用Lambda表达式
        System.out.println("\n--- 示例2：使用Lambda表达式 ---");
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println("Lambda Callable 执行中...");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            System.out.println("Lambda 计算完成");
            return sum;
        });
        
        Thread thread2 = new Thread(futureTask2, "Thread-2");
        thread2.start();
        
        try {
            // 带超时的获取结果
            Integer result2 = futureTask2.get(3, TimeUnit.SECONDS);
            System.out.println("Lambda计算结果 (1+2+...+100): " + result2 + "\n");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.err.println("获取结果超时或异常: " + e.getMessage());
        }
        
        // 示例3：检查任务状态
        System.out.println("\n--- 示例3：检查任务状态 ---");
        Method3ImplementCallable task3 = new Method3ImplementCallable("Callable-Task-3", 2);
        FutureTask<String> futureTask3 = new FutureTask<>(task3);
        Thread thread3 = new Thread(futureTask3, "Thread-3");
        
        System.out.println("任务启动前 - isDone: " + futureTask3.isDone());
        System.out.println("任务启动前 - isCancelled: " + futureTask3.isCancelled());
        
        thread3.start();
        
        try {
            Thread.sleep(500); // 等待一会儿
            System.out.println("任务执行中 - isDone: " + futureTask3.isDone());
            
            String result3 = futureTask3.get();
            System.out.println("任务完成后 - isDone: " + futureTask3.isDone());
            System.out.println("最终结果: " + result3);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        // 示例4：取消任务
        System.out.println("\n--- 示例4：取消任务 ---");
        Method3ImplementCallable task4 = new Method3ImplementCallable("Callable-Task-4", 5);
        FutureTask<String> futureTask4 = new FutureTask<>(task4);
        Thread thread4 = new Thread(futureTask4, "Thread-4");
        thread4.start();
        
        try {
            Thread.sleep(1500); // 让任务执行一会儿
            boolean cancelled = futureTask4.cancel(true); // 尝试取消任务
            System.out.println("任务取消结果: " + cancelled);
            System.out.println("任务是否被取消: " + futureTask4.isCancelled());
            
            // 尝试获取结果（会抛出CancellationException）
            // futureTask4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 示例5：异常处理
        System.out.println("\n--- 示例5：异常处理 ---");
        Method3ImplementCallable task5 = new Method3ImplementCallable("Callable-Exception-Task", 5);
        FutureTask<String> futureTask5 = new FutureTask<>(task5);
        Thread thread5 = new Thread(futureTask5, "Thread-5");
        thread5.start();
        
        try {
            String result5 = futureTask5.get();
            System.out.println("结果: " + result5);
        } catch (InterruptedException e) {
            System.err.println("线程被中断: " + e.getMessage());
        } catch (ExecutionException e) {
            System.err.println("任务执行过程中抛出异常: " + e.getCause().getMessage());
        }
        
        System.out.println("\n所有Callable演示完成");
    }
    
    public static void main(String[] args) {
        demo();
    }
}
