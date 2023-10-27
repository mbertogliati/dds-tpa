-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: pruebas
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

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
-- Table structure for table `departamentos`
--

DROP TABLE IF EXISTS `departamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departamentos` (
  `id` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `provincia_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlep1mywk3oj7ykwywd372875` (`provincia_id`),
  CONSTRAINT `FKlep1mywk3oj7ykwywd372875` FOREIGN KEY (`provincia_id`) REFERENCES `provincias` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamentos`
--

LOCK TABLES `departamentos` WRITE;
/*!40000 ALTER TABLE `departamentos` DISABLE KEYS */;
INSERT INTO `departamentos` VALUES ('54007','Apóstoles','54'),('54014','Cainguás','54'),('54021','Candelaria','54'),('54028','Capital','54'),('54035','Concepción','54'),('54042','Eldorado','54'),('54049','General Manuel Belgrano','54'),('54056','Guaraní','54'),('54063','Iguazú','54'),('54070','Leandro N. Alem','54'),('54077','Libertador General San Martín','54'),('54084','Montecarlo','54'),('54091','Oberá','54'),('54098','San Ignacio','54'),('54105','San Javier','54'),('54112','San Pedro','54'),('54119','25 de Mayo','54'),('70007','Albardón','70'),('70014','Angaco','70'),('70021','Calingasta','70'),('70028','Capital','70'),('70035','Caucete','70'),('70042','Chimbas','70'),('70049','Iglesia','70'),('70056','Jáchal','70'),('70063','9 de Julio','70'),('70070','Pocito','70'),('70077','Rawson','70'),('70084','Rivadavia','70'),('70091','San Martín','70'),('70098','Santa Lucía','70'),('70105','Sarmiento','70'),('70112','Ullum','70'),('70119','Valle Fértil','70'),('70126','25 de Mayo','70'),('70133','Zonda','70'),('74007','Ayacucho','74'),('74014','Belgrano','74'),('74021','Coronel Pringles','74'),('74028','Chacabuco','74'),('74035','General Pedernera','74'),('74042','Gobernador Dupuy','74'),('74049','Junín','74'),('74056','Juan Martín de Pueyrredón','74'),('74063','Libertador General San Martín','74');
/*!40000 ALTER TABLE `departamentos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24 21:27:01
