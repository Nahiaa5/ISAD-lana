-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-12-2024 a las 21:51:37
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bideokluba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `erabiltzaile`
--

CREATE TABLE `erabiltzaile` (
  `NAN` varchar(10) NOT NULL,
  `izena` varchar(20) DEFAULT NULL,
  `abizena` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `pasahitza` varchar(30) DEFAULT NULL,
  `admin` TINYINT(1) DEFAULT 0,
  `onartuta` TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`NAN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `erabiltzaile`
--

INSERT INTO `erabiltzaile` (`NAN`, `izena`, `abizena`, `email`, `pasahitza`,  `admin`, `onartuta`) VALUES
('12345678Z', 'Erabiltzaile', 'User', 'erabiltzaile@gmail.com', 'erab123', 0, 1),
('79224675A', 'Admin','admin' , 'admin@gmail.com', 'admin', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `film`
--

CREATE TABLE `film` (
  `filmID` int(11) NOT NULL AUTO_INCREMENT,
  `izenburua` varchar(50) DEFAULT NULL,
  `aktoreak` varchar(255) DEFAULT NULL,
  `urtea` year(4) DEFAULT NULL,
  `generoa` varchar(50) DEFAULT NULL,
  `zuzendaria` varchar(20) DEFAULT NULL,
  `adminNAN` varchar(10) DEFAULT NULL,
  `katalogoan` tinyint(1) NOT NULL DEFAULT 0,
  `puntuazioaBb` double DEFAULT 0,
  `path` varchar(255),
  PRIMARY KEY (`filmID`),
  FOREIGN KEY (`adminNAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `film`
--

INSERT INTO `film` (`izenburua`, `aktoreak`, `urtea`, `generoa`, `zuzendaria`, `adminNAN`, `katalogoan`, `puntuazioaBb`, `path`) VALUES 
('La la land', 'Ryan Gosling, Emma Stone', '2016', 'Musical', 'Damien Chazelle', '79224675A', 1, 0, '/LaLaLand.mp4');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `filmzerrenda`
--

CREATE TABLE `filmzerrenda` (
  `zerrendaID` int(11) NOT NULL AUTO_INCREMENT,
  `erabiltzaileNAN` varchar(10) DEFAULT NULL,
  `izena` varchar(30) DEFAULT NULL,
  `pribazitatea` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`zerrendaID`),
  FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `kudeatu`
--

CREATE TABLE `kudeatu` (
  `erabiltzaileNAN` varchar(10) NOT NULL,
  `adminNAN` varchar(10) NOT NULL,
  PRIMARY KEY (`erabiltzaileNAN`, `adminNAN`),
  FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`adminNAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parteizan`
--

CREATE TABLE `parteizan` (
  `zerrendaID` int(11) NOT NULL,
  `filmID` int(11) NOT NULL,
  PRIMARY KEY (`zerrendaID`, `filmID`),
  FOREIGN KEY (`zerrendaID`) REFERENCES `filmzerrenda`(`zerrendaID`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`filmID`) REFERENCES `film`(`filmID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puntuazioa`
--

CREATE TABLE `puntuazioa` (
  `NAN` varchar(10) NOT NULL,
  `filmID` int(11) NOT NULL,
  `puntuazioa` tinyint(1) DEFAULT NULL,
  `iruzkina` text DEFAULT NULL,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`NAN`, `filmID`),
  FOREIGN KEY (`NAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`filmID`) REFERENCES `film`(`filmID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hasData`
--

CREATE TABLE `hasData` (
  `data` date NOT NULL,
  PRIMARY KEY (`data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alokairua`
--

CREATE TABLE `alokairua` (
  `erabiltzaileNAN` varchar(10) NOT NULL,
  `filmID` int(11) NOT NULL,
  `hasData` date NOT NULL,
  `bukData` date NOT NULL,
  PRIMARY KEY (`erabiltzaileNAN`, `filmID`, `hasData`),
  FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile`(`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`filmID`) REFERENCES `film`(`filmID`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`hasData`) REFERENCES `hasData`(`data`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
