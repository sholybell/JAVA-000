/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : foreign-exchange-b

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-12-17 16:42:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) DEFAULT NULL,
  `balance` decimal(10,3) DEFAULT NULL,
  `freeze_amt` decimal(10,3) DEFAULT NULL,
  `currency_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('6', 'b', '100.000', '0.000', 'us');
INSERT INTO `account` VALUES ('7', 'b', '100.000', '0.000', 'rmb');
