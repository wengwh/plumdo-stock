/**
 * 股票信息采编
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockDetailController',
		[ '$scope','$mdDialog',function($scope,$mdDialog) {

			$scope.stockInfos = $scope.RestService($scope.restUrl.stockInfos);
			
			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.tableCols = [ {
				id : 'stockCode',
				name : '股票编码',
				orderBy : 'stockCode'
			}, {
				id : 'stockName',
				name : '股票名称',
				orderBy : 'stockName'
			}, {
				id : 'stockType',
				name : '板块类别',
				orderBy : 'stockType'
			} ];

			$scope.query = {
				order : 'stockCode',
				stockCode : '',
				stockName : '',
				limit : 10,
				page : 1
			};

			$scope.queryItems = function() {
				$scope.selected = [];
				$scope.promise = $scope.stockInfos.get({
					params:$scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);
	

})();
