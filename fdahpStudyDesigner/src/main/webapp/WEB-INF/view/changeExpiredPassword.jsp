<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<div class="aq-wrapper">
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBox">
			 <div id="displayMessage" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 txtcenter marT20">
		      		<div  class="bg-success" ></div>
		      		<div id="sucMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-success">
            			<div class="msg" style="padding-top: 4px !important;">${sucMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
        			<div id="errMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-danger">
            			<div class="msg" style="padding-top: 4px !important;">${errMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
			 </div>
        <!-- change password box-->
         
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0" >
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 loginFormBox marT50">
            <p>Your password has expired. You need to reset your password to proceed further.</p>
                <form:form id="forgotForm" data-toggle="validator" role="form" action="/fdahpStudyDesigner/changePassword.do" method="post" autocomplete="off">
                	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="password" class="form-control aq-inp" id="oldPassword" name="oldPassword" maxlength="14"  data-minlength="8" placeholder="Old Password" data-error="Invalid old password." required
                        autocomplete="off"/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="password" class="form-control aq-inp" id="password" name="newPassword" maxlength="14"  data-minlength="8" placeholder="Password" data-error="Password is invalid" required
                        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~])[A-Za-z\d!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~]{8,14}" autocomplete="off"/>
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
                </form:form>
            </div>
        </div>
         <!-- change password box ends-->
       </div>
  </div>
  <script>
    	$(document).ready(function(e) {
    	addPasswordPopup();
			var sucMsg = '${sucMsg}';
			if(sucMsg.length > 0){
				$("#sucMsg .msg").html(sucMsg);
		    	$("#sucMsg").show("fast");
		    	$("#errMsg").hide("fast");
		    	setTimeout(hideDisplayMessage, 5000);
			}
			var errMsg = '${errMsg}';
			if(errMsg.length > 0){
				$("#errMsg .msg").html(errMsg);
			   	$("#errMsg").show("fast");
			   	$("#sucMsg").hide("fast");
			   	setTimeout(hideDisplayMessage, 5000);
			}
    	});
    	function hideDisplayMessage(){
			$('.msg').parent().hide("fast");
		}
		 window.onload = function () {
		    if (typeof history.pushState === "function") {
		        history.pushState("jibberish", null, null);
		        window.onpopstate = function () {
		            history.pushState('newjibberish', null, null);
		            // Handle the back (or forward) buttons here
		            // Will NOT handle refresh, use onbeforeunload for this.
		        };
		    }
		    else {
		        var ignoreHashChange = true;
		        window.onhashchange = function () {
		            if (!ignoreHashChange) {
		                ignoreHashChange = true;
		                window.location.hash = Math.random();
		                // Detect and redirect change here
		                // Works in older FF and IE9
		                // * it does mess with your hash symbol (anchor?) pound sign
		                // delimiter on the end of the URL
		            }
		            else {
		                ignoreHashChange = false;   
		            }
		        };
		    }
		}
		
		var addPasswordPopup = function() {
   		 $("#password").passwordValidator({
   				// list of qualities to require
   				require: ['length', 'lower', 'upper', 'digit','spacial'],
   				// minimum length requirement
   				length: 8
   			});
   		}
    </script>