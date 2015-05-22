<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.UserActions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js">	
</script>
<script type="text/javascript">
var codeQuestionIdCount = 1;
$(document).ready(function(){
	$("#CodeTestArea").css("display","none")
	///// AJAX Tast Code Case Request/Response
	$(".send_button").click(function(){							
		var codeInText = $('#codeText').val();		 
		$.ajax({type: "POST",
			url: "handler-user-code",
			data: "userCode=" + codeInText,
			success: function(response){
				// we have the response				
				if(response.status == "SUCCESS"){	
					$("#CodeTestArea").css("display","block");
					$("#CodeTestArea").text(response.result);								 
				}else if(response.status == "ERROR"){  
					alert("code wrong try again!!!");
				}		
			},
			error: function(e){
				alert('Error: ' + e);			
			}
		}); 
	});	
});

</script>
<style type="text/css">
.imageClass{
width: 100%;
height: 100%;
}
.buttons {
    float: left;
	border: 1px solid #0a3c59;
	background: #3e779d;
	background: -webkit-gradient(linear, left top, left bottom, from(#65a9d7),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #65a9d7, #3e779d);
	background: -moz-linear-gradient(top, #65a9d7, #3e779d);
	background: -ms-linear-gradient(top, #65a9d7, #3e779d);
	background: -o-linear-gradient(top, #65a9d7, #3e779d);
	background-image: -ms-linear-gradient(top, #65a9d7 0%, #3e779d 100%);
	padding: 3px 3px;
	-webkit-border-radius: 7px;
	-moz-border-radius: 7px;
	border-radius: 7px;
	-webkit-box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	-moz-box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	text-shadow: #7ea4bd 0 1px 0;
	color: #06426c;
	font-size: 14px;
	font-family: helvetica, serif;
	text-decoration: none;
	vertical-align: middle;
	width: 100px;
	margin: 20px;
}

.buttons:hover {
	border: 1px solid #0a3c59;
	text-shadow: #1e4158 0 1px 0;
	background: #3e779d;
	background: -webkit-gradient(linear, left top, left bottom, from(#65a9d7),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #65a9d7, #3e779d);
	background: -moz-linear-gradient(top, #65a9d7, #3e779d);
	background: -ms-linear-gradient(top, #65a9d7, #3e779d);
	background: -o-linear-gradient(top, #65a9d7, #3e779d);
	background-image: -ms-linear-gradient(top, #65a9d7 0%, #3e779d 100%);
	color: #fff;
}

.buttons:active {
	text-shadow: #1e4158 0 1px 0;
	border: 1px solid #0a3c59;
	background: #65a9d7;
	background: -webkit-gradient(linear, left top, left bottom, from(#3e779d),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #3e779d, #65a9d7);
	background: -moz-linear-gradient(top, #3e779d, #65a9d7);
	background: -ms-linear-gradient(top, #3e779d, #65a9d7);
	background: -o-linear-gradient(top, #3e779d, #65a9d7);
	background-image: -ms-linear-gradient(top, #3e779d 0%, #65a9d7 100%);
	color: #fff;
}
.responseArea{
float: right;
}
</style>
<title>Trainee Test</title>
</head>
<body>
	<table id="table" border="0">
		<!-- creating table table rows and data -->
		<tr>
			<td colspan="2"><h2>${question}</h2></td>
		</tr>
		<tr>
		<td colspan="2"><h3>Description:</h3><pre>${descriptionText}</pre></td>
		</tr>
		<tr>
			<td>
				<form action="UserTestLoop" method="post">				
					<%= UserActions.AutoGenForm() %> 					
				</form>

			</td>
			<td><textarea id="CodeTestArea" class="responseArea" rows="20" cols="25"></textarea></td>
		</tr>
	</table>
	</body>
</html>