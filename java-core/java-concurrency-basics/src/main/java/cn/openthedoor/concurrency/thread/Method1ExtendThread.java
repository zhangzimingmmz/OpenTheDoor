package cn.openthedoor.concurrency.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方式一：继承Thread类
 * Method 1: Extend Thread Class
 *
 * 优点：简单直接，易于理解
 * 缺点：Java单继承限制，不够灵活
 *
 * @author OpenTheDoor
 */
public class Method1ExtendThread extends Thread {

    //定义任务类的名称
    public final String taskName;

    //定义任务类的构造方法
    public Method1ExtendThread(String taskName) {
        //构造方法中设置线程名称
        this.setName(taskName);
        //构造方法中设置任务名称
        this.taskName = taskName;
    }

    //重写run方法，定义任务类的执行逻辑
    @Override
    public  void run() {
        System.out.println("线程名称: "+ Thread.currentThread().getName());
        System.out.println("线程Id: "+ Thread.currentThread().getId());
        for (int i = 1; i <5 ; i++) {
            try {
                sleep(100);
                System.out.println(this.taskName +" - 执行进度: "+i+"/5");
            } catch (InterruptedException e) {
                System.out.println(taskName+"被中断");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(this.taskName +" - 任务执行完成");
    }



    public static void main(String[] args) {
        System.out.println("========== 方式一：继承Thread类 演示 ==========");
        System.out.println("创建线程: Thread-Task-1");
        Thread thread1 = new Method1ExtendThread("Thread-Task-1");
        System.out.println("创建线程: Thread-Task-2");
        Thread thread2 = new Method1ExtendThread("Thread-Task-2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
            System.out.println("所有任务执行完成");
        } catch (InterruptedException e) {
            System.out.println("等待线程完成被中断");
            Thread.currentThread().interrupt();
        }
    }
}