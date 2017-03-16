<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="changeContent">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do" name="activeContentFormId" id="activeContentFormId" method="post" role="form">
        <input type="hidden" name="id" value="${activeTaskBo.id}">
        <input type="hidden" name="taskType" value="${activeTaskBo.taskType}">
                    <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Title 1</div>
                         <div>
                             <div class="form-group">
                                 <input type="text" class="form-control" name="displayName" value="${activeTaskBo.displayName}"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    <div>
                        <div class="gray-xs-f mb-sm">Title 2</div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input type="text" class="form-control" name="shortTitle" value="${activeTaskBo.shortTitle}"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    
                   
                    <div class="mt-xlg blue-md-f text-uppercase">Configurable parameters</div>
                    <div class="gray-xs-f mt-md mb-sm">Instructions</div>
                    <div class="form-group">                     
                      <textarea class="form-control" rows="5" id="comment" name="instruction"></textarea>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                    <c:if test="${activeTaskBo.taskMasterAttributeBos[0].orderByTaskType eq 1}">
                    <div class="gray-xs-f mt-md mb-sm">${activeTaskBo.taskMasterAttributeBos[0].displayName}</div>                    
                    <div class="form-group col-md-2 p-none hrs">
                         <input type="hidden" name="taskAttributeValueBos[0].attributeValueId" value="">
                         <input type="hidden" name="taskAttributeValueBos[0].activeTaskMasterAttrId" value="${activeTaskBo.taskMasterAttributeBos[0].masterId}">
                         <input type="hidden" name="taskAttributeValueBos[0].addToDashboard" value="${activeTaskBo.taskMasterAttributeBos[0].addToDashboard}">
                         <input type="text" class="form-control pr-xlg timepicker" name="taskAttributeValueBos[0].attributeName"/>  
                         <!-- <span>hr</span> -->
                         <div class="help-block with-errors red-txt"></div>
                    </div>
                    <div class="clearfix"></div>
                    </c:if>
                    
                    <div class="blue-md-f text-uppercase">Results captured from the task</div>
                    <div class="pt-xs">
                         <c:if test="${activeTaskBo.taskMasterAttributeBos[1].orderByTaskType eq 2}">
                         <input type="hidden" name="taskAttributeValueBos[1].activeTaskMasterAttrId" value="${activeTaskBo.taskMasterAttributeBos[1].masterId}">
                         <input type="hidden" name="taskAttributeValueBos[1].addToDashboard" value="${activeTaskBo.taskMasterAttributeBos[1].addToDashboard}">
                        <div class="bullets bor-b-2-gray black-md-f pt-md pb-md">${activeTaskBo.taskMasterAttributeBos[1].displayName}</div>
                        </c:if>
                        <c:if test="${activeTaskBo.taskMasterAttributeBos[2].orderByTaskType eq 3}">
                        <input type="hidden" name="taskAttributeValueBos[2].attributeValueId" value="">
                        <input type="hidden" name="taskAttributeValueBos[2].activeTaskMasterAttrId" value="${activeTaskBo.taskMasterAttributeBos[2].masterId}">
                        <input type="hidden" name="taskAttributeValueBos[2].addToDashboard" value="${activeTaskBo.taskMasterAttributeBos[2].addToDashboard}">
                        <div class="bullets black-md-f pt-md">${activeTaskBo.taskMasterAttributeBos[2].displayName}</div>
                        
                        <div class="pl-xlg ml-xs bor-l-1-gray mt-lg">
                        
                          <div class="mb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="${activeTaskBo.taskMasterAttributeBos[2].attributeName}_chart_id" name="" value="option1">
                                <label for="${activeTaskBo.taskMasterAttributeBos[2].attributeName}_chart_id">Add to line chart</label>
                            </span>  
                          </div>   
                           
                          <div class="addLineChartBlock_${activeTaskBo.taskMasterAttributeBos[2].attributeName}" style="display:none">  
                          <div class="pb-lg">
                            <div class="gray-xs-f mt-md mb-sm">Time range for the chart</div>
                             <div class="add_notify_option">
                                <select class="selectpicker">
                                  <option>Days of the current week</option>
                                  <option>A Study for Pregnant Women</option>
                                  <option>Medication Survey 2</option>
                                </select>
                            </div> 
                          </div>
                            
                          <div class="pb-lg">
                              <div class="gray-xs-f mb-sm">Allow rollback of chart?</div>
                              <div>
                                <span class="radio radio-info radio-inline p-45">
                                    <input type="radio" id="inlineRadio1" value="option1" name="radioInline1">
                                    <label for="inlineRadio1">Yes</label>
                                </span>
                                <span class="radio radio-inline">
                                    <input type="radio" id="inlineRadio2" value="option1" name="radioInline1">
                                    <label for="inlineRadio2">No</label>
                                </span>
                              </div>
                          </div>
                           
                        <div class="bor-b-dash">
                            <div class="gray-xs-f mb-sm">Title for the chart</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                        </div>
                        </div>    
                         <div class="pt-lg mt-xs pb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="${activeTaskBo.taskMasterAttributeBos[2].attributeName}_stat_id" value="option1">
                                <label for="${activeTaskBo.taskMasterAttributeBos[2].attributeName}_stat_id">Use for Statistic</label>
                            </span>  
                          </div>
                          <div class="addLineStaticBlock_${activeTaskBo.taskMasterAttributeBos[2].attributeName}" style="display:none">  
                          <div>
                            <div class="gray-xs-f mb-sm">Short identifier name</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                            </div>                            
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display name for the Stat (e.g. Total Hours of Activity Over 6 Months)</div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)</div>
                             <div class="add_notify_option">
                                 <div class="form-group">
                                     <input type="text" class="form-control"/>  
                                     <div class="help-block with-errors red-txt"></div>
                                </div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Display Units (e.g. hours)</div>
                             <div class="add_notify_option">
                                  <select class="selectpicker">
                                      <option>Select</option>
                                      <option>A Study for Pregnant Women</option>
                                      <option>Medication Survey 2</option>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Formula for to be applied</div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                         </div>
                            
                         <div>
                            <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
                             <div class="add_notify_option">
                                  <select class="selectpicker">
                                      <option>Current Week</option>
                                      <option>A Study for Pregnant Women</option>
                                      <option>Medication Survey 2</option>
                                  </select>
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                         </div>
                        </div>
                            
                         </div>
                        </c:if>
                    </div>
                    </form:form>
                    </div>
 <script>
   $(document).ready(function(){
            $('#number_of_kicks_recorded_fetal_chart_id').on('click',function(){
	        	   if($(this).is(":checked")){
	        			$('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","");
	        	   }else{
	        	   	 $('.addLineChartBlock_number_of_kicks_recorded_fetal').css("display","none");
	        	   }
        		});
            $('#number_of_kicks_recorded_fetal_stat_id').on('click',function(){
	        	   if($(this).is(":checked")){
	        			$('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","");
	        	   }else{
	        	   	 $('.addLineStaticBlock_number_of_kicks_recorded_fetal').css("display","none");
	        	   }
     		}); 
            
            $("#doneId").click(function(){
            		if(isFromValid("#activeContentFormId")){
            			document.activeContentFormId.submit();    
            			console.log(isFromValid("#activeContentFormId"));
            		}
            });
            
        });
</script>                   
                    