var app = angular.module('testcreation_app', ['ngDialog']);

app.controller('testcreation_controller', function($scope, $http, ngDialog){

	$scope.urlBes = "/TestsProjectBes/tests";
	$scope.urlTemplates = $scope.urlBes + "/listTemplates";
	$scope.urlTestCreation = $scope.urlBes + "/createTestByTemplate";
	
    $scope.templates = [];
    $scope.token = token;
    $scope.link = link;
    $scope.template = null;

    $scope.personDataList = [];
    $scope.personListShow = false;

    $scope.data = null;
    $scope.index = -1;
    
    $scope.renewData = function() {    	
    	
    	$http({
			method: 'GET',
			url: $scope.urlTemplates,
			headers: {
				'Authorization' : $scope.token
			}
		}).then(function(response) {
			
	    	$scope.templates = response.data;
	    	
	    	
	    });
    }
    
    $scope.renewData();

    $scope.addOnePerson = function() {
        ngDialog.open({
            template: 'addPerson',
            controller: 'person_controller',
            scope: $scope
        });
    }

    $scope.addPersonData = function(data) {
        $scope.personDataList.push(data);
        $scope.personListShow = true;
    }

    $scope.deletePerson = function (index) {

        $scope.personDataList.splice(index, 1);
        if($scope.personDataList.length==0) $scope.personListShow = false;
    }

    $scope.editPersonData = function(data, index) {
        $scope.personDataList[index] = data;
    }

    $scope.edit = function (index) {
        $scope.data = $scope.personDataList[index];
        $scope.dataIndex = index;
        ngDialog.open({
            template: 'addPerson',
            controller: 'person_controller',
            scope: $scope
        });

    }

    $scope.clearTest = function() {
        $scope.template = null;
        $scope.personDataList = [];
        $scope.personListShow = false;
    }
    
    $scope.saveTest = function() {
        var result = {};
        result.template_id = $scope.template;
        result.test_link = $scope.link;
        result.test_persons = $scope.personDataList;

        var answer = angular.toJson(result, false);
        
        $http({
    		method: 'POST',
    		url: $scope.urlTestCreation,
    		data: answer,
    		headers: {
    			'Authorization' : $scope.token
    		}
    	}).then(function(response) {
    		        	
        	var message = {};
        	if(response.data.code != undefined) {
        		alert(response.data.code);
        		message.text = response.data.response;
        	} else {
        		
        		message.text = "Test(s) was(were) generated and sent to persons";
        		message.data = response.data;
        		$scope.showResult(message);
        	}
        	alert(4);
        	
        });

       
        
        $scope.clearTest();
    }

    $scope.showResult = function(message) {
        $scope.message = message;

        ngDialog.open({
            template: 'resultMessage',
            className: 'ngdialog-theme-default',
            scope: $scope
        });
    }
});

app.controller('person_controller', function($scope){

    $scope.edit = false;
    $scope.index;
    $scope.title = "Add person";


    $scope.fillData = function() {

        if($scope.$parent.data!=null) {
            $scope.title = "Edit person data"
            $scope.edit = true;
            $scope.index = $scope.$parent.dataIndex;
            var data = $scope.$parent.data;
            $scope.email = data.per_mail;
            $scope.name = data.per_fname;
            $scope.lastname = data.per_lname;
            $scope.passport = data.per_passport;
            $scope.$parent.data = null;
            $scope.$parent.dataIndex = null;
        }
    }

    $scope.fillData();


    $scope.personSave = function() {
        var data = {};
        data.per_mail = $scope.email;
        if($scope.name!=null) {
            data.per_fname = $scope.name;
        }
        if($scope.lastname!=null) {
            data.per_lname = $scope.lastname;
        }
        if($scope.passport!=null) {
            data.per_passport = $scope.passport;
        }

        if($scope.edit) {
            $scope.$parent.editPersonData(data, $scope.index);
        } else {
            $scope.$parent.addPersonData(data);
        }
        $scope.closeThisDialog();
    }

});

