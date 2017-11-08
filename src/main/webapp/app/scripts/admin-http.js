/**
 * Http全局拦截器，和rest接口调用封装
 * 
 * @author wengwh
 */

(function() {
	'use strict';

	angular.module('stockApp').config(['$httpProvider', function ($httpProvider) {
		$httpProvider.interceptors.push('httpInterceptor');
	}]);

	angular.module('stockApp').factory('httpInterceptor',[ '$q','$injector','$window','$rootScope',  function($q,$injector,$window,$rootScope) {
		var interceptor = {
			'request' : function(config) {
				// 成功的请求方法
				$rootScope.showProgress();
				return config || $q.when(config);
			},
			'response' : function(response) {
				// 响应成功
				$rootScope.hideProgress();
				return response || $q.when(response);
			},
			'requestError' : function(rejection) {
				// 请求发生了错误，如果能从错误中恢复，可以返回一个新的请求或promise
				console.info('requestError:'+rejection);
				return $q.reject(rejection);
			},
			'responseError' : function(rejection) {
				// 请求发生了错误，如果能从错误中恢复，可以返回一个新的响应或promise
				console.info('response error- rejection:'+rejection);
				$rootScope.hideProgress('请求异常',true);
				return $q.reject(rejection);
			}
		};
		return interceptor;
	}]);
	
	angular.module('stockApp').factory('RestService', ['$http','$q', function($http,$q) {
		var RestService = function (url,defaultConf) {
			this.url = url;
			this.defaultConf = defaultConf;
	  }
	  
		RestService.prototype = {
				url:null,
				defaultConf:null,
	      http: function (method, conf, callback) {
	      	if(this.defaultConf){
          	if(defaultConf.params){
               angular.extend(conf.params, defaultConf.params);
          	}
          	if(defaultConf.data){
              angular.extend(conf.data, defaultConf.data);
          	}
          	if(defaultConf.headers){
              angular.extend(conf.headers, defaultConf.headers);
          	}
          	if(defaultConf.responseType){
	            angular.extend(conf.responseType, defaultConf.responseType);
	        	}
          }
	        var defer = $q.defer();
          $http({method: method, url: this.url + (conf.urlPath||''), params: conf.params, data: conf.data, responseType:conf.responseType, headers: conf.headers})
	          .then(function successCallback(response) {
	      			callback(response.data);
	        		defer.resolve(response);
		        }, function errorCallback(response) {
		        	defer.reject(response);
		        });
	      },
	      get: function (conf, callback) {
	        this.http('GET', conf, callback);
		    },
		    post : function (conf, callback) {
		        this.http('POST', conf, callback);
		    },
		    put : function (conf, callback) {
		        this.http('PUT', conf, callback);
		    },
		    delete : function (conf, callback) {
		        this.http('DELETE', conf, callback);
		    }
	  };
		
		return function(contextRoot,defaultConf){
			return function (url) {
		  	return new RestService(contextRoot+url,defaultConf);
		  }; 
		}
	 
	}]);

})();