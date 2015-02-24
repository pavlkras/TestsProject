
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.Maintenance"%>
<!DOCTYPE html>
<html>
<head>
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

<style type="text/css">
* {
	text-align: center;
}
/** form for adding questions from file switch ****/
.fileaddform {
	display: none;
}
/**** Generated code form, for tag <a>, resurce from code:(http://www.bestcssbuttongenerator.com) *****/
.myButton {
	margin: 0.1em;
	-moz-box-shadow: 3px 4px 0px 0px #899599;
	-webkit-box-shadow: 3px 4px 0px 0px #899599;
	box-shadow: 3px 4px 0px 0px #899599;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ededed
		), color-stop(1, #bab1ba));
	background: -moz-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background: -webkit-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background: -o-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background: -ms-linear-gradient(top, #ededed 5%, #bab1ba 100%);
	background: linear-gradient(to bottom, #ededed 5%, #bab1ba 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed',
		endColorstr='#bab1ba', GradientType=0);
	background-color: #ededed;
	-moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	border-radius: 15px;
	border: 1px solid #d6bcd6;
	display: inline-block;
	cursor: pointer;
	color: #3a8a9e;
	font-family: arial;
	font-size: 17px;
	padding: 7px 25px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #e1e2ed;
}

.myButton:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #bab1ba
		), color-stop(1, #ededed));
	background: -moz-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background: -webkit-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background: -o-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background: -ms-linear-gradient(top, #bab1ba 5%, #ededed 100%);
	background: linear-gradient(to bottom, #bab1ba 5%, #ededed 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#bab1ba',
		endColorstr='#ededed', GradientType=0);
	background-color: #bab1ba;
}

.myButton:active {
	position: relative;
	top: 1px;
}
</style>
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
