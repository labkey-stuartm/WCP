/*

Name: 			theme.js
Written by: 	BTC - Maari Vanaraj
Version: 		1.0

*/

wow = new WOW(
  {
  boxClass:     'wow',      // default
  animateClass: 'animated', // default
  offset:       0,          // default
  mobile:       true,       // default
  live:         true        // default
}
)
wow.init();

$(document).ready(function(){
	//alert("Working..");
  $("#error").hide();  
 //Login left section
  var wht = $(window).height();
  $(".lg-space-left").css("height",wht) 
  
  var lsimg = $(".lg-space-img").innerHeight();
  var lscov = $(".lg-space-cover").innerHeight();
  var lst = $(".lg-space-txt").innerHeight();
  var ls = wht - lsimg - lscov - lst; 
  $(".lg-space-txt").css("margin-top",ls/2).css("margin-bottom",ls/2);
    

//Login right section  
  //var lbox = $(".lg-space-container").height();
 // var ptb = (wht - lbox - 5)/2;
 // $(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);
  
 //Landing page right section  
  var mlbox = $(".lg-space-center").height();
  var mtb = (wht - mlbox - 5)/2;
  $(".lg-space-center").css("margin-top",mtb).css("margin-bottom",mtb);
    
    
  //Login right section    
  var lgb = $(".login-box").height();  
  var bth = (wht - lgb)/2;
  $(".login-box").css("margin-top",bth).css("margin-bottom",bth);
  
//Password Reset Form section    
  var prfs = $("#passwordResetForm").height();  
  var bth = (wht - prfs)/2;
  $("#passwordResetForm").css("margin-top",bth).css("margin-bottom",bth);
  
  //Register page 
  var rlbox = $(".lg-register-center").height();
  var rptb = (wht - rlbox - 40)/2;
  $(".lg-register-center").css("margin-top",rptb).css("margin-bottom",rptb);
    
 $("#forgot_pwd").click(function(){
  $('#sucMsg').hide();
  $('#errMsg').hide();
  $(".login").addClass("dis-none");
  $(".pwd").removeClass("dis-none");
  resetValidation('#loginForm');
  resetValidation('#forgotForm');
  $('#loginForm input').val('');
});


$("#login").click(function(){
  $('#sucMsg').hide();
  $('#errMsg').hide();
  $(".login").removeClass("dis-none");
  $(".pwd").addClass("dis-none");
  resetValidation('#forgotForm');
  resetValidation('#loginForm');
  $('#forgotForm input').val('');
});
    
    
//Calculating right content width
var md_wd = $(".md-container").width();
var rc_wd = md_wd-255;
$(".right-content").css("width",rc_wd);

//Calculating right & left content height
var wd_ht = $(window).height();
//alert(wd_ht);
var hd_ht = $(".hd_con").height();
//alert(hd_ht);
var tit_ht = $(".tit_con").height();
//alert(tit_ht);
var ft_ht = $(".ft_con").height();
//alert(ft_ht);

var fix_nav = wd_ht -(hd_ht + tit_ht + ft_ht + 30);
//alert(fix_nav);
$(".left-content").css("height", fix_nav);
$(".right-content").css("height", fix_nav);
$(".right-content-body").css("height", fix_nav-74);


    
});
var rtime;
var timeout = false;
var delta = 200;

$(window).on('load resize', function(){    
 
	rtime = new Date();
    if (timeout === false) {
        timeout = true;
        setTimeout(resizeend, delta);
    }
    
});
function resizeend() {
    if (new Date() - rtime < delta) {
        setTimeout(resizeend, delta);
    } else {
        timeout = false;
        responsiveScreen();
//        slideUpStudyMenu();
    }               
}

function responsiveScreen() {
	//Login left section
	var wht = $(window).height();
	$(".lg-space-left").css("height",wht) 

	var lsimg = $(".lg-space-img").innerHeight();
	var lscov = $(".lg-space-cover").innerHeight();
	var lst = $(".lg-space-txt").innerHeight();
	var ls = wht - lsimg - lscov - lst;console.log(ls);  
	$(".lg-space-txt").css("margin-top",ls/2).css("margin-bottom",ls/2);
	    
	//Login right section  
	//  var lbox = $(".lg-space-container").height();
	//  var ptb = (wht - lbox - 5)/2;
	 // $(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);

	  //Landing page right section  
	  var mlbox = $(".lg-space-center").height();
	  var mtb = (wht - mlbox - 5)/2;
	  $(".lg-space-center").css("margin-top",mtb).css("margin-bottom",mtb);

	//Login right section    
	var lgb = $(".login-box").height();  
	var bth = (wht - lgb)/2;
	$(".login-box").css("margin-top",bth).css("margin-bottom",bth);
	    
	//Calculating right content width
	var md_wd = $(".md-container").width();
	//var rc_wd = md_wd-255;
	var rc_wd = md_wd-$('.left-content-container').width();
	$(".right-content").css("width",rc_wd);


	//Register page 
	var rlbox = $(".lg-register-center").height();
	var rptb = (wht - rlbox - 40)/2;
	$(".lg-register-center").css("margin-top",rptb).css("margin-bottom",rptb);


	//Calculating right & left content height
	var wd_ht = $(window).height();
	var hd_ht = $(".hd_con").height();
	var tit_ht = $(".tit_con").height();
	var ft_ht = $(".ft_con").height();

	var fix_nav = wd_ht -(hd_ht + tit_ht + ft_ht + 30);
	$(".left-content").css("height", fix_nav);
	$(".right-content").css("height", fix_nav);
	$(".right-content-body").css("height", fix_nav-74);
}

/*$(window).on('resize', function(){    
 
 //Login left section
  var wht = $(window).height();
  $(".lg-space-left").css("height",wht) 
  
  var lsimg = $(".lg-space-img").innerHeight();
  var lscov = $(".lg-space-cover").innerHeight();
  var lst = $(".lg-space-txt").innerHeight();
  var ls = wht - lsimg - lscov - lst;console.log(ls);  
  $(".lg-space-txt").css("margin-top",ls/2).css("margin-bottom",ls/2);
    
    
//Login right section  
  //var lbox = $(".lg-space-container").height();
 // var ptb = (wht - lbox - 5)/2;
  //$(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);
    
  
  //Landing page right section  
  var mlbox = $(".lg-space-center").height();
  var mtb = (wht - mlbox - 5)/2;
  $(".lg-space-center").css("margin-top",mtb).css("margin-bottom",mtb);

  //Login right section    
  var lgb = $(".login-box").height();  
  var bth = (wht - lgb)/2;
  $(".login-box").css("margin-top",bth).css("margin-bottom",bth);
  
  
  //Register page 
  var rlbox = $(".lg-register-center").height();
  var rptb = (wht - rlbox - 40)/2;
  $(".lg-register-center").css("margin-top",rptb).css("margin-bottom",rptb);
 
});*/
