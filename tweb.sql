-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 01, 2020 at 04:25 PM
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
  `id_corso` int(11) NOT NULL,
  `Titolo` varchar(15) NOT NULL,
  `descrizione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `corsi`
--

INSERT INTO `corsi` (`id_corso`, `Titolo`, `descrizione`) VALUES
(1, 'Matematica', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'),
(2, 'Italiano', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. '),
(3, 'Latino', 'Commodo sed egestas egestas fringilla phasellus faucibus scelerisque. Aliquet risus feugiat in ante metus. Et molestie ac feugiat sed lectus vestibulum. Vulputate ut pharetra sit amet aliquam id diam maecenas ultricies.'),
(4, 'Inglese', 'Quisque non tellus orci ac auctor augue. Nulla facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. Dictum sit amet justo donec enim. '),
(5, 'Chimica', 'Pretium nibh ipsum consequat nisl vel pretium lectus quam id. Volutpat maecenas volutpat blandit aliquam etiam.\r\n\r\n'),
(6, 'Fisica', 'Tincidunt praesent semper feugiat nibh. Tristique senectus et netus et malesuada fames ac turpis egestas. ');

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
  `id_corso` int(11) NOT NULL,
  `id_docente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `insegnamenti`
--

INSERT INTO `insegnamenti` (`id_corso`, `id_docente`) VALUES
(6, 2),
(2, 3),
(3, 3),
(1, 4),
(2, 4),
(3, 4),
(2, 5),
(4, 5),
(6, 5),
(5, 6);

-- --------------------------------------------------------

--
-- Table structure for table `ripetizioni`
--

CREATE TABLE `ripetizioni` (
  `ID_rip` int(11) NOT NULL,
  `Stato` set('disponibile','prenotato','svolto','disdetta') NOT NULL,
  `Giorno` varchar(9) NOT NULL,
  `Ora_i` int(11) NOT NULL,
  `Ora_f` int(11) NOT NULL,
  `id_corso` int(11) DEFAULT NULL,
  `id_docente` int(11) DEFAULT NULL,
  `Username` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ripetizioni`
--

INSERT INTO `ripetizioni` (`ID_rip`, `Stato`, `Giorno`, `Ora_i`, `Ora_f`, `id_corso`, `id_docente`, `Username`) VALUES
(2, 'disdetta', 'Lunedi', 15, 16, 6, 2, 'utente_1'),
(3, 'disponibile', 'Mercoledi', 16, 17, 5, 6, NULL),
(4, 'disponibile', 'Venerdi', 18, 19, 2, 3, NULL),
(5, 'svolto', 'Giovedi', 16, 17, 1, 4, 'utente_1'),
(6, 'prenotato', 'Martedi', 19, 20, 4, 5, 'utente_2'),
(7, 'disponibile', 'Giovedi', 18, 19, 6, 2, NULL);

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
  ADD PRIMARY KEY (`id_corso`);

--
-- Indexes for table `docenti`
--
ALTER TABLE `docenti`
  ADD PRIMARY KEY (`id_docente`) USING BTREE;

--
-- Indexes for table `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD PRIMARY KEY (`id_corso`,`id_docente`),
  ADD KEY `docente` (`id_docente`),
  ADD KEY `corso` (`id_corso`);

--
-- Indexes for table `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD PRIMARY KEY (`ID_rip`),
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
-- AUTO_INCREMENT for table `corsi`
--
ALTER TABLE `corsi`
  MODIFY `id_corso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `docenti`
--
ALTER TABLE `docenti`
  MODIFY `id_docente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD CONSTRAINT `corso` FOREIGN KEY (`id_corso`) REFERENCES `corsi` (`id_corso`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `docente` FOREIGN KEY (`id_docente`) REFERENCES `docenti` (`id_docente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `username` FOREIGN KEY (`Username`) REFERENCES `utenti` (`Username`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
