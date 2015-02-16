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
    	att.value = "display:inline-block";
    	EDIT_Q.setAttributeNode(att);
    	
    	var FORM_C = document.getElementsByName("questionID")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_C.setAttributeNode(att);
    	
    	var DELETE_Q = document.getElementsByName("delete_q")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:inline-block";
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
input:HOVER {
	background-color: silver;
	color:white;
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
.displayAction{
display: none;
float: right;
margin-top: 2em;
margin-right: 5em;
} 
.resultTextTag{
visibility: hidden;
}
.myButton {
	-moz-box-shadow:inset 0px 1px 0px 0px #f5978e;
	-webkit-box-shadow:inset 0px 1px 0px 0px #f5978e;
	box-shadow:inset 0px 1px 0px 0px #f5978e;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ed9993), color-stop(1, #c62d1f));
	background:-moz-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background:-webkit-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background:-o-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background:-ms-linear-gradient(top, #ed9993 5%, #c62d1f 100%);
	background:linear-gradient(to bottom, #ed9993 5%, #c62d1f 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed9993', endColorstr='#c62d1f',GradientType=0);
	background-color:#ed9993;
	-moz-border-radius:8px;
	-webkit-border-radius:8px;
	border-radius:8px;
	border:1px solid #d02718;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:arial;
	font-size:15px;
	font-weight:bold;
	padding:2px 21px;
	text-decoration:none;
	text-shadow:0px 1px 0px #810e05;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #c62d1f), color-stop(1, #ed9993));
	background:-moz-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background:-webkit-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background:-o-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background:-ms-linear-gradient(top, #c62d1f 5%, #ed9993 100%);
	background:linear-gradient(to bottom, #c62d1f 5%, #ed9993 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#c62d1f', endColorstr='#ed9993',GradientType=0);
	background-color:#c62d1f;
}
.myButton:active {
	position:relative;
	top:1px;
}
</style>
</head>
<body onload="actionTypeChange()">	
<a class="myButton" href='http://localhost:8080/TestsProjectFes/'>...</a>
<form name="delete_q" class="displayAction" action='deleteAction' >
    Question N:  <input  type='text' name='questionIDdelete' size="8">&nbsp;&nbsp; 
    <input type="submit" class="myButton" value='Delete ?' >
    </form>
    <form name="edit_q"  class="displayAction" action="getArrayFromDB">
	Question N:  <input	 type="text" name="questionID" size="8">&nbsp;&nbsp; 
    <input type="submit" class="myButton" value="Edit ?" >
    </form>	  
    <p onclick="test('1')">Update - Change/Delete  issues</p><!-- test working java script in this jsp file -->
	<form  name="searchCODE" action="search_actions" >
		 <input  type="text" name="free_question" size="50">
		 <input class="myButton" type="submit"  value="SEARCH"><br> 	
	</form>	
    <br><br>
	<script type="text/javascript">
	document.write("${result}");
	</script>	
</body>
</html>
