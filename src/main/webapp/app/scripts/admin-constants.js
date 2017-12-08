/**
 * 系统常量定义
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp')
//		.constant('contextRoot', ".")
		.constant('contextRoot', "http://192.168.82.142:8080")
		.constant('restUrl', {
			'stockInfos' : '/stock-infos',
			'stockHotPlates' : '/stock-hot-plates',
			'lotteryDetails' : '/lottery-details'
		});

})();