<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 grayeef2f5-bg p-none">
     <div>
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none mb-md">
         
            <div class="black-lg-f">
                Manage Users
            </div>
             
            <%--  <div class="dis-inline">
              <form class="navbar-form" role="search">
                <div class="input-group add-on">
                  <input class="form-control selectpicker" placeholder="Search" name="srch-term" id="srch-term" type="text">
                  <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                  </div>
                </div>
              </form>
             </div> --%>
             
             <div class="dis-line pull-right ml-md">
             <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">
                 <div class="form-group mb-none mt-xs">
                 	 <button type="button" class="btn btn-default gray-btn mr-sm" id="enforcePasswordId">Enforce Password Change</button>                 	
                     <button type="button" class="btn btn-primary blue-btn addOrEditUser">Add User</button>
                 </div>
             </c:if>
             </div>
            <!--  <div class="text-center"> 
       			<div class="" id="alertMsg">YES</div>
        	 </div> -->
            
            <!--  <div class="dis-line pull-right">
              <div class="form-group mb-none mt-sm">
                  <select class="form-control selectpicker btn-md" id="sel1">
                    <option disabled selected>Filter by Role</option>
                    <option>Project Lead</option>
                    <option>Coordinator</option>
                    <option>Recruiter</option>
                    <option>Project Lead</option>
                  </select>
                </div>
             </div> -->
                      
         </div>         
    </div>
    <div  class="clearfix"></div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="white-bg">
        <div>
            <table id="user_list" class="table tbl_rightalign tbl">
            <thead>
              <tr>
                <th>Name <span class="sort"></span></th>
                <th>Email  address <span class="sort"></span></th>
                <th>ROLE <span class="sort"></span></th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
              <tr>
                <td><div class="dis-ellipsis" title="${fn:escapeXml(user.userFullName)}">${fn:escapeXml(user.userFullName)}</div></td>
                <td>
                	<div class="dis-ellipsis" title="${user.userEmail}">${user.userEmail}</div>
                </td>
                <td>${user.roleName}</td>
                <td>
                	<span class="sprites_icon preview-g mr-lg viewUser" userId="${user.userId}" data-toggle="tooltip" data-placement="top" title="View"></span>
                	<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">
	                    <span class="sprites_icon edit-g addOrEditUser <c:if test='${not empty user.userPassword &&  not user.enabled}'>cursor-none</c:if>" 
	                    	userId="${user.userId}" data-toggle="tooltip" data-placement="top" title="Edit"  id="editIcon${user.userId}">
                    	</span>
	                    <span class="ml-lg">
	                        <label class="switch" data-toggle="tooltip" id="label${user.userId}" data-placement="top"
	                        <c:if test="${empty user.userPassword}">title="Status: Invitation Sent, Account Activation Pending"</c:if>
	                        <c:if test="${not empty user.userPassword && user.enabled}">title="Status: Active"</c:if>
	                        <c:if test="${not empty user.userPassword &&  not user.enabled}">title="Status: Deactivated"</c:if> >
	                          <input type="checkbox" class="switch-input" value="${user.enabled ? 1 : 0}" id="${user.userId}"
	                          <c:if test="${user.enabled}">checked</c:if> onchange="activateOrDeactivateUser(${user.userId})" 
	                          <c:if test="${empty user.userPassword}">disabled</c:if> >
	                          <span class="switch-label" data-on="On" data-off="Off"></span>
	                          <span class="switch-handle"></span>
	                        </label>
	                    </span>
                    </c:if>
                </td> 
              </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
  </div>
</div>

<form:form action="/fdahpStudyDesigner/adminUsersEdit/addOrEditUserDetails.do" id="addOrEditUserForm" name="addOrEditUserForm" method="post">
	<input type="hidden" id="userId" name="userId" value="">
	<input type="hidden" id="checkRefreshFlag" name="checkRefreshFlag">
</form:form>

<form:form action="/fdahpStudyDesigner/adminUsersView/viewUserDetails.do" id="viewUserForm" name="viewUserForm" method="post">
	<input type="hidden" id="usrId" name="userId" value="">
	<input type="hidden" id="checkViewRefreshFlag" name="checkViewRefreshFlag">
</form:form>

<form:form action="/fdahpStudyDesigner/adminUsersEdit/forceLogOut.do" id="forceLogOutForm" name="forceLogOutForm" method="post">
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	$('#rowId').parent().removeClass('#white-bg');
	
	$('#users').addClass('active');
	
	$('[data-toggle="tooltip"]').tooltip();		
	
	<c:if test="${ownUser eq '1'}">
		bootbox.alert({
			closeButton: false,
			message : 'Your user account details have been updated. Please sign in again to continue using the portal.',	
		    callback: function(result) {
		        	$('#forceLogOutForm').submit();
		    }
	    });
	</c:if>
	
	$('.addOrEditUser').on('click',function(){
			$('#userId').val($(this).attr('userId'));
			$('#checkRefreshFlag').val('Y');
			$('#addOrEditUserForm').submit();
	});
	
	$('.viewUser').on('click',function(){
			$('#usrId').val($(this).attr('userId'));
			$('#checkViewRefreshFlag').val('Y');
			$('#viewUserForm').submit();
	});

    //datatable icon toggle
   /*  $("#user_list thead tr th").click(function(){
      $(this).children().removeAttr('class')
      $(this).siblings().children().removeAttr('class').addClass('sort');    
      if($(this).attr('class') == 'sorting_asc'){
        $(this).children().addClass('asc'); 
        //alert('asc');
      }else if($(this).attr('class') == 'sorting_desc'){
       $(this).children().addClass('desc');
        //alert('desc');
      }else{
        $(this).children().addClass('sort');
      }
    }); */
    
	  
    
	    $('#enforcePasswordId').on('click',function(){
	    	bootbox.confirm({
					closeButton: false,
					message : "Are you sure you wish to enforce a password change for all users?",	
				    buttons: {
				        'cancel': {
				            label: 'No',
				        },
				        'confirm': {
				            label: 'Yes',
				        },
				    },
				    callback: function(result) {
				        if (result) {
				        	var form= document.createElement('form');
			            	form.method= 'post';
			            	var input= document.createElement('input');
			            	input.type= 'hidden';
			        		input.name= 'changePassworduserId';
			        		input.value= '';
			        		form.appendChild(input);
			        		
			        		var input= document.createElement('input');
			            	input.type= 'hidden';
			        		input.name= 'emailId';
			        		input.value= '';
			        		form.appendChild(input);
			        		
			        		input= document.createElement('input');
			            	input.type= 'hidden';
			        		input.name= '${_csrf.parameterName}';
			        		input.value= '${_csrf.token}';
			        		form.appendChild(input);
			        		
			            	form.action= '/fdahpStudyDesigner/adminUsersEdit/enforcePasswordChange.do';
			            	document.body.appendChild(form);
			            	form.submit();
				             }	
				        }
			}) 
	    	
	     });
	

		 //User_List page Datatable
		    $('#user_list').DataTable({
		        "paging":   true,
		        "aoColumns": [
		           { "bSortable": true },
		           { "bSortable": true },
		           { "bSortable": true },
		           { "bSortable": false }
		          ],  
		        "info" : false, 
		        "lengthChange": false, 
		        "searching": false, 
		        "pageLength": 15 
		    });
});



function activateOrDeactivateUser(userId){
	var status = $('#'+userId).val();
	var msgPart = "";
	if("0" == status){
		msgPart = "activate";
	} else if("1" == status){
		msgPart = "deactivate";
	}
  	 bootbox.confirm("Are you sure you want to " + msgPart + " this user?", function(result){
		 if(result){
			$.ajax({
				url : "/fdahpStudyDesigner/adminUsersEdit/activateOrDeactivateUser.do",
				type : "POST",
				datatype : "json",
				data : {
					userId : userId,
					userStatus : status,
					"${_csrf.parameterName}":"${_csrf.token}"
				},
				success : function(data) {
					var jsonObj = eval(data);
					var message = jsonObj.message;
					if(message == 'SUCCESS'){
						if(status == 1){
							showSucMsg('User successfully deactivated.');
							$('#'+userId).val("0");
							$('#label'+userId).attr('data-original-title','Status: Deactivated');
							$('#editIcon'+userId).addClass('cursor-none');
						}else{
							showSucMsg('User successfully activated.');
							$('#'+userId).val("1");
							$('#label'+userId).attr('data-original-title','Status: Active');
							$('#editIcon'+userId).removeClass('cursor-none');
						}
					}else {
						showErrMsg('Failed to update. Please try again.');
						if("0" == status){
							$('#'+userId).prop('checked', false);
						} else if("1" == checked){
							$('#'+userId).prop('checked', true);
						}
					}
				}
			});
	 	} else {
			if("0" == status){
				$('#'+userId).prop('checked', false);
			} else if("1" == status){
				$('#'+userId).prop('checked', true);
			}
			return;
		}
 	});
}
</script>
