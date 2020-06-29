/*
 Navicat Premium Data Transfer

 Source Server         : 182.92.102.51
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 182.92.102.51:3306
 Source Schema         : dataspider

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 15/08/2018 18:38:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agency
-- ----------------------------
DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `AGEINSORGCODE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `AGEINSNAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `AGEINSTYPECODE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `AGEINSTYPENAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ECOTYPECODE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ECOTYPENAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `AREACODE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `AREANAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADMDIVCODE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ADMDIVNAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `OPEADD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ZIP` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `TEL` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `FAX` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `INDACOMMREGADD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REGFUNAMOUT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MAJORSCOPE` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `REGDATE` datetime DEFAULT NULL,
  `REMARK` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUBUSERCODE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUBUSERNAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUBSTATUS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for extracting_log
-- ----------------------------
DROP TABLE IF EXISTS `extracting_log`;
CREATE TABLE `extracting_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agency_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ageins_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `extract_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `account` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
