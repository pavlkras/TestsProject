<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Point Cat</title>
</head>
<body>
	<form action="loginProcessing">
	Login:	<input type="text" name="companyName"><br>
	Password: <input
			type="password" name="password"><br> <input
			type="submit" value="LogIn" >
	</form>
    <br>
	<a href ="CompanyRegistration">Registration</a>
	<br>
	<form action="create_request">
		<input type="submit" value="Company Tests Results Start Page" />
	</form>
	<p>
		<script type="text/javascript">
			document.write("${result}");
		</script>
	</p>
</body>
</html>


