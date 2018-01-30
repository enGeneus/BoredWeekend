-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Gen 30, 2018 alle 11:56
-- Versione del server: 5.7.19
-- Versione PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bw_activities`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `activities`
--

DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (
  `id_activity` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `longi` double DEFAULT NULL,
  `daytime` varchar(11) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `info` text NOT NULL,
  `payment` tinyint(1) NOT NULL,
  `img` binary(50) DEFAULT NULL,
  PRIMARY KEY (`id_activity`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `activities`
--

INSERT INTO `activities` (`id_activity`, `name`, `city`, `lat`, `longi`, `daytime`, `state`, `info`, `payment`, `img`) VALUES
(1, 'Escursione', 'L\'Aquila', 42.21, 13.23, 'Morning', 1, '', 0, NULL),
(2, 'Museo', 'Rome', 41.53, 12.28, 'FullDay', 1, '', 1, NULL),
(3, 'Ciaspolata', 'L\'Aquila', 42.21, 13.23, 'DayLight', 1, '', 0, NULL),
(4, 'Giovedì Universitario', 'L\'Aquila', 42.21, 13.23, 'Night', 1, '', 0, NULL),
(5, 'Guida Colosseo', 'Rome', 41.53, 12.28, 'DayLight', 1, '', 1, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `activities_days`
--

DROP TABLE IF EXISTS `activities_days`;
CREATE TABLE IF NOT EXISTS `activities_days` (
  `id_activity` int(11) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL,
  KEY `id_activity` (`id_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `activities_days`
--

INSERT INTO `activities_days` (`id_activity`, `day`) VALUES
(1, 'Monday'),
(2, 'Friday'),
(2, 'Monday'),
(2, 'Wednesday'),
(3, 'Tuesday'),
(3, 'Thursday'),
(3, 'Saturday'),
(4, 'Thursday'),
(5, 'Monday'),
(5, 'Tuesday'),
(5, 'Wednesday'),
(5, 'Thursday'),
(5, 'Friday');

-- --------------------------------------------------------

--
-- Struttura della tabella `category_type`
--

DROP TABLE IF EXISTS `category_type`;
CREATE TABLE IF NOT EXISTS `category_type` (
  `id_activity` int(11) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  KEY `id_activity` (`id_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `category_type`
--

INSERT INTO `category_type` (`id_activity`, `category`) VALUES
(1, 'Outdoor'),
(1, 'Nature'),
(2, 'Family'),
(2, 'Culture'),
(3, 'Nature'),
(3, 'Outdoor'),
(4, 'CityLife'),
(5, 'Culture'),
(5, 'Family');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `activities_days`
--
ALTER TABLE `activities_days`
  ADD CONSTRAINT `activities_days_ibfk_1` FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id_activity`);

--
-- Limiti per la tabella `category_type`
--
ALTER TABLE `category_type`
  ADD CONSTRAINT `category_type_ibfk_1` FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id_activity`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
