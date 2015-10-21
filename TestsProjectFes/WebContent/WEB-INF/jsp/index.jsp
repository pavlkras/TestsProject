<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>HRTrueTest Home</title>

	<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="static/js_folder/header&&rightmenu.js"></script>
	<link href='<c:url value="/static/css_folder/style.css"></c:url>'
		rel="stylesheet">
	<link href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
		rel="stylesheet">	
</head>

<body>
	<div id="container">
		<div id="right_side">
			<div id="description">
				<h2>About Us</h2>
				<div id="imgDiv">
				<img class="leftimg" src="static/images/test.jpg" alt="sf tours logo" name="logo" width="300" align="left" height="240" id="exam" />
				<br>
				The control tests project (aka the project) is intended for performing and analysis
				of the professional and psychological tests allowing the companies to select the best candidates for hiring.<br><br>
				This document describes a development scope of MVP (Minimal Valued Product).<br><br>
				The Software being developed under the project gives tools for testing automation of the candidates
				to get specific jobs. The customers of the project software may be as companies for a candidate's selection as well
				as persons for training.<br><br>
				The MVP consists of 4 following independent parts of development:<br>	
				<ul>
					<li>Company Actions</li>
					<li>Personal Actions</li>
					<li>Tests Maintenance</li>
					<li>Creation of test's questions</li>
				</ul>			
				</div>
				<div class="clearFloat"></div>
				
							
				Company Actions involve the following Use Cases:<br>
				<ul><li>Registration</li><li>Login</li><li>Ordering test for a specified candidate </li>
				<li>Viewing test results</li></ul><br>
				Personal Actions involve the following Use Cases:
				<ul><li>Registration</li><li>Login</li><li>Performing Test</li><li>Trainee Mode</li>
				<li>Control Mode</li></ul><br>
				Tests Maintenance involves the following Use Cases:
				<ul><li>Entering test data</li><li>Update test data</li><li>Bulk entering test data</li></ul>
				
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



