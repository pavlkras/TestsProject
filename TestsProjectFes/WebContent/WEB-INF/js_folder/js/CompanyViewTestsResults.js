angular.module('view_results', ['ui.bootstrap']);
angular.module('view_results').controller('mode_selector', function ($http, $scope, $rootScope) {
   $scope.modes = {
    all: 'All tests',
    dates: 'Dates range',
    id: 'Person id'
  };

  $scope.status = {
    isopen: false
  };
  
  $scope.mode = 'Select view mode';
  
  $scope.visible = {
    dates: false,
    id: false
  };

  $scope.disabled = {
    isDisabled_ID: true,
    isDisabled_SubmitButton: true
  };

  $scope.all_switch = function() {
    $scope.disabled.isDisabled_SubmitButton = false;
    $scope.disabled.isDisabled_ID = true;
    $scope.visible.dates = false;
    $scope.visible.id = false;
    $scope.mode = $scope.modes['all'];
  };

  $scope.dates_switch = function() {
    $scope.disabled.isDisabled_SubmitButton = false;
    $scope.disabled.isDisabled_ID = true;
    $scope.visible.dates = true;
    $scope.visible.id = false;
    $scope.mode = $scope.modes['dates'];
  };

  $scope.id_switch = function() {
    $scope.disabled.isDisabled_SubmitButton = false;
    $scope.disabled.isDisabled_ID = false;
    $scope.visible.dates = false;
    $scope.visible.id = true;
    $scope.mode = $scope.modes['id'];
  };

// function firstCtrl($rootScope){
//     $rootScope.$broadcast('someEvent', [1,2,3]);
// }
$scope.show_results_click = function() {
  $http.get("http://www.w3schools.com/website/Customers_JSON.php")
        .success(function(response) {
          $rootScope.names = response;
      });
    };
    console.log($rootScope.names);
});



angular.module('view_results').controller('calendar_selector', function ($scope) {
  $scope.dates = {

  };
});

angular.module('view_results').controller('tests_results', function ($scope, $http, $rootScope) {
  $scope.show_results  = function(){
    
  };
});