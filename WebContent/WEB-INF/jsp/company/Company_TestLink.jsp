<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
import="java.util.*, java.text.*, tel_ran.tests.controller.Maintenance"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>		
<script src="static/js_folder/header&&rightmenu_company.js">
</script>	

</head>
<body>
		<div id="container">
		
			<div id="right_side_company">		

				<form action="add_test">
					<p>${myResult}</p>
					<br>
					<br> <a href="#" onclick="history.back();">back</a> <br> <br>
				</form>
				<div id="additional_area"></div>
			</div>
						

			<div id="footer_area">

				<p>Copyright &copy; 2014 HrTrueTest</p>
			</div>
		</div>
</body>


</html>