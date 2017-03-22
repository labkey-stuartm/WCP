<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<body>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              <span class="mr-xs"><a href="javascript:void(0)" class="backOrCancelBttn"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a> </span>
              <c:if test="${actionPage eq 'ADD_PAGE'}">
              	Add New User
              </c:if>
              <c:if test="${actionPage eq 'EDIT_PAGE'}">
              	Edit User
              </c:if>
              <c:if test="${actionPage eq 'VIEW_PAGE'}">
              	User Information
              </c:if>
              
            </div>
            <c:if test="${actionPage eq 'EDIT_PAGE' || actionPage eq 'VIEW_PAGE'}">
            <div class="dis-line pull-right">
                 <div class="form-group mb-none">
                     <span class="gray-95a2ab">Activate / Deactivate </span>
                     <span class="ml-xs">
                        <label class="switch bg-transparent mt-xs" <c:if test="${empty userBO.userPassword}">data-toggle="tooltip" data-placement="top" title="User not yet signed in"</c:if>>
                          <input type="checkbox" class="switch-input" value="${userBO.enabled}" id="change${userBO.userId}" 
                          <c:if test="${userBO.enabled}">checked</c:if> <c:if test="${empty userBO.userPassword || actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if> 
                          onclick="activateOrDeactivateUser(${userBO.userId});" >
                          <span class="switch-label bg-transparent" data-on="On" data-off="Off"></span>
                          <span class="switch-handle"></span>
                        </label>
                    </span>
                 </div>
             </div>
             </c:if>
             
         </div>         
    
</div>
 
<form:form action="/fdahpStudyDesigner/adminUsersEdit/addOrUpdateUserDetails.do" data-toggle="validator" id="userForm" role="form" method="post" autocomplete="off">   
<input type="hidden" name="userId" value="${userBO.userId}">
<input type="hidden" id="userStatus" name="enabled" value="${userBO.enabled}">
<input type="hidden" id="selectedStudies" name="selectedStudies">
<input type="hidden" id="permissionValues" name="permissionValues">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
    <div class="white-bg box-space">
        <div class="ed-user-layout row">
            <!-- Edit User Layout-->
            
            <div class="blue-md-f text-uppercase mb-md">Basic Information</div>
                <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">First Name<c:if test="${actionPage ne 'VIEW_PAGE'}"><small>(50 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                           <div class="form-group">
                                <input type="text" class="form-control" name="firstName" value="${fn:escapeXml(userBO.firstName)}" maxlength="50" required <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>/>
                            	<div class="help-block with-errors red-txt"></div>
                            </div>
                    </div>
                    <!-- form- input-->
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Last Name<c:if test="${actionPage ne 'VIEW_PAGE'}"><small>(50 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                           <div class="form-group">
                                <input type="text" class="form-control" name="lastName" value="${fn:escapeXml(userBO.lastName)}" maxlength="50" required <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>/>
                           		<div class="help-block with-errors red-txt"></div>
                           </div>
                    </div>
                </div>
            
            
                 <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Email Address<c:if test="${actionPage ne 'VIEW_PAGE'}"><small>(100 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                           <div class="form-group">
                                <input type="text" class="form-control validateUserEmail" name="userEmail" value="${userBO.userEmail}" oldVal="${userBO.userEmail}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" data-pattern-error="Email address is invalid" maxlength="100" required <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId || (empty userBO.userPassword && not empty userBO)}">readonly</c:if>/>
                            	<div class="help-block with-errors red-txt"></div>
                            </div>
                    </div>
                    <!-- form- input-->
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Phone Number<c:if test="${actionPage ne 'VIEW_PAGE'}"><small>(10 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                           <div class="form-group">
                                <input type="text" class="form-control phoneMask" name="phoneNumber" value="${userBO.phoneNumber}" data-minlength="12" maxlength="12" required <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>/>
                           		<div class="help-block with-errors red-txt"></div>
                           </div>
                    </div>
                </div>
            
                <div class="clearfix"></div>
                <!-- Assign Role Section -->
                <div class="blue-md-f text-uppercase mt-lg mb-md">Assign Role<span class="requiredStar"> *</span></div>
                <div class="col-md-12 p-none">
                    <!-- form- input-->
                    <div class="col-md-6 pl-none">
                           <div class="form-group">
                            <!-- <input type="text" class="form-control"/> -->
                            <select class="selectpicker <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">linkDis</c:if>" name="roleId" required>
                              <option value="" selected disabled>- Select Role -</option>
                              <c:forEach items="${roleBOList}" var="role">
                              	<option ${role.roleId eq userBO.roleId ? 'selected' : ''} value="${role.roleId}">${role.roleName}</option>
                              </c:forEach>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
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
                        <input type="checkbox" class="chk" id="inlineCheckbox1" value="option1" <c:if test="${fn:contains(permissions,7)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                        <label for="inlineCheckbox1"> Manage Users </label>
                    </span>
                    <span class="pull-right">
                        <span class="radio radio-info radio-inline p-45">
                            <input type="radio" class="musr" id="inlineRadio1" value="0" name="manageUsers" <c:if test="${!fn:contains(permissions,5)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                            <label for="inlineRadio1"></label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" class="musr" id="inlineRadio2" value="1" name="manageUsers" <c:if test="${fn:contains(permissions,5)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
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
                        <input type="checkbox" id="inlineCheckbox3" class="chk" value="option1" <c:if test="${fn:contains(permissions,4)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                        <label for="inlineCheckbox3"> Manage App-Wide Notifications </label>
                    </span>
                    <span class="pull-right">
                        <span class="radio radio-info radio-inline p-45">
                            <input type="radio" id="inlineRadio5" class="mnotf" value="0" name="manageNotifications" <c:if test="${!fn:contains(permissions,6)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                            <label for="inlineRadio5"></label>
                        </span>
                        <span class="radio radio-inline">
                            <input type="radio" id="inlineRadio6" class="mnotf" value="1" name="manageNotifications" <c:if test="${fn:contains(permissions,6)}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                            <label for="inlineRadio6"></label>
                        </span>
                    </span>
                </div>
            
              <!-- Gray Widget-->
                <div class="edit-user-list-widget">
                     <span class="checkbox checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox4" name="manageStudies" <c:if test="${fn:contains(permissions,2)}">value="1" checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                        <label for="inlineCheckbox4"> Manage Studies </label>
                    </span> 
                    <div class="mt-lg pl-lg">
                        <div class="pb-md bor-dashed">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="inlineCheckbox5" class="changeView" name="addingNewStudy" value="option1" <c:if test="${fn:contains(permissions,8)}">value="1" checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
                                <label for="inlineCheckbox5"> Adding a New Study </label>
                            </span> 
                        </div>
                        <c:if test="${actionPage ne 'VIEW_PAGE'}">
                        <div class="mt-md study-list mb-md">
                            <select class="selectpicker col-md-6 p-none changeView <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">linkDis</c:if>" title="- Select and Add Studies -" multiple id="multiple">
                              <c:forEach items="${studyBOList}" var="study">
                              	<option value="${study.id}" id="selectStudies${study.id}">${study.name}</option>
                              </c:forEach>
                            </select>
                            <span class="study-addbtn changeView">+</span>
                        </div>  
                        </c:if> 
                        <div>
                         <span class="mr-lg text-weight-semibold text-uppercase">Existing Studies</span> 
                         <c:if test="${actionPage ne 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">
                         	<span class="ablue removeAll changeView">x Remove  all</span>
                         </c:if>
                        </div>
                        <!-- Selected Study items -->
                        <div class="study-selected mt-md">
                        	<c:forEach items="${studyBOs}" var="study">
								<div class="study-selected-item selStd" id="std${study.id}">
                				<input type="hidden" class="stdCls" id="${study.id}" name="" value="${study.id}" stdTxt="${study.name}" <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
						        <span class="mr-md"><img src="/fdahpStudyDesigner/images/icons/close.png" onclick="del(${study.id});"/></span>
						        <span>${study.name}</span>
						        <span class="pull-right">
						        <span class="radio radio-info radio-inline p-45 mr-xs">
						        <input type="radio" class="v${study.id} changeView" id="v1${study.id}" name="radio${study.id}" value="0" <c:if test="${not study.viewPermission}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
						        <label for="v1${study.id}"></label></span>
						        <span class="radio radio-inline">
						        <input type="radio" class="v${study.id} changeView" id="v2${study.id}" name="radio${study.id}" value="1" <c:if test="${study.viewPermission}">checked</c:if> <c:if test="${actionPage eq 'VIEW_PAGE' || sessionObject.userId eq userBO.userId}">disabled</c:if>>
						        <label for="v2${study.id}"></label>
						        </span>
						        </span>
						        </div>
                        	</c:forEach>
                        </div>
                    </div>
                </div>
           </div>        
      </div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none">
   <div class="white-bg box-space t-bor text-right">
       <div class="dis-line text-right ml-md">
         <div class="dis-line form-group mb-none mr-sm">
             <button type="button" class="btn btn-default gray-btn backOrCancelBttn" <c:if test="${sessionObject.userId eq userBO.userId}">disabled</c:if>>Cancel</button>
         </div>
         <c:if test="${actionPage eq 'ADD_PAGE'}">
	         <div class="dis-line form-group mb-none">
	             <button type="button" class="btn btn-primary blue-btn addUpdate" <c:if test="${sessionObject.userId eq userBO.userId}">disabled</c:if>>Add</button>
	         </div>
	     </c:if>
         <c:if test="${actionPage eq 'EDIT_PAGE'}">
	         <div class="dis-line form-group mb-none">
	             <button type="button" class="btn btn-primary blue-btn addUpdate" <c:if test="${sessionObject.userId eq userBO.userId}">disabled</c:if>>Update</button>
	         </div>
	     </c:if>
           
      </div>       
    </div>
</div>
</form:form>

 <%-- <c:if test="${actionPage ne 'VIEW_PAGE'}">
              	<form:form action="/fdahpStudyDesigner/adminUsersEdit/getUserList.do" id="backOrCancelBtnForm" name="backOrCancelBtnForm" method="post">
				</form:form>
 </c:if> --%>
 <%-- <c:if test="${actionPage eq 'VIEW_PAGE'}"> --%>
              	<form:form action="/fdahpStudyDesigner/adminUsersView/getUserList.do" id="backOrCancelBtnForm" name="backOrCancelBtnForm" method="post">
				</form:form>
 <%-- </c:if> --%>
<script>


    $(document).ready(function(){
    	
    	$('#users').addClass('active');
    	
    	$('[data-toggle="tooltip"]').tooltip();	
    	
    	$("form").submit(function() {
    		$(this).submit(function() {
       	 		return false;
    		});
    		 	return true;
	    });
    	
    	$(window).on('load',function(){
    		   
    	   	$('.selStd').each(function(){
        		var stdTxt = $(this).find('.stdCls').attr('stdTxt');
        		 $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li a span:first-child").each(function(){
       	  		  var ltxt = $(this).text();
       	  		  var a = $.trim(ltxt);
       	  		  var b = $.trim(stdTxt);	  		  
       	          if(a == b){
       	        	 $(this).parent().parent().hide();
       	          }
       	      });
        	});
    	   	
       });
    	
    	//cancel or back click
    	$('.backOrCancelBttn').on('click',function(){
    		$('#backOrCancelBtnForm').submit();
    	});
    	
    	
    	if($('#inlineCheckbox1').prop("checked") == false){
    		/* $('.musr').find('input[type=radio]').prop('checked',false);
			$('.musr').find('input[type=radio]').prop('disabled',true); */
    		$('.musr').prop('checked',false);
    		$('.musr').prop('disabled',true);
    	}
    	
    	if($('#inlineCheckbox3').prop("checked") == false){
    		/* $('.mnotf').find('input[type=radio]').prop('checked',false);
			$('.mnotf').find('input[type=radio]').prop('disabled',true); */
    		$('.mnotf').prop('checked',false);
    		$('.mnotf').prop('disabled',true);
    	}
    	
    	if($('#inlineCheckbox4').prop("checked") == false){
    		$('#inlineCheckbox5').prop('checked',false);
    		$('#inlineCheckbox5').prop('disabled',true);
    	}
    	
    	$('#inlineCheckbox1').on('click',function(){
    		if($(this).prop("checked") == true){
    			/* $(this).parents('.musr').find('input[type=radio]').prop('disabled',false); */
    			$('.musr').prop('disabled',false);
    			$('#inlineRadio1').prop('checked',true);
    		}
    		else if($(this).prop("checked") == false){
    			/* $(this).parents('.musr').find('input[type=radio]').prop('checked',false);
    			$(this).parents('.musr').find('input[type=radio]').prop('disabled',true); */
    			$('.musr').prop('checked',false);
        		$('.musr').prop('disabled',true);
    		}
    	});
    	
    	$('#inlineCheckbox3').on('click',function(){
    		if($(this).prop("checked") == true){
    			/* $(this).parents('.mnotf').find('input[type=radio]').prop('disabled',false); */
    			$('.mnotf').prop('disabled',false);
    			$('#inlineRadio5').prop('checked',true);
    		}
    		else if($(this).prop("checked") == false){
    			/* $(this).parents('.mnotf').find('input[type=radio]').prop('checked',false);
    			$(this).parents('.mnotf').find('input[type=radio]').prop('disabled',true); */
    			$('.mnotf').prop('checked',false);
        		$('.mnotf').prop('disabled',true);
    		}
    	});
    	
    	/* $('#inlineCheckbox1').on('click',function(){
    		if($(this).prop("checked") == true){
    			$('.usersRadioBtn').prop('disabled',false);
    		}
    		else if($(this).prop("checked") == false){
    			$(".usersRadioBtn").prop("checked",false);
    			$('.usersRadioBtn').prop('disabled',true);
    		}
    	}); */
    	
    	$('#inlineCheckbox4').on('click',function(){
    		if($(this).prop("checked") == true){
                $(this).val(1);
                /* $('#inlineCheckbox5').prop('disabled',false); */
                $('.changeView').prop('disabled',false);
            }
            else if($(this).prop("checked") == false){
                $(this).val('');
                $('#inlineCheckbox5').val('');
                $('#inlineCheckbox5').prop('checked',false);
                /* $('#inlineCheckbox5').prop('disabled',true); */
                $('.changeView').prop('disabled',true);
                /* $('#addStudy').prop('disabled',true); */
            }
    	});
    	
    	$('#inlineCheckbox5').on('click',function(){
    		if($(this).prop("checked") == true){
                $(this).val(1);
            }
            else if($(this).prop("checked") == false){
                $(this).val('');
            }
    	});
     // Adding selected study items    
  $(".study-addbtn").click(function(){
	  
		  $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li.selected").hide();
	      
	      $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li").each(function(){
	         if($(this).text()=="- All items are already selected -"){
	           $(this).hide();  
	         }
	       });
	  
           /*  var a = $('.study-list .bootstrap-select button span.filter-option').text();
            if(a != "- Select and Add Studies -"){
            var b = a.split(',');         
            
            for(var i = 0; i < b.length; i++)
            {
            	alert(b[i]);
                var existingStudyDiv = "<div class='study-selected-item'>"
                						+"<input type='hidden' id='"+b[i]+"' name='"+b[i]+"' value='0'>"
						                +"<span class='mr-md'><img src='/fdahpStudyDesigner/images/icons/close.png'/></span>"
						                +"<span>"+b[i]+"</span>"
						                +"<span class='pull-right'>"
						                +"<span class='radio radio-info radio-inline p-45 mr-xs'>"
						                +"<input type='radio' id='inlineRadio7' value='option1' name='radioInline7'>"
						                +"<label for='inlineRadio7'></label></span>"
						                +"<span class='radio radio-inline'>"
						                +"<input type='radio' id='inlineRadio8' value='option1' name='radioInline7'>"
						                +"<label for='inlineRadio8'></label>"
						                +"</span>"
						                +"</span>"
						                +"</div>";             
            	$('.study-selected').append(existingStudyDiv);          
            }

          } */
          
		$('#multiple :selected').each(function(i, sel){ 
								    var selVal = $(sel).val(); 
								    var selTxt = $(sel).text(); 
								   /*  $('#selectStudies'+selVal).prop('disabled',true); */
								    var existingStudyDiv = "<div class='study-selected-item selStd' id='std"+selVal+"'>"
									+"<input type='hidden' class='stdCls' id='"+selVal+"' name='' value='"+selVal+"'>"
						            +"<span class='mr-md cls cur-pointer'><img src='/fdahpStudyDesigner/images/icons/close.png' onclick='del("+selVal+");'/></span>"
						            +"<span>"+selTxt+"</span>"
						            +"<span class='pull-right'>"
						            +"<span class='radio radio-info radio-inline p-45 mr-xs'>"
						            +" <input type='radio' class='v"+selVal+" changeView' id='v1"+selVal+"' name='radio"+selVal+"' value='0' checked='checked'>"
						            +"<label for='v1"+selVal+"'></label></span>"
						            +"<span class='radio radio-inline'>"
						            +"<input type='radio' class='v"+selVal+" changeView' id='v2"+selVal+"' name='radio"+selVal+"' value='1'>"
						            +" <label for='v2"+selVal+"'></label>"
						            +"</span>"
						            +"</span>"
						            +"</div>";
						            
						            $('.study-selected').append(existingStudyDiv); 
		});
          
		 $(".selectpicker").selectpicker('deselectAll');
         var tot_items = $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li").length;
         var count = $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li[style]").length;    
         if(count == tot_items){
             $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu").append('<li class="text-center">- All items are already selected -</li>');
         }
          
        });
     
//Removing selected study items
	$(".removeAll").click(function(){
		$(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li[style],.study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li").show();
      $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li").each(function(){
          if($(this).text()=="- All items are already selected -"){
            $(this).hide();  
          }
      });
      $(".study-selected-item").remove();
  });

   
  $('.addUpdate').on('click',function(){
  	var selectedStudies = "";
  	var permissionValues = "";
  	if(isFromValid($(this).parents('form'))){
	  	$('.selStd').each(function(){
	  		var studyId = $(this).find('.stdCls').val();
	  		/* alert("studyId"+studyId); */
	  		var permissionValue = $('#std'+studyId).find('input[type=radio]:checked').val();
	  		/* alert("permissionValue"+permissionValue); */
	  		if(selectedStudies == ""){
	  			selectedStudies = studyId;
	  		}else{
	  			selectedStudies += ","+studyId;
	  		}
	  		if(permissionValues == ""){
	  			permissionValues = permissionValue;
	  		}else{
	  			permissionValues += ","+permissionValue;
	  		}
	  	});
	  	/* alert(selectedStudies+" "+permissionValues); */
	  	$('#selectedStudies').val(selectedStudies);
	  	$('#permissionValues').val(permissionValues);
	  	/* resetValidation('#userForm'); */
  		$(this).parents('form').submit();	
  	}
  });
        
   });
    
    function del(id){
  	 	var atxt = $('#std'+id).children().text();
  	 	
	  	  $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li a span:first-child").each(function(){
	  		  var ltxt = $(this).text();
	  		  var a = $.trim(ltxt);
	  		  var b = $.trim(atxt);	  		  
	          if(a == b){
	        	 $(this).parent().parent().show();
	          }
	      });
	  	  
	  	 $(".study-list .bootstrap-select .dropdown-menu ul.dropdown-menu li").each(function(){
            if($(this).text()=="- All items are already selected -"){
              $(this).hide();  
            }
        });
	  	  
  	 	 $('#std'+id).remove();
  	 	
    }
    
    function activateOrDeactivateUser(userId){
    	var status = $('#change'+userId).val();
    	if(status == 'true'){
    		$('#change'+userId).val(false);
    		$('#userStatus').val(false);
    	} else {
    		$('#change'+userId).val(true);
    		$('#userStatus').val(true);
    	}
    }
</script>
</body>