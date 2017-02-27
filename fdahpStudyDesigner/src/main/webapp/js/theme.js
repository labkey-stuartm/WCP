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
	
	
	$("button").click(function(){
		if($("select").val()==""){
			$("select +div").html("Please select the option");
		}
	});
	
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
  var lbox = $(".lg-space-container").height();
  var ptb = (wht - lbox - 5)/2;
  $(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);
    
    
  //Login right section    
  var lgb = $(".login-box").height();  
  var bth = (wht - lgb)/2;
  $(".login-box").css("margin-top",bth).css("margin-bottom",bth);
    
 $("#forgot_pwd").click(function(){
  $(".login").addClass("dis-none");
  $(".pwd").removeClass("dis-none");
});


$("#login").click(function(){
  $(".login").removeClass("dis-none");
  $(".pwd").addClass("dis-none");
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

$(window).on('load', function(){    
 
//Login left section
var wht = $(window).height();
$(".lg-space-left").css("height",wht) 

var lsimg = $(".lg-space-img").innerHeight();
var lscov = $(".lg-space-cover").innerHeight();
var lst = $(".lg-space-txt").innerHeight();
var ls = wht - lsimg - lscov - lst;console.log(ls);  
$(".lg-space-txt").css("margin-top",ls/2).css("margin-bottom",ls/2);
    
//Login right section  
  var lbox = $(".lg-space-container").height();
  var ptb = (wht - lbox - 5)/2;
  $(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);


//Login right section    
var lgb = $(".login-box").height();  
var bth = (wht - lgb)/2;
$(".login-box").css("margin-top",bth).css("margin-bottom",bth);
    
//Calculating right content width
var md_wd = $(".md-container").width();
var rc_wd = md_wd-255;
$(".right-content").css("width",rc_wd);

//Calculating right & left content height
var wd_ht = $(window).height();
var hd_ht = $(".hd_con").height();
var tit_ht = $(".tit_con").height();
var ft_ht = $(".ft_con").height();

var fix_nav = wd_ht -(hd_ht + tit_ht + ft_ht + 30);
$(".left-content").css("height", fix_nav);
$(".right-content").css("height", fix_nav);
$(".right-content-body").css("height", fix_nav-74);
    
});


$(window).on('resize', function(){    
 
 //Login left section
  var wht = $(window).height();
  $(".lg-space-left").css("height",wht) 
  
  var lsimg = $(".lg-space-img").innerHeight();
  var lscov = $(".lg-space-cover").innerHeight();
  var lst = $(".lg-space-txt").innerHeight();
  var ls = wht - lsimg - lscov - lst;console.log(ls);  
  $(".lg-space-txt").css("margin-top",ls/2).css("margin-bottom",ls/2);
    
    
//Login right section  
  var lbox = $(".lg-space-container").height();
  var ptb = (wht - lbox - 5)/2;
  $(".lg-space-container").css("margin-top",ptb).css("margin-bottom",ptb);
    

  //Login right section    
  var lgb = $(".login-box").height();  
  var bth = (wht - lgb)/2;
  $(".login-box").css("margin-top",bth).css("margin-bottom",bth);
 
});
