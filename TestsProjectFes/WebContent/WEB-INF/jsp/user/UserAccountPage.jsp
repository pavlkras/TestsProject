<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="static/js_folder/header&&rightmenu.js"></script>
<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<title>HRTrueTest Home</title>
</head>
<body>
	<div id="container">
	 	
 	  <div id="right_side">


	 	<div id="formDiv">
		<h1 class="title">User personal page</h1>
			<h1>
				<a href="createTestForUser">To Test</a>
			</h1>
			<br> <a href="maintenanceadd">
				<div class="homecontent">
					<img src="static/images/logo/quizhome.png" alt="Online forum"><span
						class="homespan">Add Question</span>
				</div>
			</a>
	   </div>
				 	<div id="additional_area"> </div>
				 		
 	  </div><!--end of right area-->



	<div id="footer_area">
			
		<p>Copyright &copy; 2014 HrTrueTest</p>
	</div>
	</div>
	<script>var r = ${role}; getMenu(r);</script>	
</body>


</body>
</html>