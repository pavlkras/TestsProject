/**
 *  Test control mode test controller
 */

var app = angular.module("testPage", []);

app.controller("QuestionTestController", function($scope, $http) {
	$scope.mySwitchStartTest = false;
	$scope.mySwitchShowTest = false;
	$scope.mySwitchEndTest = false;
	$scope.mySwitchAmericanSystemTestQuestion = false;
	$scope.mySwitchCodeTestQuestion = false;
	$scope.mySwitchImage = false;
	$scope.switchTextCamera = true;

	$scope.toggleShowDetails = function() {
		$scope.mySwitchStartTest = !$scope.mySwitchStartTest;
		$scope.getQuestion(null);
	};

	$scope.getQuestion = function(userAnswer) {
		$scope.take_photo();
		var link = "/TestsProjectBes/persontest/saveprev_getnext";
		$scope.httpConfig = {
			headers : {
				'Authorization' : $scope.token
			}
		};
		var dataObj = {
			answer : userAnswer,
			index: $scope.numberQuestion
		};

		$http.post(link, dataObj, $scope.httpConfig).success(
				function(data, status, headers, config) {
					console.log("Success - request result to Rest");
					$scope.question = data;

					
//					if ($scope.question == null) {
//						console.log("data from rest (question) is null");
//						$scope.mySwitchEndTest = !$scope.mySwitchEndTest;
//						$scope.mySwitchShowTest = !$scope.mySwitchShowTest;
//					
//					} else {
						//End of Test
						if($scope.question.isPassed){
							console.log($scope.question.error);
							$scope.mySwitchShowTest = false;
							$scope.mySwitchEndTest = true;
						}else{
							$scope.mySwitchShowTest = true;
							
							$scope.numberQuestion = $scope.question.index;
							$scope.text = $scope.question.text;
							$scope.image = $scope.question.image;
							if ($scope.image != null) {
								$scope.mySwitchImage = true;
							} else {
								$scope.mySwitchImage = false;
							}
	
							switch ($scope.question.type) {
							//Type of question - AmericanSystemQuestion
								case 1: {
									console.log("case 1");
									$scope.answers = $scope.question.answers;
	//								$scope.nAnswers = $scope.getAnswers($scope.numberAnswers);
									$scope.mySwitchAmericanSystemTestQuestion = true;
									$scope.mySwitchCodeTestQuestion = false;
									break;
								}
									//Type of question - CodeQuestion
								case 2: {
									console.log("case 2");
									$scope.mySwitchAmericanSystemTestQuestion = false;
									$scope.mySwitchCodeTestQuestion = true;
									break;
								}
							}
							
							data = null;
							userAnswer = null;
							$scope.answer = {
								name : null
							};
						}
				//	}

				}).error(function(data, status, headers, config) {
			console.log("Error - request result to Rest");
		});
	};

//	$scope.getAnswers = function(number) { //filling array of answers
//		array = [];
//		for (i = 0; i < number; i++) {
//			array.push(i + 1);
//		}
//		return array;
//	};

});

app.directive('testPage', function() {
	return {
			replace : false,
			scope : false,
			templateUrl : "/TestsProjectFes/static/js_folder/person_scripts/testQuestionPage.html",
			link : function foo(scope, element, attrs) {}
	};
});

//-------------------------------------------------Camera
app.factory('CameraService', function($window) {
        var hasUserMedia = function() {
            return !!getUserMedia();
        }

        var getUserMedia = function() {
            navigator.getUserMedia = ($window.navigator.getUserMedia ||
            $window.navigator.webkitGetUserMedia ||
            $window.navigator.mozGetUserMedia ||
            $window.navigator.msGetUserMedia);
            return navigator.getUserMedia;
        }

        return {
            hasUserMedia: hasUserMedia(),
            getUserMedia: getUserMedia
        }
    })
//------------------------------------------------------------------------------------------------

app.directive('camera', function(CameraService) {
        return {
            restrict: 'EA',
            replace: true,
            transclude: true,
          //  scope: {},
            scope: false,
        //    template: '<div class="camera"><video class="camera" autoplay="" /><div ng-transclude></div></div>',
            template: '<h1 id="message" ng-show="switchTextCamera">For testing need camera. Click "allow" in pop-up window for using your camera</h1>',
            link: function(scope, ele, attrs, QuestionTestController) {
                var w = attrs.width || 320,
                    h = attrs.height || 200;

                if (CameraService.hasUserMedia) {
                    var videoElement = document.querySelector('video');
// If camera works
                    var onSuccess = function (stream) {
                        if (navigator.mozGetUserMedia) {                         //if Mozilla
                            console.log("1");
                            videoElement.mozSrcObject = stream;
                        } else {
                            console.log("2");
                            var vendorURL = window.URL || window.webkitURL;
                            videoElement.src = window.URL.createObjectURL(stream);
                        }
                        // Just to make sure it autoplays
                        videoElement.play();
                        
                        scope.mySwitchStartTest = true;
                        scope.take_photo();
                        scope.switchTextCamera = false;
                    }
// If camera doesn't work of if camera block
                    var onFailure = function (err) {
                    	document.getElementById("message").innerHTML = '<h1>Your camera does not work, testing is impossible. Try to unblock camera in browser and to reload this page. </h1>';
                        console.log("Error !");
                        console.error(err);
                    }
// Make the request for the media
                    navigator.getUserMedia({
                        video: {
                            mandatory: {
                                maxHeight: h,
                                maxWidth: w
                            }
                        },
                        audio: false
                    }, onSuccess, onFailure);

                    scope.w = w;
                    scope.h = h;
                }
                else {
                    console.log("Problem with camera: browser doesn't support camera or camera was blocked");
                    return;
                }
            },

            controller: function($scope, $q, $timeout, $http) {
                var canvas = document.querySelector('canvas');
                var context = canvas.getContext('2d');
                $scope.take_photo = function () {
                    console.log("in function Take photo");
                    var canvas = document.querySelector('canvas');
                    var context = canvas.getContext('2d');
                    var video = document.querySelector('video');
                    context.fillRect(0, 0, $scope.w, $scope.h);
                    context.drawImage(video, 0, 0, $scope.w, $scope.h);
                    var base64dataUrl = canvas.toDataURL('image/png');
                    console.log("base64dataUrl = " + base64dataUrl);
                    //var picture = base64dataUrl + "@END_LINE@";
                    
                  //function save_image
            		var link = "/TestsProjectBes/persontest/save_image";
            		$scope.httpConfig = {
            			headers : {
            				'Authorization' : $scope.token
            			}
            		};
            		var dataObj = {
            			image : base64dataUrl
            			//number question
            		};

            		$http.post(link, dataObj, $scope.httpConfig).success(
            				function(data, status, headers, config) {
            					console.log("IMAGE - Success - request result to Rest");
            				}).error(function(data, status, headers, config) {
            			console.log("IMAGE - Error - request result to Rest");
            		});
                }
            }
        }
    });
