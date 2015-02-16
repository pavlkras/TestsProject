<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">	
    function ClickLoadFromFile() {	    	
    	var FILE_SW = document.getElementById("fileaddform");
    	var att = document.createAttribute("style");
    	att.value = "display:block";
    	FILE_SW.setAttributeNode(att); 
    	
    	var HOMEP_SW = document.getElementById("homepage");
    	var att = document.createAttribute("style");
    	att.value = "display:none";
    	HOMEP_SW.setAttributeNode(att); 
    	
    	var TITLE_SW = document.getElementsByTagName("title")[0];
    	alert(TITLE_SW);
    	var att = document.createAttribute("value");
    	att.value = "Adding From File";
    	TITLE_SW.setAttributeNode(att);
    	
      }  
</script>
<title>Choice Step</title>
</head>
<style type="text/css">

a {
	font-size: 1.35em;
	color: blue;
}
a:HOVER {
	color: orange;
}
input:HOVER {
	background-color: silver;
}
.fileaddform{
display: none;
}
</style>
<body>
<ul id="homepage">
<li><a href='http://localhost:8080/TestsProjectFes/maintenanceadd'>1. Create new question</a></li>
<li><a href='http://localhost:8080/TestsProjectFes/update'>2. Update/Delete Question</a></li>
<li><a href='#' onclick="ClickLoadFromFile()">3. Adding questions from file</a></li>
<li><a href='http://localhost:8080/TestsProjectFes/'>4. Back to Home Page</a>	</li>
</ul>
<div id="fileaddform" class="fileaddform">
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
	</div>
	<br>
	<p>
	<script type="text/javascript">
		document.write("${result}");
	</script>
	</p>
</body>
</html>
