/**
 * 下载主键表控制
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').controller('DownloadKeyMakeController',
		[ '$scope',function($scope) {

			$scope.retry = function() {
				$scope.confirmDialog({
					title : '确认重载主键表',
					content : '重新下载主键表？' ,
					confirm : function(isConfirm) {
						if (isConfirm) {
							$scope.promise = $scope.RestService.post({
								urlPath : $scope.restUrl.retryHeadKeyMake
							}, function(response) {
								$scope.showMsg('重新下载主键表成功');
							});
						}
					}
				})
			};
			
		} ]);

})();
