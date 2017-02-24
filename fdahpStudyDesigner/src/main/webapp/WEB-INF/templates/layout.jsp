<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
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
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/boostrap/bootstrap.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/datatable/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/dragula/dragula.min.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/magnific-popup/magnific-popup.css">
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/font-awesome/font-awesome.min.css"> 
    <link rel="stylesheet" href="/fdahpStudyDesigner/vendor/select2/bootstrap-select.min.css">        
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
    <script src="/fdahpStudyDesigner/vendor/animation/wow.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/dragula/react-dragula.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="/fdahpStudyDesigner/vendor/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/fdahpStudyDesigner/vendor/select2/bootstrap-select.min.js"></script>
    
    <!-- Theme Custom JS-->
    <script src="/fdahpStudyDesigner/js/theme.js"></script>
    <script src="/fdahpStudyDesigner/js/jquery.mask.min.js"></script>
    <script src="/fdahpStudyDesigner/js/common.js"></script>
    
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-71064806-1', 'auto');
      ga('send', 'pageview');
    </script>
        
</head>
<body class="">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="subheader" />
			<!-- content start  -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
			    <div class="md-container white-bg">
			    <tiles:insertAttribute name="menu" />
			    <tiles:insertAttribute name="body" />
			    </div>
			</div> 
			 	
			<!-- /End content   -->
			<tiles:insertAttribute name="footer" />
		<input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
    </body>

</html>