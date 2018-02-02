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
-- Creato il: Feb 02, 2018 alle 18:11\
-- Versione del server: 5.5.42\
-- Versione PHP: 5.6.10\
\
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";\
SET time_zone = "+00:00";\
\
--\
-- Database: `BW_events`\
--\
\
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `category_type`\
--\
\
CREATE TABLE `category_type` (\
  `id_event` int(11) NOT NULL,\
  `category` varchar(50) DEFAULT NULL\
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\
\
--\
-- Dump dei dati per la tabella `category_type`\
--\
\
INSERT INTO `category_type` (`id_event`, `category`) VALUES\
(5, 'Culture'),\
(6, 'Culture'),\
(7, 'Culture');\
\
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `events`\
--\
\
CREATE TABLE `events` (\
  `id_event` int(11) NOT NULL,\
  `info` text,\
  `address` varchar(50) DEFAULT NULL,\
  `city` varchar(50) DEFAULT NULL,\
  `name` varchar(50) DEFAULT NULL,\
  `date` date DEFAULT NULL,\
  `start` datetime DEFAULT NULL,\
  `end` datetime DEFAULT NULL,\
  `description` text,\
  `location_name` varchar(50) DEFAULT NULL,\
  `img` binary(50) DEFAULT NULL,\
  `payment` tinyint(1) DEFAULT NULL\
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;\
\
--\
-- Dump dei dati per la tabella `events`\
--\
\
INSERT INTO `events` (`id_event`, `info`, `address`, `city`, `name`, `date`, `start`, `end`, `description`, `location_name`, `img`, `payment`) VALUES\
(1, 'ciao', 'asp', 'Rome', 'asp', '2018-02-02', '2008-00-00 00:00:00', '2014-00-00 00:00:00', 'asdas', 'asdasd', NULL, 1),\
(2, 'pesca della trota', 'monvalico', 'Rome', 'trotata', '2018-02-07', '2018-02-07 08:00:00', '2018-02-07 14:00:00', 'trotata tra amici', 'val di trota', NULL, 0),\
(3, NULL, NULL, NULL, NULL, NULL, '2018-02-07 07:00:00', '2018-02-02 17:00:00', NULL, NULL, NULL, NULL),\
(4, NULL, NULL, 'LOndra', 'Trotata2.0', '2019-02-02', '2019-02-02 08:00:00', '2019-02-02 17:00:00', 'Beh , c\'e8 altro da dire?', 'trotaland', NULL, 1),\
(5, 'Seconda edizione della trotata maxima', 'Via dal trota 28', 'Rome', 'Trotata2.0', '2019-02-02', '2019-02-02 08:00:00', '2019-02-02 17:00:00', 'Beh , c\'e8 altro da dire?', 'trotaland', NULL, 1),\
(6, 'Seconda edizione della trotata maxima', 'Via dal trota 28', 'L''Ondra', 'Trotata2.0', '2019-02-02', '2019-02-02 08:00:00', '2019-02-02 17:00:00', 'Beh , c''\'e8 altro da dire?', 'trotaland', NULL, 1),\
(7, 'Seconda edizione della trotata maxima', 'Via dal trota 28', 'L''Ondra', 'Trotata 3.0', '2020-02-02', '2020-02-02 08:00:00', '2019-02-02 17:00:00', 'Beh , c''\'e8 altro da dire?', 'trotaland', NULL, 1);\
\
--\
-- Indici per le tabelle scaricate\
--\
\
--\
-- Indici per le tabelle `category_type`\
--\
ALTER TABLE `category_type`\
  ADD KEY `id_event` (`id_event`);\
\
--\
-- Indici per le tabelle `events`\
--\
ALTER TABLE `events`\
  ADD PRIMARY KEY (`id_event`);\
\
--\
-- AUTO_INCREMENT per le tabelle scaricate\
--\
\
--\
-- AUTO_INCREMENT per la tabella `events`\
--\
ALTER TABLE `events`\
  MODIFY `id_event` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;\
--\
-- Limiti per le tabelle scaricate\
--\
\
--\
-- Limiti per la tabella `category_type`\
--\
ALTER TABLE `category_type`\
  ADD CONSTRAINT `category_type_ibfk_1` FOREIGN KEY (`id_event`) REFERENCES `events` (`id_event`);\
}