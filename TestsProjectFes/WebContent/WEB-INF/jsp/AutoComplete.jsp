<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.net.Inet4Address"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adding From File</title>
<style type="text/css">
* {
	text-align: center;
}

a {
	font-size: 1.35em;
	color: blue;
}

a:HOVER {
	color: orange;
}

input:HOVER {
	background-color: yellow;
}
</style>
</head>
<body>
	<a href="http://localhost:8080/Test-tr-project/"> Home Page</a>

	<p>Auto Complete Data Base from external file</p>
	<br> Please choice specific file to fill data base
	<br>
	<form action="add_from_file_actions">
		<!-- action='http://localhost...' method=post enctype='multipart/form-data' -->
		<input type="file" name="file_name"><br> <input
			type="submit">
	</form>
	<br>
	<script type="text/javascript">
		document.write("${result}");//вывод текста
	</script>

</body>
</html>
