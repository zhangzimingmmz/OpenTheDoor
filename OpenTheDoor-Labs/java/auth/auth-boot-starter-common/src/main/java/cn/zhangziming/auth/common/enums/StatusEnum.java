package cn.zhangziming.auth.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 * 
 * @author zhangziming
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    
    /**
     * 禁用
     */
    DISABLED(0, "禁用"),
    
    /**
     * 正常
     */
    NORMAL(1, "正常");
    
    private final Integer code;
    private final String desc;
    
    /**
     * 根据code获取枚举
     */
    public static StatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

