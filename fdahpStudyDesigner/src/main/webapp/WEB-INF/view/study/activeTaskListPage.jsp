<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">ACTIVE TASKS</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Cancel</button>
                     </div>
                    
                     <!-- <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Save</button>
                     </div> -->

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn" <c:if test="${empty activeTasks}"> disabled </c:if> >Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                <div>
                    <table id="questionnaire_list" class="display bor-none dragtbl" cellspacing="0" width="100%">
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
			                  <td>${activeTasksInfo.title}</td>
			                  <td>${activeTasksInfo.type}</td>
			                  <td>${activeTasksInfo.frequency}</td>
			                  <td>
			                     <span class="sprites_icon edit-g mr-lg"></span>
			                     <span class="sprites_icon copy delete"></span>
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
</form:form>        
<script>
$(document).ready(function(){  
			$(".menuNav li.active").removeClass('active');
			$(".sixthTask").addClass('active');
	
            // Fancy Scroll Bar
            $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
            
             $('#questionnaire_list').DataTable( {
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
	$("#activeTaskInfoId").val('');
	$("#activeTaskInfoForm").submit();
}      
                 
</script>     
        
        