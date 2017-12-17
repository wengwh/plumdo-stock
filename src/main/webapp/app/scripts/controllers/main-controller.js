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


    $scope.weibos = $scope.RestService($scope.restUrl.weibos);

    $scope.sendWeibo = function () {
      $scope.editDialog({
        templateUrl: 'weibo-send.html',
        title: '发送微博信息',
        confirm: function (isConfirm, formData) {
          if (isConfirm) {
            $scope.promise = $scope.weibos.post({
              urlPath: '/send',
              data: formData
            }, function () {
              $scope.showMsg('发送微博信息成功');
            });
          }
        }
      });
    };
    
    $scope.setWeiboParams = function () {
      $scope.weibos.get({urlPath: '/parameters'}, function (response) {
        $scope.editDialog({
          templateUrl: 'weibo-params-edit.html',
          formData: response,
          title: '编辑微博授权信息',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.weibos.put({
                urlPath: '/parameters',
                data: formData
              }, function () {
                $scope.showMsg('编辑微博授权信息成功');
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
