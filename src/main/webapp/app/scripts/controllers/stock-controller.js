/**
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('StockController', ['$scope', function ($scope) {
    $scope.tabItems = [{
      label: '股票信息',
      templateUrl: 'views/stock/stock-info.html'
    }, {
      label: '交易详情',
      templateUrl: 'views/stock/stock-detail.html'
    }, {
      label: '历史妖股',
      templateUrl: 'views/stock/stock-monster.html'
    }, {
      label: '黄金股',
      templateUrl: 'views/stock/stock-gold.html'
    }, {
      label: '弱势股',
      templateUrl: 'views/stock/stock-weak.html'
    }]

  }]);

})();
