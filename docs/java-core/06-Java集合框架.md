# Java 集合框架深入理解

!!! abstract "核心内容"
    深入理解Java集合框架的底层实现、性能特点和使用场景，掌握HashMap、ConcurrentHashMap等核心集合的源码原理，能够在面试中自信回答集合相关问题。

---

## 📚 本章导航

- [集合框架概述](#集合框架概述)
- [List 系列](#list-系列)
  - [ArrayList 源码分析](#arraylist-源码分析)
  - [LinkedList 源码分析](#linkedlist-源码分析)
  - [CopyOnWriteArrayList](#copyonwritearraylist)
- [Set 系列](#set-系列)
  - [HashSet](#hashset)
  - [TreeSet](#treeset)
  - [LinkedHashSet](#linkedhashset)
- [Map 系列](#map-系列)
  - [HashMap 深入解析](#hashmap-深入解析)
  - [ConcurrentHashMap 并发原理](#concurrenthashmap-并发原理)
  - [TreeMap](#treemap)
  - [LinkedHashMap](#linkedhashmap)
- [Queue 系列](#queue-系列)
- [集合工具类](#集合工具类)
- [面试高频题](#面试高频题)

---

## 集合框架概述

### Java集合体系结构

```
Collection 接口
├── List (有序、可重复)
│   ├── ArrayList (动态数组)
│   ├── LinkedList (双向链表)
│   ├── Vector (线程安全ArrayList)
│   └── CopyOnWriteArrayList (写时复制)
├── Set (无序、不重复)
│   ├── HashSet (基于HashMap)
│   ├── LinkedHashSet (保持插入顺序)
│   └── TreeSet (红黑树、有序)
└── Queue (队列)
    ├── PriorityQueue (优先队列、堆)
    ├── ArrayDeque (双端队列)
    └── BlockingQueue (阻塞队列)
        ├── ArrayBlockingQueue
        ├── LinkedBlockingQueue
        └── PriorityBlockingQueue

Map 接口 (键值对)
├── HashMap (哈希表)
├── LinkedHashMap (保持插入顺序)
├── TreeMap (红黑树、有序)
├── Hashtable (线程安全HashMap、已过时)
├── ConcurrentHashMap (高并发HashMap)
└── WeakHashMap (弱引用Map)
```

---

## List 系列

### ArrayList 源码分析

#### 核心特点

| 特性 | 说明 |
|------|------|
| **底层实现** | 动态数组 `Object[] elementData` |
| **初始容量** | 默认10（首次add时才初始化） |
| **扩容机制** | 1.5倍扩容：`newCapacity = oldCapacity + (oldCapacity >> 1)` |
| **随机访问** | O(1) - 支持快速随机访问 |
| **插入删除** | O(n) - 需要移动元素 |
| **线程安全** | ❌ 非线程安全 |

#### 核心源码解析

##### 1. 添加元素（add方法）

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // 确保容量足够
    elementData[size++] = e;           // 添加元素
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
    if (minCapacity - elementData.length > 0) {
        grow(minCapacity);  // 扩容
    }
}

private void grow(int minCapacity) {
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);  // 1.5倍扩容
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // 数组拷贝
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

##### 2. 删除元素（remove方法）

```java
public E remove(int index) {
    rangeCheck(index);
    
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if (numMoved > 0)
        // 将index后面的元素向前移动
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    elementData[--size] = null; // 清空引用，让GC回收
    
    return oldValue;
}
```

#### 关键问题

**Q1: 为什么扩容是1.5倍而不是2倍？**

```java
// 1.5倍扩容的优势：
// 1. 节省内存：2倍扩容会浪费更多内存
// 2. 适中增长：既不会频繁扩容，也不会浪费太多空间
// 3. 数学原理：1.5倍扩容能更好地利用之前释放的空间

// 扩容公式
int newCapacity = oldCapacity + (oldCapacity >> 1);  // 右移1位相当于除以2
```

**Q2: 为什么elementData声明为transient？**

```java
transient Object[] elementData;  // 为什么是transient？

// 原因：
// 1. elementData容量通常大于实际元素数量（size < elementData.length）
// 2. 序列化时只需要序列化有效元素（0 ~ size-1）
// 3. ArrayList重写了writeObject和readObject方法，只序列化有效元素

private void writeObject(java.io.ObjectOutputStream s) {
    s.defaultWriteObject();
    s.writeInt(size);  // 写入实际元素数量
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);  // 只写入有效元素
    }
}
```

---

### LinkedList 源码分析

#### 核心特点

| 特性 | 说明 |
|------|------|
| **底层实现** | 双向链表 `Node<E>` |
| **随机访问** | O(n) - 需要遍历链表 |
| **插入删除** | O(1) - 只需修改指针（已知节点位置） |
| **内存占用** | 较高 - 每个节点需要额外存储前后指针 |
| **线程安全** | ❌ 非线程安全 |

#### 核心数据结构

```java
private static class Node<E> {
    E item;          // 数据
    Node<E> next;    // 后继指针
    Node<E> prev;    // 前驱指针
    
    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

#### 核心源码解析

##### 1. 添加元素（add方法）

```java
public boolean add(E e) {
    linkLast(e);  // 添加到链表末尾
    return true;
}

void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;
    if (l == null)
        first = newNode;  // 第一个节点
    else
        l.next = newNode; // 修改前一个节点的next指针
    size++;
}
```

##### 2. 插入元素（add(index, element)）

```java
public void add(int index, E element) {
    checkPositionIndex(index);
    
    if (index == size)
        linkLast(element);  // 添加到末尾
    else
        linkBefore(element, node(index));  // 插入到index位置
}

// 根据index查找节点（关键：二分优化）
Node<E> node(int index) {
    if (index < (size >> 1)) {  // 前半部分，从头开始遍历
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else {  // 后半部分，从尾开始遍历
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

---

### ArrayList vs LinkedList

| 对比项 | ArrayList | LinkedList |
|--------|-----------|------------|
| **底层结构** | 动态数组 | 双向链表 |
| **随机访问** | O(1) ⭐ | O(n) |
| **头部插入** | O(n) | O(1) ⭐ |
| **尾部插入** | O(1) 均摊 ⭐ | O(1) ⭐ |
| **中间插入** | O(n) | O(n) (查找) + O(1) (插入) |
| **删除** | O(n) | O(n) (查找) + O(1) (删除) |
| **内存占用** | 低 ⭐ | 高（额外指针） |
| **使用场景** | 查询多、插入少 | 频繁头尾操作 |

#### 选择建议

```java
// ✅ 使用ArrayList的场景：
// 1. 大量随机访问（get操作）
// 2. 数据量不大（不会频繁扩容）
// 3. 尾部插入为主
List<String> list = new ArrayList<>();
for (int i = 0; i < 1000; i++) {
    list.get(i);  // O(1)快速访问
}

// ✅ 使用LinkedList的场景：
// 1. 频繁在头部插入/删除
// 2. 实现队列/栈（Deque接口）
// 3. 不需要随机访问
Deque<String> deque = new LinkedList<>();
deque.addFirst("头部插入");  // O(1)
deque.addLast("尾部插入");   // O(1)
```

---

### CopyOnWriteArrayList

#### 核心特点

| 特性 | 说明 |
|------|------|
| **线程安全** | ✅ 通过写时复制实现 |
| **读操作** | 无锁、高性能 |
| **写操作** | 加锁、复制整个数组 |
| **使用场景** | 读多写少 |

#### 核心原理

```java
public class CopyOnWriteArrayList<E> {
    private transient volatile Object[] array;
    
    // 读操作：无锁
    public E get(int index) {
        return get(getArray(), index);
    }
    
    // 写操作：加锁 + 复制数组
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();  // 加锁
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);  // 复制
            newElements[len] = e;  // 添加新元素
            setArray(newElements); // 替换数组
            return true;
        } finally {
            lock.unlock();
        }
    }
}
```

#### 优缺点

**优点**：
- 读操作无锁，性能高
- 适合读多写少场景

**缺点**：
- 写操作需要复制整个数组，内存消耗大
- 不能保证实时一致性（读操作可能读到旧数据）

---

## Set 系列

### HashSet

#### 核心特点

- **底层实现**：基于HashMap（value为固定的PRESENT对象）
- **无序性**：不保证元素顺序
- **唯一性**：依赖hashCode()和equals()

#### 源码分析

```java
public class HashSet<E> extends AbstractSet<E> {
    private transient HashMap<E,Object> map;
    private static final Object PRESENT = new Object();  // 固定值
    
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;  // key为元素，value为PRESENT
    }
    
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
}
```

---

### TreeSet

#### 核心特点

- **底层实现**：基于TreeMap（红黑树）
- **有序性**：自然排序或自定义Comparator
- **时间复杂度**：O(log n)

#### 使用示例

```java
// 自然排序
TreeSet<Integer> set1 = new TreeSet<>();
set1.addAll(Arrays.asList(5, 3, 8, 1, 9));
System.out.println(set1);  // [1, 3, 5, 8, 9]

// 自定义排序
TreeSet<String> set2 = new TreeSet<>((a, b) -> b.compareTo(a));  // 降序
set2.addAll(Arrays.asList("apple", "banana", "cherry"));
System.out.println(set2);  // [cherry, banana, apple]
```

---

### LinkedHashSet

#### 核心特点

- **底层实现**：HashSet + 双向链表
- **有序性**：保持插入顺序
- **性能**：略低于HashSet（额外维护链表）

---

## Map 系列

### HashMap 深入解析

#### 核心特点

| 特性 | 说明 |
|------|------|
| **底层结构** | 数组 + 链表 + 红黑树（JDK 1.8+） |
| **初始容量** | 16 |
| **负载因子** | 0.75 |
| **扩容机制** | 2倍扩容（容量达到阈值时） |
| **线程安全** | ❌ 非线程安全 |
| **允许null** | key和value都可以为null |

#### 核心数据结构

```java
public class HashMap<K,V> {
    // 数组（桶）
    transient Node<K,V>[] table;
    
    // 链表节点
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;    // hash值
        final K key;       // 键
        V value;           // 值
        Node<K,V> next;    // 下一个节点
    }
    
    // 红黑树节点
    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // 父节点
        TreeNode<K,V> left;    // 左子节点
        TreeNode<K,V> right;   // 右子节点
        TreeNode<K,V> prev;
        boolean red;           // 红黑标记
    }
}
```

#### JDK 1.8 重要优化

##### 1. 数组 + 链表 + 红黑树

```java
// JDK 1.7：数组 + 链表
// 问题：链表过长时，查询性能退化到O(n)

// JDK 1.8：数组 + 链表 + 红黑树
// 优化：链表长度>8 且 数组长度>64 时，转换为红黑树
static final int TREEIFY_THRESHOLD = 8;      // 树化阈值
static final int UNTREEIFY_THRESHOLD = 6;    // 退化为链表阈值
static final int MIN_TREEIFY_CAPACITY = 64;  // 最小树化容量

// 树化条件
if (binCount >= TREEIFY_THRESHOLD - 1) {
    if (tab.length >= MIN_TREEIFY_CAPACITY)
        treeifyBin(tab, hash);  // 转换为红黑树
}
```

**为什么是8？**
- 泊松分布计算：链表长度达到8的概率非常小（0.00000006）
- 红黑树节点占用空间是链表节点的2倍
- 8是空间和时间的平衡点

##### 2. 尾插法替代头插法

```java
// JDK 1.7：头插法
// 问题：多线程扩容时可能形成环形链表，导致死循环
void transfer(Entry[] newTable) {
    Entry[] src = table;
    for (int j = 0; j < src.length; j++) {
        Entry<K,V> e = src[j];
        while (e != null) {
            Entry<K,V> next = e.next;
            int i = indexFor(e.hash, newTable.length);
            e.next = newTable[i];  // 头插法：新节点插入到链表头部
            newTable[i] = e;
            e = next;
        }
    }
}

// JDK 1.8：尾插法
// 优化：避免形成环形链表
final Node<K,V>[] resize() {
    // ...
    if (loTail != null) {
        loTail.next = null;  // 尾插法：新节点插入到链表尾部
        newTab[j] = loHead;
    }
    // ...
}
```

#### 核心方法解析

##### 1. put方法

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

// 核心逻辑
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    
    // 1. 如果数组为空，进行初始化（懒加载）
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    
    // 2. 计算索引位置，如果该位置为空，直接插入
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        
        // 3. 如果key已存在，直接覆盖
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        
        // 4. 如果是红黑树节点，插入树中
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        
        // 5. 如果是链表节点，遍历链表
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);  // 尾插法
                    
                    // 链表长度>=8，转换为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1)
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        
        // 6. 如果key已存在，更新value
        if (e != null) {
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            return oldValue;
        }
    }
    
    ++modCount;
    
    // 7. 如果size超过阈值，进行扩容
    if (++size > threshold)
        resize();
    
    return null;
}
```

##### 2. hash方法

```java
// 为什么要高16位异或低16位？
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// 原因：
// 1. HashMap的索引计算：index = (table.length - 1) & hash
// 2. 如果table.length较小（如16），只有低4位参与计算
// 3. 高16位异或低16位，让高位也参与计算，减少hash冲突

// 示例：
// hashCode:  1111 1111 1111 1111  0000 0000 0000 0000
// h >>> 16:  0000 0000 0000 0000  1111 1111 1111 1111
// 异或结果:   1111 1111 1111 1111  1111 1111 1111 1111
```

##### 3. resize方法（扩容）

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    
    if (oldCap > 0) {
        // 1. 已达到最大容量，不再扩容
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        
        // 2. 容量和阈值都扩大2倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1;
    }
    
    // ... 省略初始化逻辑
    
    threshold = newThr;
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    
    if (oldTab != null) {
        // 3. 重新分配节点到新数组
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                
                // 单个节点，直接计算新位置
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                
                // 红黑树节点
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                
                // 链表节点
                else {
                    Node<K,V> loHead = null, loTail = null;  // 低位链表
                    Node<K,V> hiHead = null, hiTail = null;  // 高位链表
                    Node<K,V> next;
                    
                    do {
                        next = e.next;
                        // (e.hash & oldCap) == 0 表示节点留在原位置
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        // 否则移动到 原位置+oldCap
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    
                    // 放入新数组
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;  // 原位置
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;  // 原位置+oldCap
                    }
                }
            }
        }
    }
    return newTab;
}
```

#### 关键问题

**Q1: 为什么负载因子是0.75？**

```java
static final float DEFAULT_LOAD_FACTOR = 0.75f;

// 原因：
// 1. 时间和空间的折中
//    - 负载因子太小（如0.5）：浪费空间，但冲突少
//    - 负载因子太大（如1.0）：节省空间，但冲突多
// 2. 泊松分布：0.75时，链表长度超过8的概率非常小
// 3. 数学计算：
//    - 0.75 = 3/4
//    - threshold = capacity * 0.75 = capacity - (capacity >> 2)
//    - 位运算效率高
```

**Q2: 为什么容量必须是2的幂次？**

```java
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}

// 原因：
// 1. 索引计算：index = (table.length - 1) & hash
//    - 如果length是2的幂次，length-1的二进制全是1
//    - 例如：16-1=15=0b1111，&运算相当于取模，但效率更高
// 2. 扩容优化：
//    - 容量翻倍，节点要么留在原位置，要么移动到 原位置+oldCap
//    - 通过 (hash & oldCap) == 0 判断，避免重新计算hash
```

**Q3: 如何解决hash冲突？**

```java
// HashMap解决hash冲突的方法：
// 1. 链地址法（Separate Chaining）
//    - 冲突的元素放在同一个桶的链表中
// 2. 红黑树优化（JDK 1.8）
//    - 链表长度>8时，转换为红黑树，查询O(log n)
// 3. 扰动函数（高16位异或低16位）
//    - 让hash分布更均匀，减少冲突
// 4. 2倍扩容
//    - 容量不够时扩容，减少冲突概率
```

---

### ConcurrentHashMap 并发原理

#### JDK 1.7 vs JDK 1.8

| 版本 | 底层结构 | 并发方案 | 锁粒度 |
|------|---------|---------|--------|
| **JDK 1.7** | Segment数组 + HashEntry数组 + 链表 | 分段锁（Segment继承ReentrantLock） | Segment级别 |
| **JDK 1.8** | Node数组 + 链表 + 红黑树 | CAS + synchronized | 桶（Node）级别 |

#### JDK 1.8 核心原理

##### 1. put方法

```java
public V put(K key, V value) {
    return putVal(key, value, false);
}

final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
    
    int hash = spread(key.hashCode());  // 计算hash
    int binCount = 0;
    
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        
        // 1. 如果数组为空，初始化数组
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        
        // 2. 如果桶为空，CAS插入（无锁）
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value, null)))
                break;  // CAS成功，结束
        }
        
        // 3. 如果正在扩容，帮助扩容
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        
        // 4. 如果桶不为空，synchronized锁住链表头节点
        else {
            V oldVal = null;
            synchronized (f) {  // 锁住链表头节点
                if (tabAt(tab, i) == f) {
                    // 链表节点
                    if (fh >= 0) {
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            // key已存在，更新value
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            // 插入到链表尾部
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key, value, null);
                                break;
                            }
                        }
                    }
                    // 红黑树节点
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key, value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                }
            }
            
            // 5. 链表长度>=8，转换为红黑树
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
    
    addCount(1L, binCount);  // 更新size
    return null;
}
```

##### 2. get方法（无锁）

```java
public V get(Object key) {
    Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
    int h = spread(key.hashCode());
    
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (e = tabAt(tab, (n - 1) & h)) != null) {
        
        // 1. 头节点就是目标节点
        if ((eh = e.hash) == h) {
            if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                return e.val;
        }
        
        // 2. 红黑树节点
        else if (eh < 0)
            return (p = e.find(h, key)) != null ? p.val : null;
        
        // 3. 遍历链表
        while ((e = e.next) != null) {
            if (e.hash == h &&
                ((ek = e.key) == key || (ek != null && key.equals(ek))))
                return e.val;
        }
    }
    return null;
}
```

#### 核心优势

**相比JDK 1.7的分段锁**：

1. **锁粒度更细**：从Segment级别 → Node级别
2. **并发度更高**：理论上并发度=数组长度
3. **内存占用更低**：不需要Segment数组
4. **put性能更好**：
   - 桶为空时，CAS插入（无锁）
   - 桶不为空时，只锁住链表头节点

**相比HashMap**：

| 对比项 | HashMap | ConcurrentHashMap |
|--------|---------|-------------------|
| **线程安全** | ❌ | ✅ |
| **null key/value** | ✅ | ❌（抛出NPE） |
| **put性能** | 高 | 中（有锁开销） |
| **get性能** | 高 | 高（无锁） |
| **使用场景** | 单线程 | 多线程 |

---

### TreeMap

#### 核心特点

- **底层实现**：红黑树
- **有序性**：自然排序或自定义Comparator
- **时间复杂度**：O(log n)

#### 使用示例

```java
// 自然排序
TreeMap<Integer, String> map1 = new TreeMap<>();
map1.put(3, "three");
map1.put(1, "one");
map1.put(2, "two");
System.out.println(map1);  // {1=one, 2=two, 3=three}

// 自定义排序
TreeMap<String, Integer> map2 = new TreeMap<>((a, b) -> b.compareTo(a));  // 降序
map2.put("apple", 1);
map2.put("banana", 2);
map2.put("cherry", 3);
System.out.println(map2);  // {cherry=3, banana=2, apple=1}

// 范围查询
TreeMap<Integer, String> map3 = new TreeMap<>();
map3.put(1, "one");
map3.put(2, "two");
map3.put(3, "three");
map3.put(4, "four");
map3.put(5, "five");

System.out.println(map3.subMap(2, 5));  // {2=two, 3=three, 4=four}
System.out.println(map3.headMap(3));    // {1=one, 2=two}
System.out.println(map3.tailMap(3));    // {3=three, 4=four, 5=five}
```

---

### LinkedHashMap

#### 核心特点

- **底层实现**：HashMap + 双向链表
- **有序性**：保持插入顺序或访问顺序
- **LRU缓存**：通过accessOrder实现

#### 实现LRU缓存

```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    
    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // accessOrder=true，按访问顺序排序
        this.capacity = capacity;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;  // 超过容量，删除最老的元素
    }
    
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        System.out.println(cache);  // {1=one, 2=two, 3=three}
        
        cache.get(1);  // 访问1
        cache.put(4, "four");  // 插入4，删除最老的2
        System.out.println(cache);  // {3=three, 1=one, 4=four}
    }
}
```

---

## Queue 系列

### PriorityQueue

#### 核心特点

- **底层实现**：二叉堆（完全二叉树）
- **有序性**：自然排序或自定义Comparator
- **时间复杂度**：插入/删除 O(log n)，查看堆顶 O(1)

#### 使用示例

```java
// 小顶堆（默认）
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.addAll(Arrays.asList(5, 3, 8, 1, 9));
while (!minHeap.isEmpty()) {
    System.out.print(minHeap.poll() + " ");  // 1 3 5 8 9
}

// 大顶堆
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
maxHeap.addAll(Arrays.asList(5, 3, 8, 1, 9));
while (!maxHeap.isEmpty()) {
    System.out.print(maxHeap.poll() + " ");  // 9 8 5 3 1
}
```

---

### ArrayDeque

#### 核心特点

- **底层实现**：循环数组
- **性能**：比LinkedList更快（无节点分配开销）
- **推荐**：实现栈/队列时优先使用ArrayDeque

#### 使用示例

```java
// 作为栈使用
Deque<String> stack = new ArrayDeque<>();
stack.push("A");
stack.push("B");
stack.push("C");
System.out.println(stack.pop());  // C

// 作为队列使用
Deque<String> queue = new ArrayDeque<>();
queue.offer("A");
queue.offer("B");
queue.offer("C");
System.out.println(queue.poll());  // A
```

---

## 集合工具类

### Collections 常用方法

```java
List<Integer> list = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 9));

// 排序
Collections.sort(list);  // [1, 3, 5, 8, 9]
Collections.sort(list, (a, b) -> b - a);  // [9, 8, 5, 3, 1] 降序

// 二分查找
int index = Collections.binarySearch(list, 5);  // 需要先排序

// 反转
Collections.reverse(list);

// 打乱
Collections.shuffle(list);

// 最大值/最小值
int max = Collections.max(list);
int min = Collections.min(list);

// 填充
Collections.fill(list, 0);  // 全部填充为0

// 不可变集合
List<Integer> immutableList = Collections.unmodifiableList(list);

// 线程安全集合
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
```

---

### Arrays 常用方法

```java
int[] arr = {5, 3, 8, 1, 9};

// 排序
Arrays.sort(arr);  // [1, 3, 5, 8, 9]

// 二分查找
int index = Arrays.binarySearch(arr, 5);

// 转字符串
System.out.println(Arrays.toString(arr));  // [1, 3, 5, 8, 9]

// 转List
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

// 填充
Arrays.fill(arr, 0);  // 全部填充为0

// 复制
int[] newArr = Arrays.copyOf(arr, arr.length);

// 比较
boolean equals = Arrays.equals(arr, newArr);
```

---

## 面试高频题

### 必考题（⭐⭐⭐⭐⭐）

#### 1. HashMap的底层实现原理？

**答**：
- **JDK 1.7**：数组 + 链表
- **JDK 1.8**：数组 + 链表 + 红黑树
  - 链表长度>8 且 数组长度>64时，链表转红黑树
  - 红黑树节点<6时，退化为链表

**核心流程**：
1. 计算key的hash值：`(h = key.hashCode()) ^ (h >>> 16)`
2. 计算索引位置：`(table.length - 1) & hash`
3. 如果桶为空，直接插入
4. 如果桶不为空，遍历链表/红黑树
5. 如果key已存在，更新value
6. 如果key不存在，插入新节点
7. 如果size超过阈值（capacity * 0.75），扩容2倍

---

#### 2. HashMap为什么线程不安全？

**答**：

1. **JDK 1.7**：多线程扩容时，头插法可能形成环形链表，导致死循环
2. **JDK 1.8**：
   - put操作非原子性，可能丢失数据
   - 扩容时，多个线程同时resize可能导致数据丢失

**示例**：
```java
// 线程A和线程B同时put
// 1. 线程A计算索引，准备插入
// 2. 线程B计算索引，插入成功
// 3. 线程A继续插入，覆盖线程B的数据 → 数据丢失
```

---

#### 3. HashMap和ConcurrentHashMap的区别？

| 对比项 | HashMap | ConcurrentHashMap |
|--------|---------|-------------------|
| **线程安全** | ❌ | ✅ |
| **null key/value** | ✅ | ❌ |
| **并发方案** | 无 | JDK1.7分段锁 / JDK1.8 CAS+synchronized |
| **性能** | 高 | 中（有锁开销） |

**JDK 1.8 ConcurrentHashMap原理**：
- get无锁：volatile保证可见性
- put有锁：
  - 桶为空：CAS插入（无锁）
  - 桶不为空：synchronized锁住链表头节点

---

#### 4. ArrayList和LinkedList的区别？

| 对比项 | ArrayList | LinkedList |
|--------|-----------|------------|
| **底层结构** | 动态数组 | 双向链表 |
| **随机访问** | O(1) ⭐ | O(n) |
| **头部插入** | O(n) | O(1) ⭐ |
| **尾部插入** | O(1) 均摊 | O(1) |
| **内存占用** | 低 | 高（额外指针） |
| **使用场景** | 查询多、插入少 | 频繁头尾操作 |

---

#### 5. HashMap的扩容机制？

**答**：

**触发条件**：
- size > threshold（threshold = capacity * 0.75）

**扩容过程**：
1. 容量扩大2倍：`newCap = oldCap << 1`
2. 阈值扩大2倍：`newThr = oldThr << 1`
3. 创建新数组：`new Node[newCap]`
4. 重新分配节点：
   - 单个节点：直接计算新位置
   - 链表节点：拆分为低位链表和高位链表
     - 低位链表：`(hash & oldCap) == 0`，留在原位置
     - 高位链表：`(hash & oldCap) != 0`，移动到 `原位置+oldCap`
   - 红黑树节点：拆分树

**为什么是2倍扩容？**
- 保证容量始终是2的幂次
- 节点位置要么不变，要么移动`oldCap`位置
- 通过`(hash & oldCap)`判断，避免重新计算hash

---

### 高频题（⭐⭐⭐⭐）

#### 6. ConcurrentHashMap的实现原理？

**JDK 1.7**：
- 分段锁（Segment继承ReentrantLock）
- 锁粒度：Segment级别
- 并发度：Segment数量（默认16）

**JDK 1.8**：
- CAS + synchronized
- 锁粒度：Node级别（桶）
- 并发度：理论上无限（数组长度）

**put流程**：
1. 桶为空：CAS插入（无锁）
2. 桶不为空：synchronized锁住链表头节点
3. 链表长度>=8：转红黑树

**get流程**：
- 无锁（volatile保证可见性）

---

#### 7. 如何实现LRU缓存？

**方法1：LinkedHashMap**
```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    
    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);  // accessOrder=true
        this.capacity = capacity;
    }
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

**方法2：HashMap + 双向链表**
```java
class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
    }
    
    private Map<Integer, Node> map = new HashMap<>();
    private Node head, tail;
    private int capacity;
    
    // put、get、remove等方法...
}
```

---

#### 8. HashMap的hash方法为什么要异或高16位？

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

**原因**：
1. HashMap的索引计算：`index = (table.length - 1) & hash`
2. 如果table.length较小（如16），只有低4位参与计算
3. 高16位异或低16位，让高位也参与计算，减少hash冲突
4. 这种操作叫做"扰动函数"

---

#### 9. 为什么HashMap的容量必须是2的幂次？

**原因**：

1. **索引计算优化**：
   ```java
   // 如果length是2的幂次，length-1的二进制全是1
   // 例如：16-1=15=0b1111
   // index = hash & (length - 1) 相当于 hash % length，但效率更高
   ```

2. **扩容优化**：
   ```java
   // 容量翻倍，节点要么留在原位置，要么移动到 原位置+oldCap
   // 通过 (hash & oldCap) == 0 判断，避免重新计算hash
   ```

---

#### 10. fail-fast 和 fail-safe的区别？

**fail-fast（快速失败）**：
- 原理：通过modCount检测并发修改
- 代表：ArrayList、HashMap
- 抛出：`ConcurrentModificationException`

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
for (String item : list) {
    if ("B".equals(item)) {
        list.remove(item);  // 抛出ConcurrentModificationException
    }
}
```

**fail-safe（安全失败）**：
- 原理：复制一份数据进行遍历
- 代表：CopyOnWriteArrayList、ConcurrentHashMap
- 不抛异常，但可能读到旧数据

```java
List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
for (String item : list) {
    if ("B".equals(item)) {
        list.remove(item);  // 不抛异常
    }
}
```

---

## 🎯 总结

### 集合选择指南

```
List
├── ArrayList - 查询多、插入少
├── LinkedList - 频繁头尾操作
└── CopyOnWriteArrayList - 读多写少、线程安全

Set
├── HashSet - 无序、唯一
├── LinkedHashSet - 保持插入顺序
└── TreeSet - 有序（自然排序）

Map
├── HashMap - 无序、高性能
├── LinkedHashMap - 保持插入顺序、LRU缓存
├── TreeMap - 有序（自然排序）
└── ConcurrentHashMap - 线程安全、高并发

Queue
├── PriorityQueue - 优先队列（堆）
├── ArrayDeque - 栈/队列（推荐）
└── BlockingQueue - 阻塞队列（生产者消费者）
```

### 性能对比

| 集合 | get | add | remove | contains |
|------|-----|-----|--------|----------|
| ArrayList | O(1) | O(1)均摊 | O(n) | O(n) |
| LinkedList | O(n) | O(1) | O(1) | O(n) |
| HashSet | - | O(1) | O(1) | O(1) |
| TreeSet | - | O(log n) | O(log n) | O(log n) |
| HashMap | O(1) | O(1) | O(1) | O(1) |
| TreeMap | O(log n) | O(log n) | O(log n) | O(log n) |

---

## 📝 实践建议

1. **ArrayList vs LinkedList**
   - 90%的场景用ArrayList
   - 只有频繁头部操作时用LinkedList
   - 实现栈/队列用ArrayDeque

2. **HashMap vs ConcurrentHashMap**
   - 单线程用HashMap
   - 多线程用ConcurrentHashMap
   - 不要用Hashtable（已过时）

3. **HashSet vs TreeSet**
   - 无序场景用HashSet
   - 需要排序用TreeSet
   - 保持插入顺序用LinkedHashSet

4. **注意事项**
   - 预估容量，减少扩容
   - 重写equals()必须重写hashCode()
   - 使用泛型，避免类型转换
   - 遍历删除用Iterator

---

## 🔗 延伸阅读

- [HashMap源码分析](https://www.pdai.tech/md/java/collection/java-map-HashMap.html)
- [ConcurrentHashMap源码分析](https://www.pdai.tech/md/java/thread/java-thread-x-juc-collection-ConcurrentHashMap.html)
- [ArrayList vs LinkedList性能对比](https://www.baeldung.com/java-arraylist-vs-linkedlist)

---

**下一章：** [07 - Java并发编程 →](./07-Java并发编程.md)

**上一章：** [← 05 - Java新版本特性](./05-Java新版本特性.md)

