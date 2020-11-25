/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : tx

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-11-25 10:49:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `sku_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `sku_icon` varchar(255) DEFAULT NULL COMMENT '商品图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `province` varchar(50) DEFAULT NULL COMMENT '省份',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `county` varchar(50) DEFAULT NULL COMMENT '区县',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `price` int(11) DEFAULT NULL COMMENT '价格',
  `is_paid` tinyint(4) DEFAULT '0' COMMENT '是否支付',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`),
  KEY `idx_orderNo` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000028 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_sku`;
CREATE TABLE `t_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sku_name` varchar(50) DEFAULT '' COMMENT '商品名称',
  `sku_icon` varchar(255) DEFAULT NULL COMMENT '商品小图标',
  `sku_img` varchar(255) DEFAULT NULL COMMENT '商品大图',
  `sku_desc` varchar(500) DEFAULT NULL COMMENT '商品描述',
  `sku_pirce` int(11) DEFAULT NULL COMMENT '商品价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `is_use` tinyint(4) DEFAULT '1' COMMENT '是否上架',
  PRIMARY KEY (`id`),
  KEY `idx_skuName` (`sku_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `open_id` varchar(50) DEFAULT NULL COMMENT '微信openId',
  `union_id` varchar(50) DEFAULT NULL COMMENT '微信unionId',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`) USING BTREE,
  KEY `idx_openId` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
