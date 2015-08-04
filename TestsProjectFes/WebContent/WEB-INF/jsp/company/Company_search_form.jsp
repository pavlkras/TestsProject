<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company View</title>
<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>	
	<script src="static/js_folder/header&&rightmenu_company.js">
</script>	

</head>
<body>
		<div id="container">

		<div id="right_side_company">

			<div id="formDiv">
				<form action="query_processing">
					Please type Company Name for search: <input type="text"
						name="jpaStr" size=50 value="">
					<!-- Select c from Company c  -->
					<input type="submit" value="SEARCH" name="adding" />
					<button value="" name="adding">VIEW ALL</button>
				</form>
				<br>
				<p>${myResult}</p>
				<br> <a href="companyadd">Add new Company</a> <br> <br>

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