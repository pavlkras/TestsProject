<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Home Page</title>
</head>
<body>
	<br>
	<br>
	<form action="loginProcessing">
		Login: <input type="text" name="companyName" size="24"><br>
		<br> Password: <input type="password" name="password" size="20"><br>
		<br> <input type="submit" value="LogIn">
	</form>
	<br>
	<a href="companyadd">Registration</a>
	<br>
	<br>
	<a href="search_form">Search Company</a>
	<br>
	<p>
		<script type="text/javascript">
			document.write("${result}");
		</script>
	</p>
</body>
</html>


