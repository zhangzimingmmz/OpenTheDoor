package cn.openthedoor.java.syntax.datatypes;

/**
 * 包装类示例
 * Wrapper Class Example
 * 
 * 学习目标：
 * 1. 理解包装类的作用
 * 2. 掌握自动装箱和拆箱
 * 3. 了解包装类的常用方法
 * 4. 注意缓存机制
 * 
 * @author OpenTheDoor
 */
public class WrapperClassDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 包装类演示 ===\n");
        
        // 1. 包装类基础 (Wrapper Class Basics)
        demonstrateWrapperBasics();
        
        // 2. 自动装箱和拆箱 (Autoboxing and Unboxing)
        demonstrateAutoboxing();
        
        // 3. 包装类常用方法 (Common Methods)
        demonstrateCommonMethods();
        
        // 4. 缓存机制 (Cache Mechanism)
        demonstrateCacheMechanism();
        
        // 5. 注意事项 (Important Notes)
        demonstrateImportantNotes();
    }
    
    /**
     * 包装类基础
     * Wrapper Class Basics
     */
    private static void demonstrateWrapperBasics() {
        System.out.println("--- 包装类基础 ---");
        
        // 基本类型 vs 包装类
        // Primitive Type vs Wrapper Class
        System.out.println("基本类型 → 包装类:");
        System.out.println("byte    → Byte");
        System.out.println("short   → Short");
        System.out.println("int     → Integer");
        System.out.println("long    → Long");
        System.out.println("float   → Float");
        System.out.println("double  → Double");
        System.out.println("char    → Character");
        System.out.println("boolean → Boolean");
        
        // 创建包装类对象 (Creating Wrapper Objects)
        Integer intObj1 = new Integer(100);           // 已过时 (Deprecated)
        Integer intObj2 = Integer.valueOf(100);       // 推荐方式
        Integer intObj3 = 100;                        // 自动装箱 (Autoboxing)
        
        System.out.println("\n创建Integer对象: " + intObj3);
        
        System.out.println();
    }
    
    /**
     * 自动装箱和拆箱
     * Autoboxing and Unboxing
     */
    private static void demonstrateAutoboxing() {
        System.out.println("--- 自动装箱和拆箱 ---");
        
        // 1. 自动装箱 (Autoboxing) - 基本类型 → 包装类
        int primitiveInt = 100;
        Integer wrapperInt = primitiveInt; // 自动装箱
        System.out.println("自动装箱: int → Integer");
        
        // 2. 自动拆箱 (Unboxing) - 包装类 → 基本类型
        Integer anotherWrapper = 200;
        int anotherPrimitive = anotherWrapper; // 自动拆箱
        System.out.println("自动拆箱: Integer → int");
        
        // 3. 运算中的自动装箱/拆箱
        Integer a = 10;
        Integer b = 20;
        Integer c = a + b; // 自动拆箱相加，然后自动装箱
        System.out.println("Integer相加: " + a + " + " + b + " = " + c);
        
        // 4. 集合中的自动装箱
        java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
        list.add(100);           // 自动装箱
        int value = list.get(0); // 自动拆箱
        System.out.println("集合中的值: " + value);
        
        System.out.println();
    }
    
    /**
     * 包装类常用方法
     * Common Methods of Wrapper Classes
     */
    private static void demonstrateCommonMethods() {
        System.out.println("--- 包装类常用方法 ---");
        
        // 1. 类型转换 (Type Conversion)
        Integer intObj = 100;
        byte byteValue = intObj.byteValue();
        short shortValue = intObj.shortValue();
        long longValue = intObj.longValue();
        float floatValue = intObj.floatValue();
        double doubleValue = intObj.doubleValue();
        
        System.out.println("Integer(100) 转换:");
        System.out.println("  → byte: " + byteValue);
        System.out.println("  → long: " + longValue);
        System.out.println("  → double: " + doubleValue);
        
        // 2. 字符串转换 (String Conversion)
        String str = "12345";
        int parsedInt = Integer.parseInt(str);        // String → int
        Integer wrapperInt = Integer.valueOf(str);    // String → Integer
        
        System.out.println("\n字符串 \"" + str + "\" 转换:");
        System.out.println("  → int: " + parsedInt);
        System.out.println("  → Integer: " + wrapperInt);
        
        // 不同进制转换 (Different Radix)
        String binaryStr = "1010";
        int decimal = Integer.parseInt(binaryStr, 2); // 二进制转十进制
        System.out.println("  二进制\"" + binaryStr + "\" → 十进制: " + decimal);
        
        String hexStr = "FF";
        int hexDecimal = Integer.parseInt(hexStr, 16); // 十六进制转十进制
        System.out.println("  十六进制\"" + hexStr + "\" → 十进制: " + hexDecimal);
        
        // 3. 数字转字符串 (Number to String)
        int num = 12345;
        String numStr1 = Integer.toString(num);
        String numStr2 = String.valueOf(num);
        String numStr3 = num + "";
        
        System.out.println("\n数字 " + num + " 转字符串: \"" + numStr1 + "\"");
        
        // 4. 比较 (Comparison)
        Integer num1 = 100;
        Integer num2 = 200;
        int compareResult = num1.compareTo(num2); // 负数表示小于
        System.out.println("\n比较: " + num1 + ".compareTo(" + num2 + ") = " + compareResult);
        
        // 5. 最大值/最小值 (Max/Min Values)
        System.out.println("\nInteger范围:");
        System.out.println("  最小值: " + Integer.MIN_VALUE);
        System.out.println("  最大值: " + Integer.MAX_VALUE);
        
        System.out.println();
    }
    
    /**
     * 缓存机制
     * Cache Mechanism
     */
    private static void demonstrateCacheMechanism() {
        System.out.println("--- 缓存机制 ---");
        
        // Integer缓存范围: -128 ~ 127
        // Integer Cache Range: -128 to 127
        
        // 缓存范围内 (Within Cache Range)
        Integer a1 = 100;
        Integer a2 = 100;
        System.out.println("Integer a1 = 100;");
        System.out.println("Integer a2 = 100;");
        System.out.println("a1 == a2: " + (a1 == a2)); // true (指向同一对象)
        
        // 缓存范围外 (Outside Cache Range)
        Integer b1 = 200;
        Integer b2 = 200;
        System.out.println("\nInteger b1 = 200;");
        System.out.println("Integer b2 = 200;");
        System.out.println("b1 == b2: " + (b1 == b2)); // false (不同对象)
        
        // 正确的比较方式 (Correct Way to Compare)
        System.out.println("b1.equals(b2): " + b1.equals(b2)); // true (比较值)
        
        // 其他包装类的缓存 (Other Wrapper Caches)
        System.out.println("\n包装类缓存范围:");
        System.out.println("Byte: 全部缓存 (-128 ~ 127)");
        System.out.println("Short: -128 ~ 127");
        System.out.println("Integer: -128 ~ 127");
        System.out.println("Long: -128 ~ 127");
        System.out.println("Character: 0 ~ 127");
        System.out.println("Boolean: true 和 false 都缓存");
        System.out.println("Float, Double: 不缓存");
        
        System.out.println();
    }
    
    /**
     * 注意事项
     * Important Notes
     */
    private static void demonstrateImportantNotes() {
        System.out.println("--- 重要注意事项 ---");
        
        // 1. 空指针异常 (NullPointerException)
        System.out.println("⚠️ 1. 空指针异常:");
        Integer nullInt = null;
        try {
            int value = nullInt; // 自动拆箱，抛出NullPointerException
        } catch (NullPointerException e) {
            System.out.println("   拆箱null值会抛出空指针异常");
        }
        
        // 2. 包装类比较使用equals (Use equals for Wrapper Comparison)
        System.out.println("\n⚠️ 2. 包装类比较应使用equals:");
        Integer x = 128;
        Integer y = 128;
        System.out.println("   x == y: " + (x == y));         // false (比较引用)
        System.out.println("   x.equals(y): " + x.equals(y)); // true (比较值)
        
        // 3. 性能考虑 (Performance Consideration)
        System.out.println("\n⚠️ 3. 性能考虑:");
        System.out.println("   基本类型性能优于包装类");
        System.out.println("   大量运算时优先使用基本类型");
        
        // 4. 类型转换异常 (NumberFormatException)
        System.out.println("\n⚠️ 4. 字符串转换异常:");
        try {
            Integer.parseInt("abc"); // 非数字字符串
        } catch (NumberFormatException e) {
            System.out.println("   \"abc\" 无法转换为数字");
        }
        
        System.out.println();
    }
    
}

