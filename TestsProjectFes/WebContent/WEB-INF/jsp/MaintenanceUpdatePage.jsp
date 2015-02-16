<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, java.text.*,tel_ran.tests.controller.Maintenance" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UPDATE</title>

<script type="text/javascript">	
    function test(questionId) {	    	
    	var EDIT_Q = document.getElementsByName("edit_q")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:block";
    	EDIT_Q.setAttributeNode(att);
    	
    	var FORM_C = document.getElementsByName("questionID")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_C.setAttributeNode(att);
    	
    	var DELETE_Q = document.getElementsByName("delete_q")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:block";
    	DELETE_Q.setAttributeNode(att);
    	
    	var FORM_B = document.getElementsByName("questionIDdelete")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_B.setAttributeNode(att);
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
	background-color: silver;
}

td:hover {
    color:black;
	background-color: white;	
}

td {
	border-bottom: 0.1em solid black;
}
textarea:HOVER{
background-color: silver;	
}
table {
color:white;
background-color:grey;
	border: 0.1em solid grey;
	width: 100%;
}
p{
	color: blue;
	border-bottom: 0.1em solid black;	
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
<form name="delete_q" class="editingAction" action='deleteAction'>
    Question N:  <input type='text' name='questionIDdelete' size="8">&nbsp;&nbsp; 
    <input type="submit" value='Delete ?' >
    </form>
    <form name="edit_q"  class="editingAction" action="getArrayFromDB">
	Question N:  <input	type="text" name="questionID" size="8">&nbsp;&nbsp; 
    <input type="submit" value="Edit ?" >
    </form>	  
         
    <a href="http://localhost:8080/TestsProjectFes/SignInAction?username=&password=">Back to Home Page</a><br>
	<p onclick="test('1')">Update - Change/Delete  issues</p><!-- test working java script in this jsp file -->
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
