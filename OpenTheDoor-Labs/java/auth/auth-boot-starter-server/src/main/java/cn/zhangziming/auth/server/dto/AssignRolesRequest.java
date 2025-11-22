package cn.zhangziming.auth.server.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分配角色请求
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@ToString
public class AssignRolesRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}

