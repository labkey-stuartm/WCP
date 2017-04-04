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
                
             <ul class="nav nav-tabs review-tabs gray-bg" id="tabsId">
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
          			$( "#schedule" ).load( "/fdahpStudyDesigner/adminStudies/viewScheduledActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), activeTaskId : activeTaskInfoId}, function() {
// 						$(this).parents('form').attr('action','#');
	          			resetValidation($('form'));
					});
					changeTabSchedule = false;
          		} else {
//           			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskSchedule.do');
	          		resetValidation($('form'));
          		}
			});
			 function loadSelectedATask(typeOfActiveTask, activeTaskInfoId, actionType){
				 $( ".changeContent" ).load( "/fdahpStudyDesigner/adminStudies/navigateContentActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), typeOfActiveTask : typeOfActiveTask, activeTaskInfoId : activeTaskInfoId, actionType: actionType}, function() {
		       			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do');
		       			resetValidation($(this).parents('form'));
		       			
		       			
		       			var a = $(".col-lc").height();
		       			var b = $(".col-rc").height();
		       			if(a > b){
		       			$(".col-rc").css("height", a);	
		       			}else{
		       			$(".col-rc").css("height", "auto");
		       			}
					});
				 
			 }
			 
			 $('#tabsId a').click(function(e) {
				  e.preventDefault();
				  $(this).tab('show');
				});
				
				// store the currently selected tab in the hash value
				$("ul.nav-tabs > li > a").on("shown.bs.tab", function(e) {
				  var id = $(e.target).attr("href").substr(1);
				  window.location.hash = id;
				});
				
				// on load of the page: switch to the currently selected tab
				var hash = window.location.hash;
				$('#tabsId a[href="' + hash + '"]').tab('show');
			window.addEventListener("popstate", function(e) {
				var activeTab = $('[href="' + window.location.hash + '"]');
				if (activeTab.length) {
				  activeTab.tab('show');
				} else {
				  $('.nav-tabs a:first').tab('show');
				}
			});
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