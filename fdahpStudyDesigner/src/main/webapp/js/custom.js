/*

Name: 			custom.js
Written by: 	BTC - Maari Vanaraj
Version: 		1.0

*/


$(".error_msg").css("display","none");

//toggling Error message
 $("#log-btn").click(function(){
     
     //alert("Working");
 
     if($("#email").val() =="" && !ValidateEmail($("#email").val())){
         
          $(".error_msg").attr("style","display:block !important;");
          $(".error_msg").text("Invalid Username! Please enter valid Email Id!!");
          $("#email").addClass("r_bor");
         
        }else if(!ValidateEmail($("#email").val())){
         $(".error_msg").attr("style","display:block !important;");    
         $(".error_msg").text("Invalid Email format!!");
         $("#email").addClass("r_bor");
         $("#password").removeClass("r_bor");
            
        }else if($("#password").val() ==""){
         $(".error_msg").attr("style","display:block !important;");        
         $(".error_msg").css("display","block");
         $(".error_msg").text("Invalid password! Please enter valid password!!");
         $("#password").addClass("r_bor");
         $("#email").removeClass("r_bor");
            
        }
        else{
        $(".error_msg").attr("style","display: none !important;");
        $("#email,#password").removeClass("r_bor");            
    }
 
}); 


var emailId = $("#email").val();
function ValidateEmail(emailId) {
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return expr.test(emailId);
}
 