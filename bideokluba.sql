-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-01-2025 a las 12:01:39
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
-- Estructura de tabla para la tabla `alokairua`
--

CREATE TABLE `alokairua` (
  `erabiltzaileNAN` varchar(10) NOT NULL,
  `filmID` int(11) NOT NULL,
  `hasData` date NOT NULL,
  `bukData` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `admin` tinyint(1) DEFAULT 0,
  `onartuta` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `erabiltzaile`
--

INSERT INTO `erabiltzaile` (`NAN`, `izena`, `abizena`, `email`, `pasahitza`, `admin`, `onartuta`) VALUES
('12345678Z', 'Erabiltzaile', 'User', 'erabiltzaile@gmail.com', 'erab123', 0, 1),
('79224675A', 'Admin', 'admin', 'admin@gmail.com', 'admin', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `film`
--

CREATE TABLE `film` (
  `filmID` int(11) NOT NULL,
  `izenburua` varchar(50) DEFAULT NULL,
  `aktoreak` varchar(255) DEFAULT NULL,
  `urtea` year(4) DEFAULT NULL,
  `generoa` varchar(50) DEFAULT NULL,
  `zuzendaria` varchar(20) DEFAULT NULL,
  `adminNAN` varchar(10) DEFAULT NULL,
  `katalogoan` tinyint(1) NOT NULL DEFAULT 0,
  `puntuazioaBb` double DEFAULT 0,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `film`
--

INSERT INTO `film` (`filmID`, `izenburua`, `aktoreak`, `urtea`, `generoa`, `zuzendaria`, `adminNAN`, `katalogoan`, `puntuazioaBb`, `path`) VALUES
(1, 'La la land', 'Ryan Gosling, Emma Stone', '2016', 'Musikala', 'Damien Chazelle', '79224675A', 1, 0, 'resources/LaLaLand.mp4'),
(2, 'Interstellar', 'Matthew McConaughey, Anne Hathaway, Jessica Chastain', '2014', 'Adventure, Drama, Sci-Fi', 'Christopher Nolan', '79224675A', 1, 0, 'resources/Interstellar.mp4'),
(3, 'A Minecraft Movie', 'Jason Momoa, Jack Black', '2025', 'Akzioa', 'Jared Hess', '79224675A', 1, 0, 'resources/AMinecraftMovie.mp4');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `filmzerrenda`
--

CREATE TABLE `filmzerrenda` (
  `zerrendaID` int(11) NOT NULL,
  `erabiltzaileNAN` varchar(10) DEFAULT NULL,
  `izena` varchar(30) DEFAULT NULL,
  `pribazitatea` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `filmzerrenda`
--

INSERT INTO `filmzerrenda` (`zerrendaID`, `erabiltzaileNAN`, `izena`, `pribazitatea`) VALUES
(32, '12345678Z', 'test', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hasdata`
--

CREATE TABLE `hasdata` (
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `kudeatu`
--

CREATE TABLE `kudeatu` (
  `erabiltzaileNAN` varchar(10) NOT NULL,
  `adminNAN` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parteizan`
--

CREATE TABLE `parteizan` (
  `ZerrendaID` int(11) NOT NULL,
  `FilmID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `parteizan`
--

INSERT INTO `parteizan` (`ZerrendaID`, `FilmID`) VALUES
(32, 1),
(32, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puntuazioa`
--

CREATE TABLE `puntuazioa` (
  `NAN` varchar(10) NOT NULL,
  `filmID` int(11) NOT NULL,
  `puntuazioa` tinyint(1) DEFAULT NULL,
  `iruzkina` text DEFAULT NULL,
  `data` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alokairua`
--
ALTER TABLE `alokairua`
  ADD PRIMARY KEY (`erabiltzaileNAN`,`filmID`,`hasData`),
  ADD KEY `filmID` (`filmID`),
  ADD KEY `hasData` (`hasData`);

--
-- Indices de la tabla `erabiltzaile`
--
ALTER TABLE `erabiltzaile`
  ADD PRIMARY KEY (`NAN`);

--
-- Indices de la tabla `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`filmID`),
  ADD KEY `adminNAN` (`adminNAN`);

--
-- Indices de la tabla `filmzerrenda`
--
ALTER TABLE `filmzerrenda`
  ADD PRIMARY KEY (`zerrendaID`),
  ADD KEY `erabiltzaileNAN` (`erabiltzaileNAN`);

--
-- Indices de la tabla `hasdata`
--
ALTER TABLE `hasdata`
  ADD PRIMARY KEY (`data`);

--
-- Indices de la tabla `kudeatu`
--
ALTER TABLE `kudeatu`
  ADD PRIMARY KEY (`erabiltzaileNAN`,`adminNAN`),
  ADD KEY `adminNAN` (`adminNAN`);

--
-- Indices de la tabla `parteizan`
--
ALTER TABLE `parteizan`
  ADD PRIMARY KEY (`ZerrendaID`,`FilmID`),
  ADD KEY `FilmID` (`FilmID`);

--
-- Indices de la tabla `puntuazioa`
--
ALTER TABLE `puntuazioa`
  ADD PRIMARY KEY (`NAN`,`filmID`),
  ADD KEY `filmID` (`filmID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `film`
--
ALTER TABLE `film`
  MODIFY `filmID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `filmzerrenda`
--
ALTER TABLE `filmzerrenda`
  MODIFY `zerrendaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alokairua`
--
ALTER TABLE `alokairua`
  ADD CONSTRAINT `alokairua_ibfk_1` FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `alokairua_ibfk_2` FOREIGN KEY (`filmID`) REFERENCES `film` (`filmID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `alokairua_ibfk_3` FOREIGN KEY (`hasData`) REFERENCES `hasdata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `film`
--
ALTER TABLE `film`
  ADD CONSTRAINT `film_ibfk_1` FOREIGN KEY (`adminNAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `filmzerrenda`
--
ALTER TABLE `filmzerrenda`
  ADD CONSTRAINT `filmzerrenda_ibfk_1` FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `kudeatu`
--
ALTER TABLE `kudeatu`
  ADD CONSTRAINT `kudeatu_ibfk_1` FOREIGN KEY (`erabiltzaileNAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `kudeatu_ibfk_2` FOREIGN KEY (`adminNAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `parteizan`
--
ALTER TABLE `parteizan`
  ADD CONSTRAINT `parteizan_ibfk_1` FOREIGN KEY (`ZerrendaID`) REFERENCES `filmzerrenda` (`zerrendaID`),
  ADD CONSTRAINT `parteizan_ibfk_2` FOREIGN KEY (`FilmID`) REFERENCES `film` (`filmID`);

--
-- Filtros para la tabla `puntuazioa`
--
ALTER TABLE `puntuazioa`
  ADD CONSTRAINT `puntuazioa_ibfk_1` FOREIGN KEY (`NAN`) REFERENCES `erabiltzaile` (`NAN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `puntuazioa_ibfk_2` FOREIGN KEY (`filmID`) REFERENCES `film` (`filmID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
