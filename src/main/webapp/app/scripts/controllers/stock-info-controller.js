/**
 * 下载异常控制
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockInfoController',
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

			$scope.deleteStockInfo = function() {
				$scope.confirmDialog({
					title : '确认删除股票',
					content : '删除选定的股票信息？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.stockInfos.delete({
								urlPath : '/'+$scope.selected[0].stockId,
							}, function(response) {
								$scope.showMsg('删除股票成功');
								$scope.queryItems(true);
							});
						}
					}
				})
			};
			
			$scope.createItem = function() {
				$scope.editDialog({
					templateUrl: 'stock-info-edit.html',
					title : '采编',
					confirm : function(isConfirm) {
					}
				})
			};

			$scope.exportExcel = function() {
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.exportHeadDownloadError,
					responseType : 'blob',
					data : {
						query : $scope.query,
						head : $scope.tableCols
					}
				}, function(response) {
					$scope.windowExportExcel(response.data,'下载异常信息');
				});
			};

			$scope.queryItems = function(reset) {
				if(reset && reset==true){
					$scope.selected = [];
				}
				$scope.promise = $scope.stockInfos.get({
					params:$scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);
	

})();
