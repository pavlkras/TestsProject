<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" ng-app="app" ng-controller="QuestionTestController"
	ng-init="token='${token}'">
<head>
<title>TEST CONTROL MODE</title>
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/person_styles/WebCamStyle.css"></c:url>'
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="static/js_folder/person_scripts/test_control_mode.js"></script>
<script src="static/js_folder/person_scripts/angular_controller.js"></script>
</head>
<body>
	<h1>This your personal test list, don't close this page!!!</h1>
	<div class="item">
		<video id="video" width="320" height="240" autoplay="autoplay"></video>
	</div>
	<div class="item">
		<canvas id="canvas" width="320" height="240"></canvas>
	</div>
	<h1 id="message"></h1>
	<input type="button" id="start_test" class="buttons"
		value="Start your test" />
	<br>

	<script type="text/ng-template" id="testAmerikanQuestionDetails">



</script>


	<script type="text/ng-template" id="testCodeQuestionDetails">


</script>

</body>
</html>