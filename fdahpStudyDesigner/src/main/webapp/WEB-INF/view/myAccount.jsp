<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              My Account
            </div>
            <div id="errMsg" class="error_msg">${errMsg}</div>
         	<div id="sucMsg" class="suceess_msg">${sucMsg}</div>
            <div class="dis-line pull-right ml-md line34">
                <a href="javascript:formSubmit();" class="blue-link text-weight-normal text-uppercase"><span>Log Out</span> <span class="ml-xs"><img src="/fdahpStudyDesigner/images/icons/logout.png"/></span></a>  
           </div>
         </div>   
    </div>
</div>
   
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mb-lg">
     <div class="md-container white-bg box-space">
         <form:form action="/fdahpStudyDesigner/adminDashboard/updateUserDetails.do?${_csrf.parameterName}=${_csrf.token}" id="userDetailsForm" name="userDetailsForm" role="form" autocomplete="off" data-toggle="validator" method="post">
         <%-- <input type="hidden" name="userId" value="${userBO.userId}"> --%>
         <div class="b-bor">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">First Name</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans" name="firstName" value="${userBO.firstName}" oldVal="${userBO.firstName}" maxlength="50" required/>
                        	<div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>                
             </div>
        </div>
        
        <div class="b-bor mt-md">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">Last Name</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans" name="lastName" value="${userBO.lastName}" oldVal="${userBO.lastName}" maxlength="50" required readonly />
                        	<div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>                
             </div>
        </div>
         
         <div class="b-bor mt-md">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">E-mail Address</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans validateUserEmail" name="userEmail" value="${userBO.userEmail}" oldVal="${userBO.userEmail}" maxlength="100" required readonly/>
                        	<div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>                
             </div>
        </div>
         
         <div class="b-bor mt-md">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">Phone Number</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans phoneMask" name="phoneNumber" value="${userBO.phoneNumber}" oldVal="${userBO.phoneNumber}" maxlength="12" required readonly/>
                        	<div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>                
             </div>
        </div>
         
         <div class="b-bor mt-md">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">Role</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans" name="roleName" value="${userBO.roleName}" maxlength="20" readonly/>
                        	<div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>                
             </div>
        </div>
         
         <div class="b-bor mt-md mb-md">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">Password</div>
                    </div>
                    <div class="col-md-6 p-none mt-xs mb-lg">
                        <a id="pwd-link" class="blue-link txt-decoration-underline pl-sm">Change Password</a>
                        
                        <div class="changepwd pl-sm pt-md dis-none">
                             <div class="gray-xs-f line34">Old Password</div>
                              <div class="form-group mb-none">
                                <input type="password" class="form-control emptyField" id="oldPassword" name="oldPassword" data-error="Password is invalid"/>
                              	<!-- <div class="help-block with-errors red-txt"></div> -->
                              </div>
                              
                              <div class="gray-xs-f line34">New Password</div>
                              <div class="form-group mb-none">
                                <input type="password" class="form-control emptyField" id="password" maxlength="20" data-error="This field shouldn't be empty" autocomplete="off" name="password" />
                              	<!-- <div class="help-block with-errors red-txt"></div> -->
                              </div>
                            
                              <div class="gray-xs-f line34">Confirm Password</div>
                              <div class="form-group mb-none">
                                <input type="password" class="form-control emptyField" id="conpassword" data-match="#password" data-error="Password don't match" autocomplete="off" data-minlength="6" maxlength="14" />
                              	<!-- <div class="help-block with-errors red-txt"></div> -->
                              </div>
                            
                               <div class="dis-line form-group mt-md mb-none">
                                   <button type="button" class="btn btn-default gray-btn mr-sm" id="cancelBtn">Cancel</button>
                                   <button type="button" class="btn btn-primary blue-btn" id="updateBtn">Update</button>
                               </div>
                        </div>
                    </div>                
             </div>
        </div>
         
         <div class="mt-xlg">
              <div class="text-right"> 
                   <div class="dis-line form-group mb-none">
                        <button id="editable" type="button" class="btn btn-primary blue-btn">Edit</button>
                        <button id="ed-cancel" type="button" class="btn btn-default gray-btn dis-none">Cancel</button>
                        <button id="ed-update" type="submit" class="btn btn-primary blue-btn dis-none">Update</button>
                    </div>
             </div>
         </div>
        </form:form>
    </div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mb-md">
     <div class="md-container white-bg box-space">
         
         <div class="ed-user-layout row"> 
             <div class="blue-md-f text-uppercase mb-md">Assigned Permissions</div>
             
             <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget mb-xs">
                 <span>Manage Users</span>
                 <span class="gray-xs-f pull-right"><c:if test="${!fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">View Only</c:if><c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">View & Edit</c:if></span>
             </div>
             
             <div class="edit-user-list-widget mb-xs">
                 <span>Manage App-Wide Notifications</span>
                 <span class="gray-xs-f pull-right"><c:if test="${!fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">View Only</c:if><c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">View & Edit</c:if></span>
             </div>
             
             <!-- Assigned Permissions List-->
            <%--  <div class="edit-user-list-widget mb-xs">
                 <span>Manage Studies</span>
                 <span class="gray-xs-f pull-right"><c:if test="${!fn:contains(sessionObject.userPermissions,'ROLE_CREATE_MANAGE_STUDIES')}">View Only</c:if><c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_CREATE_MANAGE_STUDIES')}">View & Edit</c:if></span>
             </div> --%>
             
              <!-- Assigned Permissions List-->
             
             <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget">
                 <span>Manage Studies</span>
                 
                 <div class="mt-lg pl-md">
                 	<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_CREATE_MANAGE_STUDIES')}">
	                    <div class="pb-md bor-dashed">
	                        <span class="dot">Adding a New Study</span> 
	                    </div>
                    </c:if>
                     
                     <div class="pl-sm pt-md">
                        <span class="gray-xs-f text-weight-semibold text-uppercase">Existing Studies</span>
                     </div>
                     <c:forEach items="${studyAndPermissionList}" var="studyAndPermission">
	                     <div class="pt-sm pb-sm pl-sm b-bor-dark">
	                            <span class="dot" id="${studyAndPermission.customStudyId}">${studyAndPermission.name}</span>
	                            <span class="gray-xs-f pull-right"><c:if test="${not studyAndPermission.viewPermission}">View Only</c:if><c:if test="${studyAndPermission.viewPermission}">View & Edit</c:if></span>
	                     </div>
                    </c:forEach>
                 </div>
                 
             </div>
             
         </div>
    </div>
</div>
<input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<script>
	  $(document).ready(function(){   
	      
		  /* Profile buttons starts */
		// Edit & Update button toggling
          $("#editable").click(function(){
            $(".edit-field").prop('readonly', false).removeClass("bor-trans");
            $("#ed-cancel,#ed-update").removeClass("dis-none");
            $("#editable").addClass("dis-none");
            $("#pwd-link").addClass("linkDis");
          });
          
          //Cancel editing
          $("#ed-cancel").click(function(){
            $(".edit-field").prop('readonly', true).addClass("bor-trans");
            $("#ed-cancel,#ed-update").addClass("dis-none");
            $("#editable").removeClass("dis-none");
            $("#pwd-link").removeClass("linkDis");
          });
          
          /* Profile buttons ends */
          
          /* Password buttons starts */
          $("#cancelBtn").click(function(){
        	  $(".changepwd").slideToggle(10);
        	  $("#editable").removeClass("linkDis");
          });
          
          //toggling change password
          $(".changepwd").slideUp();
          $("#pwd-link").click(function(){
        	 $(".changepwd .emptyField").val("");
             $(".changepwd").slideToggle(10);
             $("#cancelBtn,#updateBtn").show();
             $("#editable").addClass("linkDis");
          });
	      
	      $('#updateBtn').click(function(){
	    	  	alert("hi");
						var oldPassword = $('#oldPassword').val();
						var newPassword = $('#password').val();
						$.ajax({
							url : "/fdahpStudyDesigner/adminDashboard/changePassword.do",
							type : "POST",
							datatype : "json",
							data : {
								oldPassword : oldPassword,
								newPassword : newPassword,
								"${_csrf.parameterName}":"${_csrf.token}"
							},
							success : function getResponse(data, status) {
								var jsonObj = eval(data);
								var message = jsonObj.message;
								//$('#displayMessage').parent().hide();
								if('SUCCESS' == message){
									alert("pass");
									//$('#displayMessage').removeClass('aq-danger').addClass('aq-success');
									$("#sucMsg .msg").html('Password updated successfully.');
									$("#sucMsg").show();
									$("#errMsg").hide();
									$(".changepwd").slideToggle(10);
					               	//$("#updateBtn,#cancelBtn").hide();
									//$("#savePassword").removeAttr("disabled");
								} else {
									alert("fail");
									//$('#displayMessage').removeClass('aq-success').addClass('aq-danger');
									$("#errMsg .msg").html(message);
									$("#sucMsg").hide();
									$("#errMsg").show();
								}
								setTimeout(hideDisplayMessage, 4000);
								$(".passwordBox .aq-inp").val("");
							},
						});
				});
	      
	      var sucMsg = '${sucMsg}';
	    	var errMsg = '${errMsg}';
	    	if(sucMsg.length > 0){
				$("#sucMsg .msg").html(sucMsg);
		    	$("#sucMsg").show("fast");
		    	$("#errMsg").hide("fast");
		    	setTimeout(hideDisplayMessage, 4000);
			}
	    	if(errMsg.length > 0){
				$("#errMsg .msg").html(errMsg);
			   	$("#errMsg").show("fast");
			   	$("#sucMsg").hide("fast");
			   	setTimeout(hideDisplayMessage, 4000);
			}
			
			setTimeout(hideDisplayMessage, 4000);
			
			 $('#displayMessage').click(function(){
				$('#displayMessage').hide();
			});
	      
	  });
	  
	  /* Password buttons ends */
	  function hideDisplayMessage(){
		$('.msg').parent().hide();
		}
	  
	  function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
   </script>
