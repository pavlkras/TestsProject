<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
    <td><input type="text" name="personId" size=10 /> <br> </td>
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
 <form action="input_form">
  <input type="submit" value="To input form"/>
 </form>
</body>
</html>