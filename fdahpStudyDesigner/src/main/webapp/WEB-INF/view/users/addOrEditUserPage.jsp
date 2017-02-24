<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<body>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              <span class="mr-xs"><a href="#"><img src="images/icons/back-b.png"/></a> 
              <c:if test="${actionPage eq 'ADD_PAGE'}">
              	Add User
              </c:if>
              <c:if test="${actionPage eq 'EDIT_PAGE'}">
              	Edit User
              </c:if>
              <c:if test="${actionPage eq 'VIEW_PAGE'}">
              	User Information
              </c:if>
              </span>
            </div>
         </div>         
    </div>
</div>
 
<form:form action="/fdahpStudyDesigner/adminUsersEdit/addOrUpdateUserDetails.do" data-toggle="validator" id="userForm" role="form" method="post" autocomplete="off">   
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
    <div class="md-container white-bg box-space">
        <div class="ed-user-layout row">
            <!-- Edit User Layout-->
            
            <div class="blue-md-f text-uppercase mb-md">Basic Information</div>
                <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">First Name</div>
                           <div class="form-group">
                                <input type="text" class="form-control" name="firstName" value="${userBO.firstName}" maxlength="50" required="required"/>
                            </div>
                    </div>
                    <!-- form- input-->
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Last Name</div>
                           <div class="form-group">
                                <input type="text" class="form-control" name="lastName" value="${userBO.lastName}" maxlength="50" required="required"/>
                           </div>
                    </div>
                </div>
            
            
                 <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">E-mail Address</div>
                           <div class="form-group">
                                <input type="email" class="form-control" name="userEmail" value="${userBO.userEmail}" maxlength="100" required="required"/>
                            </div>
                    </div>
                    <!-- form- input-->
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Phone Number</div>
                           <div class="form-group">
                                <input type="text" class="form-control phoneMask" name="phoneNumber" value="${userBO.phoneNumber}" data-minlength="12" maxlength="12" required="required"/>
                           </div>
                    </div>
                </div>
            
                <div class="clearfix"></div>
                <!-- Assign Role Section -->
                <div class="blue-md-f text-uppercase mt-lg mb-md">Assign Role</div>
                <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                           <div class="form-group">
                            <!-- <input type="text" class="form-control"/> -->
                            <select class="selectpicker" name="roleId">
                              <option>- Select Role -</option>
                              <c:forEach items="${roleBOList}" var="role">
                              <option ${role.roleId eq userBO.roleId ? 'selected' : ''} value="${role.roleId}">${role.roleName}</option>
                              </c:forEach>
                            </select>
                           </div>
                    </div>                    
                </div>
            
                <div class="clearfix"></div>
                <!-- Assign Permissions -->
                <div class="blue-md-f text-uppercase mt-lg">Assign Permissions</div>
                <div class="pull-right mb-xs">
                    <span class="gray-xs-f">View Only</span>
                    <span class="gray-xs-f ml-lg">View & Edit</span>
                </div>
                <div class="clearfix"></div>
            
                <!-- Gray Widget-->
                <div class="edit-user-list-widget">
                     <span class="checkbox checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox1" value="option1" <c:if test="${fn:contains(permissions,7)}">checked</c:if>>
                        <label for="inlineCheckbox1"> Manage Users </label>
                    </span>
                    <span class="pull-right">
                        <span class="radio radio-info radio-inline p-45">
                            <input type="radio" class="usersRadioBtn" id="inlineRadio1" value="0" name="manageUsers" <c:if test="${!fn:contains(permissions,5)}">checked</c:if>>
                            <label for="inlineRadio1"></label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" class="usersRadioBtn" id="inlineRadio2" value="1" name="manageUsers" <c:if test="${fn:contains(permissions,5)}">checked</c:if>>
                            <label for="inlineRadio2"></label>
                        </span>
                    </span>
                </div>
            
               <!-- Gray Widget-->
               <!--  <div class="edit-user-list-widget">
                     <span class="checkbox checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox2" value="option1">
                        <label for="inlineCheckbox2"> Manage Repository </label>
                    </span>
                    <span class="pull-right">
                        <span class="radio radio-info radio-inline p-45">
                            <input type="radio" id="inlineRadio3" value="option1" name="radioInline2">
                            <label for="inlineRadio3"></label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" id="inlineRadio4" value="option1" name="radioInline2">
                            <label for="inlineRadio4"></label>
                        </span>
                    </span>
                </div> -->
            
              <!-- Gray Widget-->
                <div class="edit-user-list-widget">
                     <span class="checkbox checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox3" value="option1" <c:if test="${fn:contains(permissions,4)}">checked</c:if>>
                        <label for="inlineCheckbox3"> Manage App-Wide Notifications </label>
                    </span>
                    <span class="pull-right">
                        <span class="radio radio-info radio-inline p-45">
                            <input type="radio" id="inlineRadio5" value="0" name="manageNotifications" <c:if test="${!fn:contains(permissions,6)}">checked</c:if>>
                            <label for="inlineRadio5"></label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" id="inlineRadio6" value="1" name="manageNotifications" <c:if test="${fn:contains(permissions,6)}">checked</c:if>>
                            <label for="inlineRadio6"></label>
                        </span>
                    </span>
                </div>
            
              <!-- Gray Widget-->
                <div class="edit-user-list-widget">
                     <span class="checkbox checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox4" name="manageStudies" <c:if test="${fn:contains(permissions,2)}">value="1" checked</c:if>>
                        <label for="inlineCheckbox4"> Manage Studies </label>
                    </span> 
                    <div class="mt-lg pl-lg">
                        <div class="pb-md bor-dashed">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox5" name="addingNewStudy" value="option1" <c:if test="${fn:contains(permissions,8)}">value="1" checked</c:if>>
                                <label for="inlineCheckbox5"> Adding a New Study </label>
                            </span> 
                        </div>
                        <%-- <div class="mt-md study-list mb-md">
                            <select class="selectpicker" title="- Select and Add Studies -" multiple>
                              <c:forEach items="${studyBOList}" var="study">
                              	<option>${study.name}</option>
                              </c:forEach>
                            </select>
                            <span class="study-addbtn">+</span>
                        </div>  --%>  
                        <!-- <div>
                         <span class="mr-lg text-weight-semibold text-uppercase">Existing Studies</span> <span class="ablue removeAll">x Remove  all</span>
                        </div> -->
                        <!-- Selected Study items -->
                        <%-- <div class="study-selected mt-md">
                        	<c:forEach items="${studyBOs}" var="study">
								<span>${study.name}</span><br>                        	
                        	</c:forEach>
                        </div> --%>
                    </div>
                </div>
           </div>        
      </div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
   <div class="md-container white-bg box-space t-bor text-right">
       <div class="dis-line text-right ml-md">
         <div class="dis-line form-group mb-none mr-sm">
             <button type="button" class="btn btn-default gray-btn">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none">
             <button type="submit" class="btn btn-primary blue-btn">Add</button>
         </div>
           
      </div>       
    </div>
</div>
</form:form>

<script>
    $(document).ready(function(){
    	
    /* 	<c:if test="${actionPage eq 'EDIT_PAGE'}">
    		<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_VIEW')}">
    			$('#inlineCheckbox1').prop("checked",true);
    		<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">
    			$('#inlineRadio2').prop("checked",true);
    			$('#inlineRadio2').val(1);
    		</c:if>
    		<c:if test="${!fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_EDIT')}">
				$('#inlineRadio1').prop("checked",true);
				$('#inlineRadio1').val(0);
			</c:if>
    		</c:if>
    		
    		<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW')}">
				$('#inlineCheckbox3').prop("checked",true);
			<c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
				$('#inlineRadio6').prop("checked",true);
				$('#inlineRadio6').val(1);
			</c:if>
			<c:if test="${!fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT')}">
				$('#inlineRadio5').prop("checked",true);
				$('#inlineRadio5').val(1);
			</c:if>
			</c:if>
      	</c:if> */
    	
    	$('#inlineCheckbox1').on('click',function(){
    		if($(this).prop("checked") == true){
    			$('.usersRadioBtn').prop('disabled',false);
    		}
    		else if($(this).prop("checked") == false){
    			$(".usersRadioBtn").prop("checked",false);
    			$('.usersRadioBtn').prop('disabled',true);
    		}
    	});
    	
    	$('#inlineCheckbox4').on('click',function(){
    		if($(this).prop("checked") == true){
                alert("Checkbox is checked.");
                $(this).val(1);
                $('#inlineCheckbox5').prop('disabled',false);
            }
            else if($(this).prop("checked") == false){
                alert("Checkbox is unchecked.");
                $(this).val('');
                $('#inlineCheckbox5').val('');
                $('#inlineCheckbox5').prop('checked',false);
                $('#inlineCheckbox5').prop('disabled',true);
            }
    	});
    	
    	$('#inlineCheckbox5').on('click',function(){
    		if($(this).prop("checked") == true){
                alert("Checkbox is checked.");
                $(this).val(1);
            }
            else if($(this).prop("checked") == false){
                alert("Checkbox is unchecked.");
                $(this).val('');
            }
    	});
     // Adding selected study items    
    /*  $(".study-addbtn").click(function(){
            var a = $('.study-list .bootstrap-select button span.filter-option').text();
            if(a != "- Select and Add Studies -"){
            var b = a.split(',');         
            
            for(var i = 0; i < b.length; i++)
            {
                  $('.study-selected').append("<div class='study-selected-item'><span class='mr-md'><img src='images/icons/close.png'/></span><span>"+b[i]+"</span><span class='pull-right'><span class='radio radio-info radio-inline p-45 mr-xs'><input type='radio' id='inlineRadio"+i+"' value='option1' name='radioInline3'><label for='inlineRadio7'></label></span><span class='radio radio-inline'><input type='radio' id='inlineRadio8' value='option1' name='radioInline3'><label for='inlineRadio8'></label></span></span></div>");          
            }

          }
        }); */
        
        //Removing selected study items
       /*  $(".removeAll").click(function(){
          $(".study-selected").children().remove();
          $('.study-list .bootstrap-select button').attr("title","- Select and Add Studies -")
          $('.study-list .bootstrap-select button span.filter-option').text("- Select and Add Studies -");
          $(".dropdown-menu li[data-original-index]").removeClass("selected");
          $(".dropdown-menu li[data-original-index] a").attr("aria-selected","false");
        });
        
        $('#inlineCheckbox1').on('click',function(){
        	alert("YES");
        }); */
    });
</script>
</body>