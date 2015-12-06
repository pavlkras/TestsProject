<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create test template</title>
<script type="text/javascript" src="<c:url value='/static/js_folder/jquery-2.1.4.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/angular.min.js' />" ></script>
<script src="static/js_folder/header&&rightmenu.js"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/tests/testcontroller.js' />" ></script>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/company_styles/company.css"></c:url>'
	rel="stylesheet">

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
	
<script>	

		var token = "${token}";
</script>

</head>
<body>

	<div id="container">
		<div id="right_side" data-ng-app="test_app">
		
		<div data-ng-controller="testController">
			<form>
				<table>
					<tr data-ng-repeat="cat in autocategories">
						<td>{{cat.cat_parent}}</td>						
					</tr>
				</table>
			</form>
		</div>
		
		
		</div> 
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>

	<script>	
	
		var roleNumber = ${role}; getMenu(roleNumber);
		
</script>

</body>
</html>