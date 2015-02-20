<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
* {
	text-align: center;
}

.myButton {
	-moz-box-shadow: inset 0px 1px 0px 0px #f5978e;
	-webkit-box-shadow: inset 0px 1px 0px 0px #f5978e;
	box-shadow: inset 0px 1px 0px 0px #f5978e;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ed9993
		), color-stop(1, #c62d1f));
	background: -moz-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background: -webkit-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background: -o-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background: -ms-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background: linear-gradient(to bottom, #ed9993 5%, #c62d1f 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed9993',
		endColorstr='#c62d1f', GradientType=0);
	background-color: #ed9993;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	border-radius: 8px;
	border: 1px solid #d02718;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: arial;
	font-size: 15px;
	font-weight: bold;
	padding: 2px 21px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #810e05;
}

.myButton:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #c62d1f
		), color-stop(1, #ed9993));
	background: -moz-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background: -webkit-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background: -o-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background: -ms-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background: linear-gradient(to bottom, #c62d1f 5%, #ed9993 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#c62d1f',
		endColorstr='#ed9993', GradientType=0);
	background-color: #c62d1f;
}

.myButton:active {
	position: relative;
	top: 1px;
}
</style>
<title>Index Page</title>
</head>
<body>
	<a class="myButton" href='http://localhost:8080/TestsProjectFes/'>Reload
		Page</a>
	<br> Maintenance SignIn
	<br>
	<form action="Maintenance">
		user name: <input type="text" name="usernamem" size=50 /><br>
		password: <input type="password" name="passwordm" size=50 /><br>
		<input type="submit" class="myButton" value="PUSH">
	</form>
	<br> Company SignIn
	<br>
	<form action="CompanyActions">
		user name: <input type="text" name="usernamec" size=50 /><br>
		password: <input type="password" name="passwordc" size=50 /><br>
		<input type="submit" class="myButton" value="PUSH">
	</form>
	
	<br> Personal SignIn
	<br>

	<form action = "PersonalActions" name="loginForm" method = "post">
		
		
		<input type="submit" class="myButton" name="login" value="Login""> 
		<input type="submit" class="myButton" name="sign_up" value="SignUp"">

	</form>
	<form action="PersonalActions"></form>
	<br>
	<p>
		Name NONE <br>Password NONE
	</p>
</body>
</html>