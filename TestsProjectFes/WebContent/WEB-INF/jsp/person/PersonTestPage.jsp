<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>TEST CONTROL MODE</title>
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="/TestsProjectFes/static/css_folder/person_styles/WebCamStyle.css">
<script src="/TestsProjectFes/static/js_folder/person_scripts/testController.js"></script>
</head>
<body ng-app="testPage" ng-controller="QuestionTestController" ng-init="token='${token}'"> 

	<camera></camera>
	<div>
    	<video id="video" width="320" height="240" autoplay="autoplay" ng-show="false"></video>
    	<canvas id="canvas" width="320" height="240" class="myImage" ng-show="false"></canvas>
	</div>
	
	<br><br>
	<div  ng-show="mySwitchStartTest">
		<h1 id="message"></h1>
    	<h1>This is your personal test list, don't close this page!!!</h1>
    	<input type="button" id="start_test" ng-click="toggleShowDetails()" value="Start your test" />
	</div>
	<br>
	<div ng-show="mySwitchShowTest" test-page></div>
	<br><br><br>
	<div ng-show="mySwitchEndTest">
    	<h1>Your test is ended!!!</h1>
	</div>

</body>
</html>
