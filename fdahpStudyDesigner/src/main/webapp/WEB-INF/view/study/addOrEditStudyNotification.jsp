<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right-content">
       <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyNotification.do?${_csrf.parameterName}=${_csrf.token}" 
       data-toggle="validator" role="form" id="studyNotificationFormId"  method="post" autocomplete="off">       
       <input type="hidden" name="buttonType" id="buttonType">
       <!-- <input type="hidden" name="currentDateTime" id="currentDateTime"> -->
       <input type="hidden" name="notificationId" value="${notificationBO.notificationId}">
       <div class="right-content-head"> 
           <div class="text-right">
               <div class="black-md-f text-uppercase dis-line pull-left line34 studyNotificationList">
	               <span>
	               		<img src="/fdahpStudyDesigner/images/icons/back-b.png" class="pr-md"/>
	               </span> Add / Edit Notification
               </div>
               
               <div class="dis-line form-group mb-none mr-sm">
                    <button type="button" class="btn btn-default gray-btn studyNotificationList">Cancel</button>
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
           <div class="gray-xs-f mb-xs">Notification Text <c:if test="${notificationBO.actionPage ne 'view'}"><span class="requiredStar">*</span></c:if></div>
           <div class="form-group">
               <textarea class="form-control" maxlength="250" rows="5" id="notificationText" name="notificationText" required
               <c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">disabled</c:if>>${notificationBO.notificationText}</textarea>
               <div class="help-block with-errors red-txt"></div>
           </div>
       </div>
       
       <div class="mt-xlg mb-lg">
       	<!-- <div class="form-group"> -->
       		<div class="form-group">
		            <span class="radio radio-info radio-inline p-45">
		                <input type="radio" id="inlineRadio1" value="notNowDateTime" name="currentDateTime"
		                <c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">disabled</c:if>
		                <c:if test="${notificationBO.notificationScheduleType eq 'notNowDateTime'}">checked</c:if>>
		                <label for="inlineRadio1">Schedule a date / time</label>	                    
		            </span>
		            <span class="radio radio-inline">
		                <input type="radio" id="inlineRadio2" value="nowDateTime" name="currentDateTime"
		                <c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">disabled</c:if>
		                <c:if test="${notificationBO.notificationScheduleType eq 'nowDateTime'}">checked</c:if>>
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
           <div class="gray-xs-f mb-xs">Select Date <c:if test="${notificationBO.actionPage ne 'view'}"><span class="requiredStar">*</span></c:if></div>
            <div class="form-group date">
                <input id='datetimepicker' type="text" class="form-control calendar datepicker resetVal" id="scheduleDate"
                 name="scheduleDate" value="${notificationBO.scheduleDate}" oldValue="${notificationBO.scheduleDate}" 
                 placeholder="MM/DD/YYYY" disabled/>                    
                <div class="help-block with-errors red-txt"></div>
           </div>
       </div>
      
       <div class="add_notify_option">
           <div class="gray-xs-f mb-xs">Time <c:if test="${notificationBO.actionPage ne 'view'}"><span class="requiredStar">*</span></c:if></div>
            <div class="form-group">
                <input id="timepicker1" class="form-control clock timepicker resetVal" id="scheduleTime" 
                name="scheduleTime" value="${notificationBO.scheduleTime}" oldValue="${notificationBO.scheduleTime}" 
                data-provide="timepicker" data-minute-step="5" data-modal-backdrop="true" type="text" data-format="h:mm a" 
                placeholder="00:00" disabled/>
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
    	 $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
         $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
         $(".menuNav li").removeClass('active');
         $(".eigthNotification").addClass('active'); 
         $("#createStudyId").show();
         $('.eigthNotification').removeClass('cursor-none'); 
         
         <c:if test="${notificationBO.actionPage ne 'view'}">
	 		if($('#inlineRadio1').prop('checked')){
	 			$('#datetimepicker, #timepicker1').prop('disabled', false);
	 			$('#datetimepicker, #timepicker1').attr('required', 'required');
	 		}
 		</c:if>
    	 
    	 $('.studyNotificationList').on('click',function(){
    		$('.studyNotificationList').prop('disabled', true);
 			$('#viewStudyNotificationListPage').submit();
 		});
    	 
    	 /* $('.studyListPageFromNotification').on('click',function(){
  			$('#studyListPage').submit();
  		}); */
    	 
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
    		 $('#studyNotificationFormId').find('.resetVal').each(function() {
					$(this).val($(this).attr('oldValue'));
		     });
    		 resetValidation('#studyNotificationFormId');
    	 });
    	 
    	 
          $("#doneStudyId").on('click', function(e){
        	  $('#inlineRadio1, #inlineRadio2').attr('required', 'required');
        	  $('#buttonType').val('done');
        	  if(isFromValid('#studyNotificationFormId')){
        			$('#doneStudyId').prop('disabled',true);
        			$('#studyNotificationFormId').submit();
              	}else{
              		$('#doneStudyId').prop('disabled',false);
                }
     		  /* $('#studyNotificationFormId').submit(); */
           });
          
          $('#saveStudyId').click(function() {
        	  $('#datetimepicker, #timepicker1').removeAttr('required', 'required');
        	  $('#inlineRadio1, #inlineRadio2').removeAttr('required', 'required');
        	  $('#buttonType').val('save');
        	  if(isFromValid('#studyNotificationFormId')){
      			$('#saveStudyId').prop('disabled',true);
      			$('#studyNotificationFormId').submit();
            	}else{
            		$('#saveStudyId').prop('disabled',false);
              }
              //$('#studyNotificationFormId').submit();
    		});
          
     });
     
    
</script>