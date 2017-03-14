<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <div class="right-content">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Notifications</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn studyListPageFromNotification">Cancel</button>
                     </div>
                    
                     <!-- <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Save</button>
                     </div> -->

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn markCompleted" onclick="markAsCompleted();"
                         <c:if test="${not empty notificationSavedList}">disabled</c:if>>Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                <div>
                    <table id="notification_list" class="display bor-none tbl_rightalign" cellspacing="0" width="100%">
                         <thead>
                            <tr>
                                <th>Title</th>                               
                                <th>
                                    <div class="dis-line form-group mb-none">
                                         <button type="button" class="btn btn-primary blue-btn studyNotificationDetails">+ Add Notification</button>
                                     </div>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${notificationList}" var="studyNotification">
	                            <tr id="${studyNotification.notificationId}">
	                                <td>${studyNotification.notificationText}</td>
	                                <td>
	                                   <!--  <span class="sprites-icons-2 send mr-lg"></span>-->
	                                    <span class="sprites_icon preview-g mr-lg studyNotificationDetails" actionType="view" notificationId="${studyNotification.notificationId}"></span>
	                                    <span class="sprites_icon edit-g mr-lg studyNotificationDetails" actionType="edit" notificationId="${studyNotification.notificationId}"></span>
	                                    <span class="sprites_icon copy studyNotificationDetails" actionType="addOrEdit" notificationText="${studyNotification.notificationText}"></span>   
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
<form:form action="/fdahpStudyDesigner/adminStudies/getStudyNotification.do" id="getStudyNotificationEditPage" name="getNotificationEditPage" method="post">
		<input type="hidden" id="notificationId" name="notificationId">
		<input type="hidden" id="notificationText" name="notificationText">
		<input type="hidden" id="actionType" name="actionType">
		<input type="hidden" name="chkRefreshflag" value="y">
</form:form>

<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="studyListPage" id="studyListPage" method="post">
</form:form>        

<form:form action="/fdahpStudyDesigner/adminStudies/notificationMarkAsCompleted.do" name="notificationMarkAsCompletedForm" id="notificationMarkAsCompletedForm" method="post">
<%-- <input type="hidden" name="studyId" id="studyId" value="${studyId}" /> --%>
</form:form>
    <script>
        $(document).ready(function(){ 
        	$(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
            $(".menuNav li").removeClass('active');
            $(".eigthNotification").addClass('active'); 
            $("#createStudyId").show();
            $('.eigthNotification').removeClass('cursor-none');
            
        	$('.studyNotificationDetails').on('click',function(){
        		$('.studyNotificationDetails').addClass('cursor-none');
    			$('#notificationId').val($(this).attr('notificationId'));
    			$('#notificationText').val($(this).attr('notificationText'));
    			$('#actionType').val($(this).attr('actionType'));
    			$('#getStudyNotificationEditPage').submit();
    			
    		});
        	
        	$('.studyListPageFromNotification').on('click',function(){
        		$('.studyListPageFromNotification').prop('disabled', true);
      			$('#studyListPage').submit();
      		});
        	
             var table = $('#notification_list').DataTable({              
              "paging":   true,              
              "info" : false, 
              "lengthChange": false, 
              "searching": false, 
              "pageLength": 15   
           });
            
     });
        
        function markAsCompleted(){
    		/* var table = $('#notification_list').DataTable()
    		if (!table.data().count() ) {
    		    alert( 'Add atleast one notification !' );
    		}else{ */
    			$('.markCompleted').prop('disabled', true);
    			$("#notificationMarkAsCompletedForm").submit();
    			//alert( 'NOT Empty table' );
    		/* } */
    	}         
    </script>
