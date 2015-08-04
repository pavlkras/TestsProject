<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">



	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<head>
	<title>HRTrueTest Home</title>
</head>


<body>
	<div id="container">
	 <div id="container">
	 	<div id ="header">
	 		<div id="logo">
	 			
	 		</div>
	 		<div id="search_area">
	 			<input id="text_Area" type="text" placeholder="Search.."/>
	 			<input id="button" type="button" value="search"/>
	 		</div>
	 		
	 	</div>
	 	<div id="nav_area">
	 			<ul>
	 				<li><a href=".">Home</a></li>
	 				<li><a href="login">User Login</a></li>
	 				<li><a href="CompanyActions">Company Login</a></li>
	 				<li><a href=".">FAQ</a></li>
	 				<li><a href=".">Contact Us</a></li>
	 			</ul>
	 	</div><!--end nav area-->

	 	<div id="left_side">
        
        
	 		<div id="testexamples">
               <div>
	 			<h2>Test Examples</h2>
              </div>
	 			<ul>
                
	 				<li><a href=".">JAVA</a></li><hr>
	 				<li><a href=".">C++</a></li><hr>
	 				<li><a href=".">C#</a></li><hr>
	 				<li><a href=".">Android</a></li><hr>
	 				<li><a href=".">Javascript</a></li><hr>
                    <li><a href=".">HTML&CSS</a></li><hr>
                    <li><a href=".">Other Tests</a></li>
	 			</ul>
 		  </div><!--end tesst examles-->

 	  </div><!--end left_side area-->	

 	  <div id="right_side">


	 	<div id="description">
		  <h2>About Us</h2>
			
            
			<p>
            <img src="static/images/test.jpg" alt="sf tours logo" name="logo" width="300" 	         align="left" height="240" id="exam" />
            The control tests project (aka the project) is intended for performing and analysis of the professional and psychological tests allowing the companies to select the best candidates for hiring
            
            This document describes a development scope of MVP (Minimal Valued Product)
            The Software being developed under the project gives tools for testing automation of the candidates to get specific jobs
            The customers of the project software may be as companies for a candidate's selection as well as persons for training 
            The MVP consists of three following independent parts of development:
            Company Actions
            Personal Actions
            Tests Maintenance 
            Creation of test's questions
            Company Actions involve the following Use Cases:
            Registration
            Login
            Ordering test for a specified candidate 
            Viewing test results
            Personal Actions involve the following Use Cases:
            Registration
            Login
            Performing Test
            Trainee Mode
            Control Mode
            Tests Maintenance involves the following Use Cases:
            Entering test data
            Update test data
            Bulk entering test data
            
            </p> 	
				 			


				 	    
				 		
				 		
				 		
				 		
	 	</div><!--end of our description area-->
				 	<div id="additional_area"> </div>
				 		
 	  </div><!--end of right area-->
 	


 	<div id="footer_area">
	
 	<p> Copyright &copy; 2014 HrTrueTest</p>
	</div>
</div>
</body>
</html>