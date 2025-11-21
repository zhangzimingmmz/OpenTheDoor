package cn.openthedoor.concurrency.thread;

/**
 * 方式二：实现 Runnable 接口（推荐）
 * Method 2: Implement Runnable Interface (Recommended)
 *
 * 优点：可以继承其他类；任务与线程分离；线程可复用同一任务对象
 * 缺点：run() 无返回值；不能直接抛出受检异常
 */
public class Method2ImplementRunnable implements Runnable {

    private final String taskName;
    private final int taskCount;

    public Method2ImplementRunnable(String taskName, int taskCount) {
        this.taskName = taskName;
        this.taskCount = taskCount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] 开始执行任务: " + taskName);

        for (int i = 1; i <= taskCount; i++) {
            System.out.println("[" + threadName + "] " + taskName + " - 进度: " + i + "/" + taskCount);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println("[" + threadName + "] " + taskName + " 被中断");
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("[" + threadName + "] " + taskName + " 执行完成\n");
    }

    /**
     * 演示不同创建 Runnable 线程的方式
     */
    public static void demo() {
        System.out.println("\n========== 方式二：实现Runnable接口 演示 ==========");

        // 1. 普通方式（最主流）
        Thread t1 = new Thread(new Method2ImplementRunnable("任务A", 3), "线程-A");
        Thread t2 = new Thread(new Method2ImplementRunnable("任务B", 3), "线程-B");

        // 2. 匿名内部类
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println("[" + name + "] 匿名任务执行");
                for (int i = 1; i <= 3; i++) {
                    System.out.println("[" + name + "] 匿名任务 进度: " + i + "/3");
                    sleepSilently(300);
                }
            }
        }, "线程-匿名");

        // 3. Lambda 表达式
        Thread t4 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("[" + name + "] Lambda任务执行");
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + name + "] Lambda任务 进度: " + i + "/3");
                sleepSilently(300);
            }
        }, "线程-Lambda");

        // 4. 多线程复用同一个 Runnable（展示共享任务原理）
        // ⚠ 仅适用于无共享可变状态的任务
        Method2ImplementRunnable shared = new Method2ImplementRunnable("共享任务", 2);
        Thread t5 = new Thread(shared, "线程-S1");
        Thread t6 = new Thread(shared, "线程-S2");

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start(); t6.start();

        try {
            t1.join(); t2.join(); t3.join(); t4.join(); t5.join(); t6.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("所有线程执行完毕\n");
    }

    // 工具方法（避免重复写 try/catch）
    private static void sleepSilently(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        demo();
    }
}