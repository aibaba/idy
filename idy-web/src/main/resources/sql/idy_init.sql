/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.34 : Database - idy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`idy` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `idy`;

/*Table structure for table `colume` */

DROP TABLE IF EXISTS `colume`;

CREATE TABLE `colume` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `en_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zn_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sequence` smallint(6) DEFAULT '1',
  `type` tinyint(1) DEFAULT '1',
  `status` smallint(1) DEFAULT '0',
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `colume` */

insert  into `colume`(`id`,`en_name`,`zn_name`,`sequence`,`type`,`status`,`description`) values (1,NULL,'序号',1,1,0,NULL),(2,NULL,'工号',1,1,0,NULL),(3,NULL,'姓名',1,1,0,NULL),(4,NULL,'证件号码',1,1,0,NULL),(5,NULL,'代称',1,1,0,NULL),(6,NULL,'入职日期',1,1,0,NULL),(7,NULL,'所属机构',1,1,0,NULL),(8,NULL,'业务单元',1,1,0,NULL),(9,NULL,'一级部门/分公司',1,1,0,NULL),(10,NULL,'办事处',1,1,0,NULL),(11,NULL,'工作地点',1,1,0,NULL),(12,NULL,'二级部门',1,1,0,NULL),(13,NULL,'三级部门',1,1,0,NULL),(14,NULL,'职位名称',1,1,0,NULL),(15,NULL,'岗位类型',1,1,0,NULL),(16,NULL,'职级',1,1,0,NULL),(17,NULL,'司龄',1,1,0,NULL),(18,NULL,'员工状态',1,1,0,NULL),(19,NULL,'直接主管',1,1,0,NULL),(20,NULL,'指导人',1,1,0,NULL),(21,NULL,'通过关注日期',1,1,0,NULL),(22,NULL,'试用期',1,1,0,NULL),(23,NULL,'转正日期',1,1,0,NULL),(24,NULL,'毕业学校',1,1,0,NULL),(25,NULL,'专业',1,1,0,NULL),(26,NULL,'学历',1,1,0,NULL),(27,NULL,'政治面貌',1,1,0,NULL),(28,NULL,'性别',1,1,0,NULL),(29,NULL,'出生日期',1,1,0,NULL),(30,NULL,'年龄',1,1,0,NULL),(31,NULL,'婚姻状态',1,1,0,NULL),(32,NULL,'民族',1,1,0,NULL),(33,NULL,'户口性质',1,1,0,NULL),(34,NULL,'户口所在地',1,1,0,NULL),(35,NULL,'身份证地址',1,1,0,NULL),(36,NULL,'现居住地址',1,1,0,NULL),(37,NULL,'本人电话',1,1,0,NULL),(38,NULL,'公司邮箱',1,1,0,NULL),(39,NULL,'紧急联系人',1,1,0,NULL),(40,NULL,'合同类型',1,1,0,NULL),(41,NULL,'合同期限类型',1,1,0,NULL),(42,NULL,'现合同/协议主体',1,1,0,NULL),(43,NULL,'现签合同/协议起始日期',1,1,0,NULL),(44,NULL,'现签合同/协议终止时间\r\n',1,1,0,NULL),(45,NULL,'录入人\r\n',1,1,0,NULL),(46,NULL,'备注\r\n',1,1,0,NULL);

/*Table structure for table `excel` */

DROP TABLE IF EXISTS `excel`;

CREATE TABLE `excel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `c01` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c02` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c03` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c04` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c05` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c06` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c07` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c08` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c09` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c10` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c11` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c12` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c13` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c14` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c15` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c16` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c17` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c18` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c19` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c20` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c21` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c22` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c23` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c24` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c25` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c26` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c27` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c28` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c29` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c30` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c31` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c32` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c33` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c34` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c35` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c36` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c37` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c38` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c39` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c40` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c41` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c42` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c43` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c44` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c45` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c46` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c47` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c48` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c49` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c50` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `excel` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
