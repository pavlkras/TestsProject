<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
 <link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<style type="text/css">
.isRequaredfn,.isRequaredln,.isRequaredmail,.isRequaredpp,.isRequaredcp{
/* this is requared span witch star  */
	color: red;
}
.confirmpasspan,.mailWrongSpan{
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
				
			}else{
				$(this).css("color","green");
				$(".passpan").css("color","green");
				$(".passpan").css("visibility","visible");
				$("#passpan").text("Ok");							
			}
			    var pass2 = document.user_create_form.confirm_password.value;
			    if(pass2.length > 1){
			    	$(".submitButton").attr("disabled", false);	
			    }
		});
		////
		$("#confirm_password").keyup(function() {
			var pass1 = document.user_create_form.first_password.value;
			var pass2 = document.user_create_form.confirm_password.value;			
			if (pass1 != pass2) {
				$(this).css("color","red");
				$(".confirmpasspan").css("color","red");
				$(".confirmpasspan").css("visibility","visible");	
				$(".submitButton").attr("disabled", true);
			}else{
				$(this).css("color","green");
				$(".confirmpasspan").css("color","green");
				$(".confirmpasspan").css("visibility","visible");
				$(".submitButton").attr("disabled", false);
				$("#confirmpasspan").text("Ok");				
			}
		});
		////
		$("#signUpForm").keyup(function() {
			var fname = document.user_create_form.firstname.value;
			if(fname.length > 3){
				$(".isRequaredfn").css("color","green");
			}else{
				$(".isRequaredfn").css("color","red");
			}
			var lname = document.user_create_form.lastname.value;
			 if(lname.length > 3){
				$(".isRequaredln").css("color","green");
			} else{
				$(".isRequaredln").css("color","red");
			}
			var mailUs =  document.user_create_form.email.value;
			if(mailUs.length > 4){
				$(".isRequaredmail").css("color","green");
			} else{
				$(".isRequaredmail").css("color","red");
			}
			var p1 = document.user_create_form.first_password.value;
			if(p1.length > 4){
				$(".isRequaredpp").css("color","green");
			} else{
				$(".isRequaredpp").css("color","red");
			}
			var p2 = document.user_create_form.confirm_password.value;	
			if(p2.length > 4){
				$(".isRequaredcp").css("color","green");
			} else{
				$(".isRequaredcp").css("color","red");
			}			
		});	   
	});
</script>
<title>Sign up page</title>
</head>
<body>
	<div id="container">
	 	<div id ="header">
	 		<div id="logo">
	 			
	 		</div>
	 		<div id="search_area">
	 			<input id="text_Area" type="text" placeholder="Search.."/>
	 			<input id="button" type="button" value="search"/>
	 		</div>
	 		
	 	</div>
	 	<div id="nav_area">
	 			<ul>
	 				<li><a href=".">Home</li></a>
	 				<li><a href="login">User Login</li></a>
	 				<li><a href="CompanyActions">Company Login</li></a>
	 				<li><a href=".">FAQ</li></a>
	 				<li><a href=".">Contact Us</li></a>
	 			</ul>
	 	</div><!--end nav area-->

	 	<div id="left_side">
        
        
	 		<div id="testexamples">
               <div>
	 			<h2>Test Examples</h2>
              </div>
	 			<ul>
                
	 				<li><a href=".">JAVA</li></a><hr>
	 				<li><a href=".">C++</li></a><hr>
	 				<li><a href=".">C#</li></a><hr>
	 				<li><a href=".">Android</li></a><hr>
	 				<li><a href=".">Javascript</li></a><hr>
                    <li><a href=".">HTML&CSS</li></a><hr>
                    <li><a href=".">Other Tests</li></a>
	 			</ul>
 		  </div><!--end tesst examles-->

 	  </div><!--end left_side area-->

 	  <div id="right_side">

	 		<div id="formDiv">
                <h2>User Registration</h2>
                
                <form action="signup_action" method="post" name="user_create_form">
        
         
        
                <div>
                <label>First Name</label>
              <span class="ipsForm_required">*</span>
                <input type="text" name="firstname" id="firstname" placeholder="Josh"/>
                </div>
                <div>
                <label>Last Name</label>
              <span class="ipsForm_required">*</span>
                <input type="text" name="lastname" id="lastname" placeholder="Jonson" />
                </div>
                <div id="longdiv">
                <label>E-mail</label>
              <span class="ipsForm_required">*</span>
               <input type="email" name="email" id="email" placeholder="Josh-Jonson@mail.com"/><span
                                id="mailWrongSpan" class="mailWrongSpan"></span>
                </div>
                <div>
                <label>Password   </label> <span class="ipsForm_required">*</span> 
                <input type="password" name="password" id="first_password" />
               
                </div>
                
                <div  id="longdiv">
                <label>Confirm Password   </label> <span class="ipsForm_required">*</span> 
               <input type="password" name="confirm_password"
                                id="confirm_password" />
               &nbsp;<span id="confirmpasspan"
						class="confirmpasspan">&nbsp;Not identical</span>
                </div>
                <div><input id="submitButton" class="submitButton" type="submit"
                                value="Create Account" disabled />
                </div>
                    
                    
                </form>
            </div>
			<div id="additional_area"> </div>
				 		
 	  </div><!--end of right area-->
 	


 	<div id="footer_area">
	
 	<p> Copyright &copy; 2014 HrTrueTest</p>
	</div>
</div>
</body>
</html>
