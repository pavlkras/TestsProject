<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.AbstractAdminActions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1251">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/company_styles/company.css"></c:url>'
	rel="stylesheet">
	
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="https://code.angularjs.org/1.4.7/angular.min.js"></script>	
<script src="static/js_folder/maintenance_scripts/update_question_page.js"></script>
<script src="/TestsProjectFes/static/js_folder/header&&rightmenu.js"></script>
<title>Question management</title>
</head>
<body onload="actionTypeChange()">
	<div id="container">


		<div id="right_side">
		<div id="main_info">
		
			<form name="company_questions_update">
				<h3>View questions</h3>
				<div>
					<select name="view_mode">
						<option value="user">Your questions</option>
						<option value="auto">Generated questions</option>
						<option value="all">All questions</option>
					</select>					
				</div>
				<input type="submit" value="show">
			</form>
		

			<!--  <div>
				<a class="myButton" href='.'>home</a>&nbsp;<a class="myButton"
					href='maintenanceadd'>add</a>
				<form name="delete_q" class="displayAction" action='deleteAction'
					method="post">
					Question N: <input type='text' name='questionIDdelete' size="8">&nbsp;&nbsp;
					<input type="submit" class="myButton" value='Delete ?'>
				</form>
				<form name="edit_q" class="displayAction"
					action="fillFormForUpdateQuestion" method="post">
					Question N: <input type="text" name="questionID" size="8">&nbsp;&nbsp;
					<input type="submit" class="myButton" value="Edit ?">
				</form>
				
				
				<form name="searchCODE" action="search_actions" method="post">
					<select name="category">
						<option value="Java">Select Category For Search</option>
						${categoryList}
					</select> <br>
					<br> <select name="levelOfDifficulty">
						<option value=1>level Of Difficulty</option>
						<option value=1>level 1</option>
						<option value=2>level 2</option>
						<option value=3>level 3</option>
						<option value=4>level 4</option>
						<option value=5>level 5</option>
					</select> <input class="myButton" type="submit" value="SEARCH"><br>
				</form>
				<br>
				<br>

				${categoryList}

			</div> -->
			
			
			<form name="questionsList" action="question_see" method="get">
			<!--  LIST OF QUESTIONS --> 
			<div ng-app="test_app" ng-controller="test_contr">
			
				<table>
					<tr class="tableHead">
						<th>id</th>
						<th>Type</th>
						<th>Category</th>
						<th>Level</th>
						<th>Text</th>
						<!--   <th>See question</th> -->
						
											</tr>
					<tr class="tableContent" ng-repeat="q in results">					
					 	<td class="border" style = "width: 10%">{{q.id}}</td>
					 	<td class="border" style = "width: 15%">{{q.metaCategory}}</td>
					 	<td class="border" style = "width: 15%">{{q.category1}}</td>
					 	<td class="border" style = "width: 10%">{{q.level}}</td>
					 	<td class="border">{{q.shortText}}</td>
					 	<td style = "width:10%"><input type="radio" name="type" value={{q.id}}/> See <br></td>
					</tr>
				</table>	
				
				<input type="submit"/>		
				
			</div> 
			</form>
			
			<!--end of our description area-->
			<div id="additional_area"></div>
		</div>
		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>	
	
	<script >
	var roleNumber = ${role};	
	getMenu(roleNumber);
	
	var appl=angular.module('test_app',[]);
	appl.controller('test_contr',function($scope){
		var json=JSON.parse('${result}');
		
		$scope.results=json.results;
	});
	</script>	
	
</body>


</html>
