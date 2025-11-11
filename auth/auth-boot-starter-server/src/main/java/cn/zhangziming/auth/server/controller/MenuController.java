package cn.zhangziming.auth.server.controller;

import cn.zhangziming.auth.common.model.Result;
import cn.zhangziming.auth.server.dto.MenuDTO;
import cn.zhangziming.auth.server.service.IMenuService;
import cn.zhangziming.auth.server.vo.MenuVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author zhangziming
 * @since 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final IMenuService menuService;

    /**
     * 查询所有菜单（平铺列表）
     */
    @GetMapping("/list")
    public Result<List<MenuVO>> listAllMenus() {
        List<MenuVO> menus = menuService.listAllMenus();
        return Result.success(menus);
    }

    /**
     * 查询菜单树
     */
    @GetMapping("/tree")
    public Result<List<MenuVO>> getMenuTree() {
        List<MenuVO> tree = menuService.getMenuTree();
        return Result.success(tree);
    }

    /**
     * 查询用户菜单树
     */
    @GetMapping("/user/{userId}")
    public Result<List<MenuVO>> getUserMenuTree(@PathVariable Long userId) {
        List<MenuVO> tree = menuService.getUserMenuTree(userId);
        return Result.success(tree);
    }

    /**
     * 查询菜单详情
     */
    @GetMapping("/{id}")
    public Result<MenuVO> getMenu(@PathVariable Long id) {
        MenuVO menu = menuService.getMenuById(id);
        return Result.success(menu);
    }

    /**
     * 创建菜单
     */
    @PostMapping("/create")
    public Result<Long> createMenu(@RequestBody MenuDTO menuDTO) {
        log.info("收到创建菜单请求: {}", menuDTO);
        Long menuId = menuService.saveMenu(menuDTO);
        return Result.success(menuId);
    }

    /**
     * 更新菜单
     */
    @PutMapping("/{id}")
    public Result<Void> updateMenu(@PathVariable Long id, 
                                    @RequestBody MenuDTO menuDTO) {
        log.info("收到更新菜单请求: id={}, menuDTO={}", id, menuDTO);
        menuDTO.setId(id);
        menuService.updateMenu(menuDTO);
        return Result.success();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return Result.success();
    }

    /**
     * 查询子菜单
     */
    @GetMapping("/children/{parentId}")
    public Result<List<MenuVO>> getChildrenMenus(@PathVariable Long parentId) {
        List<MenuVO> children = menuService.getChildrenMenus(parentId);
        return Result.success(children);
    }
}

