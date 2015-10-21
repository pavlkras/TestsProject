<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Main</title>

<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/company_styles/company.css"></c:url>'
	rel="stylesheet">	

<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="https://code.angularjs.org/1.4.7/angular.min.js"></script>
<script src="static/js_folder/header&&rightmenu.js"></script>	


</head>
<body>
		<div id="container">

		<div id="right_side">

			<h2>Success Company Login</h2>
			<div ng-app="lgn_app" ng-controller="lgn_contr" class="inform">
				
					<h3>{{company}}</h3>
					<div class="site">{{web}}</div>
					<div class="us_info">Questions in DB: {{num_question}}<br/>
					Sended tests: {{num_tests}}</p></div>
				
			
				
			</div>

		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>

<script>	
	
	var roleNumber = ${role};		
	getMenu(roleNumber);
	
	var appl=angular.module('lgn_app',[]);	
	appl.controller('lgn_contr',function($scope){
		var json=JSON.parse('${account}');
		$scope.company = json.company;	
		$scope.web = json.web;
		$scope.num_question = json.question_num;
		$scope.num_tests = json.tests_num;
	}); 

	
	</script>
	
</body>


</html>