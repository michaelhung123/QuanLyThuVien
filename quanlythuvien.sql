-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: quanlythuvien
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `tacgia` varchar(255) NOT NULL,
  `mota` varchar(255) NOT NULL,
  `namxb` int NOT NULL,
  `noixb` varchar(255) NOT NULL,
  `ngaynhap` datetime NOT NULL,
  `vitri_new` text NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_category_idx` (`category_id`),
  CONSTRAINT `fk_book_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Thiên Văn Học ','Sách','Quốc Hưng',2011,'Gò Vấp','2023-04-07 00:00:00','Tang1-K1',2),(2,'Chiêm Tinh Học ','Sách','Quốc Hưng',2011,'Gò Vấp','2023-04-20 00:00:00','Tang1-K3',1),(3,'Cửu Âm Chân Kinh','Quốc Hưng','Sách',2011,'Gò Vấp','2023-04-20 00:00:00','Tang1-K4',2),(4,'Thomas Edison','Quốc Hưng','Sách',2011,'Gò Vấp','2023-04-20 00:00:00','Tang1-K4',3),(5,'Donal Trump','Quốc Hưng','Sách',2011,'Gò Vấp','2023-04-20 00:00:00','Tang1-K4',3);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bophan`
--

DROP TABLE IF EXISTS `bophan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bophan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bophan`
--

LOCK TABLES `bophan` WRITE;
/*!40000 ALTER TABLE `bophan` DISABLE KEYS */;
INSERT INTO `bophan` VALUES (1,'Khoa CNTT'),(2,'Khoa Luat'),(3,'Khoa Marketing');
/*!40000 ALTER TABLE `bophan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Sách Kinh Tế'),(2,'Truyện Tranh '),(3,'Sách Tiểu Thuyết'),(4,'Sách Phổ Thông');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docgia`
--

DROP TABLE IF EXISTS `docgia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docgia` (
  `id` varchar(25) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gioitinh` varchar(32) NOT NULL,
  `ngaysinh` datetime NOT NULL,
  `bophan` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `diachi` varchar(32) NOT NULL,
  `phonenumber` varchar(32) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docgia`
--

LOCK TABLES `docgia` WRITE;
/*!40000 ALTER TABLE `docgia` DISABLE KEYS */;
INSERT INTO `docgia` VALUES ('BA01284','LeVanTuyet','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 04:42:57'),('BA01289','DuQuocHung','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:06:22'),('BA01299','LeVanTuyet','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:08:31'),('BA01300','LeVanTuyet','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:10:10'),('BA01300sss','LeVanTuyet','Nữ','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-07 09:18:39'),('BA01301','LeVanTuyet','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:16:17'),('BA01302','DuQuocHung','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:20:25'),('BA01304','DuQuocHung','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:19:38'),('BA01310','LeVanTuyet','Nam','2023-04-20 00:00:00','Khoa CNTT','zuroken140@gmail.com','GoVap','0901248851','2023-04-05 05:26:06'),('BA014444','Vu Van Tien','Nữ','2023-04-21 00:00:00','Khoa Luat','zuroken14034@gmail.com','GoVap','0901248851','2023-04-07 09:00:52');
/*!40000 ALTER TABLE `docgia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieumuon`
--

DROP TABLE IF EXISTS `phieumuon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieumuon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NOT NULL,
  `docgia_id` varchar(16) NOT NULL,
  `soluong` int NOT NULL DEFAULT '1',
  `ngaymuon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hantra` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_phieumuon_book_idx` (`book_id`),
  KEY `fk_phiemuon_docgia_idx` (`docgia_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieumuon`
--

LOCK TABLES `phieumuon` WRITE;
/*!40000 ALTER TABLE `phieumuon` DISABLE KEYS */;
INSERT INTO `phieumuon` VALUES (11,1,'BA01284',9,'2023-04-07 10:31:04','2023-04-26 00:00:00'),(12,4,'BA01284',1,'2023-04-07 10:43:50','2023-04-26 00:00:00'),(13,5,'BA01284',1,'2023-04-07 10:43:58','2023-02-13 00:00:00'),(14,3,'BA01284',2,'2023-04-07 10:46:10','2024-03-22 00:00:00'),(15,2,'BA01310',2,'2023-04-07 10:47:08','2023-04-08 00:00:00');
/*!40000 ALTER TABLE `phieumuon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456789','2023-04-03 10:16:57'),(2,'thuthu1','123456789','2023-04-03 10:17:11');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vitri`
--

DROP TABLE IF EXISTS `vitri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vitri` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vitri`
--

LOCK TABLES `vitri` WRITE;
/*!40000 ALTER TABLE `vitri` DISABLE KEYS */;
INSERT INTO `vitri` VALUES (1,'Tang1-K1'),(2,'Tang1-K2'),(3,'Tang1-K3'),(4,'Tang1-K4'),(5,'Tang2-K1'),(6,'Tang2-K2'),(7,'Tang2-K3'),(8,'Tang2-K4'),(9,'Tang3-K1'),(10,'Tang3-K2'),(11,'Tang3-K3'),(12,'Tang3-K4'),(13,'Tang4-K1'),(14,'Tang4-K2'),(15,'Tang4-K3'),(16,'Tang4-K4');
/*!40000 ALTER TABLE `vitri` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-07 22:04:09
