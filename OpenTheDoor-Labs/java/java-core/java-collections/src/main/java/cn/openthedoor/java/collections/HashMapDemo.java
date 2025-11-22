package cn.openthedoor.java.collections;

import java.util.*;

/**
 * HashMap深入解析
 * HashMap Deep Dive
 * 
 * 学习目标：
 * 1. 理解HashMap的底层实现
 * 2. 掌握HashMap的put/get过程
 * 3. 理解扩容机制
 * 4. 理解hash冲突的解决
 * 5. 理解JDK 1.7 vs 1.8的区别
 * 
 * @author OpenTheDoor
 */
public class HashMapDemo {
    
    public static void main(String[] args) {
        System.out.println("=== HashMap深入解析 ===\n");
        
        // 1. HashMap基础
        demonstrateBasics();
        
        // 2. HashMap原理
        demonstratePrinciples();
        
        // 3. HashMap遍历
        demonstrateIteration();
        
        // 4. HashMap常见问题
        demonstrateCommonIssues();
    }
    
    /**
     * HashMap基础
     */
    private static void demonstrateBasics() {
        System.out.println("--- HashMap基础 ---");
        
        // 创建HashMap
        Map<String, Integer> map = new HashMap<>();
        
        // 添加元素
        map.put("Java", 95);
        map.put("Python", 88);
        map.put("C++", 92);
        
        System.out.println("HashMap: " + map);
        
        // 获取元素
        System.out.println("Java分数: " + map.get("Java"));
        
        // 判断key是否存在
        System.out.println("包含Java: " + map.containsKey("Java"));
        System.out.println("包含Go: " + map.containsKey("Go"));
        
        // 删除元素
        map.remove("C++");
        System.out.println("删除C++后: " + map);
        
        // 大小
        System.out.println("大小: " + map.size());
        
        System.out.println();
    }
    
    /**
     * HashMap原理
     */
    private static void demonstratePrinciples() {
        System.out.println("--- HashMap原理 ---");
        
        System.out.println("HashMap底层实现:");
        System.out.println("JDK 1.7: 数组 + 链表");
        System.out.println("JDK 1.8: 数组 + 链表 + 红黑树");
        System.out.println();
        
        System.out.println("核心参数:");
        System.out.println("- 初始容量 (Initial Capacity): 16");
        System.out.println("- 负载因子 (Load Factor): 0.75");
        System.out.println("- 树化阈值 (Treeify Threshold): 8");
        System.out.println("- 树化最小容量: 64");
        System.out.println();
        
        System.out.println("put过程:");
        System.out.println("1. 计算key的hash值");
        System.out.println("2. 根据hash值找到数组位置");
        System.out.println("3. 如果位置为空，直接放入");
        System.out.println("4. 如果位置有值，遍历链表/树");
        System.out.println("5. 找到相同key则替换，否则添加到末尾");
        System.out.println("6. 如果链表长度>8且数组长度>=64，转为红黑树");
        System.out.println("7. 如果size超过阈值，进行扩容");
        System.out.println();
        
        System.out.println("扩容机制:");
        System.out.println("- 当size > capacity * loadFactor时扩容");
        System.out.println("- 扩容为原来的2倍");
        System.out.println("- 重新计算所有元素的位置");
        System.out.println();
    }
    
    /**
     * HashMap遍历
     */
    private static void demonstrateIteration() {
        System.out.println("--- HashMap遍历 ---");
        
        Map<String, Integer> map = new HashMap<>();
        map.put("Java", 95);
        map.put("Python", 88);
        map.put("C++", 92);
        
        // 方式1：遍历key
        System.out.println("方式1 - 遍历key:");
        for (String key : map.keySet()) {
            System.out.println("  " + key + ": " + map.get(key));
        }
        
        // 方式2：遍历value
        System.out.println("\n方式2 - 遍历value:");
        for (Integer value : map.values()) {
            System.out.println("  " + value);
        }
        
        // 方式3：遍历Entry（推荐）
        System.out.println("\n方式3 - 遍历Entry:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        // 方式4：Lambda（Java 8+）
        System.out.println("\n方式4 - Lambda:");
        map.forEach((k, v) -> System.out.println("  " + k + ": " + v));
        
        System.out.println();
    }
    
    /**
     * HashMap常见问题
     */
    private static void demonstrateCommonIssues() {
        System.out.println("--- HashMap常见问题 ---");
        
        // 1. HashMap是否线程安全？
        System.out.println("1. HashMap是否线程安全？");
        System.out.println("   答：否。多线程使用ConcurrentHashMap");
        
        // 2. HashMap允许null键和null值吗？
        System.out.println("\n2. HashMap允许null键和null值吗？");
        Map<String, String> map = new HashMap<>();
        map.put(null, "null-key");
        map.put("null-value", null);
        System.out.println("   允许。null键最多1个，null值可以多个");
        System.out.println("   " + map);
        
        // 3. HashMap和Hashtable的区别？
        System.out.println("\n3. HashMap vs Hashtable:");
        System.out.println("   HashMap: 非线程安全，允许null键值，效率高");
        System.out.println("   Hashtable: 线程安全，不允许null，效率低（已过时）");
        
        // 4. 初始容量的选择
        System.out.println("\n4. 如何选择初始容量？");
        System.out.println("   建议: initialCapacity = 预期大小 / 0.75 + 1");
        System.out.println("   例如: 预期1000个元素，初始容量设为 1000/0.75+1 ≈ 1334");
        
        System.out.println();
    }
    
}

