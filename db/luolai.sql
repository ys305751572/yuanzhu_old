/*
Navicat MySQL Data Transfer

Source Server         : lvjunjun
Source Server Version : 50703
Source Host           : 10.58.169.51:3306
Source Database       : luolai

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2014-11-04 13:33:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `assortpro`
-- ----------------------------
DROP TABLE IF EXISTS `assortpro`;
CREATE TABLE `assortpro` (
  `assortproid` int(10) NOT NULL AUTO_INCREMENT COMMENT '搭配产品主键',
  `productid` int(10) DEFAULT NULL COMMENT '产品主键',
  `pro_productid` int(10) DEFAULT NULL COMMENT '被搭配产品主键',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`assortproid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='搭配产品表';

-- ----------------------------
-- Records of assortpro
-- ----------------------------
INSERT INTO `assortpro` VALUES ('7', '1', '17', '0', null, null);
INSERT INTO `assortpro` VALUES ('8', '1', '18', '0', null, null);
INSERT INTO `assortpro` VALUES ('9', '25', '26', '0', null, null);
INSERT INTO `assortpro` VALUES ('10', '25', '26', '0', null, null);

-- ----------------------------
-- Table structure for `brand`
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `brandid` int(10) NOT NULL AUTO_INCREMENT COMMENT '品牌主键',
  `viewurl` varchar(500) DEFAULT NULL COMMENT '视频目录',
  `caption` varchar(50) DEFAULT NULL COMMENT '标题',
  `minute` varchar(500) DEFAULT NULL COMMENT '文字说明',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`brandid`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=gb2312 COMMENT='品牌/视频管理表';

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES ('7', 'http://localhost:8080/luolai/upload/vedio/7454ae97-5afd-48f8-afa1-6b58cd04e511.mp4', '测试用视频1', 'QQ', '0', 'http://localhost:8080/luolai/upload/vedio/c1296ea7-4769-435c-8f6a-c940c833738f.jpg', 'http://localhost:8080/luolai/upload/suolue/cbeac17c-5888-4f77-be2e-d4eca88502b3.jpg');
INSERT INTO `brand` VALUES ('8', 'http://192.168.1.122:9090/luolai/upload/vedio/159e77f7-0d79-42e6-95c4-934d50192bdb.mp4', '测试用视频2', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/64289cdf-8d55-4e8f-b623-e1e980cb27e4.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/c9cb800e-a230-474d-a648-886b8a6a2d01.jpg');
INSERT INTO `brand` VALUES ('9', 'http://192.168.1.122:9090/luolai/upload/vedio/8e10d774-359b-49b5-8388-e83af0cd0ad5.mp4', '测试用视频3', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/4ed7ac26-67bc-41a7-94be-d0364069716d.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/3436fcd0-2d05-49ac-addb-f943e61cf522.jpg');
INSERT INTO `brand` VALUES ('10', 'http://192.168.1.122:9090/luolai/upload/vedio/e2253f4e-9176-4b06-9945-923bee25a734.mp4', '测试用视频4', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/9ce7f234-0967-4979-9430-2fc0e4015766.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/f49a211e-ead7-4fc7-9996-fafdbd36d8e3.jpg');
INSERT INTO `brand` VALUES ('11', 'http://192.168.1.122:9090/luolai/upload/vedio/e819d2fa-3852-4413-9a77-d32ea8698272.mp4', '121212', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/5521e4f5-00f9-4668-9e82-d1cdf345498b.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/11294e8f-e326-4b00-aeae-e6ddf75e32d5.jpg');
INSERT INTO `brand` VALUES ('12', 'http://localhost:8080/luolai/upload/vedio/a35229d0-bc34-42a8-8f8e-083f08492f91.mp4', '12121212121212121212', '生生世世事实上身上1', '0', 'http://localhost:8080/luolai/upload/vedio/70cf1fef-7922-47c5-8dd6-c1bfad156984.jpg', 'http://localhost:8080/luolai/upload/suolue/7acb4f51-94c5-41f5-9065-f130774e0436.jpg');
INSERT INTO `brand` VALUES ('13', 'http://192.168.1.122:9090/luolai/upload/vedio/0977dbf0-2963-422d-8980-bd2b837455e2.mp4', '11111', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/e7280068-a390-4c78-a3a1-ea2f7de60e2e.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/1b53163e-f5ba-4685-94a0-e18c7a4e2b31.jpg');
INSERT INTO `brand` VALUES ('14', 'http://192.168.1.122:9090/luolai/upload/vedio/d2bd4c1c-46ee-4cca-b1ac-b39b8c3d4f9a.mp4', '33333333333333', '333333333333', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/d7476125-1314-411a-b6e5-1a2c58995253.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/d89e3a37-141e-4fae-8673-82514f985385.jpg');
INSERT INTO `brand` VALUES ('15', 'http://192.168.1.122:9090/luolai/upload/vedio/626ac4f0-0df4-402d-bd30-4506c9443000.mp4', '444444444444444', '4444444444444', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/89360ee2-857b-4102-bf60-62e7dbfc9d67.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/a80dc779-4bfc-44e9-8a63-9cca453e7884.jpg');
INSERT INTO `brand` VALUES ('16', 'http://192.168.1.122:9090/luolai/upload/vedio/8e5b214b-bd3f-4047-8052-bd950e11ee2c.mp4', '55555555555', '555555555', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/1f435279-df36-4b64-ab90-e1a7239181c1.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/d4653146-17e0-4a2b-ba64-0b2fd2997067.jpg');
INSERT INTO `brand` VALUES ('17', 'http://192.168.1.122:9090/luolai/upload/vedio/b3a45b89-fe61-4bec-88ad-e9ea55c8cb94.mp4', 'wdqwdqw', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/02681ff4-8f4c-4774-a1a0-5a8f2da3e55c.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/0c0803cd-fa1a-4ff1-bf79-68f960806a9a.jpg');
INSERT INTO `brand` VALUES ('18', 'http://192.168.1.122:9090/luolai/upload/vedio/fa601b0d-a757-4cfc-9e53-3a62398363a7.mp4', 'yyyyyyyyyyy', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/0344b818-169c-46c4-9b14-977eb7a88b30.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/6f00802f-3302-4818-b3ea-a299440cc918.jpg');
INSERT INTO `brand` VALUES ('19', 'http://192.168.1.122:9090/luolai/upload/vedio/fe690480-1857-4836-a1cf-0da176122d9e.mp4', 'jjjjjjjjjjjjjjj', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/8e5184db-d970-436c-9a0d-b94122043736.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/479f1491-923b-41fe-8d8e-b4574c776156.jpg');
INSERT INTO `brand` VALUES ('20', 'http://192.168.1.122:9090/luolai/upload/vedio/6fbb5f8f-1bbc-4597-9f00-a4add59fe216.mp4', 'nnnnnnnnnnnn', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/e0d15dd0-0631-445d-a8dd-d767557e33c9.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/a0ab0e6b-8e4e-423c-89a9-4d19d3b5f118.jpg');
INSERT INTO `brand` VALUES ('21', 'http://192.168.1.122:9090/luolai/upload/vedio/7b544515-edaa-481e-986b-e3f6c8da4cfb.mp4', 'xxxxxxxxxxxxxxxxxx', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/3874d08b-2b18-4854-9b62-b2437256fc17.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/956f70ed-ce1d-4483-aff6-1af5950f4232.jpg');
INSERT INTO `brand` VALUES ('22', 'http://192.168.1.122:9090/luolai/upload/vedio/8003a651-0844-4db9-920a-062b1afd2add.mp4', 'cccccccccccccccc', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/ef58e746-e89c-47c8-9b1f-0497fd71cdbd.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/ab0be9da-6119-4d7a-a215-045f0c2cd85b.jpg');
INSERT INTO `brand` VALUES ('23', 'http://192.168.1.122:9090/luolai/upload/vedio/650340b8-218f-4444-9296-d293887e85e4.mp4', 'bbbbbbbbbbbbbbb', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/2c446bfb-15fe-40a7-8364-b6d0415a8544.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/9dcbd523-0c10-4232-8af4-e87124a59555.jpg');
INSERT INTO `brand` VALUES ('24', 'http://192.168.1.122:9090/luolai/upload/vedio/0ac1e697-06b1-429a-bf39-05991df58389.mp4', 'hhhhhhhhhhhhhhhh', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/fccdbdd8-9527-40e5-95bb-103aa794f589.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/e3f116db-78d2-4493-a836-5b6ea6c92b2d.jpg');
INSERT INTO `brand` VALUES ('25', 'http://192.168.1.122:9090/luolai/upload/vedio/23b80e2c-47aa-4400-98e7-89a366c17b55.mp4', 'mmmmmmmmmmmmmmmmmmmmmmmmmm', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/e2a83f10-3c6a-41b9-9482-82923695ece1.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/61f643cb-36cb-4af8-be2d-e1f5d3161261.jpg');
INSERT INTO `brand` VALUES ('26', 'http://192.168.1.122:9090/luolai/upload/vedio/e590a0c1-8d6f-45e7-9543-67e4cd1d611e.mp4', 'iiiiiiiiiiiiiiiiiiiiiiii', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/5e136f15-315c-4ab7-9353-b2af954a651f.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/0b718b21-38dd-46f1-8b23-61bee0efd820.jpg');
INSERT INTO `brand` VALUES ('27', 'http://192.168.1.122:9090/luolai/upload/vedio/51db3a9e-6219-4913-8a3b-34adabd95cea.mp4', 'xcvxcvxcvxcv', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/9ae5c758-5e70-4fce-97cd-429938522054.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/b481f408-60f1-4053-a7e3-baea960ed239.jpg');
INSERT INTO `brand` VALUES ('28', 'http://192.168.1.122:9090/luolai/upload/vedio/680154d4-799d-4722-b0f5-7cb7d414d5fe.mp4', 'yyyyyyyyyyuuuu', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/dcb3da26-033c-4626-925f-be75587bdc06.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/6d268938-7b7d-434f-8865-511c9c738af0.jpg');
INSERT INTO `brand` VALUES ('29', 'http://192.168.1.122:9090/luolai/upload/vedio/740d3f5d-276e-437a-a7a6-51fc9b118838.mp4', 'vvvvvvvccccbbbbb', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/bda0e64b-4964-4b4f-abe8-4216bb6527a4.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/24718f66-6d3c-46d4-82ad-484d5f99d57b.jpg');
INSERT INTO `brand` VALUES ('30', 'http://192.168.1.122:9090/luolai/upload/vedio/5503a465-d2e7-4956-9f7d-64d7505dc542.mp4', 'zxc', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/a47be30f-60f1-499e-b009-374482f8e4e1.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/83241cd6-3374-4b43-8f93-173abc23971f.jpg');
INSERT INTO `brand` VALUES ('31', 'http://192.168.1.122:9090/luolai/upload/vedio/62d5e1c3-b9c6-4c88-a592-6db593d3ced4.mp4', 'eretertetre', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/582c194b-d6c0-4b5a-9ec3-23979e292ff7.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/6da0b19e-47aa-495c-a18d-a5fec339c2ef.jpg');
INSERT INTO `brand` VALUES ('32', 'http://192.168.1.122:9090/luolai/upload/vedio/476b20a4-fa71-4722-9068-77ebc1b15b6d.mp4', 'ggggggggggggfffffffff', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/a9ba3e7f-45cd-4634-af7f-3ed66e0b221a.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/2d81c29e-1f25-4cb0-a4d0-8a3e327b8ff3.jpg');
INSERT INTO `brand` VALUES ('33', 'http://192.168.1.122:9090/luolai/upload/vedio/0e1fa96d-9efe-43c3-8f2c-eb1dfdf12b1a.mp4', 'tytytyty', '', '0', 'http://192.168.1.122:9090/luolai/upload/vedio/c0847f20-2ec7-4534-940c-cf50c1c45729.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/727d2c15-5736-4ff2-b7b5-8ee7c9ced021.jpg');
INSERT INTO `brand` VALUES ('34', 'http://192.168.1.122:9090/luolai/upload/vedio/fe356d6c-34f1-4b30-b46a-65b3ce5f164c.mp4', 'yuyuhjhkhk', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/6f8f0d63-9d29-403a-a2f9-a5142f76056f.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/d3cd1dc3-0d14-4d3c-9cb6-47646cb899c0.jpg');
INSERT INTO `brand` VALUES ('35', 'http://192.168.1.122:9090/luolai/upload/vedio/67861d0a-3833-44f8-a318-c1485f740da0.mp4', 'hhhhhhhhddddddddddffff', '', '1', 'http://192.168.1.122:9090/luolai/upload/vedio/e415348f-152e-49a9-9c3e-2a556baef439.jpg', 'http://192.168.1.122:9090/luolai/upload/suolue/707d405d-3513-487a-b2aa-09ae2b023a54.jpg');

-- ----------------------------
-- Table structure for `cover`
-- ----------------------------
DROP TABLE IF EXISTS `cover`;
CREATE TABLE `cover` (
  `coverid` int(10) NOT NULL AUTO_INCREMENT COMMENT '封面主键',
  `protypeid` int(10) DEFAULT NULL COMMENT '分类主键',
  `picurl` varchar(500) DEFAULT NULL COMMENT '图片URL',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`coverid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='封面管理表';

-- ----------------------------
-- Records of cover
-- ----------------------------
INSERT INTO `cover` VALUES ('1', '4', 'upload/temp/ab68296f-6946-4da2-afa2-142b20116c6d.jpg', '1', 'upload/suolue/ce6f1b92-077b-4470-a280-66cdad02db32.jpg', null);
INSERT INTO `cover` VALUES ('2', '1', 'http://192.168.1.122:9090/luolai/upload/temp/1501d82d-01f1-4074-b0d2-b0a1dfb80950.jpg', '1', 'http://192.168.1.122:9090/luolai/upload/suolue/7325845e-7e5d-48b4-880a-1d3f05f8f6b6.jpg', null);
INSERT INTO `cover` VALUES ('3', '2', 'http://localhost:9090/luolai/upload/temp/351d699a-f0f7-4b40-8f1f-f851d7a727ca.jpg', '1', 'http://localhost:9090/luolai/upload/suolue/688f76a3-dff0-48a0-b1b4-50bee6340bf5.jpg', null);
INSERT INTO `cover` VALUES ('4', '3', 'upload/temp/2dbef907-08bd-4e30-b943-912c7dc8c0d5.jpg', '1', 'upload/suolue/62684614-3375-4a35-859f-861948fc3f05.jpg', null);
INSERT INTO `cover` VALUES ('8', '18', 'upload/temp/fa28ae39-361d-4c3a-ba9c-d1c54ecc9ac7.jpg', '1', 'upload/suolue/0037909d-af0d-466b-aad1-9291e1b592b7.jpg', null);
INSERT INTO `cover` VALUES ('9', '-1', 'http://192.168.1.122:9090/luolai/upload/temp/37409352-01af-47f3-9ff5-aba071b69208.jpg', '0', 'http://192.168.1.122:9090/luolai/upload/suolue/9bb87dec-59b8-41b7-9ecb-4bdcd44befc8.jpg', null);
INSERT INTO `cover` VALUES ('10', '1', 'http://192.168.1.122:9090/luolai/upload/temp/a0d34e3e-78ae-4812-a8a7-2f5c7691a0df.jpg', '1', 'http://192.168.1.122:9090/luolai/upload/suolue/2fdbb706-a0b6-4723-b6ab-fb4d211a3492.jpg', null);
INSERT INTO `cover` VALUES ('11', '1', 'http://localhost:8080/luolai/upload/temp/af9769df-55fb-4175-a651-6627b9e39911.jpg', '0', 'http://localhost:8080/luolai/upload/suolue/d95dfdbb-0084-4491-b13d-46a5fd49f65d.jpg', null);
INSERT INTO `cover` VALUES ('12', '2', 'upload/temp/fa75273e-5f66-4eef-a7a0-c9dd2d04e807.jpg', '0', 'upload/suolue/3ef98de3-7bc8-4de4-9a40-4bd8266a812e.jpg', null);
INSERT INTO `cover` VALUES ('13', '4', 'upload/temp/56dce834-3dc4-4e4a-8e9f-68c6a692e12e.jpg', '0', 'upload/suolue/1438c88f-d66d-4fa3-a38f-0ff009a8a3e5.jpg', null);
INSERT INTO `cover` VALUES ('14', '18', 'upload/temp/f276a92f-3920-4d7a-93ff-744315e32ef3.jpg', '0', 'upload/suolue/58f3fd57-3eec-4cc0-8efe-7dc2ff282cd4.jpg', null);
INSERT INTO `cover` VALUES ('15', '3', 'upload/temp/13321ac5-3dfd-47f2-b883-47978cb95666.jpg', '0', 'upload/suolue/3d36bf56-7706-4987-b222-0b193376c594.jpg', null);
INSERT INTO `cover` VALUES ('16', '26', 'http://localhost:8080/luolai/upload/temp/036d9d28-0980-4ae6-b938-3df5c2e922bd.jpg', '0', 'http://localhost:8080/luolai/upload/suolue/6d15f922-b027-4898-bec8-17c3d008b73c.jpg', null);

-- ----------------------------
-- Table structure for `custandpro`
-- ----------------------------
DROP TABLE IF EXISTS `custandpro`;
CREATE TABLE `custandpro` (
  `custandproid` int(10) NOT NULL AUTO_INCREMENT COMMENT '店员收藏中间表主键',
  `usersid` int(10) DEFAULT NULL COMMENT '用户主键',
  `productid` int(10) DEFAULT NULL COMMENT '产品主键',
  `scount` int(10) DEFAULT NULL COMMENT '收场数量',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`custandproid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='店员收藏中间表';

-- ----------------------------
-- Records of custandpro
-- ----------------------------
INSERT INTO `custandpro` VALUES ('1', '3', '1', '21', '1', null, null);

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `productid` int(10) NOT NULL AUTO_INCREMENT COMMENT '产品主键',
  `protypeid` int(10) DEFAULT NULL COMMENT '二级分类主键',
  `proname` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `promodel` varchar(200) DEFAULT NULL COMMENT '产品型号',
  `price` float(10,2) DEFAULT NULL COMMENT '价格',
  `spec` varchar(200) DEFAULT NULL COMMENT '规格',
  `minute` varchar(500) DEFAULT NULL COMMENT '文字描述',
  `bigurl` varchar(500) DEFAULT NULL COMMENT '大图URL',
  `smallurl` varchar(500) DEFAULT NULL COMMENT '细节图URL',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=gb2312 COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '6', '卢西亚庄园', 'ZY-KDFB2509', '3456.00', '46cm*72cm', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget odio.', 'http://192.168.1.122:9090/luolai/upload/upBigImg/8c6a2b8f-ba56-4bb1-b20d-bc335ed9a42f.jpg', 'http://192.168.1.122:9090/luolai/upload/upDetailImg/ce409a77-f20c-4c0a-b01e-a9b12a4dc0c9.jpg', '0', null, null);
INSERT INTO `product` VALUES ('2', '10', '圣地亚庄园', 'ZY-KDFB2510', '5678.00', '46cm*72cm', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget odio.', 'http://192.168.1.122:9090/luolai/upload/upBigImg/c1b9737b-4c12-4d22-84f5-230a7bbc6b7c.jpg', 'http://192.168.1.122:9090/luolai/upload/upDetailImg/9265d5a9-daaf-4d2b-8472-2a03d3e12f64.jpg', '0', null, null);
INSERT INTO `product` VALUES ('3', '12', '克罗地亚庄园', 'ZY-KFCW1023', '6789.00', '46cm*72cm', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget odio.', 'http://192.168.1.122:9090/luolai/upload/product/3fd51b2d-27be-4832-8f0f-81f9357a6603.jpg', 'http://192.168.1.122:9090/luolai/upload/productDetail/d55c7e5f-b929-498a-b337-86f38e241c4d.jpg', '0', null, null);
INSERT INTO `product` VALUES ('25', '23', '1111', '111122', '1111.00', '1111', '11111111111111111111111111111111111', 'http://localhost:8080/luolai/upload/upBigImg/aa02cd03-54c5-4564-837c-4703c9a54f6e.jpg', 'http://localhost:8080/luolai/upload/upDetailImg/74aefcaf-5793-4c53-ac73-51fd79e142a3.jpg', '0', null, null);
INSERT INTO `product` VALUES ('26', '9', '1212', '1212', '1212.00', '1212', '121212121111111111111111111111', 'http://192.168.1.122:9090/luolai/upload/product/562c26b9-a923-41cb-b406-2775ddec9c00.jpg', 'http://192.168.1.122:9090/luolai/upload/productDetail/06b2b768-2d1a-45a4-87a9-f3c50a564448.jpg', '1', null, null);

-- ----------------------------
-- Table structure for `protype`
-- ----------------------------
DROP TABLE IF EXISTS `protype`;
CREATE TABLE `protype` (
  `protypeid` int(10) NOT NULL AUTO_INCREMENT COMMENT '分类主键',
  `typecode` varchar(50) DEFAULT NULL COMMENT '分类编码',
  `showno` int(10) DEFAULT NULL COMMENT '显示顺序',
  `typename` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `parentid` int(10) DEFAULT NULL COMMENT '上级分类',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`protypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=gb2312 COMMENT='分类表';

-- ----------------------------
-- Records of protype
-- ----------------------------
INSERT INTO `protype` VALUES ('1', null, '5', '温馨风格', null, '0', null, null);
INSERT INTO `protype` VALUES ('2', null, '2', '时尚风格', null, '0', null, null);
INSERT INTO `protype` VALUES ('3', null, '3', '经典风格', null, '0', null, null);
INSERT INTO `protype` VALUES ('4', null, '4', '优雅风格', null, '0', null, null);
INSERT INTO `protype` VALUES ('5', null, '1', '温馨a', '1', '0', null, null);
INSERT INTO `protype` VALUES ('6', null, '2', '温馨b', '1', '1', null, null);
INSERT INTO `protype` VALUES ('7', null, '3', '温馨125', '1', '0', null, null);
INSERT INTO `protype` VALUES ('8', null, '1', '时尚a', '2', '0', null, null);
INSERT INTO `protype` VALUES ('9', null, '2', '时尚b', '2', '0', null, null);
INSERT INTO `protype` VALUES ('10', null, '3', '时尚c', '2', '0', null, null);
INSERT INTO `protype` VALUES ('11', null, '4', '时尚d', '2', '0', null, null);
INSERT INTO `protype` VALUES ('12', null, '1', '经典a', '3', '0', null, null);
INSERT INTO `protype` VALUES ('13', null, '2', '经典b', '3', '0', null, null);
INSERT INTO `protype` VALUES ('14', null, '3', '经典c', '3', '0', null, null);
INSERT INTO `protype` VALUES ('15', null, '4', '经典d', '3', '0', null, null);
INSERT INTO `protype` VALUES ('16', null, '1', '优雅a', '4', '0', null, null);
INSERT INTO `protype` VALUES ('17', null, '2', '经典b', '4', '1', null, null);
INSERT INTO `protype` VALUES ('18', null, '1', '当季推荐', null, '0', null, null);
INSERT INTO `protype` VALUES ('19', null, '1', '温馨', '18', '0', null, null);
INSERT INTO `protype` VALUES ('20', null, '2', '优雅', '18', '0', null, null);
INSERT INTO `protype` VALUES ('21', null, '3', '时尚', '18', '0', null, null);
INSERT INTO `protype` VALUES ('22', null, '4', '经典', '18', '0', null, null);
INSERT INTO `protype` VALUES ('23', null, '5', '温馨4', '1', '0', null, null);
INSERT INTO `protype` VALUES ('24', null, '12132', '1', null, '1', null, null);
INSERT INTO `protype` VALUES ('25', null, '14', '水电费', null, '1', null, null);
INSERT INTO `protype` VALUES ('26', null, '6', '被芯', null, '0', null, null);
INSERT INTO `protype` VALUES ('27', null, '3', '啊啊', '18', '1', null, null);
INSERT INTO `protype` VALUES ('28', '', '11', '111', '0', '0', '', '');

-- ----------------------------
-- Table structure for `sysuser`
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES ('1', 'admin', '21232F297A57A5A743894A0E4A801FC3');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `usersid` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `custno` varchar(50) DEFAULT NULL COMMENT '客户编号',
  `storeno` varchar(50) DEFAULT NULL COMMENT '门店编号',
  `account` varchar(50) DEFAULT NULL COMMENT '账户',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `usertype` char(1) DEFAULT NULL COMMENT '用户类型',
  `storename` varchar(50) DEFAULT NULL COMMENT '门店名称',
  `iscut` char(1) DEFAULT NULL COMMENT '删除标记',
  `deviceno` varchar(100) DEFAULT NULL COMMENT '设备号',
  `fstatus` char(1) DEFAULT NULL COMMENT '审核状态',
  `def1` varchar(200) DEFAULT NULL COMMENT '预留字段1',
  `def2` varchar(200) DEFAULT NULL COMMENT '预留字段2',
  PRIMARY KEY (`usersid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '1201是', 'admin', '0', 'admin', 'admin', 'admin', 'admin', '1', null, '0', null, '0', '01', null);
INSERT INTO `users` VALUES ('2', '15007184046', 'Nathan', '0', '520', '湖北省武汉', '52001', '123', '2', null, '0', '0DE7A574-8A3D-4284-92B1-133AF9EA62FE', '0', '01', null);
INSERT INTO `users` VALUES ('3', '13000000000', '嘎嘎', '1', '1', '湖北省', '101', '123', '2', null, '1', null, '0', '01', null);
INSERT INTO `users` VALUES ('4', '13444444444', '影院', '0', '1', '卡', '101', '123', '2', null, '0', '0DE7A574-8A3D-4284-92B1-133AF9EA62FE', '1', '01', null);
INSERT INTO `users` VALUES ('5', '13030304300', '嘎嘎', '0', '123', '湖北', '12301', '123', '2', null, '0', 'AB0A9D3C-2EB9-48B2-84EA-327CD4E80013', '0', '01', null);
