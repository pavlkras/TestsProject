/**
 *  Test control mode test controller
 */

var app = angular.module("testPage", []);

app.controller("QuestionTestController",function($scope, $http) {
    $scope.mySwitchStartTest = true;
    $scope.mySwitchShowTest = false;
    $scope.mySwitchEndTest = false;
    $scope.mySwitchAmericanSystemTestQuestion = false;
    $scope.mySwitchCodeTestQuestion = false;
    $scope.mySwitchImage = false;

    $scope.toggleShowDetails = function(){
        $scope.mySwitchStartTest = !$scope.mySwitchStartTest;
        $scope.getQuestion(null);  
    }

    $scope.getQuestion = function(userAnswer){
        var link = "/TestsProjectBes/persontest/saveprev_getnext";
        $scope.httpConfig = {
             headers: {
                  'Authorization': $scope.token
             }
        };
        var dataObj = {
            answer: userAnswer
            //index: nQuestion  //?
        };
        
        $http.post(link, dataObj, $scope.httpConfig).success(function (data, status, headers, config) {
            	console.log("Success - request result to Rest");
                $scope.question = data;
                if ($scope.question == null) {
                	console.log("data from rest (question) is null");
                    $scope.mySwitchEndTest = !$scope.mySwitchEndTest;
                    $scope.mySwitchShowTest = !$scope.mySwitchShowTest;
                }
                else {
                    $scope.mySwitchShowTest = true;
                    $scope.numberQuestion = $scope.question.index;
                    $scope.text = $scope.question.question_text;
                    $scope.image = $scope.question.question_image; 
                    if($scope.image != null){
                    	$scope.mySwitchImage = true;
                    }
                    else{
                    	$scope.mySwitchImage = false;
                    }
                    $scope.type_question = $scope.question.typeQuestion;                                                    
                 
                    if($scope.type_question == 1) {                                  //Type of question - AmericanSystemQuestion
                        $scope.numberAnswers = $scope.question.n_answers;
                        $scope.nAnswers = $scope.getAnswers($scope.numberAnswers);
                        $scope.mySwitchAmericanSystemTestQuestion = true;
                        $scope.mySwitchCodeTestQuestion = false;
                    }
                    if($scope.type_question == 2){                                   //Type of question - CodeQuestion
                        $scope.mySwitchAmericanSystemTestQuestion = false;
                        $scope.mySwitchCodeTestQuestion = true;
                    }
                    if($scope.type_question == 0){                                   //End Test
                        $scope.mySwitchShowTest = false;
                        $scope.mySwitchEndTest = true;
                    }
                    
                    data = null;
                    userAnswer = null;
                    $scope.answer = {
                        name: null
                    }
                }
                
        }).error(function (data, status, headers, config){
        	console.log("Error - request result to Rest");       
        });
    };


    $scope.getAnswers = function(number) {         //filling array of answers
    	array = [];
        for(i=0; i<number; i++){
        	array.push(i + 1);
        }
        return array;
    };
    
});

app.directive('testPage', function() {
    return {
        replace: false,
        scope: false,

        templateUrl: "/TestsProjectFes/static/js_folder/person_scripts/testQuestionPage.html",

        link: function foo(scope, element, attrs){}
    }
});

