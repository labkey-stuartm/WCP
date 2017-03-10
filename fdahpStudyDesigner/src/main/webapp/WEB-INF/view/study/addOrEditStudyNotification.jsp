<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right-content">
       <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyNotification.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" role="form" id="studyNotificationFormId"  method="post" autocomplete="off">       
       <input type="hidden" name="buttonType" id="buttonType">
       <!-- <input type="hidden" name="currentDateTime" id="currentDateTime"> -->
       <input type="hidden" name="notificationId" value="${notificationBO.notificationId}">
       <div class="right-content-head"> 
           <div class="text-right">
               <div class="black-md-f text-uppercase dis-line pull-left line34 studyNotificationList"><span><img src="/fdahpStudyDesigner/images/icons/back-b.png" class="pr-md"/></span> Add/Edit Notification</div>
               
               <div class="dis-line form-group mb-none mr-sm">
                    <button type="button" class="btn btn-default gray-btn studyListPageFromNotification">Cancel</button>
                </div>
                <c:if test="${not notificationBO.notificationSent && notificationBO.actionPage ne 'view'}">
                 <div class="dis-line form-group mb-none mr-sm">
                      <button type="submit" class="btn btn-default gray-btn" id="saveStudyId">Save</button>
                 </div>
                 <div class="dis-line form-group mb-none">
                     	<button type="submit" class="btn btn-primary blue-btn" id="doneStudyId">Done</button>
                 </div>
                </c:if>
            </div>
       </div>
       <!--  End  top tab section-->
       
       
       
       <!--  Start body tab section -->
       <div class="right-content-body">
        
           <!-- form- input-->
       <div class="pl-none mt-xlg">
           <div class="gray-xs-f mb-xs">Notification Text</div>
           <div <c:if test="${not notificationBO.notificationSent && notificationBO.actionPage ne 'view'}">class="form-group"</c:if> class="form-group linkDis">
               <textarea class="form-control" maxlength="250" rows="5" id="notificationText" name="notificationText" required>${notificationBO.notificationText}</textarea>
               <div class="help-block with-errors red-txt"></div>
           </div>
       </div>
       
       <div class="mt-xlg mb-lg">
       	<!-- <div class="form-group"> -->
       	<div <c:if test="${not notificationBO.notificationSent && notificationBO.actionPage ne 'view'}">class="form-group"</c:if> class="form-group linkDis">
            <span class="radio radio-info radio-inline p-45">
                <input type="radio" id="inlineRadio1" value="notNowDateTime" name="currentDateTime">
                <label for="inlineRadio1">Schedule a date/time</label>	                    
            </span>
            <span class="radio radio-inline">
                <input type="radio" id="inlineRadio2" value="nowDateTime" name="currentDateTime">
                <label for="inlineRadio2">Send it Now</label>
            </span>
            <div class="help-block with-errors red-txt"></div>
            <c:if test="${notificationBO.notificationSentDateTime ne null}">
	              <div class="lastSendDateTime">Last Sent on ${notificationBO.notificationSentDate} at ${notificationBO.notificationSentTime}</div>
	        </c:if>
	        <div class="clearfix"></div>
           </div>
       </div>
       
       
       <div class="add_notify_option">
           <div class="gray-xs-f mb-xs">Select Date</div>
            <div class="form-group date">
                <input id='datetimepicker' type="text" class="form-control calendar datepicker" id="scheduleDate" name="scheduleDate" value="${notificationBO.scheduleDate}" placeholder="MM/DD/YYYY" disabled/>                    
                <div class="help-block with-errors red-txt"></div>
           </div>
       </div>
      
       <div class="add_notify_option">
           <div class="gray-xs-f mb-xs">Time</div>
            <div class="form-group">
                <input id="timepicker1" class="form-control clock timepicker" id="scheduleTime" name="scheduleTime" value="${notificationBO.scheduleTime}" data-provide="timepicker" data-minute-step="5" data-modal-backdrop="true" type="text" data-format="h:mm a" placeholder="00:00" disabled/>
                <div class="help-block with-errors red-txt"></div>
           </div>
       </div>
           
       </div>
       </form:form>
            <!--  End body tab section -->
</div>

<form:form action="/fdahpStudyDesigner/adminStudies/viewStudyNotificationList.do" id="viewStudyNotificationListPage" name="viewStudyNotificationListPage" method="post">
</form:form>

<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="studyListPage" id="studyListPage" method="post">
</form:form>
<script>
     $(document).ready(function(){  
           
    	 $('.studyNotificationList').on('click',function(){
 			$('#viewStudyNotificationListPage').submit();
 		});
    	 
    	 $('.studyListPageFromNotification').on('click',function(){
  			$('#studyListPage').submit();
  		});
    	 
    	 $('.datepicker').datetimepicker({
             format: 'MM/DD/YYYY',
//               minDate: new Date(),
             ignoreReadonly: true,
             useCurrent :false
         }); 

    	 $(".datepicker").on("click", function (e) {
             $('.datepicker').data("DateTimePicker").minDate(new Date());
         });
    	 
    	 $('#inlineRadio2').on('click',function(){
    		 $('#datetimepicker, #timepicker1').removeAttr('required');
    		 $("#datetimepicker, #timepicker1").parent().removeClass('has-error has-danger');
    		 $("#datetimepicker, #timepicker1").parent().find(".help-block").text("");
    		 $('.add_notify_option').css("visibility","hidden");
    		 resetValidation('#studyNotificationFormId');
    	 });
    	 
    	 $('#inlineRadio1').on('click',function(){
    		 $('#datetimepicker, #timepicker1').val('');
    		 $('#datetimepicker, #timepicker1').prop('disabled', false);
    		 $('.add_notify_option').css("visibility","visible");
    		 $('#datetimepicker, #timepicker1').attr('required', 'required');
    		 resetValidation('#studyNotificationFormId');
    	 });
    	 
    	 
          $("#doneStudyId").on('click', function(e){
        	  $('#inlineRadio1, #inlineRadio2').attr('required', 'required');
     		  $('#studyNotificationFormId').submit();
           });
          
          $('#saveStudyId').click(function() {
        	  $('#datetimepicker, #timepicker1').removeAttr('required', 'required');
        	  $('#inlineRadio1, #inlineRadio2').removeAttr('required', 'required');
              $('#studyNotificationFormId').submit();
    		});
          
     });
     
    
</script>