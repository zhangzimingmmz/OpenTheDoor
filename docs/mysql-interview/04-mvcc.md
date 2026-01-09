# 4. [并发神器] MVCC 多版本并发控制

MVCC (Multi-Version Concurrency Control) 是 InnoDB 实现高并发的核心技术。它使得读写操作互不阻塞：写操作加锁，读操作不加锁（读历史版本）。

## 4.1 什么是 MVCC？

简单来说，MVCC 就是维护了数据的多个版本。当一个事务在修改数据时，其他事务可以读取该数据的旧版本，从而实现读写并行。

## 4.2 底层实现：隐藏列与 Undo Log 版本链

InnoDB 在每行数据后面默认添加了三个隐藏列：
1.  **DB_TRX_ID**：最近修改（或插入）该行数据的事务 ID。
2.  **DB_ROLL_PTR**：回滚指针，指向 Undo Log 中该行的上一个版本。
3.  **DB_ROW_ID**：隐藏主键（如果没有显式主键）。

**版本链**：
通过 `DB_ROLL_PTR` 指针，将 Undo Log 中的历史版本串连起来，形成一个链表。最新的数据在表里，旧的数据在 Undo Log 里。

## 4.3 Read View 工作机制

Read View（读视图）是事务进行快照读时产生的读视图。它包含四个核心字段：
*   `m_ids`：生成 Read View 时，当前系统中活跃（未提交）的事务 ID 列表。
*   `min_trx_id`：活跃事务中最小的事务 ID。
*   `max_trx_id`：系统应该分配给下一个事务的 ID。
*   `creator_trx_id`：生成该 Read View 的事务 ID。

**可见性判断规则**（对于某行数据）：
1.  如果 `trx_id == creator_trx_id`：数据是自己改的，**可见**。
2.  如果 `trx_id < min_trx_id`：数据在生成 Read View 前已提交，**可见**。
3.  如果 `trx_id >= max_trx_id`：数据是在生成 Read View 后才开启的事务修改的，**不可见**。
4.  如果 `min_trx_id <= trx_id < max_trx_id`：
    *   如果 `trx_id` 在 `m_ids` 集合中：说明事务还没提交，**不可见**。
    *   如果 `trx_id` 不在 `m_ids` 集合中：说明事务已提交，**可见**。

## 4.4 RC 与 RR 级别下 MVCC 的区别

这是面试中的**超级高频考点**。

*   **读已提交 (RC)**：**每次执行 SELECT 语句时**，都会重新生成一个新的 Read View。
    *   结果：能读到其他事务刚刚提交的数据（不可重复读）。
*   **可重复读 (RR)**：**只在事务第一次执行 SELECT 语句时**，生成一个 Read View，后续的 SELECT 都复用这个 Read View。
    *   结果：无论其他事务怎么修改提交，我看的数据永远是第一次查询时的快照（可重复读）。
