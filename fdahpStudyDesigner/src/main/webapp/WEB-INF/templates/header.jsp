<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%-- <common header starts> --%>
<!--navbar-->
<nav class="navbar navbar-fixed-top aq-navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
       <c:if test="${sessionObject.userType eq 'ACUITY'}">
      	<a class="navbar-brand" href="javascript:void(0)" id="acuityDefaultDash"><img src="../images/logo.png"></a>
      </c:if> 
      <c:if test="${sessionObject.userType eq 'HI'}">
			<a class="navbar-brand" href="javascript:void(0)" id="hiDefaultDash"><c:if test="${not empty sessionObject.hiLogo}"><img src="<spring:message code="acuityLink.logoDisplaydPath"/>${sessionObject.hiLogo}" class="marR10" style="display:inline-block;" width="140" height="30"></c:if><c:if test="${empty sessionObject.hiLogo}"><img src="../images/logo.png" class="marR10" style="display:inline-block;"></c:if><span style="color:#fff !important;font-size:12px;">Powered by :</span><img src="../images/small_logo.png" class="marT2" style="display:inline-block;" width="98"></a>
	  	<!-- <a class="navbar-brand" href="#"><img src="../images/logo.png" class="marR10" style="display:inline-block;" width="140" height="30"><span style="color:#fff !important;font-size:12px;">Powered by :</span><img src="../images/small_logo.png" class="marL5" style="display:inline-block;" width="75" height="16"></a> -->
	  </c:if>
      <c:if test="${sessionObject.userType eq 'ASP'}">
			<a class="navbar-brand" href="javascript:void(0)" id="aspDefaultDash"><c:if test="${not empty sessionObject.aspLogo}"><img src="<spring:message code="acuityLink.logoDisplaydPath" />${sessionObject.aspLogo}" class="marR10" style="display:inline-block;" width="140" height="30"></c:if><c:if test="${empty sessionObject.aspLogo}"><img src="../images/logo.png" class="marR10" style="display:inline-block;"></c:if><span style="color:#fff !important;font-size:12px;">Powered by :</span><img src="../images/small_logo.png" class="marT2" style="display:inline-block;" width="98"></a>
			<!-- <a class="navbar-brand" href="#"><img src="../images/logo.png" class="marR10" style="display:inline-block;" width="140" height="30"><span style="color:#fff !important;font-size:12px;">Powered by :</span><img src="../images/small_logo.png" class="marL5" style="display:inline-block;" width="75" height="16"></a> -->
      </c:if>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
	    <ul class="nav navbar-nav leftNav">
	    <c:if test="${sessionObject.userType eq 'ACUITY'}">
		    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ACUITY_DASHBOARD')}">
		        <li id="acuityAdminDashBoard"><a class=""  href="javascript:void(0)" id="acuityDash">Dashboard</a></li>
		    </c:if>
		    <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ACUITY_MANAGE_HI')}">
		        <li class="dropdown" id="managehi">
		          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">Manage HI <span class="caret"></span></a>
		          <ul class="dropdown-menu">
		            <li><a href="javascript:void(0)" id="acuityHIList">Health Institutions</a></li>
		            <li><a href="javascript:void(0)" id="acuityHIReg">Manage Registrations</a></li>
		          </ul>
		        </li>
	        </c:if>
	        <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ACUITY_MANAGE_ASP')}">
		        <li class="dropdown" id="manageasp">
		        	<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">Manage ASP <span class="caret"></span></a>
		        	<ul class="dropdown-menu">
			            <li id=""><a href="javascript:void(0)" id="acuityASPList">Ambulance Service Providers</a></li>
			            <li><a href="javascript:void(0)" id="acuityASPReg">Manage Registrations</a></li>
		          	</ul>
		        </li>
	        </c:if>
	        <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ACUITY_MANAGE_USER')}">
	        	<li id="manageUser"><a  href="javascript:void(0)" id="acuityUser">Administrators</a></li>
	        </c:if>
	        <c:if test="${fn:contains(sessionObject.userPermissions, 'ROLE_ADMIN_ACUITY_MANAGE_VARS')}">
	        	<li id="manageVariable"><a href="javascript:void(0)" id="acuityVar">Manage Variables</a></li>
	        </c:if>
	        </c:if>
	      </ul>
      <c:if test="${sessionObject.userType eq 'HI' && fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_HI')}">
	      <ul class="nav navbar-nav leftNav">
	        <li id="hIAdminDashBoard"><a href="javascript:void(0)" id="hiDash">Dashboard</a></li>
	        <li class="dropdown" id="manageASPAndPriority">
	          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">ASPs & Configuration<span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="javascript:void(0)" id="hiASPByHI">Ambulance Service Providers</a></li>
	            <li><a href="javascript:void(0)" id="hiorderPriority">Selection Logic</a></li>
	          </ul>
	        </li>
	        <li class="dropdown" id="serviceRequestsLiId"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">Requests<span class="caret"></span></a>
	            <ul class="dropdown-menu">
	            <li><a href="javascript:void(0)" id="activeReq">Active Requests</a></li>
	             <li><a href="javascript:void(0)" id="upcomingReq">Upcoming Requests</a></li>
	            <li><a href="javascript:void(0)" id="pendingReq">Pending Requests</a></li>
	            <li><a href="javascript:void(0)" id="pastReq">Past Requests</a></li>
	          </ul>
	          </li>
	        <c:if test="${sessionObject.superAdmin}">
	        	<li class="manageHIASPUser"><a  href="javascript:void(0)" id="hiSubAdmins">Administrators</a></li>
	        </c:if>
	        <li class="btnLi" id="createRequestLiId">
	        	<form name="getCreateRequestForm" id="getCreateRequestForm"></form>
	        	<button type="button" class="aq-btn highlightLabel notHover1" title="Create a New Service Request" id="createSRId">Create a Service Request</button>
	        </li>
	      </ul>
	  </c:if>
	  <c:if test="${sessionObject.userType eq 'ASP' && fn:contains(sessionObject.userPermissions,'ROLE_ADMIN_ASP')}">
	  	<ul class="nav navbar-nav leftNav">
	        <li class="" id="aSPAdminDashBoard"><a href="javascript:void(0)" id="aspDashboard">Dashboard</a></li>
	        <li class="" id="manageAmbulance"><a href="javascript:void(0)" id="aspManageVeh">Manage Vehicles</a></li>
	        <li class="" id="manageCrewMemebers"><a href="javascript:void(0)" id="aspManageCrew">Manage Crew Members</a></li>
	        <li class="dropdown" id="manageRequests"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">Manage Requests<span class="caret"></span></a>
	            <ul class="dropdown-menu">
	            <li><a href="javascript:void(0)" id="aspActiveReq">Active Requests</a></li>
	             <li><a href="javascript:void(0)" id="aspUpcomingReq">Upcoming Requests</a></li>
	            <li><a href="javascript:void(0)" id="aspPendingReq">Pending Requests</a></li>
	            <li><a href="javascript:void(0)" id="aspPastReq">Past Requests</a></li>
	          </ul>
	          </li>
	          <c:if test="${sessionObject.superAdmin}">
	          	<li class="manageHIASPUser"><a  href="javascript:void(0)" id="aspUser">Manage Users</a></li>
	          </c:if>
	      </ul>
	  </c:if>
      <ul class="nav navbar-nav navbar-right">
       <li class="dropdown notificationLi notBox notHover" id="dropdownDivId"><a href="#" data-toggle="dropdown" data-target="#"><img src="../images/bell.png" class="marT5"><span class="notiCount" id="notiCount"></span></a>
          <ul class="dropdown-menu notifications" role="menu" aria-labelledby="dLabel" id="notificationsUlId">
               <div class="notifications-wrapper" id="displayedNotiId">
                <!-- <div class="notification-item inActiveProp">
                    <label>Registration request recieved</label>
                  <p class="item-info mar0">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                </div> -->
                <div class="positionRelative notiLoad loader123" id="spinDiv">
	               		<div class="sm-loader marauto"></div>
	            </div>
	           </div>
               <div class="notification-footer"><a class="btn seeAllLink" href="javascript:void(0)" id="seeAllNot">See all</a></div>
             </ul>
          </li>
        <li class="dropdown marL20 userLi">
          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)"><img src="../images/avatar.png">&nbsp;&nbsp;Admin<span class="caret"></span></a>
          <ul class="dropdown-menu padB0 profileBox">
            <li class="userDet">
            	<c:if test="${not sessionObject.isAdminstrating}">
	                <strong>${sessionObject.firstName} ${sessionObject.lastName}</strong><br>
	                <span>${sessionObject.email}</span>
                </c:if>
                <c:if test="${sessionObject.isAdminstrating}">
	                <strong>${sessionObject.acuityAdminName}</strong><br>
	                <span>${sessionObject.acuityAdminEmail}</span>
                </c:if>
            </li>
            <li class="linkProf borT"><a href="javascript:void(0)" class="borR" id="profile">Profile</a><c:if test="${not sessionObject.isAdminstrating}"><a href="javascript:formSubmit();">Logout</a></c:if><c:if test="${sessionObject.isAdminstrating}"><a href="/acuityLink/adminAcuityDashboard/admistrationRedirect.do?userId=${sessionObject.adminstratorId}&administrate=0&userType=${sessionObject.userType}">Close</a></c:if></li>
          </ul>
          </li>
      </ul>
    </div>
  </div>
</nav>
<!--/.Navbar-->
<%-- <common header ends> --%>

<c:url value="/j_spring_security_logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<form:form action="/acuityLink/adminAcuityDashboard/viewDashBoard.do" id="acuityDashForm" name="acuityDashForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityManageHi/viewHealthInstitute.do" id="acuityHIListForm" name="acuityHIListForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityManageHi/getHealthInstituteRequests.do" id="acuityHIRegForm" name="acuityHIRegForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityManageASP/getServiceProviders.do" id="acuityASPListForm" name="acuityASPListForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityManageASP/getServiceProvidersRequests.do" id="acuityASPRegForm" name="acuityASPRegForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuitManageUser/adminList.do" id="acuityUserForm" name="acuityUserForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityManageVariable/manageVariableInfo.do" id="acuityVarForm" name="acuityVarForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/viewHiAdminDashBoard.do" id="hiDashForm" name="hiDashForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/getASPsByHI.do" id="hiASPByHIForm" name="hiASPByHIForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/orderPriority.do" id="hiorderPriorityForm" name="hiorderPriorityForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/ActiveHIRequest.do" id="activeReqForm" name="activeReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/UpcomingHIRequest.do" id="upcomingReqForm" name="upcomingReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/pendingHIRequest.do" id="pendingReqForm" name="pendingReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/pastHIRequest.do" id="pastReqForm" name="pastReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/subAdminHIList.do" id="hiSubAdminsForm" name="hiSubAdminsForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/viewASPAdminDashBoard.do" id="aspDashboardForm" name="aspDashboardForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/getAmbulancesList.do" id="aspManageVehForm" name="aspManageVehForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/getCrewMemebersList.do" id="aspManageCrewForm" name="aspManageCrewForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/activeASPRequestList.do" id="aspActiveReqForm" name="aspActiveReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/upcomingASPRequest.do" id="aspUpcomingReqForm" name="aspUpcomingReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/pendingASPRequest.do" id="aspPendingReqForm" name="aspPendingReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/pastASPRequest.do" id="aspPastReqForm" name="aspPastReqForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/subAdminASPList.do" id="aspUserForm" name="aspUserForm" method="post">
</form:form>

<form:form action="/acuityLink/notification/getNotificaionViewPage.do?action=notification" id="seeAllNotForm" name="seeAllNotForm" method="post">
</form:form>

<form:form action="/acuityLink/profile/viewProfile.do" id="profileForm" name="profileForm" method="post">
</form:form>

<form:form action="/acuityLink/adminAcuityDashboard/viewDashBoard.do" id="acuityDefaultDashForm" name="acuityDefaultDashForm" method="post">
</form:form>

<form:form action="/acuityLink/adminHI/viewHiAdminDashBoard.do" id="hiDefaultDashForm" name="hiDefaultDashForm" method="post">
</form:form>

<form:form action="/acuityLink/adminASP/viewASPAdminDashBoard.do" id="aspDefaultDashForm" name="aspDefaultDashForm" method="post">
</form:form>
<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>
<script type="text/javascript">
$(document).ready(function() {
	/*------------------------------------------added by MOHAN T starts------------------------------------------*/
	$("#createSRId").on('click', function(){
		//Check any ASP's are associated or not with the Current Hi admin
		var hiId = "${sessionObject.aspHiId}";
		if(hiId !== undefined && hiId != null && hiId != ""){
			$.ajax({
					url: "/acuityLink/adminHI/checkAspAssociatedOrNot.do",
					type: "POST",
					datatype: "json",
					data:{
						hiId : hiId,
						"${_csrf.parameterName}":"${_csrf.token}"
					},
					global: false,
					success: function getAspAssociatedStatus(data){
						var jsonobj = eval(data);
						var status = jsonobj.status;
						if(status == "1"){
							document.getCreateRequestForm.action = "/acuityLink/adminHI/serviceRequestDetails.do";
					    	document.getCreateRequestForm.submit();
						}else{
							bootbox.alert({
			    				message: "<div class='txtcenter'>Please add an ambulance service providers to your network, to create a service request</div>",
			    			});
						}
					}
			});
		}else{
			alert("Hi Id is empty.");
		}
	});
	/*------------------------------------------added by MOHAN T ends------------------------------------------*/
	
	$('#acuityDash').on('click',function(){
		$('#acuityDashForm').submit();
	});
	$('#acuityHIList').on('click',function(){
		$('#acuityHIListForm').submit();
	});
	$('#acuityHIReg').on('click',function(){
		$('#acuityHIRegForm').submit();
	});
	$('#acuityASPList').on('click',function(){
		$('#acuityASPListForm').submit();
	});
	$('#acuityASPReg').on('click',function(){
		$('#acuityASPRegForm').submit();
	});
	$('#acuityUser').on('click',function(){
		$('#acuityUserForm').submit();
	});
	$('#acuityVar').on('click',function(){
		$('#acuityVarForm').submit();
	});
	$('#hiDash').on('click',function(){
		$('#hiDashForm').submit();
	});
	$('#hiASPByHI').on('click',function(){
		$('#hiASPByHIForm').submit();
	});
	$('#hiorderPriority').on('click',function(){
		$('#hiorderPriorityForm').submit();
	});
	$('#activeReq').on('click',function(){
		$('#activeReqForm').submit();
	});
	$('#upcomingReq').on('click',function(){
		$('#upcomingReqForm').submit();
	});
	$('#pendingReq').on('click',function(){
		$('#pendingReqForm').submit();
	});
	$('#pastReq').on('click',function(){
		$('#pastReqForm').submit();
	});
	$('#hiSubAdmins').on('click',function(){
		$('#hiSubAdminsForm').submit();
	});
	$('#aspDashboard').on('click',function(){
		$('#aspDashboardForm').submit();
	});
	$('#aspManageVeh').on('click',function(){
		$('#aspManageVehForm').submit();
	});
	$('#aspManageCrew').on('click',function(){
		$('#aspManageCrewForm').submit();
	});
	$('#aspActiveReq').on('click',function(){
		$('#aspActiveReqForm').submit();
	});
	$('#aspUpcomingReq').on('click',function(){
		$('#aspUpcomingReqForm').submit();
	});
	$('#aspPendingReq').on('click',function(){
		$('#aspPendingReqForm').submit();
	});
	$('#aspPastReq').on('click',function(){
		$('#aspPastReqForm').submit();
	});
	$('#aspUser').on('click',function(){
		$('#aspUserForm').submit();
	});
	$('#seeAllNot').on('click',function(){
		$('#seeAllNotForm').submit();
	});
	$('#profile').on('click',function(){
		$('#profileForm').submit();
	});
	$('#acuityDefaultDash').on('click',function(){
		$('#acuityDefaultDashForm').submit();
	});
	$('#hiDefaultDash').on('click',function(){
		$('#hiDefaultDashForm').submit();
	});
	$('#aspDefaultDash').on('click',function(){
		$('#aspDefaultDashForm').submit();
	});
});
</script>