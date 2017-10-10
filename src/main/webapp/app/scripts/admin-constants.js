/**
 * 系统常量定义
 * @author wengwh
 */
(function() {
	'use strict';

	angular.module('stockApp')
		.constant('contextRoot', ".")
//		.constant('contextRoot', "http://127.0.0.1:60919")
		.constant('restUrl', {
			'userLogin' : '/user/login',
			'queryLocalCatenationShop' : '/local/catenation-shop/query',
			'queryLocalDataType' : '/local/data-type/query',
			'queryLocalUploadError' : '/local/upload-error/query',
			'exportLocalUploadError' : '/local/upload-error/export',
			'retryLocalUploadError' : '/local/upload-error/retry',
			'queryLocalMemberGrades' : '/local/member-grade/query',
			'queryLocalMember' : '/local/member/query',
			'exportLocalMember' : '/local/member/export',
			'retryLocalMember' : '/local/member/retry',
			'queryLocalChangeType' : '/local/change-type/query',
			'queryLocalAccountChange' : '/local/account-change/query',
			'exportLocalAccountChange' : '/local/account-change/export',
			'retryLocalAccountChange' : '/local/account-change/retry',
			'queryHeadDataType' : '/head/data-type/query',
			'queryHeadDownloadError' : '/head/download-error/query',
			'exportHeadDownloadError' : '/head/download-error/export',
			'retryHeadDownloadError' : '/head/download-error/retry',
			'queryHeadMemberGrades' : '/head/member-grade/query',
			'queryHeadMember' : '/head/member/query',
			'exportHeadMember' : '/head/member/export',
			'retryHeadMember' : '/head/member/retry',
			'queryHeadChangeType' : '/head/change-type/query',
			'queryHeadAccountChange' : '/head/account-change/query',
			'exportHeadAccountChange' : '/head/account-change/export',
			'retryHeadAccountChange' : '/head/account-change/retry',
			'retryHeadKeyMake' : '/head/key-make/retry'
		});

})();