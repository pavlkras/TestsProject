<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choice Step</title>
</head>
<style type="text/css">
*{
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
<body>
<br>

<a href="http://localhost:8080/Test-tr-project/add">1. Create new question</a>
<br>
<a href="http://localhost:8080/Test-tr-project/update">2. Update Question</a>
<br>
<a href="http://localhost:8080/Test-tr-project/addfromfile">3. Adding questions from file</a>
<br>
<script type="text/javascript">
		document.write("${result}");
	</script>
</body>
</html>
