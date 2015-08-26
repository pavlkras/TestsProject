/**
 *    adding page script
 */
$(document).ready(function(){
	$("#inputCategoryField").css("display","none");
	$("div#codepattern").hide(); 
	$("div#americanquestion").hide(); 
	$("div#compcategory").hide();
	
	$("#addingForm").keyup(function(){
		var $catSelect = $("#catSel").val();
		if($catSelect != "none"){
			$("#inputCategoryField").css("display","none");
			$("#inputCategoryField").val(" ");
		}
		
	 var question_text = document.question_adding_form.questionText.value;	
		if(question_text.length > 3){
			var $catSel = $("#catSel").val();			
			if($catSel.length != 0){// stub  test if category selected				
			var answer = document.question_adding_form.correctAnswer.value;	
			var codeT = document.question_adding_form.codeText.value;
			if(answer.length == 1 || codeT.length != 0){							
				 document.getElementsByName("button_send")[0].disabled = false;
			}else{
				document.getElementsByName("button_send")[0].disabled = false;
			}				
			}else{
				document.getElementsByName("button_send")[0].disabled = false;
			}
		}else{
			 document.getElementsByName("button_send")[0].disabled = false;
		}
	});
	////
	//test_code
	///// AJAX Tast Code Case Request/Response
	$("#test_code").click(function(){							
		var $codeInText = $('#codeText').val();			
		alert($codeInText);
		$.ajax({type: "POST",
			url: "handler-user-code",
			data: "userCode" + $codeInText,					
			success: function(response){
				// we have the response				
				if(response.status == "SUCCESS"){								
					alert(response.result);
													 
				}else if(response.status == "ERROR"){  
					alert("code wrong try again!!!");
				}		
			},
			error: function(e){
				alert('Error: ' + e);			
			}
		}); 
	});
	
	$("#newMetaCategoryCreating").click(function(){
		var $catSelect = $("#mCatSel").val();
		
		if($catSelect == "none"){
			$("#inputMetaCategoryField").css("display","block");			
		}else{
			$("#inputMetaCategoryField").css("display","none");
			$("#inputMetaCategoryField").val(" ");
		}		
	});
	
	$("#newCategoryCreating").click(function(){
		var $catSelect = $("#catSel").val();
		if($catSelect == "none"){
			$("#inputCategoryField").css("display","block");			
		}else{
			$("#inputCategoryField").css("display","none");
			$("#inputCategoryField").val(" ");
		}		
	});
	
	$( "#mCatSel" ).change(function() {
		var $catSelect = $("#mCatSel").val();
		
		if ($catSelect == "American Test") {
			$("div#americanquestion").show(); 
			$("div#codepattern").hide(); 
	    }
		else if($catSelect == "Open Question") {
			$("div#americanquestion").hide(); 
	    }
		else if($catSelect == "none") {
			$("div#codepattern").hide(); 
			$("div#americanquestion").hide(); 
	    }

		   
	});
	
	$( "#catSel" ).change(function() {
		var $catSel  = $("#catSel").val();
		
		if ($catSel == "Create company category") {
			$("div#compcategory").show();
	    }
		else {
			$("div#compcategory").hide();
		}
		   
	});
	
	var reader = new FileReader();
	var imageToSend = " ";
	
	reader.onload = function(){imageToSend = btoa(reader.result);};
	$("#takeFile").change(function(){reader.readAsBinaryString(this.files[0]);});
	
	
	$("#button_send").click(function(){
		$.ajax({
			type: "POST", url: "company_add_questions", async : false,
			data: {
				metaCategory : $("#mCatSel").val(),
				category1 : $("#catSel").val(),
				compcategory : $("#compcategorytosend").val(),
				levelOfDifficulty : $("#levelOfDifficulty").val(),
				descriptionText : $("#descriptionText").val(),
				fileLocationLink :  imageToSend,
				at1 : $("#at1").val(),
				at2 : $("#at2").val(),
				at3 : $("#at3").val(),
				at4 : $("#at4").val(),
				correctAnswer : $("#correctAnswer").val()
			},
			complete: function(data){	
			document.getElementById("resultDB").innerHTML=data.responseText;
			$("resiltDB").css("font-size", "14px");
			$("#button_OK").show();
			$("#button_OK").focus();
			
			}
		});
		
		$("#button_OK").click(function(){document.location.href = "company_add";});

		
		
	});
});