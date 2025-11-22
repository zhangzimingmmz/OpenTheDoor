package cn.openthedoor.java.functional;

import java.util.*;
import java.util.stream.*;

/**
 * Stream API示例
 * Stream API Example
 * 
 * 学习目标：
 * 1. 理解Stream的概念
 * 2. 掌握Stream的创建
 * 3. 掌握中间操作
 * 4. 掌握终止操作
 * 
 * @author OpenTheDoor
 */
public class StreamDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Stream API演示 ===\n");
        
        // 1. 创建Stream
        demonstrateStreamCreation();
        
        // 2. 中间操作
        demonstrateIntermediateOperations();
        
        // 3. 终止操作
        demonstrateTerminalOperations();
        
        // 4. Stream实战
        demonstratePracticalExamples();
    }
    
    /**
     * 创建Stream
     */
    private static void demonstrateStreamCreation() {
        System.out.println("--- 创建Stream ---");
        
        // 1. 从集合创建
        List<String> list = Arrays.asList("A", "B", "C");
        Stream<String> stream1 = list.stream();
        
        // 2. 从数组创建
        String[] array = {"A", "B", "C"};
        Stream<String> stream2 = Arrays.stream(array);
        
        // 3. 使用Stream.of()
        Stream<String> stream3 = Stream.of("A", "B", "C");
        
        // 4. 无限流
        Stream<Integer> stream4 = Stream.iterate(0, n -> n + 2).limit(10);
        System.out.println("偶数: " + stream4.collect(Collectors.toList()));
        
        System.out.println();
    }
    
    /**
     * 中间操作
     */
    private static void demonstrateIntermediateOperations() {
        System.out.println("--- 中间操作 ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // filter - 过滤
        List<Integer> evens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("偶数: " + evens);
        
        // map - 映射转换
        List<Integer> squares = numbers.stream()
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("平方: " + squares);
        
        // sorted - 排序
        List<Integer> sorted = numbers.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        System.out.println("降序: " + sorted);
        
        // distinct - 去重
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
        List<Integer> distinct = list.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("去重: " + distinct);
        
        // limit - 限制数量
        List<Integer> limited = numbers.stream()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("前5个: " + limited);
        
        System.out.println();
    }
    
    /**
     * 终止操作
     */
    private static void demonstrateTerminalOperations() {
        System.out.println("--- 终止操作 ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // forEach - 遍历
        System.out.print("遍历: ");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // count - 计数
        long count = numbers.stream()
            .filter(n -> n > 3)
            .count();
        System.out.println("大于3的数量: " + count);
        
        // reduce - 归约
        int sum = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        System.out.println("总和: " + sum);
        
        // collect - 收集
        List<Integer> list = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("偶数列表: " + list);
        
        // anyMatch, allMatch, noneMatch
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("包含偶数: " + hasEven);
        System.out.println("全部为正数: " + allPositive);
        
        System.out.println();
    }
    
    /**
     * Stream实战
     */
    private static void demonstratePracticalExamples() {
        System.out.println("--- Stream实战 ---");
        
        List<Employee> employees = Arrays.asList(
            new Employee("张三", 25, 8000),
            new Employee("李四", 30, 12000),
            new Employee("王五", 28, 10000),
            new Employee("赵六", 35, 15000)
        );
        
        // 1. 筛选并排序
        System.out.println("1. 工资大于9000的员工（按工资降序）:");
        employees.stream()
            .filter(e -> e.getSalary() > 9000)
            .sorted(Comparator.comparing(Employee::getSalary).reversed())
            .forEach(e -> System.out.println("   " + e));
        
        // 2. 统计
        System.out.println("\n2. 统计信息:");
        double avgSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0);
        System.out.println("   平均工资: " + avgSalary);
        
        int totalSalary = employees.stream()
            .mapToInt(Employee::getSalary)
            .sum();
        System.out.println("   工资总和: " + totalSalary);
        
        // 3. 分组
        System.out.println("\n3. 按年龄段分组:");
        Map<String, List<Employee>> grouped = employees.stream()
            .collect(Collectors.groupingBy(e -> 
                e.getAge() < 30 ? "青年" : "中年"));
        grouped.forEach((group, list) -> {
            System.out.println("   " + group + ": " + list);
        });
        
        System.out.println();
    }
    
}

/**
 * 员工类
 */
class Employee {
    private String name;
    private int age;
    private int salary;
    
    public Employee(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getSalary() { return salary; }
    
    @Override
    public String toString() {
        return name + "(" + age + "岁, " + salary + "元)";
    }
}

