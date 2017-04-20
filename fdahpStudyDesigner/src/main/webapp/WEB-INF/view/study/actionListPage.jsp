<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-sm-10 col-rc white-bg p-none">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">ACTIONS</div>
                    <div class="dis-line form-group mb-none mr-sm">
                     </div>
					<div class="dis-line form-group mb-none">
					</div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <!--  Start body tab section -->
            <div class="right-content-body">
               <div> 
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-primary blue-btn" id="publishId" onclick="validateStudyStatus(this);" 
<%-- 	                         <c:if test="${not empty permission && (not empty studyBo.status) && (studyBo.status eq 'Paused' || studyBo.status eq 'Active' || studyBo.status eq 'Launched' || studyBo.status eq 'Resume' || studyBo.status eq 'Deactivated')}">disabled</c:if> --%>
	                         <c:choose>
				             <c:when test="${not empty permission}">
				                disabled
				             </c:when>
				             <c:when test="${not empty studyBo.status && (studyBo.status eq 'Paused' || studyBo.status eq 'Pre-launch(Published)' || studyBo.status eq 'Launched' || studyBo.status eq 'Resume' || studyBo.status eq 'Deactivated')}">
				                    disabled
				             </c:when>
				            </c:choose>
	                         >Publish as Upcoming Study</button>
	                </div>
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                    <button type="button" class="btn btn-default gray-btn " id="lunchId" onclick="validateStudyStatus(this);" 
<%-- 	                         <c:if test="${not empty permission && (not empty studyBo.status) && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Launched' || studyBo.status eq 'Paused' || studyBo.status eq 'Resume' || studyBo.status eq 'Deactivated') }">disabled</c:if> --%>
	                          <c:choose>
				            <c:when test="${not empty permission}">
				                disabled
				             </c:when>
				             <c:when test="${not empty studyBo.status && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Launched' || studyBo.status eq 'Paused' || studyBo.status eq 'Deactivated')}">
				                    disabled
				             </c:when>
				            </c:choose>
	                         >Launch Study</button>
	                </div> 
	                
<!-- 	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;"> -->
<%-- 	                         <button type="button" class="btn btn-default gray-btn" id="updatesId" onclick="validateStudyStatus(this);" <c:if test="${not empty studyBo.status && studyBo.status ne 'Pre-launch' && studyBo.status ne 'Launched' && studyBo.status ne 'Paused' && studyBo.status ne 'Deactivated' }">disabled</c:if>>Publish Updates</button> --%>
<!-- 	                </div>   -->
	                
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button type="button" class="btn btn-default gray-btn " id="pauseId" onclick="validateStudyStatus(this);"
			            <c:choose>
			             <c:when test="${not empty permission}">
			                disabled
			             </c:when>
			             <c:when test="${not empty studyBo.status && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Pre-launch(Published)' || studyBo.status eq 'Paused'  || studyBo.status eq 'Deactivated')}">
			                    disabled
			             </c:when>
			           </c:choose> 
			             <%-- <c:if test="${(not empty permission && not empty studyBo.status)}">disabled</c:if> --%>>Pause</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button type="button" class="btn btn-default gray-btn " id="resumeId" onclick="validateStudyStatus(this);" 
<%-- 			             <c:if test="${not empty permission && (not empty studyBo.status) && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Active' || studyBo.status eq 'Launched' || studyBo.status eq 'Resume' || studyBo.status eq 'Deactivated') }">disabled</c:if> --%>
			                 <c:choose>
				             <c:when test="${not empty permission}">
				                disabled
				             </c:when>
				             <c:when test="${not empty studyBo.status && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Pre-launch(Published)' || studyBo.status eq 'Deactivated')}">
				                    disabled
				             </c:when>
				            </c:choose>
			             >Resume</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button type="button" class="btn btn-default gray-btn " id="deactivateId" onclick="validateStudyStatus(this);" 
			             <%-- <c:if test="${not empty permission && (not empty studyBo.status) && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Active' || studyBo.status eq 'Paused'  || studyBo.status eq 'Deactivated') }"
			             >disabled</c:if> --%>
			             <c:choose>
			             <c:when test="${not empty permission}">
			                disabled
			             </c:when>
			             <c:when test="${not empty studyBo.status && (studyBo.status eq 'Pre-launch' || studyBo.status eq 'Pre-launch(Published)' || studyBo.status eq 'Paused'  || studyBo.status eq 'Deactivated')}">
			                    disabled
			             </c:when>
			            </c:choose>
			            >Deactivate</button>
			       </div>
            </div>
            </div>
</div>
<form:form action="/fdahpStudyDesigner/adminStudies/updateStudyAction.do" name="actionInfoForm" id="actionInfoForm" method="post">
<input type="hidden" name="studyId" id="studyId" value="${studyBo.id}" />
<input type="hidden" name="buttonText" id="buttonText" value="" />
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	
});
function validateStudyStatus(obj){
	var buttonText = obj.id;
	messageText = "";
     if(buttonText){
    	 $.ajax({
             url: "/fdahpStudyDesigner/adminStudies/validateStudyAction.do",
             type: "POST",
             datatype: "json",
             data: {
            	 buttonText:buttonText,
                 "${_csrf.parameterName}":"${_csrf.token}",
             },
             success: function emailValid(data, status) {
                 var jsonobject = eval(data);
                 var message = jsonobject.message;
                 if (message == "SUCCESS") {
                	 if(buttonText == 'publishId'){
                	    messageText = "You are attempting to publish the study as an upcoming one. Are you sure you wish to proceed?";
                	 }else if(buttonText == 'lunchId'){
                	    messageText = "You are attempting to Launch the study. This will make the study available for mobile app users to explore and join. Are you sure you wish to proceed?";
                	 }else if(buttonText == 'pauseId'){
                	    messageText = "You are attempting to Pause the study. Mobile app users can no longer participate in study activities until you resume the study again. However, they will still be able to view the study dashboard and study resources. Are you sure you wish to proceed?";
                  	 }else if(buttonText == 'resumeId'){
                	    messageText = "You are attempting to Resume a paused study. This will activate the study and allow mobile app users to resume participation in study activities with the latest study content.  Are you sure you wish to proceed?";
                  	 }else if(buttonText == 'deactivateId'){
                		 messageText = "You are attempting to Deactivate a live study. Once deactivated, mobile app users will no longer be able to participate in the study. Also, deactivated studies can never be reactivated. Are you sure you wish to proceed?";
                  	 }
                	 bootbox.confirm({
							closeButton: false,
							message : messageText,	
						    buttons: {
						        'cancel': {
						            label: 'Cancel',
						        },
						        'confirm': {
						            label: 'OK',
						        },
						    },
						    callback: function(result) {
						        if (result) {
						        	$('#buttonText').val(buttonText);
	                            	$('#actionInfoForm').submit();
						        }
						    }
					});				
                 }else{
                	 showErrMsg1(message); 
                 }
             },
             error:function status(data, status) {
             	$("body").removeClass("loading");
             },
             complete : function(){ $('.actBut').removeAttr('disabled'); }
         });
     } 

}
function showErrMsg1(message){
	$("#alertMsg").removeClass('s-box').addClass('e-box').html(message);
	$('#alertMsg').show('10000');
	setTimeout(hideDisplayMessage, 10000);
}
</script>