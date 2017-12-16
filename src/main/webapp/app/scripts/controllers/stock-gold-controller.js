/**
 * 股票信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockGoldController', ['$scope', function ($scope) {

    $scope.stockReports = $scope.RestService($scope.restUrl.stockReports);

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
      id: 'beginPrice',
      name: '开盘价',
      orderBy: 'beginPrice'
    }, {
      id: 'endPrice',
      name: '收盘价',
      orderBy: 'endPrice'
    }, {
      id: 'highestPrice',
      name: '最高价',
      orderBy: 'highestPrice'
    }, {
      id: 'latestPrice',
      name: '最低价',
      orderBy: 'latestPrice'
    }, {
      id: 'stockNum',
      name: '交易数',
      orderBy: 'stockNum'
    }, {
      id: 'stockMoney',
      name: '交易金额',
      orderBy: 'stockMoney'
    }, {
      id: 'stockDate',
      name: '交易时间',
      orderBy: 'stockDate'
    }];

    $scope.query = {
      order: 'stockDate',
      stockDateBegin: $scope.getDate(-7),
      stockDateEnd: $scope.getDate(),
      limit: 10,
      page: 1
    };

    $scope.queryItems = function () {
      $scope.promise = $scope.stockReports.get({
        urlPath: '/stock-golds',
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

    $scope.queryItems();

  }]);

})();
