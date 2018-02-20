-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Feb 20, 2018 alle 16:01
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
  `img` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_activity`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `activities`
--

INSERT INTO `activities` (`id_activity`, `name`, `city`, `lat`, `longi`, `daytime`, `state`, `info`, `payment`, `img`) VALUES
(10, 'Musei Vaticani', 'Rome', 41.9027835, 12.4963655, 'FullDay', 1, 'Paintings and sculptures exposition.', 1, 'https://roma.zon.it/wp-content/uploads/sites/7/2016/09/musei-vaticani.jpg'),
(11, 'Colosseo', 'Rome', 41.9027835, 12.4963655, 'FullDay', 1, 'Colosseum guide tour', 1, 'https://i.ytimg.com/vi/g0d_cvtbUnc/hqdefault.jpg'),
(12, 'Skying', 'Campo Imperatore', 42.26, 13.35, 'DayLight', 1, 'SKying in a beatiful landscape!', 1, 'https://upload.wikimedia.org/wikipedia/commons/6/6f/Campo_Imperatore_winter.jpg'),
(13, 'Snowboard', 'Ovindoli', 42.08, 13.31, 'DayLight', 1, 'Snowboard lessons for everyone', 1, 'http://www.mondoneve.it/wp-content/uploads/2013/12/ovindoli-monte-magnola.jpg'),
(14, 'Forte Spagnolo', 'L\'Aquila', 42.21, 13.23, 'FullDay', 1, 'Visit our castle and enjoy our city.', 0, 'https://versolaquila.files.wordpress.com/2012/05/castello-aerea.jpg'),
(15, 'Camping', 'Barrea', 41.45, 13.59, 'FullDay', 1, 'Stay with us and enjoy the nature.', 1, 'http://conoscere.abruzzoturismo.it/repository/img_bca/2671lagodibarrea.jpg'),
(16, 'Piazza dei miracoli', 'Pisa', 43.43, 10.23, 'DayLight', 1, 'Guided tour to visit the city', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPNz9ePlkMVe6DpjcBjSj7w8BDfO56balu-IDRhE16znB9OSBV'),
(17, 'Duomo', 'Florence', 43.7695604, 11.2558136, 'DayLight', 1, 'City tour to visit Florence', 1, 'http://blog.unahotels.it/wp-content/uploads/2015/09/Firenze_panorama-670x446.jpg'),
(18, 'Piazza del Campo', 'Siena', 43.19, 11.19, 'Night', 1, 'Night visit to discover the magic of Siena', 1, 'http://www.sienatours.it/immagini/maps/sera_piazza_del_campo_imgzoom.jpg'),
(25, 'City Sightseeing', 'Rome', 41.9027835, 12.4963655, 'Daylight', 1, 'Suspended between history and legend, myth and beauty, Rome keeps untouched its infinite charm and its wonders.\nNow you can visit those places that made it immortal, observing it from another point of view, from a new and original perspective, thanks to the open tour City SightSeeing Rome.\nOn board the open double-decker red buses you\'ll have the possibility of visiting comfortably all the main sites of the eternal city, from the Roman Forum to the Colosseum, St. Peter and Santa Maria Maggiore, listening to all the information and curiosities about the beauties encountered along the way, thanks to the audio guide in eight languages.', 1, 'http://www.lagenziadiviaggi.it/wp-content/uploads/2017/04/cityseightseeing-1200x545_c.jpg'),
(26, 'Bungee Jumping Pescara', 'Salle', 52.7884639, 1.1237569, 'Daylight', 1, 'The largest bungee center in central-southern Italy, located on the Salle bridge in the province of Pescara, Abruzzo, inside the Majella National Park.', 1, 'http://www.bungeejumpingabruzzo.it/wp-content/uploads/2014/04/IMG_5210.jpg'),
(27, 'Underground Tour', 'Naples', 40.8517746, 14.2681244, 'FullDay', 1, 'In the underground of Naples lies a labyrinth of tunnels, tanks and cavities that form a real city which is the negative of the city on surface. The underground city spreads below the entire old town, its myths and legends are still alive in the imagination of all the neapolitans. The underground of Naples fascinates and impresses for the grandeur of the cavities, and therefore for the spaces, and for the maze of tunnels that cross each other for several miles below the streets and the buildings.\nCome with us into the insides of the city to discover the history and mysteries with the most fascinating tour of Naples, family and kid friendly.', 1, 'https://www.napolisotterranea.org/wp-content/uploads/DUST6526.jpg'),
(28, 'Gondola Tour', 'Venice', 45.4408474, 12.3155151, 'Daylight', 1, 'Enjoy Venice from the vantage point of a gondola ride by being lulled by its gentle movement. Take a ride along the Grand Canal and the smaller canals around Piazza San Marco and the Rialto Bridge.\n\nShare a gondola ride with up to 5 people and slide through the water with a master gondolier who will lead you through the beautiful and mysterious canals of Venice on a peaceful 30 minute ride.\n\nThe canals of Venice each have a special charm that you will discover by participating in this tour. Sail along the Grand Canal, go along smaller watercourses, see the Rialto bridge from an unusual and spectacular point of view.\n\nThe tour ends at the Orseolo Basin behind Piazza San Marco.', 1, 'https://venicegondola.com/wp-content/uploads/2014/04/giro-gondola-venezia-660x330.jpg'),
(29, 'Galleria degli Uffizi', 'Florence', 43.7695604, 11.2558136, 'FullDay', 1, 'The Gallery entirely occupies the first and second floors of the large building constructed between 1560 and 1580 and designed by Giorgio Vasari. It is famous worldwide for its outstanding collections of ancient sculptures and paintings (from the Middle Ages to the Modern period). The collections of paintings from the 14th-century and Renaissance period include some absolute masterpieces: Giotto, Simone Martini, Piero della Francesca, Beato Angelico, Filippo Lippi, Botticelli, Mantegna, Correggio, Leonardo, Raffaello, Michelangelo and Caravaggio, in addition to many precious works by European painters (mainly German, Dutch and Flemish).\n\nMoreover, the Gallery boasts an invaluable collection of ancient statues and busts from the Medici family, which adorns the corridors and consists of ancient Roman copies of lost Greek sculptures.', 1, 'http://img.uffizi.org/wp-content/gallery/home/01.jpg'),
(30, 'La Reggia', 'Caserta', 41.0854362, 14.367553, 'Daylight', 1, 'In 1750 Charles of Bourbon (1716-1788) decided to erect the Palace as the ideal center of the new kingdom of Naples, now autonomous and free from the Spanish rule.\nThe choice of the place where the new administrative capital of the Kingdom would rise fell on the plain of Terra di Lavoro, on the site dominated by the sixteenth-century Palazzo degli Acquaviva.', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-IujigXppFTsx16sn9kc_g-upSHM3XHyzQLuIKMgKXRYNF7eQ'),
(31, 'Museo Egizio', 'Turin', 45.070312, 7.686856499999999, 'FullDay', 1, 'The Museo Egizio is the only museum other than the Cairo Museum that is dedicated solely to Egyptian art and culture. Many international scholars, since the decipherer of Egyptian hieroglyphs Jean-FranÃ§ois Champollion, who came to Turin in 1824, spend much time pouring over the collections. It was Champollion who famously wrote, â??The road to Memphis and Thebes passes through Turinâ??.\n\nThe collections that make up todayâ??s Museum were enlarged by the excavations conducted in Egypt by the Museumâ??s archaeological mission between 1900 and 1935 (a period when finds were divided between the excavators and Egypt).', 1, 'http://www.artspecialday.com/9art/wp-content/uploads/2017/09/slideshow2_low_light-1-1.jpg');

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
(18, 'Sunday'),
(25, 'Monday'),
(25, 'Wednesday'),
(25, 'Friday'),
(25, 'Saturday'),
(25, 'Sunday'),
(26, 'Sunday'),
(26, 'Saturday'),
(27, 'Thursday'),
(27, 'Saturday'),
(27, 'Sunday'),
(28, 'Tuesday'),
(28, 'Saturday'),
(28, 'Sunday'),
(28, 'Friday'),
(28, 'Thursday'),
(29, 'Monday'),
(29, 'Wednesday'),
(29, 'Friday'),
(29, 'Saturday'),
(29, 'Sunday'),
(30, 'Monday'),
(30, 'Wednesday'),
(30, 'Thursday'),
(30, 'Friday'),
(30, 'Saturday'),
(30, 'Sunday'),
(31, 'Monday'),
(31, 'Wednesday'),
(31, 'Friday'),
(31, 'Saturday'),
(31, 'Sunday');

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
(18, 'Family'),
(25, 'Family'),
(25, 'Culture'),
(25, 'CityLife'),
(26, 'Outdoor'),
(26, 'Sport'),
(25, 'Outdoor'),
(27, 'Culture'),
(27, 'Family'),
(28, 'Outdoor'),
(28, 'Culture'),
(28, 'Family'),
(28, 'CityLife'),
(29, 'Culture'),
(29, 'Family'),
(30, 'Culture'),
(30, 'Family'),
(31, 'Family'),
(31, 'Culture');

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
