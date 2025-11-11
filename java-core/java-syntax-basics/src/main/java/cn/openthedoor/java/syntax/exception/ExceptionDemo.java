package cn.openthedoor.java.syntax.exception;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 异常处理示例
 * Exception Handling Example
 * 
 * 学习目标：
 * 1. 理解异常的分类（Checked vs Unchecked）
 * 2. 掌握try-catch-finally的使用
 * 3. 掌握throw和throws的使用
 * 4. 了解自定义异常
 * 5. 掌握try-with-resources (Java 7+)
 * 
 * @author OpenTheDoor
 */
public class ExceptionDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 异常处理演示 ===\n");
        
        // 1. 异常的分类 (Exception Hierarchy)
        demonstrateExceptionHierarchy();
        
        // 2. try-catch基础 (try-catch Basics)
        demonstrateTryCatch();
        
        // 3. 多个catch块 (Multiple catch Blocks)
        demonstrateMultipleCatch();
        
        // 4. finally块 (finally Block)
        demonstrateFinally();
        
        // 5. throw和throws (throw and throws)
        demonstrateThrowAndThrows();
        
        // 6. try-with-resources (Java 7+)
        demonstrateTryWithResources();
        
        // 7. 自定义异常 (Custom Exception)
        demonstrateCustomException();
        
        // 8. 异常链 (Exception Chaining)
        demonstrateExceptionChaining();
    }
    
    /**
     * 异常的分类
     * Exception Hierarchy
     */
    private static void demonstrateExceptionHierarchy() {
        System.out.println("--- 异常的分类 ---");
        System.out.println("Throwable");
        System.out.println("├── Error (系统错误，不应捕获)");
        System.out.println("│   ├── OutOfMemoryError");
        System.out.println("│   └── StackOverflowError");
        System.out.println("└── Exception");
        System.out.println("    ├── RuntimeException (Unchecked - 运行时异常)");
        System.out.println("    │   ├── NullPointerException");
        System.out.println("    │   ├── IndexOutOfBoundsException");
        System.out.println("    │   ├── ArithmeticException");
        System.out.println("    │   └── ClassCastException");
        System.out.println("    └── 其他Exception (Checked - 编译时异常)");
        System.out.println("        ├── IOException");
        System.out.println("        ├── SQLException");
        System.out.println("        └── ClassNotFoundException");
        System.out.println();
    }
    
    /**
     * try-catch基础
     * try-catch Basics
     */
    private static void demonstrateTryCatch() {
        System.out.println("--- try-catch 基础 ---");
        
        // 示例1：捕获算术异常
        System.out.println("示例1：捕获除零异常");
        try {
            int result = 10 / 0; // ArithmeticException
            System.out.println("结果: " + result);
        } catch (ArithmeticException e) {
            System.out.println("捕获到异常: " + e.getMessage());
            System.out.println("不能除以零！");
        }
        
        // 示例2：捕获空指针异常
        System.out.println("\n示例2：捕获空指针异常");
        try {
            String str = null;
            System.out.println(str.length()); // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("捕获到异常: 字符串为null");
        }
        
        // 示例3：捕获数组越界异常
        System.out.println("\n示例3：捕获数组越界异常");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]); // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕获到异常: 数组下标越界");
        }
        
        System.out.println();
    }
    
    /**
     * 多个catch块
     * Multiple catch Blocks
     */
    private static void demonstrateMultipleCatch() {
        System.out.println("--- 多个 catch 块 ---");
        
        // 方式1：多个catch块
        System.out.println("方式1：多个catch块");
        try {
            String str = "abc";
            int num = Integer.parseInt(str); // NumberFormatException
            int result = 10 / num;
        } catch (NumberFormatException e) {
            System.out.println("数字格式错误: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("算术异常: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("其他异常: " + e.getMessage());
        }
        
        // 方式2：Java 7+ 多重捕获 (Multi-catch)
        System.out.println("\n方式2：多重捕获 (Java 7+)");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("数组异常或空指针: " + e.getMessage());
        }
        
        // ⚠️ 注意：catch块的顺序
        System.out.println("\n⚠️ 注意：子类异常要在父类异常之前捕获");
        try {
            throw new IOException("IO异常");
        } catch (IOException e) { // 子类在前
            System.out.println("IO异常: " + e.getMessage());
        } catch (Exception e) { // 父类在后
            System.out.println("通用异常: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * finally块
     * finally Block
     */
    private static void demonstrateFinally() {
        System.out.println("--- finally 块 ---");
        
        // finally总是会执行
        System.out.println("示例1：正常情况");
        try {
            System.out.println("try块执行");
            int result = 10 / 2;
            System.out.println("结果: " + result);
        } catch (Exception e) {
            System.out.println("catch块执行");
        } finally {
            System.out.println("finally块总是执行");
        }
        
        // 有异常时，finally也会执行
        System.out.println("\n示例2：有异常");
        try {
            System.out.println("try块执行");
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("catch块执行");
        } finally {
            System.out.println("finally块仍然执行");
        }
        
        // finally的典型用途：释放资源
        System.out.println("\n示例3：释放资源");
        FileReader reader = null;
        try {
            // reader = new FileReader("test.txt");
            System.out.println("尝试打开文件");
        } catch (Exception e) {
            System.out.println("文件操作异常");
        } finally {
            // 关闭资源
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("资源已关闭");
                } catch (IOException e) {
                    System.out.println("关闭资源失败");
                }
            }
            System.out.println("finally块确保资源释放");
        }
        
        System.out.println();
    }
    
    /**
     * throw和throws
     * throw and throws Keywords
     */
    private static void demonstrateThrowAndThrows() {
        System.out.println("--- throw 和 throws ---");
        
        // throw：主动抛出异常
        System.out.println("throw - 主动抛出异常:");
        try {
            checkAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("捕获到异常: " + e.getMessage());
        }
        
        // throws：声明方法可能抛出的异常
        System.out.println("\nthrows - 声明方法可能抛出的异常:");
        try {
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("捕获到IO异常: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * 检查年龄（演示throw）
     * Check age (demonstrate throw)
     */
    private static void checkAge(int age) {
        if (age < 0) {
            // 主动抛出异常
            throw new IllegalArgumentException("年龄不能为负数: " + age);
        }
        System.out.println("年龄有效: " + age);
    }
    
    /**
     * 读取文件（演示throws）
     * Read file (demonstrate throws)
     */
    private static void readFile(String filename) throws IOException {
        // 方法签名中声明可能抛出IOException
        // Method signature declares that IOException may be thrown
        System.out.println("尝试读取文件: " + filename);
        throw new FileNotFoundException("文件不存在: " + filename);
    }
    
    /**
     * try-with-resources（Java 7+）
     * try-with-resources (Java 7+)
     */
    private static void demonstrateTryWithResources() {
        System.out.println("--- try-with-resources (Java 7+) ---");
        
        // 传统方式：手动关闭资源
        System.out.println("传统方式 - 手动关闭:");
        FileReader reader1 = null;
        try {
            // reader1 = new FileReader("test.txt");
            System.out.println("读取文件...");
        } catch (Exception e) {
            System.out.println("异常: " + e.getMessage());
        } finally {
            // 必须手动关闭
            if (reader1 != null) {
                try {
                    reader1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // try-with-resources：自动关闭资源
        System.out.println("\ntry-with-resources - 自动关闭:");
        try (/* FileReader reader2 = new FileReader("test.txt") */) {
            System.out.println("资源会自动关闭");
        } catch (Exception e) {
            System.out.println("演示：" + e.getMessage());
        }
        // 资源自动关闭，无需finally
        
        System.out.println();
    }
    
    /**
     * 自定义异常
     * Custom Exception
     */
    private static void demonstrateCustomException() {
        System.out.println("--- 自定义异常 ---");
        
        try {
            withdraw(1000, 1500); // 余额不足
        } catch (InsufficientFundsException e) {
            System.out.println("异常: " + e.getMessage());
            System.out.println("缺少金额: " + e.getShortfall());
        }
        
        System.out.println();
    }
    
    /**
     * 取款方法（演示自定义异常）
     * Withdraw method (demonstrate custom exception)
     */
    private static void withdraw(double balance, double amount) 
            throws InsufficientFundsException {
        if (amount > balance) {
            double shortfall = amount - balance;
            throw new InsufficientFundsException("余额不足", shortfall);
        }
        System.out.println("取款成功: " + amount);
    }
    
    /**
     * 异常链
     * Exception Chaining
     */
    private static void demonstrateExceptionChaining() {
        System.out.println("--- 异常链 ---");
        
        try {
            processData();
        } catch (Exception e) {
            System.out.println("最终异常: " + e.getMessage());
            System.out.println("原始异常: " + e.getCause().getMessage());
            
            // 打印完整异常链
            System.out.println("\n异常栈:");
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * 处理数据（演示异常链）
     * Process data (demonstrate exception chaining)
     */
    private static void processData() throws Exception {
        try {
            parseData();
        } catch (NumberFormatException e) {
            // 将原始异常包装成新异常，保留异常链
            throw new Exception("数据处理失败", e);
        }
    }
    
    /**
     * 解析数据
     * Parse data
     */
    private static void parseData() {
        // 模拟解析错误
        Integer.parseInt("abc");
    }
    
}

/**
 * 自定义异常：余额不足异常
 * Custom Exception: Insufficient Funds Exception
 */
class InsufficientFundsException extends Exception {
    
    private double shortfall; // 缺少的金额
    
    public InsufficientFundsException(String message, double shortfall) {
        super(message);
        this.shortfall = shortfall;
    }
    
    public double getShortfall() {
        return shortfall;
    }
}

