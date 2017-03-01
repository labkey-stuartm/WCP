<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<body>
<!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
            <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateSettingAndAdmins.do" data-toggle="validator" role="form" id="settingfoFormId"  method="post" autocomplete="off">
            <input type="hidden" name="buttonText" id="buttonText">
            <input type="hidden" name="id" value="${studyBo.id}">
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Settings and Admins</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="cancelId">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="saveId">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn" id="completedId">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                <!-- Start Section-->
                <div class="col-md-12 p-none mt-md">
                     <div class="gray-xs-f mb-sm">Platform(s) Supported</div>
                     <div class="form-group">
                       <span class="checkbox checkbox-inline p-45">
                            <input type="checkbox" id="inlineCheckbox1" name="platform" value="I" <c:if test="${fn:contains(studyBo.platform,'I')}">checked</c:if> required>
                            <label for="inlineCheckbox1"> iOS </label>
                      </span>

                      <span class="checkbox checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox2" name="platform" value="A" <c:if test="${fn:contains(studyBo.platform,'A')}">checked</c:if> required>
                            <label for="inlineCheckbox2"> Android </label>
                      </span>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <!-- End Section-->
                
                <!-- Start Section-->
                <div class="col-md-12 p-none mt-xlg">
                     <div class="gray-xs-f mb-sm">Allow participants to enroll? </div>

                     <div class="form-group">
                      <span class="radio radio-info radio-inline p-45">
                            <input type="radio" id="inlineRadio1" value="Yes" name="enrollingParticipants" <c:if test="${studyBo.enrollingParticipants eq 'Yes'}">checked</c:if> required>
                            <label for="inlineRadio1">Yes</label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" id="inlineRadio2" value="No" name="enrollingParticipants" <c:if test="${studyBo.enrollingParticipants eq 'No'}">checked</c:if> required>
                            <label for="inlineRadio2">No</label>
                        </span>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <!-- End Section-->
                
                 <!-- Start Section-->
                 <div class="col-md-12 p-none mt-xlg">
                     <div class="gray-xs-f mb-sm">Retain participant data when they leave a study?</div>

                     <div class="form-group">
                      <span class="radio radio-info radio-inline p-45">
                            <input type="radio" id="inlineRadio3" value="Yes" name="retainParticipant" <c:if test="${studyBo.retainParticipant eq 'Yes'}">checked</c:if> required>
                            <label for="inlineRadio3">Yes</label>
                        </span>
                        <span class="radio radio-inline p-45">
                            <input type="radio" id="inlineRadio4" value="No" name="retainParticipant" <c:if test="${studyBo.retainParticipant eq 'No'}">checked</c:if> required>
                            <label for="inlineRadio4">No</label>
                        </span>
                         <span class="radio radio-inline">
                            <input type="radio" id="inlineRadio5" value="All" name="retainParticipant" <c:if test="${studyBo.retainParticipant eq 'All'}">checked</c:if> required>
                            <label for="inlineRadio5">Allow user to choose to have their data retained or deleted</label>
                        </span>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <!-- End Section-->
                
                 <!-- Start Section-->
                <div class="col-md-12 p-none mt-xlg">
                     <div class="gray-xs-f mb-sm">Allow users to rejoin a Study once they leave it?</div>

                     <div class="form-group">
                      <span class="radio radio-info radio-inline p-45">
                            <input type="radio" class="rejoin_radio" id="inlineRadio6" value="Yes" name="allowRejoin" <c:if test="${studyBo.allowRejoin eq 'Yes'}">checked</c:if> required>
                            <label for="inlineRadio6">Yes</label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" class="rejoin_radio" id="inlineRadio7" value="No" name="allowRejoin" <c:if test="${studyBo.allowRejoin eq 'No'}">checked</c:if> required>
                            <label for="inlineRadio7">No</label>
                        </span>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                    <div class="col-md-7 p-none mt-sm">
                       <div class="form-group m-none">
                          <textarea class="form-control" name="allowRejoinText" maxlength="250" rows="5" id="rejoin_comment" placeholder="Please enter text that the user should see when they leave a study to let them know they can or cannot rejoin the study" required>${studyBo.allowRejoinText}</textarea>
                          <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                 <!-- End Section-->
                
                 <!-- Start Section-->
                
                 <!-- End Section-->
                
              
            </div>
            <!--  End body tab section -->
            </form:form>
            
            
            
        </div>
        <!-- End right Content here -->
</body>
<form:form action="/fdahpStudyDesigner/adminStudies/viewBasicInfo.do" id="basicInfoFormId" name="basicInfoFormId" method="post">
	<input type="hidden" id="studyId" name="studyId" value="${studyBo.id}">
</form:form>
<script>
$(document).ready(function(){
		$(".menuNav li.active").removeClass('active');
	    $(".menuNav li.second").addClass('active');  
		$(".rejoin_radio").click(function(){
		    $("#rejoin_comment").val('');
		    $("#rejoin_comment").attr('placeholder','Please enter text that the user should see when they leave a study to let them know they can or cannot rejoin the study');
		})
		
		$("#completedId").click(function(){
        	$("#buttonText").val('completed');
            $("#settingfoFormId").submit();
         });
         
         $("#cancelId").click(function(){
            $("#basicInfoFormId").submit();
         });
         
         $("#saveId").click(function(){
        	$('#settingfoFormId').validator('destroy');
        	$("#buttonText").val('save');
            $("#settingfoFormId").submit();
         });
});
</script>