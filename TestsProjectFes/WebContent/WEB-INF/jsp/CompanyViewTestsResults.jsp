<%@page import="tel_ran.tests.services.common.ICommonData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">
<!-- <html lang="en" class="ng-app:myapp" id="ng-app" ng-app="myapp" xmlns:ng="http://angularjs.org"> -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<script src="/TestsProjectFes/static_js/js/bootstrap.min.js"></script>
<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
<script src="/TestsProjectFes/static_js/js/angular-bootstrap-select.min.js"></script>
<script src="/TestsProjectFes/static_js/js/angular-resource.js"></script>
<script src="/TestsProjectFes/static_js/js/transition.js"></script>
<script src="/TestsProjectFes/static_js/js/collapse.js"></script>
<script src="/TestsProjectFes/static_js/js/ui-utils.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment.min.js"></script>
<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script> -->
<script src="http://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/d004434a5ff76e7b97c8b07c01f34ca69e635d97/src/js/bootstrap-datetimepicker.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">       
<link href="http://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/d004434a5ff76e7b97c8b07c01f34ca69e635d97/build/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="/TestsProjectFes/static_js/js/CompanyViewTestsResults.js"></script>
<title>View tests results</title>
</head>
<body ng-app="app" ng-controller="InputController" ng-init="companyName='${company}'">
	<div class="container-fluid">
		<h3 class="page-header">View tests results</h3>
		<div class="row">
			<form ng-submit="submit()" >
				<div class="container">
					<div class='col-md-3'>
						<div class="form-group"> 			
							<select ng-model="mode" name="mode" class="selectpicker form-control" title="Please select a mode ...">
								<option ng-click="changed_to_mode('all', '<%=ICommonData.TESTS_RESULTS%>');">All tests</option>
								<option ng-click="changed_to_mode('range', '<%=ICommonData.TESTS_RESULTS_BY_DATES%>')">Data Range</option>
								<option ng-click="changed_to_mode('id', '<%=ICommonData.TESTS_RESULTS_BY_PERSON_ID%>')">For Some Person ID</option>
							</select>
						</div>
					</div>
					<div ng-show='display.calendar'>
						<div class='col-md-3 text-left' >
							<div class="form-group">
								<div class='input-group date' id='datetimepicker1'>
									<input type='text' ng-model="dateFrom" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar">
										</span>
									</span>
								</div>
							</div>
						</div>
						<div class='col-md-3'>
							<div class="form-group">
								<div class='input-group date' id='datetimepicker2'>
									<input type='text' ng-model="dateTo" class="form-control" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div ng-show='display.id' class='col-md-2'>
					<div class="form-group">
						<input type="number" ng-model="personID" name="personID" class="form-control" placeholder="Person ID">
					</div>
				</div>
				<input type="text" ng-show="false" ng-model="token" ng-init="token = '${token}'">
				<div class='col-md-1'>
					<button ng-disabled="isButtonDisabled" type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>
	</div>
	<div class="row">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Name</th>
						<th>Surname</th>
						<th>Test Category</th>
						<th>Test Date</th>
						<th>Test Name</th>
					</tr>
				</thead>
				<tbody>
				  <tr ng-repeat="x in results">
				    <td>{{ x.personName }}</td>
				    <td>{{ x.personSurname }}</td>
				    <td>{{ x.testCategory }}</td>
				    <td>{{ x.testDate }}</td>
				    <td>{{ x.testName }}</td>
				  </tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>
