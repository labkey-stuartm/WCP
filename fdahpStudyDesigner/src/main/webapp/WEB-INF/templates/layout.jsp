<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
    <head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <meta http-equiv="x-ua-compatible" content="ie=edge">
	    <link rel="shortcut icon" href="../images/fav-icon-acuity.png" type="image/x-icon">
	
	    <title>Studies Gateway Web</title>
	    
	    <link href="https://fonts.googleapis.com/css?family=Titillium+Web:200,200i,300,300i,400,400i,600,600i,700,700i,900" rel="stylesheet">
	    
	    <!-- fontawesome CSS -->
    	<link href="../css/font-awesome.min.css" rel="stylesheet">
	    
	    <!-- Bootstrap core CSS -->
	    <link href="/acuityLink/css/bootstrap.min.css" rel="stylesheet">
	
	    <!--bootstarp select -->
	    <link href="/acuityLink/css/bootstrap-select.min.css" rel="stylesheet">
	    
	    <!-- Material Design Bootstrap -->
	    <link href="/acuityLink/css/jquery.dataTables.min.css" rel="stylesheet">
	    
	     <link href="/acuityLink/css/validation.css" rel="stylesheet">
	    
	    <!-- Your custom styles for notification -->
	    <link href="/acuityLink/css/notification.css" rel="stylesheet">
	    
	    <link href="/acuityLink/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	    
	    <!-- Your custom styles (optional) -->
    	<link href="/acuityLink/css/loader.css" rel="stylesheet">
    	
    	<!-- Your custom reordercss (optional) -->
	    <link href="/acuityLink/css/rowReorder.dataTables.min.css" rel="stylesheet">
	    
	    <link href="/acuityLink/css/daterangepicker.css" rel="stylesheet">
	    
	    <!-- Your custom styles (optional) -->
	    <link href="/acuityLink/css/style.css" rel="stylesheet">
	    <link rel="stylesheet" href="/acuityLink/css/jquery-password-validator.css"></link>
     <!-- JQuery -->
	    <script type="text/javascript" src="/acuityLink/js/jquery-1.12.4.js"></script>
	    <!-- <script type="text/javascript" src="/acuityLink/js/jquery-2.2.3.min.js"></script> -->
	
	
	    <!-- Bootstrap core JavaScript -->
	    <script type="text/javascript" src="/acuityLink/js/bootstrap.min.js"></script>
	    
	    <!-- bootstrap select -->
	    <script type="text/javascript" src="/acuityLink/js/bootstrap-select.min.js"></script>
	    
	    <!-- Datatables -->
	    <script type="text/javascript" src="/acuityLink/js/jquery.dataTables.min.js"></script>
	    
	    <!-- validation js  -->
		<!-- Bootstrap validation JavaScript -->
    	<script type="text/javascript" src="/acuityLink/js/validator.min.js" defer></script>
    
	    <!--common js-->
		<script type="text/javascript" src="/acuityLink/js/common.js"></script>
		
		<script type="text/javascript" src="/acuityLink/js/ajaxRequestInterceptor.js"></script>
		
		<script type="text/javascript" src="/acuityLink/js/in-app-notification.js"></script>
		
		<script type="text/javascript" src="/acuityLink/js/bootbox.min.js" defer></script> 
		
		<script type="text/javascript" src="/acuityLink/js/moment.js"></script> 
		
		<script type="text/javascript" src="/acuityLink/js/bootstrap-datetimepicker.min.js" defer></script>
		<!-- Mask JS -->
    	<script type="text/javascript" src="/acuityLink/js/jquery.mask.min.js"></script> 
    	
    	<script type="text/javascript" src="/acuityLink/js/daterangepicker.js"></script>
    	
    	  <script type="text/javascript" src="/acuityLink/js/dataTables.rowReorder.min.js"></script>
    	  
    	 <!--loader js-->
		<script type="text/javascript" src="/acuityLink/js/loader.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.2/underscore-min.js"></script>
		<script type="text/javascript" src="/acuityLink/js/jquery.password-validator.js"></script>
		<script type="text/javascript">
		/* $(document).ready(function(){
			var count = 0;
		    if('${sessionScope.sessionObject}' != ''){
			   	 var d2 = new Date();
				 	setInterval(function(){
				 		 var d1 = new Date();
				 		if(((d1-d2)/1000 < 60*25)){
					 		count = getNotificationCount();
					 		if(count > 0 && $('#dropdownDivId').isActive()){
					 			getNewNotification();
					 			count = 0;
					 		}
					 		if('${param.action}' == 'notification' && count > 0){
					 			getNewNotificationListPage();
					 		}
				 		} 
				 	}, 5000);
				 	$("#getYearId").html(d2.getFullYear());
				 }
		});  */
		</script>
    </head>
    <body class="loading">
		<div class="site-holder">
		<div id="loader"><span></span></div>
			<tiles:insertAttribute name="header" />
			<div class="box-holder">
			<!-- content start  -->
			 	<tiles:insertAttribute name="body" />
			<!-- /End content   -->
			</div>
			<div>
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
		<input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
    </body>
</html>
