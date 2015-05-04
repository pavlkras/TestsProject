<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link
	href='<c:url value="/static/css_folder/maintenance_styles/CreateQuestionPage.css"></c:url>'
	rel="stylesheet"> 
<script type="text/javascript">
$(document).ready(function(){
	$("#addingForm").keyup(function(){
	 var question_text = document.question_adding_form.questionText.value;	
		if(question_text.length > 3){
			var cat = document.question_adding_form.category.value;	
			if(cat.length != 0){
				var answer = document.question_adding_form.correctAnswer.value;	
				var codeT = document.question_adding_form.codeText.value;
				if(answer.length == 1 || codeT.length != 0){							
					 document.getElementsByName("button_send")[0].disabled = false;
				}else{
					document.getElementsByName("button_send")[0].disabled = true;
				}
			}else{
				document.getElementsByName("button_send")[0].disabled = true;
			}
		}else{
			 document.getElementsByName("button_send")[0].disabled = true;
		}
	});
	////
});
</script>
<title>ADDING</title>
</head>
<body>
	<p>Creating new Question</p>
	<form action="add_actions" method="post" id="addingForm"
		name="question_adding_form">
		<table>
			<tr>
				<th class="thFloatText">Input Question text</th>
				<td class="tdFloatContent"><textarea name="questionText"
						id="questionText" rows="3"
						placeholder="Type here question text or description for image"></textarea></td>
			</tr>
			<tr>
				<th class="thFloatText">Question Index Number</th>
				<td class="tdFloatContent"><input type="text"
					name="questionIndex" value="0"></td>
			</tr>
			<tr>
				<th class="thFloatText">Insert Category</th>
				<td class="tdFloatContent"><input type="text" name="category"
					placeholder="Java,Html" /></td>
			</tr>
			<tr>
				<th class="thFloatText">Select Level</th>
				<td class="tdFloatContent">1<input type="radio"
					name="levelOfDifficulty" value=1 checked="checked"> 2<input
					type="radio" name="levelOfDifficulty" value=2> 3<input
					type="radio" name="levelOfDifficulty" value=3> 4<input
					type="radio" name="levelOfDifficulty" value=4> 5<input
					type="radio" name="levelOfDifficulty" value=5>
				</td>
			</tr>
			<tr>
				<th class="thFloatText">Input Code by pattern<div>
				<pre style="color: red; ">
				class main{
				NewCode() test = new NewCode();
				test.Foo();
				}
				
				public class NewCode{
				public NewCode(){}
				
				public Foo(){
				  //any action				
				}
				</pre></div></th>
				<td class="tdFloatContent"><textarea name="codeText" rows="14" cols="23"></textarea></td>
			</tr>			
			<tr><td class="tdFloatContent" colspan="2"><div>Test Code</div></td>
			</tr>
			
			<tr id="answersTr">
				<th class="thFloatText">Input Answer</th>
				<td class="tdFloatContent">A. <textarea name="at1"></textarea><br>
					B. <textarea name="at2"></textarea><br> C. <textarea
						name="at3"></textarea><br> D. <textarea name="at4"></textarea><br>
				</td>
			</tr>
			<tr>
				<th class="thFloatText">Input Char a right question answer</th>
				<td class="tdFloatContent"><input type="text"
					name="correctAnswer" placeholder="D">
				</td>
			</tr>
			<tr>
				<th class="thFloatText">Input number answers on image</th>
				<td class="tdFloatContent"><input type="text"
					name="numberAnswersOnPicture" value="0"></td>
			</tr>
			<tr id="imageLinkTr">
				<th class="thFloatText">Image Link</th>
				<td class="tdFloatContent"><input type="text" name="imageLink"
					placeholder="/s7d9dejd78f6t4th47.jpg" /></td>
			</tr>
			<tr>
				<th colspan="2"><input name="button_send" id="button_send"
					type="submit" value="Add To DataBase" disabled></th>
			</tr>
		</table>
	</form>
	<br>

	<script type="text/javascript">
		document.write("${result}");
	</script>
	<a class="myButton" href='.'>home</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a class="myButton" href='update'>update (link for erase)</a>
</body>
</html>
