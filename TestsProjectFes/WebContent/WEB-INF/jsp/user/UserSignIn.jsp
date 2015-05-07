<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link
	href='<c:url value="/static/css_folder/user_styles/IndexPage.css"></c:url>'
	rel="stylesheet">
<script type="text/javascript">
	$(document).ready(function() {
		$(".miscStyle").css("display", "block");
		$("#h2miscel").hover(function() {
			$(".miscStyle").css("display", "block");

			$("#popularLincs").css("display", "none");
			$(".javaTech").css("display", "none");
		}, function() {
			///$("#popularLincs").css("visibility", "hidden");
		});
		//
		$("#h2popular").hover(function() {
			$("#popularLincs").css("display", "block");

			$(".miscStyle").css("display", "none");
			$(".javaTech").css("display", "none");
		}, function() {
			///$(".miscStyle").css("visibility", "hidden");
		});
		//
		$("#h2javaTech").hover(function() {
			$(".javaTech").css("display", "block");

			$("#popularLincs").css("display", "none");
			$(".miscStyle").css("display", "none");

		}, function() {
			///$(".miscStyle").css("visibility", "hidden");
		});
		//
		$("#h2login").hover(function() {
			$(".formDiv").css("display", "block");			
			
			$(".javaTech").css("display", "none");
			$("#popularLincs").css("display", "none");
			$(".miscStyle").css("display", "none");

		}, function() {
			///$(".formDiv").css("display", "none");
		});

	});
</script>
<title>Home Page</title>
</head>
<body>
	<div>
		<ul style="list-style: none; display: block;">
			<li id="h2popular"><h2>Popular Tutorials Links</h2></li>
			<li id="h2javaTech"><h2>Java Technology Tutorials</h2></li>
			<li id="h2miscel"><h2>Miscellaneous Topics</h2></li>
			<li><a href="CompanyActions"><h2>Company LogIn</h2></a></li>
		</ul>
	</div>
	<div class="formDiv">
		<h2>User Login</h2>
		<form action="login_action" method="post" name="user_login_form">
			Mail &nbsp; &nbsp; &nbsp; <span class="ipsForm_required">*</span> <input
				type="email" name="userEmail" id="email" /><br> <br>
			Password <span class="ipsForm_required">*</span><input
				type="password" name="password" id="first_password" /><br> <br>
			<input type="submit" class="buttonStyle" name="login" value="Login" />
			<input type="submit" name="sign_up" value="Registration" />
			<p style='color: red; text-align: center;'>
				<script type="text/javascript">
					document.write("${logedUser}");
				</script>
				&nbsp;
			</p>
		</form>
	</div>
	<div id="linksContent" class="linksContent">
		<div class="firsthomecontent" id="popularLincs">
			<a href="http://docs.oracle.com/javase/tutorial/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="Java tutorial"></span><span
						class="homespan">Java</span>
				</div>
			</a> <a href="http://www.w3schools.com/js/">
				<div class="homecontent">
					<img src="static/images/logo/javascripthome.png"
						alt="JavaScript tutorial"><span class="homespan">JavaScript</span>
				</div>
			</a> <a href="http://www.w3schools.com/sql/">
				<div class="homecontent">
					<img src="static/images/logo/sqlhome.png" alt="SQL tutorial"><span
						class="homespan">SQL</span>
				</div>
			</a> <a href="http://startandroid.ru/ru/">
				<div class="homecontent">
					<img src="static/images/logo/androidhome.png"
						alt="Android tutorial"><span class="homespan">Android</span>
				</div>
			</a> <a href="http://www.cprogramming.com/tutorial/c-tutorial.html">
				<div class="homecontent">
					<img src="static/images/logo/clanguagehome.png"
						alt="C Language tutorial"><span class="homespan">C
						Lang-</span>
				</div>
			</a><a href="http://www.w3schools.com/html/">
				<div class="homecontent">
					<img src="static/images/logo/html-tutorial.png" alt="html tutorial"><span
						class="homespan">HTML</span>
				</div>
			</a> <a href="http://www.w3schools.com/css/">
				<div class="homecontent">
					<img src="static/images/logo/css3.jpg" alt="css tutorial"><span
						class="homespan">CSS</span>
				</div>
			</a> <a href="https://docs.python.org/2/tutorial/">
				<div class="homecontent">
					<img src="static/images/logo/pythonhome.png" alt="Python tutorial"><span
						class="homespan">Python</span>
				</div>
			</a> <a href="http://www.w3schools.com/ajax/">
				<div class="homecontent">
					<img src="static/images/logo/ajaxhome.png" alt="AJAX tutorial"><span
						class="homespan">AJAX</span>
				</div>
			</a> <a href="http://www.tutorialspoint.com/cloud_computing/">
				<div class="homecontent">
					<img src="static/images/logo/cloudhome.png" alt="Cloud tutorial"><span
						class="homespan">Cloud</span>
				</div>
			</a> <a href="http://www.w3schools.com/webservices/">
				<div class="homecontent">
					<img src="static/images/logo/web-services.png"
						alt="Web Services tutorial"><span class="homespan">Web
						Serv-</span>
				</div>
			</a>
		</div>

		<div class="firsthomecontent javaTech">
			<a href="http://docs.oracle.com/javase/tutorial/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="Core Java tutorial"><span
						class="homespan">Core Java</span>
				</div>
			</a> <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnafd.html">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png"
						alt="Java Servlet tutorial"><span class="homespan">Servlet</span>
				</div>
			</a> <a href="http://docs.oracle.com/javaee/5/tutorial/doc/bnagx.html">
				<div class="homecontent">
					<img src="static/images/logo/jsphome.png" alt="Java JSP tutorial"><span
						class="homespan">JSP</span>
				</div>
			</a> <a href="http://docs.oracle.com/javaee/5/tutorial/doc/bnblr.html">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="EJB tutorial"><span
						class="homespan">EJB</span>
				</div>
			</a> <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gijti.html">
				<div class="homecontent">
					<img src="static/images/logo/web-services.png"
						alt="Java Web Services tutorial"><span class="homespan">Java
						WS</span>
				</div>
			</a><a href="http://struts.apache.org/docs/getting-started.html">
				<div class="homecontent">
					<img src="static/images/logo/strutshome.png" alt="Struts tutorial"><span
						class="homespan">Struts</span>
				</div>
			</a> <a
				href="https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/tutorial.html">
				<div class="homecontent">
					<img src="static/images/logo/hibernatehome.png"
						alt="Hibernate tutorial"><span class="homespan">Hibernate</span>
				</div>
			</a> <a href="https://spring.io/guides">
				<div class="homecontent">
					<img src="static/images/logo/springhome.png" alt="Spring tutorial"><span
						class="homespan">Spring</span>
				</div>
			</a> <a href="http://www.oracle.com/technetwork/java/index-138643.html">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="Java Mail tutorial"><span
						class="homespan">Java Mail</span>
				</div>
			</a> <a href="http://www.tutorialspoint.com/design_pattern/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png"
						alt="Java Design Pattern tutorial"><span class="homespan">D
						Pattern</span>
				</div>
			</a><a href="http://www.tutorialspoint.com/junit/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="JUnit tutorial"><span
						class="homespan">Junit</span>
				</div>
			</a> <a
				href="http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html">
				<div class="homecontent">
					<img src="static/images/logo/strutshome.png" alt="Maven tutorial"><span
						class="homespan">Maven</span>
				</div>
			</a> <a href="http://jsoup.org/cookbook/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="Maven tutorial"><span
						class="homespan">Jsoup</span>
				</div>
			</a> <a href="http://docs.oracle.com/javase/tutorial/jaxb/intro/">
				<div class="homecontent">
					<img src="static/images/logo/javahome.png" alt="JAXB tutorial"><span
						class="homespan">JAXB</span>
				</div>
			</a><a href="http://stackoverflow.com/">
				<div class="homecontent">
					<img src="static/images/logo/forumhome3.png" alt="Forum tutorial"><span
						class="homespan">Java Forum</span>
				</div>
			</a><a href="http://tomcat.apache.org/tomcat-7.0-doc/jasper-howto.html">
				<div class="homecontent">
					<img src="static/images/logo/javacompiler.png"
						alt="Online java compiler"> <span class="homespan">J
						Compiler</span>
				</div>
			</a> <a href="http://www.javatpoint.com/free-java-projects">
				<div class="homecontent">
					<img src="static/images/logo/projecthome.png" alt="Free Projects"><span
						class="homespan">Projects</span>
				</div>
			</a> <a href="http://www.javatpoint.com/full-form">
				<div class="homecontent">
					<img src="static/images/logo/full-form.jpg" alt="Full Form"><span
						class="homespan">Acronyms</span>
				</div>
			</a>
		</div>

		<div class="firsthomecontent miscStyle">
			<a href="maintenanceadd">
				<div class="homecontent">
					<img src="static/images/logo/quizhome.png" alt="Online forum"><span
						class="homespan">Add Question</span>
				</div>
			</a><a href="Ma">
				<div class="homecontent">
					<img src="static/images/logo/interviewhome.png" alt="admin panel"><span
						class="homespan">Admins</span>
				</div>
			</a>
		</div>
	</div>
</body>
</html>




