/*
SQLyog v10.2 
MySQL - 5.7.18-log : Database - sys_stu_manger
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sys_stu_manger` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sys_stu_manger`;

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(20) DEFAULT NULL COMMENT '院系',
  `grade` varchar(10) DEFAULT NULL COMMENT '年级',
  `classes_card` varchar(15) DEFAULT NULL COMMENT '班级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `classes` */

insert  into `classes`(`id`,`department`,`grade`,`classes_card`) values (1,'建筑学院','大一','201506301'),(2,'信息技术学院','大二','201606302'),(3,'美容学院','大三','201406401'),(4,'物流学院','大一','2014064011'),(5,'机电与汽修','大一','2014064012'),(6,'护理学院','大一','2014064013'),(8,'医学院','大一','2014064014');

/*Table structure for table `dormitory` */

DROP TABLE IF EXISTS `dormitory`;

CREATE TABLE `dormitory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(50) DEFAULT NULL COMMENT '院系',
  `build` varchar(10) DEFAULT NULL COMMENT '楼栋',
  `dorm_num` varchar(10) DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `dormitory` */

insert  into `dormitory`(`id`,`department`,`build`,`dorm_num`) values (1,'建筑学院','一栋','206'),(2,'信息技术学院','三栋','105'),(4,'护理学院','2','302'),(5,'美容学院','3','405'),(6,'机电与汽修','4','506'),(7,'建筑学院','四栋','608'),(8,'12','12','12'),(9,'医学院','二栋','705'),(10,'1','1','89'),(11,'1','12','45'),(12,'1','12','13');

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(20) DEFAULT NULL COMMENT '科目',
  `mark` decimal(3,1) DEFAULT NULL COMMENT '分数',
  `stu_id` int(11) DEFAULT NULL COMMENT '学生id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `score` */

insert  into `score`(`id`,`subject`,`mark`,`stu_id`) values (1,'java','95.0',1),(2,'mysql','12.0',2),(4,'html','56.0',7),(5,'ps','99.0',19),(6,'java','60.0',8),(7,'java','61.0',11),(8,'java','62.0',12);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_number` varchar(15) DEFAULT NULL COMMENT '学号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `classes_id` int(11) DEFAULT NULL COMMENT '班级id',
  `dormitory_id` int(11) DEFAULT NULL COMMENT '宿舍id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`stu_number`,`name`,`sex`,`classes_id`,`dormitory_id`,`create_time`,`update_time`) values (1,'20150630113','pyq','男',1,1,'2018-12-19 16:25:04','2018-12-19 16:25:04'),(2,'20150630115','bbb','男',1,1,'2018-12-19 16:25:04','2018-12-19 16:25:04'),(7,'20150630109','qqq','男',8,9,'2018-12-19 16:25:04','2018-12-20 15:07:46'),(8,'20150630114','www','gay',1,1,'2018-12-19 16:25:04','2018-12-20 15:08:22'),(11,'20150640212','eee','未知',2,2,'2018-12-19 20:09:45','2018-12-19 20:09:45'),(12,'20150630119','rrr','女',2,2,'2018-12-19 20:13:55','2018-12-19 20:13:55'),(13,'20150640515','ttt','女',3,4,'2018-12-19 20:15:57','2018-12-19 20:15:57'),(14,'20150650236','yyy','男',4,5,'2018-12-19 20:17:56','2018-12-19 20:17:56'),(15,'20160523012','uuu','女',5,6,'2018-12-19 20:21:57','2018-12-19 20:21:57'),(16,'20175892015','aaa','女',6,7,'2018-12-19 20:28:42','2018-12-19 20:28:42'),(17,'20150630108','sss','女',6,7,'2018-12-19 20:29:26','2018-12-19 20:29:26'),(19,'20150630101','ddd','gay',7,8,'2018-12-20 09:32:16','2018-12-20 09:32:16');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
