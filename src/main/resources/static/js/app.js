'use strict'

var app = angular.module('app', []);
	
app.controller('HomeController', function($scope) {
	$scope.locations = [
		{state: 'São Paulo', country: 'Brazil', latestTotalCases: 2397, diffFromPrevDay: 12}
	];			
});
		