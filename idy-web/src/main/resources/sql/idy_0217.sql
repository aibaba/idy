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
  `sheet_id` int(11) DEFAULT NULL,
  `en_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zn_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sequence` smallint(6) DEFAULT '1',
  `type` tinyint(1) DEFAULT '1',
  `status` smallint(1) DEFAULT '0',
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `excel` */

DROP TABLE IF EXISTS `excel`;

CREATE TABLE `excel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增的ID',
  `excel_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Excel文件名称',
  `sheet_id` int(5) NOT NULL COMMENT 'sheet号',
  `version` int(9) DEFAULT NULL COMMENT '版本号，用于区分历史版本，Excel名称+SheetID来区分',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `c01` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对应Excel中的column，顺序对应',
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `ser_obj` */

DROP TABLE IF EXISTS `ser_obj`;

CREATE TABLE `ser_obj` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `obj_name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `blob_val` blob,
  `info` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `sheet_log` */

DROP TABLE IF EXISTS `sheet_log`;

CREATE TABLE `sheet_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `info` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `sheet_id` int(11) DEFAULT NULL,
  `sheet_version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
