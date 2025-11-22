-- =========================================
-- Auth Boot Starter 测试数据脚本
-- 版本: 1.0.0
-- 说明: 用于开发和测试环境的示例数据
-- =========================================

USE `auth_boot`;

-- =========================================
-- 1. 测试租户
-- =========================================
INSERT INTO `sys_tenant` (`tenant_code`, `tenant_name`, `contact_name`, `contact_phone`, `contact_email`, `status`) 
VALUES 
('TENANT_TEST', '测试租户', '张三', '13800138000', 'zhangsan@test.com', 1),
('TENANT_DEMO', '演示租户', '李四', '13800138001', 'lisi@demo.com', 1);

-- =========================================
-- 2. 测试用户 (密码统一为: 123456)
-- =========================================
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `phone`, `status`, `user_type`, `tenant_id`) 
VALUES 
-- 默认租户用户
('zhangsan', '$2a$10$r0dBEGO9LXRO9DKVxzPRNeFOyPL9PkYVE.Z/XLnDlN2KR.XVb0pHq', '张三', 'zhangsan@example.com', '13800000001', 1, 1, 'DEFAULT'),
('lisi', '$2a$10$r0dBEGO9LXRO9DKVxzPRNeFOyPL9PkYVE.Z/XLnDlN2KR.XVb0pHq', '李四', 'lisi@example.com', '13800000002', 1, 1, 'DEFAULT'),
('wangwu', '$2a$10$r0dBEGO9LXRO9DKVxzPRNeFOyPL9PkYVE.Z/XLnDlN2KR.XVb0pHq', '王五', 'wangwu@example.com', '13800000003', 1, 2, 'DEFAULT'),
-- 测试租户用户
('test_user1', '$2a$10$r0dBEGO9LXRO9DKVxzPRNeFOyPL9PkYVE.Z/XLnDlN2KR.XVb0pHq', '测试用户1', 'test1@test.com', '13900000001', 1, 1, 'TENANT_TEST'),
('test_user2', '$2a$10$r0dBEGO9LXRO9DKVxzPRNeFOyPL9PkYVE.Z/XLnDlN2KR.XVb0pHq', '测试用户2', 'test2@test.com', '13900000002', 1, 1, 'TENANT_TEST');

-- =========================================
-- 3. 测试角色
-- =========================================
INSERT INTO `sys_role` (`role_code`, `role_name`, `role_level`, `data_scope`, `tenant_id`, `remark`) 
VALUES 
('DEPT_MANAGER', '部门经理', 3, 2, 'DEFAULT', '部门经理角色'),
('OPERATOR', '操作员', 4, 4, 'DEFAULT', '操作员角色'),
('VIEWER', '查看者', 5, 4, 'DEFAULT', '只读权限');

-- =========================================
-- 4. 用户角色关联
-- =========================================
INSERT INTO `sys_user_role` (`user_id`, `role_id`, `tenant_id`) 
SELECT u.id, r.id, u.tenant_id
FROM `sys_user` u, `sys_role` r
WHERE u.username = 'zhangsan' AND r.role_code = 'ADMIN' AND u.tenant_id = 'DEFAULT'
UNION ALL
SELECT u.id, r.id, u.tenant_id
FROM `sys_user` u, `sys_role` r
WHERE u.username = 'lisi' AND r.role_code = 'USER' AND u.tenant_id = 'DEFAULT'
UNION ALL
SELECT u.id, r.id, u.tenant_id
FROM `sys_user` u, `sys_role` r
WHERE u.username = 'wangwu' AND r.role_code = 'DEPT_MANAGER' AND u.tenant_id = 'DEFAULT';

-- =========================================
-- 5. 测试菜单
-- =========================================
INSERT INTO `sys_menu` (`menu_name`, `menu_code`, `parent_id`, `menu_type`, `path`, `component`, `permission`, `icon`, `sort_order`, `tenant_id`) 
VALUES 
-- 一级菜单
('系统管理', 'system', 0, 1, '/system', NULL, NULL, 'system', 1, 'DEFAULT'),
('用户管理', 'user_mgmt', 0, 1, '/users', NULL, NULL, 'user', 2, 'DEFAULT'),
-- 二级菜单
('用户列表', 'user_list', 2, 2, '/users/list', 'user/UserList', 'user:read', 'list', 1, 'DEFAULT'),
('角色管理', 'role_mgmt', 1, 2, '/system/role', 'system/Role', 'role:read', 'role', 1, 'DEFAULT'),
('菜单管理', 'menu_mgmt', 1, 2, '/system/menu', 'system/Menu', 'menu:read', 'menu', 2, 'DEFAULT'),
-- 按钮权限
('用户新增', 'user_add', 3, 3, NULL, NULL, 'user:write', NULL, 1, 'DEFAULT'),
('用户编辑', 'user_edit', 3, 3, NULL, NULL, 'user:write', NULL, 2, 'DEFAULT'),
('用户删除', 'user_delete', 3, 3, NULL, NULL, 'user:delete', NULL, 3, 'DEFAULT');

-- =========================================
-- 6. 测试API接口
-- =========================================
INSERT INTO `sys_api` (`api_name`, `api_code`, `api_path`, `api_method`, `api_category`, `tenant_id`) 
VALUES 
('查询用户列表', 'user_list', '/api/user/list', 'GET', '用户管理', 'DEFAULT'),
('查询用户详情', 'user_detail', '/api/user/{id}', 'GET', '用户管理', 'DEFAULT'),
('创建用户', 'user_create', '/api/user', 'POST', '用户管理', 'DEFAULT'),
('更新用户', 'user_update', '/api/user/{id}', 'PUT', '用户管理', 'DEFAULT'),
('删除用户', 'user_delete', '/api/user/{id}', 'DELETE', '用户管理', 'DEFAULT'),
('查询角色列表', 'role_list', '/api/role/list', 'GET', '角色管理', 'DEFAULT'),
('查询权限树', 'permission_tree', '/api/permission/tree', 'GET', '权限管理', 'DEFAULT');

-- =========================================
-- 7. 角色权限关联（示例）
-- =========================================
-- 为USER角色分配基本权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`, `tenant_id`)
SELECT r.id, p.id, r.tenant_id
FROM `sys_role` r, `sys_permission` p
WHERE r.role_code = 'USER' AND p.permission_code = 'user:read' AND r.tenant_id = 'DEFAULT';

-- 为ADMIN角色分配更多权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`, `tenant_id`)
SELECT r.id, p.id, r.tenant_id
FROM `sys_role` r, `sys_permission` p
WHERE r.role_code = 'ADMIN' 
  AND p.permission_code IN ('user:read', 'user:write', 'role:read', 'role:write')
  AND r.tenant_id = 'DEFAULT';

-- =========================================
-- 8. 测试登录日志
-- =========================================
INSERT INTO `sys_login_log` (`username`, `user_id`, `tenant_id`, `login_type`, `ip_address`, `location`, `browser`, `os`, `status`, `message`) 
SELECT 
    u.username,
    u.id,
    u.tenant_id,
    1,
    '192.168.1.100',
    '中国-北京',
    'Chrome 120',
    'Windows 11',
    1,
    '登录成功'
FROM `sys_user` u
WHERE u.username IN ('admin', 'zhangsan', 'lisi')
LIMIT 10;

-- =========================================
-- 9. 租户配置示例
-- =========================================
INSERT INTO `sys_tenant_config` (`tenant_id`, `config_key`, `config_value`, `config_type`, `remark`) 
VALUES 
('DEFAULT', 'theme', 'light', 'string', '主题配置'),
('DEFAULT', 'logo', '/assets/logo.png', 'string', 'Logo地址'),
('DEFAULT', 'max_login_attempts', '5', 'number', '最大登录尝试次数'),
('DEFAULT', 'session_timeout', '3600', 'number', '会话超时时间(秒)');

-- =========================================
-- 测试数据插入完成
-- =========================================
SELECT '测试数据插入完成！' AS message;
SELECT CONCAT('测试用户数量: ', COUNT(*)) AS user_count FROM `sys_user` WHERE tenant_id IN ('DEFAULT', 'TENANT_TEST');
SELECT CONCAT('测试角色数量: ', COUNT(*)) AS role_count FROM `sys_role`;
SELECT CONCAT('测试菜单数量: ', COUNT(*)) AS menu_count FROM `sys_menu`;

