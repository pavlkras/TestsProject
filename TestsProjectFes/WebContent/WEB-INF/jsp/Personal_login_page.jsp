<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="<c:url value="/static/js/add.js" />"></script>
<link href="<c:url value="/static/css/general.css" />" rel="stylesheet">
<link href="<c:url value="/static/css/choose.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>
<body>
	<div class="header">
		<h1 class="title">Login page</h1>
		<h3 class="subtitle">Testing login and sign up</h3>
	</div>
	<div>
		<h3 class="subtitle">Choose action</h3>
		<ul>

			<div id="loginForm">
				<form:form action="login_action" method="post"
					commandName="userForm">
					<table border="0">
						<tr>
							<td colspan="2" align="center"><h2>Login form</h2></td>
						</tr>
						<tr>
							<td>ID number:</td>
							<td><form:input path="id" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><form:password path="password" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="submit"
								name="login" value="Login" /> <input type="submit"
								name="sign_up" value="Sign UP" /></td>
						</tr>
					</table>

				</form:form>
				<br>
				<br>
				<form action='Maintenance' method='get'>
					<input type='submit' class='myButton' name='login'
						value='Administrator Login'>
				</form>

				<p id='logedUser'>User in session: ${logedUser}</p>
				<br>
	<a href=".">Home</a>
</body>
</html>