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
					<c:if test="${not empty activeTaskBo.id && actionPage eq 'addEdit'}">Edit Active Task</c:if>
					<c:if test="${not empty activeTaskBo.id && actionPage eq 'view'}">View Active Task</c:if>
                    </div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn actBut" id="saveId">Save</button>
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
                <li class="contentClass active"><a data-toggle="tab" href="#content">Content</a></li>
                <li class="scheduleTaskClass"><a data-toggle="tab" href="#schedule">Schedule</a></li>                           
              </ul>
                <div class="tab-content pl-xlg pr-xlg">
                <!-- Content--> 
                <div id="content" class="tab-pane fade in active mt-xlg">
                    <div class="mt-md blue-md-f text-uppercase">Select Active Task</div>
                    <div class="gray-xs-f mt-md mb-sm">Choose from a list of pre-defined active tasks</div>
                    <div class="col-md-4 p-none">
                        <select class="selectpicker targetOption" taskId="${activeTaskBo.id}" title="Select">
                          <c:forEach items="${activeTaskListBos}" var="activeTaskTypeInfo">
	                          <option value="${activeTaskTypeInfo.activeTaskListId}" ${(activeTaskTypeInfo.activeTaskListId eq '2' || activeTaskTypeInfo.activeTaskListId eq '3') ?'disabled':''}${activeTaskBo.taskTypeId eq activeTaskTypeInfo.activeTaskListId ?'selected':''}>${activeTaskTypeInfo.taskName}</option>
                          </c:forEach>
                        </select>
                    </div> 
                    <div class="clearfix"></div>
                    <div class="mt-sm black-xs-f italic-txt activeText"> </div>
                    
                    <div class="changeContent"> </div> 
                </div>
                <!-- End Content-->  
                <!---  Schedule ---> 
                <div id="schedule" class="tab-pane fade mt-xlg"> </div>
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
			actionPageView();
            
            
			var typeOfActiveTask = '${activeTaskBo.taskTypeId}';
		    var activeTaskInfoId = '${activeTaskBo.id}';
		    var actionType = '${actionPage}';
		    var selectedTask = $('.targetOption').find("option:selected").text();
		    
		    
		    if(activeTaskInfoId){
		    	$('.targetOption').prop('disabled', true);
		    	$('.targetOption').addClass('linkDis');
		    	$('.activeText').html('This task records fetal activity for a given duration of time, <br>in terms of the number of times the woman experiences kicks.');
		    }else{
		    	$('.actBut').hide();
		    	$('.scheduleTaskClass').prop('disabled', true);
			    $('.scheduleTaskClass').addClass('linkDis');
		    }
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
          	    $('.actBut').show();
          	});
            if(activeTaskInfoId || selectedTask){
            	loadActiveSchedule(changeTabSchedule);
            }
			function loadSelectedATask(typeOfActiveTask, activeTaskInfoId, actionType){
				 $( ".changeContent" ).load( "/fdahpStudyDesigner/adminStudies/navigateContentActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), typeOfActiveTask : typeOfActiveTask, activeTaskInfoId : activeTaskInfoId, actionType: actionType},
						 function() {
		       			$(this).parents('form').attr('action','/fdahpStudyDesigner/adminStudies/saveOrUpdateActiveTaskContent.do');
		       			resetValidation($(this).parents('form'));
		       			actionPageView();
					});
				 
			 }
			 function loadActiveSchedule(changeTabSchedule){
				 if(changeTabSchedule){
	          			$( "#schedule" ).load( "/fdahpStudyDesigner/adminStudies/viewScheduledActiveTask.do?${_csrf.parameterName}=${_csrf.token}", {noncache: new Date().getTime(), activeTaskId : activeTaskInfoId}, function() {
		          			resetValidation($('form'));
		          			actionPageView();
						});
						changeTabSchedule = false;
	          		} else {
		          		resetValidation($('form'));
	          		}
			 }
			 $('#tabsId a').click(function(e) {
				  e.preventDefault();
				  $(this).tab('show');
				});
				
				// store the currently selected tab in the hash value
				$("ul.nav-tabs > li > a").on("shown.bs.tab", function(e) {
				  var id = $(e.target).attr("href").substr(1);
				  window.location.hash = id;
				  $('#currentPageId').val(id);
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
	   function actionPageView() {
		   <c:if test="${actionPage eq 'view'}">
	       	$(document).find('input,textarea,select').prop('disabled', true);
		    $(document).find('form.elaborateClass').addClass('linkDis');
		    //$('.targetOption').prop('disabled', true);
		    //$('.targetOption').addClass('linkDis');
		    $(document).find('.actBut, .addBtnDis, .remBtnDis').remove();
	      </c:if>
	   }
	   
</script>