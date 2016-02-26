<%@ page import="tel_ran.tests.services.common.ICommonData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en" data-ng-app="app">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- <script
	src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
-->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<script
	src="static_js/js/viewresultsjs/ViewTestsResultsTemplateBased.js"></script>
<script
	src="static_js/js/viewresultsjs/smart-table.min.js"></script>
<script
	src="static_js/js/viewresultsjs/angular-bootstrap-select.min.js"></script>
<script
	src="static_js/js/viewresultsjs/bootstrap.min.js"></script>
<script
	src="static_js/js/viewresultsjs/ngDialog.min.js"></script>
<script
	src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
<script
	src="static/js_folder/header&&rightmenu_viewresults.js"></script>

<link
	href="static/css_folder/company_styles/viewresults/CompanyViewTestResults.css"
	rel="stylesheet">
<link
	href="http://cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<title>View tests results</title>

</head>
<body>
<div id="right_side_viewresults">

<div class="container">
            <div class="row">
                <div class="container">
                    <div class="panel-group" id="panels1"> 
                        <div class="panel panel-default">
                            <div class="panel-heading">Template selector</div>                             
                            <div class="panel-heading"> 
                                <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#panels1" href="#collapse1">Template selector</a> </h4> 
                            </div>                             
                            <div id="collapse1" class="panel-collapse collapse in"> 
                                <div class="panel-body">Template#1</div>                                 
                            </div>                             
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">Panel heading without title</div>                             
                            <div class="panel-heading"> 
                                <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#panels1" href="#collapse2">                                Collapsible Group Item #2                                </a> </h4> 
                            </div>                             
                            <div id="collapse2" class="panel-collapse collapse"> 
                                <div class="panel-body">                            Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid.                            </div>                                 
                            </div>                             
                        </div>                         
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="container">
                    <table class="table"> 
                        <thead style="display: table-header-group;"> 
                            <tr style="display: table-row;"> 
                                <th>#</th>                                 
                                <th>Name</th>                                 
                                <th style="display: table-cell;">
                                    <div class="btn-group open"> 
                                        <text type="text" class="dropdown-toggle" data-toggle="dropdown" style="display: block;">Common rate&nbsp;&nbsp;
                                            <span class="caret"></span>
                                        </text>                                         
                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="#">Common Rate</a>
                                            </li>
                                            <li class="divider"></li>                                             
                                            <li>
                                                <a href="#">Java</a>
                                            </li>                                             
                                            <li>
                                                <a href="#">JavaScript</a>
                                            </li>                                             
                                            <li>
                                                <a href="#">CSS</a>
                                            </li>                                             
                                        </ul>
                                    </div>
                                </th>                                 
                            </tr>                             
                        </thead>
                        <tbody style="display: table-row-group;"> 
                            <tr> 
                                <td>1</td>                                 
                                <td>Mark</td>                                 
                                <td>50%</td>                                 
                                <td style="display: table-cell;">
                                    <a href="http://">Detailsi</a>
                                </td>                                 
                            </tr>                             
                            <tr> 
                                <td>2</td>                                 
                                <td>Jacob</td>                                 
                                <td>60%</td>                                 
                                <td style="display: table-cell;">
                                    <a href="http://">Detailsi</a>
                                </td>                                 
                            </tr>                             
                            <tr> 
                                <td>3</td>                                 
                                <td>Larry</td>                                 
                                <td style="display: table-cell;">99%</td>                                 
                                <td style="display: table-cell;">
                                    <a href="http://">Detailsi</a>
                                </td>                                 
                            </tr>                             
                        </tbody>
                    </table>
                    <ul class="pagination"> 
                        <li>
                            <a href="#">&laquo;</a>
                        </li>                         
                        <li class="active">
                            <a href="#">1 <span class="sr-only">(current)</span></a>
                        </li>                         
                        <li>
                            <a href="#">2</a>
                        </li>                         
                        <li>
                            <a href="#">3</a>
                        </li>                         
                        <li>
                            <a href="#">&raquo;</a>
                        </li>                         
                    </ul>
                </div>
            </div>
        </div>

</div>
<div data-ng-controller="InitController" data-ng-init="mydata.token = '${token}';">

</div>

<div data-ng-controller='TestController'>
	<p hidden="true"> Token = {{ mydata.token }} </p>
</div>









		
<div id="additional_area"></div>
	<!--end of right area-->



	<div id="footer_area">
		<p>Copyright &copy; 2014 HrTrueTest</p>
	</div>
</body>

</html>