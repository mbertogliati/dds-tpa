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
-- Table structure for table `entidades`
--

DROP TABLE IF EXISTS `entidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `ubicacion_latitud` float DEFAULT NULL,
  `ubicacion_longitud` float DEFAULT NULL,
  `denominacion_id` int(11) DEFAULT NULL,
  `ubicacion_departamento_id` varchar(255) DEFAULT NULL,
  `ubicacion_localidad_id` varchar(255) DEFAULT NULL,
  `ubicacion_provincia_id` varchar(255) DEFAULT NULL,
  `entidad_prestadora_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6trhnyhmamacb1mpa943u572v` (`denominacion_id`),
  KEY `FK528dqb3cn6bb93jrlhinm5rl2` (`ubicacion_departamento_id`),
  KEY `FKi3d25nfetx5b2qwyta7o8g376` (`ubicacion_localidad_id`),
  KEY `FK29ymk459qmpoc0k7lhtjoqf1v` (`ubicacion_provincia_id`),
  KEY `FKap0oy91t7wdfhvyoyflu02awr` (`entidad_prestadora_id`),
  CONSTRAINT `FK29ymk459qmpoc0k7lhtjoqf1v` FOREIGN KEY (`ubicacion_provincia_id`) REFERENCES `provincias` (`id`),
  CONSTRAINT `FK528dqb3cn6bb93jrlhinm5rl2` FOREIGN KEY (`ubicacion_departamento_id`) REFERENCES `departamentos` (`id`),
  CONSTRAINT `FK6trhnyhmamacb1mpa943u572v` FOREIGN KEY (`denominacion_id`) REFERENCES `denominaciones` (`id`),
  CONSTRAINT `FKap0oy91t7wdfhvyoyflu02awr` FOREIGN KEY (`entidad_prestadora_id`) REFERENCES `entidadesprestadoras` (`id`),
  CONSTRAINT `FKi3d25nfetx5b2qwyta7o8g376` FOREIGN KEY (`ubicacion_localidad_id`) REFERENCES `localidades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entidades`
--

LOCK TABLES `entidades` WRITE;
/*!40000 ALTER TABLE `entidades` DISABLE KEYS */;
INSERT INTO `entidades` VALUES (1,'Banco Nacional Central',NULL,NULL,1,NULL,NULL,NULL,1),(2,'Hospital Metropolitano',NULL,NULL,2,NULL,NULL,NULL,3),(3,'Tienda de Ropa \"Moda Joven\"',NULL,NULL,3,NULL,NULL,NULL,4),(4,'Escuela Primaria ABC',NULL,NULL,4,NULL,NULL,NULL,8),(5,'Agencia de Viajes Mundo Tours',NULL,NULL,5,NULL,NULL,NULL,6),(6,'Farmacia San Juan',NULL,NULL,6,NULL,NULL,NULL,9),(7,'Estación de Tren Central',NULL,NULL,7,NULL,NULL,NULL,2),(8,'Biblioteca Pública Municipal',NULL,NULL,8,NULL,NULL,NULL,5),(9,'Gimnasio Fitness Plus',NULL,NULL,9,NULL,NULL,NULL,10),(10,'Supermercado Mega',NULL,NULL,10,NULL,NULL,NULL,7),(11,'Clínica de Salud XYZ',NULL,NULL,2,NULL,NULL,NULL,3),(12,'Librería \"El Sabio\"',NULL,NULL,3,NULL,NULL,NULL,1),(13,'Centro de Educación \"Aprende Más\"',NULL,NULL,4,NULL,NULL,NULL,8),(14,'Farmacia \"La Salud\"',NULL,NULL,6,NULL,NULL,NULL,9),(15,'Estación de Autobuses Central',NULL,NULL,5,NULL,NULL,NULL,6),(16,'Banco Internacional',NULL,NULL,1,NULL,NULL,NULL,1),(17,'Hospital Infantil',NULL,NULL,2,NULL,NULL,NULL,3),(18,'Tienda de Electrónica \"TecnoWorld\"',NULL,NULL,3,NULL,NULL,NULL,4),(19,'Escuela de Música \"Armonía\"',NULL,NULL,4,NULL,NULL,NULL,8),(20,'Supermercado \"Económico\"',NULL,NULL,10,NULL,NULL,NULL,10);
/*!40000 ALTER TABLE `entidades` ENABLE KEYS */;
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
