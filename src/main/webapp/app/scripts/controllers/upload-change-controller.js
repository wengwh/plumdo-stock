/**
 * 上传账户变动控制器
 * 
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('UploadAccountChangeController',
		[ '$scope',function($scope) {

			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.query = {};
			$scope.tableCols = [ 
			{
				id : 'cardnum',
				name : '会员卡号',
				orderBy : 'cardnum'
			}, {
				id : 'changevoucher',
				name : '单据号'
			}, {
				id : 'changedatetime',
				name : '变动时间',
				orderBy : 'changedatetime'
			}, {
				id : 'changetypename',
				name : '变动类型'
			}, {
				id : 'customername',
				name : '会员姓名'
			}, {
				id : 'sex',
				name : '性别'
			}, {
				id : 'gradename',
				name : '会员等级'
			}, {
				id : 'qq',
				name : 'QQ'
			}, {
				id : 'telephone',
				name : '联系电话'
			}, {
				id : 'birthdate',
				name : '出生日期'
			}, {
				id : 'lunarflagcn',
				name : '出生日期类型'
			}, {
				id : 'papertypename',
				name : '证件类型'
			}, {
				id : 'papernumber',
				name : '证件号码'
			}, {
				id : 'statuscn',
				name : '会员状态'
			}, {
				id : 'changemoney',
				name : '变动金额'
			}, {
				id : 'cashchangemoney',
				name : '变动本金'
			}, {
				id : 'presenthappenmoney',
				name : '变动赠送'
			}, {
				id : 'roompresenthappen',
				name : '变动房费赠送'
			}, {
				id : 'superpresenthappen',
				name : '变动商品赠送'
			}, {
				id : 'presentvalidtime',
				name : '变动赠送有效期'
			}, {
				id : 'changeintegral',
				name : '变动积分'
			}, {
				id : 'accountbalance',
				name : '账户余额'
			}, {
				id : 'integralbalance',
				name : '剩余积分'
			}, {
				id : 'avoidmanname',
				name : '免单人'
			}, {
				id : 'avoidmoney',
				name : '免单金额'
			}, {
				id : 'rechargemanname',
				name : '销售经理'
			}, {
				id : 'operatorname',
				name : '操作人'
			}, {
				id : 'remark',
				name : '备注'
			} ];

			$scope.changeTypes = [];

			$scope.RestService.post({
				urlPath : $scope.restUrl.queryLocalChangeType,
			}, function(response) {
				$scope.changeTypes = response;
			});

			$scope.confirmDate = function(a, b) {
				$scope.query.errorBeginDateTime = a;
				$scope.query.errorEndDateTime = b;
				$scope.choiceDatetime = a + ' 至 ' + b
			};

			$scope.confirmChangeDateTime = function(beginChoice, endChoice) {
				$scope.query.changeBeginDateTime = beginChoice;
				$scope.query.changeEndDateTime = endChoice;
				$scope.choiceChangeDateTime = beginChoice + ' 至 ' + endChoice
			};

			$scope.resetQuery = function() {
				$scope.query = {
					order : '-changedatetime',
					changeType : '-1',
					cardNum : '',
					changeVoucher : '',
					changeBeginDateTime : $scope.getDateTime(-7),
					changeEndDateTime : $scope.getDateTime(),
					limit : 10,
					page : 1
				};
				$scope.confirmChangeDateTime($scope.query.changeBeginDateTime, $scope.query.changeEndDateTime);
			}

			$scope.resetQuery();

			$scope.retry = function() {
				$scope.confirmDialog({
					title : '确认上传账户变动记录',
					content : $scope.selected.length > 0 ? '上传选定的账户变动记录？' : '根据查询条件上传账户变动记录？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.RestService.post({
								urlPath : $scope.restUrl.retryLocalAccountChange,
								data : {
									selected : $scope.selected,
									query : $scope.query
								}
							}, function(response) {
								$scope.showMsg('上传账户变动记录成功');
								$scope.queryItems(true);
							});
						}
					}
				})
			};

			$scope.exportExcel = function() {
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.exportLocalAccountChange,
					responseType : 'blob',
					data : {
						query : $scope.query,
						head : $scope.tableCols
					}
				}, function(response) {
					$scope.windowExportExcel(response.data,'本店会员账户变动信息');
				});
			};

			$scope.queryItems = function(reset) {
				if(reset && reset==true){
					$scope.selected = [];
				}
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.queryLocalAccountChange,
					data : $scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);

})();
