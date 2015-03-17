<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/css/CreateQuestionPage.css"></c:url>'
	rel="stylesheet">
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
p{
	color: blue;
	border-bottom: 0.01em solid black;		
}
table{
color:white;
background-color: grey;
}
textarea:HOVER {
	background-color: silver;
}
.thFloatText{
float: left;
}
.tdFloatContent{
float: right;
}
</style>
<script type="text/javascript">
function hideAttributes(){	
	var ANSWER_TR = document.getElementById("answersTr");
	var att = document.createAttribute("style");
	att.value = "display:none";
	ANSWER_TR.setAttributeNode(att);
}
function hideLinkAndIndex(){
	var INDEX_TR = document.getElementById("indexNumTr");
	var att = document.createAttribute("style");
	att.value = "display:none";
	INDEX_TR.setAttributeNode(att);
	
   /*  var IMAGE_TR = document.getElementById("imageLinkTr");
	var att = document.createAttribute("style");
	att.value = "display:none";
	IMAGE_TR.setAttributeNode(att);  */
}

function FileReadeFunctionCalling(){
	var INPUT_FILE = document.getElementById("changeTypeto");
	var att = document.createAttribute("style");
	att.value = "display:block";
	INPUT_FILE.setAttributeNode(att); 
	
	var INPUT_TEXT = document.getElementById("changeTypefrom");
	var att = document.createAttribute("style");
	att.value = "display:none";
	INPUT_TEXT.setAttributeNode(att); 
	
}
</script>
<title>ADDING</title>
</head>
<body>		
    <p>Creating new Question </p>
	<form action="add_actions">
	
	<table>
	<tr>
	    <th class="thFloatText">Please input Question text</th>
	     <td class="tdFloatContent"><textarea name="questionText" rows="3" >Type here question text or description for image</textarea></td>
	</tr>
	<tr id="indexNumTr">
	   <th class="thFloatText">Question Index Number</th> 
	   <td class="tdFloatContent"><input onchange="hideAttributes()" type="text" name="questionIndex" value="0"></td>	    
    </tr>
    <tr>
        <th class="thFloatText">Insert  Category</th>
          <td class="tdFloatContent">		 
		     <input	type="text" name="category" value="Java,HTML" />
		 </td>
     </tr>    
	 <tr>
	     <th class="thFloatText">Select Level</th>
		 <td class="tdFloatContent">
			 1<input type="radio" name="levelOfDifficulti" value=1 checked="checked">
			 2<input type="radio" name="levelOfDifficulti" value=2>
			 3<input type="radio" name="levelOfDifficulti" value=3>
			 4<input type="radio" name="levelOfDifficulti" value=4>
			 5<input type="radio" name="levelOfDifficulti" value=5>		
	     </td>
	 </tr>
	 <tr id="answersTr" >
	    <th class="thFloatText" >Input Answer </th>	    
	    <td class="tdFloatContent" >
	        A. <textarea name="at1" onchange="hideLinkAndIndex()"></textarea><br>
		    B. <textarea name="at2"></textarea><br>
		    C. <textarea name="at3"></textarea><br>
		    D. <textarea name="at4"></textarea><br>
	   </td>
     </tr>
	 <tr>
	   <th class="thFloatText">Input number a right question answer</th>
	   <td class="tdFloatContent">
	   <input type="text" name="correctAnswer"	value="D">
	   </td>	          
	</tr>
	<tr id="imageLinkTr">
	<th class="thFloatText" >Image Link </th>
	<td class="tdFloatContent" ><input id="changeTypefrom" onclick="FileReadeFunctionCalling()" type="text" name="imageLink" value="0" /><input id="changeTypeto" type="file" name="imageLink" style="display: none;" /></td>
	</tr>
	<tr>
	 <th colspan="2"><input type="submit" value="Add To DataBase"></th></tr>
	</table>	
	</form>
	<br>
	
	<script type="text/javascript">
		document.write("${result}");
	</script>
	<a class="myButton" href='.'>home</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="myButton" href='update'>update</a>	
</body>
</html>
