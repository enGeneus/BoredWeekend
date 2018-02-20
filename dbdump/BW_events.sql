-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Feb 20, 2018 alle 13:28
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
-- Database: `bw_events`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `category_type`
--

DROP TABLE IF EXISTS `category_type`;
CREATE TABLE IF NOT EXISTS `category_type` (
  `id_event` int(11) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  KEY `id_event` (`id_event`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `category_type`
--

INSERT INTO `category_type` (`id_event`, `category`) VALUES
(13, 'LiveMusic'),
(12, 'LiveMusic'),
(8, 'LiveMusic'),
(9, 'Culture'),
(11, 'Culture'),
(10, 'Culture'),
(14, 'Sport'),
(15, 'Sport'),
(16, 'Culture');

-- --------------------------------------------------------

--
-- Struttura della tabella `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
  `id_event` int(11) NOT NULL AUTO_INCREMENT,
  `info` text,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `description` text,
  `location_name` varchar(50) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL,
  `payment` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_event`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `events`
--

INSERT INTO `events` (`id_event`, `info`, `address`, `city`, `name`, `start`, `end`, `description`, `location_name`, `img`, `payment`) VALUES
(8, 'Music Fest', 'Via Perbellini', 'L\'Aquila', 'Music Fest', '2018-02-28 08:00:00', '2018-02-28 21:00:00', 'Music Festival', 'Via Perbellini', NULL, 0),
(9, 'Exposition', 'Via Aragona', 'Rome', 'Picasso e le sue oprete', '2018-03-14 08:00:00', '2018-03-16 00:00:00', 'Exhibition of paintings', 'Il Museale', NULL, 1),
(10, 'Biopark visit', 'Piazzale del Giardino Zoologico', 'Rome', 'Visita al Bioparco', '2018-03-04 12:00:00', '2018-03-04 21:00:00', 'Biopark visit', 'Bioparco di Roma', NULL, 1),
(11, 'Vatican museums visit', 'Viale Vaticano', 'Rome', 'Vatican museums', '2018-04-01 10:00:00', '2018-04-01 21:00:00', 'Vatican museums visit', 'Musei Vaticani', NULL, 1),
(12, 'Rock concert', 'Viale dei Gladiatori', 'Rome', 'Rock in Roma', '2018-03-10 18:00:00', '2018-03-11 05:00:00', 'Rock concert', 'Stadio Olimpico', NULL, 1),
(13, 'indie Festival', 'Viale dei Gladiatori', 'Rome', 'Indie Festival', '2018-03-10 18:00:00', '2018-03-10 23:00:00', 'indie Festival', 'Indie Bar', NULL, 0),
(14, 'Match Serie A', 'Via Filadelfia', 'Torino', '22° giornata serie A \"Torino-Milan\"', '2018-02-25 15:00:00', '2018-02-25 18:00:00', '22° giornata serie A \"Juventus-Milan\"', 'Stadio Olimpico Grande Torino', NULL, 1),
(15, 'Match Serie A', 'Corso Gaetano Scirea', 'Torino', '22° giornata serie A \"Juventus-Milan\"', '2018-02-25 15:00:00', '2018-02-25 18:00:00', '22° giornata serie A \"Juventus-Milan\"', 'Juventus Stadium', NULL, 1),
(16, 'Biopark visit \"Animali della savana\"', 'Piazzale del Giardino Zoologico', 'Rome', 'Visita al Bioparco - \"Animali della savana\"', '2018-03-04 12:00:00', '2018-03-04 21:00:00', 'Biopark visit \"Animali della savana\"', 'Bioparco di Roma', NULL, 1);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `category_type`
--
ALTER TABLE `category_type`
  ADD CONSTRAINT `category_type_ibfk_1` FOREIGN KEY (`id_event`) REFERENCES `events` (`id_event`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
