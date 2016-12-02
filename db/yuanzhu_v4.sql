alter table placard add `ptype` int default 0 comment '公告类型';

DROP TABLE IF EXISTS `backgroudimg`;

CREATE TABLE `backgroudimg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(200) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `placard_type`;

CREATE TABLE `placard_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf32 DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

insert  into `placard_type`(`id`,`type`) values (1,'新闻'),(2,'娱乐'),(3,'体育'),(4,'其他');


DROP TABLE IF EXISTS `user_bgimg`;

CREATE TABLE `user_bgimg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bgimg_id` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
