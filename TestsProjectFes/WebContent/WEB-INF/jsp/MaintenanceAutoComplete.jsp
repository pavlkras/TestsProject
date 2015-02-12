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
<script type="text/javascript">
function getTextPost(varText){
	alert(varText);
}
</script>
</head>
<body>
	<a href="http://localhost:8080/TestsProjectFes/Maintenance"> Home Page</a>

	<p>Auto Complete Data Base from external file</p>
	<br> Please choice specific file to fill data base
	<br>
	<form action="http://localhost:8080/TestsProjectFes/add_from_file_actions" onsubmit="getTextPost(1)">
		<!-- input for file not work correctly for this method -->
		<input type="file" name="fileName"><br>
		 <!-- this method is input full patch for correct work adding from file on user computer -->
		<input type="text" name="file_name" value="D:/developer-workspaces/out_project/repository/tr-project/bild.txt" size="100" ><br> 		
		<input type="submit">
	</form>
	<br>
	<script type="text/javascript">
		document.write("${result}");
	</script>

</body>
</html>
