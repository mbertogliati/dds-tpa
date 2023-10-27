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
-- Table structure for table `establecimientos`
--

DROP TABLE IF EXISTS `establecimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `establecimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `ubicacion_latitud` float DEFAULT NULL,
  `ubicacion_longitud` float DEFAULT NULL,
  `denominacion_id` int(11) DEFAULT NULL,
  `entidad_id` int(11) DEFAULT NULL,
  `ubicacion_departamento_id` varchar(255) DEFAULT NULL,
  `ubicacion_localidad_id` varchar(255) DEFAULT NULL,
  `ubicacion_provincia_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9251h1te1fq8jrbqu9g0i5p34` (`denominacion_id`),
  KEY `FKr86rbwx8ceodsoxmi9rnllr8e` (`entidad_id`),
  KEY `FKi8jvqo0lak41l6pgdi290819s` (`ubicacion_departamento_id`),
  KEY `FKdc7lgi733fqsahjsk9pd5wpfr` (`ubicacion_localidad_id`),
  KEY `FKbty6nj64bf2gi0nbl7rvowla5` (`ubicacion_provincia_id`),
  CONSTRAINT `FK9251h1te1fq8jrbqu9g0i5p34` FOREIGN KEY (`denominacion_id`) REFERENCES `denominaciones` (`id`),
  CONSTRAINT `FKbty6nj64bf2gi0nbl7rvowla5` FOREIGN KEY (`ubicacion_provincia_id`) REFERENCES `provincias` (`id`),
  CONSTRAINT `FKdc7lgi733fqsahjsk9pd5wpfr` FOREIGN KEY (`ubicacion_localidad_id`) REFERENCES `localidades` (`id`),
  CONSTRAINT `FKi8jvqo0lak41l6pgdi290819s` FOREIGN KEY (`ubicacion_departamento_id`) REFERENCES `departamentos` (`id`),
  CONSTRAINT `FKr86rbwx8ceodsoxmi9rnllr8e` FOREIGN KEY (`entidad_id`) REFERENCES `entidades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `establecimientos`
--

LOCK TABLES `establecimientos` WRITE;
/*!40000 ALTER TABLE `establecimientos` DISABLE KEYS */;
INSERT INTO `establecimientos` VALUES (1,'Sucursal A del Banco Nacional Central',NULL,NULL,1,1,NULL,NULL,NULL),(2,'Sucursal B del Banco Nacional Central',NULL,NULL,1,1,NULL,NULL,NULL),(3,'Hospital Metropolitano - Departamento de Pediatría',NULL,NULL,2,2,NULL,NULL,NULL),(4,'Hospital Metropolitano - Departamento de Cirugía',NULL,NULL,2,2,NULL,NULL,NULL),(5,'Moda Joven - Sucursal A',NULL,NULL,3,3,NULL,NULL,NULL),(6,'Moda Joven - Sucursal B',NULL,NULL,3,3,NULL,NULL,NULL),(7,'Escuela Primaria ABC - Turno Matutino',NULL,NULL,4,4,NULL,NULL,NULL),(8,'Escuela Primaria ABC - Turno Vespertino',NULL,NULL,4,4,NULL,NULL,NULL),(9,'Mundo Tours - Sucursal Principal',NULL,NULL,5,5,NULL,NULL,NULL),(10,'Mundo Tours - Sucursal en el Centro',NULL,NULL,5,5,NULL,NULL,NULL),(11,'Farmacia San Juan - Sucursal A',NULL,NULL,6,6,NULL,NULL,NULL),(12,'Farmacia San Juan - Sucursal B',NULL,NULL,6,6,NULL,NULL,NULL),(13,'Estación de Tren Central - Andén 1',NULL,NULL,7,7,NULL,NULL,NULL),(14,'Estación de Tren Central - Andén 2',NULL,NULL,7,7,NULL,NULL,NULL),(15,'Biblioteca Pública Municipal - Sucursal Principal',NULL,NULL,8,8,NULL,NULL,NULL),(16,'Biblioteca Pública Municipal - Sucursal en el Centro',NULL,NULL,8,8,NULL,NULL,NULL),(17,'Fitness Plus - Sucursal A',NULL,NULL,9,9,NULL,NULL,NULL),(18,'Fitness Plus - Sucursal B',NULL,NULL,9,9,NULL,NULL,NULL),(19,'Supermercado Mega - Sucursal Principal',NULL,NULL,10,10,NULL,NULL,NULL),(20,'Supermercado Mega - Sucursal en el Centro',NULL,NULL,10,10,NULL,NULL,NULL);
/*!40000 ALTER TABLE `establecimientos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24 21:27:02
