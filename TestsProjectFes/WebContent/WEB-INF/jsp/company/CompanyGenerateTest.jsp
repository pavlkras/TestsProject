<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html">
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="static/js_folder/header&&rightmenu_company.js"></script>	
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/company_styles/company.css"></c:url>'
	rel="stylesheet">

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<title>Test generation</title>

<style type="text/css">
.categoryCheckBox {
	float: left;
}

.inputFormBox {
	float: left;
	margin-left: 2em;
}

.otherButtonsOnPage {
	clear: left;
	margin-top: 4em;
}
</style>
</head>

	<script type="text/javascript">
	$(document)
			.ready(				
					function() {
						$("tr")
								.on(
										"click",
										function() {
																						
											var ind = $(this).index();
											$(".category")
													.each(
															function(i) {
																if (this.checked) {
																	
																	document
																			.getElementsByName("level_num")[i].disabled = false;
																} else {
																	
																	document
																			.getElementsByName("level_num")[i].disabled = true;
																}
															});
										});
					}	
					);
	function check(login) {
		if (login.length != 9) {
			document.getElementById("e_login").style.display = "inline";
			$("#generate_test").html(
					'<input type="submit" value="Generate test" disabled/>');
		} else {
			document.getElementById("e_login").style.display = "none";
			$("#generate_test").html(
					'<input type="submit" value="Generate test"/>');
		}
	}
	</script>


<body>
	<div id="container">
		<div id="right_side_company">
		
			<div>
				<form action="add_test" id="adding_form_company" width="100%">
				
				<table width="100%">
					<tbody>
						<tr>
							<td width="70%">
								<h4 class="tableTitle">Random questions</h4>
								<div class="categoryCheckBox">
									<script type="text/javascript">
										document.write("${categoryFill}");
									</script>								
								</div>
							</td>
							<td>
								<h4 class="tableTitle">Total number of questions</h4>
								Select Question Count:<br> 
								<select name="selectCountQuestions">
									<option value="5">5</option>
									<option value="10">10</option>
									<!-- for deleting !!! -->
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="50">50</option>														
								</select>
							</td>
						</tr>
						<tr>
							<td colSpan=2>
								<h4 class="tableTitle">Manual selection</h4>
														
								<input id="userQuest" class='userQuestions' type="button" name='userQuestions' onclick="showUserQuestions()"
								value="show questions list"/>
								<input id="hideQuest" class='noQuestions' type="button" name='noQuestions' onclick="hideUserQuestions()"
								value="hide questions list" style="display:none"/>		
										
								<div id="showManualSelection" style="display:none">
									<div ng-app="user_quest_app" ng-controller="user_quest_contr">
			
										<table>
											<tr class="tableHead">
												<th style = "width: 50px">id</th>
												<th>Type</th>
												<th>Category</th>
												<th>Level</th>
												<th>Text</th>
												<th>Check the question</th>						
											</tr>
											<tr class="tableContent" ng-repeat="q in userquest">					
					 							<td class="border">{{q.id}}</td>
					 							<td class="border" style = "width: 15%">{{q.metaCategory}}</td>
					 							<td class="border" style = "width: 15%">{{q.category1}}</td>
					 							<td class="border" style = "width: 10%">{{q.level}}</td>
					 							<td class="border">{{q.shortText}}</td>
					 							<td style = "width:10%">
					 							<input type="checkbox" name="questionsId" value="{{q.id}}"/></td>
											</tr>
										</table>				
				
									</div> 
								
								</div>									
																
							</td>						
						</tr>	
															
					</tbody>				
				</table>
				
				<div class="center">
			
							<h4 class="tableTitle">Data of person</h4>								
								<table>
									<tr>
										<td>Id:</td>
										<td>
											<input type="text" name="personId"
											placeholder="312645798" onkeyup="check(this.value)"
											maxlength="9" size="7" /> <span id="e_login"
											style="display: none; color: #c00;">Not correct</span><br>
										</td>
									</tr>
									<tr>
										<td>FirstName:</td>
										<td><input type="text" name="personName" placeholder="Josh"
											size=20 /></td>
									</tr>
									<tr>
										<td>SurName:</td>
										<td><input type="text" name="personSurname"
											placeholder="Jekson" size=20 /> <br></td>
									</tr>
									<tr>
										<td>E-mail:</td>
										<td><input type="text" name="personEmail" size=20
											placeholder="josh-jakson@mail.com" /> <br></td>
									</tr>
								</table>
								
								<div id="generate_test">
									<input type="submit" value="Generate test" disabled />
								</div>
						
					</div>
					<div class="inputFormBox">						 
						
					</div>
				</form>

				<!--  <div class="otherButtonsOnPage">
					<a href=".">Home</a> &nbsp;&nbsp; <a href="view_results">Test
						Results</a>
				</div> -->
				<br> <br>
				<script type="text/javascript">
					document.write("${result}");
				</script>
			</div>
			<div id="additional_area"></div>

		</div> 
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>
	
	<script>
	
	
	
	function showUserQuestions() {		
		document.getElementById("showManualSelection").style.display = "inline";
		document.getElementById("userQuest").style.display="none";
		document.getElementById("hideQuest").style.display="block";
	}
	
	function hideUserQuestions() {
		document.getElementById("showManualSelection").style.display = "none";		
		document.getElementById("userQuest").style.display="block";
		document.getElementById("hideQuest").style.display="none";
	}
	
	var appl=angular.module('user_quest_app',[]);
	appl.controller('user_quest_contr',function($scope){		
		var json=JSON.parse('${userQuestions}');		
		$scope.userquest=json.results;
	});
	
</script>
</body>
</html>
