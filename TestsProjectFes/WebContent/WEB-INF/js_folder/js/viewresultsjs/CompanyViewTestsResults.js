var app = angular.module('app', ['smart-table','mgcrea.ngStrap','ngDialog']);

app.controller('InputController', ['$scope','$http', 'ngDialog', function($scope, $http, ngDialog) {
	
  $scope.selectedMode = -1;

  $scope.display = {
	calendar: false,
	id: false,
	test_details: true,
	pictures: false,
	code: false
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
  
  $scope.showTestDetails = function(arg){
	  switch(arg){
		case 'details': 
			$scope.display.test_details = true;
			$scope.display.pictures = false;
			$scope.display.code = false;
		break;
		case 'camera_snapshots':
			$scope.display.test_details = false;
			$scope.display.pictures = true;
			$scope.display.code = false;
		break;
		case 'code':
			$scope.display.test_details = false;
			$scope.display.pictures = false;
			$scope.display.code = true;
		break;
		default:
			$scope.display.test_details = true;
			$scope.display.pictures = false;
			$scope.display.code = false;
	}
  };
  
  
  
  $scope.parameters = function(){
		var params = '';
		switch($scope.selectedMode){
			case 'all': 
				params = '';
			break;
			case 'range':
				params = "/"+$scope.formatDate($scope.dateFrom)+"/"+$scope.formatDate($scope.dateTo);
			break;
			case 'id':
				params = "/"+$scope.personID;
			break;
		}
	  return params;
	};
	
	$scope.formatDate = function(date_){
		res="e";
		if(date_!=undefined && date_!=""){
			res = date_.getFullYear()+"-"+eval(date_.getMonth()+1)+"-"+date_.getDate();
		}
		date_="";
		return res;
	};
	
	//Test Details
	$scope.getDetails = function(testid_){
		$scope.link = "/TestsProjectBes/view_results_rest/test_details"+"/"+testid_;
		$http.get($scope.link, $scope.httpConfig).success(function (response) {
			$scope.testDetails = response;
			$scope.showTestDetails();
		});
	};
	
	$scope.getProcessingResultStyle = function(arg_){
	//	console.log('got arg = '+arg_);
		var res = '';
		if(arg_ == "true"){
			res = "label-success";
		} else if(arg_ == "false"){
			res = "label-danger";
		};
		return res;
	};
		  
	$scope.showDetails = function(testid){
		$scope.getDetails(testid);
	//	console.log(testid);

		ngDialog.open({ 
    	  controller: 'InputController',
    	  className: 'ngdialog-theme-default',
    	  template: 'testDetails',
    	  scope: $scope
		});
	};
	  
	$scope.showCode = function(){
		ngDialog.open({ 
    	  controller: 'InputController',
    	  className: 'ngdialog-theme-default',
    	  template: 'code',
    	  scope: $scope
		});
	};
	
	
// Test Common Results	
	$scope.submit = function(){
		$scope.link = "/TestsProjectBes/view_results_rest"+$scope.modePath+$scope.parameters();
		$scope.httpConfig = {headers: {
	        'Authorization': $scope.token,
	        'TimeZone': new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]
	    }};
//		console.log($scope.link);
		$http.get($scope.link, $scope.httpConfig).success(function (response) {
			$scope.results = response;
			if(response.Error){
				$scope.results = "";
				alert(response.Error);
			}
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

app.directive('nagPrism', [function() {
    return {
        restrict: 'A',
        scope: {
            source: '@'
        },
        link: function(scope, element, attrs) {
            scope.$watch('source', function(v) {
                if(v) {
                    Prism.highlightElement(element.find("code")[0]);
                }
            });
        },
        template: "<code ng-bind='source'></code>"
    };

}]);