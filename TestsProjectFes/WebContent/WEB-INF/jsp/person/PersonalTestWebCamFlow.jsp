<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="java.util.*, java.text.*,tel_ran.tests.controller.PersonalActions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>   
<head>
<title>TEST CONTROL MODE</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href='<c:url value="/static/css_folder/person_styles/WebCamStyle.css"></c:url>'
	rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
	var flagTestVideo = false;
	var flagMediaSupported = false;
	var flagCameraOn = false;
	var videoStreamUrl = false;
	var video;
	var waitTime = 3000; // TO DO !!!!! Action for Shot pictures and ScreenShot slyde by any avent on html or random time Shot
	var amountPictures = 6;
	var imagesCount = 1;
	var showId;
	var canvas;
	var context;
    var codeQuestionIdCount = 0;
	$(document)
			.ready(
					function() {
						canvas = document.getElementById('canvas');
						context = canvas.getContext('2d');
						video = document.getElementById('video');
						hideE("video");
						hideE("canvas");
						//hideE("button_test_video");
						hideE("start_test");
					    hideE("tableTest"); 
						
						/* $("#button_test_video").click(function() { // show or hide test div for development ...
							if (flagTestVideo && flagMediaSupported) {
								hideE("canvas");
								hideE("video");
								flagTestVideo = false;
							} else {
								showE("video");
								showE("canvas");
								flagTestVideo = true;
							}

						}); */
						$("#start_test")
								.click(
										function() {
											document.getElementById("message").innerHTML = "Sorry camera error,  testing impossible, close this window, and try again! ";
											//showE("video");											
											//showE("canvas");
											showE("tableTest");
											showE("tabTestPerson_0");
											//hideE("button_test_video");
											hideE('start_test');
											show();
											
											//window.open('test_run_page','_blank');
										});
						if (hasGetUserMedia()) { // check cam support , if ok - true
							flagMediaSupported = true;
						}

						if (flagMediaSupported) { // if cam supported switch on it

							//video = document.querySelector("#video");
							video = document.getElementById('video');
							navigator.getUserMedia = navigator.getUserMedia
									|| navigator.webkitGetUserMedia
									|| navigator.mozGetUserMedia
									|| navigator.msGetUserMedia;
							window.URL.createObjectURL = window.URL.createObjectURL
									|| window.URL.webkitCreateObjectURL
									|| window.URL.mozCreateObjectURL
									|| window.URL.msCreateObjectURL;
							navigator
									.getUserMedia(
											{
												video : true
											},
											function(stream) {
												videoStreamUrl = window.URL
														.createObjectURL(stream);
												//  video 
												video.src = videoStreamUrl;
												document
														.getElementById("message").innerHTML = 'Camera is worked, please press button to begin the test';
												//   showE('button_test_video');  // uncomment for debug :)
												showE('start_test');
											},
											function() { // end (stream)
												document
														.getElementById("message").innerHTML = "Sorry camera error,  testing impossible ";
												hideE('button_test');
											}); // end usermedia

						} else {
							document.getElementById("message").innerHTML = "Sorry camera not supported,  testing impossible ";
						//	hideE('button_test');
						} // end if (flagMediaSupported)	
							///// AJAX Tast Code Case Request/Response
						$(".send_button").click(function(){							
							var codeInText = $('#codeText_' + codeQuestionIdCount).val();		 
							$.ajax({type: "POST",
								url: "handler-code",
								data: "personCode=" + codeInText,
								success: function(response){
									// we have the response				
									if(response.status == "SUCCESS"){										
										 onchangeClick(1);	
										 codeQuestionIdCount++;										 
									}else if(response.status == "ERROR"){  
										alert("code wrong try again!!!");
									}		
								},
								error: function(e){
									alert('Error: ' + e);			
								}
							}); 
						});
					}); // end onload

	//////////// show an hide by id functions
	function showE(id) {
		var select_id = document.getElementById(id);
		select_id.style.display = 'inline';
	}
	function hideE(id) {
		var select_id = document.getElementById(id);
		select_id.style.display = 'none';
	}
	///////////////////////////////////////////

	function hasGetUserMedia() {
		// Note: Opera builds are unprefixed.
		return !!(navigator.getUserMedia || navigator.webkitGetUserMedia
				|| navigator.mozGetUserMedia || navigator.msGetUserMedia);
	}
	//----------start of tranfer links to server--------//	
	var arrayImg = new Array();
	var personImgId = 1;

	function show() {
		clearTimeout(showId);
		captureMe();
		if (imagesCount < amountPictures) {
			showId = setTimeout('show()', waitTime);
		} else {			
			document.getElementById("imageLinkText").innerHTML = arrayImg;// setting to array text vitch links
			// for sending to server <base64>
		}
	}
	
	var captureMe = function() {
		if (!videoStreamUrl)
			alert("stream error");
		context.translate(canvas.width, 0);
		context.scale(-1, 1);
		video = document.getElementById('video');
		context.drawImage(video, 0, 0, video.width, video.height);
		var base64dataUrl = canvas.toDataURL('image/png');
		//array of ImagesLinks <base64>
		arrayImg[personImgId] = base64dataUrl + "@end_of_link@";//@end_of_link@  -  delimiter for links
		personImgId++;

		context.setTransform(1, 0, 0, 1, 0, 0);
		//var img = new Image(); // send to server >>>>>>>>>>>>
		//img.src = base64dataUrl;
		
		//window.document.body.appendChild(img);// added  images on html page for test uncoment
		document.getElementById("message").innerHTML = "Test started, counter of images "
				+ imagesCount;
		imagesCount = imagesCount + 1;
	}
	////
	var counterQuestions = 0;
	function onchangeClick(incrementValue){		
		counterQuestions  +=  incrementValue;
	var elem;
	var lengthOfQuestionTableList =	document.getElementsByTagName("th").length;
	if(lengthOfQuestionTableList > counterQuestions){	
		$("#tabTestPerson_"+counterQuestions).show("slow");
		elem = eval(parseInt(counterQuestions)-1);		
		$("#tabTestPerson_"+elem).hide();
	}else{	
		document.getElementById("message").innerHTML = "All your answers and pictures is ready,<br> to saving please click on button 'Send Test'";
		$("#message").css("color","blue");
		$("#sendTestButton").css("display","block");
		$("#tabTestPerson_"+elem).hide();
	}	
	}	
</script>
</head>
<body>
    <h1>This your personal test list, don't close this page!!!</h1>
	<div id="tableTest" class="personTestForm">		
		<%= PersonalActions.GetTestTable() %>
	</div>		
	<div class="item">
		<video id="video" width="320" height="240" autoplay="autoplay"></video>
	</div>	
	<div class="item">
		<canvas id="canvas" width="320" height="240"></canvas>
	</div>	
	 <!--  <div>
		<input id="button_test_video" type="button" class="buttons"
			value="Test video" />
	</div>  -->
	<h1 id="message"></h1>
	<input type="button" id="start_test" class="buttons"
		value="Start your test" />
	<br>
	
</body>
</html>