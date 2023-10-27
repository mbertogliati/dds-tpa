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
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `momentoNotificacion` varchar(255) DEFAULT NULL,
  `metodoNotificacion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ubicacion_latitud` float DEFAULT NULL,
  `ubicacion_longitud` float DEFAULT NULL,
  `whatsapp` int(11) DEFAULT NULL,
  `ubicacion_departamento_id` varchar(255) DEFAULT NULL,
  `ubicacion_localidad_id` varchar(255) DEFAULT NULL,
  `ubicacion_provincia_id` varchar(255) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKifsnuid8deaw28b8sqpq1s8qn` (`ubicacion_departamento_id`),
  KEY `FK4cdjexugdqm8dq1myb0bsq4kb` (`ubicacion_localidad_id`),
  KEY `FKtep2lsvpix25j5s5lnljhnm0r` (`ubicacion_provincia_id`),
  KEY `FKn07bnub0pbsug2fxbetrdcutx` (`usuario_id`),
  CONSTRAINT `FK4cdjexugdqm8dq1myb0bsq4kb` FOREIGN KEY (`ubicacion_localidad_id`) REFERENCES `localidades` (`id`),
  CONSTRAINT `FKifsnuid8deaw28b8sqpq1s8qn` FOREIGN KEY (`ubicacion_departamento_id`) REFERENCES `departamentos` (`id`),
  CONSTRAINT `FKn07bnub0pbsug2fxbetrdcutx` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKtep2lsvpix25j5s5lnljhnm0r` FOREIGN KEY (`ubicacion_provincia_id`) REFERENCES `provincias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` VALUES (1,'García','garcia@example.com','AlMomento','WPP','Ana',NULL,NULL,1122334455,NULL,NULL,NULL,NULL),(2,'Martínez','martinez@example.com','SinApuro','MAIL','Carlos',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(3,'López','lopez@example.com','AlMomento','WPP','María',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(4,'Rodríguez','rodriguez@example.com','SinApuro','MAIL','José',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(5,'Fernández','fernandez@example.com','AlMomento','WPP','Laura',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(6,'González','gonzalez@example.com','SinApuro','MAIL','Javier',NULL,NULL,1234509876,NULL,NULL,NULL,NULL),(7,'Pérez','perez@example.com','AlMomento','WPP','Isabel',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(8,'Martín','martin@example.com','SinApuro','MAIL','Antonio',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(9,'Díaz','diaz@example.com','AlMomento','WPP','Marta',NULL,NULL,2147483647,NULL,NULL,NULL,NULL),(10,'Sánchez','sanchez@example.com','SinApuro','MAIL','Pedro',NULL,NULL,1122334455,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
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
