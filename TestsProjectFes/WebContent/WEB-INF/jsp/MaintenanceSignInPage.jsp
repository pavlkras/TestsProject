<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
    	}  
</script>
<title>Maintenance Actions</title>
</head>
<style type="text/css">
 *{
	text-align: center;
}
/** form for adding questions from file switch ****/
.fileaddform{
display: none;
}
/**** Generated code form, for tag <a>, resurce from code:(http://www.bestcssbuttongenerator.com) *****/
.myButton {
    margin:0.1em;
	-moz-box-shadow: 3px 4px 0px 0px #899599;
	-webkit-box-shadow: 3px 4px 0px 0px #899599;
	box-shadow: 3px 4px 0px 0px #899599;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ededed), color-stop(1, #bab1ba));
	background:-moz-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background:-webkit-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background:-o-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background:-ms-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background:linear-gradient(to bottom, #ededed 5%, #bab1ba 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#bab1ba',GradientType=0);
	background-color:#ededed;
	-moz-border-radius:15px;
	-webkit-border-radius:15px;
	border-radius:15px;
	border:1px solid #d6bcd6;
	display:inline-block;
	cursor:pointer;
	color:#3a8a9e;
	font-family:arial;
	font-size:17px;
	padding:7px 25px;
	text-decoration:none;
	text-shadow:0px 1px 0px #e1e2ed;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #bab1ba), color-stop(1, #ededed));
	background:-moz-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background:-webkit-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background:-o-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background:-ms-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background:linear-gradient(to bottom, #bab1ba 5%, #ededed 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#bab1ba', endColorstr='#ededed',GradientType=0);
	background-color:#bab1ba;
}
.myButton:active {
	position:relative;
	top:1px;
}
</style>
<body>
<div id="homepage">
<a class="myButton" href='http://localhost:8080/TestsProjectFes/maintenanceadd'>Create new question</a>
<br>
<a class="myButton" href='http://localhost:8080/TestsProjectFes/update'>Update/Delete Question</a>
<br>
<a class="myButton" href='#' onclick="ClickLoadFromFile()">Adding questions from file</a>
<a class="myButton" href='http://localhost:8080/TestsProjectFes/'>...</a>
 </div>
<div id="fileaddform" class="fileaddform">
	<p>Auto Complete Data Base from external file</p>
	<br> Please choice specific file to fill data base
	<br>
	<form action="add_from_file_actions">
 	 <input type="file" name="file_name" id="loaded_file">
  	 <br>   
  	<input type="submit">
 	</form>
 <br>
</div>
	<br>
	<p>
	<script type="text/javascript">
		document.write("${result}");
	</script>
	</p>
<script>
var control = document.getElementById("loaded_file");
</script>
</body>
</html>
