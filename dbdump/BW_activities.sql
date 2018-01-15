-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Creato il: Gen 15, 2018 alle 16:38
-- Versione del server: 5.5.42
-- Versione PHP: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `BW_activities`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `activities`
--

CREATE TABLE `activities` (
  `id_activity` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `longi` double DEFAULT NULL,
  `daytime` time DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `info` text NOT NULL,
  `payment` tinyint(1) NOT NULL,
  `img` binary(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `activities_days`
--

CREATE TABLE `activities_days` (
  `id_activity` int(11) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `category_type`
--

CREATE TABLE `category_type` (
  `id_activity` int(11) NOT NULL,
  `category` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `activities`
--
ALTER TABLE `activities`
  ADD PRIMARY KEY (`id_activity`);

--
-- Indici per le tabelle `activities_days`
--
ALTER TABLE `activities_days`
  ADD KEY `id_activity` (`id_activity`);

--
-- Indici per le tabelle `category_type`
--
ALTER TABLE `category_type`
  ADD KEY `id_activity` (`id_activity`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `activities`
--
ALTER TABLE `activities`
  MODIFY `id_activity` int(11) NOT NULL AUTO_INCREMENT;
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
