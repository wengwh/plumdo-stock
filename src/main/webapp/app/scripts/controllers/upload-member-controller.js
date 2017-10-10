/**
 * 上传异常控制
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('UploadMemberController',
		[ '$scope','$window',function($scope,$window) {

			$scope.tableData = [];
			$scope.totalItems = 0;
			$scope.selected = [];
			$scope.query = {};
			$scope.tableCols = [ {
				id : 'customername',
				name : '顾客姓名'
			}, {
				id : 'gradename',
				name : '等级名称',
				orderBy : 'gradename'
			}, {
				id : 'cardnum',
				name : '会员卡号',
				orderBy : 'cardnum'
			}, {
				id : 'accountbalance',
				name : '账户余额'
			}, {
				id : 'accountcash',
				name : '本金余额'
			}, {
				id : 'accountpresent',
				name : '赠送余额'
			}, {
				id : 'accountoverdraft',
				name : '可透支额'
			}, {
				id : 'cardintegral',
				name : '剩余积分'
			}, {
				id : 'totalintegral',
				name : '累计积分'
			}, {
				id : 'telephone',
				name : '联系电话'
			}, {
				id : 'sex',
				name : '性别'
			}, {
				id : 'statuscn',
				name : '状态'
			}, {
				id : 'carduselimitdate',
				name : '有效期限'
			}, {
				id : 'createdatetime',
				name : '建卡时间',
				orderBy : 'createdatetime'
			}, {
				id : 'createmanname',
				name : '建卡人'
			}, {
				id : 'createshopname',
				name : '建卡分店'
			}, {
				id : 'sellcardflagcn',
				name : '售卡状态'
			}, {
				id : 'sellcarddatetime',
				name : '售卡时间',
				orderBy : 'sellcarddatetime'
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
				id : 'email',
				name : 'Email'
			}, {
				id : 'company',
				name : '工作单位'
			}, {
				id : 'qq',
				name : 'QQ'
			}, {
				id : 'weibo',
				name : '微博号码'
			}, {
				id : 'weixin',
				name : '微信号码'
			}, {
				id : 'hasbirthdaycn',
				name : '本年已过生日'
			}, {
				id : 'chargedannualfeetime',
				name : '年费到期时间'
			}, {
				id : 'address',
				name : '联系地址'
			}, {
				id : 'remark',
				name : '备注'
			}, {
				id : 'totalconsumetimes',
				name : '包厢使用次数'
			}, {
				id : 'lastconsumedatetime',
				name : '最后消费时间'
			} ];

			$scope.memberGrades = [];

			$scope.RestService.post({
				urlPath : $scope.restUrl.queryLocalMemberGrades,
			}, function(response) {
				$scope.memberGrades = response;
			});

			$scope.confirmDate = function(a, b) {
				$scope.query.errorBeginDateTime = a;
				$scope.query.errorEndDateTime = b;
				$scope.choiceDatetime = a + ' 至 ' + b
			};

			$scope.confirmCreateDateTime = function(beginChoice, endChoice) {
				$scope.query.createBeginDateTime = beginChoice;
				$scope.query.createEndDateTime = endChoice;
				$scope.choiceCreateDateTime = beginChoice + ' 至 ' + endChoice
			};

			$scope.confirmSellDateTime = function(beginChoice, endChoice) {
				$scope.query.sellBeginDateTime = beginChoice;
				$scope.query.sellEndDateTime = endChoice;
				$scope.choiceSellDateTime = beginChoice + ' 至 ' + endChoice
			};

			$scope.resetQuery = function() {
				$scope.query = {
					order : '-sellcarddatetime',
					gradeId : '-1',
					customerName : '',
					cardNum : '',
					telePhone : '',
					createBeginDateTime : $scope.getDateTime(-7),
					createEndDateTime : $scope.getDateTime(),
					sellBeginDateTime : $scope.getDateTime(-7),
					sellEndDateTime : $scope.getDateTime(),
					limit : 10,
					page : 1
				};
				$scope.confirmCreateDateTime($scope.query.createBeginDateTime, $scope.query.createEndDateTime);
				$scope.confirmSellDateTime($scope.query.sellBeginDateTime, $scope.query.sellEndDateTime);
			}

			$scope.resetQuery();

			$scope.retry = function() {
				$scope.confirmDialog({
					title : '确认上传会员信息',
					content : $scope.selected.length > 0 ? '上传选定的会员信息？' : '根据查询条件上传会员信息？',
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.RestService.post({
								urlPath : $scope.restUrl.retryLocalMember,
								data : {
									selected : $scope.selected,
									query : $scope.query
								}
							}, function(response) {
								$scope.showMsg('上传会员信息成功');
								$scope.queryItems(true);
							});
						}
					}
				})
			};

			$scope.exportExcel = function() {
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.exportLocalMember,
					responseType : 'blob',
					data : {
						query : $scope.query,
						head : $scope.tableCols
					}
				}, function(response) {
					$scope.windowExportExcel(response.data,'本店会员信息');
				});
			};

			$scope.queryItems = function(reset) {
				if(reset && reset==true){
					$scope.selected = [];
				}
				$scope.promise = $scope.RestService.post({
					urlPath : $scope.restUrl.queryLocalMember,
					data : $scope.query
				}, function(response) {
					$scope.tableData = response.data;
					$scope.totalItems = response.total;
				});
			};
		
		} ]);

})();
