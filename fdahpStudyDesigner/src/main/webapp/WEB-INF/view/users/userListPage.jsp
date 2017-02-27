<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<body>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
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
                 <div class="form-group mb-none mt-sm">
                     <button type="button" class="btn btn-primary blue-btn addOrEditUser"><span class="mr-xs">+</span> Add User</button>
                 </div>
             </c:if>
             </div>
            
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
</div>

<div class="clearfix"></div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none"> 
    <div class="md-container white-bg">
        <div class="table-responsive">
            <table id="user_list" class="table">
            <thead>
              <tr>
                <th>Name <span class="sort"></span></th>
                <th>e-mail address</th>
                <th>ROLE <span class="sort"></span></th>
                <%-- <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}"> --%>
                <th>Actions</th>
                <%-- </c:if --%>
              </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
              <tr>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.userEmail}</td>
                <td>${user.roleName}</td>
                <td>
                	<span class="sprites_icon preview-g mr-lg viewUser" userId="${user.userId}"></span>
                	<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">
                    <span class="sprites_icon edit-g addOrEditUser" userId="${user.userId}"></span>
                    <span class="ml-lg">
                    <c:if test="${user.userPassword eq ''}">
                        <label class="switch">
                          <input type="checkbox" class="switch-input" value="${user.enabled ? 1 : 0}" id="${user.userId}" 
                          <c:if test="${user.enabled}">checked</c:if> onchange="activateOrDeactivateUser(${user.userId})">
                          <span class="switch-label" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </c:if>
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
</body>

<form:form action="/fdahpStudyDesigner/adminUsersEdit/addOrEditUserDetails.do" id="addOrEditUserForm" name="addOrEditUserForm" method="post">
	<input type="hidden" id="userId" name="userId" value="">
</form:form>

<form:form action="/fdahpStudyDesigner/adminUsersView/viewUserDetails.do" id="viewUserForm" name="viewUserForm" method="post">
	<input type="hidden" id="usrId" name="userId" value="">
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	
	$('#users').addClass('active');
	
	 //User_List page Datatable
    $('#user_list').DataTable({
        "paging":   true,
        "aoColumns": [
           { "bSortable": true },
           { "bSortable": false },
           { "bSortable": true },
           { "bSortable": false }
          ],  
        "info" : false, 
        "lengthChange": false, 
        "searching": false, 
        "pageLength": 10 
    });
	 
	$('.addOrEditUser').on('click',function(){
			$('#userId').val($(this).attr('userId'));
			$('#addOrEditUserForm').submit();
	});
	
	$('.viewUser').on('click',function(){
		alert($(this).attr('userId'));
			$('#usrId').val($(this).attr('userId'));
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
	
});

function activateOrDeactivateUser(userId){
	var status = $('#'+userId).val();
	var msgPart = "";
	if("0" == status){
		msgPart = "activate";
	} else if("1" == status){
		msgPart = "deactivate";
	}
    /* bootbox.confirm("Are you sure you want to " + msgPart + " this user?", function(result){
	 if(result){ */
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
					if(status == "0"){
						$('#'+userId).val("1");
					}else{
						$('#'+userId).val("0");
					}
				}else {
					if("0" == status){
						$('#'+userId).prop('checked', false);
					} else if("1" == checked){
						$('#'+userId).prop('checked', true);
					}
				}
			}
		});
/*   } else {
		if("0" == status){
			$('#'+aspId).prop('checked', false);
		} else if("1" == checked){
			$('#'+aspId).prop('checked', true);
		}
		return;
	}
	 	});  */
}
</script>
