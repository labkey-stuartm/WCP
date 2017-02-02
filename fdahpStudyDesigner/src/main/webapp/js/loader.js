jQuery(function($){
	// Calling the css3 loader upon page loading
	jQuery(window).load(function() {
      $("body").removeClass("loading");//  fade out
    });
	
});
$(document).ajaxStart(function() {
	var isOnline = navigator.onLine;
	if(isOnline){
		$("body").addClass("loading");
	}
});
$(document).ajaxStop(function() {
	$("body").removeClass("loading"); //  fade out
});
$(window).bind("load", function() {
	$("body").removeClass("loading"); //  fade out
});

function ajaxSessionTimeout()
{
	window.location.href = 'login.do';
}