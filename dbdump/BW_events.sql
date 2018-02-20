-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Creato il: Feb 20, 2018 alle 17:16
-- Versione del server: 5.5.42
-- Versione PHP: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `BW_events`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `events`
--

CREATE TABLE `events` (
  `id_event` int(11) NOT NULL,
  `info` text,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `description` text,
  `location_name` varchar(50) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL,
  `payment` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `events`
--

INSERT INTO `events` (`id_event`, `info`, `address`, `city`, `name`, `start`, `end`, `description`, `location_name`, `img`, `payment`) VALUES
(8, 'Music Fest', 'Via Perbellini', 'L''Aquila', 'Music Fest', '2018-02-28 08:00:00', '2018-02-28 21:00:00', 'Music Festival', 'Via Perbellini', NULL, 0),
(9, 'Exposition', 'Via Aragona', 'Rome', 'Picasso e le sue oprete', '2018-03-14 08:00:00', '2018-03-16 00:00:00', 'Exhibition of paintings', 'Il Museale', NULL, 1),
(10, 'Biopark visit', 'Piazzale del Giardino Zoologico', 'Rome', 'Visita al Bioparco', '2018-03-04 12:00:00', '2018-03-04 21:00:00', 'Biopark visit', 'Bioparco di Roma', NULL, 1),
(11, 'Vatican museums visit', 'Viale Vaticano', 'Rome', 'Vatican museums', '2018-04-01 10:00:00', '2018-04-01 21:00:00', 'Vatican museums visit', 'Musei Vaticani', NULL, 1),
(12, 'Rock concert', 'Viale dei Gladiatori', 'Rome', 'Rock in Roma', '2018-03-10 18:00:00', '2018-03-11 05:00:00', 'Rock concert', 'Stadio Olimpico', NULL, 1),
(13, 'indie Festival', 'Viale dei Gladiatori', 'Rome', 'Indie Festival', '2018-03-10 18:00:00', '2018-03-10 23:00:00', 'indie Festival', 'Indie Bar', NULL, 0),
(14, 'Match Serie A', 'Via Filadelfia', 'Torino', '22° giornata serie A "Torino-Milan"', '2018-02-25 15:00:00', '2018-02-25 18:00:00', '22° giornata serie A "Juventus-Milan"', 'Stadio Olimpico Grande Torino', NULL, 1),
(15, 'Match Serie A', 'Corso Gaetano Scirea', 'Torino', '22° giornata serie A "Juventus-Milan"', '2018-02-25 15:00:00', '2018-02-25 18:00:00', '22° giornata serie A "Juventus-Milan"', 'Juventus Stadium', NULL, 1),
(16, 'Biopark visit "Animali della savana"', 'Piazzale del Giardino Zoologico', 'Rome', 'Visita al Bioparco - "Animali della savana"', '2018-03-04 12:00:00', '2018-03-04 21:00:00', 'Biopark visit "Animali della savana"', 'Bioparco di Roma', NULL, 1),
(19, 'Pista: 20 euro con shot Tavoli: 30 euro a persona (minimo 5 pax) (1 bottiglia ogni 5 pax)\n\n', 'Via Tagliamento, 9', 'Rome', 'Una vita da libidine: Jerry calÃ  al Piper', '2018-03-01 23:30:00', '2018-03-02 03:00:00', 'Venerdi 23 febbraio 2018 Piper Club presenta â??Jerry Calaâ?? â?? Una Vita Da Libidine", un evento imperdibile per gli amanti del genere. Sul palco dello storico club di via Tagliamento, locale cult per intere generazioni di romani, arriva il protagonista di molti film di successo: Vacanze di Natale â??83, Bomber, Yuppies, Sapore di Mare...â??\n', 'Piper', 'http://2.citynews-romatoday.stgy.ovh/~media/original-hi/62466332260427/jerry-cala-2.jpg', 1),
(20, 'terralta.it', 'Via dell''Almone, 105', 'Rome', 'Sagra della polenta di Lariano', '2018-03-06 11:00:00', '2018-03-07 00:30:00', 'Roma Ã¨ il comune metropolitano agricolo piÃ¹ grande, per estensione e per area coltivata, dâ??Europa. La Regione Lazio possiede oltre 100 aree classificate â??protetteâ?? tra parchi, riserve e luoghi di vario genere, dove si sono sviluppate nel corso degli anni circa 135 aziende agroalimentari, le quali attualmente producono 334 prodotti tipici regionali su 650 totali, provenienti da coltivazione tradizionale e biologica, a marchio DOP, IGP, DOC, DOCG ed IGT.\n\nIl settore dellâ??agroindustria, nel Lazio, rappresenta il 18% del Pil regionale ed aumenta sempre piÃ¹ la richiesta di cibo italiano buono e sano.\nLazio, Natura e Gusto vuole mettere a contatto le realtÃ  contadine e rurali regionali con i cittadini romani, promuovendo la qualitÃ  dei prodotti coltivati e trasformati ed i sistemi di lavorazione, al fine di valorizzare il patrimonio naturalistico, ed accrescere lo sviluppo della filiera corta e la commercializzazione dei prodotti a km 0.', 'Parco Egeria', 'http://3.citynews-romatoday.stgy.ovh/~media/original-hi/33063708308580/sagra-polenta-2.jpg"', 0),
(21, 'sito: wiadroma.it\n', 'Via di Monte Testaccio, 34', 'Rome', 'World IA Day Rome', '2018-03-14 13:00:00', '2018-03-14 20:00:00', 'Quali linguaggi comuni e strumenti di comunicazione condivisi si possono utilizzare per dialogare con altre culture, lâ??importanza della progettazione incentrata sulle persone, e il suo valore per il raggiungimento di obiettivi sociali, di fiducia e di coesione: su queste tematiche si confronteranno gli speaker della terza edizione del WIAD Rome - World Information Architecture Rome, in programma per il 24 Febbraio presso il Latte Creative Coworking.', 'Latte Creative Coworking', 'http://3.citynews-romatoday.stgy.ovh/~media/original-hi/56779173730355/wiad2018-2.jpg', 0),
(22, 'info', 'addd', 'city', 'name', '2018-02-20 02:00:00', '2018-02-20 04:00:00', 'desciptio', 'loca', 'img', 1),
(23, 'Adulti: € 16,00 Studenti, over 65, disabili: € 14,00 Bambini sotto i tre anni: gratuito (accompagnati da un familiare) Ragazzi: (4-13 anni) € 12,00 Famiglia: 2 adulti e 2 bambini: € 44,00 Maxi famiglia: 2 adulti + 3 Bambini: € 50,00', 'Via Girolamo Iundo, 4', 'Rome', 'The Art of the Brick', '2018-03-05 11:00:00', '2018-03-05 21:00:00', 'The Art of the brick: DC Super Heroes. I fan dei personaggi della DC e gli amanti dei LEGO® potranno godere, anche a Roma, dell’esperienza di THE ART OF THE BRICK: DC SUPER HEROES, la mostra  realizzata con grandi sculture fatte di mattoncini LEGO e ispirate agli eroi ma anche ai cattivi della DC, tra cui Batman, Superman, Wonder Woman, Joker e Harley Quinn.', 'Palazzo degli Esami', 'http://1.citynews-romatoday.stgy.ovh/~media/original-hi/1601970791845/ww-2.jpg', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id_event`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `events`
--
ALTER TABLE `events`
  MODIFY `id_event` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;