/*
Navicat MySQL Data Transfer

Source Server         : localhost_3307
Source Server Version : 50505
Source Host           : localhost:3307
Source Database       : ucenter

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-05-22 13:32:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `token_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `token_create_time` datetime NOT NULL,
  `token_update_time` datetime NOT NULL,
  `token_expire_time` datetime NOT NULL,
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of token
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(64) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `user_email` varchar(255) DEFAULT NULL,
  `user_create_time` datetime NOT NULL,
  `user_update_time` datetime NOT NULL,
  `user_state` tinyint(4) NOT NULL COMMENT '1：可用 2：不可用 3：逻辑上删除',
  `user_group_id` varchar(64) DEFAULT NULL,
  `user_real_name` varchar(255) DEFAULT NULL,
  `user_sex` tinyint(4) DEFAULT NULL COMMENT '1：女 2：男',
  `user_age` tinyint(4) DEFAULT NULL,
  `user_group_top_id` varchar(64) DEFAULT NULL,
  `user_role` tinyint(4) NOT NULL COMMENT '1：普通用户2：组织管理员3：用户中心管理员',
  `user_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `user_phone` (`user_phone`) USING BTREE,
  UNIQUE KEY `user_email` (`user_email`) USING BTREE,
  KEY `user_group_id` (`user_group_id`),
  KEY `user_group_top_id` (`user_group_top_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`user_group_id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`user_group_top_id`) REFERENCES `user_group` (`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', '123456', null, null, '2017-03-29 09:23:18', '2017-03-29 09:23:22', '1', null, null, null, null, null, '3', null);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `user_group_id` varchar(64) NOT NULL,
  `user_group_name` varchar(255) NOT NULL,
  `user_group_parent_id` varchar(64) DEFAULT NULL COMMENT '空 ：代表顶级',
  `user_group_create_time` datetime NOT NULL,
  `user_group_update_time` datetime NOT NULL,
  `user_group_state` tinyint(4) NOT NULL COMMENT '1：可用 2：不可用 3：逻辑上删除',
  `user_group_top_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`user_group_id`),
  UNIQUE KEY `user_group_name` (`user_group_name`,`user_group_top_id`) USING BTREE,
  KEY `user_group_parent_id` (`user_group_parent_id`),
  KEY `user_group_top_id` (`user_group_top_id`),
  CONSTRAINT `user_group_ibfk_1` FOREIGN KEY (`user_group_parent_id`) REFERENCES `user_group` (`user_group_id`),
  CONSTRAINT `user_group_ibfk_2` FOREIGN KEY (`user_group_top_id`) REFERENCES `user_group` (`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_group
-- ----------------------------
