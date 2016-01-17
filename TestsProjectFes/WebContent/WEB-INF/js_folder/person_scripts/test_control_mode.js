/** 
 *  
 */
var waitTime = 7000; // TO DO !!!!! Action for Shot pictures and ScreenShot slyde by any avent on html or random time Shot
var AMOUNT_PICTURES = 6;
var flagTestVideo = false;
var flagMediaSupported = false;
var flagCameraOn = false;
var videoStreamUrl = false;
////
var video;
var canvas;
var context;
var showId;
var imagesCount = 1;
var codeQuestionIdCount = 0;
var picturesArray = new Array();
var screenPicture = new Array();

$(document)
.ready(
		function() {
			canvas = document.getElementById('canvas');
			context = canvas.getContext('2d');
			video = document.getElementById('video');
			hideE("video");
			hideE("canvas");
			hideE("start_test");
			hideE("tableTest"); 
			////
			$("#start_test")
			.click(
					function() {
						showE("tableTest");
						showE("tabTestPerson_0");
						hideE('start_test');
						show();
					});
			////
			if (hasGetUserMedia()) { // check cam support , if ok - true
				flagMediaSupported = true;
			}

			if (flagMediaSupported) { // if cam supported switch on it
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
							////  video 
							video.src = videoStreamUrl;
							document.getElementById("message").innerHTML = 'Camera is worked, please press button to begin the test';
							////
							showE('start_test');
						},
						function() { // end (stream)
							document.getElementById("message").innerHTML = "Sorry camera error,  testing impossible ";
							hideE('button_test');
						}); // end usermedia

			} else {
				document.getElementById("message").innerHTML = "Sorry camera not supported,  testing impossible ";						
			} 
			// end if (flagMediaSupported)	
			///// AJAX Tast Code Case Request/Response
			$(".send_button").click(function(){							
				var $codeInText = $('#codeText_' + codeQuestionIdCount).val();	
				var $questionID = $('#idQuestion_' + codeQuestionIdCount).text();

				$.ajax({type: "POST",
					url: "handler-code",
					data: {"personCode" : $codeInText , "questionID" : $questionID },					
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
	//select_id.style.display = 'none';
}
///////////////////////////////////////////

function hasGetUserMedia() {
	// Note: Opera builds are unprefixed.
	return !!(navigator.getUserMedia || navigator.webkitGetUserMedia
			|| navigator.mozGetUserMedia || navigator.msGetUserMedia);
}
//----------start of tranfer links to server--------//	

function show() {
	clearTimeout(showId);
	captureMe();
	if (imagesCount < AMOUNT_PICTURES) {
		showId = setTimeout('show()', waitTime);// TO DO action for shot photo for sending and saving
	} else {				
		alert("end");
	} 
}	
var captureMe = function() {
	if (!videoStreamUrl)
		alert("stream error");
	context.translate(canvas.width, 0);
	context.scale(-1, 1);
	video = document.getElementById('video');
	context.drawImage(video, 0, 0, video.width, video.height);

	////// send to server >>>>>>>>>>>>
	var base64dataUrl = canvas.toDataURL('image/png');		
	picturesArray = base64dataUrl + "@END_LINE@";
	screenPicture = "@END_LINE@";
	 
	var img = new Image(); 
	img.src = base64dataUrl;
	window.document.body.appendChild(img);// added  images on html page for test uncoment
	context.setTransform(1, 0, 0, 1, 0, 0);
}

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
		document.getElementById("personPictures").innerHTML = picturesArray;
		document.getElementById("screenPictures").innerHTML = screenPicture;
		$("#message").css("color","blue");
		$("#sendTestButton").css("display","block");
		$("#tabTestPerson_"+elem).hide();
	}	
}	
