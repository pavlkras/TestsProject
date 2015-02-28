<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book query</title>
</head>
<body>
	<form action="query_processing">
		Please type Company Name for search: <input type="text" name="jpaStr" size=50 value="" ><!-- Select c from Company c  -->
		<input type="submit" value="SEARCH" name="adding"/>
		<button  value="" name="adding">VIEW ALL</button>
	</form>
	<br>
	<p>${myResult}</p>
	<br>	
	<a href="companyadd">Add new Company</a>
	<br>
	<br>
	<a href=".">Home Page</a>
</body>
</html>