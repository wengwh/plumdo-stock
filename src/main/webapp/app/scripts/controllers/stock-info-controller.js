/**
 * 股票信息采编
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockInfoController',
		[ '$scope','$mdDialog',function($scope,$mdDialog) {

			$scope.stockInfos = $scope.RestService($scope.restUrl.stockInfos);
			$scope.stockMonsters = $scope.RestService($scope.restUrl.stockMonsters);
			
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

			$scope.deleteItem = function(stockId) {
				$scope.confirmDialog({
					title : '确认删除股票',
					content : '删除选定的股票信息？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.stockInfos.delete({
								urlPath : '/'+stockId,
							}, function(response) {
								$scope.showMsg('删除股票成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.createItem = function() {
				$scope.editDialog({
					templateUrl: 'stock-info-edit.html',
					formData: {stockType:'sh'},
					title : '新增股票',
					confirm : function(isConfirm, formData) {
						if (isConfirm) {
							$scope.promise = $scope.stockInfos.post({
								data:formData
							}, function(response) {
								$scope.showMsg('新增股票成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.updateItem = function(stockId) {
				$scope.stockInfos.get({urlPath : '/'+stockId}, function(response) {
					$scope.editDialog({
						templateUrl: 'stock-info-edit.html',
						formData: response,
						title : '编辑股票',
						confirm : function(isConfirm, formData) {
							if (isConfirm) {
								$scope.promise = $scope.stockInfos.put({
									urlPath : '/'+stockId,
									data:formData
								}, function(response) {
									$scope.showMsg('编辑股票成功');
									$scope.queryItems();
								});
							}
						}
					});
				});
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
