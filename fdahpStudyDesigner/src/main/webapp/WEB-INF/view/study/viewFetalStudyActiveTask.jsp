<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="changeContent">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do" name="activeContentFormId" id="activeContentFormId" method="post" role="form">
        <input type="hidden" name="id" value="${activeTaskBo.id}">
        <input type="hidden" name="taskTypeId" value="${activeTaskBo.taskTypeId}">
        <input type="hidden" name="studyId" value="${activeTaskBo.studyId}">
        <input type="hidden" value="" id="buttonText" name="buttonText"> 
                    <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Activity Short Title or Key <small>(50 characters max)</small><span class="requiredStar"> *</span></div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input type="text" class="form-control shortTitleIdCls" id="shortTitleId" name="shortTitle" value="${activeTaskBo.shortTitle}" maxlength="50" required/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    <div>
                        <div class="gray-xs-f mb-sm">Display name<small>(150 characters max)</small><span class="requiredStar"> *</span></div>
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
                                <select class="selectpicker elaborateClass requireClass" name="taskAttributeValueBos[1].timeRangeChart">
                                    <option value="" selected disabled>Select</option>
	                                <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                                    <option value="${timeRangeAttr}">${timeRangeAttr}</option>
	                                </c:forEach>
                                </select>
                                <div class="help-block with-errors red-txt"></div>
                            </div> 
                          </div>
                            
                          <div class="pb-lg">
                              <div class="gray-xs-f mb-sm">Allow rollback of chart?</div>
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
                            <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
                             <div class="add_notify_option form-group">
                                  Current Week . Current Month . Custom StartDate and EndDate
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
	                        
	                          <div class="mb-lg">
	                            <span class="checkbox checkbox-inline">
	                                <input type="checkbox" id="${taskMasterAttributeBo.attributeName}_chart_id" name="taskAttributeValueBos[1].addToLineChart" <c:if test="${taskValueAttributeBo.addToLineChart==true}">checked</c:if> value="${taskValueAttributeBo.addToLineChart}">
	                                <label for="${taskMasterAttributeBo.attributeName}_chart_id">Add to line chart</label>
	                            </span>  
	                          </div>   
	                           
	                          <div class="addLineChartBlock_${taskMasterAttributeBo.attributeName}" style="${taskValueAttributeBo.addToLineChart==true?'':'display:none'}">  
	                          <div class="pb-lg">
	                            <div class="gray-xs-f mt-md mb-sm">Time range for the chart<span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option form-group">
	                                <select class="selectpicker elaborateClass requireClass" name="taskAttributeValueBos[1].timeRangeChart">
	                                  <option value="" selected disabled>Select</option>
	                                <c:forEach items="${timeRangeList}" var="timeRangeAttr">
	                                    <option value="${timeRangeAttr}" ${taskValueAttributeBo.timeRangeChart eq timeRangeAttr?'selected':''}>${timeRangeAttr}</option>
	                                </c:forEach> 
	                                </select>
	                                <div class="help-block with-errors red-txt"></div>
	                            </div> 
	                          </div>
	                          
	                            
	                          <div class="pb-lg">
	                              <div class="gray-xs-f mb-sm">Allow rollback of chart?</div>
	                              <div class="form-group">
	                                <span class="radio radio-info radio-inline p-45">
	                                    <input type="radio" id="inlineRadio1" value="Yes" name="taskAttributeValueBos[1].rollbackChat" ${taskValueAttributeBo.rollbackChat eq 'Yes'?'checked':""}>
	                                    <label for="inlineRadio1">Yes</label>
	                                </span>
	                                <span class="radio radio-inline">
	                                    <input type="radio" id="inlineRadio2" value="No" name="taskAttributeValueBos[1].rollbackChat" ${taskValueAttributeBo.rollbackChat eq 'No'?'checked':""}>
	                                    <label for="inlineRadio2">No</label>
	                                </span>
	                                <div class="help-block with-errors red-txt"></div>
	                              </div>
	                          </div>
	                           
	                        <div class="bor-b-dash">
	                            <div class="gray-xs-f mb-sm">Title for the chart <small>(30 characters max)</small><span class="requiredStar"> *</span></div>
	                             <div class="add_notify_option">
	                                 <div class="form-group">
	                                     <input type="text" class="form-control requireClass" name="taskAttributeValueBos[1].titleChat" maxlength="30" value="${taskValueAttributeBo.titleChat}"/>  
	                                     <div class="help-block with-errors red-txt"></div>
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
	                                  <select class="selectpicker  requireClass" title="Select" name="taskAttributeValueBos[1].uploadTypeStat">
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
	                                  <select class="selectpicker requireClass" title="Select" name="taskAttributeValueBos[1].formulaAppliedStat">
                                      <option value="" selected disabled>Select</option>
                                      <c:forEach items="${activetaskFormulaList}" var="activetaskFormula">
	                                    <option value="${activetaskFormula.activetaskFormulaId}" ${taskValueAttributeBo.formulaAppliedStat eq activetaskFormula.activetaskFormulaId?'selected':""}>${activetaskFormula.value}</option>
	                                  </c:forEach>
                                      </select>
	                                 <div class="help-block with-errors red-txt"></div>
	                            </div>
	                         </div>
	                         <div>
	                            <div class="gray-xs-f mb-sm">Time ranges options available to the mobile app user</div>
	                             <div class="add_notify_option form-group">
                                  Current Week . Current Month . Custom StartDate and EndDate
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
            $("#doneId").click(function(){
            		if(isFromValid("#activeContentFormId")){
            			doneActiveTask(this, 'done', function(val) {
							if(val) {
								$("#buttonText").val('completed');
		            			document.activeContentFormId.submit();
							}
						});
            		}
            });
            $('#saveId').click(function(e) {
            	$("#shortTitleId").parent().find(".help-block").empty();
            	$('#activeContentFormId').validator('destroy').validator();
                if(!$('#shortTitleId')[0].checkValidity()){
                	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>This is a required field.</li></ul>');
                    return false;
                } else {
                	validateShortTitleId(e, function(st,event){
                		if(st){
                			doneActiveTask(this, 'save', function(val) {
    							if(val) {
    								$('#activeContentFormId').validator('destroy');
    	                        	$("#buttonText").val('save');
    	                        	document.activeContentFormId.submit();
    							}
    						});
                		}
                	});
                }
    		});
            $('.shortTitleIdCls').on('blur',function(){
            	validateShortTitleId('', function(st, event){
            		
            	});
            });
            $('.shortTitleStatCls').on('blur',function(){
            	var activeTaskAttName = 'identifierNameStat';
            	var activeTaskAttIdVal = $(this).val();
            	var activeTaskAttIdName = $(this).attr('id');
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
        	                   $(this).parent().find(".help-block").html("");
        	                   if (message == "SUCCESS") {
        	                	    $(this).parent().find(".help-block").empty();
        	                	    $(this).parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+activeTaskAttIdVal+' already exist.</li></ul>');
        	                	    $(this).val('');
        	                   }
        	               },
        	               error:function status(data, status) {
        	               	$("body").removeClass("loading");
        	               },
        	               global:false,
        	               complete : function(){ $('.actBut').removeAttr('disabled'); }
        	           });
        	     }
            });
            $(window).on("load",function(){				
            	var a = $(".col-lc").height();
            	var b = $(".col-rc").height();
            	if(a > b){
            		$(".col-rc").css("height", a);	
            	}else{
            		$(".col-rc").css("height", "auto");
            	}
			});
//             <c:if test="${actionPage eq 'view'}">
// 			    $('#activeContentFormId input,textarea,select').prop('disabled', true);
// 			    $('#activeContentFormId .elaborateClass').addClass('linkDis');
// 			    $('.actBut').hide();
//             </c:if>
            
//             $(".clock").datetimepicker({
//            	 format: 'HH:mm',
//            });
            $('#inputClockId').datetimepicker({
   	    	 format: 'HH:mm',
   	       });
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
                   	    $("#shortTitleId").parent().find(".help-block").empty();
                       	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+shortTitleId+' already exist.</li></ul>');
                       	$("#shortTitleId").val('');
                       	chk = false;
                   }
                   cb(chk,event);
               },
               error:function status(data, status) {
               	$("body").removeClass("loading");
               	cb(false, event);
               },
               global:false,
               complete : function(){ $('.actBut').removeAttr('disabled'); }
           });
     } else {
   	  cb(true, event);
     }
   }
   function validateShortTitleStatId(activeTaskAttName, activeTaskAttIdVal, activeTaskAttIdName){
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
	               	$("#shortTitleId").parent().find(".help-block").html("");
	               	var chk = true;
	                   if (message == "SUCCESS") {
	                   	    $("#shortTitleId").parent().find(".help-block").empty();
	                       	$("#shortTitleId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+shortTitleId+' already exist.</li></ul>');
	                       	$("#shortTitleId").val('');
	                       	chk = false;
	                   }
	                   cb(chk,event);
	               },
	               error:function status(data, status) {
	               	$("body").removeClass("loading");
	               	cb(false, event);
	               },
	               global:false,
	               complete : function(){ $('.actBut').removeAttr('disabled'); }
	           });
	     } else {
	   	  cb(true, event);
	     }
	   }
</script>                   
                    