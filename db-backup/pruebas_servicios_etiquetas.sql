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
-- Table structure for table `servicios_etiquetas`
--

DROP TABLE IF EXISTS `servicios_etiquetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios_etiquetas` (
  `Servicio_id` int(11) NOT NULL,
  `etiquetas_id` int(11) NOT NULL,
  KEY `FK727ax1928jd35ck9k54s65w2u` (`etiquetas_id`),
  KEY `FKdh1c67lgffbcq2cu755bbbixs` (`Servicio_id`),
  CONSTRAINT `FK727ax1928jd35ck9k54s65w2u` FOREIGN KEY (`etiquetas_id`) REFERENCES `etiquetas` (`id`),
  CONSTRAINT `FKdh1c67lgffbcq2cu755bbbixs` FOREIGN KEY (`Servicio_id`) REFERENCES `servicios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios_etiquetas`
--

LOCK TABLES `servicios_etiquetas` WRITE;
/*!40000 ALTER TABLE `servicios_etiquetas` DISABLE KEYS */;
INSERT INTO `servicios_etiquetas` VALUES (1,1),(1,3),(1,5),(2,1),(2,3),(2,5),(3,1),(3,5),(3,6),(4,1),(4,5),(4,7),(5,1),(5,5),(5,7),(6,1),(6,5),(6,7),(7,1),(7,5),(7,6),(8,1),(8,5),(8,6),(9,1),(9,5),(9,7),(10,1),(10,5),(10,7),(11,1),(11,5),(11,7),(12,1),(12,5),(12,7),(13,1),(13,5),(13,7),(14,1),(14,5),(14,7),(15,1),(15,5),(15,7);
/*!40000 ALTER TABLE `servicios_etiquetas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24 21:27:03
