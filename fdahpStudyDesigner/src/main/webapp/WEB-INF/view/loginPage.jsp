<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html class="overflow-hidden">
	<head>
    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
            
    <title>FDA HSMP</title>	
    
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">

    <!-- Favicon -->
    <link rel="shortcut icon" href="/fdahpStudyDesigner/images/icons/fav.jpg" type="image/x-icon" />
    <link rel="apple-touch-icon" href="/fdahpStudyDesigner/images/icons/fav.jpg">
        
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        
    <!-- Web Fonts  -->
   <link href="https://fonts.googleapis.com/css?family=Roboto:300,400" rel="stylesheet">
        
    <!-- Vendor CSS -->
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/boostrap/bootstrap.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/datatable/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/datatable/css/jquery.dataTables.min.css">
    
     <!-- Your custom styles (optional) -->
    <link href="/fdahpStudyDesigner/css/loader.css" rel="stylesheet">
    
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/datatable/css/rowReorder.dataTables.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/dragula/dragula.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/magnific-popup/magnific-popup.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/font-awesome/font-awesome.min.css"> 
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/select2/bootstrap-select.min.css">  
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/select2/bootstrap-multiselect.css">      
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/animation/animate.css">
        
    <!-- Theme Responsive CSS -->
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/layout.css">   
        
    <!-- Theme CSS -->
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/theme.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/style.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/sprites_icon.css">
        
    <!-- Head Libs -->
    <script src="/fdahpStudyDesigner/vendor/modernizr/modernizr.js"></script>
    
    <!-- Vendor -->
    <script src="/fdahpStudyDesigner/vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/boostrap/bootstrap.min.js"></script>
    <script src="/fdahpStudyDesigner/js/validator.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/animation/wow.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/datatable/js/jquery.dataTables.min.js"></script>
     <script src="/fdahpStudyDesigner/vendor/datatable/js/dataTables.rowReorder.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/dragula/react-dragula.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="/fdahpStudyDesigner/vendor/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/select2/bootstrap-select.min.js"></script>
     <script type="text/javascript" src="/fdahpStudyDesigner/js/loader.js"></script>
    
    
    
    
    
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-71064806-1', 'auto');
      ga('send', 'pageview');
    </script>
        
</head>
<body class="loading white-bg">
    <div id="loader"><span></span></div>
    <div class="lg-container">
        
        <!-- Login Left Section-->
        <div class="lg-space-left">
            <div class="lg-space-img">
                <img src="images/logo/fda-logo-w.png"/>
            </div>
            <div class="lg-space-txt">
               Health Study <br>Management Portal
            </div>
             <div class="lg-space-cover">
                <img src="images/icons/web.png"/>
            </div>
        </div>
        <!-- End Login Left Section-->
        
        <!-- Login Right Section-->
        <div class="lg-space-right">
            
            <div class="login-box">
             <c:url value='/j_spring_security_check' var="fdaLink"/>
             <form:form id="loginForm" data-toggle="validator" role="form" action="${fdaLink}"  name="loginForm" method="post" >  
                    <div id="errMsg" class="error_msg">${errMsg}</div>
                    <div id="sucMsg" class="suceess_msg">${sucMsg}</div>
                    <div class="login">
                        <div class="mb-lg form-group">
                            <input type="text" class="input-field wow_input" id="email" name="username" data-pattern-error="Email address is invalid" 
                            	placeholder="Email Address" required maxlength="100" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"  autofocus>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg form-group">
                            <input type="password" class="input-field wow_input" id="password"  
                        		placeholder="Password"  required maxlength="20" data-error="This field shouldn't be empty" autocomplete="off" name="password">
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg form-group">
                            <button type="submit" class="btn lg-btn" id="log-btn">Sign In</button>
                        </div>
                        <div class="pb-md">
                            <a id="forgot_pwd" class="gray-link" href="javascript:void(0)">Forgot Password?</a>
                        </div>
                   </div>
                </form:form>
                <form:form id="forgotForm" data-toggle="validator" role="form" action="forgotPassword.do" method="post" autocomplete="off">
                   <div class="pwd dis-none">
                     <div class="mb-lg">
                         <h3 class="mt-none text-weight-bold">Forgot Password?</h3>
                        <div class="gray-xs-f mt-md">Enter your Email address to get  a link to reset password</div>
                        </div>
                        <div class="mb-lg form-group">
                            <input type="text" class="input-field wow_input" id="emailReg" name="email" maxlength="100" placeholder="Email Address" 
                            data-pattern-error = "Email address is invalid"  required maxlength="100" 
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$">
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg">
                            <button type="submit" class="btn lg-btn" id="log-btn">Submit</button>
                        </div>
                        <div>
                            <a id="login" class="gray-link" href="javascript:void(0)">Back to Sign in</a>
                        </div>
                   </div>
              </form:form>   
            </div>
            
            
            <div class="clearfix"></div>
            
             <div class="footer">
                    <span>Copyright © 2017 FDA</span><span><a href="javascript:void(0)">Terms</a></span><span><a href="javascript:void(0)">Privacy Policy</a></span>
              </div>
             
        </div>
        <!-- End Login Right Section-->
        
    </div>
    
    
    <script src="/fdahpStudyDesigner/js/theme.js"></script>
    <script src="/fdahpStudyDesigner/js/jquery.mask.min.js"></script>
    <script src="/fdahpStudyDesigner/js/common.js"></script>
    <script src="/fdahpStudyDesigner/js/jquery.nicescroll.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/tinymce/tinymce.min.js"></script>
    <script src="/fdahpStudyDesigner/js/bootbox.min.js"></script>
    <script src="/fdahpStudyDesigner/js/autofill-event.js"></script>
   
   
   <script>
   		var isChanged = true;
    	$(document).ready(function(e) {
			
    		$("form").submit(function() {
        		$(this).submit(function() {
           	 		return false;
        		});
        		 	return true;
    		});
			$('input:last').change(function() {
				if(isChanged) {
					if($('#email').val()){
						setTimeout(function(){$('button').removeClass('disabled');}, 200);
					}
					isChanged = false;
				}
			});
    		
    		var errMsg = '${errMsg}';
			if(errMsg.length > 0){
				$("#errMsg").html(errMsg);
			   	$("#errMsg").show("fast");
			   	//$("#sucMsg").hide("fast");
			   	setTimeout(hideDisplayMessage, 4000);
			}
			var sucMsg = '${sucMsg}';
			if(sucMsg.length > 0){
				$("#sucMsg").html(sucMsg);
		    	$("#sucMsg").show("fast");
		    	$("#errMsg").hide("fast");
		    	setTimeout(hideDisplayMessage, 4000);
			}
			

		    // Internet Explorer 6-11
		    var isIE = /*@cc_on!@*/false || !!document.documentMode;
		    // Edge 20+
		    var isEdge = !isIE && !!window.StyleMedia;
		    
			$('#email').keyup(function(event){
				event = (event || window.event);
		    	if(event.keyCode == 13) {
					var isEmail = false;
					var emailAdd = $('#email').val();
					var regEX = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
			        isEmail = regEX.test(emailAdd);
			        if(emailAdd == ''){
			        	if(isIE || isEdge){
			        		$('#email').parent().find(".help-block").html("<ul class='list-unstyled'><li>This is a required field</li></ul>");
			        	} else {
			        		$('#email').parent().find(".help-block").html("<ul class='list-unstyled'><li>Please fill out this field</li></ul>");
			        	}
					} else if(!isEmail){
						$('#email').parent().find(".help-block").html("<ul class='list-unstyled'><li>Email address is invalid</li></ul>");
					}
		    	}
			});
			
			$('#emailReg').keyup(function(event){
				event = (event || window.event);
		    	if(event.keyCode == 13) {
					var isEmail = false;
					var emailAdd = $('#emailReg').val();
					var regEX = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
			        isEmail = regEX.test(emailAdd);
					if(emailAdd == ''){
						if(isIE || isEdge){
			        		$('#emailReg').parent().find(".help-block").html("<ul class='list-unstyled'><li>This is a required field</li></ul>");
			        	} else {
			        		$('#emailReg').parent().find(".help-block").html("<ul class='list-unstyled'><li>Please fill out this field</li></ul>");
			        	}
					} else if(!isEmail){
						$('#emailReg').parent().find(".help-block").html("<ul class='list-unstyled'><li>Email address is invalid</li></ul>");
					}
		    	}
			});
			
    	});
    	function hideDisplayMessage(){
			$('#sucMsg').hide();
			$('#errMsg').hide();
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
    </script>

</body>
</html>