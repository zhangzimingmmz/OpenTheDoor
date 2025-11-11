package cn.openthedoor.java.syntax.controlflow;

/**
 * 条件控制语句示例
 * Conditional Statements Example
 * 
 * 学习目标：
 * 1. 掌握if-else语句
 * 2. 掌握switch语句
 * 3. 理解条件表达式的使用
 * 
 * @author OpenTheDoor
 */
public class ConditionalDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 条件控制语句演示 ===\n");
        
        // 1. if语句 (if Statement)
        demonstrateIfStatement();
        
        // 2. if-else语句 (if-else Statement)
        demonstrateIfElseStatement();
        
        // 3. if-else if-else语句 (if-else if-else Statement)
        demonstrateIfElseIfStatement();
        
        // 4. 嵌套if语句 (Nested if Statement)
        demonstrateNestedIf();
        
        // 5. switch语句 (switch Statement)
        demonstrateSwitchStatement();
        
        // 6. switch表达式 (Switch Expression - Java 14+)
        demonstrateSwitchExpression();
    }
    
    /**
     * if语句
     * Single if Statement
     */
    private static void demonstrateIfStatement() {
        System.out.println("--- if 语句 ---");
        
        int age = 18;
        
        // 单行if语句 (Single-line if)
        if (age >= 18) {
            System.out.println("已成年");
        }
        
        // 不推荐：省略大括号（容易出错）
        // Not recommended: Omitting braces
        if (age >= 18)
            System.out.println("可以投票");
        
        System.out.println();
    }
    
    /**
     * if-else语句
     * if-else Statement
     */
    private static void demonstrateIfElseStatement() {
        System.out.println("--- if-else 语句 ---");
        
        int score = 75;
        
        if (score >= 60) {
            System.out.println("及格了！分数: " + score);
        } else {
            System.out.println("不及格。分数: " + score);
        }
        
        // 三元运算符替代简单if-else (Ternary Operator)
        String result = (score >= 60) ? "及格" : "不及格";
        System.out.println("结果: " + result);
        
        System.out.println();
    }
    
    /**
     * if-else if-else语句
     * if-else if-else Statement
     */
    private static void demonstrateIfElseIfStatement() {
        System.out.println("--- if-else if-else 语句 ---");
        
        int score = 85;
        String grade;
        
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        
        System.out.println("分数: " + score + ", 等级: " + grade);
        
        System.out.println();
    }
    
    /**
     * 嵌套if语句
     * Nested if Statement
     */
    private static void demonstrateNestedIf() {
        System.out.println("--- 嵌套 if 语句 ---");
        
        int age = 25;
        boolean hasLicense = true;
        
        // 嵌套if (Nested if)
        if (age >= 18) {
            System.out.println("年龄符合要求");
            
            if (hasLicense) {
                System.out.println("可以驾驶汽车");
            } else {
                System.out.println("需要先考取驾照");
            }
        } else {
            System.out.println("年龄不足，不能驾驶");
        }
        
        // 更好的写法：使用逻辑运算符简化 (Better: Use logical operators)
        System.out.println("\n简化写法:");
        if (age >= 18 && hasLicense) {
            System.out.println("可以驾驶汽车");
        } else if (age >= 18 && !hasLicense) {
            System.out.println("需要先考取驾照");
        } else {
            System.out.println("年龄不足，不能驾驶");
        }
        
        System.out.println();
    }
    
    /**
     * switch语句（传统）
     * Traditional switch Statement
     */
    private static void demonstrateSwitchStatement() {
        System.out.println("--- switch 语句 ---");
        
        // 示例1：整数switch (Integer switch)
        int dayOfWeek = 3;
        String dayName;
        
        switch (dayOfWeek) {
            case 1:
                dayName = "星期一";
                break;
            case 2:
                dayName = "星期二";
                break;
            case 3:
                dayName = "星期三";
                break;
            case 4:
                dayName = "星期四";
                break;
            case 5:
                dayName = "星期五";
                break;
            case 6:
                dayName = "星期六";
                break;
            case 7:
                dayName = "星期日";
                break;
            default:
                dayName = "无效日期";
                break; // default的break可以省略
        }
        
        System.out.println("今天是: " + dayName);
        
        // 示例2：字符串switch (String switch - Java 7+)
        String month = "三月";
        int days;
        
        switch (month) {
            case "一月":
            case "三月":
            case "五月":
            case "七月":
            case "八月":
            case "十月":
            case "十二月":
                days = 31;
                break;
            case "四月":
            case "六月":
            case "九月":
            case "十一月":
                days = 30;
                break;
            case "二月":
                days = 28; // 暂不考虑闰年
                break;
            default:
                days = 0;
                System.out.println("无效月份");
        }
        
        System.out.println(month + " 有 " + days + " 天");
        
        // ⚠️ 注意：忘记break会导致fall-through
        System.out.println("\n⚠️ 注意 fall-through:");
        int number = 2;
        switch (number) {
            case 1:
                System.out.println("Case 1");
                // 忘记break，会继续执行下一个case
            case 2:
                System.out.println("Case 2");
                // 忘记break
            case 3:
                System.out.println("Case 3");
                break;
            default:
                System.out.println("Default");
        }
        
        System.out.println();
    }
    
    /**
     * switch表达式（Java 14+）
     * Switch Expression (Java 14+)
     */
    private static void demonstrateSwitchExpression() {
        System.out.println("--- switch 表达式 (Java 14+) ---");
        
        // 新语法：箭头形式，不需要break
        // New syntax: Arrow form, no break needed
        int dayOfWeek = 3;
        String dayName = switch (dayOfWeek) {
            case 1 -> "星期一";
            case 2 -> "星期二";
            case 3 -> "星期三";
            case 4 -> "星期四";
            case 5 -> "星期五";
            case 6 -> "星期六";
            case 7 -> "星期日";
            default -> "无效日期";
        };
        
        System.out.println("今天是: " + dayName);
        
        // 多个case值 (Multiple case labels)
        String month = "三月";
        int days = switch (month) {
            case "一月", "三月", "五月", "七月", "八月", "十月", "十二月" -> 31;
            case "四月", "六月", "九月", "十一月" -> 30;
            case "二月" -> 28;
            default -> 0;
        };
        
        System.out.println(month + " 有 " + days + " 天");
        
        // 使用代码块 (Using code blocks)
        int score = 85;
        String grade = switch (score / 10) {
            case 10, 9 -> {
                System.out.println("优秀！");
                yield "A"; // yield用于返回值
            }
            case 8 -> {
                System.out.println("良好！");
                yield "B";
            }
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
        
        System.out.println("分数: " + score + ", 等级: " + grade);
        
        System.out.println();
    }
    
}

