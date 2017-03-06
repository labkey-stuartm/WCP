<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateInstructionStep.do" name="basicInfoFormId" id="basicInfoFormId" method="post" data-toggle="validator" role="form">
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="mr-xs"><a href="#"><img src="../images/icons/back-b.png"/></a></span> Add Instruction Step</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage();">Cancel</button>
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
      <div class="mt-lg">
         <div class="gray-xs-f mb-xs">Title</div>
         <div class="form-group">
            <input type="text" class="form-control" required name="instructionTitle" id="instructionTitle" value="${instructionsBo.instructionTitle}" maxlength="250"/>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- form- input-->
      <div class="mt-lg">
         <div class="gray-xs-f mb-xs">Instruction Text</div>
         <div class="form-group m-none">
            <textarea class="form-control" rows="5" id="instructionText" name="instructionText" required maxlength="2500">${instructionsBo.instructionText}</textarea>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- form- input-->
      <div class="col-md-4 col-lg-4 mt-sm p-none">
         <div class="gray-xs-f mb-xs">Button Text</div>
         <div class="form-group">
            <input type="text" class="form-control" required name="buttonText" id="buttonText" value="${instructionsBo.buttonText}" maxlength="150"/>
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
});
function saveInstruction(item){
	var instruction_id = $("#id").val();
	var questionnaire_id = $("#questionnaireId").val();
	var instruction_title = $("#instructionTitle").val();
	var instruction_text = $("#instructionText").val();
	var button_text = $("#buttonText").val();
	
	var instruction = new Object()
	if(questionnaire_id != null && questionnaire_id !='' && typeof questionnaire_id != 'undefined'){
		instruction.questionnaireId = questionnaire_id;
		instruction.id = instruction_id;
		instruction.instructionTitle = instruction_title;
		instruction.instructionText = instruction_text;
		instruction.buttonText = button_text;
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
					$("#id").val(instructionId);
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
function goToBackPage(){
	window.history.back();
}
</script>