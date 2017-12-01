/**
 * 热门板块信息采编
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('StockMonsterController',
		[ '$scope',function($scope) {

			$scope.stockMonsters = $scope.RestService($scope.restUrl.stockMonsters);
			
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

			$scope.confirmCollectTime = function(begin, end) {
				$scope.query.collectTimeBegin = begin;
				$scope.query.collectTimeEnd = end;
				$scope.choiceCollectTime = begin + ' 至 ' + end;
			};
			
			$scope.query = {
				order : 'collectTime',
				plateName : '',
				collectTimeBegin : $scope.getDateTime(-7),
				collectTimeEnd : $scope.getDateTime(7),
				limit : 10,
				page : 1
			};
			
			$scope.confirmCollectTime($scope.query.collectTimeBegin, $scope.query.collectTimeEnd);
			
			$scope.deleteItem = function() {
				$scope.confirmDialog({
					title : '确认删除热门板块',
					content : '删除选定的热门板块？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.stockMonsters.delete({
								urlPath : '/'+$scope.selected[0].monsterId,
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
					templateUrl: 'stock-monster-edit.html',
					formData: {collectTime:$scope.getDateTime()},
					title : '新增热门板块',
					confirm : function(isConfirm, formData) {
						if (isConfirm) {
							$scope.promise = $scope.stockMonsters.post({
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
					$scope.stockMonsters.get({urlPath : '/'+$scope.selected[0].monsterId}, function(response) {
						$scope.editDialog({
							templateUrl: 'stock-monster-edit.html',
							formData: response,
							title : '编辑热门板块',
							confirm : function(isConfirm, formData) {
								if (isConfirm) {
									$scope.promise = $scope.stockMonsters.put({
										urlPath : '/'+$scope.selected[0].monsterId,
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
				$scope.promise = $scope.stockMonsters.get({
					params:$scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);
	
})();
