CREATE DATABASE IF NOT EXISTS plumdo_stock DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE plumdo_stock;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `stock_detail`;
CREATE TABLE `stock_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `stock_code` varchar(255) NOT NULL COMMENT '股票编码',
  `stock_name` varchar(255) NULL COMMENT '股票名称',
  `begin_price` decimal(20,2) NULL COMMENT '开盘价',
  `end_price` decimal(20,2) NULL COMMENT '收盘价',
  `highest_price` decimal(20,2) NULL COMMENT '最高价',
  `latest_price` decimal(20,2) NULL COMMENT '最低价',
  `stock_num` int(11) NULL COMMENT '交易数',
  `stock_money` decimal(20,2) NULL COMMENT '交易金额',
  `stock_date` date NULL COMMENT '交易时间',
  PRIMARY KEY (`detail_id`),
  KEY `IDX_STOCK_DETAIL_01` (`stock_code`) USING BTREE,
  KEY `IDX_STOCK_DETAIL_02` (`stock_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='股票详情';

DROP TABLE IF EXISTS `stock_gold`;
CREATE TABLE `stock_gold` (
  `gold_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '黄金股ID',
  `stock_code` varchar(255) NOT NULL COMMENT '股票编码',
  `stock_name` varchar(255) NULL COMMENT '股票名称',
  `begin_price` decimal(20,2) NULL COMMENT '开盘价',
  `end_price` decimal(20,2) NULL COMMENT '收盘价',
  `highest_price` decimal(20,2) NULL COMMENT '最高价',
  `latest_price` decimal(20,2) NULL COMMENT '最低价',
  `stock_num` int(11) NULL COMMENT '交易数',
  `stock_money` decimal(20,2) NULL COMMENT '交易金额',
  `stock_date` date NULL COMMENT '交易时间',
  PRIMARY KEY (`gold_id`),
  KEY `IDX_STOCK_GOLD_01` (`stock_code`) USING BTREE,
  KEY `IDX_STOCK_GOLD_02` (`stock_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='黄金股详情';

DROP TABLE IF EXISTS `stock_hot_plate`;
CREATE TABLE `stock_hot_plate` (
  `hot_plate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '热门板块ID',
  `plate_name` varchar(255) NOT NULL COMMENT '热门板块名称',
  `collect_time` date NOT NULL COMMENT '采集时间',
  PRIMARY KEY (`hot_plate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='热门板块';

DROP TABLE IF EXISTS `stock_info`;
CREATE TABLE `stock_info` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '股票ID',
  `stock_code` varchar(255) NOT NULL COMMENT '股票编码',
  `stock_name` varchar(255) NOT NULL COMMENT '股票名称',
  `stock_type` varchar(255) NULL COMMENT '股票板块',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='股票管理';


DROP TABLE IF EXISTS `stock_monster`;
CREATE TABLE `stock_monster` (
  `monster_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '妖股ID',
  `stock_code` varchar(255) NOT NULL COMMENT '股票编码',
  `stock_name` varchar(255) NOT NULL COMMENT '股票名称',
  `collect_time` date NOT NULL COMMENT '采集时间',
  PRIMARY KEY (`monster_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='妖股详情';

DROP TABLE IF EXISTS `lottery_detail`;
CREATE TABLE `lottery_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `lottery_year` int(11) NULL COMMENT '彩票月份',
  `lottery_period` int(11) NULL COMMENT '彩票期数',
  `lottery_n1` int(11) NULL COMMENT '号码1',
  `lottery_n2` int(11) NULL COMMENT '号码2',
  `lottery_n3` int(11) NULL COMMENT '号码3',
  `lottery_n4` int(11) NULL COMMENT '号码4',
  `lottery_n5` int(11) NULL COMMENT '号码5',
  `lottery_n6` int(11) NULL COMMENT '号码6',
  `lottery_code` int(11) NULL COMMENT '特码',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='六合彩详情';

DROP TABLE IF EXISTS `system_parameter`;
CREATE TABLE `system_parameter` (
  `parameter_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `parameter_name` varchar(255) NOT NULL COMMENT '参数名称',
  `parameter_value` varchar(255) NOT NULL COMMENT '参数值',
  PRIMARY KEY (`parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数';
