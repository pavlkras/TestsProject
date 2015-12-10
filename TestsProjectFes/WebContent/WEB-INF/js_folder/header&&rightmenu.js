	var topMenuArray = new Array();
	topMenuArray[0] = '<li><a href=".">Home</li></a>'+
					'<li><a href="login">User Login</a></li>' + 
					'<li><a href="companyLogin">Company Login</a></li>' +
					'<li><a href=".">FAQ</a></li>' +
					'<li><a href=".">Contact Us</a></li>';
	topMenuArray[1] = topMenuArray[0];
	topMenuArray[2] = '<li><a href=".">Home</li></a>'+					
					'<li><a href=".">FAQ</a></li>' +
					'<li><a href=".">Contact Us</a></li>' + 
					'<li><a href="logout">LogOut</a></li>' ;
	topMenuArray[3] = '<li><a href=".">Home</a></li>'+
    				'<li>'+
    				'<a href="#">Question Maintance <span class="arrow">&#9660;</span></a>'+
    				'<ul class="sub-menu">'+
    					'<li><a href="add_question">Creating questions</a></li>'+
    					'<li><a href="questions_update">Update/delete questions</a></li>'+    					
    				'</ul>'+
    				'</li>'+
    				'<li>' +
    				'<a href="#">Tests management<span class="arrow">&#9660;</span></a>'+
    				'<ul class="sub-menu">'+
    					'<li><a href="testGeneration">Test generation</a></li>'+
    					'<li><a href="create_template">Create test template</a></li>'+
    				'</ul>'+
    				'</li>'+
    				'<li><a href="view_results">View test results</a></li>'+
    				'<li><a href=".">FAQ</a></li>'+
    				'<li><a href=".">Contacn us</a></li>'+
    				'<li><a href="logout">LogOut</a></li>';
	topMenuArray[4] = '<li><a href=".">Home</a></li>'+
    				'<li>'+
    				'<a href="#">Question Maintance <span class="arrow">&#9660;</span></a>'+
    				'<ul class="sub-menu">'+
    					'<li><a href="add_question">Creating questions</a></li>'+
    					'<li><a href="questions_update">Update/delete questions</a></li>'+
    					'<li><a href="autoGeneration">Auto generation</a></li>'+
    				'</ul>'+
    				'</li>'+
    				'<li>' +
    				'<a href="#">Tests management<span class="arrow">&#9660;</span></a>'+
    				'<ul class="sub-menu">'+
    					'<li><a href="testGeneration">Test generation</a></li>'+
    					'<li><a href="create_template">Create test template</a></li>'+
    				'</ul>'+
    				'</li>'+
    				'<li><a href=".">FAQ</a></li>'+
    				'<li><a href=".">Contacn us</a></li>'+
    				'<li><a href="logout">LogOut</a></li>';

function getMenu(roleNumber) {
	// 0 = guest 
	// 1 - person
	// 2 - user
	// 3 - company
	// 4 - administrator
	if(roleNumber<0 || roleNumber>4)
		roleNumber = 0;
	

	
	var beginText = '<div id ="header">' +
						'<div id="logo">' + 
						'</div>' +
						'<div id="search_area">' + 
							'<input id="text_Area" type="text" placeholder="Search.."/>'+
							'<input id="button" type="button" value="search"/>'+
						'</div>' + 
					'</div>' + 
					'<div id="nav_area">'+
						'<ul class="clearfix">';
	var endText = '</ul>' +
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
   				'</div>';
	

	var menuToShow = beginText + topMenuArray[roleNumber] + endText;
	
	$(menuToShow).insertBefore("#right_side");

}



