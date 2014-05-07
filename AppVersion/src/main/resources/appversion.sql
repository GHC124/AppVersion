-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 06, 2014 at 12:27 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `appversion`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `AUTHORITY` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ROLE_ADMIN',
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_ADMINS_1` (`USER_NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`ID`, `USER_NAME`, `PASSWORD`, `AUTHORITY`, `IS_ACTIVE`) VALUES
(1, 'admin', 'admin123', 'ROLE_ADMIN', 1);

-- --------------------------------------------------------

--
-- Table structure for table `apps`
--

CREATE TABLE IF NOT EXISTS `apps` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ICON_URL` varchar(4000) COLLATE utf8_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATEST_VERSION` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `PLATFORM_ID` int(10) unsigned NOT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_APPS_1` (`NAME`,`PLATFORM_ID`),
  KEY `FK_APPS_1` (`PLATFORM_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=12 ;

--
-- Dumping data for table `apps`
--

INSERT INTO `apps` (`ID`, `NAME`, `ICON_URL`, `DESCRIPTION`, `LATEST_VERSION`, `PLATFORM_ID`, `VERSION`) VALUES
(8, 'Copilot-Tablet', '\\AppIcon\\icon_1398654486783.png', 'abc', '1.0', 2, 0),
(9, 'Copilot-Mobile', '\\AppIcon\\icon_1398668683287.png', '', '1.5', 3, 0),
(10, 'Copilot-IPad', '\\AppIcon\\icon_1399360952127.png', '', '1.7', 5, 0),
(11, 'Copilot-IPhone', '\\AppIcon\\icon_1398752321347.png', '', '1.0', 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `app_group`
--

CREATE TABLE IF NOT EXISTS `app_group` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `APP_ID` int(10) unsigned NOT NULL,
  `GROUP_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_APP_GROUP_1` (`APP_ID`,`GROUP_ID`),
  KEY `FK_APP_GROUP_1` (`APP_ID`),
  KEY `FK_APP_GROUP_2` (`GROUP_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=25 ;

--
-- Dumping data for table `app_group`
--

INSERT INTO `app_group` (`ID`, `APP_ID`, `GROUP_ID`) VALUES
(15, 8, 7),
(8, 8, 9),
(9, 8, 42),
(12, 9, 2),
(22, 9, 7),
(16, 9, 9),
(24, 10, 1),
(17, 10, 2),
(18, 10, 6),
(19, 10, 7),
(23, 10, 12),
(20, 11, 6),
(21, 11, 7);

-- --------------------------------------------------------

--
-- Table structure for table `app_versions`
--

CREATE TABLE IF NOT EXISTS `app_versions` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `RELEASE_DATE` date NOT NULL,
  `RELEASE_NOTE` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `APP_ID` int(10) unsigned NOT NULL,
  `APP_DOWNLOAD_URL` varchar(4000) COLLATE utf8_unicode_ci NOT NULL,
  `APP_SIZE` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_APP_VERSIONS_1` (`VERSION`,`APP_ID`),
  KEY `FK_APP_VERSIONS_1` (`APP_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=16 ;

--
-- Dumping data for table `app_versions`
--

INSERT INTO `app_versions` (`ID`, `VERSION`, `RELEASE_DATE`, `RELEASE_NOTE`, `APP_ID`, `APP_DOWNLOAD_URL`, `APP_SIZE`) VALUES
(2, '1.0', '2014-04-27', '', 8, '\\AppBinary\\android_1398654486861.apk', 665266),
(3, '1.0', '2014-04-27', '', 9, '\\AppBinary\\android_1398664119071.apk', 665266),
(4, '1.0', '2014-04-27', '', 10, '\\AppBinary\\android_1398668784386.apk', 665266),
(10, '1.6', '2014-04-28', '', 10, '\\AppBinary\\android_1398739119458.apk', 665266),
(11, '1.4', '2014-04-28', '', 9, '\\AppBinary\\android_1398739545263.apk', 665266),
(13, '1.0', '2014-04-29', '', 11, '\\AppBinary\\android_1398741289931.apk', 665266),
(14, '1.5', '2014-04-29', '', 9, '\\AppBinary\\android_1398752251991.apk', 665266),
(15, '1.7', '2014-05-06', '', 10, '\\AppBinary\\android_1399360967818.apk', 665266);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_GROUPS_1` (`NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=43 ;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`ID`, `NAME`) VALUES
(1, 'Android-Mobile'),
(2, 'Android-Tablet'),
(6, 'iOS-Mobile'),
(7, 'iOS-Tablet'),
(12, 'test1'),
(9, 'test3'),
(42, 'test4');

-- --------------------------------------------------------

--
-- Table structure for table `platforms`
--

CREATE TABLE IF NOT EXISTS `platforms` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `PLATFORM_TYPE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_PLATFORM_1` (`NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

--
-- Dumping data for table `platforms`
--

INSERT INTO `platforms` (`ID`, `NAME`, `PLATFORM_TYPE`) VALUES
(2, 'Android-Tablet', 'ANDROID'),
(3, 'Android-Mobile', 'ANDROID'),
(4, 'iOS-Mobile', 'IOS'),
(5, 'iOS-Tablet', 'IOS');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `PASSWORD` varchar(4000) COLLATE utf8_unicode_ci NOT NULL,
  `FIRST_NAME` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LAST_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `AUTHORITY` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ROLE_USER',
  `LOGIN_TOKEN` varchar(4000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_USERS_1` (`EMAIL`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=33 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `EMAIL`, `PASSWORD`, `FIRST_NAME`, `LAST_NAME`, `IS_ACTIVE`, `AUTHORITY`, `LOGIN_TOKEN`, `VERSION`) VALUES
(1, 'chung.pv07951@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'Chung', 'Van', 1, 'ROLE_USER', '3d6e5bd2be1abb117e313a9b2b480fe3', 0),
(2, 'abc@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'abc 2', '', 1, 'ROLE_USER', NULL, 0),
(3, 'test@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test', 'test', 1, 'ROLE_USER', NULL, 0),
(4, 'test1@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test1', '', 1, 'ROLE_USER', NULL, 0),
(5, 'test2@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test2', '', 1, 'ROLE_USER', NULL, 0),
(6, 'test3@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test3', '', 1, 'ROLE_USER', NULL, 0),
(7, 'test4@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test4', '', 1, 'ROLE_USER', NULL, 0),
(8, 'test5@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test5', '', 1, 'ROLE_USER', NULL, 0),
(9, 'test6@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test6', '', 1, 'ROLE_USER', NULL, 0),
(10, 'test7@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test7', '', 1, 'ROLE_USER', NULL, 0),
(11, 'test8@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test8', '', 1, 'ROLE_USER', NULL, 0),
(12, 'test9@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test9', '', 1, 'ROLE_USER', NULL, 0),
(13, 'test10@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test10', '', 1, 'ROLE_USER', NULL, 0),
(14, 'test11@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test11', '', 1, 'ROLE_USER', NULL, 0),
(15, 'test12@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test12', '', 1, 'ROLE_USER', NULL, 0),
(16, 'test14@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test14 updated', '', 1, 'ROLE_USER', NULL, 0),
(17, 'test15@gmail.com', '1000:5b42403239616130303438:b21560a87ec121bec53fda8606d079d6892178e5c16665230bf03b9348715163006224798e13b5d6790c5a4b98973de779065c86ad670c5313c3e68567a2a9bb', 'test15', '', 1, 'ROLE_USER', NULL, 0),
(18, 'test16@gmail.com', '1000:5b42403665363564643437:d56041fa14db2754e8136f8f80bb15b500fb4389cc8bc7449865d694bfbf7c8d9c77d3245eb93671df811f8e4ed57e464261ca0033069c8f77c2e491f5844604', 'test16', '', 1, 'ROLE_USER', NULL, 0),
(32, 'abc1@gmail.com', '1000:5b42403137663162346464:c94743caeb2bba985787bb4b3ee14e6821e17393b89d757fa071cc035e31b2a5c41f97fc2a873535837088e6ce28d400e6093de6652491c5befdf680e18c6934', 'abc', '', 1, 'ROLE_USER', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_group`
--

CREATE TABLE IF NOT EXISTS `user_group` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` int(10) unsigned NOT NULL,
  `GROUP_ID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_USER_GROUP_1` (`USER_ID`,`GROUP_ID`),
  KEY `FK_USER_GROUP_1` (`USER_ID`),
  KEY `FK_USER_GROUP_2` (`GROUP_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=119 ;

--
-- Dumping data for table `user_group`
--

INSERT INTO `user_group` (`ID`, `USER_ID`, `GROUP_ID`) VALUES
(26, 1, 1),
(71, 1, 6),
(51, 1, 7),
(98, 1, 42),
(66, 2, 2),
(2, 2, 7),
(91, 3, 1),
(72, 3, 6),
(82, 3, 9),
(95, 3, 12),
(90, 4, 1),
(112, 4, 6),
(55, 4, 7),
(116, 5, 6),
(115, 6, 6),
(114, 7, 6),
(84, 7, 9),
(99, 7, 42),
(113, 8, 6),
(78, 9, 2),
(40, 9, 6),
(83, 10, 9),
(89, 13, 1),
(88, 13, 6),
(118, 13, 7),
(81, 13, 9),
(94, 13, 12),
(108, 14, 2),
(109, 14, 6),
(111, 15, 6),
(86, 15, 9),
(96, 15, 42),
(92, 16, 1),
(53, 16, 7),
(80, 16, 9),
(63, 17, 1),
(73, 17, 6),
(85, 17, 9),
(93, 17, 12),
(117, 18, 6),
(103, 32, 1),
(104, 32, 2),
(105, 32, 6),
(107, 32, 7),
(106, 32, 9);

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_app_group_summary`
--
CREATE TABLE IF NOT EXISTS `view_app_group_summary` (
`APP_ID` int(10) unsigned
,`NAME` varchar(255)
,`GROUP_ID` int(10) unsigned
,`GROUP_NAME` varchar(255)
,`APP_GROUP_ID` int(10) unsigned
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `view_user_group_summary`
--
CREATE TABLE IF NOT EXISTS `view_user_group_summary` (
`USER_ID` int(10) unsigned
,`EMAIL` varchar(255)
,`GROUP_ID` int(10) unsigned
,`GROUP_NAME` varchar(255)
,`USER_GROUP_ID` int(10) unsigned
);
-- --------------------------------------------------------

--
-- Structure for view `view_app_group_summary`
--
DROP TABLE IF EXISTS `view_app_group_summary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_app_group_summary` AS select `u`.`ID` AS `APP_ID`,`u`.`NAME` AS `NAME`,`g`.`ID` AS `GROUP_ID`,`g`.`NAME` AS `GROUP_NAME`,`ug`.`ID` AS `APP_GROUP_ID` from ((`apps` `u` left join `app_group` `ug` on((`u`.`ID` = `ug`.`APP_ID`))) left join `groups` `g` on((`ug`.`GROUP_ID` = `g`.`ID`)));

-- --------------------------------------------------------

--
-- Structure for view `view_user_group_summary`
--
DROP TABLE IF EXISTS `view_user_group_summary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_user_group_summary` AS select `u`.`ID` AS `USER_ID`,`u`.`EMAIL` AS `EMAIL`,`g`.`ID` AS `GROUP_ID`,`g`.`NAME` AS `GROUP_NAME`,`ug`.`ID` AS `USER_GROUP_ID` from ((`users` `u` left join `user_group` `ug` on((`u`.`ID` = `ug`.`USER_ID`))) left join `groups` `g` on((`ug`.`GROUP_ID` = `g`.`ID`)));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `apps`
--
ALTER TABLE `apps`
  ADD CONSTRAINT `FK_APPS_1` FOREIGN KEY (`PLATFORM_ID`) REFERENCES `platforms` (`ID`);

--
-- Constraints for table `app_group`
--
ALTER TABLE `app_group`
  ADD CONSTRAINT `FK_APP_GROUP_1` FOREIGN KEY (`APP_ID`) REFERENCES `apps` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_APP_GROUP_2` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`ID`);

--
-- Constraints for table `app_versions`
--
ALTER TABLE `app_versions`
  ADD CONSTRAINT `FK_APP_VERSIONS_1` FOREIGN KEY (`APP_ID`) REFERENCES `apps` (`ID`) ON DELETE CASCADE;

--
-- Constraints for table `user_group`
--
ALTER TABLE `user_group`
  ADD CONSTRAINT `FK_USER_GROUP_1` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `FK_USER_GROUP_2` FOREIGN KEY (`GROUP_ID`) REFERENCES `groups` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
