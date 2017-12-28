/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-22 16:59:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_icon` varchar(255) DEFAULT NULL COMMENT 't头像地址',
  `gender` tinyint(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `age` smallint(6) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `blog_year` smallint(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `occupation` varchar(255) DEFAULT NULL COMMENT '职业',
  `personal_signature` text COMMENT '个性签名',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `sign` text COMMENT '标签',
  `birth` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_account
-- ----------------------------

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `support` varchar(255) DEFAULT NULL,
  `dislike` varchar(255) DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `visit_times` datetime DEFAULT NULL,
  `top` varchar(255) DEFAULT NULL,
  `to_top` varchar(255) DEFAULT NULL,
  `assortment_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_article
-- ----------------------------

-- ----------------------------
-- Table structure for tb_assortment
-- ----------------------------
DROP TABLE IF EXISTS `tb_assortment`;
CREATE TABLE `tb_assortment` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `article_num` int(11) DEFAULT NULL,
  `assortment_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_assortment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_chat
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat`;
CREATE TABLE `tb_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `current_account_id` int(11) NOT NULL,
  `from_account_id` int(11) NOT NULL,
  `to_account_id` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `is_delete` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_chat
-- ----------------------------

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` text,
  `source` varchar(255) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  `picture_group_id` int(11) DEFAULT NULL,
  `current_account_id` int(255) DEFAULT NULL,
  `from_account_id` int(11) DEFAULT NULL,
  `to_account_id` int(11) DEFAULT NULL,
  `replay_comment_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tb_conference
-- ----------------------------
DROP TABLE IF EXISTS `tb_conference`;
CREATE TABLE `tb_conference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL COMMENT '会议成员 tb_account',
  `sponsor_id` int(11) DEFAULT NULL COMMENT '会议发起人 tb_account',
  `theme` text COMMENT '会议主题',
  `details` text,
  `is_email` tinyint(255) DEFAULT '0' COMMENT '是否发送邮件 0：不发送，1发松',
  `place` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_conference
-- ----------------------------

-- ----------------------------
-- Table structure for tb_conference_flow
-- ----------------------------
DROP TABLE IF EXISTS `tb_conference_flow`;
CREATE TABLE `tb_conference_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `conference_id` int(11) DEFAULT NULL COMMENT '会议id',
  `describe` varchar(255) DEFAULT NULL,
  `record` varchar(255) DEFAULT NULL COMMENT '会议记录',
  `recorder_id` int(11) DEFAULT NULL COMMENT '会议记录人 tb_account',
  `is_delete` varchar(255) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_conference_flow
-- ----------------------------

-- ----------------------------
-- Table structure for tb_conference_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_conference_function`;
CREATE TABLE `tb_conference_function` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `function_id` int(11) DEFAULT NULL,
  `conference_id` int(11) DEFAULT NULL,
  `sponsor_id` int(11) DEFAULT NULL,
  `memebr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_conference_function
-- ----------------------------

-- ----------------------------
-- Table structure for tb_discussion_group_member
-- ----------------------------
DROP TABLE IF EXISTS `tb_discussion_group_member`;
CREATE TABLE `tb_discussion_group_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `discussion_group_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL COMMENT '对应account_id',
  `current_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_discussion_group_member
-- ----------------------------

-- ----------------------------
-- Table structure for tb_disscussion_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_disscussion_group`;
CREATE TABLE `tb_disscussion_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `discussion_group_name` varchar(255) DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_disscussion_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_email
-- ----------------------------
DROP TABLE IF EXISTS `tb_email`;
CREATE TABLE `tb_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `receive_account_id` int(11) DEFAULT NULL,
  `is_delete` tinyint(255) NOT NULL DEFAULT '0' COMMENT '0 否 1是',
  `pushMessage` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_email
-- ----------------------------

-- ----------------------------
-- Table structure for tb_exception
-- ----------------------------
DROP TABLE IF EXISTS `tb_exception`;
CREATE TABLE `tb_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `exception` text,
  `method` varchar(255) DEFAULT NULL,
  `code` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_exception
-- ----------------------------

-- ----------------------------
-- Table structure for tb_friend
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend`;
CREATE TABLE `tb_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL COMMENT '对应 tb_account',
  `special_attention` varchar(255) DEFAULT NULL COMMENT '特别关注',
  `is_defriend` tinyint(255) DEFAULT '0' COMMENT '是否拉黑 0：否，1是',
  `is_delete` tinyint(255) DEFAULT '0' COMMENT '是否删除 0 否，1是',
  `is_agree` tinyint(255) DEFAULT '0' COMMENT '是否同意申请：0 申请 1同意，2拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_friend
-- ----------------------------

-- ----------------------------
-- Table structure for tb_friend_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_function`;
CREATE TABLE `tb_friend_function` (
  `id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `current_account_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `function_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_friend_function
-- ----------------------------

-- ----------------------------
-- Table structure for tb_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_function`;
CREATE TABLE `tb_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `authority` varchar(255) DEFAULT NULL COMMENT '权限',
  `describe` varchar(255) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_function
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `group_name` varchar(255) DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group_chat
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_chat`;
CREATE TABLE `tb_group_chat` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `discussion_group_id` int(11) DEFAULT NULL COMMENT '讨论组id',
  `speech_id` int(11) DEFAULT NULL COMMENT '发言人 tb_account',
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_chat
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group_friend
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_friend`;
CREATE TABLE `tb_group_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `current_account_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL COMMENT '对应表tb_group',
  `friend_id` int(11) DEFAULT NULL COMMENT '对应account_id',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_friend
-- ----------------------------

-- ----------------------------
-- Table structure for tb_group_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_function`;
CREATE TABLE `tb_group_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `current_account_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `function_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_group_function
-- ----------------------------

-- ----------------------------
-- Table structure for tb_message_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_info`;
CREATE TABLE `tb_message_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `push_message` varchar(255) DEFAULT NULL,
  `from_account_id` int(11) DEFAULT NULL,
  `to_account_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_message_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_picture
-- ----------------------------
DROP TABLE IF EXISTS `tb_picture`;
CREATE TABLE `tb_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture_src` varchar(255) DEFAULT NULL,
  `picture_describe` text,
  `picture_name` varchar(255) DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_picture
-- ----------------------------

-- ----------------------------
-- Table structure for tb_picture_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_picture_group`;
CREATE TABLE `tb_picture_group` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture_group_name` varchar(255) DEFAULT NULL,
  `group_describe` varchar(255) DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_picture_group
-- ----------------------------

-- ----------------------------
-- Table structure for tb_picture_group_function
-- ----------------------------
DROP TABLE IF EXISTS `tb_picture_group_function`;
CREATE TABLE `tb_picture_group_function` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_account_id` int(11) DEFAULT NULL,
  `function_id` int(11) DEFAULT NULL,
  `picture_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_picture_group_function
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
