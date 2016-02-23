<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html data-ng-app="testcreation_app" data-ng-controller="testcreation_controller">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create test</title>
<script type="text/javascript" src="<c:url value='/static/js_folder/jquery-2.1.4.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/angular.min.js' />" ></script>

<script type="text/javascript" src="<c:url value='/static/js_folder/js/viewresultsjs/ngDialog.min.js' />"></script>
<script src="static/js_folder/header&&rightmenu.js"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/tests/creationController.js' />" ></script>	

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
	
<link href='<c:url value="/static/css_folder/tests/style.css"></c:url>' rel="stylesheet">
<link href='<c:url value="/static/css_folder/company_styles/viewresults/ngDialog-theme-default.min.css"></c:url>' 
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/company_styles/viewresults/ngDialog.min.css"></c:url>' 
	rel="stylesheet">
	
<script>	
		var token = "${token}";
		var link = "${link}";
</script>
</head>
<body>
<div id="container">
		<div id="right_side" >
		
		<div >
    <h3 class="page-header">Template: </h3>
    <div class="oneRow">
        <select class="form-control bigField" data-ng-model="template">
            <option data-ng-repeat="t in templates | orderBy : 'template_name'" value="{{t.template_id}}">{{t.template_name}}</option>
        </select>
    </div>
    <div id="secondRow" >
        <h3 class="page-header">For persons: </h3>

        <div>
        <table id="buttons" >
            <tr>
                <td>
                    <button class="miniButtons" data-ng-click="addOnePerson()">Add Person</button>
                </td>
                <td>
                    <button class="miniButtons" data-ng-click="addPersons()" data-ng-hide="true">Add some persons</button>
                </td>

            </tr>
        </table>
        </div>

        <div data-ng-show="personListShow">
            <table class="resultTable">
                <thead>
                <tr>
                    <th></th>
                    <th>Last name</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Passport/ID</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tr data-ng-repeat="person in personDataList">
                    <td>{{$index+1}}</td>
                    <td>{{person.per_lname}}</td>
                    <td>{{person.per_fname}}</td>
                    <td>{{person.per_mail}}</td>
                    <td>{{person.per_passport}}</td>
                    <td class="delete-cell" data-ng-click="edit($index)">E</td>
                    <td class="delete-cell" data-ng-click="deletePerson($index)">X</td>
                </tr>
            </table>

            <div class="lastButtons">
                <div class="buttonsMini">
                    <button class="actionBtn" data-ng-click="clearTest()">Clear all</button>
                    <button class="actionBtn" data-ng-click="saveTest()" data-ng-disabled="template==null">Send test</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/ng-template" id="addPerson">
    <div class="modaleWindow">
        <h3>{{title}}</h3>
    <form name="PersonForm" novalidate>
        <table class="formInput">
            <tr>
            <td><label>Name</label></td>
            <td><input class="form-control" type="text" data-ng-model="name"></td>
            <td></td>
        </tr>
            <tr>
                <td><label>Last Name</label></td>
                <td><input class="form-control" type="text" data-ng-model="lastname"></td>
                <td></td>
            </tr>
            <tr>
                <td><label>E-mail</label></td>
                <td><input class="form-control" name="mail" type="email" data-ng-model="email"></td>
                <td class="requiredStar">*
                    <span data-ng-show="PersonForm.mail.$error.email">Email is incorrect! </span></td>
            </tr>
            <tr>
                <td><label>Passport / ID </label></td>
                <td><input class="form-control" type="text" data-ng-model="passport"></td>
            </tr>
        </table>
        <div class="rowButton">
        <div class="buttonsMini">
            <button class="actionBtn" data-ng-click="closeThisDialog()">Cancel</button>
            <button class="actionBtn" data-ng-click="personSave()" data-ng-disabled="email==null">Add</button>
        </div>
        </div>
    </form>
    </div>
</script>

<script type="text/ng-template" id="resultMessage">
    <div class="modaleWindow">
        <h4>{{message.text}}</h4>
		<div data-ng-show="message.data!=null">
			<table>
				<tr data-ng-repeat="d in message.data">
					<td>For: {{d.per_mail}}</td>
					<td><a href="{{d.test_link}}">Link to test</a></td>
					<td><span data-ng-show="{{d.test_is_sent}}> was succesfully sent</span>
						<span data-ng-hide="{{d.test_is_sent}}> wasn't sent!</span></td>
				</tr>
			</table>
		</div>
        <div class="rowButton">

            <div class="buttonsMini">
                <button class="actionBtn" data-ng-click="closeThisDialog()">Ok</button>

            </div>
        </div>

    </div>
</script>
		
		
		</div>
</div>

<script>		
		var roleNumber = ${role}; getMenu(roleNumber);		
</script>
</body>
</html>