package cn.openthedoor.java.syntax.datatypes;

/**
 * 基本数据类型示例
 * Primitive Data Types Example
 * 
 * 学习目标：
 * 1. 掌握Java 8种基本数据类型
 * 2. 理解类型转换规则（自动转换和强制转换）
 * 3. 注意溢出问题
 * 
 * @author OpenTheDoor
 */
public class PrimitiveTypesDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 基本数据类型演示 ===\n");
        
        // 1. 整型 (Integer Types)
        demonstrateIntegerTypes();
        
        // 2. 浮点型 (Floating-Point Types)
        demonstrateFloatingTypes();
        
        // 3. 字符型 (Character Type)
        demonstrateCharType();
        
        // 4. 布尔型 (Boolean Type)
        demonstrateBooleanType();
        
        // 5. 类型转换 (Type Conversion)
        demonstrateTypeConversion();
        
        // 6. 溢出问题 (Overflow Issue)
        demonstrateOverflow();
    }
    
    /**
     * 整型示例
     * Integer Types Example
     */
    private static void demonstrateIntegerTypes() {
        System.out.println("--- 整型 (Integer Types) ---");
        
        // byte: 1字节，范围 -128 ~ 127
        byte byteVar = 127;
        System.out.println("byte: " + byteVar + " (范围: -128 ~ 127)");
        
        // short: 2字节，范围 -32,768 ~ 32,767
        short shortVar = 32767;
        System.out.println("short: " + shortVar + " (范围: -32,768 ~ 32,767)");
        
        // int: 4字节，范围 -2^31 ~ 2^31-1
        int intVar = 2147483647;
        System.out.println("int: " + intVar + " (范围: -2,147,483,648 ~ 2,147,483,647)");
        
        // long: 8字节，范围 -2^63 ~ 2^63-1
        // 注意：long类型字面量需要加L后缀
        long longVar = 9223372036854775807L;
        System.out.println("long: " + longVar + " (注意：需要加L后缀)");
        
        // 不同进制表示 (Different Numeral Systems)
        int decimal = 100;        // 十进制 (Decimal)
        int binary = 0b1100100;   // 二进制 (Binary) - Java 7+
        int octal = 0144;         // 八进制 (Octal)
        int hex = 0x64;           // 十六进制 (Hexadecimal)
        System.out.println("十进制100 = 二进制" + binary + " = 八进制" + octal + " = 十六进制" + hex);
        
        System.out.println();
    }
    
    /**
     * 浮点型示例
     * Floating-Point Types Example
     */
    private static void demonstrateFloatingTypes() {
        System.out.println("--- 浮点型 (Floating-Point Types) ---");
        
        // float: 4字节，单精度
        // 注意：float类型字面量需要加f或F后缀
        float floatVar = 3.14f;
        System.out.println("float: " + floatVar + " (注意：需要加f后缀)");
        
        // double: 8字节，双精度（默认）
        double doubleVar = 3.141592653589793;
        System.out.println("double: " + doubleVar + " (默认小数类型)");
        
        // 科学计数法 (Scientific Notation)
        double scientificNotation = 1.23e-4; // 1.23 * 10^-4
        System.out.println("科学计数法: " + scientificNotation);
        
        // 特殊值 (Special Values)
        double positiveInfinity = Double.POSITIVE_INFINITY;
        double negativeInfinity = Double.NEGATIVE_INFINITY;
        double nan = Double.NaN; // Not a Number
        System.out.println("正无穷: " + positiveInfinity);
        System.out.println("负无穷: " + negativeInfinity);
        System.out.println("非数字: " + nan);
        
        // ⚠️ 浮点数精度问题 (Precision Issue)
        System.out.println("⚠️ 注意精度问题: 0.1 + 0.2 = " + (0.1 + 0.2)); // 不等于0.3
        
        System.out.println();
    }
    
    /**
     * 字符型示例
     * Character Type Example
     */
    private static void demonstrateCharType() {
        System.out.println("--- 字符型 (Character Type) ---");
        
        // char: 2字节，存储Unicode字符
        char charVar = 'A';
        System.out.println("字符: " + charVar);
        
        // Unicode表示 (Unicode Representation)
        char unicodeChar = '\u0041'; // 也是'A'
        System.out.println("Unicode表示: " + unicodeChar);
        
        // 中文字符 (Chinese Character)
        char chineseChar = '中';
        System.out.println("中文字符: " + chineseChar);
        
        // 转义字符 (Escape Characters)
        System.out.println("转义字符:");
        System.out.println("\t制表符 (Tab)");
        System.out.println("换行符 (Newline)\n第二行");
        System.out.println("单引号: \'");
        System.out.println("双引号: \"");
        System.out.println("反斜杠: \\");
        
        // char本质是整数 (char is essentially an integer)
        char a = 'A';
        int asciiCode = a; // 自动转换为int
        System.out.println("字符 'A' 的ASCII码: " + asciiCode); // 65
        
        // 字符运算 (Character Arithmetic)
        char nextChar = (char) (a + 1); // 'B'
        System.out.println("'A' + 1 = " + nextChar);
        
        System.out.println();
    }
    
    /**
     * 布尔型示例
     * Boolean Type Example
     */
    private static void demonstrateBooleanType() {
        System.out.println("--- 布尔型 (Boolean Type) ---");
        
        // boolean: 只有true和false两个值
        boolean isJavaFun = true;
        boolean isFishMammal = false;
        
        System.out.println("Java好玩吗? " + isJavaFun);
        System.out.println("鱼是哺乳动物吗? " + isFishMammal);
        
        // 逻辑运算 (Logical Operations)
        boolean and = true && false;  // 与 (AND)
        boolean or = true || false;   // 或 (OR)
        boolean not = !true;          // 非 (NOT)
        
        System.out.println("true && false = " + and);
        System.out.println("true || false = " + or);
        System.out.println("!true = " + not);
        
        // ⚠️ 注意：Java中boolean不能转换为数字（与C/C++不同）
        // int num = isJavaFun; // 编译错误！
        
        System.out.println();
    }
    
    /**
     * 类型转换示例
     * Type Conversion Example
     */
    private static void demonstrateTypeConversion() {
        System.out.println("--- 类型转换 (Type Conversion) ---");
        
        // 1. 自动类型转换（小范围 → 大范围）
        // Automatic Type Conversion (Widening)
        System.out.println("自动类型转换（小 → 大）:");
        byte b = 10;
        int i = b;           // byte → int
        long l = i;          // int → long
        float f = l;         // long → float
        double d = f;        // float → double
        System.out.println("byte(10) → double: " + d);
        
        // 2. 强制类型转换（大范围 → 小范围）
        // Explicit Type Casting (Narrowing)
        System.out.println("\n强制类型转换（大 → 小）:");
        double doubleValue = 3.14;
        int intValue = (int) doubleValue; // 小数部分丢失
        System.out.println("double(3.14) → int: " + intValue); // 3
        
        long longValue = 1000L;
        int intValue2 = (int) longValue;
        System.out.println("long(1000) → int: " + intValue2);
        
        // 3. 表达式中的自动提升 (Automatic Promotion in Expressions)
        System.out.println("\n表达式中的类型提升:");
        byte b1 = 10;
        byte b2 = 20;
        // byte b3 = b1 + b2; // 编译错误！b1+b2自动提升为int
        int b3 = b1 + b2; // 正确
        System.out.println("byte + byte = int: " + b3);
        
        // 4. 精度损失示例 (Precision Loss)
        System.out.println("\n⚠️ 精度损失:");
        int largeInt = 123456789;
        float floatValue = largeInt; // 可能损失精度
        int backToInt = (int) floatValue;
        System.out.println("原始int: " + largeInt);
        System.out.println("转float后再转回int: " + backToInt);
        
        System.out.println();
    }
    
    /**
     * 溢出问题示例
     * Overflow Issue Example
     */
    private static void demonstrateOverflow() {
        System.out.println("--- 溢出问题 (Overflow) ---");
        
        // byte溢出 (byte overflow)
        byte maxByte = 127;
        System.out.println("byte最大值: " + maxByte);
        maxByte++;
        System.out.println("byte最大值+1: " + maxByte + " (溢出变成-128)");
        
        // int溢出 (int overflow)
        int maxInt = Integer.MAX_VALUE;
        System.out.println("\nint最大值: " + maxInt);
        System.out.println("int最大值+1: " + (maxInt + 1) + " (溢出变成负数)");
        
        // 安全的运算 (Safe arithmetic - Java 8+)
        try {
            int result = Math.addExact(maxInt, 1); // 溢出时抛出异常
        } catch (ArithmeticException e) {
            System.out.println("检测到溢出: " + e.getMessage());
        }
        
        System.out.println();
    }
    
}

