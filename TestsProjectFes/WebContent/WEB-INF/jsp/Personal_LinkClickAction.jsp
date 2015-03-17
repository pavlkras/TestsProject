<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>VERYFICATION</title>
</head>
<body>
	<form action="jobSeeker_authorization_event" method="post"
		commandName="authorization_event_form">
		<table>
			<tr>
				<td colspan="2" align="center"><h2>Login form</h2></td>
			</tr>
			<tr>
				<td>ID number:</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" /></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
document.write("${logedUser}");
</script>
<br>
	<a href=".">Home</a>
</body>
</html>