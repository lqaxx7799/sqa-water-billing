-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 13, 2021 at 06:17 AM
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
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_account`
--

CREATE TABLE `tbl_account` (
  `id` int(10) NOT NULL,
  `email` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `password` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `created_at` date NOT NULL,
  `role` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_account`
--

INSERT INTO `tbl_account` (`id`, `email`, `password`, `created_at`, `role`) VALUES
(1, 'qanh@gmail.com', '7ce0359f12857f2a90c7de465f40a95f01cb5da9', '2021-04-06', 'EMPLOYEE'),
(2, 'anna@gmail.com', '7ce0359f12857f2a90c7de465f40a95f01cb5da9', '2021-04-06', 'CUSTOMER'),
(3, 'lqaxx7799@gmail.com', '7ce0359f12857f2a90c7de465f40a95f01cb5da9', '2021-04-07', 'CUSTOMER');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_address`
--

CREATE TABLE `tbl_address` (
  `id` int(10) NOT NULL,
  `house_number` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `street` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `tbl_customer_id` int(10) NOT NULL,
  `tbl_address_type_id` int(10) NOT NULL,
  `tbl_ward_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_address`
--

INSERT INTO `tbl_address` (`id`, `house_number`, `street`, `tbl_customer_id`, `tbl_address_type_id`, `tbl_ward_id`) VALUES
(1, '100', 'Đặng Văn Ngữ', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_address_type`
--

CREATE TABLE `tbl_address_type` (
  `id` int(10) NOT NULL,
  `type` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `description` varchar(256) COLLATE utf8_vietnamese_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_address_type`
--

INSERT INTO `tbl_address_type` (`id`, `type`, `description`) VALUES
(1, 'Hộ gia đình', 'Hộ gia đình'),
(2, 'Hộ nghèo', 'Hộ nghèo'),
(3, 'Cơ quan hành chính', 'Cơ quan hành chính'),
(4, 'Đơn vị sự nghiệp, dịch vụ công cộng', 'Đơn vị sự nghiệp, dịch vụ công cộng'),
(5, 'Đơn vị sản xuất', 'Đơn vị sản xuất'),
(6, 'Kinh doanh dịch vụ', 'Kinh doanh dịch vụ');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_area`
--

CREATE TABLE `tbl_area` (
  `id` int(10) NOT NULL,
  `ward` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `district` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `city` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `country` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_assigned_area`
--

CREATE TABLE `tbl_assigned_area` (
  `tbl_employee_id` int(10) NOT NULL,
  `tbl_ward_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_assigned_area`
--

INSERT INTO `tbl_assigned_area` (`tbl_employee_id`, `tbl_ward_id`) VALUES
(1, 1),
(1, 2);

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

--
-- Dumping data for table `tbl_customer`
--

INSERT INTO `tbl_customer` (`id`, `first_name`, `last_name`, `phone_number`, `date_of_birth`, `gender`, `id_number`, `created_at`, `is_verified`, `tbl_account_id`) VALUES
(1, 'Anna', 'Bell', '0123456789', '1999-10-23', 'female', '01232323232', '2021-04-06', 1, 2),
(4, 'Quoc', 'Lam', '+84976176490', NULL, 'male', '0535234543', '2021-04-07', 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_district`
--

CREATE TABLE `tbl_district` (
  `id` int(10) NOT NULL,
  `district_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `tbl_province_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_district`
--

INSERT INTO `tbl_district` (`id`, `district_name`, `tbl_province_id`) VALUES
(1, 'Quận Đống Đa', 1),
(2, 'Quận Thanh Xuân', 1);

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

--
-- Dumping data for table `tbl_employee`
--

INSERT INTO `tbl_employee` (`id`, `first_name`, `last_name`, `phone_number`, `created_at`, `is_working`, `salary`, `tbl_account_id`) VALUES
(1, 'Quốc Anh', 'Lâm', '0976176490', '2021-04-06', 1, 1000, 1);

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
  `confirmed` tinyint(1) NOT NULL,
  `otp_code` varchar(16) COLLATE utf8_vietnamese_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_payment`
--

INSERT INTO `tbl_payment` (`id`, `payment_type`, `payment_code`, `created_at`, `tbl_water_bill_id`, `confirmed`, `otp_code`) VALUES
(1, 'ACB', '1151151515151', '2021-04-13', 7, 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_pricing`
--

CREATE TABLE `tbl_pricing` (
  `id` int(10) NOT NULL,
  `tbl_address_type_id` int(10) NOT NULL,
  `applied_from` date NOT NULL,
  `applied_to` date DEFAULT NULL,
  `is_applying` tinyint(1) NOT NULL,
  `unit_price_level_1` float NOT NULL,
  `unit_price_level_2` float DEFAULT NULL,
  `unit_price_level_3` float DEFAULT NULL,
  `unit_price_level_4` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_pricing`
--

INSERT INTO `tbl_pricing` (`id`, `tbl_address_type_id`, `applied_from`, `applied_to`, `is_applying`, `unit_price_level_1`, `unit_price_level_2`, `unit_price_level_3`, `unit_price_level_4`) VALUES
(1, 1, '2021-01-01', '2021-12-31', 1, 5973, 7052, 8669, 15929),
(2, 2, '2021-01-01', '2021-12-31', 1, 3600, 4500, 5600, 6700),
(3, 3, '2021-01-01', '2021-12-31', 1, 9955, 9955, 9955, 9955),
(4, 4, '2021-01-01', '2021-12-31', 1, 9955, 9955, 9955, 9955),
(5, 5, '2021-01-01', '2021-12-31', 1, 11615, 11615, 11615, 11615),
(6, 6, '2021-01-01', '2021-12-31', 1, 22068, 22068, 22068, 22068);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_province`
--

CREATE TABLE `tbl_province` (
  `id` int(10) NOT NULL,
  `province_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_province`
--

INSERT INTO `tbl_province` (`id`, `province_name`) VALUES
(1, 'Thành phố Hà Nội'),
(2, 'Tỉnh Nam Định');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ward`
--

CREATE TABLE `tbl_ward` (
  `id` int(10) NOT NULL,
  `ward_name` varchar(256) COLLATE utf8_vietnamese_ci NOT NULL,
  `tbl_district_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_ward`
--

INSERT INTO `tbl_ward` (`id`, `ward_name`, `tbl_district_id`) VALUES
(1, 'Phường Nam Đồng', 1),
(2, 'Phường Thành Công', 1);

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

--
-- Dumping data for table `tbl_water_bill`
--

INSERT INTO `tbl_water_bill` (`id`, `amount`, `created_at`, `is_paid`, `due_date`, `tbl_water_meter_reading_id`, `handled_employee_id`) VALUES
(7, 1331970, '2021-04-09', 1, '2021-04-16', 6, 1);

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

--
-- Dumping data for table `tbl_water_meter`
--

INSERT INTO `tbl_water_meter` (`id`, `installed_date`, `maximum_reading`, `expired_date`, `is_active`, `tbl_address_id`) VALUES
(1, '2021-01-01', 10000, '2021-07-01', 1, 1);

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
  `handled_employee_id` int(10) NOT NULL,
  `calculated_value` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Dumping data for table `tbl_water_meter_reading`
--

INSERT INTO `tbl_water_meter_reading` (`id`, `month`, `year`, `reading_value`, `created_at`, `tbl_water_meter_id`, `handled_employee_id`, `calculated_value`) VALUES
(6, 4, 2021, 100, '2021-04-09', 1, 1, 100);

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
  ADD KEY `FKtbl_addres888450` (`tbl_customer_id`),
  ADD KEY `FKtbl_addres438927` (`tbl_address_type_id`),
  ADD KEY `FKtbl_addres871841` (`tbl_ward_id`);

--
-- Indexes for table `tbl_address_type`
--
ALTER TABLE `tbl_address_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `type` (`type`);

--
-- Indexes for table `tbl_area`
--
ALTER TABLE `tbl_area`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_assigned_area`
--
ALTER TABLE `tbl_assigned_area`
  ADD PRIMARY KEY (`tbl_employee_id`,`tbl_ward_id`),
  ADD KEY `FKtbl_assign664065` (`tbl_employee_id`),
  ADD KEY `FKtbl_assign23779` (`tbl_ward_id`);

--
-- Indexes for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_number` (`id_number`),
  ADD KEY `FKtbl_custom195669` (`tbl_account_id`);

--
-- Indexes for table `tbl_district`
--
ALTER TABLE `tbl_district`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_distri718273` (`tbl_province_id`);

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
-- Indexes for table `tbl_pricing`
--
ALTER TABLE `tbl_pricing`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_pricin802236` (`tbl_address_type_id`);

--
-- Indexes for table `tbl_province`
--
ALTER TABLE `tbl_province`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_ward`
--
ALTER TABLE `tbl_ward`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtbl_ward308438` (`tbl_district_id`);

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
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_address`
--
ALTER TABLE `tbl_address`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_address_type`
--
ALTER TABLE `tbl_address_type`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_area`
--
ALTER TABLE `tbl_area`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_district`
--
ALTER TABLE `tbl_district`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_employee`
--
ALTER TABLE `tbl_employee`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_manager`
--
ALTER TABLE `tbl_manager`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_payment`
--
ALTER TABLE `tbl_payment`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_pricing`
--
ALTER TABLE `tbl_pricing`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_province`
--
ALTER TABLE `tbl_province`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_ward`
--
ALTER TABLE `tbl_ward`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_water_bill`
--
ALTER TABLE `tbl_water_bill`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tbl_water_meter`
--
ALTER TABLE `tbl_water_meter`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_water_meter_reading`
--
ALTER TABLE `tbl_water_meter_reading`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_address`
--
ALTER TABLE `tbl_address`
  ADD CONSTRAINT `FKtbl_addres438927` FOREIGN KEY (`tbl_address_type_id`) REFERENCES `tbl_address_type` (`id`),
  ADD CONSTRAINT `FKtbl_addres871841` FOREIGN KEY (`tbl_ward_id`) REFERENCES `tbl_ward` (`id`),
  ADD CONSTRAINT `FKtbl_addres888450` FOREIGN KEY (`tbl_customer_id`) REFERENCES `tbl_customer` (`id`);

--
-- Constraints for table `tbl_assigned_area`
--
ALTER TABLE `tbl_assigned_area`
  ADD CONSTRAINT `FKtbl_assign23779` FOREIGN KEY (`tbl_ward_id`) REFERENCES `tbl_ward` (`id`),
  ADD CONSTRAINT `FKtbl_assign664065` FOREIGN KEY (`tbl_employee_id`) REFERENCES `tbl_employee` (`id`);

--
-- Constraints for table `tbl_customer`
--
ALTER TABLE `tbl_customer`
  ADD CONSTRAINT `FKtbl_custom195669` FOREIGN KEY (`tbl_account_id`) REFERENCES `tbl_account` (`id`);

--
-- Constraints for table `tbl_district`
--
ALTER TABLE `tbl_district`
  ADD CONSTRAINT `FKtbl_distri718273` FOREIGN KEY (`tbl_province_id`) REFERENCES `tbl_province` (`id`);

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
-- Constraints for table `tbl_pricing`
--
ALTER TABLE `tbl_pricing`
  ADD CONSTRAINT `FKtbl_pricin802236` FOREIGN KEY (`tbl_address_type_id`) REFERENCES `tbl_address_type` (`id`);

--
-- Constraints for table `tbl_ward`
--
ALTER TABLE `tbl_ward`
  ADD CONSTRAINT `FKtbl_ward308438` FOREIGN KEY (`tbl_district_id`) REFERENCES `tbl_district` (`id`);

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
