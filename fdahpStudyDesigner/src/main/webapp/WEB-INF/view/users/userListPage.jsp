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
                     <button type="button" class="btn btn-primary blue-btn addOrEditUser"><span class="mr-xs">+</span> Add User</button>
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
    <%-- <div id="displayMessage">
	    <div id="errMsg" class="text-center e-box p-none">${errMsg}</div>
	    <div id="sucMsg" class="text-center s-box p-none">${sucMsg}</div>
	</div> --%>
</div>
<!-- <div class="clearfix"></div> -->
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="white-bg">
        <div class="table-responsive">
            <table id="user_list" class="table tbl_rightalign tbl">
            <thead>
              <tr>
                <th>Name <span class="sort"></span></th>
                <th>Email  address <span class="sort"></span></th>
                <th>ROLE <span class="sort"></span></th>
                <%-- <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}"> --%>
                <th>Actions</th>
                <%-- </c:if --%>
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
                    <span class="sprites_icon edit-g addOrEditUser" userId="${user.userId}" data-toggle="tooltip" data-placement="top" title="Edit" ></span>
                    <span class="ml-lg">
                        <label class="switch" data-toggle="tooltip" id="label${user.userId}" data-placement="top"<c:if test="${empty user.userPassword}">title="Status: Invitation Sent, Account Activation Pending"</c:if>
                        <c:if test="${not empty user.userPassword && user.enabled}">title="Status: Active"</c:if>
                        <c:if test="${not empty user.userPassword &&  not user.enabled}">title="Status: Deactivated"</c:if>>
                          <input type="checkbox" class="switch-input" value="${user.enabled ? 1 : 0}" id="${user.userId}"
                          <c:if test="${user.enabled}">checked</c:if> onchange="activateOrDeactivateUser(${user.userId})" <c:if test="${empty user.userPassword}">disabled</c:if>
                          >
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
						/* $("#sucMsg").html('User successfully deactivated.'); */
						showSucMsg('User successfully deactivated.');
						$('#'+userId).val("0");
						/* $("#sucMsg").show();
						$("#errMsg").hide(); */
						$('#label'+userId).attr('data-original-title','Status: Deactivated');
					}else{
						/* $("#sucMsg").html('User successfully activated.'); */
						showSucMsg('User successfully activated.');
						$('#'+userId).val("1");
						/* $("#sucMsg").show();
						$("#errMsg").hide(); */
						$('#label'+userId).attr('data-original-title','Status: Active');
					}
					/* if(status == "0"){
						$('#'+userId).val("1");
					}else{
						$('#'+userId).val("0");
					} */
				}else {
					/* $("#errMsg").html('Failed to update. Please try again.'); */
					showErrMsg('Failed to update. Please try again.');
					/* $("#sucMsg").hide();
					$("#errMsg").show(); */
					if("0" == status){
						$('#'+userId).prop('checked', false);
					} else if("1" == checked){
						$('#'+userId).prop('checked', true);
					}
				}
				//setTimeout(hideDisplayMessage, 4000);
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

/* function hideDisplayMessage(){
	$('#sucMsg').hide();
	$('#errMsg').hide();
} */
/* function hideDisplayMessage(){
	$('#alertMsg').hide();
} */
</script>
