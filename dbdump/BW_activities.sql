-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Feb 16, 2018 alle 18:07
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `activities`
--

INSERT INTO `activities` (`id_activity`, `name`, `city`, `lat`, `longi`, `daytime`, `state`, `info`, `payment`, `img`) VALUES
(10, 'Musei Vaticani', 'Rome', 41.53, 12.28, 'FullDay', 1, 'Paintings and sculptures exposition.', 1, NULL),
(11, 'Colosseo', 'Rome', 41.53, 12.28, 'FullDay', 1, 'Colosseum guide tour', 1, NULL),
(12, 'Skying', 'Campo Imperatore', 42.26, 13.35, 'DayLight', 1, 'SKying in a beatiful landscape!', 1, NULL),
(13, 'Snowboard', 'Ovindoli', 42.08, 13.31, 'DayLight', 1, 'Snowboard lessons for everyone', 1, NULL),
(14, 'Forte Spagnolo', 'L\'Aquila', 42.21, 13.23, 'FullDay', 1, 'Visit our castle and enjoy our city.', 0, NULL),
(15, 'Camping', 'Barrea', 41.45, 13.59, 'FullDay', 0, 'Stay with us and enjoy the nature.', 1, NULL),
(16, 'Piazza dei miracoli', 'Pisa', 43.43, 10.23, 'DayLight', 1, 'Guided tour to visit the city', 1, NULL),
(17, 'Duomo', 'Florence', 43.46, 11.15, 'DayLight', 1, 'City tour to visit Florence', 1, NULL),
(18, 'Piazza del Campo', 'Siena', 43.19, 11.19, 'Night', 1, 'Night visit to discover the magic of Siena', 1, NULL);

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
(10, 'Monday'),
(10, 'Tuesday'),
(10, 'Wedsnesday'),
(10, 'Thursday'),
(10, 'Friday'),
(11, 'Lunedì'),
(11, 'Tuesday'),
(11, 'Wednesday'),
(11, 'Friday'),
(11, 'Sunday'),
(12, 'Monday'),
(12, 'Tuesday'),
(12, 'Wednesday'),
(12, 'Saturday'),
(12, 'Sunday'),
(13, 'Tuesday'),
(13, 'Wednesday'),
(13, 'Friday'),
(13, 'Saturday'),
(13, 'Sunday'),
(14, 'Friday'),
(14, 'Saturday'),
(14, 'Sunday'),
(15, 'Monday'),
(15, 'Thursday'),
(15, 'Wednesday'),
(15, 'Thursday'),
(15, 'Friday'),
(15, 'Saturday'),
(15, 'Sunday'),
(16, 'Monday'),
(16, 'Wednesday'),
(16, 'Friday'),
(16, 'Saturday'),
(16, 'Sunday'),
(17, 'Monday'),
(17, 'Wednesday'),
(17, 'Friday'),
(17, 'Saturday'),
(17, 'Sunday'),
(18, 'Monday'),
(18, 'Wednesday'),
(18, 'Friday'),
(18, 'Saturday'),
(18, 'Sunday');

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
(10, 'Culture'),
(11, 'Culture'),
(12, 'Outdoor'),
(12, 'Nature'),
(12, 'Sport'),
(13, 'Outdoor'),
(13, 'Nature'),
(13, 'Sport'),
(14, 'Culture'),
(14, 'Family'),
(15, 'Outdoor'),
(15, 'Nature'),
(15, 'Sport'),
(10, 'Family'),
(11, 'Family'),
(16, 'Culture'),
(16, 'Family'),
(17, 'Culture'),
(17, 'Family'),
(18, 'Culture'),
(18, 'Family');

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
