<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
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
    
    <!-- Vendor -->
    <script src="vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="vendor/boostrap/bootstrap.min.js"></script>
    <script src="vendor/animation/wow.min.js"></script>
    <script src="vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="vendor/dragula/react-dragula.min.js"></script>
    <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="vendor/slimscroll/jquery.slimscroll.min.js"></script>
    
    <!-- Theme Custom JS-->
    <script src="js/theme.js"></script>
    <script src="js/common.js"></script>
    
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-71064806-1', 'auto');
      ga('send', 'pageview');
    </script>
        
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
        <c:url value='/j_spring_security_check' var="actionLink"/>
           <form:form id="loginForm" data-toggle="validator" role="form" action="${actionLink}"  name="loginForm" method="post" autocomplete="off">
            <div class="login">
                <div class="lg-space-container">                    
                    <div class="mb-lg">
                        <input type="text" class="input-field wow wow_input" id="email" name="username" data-wow-duration="1s" data-error="E-mail address is invalid" 
                        placeholder="E-mail Address" required maxlength="100" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autocomplete="off">
                        <div class="error"></div>
                    </div>
                    <div class="mb-lg">
                        <input type="password" class="input-field wow wow_input" name="password" id="password" placeholder="Password"  data-error="This field shouldn't be empty" required maxlength="20" data-wow-duration="1s" 
                        autocomplete="off">
                        <div class="error"></div>
                    </div>
                    <div class="mb-lg">
                        <button id="log-btn" class="lg-btn">Sign In</button>
                    </div>
                    <div>
                        <a class="gray-link" href="#">Forgot Password?</a>
                    </div>
                </div>
                <div id="error" class="wow slideInRight"><span><img src="images/icons/warning.png"/></span> ${errMsg}</div>
            </div>
           </form:form>            
        </div>
        <!-- End Login Right Section-->
        
    </div>
    <script>
    	$(document).ready(function(e) {
    		$("#loginForm input:first").focus();
    		$("form").submit(function() {
        		$(this).submit(function() {
           	 		return false;
        		});
        		 	return true;
    		});
    		var a = '${errMsg}';
    		if(a){
    			$("#error").show();
    		}
    	});
    </script>	
</body>
</html>