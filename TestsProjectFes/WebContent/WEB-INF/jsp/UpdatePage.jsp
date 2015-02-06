<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*, controller.MappingController" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UPDATE</title>

<script type="text/javascript">	
    function test(questionId) {	    	
    	var EDIT_Q = document.getElementsByName("editing")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:block";
    	EDIT_Q.setAttributeNode(att);
    	
    	var FORM_C = document.getElementsByName("questionKey")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_C.setAttributeNode(att);	    	
      }  
</script>
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

td:hover {
	background-color: yellow;
	border:none;
}

td {
	border: 0.01em solid black;
}

table {
	border: 0.01em solid black;
	width: 100%;
}
p{
	color: blue;
	border-bottom: 0.01em solid black;	
}
.editingAction{
display: none;
float: right;
margin-top: 2em;
margin-right: 5em;
} 
.resultTextTag{
visibility: hidden;
}
 .getFromDB{
display: none;
} 
</style>
</head>
<body onload="actionTypeChange()">	
<form name="editing"  class="editingAction" action="getArrayFromDB">
	Question N:  <input	type="text" name="questionKey" size="8">&nbsp;&nbsp; 
    <input type="submit" value="Edit ?" >
    </form>	
<a href="http://localhost:8080/Test-tr-project/">Home Page</a><br>
	<p onclick="test('1')">Update - Change  issues</p><!-- test working java script in this jsp file -->
	<form  name="searchCODE" action="search_actions">
		 <input  type="text" name="free_question" size="50">
		 <input type="submit"	value="SEARCH"><br> 	
	</form>	
    <br><br>
	<script type="text/javascript">
	document.write("${result}");
	</script>	
</body>
</html>
