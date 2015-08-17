$(document).ready(function(){
	
	$('<div id ="header">' +
		'<div id="logo">' + 
		'</div>' +
		'<div id="search_area">' + 
			'<input id="text_Area" type="text" placeholder="Search.."/>'+
			'<input id="button" type="button" value="search"/>'+
		 '</div>' + 
	   '</div>' + 
	   '<div id="nav_area">' +
	       '<ul class="clearfix">' +
		       '<li><a href=".">Home</a></li>'+
		       '<li>'+
		           '<a href="#">Question Maintance <span class="arrow">&#9660;</span></a>'+
		           '<ul class="sub-menu">'+
		               '<li><a href="maintenanceadd">Creating questions</a></li>'+
		               '<li><a href="update">Update/delete questions</a></li>'+
		               '<li><a href="company_otherResursCreation">Adding question from other resources</a></li>'+
		           '</ul>'+
		       '</li>'+
		       '<li><a href="testGeneration">Test generation</a></li>'+
		       '<li><a href="view_results">View test results</a></li>'+
		       '<li><a href=".">FAQ</a></li>'+
		       '<li><a href=".">Contacn us</a></li>'+
		   '</ul>'+
		'</div>' +
	   	'</div>').insertBefore("#right_side_viewresults");
});