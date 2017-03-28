<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              <span class="mr-xs"><a href="javascript:void(0)" class="backOrCancelBtnOfNotification">
              <img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span> 
              <c:if test="${notificationBO.actionPage eq 'addOrCopy' || notificationBO eq null}">Add Notification</c:if>
              <c:if test="${notificationBO.actionPage eq 'edit'}">Edit Notification</c:if>
              <c:if test="${notificationBO.actionPage eq 'view'}">View Notification</c:if>
            </div>
         </div>         
    </div>
</div> 
<form:form action="/fdahpStudyDesigner/adminNotificationEdit/saveOrUpdateNotification.do?${_csrf.parameterName}=${_csrf.token}" 
     data-toggle="validator" role="form" id="appNotificationFormId"  method="post" autocomplete="off">       
     <input type="hidden" name="buttonType" id="buttonType">
     <!-- <input type="hidden" name="currentDateTime" id="currentDateTime"> -->
     <input type="hidden" name="notificationId" value="${notificationBO.notificationId}">
 
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
	    <div class="col-md-12 p-none">
	        <div class="md-container box-space white-bg">
	            
	            <!-- form- input-->
	            <div class="pl-none">
	                <div class="gray-xs-f mb-xs">Notification Text (250 characters max) <span class="requiredStar">*</span></div>
	                 <div class="form-group">
	                    <textarea class="form-control" maxlength="250" rows="5" id="notificationText" name="notificationText" required
	                    <c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">disabled</c:if>>${notificationBO.notificationText}</textarea>
	                    <div class="help-block with-errors red-txt"></div>
	                </div>
	            </div>
	            
	            <div class="mt-xlg mb-lg">
	             <div class="form-group">
		                <span class="radio radio-info radio-inline p-45">
		                    <input type="radio" id="inlineRadio1" value="notNowDateTime" name="currentDateTime" 
		                    <c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">disabled</c:if>
		                    <c:if test="${notificationBO.notificationScheduleType eq 'notNowDateTime'}">checked</c:if>>
		                    <label for="inlineRadio1">Schedule a date/time</label>
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
	            
	            
	            <div class="add_notify_option mandatoryForAppNotification">
	                <div class="gray-xs-f mb-xs">Select Date <span class="requiredStar">*</span></div>
	                 <div class="form-group date">
	                     <input id='datetimepicker' type="text" class="form-control calendar datepicker resetVal" 
	                     name="scheduleDate" value="${notificationBO.scheduleDate}" oldValue="${notificationBO.scheduleDate}" 
	                     placeholder="MM/DD/YYYY"  disabled/>                    
	                     <div class="help-block with-errors red-txt"></div>
	                </div>
	            </div>
	           
	            <div class="add_notify_option mandatoryForAppNotification">
	                <div class="gray-xs-f mb-xs">Time <c:if test="${notificationBO.actionPage ne 'view'}">
	                	</c:if><span class="requiredStar">*</span></div>
	                 <div class="form-group">
	                     <input id="timepicker1" class="form-control clock timepicker resetVal" name="scheduleTime" 
	                     value="${notificationBO.scheduleTime}" oldValue="${notificationBO.scheduleTime}" data-provide="timepicker" 
	                     data-minute-step="5" data-modal-backdrop="true" type="text" data-format="h:mm a" placeholder="00:00"   disabled/>
	                     <div class="help-block with-errors red-txt"></div>
	                </div>
	            </div>
	            
	            
	        </div>
	    </div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
	   <div class="md-container white-bg box-space t-bor text-right">
	       <div class="dis-line text-right ml-md">
	       
	         <div class="dis-line form-group mb-none mr-sm">
	             <button type="button" class="btn btn-default gray-btn backOrCancelBtnOfNotification">Cancel</button>
	         </div>
	         <c:if test="${empty notificationBO || notificationBO.actionPage eq 'addOrCopy'}">  
		         <div class="dis-line form-group mb-none">
		             <button type="submit" class="btn btn-primary blue-btn addNotification">Add</button>
		         </div>
	         </c:if>
	         <c:if test="${not empty notificationBO && not notificationBO.notificationSent && notificationBO.actionPage eq 'edit'}">  
		         <div class="dis-line form-group mb-none">
		             <button type="submit" class="btn btn-primary blue-btn updateNotification">Update</button>
		         </div>
	         </c:if>
	      </div>       
	    </div>
	</div>  
</form:form>    
<form:form action="/fdahpStudyDesigner/adminNotificationView/viewNotificationList.do" id="notificationBackOrCancelBtnForm" name="notificationBackOrCancelBtnForm" method="post">
</form:form>
<script>  
$(document).ready(function(){
	$("#notification").addClass("active");
	
	<c:if test="${not notificationBO.notificationSent && notificationBO.actionPage ne 'view'}">
		if($('#inlineRadio1').prop('checked')){
			$('#datetimepicker, #timepicker1').prop('disabled', false);
			$('#datetimepicker, #timepicker1').attr('required', 'required');
		}
	</c:if>
	
	<c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">
	    $('#appNotificationFormId input,textarea').prop('disabled', true);
	</c:if>
 
	$('#inlineRadio2').on('click',function(){
		 //$("#doneStudyId").removeAttr('disabled');
// 		 $('#datetimepicker, #timepicker1').prop('disabled', true); 
// 		 $('#datetimepicker, #timepicker1').prop('readonly', true);
		 $('#datetimepicker, #timepicker1').removeAttr('required');
		 $("#datetimepicker, #timepicker1").parent().removeClass('has-error has-danger');
		 $("#datetimepicker, #timepicker1").parent().find(".help-block").text("");
		 $('.add_notify_option').addClass('dis-none');
// 		 $("#currentDateTime").val('nowDateTime');
		 resetValidation('.mandatoryForAppNotification');
// 		 isFromValid('#appNotificationFormId');
	 });
	 
	 $('#inlineRadio1').on('click',function(){
		 //$("#doneStudyId").removeAttr('disabled');
		 $('#datetimepicker, #timepicker1').val('');
		 $('#datetimepicker, #timepicker1').prop('disabled', false);
// 		 $('#datetimepicker, #timepicker1').prop('readonly', true);
		 $('.add_notify_option').removeClass('dis-none');
		 $('#datetimepicker, #timepicker1').attr('required', 'required');
// 		 $("#datetimepicker, #timepicker1").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
// 		 $("#currentDateTime").val('notNowDateTime');
		 $('#appNotificationFormId').find('.resetVal').each(function() {
					$(this).val($(this).attr('oldValue'));
		 });
		 resetValidation('.mandatoryForAppNotification');
// 		 isFromValid('#appNotificationFormId');
	 });
	
	
	$('.backOrCancelBtnOfNotification').on('click',function(){
		$('.backOrCancelBtnOfNotification').prop('disabled', true);
		$('#notificationBackOrCancelBtnForm').submit();
	});
	
	$('.addNotification').on('click',function(){
		if(isFromValid('#appNotificationFormId')){
			$('.addNotification').prop('disabled',true);
			$('#appNotificationFormId').submit();
      	}else{
      		$('.addNotification').prop('disabled',false);
        }
		//$('.addNotification').prop('disabled', true);
		//$('#appNotificationFormId').submit();
	});
	
	$('.updateNotification').on('click',function(){
		if(isFromValid('#appNotificationFormId')){
			$('.updateNotification').prop('disabled',true);
			$('#appNotificationFormId').submit();
      	}else{
      		$('.updateNotification').prop('disabled',false);
        }
		//$('#appNotificationFormId').submit();
	});
	
	$('.datepicker').datetimepicker({
        format: 'MM/DD/YYYY',
//          minDate: new Date(),
        ignoreReadonly: true,
        useCurrent :false
    }); 
	
	$(".datepicker").on("click", function (e) {
        $('.datepicker').data("DateTimePicker").minDate(new Date());
    });
});
    /* $(function () {
        $('#datetimepicker').datetimepicker({
            format: 'DD/MM/YYYY'            
        });
    });
    
    function timep() {
       $('#timepicker1').timepicker().on('show.timepicker', function(e) {
        console.log('The time is ' + e.time.value);
        console.log('The hour is ' + e.time.hours);
        console.log('The minute is ' + e.time.minutes);
        console.log('The meridian is ' + e.time.meridian);
       });
    } */
</script>
