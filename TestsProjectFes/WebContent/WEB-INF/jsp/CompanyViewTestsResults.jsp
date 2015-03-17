<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en" ng-app="view_results">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
  <script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
  <script src="/TestsProjectFes/static_js/js/CompanyViewTestsResults.js"></script>
  <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container">
    <div class="row">
      <form name="view_test_results" ng-controller="mode_selector">
        <div class="col-xs-2">
          <div class="btn-group" dropdown is-open="status.isopen">
            <button type="button" class="btn btn-primary dropdown-toggle" dropdown-toggle >
              {{mode}}<span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
              <li ng-click="all_switch()"><a href="#">{{modes.all}}</a></li>
              <li ng-click="dates_switch()"><a href="#">{{modes.dates}}</a></li>
              <li ng-click="id_switch()"><a href="#">{{modes.id}}</a></li>
              <input type="text" ng-show="false" class="form-control" value = "1">
            </ul>
          </div>
        </div>
        <div class="col-xs-4">
          <div ng-controller="calendar_selector" ng-show="visible.dates">
            calendar
          </div>
          <div ng-show="visible.id">
            <input type="number" ng-disabled="disabled.isDisabled_ID" placeholder="Please input person ID" class="form-control">
          </div>
        </div>
        <div>
          <div class="col-xs-2">
            <button type="submit" ng-Click="show_results_click()" ng-disabled="disabled.isDisabled_SubmitButton" class="btn btn-primary">Show Results</button>
          </div>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-xs-4">
        <div class = "container">
            <table>
             <tr ng-repeat="x in names">
              <td>{{ x.Name }}</td>
              <td>{{ x.Country }}</td>
            </tr>
          </table>
      </div>
    </div>
  </div>

</div>
<br>
	<a href=".">Home</a>
</body>
</html>
    