<%@page import="tel_ran.tests.services.common.ICommonData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" ng-app="app" ng-controller="InputController" ng-init="token='${token}'">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/angular-strap/2.1.2/angular-strap.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/angular-strap/2.1.2/angular-strap.tpl.min.js"></script>
<script src="/TestsProjectFes/static_js/js/CompanyViewTestsResults.js"></script>
<script src="/TestsProjectFes/static_js/js/smart-table.min.js"></script>
<script src="/TestsProjectFes/static_js/js/angular-bootstrap-select.min.js"></script>
<script src="/TestsProjectFes/static_js/js/transition.js"></script>
<script src="/TestsProjectFes/static_js/js/collapse.js"></script>
<script src="/TestsProjectFes/static_js/js/ui-utils.min.js"></script>
<script src="/TestsProjectFes/static_js/js/bootstrap.min.js"></script>
<script src="/TestsProjectFes/static_js/js/date-parser.js"></script>
<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
<!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment.min.js"></script> -->
<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script> -->
<!-- <script src="http://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/d004434a5ff76e7b97c8b07c01f34ca69e635d97/src/js/bootstrap-datetimepicker.js"></script>-->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">       
<!--  <link href="http://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/d004434a5ff76e7b97c8b07c01f34ca69e635d97/build/css/bootstrap-datetimepicker.css" rel="stylesheet">-->

<link href="/TestsProjectFes/static/css_folder/css/CompanyViewTestResults.css" rel="stylesheet">
<title>View tests results</title>
</head>
<body>
	<div class="container-fluid">
		
		<div class="row">
			<form ng-submit="submit()" >
				<div class="container">
				<h3 class="page-header">View tests results</h3>
					<div class='col-md-3'>
						<div class="form-group"> 			
							<select ng-model="mode" name="mode" class="selectpicker form-control" title="Please select a mode ...">
								<option ng-click="changed_to_mode('all', '/all_tests_results');">All tests</option>
								<option ng-click="changed_to_mode('range', '/tests_results_by_dates')">Data Range</option>
								<option ng-click="changed_to_mode('id', '/tests_results_by_person')">For Some Person ID</option>
							</select>
						</div>
					</div>
					<div ng-show='display.calendar'>
						<div class='col-md-3 text-left' >
							<div class="form-group">
								<div class='input-group date'>
									<input class="form-control" data-date-format="yyyy-MM-dd" data-max-date="today" ng-model="dateFrom" data-max-date="{{dateTo}}" placeholder="From" bs-datepicker type="text">
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar">
										</span>
									</span>
								</div>
							</div>
						</div>
						<div class='col-md-3'>
							<div class="form-group">
								<div class='input-group date'>
									<input id="dateTo" data-date-format="yyyy-MM-dd"  data-max-date="today" class="form-control" ng-model="dateTo" data-min-date="{{dateFrom}}" placeholder="Until" bs-datepicker type="text">
									<span ng-click="$('dateTo').focus();" class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div ng-show='display.id' class='col-md-2'>
					<div class="form-group">
						<input type="number" ng-model="personID" name="personID" ng-minlength="1" class="form-control" placeholder="Person ID">
					</div>
				</div>
			<!--	<input type="text" ng-show="false" ng-model="token" ng-init="token = 'Comp1'">  -->
				<div class='col-md-1'>
					<button ng-disabled="isButtonDisabled" type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>
	</div>
	<div class="row">
		<div class="container">
		<div class="table-responsive">
			<table st-table="rowCollection" st-safe-src="results" class="table table-striped table-hover ">
				<thead>
					<tr>
						<th st-ratio="20" st-sort="personName">First Name</th>
						<th st-ratio="20" st-sort="personSurname">Second Name</th>
						<th st-ratio="15" st-sort="testCategory">Test Category</th>
						<th st-ratio="15" st-sort="testDate">Test Date</th>
						<th st-ratio="15" st-sort="testName">Test Name</th>
						<th st-ratio="15">Details</th>
					</tr>
				</thead>
				<tbody>
				  <tr ng-repeat="x in rowCollection">
				    <td st-ratio="20">{{ x.personName }}</td>
				    <td st-ratio="20">{{ x.personSurname }}</td>
				    <td st-ratio="15">{{ x.testCategory }}</td>
				    <td st-ratio="15">{{ x.testDate }}</td>
				    <td st-ratio="15">{{ x.testName }}</td>
				    <td st-ratio="15" ng-click="showDetails(x.testid)"><a href="#">Details</a></td>
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
</body>
</html>