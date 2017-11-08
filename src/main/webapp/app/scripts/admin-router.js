/**
 * 系统路由配置
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').config(
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('home', {
				url : '',
				templateUrl : 'views/main.html',
				controller : 'MainController',
				abstract : true
			}).state('home.stock-info', {
				url : '/stock-info',
				controller : 'StockInfoController',
				templateUrl : 'views/stock-info.html'
			});

			$urlRouterProvider.otherwise('/stock-info');
	});

})();