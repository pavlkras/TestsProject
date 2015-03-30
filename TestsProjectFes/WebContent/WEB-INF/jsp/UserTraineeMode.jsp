<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.UserActions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">	
</script>
<title>Trainee Test</title>
</head>
<body>
	<table id="table" border="0">
		<!-- creating table table rows and data -->
		<tr>
			<td><h2>${question}</h2></td>
		</tr>
		<tr>
			<td>
				<form action="UserTestLoop" method="post">				
					<%= UserActions.AutoGenForm() %> 					
				</form>

			</td>
		</tr>
	</table>
	</body>
</html>