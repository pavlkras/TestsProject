<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View test results</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  	$(function() {
    	$( "input[name^=date]" ).datepicker();
  	});
  
  	$(function() {
  		$(':radio[id=all]').change(function() {
	   		$("#dates").hide();
	   		$("#person_id_block").hide();
		});
	});
  	
  	$(function() {
		$(':radio[id=time_interval]').change(function() {
	   		$("#dates").show();
	   		$("#person_id_block").hide();
		});
	});
  	
  	$(function() {
  		$(':radio[id=by_person_id]').change(function() {
  		 	$("#dates").hide();
  		 	$("#person_id_block").show();
  		});
  	});

  </script>

</head>
<body>
<form action="process_request">
<label><input type="radio" name="request_type" id="all" value="all" checked>All tests</label>
<br>
<label><input type="radio" name="request_type" id="time_interval" value="time_interval">Time interval</label>
<br>
<label><input type="radio" name="request_type" id="by_person_id" value="by_person_id">By person ID</label>
<br>
<div id="dates" style="display:none">		
<p><label>Date from: <input type="text" name="date_from"  value=""></label></p>
<p><label>Date to: <input type="text" name="date_until" value=""></label></p>
</div>
<div id="person_id_block" style="display:none">
Person ID <input type="text" name="person_id">
</div>


<input type="submit">
</form>
</body>
</html>