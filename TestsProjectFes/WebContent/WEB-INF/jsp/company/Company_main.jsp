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
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>	
	<script src="static/js_folder/header&&rightmenu_company.js">
</script>	
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

</head>
<body>
		<div id="container">

		<div id="right_side_company">

			<h2>Success CompanyName Login</h2>
			<div ng-app="lgn_app" ng-controller="lgn_contr" class="inform">
				
					<h4>{{name}}</h4>
					<div class="site"><p>{{webs}}</p></div>
					<div class="us_info"><p>Questions in DB: {{num_question}}</br></p>
					<p>Sended tests: {{num_tests}}</p></div>
				
			
				
			</div>

		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>

<script>	
	var appl=angular.module('lgn_app',[]);
	appl.controller('lgn_contr',function($scope){
		var json=JSON.parse('${info}');
		$scope.name = json.name;	
		$scope.webs = json.website;
		$scope.num_question = json.question_num;
		$scope.num_tests = json.tests_num;
	}); </script>
	
</body>


</html>