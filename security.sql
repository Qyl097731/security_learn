/*
Navicat MySQL Data Transfer

Source Server         : qiuyiliang
Source Server Version : 80029
Source Host           : localhost:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 80029
File Encoding         : 65001

Date: 2022-09-02 17:14:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acl_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_permission`;
CREATE TABLE `acl_permission` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';

-- ----------------------------
-- Records of acl_permission
-- ----------------------------
INSERT INTO `acl_permission` VALUES ('1', '0', '全部数据', '0', null, null, null, null, null, '0', '2019-11-15 17:13:06', '2019-11-15 17:13:06');
INSERT INTO `acl_permission` VALUES ('2', '1', '用户管理', '1', 'null', '/user', null, null, null, '0', '2022-07-12 13:36:11', '2022-07-12 13:36:13');
INSERT INTO `acl_permission` VALUES ('3', '1', '商品管理', '1', 'null', '/goods', null, null, null, '0', '2022-07-29 16:57:48', '2022-07-29 16:57:52');
INSERT INTO `acl_permission` VALUES ('4', '2', '用户添加', '1', 'null', '/user/add', null, null, null, '0', '2022-08-03 14:57:54', '2022-08-03 14:57:56');
INSERT INTO `acl_permission` VALUES ('5', '4', '添加', '2', 'user.add', '', null, null, null, '0', null, null);

-- ----------------------------
-- Table structure for acl_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_cn` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of acl_role
-- ----------------------------
INSERT INTO `acl_role` VALUES ('1', 'NSEC_system', '超级管理员', null, '0', '2022-08-23 13:09:32', '2022-08-23 10:27:18');
INSERT INTO `acl_role` VALUES ('2', 'NSEC_goods', '商品管理员', null, '0', '2022-07-12 13:31:22', '2022-07-12 13:31:25');

-- ----------------------------
-- Table structure for acl_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission` (
  `id` char(19) NOT NULL DEFAULT '',
  `role_id` char(19) NOT NULL DEFAULT '',
  `permission_id` char(19) NOT NULL DEFAULT '',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色权限';

-- ----------------------------
-- Records of acl_role_permission
-- ----------------------------
INSERT INTO `acl_role_permission` VALUES ('1', '1', '1', '0', '2022-07-12 13:36:28', '2022-07-12 13:36:31');
INSERT INTO `acl_role_permission` VALUES ('2', '1', '2', '0', '2022-07-12 13:36:35', '2022-07-12 13:36:38');
INSERT INTO `acl_role_permission` VALUES ('3', '2', '1', '0', '2022-07-12 13:36:48', '2022-07-12 13:36:51');
INSERT INTO `acl_role_permission` VALUES ('4', '1', '3', '0', '2022-07-29 10:20:48', '2022-07-29 10:20:50');
INSERT INTO `acl_role_permission` VALUES ('5', '2', '3', '0', '2022-07-29 10:21:08', '2022-07-29 10:21:11');
INSERT INTO `acl_role_permission` VALUES ('6', '1', '4', '0', '2022-08-03 15:00:49', '2022-08-03 15:00:51');
INSERT INTO `acl_role_permission` VALUES ('7', '2', '4', '0', '2022-08-03 15:00:59', '2022-08-03 15:01:01');

-- ----------------------------
-- Table structure for acl_user
-- ----------------------------
DROP TABLE IF EXISTS `acl_user`;
CREATE TABLE `acl_user` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '微信openid',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `salt` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `token` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of acl_user
-- ----------------------------
INSERT INTO `acl_user` VALUES ('1', 'admin', '$2a$10$ULD6h5ebKWaBKkpl0fvaYOlhLX/DUbww0VBV3XaHTS/9k4CZ6C5Tm', null, null, null, '0', '2022-07-29 05:45:30', '2022-07-29 05:45:30');
INSERT INTO `acl_user` VALUES ('2', 'test', '$2a$10$ULD6h5ebKWaBKkpl0fvaYOlhLX/DUbww0VBV3XaHTS/9k4CZ6C5Tm', 'test', null, null, '0', '2019-11-01 16:36:07', '2019-11-01 16:40:08');
INSERT INTO `acl_user` VALUES ('3', 'root', '$2a$10$ULD6h5ebKWaBKkpl0fvaYOlhLX/DUbww0VBV3XaHTS/9k4CZ6C5Tm', null, null, null, '0', '2022-07-29 09:23:23', '2022-07-29 09:23:23');

-- ----------------------------
-- Table structure for acl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of acl_user_role
-- ----------------------------
INSERT INTO `acl_user_role` VALUES ('1', '1', '1', '0', '2019-11-11 13:09:53', '2019-11-11 13:09:53');
INSERT INTO `acl_user_role` VALUES ('2', '2', '2', '0', '2022-07-12 13:32:35', '2022-07-12 13:32:37');
SET FOREIGN_KEY_CHECKS=1;
