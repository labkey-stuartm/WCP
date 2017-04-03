<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
       <div class="col-sm-10 col-rc white-bg p-none">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="pr-sm cur-pointer" onclick="goToBackPage(this);">
                    <img src="../images/icons/back-b.png" class="pr-md"/></span> 
                    <c:if test="${empty activeTaskBo.id}"> Add Active Task</c:if>
					<c:if test="${not empty activeTaskBo.id && actionPage eq 'addEdit'}">Edit Consent</c:if>
					<c:if test="${not empty activeTaskBo.id && actionPage eq 'view'}">View Consent</c:if>
                    </div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn actBut">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn actBut" id="doneId" >Done</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body pt-none pl-none pr-none">
                
             <ul class="nav nav-tabs review-tabs gray-bg">
                <li class="active"><a data-toggle="tab" href="#content">Content</a></li>
                <li><a data-toggle="tab" href="#schedule">Schedule</a></li>                           
              </ul>
                
                
                <div class="tab-content pl-xlg pr-xlg">
                
                <!-- Content--> 
                <div id="content" class="tab-pane fade in active mt-xlg">
                   
                    <div class="mt-md blue-md-f text-uppercase">Select Active Task</div>
                    <div class="gray-xs-f mt-md mb-sm">Choose from a list of pre-defined active tasks</div>
                    <div class="col-md-4 p-none">
                        <select class="selectpicker targetOption taskId='${activeTaskBo.id}'" title="Select">
                          <c:forEach items="${activeTaskListBos}" var="activeTaskTypeInfo">
	                          <option value="${activeTaskTypeInfo.activeTaskListId}" ${activeTaskBo.taskTypeId eq activeTaskTypeInfo.activeTaskListId ?'selected':''}>${activeTaskTypeInfo.taskName}</option>
                          </c:forEach>
                        </select>
                    </div> 
                    <div class="clearfix"></div>
                    <div class="mt-sm black-xs-f italic-txt activeText">
                        
                    </div>
                    
                    
                    <div class="changeContent">
                    <!-- <div class="pt-lg">
                        <div class="gray-xs-f mb-sm">Title 1</div>
                         <div>
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    <div>
                        <div class="gray-xs-f mb-sm">Title 2</div>
                         <div class="add_notify_option">
                             <div class="form-group">
                                 <input type="text" class="form-control"/>  
                                 <div class="help-block with-errors red-txt"></div>
                            </div>
                        </div>                            
                    </div>
                    
                    
                    
                    <div class="mt-xlg blue-md-f text-uppercase">Configurable parameters</div>
                    <div class="gray-xs-f mt-md mb-sm">Instructions</div>
                    <div class="form-group">                     
                      <textarea class="form-control" rows="5" id="comment"></textarea>
                      <div class="help-block with-errors red-txt"></div>
                    </div>
                    
                    <div class="gray-xs-f mt-md mb-sm">Duration over which to record the kick count</div>                    
                    <div class="form-group col-md-2 p-none hrs">
                         <input type="text" class="form-control pr-xlg"/>  
                         <span>hr</span>
                         <div class="help-block with-errors red-txt"></div>
                    </div>
                    
                    <div class="clearfix"></div>
                    
                    <div class="blue-md-f text-uppercase">Results captured from the task</div>
                    <div class="pt-xs">
                        <div class="bullets bor-b-2-gray black-md-f pt-md pb-md">Duration over which kick count is recorded</div>
                        <div class="bullets black-md-f pt-md">Number of kicks recorded</div>
                        
                        <div class="pl-xlg ml-xs bor-l-1-gray mt-lg">
                        
                          <div class="mb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox1" value="option1">
                                <label for="inlineCheckbox1">Add to line chart</label>
                            </span>  
                          </div>   
                            
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
                            
                         <div class="pt-lg mt-xs pb-lg">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox1" value="option1">
                                <label for="inlineCheckbox1">Use for Statistic</label>
                            </span>  
                          </div>
                            
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
                    </div> -->
                    
                    </div> 
                </div>
                <!-- End Content-->  
                    
                    
                    
                  
                <!---  Schedule ---> 
                <div id="schedule" class="tab-pane fade mt-xlg">
                  <!-- <div class="gray-xs-f mb-sm">Questionnaire Frequency</div>
                    
                  <div class="pb-lg b-bor">
                    <span class="radio radio-info radio-inline p-40">
                        <input type="radio" id="inlineRadio1" class="schedule" value="oneTime" name="radioInline1" checked>
                        <label for="inlineRadio1">One Time</label>
                    </span>
                    <span class="radio radio-inline p-40">
                        <input type="radio" id="inlineRadio2" class="schedule" value="daily" name="radioInline1">
                        <label for="inlineRadio2">Daily</label>
                    </span>
                    <span class="radio radio-inline p-40">
                        <input type="radio" id="inlineRadio3" class="schedule" value="week" name="radioInline1">
                        <label for="inlineRadio3">Weekly</label>
                    </span>
                    <span class="radio radio-inline p-40">
                        <input type="radio" id="inlineRadio4" class="schedule" value="month" name="radioInline1">
                        <label for="inlineRadio4">Monthly</label>
                    </span>
                    <span class="radio radio-inline p-40">
                        <input type="radio" id="inlineRadio5" class="schedule" value="manually" name="radioInline1">
                        <label for="inlineRadio5">Manually Schedule</label>
                    </span>
                  </div>
                    
                  One Time Section    
                  <div class="oneTime all mt-xlg">
                      
                     <div class="gray-xs-f mb-sm">Date/Time of launch(pick one)</div>                      
                     <div class="mt-sm">
                        <span class="checkbox checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox2" value="option2">
                            <label for="inlineCheckbox2"> Launch with study</label>
                        </span>
                         
                        <div class="mt-md">
                            <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                                 <input id="chooseDate" type="text" class="form-control calendar" placeholder="Choose Date"/>
                            </span>
                            <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                                 <input id="selectTime" type="text" class="form-control clock" data-provide="timepicker" data-minute-step="1" data-modal-backdrop="true" placeholder="Select Time" onclick="timep()" />
                            </span>
                        </div>                         
                     </div> 
                      
                    <div class="gray-xs-f mb-sm mt-xlg">Lifetime of the run and of the questionnaire</div>                      
                     <div class="mt-sm">
                        <span class="checkbox checkbox-inline">
                            <input type="checkbox" id="inlineCheckbox1" value="option1">
                            <label for="inlineCheckbox1"> Study Lifetime</label>
                        </span>
                         
                        <div class="mt-md">
                            <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                                 <input id="chooseEndDate" type="text" class="form-control calendar" placeholder="Choose End Date"/>
                            </span>                             
                        </div>                         
                     </div>
                      
                  </div>
                    
                  Daily Section    
                  <div class="daily all mt-xlg dis-none">
                      <div class="gray-xs-f mb-sm">Time(s) of the day for daily occurrence</div>
                      <div class="mt-md">
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="time" type="text" class="form-control clock" placeholder="00:00"/>
                        </span> 
                        <span class="delete vertical-align-middle"></span>
                     </div>  
                      
                     <div class="mt-md">
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="time" type="text" class="form-control clock" placeholder="00:00"/>
                        </span> 
                        <span class="addbtn">+</span>
                     </div>
                      
                      <div class="mt-xlg">                        
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                            <input id="startDate" type="text" class="form-control mt-sm calendar" />
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
                    
                  
                  Weekly Section    
                  <div class="week all mt-xlg dis-none">
                      
                      <div>                        
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">Day/Time (of the week)</span><br/>                            
                            <input id="startDateWeekly" type="text" class="form-control mt-sm calendar" />
                        </span>
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">&nbsp;</span><br/>
                            <input id="starttimeWeekly" type="text" class="form-control mt-sm clock" />
                        </span>                        
                      </div>
                      
                      <div class="mt-xlg">                        
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                            <input id="startDate" type="text" class="form-control mt-sm calendar" />
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
                    
                    
                 Monthly Section    
                  <div class="month all mt-xlg dis-none">
                      
                      <div>                        
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">Select Date/Time (of the month)</span><br/>                            
                            <input id="startDateWeekly" type="text" class="form-control mt-sm calendar" />
                        </span>
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">&nbsp;</span><br/>
                            <input id="starttimeWeekly" type="text" class="form-control mt-sm clock" />
                        </span>
                        <div class="gray-xs-f mt-xs italic-txt text-weight-light">If the selected date is not available in a month, the last day of the month will be used instead</div>
                      </div>
                      
                      <div class="mt-xlg">                        
                        <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                            <span class="gray-xs-f">Start date (pick a date)</span><br/>                            
                            <input id="startDate" type="text" class="form-control mt-sm calendar" />
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
                    
                   Manually Section    
                  <div class="manually all mt-xlg dis-none">
                      <div class="gray-xs-f mb-sm">Select time period</div>
                      <div>
                         <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="StartDate" type="text" class="form-control calendar" placeholder="Start Date"/>
                         </span>
                         <span class="gray-xs-f mb-sm pr-md">
                            to 
                         </span>
                          <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="EndDate" type="text" class="form-control calendar" placeholder="End Date"/>
                         </span>
                         <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="EndDate" type="text" class="form-control clock" placeholder="Time"/>
                         </span>
                         <span id="delete" class="sprites_icon delete vertical-align-middle"></span>
                     </div>
                      
                     <div class="mt-md">
                         <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="StartDate" type="text" class="form-control calendar" placeholder="Start Date"/>
                         </span>
                         <span class="gray-xs-f mb-sm pr-md">
                            to 
                         </span>
                          <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="EndDate" type="text" class="form-control calendar" placeholder="End Date"/>
                         </span>
                         <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                             <input id="EndDate" type="text" class="form-control clock" placeholder="Time"/>
                         </span>
                         <span class="addbtn">+</span>
                     </div>
                      
                       <div class="mt-xlg">
                          <div class="gray-xs-f mb-xs">Default Lifetime of each run </div>
                          <div class="black-xs-f">As defined by the start and end times selected above</div>
                      </div>
                      
                  </div> -->
                    

                  
                </div>
               
              </div>

                
            </div>
            <!--  End body tab section -->
            
        </div>
        <!-- End right Content here -->
<script>
   $(document).ready(function(){  
            // Fancy Scroll Bar
            var changeTabSchedule = true;
           /*  $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"}); */
            
            $(".menuNav li.active").removeClass('active');
			$(".sixthTask").addClass('active');
            
			<c:if test="${actionPage eq 'view'}">
			    $('.targetOption').prop('disabled', true);
			    $('.targetOption').addClass('linkDis');
			    $('.actBut').hide();
            </c:if>
			
			var typeOfActiveTask = '${activeTaskBo.taskTypeId}';
		    var activeTaskInfoId = '${activeTaskBo.id}';
		    var actionType = '${actionPage}';
		    if(typeOfActiveTask && activeTaskInfoId)
		    loadSelectedATask(typeOfActiveTask, activeTaskInfoId, actionType);
            $(".schedule").click(function(){
                $(".all").addClass("dis-none");
                var schedule_opts = $(this).val();
                $("." + schedule_opts).removeClass("dis-none");
            });
            $( ".targetOption" ).change(function() {
          	    console.log($(this).val());
          	    $('.activeText').html('This task records fetal activity for a given duration of time, <br>in terms of the number of times the woman experiences kicks.');
          	    var typeOfActiveTask = $(this).val();
          	    var activeTaskInfoId = $(this).attr('taskId');
          	    loadSelectedATask(typeOfActiveTask, activeTaskInfoId, actionType);
          	});
          	$('.nav-tabs a[href="#schedule"]').on('show.bs.tab', function() {
          		if(changeTabSchedule){
          			$( "#schedule" ).load( "/fdahpStudyDesigner/adminStudies/viewScheduledActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), studyId : ""}, function() {
						$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskSchedule.do');
	          			resetValidation($(this).parents('form'));
					});
					changeTabSchedule = false;
          		} else {
          			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskSchedule.do');
	          		resetValidation($(this).parents('form'));
          		}
			});
			$('.nav-tabs a[href="#schedule"]').on('show.bs.tab', function() {
          		if(changeTabSchedule){
          			$( "#schedule" ).load( "/fdahpStudyDesigner/adminStudies/viewScheduledActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), studyId : ""}, function() {
// 						$(this).parents('form').attr('action','#');
	          			resetValidation($('form'));
					});
					changeTabSchedule = false;
          		} else {
//           			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskSchedule.do');
	          		resetValidation($('form'));
          		}
			});
			
			 $(".clock").datetimepicker({
	    	       format: 'HH:mm'
	           });
			 function loadSelectedATask(typeOfActiveTask, activeTaskInfoId, actionType){
				 $( ".changeContent" ).load( "/fdahpStudyDesigner/adminStudies/navigateContentActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), typeOfActiveTask : typeOfActiveTask, activeTaskInfoId : activeTaskInfoId, actionType: actionType}, function() {
		       			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do');
		       			resetValidation($(this).parents('form'));
					});
			 }
        }); 
	   function goToBackPage(item){
			//window.history.back();
			<c:if test="${actionPage ne 'view'}">
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
					        	a.href = "/fdahpStudyDesigner/adminStudies/viewStudyActiveTasks.do";
					        	document.body.appendChild(a).click();
					        }else{
					        	$(item).prop('disabled', false);
					        }
					    }
				});
			</c:if>
			<c:if test="${actionPage eq 'view'}">
				var a = document.createElement('a');
				a.href = "/fdahpStudyDesigner/adminStudies/viewStudyActiveTasks.do";
				document.body.appendChild(a).click();
			</c:if>
		}
</script>