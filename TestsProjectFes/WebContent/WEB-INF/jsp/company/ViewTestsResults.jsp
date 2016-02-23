<%@page import="tel_ran.tests.services.common.ICommonData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en" ng-app="app" ng-controller="InputController"
	ng-init="token='${token}'">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/angular-strap/2.1.2/angular-strap.min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/angular-strap/2.1.2/angular-strap.tpl.min.js"></script>
<link
	href="/TestsProjectFes/static/css_folder/company_styles/viewresults/CompanyViewTestResults.css"
	rel="stylesheet">
<link
	href="/TestsProjectFes/static/css_folder/company_styles/viewresults/ngDialog.min.css"
	rel="stylesheet">
<link
	href="/TestsProjectFes/static/css_folder/company_styles/viewresults/ngDialog-theme-default.min.css"
	rel="stylesheet">
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/ViewTestsResults.js"></script>
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/smart-table.min.js"></script>
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/angular-bootstrap-select.min.js"></script>
<script src="/TestsProjectFes/static_js/js/viewresultsjs/transition.js"></script>
<script src="/TestsProjectFes/static_js/js/viewresultsjs/collapse.js"></script>
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/ui-utils.min.js"></script>
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/bootstrap.min.js"></script>
<script src="/TestsProjectFes/static_js/js/viewresultsjs/date-parser.js"></script>
<script
	src="/TestsProjectFes/static_js/js/viewresultsjs/ngDialog.min.js"></script>
<script
	src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
<link href="http://mgcrea.github.io/angular-strap/styles/libs.min.css"
	rel="stylesheet">
<!-- Code Highlighter  -->
<link rel="stylesheet"
	href="/TestsProjectFes/static/css_folder/company_styles/viewresults/prism.css">
<script src="/TestsProjectFes/static_js/js/viewresultsjs/prism.js"></script>
<!-- EOF Code Highlighter  -->


<link
	href="http://cdn.jsdelivr.net/bootstrap/3.3.4/css/bootstrap.min.css"
	rel="stylesheet">

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
<title>View tests results</title>
</head>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="static/js_folder/header&&rightmenu_viewresults.js">
</script>

</head>
<body>
	<div id="container">

		<div id="right_side_viewresults">

			<div class="container-fluid">
				<div class="row">
					<form ng-submit="submit()">
						<div class="container">
							<h3 class="page-header">View tests results</h3>
							<div class='col-md-3'>
								<div class="form-group">
									<select ng-model="mode" name="mode"
										class="selectpicker form-control"
										title="Please select a mode ...">
										<option
											ng-click="changed_to_mode('all', '/all_tests_results');">All
											tests</option>
										<option
											ng-click="changed_to_mode('range', '/tests_results_by_dates')">Data
											Range</option>
										<option
											ng-click="changed_to_mode('id', '/tests_results_by_person')">For
											Some Person ID</option>
									</select>
								</div>
							</div>
							<div ng-show='display.calendar'>
								<div class='col-md-3 text-left'>
									<div class="form-group">
										<div class='input-group date'>
											<input class="form-control" data-date-format="yyyy-MM-dd"
												data-max-date="today" ng-model="dateFrom"
												ng-required="display.calendar" data-max-date="{{dateTo}}"
												placeholder="From" bs-datepicker type="text"> <span
												class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"> </span>
											</span>
										</div>
									</div>
								</div>
								<div class='col-md-3'>
									<div class="form-group">
										<div class='input-group date'>
											<input id="dateTo" data-date-format="yyyy-MM-dd"
												data-max-date="today" class="form-control" ng-model="dateTo"
												ng-required="display.calendar" data-min-date="{{dateFrom}}"
												placeholder="Until" bs-datepicker type="text"> <span
												ng-click="$('dateTo').focus();" class="input-group-addon"><span
												class="glyphicon glyphicon-calendar"></span> </span>
										</div>
									</div>
								</div>
							</div>

							<div ng-show='display.id' class='col-md-2'>
								<div class="form-group">
									<input type="number" ng-model="personID" name="personID"
										ng-minlength="1" class="form-control" placeholder="Person ID">
								</div>
							</div>
							<!--	<input type="text" ng-show="false" ng-model="token" ng-init="token = 'Comp1'">  -->
							<div class='col-md-1'>
								<button ng-disabled="isButtonDisabled" type="submit"
									class="btn btn-primary">Submit</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="container">
						<div class="table-responsive">
							<table st-table="rowCollection" st-safe-src="results"
								class="table table-striped table-hover ">
								<thead>
									<tr>
										<th class="text-left" st-ratio="20" st-sort="personName">First Name</th>
										<th class="text-left" st-ratio="20" st-sort="personSurname">Second Name</th>
										<th class="text-left" st-ratio="20" st-sort="testDate">Test Date</th>
										<th class="text-left" st-ratio="20" st-sort="testCategory">% Of Right Answers</th>
										<th class="text-left" st-ratio="20">Details</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="x in rowCollection">
										<td class="text-left" st-ratio="20">{{ x.personName }}</td>
										<td class="text-left" st-ratio="20">{{ x.personSurname }}</td>
										<td class="text-left" st-ratio="20">{{ x.testDate }}</td>
										<td class="text-left" st-ratio="20">{{ x.persentOfRightAnswers }}</td>
										<td class="text-left" st-ratio="20" ng-click="showDetails(x.testid)"><a href="#">Details</a></td>
									</tr>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" class="text-center">
											<div st-items-by-page="10" st-pagination=""></div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<script type="text/ng-template" id="testDetails">
<div class="ngdialog ngdialog-overlay">
<div class="ngdialog-content">
 <div class="bs-example">    
	<div class="panel panel-primary">
		<a href="#"> 		
			<div class="panel-heading" ng-click="showTestDetails('details')">
				<h3 id="panel-title" class="panel-title">Test details<a class="anchorjs-link" href="#panel-title"></a></h3>
			</div>
		</a>
		<div class="panel-body" ng-show="display.test_details">
    		<ul class="list-group">
      			<li class="list-group-item">
        			<span class="badge">{{testDetails.duration}}</span>
        			duration
	      		</li>
    	  		<li class="list-group-item">
        			<span class="badge">{{testDetails.amountOfCorrectAnswers}}</span>
        			amountOfCorrectAnswers
	      		</li>
				<li class="list-group-item">
        			<span class="badge">{{testDetails.amountOfQuestions}}</span>
        			amountOfQuestions
	      		</li>
    	  		
			</ul>
		</div>
	</div>
	<div class="panel panel-primary">
		<a href="#">
			<div class="panel-heading" ng-click="showTestDetails('camera_snapshots')">
				<h3 id="panel-title" class="panel-title">Camera snapshots<a class="anchorjs-link" href="#panel-title"></a></h3>
			</div>
		</a>
		<div class="panel-body" ng-show="display.pictures">
			<p ng-repeat="value in testDetails.pictures">
				<img class="img-thumbnail img-previewSize" ng-src="{{value}}"/>
			</p>
		</div>
	</div>
	<div class="panel panel-primary">
		<a href="#">
			<div class="panel-heading" ng-click="showTestDetails('questions')">
				<h3 id="panel-title" class="panel-title">Test Questions<a class="anchorjs-link" href="#panel-title"></a></h3>
			</div>
		</a>
		<div class="panel-body" ng-show="display.questions">
			<div ng-repeat="question_ in testDetails.questions">
				<div class="container-fluid">
				<ul class="list-group">
	      			<li class="list-group-item">
    	    			<h3>
							<a ng-href="#" ng-click="showQuestion(question_.questionId)">Question {{question_.index + 1}}</a>
						</h3>
						Metacategory: {{question_.metaCategory}}
						<br>
						Status:	<span class="label {{getProcessingResultStyle(question_.statusString)}}">{{question_.statusString}}</span>
	      			</li>
				</ul>
				</div>
			</div>
		</div> 
	</div>
 </div>
</div>
</div>
</script>


<script type="text/ng-template" id="questionDetails">
<div class="ngdialog ngdialog-overlay">
<div class="ngdialog-content">
 <div class="bs-example">    
	<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 id="panel-title" class="panel-title">Question Details<a class="anchorjs-link" href="#panel-title"></a></h3>
			</div>		
	</div>
 </div>
</div>
</div>
</script>
		
		
<div id="additional_area"></div>
	</div>
	<!--end of right area-->



	<div id="footer_area">

		<p>Copyright &copy; 2014 HrTrueTest</p>
	</div>
	</div>
</body>

</html>