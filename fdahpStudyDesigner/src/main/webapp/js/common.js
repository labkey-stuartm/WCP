/*

Name: 			common.js
Written by: 	BTC - Maari Vanaraj
Version: 		1.0

*/


$(window).on('load', function(){
     $(".error_msg").hide();
});

$(document).ready(function(){
	$('.phoneMask').mask('000-000-0000');
	$(".phoneMask").keyup(function(){
    	if($(this).val() == "000-000-0000" ){
    		$(this).val("");
    		$(this).parent().addClass("has-danger").addClass("has-error");
    		$(this).parent().find(".help-block").text("Invalid phone number");
    	}
    });
});









