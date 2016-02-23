var app = angular.module('app', []).run(function($rootScope) {
	// The first-time rootScope field initialization
	$rootScope.mydata={ token : ""};
});

app.controller('InitController', function($scope, $rootScope) {

	$scope.token = "";
	
	$scope.setToken = function(){
		$rootScope.token = $scope.token;
	}
});

app.controller('TestController', function($scope, $rootScope) {

	$scope.tokkk = $rootScope.token;
	              
});