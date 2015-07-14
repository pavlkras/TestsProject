/**
 *  Test control mode angular controller
 */

/*var app = angular.module('app', ['smart-table','mgcrea.ngStrap','ngDialog']);

app.controller('QuestionTestController', ['$scope','$http', 'ngDialog', function($scope, $http, ngDialog) {
	
	
}]); */

var app = angular.module("testPage", []);

app.controller("QuestionTestController",function($scope, $http) {
    $scope.mySwitchStartTest = true;
    $scope.mySwitchShowTest = false;
    $scope.mySwitchEndTest = false;
    $scope.mySwitchAmericanSystemTestQuestion = false;
    $scope.mySwitchCodeTestQuestion = false;

    $scope.toggleShowDetails = function(){
        $scope.mySwitchStartTest = !$scope.mySwitchStartTest;
        $scope.getQuestion(0);  //+ send keyTest to BES
    }

    // Request ----------------------------------------------------------------
    $scope.getQuestion = function(userAnswer){
        console.log("in getQuestion answer = " + userAnswer);
            var link = "/TestsProjectBes/personTest/saveprev_getnext";
          /*  $scope.httpConfig = {
                headers: {
                    'Authorization': $scope.token
                }
            };*/
        var dataObj = {
            answer: userAnswer
            //index: nQuestion  //?
        };
        
            //$http.post(link, dataObj, $scope.httpConfig).success(function (response) {
        $http.post(link, dataObj).success(function(data, status, headers, config) {
                $scope.question = data;
                if ($scope.question == null) {
                    $scope.mySwitchEndTest = !$scope.mySwitchEndTest;
                    $scope.mySwitchShowTest = !$scope.mySwitchShowTest;
                }
                else {
                    $scope.mySwitchShowTest = true;
                    //$scope.question = response;
                    $scope.numberQuestion = $scope.question[0].index;
                    $scope.text = $scope.question[0].question_text;
                    $scope.image = $scope.question[0].question_image;            //if > 30 ->question with image (not codeAnswer)
                                                                                 //decoding!
                    $scope.numberAnswers = $scope.question[0].n_answers;
                    data = null;
                }
        }).error(function (data, status, headers, config){
            $scope.mySwitchShowTest = true;

            $http.get("question1.txt").success(function(response){
                $scope.question = response;
                $scope.numberQuestion = $scope.question[0].index;
                $scope.text = $scope.question[0].question_text;
                $scope.image = $scope.question[0].question_image;
                $scope.type_question = $scope.question[0].typeQuestion;
                //console.log("$scope.typeQuestion = " + $scope.type_question);
                if($scope.type_question == 1) {                       //AmericanSystemQuestion
                    $scope.numberAnswers = response[0].n_answers;
                    $scope.nAnswers = [];
                    $scope.addToArray();
                    $scope.mySwitchAmericanSystemTestQuestion = true;
                    $scope.mySwitchCodeTestQuestion = false;
                }
                if($scope.type_question == 2){                        //CodeQuestion
                    //hidden template for AmericanSystemQuestion
                    $scope.mySwitchAmericanSystemTestQuestion = false;
                    $scope.mySwitchCodeTestQuestion = true;
                }
                console.log("$scope.numberAnswers " + $scope.numberAnswers);
                response = null;
                userAnswer = null;
                $scope.answer = {
                    name: null
                }


            });
            //console.log("in cntrl" + $scope);
            //console.log("in Error response e: " + $scope.numberAnswers);
        });
    };

    $scope.addToArray = function() {
        for(i=0; i<$scope.numberAnswers; i++){
            $scope.nAnswers.push(i + 1);
            //console.log("array " + $scope.nAnswers[i]);
        }
    };
});

app.directive('testPage', function() {
    return {

        replace: false,
        scope: false,

        templateUrl: /TestsProjectFes/static/js_folder/person_scripts/testQuestionPage.html,

        link: function foo(scope, element, attrs){
            console.log("in directive " + scope.nAnswers);
        }
    }
});

