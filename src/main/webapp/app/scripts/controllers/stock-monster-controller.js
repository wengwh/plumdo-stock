/**
 * 热门板块信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockMonsterController', ['$scope', function ($scope) {

    $scope.stockMonsters = $scope.RestService($scope.restUrl.stockMonsters);

    $scope.tableData = [];
    $scope.totalItems = 0;
    $scope.tableCols = [{
      id: 'stockCode',
      name: '股票编码',
      orderBy: 'stockCode'
    }, {
      id: 'stockName',
      name: '股票名称',
      orderBy: 'stockName'
    }, {
      id: 'collectTime',
      name: '采集时间',
      orderBy: 'collectTime'
    }];


    $scope.query = {
      order: 'collectTime',
      collectTimeBegin: $scope.getDate(-7),
      collectTimeEnd: $scope.getDate(),
      limit: 10,
      page: 1
    };


    $scope.deleteItem = function (monsterId) {
      $scope.confirmDialog({
        title: '确认删除妖股',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.stockMonsters.delete({
              urlPath: '/' + monsterId,
            }, function () {
              $scope.showMsg('删除妖股成功');
              $scope.queryItems();
            });
          }
        }
      })
    };

    $scope.queryItems = function () {
      $scope.promise = $scope.stockMonsters.get({
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

    $scope.queryItems();

  }]);

})();
