<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Insert title here</title>

<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<link
	href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
</head>
<body>
	<div id="conatiner">
		<div id="header">
			<div id="logo"></div>
			<div id="search_area">
				<input id="text_Area" type="text" placeholder="Search.." /> <input
					id="button" type="button" value="search" />
			</div>

		</div>
		<div id="nav_area">
			<ul>
				<li><a href="index.html">Home</li>
				</a>
				<li><a href="UserSignIn.html">User Login</li>
				</a>
				<li><a href="CompanyActions">Company Login</li>
				</a>
				<li><a href="index.html">FAQ</li>
				</a>
				<li><a href="index.html">Contact Us</li>
				</a>
			</ul>
		</div>
		<!--end nav area-->

		<div id="left_side">


			<div id="testexamples">
				<div>
					<h2>Test Examples</h2>
				</div>
				<ul>

					<li><a href="index.html">JAVA</li>
					</a>
					<hr>
					<li><a href="index.html">C++</li>
					</a>
					<hr>
					<li><a href="index.html">C#</li>
					</a>
					<hr>
					<li><a href="index.html">Android</li>
					</a>
					<hr>
					<li><a href="index.html">Javascript</li>
					</a>
					<hr>
					<li><a href="index.html">HTML&CSS</li>
					</a>
					<hr>
					<li><a href="index.html">Other Tests</li>
					</a>
				</ul>
			</div>
			<!--end catagories-->

		</div>
		<!--end left_side area-->

		<div id="right_side">

			<form action="add_test">
				<p>${myResult}</p>
				<br>
				<br> <a href="#" onclick="history.back();">back</a> <br> <br>
			</form>
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