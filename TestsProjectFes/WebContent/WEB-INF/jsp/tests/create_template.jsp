<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html data-ng-app="test_app" data-ng-controller="test_controller">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create test template</title>
<script type="text/javascript" src="<c:url value='/static/js_folder/jquery-2.1.4.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/angular.min.js' />" ></script>

<script type="text/javascript" src="<c:url value='/static/js_folder/js/viewresultsjs/ngDialog.min.js' />"></script>
<script src="static/js_folder/header&&rightmenu.js"></script>
<script type="text/javascript" src="<c:url value='/static/js_folder/tests/testcontroller.js' />" ></script>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href='<c:url value="/static/css_folder/style.css"></c:url>'
	rel="stylesheet">
	
<link href='<c:url value="/static/css_folder/tests/style.css"></c:url>' rel="stylesheet">
<link href='<c:url value="/static/css_folder/company_styles/viewresults/ngDialog-theme-default.min.css"></c:url>' 
	rel="stylesheet">
<link href='<c:url value="/static/css_folder/company_styles/viewresults/ngDialog.min.css"></c:url>' 
	rel="stylesheet">
	
<script>	
		var token = "${token}";
</script>

</head>
<body>

	<div id="container">
		<div id="right_side" >
		
		
		
		
<div>
    <h3 class="page-header">Create test template</h3>

<div id="firstRow">

    <table id="buttons" data-ng-show="buttonsShow">
        <tr>
            <td>
                <button data-ng-click="switchBtn(1)">Generate questions <br> <span>by categories</span></button>
            </td>
            <td>
                <button data-ng-click="switchBtn(2)">Site base questions <br> <span>by categories</span></button>
            </td>

            <td>
                <button data-ng-click="switchBtn(3)">Your questions <br> <span>by categories</span></button>
            </td>
            <td>
                <button data-ng-click="switchBtn(4)">Your questions <br> <span>by text</span></button>
            </td>
        </tr>
    </table>

    <div data-ng-show="!buttonsShow">
        <h3>{{titles[switchN]}}</h3>

        <div class="row" data-ng-show="switchN!=4">
            <div class="cell">
                 <ul><li data-ng-repeat="cat in categories" data-ng-click="setCategory(cat, $event)">{{cat.cat_parent}}</li></ul>
            </div>
            <div class="cell" data-ng-show="showCat2">
                <ul><li data-ng-repeat="cat2 in subCategories" data-ng-click="setSubCategory(cat2, $event)">{{cat2.cat_child}}</li></ul>
            </div>
            <div class="cell" data-ng-show="showMc">
                <ul><li data-ng-repeat="mc in mCategories" data-ng-click="setMCategory(mc, $event)">{{mc.cat_mc}}</li></ul>
            </div>
            <div class="cell" data-ng-show="showLevel">

                <select class="form-control" data-ng-model="level" data-ng-init="0">
                    <option value="1">1: Easy</option>
                    <option value="2">2: Middle</option>
                    <option value="3">3: Hard</option>
                </select>

            </div>
            <div class="cell" data-ng-show="level > 0">

                <input class="form-control" type="number" data-ng-model="numberQuestions">

            </div>
        </div>

        <div class="buttonsMini">
            <button class="actionBtn" data-ng-click="cancel()">Cancel</button>
            <button id="btnOk" class="actionBtn" data-ng-disabled="numberQuestions<1 && switchN!=4" data-ng-click="dataSave()">Add</button>
        </div>
    </div>


</div>

<div id="secondRow" >

    <div class="titleTemplate">
        <div class="cell">
    <h3>Template: {{templateName}} </h3>
        </div>
        <div class="cellRight">
            {{totalNumberQuestions}} questions
        </div>
    </div>
    <div class="formTemplate">
    <label>Name of template</label><input type="text" data-ng-model="templateName" class="form-control">
    </div>
    <div data-ng-hide="categorisEmpty">
    <h4>By category (randomly)</h4>
    <table class="resultTable">
        <thead>
        <tr>
            <th>Source</th>
            <th>Meta Category</th>
            <th>Category</th>
            <th>SubCategory</th>
            <th>Level</th>
            <th>Quantity</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tr data-ng-repeat="res in resultCategories">
            <td class="{{res.type}}">{{res.type}}</td>
            <td>{{res.metaCategory}}</td>
            <td>{{res.category1}}</td>
            <td>{{res.category2}}</td>
            <td>{{res.level}}</td>
            <td>{{res.quantity}}</td>
            <td class="delete-cell" data-ng-click="deleteCat($index)">X</td>
        </tr>

    </table>
    </div>

    <div data-ng-hide="questionsEmpty">
        <h4>By questions</h4>
        <table class="resultTable">
            <thead>
            <tr>
                <th>Category 1</th>
                <th>Category 2</th>
                <th>Type</th>
                <th>Level</th>
                <th>Text</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tr data-ng-repeat="ques in questionsForShow">
                <td>{{ques.category1}}</td>
                <td>{{ques.category2}}</td>
                <td>{{ques.metaCategory}}</td>
                <td>{{ques.level}}</td>
                <td>{{ques.shortText}}</td>
                <td class="delete-cell" data-ng-click="deleteQuestion($index,ques)">X</td>
            </tr>
        </table>

    </div>

    <div class="lastButtons">
    <div class="buttonsMini">
        <button class="actionBtn" data-ng-click="clearTemplate()" data-ng-disabled="totalNumberQuestions<1 && templateName==null">Clear template</button>
        <button class="actionBtn" data-ng-disabled="totalNumberQuestions<1 || templateName==null" data-ng-click="saveTemplate()">Save template</button>

    </div>
    </div>
</div>
</div>
		
		
		</div> 
		<!--end of right area-->



		<div id="footer_area">

			<p>Copyright &copy; 2014 HrTrueTest</p>
		</div>
	</div>

<script>		
		var roleNumber = ${role}; getMenu(roleNumber);		
</script>


<script type="text/ng-template" id="questionList">
    <div class="modaleWindow">
    <h3>Your questions</h3>
    <form class="topModaleForm">
        <label>Search </label>
      <input type="text" data-ng-model="textSearch">



    </form>
    <div class="rowWindow">
        <table class="modaleTable">
            <thead>
            <tr>
                <th>Category 1</th>
                <th>Category 2</th>
                <th>Type</th>
                <th>Level</th>
                <th>Text</th>
                <th>Adding</th>
            </tr>
            </thead>
            <tr data-ng-repeat="cat in categories | checkedFilter | pageFilter:currentPage:itemsOnPage | filter : textSearch">
                <td data-ng-init="cat.checked=false">{{cat.category1}}</td>
                <td>{{cat.category2}}</td>
                <td>{{cat.metaCategory}}</td>
                <td>{{cat.level}}</td>
                <td>{{cat.shortText}}</td>
                <td><input type="checkbox" data-ng-model="cat.checked"></td>
            </tr>
        </table>

    </div>
            <div class="rowButton">
                <div class="pageCounter cell">
                    <span data-ng-click="goToPage(1)">Home</span>
                    <span data-ng-click="goToPage(currentPage-1)" data-ng-show="currentPage>1">Prev</span>
                    <span data-ng-class="{curPage:n==currentPage}" data-ng-click="goToPage(n)" data-ng-repeat="n in pages | pageNumbersInlineFilter : itemsOnPage : numQuestions : currentPage">{{n}}</span>
                    <span data-ng-click="goToPage(currentPage+1)" data-ng-show="checkLastPage(itemsOnPage,numQuestions,currentPage)">Next</span>
                    <span data-ng-click="goToLastPage(itemsOnPage, numQuestions)">End</span>
                </div>
                <div class="cell pager">
                    <div class="cell">
                        <div class="lbl"><label>Questions on page</label></div>
                        <div class="inpt"><input data-ng-model="itemsOnPage" type="number" min="1" max="20"></div>
                    </div>
                    <div class="cell">
                        <div class="lbl"><label>Go To Page</label></div>
                        <div class="inpt"><select data-ng-model="currentPage">
                            <option data-ng-repeat="n in pages | pageNumFilter : itemsOnPage : numQuestions" value={{$index+1}}>{{$index + 1}}</option>
                        </select></div>
                    </div>


                </div>
    <div class="buttonsMini">
        <button class="actionBtn" data-ng-click="closeThisDialog()">Cancel</button>
        <button id="btnOk" class="actionBtn" data-ng-click="questionsSave()">Add</button>
    </div>
            </div>
    </div>
</script>

<script type="text/ng-template" id="categoriesList">
    <div class="modaleWindow">
        <h3>{{titleName}}</h3>

        <div class="rowWindow" data-ng-show="switchN!=4">
            <div class="cellWindow">
                <ul><li data-ng-repeat="cat in categories" data-ng-click="setCategory(cat, $event)">{{cat.cat_parent}}</li></ul>
            </div>
            <div class="cellWindow" data-ng-show="showCat2">
                <ul><li data-ng-repeat="cat2 in subCategories" data-ng-click="setSubCategory(cat2, $event)">{{cat2.cat_child}}</li></ul>
            </div>
            <div class="cellWindow" data-ng-show="showMc">
                <ul><li data-ng-repeat="mc in mCategories" data-ng-click="setMCategory(mc, $event)">{{mc.cat_mc}}</li></ul>
            </div>
            <div class="cellWindow" data-ng-show="showLevel">

                <select class="form-control" data-ng-model="level" data-ng-init="0">
                    <option value="1">1: Easy</option>
                    <option value="2">2: Middle</option>
                    <option value="3">3: Hard</option>
                </select>

            </div>
            <div class="cellWindow" data-ng-show="level > 0">

                <input class="form-control" type="number" data-ng-model="numberQuestions" min="1" max="50">

            </div>
        </div>
        <div class="rowButton">

        <div class="buttonsMini">
            <button class="actionBtn" data-ng-click="cancel()">Cancel</button>
            <button id="btnOk" class="actionBtn" data-ng-disabled="numberQuestions<1 && switchN!=4" data-ng-click="dataSave()">Add</button>
        </div>
            </div>
    </div>
</script>

<script type="text/ng-template" id="resultMessage">
	<div class="modaleWindow">
		<h4>{{message}}</h4>
		<div class="rowButton">

        <div class="buttonsMini">            
			<button class="actionBtn" data-ng-click="closeThisDialog()">Ok</button>

        </div>
            </div>
		
	</div>
</script>

</body>
</html>