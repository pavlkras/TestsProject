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
 Hi, my dear friend!</br> 
 Please select a Category of Question: <br>
  C#   <input type="checkbox" name="category" value="C#" />
  Java <input type="checkbox" name="category" value="Java" />
  C++  <input type="checkbox" name="category" value="C++" /> <br> 
      <br><br>    
 Please select Level for Question: <br>   
    1<input type="radio" name="level" value=1 checked="checked">
    2<input type="radio" name="level" value=2>
    3<input type="radio" name="level" value=3>
    4<input type="radio" name="level" value=4>
    5<input type="radio" name="level" value=5> <br> 
  <br><br> 
  Please fill data of Person:   <br>        
   <table>
   <tr>
    <td>personId:</td> 
    <td> <input type="text" name="personId" onkeyup="check(this.value)"  maxlength="9" size = "7"/> <span id="e_login" style="display: none; color: #c00;">Not correct</span><br> </td>
   </tr>
   <tr>
    <td>personName:</td>
    <td><input type="text" name="personName" size=20 /></td> 
   </tr>
   <tr>
    <td>personSurname: </td>
    <td><input type="text" name="personSurname" size=20 /> <br> </td> 
   </tr> 
   </table>    
   <input type="submit" value="Generate test" />
 </form>
 <br> 
 <a href="create_request">Test Details</a>
</body>
</html>