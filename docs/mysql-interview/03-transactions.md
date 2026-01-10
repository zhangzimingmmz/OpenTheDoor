# 3. 事务与隔离级别

事务是数据库区别于文件系统的重要特性。

## 3.1 ACID 的实现原理

面试官常问：“MySQL 是如何保证 ACID 的？”

*   **原子性 (Atomicity)**：由 **Undo Log** 保证。如果事务执行失败或回滚，利用 Undo Log 将数据恢复到事务开始前的状态。
*   **一致性 (Consistency)**：这是最终目标。由原子性、隔离性、持久性共同保证，以及业务层的约束（如外键）。
*   **隔离性 (Isolation)**：由 **MVCC**（多版本并发控制）和 **锁机制** 保证。
*   **持久性 (Durability)**：由 **Redo Log** 保证。事务提交时，先写 Redo Log，再异步刷新到磁盘数据页（WAL 技术）。即使宕机，也能通过 Redo Log 恢复。

## 3.2 并发事务带来的问题

*   **脏读 (Dirty Read)**：读到了其他事务未提交的数据。
*   **不可重复读 (Non-Repeatable Read)**：同一个事务中，两次读取同一行数据，结果不一样（被其他事务 update/delete 了）。
*   **幻读 (Phantom Read)**：同一个事务中，两次查询同一个范围，结果集行数不一样（被其他事务 insert 了）。

## 3.3 四种隔离级别详解

| 隔离级别 | 脏读 | 不可重复读 | 幻读 | 实现机制 |
| :--- | :--- | :--- | :--- | :--- |
| **读未提交 (RU)** | √ | √ | √ | 不加锁，直接读最新数据 |
| **读已提交 (RC)** | × | √ | √ | MVCC (每次 Select 生成新 ReadView) |
| **可重复读 (RR)** | × | × | × (大部分) | MVCC (第一次 Select 生成 ReadView) + Next-Key Lock |
| **串行化 (Serializable)** | × | × | × | 强制加锁，读写串行 |

**注意**：MySQL 的默认隔离级别是 **RR (Repeatable Read)**，而 Oracle 的默认级别是 **RC (Read Committed)**。
