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
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateFromQuestion.do" name="questionStepId" id="questionStepId" method="post" data-toggle="validator" role="form">
   <div class="right-content-body pt-none pl-none pr-none">
      <ul class="nav nav-tabs review-tabs gray-bg">
         <li class="questionLevel active"><a data-toggle="tab" href="#qla">Question-level Attributes</a></li>
         <li class="responseLevel"><a data-toggle="tab" href="#rla">Response-level Attributes</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <!-- Step-level Attributes--> 
         <input type="hidden" name="stepType" id="stepType" value="Question">
         <input type="hidden" id="type" name="type" value="complete" />
         <input type="hidden" name="id" id="questionId" value="${questionsBo.id}">
         <input type="hidden" id="fromId" name="fromId" value="${formId}" />
         <!---  Form-level Attributes ---> 
         <div id="qla" class="tab-pane fade active in mt-xlg">
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Text of the question <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control" name="question" id="questionTextId" placeholder="Type the question you wish to ask the participant" value="${questionsBo.question}" required maxlength="250"/>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description of the question</div>
               <div class="form-group">
                  <textarea class="form-control" rows="4" name="description" id="descriptionId" placeholder="Enter a line that describes your question, if needed" maxlength="500">${questionsBo.description}</textarea>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="mt-md">
               <div class="gray-xs-f">Response Type <span class="requiredStar">*</span></div>
               <div class="gray-xs-f mb-xs"><small>The type of interface needed to capture the response</small></div>
               <div class="clearfix"></div>
               <div class="col-md-4 col-lg-3 p-none">
                  <div class="form-group">
                     <select id="responseTypeId" class="selectpicker" name="responseType" required value="${questionsBo.responseType}">
                      <option value=''>Select</option>
                      <c:forEach items="${questionResponseTypeMasterInfoList}" var="questionResponseTypeMasterInfo">
                      	<option value="${questionResponseTypeMasterInfo.id}" ${questionsBo.responseType eq questionResponseTypeMasterInfo.id ? 'selected' : ''}>${questionResponseTypeMasterInfo.responseType}</option>
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
                     - NA -
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Data Type</div>
                  <div id="responseTypeDataType">- NA - </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="mt-lg mb-lg" id="addLineChartContainerId" style="display: none">
               <span class="checkbox checkbox-inline">
               <input type="checkbox" id="addLineChart" name="addLineChart" value="Yes" ${questionsBo.addLineChart eq 'Yes' ? 'checked':''}>
               <label for="addLineChart"> Add response data to line chart on app dashboard </label>
               </span>
            </div>
            <div class="clearfix"></div>
            <div id="chartContainer" style="display: none">
            <div class="col-md-6 p-none">
               <div class="gray-xs-f mb-xs">Time range for the chart <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass chartrequireClass" id="lineChartTimeRangeId" name="lineChartTimeRange" value="${questionsBo.lineChartTimeRange}">
                       <option value="" selected disabled>Select</option>
	                   <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                        <option value="${timeRangeAttr}" ${questionsBo.lineChartTimeRange eq timeRangeAttr ? 'selected':''}>${timeRangeAttr}</option>
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
                  <input type="radio" id="allowRollbackChartYes" value="Yes" name="allowRollbackChart" ${questionsBo.allowRollbackChart eq 'Yes' ? 'checked': ''}>
                  <label for="allowRollbackChartYes">Yes</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" id="allowRollbackChartNo" value="No" name="allowRollbackChart" ${questionsBo.allowRollbackChart eq 'No' ? 'checked': ''}>
                  <label for="allowRollbackChartNo">No</label>
                  </span>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Title for the chart <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control chartrequireClass" name="chartTitle" id="chartTitleId" value="${questionsBo.chartTitle}">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            </div>
            <div class="clearfix"></div>
            <div class="bor-dashed mt-sm mb-md"></div>
            <div class="clearfix"></div>
            <div class="mb-lg" id="useStasticDataContainerId" style="display: none">
               <span class="checkbox checkbox-inline">
               <input type="checkbox" id= "useStasticData" value="Yes" name="useStasticData" ${questionsBo.useStasticData eq 'Yes' ? 'checked':''}>
               <label for="useStasticData"> Use response data for statistic on dashboard</label>
               </span>
            </div>
            <div class="clearfix"></div>
            <div id="statContainer" style="display: none">
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Short identifier name <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="statShortName" id="statShortNameId" value="${questionsBo.statShortName}">
               	  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months) <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="statDisplayName" id="statDisplayNameId" value="${questionsBo.statDisplayName}">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Display Units (e.g. hours) <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <input type="text" class="form-control requireClass" name="statDisplayUnits" id="statDisplayUnitsId" value="${questionsBo.statDisplayUnits}" >
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-4 col-lg-3 p-none">
               <div class="gray-xs-f mb-xs">Stat Type for image upload <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass requireClass" id="statTypeId" title="Select" name="statType">
			         <option value="" selected disabled>Select</option>
			         <c:forEach items="${statisticImageList}" var="statisticImage">
			            <option value="${statisticImage.statisticImageId}" ${questionsBo.statType eq statisticImage.statisticImageId ? 'selected':''}>${statisticImage.value}</option>
			         </c:forEach>
			      </select> 
			      <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Formula for to be applied <span class="requiredStar">*</span></div>
               <div class="form-group">
                  <select class="selectpicker elaborateClass requireClass" id="statFormula" title="Select" name="statFormula">
			         <option value="" selected disabled>Select</option>
			         <c:forEach items="${activetaskFormulaList}" var="activetaskFormula">
			            <option value="${activetaskFormula.activetaskFormulaId}" ${questionsBo.statFormula eq activetaskFormula.activetaskFormulaId ? 'selected':''}>${activetaskFormula.value}</option>
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
            <input type="hidden" class="form-control" name="questionReponseTypeBo.responseTypeId" id="questionResponseTypeId" value="${questionsBo.questionReponseTypeBo.responseTypeId}">
            <input type="hidden" class="form-control" name="questionReponseTypeBo.questionsResponseTypeId" id="responseQuestionId" value="${questionsBo.questionReponseTypeBo.questionsResponseTypeId}">
            <input type="hidden" class="form-control" name="questionReponseTypeBo.placeholder" id="placeholderTextId" />
            <input type="hidden" class="form-control" name="questionReponseTypeBo.step" id="stepValueId" />
            <div id="Scale" style="display: none">
            <div class="mt-lg">
               <div class="gray-xs-f mb-xs">Scale Type <span class="requiredStar">*</span></div>
               <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" class="ScaleRequired" id="vertical" value="true" name="questionReponseTypeBo.vertical"  ${questionsBo.questionReponseTypeBo.vertical ? 'checked':''} >
                  <label for="vertical">Vertical</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" class="ScaleRequired" id="horizontal" value="false" name="questionReponseTypeBo.vertical" ${empty questionsBo.questionReponseTypeBo.vertical || !questionsBo.questionReponseTypeBo.vertical ? 'checked':''} >
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
                        <input type="text" class="form-control ScaleRequired"  name="questionReponseTypeBo.minValue" id="scaleMinValueId" value="${questionsBo.questionReponseTypeBo.minValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
               <div class="col-md-6 pl-none">
                  <div class="col-md-8 col-lg-8 p-none">
                     <div class="gray-xs-f mb-xs">Maximum Value <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
                     <div class="form-group">
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.maxValue" id="scaleMaxValueId" value="${questionsBo.questionReponseTypeBo.maxValue}" onkeypress="return isNumber(event)">
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
                        <input type="text" class="form-control ScaleRequired" name="questionReponseTypeBo.defaultValue" id="scaleDefaultValueId" value="${questionsBo.questionReponseTypeBo.defaultValue}" onkeypress="return isNumber(event)">
                        <div class="help-block with-errors red-txt"></div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for minimum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.minDescription" id="scaleMinDescriptionId" value="${questionsBo.questionReponseTypeBo.minDescription}" placeholder="Type the question you wish to ask the participant" />
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="col-md-10 p-none">
               <div class="gray-xs-f mb-xs">Description for maximum value</div>
               <div class="form-group">
                  <input type="text" class="form-control" name="questionReponseTypeBo.maxDescription" id="scaleMaxDescriptionId" value="${questionsBo.questionReponseTypeBo.maxDescription}" placeholder="Type the question you wish to ask the participant" />
               </div>
            </div>
            <div class="col-md-4 col-lg-4 p-none mb-lg">
               <div class="gray-xs-f mb-xs">Number of Steps  <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
               <div class="form-group">
                  <input type="text" class="form-control ScaleRequired"  id="scaleStepId" value="${questionsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            </div>
            <div id="Location" style="display: none">
            	<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Use Current Location <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The Tooltip plugin is small pop-up box that appears when the user moves."></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="LocationRequired" id="useCurrentLocationYes" value="true" name="questionReponseTypeBo.useCurrentLocation"  ${empty questionsBo.questionReponseTypeBo.useCurrentLocation || questionsBo.questionReponseTypeBo.useCurrentLocation ? 'checked':''} >
	                  <label for="useCurrentLocationYes">Yes</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="LocationRequired" id="useCurrentLocationNo" value="false" name="questionReponseTypeBo.useCurrentLocation" ${!questionsBo.questionReponseTypeBo.useCurrentLocation ? 'checked':''} >
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
	                        <input type="text" class="form-control" placeholder="1-40 characters"  id="placeholderId" value="${questionsBo.questionReponseTypeBo.placeholder}" maxlength="40">
	                     </div>
	                  </div>
	               </div>
	            </div>
            </div>
           <div id="Text" style="display: none">
           		<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Allow Multiple Lines? <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="TChoose Yes if you need the user to enter large text in a text area."></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="TextRequired" id="multipleLinesYes" value="true" name="questionReponseTypeBo.multipleLines"  ${questionsBo.questionReponseTypeBo.multipleLines ? 'checked':''} >
	                  <label for="multipleLinesYes">Yes</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="TextRequired" id="multipleLinesNo" value="false" name="questionReponseTypeBo.multipleLines" ${empty questionsBo.questionReponseTypeBo.multipleLines || !questionsBo.questionReponseTypeBo.multipleLines ? 'checked':''} >
	                  <label for="multipleLinesNo">No</label>
	                  </span>
	                  <div class="help-block with-errors red-txt"></div>
	               </div>
	            </div>
           		<div class="clearfix"></div>
	            <div class="row">
	               <div class="col-md-6 pl-none">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="TEnter an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  placeholder="1-50 characters"  id="textPlaceholderId" value="${questionsBo.questionReponseTypeBo.placeholder}" maxlength="50">
	                     </div>
	                  </div>
	               </div>
	               <div class="col-md-4">
	                  <div class="col-md-4 col-lg-4 p-none">
	                     <div class="gray-xs-f mb-xs">Max Length  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Enter an integer for the maximum length of text allowed. If left empty, there will be no max limit applied."></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control" name="questionReponseTypeBo.maxLength" id="textmaxLengthId" value="${questionsBo.questionReponseTypeBo.maxLength}" onkeypress="return isNumber(event)">
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
	                  <input type="radio" class="HeightRequired" id="measurementSystemLocal" value="Local" name="questionReponseTypeBo.measurementSystem"  ${questionsBo.questionReponseTypeBo.measurementSystem eq 'Local'? 'checked':''} >
	                  <label for="measurementSystemLocal">Local</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="HeightRequired" id="measurementSystemMetric" value="Metric" name="questionReponseTypeBo.measurementSystem" ${questionsBo.questionReponseTypeBo.measurementSystem eq 'Metric' ? 'checked':''} >
	                  <label for="measurementSystemMetric">Metric</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="HeightRequired" id="measurementSystemUS" value="US" name="questionReponseTypeBo.measurementSystem" ${empty questionsBo.questionReponseTypeBo.measurementSystem || questionsBo.questionReponseTypeBo.multipleLines eq 'US' ? 'checked':''} >
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
	                        <input type="text" class="form-control" placeholder="1-20 characters"  id="heightPlaceholderId" value="${questionsBo.questionReponseTypeBo.placeholder}" maxlength="20">
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
	                  <input type="text" class="form-control TimeintervalRequired"  id="timeIntervalStepId" value="${questionsBo.questionReponseTypeBo.step}" onkeypress="return isNumber(event)">
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
	                  <input type="radio" class="NumericRequired" id="styleDecimal" value="Decimal" name="questionReponseTypeBo.style"  ${empty questionsBo.questionReponseTypeBo.style || questionsBo.questionReponseTypeBo.style eq 'Decimal' ? 'checked':''} >
	                  <label for="styleDecimal">Decimal</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="NumericRequired" id="styleInteger" value="Integer" name="questionReponseTypeBo.style" ${questionsBo.questionReponseTypeBo.style eq 'Integer' ? 'checked':''} >
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
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.unit" id="numericUnitId" value="${questionsBo.questionReponseTypeBo.unit}" maxlength="15">
	                     </div>
	                  </div>
	               </div>
	               <div class="col-md-6">
	                  <div class="col-md-8 col-lg-8 p-none">
	                     <div class="gray-xs-f mb-xs">Placeholder Text(1 to 30 characters)  <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Provide an input hint to the user"></span></div>
	                     <div class="form-group">
	                        <input type="text" class="form-control"  id="numericPlaceholderId" value="${questionsBo.questionReponseTypeBo.placeholder}" maxlength="30">
	                     </div>
	                  </div>
	               </div>
	        </div>
          </div>
          <div id="Date" style="display: none;">
          	<div class="mt-lg">
	               <div class="gray-xs-f mb-xs">Style <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="Choose the kind of numeric input needed"></span></div>
	               <div>
	                  <span class="radio radio-info radio-inline p-45">
	                  <input type="radio" class="DateRequired" id="date" value="Date" name="questionReponseTypeBo.style"  ${empty questionsBo.questionReponseTypeBo.style || questionsBo.questionReponseTypeBo.style eq 'Date' ? 'checked':''} >
	                  <label for="date">Date</label>
	                  </span>
	                  <span class="radio radio-inline">
	                  <input type="radio" class="DateRequired" id="dateTime" value="Date-Time" name="questionReponseTypeBo.style" ${questionsBo.questionReponseTypeBo.style eq 'Date-Time' ? 'checked':''} >
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
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.minDate" id="minDateId" value="${questionsBo.questionReponseTypeBo.minDate}" >
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
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.maxDate"id="maxDateId" value="${questionsBo.questionReponseTypeBo.maxDate}" >
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
	                        <input type="text" class="form-control"  name="questionReponseTypeBo.defaultDate" id="defaultDate" value="${questionsBo.questionReponseTypeBo.defaultDate}">
	                        <div class="help-block with-errors red-txt"></div>
	                     </div>
	                  </div>
	               </div>
	        </div>
          </div>
          <div id="Boolean" style="display: none;">
          	<div class="clearfix"></div>
          	<div class="row mt-lg" id="0">
          		<input type="hidden" class="form-control" id="responseSubTypeValueId0" name="questionResponseSubTypeList[0].responseSubTypeValueId" value="${questionsBo.questionResponseSubTypeList[0].responseSubTypeValueId}">
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
				       <select name="questionResponseSubTypeList[0].destinationStepId" id="destinationStepId0" title="select" data-error="Please choose one title" class="selectpicker" required>
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionsBo.questionResponseSubTypeList[0].destinationStepId eq destinationStep.stepId ? 'selected' :''}>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${questionsBo.questionResponseSubTypeList[0].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
				       </select>
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
			   </c:if>
			</div>
			
			<div class="row" id="1">
	          	<div class="col-md-3 pl-none" >
	          	<input type="hidden" class="form-control" id="responseSubTypeValueId1" name="questionResponseSubTypeList[1].responseSubTypeValueId" value="${questionsBo.questionResponseSubTypeList[1].responseSubTypeValueId}">
				   <div class="gray-xs-f mb-xs">Display Text <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="dispalyText1" name="questionResponseSubTypeList[1].text" value="No" readonly="readonly">
				      <div class="help-block with-errors red-txt" ></div>
				   </div>
				</div>
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Value <span class="requiredStar">*</span> </div>
				   <div class="form-group">
				      <input type="text" class="form-control" id="displayValue1" value="false" name="questionResponseSubTypeList[1].value" readonly="readonly">
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
				<c:if test="${questionnaireBo.branching}">
				<div class="col-md-3 pl-none">
				   <div class="gray-xs-f mb-xs">Destination Step <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="If there is branching applied to your questionnaire, you can  define destination steps for the Yes and No choices"></span> </div>
				   <div class="form-group">
				 
				      <select name="questionResponseSubTypeList[1].destinationStepId" id="destinationStepId1" title="select" data-error="Please choose one title" class="selectpicker" required>
				         <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionsBo.questionResponseSubTypeList[1].destinationStepId eq destinationStep.stepId ? 'selected' :''} >Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				         </c:forEach>
				         <option value="0" ${questionsBo.questionResponseSubTypeList[1].destinationStepId eq 0 ? 'selected' :''}>Completion Step</option>
				     </select>
				      <div class="help-block with-errors red-txt"></div>
				   </div>
				</div>
				</c:if>
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
   		  }
   		 $("#placeholderTextId").val(placeholderText);
   		 $("#stepValueId").val(stepText);
    		 document.questionStepId.submit();
		}else{
		   $('.stepLevel a').tab('show');
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
    var responseTypeId= '${questionsBo.responseType}';
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
        console.log("minDate:"+minDate);
        console.log("maxDate:"+maxDate);
        console.log("defaultDate:"+defaultDate);
        if(minDate!='' && maxDate!='' && defaultDate != ''){
        	if(toJSDate(defaultDate) > toJSDate(minDate) && toJSDate(defaultDate) < toJSDate(maxDate)){
        		$('#defaultDate').parent().removeClass("has-danger").removeClass("has-error");
                $('#defaultDate').parent().find(".help-block").html("");
                console.log("ifffff");
        	}else{
        		$('#defaultDate').parent().addClass("has-danger").addClass("has-error");
           	    $('#defaultDate').parent().find(".help-block").html('<ul class="list-unstyled"><li>Enter default date to be shown as selected as per availability of Min and Max</li></ul>');
           	    $('#defaultDate').val('');
           	     console.log("else");
        	}
        }
    });
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
		var previousResponseType = '${questionsBo.responseType}';
		if(Number(id) != Number(previousResponseType)){
			 var responseType = $("#responseTypeId>option:selected").html();
			 if(responseType != 'Continuous Scale' && responseType != 'Scale' && responseType != 'Boolean'){
				 $("#"+responseType.replace(/\s/g, '')).find('input:text').val(''); 
				 $("#"+responseType.replace(/\s/g, '')).find('input:text').val(''); 
				 if(responseType == "Date"){
					 $("#"+responseType.replace(/\s/g, '')).find('input:text').data("DateTimePicker").clear();					 
				 }
				 console.log("ifff:"+responseType);
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
			 var style = '${questionReponseTypeBo.style}';
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
	
	var fromId = $("#fromId").val();
	
	var questionsBo = new Object();
	
	var questionText = $("#questionTextId").val();
	var descriptionText = $("#descriptionId").val();
	var responseType = $("#responseTypeId").val();
	var addLinceChart = $('input[name="addLineChart"]:checked').val();
	var lineChartTimeRange = $("#lineChartTimeRangeId").val();
	var allowRollbackChart = $('input[name="allowRollbackChart"]:checked').val();
	var chartTitle = $('#chartTitleId').val();
	var useStasticData = $('input[name="useStasticData"]:checked').val();
	var statShortName = $("#statShortNameId").val();
	var statDisplayName = $("#statDisplayNameId").val();
	var statDisplayUnits = $("#statDisplayUnitsId").val();
	var statType=$("#statType").val();
	var statFormula=$("#statFormula").val();
	var questionid = $("#questionId").val();
	
	console.log("questionid:"+questionid);
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
	questionsBo.type="save";
	questionsBo.fromId=fromId;
	questionsBo.id = questionid;
	
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
		questionsBo.questionResponseSubTypeList = questionSubResponseArray;
		
		
	}
	
	var response_type_id = $("#questionResponseTypeId").val();
	var question_response_type_id = $("#responseQuestionId").val();
	
	questionReponseTypeBo.responseTypeId=response_type_id;
	questionReponseTypeBo.questionsResponseTypeId=question_response_type_id;
	
	
	questionsBo.questionReponseTypeBo=questionReponseTypeBo;
	if(fromId != null && fromId!= '' && typeof fromId !='undefined' && 
			questionText != null && questionText!= '' && typeof questionText !='undefined'){
		var data = JSON.stringify(questionsBo);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveQuestion.do",
	          type: "POST",
	          datatype: "json",
	          data: {questionInfo:data},
	          beforeSend: function(xhr, settings){
	              xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	          },
	          success:function(data){
	        	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					
					var questionId = jsonobject.stepId;
					var questionResponseId = jsonobject.stepId;
					
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
			        	a.href = "/fdahpStudyDesigner/adminStudies/formStep.do";
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