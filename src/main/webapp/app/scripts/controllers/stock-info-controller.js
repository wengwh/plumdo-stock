/**
 * 股票信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockInfoController', ['$scope', function ($scope) {

    $scope.stockInfos = $scope.RestService($scope.restUrl.stockInfos);
    $scope.stockMonsters = $scope.RestService($scope.restUrl.stockMonsters);
    $scope.weibos = $scope.RestService($scope.restUrl.weibos);

    $scope.tableData = [];
    $scope.totalItems = 0;
    $scope.selectedItems = [];
    $scope.tableCols = [{
      id: 'stockCode',
      name: '股票编码',
      orderBy: 'stockCode'
    }, {
      id: 'stockName',
      name: '股票名称',
      orderBy: 'stockName'
    }];

    $scope.query = {
      order: 'stockCode',
      stockType: '%',
      limit: 10,
      page: 1
    };

    $scope.batchDeleteItem = function () {
      $scope.confirmDialog({
        title: '确认批量删除选定的股票信息',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockInfos.post({
              urlPath: '/batch-delete',
              data: $scope.selectedItems.map(function (v) {
                return v.stockId;
              })
            }, function () {
              $scope.showMsg('批量删除股票信息成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.collectDetail = function () {
      $scope.confirmDialog({
        title: '确认采集股票最近一天的交易信息',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockInfos.post({
              urlPath: '/collect'
            }, function (response) {
              $scope.showMsg('采集股票交易信息成功');
              $scope.promise = $scope.weibos.post({
                urlPath: '/send',
                data: response
              }, function () {
                $scope.showMsg('发送微博信息成功');
              });
            });
          }
        }
      });
    };


    $scope.deleteItem = function (stockId) {
      $scope.confirmDialog({
        title: '确认删除股票信息',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockInfos.delete({
              urlPath: '/' + stockId,
            }, function () {
              $scope.showMsg('删除股票成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.createItem = function () {
      $scope.editDialog({
        templateUrl: 'stock-info-edit.html',
        formData: {stockType: 'sh'},
        title: '新增股票',
        confirm: function (isConfirm, formData) {
          if (isConfirm) {
            $scope.promise = $scope.stockInfos.post({
              data: formData
            }, function () {
              $scope.showMsg('新增股票成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.updateItem = function (stockId) {
      $scope.stockInfos.get({urlPath: '/' + stockId}, function (response) {
        $scope.editDialog({
          templateUrl: 'stock-info-edit.html',
          formData: response,
          title: '编辑股票',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.stockInfos.put({
                urlPath: '/' + stockId,
                data: formData
              }, function () {
                $scope.showMsg('编辑股票成功');
                $scope.queryItems(true);
              });
            }
          }
        });
      });
    };

    $scope.setMonster = function (stockId) {
      $scope.stockInfos.get({urlPath: '/' + stockId}, function (response) {
        $scope.editDialog({
          templateUrl: 'stock-monster-edit.html',
          formData: angular.extend(response, {collectTime: $scope.getDate()}),
          title: '添加妖股',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.stockMonsters.post({
                data: formData
              }, function () {
                $scope.showMsg('添加妖股成功');
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
      $scope.promise = $scope.stockInfos.get({
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

    $scope.queryItems();

  }]);

})();
