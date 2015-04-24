<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
  function check(login) {
    if (login.length != 9) document.getElementById("e_login").style.display = "inline";
    else document.getElementById("e_login").style.display = "none";
  }
</script>
<style type="text/css">
.categoryCheckBox{
float: left;
}
.inputFormBox{
float: left;
 margin-left: 2em;
}
.otherButtonsOnPage{
clear: left;
margin-top: 4em;
}
</style>
</head>
<body>
 <form action="add_test">
 <div class="categoryCheckBox"> 
 Please select a Category of Question: <br> <br>  
	<script type="text/javascript">
	document.write("${categoryFill}");
	</script></div>  <div class="inputFormBox">  
 Level of Difficulty min: <br>   
    1<input type="radio" name="levelmin" value=1 checked="checked">
    2<input type="radio" name="levelmin" value=2>
    3<input type="radio" name="levelmin" value=3>
    4<input type="radio" name="levelmin" value=4>
    5<input type="radio" name="levelmin" value=5> <br> 
    Level of Difficulty max: <br>   
    1<input type="radio" name="levelmax" value=1>
    2<input type="radio" name="levelmax" value=2>
    3<input type="radio" name="levelmax" value=3>
    4<input type="radio" name="levelmax" value=4>
    5<input type="radio" name="levelmax" value=5 checked="checked"> <br> 
    SelectQuestion Count:<br>
    <select name="selectCountQuestions">    
    <option value="10">10test</option><!-- for deleting !!! -->
    <option value="30">30</option>
    <option value="40">40</option>
    <option value="50">50</option>
    <option value="60">60</option>    
    </select>
  <br><br> 
  Please fill data of Person:   <br>        
   <table>
   <tr>
    <td>Id:</td> 
    <td> <input type="text" name="personId" placeholder="312645798" onkeyup="check(this.value)"  maxlength="9" size = "7"/> <span id="e_login" style="display: none; color: #c00;">Not correct</span><br> </td>
   </tr>
   <tr>
    <td>FirstName:</td>
    <td><input type="text" name="personName" placeholder="Josh" size=20 /></td> 
   </tr>
   <tr>
    <td>SurName: </td>
    <td><input type="text" name="personSurname" placeholder="Jekson"size=20 /> <br> </td> 
   </tr> 
    <tr>
    <td>E-mail: </td>
    <td><input type="text" name="personEmail" size=20  placeholder="josh-jakson@mail.com"/> <br> </td> 
   </tr> 
   </table>    
   <input type="submit" value="Generate test" />
   </div>
 </form>
 
 <div class="otherButtonsOnPage">
  <a href=".">Home</a> &nbsp;&nbsp; <a href="view_results">Test Results</a>
  </div>
  <br><br>
  <script type="text/javascript">
	document.write("${result}");
	</script>
</body>
</html>
