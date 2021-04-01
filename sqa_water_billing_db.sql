-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 01, 2021 at 06:37 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sqa_water_billing_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_account`
--

CREATE TABLE `tbl_account` (
  `id` int(10) NOT NULL,
  `email` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `password` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_address`
--

CREATE TABLE `tbl_address` (
  `id` int(10) NOT NULL,
  `house_number` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `street` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `province` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `city` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `country` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `tbl_area_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_area`
--

CREATE TABLE `tbl_area` (
  `id` int(10) NOT NULL,
  `province` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `city` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `country` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_assigned_area`
--

CREATE TABLE `tbl_assigned_area` (
  `tbl_employee_id` int(10) NOT NULL,
  `tbl_area_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customer`
--

CREATE TABLE `tbl_customer` (
  `id` int(10) NOT NULL,
  `first_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `last_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `phone_number` varchar(16) COLLATE utf8_vietnamese_ci NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(16) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `id_number` varchar(16) COLLATE utf8_vietnamese_ci NOT NULL,
  `created_at` date NOT NULL,
  `is_verified` tinyint(1) NOT NULL,
  `tbl_account_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customer_address`
--

CREATE TABLE `tbl_customer_address` (
  `tbl_customer_id` int(10) NOT NULL,
  `tbl_address_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_employee`
--

CREATE TABLE `tbl_employee` (
  `id` int(10) NOT NULL,
  `first_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `last_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `phone_number` varchar(16) COLLATE utf8_vietnamese_ci NOT NULL,
  `created_at` date NOT NULL,
  `is_working` tinyint(1) NOT NULL,
  `salary` float DEFAULT NULL,
  `tbl_account_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_manager`
--

CREATE TABLE `tbl_manager` (
  `id` int(10) NOT NULL,
  `first_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `last_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `phone_number` varchar(16) COLLATE utf8_vietnamese_ci NOT NULL,
  `created_at` date NOT NULL,
  `is_working` tinyint(1) NOT NULL,
  `tbl_account_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_payment`
--

CREATE TABLE `tbl_payment` (
  `id` int(10) NOT NULL,
  `payment_type` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `payment_code` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `created_at` date NOT NULL,
  `tbl_water_bill_id` int(10) NOT NULL,
  `confirmed` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_water_bill`
--

CREATE TABLE `tbl_water_bill` (
  `id` int(10) NOT NULL,
  `amount` float NOT NULL,
  `created_at` date NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  `due_date` date DEFAULT NULL,
  `tbl_water_meter_reading_id` int(10) NOT NULL,
  `handled_employee_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_water_meter`
--

CREATE TABLE `tbl_water_meter` (
  `id` int(10) NOT NULL,
  `installed_date` date NOT NULL,
  `maximum_reading` int(10) DEFAULT NULL,
  `expired_date` date DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `tbl_address_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_water_meter_reading`
--

CREATE TABLE `tbl_water_meter_reading` (
  `id` int(10) NOT NULL,
  `month` int(10) NOT NULL,
  `year` int(10) NOT NULL,
  `reading_value` int(10) NOT NULL,
  `created_at` date NOT NULL,
  `tbl_water_meter_id` int(10) NOT NULL,
  `handled_employee_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_account`
--
ALTER TABLE `tbl_account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `tbl_address`
--
ALTER TABLE `tbl_address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_addres380877` (`tbl_area_id`);

--
-- Indexes for table `tbl_area`
--
ALTER TABLE `tbl_area`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_assigned_area`
--
ALTER TABLE `tbl_assigned_area`
  ADD PRIMARY KEY (`tbl_employee_id`,`tbl_area_id`),
  ADD KEY `FKtbl_assign664065` (`tbl_employee_id`),
  ADD KEY `FKtbl_assign695092` (`tbl_area_id`);

--
-- Indexes for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_number` (`id_number`),
  ADD KEY `FKtbl_custom195669` (`tbl_account_id`);

--
-- Indexes for table `tbl_customer_address`
--
ALTER TABLE `tbl_customer_address`
  ADD PRIMARY KEY (`tbl_customer_id`,`tbl_address_id`),
  ADD KEY `FKtbl_custom331234` (`tbl_customer_id`),
  ADD KEY `FKtbl_custom140314` (`tbl_address_id`);

--
-- Indexes for table `tbl_employee`
--
ALTER TABLE `tbl_employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_employ480918` (`tbl_account_id`);

--
-- Indexes for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_manage520147` (`tbl_account_id`);

--
-- Indexes for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_paymen613026` (`tbl_water_bill_id`);

--
-- Indexes for table `tbl_water_bill`
--
ALTER TABLE `tbl_water_bill`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_water_729069` (`handled_employee_id`),
  ADD KEY `FKtbl_water_621921` (`tbl_water_meter_reading_id`);

--
-- Indexes for table `tbl_water_meter`
--
ALTER TABLE `tbl_water_meter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_water_272479` (`tbl_address_id`);

--
-- Indexes for table `tbl_water_meter_reading`
--
ALTER TABLE `tbl_water_meter_reading`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_water_634980` (`tbl_water_meter_id`),
  ADD KEY `FKtbl_water_669664` (`handled_employee_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_account`
--
ALTER TABLE `tbl_account`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_address`
--
ALTER TABLE `tbl_address`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_area`
--
ALTER TABLE `tbl_area`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_employee`
--
ALTER TABLE `tbl_employee`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_water_bill`
--
ALTER TABLE `tbl_water_bill`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_water_meter`
--
ALTER TABLE `tbl_water_meter`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_water_meter_reading`
--
ALTER TABLE `tbl_water_meter_reading`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_address`
--
ALTER TABLE `tbl_address`
  ADD CONSTRAINT `FKtbl_addres380877` FOREIGN KEY (`tbl_area_id`) REFERENCES `tbl_area` (`id`);

--
-- Constraints for table `tbl_assigned_area`
--
ALTER TABLE `tbl_assigned_area`
  ADD CONSTRAINT `FKtbl_assign664065` FOREIGN KEY (`tbl_employee_id`) REFERENCES `tbl_employee` (`id`),
  ADD CONSTRAINT `FKtbl_assign695092` FOREIGN KEY (`tbl_area_id`) REFERENCES `tbl_area` (`id`);

--
-- Constraints for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  ADD CONSTRAINT `FKtbl_custom195669` FOREIGN KEY (`tbl_account_id`) REFERENCES `tbl_account` (`id`);

--
-- Constraints for table `tbl_customer_address`
--
ALTER TABLE `tbl_customer_address`
  ADD CONSTRAINT `FKtbl_custom140314` FOREIGN KEY (`tbl_address_id`) REFERENCES `tbl_address` (`id`),
  ADD CONSTRAINT `FKtbl_custom331234` FOREIGN KEY (`tbl_customer_id`) REFERENCES `tbl_customer` (`id`);

--
-- Constraints for table `tbl_employee`
--
ALTER TABLE `tbl_employee`
  ADD CONSTRAINT `FKtbl_employ480918` FOREIGN KEY (`tbl_account_id`) REFERENCES `tbl_account` (`id`);

--
-- Constraints for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  ADD CONSTRAINT `FKtbl_manage520147` FOREIGN KEY (`tbl_account_id`) REFERENCES `tbl_account` (`id`);

--
-- Constraints for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  ADD CONSTRAINT `FKtbl_paymen613026` FOREIGN KEY (`tbl_water_bill_id`) REFERENCES `tbl_water_bill` (`id`);

--
-- Constraints for table `tbl_water_bill`
--
ALTER TABLE `tbl_water_bill`
  ADD CONSTRAINT `FKtbl_water_621921` FOREIGN KEY (`tbl_water_meter_reading_id`) REFERENCES `tbl_water_meter_reading` (`id`),
  ADD CONSTRAINT `FKtbl_water_729069` FOREIGN KEY (`handled_employee_id`) REFERENCES `tbl_employee` (`id`);

--
-- Constraints for table `tbl_water_meter`
--
ALTER TABLE `tbl_water_meter`
  ADD CONSTRAINT `FKtbl_water_272479` FOREIGN KEY (`tbl_address_id`) REFERENCES `tbl_address` (`id`);

--
-- Constraints for table `tbl_water_meter_reading`
--
ALTER TABLE `tbl_water_meter_reading`
  ADD CONSTRAINT `FKtbl_water_634980` FOREIGN KEY (`tbl_water_meter_id`) REFERENCES `tbl_water_meter` (`id`),
  ADD CONSTRAINT `FKtbl_water_669664` FOREIGN KEY (`handled_employee_id`) REFERENCES `tbl_employee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
