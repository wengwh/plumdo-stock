/**
 * 热门板块信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockHotPlateController', ['$scope', function ($scope) {

    $scope.stockHotPlates = $scope.RestService($scope.restUrl.stockHotPlates);

    $scope.tableData = [];
    $scope.totalItems = 0;
    $scope.selectedItems = [];

    $scope.tableCols = [{
      id: 'plateName',
      name: '板块名称',
      orderBy: 'plateName'
    }, {
      id: 'collectTime',
      name: '采集时间',
      orderBy: 'collectTime'
    }];


    $scope.query = {
      order: 'collectTime',
      collectTimeBegin: null,
      collectTimeEnd: null,
      limit: 10,
      page: 1
    };

    $scope.batchDeleteItem = function () {
      $scope.confirmDialog({
        title: '确认批量删除选定的热门板块',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockHotPlates.post({
              urlPath: '/batch-delete',
              data: $scope.selectedItems.map(function (v) {
                return v.hotPlateId;
              })
            }, function () {
              $scope.showMsg('批量删除热门板块成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.deleteItem = function (hotPlateId) {
      $scope.confirmDialog({
        title: '确认删除热门板块',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockHotPlates.delete({
              urlPath: '/' + hotPlateId
            }, function () {
              $scope.showMsg('删除热门板块成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.createItem = function () {
      $scope.editDialog({
        templateUrl: 'stock-hot-plate-edit.html',
        title: '新增热门板块',
        confirm: function (isConfirm, formData) {
          if (isConfirm) {
            $scope.promise = $scope.stockHotPlates.post({
              data: formData
            }, function () {
              $scope.showMsg('新增热门板块成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.updateItem = function (hotPlateId) {
      $scope.stockHotPlates.get({urlPath: '/' + hotPlateId}, function (response) {
        $scope.editDialog({
          templateUrl: 'stock-hot-plate-edit.html',
          formData: response,
          title: '编辑热门板块',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.stockHotPlates.put({
                urlPath: '/' + hotPlateId,
                data: formData
              }, function () {
                $scope.showMsg('编辑热门板块成功');
                $scope.queryItems(true);
              });
            }
          }
        });
      });
    };

    $scope.queryItems = function (isReset) {
      if (isReset === true) {
        $scope.selectedItems = [];
      }
      $scope.promise = $scope.stockHotPlates.get({
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

    $scope.queryItems();

  }]);

})();
