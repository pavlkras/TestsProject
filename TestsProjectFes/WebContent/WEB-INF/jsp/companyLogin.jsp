<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<link
	href='<c:url value="/static/css_folder/css/MaintenanceHomePage.css"></c:url>'
	rel="stylesheet">

<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">


<script src="static/js_folder/header&&rightmenu.js">
	
</script>
<title>Company Home Page</title>
</head>


<body>
	<div id="container">

		<div id="right_side" class="centerBlock">
			<div id="formDiv">
				<br>
				<h2>Company LogIn</h2>
				<br>
				<script type="text/javascript">
						document.write("${myResult}");						
					</script>
				<br>
				<form class='myButton' action="loginProcessing">
					<div>
						<label>Login: </label> <span class="ipsForm_required">*</span> <input
							type="text" name="companyName" size="24">
					</div>
					<div>
						<label>Password </label> <span class="ipsForm_required">*</span> <input
							type="password" name="password" size="20">
					</div>

					<div>
						<input class='myButton' type="submit" value="LogIn">
					</div>




				</form>
				<br> <br> <a class='myButton' href="search_form">Search
					Company</a> <br> <br> <a class='myButton' href="companyadd">Registration</a>
				<br>
				<p>
					<script type="text/javascript">
						document.write("${result}");						
					</script>
				</p>
			</div>
			<div id="additional_area"></div>

		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>
	<script>var roleNumber = ${role}; getMenu(roleNumber);</script>	
</body>
</html>