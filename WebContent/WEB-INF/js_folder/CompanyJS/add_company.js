/**
 * 
 */
$(document).ready(function(){	
	// get the form values
	$("#login_id").keyup(function(){
		var name = $('#login_id').val();		 
		$.ajax({type: "POST",
			url: "add_processing_ajax",
			data: "name=" + name,
			success: function(response){
				// we have the response				
				if(response.status == "SUCCESS"){					
					$('#login_correct').text(response.result);
					$('#login_correct').css("color","green");	
					$('#login_correct').css("font-size","16px");					
				}else if(response.status == "ERROR"){  
					$('#login_correct').text(response.result);
					$('#login_correct').css("color","red");		
					$('#login_correct').css("font-size","16px");	
					document.getElementById('check_login').value = 0;
				}		
			},
			error: function(e){
				alert('Error: ' + e);			
			}
		}); 
	});
});


function CountLogin(item) {
	var item_view = 'login_view';
	var item_correct = 'login_correct';
	document.getElementById(item_view).innerHTML = document
	.getElementById(item).value.length++;
	if (document.getElementById(item).value.length >= 5) {			
		document.getElementById(item_correct).className = 'correct';
		document.getElementById('check_login').value = 1;			 
	} else {
		if(document.getElementById(item).value.length == 0){
		$("#login_correct").css("color","#666");
		$('#login_correct').css("font-size","10px");
		document.getElementById(item_correct).innerHTML = 'Minimum 5 characters';
		//document.getElementById(item_correct).className = '';
		}
		document.getElementById('check_login').value = 0;
	}
	checkAll();
}

function CountPass(item) {
	var item_view = 'pass_view';
	var item_correct = 'pass_correct';
	var item_login_value = document.getElementById('login_id').value;
	var item_login_length = document.getElementById('login_id').value.length;
	document.getElementById(item_view).innerHTML = document
	.getElementById(item).value.length++;
	if (document.getElementById(item).value == item_login_value
			&& item_login_length >= 5) {
		document.getElementById(item_correct).innerHTML = 'acorrect';
		document.getElementById(item_correct).className = 'acorrect';
		document.getElementById('check_pass').value = 0;
	} else {
		if (document.getElementById(item).value.length >= 4) {
			document.getElementById(item_correct).innerHTML = 'OK';				
			document.getElementById(item_correct).className = 'correct';
			document.getElementById('check_pass').value = 1;
		} else if (document.getElementById(item).value.length < 4) {
			document.getElementById(item_correct).innerHTML = 'Password must be between 4 and 20 characters';
			document.getElementById(item_correct).className = '';
			document.getElementById('check_pass').value = 0;
		}
	}
	checkAll();
}

function CorrectPass(item) {
	var item_pass_value = document.getElementById('pass_id').value;
	var item_pass_length = document.getElementById('pass_id').value.length
	var item_correct = 'repass_correct';
	if (item_pass_length >= 4) {
		if (document.getElementById(item).value == item_pass_value) {
			document.getElementById(item_correct).innerHTML = 'correct';
			document.getElementById(item_correct).className = 'correct';
			document.getElementById('check_repass').value = 1;
		} else if (document.getElementById(item).value.length >= 4) {
			document.getElementById(item_correct).innerHTML = 'acorrect';
			document.getElementById(item_correct).className = 'acorrect';
			document.getElementById('check_repass').value = 0;
		}
	}
	checkAll();
}

function checkAll() {
	var x;
	var check_login = document.getElementById('check_login').value;
	var check_pass = document.getElementById('check_pass').value;
	var check_repass = document.getElementById('check_repass').value;		
	x = check_login + check_pass + check_repass;
	document.getElementById('check_all').value = x;
	if (document.getElementById('check_all').value == 111) {
		document.getElementById('submit_id').disabled = false;
	} else {
		document.getElementById('submit_id').disabled = true;
	}
}




