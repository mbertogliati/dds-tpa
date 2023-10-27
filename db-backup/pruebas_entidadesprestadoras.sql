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
-- Table structure for table `entidadesprestadoras`
--

DROP TABLE IF EXISTS `entidadesprestadoras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entidadesprestadoras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `personaAInformar_id` int(11) DEFAULT NULL,
  `organismo_control_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7wxs02g74kim1asbqrf5949gx` (`personaAInformar_id`),
  KEY `FKa0lrcymd3ebib2js3wanvqv53` (`organismo_control_id`),
  CONSTRAINT `FK7wxs02g74kim1asbqrf5949gx` FOREIGN KEY (`personaAInformar_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `FKa0lrcymd3ebib2js3wanvqv53` FOREIGN KEY (`organismo_control_id`) REFERENCES `organismosdecontrol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entidadesprestadoras`
--

LOCK TABLES `entidadesprestadoras` WRITE;
/*!40000 ALTER TABLE `entidadesprestadoras` DISABLE KEYS */;
INSERT INTO `entidadesprestadoras` VALUES (1,'Salud Total',NULL,NULL),(2,'Seguros de Vida Universal',NULL,NULL),(3,'Clínica San José',NULL,NULL),(4,'MediCare Plus',NULL,NULL),(5,'Centro de Rehabilitación Física',NULL,NULL),(6,'Banco Nacional',NULL,NULL),(7,'Compañía de Transporte Aéreo',NULL,NULL),(8,'Agencia de Viajes',NULL,NULL),(9,'Escuela de Idiomas',NULL,NULL),(10,'Instituto de Investigación Financiera',NULL,NULL);
/*!40000 ALTER TABLE `entidadesprestadoras` ENABLE KEYS */;
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
