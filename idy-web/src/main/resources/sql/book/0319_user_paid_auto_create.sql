-- ----------------------------
-- Table structure for `user_paid_auto`
-- 数据库：dg_xiaoshuo
-- ----------------------------
DROP TABLE IF EXISTS `user_paid_auto`;
CREATE TABLE `user_paid_auto` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL COMMENT '自动购买的作品ID',
  `create_time` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户自动购买设置表';