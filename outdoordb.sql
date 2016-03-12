-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: outdoordb
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `brand_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(50) NOT NULL,
  `brand_user_email` varchar(60) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `brand_id_UNIQUE` (`brand_id`),
  KEY `user_email_idx` (`brand_user_email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkin` (
  `checkin_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `checkin_user_email` varchar(60) NOT NULL,
  `date` datetime DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `checkin_place_name` varchar(50) NOT NULL,
  PRIMARY KEY (`checkin_id`),
  UNIQUE KEY `checkin_id_UNIQUE` (`checkin_id`),
  KEY `place_id_idx` (`checkin_place_name`),
  KEY `checkin_user_email` (`checkin_user_email`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin`
--

LOCK TABLES `checkin` WRITE;
/*!40000 ALTER TABLE `checkin` DISABLE KEYS */;
INSERT INTO `checkin` VALUES (17,'osama.fathy@hotmail.com','2015-12-28 07:04:08','Ù…Ø±Ø­Ø¨Ø§ Ø¬Ù…ÙŠØ¹Ø§Ù‹\nØ§Ù†ØªÙ‡ÙŠØª Ù…Ù† Ø£Ø¯Ø§Ø¡ Ø§Ù„Ø¹Ù…Ø±Ø© ÙˆØ§Ù†Ø§ Ø¹Ø§Ø¦Ø¯ Ø§Ù„Ù‰ Ù…ØµØ± ','Mecca'),(16,'ken@gmail.com','2015-12-28 06:15:33','Hi\nGooood morning from Damascus\nThe most beautiful city in the world :)','Damascus'),(18,'ibra@gmail.com','2015-12-28 09:51:07','Hi\nthis is my first check-in via Outdoor :D','Cairo University'),(19,'omar@gmail.com','2015-12-28 10:02:53','Trust me, this place is amazing :)','Sharm El-Sheikh'),(31,'ken@gmail.com','2016-01-05 22:13:02','Anooos','Giza'),(23,'test94@gmail.com','2015-12-29 06:09:24','at home','Giza'),(32,'osama.fathy@hotmail.com','2016-02-08 20:50:01','one','Giza'),(33,'osama.fathy@hotmail.com','2016-02-08 20:50:29','hubhyhbu','Giza'),(34,'osama.fathy@hotmail.com','2016-02-08 20:51:46','hubhyhbu','Giza');
/*!40000 ALTER TABLE `checkin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` mediumtext NOT NULL,
  `comment_user_email` varchar(60) NOT NULL,
  `date` datetime NOT NULL,
  `brand_id` int(10) unsigned DEFAULT NULL,
  `comment_place_id` int(10) unsigned DEFAULT NULL,
  `checkin_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_id_UNIQUE` (`comment_id`),
  KEY `normalUser_email_idx` (`comment_user_email`),
  KEY `brand_id_idx` (`brand_id`),
  KEY `place_id_idx` (`comment_place_id`),
  KEY `checkin_id_idx` (`checkin_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conversation` (
  `conversation_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`conversation_id`),
  UNIQUE KEY `conversation_id_UNIQUE` (`conversation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `creditCard_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `creditCard_user_email` varchar(60) NOT NULL,
  `creditCardNumber` varchar(50) NOT NULL,
  `creaditCardPassword` varchar(50) NOT NULL,
  PRIMARY KEY (`creditCard_id`),
  UNIQUE KEY `creditCard_id_UNIQUE` (`creditCard_id`),
  KEY `user_email_idx` (`creditCard_user_email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list`
--

DROP TABLE IF EXISTS `list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list` (
  `list_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`list_id`),
  UNIQUE KEY `list_id_UNIQUE` (`list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list`
--

LOCK TABLES `list` WRITE;
/*!40000 ALTER TABLE `list` DISABLE KEYS */;
/*!40000 ALTER TABLE `list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list_has_place`
--

DROP TABLE IF EXISTS `list_has_place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_has_place` (
  `list_list_id` int(10) unsigned NOT NULL,
  `place_place_id` int(10) unsigned NOT NULL,
  `list_has_place_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`list_has_place_id`),
  UNIQUE KEY `list_has_place_id_UNIQUE` (`list_has_place_id`),
  KEY `fk_list_has_place_place1_idx` (`place_place_id`),
  KEY `fk_list_has_place_list1_idx` (`list_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_has_place`
--

LOCK TABLES `list_has_place` WRITE;
/*!40000 ALTER TABLE `list_has_place` DISABLE KEYS */;
/*!40000 ALTER TABLE `list_has_place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `location_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `location_id_UNIQUE` (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,5,6),(2,40.5,61),(3,15,102),(4,94,95),(5,10.221,19.78);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `message_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` longtext NOT NULL,
  `date` datetime NOT NULL,
  `message_user_email` varchar(60) NOT NULL,
  `conversation_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  UNIQUE KEY `message_id_UNIQUE` (`message_id`),
  KEY `user_email_idx` (`message_user_email`),
  KEY `conversation_id_idx` (`conversation_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `place` (
  `place_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `place_image` varchar(100) DEFAULT NULL,
  `placeName` varchar(25) NOT NULL,
  `rate` double DEFAULT NULL,
  `numberOfUsers` int(11) DEFAULT NULL,
  `place_user_email` varchar(60) NOT NULL,
  `location_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`place_id`),
  UNIQUE KEY `place_id_UNIQUE` (`place_id`),
  UNIQUE KEY `placeName` (`placeName`),
  KEY `user_email_idx` (`place_user_email`),
  KEY `location_id_idx` (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (1,NULL,'Cairo University',5.5,25,'mis1494@gmail.com',1),(2,NULL,'Giza',6,100,'osama.f@hotmail.com',2),(3,NULL,'Damascus',7,90,'osama.fathy@hotmail.com',3),(4,NULL,'KafrElSheikh',4.5,41,'mis1494@gmail.com',4),(5,NULL,'Mecca',10,400,'osama.f@hotmail.com',5),(6,NULL,'Sharm El-Sheikh',NULL,NULL,'',0);
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taste`
--

DROP TABLE IF EXISTS `taste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taste` (
  `taste_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tasteName` varchar(30) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`taste_id`),
  UNIQUE KEY `tasteName` (`tasteName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taste`
--

LOCK TABLES `taste` WRITE;
/*!40000 ALTER TABLE `taste` DISABLE KEYS */;
/*!40000 ALTER TABLE `taste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_email` varchar(60) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `security_question` varchar(50) DEFAULT NULL,
  `security_answer` varchar(50) DEFAULT NULL,
  `alternative_email` varchar(50) DEFAULT NULL,
  `isPremium` tinyint(1) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `long` double DEFAULT NULL,
  PRIMARY KEY (`user_email`),
  UNIQUE KEY `email_UNIQUE` (`user_email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('test94@gmail.com','Mohammed','.a9bea7a4ceb94f113246454c2918620e.','balala','hahaha','test93@gmail.com',NULL,NULL,NULL),('ken@gmail.com','Hassan','.421f44cf7ccf70201530e25209598b77.','Where are you from?','Syria','ke@gmail.com',NULL,NULL,NULL),('osama.fathy@hotmail.com','Osama','.6eee34cd18a6d30c2fc766b6db2bf07c.','yes','no','osmj2009@gmail.com',NULL,NULL,NULL),('omar@gmail.com','Omar','.d73a3ac19c8baea6cda25bfdca0e30a8.','What is your BD?','25/2/1993','omar2@gmail.com',NULL,NULL,NULL),('ibra@gmail.com','Ibrahim','.16373544a3c17d68942aaf7b313cc69b.','What is your favorite color?','Blue','ibra2@gmail.com',NULL,NULL,NULL),('test','test','test','test','test','test',NULL,NULL,NULL),('a','a','a','b','b','b',NULL,NULL,NULL),('os@so','OsamaFathy','123','what about outdoor?','what about it?','so@os',NULL,123.321,321.123);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_follows_brand`
--

DROP TABLE IF EXISTS `user_follows_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_follows_brand` (
  `user_email` varchar(60) NOT NULL,
  `Brand_brand_id` int(10) unsigned NOT NULL,
  `user_follows_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_follows_id`),
  UNIQUE KEY `user_follows_id_UNIQUE` (`user_follows_id`),
  KEY `fk_normalUser_has_Brand_Brand1_idx` (`Brand_brand_id`),
  KEY `fk_normalUser_has_Brand_normalUser1_idx` (`user_email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_follows_brand`
--

LOCK TABLES `user_follows_brand` WRITE;
/*!40000 ALTER TABLE `user_follows_brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_follows_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_friend`
--

DROP TABLE IF EXISTS `user_has_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_friend` (
  `user_has_friend_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(60) COLLATE latin1_general_ci NOT NULL,
  `friend_user_email` varchar(60) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`user_has_friend_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_friend`
--

LOCK TABLES `user_has_friend` WRITE;
/*!40000 ALTER TABLE `user_has_friend` DISABLE KEYS */;
INSERT INTO `user_has_friend` VALUES (22,'ken@gmail.com','test94@gmail.com'),(21,'test94@gmail.com','test94@gmail.com');
/*!40000 ALTER TABLE `user_has_friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_message`
--

DROP TABLE IF EXISTS `user_has_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_message` (
  `user_has_message_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(60) COLLATE latin1_general_ci NOT NULL,
  `sender_user_email` varchar(60) COLLATE latin1_general_ci NOT NULL,
  `message` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_has_message_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_message`
--

LOCK TABLES `user_has_message` WRITE;
/*!40000 ALTER TABLE `user_has_message` DISABLE KEYS */;
INSERT INTO `user_has_message` VALUES (12,'osama.fathy@hotmail.com','test94@gmail.com','osamaaaaa','2016-01-03 11:18:48'),(11,'test94@gmail.com','osama.fathy@hotmail.com','mashy','2016-01-03 11:13:13'),(10,'osama.fathy@hotmail.com','test94@gmail.com','3abbas el daww bey2ool la2aaaaaaaa','2016-01-03 11:12:47'),(13,'test94@gmail.com','ken@gmail.com','kol khara','2016-01-05 22:14:50');
/*!40000 ALTER TABLE `user_has_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_likes_checkin`
--

DROP TABLE IF EXISTS `user_likes_checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_likes_checkin` (
  `user_email` varchar(60) NOT NULL,
  `checkin_checkin_id` int(10) unsigned NOT NULL,
  `user_likes_checkin_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_likes_checkin_id`),
  UNIQUE KEY `user_likes_checkin_id_UNIQUE` (`user_likes_checkin_id`),
  KEY `fk_user_has_checkin_checkin1_idx` (`checkin_checkin_id`),
  KEY `fk_user_has_checkin_user1_idx` (`user_email`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_likes_checkin`
--

LOCK TABLES `user_likes_checkin` WRITE;
/*!40000 ALTER TABLE `user_likes_checkin` DISABLE KEYS */;
INSERT INTO `user_likes_checkin` VALUES ('test94@gmail.com',7,4),('ken@gmail.com',14,3),('test94@gmail.com',10,5),('ken@gmail.com',16,6),('test94@gmail.com',16,7),('osama.fathy@hotmail.com',16,8),('ken@gmail.com',17,9),('ken@gmail.com',7,10),('ibra@gmail.com',18,11),('ken@gmail.com',19,12),('osama.fathy@hotmail.com',17,13),('osama.fathy@hotmail.com',10,14),('osama.fathy@hotmail.com',7,15),('',19,16),('test94@gmail.com',22,17),('test94@gmail.com',21,18),('osama.fathy@hotmail.com',23,19),('test94@gmail.com',20,20),('osama.fathy@hotmail.com',24,21),('osama.fathy@hotmail.com',19,22),('ken@gmail.com',0,23),('ken@gmail.com',23,24),('test94@gmail.com',26,25),('test94@gmail.com',14,26),('test94@gmail.com',27,27),('ken@gmail.com',26,28),('ken@gmail.com',31,29);
/*!40000 ALTER TABLE `user_likes_checkin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_likes_comment`
--

DROP TABLE IF EXISTS `user_likes_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_likes_comment` (
  `user_email` varchar(60) NOT NULL,
  `comment_comment_id` int(10) unsigned NOT NULL,
  `user_likes_comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_likes_comment_id`),
  UNIQUE KEY `user_likes_comment_id_UNIQUE` (`user_likes_comment_id`),
  KEY `fk_normalUser_has_comment_comment1_idx` (`comment_comment_id`),
  KEY `fk_normalUser_has_comment_normalUser1_idx` (`user_email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_likes_comment`
--

LOCK TABLES `user_likes_comment` WRITE;
/*!40000 ALTER TABLE `user_likes_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_likes_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-12 15:25:10
