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
		               '<li><a href="otherResursCreationMethod">Adding question from other resources</a></li>'+
		           '</ul>'+
		       '</li>'+
		       '<li><a href="CompanyGenerateTest">Test generation</a></li>'+
		       '<li><a href="view_results">View test results</a></li>'+
		       '<li><a href=".">FAQ</a></li>'+
		       '<li><a href=".">Contacn us</a></li>'+
		   '</ul>'+
		'</div>' +
	   	'<div id="left_side">' +
	   		'<div id="testexamples">' +
	   			'<div>' +
	   				'<h2>Test Examples</h2>' +
	   			'</div>' + 
	   				'<ul>' + 
	   					'<li><a href=".">JAVA</a></li><hr>' +
	   					'<li><a href=".">C++</a></li><hr>' +
	   					'<li><a href=".">C#</a></li><hr>' +
	   					'<li><a href=".">Android</a></li><hr>' +
	   					'<li><a href=".">Javascript</a></li><hr>' +
	   					'<li><a href=".">HTML&CSS</a></li><hr>' +
	   					'<li><a href=".">Other tests</a></li>' +
	   				'</ul>'+
	   		'</div>' +
	   	'</div>').insertBefore("#right_side_company");
});