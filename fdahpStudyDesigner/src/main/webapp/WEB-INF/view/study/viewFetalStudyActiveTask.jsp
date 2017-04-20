<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="changeContent">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do" name="activeContentFormId" id="activeContentFormId" method="post" role="form">
        <input type="hidden" value="content" name="currentPage" id="currentPageId">
        <input type="hidden" name="id" id="taskContentId" value="${activeTaskBo.id}">
        <input type="hidden" name="taskTypeId" value="${activeTaskBo.taskTypeId}">
        <input type="hidden" name="studyId" value="${activeTaskBo.studyId}">
        <input type="hidden" value="" id="buttonText" name="buttonText"> 
                    <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Activity Short Title or Key <small>(50 characters max)</small><span class="requiredStar"> *</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="This must be a human-readable activity identifier and unique across all activities of the study."></span></div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input type="text" class="form-control shortTitleIdCls" id="shortTitleId" name="shortTitle" value="${activeTaskBo.shortTitle}" maxlength="50" required/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    <div>
                        <div class="gray-xs-f mb-sm">Display name<small>(150 characters max)</small><span class="requiredStar"> *</span><span class="ml-xs sprites_v3 filled-tooltip"  data-toggle="tooltip" title="A name that gets displayed for the task in the app."></span></div>
                         <div>
                             <div class="form-group">
                                 <input type="text" class="form-control" name="displayName" value="${activeTaskBo.displayName}" maxlength="150" required/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    <div class="mt-xlg blue-md-f text-uppercase">Configurable parameters</div>
                    <div class="gray-xs-f mt-md mb-sm">Instructions <small>(150 characters max)</small><span class="requiredStar"> *</span></div>
                    <div class="form-group">                     
                      <textarea class="form-control" rows="5" id="comment" name="instruction" maxlength="150" required>${activeTaskBo.instruction}</textarea>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                    <c:if test="${fn:length(activeTaskBo.taskAttributeValueBos) eq 0}">
                    <c:forEach items="${activeTaskBo.taskMasterAttributeBos}" var ="taskMasterAttributeBo">
                    <c:if test="${taskMasterAttributeBo.orderByTaskType eq 1}">
                    <div class="gray-xs-f mt-md mb-sm">${taskMasterAttributeBo.displayName}<span class="requiredStar"> *</span></div>                    
                    <div class="form-group col-md-2 p-none">
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
                                    <input type="radio" id="inlineRadio2" value="No" name="taskAttributeValueBos[1].rollbackChat">
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
                                     <input type="text" class="form-control requireClass shortTitleStatCls" id="static" name="taskAttributeValueBos[1].identifierNameStat" maxlength="20"/>
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display name for the Stat(e.g. Total Hours of Activity Over 6 Months) <small>(50 characters max)</small><span class="requiredStar"> *</span></div>
                             <div class="form-group">
                                 <input type="text" class="form-control requireClass" name="taskAttributeValueBos[1].displayNameStat" maxlength="50"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)<small>(15 characters max)</small><span class="requiredStar"> *</span></div>
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
				               <div>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Month</span></span>
				                  <span class="txt-gray">(Rollback option provided for these three options)</span>
				               </div>
				               <div class="mt-sm">
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Custom Start and End Date</span></span>
				               </div>
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
	                    <div class="form-group col-md-2 p-none">
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
	                                <select class="selectpicker elaborateClass requireClass frequencyIdList" name="taskAttributeValueBos[1].timeRangeChart" id="chartId">
	                                  <option value="" selected disabled>Select</option>
	                                  <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                                    <option value="${timeRangeAttr}" ${fn:escapeXml(taskValueAttributeBo.timeRangeChart) eq fn:escapeXml(timeRangeAttr)?'selected':''}>${timeRangeAttr}</option>
	                                  </c:forEach> 
	                                </select>
	                               <div class="clearfix"></div>
	                               <div class="mt-sm black-xs-f italic-txt activeaddToChartText" style="display: none;">
	                                  
	                              </div> 
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
	                                    <input class="rollbackRadioClass" type="radio" id="inlineRadio2" value="No" name="taskAttributeValueBos[1].rollbackChat" <c:if test="${empty taskValueAttributeBo.rollbackChat  || empty taskValueAttributeBo}">checked</c:if>>
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
	                                     <input type="text" class="form-control requireClass" id="lineChartId" name="taskAttributeValueBos[1].titleChat" maxlength="30" value="${taskValueAttributeBo.titleChat}"/>  
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
	                                     <input type="text" class="form-control shortTitleStatCls" id="${taskValueAttributeBo.attributeValueId}" name="taskAttributeValueBos[1].identifierNameStat" maxlength="20" value="${taskValueAttributeBo.identifierNameStat}"/>
	                                     <div class="help-block with-errors red-txt"></div>
	                                </div>
	                            </div>                            
	                         </div>
	                            
	                         <div>
	                            <div class="gray-xs-f mb-sm">Display name for the Stat(e.g. Total Hours of Activity Over 6 Months) <small>(50 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="form-group">
	                                 <input type="text" class="form-control" name="taskAttributeValueBos[1].displayNameStat" maxlength="50" value="${taskValueAttributeBo.displayNameStat}"/>  
	                                 <div class="help-block with-errors red-txt"></div>
	                            </div>
	                         </div>
	                         
	                            
	                         <div>
	                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)<small>(15 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option">
	                                 <div class="form-group">
	                                     <input type="text" class="form-control" name="taskAttributeValueBos[1].displayUnitStat" maxlength="15" value="${taskValueAttributeBo.displayUnitStat}"/>  
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
				               <div>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Week</span></span>
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Current Month</span></span>
				                  <span class="txt-gray">(Rollback option provided for these three options)</span>
				               </div>
				               <div class="mt-sm">
				                  <span class="mr-lg"><span class="mr-sm"><img src="../images/icons/tick.png"/></span><span>Custom Start and End Date</span></span>
				               </div>
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
	        	   }
	        	   resetValidation($(this).parents('form'));
     	   });
            $('#number_of_kicks_recorded_fetal_stat_id').on('click',function(){
	        	   if($(this).is(":checked")){
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","");
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', 'required');
	        			$('#number_of_kicks_recorded_fetal_stat_id').val(true);
	        	   }else{
	        	   	 $('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","none");
	        	   	$('.addLineStaticBlock_number_of_kicks_recorded_fetal').find('input,textarea,select').prop('required', false);
	        	   	$('#number_of_kicks_recorded_fetal_stat_id').val(false);
	        	   }
     		}); 
            $("#doneId").click(function(e){
            	var taskInfoId = $('#id').val();
            	$('.shortTitleIdCls').trigger('change');
            	validateShortTitleId(e, function(st,event){
            		if(st){
	            		if(isFromValid("#activeContentFormId")){
	            			$('.scheduleTaskClass').removeAttr('disabled');
	        			    $('.scheduleTaskClass').removeClass('linkDis');
	            			doneActiveTask(this, 'done', function(val) {
								if(val) {
									//$('.frequencyIdList').selectpicker('refresh');
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
            	});
            });
            $('#saveId').click(function(e) {
            	$("#shortTitleId").parent().find(".help-block").empty();
            	$('#activeContentFormId').validator('destroy').validator();
            	$('.shortTitleIdCls').trigger('change');
                if(!$('#shortTitleId')[0].checkValidity()){
                	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>This is a required field.</li></ul>');
                    $('.contentClass a').tab('show');
                    return false;
                } else {
                	validateShortTitleId(e, function(st,event){
                		if(st){
                			if(taskId){
                				doneActiveTask(this, 'save', function(val) {
        							if(val) {
        								$('#activeContentFormId').validator('destroy');
        	                        	$("#buttonText").val('save');
        	                        	document.activeContentFormId.submit();
        							}
        						});
                			}else{
                				$('#activeContentFormId').validator('destroy');
	                        	$("#buttonText").val('save');
	                        	document.activeContentFormId.submit();
                			}
                			
                		} else {
    		              	$('.contentClass a').tab('show');
    					}
                	});
                }
    		});
            $('.shortTitleIdCls').on('change',function(){
            	validateShortTitleId('', function(st, event){});
            });
//             $('.shortTitleStatCls').on('change',function(){
//             	validateShortTitleStatId('', function(st, event){});
//             });
            
            $('.shortTitleStatCls').on('blur',function(){
            	var activeTaskAttName = 'identifierNameStat';
            	var activeTaskAttIdVal = $(this).val();
            	var activeTaskAttIdName = $(this).attr('id');
            	var thisAttr = this;
            	if(activeTaskAttName && activeTaskAttIdVal && activeTaskAttIdName){
        	   		$('.actBut').attr('disabled','disabled');
        	   		$.ajax({
        	               url: "/fdahpStudyDesigner/adminStudies/validateActiveTaskShortTitleId.do",
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
        	                   $(thisAttr).parent().find(".help-block").html("");
        	                   if (message == "SUCCESS") {
        	                	    $(thisAttr).parent().find(".help-block").empty();
        	                	    $(thisAttr).parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+activeTaskAttIdVal+' already exist.</li></ul>');
        	                	    $(thisAttr).val('');
        	                   }
        	               },
        	               error:function status(data, status) {
        	               	
        	               },
        	               
        	               complete : function(){ $('.actBut').removeAttr('disabled'); }
        	           });
        	     }
            });
            var dt = new Date();
            $('#inputClockId').datetimepicker({
				format: 'HH:mm',
				minDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 01, 00),
				maxDate : new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(), 23, 59)
   	       });
 	       $('.selectpicker').selectpicker('refresh');
		   $('[data-toggle="tooltip"]').tooltip();
   });
   function validateShortTitleId(event, cb){
	var shortTitleId = $("#shortTitleId").val();
   	var dbshortTitleId = '${activeTaskBo.shortTitle}';
   	var activeTaskAttName = 'shortTitle'
   	var activeTaskAttIdVal = shortTitleId;
   	var activeTaskAttIdName = "not";
   	if(shortTitleId && (dbshortTitleId !=shortTitleId) && activeTaskAttIdName){
   		$('.actBut').attr('disabled','disabled');
   		$.ajax({
               url: "/fdahpStudyDesigner/adminStudies/validateActiveTaskShortTitleId.do",
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
               	$("#shortTitleId").parent().find(".help-block").html("");
               	var chk = true;
                   if (message == "SUCCESS") {
                       	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+shortTitleId+' already exist.</li></ul>');
                       	chk = false;
                   }
                   cb(chk,event);
               },
               error:function status(data, status) {
               	$("body").removeClass("loading");
               	cb(false, event);
               },
               complete : function(){ $('.actBut').removeAttr('disabled'); }
           });
     } else {
    	$("#shortTitleId").parent().removeClass('has-error has-danger').find(".help-block").empty();
		cb(true, event);
     }
   }
   function validateShortTitleStatId(activeTaskAttName, activeTaskAttIdVal, activeTaskAttIdName){
	   var activeTaskAttName = 'identifierNameStat';
   	   var activeTaskAttIdVal = $(this).val();
   	   var activeTaskAttIdName = $(this).attr('id');
   	   var thisAttr = this;
   	  if(activeTaskAttName && activeTaskAttIdVal && activeTaskAttIdName){
	   		$('.actBut').attr('disabled','disabled');
	   		$.ajax({
	               url: "/fdahpStudyDesigner/adminStudies/validateActiveTaskShortTitleId.do",
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
	                   $(thisAttr).parent().find(".help-block").html("");
	                   if (message == "SUCCESS") {
	                	    $(thisAttr).parent().find(".help-block").empty();
	                	    $(thisAttr).parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+activeTaskAttIdVal+' already exist.</li></ul>');
	                	    $(thisAttr).val('');
	                   }
	               },
	               error:function status(data, status) {
	               	
	               },
	               
	               complete : function(){ $('.actBut').removeAttr('disabled'); }
	           });
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
     //# sourceURL=filename1.js
</script>                   
                    