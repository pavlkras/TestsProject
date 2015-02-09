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
		please type query: <input type="text" name="jpaStr" size=50 value="Select c from Company c" >
		<input type="submit" value="Send query" name="adding"/>
		<button  value="Select c from Company c" name="adding">VIEW ALL</button>
	</form>
	<br>
	<form action="add">
		<input type="submit" value="Add COMPANY"/>
	</form>
	<br>
	<a href="http://localhost:8080/Company_Project/add">Add COMPANY</a>
</body>
</html>