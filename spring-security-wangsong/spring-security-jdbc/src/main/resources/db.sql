SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `name` varchar(20) DEFAULT NULL,
                                  `permit` varchar(50) DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, '删除用户', 'user:delete');
INSERT INTO `sys_permission` VALUES (2, '展示用户', 'user');
INSERT INTO `sys_permission` VALUES (3, '添加用户', 'user:add');
INSERT INTO `sys_permission` VALUES (4, '编辑用户', 'user:edit');
INSERT INTO `sys_permission` VALUES (5, '导出用户', 'user:export');
INSERT INTO `sys_permission` VALUES (6, '部门展示', 'dept');
INSERT INTO `sys_permission` VALUES (7, '删除部门', 'dept:delete');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(20) NOT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'admin', '系统管理员');
INSERT INTO `sys_role` VALUES (2, 'finance', '财务管理');
INSERT INTO `sys_role` VALUES (3, 'administration ', '行政管理');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
                                       `role_id` int(11) NOT NULL,
                                       `permission_id` int(11) NOT NULL,
                                       PRIMARY KEY (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (2, 2);
INSERT INTO `sys_role_permission` VALUES (2, 5);
INSERT INTO `sys_role_permission` VALUES (2, 6);
INSERT INTO `sys_role_permission` VALUES (3, 2);
INSERT INTO `sys_role_permission` VALUES (3, 6);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` int(11) NOT NULL,
                            `username` char(20) DEFAULT NULL,
                            `password` char(100) DEFAULT NULL,
                            `mobile` char(15) DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'jack', '$2a$10$A1vqzfx5z4QpkO4fEvHlP.7KjaiilmNtS4HjzVoCAbg3M0Up81zfK', '13268050688');
INSERT INTO `sys_user` VALUES (2, 'rose', '$2a$10$5V1J3sJ3MEGa0HmP/YXx5.2IPIefVyh.6NOOnLwkQLDn5frKDFGam', '13268050688');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `user_id` int(11) NOT NULL,
                                 `role_id` int(11) NOT NULL,
                                 PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (2, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;