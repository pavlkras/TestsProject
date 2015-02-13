<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='<c:url value="/static/css/trainee_mode_parameters_styles.css"></c:url>' rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<title>Category choice</title>
<script type="text/javascript">

function checkCategory(){
    var category = document.getElementById("catname").selectedIndex;
    if(category == 0 || category == -1){
       alert("Please, choose category!!!");
       return false;
    }
    else
       return true;
    }

</script>
</head>

<body>

<form action="add_questions_count" onsubmit="return checkCategory()">

    <table class="tab">

    <tr>
    <th>Select category:</th>
    <td>
    <form:select id="catname" name="catName" path="categoryNames">
    <form:option value="NONE" label="-SELECT-"></form:option>
    <form:options items="${categoryNames}" />
    </form:select>
    </td>
    </tr>
    <tr>
    <td colspan="2"><input class="button" type="submit" value="Submit"/></td>
    </tr>

    </table>

</form>
</body>
</html>