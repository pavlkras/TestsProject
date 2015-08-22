<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/css/MaintenanceHomePage.css"></c:url>'
	rel="stylesheet">

<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>		
<script src="static/js_folder/header&&rightmenu.js">
	
</script>
<title>Company Home Page</title>
</head>


<body>
	<div id="container">
		
		<div id="right_side">
			<div id="formDiv">
				<br>
				<h2>Company LogIn</h2>
				<br>	
					<script type="text/javascript">
						document.write("${myResult}");						
					</script>		
				<br>
				<form class='myButton' action="loginProcessing">
					Login: <input type="text" name="companyName" size="24"><br>
					<br> Password: <input type="password" name="password"
						size="20"><br> <br> <input class='myButton'
						type="submit" value="LogIn">
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
</body>
</html>


