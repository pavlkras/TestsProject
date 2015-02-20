<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/static/css/general.css" />" rel="stylesheet">
<link href="<c:url value="/static/css/choose.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">
	
</script>
<title>Login page</title>
</head>
<body>
	<div class="header">
		<h1 class="title">Test mode</h1>
		<h3 class="subtitle">Test tests:)</h3>
	</div>
	<div>
		<h3 class="subtitle">Test runs</h3>
		<p>User in session: ${logedUserId}</p>
		<p>We are ready to start test: ${testId}</p>
		<div>
			Question field<br> 
		</div>
	</div>
	<!-- creating table headers -->
	<table id="table" border="0">

		<!-- creating table table rows and data -->




		<tr>
			<td>"${question}"</td>
		</tr>
      
		<tr>
			<td>
				    <form action="test_run" method="post">
					<c:forEach items="${answersList}" var="element">
						<input type="radio" name="answer" value="${element}">${element}<Br>
					</c:forEach>
					<br> <input type="submit" value="Confirm answer" />
					</form>
				
			</td>
		</tr>


	</table>


</body>
</html>