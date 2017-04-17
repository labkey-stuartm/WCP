<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              <span class="mr-xs"><a href="javascript:void(0)" class="backOrCancelBtnOfNotification">
              <img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span> 
              <c:if test="${notificationBO.actionPage eq 'addOrCopy' || notificationBO eq null}">Create Notification</c:if>
              <c:if test="${notificationBO.actionPage eq 'edit'}">Edit Notification</c:if>
              <c:if test="${notificationBO.actionPage eq 'view'}">View Notification</c:if>
              <c:if test="${notificationBO.actionPage eq 'resend'}">Resend Notification</c:if>
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
	        <div class="box-space white-bg">
	            
	            <!-- form- input-->
	            <c:if test="${notificationBO.notificationSent && notificationBO.actionPage eq 'edit'}">
			       <div>
			       		<span>This notification has already been sent out to users and cannot be edited. To resend this notification, use the Resend action and choose a time for firing the notification.</span>
			       </div>
       			</c:if>
	            <div class="pl-none">
	                <div class="gray-xs-f mb-xs mt-xs">Notification Text (250 characters max) <span class="requiredStar">*</span></div>
	                 <div class="form-group">
	                    <textarea class="form-control" maxlength="250" rows="5" id="notificationText" name="notificationText" required
	                    >${notificationBO.notificationText}</textarea>
	                    <div class="help-block with-errors red-txt"></div>
	                </div>
	            </div>
	            
	            <div class="mt-xlg mb-lg">
	             <div class="form-group">
		                <span class="radio radio-info radio-inline p-45">
		                    <input type="radio" id="inlineRadio1" value="notImmediate" name="currentDateTime" 
		                    <c:if test="${notificationBO.notificationScheduleType eq 'notImmediate'}">checked</c:if>
		                    <c:if test="${notificationBO.actionPage eq 'addOrCopy'}">checked</c:if>>
		                    <label for="inlineRadio1">Schedule a date/time</label>
		                </span>
		                <span class="radio radio-inline">
		                    <input type="radio" id="inlineRadio2" value="immediate" name="currentDateTime"
		                    <c:if test="${notificationBO.notificationScheduleType eq 'immediate'}">checked</c:if>>
		                    <label for="inlineRadio2">Send Immediately</label>
		                </span>
	                	<div class="help-block with-errors red-txt"></div>
	                	<c:if test="${not empty notificationHistoryNoDateTime}">
				            <c:forEach items="${notificationHistoryNoDateTime}" var="notificationHistory">
				            	<%-- <c:if test="${not empty notificationHistory.notificationSentdtTime}"> --%>
					              <span class="lastSendDateTime">${notificationHistory.notificationSentdtTime}</span><br><br>
					           <%--  </c:if> --%>
					        </c:forEach>
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
	                     value="${notificationBO.scheduleTime}" oldValue="${notificationBO.scheduleTime}"  placeholder="00:00" disabled/>
	                     <div class="help-block with-errors red-txt"></div>
	                </div>
	            </div>
	            
	            
	        </div>
	    </div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
	   <div class="white-bg box-space t-bor text-right">
	       <div class="dis-line text-right ml-md">
	       
	         <div class="dis-line form-group mb-none mr-sm">
	             <button type="button" class="btn btn-default gray-btn backOrCancelBtnOfNotification">Cancel</button>
	         </div>
	         <c:if test="${empty notificationBO || notificationBO.actionPage eq 'addOrCopy'}">  
		         <div class="dis-line form-group mb-none mr-sm">
		             <button type="button" class="btn btn-primary blue-btn addNotification">Save</button>
		         </div>
	         </c:if>
	          <c:if test="${not empty notificationBO && not notificationBO.notificationSent && notificationBO.actionPage eq 'edit' && empty notificationHistoryNoDateTime}">  
		         <div class="dis-line form-group mb-none mr-sm">
		         	 <button type="button" class="btn btn-primary blue-btn deleteButtonHide" id="deleteNotification">Delete</button>
		         </div>
	         </c:if>
	         <c:if test="${not empty notificationBO && not notificationBO.notificationSent && notificationBO.actionPage eq 'edit'}">  
		         <div class="dis-line form-group mb-none mr-sm">
		             <button type="button" class="btn btn-primary blue-btn updateNotification">Update</button>
		         </div>
	         </c:if>
	         <c:if test="${not empty notificationBO && notificationBO.notificationSent && notificationBO.actionPage eq 'resend'}">  
		         <div class="dis-line form-group mb-none mr-sm">
		             <button type="button" class="btn btn-primary blue-btn resendNotification">Resend</button>
		         </div>
	         </c:if>
	      </div>       
	    </div>
	</div>  
</form:form>    
<form:form action="/fdahpStudyDesigner/adminNotificationView/viewNotificationList.do" id="notificationBackOrCancelBtnForm" name="notificationBackOrCancelBtnForm" method="post">
</form:form>
<form:form action="/fdahpStudyDesigner/adminNotificationEdit/deleteNotification.do" id="deleteNotificationForm" name="deleteNotificationForm" method="post">
	<input type="hidden" name="notificationId" value="${notificationBO.notificationId}">
</form:form>
<script>  
$(document).ready(function(){
	$('#rowId').parent().removeClass('white-bg');
	$("#notification").addClass("active");
	
	/* <c:if test="${not notificationBO.notificationSent && notificationBO.actionPage ne 'view'}">
		if($('#inlineRadio1').prop('checked')){
			$('#datetimepicker, #timepicker1').prop('disabled', false);
			$('#datetimepicker, #timepicker1').attr('required', 'required');
		}
		if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
		}
	</c:if> */
	
	<c:if test="${notificationBO.notificationSent || notificationBO.actionPage eq 'view'}">
	    $('#appNotificationFormId input,textarea').prop('disabled', true);
	    if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
		}
	</c:if>
	
	<c:if test="${not notificationBO.notificationSent && notificationBO.actionPage eq 'resend'}">
    	$('#appNotificationFormId input,textarea').prop('disabled', true);
		if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
		}
	</c:if>
	
	<c:if test="${notificationBO.actionPage eq 'addOrCopy'}">
			$('#inlineRadio1').prop('checked','checked');
			$('.deleteButtonHide').addClass('dis-none');
			if($('#inlineRadio1').prop('checked')){
				$('#datetimepicker, #timepicker1').prop('disabled', false);
				$('#datetimepicker, #timepicker1').attr('required', 'required');
			}
			if($('#inlineRadio2').prop('checked')){
				$('.add_notify_option').addClass('dis-none');
			}
	</c:if>
	
	<c:if test="${not notificationBO.notificationSent && notificationBO.actionPage eq 'edit' && not empty notificationHistoryNoDateTime}">
		$('#appNotificationFormId textarea').prop('disabled', true);
		$('.deleteButtonHide').addClass('dis-none');
		if($('#inlineRadio1').prop('checked')){
			$('#datetimepicker, #timepicker1').prop('disabled', false);
			$('#datetimepicker, #timepicker1').attr('required', 'required');
		}
		if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
		}
	</c:if>
	
	<c:if test="${not notificationBO.notificationSent && notificationBO.actionPage eq 'edit' && empty notificationHistoryNoDateTime}">
		$('.deleteButtonHide').removeClass('dis-none');
		if($('#inlineRadio1').prop('checked')){
			$('#datetimepicker, #timepicker1').prop('disabled', false);
			$('#datetimepicker, #timepicker1').attr('required', 'required');
		}
		if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
		}
	</c:if>
	
	
	<c:if test="${notificationBO.notificationSent && notificationBO.actionPage eq 'resend'}">
		$('#appNotificationFormId #inlineRadio1,#inlineRadio2').prop('disabled', false);
		$('#appNotificationFormId input,textarea').prop('disabled', false);
		$('#appNotificationFormId textarea').prop('readonly', true);
		if($('#inlineRadio1').prop('checked')){
			$('#datetimepicker, #timepicker1').attr('required', 'required');
		}
		if($('#inlineRadio2').prop('checked')){
			$('.add_notify_option').addClass('dis-none');
			$('#datetimepicker, #timepicker1').removeAttr('required');
		}
		$('#buttonType').val('resend');
	</c:if>
 
	$('#inlineRadio2').on('click',function(){
		 $('#datetimepicker, #timepicker1').removeAttr('required');
		 $("#datetimepicker, #timepicker1").parent().removeClass('has-error has-danger');
		 $("#datetimepicker, #timepicker1").parent().find(".help-block").text("");
		 $('.add_notify_option').addClass('dis-none');
		 resetValidation('.mandatoryForAppNotification');
		 $('.addNotification').prop('disabled',false);
	 });
	 
	 $('#inlineRadio1').on('click',function(){
		 $('#datetimepicker, #timepicker1').val('');
		 $('#datetimepicker, #timepicker1').prop('disabled', false);
		 $('.add_notify_option').removeClass('dis-none');
		 $('#datetimepicker, #timepicker1').attr('required', 'required');
		 $('#appNotificationFormId').find('.resetVal').each(function() {
					$(this).val($(this).attr('oldValue'));
		 });
		 resetValidation('.mandatoryForAppNotification');
	 });
	
	
	$('.backOrCancelBtnOfNotification').on('click',function(){
		$('.backOrCancelBtnOfNotification').prop('disabled', true);
		$('#notificationBackOrCancelBtnForm').submit();
	});
	
	$('.addNotification').on('click',function(){
		$('#buttonType').val('add');
		if(isFromValid('#appNotificationFormId')){
			if($('#inlineRadio2').prop('checked')){
  			  	bootbox.confirm("Are you sure you want to send this notification immediately?", function(result){ 
          	  		if(result){
          	  			$('.updateNotification').prop('disabled',true);
        				$('#appNotificationFormId').submit();
          	  		}
         	  	});
			}else if($('#inlineRadio1').prop('checked')){
	  			if(validateTime()){
	  				$('.updateNotification').prop('disabled',true);
	  				$('#appNotificationFormId').submit();
	  			}
	  		  }
      	}else{
      		$('.addNotification').prop('disabled',false);
        }
		//$('.addNotification').prop('disabled', true);
		//$('#appNotificationFormId').submit();
	});
	
	$('.updateNotification').on('click',function(){
		$('#buttonType').val('update');
		if(isFromValid('#appNotificationFormId')){
			if($('#inlineRadio2').prop('checked')){
	  			  bootbox.confirm("Are you sure you want to send this notification immediately?", function(result){ 
	          	  		if(result){
	          	  		$('.updateNotification').prop('disabled',true);
	        			$('#appNotificationFormId').submit();
	          	  		}
	          	  	  });
			}else if($('#inlineRadio1').prop('checked')){
	  			if(validateTime()){
	  				$('.updateNotification').prop('disabled',true);
					$('#appNotificationFormId').submit();
	  			}
	  		  }
      	}else{
      		$('.updateNotification').prop('disabled',false);
        }
		//$('#appNotificationFormId').submit();
	});
	
	$('.resendNotification').on('click',function(){
		$('#buttonType').val('resend');
		if(isFromValid('#appNotificationFormId')){
			 if($('#inlineRadio2').prop('checked')){
	  			  bootbox.confirm("Are you sure you want to resend this notification immediately?", function(result){ 
	          	  		if(result){
	          	  		$('.updateNotification').prop('disabled',true);
	        			$('#appNotificationFormId').submit();
	          	  		}
	          	  	  });
			}else if($('#inlineRadio1').prop('checked')){
	  			if(validateTime()){
	  				$('.updateNotification').prop('disabled',true);
					$('#appNotificationFormId').submit();
	  			}
	  		  }
      	}else{
      		$('.updateNotification').prop('disabled',false);
        }
	});
	
	$('#deleteNotification').on('click',function(){
  	  	bootbox.confirm("Are you sure want to delete notification!", function(result){ 
  		if(result){
  	    		$('#deleteNotificationForm').submit();
  		}
  	  });
  	});
	
	$('.datepicker').datetimepicker({
        format: 'MM/DD/YYYY',
//          minDate: new Date(),
        ignoreReadonly: true,
        useCurrent :false
    }).on('dp.change change', function(e) {
    	validateTime();
	});
	
	$('.timepicker').datetimepicker({
		format: 'h:mm a'
    }).on('dp.change change', function(e) {
    	validateTime();
	}); 
	
	 $(".datepicker").on("click", function (e) {
         $('.datepicker').data("DateTimePicker").minDate(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()));
     });
	 
	 $(".timepicker").on("click", function (e) {
		 var dt = $('#datetimepicker').val();
		 var date = new Date();
		 var day = date.getDate() > 10 ? date.getDate() : ('0' + date.getDate());
		 var month = (date.getMonth()+1) > 10 ? (date.getMonth()+1) : ('0' + (date.getMonth()+1));
		 var today = month + '/' +  day + '/' + date.getFullYear();
		 if(dt != '' && dt != today){
			 $('.timepicker').data("DateTimePicker").minDate(false); 
		 } else {
			 $('.timepicker').data("DateTimePicker").minDate(moment());
		 }
     });
	 
	 /* $('.deleteNotification').on('click',function(){
	  	    var notificationIdForDelete = $(this).attr('notificationIdForDelete');
	  	  	//var scheduledDate = $(this).attr('scheduledDate');
	  	  	//var scheduledTime = $(this).attr('scheduledTime');
	  	  	bootbox.confirm("Are you sure want to delete notification!", function(result){ 
	  		if(result){
	  	    	if(notificationIdForDelete != '' && notificationIdForDelete != null && typeof notificationIdForDelete != 'undefined'){
			  		$.ajax({
			  			url : "/fdahpStudyDesigner/adminNotificationEdit/deleteNotification.do",
			  			type : "POST",
			  			datatype: "json",
			  			data : {
			  				notificationIdForDelete : notificationIdForDelete,
			  				//scheduledDate : scheduledDate,
			  				//scheduledTime : scheduledTime,
			  		  		"${_csrf.parameterName}":"${_csrf.token}"
			  			},
			  			success:function(data){
			  			var jsonObj = eval(data);
								var message = jsonObj.message;
								if(message == 'SUCCESS'){
									alert("Success");
								} else {
									alert("Failed");
								}
			  			},
			  		});
	  	    	}
	  		}
	  	  });
	  	}); */
});
function validateTime(){
	var dt = $('#datetimepicker').val();
	var tm = $('#timepicker1').val();
	var valid = true;
	if(dt && tm) {
		dt = moment(dt, "MM/DD/YYYY").toDate();
		thisDate = moment($('.timepicker').val(), "h:mm a").toDate();
		dt.setHours(thisDate.getHours());
		dt.setMinutes(thisDate.getMinutes());
		if(dt < new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), new Date().getHours(), new Date().getMinutes())) {
			$('#timepicker1').val('');
			// $('.timepicker').data("DateTimePicker").minDate(moment());
			$('.timepicker').parent().addClass('has-error has-danger').find('.help-block.with-errors').html('<ul class="list-unstyled"><li>Check Time.</li></ul>');
			valid = false;
		} else {
			$('.timepicker').parent().removeClass('has-error has-danger').find('.help-block.with-errors').html('');
		}
	}
	return valid;
}
</script>
