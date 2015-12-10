var app = angular.module('test_app', ['ngDialog']);

app.controller('test_controller', function($scope, $http, ngDialog){
	$scope.autocategories;
	$scope.admincategories;
	$scope.urlBes = "/TestsProjectBes/tests";
	$scope.urlAutoCategories = $scope.urlBes + "/autoList";
	$scope.urlAdminCategories = $scope.urlBes + "/adminList";
	$scope.urlCustomCategories = $scope.urlBes + "/categoriesList";
	$scope.urlCustomQuestionsList = $scope.urlBes + "/questionList";
	$scope.urlPostTemplate = $scope.urlBes + "/createTemplate";
	$scope.token = token;
	
    $scope.buttonsShow = true;
    $scope.switchN = 0;
    $scope.titles = ["", "Generate question", "Questions common base", "Your questions", "Your questions"];

    $scope.allCategories = [];
    $scope.categories = [];
    
    $scope.category1;
    $scope.category2;
    $scope.mCategory;
    $scope.showCat2 = false;
    $scope.showLevel = false;
    $scope.subCategories;
    $scope.level = 0;
    $scope.numberQuestions = 0;
    $scope.showMc = false;
    $scope.mCategories;
    $scope.listQuestions = [];

    $scope.elementCategory1 = null;
    $scope.elementCategory2 = null;
    $scope.elementMCategory = null;
    

    $scope.resultCategories = [];
    $scope.categorisEmpty = true;

    $scope.resultQuestions = [];
    $scope.questionsEmpty = true;

    $scope.questionsForShow = [];

    $scope.templateName=null;
    $scope.totalNumberQuestions = 0;
            
	
	$scope.renewCategories = function() {	
		if($scope.$parent.categories==null) {
			
			
		$http({
			method: 'GET',
			url: $scope.urlAutoCategories,
			headers: {
				'Authorization' : $scope.token
			}
		}).then(function(response) {
			
	    	$scope.allCategories[1] = response.data;
	    	
	    });
		
				
		$http({
			method: 'GET',
			url: $scope.urlAdminCategories,
			headers: {
				'Authorization' : token
			}
		}).then(function(response) {
	    	$scope.allCategories[2] = response.data;
	    });
			
		$http({
			method: 'GET',
			url: $scope.urlCustomCategories,
			headers: {
				'Authorization' : token
			}
		}).then(function(response){
			$scope.allCategories[3] = response.data;
		});
		
		$http({
			method: 'GET',
			url: $scope.urlCustomQuestionsList,
			headers: {
				'Authorization' : token				
			}
		}).then(function(response){
			$scope.allCategories[4] = response.data;
		});
		} else {
			$scope.categories = $scope.$parent.categories;
		}
	};
	
	
	
	$scope.renewCategories();
	
	
    $scope.switchBtn = function(a) {
    	
    	$scope.switchN = a;

    	
    	if(a==4) {
    		$scope.showQuestionWindow();
    	} else {
    		
    		$scope.showCategoriesWindow();
//        $scope.buttonsShow = false;
    	}
}

$scope.showQuestionWindow = function() {
    $scope.itemsOnPage = 1;
    $scope.currentPage=1;
    $scope.pages = [1, 2, 3, 4, 5];
    $scope.numQuestions = $scope.categories.length;
    $scope.categories = $scope.allCategories[4];
    ngDialog.open({
        template: 'questionList',
        className: 'ngdialog-theme-default',
        controller: 'test_controller',
        scope: $scope
    });

}

$scope.showCategoriesWindow = function() {
	
    $scope.titleName = $scope.titles[$scope.switchN];
    $scope.categories = $scope.allCategories[$scope.switchN];
    
    ngDialog.open({
        template: 'categoriesList',
        className: 'ngdialog-theme-default',
        controller: 'test_controller',
        scope: $scope
    });
}

$scope.cancel = function() {
    $scope.cancelWindow();
//    $scope.buttonsShow = true;
    $scope.switchN = 0;
    $scope.clearAll();
}

$scope.clearAll = function() {
    $scope.showCat2 = false;
    $scope.clearSubCategory();
    $scope.clearCategory1();

}

$scope.clearSubCategory = function() {
    $scope.showLevel = false;
    $scope.clearCategory2();
    $scope.clearMCategory();

}

$scope.clearMCategory = function() {
    $scope.showMc = false;
    $scope.level = 0;
    $scope.numberQuestions = 0;
    $scope.clearMCElement();
}

$scope.clearMCElement = function() {
    $scope.mCategory = null;
    angular.element($scope.elementMCategory).removeClass('chosen');
    $scope.elementMCategory = null;
}

$scope.clearCategory1 = function() {
    $scope.category1 = null;
    angular.element($scope.elementCategory1).removeClass('chosen');
    $scope.elementCategory1 = null;
}

$scope.clearCategory2 = function() {
    $scope.category2 = null;
    angular.element($scope.elementCategory2).removeClass('chosen');
    $scope.elementCategory2 = null;
}

$scope.setCategory = function(cat, $event) {
    if($scope.category1!=null) {
        $scope.clearAll();
    }
    $scope.category1 = cat.cat_parent;
    $scope.changeStyle($event);
    $scope.elementCategory1 = $event.target;
    if(cat.cat_children!=null && cat.cat_children.length>0) {
    	
        if(cat.cat_children[0].cat_child==null && cat.cat_children.length==1 && cat.cat_children[0].cat_mc_array!=null) {
            $scope.showMc = true;
            $scope.mCategories = cat.cat_children[0].cat_mc_array;
        } else {
        $scope.showCat2 = true;
        $scope.subCategories = cat.cat_children;
        }
    } else {
        $scope.toLevel();
    }
}

$scope.setSubCategory = function(cat2, $event) {
    if($scope.category2!=null) $scope.clearSubCategory();
    $scope.changeStyle($event);
    $scope.elementCategory2 = $event.target;
    $scope.category2 = cat2.cat_child;

    if(cat2.cat_mc_array!=null) {
        $scope.showMc = true;
        $scope.mCategories = cat2.cat_mc_array;
    } else {
        $scope.toLevel();
    }
}

$scope.setMCategory = function(mc, $event) {
    if($scope.mCategory!=null) $scope.clearMCategory();
    $scope.changeStyle($event);
    $scope.elementMCategory = $event.target;
    $scope.mCategory = mc.cat_mc;

    $scope.toLevel();
}

$scope.changeStyle = function($event) {
    angular.element($event.target).addClass('chosen');
}

$scope.toLevel = function() {
    $scope.showLevel = true;
}

$scope.dataSave = function() {

    var data = {
    };
    data.level = $scope.level;
    data.quantity = $scope.numberQuestions;
    switch ($scope.$parent.switchN) {
        case 1 :
            data.metaCategory = $scope.category1;
            data.category1 = $scope.category2;
            data.type = "Generation";
            break;
        case 2 :
            data.metaCategory = $scope.mCategory;
            data.category1 = $scope.category1;
            data.category2 = $scope.category2;
            data.type = "SiteBase";
            break;
        case 3 :
            data.metaCategory = $scope.mCategory;
            data.category1 = $scope.category1;
            data.category2 = $scope.category2;
            data.type = "Custom";
            break;
    }
    $scope.$parent.resultCategories.push(data);
    $scope.$parent.totalNumberQuestions += $scope.numberQuestions;
    $scope.$parent.categorisEmpty = false;
    $scope.cancel();


}

$scope.questionsSave = function() {

    $scope.addQuestionsToList();
}

$scope.addQuestionsToList = function() {

    for(var question in $scope.categories) {

        var q = $scope.categories[question];

        if(q.checked) {
            q.added = true;
            q.checked=false;
            $scope.$parent.questionsForShow.push(q);
            var data = {};
            data.template_quest_id = q.id;
            $scope.$parent.resultQuestions.push(data);
            $scope.$parent.questionsEmpty = false;
            $scope.$parent.totalNumberQuestions++;
        }
    }
    $scope.$parent.allCategories[4] = $scope.categories;
    $scope.categories = [];
    $scope.cancelWindow();
}

$scope.cancelWindow = function() {

    $scope.closeThisDialog();
}

$scope.deleteCat = function (index) {
    $scope.totalNumberQuestions -= $scope.resultCategories[index].quantity;
    $scope.resultCategories.splice(index, 1);
    if($scope.resultCategories.length==0) $scope.categorisEmpty = true;
}

$scope.clearTemplate = function() {
    $scope.resultCategories = [];
    $scope.templateName = null;
    $scope.totalNumberQuestions = 0;
    $scope.cancel();
    $scope.categorisEmpty = true;
    $scope.questionsEmpty = true;
}

$scope.saveTemplate = function() {
    var result = {};
    result.template_name = $scope.templateName;
    result.template_categories = $scope.resultCategories;
    result.template_questions = $scope.resultQuestions;
    
    var answer = angular.toJson(result, false);
    alert(answer);
    $http({
		method: 'POST',
		url: $scope.urlPostTemplate,
		data: answer,
		headers: {
			'Authorization' : $scope.token
		}
	}).then(function(response) {
		
    	$scope.allCategories[1] = response.data;
    	
    });

}

$scope.checkQuestion = function(question) {
    $scope.numberQuestions++;
    question.checked = true;
}

$scope.deleteQuestion = function($index, question) {
    $scope.questionsForShow.splice($index,1);
    $scope.resultQuestions.splice($index,1);
    question.added = false;
    if($scope.resultQuestions.length<1) $scope.questionsEmpty = true;
    $scope.returnQuestion(question.id);
    $scope.totalNumberQuestions--;
}

$scope.returnQuestion = function(id) {
    for(var question in $scope.allCategories[4]) {
        var q = $scope.allCategories[4][question];

        if(q.id == id) {
            q.added = false;

        }
    }
}

$scope.goToPage = function(n) {
    $scope.currentPage = n;
};

$scope.getMaxPage = function(itemsOnPage, numQuestions) {
    var maxPage = numQuestions / itemsOnPage;
    if(maxPage*itemsOnPage < numQuestions) maxPage++;
    return maxPage;
}

$scope.goToLastPage = function(itemsOnPage, numQuestions) {

    $scope.currentPage = $scope.getMaxPage(itemsOnPage, numQuestions);
}

$scope.checkLastPage = function(itemsOnPage, numQuestions, currentPage) {
    var max = $scope.getMaxPage(itemsOnPage, numQuestions);
    return currentPage < max;
}
});

app.filter('checkedFilter', function(){
return function(items) {

    var result = [];
    for(var item in items) {
        if(items[item].added==null || !items[item].added)
            result.push(items[item]);
    }
    return result;
};
});

app.filter('pageFilter', function(){
return function(items, page, itemsOnPage) {
   if(itemsOnPage<=0) itemsOnPage = 1;
   var result = [];
   var start = (page-1) * itemsOnPage;
   var stop = start + itemsOnPage;

   if(stop >= items.length) {
       stop = items.length;
   }

   for(var i = start; i < stop; i++) {

       result.push(items[i]);
   }
   return result;
};

});

app.filter('pageNumFilter', function(){
return function(items, itemsOnPage, numItems) {
   if(itemsOnPage <= 0) {
       itemsOnPage = 1;
   }
   var result = [];
   var stop = numItems / itemsOnPage;
   if(stop*itemsOnPage < numItems) stop++;
   for(var i = 0; i < stop; i++) {
       result.push(i);
   }
   return result;
}
});

app.filter('pageNumbersInlineFilter', function(){
return function(items, itemsOnPage, numItems, currentPage) {
   if(itemsOnPage <= 0) {
       itemsOnPage = 1;
   }
   var result = [];
   var start = currentPage - 2;
   if(start<=0) start = 1;
   var maxPage = numItems / itemsOnPage;
   if((maxPage * itemsOnPage) < numItems) {
       maxPage++;
   }
   var stop = parseInt(currentPage) + 2;

   if(stop > maxPage) stop = maxPage;
   for(var i = start; i <= stop; i++) {
       result.push(i);
   }
   return result;
}
});
