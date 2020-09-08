-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 08, 2020 at 05:52 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tweb`
--

-- --------------------------------------------------------

--
-- Table structure for table `corsi`
--

CREATE TABLE `corsi` (
  `Titolo` varchar(30) NOT NULL,
  `descrizione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `corsi`
--

INSERT INTO `corsi` (`Titolo`, `descrizione`) VALUES
('Chimica', 'Pretium nibh ipsum consequat nisl vel pretium lectus quam id. Volutpat maecenas volutpat blandit aliquam etiam.\r\n\r\n'),
('Fisica', 'Tincidunt praesent semper feugiat nibh. Tristique senectus et netus et malesuada fames ac turpis egestas. '),
('Inglese', 'Quisque non tellus orci ac auctor augue. Nulla facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. Dictum sit amet justo donec enim. '),
('Italiano', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. '),
('Latino', 'Commodo sed egestas egestas fringilla phasellus faucibus scelerisque. Aliquet risus feugiat in ante metus. Et molestie ac feugiat sed lectus vestibulum. Vulputate ut pharetra sit amet aliquam id diam maecenas ultricies.'),
('Matematica', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');

-- --------------------------------------------------------

--
-- Table structure for table `docenti`
--

CREATE TABLE `docenti` (
  `nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `docenti`
--

INSERT INTO `docenti` (`nome`) VALUES
('Carla Bengasi'),
('Cristina Ciocia'),
('Mauro Foti'),
('Silvia Saporetti'),
('Tiziana Basanisi');

-- --------------------------------------------------------

--
-- Table structure for table `insegnamenti`
--

CREATE TABLE `insegnamenti` (
  `id_docente` varchar(30) NOT NULL,
  `Titolo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `insegnamenti`
--

INSERT INTO `insegnamenti` (`id_docente`, `Titolo`) VALUES
('Carla Bengasi', 'Chimica'),
('Silvia Saporetti', 'Chimica'),
('Carla Bengasi', 'Fisica'),
('Silvia Saporetti', 'Fisica'),
('Mauro Foti', 'Inglese'),
('Mauro Foti', 'Italiano'),
('Tiziana Basanisi', 'Italiano'),
('Tiziana Basanisi', 'Latino'),
('Cristina Ciocia', 'Matematica');

-- --------------------------------------------------------

--
-- Table structure for table `ripetizioni`
--

CREATE TABLE `ripetizioni` (
  `Stato` set('disponibile','prenotato','svolto','disdetta') NOT NULL,
  `Giorno` smallint(9) NOT NULL,
  `Ora_i` int(11) NOT NULL,
  `id_corso` varchar(30) DEFAULT NULL,
  `id_docente` varchar(30) NOT NULL,
  `Username` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ripetizioni`
--

INSERT INTO `ripetizioni` (`Stato`, `Giorno`, `Ora_i`, `id_corso`, `id_docente`, `Username`) VALUES
('disponibile', 1, 15, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 1, 15, 'Chimica', 'Silvia Saporetti', NULL),
('disponibile', 1, 16, 'Matematica', 'Cristina Ciocia', NULL),
('prenotato', 1, 16, 'Inglese', 'Mauro Foti', 'riki'),
('disponibile', 1, 16, 'Chimica', 'Silvia Saporetti', NULL),
('disponibile', 1, 17, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 1, 17, 'Inglese', 'Mauro Foti', NULL),
('disponibile', 1, 17, 'Chimica', 'Silvia Saporetti', NULL),
('disponibile', 1, 18, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 1, 18, 'Chimica', 'Silvia Saporetti', NULL),
('disponibile', 2, 15, 'Matematica', 'Cristina Ciocia', NULL),
('disdetta', 2, 15, 'Latino', 'Tiziana Basanisi', 'riki'),
('disponibile', 2, 16, 'Matematica', 'Cristina Ciocia', NULL),
('svolto', 2, 16, 'Italiano', 'Mauro Foti', 'riki'),
('disponibile', 2, 16, 'Fisica', 'Silvia Saporetti', NULL),
('disponibile', 2, 16, 'Latino', 'Tiziana Basanisi', NULL),
('disponibile', 2, 17, 'Fisica', 'Carla Bengasi', NULL),
('disponibile', 2, 17, 'Italiano', 'Mauro Foti', NULL),
('prenotato', 2, 17, 'Fisica', 'Silvia Saporetti', 'riki'),
('disponibile', 2, 18, 'Fisica', 'Carla Bengasi', NULL),
('disponibile', 2, 18, 'Fisica', 'Silvia Saporetti', NULL),
('disponibile', 3, 15, 'Chimica', 'Carla Bengasi', NULL),
('disponibile', 3, 15, 'Matematica', 'Cristina Ciocia', NULL),
('svolto', 3, 16, 'Chimica', 'Carla Bengasi', 'riki'),
('disponibile', 3, 16, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 3, 16, 'Italiano', 'Mauro Foti', NULL),
('disponibile', 3, 17, 'Chimica', 'Carla Bengasi', NULL),
('disponibile', 3, 17, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 3, 17, 'Italiano', 'Mauro Foti', NULL),
('prenotato', 3, 17, 'Italiano', 'Tiziana Basanisi', 'riki'),
('disponibile', 3, 18, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 3, 18, 'Italiano', 'Tiziana Basanisi', NULL),
('disponibile', 4, 15, 'Fisica', 'Silvia Saporetti', NULL),
('disponibile', 4, 16, 'Inglese', 'Carla Bengasi', NULL),
('disponibile', 4, 16, 'Fisica', 'Silvia Saporetti', NULL),
('svolto', 4, 16, 'Latino', 'Tiziana Basanisi', 'riki'),
('disponibile', 4, 17, 'Fisica', 'Carla Bengasi', NULL),
('prenotato', 4, 17, 'Inglese', 'Mauro Foti', 'riki'),
('disponibile', 4, 17, 'Fisica', 'Silvia Saporetti', NULL),
('disponibile', 4, 17, 'Latino', 'Tiziana Basanisi', NULL),
('disponibile', 4, 18, 'Fisica', 'Carla Bengasi', NULL),
('disponibile', 4, 18, 'Inglese', 'Mauro Foti', NULL),
('disponibile', 4, 18, 'Fisica', 'Silvia Saporetti', NULL),
('disponibile', 4, 18, 'Latino', 'Tiziana Basanisi', NULL),
('prenotato', 5, 15, 'Latino', 'Tiziana Basanisi', 'riki'),
('disponibile', 5, 16, 'Italiano', 'Tiziana Basanisi', NULL),
('disponibile', 5, 17, 'Chimica', 'Carla Bengasi', NULL),
('prenotato', 5, 17, 'Matematica', 'Cristina Ciocia', 'riki'),
('disponibile', 5, 17, 'Italiano', 'Tiziana Basanisi', NULL),
('disponibile', 5, 18, 'Chimica', 'Carla Bengasi', NULL),
('disponibile', 5, 18, 'Matematica', 'Cristina Ciocia', NULL),
('disponibile', 5, 18, 'Italiano', 'Tiziana Basanisi', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `utenti`
--

CREATE TABLE `utenti` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(8) NOT NULL,
  `Amministratore` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `utenti`
--

INSERT INTO `utenti` (`Username`, `Password`, `Amministratore`) VALUES
('Admin', 'Admin', 1),
('Ciccio', 'Gamer89', 0),
('dylan', 'dylan', 0),
('riki', 'riki', 0),
('utente_1', 'utente1', 0),
('utente_2', 'utente2', 0),
('utente_3', 'utente3', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `corsi`
--
ALTER TABLE `corsi`
  ADD PRIMARY KEY (`Titolo`);

--
-- Indexes for table `docenti`
--
ALTER TABLE `docenti`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD PRIMARY KEY (`id_docente`,`Titolo`),
  ADD KEY `corso` (`Titolo`);

--
-- Indexes for table `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD PRIMARY KEY (`Giorno`,`Ora_i`,`id_docente`) USING BTREE,
  ADD KEY `username` (`Username`) USING BTREE,
  ADD KEY `corso` (`id_corso`);

--
-- Indexes for table `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`Username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD CONSTRAINT `corso` FOREIGN KEY (`Titolo`) REFERENCES `corsi` (`Titolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `rip_corso` FOREIGN KEY (`id_corso`) REFERENCES `corsi` (`Titolo`),
  ADD CONSTRAINT `username` FOREIGN KEY (`Username`) REFERENCES `utenti` (`Username`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
