/**
 * 六合彩信息采编
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').controller('LotteryDetailController', ['$scope', function ($scope) {

    $scope.lotteryDetails = $scope.RestService($scope.restUrl.lotteryDetails);

    $scope.tableData = [];
    $scope.totalItems = 0;
    $scope.selectedItems = [];
    $scope.tableCols = [{
      id: 'lotteryYear',
      name: '年份',
      orderBy: 'lotteryYear'
    }, {
      id: 'lotteryPeriod',
      name: '期数',
      orderBy: 'lotteryPeriod'
    }, {
      id: 'lotteryN1',
      name: '号码1',
      orderBy: 'lotteryN1'
    }, {
      id: 'lotteryN2',
      name: '号码2',
      orderBy: 'lotteryN2'
    }, {
      id: 'lotteryN3',
      name: '号码3',
      orderBy: 'lotteryN3'
    }, {
      id: 'lotteryN4',
      name: '号码4',
      orderBy: 'lotteryN4'
    }, {
      id: 'lotteryN5',
      name: '号码5',
      orderBy: 'lotteryN5'
    }, {
      id: 'lotteryN6',
      name: '号码6',
      orderBy: 'lotteryN6'
    }, {
      id: 'lotteryCode',
      name: '特码',
      orderBy: 'lotteryCode'
    }];

    $scope.query = {
      order: 'lotteryYear',
      limit: 10,
      page: 1
    };

    $scope.batchDeleteItem = function (hotPlateId) {
      $scope.confirmDialog({
        title: '确认批量删除选定的六合彩',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.lotteryDetails.post({
              urlPath: '/batch-delete',
              data: $scope.selectedItems.map(function (v) {
                return v.detailId;
              })
            }, function () {
              $scope.showMsg('批量删除六合彩成功');
              $scope.queryItems(true);
            });
          }
        }
      });
    };

    $scope.deleteItem = function (detailId) {
      $scope.confirmDialog({
        title: '确认删除六合彩',
        confirm: function (isConfirm) {
          if (isConfirm) {
            $scope.promise = $scope.lotteryDetails.delete({
              urlPath: '/' + detailId
            }, function () {
              $scope.showMsg('删除六合彩成功');
              $scope.queryItems(true);
            });
          }
        }
      })
    };

    $scope.createItem = function () {
      $scope.editDialog({
        templateUrl: 'lottery-detail-edit.html',
        title: '新增六合彩',
        confirm: function (isConfirm, formData) {
          if (isConfirm) {
            $scope.promise = $scope.lotteryDetails.post({
              data: formData
            }, function () {
              $scope.showMsg('新增六合彩成功');
              $scope.queryItems(true);
            });
          }
        }
      })
    };

    $scope.updateItem = function (detailId) {
      $scope.lotteryDetails.get({urlPath: '/' + detailId}, function (response) {
        $scope.editDialog({
          templateUrl: 'lottery-detail-edit.html',
          formData: response,
          title: '编辑六合彩',
          confirm: function (isConfirm, formData) {
            if (isConfirm) {
              $scope.promise = $scope.lotteryDetails.put({
                urlPath: '/' + detailId,
                data: formData
              }, function () {
                $scope.showMsg('编辑六合彩成功');
                $scope.queryItems(true);
              });
            }
          }
        });
      });
    };

    $scope.queryItems = function (isReset) {
      if (isReset == true) {
        $scope.selectedItems = [];
      }
      $scope.promise = $scope.lotteryDetails.get({
        params: $scope.query
      }, function (response) {
        $scope.tableData = response.data;
        $scope.totalItems = response.total;
      });
    };

    $scope.queryItems();

  }]);

})();
