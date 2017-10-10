/**
 * 系统路由配置
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp').config(
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('home', {
				url : '',
				templateUrl : 'views/main.html',
				controller : 'MainController',
				abstract : true
			}).state('home.upload', {
				url : '/upload',
				template: '<div ui-view></div>',
				abstract: true
			}).state('home.upload.error', {
				url : '/error',
				controller : 'UploadErrorController',
				templateUrl : 'views/upload-error.html'
			}).state('home.upload.member', {
				url : '/member',
				controller : 'UploadMemberController',
				templateUrl : 'views/upload-member.html'
			}).state('home.upload.change', {
				url : '/change',
				controller : 'UploadAccountChangeController',
				templateUrl : 'views/upload-change.html'
			}).state('home.download', {
				url : '/download',
				template: '<div ui-view></div>',
				abstract: true
			}).state('home.download.error', {
				url : '/error',
				controller : 'DownloadErrorController',
				templateUrl : 'views/download-error.html'
			}).state('home.download.member', {
				url : '/member',
				controller : 'DownloadMemberController',
				templateUrl : 'views/download-member.html'
			}).state('home.download.change', {
				url : '/change',
				controller : 'DownloadAccountChangeController',
				templateUrl : 'views/download-change.html'
			}).state('home.download.keymake', {
				url : '/keymake',
				controller : 'DownloadKeyMakeController',
				templateUrl : 'views/download-keymake.html'
			});

			$urlRouterProvider.otherwise('/upload/error');
	});

})();