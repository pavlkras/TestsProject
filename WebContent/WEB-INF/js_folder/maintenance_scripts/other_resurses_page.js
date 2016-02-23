/**
 *    other resurses page script
 */
//// JQuery actions
	$(document).ready(function() {
		///// AJAX  Request/Response creating category for generated questions
		var $RtemP = "request";
		$.ajax({
			type : "POST",
			url : "categoryList",
			data : "request" + $RtemP,
			success : function(response) {
				// we have the response				
				if (response.status == "SUCCESS") {
					var gres = response.result;
					$('#categoryOfQuestions').html(gres);
				} else {
					alert("wrong BES not Responsed");
				}
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
		////
		$(".formCategory").css("display", "none");
		$(".formFileAdding").css("display", "none");
		////
		$("#readTextFromFile").click(function() {
			$(".generateQuestions,.fillfromfile").css("display", "none");
			$(".formFileAdding").css("display", "block");
		});
		////
		$("#generateQuestionsByCategory").click(function() {
			$(".generateQuestions,.fillfromfile").css("display", "none");
			$(".formCategory").css("display", "block");
		});
	});
	/////
	var openFile = function(event) {
		var input = event.target;
		var reader = new FileReader();
		reader.onload = function() {
			var text = reader.result;
			$("#hiddenTextFromFile").text(reader.result);
		};
		reader.readAsText(input.files[0]);
	};