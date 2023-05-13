-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3305
-- Tiempo de generación: 13-05-2023 a las 16:15:34
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pecuario`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alimentos`
--

CREATE TABLE `alimentos` (
  `id_alimentos` int(11) NOT NULL,
  `kgHembras` double DEFAULT NULL,
  `grsHembras` double DEFAULT NULL,
  `kgMachos` double DEFAULT NULL,
  `grsMachos` double DEFAULT NULL,
  `id_registro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alimentos`
--

INSERT INTO `alimentos` (`id_alimentos`, `kgHembras`, `grsHembras`, `kgMachos`, `grsMachos`, `id_registro`) VALUES
(1, 210, 0.09226713, 10, 0.037453182, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `existencia`
--

CREATE TABLE `existencia` (
  `id_existencia` int(11) NOT NULL,
  `CanMachos` int(11) NOT NULL DEFAULT 0,
  `id_registro` int(11) NOT NULL,
  `CantHembras` int(11) NOT NULL DEFAULT 0,
  `edad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `existencia`
--

INSERT INTO `existencia` (`id_existencia`, `CanMachos`, `id_registro`, `CantHembras`, `edad`) VALUES
(1, 267, 1, 2276, 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lote`
--

CREATE TABLE `lote` (
  `id_lote` int(11) NOT NULL,
  `lote` varchar(50) NOT NULL,
  `FechaDeNacimiento` varchar(50) DEFAULT NULL,
  `HembrasInicio` int(11) DEFAULT NULL,
  `MachosInicio` int(11) DEFAULT NULL,
  `HembrasAlojadas` int(11) DEFAULT NULL,
  `MachosAlojados` int(11) DEFAULT NULL,
  `Estado` varchar(50) NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `lote`
--

INSERT INTO `lote` (`id_lote`, `lote`, `FechaDeNacimiento`, `HembrasInicio`, `MachosInicio`, `HembrasAlojadas`, `MachosAlojados`, `Estado`) VALUES
(1, '148', '23/05/2022', 2744, 2411, 2276, 267, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mortalidad`
--

CREATE TABLE `mortalidad` (
  `id_mortalidad` int(11) NOT NULL,
  `diaHembra` int(11) DEFAULT NULL,
  `promedioHembra` double DEFAULT NULL,
  `selHembra` int(11) DEFAULT NULL,
  `ventasHembras` int(11) DEFAULT NULL,
  `diaMachos` int(11) DEFAULT NULL,
  `promedioMachos` double DEFAULT NULL,
  `selMachos` int(11) DEFAULT NULL,
  `ventasMachos` int(11) DEFAULT NULL,
  `id_registro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mortalidad`
--

INSERT INTO `mortalidad` (`id_mortalidad`, `diaHembra`, `promedioHembra`, `selHembra`, `ventasHembras`, `diaMachos`, `promedioMachos`, `selMachos`, `ventasMachos`, `id_registro`) VALUES
(1, 1, 0.043936733, 1, 0, 1, 0.37453184, 0, 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_produccion` int(11) NOT NULL,
  `totalHuevos` int(11) DEFAULT NULL,
  `promedioTotal` double DEFAULT NULL,
  `incubable` int(11) DEFAULT NULL,
  `promedioInc` double DEFAULT NULL,
  `comercio` int(11) DEFAULT NULL,
  `roto` int(11) DEFAULT NULL,
  `id_registro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_produccion`, `totalHuevos`, `promedioTotal`, `incubable`, `promedioInc`, `comercio`, `roto`, `id_registro`) VALUES
(1, 33, 0.014499121, 3, 9.090909, 5, 25, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registros`
--

CREATE TABLE `registros` (
  `id_registro` int(11) NOT NULL,
  `fecha` varchar(50) NOT NULL,
  `id_lote` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `registros`
--

INSERT INTO `registros` (`id_registro`, `fecha`, `id_lote`) VALUES
(1, '10/05/2023', '148');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  ADD PRIMARY KEY (`id_alimentos`),
  ADD KEY `id_registro` (`id_registro`);

--
-- Indices de la tabla `existencia`
--
ALTER TABLE `existencia`
  ADD PRIMARY KEY (`id_existencia`),
  ADD KEY `id_registro` (`id_registro`);

--
-- Indices de la tabla `lote`
--
ALTER TABLE `lote`
  ADD PRIMARY KEY (`id_lote`);

--
-- Indices de la tabla `mortalidad`
--
ALTER TABLE `mortalidad`
  ADD PRIMARY KEY (`id_mortalidad`),
  ADD KEY `id_registro` (`id_registro`);

--
-- Indices de la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD PRIMARY KEY (`id_produccion`),
  ADD KEY `id_registro` (`id_registro`);

--
-- Indices de la tabla `registros`
--
ALTER TABLE `registros`
  ADD PRIMARY KEY (`id_registro`),
  ADD KEY `id_lote` (`id_lote`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  MODIFY `id_alimentos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `existencia`
--
ALTER TABLE `existencia`
  MODIFY `id_existencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `lote`
--
ALTER TABLE `lote`
  MODIFY `id_lote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `mortalidad`
--
ALTER TABLE `mortalidad`
  MODIFY `id_mortalidad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_produccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `registros`
--
ALTER TABLE `registros`
  MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
