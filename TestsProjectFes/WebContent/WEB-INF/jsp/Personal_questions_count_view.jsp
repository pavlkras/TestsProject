<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='<c:url value="/static/css/trainee_mode_parameters_styles.css"></c:url>' rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<title>Questions quantity choice</title>
<script type="text/javascript">

function checkQuestionsNumber(){
    var maxNumber = parseInt(document.getElementById("maxnum").innerHTML); 
    var qnumber = parseInt(document.getElementById('qnum').value);
    if(isNaN(qnumber) || qnumber <= 0 || qnumber > maxNumber){
           alert("Wrong number of the questions. " +
           "Type the right number that is not more than maximal questions number");
           document.getElementById("myform").qnumber.focus();
           return false;
    }
    else
       return true;
    }

</script>
</head>
<body>
<div>
<pre id="info">
You have chosen category <span id="cname">${catName}</span>
                complexity level <span id="clevel">${levelName}</span> 
with maximal number of questions <span id="maxnum">${questionsCountByCategoryLevel}</span>
</pre>
</div>
<br>
<br>
<br>
<form id="myform" method="post" action="test_preparing" onsubmit="return checkQuestionsNumber()">
Please input a number of questions you want to test: <input id="qnum" type="text" name="qnumber" size=20/>
<input id="submitbutton" type="submit" value="Submit"/>
</form>

</body>
</html>