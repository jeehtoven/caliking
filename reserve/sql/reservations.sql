-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 17, 2015 at 11:05 PM
-- Server version: 5.5.42-37.1-log
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `jeehtove_caliking`
--

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `room` char(11) NOT NULL,
  `checkin` char(20) NOT NULL,
  `checkout` char(20) NOT NULL,
  `type` char(20) NOT NULL,
  `name` char(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--