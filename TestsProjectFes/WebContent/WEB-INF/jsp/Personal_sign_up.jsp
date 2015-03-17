<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="<c:url value="/static/js/add.js" />"></script>
<link href="<c:url value="/static/css/general.css" />" rel="stylesheet">
<link href="<c:url value="/static/css/choose.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up page</title>
</head>
<body>
<div class = "header">
<h1 class= "title">Sign up page</h1>
<h3 class = "subtitle">Testing sign up</h3>
</div>

<div id ="signUpForm"> 
<form:form action="signup_action" method="post" commandName="userForm" >
<table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>SignUp form</h2></td>
                </tr>
                 <tr>
                    <td>ID number:</td>
                    <td><form:input path="id" id="id"/></td>
                </tr>
                <tr>
                    <td>User Name:</td>
                    <td><form:input path="name" id="name"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:password path="password" id="first_password"/></td>
                </tr>
                 <tr>
                    <td>Confirm Password:</td>
                   <%--  <td><form:password path="confirm_password" id="confirm_password"/></td> --%>
                </tr>
                <tr>
                    <td>E-mail:</td> 
                    <td><form:input path="email" id="email"/></td>
                </tr>               
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="SignUp" /></td>
                    
                </tr>           
                <tr>
                <td><input type="submit" name="login" value="Login" /> </td>
                </tr>   
                </table> 
                </form:form>
       
</div>
<p id = 'logedUser'>User in session: ${logedUser}</p>
<br>
	<a href=".">Home</a>
</body>
</html>
