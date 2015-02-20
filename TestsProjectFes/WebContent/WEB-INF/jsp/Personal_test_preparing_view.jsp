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
<title>Test's params</title>
</head>
<body>
	<div class="header">
		<h1 class="title">Test's params</h1>
		<h3 class="subtitle">Choose your test</h3>
	</div>

	<p>User in session: ${logedUserId}</p>

	<ul>
		<li>some user's actions</li>
		<li><p>Created test with ID: ${testId}</p></li>
		<li><p>Category: ${category}</p></li>
		<li><p>Questions level: ${level}</p></li>
		<li><p>Questions quantity: ${quantity}</p></li>
		<li><a href="test_run">Start test</a></li>

	</ul>
</body>
</html>