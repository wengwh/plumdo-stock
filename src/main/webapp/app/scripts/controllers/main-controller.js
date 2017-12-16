/**
 * 主窗口控制
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('MainController', ['$scope', '$mdSidenav', '$mdBottomSheet', function ($scope, $mdSidenav, $mdBottomSheet) {

    $scope.menuItems = [{
      name: '股票管理',
      icon: 'dashboard',
      sref: 'home.stock'
    }, {
      name: '热门板块',
      icon: 'whatshot',
      sref: 'home.stock-hot-plate'
    }, {
      name: '六合彩',
      icon: 'view_comfy',
      sref: 'home.lottery-detail'
    }];

    $scope.toggleMenuList = function () {
      var pending = $mdBottomSheet.hide();
      pending.then(function () {
        $mdSidenav('left').toggle();
      });
    };


    $scope.systemParameters = $scope.RestService($scope.restUrl.systemParameters);

    $scope.setWeiboToken = function () {
      $scope.systemParameters.get({urlPath: '/weibo_access_token'}, function (response) {
        $scope.editDialog({
          templateUrl: 'weibo-token-edit.html',
          formData: response,
          title: '编辑微博Token',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.systemParameters.put({
                urlPath: '/weibo_access_token',
                data: formData
              }, function () {
                $scope.showMsg('编辑微博Token成功');
                $scope.queryItems(true);
              });
            }
          }
        });
      });
    };

    $scope.selectItem = function (item) {
      $scope.showMsg('进入' + item.name + '窗口');
    };

    $scope.showSystemNote = function ($event) {
      $mdBottomSheet.show({
        parent: angular.element(document.getElementById('content')),
        templateUrl: 'views/common/system-note.html',
        bindToController: true,
        targetEvent: $event
      });
    };

  }]);

})();
