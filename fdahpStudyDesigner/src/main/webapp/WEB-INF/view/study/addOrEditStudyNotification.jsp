<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<div class="right-content">
            <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyNotification.do" data-toggle="validator" role="form" id="studyNotificationFormId"  method="post" autocomplete="off">       
            <input type="hidden" name="buttonType" id="buttonType">
            <input type="hidden" name="id" value="${notificationBO.notificationId}">
            <div class="right-content-head"> 
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34 studyNotificationList"><span><img src="/fdahpStudyDesigner/images/icons/back-b.png" class="pr-md"/></span> Add/Edit Notification</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn studyListPageFromNotification">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="saveStudyId">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn" id="DoneStudyId">Done</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
             
                <!-- form- input-->
            <div class="pl-none mt-xlg">
                <div class="gray-xs-f mb-xs">Notification Text</div>
                <div class="form-group">
                    <textarea class="form-control" maxlength="250" rows="5" id="comment" name="notificationText" value="" required>${notificationBO.notificationText}</textarea>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            
            <div class="mt-xlg mb-lg">
                <span class="radio radio-info radio-inline p-45">
                    <input type="radio" id="inlineRadio1" value="later" name="radioInline1">
                    <label for="inlineRadio1">Schedule a date/time</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio2" value="now" name="radioInline1">
                    <label for="inlineRadio2">Send it Now</label>
                </span>
            </div>
            
            
            <div class="add_notify_option">
                <div class="gray-xs-f mb-xs">Select Date</div>
                 <div class="form-group date">
                     <input id='datetimepicker' type="text" class="form-control calendar" name="scheduleDate" value="${notificationBO.scheduleDate}" placeholder="MM/DD/YYYY"/>                    
                     <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
           
            <div class="add_notify_option">
                <div class="gray-xs-f mb-xs">Time</div>
                 <div class="form-group">
                     <input id="timepicker1" class="form-control clock" name="scheduleTime" value="${notificationBO.scheduleTime}" data-provide="timepicker" data-minute-step="1" data-modal-backdrop="true" type="text" placeholder="00:00" />
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
    	 
    	 $('#datetimepicker').datetimepicker({
             format: 'MM/DD/YYYY'            
         }); 
    	 
    	 $('#inlineRadio2').on('click',function(){
    		 $('.add_notify_option').hide();
    	 });
    	 
    	 $('#inlineRadio1').on('click',function(){
    		 $('.add_notify_option').show();
    	 });
    	 
    	 $("#DoneStudyId").click(function(){
         	$("#buttonType").val('done');
             $("#studyNotificationFormId").submit();
          });
          
          $("#saveStudyId").click(function(){
        	  alert("save");
         	$('#studyNotificationFormId').validator('destroy');
         	$("#buttonType").val('save');
             $("#studyNotificationFormId").submit();
          });
    	    
     });
</script>