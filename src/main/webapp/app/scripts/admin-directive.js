/**
 * 时间指令，由于第三方的时间使用默认按钮，input不会触发，自定义通过事件触发
 *
 * @author wengwh
 */
(function () {
  'use strict';

  angular.module('stockApp').directive('fixedHeader', ['$timeout', '$window', function ($timeout, $window) {
    return {
      restrict: 'A',
      link: function ($scope, $elem, $attrs, $ctrl) {
        var headerHeight = $attrs.fixedHeader || 430;
        // github的固定表头，在多列情况不对齐，直接修改代码，不使用bower引入
        var elem = $elem[0];

        function tableDataLoaded() {
          // first cell in the tbody exists when data is loaded but doesn't have
          // a width
          // until after the table is transformed
          var firstCell = elem.querySelector('tbody tr:first-child td:first-child');
          return firstCell && !firstCell.style.minWidth;
        }
        // wait for data to load and then transform the table
        $scope.$watch(tableDataLoaded, function (isTableDataLoaded) {
          if (isTableDataLoaded) {
            transformTable();
          }
        });

        function transformTable() {
          // reset display styles so column widths are correct when measured
          // below
          angular.element(elem.querySelectorAll('thead, tbody, tfoot')).css('display', '');

          // wrap in $timeout to give table a chance to finish rendering
          $timeout(function () {
            // set widths of columns
            var thElems = elem.querySelectorAll('tr:first-child th');
            angular.forEach(thElems, function (thElem, i) {

              var tdElems = elem.querySelector('tbody tr:first-child td:nth-child(' + (i + 1) + ')');
              var tfElems = elem.querySelector('tfoot tr:first-child td:nth-child(' + (i + 1) + ')');

              var columnWidth = tdElems ? tdElems.offsetWidth : thElem.offsetWidth;
              if (i == 0 || i == thElems.length - 2) {
                columnWidth = columnWidth - 25;
              } else if (i == 1) {
                columnWidth = columnWidth - 48;
              } else if (i < thElems.length - 2) {
                columnWidth = columnWidth - 56;
              }
              if (tdElems) {
                tdElems.style.minWidth = columnWidth + 'px';
                tdElems.style.width = columnWidth + 'px';
              }
              if (thElem) {
                thElem.style.minWidth = columnWidth + 'px';
                thElem.style.width = columnWidth + 'px';
              }
              if (tfElems) {
                tfElems.style.width = columnWidth + 'px';
              }
            });

            // set css styles on thead and tbody
            angular.element(elem.querySelectorAll('thead, tfoot')).css('display', 'block');

            angular.element(elem.querySelectorAll('tbody')).css({
              'display': 'block',
              'height': $attrs.tableHeight || ($window.innerHeight - headerHeight > 200 ? $window.innerHeight - headerHeight : 200),
              'overflow': 'auto'
            });

            angular.element(window).bind('resize', function () {
              angular.element(elem.querySelectorAll('tbody')).css({
                'height': $attrs.tableHeight || ($window.innerHeight - headerHeight > 200 ? $window.innerHeight - headerHeight : 200)
              });
            });

            // reduce width of last column by width of scrollbar
            var tbody = elem.querySelector('tbody');
            var scrollBarWidth = tbody.offsetWidth - tbody.clientWidth;
            if (scrollBarWidth > 0) {
              // for some reason trimming the width by 2px lines everything up
              // better
              scrollBarWidth -= 2;
              var lastColumn = elem.querySelector('tbody tr:first-child td:last-child');
              lastColumn.style.width = (lastColumn.offsetWidth - scrollBarWidth) + 'px';
            }
          });
        }
      }
    };
  }]).directive('dynamicBind', ['$compile', function ($compile) {
    return {
      restrict: 'A',
      priority: 1,
      terminal: true,
      link: function (scope, element, attrs) {
        // 由于table的绑定需要双重绑定，自定义一个动态绑定指令
        var dynamicBind = scope.$eval(attrs.dynamicBind);
        if (attrs.ngBind === dynamicBind || !dynamicBind) {
          return;
        }
        if (attrs.dynamicBindPre) {
          if (dynamicBind.indexOf(".") >= 0) {
            dynamicBind = attrs.dynamicBindPre + '.' + dynamicBind;
          } else {
            dynamicBind = attrs.dynamicBindPre + '[\'' + dynamicBind + '\']';
          }
        }
        if (attrs.dynamicBindSuf) {
          if (dynamicBind.indexOf(".") >= 0) {
            dynamicBind = dynamicBind + '.' + attrs.dynamicBindSuf;
          } else {
            dynamicBind = dynamicBind + '[\'' + attrs.dynamicBindSuf + '\']';
          }
        }

        element.attr('ng-bind', dynamicBind);
        if (dynamicBind === '') {
          element.removeAttr('ng-bind');
        }
        element.removeAttr('dynamic-bind');
        element.unbind();
        $compile(element)(scope);
      }
    };
  }]).directive('viewLoad', function () {
    return {
      restrict: 'A',
      templateUrl: 'views/common/view-load.html',
      link: function (scope, element) {
        $(element).fadeIn(300);
      }
    };
  });

})();
