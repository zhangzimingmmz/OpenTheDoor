package cn.openthedoor.java.functional;

import java.util.*;
import java.util.function.*;

/**
 * Lambda表达式示例
 * Lambda Expression Example
 * 
 * 学习目标：
 * 1. 掌握Lambda语法
 * 2. 理解函数式接口
 * 3. 掌握方法引用
 * 4. 理解Lambda vs 匿名内部类
 * 
 * @author OpenTheDoor
 */
public class LambdaDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Lambda表达式演示 ===\n");
        
        // 1. Lambda语法
        demonstrateLambdaSyntax();
        
        // 2. 函数式接口
        demonstrateFunctionalInterfaces();
        
        // 3. 方法引用
        demonstrateMethodReferences();
        
        // 4. Lambda实战
        demonstratePracticalUse();
    }
    
    /**
     * Lambda语法
     */
    private static void demonstrateLambdaSyntax() {
        System.out.println("--- Lambda语法 ---");
        
        // 1. 无参数Lambda
        Runnable task1 = () -> System.out.println("Hello Lambda");
        task1.run();
        
        // 2. 单参数Lambda（括号可省略）
        Consumer<String> consumer = s -> System.out.println("输入: " + s);
        consumer.accept("World");
        
        // 3. 多参数Lambda
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("10 + 20 = " + add.apply(10, 20));
        
        // 4. 多行Lambda（需要大括号和return）
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> {
            int result = a * b;
            System.out.println("计算: " + a + " * " + b + " = " + result);
            return result;
        };
        multiply.apply(5, 6);
        
        System.out.println();
    }
    
    /**
     * 函数式接口
     */
    private static void demonstrateFunctionalInterfaces() {
        System.out.println("--- 函数式接口 ---");
        
        // Function<T, R> - 接受参数返回结果
        Function<String, Integer> strLength = s -> s.length();
        System.out.println("\"Hello\"的长度: " + strLength.apply("Hello"));
        
        // Predicate<T> - 接受参数返回boolean
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("4是偶数: " + isEven.test(4));
        System.out.println("5是偶数: " + isEven.test(5));
        
        // Consumer<T> - 接受参数无返回值
        Consumer<String> printer = s -> System.out.println("打印: " + s);
        printer.accept("Hello Consumer");
        
        // Supplier<T> - 无参数返回结果
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("随机数: " + randomSupplier.get());
        
        System.out.println("\n常用函数式接口:");
        System.out.println("Function<T,R>     - T -> R");
        System.out.println("Predicate<T>      - T -> boolean");
        System.out.println("Consumer<T>       - T -> void");
        System.out.println("Supplier<T>       - () -> T");
        System.out.println("BiFunction<T,U,R> - (T, U) -> R");
        
        System.out.println();
    }
    
    /**
     * 方法引用
     */
    private static void demonstrateMethodReferences() {
        System.out.println("--- 方法引用 ---");
        
        List<String> list = Arrays.asList("A", "C", "B");
        
        // 1. 静态方法引用: 类名::静态方法名
        list.forEach(System.out::println);
        
        // 2. 实例方法引用: 对象::实例方法名
        String prefix = "Item: ";
        Consumer<String> printer = prefix::concat;
        
        // 3. 类的实例方法引用: 类名::实例方法名
        list.sort(String::compareToIgnoreCase);
        System.out.println("排序后: " + list);
        
        // 4. 构造器引用: 类名::new
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        
        System.out.println();
    }
    
    /**
     * Lambda实战
     */
    private static void demonstratePracticalUse() {
        System.out.println("--- Lambda实战 ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        // 排序
        names.sort((a, b) -> a.compareTo(b));
        System.out.println("排序: " + names);
        
        // 过滤
        List<String> filtered = new ArrayList<>();
        names.forEach(name -> {
            if (name.length() > 4) {
                filtered.add(name);
            }
        });
        System.out.println("长度>4: " + filtered);
        
        System.out.println();
    }
    
}

