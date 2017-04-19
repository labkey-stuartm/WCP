<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<!-- <style>
div.tooltip-inner {
    max-width: 300px;
}
</style> -->
<!-- Start right Content here -->
<div class="col-sm-10 col-rc white-bg p-none">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f dis-line pull-left line34">
            <span class="mr-sm" onclick="goToBackPage(this);"><img src="../images/icons/back-b.png"/></span>
            <c:if test="${actionTypeForQuestionPage == 'edit'}">Edit Question Step</c:if>
         	<c:if test="${actionTypeForQuestionPage == 'view'}">View Question Step</c:if>
         	<c:if test="${actionTypeForQuestionPage == 'add'}">Add Question Step</c:if>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
         </div>
         <c:if test="${actionTypeForQuestionPage ne 'view'}">
	         <div class="dis-line form-group mb-none mr-sm">
	            <button type="button" class="btn btn-default gray-btn questionStepSaveDoneButtonHide" onclick="saveQuestionStepQuestionnaire(this);">Save</button>
	         </div>
	         <div class="dis-line form-group mb-none">
	            <button type="button" class="btn btn-primary blue-btn questionStepSaveDoneButtonHide" id="doneId">Done</button>
	         </div>
         </c:if>
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
                  <div class="gray-xs-f mb-xs">Step title or Key (1 to 15 characters) <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="A human readable step identifier and must be unique across all steps of the questionnaire."></span></div>
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
               <c:if test="${questionnaireBo.branching}">
               <div class="col-md-4 col-lg-3 p-none">
                  <div class="gray-xs-f mb-xs">Default Destination Step <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The step that the user must be directed to from this step."></span></div>
                  <div class="form-group">
                     <select name="destinationStep" id="destinationStepId" data-error="Please choose one title" class="selectpicker" required>
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.destinationStep eq destinationStep.stepId ? 'selected' :''}>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${questionnairesStepsBo.destinationStep eq 0 ? 'selected' :''}>Completion Step</option>
				     </select>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
               </c:if>
            </div>
         </div>
         <!---  Form-level Attributes ---> 
         <div id="qla" class="tab-pane fade mt-xlg">
          <input type="hidden" name="questionsBo.id" id="questionId" value="${questionnairesStepsBo.questionsBo.id}">
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Text of the question <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="The question you wish to ask the participant."></span></div>
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
                  <div class="gray-xs-f mb-xs">Description of response type <span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="A numeric answer format to provide response using a numeric keyboard."></span></div>
                  <div id="responseTypeDescrption">
                     A numeric answer format to provide response using a numeric keyboard.
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Data Type</div>
                  <div id="responseTypeDataType">Double</div>
               </div>
            </div>
            <div class="mt-lg mb-lg" id="useAnchorDateContainerId" style="display: none">
            <c:choose>
            	<c:when test="${questionnairesStepsBo.questionsBo.useAnchorDate}">
            		<span class="checkbox checkbox-inline">
			               <input type="checkbox" id="useAnchorDateId" name="questionsBo.useAnchorDate" value="true" ${questionnairesStepsBo.questionsBo.useAnchorDate ? 'checked':''} >
			               <label for="useAnchorDateId"> Use Anchor Date </label>
		               </span>
            	</c:when>
            	<c:otherwise>
            		<span class="tool-tip" data-toggle="tooltip" data-html="true" data-placement="top" <c:if test="${questionnaireBo.frequency ne 'One time' || isAnchorDate}"> title="This field is disabled for one of the following reasons:<br/>1. Your questionnaire is scheduled for a frequency other than 'one-time'<br/>2. There is already another question in the study that has been marked for anchor date<br/>Please make changes accordingly and try again." </c:if> >
		               <span class="checkbox checkbox-inline">
			               <input type="checkbox" id="useAnchorDateId" name="questionsBo.useAnchorDate" value="true" ${questionnairesStepsBo.questionsBo.useAnchorDate ? 'checked':''} <c:if test="${questionnaireBo.frequency ne 'One time' || isAnchorDate}"> disabled </c:if> >
			               <label for="useAnchorDateId"> Use Anchor Date </label>
		               </span>
	               </span>
            	</c:otherwise>
            </c:choose>
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
                  <input type="radio" id="allowRollbackChartYes" value="Yes" name="questionsBo.allowRollbackChart" ${questionnairesStepsBo.questionsBo.allowRollbackChart eq 'Yes' ? 'checked': ''}>
                  <label for="allowRollbackChartYes">Yes</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" id="allowRollbackChartNo" value="No" name="questionsBo.allowRollbackChart" ${questionnairesStepsBo.questionsBo.allowRollbackChart eq 'No' ? 'checked': ''}>
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
            <div class="col-md-4 col-lg-4 p-none">
               <div class="gray-xs-f mb-xs">Response Type  </div>
               <small>The type of interface needed to capture the response</small>
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
            <input type="hidden" class="form-control" name="questionReponseTypeBo.responseTypeId" id="questionResponseTypeId" value="${questionnairesStepsBo.questionReponseTypeBo.responseTypeId}">
            <input type="hidden" class="form-control" name="questionReponseTypeBo.questionsResponseTypeId" id="responseQuestionId" value="${questionnairesStepsBo.questionReponseTypeBo.questionsResponseTypeId}">
            <input type="hidden" class="form-control" name="questionReponseTypeBo.placeholder" id="placeholderTextId" />
            <input type="hidden" class="form-control" name="questionReponseTypeBo.step" id="stepValueId" />
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
                     <div class="gray-xs-f mb-xs">Minimum Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer number in the range (Min, 10000)."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired"  name="questionReponseTypeBo.minValue" id="scaleMinValueId" value="${questionnairesStepsBo.questionReponseTypeBo.minValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
               <div class="col-md-6 pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Maximum Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer number in the range (Min+1, 10000)."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.maxValue" id="scaleMaxValueId" value="${questionnairesStepsBo.questionReponseTypeBo.maxValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row mt-sm">
               <div class="col-md-6  pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Default value (slider position) <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer between the minimum and maximum."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.defaultValue" id="scaleDefaultValueId" value="${questionnairesStepsBo.questionReponseTypeBo.defaultValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for minimum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.minDescription" id="scaleMinDescriptionId" value="${questionnairesStepsBo.questionReponseTypeBo.minDescription}" placeholder="Type the question you wish to ask the participant" maxlength="20"/>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for maximum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.maxDescription" id="scaleMaxDescriptionId" value="${questionnairesStepsBo.questionReponseTypeBo.maxDescription}" placeholder="Type the question you wish to ask the participant" maxlength="20" />
               </div>
            </div>
            <div class="col-md-4 col-lg-4 p-none mb-lg">
               <div class="gray-xs-f mb-xs">Number of Steps  <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Specify the number of steps to divide the scale into."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control ScaleRequired"  id="scaleStepId" value="${questionnairesStepsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            </div>
            <div id="Location" style="display: none">
            	<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Use Current Location <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Choose Yes if you wish to mark the user's current location on the map used to provide the response."></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="LocationRequired" id="useCurrentLocationYes" value="true" name="questionReponseTypeBo.useCurrentLocation"  ${empty questionnairesStepsBo.questionReponseTypeBo.useCurrentLocation || questionnairesStepsBo.questionReponseTypeBo.useCurrentLocation ? 'checked':''} >
	                  <label for="useCurrentLocationYes">Yes</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="LocationRequired" id="useCurrentLocationNo" value="false" name="questionReponseTypeBo.useCurrentLocation" ${!questionnairesStepsBo.questionReponseTypeBo.useCurrentLocation ? 'checked':''} >
	                  <label for="useCurrentLocationNo"">No</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	            </div>
            </div>
            <div id="Email" style="display: none">
	            <div class="row mt-sm">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-12 col-lg-12 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder Text <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control" placeholder="1-40 characters"  id="placeholderId" value="${questionnairesStepsBo.questionReponseTypeBo.placeholder}" maxlength="40">
	                     </div>
	                  </div>
	               </div>
	            </div>
            </div>
           <div id="Text" style="display: none">
           		<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Allow Multiple Lines? <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Choose Yes if you need the user to enter large text in a text area."></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="TextRequired" id="multipleLinesYes" value="true" name="questionReponseTypeBo.multipleLines"  ${questionnairesStepsBo.questionReponseTypeBo.multipleLines ? 'checked':''} >
	                  <label for="multipleLinesYes">Yes</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="TextRequired" id="multipleLinesNo" value="false" name="questionReponseTypeBo.multipleLines" ${empty questionnairesStepsBo.questionReponseTypeBo.multipleLines || !questionnairesStepsBo.questionReponseTypeBo.multipleLines ? 'checked':''} >
	                  <label for="multipleLinesNo">No</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	            </div>
           		<div class="clearfix"></div>
	            <div class="row">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  placeholder="1-50 characters"  id="textPlaceholderId" value="${questionnairesStepsBo.questionReponseTypeBo.placeholder}" maxlength="50">
	                     </div>
	                  </div>
	               </div>
	               <div class="col-md-4">
	                  <div class="col-md-4 col-lg-4 p-none">
	                     <div class="gray-xs-f mb-xs">Max Length  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer for the maximum length of text allowed. If left empty, there will be no max limit applied."></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control" name="questionReponseTypeBo.maxLength" id="textmaxLengthId" value="${questionnairesStepsBo.questionReponseTypeBo.maxLength}" onkeypress="return isNumber(event)">
	                     </div>
	                  </div>
	               </div>
	            </div>
           </div>
           <div id="Height" style="display: none">
           		<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Allow Multiple Lines? <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Select a suitable measurement system for height"></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="HeightRequired" id="measurementSystemLocal" value="Local" name="questionReponseTypeBo.measurementSystem"  ${questionnairesStepsBo.questionReponseTypeBo.measurementSystem eq 'Local'? 'checked':''} >
	                  <label for="measurementSystemLocal">Local</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="HeightRequired" id="measurementSystemMetric" value="Metric" name="questionReponseTypeBo.measurementSystem" ${questionnairesStepsBo.questionReponseTypeBo.measurementSystem eq 'Metric' ? 'checked':''} >
	                  <label for="measurementSystemMetric">Metric</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="HeightRequired" id="measurementSystemUS" value="US" name="questionReponseTypeBo.measurementSystem" ${empty questionnairesStepsBo.questionReponseTypeBo.measurementSystem || questionnairesStepsBo.questionReponseTypeBo.multipleLines eq 'US' ? 'checked':''} >
	                  <label for="measurementSystemUS">US</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	            </div>
           		<div class="clearfix"></div>
	            <div class="row mt-sm">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-12 col-lg-12 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder Text <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control" placeholder="1-20 characters"  id="heightPlaceholderId" value="${questionnairesStepsBo.questionReponseTypeBo.placeholder}" maxlength="20">
	                     </div>
	                  </div>
	               </div>
	            </div>
           </div>
           <div id="Timeinterval" style="display: none;">
	           <div class="row mt-sm">
	           	<div class="col-md-2 pl-none">
	               <div class="gray-xs-f mb-xs">Step value  <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title=" The step in the interval, in minutes. The value of this parameter must be between 1 and 30."></span></div>
	               <div class="form-group">
	                  <input type="text" class="form-control TimeintervalRequired"  id="timeIntervalStepId" value="${questionnairesStepsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	            </div>
	         </div>
          </div>
          <div id="Numeric" style="display: none;">
          	<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Style <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Choose the kind of numeric input needed"></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="NumericRequired" id="styleDecimal" value="Decimal" name="questionReponseTypeBo.style"  ${empty questionnairesStepsBo.questionReponseTypeBo.style || questionnairesStepsBo.questionReponseTypeBo.style eq 'Decimal' ? 'checked':''} >
	                  <label for="styleDecimal">Decimal</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="NumericRequired" id="styleInteger" value="Integer" name="questionReponseTypeBo.style" ${questionnairesStepsBo.questionReponseTypeBo.style eq 'Integer' ? 'checked':''} >
	                  <label for="styleInteger">Integer</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	        </div>
           	<div class="clearfix"></div>
          	<div class="row">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Units(1 to 15 characters)  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter the applicable units for the numeric input"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.unit" id="numericUnitId" value="${questionnairesStepsBo.questionReponseTypeBo.unit}" maxlength="15">
	                     </div>
	                  </div>
	               </div>
	               <div class="col-md-6">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder Text(1 to 30 characters)  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Provide an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  id="numericPlaceholderId" value="${questionnairesStepsBo.questionReponseTypeBo.placeholder}" maxlength="30">
	                     </div>
	                  </div>
	               </div>
	        </div>
          </div>
          <div id="Date" style="display: none;">
          	<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Style <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Choose whether you wish to capture only date from the user or date and time."></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="DateRequired" id="date" value="Date" name="questionReponseTypeBo.style"  ${empty questionnairesStepsBo.questionReponseTypeBo.style || questionnairesStepsBo.questionReponseTypeBo.style eq 'Date' ? 'checked':''} >
	                  <label for="date">Date</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="DateRequired" id="dateTime" value="Date-Time" name="questionReponseTypeBo.style" ${questionnairesStepsBo.questionReponseTypeBo.style eq 'Date-Time' ? 'checked':''} >
	                  <label for="dateTime">Date-Time</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	        </div>
           	<div class="clearfix"></div>
          	<div class="row">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Minimum Date  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter minimum date allowed."></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.minDate" id="minDateId" value="${questionnairesStepsBo.questionReponseTypeBo.minDate}" >
	                        <div class="help-block with-errors red-txt"></div>
	                     </div>
	                  </div>
	               </div>
	       </div>
	       <div class="row">
	               <div class="col-md-6  pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Maximum Date <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter maximum date allowed"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.maxDate"id="maxDateId" value="${questionnairesStepsBo.questionReponseTypeBo.maxDate}" >
	                        <div class="help-block with-errors red-txt"></div>
	                     </div>
	                  </div>
	               </div>
	        </div>
	        <div class="row">
	               <div class="col-md-6  pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Default Date <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter default date to be shown as selected"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.defaultDate" id="defaultDate" value="${questionnairesStepsBo.questionReponseTypeBo.defaultDate}">
	                        <div class="help-block with-errors red-txt"></div>
	                     </div>
	                  </div>
	               </div>
	        </div>
          </div>
          <div id="Boolean" style="display: none;">
          	<div class="clearfix"></div>
          	<div class="row mt-lg" id="0">
          		<input type="hidden" class="form-control" id="responseSubTypeValueId0" name="questionResponseSubTypeList[0].responseSubTypeValueId" value="${questionnairesStepsBo.questionResponseSubTypeList[0].responseSubTypeValueId}">
	          	<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="dispalyText0" name="questionResponseSubTypeList[0].text" value="Yes" readonly="readonly">
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="displayValue0" value="true" name="questionResponseSubTypeList[0].value" readonly="readonly">
				      <div class="help-block with-errors red-txt" ></div>
				   </div>
				</div>
			   <c:if test="${questionnaireBo.branching}">
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Destination Step <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="If there is branching applied to your questionnaire, you can  define destination steps for the Yes and No choices"></span> </div>
				   <div class="form-group">
				       <select name="questionResponseSubTypeList[0].destinationStepId" id="destinationStepId0" title="select" data-error="Please choose one title" class="selectpicker">
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq destinationStep.stepId ? 'selected' :''}>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
				       </select>
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
			   </c:if>
			</div>
			
			<div class="row" id="1">
	          	<div class="col-md-3 pl-none">
	          	<input type="hidden" class="form-control" id="responseSubTypeValueId1" name="questionResponseSubTypeList[1].responseSubTypeValueId" value="${questionnairesStepsBo.questionResponseSubTypeList[1].responseSubTypeValueId}">
				   <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="dispalyText1" name="questionResponseSubTypeList[1].text" value="No" readonly="readonly" >
				      <div class="help-block with-errors red-txt" ></div>
				   </div>
				</div>
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="displayValue1" value="false" name="questionResponseSubTypeList[1].value" readonly="readonly" >
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
				<c:if test="${questionnaireBo.branching}">
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Destination Step <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="If there is branching applied to your questionnaire, you can  define destination steps for the Yes and No choices"></span> </div>
				   <div class="form-group">
				 
				      <select name="questionResponseSubTypeList[1].destinationStepId" id="destinationStepId1" title="select" data-error="Please choose one title" class="selectpicker BooleanRequired" >
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
				     </select>
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
				</c:if>
			</div>
          </div> 
          <div id="ValuePicker" style="display: none;">
          <div class="row mt-lg" id="0">
          	<div class="col-md-3 pl-none">
			   <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter values in the order they must appear in the picker. Each row needs a display text and an associated value that gets captured if that choice is picked by the user. "></span></div>
			</div>
			<div class="col-md-4 pl-none">
			   <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter values in the order they must appear in the picker. Each row needs a display text and an associated value that gets captured if that choice is picked by the user. "></span></div>
			</div>
			<div class="clearfix"></div>
			<div class="ValuePickerContainer">
			<c:choose>
			  <c:when test="${questionnairesStepsBo.questionsBo.responseType eq 4 && fn:length(questionnairesStepsBo.questionResponseSubTypeList) gt 1}">
			  	<c:forEach items="${questionnairesStepsBo.questionResponseSubTypeList}" var="questionResponseSubType" varStatus="subtype">
			  		<div class="value-picker row form-group" id="${subtype.index}">
			  		<input type="hidden" class="form-control" id="valPickSubTypeValueId${subtype.index}" name="questionResponseSubTypeList[${subtype.index}].responseSubTypeValueId" value="${questionResponseSubType.responseSubTypeValueId}">
						<div class="col-md-3 pl-none">
						   <div class="form-group">
						      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[${subtype.index}].text" id="displayValPickText${subtype.index}" value="${questionResponseSubType.text}" maxlength="15">
						      <div class="help-block with-errors red-txt"></div>
						   </div>
						</div>
						<div class="col-md-4 pl-none">
						   <div class="form-group">
						      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[${subtype.index}].value" id="displayValPickValue${subtype.index}" value="${questionResponseSubType.value}" maxlength="50">
						      <div class="help-block with-errors red-txt"></div>
						   </div>
						</div>
						<div class="col-md-2 pl-none mt-md">
						   <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addValuePicker();'>+</span>
				           <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeValuePicker(this);'></span>
						</div>
					</div>
			  	</c:forEach>
			  </c:when>
			  <c:otherwise>
			  	<div class="value-picker row form-group" id="0">
					<div class="col-md-3 pl-none">
					   <div class="form-group">
					      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[0].text" id="displayValPickText0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].text}" maxlength="15">
					      <div class="help-block with-errors red-txt"></div>
					   </div>
					</div>
					<div class="col-md-4 pl-none">
					   <div class="form-group">
					      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[0].value" id="displayValPickValue0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].value}" maxlength="50">
					      <div class="help-block with-errors red-txt"></div>
					   </div>
					</div>
					<div class="col-md-2 pl-none mt-md">
					   <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addValuePicker();'>+</span>
			           <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeValuePicker(this);'></span>
					</div>
				</div>
			   <div class="value-picker row form-group" id="1">
					<div class="col-md-3 pl-none">
					   <div class="form-group">
					      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[1].text" id="displayValPickText1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].text}" maxlength="15">
					      <div class="help-block with-errors red-txt"></div>
					   </div>
					</div>
					<div class="col-md-4 pl-none">
					   <div class="form-group">
					      <input type="text" class="form-control ValuePickerRequired" name="questionResponseSubTypeList[1].value" id="displayValPickValue1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].value}" maxlength="50">
					      <div class="help-block with-errors red-txt"></div>
					   </div>
					</div>
					<div class="col-md-2 pl-none mt-md">
					<span class="addBtnDis addbtn mr-sm align-span-center" onclick='addValuePicker();'>+</span>
			        <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeValuePicker(this);'></span>
					</div>
			   </div> 
			  </c:otherwise>
			</c:choose>
          	</div>
          </div>
         <div>
         </div>
         </div>
         <div id="TextScale" style="display: none;">
         	<div class="mt-lg">
              <div class="gray-xs-f mb-xs">Scale Type <span class="requiredStar">*</span></div>
              <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" class="TextScaleRequired" id="textScaleVertical" value="true" name="questionReponseTypeBo.vertical"  ${questionnairesStepsBo.questionReponseTypeBo.vertical ? 'checked':''} >
                  <label for="textScaleVertical">Vertical</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" class="TextScaleRequired" id="textScaleHorizontal" value="false" name="questionReponseTypeBo.vertical" ${empty questionnairesStepsBo.questionReponseTypeBo.vertical || !questionnairesStepsBo.questionReponseTypeBo.vertical ? 'checked':''} >
                  <label for="textScaleHorizontal">Horizontal</label>
                  </span>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
				   <div class="col-md-3 pl-none">
				      <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
				   </div>
				   <div class="col-md-4 pl-none">
				      <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
				   </div>
				   <c:if test="${questionnaireBo.branching}">
				   <div class="col-md-2 pl-none">
				      <div class="gray-xs-f mb-xs">Destination Step  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
				   </div>
				   </c:if>
				</div>
			<div class="TextScaleContainer">
				<c:choose>
					<c:when test="${questionnairesStepsBo.questionsBo.responseType eq 3 && fn:length(questionnairesStepsBo.questionResponseSubTypeList) gt 1}">
						<c:forEach items="${questionnairesStepsBo.questionResponseSubTypeList}" var="questionResponseSubType" varStatus="subtype">
							<div class="text-scale row" id="${subtype.index}">
							<input type="hidden" class="form-control" id="textScaleSubTypeValueId${subtype.index}" name="questionResponseSubTypeList[${subtype.index}].responseSubTypeValueId" value="${questionResponseSubType.responseSubTypeValueId}">
							   <div class="col-md-3 pl-none">
							      <div class="form-group">
							         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[${subtype.index}].text" id="displayTextSclText${subtype.index}" value="${questionResponseSubType.text}" maxlength="15">
							         <div class="help-block with-errors red-txt"></div>
							      </div>
							   </div>
							   <div class="col-md-4 pl-none">
							      <div class="form-group">
							         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[${subtype.index}].value" id="displayTextSclValue${subtype.index}" value="${questionResponseSubType.value}" maxlength="50">
							         <div class="help-block with-errors red-txt"></div>
							      </div>
							   </div>
							   <c:if test="${questionnaireBo.branching}">
							   <div class="col-md-3 pl-none">
							      <div class="form-group">
							         <select name="questionResponseSubTypeList[${subtype.index}].destinationStepId" id="destinationTextSclStepId${subtype.index}" title="select" data-error="Please choose one title" class="selectpicker TextScaleRequired" >
								         <c:forEach items="${destinationStepList}" var="destinationStep">
								         	<option value="${destinationStep.stepId}" ${questionResponseSubType.destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
								         </c:forEach> 
								         <option value="0" ${questionResponseSubType.destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
								     </select>
							         <div class="help-block with-errors red-txt"></div>
							      </div>
							   </div>
							   </c:if>
							   <div class="col-md-2 pl-none mt-md">
								<span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTextScale();'>+</span>
						        <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTextScale(this);'></span>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="text-scale row" id="0">
						   <div class="col-md-3 pl-none">
						      <div class="form-group">
						         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[0].text" id="displayTextSclText0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].text}" maxlength="15">
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   <div class="col-md-4 pl-none">
						      <div class="form-group">
						         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[0].value" id="displayTextSclValue0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].value}" maxlength="50">
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   <c:if test="${questionnaireBo.branching}">
						   <div class="col-md-3 pl-none">
						      <div class="form-group">
						         <select name="questionResponseSubTypeList[0].destinationStepId" id="destinationTextSclStepId0" title="select" data-error="Please choose one title" class="selectpicker" >
							         <c:forEach items="${destinationStepList}" var="destinationStep">
							         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
							         </c:forEach> 
							         <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
							     </select>
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   </c:if>
						   <div class="col-md-2 pl-none mt-md">
							<span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTextScale();'>+</span>
					        <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTextScale(this);'></span>
							</div>
						</div>
		            	<div class="text-scale row" id="1">
						   <div class="col-md-3 pl-none">
						      <div class="form-group">
						         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[1].text" id="displayTextSclText1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].text}" maxlength="15">
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   <div class="col-md-4 pl-none">
						      <div class="form-group">
						         <input type="text" class="form-control TextScaleRequired" name="questionResponseSubTypeList[1].value" id="displayTextSclValue1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].value}" maxlength="50">
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   <c:if test="${questionnaireBo.branching}">
						   <div class="col-md-3 pl-none">
						      <div class="form-group">
						         <select name="questionResponseSubTypeList[1].destinationStepId" id="destinationTextSclStepId1" title="select" data-error="Please choose one title" class="selectpicker" >
							        <c:forEach items="${destinationStepList}" var="destinationStep">
							         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
							         </c:forEach> 
							         <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
							     </select>
						         <div class="help-block with-errors red-txt"></div>
						      </div>
						   </div>
						   </c:if>
						   <div class="col-md-2 pl-none mt-md">
							<span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTextScale();'>+</span>
					        <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTextScale(this);'></span>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
            </div>
            <div class="clearfix"></div>
            <div class="row mt-sm">
                <div class="col-md-6 pl-none">
                   <div class="col-md-8 col-lg-8 p-none">
                        <div class="gray-xs-f mb-xs">Default slider position  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer number to indicate the desired default slider position. For example, if you have 6 choices, 5 will indicate the 5th choice."></span></div>
                        <div class="form-group">
                           <input type="text" class="form-control" id="textScalePositionId"  value="${questionnairesStepsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
                        </div>
                        </div>
                   </div>                          
               </div>           
         </div> 
         <div id="TextChoice" style="display: none;">
          <div class="mt-lg">
              <div class="gray-xs-f mb-xs">Selection Style <span class="requiredStar">*</span></div>
              <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" class="TextChoiceRequired" id="singleSelect" value="Single" name="questionReponseTypeBo.selectionStyle"  ${empty questionnairesStepsBo.questionReponseTypeBo.selectionStyle || questionnairesStepsBo.questionReponseTypeBo.selectionStyle eq 'Single' ? 'checked':''} >
                  <label for="singleSelect">Single Select</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" class="TextChoiceRequired" id="multipleSelect" value="Multiple" name="questionReponseTypeBo.selectionStyle" ${questionnairesStepsBo.questionReponseTypeBo.selectionStyle eq 'Multiple' ? 'checked':''} >
                  <label for="multipleSelect">Multiple Select</label>
                  </span>
                  <div class="help-block with-errors red-txt"></div>
               </div>
          </div>
         <div class="clearfix"></div>
		 <div class="row">
		   <div class="col-md-2 pl-none">
		      <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
		   </div>
		   <div class="col-md-4 pl-none">
		      <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
		   </div>
		   <div class="col-md-2 pl-none">
		      <div class="gray-xs-f mb-xs">Mark as exclusive ? <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
		   </div>
		   <c:if test="${questionnaireBo.branching}">
		      <div class="col-md-2 pl-none">
		         <div class="gray-xs-f mb-xs">Destination Step  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter text choices in the order you want them to appear on the slider. You can enter a text that will be displayed for each slider position, and an associated  value to be captured if that position is selected by the user.  You can also select a destination step for each choice, if you have branching enabled for the questionnaire. "></span></div>
		      </div>
		   </c:if>
		 </div>
         <div class="TextChoiceContainer">
         	<div class="col-md-12 p-none text-choice row" id="1">
			   <div class="col-md-2 pl-none">
			      <div class="form-group">
			         <input type="text" class="form-control TextChoiceRequired" name="questionResponseSubTypeList[0].text" id="displayTextSclText0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].text}" maxlength="15">
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <div class="col-md-4 pl-none">
			      <div class="form-group">
			         <input type="text" class="form-control TextChoiceRequired" name="questionResponseSubTypeList[0].value" id="displayTextSclValue0" value="${questionnairesStepsBo.questionResponseSubTypeList[0].value}" maxlength="50">
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <div class="col-md-2 pl-none">
			      <div class="form-group">
			          <select name="questionResponseSubTypeList[0].exclusive" id="destinationTextSclStepId0" title="select" data-error="Please choose one title" class="selectpicker TextChoiceRequired" >
			              <option value="Yes" ${questionnairesStepsBo.questionResponseSubTypeList[0].exclusive eq 'Yes' ? 'selected' :''}>Yes</option>
			              <option value="No" ${questionnairesStepsBo.questionResponseSubTypeList[0].exclusive eq 'No' ? 'selected' :''}>No</option>
			          </select>
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <c:if test="${questionnaireBo.branching}">
			      <div class="col-md-2 pl-none">
			         <div class="form-group">
			            <select name="questionResponseSubTypeList[0].destinationStepId" id="destinationTextSclStepId0" title="select" data-error="Please choose one title" class="selectpicker" >
			               <c:forEach items="${destinationStepList}" var="destinationStep">
			                  <option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[0].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
			               </c:forEach>
			               <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
			            </select>
			            <div class="help-block with-errors red-txt"></div>
			         </div>
			      </div>
			   </c:if>
			   <div class="col-md-2 pl-none mt-md">
			      <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTextScale();'>+</span>
			      <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTextScale(this);'></span>
			   </div>
			</div>
			<div class="col-md-12 p-none text-choice row" id="1">
			   <div class="col-md-2 pl-none">
			      <div class="form-group">
			         <input type="text" class="form-control TextChoiceRequired" name="questionResponseSubTypeList[1].text" id="displayTextSclText1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].text}" maxlength="15">
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <div class="col-md-4 pl-none">
			      <div class="form-group">
			         <input type="text" class="form-control TextChoiceRequired" name="questionResponseSubTypeList[1].value" id="displayTextSclValue1" value="${questionnairesStepsBo.questionResponseSubTypeList[1].value}" maxlength="50">
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <div class="col-md-2 pl-none">
			      <div class="form-group">
			          <select name="questionResponseSubTypeList[1].exclusive" id="destinationTextSclStepId1" title="select" data-error="Please choose one title" class="selectpicker TextChoiceRequired" >
			              <option value="Yes" ${questionnairesStepsBo.questionResponseSubTypeList[1].exclusive eq 'Yes' ? 'selected' :''}>Yes</option>
			              <option value="No" ${!questionnairesStepsBo.questionResponseSubTypeList[1].exclusive eq 'No' ? 'selected' :''}>No</option>
			          </select>
			         <div class="help-block with-errors red-txt"></div>
			      </div>
			   </div>
			   <c:if test="${questionnaireBo.branching}">
			      <div class="col-md-2 pl-none">
			         <div class="form-group">
			            <select name="questionResponseSubTypeList[1].destinationStepId" id="destinationTextSclStepId1" title="select" data-error="Please choose one title" class="selectpicker" >
			               <c:forEach items="${destinationStepList}" var="destinationStep">
			                  <option value="${destinationStep.stepId}" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
			               </c:forEach>
			               <option value="0" ${questionnairesStepsBo.questionResponseSubTypeList[1].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
			            </select>
			            <div class="help-block with-errors red-txt"></div>
			         </div>
			      </div>
			   </c:if>
			   <div class="col-md-2 pl-none mt-md">
			      <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTextScale();'>+</span>
			      <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTextScale(this);'></span>
			   </div>
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
	<c:if test="${actionTypeForQuestionPage == 'view'}">
		$('#questionStepId input,textarea ').prop('disabled', true);
		$('#questionStepId select').addClass('linkDis');
		$('.addBtnDis, .remBtnDis').addClass('dis-none');
	</c:if>
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	    var a = $(".col-lc").height();
	    var b = $(".col-rc").height();
	    if(a > b){
	        $(".col-rc").css("height", a);	
	    }else{
	        $(".col-rc").css("height", "auto");
	    }
	});
	if($('.value-picker').length > 1){
		$('.ValuePickerContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.ValuePickerContainer').find(".remBtnDis").addClass("hide");
	}
	if($('.text-scale').length > 1){
		$('.TextScaleContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.TextScaleContainer').find(".remBtnDis").addClass("hide");
	}
	if($('.text-choice').length > 1){
		$('.TextChoiceContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.TextChoiceContainer').find(".remBtnDis").addClass("hide");
	}
	$(".menuNav li.active").removeClass('active');
	$(".sixthQuestionnaires").addClass('active');
     $("#doneId").click(function(){
    	 if(isFromValid("#questionStepId")){
    		  var resType = $("#rlaResonseType").val();
    		  var placeholderText ='';
    		  var stepText = "";
    		  if(resType == "Email"){
    				 placeholderText = $("#placeholderId").val();	
    		  }else if(resType == "Text"){
    				placeholderText = $("#textPlaceholderId").val(); 
    		  }else if(resType == "Height"){
    				placeholderText = $("#heightPlaceholderId").val();
    		  }else if(resType == "Numeric"){
    				placeholderText = $("#numericPlaceholderId").val(); 
    		  }else if(resType == "Time interval"){
    			  stepText = $("#timeIntervalStepId").val();
    		  }else if(resType == "Scale" || resType == "Continuous Scale"){
    			  stepText =  $("#scaleStepId").val();
    		  }else if(resType == 'Text Scale'){
    			  stepText =  $("#textScalePositionId").val();
    		  }
    		 $("#placeholderTextId").val(placeholderText);
    		 $("#stepValueId").val(stepText);
		     if(resType != '' && resType != null && resType != 'undefined'){
		    	 $("#responseTypeId > option").each(function() {
		    		 var textVal = this.text.replace(/\s/g, '');
		    		 console.log("textVal:"+textVal);
	    			 if(resType.replace(/\s/g, '') == textVal){
	    			 }else{
	    				 $("#"+textVal).empty();
	    			 }    
	    		 });
    		 }
    		 document.questionStepId.submit();
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
    $('.DateRequired').on("change",function(){
    	var value= $(this).val();
    	setResponseDate(value);
    	
    });
    $("#minDateId").on('dp.change',function(){
    	var minDate = $("#minDateId").val();
        var maxDate = $('#maxDateId').val();
        if(minDate!='' && maxDate!='' && toJSDate(minDate) > toJSDate(maxDate)){
        	$('#minDateId').parent().addClass("has-danger").addClass("has-error");
       	    $('#minDateId').parent().find(".help-block").html('<ul class="list-unstyled"><li>Max Date and Time Should not be less than Min Date and Time</li></ul>');
       	    $('#minDateId').val('');
        }else{
        	$('#minDateId').parent().removeClass("has-danger").removeClass("has-error");
            $('#minDateId').parent().find(".help-block").html("");
            $("#maxDateId").parent().removeClass("has-danger").removeClass("has-error");
            $("#maxDateId").parent().find(".help-block").html("");
        }
    	
    });
    $("#maxDateId").on('dp.change',function(){
    	var minDate = $("#minDateId").val();
        var maxDate = $('#maxDateId').val();
        console.log("minDate:"+minDate);
        console.log("maxDate:"+maxDate);
        if(minDate!='' && maxDate!='' && toJSDate(minDate) > toJSDate(maxDate)){
        	$('#maxDateId').parent().addClass("has-danger").addClass("has-error");
       	    $('#maxDateId').parent().find(".help-block").html('<ul class="list-unstyled"><li>Max Date and Time Should not be less than Min Date and Time</li></ul>');
       	    $('#maxDateId').val('');
       	    console.log("ifffffffff");
        }else{
        	$('#maxDateId').parent().removeClass("has-danger").removeClass("has-error");
            $('#maxDateId').parent().find(".help-block").html("");
            $("#minDateId").parent().removeClass("has-danger").removeClass("has-error");
            $("#minDateId").parent().find(".help-block").html("");
        }
    });
    $("#defaultDate").on('dp.change',function(){
    	var minDate = $("#minDateId").val();
        var maxDate = $('#maxDateId').val();
        var defaultDate = $("#defaultDate").val();
        if(minDate!='' && maxDate!='' && defaultDate != ''){
        	if(toJSDate(defaultDate) >= toJSDate(minDate) && toJSDate(defaultDate) <= toJSDate(maxDate)){
        		$('#defaultDate').parent().removeClass("has-danger").removeClass("has-error");
                $('#defaultDate').parent().find(".help-block").html("");
        	}else{
        		$('#defaultDate').parent().addClass("has-danger").addClass("has-error");
           	    $('#defaultDate').parent().find(".help-block").html('<ul class="list-unstyled"><li>Enter default date to be shown as selected as per availability of Min and Max</li></ul>');
           	    $('#defaultDate').val('');
        	}
        }
    });
    $('[data-toggle="tooltip"]').tooltip();
});
function toJSDate( dateTime ) {
	if(dateTime != null && dateTime !='' && typeof dateTime != 'undefined'){
		var date = dateTime.split("/");
		return new Date(date[2], (date[0]-1), date[1]);
	}
}
function setResponseDate(type){
	console.log("type:"+type);
	if(type == 'Date-Time'){
		
		$("#minDateId").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY HH:mm:ss').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	    $("#maxDateId").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY HH:mm:ss').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	    $("#defaultDate").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY HH:mm:ss').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	    
	}else{
		
		$("#minDateId").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	    $("#maxDateId").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	    $("#defaultDate").datetimepicker().data('DateTimePicker').format('MM/DD/YYYY').minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
	   
	}
}
function getResponseType(id){
	if(id != null && id !='' && typeof id != 'undefined'){
		var previousResponseType = '${questionnairesStepsBo.questionsBo.responseType}';
		if(Number(id) != Number(previousResponseType)){
			 var responseType = $("#responseTypeId>option:selected").html();
			 if(responseType != 'Continuous Scale' && responseType != 'Scale' && responseType != 'Boolean'){
				// $("#"+responseType.replace(/\s/g, '')).find('input:text').val(''); 
				 $("#"+responseType.replace(/\s/g, '')).find('input:text').val(''); 
				 if(responseType == "Date"){
					 $("#"+responseType.replace(/\s/g, '')).find('input:text').data("DateTimePicker").clear();					 
				 }
			 }
			 if(responseType == 'Text Scale' && responseType == 'Text Choice' && responseType == 'Boolean'){
			 	var container = document.getElementById(responseType.replace(/\s/g, ''));
			    var children = container.getElementsByTagName('select');
			    console.log("children.length:"+children.length);
			    for (var i = 0; i < children.length; i++) {
			        children[i].selectedIndex = 0;
			    }
			 }
		 }
		
		<c:forEach items="${questionResponseTypeMasterInfoList}" var="questionResponseTypeMasterInfo">
		 var infoId = Number('${questionResponseTypeMasterInfo.id}'); 
		 var responseType = '${questionResponseTypeMasterInfo.responseType}';
		 var type='';
		 if(responseType == 'Continuous Scale'){
			 type = 'Scale';
		 }else{
			 type = responseType;
			 $("#"+type.replace(/\s/g, '')).hide();
		 }
		 if(responseType == 'Date'){
			 var style = '${questionnairesStepsBo.questionReponseTypeBo.style}';
			 console.log("style:"+style);
			 setResponseDate(style);
		 }
		 $("."+type.replace(/\s/g, '')+"Required").attr("required",false);
		 if(id == infoId){
    		var description = '${questionResponseTypeMasterInfo.description}';
    		var dataType = "${questionResponseTypeMasterInfo.dataType}";
    		var dashboard = '${questionResponseTypeMasterInfo.dashBoardAllowed}';
    		$("#responseTypeDataType").text(dataType);
    		$("#responseTypeDescrption").text(description);
    		$("#rlaResonseType").val(responseType)
    		$("#rlaResonseDataType").text(dataType);
    		$("#rlaResonseTypeDescription").text(description);
    		if(dashboard == 'true'){
    			$("#useStasticDataContainerId").show();
        		$("#addLineChartContainerId").show();	
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
    		if(responseType == 'Date'){
   			 	$("#useAnchorDateContainerId").show();
	   		}else{
	   			$("#useAnchorDateContainerId").hide();
	   		}
    		console.log(type.replace(/\s/g, ''));
    		$("#"+type.replace(/\s/g, '')).show();
    		$("."+type.replace(/\s/g, '')+"Required").attr("required",true);
    	 }else{
    		 
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
	var anchor_date = $('input[name="questionsBo.useAnchorDate"]:checked').val();
	
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
	questionsBo.useAnchorDate=anchor_date;
	questionnaireStep.questionsBo=questionsBo;
	
	var questionReponseTypeBo = new  Object();
	
	var minValue='';
	var maxValue='';
	var defaultValue='';
	var maxdescription='';
	var mindescrption='';
	var step='';
	var resType = $("#rlaResonseType").val();
	if(resType == "Scale" || resType == "Continuous Scale"){
		minValue = $("#scaleMinValueId").val();
		maxValue = $("#scaleMaxValueId").val();
		defaultValue = $("#scaleDefaultValueId").val();
		mindescrption = $("#scaleMinDescriptionId").val();
		maxdescription = $("#scaleMaxDescriptionId").val();
		step = $("#scaleStepId").val();
		
		questionReponseTypeBo.minValue=minValue;
		questionReponseTypeBo.maxValue=maxValue;
		questionReponseTypeBo.defaultValue=defaultValue;
		questionReponseTypeBo.minDescription=mindescrption;
		questionReponseTypeBo.maxDescription=maxdescription;
		questionReponseTypeBo.step=step;
		
	}else if(resType == "Location"){
		var usecurrentlocation = $('input[name="questionReponseTypeBo.useCurrentLocation"]:checked').val();	
		questionReponseTypeBo.useCurrentLocation=usecurrentlocation;
	}else if(resType == "Email"){
		var placeholderText = $("#placeholderId").val();	
		questionReponseTypeBo.placeholder=placeholderText;
	}else if(resType == "Text"){
		var max_length = $("#textmaxLengthId").val();
		var placeholderText = $("#textPlaceholderId").val(); 
	    var multiple_lines = $('input[name="questionReponseTypeBo.multipleLines"]:checked').val();	
			
	    questionReponseTypeBo.maxLength = max_length;
	    questionReponseTypeBo.placeholder = placeholderText;
	    questionReponseTypeBo.multipleLines = multiple_lines;
	}else if(resType == "Height"){
		var measurement_system = $('input[name="questionReponseTypeBo.measurementSystem"]:checked').val();
		var placeholder_text = $("#heightPlaceholderId").val();
		questionReponseTypeBo.measurementSystem = measurement_system;
		questionReponseTypeBo.placeholder = placeholder_text;
	}else if(resType == "Time interval"){
		 var stepValue = $("#timeIntervalStepId").val();
		 questionReponseTypeBo.step=stepValue;
	}else if(resType == "Numeric"){
		var styletext = $('input[name="questionReponseTypeBo.style"]:checked').val();
		var unitText = $("#numericUnitId").val();
		var palceholder_text = $("#numericPlaceholderId").val(); 
		questionReponseTypeBo.style = styletext;
		questionReponseTypeBo.placeholder = palceholder_text;
		questionReponseTypeBo.unit=unitText;
	}else if(resType == "Date"){
		var min_date = $("#minDateId").val(); 
		var max_date = $("#maxDateId").val(); 
		var default_date = $("#defaultDate").val(); 
		var style=$('input[name="questionReponseTypeBo.style"]:checked').val();
		questionReponseTypeBo.minDate = min_date;
		questionReponseTypeBo.maxDate = max_date;
		questionReponseTypeBo.defaultDate = default_date;
		questionReponseTypeBo.style=style;
	}else if(resType == "Boolean"){
		var questionSubResponseArray  = new Array();
		$('#Boolean .row').each(function(){
			var questionSubResponseType = new Object();
			var id = $(this).attr("id");
			var response_sub_type_id = $("#responseSubTypeValueId"+id).val();
			var diasplay_text = $("#dispalyText"+id).val();
			var diaplay_value = $("#displayValue"+id).val();
			var destination_step = $("#destinationStepId"+id).val();
			
			questionSubResponseType.responseSubTypeValueId=response_sub_type_id;
			questionSubResponseType.text=diasplay_text;
			questionSubResponseType.value=diaplay_value;
			questionSubResponseType.destinationStepId=destination_step;
			
			questionSubResponseArray.push(questionSubResponseType);
		});
		questionnaireStep.questionResponseSubTypeList = questionSubResponseArray;
		
		
	}else if(resType == "Value Picker"){
		var questionSubResponseArray  = new Array();
		$('.value-picker').each(function(){
			var questionSubResponseType = new Object();
			var id = $(this).attr("id");
			var response_sub_type_id = $("#valPickSubTypeValueId"+id).val();
			var diasplay_text = $("#displayValPickText"+id).val();
			var diaplay_value = $("#displayValPickValue"+id).val();
			
			questionSubResponseType.responseSubTypeValueId=response_sub_type_id;
			questionSubResponseType.text=diasplay_text;
			questionSubResponseType.value=diaplay_value;
			
			
			questionSubResponseArray.push(questionSubResponseType);
		});
		questionnaireStep.questionResponseSubTypeList = questionSubResponseArray;
	} else if(resType == "Text Scale"){
		var questionSubResponseArray  = new Array();
		$('.text-scale').each(function(){
			var questionSubResponseType = new Object();
			var id = $(this).attr("id");
			console.log("id:"+id);
			var response_sub_type_id = $("#textScaleSubTypeValueId"+id).val();
			var diasplay_text = $("#displayTextSclText"+id).val();
			var diaplay_value = $("#displayTextSclValue"+id).val();
			var destination_step = $("#destinationTextSclStepId"+id).val();
			console.log("diasplay_text:"+diasplay_text);
			console.log("diaplay_value:"+diaplay_value);
			console.log("destination_step:"+destination_step);
			questionSubResponseType.responseSubTypeValueId=response_sub_type_id;
			questionSubResponseType.text=diasplay_text;
			questionSubResponseType.value=diaplay_value;
			questionSubResponseType.destinationStepId=destination_step;
			questionSubResponseArray.push(questionSubResponseType);
			
		});
		questionnaireStep.questionResponseSubTypeList = questionSubResponseArray;
	}
	
	
	var response_type_id = $("#questionResponseTypeId").val();
	var question_response_type_id = $("#responseQuestionId").val();
	
	questionReponseTypeBo.responseTypeId=response_type_id;
	questionReponseTypeBo.questionsResponseTypeId=question_response_type_id;
	
	
	
	
	
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
					
					var stepId = jsonobject.stepId;
					var questionId = jsonobject.questionId;
					var questionResponseId = jsonobject.questionResponseId;
					var questionsResponseTypeId = jsonobject.questionsResponseTypeId;
					
					console.log("stepId:"+stepId);
					console.log("questionId:"+questionId);
					console.log("questionResponseId:"+questionResponseId);
					console.log("questionsResponseTypeId:"+questionsResponseTypeId);
					
					$("#stepId").val(stepId);
					$("#questionId").val(questionId);
					$("#questionResponseTypeId").val(questionResponseId);
					$("#responseQuestionId").val(questionId);
					
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
	$(item).prop('disabled', true);
	<c:if test="${actionTypeForQuestionPage ne 'view'}">
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
	</c:if>
	<c:if test="${actionTypeForQuestionPage eq 'view'}">
		var a = document.createElement('a');
		a.href = "/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do";
		document.body.appendChild(a).click();
	</c:if>
}
var count = $('.value-picker').length;
function addValuePicker(){
	count = count+1;
	var newValuePicker ="<div class='value-picker row form-group' id="+count+">"+
						"	<div class='col-md-3 pl-none'>"+
						"   <div class='form-group'>"+
						"      <input type='text' class='form-control' name='questionResponseSubTypeList["+count+"].text' id='displayValPickText"+count+"' required maxlength='15'>"+
						"      <div class='help-block with-errors red-txt'></div>"+
						"   </div>"+
						"</div>"+
						"<div class='col-md-4 pl-none'>"+
						"   <div class='form-group'>"+
						"      <input type='text' class='form-control' name='questionResponseSubTypeList["+count+"].value' id='displayValPickValue"+count+"' required maxlength='50'>"+
						"      <div class='help-block with-errors red-txt'></div>"+
						"   </div>"+
						"</div>"+
						"<div class='col-md-2 pl-none mt-md'>"+
						"   <span class='addBtnDis addbtn mr-sm align-span-center' onclick='addValuePicker();'>+</span>"+
					    "<span class='delete vertical-align-middle remBtnDis hide pl-md align-span-center' onclick='removeValuePicker(this);'></span>"+
						"</div>"+
					"</div>";	
	$(".value-picker:last").after(newValuePicker);
	$(".value-picker").parents("form").validator("destroy");
    $(".value-picker").parents("form").validator();
	if($('.value-picker').length > 1){
		$(".remBtnDis").removeClass("hide");
	}else{
		$(".remBtnDis").addClass("hide");
	}
}
function removeValuePicker(param){
	if($('.value-picker').length > 2){
		
		$(param).parents(".value-picker").remove();
	    $(".value-picker").parents("form").validator("destroy");
		$(".value-picker").parents("form").validator();
		if($('.value-picker').length > 1){
			$(".remBtnDis").removeClass("hide");
		}else{
			$(".remBtnDis").addClass("hide");
		}
	}
}
var scaleCount = $('.text-scale').length;
function addTextScale(){
	scaleCount = scaleCount+1;
	var newTextScale = "<div class='text-scale row' id="+scaleCount+">"+
					    "	<div class='col-md-3 pl-none'>"+
					    "    <div class='form-group'>"+
				        "      <input type='text' class='form-control TextScaleRequired' name='questionResponseSubTypeList["+scaleCount+"].text' id='displayTextSclText"+scaleCount+"'+  maxlength='15' required>"+
					    "      <div class='help-block with-errors red-txt'></div>"+
					    "   </div>"+
						"</div>"+
						" <div class='col-md-4 pl-none'>"+
						"    <div class='form-group'>"+
						"       <input type='text' class='form-control TextScaleRequired' class='form-control' name='questionResponseSubTypeList["+scaleCount+"].value' id='displayTextSclValue"+scaleCount+"' maxlength='50' required>"+
						"       <div class='help-block with-errors red-txt'></div>"+
						"    </div>"+
						" </div>"+
						" <div class='col-md-3 pl-none'>"+
						"    <div class='form-group'>"+
						"       <select class='selectpicker' name='questionResponseSubTypeList["+scaleCount+"].destinationStepId' id='destinationTextSclStepId"+scaleCount+"' title='select' data-error='Please choose one title'>";
						<c:forEach items="${destinationStepList}" var="destinationStep">
						newTextScale+="<option value='${destinationStep.stepId}'>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>";
			        	 </c:forEach> 
			        	newTextScale+="	<option value='0'>Completion Step</option>"+
						"	     </select>"+
						"      <div class='help-block with-errors red-txt'></div>"+
						"   </div>"+
						"</div>"+
						"<div class='col-md-2 pl-none mt-md'>"+
						"	<span class='addBtnDis addbtn mr-sm align-span-center' onclick='addTextScale();'>+</span>"+
						"  <span class='delete vertical-align-middle remBtnDis hide pl-md align-span-center' onclick='removeTextScale(this);'></span>"+
						"	</div>"+
						"</div>";
	$(".text-scale:last").after(newTextScale);
	$('.selectpicker').selectpicker('refresh');
	$(".text-scale").parents("form").validator("destroy");
    $(".text-scale").parents("form").validator();
	if($('.text-scale').length > 1){
		$(".remBtnDis").removeClass("hide");
	}else{
		$(".remBtnDis").addClass("hide");
	}
}
function removeTextScale(param){
	if($('.text-scale').length > 2){
		$(param).parents(".text-scale").remove();
	    $(".text-scale").parents("form").validator("destroy");
		$(".text-scale").parents("form").validator();
		if($('.text-scale').length > 1){
			$(".remBtnDis").removeClass("hide");
		}else{
			$(".remBtnDis").addClass("hide");
		}
	}
}
</script>