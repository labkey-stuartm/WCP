<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
 <div class="col-sm-10 col-rc white-bg p-none">
   <!--  Start top tab section-->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateInstructionStep.do" name="basicInfoFormId" id="basicInfoFormId" method="post" data-toggle="validator" role="form">
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="mr-xs" onclick="goToBackPage(this);"><a href="#"><img src="../images/icons/back-b.png"/></a></span> Add Instruction Step</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="saveInstruction(this);">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="submit" class="btn btn-primary blue-btn">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <!-- form- input-->
      <input type="hidden" name="id" id="id" value="${instructionsBo.id}">
      <input type="hidden" name="questionnaireId" id="questionnaireId" value="${questionnaireId}">
       <input type="hidden" name="questionnairesStepsBo.stepId" id="stepId" value="${instructionsBo.questionnairesStepsBo.stepId}">
		    <div class="col-md-6 pl-none">
			   <div class="gray-xs-f mb-xs">Step title or Key * (1 to 15 characters) <span class="ml-xs sprites_v3 filled-tooltip"></span></div>
			   <div class="form-group mb-none">
			      <input type="text" class="form-control" name="questionnairesStepsBo.stepShortTitle" id="shortTitleId" value="${instructionsBo.questionnairesStepsBo.stepShortTitle}" required="required" maxlength="50"/>
		      	   <div class="help-block with-errors red-txt"></div>
			   </div>
			</div>
			<div class="col-md-6">
			   <div class="gray-xs-f mb-xs">Step Type</div>
			   <div>Instruction Step</div>
			</div>
		  <div class="clearfix"></div>
	      <div class="gray-xs-f mb-xs">Title <span class="requiredStar">*</span></div>
		  <div class="form-group">
			    <input type="text" class="form-control" required name="instructionTitle" id="instructionTitle" value="${instructionsBo.instructionTitle}" maxlength="250"/>
		  </div>
		  <div class="clearfix"></div>
		  
		  <div class="gray-xs-f mb-xs">Instruction Text <span class="requiredStar">*</span></div>
		  <textarea class="form-control" rows="5" id="instructionText" name="instructionText" required maxlength="2500">${instructionsBo.instructionText}</textarea>
          <div class="help-block with-errors red-txt"></div>
          <div class="clearfix"></div>
          
			<div class="col-md-4 col-lg-3 p-none">
			   <div class="gray-xs-f mb-xs">Default Destination Step  <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip"></span></div>
			   <div class="form-group">
			      <select name="questionnairesStepsBo.stepShortTitle.destinationStep" id="destinationStepId" data-error="Please choose one title" class="selectpicker" >
			         <option value="0" ${questionnairesStepsBo.destinationStep eq 0 ? 'selected' :''}>Completion Step</option>
			         <option>Step 4: DosageQuestion</option>
			         <option>Step 4: DosageQuestion</option>
			         <option>Step 4: DosageQuestion</option>
			      </select>
			      <div class="help-block with-errors red-txt"></div>
			   </div>
			</div>
   </div>
   </form:form>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){ 
	$(".menuNav li").removeClass('active');
	$(".sixthQuestionnaires").addClass("active");
	$("#shortTitleId").blur(function(){
    	var shortTitle = $(this).val();
    	var questionnaireId = $("#questionnaireId").val();
    	var stepType="Instruction";
    	var thisAttr= this;
    	var existedKey = '${instructionsBo.questionnairesStepsBo.stepShortTitle}';
    	if(shortTitle != null && shortTitle !='' && typeof shortTitle!= 'undefined'){
    		if( existedKey !=shortTitle){
    			$.ajax({
                    url: "/fdahpStudyDesigner/adminStudies/validateQuestionnaireStepKey.do",
                    type: "POST",
                    datatype: "json",
                    data: {
                    	shortTitle : shortTitle,
                    	questionnaireId : questionnaireId,
                    	stepType : stepType
                    },
                    beforeSend: function(xhr, settings){
                        xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
                    },
                    success:  function getResponse(data){
                        var message = data.message;
                        console.log(message);
                        if('SUCCESS' != message){
                            $(thisAttr).validator('validate');
                            $(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
                            $(thisAttr).parent().find(".help-block").html("");
                        }else{
                            $(thisAttr).val('');
                            $(thisAttr).parent().addClass("has-danger").addClass("has-error");
                            $(thisAttr).parent().find(".help-block").empty();
                            $(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + shortTitle + "' already exists.</li></ul>");
                        }
                    }
              });
    		}
    	}
    });
});
function saveInstruction(item){
	var instruction_id = $("#id").val();
	var questionnaire_id = $("#questionnaireId").val();
	var instruction_title = $("#instructionTitle").val();
	var instruction_text = $("#instructionText").val();
	
	var shortTitle = $("#shortTitleId").val();
	var destinationStep = $("#destinationStepId").val();
	var step_id=$("#stepId").val(); 
	
	var instruction = new Object();
	if((questionnaire_id != null && questionnaire_id !='' && typeof questionnaire_id != 'undefined') && 
			(shortTitle != null && shortTitle !='' && typeof shortTitle != 'undefined')){
		instruction.questionnaireId = questionnaire_id;
		instruction.id = instruction_id;
		instruction.instructionTitle = instruction_title;
		instruction.instructionText = instruction_text;

		var questionnaireStep = new Object();
		questionnaireStep.stepId=step_id;
		questionnaireStep.stepShortTitle = shortTitle;
		questionnaireStep.destinationStep=destinationStep
		instruction.questionnairesStepsBo=questionnaireStep;
		
		var data = JSON.stringify(instruction);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveInstructionStep.do",
	          type: "POST",
	          datatype: "json",
	          data: {instructionsInfo:data},
	          beforeSend: function(xhr, settings){
	              xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	          },
	          success:function(data){
	        	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					var instructionId = jsonobject.instructionId;
					var stepId = jsonobject.stepId;
					$("#id").val(instructionId);
					$("#stepId").val(stepId);
					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Instruction saved successfully");
					$(item).prop('disabled', false);
					$('#alertMsg').show();
				}else{
					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
					$('#alertMsg').show();
				}
				setTimeout(hideDisplayMessage, 4000);
	          },
	          error: function(xhr, status, error) {
    			  $(item).prop('disabled', false);
    			  $('#alertMsg').show();
    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
    			  setTimeout(hideDisplayMessage, 4000);
    		  }
	   }); 
	}else{
		 $('#alertMsg').show();
		 $("#alertMsg").removeClass('s-box').addClass('e-box').html("No QuestionnaireId Mapped");
		 setTimeout(hideDisplayMessage, 4000);
	}
}
/* function goToBackPage(){
	//window.history.back();
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do";
	document.body.appendChild(a).click();
} */
function goToBackPage(item){
	//window.history.back();
	//<c:if test="${actionPage ne 'view'}">
		$(item).prop('disabled', true);
		bootbox.confirm({
				closeButton: false,
				message : 'You are about to leave the page and any unsaved changes will be lost. Are you sure you want to proceed?',	
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
			        	var a = document.createElement('a');
			        	a.href = "/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do";
			        	document.body.appendChild(a).click();
			        }else{
			        	$(item).prop('disabled', false);
			        }
			    }
		});
	//</c:if>
	/* <c:if test="${actionPage eq 'view'}">
		var a = document.createElement('a');
		a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do";
		document.body.appendChild(a).click();
	</c:if> */
}
</script>