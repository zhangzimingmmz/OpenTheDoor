package cn.zhangziming.auth.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON工具类
 * 
 * @author zhangziming
 * @since 1.0.0
 */
@Slf4j
public class JsonUtil {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    static {
        // 注册Java 8时间模块
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }
    
    /**
     * 对象转JSON字符串
     * 
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("对象转JSON失败", e);
            return null;
        }
    }
    
    /**
     * JSON字符串转对象
     * 
     * @param json JSON字符串
     * @param clazz 目标类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.error("JSON转对象失败", e);
            return null;
        }
    }
    
    /**
     * JSON字符串转对象（支持泛型）
     * 
     * @param json JSON字符串
     * @param typeReference 类型引用
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("JSON转对象失败", e);
            return null;
        }
    }
}

