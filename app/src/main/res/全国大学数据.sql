/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : university

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-01-17 15:54:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `week` int(11) DEFAULT NULL,
  `weekday` int(11) DEFAULT NULL,
  `start_time` varchar(30) DEFAULT NULL,
  `end_time` varchar(30) DEFAULT NULL,
  `max_count` int(11) DEFAULT NULL,
  `free_count` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `publisher_type` varchar(20) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '校运会3', '学校操场2', '18', '5', '09:30', '11:30', '10000', '9800', '1', '校方', '18周周五要举行校运会，希望大家前来观赛', '2017-12-20 16:31:35', '1367');
INSERT INTO `activity` VALUES ('5', '操场', '操场', '1', '1', '06:00', '06:01', '58', '58', '5', '校方', '可以的', '2017-12-26 19:59:06', '1367');

-- ----------------------------
-- Table structure for `alarm`
-- ----------------------------
DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `start_week` int(11) DEFAULT NULL,
  `end_week` int(11) DEFAULT NULL,
  `weekdays` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `start_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alarm
-- ----------------------------
INSERT INTO `alarm` VALUES ('10', '马克思原理', '学校课程', '1', '18', '周三', '3', '8:00');

-- ----------------------------
-- Table structure for `club`
-- ----------------------------
DROP TABLE IF EXISTS `club`;
CREATE TABLE `club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of club
-- ----------------------------
INSERT INTO `club` VALUES ('1', '轮滑社');
INSERT INTO `club` VALUES ('2', '羽毛球社');

-- ----------------------------
-- Table structure for `club_manager`
-- ----------------------------
DROP TABLE IF EXISTS `club_manager`;
CREATE TABLE `club_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `club_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of club_manager
-- ----------------------------
INSERT INTO `club_manager` VALUES ('1', '1', '1', null);

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `consult_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '老铁，可以的', '1', '2017-12-20 22:58:43', '1', '学生');
INSERT INTO `comment` VALUES ('2', '老铁，可以的', '2', '2017-12-20 22:58:43', '1', '学生');
INSERT INTO `comment` VALUES ('3', '。。。', '17', '2017-12-28 19:05:25', '5', '负责人');
INSERT INTO `comment` VALUES ('4', '。。', '16', '2017-12-28 19:48:53', '5', '负责人');
INSERT INTO `comment` VALUES ('5', '怎么样', '20', '2017-12-29 15:11:38', '3', '学生');
INSERT INTO `comment` VALUES ('6', '。。。', '14', '2018-01-17 15:28:46', '3', '学生');

-- ----------------------------
-- Table structure for `communication`
-- ----------------------------
DROP TABLE IF EXISTS `communication`;
CREATE TABLE `communication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  `message` varchar(200) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of communication
-- ----------------------------
INSERT INTO `communication` VALUES ('1', '3', '4', '的点点滴滴', '2017-12-22 08:45:43', 'unread');
INSERT INTO `communication` VALUES ('2', '4', '3', 'zz订单订单寻寻', '2017-12-22 08:45:59', 'unread');
INSERT INTO `communication` VALUES ('3', '3', '4', null, '2018-01-01 21:23:08', 'unread');
INSERT INTO `communication` VALUES ('4', '3', '4', '你好', '2018-01-01 21:24:19', 'unread');

-- ----------------------------
-- Table structure for `consult`
-- ----------------------------
DROP TABLE IF EXISTS `consult`;
CREATE TABLE `consult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consult
-- ----------------------------
INSERT INTO `consult` VALUES ('1', '哎哟，不错哦，工作辛苦不', '1', '2017-12-20 22:41:08', '1', '学生', null);
INSERT INTO `consult` VALUES ('2', '哎哟，不错哦，工作辛苦不', '6', '2017-12-20 22:41:08', '1', '学生', null);
INSERT INTO `consult` VALUES ('3', '哎哟，不错哦，工作辛苦不', '6', '2017-12-20 22:41:08', '1', '学生', null);
INSERT INTO `consult` VALUES ('5', '真的假的呀', '1', '2017-12-23 08:00:00', '5', '校方', 'work');
INSERT INTO `consult` VALUES ('6', '发布成功了吗', '1', '2017-12-23 08:00:00', '5', '校方', 'work');
INSERT INTO `consult` VALUES ('7', '我来留个言', '4', '2017-12-23 08:00:00', '5', '负责人', 'work');
INSERT INTO `consult` VALUES ('8', '留个言', '4', '2017-12-23 21:03:08', '5', '负责人', 'work');
INSERT INTO `consult` VALUES ('9', '哎哟  不错哦', '2', '2017-12-25 20:04:41', '5', '校方', 'work');
INSERT INTO `consult` VALUES ('10', '可以的', '2', '2017-12-25 20:07:02', '5', '校方', 'work');
INSERT INTO `consult` VALUES ('11', '大家踊跃留言', '3', '2017-12-27 11:19:05', '5', '负责人', 'work');
INSERT INTO `consult` VALUES ('14', '犯法的', '3', '2017-12-27 22:34:36', '5', '负责人', 'work');
INSERT INTO `consult` VALUES ('15', '可以的', '3', '2017-12-28 18:46:55', '1', '校方', 'work');
INSERT INTO `consult` VALUES ('16', '可以的', '3', '2017-12-28 18:48:03', '1', '校方', 'work');
INSERT INTO `consult` VALUES ('17', '可以吗', '5', '2017-12-28 18:48:14', '1', '校方', 'activity');
INSERT INTO `consult` VALUES ('18', '评论一下', '2', '2017-12-29 10:01:49', '5', '校方', 'work');
INSERT INTO `consult` VALUES ('19', '留个言', '3', '2017-12-29 10:33:35', '5', '负责人', 'work');
INSERT INTO `consult` VALUES ('20', '哎哟 不错哦', '3', '2017-12-29 15:11:06', '3', '学生', 'work');
INSERT INTO `consult` VALUES ('21', '第一个留言的人', '1', '2017-12-29 15:35:22', '3', '学生', 'activity');
INSERT INTO `consult` VALUES ('22', '大大', '2', '2018-01-16 19:40:58', '3', '学生', 'work');
INSERT INTO `consult` VALUES ('23', '评论一下', '5', '2018-01-17 15:08:41', '3', '学生', 'activity');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  `start_week` int(11) DEFAULT NULL,
  `end_week` int(11) DEFAULT NULL,
  `weekday` int(11) DEFAULT NULL,
  `start_section` int(11) DEFAULT NULL,
  `end_section` int(11) DEFAULT NULL,
  `teacher` varchar(20) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `major` varchar(40) DEFAULT NULL,
  `term` varchar(10) DEFAULT NULL,
  `grade` varchar(20) DEFAULT NULL,
  `student_class` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '马克思原理', '教学楼A404', '1', '18', '3', '1', '2', '李老师', '1367', '软件工程', '第一学期', '大一', 'w2020');
INSERT INTO `course` VALUES ('2', '算法', '教学楼303', '1', '18', '4', '7', '8', '王老师', '1367', null, null, null, null);

-- ----------------------------
-- Table structure for `custom_course`
-- ----------------------------
DROP TABLE IF EXISTS `custom_course`;
CREATE TABLE `custom_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `end_section` int(11) DEFAULT NULL,
  `end_week` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_section` int(11) DEFAULT NULL,
  `start_week` int(11) DEFAULT NULL,
  `weekday` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom_course
-- ----------------------------
INSERT INTO `custom_course` VALUES ('4', '改一下地点', '2', '10', '中国近代史B', '1', '10', '2', '3');
INSERT INTO `custom_course` VALUES ('7', null, '1', '1', '活动-操场', '1', '1', '1', '3');
INSERT INTO `custom_course` VALUES ('8', '学校操场2', '4', '18', '活动-校运会3', '3', '18', '5', '3');
INSERT INTO `custom_course` VALUES ('14', '666', '6', '12', '666', '5', '10', '5', '3');

-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('4', '4', '3', '已同意');

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) DEFAULT NULL,
  `detail` varchar(40) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '奖学金事宜', '本学期奖学金取消！', '1', '2017-12-20 16:49:17', '1367');
INSERT INTO `notice` VALUES ('2', '停水事宜', '本周末会停水！', '1', '2017-12-20 16:49:17', '1367');
INSERT INTO `notice` VALUES ('4', '发个', '不错哦哦', '5', '2017-12-26 19:41:54 19:41', '1367');

-- ----------------------------
-- Table structure for `province`
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of province
-- ----------------------------
INSERT INTO `province` VALUES ('1', '北京', null);
INSERT INTO `province` VALUES ('2', '天津', null);
INSERT INTO `province` VALUES ('3', '河北', null);
INSERT INTO `province` VALUES ('4', '山西', null);
INSERT INTO `province` VALUES ('5', '内蒙古', null);
INSERT INTO `province` VALUES ('6', '辽宁', null);
INSERT INTO `province` VALUES ('7', '吉林', null);
INSERT INTO `province` VALUES ('8', '黑龙江', null);
INSERT INTO `province` VALUES ('9', '上海', null);
INSERT INTO `province` VALUES ('10', '江苏', null);
INSERT INTO `province` VALUES ('11', '浙江', null);
INSERT INTO `province` VALUES ('12', '安徽', null);
INSERT INTO `province` VALUES ('13', '福建', null);
INSERT INTO `province` VALUES ('14', '江西', null);
INSERT INTO `province` VALUES ('15', '山东', null);
INSERT INTO `province` VALUES ('16', '河南', null);
INSERT INTO `province` VALUES ('17', '湖北', null);
INSERT INTO `province` VALUES ('18', '湖南', null);
INSERT INTO `province` VALUES ('19', '广东', null);
INSERT INTO `province` VALUES ('20', '广西', null);
INSERT INTO `province` VALUES ('21', '海南', null);
INSERT INTO `province` VALUES ('22', '重庆', null);
INSERT INTO `province` VALUES ('23', '四川', null);
INSERT INTO `province` VALUES ('24', '贵州', null);
INSERT INTO `province` VALUES ('25', '云南', null);
INSERT INTO `province` VALUES ('26', '西藏', null);
INSERT INTO `province` VALUES ('27', '陕西', null);
INSERT INTO `province` VALUES ('28', '甘肃', null);
INSERT INTO `province` VALUES ('29', '青海', null);
INSERT INTO `province` VALUES ('30', '宁夏', null);
INSERT INTO `province` VALUES ('31', '新疆', null);
INSERT INTO `province` VALUES ('32', '香港', null);
INSERT INTO `province` VALUES ('33', '澳门', null);
INSERT INTO `province` VALUES ('34', '台湾', null);

-- ----------------------------
-- Table structure for `publisher`
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `publish_password` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `unit` varchar(30) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `club_name` varchar(30) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `work_joiner_id` int(11) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK6rp1rm49611h2r8wh6m7r2cof` (`work_joiner_id`),
  CONSTRAINT `FK6rp1rm49611h2r8wh6m7r2cof` FOREIGN KEY (`work_joiner_id`) REFERENCES `work_joiner` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publisher
-- ----------------------------
INSERT INTO `publisher` VALUES ('1', 'icelee', 'icelee', 'icelee', '1367', '保卫处', '12312312312', '校方', null, '更新了', null, null);
INSERT INTO `publisher` VALUES ('3', 'icelee2', 'icelee', 'icelee2', '1367', '保卫处', '12312312312', '校方', null, '李斌', null, null);
INSERT INTO `publisher` VALUES ('4', 'icelee3', 'icelee', 'icelee3', '1367', null, '12312312312', '校方', null, '李老师', null, null);
INSERT INTO `publisher` VALUES ('5', 'zzz', 'zzzzzz', 'zzzzzz', '1367', '保卫处', '18321452145', '校方', null, 'zzz', null, 'https://www.ice97.cn/download/image/268cachezzz.jpg');

-- ----------------------------
-- Table structure for `school_manager`
-- ----------------------------
DROP TABLE IF EXISTS `school_manager`;
CREATE TABLE `school_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `union` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_manager
-- ----------------------------
INSERT INTO `school_manager` VALUES ('1', '李老师', '保卫处', 'icelee', 'icelee', '1367', null);
INSERT INTO `school_manager` VALUES ('2', '2', '2', '2', '2', '1367', '2');
INSERT INTO `school_manager` VALUES ('3', 'zz', 'z', 'zzz', 'zzz', '1367', '保卫处');

-- ----------------------------
-- Table structure for `school_student`
-- ----------------------------
DROP TABLE IF EXISTS `school_student`;
CREATE TABLE `school_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `major` varchar(40) DEFAULT NULL,
  `grade` varchar(10) NOT NULL,
  `student_class` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_student
-- ----------------------------
INSERT INTO `school_student` VALUES ('1', 'icelee', 'icelee', '软件工程', '大一', 'w2020', '20', '男', '1367', '小李');
INSERT INTO `school_student` VALUES ('2', 'icelee2', 'icelee2', '软件工程', '大一', 'w2020', '20', '男', '1367', '小李');
INSERT INTO `school_student` VALUES ('3', 'ccc', 'ccc', 'ccc', 'ccc', 'c', '22', 'c', '1367', 'cc');

-- ----------------------------
-- Table structure for `school_time`
-- ----------------------------
DROP TABLE IF EXISTS `school_time`;
CREATE TABLE `school_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `university_id` int(11) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `one` varchar(20) DEFAULT NULL,
  `two` varchar(20) DEFAULT NULL,
  `three` varchar(20) DEFAULT NULL,
  `four` varchar(20) DEFAULT NULL,
  `five` varchar(20) DEFAULT NULL,
  `six` varchar(20) DEFAULT NULL,
  `seven` varchar(20) DEFAULT NULL,
  `eight` varchar(20) DEFAULT NULL,
  `nine` varchar(20) DEFAULT NULL,
  `ten` varchar(20) DEFAULT NULL,
  `eleven` varchar(20) DEFAULT NULL,
  `twelve` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_time
-- ----------------------------
INSERT INTO `school_time` VALUES ('1', '1367', '45', '8:00', '9:00', '10:00', '11:00', '14:00', '15:00', '16:00', '17:00', '19:00', '20:00', '0', '0');
INSERT INTO `school_time` VALUES ('2', '0', '45', '8:00', '9:00', '10:00', '11:00', '14:00', '15:00', '16:00', '17:00', '19:00', '20:00', '0', '0');

-- ----------------------------
-- Table structure for `student_union_manager`
-- ----------------------------
DROP TABLE IF EXISTS `student_union_manager`;
CREATE TABLE `student_union_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_union_manager
-- ----------------------------
INSERT INTO `student_union_manager` VALUES ('1', '2', null);
INSERT INTO `student_union_manager` VALUES ('2', '3', null);

-- ----------------------------
-- Table structure for `system_message`
-- ----------------------------
DROP TABLE IF EXISTS `system_message`;
CREATE TABLE `system_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `title2` varchar(50) DEFAULT NULL,
  `detail` varchar(300) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  `event_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_message
-- ----------------------------
INSERT INTO `system_message` VALUES ('1', '您报名的兼职审核未通过,已通过', '兼职名称：null', '审核时间：', 'unread', null, null, null, '未读', null, null);
INSERT INTO `system_message` VALUES ('2', '您报名的兼职审核未通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('3', '您报名的兼职审核已通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('4', '您报名的兼职审核未通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('5', '您报名的兼职审核已通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('6', '您报名的兼职审核已通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('7', '您报名的兼职审核未通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('8', '您报名的兼职审核已通过', '兼职名称：地表最强兼职', '审核时间：', 'unread', null, null, '4', '未读', null, null);
INSERT INTO `system_message` VALUES ('9', '请审核兼职', '工作名称：', null, null, null, null, null, null, null, null);
INSERT INTO `system_message` VALUES ('10', '请审核兼职', '兼职名称：地表最强兼职', '小李 报名了您发布的兼职：地表最强兼职 请尽快审核', 'verify', '3', '2017-12-28 10:59', '5', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('11', '请回复留言', '被留言的兼职名称：地表最强兼职', '留言内容：可以的', 'consult', '16', '2017-12-28 18:48', '5', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('13', '您报名的兼职审核未通过', '兼职名称：null', '审核时间：2017-12-28 19:48', 'work', null, '2017-12-28 19:48', '4', '未读', 'user', null);
INSERT INTO `system_message` VALUES ('14', '您报名的兼职审核已通过', '兼职名称：null', '审核时间：2017-12-28 19:48', 'work', null, '2017-12-28 19:48', '3', '未读', 'user', null);
INSERT INTO `system_message` VALUES ('15', '请回复留言', '被留言的兼职名称：操场值班', '留言内容：评论一下', 'consult', '18', '2017-12-29 10:01', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('16', '请回复留言', '被留言的兼职名称：地表最强兼职', '留言内容：哎哟 不错哦', 'consult', '20', '2017-12-29 15:11', '5', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('17', '有人回复了您的留言', '您的留言：哎哟 不错哦', '用户回复内容：哎哟 不错哦', 'comment', '20', '2017-12-29 15:11', '3', '未读', 'user', 'consult');
INSERT INTO `system_message` VALUES ('18', '请回复留言', '被留言的活动名称：校运会3', '留言内容：第一个留言的人', 'consult', '21', '2017-12-29 15:35', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('19', '请审核兼职', '兼职名称：地表最强兼职', '小李 报名了您发布的兼职：地表最强兼职 请尽快审核', 'verify', '3', '2017-12-29 16:51', '5', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('20', '好友变动提醒', '您的好友摩羯座将您删除了', '摩羯座 将不再出现再您的好友列表之中', 'deleteFriend', null, '2017-12-30 10:09', '4', '未读', 'user', null);
INSERT INTO `system_message` VALUES ('21', '添加好友', '摩羯座 请求添加您为好友', '验证信息：你好', null, '2', '2017-12-30 10:46', '4', '未读', 'user', 'friend');
INSERT INTO `system_message` VALUES ('24', '添加好友', 'zzzz2 通过了您的添加请求', '你们已经是好友啦！可以进行留言与查看信息', null, '3', '2017-12-30 11:18', '4', '未读', 'user', 'user');
INSERT INTO `system_message` VALUES ('25', '请审核兼职', '兼职名称：操场值班', '小李 报名了您发布的兼职：操场值班 请尽快审核', 'verify', '2', '2018-01-01 20:49', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('27', '请审核兼职', '兼职名称：教学楼值班', '小李 报名了您发布的兼职：教学楼值班 请尽快审核', 'verify', '1', '2018-01-07 09:43', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('28', '请回复留言', '被留言的兼职名称：操场值班', '留言内容：大大', 'consult', '22', '2018-01-16 19:40', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('29', '请审核兼职', '兼职名称：操场值班', '小李 报名了您发布的兼职：操场值班 请尽快审核', 'verify', '2', '2018-01-17 09:06', '1', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('30', '请回复留言', '被留言的活动名称：操场', '留言内容：评论一下', 'consult', '23', '2018-01-17 15:08', '5', '未读', 'publisher', 'work');
INSERT INTO `system_message` VALUES ('31', '有人回复了您的留言', '您的留言：犯法的', '用户回复内容：犯法的', 'comment', '14', '2018-01-17 15:28', '5', '未读', 'student', 'consult');

-- ----------------------------
-- Table structure for `university`
-- ----------------------------
DROP TABLE IF EXISTS `university`;
CREATE TABLE `university` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `province_id` tinyint(4) NOT NULL,
  `level` varchar(20) DEFAULT NULL,
  `website` varchar(30) DEFAULT NULL,
  `abbreviation` varchar(10) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3962 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of university
-- ----------------------------
INSERT INTO `university` VALUES ('1367', '北京大学', '1', '本科', 'http://www.pku.edu.cn/', 'pku', '北京市');
INSERT INTO `university` VALUES ('1368', '中国人民大学', '1', '本科', 'http://www.ruc.edu.cn/', 'ruc', '北京市');
INSERT INTO `university` VALUES ('1369', '清华大学', '1', '本科', 'http://www.tsinghua.edu.cn/', 'tsinghua', '北京市');
INSERT INTO `university` VALUES ('1370', '北京交通大学', '1', '本科', 'http://www.njtu.edu.cn/', 'njtu', '北京市');
INSERT INTO `university` VALUES ('1371', '北京工业大学', '1', '本科', 'http://www.bjut.edu.cn/', 'bjut', '北京市');
INSERT INTO `university` VALUES ('1372', '北京航空航天大学', '1', '本科', 'http://www.buaa.edu.cn/', 'buaa', '北京市');
INSERT INTO `university` VALUES ('1373', '北京理工大学', '1', '本科', 'http://www.bit.edu.cn/', 'bit', '北京市');
INSERT INTO `university` VALUES ('1374', '北京科技大学', '1', '本科', 'http://www.ustb.edu.cn/', 'ustb', '北京市');
INSERT INTO `university` VALUES ('1375', '北方工业大学', '1', '本科', 'http://www.ncut.edu.cn/', 'ncut', '北京市');
INSERT INTO `university` VALUES ('1376', '北京化工大学', '1', '本科', 'http://www.buct.edu.cn/', 'buct', '北京市');
INSERT INTO `university` VALUES ('1377', '北京工商大学', '1', '本科', 'http://www.btbu.edu.cn/', 'btbu', '北京市');
INSERT INTO `university` VALUES ('1378', '北京服装学院', '1', '本科', 'http://www.bift.edu.cn/', 'bift', '北京市');
INSERT INTO `university` VALUES ('1379', '北京邮电大学', '1', '本科', 'http://www.bupt.edu.cn/', 'bupt', '北京市');
INSERT INTO `university` VALUES ('1380', '北京印刷学院', '1', '本科', 'http://www.bigc.edu.cn/', 'bigc', '北京市');
INSERT INTO `university` VALUES ('1381', '北京建筑大学', '1', '本科', 'http://www.bucea.edu.cn/', 'bucea', '北京市');
INSERT INTO `university` VALUES ('1382', '北京石油化工学院', '1', '本科', 'http://www.bipt.edu.cn/', 'bipt', '北京市');
INSERT INTO `university` VALUES ('1383', '北京电子科技学院', '1', '本科', 'http://www.besti.edu.cn/', 'besti', '北京市');
INSERT INTO `university` VALUES ('1384', '中国农业大学', '1', '本科', 'http://www.cau.edu.cn/', 'cau', '北京市');
INSERT INTO `university` VALUES ('1385', '北京农学院', '1', '本科', 'http://www.bac.edu.cn/', 'bac', '北京市');
INSERT INTO `university` VALUES ('1386', '北京林业大学', '1', '本科', 'http://www.bjfu.edu.cn/', 'bjfu', '北京市');
INSERT INTO `university` VALUES ('1387', '北京协和医学院', '1', '本科', 'http://www.cams.ac.cn/', 'cams', '北京市');
INSERT INTO `university` VALUES ('1388', '首都医科大学', '1', '本科', 'http://www.ccmu.edu.cn/', 'ccmu', '北京市');
INSERT INTO `university` VALUES ('1389', '北京中医药大学', '1', '本科', 'http://www.bucm.edu.cn/', 'bucm', '北京市');
INSERT INTO `university` VALUES ('1390', '北京师范大学', '1', '本科', 'http://www.bnu.edu.cn/', 'bnu', '北京市');
INSERT INTO `university` VALUES ('1391', '首都师范大学', '1', '本科', 'http://www.cnu.edu.cn/', 'cnu', '北京市');
INSERT INTO `university` VALUES ('1392', '首都体育学院', '1', '本科', 'http://www.cupes.edu.cn/', 'cupes', '北京市');
INSERT INTO `university` VALUES ('1393', '北京外国语大学', '1', '本科', 'http://www.bfsu.edu.cn/', 'bfsu', '北京市');
INSERT INTO `university` VALUES ('1394', '北京第二外国语学院', '1', '本科', 'http://www.bisu.edu.cn/', 'bisu', '北京市');
INSERT INTO `university` VALUES ('1395', '北京语言大学', '1', '本科', 'http://www.blcu.edu.cn/', 'blcu', '北京市');
INSERT INTO `university` VALUES ('1396', '中国传媒大学', '1', '本科', 'http://www.cuc.edu.cn/', 'cuc', '北京市');
INSERT INTO `university` VALUES ('1397', '中央财经大学', '1', '本科', 'http://www.cufe.edu.cn/', 'cufe', '北京市');
INSERT INTO `university` VALUES ('1398', '对外经济贸易大学', '1', '本科', 'http://www.uibe.edu.cn/', 'uibe', '北京市');
INSERT INTO `university` VALUES ('1399', '北京物资学院', '1', '本科', 'http://www.bwu.edu.cn/', 'bwu', '北京市');
INSERT INTO `university` VALUES ('1400', '首都经济贸易大学', '1', '本科', 'http://www.cueb.edu.cn/', 'cueb', '北京市');
INSERT INTO `university` VALUES ('1401', '外交学院', '1', '本科', 'http://www.cfau.edu.cn/', 'cfau', '北京市');
INSERT INTO `university` VALUES ('1402', '中国人民公安大学', '1', '本科', 'http://www.cppsu.edu.cn/', 'cppsu', '北京市');
INSERT INTO `university` VALUES ('1403', '国际关系学院', '1', '本科', 'http://www.uir.cn/', 'uir', '北京市');
INSERT INTO `university` VALUES ('1404', '北京体育大学', '1', '本科', 'http://www.bsu.edu.cn/', 'bsu', '北京市');
INSERT INTO `university` VALUES ('1405', '中央音乐学院', '1', '本科', 'http://www.ccom.edu.cn/', 'ccom', '北京市');
INSERT INTO `university` VALUES ('1406', '中国音乐学院', '1', '本科', 'http://www.ccmusic.edu.cn/', 'ccmusic', '北京市');
INSERT INTO `university` VALUES ('1407', '中央美术学院', '1', '本科', 'http://www.cafa.edu.cn/', 'cafa', '北京市');
INSERT INTO `university` VALUES ('1408', '中央戏剧学院', '1', '本科', 'http://www.chntheatre.edu.cn/', 'chntheatre', '北京市');
INSERT INTO `university` VALUES ('1409', '中国戏曲学院', '1', '本科', 'http://www.nacta.edu.cn/', 'nacta', '北京市');
INSERT INTO `university` VALUES ('1410', '北京电影学院', '1', '本科', 'http://www.bfa.edu.cn/', 'bfa', '北京市');
INSERT INTO `university` VALUES ('1411', '北京舞蹈学院', '1', '本科', 'http://www.bda.edu.cn/', 'bda', '北京市');
INSERT INTO `university` VALUES ('1412', '中央民族大学', '1', '本科', 'http://www.muc.edu.cn/', 'muc', '北京市');
INSERT INTO `university` VALUES ('1413', '中国政法大学', '1', '本科', 'http://www.cupl.edu.cn/', 'cupl', '北京市');
INSERT INTO `university` VALUES ('1414', '华北电力大学', '1', '本科', 'http://www.ncepu.edu.cn/', 'ncepu', '北京市');
INSERT INTO `university` VALUES ('1415', '中华女子学院', '1', '本科', 'http://www.cwu.edu.cn/', 'cwu', '北京市');
INSERT INTO `university` VALUES ('1416', '北京信息科技大学', '1', '本科', 'http://www.bistu.edu.cn/', 'bistu', '北京市');
INSERT INTO `university` VALUES ('1417', '中国矿业大学（北京）', '1', '本科', 'http://www.cumtb.edu.cn/', 'cumtb', '北京市');
INSERT INTO `university` VALUES ('1418', '中国石油大学（北京）', '1', '本科', 'http://www.cup.edu.cn/', 'cup', '北京市');
INSERT INTO `university` VALUES ('1419', '中国地质大学（北京）', '1', '本科', 'http://www.cugb.edu.cn/', 'cugb', '北京市');
INSERT INTO `university` VALUES ('1420', '北京联合大学', '1', '本科', 'http://www.buu.edu.cn/', 'buu', '北京市');
INSERT INTO `university` VALUES ('1421', '北京城市学院', '1', '本科', 'http://www.bcu.edu.cn/', 'bcu', '北京市');
INSERT INTO `university` VALUES ('1422', '中国青年政治学院', '1', '本科', 'http://www.cyu.edu.cn/', 'cyu', '北京市');
INSERT INTO `university` VALUES ('1423', '首钢工学院', '1', '本科', 'http://www.sgit.edu.cn/', 'sgit', '北京市');
INSERT INTO `university` VALUES ('1424', '中国劳动关系学院', '1', '本科', 'http://www.ciir.edu.cn/', 'ciir', '北京市');
INSERT INTO `university` VALUES ('1425', '北京吉利学院', '1', '本科', 'http://www.bgu.edu.cn/', 'bgu', '北京市');
INSERT INTO `university` VALUES ('1426', '首都师范大学科德学院', '1', '本科', 'http://www.kdcnu.com/', 'kdcnu', '北京市');
INSERT INTO `university` VALUES ('1427', '北京工商大学嘉华学院', '1', '本科', 'http://www.canvard.com.cn/', 'canvard', '北京市');
INSERT INTO `university` VALUES ('1428', '北京邮电大学世纪学院', '1', '本科', 'http://www.ccbupt.cn/', 'ccbupt', '北京市');
INSERT INTO `university` VALUES ('1429', '北京工业大学耿丹学院', '1', '本科', 'http://www.gengdan.cn/', 'gengdan', '北京市');
INSERT INTO `university` VALUES ('1430', '北京警察学院', '1', '本科', 'http://www.bjpc.edu.cn/', 'bjpc', '北京市');
INSERT INTO `university` VALUES ('1431', '北京第二外国语学院中瑞酒店管理学院', '1', '本科', 'http://www.bhi.edu.cn/', 'bhi', '北京市');
INSERT INTO `university` VALUES ('1432', '中国科学院大学', '1', '本科', 'http://www.gucas.ac.cn/', 'gucas', '北京市');
INSERT INTO `university` VALUES ('1433', '中国社会科学院大学', '1', '本科', 'http://ucass.gscass.cn/', 'gscass', '北京市');
INSERT INTO `university` VALUES ('1434', '北京工业职业技术学院', '1', '专科', 'http://www.bgy.org.cn/', 'bgy', '北京市');
INSERT INTO `university` VALUES ('1435', '北京信息职业技术学院', '1', '专科', 'http://www.bitc.edu.cn/', 'bitc', '北京市');
INSERT INTO `university` VALUES ('1436', '北京电子科技职业学院', '1', '专科', 'http://www.dky.bjedu.cn/', 'dky', '北京市');
INSERT INTO `university` VALUES ('1437', '北京京北职业技术学院', '1', '专科', 'http://www.jbzy.com.cn/', 'jbzy', '北京市');
INSERT INTO `university` VALUES ('1438', '北京交通职业技术学院', '1', '专科', 'http://www.jtxy.com.cn/', 'jtxy', '北京市');
INSERT INTO `university` VALUES ('1439', '北京青年政治学院', '1', '专科', 'http://www.bjypc.edu.cn/', 'bjypc', '北京市');
INSERT INTO `university` VALUES ('1440', '北京农业职业学院', '1', '专科', 'http://www.bvca.edu.cn/', 'bvca', '北京市');
INSERT INTO `university` VALUES ('1441', '北京政法职业学院', '1', '专科', 'http://www.bcpl.cn/', 'bcpl', '北京市');
INSERT INTO `university` VALUES ('1442', '北京财贸职业学院', '1', '专科', 'http://www.bjczy.edu.cn/', 'bjczy', '北京市');
INSERT INTO `university` VALUES ('1443', '北京北大方正软件职业技术学院', '1', '专科', 'http://www.pfc.edu.cn/', 'pfc', '北京市');
INSERT INTO `university` VALUES ('1444', '北京经贸职业学院', '1', '专科', 'http://www.csuedu.com/', 'csuedu', '北京市');
INSERT INTO `university` VALUES ('1445', '北京经济技术职业学院', '1', '专科', 'http://www.tangedu.cn/', 'tangedu', '北京市');
INSERT INTO `university` VALUES ('1446', '北京戏曲艺术职业学院', '1', '专科', 'http://www.bjxx.com.cn/', 'bjxx', '北京市');
INSERT INTO `university` VALUES ('1447', '北京汇佳职业学院', '1', '专科', 'http://www.hju.net.cn/', 'hju', '北京市');
INSERT INTO `university` VALUES ('1448', '北京科技经营管理学院', '1', '专科', 'http://www.bjjsy1985.cn/', 'bjjsy1985', '北京市');
INSERT INTO `university` VALUES ('1449', '北京科技职业学院', '1', '专科', 'http://www.5aaa.com/', '5aaa', '北京市');
INSERT INTO `university` VALUES ('1450', '北京培黎职业学院', '1', '专科', 'http://www.bjpldx.edu.cn/', 'bjpldx', '北京市');
INSERT INTO `university` VALUES ('1451', '北京经济管理职业学院', '1', '专科', 'http://www.biem.edu.cn/', 'biem', '北京市');
INSERT INTO `university` VALUES ('1452', '北京劳动保障职业学院', '1', '专科', 'http://www.bvclss.cn/', 'bvclss', '北京市');
INSERT INTO `university` VALUES ('1453', '北京社会管理职业学院', '1', '专科', 'http://www.bcsa.edu.cn/', 'bcsa', '北京市');
INSERT INTO `university` VALUES ('1454', '北京艺术传媒职业学院', '1', '专科', 'http://www.bjamu.cn/', 'bjamu', '北京市');
INSERT INTO `university` VALUES ('1455', '北京体育职业学院', '1', '专科', 'http://www.bjtzhy.org/', 'bjtzhy', '北京市');
INSERT INTO `university` VALUES ('1456', '北京交通运输职业学院', '1', '专科', 'http://www.bjjtxx.com/', 'bjjtxx', '北京市');
INSERT INTO `university` VALUES ('1457', '北京卫生职业学院', '1', '专科', 'http://www.bjwsxx.com/', 'bjwsxx', '北京市');
INSERT INTO `university` VALUES ('1458', '北京网络职业学院', '1', '专科', 'http://www.bjwlxy.org.cn/', 'bjwlxy', '北京市');
INSERT INTO `university` VALUES ('1459', '南开大学', '2', '本科', 'http://www.nankai.edu.cn/', 'nankai', '天津市');
INSERT INTO `university` VALUES ('1460', '天津大学', '2', '本科', 'http://www.tju.edu.cn/', 'tju', '天津市');
INSERT INTO `university` VALUES ('1461', '天津科技大学', '2', '本科', 'http://www.tuli.edu.cn/', 'tuli', '天津市');
INSERT INTO `university` VALUES ('1462', '天津工业大学', '2', '本科', 'http://www.tjpu.edu.cn/', 'tjpu', '天津市');
INSERT INTO `university` VALUES ('1463', '中国民航大学', '2', '本科', 'http://www.cauc.edu.cn/', 'cauc', '天津市');
INSERT INTO `university` VALUES ('1464', '天津理工大学', '2', '本科', 'http://www.tjut.edu.cn/', 'tjut', '天津市');
INSERT INTO `university` VALUES ('1465', '天津农学院', '2', '本科', 'http://www.tjac.edu.cn/', 'tjac', '天津市');
INSERT INTO `university` VALUES ('1466', '天津医科大学', '2', '本科', 'http://www.tijmu.edu.cn/', 'tijmu', '天津市');
INSERT INTO `university` VALUES ('1467', '天津中医药大学', '2', '本科', 'http://www.tjutcm.edu.cn/', 'tjutcm', '天津市');
INSERT INTO `university` VALUES ('1468', '天津师范大学', '2', '本科', 'http://www.tjnu.edu.cn/', 'tjnu', '天津市');
INSERT INTO `university` VALUES ('1469', '天津职业技术师范大学', '2', '本科', 'http://www.tute.edu.cn/', 'tute', '天津市');
INSERT INTO `university` VALUES ('1470', '天津外国语大学', '2', '本科', 'http://www.tjfsu.edu.cn/', 'tjfsu', '天津市');
INSERT INTO `university` VALUES ('1471', '天津商业大学', '2', '本科', 'http://www.tjcu.edu.cn/', 'tjcu', '天津市');
INSERT INTO `university` VALUES ('1472', '天津财经大学', '2', '本科', 'http://www.tjufe.edu.cn/', 'tjufe', '天津市');
INSERT INTO `university` VALUES ('1473', '天津体育学院', '2', '本科', 'http://www.tjus.edu.cn/', 'tjus', '天津市');
INSERT INTO `university` VALUES ('1474', '天津音乐学院', '2', '本科', 'http://www.tjcm.edu.cn/', 'tjcm', '天津市');
INSERT INTO `university` VALUES ('1475', '天津美术学院', '2', '本科', 'http://www.tjarts.edu.cn/', 'tjarts', '天津市');
INSERT INTO `university` VALUES ('1476', '天津城建大学', '2', '本科', 'http://www.tjuci.edu.cn/', 'tjuci', '天津市');
INSERT INTO `university` VALUES ('1477', '天津天狮学院', '2', '本科', 'http://www.tianshi.edu.cn/', 'tianshi', '天津市');
INSERT INTO `university` VALUES ('1478', '天津中德应用技术大学', '2', '本科', 'http://www.zdtj.cn/', 'zdtj', '天津市');
INSERT INTO `university` VALUES ('1479', '天津外国语学院滨海外事学院', '2', '本科', 'http://bhws.tjfsu.edu.cn/', 'tjfsu', '天津市');
INSERT INTO `university` VALUES ('1480', '天津体育学院运动与文化艺术学院', '2', '本科', 'http://www.tjtwy.cn/', 'tjtwy', '天津市');
INSERT INTO `university` VALUES ('1481', '天津商业大学宝德学院', '2', '本科', 'http://www.boustead.edu.cn/', 'boustead', '天津市');
INSERT INTO `university` VALUES ('1482', '天津医科大学临床医学院', '2', '本科', 'http://www.tijmu.edu.cn/lcyxy/', 'tijmu', '天津市');
INSERT INTO `university` VALUES ('1483', '南开大学滨海学院', '2', '本科', 'http://binhai.nankai.edu.cn/', 'nankai', '天津市');
INSERT INTO `university` VALUES ('1484', '天津师范大学津沽学院', '2', '本科', 'http://www.jinguxy.cn/', 'jinguxy', '天津市');
INSERT INTO `university` VALUES ('1485', '天津理工大学中环信息学院', '2', '本科', 'http://www.tjzhic.com/', 'tjzhic', '天津市');
INSERT INTO `university` VALUES ('1486', '北京科技大学天津学院', '2', '本科', 'http://tj.ustb.edu.cn/', 'ustb', '天津市');
INSERT INTO `university` VALUES ('1487', '天津大学仁爱学院', '2', '本科', 'http://www.tjrac.edu.cn/', 'tjrac', '天津市');
INSERT INTO `university` VALUES ('1488', '天津财经大学珠江学院', '2', '本科', 'http://zhujiang.tjufe.edu.cn/', 'tjufe', '天津市');
INSERT INTO `university` VALUES ('1489', '天津职业大学', '2', '专科', 'http://www.tjtc.edu.cn/', 'tjtc', '天津市');
INSERT INTO `university` VALUES ('1490', '天津滨海职业学院', '2', '专科', 'http://www.tjbpi.com/', 'tjbpi', '天津市');
INSERT INTO `university` VALUES ('1491', '天津工程职业技术学院', '2', '专科', 'http://www.tjeti.com/', 'tjeti', '天津市');
INSERT INTO `university` VALUES ('1492', '天津青年职业学院', '2', '专科', 'http://www.tjqnzyxy.cn/', 'tjqnzyxy', '天津市');
INSERT INTO `university` VALUES ('1493', '天津渤海职业技术学院', '2', '专科', 'http://www.tjbhzy.com/', 'tjbhzy', '天津市');
INSERT INTO `university` VALUES ('1494', '天津电子信息职业技术学院', '2', '专科', 'http://www.tjdz.net/', 'tjdz', '天津市');
INSERT INTO `university` VALUES ('1495', '天津机电职业技术学院', '2', '专科', 'http://www.suoyuan.com.cn/', 'suoyuan', '天津市');
INSERT INTO `university` VALUES ('1496', '天津现代职业技术学院', '2', '专科', 'http://www.xdxy.com.cn/', 'xdxy', '天津市');
INSERT INTO `university` VALUES ('1497', '天津公安警官职业学院', '2', '专科', 'http://www.tjjingyuan.cn/', 'tjjingyuan', '天津市');
INSERT INTO `university` VALUES ('1498', '天津轻工职业技术学院', '2', '专科', 'http://www.tjlivtc.edu.cn/', 'tjlivtc', '天津市');
INSERT INTO `university` VALUES ('1499', '天津商务职业学院', '2', '专科', 'http://www.tifert.edu.cn/', 'tifert', '天津市');
INSERT INTO `university` VALUES ('1500', '天津国土资源和房屋职业学院', '2', '专科', 'http://www.tjgsxy.com.cn/', 'tjgsxy', '天津市');
INSERT INTO `university` VALUES ('1501', '天津医学高等专科学校', '2', '专科', 'http://www.tjyzh.cn/', 'tjyzh', '天津市');
INSERT INTO `university` VALUES ('1502', '天津开发区职业技术学院', '2', '专科', 'http://www.tedazj.com/', 'tedazj', '天津市');
INSERT INTO `university` VALUES ('1503', '天津艺术职业学院', '2', '专科', 'http://www.tjysxy.com/', 'tjysxy', '天津市');
INSERT INTO `university` VALUES ('1504', '天津交通职业学院', '2', '专科', 'http://www.tjtvc.com/', 'tjtvc', '天津市');
INSERT INTO `university` VALUES ('1505', '天津冶金职业技术学院', '2', '专科', 'http://www.tjmvti.cn/', 'tjmvti', '天津市');
INSERT INTO `university` VALUES ('1506', '天津石油职业技术学院', '2', '专科', 'http://www.tjsyxy.com/', 'tjsyxy', '天津市');
INSERT INTO `university` VALUES ('1507', '天津城市职业学院', '2', '专科', 'http://www.tjcsxy.cn/', 'tjcsxy', '天津市');
INSERT INTO `university` VALUES ('1508', '天津铁道职业技术学院', '2', '专科', 'http://www.tjtdxy.cn/', 'tjtdxy', '天津市');
INSERT INTO `university` VALUES ('1509', '天津工艺美术职业学院', '2', '专科', 'http://www.gmtj.com/', 'gmtj', '天津市');
INSERT INTO `university` VALUES ('1510', '天津城市建设管理职业技术学院', '2', '专科', 'http://www.tjchengjian.com/', 'tjchengjia', '天津市');
INSERT INTO `university` VALUES ('1511', '天津生物工程职业技术学院', '2', '专科', 'http://www.tjbio.cn/', 'tjbio', '天津市');
INSERT INTO `university` VALUES ('1512', '天津海运职业学院', '2', '专科', 'http://www.tjmvi.cn/', 'tjmvi', '天津市');
INSERT INTO `university` VALUES ('1513', '天津广播影视职业学院', '2', '专科', 'http://www.tjgbys.com/', 'tjgbys', '天津市');
INSERT INTO `university` VALUES ('1514', '天津滨海汽车工程职业学院', '2', '专科', 'http://www.tqzyxy.com/', 'tqzyxy', '天津市');
INSERT INTO `university` VALUES ('1515', '河北大学', '3', '本科', 'http://www.hbu.edu.cn/', 'hbu', '保定市');
INSERT INTO `university` VALUES ('1516', '河北工程大学', '3', '本科', 'http://www.hebeu.edu.cn/', 'hebeu', '邯郸市');
INSERT INTO `university` VALUES ('1517', '河北地质大学', '3', '本科', 'http://www.hgu.edu.cn/', 'hgu', '石家庄');
INSERT INTO `university` VALUES ('1518', '河北工业大学', '3', '本科', 'http://www.hebut.edu.cn/', 'hebut', '天津市');
INSERT INTO `university` VALUES ('1519', '华北理工大学', '3', '本科', 'http://www.ncst.edu.cn/', 'ncst', '唐山市');
INSERT INTO `university` VALUES ('1520', '河北科技大学', '3', '本科', 'http://www.hebust.edu.cn/', 'hebust', '石家庄');
INSERT INTO `university` VALUES ('1521', '河北建筑工程学院', '3', '本科', 'http://www.hebiace.edu.cn/', 'hebiace', '张家口');
INSERT INTO `university` VALUES ('1522', '河北水利电力学院', '3', '本科', 'http://www.hbgz.edu.cn/', 'hbgz', '沧州市');
INSERT INTO `university` VALUES ('1523', '河北农业大学', '3', '本科', 'http://www.hebau.edu.cn/', 'hebau', '保定市');
INSERT INTO `university` VALUES ('1524', '河北医科大学', '3', '本科', 'http://www.hebmu.edu.cn/', 'hebmu', '石家庄');
INSERT INTO `university` VALUES ('1525', '河北北方学院', '3', '本科', 'http://www.hebeinu.edu.cn/', 'hebeinu', '张家口');
INSERT INTO `university` VALUES ('1526', '承德医学院', '3', '本科', 'http://www.cdmc.edu.cn/', 'cdmc', '承德市');
INSERT INTO `university` VALUES ('1527', '河北师范大学', '3', '本科', 'http://www.hebtu.edu.cn/', 'hebtu', '石家庄');
INSERT INTO `university` VALUES ('1528', '保定学院', '3', '本科', 'http://www.bdu.edu.cn/', 'bdu', '保定市');
INSERT INTO `university` VALUES ('1529', '河北民族师范学院', '3', '本科', 'http://www.hbun.net/', 'hbun', '承德市');
INSERT INTO `university` VALUES ('1530', '唐山师范学院', '3', '本科', 'http://www.tstc.edu.cn/', 'tstc', '唐山市');
INSERT INTO `university` VALUES ('1531', '廊坊师范学院', '3', '本科', 'http://www.lfsfxy.edu.cn/', 'lfsfxy', '廊坊市');
INSERT INTO `university` VALUES ('1532', '衡水学院', '3', '本科', 'http://www.hsnc.edu.cn/', 'hsnc', '衡水市');
INSERT INTO `university` VALUES ('1533', '石家庄学院', '3', '本科', 'http://www.sjzc.edu.cn/', 'sjzc', '石家庄');
INSERT INTO `university` VALUES ('1534', '邯郸学院', '3', '本科', 'http://www.hdc.edu.cn/', 'hdc', '邯郸市');
INSERT INTO `university` VALUES ('1535', '邢台学院', '3', '本科', 'http://www.xttc.edu.cn/', 'xttc', '邢台市');
INSERT INTO `university` VALUES ('1536', '沧州师范学院', '3', '本科', 'http://www.caztc.edu.cn/', 'caztc', '沧州市');
INSERT INTO `university` VALUES ('1537', '石家庄铁道大学', '3', '本科', 'http://www.stdu.edu.cn/', 'stdu', '石家庄');
INSERT INTO `university` VALUES ('1538', '燕山大学', '3', '本科', 'http://www.ysu.edu.cn/', 'ysu', '秦皇岛');
INSERT INTO `university` VALUES ('1539', '河北科技师范学院', '3', '本科', 'http://www.hevttc.edu.cn/', 'hevttc', '秦皇岛');
INSERT INTO `university` VALUES ('1540', '唐山学院', '3', '本科', 'http://www.tsc.edu.cn/', 'tsc', '唐山市');
INSERT INTO `university` VALUES ('1541', '华北科技学院', '3', '本科', 'http://www.ncist.edu.cn/', 'ncist', '三河市');
INSERT INTO `university` VALUES ('1542', '中国人民武装警察部队学院', '3', '本科', 'http://www.wjxy.edu.cn/', 'wjxy', '廊坊市');
INSERT INTO `university` VALUES ('1543', '河北体育学院', '3', '本科', 'http://www.hepec.edu.cn/', 'hepec', '石家庄');
INSERT INTO `university` VALUES ('1544', '河北金融学院', '3', '本科', 'http://www.bdcf.net/', 'bdcf', '保定市');
INSERT INTO `university` VALUES ('1545', '北华航天工业学院', '3', '本科', 'http://www.nciae.edu.cn/', 'nciae', '廊坊市');
INSERT INTO `university` VALUES ('1546', '防灾科技学院', '3', '本科', 'http://www.fzxy.edu.cn/', 'fzxy', '三河市');
INSERT INTO `university` VALUES ('1547', '河北经贸大学', '3', '本科', 'http://www.heuet.edu.cn/', 'heuet', '石家庄');
INSERT INTO `university` VALUES ('1548', '中央司法警官学院', '3', '本科', 'http://www.cicp.edu.cn/', 'cicp', '保定市');
INSERT INTO `university` VALUES ('1549', '河北传媒学院', '3', '本科', 'http://www.hebic.cn/', 'hebic', '石家庄');
INSERT INTO `university` VALUES ('1550', '河北工程技术学院', '3', '本科', 'http://www.hbfsh.com/', 'hbfsh', '石家庄');
INSERT INTO `university` VALUES ('1551', '河北美术学院', '3', '本科', 'http://www.hbafa.com/', 'hbafa', '石家庄');
INSERT INTO `university` VALUES ('1552', '河北科技学院', '3', '本科', 'http://www.hbkjxy.cn/', 'hbkjxy', '保定市');
INSERT INTO `university` VALUES ('1553', '河北外国语学院', '3', '本科', 'http://www.hbwy.com.cn/', 'hbwy', '石家庄');
INSERT INTO `university` VALUES ('1554', '河北大学工商学院', '3', '本科', 'http://www.hicc.cn/', 'hicc', '保定市');
INSERT INTO `university` VALUES ('1555', '华北理工大学轻工学院', '3', '本科', 'http://www.qgxy.cn/', 'qgxy', '唐山市');
INSERT INTO `university` VALUES ('1556', '河北科技大学理工学院', '3', '本科', 'http://hbklg.hebust.edu.cn/', 'hebust', '石家庄');
INSERT INTO `university` VALUES ('1557', '河北师范大学汇华学院', '3', '本科', 'http://huihua.hebtu.edu.cn/', 'hebtu', '石家庄');
INSERT INTO `university` VALUES ('1558', '河北经贸大学经济管理学院', '3', '本科', 'http://jgxy.heuet.edu.cn/', 'heuet', '石家庄');
INSERT INTO `university` VALUES ('1559', '河北医科大学临床学院', '3', '本科', 'http://202.206.48.102/', '206', '石家庄');
INSERT INTO `university` VALUES ('1560', '华北电力大学科技学院', '3', '本科', 'http://www.hdky.edu.cn/', 'hdky', '保定市');
INSERT INTO `university` VALUES ('1561', '河北工程大学科信学院', '3', '本科', 'http://kexin.hebeu.edu.cn/', 'hebeu', '邯郸市');
INSERT INTO `university` VALUES ('1562', '河北工业大学城市学院', '3', '本科', 'http://cc.hebut.edu.cn/', 'hebut', '天津市');
INSERT INTO `university` VALUES ('1563', '燕山大学里仁学院', '3', '本科', 'http://stc.ysu.edu.cn/', 'ysu', '秦皇岛');
INSERT INTO `university` VALUES ('1564', '石家庄铁道大学四方学院', '3', '本科', 'http://www.stdusfc.cn/', 'stdusfc', '石家庄');
INSERT INTO `university` VALUES ('1565', '河北地质大学华信学院', '3', '本科', 'http://www.sjzuehx.cn/', 'sjzuehx', '石家庄');
INSERT INTO `university` VALUES ('1566', '河北农业大学现代科技学院', '3', '本科', 'http://xianke.hebau.edu.cn/', 'hebau', '保定市');
INSERT INTO `university` VALUES ('1567', '华北理工大学冀唐学院', '3', '本科', 'http://jtxy.heuu.edu.cn/', 'heuu', '唐山市');
INSERT INTO `university` VALUES ('1568', '中国地质大学长城学院', '3', '本科', 'http://www.cuggw.com/', 'cuggw', '保定市');
INSERT INTO `university` VALUES ('1569', '燕京理工学院', '3', '本科', 'http://www.ncbuct.com/', 'ncbuct', '京东燕郊');
INSERT INTO `university` VALUES ('1570', '北京中医药大学东方学院', '3', '本科', 'http://www.df-college.com/', 'df-college', '廊坊市');
INSERT INTO `university` VALUES ('1571', '北京交通大学海滨学院', '3', '本科', 'http://www.bjtuhbxy.cn/', 'bjtuhbxy', '黄骅市');
INSERT INTO `university` VALUES ('1572', '河北东方学院', '3', '本科', 'http://www.dfzy.edu.cn/', 'dfzy', '廊坊市');
INSERT INTO `university` VALUES ('1573', '河北中医学院', '3', '本科', 'http://www.hbcmc.edu.cn/', 'hbcmc', '石家庄');
INSERT INTO `university` VALUES ('1574', '张家口学院', '3', '本科', 'http://www.zjku.edu.cn/', 'zjku', '张家口');
INSERT INTO `university` VALUES ('1575', '河北环境工程学院', '3', '本科', 'http://www.emcc.cn/', 'emcc', '秦皇岛市');
INSERT INTO `university` VALUES ('1576', '河北工业职业技术学院', '3', '专科', 'http://www.hbcit.edu.cn/', 'hbcit', '石家庄');
INSERT INTO `university` VALUES ('1577', '邯郸职业技术学院', '3', '专科', 'http://www.hd-u.com/', 'hd-u', '邯郸市');
INSERT INTO `university` VALUES ('1578', '石家庄职业技术学院', '3', '专科', 'http://www.sjzpt.edu.cn/', 'sjzpt', '石家庄');
INSERT INTO `university` VALUES ('1579', '张家口职业技术学院', '3', '专科', 'http://www.zhz.cn/', 'zhz', '张家口');
INSERT INTO `university` VALUES ('1580', '承德石油高等专科学校', '3', '专科', 'http://www.cdpc.edu.cn/', 'cdpc', '承德市');
INSERT INTO `university` VALUES ('1581', '邢台职业技术学院', '3', '专科', 'http://www.xpc.edu.cn/', 'xpc', '邢台市');
INSERT INTO `university` VALUES ('1582', '河北软件职业技术学院', '3', '专科', 'http://www.hbsi.edu.cn/', 'hbsi', '保定市');
INSERT INTO `university` VALUES ('1583', '河北石油职业技术学院', '3', '专科', 'http://www.pvtc.edu.cn/', 'pvtc', '廊坊市');
INSERT INTO `university` VALUES ('1584', '河北建材职业技术学院', '3', '专科', 'http://www.hbjcxy.com/', 'hbjcxy', '秦皇岛');
INSERT INTO `university` VALUES ('1585', '河北政法职业学院', '3', '专科', 'http://www.helc.edu.cn/', 'helc', '石家庄');
INSERT INTO `university` VALUES ('1586', '沧州职业技术学院', '3', '专科', 'http://www.czvtc.cn/', 'czvtc', '沧州市');
INSERT INTO `university` VALUES ('1587', '河北能源职业技术学院', '3', '专科', 'http://www.hbnyxy.cn/', 'hbnyxy', '唐山市');
INSERT INTO `university` VALUES ('1588', '石家庄铁路职业技术学院', '3', '专科', 'http://www.sirt.edu.cn/', 'sirt', '石家庄');
INSERT INTO `university` VALUES ('1589', '保定职业技术学院', '3', '专科', 'http://www.bvtc.com.cn/', 'bvtc', '保定市');
INSERT INTO `university` VALUES ('1590', '秦皇岛职业技术学院', '3', '专科', 'http://www.qhdvtc.com/', 'qhdvtc', '秦皇岛');
INSERT INTO `university` VALUES ('1591', '石家庄工程职业学院', '3', '专科', 'http://www.sjzcvc.com/', 'sjzcvc', '石家庄');
INSERT INTO `university` VALUES ('1592', '石家庄城市经济职业学院', '3', '专科', 'http://www.sjzcsjjxy.com/', 'sjzcsjjxy', '石家庄');
INSERT INTO `university` VALUES ('1593', '唐山职业技术学院', '3', '专科', 'http://www.tsvtc.com/', 'tsvtc', '唐山市');
INSERT INTO `university` VALUES ('1594', '衡水职业技术学院', '3', '专科', 'http://www.hsvtc.cn/', 'hsvtc', '衡水市');
INSERT INTO `university` VALUES ('1595', '唐山工业职业技术学院', '3', '专科', 'http://www.tsgzy.edu.cn/', 'tsgzy', '唐山市');
INSERT INTO `university` VALUES ('1596', '邢台医学高等专科学校', '3', '专科', 'http://www.xtmc.net/', 'xtmc', '邢台市');
INSERT INTO `university` VALUES ('1597', '河北艺术职业学院', '3', '专科', 'http://www.hebart.com/', 'hebart', '石家庄');
INSERT INTO `university` VALUES ('1598', '河北旅游职业学院', '3', '专科', 'http://www.cdtvc.com/', 'cdtvc', '承德市');
INSERT INTO `university` VALUES ('1599', '石家庄财经职业学院', '3', '专科', 'http://www.sjzcj.edu.cn/', 'sjzcj', '石家庄');
INSERT INTO `university` VALUES ('1600', '河北交通职业技术学院', '3', '专科', 'http://www.hejtxy.edu.cn/', 'hejtxy', '石家庄');
INSERT INTO `university` VALUES ('1601', '河北化工医药职业技术学院', '3', '专科', 'http://www.hebcpc.cn/', 'hebcpc', '石家庄');
INSERT INTO `university` VALUES ('1602', '石家庄信息工程职业学院', '3', '专科', 'http://www.sjziei.com/', 'sjziei', '石家庄');
INSERT INTO `university` VALUES ('1603', '河北对外经贸职业学院', '3', '专科', 'http://www.hbvcfl.com.cn/', 'hbvcfl', '秦皇岛');
INSERT INTO `university` VALUES ('1604', '保定电力职业技术学院', '3', '专科', 'http://www.bddy.cn/', 'bddy', '保定市');
INSERT INTO `university` VALUES ('1605', '河北机电职业技术学院', '3', '专科', 'http://www.hbjd.com.cn/', 'hbjd', '邢台市');
INSERT INTO `university` VALUES ('1606', '渤海石油职业学院', '3', '专科', 'http://www.bhsyxy.com/', 'bhsyxy', '河北省');
INSERT INTO `university` VALUES ('1607', '廊坊职业技术学院', '3', '专科', 'http://www.lfzhjxy.net/', 'lfzhjxy', '廊坊市');
INSERT INTO `university` VALUES ('1608', '唐山科技职业技术学院', '3', '专科', 'http://www.tskjzy.cn/', 'tskjzy', '唐山市');
INSERT INTO `university` VALUES ('1609', '石家庄邮电职业技术学院', '3', '专科', 'http://www.sjzpc.edu.cn/', 'sjzpc', '石家庄');
INSERT INTO `university` VALUES ('1610', '河北公安警察职业学院', '3', '专科', 'http://www.hebsjy.com/', 'hebsjy', '石家庄');
INSERT INTO `university` VALUES ('1611', '石家庄工商职业学院', '3', '专科', 'http://www.sjzgsxy.com/', 'sjzgsxy', '石家庄');
INSERT INTO `university` VALUES ('1612', '石家庄理工职业学院', '3', '专科', 'http://www.sjzlg.com/', 'sjzlg', '石家庄');
INSERT INTO `university` VALUES ('1613', '石家庄科技信息职业学院', '3', '专科', 'http://www.kjxinxiedu.com/', 'kjxinxiedu', '石家庄');
INSERT INTO `university` VALUES ('1614', '河北司法警官职业学院', '3', '专科', 'http://www.jjgxy.com.cn/', 'jjgxy', '邯郸市');
INSERT INTO `university` VALUES ('1615', '沧州医学高等专科学校', '3', '专科', 'http://www.czmc.cn/', 'czmc', '沧州市');
INSERT INTO `university` VALUES ('1616', '河北女子职业技术学院', '3', '专科', 'http://www.hebnzxy.com/', 'hebnzxy', '石家庄');
INSERT INTO `university` VALUES ('1617', '石家庄医学高等专科学校', '3', '专科', 'http://www.sjzmc.cn/', 'sjzmc', '石家庄');
INSERT INTO `university` VALUES ('1618', '石家庄经济职业学院', '3', '专科', 'http://www.sjzjjxy.net/', 'sjzjjxy', '石家庄');
INSERT INTO `university` VALUES ('1619', '冀中职业学院', '3', '专科', 'http://www.jzhxy.com/', 'jzhxy', '定州市');
INSERT INTO `university` VALUES ('1620', '石家庄人民医学高等专科学校', '3', '专科', 'http://www.sjzrmyz.com/', 'sjzrmyz', '石家庄');
INSERT INTO `university` VALUES ('1621', '石家庄科技工程职业学院', '3', '专科', 'http://www.zdsf.net/', 'zdsf', '石家庄');
INSERT INTO `university` VALUES ('1622', '河北劳动关系职业学院', '3', '专科', 'http://www.hbgy.edu.cn/', 'hbgy', '邯郸市');
INSERT INTO `university` VALUES ('1623', '石家庄科技职业学院', '3', '专科', 'http://www.sjzkjxy.net/', 'sjzkjxy', '石家庄');
INSERT INTO `university` VALUES ('1624', '泊头职业学院', '3', '专科', 'http://www.btzyxy.com.cn/', 'btzyxy', '泊头市');
INSERT INTO `university` VALUES ('1625', '宣化科技职业学院', '3', '专科', 'http://www.xhkjzyxy.com/', 'xhkjzyxy', '张家口');
INSERT INTO `university` VALUES ('1626', '廊坊燕京职业技术学院', '3', '专科', 'http://www.lfyjzjxy.com/', 'lfyjzjxy', '廊坊市');
INSERT INTO `university` VALUES ('1627', '承德护理职业学院', '3', '专科', 'http://www.cdwx.cn/', 'cdwx', '承德市');
INSERT INTO `university` VALUES ('1628', '石家庄幼儿师范高等专科学校', '3', '专科', 'http://www.hbyesf.com/', 'hbyesf', '石家庄');
INSERT INTO `university` VALUES ('1629', '廊坊卫生职业学院', '3', '专科', 'http://www.lfwx.net/', 'lfwx', '廊坊市');
INSERT INTO `university` VALUES ('1630', '河北轨道运输职业技术学院', '3', '专科', 'http://www.hbgdys.cn/', 'hbgdys', '石家庄');
INSERT INTO `university` VALUES ('1631', '保定幼儿师范高等专科学校', '3', '专科', 'http://www.hezs.cn/', 'hezs', '涿州市');
INSERT INTO `university` VALUES ('1632', '河北工艺美术职业学院', '3', '专科', 'http://www.1964.cn/', '1964', '保定市');
INSERT INTO `university` VALUES ('1633', '渤海理工职业学院', '3', '专科', 'http://www.bhlgxy.com/', 'bhlgxy', '黄骅市');
INSERT INTO `university` VALUES ('1634', '唐山幼儿师范高等专科学校', '3', '专科', 'http://www.tsyzh.com/', 'tsyzh', '唐山市');
INSERT INTO `university` VALUES ('1635', '曹妃甸职业技术学院', '3', '专科', 'http://www.cct.edu.cn/', 'cct', '唐山市');
INSERT INTO `university` VALUES ('1636', '山西大学', '4', '本科', 'http://www.sxu.edu.cn/', 'sxu', '太原市');
INSERT INTO `university` VALUES ('1637', '太原科技大学', '4', '本科', 'http://www.tyust.edu.cn/', 'tyust', '太原市');
INSERT INTO `university` VALUES ('1638', '中北大学', '4', '本科', 'http://www.nuc.edu.cn/', 'nuc', '太原市');
INSERT INTO `university` VALUES ('1639', '太原理工大学', '4', '本科', 'http://www.tyut.edu.cn/', 'tyut', '太原市');
INSERT INTO `university` VALUES ('1640', '山西农业大学', '4', '本科', 'http://www.sxau.edu.cn/', 'sxau', '太谷县');
INSERT INTO `university` VALUES ('1641', '山西医科大学', '4', '本科', 'http://www.sxmu.edu.cn/', 'sxmu', '太原市');
INSERT INTO `university` VALUES ('1642', '长治医学院', '4', '本科', 'http://www.czmc.com/', 'czmc', '长治市');
INSERT INTO `university` VALUES ('1643', '山西师范大学', '4', '本科', 'http://www.sxnu.edu.cn/', 'sxnu', '临汾市');
INSERT INTO `university` VALUES ('1644', '太原师范学院', '4', '本科', 'http://www.tynu.edu.cn/', 'tynu', '太原市');
INSERT INTO `university` VALUES ('1645', '山西大同大学', '4', '本科', 'http://www.sxdtdx.edu.cn/', 'sxdtdx', '大同市');
INSERT INTO `university` VALUES ('1646', '晋中学院', '4', '本科', 'http://www.sxjztc.edu.cn/', 'sxjztc', '榆次市');
INSERT INTO `university` VALUES ('1647', '长治学院', '4', '本科', 'http://www.czc.edu.cn/', 'czc', '长治市');
INSERT INTO `university` VALUES ('1648', '运城学院', '4', '本科', 'http://www.ycu.edu.cn/', 'ycu', '运城市');
INSERT INTO `university` VALUES ('1649', '忻州师范学院', '4', '本科', 'http://www.xztc.edu.cn/', 'xztc', '忻州市');
INSERT INTO `university` VALUES ('1650', '山西财经大学', '4', '本科', 'http://www.sxufe.edu.cn/', 'sxufe', '太原市');
INSERT INTO `university` VALUES ('1651', '山西中医药大学', '4', '本科', 'http://www.sxtcm.com/', 'sxtcm', '太原市');
INSERT INTO `university` VALUES ('1652', '吕梁学院', '4', '本科', 'http://www.llhc.edu.cn/', 'llhc', '吕梁市');
INSERT INTO `university` VALUES ('1653', '太原学院', '4', '本科', 'http://www.sxtyu.com/', 'sxtyu', '太原市');
INSERT INTO `university` VALUES ('1654', '山西警察学院', '4', '本科', 'http://www.sxpc.edu.cn/', 'sxpc', '太原市');
INSERT INTO `university` VALUES ('1655', '山西应用科技学院', '4', '本科', 'http://www.sxxh.org/', 'sxxh', '太原市');
INSERT INTO `university` VALUES ('1656', '山西大学商务学院', '4', '本科', 'http://www.sdsy.sxu.edu.cn/', 'sdsy', '太原市');
INSERT INTO `university` VALUES ('1657', '太原理工大学现代科技学院', '4', '本科', 'http://www.xdkj.tyut.edu.cn/', 'xdkj', '太原市');
INSERT INTO `university` VALUES ('1658', '山西农业大学信息学院', '4', '本科', 'http://www.cisau.com.cn/', 'cisau', '太谷县');
INSERT INTO `university` VALUES ('1659', '山西师范大学现代文理学院', '4', '本科', 'http://www.xdwl-sxnu.cn/', 'xdwl-sxnu', '临汾市');
INSERT INTO `university` VALUES ('1660', '中北大学信息商务学院', '4', '本科', 'http://xxsw.nuc.edu.cn/', 'nuc', '太原市');
INSERT INTO `university` VALUES ('1661', '太原科技大学华科学院', '4', '本科', 'http://www.kdhk.cn/', 'kdhk', '太原市');
INSERT INTO `university` VALUES ('1662', '山西医科大学晋祠学院', '4', '本科', 'http://www.sxmu-jcc.com/', 'sxmu-jcc', '太原市');
INSERT INTO `university` VALUES ('1663', '山西财经大学华商学院', '4', '本科', 'http://www.schsxy.com/', 'schsxy', '太原市');
INSERT INTO `university` VALUES ('1664', '山西工商学院', '4', '本科', 'http://www.sxtbu.net/', 'sxtbu', '太原市');
INSERT INTO `university` VALUES ('1665', '太原工业学院', '4', '本科', 'http://www.tit.edu.cn/', 'tit', '太原市');
INSERT INTO `university` VALUES ('1666', '山西传媒学院', '4', '本科', 'http://www.arft.net/', 'arft', '太原市');
INSERT INTO `university` VALUES ('1667', '山西工程技术学院', '4', '本科', 'http://www.sxit.edu.cn/', 'sxit', '阳泉市');
INSERT INTO `university` VALUES ('1668', '山西能源学院', '4', '本科', 'http://www.sxeu.cn/', 'sxeu', '晋中市');
INSERT INTO `university` VALUES ('1669', '山西省财政税务专科学校', '4', '专科', 'http://www.sxftc.edu.cn/', 'sxftc', '太原市');
INSERT INTO `university` VALUES ('1670', '长治职业技术学院', '4', '专科', 'http://www.czzy.cn/', 'czzy', '长治市');
INSERT INTO `university` VALUES ('1671', '山西艺术职业学院', '4', '专科', 'http://www.sxyz.com/', 'sxyz', '太原市');
INSERT INTO `university` VALUES ('1672', '晋城职业技术学院', '4', '专科', 'http://www.sxjczy.cn/', 'sxjczy', '晋城市');
INSERT INTO `university` VALUES ('1673', '山西建筑职业技术学院', '4', '专科', 'http://www.sxatc.com/', 'sxatc', '太原市');
INSERT INTO `university` VALUES ('1674', '山西药科职业学院', '4', '专科', 'http://www.sxbac.net.cn/', 'sxbac', '太原市');
INSERT INTO `university` VALUES ('1675', '山西工程职业技术学院', '4', '专科', 'http://www.sxgy.edu.cn/', 'sxgy', '太原市');
INSERT INTO `university` VALUES ('1676', '山西交通职业技术学院', '4', '专科', 'http://www.sxjt.edu.cn/', 'sxjt', '太原市');
INSERT INTO `university` VALUES ('1677', '大同煤炭职业技术学院', '4', '专科', 'http://www.dtmtxy.cn/', 'dtmtxy', '大同市');
INSERT INTO `university` VALUES ('1678', '山西机电职业技术学院', '4', '专科', 'http://www.sxjdxy.org/', 'sxjdxy', '长治市');
INSERT INTO `university` VALUES ('1679', '山西戏剧职业学院', '4', '专科', 'http://www.shanxixjxy.com/', 'shanxixjxy', '太原市');
INSERT INTO `university` VALUES ('1680', '山西财贸职业技术学院', '4', '专科', 'http://www.sxcmvc.com/', 'sxcmvc', '太原市');
INSERT INTO `university` VALUES ('1681', '山西林业职业技术学院', '4', '专科', 'http://www.sxly.com.cn/', 'sxly', '太原市');
INSERT INTO `university` VALUES ('1682', '山西水利职业技术学院', '4', '专科', 'http://www.sxsy.com.cn/', 'sxsy', '太原市');
INSERT INTO `university` VALUES ('1683', '阳泉职业技术学院', '4', '专科', 'http://www.tyutyqc.edu.cn/', 'tyutyqc', '阳泉市');
INSERT INTO `university` VALUES ('1684', '临汾职业技术学院', '4', '专科', 'http://www.lfvtc.cn/', 'lfvtc', '临汾市');
INSERT INTO `university` VALUES ('1685', '山西职业技术学院', '4', '专科', 'http://www.sxzzy.cn/', 'sxzzy', '太原市');
INSERT INTO `university` VALUES ('1686', '山西煤炭职业技术学院', '4', '专科', 'http://www.sxmtxy.com.cn/', 'sxmtxy', '太原市');
INSERT INTO `university` VALUES ('1687', '山西金融职业学院', '4', '专科', 'http://www.sxjrzyxy.com/', 'sxjrzyxy', '太原市');
INSERT INTO `university` VALUES ('1688', '太原城市职业技术学院', '4', '专科', 'http://www.cntcvc.com/', 'cntcvc', '太原市');
INSERT INTO `university` VALUES ('1689', '山西信息职业技术学院', '4', '专科', 'http://www.vcit.cn/', 'vcit', '临汾市');
INSERT INTO `university` VALUES ('1690', '山西体育职业学院', '4', '专科', 'http://www.sxptc.com/', 'sxptc', '太原市');
INSERT INTO `university` VALUES ('1691', '山西警官职业学院', '4', '专科', 'http://www.sxpolice.cn/', 'sxpolice', '太原市');
INSERT INTO `university` VALUES ('1692', '山西国际商务职业学院', '4', '专科', 'http://www.sxibs.com/', 'sxibs', '太原市');
INSERT INTO `university` VALUES ('1693', '潞安职业技术学院', '4', '专科', 'http://sxlazy.cen114.com/', 'cen114', '长治市');
INSERT INTO `university` VALUES ('1694', '太原旅游职业学院', '4', '专科', 'http://www.tylyzyxy.com/', 'tylyzyxy', '太原市');
INSERT INTO `university` VALUES ('1695', '山西旅游职业学院', '4', '专科', 'http://www.sxtvi.com.cn/', 'sxtvi', '太原市');
INSERT INTO `university` VALUES ('1696', '山西管理职业学院', '4', '专科', 'http://www.sxglzyxy.com.cn/', 'sxglzyxy', '临汾市');
INSERT INTO `university` VALUES ('1697', '山西电力职业技术学院', '4', '专科', 'http://www.vtep.edu.cn/', 'vtep', '太原市');
INSERT INTO `university` VALUES ('1698', '忻州职业技术学院', '4', '专科', 'http://www.xzvtc.com/', 'xzvtc', '忻州市');
INSERT INTO `university` VALUES ('1699', '山西同文职业技术学院', '4', '专科', 'http://www.sxtwedu.com/', 'sxtwedu', '介休市');
INSERT INTO `university` VALUES ('1700', '晋中职业技术学院', '4', '专科', 'http://www.jzzy.sx.cn/', 'jzzy', '晋中市');
INSERT INTO `university` VALUES ('1701', '山西华澳商贸职业学院', '4', '专科', 'http://www.huaao.sx.cn/', 'huaao', '太原市');
INSERT INTO `university` VALUES ('1702', '山西运城农业职业技术学院', '4', '专科', 'http://www.sycnxy.com/', 'sycnxy', '运城市');
INSERT INTO `university` VALUES ('1703', '运城幼儿师范高等专科学校', '4', '专科', 'http://www.sxycys.com/', 'sxycys', '运城市');
INSERT INTO `university` VALUES ('1704', '山西老区职业技术学院', '4', '专科', 'http://www.sxlqzy.cn/', 'sxlqzy', '太原市');
INSERT INTO `university` VALUES ('1705', '山西经贸职业学院', '4', '专科', 'http://www.sxemc.com/', 'sxemc', '太原市');
INSERT INTO `university` VALUES ('1706', '朔州职业技术学院', '4', '专科', 'http://www.szvtc.sx.cn/', 'szvtc', '朔州市');
INSERT INTO `university` VALUES ('1707', '运城职业技术学院', '4', '专科', 'http://www.ycptu.com/', 'ycptu', '运城市');
INSERT INTO `university` VALUES ('1708', '山西轻工职业技术学院', '4', '专科', 'http://www.sxqgzy.cn/', 'sxqgzy', '太原市');
INSERT INTO `university` VALUES ('1709', '晋中师范高等专科学校', '4', '专科', 'http://www.sxjzsf.cn/', 'sxjzsf', '晋中市');
INSERT INTO `university` VALUES ('1710', '阳泉师范高等专科学校', '4', '专科', 'http://www.sxyqsz.cn/', 'sxyqsz', '阳泉市');
INSERT INTO `university` VALUES ('1711', '山西青年职业学院', '4', '专科', 'http://www.sxqzy.cn/', 'sxqzy', '太原市');
INSERT INTO `university` VALUES ('1712', '运城护理职业学院', '4', '专科', 'http://www.ychlxy.com/', 'ychlxy', '运城市');
INSERT INTO `university` VALUES ('1713', '运城师范高等专科学校', '4', '专科', 'http://www.sxycsf.com/', 'sxycsf', '运城市');
INSERT INTO `university` VALUES ('1714', '朔州师范高等专科学校', '4', '专科', 'http://www.szsfdx.com/', 'szsfdx', '朔州市');
INSERT INTO `university` VALUES ('1715', '内蒙古大学', '5', '本科', 'http://www.imu.edu.cn/', 'imu', '呼和浩特');
INSERT INTO `university` VALUES ('1716', '内蒙古科技大学', '5', '本科', 'http://www.imust.cn/', 'imust', '包头市');
INSERT INTO `university` VALUES ('1717', '内蒙古工业大学', '5', '本科', 'http://www.imut.edu.cn/', 'imut', '呼和浩特');
INSERT INTO `university` VALUES ('1718', '内蒙古农业大学', '5', '本科', 'http://www.imau.edu.cn/', 'imau', '呼和浩特');
INSERT INTO `university` VALUES ('1719', '内蒙古医科大学', '5', '本科', 'http://www.immc.edu.cn/', 'immc', '呼和浩特');
INSERT INTO `university` VALUES ('1720', '内蒙古师范大学', '5', '本科', 'http://www.imnu.edu.cn/', 'imnu', '呼和浩特');
INSERT INTO `university` VALUES ('1721', '内蒙古民族大学', '5', '本科', 'http://www.imun.edu.cn/', 'imun', '通辽市');
INSERT INTO `university` VALUES ('1722', '赤峰学院', '5', '本科', 'http://www.cfxy.cn/', 'cfxy', '赤峰市');
INSERT INTO `university` VALUES ('1723', '内蒙古财经大学', '5', '本科', 'http://www.imufe.edu.cn/', 'imufe', '呼和浩特');
INSERT INTO `university` VALUES ('1724', '呼伦贝尔学院', '5', '本科', 'http://www.hlbrc.cn/', 'hlbrc', '呼伦贝尔');
INSERT INTO `university` VALUES ('1725', '集宁师范学院', '5', '本科', 'http://www.jntc.nm.cn/', 'jntc', '乌兰察布');
INSERT INTO `university` VALUES ('1726', '河套学院', '5', '本科', 'http://www.hetaodaxue.com/', 'hetaodaxue', '巴彦淖尔');
INSERT INTO `university` VALUES ('1727', '呼和浩特民族学院', '5', '本科', 'http://www.imnc.edu.cn/', 'imnc', '呼和浩特');
INSERT INTO `university` VALUES ('1728', '内蒙古大学创业学院', '5', '本科', 'http://www.imuchuangye.cn/', 'imuchuangy', '呼和浩特');
INSERT INTO `university` VALUES ('1729', '内蒙古师范大学鸿德学院', '5', '本科', 'http://www.honder.com/', 'honder', '呼和浩特');
INSERT INTO `university` VALUES ('1730', '内蒙古艺术学院', '5', '本科', 'http://www.imac.edu.cn/', 'imac', '呼和浩特');
INSERT INTO `university` VALUES ('1731', '鄂尔多斯应用技术学院', '5', '本科', 'http://www.oit.edu.cn/', 'oit', '鄂尔多斯');
INSERT INTO `university` VALUES ('1732', '内蒙古建筑职业技术学院', '5', '专科', 'http://www.imaa.edu.cn/', 'imaa', '呼和浩特');
INSERT INTO `university` VALUES ('1733', '内蒙古丰州职业学院', '5', '专科', 'http://www.qcdx.net/', 'qcdx', '呼和浩特');
INSERT INTO `university` VALUES ('1734', '包头职业技术学院', '5', '专科', 'http://www.btzyjsxy.cn/', 'btzyjsxy', '包头市');
INSERT INTO `university` VALUES ('1735', '兴安职业技术学院', '5', '专科', 'http://www.nmxzy.cn/', 'nmxzy', '乌兰浩特');
INSERT INTO `university` VALUES ('1736', '呼和浩特职业学院', '5', '专科', 'http://www.hhvc.net.cn/', 'hhvc', '呼和浩特');
INSERT INTO `university` VALUES ('1737', '包头轻工职业技术学院', '5', '专科', 'http://www.btqy.com.cn/', 'btqy', '包头市');
INSERT INTO `university` VALUES ('1738', '内蒙古电子信息职业技术学院', '5', '专科', 'http://www.imeic.cn/', 'imeic', '呼和浩特');
INSERT INTO `university` VALUES ('1739', '内蒙古机电职业技术学院', '5', '专科', 'http://www.nmgjdxy.com/', 'nmgjdxy', '呼和浩特');
INSERT INTO `university` VALUES ('1740', '内蒙古化工职业学院', '5', '专科', 'http://www.hgzyxy.com.cn/', 'hgzyxy', '呼和浩特');
INSERT INTO `university` VALUES ('1741', '内蒙古商贸职业学院', '5', '专科', 'http://www.imvcc.com/', 'imvcc', '呼和浩特');
INSERT INTO `university` VALUES ('1742', '锡林郭勒职业学院', '5', '专科', 'http://www.xlglvc.cn/', 'xlglvc', '锡林浩特');
INSERT INTO `university` VALUES ('1743', '内蒙古警察职业学院', '5', '专科', 'http://www.imppc.cn/', 'imppc', '呼和浩特');
INSERT INTO `university` VALUES ('1744', '内蒙古体育职业学院', '5', '专科', 'http://www.nmtyxy.com/', 'nmtyxy', '呼和浩特');
INSERT INTO `university` VALUES ('1745', '乌兰察布职业学院', '5', '专科', 'http://www.wlcbzyxy.com.cn/', 'wlcbzyxy', '乌兰察布');
INSERT INTO `university` VALUES ('1746', '通辽职业学院', '5', '专科', 'http://www.tlzyxy.com/', 'tlzyxy', '通辽市');
INSERT INTO `university` VALUES ('1747', '科尔沁艺术职业学院', '5', '专科', 'http://www.keqysxy.com.cn/', 'keqysxy', '通辽市');
INSERT INTO `university` VALUES ('1748', '内蒙古交通职业技术学院', '5', '专科', 'http://www.nmjtzy.com/', 'nmjtzy', '赤峰市');
INSERT INTO `university` VALUES ('1749', '包头钢铁职业技术学院', '5', '专科', 'http://www.btsvc.edu.cn/', 'btsvc', '包头市');
INSERT INTO `university` VALUES ('1750', '乌海职业技术学院', '5', '专科', 'http://www.whvtc.net/', 'whvtc', '乌海市');
INSERT INTO `university` VALUES ('1751', '内蒙古科技职业学院', '5', '专科', 'http://www.imstu.org.cn/', 'imstu', '呼和浩特');
INSERT INTO `university` VALUES ('1752', '内蒙古北方职业技术学院', '5', '专科', 'http://www.nmbfxy.com/', 'nmbfxy', '呼和浩特');
INSERT INTO `university` VALUES ('1753', '赤峰职业技术学院', '5', '专科', 'http://www.cfzyjsxy.com/', 'cfzyjsxy', '赤峰市');
INSERT INTO `university` VALUES ('1754', '内蒙古经贸外语职业学院', '5', '专科', 'http://www.nmgjwy.com/', 'nmgjwy', '包头市');
INSERT INTO `university` VALUES ('1755', '包头铁道职业技术学院', '5', '专科', 'http://www.btgx.com/', 'btgx', '包头市');
INSERT INTO `university` VALUES ('1756', '乌兰察布医学高等专科学校', '5', '专科', 'http://www.wlcbswx.com/', 'wlcbswx', '乌兰察布');
INSERT INTO `university` VALUES ('1757', '鄂尔多斯职业学院', '5', '专科', 'http://www.ordosvc.cn/', 'ordosvc', '鄂尔多斯');
INSERT INTO `university` VALUES ('1758', '内蒙古工业职业学院', '5', '专科', 'http://www.nmxuanyuan.cn/', 'nmxuanyuan', '呼和浩特');
INSERT INTO `university` VALUES ('1759', '呼伦贝尔职业技术学院', '5', '专科', 'http://www.hlbrzy.com/', 'hlbrzy', '呼伦贝尔');
INSERT INTO `university` VALUES ('1760', '满洲里俄语职业学院', '5', '专科', 'http://ey.mzlxy.cn/', 'mzlxy', '满洲里');
INSERT INTO `university` VALUES ('1761', '内蒙古能源职业学院', '5', '专科', 'http://www.nmpower.cn/', 'nmpower', '呼和浩特');
INSERT INTO `university` VALUES ('1762', '赤峰工业职业技术学院', '5', '专科', 'http://222.74.246.51/', '74', '赤峰市');
INSERT INTO `university` VALUES ('1763', '阿拉善职业技术学院', '5', '专科', 'http://www.alszyxy.cn/', 'alszyxy', '阿拉善');
INSERT INTO `university` VALUES ('1764', '内蒙古美术职业学院', '5', '专科', 'http://www.nmgmsxy.net/', 'nmgmsxy', '巴彦淖尔');
INSERT INTO `university` VALUES ('1765', '内蒙古民族幼儿师范高等专科学校', '5', '专科', 'http://www.nmmysz.com/', 'nmmysz', '鄂尔多斯');
INSERT INTO `university` VALUES ('1766', '鄂尔多斯生态环境职业学院', '5', '专科', 'http://www.ordosnmxx.com/', 'ordosnmxx', '鄂尔多斯市');
INSERT INTO `university` VALUES ('1767', '扎兰屯职业学院', '5', '专科', 'http://www.zltzyxy.com/', 'zltzyxy', '呼伦贝尔市');
INSERT INTO `university` VALUES ('1768', '辽宁大学', '6', '本科', 'http://www.lnu.edu.cn/', 'lnu', '沈阳市');
INSERT INTO `university` VALUES ('1769', '大连理工大学', '6', '本科', 'http://www.dlut.edu.cn/', 'dlut', '大连市');
INSERT INTO `university` VALUES ('1770', '沈阳工业大学', '6', '本科', 'http://www.sut.edu.cn', 'sut', '沈阳市');
INSERT INTO `university` VALUES ('1771', '沈阳航空航天大学', '6', '本科', 'http://www.sau.edu.cn/', 'sau', '沈阳市');
INSERT INTO `university` VALUES ('1772', '沈阳理工大学', '6', '本科', 'http://www.syit.edu.cn', 'syit', '沈阳市');
INSERT INTO `university` VALUES ('1773', '东北大学', '6', '本科', 'http://www.neu.edu.cn/', 'neu', '沈阳市');
INSERT INTO `university` VALUES ('1774', '辽宁科技大学', '6', '本科', 'http://www.ustl.edu.cn/', 'ustl', '鞍山市');
INSERT INTO `university` VALUES ('1775', '辽宁工程技术大学', '6', '本科', 'http://www.lntu.edu.cn/', 'lntu', '阜新市');
INSERT INTO `university` VALUES ('1776', '辽宁石油化工大学', '6', '本科', 'http://www.lnpu.edu.cn/', 'lnpu', '抚顺市');
INSERT INTO `university` VALUES ('1777', '沈阳化工大学', '6', '本科', 'http://www.syuct.edu.cn/', 'syuct', '沈阳市');
INSERT INTO `university` VALUES ('1778', '大连交通大学', '6', '本科', 'http://www.djtu.edu.cn/', 'djtu', '大连市');
INSERT INTO `university` VALUES ('1779', '大连海事大学', '6', '本科', 'http://www.dlmu.edu.cn/', 'dlmu', '大连市');
INSERT INTO `university` VALUES ('1780', '大连工业大学', '6', '本科', 'http://www.dlpu.edu.cn/', 'dlpu', '大连市');
INSERT INTO `university` VALUES ('1781', '沈阳建筑大学', '6', '本科', 'http://www.sjzu.edu.cn/', 'sjzu', '沈阳市');
INSERT INTO `university` VALUES ('1782', '辽宁工业大学', '6', '本科', 'http://www.lnit.edu.cn/', 'lnit', '锦州市');
INSERT INTO `university` VALUES ('1783', '沈阳农业大学', '6', '本科', 'http://www.syau.edu.cn/', 'syau', '沈阳市');
INSERT INTO `university` VALUES ('1784', '大连海洋大学', '6', '本科', 'http://www.dlou.edu.cn/', 'dlou', '大连市');
INSERT INTO `university` VALUES ('1785', '中国医科大学', '6', '本科', 'http://www.cmu.edu.cn/', 'cmu', '沈阳市');
INSERT INTO `university` VALUES ('1786', '锦州医科大学', '6', '本科', 'http://www.lnmu.edu.cn/', 'lnmu', '锦州市');
INSERT INTO `university` VALUES ('1787', '大连医科大学', '6', '本科', 'http://www.dlmedu.edu.cn/', 'dlmedu', '大连市');
INSERT INTO `university` VALUES ('1788', '辽宁中医药大学', '6', '本科', 'http://www.lnutcm.edu.cn/', 'lnutcm', '沈阳市');
INSERT INTO `university` VALUES ('1789', '沈阳药科大学', '6', '本科', 'http://www.syphu.edu.cn/', 'syphu', '沈阳市');
INSERT INTO `university` VALUES ('1790', '沈阳医学院', '6', '本科', 'http://www.symc.edu.cn/', 'symc', '沈阳市');
INSERT INTO `university` VALUES ('1791', '辽宁师范大学', '6', '本科', 'http://www.lnnu.edu.cn/', 'lnnu', '大连市');
INSERT INTO `university` VALUES ('1792', '沈阳师范大学', '6', '本科', 'http://www.synu.edu.cn', 'synu', '沈阳市');
INSERT INTO `university` VALUES ('1793', '渤海大学', '6', '本科', 'http://www.bhu.edu.cn/', 'bhu', '锦州市');
INSERT INTO `university` VALUES ('1794', '鞍山师范学院', '6', '本科', 'http://www.asnc.edu.cn/', 'asnc', '鞍山市');
INSERT INTO `university` VALUES ('1795', '大连外国语大学', '6', '本科', 'http://www.dlufl.edu.cn', 'dlufl', '大连市');
INSERT INTO `university` VALUES ('1796', '东北财经大学', '6', '本科', 'http://www.dufe.edu.cn/', 'dufe', '大连市');
INSERT INTO `university` VALUES ('1797', '中国刑事警察学院', '6', '本科', 'http://www.npuc.edu.cn/', 'npuc', '沈阳市');
INSERT INTO `university` VALUES ('1798', '沈阳体育学院', '6', '本科', 'http://www.syty.edu.cn/', 'syty', '沈阳市');
INSERT INTO `university` VALUES ('1799', '沈阳音乐学院', '6', '本科', 'http://www.sycm.com.cn/', 'sycm', '沈阳市');
INSERT INTO `university` VALUES ('1800', '鲁迅美术学院', '6', '本科', 'http://www.lumei.edu.cn/', 'lumei', '沈阳市');
INSERT INTO `university` VALUES ('1801', '辽宁对外经贸学院', '6', '本科', 'http://www.luibe.edu.cn/', 'luibe', '大连市');
INSERT INTO `university` VALUES ('1802', '沈阳大学', '6', '本科', 'http://www.syu.edu.cn/', 'syu', '沈阳市');
INSERT INTO `university` VALUES ('1803', '大连大学', '6', '本科', 'http://www.dlu.edu.cn/', 'dlu', '大连市');
INSERT INTO `university` VALUES ('1804', '辽宁科技学院', '6', '本科', 'http://www.lnist.edu.cn/', 'lnist', '本溪市');
INSERT INTO `university` VALUES ('1805', '辽宁警察学院', '6', '本科', 'http://www.lnpc.cn/', 'lnpc', '大连市');
INSERT INTO `university` VALUES ('1806', '沈阳工程学院', '6', '本科', 'http://www.sie.edu.cn/', 'sie', '沈阳市');
INSERT INTO `university` VALUES ('1807', '辽东学院', '6', '本科', 'http://www.ldxy.cn/', 'ldxy', '丹东市');
INSERT INTO `university` VALUES ('1808', '大连民族大学', '6', '本科', 'http://www.dlnu.edu.cn/', 'dlnu', '大连市');
INSERT INTO `university` VALUES ('1809', '大连理工大学城市学院', '6', '本科', 'http://city.dlut.edu.cn/', 'dlut', '大连市');
INSERT INTO `university` VALUES ('1810', '沈阳工业大学工程学院', '6', '本科', 'http://gcxy.sut.edu.cn/', 'sut', '沈阳市');
INSERT INTO `university` VALUES ('1811', '沈阳航空航天大学北方科技学院', '6', '本科', 'http://nstc.sau.edu.cn/', 'sau', '沈阳市');
INSERT INTO `university` VALUES ('1812', '沈阳工学院', '6', '本科', 'http://www.syyyy.com.cn/', 'syyyy', '沈阳市');
INSERT INTO `university` VALUES ('1813', '大连工业大学艺术与信息工程学院', '6', '本科', 'http://www.caie.org/', 'caie', '大连市');
INSERT INTO `university` VALUES ('1814', '大连科技学院', '6', '本科', 'http://www.dist.edu.cn/', 'dist', '大连市');
INSERT INTO `university` VALUES ('1815', '沈阳城市建设学院', '6', '本科', 'http://www.sjcy.cn/', 'sjcy', '沈阳市');
INSERT INTO `university` VALUES ('1816', '中国医科大学临床医药学院', '6', '本科', 'http://www.cmu.edu.cn/cmc/', 'cmu', '沈阳市');
INSERT INTO `university` VALUES ('1817', '大连医科大学中山学院', '6', '本科', 'http://www.dmuzs.edu.cn/', 'dmuzs', '大连市');
INSERT INTO `university` VALUES ('1818', '锦州医科大学医疗学院', '6', '本科', 'http://ylxy.lnmu.edu.cn/', 'lnmu', '锦州市');
INSERT INTO `university` VALUES ('1819', '辽宁师范大学海华学院', '6', '本科', 'http://haihua.lnnu.edu.cn/', 'lnnu', '大连市');
INSERT INTO `university` VALUES ('1820', '辽宁理工学院', '6', '本科', 'http://www.lise.edu.cn/', 'lise', '锦州市');
INSERT INTO `university` VALUES ('1821', '大连财经学院', '6', '本科', 'http://www.kingbridge.net/', 'kingbridge', '大连市');
INSERT INTO `university` VALUES ('1822', '沈阳城市学院', '6', '本科', 'http://www.sdkj-syu.net/', 'sdkj-syu', '沈阳市');
INSERT INTO `university` VALUES ('1823', '辽宁石油化工大学顺华能源学院', '6', '本科', 'http://www.lnshny.com/', 'lnshny', '抚顺市');
INSERT INTO `university` VALUES ('1824', '大连艺术学院', '6', '本科', 'http://www.dac.edu.cn/', 'dac', '大连市');
INSERT INTO `university` VALUES ('1825', '辽宁中医药大学杏林学院', '6', '本科', 'http://www.lncmxl.edu.cn/', 'lncmxl', '沈阳市');
INSERT INTO `university` VALUES ('1826', '辽宁何氏医学院', '6', '本科', 'http://www.he-edu.com/', 'he-edu', '沈阳市');
INSERT INTO `university` VALUES ('1827', '沈阳科技学院', '6', '本科', 'http://www.syuctky.edu.cn/', 'syuctky', '沈阳市');
INSERT INTO `university` VALUES ('1828', '大连东软信息学院', '6', '本科', 'http://www.neusoft.edu.cn/', 'neusoft', '大连市');
INSERT INTO `university` VALUES ('1829', '辽宁财贸学院', '6', '本科', 'http://www.lncmxy.com/', 'lncmxy', '兴城市');
INSERT INTO `university` VALUES ('1830', '辽宁传媒学院', '6', '本科', 'http://www.lncu.cn/', 'lncu', '沈阳市');
INSERT INTO `university` VALUES ('1831', '营口理工学院', '6', '本科', 'http://www.yku.edu.cn/', 'yku', '营口市');
INSERT INTO `university` VALUES ('1832', '朝阳师范高等专科学校', '6', '专科', 'http://www.cysz.com.cn/', 'cysz', '朝阳市');
INSERT INTO `university` VALUES ('1833', '抚顺师范高等专科学校', '6', '专科', 'http://www.fstc.cn/', 'fstc', '抚顺市');
INSERT INTO `university` VALUES ('1834', '锦州师范高等专科学校', '6', '专科', 'http://www.jzsz.com.cn/', 'jzsz', '锦州市');
INSERT INTO `university` VALUES ('1835', '营口职业技术学院', '6', '专科', 'http://www.ykdx.net/', 'ykdx', '营口市');
INSERT INTO `university` VALUES ('1836', '铁岭师范高等专科学校', '6', '专科', 'http://www.tlsz.com.cn/', 'tlsz', '铁岭市');
INSERT INTO `university` VALUES ('1837', '大连职业技术学院', '6', '专科', 'http://www.dlvtc.edu.cn/', 'dlvtc', '大连市');
INSERT INTO `university` VALUES ('1838', '辽宁农业职业技术学院', '6', '专科', 'http://www.lnnzy.ln.cn/', 'lnnzy', '营口市');
INSERT INTO `university` VALUES ('1839', '抚顺职业技术学院', '6', '专科', 'http://www.fvti.com/', 'fvti', '抚顺市');
INSERT INTO `university` VALUES ('1840', '辽阳职业技术学院', '6', '专科', 'http://www.419.com.cn/', '419', '辽阳市');
INSERT INTO `university` VALUES ('1841', '阜新高等专科学校', '6', '专科', 'http://www.fxgz.com.cn/', 'fxgz', '阜新市');
INSERT INTO `university` VALUES ('1842', '辽宁交通高等专科学校', '6', '专科', 'http://www.lncc.edu.cn/', 'lncc', '沈阳市');
INSERT INTO `university` VALUES ('1843', '辽宁税务高等专科学校', '6', '专科', 'http://www.dltaxedu.com/', 'dltaxedu', '大连市');
INSERT INTO `university` VALUES ('1844', '盘锦职业技术学院', '6', '专科', 'http://www.pjzy.net.cn/', 'pjzy', '盘锦市');
INSERT INTO `university` VALUES ('1845', '沈阳航空职业技术学院', '6', '专科', 'http://www.syhzy.cn/', 'syhzy', '沈阳市');
INSERT INTO `university` VALUES ('1846', '辽宁职业学院', '6', '专科', 'http://www.lnvc.cn/', 'lnvc', '铁岭市');
INSERT INTO `university` VALUES ('1847', '辽宁林业职业技术学院', '6', '专科', 'http://www.lnlzy.cn/', 'lnlzy', '沈阳市');
INSERT INTO `university` VALUES ('1848', '沈阳职业技术学院', '6', '专科', 'http://www.vtcsy.com/', 'vtcsy', '沈阳市');
INSERT INTO `university` VALUES ('1849', '辽宁理工职业学院', '6', '专科', 'http://www.lndhdx.com/', 'lndhdx', '锦州市');
INSERT INTO `university` VALUES ('1850', '大连商务职业学院', '6', '专科', 'http://www.dlswedu.com/', 'dlswedu', '大连市');
INSERT INTO `university` VALUES ('1851', '辽宁金融职业学院', '6', '专科', 'http://www.lnfvc.cn', 'lnfvc', '沈阳市');
INSERT INTO `university` VALUES ('1852', '辽宁轨道交通职业学院', '6', '专科', 'http://www.stjx.com.cn/', 'stjx', '沈阳市');
INSERT INTO `university` VALUES ('1853', '辽宁广告职业学院', '6', '专科', 'http://www.ggxy.com/', 'ggxy', '沈阳市');
INSERT INTO `university` VALUES ('1854', '辽宁机电职业技术学院', '6', '专科', 'http://www.lnmec.net.cn/', 'lnmec', '丹东市');
INSERT INTO `university` VALUES ('1855', '辽宁经济职业技术学院', '6', '专科', 'http://www.lnemci.com/', 'lnemci', '沈阳市');
INSERT INTO `university` VALUES ('1856', '辽宁石化职业技术学院', '6', '专科', 'http://www.lnpc.edu.cn/', 'lnpc', '锦州市');
INSERT INTO `university` VALUES ('1857', '渤海船舶职业学院', '6', '专科', 'http://www.bhcy.cn/', 'bhcy', '葫芦岛');
INSERT INTO `university` VALUES ('1858', '大连软件职业学院', '6', '专科', 'http://www.rjedu.com/', 'rjedu', '大连市');
INSERT INTO `university` VALUES ('1859', '大连翻译职业学院', '6', '专科', 'http://www.dltcedu.org/', 'dltcedu', '大连市');
INSERT INTO `university` VALUES ('1860', '辽宁商贸职业学院', '6', '专科', 'http://www.lnsmzy.edu.cn/', 'lnsmzy', '沈阳市');
INSERT INTO `university` VALUES ('1861', '大连枫叶职业技术学院', '6', '专科', 'http://www.dmlit.cn/', 'dmlit', '大连市');
INSERT INTO `university` VALUES ('1862', '辽宁装备制造职业技术学院', '6', '专科', 'http://www.ltcem.com/', 'ltcem', '沈阳市');
INSERT INTO `university` VALUES ('1863', '辽河石油职业技术学院', '6', '专科', 'http://www.lhptc.com/', 'lhptc', '盘锦市');
INSERT INTO `university` VALUES ('1864', '辽宁地质工程职业学院', '6', '专科', 'http://www.lndzxy.com/', 'lndzxy', '丹东市');
INSERT INTO `university` VALUES ('1865', '辽宁铁道职业技术学院', '6', '专科', 'http://www.jztlyx.com/', 'jztlyx', '锦州市');
INSERT INTO `university` VALUES ('1866', '辽宁建筑职业学院', '6', '专科', 'http://www.lnjzxy.com/', 'lnjzxy', '辽阳市');
INSERT INTO `university` VALUES ('1867', '大连航运职业技术学院', '6', '专科', 'http://www.dlsc.net.cn/', 'dlsc', '大连市');
INSERT INTO `university` VALUES ('1868', '大连装备制造职业技术学院', '6', '专科', 'http://www.dlzbzzedu.com/', 'dlzbzzedu', '大连市');
INSERT INTO `university` VALUES ('1869', '大连汽车职业技术学院', '6', '专科', 'http://www.dlqcxy.com/', 'dlqcxy', '大连市');
INSERT INTO `university` VALUES ('1870', '辽宁现代服务职业技术学院', '6', '专科', 'http://www.lnxdfwxy.com/', 'lnxdfwxy', '沈阳市');
INSERT INTO `university` VALUES ('1871', '辽宁冶金职业技术学院', '6', '专科', 'http://www.lnyj.net/', 'lnyj', '本溪市');
INSERT INTO `university` VALUES ('1872', '辽宁工程职业学院', '6', '专科', 'http://www.lngczyxy.com/', 'lngczyxy', '铁岭市');
INSERT INTO `university` VALUES ('1873', '辽宁城市建设职业技术学院', '6', '专科', 'http://www.lncjxy.com/', 'lncjxy', '沈阳市');
INSERT INTO `university` VALUES ('1874', '辽宁医药职业学院', '6', '专科', 'http://www.lnwsxy-edu.com/', 'lnwsxy-edu', '沈阳市');
INSERT INTO `university` VALUES ('1875', '铁岭卫生职业学院', '6', '专科', 'http://www.lntlhc.com/', 'lntlhc', '铁岭市');
INSERT INTO `university` VALUES ('1876', '沈阳北软信息职业技术学院', '6', '专科', 'http://www.nsi-soft.com/', 'nsi-soft', '沈阳市');
INSERT INTO `university` VALUES ('1877', '辽宁政法职业学院', '6', '专科', 'http://www.lacpj.com/', 'lacpj', '沈阳市');
INSERT INTO `university` VALUES ('1878', '辽宁民族师范高等专科学校', '6', '专科', 'http://www.lnkn.edu.cn/', 'lnkn', '沈阳市');
INSERT INTO `university` VALUES ('1879', '辽宁轻工职业学院', '6', '专科', 'http://www.lnqg.com.cn/', 'lnqg', '大连市');
INSERT INTO `university` VALUES ('1880', '辽宁水利职业学院', '6', '专科', 'http://www.sngzy.cn/', 'sngzy', '沈阳市');
INSERT INTO `university` VALUES ('1881', '辽宁特殊教育师范高等专科学校', '6', '专科', 'http://www.lntjw.com/', 'lntjw', '沈阳市');
INSERT INTO `university` VALUES ('1882', '吉林大学', '7', '本科', 'http://www.jlu.edu.cn/', 'jlu', '长春市');
INSERT INTO `university` VALUES ('1883', '延边大学', '7', '本科', 'http://www.ybu.edu.cn/', 'ybu', '延吉市');
INSERT INTO `university` VALUES ('1884', '长春理工大学', '7', '本科', 'http://www.cust.edu.cn/', 'cust', '长春市');
INSERT INTO `university` VALUES ('1885', '东北电力大学', '7', '本科', 'http://www.nedu.edu.cn/', 'nedu', '吉林市');
INSERT INTO `university` VALUES ('1886', '长春工业大学', '7', '本科', 'http://www.ccut.edu.cn/', 'ccut', '长春市');
INSERT INTO `university` VALUES ('1887', '吉林建筑大学', '7', '本科', 'http://www.jliae.edu.cn/', 'jliae', '长春市');
INSERT INTO `university` VALUES ('1888', '吉林化工学院', '7', '本科', 'http://www.jlict.edu.cn/', 'jlict', '吉林市');
INSERT INTO `university` VALUES ('1889', '吉林农业大学', '7', '本科', 'http://www.jlau.edu.cn/', 'jlau', '长春市');
INSERT INTO `university` VALUES ('1890', '长春中医药大学', '7', '本科', 'http://www.ccucm.edu.cn/', 'ccucm', '长春市');
INSERT INTO `university` VALUES ('1891', '东北师范大学', '7', '本科', 'http://www.nenu.edu.cn/', 'nenu', '长春市');
INSERT INTO `university` VALUES ('1892', '北华大学', '7', '本科', 'http://www.beihua.edu.cn/', 'beihua', '吉林市');
INSERT INTO `university` VALUES ('1893', '通化师范学院', '7', '本科', 'http://www.thnu.edu.cn/', 'thnu', '通化市');
INSERT INTO `university` VALUES ('1894', '吉林师范大学', '7', '本科', 'http://www.jlnu.edu.cn/', 'jlnu', '四平市');
INSERT INTO `university` VALUES ('1895', '吉林工程技术师范学院', '7', '本科', 'http://www.jltiet.net/', 'jltiet', '长春市');
INSERT INTO `university` VALUES ('1896', '长春师范大学', '7', '本科', 'http://www.cncnc.edu.cn/', 'cncnc', '长春市');
INSERT INTO `university` VALUES ('1897', '白城师范学院', '7', '本科', 'http://www.bcsfxy.com/', 'bcsfxy', '白城市');
INSERT INTO `university` VALUES ('1898', '吉林财经大学', '7', '本科', 'http://www.ctu.cc.jl.cn/', 'ctu', '长春市');
INSERT INTO `university` VALUES ('1899', '吉林体育学院', '7', '本科', 'http://www.jlsu.edu.cn/', 'jlsu', '长春市');
INSERT INTO `university` VALUES ('1900', '吉林艺术学院', '7', '本科', 'http://www.jlart.edu.cn/', 'jlart', '长春市');
INSERT INTO `university` VALUES ('1901', '吉林华桥外国语学院', '7', '本科', 'http://www.hqwy.com/', 'hqwy', '长春市');
INSERT INTO `university` VALUES ('1902', '吉林工商学院', '7', '本科', 'http://www.jlbtc.edu.cn/', 'jlbtc', '长春市');
INSERT INTO `university` VALUES ('1903', '长春工程学院', '7', '本科', 'http://www.ccit.edu.cn/', 'ccit', '长春市');
INSERT INTO `university` VALUES ('1904', '吉林农业科技学院', '7', '本科', 'http://www.jlnku.com/', 'jlnku', '吉林市');
INSERT INTO `university` VALUES ('1905', '吉林警察学院', '7', '本科', 'http://www.jljcxy.com/', 'jljcxy', '长春市');
INSERT INTO `university` VALUES ('1906', '长春大学', '7', '本科', 'http://www.ccu.edu.cn/', 'ccu', '长春市');
INSERT INTO `university` VALUES ('1907', '长春光华学院', '7', '本科', 'http://www.ccughc.net/', 'ccughc', '长春市');
INSERT INTO `university` VALUES ('1908', '长春工业大学人文信息学院', '7', '本科', 'http://www.ccutchi.com/', 'ccutchi', '长春市');
INSERT INTO `university` VALUES ('1909', '长春理工大学光电信息学院', '7', '本科', 'http://www.csoei.com/', 'csoei', '长春市');
INSERT INTO `university` VALUES ('1910', '长春财经学院', '7', '本科', 'http://www.ccufe.com/', 'ccufe', '长春市');
INSERT INTO `university` VALUES ('1911', '吉林建筑大学城建学院', '7', '本科', 'http://www.jlucc.net/', 'jlucc', '长春市');
INSERT INTO `university` VALUES ('1912', '长春建筑学院', '7', '本科', 'http://www.jladi.com/', 'jladi', '长春市');
INSERT INTO `university` VALUES ('1913', '长春科技学院', '7', '本科', 'http://www.jlaudev.com.cn/', 'jlaudev', '长春市');
INSERT INTO `university` VALUES ('1914', '吉林动画学院', '7', '本科', 'http://www.jldh.com.cn/', 'jldh', '长春市');
INSERT INTO `university` VALUES ('1915', '吉林师范大学博达学院', '7', '本科', 'http://www.bdxy.com.cn/', 'bdxy', '四平市');
INSERT INTO `university` VALUES ('1916', '长春大学旅游学院', '7', '本科', 'http://www.cctourcollege.com/', 'cctourcoll', '长春市');
INSERT INTO `university` VALUES ('1917', '东北师范大学人文学院', '7', '本科', 'http://www.chsnenu.edu.cn/', 'chsnenu', '长春市');
INSERT INTO `university` VALUES ('1918', '吉林医药学院', '7', '本科', 'http://www.jlmu.cn/', 'jlmu', '吉林市');
INSERT INTO `university` VALUES ('1919', '长春师范高等专科学校', '7', '专科', 'http://ccsfgz.com/', 'com/', '长春市');
INSERT INTO `university` VALUES ('1920', '辽源职业技术学院', '7', '专科', 'http://www.lyvtc.cn/', 'lyvtc', '辽源市');
INSERT INTO `university` VALUES ('1921', '四平职业大学', '7', '专科', 'http://www.jlsppc.cn/', 'jlsppc', '四平市');
INSERT INTO `university` VALUES ('1922', '长春汽车工业高等专科学校', '7', '专科', 'http://www.caii.edu.cn/', 'caii', '长春市');
INSERT INTO `university` VALUES ('1923', '长春金融高等专科学校', '7', '专科', 'http://www.cjgz.edu.cn/', 'cjgz', '长春市');
INSERT INTO `university` VALUES ('1924', '长春医学高等专科学校', '7', '专科', 'http://www.cmcedu.com/', 'cmcedu', '长春市');
INSERT INTO `university` VALUES ('1925', '吉林交通职业技术学院', '7', '专科', 'http://www.jjtc.com.cn/', 'jjtc', '长春市');
INSERT INTO `university` VALUES ('1926', '长春东方职业学院', '7', '专科', 'http://www.dfzyxy.net/', 'dfzyxy', '长春市');
INSERT INTO `university` VALUES ('1927', '吉林司法警官职业学院', '7', '专科', 'http://www.jlsfjy.cn/', 'jlsfjy', '长春市');
INSERT INTO `university` VALUES ('1928', '吉林电子信息职业技术学院', '7', '专科', 'http://www.jltc.edu.cn/', 'jltc', '吉林市');
INSERT INTO `university` VALUES ('1929', '吉林工业职业技术学院', '7', '专科', 'http://www.jvcit.edu.cn/', 'jvcit', '吉林市');
INSERT INTO `university` VALUES ('1930', '吉林工程职业学院', '7', '专科', 'http://www.jlevc.cn/', 'jlevc', '四平市');
INSERT INTO `university` VALUES ('1931', '长春职业技术学院', '7', '专科', 'http://www.cvit.com.cn/', 'cvit', '长春市');
INSERT INTO `university` VALUES ('1932', '白城医学高等专科学校', '7', '专科', 'http://www.bcyz.cn/', 'bcyz', '白城市');
INSERT INTO `university` VALUES ('1933', '长春信息技术职业学院', '7', '专科', 'http://www.citpc.net/', 'citpc', '长春市');
INSERT INTO `university` VALUES ('1934', '松原职业技术学院', '7', '专科', 'http://www.sypt.cn/', 'sypt', '松原市');
INSERT INTO `university` VALUES ('1935', '吉林铁道职业技术学院', '7', '专科', 'http://www.jtpt.cn/', 'jtpt', '吉林市');
INSERT INTO `university` VALUES ('1936', '白城职业技术学院', '7', '专科', 'http://www.bcvit.cn/', 'bcvit', '白城市');
INSERT INTO `university` VALUES ('1937', '长白山职业技术学院', '7', '专科', 'http://www.cbsvtc.com.cn/', 'cbsvtc', '白山市');
INSERT INTO `university` VALUES ('1938', '吉林科技职业技术学院', '7', '专科', 'http://www.jilinkj.com/', 'jilinkj', '长春市');
INSERT INTO `university` VALUES ('1939', '延边职业技术学院', '7', '专科', 'http://www.ybvtc.com/', 'ybvtc', '延边市');
INSERT INTO `university` VALUES ('1940', '吉林城市职业技术学院', '7', '专科', 'http://www.jlcsxy.com/', 'jlcsxy', '长春市');
INSERT INTO `university` VALUES ('1941', '吉林职业技术学院', '7', '专科', 'http://www.jlhtedu.com/', 'jlhtedu', '龙井市');
INSERT INTO `university` VALUES ('1942', '吉林水利电力职业学院', '7', '专科', 'http://www.jlsdzy.cn/', 'jlsdzy', '长春市');
INSERT INTO `university` VALUES ('1943', '吉林对外经贸职业学院', '7', '专科', 'http://www.jvcfte.com.cn/', 'jvcfte', '长春市');
INSERT INTO `university` VALUES ('1944', '黑龙江大学', '8', '本科', 'http://www.hlju.edu.cn/', 'hlju', '哈尔滨市');
INSERT INTO `university` VALUES ('1945', '哈尔滨工业大学', '8', '本科', 'http://www.hit.edu.cn/', 'hit', null);
INSERT INTO `university` VALUES ('1946', '哈尔滨理工大学', '8', '本科', 'http://www.hrbust.edu.cn/', 'hrbust', '哈尔滨市');
INSERT INTO `university` VALUES ('1947', '哈尔滨工程大学', '8', '本科', 'http://www.hrbeu.edu.cn/', 'hrbeu', null);
INSERT INTO `university` VALUES ('1948', '黑龙江科技大学', '8', '本科', 'http://www.usth.edu.cn/', 'usth', '哈尔滨市');
INSERT INTO `university` VALUES ('1949', '东北石油大学', '8', '本科', 'http://www.nepu.edu.cn/', 'nepu', '大庆市');
INSERT INTO `university` VALUES ('1950', '佳木斯大学', '8', '本科', 'http://www.jmsu.org/', 'jmsu', '佳木斯');
INSERT INTO `university` VALUES ('1951', '黑龙江八一农垦大学', '8', '本科', 'http://www.hlau.cn/', 'hlau', '大庆市');
INSERT INTO `university` VALUES ('1952', '东北农业大学', '8', '本科', 'http://www.neau.edu.cn/', 'neau', '哈尔滨市');
INSERT INTO `university` VALUES ('1953', '东北林业大学', '8', '本科', 'http://www.nefu.edu.cn/', 'nefu', '哈尔滨市');
INSERT INTO `university` VALUES ('1954', '哈尔滨医科大学', '8', '本科', 'http://www.hrbmu.edu.cn/', 'hrbmu', '哈尔滨市');
INSERT INTO `university` VALUES ('1955', '黑龙江中医药大学', '8', '本科', 'http://www.hljucm.net/', 'hljucm', '哈尔滨市');
INSERT INTO `university` VALUES ('1956', '牡丹江医学院', '8', '本科', 'http://www.mdjmu.cn/', 'mdjmu', '牡丹江市');
INSERT INTO `university` VALUES ('1957', '哈尔滨师范大学', '8', '本科', 'http://www.hrbnu.edu.cn/', 'hrbnu', '哈尔滨市');
INSERT INTO `university` VALUES ('1958', '齐齐哈尔大学', '8', '本科', 'http://www.qqhru.edu.cn/', 'qqhru', '齐齐哈尔');
INSERT INTO `university` VALUES ('1959', '牡丹江师范学院', '8', '本科', 'http://www.mdjnu.cn/', 'mdjnu', '牡丹江市');
INSERT INTO `university` VALUES ('1960', '哈尔滨学院', '8', '本科', 'http://www.hrbu.edu.cn/', 'hrbu', '哈尔滨市');
INSERT INTO `university` VALUES ('1961', '大庆师范学院', '8', '本科', 'http://www.dqsy.net/', 'dqsy', '大庆市');
INSERT INTO `university` VALUES ('1962', '绥化学院', '8', '本科', 'http://www.shxy.net/', 'shxy', '绥化市');
INSERT INTO `university` VALUES ('1963', '哈尔滨商业大学', '8', '本科', 'http://www.hrbcu.edu.cn/', 'hrbcu', '哈尔滨市');
INSERT INTO `university` VALUES ('1964', '哈尔滨体育学院', '8', '本科', 'http://www.hrbipe.edu.cn/', 'hrbipe', '哈尔滨市');
INSERT INTO `university` VALUES ('1965', '哈尔滨金融学院', '8', '本科', 'http://www.hrbfu.edu.cn/', 'hrbfu', '哈尔滨市');
INSERT INTO `university` VALUES ('1966', '齐齐哈尔医学院', '8', '本科', 'http://www.qqhrmu.cn/', 'qqhrmu', '齐齐哈尔');
INSERT INTO `university` VALUES ('1967', '黑龙江工业学院', '8', '本科', 'http://www.hljgyxy.net/', 'hljgyxy', '鸡西市');
INSERT INTO `university` VALUES ('1968', '黑龙江东方学院', '8', '本科', 'http://www.dfxy.net/', 'dfxy', null);
INSERT INTO `university` VALUES ('1969', '哈尔滨信息工程学院', '8', '本科', 'http://www.hxci.com.cn/', 'hxci', null);
INSERT INTO `university` VALUES ('1970', '黑龙江工程学院', '8', '本科', 'http://www.hljit.edu.cn/', 'hljit', '哈尔滨市');
INSERT INTO `university` VALUES ('1971', '齐齐哈尔工程学院', '8', '本科', 'http://www.qqhrit.com/', 'qqhrit', null);
INSERT INTO `university` VALUES ('1972', '黑龙江外国语学院', '8', '本科', 'http://www.hiu.edu.cn/', 'hiu', null);
INSERT INTO `university` VALUES ('1973', '黑龙江财经学院', '8', '本科', 'http://www.hfu.edu.cn/', 'hfu', null);
INSERT INTO `university` VALUES ('1974', '哈尔滨石油学院', '8', '本科', 'http://www.hr-edu.com/', 'hr-edu', null);
INSERT INTO `university` VALUES ('1975', '黑龙江工商学院', '8', '本科', 'http://www.hgs-edu.cn/', 'hgs-edu', null);
INSERT INTO `university` VALUES ('1976', '哈尔滨远东理工学院', '8', '本科', 'http://www.fe-edu.com.cn/', 'fe-edu', null);
INSERT INTO `university` VALUES ('1977', '哈尔滨剑桥学院', '8', '本科', 'http://www.jqu.net.cn/', 'jqu', null);
INSERT INTO `university` VALUES ('1978', '黑龙江工程学院昆仑旅游学院', '8', '本科', 'http://www.kllyxy.com/', 'kllyxy', null);
INSERT INTO `university` VALUES ('1979', '哈尔滨广厦学院', '8', '本科', 'http://www.gsxy.cn/', 'gsxy', null);
INSERT INTO `university` VALUES ('1980', '哈尔滨华德学院', '8', '本科', 'http://www.hithd.net/', 'hithd', null);
INSERT INTO `university` VALUES ('1981', '黑河学院', '8', '本科', 'http://www.hhxyzsb.com/', 'hhxyzsb', '黑河市');
INSERT INTO `university` VALUES ('1982', '哈尔滨音乐学院', '8', '本科', 'http://www.hrbcm.edu.cn/', 'hrbcm', '哈尔滨市');
INSERT INTO `university` VALUES ('1983', '齐齐哈尔高等师范专科学校', '8', '专科', 'http://www.qqhrtc.com/', 'qqhrtc', '齐齐哈尔');
INSERT INTO `university` VALUES ('1984', '伊春职业学院', '8', '专科', 'http://www.ycvc.com.cn/', 'ycvc', '伊春市');
INSERT INTO `university` VALUES ('1985', '牡丹江大学', '8', '专科', 'http://www.mdjdx.cn/', 'mdjdx', '牡丹江市');
INSERT INTO `university` VALUES ('1986', '黑龙江职业学院', '8', '专科', 'http://www.hljp.edu.cn/', 'hljp', '哈尔滨市');
INSERT INTO `university` VALUES ('1987', '<a href=\"cuabout.asp', '8', '', 'http://www.hcc.net.cn/', 'hcc', null);
INSERT INTO `university` VALUES ('1988', '黑龙江艺术职业学院', '8', '专科', 'http://www.hljyzy.org.cn/', 'hljyzy', '哈尔滨市');
INSERT INTO `university` VALUES ('1989', '大庆职业学院', '8', '专科', 'http://www.dqzyxy.net/', 'dqzyxy', '大庆市');
INSERT INTO `university` VALUES ('1990', '黑龙江林业职业技术学院', '8', '专科', 'http://www.hljlzy.com/', 'hljlzy', '牡丹江市');
INSERT INTO `university` VALUES ('1991', '黑龙江农业职业技术学院', '8', '专科', 'http://www.hljnzy.net/', 'hljnzy', '佳木斯市');
INSERT INTO `university` VALUES ('1992', '黑龙江农业工程职业学院', '8', '专科', 'http://www.hngzy.com/', 'hngzy', '哈尔滨市');
INSERT INTO `university` VALUES ('1993', '黑龙江农垦职业学院', '8', '专科', 'http://www.nkzy.com/', 'nkzy', '哈尔滨市');
INSERT INTO `university` VALUES ('1994', '黑龙江司法警官职业学院', '8', '专科', 'http://www.hlsfjx.com/', 'hlsfjx', '哈尔滨市');
INSERT INTO `university` VALUES ('1995', '鹤岗师范高等专科学校', '8', '专科', 'http://www.hgtc.org.cn/', 'hgtc', '鹤岗市');
INSERT INTO `university` VALUES ('1996', '哈尔滨电力职业技术学院', '8', '专科', 'http://www.hl.sgcc.com.cn/hdy/', 'hl', '哈尔滨市');
INSERT INTO `university` VALUES ('1997', '哈尔滨铁道职业技术学院', '8', '专科', 'http://www.htxy.net/', 'htxy', '哈尔滨市');
INSERT INTO `university` VALUES ('1998', '大兴安岭职业学院', '8', '专科', 'http://www.dxalu.com/', 'dxalu', '大兴安岭');
INSERT INTO `university` VALUES ('1999', '黑龙江农业经济职业学院', '8', '专科', 'http://www.nyjj.net.cn/', 'nyjj', '牡丹江市');
INSERT INTO `university` VALUES ('2000', '哈尔滨职业技术学院', '8', '专科', 'http://www.hzjxy.net/', 'hzjxy', '哈尔滨市');
INSERT INTO `university` VALUES ('2001', '哈尔滨传媒职业学院', '8', '专科', 'http://www.hrbmcc.com/', 'hrbmcc', null);
INSERT INTO `university` VALUES ('2002', '黑龙江生物科技职业学院', '8', '专科', 'http://www.swkj.net/', 'swkj', '哈尔滨市');
INSERT INTO `university` VALUES ('2003', '黑龙江商业职业学院', '8', '专科', 'http://www.hljszy.net/', 'hljszy', '牡丹江市');
INSERT INTO `university` VALUES ('2004', '黑龙江公安警官职业学院', '8', '专科', 'http://www.hlpolice.com/', 'hlpolice', '哈尔滨市');
INSERT INTO `university` VALUES ('2005', '黑龙江信息技术职业学院', '8', '专科', 'http://www.hljitpc.com/', 'hljitpc', '哈尔滨市');
INSERT INTO `university` VALUES ('2006', '哈尔滨城市职业学院', '8', '专科', 'http://www.13451.cn/', '13451', null);
INSERT INTO `university` VALUES ('2007', '黑龙江农垦科技职业学院', '8', '专科', 'http://www.nkkjxy.com/', 'nkkjxy', '哈尔滨市');
INSERT INTO `university` VALUES ('2008', '黑龙江旅游职业技术学院', '8', '专科', 'http://www.ljly.net/', 'ljly', '哈尔滨市');
INSERT INTO `university` VALUES ('2009', '黑龙江三江美术职业学院', '8', '专科', 'http://www.sjmsxy.net.cn/', 'sjmsxy', null);
INSERT INTO `university` VALUES ('2010', '黑龙江生态工程职业学院', '8', '专科', 'http://www.hfmc.net/', 'hfmc', '哈尔滨市');
INSERT INTO `university` VALUES ('2011', '黑龙江能源职业学院', '8', '专科', 'http://www.hmzy.cn/', 'hmzy', '双鸭山市');
INSERT INTO `university` VALUES ('2012', '七台河职业学院', '8', '专科', 'http://www.qthzyxy.com/', 'qthzyxy', '七台河市');
INSERT INTO `university` VALUES ('2013', '黑龙江民族职业学院', '8', '专科', 'http://www.mvcollege.com/', 'mvcollege', '哈尔滨市');
INSERT INTO `university` VALUES ('2014', '大庆医学高等专科学校', '8', '专科', 'http://dqyz.petrodaqing.com/', 'petrodaqin', '大庆市');
INSERT INTO `university` VALUES ('2015', '黑龙江交通职业技术学院', '8', '专科', 'http://www.hlcp.com.cn/', 'hlcp', '齐齐哈尔');
INSERT INTO `university` VALUES ('2016', '哈尔滨应用职业技术学院', '8', '专科', 'http://www.hyyzy.com/', 'hyyzy', null);
INSERT INTO `university` VALUES ('2017', '黑龙江幼儿师范高等专科学校', '8', '专科', 'http://www.hljys.cn/', 'hljys', '牡丹江市');
INSERT INTO `university` VALUES ('2018', '哈尔滨科学技术职业学院', '8', '专科', 'http://www.hrbkjzy.cn/', 'hrbkjzy', '哈尔滨市');
INSERT INTO `university` VALUES ('2019', '黑龙江粮食职业学院', '8', '专科', 'http://www.hljgvc.com/', 'hljgvc', '哈尔滨市');
INSERT INTO `university` VALUES ('2020', '佳木斯职业学院', '8', '专科', 'http://www.jmszy.org.cn/', 'jmszy', '佳木斯市');
INSERT INTO `university` VALUES ('2021', '黑龙江护理高等专科学校', '8', '专科', 'http://www.hljhlgz.com/', 'hljhlgz', '哈尔滨市');
INSERT INTO `university` VALUES ('2022', '齐齐哈尔理工职业学院', '8', '专科', 'http://www.qlgxy.com/', 'qlgxy', null);
INSERT INTO `university` VALUES ('2023', '哈尔滨幼儿师范高等专科学校', '8', '专科', 'http://www.hayouzhuan.com/', 'hayouzhuan', '哈尔滨市');
INSERT INTO `university` VALUES ('2024', '黑龙江冰雪体育职业学院', '8', '专科', 'http://www.vcws.edu.cn/', 'vcws', '哈尔滨市');
INSERT INTO `university` VALUES ('2025', '复旦大学', '9', '本科', 'http://www.fudan.edu.cn/', 'fudan', '上海市');
INSERT INTO `university` VALUES ('2026', '同济大学', '9', '本科', 'http://www.tongji.edu.cn/', 'tongji', '上海市');
INSERT INTO `university` VALUES ('2027', '上海交通大学', '9', '本科', 'http://www.sjtu.edu.cn/', 'sjtu', '上海市');
INSERT INTO `university` VALUES ('2028', '华东理工大学', '9', '本科', 'http://www.ecust.edu.cn/', 'ecust', '上海市');
INSERT INTO `university` VALUES ('2029', '上海理工大学', '9', '本科', 'http://www.usst.edu.cn/', 'usst', '上海市');
INSERT INTO `university` VALUES ('2030', '上海海事大学', '9', '本科', 'http://www.shmtu.edu.cn/', 'shmtu', '上海市');
INSERT INTO `university` VALUES ('2031', '东华大学', '9', '本科', 'http://www.dhu.edu.cn/', 'dhu', '上海市');
INSERT INTO `university` VALUES ('2032', '上海电力学院', '9', '本科', 'http://www.shiep.edu.cn', 'shiep', '上海市');
INSERT INTO `university` VALUES ('2033', '上海应用技术大学', '9', '本科', 'http://www.sit.edu.cn/', 'sit', '上海市');
INSERT INTO `university` VALUES ('2034', '上海健康医学院', '9', '本科', 'http://www.sumhs.edu.cn/', 'sumhs', '上海市');
INSERT INTO `university` VALUES ('2035', '上海海洋大学', '9', '本科', 'http://www.shou.edu.cn/', 'shou', '上海市');
INSERT INTO `university` VALUES ('2036', '上海中医药大学', '9', '本科', 'http://www.shutcm.edu.cn/', 'shutcm', '上海市');
INSERT INTO `university` VALUES ('2037', '华东师范大学', '9', '本科', 'http://www.ecnu.edu.cn/', 'ecnu', '上海市');
INSERT INTO `university` VALUES ('2038', '上海师范大学', '9', '本科', 'http://www.shnu.edu.cn', 'shnu', '上海市');
INSERT INTO `university` VALUES ('2039', '上海外国语大学', '9', '本科', 'http://www.shisu.edu.cn/', 'shisu', '上海市');
INSERT INTO `university` VALUES ('2040', '上海财经大学', '9', '本科', 'http://www.shufe.edu.cn/', 'shufe', '上海市');
INSERT INTO `university` VALUES ('2041', '上海对外经贸大学', '9', '本科', 'http://www.suibe.edu.cn/', 'suibe', '上海市');
INSERT INTO `university` VALUES ('2042', '上海海关学院', '9', '本科', 'http://shanghai_edu.customs.go', 'customs', '上海市');
INSERT INTO `university` VALUES ('2043', '华东政法大学', '9', '本科', 'http://www.ecupl.edu.cn/', 'ecupl', '上海市');
INSERT INTO `university` VALUES ('2044', '上海体育学院', '9', '本科', 'http://www.sus.edu.cn/', 'sus', '上海市');
INSERT INTO `university` VALUES ('2045', '上海音乐学院', '9', '本科', 'http://www.shcmusic.edu.cn/', 'shcmusic', '上海市');
INSERT INTO `university` VALUES ('2046', '上海戏剧学院', '9', '本科', 'http://www.sta.edu.cn/', 'sta', '上海市');
INSERT INTO `university` VALUES ('2047', '上海大学', '9', '本科', 'http://www.shu.edu.cn/', 'shu', '上海市');
INSERT INTO `university` VALUES ('2048', '上海公安学院', '9', '本科', 'http://www.shpc.edu.cn/', 'shpc', '上海市');
INSERT INTO `university` VALUES ('2049', '上海工程技术大学', '9', '本科', 'http://www.sues.edu.cn/', 'sues', '上海市');
INSERT INTO `university` VALUES ('2050', '上海立信会计金融学院', '9', '本科', 'http://www.lixin.edu.cn/', 'lixin', '上海市');
INSERT INTO `university` VALUES ('2051', '上海电机学院', '9', '本科', 'http://www.sdju.edu.cn/', 'sdju', '上海市');
INSERT INTO `university` VALUES ('2052', '上海杉达学院', '9', '本科', 'http://www.sandau.edu.cn/', 'sandau', '上海市');
INSERT INTO `university` VALUES ('2053', '上海政法学院', '9', '本科', 'http://www.shupl.edu.cn/', 'shupl', '上海市');
INSERT INTO `university` VALUES ('2054', '上海第二工业大学', '9', '本科', 'http://www.sspu.edu.cn/', 'sspu', '上海市');
INSERT INTO `university` VALUES ('2055', '上海商学院', '9', '本科', 'http://www.sbs.edu.cn/', 'sbs', '上海市');
INSERT INTO `university` VALUES ('2056', '上海建桥学院', '9', '本科', 'http://www.gench.com.cn/', 'gench', '上海市');
INSERT INTO `university` VALUES ('2057', '上海兴伟学院', '9', '本科', 'http://www.xingwei.edu.cn/', 'xingwei', '上海市');
INSERT INTO `university` VALUES ('2058', '上海视觉艺术学院', '9', '本科', 'http://www.siva.edu.cn/', 'siva', '上海市');
INSERT INTO `university` VALUES ('2059', '上海外国语大学贤达经济人文学院', '9', '本科', 'http://www.xdsisu.edu.cn/', 'xdsisu', '上海市');
INSERT INTO `university` VALUES ('2060', '上海师范大学天华学院', '9', '本科', 'http://www.sthu.cn/', 'sthu', '上海市');
INSERT INTO `university` VALUES ('2061', '上海纽约大学', '9', '本科', 'http://shanghai.nyu.edu/cn', 'nyu', '上海市');
INSERT INTO `university` VALUES ('2062', '上海科技大学', '9', '本科', 'http://www.shanghaitech.edu.cn', 'shanghaite', '上海市');
INSERT INTO `university` VALUES ('2063', '上海旅游高等专科学校', '9', '专科', 'http://sit.shnu.edu.cn/', 'shnu', '上海市');
INSERT INTO `university` VALUES ('2064', '上海东海职业技术学院', '9', '专科', 'http://www.esu.edu.cn/', 'esu', '上海市');
INSERT INTO `university` VALUES ('2065', '上海工商职业技术学院', '9', '专科', 'http://www.sicp.sh.cn/', 'sicp', '上海市');
INSERT INTO `university` VALUES ('2066', '上海出版印刷高等专科学校', '9', '专科', 'http://www.sppc.edu.cn/', 'sppc', '上海市');
INSERT INTO `university` VALUES ('2067', '上海行健职业学院', '9', '专科', 'http://www.shxj.cn/', 'shxj', '上海市');
INSERT INTO `university` VALUES ('2068', '上海城建职业学院', '9', '专科', 'http://www.succ.edu.cn/', 'succ', '上海市');
INSERT INTO `university` VALUES ('2069', '上海交通职业技术学院', '9', '专科', 'http://www.scp.edu.cn/', 'scp', '上海市');
INSERT INTO `university` VALUES ('2070', '上海海事职业技术学院', '9', '专科', 'http://www.sma.edu.cn/', 'sma', '上海市');
INSERT INTO `university` VALUES ('2071', '上海电子信息职业技术学院', '9', '专科', 'http://www.stiei.edu.cn/', 'stiei', '上海市');
INSERT INTO `university` VALUES ('2072', '上海震旦职业学院', '9', '专科', 'http://www.aurora-college.cn/', 'aurora-col', '上海市');
INSERT INTO `university` VALUES ('2073', '上海民远职业技术学院', '9', '专科', 'http://www.min-yuan.com/', 'min-yuan', '上海市');
INSERT INTO `university` VALUES ('2074', '上海欧华职业技术学院', '9', '专科', 'http://www.shouhua.net.cn/', 'shouhua', '上海市');
INSERT INTO `university` VALUES ('2075', '上海思博职业技术学院', '9', '专科', 'http://www.shsipo.com/', 'shsipo', '上海市');
INSERT INTO `university` VALUES ('2076', '上海立达职业技术学院', '9', '专科', 'http://www.lidapoly.com/', 'lidapoly', '上海市');
INSERT INTO `university` VALUES ('2077', '上海工艺美术职业学院', '9', '专科', 'http://www.shgymy.com/', 'shgymy', '上海市');
INSERT INTO `university` VALUES ('2078', '上海济光职业技术学院', '9', '专科', 'http://www.shjgxy.net/', 'shjgxy', '上海市');
INSERT INTO `university` VALUES ('2079', '上海工商外国语职业学院', '9', '专科', 'http://www.sicfl.edu.cn/', 'sicfl', '上海市');
INSERT INTO `university` VALUES ('2080', '上海科学技术职业学院', '9', '专科', 'http://www.scst.edu.cn/', 'scst', '上海市');
INSERT INTO `university` VALUES ('2081', '上海农林职业技术学院', '9', '专科', 'http://www.shafc.edu.cn/', 'shafc', '上海市');
INSERT INTO `university` VALUES ('2082', '上海邦德职业技术学院', '9', '专科', 'http://www.shbangde.com/', 'shbangde', '上海市');
INSERT INTO `university` VALUES ('2083', '上海中侨职业技术学院', '9', '专科', 'http://www.shzq.edu.cn/', 'shzq', '上海市');
INSERT INTO `university` VALUES ('2084', '上海电影艺术职业学院', '9', '专科', 'http://www.sfaa.com.cn/', 'sfaa', '上海市');
INSERT INTO `university` VALUES ('2085', '上海中华职业技术学院', '9', '专科', 'http://www.zhonghuacollege.com', 'zhonghuaco', '上海市');
INSERT INTO `university` VALUES ('2086', '上海工会管理职业学院', '9', '专科', 'http://www.shghxyedu.net/', 'shghxyedu', '上海市');
INSERT INTO `university` VALUES ('2087', '上海体育职业学院', '9', '专科', 'http://www.ssi.edu.cn/', 'ssi', '上海市');
INSERT INTO `university` VALUES ('2088', '上海民航职业技术学院', '9', '专科', 'http://www.shcac.edu.cn/', 'shcac', '上海市');
INSERT INTO `university` VALUES ('2089', '南京大学', '10', '本科', 'http://www.nju.edu.cn/', 'nju', '南京市');
INSERT INTO `university` VALUES ('2090', '苏州大学', '10', '本科', 'http://www.suda.edu.cn/', 'suda', '苏州市');
INSERT INTO `university` VALUES ('2091', '东南大学', '10', '本科', 'http://www.seu.edu.cn/', 'seu', '南京市');
INSERT INTO `university` VALUES ('2092', '南京航空航天大学', '10', '本科', 'http://www.nuaa.edu.cn/', 'nuaa', null);
INSERT INTO `university` VALUES ('2093', '南京理工大学', '10', '本科', 'http://www.njust.edu.cn/', 'njust', null);
INSERT INTO `university` VALUES ('2094', '江苏科技大学', '10', '本科', 'http://www.just.edu.cn/', 'just', '镇江市');
INSERT INTO `university` VALUES ('2095', '中国矿业大学', '10', '本科', 'http://www.cumt.edu.cn/', 'cumt', '徐州市');
INSERT INTO `university` VALUES ('2096', '南京工业大学', '10', '本科', 'http://www.njtech.edu.cn/', 'njtech', '南京市');
INSERT INTO `university` VALUES ('2097', '常州大学', '10', '本科', 'http://www.cczu.edu.cn/', 'cczu', '常州市');
INSERT INTO `university` VALUES ('2098', '南京邮电大学', '10', '本科', 'http://www.njupt.edu.cn/', 'njupt', '南京市');
INSERT INTO `university` VALUES ('2099', '河海大学', '10', '本科', 'http://www.hhu.edu.cn/', 'hhu', '南京市');
INSERT INTO `university` VALUES ('2100', '江南大学', '10', '本科', 'http://www.jiangnan.edu.cn/', 'jiangnan', '无锡市');
INSERT INTO `university` VALUES ('2101', '南京林业大学', '10', '本科', 'http://www.njfu.edu.cn/', 'njfu', '南京市');
INSERT INTO `university` VALUES ('2102', '江苏大学', '10', '本科', 'http://www.ujs.edu.cn/', 'ujs', '镇江市');
INSERT INTO `university` VALUES ('2103', '南京信息工程大学', '10', '本科', 'http://www.nuist.edu.cn/', 'nuist', '南京市');
INSERT INTO `university` VALUES ('2104', '南通大学', '10', '本科', 'http://www.ntu.edu.cn/', 'ntu', '南通市');
INSERT INTO `university` VALUES ('2105', '盐城工学院', '10', '本科', 'http://www.ycit.cn/', 'ycit', '盐城市');
INSERT INTO `university` VALUES ('2106', '南京农业大学', '10', '本科', 'http://www.njau.edu.cn/', 'njau', '南京市');
INSERT INTO `university` VALUES ('2107', '南京医科大学', '10', '本科', 'http://www.njmu.edu.cn/', 'njmu', '南京市');
INSERT INTO `university` VALUES ('2108', '徐州医科大学', '10', '本科', 'http://www.xzmc.edu.cn/', 'xzmc', '徐州市');
INSERT INTO `university` VALUES ('2109', '南京中医药大学', '10', '本科', 'http://www.njutcm.edu.cn/', 'njutcm', '南京市');
INSERT INTO `university` VALUES ('2110', '中国药科大学', '10', '本科', 'http://www.cpu.edu.cn/', 'cpu', '南京市');
INSERT INTO `university` VALUES ('2111', '南京师范大学', '10', '本科', 'http://www.njnu.edu.cn/', 'njnu', '南京市');
INSERT INTO `university` VALUES ('2112', '江苏师范大学', '10', '本科', 'http://www.jsnu.edu.cn/', 'jsnu', '徐州市');
INSERT INTO `university` VALUES ('2113', '淮阴师范学院', '10', '本科', 'http://www.hytc.edu.cn/', 'hytc', '淮阴市');
INSERT INTO `university` VALUES ('2114', '盐城师范学院', '10', '本科', 'http://www.yctc.edu.cn/', 'yctc', '盐城市');
INSERT INTO `university` VALUES ('2115', '南京财经大学', '10', '本科', 'http://www.njue.edu.cn/', 'njue', '南京市');
INSERT INTO `university` VALUES ('2116', '江苏警官学院', '10', '本科', 'http://www.jspi.cn/', 'jspi', '南京市');
INSERT INTO `university` VALUES ('2117', '南京体育学院', '10', '本科', 'http://www.nipes.cn/', 'nipes', '南京市');
INSERT INTO `university` VALUES ('2118', '南京艺术学院', '10', '本科', 'http://www.nua.edu.cn/', 'nua', '南京市');
INSERT INTO `university` VALUES ('2119', '苏州科技大学', '10', '本科', 'http://www.usts.edu.cn/', 'usts', '苏州市');
INSERT INTO `university` VALUES ('2120', '常熟理工学院', '10', '本科', 'http://www.cslg.cn/', 'cslg', '苏州市');
INSERT INTO `university` VALUES ('2121', '淮阴工学院', '10', '本科', 'http://www.hyit.edu.cn/', 'hyit', '淮阴市');
INSERT INTO `university` VALUES ('2122', '常州工学院', '10', '本科', 'http://www.czu.cn/', 'czu', '常州市');
INSERT INTO `university` VALUES ('2123', '扬州大学', '10', '本科', 'http://www.yzu.edu.cn/', 'yzu', '扬州市');
INSERT INTO `university` VALUES ('2124', '三江学院', '10', '本科', 'http://www.sju.edu.cn/', 'sju', '南京市');
INSERT INTO `university` VALUES ('2125', '南京工程学院', '10', '本科', 'http://www.njit.edu.cn/', 'njit', '南京市');
INSERT INTO `university` VALUES ('2126', '南京审计大学', '10', '本科', 'http://www.nau.edu.cn/', 'nau', '南京市');
INSERT INTO `university` VALUES ('2127', '南京晓庄学院', '10', '本科', 'http://www.njxzc.edu.cn/', 'njxzc', '南京市');
INSERT INTO `university` VALUES ('2128', '江苏理工学院', '10', '本科', 'http://www.jstu.edu.cn/', 'jstu', '常州市');
INSERT INTO `university` VALUES ('2129', '淮海工学院', '10', '本科', 'http://www.hhit.edu.cn/', 'hhit', '连云港');
INSERT INTO `university` VALUES ('2130', '徐州工程学院', '10', '本科', 'http://www.xzit.edu.cn/', 'xzit', '徐州市');
INSERT INTO `university` VALUES ('2131', '南京特殊教育师范学院', '10', '本科', 'http://www.njts.edu.cn/', 'njts', '南京市');
INSERT INTO `university` VALUES ('2132', '南通理工学院', '10', '本科', 'http://www.zlvc.edu.cn/', 'zlvc', '南通市');
INSERT INTO `university` VALUES ('2133', '南京森林警察学院', '10', '本科', 'http://www.forestpolice.net/', 'forestpoli', '南京市');
INSERT INTO `university` VALUES ('2134', '东南大学成贤学院', '10', '本科', 'http://cxxy.seu.edu.cn/', 'seu', '南京市');
INSERT INTO `university` VALUES ('2135', '泰州学院', '10', '本科', 'http://www.tzu-edu.cn/', 'tzu-edu', '泰州市');
INSERT INTO `university` VALUES ('2136', '无锡太湖学院', '10', '本科', 'http://www.thxy.org/', 'thxy', '无锡市');
INSERT INTO `university` VALUES ('2137', '金陵科技学院', '10', '本科', 'http://www.jit.edu.cn/', 'jit', '南京市');
INSERT INTO `university` VALUES ('2138', '中国矿业大学徐海学院', '10', '本科', 'http://xhxy.cumt.edu.cn/', 'cumt', '徐州市');
INSERT INTO `university` VALUES ('2139', '南京大学金陵学院', '10', '本科', 'http://www.jlxy.nju.edu.cn/', 'jlxy', '南京市');
INSERT INTO `university` VALUES ('2140', '南京理工大学紫金学院', '10', '本科', 'http://zj.njust.edu.cn/', 'njust', '南京市');
INSERT INTO `university` VALUES ('2141', '南京航空航天大学金城学院', '10', '本科', 'http://jc.nuaa.edu.cn/', 'nuaa', '南京市');
INSERT INTO `university` VALUES ('2142', '中国传媒大学南广学院', '10', '本科', 'http://www.cucn.edu.cn/', 'cucn', '南京市');
INSERT INTO `university` VALUES ('2143', '南京理工大学泰州科技学院', '10', '本科', 'http://www.nustti.edu.cn/', 'nustti', '泰州市');
INSERT INTO `university` VALUES ('2144', '南京师范大学泰州学院', '10', '本科', 'http://www.njnutz.com/', 'njnutz', '泰州市');
INSERT INTO `university` VALUES ('2145', '南京工业大学浦江学院', '10', '本科', 'http://www.njpji.cn/', 'njpji', '南京市');
INSERT INTO `university` VALUES ('2146', '南京师范大学中北学院', '10', '本科', 'http://zbzs.njnu.edu.cn/', 'njnu', '南京市');
INSERT INTO `university` VALUES ('2147', '南京医科大学康达学院', '10', '本科', 'http://kdc.njmu.edu.cn/', 'njmu', '南京市');
INSERT INTO `university` VALUES ('2148', '南京中医药大学翰林学院', '10', '本科', 'http://www.hlxy.edu.cn/', 'hlxy', '泰州市');
INSERT INTO `university` VALUES ('2149', '南京信息工程大学滨江学院', '10', '本科', 'http://www.bjxy.cn/', 'bjxy', '南京市');
INSERT INTO `university` VALUES ('2150', '苏州大学文正学院', '10', '本科', 'http://www.sdwz.cn/', 'sdwz', '南京市');
INSERT INTO `university` VALUES ('2151', '苏州大学应用技术学院', '10', '本科', 'http://tec.suda.edu.cn/', 'suda', '苏州市');
INSERT INTO `university` VALUES ('2152', '苏州科技大学天平学院', '10', '本科', 'http://tpxy.usts.edu.cn/', 'usts', '苏州市');
INSERT INTO `university` VALUES ('2153', '江苏大学京江学院', '10', '本科', 'http://jjxy.ujs.edu.cn/', 'ujs', '镇江市');
INSERT INTO `university` VALUES ('2154', '扬州大学广陵学院', '10', '本科', 'http://glxy.yzu.edu.cn/', 'yzu', '扬州市');
INSERT INTO `university` VALUES ('2155', '江苏师范大学科文学院', '10', '本科', 'http://kwxy.xznu.edu.cn/', 'xznu', '徐州市');
INSERT INTO `university` VALUES ('2156', '南京邮电大学通达学院', '10', '本科', 'http://www.tdxy.com.cn/', 'tdxy', '南京市');
INSERT INTO `university` VALUES ('2157', '南京财经大学红山学院', '10', '本科', 'http://hs.njue.edu.cn/', 'njue', '镇江市');
INSERT INTO `university` VALUES ('2158', '江苏科技大学苏州理工学院', '10', '本科', 'http://szlg.just.edu.cn/', 'just', '张家港市');
INSERT INTO `university` VALUES ('2159', '常州大学怀德学院', '10', '本科', 'http://hdc.cczu.edu.cn/', 'cczu', '靖江市');
INSERT INTO `university` VALUES ('2160', '南通大学杏林学院', '10', '本科', 'http://xlxy.ntu.edu.cn/', 'ntu', '南通市');
INSERT INTO `university` VALUES ('2161', '南京审计学院金审学院', '10', '本科', 'http://jsxy.nau.edu.cn/', 'nau', '南京市');
INSERT INTO `university` VALUES ('2162', '宿迁学院', '10', '本科', 'http://www.sqc.edu.cn/', 'sqc', '宿迁市');
INSERT INTO `university` VALUES ('2163', '江苏第二师范学院', '10', '本科', 'http://www.jssnu.edu.cn/', 'jssnu', '南京市');
INSERT INTO `university` VALUES ('2164', '西交利物浦大学', '10', '本科', 'http://www.xjtlu.edu.cn/', 'xjtlu', '苏州市');
INSERT INTO `university` VALUES ('2165', '昆山杜克大学', '10', '本科', 'http://dku.edu.cn/', 'edu', '昆山市');
INSERT INTO `university` VALUES ('2166', '盐城幼儿师范高等专科学校', '10', '专科', 'http://www.yfysz.com/', 'yfysz', '盐城市');
INSERT INTO `university` VALUES ('2167', '苏州幼儿师范高等专科学校', '10', '专科', 'http://www.szys.net/', 'szys', '苏州市');
INSERT INTO `university` VALUES ('2168', '民办明达职业技术学院', '10', '专科', 'http://www.mdut.cn/', 'mdut', '盐城市');
INSERT INTO `university` VALUES ('2169', '无锡职业技术学院', '10', '专科', 'http://www.wxit.edu.cn/', 'wxit', '无锡市');
INSERT INTO `university` VALUES ('2170', '江苏建筑职业技术学院', '10', '专科', 'http://www.xzcat.edu.cn/', 'xzcat', '徐州市');
INSERT INTO `university` VALUES ('2171', '南京工业职业技术学院', '10', '专科', 'http://www.niit.edu.cn/', 'niit', '南京市');
INSERT INTO `university` VALUES ('2172', '江苏工程职业技术学院', '10', '专科', 'http://www.nttec.edu.cn/', 'nttec', '南通市');
INSERT INTO `university` VALUES ('2173', '苏州工艺美术职业技术学院', '10', '专科', 'http://www.sgmart.com/', 'sgmart', '苏州市');
INSERT INTO `university` VALUES ('2174', '连云港职业技术学院', '10', '专科', 'http://www.lygtc.net.cn/', 'lygtc', '连云港');
INSERT INTO `university` VALUES ('2175', '镇江市高等专科学校', '10', '专科', 'http://www.zjc.edu.cn/', 'zjc', '镇江市');
INSERT INTO `university` VALUES ('2176', '南通职业大学', '10', '专科', 'http://www.ntvc.edu.cn/', 'ntvc', '南通市');
INSERT INTO `university` VALUES ('2177', '苏州职业大学', '10', '专科', 'http://www.jssvc.edu.cn/', 'jssvc', '苏州市');
INSERT INTO `university` VALUES ('2178', '沙洲职业工学院', '10', '专科', 'http://www.szit.edu.cn/', 'szit', '张家港');
INSERT INTO `university` VALUES ('2179', '扬州市职业大学', '10', '专科', 'http://www.yzpc.edu.cn/', 'yzpc', '扬州市');
INSERT INTO `university` VALUES ('2180', '连云港师范高等专科学校', '10', '专科', 'http://www.lygsf.cn/', 'lygsf', '连云港');
INSERT INTO `university` VALUES ('2181', '江苏经贸职业技术学院', '10', '专科', 'http://www.jseti.edu.cn/', 'jseti', '南京市');
INSERT INTO `university` VALUES ('2182', '九州职业技术学院', '10', '专科', 'http://www.jznu.com.cn/', 'jznu', '徐州市');
INSERT INTO `university` VALUES ('2183', '硅湖职业技术学院', '10', '专科', 'http://www.usl.edu.cn/', 'usl', '苏州市');
INSERT INTO `university` VALUES ('2184', '泰州职业技术学院', '10', '专科', 'http://www.tzpc.edu.cn/', 'tzpc', '泰州市');
INSERT INTO `university` VALUES ('2185', '常州信息职业技术学院', '10', '专科', 'http://www.ccit.js.cn/', 'ccit', '常州市');
INSERT INTO `university` VALUES ('2186', '江苏联合职业技术学院', '10', '专科', 'http://www.juti.cn/', 'juti', '南京市');
INSERT INTO `university` VALUES ('2187', '江苏海事职业技术学院', '10', '专科', 'http://www.jmi.edu.cn/', 'jmi', '南京市');
INSERT INTO `university` VALUES ('2188', '应天职业技术学院', '10', '专科', 'http://www.ytc.edu.cn/', 'ytc', '南京市');
INSERT INTO `university` VALUES ('2189', '无锡科技职业学院', '10', '专科', 'http://www.wxstc.cn/', 'wxstc', '无锡市');
INSERT INTO `university` VALUES ('2190', '江苏医药职业学院', '10', '专科', 'http://www.jsycmc.com/', 'jsycmc', '盐城市');
INSERT INTO `university` VALUES ('2191', '扬州环境资源职业技术学院', '10', '专科', 'http://www.yzerc.org/', 'yzerc', '扬州市');
INSERT INTO `university` VALUES ('2192', '南通科技职业学院', '10', '专科', 'http://www.ntac.edu.cn/', 'ntac', '南通市');
INSERT INTO `university` VALUES ('2193', '苏州经贸职业技术学院', '10', '专科', 'http://www.szjm.edu.cn/', 'szjm', '苏州市');
INSERT INTO `university` VALUES ('2194', '苏州工业职业技术学院', '10', '专科', 'http://www.siit.cn/', 'siit', '苏州市');
INSERT INTO `university` VALUES ('2195', '苏州托普信息职业技术学院', '10', '专科', 'http://www.szetop.com/', 'szetop', '苏州市');
INSERT INTO `university` VALUES ('2196', '苏州卫生职业技术学院', '10', '专科', 'http://www.szmtc.com/', 'szmtc', '苏州市');
INSERT INTO `university` VALUES ('2197', '无锡商业职业技术学院', '10', '专科', 'http://www.jscpu.com/', 'jscpu', '无锡市');
INSERT INTO `university` VALUES ('2198', '南通航运职业技术学院', '10', '专科', 'http://www.ntsc.edu.cn/', 'ntsc', '南通市');
INSERT INTO `university` VALUES ('2199', '南京交通职业技术学院', '10', '专科', 'http://www.njci.cn/', 'njci', '南京市');
INSERT INTO `university` VALUES ('2200', '淮安信息职业技术学院', '10', '专科', 'http://www.hcit.edu.cn/', 'hcit', '淮安市');
INSERT INTO `university` VALUES ('2201', '江苏农牧科技职业学院', '10', '专科', 'http://www.jsahvc.edu.cn/', 'jsahvc', '泰州市');
INSERT INTO `university` VALUES ('2202', '常州纺织服装职业技术学院', '10', '专科', 'http://www.cztgi.edu.cn/', 'cztgi', '常州市');
INSERT INTO `university` VALUES ('2203', '苏州农业职业技术学院', '10', '专科', 'http://www.szai.com/', 'szai', '苏州市');
INSERT INTO `university` VALUES ('2204', '苏州工业园区职业技术学院', '10', '专科', 'http://www.sipivt.edu.cn/', 'sipivt', '苏州市');
INSERT INTO `university` VALUES ('2205', '太湖创意职业技术学院', '10', '专科', 'http://www.thcyzy.org/', 'thcyzy', '无锡市');
INSERT INTO `university` VALUES ('2206', '炎黄职业技术学院', '10', '专科', 'http://www.yhust.edu.cn/', 'yhust', '淮安市');
INSERT INTO `university` VALUES ('2207', '南京科技职业学院', '10', '专科', 'http://www.njcc.edu.cn/', 'njcc', '南京市');
INSERT INTO `university` VALUES ('2208', '正德职业技术学院', '10', '专科', 'http://www.zdxy.cn/', 'zdxy', '南京市');
INSERT INTO `university` VALUES ('2209', '钟山职业技术学院', '10', '专科', 'http://www.zscollege.com/', 'zscollege', '南京市');
INSERT INTO `university` VALUES ('2210', '无锡南洋职业技术学院', '10', '专科', 'http://www.wsoc.edu.cn/', 'wsoc', '无锡市');
INSERT INTO `university` VALUES ('2211', '江南影视艺术职业学院', '10', '专科', 'http://www.jnys.cn/', 'jnys', '无锡市');
INSERT INTO `university` VALUES ('2212', '金肯职业技术学院', '10', '专科', 'http://www.jku.edu.cn/', 'jku', '南京市');
INSERT INTO `university` VALUES ('2213', '常州轻工职业技术学院', '10', '专科', 'http://www.czili.edu.cn/', 'czili', '常州市');
INSERT INTO `university` VALUES ('2214', '常州工程职业技术学院', '10', '专科', 'http://www.czie.net/', 'czie', '常州市');
INSERT INTO `university` VALUES ('2215', '江苏农林职业技术学院', '10', '专科', 'http://www.jsafc.net/', 'jsafc', '句容市');
INSERT INTO `university` VALUES ('2216', '江苏食品药品职业技术学院', '10', '专科', 'http://www.jsfsc.edu.cn/', 'jsfsc', '淮安市');
INSERT INTO `university` VALUES ('2217', '建东职业技术学院', '10', '专科', 'http://www.czjdu.com/', 'czjdu', '常州市');
INSERT INTO `university` VALUES ('2218', '南京铁道职业技术学院', '10', '专科', 'http://www.njrts.edu.cn/', 'njrts', '南京市');
INSERT INTO `university` VALUES ('2219', '徐州工业职业技术学院', '10', '专科', 'http://www.xzcit.cn/', 'xzcit', '徐州市');
INSERT INTO `university` VALUES ('2220', '江苏信息职业技术学院', '10', '专科', 'http://www.jsit.edu.cn/', 'jsit', '无锡市');
INSERT INTO `university` VALUES ('2221', '宿迁职业技术学院', '10', '专科', 'http://www.sqzyxy.com/', 'sqzyxy', '宿迁市');
INSERT INTO `university` VALUES ('2222', '南京信息职业技术学院', '10', '专科', 'http://www.njcit.edu.cn/', 'njcit', '南京市');
INSERT INTO `university` VALUES ('2223', '江海职业技术学院', '10', '专科', 'http://www.jhu.cn/', 'jhu', '扬州市');
INSERT INTO `university` VALUES ('2224', '常州机电职业技术学院', '10', '专科', 'http://www.czmec.cn/', 'czmec', '常州市');
INSERT INTO `university` VALUES ('2225', '江阴职业技术学院', '10', '专科', 'http://www.jypc.org/', 'jypc', '江阴市');
INSERT INTO `university` VALUES ('2226', '无锡城市职业技术学院', '10', '专科', 'http://www.wxcsxy.com/', 'wxcsxy', '无锡市');
INSERT INTO `university` VALUES ('2227', '无锡工艺职业技术学院', '10', '专科', 'http://www.wxgyxy.cn/', 'wxgyxy', '无锡市');
INSERT INTO `university` VALUES ('2228', '金山职业技术学院', '10', '专科', 'http://www.jinshan-cn.com/', 'jinshan-cn', '镇江市');
INSERT INTO `university` VALUES ('2229', '苏州健雄职业技术学院', '10', '专科', 'http://www.wjxvtc.cn/', 'wjxvtc', '苏州市');
INSERT INTO `university` VALUES ('2230', '盐城工业职业技术学院', '10', '专科', 'http://www.yctei.cn/', 'yctei', '盐城市');
INSERT INTO `university` VALUES ('2231', '江苏财经职业技术学院', '10', '专科', 'http://www.jscjxy.cn/', 'jscjxy', '淮安市');
INSERT INTO `university` VALUES ('2232', '扬州工业职业技术学院', '10', '专科', 'http://www.ypi.edu.cn/', 'ypi', '扬州市');
INSERT INTO `university` VALUES ('2233', '苏州百年职业学院', '10', '专科', 'http://www.scc.edu.cn/', 'scc', '苏州市');
INSERT INTO `university` VALUES ('2234', '昆山登云科技职业学院', '10', '专科', 'http://www.dyc.edu.cn/', 'dyc', '昆山市');
INSERT INTO `university` VALUES ('2235', '南京视觉艺术职业学院', '10', '专科', 'http://www.niva.cn/', 'niva', '南京市');
INSERT INTO `university` VALUES ('2236', '江苏城市职业学院', '10', '专科', 'http://www.jscvc.cn/', 'jscvc', '南京市');
INSERT INTO `university` VALUES ('2237', '南京城市职业学院', '10', '专科', 'http://www.ncc.com.cn/', 'ncc', '南京市');
INSERT INTO `university` VALUES ('2238', '南京机电职业技术学院', '10', '专科', 'http://www.njcmee.net/', 'njcmee', '南京市');
INSERT INTO `university` VALUES ('2239', '苏州高博软件技术职业学院', '10', '专科', 'http://www.gist.edu.cn/', 'gist', '苏州市');
INSERT INTO `university` VALUES ('2240', '南京旅游职业学院', '10', '专科', 'http://www.jltu.net/', 'jltu', '南京市');
INSERT INTO `university` VALUES ('2241', '江苏卫生健康职业学院', '10', '专科', 'http://www.jssmu.edu.cn/', 'jssmu', '南京市');
INSERT INTO `university` VALUES ('2242', '苏州信息职业技术学院', '10', '专科', 'http://www.szitu.cn/', 'szitu', '吴江市');
INSERT INTO `university` VALUES ('2243', '宿迁泽达职业技术学院', '10', '专科', 'http://www.zdct.cn/', 'zdct', '宿迁市');
INSERT INTO `university` VALUES ('2244', '苏州工业园区服务外包职业学院', '10', '专科', 'http://www.siso.edu.cn/', 'siso', '苏州市');
INSERT INTO `university` VALUES ('2245', '徐州幼儿师范高等专科学校', '10', '专科', 'http://www.xzys.xze.cn/', 'xzys', '徐州市');
INSERT INTO `university` VALUES ('2246', '徐州生物工程职业技术学院', '10', '专科', 'http://www.xzsw.net/', 'xzsw', '徐州市');
INSERT INTO `university` VALUES ('2247', '江苏商贸职业学院', '10', '专科', 'http://www.ntgx.edu.cn/', 'ntgx', '南通市');
INSERT INTO `university` VALUES ('2248', '南通师范高等专科学校', '10', '专科', 'http://www.ntgs.com.cn/', 'ntgs', '南通市');
INSERT INTO `university` VALUES ('2249', '扬州中瑞酒店职业学院', '10', '专科', 'http://www.yhiedu.com/', 'yhiedu', '扬州市');
INSERT INTO `university` VALUES ('2250', '江苏护理职业学院', '10', '专科', 'http://www.jshywx.com/', 'jshywx', '南通市');
INSERT INTO `university` VALUES ('2251', '江苏财会职业学院', '10', '专科', 'http://www.lygcx.net/', 'lygcx', '南通市');
INSERT INTO `university` VALUES ('2252', '江苏城乡建设职业学院', '10', '专科', 'http://www.js-cj.com/', 'js-cj', '常州市');
INSERT INTO `university` VALUES ('2253', '江苏航空职业技术学院', '10', '专科', 'http://www.jatc.edu.cn/', 'jatc', '镇江市');
INSERT INTO `university` VALUES ('2254', '江苏安全技术职业学院', '10', '专科', 'http://www.xzjdgz.com/', 'xzjdgz', '徐州市');
INSERT INTO `university` VALUES ('2255', '江苏旅游职业学院', '10', '专科', 'http://www.jsyzsx.com/', 'jsyzsx', '扬州市');
INSERT INTO `university` VALUES ('2256', '昆山登云科技职业学院　　　　　　　　　　', '10', '', 'http://www.dyc.edu.cn/', 'dyc', null);
INSERT INTO `university` VALUES ('2257', '南京视觉艺术职业学院　　　　　　　　　　', '10', '', 'http://www.niva.cn/', 'niva', null);
INSERT INTO `university` VALUES ('2258', '浙江大学', '11', '本科', 'http://www.zju.edu.cn/', 'zju', '杭州市');
INSERT INTO `university` VALUES ('2259', '杭州电子科技大学', '11', '本科', 'http://www.hdu.edu.cn/', 'hdu', '杭州市');
INSERT INTO `university` VALUES ('2260', '浙江工业大学', '11', '本科', 'http://www.zjut.edu.cn/', 'zjut', '杭州市');
INSERT INTO `university` VALUES ('2261', '浙江理工大学', '11', '本科', 'http://www.zstu.edu.cn/', 'zstu', '杭州市');
INSERT INTO `university` VALUES ('2262', '浙江海洋大学', '11', '本科', 'http://www.zjou.net.cn/', 'zjou', '舟山市');
INSERT INTO `university` VALUES ('2263', '浙江农林大学', '11', '本科', 'http://www.zafu.edu.cn/', 'zafu', '临安市');
INSERT INTO `university` VALUES ('2264', '温州医科大学', '11', '本科', 'http://www.wzmc.net/', 'wzmc', '温州市');
INSERT INTO `university` VALUES ('2265', '浙江中医药大学', '11', '本科', 'http://www.zjtcm.net/', 'zjtcm', '杭州市');
INSERT INTO `university` VALUES ('2266', '浙江师范大学', '11', '本科', 'http://www.zjnu.edu.cn/', 'zjnu', '金华市');
INSERT INTO `university` VALUES ('2267', '杭州师范大学', '11', '本科', 'http://www.hznu.edu.cn/', 'hznu', '杭州市');
INSERT INTO `university` VALUES ('2268', '湖州师范学院', '11', '本科', 'http://www.hutc.zj.cn/', 'hutc', '湖州市');
INSERT INTO `university` VALUES ('2269', '绍兴文理学院', '11', '本科', 'http://www.usx.edu.cn/', 'usx', '绍兴市');
INSERT INTO `university` VALUES ('2270', '台州学院', '11', '本科', 'http://www.tzc.edu.cn/', 'tzc', '临海市');
INSERT INTO `university` VALUES ('2271', '温州大学', '11', '本科', 'http://www.wzu.edu.cn/', 'wzu', '温州市');
INSERT INTO `university` VALUES ('2272', '丽水学院', '11', '本科', 'http://www.lsu.edu.cn/', 'lsu', '丽水市');
INSERT INTO `university` VALUES ('2273', '浙江工商大学', '11', '本科', 'http://www.hzic.edu.cn/', 'hzic', '杭州市');
INSERT INTO `university` VALUES ('2274', '嘉兴学院', '11', '本科', 'http://www.zjxu.edu.cn/', 'zjxu', '嘉兴市');
INSERT INTO `university` VALUES ('2275', '中国美术学院', '11', '本科', 'http://www.caa.edu.cn/', 'caa', '杭州市');
INSERT INTO `university` VALUES ('2276', '中国计量大学', '11', '本科', 'http://www.cjlu.edu.cn/', 'cjlu', '杭州市');
INSERT INTO `university` VALUES ('2277', '公安海警学院', '11', '本科', 'http://www.hjgz.net/', 'hjgz', '宁波市');
INSERT INTO `university` VALUES ('2278', '浙江万里学院', '11', '本科', 'http://www.zjwu.net/', 'zjwu', '宁波市');
INSERT INTO `university` VALUES ('2279', '浙江科技学院', '11', '本科', 'http://www.zust.edu.cn/', 'zust', '杭州市');
INSERT INTO `university` VALUES ('2280', '宁波工程学院', '11', '本科', 'http://www.nbut.cn/', 'nbut', '宁波市');
INSERT INTO `university` VALUES ('2281', '浙江水利水电学院', '11', '本科', 'http://www.zjweu.edu.cn/', 'zjweu', '杭州市');
INSERT INTO `university` VALUES ('2282', '浙江财经大学', '11', '本科', 'http://www.zufe.edu.cn/', 'zufe', '杭州市');
INSERT INTO `university` VALUES ('2283', '浙江警察学院', '11', '本科', 'http://www.zjjcxy.cn/', 'zjjcxy', '杭州市');
INSERT INTO `university` VALUES ('2284', '衢州学院', '11', '本科', 'http://www.qzu.zj.cn/', 'qzu', '衢州市');
INSERT INTO `university` VALUES ('2285', '宁波大学', '11', '本科', 'http://www.nbu.edu.cn/', 'nbu', '宁波市');
INSERT INTO `university` VALUES ('2286', '浙江传媒学院', '11', '本科', 'http://www.zjicm.edu.cn/', 'zjicm', '杭州市');
INSERT INTO `university` VALUES ('2287', '浙江树人学院', '11', '本科', 'http://www.zjsru.edu.cn/', 'zjsru', '杭州市');
INSERT INTO `university` VALUES ('2288', '浙江越秀外国语学院', '11', '本科', 'http://www.zyufl.edu.cn/', 'zyufl', '绍兴市');
INSERT INTO `university` VALUES ('2289', '宁波大红鹰学院', '11', '本科', 'http://www.dhyedu.com/', 'dhyedu', '宁波市');
INSERT INTO `university` VALUES ('2290', '浙江大学城市学院', '11', '本科', 'http://www.zucc.edu.cn/', 'zucc', '杭州市');
INSERT INTO `university` VALUES ('2291', '浙江大学宁波理工学院', '11', '本科', 'http://www.nit.net.cn/', 'nit', '宁波市');
INSERT INTO `university` VALUES ('2292', '杭州医学院', '11', '本科', 'http://www.hmc.edu.cn/', 'hmc', '杭州市');
INSERT INTO `university` VALUES ('2293', '浙江工业大学之江学院', '11', '本科', 'http://www.zjc.zjut.edu.cn/', 'zjc', '杭州市');
INSERT INTO `university` VALUES ('2294', '浙江师范大学行知学院', '11', '本科', 'http://xz.zjnu.net.cn/', 'zjnu', '金华市');
INSERT INTO `university` VALUES ('2295', '宁波大学科学技术学院', '11', '本科', 'http://www.ndkjxy.net.cn/', 'ndkjxy', '宁波市');
INSERT INTO `university` VALUES ('2296', '杭州电子科技大学信息工程学院', '11', '本科', 'http://infoedu.hdu.edu.cn/', 'hdu', '杭州市');
INSERT INTO `university` VALUES ('2297', '浙江理工大学科技与艺术学院', '11', '本科', 'http://www.ky.zstu.edu.cn/', 'ky', '杭州市');
INSERT INTO `university` VALUES ('2298', '浙江海洋大学东海科学技术学院', '11', '本科', 'http://dk.zjou.edu.cn/', 'zjou', '舟山市');
INSERT INTO `university` VALUES ('2299', '浙江农林大学暨阳学院', '11', '本科', 'http://www.zjyc.edu.cn/', 'zjyc', '诸暨市');
INSERT INTO `university` VALUES ('2300', '温州医科大学仁济学院', '11', '本科', 'http://rjxy.wzmc.edu.cn/', 'wzmc', '温州市');
INSERT INTO `university` VALUES ('2301', '浙江中医药大学滨江学院', '11', '本科', 'http://bjxy.zjtcm.net/', 'zjtcm', '杭州市');
INSERT INTO `university` VALUES ('2302', '杭州师范大学钱江学院', '11', '本科', 'http://qjxy.hznu.edu.cn/', 'hznu', '杭州市');
INSERT INTO `university` VALUES ('2303', '湖州师范学院求真学院', '11', '本科', 'http://qzxy.hutc.zj.cn/', 'hutc', '湖州市');
INSERT INTO `university` VALUES ('2304', '绍兴文理学院元培学院', '11', '本科', 'http://www.ypcol.com/', 'ypcol', '绍兴市');
INSERT INTO `university` VALUES ('2305', '温州大学瓯江学院', '11', '本科', 'http://www.ojc.zj.cn/', 'ojc', '温州市');
INSERT INTO `university` VALUES ('2306', '浙江工商大学杭州商学院', '11', '本科', 'http://hsy.zjgsu.edu.cn/', 'zjgsu', '杭州市');
INSERT INTO `university` VALUES ('2307', '嘉兴学院南湖学院', '11', '本科', 'http://nhxy.zjxu.edu.cn/', 'zjxu', '嘉兴市');
INSERT INTO `university` VALUES ('2308', '中国计量大学现代科技学院', '11', '本科', 'http://xdkj.cjlu.edu.cn/', 'cjlu', '杭州市');
INSERT INTO `university` VALUES ('2309', '浙江财经大学东方学院', '11', '本科', 'http://www.zufedfc.edu.cn/', 'zufedfc', '海宁市');
INSERT INTO `university` VALUES ('2310', '温州商学院', '11', '本科', 'http://www.wzbc.edu.cn/', 'wzbc', '温州市');
INSERT INTO `university` VALUES ('2311', '同济大学浙江学院', '11', '本科', 'http://www.tjzj.edu.cn/', 'tjzj', '嘉兴市');
INSERT INTO `university` VALUES ('2312', '上海财经大学浙江学院', '11', '本科', 'http://www.shufe-zj.edu.cn/', 'shufe-zj', '金华市');
INSERT INTO `university` VALUES ('2313', '浙江外国语学院', '11', '本科', 'http://www.zisu.edu.cn/', 'zisu', '杭州市');
INSERT INTO `university` VALUES ('2314', '浙江音乐学院', '11', '本科', 'http://www.zjcm.edu.cn/', 'zjcm', '杭州市');
INSERT INTO `university` VALUES ('2315', '宁波诺丁汉大学', '11', '本科', 'http://www.nottingham.edu.cn/', 'nottingham', '宁波市');
INSERT INTO `university` VALUES ('2316', '温州肯恩大学', '11', '本科', 'http://www.wku.edu.cn/', 'wku', '温州市');
INSERT INTO `university` VALUES ('2317', '宁波职业技术学院', '11', '专科', 'http://www.nbptweb.net/', 'nbptweb', '宁波市');
INSERT INTO `university` VALUES ('2318', '温州职业技术学院', '11', '专科', 'http://www.wzvtc.cn/', 'wzvtc', '温州市');
INSERT INTO `university` VALUES ('2319', '浙江交通职业技术学院', '11', '专科', 'http://www.zjvtit.edu.cn/', 'zjvtit', '杭州市');
INSERT INTO `university` VALUES ('2320', '金华职业技术学院', '11', '专科', 'http://www.jhc.cn/', 'jhc', '金华市');
INSERT INTO `university` VALUES ('2321', '宁波城市职业技术学院', '11', '专科', 'http://www.nbcc.cn/', 'nbcc', '宁波市');
INSERT INTO `university` VALUES ('2322', '浙江同济科技职业学院', '11', '专科', 'http://www.zjtongji.edu.cn/', 'zjtongji', '台州市');
INSERT INTO `university` VALUES ('2323', '浙江工商职业技术学院', '11', '专科', 'http://www.zjbti.net.cn/', 'zjbti', '宁波市');
INSERT INTO `university` VALUES ('2324', '台州职业技术学院', '11', '专科', 'http://www.tzvtc.com/', 'tzvtc', '台州市');
INSERT INTO `university` VALUES ('2325', '浙江工贸职业技术学院', '11', '专科', 'http://www.zjitc.net/', 'zjitc', '温州市');
INSERT INTO `university` VALUES ('2326', '浙江医药高等专科学校', '11', '专科', 'http://www.zjpc.net.cn/', 'zjpc', '宁波市');
INSERT INTO `university` VALUES ('2327', '浙江机电职业技术学院', '11', '专科', 'http://www.zime.edu.cn/', 'zime', '杭州市');
INSERT INTO `university` VALUES ('2328', '浙江建设职业技术学院', '11', '专科', 'http://www.zjjy.net/', 'zjjy', '杭州市');
INSERT INTO `university` VALUES ('2329', '浙江艺术职业学院', '11', '专科', 'http://www.zj-art.com/', 'zj-art', '杭州市');
INSERT INTO `university` VALUES ('2330', '浙江经贸职业技术学院', '11', '专科', 'http://www.zjiet.edu.cn/', 'zjiet', '杭州市');
INSERT INTO `university` VALUES ('2331', '浙江商业职业技术学院', '11', '专科', 'http://www.zjvcc.edu.cn/', 'zjvcc', '杭州市');
INSERT INTO `university` VALUES ('2332', '浙江经济职业技术学院', '11', '专科', 'http://www.zjtie.edu.cn/', 'zjtie', '杭州市');
INSERT INTO `university` VALUES ('2333', '浙江旅游职业学院', '11', '专科', 'http://www.tczj.net/', 'tczj', '杭州市');
INSERT INTO `university` VALUES ('2334', '浙江育英职业技术学院', '11', '专科', 'http://www.zjyyc.com/', 'zjyyc', '杭州市');
INSERT INTO `university` VALUES ('2335', '浙江警官职业学院', '11', '专科', 'http://www.zjjy.com.cn/', 'zjjy', '杭州市');
INSERT INTO `university` VALUES ('2336', '浙江金融职业学院', '11', '专科', 'http://www.zfc.edu.cn/', 'zfc', '杭州市');
INSERT INTO `university` VALUES ('2337', '浙江工业职业技术学院', '11', '专科', 'http://www.zjipc.com/', 'zjipc', '绍兴市');
INSERT INTO `university` VALUES ('2338', '杭州职业技术学院', '11', '专科', 'http://www.hzvtc.edu.cn/', 'hzvtc', '杭州市');
INSERT INTO `university` VALUES ('2339', '嘉兴职业技术学院', '11', '专科', 'http://www.jxvtc.net/', 'jxvtc', '嘉兴市');
INSERT INTO `university` VALUES ('2340', '湖州职业技术学院', '11', '专科', 'http://www.hzvtc.net/', 'hzvtc', '湖州市');
INSERT INTO `university` VALUES ('2341', '绍兴职业技术学院', '11', '专科', 'http://www.sxvtc.com/', 'sxvtc', '绍兴市');
INSERT INTO `university` VALUES ('2342', '衢州职业技术学院', '11', '专科', 'http://www.qzct.net/', 'qzct', '衢州市');
INSERT INTO `university` VALUES ('2343', '丽水职业技术学院', '11', '专科', 'http://www.lszjy.com/', 'lszjy', '丽水市');
INSERT INTO `university` VALUES ('2344', '浙江东方职业技术学院', '11', '专科', 'http://www.zjdfc.com/', 'zjdfc', '温州市');
INSERT INTO `university` VALUES ('2345', '义乌工商职业技术学院', '11', '专科', 'http://www.ywu.cn/', 'ywu', '义乌市');
INSERT INTO `university` VALUES ('2346', '浙江纺织服装职业技术学院', '11', '专科', 'http://www.zjff.net/', 'zjff', '宁波市');
INSERT INTO `university` VALUES ('2347', '杭州科技职业技术学院', '11', '专科', 'http://www.hzaspt.edu.cn/', 'hzaspt', '杭州市');
INSERT INTO `university` VALUES ('2348', '浙江长征职业技术学院', '11', '专科', 'http://www.zjczxy.cn/', 'zjczxy', '杭州市');
INSERT INTO `university` VALUES ('2349', '嘉兴南洋职业技术学院', '11', '专科', 'http://www.jxnyc.net/', 'jxnyc', '嘉兴市');
INSERT INTO `university` VALUES ('2350', '浙江广厦建设职业技术学院', '11', '专科', 'http://www.guangshaxy.com/', 'guangshaxy', '东阳市');
INSERT INTO `university` VALUES ('2351', '杭州万向职业技术学院', '11', '专科', 'http://www.wxpoly.cn/', 'wxpoly', '杭州市');
INSERT INTO `university` VALUES ('2352', '浙江邮电职业技术学院', '11', '专科', 'http://www.zptc.cn/', 'zptc', '绍兴市');
INSERT INTO `university` VALUES ('2353', '宁波卫生职业技术学院', '11', '专科', 'http://www.nchs.net.cn/', 'nchs', '宁波市');
INSERT INTO `university` VALUES ('2354', '台州科技职业学院', '11', '专科', 'http://www.tzvcst.net/', 'tzvcst', '台州市');
INSERT INTO `university` VALUES ('2355', '浙江国际海运职业技术学院', '11', '专科', 'http://www.zimc.cn/', 'zimc', '舟山市');
INSERT INTO `university` VALUES ('2356', '浙江体育职业技术学院', '11', '专科', 'http://www.zjcs.net.cn/', 'zjcs', '杭州市');
INSERT INTO `university` VALUES ('2357', '温州科技职业学院', '11', '专科', 'http://www.wzvcst.cn/', 'wzvcst', '温州市');
INSERT INTO `university` VALUES ('2358', '浙江汽车职业技术学院', '11', '专科', 'http://www.geelyedu.com/', 'geelyedu', '临海市');
INSERT INTO `university` VALUES ('2359', '浙江横店影视职业学院', '11', '专科', 'http://www.zjhyxy.net/', 'zjhyxy', '东阳市');
INSERT INTO `university` VALUES ('2360', '浙江农业商贸职业学院', '11', '专科', 'http://www.znszy.com/', 'znszy', '绍兴市');
INSERT INTO `university` VALUES ('2361', '浙江特殊教育职业学院', '11', '专科', 'http://www.zjtjxy.net/', 'zjtjxy', '杭州市');
INSERT INTO `university` VALUES ('2362', '浙江安防职业技术学院', '11', '专科', 'http://www.zjist.cn/', 'zjist', '温州市');
INSERT INTO `university` VALUES ('2363', '浙江舟山群岛新区旅游与健康职业学院', '11', '专科', 'http://www.zsthc.com/', 'zsthc', '舟山市');
INSERT INTO `university` VALUES ('2364', '安徽大学', '12', '本科', 'http://www.ahu.edu.cn/', 'ahu', '合肥市');
INSERT INTO `university` VALUES ('2365', '中国科学技术大学', '12', '本科', 'http://www.ustc.edu.cn/', 'ustc', '合肥市');
INSERT INTO `university` VALUES ('2366', '合肥工业大学', '12', '本科', 'http://www.hfut.edu.cn/', 'hfut', '合肥市');
INSERT INTO `university` VALUES ('2367', '安徽工业大学', '12', '本科', 'http://www.ahut.edu.cn/', 'ahut', '马鞍山');
INSERT INTO `university` VALUES ('2368', '安徽理工大学', '12', '本科', 'http://www.aust.edu.cn/', 'aust', '淮南市');
INSERT INTO `university` VALUES ('2369', '安徽工程大学', '12', '本科', 'http://www.ahpu.edu.cn/', 'ahpu', '芜湖市');
INSERT INTO `university` VALUES ('2370', '安徽农业大学', '12', '本科', 'http://www.ahau.edu.cn/', 'ahau', '合肥市');
INSERT INTO `university` VALUES ('2371', '安徽医科大学', '12', '本科', 'http://www.ahmu.edu.cn/', 'ahmu', '合肥市');
INSERT INTO `university` VALUES ('2372', '蚌埠医学院', '12', '本科', 'http://www.bbmc.edu.cn/', 'bbmc', '蚌埠市');
INSERT INTO `university` VALUES ('2373', '皖南医学院', '12', '本科', 'http://www.wnmc.edu.cn/', 'wnmc', '芜湖市');
INSERT INTO `university` VALUES ('2374', '安徽中医药大学', '12', '本科', 'http://www.ahtcm.edu.cn/', 'ahtcm', '合肥市');
INSERT INTO `university` VALUES ('2375', '安徽师范大学', '12', '本科', 'http://www.ahnu.edu.cn/', 'ahnu', '芜湖市');
INSERT INTO `university` VALUES ('2376', '阜阳师范学院', '12', '本科', 'http://www.fync.edu.cn/', 'fync', '阜阳市');
INSERT INTO `university` VALUES ('2377', '安庆师范大学', '12', '本科', 'http://www.aqnu.edu.cn/', 'aqnu', '安庆市');
INSERT INTO `university` VALUES ('2378', '淮北师范大学', '12', '本科', 'http://www.hbcnc.edu.cn/', 'hbcnc', '淮北市');
INSERT INTO `university` VALUES ('2379', '黄山学院', '12', '本科', 'http://www.hsu.edu.cn/', 'hsu', '屯溪市');
INSERT INTO `university` VALUES ('2380', '皖西学院', '12', '本科', 'http://www.wxc.edu.cn/', 'wxc', '六安市');
INSERT INTO `university` VALUES ('2381', '滁州学院', '12', '本科', 'http://www.chzu.edu.cn/', 'chzu', '滁州市');
INSERT INTO `university` VALUES ('2382', '安徽财经大学', '12', '本科', 'http://www.aufe.edu.cn/', 'aufe', '蚌埠市');
INSERT INTO `university` VALUES ('2383', '宿州学院', '12', '本科', 'http://www.ahsztc.edu.cn/', 'ahsztc', '宿州市');
INSERT INTO `university` VALUES ('2384', '巢湖学院', '12', '本科', 'http://www.chtc.edu.cn/', 'chtc', '巢湖市');
INSERT INTO `university` VALUES ('2385', '淮南师范学院', '12', '本科', 'http://www.hnnu.edu.cn/', 'hnnu', '淮南市');
INSERT INTO `university` VALUES ('2386', '铜陵学院', '12', '本科', 'http://www.tlc.edu.cn/', 'tlc', '铜陵市');
INSERT INTO `university` VALUES ('2387', '安徽建筑大学', '12', '本科', 'http://www.ahjzu.edu.cn/', 'ahjzu', '合肥市');
INSERT INTO `university` VALUES ('2388', '安徽科技学院', '12', '本科', 'http://www.ahstu.edu.cn/', 'ahstu', '滁州市');
INSERT INTO `university` VALUES ('2389', '安徽三联学院', '12', '本科', 'http://www.sanlian.net.cn/', 'sanlian', '合肥市');
INSERT INTO `university` VALUES ('2390', '合肥学院', '12', '本科', 'http://www.hfuu.edu.cn/', 'hfuu', '合肥市');
INSERT INTO `university` VALUES ('2391', '蚌埠学院', '12', '本科', 'http://www.bbc.edu.cn/', 'bbc', '蚌埠市');
INSERT INTO `university` VALUES ('2392', '池州学院', '12', '本科', 'http://www.czu.edu.cn/', 'czu', '贵池市');
INSERT INTO `university` VALUES ('2393', '安徽新华学院', '12', '本科', 'http://www.axhu.cn/', 'axhu', '合肥市');
INSERT INTO `university` VALUES ('2394', '安徽文达信息工程学院', '12', '本科', 'http://www.wendaedu.com.cn/', 'wendaedu', '合肥市');
INSERT INTO `university` VALUES ('2395', '亳州学院', '12', '本科', 'http://www.bzuu.edu.cn/', 'bzuu', '亳州市');
INSERT INTO `university` VALUES ('2396', '安徽外国语学院', '12', '本科', 'http://www.aflc.com.cn/', 'aflc', '合肥市');
INSERT INTO `university` VALUES ('2397', '安徽财经大学商学院', '12', '本科', 'http://acsxy.aufe.edu.cn/', 'aufe', '蚌埠市');
INSERT INTO `university` VALUES ('2398', '安徽大学江淮学院', '12', '本科', 'http://www.ahujhc.cn/', 'ahujhc', '合肥市');
INSERT INTO `university` VALUES ('2399', '安徽信息工程学院', '12', '本科', 'http://www.ahpumec.edu.cn', 'ahpumec', '芜湖市');
INSERT INTO `university` VALUES ('2400', '安徽工业大学工商学院', '12', '本科', 'http://icc.ahut.edu.cn/', 'ahut', '马鞍山市');
INSERT INTO `university` VALUES ('2401', '安徽建筑大学城市建设学院', '12', '本科', 'http://www.aiai.edu.cn/cjxy/', 'aiai', '合肥市');
INSERT INTO `university` VALUES ('2402', '安徽农业大学经济技术学院', '12', '本科', 'http://jjjs.ahau.edu.cn/', 'ahau', '合肥市');
INSERT INTO `university` VALUES ('2403', '安徽师范大学皖江学院', '12', '本科', 'http://wjxy.ahnu.edu.cn/', 'ahnu', '芜湖市');
INSERT INTO `university` VALUES ('2404', '安徽医科大学临床医学院', '12', '本科', 'http://cc.ahmu.edu.cn/', 'ahmu', '合肥市');
INSERT INTO `university` VALUES ('2405', '阜阳师范学院信息工程学院', '12', '本科', 'http://www.fync.edu.cn/cie/', 'fync', '阜阳市');
INSERT INTO `university` VALUES ('2406', '淮北师范大学信息学院', '12', '本科', 'http://www.hbcnc.edu.cn/Site/x', 'hbcnc', '淮北市');
INSERT INTO `university` VALUES ('2407', '合肥师范学院', '12', '本科', 'http://www.hfnu.edu.cn/', 'hfnu', '合肥市');
INSERT INTO `university` VALUES ('2408', '河海大学文天学院', '12', '本科', 'http://wtian.hhu.edu.cn/', 'hhu', '马鞍山市');
INSERT INTO `university` VALUES ('2409', '安徽职业技术学院', '12', '专科', 'http://www.ahzybys.com/', 'ahzybys', '合肥市');
INSERT INTO `university` VALUES ('2410', '淮北职业技术学院', '12', '专科', 'http://www.hbvtc.net/', 'hbvtc', '淮北市');
INSERT INTO `university` VALUES ('2411', '芜湖职业技术学院', '12', '专科', 'http://www.whptu.ah.cn/', 'whptu', '芜湖市');
INSERT INTO `university` VALUES ('2412', '淮南联合大学', '12', '专科', 'http://www.hnuu.edu.cn/', 'hnuu', '淮南市');
INSERT INTO `university` VALUES ('2413', '安徽商贸职业技术学院', '12', '专科', 'http://www.abc.edu.cn/', 'abc', '合肥市');
INSERT INTO `university` VALUES ('2414', '安徽水利水电职业技术学院', '12', '专科', 'http://www.ahsdxy.ah.edu.cn/', 'ahsdxy', '合肥市');
INSERT INTO `university` VALUES ('2415', '阜阳职业技术学院', '12', '专科', 'http://www.fyvtc.edu.cn/', 'fyvtc', '阜阳市');
INSERT INTO `university` VALUES ('2416', '铜陵职业技术学院', '12', '专科', 'http://www.tlpt.net.cn/', 'tlpt', '铜陵市');
INSERT INTO `university` VALUES ('2417', '民办万博科技职业学院', '12', '专科', 'http://www.wbc.edu.cn/', 'wbc', '合肥市');
INSERT INTO `university` VALUES ('2418', '安徽警官职业学院', '12', '专科', 'http://www.ahjgxy.com/', 'ahjgxy', '合肥市');
INSERT INTO `university` VALUES ('2419', '淮南职业技术学院', '12', '专科', 'http://www.hnvtc.ah.edu.cn/', 'hnvtc', '淮南市');
INSERT INTO `university` VALUES ('2420', '安徽工业经济职业技术学院', '12', '专科', 'http://www.ahiec.net/', 'ahiec', '合肥市');
INSERT INTO `university` VALUES ('2421', '合肥通用职业技术学院', '12', '专科', 'http://www.hftyxy.com/', 'hftyxy', '合肥市');
INSERT INTO `university` VALUES ('2422', '安徽工贸职业技术学院', '12', '专科', 'http://www.ahgmedu.cn/', 'ahgmedu', '淮南市');
INSERT INTO `university` VALUES ('2423', '宿州职业技术学院', '12', '专科', 'http://www.szzy.ah.cn/', 'szzy', '宿州市');
INSERT INTO `university` VALUES ('2424', '六安职业技术学院', '12', '专科', 'http://www.lvtc.edu.cn/', 'lvtc', '六安市');
INSERT INTO `university` VALUES ('2425', '安徽电子信息职业技术学院', '12', '专科', 'http://www.avceit.cn/', 'avceit', '蚌埠市');
INSERT INTO `university` VALUES ('2426', '民办合肥经济技术职业学院', '12', '专科', 'http://www.hfet.com/', 'hfet', '合肥市');
INSERT INTO `university` VALUES ('2427', '安徽交通职业技术学院', '12', '专科', 'http://www.ahctc.com/', 'ahctc', '合肥市');
INSERT INTO `university` VALUES ('2428', '安徽体育运动职业技术学院', '12', '专科', 'http://www.ahty.net/', 'ahty', '合肥市');
INSERT INTO `university` VALUES ('2429', '安徽中医药高等专科学校', '12', '专科', 'http://www.ahzyygz.com/', 'ahzyygz', '芜湖市');
INSERT INTO `university` VALUES ('2430', '安徽医学高等专科学校', '12', '专科', 'http://www.ahyz.cn/', 'ahyz', '合肥市');
INSERT INTO `university` VALUES ('2431', '合肥职业技术学院', '12', '专科', 'http://www.chzy.org/', 'chzy', '巢湖市');
INSERT INTO `university` VALUES ('2432', '滁州职业技术学院', '12', '专科', 'http://www.czc.net.cn/', 'czc', '滁州市');
INSERT INTO `university` VALUES ('2433', '池州职业技术学院', '12', '专科', 'http://www.czgz.cn/', 'czgz', '池州市');
INSERT INTO `university` VALUES ('2434', '宣城职业技术学院', '12', '专科', 'http://www.xczy.net.cn/', 'xczy', '宣城市');
INSERT INTO `university` VALUES ('2435', '安徽广播影视职业技术学院', '12', '专科', 'http://www.amtc.cn/', 'amtc', '合肥市');
INSERT INTO `university` VALUES ('2436', '民办合肥滨湖职业技术学院', '12', '专科', 'http://www.hfbhxy.com/', 'hfbhxy', '合肥市');
INSERT INTO `university` VALUES ('2437', '安徽电气工程职业技术学院', '12', '专科', 'http://www.aepu.com.cn/', 'aepu', '合肥市');
INSERT INTO `university` VALUES ('2438', '安徽冶金科技职业学院', '12', '专科', 'http://www.ahyky.com/', 'ahyky', '马鞍山');
INSERT INTO `university` VALUES ('2439', '安徽城市管理职业学院', '12', '专科', 'http://www.cmoc.cn/', 'cmoc', '合肥市');
INSERT INTO `university` VALUES ('2440', '安徽机电职业技术学院', '12', '专科', 'http://www.ahcme.cn/', 'ahcme', '芜湖市');
INSERT INTO `university` VALUES ('2441', '安徽工商职业学院', '12', '专科', 'http://www.ahbvc.cn/', 'ahbvc', '合肥市');
INSERT INTO `university` VALUES ('2442', '安徽中澳科技职业学院', '12', '专科', 'http://www.acac.cn/', 'acac', '合肥市');
INSERT INTO `university` VALUES ('2443', '阜阳科技职业学院', '12', '专科', 'http://www.fky.net.cn/', 'fky', '阜阳市');
INSERT INTO `university` VALUES ('2444', '亳州职业技术学院', '12', '专科', 'http://www.bzvtc.com/', 'bzvtc', '亳州市');
INSERT INTO `university` VALUES ('2445', '安徽国防科技职业学院', '12', '专科', 'http://www.ahgf.com.cn/', 'ahgf', '六安市');
INSERT INTO `university` VALUES ('2446', '安庆职业技术学院', '12', '专科', 'http://www.aqvtc.cn/', 'aqvtc', '安庆市');
INSERT INTO `university` VALUES ('2447', '安徽艺术职业学院', '12', '专科', 'http://www.artah.cn/', 'artah', '合肥市');
INSERT INTO `university` VALUES ('2448', '马鞍山师范高等专科学校', '12', '专科', 'http://www.massz.cn/', 'massz', '马鞍山');
INSERT INTO `university` VALUES ('2449', '安徽财贸职业学院', '12', '专科', 'http://www.aftvc.com/', 'aftvc', '合肥市');
INSERT INTO `university` VALUES ('2450', '安徽国际商务职业学院', '12', '专科', 'http://www.ahiib.com/', 'ahiib', '合肥市');
INSERT INTO `university` VALUES ('2451', '安徽公安职业学院', '12', '专科', 'http://www.ahgaxy.com.cn/', 'ahgaxy', '合肥市');
INSERT INTO `university` VALUES ('2452', '安徽林业职业技术学院', '12', '专科', 'http://www.ahlyxy.cn/', 'ahlyxy', '合肥市');
INSERT INTO `university` VALUES ('2453', '安徽审计职业学院', '12', '专科', 'http://www.ahsjxy.cn/', 'ahsjxy', '合肥市');
INSERT INTO `university` VALUES ('2454', '安徽新闻出版职业技术学院', '12', '专科', 'http://www.ahcbxy.cn/', 'ahcbxy', '合肥市');
INSERT INTO `university` VALUES ('2455', '安徽邮电职业技术学院', '12', '专科', 'http://www.ahptc.cn/', 'ahptc', '合肥市');
INSERT INTO `university` VALUES ('2456', '安徽工业职业技术学院', '12', '专科', 'http://www.ahip.cn/', 'ahip', '铜陵市');
INSERT INTO `university` VALUES ('2457', '民办合肥财经职业学院', '12', '专科', 'http://www.hffe.cn/', 'hffe', '合肥市');
INSERT INTO `university` VALUES ('2458', '安庆医药高等专科学校', '12', '专科', 'http://www.aqyyz.cn/', 'aqyyz', '芜湖市');
INSERT INTO `university` VALUES ('2459', '安徽涉外经济职业学院', '12', '专科', 'http://www.ahaec-edu.cn/', 'ahaec-edu', '合肥市');
INSERT INTO `university` VALUES ('2460', '安徽绿海商务职业学院', '12', '专科', 'http://www.lhub.cn/', 'lhub', '合肥市');
INSERT INTO `university` VALUES ('2461', '合肥共达职业技术学院', '12', '专科', 'http://gdxy.hfut.edu.cn/', 'hfut', '合肥市');
INSERT INTO `university` VALUES ('2462', '蚌埠经济技术职业学院', '12', '专科', 'http://www.bjy.ah.cn/', 'bjy', '蚌埠市');
INSERT INTO `university` VALUES ('2463', '民办安徽旅游职业学院', '12', '专科', 'http://www.ahlyedu.cn/', 'ahlyedu', '颍上县');
INSERT INTO `university` VALUES ('2464', '徽商职业学院', '12', '专科', 'http://www.huishangedu.cn/', 'huishanged', '合肥市');
INSERT INTO `university` VALUES ('2465', '马鞍山职业技术学院', '12', '专科', 'http://www.mastc.edu.cn/', 'mastc', '马鞍山');
INSERT INTO `university` VALUES ('2466', '安徽现代信息工程职业学院', '12', '专科', 'http://www.ahmodern.cn/', 'ahmodern', '寿县');
INSERT INTO `university` VALUES ('2467', '安徽矿业职业技术学院', '12', '专科', 'http://www.anhky.com/', 'anhky', '淮北市');
INSERT INTO `university` VALUES ('2468', '合肥信息技术职业学院', '12', '专科', 'http://www.hfitu.cn/', 'hfitu', '合肥市');
INSERT INTO `university` VALUES ('2469', '桐城师范高等专科学校', '12', '专科', 'http://www.tcsfgz.com/', 'tcsfgz', '安庆市');
INSERT INTO `university` VALUES ('2470', '黄山职业技术学院', '12', '专科', 'http://www.hszyxy.com/', 'hszyxy', '黄山市');
INSERT INTO `university` VALUES ('2471', '滁州城市职业学院', '12', '专科', 'http://www.czcvc.net/', 'czcvc', '凤阳县');
INSERT INTO `university` VALUES ('2472', '安徽汽车职业技术学院', '12', '专科', 'http://www.jacedu.cn/', 'jacedu', '合肥市');
INSERT INTO `university` VALUES ('2473', '皖西卫生职业学院', '12', '专科', 'http://www.wxwsxy.cn/', 'wxwsxy', '六安市');
INSERT INTO `university` VALUES ('2474', '合肥幼儿师范高等专科学校', '12', '专科', 'http://www.hfpec.edu.cn/', 'hfpec', '合肥市');
INSERT INTO `university` VALUES ('2475', '安徽长江职业学院', '12', '专科', 'http://www.ahcjxy.com/', 'ahcjxy', '合肥市');
INSERT INTO `university` VALUES ('2476', '安徽扬子职业技术学院', '12', '专科', 'http://www.yangzixueyuan.com/', 'yangzixuey', '芜湖市');
INSERT INTO `university` VALUES ('2477', '安徽黄梅戏艺术职业学院', '12', '专科', 'http://www.ahmxx.cn/', 'ahmxx', '安庆市');
INSERT INTO `university` VALUES ('2478', '安徽粮食工程职业学院', '12', '专科', 'http://www.ahlsxy.cn/', 'ahlsxy', '合肥市');
INSERT INTO `university` VALUES ('2479', '安徽卫生健康职业学院', '12', '专科', 'http://www.ahrkxy.com/', 'ahrkxy', '池州市');
INSERT INTO `university` VALUES ('2480', '合肥科技职业学院', '12', '专科', 'http://www.hstu.edu.cn/', 'hstu', '合肥市');
INSERT INTO `university` VALUES ('2481', '皖北卫生职业学院', '12', '专科', 'http://www.wbwsxy.com/', 'wbwsxy', '宿州市');
INSERT INTO `university` VALUES ('2482', '阜阳幼儿师范高等专科学校', '12', '专科', 'http://www.fysf.net/', 'fysf', '阜阳市');
INSERT INTO `university` VALUES ('2483', '厦门大学', '13', '本科', 'http://www.xmu.edu.cn/', 'xmu', '厦门市');
INSERT INTO `university` VALUES ('2484', '华侨大学', '13', '本科', 'http://www.hqu.edu.cn/', 'hqu', '泉州市');
INSERT INTO `university` VALUES ('2485', '福州大学', '13', '本科', 'http://www.fzu.edu.cn/', 'fzu', '福州市');
INSERT INTO `university` VALUES ('2486', '福建工程学院', '13', '本科', 'http://www.fjut.edu.cn/', 'fjut', '福州市');
INSERT INTO `university` VALUES ('2487', '福建农林大学', '13', '本科', 'http://www.fafu.edu.cn/', 'fafu', '福州市');
INSERT INTO `university` VALUES ('2488', '集美大学', '13', '本科', 'http://www.jmu.edu.cn/', 'jmu', '厦门市');
INSERT INTO `university` VALUES ('2489', '福建医科大学', '13', '本科', 'http://www.fjmu.edu.cn/', 'fjmu', '福州市');
INSERT INTO `university` VALUES ('2490', '福建中医药大学', '13', '本科', 'http://www.fjtcm.edu.cn/', 'fjtcm', '福州市');
INSERT INTO `university` VALUES ('2491', '福建师范大学', '13', '本科', 'http://www.fjnu.edu.cn/', 'fjnu', '福州市');
INSERT INTO `university` VALUES ('2492', '闽江学院', '13', '本科', 'http://www.mju.edu.cn/', 'mju', '福州市');
INSERT INTO `university` VALUES ('2493', '武夷学院', '13', '本科', 'http://www.nptc.edu.cn/', 'nptc', '南平市');
INSERT INTO `university` VALUES ('2494', '宁德师范学院', '13', '专科', 'http://www.ndsz.net/', 'ndsz', '宁德市');
INSERT INTO `university` VALUES ('2495', '泉州师范学院', '13', '本科', 'http://www.qztc.edu.cn/', 'qztc', '泉州市');
INSERT INTO `university` VALUES ('2496', '闽南师范大学', '13', '本科', 'http://www.fjzs.edu.cn/', 'fjzs', '漳州市');
INSERT INTO `university` VALUES ('2497', '厦门理工学院', '13', '本科', 'http://www.xmut.edu.cn/', 'xmut', '厦门市');
INSERT INTO `university` VALUES ('2498', '三明学院', '13', '本科', 'http://www.fjsmu.cn/', 'fjsmu', '三明市');
INSERT INTO `university` VALUES ('2499', '龙岩学院', '13', '本科', 'http://www.lyun.edu.cn/', 'lyun', '龙岩市');
INSERT INTO `university` VALUES ('2500', '福建商学院', '13', '本科', 'http://www.fjcc.edu.cn/', 'fjcc', '福州市');
INSERT INTO `university` VALUES ('2501', '福建警察学院', '13', '本科', 'http://www.fjpsc.edu.cn/', 'fjpsc', '福州市');
INSERT INTO `university` VALUES ('2502', '莆田学院', '13', '本科', 'http://202.101.111.193/', '101', '莆田市');
INSERT INTO `university` VALUES ('2503', '仰恩大学', '13', '本科', 'http://www.yeu.edu.cn/', 'yeu', '泉州市');
INSERT INTO `university` VALUES ('2504', '厦门医学院', '13', '本科', 'http://www.xmmc.edu.cn/', 'xmmc', '福州市');
INSERT INTO `university` VALUES ('2505', '厦门华厦学院', '13', '本科', 'http://www.hxxy.edu.cn/', 'hxxy', '厦门市');
INSERT INTO `university` VALUES ('2506', '闽南理工学院', '13', '本科', 'http://www.mnust.cn/', 'mnust', '泉州市');
INSERT INTO `university` VALUES ('2507', '福建师范大学闽南科技学院', '13', '本科', 'http://www.mnkjxy.com/', 'mnkjxy', '泉州市');
INSERT INTO `university` VALUES ('2508', '福建农林大学东方学院', '13', '本科', 'http://www.fafuoc.com/', 'fafuoc', '福州市');
INSERT INTO `university` VALUES ('2509', '厦门工学院', '13', '本科', 'http://www.xit.edu.cn/', 'xit', '厦门市');
INSERT INTO `university` VALUES ('2510', '阳光学院', '13', '本科', 'http://ygxy.fzu.edu.cn/', 'fzu', '福州市');
INSERT INTO `university` VALUES ('2511', '厦门大学嘉庚学院', '13', '本科', 'http://jgxy.xmu.edu.cn/', 'xmu', '厦门市');
INSERT INTO `university` VALUES ('2512', '福州大学至诚学院', '13', '本科', 'http://www.fdzcxy.com/', 'fdzcxy', '福州市');
INSERT INTO `university` VALUES ('2513', '集美大学诚毅学院', '13', '本科', 'http://chengyi.jmu.edu.cn/', 'jmu', '厦门市');
INSERT INTO `university` VALUES ('2514', '福建师范大学协和学院', '13', '本科', 'http://cuc.fjnu.edu.cn/', 'fjnu', '福州市');
INSERT INTO `university` VALUES ('2515', '福州外语外贸学院', '13', '本科', 'http://www.fzfu.com/', 'fzfu', '福州市');
INSERT INTO `university` VALUES ('2516', '福建江夏学院', '13', '本科', 'http://www.jxxyc.cn/', 'jxxyc', '福州市');
INSERT INTO `university` VALUES ('2517', '泉州信息工程学院', '13', '本科', 'http://www.qziedu.cn/', 'qziedu', '福州市');
INSERT INTO `university` VALUES ('2518', '福州理工学院', '13', '本科', 'http://www.hxcollege.com/', 'hxcollege', '福州市');
INSERT INTO `university` VALUES ('2519', '福建农林大学金山学院', '13', '本科', 'http://www2.fjau.edu.cn/jsxy/', 'fjau', '福州市');
INSERT INTO `university` VALUES ('2520', '福建船政交通职业学院', '13', '专科', 'http://www.fjcpc.edu.cn/', 'fjcpc', '福州市');
INSERT INTO `university` VALUES ('2521', '漳州职业技术学院', '13', '专科', 'http://www.fjzzit.cn/', 'fjzzit', '漳州市');
INSERT INTO `university` VALUES ('2522', '闽西职业技术学院', '13', '专科', 'http://www.mxdx.net/', 'mxdx', '龙岩市');
INSERT INTO `university` VALUES ('2523', '黎明职业大学', '13', '专科', 'http://www.lmu.cn/', 'lmu', '泉州市');
INSERT INTO `university` VALUES ('2524', '福建华南女子职业学院', '13', '专科', 'http://www.hnwomen.com.cn/', 'hnwomen', '福州市');
INSERT INTO `university` VALUES ('2525', '福州职业技术学院', '13', '专科', 'http://www.fvti.cn/', 'fvti', '福州市');
INSERT INTO `university` VALUES ('2526', '福建林业职业技术学院', '13', '专科', 'http://www.fjlzy.com/', 'fjlzy', '南平市');
INSERT INTO `university` VALUES ('2527', '福建信息职业技术学院', '13', '专科', 'http://www.mitu.cn/', 'mitu', '福州市');
INSERT INTO `university` VALUES ('2528', '福建水利电力职业技术学院', '13', '专科', 'http://www.fjsdxy.com/', 'fjsdxy', '永安市');
INSERT INTO `university` VALUES ('2529', '福建电力职业技术学院', '13', '专科', 'http://www.fjdy.net/', 'fjdy', '泉州市');
INSERT INTO `university` VALUES ('2530', '厦门海洋职业技术学院', '13', '专科', 'http://www.xmoc.cn/', 'xmoc', '厦门市');
INSERT INTO `university` VALUES ('2531', '福建农业职业技术学院', '13', '专科', 'http://www.fjny.com/', 'fjny', '福州市');
INSERT INTO `university` VALUES ('2532', '福建卫生职业技术学院', '13', '专科', 'http://www.fjwx.com.cn/', 'fjwx', '福州市');
INSERT INTO `university` VALUES ('2533', '泉州医学高等专科学校', '13', '专科', 'http://www.qzygz.com/', 'qzygz', '泉州市');
INSERT INTO `university` VALUES ('2534', '福州英华职业学院', '13', '专科', 'http://www.fzacc.com/', 'fzacc', '福州市');
INSERT INTO `university` VALUES ('2535', '泉州纺织服装职业学院', '13', '专科', 'http://www.qzfzfz.com/', 'qzfzfz', '泉州市');
INSERT INTO `university` VALUES ('2536', '泉州华光职业学院', '13', '专科', 'http://www.hgu.cn/', 'hgu', '泉州市');
INSERT INTO `university` VALUES ('2537', '泉州理工职业学院', '13', '专科', 'http://www.qzit.edu.cn/', 'qzit', '泉州市');
INSERT INTO `university` VALUES ('2538', '闽北职业技术学院', '13', '专科', 'http://www.mbu.cn/', 'mbu', '南平市');
INSERT INTO `university` VALUES ('2539', '福州黎明职业技术学院', '13', '专科', 'http://www.fzlmxy.cn/', 'fzlmxy', '福州市');
INSERT INTO `university` VALUES ('2540', '厦门演艺职业学院', '13', '专科', 'http://www.xmyanyi.com/', 'xmyanyi', '厦门市');
INSERT INTO `university` VALUES ('2541', '厦门华天涉外职业技术学院', '13', '专科', 'http://www.xmht.com/', 'xmht', '厦门市');
INSERT INTO `university` VALUES ('2542', '福州科技职业技术学院', '13', '专科', 'http://www.fzstc.com/', 'fzstc', '福州市');
INSERT INTO `university` VALUES ('2543', '泉州经贸职业技术学院', '13', '专科', 'http://www.qzjmc.cn/', 'qzjmc', '泉州市');
INSERT INTO `university` VALUES ('2544', '福建对外经济贸易职业技术学院', '13', '专科', 'http://www.fibec.cn/', 'fibec', '福州市');
INSERT INTO `university` VALUES ('2545', '湄洲湾职业技术学院', '13', '专科', 'http://www.fjmzw.com/', 'fjmzw', '湄洲');
INSERT INTO `university` VALUES ('2546', '福建生物工程职业技术学院', '13', '专科', 'http://www.fjvcb.cn/', 'fjvcb', '福州市');
INSERT INTO `university` VALUES ('2547', '福建艺术职业学院', '13', '专科', 'http://www.fjyszyxy.com/', 'fjyszyxy', '福州市');
INSERT INTO `university` VALUES ('2548', '福建幼儿师范高等专科学校', '13', '专科', 'http://www.fj61.net/', 'fj61', '福州市');
INSERT INTO `university` VALUES ('2549', '厦门城市职业学院', '13', '专科', 'http://www.xmcu.cn/', 'xmcu', '厦门市');
INSERT INTO `university` VALUES ('2550', '泉州工艺美术职业学院', '13', '专科', 'http://www.dhcc.cc/', 'dhcc', '泉州市');
INSERT INTO `university` VALUES ('2551', '三明医学科技职业学院', '13', '专科', 'http://www.smvtc.com/', 'smvtc', '三明市');
INSERT INTO `university` VALUES ('2552', '宁德职业技术学院', '13', '专科', 'http://www.ndgzy.com/', 'ndgzy', '宁德市');
INSERT INTO `university` VALUES ('2553', '福州软件职业技术学院', '13', '专科', 'http://www.fzrjxy.com/', 'fzrjxy', '福州市');
INSERT INTO `university` VALUES ('2554', '厦门兴才职业技术学院', '13', '专科', 'http://www.xmxc.com/', 'xmxc', '厦门市');
INSERT INTO `university` VALUES ('2555', '厦门软件职业技术学院', '13', '专科', 'http://www.xmstc.cn/', 'xmstc', '厦门市');
INSERT INTO `university` VALUES ('2556', '福建体育职业技术学院', '13', '专科', 'http://www.fjipe.cn/', 'fjipe', '福州市');
INSERT INTO `university` VALUES ('2557', '漳州城市职业学院', '13', '专科', 'http://www.zcvc.cn/', 'zcvc', '漳州市');
INSERT INTO `university` VALUES ('2558', '厦门南洋职业学院', '13', '专科', 'http://www.ny2000.com/', 'ny2000', '厦门市');
INSERT INTO `university` VALUES ('2559', '厦门东海职业技术学院', '13', '专科', 'http://www.xmdh.com/', 'xmdh', '厦门市');
INSERT INTO `university` VALUES ('2560', '漳州科技职业学院', '13', '专科', 'http://www.tftc.edu.cn/', 'tftc', '漳州市');
INSERT INTO `university` VALUES ('2561', '漳州理工职业学院', '13', '专科', 'http://www.zzlg.org/', 'zzlg', '漳州市');
INSERT INTO `university` VALUES ('2562', '武夷山职业学院', '13', '专科', 'http://www.wyszyxy.com/', 'wyszyxy', '武夷山市');
INSERT INTO `university` VALUES ('2563', '漳州卫生职业学院', '13', '专科', 'http://www.zzyhxy.com/', 'zzyhxy', '漳州市');
INSERT INTO `university` VALUES ('2564', '泉州海洋职业学院', '13', '专科', 'http://www.qzoiedu.com/', 'qzoiedu', '泉州市');
INSERT INTO `university` VALUES ('2565', '泉州轻工职业学院', '13', '专科', 'http://www.qzqgxy.com/', 'qzqgxy', '泉州市');
INSERT INTO `university` VALUES ('2566', '厦门安防科技职业学院', '13', '专科', 'http://www.xmafkj.com/', 'xmafkj', '厦门市');
INSERT INTO `university` VALUES ('2567', '泉州幼儿师范高等专科学校', '13', '专科', 'http://www.qzygz.net/', 'qzygz', '泉州市');
INSERT INTO `university` VALUES ('2568', '闽江师范高等专科学校', '13', '专科', 'http://www.fzjyxy.com/', 'fzjyxy', '福州市');
INSERT INTO `university` VALUES ('2569', '泉州工程职业技术学院', '13', '专科', 'http://www.qzgcxy.com/', 'qzgcxy', '泉州市');
INSERT INTO `university` VALUES ('2570', '福州墨尔本理工职业学院', '13', '专科', 'http://www.fmp.edu.cn/', 'fmp', '福州市');
INSERT INTO `university` VALUES ('2571', '南昌大学', '14', '本科', 'http://www.ncu.edu.cn/', 'ncu', '南昌市');
INSERT INTO `university` VALUES ('2572', '华东交通大学', '14', '本科', 'http://www.ecjtu.jx.cn/', 'ecjtu', '南昌市');
INSERT INTO `university` VALUES ('2573', '东华理工大学', '14', '本科', 'http://www.ecit.edu.cn/', 'ecit', '抚州市');
INSERT INTO `university` VALUES ('2574', '南昌航空大学', '14', '本科', 'http://www.nchu.edu.cn/', 'nchu', '南昌市');
INSERT INTO `university` VALUES ('2575', '江西理工大学', '14', '本科', 'http://www.jxust.cn/', 'jxust', '赣州市');
INSERT INTO `university` VALUES ('2576', '景德镇陶瓷大学', '14', '本科', 'http://www.jci.edu.cn/', 'jci', '景德镇');
INSERT INTO `university` VALUES ('2577', '江西农业大学', '14', '本科', 'http://www.jxau.edu.cn/', 'jxau', '南昌市');
INSERT INTO `university` VALUES ('2578', '江西中医药大学', '14', '本科', 'http://www.jxtcmi.com/', 'jxtcmi', '南昌市');
INSERT INTO `university` VALUES ('2579', '赣南医学院', '14', '本科', 'http://www.gmu.cn/', 'gmu', '赣州市');
INSERT INTO `university` VALUES ('2580', '江西师范大学', '14', '本科', 'http://www.jxnu.edu.cn/', 'jxnu', '南昌市');
INSERT INTO `university` VALUES ('2581', '上饶师范学院', '14', '本科', 'http://www.sru.jx.cn/', 'sru', '上饶市');
INSERT INTO `university` VALUES ('2582', '宜春学院', '14', '本科', 'http://www.ycu.jx.cn/', 'ycu', '宜春市');
INSERT INTO `university` VALUES ('2583', '赣南师范大学', '14', '本科', 'http://www.gnnu.cn/', 'gnnu', '赣州市');
INSERT INTO `university` VALUES ('2584', '井冈山大学', '14', '本科', 'http://www.jgsu.edu.cn/', 'jgsu', '吉安市');
INSERT INTO `university` VALUES ('2585', '江西财经大学', '14', '本科', 'http://www.jxufe.edu.cn/', 'jxufe', '南昌市');
INSERT INTO `university` VALUES ('2586', '江西科技学院', '14', '本科', 'http://www.jxut.edu.cn/', 'jxut', '南昌市');
INSERT INTO `university` VALUES ('2587', '景德镇学院', '14', '本科', 'http://www.jdzu.jx.cn/', 'jdzu', '景德镇');
INSERT INTO `university` VALUES ('2588', '萍乡学院', '14', '本科', 'http://www.pxc.jx.cn/', 'pxc', '萍乡市');
INSERT INTO `university` VALUES ('2589', '江西科技师范大学', '14', '本科', 'http://www.jxstnu.cn/', 'jxstnu', '南昌市');
INSERT INTO `university` VALUES ('2590', '南昌工程学院', '14', '本科', 'http://www.nit.edu.cn/', 'nit', '南昌市');
INSERT INTO `university` VALUES ('2591', '江西警察学院', '14', '本科', 'http://www.jxga.com/', 'jxga', '南昌市');
INSERT INTO `university` VALUES ('2592', '新余学院', '14', '本科', 'http://www.xyc.edu.cn/', 'xyc', '新余市');
INSERT INTO `university` VALUES ('2593', '九江学院', '14', '本科', 'http://www.jju.edu.cn/', 'jju', '九江市');
INSERT INTO `university` VALUES ('2594', '江西工程学院', '14', '本科', 'http://www.ygxy.com/', 'ygxy', '新余市');
INSERT INTO `university` VALUES ('2595', '南昌理工学院', '14', '本科', 'http://www.nclg.com.cn/', 'nclg', '南昌市');
INSERT INTO `university` VALUES ('2596', '江西应用科技学院', '14', '本科', 'http://www.jxcsedu.com/', 'jxcsedu', '南昌市');
INSERT INTO `university` VALUES ('2597', '江西服装学院', '14', '本科', 'http://www.fuzhuang.com.cn/', 'fuzhuang', '南昌市');
INSERT INTO `university` VALUES ('2598', '南昌工学院', '14', '本科', 'http://www.ncgxy.com/', 'ncgxy', '南昌市');
INSERT INTO `university` VALUES ('2599', '南昌大学科学技术学院', '14', '本科', 'http://www.ndkj.com.cn/', 'ndkj', '南昌市');
INSERT INTO `university` VALUES ('2600', '南昌大学共青学院', '14', '本科', 'http://www.ndgy.net/', 'ndgy', '共青城');
INSERT INTO `university` VALUES ('2601', '华东交通大学理工学院', '14', '本科', 'http://www.ecjtuit.com.cn/', 'ecjtuit', '南昌市');
INSERT INTO `university` VALUES ('2602', '东华理工大学长江学院', '14', '本科', 'http://ytc.ecit.edu.cn/', 'ecit', '抚州市');
INSERT INTO `university` VALUES ('2603', '南昌航空大学科技学院', '14', '本科', 'http://www.nckjxy.cn/', 'nckjxy', '南昌市');
INSERT INTO `university` VALUES ('2604', '江西理工大学应用科学学院', '14', '本科', 'http://www.asc.jx.cn/', 'asc', '赣州市');
INSERT INTO `university` VALUES ('2605', '景德镇陶瓷大学科技艺术学院', '14', '本科', 'http://www.jci-ky.cn/', 'jci-ky', '景德镇市');
INSERT INTO `university` VALUES ('2606', '江西农业大学南昌商学院', '14', '本科', 'http://www.ncsxy.com/', 'ncsxy', '南昌市');
INSERT INTO `university` VALUES ('2607', '江西中医药大学科技学院', '14', '本科', 'http://www.jxtcmstc.com/', 'jxtcmstc', '南昌市');
INSERT INTO `university` VALUES ('2608', '江西师范大学科学技术学院', '14', '本科', 'http://kjxy.jxnu.edu.cn/', 'jxnu', '南昌市');
INSERT INTO `university` VALUES ('2609', '赣南师范大学科技学院', '14', '本科', 'http://www.gnsyky.cn/', 'gnsyky', '赣州市');
INSERT INTO `university` VALUES ('2610', '江西科技师范大学理工学院', '14', '本科', 'http://www.jxstnupi.cn/', 'jxstnupi', '南昌市');
INSERT INTO `university` VALUES ('2611', '江西财经大学现代经济管理学院', '14', '本科', 'http://xjg.jxufe.cn/', 'jxufe', '南昌市');
INSERT INTO `university` VALUES ('2612', '豫章师范学院', '14', '本科', 'http://www.nctc.com.cn/', 'nctc', '南昌市');
INSERT INTO `university` VALUES ('2613', '南昌师范学院', '14', '本科', 'http:/www.jxie.edu.cn/', 'jxie', '南昌市');
INSERT INTO `university` VALUES ('2614', '上饶幼儿师范高等专科学校', '14', '专科', 'http://www.srsf.cn/', 'srsf', '上饶市');
INSERT INTO `university` VALUES ('2615', '抚州幼儿师范高等专科学校', '14', '专科', 'http://www.fzpec.cn/', 'fzpec', '抚州市');
INSERT INTO `university` VALUES ('2616', '江西工业职业技术学院', '14', '专科', 'http://www.jxgzy.cn/', 'jxgzy', '南昌市');
INSERT INTO `university` VALUES ('2617', '江西医学高等专科学校', '14', '专科', 'http://www.sryx.cn/', 'sryx', '上饶市');
INSERT INTO `university` VALUES ('2618', '九江职业大学', '14', '专科', 'http://www.jjvu.jx.cn/', 'jjvu', '九江市');
INSERT INTO `university` VALUES ('2619', '九江职业技术学院', '14', '专科', 'http://www.jvtc.jx.cn/', 'jvtc', '九江市');
INSERT INTO `university` VALUES ('2620', '江西司法警官职业学院', '14', '专科', 'http://218.65.115.100/', '65', '南昌市');
INSERT INTO `university` VALUES ('2621', '江西陶瓷工艺美术职业技术学院', '14', '专科', 'http://www.jxgymy.com/', 'jxgymy', '景德镇市');
INSERT INTO `university` VALUES ('2622', '江西旅游商贸职业学院', '14', '专科', 'http://www.jxlsxy.com/', 'jxlsxy', '南昌市');
INSERT INTO `university` VALUES ('2623', '江西电力职业技术学院', '14', '专科', 'http://www.dlzy.jx.sgcc.com.cn', 'dlzy', '南昌市');
INSERT INTO `university` VALUES ('2624', '江西环境工程职业学院', '14', '专科', 'http://www.jxhjxy.com/', 'jxhjxy', '赣州市');
INSERT INTO `university` VALUES ('2625', '江西艺术职业学院', '14', '专科', 'http://www.jxysedu.com/', 'jxysedu', '南昌市');
INSERT INTO `university` VALUES ('2626', '鹰潭职业技术学院', '14', '专科', 'http://www.jxytxy.com.cn/', 'jxytxy', '鹰潭市');
INSERT INTO `university` VALUES ('2627', '江西信息应用职业技术学院', '14', '专科', 'http://www.jxcia.com/', 'jxcia', '南昌市');
INSERT INTO `university` VALUES ('2628', '江西交通职业技术学院', '14', '专科', 'http://www.jxjtxy.com/', 'jxjtxy', '南昌市');
INSERT INTO `university` VALUES ('2629', '江西财经职业学院', '14', '专科', 'http://www.jxvc.jx.cn/', 'jxvc', '九江市');
INSERT INTO `university` VALUES ('2630', '江西应用工程职业学院', '14', '专科', 'http://www.jxatei.net/', 'jxatei', '萍乡市');
INSERT INTO `university` VALUES ('2631', '江西现代职业技术学院', '14', '专科', 'http://www.jxxdxy.com/', 'jxxdxy', '南昌市');
INSERT INTO `university` VALUES ('2632', '江西工业工程职业技术学院', '14', '专科', 'http://www.jxgcxy.net/', 'jxgcxy', '萍乡市');
INSERT INTO `university` VALUES ('2633', '江西机电职业技术学院', '14', '专科', 'http://www.jxjdxy.com/', 'jxjdxy', '南昌市');
INSERT INTO `university` VALUES ('2634', '江西科技职业学院', '14', '专科', 'http://www.jxkeda.com/', 'jxkeda', '南昌市');
INSERT INTO `university` VALUES ('2635', '南昌职业学院', '14', '专科', 'http://www.nczyxy.com/', 'nczyxy', '南昌市');
INSERT INTO `university` VALUES ('2636', '江西外语外贸职业学院', '14', '专科', 'http://www.jxcfs.com/', 'jxcfs', '南昌市');
INSERT INTO `university` VALUES ('2637', '江西工业贸易职业技术学院', '14', '专科', 'http://www.jxgmxy.com/', 'jxgmxy', '南昌市');
INSERT INTO `university` VALUES ('2638', '宜春职业技术学院', '14', '专科', 'http://www.ycvc.jx.cn/', 'ycvc', '宜春市');
INSERT INTO `university` VALUES ('2639', '江西应用技术职业学院', '14', '专科', 'http://www.jxyyxy.com/', 'jxyyxy', '赣州市');
INSERT INTO `university` VALUES ('2640', '江西生物科技职业学院', '14', '专科', 'http://www.jxswkj.com/', 'jxswkj', '南昌市');
INSERT INTO `university` VALUES ('2641', '江西建设职业技术学院', '14', '专科', 'http://www.jxjsxy.com/', 'jxjsxy', '南昌市');
INSERT INTO `university` VALUES ('2642', '抚州职业技术学院', '14', '专科', 'http://www.fzjsxy.com/', 'fzjsxy', '抚州市');
INSERT INTO `university` VALUES ('2643', '江西中医药高等专科学校', '14', '专科', 'http://www.jxtcms.net/', 'jxtcms', '抚州市');
INSERT INTO `university` VALUES ('2644', '江西先锋软件职业技术学院', '14', '专科', 'http://www.aheadedu.com/', 'aheadedu', '南昌市');
INSERT INTO `university` VALUES ('2645', '江西经济管理职业学院', '14', '专科', 'http://www.jiea.cn/', 'jiea', '南昌市');
INSERT INTO `university` VALUES ('2646', '江西制造职业技术学院', '14', '专科', 'http://www.jxmtc.com/', 'jxmtc', '南昌市');
INSERT INTO `university` VALUES ('2647', '江西工程职业学院', '14', '专科', 'http://www.jxgcxy.com/', 'jxgcxy', '南昌市');
INSERT INTO `university` VALUES ('2648', '江西青年职业学院', '14', '专科', 'http://www.jxqy.com.cn/', 'jxqy', '南昌市');
INSERT INTO `university` VALUES ('2649', '上饶职业技术学院', '14', '专科', 'http://www.srzy.cn/', 'srzy', '上饶市');
INSERT INTO `university` VALUES ('2650', '江西航空职业技术学院', '14', '专科', 'http://www.jhxy.com.cn/', 'jhxy', '南昌市');
INSERT INTO `university` VALUES ('2651', '江西农业工程职业学院', '14', '专科', 'http://www.jxaevc.gov.cn/', 'jxaevc', '樟树市');
INSERT INTO `university` VALUES ('2652', '赣西科技职业学院', '14', '专科', 'http://www.ganxidx.com/', 'ganxidx', '新余市');
INSERT INTO `university` VALUES ('2653', '江西卫生职业学院', '14', '专科', 'http://www.jxhlxy.com.cn/', 'jxhlxy', '南昌市');
INSERT INTO `university` VALUES ('2654', '江西新能源科技职业学院', '14', '专科', 'http://www.tynxy.com/', 'tynxy', '新余市');
INSERT INTO `university` VALUES ('2655', '江西枫林涉外经贸职业学院', '14', '专科', 'http://www.fenglin.org/', 'fenglin', '九江市');
INSERT INTO `university` VALUES ('2656', '江西泰豪动漫职业学院', '14', '专科', 'http://www.tellhowedu.com/', 'tellhowedu', '南昌市');
INSERT INTO `university` VALUES ('2657', '江西冶金职业技术学院', '14', '专科', 'http://www.jxyjxy.com/', 'jxyjxy', '新余市');
INSERT INTO `university` VALUES ('2658', '江西传媒职业学院', '14', '专科', 'http://www.jxcb.com/', 'jxcb', '南昌市');
INSERT INTO `university` VALUES ('2659', '江西工商职业技术学院', '14', '专科', 'http://www.jxgsxy.cn/', 'jxgsxy', '南昌市');
INSERT INTO `university` VALUES ('2660', '景德镇陶瓷职业技术学院', '14', '专科', 'http://www.jcivt.com/', 'jcivt', '景德镇市');
INSERT INTO `university` VALUES ('2661', '共青科技职业学院', '14', '专科', 'http://www.gqkj.com.cn/', 'gqkj', '共青城');
INSERT INTO `university` VALUES ('2662', '赣州师范高等专科学校', '14', '专科', 'http://www.gnjyxy.com/', 'gnjyxy', '赣州市');
INSERT INTO `university` VALUES ('2663', '江西水利职业学院', '14', '专科', 'http://www.jxslsd.com/', 'jxslsd', '南昌市');
INSERT INTO `university` VALUES ('2664', '宜春幼儿师范高等专科学校', '14', '专科', 'http://www.gacycu.cn/', 'gacycu', '宜春市');
INSERT INTO `university` VALUES ('2665', '吉安职业技术学院', '14', '专科', 'http://www.japt.com.cn/', 'japt', '吉安市');
INSERT INTO `university` VALUES ('2666', '江西洪州职业学院', '14', '专科', 'http://www.jxhzzyxy.com/', 'jxhzzyxy', '宜春市');
INSERT INTO `university` VALUES ('2667', '江西师范高等专科学校', '14', '专科', 'http://www.jxsfgz.com/', 'jxsfgz', '鹰潭市');
INSERT INTO `university` VALUES ('2668', '南昌影视传播职业学院', '14', '专科', 'http://www.ncyscb.com/', 'ncyscb', '南昌市');
INSERT INTO `university` VALUES ('2669', '山东大学', '15', '本科', 'http://www.sdu.edu.cn/', 'sdu', '济南市');
INSERT INTO `university` VALUES ('2670', '中国海洋大学', '15', '本科', 'http://www.ouc.edu.cn/', 'ouc', '青岛市');
INSERT INTO `university` VALUES ('2671', '山东科技大学', '15', '本科', 'http://www.sdust.edu.cn/', 'sdust', '青岛市');
INSERT INTO `university` VALUES ('2672', '中国石油大学（华东）', '15', '本科', 'http://www.upc.edu.cn/', 'upc', '青岛市');
INSERT INTO `university` VALUES ('2673', '青岛科技大学', '15', '本科', 'http://www.qust.edu.cn/', 'qust', '青岛市');
INSERT INTO `university` VALUES ('2674', '济南大学', '15', '本科', 'http://www.ujn.edu.cn/', 'ujn', '济南市');
INSERT INTO `university` VALUES ('2675', '青岛理工大学', '15', '本科', 'http://www.qtech.edu.cn/', 'qtech', '青岛市');
INSERT INTO `university` VALUES ('2676', '山东建筑大学', '15', '本科', 'http://www.sdjzu.edu.cn/', 'sdjzu', '济南市');
INSERT INTO `university` VALUES ('2677', '齐鲁工业大学', '15', '本科', 'http://www.sdili.edu.cn/', 'sdili', '济南市');
INSERT INTO `university` VALUES ('2678', '山东理工大学', '15', '本科', 'http://www.sdut.edu.cn/', 'sdut', '淄博市');
INSERT INTO `university` VALUES ('2679', '山东农业大学', '15', '本科', 'http://www.sdau.edu.cn/', 'sdau', '泰安市');
INSERT INTO `university` VALUES ('2680', '青岛农业大学', '15', '本科', 'http://www.qau.edu.cn/', 'qau', '青岛市');
INSERT INTO `university` VALUES ('2681', '潍坊医学院', '15', '本科', 'http://www.wfmc.edu.cn/', 'wfmc', '潍坊市');
INSERT INTO `university` VALUES ('2682', '泰山医学院', '15', '本科', 'http://www.tsmc.edu.cn/', 'tsmc', '泰安市');
INSERT INTO `university` VALUES ('2683', '滨州医学院', '15', '本科', 'http://www.bzmc.edu.cn/', 'bzmc', '滨州市');
INSERT INTO `university` VALUES ('2684', '山东中医药大学', '15', '本科', 'http://www.sdutcm.edu.cn/', 'sdutcm', '济南市');
INSERT INTO `university` VALUES ('2685', '济宁医学院', '15', '本科', 'http://www.jnmc.edu.cn/', 'jnmc', '济宁市');
INSERT INTO `university` VALUES ('2686', '山东师范大学', '15', '本科', 'http://www.sdnu.edu.cn/', 'sdnu', '济南市');
INSERT INTO `university` VALUES ('2687', '曲阜师范大学', '15', '本科', 'http://www.qfnu.edu.cn/', 'qfnu', '曲阜市');
INSERT INTO `university` VALUES ('2688', '聊城大学', '15', '本科', 'http://www.lcu.edu.cn/', 'lcu', '聊城市');
INSERT INTO `university` VALUES ('2689', '德州学院', '15', '本科', 'http://www.dzu.edu.cn/', 'dzu', '德州市');
INSERT INTO `university` VALUES ('2690', '滨州学院', '15', '本科', 'http://www.bzu.edu.cn/', 'bzu', '滨州市');
INSERT INTO `university` VALUES ('2691', '鲁东大学', '15', '本科', 'http://www.ldu.edu.cn/', 'ldu', '烟台市');
INSERT INTO `university` VALUES ('2692', '临沂大学', '15', '本科', 'http://www.lyu.edu.cn/', 'lyu', '临沂市');
INSERT INTO `university` VALUES ('2693', '泰山学院', '15', '本科', 'http://www.tsu.edu.cn/', 'tsu', '泰安市');
INSERT INTO `university` VALUES ('2694', '济宁学院', '15', '本科', 'http://www.jnxy.edu.cn/', 'jnxy', '济宁市');
INSERT INTO `university` VALUES ('2695', '菏泽学院', '15', '本科', 'http://www.hezeu.edu.cn/', 'hezeu', '菏泽市');
INSERT INTO `university` VALUES ('2696', '山东财经大学', '15', '本科', 'http://www.sdufe.edu.cn/', 'sdufe', '济南市');
INSERT INTO `university` VALUES ('2697', '山东体育学院', '15', '本科', 'http://www.sdpei.edu.cn/', 'sdpei', '济南市');
INSERT INTO `university` VALUES ('2698', '山东艺术学院', '15', '本科', 'http://www.sdca.edu.cn/', 'sdca', '济南市');
INSERT INTO `university` VALUES ('2699', '齐鲁医药学院', '15', '本科', 'http://www.wjmu.net/', 'wjmu', '淄博市');
INSERT INTO `university` VALUES ('2700', '青岛滨海学院', '15', '本科', 'http://www.binhaicollege.com/', 'binhaicoll', '青岛市');
INSERT INTO `university` VALUES ('2701', '枣庄学院', '15', '本科', 'http://www.uzz.edu.cn/', 'uzz', '枣庄市');
INSERT INTO `university` VALUES ('2702', '山东工艺美术学院', '15', '本科', 'http://www.sdada.edu.cn/', 'sdada', '济南市');
INSERT INTO `university` VALUES ('2703', '青岛大学', '15', '本科', 'http://www.qdu.edu.cn/', 'qdu', '青岛市');
INSERT INTO `university` VALUES ('2704', '烟台大学', '15', '本科', 'http://www.ytu.edu.cn/', 'ytu', '烟台市');
INSERT INTO `university` VALUES ('2705', '潍坊学院', '15', '本科', 'http://www.wfu.edu.cn/', 'wfu', '潍坊市');
INSERT INTO `university` VALUES ('2706', '山东警察学院', '15', '本科', 'http://www.sdpc.edu.cn/', 'sdpc', '济南市');
INSERT INTO `university` VALUES ('2707', '山东交通学院', '15', '本科', 'http://www.sdjtu.edu.cn/', 'sdjtu', '济南市');
INSERT INTO `university` VALUES ('2708', '山东工商学院', '15', '本科', 'http://www.sdibt.edu.cn/', 'sdibt', '烟台市');
INSERT INTO `university` VALUES ('2709', '山东女子学院', '15', '本科', 'http://www.sdwu.edu.cn/', 'sdwu', '济南市');
INSERT INTO `university` VALUES ('2710', '烟台南山学院', '15', '本科', 'http://www.nanshan.edu.cn/', 'nanshan', '烟台市');
INSERT INTO `university` VALUES ('2711', '潍坊科技学院', '15', '本科', 'http://www.wfkjxy.com.cn/', 'wfkjxy', '潍坊市');
INSERT INTO `university` VALUES ('2712', '山东英才学院', '15', '本科', 'http://www.ycxy.com/', 'ycxy', '济南市');
INSERT INTO `university` VALUES ('2713', '青岛恒星科技学院', '15', '本科', 'http://www.hx.cn/', 'hx', '青岛市');
INSERT INTO `university` VALUES ('2714', '青岛黄海学院', '15', '本科', 'http://www.huanghaicollege.com', 'huanghaico', '青岛市');
INSERT INTO `university` VALUES ('2715', '山东现代学院', '15', '本科', 'http://www.uxd.com.cn/', 'uxd', '济南市');
INSERT INTO `university` VALUES ('2716', '山东协和学院', '15', '本科', 'http://www.sdxiehe.com/', 'sdxiehe', '济南市');
INSERT INTO `university` VALUES ('2717', '烟台大学文经学院', '15', '本科', 'http://wenjing.ytu.edu.cn/', 'ytu', '烟台市');
INSERT INTO `university` VALUES ('2718', '聊城大学东昌学院', '15', '本科', 'http://www.lcudc.cn/', 'lcudc', '聊城市');
INSERT INTO `university` VALUES ('2719', '青岛理工大学琴岛学院', '15', '本科', 'http://www.qdc.cn/', 'qdc', '青岛市');
INSERT INTO `university` VALUES ('2720', '山东师范大学历山学院', '15', '本科', 'http://www.lsxy.sdnu.edu.cn/', 'lsxy', '济南市');
INSERT INTO `university` VALUES ('2721', '山东经济学院燕山学院', '15', '本科', 'http://www.yanshanxueyuan.com/', 'yanshanxue', '济南市');
INSERT INTO `university` VALUES ('2722', '中国石油大学胜利学院', '15', '本科', 'http://www.slcupc.edu.cn/', 'slcupc', '东营市');
INSERT INTO `university` VALUES ('2723', '山东科技大学泰山科技学院', '15', '本科', 'http://tskjxy.sdust.edu.cn/', 'sdust', '泰安市');
INSERT INTO `university` VALUES ('2724', '山东华宇工学院', '15', '本科', 'http://www.sdhyxy.com/', 'sdhyxy', '德州市');
INSERT INTO `university` VALUES ('2725', '青岛工学院', '15', '本科', 'http://www.oucqdc.edu.cn/', 'oucqdc', '青岛市');
INSERT INTO `university` VALUES ('2726', '青岛农业大学海都学院', '15', '本科', 'http://www.hdxy.org/', 'hdxy', '莱阳市');
INSERT INTO `university` VALUES ('2727', '齐鲁理工学院', '15', '本科', 'http://www.qlit.edu.cn/', 'qlit', '曲阜市');
INSERT INTO `university` VALUES ('2728', '山东财经大学东方学院', '15', '本科', 'http://www.sdor.cn/', 'sdor', '泰安市');
INSERT INTO `university` VALUES ('2729', '济南大学泉城学院', '15', '本科', 'http://jdqy.ujn.edu.cn/', 'ujn', '蓬莱市');
INSERT INTO `university` VALUES ('2730', '山东政法学院', '15', '本科', 'http://www.sdupsl.edu.cn/', 'sdupsl', '济南市');
INSERT INTO `university` VALUES ('2731', '齐鲁师范学院', '15', '本科', 'http://www.qlnu.edu.cn/', 'qlnu', '济南市');
INSERT INTO `university` VALUES ('2732', '山东青年政治学院', '15', '本科', 'http://www.sdyu.edu.cn/', 'sdyu', '济南市');
INSERT INTO `university` VALUES ('2733', '北京电影学院现代创意媒体学院', '15', '本科', 'http://www.bfamcmc.edu.cn/', 'bfamcmc', '青岛市');
INSERT INTO `university` VALUES ('2734', '山东管理学院', '15', '本科', 'http://www.sdmu.edu.cn/', 'sdmu', '济南市');
INSERT INTO `university` VALUES ('2735', '山东农业工程学院', '15', '本科', 'http://www.sdngy.edu.cn/', 'sdngy', '济南市');
INSERT INTO `university` VALUES ('2736', '山东医学高等专科学校', '15', '专科', 'http://www.sdmc.net.cn/', 'sdmc', '临沂市');
INSERT INTO `university` VALUES ('2737', '菏泽医学专科学校', '15', '专科', 'http://www.hzmc.edu.cn/', 'hzmc', '菏泽市');
INSERT INTO `university` VALUES ('2738', '山东商业职业技术学院', '15', '专科', 'http://www.sict.edu.cn/', 'sict', '济南市');
INSERT INTO `university` VALUES ('2739', '山东电力高等专科学校', '15', '专科', 'http://www.sepc.edu.cn/', 'sepc', '济南市');
INSERT INTO `university` VALUES ('2740', '日照职业技术学院', '15', '专科', 'http://www.rzpt.cn/', 'rzpt', '日照市');
INSERT INTO `university` VALUES ('2741', '曲阜远东职业技术学院', '15', '专科', 'http://www.fareast-edu.net/', 'fareast-ed', '曲阜市');
INSERT INTO `university` VALUES ('2742', '青岛职业技术学院', '15', '专科', 'http://www.qtc.edu.cn/', 'qtc', '青岛市');
INSERT INTO `university` VALUES ('2743', '威海职业学院', '15', '专科', 'http://www.weihaicollege.com/', 'weihaicoll', '威海市');
INSERT INTO `university` VALUES ('2744', '山东职业学院', '15', '专科', 'http://www.sdp.edu.cn/', 'sdp', '济南市');
INSERT INTO `university` VALUES ('2745', '山东劳动职业技术学院', '15', '专科', 'http://www.sdlvtc.cn/', 'sdlvtc', '济南市');
INSERT INTO `university` VALUES ('2746', '莱芜职业技术学院', '15', '专科', 'http://www.lwvc.net/', 'lwvc', '莱芜市');
INSERT INTO `university` VALUES ('2747', '济宁职业技术学院', '15', '专科', 'http://www.jnzjxy.com.cn/', 'jnzjxy', '济宁市');
INSERT INTO `university` VALUES ('2748', '潍坊职业学院', '15', '专科', 'http://www.sdwfvc.com/', 'sdwfvc', '潍坊市');
INSERT INTO `university` VALUES ('2749', '烟台职业学院', '15', '专科', 'http://www.ytvc.com.cn/', 'ytvc', '烟台市');
INSERT INTO `university` VALUES ('2750', '东营职业学院', '15', '专科', 'http://www.dyxy.net/', 'dyxy', '东营市');
INSERT INTO `university` VALUES ('2751', '聊城职业技术学院', '15', '专科', 'http://www.lctvu.sd.cn/', 'lctvu', '聊城市');
INSERT INTO `university` VALUES ('2752', '滨州职业学院', '15', '专科', 'http://www.edubzvc.com.cn/', 'edubzvc', '滨州市');
INSERT INTO `university` VALUES ('2753', '山东科技职业学院', '15', '专科', 'http://www.sdzy.com.cn/', 'sdzy', '潍坊市');
INSERT INTO `university` VALUES ('2754', '山东服装职业学院', '15', '专科', 'http://www.svict.com/', 'svict', '泰安市');
INSERT INTO `university` VALUES ('2755', '德州科技职业学院', '15', '专科', 'http://www.sddzkj.com/', 'sddzkj', '德州市');
INSERT INTO `university` VALUES ('2756', '山东力明科技职业学院', '15', '专科', 'http://www.6789.com.cn', '6789', '济南市');
INSERT INTO `university` VALUES ('2757', '山东圣翰财贸职业学院', '15', '专科', 'http://www.suu.com.cn/', 'suu', '济南市');
INSERT INTO `university` VALUES ('2758', '山东水利职业学院', '15', '专科', 'http://www.sdwrp.com/', 'sdwrp', '日照市');
INSERT INTO `university` VALUES ('2759', '山东畜牧兽医职业学院', '15', '专科', 'http://www.sdmyxy.cn/', 'sdmyxy', '潍坊市');
INSERT INTO `university` VALUES ('2760', '青岛飞洋职业技术学院', '15', '专科', 'http://www.feiyangcollege.com/', 'feiyangcol', '青岛市');
INSERT INTO `university` VALUES ('2761', '东营科技职业学院', '15', '专科', 'http://www.dycollege.net/', 'dycollege', '东营市');
INSERT INTO `university` VALUES ('2762', '山东交通职业学院', '15', '专科', 'http://www.sdjtzyxy.com/', 'sdjtzyxy', '潍坊市');
INSERT INTO `university` VALUES ('2763', '淄博职业学院', '15', '专科', 'http://www.zbvc.cn/', 'zbvc', '淄博市');
INSERT INTO `university` VALUES ('2764', '山东外贸职业学院', '15', '专科', 'http://www.sdftcollege.com.cn/', 'sdftcolleg', '青岛市');
INSERT INTO `university` VALUES ('2765', '青岛酒店管理职业技术学院', '15', '专科', 'http://www.qchm.edu.cn/', 'qchm', '青岛市');
INSERT INTO `university` VALUES ('2766', '山东信息职业技术学院', '15', '专科', 'http://www.sdcit.cn/', 'sdcit', '潍坊市');
INSERT INTO `university` VALUES ('2767', '青岛港湾职业技术学院', '15', '专科', 'http://www.qdgw.com/', 'qdgw', '青岛市');
INSERT INTO `university` VALUES ('2768', '山东胜利职业学院', '15', '专科', 'http://www.sdslvc.com/', 'sdslvc', '东营市');
INSERT INTO `university` VALUES ('2769', '山东经贸职业学院', '15', '专科', 'http://www.sdecu.com/', 'sdecu', '潍坊市');
INSERT INTO `university` VALUES ('2770', '山东工业职业学院', '15', '专科', 'http://www.sdivc.net.cn/', 'sdivc', '淄博市');
INSERT INTO `university` VALUES ('2771', '山东化工职业学院', '15', '专科', 'http://www.qledu.net/', 'qledu', '淄博市');
INSERT INTO `university` VALUES ('2772', '青岛求实职业技术学院', '15', '专科', 'http://www.qdqs.com/', 'qdqs', '青岛市');
INSERT INTO `university` VALUES ('2773', '济南职业学院', '15', '专科', 'http://www.jnvc.edu.cn/', 'jnvc', '济南市');
INSERT INTO `university` VALUES ('2774', '烟台工程职业技术学院', '15', '专科', 'http://www.ytetc.edu.cn/', 'ytetc', '烟台市');
INSERT INTO `university` VALUES ('2775', '山东凯文科技职业学院', '15', '专科', 'http://www.sdkevin.cn/', 'sdkevin', '济南市');
INSERT INTO `university` VALUES ('2776', '山东外国语职业学院', '15', '专科', 'http://www.sdflc.com/', 'sdflc', '日照市');
INSERT INTO `university` VALUES ('2777', '潍坊工商职业学院', '15', '专科', 'http://www.wfgsxy.com/', 'wfgsxy', '潍坊市');
INSERT INTO `university` VALUES ('2778', '德州职业技术学院', '15', '专科', 'http://www.dzvtc.cn/', 'dzvtc', '德州市');
INSERT INTO `university` VALUES ('2779', '枣庄科技职业学院', '15', '专科', 'http://www.zzkjxy.com/', 'zzkjxy', '枣庄市');
INSERT INTO `university` VALUES ('2780', '淄博师范高等专科学校', '15', '专科', 'http://www.zbnc.edu.cn/', 'zbnc', '淄博市');
INSERT INTO `university` VALUES ('2781', '山东中医药高等专科学校', '15', '专科', 'http://www.stcmchina.com/', 'stcmchina', '烟台市');
INSERT INTO `university` VALUES ('2782', '济南工程职业技术学院', '15', '专科', 'http://www.jngcxy.com/', 'jngcxy', '济南市');
INSERT INTO `university` VALUES ('2783', '山东电子职业技术学院', '15', '专科', 'http://www.sdcet.cn/', 'sdcet', '济南市');
INSERT INTO `university` VALUES ('2784', '山东旅游职业学院', '15', '专科', 'http://www.sdts.net.cn/', 'sdts', '济南市');
INSERT INTO `university` VALUES ('2785', '山东铝业职业学院', '15', '专科', 'http://www.shlzhj.net/', 'shlzhj', '淄博市');
INSERT INTO `university` VALUES ('2786', '山东杏林科技职业学院', '15', '专科', 'http://www.sdxlxy.com/', 'sdxlxy', '济南市');
INSERT INTO `university` VALUES ('2787', '泰山职业技术学院', '15', '专科', 'http://www.mtotc.com.cn/', 'mtotc', '泰安市');
INSERT INTO `university` VALUES ('2788', '山东外事翻译职业学院', '15', '专科', 'http://www.wsfy.cn/', 'wsfy', '威海市');
INSERT INTO `university` VALUES ('2789', '山东药品食品职业学院', '15', '专科', 'http://www.sddfvc.cn/', 'sddfvc', '威海市');
INSERT INTO `university` VALUES ('2790', '山东商务职业学院', '15', '专科', 'http://www.sdbi.com.cn/', 'sdbi', '烟台市');
INSERT INTO `university` VALUES ('2791', '山东轻工职业学院', '15', '专科', 'http://www.silkedu.com/', 'silkedu', '淄博市');
INSERT INTO `university` VALUES ('2792', '山东城市建设职业学院', '15', '专科', 'http://www.sdcjxy.com/', 'sdcjxy', '济南市');
INSERT INTO `university` VALUES ('2793', '烟台汽车工程职业学院', '15', '专科', 'http://www.ytqcvc.cn/', 'ytqcvc', '烟台市');
INSERT INTO `university` VALUES ('2794', '山东司法警官职业学院', '15', '专科', 'http://www.sdsfjy.com/', 'sdsfjy', '济南市');
INSERT INTO `university` VALUES ('2795', '菏泽家政职业学院', '15', '专科', 'http://www.hzjzxy.com/', 'hzjzxy', '单县');
INSERT INTO `university` VALUES ('2796', '山东传媒职业学院', '15', '专科', 'http://www.sdcmc.net/', 'sdcmc', '济南市');
INSERT INTO `university` VALUES ('2797', '临沂职业学院', '15', '专科', 'http://www.lyzyxy.com/', 'lyzyxy', '临沂市');
INSERT INTO `university` VALUES ('2798', '枣庄职业学院', '15', '专科', 'http://www.sdzzvc.cn/', 'sdzzvc', '枣庄市');
INSERT INTO `university` VALUES ('2799', '山东理工职业学院', '15', '专科', 'http://www.sdlgzy.com/', 'sdlgzy', '济宁市');
INSERT INTO `university` VALUES ('2800', '山东文化产业职业学院', '15', '专科', 'http://www.sdcivc.com/', 'sdcivc', '烟台市');
INSERT INTO `university` VALUES ('2801', '青岛远洋船员职业学院', '15', '专科', 'http://www.coscoqmc.com.cn/', 'coscoqmc', '青岛市');
INSERT INTO `university` VALUES ('2802', '济南幼儿师范高等专科学校', '15', '专科', 'http://www.jnygz.com/', 'jnygz', '济南市');
INSERT INTO `university` VALUES ('2803', '济南护理职业学院', '15', '专科', 'http://www.sdjnwx.com/', 'sdjnwx', '济南市');
INSERT INTO `university` VALUES ('2804', '泰山护理职业学院', '15', '专科', 'http://www.tawx.com.cn/', 'tawx', '泰安市');
INSERT INTO `university` VALUES ('2805', '山东海事职业学院', '15', '专科', 'http://www.sdm.net.cn/', 'sdm', '潍坊市');
INSERT INTO `university` VALUES ('2806', '潍坊护理职业学院', '15', '专科', 'http://www.wfhlxy.com/', 'wfhlxy', '潍坊市');
INSERT INTO `university` VALUES ('2807', '潍坊工程职业学院', '15', '专科', 'http://www.wfec.cn/', 'wfec', '潍坊市');
INSERT INTO `university` VALUES ('2808', '菏泽职业学院', '15', '专科', 'http://www.hzzyxy.com/', 'hzzyxy', '菏泽市');
INSERT INTO `university` VALUES ('2809', '山东艺术设计职业学院', '15', '专科', 'http://www.sysy.com.cn/', 'sysy', '济南市');
INSERT INTO `university` VALUES ('2810', '威海海洋职业学院', '15', '专科', 'http://www.whovc.cn/', 'whovc', '威海市');
INSERT INTO `university` VALUES ('2811', '山东特殊教育职业学院', '15', '专科', 'http://www.sdse.cn/', 'sdse', '济南市');
INSERT INTO `university` VALUES ('2812', '烟台黄金职业学院', '15', '专科', 'http://www.ytgoldcollege.com/', 'ytgoldcoll', '烟台市');
INSERT INTO `university` VALUES ('2813', '日照航海工程职业学院', '15', '专科', 'http://www.rzmevc.com/', 'rzmevc', '日照市');
INSERT INTO `university` VALUES ('2814', '华北水利水电大学', '16', '本科', 'http://www.ncwu.edu.cn/', 'ncwu', '郑州市');
INSERT INTO `university` VALUES ('2815', '郑州大学', '16', '本科', 'http://www.zzu.edu.cn/', 'zzu', '郑州市');
INSERT INTO `university` VALUES ('2816', '河南理工大学', '16', '本科', 'http://www.hpu.edu.cn/', 'hpu', '焦作市');
INSERT INTO `university` VALUES ('2817', '郑州轻工业学院', '16', '本科', 'http://www.zzili.edu.cn/', 'zzili', '郑州市');
INSERT INTO `university` VALUES ('2818', '河南工业大学', '16', '本科', 'http://www.haut.edu.cn/', 'haut', '郑州市');
INSERT INTO `university` VALUES ('2819', '河南科技大学', '16', '本科', 'http://www.haust.edu.cn/', 'haust', '洛阳市');
INSERT INTO `university` VALUES ('2820', '中原工学院', '16', '本科', 'http://www.zzti.edu.cn/', 'zzti', '郑州市');
INSERT INTO `university` VALUES ('2821', '河南农业大学', '16', '本科', 'http://www.henau.edu.cn/', 'henau', '郑州市');
INSERT INTO `university` VALUES ('2822', '河南科技学院', '16', '本科', 'http://www.hist.edu.cn/', 'hist', '新乡市');
INSERT INTO `university` VALUES ('2823', '河南牧业经济学院', '16', '本科', 'http://www.habc.edu.cn/', 'habc', '郑州市');
INSERT INTO `university` VALUES ('2824', '河南中医药大学', '16', '本科', 'http://www.hactcm.edu.cn/', 'hactcm', '郑州市');
INSERT INTO `university` VALUES ('2825', '新乡医学院', '16', '本科', 'http://www.xxmu.edu.cn/', 'xxmu', '新乡市');
INSERT INTO `university` VALUES ('2826', '河南大学', '16', '本科', 'http://www.henu.edu.cn/', 'henu', '开封市');
INSERT INTO `university` VALUES ('2827', '河南师范大学', '16', '本科', 'http://www.henannu.edu.cn/', 'henannu', '新乡市');
INSERT INTO `university` VALUES ('2828', '信阳师范学院', '16', '本科', 'http://www.xytc.edu.cn/', 'xytc', '信阳市');
INSERT INTO `university` VALUES ('2829', '周口师范学院', '16', '本科', 'http://www.zknu.edu.cn/', 'zknu', '周口市');
INSERT INTO `university` VALUES ('2830', '安阳师范学院', '16', '本科', 'http://www.aynu.edu.cn/', 'aynu', '安阳市');
INSERT INTO `university` VALUES ('2831', '许昌学院', '16', '本科', 'http://www.xcu.edu.cn/', 'xcu', '许昌市');
INSERT INTO `university` VALUES ('2832', '南阳师范学院', '16', '本科', 'http://www.nynu.edu.cn/', 'nynu', '南阳市');
INSERT INTO `university` VALUES ('2833', '洛阳师范学院', '16', '本科', 'http://www.lynu.cn/', 'lynu', '洛阳市');
INSERT INTO `university` VALUES ('2834', '商丘师范学院', '16', '本科', 'http://www.sqnc.edu.cn/', 'sqnc', '商丘市');
INSERT INTO `university` VALUES ('2835', '河南财经政法大学', '16', '本科', 'http://www.huel.edu.cn/', 'huel', '郑州市');
INSERT INTO `university` VALUES ('2836', '郑州航空工业管理学院', '16', '本科', 'http://www.zzia.edu.cn/', 'zzia', '郑州市');
INSERT INTO `university` VALUES ('2837', '黄淮学院', '16', '本科', 'http://www.huanghuai.edu.cn/', 'huanghuai', '驻马店');
INSERT INTO `university` VALUES ('2838', '平顶山学院', '16', '本科', 'http://www.pdsu.edu.cn/', 'pdsu', '平顶山');
INSERT INTO `university` VALUES ('2839', '郑州工程技术学院', '16', '本科', 'http://www.zhzhu.edu.cn/', 'zhzhu', '郑州市');
INSERT INTO `university` VALUES ('2840', '洛阳理工学院', '16', '本科', 'http://www.lit.edu.cn/', 'lit', '洛阳市');
INSERT INTO `university` VALUES ('2841', '新乡学院', '16', '本科', 'http://www.xxu.edu.cn/', 'xxu', '新乡市');
INSERT INTO `university` VALUES ('2842', '信阳农林学院', '16', '本科', 'http://www.xyac.edu.cn/', 'xyac', '信阳市');
INSERT INTO `university` VALUES ('2843', '河南工学院', '16', '本科', 'http://www.hneeu.edu.cn/', 'hneeu', '新乡市');
INSERT INTO `university` VALUES ('2844', '安阳工学院', '16', '本科', 'http://www.ayit.edu.cn/', 'ayit', '安阳市');
INSERT INTO `university` VALUES ('2845', '河南工程学院', '16', '本科', 'http://www.haue.edu.cn/', 'haue', '郑州市');
INSERT INTO `university` VALUES ('2846', '河南财政金融学院', '16', '本科', 'http://www.hacz.edu.cn/', 'hacz', '郑州市');
INSERT INTO `university` VALUES ('2847', '南阳理工学院', '16', '本科', 'http://www.nyist.net/', 'nyist', '南阳市');
INSERT INTO `university` VALUES ('2848', '河南城建学院', '16', '本科', 'http://www.hncj.edu.cn/', 'hncj', '平顶山');
INSERT INTO `university` VALUES ('2849', '河南警察学院', '16', '本科', 'http://www.hngazk.edu.cn/', 'hngazk', '郑州市');
INSERT INTO `university` VALUES ('2850', '黄河科技学院', '16', '本科', 'http://www.hhstu.edu.cn/', 'hhstu', '郑州市');
INSERT INTO `university` VALUES ('2851', '铁道警察学院', '16', '本科', 'http://www.rpc.edu.cn/', 'rpc', '郑州市');
INSERT INTO `university` VALUES ('2852', '郑州科技学院', '16', '本科', 'http://www.zzist.net/', 'zzist', '郑州市');
INSERT INTO `university` VALUES ('2853', '郑州工业应用技术学院', '16', '本科', 'http://www.zzhxxy.com/', 'zzhxxy', '郑州市');
INSERT INTO `university` VALUES ('2854', '郑州师范学院', '16', '本科', 'http://www.zztc.com.cn/', 'zztc', '郑州市');
INSERT INTO `university` VALUES ('2855', '郑州财经学院', '16', '本科', 'http://www.zzjm.edu.cn/', 'zzjm', '郑州市');
INSERT INTO `university` VALUES ('2856', '黄河交通学院', '16', '本科', 'http://www.zjtu.edu.cn/', 'zjtu', '郑州市');
INSERT INTO `university` VALUES ('2857', '商丘工学院', '16', '本科', 'http://www.sstvc.com/', 'sstvc', '商丘市');
INSERT INTO `university` VALUES ('2858', '河南大学民生学院', '16', '本科', 'http://minsheng.henu.edu.cn/', 'henu', '开封市');
INSERT INTO `university` VALUES ('2859', '河南师范大学新联学院', '16', '本科', 'http://www.xlxy.edu.cn/', 'xlxy', '郑州市');
INSERT INTO `university` VALUES ('2860', '信阳学院', '16', '本科', 'http://www.hrxy.edu.cn/', 'hrxy', '信阳市');
INSERT INTO `university` VALUES ('2861', '安阳学院', '16', '本科', 'http://www.ayrwedu.cn/', 'ayrwedu', '安阳市');
INSERT INTO `university` VALUES ('2862', '新乡医学院三全学院', '16', '本科', 'http://www.sqmc.edu.cn/', 'sqmc', '新乡市');
INSERT INTO `university` VALUES ('2863', '河南科技学院新科学院', '16', '本科', 'http://xkxy.hist.edu.cn/', 'hist', '新乡市');
INSERT INTO `university` VALUES ('2864', '郑州工商学院', '16', '本科', 'http://www.wanfang.edu.cn/', 'wanfang', '焦作市');
INSERT INTO `university` VALUES ('2865', '中原工学院信息商务学院', '16', '本科', 'http://www.zcib.edu.cn/', 'zcib', '郑州市');
INSERT INTO `university` VALUES ('2866', '商丘学院', '16', '本科', 'http://www.hnhyedu.net/', 'hnhyedu', '商丘市');
INSERT INTO `university` VALUES ('2867', '郑州成功财经学院', '16', '本科', 'http://www.chenggong.edu.cn/', 'chenggong', '郑州市');
INSERT INTO `university` VALUES ('2868', '郑州升达经贸管理学院', '16', '本科', 'http://www.shengda.edu.cn/', 'shengda', '郑州市');
INSERT INTO `university` VALUES ('2869', '河南职业技术学院', '16', '专科', 'http://www.hnzj.ha.cn/', 'hnzj', '郑州市');
INSERT INTO `university` VALUES ('2870', '漯河职业技术学院', '16', '专科', 'http://www.lhvtc.edu.cn/', 'lhvtc', '漯河市');
INSERT INTO `university` VALUES ('2871', '三门峡职业技术学院', '16', '专科', 'http://www.smxpt.cn/', 'smxpt', '三门峡');
INSERT INTO `university` VALUES ('2872', '郑州铁路职业技术学院', '16', '专科', 'http://www.zzrvtc.com/', 'zzrvtc', '郑州市');
INSERT INTO `university` VALUES ('2873', '开封大学', '16', '专科', 'http://www.kfu.cn/', 'kfu', '开封市');
INSERT INTO `university` VALUES ('2874', '焦作大学', '16', '专科', 'http://www.jzu.edu.cn/', 'jzu', '焦作市');
INSERT INTO `university` VALUES ('2875', '濮阳职业技术学院', '16', '专科', 'http://www.pyvtc.cn/', 'pyvtc', '濮阳市');
INSERT INTO `university` VALUES ('2876', '郑州电力高等专科学校', '16', '专科', 'http://www.zepc.edu.cn/', 'zepc', '郑州市');
INSERT INTO `university` VALUES ('2877', '黄河水利职业技术学院', '16', '专科', 'http://www.yrcti.edu.cn/', 'yrcti', '开封市');
INSERT INTO `university` VALUES ('2878', '许昌职业技术学院', '16', '专科', 'http://www.xcitc.edu.cn/', 'xcitc', '许昌市');
INSERT INTO `university` VALUES ('2879', '河南工业和信息化职业学院', '16', '专科', 'http://www.hnets.edu.cn/', 'hnets', '焦作市');
INSERT INTO `university` VALUES ('2880', '河南水利与环境职业学院', '16', '专科', 'http://www.zzslxx.com/', 'zzslxx', '郑州市');
INSERT INTO `university` VALUES ('2881', '商丘职业技术学院', '16', '专科', 'http://www.sqzy.com.cn/', 'sqzy', '商丘市');
INSERT INTO `university` VALUES ('2882', '平顶山工业职业技术学院', '16', '专科', 'http://www.pzxy.edu.cn/', 'pzxy', '平顶山');
INSERT INTO `university` VALUES ('2883', '周口职业技术学院', '16', '专科', 'http://www.zkvtc.edu.cn/', 'zkvtc', '周口市');
INSERT INTO `university` VALUES ('2884', '济源职业技术学院', '16', '专科', 'http://www.jyvtc.com/', 'jyvtc', '济源市');
INSERT INTO `university` VALUES ('2885', '河南司法警官职业学院', '16', '专科', 'http://www.hnsfjg.com/', 'hnsfjg', '郑州市');
INSERT INTO `university` VALUES ('2886', '鹤壁职业技术学院', '16', '专科', 'http://www.hbzy.edu.cn/', 'hbzy', '鹤壁市');
INSERT INTO `university` VALUES ('2887', '河南工业职业技术学院', '16', '专科', 'http://www.hnpi.cn/', 'hnpi', '南阳市');
INSERT INTO `university` VALUES ('2888', '郑州澍青医学高等专科学校', '16', '专科', 'http://www.shuqing.org/', 'shuqing', '郑州市');
INSERT INTO `university` VALUES ('2889', '焦作师范高等专科学校', '16', '专科', 'http://www.jzsz.cn/', 'jzsz', '焦作市');
INSERT INTO `university` VALUES ('2890', '河南检察职业学院', '16', '专科', 'http://www.hnjc.org/', 'hnjc', '郑州市');
INSERT INTO `university` VALUES ('2891', '河南质量工程职业学院', '16', '专科', 'http://www.zlxy.cn/', 'zlxy', '平顶山');
INSERT INTO `university` VALUES ('2892', '郑州信息科技职业学院', '16', '专科', 'http://www.techcollege.cn/', 'techcolleg', '郑州市');
INSERT INTO `university` VALUES ('2893', '漯河医学高等专科学校', '16', '专科', 'http://www.lhmc.edu.cn/', 'lhmc', '漯河市');
INSERT INTO `university` VALUES ('2894', '南阳医学高等专科学校', '16', '专科', 'http://www.nymc.cn/', 'nymc', '南阳市');
INSERT INTO `university` VALUES ('2895', '商丘医学高等专科学校', '16', '专科', 'http://www.sqyx.edu.cn/', 'sqyx', '商丘市');
INSERT INTO `university` VALUES ('2896', '郑州电子信息职业技术学院', '16', '专科', 'http://www.zyfb.com/', 'zyfb', '郑州市');
INSERT INTO `university` VALUES ('2897', '信阳职业技术学院', '16', '专科', 'http://www.xyvtc.edu.cn/', 'xyvtc', '信阳市');
INSERT INTO `university` VALUES ('2898', '嵩山少林武术职业学院', '16', '专科', 'http://www.shaolinkungfu.edu.c', 'shaolinkun', '登封市');
INSERT INTO `university` VALUES ('2899', '郑州工业安全职业学院', '16', '专科', 'http://www.zazy.cn/', 'zazy', '郑州市');
INSERT INTO `university` VALUES ('2900', '永城职业学院', '16', '专科', 'http://www.yczyxy.com/', 'yczyxy', '永城市');
INSERT INTO `university` VALUES ('2901', '河南经贸职业学院', '16', '专科', 'http://www.hnjmxy.cn/', 'hnjmxy', '郑州市');
INSERT INTO `university` VALUES ('2902', '河南交通职业技术学院', '16', '专科', 'http://www.hncc.net/', 'hncc', '郑州市');
INSERT INTO `university` VALUES ('2903', '河南农业职业学院', '16', '专科', 'http://www.hnac.com.cn/', 'hnac', '郑州市');
INSERT INTO `university` VALUES ('2904', '郑州旅游职业学院', '16', '专科', 'http://www.zztrc.edu.cn/', 'zztrc', '郑州市');
INSERT INTO `university` VALUES ('2905', '郑州职业技术学院', '16', '专科', 'http://www.zzyedu.cn/', 'zzyedu', '郑州市');
INSERT INTO `university` VALUES ('2906', '河南信息统计职业学院', '16', '专科', 'http://www.hnisvc.com/', 'hnisvc', '郑州市');
INSERT INTO `university` VALUES ('2907', '河南林业职业学院', '16', '专科', 'http://www.hnfjc.cn/', 'hnfjc', '洛阳市');
INSERT INTO `university` VALUES ('2908', '河南工业贸易职业学院', '16', '专科', 'http://www.hngm.cn/', 'hngm', '郑州市');
INSERT INTO `university` VALUES ('2909', '郑州电力职业技术学院', '16', '专科', 'http://www.zzdl.com/', 'zzdl', '郑州市');
INSERT INTO `university` VALUES ('2910', '周口科技职业学院', '16', '专科', 'http://www.zkkjxy.net/', 'zkkjxy', '周口市');
INSERT INTO `university` VALUES ('2911', '河南建筑职业技术学院', '16', '专科', 'http://www.hnjs.com.cn/', 'hnjs', '郑州市');
INSERT INTO `university` VALUES ('2912', '漯河食品职业学院', '16', '专科', 'http://www.lsgx.com.cn/', 'lsgx', '漯河市');
INSERT INTO `university` VALUES ('2913', '郑州城市职业学院', '16', '专科', 'http://www.brenda.edu.cn/', 'brenda', '郑州市');
INSERT INTO `university` VALUES ('2914', '安阳职业技术学院', '16', '专科', 'http://jsxy.anyangedu.com/', 'anyangedu', '安阳市');
INSERT INTO `university` VALUES ('2915', '新乡职业技术学院', '16', '专科', 'http://www.xxvtc.com/', 'xxvtc', '新乡市');
INSERT INTO `university` VALUES ('2916', '驻马店职业技术学院', '16', '专科', 'http://www.zmdvtc.cn/', 'zmdvtc', '驻马店市');
INSERT INTO `university` VALUES ('2917', '焦作工贸职业学院', '16', '专科', 'http://www.jzgmxy.com/', 'jzgmxy', '焦作市');
INSERT INTO `university` VALUES ('2918', '许昌陶瓷职业学院', '16', '专科', 'http://www.xccvc.com/', 'xccvc', '许昌市');
INSERT INTO `university` VALUES ('2919', '郑州理工职业学院', '16', '专科', 'http://www.zzlgxy.net/', 'zzlgxy', '郑州市');
INSERT INTO `university` VALUES ('2920', '郑州信息工程职业学院', '16', '专科', 'http://www.zxxyedu.com/', 'zxxyedu', '郑州市');
INSERT INTO `university` VALUES ('2921', '长垣烹饪职业技术学院', '16', '专科', 'http://www.cyprxy.com/', 'cyprxy', '长垣市');
INSERT INTO `university` VALUES ('2922', '开封文化艺术职业学院', '16', '专科', 'http://www.kfvcca.com/', 'kfvcca', '开封市');
INSERT INTO `university` VALUES ('2923', '河南应用技术职业学院', '16', '专科', 'http://www.haict.edu.cn/', 'haict', '郑州市');
INSERT INTO `university` VALUES ('2924', '河南艺术职业学院', '16', '专科', 'http://www.hnyszyxy.net/', 'hnyszyxy', '郑州市');
INSERT INTO `university` VALUES ('2925', '河南机电职业学院', '16', '专科', 'http://www.hnjd.edu.cn/', 'hnjd', '郑州市');
INSERT INTO `university` VALUES ('2926', '河南护理职业学院', '16', '专科', 'http://www.hncedu.cn/', 'hncedu', '安阳市');
INSERT INTO `university` VALUES ('2927', '许昌电气职业学院', '16', '专科', 'http://www.xcevc.cn/', 'xcevc', '许昌市');
INSERT INTO `university` VALUES ('2928', '信阳涉外职业技术学院', '16', '专科', 'http://www.xyswxy.com/', 'xyswxy', '信阳市');
INSERT INTO `university` VALUES ('2929', '鹤壁汽车工程职业学院', '16', '专科', 'http://www.hbqcxy.com/', 'hbqcxy', '鹤壁市');
INSERT INTO `university` VALUES ('2930', '南阳职业学院', '16', '专科', 'http://www.nyzyxy.com/', 'nyzyxy', '南阳市');
INSERT INTO `university` VALUES ('2931', '郑州商贸旅游职业学院', '16', '专科', 'http://www.zzvcct.com/', 'zzvcct', '郑州市');
INSERT INTO `university` VALUES ('2932', '河南推拿职业学院', '16', '专科', 'http://www.hnzjschool.com/', 'hnzjschool', '洛阳市');
INSERT INTO `university` VALUES ('2933', '洛阳职业技术学院', '16', '专科', 'http://www.lyvtc.net/', 'lyvtc', '洛阳市');
INSERT INTO `university` VALUES ('2934', '郑州幼儿师范高等专科学校', '16', '专科', 'http://www.zyedu.org/', 'zyedu', '郑州市');
INSERT INTO `university` VALUES ('2935', '安阳幼儿师范高等专科学校', '16', '专科', 'http://www.ayys.net.cn/', 'ayys', '郑州市');
INSERT INTO `university` VALUES ('2936', '郑州黄河护理职业学院', '16', '专科', 'http://www.zyrnvc.com/', 'zyrnvc', '郑州市');
INSERT INTO `university` VALUES ('2937', '河南医学高等专科学校', '16', '专科', 'http://www.hamc.edu.cn/', 'hamc', '郑州市');
INSERT INTO `university` VALUES ('2938', '郑州财税金融职业学院', '16', '专科', 'http://www.zzcsjr.edu.cn/', 'zzcsjr', '郑州市');
INSERT INTO `university` VALUES ('2939', '南阳农业职业学院', '16', '专科', 'http://www.nyac.cn/nzy/', 'nyac', '南阳市');
INSERT INTO `university` VALUES ('2940', '洛阳科技职业学院', '16', '专科', 'http://www.lykjxy.cn/', 'lykjxy', '洛阳市');
INSERT INTO `university` VALUES ('2941', '濮阳医学高等专科学校', '16', '专科', 'http://www.pyyzh.cn/', 'pyyzh', '濮阳市');
INSERT INTO `university` VALUES ('2942', '三门峡社会管理职业学院', '16', '专科', 'http://www.smxcsa.com/', 'smxcsa', '三门峡市');
INSERT INTO `university` VALUES ('2943', '河南轻工职业学院', '16', '专科', 'http://www.pili-zz.net/', 'pili-zz', '郑州市');
INSERT INTO `university` VALUES ('2944', '河南测绘职业学院', '16', '专科', 'http://www.zzcx.net/', 'zzcx', '郑州市');
INSERT INTO `university` VALUES ('2945', '武汉大学', '17', '本科', 'http://www.whu.edu.cn/', 'whu', '武汉市');
INSERT INTO `university` VALUES ('2946', '华中科技大学', '17', '本科', 'http://www.hust.edu.cn/', 'hust', '武汉市');
INSERT INTO `university` VALUES ('2947', '武汉科技大学', '17', '本科', 'http://www.wust.edu.cn/', 'wust', '武汉市');
INSERT INTO `university` VALUES ('2948', '长江大学', '17', '本科', 'http://www.yangtzeu.edu.cn/', 'yangtzeu', '荆州市');
INSERT INTO `university` VALUES ('2949', '武汉工程大学', '17', '本科', 'http://www.wit.edu.cn/', 'wit', '武汉市');
INSERT INTO `university` VALUES ('2950', '中国地质大学（武汉）', '17', '本科', 'http://www.cug.edu.cn/', 'cug', '武汉市');
INSERT INTO `university` VALUES ('2951', '武汉纺织大学', '17', '本科', 'http://www.wtu.edu.cn/', 'wtu', '武汉市');
INSERT INTO `university` VALUES ('2952', '武汉轻工大学', '17', '本科', 'http://www.whpu.edu.cn/', 'whpu', '武汉市');
INSERT INTO `university` VALUES ('2953', '武汉理工大学', '17', '本科', 'http://www.whut.edu.cn/', 'whut', '武汉市');
INSERT INTO `university` VALUES ('2954', '湖北工业大学', '17', '本科', 'http://www.hbut.edu.cn/', 'hbut', '武汉市');
INSERT INTO `university` VALUES ('2955', '华中农业大学', '17', '本科', 'http://www.hzau.edu.cn/', 'hzau', '武汉市');
INSERT INTO `university` VALUES ('2956', '湖北中医药大学', '17', '本科', 'http://www.hbtcm.edu.cn/', 'hbtcm', '武汉市');
INSERT INTO `university` VALUES ('2957', '华中师范大学', '17', '本科', 'http://www.ccnu.edu.cn/', 'ccnu', '武汉市');
INSERT INTO `university` VALUES ('2958', '湖北大学', '17', '本科', 'http://www.hubu.edu.cn/', 'hubu', '武汉市');
INSERT INTO `university` VALUES ('2959', '湖北师范大学', '17', '本科', 'http://www.hbnu.edu.cn/', 'hbnu', '黄石市');
INSERT INTO `university` VALUES ('2960', '黄冈师范学院', '17', '本科', 'http://www.hgnu.cn/', 'hgnu', '黄冈市');
INSERT INTO `university` VALUES ('2961', '湖北民族学院', '17', '本科', 'http://www.hbmy.edu.cn/', 'hbmy', '恩施市');
INSERT INTO `university` VALUES ('2962', '汉江师范学院', '17', '本科', 'http://www.hjnu.edu.cn/', 'hjnu', '十堰市');
INSERT INTO `university` VALUES ('2963', '湖北文理学院', '17', '本科', 'http://www.hbuas.edu.cn/', 'hbuas', '襄樊市');
INSERT INTO `university` VALUES ('2964', '中南财经政法大学', '17', '本科', 'http://www.zuel.edu.cn/', 'zuel', '武汉市');
INSERT INTO `university` VALUES ('2965', '武汉体育学院', '17', '本科', 'http://www.whsu.edu.cn/', 'whsu', '武汉市');
INSERT INTO `university` VALUES ('2966', '湖北美术学院', '17', '本科', 'http://www.hifa.edu.cn/', 'hifa', '武汉市');
INSERT INTO `university` VALUES ('2967', '中南民族大学', '17', '本科', 'http://www.scuec.edu.cn/', 'scuec', '武汉市');
INSERT INTO `university` VALUES ('2968', '湖北汽车工业学院', '17', '本科', 'http://www.huat.edu.cn/', 'huat', '十堰市');
INSERT INTO `university` VALUES ('2969', '湖北工程学院', '17', '本科', 'http://www.hbeu.cn/', 'hbeu', '孝感市');
INSERT INTO `university` VALUES ('2970', '湖北理工学院', '17', '本科', 'http://www.hbpu.edu.cn/', 'hbpu', '黄石市');
INSERT INTO `university` VALUES ('2971', '湖北科技学院', '17', '本科', 'http://www.hbust.com.cn/', 'hbust', '咸宁市');
INSERT INTO `university` VALUES ('2972', '湖北医药学院', '17', '本科', 'http://www.hbmu.edu.cn/', 'hbmu', '十堰市');
INSERT INTO `university` VALUES ('2973', '江汉大学', '17', '本科', 'http://www.jhun.edu.cn/', 'jhun', '武汉市');
INSERT INTO `university` VALUES ('2974', '三峡大学', '17', '本科', 'http://www.ctgu.edu.cn/', 'ctgu', '宜昌市');
INSERT INTO `university` VALUES ('2975', '湖北警官学院', '17', '本科', 'http://www.hbpa.edu.cn/', 'hbpa', '武汉市');
INSERT INTO `university` VALUES ('2976', '荆楚理工学院', '17', '本科', 'http://www.jcut.edu.cn/', 'jcut', '荆门市');
INSERT INTO `university` VALUES ('2977', '武汉音乐学院', '17', '本科', 'http://www.whcm.edu.cn/', 'whcm', '武汉市');
INSERT INTO `university` VALUES ('2978', '湖北经济学院', '17', '本科', 'http://www.hbue.edu.cn/', 'hbue', '武汉市');
INSERT INTO `university` VALUES ('2979', '武汉商学院', '17', '本科', 'http://www.wbu.edu.cn/', 'wbu', '武汉市');
INSERT INTO `university` VALUES ('2980', '武汉东湖学院', '17', '本科', 'http://www.wdu.edu.cn/', 'wdu', '武汉市');
INSERT INTO `university` VALUES ('2981', '汉口学院', '17', '本科', 'http://www.hkxy.edu.cn/', 'hkxy', '武汉市');
INSERT INTO `university` VALUES ('2982', '武昌首义学院', '17', '本科', 'http://www.wsyu.edu.cn/', 'wsyu', '武汉市');
INSERT INTO `university` VALUES ('2983', '武昌理工学院', '17', '本科', 'http://www.wut.edu.cn/', 'wut', '武汉市');
INSERT INTO `university` VALUES ('2984', '武汉生物工程学院', '17', '本科', 'http://www.whsw.cn/', 'whsw', '武汉市');
INSERT INTO `university` VALUES ('2985', '武汉晴川学院', '17', '本科', 'http://luojia-whu.cn/', 'cn/', '武汉市');
INSERT INTO `university` VALUES ('2986', '湖北大学知行学院', '17', '本科', 'http://www.hudazx.cn/', 'hudazx', '武汉市');
INSERT INTO `university` VALUES ('2987', '武汉科技大学城市学院', '17', '本科', 'http://www.city.wust.edu.cn/', 'city', '武汉市');
INSERT INTO `university` VALUES ('2988', '三峡大学科技学院', '17', '本科', 'http://210.42.35.198/', '42', '宜昌市');
INSERT INTO `university` VALUES ('2989', '江汉大学文理学院', '17', '本科', 'http://www.jdwlxy.cn/', 'jdwlxy', '武汉市');
INSERT INTO `university` VALUES ('2990', '湖北工业大学工程技术学院', '17', '本科', 'http://gcxy.hbut.edu.cn/', 'hbut', '武汉市');
INSERT INTO `university` VALUES ('2991', '武汉工程大学邮电与信息工程学院', '17', '本科', 'http://www.witpt.edu.cn/', 'witpt', '武汉市');
INSERT INTO `university` VALUES ('2992', '武汉纺织大学外经贸学院', '17', '本科', 'http://cibe.wtu.edu.cn/', 'wtu', '武汉市');
INSERT INTO `university` VALUES ('2993', '武昌工学院', '17', '本科', 'http://www.wuit.cn/', 'wuit', '武汉市');
INSERT INTO `university` VALUES ('2994', '武汉工商学院', '17', '本科', 'http://www.wtbu.edu.cn/', 'wtbu', '武汉市');
INSERT INTO `university` VALUES ('2995', '长江大学工程技术学院', '17', '本科', 'http://gcxy.yangtzeu.edu.cn/', 'yangtzeu', '荆州市');
INSERT INTO `university` VALUES ('2996', '长江大学文理学院', '17', '本科', 'http://wlxy.yangtzeu.edu.cn/', 'yangtzeu', '荆州市');
INSERT INTO `university` VALUES ('2997', '湖北商贸学院', '17', '本科', 'http://www.hugsmxy.com/', 'hugsmxy', '武汉市');
INSERT INTO `university` VALUES ('2998', '湖北汽车工业学院科技学院', '17', '本科', 'http://kjxy.huat.edu.cn/', 'huat', '十堰市');
INSERT INTO `university` VALUES ('2999', '湖北医药学院药护学院', '17', '本科', 'http://yhgj.hbmu.edu.cn/struct', 'hbmu', '十堰市');
INSERT INTO `university` VALUES ('3000', '湖北民族学院科技学院', '17', '本科', 'http://www.hbmykjxy.cn/', 'hbmykjxy', '恩施市');
INSERT INTO `university` VALUES ('3001', '湖北经济学院法商学院', '17', '本科', 'http://fsxy.hbue.edu.cn/', 'hbue', '武汉市');
INSERT INTO `university` VALUES ('3002', '武汉体育学院体育科技学院', '17', '本科', 'http://www.kjxy.wipe.edu.cn/', 'kjxy', '武汉市');
INSERT INTO `university` VALUES ('3003', '湖北师范大学文理学院', '17', '本科', 'http://www.wlxy.hbnu.edu.cn/', 'wlxy', '黄石市');
INSERT INTO `university` VALUES ('3004', '湖北文理学院理工学院', '17', '本科', 'http://www.xfu.edu.cn/zsweb/lg', 'xfu', '襄阳市');
INSERT INTO `university` VALUES ('3005', '湖北工程学院新技术学院', '17', '本科', 'http://www.hbeutc.cn/', 'hbeutc', '孝感市');
INSERT INTO `university` VALUES ('3006', '文华学院', '17', '本科', 'http://www.hustwenhua.net/', 'hustwenhua', '武汉市');
INSERT INTO `university` VALUES ('3007', '武汉学院', '17', '本科', 'http://www.whxy.net/', 'whxy', '武汉市');
INSERT INTO `university` VALUES ('3008', '武汉工程科技学院', '17', '本科', 'http://www.wuhues.com/', 'wuhues', '武汉市');
INSERT INTO `university` VALUES ('3009', '武汉华夏理工学院', '17', '本科', 'http://www.1957.cn/', '1957', '武汉市');
INSERT INTO `university` VALUES ('3010', '武汉传媒学院', '17', '本科', 'http://www.whmc.edu.cn/', 'whmc', '湖北省');
INSERT INTO `university` VALUES ('3011', '武汉设计工程学院', '17', '本科', 'http://www.hnctxy.com/', 'hnctxy', '武汉市');
INSERT INTO `university` VALUES ('3012', '湖北第二师范学院', '17', '本科', 'http://www.hue.edu.cn/', 'hue', '武汉市');
INSERT INTO `university` VALUES ('3013', '武汉职业技术学院', '17', '专科', 'http://www.wtc.edu.cn/', 'wtc', '武汉市');
INSERT INTO `university` VALUES ('3014', '黄冈职业技术学院', '17', '专科', 'http://www.hbhgzy.com.cn/', 'hbhgzy', '黄冈市');
INSERT INTO `university` VALUES ('3015', '长江职业学院', '17', '专科', 'http://www.cjxy.edu.cn/', 'cjxy', '武汉市');
INSERT INTO `university` VALUES ('3016', '荆州理工职业学院', '17', '专科', 'http://www.ssdx.net/', 'ssdx', '沙市市');
INSERT INTO `university` VALUES ('3017', '湖北工业职业技术学院', '17', '专科', 'http://www.syzy.com.cn/', 'syzy', '十堰市');
INSERT INTO `university` VALUES ('3018', '鄂州职业大学', '17', '专科', 'http://www.ezu.net.cn/', 'ezu', '鄂州市');
INSERT INTO `university` VALUES ('3019', '武汉城市职业学院', '17', '专科', 'http://www.whcvc.cn/', 'whcvc', '武汉市');
INSERT INTO `university` VALUES ('3020', '湖北职业技术学院', '17', '专科', 'http://www.hbvtc.edu.cn/', 'hbvtc', '孝感市');
INSERT INTO `university` VALUES ('3021', '武汉船舶职业技术学院', '17', '专科', 'http://www.wspc.edu.cn/', 'wspc', '武汉市');
INSERT INTO `university` VALUES ('3022', '恩施职业技术学院', '17', '专科', 'http://www.eszy.edu.cn/', 'eszy', '恩施市');
INSERT INTO `university` VALUES ('3023', '襄阳职业技术学院', '17', '专科', 'http://www.hbxftc.com/', 'hbxftc', '襄阳市');
INSERT INTO `university` VALUES ('3024', '武汉工贸职业学院', '17', '专科', 'http://www.whgmxy.com/', 'whgmxy', '武汉市');
INSERT INTO `university` VALUES ('3025', '荆州职业技术学院', '17', '专科', 'http://www.jzit.net.cn/', 'jzit', '荆州市');
INSERT INTO `university` VALUES ('3026', '武汉工程职业技术学院', '17', '专科', 'http://www.wgxy.net/', 'wgxy', '武汉市');
INSERT INTO `university` VALUES ('3027', '仙桃职业学院', '17', '专科', 'http://www.hbxtzy.com/', 'hbxtzy', '仙桃市');
INSERT INTO `university` VALUES ('3028', '湖北轻工职业技术学院', '17', '专科', 'http://www.hbliti.com/', 'hbliti', '武汉市');
INSERT INTO `university` VALUES ('3029', '湖北交通职业技术学院', '17', '专科', 'http://www.hbctc.edu.cn/', 'hbctc', '武汉市');
INSERT INTO `university` VALUES ('3030', '湖北中医药高等专科学校', '17', '专科', 'http://www.hbzyy.org/', 'hbzyy', '荆州市');
INSERT INTO `university` VALUES ('3031', '武汉航海职业技术学院', '17', '专科', 'http://www.whhhxy.com/', 'whhhxy', '武汉市');
INSERT INTO `university` VALUES ('3032', '武汉铁路职业技术学院', '17', '专科', 'http://www.wru.com.cn/', 'wru', '武汉市');
INSERT INTO `university` VALUES ('3033', '武汉软件工程职业学院', '17', '专科', 'http://www.whvcse.com/', 'whvcse', '武汉市');
INSERT INTO `university` VALUES ('3034', '湖北三峡职业技术学院', '17', '专科', 'http://www.tgc.edu.cn/', 'tgc', '宜昌市');
INSERT INTO `university` VALUES ('3035', '随州职业技术学院', '17', '专科', 'http://www.szvtc.cn/', 'szvtc', '随州市');
INSERT INTO `university` VALUES ('3036', '武汉电力职业技术学院', '17', '专科', 'http://www.whetc.com/', 'whetc', '武汉市');
INSERT INTO `university` VALUES ('3037', '湖北水利水电职业技术学院', '17', '专科', 'http://www.hbsy.cn/', 'hbsy', '武汉市');
INSERT INTO `university` VALUES ('3038', '湖北城市建设职业技术学院', '17', '专科', 'http://www.ucvc.net/', 'ucvc', '武汉市');
INSERT INTO `university` VALUES ('3039', '武汉警官职业学院', '17', '专科', 'http://www.whpa.cn/', 'whpa', '武汉市');
INSERT INTO `university` VALUES ('3040', '湖北生物科技职业学院', '17', '专科', 'http://www.hbswkj.com/', 'hbswkj', '武汉市');
INSERT INTO `university` VALUES ('3041', '湖北开放职业学院', '17', '专科', 'http://www.hbou.cn/', 'hbou', '武汉市');
INSERT INTO `university` VALUES ('3042', '武汉科技职业学院', '17', '专科', 'http://www.whkjzy.cn/', 'whkjzy', '武汉市');
INSERT INTO `university` VALUES ('3043', '武汉外语外事职业学院', '17', '专科', 'http://www.whflfa.com/', 'whflfa', '武汉市');
INSERT INTO `university` VALUES ('3044', '武汉信息传播职业技术学院', '17', '专科', 'http://www.whinfo.cn/', 'whinfo', '武汉市');
INSERT INTO `university` VALUES ('3045', '武昌职业学院', '17', '专科', 'http://www.wlci.com.cn/', 'wlci', '武汉市');
INSERT INTO `university` VALUES ('3046', '武汉商贸职业学院', '17', '专科', 'http://www.whicu.com/', 'whicu', '武汉市');
INSERT INTO `university` VALUES ('3047', '湖北艺术职业学院', '17', '专科', 'http://www.artschool.com.cn/', 'artschool', '武汉市');
INSERT INTO `university` VALUES ('3048', '武汉交通职业学院', '17', '专科', 'http://www.whjzy.net/', 'whjzy', '武汉市');
INSERT INTO `university` VALUES ('3049', '咸宁职业技术学院', '17', '专科', 'http://www.xnec.cn/', 'xnec', '咸宁市');
INSERT INTO `university` VALUES ('3050', '长江工程职业技术学院', '17', '专科', 'http://www.cj-edu.com.cn/', 'cj-edu', '武汉市');
INSERT INTO `university` VALUES ('3051', '江汉艺术职业学院', '17', '专科', 'http://www.hbjhart.com/', 'hbjhart', '潜江市');
INSERT INTO `university` VALUES ('3052', '武汉工业职业技术学院', '17', '专科', 'http://www.wgy.cn/', 'wgy', '武汉市');
INSERT INTO `university` VALUES ('3053', '武汉民政职业学院', '17', '专科', 'http://www.whmzxy.cn/', 'whmzxy', '武汉市');
INSERT INTO `university` VALUES ('3054', '鄂东职业技术学院', '17', '专科', 'http://www.edzy.cn/', 'edzy', '黄冈市');
INSERT INTO `university` VALUES ('3055', '湖北财税职业学院', '17', '专科', 'http://www.hbftc.org.cn/', 'hbftc', '武汉市');
INSERT INTO `university` VALUES ('3056', '黄冈科技职业学院', '17', '专科', 'http://www.hbhgkj.com/', 'hbhgkj', '黄冈市');
INSERT INTO `university` VALUES ('3057', '湖北国土资源职业学院', '17', '专科', 'http://www.hbgt.com.cn/', 'hbgt', '武汉市');
INSERT INTO `university` VALUES ('3058', '湖北生态工程职业技术学院', '17', '专科', 'http://www.hb-green.com/', 'hb-green', '武汉市');
INSERT INTO `university` VALUES ('3059', '三峡电力职业学院', '17', '专科', 'http://www.tgcep.cn/', 'tgcep', '宜昌市');
INSERT INTO `university` VALUES ('3060', '湖北科技职业学院', '17', '专科', 'http://www.hubstc.com.cn/', 'hubstc', '武汉市');
INSERT INTO `university` VALUES ('3061', '湖北青年职业学院', '17', '专科', 'http://www.hbqnxy.com/', 'hbqnxy', '武汉市');
INSERT INTO `university` VALUES ('3062', '湖北工程职业学院', '17', '专科', 'http://www.hspt.net.cn/', 'hspt', '黄石市');
INSERT INTO `university` VALUES ('3063', '三峡旅游职业技术学院', '17', '专科', 'http://www.sxlyzy.edu.cn/', 'sxlyzy', '宜昌市');
INSERT INTO `university` VALUES ('3064', '天门职业学院', '17', '专科', 'http://www.tmzyxy.cn/', 'tmzyxy', '天门市');
INSERT INTO `university` VALUES ('3065', '湖北体育职业学院', '17', '专科', 'http://www.hbtyzy.com/', 'hbtyzy', '武汉市');
INSERT INTO `university` VALUES ('3066', '襄阳汽车职业技术学院', '17', '专科', 'http://www.xyqczy.com/', 'xyqczy', '襄阳市');
INSERT INTO `university` VALUES ('3067', '湖北幼儿师范高等专科学校', '17', '专科', 'http://www.hbssyys.cn/', 'hbssyys', '武汉市');
INSERT INTO `university` VALUES ('3068', '湖北铁道运输职业学院', '17', '专科', 'http://www.wtsx.com.cn/', 'wtsx', '武汉市');
INSERT INTO `university` VALUES ('3069', '武汉海事职业学院', '17', '专科', 'http://www.whmvc.net/', 'whmvc', '武汉市');
INSERT INTO `university` VALUES ('3070', '长江艺术工程职业学院', '17', '专科', 'http://www.cj-cx.com/', 'cj-cx', '十堰市');
INSERT INTO `university` VALUES ('3071', '荆门职业学院', '17', '专科', 'http://www.jmzyxy.net.cn/', 'jmzyxy', '荆门市');
INSERT INTO `university` VALUES ('3072', '武汉铁路桥梁职业学院', '17', '专科', 'http://www.wrbss.net/', 'wrbss', '武汉市');
INSERT INTO `university` VALUES ('3073', '武汉光谷职业学院', '17', '专科', 'http://www.whggvc.net/', 'whggvc', '武汉市');
INSERT INTO `university` VALUES ('3074', '湖北师范学院文理学院', '17', '本科', 'http://www.wlxy.hbnu.edu.cn/', 'wlxy', '黄石市');
INSERT INTO `university` VALUES ('3075', '湘潭大学', '18', '本科', 'http://www.xtu.edu.cn/', 'xtu', '湘潭市');
INSERT INTO `university` VALUES ('3076', '吉首大学', '18', '本科', 'http://www.jsu.edu.cn', 'jsu', '吉首市');
INSERT INTO `university` VALUES ('3077', '湖南大学', '18', '本科', 'http://www.hnu.cn/', 'hnu', '长沙市');
INSERT INTO `university` VALUES ('3078', '中南大学', '18', '本科', 'http://www.csu.edu.cn/', 'csu', '长沙市');
INSERT INTO `university` VALUES ('3079', '湖南科技大学', '18', '本科', 'http://www.hnust.edu.cn/', 'hnust', '湘潭市');
INSERT INTO `university` VALUES ('3080', '长沙理工大学', '18', '本科', 'http://www.csust.edu.cn/', 'csust', '长沙市');
INSERT INTO `university` VALUES ('3081', '湖南农业大学', '18', '本科', 'http://www.hunau.edu.cn/', 'hunau', '长沙市');
INSERT INTO `university` VALUES ('3082', '中南林业科技大学', '18', '本科', 'http://www.csuft.com/', 'csuft', '长沙市');
INSERT INTO `university` VALUES ('3083', '湖南中医药大学', '18', '本科', 'http://www.hnctcm.edu.cn/', 'hnctcm', '长沙市');
INSERT INTO `university` VALUES ('3084', '湖南师范大学', '18', '本科', 'http://www.hunnu.edu.cn/', 'hunnu', '长沙市');
INSERT INTO `university` VALUES ('3085', '湖南理工学院', '18', '本科', 'http://www.hnist.cn/', 'hnist', '岳阳市');
INSERT INTO `university` VALUES ('3086', '湘南学院', '18', '本科', 'http://www.xnu.edu.cn/', 'xnu', '郴州市');
INSERT INTO `university` VALUES ('3087', '衡阳师范学院', '18', '本科', 'http://www.hynu.edu.cn/', 'hynu', '衡阳市');
INSERT INTO `university` VALUES ('3088', '邵阳学院', '18', '本科', 'http://www.hnsyu.net/', 'hnsyu', '邵阳市');
INSERT INTO `university` VALUES ('3089', '怀化学院', '18', '本科', 'http://www.hhtc.edu.cn/', 'hhtc', '怀化市');
INSERT INTO `university` VALUES ('3090', '湖南文理学院', '18', '本科', 'http://www.huas.cn/', 'huas', '常德市');
INSERT INTO `university` VALUES ('3091', '湖南科技学院', '18', '本科', 'http://www.huse.cn/', 'huse', '永州市');
INSERT INTO `university` VALUES ('3092', '湖南人文科技学院', '18', '本科', 'http://www.hnrku.net.cn/', 'hnrku', '娄底市');
INSERT INTO `university` VALUES ('3093', '湖南商学院', '18', '本科', 'http://www.hnuc.edu.cn/', 'hnuc', '长沙市');
INSERT INTO `university` VALUES ('3094', '南华大学', '18', '本科', 'http://www.usc.edu.cn/', 'usc', '衡阳市');
INSERT INTO `university` VALUES ('3095', '长沙医学院', '18', '本科', 'http://www.csmu.edu.cn/', 'csmu', '长沙市');
INSERT INTO `university` VALUES ('3096', '长沙学院', '18', '本科', 'http://www.ccsu.cn/', 'ccsu', '长沙市');
INSERT INTO `university` VALUES ('3097', '湖南工程学院', '18', '本科', 'http://www.hnie.edu.cn/', 'hnie', '湘潭市');
INSERT INTO `university` VALUES ('3098', '湖南城市学院', '18', '本科', 'http://www.hncu.net/', 'hncu', '益阳市');
INSERT INTO `university` VALUES ('3099', '湖南工学院', '18', '本科', 'http://www.hnpu.edu.cn/', 'hnpu', '衡阳市');
INSERT INTO `university` VALUES ('3100', '湖南财政经济学院', '18', '本科', 'http://www.hufe.edu.cn/', 'hufe', '长沙市');
INSERT INTO `university` VALUES ('3101', '湖南警察学院', '18', '本科', 'http://www.hnpolice.com/', 'hnpolice', '长沙市');
INSERT INTO `university` VALUES ('3102', '湖南工业大学', '18', '本科', 'http://www.hut.edu.cn/', 'hut', '株洲市');
INSERT INTO `university` VALUES ('3103', '湖南女子学院', '18', '本科', 'http://www.hnnd.com.cn/', 'hnnd', '长沙市');
INSERT INTO `university` VALUES ('3104', '湖南第一师范学院', '18', '本科', 'http://www.hnfnu.edu.cn/', 'hnfnu', '长沙市');
INSERT INTO `university` VALUES ('3105', '湖南医药学院', '18', '本科', 'http://www.hnmu.com.cn/', 'hnmu', '怀化市');
INSERT INTO `university` VALUES ('3106', '湖南涉外经济学院', '18', '本科', 'http://www.hunaneu.com/', 'hunaneu', '长沙市');
INSERT INTO `university` VALUES ('3107', '湘潭大学兴湘学院', '18', '本科', 'http://xxxy.xtu.edu.cn/', 'xtu', '湘潭市');
INSERT INTO `university` VALUES ('3108', '湖南工业大学科技学院', '18', '本科', 'http://www.hnut-d.com/', 'hnut-d', '株洲市');
INSERT INTO `university` VALUES ('3109', '湖南科技大学潇湘学院', '18', '本科', 'http://dep.hnust.edu.cn/xxxy/', 'hnust', '湘潭市');
INSERT INTO `university` VALUES ('3110', '南华大学船山学院', '18', '本科', 'http://usc.edu.cn/csxy/', 'edu', '衡阳市');
INSERT INTO `university` VALUES ('3111', '湖南商学院北津学院', '18', '本科', 'http://www.bjxy.net.cn/', 'bjxy', '长沙市');
INSERT INTO `university` VALUES ('3112', '湖南师范大学树达学院', '18', '本科', 'http://sdw.hunnu.edu.cn/', 'hunnu', '长沙市');
INSERT INTO `university` VALUES ('3113', '湖南农业大学东方科技学院', '18', '本科', 'http://www.hnaues.com/', 'hnaues', '长沙市');
INSERT INTO `university` VALUES ('3114', '中南林业科技大学涉外学院', '18', '本科', 'http://new.zswxy.cn/', 'zswxy', '长沙市');
INSERT INTO `university` VALUES ('3115', '湖南文理学院芙蓉学院', '18', '本科', 'http://fur.huas.cn/', 'huas', '常德市');
INSERT INTO `university` VALUES ('3116', '湖南理工学院南湖学院', '18', '本科', 'http://nh.80city.cn/', '80city', '岳阳市');
INSERT INTO `university` VALUES ('3117', '衡阳师范学院南岳学院', '18', '本科', 'http://nyxy.hynu.cn/', 'hynu', '衡阳市');
INSERT INTO `university` VALUES ('3118', '湖南工程学院应用技术学院', '18', '本科', 'http://www.hnieyy.cn/', 'hnieyy', '湘潭市');
INSERT INTO `university` VALUES ('3119', '湖南中医药大学湘杏学院', '18', '本科', 'http://www.hnzyxx.com/', 'hnzyxx', '长沙市');
INSERT INTO `university` VALUES ('3120', '吉首大学张家界学院', '18', '本科', 'http://zjj.jsu.edu.cn/', 'jsu', '张家界市');
INSERT INTO `university` VALUES ('3121', '长沙理工大学城南学院', '18', '本科', 'http://www.csust.edu.cn/pub/cn', 'csust', '长沙市');
INSERT INTO `university` VALUES ('3122', '长沙师范学院', '18', '本科', 'http://www.cssf.cn/', 'cssf', '长沙市');
INSERT INTO `university` VALUES ('3123', '湖南应用技术学院', '18', '本科', 'http://www.hnyyjsxy.com/', 'hnyyjsxy', '常德市');
INSERT INTO `university` VALUES ('3124', '湖南信息学院', '18', '本科', 'http://www.hnisc.com/', 'hnisc', '长沙市');
INSERT INTO `university` VALUES ('3125', '湖南交通工程学院', '18', '本科', 'http://www.hnkjjm.com/', 'hnkjjm', '衡阳市');
INSERT INTO `university` VALUES ('3126', '湘中幼儿师范高等专科学校', '18', '专科', 'http://www.hnsysf.com/', 'hnsysf', '邵阳市');
INSERT INTO `university` VALUES ('3127', '长沙民政职业技术学院', '18', '专科', 'http://www.csmzxy.com/', 'csmzxy', '长沙市');
INSERT INTO `university` VALUES ('3128', '湖南工业职业技术学院', '18', '专科', 'http://www.hunangy.com/', 'hunangy', '长沙市');
INSERT INTO `university` VALUES ('3129', '湖南信息职业技术学院', '18', '专科', 'http://www.hniu.com/', 'hniu', '长沙市');
INSERT INTO `university` VALUES ('3130', '湖南税务高等专科学校', '18', '专科', 'http://www.csttc.cn/', 'csttc', '长沙市');
INSERT INTO `university` VALUES ('3131', '长沙航空职业技术学院', '18', '专科', 'http://www.cavtc.cn/', 'cavtc', '长沙市');
INSERT INTO `university` VALUES ('3132', '湖南大众传媒职业技术学院', '18', '专科', 'http://www.hnmmc.cn/', 'hnmmc', '长沙市');
INSERT INTO `university` VALUES ('3133', '永州职业技术学院', '18', '专科', 'http://www.hnyzzy.com/', 'hnyzzy', '永州市');
INSERT INTO `university` VALUES ('3134', '湖南铁道职业技术学院', '18', '专科', 'http://www.hnrpc.com/', 'hnrpc', '株洲市');
INSERT INTO `university` VALUES ('3135', '湖南科技职业学院', '18', '专科', 'http://www.hnkjxy.net.cn/', 'hnkjxy', '长沙市');
INSERT INTO `university` VALUES ('3136', '湖南生物机电职业技术学院', '18', '专科', 'http://www.hnbemc.com/', 'hnbemc', '长沙市');
INSERT INTO `university` VALUES ('3137', '湖南交通职业技术学院', '18', '专科', 'http://www.hnjtzy.com.cn/', 'hnjtzy', '长沙市');
INSERT INTO `university` VALUES ('3138', '湖南商务职业技术学院', '18', '专科', 'http://www.hnswxy.com/', 'hnswxy', '长沙市');
INSERT INTO `university` VALUES ('3139', '湖南体育职业学院', '18', '专科', 'http://tyzy.hnedu.cn/', 'hnedu', '长沙市');
INSERT INTO `university` VALUES ('3140', '湖南工程职业技术学院', '18', '专科', 'http://www.hngcjx.com.cn/', 'hngcjx', '长沙市');
INSERT INTO `university` VALUES ('3141', '保险职业学院', '18', '专科', 'http://www.bxxy.com/', 'bxxy', '长沙市');
INSERT INTO `university` VALUES ('3142', '湖南外贸职业学院', '18', '专科', 'http://www.hnwmxy.com/', 'hnwmxy', '长沙市');
INSERT INTO `university` VALUES ('3143', '湖南网络工程职业学院', '18', '专科', 'http://www.hnevc.com/', 'hnevc', '长沙市');
INSERT INTO `university` VALUES ('3144', '邵阳职业技术学院', '18', '专科', 'http://www.syzyedu.com/', 'syzyedu', '邵阳市');
INSERT INTO `university` VALUES ('3145', '湖南司法警官职业学院', '18', '专科', 'http://www.jgzy.com/', 'jgzy', '长沙市');
INSERT INTO `university` VALUES ('3146', '长沙商贸旅游职业技术学院', '18', '专科', 'http://www.hncpu.com/', 'hncpu', '长沙市');
INSERT INTO `university` VALUES ('3147', '湖南环境生物职业技术学院', '18', '专科', 'http://www.hnebp.edu.cn/', 'hnebp', '衡阳市');
INSERT INTO `university` VALUES ('3148', '湖南邮电职业技术学院', '18', '专科', 'http://www.hnydxy.com/', 'hnydxy', '长沙市');
INSERT INTO `university` VALUES ('3149', '湘潭医卫职业技术学院', '18', '专科', 'http://www.xtzy.com/', 'xtzy', '湘潭市');
INSERT INTO `university` VALUES ('3150', '郴州职业技术学院', '18', '专科', 'http://www.czzy-edu.com/', 'czzy-edu', '郴州市');
INSERT INTO `university` VALUES ('3151', '娄底职业技术学院', '18', '专科', 'http://www.ldzy.com/', 'ldzy', '娄底市');
INSERT INTO `university` VALUES ('3152', '张家界航空工业职业技术学院', '18', '专科', 'http://www.zjjhy.net/', 'zjjhy', '张家界市');
INSERT INTO `university` VALUES ('3153', '长沙环境保护职业技术学院', '18', '专科', 'http://www.hbcollege.com.cn/', 'hbcollege', '长沙市');
INSERT INTO `university` VALUES ('3154', '湖南艺术职业学院', '18', '专科', 'http://www.arthn.com/', 'arthn', '长沙市');
INSERT INTO `university` VALUES ('3155', '湖南机电职业技术学院', '18', '专科', 'http://www.hnjdzy.net/', 'hnjdzy', '长沙市');
INSERT INTO `university` VALUES ('3156', '长沙职业技术学院', '18', '专科', 'http://www.cszyedu.cn/', 'cszyedu', '长沙市');
INSERT INTO `university` VALUES ('3157', '怀化职业技术学院', '18', '专科', 'http://www.hhvtc.com.cn/', 'hhvtc', '怀化市');
INSERT INTO `university` VALUES ('3158', '岳阳职业技术学院', '18', '专科', 'http://www.yvtc.edu.cn/', 'yvtc', '岳阳市');
INSERT INTO `university` VALUES ('3159', '常德职业技术学院', '18', '专科', 'http://www.cdzy.cn/', 'cdzy', '常德市');
INSERT INTO `university` VALUES ('3160', '长沙南方职业学院', '18', '专科', 'http://www.nfdx.net/', 'nfdx', '长沙市');
INSERT INTO `university` VALUES ('3161', '潇湘职业学院', '18', '专科', 'http://www.hnxxc.com/', 'hnxxc', '娄底市');
INSERT INTO `university` VALUES ('3162', '湖南化工职业技术学院', '18', '专科', 'http://www.hnhgzy.com/', 'hnhgzy', '株洲市');
INSERT INTO `university` VALUES ('3163', '湖南城建职业技术学院', '18', '专科', 'http://www.hnucc.com/', 'hnucc', '湘潭市');
INSERT INTO `university` VALUES ('3164', '湖南石油化工职业技术学院', '18', '专科', 'http://www.hnshzy.cn/', 'hnshzy', '岳阳市');
INSERT INTO `university` VALUES ('3165', '湖南中医药高等专科学校', '18', '专科', 'http://www.hntcmc.net/', 'hntcmc', '湖南省');
INSERT INTO `university` VALUES ('3166', '湖南民族职业学院', '18', '专科', 'http://www.hnvc.net.cn/', 'hnvc', '岳阳市');
INSERT INTO `university` VALUES ('3167', '湘西民族职业技术学院', '18', '专科', 'http://www.xxmzy.com/', 'xxmzy', '吉首市');
INSERT INTO `university` VALUES ('3168', '湖南财经工业职业技术学院', '18', '专科', 'http://www.hycgy.com/', 'hycgy', '衡阳市');
INSERT INTO `university` VALUES ('3169', '益阳职业技术学院', '18', '专科', 'http://www.yyvtc.cn/', 'yyvtc', '益阳市');
INSERT INTO `university` VALUES ('3170', '湖南工艺美术职业学院', '18', '专科', 'http://www.hnmeida.com.cn/', 'hnmeida', '益阳市');
INSERT INTO `university` VALUES ('3171', '湖南九嶷职业技术学院', '18', '专科', 'http://www.4744edu.com/', '4744edu', '永州市');
INSERT INTO `university` VALUES ('3172', '湖南理工职业技术学院', '18', '专科', 'http://www.xlgy.com/', 'xlgy', '湘潭市');
INSERT INTO `university` VALUES ('3173', '湖南软件职业学院', '18', '专科', 'http://www.hnsoftedu.com/', 'hnsoftedu', '湘潭市');
INSERT INTO `university` VALUES ('3174', '湖南汽车工程职业学院', '18', '专科', 'http://www.zzptc.com/', 'zzptc', '株洲市');
INSERT INTO `university` VALUES ('3175', '长沙电力职业技术学院', '18', '专科', 'http://www.cseptc.net/', 'cseptc', '长沙市');
INSERT INTO `university` VALUES ('3176', '湖南水利水电职业技术学院', '18', '专科', 'http://www.hnslsdxy.com/', 'hnslsdxy', '长沙市');
INSERT INTO `university` VALUES ('3177', '湖南现代物流职业技术学院', '18', '专科', 'http://www.56edu.com/', '56edu', '长沙市');
INSERT INTO `university` VALUES ('3178', '湖南高速铁路职业技术学院', '18', '专科', 'http://www.htcrh.com/', 'htcrh', '衡阳市');
INSERT INTO `university` VALUES ('3179', '湖南铁路科技职业技术学院', '18', '专科', 'http://www.hntky.com/', 'hntky', '株洲市');
INSERT INTO `university` VALUES ('3180', '湖南安全技术职业学院', '18', '专科', 'http://www.cssttc.gov.cn/', 'cssttc', '长沙市');
INSERT INTO `university` VALUES ('3181', '湖南电气职业技术学院', '18', '专科', 'http://www.hnjd.net.cn/', 'hnjd', '湘潭市');
INSERT INTO `university` VALUES ('3182', '湖南外国语职业学院', '18', '专科', 'http://www.hnflc.cn/', 'hnflc', '长沙市');
INSERT INTO `university` VALUES ('3183', '益阳医学高等专科学校', '18', '专科', 'http://www.hnyyyz.com/', 'hnyyyz', '益阳市');
INSERT INTO `university` VALUES ('3184', '湖南都市职业学院', '18', '专科', 'http://www.hnupc.net/', 'hnupc', '长沙市');
INSERT INTO `university` VALUES ('3185', '湖南电子科技职业学院', '18', '专科', 'http://www.8379888.com/', '8379888', '长沙市');
INSERT INTO `university` VALUES ('3186', '湖南国防工业职业技术学院', '18', '专科', 'http://www.hnkgzy.com/', 'hnkgzy', '湘潭市');
INSERT INTO `university` VALUES ('3187', '湖南高尔夫旅游职业学院', '18', '专科', 'http://www.2823333.com/', '2823333', '常德市');
INSERT INTO `university` VALUES ('3188', '湖南工商职业学院', '18', '专科', 'http://www.hngsxy.com/', 'hngsxy', '衡阳市');
INSERT INTO `university` VALUES ('3189', '湖南三一工业职业技术学院', '18', '专科', 'http://www.sanyedu.com/', 'sanyedu', '长沙市');
INSERT INTO `university` VALUES ('3190', '长沙卫生职业学院', '18', '专科', 'http://www.cswszy.com/', 'cswszy', '长沙市');
INSERT INTO `university` VALUES ('3191', '湖南食品药品职业学院', '18', '专科', 'http://www.hnyyxx.net/', 'hnyyxx', '长沙市');
INSERT INTO `university` VALUES ('3192', '湖南有色金属职业技术学院', '18', '专科', 'http://www.hnyszy.com.cn/', 'hnyszy', '株洲市');
INSERT INTO `university` VALUES ('3193', '湖南吉利汽车职业技术学院', '18', '专科', 'http://www.hngeelyedu.cn/', 'hngeelyedu', '湘潭市');
INSERT INTO `university` VALUES ('3194', '湖南幼儿师范高等专科学校', '18', '专科', 'http://www.cdgdsf.com/', 'cdgdsf', '常德市');
INSERT INTO `university` VALUES ('3195', '湘南幼儿师范高等专科学校', '18', '专科', 'http://www.hnczsf.com/', 'hnczsf', '郴州市');
INSERT INTO `university` VALUES ('3196', '湖南劳动人事职业学院', '18', '专科', 'http://www.hnlrx.net/', 'hnlrx', '长沙市');
INSERT INTO `university` VALUES ('3197', '中山大学', '19', '本科', 'http://www.sysu.edu.cn/', 'sysu', '广州市');
INSERT INTO `university` VALUES ('3198', '暨南大学', '19', '本科', 'http://www.jnu.edu.cn/', 'jnu', '广州市');
INSERT INTO `university` VALUES ('3199', '汕头大学', '19', '本科', 'http://www.stu.edu.cn/', 'stu', '汕头市');
INSERT INTO `university` VALUES ('3200', '华南理工大学', '19', '本科', 'http://www.scut.edu.cn/', 'scut', '广州市');
INSERT INTO `university` VALUES ('3201', '华南农业大学', '19', '本科', 'http://www.scau.edu.cn/', 'scau', '广州市');
INSERT INTO `university` VALUES ('3202', '广东海洋大学', '19', '本科', 'http://www.gdou.edu.cn/', 'gdou', '湛江市');
INSERT INTO `university` VALUES ('3203', '广州医科大学', '19', '本科', 'http://www.gzhmu.edu.cn/', 'gzhmu', '广州市');
INSERT INTO `university` VALUES ('3204', '广东医科大学', '19', '本科', 'http://www.gdmu.edu.cn/', 'gdmu', '湛江市');
INSERT INTO `university` VALUES ('3205', '广州中医药大学', '19', '本科', 'http://www.gzhtcm.edu.cn/', 'gzhtcm', '广州市');
INSERT INTO `university` VALUES ('3206', '广东药科大学', '19', '本科', 'http://www.gdpu.edu.cn/', 'gdpu', '广州市');
INSERT INTO `university` VALUES ('3207', '华南师范大学', '19', '本科', 'http://www.scnu.edu.cn/', 'scnu', '广州市');
INSERT INTO `university` VALUES ('3208', '韶关学院', '19', '本科', 'http://www.sgu.edu.cn/', 'sgu', '韶关市');
INSERT INTO `university` VALUES ('3209', '惠州学院', '19', '本科', 'http://www.hzu.edu.cn/', 'hzu', '惠州市');
INSERT INTO `university` VALUES ('3210', '韩山师范学院', '19', '本科', 'http://www.hstc.edu.cn/', 'hstc', '潮州市');
INSERT INTO `university` VALUES ('3211', '岭南师范学院', '19', '本科', 'http://www.lingnan.edu.cn/', 'lingnan', '湛江市');
INSERT INTO `university` VALUES ('3212', '肇庆学院', '19', '本科', 'http://www.zqu.edu.cn/', 'zqu', '肇庆市');
INSERT INTO `university` VALUES ('3213', '嘉应学院', '19', '本科', 'http://www.jyu.edu.cn/', 'jyu', '梅州市');
INSERT INTO `university` VALUES ('3214', '广州体育学院', '19', '本科', 'http://www.gipe.edu.cn/', 'gipe', '广州市');
INSERT INTO `university` VALUES ('3215', '广州美术学院', '19', '本科', 'http://www.gzarts.edu.cn/', 'gzarts', '广州市');
INSERT INTO `university` VALUES ('3216', '星海音乐学院', '19', '本科', 'http://www.xhcom.edu.cn/', 'xhcom', '广州市');
INSERT INTO `university` VALUES ('3217', '广东技术师范学院', '19', '本科', 'http://www.gdin.edu.cn/', 'gdin', '广州市');
INSERT INTO `university` VALUES ('3218', '深圳大学', '19', '本科', 'http://www.szu.edu.cn/', 'szu', '深圳市');
INSERT INTO `university` VALUES ('3219', '广东财经大学', '19', '本科', 'http://www.gdufe.edu.cn/', 'gdufe', '广州市');
INSERT INTO `university` VALUES ('3220', '广东白云学院', '19', '本科', 'http://www.bvtc.edu.cn/', 'bvtc', '广州市');
INSERT INTO `university` VALUES ('3221', '广州大学', '19', '本科', 'http://www.gzhu.edu.cn/', 'gzhu', '广州市');
INSERT INTO `university` VALUES ('3222', '广州航海学院', '19', '本科', 'http://www.gzhmt.edu.cn/', 'gzhmt', '广州市');
INSERT INTO `university` VALUES ('3223', '广东警官学院', '19', '本科', 'http://www.gdppla.edu.cn/', 'gdppla', '广州市');
INSERT INTO `university` VALUES ('3224', '仲恺农业工程学院', '19', '本科', 'http://www.zhku.edu.cn/', 'zhku', '广州市');
INSERT INTO `university` VALUES ('3225', '五邑大学', '19', '本科', 'http://www.wyu.edu.cn/', 'wyu', '江门市');
INSERT INTO `university` VALUES ('3226', '广东金融学院', '19', '本科', 'http://www.gduf.edu.cn/', 'gduf', '广州市');
INSERT INTO `university` VALUES ('3227', '电子科技大学中山学院', '19', '本科', 'http://www.zsc.edu.cn/', 'zsc', '中山市');
INSERT INTO `university` VALUES ('3228', '广东石油化工学院', '19', '本科', 'http://www.gdpa.edu.cn/', 'gdpa', '茂名市');
INSERT INTO `university` VALUES ('3229', '东莞理工学院', '19', '本科', 'http://www.dgut.edu.cn/', 'dgut', '东莞市');
INSERT INTO `university` VALUES ('3230', '广东工业大学', '19', '本科', 'http://www.gdut.edu.cn/', 'gdut', '广州市');
INSERT INTO `university` VALUES ('3231', '广东外语外贸大学', '19', '本科', 'http://www.gdufs.edu.cn/', 'gdufs', '广州市');
INSERT INTO `university` VALUES ('3232', '佛山科学技术学院', '19', '本科', 'http://www.fosu.edu.cn/', 'fosu', '佛山市');
INSERT INTO `university` VALUES ('3233', '广东培正学院', '19', '本科', 'http://www.peizheng.net.cn/', 'peizheng', '广州市');
INSERT INTO `university` VALUES ('3234', '南方医科大学', '19', '本科', 'http://www.fimmu.com/', 'fimmu', '广州市');
INSERT INTO `university` VALUES ('3235', '广东东软学院', '19', '本科', 'http://www.neusoft.gd.cn/', 'neusoft', '佛山市');
INSERT INTO `university` VALUES ('3236', '华南理工大学广州学院', '19', '本科', 'http://www.gcu.edu.cn/', 'gcu', '广州市');
INSERT INTO `university` VALUES ('3237', '广州大学华软软件学院', '19', '本科', 'http://www.sise.com.cn/', 'sise', '广州市');
INSERT INTO `university` VALUES ('3238', '中山大学南方学院', '19', '本科', 'http://www.nfsysu.cn/', 'nfsysu', '广州市');
INSERT INTO `university` VALUES ('3239', '广东外语外贸大学南国商学院', '19', '本科', 'http://www.gwng.edu.cn/', 'gwng', '广州市');
INSERT INTO `university` VALUES ('3240', '广东财经大学华商学院', '19', '本科', 'http://www.gdhsc.edu.cn/', 'gdhsc', '广州市');
INSERT INTO `university` VALUES ('3241', '广东海洋大学寸金学院', '19', '本科', 'http://www.gdcjxy.com/', 'gdcjxy', '湛江市');
INSERT INTO `university` VALUES ('3242', '华南农业大学珠江学院', '19', '本科', 'http://www.scauzhujiang.cn/', 'scauzhujia', '广州市');
INSERT INTO `university` VALUES ('3243', '广东技术师范学院天河学院', '19', '本科', 'http://www.thxy.cn/', 'thxy', '广州市');
INSERT INTO `university` VALUES ('3244', '北京师范大学珠海分校', '19', '本科', 'http://www.bnuep.com/', 'bnuep', '珠海市');
INSERT INTO `university` VALUES ('3245', '广东工业大学华立学院', '19', '本科', 'http://www.hualixy.com/', 'hualixy', '广州市');
INSERT INTO `university` VALUES ('3246', '广州大学松田学院', '19', '本科', 'http://www.sontan.net/', 'sontan', '广州市');
INSERT INTO `university` VALUES ('3247', '广州商学院', '19', '本科', 'http://www.gzcc.cn/', 'gzcc', '广州市');
INSERT INTO `university` VALUES ('3248', '北京理工大学珠海学院', '19', '本科', 'http://www.zhbit.com/', 'zhbit', '珠海市');
INSERT INTO `university` VALUES ('3249', '吉林大学珠海学院', '19', '本科', 'http://www.jluzh.com/', 'jluzh', '珠海市');
INSERT INTO `university` VALUES ('3250', '广州工商学院', '19', '本科', 'http://www.gzgs.org.cn/', 'gzgs', '广州市');
INSERT INTO `university` VALUES ('3251', '广东科技学院', '19', '本科', 'http://www.gdst.cc/', 'gdst', '东莞市');
INSERT INTO `university` VALUES ('3252', '广东理工学院', '19', '本科', 'http://www.gdlgxy.com/', 'gdlgxy', '肇庆市');
INSERT INTO `university` VALUES ('3253', '东莞理工学院城市学院', '19', '本科', 'http://csxy.dgut.edu.cn/', 'dgut', '东莞市');
INSERT INTO `university` VALUES ('3254', '中山大学新华学院', '19', '本科', 'http://xh.sysu.edu.cn/', 'sysu', '广州市');
INSERT INTO `university` VALUES ('3255', '广东第二师范学院', '19', '本科', 'http://www.gdei.edu.cn/', 'gdei', '广州市');
INSERT INTO `university` VALUES ('3256', '南方科技大学', '19', '本科', 'http://www.sustc.edu.cn/', 'sustc', '深圳市');
INSERT INTO `university` VALUES ('3257', '北京师范大学－香港浸会大学联合国际学院', '19', '本科', 'http://uic.edu.hk/cn', 'edu', '珠海市');
INSERT INTO `university` VALUES ('3258', '香港中文大学（深圳）', '19', '本科', 'http://www.cuhk.edu.cn/', 'cuhk', '深圳市');
INSERT INTO `university` VALUES ('3259', '深圳北理莫斯科大学', '19', '本科', 'http://www.szmsubit.edu.cn/', 'szmsubit', '深圳市');
INSERT INTO `university` VALUES ('3260', '广东以色列理工学院', '19', '本科', 'http://www.gtiit.edu.cn/', 'gtiit', '汕头市');
INSERT INTO `university` VALUES ('3261', '顺德职业技术学院', '19', '专科', 'http://www.sdpt.com.cn/', 'sdpt', '佛山市');
INSERT INTO `university` VALUES ('3262', '广东轻工职业技术学院', '19', '专科', 'http://www.gdqy.edu.cn/', 'gdqy', '广州市');
INSERT INTO `university` VALUES ('3263', '广东交通职业技术学院', '19', '专科', 'http://www.gdcp.cn/', 'gdcp', '广州市');
INSERT INTO `university` VALUES ('3264', '广东水利电力职业技术学院', '19', '专科', 'http://www.gdsdxy.edu.cn/', 'gdsdxy', '广州市');
INSERT INTO `university` VALUES ('3265', '潮汕职业技术学院', '19', '专科', 'http://www.chaoshan.cn/', 'chaoshan', '揭阳市');
INSERT INTO `university` VALUES ('3266', '深圳职业技术学院', '19', '专科', 'http://www.szpt.edu.cn/', 'szpt', '深圳市');
INSERT INTO `university` VALUES ('3267', '广东南华工商职业学院', '19', '专科', 'http://www.nhic.edu.cn/', 'nhic', '广州市');
INSERT INTO `university` VALUES ('3268', '私立华联学院', '19', '专科', 'http://www.hlu.edu.cn/', 'hlu', '广州市');
INSERT INTO `university` VALUES ('3269', '广州民航职业技术学院', '19', '专科', 'http://www.caac.net/', 'caac', '广州市');
INSERT INTO `university` VALUES ('3270', '广州番禺职业技术学院', '19', '专科', 'http://www.gzpyp.edu.cn/', 'gzpyp', '广州市');
INSERT INTO `university` VALUES ('3271', '广东松山职业技术学院', '19', '专科', 'http://www.gdsspt.net/', 'gdsspt', '韶关市');
INSERT INTO `university` VALUES ('3272', '广东农工商职业技术学院', '19', '专科', 'http://www.gdaib.edu.cn/', 'gdaib', '广州市');
INSERT INTO `university` VALUES ('3273', '广东新安职业技术学院', '19', '专科', 'http://www.gdxa.cn/', 'gdxa', '深圳市');
INSERT INTO `university` VALUES ('3274', '佛山职业技术学院', '19', '专科', 'http://www.fspt.net/', 'fspt', '佛山市');
INSERT INTO `university` VALUES ('3275', '广东科学技术职业学院', '19', '专科', 'http://www.gdit.edu.cn/', 'gdit', '广州市');
INSERT INTO `university` VALUES ('3276', '广东食品药品职业学院', '19', '专科', 'http://www.gdyzy.edu.cn/', 'gdyzy', '广州市');
INSERT INTO `university` VALUES ('3277', '广州康大职业技术学院', '19', '专科', 'http://www.kdvtc-edu.cn/', 'kdvtc-edu', '广州市');
INSERT INTO `university` VALUES ('3278', '珠海艺术职业学院', '19', '专科', 'http://www.zhac.net/', 'zhac', '珠海市');
INSERT INTO `university` VALUES ('3279', '广东行政职业学院', '19', '专科', 'http://www.gdxzzy.cn/', 'gdxzzy', '广州市');
INSERT INTO `university` VALUES ('3280', '广东体育职业技术学院', '19', '专科', 'http://www.gdvcp.net/', 'gdvcp', '广州市');
INSERT INTO `university` VALUES ('3281', '广东职业技术学院', '19', '专科', 'http://www.gdptc.cn/', 'gdptc', '佛山市');
INSERT INTO `university` VALUES ('3282', '广东建设职业技术学院', '19', '专科', 'http://www.gdcvi.net/', 'gdcvi', '广州市');
INSERT INTO `university` VALUES ('3283', '广东女子职业技术学院', '19', '专科', 'http://www.gdfs.edu.cn/', 'gdfs', '广州市');
INSERT INTO `university` VALUES ('3284', '广东机电职业技术学院', '19', '专科', 'http://www.gdmec.com/', 'gdmec', '广州市');
INSERT INTO `university` VALUES ('3285', '广东岭南职业技术学院', '19', '专科', 'http://www.lingnancollege.com.', 'lingnancol', '广州市');
INSERT INTO `university` VALUES ('3286', '汕尾职业技术学院', '19', '专科', 'http://www.swvtc.cn/', 'swvtc', '汕尾市');
INSERT INTO `university` VALUES ('3287', '罗定职业技术学院', '19', '专科', 'http://www.ldpoly.com/', 'ldpoly', '罗定市');
INSERT INTO `university` VALUES ('3288', '阳江职业技术学院', '19', '专科', 'http://www.yjcollege.net/', 'yjcollege', '阳江市');
INSERT INTO `university` VALUES ('3289', '河源职业技术学院', '19', '专科', 'http://www.hycollege.net/', 'hycollege', '河源市');
INSERT INTO `university` VALUES ('3290', '广东邮电职业技术学院', '19', '专科', 'http://www.gupt.net/', 'gupt', '广州市');
INSERT INTO `university` VALUES ('3291', '汕头职业技术学院', '19', '专科', 'http://www.gdwlxy.cn/', 'gdwlxy', '汕头市');
INSERT INTO `university` VALUES ('3292', '揭阳职业技术学院', '19', '专科', 'http://www.jyc.edu.cn/', 'jyc', '揭阳市');
INSERT INTO `university` VALUES ('3293', '深圳信息职业技术学院', '19', '专科', 'http://www.sziit.com.cn/', 'sziit', '深圳市');
INSERT INTO `university` VALUES ('3294', '清远职业技术学院', '19', '专科', 'http://www.qypt.com.cn/', 'qypt', '清远市');
INSERT INTO `university` VALUES ('3295', '广东工贸职业技术学院', '19', '专科', 'http://www.gdgm.cn/', 'gdgm', '广州市');
INSERT INTO `university` VALUES ('3296', '广东司法警官职业学院', '19', '专科', 'http://www.gdsfjy.cn/', 'gdsfjy', '广州市');
INSERT INTO `university` VALUES ('3297', '广东亚视演艺职业学院', '19', '专科', 'http://www.atvcn.com/', 'atvcn', '东莞市');
INSERT INTO `university` VALUES ('3298', '广东省外语艺术职业学院', '19', '专科', 'http://www.gtcfla.net/', 'gtcfla', '广州市');
INSERT INTO `university` VALUES ('3299', '广东文艺职业学院', '19', '专科', 'http://www.gdla.edu.cn/', 'gdla', '广州市');
INSERT INTO `university` VALUES ('3300', '广州体育职业技术学院', '19', '专科', 'http://www.gztzy.edu.cn/', 'gztzy', '广州市');
INSERT INTO `university` VALUES ('3301', '广州工程技术职业学院', '19', '专科', 'http://www.gzvtc.cn/', 'gzvtc', '广州市');
INSERT INTO `university` VALUES ('3302', '中山火炬职业技术学院', '19', '专科', 'http://www.zstp.cn/', 'zstp', '中山市');
INSERT INTO `university` VALUES ('3303', '江门职业技术学院', '19', '专科', 'http://www.jmpt.cn/', 'jmpt', '江门市');
INSERT INTO `university` VALUES ('3304', '茂名职业技术学院', '19', '专科', 'http://www.mmvtc.cn/', 'mmvtc', '茂名市');
INSERT INTO `university` VALUES ('3305', '珠海城市职业技术学院', '19', '专科', 'http://www.zhcpt.edu.cn/', 'zhcpt', '珠海市');
INSERT INTO `university` VALUES ('3306', '广州涉外经济职业技术学院', '19', '专科', 'http://www.gziec.net/', 'gziec', '广州市');
INSERT INTO `university` VALUES ('3307', '广州南洋理工职业学院', '19', '专科', 'http://www.nyjy.cn/', 'nyjy', '广州市');
INSERT INTO `university` VALUES ('3308', '广州科技职业技术学院', '19', '专科', 'http://www.gzkjxy.net/', 'gzkjxy', '广州市');
INSERT INTO `university` VALUES ('3309', '惠州经济职业技术学院', '19', '专科', 'http://www.hzcollege.com/', 'hzcollege', '惠州市');
INSERT INTO `university` VALUES ('3310', '广东工商职业学院', '19', '专科', 'http://www.zqtbu.com/', 'zqtbu', '肇庆市');
INSERT INTO `university` VALUES ('3311', '肇庆医学高等专科学校', '19', '专科', 'http://www.zqmc.net/', 'zqmc', '肇庆市');
INSERT INTO `university` VALUES ('3312', '广州现代信息工程职业技术学院', '19', '专科', 'http://www.gzmodern.cn/', 'gzmodern', '广州市');
INSERT INTO `university` VALUES ('3313', '广东理工职业学院', '19', '专科', 'http://www.gdpi.edu.cn/', 'gdpi', '广州市');
INSERT INTO `university` VALUES ('3314', '广州华南商贸职业学院', '19', '专科', 'http://www.hnsmxy.cn/', 'hnsmxy', '广州市');
INSERT INTO `university` VALUES ('3315', '广州华立科技职业学院', '19', '专科', 'http://www.hlxy.net/', 'hlxy', '广州市');
INSERT INTO `university` VALUES ('3316', '广州城市职业学院', '19', '专科', 'http://www.gcp.edu.cn/', 'gcp', '广州市');
INSERT INTO `university` VALUES ('3317', '广东工程职业技术学院', '19', '专科', 'http://www.gpc.net.cn/', 'gpc', '广州市');
INSERT INTO `university` VALUES ('3318', '广州铁路职业技术学院', '19', '专科', 'http://www.gtxy.cn/', 'gtxy', '广州市');
INSERT INTO `university` VALUES ('3319', '广东科贸职业学院', '19', '专科', 'http://www.gdkm.edu.cn/', 'gdkm', '广州市');
INSERT INTO `university` VALUES ('3320', '广州科技贸易职业学院', '19', '专科', 'http://www.gzkmu.com/', 'gzkmu', '广州市');
INSERT INTO `university` VALUES ('3321', '中山职业技术学院', '19', '专科', 'http://www.zspt.cn/', 'zspt', '中山市');
INSERT INTO `university` VALUES ('3322', '广州珠江职业技术学院', '19', '专科', 'http://www.gzzjedu.cn/', 'gzzjedu', '广州市');
INSERT INTO `university` VALUES ('3323', '广州松田职业学院', '19', '专科', 'http://www.sontanedu.cn/', 'sontanedu', '广州市');
INSERT INTO `university` VALUES ('3324', '广东文理职业学院', '19', '专科', 'http://www.gdwlxy.cn/', 'gdwlxy', '湛江市');
INSERT INTO `university` VALUES ('3325', '广州城建职业学院', '19', '专科', 'http://www.gzccc.edu.cn/', 'gzccc', '广州市');
INSERT INTO `university` VALUES ('3326', '东莞职业技术学院', '19', '专科', 'http://www.dgpt.edu.cn/', 'dgpt', '东莞市');
INSERT INTO `university` VALUES ('3327', '广东南方职业学院', '19', '专科', 'http://www.gdnfu.com/', 'gdnfu', '江门市');
INSERT INTO `university` VALUES ('3328', '广州华商职业学院', '19', '专科', 'http://www.gzhsvc.com/', 'gzhsvc', '广州市');
INSERT INTO `university` VALUES ('3329', '广州华夏职业学院', '19', '专科', 'http://www.gzhxtc.cn/', 'gzhxtc', '广州市');
INSERT INTO `university` VALUES ('3330', '广东环境保护工程职业学院', '19', '专科', 'http://www.gdepc.cn/', 'gdepc', '佛山市');
INSERT INTO `university` VALUES ('3331', '广东青年职业学院', '19', '专科', 'http://www.gdylc.cn/', 'gdylc', '广州市');
INSERT INTO `university` VALUES ('3332', '广州东华职业学院', '19', '专科', 'http://www.gzdhxy.com/', 'gzdhxy', '广州市');
INSERT INTO `university` VALUES ('3333', '广东创新科技职业学院', '19', '专科', 'http://www.gdcxxy.net/', 'gdcxxy', '东莞市');
INSERT INTO `university` VALUES ('3334', '广东舞蹈戏剧职业学院', '19', '专科', 'http://www.gdddc.cn/', 'gdddc', '佛山市');
INSERT INTO `university` VALUES ('3335', '惠州卫生职业技术学院', '19', '专科', 'http://www.hzwx.cn/', 'hzwx', '惠州市');
INSERT INTO `university` VALUES ('3336', '广东信息工程职业学院', '19', '专科', 'http://121.10.238.88/', '10', '肇庆市');
INSERT INTO `university` VALUES ('3337', '广东生态工程职业学院', '19', '专科', 'http://www.gdsty.cn/', 'gdsty', '广州市');
INSERT INTO `university` VALUES ('3338', '惠州城市职业学院', '19', '专科', 'http://www.hzc.edu.cn/', 'hzc', '惠州市');
INSERT INTO `university` VALUES ('3339', '广东碧桂园职业学院', '19', '专科', 'http://www.bgypt.com/', 'bgypt', '清远市');
INSERT INTO `university` VALUES ('3340', '广东酒店管理职业技术学院', '19', '专科', 'http://www.gdjdxy.com/', 'gdjdxy', '东莞市');
INSERT INTO `university` VALUES ('3341', '广东茂名幼儿师范专科学校', '19', '专科', 'http://www.gdgzsf.cn/', 'gdgzsf', '茂名市');
INSERT INTO `university` VALUES ('3342', '广州卫生职业技术学院', '19', '专科', 'http://www.gzws.net/', 'gzws', '广州市');
INSERT INTO `university` VALUES ('3343', '广东江门中医药职业学院', '19', '专科', 'http://www.gdjmcmc.com/', 'gdjmcmc', '江门市');
INSERT INTO `university` VALUES ('3344', '湛江市幼儿师范学校', '19', '专科', 'http://www.zjys.net/', 'zjys', '湛江市');
INSERT INTO `university` VALUES ('3345', '广西大学', '20', '本科', 'http://www.gxu.edu.cn/', 'gxu', '南宁市');
INSERT INTO `university` VALUES ('3346', '广西科技大学', '20', '本科', 'http://www.gxut.edu.cn/', 'gxut', '柳州市');
INSERT INTO `university` VALUES ('3347', '桂林电子科技大学', '20', '本科', 'http://www.gliet.edu.cn/', 'gliet', '桂林市');
INSERT INTO `university` VALUES ('3348', '桂林理工大学', '20', '本科', 'http://www.glite.edu.cn/', 'glite', '桂林市');
INSERT INTO `university` VALUES ('3349', '广西医科大学', '20', '本科', 'http://www.gxmu.edu.cn/', 'gxmu', '南宁市');
INSERT INTO `university` VALUES ('3350', '右江民族医学院', '20', '本科', 'http://www.ymcn.gx.cn/', 'ymcn', '百色市');
INSERT INTO `university` VALUES ('3351', '广西中医药大学', '20', '本科', 'http://www.gxtcmu.edu.cn/', 'gxtcmu', '南宁市');
INSERT INTO `university` VALUES ('3352', '桂林医学院', '20', '本科', 'http://www.glmc.edu.cn/', 'glmc', '桂林市');
INSERT INTO `university` VALUES ('3353', '广西师范大学', '20', '本科', 'http://www.gxnu.edu.cn/', 'gxnu', '桂林市');
INSERT INTO `university` VALUES ('3354', '广西师范学院', '20', '本科', 'http://www.gxtc.edu.cn/', 'gxtc', '南宁市');
INSERT INTO `university` VALUES ('3355', '广西民族师范学院', '20', '本科', 'http://www.gxnun.net/', 'gxnun', '崇左市');
INSERT INTO `university` VALUES ('3356', '河池学院', '20', '本科', 'http://www.hcnu.edu.cn/', 'hcnu', '宜州市');
INSERT INTO `university` VALUES ('3357', '玉林师范学院', '20', '本科', 'http://www.ylu.edu.cn/', 'ylu', '玉林市');
INSERT INTO `university` VALUES ('3358', '广西艺术学院', '20', '本科', 'http://www.gxau.edu.cn/', 'gxau', '南宁市');
INSERT INTO `university` VALUES ('3359', '广西民族大学', '20', '本科', 'http://www.gxun.edu.cn/', 'gxun', '南宁市');
INSERT INTO `university` VALUES ('3360', '百色学院', '20', '本科', 'http://www.bsuc.cn/', 'bsuc', '百色市');
INSERT INTO `university` VALUES ('3361', '梧州学院', '20', '本科', 'http://www.gxuwz.edu.cn/', 'gxuwz', '梧州市');
INSERT INTO `university` VALUES ('3362', '广西科技师范学院', '20', '本科', 'http://www.gxlztc.net/', 'gxlztc', '来宾市');
INSERT INTO `university` VALUES ('3363', '广西财经学院', '20', '本科', 'http://www.gxufe.cn/', 'gxufe', '南宁市');
INSERT INTO `university` VALUES ('3364', '南宁学院', '20', '本科', 'http://www.nnxy.cn/', 'nnxy', '南宁市');
INSERT INTO `university` VALUES ('3365', '钦州学院', '20', '本科', 'http://www.qzu.net.cn/', 'qzu', '钦州市');
INSERT INTO `university` VALUES ('3366', '桂林航天工业学院', '20', '本科', 'http://www.guat.edu.cn/', 'guat', '桂林市');
INSERT INTO `university` VALUES ('3367', '桂林旅游学院', '20', '本科', 'http://www.glit.cn/', 'glit', '桂林市');
INSERT INTO `university` VALUES ('3368', '贺州学院', '20', '本科', 'http://www.hzu.gx.cn/', 'hzu', '贺州市');
INSERT INTO `university` VALUES ('3369', '广西警察学院', '20', '本科', 'http://www.gagx.com.cn/', 'gagx', '南宁市');
INSERT INTO `university` VALUES ('3370', '北海艺术设计学院', '20', '本科', 'http://www.sszss.com/', 'sszss', '北海市');
INSERT INTO `university` VALUES ('3371', '广西大学行健文理学院', '20', '本科', 'http://xingjian.gxu.edu.cn/', 'gxu', '南宁市');
INSERT INTO `university` VALUES ('3372', '广西科技大学鹿山学院', '20', '本科', 'http://www.lzls.gxut.edu.cn/', 'lzls', '柳州市');
INSERT INTO `university` VALUES ('3373', '广西民族大学相思湖学院', '20', '本科', 'http://xshxy.gxun.edu.cn/', 'gxun', '南宁市');
INSERT INTO `university` VALUES ('3374', '广西师范大学漓江学院', '20', '本科', 'http://www.gxljcollege.cn/', 'gxljcolleg', '桂林市');
INSERT INTO `university` VALUES ('3375', '广西师范学院师园学院', '20', '本科', 'http://www.gxsyu.com/', 'gxsyu', '南宁市');
INSERT INTO `university` VALUES ('3376', '广西中医学院赛恩斯新医药学院', '20', '本科', 'http://www.gxzyxysy.com/', 'gxzyxysy', '南宁市');
INSERT INTO `university` VALUES ('3377', '桂林电子科技大学信息科技学院', '20', '本科', 'http://iit.guet.edu.cn/', 'guet', '桂林市');
INSERT INTO `university` VALUES ('3378', '桂林理工大学博文管理学院', '20', '本科', 'http://bwgl.glite.edu.cn/', 'glite', '桂林市');
INSERT INTO `university` VALUES ('3379', '广西外国语学院', '20', '本科', 'http://www.gxufl.com/', 'gxufl', '南宁市');
INSERT INTO `university` VALUES ('3380', '北京航空航天大学北海学院', '20', '本科', 'http://www.bhbhxy.com/', 'bhbhxy', '北海市');
INSERT INTO `university` VALUES ('3381', '广西机电职业技术学院', '20', '专科', 'http://www.gxcme.edu.cn/', 'gxcme', '南宁市');
INSERT INTO `university` VALUES ('3382', '广西体育高等专科学校', '20', '专科', 'http://www.gxtznn.com/', 'gxtznn', '南宁市');
INSERT INTO `university` VALUES ('3383', '南宁职业技术学院', '20', '专科', 'http://www.ncvt.net', 'ncvt', '南宁市');
INSERT INTO `university` VALUES ('3384', '广西水利电力职业技术学院', '20', '专科', 'http://www.gxsdxy.cn/', 'gxsdxy', '南宁市');
INSERT INTO `university` VALUES ('3385', '桂林师范高等专科学校', '20', '专科', 'http://www.glnc.edu.cn/', 'glnc', '桂林市');
INSERT INTO `university` VALUES ('3386', '广西职业技术学院', '20', '专科', 'http://www.gxzjy.com/', 'gxzjy', '南宁市');
INSERT INTO `university` VALUES ('3387', '柳州职业技术学院', '20', '专科', 'http://www.lzzy.net/', 'lzzy', '柳州市');
INSERT INTO `university` VALUES ('3388', '广西生态工程职业技术学院', '20', '专科', 'http://www.gxstzy.cn/', 'gxstzy', '柳州市');
INSERT INTO `university` VALUES ('3389', '广西交通职业技术学院', '20', '专科', 'http://www.gxjzy.com/', 'gxjzy', '南宁市');
INSERT INTO `university` VALUES ('3390', '广西工业职业技术学院', '20', '专科', 'http://www.gxic.net/', 'gxic', '南宁市');
INSERT INTO `university` VALUES ('3391', '广西国际商务职业技术学院', '20', '专科', 'http://www.gxibvc.net/', 'gxibvc', '南宁市');
INSERT INTO `university` VALUES ('3392', '广西农业职业技术学院', '20', '专科', 'http://www.gxnyxy.com.cn/', 'gxnyxy', '南宁市');
INSERT INTO `university` VALUES ('3393', '柳州铁道职业技术学院', '20', '专科', 'http://www.lztdzy.com/', 'lztdzy', '柳州市');
INSERT INTO `university` VALUES ('3394', '广西建设职业技术学院', '20', '专科', 'http://www.gxjsxy.cn/', 'gxjsxy', '南宁市');
INSERT INTO `university` VALUES ('3395', '广西现代职业技术学院', '20', '专科', 'http://www.gxxd.net.cn/', 'gxxd', '河池市');
INSERT INTO `university` VALUES ('3396', '北海职业学院', '20', '专科', 'http://www.bhzyxy.net/', 'bhzyxy', '北海市');
INSERT INTO `university` VALUES ('3397', '桂林山水职业学院', '20', '专科', 'http://www.guolianweb.com/', 'guolianweb', '桂林市');
INSERT INTO `university` VALUES ('3398', '广西经贸职业技术学院', '20', '专科', 'http://www.gxjmzy.com/', 'gxjmzy', '南宁市');
INSERT INTO `university` VALUES ('3399', '广西工商职业技术学院', '20', '专科', 'http://www.gxgsxy.com/', 'gxgsxy', '南宁市');
INSERT INTO `university` VALUES ('3400', '广西演艺职业学院', '20', '专科', 'http://www.gxart.cn/', 'gxart', '南宁市');
INSERT INTO `university` VALUES ('3401', '广西电力职业技术学院', '20', '专科', 'http://www.gxdlxy.com/', 'gxdlxy', '南宁市');
INSERT INTO `university` VALUES ('3402', '广西城市职业学院', '20', '专科', 'http://www.gxccedu.com/', 'gxccedu', '扶绥县');
INSERT INTO `university` VALUES ('3403', '广西英华国际职业学院', '20', '专科', 'http://www.tic-gx.com/', 'tic-gx', '钦州市');
INSERT INTO `university` VALUES ('3404', '柳州城市职业学院', '20', '专科', 'http://www.lcvc.cn/', 'lcvc', '柳州市');
INSERT INTO `university` VALUES ('3405', '百色职业学院', '20', '专科', 'http://www.bszyxy.com/', 'bszyxy', '百色市');
INSERT INTO `university` VALUES ('3406', '广西工程职业学院', '20', '专科', 'http://www.gxgcedu.com/', 'gxgcedu', '百色市');
INSERT INTO `university` VALUES ('3407', '广西理工职业技术学院', '20', '专科', 'http://www.gxlgxy.com/', 'gxlgxy', '崇左市');
INSERT INTO `university` VALUES ('3408', '梧州职业学院', '20', '专科', 'http://www.wzzyedu.com/', 'wzzyedu', '梧州市');
INSERT INTO `university` VALUES ('3409', '广西经济职业学院', '20', '专科', 'http://www.gxevc.com/', 'gxevc', '南宁市');
INSERT INTO `university` VALUES ('3410', '广西幼儿师范高等专科学校', '20', '专科', 'http://www.gxyesf.com/', 'gxyesf', '南宁市');
INSERT INTO `university` VALUES ('3411', '广西科技职业学院', '20', '专科', 'http://www.gxkjzy.com/', 'gxkjzy', '南宁市');
INSERT INTO `university` VALUES ('3412', '广西卫生职业技术学院', '20', '专科', 'http://www.gxwzy.com.cn/', 'gxwzy', '南宁市');
INSERT INTO `university` VALUES ('3413', '广西培贤国际职业学院', '20', '专科', 'http://xxsjxkj.ha185.cn/', 'ha185', '百色市');
INSERT INTO `university` VALUES ('3414', '广西金融职业技术学院', '20', '专科', 'http://www.gxjrxy.com/', 'gxjrxy', '南宁市');
INSERT INTO `university` VALUES ('3415', '广西蓝天航空职业学院', '20', '专科', 'http://www.gxltu.com/', 'gxltu', '来宾市');
INSERT INTO `university` VALUES ('3416', '广西安全工程职业技术学院', '20', '专科', 'http://www.gxazy.com/', 'gxazy', '南宁市');
INSERT INTO `university` VALUES ('3417', '海南大学', '21', '本科', 'http://www.hainu.edu.cn/', 'hainu', '海口市');
INSERT INTO `university` VALUES ('3418', '海南热带海洋学院', '21', '本科', 'http://www.qzu.edu.cn/', 'qzu', '三亚市');
INSERT INTO `university` VALUES ('3419', '海南师范大学', '21', '本科', 'http://www.hainnu.edu.cn/', 'hainnu', '海口市');
INSERT INTO `university` VALUES ('3420', '海南医学院', '21', '本科', 'http://www.hainmc.edu.cn/', 'hainmc', '海口市');
INSERT INTO `university` VALUES ('3421', '海口经济学院', '21', '本科', 'http://www.hkc.edu.cn/', 'hkc', '海口市');
INSERT INTO `university` VALUES ('3422', '琼台师范学院', '21', '本科', 'http://www.qttc.edu.cn/', 'qttc', '海口市');
INSERT INTO `university` VALUES ('3423', '三亚学院', '21', '本科', 'http://www.sanyau.edu.cn/', 'sanyau', '三亚市');
INSERT INTO `university` VALUES ('3424', '海南职业技术学院', '21', '专科', 'http://www.hcvt.cn/', 'hcvt', '海口市');
INSERT INTO `university` VALUES ('3425', '三亚城市职业学院', '21', '专科', 'http://www.sycsxy.cn/', 'sycsxy', '三亚市');
INSERT INTO `university` VALUES ('3426', '海南软件职业技术学院', '21', '专科', 'http://www.hnspi.edu.cn/', 'hnspi', '琼海市');
INSERT INTO `university` VALUES ('3427', '海南政法职业学院', '21', '专科', 'http://www.hnplc.com/', 'hnplc', '海口市');
INSERT INTO `university` VALUES ('3428', '海南外国语职业学院', '21', '专科', 'http://www.hnflvc.com/', 'hnflvc', '文昌市');
INSERT INTO `university` VALUES ('3429', '海南经贸职业技术学院', '21', '专科', 'http://www.hnjmc.com/', 'hnjmc', '海口市');
INSERT INTO `university` VALUES ('3430', '海南工商职业学院', '21', '专科', 'http://www.hntbc.edu.cn/', 'hntbc', '海口市');
INSERT INTO `university` VALUES ('3431', '三亚航空旅游职业学院', '21', '专科', 'http://www.hnasatc.com/', 'hnasatc', '三亚市');
INSERT INTO `university` VALUES ('3432', '海南科技职业学院', '21', '专科', 'http://www.hnkjedu.cn/', 'hnkjedu', '海口市');
INSERT INTO `university` VALUES ('3433', '三亚理工职业学院', '21', '专科', 'http://www.ucsanya.com/', 'ucsanya', '三亚市');
INSERT INTO `university` VALUES ('3434', '海南体育职业技术学院', '21', '专科', 'http://www.hnstx.com/', 'hnstx', '海口市');
INSERT INTO `university` VALUES ('3435', '三亚中瑞酒店管理职业学院', '21', '专科', 'http://www.his-edu.cn/', 'his-edu', '三亚市');
INSERT INTO `university` VALUES ('3436', '重庆大学', '22', '本科', 'http://www.cqu.edu.cn/', 'cqu', '重庆市');
INSERT INTO `university` VALUES ('3437', '重庆邮电大学', '22', '本科', 'http://www.cqupt.edu.cn/', 'cqupt', '重庆市');
INSERT INTO `university` VALUES ('3438', '重庆交通大学', '22', '本科', 'http://www.cqjtu.edu.cn/', 'cqjtu', '重庆市');
INSERT INTO `university` VALUES ('3439', '重庆医科大学', '22', '本科', 'http://www.cqmu.edu.cn/', 'cqmu', '重庆市');
INSERT INTO `university` VALUES ('3440', '西南大学', '22', '本科', 'http://www.swu.edu.cn/', 'swu', '重庆市');
INSERT INTO `university` VALUES ('3441', '重庆师范大学', '22', '本科', 'http://www.cqnu.edu.cn/', 'cqnu', '重庆市');
INSERT INTO `university` VALUES ('3442', '重庆文理学院', '22', '本科', 'http://www.cqwu.net/', 'cqwu', '重庆市');
INSERT INTO `university` VALUES ('3443', '重庆三峡学院', '22', '本科', 'http://www.sanxiau.net/', 'sanxiau', '重庆市');
INSERT INTO `university` VALUES ('3444', '长江师范学院', '22', '本科', 'http://www.yznu.cn/', 'yznu', '重庆市');
INSERT INTO `university` VALUES ('3445', '四川外国语大学', '22', '本科', 'http://www.sisu.edu.cn/', 'sisu', '重庆市');
INSERT INTO `university` VALUES ('3446', '西南政法大学', '22', '本科', 'http://www.swupl.edu.cn/', 'swupl', '重庆市');
INSERT INTO `university` VALUES ('3447', '四川美术学院', '22', '本科', 'http://www.scfai.edu.cn/', 'scfai', '重庆市');
INSERT INTO `university` VALUES ('3448', '重庆科技学院', '22', '本科', 'http://www.cqust.edu.cn/', 'cqust', '重庆市');
INSERT INTO `university` VALUES ('3449', '重庆理工大学', '22', '本科', 'http://www.cqut.edu.cn/', 'cqut', '重庆市');
INSERT INTO `university` VALUES ('3450', '重庆工商大学', '22', '本科', 'http://www.ctbu.edu.cn/', 'ctbu', '重庆市');
INSERT INTO `university` VALUES ('3451', '重庆工程学院', '22', '本科', 'http://www.cqgcxy.com/', 'cqgcxy', '重庆市');
INSERT INTO `university` VALUES ('3452', '重庆大学城市科技学院', '22', '本科', 'http://www.cqucc.com.cn/', 'cqucc', '重庆市');
INSERT INTO `university` VALUES ('3453', '重庆警察学院', '22', '本科', 'http://www.cqjy.com.cn/', 'cqjy', '重庆市');
INSERT INTO `university` VALUES ('3454', '重庆人文科技学院', '22', '本科', 'http://www.swuyc.edu.cn/', 'swuyc', '重庆市');
INSERT INTO `university` VALUES ('3455', '四川外语学院重庆南方翻译学院', '22', '本科', 'http://www.tcsisu.com/', 'tcsisu', '重庆市');
INSERT INTO `university` VALUES ('3456', '重庆师范大学涉外商贸学院', '22', '本科', 'http://www.swsm.cn/', 'swsm', '重庆市');
INSERT INTO `university` VALUES ('3457', '重庆工商大学融智学院', '22', '本科', 'http://www.cqrzedu.cn/', 'cqrzedu', '重庆市');
INSERT INTO `university` VALUES ('3458', '重庆工商大学派斯学院', '22', '本科', 'http://paisi.ctbu.edu.cn/', 'ctbu', '重庆市');
INSERT INTO `university` VALUES ('3459', '重庆邮电大学移通学院', '22', '本科', 'http://www.cqyti.com/', 'cqyti', '重庆市');
INSERT INTO `university` VALUES ('3460', '重庆第二师范学院', '22', '本科', 'http://www.cque.edu.cn/', 'cque', '重庆市');
INSERT INTO `university` VALUES ('3461', '重庆航天职业技术学院', '22', '专科', 'http://www.cqepc.cn/', 'cqepc', '重庆市');
INSERT INTO `university` VALUES ('3462', '重庆电力高等专科学校', '22', '专科', 'http://www.cqepc.com.cn/', 'cqepc', '重庆市');
INSERT INTO `university` VALUES ('3463', '重庆工业职业技术学院', '22', '专科', 'http://www.cqipc.net/', 'cqipc', '重庆市');
INSERT INTO `university` VALUES ('3464', '重庆三峡职业学院', '22', '专科', 'http://www.cqsxedu.com/', 'cqsxedu', '重庆市');
INSERT INTO `university` VALUES ('3465', '重庆工贸职业技术学院', '22', '专科', 'http://www.cqgmy.cn/', 'cqgmy', '重庆市');
INSERT INTO `university` VALUES ('3466', '重庆机电职业技术学院', '22', '专科', 'http://www.cqevi.net.cn/', 'cqevi', '重庆市');
INSERT INTO `university` VALUES ('3467', '重庆电子工程职业学院', '22', '专科', 'http://www.cqcet.com/', 'cqcet', '重庆市');
INSERT INTO `university` VALUES ('3468', '重庆海联职业技术学院', '22', '专科', 'http://www.cqhl.net.cn/', 'cqhl', '重庆市');
INSERT INTO `university` VALUES ('3469', '重庆信息技术职业学院', '22', '专科', 'http://www.cqeec.com/', 'cqeec', '重庆市');
INSERT INTO `university` VALUES ('3470', '重庆传媒职业学院', '22', '专科', 'http://www.cqcmxy.com/', 'cqcmxy', '重庆市');
INSERT INTO `university` VALUES ('3471', '重庆城市管理职业学院', '22', '专科', 'http://www.cswu.cn/', 'cswu', '重庆市');
INSERT INTO `university` VALUES ('3472', '重庆工程职业技术学院', '22', '专科', 'http://www.cqvie.edu.cn/', 'cqvie', '重庆市');
INSERT INTO `university` VALUES ('3473', '重庆房地产职业学院', '22', '专科', 'http://www.cqbyxy.com/', 'cqbyxy', '重庆市');
INSERT INTO `university` VALUES ('3474', '重庆城市职业学院', '22', '专科', 'http://www.cqcvc.com.cn/', 'cqcvc', '重庆市');
INSERT INTO `university` VALUES ('3475', '重庆水利电力职业技术学院', '22', '专科', 'http://www.cqsdzy.com/', 'cqsdzy', '重庆市');
INSERT INTO `university` VALUES ('3476', '重庆工商职业学院', '22', '专科', 'http://www.cqtbi.edu.cn/', 'cqtbi', '重庆市');
INSERT INTO `university` VALUES ('3477', '重庆应用技术职业学院', '22', '专科', 'http://www.cqms.edu.cn/', 'cqms', '重庆市');
INSERT INTO `university` VALUES ('3478', '重庆三峡医药高等专科学校', '22', '专科', 'http://www.sxyyc.net/', 'sxyyc', '重庆市');
INSERT INTO `university` VALUES ('3479', '重庆医药高等专科学校', '22', '专科', 'http://www.cqyygz.com/', 'cqyygz', '重庆市');
INSERT INTO `university` VALUES ('3480', '重庆青年职业技术学院', '22', '专科', 'http://www.cqqzy.cn/', 'cqqzy', '重庆市');
INSERT INTO `university` VALUES ('3481', '重庆财经职业学院', '22', '专科', 'http://www.cqcfe.com/', 'cqcfe', '重庆市');
INSERT INTO `university` VALUES ('3482', '重庆科创职业学院', '22', '专科', 'http://www.cqie.cn/', 'cqie', '重庆市');
INSERT INTO `university` VALUES ('3483', '重庆建筑工程职业学院', '22', '专科', 'http://www.cctc.cq.cn/', 'cctc', '重庆市');
INSERT INTO `university` VALUES ('3484', '重庆电讯职业学院', '22', '专科', 'http://www.cqdxxy.com.cn/', 'cqdxxy', '重庆市');
INSERT INTO `university` VALUES ('3485', '重庆能源职业学院', '22', '专科', 'http://www.cqny.net/', 'cqny', '重庆市');
INSERT INTO `university` VALUES ('3486', '重庆商务职业学院', '22', '专科', 'http://www.cqswxy.cn/', 'cqswxy', '重庆市');
INSERT INTO `university` VALUES ('3487', '重庆交通职业学院', '22', '专科', 'http://www.cqjky.com/', 'cqjky', '重庆市');
INSERT INTO `university` VALUES ('3488', '重庆化工职业学院', '22', '专科', 'http://www.cqhgzy.com/', 'cqhgzy', '重庆市');
INSERT INTO `university` VALUES ('3489', '重庆旅游职业学院', '22', '专科', 'http://www.cqvit.com/', 'cqvit', '重庆市');
INSERT INTO `university` VALUES ('3490', '重庆安全技术职业学院', '22', '专科', 'http://www.cqvist.net/', 'cqvist', '重庆市');
INSERT INTO `university` VALUES ('3491', '重庆公共运输职业学院', '22', '专科', 'http://www.cqgyzy.com/', 'cqgyzy', '重庆市');
INSERT INTO `university` VALUES ('3492', '重庆艺术工程职业学院', '22', '专科', 'http://www.cqysxy.com/', 'cqysxy', '重庆市');
INSERT INTO `university` VALUES ('3493', '重庆轻工职业学院', '22', '专科', 'http://www.cqivc.com/', 'cqivc', '重庆市');
INSERT INTO `university` VALUES ('3494', '重庆电信职业学院', '22', '专科', 'http://www.cqtcedu.com/', 'cqtcedu', '重庆市');
INSERT INTO `university` VALUES ('3495', '重庆经贸职业学院', '22', '专科', 'http://www.cqvcet.com/', 'cqvcet', '重庆市');
INSERT INTO `university` VALUES ('3496', '重庆幼儿师范高等专科学校', '22', '专科', 'http://www.cqsxsf.com/', 'cqsxsf', '重庆市');
INSERT INTO `university` VALUES ('3497', '重庆文化艺术职业学院', '22', '专科', 'http://www.cqyixiao.com/', 'cqyixiao', '重庆市');
INSERT INTO `university` VALUES ('3498', '重庆科技职业学院', '22', '专科', 'http://www.cqfzgc.com/', 'cqfzgc', '重庆市');
INSERT INTO `university` VALUES ('3499', '重庆资源与环境保护职业学院', '22', '专科', 'http://www.cqcjxy.com/', 'cqcjxy', '重庆市');
INSERT INTO `university` VALUES ('3500', '四川大学', '23', '本科', 'http://www.scu.edu.cn/', 'scu', '成都市');
INSERT INTO `university` VALUES ('3501', '西南交通大学', '23', '本科', 'http://www.swjtu.edu.cn/', 'swjtu', '成都市');
INSERT INTO `university` VALUES ('3502', '电子科技大学', '23', '本科', 'http://www.uestc.edu.cn/', 'uestc', '成都市');
INSERT INTO `university` VALUES ('3503', '西南石油大学', '23', '本科', 'http://www.swpu.edu.cn/', 'swpu', '成都市');
INSERT INTO `university` VALUES ('3504', '成都理工大学', '23', '本科', 'http://www.cdut.edu.cn/', 'cdut', '成都市');
INSERT INTO `university` VALUES ('3505', '西南科技大学', '23', '本科', 'http://www.swust.edu.cn/', 'swust', '绵阳市');
INSERT INTO `university` VALUES ('3506', '成都信息工程大学', '23', '本科', 'http://www.cuit.edu.cn/', 'cuit', '成都市');
INSERT INTO `university` VALUES ('3507', '四川理工学院', '23', '本科', 'http://www.suse.edu.cn/', 'suse', '自贡市');
INSERT INTO `university` VALUES ('3508', '西华大学', '23', '本科', 'http://www.xhu.edu.cn/', 'xhu', '成都市');
INSERT INTO `university` VALUES ('3509', '中国民用航空飞行学院', '23', '本科', 'http://www.cafuc.edu.cn/', 'cafuc', '广汉市');
INSERT INTO `university` VALUES ('3510', '四川农业大学', '23', '本科', 'http://www.sicau.edu.cn/', 'sicau', '雅安市');
INSERT INTO `university` VALUES ('3511', '西昌学院', '23', '本科', 'http://www.xcc.sc.cn/', 'xcc', '西昌市');
INSERT INTO `university` VALUES ('3512', '西南医科大学', '23', '本科', 'http://www.scmu.edu.cn/', 'scmu', '泸州市');
INSERT INTO `university` VALUES ('3513', '成都中医药大学', '23', '本科', 'http://www.cdutcm.edu.cn/', 'cdutcm', '成都市');
INSERT INTO `university` VALUES ('3514', '川北医学院', '23', '本科', 'http://www.nsmc.edu.cn/', 'nsmc', '南充市');
INSERT INTO `university` VALUES ('3515', '四川师范大学', '23', '本科', 'http://www.sicnu.edu.cn/', 'sicnu', '成都市');
INSERT INTO `university` VALUES ('3516', '西华师范大学', '23', '本科', 'http://www.cwnu.edu.cn/', 'cwnu', '南充市');
INSERT INTO `university` VALUES ('3517', '绵阳师范学院', '23', '本科', 'http://www.mytc.edu.cn/', 'mytc', '绵阳市');
INSERT INTO `university` VALUES ('3518', '内江师范学院', '23', '本科', 'http://www.njtc.edu.cn/', 'njtc', '内江市');
INSERT INTO `university` VALUES ('3519', '宜宾学院', '23', '本科', 'http://www.yibinu.cn/', 'yibinu', '宜宾市');
INSERT INTO `university` VALUES ('3520', '四川文理学院', '23', '本科', 'http://www.sasu.edu.cn/', 'sasu', '达州市');
INSERT INTO `university` VALUES ('3521', '阿坝师范学院', '23', '本科', 'http://www.abtc.edu.cn/', 'abtc', '汶川县');
INSERT INTO `university` VALUES ('3522', '乐山师范学院', '23', '本科', 'http://www.lstc.edu.cn/', 'lstc', '乐山市');
INSERT INTO `university` VALUES ('3523', '西南财经大学', '23', '本科', 'http://www.swufe.edu.cn/', 'swufe', '成都市');
INSERT INTO `university` VALUES ('3524', '成都体育学院', '23', '本科', 'http://www.cdsu.edu.cn/', 'cdsu', '成都市');
INSERT INTO `university` VALUES ('3525', '四川音乐学院', '23', '本科', 'http://www.sccm.cn/', 'sccm', '成都市');
INSERT INTO `university` VALUES ('3526', '西南民族大学', '23', '本科', 'http://www.swun.edu.cn/', 'swun', '成都市');
INSERT INTO `university` VALUES ('3527', '成都学院', '23', '本科', 'http://www.cdu.edu.cn/', 'cdu', '成都市');
INSERT INTO `university` VALUES ('3528', '成都工业学院', '23', '本科', 'http://www.cdtu.edu.cn/', 'cdtu', '成都市');
INSERT INTO `university` VALUES ('3529', '攀枝花学院', '23', '本科', 'http://www.pzhu.edu.cn/', 'pzhu', '攀枝花');
INSERT INTO `university` VALUES ('3530', '四川旅游学院', '23', '本科', 'http://www.sctu.edu.cn/', 'sctu', '成都市');
INSERT INTO `university` VALUES ('3531', '四川民族学院', '23', '本科', 'http://www.scun.edu.cn/', 'scun', '康定市');
INSERT INTO `university` VALUES ('3532', '四川警察学院', '23', '本科', 'http://www.scpolicec.com/', 'scpolicec', '泸州市');
INSERT INTO `university` VALUES ('3533', '成都东软学院', '23', '本科', 'http://www.nsu.edu.cn/', 'nsu', '成都市');
INSERT INTO `university` VALUES ('3534', '电子科技大学成都学院', '23', '本科', 'http://www.gtsoft.com.cn/', 'gtsoft', '成都市');
INSERT INTO `university` VALUES ('3535', '成都理工大学工程技术学院', '23', '本科', 'http://www.cdutetc.cn/', 'cdutetc', '乐山市');
INSERT INTO `university` VALUES ('3536', '四川传媒学院', '23', '本科', 'http://www.scmc.edu.cn/', 'scmc', '成都市');
INSERT INTO `university` VALUES ('3537', '成都信息工程大学银杏酒店管理学院', '23', '本科', 'http://www.yihms.com/', 'yihms', '成都市');
INSERT INTO `university` VALUES ('3538', '成都文理学院', '23', '本科', 'http://www.cdcas.edu.cn/', 'cdcas', '成都市');
INSERT INTO `university` VALUES ('3539', '四川工商学院', '23', '本科', 'http://www.stbu.edu.cn/', 'stbu', '成都市');
INSERT INTO `university` VALUES ('3540', '四川外国语大学成都学院', '23', '本科', 'http://www.cisisu.edu.cn/', 'cisisu', '都江堰');
INSERT INTO `university` VALUES ('3541', '成都医学院', '23', '本科', 'http://www.cmc.edu.cn/', 'cmc', '成都市');
INSERT INTO `university` VALUES ('3542', '四川工业科技学院', '23', '本科', 'http://www.scit.cn/', 'scit', '德阳市');
INSERT INTO `university` VALUES ('3543', '四川大学锦城学院', '23', '本科', 'http://www.scujcc.com.cn/', 'scujcc', '成都市');
INSERT INTO `university` VALUES ('3544', '西南财经大学天府学院', '23', '本科', 'http://www.tf-swufe.net/', 'tf-swufe', '绵阳市');
INSERT INTO `university` VALUES ('3545', '四川大学锦江学院', '23', '本科', 'http://www.scujj.com/', 'scujj', '成都市');
INSERT INTO `university` VALUES ('3546', '四川文化艺术学院', '23', '本科', 'http://www.cymy.edu.cn/', 'cymy', '绵阳市');
INSERT INTO `university` VALUES ('3547', '西南科技大学城市学院', '23', '本科', 'http://www.ccswust.com.cn/', 'ccswust', '绵阳市');
INSERT INTO `university` VALUES ('3548', '西南交通大学希望学院', '23', '本科', 'http://www.swjtuhc.cn/', 'swjtuhc', '南充市');
INSERT INTO `university` VALUES ('3549', '成都师范学院', '23', '本科', 'http://www.cdnu.edu.cn/', 'cdnu', '成都市');
INSERT INTO `university` VALUES ('3550', '四川电影电视学院', '23', '本科', 'http://www.scftvc.com/', 'scftvc', '成都市');
INSERT INTO `university` VALUES ('3551', '成都纺织高等专科学校', '23', '专科', 'http://www.cdtc.edu.cn/', 'cdtc', '成都市');
INSERT INTO `university` VALUES ('3552', '民办四川天一学院', '23', '专科', 'http://www.tianyi.org/', 'tianyi', '成都市');
INSERT INTO `university` VALUES ('3553', '成都航空职业技术学院', '23', '专科', 'http://www.cdavtc.edu.cn/', 'cdavtc', '成都市');
INSERT INTO `university` VALUES ('3554', '四川电力职业技术学院', '23', '专科', 'http://www.scdy.edu.cn/', 'scdy', '成都市');
INSERT INTO `university` VALUES ('3555', '成都职业技术学院', '23', '专科', 'http://www.cdvtc.com/', 'cdvtc', '成都市');
INSERT INTO `university` VALUES ('3556', '四川化工职业技术学院', '23', '专科', 'http://www.sccvtc.cn/', 'sccvtc', '泸州市');
INSERT INTO `university` VALUES ('3557', '四川水利职业技术学院', '23', '专科', 'http://www.swcvc.net.cn/', 'swcvc', '成都市');
INSERT INTO `university` VALUES ('3558', '南充职业技术学院', '23', '专科', 'http://www.nczy.com/', 'nczy', '南充市');
INSERT INTO `university` VALUES ('3559', '内江职业技术学院', '23', '专科', 'http://www.njvtc.cn/', 'njvtc', '内江市');
INSERT INTO `university` VALUES ('3560', '四川航天职业技术学院', '23', '专科', 'http://www.sacvt.com/', 'sacvt', '成都市');
INSERT INTO `university` VALUES ('3561', '四川邮电职业技术学院', '23', '专科', 'http://www.sptpc.com/', 'sptpc', '成都市');
INSERT INTO `university` VALUES ('3562', '四川机电职业技术学院', '23', '专科', 'http://www.scemi.com/', 'scemi', '攀枝花市');
INSERT INTO `university` VALUES ('3563', '绵阳职业技术学院', '23', '专科', 'http://www.myvtc.edu.cn/', 'myvtc', '绵阳市');
INSERT INTO `university` VALUES ('3564', '四川交通职业技术学院', '23', '专科', 'http://www.svtcc.net/', 'svtcc', '成都市');
INSERT INTO `university` VALUES ('3565', '四川工商职业技术学院', '23', '专科', 'http://www.sctbc.net/', 'sctbc', '都江堰');
INSERT INTO `university` VALUES ('3566', '四川工程职业技术学院', '23', '专科', 'http://www.scetc.net/', 'scetc', '德阳市');
INSERT INTO `university` VALUES ('3567', '四川建筑职业技术学院', '23', '专科', 'http://www.scatc.net/', 'scatc', '德阳市');
INSERT INTO `university` VALUES ('3568', '达州职业技术学院', '23', '专科', 'http://www.dzzjy.com/', 'dzzjy', '达州市');
INSERT INTO `university` VALUES ('3569', '四川托普信息技术职业学院', '23', '专科', 'http://www.scetop.com/', 'scetop', '成都市');
INSERT INTO `university` VALUES ('3570', '四川国际标榜职业学院', '23', '专科', 'http://www.polus.edu.cn/', 'polus', '成都市');
INSERT INTO `university` VALUES ('3571', '成都农业科技职业学院', '23', '专科', 'http://www.cdnkxy.com/', 'cdnkxy', '成都市');
INSERT INTO `university` VALUES ('3572', '宜宾职业技术学院', '23', '专科', 'http://www.ybzy.cn/', 'ybzy', '宜宾市');
INSERT INTO `university` VALUES ('3573', '泸州职业技术学院', '23', '专科', 'http://www.lzy.edu.cn/', 'lzy', '泸州市');
INSERT INTO `university` VALUES ('3574', '眉山职业技术学院', '23', '专科', 'http://www.msvtc.net/', 'msvtc', '眉山市');
INSERT INTO `university` VALUES ('3575', '成都艺术职业学院', '23', '专科', 'http://www.cdartpro.cn/', 'cdartpro', '成都市');
INSERT INTO `university` VALUES ('3576', '四川职业技术学院', '23', '专科', 'http://www.scvtc.com/', 'scvtc', '遂宁市');
INSERT INTO `university` VALUES ('3577', '乐山职业技术学院', '23', '专科', 'http://www.lszyxy.com/', 'lszyxy', '乐山市');
INSERT INTO `university` VALUES ('3578', '雅安职业技术学院', '23', '专科', 'http://www.yazjy.com/', 'yazjy', '雅安市');
INSERT INTO `university` VALUES ('3579', '四川商务职业学院', '23', '专科', 'http://www.scsw.net.cn/', 'scsw', '成都市');
INSERT INTO `university` VALUES ('3580', '四川司法警官职业学院', '23', '专科', 'http://www.sjpopc.net/', 'sjpopc', '德阳市');
INSERT INTO `university` VALUES ('3581', '广安职业技术学院', '23', '专科', 'http://www.gavtc.cn/', 'gavtc', '广安市');
INSERT INTO `university` VALUES ('3582', '四川信息职业技术学院', '23', '专科', 'http://www.scitc.com.cn/', 'scitc', '广元市');
INSERT INTO `university` VALUES ('3583', '四川文化传媒职业学院', '23', '专科', 'http://www.svccc.net/', 'svccc', '成都市');
INSERT INTO `university` VALUES ('3584', '四川华新现代职业学院', '23', '专科', 'http://www.schxmvc.com.cn/', 'schxmvc', '成都市');
INSERT INTO `university` VALUES ('3585', '四川管理职业学院', '23', '专科', 'http://www.scmpi.cn/', 'scmpi', '成都市');
INSERT INTO `university` VALUES ('3586', '四川艺术职业学院', '23', '专科', 'http://www.scapi.cn/', 'scapi', '成都市');
INSERT INTO `university` VALUES ('3587', '四川中医药高等专科学校', '23', '专科', 'http://www.scctcm.cn/', 'scctcm', '绵阳市');
INSERT INTO `university` VALUES ('3588', '四川科技职业学院', '23', '专科', 'http://www.scstc.cn/', 'scstc', '成都市');
INSERT INTO `university` VALUES ('3589', '四川文化产业职业学院', '23', '专科', 'http://www.scvcci.cn/', 'scvcci', '成都市');
INSERT INTO `university` VALUES ('3590', '四川财经职业学院', '23', '专科', 'http://www.scpcfe.cn/', 'scpcfe', '成都市');
INSERT INTO `university` VALUES ('3591', '四川城市职业学院', '23', '专科', 'http://www.scuvc.com/', 'scuvc', '成都市');
INSERT INTO `university` VALUES ('3592', '四川现代职业学院', '23', '专科', 'http://www.scmvc.cn/', 'scmvc', '成都市');
INSERT INTO `university` VALUES ('3593', '四川幼儿师范高等专科学校', '23', '专科', 'http://www.scyesz.edu.cn/', 'scyesz', '绵阳市');
INSERT INTO `university` VALUES ('3594', '四川长江职业学院', '23', '专科', 'http://www.sccvc.com/', 'sccvc', '成都市');
INSERT INTO `university` VALUES ('3595', '四川三河职业学院', '23', '专科', 'http://www.scshpc.com/', 'scshpc', '泸州市');
INSERT INTO `university` VALUES ('3596', '川北幼儿师范高等专科学校', '23', '专科', 'http://www.gysfxx.com.cn/', 'gysfxx', '广元市');
INSERT INTO `university` VALUES ('3597', '四川卫生康复职业学院', '23', '专科', 'http://www.sczghs.com/', 'sczghs', '自贡市');
INSERT INTO `university` VALUES ('3598', '四川汽车职业技术学院', '23', '专科', 'http://www.scavtc.com/', 'scavtc', '绵阳市');
INSERT INTO `university` VALUES ('3599', '巴中职业技术学院', '23', '专科', 'http://www.bzzyjsxy.cn/', 'bzzyjsxy', '巴中市');
INSERT INTO `university` VALUES ('3600', '四川希望汽车职业学院', '23', '专科', 'http://www.qicheedu.com/', 'qicheedu', '资阳市');
INSERT INTO `university` VALUES ('3601', '四川电子机械职业技术学院', '23', '专科', 'http://www.scemvtc.com/', 'scemvtc', '绵阳市');
INSERT INTO `university` VALUES ('3602', '四川文轩职业学院', '23', '专科', 'http://www.scztjy.cn/', 'scztjy', '成都市');
INSERT INTO `university` VALUES ('3603', '川南幼儿师范专科学校', '23', '专科', 'http://www.sclcys.com/', 'sclcys', '隆昌县');
INSERT INTO `university` VALUES ('3604', '四川护理职业学院', '23', '专科', 'http://www.scswx.com/', 'scswx', '成都市');
INSERT INTO `university` VALUES ('3605', '成都工业职业技术学院', '23', '专科', 'http://www.cdivtc.com.cn/', 'cdivtc', '成都市');
INSERT INTO `university` VALUES ('3606', '四川西南航空职业学院', '23', '专科', 'http://www.xnhkxy.edu.cn/', 'xnhkxy', '成都市');
INSERT INTO `university` VALUES ('3607', '成都工贸职业技术学院', '23', '专科', 'http://www.cdgmxy.com/', 'cdgmxy', '成都市');
INSERT INTO `university` VALUES ('3608', '四川应用技术职业学院', '23', '专科', 'http://www.sccas.net/', 'sccas', '西昌市');
INSERT INTO `university` VALUES ('3609', '贵州大学', '24', '本科', 'http://www.gzu.edu.cn/', 'gzu', '贵阳市');
INSERT INTO `university` VALUES ('3610', '贵州医科大学', '24', '本科', 'http://www.gmc.edu.cn/', 'gmc', '贵阳市');
INSERT INTO `university` VALUES ('3611', '遵义医学院', '24', '本科', 'http://www.zmc.edu.cn/', 'zmc', '遵义市');
INSERT INTO `university` VALUES ('3612', '贵阳中医学院', '24', '本科', 'http://www.gyctcm.edu.cn/', 'gyctcm', '贵阳市');
INSERT INTO `university` VALUES ('3613', '贵州师范大学', '24', '本科', 'http://www.gznu.edu.cn/', 'gznu', '贵阳市');
INSERT INTO `university` VALUES ('3614', '遵义师范学院', '24', '本科', 'http://www.zync.edu.cn/', 'zync', '遵义市');
INSERT INTO `university` VALUES ('3615', '铜仁学院', '24', '本科', 'http://www.gztrc.edu.cn/', 'gztrc', '铜仁市');
INSERT INTO `university` VALUES ('3616', '兴义民族师范学院', '24', '本科', 'http://www.qxntc.edu.cn/', 'qxntc', '兴义市');
INSERT INTO `university` VALUES ('3617', '安顺学院', '24', '本科', 'http://www.asu.edu.cn/', 'asu', '安顺市');
INSERT INTO `university` VALUES ('3618', '贵州工程应用技术学院', '24', '本科', 'http://www.gues.edu.cn/', 'gues', '毕节市');
INSERT INTO `university` VALUES ('3619', '凯里学院', '24', '本科', 'http://www.kluniv.cn/', 'kluniv', '凯里市');
INSERT INTO `university` VALUES ('3620', '黔南民族师范学院', '24', '本科', 'http://www.sgmtu.edu.cn/', 'sgmtu', '都匀市');
INSERT INTO `university` VALUES ('3621', '贵州财经大学', '24', '本科', 'http://www.gzife.edu.cn/', 'gzife', '贵阳市');
INSERT INTO `university` VALUES ('3622', '贵州民族大学', '24', '本科', 'http://www.gzmu.edu.cn/', 'gzmu', '贵阳市');
INSERT INTO `university` VALUES ('3623', '贵阳学院', '24', '本科', 'http://www.gyu.cn/', 'gyu', '贵阳市');
INSERT INTO `university` VALUES ('3624', '六盘水师范学院', '24', '本科', 'http://www.lpssy.edu.cn/', 'lpssy', '六盘水市');
INSERT INTO `university` VALUES ('3625', '贵州商学院', '24', '本科', 'http://www.gzcc.edu.cn/', 'gzcc', '贵阳市');
INSERT INTO `university` VALUES ('3626', '贵州警察学院', '24', '本科', 'http://www.gzjgxy.cn/', 'gzjgxy', '贵阳市');
INSERT INTO `university` VALUES ('3627', '贵州财经学院商务学院', '24', '本科', 'http://portal.gzife.edu.cn/com', 'gzife', '贵阳市');
INSERT INTO `university` VALUES ('3628', '贵州大学科技学院', '24', '本科', 'http://cst.gzu.edu.cn/', 'gzu', '贵阳市');
INSERT INTO `university` VALUES ('3629', '贵州大学明德学院', '24', '本科', 'http://mdc.gzu.edu.cn/', 'gzu', '贵阳市');
INSERT INTO `university` VALUES ('3630', '贵州民族学院人文科技学院', '24', '本科', 'http://www.gzmyrw.cn/', 'gzmyrw', '贵阳市');
INSERT INTO `university` VALUES ('3631', '贵州师范大学求是学院', '24', '本科', 'http://qsxy.gznu.edu.cn/', 'gznu', '贵阳市');
INSERT INTO `university` VALUES ('3632', '遵义医学院医学与科技学院', '24', '本科', 'http://kj.zmc.edu.cn/', 'zmc', '遵义市');
INSERT INTO `university` VALUES ('3633', '贵阳医学院神奇民族医药学院', '24', '本科', 'http://sqxy.gmc.edu.cn/', 'gmc', '贵阳市');
INSERT INTO `university` VALUES ('3634', '贵州师范学院', '24', '本科', 'http://www.gznc.edu.cn/', 'gznc', '贵阳市');
INSERT INTO `university` VALUES ('3635', '贵州理工学院', '24', '本科', 'http://www.git.edu.cn/', 'git', '贵阳市');
INSERT INTO `university` VALUES ('3636', '茅台学院', '24', '本科', 'http://www.mtxy.cn/', 'mtxy', '遵义市');
INSERT INTO `university` VALUES ('3637', '黔南民族医学高等专科学校', '24', '专科', 'http://www.qnmc.cn/', 'qnmc', '都匀市');
INSERT INTO `university` VALUES ('3638', '贵州交通职业技术学院', '24', '专科', 'http://www.gzjtzy.net/', 'gzjtzy', '贵阳市');
INSERT INTO `university` VALUES ('3639', '贵州航天职业技术学院', '24', '专科', 'http://www.gzhtzy.com/', 'gzhtzy', '遵义市');
INSERT INTO `university` VALUES ('3640', '贵州电子信息职业技术学院', '24', '专科', 'http://www.gzeic.com/', 'gzeic', '凯里市');
INSERT INTO `university` VALUES ('3641', '安顺职业技术学院', '24', '专科', 'http://www.asotc.cn/', 'asotc', '安顺市');
INSERT INTO `university` VALUES ('3642', '黔东南民族职业技术学院', '24', '专科', 'http://www.qdnpt.com/', 'qdnpt', '凯里市');
INSERT INTO `university` VALUES ('3643', '黔南民族职业技术学院', '24', '专科', 'http://www.qnzy.net/', 'qnzy', '都匀市');
INSERT INTO `university` VALUES ('3644', '遵义职业技术学院', '24', '专科', 'http://www.zyzy.gov.cn/', 'zyzy', '遵义市');
INSERT INTO `university` VALUES ('3645', '贵州城市职业学院', '24', '专科', 'http://www.cityp.net/', 'cityp', '贵阳市');
INSERT INTO `university` VALUES ('3646', '贵州工业职业技术学院', '24', '专科', 'http://www.gzky.edu.cn/', 'gzky', '贵阳市');
INSERT INTO `university` VALUES ('3647', '贵州电力职业技术学院', '24', '专科', 'http://www.csgedu.cn/', 'csgedu', '贵阳市');
INSERT INTO `university` VALUES ('3648', '六盘水职业技术学院', '24', '专科', 'http://www.lpszy.cn/', 'lpszy', '六盘水市');
INSERT INTO `university` VALUES ('3649', '铜仁职业技术学院', '24', '专科', 'http://www.trzy.cn/', 'trzy', '铜仁市');
INSERT INTO `university` VALUES ('3650', '黔西南民族职业技术学院', '24', '专科', 'http://www.qxnzy.net/', 'qxnzy', '兴义市');
INSERT INTO `university` VALUES ('3651', '贵州轻工职业技术学院', '24', '专科', 'http://www.gzqy.cn/', 'gzqy', '贵阳市');
INSERT INTO `university` VALUES ('3652', '遵义医药高等专科学校', '24', '专科', 'http://www.zunyiyizhuan.com/', 'zunyiyizhu', '遵义市');
INSERT INTO `university` VALUES ('3653', '贵阳护理职业学院', '24', '专科', 'http://www.gynvc.edu.cn/', 'gynvc', '贵阳市');
INSERT INTO `university` VALUES ('3654', '贵阳职业技术学院', '24', '专科', 'http://www.gyvtc.cn/', 'gyvtc', '贵阳市');
INSERT INTO `university` VALUES ('3655', '毕节职业技术学院', '24', '专科', 'http://www.gzbjzy.edu.cn/', 'gzbjzy', '毕节市');
INSERT INTO `university` VALUES ('3656', '贵州职业技术学院', '24', '专科', 'http://www.gzvti.com/', 'gzvti', '贵阳市');
INSERT INTO `university` VALUES ('3657', '贵州盛华职业学院', '24', '专科', 'http://www.forerunnercollege.c', 'forerunner', '惠水县');
INSERT INTO `university` VALUES ('3658', '贵州工商职业学院', '24', '专科', 'http://www.gzgszy.com/', 'gzgszy', '贵阳市');
INSERT INTO `university` VALUES ('3659', '贵阳幼儿师范高等专科学校', '24', '专科', 'http://www.gyys.cn/', 'gyys', '贵阳市');
INSERT INTO `university` VALUES ('3660', '铜仁幼儿师范高等专科学校', '24', '专科', 'http://www.gzsnsf.cn/', 'gzsnsf', '思南县');
INSERT INTO `university` VALUES ('3661', '黔南民族幼儿师范高等专科学校', '24', '专科', 'http://www.gdsf.com.cn/', 'gdsf', '贵定县');
INSERT INTO `university` VALUES ('3662', '毕节医学高等专科学校', '24', '专科', 'http://www.qxnzy.net/', 'qxnzy', '毕节市');
INSERT INTO `university` VALUES ('3663', '贵州建设职业技术学院', '24', '专科', 'http://www.gzsjsxx.cn/', 'gzsjsxx', '贵阳市');
INSERT INTO `university` VALUES ('3664', '毕节幼儿师范高等专科学校', '24', '专科', 'http://www.gzbjyz.com/', 'gzbjyz', '毕节市');
INSERT INTO `university` VALUES ('3665', '贵州农业职业学院', '24', '专科', 'http://www.gznzy.net/', 'gznzy', '贵阳市');
INSERT INTO `university` VALUES ('3666', '贵州工程职业学院', '24', '专科', 'http://www.gzieu.com/', 'gzieu', '铜仁市');
INSERT INTO `university` VALUES ('3667', '贵州工贸职业学院', '24', '专科', 'http://www.gzgmzyxy.com/', 'gzgmzyxy', '毕节市');
INSERT INTO `university` VALUES ('3668', '贵州应用技术职业学院', '24', '专科', 'http://www.gzyyxy.com/', 'gzyyxy', '福泉市');
INSERT INTO `university` VALUES ('3669', '贵州电子科技职业学院', '24', '专科', 'http://www.gzkzy.top/', 'gzkzy', '贵阳市');
INSERT INTO `university` VALUES ('3670', '贵州健康职业学院', '24', '专科', 'http://www.gzjkzy.com/', 'gzjkzy', '铜仁市');
INSERT INTO `university` VALUES ('3671', '云南大学', '25', '本科', 'http://www.ynu.edu.cn/', 'ynu', '昆明市');
INSERT INTO `university` VALUES ('3672', '昆明理工大学', '25', '本科', 'http://www.kmust.edu.cn/', 'kmust', '昆明市');
INSERT INTO `university` VALUES ('3673', '云南农业大学', '25', '本科', 'http://www.ynau.edu.cn/', 'ynau', '昆明市');
INSERT INTO `university` VALUES ('3674', '西南林业大学', '25', '本科', 'http://www.swfc.edu.cn/', 'swfc', '昆明市');
INSERT INTO `university` VALUES ('3675', '昆明医科大学', '25', '本科', 'http://www.kmmc.cn/', 'kmmc', '昆明市');
INSERT INTO `university` VALUES ('3676', '大理大学', '25', '本科', 'http://www.dali.edu.cn/', 'dali', '大理市');
INSERT INTO `university` VALUES ('3677', '云南中医学院', '25', '本科', 'http://www.ynutcm.edu.cn/', 'ynutcm', '昆明市');
INSERT INTO `university` VALUES ('3678', '云南师范大学', '25', '本科', 'http://www.ynnu.edu.cn/', 'ynnu', '昆明市');
INSERT INTO `university` VALUES ('3679', '昭通学院', '25', '本科', 'http://www.zttc.edu.cn/', 'zttc', '昭通市');
INSERT INTO `university` VALUES ('3680', '曲靖师范学院', '25', '本科', 'http://www.qjnu.edu.cn/', 'qjnu', '曲靖市');
INSERT INTO `university` VALUES ('3681', '普洱学院', '25', '本科', 'http://www.peuni.cn/', 'peuni', '普洱市');
INSERT INTO `university` VALUES ('3682', '保山学院', '25', '本科', 'http://www.bsnc.cn/', 'bsnc', '保山市');
INSERT INTO `university` VALUES ('3683', '红河学院', '25', '本科', 'http://www.uoh.edu.cn/', 'uoh', '蒙自市');
INSERT INTO `university` VALUES ('3684', '云南财经大学', '25', '本科', 'http://www.ynufe.edu.cn/', 'ynufe', '昆明市');
INSERT INTO `university` VALUES ('3685', '云南艺术学院', '25', '本科', 'http://www.ynart.edu.cn/', 'ynart', '昆明市');
INSERT INTO `university` VALUES ('3686', '云南民族大学', '25', '本科', 'http://www.ynni.edu.cn/', 'ynni', '昆明市');
INSERT INTO `university` VALUES ('3687', '玉溪师范学院', '25', '本科', 'http://www.yxnu.net/', 'yxnu', '玉溪市');
INSERT INTO `university` VALUES ('3688', '楚雄师范学院', '25', '本科', 'http://www.cxtc.net.cn/', 'cxtc', '楚雄市');
INSERT INTO `university` VALUES ('3689', '云南警官学院', '25', '本科', 'http://www.yn-psnc.com/', 'yn-psnc', '昆明市');
INSERT INTO `university` VALUES ('3690', '昆明学院', '25', '本科', 'http://www.kmu.edu.cn/', 'kmu', '昆明市');
INSERT INTO `university` VALUES ('3691', '文山学院', '25', '本科', 'http://www.wstc.net/', 'wstc', '文山市');
INSERT INTO `university` VALUES ('3692', '云南经济管理学院', '25', '本科', 'http://www.ynjgx.com/', 'ynjgx', '昆明市');
INSERT INTO `university` VALUES ('3693', '云南大学滇池学院', '25', '本科', 'http://dcxy.ynu.edu.cn/', 'ynu', '昆明市');
INSERT INTO `university` VALUES ('3694', '云南大学旅游文化学院', '25', '本科', 'http://www.lywhxy.com/', 'lywhxy', '丽江市');
INSERT INTO `university` VALUES ('3695', '昆明理工大学津桥学院', '25', '本科', 'http://www.oxbridge.edu.cn/', 'oxbridge', '昆明市');
INSERT INTO `university` VALUES ('3696', '云南师范大学商学院', '25', '本科', 'http://www.ynnubs.com/', 'ynnubs', '昆明市');
INSERT INTO `university` VALUES ('3697', '云南师范大学文理学院', '25', '本科', 'http://www.ysdwl.cn/', 'ysdwl', '昆明市');
INSERT INTO `university` VALUES ('3698', '昆明医学院海源学院', '25', '本科', 'http://www.kyhyxy.com/', 'kyhyxy', '昆明市');
INSERT INTO `university` VALUES ('3699', '云南艺术学院文华学院', '25', '本科', 'http://whxy.ynart.edu.cn/', 'ynart', '昆明市');
INSERT INTO `university` VALUES ('3700', '云南工商学院', '25', '本科', 'http://www.yngsxy.net/', 'yngsxy', '昆明市');
INSERT INTO `university` VALUES ('3701', '滇西科技师范学院', '25', '本科', 'http://www.dxstnu.edu.cn/', 'dxstnu', '临沧市');
INSERT INTO `university` VALUES ('3702', '昆明冶金高等专科学校', '25', '专科', 'http://www.kmyz.edu.cn/', 'kmyz', '昆明市');
INSERT INTO `university` VALUES ('3703', '云南国土资源职业学院', '25', '专科', 'http://www.yngtxy.net/', 'yngtxy', '昆明市');
INSERT INTO `university` VALUES ('3704', '云南交通职业技术学院', '25', '专科', 'http://www.yncs.edu.cn/', 'yncs', '昆明市');
INSERT INTO `university` VALUES ('3705', '昆明工业职业技术学院', '25', '专科', 'http://www.kmvtc.net/', 'kmvtc', '昆明市');
INSERT INTO `university` VALUES ('3706', '云南农业职业技术学院', '25', '专科', 'http://www.ynavc.com/', 'ynavc', '昆明市');
INSERT INTO `university` VALUES ('3707', '云南司法警官职业学院', '25', '专科', 'http://www.yncpu.net/', 'yncpu', '昆明市');
INSERT INTO `university` VALUES ('3708', '云南文化艺术职业学院', '25', '专科', 'http://www.ynarts.cn/', 'ynarts', '昆明市');
INSERT INTO `university` VALUES ('3709', '云南体育运动职业技术学院', '25', '专科', 'http://www.ynasc.com/', 'ynasc', '昆明市');
INSERT INTO `university` VALUES ('3710', '云南科技信息职业学院', '25', '专科', 'http://www.ynkexin.cn/', 'ynkexin', '昆明市');
INSERT INTO `university` VALUES ('3711', '西双版纳职业技术学院', '25', '专科', 'http://www.xsbnzy.com/', 'xsbnzy', '景洪市');
INSERT INTO `university` VALUES ('3712', '昆明艺术职业学院', '25', '专科', 'http://www.kmac.org.cn/', 'kmac', '昆明市');
INSERT INTO `university` VALUES ('3713', '玉溪农业职业技术学院', '25', '专科', 'http://www.yxnzy.net/', 'yxnzy', '玉溪市');
INSERT INTO `university` VALUES ('3714', '云南能源职业技术学院', '25', '专科', 'http://www.ynny.cn/', 'ynny', '曲靖市');
INSERT INTO `university` VALUES ('3715', '云南国防工业职业技术学院', '25', '专科', 'http://www.ynvtc.cn/', 'ynvtc', '昆明市');
INSERT INTO `university` VALUES ('3716', '云南机电职业技术学院', '25', '专科', 'http://www.ynmec.com/', 'ynmec', '昆明市');
INSERT INTO `university` VALUES ('3717', '云南林业职业技术学院', '25', '专科', 'http://www.ynftc.cn/', 'ynftc', '昆明市');
INSERT INTO `university` VALUES ('3718', '云南城市建设职业学院', '25', '专科', 'http://www.yncjxy.com/', 'yncjxy', '昆明市');
INSERT INTO `university` VALUES ('3719', '云南工程职业学院', '25', '专科', 'http://www.sailingedu.com/', 'sailingedu', '昆明市');
INSERT INTO `university` VALUES ('3720', '曲靖医学高等专科学校', '25', '专科', 'http://www.qjyz.org/', 'qjyz', '曲靖市');
INSERT INTO `university` VALUES ('3721', '楚雄医药高等专科学校', '25', '专科', 'http://www.cxmtc.net/', 'cxmtc', '楚雄市');
INSERT INTO `university` VALUES ('3722', '保山中医药高等专科学校', '25', '专科', 'http://www.bszyz.cn/', 'bszyz', '保山市');
INSERT INTO `university` VALUES ('3723', '丽江师范高等专科学校', '25', '专科', 'http://www.lj-edu.cn/', 'lj-edu', '丽江市');
INSERT INTO `university` VALUES ('3724', '德宏师范高等专科学校', '25', '专科', 'http://www.yndhec.net/', 'yndhec', '芒市');
INSERT INTO `university` VALUES ('3725', '云南新兴职业学院', '25', '专科', 'http://www.ynxzy.com/', 'ynxzy', '昆明市');
INSERT INTO `university` VALUES ('3726', '云南锡业职业技术学院', '25', '专科', 'http://www.ytvtc.com/', 'ytvtc', '个旧市');
INSERT INTO `university` VALUES ('3727', '云南经贸外事职业学院', '25', '专科', 'http://www.ynjw.net/', 'ynjw', '昆明市');
INSERT INTO `university` VALUES ('3728', '云南三鑫职业技术学院', '25', '专科', 'http://www.ynsxzy.com/', 'ynsxzy', '文山市');
INSERT INTO `university` VALUES ('3729', '德宏职业学院', '25', '专科', 'http://www.yndhvc.com/', 'yndhvc', '芒市');
INSERT INTO `university` VALUES ('3730', '云南商务职业学院', '25', '专科', 'http://www.ynswzyxy.com/', 'ynswzyxy', '昆明市');
INSERT INTO `university` VALUES ('3731', '昆明卫生职业学院', '25', '专科', 'http://www.kmhpc.net/', 'kmhpc', '昆明市');
INSERT INTO `university` VALUES ('3732', '云南现代职业技术学院', '25', '专科', 'http://www.ynxd.net.cn/', 'ynxd', '楚雄州');
INSERT INTO `university` VALUES ('3733', '云南旅游职业学院', '25', '专科', 'http://www.ynctv.com/', 'ynctv', '昆明市');
INSERT INTO `university` VALUES ('3734', '红河卫生职业学院', '25', '专科', 'http://www.hhwx.org.cn/', 'hhwx', '蒙自市');
INSERT INTO `university` VALUES ('3735', '云南外事外语职业学院', '25', '专科', 'http://www.fafl.cn/', 'fafl', '昆明市');
INSERT INTO `university` VALUES ('3736', '大理农林职业技术学院', '25', '专科', 'http://www.dlcaf.com/', 'dlcaf', '大理市');
INSERT INTO `university` VALUES ('3737', '云南财经职业学院', '25', '专科', 'http://www.ynczy.cn/', 'ynczy', '昆明市');
INSERT INTO `university` VALUES ('3738', '云南轻纺职业学院', '25', '专科', 'http://www.ynqgx.cn/', 'ynqgx', '昆明市');
INSERT INTO `university` VALUES ('3739', '云南交通运输职业学院', '25', '专科', 'http://www.ynvct.com/', 'ynvct', '昆明市');
INSERT INTO `university` VALUES ('3740', '西藏大学', '26', '本科', 'http://www.utibet.edu.cn/', 'utibet', '拉萨市');
INSERT INTO `university` VALUES ('3741', '西藏民族大学', '26', '本科', 'http://www.xzmy.edu.cn/', 'xzmy', '咸阳市');
INSERT INTO `university` VALUES ('3742', '西藏藏医学院', '26', '本科', 'http://www.ttmc.edu.cn/', 'ttmc', '拉萨市');
INSERT INTO `university` VALUES ('3743', '西藏农牧学院', '26', '本科', 'http://www.xza.cn/', 'xza', '林芝市');
INSERT INTO `university` VALUES ('3744', '西藏警官高等专科学校', '26', '专科', 'http://www.tpa.net.cn/', 'tpa', '拉萨市');
INSERT INTO `university` VALUES ('3745', '拉萨师范高等专科学校', '26', '专科', 'http://www.xzlssf.org/', 'xzlssf', '拉萨市');
INSERT INTO `university` VALUES ('3746', '西藏职业技术学院', '26', '专科', 'http://www.xzgzy.cn/', 'xzgzy', '拉萨市');
INSERT INTO `university` VALUES ('3747', '西北大学', '27', '本科', 'http://www.nwu.edu.cn/', 'nwu', '西安市');
INSERT INTO `university` VALUES ('3748', '西安交通大学', '27', '本科', 'http://www.xjtu.edu.cn/', 'xjtu', '西安市');
INSERT INTO `university` VALUES ('3749', '西北工业大学', '27', '本科', 'http://www.nwpu.edu.cn/', 'nwpu', '西安市');
INSERT INTO `university` VALUES ('3750', '西安理工大学', '27', '本科', 'http://www.xaut.edu.cn/', 'xaut', '西安市');
INSERT INTO `university` VALUES ('3751', '西安电子科技大学', '27', '本科', 'http://www.xidian.edu.cn/', 'xidian', '西安市');
INSERT INTO `university` VALUES ('3752', '西安工业大学', '27', '本科', 'http://www.xatu.cn/', 'xatu', '西安市');
INSERT INTO `university` VALUES ('3753', '西安建筑科技大学', '27', '本科', 'http://www.xauat.edu.cn/', 'xauat', '西安市');
INSERT INTO `university` VALUES ('3754', '西安科技大学', '27', '本科', 'http://www.xust.edu.cn/', 'xust', '西安市');
INSERT INTO `university` VALUES ('3755', '西安石油大学', '27', '本科', 'http://www.xapi.edu.cn/', 'xapi', '西安市');
INSERT INTO `university` VALUES ('3756', '陕西科技大学', '27', '本科', 'http://www.sust.edu.cn/', 'sust', '西安市');
INSERT INTO `university` VALUES ('3757', '西安工程大学', '27', '本科', 'http://www.xpu.edu.cn/', 'xpu', '西安市');
INSERT INTO `university` VALUES ('3758', '长安大学', '27', '本科', 'http://www.chd.edu.cn/', 'chd', '西安市');
INSERT INTO `university` VALUES ('3759', '西北农林科技大学', '27', '本科', 'http://www.nwsuaf.edu.cn/', 'nwsuaf', '杨凌');
INSERT INTO `university` VALUES ('3760', '陕西中医药大学', '27', '本科', 'http://www.sntcm.edu.cn/', 'sntcm', '咸阳市');
INSERT INTO `university` VALUES ('3761', '陕西师范大学', '27', '本科', 'http://www.snnu.edu.cn/', 'snnu', '西安市');
INSERT INTO `university` VALUES ('3762', '延安大学', '27', '本科', 'http://www.yau.edu.cn/', 'yau', '延安市');
INSERT INTO `university` VALUES ('3763', '陕西理工大学', '27', '本科', 'http://www.snut.edu.cn/', 'snut', '汉中市');
INSERT INTO `university` VALUES ('3764', '宝鸡文理学院', '27', '本科', 'http://www.bjwlxy.edu.cn/', 'bjwlxy', '宝鸡市');
INSERT INTO `university` VALUES ('3765', '咸阳师范学院', '27', '本科', 'http://www.xysfxy.cn/', 'xysfxy', '咸阳市');
INSERT INTO `university` VALUES ('3766', '渭南师范学院', '27', '本科', 'http://www.wntc.edu.cn/', 'wntc', '渭南市');
INSERT INTO `university` VALUES ('3767', '西安外国语大学', '27', '本科', 'http://www.xisu.edu.cn/', 'xisu', '西安市');
INSERT INTO `university` VALUES ('3768', '西北政法大学', '27', '本科', 'http://www.nwupl.cn/', 'nwupl', '西安市');
INSERT INTO `university` VALUES ('3769', '西安体育学院', '27', '本科', 'http://www.xaipe.edu.cn/', 'xaipe', '西安市');
INSERT INTO `university` VALUES ('3770', '西安音乐学院', '27', '本科', 'http://www.xacom.edu.cn/', 'xacom', '西安市');
INSERT INTO `university` VALUES ('3771', '西安美术学院', '27', '本科', 'http://www.xafa.edu.cn/', 'xafa', '西安市');
INSERT INTO `university` VALUES ('3772', '西安文理学院', '27', '本科', 'http://www.xawl.org/', 'xawl', '西安市');
INSERT INTO `university` VALUES ('3773', '榆林学院', '27', '本科', 'http://www.yulinu.edu.cn/', 'yulinu', '榆林市');
INSERT INTO `university` VALUES ('3774', '商洛学院', '27', '本科', 'http://www.slxy.cn/', 'slxy', '商洛市');
INSERT INTO `university` VALUES ('3775', '安康学院', '27', '本科', 'http://www.aku.edu.cn/', 'aku', '安康市');
INSERT INTO `university` VALUES ('3776', '西安培华学院', '27', '本科', 'http://www.peihua.cn/', 'peihua', '西安市');
INSERT INTO `university` VALUES ('3777', '西安财经学院', '27', '本科', 'http://www.xaufe.edu.cn/', 'xaufe', '西安市');
INSERT INTO `university` VALUES ('3778', '西安邮电大学', '27', '本科', 'http://www.xiyou.edu.cn/', 'xiyou', '西安市');
INSERT INTO `university` VALUES ('3779', '西安航空学院', '27', '专科', 'http://www.xaau.edu.cn/', 'xaau', '西安市');
INSERT INTO `university` VALUES ('3780', '西安医学院', '27', '本科', 'http://www.xiyi.edu.cn/', 'xiyi', '西安市');
INSERT INTO `university` VALUES ('3781', '西安欧亚学院', '27', '本科', 'http://www.eurasia.edu/', 'eurasia', '西安市');
INSERT INTO `university` VALUES ('3782', '西安外事学院', '27', '本科', 'http://www.xaiu.edu.cn/', 'xaiu', '西安市');
INSERT INTO `university` VALUES ('3783', '西安翻译学院', '27', '本科', 'http://www.xafy.edu.cn/', 'xafy', '西安市');
INSERT INTO `university` VALUES ('3784', '西京学院', '27', '本科', 'http://www.xijing.com.cn/', 'xijing', '西安市');
INSERT INTO `university` VALUES ('3785', '西安思源学院', '27', '本科', 'http://www.xasyu.cn/', 'xasyu', '西安市');
INSERT INTO `university` VALUES ('3786', '陕西国际商贸学院', '27', '本科', 'http://www.csiic.com/', 'csiic', '西安市');
INSERT INTO `university` VALUES ('3787', '陕西服装工程学院', '27', '本科', 'http://www.sxfu.org/', 'sxfu', '西安市');
INSERT INTO `university` VALUES ('3788', '西安交通工程学院', '27', '本科', 'http://www.xjgyedu.cn/', 'xjgyedu', '西安市');
INSERT INTO `university` VALUES ('3789', '西安交通大学城市学院', '27', '本科', 'http://www.xjtucc.cn/', 'xjtucc', '西安市');
INSERT INTO `university` VALUES ('3790', '西北大学现代学院', '27', '本科', 'http://www.xdxd.cn/', 'xdxd', '西安市');
INSERT INTO `university` VALUES ('3791', '西安建筑科技大学华清学院', '27', '本科', 'http://www.xauat-hqc.com/', 'xauat-hqc', '西安市');
INSERT INTO `university` VALUES ('3792', '西安财经学院行知学院', '27', '本科', 'http://www.xcxz.com.cn/', 'xcxz', '西安市');
INSERT INTO `university` VALUES ('3793', '陕西科技大学镐京学院', '27', '本科', 'http://www.kdhj-edu.net/', 'kdhj-edu', '西安市');
INSERT INTO `university` VALUES ('3794', '西安工业大学北方信息工程学院', '27', '本科', 'http://www.bxait.cn/', 'bxait', '西安市');
INSERT INTO `university` VALUES ('3795', '延安大学西安创新学院', '27', '本科', 'http://www.xacxxy.com/', 'xacxxy', '西安市');
INSERT INTO `university` VALUES ('3796', '西安电子科技大学长安学院', '27', '本科', 'http://www.xdca.com.cn/', 'xdca', '西安市');
INSERT INTO `university` VALUES ('3797', '西北工业大学明德学院', '27', '本科', 'http://www.npumd.cn/', 'npumd', '西安市');
INSERT INTO `university` VALUES ('3798', '长安大学兴华学院', '27', '本科', 'http://www.chdxhxy.com/', 'chdxhxy', '西安市');
INSERT INTO `university` VALUES ('3799', '西安理工大学高科学院', '27', '本科', 'http://www.xthtc.com/', 'xthtc', '西安市');
INSERT INTO `university` VALUES ('3800', '西安科技大学高新学院', '27', '本科', 'http://www.gaoxinedu.com/', 'gaoxinedu', '西安市');
INSERT INTO `university` VALUES ('3801', '陕西学前师范学院', '27', '本科', 'http://www.snie.edu.cn/', 'snie', '西安市');
INSERT INTO `university` VALUES ('3802', '陕西工业职业技术学院', '27', '专科', 'http://www.sxpi.com.cn/', 'sxpi', '咸阳市');
INSERT INTO `university` VALUES ('3803', '杨凌职业技术学院', '27', '专科', 'http://www.ylvtc.cn/', 'ylvtc', '杨凌市');
INSERT INTO `university` VALUES ('3804', '西安电力高等专科学校', '27', '专科', 'http://www.xaepi.edu.cn/', 'xaepi', '西安市');
INSERT INTO `university` VALUES ('3805', '陕西能源职业技术学院', '27', '专科', 'http://www.sxny.cn/', 'sxny', '咸阳市');
INSERT INTO `university` VALUES ('3806', '陕西国防工业职业技术学院', '27', '专科', 'http://www.gfxy.com/', 'gfxy', '西安市');
INSERT INTO `university` VALUES ('3807', '西安航空职业技术学院', '27', '专科', 'http://www.xihang.com.cn/', 'xihang', '西安市');
INSERT INTO `university` VALUES ('3808', '陕西财经职业技术学院', '27', '专科', 'http://www.sxptife.net/', 'sxptife', '咸阳市');
INSERT INTO `university` VALUES ('3809', '陕西交通职业技术学院', '27', '专科', 'http://www.scct.cn/', 'scct', '西安市');
INSERT INTO `university` VALUES ('3810', '陕西职业技术学院', '27', '专科', 'http://www.spvec.com.cn/', 'spvec', '西安市');
INSERT INTO `university` VALUES ('3811', '西安高新科技职业学院', '27', '专科', 'http://www.xhtu.com.cn/', 'xhtu', '西安市');
INSERT INTO `university` VALUES ('3812', '西安城市建设职业学院', '27', '专科', 'http://www.xacsjsedu.com/', 'xacsjsedu', '西安市');
INSERT INTO `university` VALUES ('3813', '陕西铁路工程职业技术学院', '27', '专科', 'http://www.sxri.net/', 'sxri', '渭南市');
INSERT INTO `university` VALUES ('3814', '宝鸡职业技术学院', '27', '专科', 'http://www.bjvtc.com/', 'bjvtc', '宝鸡市');
INSERT INTO `university` VALUES ('3815', '陕西航空职业技术学院', '27', '专科', 'http://www.sxhkxy.com/', 'sxhkxy', '汉中市');
INSERT INTO `university` VALUES ('3816', '陕西电子信息职业技术学院', '27', '专科', 'http://www.sxitu.com/', 'sxitu', '西安市');
INSERT INTO `university` VALUES ('3817', '陕西邮电职业技术学院', '27', '专科', 'http://www.sptc.sn.cn/', 'sptc', '西安市');
INSERT INTO `university` VALUES ('3818', '西安海棠职业学院', '27', '专科', 'http://www.xahtxy.cn/', 'xahtxy', '西安市');
INSERT INTO `university` VALUES ('3819', '西安汽车科技职业学院', '27', '专科', 'http://www.atc168.com/', 'atc168', '西安市');
INSERT INTO `university` VALUES ('3820', '西安东方亚太职业技术学院', '27', '专科', 'http://www.yt-edu.cn/', 'yt-edu', '西安市');
INSERT INTO `university` VALUES ('3821', '陕西警官职业学院', '27', '专科', 'http://www.sxjgxy.edu.cn/', 'sxjgxy', '西安市');
INSERT INTO `university` VALUES ('3822', '陕西经济管理职业技术学院', '27', '专科', 'http://www.sxjgy.com/', 'sxjgy', '西安市');
INSERT INTO `university` VALUES ('3823', '西安铁路职业技术学院', '27', '专科', 'http://www.xatzy.cn/', 'xatzy', '西安市');
INSERT INTO `university` VALUES ('3824', '咸阳职业技术学院', '27', '专科', 'http://www.xianyangzhiyuan.cn/', 'xianyangzh', '咸阳市');
INSERT INTO `university` VALUES ('3825', '西安职业技术学院', '27', '专科', 'http://www.xzyedu.com.cn/', 'xzyedu', '西安市');
INSERT INTO `university` VALUES ('3826', '商洛职业技术学院', '27', '专科', 'http://www.slzyjsxy.com/', 'slzyjsxy', '商洛市');
INSERT INTO `university` VALUES ('3827', '汉中职业技术学院', '27', '专科', 'http://www.hzvtc.cn/', 'hzvtc', '汉中市');
INSERT INTO `university` VALUES ('3828', '延安职业技术学院', '27', '专科', 'http://www.yapt.cn/', 'yapt', '延安市');
INSERT INTO `university` VALUES ('3829', '渭南职业技术学院', '27', '专科', 'http://www.wnzy.net/', 'wnzy', '渭南市');
INSERT INTO `university` VALUES ('3830', '安康职业技术学院', '27', '专科', 'http://www.ak321.com/', 'ak321', '安康市');
INSERT INTO `university` VALUES ('3831', '铜川职业技术学院', '27', '专科', 'http://www.tczyxy.net/', 'tczyxy', '铜川市');
INSERT INTO `university` VALUES ('3832', '陕西青年职业学院', '27', '专科', 'http://www.sxqzy.com/', 'sxqzy', '西安市');
INSERT INTO `university` VALUES ('3833', '陕西工商职业学院', '27', '专科', 'http://www.snbcedu.cn/', 'snbcedu', '西安市');
INSERT INTO `university` VALUES ('3834', '陕西电子科技职业学院', '27', '专科', 'http://www.sxetcedu.com/', 'sxetcedu', '西安市');
INSERT INTO `university` VALUES ('3835', '陕西旅游烹饪职业学院', '27', '专科', 'http://www.sntcc.cn/', 'sntcc', '西安市');
INSERT INTO `university` VALUES ('3836', '西安医学高等专科学校', '27', '专科', 'http://www.xagdyz.com/', 'xagdyz', '西安市');
INSERT INTO `university` VALUES ('3837', '榆林职业技术学院', '27', '专科', 'http://www.yulinvtc.com.cn/', 'yulinvtc', '榆林市');
INSERT INTO `university` VALUES ('3838', '陕西艺术职业学院', '27', '专科', 'http://www.sxavc.com/', 'sxavc', '西安市');
INSERT INTO `university` VALUES ('3839', '陕西机电职业技术学院', '27', '专科', 'http://www.sxjdzy.cn/', 'sxjdzy', '西安市');
INSERT INTO `university` VALUES ('3840', '兰州大学', '28', '本科', 'http://www.lzu.edu.cn/', 'lzu', '兰州市');
INSERT INTO `university` VALUES ('3841', '兰州理工大学', '28', '本科', 'http://www.lut.cn/', 'lut', '兰州市');
INSERT INTO `university` VALUES ('3842', '兰州交通大学', '28', '本科', 'http://www.lzjtu.edu.cn/', 'lzjtu', '兰州市');
INSERT INTO `university` VALUES ('3843', '甘肃农业大学', '28', '本科', 'http://www.gsau.edu.cn/', 'gsau', '兰州市');
INSERT INTO `university` VALUES ('3844', '甘肃中医药大学', '28', '本科', 'http://www.gszy.edu.cn/', 'gszy', '兰州市');
INSERT INTO `university` VALUES ('3845', '西北师范大学', '28', '本科', 'http://www.nwnu.edu.cn/', 'nwnu', '兰州市');
INSERT INTO `university` VALUES ('3846', '兰州城市学院', '28', '本科', 'http://www.lztc.edu.cn/', 'lztc', '兰州市');
INSERT INTO `university` VALUES ('3847', '陇东学院', '28', '本科', 'http://www.ldxy.edu.cn/', 'ldxy', '庆阳市');
INSERT INTO `university` VALUES ('3848', '天水师范学院', '28', '本科', 'http://www.tsnc.edu.cn/', 'tsnc', '天水市');
INSERT INTO `university` VALUES ('3849', '河西学院', '28', '本科', 'http://www.hxu.edu.cn/', 'hxu', '张掖市');
INSERT INTO `university` VALUES ('3850', '兰州财经大学', '28', '本科', 'http://www.lzcc.edu.cn/', 'lzcc', '兰州市');
INSERT INTO `university` VALUES ('3851', '西北民族大学', '28', '本科', 'http://www.xbmu.edu.cn/', 'xbmu', '兰州市');
INSERT INTO `university` VALUES ('3852', '甘肃政法学院', '28', '本科', 'http://www.gsli.edu.cn/', 'gsli', '兰州市');
INSERT INTO `university` VALUES ('3853', '甘肃民族师范学院', '28', '本科', 'http://www.gnun.edu.cn/', 'gnun', '合作市');
INSERT INTO `university` VALUES ('3854', '兰州文理学院', '28', '本科', 'http://www.luas.edu.cn/', 'luas', '兰州市');
INSERT INTO `university` VALUES ('3855', '甘肃医学院', '28', '本科', 'http://www.plmc.edu.cn/', 'plmc', '平凉市');
INSERT INTO `university` VALUES ('3856', '兰州工业学院', '28', '本科', 'http://www.lzptc.edu.cn/', 'lzptc', '兰州市');
INSERT INTO `university` VALUES ('3857', '西北师范大学知行学院', '28', '本科', 'http://zxxy.nwnu.edu.cn/', 'nwnu', '兰州市');
INSERT INTO `university` VALUES ('3858', '兰州商学院陇桥学院', '28', '本科', 'http://www.lzlqc.com/', 'lzlqc', '兰州市');
INSERT INTO `university` VALUES ('3859', '兰州财经大学长青学院', '28', '本科', 'http://changqing.lzcc.edu.cn/', 'lzcc', '兰州市');
INSERT INTO `university` VALUES ('3860', '兰州交通大学博文学院', '28', '本科', 'http://www.bowenedu.cn/', 'bowenedu', '兰州市');
INSERT INTO `university` VALUES ('3861', '兰州理工大学技术工程学院', '28', '本科', 'http://www.lutcte.cn/', 'lutcte', '兰州市');
INSERT INTO `university` VALUES ('3862', '兰州石化职业技术学院', '28', '专科', 'http://www.lzpcc.com.cn/', 'lzpcc', '兰州市');
INSERT INTO `university` VALUES ('3863', '陇南师范高等专科学校', '28', '专科', 'http://www.lntc.edu.cn/', 'lntc', '成县');
INSERT INTO `university` VALUES ('3864', '定西师范高等专科学校', '28', '专科', 'http://www.dxatc.cn/', 'dxatc', '定西市');
INSERT INTO `university` VALUES ('3865', '甘肃建筑职业技术学院', '28', '专科', 'http://www.gcvtc.gsedu.cn/', 'gcvtc', '兰州市');
INSERT INTO `university` VALUES ('3866', '酒泉职业技术学院', '28', '专科', 'http://www.jqzy.com/', 'jqzy', '酒泉市');
INSERT INTO `university` VALUES ('3867', '兰州外语职业学院', '28', '专科', 'http://www.lzwyedu.com/', 'lzwyedu', '兰州市');
INSERT INTO `university` VALUES ('3868', '兰州职业技术学院', '28', '专科', 'http://www.lvu.edu.cn/', 'lvu', '兰州市');
INSERT INTO `university` VALUES ('3869', '甘肃警察职业学院', '28', '专科', 'http://www.gs-police.com/', 'gs-police', '兰州市');
INSERT INTO `university` VALUES ('3870', '甘肃林业职业技术学院', '28', '专科', 'http://www.gsfc.edu.cn/', 'gsfc', '天水市');
INSERT INTO `university` VALUES ('3871', '甘肃工业职业技术学院', '28', '专科', 'http://www.gipc.edu.cn/', 'gipc', '天水市');
INSERT INTO `university` VALUES ('3872', '武威职业学院', '28', '专科', 'http://www.wwoc.cn/', 'wwoc', '武威市');
INSERT INTO `university` VALUES ('3873', '甘肃交通职业技术学院', '28', '专科', 'http://www.gsjtxy.edu.cn/', 'gsjtxy', '兰州市');
INSERT INTO `university` VALUES ('3874', '兰州资源环境职业技术学院', '28', '专科', 'http://www.lzre.edu.cn/', 'lzre', '兰州市');
INSERT INTO `university` VALUES ('3875', '甘肃农业职业技术学院', '28', '专科', 'http://www.gscat.cn/', 'gscat', '兰州市');
INSERT INTO `university` VALUES ('3876', '甘肃畜牧工程职业技术学院', '28', '专科', 'http://www.xmgcxy.gsedu.cn/', 'xmgcxy', '武威市');
INSERT INTO `university` VALUES ('3877', '甘肃钢铁职业技术学院', '28', '专科', 'http://www.ggzy.edu.cn/', 'ggzy', '嘉峪关');
INSERT INTO `university` VALUES ('3878', '甘肃机电职业技术学院', '28', '专科', 'http://www.gsjdxy.com/', 'gsjdxy', '天水市');
INSERT INTO `university` VALUES ('3879', '甘肃有色冶金职业技术学院', '28', '专科', 'http://www.gsysyj.edu.cn/', 'gsysyj', '金昌市');
INSERT INTO `university` VALUES ('3880', '白银矿冶职业技术学院', '28', '专科', 'http://www.bymu.cn/', 'bymu', '白银市');
INSERT INTO `university` VALUES ('3881', '甘肃卫生职业学院', '28', '专科', 'http://www.gswx.com.cn/', 'gswx', '兰州市');
INSERT INTO `university` VALUES ('3882', '兰州科技职业学院', '28', '专科', 'http://www.lzust.com/', 'lzust', '兰州市');
INSERT INTO `university` VALUES ('3883', '庆阳职业技术学院', '28', '专科', 'http://qyvtc.autocat.cn/', 'autocat', '庆阳市');
INSERT INTO `university` VALUES ('3884', '平凉职业技术学院', '28', '专科', 'http://www.plvtc.cn/', 'plvtc', '临夏州');
INSERT INTO `university` VALUES ('3885', '甘肃能源化工职业学院', '28', '专科', 'http://www.gsnyedu.cn/', 'gsnyedu', '兰州市');
INSERT INTO `university` VALUES ('3886', '青海大学', '29', '本科', 'http://www.qhu.edu.cn/', 'qhu', '西宁市');
INSERT INTO `university` VALUES ('3887', '青海师范大学', '29', '本科', 'http://www.qhnu.edu.cn/', 'qhnu', '西宁市');
INSERT INTO `university` VALUES ('3888', '青海民族大学', '29', '本科', 'http://www.qhmu.edu.cn/', 'qhmu', '西宁市');
INSERT INTO `university` VALUES ('3889', '青海大学昆仑学院', '29', '专科', 'http://klc.qhu.edu.cn/', 'qhu', '西宁市');
INSERT INTO `university` VALUES ('3890', '青海卫生职业技术学院', '29', '专科', 'http://www.qhwszy.edu.cn/', 'qhwszy', '西宁市');
INSERT INTO `university` VALUES ('3891', '青海警官职业学院', '29', '专科', 'http://www.qhjyedu.com/', 'qhjyedu', '西宁市');
INSERT INTO `university` VALUES ('3892', '青海畜牧兽医职业技术学院', '29', '专科', 'http://www.qhxmzy.com.cn/', 'qhxmzy', '西宁市');
INSERT INTO `university` VALUES ('3893', '青海交通职业技术学院', '29', '专科', 'http://www.qhctc.edu.cn/', 'qhctc', '西宁市');
INSERT INTO `university` VALUES ('3894', '青海建筑职业技术学院', '29', '专科', 'http://www.qhavtc.com/', 'qhavtc', '西宁市');
INSERT INTO `university` VALUES ('3895', '西宁城市职业技术学院', '29', '专科', 'http://www.xncszy.com/', 'xncszy', '西宁市');
INSERT INTO `university` VALUES ('3896', '青海高等职业技术学院', '29', '专科', 'http://www.qhgdzyjsxy.com/', 'qhgdzyjsxy', '海东市');
INSERT INTO `university` VALUES ('3897', '青海柴达木职业技术学院', '29', '专科', 'http://61.133.238.121/', '133', '海西州');
INSERT INTO `university` VALUES ('3898', '宁夏大学', '30', '本科', 'http://www.nxu.edu.cn/', 'nxu', '银川市');
INSERT INTO `university` VALUES ('3899', '宁夏医科大学', '30', '本科', 'http://www.nxmu.edu.cn/', 'nxmu', '银川市');
INSERT INTO `university` VALUES ('3900', '宁夏师范学院', '30', '本科', 'http://www.nxtu.cn/', 'nxtu', '固原市');
INSERT INTO `university` VALUES ('3901', '北方民族大学', '30', '本科', 'http://www.nwsni.edu.cn/', 'nwsni', '银川市');
INSERT INTO `university` VALUES ('3902', '宁夏理工学院', '30', '本科', 'http://www.nxist.com/', 'nxist', '石嘴山');
INSERT INTO `university` VALUES ('3903', '宁夏大学新华学院', '30', '本科', 'http://xinhua.nxu.edu.cn/', 'nxu', '银川市');
INSERT INTO `university` VALUES ('3904', '银川能源学院', '30', '本科', 'http://www.ycu.com.cn/', 'ycu', '银川市');
INSERT INTO `university` VALUES ('3905', '中国矿业大学银川学院', '30', '本科', 'http://www.cumtyc.com.cn/', 'cumtyc', '银川市');
INSERT INTO `university` VALUES ('3906', '宁夏民族职业技术学院', '30', '专科', 'http://www.nxmzy.com.cn/', 'nxmzy', '吴忠市');
INSERT INTO `university` VALUES ('3907', '宁夏工业职业学院', '30', '专科', 'http://www.ngzy.nx.edu.cn/', 'ngzy', '石嘴山市');
INSERT INTO `university` VALUES ('3908', '宁夏职业技术学院', '30', '专科', 'http://www.nxtc.edu.cn/', 'nxtc', '银川市');
INSERT INTO `university` VALUES ('3909', '宁夏工商职业技术学院', '30', '专科', 'http://www.nxgs.edu.cn/', 'nxgs', '银川市');
INSERT INTO `university` VALUES ('3910', '宁夏财经职业技术学院', '30', '专科', 'http://www.nxcy.edu.cn/', 'nxcy', ' ');
INSERT INTO `university` VALUES ('3911', '宁夏司法警官职业学院', '30', '专科', 'http://www.nsjy.com.cn/', 'nsjy', '银川市');
INSERT INTO `university` VALUES ('3912', '宁夏建设职业技术学院', '30', '专科', 'http://www.nxjy.edu.cn/', 'nxjy', '银川市');
INSERT INTO `university` VALUES ('3913', '宁夏葡萄酒与防沙治沙职业技术学院', '30', '专科', 'http://www.nxfszs.com/', 'nxfszs', '银川市');
INSERT INTO `university` VALUES ('3914', '宁夏幼儿师范高等专科学校', '30', '专科', 'http://www.nxysedu.com/', 'nxysedu', '银川市');
INSERT INTO `university` VALUES ('3915', '宁夏艺术职业学院', '30', '专科', 'http://www.nxyx.cn/', 'nxyx', '银川市');
INSERT INTO `university` VALUES ('3916', '新疆大学', '31', '本科', 'http://www.xju.edu.cn/', 'xju', '乌鲁木齐');
INSERT INTO `university` VALUES ('3917', '塔里木大学', '31', '本科', 'http://www.taru.edu.cn/', 'taru', '阿拉尔市');
INSERT INTO `university` VALUES ('3918', '新疆农业大学', '31', '本科', 'http://www.xjau.edu.cn/', 'xjau', '乌鲁木齐');
INSERT INTO `university` VALUES ('3919', '石河子大学', '31', '本科', 'http://www.shzu.edu.cn/', 'shzu', '石河子');
INSERT INTO `university` VALUES ('3920', '新疆医科大学', '31', '本科', 'http://www.xjmu.edu.cn/', 'xjmu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3921', '新疆师范大学', '31', '本科', 'http://www.xjnu.edu.cn/', 'xjnu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3922', '喀什大学', '31', '本科', 'http://www.ksu.edu.cn/', 'ksu', '喀什市');
INSERT INTO `university` VALUES ('3923', '伊犁师范学院', '31', '本科', 'http://www.ylsy.edu.cn/', 'ylsy', '伊宁市');
INSERT INTO `university` VALUES ('3924', '新疆财经大学', '31', '本科', 'http://www.xjufe.edu.cn/', 'xjufe', '乌鲁木齐');
INSERT INTO `university` VALUES ('3925', '新疆艺术学院', '31', '本科', 'http://www.xjart.edu.cn/', 'xjart', '乌鲁木齐');
INSERT INTO `university` VALUES ('3926', '新疆工程学院', '31', '本科', 'http://www.xjie.edu.cn/', 'xjie', '乌鲁木齐');
INSERT INTO `university` VALUES ('3927', '昌吉学院', '31', '本科', 'http://www.cjc.edu.cn/', 'cjc', '昌吉市');
INSERT INTO `university` VALUES ('3928', '新疆警察学院', '31', '本科', 'http://www.xjjz.cn/', 'xjjz', '乌鲁木齐');
INSERT INTO `university` VALUES ('3929', '新疆大学科学技术学院', '31', '本科', 'http://www.kj.xju.edu.cn/', 'kj', '乌鲁木齐');
INSERT INTO `university` VALUES ('3930', '新疆农业大学科学技术学院', '31', '本科', 'http://www.xjstc.net/', 'xjstc', '乌鲁木齐');
INSERT INTO `university` VALUES ('3931', '新疆医科大学厚博学院', '31', '本科', 'http://www1.xjmu.edu.cn/hbxy/i', 'xjmu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3932', '新疆财经大学商务学院', '31', '本科', 'http://www.swxyzsw.cn/', 'swxyzsw', '乌鲁木齐');
INSERT INTO `university` VALUES ('3933', '石河子大学科技学院', '31', '本科', 'http://kjxy.shzu.edu.cn/struct', 'shzu', '石河子市');
INSERT INTO `university` VALUES ('3934', '和田师范专科学校', '31', '专科', 'http://www.htszedu.cn/', 'htszedu', '和田市');
INSERT INTO `university` VALUES ('3935', '新疆农业职业技术学院', '31', '专科', 'http://www.xjnzy.edu.cn/', 'xjnzy', '昌吉市');
INSERT INTO `university` VALUES ('3936', '乌鲁木齐职业大学', '31', '专科', 'http://www.uvu.edu.cn/', 'uvu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3937', '新疆维吾尔医学专科学校', '31', '专科', 'http://www.xjumc.com/', 'xjumc', '和田市');
INSERT INTO `university` VALUES ('3938', '克拉玛依职业技术学院', '31', '专科', 'http://www.kzjsxy.net/', 'kzjsxy', '克拉玛依');
INSERT INTO `university` VALUES ('3939', '新疆轻工职业技术学院', '31', '专科', 'http://www.xjqg.edu.cn/', 'xjqg', '乌鲁木齐');
INSERT INTO `university` VALUES ('3940', '新疆能源职业技术学院', '31', '专科', 'http://www.xjnyedu.com/', 'xjnyedu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3941', '昌吉职业技术学院', '31', '专科', 'http://www.cjpt.cn/', 'cjpt', '昌吉市');
INSERT INTO `university` VALUES ('3942', '伊犁职业技术学院', '31', '专科', 'http://www.ylzyjs.cn/', 'ylzyjs', '伊宁市');
INSERT INTO `university` VALUES ('3943', '阿克苏职业技术学院', '31', '专科', 'http://www.akszy.com/', 'akszy', '阿克苏');
INSERT INTO `university` VALUES ('3944', '巴音郭楞职业技术学院', '31', '专科', 'http://www.xjbyxy.cn/', 'xjbyxy', '库尔勒');
INSERT INTO `university` VALUES ('3945', '新疆建设职业技术学院', '31', '专科', 'http://www.xjjszy.net/', 'xjjszy', '乌鲁木齐');
INSERT INTO `university` VALUES ('3946', '新疆兵团警官高等专科学校', '31', '专科', 'http://www.xjbtjx.com/', 'xjbtjx', '五家渠市');
INSERT INTO `university` VALUES ('3947', '新疆现代职业技术学院', '31', '专科', 'http://www.xjxiandai.net/', 'xjxiandai', '乌鲁木齐');
INSERT INTO `university` VALUES ('3948', '新疆天山职业技术学院', '31', '专科', 'http://www.xjtsxy.cn/', 'xjtsxy', '乌鲁木齐');
INSERT INTO `university` VALUES ('3949', '新疆交通职业技术学院', '31', '专科', 'http://www.xjjtedu.com/', 'xjjtedu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3950', '新疆石河子职业技术学院', '31', '专科', 'http://www.xjshzzy.com/', 'xjshzzy', '石河子');
INSERT INTO `university` VALUES ('3951', '新疆职业大学', '31', '专科', 'http://www.xjvu.edu.cn/', 'xjvu', '乌鲁木齐');
INSERT INTO `university` VALUES ('3952', '新疆体育职业技术学院', '31', '专科', 'http://www.xjsvtc.com/', 'xjsvtc', '乌鲁木齐');
INSERT INTO `university` VALUES ('3953', '新疆应用职业技术学院', '31', '专科', 'http://www.xjyy.edu.cn/', 'xjyy', '奎屯市');
INSERT INTO `university` VALUES ('3954', '新疆师范高等专科学校', '31', '专科', 'http://www.xjei.cn/', 'xjei', '乌鲁木齐');
INSERT INTO `university` VALUES ('3955', '新疆铁道职业技术学院', '31', '专科', 'http://www.xjtzy.net/', 'xjtzy', '乌鲁木齐');
INSERT INTO `university` VALUES ('3956', '新疆生产建设兵团兴新职业技术学院', '31', '专科', 'http://www.btc.edu.cn/', 'btc', '乌鲁木齐');
INSERT INTO `university` VALUES ('3957', '哈密职业技术学院', '31', '专科', 'http://www.hmzyjsxx.cn/', 'hmzyjsxx', '哈密市');
INSERT INTO `university` VALUES ('3958', '新疆科技职业技术学院', '31', '专科', 'http://www.xjkjzyjsxy.com/', 'xjkjzyjsxy', '乌鲁木齐');
INSERT INTO `university` VALUES ('3959', '香港中文大学', '32', '本科', 'http://www.cuhk.edu.hk/', 'cuhk', '香港');
INSERT INTO `university` VALUES ('3960', '澳门大学', '33', '本科', 'http://www.umac.mo/', 'umac', '澳门');
INSERT INTO `university` VALUES ('3961', '台湾中央大学', '34', null, 'http://www.ncu.edu.tw/', null, '台湾');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `introduction` varchar(200) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `pic_url` varchar(100) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `credit` float(10,0) unsigned zerofill DEFAULT NULL,
  `work_count` int(11) unsigned zerofill DEFAULT NULL,
  `finish_count` int(11) unsigned zerofill DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `work_joiner_id` int(11) DEFAULT NULL,
  `advance_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `FKfbg5mywq8qu5ijci3de50aske` (`work_joiner_id`),
  CONSTRAINT `FKfbg5mywq8qu5ijci3de50aske` FOREIGN KEY (`work_joiner_id`) REFERENCES `work_joiner` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', 'icelee', 'zzzzzz', '程序员', '摩羯座', 'https://www.ice97.cn/download/image/601cacheicelee.jpg', '1367', '0000000004', '00000000003', '00000000000', 'zjl', '5868685848', null, null, null);
INSERT INTO `user` VALUES ('4', 'icelee2', '7777777', '在这种', 'zzzz2', 'https://www.ice97.cn/download/image/372cacheicelee2.jpg', '1367', '0000000003', '00000000003', '00000000002', 'zzz', '', null, null, null);

-- ----------------------------
-- Table structure for `user_event`
-- ----------------------------
DROP TABLE IF EXISTS `user_event`;
CREATE TABLE `user_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_event
-- ----------------------------
INSERT INTO `user_event` VALUES ('9', '3', '已通过', 'work', '4', '5');
INSERT INTO `user_event` VALUES ('18', '1', '待审核', 'work', '3', '1');
INSERT INTO `user_event` VALUES ('19', '2', '待审核', 'work', '3', '1');
INSERT INTO `user_event` VALUES ('21', '5', null, 'activity', '3', '5');
INSERT INTO `user_event` VALUES ('22', '1', null, 'activity', '3', '1');

-- ----------------------------
-- Table structure for `work`
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `start_week` int(11) DEFAULT NULL,
  `end_week` int(11) DEFAULT NULL,
  `weekdays` varchar(20) DEFAULT NULL,
  `start_time` varchar(30) DEFAULT NULL,
  `end_time` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `max_number` int(11) DEFAULT NULL,
  `free_number` int(11) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `create_time` varchar(30) DEFAULT NULL,
  `university_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES ('1', '教学楼值班', '10', '12', '周一、周二、周四', '19:00:00', '21:00:00', '教学楼', '保卫处', '1', '20', '15', '教学楼值班，希望大家要有责任心，按时值班', '2017-12-20 16:40:19', '1367', null);
INSERT INTO `work` VALUES ('2', '操场值班', '10', '12', '周一、周二、周四', '19:00:00', '21:00:00', '教学楼', '保卫处', '1', '20', '15', '教学楼值班，希望大家要有责任心，按时值班', '2017-12-20 16:40:19', '1367', null);
INSERT INTO `work` VALUES ('3', '地表最强兼职', '10', '11', '周一、周日', '06:00', '07:00', '地表最强兼职', '保卫处', '5', '45', '45', '维持秩序2', '2017-12-26 08:48:29', '1367', null);

-- ----------------------------
-- Table structure for `work_joiner`
-- ----------------------------
DROP TABLE IF EXISTS `work_joiner`;
CREATE TABLE `work_joiner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKat91kgvimnew2q1xvur7e0ltu` (`publisher_id`),
  KEY `FKfvvhwcuu74ub24dgu64m5gcc1` (`user_id`),
  CONSTRAINT `FKat91kgvimnew2q1xvur7e0ltu` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`),
  CONSTRAINT `FKfvvhwcuu74ub24dgu64m5gcc1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_joiner
-- ----------------------------
INSERT INTO `work_joiner` VALUES ('1', 'waiting', '1', '3');
