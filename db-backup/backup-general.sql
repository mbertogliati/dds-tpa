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
-- Table structure for table `comunidades`
--

DROP TABLE IF EXISTS `comunidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detalle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunidades`
--

LOCK TABLES `comunidades` WRITE;
/*!40000 ALTER TABLE `comunidades` DISABLE KEYS */;
INSERT INTO `comunidades` VALUES (1,'Personas con discapacidad motriz en silla de ruedas'),(2,'Adultos mayores con movilidad reducida'),(3,'Madres y padres con cochecitos de bebé'),(4,'Personas con problemas de movilidad temporal debido a lesiones'),(5,'Personas con discapacidad visual o ceguera'),(6,'Personas con discapacidad auditiva o sordera'),(7,'Trabajadores con turnos nocturnos que dependen del transporte público'),(8,'Personas con movilidad reducida debido a obesidad mórbida'),(9,'Personas con enfermedades crónicas que afectan la movilidad'),(10,'Personas con trastornos del espectro autista que pueden tener dificultades en entornos urbanos');
/*!40000 ALTER TABLE `comunidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunidades_servicios`
--

DROP TABLE IF EXISTS `comunidades_servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunidades_servicios` (
  `Comunidad_id` int(11) NOT NULL,
  `servicios_id` int(11) NOT NULL,
  KEY `FKbejtecews5pn7k71x01vmd8c3` (`servicios_id`),
  KEY `FKndbf2b2tt53o2o29eko5kxqyn` (`Comunidad_id`),
  CONSTRAINT `FKbejtecews5pn7k71x01vmd8c3` FOREIGN KEY (`servicios_id`) REFERENCES `servicios` (`id`),
  CONSTRAINT `FKndbf2b2tt53o2o29eko5kxqyn` FOREIGN KEY (`Comunidad_id`) REFERENCES `comunidades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunidades_servicios`
--

LOCK TABLES `comunidades_servicios` WRITE;
/*!40000 ALTER TABLE `comunidades_servicios` DISABLE KEYS */;
/*!40000 ALTER TABLE `comunidades_servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `denominaciones`
--

DROP TABLE IF EXISTS `denominaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `denominaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `denominaciones`
--

LOCK TABLES `denominaciones` WRITE;
/*!40000 ALTER TABLE `denominaciones` DISABLE KEYS */;
INSERT INTO `denominaciones` VALUES (1,'Banco'),(2,'Hospital'),(3,'Comercio'),(4,'Centro Educativo'),(5,'Agencia de Viajes'),(6,'Estación de Tren'),(7,'Farmacia'),(8,'Biblioteca'),(9,'Gimnasio'),(10,'Supermercado');
/*!40000 ALTER TABLE `denominaciones` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `etiquetas`
--

DROP TABLE IF EXISTS `etiquetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etiquetas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etiquetas`
--

LOCK TABLES `etiquetas` WRITE;
/*!40000 ALTER TABLE `etiquetas` DISABLE KEYS */;
INSERT INTO `etiquetas` VALUES (1,'Categoría','Servicio Público'),(2,'Categoría','Accesibilidad'),(3,'Tipo','Baño'),(4,'Tipo','Ascensor'),(5,'Accesibilidad','Para Ciegos'),(6,'Accesibilidad','Para Sordos'),(7,'Ubicación','Ciudad');
/*!40000 ALTER TABLE `etiquetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fechasdesemana`
--

DROP TABLE IF EXISTS `fechasdesemana`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fechasdesemana` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia` varchar(255) DEFAULT NULL,
  `horario` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fechasdesemana`
--

LOCK TABLES `fechasdesemana` WRITE;
/*!40000 ALTER TABLE `fechasdesemana` DISABLE KEYS */;
/*!40000 ALTER TABLE `fechasdesemana` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidentes`
--

DROP TABLE IF EXISTS `incidentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidentes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_hora_apertura` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `observaciones` varchar(255) DEFAULT NULL,
  `autor_apertura_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpqclmes8ao5p0hpvlde2t3jfr` (`autor_apertura_id`),
  CONSTRAINT `FKpqclmes8ao5p0hpvlde2t3jfr` FOREIGN KEY (`autor_apertura_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidentes`
--

LOCK TABLES `incidentes` WRITE;
/*!40000 ALTER TABLE `incidentes` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidentes` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `incidentes_serviciosprestados`
--

DROP TABLE IF EXISTS `incidentes_serviciosprestados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidentes_serviciosprestados` (
  `Incidente_id` int(11) NOT NULL,
  `serviciosAfectados_id` int(11) NOT NULL,
  KEY `FK8cf3il1va3sqrohnhwsoymjc3` (`serviciosAfectados_id`),
  KEY `FKtlb7jlit9f50nmhs8wo97nq56` (`Incidente_id`),
  CONSTRAINT `FK8cf3il1va3sqrohnhwsoymjc3` FOREIGN KEY (`serviciosAfectados_id`) REFERENCES `serviciosprestados` (`id`),
  CONSTRAINT `FKtlb7jlit9f50nmhs8wo97nq56` FOREIGN KEY (`Incidente_id`) REFERENCES `incidentes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidentes_serviciosprestados`
--

LOCK TABLES `incidentes_serviciosprestados` WRITE;
/*!40000 ALTER TABLE `incidentes_serviciosprestados` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidentes_serviciosprestados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interes_entidades`
--

DROP TABLE IF EXISTS `interes_entidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interes_entidades` (
  `Persona_id` int(11) NOT NULL,
  `entidades_id` int(11) NOT NULL,
  KEY `FK3xy808bf7xpl48fv5nqyy66mo` (`entidades_id`),
  KEY `FKlsblg7i5hs9v1own3q507cruc` (`Persona_id`),
  CONSTRAINT `FK3xy808bf7xpl48fv5nqyy66mo` FOREIGN KEY (`entidades_id`) REFERENCES `entidades` (`id`),
  CONSTRAINT `FKlsblg7i5hs9v1own3q507cruc` FOREIGN KEY (`Persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interes_entidades`
--

LOCK TABLES `interes_entidades` WRITE;
/*!40000 ALTER TABLE `interes_entidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `interes_entidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interes_serviciosprestados`
--

DROP TABLE IF EXISTS `interes_serviciosprestados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interes_serviciosprestados` (
  `Persona_id` int(11) NOT NULL,
  `servicios_id` int(11) NOT NULL,
  KEY `FKkxa8shbj9qvbukoh7hd6q69nn` (`servicios_id`),
  KEY `FK7eysfb7eleh9kbwc2cpvjd2rb` (`Persona_id`),
  CONSTRAINT `FK7eysfb7eleh9kbwc2cpvjd2rb` FOREIGN KEY (`Persona_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `FKkxa8shbj9qvbukoh7hd6q69nn` FOREIGN KEY (`servicios_id`) REFERENCES `serviciosprestados` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interes_serviciosprestados`
--

LOCK TABLES `interes_serviciosprestados` WRITE;
/*!40000 ALTER TABLE `interes_serviciosprestados` DISABLE KEYS */;
/*!40000 ALTER TABLE `interes_serviciosprestados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localidades`
--

DROP TABLE IF EXISTS `localidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `localidades` (
  `id` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `departamento_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpk5yicbu40eiqukwe9hrugix0` (`departamento_id`),
  CONSTRAINT `FKpk5yicbu40eiqukwe9hrugix0` FOREIGN KEY (`departamento_id`) REFERENCES `departamentos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localidades`
--

--
-- Table structure for table `membresias`
--

DROP TABLE IF EXISTS `membresias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membresias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comunidad_id` int(11) DEFAULT NULL,
  `persona_id` int(11) DEFAULT NULL,
  `rolComunidad_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55isqwjnmoou1ilx1hsfs3nxl` (`comunidad_id`),
  KEY `FKfm15w2y2e55ytxqnt33wbsbww` (`persona_id`),
  KEY `FK2xahgc7iusirc6xteu3la3tj` (`rolComunidad_id`),
  CONSTRAINT `FK2xahgc7iusirc6xteu3la3tj` FOREIGN KEY (`rolComunidad_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK55isqwjnmoou1ilx1hsfs3nxl` FOREIGN KEY (`comunidad_id`) REFERENCES `comunidades` (`id`),
  CONSTRAINT `FKfm15w2y2e55ytxqnt33wbsbww` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membresias`
--

LOCK TABLES `membresias` WRITE;
/*!40000 ALTER TABLE `membresias` DISABLE KEYS */;
INSERT INTO `membresias` VALUES (1,1,1,1),(2,2,2,2),(3,3,3,3),(4,4,4,4),(5,5,5,5),(6,1,6,1),(7,2,7,2),(8,3,8,3),(9,4,9,4),(10,5,10,5),(11,1,2,1),(12,2,3,2),(13,3,4,3),(14,4,5,4),(15,5,6,5);
/*!40000 ALTER TABLE `membresias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membresias_servicios`
--

DROP TABLE IF EXISTS `membresias_servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membresias_servicios` (
  `Membresia_id` int(11) NOT NULL,
  `serviciosObservados_id` int(11) NOT NULL,
  KEY `FK585diwirafbmkv9sc1wmwewot` (`serviciosObservados_id`),
  KEY `FK28c9u11u8t3o9rr0jp58kl0se` (`Membresia_id`),
  CONSTRAINT `FK28c9u11u8t3o9rr0jp58kl0se` FOREIGN KEY (`Membresia_id`) REFERENCES `membresias` (`id`),
  CONSTRAINT `FK585diwirafbmkv9sc1wmwewot` FOREIGN KEY (`serviciosObservados_id`) REFERENCES `servicios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membresias_servicios`
--

LOCK TABLES `membresias_servicios` WRITE;
/*!40000 ALTER TABLE `membresias_servicios` DISABLE KEYS */;
/*!40000 ALTER TABLE `membresias_servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificableconfecha`
--

DROP TABLE IF EXISTS `notificableconfecha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificableconfecha` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificableconfecha`
--

LOCK TABLES `notificableconfecha` WRITE;
/*!40000 ALTER TABLE `notificableconfecha` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificableconfecha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organismosdecontrol`
--

DROP TABLE IF EXISTS `organismosdecontrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organismosdecontrol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `personaAInformar_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKop54p5co75jf1xs94dl6jdt7d` (`personaAInformar_id`),
  CONSTRAINT `FKop54p5co75jf1xs94dl6jdt7d` FOREIGN KEY (`personaAInformar_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organismosdecontrol`
--

LOCK TABLES `organismosdecontrol` WRITE;
/*!40000 ALTER TABLE `organismosdecontrol` DISABLE KEYS */;
INSERT INTO `organismosdecontrol` VALUES (11,'Organismo de Control 1',1),(12,'Organismo de Control 2',2),(13,'Organismo de Control 3',3),(14,'Organismo de Control 4',4),(15,'Organismo de Control 5',5),(16,'Organismo de Control 6',6),(17,'Organismo de Control 7',7),(18,'Organismo de Control 8',8),(19,'Organismo de Control 9',9),(20,'Organismo de Control 10',10);
/*!40000 ALTER TABLE `organismosdecontrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detalles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona_notificablessinnotificar`
--

DROP TABLE IF EXISTS `persona_notificablessinnotificar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_notificablessinnotificar` (
  `Persona_id` int(11) NOT NULL,
  `notificables_id` int(11) NOT NULL,
  KEY `FKh874excy3u8xt32gncv5kxa7x` (`notificables_id`),
  KEY `FKi8ts0u7m5cc8bqkyb4muwagh1` (`Persona_id`),
  CONSTRAINT `FKh874excy3u8xt32gncv5kxa7x` FOREIGN KEY (`notificables_id`) REFERENCES `notificableconfecha` (`id`),
  CONSTRAINT `FKi8ts0u7m5cc8bqkyb4muwagh1` FOREIGN KEY (`Persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona_notificablessinnotificar`
--

LOCK TABLES `persona_notificablessinnotificar` WRITE;
/*!40000 ALTER TABLE `persona_notificablessinnotificar` DISABLE KEYS */;
/*!40000 ALTER TABLE `persona_notificablessinnotificar` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `personas_fechasdesemana`
--

DROP TABLE IF EXISTS `personas_fechasdesemana`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personas_fechasdesemana` (
  `Persona_id` int(11) NOT NULL,
  `fechas_id` int(11) NOT NULL,
  KEY `FKoitb1p7jcayu28e8ojr2ttsqp` (`fechas_id`),
  KEY `FKsjfq2u8nas839nur0ta662so9` (`Persona_id`),
  CONSTRAINT `FKoitb1p7jcayu28e8ojr2ttsqp` FOREIGN KEY (`fechas_id`) REFERENCES `fechasdesemana` (`id`),
  CONSTRAINT `FKsjfq2u8nas839nur0ta662so9` FOREIGN KEY (`Persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas_fechasdesemana`
--

LOCK TABLES `personas_fechasdesemana` WRITE;
/*!40000 ALTER TABLE `personas_fechasdesemana` DISABLE KEYS */;
/*!40000 ALTER TABLE `personas_fechasdesemana` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincias`
--

DROP TABLE IF EXISTS `provincias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincias` (
  `id` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincias`
--

--
-- Table structure for table `puntosporentidades`
--

DROP TABLE IF EXISTS `puntosporentidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puntosporentidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `puntos` double DEFAULT NULL,
  `entidad_id` int(11) DEFAULT NULL,
  `ranking_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjo9d4auem79umwts9wxxibmtd` (`entidad_id`),
  KEY `FKhq9wp2gquuvwaq7guqa006che` (`ranking_id`),
  CONSTRAINT `FKhq9wp2gquuvwaq7guqa006che` FOREIGN KEY (`ranking_id`) REFERENCES `rankings` (`id`),
  CONSTRAINT `FKjo9d4auem79umwts9wxxibmtd` FOREIGN KEY (`entidad_id`) REFERENCES `entidades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntosporentidades`
--

LOCK TABLES `puntosporentidades` WRITE;
/*!40000 ALTER TABLE `puntosporentidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `puntosporentidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rankings`
--

DROP TABLE IF EXISTS `rankings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rankings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_hora_creacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rankings`
--

LOCK TABLES `rankings` WRITE;
/*!40000 ALTER TABLE `rankings` DISABLE KEYS */;
/*!40000 ALTER TABLE `rankings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1),(2),(3),(4),(5);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_permisos`
--

DROP TABLE IF EXISTS `roles_permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_permisos` (
  `Rol_id` int(11) NOT NULL,
  `permisos_id` int(11) NOT NULL,
  KEY `FKnmdv1c7tnncnre57gms7moba8` (`permisos_id`),
  KEY `FKl329kgcyvbmslitgb94702jh7` (`Rol_id`),
  CONSTRAINT `FKl329kgcyvbmslitgb94702jh7` FOREIGN KEY (`Rol_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKnmdv1c7tnncnre57gms7moba8` FOREIGN KEY (`permisos_id`) REFERENCES `permisos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permisos`
--

LOCK TABLES `roles_permisos` WRITE;
/*!40000 ALTER TABLE `roles_permisos` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'Baño'),(2,'Ascensor'),(3,'Servicio de Información'),(4,'Área de Descanso'),(5,'Punto de Recarga de Teléfonos'),(6,'Área de Juegos para Niños'),(7,'Puesto de Primeros Auxilios'),(8,'Estación de Carga para Dispositivos Electrónicos'),(9,'Sala de Espera'),(10,'Mesa de Ayuda'),(11,'Área de Almuerzo'),(12,'Sala de Conferencias'),(13,'Área de Lectura'),(14,'Oficina de Información Turística'),(15,'Área para Mascotas');
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `serviciosprestados`
--

DROP TABLE IF EXISTS `serviciosprestados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviciosprestados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `establecimiento_id` int(11) DEFAULT NULL,
  `servicio_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmw2aii27ptmuh8du9m0qb3b1a` (`establecimiento_id`),
  KEY `FKp83tnmni8s7v5bue6sxhwxpxp` (`servicio_id`),
  CONSTRAINT `FKmw2aii27ptmuh8du9m0qb3b1a` FOREIGN KEY (`establecimiento_id`) REFERENCES `establecimientos` (`id`),
  CONSTRAINT `FKp83tnmni8s7v5bue6sxhwxpxp` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviciosprestados`
--

LOCK TABLES `serviciosprestados` WRITE;
/*!40000 ALTER TABLE `serviciosprestados` DISABLE KEYS */;
INSERT INTO `serviciosprestados` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5),(6,6,6),(7,7,7),(8,8,8),(9,9,9),(10,10,10),(11,11,11),(12,12,12),(13,13,13),(14,14,14),(15,15,15);
/*!40000 ALTER TABLE `serviciosprestados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `rolPlataforma_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20j7c86n39mw0kdtw7d31upvi` (`rolPlataforma_id`),
  CONSTRAINT `FK20j7c86n39mw0kdtw7d31upvi` FOREIGN KEY (`rolPlataforma_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24 21:28:12
