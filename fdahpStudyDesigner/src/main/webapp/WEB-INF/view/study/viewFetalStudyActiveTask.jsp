<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="changeContent">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do?_S=${param._S}" name="activeContentFormId" id="activeContentFormId" method="post" role="form">
        <input type="hidden" name="id" id="taskContentId" value="${activeTaskBo.id}">
        <input type="hidden" name="taskTypeId" value="${activeTaskBo.taskTypeId}">
        <input type="hidden" name="studyId" value="${activeTaskBo.studyId}">
        <input type="hidden" value="" id="buttonText" name="buttonText"> 
        <input type="hidden" value="${actionPage}" id="actionPage" name="actionPage"> 
        <input type="hidden" value="" id="currentPageId" name="currentPage">
                    <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Activity Short Title or Key <small>(50 characters max)</small><span class="requiredStar"> *</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="This must be a human-readable activity identifier and unique across all activities of the study."></span></div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input autofocus="autofocus" type="text" custAttType="cust" class="form-control shortTitleIdCls" id="shortTitleId" name="shortTitle" value="${fn:escapeXml(activeTaskBo.shortTitle)}" 
                                 <c:if test="${not empty activeTaskBo.isDuplicate && (activeTaskBo.isDuplicate gt 1)}"> disabled</c:if> maxlength="50" required/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    <div>
                        <div class="gray-xs-f mb-sm">Display name <small>(150 characters max)</small><span class="requiredStar"> *</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="A name that gets displayed for the task in the app."></span></div>
                         <div>
                             <div class="form-group">
                                 <input type="text" class="form-control" name="displayName" value="${fn:escapeXml(activeTaskBo.displayName)}" maxlength="150" required/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    <div class="mt-lg blue-md-f text-uppercase">Configurable parameters</div>
                    <div class="gray-xs-f mt-md mb-sm">Instructions <small>(150 characters max)</small><span class="requiredStar"> *</span></div>
                    <div class="form-group">                     
                      <textarea class="form-control" rows="5" id="comment" name="instruction" maxlength="150" required>${activeTaskBo.instruction}</textarea>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                    <c:if test="${fn:length(activeTaskBo.taskAttributeValueBos) eq 0}">
                    <c:forEach items="${activeTaskBo.taskMasterAttributeBos}" var ="taskMasterAttributeBo">
                    <c:if test="${taskMasterAttributeBo.orderByTaskType eq 1}">
                    <div class="gray-xs-f mt-md mb-sm">${taskMasterAttributeBo.displayName}<span class="requiredStar"> *</span></div>                    
                    <div class="form-group col-md-3 col-lg-3 p-none timeDurationClass">
                         <input type="hidden" name="taskAttributeValueBos[0].attributeValueId" value="">
                         <input type="hidden" name="taskAttributeValueBos[0].activeTaskMasterAttrId" value="${taskMasterAttributeBo.masterId}">
                         <input type="hidden" name="taskAttributeValueBos[0].addToDashboard" value="${taskMasterAttributeBo.addToDashboard}">
                         <input type="text" id="inputClockId" class="form-control pr-xlg clock" placeholder="Time" name="taskAttributeValueBos[0].attributeVal" 
                           required /> 
                         <div class="help-block with-errors red-txt"></div>
                    </div>
                    <div class="clearfix"></div>
                    </c:if>
                    <c:if test="${taskMasterAttributeBo.orderByTaskType eq 2}">
                    <div class="blue-md-f text-uppercase">Results captured from the task</div>
                    <div class="pt-xs">
                        <div class="bullets bor-b-2-gray black-md-f pt-md pb-md">${taskMasterAttributeBo.displayName}</div>
                    </c:if>
                        <c:if test="${taskMasterAttributeBo.orderByTaskType eq 3}">
                        <input type="hidden" name="taskAttributeValueBos[1].attributeValueId" value="">
                        <input type="hidden" name="taskAttributeValueBos[1].activeTaskMasterAttrId" value="${taskMasterAttributeBo.masterId}">
                        <input type="hidden" name="taskAttributeValueBos[1].addToDashboard" value="${taskMasterAttributeBo.addToDashboard}">
                        <div class="bullets black-md-f pt-md">${taskMasterAttributeBo.displayName}</div>
                        
                        <div class="pl-xlg ml-xs bor-l-1-gray mt-lg">
                           <div class="chartSection" style="display:none">
                          <div class="mb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="${taskMasterAttributeBo.attributeName}_chart_id" name="taskAttributeValueBos[1].addToLineChart" value="option1">
                                <label for="${taskMasterAttributeBo.attributeName}_chart_id">Add to line chart</label>
                            </span>  
                          </div>   
                           
                          <div class="addLineChartBlock_${taskMasterAttributeBo.attributeName}" style="display:none">  
                          <div class="pb-lg">
                            <div class="gray-xs-f mt-md mb-sm">Time range for the chart<span class="requiredStar"> *</span></div>
                             <div class="add_notify_option form-group">
                                <select class="selectpicker elaborateClass requireClass " name="taskAttributeValueBos[1].timeRangeChart">
                                    <option value="" selected disabled>Select</option>
	                                <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                                    <option value="${timeRangeAttr}">${timeRangeAttr}</option>
	                                  </c:forEach>
	                                    <!-- <option value="Days of the current week" >Days of the current week</option>
	                                    <option value="Days of the current month" >Days of the current month</option>
	                                    <option value="24 hours of current day"  >24 hours of current day</option>
	                                    <option value="Weeks of the current month" >Weeks of the current month</option>
	                                    <option value="Months of the current year" >Months of the current year</option>
	                                    <option value="Run-based" >Run-based</option> -->
                                </select>
                                <div class="help-block with-errors red-txt"></div>
                            </div> 
                          </div>
                            
                          <div class="pb-lg">
                              <div class="gray-xs-f mb-sm">Allow rollback of chart?
                              <span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="If you select Yes, the chart will be allowed for rollback until the date of enrollment into the study."></span>
                              </div>
                              <div class="form-group">
                                <span class="radio radio-info radio-inline p-45">
                                    <input type="radio" id="inlineRadio1" value="Yes" name="taskAttributeValueBos[1].rollbackChat">
                                    <label for="inlineRadio1">Yes</label>
                                </span>
                                <span class="radio radio-inline">
                                    <input class="rollbackRadioClass" type="radio" id="inlineRadio2" value="No" name="taskAttributeValueBos[1].rollbackChat">
                                    <label for="inlineRadio2">No</label>
                                </span>
                                <div class="help-block with-errors red-txt"></div>
                              </div>
                          </div>
                           
                        <div class="bor-b-dash">
                            <div class="gray-xs-f mb-sm">Title for the chart <small>(30 characters max)</small><span class="requiredStar"> *</span></div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control requireClass" name="taskAttributeValueBos[1].titleChat" maxlength="30"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                        </div>
                        </div>
                        </div>    
                         <div class="pt-lg mt-xs pb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="${taskMasterAttributeBo.attributeName}_stat_id" name="taskAttributeValueBos[1].useForStatistic" value="option1">
                                <label for="${taskMasterAttributeBo.attributeName}_stat_id">Use for Statistic</label>
                            </span>  
                          </div>
                          <div class="addLineStaticBlock_${taskMasterAttributeBo.attributeName}" style="display:none">  
                          <div>
                            <div class="gray-xs-f mb-sm">Short name <small>(20 characters max)</small><span class="requiredStar"> *</span></div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input autofocus="autofocus" type="text" custAttType="cust" class="form-control requireClass shortTitleStatCls" id="static" name="taskAttributeValueBos[1].identifierNameStat" maxlength="20"/>
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months) <small>(50 characters max)</small><span class="requiredStar"> *</span></div>
                             <div class="form-group">
                                 <input type="text" class="form-control requireClass" name="taskAttributeValueBos[1].displayNameStat" maxlength="50"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours) <small>(15 characters max)</small><span class="requiredStar"> *</span></div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control requireClass" name="taskAttributeValueBos[1].displayUnitStat" maxlength="15"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Stat Type for image display<span class="requiredStar"> *</span></div>
                             <div class="add_notify_option form-group">
                                  <select class="selectpicker elaborateClass requireClass" title="Select" name="taskAttributeValueBos[1].uploadTypeStat">
                                      <option value="" selected disabled>Select</option>
                                      <c:forEach items="${statisticImageList}" var="statisticImage">
	                                    <option value="${statisticImage.statisticImageId}">${statisticImage.value}</option>
	                                </c:forEach>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Formula for to be applied<span class="requiredStar"> *</span></div>
                             <div class="form-group">
                                 <select class="selectpicker elaborateClass requireClass" title="Select" name="taskAttributeValueBos[1].formulaAppliedStat">
                                      <option value="" selected disabled>Select</option>
                                      <c:forEach items="${activetaskFormulaList}" var="activetaskFormula">
	                                    <option value="${activetaskFormula.activetaskFormulaId}">${activetaskFormula.value}</option>
	                                  </c:forEach>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                             <div>
                               <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
				               <div>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Day</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Month</span></span>
				                  <span class="txt-gray">(Rollback option provided for these three options)</span>
				               </div>
				               <!-- <div class="mt-sm">
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Custom Start and End Date</span></span>
				               </div> -->
				            </div>
                        </div>
                            
                         </div>
                    </div>
                    </c:if>
                    </c:forEach>
                    </c:if>
                    <c:if test="${fn:length(activeTaskBo.taskAttributeValueBos) gt 0}">
                    <c:set var="count" value="0"/>
                     <c:forEach items="${activeTaskBo.taskMasterAttributeBos}" var ="taskMasterAttributeBo">
                     <c:forEach items="${activeTaskBo.taskAttributeValueBos}" var ="taskValueAttributeBo">
	                    <c:if test="${taskMasterAttributeBo.orderByTaskType eq 1 && taskMasterAttributeBo.masterId eq taskValueAttributeBo.activeTaskMasterAttrId}">
	                    <div class="gray-xs-f mt-md mb-sm">${taskMasterAttributeBo.displayName}</div>                    
	                    <div class="form-group col-md-3 col-lg-3 p-none timeDurationClass">
	                         <input type="hidden" name="taskAttributeValueBos[0].attributeValueId" value="${taskValueAttributeBo.attributeValueId}">
	                         <input type="hidden" name="taskAttributeValueBos[0].activeTaskMasterAttrId" value="${taskMasterAttributeBo.masterId}">
	                         <input type="hidden" name="taskAttributeValueBos[0].addToDashboard" value="${taskMasterAttributeBo.addToDashboard}">
	                         <input type="text" id="inputClockId" class="form-control pr-xlg clock" placeholder="Time" 
	                                  name="taskAttributeValueBos[0].attributeVal" value="${taskValueAttributeBo.attributeVal}" required/>
	                         <div class="help-block with-errors red-txt"></div>
	                    </div>
	                    <div class="clearfix"></div>
	                    </c:if>
	                    <c:if test="${taskMasterAttributeBo.orderByTaskType eq 2 && count == 0}">
	                    <c:set var="count" value="${count+1}"/>
	                    <div class="blue-md-f text-uppercase">Results captured from the task</div>
	                    <div class="pt-xs">
	                        <div class="bullets bor-b-2-gray black-md-f pt-md pb-md">${taskMasterAttributeBo.displayName}</div>
	                    </c:if>
	                        <c:if test="${taskMasterAttributeBo.orderByTaskType eq 3 && taskMasterAttributeBo.masterId eq taskValueAttributeBo.activeTaskMasterAttrId}">
	                        <input type="hidden" name="taskAttributeValueBos[1].attributeValueId" value="${taskValueAttributeBo.attributeValueId}">
	                        <input type="hidden" name="taskAttributeValueBos[1].activeTaskMasterAttrId" value="${taskMasterAttributeBo.masterId}">
	                        <input type="hidden" name="taskAttributeValueBos[1].addToDashboard" value="${taskMasterAttributeBo.addToDashboard}">
	                        <div class="bullets black-md-f pt-md">${taskMasterAttributeBo.displayName}</div>
	                        
	                        <div class="pl-xlg ml-xs bor-l-1-gray mt-lg">
	                        <div class="chartSection" style="display:none">
	                          <div class="mb-lg">
	                            <span class="checkbox checkbox-inline">
	                                <input type="checkbox" id="${taskMasterAttributeBo.attributeName}_chart_id" name="taskAttributeValueBos[1].addToLineChart" <c:if test="${taskValueAttributeBo.addToLineChart==true}">checked</c:if> value="${taskValueAttributeBo.addToLineChart}">
	                                <label for="${taskMasterAttributeBo.attributeName}_chart_id">Add to line chart</label>
	                            </span>  
	                          </div>   
	                           
	                          <div class="addLineChartBlock_${taskMasterAttributeBo.attributeName}" style="${taskValueAttributeBo.addToLineChart==true?'':'display:none'}">  
	                          <div class="pb-lg">
	                            <div class="gray-xs-f mt-md mb-sm">Time range for the chart<span class="requiredStar"> *</span></div>
	                              <div class="add_notify_option form-group mb-none">
		                           <select class="selectpicker aq-select aq-select-form elaborateClass frequencyIdList" id="chartId" name="taskAttributeValueBos[1].timeRangeChart" title="Select" >
		                              <c:forEach items="${timeRangeList}" var="timeRangeAttr">
		                                 <option value="${timeRangeAttr}" ${fn:escapeXml(taskValueAttributeBo.timeRangeChart) eq fn:escapeXml(timeRangeAttr)?'selected':''}>${timeRangeAttr}</option>
		                              </c:forEach>
		                            </select>
		                            <div class="help-block with-errors red-txt"></div>
		                         </div>
	                           
	                          </div>
	                          
	                            
	                          <div class="pb-lg">
	                           <div class="gray-xs-f mb-sm">Allow rollback of chart?
	                           <span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="If you select Yes, the chart will be allowed for rollback until the date of enrollment into the study."></span>
                                </div>
	                              <div class="form-group">
	                                <span class="radio radio-info radio-inline p-45">
	                                    <input class="" type="radio" id="inlineRadio1" value="Yes" name="taskAttributeValueBos[1].rollbackChat" ${taskValueAttributeBo.rollbackChat eq 'Yes'?'checked':""}>
	                                    <label for="inlineRadio1">Yes</label>
	                                </span>
	                                <span class="radio radio-inline">
	                                    <input class="rollbackRadioClass" type="radio" id="inlineRadio2" value="No" name="taskAttributeValueBos[1].rollbackChat" <c:if test="${empty taskValueAttributeBo.rollbackChat  || empty taskValueAttributeBo}">checked</c:if> ${taskValueAttributeBo.rollbackChat eq 'No'?'checked':""}>
	                                    <label for="inlineRadio2">No</label>
	                                </span>
	                                <div class="help-block with-errors red-txt"></div>
	                              </div>
	                          </div>
	                           
	                        <div class="bor-b-dash">
	                         <div class="gray-xs-f mb-sm">Title for the chart <small>(30 characters max)</small><span class="requiredStar"> *</span>
                             </div>
	                             <div class="add_notify_option">
	                                 <div class="form-group">
	                                     <input type="text" class="form-control requireClass" id="lineChartId" name="taskAttributeValueBos[1].titleChat" maxlength="30" value="${fn:escapeXml(taskValueAttributeBo.titleChat)}"/>  
	                                     <div class="help-block with-errors red-txt"></div>
	                                </div>
	                            </div>                            
	                        </div>
	                        </div>
	                        </div>    
	                         <div class="pt-lg mt-xs pb-lg">
	                            <span class="checkbox checkbox-inline">
	                                <input type="checkbox" id="${taskMasterAttributeBo.attributeName}_stat_id" name="taskAttributeValueBos[1].useForStatistic" <c:if test="${taskValueAttributeBo.useForStatistic==true}">checked</c:if> value="${taskValueAttributeBo.useForStatistic}">
	                                <label for="${taskMasterAttributeBo.attributeName}_stat_id">Use for Statistic</label>
	                            </span>  
	                          </div>
	                          <div class="addLineStaticBlock_${taskMasterAttributeBo.attributeName}" style="${taskValueAttributeBo.useForStatistic==true?'':'display:none'}">  
	                          <div>
	                            <div class="gray-xs-f mb-sm">Short name <small>(20 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option">
	                                 <div class="form-group">
	                                     <input autofocus="autofocus" type="text" class="form-control shortTitleStatCls" id="${taskValueAttributeBo.attributeValueId}" name="taskAttributeValueBos[1].identifierNameStat" 
	                                     maxlength="20" value="${fn:escapeXml(taskValueAttributeBo.identifierNameStat)}" <c:if test="${not empty taskValueAttributeBo.isIdentifierNameStatDuplicate && (taskValueAttributeBo.isIdentifierNameStatDuplicate gt 1)}"> disabled</c:if>/>
	                                     <div class="help-block with-errors red-txt"></div>
	                                </div>
	                            </div>                            
	                         </div>
	                            
	                         <div>
	                            <div class="gray-xs-f mb-sm">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months) <small> (50 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="form-group">
	                                 <input type="text" class="form-control" name="taskAttributeValueBos[1].displayNameStat" maxlength="50" value="${fn:escapeXml(taskValueAttributeBo.displayNameStat)}"/>  
	                                 <div class="help-block with-errors red-txt"></div>
	                            </div>
	                         </div>
	                         
	                            
	                         <div>
	                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours) <small>(15 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option">
	                                 <div class="form-group">
	                                     <input type="text" class="form-control" name="taskAttributeValueBos[1].displayUnitStat" maxlength="15" value="${fn:escapeXml(taskValueAttributeBo.displayUnitStat)}"/>  
	                                     <div class="help-block with-errors red-txt"></div>
	                                </div>
	                            </div>
	                         </div>
	                         
	                         <div>
                            
	                            
	                         <div>
	                            <div class="gray-xs-f mb-sm">Stat Type for image display<span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option form-group">
	                                  <select class="selectpicker  elaborateClass requireClass" title="Select" name="taskAttributeValueBos[1].uploadTypeStat">
                                      <option value="" selected disabled>Select</option>
                                      <c:forEach items="${statisticImageList}" var="statisticImage">
	                                    <option value="${statisticImage.statisticImageId}" ${taskValueAttributeBo.uploadTypeStat eq statisticImage.statisticImageId?'selected':''}>${statisticImage.value}</option>
	                                </c:forEach>
                                    </select>
	                                 <div class="help-block with-errors red-txt"></div>
	                             </div>
	                         </div>
	                         <div>
	                            <div class="gray-xs-f mb-sm">Formula for to be applied<span class="requiredStar"> *</span></div>
	                             <div class="form-group">
	                                  <select class="selectpicker elaborateClass requireClass" title="Select" name="taskAttributeValueBos[1].formulaAppliedStat">
                                      <option value="" selected disabled>Select</option>
                                      <c:forEach items="${activetaskFormulaList}" var="activetaskFormula">
	                                    <option value="${activetaskFormula.activetaskFormulaId}" ${taskValueAttributeBo.formulaAppliedStat eq activetaskFormula.activetaskFormulaId?'selected':""}>${activetaskFormula.value}</option>
	                                  </c:forEach>
                                      </select>
	                                 <div class="help-block with-errors red-txt"></div>
	                            </div>
	                         </div>
	                         <!-- <div>
	                            <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
	                             <div class="add_notify_option form-group">
                                  Current Week . Current Month . Custom StartDate and EndDate
                                </div>
	                         </div> -->
	                         <div>
	                           <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
				               <div>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Day</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Month</span></span>
				                  <span class="txt-gray">(Rollback option provided for these three options)</span>
				               </div>
				               <!-- <div class="mt-sm">
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Custom Start and End Date</span></span>
				               </div> -->
				            </div>
	                        </div>
	                            
	                         </div>
	                    </div>
	                    </c:if>
	                    </c:forEach>
	                  </c:forEach>  
                    </c:if>
                    </form:form>
                    </div>
 <script>
 	var shortTitleFlag = true;
 	var shortTitleStatFlag = true;
 	var durationFlag = true;
   $(document).ready(function(){
// 	       var taskId = $('#taskContentId').val();
//           if(taskId){
//             var flag = "content";
//             setFrequencyVal(flag);
//  	       }
           var taskId = $('#taskContentId').val();
           if(taskId){
        	   var frequencyType = '${activeTaskBo.frequency}';
        	   if(frequencyType && frequencyType != 'One time')
        	      $('.chartSection').show();
        	   if(frequencyType && frequencyType == 'Manually Schedule'){
        		   $('.activeaddToChartText').show();
    			   $('.activeaddToChartText').html('A max of x runs will be displayed in each view of the chart.');
        	   }
           }
           var dt = new Date();
           $('#inputClockId').datetimepicker({
				format: 'HH:mm',
				minDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 00, 00),
				maxDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 23, 59)
  	       }).on("dp.change", function (e) {
				var durationTime = $('#inputClockId').val();
				if(durationTime && durationTime == '00:00'){
					durationFlag = false;
					$('#inputClockId').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Please select a non-zero Duration value.</li></ul>');
				}else{
					durationFlag = true;
					$('#inputClockId').parent().find(".help-block").empty();
					var dt = new Date();
					$('#inputClockId').datetimepicker({format: 'HH:mm',
				 		minDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 00, 00),
						maxDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 23, 59)});
					$('.timeDurationClass').removeClass('has-error has-danger');
				}
  	       });
	       setLineChatStatCheckedVal();
	        $('#number_of_kicks_recorded_fetal_chart_id').on('click',function(){
	        	   if($(this).is(":checked")){
	        			$('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","");
	        			$('.addLineChartBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', 'required');
	        			$('#number_of_kicks_recorded_fetal_chart_id').val(true);
	        	   }else{
	        	   	 $('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","none");
	        	   	 $('.addLineChartBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', false);
	        	   	 $('#number_of_kicks_recorded_fetal_chart_id').val(false);
	        	   	 $("#chartId").prop('required', false);
	        	   }
	        	   resetValidation($(this).parents('form'));
     	   });
            $('#number_of_kicks_recorded_fetal_stat_id').on('click',function(){
	        	   if($(this).is(":checked")){
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","");
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', 'required');
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', 'required');
	        			$('#number_of_kicks_recorded_fetal_stat_id').val(true);
	        	   }else{
	        	   	 $('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","none");
	        	   	$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', false);
	        	   	$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', false);
	        	   	$('#number_of_kicks_recorded_fetal_stat_id').val(false);
	        	   }
     		}); 
            $(document).on('click', '#doneId', function(e){
            	var taskInfoId = $('#id').val();
//             	validateShortTitleId(e, function(st,event){
//             		if(st){
	 					  if($('#pickStartDate').val() == ''){
						    $('#pickStartDate').attr("readonly",false);	
						  }
						  if($('#startWeeklyDate').val() == ''){
							$('#startWeeklyDate').attr("readonly",false);	
						  }
						  $('.shortTitleIdCls,.shortTitleStatCls').prop('disabled', false);
//             			validateShortTitleStatId(e, '.shortTitleStatCls', function(st,event){
            			  if(isFromValid("#activeContentFormId")){
	                        if(shortTitleFlag && shortTitleStatFlag){
                				if(!durationFlag){
                					$('#inputClockId').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Please select a non-zero Duration value.</li></ul>');
                					$('#inputClockId').focus();
                					return false;
                				}else{
                					$('#inputClockId').parent().find(".help-block").empty();
                					var dt = new Date();
                					$('#inputClockId').datetimepicker({format: 'HH:mm',
                				 		minDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 00, 00),
                						maxDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 23, 59)});
                				}
    	            			$('.scheduleTaskClass').removeAttr('disabled');
    	        			    $('.scheduleTaskClass').removeClass('linkDis');
    	        			    doneActiveTask(this, 'done', function(val) {
        								if(val) {
        									$("#buttonText").val('completed');
        			            			document.activeContentFormId.submit();
        								}
        							});
    	            		} else {
    			            	showErrMsg("Please fill in all mandatory fields.");
    			              	$('.contentClass a').tab('show');
    						}
            			} else {
		              		$('.contentClass a').tab('show');
						}
//             			});
//             		} else {
// 		              	$('.contentClass a').tab('show');
// 					}
//             	});
            });
            $('#saveId').click(function(e) {
//             	$("#shortTitleId").parent().find(".help-block").empty();
             	$('#activeContentFormId').validator('destroy').validator();
                if(!$('#shortTitleId')[0].checkValidity()){
                	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>This is a required field.</li></ul>');
                    $('.contentClass a').tab('show');
                    return false;
                } else {
//                 	validateShortTitleId(e, function(st,event){
//                 		if(st){
//                 			validateShortTitleStatId(e, '.shortTitleStatCls', function(st,event){
                			if(shortTitleFlag && shortTitleStatFlag){
                				if(!durationFlag){
                					$('#inputClockId').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Please select a non-zero Duration value.</li></ul>');
                					$('#inputClockId').focus();
                					return false;
                				}else{
                					$('#inputClockId').parent().find(".help-block").empty();
                					var dt = new Date();
                					$('#inputClockId').datetimepicker({format: 'HH:mm',
                				 		minDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 00, 00),
                						maxDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 23, 59)});
                				}
	                			if(taskId){
	                				doneActiveTask(this, 'save', function(val) {
	        							if(val) {
	        								$('.shortTitleIdCls,.shortTitleStatCls').prop('disabled', false);
	        								$('#activeContentFormId').validator('destroy');
	        	                        	$("#buttonText").val('save');
	        	                        	document.activeContentFormId.submit();
	        							}
	        						});
	                			}else {
	                				$('#activeContentFormId').validator('destroy');
		                        	$("#buttonText").val('save');
		                        	document.activeContentFormId.submit();
	                			}
                			} else {
    		              		$('.contentClass a').tab('show');
    						}
//                 			});
//                 		} else {
//     		              	$('.contentClass a').tab('show');
//     					}
//                 	});
                }
    		});
            $('.shortTitleIdCls').on('keyup',function(){
            	validateShortTitleId('', function(st, event){
            		
            	});
            });
            
            $('.shortTitleStatCls').on('keyup',function(){
				validateShortTitleStatId('', this, function(st,event){
					
				});
            });
 	       $('.selectpicker').selectpicker('refresh');
		   $('[data-toggle="tooltip"]').tooltip();
// 		   updateLogoutCsrf();
   });
   function validateShortTitleId(event, cb){
	var shortTitleId = $("#shortTitleId").val();
   	var dbshortTitleId = '${activeTaskBo.shortTitle}';
   	var activeTaskAttName = 'shortTitle'
   	var activeTaskAttIdVal = shortTitleId;
   	var activeTaskAttIdName = "not";
   	if(shortTitleId && (dbshortTitleId !=shortTitleId) && activeTaskAttIdName){
   		$('.actBut').prop('disabled', true);
   		$.ajax({
               url: "/fdahpStudyDesigner/adminStudies/validateActiveTaskShortTitleId.do?_S=${param._S}",
               type: "POST",
               datatype: "json",
               data: {
            	   activeTaskAttName:activeTaskAttName,
            	   activeTaskAttIdVal:activeTaskAttIdVal,
            	   activeTaskAttIdName:activeTaskAttIdName,
                   "${_csrf.parameterName}":"${_csrf.token}",
               },
               success: function emailValid(data, status) {
            	   var jsonobject = eval(data);
                   var message = jsonobject.message;
               	$("#shortTitleId").parent().removeClass('has-error has-danger').find(".help-block").html("");
               	var chk = true;
                   if (message == "SUCCESS") {
                       	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+shortTitleId+' already exist.</li></ul>');
                       	chk = false;
                       	shortTitleFlag = false;
                   } else {
                	   shortTitleFlag = true;
                   }
                   cb(chk,event);
               },
               error:function status(data, status) {
               	cb(false, event);
               },
               complete : function(){ $('.actBut').prop('disabled', false); },
               global : false
           });
     } else {
    	$("#shortTitleId").parent().removeClass('has-error has-danger').find(".help-block").empty();
		cb(true, event);
     }
   }
   function validateShortTitleStatId(event, thisAttr, cb){
	   var activeTaskAttName = 'identifierNameStat';
   	   var activeTaskAttIdVal = $(thisAttr).val();
   	   var activeTaskAttIdName = $(thisAttr).attr('id');
   	  if(activeTaskAttIdVal && activeTaskAttIdName){
	   		$('.actBut').prop('disabled', true);
	   		$.ajax({
	               url: "/fdahpStudyDesigner/adminStudies/validateActiveTaskShortTitleId.do?_S=${param._S}",
	               type: "POST",
	               datatype: "json",
	               data: {
	            	   activeTaskAttName:activeTaskAttName,
	            	   activeTaskAttIdVal:activeTaskAttIdVal,
	            	   activeTaskAttIdName:activeTaskAttIdName,
	                   "${_csrf.parameterName}":"${_csrf.token}",
	               },
	               success: function emailValid(data, status) {
	            	   var jsonobject = eval(data);
	                   var message = jsonobject.message;
	                   $(thisAttr).parent().removeClass('has-error has-danger').find(".help-block").html("");
	                   var chk = true;
	                   if (message == "SUCCESS") {
	                	    chk = false;
	                	    $(thisAttr).parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+activeTaskAttIdVal+' already exist.</li></ul>');
	                   		window.scrollTo(0,$(thisAttr).offset().top);
	                   		shortTitleStatFlag = false;
	                   } else {
	                	   shortTitleStatFlag = true;
	                   }
	                   cb(chk,event);
	               },
	               error:function status(data, status) {
	               		cb(false,event);
	               },
	               complete : function(){ $('.actBut').prop('disabled', false); },
	               global : false
	           });
	     } else {
	     	$(thisAttr).parent().removeClass('has-error has-danger').find(".help-block").html('');
	     	cb(true, event);
	     }
	   }
       function setLineChatStatCheckedVal(){
        	   if($('#number_of_kicks_recorded_fetal_chart_id').is(":checked")){
        			$('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","");
        			$('.addLineChartBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', 'required');
        			$('#number_of_kicks_recorded_fetal_chart_id').val(true);
        	   }else{
        	   	 $('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","none");
        	   	 $('.addLineChartBlock_number_of_kicks_recorded_fetal').find('.requireClass').prop('required', false);
        	   	 $('#number_of_kicks_recorded_fetal_chart_id').val(false);
        	   }
        	   if($('#number_of_kicks_recorded_fetal_stat_id').is(":checked")){
        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","");
        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', 'required');
        			$('#number_of_kicks_recorded_fetal_stat_id').val(true);
        	   }else{
        	   	 $('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","none");
        	   	$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', false);
        	   	$('#number_of_kicks_recorded_fetal_stat_id').val(false);
 		       }
       }
       var updateLogoutCsrf = function() {
			$('#logoutCsrf').val('${_csrf.token}');
			$('#logoutCsrf').prop('name', '${_csrf.parameterName}');
		}
     //# sourceURL=filename1.js
</script>                   
                    