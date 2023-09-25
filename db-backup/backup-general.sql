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

LOCK TABLES `departamentos` WRITE;
/*!40000 ALTER TABLE `departamentos` DISABLE KEYS */;
INSERT INTO `departamentos` VALUES ('54007','Apóstoles','54'),('54014','Cainguás','54'),('54021','Candelaria','54'),('54028','Capital','54'),('54035','Concepción','54'),('54042','Eldorado','54'),('54049','General Manuel Belgrano','54'),('54056','Guaraní','54'),('54063','Iguazú','54'),('54070','Leandro N. Alem','54'),('54077','Libertador General San Martín','54'),('54084','Montecarlo','54'),('54091','Oberá','54'),('54098','San Ignacio','54'),('54105','San Javier','54'),('54112','San Pedro','54'),('54119','25 de Mayo','54'),('70007','Albardón','70'),('70014','Angaco','70'),('70021','Calingasta','70'),('70028','Capital','70'),('70035','Caucete','70'),('70042','Chimbas','70'),('70049','Iglesia','70'),('70056','Jáchal','70'),('70063','9 de Julio','70'),('70070','Pocito','70'),('70077','Rawson','70'),('70084','Rivadavia','70'),('70091','San Martín','70'),('70098','Santa Lucía','70'),('70105','Sarmiento','70'),('70112','Ullum','70'),('70119','Valle Fértil','70'),('70126','25 de Mayo','70'),('70133','Zonda','70'),('74007','Ayacucho','74'),('74014','Belgrano','74'),('74021','Coronel Pringles','74'),('74028','Chacabuco','74'),('74035','General Pedernera','74'),('74042','Gobernador Dupuy','74'),('74049','Junín','74'),('74056','Juan Martín de Pueyrredón','74'),('74063','Libertador General San Martín','74');
/*!40000 ALTER TABLE `departamentos` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `localidades` WRITE;
/*!40000 ALTER TABLE `localidades` DISABLE KEYS */;
INSERT INTO `localidades` VALUES ('54007010000','APOSTOLES','54007'),('54007020000','AZARA','54007'),('54007025000','BARRIO RURAL','54007'),('54007030000','ESTACION APOSTOLES','54007'),('54007040000','PINDAPOY','54007'),('54007050000','RINCON DE AZARA','54007'),('54007060000','SAN JOSE','54007'),('54007070000','TRES CAPONES','54007'),('54014010000','ARISTOBULO DEL VALLE','54014'),('54014020000','CAMPO GRANDE','54014'),('54014030000','DOS DE MAYO NUCLEO I','54014'),('54014030001','NUCLEO I','54014'),('54014030002','NUCLEO II','54014'),('54014050000','DOS DE MAYO NUCLEO III','54014'),('54014055000','KILOMETRO 17 (RUTA 8)','54014'),('54014060000','1 DE MAYO','54014'),('54014070000','PUEBLO ILLIA','54014'),('54014080000','SALTO ENCANTADO','54014'),('54021005000','BARRIO DEL LAGO','54021'),('54021010000','BONPLAND','54021'),('54021020000','CANDELARIA','54021'),('54021030000','CERRO CORA','54021'),('54021040000','LORETO','54021'),('54021050000','MARTIRES','54021'),('54021060000','PROFUNDIDAD','54021'),('54021070000','PUERTO SANTA ANA','54021'),('54021080000','SANTA ANA','54021'),('54028005000','BARRIO NUEVO GARUPA','54028'),('54028010000','GARUPA','54028'),('54028020000','NEMESIO PARMA','54028'),('54028030000','POSADAS','54028'),('54035010000','BARRA CONCEPCION','54035'),('54035020000','CONCEPCION DE LA SIERRA','54035'),('54035030000','LA CORITA','54035'),('54035040000','SANTA MARIA','54035'),('54042010000','COLONIA VICTORIA','54042'),('54042020000','ELDORADO','54042'),('54042030000','MARIA MAGDALENA','54042'),('54042035000','NUEVA DELICIA','54042'),('54042040000','9 DE JULIO','54042'),('54042050000','9 DE JULIO KILOMETRO 20','54042'),('54042055000','PUEBLO NUEVO','54042'),('54042060000','PUERTO MADO','54042'),('54042070000','PUERTO PINARES','54042'),('54042080000','SANTIAGO DE LINIERS','54042'),('54042090000','VALLE HERMOSO','54042'),('54042100000','VILLA ROULET','54042'),('54049010000','ALMIRANTE BROWN','54049'),('54049020000','BERNARDO DE IRIGOYEN','54049'),('54049025000','CABUREI','54049'),('54049030000','DOS HERMANAS','54049'),('54049040000','INTEGRACION','54049'),('54049043000','PIÑALITO NORTE','54049'),('54049045000','PUERTO ANDRESITO','54049'),('54049047000','PUERTO DESEADO','54049'),('54049050000','SAN ANTONIO','54049'),('54056010000','EL SOBERBIO','54056'),('54056020000','FRACRAN','54056'),('54056030000','SAN VICENTE','54056'),('54063010000','ESPERANZA','54063'),('54063020000','LIBERTAD','54063'),('54063030000','PUERTO IGUAZU','54063'),('54063035000','VILLA COOPERATIVA','54063'),('54063040000','WANDA','54063'),('54070010000','ALMAFUERTE','54070'),('54070020000','ARROYO DEL MEDIO','54070'),('54070030000','CAA - YARI','54070'),('54070040000','CERRO AZUL','54070'),('54070050000','DOS ARROYOS','54070'),('54070060000','GOBERNADOR LOPEZ','54070'),('54070070000','LEANDRO N. ALEM','54070'),('54070080000','OLEGARIO V. ANDRADE','54070'),('54070090000','VILLA LIBERTAD','54070'),('54077010000','CAPIOVI','54077'),('54077015000','CAPIOVICIÑO','54077'),('54077020000','EL ALCAZAR','54077'),('54077030000','GARUHAPE','54077'),('54077040000','MBOPICUA','54077'),('54077050000','PUERTO LEONI','54077'),('54077060000','PUERTO RICO','54077'),('54077070000','RUIZ DE MONTOYA','54077'),('54077080000','SAN ALBERTO','54077'),('54077090000','SAN GOTARDO','54077'),('54077100000','SAN MIGUEL','54077'),('54077110000','VILLA AKERMAN','54077'),('54077120000','VILLA URRUTIA','54077'),('54084003000','BARRIO CUATRO BOCAS','54084'),('54084005000','BARRIO GUATAMBU','54084'),('54084007000','BARIO ITA','54084'),('54084010000','CARAGUATAY','54084'),('54084020000','LAHARRAGUE','54084'),('54084030000','MONTECARLO','54084'),('54084040000','PIRAY KILOMETRO 18','54084'),('54084050000','PUERTO PIRAY','54084'),('54084060000','TARUMA','54084'),('54084070000','VILLA PARODI','54084'),('54091010000','ALBERDI','54091'),('54091013000','BARRIO ESCUELA 461','54091'),('54091017000','BARRIO ESCUELA 633','54091'),('54091020000','CAMPO RAMON','54091'),('54091030000','CAMPO VIERA','54091'),('54091040000','EL SALTO','54091'),('54091050000','GENERAL ALVEAR','54091'),('54091060000','GUARANI','54091'),('54091070000','LOS HELECHOS','54091'),('54091080000','OBERA','54091'),('54091090000','PANAMBI','54091'),('54091100000','PANAMBI KILOMETRO 8','54091'),('54091105000','PANAMBI KILOMETRO 15','54091'),('54091110000','SAN MARTIN','54091'),('54091120000','VILLA BONITA','54091'),('54098005000','BARRIO TUNGOIL','54098'),('54098010000','COLONIA POLANA','54098'),('54098020000','CORPUS','54098'),('54098030000','DOMINGO SAVIO','54098'),('54098040000','GENERAL URQUIZA','54098'),('54098050000','GOBERNADOR ROCA','54098'),('54098060000','HELVECIA','54098'),('54098070000','HIPOLITO YRIGOYEN','54098'),('54098080000','JARDIN AMERICA','54098'),('54098090000','OASIS','54098'),('54098100000','ROCA CHICA','54098'),('54098110000','SAN IGNACIO','54098'),('54098120000','SANTO PIPO','54098'),('54105010000','FLORENTINO AMEGHINO','54105'),('54105020000','ITACARUARE','54105'),('54105030000','MOJON GRANDE','54105'),('54105040000','SAN JAVIER','54105'),('54112010000','CRUCE CABALLERO','54112'),('54112020000','PARAISO','54112'),('54112030000','PIÑALITO SUR','54112'),('54112040000','SAN PEDRO','54112'),('54112050000','TOBUNA','54112'),('54119010000','ALBA POSSE','54119'),('54119020000','COLONIA ALICIA','54119'),('54119025000','ALICIA BAJA','54119'),('54119030000','COLONIA AURORA','54119'),('54119040000','SAN FRANCISCO DE ASIS','54119'),('54119050000','SANTA RITA','54119'),('54119060000','25 DE MAYO','54119'),('70007010000','EL RINCON','70007'),('70007020001','CAMPO AFUERA','70007'),('70007020002','LA CAÑADA','70007'),('70007020003','VILLA AMPACAMA','70007'),('70007020004','VILLA GENERAL SAN MARTIN','70007'),('70014010000','LAS TAPIAS','70014'),('70014020001','VILLA EL SALVADOR','70014'),('70014020002','VILLA SEFAIR (TALACASTO)','70014'),('70021010001','BARREAL','70021'),('70021010002','VILLA PITUIL','70021'),('70021020000','CALINGASTA','70021'),('70021030000','TAMBERIAS','70021'),('70028010000','SAN JUAN','70028'),('70035010000','BERMEJO','70035'),('70035015000','BARRIO JUSTO P. CASTRO IV','70035'),('70035020000','CAUCETE','70035'),('70035030000','EL RINCON','70035'),('70035040001','LAS TALAS','70035'),('70035040002','LOS MEDANOS','70035'),('70035050000','MARAYES','70035'),('70035060000','PIE DE PALO','70035'),('70035070000','VALLECITO','70035'),('70035080000','VILLA INDEPENDENCIA','70035'),('70042010001','CHIMBAS','70042'),('70042010002','EL MOGOTE','70042'),('70042010003','VILLA PAULA ALBARRACIN','70042'),('70049010000','ANGUALASTO','70049'),('70049020000','BELLA VISTA','70049'),('70049030000','IGLESIA','70049'),('70049040000','LAS FLORES','70049'),('70049050000','PISMANTA','70049'),('70049060000','RODEO','70049'),('70049070000','TUDCUM','70049'),('70056010000','EL MEDANO','70056'),('70056020000','GRAN CHINA','70056'),('70056030000','HUACO','70056'),('70056040000','MOGNA','70056'),('70056050000','NIQUIVIL','70056'),('70056060001','EL FISCAL','70056'),('70056060002','LA FALDA','70056'),('70056060003','PAMPA VIEJA','70056'),('70056070000','SAN ISIDRO','70056'),('70056080000','SAN JOSE DE JACHAL','70056'),('70056090000','TAMBERIAS','70056'),('70056100000','VILLA MALVINAS ARGENTINAS','70056'),('70056110000','VILLA MERCEDES','70056'),('70063020000','COLONIA FIORITO','70063'),('70063030000','LAS CHACRITAS','70063'),('70063040000','9 DE JULIO','70063'),('70070005000','BARRIO MUNICIPAL','70070'),('70070010000','BARRIO RUTA 40','70070'),('70070020000','CARPINTERIA','70070'),('70070025000','LAS PIEDRITAS','70070'),('70070030000','QUINTO CUARTEL','70070'),('70070040001','LA RINCONADA','70070'),('70070040002','VILLA ABERASTAIN','70070'),('70070050001','VILLA BARBOZA','70070'),('70070050002','VILLA NACUSI','70070'),('70070060000','VILLA CENTENARIO','70070'),('70077010001','EL MEDANITO','70077'),('70077010002','RAWSON','70077'),('70077010003','VILLA KRAUSE','70077'),('70077020000','VILLA BOLAÑOS','70077'),('70084010000','RIVADAVIA','70084'),('70091010000','BARRIO SADOP','70091'),('70091020000','DOS ACEQUIAS','70091'),('70091030000','SAN ISIDRO','70091'),('70091040000','VILLA EL SALVADOR','70091'),('70091050000','VILLA DOMINGUITO','70091'),('70091060000','VILLA DON BOSCO','70091'),('70091070000','VILLA SAN MARTIN','70091'),('70098010001','ALTO DE SIERRA','70098'),('70098010002','COLONIA GUTIERREZ','70098'),('70098010003','SANTA LUCIA','70098'),('70105010000','CAÑADA HONDA','70105'),('70105020000','CIENAGUITA','70105'),('70105030000','COLONIA FISCAL','70105'),('70105040000','DIVISADERO','70105'),('70105050000','GUANACACHE','70105'),('70105060000','LAS LAGUNAS','70105'),('70105070000','LOS BERROS','70105'),('70105080000','PEDERNAL','70105'),('70105090000','PUNTA DEL MEDANO','70105'),('70105100000','VILLA MEDIA AGUA','70105'),('70112010000','VILLA IBAÑEZ','70112'),('70119010000','ASTICA','70119'),('70119020000','BALDE DEL ROSARIO','70119'),('70119030000','CHUCUMA','70119'),('70119040000','LOS BALDECITOS','70119'),('70119050000','USNO','70119'),('70119060000','VILLA SAN AGUSTIN','70119'),('70126010000','EL ENCON','70126'),('70126020000','TUPELI','70126'),('70126030001','LA CHIMBERA','70126'),('70126030002','VILLA BORJAS','70126'),('70126040000','VILLA EL TANGO','70126'),('70126050000','VILLA SANTA ROSA','70126'),('70133010001','VILLA BASILIO NIEVAS','70133'),('70133010002','VILLA TACU','70133'),('74007010000','CANDELARIA','74007'),('74007030000','LEANDRO N. ALEM','74007'),('74007040000','LUJAN','74007'),('74007050000','QUINES','74007'),('74007070000','SAN FRANCISCO DEL MONTE DE ORO','74007'),('74014010000','LA CALERA','74014'),('74014020000','NOGOLI','74014'),('74014030000','VILLA DE LA QUEBRADA','74014'),('74014040000','VILLA GENERAL ROCA','74014'),('74021010000','CAROLINA','74021'),('74021020000','EL TRAPICHE','74021'),('74021025000','ESTANCIA GRANDE','74021'),('74021030000','FRAGA','74021'),('74021040000','LA BAJADA','74021'),('74021050000','LA FLORIDA','74021'),('74021060000','LA TOMA','74021'),('74021070000','RIOCITO','74021'),('74021080000','RIO GRANDE','74021'),('74021090000','SALADILLO','74021'),('74028010000','CONCARAN','74028'),('74028020000','CORTADERAS','74028'),('74028030000','NASCHEL','74028'),('74028040000','PAPAGAYOS','74028'),('74028050000','RENCA','74028'),('74028060000','SAN PABLO','74028'),('74028070000','TILISARAO','74028'),('74028080000','VILLA DEL CARMEN','74028'),('74028090000','VILLA LARCA','74028'),('74035010000','JUAN JORBA','74035'),('74035020000','JUAN LLERENA','74035'),('74035030000','JUSTO DARACT','74035'),('74035040000','LA PUNILLA','74035'),('74035050000','LAVAISSE','74035'),('74035055000','NACION RANQUEL','74035'),('74035060000','SAN JOSE DEL MORRO','74035'),('74035070000','VILLA MERCEDES','74035'),('74035070001','LA RIBERA','74035'),('74035070002','VILLA MERCEDES','74035'),('74035080000','VILLA REYNOLDS','74035'),('74035080001','COUNTRY CLUB LOS CALDENES','74035'),('74035080002','5TA BRIGADA','74035'),('74035090000','VILLA SALLES','74035'),('74042010000','ANCHORENA','74042'),('74042020000','ARIZONA','74042'),('74042030000','BAGUAL','74042'),('74042040000','BATAVIA','74042'),('74042050000','BUENA ESPERANZA','74042'),('74042060000','FORTIN EL PATRIA','74042'),('74042070000','FORTUNA','74042'),('74042080000','LA MAROMA','74042'),('74042090000','LOS OVEROS','74042'),('74042100000','MARTIN DE LOYOLA','74042'),('74042110000','NAHUEL MAPA','74042'),('74042120000','NAVIA','74042'),('74042130000','NUEVA GALIA','74042'),('74042140000','UNION','74042'),('74049010000','CARPINTERIA','74049'),('74049020000','CERRO DE ORO','74049'),('74049030000','LAFINUR','74049'),('74049040000','LOS CAJONES','74049'),('74049050000','LOS MOLLES','74049'),('74049060000','MERLO','74049'),('74049070000','SANTA ROSA DEL CONLARA','74049'),('74049080000','TALITA','74049'),('74056010000','ALTO PELADO','74056'),('74056020000','ALTO PENCOSO','74056'),('74056030000','BALDE','74056'),('74056040000','BEAZLEY','74056'),('74056050000','CAZADOR','74056'),('74056060000','CHOSMES','74056'),('74056070000','DESAGUADERO','74056'),('74056080000','EL VOLCAN','74056'),('74056090000','JARILLA','74056'),('74056100001','CERRO COLORADO','74056'),('74056100002','CRUZ DE PIEDRA','74056'),('74056100003','EL CHORRILLO','74056'),('74056100004','LAS CHACRAS','74056'),('74056100005','SAN ROQUE','74056'),('74056105000','CIUDAD DE LA PUNTA','74056'),('74056110000','MOSMOTA','74056'),('74056120000','POTRERO DE LOS FUNES','74056'),('74056130000','SALINAS DEL BEBEDERO','74056'),('74056140000','SAN JERONIMO','74056'),('74056150000','SAN LUIS','74056'),('74056160000','ZANJITAS','74056'),('74063010000','LA VERTIENTE','74063'),('74063020000','LAS AGUADAS','74063'),('74063030000','LAS CHACRAS','74063'),('74063040000','LAS LAGUNAS','74063'),('74063050000','PASO GRANDE','74063'),('74063060000','POTRERILLO','74063'),('74063070000','SAN MARTIN','74063'),('74063080000','VILLA DE PRAGA','74063');
/*!40000 ALTER TABLE `localidades` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `provincias` WRITE;
/*!40000 ALTER TABLE `provincias` DISABLE KEYS */;
INSERT INTO `provincias` VALUES ('54','Misiones'),('70','San Juan'),('74','San Luis');
/*!40000 ALTER TABLE `provincias` ENABLE KEYS */;
UNLOCK TABLES;

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
