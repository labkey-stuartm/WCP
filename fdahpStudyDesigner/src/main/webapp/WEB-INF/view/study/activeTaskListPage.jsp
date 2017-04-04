<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
       <div class="col-sm-10 col-rc white-bg p-none">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">ACTIVE TASKS</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
                     </div>
                    
                     <!-- <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Save</button>
                     </div> -->

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn" id="markAsComp" <c:if test="${empty activeTasks}"> disabled </c:if> >Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                <div>
                    <table id="activedatatable_list" class="display bor-none dragtbl" cellspacing="0" width="100%">
                         <thead>
                            <tr>
                                <th>TITLE<span class="sort"></span></th>
                                <th>TYPE<span class="sort"></span></th>
                                <th>FREQUENCY</th>                                
                                <th>
                                    <div class="dis-line form-group mb-none">
                                         <button type="button" class="btn btn-primary blue-btn" onclick="addActiveTaskPage();">+ Add Active Task</button>
                                     </div>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${activeTasks}" var="activeTasksInfo">
		             	    <tr id="row${activeTasksInfo.id}">
			                  <td>${activeTasksInfo.shortTitle}</td>
			                  <td>${activeTasksInfo.type}</td>
			                  <td>${activeTasksInfo.frequency}</td>
			                  <td>
			                     <span class="sprites_icon preview-g mr-lg" onclick="viewTaskInfo(${activeTasksInfo.id});"></span>
			                     <span class="sprites_icon edit-g mr-lg <c:if test="${not empty permission}"> cursor-none </c:if>" id="editTask" onclick="editTaskInfo(${activeTasksInfo.id});"></span>
			                     <span class="sprites_icon copy delete <c:if test="${not empty permission}"> cursor-none </c:if>" id="delTask" onclick="deleteTaskInfo(${activeTasksInfo.id});"></span>
			                  </td>
			               </tr>
			             </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!--  End body tab section -->
            
            
            
            
        </div>
        <!-- End right Content here -->
<form:form action="/fdahpStudyDesigner/adminStudies/viewActiveTask.do" name="activeTaskInfoForm" id="activeTaskInfoForm" method="post">
<input type="hidden" name="activeTaskInfoId" id="activeTaskInfoId" value="">
<input type="hidden" name="actionType" id="actionType">
<input type="hidden" name="studyId" id="studyId" value="${studyBo.id}" />
</form:form>        
<script>
var dataTable;
$(document).ready(function(){  
			$(".menuNav li.active").removeClass('active');
			$(".sixthTask").addClass('active');
	
            // Fancy Scroll Bar
           /*  $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"}); */
            
            dataTable = $('#activedatatable_list').DataTable( {
                 "paging":   true,
                 "abColumns": [
                   { "bSortable": true },
                   { "bSortable": true },
                   { "bSortable": false }
                   ],
                   "order": [[ 0, "desc" ]],
                 "info" : false, 
                 "lengthChange": false, 
                 "searching": false, 
                 "pageLength": 10 
             } );
  });
function addActiveTaskPage(){
	$("#actionType").val('addEdit');
	$("#activeTaskInfoId").val('');
	$("#activeTaskInfoForm").submit();
}
function viewTaskInfo(taskInfoId){
	if(taskInfoId != null && taskInfoId != '' && typeof taskInfoId !='undefined'){
		$("#actionType").val('view');
		$("#activeTaskInfoId").val(taskInfoId);
		$("#activeTaskInfoForm").submit();
	}
}
function editTaskInfo(taskInfoId){
	if(taskInfoId != null && taskInfoId != '' && typeof taskInfoId !='undefined'){
		$('#editTask').addClass('cursor-none');
		$("#actionType").val('addEdit');
		$("#activeTaskInfoId").val(taskInfoId);
		$("#activeTaskInfoForm").submit();
	}
}
function deleteTaskInfo(activeTaskInfoId){
	$('#delTask').addClass('cursor-none');
	bootbox.confirm("Are you sure want to delete Active Task!", function(result){ 
		if(result){
	    	if(activeTaskInfoId != '' && activeTaskInfoId != null && typeof activeTaskInfoId != 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteActiveTask.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				activeTaskInfoId: activeTaskInfoId,
	    				studyId : '${studyId}',
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteActiveInfo(data){
	    				var status = data.message;
	    				var resourceSaved = data.resourceSaved;
	    				if(status == "SUCCESS"){
							dataTable
	    			        .row($('#row'+activeTaskInfoId))
	    			        .remove()
	    			        .draw();
	    					if(resourceSaved){
	    						$('#markAsComp').prop('disabled',true);
	    						$('[data-toggle="tooltip"]').tooltip();
	    					}else{
	    						$('#markAsComp').prop('disabled',false);
	    						$('[data-toggle="tooltip"]').tooltip('destroy');
	    					}
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("ActiveTask deleted successfully");
	    					$('#alertMsg').show();
	    					/* reloadData(studyId); */
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete resource");
	    					$('#alertMsg').show();
	    	            }
	    				setTimeout(hideDisplayMessage, 4000);
	    			},
	    			error: function(xhr, status, error) {
	    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
	    			  setTimeout(hideDisplayMessage, 4000);
	    			}
	    		});
	    	}
		}
	});
	$('#delTask').removeClass('cursor-none');
}
</script>     
        
        