<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>
<input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
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
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/boostrap/bootstrap.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/datatable/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/dragula/dragula.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/magnific-popup/magnific-popup.css">        
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/animation/animate.css">    
        
    <!-- Theme Responsive CSS -->
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/sprites.css">   
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/layout.css">   
        
    <!-- Theme CSS -->
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/theme.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/css/style.css">
        
    <!-- Head Libs -->
    <script src="/fdahpStudyDesigner/vendor/modernizr/modernizr.js"></script>
        
</head>
<body class="white-bg">
     <form action="" name="studyListForm" id="studyListForm" method="post">
     </form>
    <div class="lg-container">
        
        <!-- Login Left Section-->
        <div class="lg-space-left">
            <div class="lg-space-img">
                <img src="/fdahpStudyDesigner/images/logo/fda-logo-w.png"/>
            </div>
            <div class="lg-space-txt">
               Health Study <br>Management Portal
            </div>
             <div class="lg-space-cover">
                <img src="/fdahpStudyDesigner/images/icons/web.png"/>
            </div>
        </div>
        <!-- End Login Left Section-->
        <div>
           LogOut 
        </div>
        <!-- Login Right Section-->
        <div class="lg-space-right">
            <div class="lg-space-container wd">
                
                <div class="lg-space-title">
                    <span>Welcome,</span><span>${sessionObject.firstName}</span>
                </div>
                <div class='lg-icons'> 
                   <ul class="lg-icons-list"> 
                    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_STUDIES')}">
                    <li>
                        <a class='studies-g' href='#' onclick="getStudyList()"></a>
                        <div>Manage Studies</div>
                    </li>
                    </c:if> 
                    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_REPO') || fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_REPO')}">
                    <li>
                        <a class='repository-g' href='#'></a>
                        <div>Manage Repository</div>
                    </li> 
                    </c:if>
                    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW') || fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
                    <li>
                        <a class='notifications-g' href='#'></a>
                        <div>Manage Notifications</div>
                    </li> 
                    </c:if>
                    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_STUDIES_VIEW') || fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_STUDIES_EDIT')}">
                    <li>
                        <a class='user-g' href='#'></a>
                        <div>Manage Users</div>
                    </li> 
                    </c:if>
                    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ACUITY_DASHBOARD')}">
                    <li>
                        <a class='account-g' href='#'></a>
                        <div>My Account</div>
                    </li>
                    </c:if> 
                 </ul> 
                </div>
            </div>
            <div class="footer">
                <span>Copyright © 2016 FDA</span><span><a href="#">Terms</a></span><span><a href="#">Privacy Policy</a></span>
            </div>
        </div>
        <!-- End Login Right Section-->
        
    </div>
    
    
    <!-- Vendor -->
    <script src="/fdahpStudyDesigner/vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/boostrap/bootstrap.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/animation/wow.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/dragula/react-dragula.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="/fdahpStudyDesigner/vendor/slimscroll/jquery.slimscroll.min.js"></script>
    
    
    <!-- Theme Custom JS-->
    <script src="/fdahpStudyDesigner/js/theme.js"></script>
    <script src="/fdahpStudyDesigner/js/common.js"></script>
    
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-71064806-1', 'auto');
      ga('send', 'pageview');
    </script>
    <script>
    $(document).ready(function(e) {
    	getStudyList(){
    		document.studyListForm.action="/fdahpStudyDesigner/adminStudies/geStudyList.do";
    		document.studyListForm.submit();
    	}
    	
    });
    </script>
</body>
</html>