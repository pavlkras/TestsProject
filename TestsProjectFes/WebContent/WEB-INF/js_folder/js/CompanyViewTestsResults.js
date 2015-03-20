var app = angular.module('app', ['ngResource']);

//app.factory('restService', function($resource) {
//	  return $resource('/todo/:todoId', { todoId:'@_id' });
//});

app.controller('InputController', ['$scope','$http', function($scope, $http) {
  $scope.selectedMode = -1;

  $scope.display = {
	calendar: false,
	id: false
  };
  
  $scope.isButtonDisabled = true;
  $scope.changed_to_mode = function(i) {
	$scope.selectedMode = i;
	$scope.isButtonDisabled = false;
	
	switch(i){
		case 'all': 
		$scope.display.calendar = false;
		$scope.display.id = false;
		break;
		case 'range':
		$scope.display.calendar = true;
		$scope.display.id = false;
		break;
		case 'id':
		$scope.display.calendar = false;
		$scope.display.id = true;
		break;
	}
  };
  
  $scope.submit = function(){
	console.log($scope.selectedMode);
	$http.get("http://localhost:8080/TestsProjectBes/view_results_rest/all_tests_results/Comp1").success(function (response) {
		$scope.results = response;
	});
  };
  
}]);

$(function () {
		$('#datetimepicker1').datetimepicker();
		$('#datetimepicker2').datetimepicker();
		$("#datetimepicker1").on("dp.change",function (e) {
		$('#datetimepicker2').data("DateTimePicker").minDate(e.date);
	});
		$("#datetimepicker2").on("dp.change",function (e) {
		$('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
	});
});

//$(function () {
//		$('#datetimepicker1').datetimepicker();
//		$('#datetimepicker2').datetimepicker();
//		$("#datetimepicker1").on("dp.change",function (e) {
//		$('#datetimepicker2').data("DateTimePicker").minDate(e.date);
//	});
//		$("#datetimepicker2").on("dp.change",function (e) {
//		$('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
//	});
//});