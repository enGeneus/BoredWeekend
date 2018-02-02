{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf200
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 -- phpMyAdmin SQL Dump\
-- version 4.4.10\
-- http://www.phpmyadmin.net\
--\
-- Host: localhost:3306\
-- Creato il: Feb 02, 2018 alle 18:10\
-- Versione del server: 5.5.42\
-- Versione PHP: 5.6.10\
\
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";\
SET time_zone = "+00:00";\
\
--\
-- Database: `BW_activities`\
--\
\
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `activities`\
--\
\
CREATE TABLE `activities` (\
  `id_activity` int(11) NOT NULL,\
  `name` varchar(100) DEFAULT NULL,\
  `city` varchar(100) DEFAULT NULL,\
  `lat` double DEFAULT NULL,\
  `longi` double DEFAULT NULL,\
  `daytime` varchar(11) DEFAULT NULL,\
  `state` tinyint(1) DEFAULT NULL,\
  `info` text,\
  `payment` tinyint(1) DEFAULT NULL,\
  `img` binary(50) DEFAULT NULL\
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;\
\
--\
-- Dump dei dati per la tabella `activities`\
--\
\
INSERT INTO `activities` (`id_activity`, `name`, `city`, `lat`, `longi`, `daytime`, `state`, `info`, `payment`, `img`) VALUES\
(1, 'Escursione', 'L''Aquila', 42.21, 13.23, 'Morning', 1, '', 0, NULL),\
(2, 'Museo', 'Rome', 41.53, 12.28, 'FullDay', 1, '', 1, NULL),\
(3, 'Ciaspolata', 'L''Aquila', 42.21, 13.23, 'DayLight', 1, '', 0, NULL),\
(4, 'Gioved\'ec Universitario', 'L''Aquila', 42.21, 13.23, 'Night', 1, '', 0, NULL),\
(5, 'Guida Colosseo', 'Rome', 41.53, 12.28, 'DayLight', 1, '', 1, NULL),\
(8, 'stocazzo', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),\
(9, 'stocazzo', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),\
(10, 'stocazzo', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),\
(11, 'Gatto', 'Rome', 40, 40, 'FULL_DAY', 1, 'Stocazzo', 1, NULL),\
(12, 'Gatto', 'Rome', 40, 40, 'FULL_DAY', 1, 'Stocazzo', 1, NULL),\
(13, 'Gatto', 'Rome', 40, 40, 'FULL_DAY', 1, 'Stocazzo', 1, NULL);\
\
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `activities_days`\
--\
\
CREATE TABLE `activities_days` (\
  `id_activity` int(11) DEFAULT NULL,\
  `day` varchar(20) DEFAULT NULL\
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\
\
--\
-- Dump dei dati per la tabella `activities_days`\
--\
\
INSERT INTO `activities_days` (`id_activity`, `day`) VALUES\
(1, 'Monday'),\
(2, 'Friday'),\
(2, 'Monday'),\
(2, 'Wednesday'),\
(3, 'Tuesday'),\
(3, 'Thursday'),\
(3, 'Saturday'),\
(4, 'Thursday'),\
(5, 'Monday'),\
(5, 'Tuesday'),\
(5, 'Wednesday'),\
(5, 'Thursday'),\
(5, 'Friday'),\
(12, 'MONDAY'),\
(12, 'SATURDAY'),\
(13, 'MONDAY'),\
(13, 'SATURDAY');\
\
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `category_type`\
--\
\
CREATE TABLE `category_type` (\
  `id_activity` int(11) NOT NULL,\
  `category` varchar(50) DEFAULT NULL\
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\
\
--\
-- Dump dei dati per la tabella `category_type`\
--\
\
INSERT INTO `category_type` (`id_activity`, `category`) VALUES\
(1, 'Outdoor'),\
(1, 'Nature'),\
(2, 'Family'),\
(2, 'Culture'),\
(3, 'Nature'),\
(3, 'Outdoor'),\
(4, 'CityLife'),\
(5, 'Culture'),\
(5, 'Family'),\
(12, 'NATURE'),\
(12, 'null'),\
(13, 'NATURE'),\
(13, 'SPORT');\
\
--\
-- Indici per le tabelle scaricate\
--\
\
--\
-- Indici per le tabelle `activities`\
--\
ALTER TABLE `activities`\
  ADD PRIMARY KEY (`id_activity`);\
\
--\
-- Indici per le tabelle `activities_days`\
--\
ALTER TABLE `activities_days`\
  ADD KEY `id_activity` (`id_activity`);\
\
--\
-- Indici per le tabelle `category_type`\
--\
ALTER TABLE `category_type`\
  ADD KEY `id_activity` (`id_activity`);\
\
--\
-- AUTO_INCREMENT per le tabelle scaricate\
--\
\
--\
-- AUTO_INCREMENT per la tabella `activities`\
--\
ALTER TABLE `activities`\
  MODIFY `id_activity` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;\
--\
-- Limiti per le tabelle scaricate\
--\
\
--\
-- Limiti per la tabella `activities_days`\
--\
ALTER TABLE `activities_days`\
  ADD CONSTRAINT `activities_days_ibfk_1` FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id_activity`);\
\
--\
-- Limiti per la tabella `category_type`\
--\
ALTER TABLE `category_type`\
  ADD CONSTRAINT `category_type_ibfk_1` FOREIGN KEY (`id_activity`) REFERENCES `activities` (`id_activity`);\
}