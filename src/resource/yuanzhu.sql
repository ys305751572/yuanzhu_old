/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.62-community : Database - youzhu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`youzhu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `youzhu`;

/*Table structure for table `actcomment` */

DROP TABLE IF EXISTS `actcomment`;

CREATE TABLE `actcomment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `activityId` int(10) NOT NULL DEFAULT '0' COMMENT '活动编号',
  `stuUserId` int(10) NOT NULL DEFAULT '0' COMMENT '移动端用户编号',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '评论内容',
  `createTime` longtext NOT NULL COMMENT '评论时间',
  `reComment` varchar(500) DEFAULT '' COMMENT '评论追加内容',
  `reTime` longtext COMMENT '回复时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8 COMMENT='活动评论表';

/*Table structure for table `activities` */

DROP TABLE IF EXISTS `activities`;

CREATE TABLE `activities` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '活动编号',
  `content` varchar(500) DEFAULT '' COMMENT '活动内容',
  `createTime` longtext COMMENT '发布时间',
  `provinceId` int(10) DEFAULT '0' COMMENT '省份编号',
  `cityId` int(10) DEFAULT '0' COMMENT '城市编号',
  `schoolId` int(20) DEFAULT '0',
  `typeId` int(10) DEFAULT '0' COMMENT '活动类型编号',
  `stuUserId` int(10) DEFAULT '0' COMMENT '活动发起人编号',
  `startTime` longtext COMMENT '活动开始时间',
  `endTime` longtext COMMENT '活动结束时间',
  `picture` varchar(200) DEFAULT '' COMMENT '活动图片URL',
  `maxCount` int(10) DEFAULT '0' COMMENT '限制人数',
  `status` char(1) DEFAULT '0' COMMENT '审核状态，0=未审核，1=审核通过，2=审核未通过',
  `location` varchar(200) DEFAULT '' COMMENT '活动地点',
  `pwidth` int(20) DEFAULT '0' COMMENT '活动图片宽度',
  `pheight` int(20) DEFAULT '0' COMMENT '活动图片高度',
  `qrCode` varchar(200) DEFAULT '' COMMENT '活动二维码',
  `lon` double DEFAULT '0' COMMENT '经度',
  `lat` double DEFAULT '0' COMMENT '维度',
  `isnew` int(11) DEFAULT '0' COMMENT '0:无消息 1:有消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='活动表';

/*Table structure for table `acttype` */

DROP TABLE IF EXISTS `acttype`;

CREATE TABLE `acttype` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '活动类型编号',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '活动类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='活动类型表';

/*Table structure for table `city` */

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `provinceId` int(11) NOT NULL COMMENT '省份主键',
  `brevitycode` varchar(20) NOT NULL COMMENT '简码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市表';

/*Table structure for table `coinlog` */

DROP TABLE IF EXISTS `coinlog`;

CREATE TABLE `coinlog` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `groupId` varchar(50) DEFAULT '0' COMMENT '群编号',
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  `money` double(10,0) DEFAULT '0' COMMENT '充值金额',
  `coin` int(10) NOT NULL COMMENT '爱心币',
  `createTime` longtext COMMENT '创建时间',
  `orderid` varchar(36) DEFAULT NULL,
  `orderName` varchar(50) DEFAULT NULL,
  `status1` tinyint(2) DEFAULT NULL COMMENT '状态：0：未成功，1：已成功',
  `type` int(11) DEFAULT '0' COMMENT '0:充值1:注册2:完善资料3.个人认证4:协助认证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='爱心币log表';

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '反馈编号',
  `content` varchar(500) NOT NULL COMMENT '反馈内容',
  `backTime` varchar(20) NOT NULL COMMENT '反馈时间',
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '审核状态，1=未处理，2=已处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='反馈表';

/*Table structure for table `filtration` */

DROP TABLE IF EXISTS `filtration`;

CREATE TABLE `filtration` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '关键词编号',
  `content` varchar(200) NOT NULL COMMENT '关键词内容',
  `userId` int(10) NOT NULL COMMENT '创建人编号',
  `createTime` longtext NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='关键词表';

/*Table structure for table `friend` */

DROP TABLE IF EXISTS `friend`;

CREATE TABLE `friend` (
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  `friendId` int(10) NOT NULL COMMENT '好友编号',
  `fnote` varchar(16) DEFAULT '' COMMENT '好友备注',
  PRIMARY KEY (`stuUserId`,`friendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友表';

/*Table structure for table `gift` */

DROP TABLE IF EXISTS `gift`;

CREATE TABLE `gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT '' COMMENT '礼品名称',
  `price` double DEFAULT '0' COMMENT '需要的实际价格',
  `coin` int(11) DEFAULT '0' COMMENT '需要爱佑币',
  `pic` varchar(500) DEFAULT '' COMMENT '礼品名称',
  `detail` varchar(2000) DEFAULT '' COMMENT '礼品详情',
  `g_status` int(11) DEFAULT '0' COMMENT '0:下架 1:上架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='礼品表';

/*Table structure for table `gift_exchange` */

DROP TABLE IF EXISTS `gift_exchange`;

CREATE TABLE `gift_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(36) DEFAULT NULL COMMENT '订单ID',
  `gift_id` int(11) DEFAULT '0' COMMENT '礼品ID',
  `user_id` int(11) DEFAULT '0' COMMENT '兑换用户ID',
  `nums` int(11) DEFAULT '0' COMMENT '下单数',
  `create_time` date DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼品兑换表';

/*Table structure for table `groupinfo` */

DROP TABLE IF EXISTS `groupinfo`;

CREATE TABLE `groupinfo` (
  `id` varchar(50) NOT NULL COMMENT '群编号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '群名称',
  `scaleId` int(10) DEFAULT '1' COMMENT '群规模编号',
  `groupType` int(10) DEFAULT '1' COMMENT '话题类型（群类型）',
  `provinceId` int(10) DEFAULT '0' COMMENT '群省份编号',
  `cityId` int(10) DEFAULT '0' COMMENT '群城市编号',
  `schoolId` int(10) DEFAULT '0' COMMENT '群学校',
  `stuUserId` int(10) NOT NULL DEFAULT '0' COMMENT '群主编号',
  `notice` varchar(500) DEFAULT '' COMMENT '群公告',
  `noticeTime` longtext,
  `message` varchar(500) DEFAULT '' COMMENT '群寄语',
  `head` varchar(200) DEFAULT '' COMMENT '群头像URL',
  `status` char(1) DEFAULT '0' COMMENT '认证状态，0=未审核，1=审核通过，2=审核未通过',
  `coin` int(10) DEFAULT '0' COMMENT '爱心币',
  `createTime` longtext,
  `qrCode` varchar(200) DEFAULT NULL COMMENT '二维码地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群表';

/*Table structure for table `groupliveness` */

DROP TABLE IF EXISTS `groupliveness`;

CREATE TABLE `groupliveness` (
  `groupId` varchar(50) NOT NULL DEFAULT '',
  `speakNum` int(11) DEFAULT '0',
  `stuUserId` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `groupmember` */

DROP TABLE IF EXISTS `groupmember`;

CREATE TABLE `groupmember` (
  `groupId` varchar(50) NOT NULL DEFAULT '',
  `stuUserId` int(10) NOT NULL DEFAULT '0',
  `daysign` int(11) DEFAULT '0' COMMENT '每天群签到表 0:未签到 1:已签到',
  `monthsign` int(11) DEFAULT '0' COMMENT '每月签到次数 ，月底清零'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `groupscale` */

DROP TABLE IF EXISTS `groupscale`;

CREATE TABLE `groupscale` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '群规模编号',
  `scale` int(10) NOT NULL DEFAULT '500' COMMENT '群规模，默认为500',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='群规模表';

/*Table structure for table `grouptype` */

DROP TABLE IF EXISTS `grouptype`;

CREATE TABLE `grouptype` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '群类型编号',
  `name` varchar(50) NOT NULL COMMENT '群类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='群类型表';

/*Table structure for table `interests` */

DROP TABLE IF EXISTS `interests`;

CREATE TABLE `interests` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '兴趣编号',
  `interest` varchar(50) NOT NULL COMMENT '兴趣名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=764 DEFAULT CHARSET=utf8 COMMENT='兴趣表';

/*Table structure for table `labels` */

DROP TABLE IF EXISTS `labels`;

CREATE TABLE `labels` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='标签表';

/*Table structure for table `placard` */

DROP TABLE IF EXISTS `placard`;

CREATE TABLE `placard` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '公告编号',
  `content` varchar(500) NOT NULL COMMENT '公告内容',
  `createTime` varchar(20) NOT NULL COMMENT '发布时间',
  `stuUserId` int(10) NOT NULL COMMENT '后台用户编号',
  `provinceId` int(10) NOT NULL COMMENT '省份编号',
  `cityId` int(10) NOT NULL COMMENT '城市编号',
  `schoolId` int(10) NOT NULL COMMENT '学校编号',
  `picture` varchar(200) DEFAULT '' COMMENT '公告图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='公告表';

/*Table structure for table `praise` */

DROP TABLE IF EXISTS `praise`;

CREATE TABLE `praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activityId` int(11) DEFAULT '0' COMMENT '活动id',
  `stuUserId` int(11) DEFAULT '0' COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `province` */

DROP TABLE IF EXISTS `province`;

CREATE TABLE `province` (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `brevitycode` varchar(20) NOT NULL COMMENT '简码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份表';

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '举报编号',
  `content` varchar(500) NOT NULL COMMENT '举报内容',
  `userId` int(10) NOT NULL COMMENT '举报人编号',
  `createTime` longtext NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='举报表';

/*Table structure for table `sacollect` */

DROP TABLE IF EXISTS `sacollect`;

CREATE TABLE `sacollect` (
  `activitilyId` int(10) NOT NULL COMMENT '活动编号',
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  PRIMARY KEY (`activitilyId`,`stuUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='移动端用户收藏活动中间表';

/*Table structure for table `sajoin` */

DROP TABLE IF EXISTS `sajoin`;

CREATE TABLE `sajoin` (
  `activitilyId` int(10) NOT NULL COMMENT '活动编号',
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '审核状态，0=未审核，1=审核通过，2=审核未通过，默认为0',
  `createTime` longtext NOT NULL COMMENT '参加时间',
  PRIMARY KEY (`activitilyId`,`stuUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='移动端用户参加活动中间表';

/*Table structure for table `scs` */

DROP TABLE IF EXISTS `scs`;

CREATE TABLE `scs` (
  `jid` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `id` bigint(10) NOT NULL COMMENT '编号',
  `proName` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `pid` bigint(10) NOT NULL DEFAULT '0' COMMENT '上级Id',
  `level` char(1) NOT NULL DEFAULT '1' COMMENT '级别，1=学校，2=学院，3=专业',
  `provinceId` int(10) NOT NULL DEFAULT '0' COMMENT '省份id',
  `cityId` int(10) NOT NULL DEFAULT '0' COMMENT '城市id',
  PRIMARY KEY (`jid`)
) ENGINE=InnoDB AUTO_INCREMENT=316092 DEFAULT CHARSET=utf8 COMMENT='学校-学院-专业表';

/*Table structure for table `stuinterests` */

DROP TABLE IF EXISTS `stuinterests`;

CREATE TABLE `stuinterests` (
  `stuUserId` int(10) NOT NULL COMMENT '移动端用户编号',
  `interestId` int(10) NOT NULL COMMENT '兴趣编号',
  PRIMARY KEY (`stuUserId`,`interestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='移动端用户-兴趣中间表';

/*Table structure for table `stuuser` */

DROP TABLE IF EXISTS `stuuser`;

CREATE TABLE `stuuser` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `nickname` varchar(50) DEFAULT '' COMMENT '昵称',
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `birthday` varchar(50) DEFAULT '' COMMENT '生日',
  `provinceId` int(10) DEFAULT '0' COMMENT '省份编号',
  `cityId` int(10) DEFAULT '0' COMMENT '城市编号',
  `sex` char(1) DEFAULT '0' COMMENT '性别，0=男，1=女，默认为0',
  `startYear` varchar(4) DEFAULT '' COMMENT '入学年份',
  `schoolId` int(10) DEFAULT '0' COMMENT '学校编号',
  `collegeId` int(10) DEFAULT '0' COMMENT '学院编号',
  `specialtyId` int(10) DEFAULT '0' COMMENT '专业编号',
  `status` char(1) DEFAULT '0' COMMENT '认证状态，0=未认证，1=认证未通过，2=认证通过，3=认证中，默认为0',
  `recUserId` int(10) DEFAULT '0' COMMENT '推荐人编号',
  `head` varchar(200) DEFAULT '' COMMENT '头像URL',
  `word` varchar(500) DEFAULT '' COMMENT '签名',
  `coverPic` varchar(200) DEFAULT '' COMMENT '学生证封面照片',
  `contentPic` varchar(200) DEFAULT '' COMMENT '学生证内容照片',
  `coin` int(10) DEFAULT '0' COMMENT '爱心币',
  `labelId` int(10) DEFAULT '0',
  `isOnline` char(1) DEFAULT '1' COMMENT '用户在线状态，0=在线，1=离线',
  `perfectInfo` tinyint(1) DEFAULT '0' COMMENT '0:未完成，1:已完成',
  `qrCode` varchar(200) DEFAULT NULL COMMENT '用户个人二维码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=558735 DEFAULT CHARSET=utf8 COMMENT='移动端用户表';

/*Table structure for table `t_test` */

DROP TABLE IF EXISTS `t_test`;

CREATE TABLE `t_test` (
  `id` int(11) DEFAULT NULL,
  `reId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_title` varchar(200) DEFAULT '' COMMENT '任务标题',
  `task_pic` varchar(500) DEFAULT '' COMMENT '任务图片',
  `task_type_id` varchar(50) DEFAULT '0' COMMENT '任务类型,多个类型用逗号分隔',
  `release_user_id` int(11) DEFAULT NULL COMMENT '发布者ID',
  `task_sex` int(11) DEFAULT '0' COMMENT '接单性别 0,不限 1,男 2,女',
  `task_lat` double DEFAULT '0' COMMENT '纬度',
  `task_lon` double DEFAULT '0' COMMENT '经度',
  `task_address` varchar(2000) DEFAULT '' COMMENT '任务地址',
  `task_end_time` date DEFAULT NULL COMMENT '活动结束时间',
  `task_coin` int(11) DEFAULT '0' COMMENT '任务奖励',
  `task_desc` varchar(5000) DEFAULT '' COMMENT '任务描述',
  `accept_id` int(11) DEFAULT '0' COMMENT '任务接单人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='任务表';

/*Table structure for table `task_rep` */

DROP TABLE IF EXISTS `task_rep`;

CREATE TABLE `task_rep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT '0' COMMENT '任务ID',
  `url` varchar(500) DEFAULT '' COMMENT '活动类型 & 活动图片URL',
  `width` double DEFAULT NULL COMMENT '图片宽度',
  `height` double DEFAULT NULL COMMENT '图片高度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='活动映射表，存储活动的多张图片，多中类型';

/*Table structure for table `task_type` */

DROP TABLE IF EXISTS `task_type`;

CREATE TABLE `task_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT '' COMMENT '类型名称',
  `url` varchar(200) DEFAULT '' COMMENT '类别图片url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='任务类型表';

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `userName` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名',
  `realName` varchar(100) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  `userType` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型，1=普通管理员，2=系统管理员，默认为1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

/*Table structure for table `years` */

DROP TABLE IF EXISTS `years`;

CREATE TABLE `years` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '年份编号',
  `year` varchar(5) NOT NULL DEFAULT '' COMMENT '年份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='入学年份表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
