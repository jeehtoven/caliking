-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 14, 2015 at 08:30 AM
-- Server version: 5.5.42-37.1-log
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `jeehtove_caliking`
--

-- --------------------------------------------------------

--
-- Table structure for table `makereservation_rooms`
--

DROP TABLE IF EXISTS `makereservation_rooms`;
CREATE TABLE IF NOT EXISTS `makereservation_rooms` (
  `ROOM` int(11) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `BOOKED` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;