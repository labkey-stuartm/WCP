<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.cursonMove{
 cursor: move !important;
}
.sepimgClass{
 position: relative;
}
.time-opts .addBtnDis{
	display: none;
}
.time-opts:last-child .addBtnDis{
	display: initial;
}
.manually-option .addBtnDis{
	display: none;
}
.manually-option:last-child .addBtnDis{
	display: initial;
}
.tool-tip {
  display: inline-block;
}

.tool-tip [disabled] {
  pointer-events: none;
}
/* .time-opts .remBtnDis{
	display: initial;
} */
</style>

<script type="text/javascript">
function isNumber(evt) {
	evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode < 48 && charCode > 57) || (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)){
    	 return false;
    }
    return true;
}
</script>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
 <div class="col-sm-10 col-rc white-bg p-none">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span onclick="goToBackPage(this);"><img src="../images/icons/back-b.png" class="pr-md"/></span> Add Questionnaire</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
         </div>
         <c:if test="${empty permission}">
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="saveQuestionnaire(this);">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
	         <span class="tool-tip" data-toggle="tooltip" data-placement="top" <c:if test="${fn:length(qTreeMap) eq 0 || !isDone }"> title="Please ensure individual list items are Marked as Completed before marking the section as Complete" </c:if> >
            	<button type="button" class="btn btn-primary blue-btn" id="doneId" <c:if test="${fn:length(qTreeMap) eq 0 || !isDone }">disabled</c:if>>Mark as Completed</button>
            </span>
         </div>
         </c:if>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <input type="hidden" name="id" value=" ${questionnaireBo.id}">
   <div class="right-content-body pt-none pl-none" id="rootContainer">
      <ul class="nav nav-tabs review-tabs" id="tabContainer">
         <li class="contentqusClass active"><a data-toggle="tab" href="#contentTab">Content</a></li>
         <li class ="scheduleQusClass"><a data-toggle="tab" href="#schedule">Schedule</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <!-- Content--> 
		<div id="contentTab" class="tab-pane fade in active mt-xlg">
		   <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="contentFormId" id="contentFormId" method="post" data-toggle="validator" role="form">
		   <input type="hidden" name="type" id="type" value="content">
		   <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
		   <input type="hidden" name="status" id="status" value="true">
		   <input type="hidden" name="questionnaireId" id="questionnaireId" value="${questionnaireBo.id}">
	       <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
	       <input type="hidden" name="instructionId" id="instructionId" value="">
	       <input type="hidden" name="formId" id="formId" value="">
	       <input type="hidden" name="questionId" id="questionId" value="">
		   <div class="gray-xs-f mb-xs">Activity Short Title or Key  <span class="requiredStar">*</span><span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="A human readable step identifier and must be unique across all steps of the questionnaire."></span></div>
		   <div class="form-group col-md-5 p-none">
		      <input type="text" class="form-control" name="shortTitle" id="shortTitleId" value="${questionnaireBo.shortTitle}" required="required" maxlength="50"/>
		      <div class="help-block with-errors red-txt"></div>
		   </div>
		   <div class="clearfix"></div>
		   <div class="gray-xs-f mb-xs">Title</div>
		   <div class="form-group">
		      <input type="text" class="form-control" name="title" id="titleId" value="${questionnaireBo.title}" maxlength="250"/>
		   </div>
		   <div class="mt-xlg">
		      <div class="add-steps-btn blue-bg" onclick="getQuestionnaireStep('Instruction');"><span class="pr-xs">+</span>  Add Instruction Step</div>
		      <div class="add-steps-btn green-bg" onclick="getQuestionnaireStep('Question');"><span class="pr-xs">+</span>  Add Question Step</div>
		      <div class="add-steps-btn skyblue-bg" onclick="getQuestionnaireStep('Form');"><span class="pr-xs">+</span>  Add Form Step</div>
		      <span class="sprites_v3 info"></span>
		      <div class="pull-right mt-xs">
		         <span class="checkbox checkbox-inline">
		         <input type="checkbox" id="branchingId" value="true" name="branching" ${questionnaireBo.branching ? 'checked':''} >
		         <label for="branchingId"> Apply Branching </label>
		         </span>
		      </div>
		   </div>
		   </form:form>
		   <div class="mt-md">
		      <table id="content" class="display" cellspacing="0" width="100%" style="border-color: #ffffff;" >
		      	 <thead style="display: none;"></thead>
		      	 <tbody>
		      	 <c:forEach items="${qTreeMap}" var="entry">
		      	 	<tr>
		      	 	<c:choose>
		      	 		  <c:when test="${entry.value.stepType eq 'Instruction'}"><td> <span id="${entry.key}" class="round blue-round">${entry.key}</span></td></c:when>
		               	  <c:when test="${entry.value.stepType eq 'Question'}"><td> <span id="${entry.key}" class="round green-round">${entry.key}</span></td></c:when>
		               	  <c:otherwise><td><span id="${entry.key}" class="round teal-round">${entry.key}</span></td>
		               	 	<%-- <c:forEach begin="0" end="${fn:length(entry.value.fromMap)-1}">
								    <div>&nbsp;</div>
							 </c:forEach> --%>
		            	  </c:otherwise>
		      	 	</c:choose>
		            <td>
				      <c:choose>
				              	<c:when test="${entry.value.stepType eq 'Form'}">
					             	<c:forEach items="${entry.value.fromMap}" var="subentry">
			               			  	<div>${subentry.value.title}</div>
			               			 </c:forEach>
					             </c:when>
					             <c:otherwise>
					               	<div>${entry.value.title}</div>
			               		  </c:otherwise>
				       </c:choose>
		            </td>
		            <td> <div class="destinationStep" style="display: none;">${entry.key}</div> </td>
		            <td>
		            	<div>
		                  <div class="text-right pos-relative">
		                  	 <c:if test="${entry.value.stepType ne 'Instruction'}">
		                    <!--  <span class="sprites_v3 status-blue mr-md"></span>
		                     <span class="sprites_v3 heart-blue mr-md"></span>
		                     <span class="sprites_v3 calender-blue mr-md"></span> -->
		                     <c:choose>
                              	 	<c:when test="${entry.value.responseTypeText eq 'Numeric ' && entry.value.lineChart eq 'Yes'}">
                              	 		<span class="sprites_v3 status-blue mr-md"></span>
                              	 	</c:when>
                         			<c:when test="${entry.value.responseTypeText eq 'Numeric ' && entry.value.lineChart eq 'No'}">
                              	 		<span class="sprites_v3 status-gray mr-md"></span>
                              	 	</c:when> 
                              	 	<c:when test="${entry.value.responseTypeText eq 'Date'}"><span class="sprites_v3 calender-gray mr-md"></span></c:when>
                             </c:choose>
		                     </c:if>
		                      
		                     <span class="ellipse" onmouseenter="ellipseHover(this);"></span>
		                     <div class="ellipse-hover-icon" onmouseleave="ellipseUnHover(this);">
		                        <span class="sprites_icon preview-g mr-sm"></span>
		                        <span class="sprites_icon edit-g mr-sm" onclick="editStep(${entry.value.stepId},'${entry.value.stepType}')"></span>
		                        <span class="sprites_icon delete deleteStepButton" onclick="deletStep(${entry.value.stepId},'${entry.value.stepType}')"></span>
		                     </div>
		                  </div>
		                  <c:if test="${entry.value.stepType eq 'Form'}">
			                 <c:if test="${fn:length(entry.value.fromMap) gt 0}">
			                 <c:forEach begin="0" end="${fn:length(entry.value.fromMap)-1}">
								    <div>&nbsp;</div>
							 </c:forEach>
							 </c:if>
		                  </c:if>
		                 </div>
		            </td>
		          </tr>
		      	</c:forEach>
		      	</tbody>
		      </table>
		   </div>
		</div>
		<!-- End Content-->
         <!---  Schedule ---> 
         <div id="schedule" class="tab-pane fade mt-xlg">
            <div class="gray-xs-f mb-sm">Questionnaire Frequency</div>
            <div class="pb-lg b-bor">
               <span class="radio radio-info radio-inline p-40">
               <input type="radio" id="inlineRadio1" class="schedule" frequencytype="oneTime" value="One time" name="frequency" ${empty questionnaireBo.frequency  || questionnaireBo.frequency=='One time' ?'checked':''}>
               <label for="inlineRadio1">One Time</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio2" class="schedule" frequencytype="daily" value="Daily" name="frequency" ${questionnaireBo.frequency=='Daily' ?'checked':''}>
               <label for="inlineRadio2">Daily</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio3" class="schedule" frequencytype="week" value="Weekly" name="frequency" ${questionnaireBo.frequency=='Weekly' ?'checked':''}>
               <label for="inlineRadio3">Weekly</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio4" class="schedule" frequencytype="month" value="Monthly" name="frequency" ${questionnaireBo.frequency=='Monthly' ?'checked':''}>
               <label for="inlineRadio4">Monthly</label>
               </span>
               <span class="radio radio-inline p-40">
               <input type="radio" id="inlineRadio5" class="schedule" frequencytype="manually" value="Manually Schedule" name="frequency" ${questionnaireBo.frequency=='Manually schedule' ?'checked':''}>
               <label for="inlineRadio5">Manually Schedule</label>
               </span>
            </div>
            <!-- One Time Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="oneTimeFormId" id="oneTimeFormId" method="post" role="form">
	            <input type="hidden" name="frequency" id="frequencyId" value="${questionnaireBo.frequency}">
	            <input type="hidden" name="previousFrequency" id="previousFrequency" value="${questionnaireBo.frequency}">
	            <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
	            <input type="hidden" name="type" id="type" value="schedule">
	            <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
	            <div class="oneTime all mt-xlg">
	               <div class="gray-xs-f mb-sm">Date/Time of launch(pick one)</div>
	               <div class="mt-sm">
	                  <span class="checkbox checkbox-inline">
	                  <input type="hidden" name="questionnairesFrequenciesBo.id" id="oneTimeFreId" value="${questionnaireBo.questionnairesFrequenciesBo.id}">
	                  <input type="checkbox" id="isLaunchStudy" name="questionnairesFrequenciesBo.isLaunchStudy" value="true" ${questionnaireBo.questionnairesFrequenciesBo.isLaunchStudy ?'checked':''} disabled="disabled">
	                  <label for="isLaunchStudy"> Launch with study</label>
	                  </span>
	                  <div class="mt-md form-group">
	                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                     <input id="chooseDate" type="text" class="form-control calendar" name="questionnairesFrequenciesBo.frequencyDate" placeholder="Choose Date" value="${questionnaireBo.questionnairesFrequenciesBo.frequencyDate}" required <c:if test="${questionnaireBo.questionnairesFrequenciesBo.isLaunchStudy}"> disabled </c:if> />
	                      <span class='help-block with-errors red-txt'></span>
	                     </span>
	                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                     <input id="selectTime" type="text" class="form-control clock"  name="questionnairesFrequenciesBo.frequencyTime"  value="${questionnaireBo.questionnairesFrequenciesBo.frequencyTime}" required <c:if test="${questionnaireBo.questionnairesFrequenciesBo.isLaunchStudy}"> disabled </c:if>  placeholder="Select Time"  />
	                     <span class='help-block with-errors red-txt'></span>
	                     </span>
	                  </div>
	               </div>
	               <div class="gray-xs-f mb-sm mt-xlg">Lifetime of the run and of the questionnaire</div>
	               <div class="mt-sm">
	                  <span class="checkbox checkbox-inline">
	                  <input type="checkbox" id="isStudyLifeTime" name="questionnairesFrequenciesBo.isStudyLifeTime" value="true" ${questionnaireBo.questionnairesFrequenciesBo.isStudyLifeTime ?'checked':''} required="required" disabled="disabled">
	                  <label for="isStudyLifeTime"> Study Lifetime</label>
	                  </span>
	                  <div class="mt-md form-group">
	                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                     <input id="chooseEndDate" type="text" class="form-control calendar" name="studyLifetimeEnd" placeholder="Choose End Date" required <c:if test="${questionnaireBo.questionnairesFrequenciesBo.isStudyLifeTime }"> disabled </c:if> value="${questionnaireBo.studyLifetimeEnd}"/>
	                     <span class='help-block with-errors red-txt'></span>
	                     </span>                            
	                  </div>
	               </div>
	            </div>
            </form:form>
            <!-- Daily Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="dailyFormId" id="dailyFormId" method="post" role="form">
	           	 <input type="hidden" name="frequency" id="dailyFrequencyId" value="${questionnaireBo.frequency}">
	           	 <input type="hidden" name="previousFrequency" id="previousFrequency" value="${questionnaireBo.frequency}">
	             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
	             <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
	              <input type="hidden" name="type" id="type" value="schedule">
	            <div class="daily all mt-xlg dis-none">
	               <div class="gray-xs-f mb-sm">Time(s) of the day for daily occurrence</div>
	               <div class="dailyContainer">
	               <c:if test="${fn:length(questionnaireBo.questionnairesFrequenciesList) eq 0}">
		               <div class="time-opts mt-md dailyTimeDiv" id="0">
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="time0" type="text" name="questionnairesFrequenciesList[0].frequencyTime" required class="form-control clock dailyClock" placeholder="Time" onclick ='timep(this.id);'/>
		                  <span class='help-block with-errors red-txt'></span>
		                  </span> 
		                  <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTime();'>+</span>
		                  <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTime(this);'></span>
		               </div>
	               </c:if>
	               <c:if test="${fn:length(questionnaireBo.questionnairesFrequenciesList) gt 0}">
		                <c:forEach items="${questionnaireBo.questionnairesFrequenciesList}" var="questionnairesFrequencies" varStatus="frequeincesVar">
			                <div class="time-opts mt-md dailyTimeDiv" id="${frequeincesVar.index}">
			                <input type="hidden" name="questionnairesFrequenciesList[${frequeincesVar.index}].id" value="${questionnairesFrequencies.id}">
			                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
			                  <input id="time${frequeincesVar.index}" type="text" name="questionnairesFrequenciesList[${frequeincesVar.index}].frequencyTime" required class="form-control clock dailyClock" placeholder="Time" onclick ='timep(this.id);' value="${questionnairesFrequencies.frequencyTime}"/>
			                  <span class='help-block with-errors red-txt'></span>
			                  </span> 
			                  <span class="addBtnDis addbtn mr-sm align-span-center" onclick='addTime();'>+</span>
			                  <span class="delete vertical-align-middle remBtnDis hide pl-md align-span-center" onclick='removeTime(this);'></span>
			               </div>
		                </c:forEach>
	               </c:if>
	               </div>
	               <div class="mt-xlg">                        
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                          
	                  <input id="startDate" type="text" class="form-control mt-sm calendar" placeholder="Choose Date" required name="studyLifetimeStart" value="${questionnaireBo.studyLifetimeStart}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">No. of days to repeat the questionnaire</span><br/>
	                  <input id="days" type="text" class="form-control mt-sm" name="repeatQuestionnaire" placeholder="No of Days"required value="${questionnaireBo.repeatQuestionnaire}" onkeypress="return isNumber(event)"/>
	                   <span class='help-block with-errors red-txt'></span>
	                  </span>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">End Date </div>
	                  <div class="black-xs-f" id="endDateId">${not empty questionnaireBo.studyLifetimeEnd ? questionnaireBo.studyLifetimeEnd :'NA'}</div>
	                  <input type="hidden" name="studyLifetimeEnd" id="studyDailyLifetimeEnd" value="${questionnaireBo.studyLifetimeEnd}">
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
	                  <div class="black-xs-f">Until the next run comes up</div>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
	                  <div class="black-xs-f" id="lifeTimeId">${questionnaireBo.studyLifetimeStart}  -  ${questionnaireBo.studyLifetimeEnd}</div>
	               </div>
	            </div> 
            </form:form>
            <!-- Weekly Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="weeklyFormId" id="weeklyFormId" method="post" role="form">
	             <input type="hidden" name="frequency" id="weeklyfrequencyId">
	             <input type="hidden" name="previousFrequency" id="previousFrequency" value="${questionnaireBo.frequency}">
	             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
	             <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
	             <input type="hidden" name="questionnairesFrequenciesBo.id" id="weeklyFreId" value="${questionnaireBo.questionnairesFrequenciesBo.id}">
	             <input type="hidden" name="type" id="type" value="schedule">
	            <div class="week all mt-xlg dis-none">
	               <div>                        
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">Day/Time (of the week)</span><br/>
	                  <select id="startDateWeekly" class="form-control mt-sm" name="dayOfTheWeek" required>
		                  <option value=''>Select</option>
		                  <option value='Sunday' ${questionnaireBo.dayOfTheWeek eq 'Sunday' ? 'selected':''}>Sunday</option>
		                  <option value='Monday' ${questionnaireBo.dayOfTheWeek eq 'Monday' ?'selected':''}>Monday</option>
		                  <option value='Tuesday' ${questionnaireBo.dayOfTheWeek eq 'Tuesday' ?'selected':''}>Tuesday</option>
		                  <option value='Wednesday' ${questionnaireBo.dayOfTheWeek eq 'Wednesday' ?'selected':''}>Wednesday</option>
		                  <option value='Thursday' ${questionnaireBo.dayOfTheWeek eq 'Thursday' ?'selected':''}>Thursday</option>
		                  <option value='Friday' ${questionnaireBo.dayOfTheWeek eq 'Friday' ?'selected':''}>Friday </option>
		                  <option value='Saturday' ${questionnaireBo.dayOfTheWeek eq 'Saturday' ?'selected':''}>Saturday</option>
	                  </select>   
	                  <span class='help-block with-errors red-txt'></span>                         
	                  </span>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">&nbsp;</span><br/>
	                  <input id="selectWeeklyTime" type="text" class="form-control mt-sm clock" required onclick="timep(this.id)" placeholder="Time" name="questionnairesFrequenciesBo.frequencyTime" value="${questionnaireBo.questionnairesFrequenciesBo.frequencyTime}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>                        
	               </div>
	               <div class="mt-xlg">                        
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                           
	                  <input id="startWeeklyDate" type="text" class="form-control mt-sm calendar" required name="studyLifetimeStart"  placeholder="Choose Date" value="${questionnaireBo.studyLifetimeStart}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">No. of weeks to repeat the questionnaire</span><br/>
	                  <input id="weeks" type="text" class="form-control mt-sm" name="repeatQuestionnaire"  placeholder="No of Weeks" value="${questionnaireBo.repeatQuestionnaire}" required onkeypress="return isNumber(event)"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">End Date </div>
	                  <div class="black-xs-f" id="weekEndDate">${not empty questionnaireBo.studyLifetimeEnd ? questionnaireBo.studyLifetimeEnd :'NA'}</div>
	                  <input type="hidden" name="studyLifetimeEnd" id="studyWeeklyLifetimeEnd" value="${questionnaireBo.studyLifetimeEnd}">
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
	                  <div class="black-xs-f">Until the next run comes up</div>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
	                  <div class="black-xs-f" id="weekLifeTimeEnd">${questionnaireBo.studyLifetimeStart}  -  ${questionnaireBo.studyLifetimeEnd}</div>
	               </div>
	            </div> 
            </form:form>
            <!-- Monthly Section-->   
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="monthlyFormId" id="monthlyFormId" method="post" role="form"> 
	            <input type="hidden" name="frequency" id="monthlyfrequencyId" value="${questionnaireBo.frequency}">
	            <input type="hidden" name="previousFrequency" id="previousFrequency" value="${questionnaireBo.frequency}">
	            <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
	            <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
	            <input type="hidden" name="questionnairesFrequenciesBo.id" id="monthFreId" value="${questionnaireBo.questionnairesFrequenciesBo.id}">
	             <input type="hidden" name="type" id="type" value="schedule">
	            <div class="month all mt-xlg dis-none">
	               <div>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">Select Date/Time (of the month)</span><br/>                            
	                  <input id="startDateMonthly" type="text" class="form-control mt-sm calendar" required  placeholder="Choose Date" name="questionnairesFrequenciesBo.frequencyDate" value="${questionnaireBo.questionnairesFrequenciesBo.frequencyDate}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">&nbsp;</span><br/>
	                  <input id="selectMonthlyTime" type="text" class="form-control mt-sm clock" required onclick="timep(this.id)"  placeholder="Time" name="questionnairesFrequenciesBo.frequencyTime" value="${questionnaireBo.questionnairesFrequenciesBo.frequencyTime}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	                  <div class="gray-xs-f mt-xs italic-txt text-weight-light">If the selected date is not available in a month, the last day of the month will be used instead</div>
	               </div>
	               <div class="mt-xlg">                        
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">Start date (pick a date)</span><br/>      
	                  <input id="pickStartDate" type="text" class="form-control mt-sm calendar"  placeholder="Choose Start Date" required name="studyLifetimeStart" value="${questionnaireBo.studyLifetimeStart}"/>
	                  <span class='help-block with-errors red-txt'></span>
	                  </span>
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <span class="gray-xs-f">No. of months to repeat the questionnaire</span><br/>
	                  <input id="months" type="text" class="form-control mt-sm" name="repeatQuestionnaire"  placeholder="No of Months" required value="${questionnaireBo.repeatQuestionnaire}" onkeypress="return isNumber(event)" />
	                   <span class='help-block with-errors red-txt'></span>
	                  </span>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">End Date </div>
	                  <div class="black-xs-f" id="monthEndDate">${not empty questionnaireBo.studyLifetimeEnd ? questionnaireBo.studyLifetimeEnd :'NA'}</div>
	                  <input type="hidden" name="studyLifetimeEnd" id="studyMonthlyLifetimeEnd" value="${questionnaireBo.studyLifetimeEnd}">
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
	                  <div class="black-xs-f">Until the next run comes up</div>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
	                  <div class="black-xs-f" id="monthLifeTimeDate">${questionnaireBo.studyLifetimeStart}  -  ${questionnaireBo.studyLifetimeEnd}</div>
	               </div>
	            </div> 
            </form:form>
            <!-- Manually Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="customFormId" id="customFormId" method="post" role="form">
	           <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
               <input type="hidden" name="studyId" id="studyId" value="${not empty questionnaireBo.studyId ? questionnaireBo.studyId : studyBo.id}">
               <input type="hidden" name="frequency" id="customfrequencyId" value="${questionnaireBo.frequency}">
               <input type="hidden" name="previousFrequency" id="previousFrequency" value="${questionnaireBo.frequency}">
                <input type="hidden" name="type" id="type" value="schedule">
	           <div class="manually all mt-xlg dis-none">
	               <div class="gray-xs-f mb-sm">Select time period</div>
	               <div class="manuallyContainer">
	                 <c:if test="${fn:length(questionnaireBo.questionnaireCustomScheduleBo) eq 0}">
	                 	<div class="manually-option mb-md form-group" id="0" >
	                 	  <input type="hidden" name="questionnaireCustomScheduleBo[0].questionnairesId" id="questionnairesId" value="${questionnaireBo.id}">
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="StartDate0" type="text" count='0' class="form-control calendar customCalnder cusStrDate" name="questionnaireCustomScheduleBo[0].frequencyStartDate" value="" placeholder="Start Date" onclick='customStartDate(this.id,0);' required/>
		                  <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="gray-xs-f mb-sm pr-md align-span-center">
		                  to 
		                  </span>
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="EndDate0" type="text" count='0' class="form-control calendar customCalnder cusEndDate" name="questionnaireCustomScheduleBo[0].frequencyEndDate" placeholder="End Date" onclick='customEndDate(this.id,0);' required/>
		                   <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="customTime0" type="text" count='0' class="form-control clock cusTime" name="questionnaireCustomScheduleBo[0].frequencyTime" placeholder="Time" onclick='timep(this.id);' disabled required/>
		                   <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="addbtn addBtnDis align-span-center" onclick="addDate();">+</span>
		                  <span id="delete" class="sprites_icon delete vertical-align-middle remBtnDis hide align-span-center" onclick="removeDate(this);"></span>
		               </div>
	                 </c:if>
	                 <c:if test="${fn:length(questionnaireBo.questionnaireCustomScheduleBo) gt 0}">
	                 	<c:forEach items="${questionnaireBo.questionnaireCustomScheduleBo}" var="questionnaireCustomScheduleBo" varStatus="customVar">
		                  <div class="manually-option mb-md form-group" id="${customVar.index}">
		                  	  <input type="hidden" name="questionnaireCustomScheduleBo[${customVar.index}].id" id="id" value="${questionnaireCustomScheduleBo.id}">
	                 	  	  <input type="hidden" name="questionnaireCustomScheduleBo[${customVar.index}].questionnairesId" id="questionnairesId" value="${questionnaireCustomScheduleBo.questionnairesId}">
			                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
			                  <input id="StartDate${customVar.index}" type="text" count='${customVar.index}' class="form-control calendar cusStrDate" name="questionnaireCustomScheduleBo[${customVar.index}].frequencyStartDate" value="${questionnaireCustomScheduleBo.frequencyStartDate}" placeholder="Start Date" onclick='customStartDate(this.id,${customVar.index});' required/>
			                  <span class='help-block with-errors red-txt'></span>
			                  </span>
			                  <span class="gray-xs-f mb-sm pr-md align-span-center">
			                  to 
			                  </span>
			                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
			                  <input id="EndDate${customVar.index}" type="text" count='${customVar.index}' class="form-control calendar cusEndDate" name="questionnaireCustomScheduleBo[${customVar.index}].frequencyEndDate" value="${questionnaireCustomScheduleBo.frequencyEndDate}" placeholder="End Date" onclick='customEndDate(this.id,${customVar.index});' required/>
			                   <span class='help-block with-errors red-txt'></span>
			                  </span>
			                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
			                  <input id="customTime${customVar.index}" type="text" count='${customVar.index}' class="form-control clock cusTime" name="questionnaireCustomScheduleBo[${customVar.index}].frequencyTime" value="${questionnaireCustomScheduleBo.frequencyTime}" placeholder="Time" onclick='timep(this.id);' required/>
			                   <span class='help-block with-errors red-txt'></span>
			                  </span>
			                  <span class="addbtn addBtnDis align-span-center" onclick="addDate();">+</span>
			                  <span id="delete" class="sprites_icon delete vertical-align-middle remBtnDis hide align-span-center" onclick="removeDate(this);"></span>
			               </div>
	                 	</c:forEach>
	                 </c:if>
	               </div>
	               <div class="mt-xlg">
	                  <div class="gray-xs-f mb-xs">Default Lifetime of each run </div>
	                  <div class="black-xs-f">As defined by the start and end times selected above</div>
	               </div>
	            </div>
            </form:form> 
         </div>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
var count = 0;
var customCount = 0;
var frequencey = "${questionnaireBo.frequency}";
customCount = '${customCount}';
count = Number('${count}');
var isValidManuallySchedule = true;
var multiTimeVal = true;
$(document).ready(function() {
	$(".menuNav li.active").removeClass('active');
	$(".sixthQuestionnaires").addClass('active');
	checkDateRange();
	customStartDate('StartDate'+customCount,customCount);
	customEndDate('EndDate'+customCount,customCount);
	if($('.time-opts').length > 1){
		$('.dailyContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.dailyContainer').find(".remBtnDis").addClass("hide");
	}
	if($('.manually-option').length > 1){
		$('.manuallyContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.manuallyContainer').find(".remBtnDis").addClass("hide");
	}
    var viewPermission = "${permission}";
    var reorder = true;
    if(viewPermission == 'view'){
        reorder = false;
    }else{
    	reorder = true;
    } 
   var table1 = $('#content').DataTable( {
	    "paging":false,
	    "info": false,
	    "filter": false,
	     rowReorder: reorder,
         "columnDefs": [ { orderable: false, targets: [0,1,2,3] } ],
	     "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
	    	 if(viewPermission != 'view'){
	    		$('td:eq(0)', nRow).addClass("cursonMove dd_icon");
	    	 } 
	    	 $('td:eq(0)', nRow).addClass("qs-items");
	    	 $('td:eq(1)', nRow).addClass("qs-items");
	    	 $('td:eq(2)', nRow).addClass("qs-items");
	    	 $('td:eq(3)', nRow).addClass("qs-items");
	      }
	});  
   table1.on( 'row-reorder', function ( e, diff, edit ) {
		var oldOrderNumber = '', newOrderNumber = '';
		var oldClass='',newclass='';
	    var result = 'Reorder started on row: '+edit.triggerRow.data()[1]+'<br>';
		var studyId = $("#studyId").val();
		var questionnaireId = $("#id").val();
	    for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
	        var rowData = table1.row( diff[i].node ).data();
	        if(i==0){
	        	oldOrderNumber = $(diff[i].oldData).attr('id');
	            newOrderNumber = $(diff[i].newData).attr('id');
	            oldClass = $(diff[i].oldData).attr('class');
	            newclass = $(diff[i].newData).attr('class');
	        }
	        result += rowData[1]+' updated to be in position '+
	            diff[i].newData+' (was '+diff[i].oldData+')<br>';
	    }

	    console.log('oldOrderNumber:'+oldOrderNumber);
	    console.log('newOrderNumber:'+newOrderNumber);
	    console.log('studyId:'+studyId);
	    console.log("questionnaireId:"+questionnaireId);
	    console.log('oldClass:'+oldClass);
	    console.log('newclass:'+newclass);
	    
	    if(oldOrderNumber !== undefined && oldOrderNumber != null && oldOrderNumber != "" 
			&& newOrderNumber !== undefined && newOrderNumber != null && newOrderNumber != ""){
	    	$("#"+oldOrderNumber).addClass(oldClass);
	 	    $("#"+newOrderNumber).addClass(newclass);
	    	$.ajax({
				url: "/fdahpStudyDesigner/adminStudies/reOrderQuestionnaireStepInfo.do",
				type: "POST",
				datatype: "json",
				data:{
					questionnaireId : questionnaireId,
					oldOrderNumber: oldOrderNumber,
					newOrderNumber : newOrderNumber,
					"${_csrf.parameterName}":"${_csrf.token}",
				},
				success: function consentInfo(data){
					var status = data.message;
					if(status == "SUCCESS"){
						$('#alertMsg').show();
						$("#alertMsg").removeClass('e-box').addClass('s-box').html("Reorder done successfully");
					}else{
						$('#alertMsg').show();
						$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to reorder questionnaire");
		            }
					setTimeout(hideDisplayMessage, 4000);
				},
				error: function(xhr, status, error) {
				  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
				  setTimeout(hideDisplayMessage, 4000);
				}
			}); 
	    }
	});
	console.log("customCount:"+customCount)
	if(document.getElementById("doneId") != null && document.getElementById("doneId").disabled){
 		$('[data-toggle="tooltip"]').tooltip();
 	}
	//var previousFrequency = $("previousFrequency").val();
	$(".schedule").change(function() {
        $(".all").addClass("dis-none");
        var schedule_opts = $(this).attr('frequencytype');
        var val = $(this).val();
        console.log("val:"+val);
        $("." + schedule_opts).removeClass("dis-none");
        resetValidation($("#oneTimeFormId"));
        resetValidation($("#customFormId"));
        resetValidation($("#dailyFormId"));
        resetValidation($("#weeklyFormId"));
        resetValidation($("#monthlyFormId"));
        if((frequencey != null && frequencey != "" && typeof frequencey != 'undefined')){
        	if(frequencey != val){
        		if(val == 'One time'){
        			$("#chooseDate").val('');
        			$("#selectTime").val('');
        			$("#chooseEndDate").val('');
        			$("#oneTimeFreId").val('');
        			$("#isLaunchStudy").val('');
        			$("#isStudyLifeTime").val('');
            	}else if(val == 'Manually Schedule'){
            		$('.manually').find('input:text').val('');    
            		isValidManuallySchedule = true;
            		$('.manually-option:not(:first)').find('.remBtnDis').click();
            		$('.manually-option').find('input').val('');
            	}else if(val == 'Daily'){
            		$("#startDate").val('');
            		$("#days").val('');
            		$("#endDateId").text('NA');
            		$("#lifeTimeId").text('-');
            		$('.dailyClock').val('');
            		$('.dailyClock:not(:first)').parent().parent().remove();
            		multiTimeVal = true;
            	}else if(val == 'Weekly'){
            		$("#startDateWeekly").val('');
            		$("#weeklyFreId").val('');
            		$("#questionnairesFrequenciesBo.frequencyTime").val('');
            		$("#startWeeklyDate").val('');
            		$("#weeks").val('');
            		$("#weekEndDate").text('NA');
            		$("#weekLifeTimeEnd").text('-');
            		$("#selectWeeklyTime").val('');
            	}else if(val == 'Monthly'){
            		$("#monthFreId").val('');
            		$("#selectMonthlyTime").val('');
            		$("#pickStartDate").val('');
            		$("#months").val('');
            		$("#monthEndDate").text('NA');
            		$("#monthLifeTimeDate").text('-');
            	}
        	}
        }else{
    		$('.oneTime').find('input:text').val(''); 
    		$(".daily").find('input:text').val('');    
    		$(".week").find('input:text').val('');    
    		$("#startDateWeekly").val('');
    		$(".month").find('input:text').val('');    
    		$('.manually').find('input:text').val('');    
    		$("#isLaunchStudy").val('');
			$("#isStudyLifeTime").val('');
			$("#monthEndDate").text('NA');
    		$("#monthLifeTimeDate").text('-');
    		$("#weekEndDate").text('NA');
    		$("#weekLifeTimeEnd").text('-');
    		$("#endDateId").text('NA');
    		$("#lifeTimeId").text('-');
    		$('.manually-option:not(:first)').find('.remBtnDis').click();
         	$('.manually-option').find('input').val('');
         	$('.dailyClock').val('');
            $('.dailyClock:not(:first)').parent().parent().remove();
        }
    });
    console.log("frequencey:"+frequencey);
    if(frequencey != null && frequencey != "" && typeof frequencey != 'undefined'){
    	$(".all").addClass("dis-none");
    	if(frequencey == 'One time'){
    		$(".oneTime").removeClass("dis-none");
    	}else if(frequencey == 'Manually Schedule'){
    		$(".manually").removeClass("dis-none");
    	}else if(frequencey == 'Daily'){
    		$(".daily").removeClass("dis-none");
    	}else if(frequencey == 'Weekly'){
    		$(".week").removeClass("dis-none");
    	}else if(frequencey == 'Monthly'){
    		$(".month").removeClass("dis-none");
    	}
    }
    
    $('#chooseDate').datetimepicker({
        format: 'MM/DD/YYYY',
        //minDate: new Date(),
    }).on("click", function (e) {
        $('#chooseDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    }).on("dp.change", function (e) {
    	$('#chooseDate').parent().removeClass("has-danger").removeClass("has-error");
        $('#chooseDate').parent().find(".help-block").html("");
        $("#chooseEndDate").parent().removeClass("has-danger").removeClass("has-error");
        $("#chooseEndDate").parent().find(".help-block").html("");
    	var startDate = $("#chooseDate").val();
        var endDate = $('#chooseEndDate').val();
        console.log("startDate:"+startDate);
        console.log("endDate:"+endDate);
        if(startDate!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
        	$('#chooseDate').parent().addClass("has-danger").addClass("has-error");
       	    $('#chooseDate').parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date and Time Should not be less than Start Date and Time</li></ul>');
       	    $('#chooseDate').val('');
        }else{
        	$('#chooseDate').parent().removeClass("has-danger").removeClass("has-error");
            $('#chooseDate').parent().find(".help-block").html("");
            $("#chooseEndDate").parent().removeClass("has-danger").removeClass("has-error");
            $("#chooseEndDate").parent().find(".help-block").html("");
        }
    });
    
    $(document).on('change dp.change', '.dailyClock', function() {
    	var chkVal = true;
		var thisDailyTimeDiv = $(this).parents('.dailyTimeDiv');
		var thisAttr = $(this);
		$(this).parents('.dailyContainer').find('.dailyTimeDiv').each(function() {
			if(!thisDailyTimeDiv.is($(this)) && $(this).find('.dailyClock').val()) {
				if($(this).find('.dailyClock').val() == thisAttr.val()) {
					if(chkVal)
						chkVal = false;
				}
			}
		});
		if(!chkVal) {
			thisAttr.parents('.dailyTimeDiv').find('.dailyClock').parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date and Time Should not be less than Start Date and Time</li></ul>');
		} else {
			thisAttr.parents('.dailyTimeDiv').find('.dailyClock').parent().find(".help-block").html('');
		}
		var a = 0;
		$('.dailyContainer').find('.dailyTimeDiv').each(function() {
			if($(this).find('.dailyClock').parent().find('.help-block.with-errors').children().length > 0) {
				a++;
			}
		});
		multiTimeVal = !(a > 0);
	});
    
    $('#chooseEndDate').datetimepicker({
        format: 'MM/DD/YYYY',
        //minDate: new Date(),
    }).on("click", function (e) {
        $('#chooseEndDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    }).on("dp.change", function (e) {
    	$('#chooseEndDate').parent().removeClass("has-danger").removeClass("has-error");
        $('#chooseEndDate').parent().find(".help-block").html("");
        $("#chooseDate").parent().removeClass("has-danger").removeClass("has-error");
        $("#chooseDate").parent().find(".help-block").html("");
    	var startDate = $("#chooseDate").val();
        var endDate = $('#chooseEndDate').val();
        console.log("startDate:"+startDate);
        console.log("endDate:"+endDate);
        if(startDate!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
        	$('#chooseEndDate').parent().addClass("has-danger").addClass("has-error");
       	    $('#chooseEndDate').parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date and Time Should not be less than Start Date and Time</li></ul>');
       	    $('#chooseEndDate').val();
        }else{
        	$('#chooseEndDate').parent().removeClass("has-danger").removeClass("has-error");
            $('#chooseEndDate').parent().find(".help-block").html("");
            $("#chooseDate").parent().removeClass("has-danger").removeClass("has-error");
            $("#chooseDate").parent().find(".help-block").html("");
        }
    });
    
    
    $('#startDate').datetimepicker({
        format: 'MM/DD/YYYY',
       // minDate: new Date(),
    }).on("dp.change", function (e) {
    	var startDate = $("#startDate").val();
    	var days = $("#days").val();
    	var endDate = ''
    	if((startDate != null && startDate != '' && typeof startDate != 'undefined') && (days != null && days != '' && typeof days != 'undefined')){
    		var dt = new Date(startDate);
            dt.setDate(dt.getDate() + Number(days));	
            endDate = formatDate(dt);
            $("#studyDailyLifetimeEnd").val(endDate);
            $("#lifeTimeId").text(startDate+' - '+endDate);
            $("#endDateId").text(endDate);
    	}
    }).on("click", function (e) {
        $('#startDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    });
    $('#startDateMonthly').datetimepicker({
        format: 'MM/DD/YYYY',
       // minDate: new Date(),
    }).on("click", function (e) {
        $('#startDateMonthly').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    }).on("dp.change",function(e){
    	//$('#pickStartDate').data("DateTimePicker").minDate(e.date);
    	if(e.date._d != $('#pickStartDate').data("DateTimePicker").date()) {
    		$('#pickStartDate').val('');
    	}
    	var dateArr = []; 
	    for(var i = new Date(e.date._d).getFullYear(); i < 2108 ; i++) {
	    	for(var j= 0; j < 12 ; j++) {
	    		dateArr.push(new Date(i, j ,new Date(e.date._d).getDate()));
	    	}
	    }
    	 $('#pickStartDate').data("DateTimePicker").enabledDates(dateArr);
    	//$('#pickStartDate').data("DateTimePicker").enabledDates([ moment(e.date), new Date(2020, 4 - 1, 3), "4/4/2014 00:53" ]);
    });
    
    $(".clock").datetimepicker({
    	 format: 'HH:mm',
    });
    
    $(document).on('dp.change', '.cusStrDate', function(e) {
    	var nxtDate = moment(new Date(e.date._d)).add(1, 'days');
    	if(!$(this).parents('.manually-option').find('.cusEndDate').data("DateTimePicker")){
    		customEndDate($(this).parents('.manually-option').find('.cusEndDate').attr('id') ,0);
    	}
		$(this).parents('.manually-option').find('.cusEndDate').data("DateTimePicker").minDate(nxtDate);
	});
	$(document).on('dp.change change', '.cusStrDate, .cusEndDate', function() {
		if($(this).parents('.manually-option').find('.cusStrDate').val() && $(this).parents('.manually-option').find('.cusEndDate').val()) {
			$(this).parents('.manually-option').find('.cusTime').prop('disabled', false);
		} else {
			$(this).parents('.manually-option').find('.cusTime').prop('disabled', true);
		}
	});
	
    $('#pickStartDate').datetimepicker({
        format: 'MM/DD/YYYY',
        
        //minDate: new Date(),
    }).on("dp.change",function(e){
    	var pickStartDate = $("#pickStartDate").val();
    	var months = $("#months").val();
    	if((pickStartDate != null && pickStartDate != '' && typeof pickStartDate != 'undefined') && (months != null && months != '' && typeof months != 'undefined')){
    		var dt = new Date(pickStartDate);
    		var monthCount = Number(months)*30;
    		console.log(monthCount)
            dt.setDate(dt.getDate() + Number(monthCount));	
            endDate = formatDate(dt);
            $("#studyMonthlyLifetimeEnd").val(endDate);
            $("#monthEndDate").text(endDate);
            $("#monthLifeTimeDate").text(pickStartDate+' - '+endDate);
    	}
    }).on("click", function (e) {
        $('#pickStartDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    });
    $('#startWeeklyDate').datetimepicker({
        format: 'MM/DD/YYYY',
       // minDate: new Date(),
    }).on("dp.change", function (e) {
    	var weeklyDate = $("#startWeeklyDate").val();
    	var weeks = $("#weeks").val();
    	console.log("weeklyDate:"+weeklyDate);
    	console.log("weeks:"+weeks);
    	if((weeklyDate != null && weeklyDate != '' && typeof weeklyDate != 'undefined') && (weeks != null && weeks != '' && typeof weeks != 'undefined')){
    		var dt = new Date(weeklyDate);
    		var weekcount = Number(weeks)*7;
    		console.log(weekcount)
            dt.setDate(dt.getDate() + Number(weekcount));	
            endDate = formatDate(dt);
            $("#studyWeeklyLifetimeEnd").val(endDate);
            $("#weekEndDate").text(endDate);
            $("#weekLifeTimeEnd").text(weeklyDate+' - '+endDate);
    	}
    }).on("click", function (e) {
        $('#startWeeklyDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()));
    });
    $('.customCalnder').datetimepicker({
        format: 'MM/DD/YYYY',
        minDate: new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()),
    }); 
    var daysOfWeek = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
    $("#startDateWeekly").on('change', function(){
    	var weekDay = $("#startDateWeekly").val();
    	var weeks=[];
    	for(var i=0;i<daysOfWeek.length;i++){
    		if(weekDay != daysOfWeek[i]){
    			weeks.push(i);		
        	}    		
    	}
    	$('#startWeeklyDate').data("DateTimePicker").destroy();
    	$('#startWeeklyDate').datetimepicker({
            format: 'MM/DD/YYYY',
            minDate: new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()),
            daysOfWeekDisabled: weeks
        }).on("dp.change", function (e) {
        	var weeklyDate = $("#startWeeklyDate").val();
        	var weeks = $("#weeks").val();
        	console.log("weeklyDate:"+weeklyDate);
        	console.log("weeks:"+weeks);
        	if((weeklyDate != null && weeklyDate != '' && typeof weeklyDate != 'undefined') && (weeks != null && weeks != '' && typeof weeks != 'undefined')){
        		var dt = new Date(weeklyDate);
        		var weekcount = Number(weeks)*7;
        		console.log(weekcount)
                dt.setDate(dt.getDate() + Number(weekcount));	
                endDate = formatDate(dt);
                $("#studyWeeklyLifetimeEnd").val(endDate);
                $("#weekEndDate").text(endDate);
                $("#weekLifeTimeEnd").text(weeklyDate+' - '+endDate);
        	}
        });
    	$('#startWeeklyDate').val('');
    });
	$("#doneId").click(function(){
		var table = $('#content').DataTable();
		/* if (!table.data().count() ) {
			console.log( 'Add atleast one consent !' );
			$('#alertMsg').show();
			$("#alertMsg").removeClass('s-box').addClass('e-box').html("Add atleat one questionnaire Step");
			setTimeout(hideDisplayMessage, 4000);
		}else{ */
			if(isFromValid("#contentFormId")){
				doneQuestionnaire(this, 'done', function(val) {
					if(val) {
						document.contentFormId.submit();
					}
				});
			}else{
				$('.contentqusClass a').tab('show');
			}
	//	}
	 });
//     $("#doneId").click(function(){
//     	var frequency = $('input[name="frequency"]:checked').val();
//     	console.log("frequency:"+frequency)
    	
    	
//     	if(isFromValid("#contentFormId")){
// 			document.contentFormId.submit();    
// 			console.log(isFromValid("#contentFormId"));
// 		}
//     	if(frequency == 'One time'){
//     		$("#frequencyId").val(frequency);
//     		if(isFromValid("#oneTimeFormId")){
//     			document.oneTimeFormId.submit();    
//     			console.log(isFromValid("#oneTimeFormId"));
//     		}
//     	}else if(frequency == 'Manually Schedule'){
//     		$("#customfrequencyId").val(frequency);
//     		if(isFromValid("#customFormId")){
//     			document.customFormId.submit();
//     		}
//     	}else if(frequency == 'Daily'){
//     		$("#dailyFrequencyId").val(frequency);
//     		if(isFromValid("#dailyFormId")){
//     			document.dailyFormId.submit();
//     		}
//     	}else if(frequency == 'Weekly'){
//     		$("#weeklyfrequencyId").val(frequency);
//     		if(isFromValid("#weeklyFormId")){
//     			document.weeklyFormId.submit();
//     		}
//     	}else if(frequency == 'Monthly'){
//     		$("#monthlyfrequencyId").val(frequency);
//     		if(isFromValid("#monthlyFormId")){
//     			document.monthlyFormId.submit();
//     		}
//     	} 
    	
//     });
   
    $("#days").on('change',function(){
    	console.log("change");
    	var startDate = $("#startDate").val();
    	var days = $("#days").val();
    	var endDate = ''
    	if((startDate != null && startDate != '' && typeof startDate != 'undefined') && (days != null && days != '' && typeof days != 'undefined')){
    		var dt = new Date(startDate);
            dt.setDate(dt.getDate() + Number(days));	
            endDate = formatDate(dt);
            $("#studyDailyLifetimeEnd").val(endDate);
            $("#lifeTimeId").text(startDate+' - '+endDate);
            $("#endDateId").text(endDate);
    	}
    })
    
    $("#weeks").on('change',function(){
    	var weeklyDate = $("#startWeeklyDate").val();
    	var weeks = $("#weeks").val();
    	console.log("weeklyDate:"+weeklyDate);
    	console.log("weeks:"+weeks);
    	if((weeklyDate != null && weeklyDate != '' && typeof weeklyDate != 'undefined') && (weeks != null && weeks != '' && typeof weeks != 'undefined')){
    		var dt = new Date(weeklyDate);
    		var weekcount = Number(weeks)*7;
    		console.log(weekcount)
            dt.setDate(dt.getDate() + Number(weekcount));	
            endDate = formatDate(dt);
            $("#studyWeeklyLifetimeEnd").val(endDate);
            $("#weekEndDate").text(endDate);
            $("#weekLifeTimeEnd").text(weeklyDate+' - '+endDate);
    	}
    });
    $("#months").on('change',function(){
    	var pickStartDate = $("#pickStartDate").val();
    	var months = $("#months").val();
    	if((pickStartDate != null && pickStartDate != '' && typeof pickStartDate != 'undefined') && (months != null && months != '' && typeof months != 'undefined')){
    		var dt = new Date(pickStartDate);
    		var monthCount = Number(months)*30;
    		console.log(monthCount)
            dt.setDate(dt.getDate() + Number(monthCount));	
            endDate = formatDate(dt);
            $("#studyMonthlyLifetimeEnd").val(endDate);
            $("#monthEndDate").text(endDate);
            $("#monthLifeTimeDate").text(pickStartDate+' - '+endDate);
    	}
    });
    $("#isLaunchStudy").change(function(){
    	if(!$("#isLaunchStudy").is(':checked')){
    		$("#chooseDate").attr("disabled",false);
    		$("#selectTime").attr("disabled",false);
    		$("#chooseDate").required = false;
    		$("#selectTime").required = false;
    	}else{
    		$("#chooseDate").attr("disabled",true);
    		$("#selectTime").attr("disabled",true);
    		$("#chooseDate").required = true;
    		$("#selectTime").required = true;
    	}
    });
    $("#isStudyLifeTime").change(function(){
    	if(!$("#isStudyLifeTime").is(':checked')){
    		$("#chooseEndDate").attr("disabled",false);
    		$("#chooseEndDate").required = false;
    	}else{
    		$("#chooseEndDate").attr("disabled",true);
    		$("#chooseEndDate").required = true;
    	}
    });
    $("#shortTitleId").blur(function(){
    	var shortTitle = $(this).val();
    	var studyId = $("#studyId").val();
    	var thisAttr= this;
    	var existedKey = '${questionnaireBo.shortTitle}';
    	if(shortTitle != null && shortTitle !='' && typeof shortTitle!= 'undefined'){
    		if( existedKey !=shortTitle){
    		$.ajax({
                url: "/fdahpStudyDesigner/adminStudies/validateQuestionnaireKey.do",
                type: "POST",
                datatype: "json",
                data: {
                	shortTitle : shortTitle,
                	studyId : studyId
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
    
    // Branching Logic starts here
    
    $("#branchingId").change(function(){
    	if($("#branchingId").is(':checked')){
    		$(".deleteStepButton").hide();
    		$(".destinationStep").show();
    		table1.rowReorder.disable();
    	}else{
    		$(".deleteStepButton").show();
    		$(".destinationStep").hide();
    		table1.rowReorder.enable();
    	}
    });
    var branching = "${questionnaireBo.branching}";
    if(branching == "true"){
    	$(".destinationStep").show();
    }else{
   		$(".destinationStep").hide();
    }
    // Branching Logic starts here
    $('[data-toggle="tooltip"]').tooltip();
    
    
});
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [month, day, year].join('/');
}
function addTime(){
	count = count +1;
	var newTime = "<div class='time-opts mt-md dailyTimeDiv' id="+count+">"+
				  "  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"+
				  "  <input id='time"+count+"' type='text' required name='questionnairesFrequenciesList["+count+"].frequencyTime' placeholder='Time' class='form-control clock dailyClock' placeholder='00:00' onclick='timep(this.id);'/>"+
				  "<span class='help-block with-errors red-txt'></span>"+
				  " </span>"+ 
				  "  <span class='addBtnDis addbtn mr-sm align-span-center' onclick='addTime();'>+</span>"+
				  " <span class='delete vertical-align-middle remBtnDis hide pl-md align-span-center' onclick='removeTime(this);'></span>"+
				 "</div>";
	$(".time-opts:last").after(newTime);
	$(".time-opts").parents("form").validator("destroy");
    $(".time-opts").parents("form").validator();
	if($('.time-opts').length > 1){
		$(".remBtnDis").removeClass("hide");
	}else{
		$(".remBtnDis").addClass("hide");
	}
	timep('time'+count);
	$('#time'+count).val("");
	$(document).find('.dailyClock').trigger('dp.change');
}
function removeTime(param){
    $(param).parents(".time-opts").remove();
    $(".time-opts").parents("form").validator("destroy");
		$(".time-opts").parents("form").validator();
		if($('.time-opts').length > 1){
			$(".remBtnDis").removeClass("hide");
		}else{
			$(".remBtnDis").addClass("hide");
		}
}
function addDate(){
	customCount = customCount +1;
	var newDateCon = "<div class='manually-option mb-md form-group' id='"+customCount+"'>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='StartDate"+customCount+"' type='text' count='"+customCount+"' required name='questionnaireCustomScheduleBo["+customCount+"].frequencyStartDate' class='form-control calendar customCalnder cusStrDate' placeholder='Start Date' onclick='customStartDate(this.id,"+customCount+");'/>"
				  +"	<span class='help-block with-errors red-txt'></span>"
				  +"  </span>"
				  +"  <span class='gray-xs-f mb-sm pr-md align-span-center'>"
				  +"  to "
				  +"  </span>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='EndDate"+customCount+"' type='text' count='"+customCount+"' required name='questionnaireCustomScheduleBo["+customCount+"].frequencyEndDate' class='form-control calendar customCalnder cusEndDate' placeholder='End Date' onclick='customEndDate(this.id,"+customCount+");'/>"
				  +"<span class='help-block with-errors red-txt'></span>"
				  +"  </span>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='customTime"+customCount+"' type='text' count='"+customCount+"' required name='questionnaireCustomScheduleBo["+customCount+"].frequencyTime' class='form-control clock customTime cusTime' placeholder='Time' onclick='timep(this.id);' disabled/>"
				  +"<span class='help-block with-errors red-txt'></span>"
				  +"  </span>"
				  +"  <span class='addbtn addBtnDis align-span-center' onclick='addDate();'>+</span>"
				  +"  <span id='delete' class='sprites_icon delete vertical-align-middle remBtnDis hide align-span-center' onclick='removeDate(this);'></span>"
				  +"</div>";
				  
	$(".manually-option:last").after(newDateCon);
	$(".manually-option").parents("form").validator("destroy");
    $(".manually-option").parents("form").validator();
	if($('.manually-option').length > 1){
		$('.manuallyContainer').find(".remBtnDis").removeClass("hide");
	}else{
		$('.manuallyContainer').find(".remBtnDis").addClass("hide");
	}
	customStartDate('StartDate'+customCount,customCount);
	customEndDate('EndDate'+customCount,customCount);
	timep('customTime'+customCount);
	$('#customTime'+customCount).val("");
}
function removeDate(param){
    $(param).parents(".manually-option").remove();
    $(".manually-option").parents("form").validator("destroy");
		$(".manually-option").parents("form").validator();
		if($('.manually-option').length > 1){
			$('.manuallyContainer').find(".remBtnDis").removeClass("hide");
		}else{
			$('.manuallyContainer').find(".remBtnDis").addClass("hide");
		}
		$(document).find('.cusTime').trigger('dp.change');
}
function timep(item) {
    $('#'+item).datetimepicker({
    	 format: 'HH:mm',
    });
}
function customStartDate(id,count){
	console.log("count:"+count);
	$('.cusStrDate').datetimepicker({
		format: 'MM/DD/YYYY',
        minDate: new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()),
    }).on("dp.change", function (e) {
    	$("#"+id).parent().removeClass("has-danger").removeClass("has-error");
        $("#"+id).parent().find(".help-block").html("");
        $("#EndDate"+count).parent().removeClass("has-danger").removeClass("has-error");
        $("#EndDate"+count).parent().find(".help-block").html("");
        var startDate = $("#"+id).val();
        var endDate = $("#EndDate"+count).val();
        if(startDate !='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
            $("#"+id).parent().addClass("has-danger").addClass("has-error");
       	   $("#"+id).parent().find(".help-block").html('<ul class="list-unstyled"><li>Start Date and Time Should not be greater than End Date and Time</li></ul>');
        }else{
     	   $("#id").parent().removeClass("has-danger").removeClass("has-error");
            $("#id").parent().find(".help-block").html("");
            $("#EndDate"+count).parent().removeClass("has-danger").removeClass("has-error");
            $("#EndDate"+count).parent().find(".help-block").html("");
            
        }
 });
}
function customEndDate(id,count){
	$('.cusEndDate').datetimepicker({
		format: 'MM/DD/YYYY',
        minDate: new Date(new Date().getFullYear(), new Date().getMonth() ,new Date().getDate()),
    }).on("dp.change", function (e) {
    	$('#'+id).parent().removeClass("has-danger").removeClass("has-error");
        $('#'+id).parent().find(".help-block").html("");
        $("#StartDate"+count).parent().removeClass("has-danger").removeClass("has-error");
        $("#StartDate"+count).parent().find(".help-block").html("");
    	var startDate = $("#StartDate"+count).val();
        var endDate = $('#'+id).val();
        if(startDate!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
        	$('#'+id).parent().addClass("has-danger").addClass("has-error");
       	    $('#'+id).parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date and Time Should not be less than Start Date and Time</li></ul>');
        }else{
        	$('#'+id).parent().removeClass("has-danger").removeClass("has-error");
            $('#'+id).parent().find(".help-block").html("");
            $("#StartDate"+count).parent().removeClass("has-danger").removeClass("has-error");
            $("#StartDate"+count).parent().find(".help-block").html("");
        }
    });
}
function toJSDate( dateTime ) {
	if(dateTime != null && dateTime !='' && typeof dateTime != 'undefined'){
		var date = dateTime.split("/");
		return new Date(date[2], (date[0]-1), date[1]);
	}
}
function saveQuestionnaire(item, callback){
	
	var id = $("#id").val();
	var study_id= $("#studyId").val();
	var title_text = $("#titleId").val();
	var short_title = $("#shortTitleId").val();
	var frequency_text = $('input[name="frequency"]:checked').val();
	var previous_frequency = $("#previousFrequency").val();
	var isFormValid = true;
	
	var study_lifetime_end = '';
	var study_lifetime_start = ''
	var repeat_questionnaire = ''
	
	branching =  $('input[name="branching"]:checked').val();
   
	//var type_text = $("#type").val();
	var type_text = "";
	var tab = $("#tabContainer li.active").text();
	
	if(tab == 'Content'){
		type_text = "content";
	}else if(tab == 'Schedule'){
		type_text = "schedule";
	}
	
	var questionnaire = new Object();
	
	if(id != null && id != '' && typeof id != 'undefined'){
		questionnaire.id=id;
	}
	if(branching != null && branching != '' && typeof branching != 'undefined'){
		questionnaire.branching=branching;
	}
	if(frequency_text != null && frequency_text != '' && typeof frequency_text != 'undefined'){
		questionnaire.frequency=frequency_text;
	}
	if(study_id != null && study_id != '' && typeof study_id != 'undefined'){
		questionnaire.studyId=study_id;
	}
	if(short_title != null && short_title != '' && typeof short_title != 'undefined'){
		questionnaire.shortTitle=short_title;
	}
	if(title_text != null && title_text != '' && typeof title_text != 'undefined'){
		questionnaire.title=title_text;
	}
	if(previous_frequency != null && previous_frequency != '' && typeof previous_frequency != 'undefined'){
		questionnaire.previousFrequency=previous_frequency;
	}else{
		questionnaire.previousFrequency=frequency_text;
	}
	if(type_text != null && type_text != '' && typeof type_text != 'undefined'){
		questionnaire.type=type_text;
	}
	questionnaire.status=false;
	var questionnaireFrequencey = new Object();
	
	if(frequency_text == 'One time'){
		
		var frequence_id = $("#oneTimeFreId").val();
		var frequency_date = $("#chooseDate").val();
		var freQuence_time = $("#selectTime").val();
		if($('#isLaunchStudy').is(':checked')){
			var isLaunch_study = true;
		}
		if($('#isStudyLifeTime').is(':checked')){
			var isStudy_lifeTime = true;
		}
		
		study_lifetime_end = $("#chooseEndDate").val();
		if(study_lifetime_end != null && study_lifetime_end != '' && typeof study_lifetime_end != 'undefined'){
			questionnaire.studyLifetimeEnd=study_lifetime_end;
		}
		if(frequence_id != null && frequence_id != '' && typeof frequence_id != 'undefined'){
			questionnaireFrequencey.id=frequence_id;
		}
		if(frequency_date != null && frequency_date != '' && typeof frequency_date != 'undefined'){
			questionnaireFrequencey.frequencyDate=frequency_date;
		}
		if(freQuence_time != null && freQuence_time != '' && typeof freQuence_time != 'undefined'){
			questionnaireFrequencey.frequencyTime=freQuence_time;
		}
		if(isLaunch_study != null && isLaunch_study != '' && typeof isLaunch_study != 'undefined'){
			questionnaireFrequencey.isLaunchStudy=isLaunch_study;
		}
		if(isStudy_lifeTime != null && isStudy_lifeTime != '' && typeof isStudy_lifeTime != 'undefined'){
			questionnaireFrequencey.isStudyLifeTime=isStudy_lifeTime;
		}
		if(id != null && id != '' && typeof id != 'undefined'){
			questionnaireFrequencey.questionnairesId = id;
		}
		questionnaire.questionnairesFrequenciesBo=questionnaireFrequencey;
		
	}else if(frequency_text == 'Manually Schedule'){
		var customArray  = new Array();
		isFormValid = isValidManuallySchedule;
		$('.manually-option').each(function(){
			var questionnaireCustomFrequencey = new Object();
			questionnaireCustomFrequencey.questionnairesId = id;
			var id = $(this).attr("id");
			var startdate = $("#StartDate"+id).val();
			var enddate = $("#EndDate"+id).val();
			var time = $("#customTime"+id).val();
			if(startdate != null && startdate != '' && typeof startdate != 'undefined'){
				questionnaireCustomFrequencey.frequencyStartDate=startdate;
			}
			if(enddate != null && enddate != '' && typeof enddate != 'undefined'){
				questionnaireCustomFrequencey.frequencyEndDate=enddate;
			}
			if(time != null && time != '' && typeof time != 'undefined'){
				questionnaireCustomFrequencey.frequencyTime=time;
			}
			customArray.push(questionnaireCustomFrequencey)
		})  
		questionnaire.questionnaireCustomScheduleBo=customArray;
		
	}else if(frequency_text == 'Daily'){
		isFormValid = multiTimeVal;
		var frequenceArray = new Array();
		study_lifetime_start = $("#startDate").val();
		repeat_questionnaire = $("#days").val();
		study_lifetime_end = $("#endDateId").text();
		
		$('.time-opts').each(function(){
			var questionnaireFrequencey = new Object();
			var id = $(this).attr("id");
			console.log("id:"+id);
			var frequence_time = $('#time'+id).val();
			console.log("frequence_time:"+frequence_time);
			if(frequence_time != null && frequence_time != '' && typeof frequence_time != 'undefined'){
				questionnaireFrequencey.frequencyTime=frequence_time;
			}
			frequenceArray.push(questionnaireFrequencey);
			
		})
		questionnaire.questionnairesFrequenciesList=frequenceArray;
		if(study_lifetime_start != null && study_lifetime_start != '' && typeof study_lifetime_start != 'undefined'){
			questionnaire.studyLifetimeStart=study_lifetime_start;
		}
		if(study_lifetime_end != null && study_lifetime_end != '' && typeof study_lifetime_end != 'undefined'){
			questionnaire.studyLifetimeEnd=study_lifetime_end;
		}
		if(repeat_questionnaire != null && repeat_questionnaire != '' && typeof repeat_questionnaire != 'undefined'){
			questionnaire.repeatQuestionnaire=repeat_questionnaire;
		}
		questionnaire.questionnairesFrequenciesBo=questionnaireFrequencey;
		
	}else if(frequency_text == 'Weekly'){
		
		var frequence_id = $("#weeklyFreId").val();
		study_lifetime_start = $("#startWeeklyDate").val();
		var frequence_time = $("#selectWeeklyTime").val();
		var dayOftheweek = $("#startDateWeekly").val();
		repeat_questionnaire = $("#weeks").val();
		study_lifetime_end = $("#weekEndDate").text();
		
		if(dayOftheweek != null && dayOftheweek != '' && typeof dayOftheweek != 'undefined'){
			questionnaire.dayOfTheWeek=dayOftheweek;
		}
		if(study_lifetime_start != null && study_lifetime_start != '' && typeof study_lifetime_start != 'undefined'){
			questionnaire.studyLifetimeStart=study_lifetime_start;
		}
		if(study_lifetime_end != null && study_lifetime_end != '' && typeof study_lifetime_end != 'undefined'){
			questionnaire.studyLifetimeEnd=study_lifetime_end;
		}
		if(repeat_questionnaire != null && repeat_questionnaire != '' && typeof repeat_questionnaire != 'undefined'){
			questionnaire.repeatQuestionnaire=repeat_questionnaire;
		}
		if(id != null && id != '' && typeof id != 'undefined'){
			questionnaireFrequencey.questionnairesId = id;
		}
		if(frequence_id != null && frequence_id != '' && typeof frequence_id != 'undefined'){
			questionnaireFrequencey.id=frequence_id;
		}
		if(frequence_time != null && frequence_time != '' && typeof frequence_time != 'undefined'){
			questionnaireFrequencey.frequencyTime=frequence_time;
		}
		questionnaire.questionnairesFrequenciesBo=questionnaireFrequencey;
		
	}else if(frequency_text == 'Monthly'){
		
		var frequence_id = $("#monthFreId").val();
		var frequencydate = $("#startDateMonthly").val();
		var frequencetime = $("#selectMonthlyTime").val();
		study_lifetime_start = $("#pickStartDate").val();
		repeat_questionnaire = $("#months").val();
		study_lifetime_end = $("#monthEndDate").text();
		
		if(study_lifetime_start != null && study_lifetime_start != '' && typeof study_lifetime_start != 'undefined'){
			questionnaire.studyLifetimeStart=study_lifetime_start;
		}
		if(study_lifetime_end != null && study_lifetime_end != '' && typeof study_lifetime_end != 'undefined'){
			questionnaire.studyLifetimeEnd=study_lifetime_end;
		}
		if(repeat_questionnaire != null && repeat_questionnaire != '' && typeof repeat_questionnaire != 'undefined'){
			questionnaire.repeatQuestionnaire=repeat_questionnaire;
		}
		if(id != null && id != '' && typeof id != 'undefined'){
			questionnaireFrequencey.questionnairesId = id;
		}
		if(frequence_id != null && frequence_id != '' && typeof frequence_id != 'undefined'){
			questionnaireFrequencey.id=frequence_id;
		}
		if(frequencydate != null && frequencydate != '' && typeof frequencydate != 'undefined'){
			questionnaireFrequencey.frequencyDate=frequencydate;
		}
		if(frequencetime != null && frequencetime != '' && typeof frequencetime != 'undefined'){
			questionnaireFrequencey.frequencyTime=frequencetime;
		}
		questionnaire.questionnairesFrequenciesBo=questionnaireFrequencey;
		
	}
	console.log("questionnaire:"+JSON.stringify(questionnaire));
	var data = JSON.stringify(questionnaire);
	$(item).prop('disabled', true);
	if(study_id != null && short_title != '' && short_title != null && isFormValid ){
		$.ajax({ 
	        url: "/fdahpStudyDesigner/adminStudies/saveQuestionnaireSchedule.do",
	        type: "POST",
	        datatype: "json",
	        data: {questionnaireScheduleInfo:data},
	        beforeSend: function(xhr, settings){
	            xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	        },
	        success:function(data){
	      	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					var questionnaireId = jsonobject.questionnaireId;
					var questionnaireFrequenceId = jsonobject.questionnaireFrequenceId;
					$("#id").val(questionnaireId);
					$("#previousFrequency").val(frequency_text);
					if(frequency_text == 'One time'){
						$("#oneTimeFreId").val(questionnaireFrequenceId);
					}else if(frequency_text == 'Weekly'){
						$("#weeklyFreId").val(questionnaireFrequenceId);
					}else if(frequency_text == 'Monthly'){
						$("#monthFreId").val(questionnaireFrequenceId);
					}
					frequencey = frequency_text;
// 					showSucMsg("Questionnaire saved successfully");
					if (callback)
						callback(true);
				}else{
// 					showErrMsg("Something went Wrong");
					if (callback)
  						callback(false);
				}
	        },
	        error: function(xhr, status, error) {
// 				  showErrMsg("Something went Wrong");
					if (callback)
  						callback(false);
			  },
			complete : function() {
				$(item).prop('disabled', false);
			}
	 	});
	}else{
		$(item).prop('disabled', false);
		if (callback)
			callback(false);
	}
}
function checkDateRange(){
	$(document).on('dp.change change', '.cusStrDate, .cusEndDate, .cusTime', function(e) {
		var chkVal = true;
		if($(this).parents('.manually-option').find('.cusStrDate').val() && $(this).parents('.manually-option').find('.cusEndDate').val() && $(this).parents('.manually-option').find('.cusTime').val()) {
			var thisAttr = this;
			$(this).parents('.manuallyContainer').find('.manually-option').each(function() {
				if((!$(thisAttr).parents('.manually-option').is($(this))) && $(this).find('.cusStrDate').val() && $(this).find('.cusEndDate').val() && $(this).find('.cusTime').val()) {
					var fromDate = moment($(this).find('.cusStrDate').val(), "MM/DD/YYYY").toDate();
					var cusTime =  moment($(this).find('.cusTime').val(), "HH:mm").toDate()
					fromDate.setHours(cusTime.getHours());
					fromDate.setMinutes(cusTime.getMinutes());
					var toDate = moment($(this).find('.cusEndDate').val(), "MM/DD/YYYY").toDate();
					toDate.setHours(cusTime.getHours());
					toDate.setMinutes(cusTime.getMinutes());
					var thisFromDate = moment($(thisAttr).parents('.manually-option').find('.cusStrDate').val(), "MM/DD/YYYY").toDate();
					var thisCusTime =  moment($(thisAttr).parents('.manually-option').find('.cusTime').val(), "HH:mm").toDate()
					thisFromDate.setHours(thisCusTime.getHours());
					thisFromDate.setMinutes(thisCusTime.getMinutes());
					var thisToDate = moment($(thisAttr).parents('.manually-option').find('.cusEndDate').val(), "MM/DD/YYYY").toDate();
					thisToDate.setHours(thisCusTime.getHours());
					thisToDate.setMinutes(thisCusTime.getMinutes());
					if(chkVal)
						chkVal = !((thisFromDate >= fromDate && thisFromDate <= toDate) || (thisToDate >= fromDate && thisToDate <= toDate));
				}
			});
		}
		if(!chkVal) {
			console.log('check the date');
			$(thisAttr).parents('.manually-option').find('.cusTime').parent().addClass('has-error has-danger').find(".help-block").html('<ul class="list-unstyled"><li>End Date and Time Should not be less than Start Date and Time</li></ul>');
		} else {
			$(thisAttr).parents('.manually-option').find('.cusTime').parent().removeClass('has-error has-danger').find(".help-block").html('');
		}	
		var a = 0;
		$('.manuallyContainer').find('.manually-option').each(function() {
			if($(this).find('.cusTime').parent().find('.help-block.with-errors').children().length > 0) {
				a++;
			}
		});
		isValidManuallySchedule = !(a > 0);
	});
	return isValidManuallySchedule;
}
function doneQuestionnaire(item, actType, callback) {
		var frequency = $('input[name="frequency"]:checked').val();
    	console.log("frequency:"+frequency)
    	var valForm = false;
    	if(actType !=='save'){
	    	if(frequency == 'One time'){
	    		$("#frequencyId").val(frequency);
	    		if(isFromValid("#oneTimeFormId")){
	    			valForm = true;
	    		}
	    	}else if(frequency == 'Manually Schedule'){
	    		$("#customfrequencyId").val(frequency);
	    		if(isFromValid("#customFormId")){
	    			valForm = true;
	    		}
	    	}else if(frequency == 'Daily'){
	    		$("#dailyFrequencyId").val(frequency);
	    		if(isFromValid("#dailyFormId")){
	    			valForm = true;
	    		}
	    	}else if(frequency == 'Weekly'){
	    		$("#weeklyfrequencyId").val(frequency);
	    		if(isFromValid("#weeklyFormId")){
	    			valForm = true;
	    		}
	    	}else if(frequency == 'Monthly'){
	    		$("#monthlyfrequencyId").val(frequency);
	    		if(isFromValid("#monthlyFormId")){
	    			valForm = true;
	    		}
	    	}
    	} else {
    		valForm = true;
    	} 
    	if(valForm) {
    		saveQuestionnaire(item, function(val) {
    			if(!val){
    				$('.scheduleQusClass a').tab('show');
    			}
				callback(val);
			});
    	} else {
    		showErrMsg("Please fill all mandatory filds.");
    		$('.scheduleQusClass a').tab('show');
    		if (callback)
    			callback(false);
    	}
}
// $(window).on("load",function(){				
// 	var a = $(".col-lc").height();
// 	var b = $(".col-rc").height();
// 	if(a > b){
// 		$(".col-rc").css("height", a);	
// 	}else{
// 		$(".col-rc").css("height", "auto");
// 	}
// });
function deletStep(stepId,stepType){
	bootbox.confirm("Are you sure you want to delete this questionnaire step?", function(result){ 
		if(result){
			var questionnaireId = $("#id").val();
			var studyId = $("#studyId").val();
			if((stepId != null && stepId != '' && typeof stepId != 'undefined') && 
					(questionnaireId != null && questionnaireId != '' && typeof questionnaireId != 'undefined')){
				$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteQuestionnaireStep.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				questionnaireId: questionnaireId,
	    				stepId : stepId,
	    				stepType: stepType,
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteConsentInfo(data){
	    				 var jsonobject = eval(data);
	    				var status = jsonobject.message;
	    				if(status == "SUCCESS"){
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Questionnaire step deleted successfully");
	    					$('#alertMsg').show();
	    					console.log(jsonobject.questionnaireJsonObject);
	    					var questionnaireSteps = jsonobject.questionnaireJsonObject; 
	    					reloadQuestionnaireStepData(questionnaireSteps);
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete questionnaire step");
	    					$('#alertMsg').show();
	    	            }
	    				setTimeout(hideDisplayMessage, 4000);
	    			},
	    			error: function(xhr, status, error) {
	    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
	    			  setTimeout(hideDisplayMessage, 4000);
	    			}
	    		});
			}else{
				bootbox.alert("Ooops..! Something went worng. Try later");
			}
		}
	});
}
function reloadQuestionnaireStepData(questionnaire){
	$('#content').DataTable().clear();
	 if(typeof questionnaire != 'undefined' && questionnaire != null && Object.keys(questionnaire).length > 0){
		 $.each(questionnaire, function(key, value) {
			 var datarow = [];
			 if(typeof key === "undefined"){
					datarow.push(' ');
				 }else{
				   var dynamicTable = '';
				   if(value.stepType == 'Instruction'){
					   datarow.push('<span id="'+key+'" class="round blue-round">'+key+'</span>');			  
	      	 	   }else if(value.stepType == 'Question'){
	      	 		   datarow.push('<span id="'+key+'" class="round green-round">'+key+'</span>');		
	      	 	   }else{
		      	 		dynamicTable +='<span id="'+key+'" class="round teal-round">'+key+'</span>';
	      	 			datarow.push(dynamicTable);		
	      	 	   }
				 }	
			     if(typeof value.title == "undefined"){
			    	 datarow.push(' ');
			     }else{
			    	 var title="";
			    	 if(value.stepType == 'Form'){
				    	  $.each(value.fromMap, function(key, value) {
				    		  title +='<div>'+value.title+'</div>';
				    	  });
				      }else{
				    	  title +='<div>'+value.title+'</div>';
				      }
			    	 datarow.push(title);
			     }
			     var dynamicAction ='<div>'+
			                  '<div class="text-right pos-relative">';
			      if(value.stepType != 'Instruction'){
			    	  dynamicAction +='<span class="sprites_v3 status-blue mr-md"></span>'+
		                  '<span class="sprites_v3 heart-blue mr-md"></span>'
			      }
			      dynamicAction +='<span class="sprites_v3 calender-blue mr-md"></span>'+
					              '<span class="ellipse" onmouseenter="ellipseHover(this);"></span>'+
					              '<div class="ellipse-hover-icon" onmouseleave="ellipseUnHover(this);">'+
					               '  <span class="sprites_icon preview-g mr-sm"></span>'+
					               '  <span class="sprites_icon edit-g mr-sm" onclick="editStep('+value.stepId+',&#34;'+value.stepType+'&#34;)"></span>'+
					               '  <span class="sprites_icon delete" onclick="deletStep('+value.stepId+',&#34;'+value.stepType+'&#34;)"></span>'+
					              '</div>'+
					           '</div>';
				if(value.stepType == 'Form'){
					if(Object.keys(value.fromMap).length > 0){
						for(var j=0 ;j < Object.keys(value.fromMap).length-1; j++ ){
							dynamicAction +='<div>&nbsp;</div>';	
						}
					 }
				}
				dynamicAction +='</div>';
				datarow.push(dynamicAction);    	 
			$('#content').DataTable().row.add(datarow);
		 });
		 $('#content').DataTable().draw();
	 }else{
		 $('#content').DataTable().draw();
	 }
}
function ellipseHover(item){
	 $(item).hide();
     $(item).next().show();
}
function ellipseUnHover(item){
	$(item).hide();
    $(item).prev().show();
}
function getQuestionnaireStep(stepType){
	if(stepType == 'Instruction'){
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/instructionsStep.do";
		document.contentFormId.submit();
	}else if(stepType == 'Form'){
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/formStep.do";
		document.contentFormId.submit();
	}else if(stepType == 'Question'){
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/questionStep.do";
		document.contentFormId.submit();
	}
}
function editStep(stepId,stepType){
	if(stepType == 'Instruction'){
		$("#instructionId").val(stepId);
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/instructionsStep.do";
		document.contentFormId.submit();
	}else if(stepType == 'Form'){
		$("#formId").val(stepId);
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/formStep.do";
		document.contentFormId.submit();
	}else if(stepType == 'Question'){
		$("#questionId	").val(stepId);
		document.contentFormId.action="/fdahpStudyDesigner/adminStudies/questionStep.do";
		document.contentFormId.submit();
	}
}
function goToBackPage(item){
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
			        	a.href = "/fdahpStudyDesigner/adminStudies/viewStudyQuestionnaires.do";
			        	document.body.appendChild(a).click();
			        }else{
			        	$(item).prop('disabled', false);
			        }
			    }
		});
}
</script>