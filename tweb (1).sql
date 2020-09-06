-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 06, 2020 at 05:17 PM
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
  `id_docente` int(11) NOT NULL,
  `Nome` varchar(25) NOT NULL,
  `Cognome` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `docenti`
--

INSERT INTO `docenti` (`id_docente`, `Nome`, `Cognome`) VALUES
(2, 'Silvia', 'Saporetti'),
(3, 'Carla', 'Bengasi'),
(4, 'Mauro', 'Foti'),
(5, 'Cristina', 'Ciocia'),
(6, 'Tiziana', 'Basanisi');

-- --------------------------------------------------------

--
-- Table structure for table `insegnamenti`
--

CREATE TABLE `insegnamenti` (
  `id_docente` int(11) NOT NULL,
  `Titolo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `insegnamenti`
--

INSERT INTO `insegnamenti` (`id_docente`, `Titolo`) VALUES
(2, 'Chimica'),
(3, 'Chimica'),
(2, 'Fisica'),
(3, 'Fisica'),
(4, 'Inglese'),
(4, 'Italiano'),
(6, 'Italiano'),
(6, 'Latino'),
(5, 'Matematica');

-- --------------------------------------------------------

--
-- Table structure for table `ripetizioni`
--

CREATE TABLE `ripetizioni` (
  `Stato` set('disponibile','prenotato','svolto','disdetta') NOT NULL,
  `Giorno` smallint(9) NOT NULL,
  `Ora_i` int(11) NOT NULL,
  `id_corso` varchar(30) DEFAULT NULL,
  `id_docente` int(11) NOT NULL,
  `Username` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ripetizioni`
--

INSERT INTO `ripetizioni` (`Stato`, `Giorno`, `Ora_i`, `id_corso`, `id_docente`, `Username`) VALUES
('disponibile', 1, 15, 'Chimica', 2, NULL),
('disponibile', 1, 15, 'Matematica', 5, NULL),
('disponibile', 1, 16, 'Chimica', 2, NULL),
('disponibile', 1, 16, 'Inglese', 4, NULL),
('disponibile', 1, 16, 'Matematica', 5, NULL),
('disponibile', 1, 17, 'Chimica', 2, NULL),
('disponibile', 1, 17, 'Inglese', 4, NULL),
('disponibile', 1, 17, 'Matematica', 5, NULL),
('disponibile', 1, 18, 'Chimica', 2, NULL),
('disponibile', 1, 18, 'Matematica', 5, NULL),
('disponibile', 2, 15, 'Matematica', 5, NULL),
('disponibile', 2, 15, 'Latino', 6, NULL),
('disponibile', 2, 16, 'Fisica', 2, NULL),
('disponibile', 2, 16, 'Italiano', 4, NULL),
('disponibile', 2, 16, 'Matematica', 5, NULL),
('disponibile', 2, 16, 'Latino', 6, NULL),
('disponibile', 2, 17, 'Fisica', 2, NULL),
('disponibile', 2, 17, 'Fisica', 3, NULL),
('disponibile', 2, 17, 'Italiano', 4, NULL),
('disponibile', 2, 18, 'Fisica', 2, NULL),
('disponibile', 2, 18, 'Fisica', 3, NULL),
('disponibile', 3, 15, 'Chimica', 3, NULL),
('disponibile', 3, 15, 'Matematica', 5, NULL),
('disponibile', 3, 16, 'Chimica', 3, NULL),
('disponibile', 3, 16, 'Italiano', 4, NULL),
('disponibile', 3, 16, 'Matematica', 5, NULL),
('disponibile', 3, 17, 'Chimica', 3, NULL),
('disponibile', 3, 17, 'Italiano', 4, NULL),
('disponibile', 3, 17, 'Matematica', 5, NULL),
('disponibile', 3, 17, 'Italiano', 6, NULL),
('disponibile', 3, 18, 'Matematica', 5, NULL),
('disponibile', 3, 18, 'Italiano', 6, NULL),
('disponibile', 4, 15, 'Fisica', 2, NULL),
('disponibile', 4, 16, 'Fisica', 2, NULL),
('disponibile', 4, 16, 'Inglese', 4, NULL),
('disponibile', 4, 16, 'Latino', 6, NULL),
('disponibile', 4, 17, 'Fisica', 2, NULL),
('disponibile', 4, 17, 'Fisica', 3, NULL),
('disponibile', 4, 17, 'Inglese', 4, NULL),
('disponibile', 4, 17, 'Latino', 6, NULL),
('disponibile', 4, 18, 'Fisica', 2, NULL),
('disponibile', 4, 18, 'Fisica', 3, NULL),
('disponibile', 4, 18, 'Inglese', 4, NULL),
('disponibile', 4, 18, 'Latino', 6, NULL),
('disponibile', 5, 15, 'Latino', 6, NULL),
('disponibile', 5, 16, 'Italiano', 6, NULL),
('disponibile', 5, 17, 'Chimica', 3, NULL),
('disponibile', 5, 17, 'Matematica', 5, NULL),
('disponibile', 5, 17, 'Italiano', 6, NULL),
('disponibile', 5, 18, 'Chimica', 3, NULL),
('disponibile', 5, 18, 'Matematica', 5, NULL),
('disponibile', 5, 18, 'Italiano', 6, NULL);

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
  ADD PRIMARY KEY (`id_docente`);

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
  ADD KEY `docente` (`id_docente`),
  ADD KEY `corso` (`id_corso`);

--
-- Indexes for table `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `docenti`
--
ALTER TABLE `docenti`
  MODIFY `id_docente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD CONSTRAINT `corso` FOREIGN KEY (`Titolo`) REFERENCES `corsi` (`Titolo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `docente` FOREIGN KEY (`id_docente`) REFERENCES `docenti` (`id_docente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `rip_corso` FOREIGN KEY (`id_corso`) REFERENCES `corsi` (`Titolo`),
  ADD CONSTRAINT `rip_docente` FOREIGN KEY (`id_docente`) REFERENCES `docenti` (`id_docente`),
  ADD CONSTRAINT `username` FOREIGN KEY (`Username`) REFERENCES `utenti` (`Username`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
