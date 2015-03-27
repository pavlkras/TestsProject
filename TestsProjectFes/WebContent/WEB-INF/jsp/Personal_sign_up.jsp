<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html ">
<html>
<head>
<style type="text/css">
.ipsForm_required {
	color: red;
	
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up page</title>
</head>
<body>
	<div id="signUpForm">	
		<form action="signup_action" method="post" name="user_create_form">
			<table>
				<tr>
					<td colspan="2" align="center"><h2>Registration form</h2></td>
				</tr>
				<tr>
					<td>First Name <span class="ipsForm_required">*</span></td>
					<td><input type="text" name="firstname" id="id" /></td>
				</tr>
				<tr>
					<td>Last Name <span class="ipsForm_required">*</span></td>
					<td><input type="text" name="lastname" id="name" /></td>
				</tr>
				<tr>
					<td>E-mail Address <span class="ipsForm_required">*</span></td>
					<td><input type="email"  name="email" id="email" /></td>
				</tr>
				<tr>
					<td>Password <span class="ipsForm_required">*</span></td>
					<td><input type="password" name="password" id="first_password" /></td>
				</tr>
				<tr>
					<td>Confirm Password <span class="ipsForm_required">*</span></td>
					<td><input type="password" name="confirm_password" id="confirm_password" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Create Account" /></td>
				</tr>
			</table>
		</form>
	</div>

	<br>
	<a href=".">Home</a>
</body>
</html>
