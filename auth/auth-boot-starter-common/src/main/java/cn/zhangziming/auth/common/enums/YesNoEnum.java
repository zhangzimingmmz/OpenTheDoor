package cn.zhangziming.auth.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 * 
 * @author zhangziming
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {
    
    /**
     * 否
     */
    NO(0, "否"),
    
    /**
     * 是
     */
    YES(1, "是");
    
    private final Integer code;
    private final String desc;
    
    /**
     * 根据code获取枚举
     */
    public static YesNoEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (YesNoEnum yesNo : values()) {
            if (yesNo.getCode().equals(code)) {
                return yesNo;
            }
        }
        return null;
    }
    
    /**
     * 判断是否为"是"
     */
    public static boolean isYes(Integer code) {
        return YES.getCode().equals(code);
    }
    
    /**
     * 判断是否为"否"
     */
    public static boolean isNo(Integer code) {
        return NO.getCode().equals(code);
    }
}

