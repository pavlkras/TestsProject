<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/static/css/general.css" />" rel="stylesheet">
<link href="<c:url value="/static/css/choose.css" />" rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="<c:url value="/static/js/add.js" />"></script>
<script>
var flagTestVideo = false;
var flagMediaSupported = false;
var flagCameraOn = false;
var videoStreamUrl = false;
var video;
var waitTime = 5000;
var imagesCount = 1;
var showId;
var canvas; 
var context; 

$(document).ready(function(){ 
canvas = document.getElementById('canvas');
context = canvas.getContext('2d');
video = document.getElementById('video');
hideE("video");
hideE("canvas");
hideE("button_test_video");
hideE("start_test");

$("#button_test_video").click(function(){ // show or hide test div for development ...
	if(flagTestVideo && flagMediaSupported) {
		hideE("canvas");hideE("video"); flagTestVideo = false;
		} else {showE("video"); showE("canvas");flagTestVideo =true;}
  
});
$("#start_test").click(function(){ 	
	document.getElementById("message").innerHTML="Test started, do not close this window!!! ";
	// showE("video");
	// showE("canvas");
	hideE("button_test_video");
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
navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
window.URL.createObjectURL = window.URL.createObjectURL || window.URL.webkitCreateObjectURL || window.URL.mozCreateObjectURL || window.URL.msCreateObjectURL;
navigator.getUserMedia({video: true}, function (stream) {
  
    videoStreamUrl = window.URL.createObjectURL(stream);
    // устанавливаем как источник для video 
    video.src = videoStreamUrl;
    document.getElementById("message").innerHTML='Camera is worked, please press link <next> for testing ';
 //   showE('button_test_video');  // uncomment for debug :)
    showE('start_test'); 
}, function () { // end (stream)
	document.getElementById("message").innerHTML="Sorry camera error,  testing impossible ";
	hideE('button_test');
}); // end usermedia

} else {
	document.getElementById("message").innerHTML="Sorry camera not supported,  testing impossible ";
	hideE('button_test');
} // end if (flagMediaSupported)
}); // end onload



//////////// show an hide by id functions
function showE(id){
    var select_id = document.getElementById(id);
    select_id.style.display = 'inline';
}
function hideE(id){
    var select_id = document.getElementById(id);
    select_id.style.display = 'none';
}
///////////////////////////////////////////

function hasGetUserMedia() {
	  // Note: Opera builds are unprefixed.
	  return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
	            navigator.mozGetUserMedia || navigator.msGetUserMedia);
}
function show() {
    clearTimeout(showId);
    captureMe();
    if( imagesCount < 6) {
    showId=setTimeout('show()',waitTime);
    } else {
    	 document.getElementById("message").innerHTML=" All pictures is ready ... end of test";
    	 
    }
}
var captureMe = function () {
	
	if(!videoStreamUrl) alert("stream error");
	
	context.translate(canvas.width, 0);
    context.scale(-1, 1);
    video = document.getElementById('video');
    context.drawImage(video, 0, 0, video.width, video.height);
    var base64dataUrl = canvas.toDataURL('image/png');
    context.setTransform(1, 0, 0, 1, 0, 0); 
    var img = new Image(); // send to server >>>>>>>>>>>>
    img.src = base64dataUrl;
    window.document.body.appendChild(img);
    document.getElementById("message").innerHTML="Test started, do not close this window!!! " + imagesCount;
    imagesCount = imagesCount + 1; 
}
	</script>

<style>
.buttons {
	border: 1px solid #0a3c59;
	background: #3e779d;
	background: -webkit-gradient(linear, left top, left bottom, from(#65a9d7),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #65a9d7, #3e779d);
	background: -moz-linear-gradient(top, #65a9d7, #3e779d);
	background: -ms-linear-gradient(top, #65a9d7, #3e779d);
	background: -o-linear-gradient(top, #65a9d7, #3e779d);
	background-image: -ms-linear-gradient(top, #65a9d7 0%, #3e779d 100%);
	padding: 3px 3px;
	-webkit-border-radius: 7px;
	-moz-border-radius: 7px;
	border-radius: 7px;
	-webkit-box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	-moz-box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	box-shadow: rgba(255, 255, 255, 0.4) 0 1px 0, inset
		rgba(255, 255, 255, 0.4) 0 0px 0;
	text-shadow: #7ea4bd 0 1px 0;
	color: #06426c;
	font-size: 14px;
	font-family: helvetica, serif;
	text-decoration: none;
	vertical-align: middle;
	width: 100px;
	margin: 20px;
}

.buttons:hover {
	border: 1px solid #0a3c59;
	text-shadow: #1e4158 0 1px 0;
	background: #3e779d;
	background: -webkit-gradient(linear, left top, left bottom, from(#65a9d7),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #65a9d7, #3e779d);
	background: -moz-linear-gradient(top, #65a9d7, #3e779d);
	background: -ms-linear-gradient(top, #65a9d7, #3e779d);
	background: -o-linear-gradient(top, #65a9d7, #3e779d);
	background-image: -ms-linear-gradient(top, #65a9d7 0%, #3e779d 100%);
	color: #fff;
}

.buttons:active {
	text-shadow: #1e4158 0 1px 0;
	border: 1px solid #0a3c59;
	background: #65a9d7;
	background: -webkit-gradient(linear, left top, left bottom, from(#3e779d),
		to(#3e779d));
	background: -webkit-linear-gradient(top, #3e779d, #65a9d7);
	background: -moz-linear-gradient(top, #3e779d, #65a9d7);
	background: -ms-linear-gradient(top, #3e779d, #65a9d7);
	background: -o-linear-gradient(top, #3e779d, #65a9d7);
	background-image: -ms-linear-gradient(top, #3e779d 0%, #65a9d7 100%);
	color: #fff;
}

#videoElement {
	margin: 0px auto;
	width: 320px;
	height: 240px;
	background-color: #666;
	border: 10px #333 solid;
}

body {
	text-align: center;
	font-family: arial;
	font-family: "Abel", Helvetica, sans-serif;
}

.stuff {
	display: none;
}

.item {
	display: inline-block;
	width: 300px;
	margin: 20px 10px;
	background: white;
	padding: 10px 0px;
}

.item span {
	padding-bottom: 15px;
	display: block;
	font-size: 36px;
	color: rgb(100, 100, 100)
}

video, canvas {
	width: 266px;
	height: 200px;
	background: rgba(0, 0, 0, 0.1);
}

video {
	transform: scaleX(-1);
	-o-transform: scaleX(-1);
	-ms-transform: scaleX(-1);
	-moz-transform: scaleX(-1);
	-webkit-transform: scaleX(-1);
}

img {
	width: 128px;
	height: 96px;
	margin: 6px;
}
</style>
<title>WEB_CAM test page, DO NOT CLOSE!</title>
</head>
<body>
	<div class="header">
		<h1 class="title">camera page</h1>
		<h3 class="subtitle">Checking web camera features, if OK, then
			pass to the next page, in the new tab (this page must be online
			during test )</h3>
	</div>
	<div>
		<input id="button_test_video" type="button" class="buttons"
			value="Test video" />
	</div>
	<div class="item item1">
		<video id="video" width="320" height="240" autoplay="autoplay"></video>
	</div>
	<div class="item item2">
		<canvas id="canvas" width="320" height="240"></canvas>
	</div>
	<h1 class="title" id="message">To continue , needed the web
		camera. Please let use the camera at the top of the page.</h1>

	<!-- <input type="button" id = "start_test" class = "buttons" value="Start test" onclick="window.location.href='test_run'"/> original link -->
	<input type="button" id="start_test" class="buttons" value="Start test"
		onclick="window.location.href='jobSeeker_test_preparing_click_event'" />

</body>
</html>