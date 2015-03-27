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
</head>
<body>
 <form action="add_test"> 
 Please select a Category of Question: <br>   
	<script type="text/javascript">
	document.write("${categoryFill}");
	</script><br>    
 Please select Level for Question: <br>   
    1<input type="radio" name="level" value=1 checked="checked">
    2<input type="radio" name="level" value=2>
    3<input type="radio" name="level" value=3>
    4<input type="radio" name="level" value=4>
    5<input type="radio" name="level" value=5> <br> 
    SelectQuestion Count:<br>
    <select name="selectCountQuestions">
    <option  value="5">5</option>
    <option value="10">10</option>
    <option value="15">15</option>
    <option value="25">25</option>
    </select>
  <br><br> 
  Please fill data of Person:   <br>        
   <table>
   <tr>
    <td>Id:</td> 
    <td> <input type="text" name="personId" onkeyup="check(this.value)"  maxlength="9" size = "7"/> <span id="e_login" style="display: none; color: #c00;">Not correct</span><br> </td>
   </tr>
   <tr>
    <td>FirstName:</td>
    <td><input type="text" name="personName" size=20 /></td> 
   </tr>
   <tr>
    <td>SurName: </td>
    <td><input type="text" name="personSurname" size=20 /> <br> </td> 
   </tr> 
    <tr>
    <td>E-mail: </td>
    <td><input type="text" name="personEmail" size=20 /> <br> </td> 
   </tr> 
   </table>    
   <input type="submit" value="Generate test" />
 </form>
 <br> 
<br> 
	<script type="text/javascript">
	document.write("${result}");
	</script>
	<br> 
  <a href=".">Home</a> &nbsp;&nbsp; <a href="view_results">Test Details</a>
</body>
</html>
