<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none white-bg hd_con">
     <div class="md-container">
         
         <!-- Navigation Menu-->
         <nav class="navbar navbar-inverse">
          <div class="container-fluid p-none">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>                        
              </button>
              <a class="navbar-brand" href="javascript:void(0)" id="landingScreen"><img src="/fdahpStudyDesigner/images/logo/logo-sm.png"/></a>
            </div>
            <div class="collapse navbar-collapse p-none" id="myNavbar">
              <ul class="nav navbar-nav">
              <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_STUDIES')}">
                <li class="studyClass"><a href="javascript:void(0)" id="studySection" >Studies</a></li>
              </c:if>
                <!-- <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">repository <span><i class="fa fa-angle-down" aria-hidden="true"></i></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Reference Tables</a></li>
                    <li><a href="#">QA content</a></li>
                    <li><a href="#">Resources</a></li>
                    <li><a href="#">Gateway app level content</a></li>
                    <li><a href="#">Legal Text</a></li>
                  </ul>
                </li> -->
                <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW')}">
                <li id="notification" class="linkDis"><a href="javascript:void(0)" id="manageNotificationSection">Notifications</a></li>
                </c:if>
                <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_MANAGE_USERS_VIEW')}">
                <li id="users"><a href="javascript:void(0)" id="usersSection">Users</a></li>
                </c:if>
              </ul>
              
              <ul class="nav navbar-nav navbar-right">
		        <li id="myAccount" class="dropdown ml-lg userLi">
		          <a class="dropdown-toggle blue-link" data-toggle="dropdown" href="javascript:void(0)">${sessionObject.firstName} ${sessionObject.lastName} &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
		          <ul class="dropdown-menu pb-none profileBox">
		         
		            <li class="linkProf"><a href="javascript:void(0)" class="blue-link text-weight-normal text-uppercase" id="profileSection">My Account</a><hr align="left" width="100%"><a href="javascript:formSubmit();" class="blue-link text-weight-normal text-uppercase"><span>sign Out</span> <span class="ml-xs"><img src="/fdahpStudyDesigner/images/icons/logout.png"/></span></a></li>
		          </ul>
		          </li>
               </ul>
              
              
               <%-- <ul class="nav navbar-nav navbar-right">
		        <li id="myAccount" class="dropdown ml-lg userLi">
		          <a class="dropdown-toggle blue-link" data-toggle="dropdown" href="javascript:void(0)">${sessionObject.firstName} ${sessionObject.lastName} &nbsp;<span class="caret"></span></a>
		          <ul class="dropdown-menu pb-none profileBox">
		         
		            <li class="linkProf"><a href="javascript:void(0)" class="blue-link text-weight-normal text-uppercase" id="profileSection">My Account</a><hr align="left" width="100%"><a href="javascript:formSubmit();" class="blue-link text-weight-normal text-uppercase"><span>sign Out</span> <span class="ml-xs"><img src="/fdahpStudyDesigner/images/icons/logout.png"/></span></a></li>
		          </ul>
		          </li>
               </ul> --%>
               
               
              <%-- <ul style="float: right;" class="nav navbar-nav">
                <li id="myAccount">
                <a href="javascript:void(0)" id="profileSection" class="blue-link">${sessionObject.firstName} ${sessionObject.lastName}&nbsp;&nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a></li>
              </ul> --%>
            </div>
          </div>
        </nav>   
         
     </div>
 </div>
 
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<form:form action="/fdahpStudyDesigner/adminUsersView/getUserList.do" id="userListForm" name="userListForm" method="post">
</form:form>
<form:form action="/fdahpStudyDesigner/adminNotificationView/viewNotificationList.do" id="manageNotificationForm" name="manageNotificationForm" method="post">
</form:form>
<form:form action="/fdahpStudyDesigner/adminDashboard/viewUserDetails.do" id="myAccountForm" name="myAccountForm" method="post">
</form:form>
<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" id="adminStudyDashForm" name="adminStudyDashForm" method="post">
</form:form>
<form:form action="/fdahpStudyDesigner/adminDashboard/viewDashBoard.do" id="landingPageForm" name="landingPageForm" method="post">
</form:form>
 <script type="text/javascript">
 $(document).ready(function(){
 	$('#usersSection').on('click',function(){
 		$('#userListForm').submit();
 	});
 	
 	$('#manageNotificationSection').on('click',function(){
 		$('#manageNotificationForm').submit();
 	});
 	
 	$('#profileSection').on('click',function(){
 		$('#myAccountForm').submit();
 	});
 	
 	$('#studySection').on('click',function(){
 		$('#adminStudyDashForm').submit();
 	});
 	
 	$('#landingScreen').on('click',function(){
 		$('#landingPageForm').submit();
 	});
 	
 });
  function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
 </script>