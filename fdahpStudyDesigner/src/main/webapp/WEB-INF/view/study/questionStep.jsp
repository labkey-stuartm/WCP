<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
</script>
<!-- Start right Content here -->
<div class="col-sm-10 col-rc white-bg p-none">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f dis-line pull-left line34">
            <span class="mr-sm" onclick="goToBackPage(this);"><a href="#"><img src="../images/icons/back-b.png"/></a></span> Add Question Step
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="saveQuestionStepQuestionnaire(this);">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn" id="doneId">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateQuestionStepQuestionnaire.do" name="questionStepId" id="questionStepId" method="post" data-toggle="validator" role="form">
   <div class="right-content-body pt-none pl-none pr-none">
      <ul class="nav nav-tabs review-tabs gray-bg">
         <li class="stepLevel active"><a data-toggle="tab" href="#sla">Step-level Attributes</a></li>
         <li class="questionLevel"><a data-toggle="tab" href="#qla">Question-level Attributes</a></li>
         <li class="responseLevel"><a data-toggle="tab" href="#rla">Response-level Attributes</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <!-- Step-level Attributes--> 
         <input type="hidden" name="stepId" id="stepId" value="${questionnairesStepsBo.stepId}">
         <input type="hidden" name="questionnairesId" id="questionnairesId" value="${questionnaireId}">
         <input type="hidden" name="stepType" id="stepType" value="Question">
         <input type="hidden" name="instructionFormId" id="instructionFormId" value="${questionnairesStepsBo.instructionFormId}">
         <input type="hidden" id="type" name="type" value="complete" />
         <div id="sla" class="tab-pane fade in active mt-xlg">
            <div class="row">
               <div class="col-md-6 pl-none">
                  <div class="gray-xs-f mb-xs">Step title or Key * (1 to 15 characters) <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                  <div class="form-group mb-none">
                     <input type="text" class="form-control" name="stepShortTitle" id="stepShortTitle" value="${questionnairesStepsBo.stepShortTitle}" required maxlength="15"/>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Step Type</div>
                  <div>Question Step</div>
               </div>
               <div class="clearfix"></div>
               <div class="col-md-4 col-lg-3 p-none">
                  <div class="gray-xs-f mb-xs">Default Destination Step <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                  <div class="form-group">
                     <select name="destinationStep" id="destinationStepId" data-error="Please choose one title" class="selectpicker" required>
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${instructionsBo.questionnairesStepsBo.destinationStep eq destinationStep.stepId ? 'selected' :''}>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${instructionsBo.questionnairesStepsBo.destinationStep eq 0 ? 'selected' :''}>Completion Step</option>
				     </select>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
            </div>
         </div>
         <!---  Form-level Attributes ---> 
         <div id="qla" class="tab-pane fade mt-xlg">
          <input type="hidden" name="questionsBo.id" id="questionId" value="${questionnairesStepsBo.questionsBo.id}">
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Text of the question <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionsBo.question" id="questionTextId" placeholder="Type the question you wish to ask the participant" value="${questionnairesStepsBo.questionsBo.question}" required maxlength="250"/>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description of the question</div>
               <div class="form-group">
                  <textarea class="form-control" rows="4" name="questionsBo.description" id="descriptionId" placeholder="Enter a line that describes your question, if needed" maxlength="500">${questionnairesStepsBo.questionsBo.description}</textarea>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div>
               <div class="gray-xs-f mb-xs">Is this a Skippable Step?</div>
               <div>
                  <span class="radio radio-info radio-inline p-45">
                     <input type="radio" id="skiappableYes" value="Yes" name="skiappable"  ${empty questionnairesStepsBo.skiappable  || questionnairesStepsBo.skiappable=='Yes' ? 'checked':''}>
                     <label for="skiappableYes">Yes</label>
                  </span>
                  <span class="radio radio-inline">
                     <input type="radio" id="skiappableNo" value="No" name="skiappable" ${questionnairesStepsBo.skiappable=='No' ?'checked':''}>
                     <label for="skiappableNo">No</label>
                  </span>
             </div>
            </div>
            <div class="mt-md">
               <div class="gray-xs-f">Response Type <span class="requiredStar">*</span></div>
               <div class="gray-xs-f mb-xs"><small>The type of interface needed to capture the response</small></div>
               <div class="clearfix"></div>
               <div class="col-md-4 col-lg-3 p-none">
                  <div class="form-group">
                     <select id="responseTypeId" class="selectpicker" name="questionsBo.responseType" required value="${questionnairesStepsBo.questionsBo.responseType}">
                      <option value=''>Select</option>
                      <c:forEach items="${questionResponseTypeMasterInfoList}" var="questionResponseTypeMasterInfo">
                      	<option value="${questionResponseTypeMasterInfo.id}" ${questionnairesStepsBo.questionsBo.responseType eq questionResponseTypeMasterInfo.id ? 'selected' : ''}>${questionResponseTypeMasterInfo.responseType}</option>
                      </c:forEach>
                     </select>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
               <div class="col-md-6 pl-none">
                  <div class="gray-xs-f mb-xs">Description of response type <span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                  <div id="responseTypeDescrption">
                     A numeric answer format to provide response using a numeric keyboard.
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Data Type</div>
                  <div id="responseTypeDataType">Double</div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="mt-lg mb-lg" id="addLineChartContainerId" style="display: none">
               <span class="checkbox checkbox-inline">
               <input type="checkbox" id="addLineChart" name="questionsBo.addLineChart" value="Yes" ${questionnairesStepsBo.questionsBo.addLineChart eq 'Yes' ? 'checked':''}>
               <label for="addLineChart"> Add response data to line chart on app dashboard </label>
               </span>
            </div>
            <div class="clearfix"></div>
            <div id="chartContainer" style="display: none">
            <div class="col-md-6 p-none">
               <div class="gray-xs-f mb-xs">Time range for the chart <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass chartrequireClass" id="lineChartTimeRangeId" name="questionsBo.lineChartTimeRange" value="${questionnairesStepsBo.questionsBo.lineChartTimeRange}">
                       <option value="" selected disabled>Select</option>
	                   <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                        <option value="${timeRangeAttr}" ${questionnairesStepsBo.questionsBo.lineChartTimeRange eq timeRangeAttr ? 'selected':''}>${timeRangeAttr}</option>
	                   </c:forEach>
                  </select>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div>
               <div class="gray-xs-f mb-xs">Allow rollback of chart? <span class="sprites_icon info"></span></div>
               <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" id="allowRollbackChartYes" value="Yes" name="questionsBo.allowRollbackChart">
                  <label for="allowRollbackChartYes">Yes</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" id="allowRollbackChartNo" value="No" name="questionsBo.allowRollbackChart">
                  <label for="allowRollbackChartNo">No</label>
                  </span>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Title for the chart <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control chartrequireClass" name="questionsBo.chartTitle" id="chartTitleId" value="${questionnairesStepsBo.questionsBo.chartTitle}">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            </div>
            <div class="clearfix"></div>
            <div class="bor-dashed mt-sm mb-md"></div>
            <div class="clearfix"></div>
            <div class="mb-lg" id="useStasticDataContainerId" style="display: none">
               <span class="checkbox checkbox-inline">
               <input type="checkbox" id= "useStasticData" value="Yes" name="questionsBo.useStasticData" ${questionnairesStepsBo.questionsBo.useStasticData eq 'Yes' ? 'checked':''}>
               <label for="useStasticData"> Use response data for statistic on dashboard</label>
               </span>
            </div>
            <div class="clearfix"></div>
            <div id="statContainer" style="display: none">
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Short identifier name <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="questionsBo.statShortName" id="statShortNameId" value="${questionnairesStepsBo.questionsBo.statShortName}">
               	  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months) <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="questionsBo.statDisplayName" id="statDisplayNameId" value="${questionnairesStepsBo.questionsBo.statDisplayName}">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Display Units (e.g. hours) <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="questionsBo.statDisplayUnits" id="statDisplayUnitsId" value="${questionnairesStepsBo.questionsBo.statDisplayUnits}" >
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Stat Type for image upload <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass requireClass" id="statTypeId" title="Select" name="questionsBo.statType">
			         <option value="" selected disabled>Select</option>
			         <c:forEach items="${statisticImageList}" var="statisticImage">
			            <option value="${statisticImage.statisticImageId}" ${questionnairesStepsBo.questionsBo.statType eq statisticImage.statisticImageId ? 'selected':''}>${statisticImage.value}</option>
			         </c:forEach>
			      </select> 
			      <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Formula for to be applied <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass requireClass" id="statFormula" title="Select" name="questionsBo.statFormula">
			         <option value="" selected disabled>Select</option>
			         <c:forEach items="${activetaskFormulaList}" var="activetaskFormula">
			            <option value="${activetaskFormula.activetaskFormulaId}" ${questionnairesStepsBo.questionsBo.statFormula eq activetaskFormula.activetaskFormulaId ? 'selected':''}>${activetaskFormula.value}</option>
			         </c:forEach>
			      </select>
			      <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Time ranges options available to the mobile app user</div>
               <div class="clearfix"></div>
            </div>
		</div>
            <div class="clearfix"></div>
            <div>
               <div>
                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Month</span></span>
                  <span class="txt-gray">(Rollback option provided for these three options)</span>
               </div>
               <div class="mt-sm">
                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Custom Start and End Date</span></span>
               </div>
            </div>
         </div>
         <!---  Form-level Attributes ---> 
         <div id="rla" class="tab-pane fade mt-xlg">
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Response Type * <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control" id="rlaResonseType" disabled>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
               <div class="col-md-6 pl-none">
                  <div class="gray-xs-f mb-xs">Description of response type</div>
                  <div id="rlaResonseTypeDescription">
                     Represents an answer format that includes a slider control.
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Data Type</div>
                  <div id="rlaResonseDataType">Double</div>
               </div>
            </div>
            <div class="clearfix"></div>
            <input type="hidden" class="form-control" name="questionReponseTypeBo.responseTypeId" id="responseTypeId" value="${questionnairesStepsBo.questionReponseTypeBo.responseTypeId}">
            <input type="hidden" class="form-control" name="questionReponseTypeBo.questionsResponseTypeId" id="questionsResponseTypeId" value="${questionnairesStepsBo.questionReponseTypeBo.questionsResponseTypeId}">
            <div id="Scale" style="display: none">
            <div class="mt-lg">
               <div class="gray-xs-f mb-xs">Scale Type <span class="requiredStar">*</span></div>
               <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" class="ScaleRequired" id="vertical" value="true" name="questionReponseTypeBo.vertical"  ${questionnairesStepsBo.questionReponseTypeBo.vertical ? 'checked':''} >
                  <label for="vertical">Vertical</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" class="ScaleRequired" id="horizontal" value="false" name="questionReponseTypeBo.vertical" ${empty questionnairesStepsBo.questionReponseTypeBo.vertical || !questionnairesStepsBo.questionReponseTypeBo.vertical ? 'checked':''} >
                  <label for="horizontal">Horizontal</label>
                  </span>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
               <div class="col-md-6 pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Minimum Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired"  name="questionReponseTypeBo.minValue" id="scaleMinValueId" value="${questionnairesStepsBo.questionReponseTypeBo.minValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
               <div class="col-md-6 pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Maximum Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.maxValue" id="scaleMaxValueId" value="${questionnairesStepsBo.questionReponseTypeBo.maxValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row mt-sm">
               <div class="col-md-6 pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Default value (slider position) <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.defaultValue" id="scaleDefaultValueId" value="${questionnairesStepsBo.questionReponseTypeBo.defaultValue}" onkeypress="return isNumber(event)">
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for minimum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.minDescription" id="scaleMinDescriptionId" value="${questionnairesStepsBo.questionReponseTypeBo.minDescription}" placeholder="Type the question you wish to ask the participant" />
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for maximum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.maxDescription" id="scaleMaxDescriptionId" value="${questionnairesStepsBo.questionReponseTypeBo.maxDescription}" placeholder="Type the question you wish to ask the participant" />
               </div>
            </div>
            <div class="col-md-4 col-lg-4 p-none mb-lg">
               <div class="gray-xs-f mb-xs">Number of Steps  <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.step" id="scaleStepId" value="${questionnairesStepsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            </div>
         </div>
      </div>
   </div>
   </form:form>
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	    var a = $(".col-lc").height();
	    var b = $(".col-rc").height();
	    if(a > b){
	        $(".col-rc").css("height", a);	
	    }else{
	        $(".col-rc").css("height", "auto");
	    }
	});
     $("#doneId").click(function(){
    	 if(isFromValid("#questionStepId")){
    		 document.questionStepId.submit();
    		 /* if(stepId != null && stepId!= '' && typeof stepId !='undefined'){
    		    if (!table.data().count() ) {
      				$('#alertMsg').show();
      				$("#alertMsg").removeClass('s-box').addClass('e-box').html("Add atleast one question");
      				setTimeout(hideDisplayMessage, 4000);
      	 			$('.formLevel a').tab('show');
 	     	 	}else{
 	     	 		document.formStepId.submit();	 
 	     	    } 
    		 }else{
    			// document.formStepId.submit();
    			 saveFormStepQuestionnaire(this, function(val) {
    	    	 if(val){
    	    		 if (!table.data().count() ) {
    	      				$('#alertMsg').show();
    	      				$("#alertMsg").removeClass('s-box').addClass('e-box').html("Add atleast one question");
    	      				setTimeout(hideDisplayMessage, 4000);
    	      	 			$('.formLevel a').tab('show');
    	 	     	 }
    	    	 }
    			});
    		 } */
    		 
		}else{
		   $('.stepLevel a').tab('show');
		} 
     });
     $("#stepShortTitle").blur(function(){
     	var shortTitle = $(this).val();
     	var questionnaireId = $("#questionnairesId").val();
     	var stepType="Question";
     	var thisAttr= this;
     	var existedKey = '${questionnairesStepsBo.stepShortTitle}';
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
     $("#addLineChart").on('change',function(){
    	if($(this).is(":checked")){
    		$(this).val("Yes");
    		$("#chartContainer").show();
    		$(".chartrequireClass").attr('required',true);
    	} else{
    		$(this).val("No");
    		$("#chartContainer").hide();
    		$(".chartrequireClass").attr('required',false);
    	}
     });
    $("#useStasticData").on('change',function(){
    	if($(this).is(":checked")){
    		$(this).val("Yes");
    		$("#statContainer").show();
    		$(".requireClass").attr('required',true);
    	} else{
    		$(this).val("No");
    		$("#statContainer").hide();
    		$(".requireClass").attr('required',false);
    	}
    });
    $("#scaleMinValueId").blur(function(){
    	var value= $(this).val();
    	var maxValue = $("#scaleMaxValueId").val();
    	if(value >= -10000 && value <= 9999){
    		$(this).validator('validate');
    		$(this).parent().removeClass("has-danger").removeClass("has-error");
            $(this).parent().find(".help-block").html("");
    	}else if(value > maxValue){
    		$(this).val('');
   		    $(this).parent().addClass("has-danger").addClass("has-error");
            $(this).parent().find(".help-block").empty();
            $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer number in the range (Min, 10000)</li></ul>");
    	}else{
    		$(this).val('');
   		    $(this).parent().addClass("has-danger").addClass("has-error");
            $(this).parent().find(".help-block").empty();
            $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer number in the range (Min, 10000) </li></ul>");
    	}
    });
    $("#scaleMaxValueId").blur(function(){
    	var value= $(this).val();
    	var minValue = $("#scaleMinValueId").val();
    	if(minValue != ''){
    		if(value >= minValue+1 && value <= 9999){
    			$(this).validator('validate');
        		$(this).parent().removeClass("has-danger").removeClass("has-error");
                $(this).parent().find(".help-block").html("");
    		}else if(value < minValue){
    			$(this).val('');
       		    $(this).parent().addClass("has-danger").addClass("has-error");
                $(this).parent().find(".help-block").empty();
                $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer number in the range (Min+1, 10000)</li></ul>");
    		}else{
    			$(this).val('');
       		    $(this).parent().addClass("has-danger").addClass("has-error");
                $(this).parent().find(".help-block").empty();
                $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer number in the range (Min+1, 10000)</li></ul>");
    		}
    	}
    });
    $("#scaleStepId").blur(function(){
    	var value= $(this).val();
    	if(value >= 1 && value <= 13){
    		$(this).validator('validate');
    		$(this).parent().removeClass("has-danger").removeClass("has-error");
            $(this).parent().find(".help-block").html("");
    	}else{
    	     $(this).val('');
    		 $(this).parent().addClass("has-danger").addClass("has-error");
             $(this).parent().find(".help-block").empty();
             $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer between the 1 and 13 </li></ul>");
    	}
    });
    $("#scaleDefaultValueId").blur(function(){
    	var value= $(this).val();
    	var minValue = $("#scaleMinValueId").val();
		var maxValue = $("#scaleMaxValueId").val();
		if(value >= minValue && value <= maxValue){
			$(this).validator('validate');
    		$(this).parent().removeClass("has-danger").removeClass("has-error");
            $(this).parent().find(".help-block").html("");
		}else{
			 $(this).val('');
    		 $(this).parent().addClass("has-danger").addClass("has-error");
             $(this).parent().find(".help-block").empty();
             $(this).parent().find(".help-block").append("<ul class='list-unstyled'><li>Please enter an integer between the minimum and maximum  </li></ul>");
		}
    })
    var responseTypeId= '${questionnairesStepsBo.questionsBo.responseType}';
    if(responseTypeId != null && responseTypeId !='' && typeof responseTypeId != 'undefined'){
    	 getResponseType(responseTypeId);
    }
    $("#responseTypeId").on("change",function(){
    	var value= $(this).val();
    	console.log(value);
    	 getResponseType(value);
    });
});
function getResponseType(id){
	if(id != null && id !='' && typeof id != 'undefined'){
		<c:forEach items="${questionResponseTypeMasterInfoList}" var="questionResponseTypeMasterInfo">
		 var infoId = Number('${questionResponseTypeMasterInfo.id}'); 
		 var responseType = '${questionResponseTypeMasterInfo.responseType}';
		 var type=''
		 if(responseType == 'Continuous Scale'){
			 type = 'Scale';
		 }else{
			 type=responseType;
		 }
		 $("#"+type.replace(/\s/g, '')).hide();
		 $("."+type.replace(/\s/g, '')+"Required").attr("required",false);
		 if(id == infoId){
    		var description = '${questionResponseTypeMasterInfo.description}';
    		var dataType = '${questionResponseTypeMasterInfo.dataType}';
    		var dashboard = '${questionResponseTypeMasterInfo.dashBoardAllowed}';
    		$("#responseTypeDataType").text(dataType);
    		$("#responseTypeDescrption").text(description);
    		$("#rlaResonseType").val(responseType)
    		$("#rlaResonseDataType").text(dataType);
    		$("#rlaResonseTypeDescription").text(description);
    		console.log(dashboard);
    		$("#"+type.replace(/\s/g, '')).show();
    		 $("."+type.replace(/\s/g, '')+"Required").attr("required",true);
    		if(dashboard == 'true'){
    			$("#useStasticDataContainerId").show();
        		$("#addLineChartContainerId").show();	
        		console.log("ifff");
        		 if($("#addLineChart").is(":checked")){
        			 $("#chartContainer").show();
        			 $(".chartrequireClass").attr('required',true);
        		 }
        		 if($("#useStasticData").is(":checked")){
        			 $("#statContainer").show();
        			 $(".requireClass").attr('required',true);
        		 }
    		}else{
    			$("#useStasticDataContainerId").hide();
        		$("#addLineChartContainerId").hide();
    		}
    		
    	 }
    	</c:forEach>
	}
}
function saveQuestionStepQuestionnaire(item,callback){
	
	var stepId =$("#stepId").val();
	var quesstionnaireId=$("#questionnairesId").val();
	var questionId = $("#instructionFormId").val();
	var shortTitle=$("#stepShortTitle").val();
	var skiappable=$('input[name="skiappable"]:checked').val();
	var destionationStep=$("#destinationStepId").val();
	var repeatable=$('input[name="repeatable"]:checked').val();
	var repeatableText=$("#repeatableText").val();
	var step_type=$("#stepType").val();
	var instructionFormId = $("#instructionFormId").val();
	
	var questionnaireStep = new Object();
	questionnaireStep.stepId=stepId;
	questionnaireStep.questionnairesId=quesstionnaireId;
	questionnaireStep.instructionFormId=instructionFormId;
	questionnaireStep.stepShortTitle=shortTitle;
	questionnaireStep.skiappable=skiappable;
	questionnaireStep.destinationStep=destionationStep;
	questionnaireStep.type="save";
	questionnaireStep.stepType=step_type;
	
	var questionsBo = new Object();
	var questionText = $("#questionTextId").val();
	var descriptionText = $("#descriptionId").val();
	var responseType = $("#responseTypeId").val();
	var addLinceChart = $('input[name="questionsBo.addLineChart"]:checked').val();
	var lineChartTimeRange = $("#lineChartTimeRangeId").val();
	var allowRollbackChart = $('input[name="questionsBo.allowRollbackChart"]:checked').val();
	var chartTitle = $('#chartTitleId').val();
	var useStasticData = $('input[name="questionsBo.useStasticData"]:checked').val();
	var statShortName = $("#statShortNameId").val();
	var statDisplayName = $("#statDisplayNameId").val();
	var statDisplayUnits = $("#statDisplayUnitsId").val();
	var statType=$("#statType").val();
	var statFormula=$("#statFormula").val();
	var questionid = $("#questionId").val();
	
	questionsBo.id=questionId;
	questionsBo.question=questionText;
	questionsBo.description=descriptionText;
	questionsBo.responseType=responseType;
	questionsBo.lineChartTimeRange=lineChartTimeRange;
	questionsBo.addLineChart=addLinceChart;
	questionsBo.allowRollbackChart=allowRollbackChart;
	questionsBo.chartTitle=chartTitle;
	questionsBo.useStasticData=useStasticData;
	questionsBo.statShortName=statShortName;
	questionsBo.statDisplayName=statDisplayName;
	questionsBo.statDisplayUnits=statDisplayUnits;
	questionsBo.statType=statType;
	questionsBo.statFormula=statFormula;
	
	questionnaireStep.questionsBo=questionsBo;
	
	var questionReponseTypeBo = new  Object();
	var minValue='';
	var maxValue='';
	var defaultValue='';
	var maxdescription='';
	var mindescrption='';
	var step='';
	var resType = $("#rlaResonseType").val();
	if(resType == "Scale"){
		minValue = $("#scaleMinValueId").val();
		maxValue = $("#scaleMaxValueId").val();
		defaultValue = $("#scaleDefaultValueId").val();
		mindescrption = $("#scaleMinDescriptionId").val();
		maxdescription = $("#scaleMaxDescriptionId").val();
		step = $("#scaleStepId").val();
	}else{
		minValue = $("#continueScaleMinValueId").val();
		maxValue = $("#continueScaleMaxValueId").val();
		defaultValue = $("#continueScaleDefaultValueId").val();
		mindescrption = $("#continueScaleMinDescriptionId").val();
		maxdescription = $("#continueScaleMaxDescriptionId").val();
		step = $("#continueScaleStepId").val();
	}
	var response_type_id = $("#responseTypeId").val();
	var question_response_type_id = $("questionsResponseTypeId").val();
	
	questionReponseTypeBo.responseTypeId=response_type_id;
	questionReponseTypeBo.questionsResponseTypeId=question_response_type_id;
	questionReponseTypeBo.minValue=minValue;
	questionReponseTypeBo.maxValue=maxValue;
	questionReponseTypeBo.defaultValue=defaultValue;
	questionReponseTypeBo.minDescription=mindescrption;
	questionReponseTypeBo.maxDescription=maxdescription;
	questionReponseTypeBo.step=step;
	
	questionnaireStep.questionReponseTypeBo=questionReponseTypeBo;
	if(quesstionnaireId != null && quesstionnaireId!= '' && typeof quesstionnaireId !='undefined' && 
			shortTitle != null && shortTitle!= '' && typeof shortTitle !='undefined'){
		var data = JSON.stringify(questionnaireStep);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveQuestionStep.do",
	          type: "POST",
	          datatype: "json",
	          data: {questionnaireStepInfo:data},
	          beforeSend: function(xhr, settings){
	              xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	          },
	          success:function(data){
	        	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					
					var instructionId = jsonobject.instructionId;
					var stepId = jsonobject.stepId;
					var questionId = jsonobject.stepId;
					var questionResponseId = jsonobject.stepId;
					
					$("#stepId").val(stepId);
					$("#questionId").val(questionId);
					$("#responseTypeId").val(questionResponseId);
					
					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Question Step saved successfully");
					$(item).prop('disabled', false);
					$('#alertMsg').show();
					if (callback)
						callback(true);
				}else{
					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
					$('#alertMsg').show();
					if (callback)
  						callback(false);
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
	}
}
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