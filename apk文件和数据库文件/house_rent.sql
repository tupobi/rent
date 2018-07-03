/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.20-log : Database - house_rent
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`house_rent` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `house_rent`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `userName` varchar(32) NOT NULL,
  `houseName` varchar(32) NOT NULL,
  `content` varchar(512) NOT NULL,
  `date` varchar(32) NOT NULL,
  `avatarUrl` varchar(512) NOT NULL,
  PRIMARY KEY (`userName`,`houseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `history` */

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `userName` varchar(32) NOT NULL,
  `houseName` varchar(32) NOT NULL,
  `history` int(4) DEFAULT NULL,
  `collect` int(4) DEFAULT NULL,
  PRIMARY KEY (`userName`,`houseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `history` */

/*Table structure for table `house` */

DROP TABLE IF EXISTS `house`;

CREATE TABLE `house` (
  `userName` varchar(32) NOT NULL,
  `houseName` varchar(32) NOT NULL,
  `houseCity` varchar(16) NOT NULL,
  `houseArea` varchar(16) NOT NULL,
  `houseTyle` varchar(16) NOT NULL,
  `houseTitle` varchar(64) NOT NULL,
  `monthPrice` int(4) NOT NULL,
  `houseDescription` text NOT NULL,
  `houseLocation` varchar(128) NOT NULL,
  `ownerPhone` varchar(16) DEFAULT NULL,
  `pic1Url` varchar(1024) NOT NULL,
  `pic2Url` varchar(1024) DEFAULT NULL,
  `pic3Url` varchar(1024) DEFAULT NULL,
  `pic4Url` varchar(1024) DEFAULT NULL,
  `pic5Url` varchar(1024) DEFAULT NULL,
  `pic6Url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`houseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `house` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(4) NOT NULL AUTO_INCREMENT,
  `userName` varchar(36) NOT NULL,
  `userPassword` varchar(36) NOT NULL,
  `userType` int(11) NOT NULL,
  `avatarUrl` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`userId`,`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
