<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">


<head>
<title>TEST CONTROL MODE</title>
</head>

<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<script
	src="static/js_folder/person_scripts/testController.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">


<body>

	<div id="container" >
		<div id="header">
			<div id="logo"></div>

		</div>

		<div align="center" ng-app="testPage"
		ng-controller="QuestionTestController" ng-init="token='${token}'">

			<camera></camera>
			<div>
				<video id="video" width="320" height="240" autoplay="autoplay"
					ng-show="false"></video>
				<canvas id="canvas" width="320" height="240" class="myImage"
					ng-show="false"></canvas>
			</div>

			<br> <br>
			<div align="center" ng-show="mySwitchStartTest">
				<h1 id="message"></h1>
				<h1>This is your personal test list, don't close this page!!!</h1>
				<input type="button" id="start_test" ng-click="toggleShowDetails()"
					value="Start your test" />
			</div>
			<br>
			<div ng-show="mySwitchShowTest" test-page></div>
			<br> <br> <br>
			<div ng-show="mySwitchEndTest">
				<h1>Your test is ended!!!</h1>
			</div>

		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>
</body>
</html>