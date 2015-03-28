angular.module('app', ['smart-table','mgcrea.ngStrap'])

.controller('InputController', ['$scope','$http', function($scope, $http) {
  $scope.selectedMode = -1;

  $scope.display = {
	calendar: false,
	id: false
  };
  
  $scope.showDetails = function(testid){
	  console.log(testid);
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
	
//	$scope.setToken = function(token){
//		$scope.token = token;
//	};
	
	
  $scope.submit = function(){
	$scope.link = "/TestsProjectBes/view_results_rest"+$scope.modePath+$scope.parameters()+"/"+$scope.token;
	console.log($scope.link);
	$http.get($scope.link).success(function (response) {
		$scope.results = response;
	});
  };
  
}])

.directive('pageSelect', function() {
	  return {
	    restrict: 'E',
	    template: '<input type="text" class="select-page" ng-model="inputPage" ng-change="selectPage(inputPage)">',
	    link: function(scope, element, attrs) {
	      scope.$watch('currentPage', function(c) {
	        scope.inputPage = c;
	      });
	    }
	  }
	})
	.directive('stRatio',function(){
	    return {
	        link:function(scope, element, attr){
	          var ratio=+(attr.stRatio);
	          element.css('width',ratio+'%');	          
	        }
	     };
	 });

// jQuery for calendar picker
//$(function () {
//		$('#datetimepicker1').datetimepicker({
//			format: 'DD-MM-YYYY'
//		});
//		$('#datetimepicker2').datetimepicker({
//			format: 'DD-MM-YYYY'
//		});
//		$("#datetimepicker1").on("dp.change",function (e) {
//		$('#datetimepicker2').data("DateTimePicker").minDate(e.date);
//	});
//		$("#datetimepicker2").on("dp.change",function (e) {
//		$('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
//	});
//});
