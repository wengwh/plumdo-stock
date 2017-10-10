/**
 * 上传异常控制
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('UploadErrorController',
		[ '$scope',function($scope) {

			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.query = {};
			$scope.tableCols = [ {
				id : 'datatypename',
				name : '数据类型',
				orderBy : 'datatypename'
			}, {
				id : 'cardnum',
				name : '会员卡号',
				orderBy : 'cardnum'
			}, {
				id : 'billvoucher',
				name : '单据号',
				orderBy : 'billvoucher'
			}, {
				id : 'errormessage',
				name : '错误信息'
			}, {
				id : 'exceptmessage',
				name : '异常信息'
			}, {
				id : 'operatedatetime',
				name : '异常时间',
				orderBy : 'operatedatetime'
			} ];

			$scope.dataTypes = [];

			$scope.RestService.post({
				urlPath : $scope.restUrl.queryLocalDataType
			}, function(response) {
				$scope.dataTypes = response;
			});

			$scope.confirmDate = function(beginChoice, endChoice) {
				$scope.query.errorBeginDateTime = beginChoice;
				$scope.query.errorEndDateTime = endChoice;
				$scope.choiceDatetime = beginChoice + ' 至 ' + endChoice;
			};

			$scope.resetQuery = function() {
				$scope.query = {
					order : '-operatedatetime',
					dataType : '-1',
					cardNum : '',
					billVoucher : '',
					errorBeginDateTime : $scope.getDateTime(-7),
					errorEndDateTime : $scope.getDateTime(),
					limit : 10,
					page : 1
				};
				$scope.confirmDate($scope.query.errorBeginDateTime,$scope.query.errorEndDateTime);
			}

			$scope.resetQuery();

			$scope.retry = function() {
				$scope.confirmDialog({
					title : '确认重传异常信息',
					content : $scope.selected.length > 0 ? '重新上传选定的异常信息？' : '根据查询条件重新上传异常信息？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.RestService.post({
								urlPath : $scope.restUrl.retryLocalUploadError,
								data : {
									selected : $scope.selected,
									query : $scope.query
								}
							}, function(response) {
								$scope.showMsg('重新上传异常信息成功');
								$scope.queryItems(true);
							});
						}
					}
				})
			};

			$scope.exportExcel = function() {
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.exportLocalUploadError,
					responseType : 'blob',
					data : {
						query : $scope.query,
						head : $scope.tableCols
					}
				}, function(response) {
					$scope.windowExportExcel(response.data,'上传异常信息');
				});
			};

			$scope.queryItems = function(reset) {
				if(reset && reset==true){
					$scope.selected = [];
				}
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.queryLocalUploadError,
					data : $scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);

})();
