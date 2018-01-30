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
-- Creato il: Gen 30, 2018 alle 11:39\
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
-- --------------------------------------------------------\
\
--\
-- Struttura della tabella `events`\
--\
\
CREATE TABLE `events` (\
  `id_event` int(11) NOT NULL,\
  `info` text NOT NULL,\
  `address` varchar(50) DEFAULT NULL,\
  `city` varchar(50) DEFAULT NULL,\
  `name` varchar(50) DEFAULT NULL,\
  `date` date DEFAULT NULL,\
  `start` time DEFAULT NULL,\
  `end` time DEFAULT NULL,\
  `description` text,\
  `location_name` varchar(50) DEFAULT NULL,\
  `img` binary(50) DEFAULT NULL,\
  `payment` tinyint(1) DEFAULT NULL\
) ENGINE=InnoDB DEFAULT CHARSET=utf8;\
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
  MODIFY `id_event` int(11) NOT NULL AUTO_INCREMENT;\
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