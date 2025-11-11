package cn.openthedoor.java.syntax.string;

/**
 * 字符串处理示例
 * String Handling Example
 * 
 * 学习目标：
 * 1. 理解String的不可变性
 * 2. 掌握String常用方法
 * 3. 理解String、StringBuilder、StringBuffer的区别
 * 4. 掌握字符串常见操作
 * 
 * @author OpenTheDoor
 */
public class StringDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 字符串处理演示 ===\n");
        
        // 1. String基础 (String Basics)
        demonstrateStringBasics();
        
        // 2. String常用方法 (Common String Methods)
        demonstrateStringMethods();
        
        // 3. String不可变性 (String Immutability)
        demonstrateStringImmutability();
        
        // 4. StringBuilder和StringBuffer (StringBuilder and StringBuffer)
        demonstrateStringBuilder();
        
        // 5. 字符串比较 (String Comparison)
        demonstrateStringComparison();
        
        // 6. 字符串实战 (Practical String Operations)
        demonstratePracticalOperations();
    }
    
    /**
     * String基础
     * String Basics
     */
    private static void demonstrateStringBasics() {
        System.out.println("--- String 基础 ---");
        
        // 创建字符串的方式 (Ways to create String)
        String str1 = "Hello"; // 字符串字面量（推荐）
        String str2 = new String("Hello"); // 使用new（不推荐）
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        String str3 = new String(chars); // 从字符数组创建
        
        System.out.println("str1: " + str1);
        System.out.println("str2: " + str2);
        System.out.println("str3: " + str3);
        
        // 字符串拼接 (String Concatenation)
        String firstName = "张";
        String lastName = "三";
        String fullName = firstName + lastName; // 使用+
        System.out.println("\n姓名: " + fullName);
        
        // 字符串模板（Java 15预览，Java 21正式）
        // Text Blocks (Java 15+)
        String multiLine = """
                第一行
                第二行
                第三行
                """;
        System.out.println("多行文本:\n" + multiLine);
        
        System.out.println();
    }
    
    /**
     * String常用方法
     * Common String Methods
     */
    private static void demonstrateStringMethods() {
        System.out.println("--- String 常用方法 ---");
        
        String str = "  Hello, Java World!  ";
        
        // 1. 长度 (Length)
        System.out.println("原字符串: \"" + str + "\"");
        System.out.println("长度: " + str.length());
        
        // 2. 去除首尾空格 (Trim)
        String trimmed = str.trim();
        System.out.println("去除空格: \"" + trimmed + "\"");
        
        // 3. 大小写转换 (Case Conversion)
        System.out.println("转大写: " + trimmed.toUpperCase());
        System.out.println("转小写: " + trimmed.toLowerCase());
        
        // 4. 查找 (Search)
        System.out.println("\n查找操作:");
        System.out.println("是否包含'Java': " + trimmed.contains("Java"));
        System.out.println("是否以'Hello'开头: " + trimmed.startsWith("Hello"));
        System.out.println("是否以'!'结尾: " + trimmed.endsWith("!"));
        System.out.println("'Java'的位置: " + trimmed.indexOf("Java"));
        System.out.println("'o'最后出现的位置: " + trimmed.lastIndexOf("o"));
        
        // 5. 提取子串 (Substring)
        System.out.println("\n提取子串:");
        System.out.println("从索引7开始: " + trimmed.substring(7));
        System.out.println("索引7到11: " + trimmed.substring(7, 11));
        
        // 6. 替换 (Replace)
        System.out.println("\n替换操作:");
        System.out.println("替换'Java': " + trimmed.replace("Java", "Python"));
        System.out.println("替换所有空格: " + trimmed.replaceAll("\\s+", "_"));
        System.out.println("替换第一个'l': " + trimmed.replaceFirst("l", "L"));
        
        // 7. 分割 (Split)
        System.out.println("\n分割操作:");
        String data = "apple,banana,orange";
        String[] fruits = data.split(",");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
        
        // 8. 字符访问 (Character Access)
        System.out.println("\n字符访问:");
        String word = "Java";
        System.out.println("第一个字符: " + word.charAt(0));
        System.out.println("字符数组: " + java.util.Arrays.toString(word.toCharArray()));
        
        // 9. 判空 (Empty Check)
        System.out.println("\n判空操作:");
        String empty = "";
        String blank = "   ";
        System.out.println("空字符串.isEmpty(): " + empty.isEmpty());
        System.out.println("空白字符串.isBlank() (Java 11+): " + blank.isBlank());
        
        System.out.println();
    }
    
    /**
     * String不可变性
     * String Immutability
     */
    private static void demonstrateStringImmutability() {
        System.out.println("--- String 不可变性 ---");
        
        String str = "Hello";
        System.out.println("原始字符串: " + str);
        System.out.println("原始字符串地址: " + System.identityHashCode(str));
        
        // 修改字符串实际上创建了新对象
        str = str + " World";
        System.out.println("拼接后字符串: " + str);
        System.out.println("拼接后字符串地址: " + System.identityHashCode(str));
        System.out.println("地址已改变，说明创建了新对象");
        
        // 字符串常量池 (String Pool)
        System.out.println("\n字符串常量池:");
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = new String("Java").intern(); // 放入常量池
        
        System.out.println("s1 == s2: " + (s1 == s2)); // true（同一对象）
        System.out.println("s1 == s3: " + (s1 == s3)); // false（不同对象）
        System.out.println("s1 == s4: " + (s1 == s4)); // true（intern后指向常量池）
        
        // 不可变性的优点
        System.out.println("\n不可变性的优点:");
        System.out.println("1. 线程安全");
        System.out.println("2. 可以缓存hash值");
        System.out.println("3. 字符串常量池优化");
        System.out.println("4. 安全性（密码等敏感信息）");
        
        System.out.println();
    }
    
    /**
     * StringBuilder和StringBuffer
     * StringBuilder and StringBuffer
     */
    private static void demonstrateStringBuilder() {
        System.out.println("--- StringBuilder 和 StringBuffer ---");
        
        // StringBuilder: 可变字符串，非线程安全，性能高
        System.out.println("StringBuilder示例:");
        StringBuilder sb = new StringBuilder("Hello");
        System.out.println("初始: " + sb);
        System.out.println("初始地址: " + System.identityHashCode(sb));
        
        sb.append(" World"); // 追加
        System.out.println("追加后: " + sb);
        System.out.println("追加后地址: " + System.identityHashCode(sb));
        System.out.println("地址未变，说明对象未重新创建");
        
        sb.insert(5, ","); // 插入
        System.out.println("插入后: " + sb);
        
        sb.delete(5, 6); // 删除
        System.out.println("删除后: " + sb);
        
        sb.reverse(); // 反转
        System.out.println("反转后: " + sb);
        sb.reverse(); // 恢复
        
        sb.replace(0, 5, "Hi"); // 替换
        System.out.println("替换后: " + sb);
        
        // StringBuffer: 可变字符串，线程安全，性能较低
        System.out.println("\nStringBuffer示例 (线程安全):");
        StringBuffer sbf = new StringBuffer("Thread");
        sbf.append(" Safe");
        System.out.println("StringBuffer: " + sbf);
        
        // 性能对比
        System.out.println("\n性能对比:");
        long startTime, endTime;
        int count = 10000;
        
        // String拼接
        startTime = System.nanoTime();
        String str = "";
        for (int i = 0; i < count; i++) {
            str += "a"; // 每次都创建新对象
        }
        endTime = System.nanoTime();
        System.out.println("String拼接" + count + "次: " + (endTime - startTime) / 1000000.0 + "ms");
        
        // StringBuilder拼接
        startTime = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append("a");
        }
        endTime = System.nanoTime();
        System.out.println("StringBuilder拼接" + count + "次: " + (endTime - startTime) / 1000000.0 + "ms");
        
        // 使用建议
        System.out.println("\n使用建议:");
        System.out.println("String: 字符串内容不变时使用");
        System.out.println("StringBuilder: 单线程下字符串频繁修改时使用（推荐）");
        System.out.println("StringBuffer: 多线程下字符串频繁修改时使用");
        
        System.out.println();
    }
    
    /**
     * 字符串比较
     * String Comparison
     */
    private static void demonstrateStringComparison() {
        System.out.println("--- 字符串比较 ---");
        
        String s1 = "apple";
        String s2 = "Apple";
        String s3 = "banana";
        
        // 1. equals() - 比较内容
        System.out.println("内容比较 (equals):");
        System.out.println("\"apple\".equals(\"Apple\"): " + s1.equals(s2));
        System.out.println("\"apple\".equalsIgnoreCase(\"Apple\"): " + s1.equalsIgnoreCase(s2));
        
        // 2. compareTo() - 字典顺序比较
        System.out.println("\n字典顺序比较 (compareTo):");
        System.out.println("\"apple\".compareTo(\"banana\"): " + s1.compareTo(s3)); // 负数
        System.out.println("\"banana\".compareTo(\"apple\"): " + s3.compareTo(s1)); // 正数
        System.out.println("\"apple\".compareTo(\"apple\"): " + s1.compareTo("apple")); // 0
        
        // 3. == vs equals
        System.out.println("\n== vs equals:");
        String str1 = "Java";
        String str2 = "Java";
        String str3 = new String("Java");
        
        System.out.println("str1 == str2: " + (str1 == str2)); // true（引用相同）
        System.out.println("str1 == str3: " + (str1 == str3)); // false（引用不同）
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true（内容相同）
        
        System.out.println("\n⚠️ 建议：字符串比较总是使用equals()，不要使用==");
        
        System.out.println();
    }
    
    /**
     * 字符串实战操作
     * Practical String Operations
     */
    private static void demonstratePracticalOperations() {
        System.out.println("--- 字符串实战操作 ---");
        
        // 1. 字符串格式化 (String Formatting)
        System.out.println("1. 字符串格式化:");
        String name = "张三";
        int age = 25;
        double salary = 8500.50;
        
        String formatted = String.format("姓名: %s, 年龄: %d, 工资: %.2f", name, age, salary);
        System.out.println(formatted);
        
        // 2. 字符串连接 (String Joining - Java 8+)
        System.out.println("\n2. 字符串连接:");
        String[] words = {"Hello", "Java", "World"};
        String joined = String.join(", ", words);
        System.out.println("连接结果: " + joined);
        
        // 3. 重复字符串 (Repeat String - Java 11+)
        System.out.println("\n3. 重复字符串 (Java 11+):");
        String repeated = "Hello ".repeat(3);
        System.out.println(repeated);
        
        // 4. 判断回文 (Check Palindrome)
        System.out.println("\n4. 判断回文:");
        String palindrome1 = "level";
        String palindrome2 = "hello";
        System.out.println("\"" + palindrome1 + "\" 是回文: " + isPalindrome(palindrome1));
        System.out.println("\"" + palindrome2 + "\" 是回文: " + isPalindrome(palindrome2));
        
        // 5. 反转字符串 (Reverse String)
        System.out.println("\n5. 反转字符串:");
        String original = "Hello";
        String reversed = new StringBuilder(original).reverse().toString();
        System.out.println("原字符串: " + original);
        System.out.println("反转后: " + reversed);
        
        // 6. 统计字符 (Count Characters)
        System.out.println("\n6. 统计字符:");
        String text = "hello world";
        char target = 'l';
        int count = countChar(text, target);
        System.out.println("\"" + text + "\" 中 '" + target + "' 出现次数: " + count);
        
        // 7. 移除空白字符 (Remove Whitespace)
        System.out.println("\n7. 移除空白字符:");
        String withSpaces = "  Hello   Java   World  ";
        String noSpaces = withSpaces.replaceAll("\\s+", " ").trim();
        System.out.println("原字符串: \"" + withSpaces + "\"");
        System.out.println("移除后: \"" + noSpaces + "\"");
        
        System.out.println();
    }
    
    /**
     * 判断是否为回文
     * Check if string is palindrome
     */
    private static boolean isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * 统计字符出现次数
     * Count character occurrences
     */
    private static int countChar(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
    
}

