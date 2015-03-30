<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.isRequared{
/* this is requared span witch star  */
	color: red;
}
.submitButton,.confirmpasspan,.mailWrongSpan{
visibility: hidden;
}
.confirmpasspan,.passpan{
font-size: 0.80em;
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var confirmPassFlag = false;
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
              //// ---- Password Actions -----
               $("#first_password").keyup(function() {
			    var pass1 = document.user_create_form.first_password.value;			   		
			    if (pass1.length < 7) {
				$(this).css("color","red");
				$(".passpan").css("color","red");
				$(".passpan").css("visibility","visible");
				$("#passpan").text("Weak password");
				$(".submitButton").css("visibility","hidden");
			}else{
				$(this).css("color","green");
				$(".passpan").css("color","green");
				$(".passpan").css("visibility","visible");
				$("#passpan").text("Ok");	
				confirmPassFlag = false;
			}
		});
		
		$("#confirm_password").keyup(function() {
			var pass1 = document.user_create_form.first_password.value;
			var pass2 = document.user_create_form.confirm_password.value;			
			if (pass1 != pass2) {
				$(this).css("color","red");
				$(".confirmpasspan").css("color","red");
				$(".confirmpasspan").css("visibility","visible");				
				$(".submitButton").css("visibility","hidden");
				confirmPassFlag = false;
			}else{
				$(this).css("color","green");
				$(".confirmpasspan").css("color","green");
				$(".confirmpasspan").css("visibility","visible");
				$("#confirmpasspan").text("Ok");
				confirmPassFlag = true;
			}
		});
	    //// ---- Password Actions -----
		$("body").mousemove(function(){
			var p1 = document.user_create_form.first_password.value;
			var p2 = document.user_create_form.confirm_password.value;	
			var mailUs =  document.user_create_form.email.value;
			var fname = document.user_create_form.firstname.value;
			var lname = document.user_create_form.lastname.value;
			if(p1.length > 3 && p2.length > 3 && mailUs.length > 4 && fname.length > 3 && lname.length > 3 && confirmPassFlag){
				$(".submitButton").css("visibility","visible");
				$(".isRequared").css("color","green");
			}else{
				$(".submitButton").css("visibility","hidden");
				$(".isRequared").css("color","red");
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
					<td>First Name <span class="isRequared">*</span></td>
					<td><input type="text" name="firstname" id="firstname" /></td>
				</tr>
				<tr>
					<td>Last Name <span class="isRequared">*</span></td>
					<td><input type="text" name="lastname" id="lastname" /></td>
				</tr>
				<tr>
					<td>E-mail Address <span class="isRequared">*</span></td>
					<td><input type="email" name="email" id="email" />&nbsp;<span
						id="mailWrongSpan" class="mailWrongSpan">&nbsp;</span></td>
				</tr>
				<tr>
					<td>Password <span class="isRequared">*</span></td>
					<td><input type="password" name="password" id="first_password" />&nbsp;<span id="passpan"
						class="passpan">&nbsp;</span></td>
				</tr>
				<tr>
					<td>Confirm Password <span class="isRequared">*</span></td>
					<td><input type="password" name="confirm_password"
						id="confirm_password" /> &nbsp;<span id="confirmpasspan"
						class="confirmpasspan">&nbsp;Not identical</span></td>
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
