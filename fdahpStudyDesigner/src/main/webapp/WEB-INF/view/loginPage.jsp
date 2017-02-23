<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html class="overflow-hidden">
	<head>
        
    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
            
    <title>FDA</title>	
    
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">

    <!-- Favicon -->
    <link rel="shortcut icon" href="#" type="image/x-icon" />
    <link rel="apple-touch-icon" href="#">
        
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        
    <!-- Web Fonts  -->
   <link href="https://fonts.googleapis.com/css?family=Roboto:300,400" rel="stylesheet">
        
    <!-- Vendor CSS -->
    <link rel="stylesheet" href="vendor/boostrap/bootstrap.min.css">
    <link rel="stylesheet" href="vendor/datatable/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="vendor/dragula/dragula.min.css">
    <link rel="stylesheet" href="vendor/magnific-popup/magnific-popup.css">        
    <link rel="stylesheet" href="vendor/animation/animate.css">    
        
    <!-- Theme Responsive CSS -->
    <link rel="stylesheet" href="css/layout.css">   
        
    <!-- Theme CSS -->
    <link rel="stylesheet" href="css/theme.css">
    <link rel="stylesheet" href="css/style.css">
        
    <!-- Head Libs -->
    <script src="vendor/modernizr/modernizr.js"></script>
        
</head>
<body class="white-bg">
    
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
             <form:form id="loginForm" data-toggle="validator" role="form" action="${fdaLink}"  name="loginForm" method="post" autocomplete="off">  
                    <div class="login">
                        <div class="mb-lg form-group">
                            <input type="text" class="input-field wow_input" id="email" name="username" data-error="E-mail address is invalid" placeholder="E-mail Address" required maxlength="100" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autocomplete="off">
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg form-group">
                            <input type="password" class="input-field wow_input" id="password"  
                        		placeholder="Password"  required maxlength="20" data-error="This field shouldn't be empty" autocomplete="off" name="password">
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg form-group">
                            <!-- <button type="submit" id="log-btn" class="lg-btn">Sign In</button> -->
                            <button type="submit" class="lg-btn" id="log-btn">Sign In</button>
                        </div>
                        <div class="pb-md">
                            <a id="forgot_pwd" class="gray-link" href="#">Forgot Password?</a>
                        </div>
                   </div>
                </form:form>
                <form:form id="forgotForm" data-toggle="validator" role="form" action="forgotPassword.do" method="post" autocomplete="off">
                   <div class="pwd dis-none">
                     <div class="mb-lg">
                         <h3 class="mt-none">Forgot Password?</h3>
                        <div class="gray-xs-f mt-md">Enter your E-mail address to get  a link to reset password</div>
                        </div>
                        <div class="mb-lg form-group">
                            <input id="email" type="text" class="input-field wow_input" pattern="/^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/" placeholder="Email Address" required/>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mb-lg">
                            <button id="log-btn" class="lg-btn">Submit</button>
                        </div>
                        <div>
                            <a id="login" class="gray-link" href="#">Back to Sign in</a>
                        </div>
                   </div>
              </form:form>   
            </div>
            
            
            <div class="clearfix"></div>
            
             <div class="footer">
                    <span>Copyright © 2016 FDA</span><span><a href="#">Terms</a></span><span><a href="#">Privacy Policy</a></span>
              </div>
             
        </div>
        <!-- End Login Right Section-->
        
    </div>
    
    
    <!-- Vendor -->
    <script src="vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="vendor/boostrap/bootstrap.min.js"></script>
    <script src="vendor/animation/wow.min.js"></script>
    <script src="vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="vendor/dragula/react-dragula.min.js"></script>
    <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="vendor/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="js/validator.min.js"></script>
    
    <!-- Theme Custom JS-->
    <script src="js/theme.js"></script>
    <script src="js/common.js"></script>
    <script src="js/custom.js"></script>
   
   
   <script>
    	$(document).ready(function(e) {
    		$("#loginForm input:first").focus();
    		$("form").submit(function() {
        		$(this).submit(function() {
           	 		return false;
        		});
        		 	return true;
    		});
    	});
    </script>

</body>
</html>