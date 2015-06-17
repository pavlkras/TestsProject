<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company View</title>
<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">

</head>
<body>
		<div id="conatiner">
	 	<div id ="header">
	 		<div id="logo">
	 			
	 		</div>
	 		<div id="search_area">
	 			<input id="text_Area" type="text" placeholder="Search.."/>
	 			<input id="button" type="button" value="search"/>
	 		</div>
	 		
	 	</div>
	 	<div id="nav_area">
	 			<ul>
	 				<li><a href=".">Home</li></a>
	 				<li><a href="login">User Login</li></a>
	 				<li><a href="CompanyActions">Company Login</li></a>
	 				<li><a href=".">FAQ</li></a>
	 				<li><a href=".">Contact Us</li></a>
	 			</ul>
	 	</div><!--end nav area-->

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
 		  </div><!--end tesst examles-->

 	  </div><!--end left_side area-->

		</div>
		<!--end left_side area-->

		<div id="right_side">

			<div id="formDiv">
				<form action="query_processing">
					Please type Company Name for search: <input type="text"
						name="jpaStr" size=50 value="">
					<!-- Select c from Company c  -->
					<input type="submit" value="SEARCH" name="adding" />
					<button value="" name="adding">VIEW ALL</button>
				</form>
				<br>
				<p>${myResult}</p>
				<br> <a href="companyadd">Add new Company</a> <br> <br>

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