<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>

<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<script src="static/js_folder/header&&rightmenu.js"></script>	
<script type="text/javascript">
	$(document).ready(function() {
		$(".miscStyle").css("display", "block");
		$("#h2miscel").hover(function() {
			$(".miscStyle").css("display", "block");

			$("#popularLincs").css("display", "none");
			$(".javaTech").css("display", "none");
		}, function() {
			///$("#popularLincs").css("visibility", "hidden");
		});
		//
		$("#h2popular").hover(function() {
			$("#popularLincs").css("display", "block");

			$(".miscStyle").css("display", "none");
			$(".javaTech").css("display", "none");
		}, function() {
			///$(".miscStyle").css("visibility", "hidden");
		});
		//
		$("#h2javaTech").hover(function() {
			$(".javaTech").css("display", "block");

			$("#popularLincs").css("display", "none");
			$(".miscStyle").css("display", "none");

		}, function() {
			///$(".miscStyle").css("visibility", "hidden");
		});
		//
		$("#h2login").hover(function() {
			$(".formDiv").css("display", "block");			
			
			$(".javaTech").css("display", "none");
			$("#popularLincs").css("display", "none");
			$(".miscStyle").css("display", "none");

		}, function() {
			///$(".formDiv").css("display", "none");
		});
	});
</script>
<title>HRTrueTest User SignIn</title>
</head>
<body>
	<div id="container">
 	  <div id="right_side" class="centerBlock">
	 		<div id="formDiv">
		<h2>User Login</h2>
        <form action="login_action" method="post" name="user_login_form">
		<div>
        <label>Mail       </label>
      <span class="ipsForm_required">*</span>
        <input type="email" name="userEmail" id="email" />
 		</div>
		<div>
        <label>Password   </label> <span class="ipsForm_required">*</span> 
        <input type="password" name="password" id=			"first_password" />
       
		</div>
        <div><input type="submit" class="buttonStyle" name="login" value="Login" /> &nbsp;
        <input type="submit" name="sign_up" value=		"Registration" />
        </div>
			
		<div id="resultMessage">${logedUser}</div>	
		</form>
	   </div>
			<div id="additional_area"></div>						
 	  </div><!--end of right area-->
 	<div id="footer_area">	
 	<p> Copyright &copy; 2014 HrTrueTest</p>
	</div>
</div>
<script>var r = ${role}; getMenu(r);</script>	
</body>
</html>