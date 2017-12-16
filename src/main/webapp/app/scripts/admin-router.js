/**
 * 系统路由配置
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').config(
    function ($stateProvider, $urlRouterProvider) {

      $stateProvider.state('home', {
        url: '',
        templateUrl: 'views/main.html',
        controller: 'MainController',
        abstract: true
      }).state('home.stock', {
        url: '/stock',
        controller: 'StockController',
        templateUrl: 'views/stock/main-layout.html'
      }).state('home.stock-hot-plate', {
        url: '/stock-hot-plate',
        controller: 'StockHotPlateController',
        templateUrl: 'views/stock-hot-plate.html'
      }).state('home.lottery-detail', {
        url: '/lottery-detail',
        controller: 'LotteryDetailController',
        templateUrl: 'views/lottery-detail.html'
      });

      $urlRouterProvider.otherwise('/stock');
    });

})();