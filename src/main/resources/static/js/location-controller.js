'use strict'

var module = angular.module('demo.controllers', []);

module.controller('LocationController', [
	'$scope', 'LocationService', function($scope, LocationService) {
	
		$scope.locations = [];
		
		_refreshLocationsData();
		
		function _refreshLocationsData() {
			LocationService.findAllLocations().then(function success(response) {
				$scope.locations = response.data;
			}, function error(response) {
				console.log(response.statusText);
			});
		};
}])