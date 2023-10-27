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
-- Table structure for table `incidentes_por_comunidad`
--

DROP TABLE IF EXISTS `incidentes_por_comunidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidentes_por_comunidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `esta_cerrado` bit(1) DEFAULT NULL,
  `fecha_hora_cierre` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `autor_cierre_id` int(11) DEFAULT NULL,
  `incidente_id` int(11) DEFAULT NULL,
  `comunidad_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5hudbum8xwl3hmbo7dt1q32dg` (`autor_cierre_id`),
  KEY `FKaxpmshgfo7q57qlh3gucngp` (`incidente_id`),
  KEY `FKir7o9xadjri772tpx0fsf2580` (`comunidad_id`),
  CONSTRAINT `FK5hudbum8xwl3hmbo7dt1q32dg` FOREIGN KEY (`autor_cierre_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `FKaxpmshgfo7q57qlh3gucngp` FOREIGN KEY (`incidente_id`) REFERENCES `incidentes` (`id`),
  CONSTRAINT `FKir7o9xadjri772tpx0fsf2580` FOREIGN KEY (`comunidad_id`) REFERENCES `comunidades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidentes_por_comunidad`
--

LOCK TABLES `incidentes_por_comunidad` WRITE;
/*!40000 ALTER TABLE `incidentes_por_comunidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidentes_por_comunidad` ENABLE KEYS */;
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
