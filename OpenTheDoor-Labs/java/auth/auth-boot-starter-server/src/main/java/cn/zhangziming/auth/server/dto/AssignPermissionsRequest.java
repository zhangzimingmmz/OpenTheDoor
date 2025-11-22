package cn.zhangziming.auth.server.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分配权限请求
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Data
@ToString
public class AssignPermissionsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID列表
     */
    @NotEmpty(message = "权限ID列表不能为空")
    private List<Long> permissionIds;
}

