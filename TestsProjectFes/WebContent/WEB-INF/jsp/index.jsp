<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function feedAltText() {
		for (var i = 0; i < document.getElementsByTagName("img").length; i++) {
			var EDIT_Q = document.getElementsByTagName("img")[i];
			var att = document.createAttribute("alt");
			att.value = ".";
			EDIT_Q.setAttributeNode(att);
		}
	}
</script>
<style type="text/css">
.firsthomecontent a {
	float: left;
}

.homecontent {
	background-color: #fff;
	border: 1px solid #ffc0cb;
	margin: 5px;
	padding: 10px;
	width: 150px;
	border-radius: 10px
}

.h2 {
	font-family: erdana, helvetica, arial, sans-serif;
	font-size: 25px;
	color: #610B38;
	font-weight: 400;
}

a {
	color: green;
	text-decoration: none
}

a:hover {
	text-decoration: underline;
	color: red;
}

.homespan {
	font: 21px times new roman;
	padding: 5px;
	float: right;
}

.gra1 {
	border: 1px solid #ffc0cb;
	border-radius: 10px;
	background: -webkit-linear-gradient(#FAFAE5, white);
	background: -o-linear-gradient(#FAFAE5, white);
	background: -moz-linear-gradient(#FAFAE5, white);
	background: linear-gradient(#FAFAE5, white);
	height: 11em;
}
.gra2 {
	border: 1px solid #ffc0cb;
	border-radius: 10px;
	background: -webkit-linear-gradient(#FAFAE5, white);
	background: -o-linear-gradient(#FAFAE5, white);
	background: -moz-linear-gradient(#FAFAE5, white);
	background: linear-gradient(#FAFAE5, white);
	height: 23em;
}

img {
	max-width: 100%;
	height: auto;
}
</style>
<title>Index Page</title>
</head>
<body onload="feedAltText()">
	<table>
		<tr>
			<td>
				<h2 class="h2">Library of popular programming languages Links</h2>				
			</td>
		</tr>
		<tr>
			<td>
				<div class="gra1">
					<h2 class="h2">Popular Tutorials</h2>
					<div class="firsthomecontent">
						<a href="http://docs.oracle.com/javase/tutorial/">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="Java tutorial"><span
									class="homespan">Java</span>
							</div>
						</a> <a href="http://www.w3schools.com/js/">
							<div class="homecontent">
								<img src="images/logo/javascripthome.png"
									alt="JavaScript tutorial"><span class="homespan">JavaScript</span>
							</div>
						</a> <a href="http://www.w3schools.com/sql/">
							<div class="homecontent">
								<img src="images/logo/sqlhome.png" alt="SQL tutorial"><span
									class="homespan">SQL</span>
							</div>
						</a> <a href="http://startandroid.ru/ru/">
							<div class="homecontent">
								<img src="images/logo/androidhome.png" alt="Android tutorial"><span
									class="homespan">Android</span>
							</div>
						</a> <a href="http://www.cprogramming.com/tutorial/c-tutorial.html">
							<div class="homecontent">
								<img src="images/logo/clanguagehome.png"
									alt="C Language tutorial"><span class="homespan">C
									Lang-</span>
							</div>
						</a><a href="http://www.w3schools.com/html/">
							<div class="homecontent">
								<img src="images/logo/html-tutorial.png" alt="html tutorial"><span
									class="homespan">HTML</span>
							</div>
						</a> <a href="http://www.w3schools.com/css/">
							<div class="homecontent">
								<img src="images/logo/css3.jpg" alt="css tutorial"><span
									class="homespan">CSS</span>
							</div>
						</a> <a href="https://docs.python.org/2/tutorial/">
							<div class="homecontent">
								<img src="images/logo/pythonhome.png" alt="Python tutorial"><span
									class="homespan">Python</span>
							</div>
						</a> <a href="http://www.w3schools.com/ajax/">
							<div class="homecontent">
								<img src="images/logo/ajaxhome.png" alt="AJAX tutorial"><span
									class="homespan">AJAX</span>
							</div>
						</a> <a href="http://www.tutorialspoint.com/cloud_computing/">
							<div class="homecontent">
								<img src="images/logo/cloudhome.png" alt="Cloud tutorial"><span
									class="homespan">Cloud</span>
							</div>
						</a> <a href="http://www.w3schools.com/webservices/">
							<div class="homecontent">
								<img src="images/logo/web-services.png"
									alt="Web Services tutorial"><span class="homespan">Web
									Serv-</span>
							</div>
						</a>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="gra1">
					<h2 class="h2">Java Technology Tutorials</h2>
					<div class="firsthomecontent">
						<a href="http://docs.oracle.com/javase/tutorial/">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="Core Java tutorial"><span
									class="homespan">Core Java</span>
							</div>
						</a> <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnafd.html">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="Java Servlet tutorial"><span
									class="homespan">Servlet</span>
							</div>
						</a> <a href="http://docs.oracle.com/javaee/5/tutorial/doc/bnagx.html">
							<div class="homecontent">
								<img src="images/logo/jsphome.png" alt="Java JSP tutorial"><span
									class="homespan">JSP</span>
							</div>
						</a> <a href="http://docs.oracle.com/javaee/5/tutorial/doc/bnblr.html">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="EJB tutorial"><span
									class="homespan">EJB</span>
							</div>
						</a> <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gijti.html">
							<div class="homecontent">
								<img src="images/logo/web-services.png"
									alt="Java Web Services tutorial"><span class="homespan">Java
									WS</span>
							</div>
						</a><a href="http://struts.apache.org/docs/getting-started.html">
							<div class="homecontent">
								<img src="images/logo/strutshome.png" alt="Struts tutorial"><span
									class="homespan">Struts</span>
							</div>
						</a> <a
							href="https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/tutorial.html">
							<div class="homecontent">
								<img src="images/logo/hibernatehome.png"
									alt="Hibernate tutorial"><span class="homespan">Hibernate</span>
							</div>
						</a> <a href="https://spring.io/guides">
							<div class="homecontent">
								<img src="images/logo/springhome.png" alt="Spring tutorial"><span
									class="homespan">Spring</span>
							</div>
						</a> <a
							href="http://www.oracle.com/technetwork/java/index-138643.html">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="Java Mail tutorial"><span
									class="homespan">Java Mail</span>
							</div>
						</a> <a href="http://www.tutorialspoint.com/design_pattern/">
							<div class="homecontent">
								<img src="images/logo/javahome.png"
									alt="Java Design Pattern tutorial"><span class="homespan">D
									Pattern</span>
							</div>
						</a><a href="http://www.tutorialspoint.com/junit/">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="JUnit tutorial"><span
									class="homespan">Junit</span>
							</div>
						</a> <a
							href="http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html">
							<div class="homecontent">
								<img src="images/logo/strutshome.png" alt="Maven tutorial"><span
									class="homespan">Maven</span>
							</div>
						</a> <a href="http://jsoup.org/cookbook/">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="Maven tutorial"><span
									class="homespan">Jsoup</span>
							</div>
						</a> <a href="http://docs.oracle.com/javase/tutorial/jaxb/intro/">
							<div class="homecontent">
								<img src="images/logo/javahome.png" alt="JAXB tutorial"><span
									class="homespan">JAXB</span>
							</div>
						</a>
					</div>
				</div>
			</td>
			<td rowspan="2">
				<div class="gra2">
				<h2 class="h2">Application Resources</h2>
					<a href="Personal_result_view">
						<div class="homecontent">
							<img src="images/logo/interviewhome.png"><span
								class="homespan">Interview</span>
						</div>
					</a><a href="Maintenance" name="inputString" value="Login">
						<div class="homecontent">
							<img src="images/logo/interviewhome.png"><span
								class="homespan">Login</span>
						</div>
					</a><a href="PersonalActions">
						<div class="homecontent">
							<img src="images/logo/interviewhome.png"><span
								class="homespan">Sign Up</span>
						</div>
					</a><a href="CompanyActions">
						<div class="homecontent">
							<img src="images/logo/interviewhome.png"><span
								class="homespan">Company</span>
						</div>
					</a> <a href="web_cam">
						<div class="homecontent">
							<img src="images/logo/interviewhome.png"> <span
								class="homespan">Cam Test</span>
						</div>
					</a>
					
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="gra1">
					<h2 class="h2">Miscellaneous Topics</h2>
					<div class="firsthomecontent">
						<a href="http://stackoverflow.com/">
							<div class="homecontent">
								<img src="images/logo/forumhome3.png" alt="Forum tutorial"><span
									class="homespan">Java Forum</span>
							</div>
						</a> <a
							href="http://tomcat.apache.org/tomcat-7.0-doc/jasper-howto.html">
							<div class="homecontent">
								<img src="images/logo/javacompiler.png"
									alt="Online java compiler"> <span class="homespan">J
									Compiler</span>
							</div>
						</a><a href="http://stackoverflow.com/">
							<div class="homecontent">
								<img src="images/logo/quizhome.png" alt="Online forum"><span
									class="homespan">Forum</span>
							</div>
						</a> <a href="http://localhost:8080/TestsProjectFes/jobSeeker_test_preparing_click_event?8">
							<div class="homecontent"><!-- 
								<img src="images/logo/interviewhome.png"
									alt="Interview Questions"><span class="homespan"> -->Test Link click case<!-- </span> -->
							</div>
						</a> <a href="http://www.javatpoint.com/free-java-projects">
							<div class="homecontent">
								<img src="images/logo/projecthome.png" alt="Free Projects"><span
									class="homespan">Projects</span>
							</div>
						</a> <a href="http://www.javatpoint.com/full-form">
							<div class="homecontent">
								<img src="images/logo/full-form.jpg" alt="Full Form"><span
									class="homespan">Acronyms</span>
							</div>
						</a>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
</body>
</html>




