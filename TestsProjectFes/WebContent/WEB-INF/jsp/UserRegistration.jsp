<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.ipsForm_required {
	color: red;
}
.submitButton,.confirmpasspan,.mailWrongSpan{
visibility: hidden;
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	$(document).ready(function() {		
		$("#email").change(function() {			
			var interedMailFromUser =  document.user_create_form.email.value;			
			var isExistUserMail = false; 
			//  isExistUserMail = isExist...// TO DO method rest or ajax for send query to DB and return to page
			var isMailEmpty = false;
			// ------ stub method  --------
			if(interedMailFromUser == "aaa"){
				isExistUserMail = true;
			}else if(interedMailFromUser == " " || interedMailFromUser == ""){
				isMailEmpty = true;
			}
			// ---- end stub method ---		
			if (isExistUserMail) {
				$(this).css("color","red");
				$(".mailWrongSpan").css("color","red");
				$(".mailWrongSpan").css("visibility","visible");
				$(".mailWrongSpan").text("This Mail Already Exist");
			}else if(isMailEmpty){
				$(".mailWrongSpan").css("color","red");
				$(".mailWrongSpan").css("visibility","visible");
				$(".mailWrongSpan").text("A Field is Empty !");
			}
		});

		$("#confirm_password").keyup(function() {
			var pass1 = document.user_create_form.first_password.value;
			var pass2 = document.user_create_form.confirm_password.value;			
			if (pass1 != pass2) {
				$(this).css("color","red");
				$(".confirmpasspan").css("color","red");
				$(".confirmpasspan").css("visibility","visible");
				$("#confirmpasspan").text("Wrong !!!");
				$(".submitButton").css("visibility","hidden");
			}else{
				$(this).css("color","black");
				$(".confirmpasspan").css("color","green");
				$(".confirmpasspan").css("visibility","visible");
				$("#confirmpasspan").text("Confirm");
				$(".submitButton").css("visibility","visible");
			}
		});
		
	
	});
</script>
<title>Sign up page</title>
</head>
<body>
	<div id="signUpForm">
		<form action="signup_action" method="post" name="user_create_form">
			<table>
				<tr>
					<td colspan="3" align="center"><h2>Registration form</h2></td>
				</tr>
				<tr>
					<td>First Name <span class="ipsForm_required">*</span></td>
					<td><input type="text" name="firstname" id="firstname" /></td>
				</tr>
				<tr>
					<td>Last Name <span class="ipsForm_required">*</span></td>
					<td><input type="text" name="lastname" id="lastname" /></td>
				</tr>
				<tr>
					<td>E-mail Address <span class="ipsForm_required">*</span></td>
					<td><input type="email" name="email" id="email" />&nbsp;<span
						id="mailWrongSpan" class="mailWrongSpan">&nbsp;</span></td>
				</tr>
				<tr>
					<td>Password <span class="ipsForm_required">*</span></td>
					<td><input type="password" name="password" id="first_password" /></td>
				</tr>
				<tr>
					<td>Confirm Password <span class="ipsForm_required">*</span></td>
					<td><input type="password" name="confirm_password"
						id="confirm_password" /> &nbsp;<span id="confirmpasspan"
						class="confirmpasspan">&nbsp;</span></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input class="submitButton" type="submit"
						value="Create Account" /></td>
				</tr>
			</table>
		</form>
	</div>

	<br>
	<a href=".">Home</a>
</body>
</html>
