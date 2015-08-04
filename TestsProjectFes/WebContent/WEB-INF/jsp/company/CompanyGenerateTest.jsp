<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html">
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/company/CompanyMainPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<title>Test generation</title>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("tr")
								.on(
										"click",
										function() {
											var ind = $(this).index();
											$(".category")
													.each(
															function(i) {
																if (this.checked) {
																	document
																			.getElementsByName("level_num")[i].disabled = false;
																} else {
																	document
																			.getElementsByName("level_num")[i].disabled = true;
																}
															});
										});
					});
	function check(login) {
		if (login.length != 9) {
			document.getElementById("e_login").style.display = "inline";
			$("#generate_test").html(
					'<input type="submit" value="Generate test" disabled/>');
		} else {
			document.getElementById("e_login").style.display = "none";
			$("#generate_test").html(
					'<input type="submit" value="Generate test"/>');
		}
	}
</script>
<style type="text/css">
.categoryCheckBox {
	float: left;
}

.inputFormBox {
	float: left;
	margin-left: 2em;
}

.otherButtonsOnPage {
	clear: left;
	margin-top: 4em;
}
</style>
</head>


<body>
			<div id="container">
	 	<div id ="header">
	 		<div id="logo">
	 			
	 		</div>
	 		<div id="search_area">
	 			<input id="text_Area" type="text" placeholder="Search.."/>
	 			<input id="button" type="button" value="search"/>
	 		</div>
	 		
	 	</div>
	 	<div id="nav_area">
                    <ul class="clearfix">
                        <li><a href=".">Home</a></li>
                        <li>
                            <a href="#">Question Maintance <span class="arrow">&#9660;</span></a>
             
                            <ul class="sub-menu">
                                <li><a href="maintenanceadd">Creating questions</a></li>
                                <li><a href="update">Update/delete questions</a></li>
                                <li><a href="otherResursCreationMethod">Adding question from other resources</a></li>
                            </ul>
                        </li>
                        <li><a href="testGeneration">Test generation</a></li>
                        <li><a href="view_results">View test results</a></li>
                        <li><a href=".">FAQ</a></li>
                        <li><a href=".">Contacn us</a></li>
                    </ul>
         </div>

	 	<div id="left_side">
        
        
	 		<div id="testexamples">
               <div>
	 			<h2>Test Examples</h2>
              </div>
	 			<ul>
                
	 				<li><a href=".">JAVA</li></a><hr>
	 				<li><a href=".">C++</li></a><hr>
	 				<li><a href=".">C#</li></a><hr>
	 				<li><a href=".">Android</li></a><hr>
	 				<li><a href=".">Javascript</li></a><hr>
                    <li><a href=".">HTML&CSS</li></a><hr>
                    <li><a href=".">Other Tests</li></a>
	 			</ul>
 		  </div><!--end test examles-->

 	  </div><!--end left_side area-->

		<div id="right_side">
			<div>
				<form action="add_test" id="adding_form_company">
					<div class="categoryCheckBox">
						<script type="text/javascript">
							document.write("${categoryFill}");
						</script>
					</div>
					<div class="inputFormBox">
						SelectQuestion Count:<br> <select name="selectCountQuestions">
							<option value="10">10test</option>
							<!-- for deleting !!! -->
							<option value="30">30</option>
							<option value="40">40</option>
							<option value="50">50</option>
							<option value="60">60</option>
						</select> <br> <br> Please fill data of Person: <br>
						<table>
							<tr>
								<td>Id:</td>
								<td><input type="text" name="personId"
									placeholder="312645798" onkeyup="check(this.value)"
									maxlength="9" size="7" /> <span id="e_login"
									style="display: none; color: #c00;">Not correct</span><br>
								</td>
							</tr>
							<tr>
								<td>FirstName:</td>
								<td><input type="text" name="personName" placeholder="Josh"
									size=20 /></td>
							</tr>
							<tr>
								<td>SurName:</td>
								<td><input type="text" name="personSurname"
									placeholder="Jekson" size=20 /> <br></td>
							</tr>
							<tr>
								<td>E-mail:</td>
								<td><input type="text" name="personEmail" size=20
									placeholder="josh-jakson@mail.com" /> <br></td>
							</tr>
						</table>
						<div id="generate_test">
							<input type="submit" value="Generate test" disabled />
						</div>
					</div>
				</form>

				<div class="otherButtonsOnPage">
					<a href=".">Home</a> &nbsp;&nbsp; <a href="view_results">Test
						Results</a>
				</div>
				<br> <br>
				<script type="text/javascript">
					document.write("${result}");
				</script>
			</div>
			<div id="additional_area"></div>

		</div>
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>
</body>
</html>
