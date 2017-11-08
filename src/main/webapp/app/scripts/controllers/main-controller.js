/**
 * 主窗口控制
 * @author wengwh
 */
(function(){
	'use strict';
	
	angular.module('stockApp').controller('MainController',
		[ '$scope', '$mdSidenav', '$mdBottomSheet','$window', function($scope, $mdSidenav, $mdBottomSheet,$window) {
			
			$scope.menuItems = [ {
				name : '股票信息',
				icon : 'cloud_upload',
				sref : 'home.stock-info'
			} ];
			
	    $scope.toggleMenuList = function () {
	      var pending = $mdBottomSheet.hide() || $q.when(true);
	      pending.then(function(){
	        $mdSidenav('left').toggle();
	      });
	    };

	    $scope.selectItem = function (item,$event) {
	      $scope.showMsg('进入'+item.name+'窗口');
	    };

	    $scope.showSystemNote = function ($event) {
	        $mdBottomSheet.show({
	          parent: angular.element(document.getElementById('content')),
	          templateUrl: 'views/system-note.html',
	          bindToController : true,
	          targetEvent: $event
	        }).then(function(clickedItem) {
	          clickedItem && $log.debug( clickedItem.name + ' clicked!');
	        });
	    };
	    
		}]);

})();
