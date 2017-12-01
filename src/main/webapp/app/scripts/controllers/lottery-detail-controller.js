/**
 * 六合彩信息采编
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('LotteryDetailController',
		[ '$scope',function($scope) {

			$scope.lotteryDetails = $scope.RestService($scope.restUrl.lotteryDetails);
			
			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.tableCols = [ {
				id : 'lotteryYear',
				name : '年份',
				orderBy : 'lotteryYear'
			}, {
				id : 'lotteryPeriod',
				name : '期数',
				orderBy : 'lotteryPeriod'
			}, {
				id : 'lotteryN1',
				name : '号码1',
				orderBy : 'lotteryN1'
			}, {
				id : 'lotteryN2',
				name : '号码2',
				orderBy : 'lotteryN2'
			}, {
				id : 'lotteryN3',
				name : '号码3',
				orderBy : 'lotteryN3'
			}, {
				id : 'lotteryN4',
				name : '号码4',
				orderBy : 'lotteryN4'
			}, {
				id : 'lotteryN5',
				name : '号码5',
				orderBy : 'lotteryN5'
			}, {
				id : 'lotteryN6',
				name : '号码6',
				orderBy : 'lotteryN6'
			}, {
				id : 'lotteryCode',
				name : '特码',
				orderBy : 'lotteryCode'
			} ];

			$scope.query = {
				order : 'lotteryYear',
				limit : 10,
				page : 1
			};
			
			$scope.deleteItem = function() {
				$scope.confirmDialog({
					title : '确认删除六合彩',
					content : '删除选定的六合彩？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.lotteryDetails.delete({
								urlPath : '/'+$scope.selected[0].detailId,
							}, function(response) {
								$scope.showMsg('删除六合彩成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.createItem = function() {
				$scope.editDialog({
					templateUrl: 'lottery-detail-edit.html',
					title : '新增六合彩',
					confirm : function(isConfirm, formData) {
						if (isConfirm) {
							$scope.promise = $scope.lotteryDetails.post({
								data:formData
							}, function(response) {
								$scope.showMsg('新增六合彩成功');
								$scope.queryItems();
							});
						}
					}
				})
			};
			
			$scope.updateItem = function() {
				if($scope.selected[0]){
					$scope.lotteryDetails.get({urlPath : '/'+$scope.selected[0].detailId}, function(response) {
						$scope.editDialog({
							templateUrl: 'lottery-detail-edit.html',
							formData: response,
							title : '编辑六合彩',
							confirm : function(isConfirm, formData) {
								if (isConfirm) {
									$scope.promise = $scope.lotteryDetails.put({
										urlPath : '/'+$scope.selected[0].detailId,
										data:formData
									}, function(response) {
										$scope.showMsg('编辑六合彩成功');
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
				$scope.promise = $scope.lotteryDetails.get({
					params:$scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);
	
})();
