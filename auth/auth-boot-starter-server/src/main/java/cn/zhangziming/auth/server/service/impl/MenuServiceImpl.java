package cn.zhangziming.auth.server.service.impl;

import cn.zhangziming.auth.common.constant.ErrorCode;
import cn.zhangziming.auth.common.exception.BusinessException;
import cn.zhangziming.auth.server.dto.MenuDTO;
import cn.zhangziming.auth.server.entity.SysMenu;
import cn.zhangziming.auth.server.mapper.SysMenuMapper;
import cn.zhangziming.auth.server.service.IMenuService;
import cn.zhangziming.auth.server.service.IPermissionService;
import cn.zhangziming.auth.server.vo.MenuVO;
import cn.zhangziming.auth.server.vo.PermissionVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService {

    private final SysMenuMapper menuMapper;
    private final IPermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveMenu(MenuDTO menuDTO) {
        log.info("创建菜单: menuCode={}", menuDTO.getMenuCode());

        // 1. 检查菜单编码是否存在
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getMenuCode, menuDTO.getMenuCode());
        if (menuMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "菜单编码已存在");
        }

        // 2. 检查父菜单是否存在
        if (menuDTO.getParentId() != null && menuDTO.getParentId() > 0) {
            SysMenu parentMenu = menuMapper.selectById(menuDTO.getParentId());
            if (parentMenu == null) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "父菜单不存在");
            }
        }

        // 3. 创建菜单
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(menuDTO, menu);

        // 设置默认值
        if (menu.getStatus() == null) {
            menu.setStatus(1); // 默认正常
        }
        if (menu.getVisible() == null) {
            menu.setVisible(1); // 默认显示
        }
        if (menu.getSortOrder() == null) {
            menu.setSortOrder(0);
        }

        menuMapper.insert(menu);

        log.info("创建菜单成功: menuId={}, menuCode={}", menu.getId(), menu.getMenuCode());
        return menu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuDTO menuDTO) {
        log.info("更新菜单: menuId={}", menuDTO.getId());

        // 1. 检查菜单是否存在
        SysMenu existMenu = menuMapper.selectById(menuDTO.getId());
        if (existMenu == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "菜单不存在");
        }

        // 2. 检查菜单编码是否重复（排除自己）
        if (!existMenu.getMenuCode().equals(menuDTO.getMenuCode())) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getMenuCode, menuDTO.getMenuCode());
            wrapper.ne(SysMenu::getId, menuDTO.getId());
            if (menuMapper.selectCount(wrapper) > 0) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "菜单编码已存在");
            }
        }

        // 3. 检查是否设置自己为父菜单
        if (menuDTO.getParentId() != null && menuDTO.getParentId().equals(menuDTO.getId())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "不能将自己设置为父菜单");
        }

        // 4. 更新菜单
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(menuDTO, menu);
        menuMapper.updateById(menu);

        log.info("更新菜单成功: menuId={}", menu.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        log.info("删除菜单: menuId={}", menuId);

        SysMenu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "菜单不存在");
        }

        // 1. 检查是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, menuId);
        if (menuMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "该菜单存在子菜单，无法删除");
        }

        // 2. 删除菜单（逻辑删除）
        menuMapper.deleteById(menuId);

        log.info("删除菜单成功: menuId={}", menuId);
    }

    @Override
    public MenuVO getMenuById(Long menuId) {
        SysMenu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "菜单不存在");
        }
        return convertToVO(menu);
    }

    @Override
    public List<MenuVO> listAllMenus() {
        List<SysMenu> menus = menuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getStatus, 1)
                        .orderByAsc(SysMenu::getSortOrder)
        );
        return menus.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuVO> getMenuTree() {
        // 1. 查询所有菜单
        List<MenuVO> allMenus = listAllMenus();

        // 2. 构建菜单树
        return buildMenuTree(allMenus, 0L);
    }

    @Override
    public List<MenuVO> getUserMenuTree(Long userId) {
        // 1. 查询用户权限
        Set<String> permissionCodes = permissionService.getUserPermissionCodes(userId);

        // 2. 查询所有菜单
        List<MenuVO> allMenus = listAllMenus();

        // 3. 过滤用户有权限的菜单
        List<MenuVO> userMenus = allMenus.stream()
                .filter(menu -> hasMenuPermission(menu, permissionCodes))
                .collect(Collectors.toList());

        // 4. 构建菜单树
        return buildMenuTree(userMenus, 0L);
    }

    @Override
    public List<MenuVO> getChildrenMenus(Long parentId) {
        List<SysMenu> menus = menuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getParentId, parentId)
                        .eq(SysMenu::getStatus, 1)
                        .orderByAsc(SysMenu::getSortOrder)
        );
        return menus.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 构建菜单树
     *
     * @param menuList 菜单列表
     * @param parentId 父菜单ID
     * @return 菜单树
     */
    private List<MenuVO> buildMenuTree(List<MenuVO> menuList, Long parentId) {
        if (CollectionUtils.isEmpty(menuList)) {
            return new ArrayList<>();
        }

        List<MenuVO> tree = new ArrayList<>();

        for (MenuVO menu : menuList) {
            if (parentId.equals(menu.getParentId())) {
                // 递归查找子菜单
                List<MenuVO> children = buildMenuTree(menuList, menu.getMenuId());
                if (!CollectionUtils.isEmpty(children)) {
                    menu.setChildren(children);
                }
                tree.add(menu);
            }
        }

        return tree;
    }

    /**
     * 检查菜单权限
     *
     * @param menu            菜单
     * @param permissionCodes 权限编码集合
     * @return 是否有权限
     */
    private boolean hasMenuPermission(MenuVO menu, Set<String> permissionCodes) {
        // 如果菜单没有设置权限标识，默认可见
        if (menu.getPermission() == null || menu.getPermission().isEmpty()) {
            return true;
        }

        // 检查用户是否拥有该菜单的权限
        return permissionCodes.contains(menu.getPermission());
    }

    /**
     * 转换为VO
     */
    private MenuVO convertToVO(SysMenu menu) {
        MenuVO vo = new MenuVO();
        BeanUtils.copyProperties(menu, vo);
        vo.setMenuId(menu.getId());
        return vo;
    }
}

