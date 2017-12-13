/**
 * 热门板块信息采编
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockHotPlateController',
		[ '$scope',function($scope) {

			$scope.stockHotPlates = $scope.RestService($scope.restUrl.stockHotPlates);
			
			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.tableCols = [ {
				id : 'plateName',
				name : '板块名称',
				orderBy : 'plateName'
			}, {
				id : 'collectTime',
				name : '采集时间',
				orderBy : 'collectTime'
			} ];

			
			$scope.query = {
				order : 'collectTime',
				plateName : '',
				collectTimeBegin : $scope.getDateTime(-7),
				collectTimeEnd : $scope.getDateTime(7),
				limit : 10,
				page : 1
			};
			
			$scope.deleteItem = function() {
				$scope.confirmDialog({
					title : '确认删除热门板块',
					content : '删除选定的热门板块？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.stockHotPlates.delete({
								urlPath : '/'+$scope.selected[0].hotPlateId,
							}, function(response) {
								$scope.showMsg('删除热门板块成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.createItem = function() {
				$scope.editDialog({
					templateUrl: 'stock-hot-plate-edit.html',
					title : '新增热门板块',
					confirm : function(isConfirm, formData) {
						if (isConfirm) {
							$scope.promise = $scope.stockHotPlates.post({
								data:formData
							}, function(response) {
								$scope.showMsg('新增热门板块成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.updateItem = function() {
				if($scope.selected[0]){
					$scope.stockHotPlates.get({urlPath : '/'+$scope.selected[0].hotPlateId}, function(response) {
						$scope.editDialog({
							templateUrl: 'stock-hot-plate-edit.html',
							formData: response,
							title : '编辑热门板块',
							confirm : function(isConfirm, formData) {
								if (isConfirm) {
									$scope.promise = $scope.stockHotPlates.put({
										urlPath : '/'+$scope.selected[0].hotPlateId,
										data:formData
									}, function(response) {
										$scope.showMsg('编辑热门板块成功');
										$scope.queryItems();
									});
								}
							}
						});
					});
				} else {
					$scope.showErrorMsg('请选择行');
				}
			};

			$scope.queryItems = function() {
				$scope.selected = [];
				$scope.promise = $scope.stockHotPlates.get({
					params:$scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);
	
})();
