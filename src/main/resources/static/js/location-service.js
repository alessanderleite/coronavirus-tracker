'use strict'

angular.module('demo.services', [])
	.factory('LocationService', ['$http', function($http) {
		var service = {};
		
		service.findAllLocations = function() {
			return $http.get('/findAllLocations');
		}
		
		return service;
	}]);