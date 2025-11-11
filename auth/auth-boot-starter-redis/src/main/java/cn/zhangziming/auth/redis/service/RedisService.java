package cn.zhangziming.auth.redis.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务接口
 * 
 * <p>封装常用的Redis操作
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface RedisService {

    // ========== String 操作 ==========

    /**
     * 设置缓存
     */
    void set(String key, Object value);

    /**
     * 设置缓存（带过期时间）
     */
    void set(String key, Object value, long timeout);

    /**
     * 设置缓存（带过期时间和时间单位）
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 获取缓存
     */
    Object get(String key);

    /**
     * 删除缓存
     */
    Boolean delete(String key);

    /**
     * 批量删除缓存
     */
    Long delete(Collection<String> keys);

    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long timeout);

    /**
     * 设置过期时间（带时间单位）
     */
    Boolean expire(String key, long timeout, TimeUnit unit);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    // ========== Hash 操作 ==========

    /**
     * Hash设置
     */
    void hSet(String key, String field, Object value);

    /**
     * Hash获取
     */
    Object hGet(String key, String field);

    /**
     * Hash批量设置
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * Hash获取所有
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * Hash删除
     */
    Long hDelete(String key, Object... fields);

    /**
     * Hash是否存在
     */
    Boolean hHasKey(String key, String field);

    // ========== Set 操作 ==========

    /**
     * Set添加
     */
    Long sAdd(String key, Object... values);

    /**
     * Set获取所有成员
     */
    Set<Object> sMembers(String key);

    /**
     * Set是否包含
     */
    Boolean sIsMember(String key, Object value);

    /**
     * Set大小
     */
    Long sSize(String key);

    /**
     * Set删除
     */
    Long sRemove(String key, Object... values);

    // ========== List 操作 ==========

    /**
     * List右推
     */
    Long lRightPush(String key, Object value);

    /**
     * List左推
     */
    Long lLeftPush(String key, Object value);

    /**
     * List右弹出
     */
    Object lRightPop(String key);

    /**
     * List左弹出
     */
    Object lLeftPop(String key);

    /**
     * List大小
     */
    Long lSize(String key);

    /**
     * List范围查询
     */
    List<Object> lRange(String key, long start, long end);

    // ========== 自增自减 ==========

    /**
     * 递增
     */
    Long increment(String key);

    /**
     * 递增指定值
     */
    Long increment(String key, long delta);

    /**
     * 递减
     */
    Long decrement(String key);

    /**
     * 递减指定值
     */
    Long decrement(String key, long delta);
}

