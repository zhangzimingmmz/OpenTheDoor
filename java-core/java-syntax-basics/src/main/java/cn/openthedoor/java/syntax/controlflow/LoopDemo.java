package cn.openthedoor.java.syntax.controlflow;

/**
 * 循环控制语句示例
 * Loop Statements Example
 * 
 * 学习目标：
 * 1. 掌握for循环
 * 2. 掌握while循环
 * 3. 掌握do-while循环
 * 4. 理解break和continue的使用
 * 
 * @author OpenTheDoor
 */
public class LoopDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 循环控制语句演示 ===\n");
        
        // 1. for循环 (for Loop)
        demonstrateForLoop();
        
        // 2. 增强for循环 (Enhanced for Loop - for-each)
        demonstrateForEachLoop();
        
        // 3. while循环 (while Loop)
        demonstrateWhileLoop();
        
        // 4. do-while循环 (do-while Loop)
        demonstrateDoWhileLoop();
        
        // 5. 嵌套循环 (Nested Loops)
        demonstrateNestedLoops();
        
        // 6. break和continue (break and continue)
        demonstrateBreakContinue();
        
        // 7. 标签 (Labels)
        demonstrateLabels();
    }
    
    /**
     * for循环
     * for Loop
     */
    private static void demonstrateForLoop() {
        System.out.println("--- for 循环 ---");
        
        // 基本for循环 (Basic for loop)
        System.out.println("打印1到5:");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // 步长为2 (Step of 2)
        System.out.println("\n偶数1到10:");
        for (int i = 2; i <= 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // 倒序 (Reverse order)
        System.out.println("\n倒序5到1:");
        for (int i = 5; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // 多个变量 (Multiple variables)
        System.out.println("\n多个变量:");
        for (int i = 0, j = 10; i < 5; i++, j--) {
            System.out.println("i = " + i + ", j = " + j);
        }
        
        // 无限循环（需要break跳出）
        // Infinite loop (需要break跳出)
        // for (;;) {
        //     // 无限循环
        // }
        
        System.out.println();
    }
    
    /**
     * 增强for循环（for-each）
     * Enhanced for Loop (for-each)
     */
    private static void demonstrateForEachLoop() {
        System.out.println("--- 增强 for 循环 (for-each) ---");
        
        // 遍历数组 (Iterate over array)
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println("遍历数组:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // 遍历字符串数组 (Iterate over String array)
        String[] fruits = {"苹果", "香蕉", "橙子"};
        System.out.println("\n遍历字符串数组:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
        
        // ⚠️ 注意：for-each不能修改数组元素
        System.out.println("\n⚠️ for-each不能修改数组元素:");
        int[] arr = {1, 2, 3};
        for (int num : arr) {
            num = num * 2; // 不会改变数组
        }
        System.out.println("原数组: [" + arr[0] + ", " + arr[1] + ", " + arr[2] + "]");
        
        System.out.println();
    }
    
    /**
     * while循环
     * while Loop
     */
    private static void demonstrateWhileLoop() {
        System.out.println("--- while 循环 ---");
        
        // 基本while循环 (Basic while loop)
        System.out.println("打印1到5:");
        int i = 1;
        while (i <= 5) {
            System.out.print(i + " ");
            i++;
        }
        System.out.println();
        
        // 求和示例 (Sum example)
        System.out.println("\n计算1到10的和:");
        int sum = 0;
        int n = 1;
        while (n <= 10) {
            sum += n;
            n++;
        }
        System.out.println("和 = " + sum);
        
        // 用户输入验证示例（模拟）
        // User input validation example (simulated)
        System.out.println("\n密码验证示例:");
        String correctPassword = "123456";
        String inputPassword = "wrong";
        int attempts = 0;
        
        while (!inputPassword.equals(correctPassword) && attempts < 3) {
            attempts++;
            System.out.println("密码错误，第 " + attempts + " 次尝试");
            if (attempts == 2) {
                inputPassword = "123456"; // 模拟正确输入
            }
        }
        
        if (inputPassword.equals(correctPassword)) {
            System.out.println("登录成功！");
        } else {
            System.out.println("尝试次数过多，账户锁定");
        }
        
        System.out.println();
    }
    
    /**
     * do-while循环
     * do-while Loop
     */
    private static void demonstrateDoWhileLoop() {
        System.out.println("--- do-while 循环 ---");
        
        // 基本do-while循环 (Basic do-while loop)
        System.out.println("打印1到5:");
        int i = 1;
        do {
            System.out.print(i + " ");
            i++;
        } while (i <= 5);
        System.out.println();
        
        // while vs do-while 的区别
        // Difference between while and do-while
        System.out.println("\nwhile vs do-while 的区别:");
        
        // while: 先判断后执行，可能一次都不执行
        int j = 10;
        System.out.println("while (j < 5):");
        while (j < 5) {
            System.out.println("执行了"); // 不会执行
        }
        System.out.println("没有输出");
        
        // do-while: 先执行后判断，至少执行一次
        int k = 10;
        System.out.println("\ndo-while (k < 5):");
        do {
            System.out.println("至少执行一次"); // 会执行一次
        } while (k < 5);
        
        // 实际应用：菜单系统
        System.out.println("\n菜单系统示例:");
        int choice;
        int count = 0;
        do {
            System.out.println("--- 菜单 ---");
            System.out.println("1. 选项1");
            System.out.println("2. 选项2");
            System.out.println("0. 退出");
            
            // 模拟用户选择
            choice = (count == 0) ? 1 : 0;
            count++;
            
            System.out.println("您选择了: " + choice);
            
            if (choice == 1) {
                System.out.println("执行选项1");
            } else if (choice == 2) {
                System.out.println("执行选项2");
            }
            
        } while (choice != 0);
        System.out.println("退出程序");
        
        System.out.println();
    }
    
    /**
     * 嵌套循环
     * Nested Loops
     */
    private static void demonstrateNestedLoops() {
        System.out.println("--- 嵌套循环 ---");
        
        // 九九乘法表 (Multiplication table)
        System.out.println("九九乘法表:");
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "×" + i + "=" + (i * j) + "\t");
            }
            System.out.println();
        }
        
        // 打印星号三角形 (Print star triangle)
        System.out.println("\n星号三角形:");
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        
        // 打印矩形 (Print rectangle)
        System.out.println("\n矩形:");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 6; j++) {
                System.out.print("# ");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * break和continue
     * break and continue Statements
     */
    private static void demonstrateBreakContinue() {
        System.out.println("--- break 和 continue ---");
        
        // break: 跳出循环 (Exit loop)
        System.out.println("break示例 - 找到5就停止:");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                break; // 跳出循环
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // continue: 跳过本次循环 (Skip current iteration)
        System.out.println("\ncontinue示例 - 跳过偶数:");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // 跳过偶数
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // 实际应用：查找质数
        System.out.println("\n查找100以内的质数:");
        int count = 0;
        for (int i = 2; i <= 100; i++) {
            boolean isPrime = true;
            
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break; // 找到因数，不是质数，跳出
                }
            }
            
            if (isPrime) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) {
                    System.out.println(); // 每10个换行
                }
            }
        }
        System.out.println();
        
        System.out.println();
    }
    
    /**
     * 标签（用于多层循环的break和continue）
     * Labels (for break and continue in nested loops)
     */
    private static void demonstrateLabels() {
        System.out.println("--- 标签 (Labels) ---");
        
        // 不使用标签：只能跳出内层循环
        System.out.println("不使用标签 - 只跳出内层循环:");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    break; // 只跳出内层循环
                }
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
        
        // 使用标签：跳出外层循环
        System.out.println("\n使用标签 - 跳出外层循环:");
        outer: // 外层循环标签
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    break outer; // 跳出到outer标签的循环
                }
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
        System.out.println("已跳出外层循环");
        
        // 标签与continue
        System.out.println("\n标签与continue:");
        outer2:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j == 2) {
                    continue outer2; // 继续外层循环的下一次迭代
                }
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
}

