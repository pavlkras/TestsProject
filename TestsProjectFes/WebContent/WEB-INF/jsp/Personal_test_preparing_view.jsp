<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='<c:url value="/static/css/trainee_mode_parameters_styles.css"></c:url>' rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<title>Test parameters</title>
</head>

<body>

<table id="prepInfo">
<tr>
<th colspan="2">Chosen test parameters:</th>
</tr>
<tr>
<td>Category:</td>
<td>${param1}</td>
</tr>
<tr>
<td>Questions quantity:</td>
<td>${param2}</td>
</tr>
<tr>
<td colspan="2"><input class="button" type="submit" value="Create test"/></td>
</tr>
</table>

</body>
</html>