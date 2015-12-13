var app = angular.module('testcreation_app', ['ngDialog']);

app.controller('testcreation_controller', function($scope, $http, ngDialog){

    $scope.templates = [{
        template_id : 0,
        template_name : "Programmer"
    }, {
        template_id : 1,
        template_name : "Administrator"
    }, {
        template_id : 2,
        template_name : "Tester"
    }];

    $scope.template = null;

    $scope.personDataList = [];
    $scope.personListShow = false;

    $scope.data = null;
    $scope.index = -1;

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
        result.persons = $scope.personDataList;

        var answer = angular.toJson(result, false);

        alert(answer);

        var message = "Test was generated and sended to persons";
        $scope.showResult(message);
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
            $scope.email = data.person_email;
            $scope.name = data.person_name;
            $scope.lastname = data.person_lastname;
            $scope.passport = data.person_passport;
            $scope.$parent.data = null;
            $scope.$parent.dataIndex = null;
        }
    }

    $scope.fillData();


    $scope.personSave = function() {
        var data = {};
        data.person_email = $scope.email;
        if($scope.name!=null) {
            data.person_name = $scope.name;
        }
        if($scope.lastname!=null) {
            data.person_lastname = $scope.lastname;
        }
        if($scope.passport!=null) {
            data.person_passport = $scope.passport;
        }

        if($scope.edit) {
            $scope.$parent.editPersonData(data, $scope.index);
        } else {
            $scope.$parent.addPersonData(data);
        }
        $scope.closeThisDialog();
    }

});

