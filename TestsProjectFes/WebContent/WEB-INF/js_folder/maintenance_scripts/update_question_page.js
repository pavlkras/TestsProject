/**
 *    update page script
 */
 function test(questionId) {	    	
    	var EDIT_Q = document.getElementsByName("edit_q")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:inline-block";
    	EDIT_Q.setAttributeNode(att);
    	
    	var FORM_C = document.getElementsByName("questionID")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_C.setAttributeNode(att);
    	
    	var DELETE_Q = document.getElementsByName("delete_q")[0];
    	var att = document.createAttribute("style");
    	att.value = "display:inline-block";
    	DELETE_Q.setAttributeNode(att);
    	
    	var FORM_B = document.getElementsByName("questionIDdelete")[0];
    	var att = document.createAttribute("value");
    	att.value = questionId;
    	FORM_B.setAttributeNode(att);
      }  