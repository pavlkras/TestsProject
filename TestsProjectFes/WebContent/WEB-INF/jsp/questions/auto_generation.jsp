
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*, tel_ran.tests.controller.AbstractAdminActions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">

<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="/TestsProjectFes/static_js/CompanyJS/comp_other_resourses_page.js"></script>
<script src="/TestsProjectFes/static/js_folder/header&&rightmenu.js"></script>

<title>OTHER RESURSES FOR COMPANY</title>
</head>




<body>
	<div id="container">


 	  <div id="right_side">


	 	<div id="description">

	<div id="formCategory" class="formCategory">
		
		<form action='callAutoGeneration'>
		    <div>
		    <label>Select a category questions:</label>
			<select name="category" id="categoryOfQuestions">
				<option value="none">Select Category For Generation</option>
				
				
			</select>
			</div>
			
			<div>
			<label>Number of questions :</label>
			<input type="number"
				name='nQuestions' value='50' size="5"> 
			</div>
				<div>
				<input type='submit' value="Generate Questions">
				</div>
				<label>${result}</label>
		</form>
	</div>
	<br>
	
	<br>
	 	</div><!--end of our description area-->
				 	<div id="additional_area"> </div>
				 		
 	  </div><!--end of right area-->
 	


 	<div id="footer_area">
	
 	<p> Copyright &copy; 2014 HrTrueTest</p>
	</div>
</div>
	<script>	
	var roleNumber = ${role};	
	getMenu(roleNumber);</script>	
</body>
</html>