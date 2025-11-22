package cn.zhangziming.auth.server.service;

import cn.zhangziming.auth.server.dto.MenuDTO;
import cn.zhangziming.auth.server.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author zhangziming
 * @since 2024-10-29
 */
public interface IMenuService {

    /**
     * 创建菜单
     *
     * @param menuDTO 菜单DTO
     * @return 菜单ID
     */
    Long saveMenu(MenuDTO menuDTO);

    /**
     * 更新菜单
     *
     * @param menuDTO 菜单DTO
     */
    void updateMenu(MenuDTO menuDTO);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    void deleteMenu(Long menuId);

    /**
     * 根据ID查询菜单
     *
     * @param menuId 菜单ID
     * @return 菜单VO
     */
    MenuVO getMenuById(Long menuId);

    /**
     * 查询所有菜单（平铺列表）
     *
     * @return 菜单列表
     */
    List<MenuVO> listAllMenus();

    /**
     * 查询菜单树
     *
     * @return 菜单树
     */
    List<MenuVO> getMenuTree();

    /**
     * 查询用户菜单树
     *
     * @param userId 用户ID
     * @return 用户菜单树
     */
    List<MenuVO> getUserMenuTree(Long userId);

    /**
     * 根据父菜单ID查询子菜单
     *
     * @param parentId 父菜单ID
     * @return 子菜单列表
     */
    List<MenuVO> getChildrenMenus(Long parentId);
}

