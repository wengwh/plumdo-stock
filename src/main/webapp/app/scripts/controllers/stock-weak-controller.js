/**
 * 股票信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockWeakController', ['$scope', function ($scope) {

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
    }];

    $scope.query = {
      order: 'stockCode',
      stockRange: '0.01',
      stockDateBegin: $scope.getDate(-7),
      stockDateEnd: $scope.getDate(),
      limit: 10,
      page: 1
    };

    $scope.queryItems = function () {
      $scope.promise = $scope.stockReports.get({
        urlPath: '/stock-weaks',
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

  }]);

})();
