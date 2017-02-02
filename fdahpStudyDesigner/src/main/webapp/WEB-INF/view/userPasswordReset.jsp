<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
 <head>
     	<meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <meta http-equiv="x-ua-compatible" content="ie=edge">
	   <link rel="shortcut icon" href="images/fav-icon-acuity.png" type="image/x-icon">
	
	    <title>Studies Gateway Web</title>
	    
	    
	    <!-- Bootstrap core CSS -->
	    <link href="css/bootstrap.min.css" rel="stylesheet">
	
	    <!--bootstarp select -->
	    <link href="css/bootstrap-select.min.css" rel="stylesheet">
	    
	    <!-- Material Design Bootstrap -->
	    <link href="css/jquery.dataTables.min.css" rel="stylesheet">
	    
	     <link href="css/validation.css" rel="stylesheet">
	    
	    <!-- Your custom styles (optional) -->
	    <link href="css/style.css" rel="stylesheet">
	    <link rel="stylesheet" href="css/jquery-password-validator.css"></link>
   <style type="text/css">
   
   
   </style>     
<script type="text/javascript">
  function noBack() { 
	  history.pushState(null, null, 'login.do');
	   window.addEventListener('popstate', function(event) {
	     history.pushState(null, null, 'login.do');
	  }); 
  }
  if (typeof(Storage) != "undefined" ) {
	  localStorage.clear();
  }
  
</script>

</head>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBanner">
    <div class="acutyBannerColor"></div>
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBoxHeight">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBoxParent">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBox">
        	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 txtcenter">
            	<img src="images/acutyLogo.png">
            </div>
            <%-- <div id="displayMessage" class="${messageClass} col-md-12">
		      		<div id="sucMsg" class="bg-success" style="display:none;"></div>
					<div id="errMsg" class="bg-danger" style="display:none;"></div>
			 </div> --%>
			 <div id="displayMessage" class="${messageClass} col-md-12 pad0 txtcenter marT20">
		      		<div  class="bg-success" ></div>
		      		<div id="sucMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-success">
            			<div class="msg" style="padding-top: 4px !important;">${sucMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
        			<div id="errMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-danger">
            			<div class="msg" style="padding-top: 4px !important;">${errMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
			 </div>
        <!-- forgot password box-->
         
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0" >
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 loginFormBox marT50">
            <c:if test="${isValidToken}">
            <p>To begin using the services on  and complete your account setup process, kindly use the access code provided on your email and set up your account password.</p>
                <form:form id="forgotForm" data-toggle="validator" role="form" action="addPassword.do" method="post" autocomplete="off">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="text" class="form-control aq-inp" id="" name="accessCode" maxlength="6" placeholder="Access Code" data-error="Access Code is invalid" required autocomplete="off"/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                    
                        <input type="password" class="form-control aq-inp" id="password" name="password" maxlength="14"  data-minlength="8" placeholder="Password"  required
                        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~])[A-Za-z\d!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~]{7,13}" autocomplete="off" data-error="Password is invalid" />
                        <div class="help-block with-errors"></div>
                        <span class="arrowLeftSugg"></span>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="password" class="form-control aq-inp" id="" name="" maxlength="14" data-match="#password" data-match-error="Whoops, these don't match" placeholder="Confirm password" 
                         required  autocomplete="off"/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">
                            <button type="submit" class="btn aq-btn" id="ResetPassword">Submit</button>
                    </div>
                    <c:if test="${not empty isFirstLogin}">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">
                            <span>By clicking submit you accept the<a href="javascript:void(0)" class="termsClk"> 
                            Terms Of Use</a> and<a href="javascript:void(0)" class="privacyClk"> Privacy policy </a></span>
                    </div>
                    </c:if>
                    <input type="hidden" name="securityToken" value="${securityToken}" />
                </form:form>
              </c:if>
              <c:if test="${not isValidToken}"><p class="txtRed"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>The Password Reset Link is either expired or invalid.</p></c:if>
            </div>
              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 txtcenter marT20 backToLogin">
                    <a class="backToLogin" href="javascript:void(0)" id="backToLogin">Back To Login</a>
              </div>
        </div>
         <!-- forgot password box ends-->
       </div>
    </div>
    </div>
      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 footer">
          <div class="col-lg-7 col-md-12 col-sm-12 col-xs-12 pad0 txtright" style="margin-left: -36px;">
        	Copyright <span class="fontC">&copy;</span> @ 2017 FDA.
          </div>
          <div class="col-lg-5 col-md-12 col-sm-12 col-xs-12 txtright marL20 pad0">
             <a href="javascript:void(0)" class="termsClk">Terms Of Use</a> | <a href="javascript:void(0)" class="privacyClk">Privacy Policy</a>
          </div>
       </div>
    </div>
    <!-- /login-->
    
    <form:form action="/acuityLink/privacyPolicy.do" id="privacyForm" name="privacyForm" method="post" target="_blank">
	</form:form>
	
	<form:form action="/acuityLink/termsAndCondition.do" id="termsForm" name="termsForm" method="post" target="_blank">
	</form:form>
	
	<form:form action="/acuityLink/login.do" id="backToLoginForm" name="backToLoginForm" method="post">
	</form:form>
    

    <!-- SCRIPTS -->

    <!-- JQuery -->
    <script type="text/javascript" src="js/jquery-2.2.3.min.js" ></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.2/underscore-min.js"></script>
    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js" defer></script>
    
    <!-- bootstrap select -->
    <script type="text/javascript" src="js/bootstrap-select.min.js"></script>
    
    <!-- Bootstrap validation JavaScript -->
    <script type="text/javascript" src="js/validator.min.js" defer></script>
    <script type="text/javascript" src="js/jquery.mask.min.js"></script>
    
    <!--common js-->
	<script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/jquery.password-validator.js"></script>
    <script>
    	$(document).ready(function(e) {
			/*document.body.style.setProperty("--theme-color", "#f00");*/
			var sucMsg = '${sucMsg}';
			if(sucMsg.length > 0){
				$("#sucMsg .msg").html(sucMsg);
		    	$("#sucMsg").show("fast");
		    	$("#errMsg").hide("fast");
		    	setTimeout(hideDisplayMessage, 5000);
			}
			var errMsg = '${errMsg}';
			console.log(errMsg);
			if(errMsg.length > 0){
				$("#errMsg .msg").html(errMsg);
			   	$("#errMsg").show("fast");
			   	$("#sucMsg").hide("fast");
			   	setTimeout(hideDisplayMessage, 5000);
			}
	        $("#password").passwordValidator({
				// list of qualities to require
				require: ['length', 'lower', 'upper', 'digit','spacial'],
				// minimum length requirement
				length: 8
			});  
			
			$('.privacyClk').on('click',function(){
				$('#privacyForm').submit();
			});
			
			$('.termsClk').on('click',function(){
				$('#termsForm').submit();
			}); 
			
			$('#backToLogin').on('click',function(){
				$('#backToLoginForm').submit();
			});
        });
    	function hideDisplayMessage(){
			$('.msg').parent().hide("fast");
		}
    </script>
</body>
</html>