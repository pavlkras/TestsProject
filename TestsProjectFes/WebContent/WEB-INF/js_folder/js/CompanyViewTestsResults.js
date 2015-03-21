var app = angular.module('app', ['ngResource']);

app.controller('InputController', ['$scope','$http', function($scope, $http) {
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
  
  	$scope.parameters = function(){
		var params = '';
		switch($scope.selectedMode){
			case 'all': 
				params = '';
			break;
			case 'range':
				params = "/"+"date1"+"/"+"date2";
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
  
}]);
// jQuery for calendar picker
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