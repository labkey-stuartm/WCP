<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
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
/* .time-opts .remBtnDis{
	display: initial;
} */
</style>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span><img src="../images/icons/back-b.png" class="pr-md"/></span> Add Questionnaire</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn" id="doneId">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <input type="hidden" name="id" value=" ${questionnaireBo.id}">
   <div class="right-content-body pt-none pl-none">
      <ul class="nav nav-tabs review-tabs">
         <li><a data-toggle="tab" href="#content">Content</a></li>
         <li class="active"><a data-toggle="tab" href="#schedule">Schedule</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <div id="content" class="tab-pane fade">
            <h3>Share Data Permissions</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
         </div>
         <!---  Schedule ---> 
         <div id="schedule" class="tab-pane fade in active mt-xlg">
            <div class="gray-xs-f mb-sm">Questionnaire Frequency</div>
            <div class="pb-lg b-bor">
               <span class="radio radio-info radio-inline p-40">
               <input type="radio" id="inlineRadio1" class="schedule" frequencytype="oneTime" value="One Time" name="frequency" ${empty questionnaireBo.frequency  || questionnaireBo.frequency=='One Time' ?'checked':''}>
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
               <input type="radio" id="inlineRadio5" class="schedule" frequencytype="manually" value="Manually schedule" name="frequency" ${questionnaireBo.frequency=='Manually schedule' ?'checked':''}>
               <label for="inlineRadio5">Manually Schedule</label>
               </span>
            </div>
            <!-- One Time Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="oneTimeFormId" id="oneTimeFormId" method="post" role="form">
             <input type="hidden" name="frequency" id="frequencyId">
             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
            <div class="oneTime all mt-xlg">
               <div class="gray-xs-f mb-sm">Date/Time of launch(pick one)</div>
               <div class="mt-sm">
                  <span class="checkbox checkbox-inline">
                  <input type="hidden" name="questionnaireBo.questionnairesFrequenciesBo.id">
                  <input type="checkbox" id="isLaunchStudy" name="questionnairesFrequenciesBo.isLaunchStudy" value="true" ${questionnaireBo.questionnairesFrequenciesBo.isLaunchStudy ?'checked':''}>
                  <label for="isLaunchStudy"> Launch with study</label>
                  </span>
                  <div class="mt-md form-group">
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="chooseDate" type="text" class="form-control calendar" name="questionnairesFrequenciesBo.frequencyDate" placeholder="Choose Date" value="${questionnaireBo.questionnairesFrequenciesBo.frequencyDate}" required/>
                      <span class='help-block with-errors red-txt'></span>
                     </span>
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="selectTime" type="text" class="form-control clock" data-provide="timepicker" name="questionnairesFrequenciesBo.frequencyTime"  value="${questionnaireBo.questionnairesFrequenciesBo.frequencyTime}" required data-minute-step="1" data-modal-backdrop="true" placeholder="Select Time" onclick="timep(this.id)" />
                     <span class='help-block with-errors red-txt'></span>
                     </span>
                  </div>
               </div>
               <div class="gray-xs-f mb-sm mt-xlg">Lifetime of the run and of the questionnaire</div>
               <div class="mt-sm">
                  <span class="checkbox checkbox-inline">
                  <input type="checkbox" id="isStudyLifeTime" name="questionnairesFrequenciesBo.isStudyLifeTime" value="true" ${questionnaireBo.questionnairesFrequenciesBo.isStudyLifeTime ?'checked':''}>
                  <label for="isStudyLifeTime"> Study Lifetime</label>
                  </span>
                  <div class="mt-md form-group">
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="chooseEndDate" type="text" class="form-control calendar" name="studyLifetimeEnd" placeholder="Choose End Date" required value="${questionnaireBo.studyLifetimeEnd}"/>
                     <span class='help-block with-errors red-txt'></span>
                     </span>                            
                  </div>
               </div>
            </div>
            </form:form>
            <!-- Daily Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="dailyFormId" id="dailyFormId" method="post" role="form">
           	 <input type="hidden" name="frequency" id="dailyFrequencyId" value="${questionnaireBo.frequency}">
             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
            <div class="daily all mt-xlg dis-none">
               <div class="gray-xs-f mb-sm">Time(s) of the day for daily occurrence</div>
               <div class="dailyContainer">
               <c:if test="${fn:length(questionnaireBo.questionnairesFrequenciesList) eq 0}">
               <div class="time-opts mt-md">
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <input id="time0" type="text" name="questionnairesFrequenciesList[0].frequencyTime" class="form-control clock" placeholder="00:00" onclick ='timep(this.id);'/>
                  </span> 
                  <span class="addBtnDis addbtn mr-sm" onclick='addTime();'>+</span>
                  <span class="delete vertical-align-middle remBtnDis hide pl-md" onclick='removeTime(this);'></span>
               </div>
               </c:if>
               <c:if test="${fn:length(questionnaireBo.questionnairesFrequenciesList) gt 0}">
                <c:forEach items="${questionnaireBo.questionnairesFrequenciesList}" var="questionnairesFrequencies" varStatus="frequeincesVar">
	                <div class="time-opts mt-md">
	                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
	                  <input id="time1" type="text" name="questionnairesFrequenciesList[${frequeincesVar.index}].frequencyTime" class="form-control clock" placeholder="00:00" onclick ='timep(this.id);'/>
	                  </span> 
	                  <span class="addBtnDis addbtn mr-sm" onclick='addTime();'>+</span>
	                  <span class="delete vertical-align-middle remBtnDis hide pl-md" onclick='removeTime(this);'></span>
	               </div>
                </c:forEach>
               </c:if>
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="startDate" type="text" class="form-control mt-sm calendar" name="studyLifetimeStart" value="${questionnaireBo.studyLifetimeStart}"/>
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of days to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" name="repeatQuestionnaire" value="${questionnaireBo.repeatQuestionnaire}"/>
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f" id="endDateId">${not empty questionnaireBo.studyLifetimeEnd ? questionnaireBo.studyLifetimeEnd :'NA'}</div>
                  <input type="hidden" name="studyLifetimeEnd" id="studyLifetimeEnd" value="${questionnaireBo.studyLifetimeEnd}">
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
             <input type="hidden" name="frequency" id="frequencyId">
             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
            <div class="week all mt-xlg dis-none">
               <div>                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Day/Time (of the week)</span><br/>
                  <select id="startDateWeekly" class="form-control mt-sm">
	                  <option value=''>Select</option>
	                  <option value='Sunday'>Sunday</option>
	                  <option value='Monday'>Monday</option>
	                  <option value='Tuesday'>Tuesday</option>
	                  <option value='Wednesday'>Wednesday</option>
	                  <option value='Thursday'>Thursday</option>
	                  <option value='Friday'>Friday </option>
	                  <option value='Saturday'>Saturday</option>
                  </select>                            
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">&nbsp;</span><br/>
                  <input id="selectWeeklyTime" type="text" class="form-control mt-sm clock" onclick="timep(this.id)"/>
                  </span>                        
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="startWeeklyDate" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of days to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" />
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f">2/19/2017</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
                  <div class="black-xs-f">Until the next run comes up</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
                  <div class="black-xs-f">2/14/2017  -  2/19/2017</div>
               </div>
            </div> 
            </form:form>
            <!-- Monthly Section-->   
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="monthlyFormId" id="monthlyFormId" method="post" role="form"> 
            <input type="hidden" name="frequency" id="frequencyId">
             <input type="hidden" name="id" id="id" value="${questionnaireBo.id}">
            <div class="month all mt-xlg dis-none">
               <div>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Select Date/Time (of the month)</span><br/>                            
                  <input id="startDateMonthly" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">&nbsp;</span><br/>
                  <input id="selectMonthlyTime" type="text" class="form-control mt-sm clock" onclick="timep(this.id)"/>
                  </span>
                  <div class="gray-xs-f mt-xs italic-txt text-weight-light">If the selected date is not available in a month, the last day of the month will be used instead</div>
               </div>
               <div class="mt-xlg">                        
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                  <input id="pickStartDate" type="text" class="form-control mt-sm calendar" />
                  </span>
                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                  <span class="gray-xs-f">No. of months to repeat the questionnaire</span><br/>
                  <input id="days" type="text" class="form-control mt-sm" />
                  </span>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">End Date </div>
                  <div class="black-xs-f">2/19/2017</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of each run</div>
                  <div class="black-xs-f">Until the next run comes up</div>
               </div>
               <div class="mt-xlg">
                  <div class="gray-xs-f mb-xs">Lifetime of the questionnaire </div>
                  <div class="black-xs-f">2/14/2017  -  2/19/2017</div>
               </div>
            </div> 
            </form:form>
            <!-- Manually Section-->    
            <form:form action="/fdahpStudyDesigner/adminStudies/saveorUpdateQuestionnaireSchedule.do" name="customFormId" id="customFormId" method="post" role="form">
	           <div class="manually all mt-xlg dis-none">
	               <div class="gray-xs-f mb-sm">Select time period</div>
	               <div class="manuallyContainer">
		               <div class="manually-option mb-md form-group">
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="StartDate1" type="text" count='1' class="form-control calendar" placeholder="Start Date" onclick='customStartDate(this.id,1);' required/>
		                  <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="gray-xs-f mb-sm pr-md align-span-center">
		                  to 
		                  </span>
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="EndDate1" type="text" count='1' class="form-control calendar" placeholder="End Date" onclick='customEndDate(this.id,1);' required/>
		                   <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="form-group m-none dis-inline vertical-align-middle pr-md">
		                  <input id="customTime1" type="text" count='1' class="form-control clock" placeholder="Time" onclick='timep(this.id);' required/>
		                   <span class='help-block with-errors red-txt'></span>
		                  </span>
		                  <span class="addbtn addBtnDis align-span-center" onclick="addDate();">+</span>
		                  <span id="delete" class="sprites_icon delete vertical-align-middle remBtnDis hide align-span-center" onclick="removeDate(this);"></span>
		               </div>
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
var customCount = 1;
$(document).ready(function() {
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
    $(".schedule").click(function() {
        $(".all").addClass("dis-none");
        var schedule_opts = $(this).attr('frequencytype');
        $("." + schedule_opts).removeClass("dis-none");
        
        
    });
    $('#chooseDate').datetimepicker({
        format: 'MM/DD/YYYY'
    });
    $('#chooseEndDate').datetimepicker({
        format: 'MM/DD/YYYY'
    });
    $('#startDate').datetimepicker({
        format: 'MM/DD/YYYY'
    });
    /* $('#startWeeklyDate').datetimepicker({
        format: 'MM/DD/YYYY',
    }); */
    $('#startDateMonthly').datetimepicker({
        format: 'MM/DD/YYYY'
    });
    $('#pickStartDate').datetimepicker({
        format: 'MM/DD/YYYY'
    });
    $('#startWeeklyDate').datetimepicker({
        format: 'MM/DD/YYYY',
    })
   /*  $('.calendar').datetimepicker({
        format: 'MM/DD/YYYY'
    }); */
    var daysOfWeek = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
    $("#startDateWeekly").on('change', function(){
    	var weekDay = $("#startDateWeekly").val();
    	var weeks=[];
    	for(var i=0;i<daysOfWeek.length;i++){
    		if(weekDay != daysOfWeek[i]){
    			weeks.push(i);		
        	}    		
    	}
    	console.log(weeks);
    	$('#startWeeklyDate').data("DateTimePicker").destroy();
    	$('#startWeeklyDate').datetimepicker({
            format: 'MM/DD/YYYY',
            minDate: new Date(),
            daysOfWeekDisabled: weeks
        });
    	$('#startWeeklyDate').val('');
    });
    $("#doneId").click(function(){
    	var frequency = $('input[name="frequency"]:checked').val();
    	console.log("frequency:"+frequency)
    	if(frequency == 'One Time'){
    		$("#oneTimeFormId").submit();
    	}else if(frequency == 'Manually schedule'){
    		$("#customFormId").submit();
    	}else if(frequency == 'Daily'){
    		$("#dailyFrequencyId").val(frequency);
    		$("#dailyFormId").submit();
    	}else if(frequency == 'Weekly'){
    		$("#weeklyFormId").submit();
    	}else if(frequency == 'Monthly'){
    		$("#monthlyFormId").submit();
    	}
    	
    });
   
    $("#startDate").change(function(){
    	var startDate = $("#startDate").val();
    	var days = $("#days").val();
    	var endDate = ''
    	if((startDate != null && startDate != '' && typeof startDate != 'undefined') && (days != null && days != '' && typeof days != 'undefined')){
    		var dt = new Date(startDate);
            dt.setDate(dt.getDate() + Number(days));	
            endDate = formatDate(dt);
            $("#studyLifetimeEnd").val(endDate);
            $("#lifeTimeId").text(startDate+' - '+endDate);
            $("#endDateId").text(endDate);
    	}
    })
    $("#days").change(function(){
    	var startDate = $("#startDate").val();
    	var days = $("#days").val();
    	var dt = new Date(startDate);
    	if((startDate != null && startDate != '' && typeof startDate != 'undefined') && (days != null && days != '' && typeof days != 'undefined')){
    		var dt = new Date(startDate);
            dt.setDate(dt.getDate() + Number(days));	
            endDate = formatDate(dt);
            $("#studyLifetimeEnd").val(endDate);
            $("#lifeTimeId").text(startDate+' - '+endDate);
            $("#endDateId").text(endDate);
    	}
    })
    $("#isLaunchStudy").change(function(){
    	if(!$("#isLaunchStudy").is(':checked')){
    		$("#chooseDate").attr("disabled",false);
    		$("#selectTime").attr("disabled",false);
    	}else{
    		$("#chooseDate").attr("disabled",true);
    		$("#selectTime").attr("disabled",true);
    	}
    });
    $("#isStudyLifeTime").change(function(){
    	if(!$("#isStudyLifeTime").is(':checked')){
    		$("#chooseEndDate").attr("disabled",false);
    	}else{
    		$("#chooseEndDate").attr("disabled",true);
    	}
    });
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
	var newTime = "<div class='time-opts mt-md'>"+
				  "  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"+
				  "  <input id='time"+count+"' type='text' name='questionnairesFrequenciesList["+count+"].frequencyTime' class='form-control clock' placeholder='00:00' onclick='timep(this.id);'/>"+
				  " </span>"+ 
				  "  <span class='addBtnDis addbtn mr-sm' onclick='addTime();'>+</span>"+
				  " <span class='delete vertical-align-middle remBtnDis hide pl-md' onclick='removeTime(this);'></span>"+
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
	//$('#time'+count).val("");
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
	var newDateCon = "<div class='manually-option mb-md form-group'>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='StartDate"+customCount+"' type='text' count='"+customCount+"' class='form-control calendar' placeholder='Start Date' onclick='customStartDate(this.id,"+customCount+");'/>"
				  +"	<span class='help-block with-errors red-txt'></span>"
				  +"  </span>"
				  +"  <span class='gray-xs-f mb-sm pr-md align-span-center'>"
				  +"  to "
				  +"  </span>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='EndDate"+customCount+"' type='text' count='"+customCount+"' class='form-control calendar' placeholder='End Date' onclick='customEndDate(this.id,"+customCount+");'/>"
				  +"<span class='help-block with-errors red-txt'></span>"
				  +"  </span>"
				  +"  <span class='form-group m-none dis-inline vertical-align-middle pr-md'>"
				  +"  <input id='customTime"+customCount+"' type='text' count='"+customCount+"' class='form-control clock customTime' placeholder='Time' onclick='timep(this.id);'/>"
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
}
function timep(item) {
    $('#'+item).timepicker().on('show.timepicker', function(e) {
        console.log('The time is ' + e.time.value);
        console.log('The hour is ' + e.time.hours);
        console.log('The minute is ' + e.time.minutes);
        console.log('The meridian is ' + e.time.meridian);
    });
}
function customStartDate(id,count){
	console.log("count:"+count);
	$('#'+id).datetimepicker({
		format: 'MM/DD/YYYY',
        minDate: new Date(),
    }).on("dp.change", function (e) {
    	$("#"+id).parent().removeClass("has-danger").removeClass("has-error");
        $("#"+id).parent().find(".help-block").html("");
        $("#EndDate"+count).parent().removeClass("has-danger").removeClass("has-error");
        $("#EndDate"+count).parent().find(".help-block").html("");
        var startDate = $("#"+id).val();
        var endDate = $("#EndDate"+count).val();
        console.log("endDate:"+endDate);
        console.log("startDate:"+startDate);
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
	console.log("count:"+count);
	$('#'+id).datetimepicker({
		format: 'MM/DD/YYYY',
        minDate: new Date(),
    }).on("dp.change", function (e) {
    	$('#'+id).parent().removeClass("has-danger").removeClass("has-error");
        $('#'+id).parent().find(".help-block").html("");
        $("#StartDate"+count).parent().removeClass("has-danger").removeClass("has-error");
        $("#StartDate"+count).parent().find(".help-block").html("");
    	var startDate = $("#StartDate"+count).val();
        var endDate = $('#'+id).val();
        console.log("endDate:"+endDate);
        console.log("startDate:"+startDate);
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
	console.log("dateTime:"+dateTime);
	//var dateTime = dateTime.split(" ");
	var date = dateTime.split("/");
	//var time = dateTime[1].split(":");
	return new Date(date[2], (date[0]-1), date[1]);
}
</script>