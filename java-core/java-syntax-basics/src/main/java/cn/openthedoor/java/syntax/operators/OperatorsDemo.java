package cn.openthedoor.java.syntax.operators;

/**
 * 运算符示例
 * Operators Example
 * 
 * 学习目标：
 * 1. 掌握算术运算符
 * 2. 掌握关系运算符
 * 3. 掌握逻辑运算符
 * 4. 掌握位运算符
 * 5. 理解运算符优先级
 * 
 * @author OpenTheDoor
 */
public class OperatorsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 运算符演示 ===\n");
        
        // 1. 算术运算符 (Arithmetic Operators)
        demonstrateArithmeticOperators();
        
        // 2. 关系运算符 (Relational Operators)
        demonstrateRelationalOperators();
        
        // 3. 逻辑运算符 (Logical Operators)
        demonstrateLogicalOperators();
        
        // 4. 位运算符 (Bitwise Operators)
        demonstrateBitwiseOperators();
        
        // 5. 赋值运算符 (Assignment Operators)
        demonstrateAssignmentOperators();
        
        // 6. 其他运算符 (Other Operators)
        demonstrateOtherOperators();
        
        // 7. 运算符优先级 (Operator Precedence)
        demonstrateOperatorPrecedence();
    }
    
    /**
     * 算术运算符
     * Arithmetic Operators: +, -, *, /, %, ++, --
     */
    private static void demonstrateArithmeticOperators() {
        System.out.println("--- 算术运算符 (Arithmetic Operators) ---");
        
        int a = 10;
        int b = 3;
        
        // 基本运算 (Basic Operations)
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("加法 (+): a + b = " + (a + b));
        System.out.println("减法 (-): a - b = " + (a - b));
        System.out.println("乘法 (*): a * b = " + (a * b));
        System.out.println("除法 (/): a / b = " + (a / b));    // 整数除法，结果为3
        System.out.println("取模 (%): a % b = " + (a % b));    // 余数为1
        
        // 浮点除法 (Floating-Point Division)
        double da = 10.0;
        double db = 3.0;
        System.out.println("\n浮点除法: " + da + " / " + db + " = " + (da / db));
        
        // 自增自减 (Increment and Decrement)
        System.out.println("\n自增自减:");
        int x = 5;
        System.out.println("x = " + x);
        System.out.println("x++ (后缀): " + (x++)); // 先使用，后自增，输出5
        System.out.println("x = " + x);              // x现在是6
        System.out.println("++x (前缀): " + (++x)); // 先自增，后使用，输出7
        System.out.println("x = " + x);              // x现在是7
        
        int y = 5;
        System.out.println("\ny = " + y);
        System.out.println("y-- (后缀): " + (y--)); // 先使用，后自减，输出5
        System.out.println("y = " + y);              // y现在是4
        System.out.println("--y (前缀): " + (--y)); // 先自减，后使用，输出3
        System.out.println("y = " + y);              // y现在是3
        
        System.out.println();
    }
    
    /**
     * 关系运算符
     * Relational Operators: ==, !=, >, <, >=, <=
     */
    private static void demonstrateRelationalOperators() {
        System.out.println("--- 关系运算符 (Relational Operators) ---");
        
        int a = 10;
        int b = 20;
        
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("a == b: " + (a == b)); // 等于
        System.out.println("a != b: " + (a != b)); // 不等于
        System.out.println("a > b:  " + (a > b));  // 大于
        System.out.println("a < b:  " + (a < b));  // 小于
        System.out.println("a >= b: " + (a >= b)); // 大于等于
        System.out.println("a <= b: " + (a <= b)); // 小于等于
        
        // ⚠️ 字符串比较 (String Comparison)
        System.out.println("\n⚠️ 字符串比较:");
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");
        
        System.out.println("s1 == s2: " + (s1 == s2));         // true (字符串常量池)
        System.out.println("s1 == s3: " + (s1 == s3));         // false (不同对象)
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true (内容相同)
        System.out.println("建议：字符串比较使用 equals()");
        
        System.out.println();
    }
    
    /**
     * 逻辑运算符
     * Logical Operators: &&, ||, !
     */
    private static void demonstrateLogicalOperators() {
        System.out.println("--- 逻辑运算符 (Logical Operators) ---");
        
        boolean a = true;
        boolean b = false;
        
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("逻辑与 (&&): a && b = " + (a && b)); // AND
        System.out.println("逻辑或 (||): a || b = " + (a || b)); // OR
        System.out.println("逻辑非 (!):  !a = " + (!a));         // NOT
        
        // 短路运算 (Short-Circuit Evaluation)
        System.out.println("\n短路运算:");
        int x = 10;
        int y = 0;
        
        // && 短路：第一个为false，不会执行第二个
        boolean result1 = (y != 0) && (x / y > 0); // 不会执行x/y，避免除零
        System.out.println("(y != 0) && (x / y > 0) = " + result1);
        
        // || 短路：第一个为true，不会执行第二个
        boolean result2 = (x > 0) || (x / y > 0); // 不会执行x/y
        System.out.println("(x > 0) || (x / y > 0) = " + result2);
        
        // 非短路运算 (Non-Short-Circuit): & 和 |
        System.out.println("\n非短路运算 (& 和 |):");
        boolean result3 = (x > 0) & (y > 0); // 两边都会执行
        System.out.println("(x > 0) & (y > 0) = " + result3);
        
        System.out.println();
    }
    
    /**
     * 位运算符
     * Bitwise Operators: &, |, ^, ~, <<, >>, >>>
     */
    private static void demonstrateBitwiseOperators() {
        System.out.println("--- 位运算符 (Bitwise Operators) ---");
        
        int a = 60;  // 0011 1100
        int b = 13;  // 0000 1101
        
        System.out.println("a = " + a + " (二进制: " + Integer.toBinaryString(a) + ")");
        System.out.println("b = " + b + " (二进制: " + Integer.toBinaryString(b) + ")");
        
        // 按位与 (AND)
        int and = a & b; // 0000 1100 = 12
        System.out.println("\n按位与 (&): " + and + " (二进制: " + Integer.toBinaryString(and) + ")");
        
        // 按位或 (OR)
        int or = a | b; // 0011 1101 = 61
        System.out.println("按位或 (|): " + or + " (二进制: " + Integer.toBinaryString(or) + ")");
        
        // 按位异或 (XOR)
        int xor = a ^ b; // 0011 0001 = 49
        System.out.println("按位异或 (^): " + xor + " (二进制: " + Integer.toBinaryString(xor) + ")");
        
        // 按位取反 (NOT)
        int not = ~a; // 补码
        System.out.println("按位取反 (~): " + not);
        
        // 左移 (Left Shift)
        int leftShift = a << 2; // 左移2位，相当于乘以4
        System.out.println("\n左移 (<<): " + a + " << 2 = " + leftShift + " (相当于 * 4)");
        
        // 右移 (Right Shift)
        int rightShift = a >> 2; // 右移2位，相当于除以4
        System.out.println("右移 (>>): " + a + " >> 2 = " + rightShift + " (相当于 / 4)");
        
        // 无符号右移 (Unsigned Right Shift)
        int negativeNum = -8;
        int unsignedRightShift = negativeNum >>> 2;
        System.out.println("\n无符号右移 (>>>): " + negativeNum + " >>> 2 = " + unsignedRightShift);
        
        // 位运算的实际应用 (Practical Uses)
        System.out.println("\n实际应用:");
        System.out.println("判断奇偶: " + a + " & 1 = " + (a & 1) + " (0为偶数，1为奇数)");
        System.out.println("交换两数 (无需临时变量):");
        int x = 5, y = 10;
        System.out.println("  交换前: x=" + x + ", y=" + y);
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("  交换后: x=" + x + ", y=" + y);
        
        System.out.println();
    }
    
    /**
     * 赋值运算符
     * Assignment Operators: =, +=, -=, *=, /=, %=, &=, |=, ^=, <<=, >>=, >>>=
     */
    private static void demonstrateAssignmentOperators() {
        System.out.println("--- 赋值运算符 (Assignment Operators) ---");
        
        int a = 10;
        System.out.println("初始值: a = " + a);
        
        a += 5; // a = a + 5
        System.out.println("a += 5:  a = " + a);
        
        a -= 3; // a = a - 3
        System.out.println("a -= 3:  a = " + a);
        
        a *= 2; // a = a * 2
        System.out.println("a *= 2:  a = " + a);
        
        a /= 4; // a = a / 4
        System.out.println("a /= 4:  a = " + a);
        
        a %= 3; // a = a % 3
        System.out.println("a %= 3:  a = " + a);
        
        // 位运算赋值 (Bitwise Assignment)
        int b = 12; // 1100
        b &= 10;    // 1010 → 1000 = 8
        System.out.println("\n位运算赋值: b &= 10, b = " + b);
        
        System.out.println();
    }
    
    /**
     * 其他运算符
     * Other Operators: ?: (ternary), instanceof
     */
    private static void demonstrateOtherOperators() {
        System.out.println("--- 其他运算符 (Other Operators) ---");
        
        // 1. 三元运算符 (Ternary Operator)
        System.out.println("三元运算符 (? :):");
        int a = 10;
        int b = 20;
        int max = (a > b) ? a : b; // 如果a > b，返回a，否则返回b
        System.out.println("max(" + a + ", " + b + ") = " + max);
        
        // 嵌套三元运算符 (Nested Ternary)
        int x = 5;
        String type = (x > 0) ? "正数" : (x < 0) ? "负数" : "零";
        System.out.println(x + " 是 " + type);
        
        // 2. instanceof运算符 (instanceof Operator)
        System.out.println("\ninstanceof 运算符:");
        String str = "Hello";
        Object obj = str;
        
        System.out.println("str instanceof String: " + (str instanceof String));
        System.out.println("str instanceof Object: " + (str instanceof Object));
        System.out.println("obj instanceof String: " + (obj instanceof String));
        
        System.out.println();
    }
    
    /**
     * 运算符优先级
     * Operator Precedence
     */
    private static void demonstrateOperatorPrecedence() {
        System.out.println("--- 运算符优先级 (Operator Precedence) ---");
        
        // 示例1：算术 vs 关系
        int result1 = 2 + 3 * 4; // * 优先于 +
        System.out.println("2 + 3 * 4 = " + result1 + " (* 优先于 +)");
        
        // 示例2：关系 vs 逻辑
        boolean result2 = 5 > 3 && 10 < 20; // > < 优先于 &&
        System.out.println("5 > 3 && 10 < 20 = " + result2);
        
        // 示例3：复杂表达式
        int a = 10, b = 20, c = 30;
        boolean result3 = a + b > c && c - b < a; // 算术 > 关系 > 逻辑
        System.out.println("a + b > c && c - b < a = " + result3);
        
        // 建议：使用括号提高可读性
        System.out.println("\n建议：使用括号提高可读性");
        boolean result4 = ((a + b) > c) && ((c - b) < a);
        System.out.println("((a + b) > c) && ((c - b) < a) = " + result4);
        
        // 优先级顺序（从高到低）
        System.out.println("\n运算符优先级（部分，从高到低）:");
        System.out.println("1. () [] .              - 括号、数组、成员访问");
        System.out.println("2. ++ -- ! ~            - 一元运算符");
        System.out.println("3. * / %                - 乘除取模");
        System.out.println("4. + -                  - 加减");
        System.out.println("5. << >> >>>            - 位移");
        System.out.println("6. < > <= >= instanceof - 关系");
        System.out.println("7. == !=                - 相等");
        System.out.println("8. &                    - 按位与");
        System.out.println("9. ^                    - 按位异或");
        System.out.println("10. |                   - 按位或");
        System.out.println("11. &&                  - 逻辑与");
        System.out.println("12. ||                  - 逻辑或");
        System.out.println("13. ?:                  - 三元");
        System.out.println("14. = += -= *= /= ...   - 赋值");
        
        System.out.println();
    }
    
}

