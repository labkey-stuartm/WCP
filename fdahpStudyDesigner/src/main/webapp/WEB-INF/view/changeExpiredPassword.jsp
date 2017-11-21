<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<div class="lg-container">
        
        <!-- Login Left Section-->
        <div class="lg-space-left">
            <div class="lg-space-img">
                <img src="/fdahpStudyDesigner/images/logo/fda-logo-w.png"/>
            </div>
            <div class="lg-space-txt">
               FDA My Studies <br>Management Portal
            </div>
             <div class="lg-space-cover">
                <img src="/fdahpStudyDesigner/images/icons/web.png"/>
            </div>
        </div>
	<div class="lg-space-right">
		<div>
	        <!-- change password box-->
	    <input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
		<form:form id="passwordResetForm" data-toggle="validator" role="form" action="/fdahpStudyDesigner/changePassword.do" method="post" autocomplete="off">
			<div>
				<p>Your password has expired. You need to reset your password to proceed further.</p>
	        	<div class="mb-lg form-group">
	                <input type="password" class="form-control input-field wow_input" id="oldPassword" name="" maxlength="14"  data-minlength="8" placeholder="Old Password" data-error="Invalid old password." required
	                autocomplete="off"/>
	                <div class="help-block with-errors"></div>
	                <input type="hidden" name="oldPassword" id="hideOldPass" />
	            </div>
	            <div class="mb-lg form-group">
	                <input type="password" class="form-control input-field wow_input" id="password" name="" maxlength="14"  data-minlength="8" placeholder="Password" data-error="Password is invalid" required
	                pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~])[A-Za-z\d!&quot;#$%&amp;'()*+,-.:;&lt;=&gt;?@[\]^_`{|}~]{8,14}" autocomplete="off"/>
	                <div class="help-block with-errors"></div>
	                <span class="arrowLeftSugg"></span>
	            </div>
				<div class="mb-lg form-group">
	                <input type="password" class="form-control input-field wow_input" id="cfnPassword" name="" maxlength="14" data-match="#password" data-match-error="Whoops, these don't match" placeholder="Confirm password" 
	                 required  autocomplete="off"/>
	                <div class="help-block with-errors"></div>
	            </div>
	            <div class="mb-lg form-group">
	  				<button type="button" class="btn lg-btn" id="resetPasswordBut">Submit</button>
	            </div>
			</div>
			<input type="password" name="newPassword" id="hidePass" style="display: none;"/>
		</form:form>
	         <!-- change password box ends-->
  		</div>
  		<div class="clearfix"></div>
            
             <div class="footer">
                    <div><span>Copyright © 2017 FDA</span><span><a href="https://www.fda.gov/AboutFDA/AboutThisWebsite/WebsitePolicies/" class="" target="_blank">Terms</a></span><span><a href="https://www.fda.gov/AboutFDA/AboutThisWebsite/WebsitePolicies/#privacy" class="" target="_blank">Privacy Policy</a></span></div>
              </div>
  	</div>
  	</div>
  <script>
    	$(document).ready(function(e) {
    		addPasswordPopup();
    	});
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
		    $(document).find('.md-container.white-bg ')
    			.removeClass('md-container');
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