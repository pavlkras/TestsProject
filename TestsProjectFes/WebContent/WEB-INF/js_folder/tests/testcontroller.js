var app = angular.module('test_app',[]);

app.controller('testController', function($scope,$http) {
	$scope.autocategories;
	$scope.admincategories;
	$scope.urlBes = "/TestsProjectBes/tests";
	$scope.urlAutoCategories = $scope.urlBes + "/autoList";
	$scope.urlAdminCategories = $scope.urlBes + "/adminList";
	$scope.token = token;
	

	
	$scope.renewCategories = function() {		
		$http({
			method: 'GET',
			url: $scope.urlAutoCategories,
			headers: {
				'Authorization' : $scope.token
			}
		}).then(function(response) {
	    	$scope.autocategories = response.data;
	    });
		
		$http({
			method: 'GET',
			url: $scope.urlAdminCategories,
			headers: {
				'Authorization' : token
			}
		}).then(function(response) {
	    	$scope.admincategories = response.data;
	    });
			
		
	};
	
	$scope.renewCategories();
	
});