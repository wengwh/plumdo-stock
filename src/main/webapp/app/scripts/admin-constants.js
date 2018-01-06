/**
 * 系统常量定义
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp')
//   .constant('contextRoot', ".")
    .constant('contextRoot', "http://127.0.0.1:8088")
    .constant('restUrl', {
    'stockInfos': '/stock-infos',
    'stockMonsters': '/stock-monsters',
    'stockReports': '/stock-reports',
    'stockHotPlates': '/stock-hot-plates',
    'lotteryDetails': '/lottery-details',
    'weibos': '/weibos'
  });

})();