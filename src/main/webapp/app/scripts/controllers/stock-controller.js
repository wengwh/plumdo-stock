/**
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockController',
		[ '$scope',function($scope) {
			$scope.tabItems=[{
				label : '信息',
				templateUrl : 'views/stock/stock-info.html'
			},{
				label : '详情',
				templateUrl : 'views/stock/stock-detail.html'
			},{
				label : '妖股',
				templateUrl : 'views/stock/stock-detail.html'
			},{
				label : '黄金股',
				templateUrl : 'views/stock/stock-detail.html'
			},{
				label : '弱势股',
				templateUrl : 'views/stock/stock-detail.html'
			}]
			
		} ]);

})();
