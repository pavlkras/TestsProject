<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">	
</script>

<title>Trainee Test</title>
</head>
<body>

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