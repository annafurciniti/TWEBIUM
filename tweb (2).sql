-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 21, 2020 alle 12:17
-- Versione del server: 10.4.14-MariaDB
-- Versione PHP: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
-- Struttura della tabella `corsi`
--

CREATE TABLE `corsi` (
  `id_corso` int(11) NOT NULL,
  `Titolo` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corsi`
--

INSERT INTO `corsi` (`id_corso`, `Titolo`) VALUES
(1, 'Matematica'),
(2, 'Italiano'),
(3, 'Latino'),
(4, 'Inglese'),
(5, 'Chimica'),
(6, 'Fisica');

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti`
--

CREATE TABLE `docenti` (
  `id_docente` int(11) NOT NULL,
  `Nome` varchar(25) NOT NULL,
  `Cognome` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenti`
--

INSERT INTO `docenti` (`id_docente`, `Nome`, `Cognome`) VALUES
(2, 'Silvia', 'Saporetti'),
(3, 'Carla', 'Bengasi'),
(4, 'Mauro', 'Foti'),
(5, 'Cristina', 'Ciocia'),
(6, 'Tiziana', 'Basanisi');

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnamenti`
--

CREATE TABLE `insegnamenti` (
  `id_corso` int(11) NOT NULL,
  `id_docente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `insegnamenti`
--

INSERT INTO `insegnamenti` (`id_corso`, `id_docente`) VALUES
(1, 4),
(2, 3),
(3, 3),
(4, 5),
(5, 6),
(6, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `ripetizioni`
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
-- Dump dei dati per la tabella `ripetizioni`
--

INSERT INTO `ripetizioni` (`ID_rip`, `Stato`, `Giorno`, `Ora_i`, `Ora_f`, `id_corso`, `id_docente`, `Username`) VALUES
(1, 'prenotato', 'Lunedi', 15, 16, 1, 1, 'Admin'),
(2, 'disdetta', 'Lunedi', 15, 16, 6, 2, 'utente_1'),
(3, 'disponibile', 'Mercoledi', 16, 17, 5, 6, NULL),
(4, 'disponibile', 'Venerdi', 18, 19, 2, 3, NULL),
(5, 'svolto', 'Giovedi', 16, 17, 1, 4, 'utente_1'),
(6, 'prenotato', 'Martedi', 19, 20, 4, 5, 'utente_2'),
(7, 'disponibile', 'Giovedi', 18, 19, 6, 2, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(8) NOT NULL,
  `Amministratore` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`Username`, `Password`, `Amministratore`) VALUES
('Admin', 'Admin', 0),
('utente_1', 'utente1', 0),
('utente_2', 'utente2', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corsi`
--
ALTER TABLE `corsi`
  ADD PRIMARY KEY (`id_corso`);

--
-- Indici per le tabelle `docenti`
--
ALTER TABLE `docenti`
  ADD PRIMARY KEY (`id_docente`) USING BTREE;

--
-- Indici per le tabelle `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD PRIMARY KEY (`id_corso`,`id_docente`),
  ADD KEY `docente` (`id_docente`),
  ADD KEY `corso` (`id_corso`);

--
-- Indici per le tabelle `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD PRIMARY KEY (`ID_rip`),
  ADD KEY `username` (`Username`) USING BTREE,
  ADD KEY `docente` (`id_docente`),
  ADD KEY `corso` (`id_corso`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`Username`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `insegnamenti`
--
ALTER TABLE `insegnamenti`
  ADD CONSTRAINT `corso` FOREIGN KEY (`id_corso`) REFERENCES `corsi` (`id_corso`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `docente` FOREIGN KEY (`id_docente`) REFERENCES `docenti` (`id_docente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `username` FOREIGN KEY (`Username`) REFERENCES `utenti` (`Username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
