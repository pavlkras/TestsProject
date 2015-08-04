$(document).ready(function(){
	
	$('<div id ="header">' +
		'<div id="logo">' + 
		'</div>' +
		'<div id="search_area">' + 
			'<input id="text_Area" type="text" placeholder="Search.."/>'+
			'<input id="button" type="button" value="search"/>'+
		 '</div>' + 
	   '</div>' + 
	   '<div id="nav_area">'+
	   		'<ul>' +
	   			'<li><a href=".">Home</li></a>'+
	   			'<li><a href="login">User Login</a></li>' + 
	   			'<li><a href="CompanyActions">Company Login</a></li>' +
	   			'<li><a href=".">FAQ</a></li>' +
	   			'<li><a href=".">Contact Us</a></li>' +
	   		'</ul>' +
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
	   	'</div>').insertBefore("#right_side");
});