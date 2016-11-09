1.groupmember表新增 
		--daysign (每天签到状态)
		--monthsign (每月签到集合)

2.friend表新增
		--fnode (好友备注)

2.activity表
        --新增二维码字段（qrCode）
		--新增经度字段（lon）
        --新增维度字段(lat)
        --新增是否有新消息状态字段 (isnew)

3.struuser表
		--新增个人二维码字段(qrCode)
		
4.groupinfo表
		--新增二维码字段(qrCode)
		
5.actcomment表
		
		--新增评论追加内容字段(reComment)
		--新增评论回复时间(reTime)
		
6.praise表
		--新增点赞表
		
7.coinlog表
		--新增type字段(爱友币变更记录类型)
		--新增内容表
		
8.placard表
		--新增collegeId(增加学院ID)
//================接口文档修改=================
1.注册页面新增 昵称字段
2.群管理接口新增
	2.1  获取当天是否已签到
	2.2 签到接口
//==================jar包==================
1.新增httpclient-4.3.5.jar
2.新增httpcore-4.3.2.jar



------------------------------------新增字段--------------------------------------------
--motify colunms
alter table actcomment add `reComment` varchar(500) default '' comment '回复内容';
alter table actcomment add `reTime` longtext comment '回复时间';
alter table activities add `qrCode` varchar(200) default '' comment '活动二维码';
alter table activities add `lon` double default 0 comment '经度';
alter table activities add `lat` double default 0 comment '纬度';
alter table activities add `isnew` int default 0 COMMENT '是否有新消息 0:无消息 1:有消息';
alter table friend add `fnote` varchar(20) default '' comment '好友备注';
alter table groupinfo add `qrCode` varchar(200) default '' comment '群二维码';
alter table groupmember add `daysign` int default 0 comment '每天是否签到';
alter table groupmember add `monthsign` int default 0 comment '每月签到数';
alter table stuuser add `qrCode` varchar(200) default '' comment '个人二维码';
alter table coinlog add `type` int default 0 comment '0:充值1.其他';
alter table coinlog add `content` varchar(200) default '' comment '记录内容';
--alter table groupmember add 'weeksign' int default 0 comment '每周签到数';
ALTER TABLE placard ADD collegeId INT COMMENT '学院ID'
--alter table gift add `small_image_url` varchar(200) default '' comment '缩略图url';
--alter table gift add `small_image_width` double default 0 comment '缩略图宽';
--alter table gift add `small_image_height` double default 0 comment '缩略图高';
--alter table task add `small_image_url` varchar(200) default '' comment '缩略图url';
--alter table task add `small_image_width` double default 0 comment '缩略图宽';
--alter table task add `small_image_height` double default 0 comment '缩略图高';
--ALTER TABLE task_rep ADD `small_image_url` VARCHAR(200) DEFAULT '' COMMENT '缩略图url';
--ALTER TABLE task_rep ADD `small_image_width` DOUBLE DEFAULT 0 COMMENT '缩略图宽';
--ALTER TABLE task_rep ADD `small_image_height` DOUBLE DEFAULT 0 COMMENT '缩略图高';
--------------------------------新增数据-----------------------------------------
-- 群规模新增数据，600人
insert into groupscale(id,scale) values (6,600);

---------------------------------新增表---------------------------------------------
--create table
CREATE TABLE `praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activityId` int(11) DEFAULT '0' COMMENT '活动id',
  `stuUserId` int(11) DEFAULT '0' COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--create table task 任务表
--create table task_rep 任务图片映射表
--create table task_type 任务类型表 包括初始化数据
--create table gift 礼品表
--create table gift_exchanage 礼品兑换表
--


--////////////////////////////////////
--/////////////中途整改//////////////////
--////////////////////////////////////

--1.stuuser表新增验收字段validate
alter table stuuser add validate int(2) default 0 comment '用户验证 0:未验证 1:验证通过 2:验证拒绝'





-------------------------------
-- 三期数据库修改
-------------------------------

--1.stuuser表新增支付宝账号字段
ALTER TABLE stuuser ADD alipay VARCHAR(50) default '' COMMENT '支付宝账号' 
ALTER TABLE stuuser ADD alipayname VARCHAR(50) default '' COMMENT '支付宝账号名' 
--2.群列表查询方法修改
-- groupserviceImpl.java GroupinfoMapper.xml
--3. 任务表中添加评分字段
ALTER TABLE task ADD score int(50) COMMENT '任务评分'
--4，用户表中新增诚信积分字段
ALTER TABLE stuuser ADD integral int(50) default 0 COMMENT '诚信积分'
--6。新增job刷新过期任务将冻结爱佑币返回给用户
--7.task表新增状态字段status
ALTER TABLE task ADD status int(2) default 0 COMMENT '完成状态 0:待接单1:已接单 2:待评价3:已完成 4:已过期5.已取消'

alter table activities add title varchar(100) default '' comment '活动标题'
alter table placard add title varchar(100) default '' comment '公告标题'

--8 新增表task_record
create table task_record (
	`id` int(32) auto_increment,
	`user_id` int(32) default 0 comment '用户ID',
	`task_id` int(32) default 0 comment '任务ID',
	`type` int(2) default 0 comment '类型,0:支付宝 1:积分',
	`content` varchar(50) default '' comment '记录描述',
	`result` varchar(25) default '' comment '数值,类型不同,显示的结果不同,type为0时结果显示为支付包记录,type为1时结果显示为积分记录',
	`status` int(2) default 0 comment '记录状态 类型为0时有值, 0:待结算,1:已结算'
	`create_time` datetime comment '创建时间',
	PRIMARY KEY (`id`)
)

--9 activitiy(活动)表新增标题字段
alter table activities add title varchar(50) default '' comment '活动标题'
-- 10 新增贴吧表
create table post_bar(
	`id` int(32) auto_increment,
	`name` varchar(50) default '' comment '贴吧名称',
	`short_name` varchar(50) default '' comment '贴吧简称',
	`image_id` int(32) default 0 comment '图片ID',
	`content` varchar(500) default '' comment '贴吧介绍',
	`bar_manager_id` int(32) default 0 comment '贴吧管理员',
	`create_date` datetime comment '创建时间',
	`qrCode` varchar(200) default '' comment '二维码',
	`is_list` int(2) default -1 comment '是否上架 -1:未上架 1:已上架',
	PRIMARY KEY (`id`)
)

-- 11 新增帖子表
create table post(
	`id` int(32) auto_increment,
	`title` varchar(100) default '' comment '帖子标题',
	`content` varchar(2000) default '' comment '帖子内容',
	`user_id` int(32) default 0 comment '发贴人ID',
	`post_bar_id` int(32) default 0 comment '所属贴吧',
	`create_date` datetime comment '创建时间',
	`is_list` int(2) default -1 comment '是否上架 -1:未上架 1:已上架',
	PRIMARY KEY (`id`)
)

-- 12 新增贴吧图片表
create table post_image(
	`id` int(32) auto_increment,
	`url` varchar(100) default '' comment '图片URL',
	`small_image_url` varchar(100) default '' comment '缩略图URL',
	`post_id` int(32) default 0 comment '所属帖子ID',
	`size` int(10) default 0 comment '图片大小',
	`width` int(20) default 0 comment '图片宽度',
	`height` int(20) default 0 comment '图片高度',
	PRIMARY KEY (`id`)
)

-- 13 新增帖子评论表
create table post_comment(
	`id` int(32) auto_increment,
	`from_user_id` int(32) default 0 comment '评论人ID',
	`to_user_id` int(32) default 0 comment '被评论人ID',
	`comment_id` int(32) default 0 comment '被评论Id',
	`post_id` int(32) default 0 comment '所属帖子ID',
	`content` varchar(2000) default '' comment '评论内容',
	`create_date` datetime comment '创建时间',
	PRIMARY KEY (`id`)
)

-- 14 新增帖子点赞记录
create table post_praise(
	`id` int(32) auto_increment,
	`post_id` int(32) default 0 comment '帖子ID',
	`user_id` int(32) default 0 comment '用户ID',
	PRIMARY KEY (`id`)
)

-- 15. 新增秀吧管理员关系表
create table post_bar_permissions(
	`id` int(32) auto_increment,
	`user_id` int(32) default 0 comment '用户ID',
	`post_bar_id` int(32) default 0 comment '贴吧ID',
	PRIMARY KEY (`id`)
)

-- 16. 任务表task 新增reword字段
alter table task add reword int(2) default 0 comment '0:未奖励1：已奖励'

--17. 活动评论表新增回复用户ID
alter table actcomment add reUserId int(32) default 0 comment '评论回复用户ID'

--18. 	活动表新增对内对外字段
alter table activities add permission int(2) default 0 comment '对内对外权限 1对内 0对外'


--19. 新增配置表（已更新到正式服务器）
create table tb_config(
    `id` int(32) not null auto_increment,
    `coin` int(4) not null comment '爱佑币-人民币比例',
    `coefficient` double(10,2) not null comment '系数',
    `coin_register` int(4) not null comment '注册奖励',
    `coin_person` int(4) not null comment '完善信息奖励',
    `coin_card` int(4) not null comment '学生证认证奖励',
    `coin_us` int(4) not null comment '推荐认证奖励-自己',
    `coin_rec` int(4) not null comment '推荐认证奖励-他人',
    PRIMARY KEY (`id`)
);
INSERT INTO tb_config(`coin`,`coefficient`,`coin_register`,`coin_person`,`coin_card`,`coin_us`,`coin_rec`)
VALUES
(100,10,100,100,100,100,100)

--20.编辑群状态，默认为1 已通过
alter table groupinfo modify status char(1) default 1;

alter table placard add type int(2) default 1 comment '对外1对内2';

-------------------------------未更新正式服务器---------------------------------------------
alter table users add `provinceId` int default 0 comment '省份ID';
alter table users add `cityId` int default 0 comment '城市ID';
alter table users add `schoolId` int default 0 comment '学校ID';



-- /////////////////////////////////未上传//////////////////////////////////////
-- 1。 tb_config 表新增integral字段
alter table tb_config add `integral_release` int default 1 comment '发布人积分';
alter table tb_config add `integral_accept` int default 1 comment '接收人积分';
alter table tb_config add `integral_release_deduct` int default 1 comment '发布人扣除积分';
alter table tb_config add `integral_accept_deduct` int default 1 comment '接收人扣除积分';


--/////////////////////////////////2016-3-31/////////////////////////////////////////
alter table post_bar add `index` int default 1 comment '排序';

drop table if exists `placard_record`;
create table `placard_record`(
	`id` int(32) not null auto_increment,
	`user_id` int(32) not null comment '阅读用户ID',
	`placard_id` int(32) not null comment '咨询ID',
	`create_date` datetime comment '阅读时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='咨询阅读表';




------------------------------------- 2016-5-3 更新 ---------------------------------------------------
ALTER TABLE post ADD `is_top` INT DEFAULT 1 COMMENT '是否置顶 1:未置顶 2:已置顶';


---------------------------------------2016-7-19 有助三期优化  已提交------------------------------------
ALTER TABLE post ADD `group_id` varchar(100) DEFAULT 0 COMMENT '帖子绑定群ID';
ALTER TABLE task_type ADD `group_id` varchar(100) DEFAULT 0 COMMENT '任务类型绑定群ID';



---------------------------------------2016-8-08 有助三期优化 已提交------------------------------------

ALTER TABLE placard ADD `linkUrl` varchar(200) DEFAULT '' COMMENT '公告链接';
ALTER TABLE post ADD `linkUrl` varchar(200) DEFAULT '' COMMENT '帖子链接';
















