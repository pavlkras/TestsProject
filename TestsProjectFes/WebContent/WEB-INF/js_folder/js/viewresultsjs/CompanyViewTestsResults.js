var app = angular.module('app', ['smart-table','mgcrea.ngStrap','ngDialog'])

app.controller('InputController', ['$scope','$http', 'ngDialog', function($scope, $http, ngDialog) {
  $scope.selectedMode = -1;

  $scope.display = {
	calendar: false,
	id: false
  };
  
  $scope.isButtonDisabled = true;
  $scope.changed_to_mode = function(i, modePath) {
	$scope.selectedMode = i;
	$scope.isButtonDisabled = false;
	$scope.modePath = modePath;
	switch(i){
		case 'all': 
		$scope.display.calendar = false;
		$scope.display.id = false;
		$scope.calendar = false;
		$scope.personID = false;
		break;
		case 'range':
		$scope.display.calendar = true;
		$scope.display.id = false;
		$scope.calendar = true;
		$scope.personID = false;
		break;
		case 'id':
		$scope.display.calendar = false;
		$scope.display.id = true;
		$scope.calendar = false;
		$scope.personID = true;
		break;
	}
  };
  
  	$scope.parameters = function(){
		var params = '';
		switch($scope.selectedMode){
			case 'all': 
				params = '';
			break;
			case 'range':
				params = "/"+$scope.dateFrom+"/"+$scope.dateTo;
			break;
			case 'id':
				params = "/"+$scope.personID;
			break;
		}
	  return params;
	};
	
	//Test Details
	$scope.getDetails = function(testid_){
		$scope.link = "/TestsProjectBes/view_results_rest/test_details"+"/"+testid_+"/"+$scope.token;
		console.log($scope.link);
		$http.get($scope.link).success(function (response) {
			$scope.testDetails = angular.fromJson(response);
		});
	};
		  
	$scope.showDetails = function(testid){
		$scope.getDetails(testid);
		console.log(testid);

		ngDialog.open({ 
    	  controller: 'InputController',
    	  className: 'ngdialog-theme-default',
    	  template: 'testDetails',
    	  scope: $scope
		});
	};
	
// Test Common Results	
  $scope.submit = function(){
	$scope.link = "/TestsProjectBes/view_results_rest"+$scope.modePath+$scope.parameters()+"/"+$scope.token;
	console.log($scope.link);
	$http.get($scope.link).success(function (response) {
		$scope.results = response;
	});
  };
  
}]);

app.directive('pageSelect', function() {
	  return {
	    restrict: 'E',
	    template: '<input type="text" class="select-page" ng-model="inputPage" ng-change="selectPage(inputPage)">',
	    link: function(scope, element, attrs) {
	      scope.$watch('currentPage', function(c) {
	        scope.inputPage = c;
	      });
	    }
	 };
});

app.directive('stRatio',function(){
    return {
    	link:function(scope, element, attr){
    		var ratio=+(attr.stRatio);
    		element.css('width',ratio+'%');	          
    	}
     };
 });