
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.Maintenance"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link href='<c:url value="/static/css_folder/css/MaintenanceHomePage.css"></c:url>' rel="stylesheet">
<title>Maintenance Actions</title>
<script type="text/javascript">
	var textTxt;
	evt = event || window.event;
	function readSingleFile(evt) {
		//Retrieve the first (and only!) File from the FileList object
		var f = evt.target.files[0];
		if (f) {
			var r = new FileReader();
			r.onload = function(e) {
				var contents = e.target.result;
				///////////////////////////////////////////////////////////////////
				alert("Got the file.  \n" + "name: " + f.name + "\n" + "type: "
						+ f.type + "\n" + "size: " + f.size + " bytes \n"
						+ "starts with: "
						+ contents.substr(1, contents.indexOf("r")) + "\n"
						+ "contents: " + contents + "\n");				
				// textTxt = contents.substr(1, contents.indexOf("r"); 
                textTxt = contents;              
                ///////////////////////////////////////////////////////////////////
               // TO DO AJAX function for send text from file in var textTxt;
			}
			r.readAsText(f);
		} else {
			alert("Failed to load file");
		}
	}

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
	function getTextText(){
		  var FILE_SW = document.getElementById("textfromfile");
  		var att = document.createAttribute("value");
  		att.value = ""+contents;
  		FILE_SW.setAttributeNode(att);
	}
</script>
</head>
<body
	onload="document.getElementById('fileinput').addEventListener('click',readSingleFile,false)">
	<p>
		<script type="text/javascript">
			document.write("${loginText}");
		</script>
	</p>
	<br>
	<br>
	<div id="fileaddform" class="fileaddform">
		<p>Auto Complete Data Base from external file</p>
		<br> Please choice specific file to fill data base <br> <br>

		<input type="file" id="fileinput">
		<form action="add_from_file_actions">
		<input type="text" name="textfromfile" value="" onclick="getTextText()">
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
