package cn.openthedoor.java.advanced.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型示例
 * Generics Example
 * 
 * 学习目标：
 * 1. 理解泛型的作用
 * 2. 掌握泛型类、泛型方法
 * 3. 掌握通配符（?, extends, super）
 * 4. 理解类型擦除
 * 
 * @author OpenTheDoor
 */
public class GenericsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 泛型演示 ===\n");
        
        // 1. 为什么需要泛型
        demonstrateWhyGenerics();
        
        // 2. 泛型类
        demonstrateGenericClass();
        
        // 3. 泛型方法
        demonstrateGenericMethod();
        
        // 4. 通配符
        demonstrateWildcards();
        
        // 5. 类型擦除
        demonstrateTypeErasure();
    }
    
    /**
     * 为什么需要泛型
     */
    private static void demonstrateWhyGenerics() {
        System.out.println("--- 为什么需要泛型 ---");
        
        // 不使用泛型的问题
        System.out.println("不使用泛型:");
        List list1 = new ArrayList();
        list1.add("Hello");
        list1.add(123);
        list1.add(true);
        
        // 需要强制类型转换，容易出错
        String str = (String) list1.get(0);
        System.out.println(str);
        
        // 使用泛型的好处
        System.out.println("\n使用泛型:");
        List<String> list2 = new ArrayList<>();
        list2.add("World");
        // list2.add(123); // 编译错误！类型安全
        
        String str2 = list2.get(0); // 不需要强制转换
        System.out.println(str2);
        
        System.out.println("\n泛型的优点:");
        System.out.println("1. 类型安全（编译时检查）");
        System.out.println("2. 消除强制类型转换");
        System.out.println("3. 提高代码复用性");
        
        System.out.println();
    }
    
    /**
     * 泛型类
     */
    private static void demonstrateGenericClass() {
        System.out.println("--- 泛型类 ---");
        
        // 使用泛型类
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello");
        System.out.println("String Box: " + stringBox.get());
        
        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        System.out.println("Integer Box: " + intBox.get());
        
        // 多类型参数
        Pair<String, Integer> pair = new Pair<>("Age", 25);
        System.out.println("Pair: " + pair.getKey() + " = " + pair.getValue());
        
        System.out.println();
    }
    
    /**
     * 泛型方法
     */
    private static void demonstrateGenericMethod() {
        System.out.println("--- 泛型方法 ---");
        
        // 调用泛型方法
        String[] strArray = {"A", "B", "C"};
        printArray(strArray);
        
        Integer[] intArray = {1, 2, 3};
        printArray(intArray);
        
        // 泛型方法返回值
        String max = getMax("Apple", "Banana");
        System.out.println("Max: " + max);
        
        System.out.println();
    }
    
    /**
     * 通配符
     */
    private static void demonstrateWildcards() {
        System.out.println("--- 通配符 ---");
        
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.5);
        doubleList.add(2.5);
        
        System.out.println("Integer列表和: " + sumOfList(intList));
        System.out.println("Double列表和: " + sumOfList(doubleList));
        
        // 上界通配符示例
        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("B");
        addNumbers(intList); // 可以添加
        // addNumbers(stringList); // 编译错误！String不是Number子类
        
        System.out.println("\n通配符类型:");
        System.out.println("<?> - 无界通配符");
        System.out.println("<? extends T> - 上界通配符（只能读取）");
        System.out.println("<? super T> - 下界通配符（只能写入）");
        
        System.out.println();
    }
    
    /**
     * 类型擦除
     */
    private static void demonstrateTypeErasure() {
        System.out.println("--- 类型擦除 ---");
        
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        
        // 运行时，泛型信息被擦除
        System.out.println("String List类型: " + stringList.getClass());
        System.out.println("Integer List类型: " + intList.getClass());
        System.out.println("两者类型相同: " + (stringList.getClass() == intList.getClass()));
        
        System.out.println("\n类型擦除的影响:");
        System.out.println("1. 泛型信息只存在于编译期");
        System.out.println("2. 运行时无法获取泛型的实际类型");
        System.out.println("3. 不能使用基本类型作为类型参数");
        
        System.out.println();
    }
    
    // 泛型方法
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    public static <T extends Comparable<T>> T getMax(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }
    
    // 上界通配符 - 只能读取
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }
    
    // 下界通配符 - 可以写入
    public static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }
}

/**
 * 泛型类：盒子
 */
class Box<T> {
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
}

/**
 * 多类型参数泛型类：键值对
 */
class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }
}

