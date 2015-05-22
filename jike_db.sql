/*
Navicat MySQL Data Transfer

Source Server         : mysql55
Source Server Version : 50542
Source Host           : localhost:3306
Source Database       : jike_db

Target Server Type    : MYSQL
Target Server Version : 50542
File Encoding         : 65001

Date: 2015-05-22 19:13:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `curriculum_tb`
-- ----------------------------
DROP TABLE IF EXISTS `curriculum_tb`;
CREATE TABLE `curriculum_tb` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `url` varchar(100) DEFAULT NULL COMMENT '课程宣传图片的uri',
  `briefIntroduction` varchar(500) DEFAULT NULL COMMENT '简介',
  `useFlag` int(20) DEFAULT NULL COMMENT '否是是Vip课程<0为普通,1为Vip>',
  `isValid` int(20) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of curriculum_tb
-- ----------------------------
INSERT INTO `curriculum_tb` VALUES ('1', 'Hadoop 概述', '-1638430572.png', '本课程介绍了 Hadoop 技术的由来，Hadoop 技术的优势，如何创建本地 Hadoop 集群、创建托管的本地 Hadoop 集群等内容。', '0', '1');
INSERT INTO `curriculum_tb` VALUES ('2', 'Python 语法基础', '-540983000.png', '本课程会讲解 Python 的基础语法，包括变量、字符串、标识符、行与缩进等知识，并结合实际案例进行讲解，加深大家的理解', '1', '1');
INSERT INTO `curriculum_tb` VALUES ('3', 'Photoshop 图像和画布', '-1636294436.png', '本课程将简单介绍在 Photoshop 绘图环境中画布区域与图像大小的区别及相关设置，并对不同模式图像的特性进行详细的说明，并结合简单实例，演示如何利用图像模式完成一些特殊效果的制作。', '1', '1');
INSERT INTO `curriculum_tb` VALUES ('4', 'Django开发－基础篇', '-1443161748.png', '本课程首先介绍Django的历史发展，然后从视图、模型、模板与数据库交互等方面讲解如何快速开发一个Django应用，为进一步开发更完善、更高级的Django应用做准备。', '1', '1');
INSERT INTO `curriculum_tb` VALUES ('5', 'Spring 入门示例', '-1573242854.png', '本节课程主要介绍一下 Spring 的基本软件需求，然后逐个进行安装和配置，接下来会实际编写一个简单的代码示例并对相关注意点以及知识点进行详细介绍，通过本次课程，希望大家能够搭建简单的 Spring 的开源框架', '1', '1');
INSERT INTO `curriculum_tb` VALUES ('6', 'C++ 简介及环境搭建', '-1144079965.png', '本课程讲解 C 和 C++ 的区别和联系；解析学习 C++ 不需要先学 C 的原因；阐述编辑器、编译器和 IDE 的关系。', '1', '1');

-- ----------------------------
-- Table structure for `duration_tb`
-- ----------------------------
DROP TABLE IF EXISTS `duration_tb`;
CREATE TABLE `duration_tb` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `curriculum_id` int(20) DEFAULT NULL COMMENT '课程Id',
  `name` varchar(50) DEFAULT NULL COMMENT '课时名称',
  `url` varchar(200) DEFAULT NULL COMMENT '视频的uri',
  `timeSpan` mediumtext COMMENT '课时总时间',
  `briefIntroduction` varchar(500) DEFAULT NULL COMMENT '简介',
  `useFlag` int(2) DEFAULT NULL COMMENT '是否是vip课程',
  `isValid` int(2) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `curriculum_id_index` (`curriculum_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duration_tb
-- ----------------------------
INSERT INTO `duration_tb` VALUES ('1', '1', 'Hadoop 历史 ', '1.mp4', '341000', '本课时讲解 Hadoop 技术出现的历史背景。', '0', '1');
INSERT INTO `duration_tb` VALUES ('2', '1', '创建本地 Hadoop 集群', '2.mp4', '670000', '本课时讲解在 Ubuntu 主机上创建 Hadoop 集群的步骤。', '0', '1');
INSERT INTO `duration_tb` VALUES ('3', '1', '创建托管 Hadoop 集群', '3.mp4', '582000', '本课时讲解基于 AWS 创建 Hadoop 集群的方法。', '0', '1');
INSERT INTO `duration_tb` VALUES ('4', '2', 'Python 常量与变量 ', '4.mp4', '567000', '本课时主要给大家讲解 Python 的常量与变量的知识，让大家掌握 Python 的变量与常量的区别，并且让学员结合具体程序掌握变量与常量的知识', '1', '1');
INSERT INTO `duration_tb` VALUES ('5', '2', 'Python 数与字符串', '5.mp4', '946000', '本课时会给大家讲解 Python 的语法基础中的数与字符串部分，并结合程序案例让学员深入理解数与字符串。', '1', '1');
INSERT INTO `duration_tb` VALUES ('6', '2', 'Python 数据类型', '6.mp4', '901000', '本课时会给大家讲解 Python 的语法基础中的数据类型部分，并结合程序案例让学员深入理解数据类型。', '1', '1');
INSERT INTO `duration_tb` VALUES ('7', '2', 'Python 认识标识符', '7.mp4', '372000', '本课时会给大家讲解本课时会给大家讲解 Python 的语法基础中的标识符部分，并结合程序案例让学员深入理解标识符。', '1', '1');
INSERT INTO `duration_tb` VALUES ('8', '2', 'Python Python 对象', '8.mp4', '570000', '本课时会给大家讲解 Python 的语法基础中的对象部分，并结合程序案例让学员深入理解对象。', '1', '1');
INSERT INTO `duration_tb` VALUES ('9', '2', 'Python 行与缩进', '9.mp4', '600000', '本课时会给大家讲解 Python 的语法基础中的行与缩进部分，并结合程序案例让学员深入理解行与缩进。', '1', '1');
INSERT INTO `duration_tb` VALUES ('10', '3', '课程简介', '10.mp4', '46000', '本课时将对课程内容做一个简单的介绍。', '1', '1');
INSERT INTO `duration_tb` VALUES ('11', '3', '画布、图像的大小', '11.mp4', '663000', '本课时将介绍在Photoshop中画布区域与图像大小的区别及相关设置，包括画布大小设置，图像大小、角度设置。', '1', '1');
INSERT INTO `duration_tb` VALUES ('12', '3', '裁切工具', '12.mp4', '382000', '本课时将介绍裁切工具的用途和使用方式，并介绍辅助工具的使用。', '1', '1');
INSERT INTO `duration_tb` VALUES ('13', '3', '图像模式和通道', '13.mp4', '600000', '本课时将介绍不同图像模式的特性，包括8种；以及图像菜单中通道位数的意义。', '1', '1');
INSERT INTO `duration_tb` VALUES ('14', '3', '利用图像模式制作示例', '14.mp4', '475000', '本课时将使用图像菜单提供的选项，通过在不同图像模式之间的转换，完成点阵效果图像示例的制作。', '1', '1');
INSERT INTO `duration_tb` VALUES ('15', '4', 'Django 概述', '15.mp4', '296000', '本课时首先讲解 Django 的历史，最新版本及其框架特性等基础知识,然后介绍 Django 在不同平台下的安装方法，并快速搭建一个 Django 应用。', '1', '1');
INSERT INTO `duration_tb` VALUES ('16', '4', '视图开发及 URL 配置', '16.mp4', '439000', '本课时主要讲解如何编写一个视图函数，并且配置匹配的 URL。', '1', '1');
INSERT INTO `duration_tb` VALUES ('17', '4', 'Django 模板语法及使用', '17.mp4', '524000', '本课时主要学习 Django 的模板语法，以及使用、加载、渲染模板的方法。', '1', '1');
INSERT INTO `duration_tb` VALUES ('18', '4', '模型开发与数据库交互', '18.mp4', '517000', '本课时主要讲解 Django 的 MTV 开发模式、模型的概念、写法，以及数据库的交互。', '1', '1');
INSERT INTO `duration_tb` VALUES ('19', '4', 'Django 的后台管理及表单类介绍', '19.mp4', '400000', '本课时首先讲解如何使用 Django 自带的后台管理系统，修改组以及权限，然后介绍 Django 自有的表单类。', '1', '1');
INSERT INTO `duration_tb` VALUES ('20', '5', 'Spring开发环境搭建', '20.mp4', '559000', '本课程主要介绍了Spring开发用到的基本软件环境，并详细的介绍了相关软件的下载和安装的方法，帮助大家能够迅速的搭建Spring的开发环境', '1', '1');
INSERT INTO `duration_tb` VALUES ('21', '5', 'Spring开发包介绍 ', '21.mp4', '526000', '本课时主要介绍了Spring下的核心开发包和辅助开发包的主要功能，由于这些包文件是Spring工程开发和运行的基础，因此理解这些开发包的公用有利于后续的代码开发。', '1', '1');
INSERT INTO `duration_tb` VALUES ('22', '5', '创建示例工程', '22.mp4', '1173000', '本课程主要讲解了Spring的开发过程，包括工程的建立，代码的开发，配置文件的编写以及编译运行程序结果。', '1', '1');
INSERT INTO `duration_tb` VALUES ('23', '5', '总结', '23.mp4', '47000', '本课时主要对示例代码开发的过程进行了总结，进一步加深和巩固大家对Spring的IOC的概念。', '1', '1');
INSERT INTO `duration_tb` VALUES ('24', '6', 'C 和 C++ 的区别及联系', '24.mp4', '799000', '本课时主要对 C 和 C++ 的发展历史、功能和特性进行介绍，阐述 C 和 C++ 的区别和联系，并阐明学习 C 并不是学习 C++ 的前提。', '1', '1');
INSERT INTO `duration_tb` VALUES ('25', '6', 'C/C++ 编译器介绍', '25.mp4', '391000', '本课时对 C/C++ 编译器的作用、程序编译的步骤，以及其源文件、目标文件进行概述，并给出 C/C++ 编译器的不完全列表。', '1', '1');
INSERT INTO `duration_tb` VALUES ('26', '6', 'C/C++ 编辑器和 IDE', '26.mp4', '610000', '本课时介绍常用的编辑器及其作用，阐述 IDE、编辑器和编译器的关系。', '1', '1');
INSERT INTO `duration_tb` VALUES ('27', '6', 'IDE/开发工具的安装和使用', '27.mp4', '314000', '本课时介绍 IDE /开发工具的安装和使用，包括 GCC、clang 和 VC++ 2013 社区版。', '1', '1');

-- ----------------------------
-- Table structure for `user_tb`
-- ----------------------------
DROP TABLE IF EXISTS `user_tb`;
CREATE TABLE `user_tb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `create_date` datetime DEFAULT NULL COMMENT '用户注册时间',
  `use_type` bigint(20) DEFAULT NULL COMMENT '用户类型',
  `image_url` varchar(50) DEFAULT NULL COMMENT '头像路径',
  `liveness` int(11) DEFAULT '0' COMMENT '活跃度',
  `isvalid` int(11) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_tb
-- ----------------------------
INSERT INTO `user_tb` VALUES ('1', '金子', 'e10adc3949ba59abbe56e057f20f883e', '13800138000', '929280059@qq.com', '2015-04-14 19:10:45', '1', 'default.png', '0', '1');
INSERT INTO `user_tb` VALUES ('2', '小雪', 'e10adc3949ba59abbe56e057f20f883e', '19280013800', '985847932@qq.com', '2015-04-14 19:18:40', '3', 'default.png', '0', '1');

-- ----------------------------
-- Table structure for `vip_type_tb`
-- ----------------------------
DROP TABLE IF EXISTS `vip_type_tb`;
CREATE TABLE `vip_type_tb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `vip_name` varchar(20) DEFAULT NULL COMMENT 'Vip名称',
  `liveness` bigint(20) DEFAULT NULL COMMENT '活跃度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vip_type_tb
-- ----------------------------
INSERT INTO `vip_type_tb` VALUES ('1', '普通用户', null);
INSERT INTO `vip_type_tb` VALUES ('2', '超级管理员', null);
INSERT INTO `vip_type_tb` VALUES ('3', 'VIP1', '0');
INSERT INTO `vip_type_tb` VALUES ('4', 'VIP2', '500');
INSERT INTO `vip_type_tb` VALUES ('5', 'VIP3', '1000');
INSERT INTO `vip_type_tb` VALUES ('6', 'VIP4', '2000');
INSERT INTO `vip_type_tb` VALUES ('7', 'VIP5', '4000');
INSERT INTO `vip_type_tb` VALUES ('8', 'VIP6', '8000');
INSERT INTO `vip_type_tb` VALUES ('9', 'VIP7', '16000');
INSERT INTO `vip_type_tb` VALUES ('10', 'VIP8', '32000');
