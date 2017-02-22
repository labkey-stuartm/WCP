<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Notification
            </div>
             
            <%--  <div class="dis-inline">
              <form class="navbar-form" role="search">
                <div class="input-group add-on">
                  <input class="form-control selectpicker" placeholder="Search" name="srch-term" id="srch-term" type="text">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                  </div>
                </div>
              </form>
             </div> --%>
             
             <div class="dis-line pull-right ml-md">
            	 <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
	                 <div class="form-group mb-none mt-sm">
	                     <button type="button" class="btn btn-primary blue-btn notificationDetails"><span class="mr-xs">+</span> Create Notification</button>
	                 </div>
                 </c:if>
             </div>
            
            <!--  <div class="dis-line pull-right">
              <div class="form-group mb-none mt-sm">
                  <select class="form-control selectpicker btn-md" id="sel1">
                    <option disabled selected>Filter by Role</option>
                    <option>Project Lead</option>
                    <option>Coordinator</option>
                    <option>Recruiter</option>
                    <option>Project Lead</option>
                  </select>
                </div>
             </div> -->
                      
         </div>         
    </div>
</div>

<div class="clearfix"></div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="md-container white-bg">
        <div class="table-responsive">
            <table id="notification_list" class="table">
            <thead>
              <tr>
                <th>NOTIFICATION</th>
                <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
                	<th style="width: 200px;">ACTIONS</th>
                </c:if>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${notificationList}" var="notification" varStatus="status">
              <tr id="${notification.notificationId}">
                <td>${notification.notificationText}</td>
                  <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
	                 <td>
	                    <%-- <span class="sprites_icon edit-g" id="${notification.notificationId}" ></span> --%>
	                    <a href="javascript:void(0)" class="sprites_icon edit-g notificationDetails" notificationId="${notification.notificationId}"></a>
	                    <%-- <button class="deleteNotification" notificationIdForDelete="${notification.notificationId}" scheduledDate="${notification.scheduleDate}" scheduledTime="${notification.scheduleTime}">delete</button> --%>
	                	<a href="javascript:void(0)" class="notificationDetails" notificationText="${notification.notificationText}">Copy</a>
	                	<button class="resendNotification" notificationIdToResend="${notification.notificationId}">Resend</button>
	                 </td> 
                 </c:if>       
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
  </div>
</div>
<form:form action="/fdahpStudyDesigner/adminNotificationView/getNotification.do" id="getNotificationEditPage" name="getNotificationEditPage" method="post">
		<input type="hidden" id="notificationId" name="notificationId">
		<input type="hidden" id="notificationText" name="notificationText">
		<input type="hidden" name="chkRefreshflag" value="y">
</form:form>
<script>
	notificationTable();
	$(document).ready(function(){
		$("#notification").addClass("active");
		
		$('#notification_list').DataTable( {
		    "paging":   true,
		    "aoColumns": [
		       { "bSortable": false },
		       { "bSortable": false }
		      ],  
		    "info" : false, 
		    "lengthChange": false, 
		    "searching": false, 
		    "pageLength": 10 
		});
	
		$('.notificationDetails').on('click',function(){
			$('#notificationId').val($(this).attr('notificationId'));
			$('#notificationText').val($(this).attr('notificationText'));
			$('#getNotificationEditPage').submit();
		});
		
		$('.deleteNotification').on('click',function(){
	  	    var notificationIdForDelete = $(this).attr('notificationIdForDelete');
	  	  	var scheduledDate = $(this).attr('scheduledDate');
	  	  	var scheduledTime = $(this).attr('scheduledTime');
	  	  
	  	   /*  bootbox.confirm("Are you sure you want to delete this Notification?", function(result){
	  	    	alert("alert bootstrap"); */
		  	    /* if (result) { */
			  		$.ajax({
			  			url : "/fdahpStudyDesigner/adminNotificationEdit/deleteNotification.do",
			  			type : "POST",
			  			datatype: "json",
			  			data : {
			  				notificationIdForDelete : notificationIdForDelete,
			  				scheduledDate : scheduledDate,
			  				scheduledTime : scheduledTime,
			  		  		"${_csrf.parameterName}":"${_csrf.token}"
			  			},
			  			success:function(data){
			  			var jsonObj = eval(data);
								var message = jsonObj.message;
								if(message == 'SUCCESS'){
									alert("Success");
									//$('#displayMessage').removeClass('aq-danger').addClass('aq-success');
									$("#"+notificationIdForDelete).remove();
									//$("#sucMsg .msg").html('Deleted successfully.');
									//$("#sucMsg").show();
									//$("#errMsg").hide();
								} else if(message == 'SELECTEDNOTIFICATIONPAST'){
									alert("pastNotification");
									//$("#sucMsg .msg").html('Deleted successfully.');
									//$("#sucMsg").show();
									//$("#errMsg").hide();
								} else {
									alert("Failed");
									/* $('#displayMessage').removeClass('aq-success').addClass('aq-danger');
									$("#errMsg .msg").html('Failed to delete. Please try again.');
									$("#errMsg").show();
									$("#sucMsg").hide(); */
								}
								/* setTimeout(hideDisplayMessage, 4000); */
			  			},
			  		});
		  	  	//}
	  	  	//});
	  	});
		
		$('.resendNotification').on('click',function(){
	  	    var notificationIdToResend = $(this).attr('notificationIdToResend');
	  	  alert(notificationIdToResend);
	  	   /*  bootbox.confirm("Are you sure you want to delete this Notification?", function(result){
	  	    	alert("alert bootstrap"); */
		  	    /* if (result) { */
			  		$.ajax({
			  			url : "/fdahpStudyDesigner/adminNotificationEdit/resendNotification.do",
			  			type : "POST",
			  			datatype: "json",
			  			data : {
			  				notificationIdToResend : notificationIdToResend,
			  		  		"${_csrf.parameterName}":"${_csrf.token}"
			  			},
			  			success:function(data){
			  			var jsonObj = eval(data);
								var message = jsonObj.message;
								if(message == 'SUCCESS'){
									alert("Success");
									//$('#displayMessage').removeClass('aq-danger').addClass('aq-success');
									//$("#sucMsg .msg").html('Deleted successfully.');
									//$("#sucMsg").show();
									//$("#errMsg").hide();
								}  else {
									alert("Failed");
									/* $('#displayMessage').removeClass('aq-success').addClass('aq-danger');
									$("#errMsg .msg").html('Failed to delete. Please try again.');
									$("#errMsg").show();
									$("#sucMsg").hide(); */
								}
								/* setTimeout(hideDisplayMessage, 4000); */
			  			},
			  		});
		  	  	//}
	  	  	//});
	  	});
});

function notificationTable(){
	var string = [];
	$.ajax({
			url : "/fdahpStudyDesigner/adminNotificationView/reloadNotificationList.do?${_csrf.parameterName}=${_csrf.token}",
			type : "POST",
			datatype: "json",
			success:function(data){
			var jsonObj = eval(data);
				var message = jsonObj.message;
				if(message == 'SUCCESS'){
					var jsonList = jsonObj.jsonList;
					/* $.each(jsonList, function(i, tabObj) {
						     console.log(tabVal);
					}); */
				}  else {
					alert("Failed");
					/* $('#displayMessage').removeClass('aq-success').addClass('aq-danger');
					$("#errMsg .msg").html('Failed to delete. Please try again.');
					$("#errMsg").show();
					$("#sucMsg").hide(); */
				}
				/* setTimeout(hideDisplayMessage, 4000); */
			},
		});
}
</script>