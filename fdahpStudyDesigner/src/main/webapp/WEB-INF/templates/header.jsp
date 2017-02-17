<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none white-bg">
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
              <a class="navbar-brand" href="#"><img src="/fdahpStudyDesigner/images/logo/logo-sm.png"/></a>
            </div>
            <div class="collapse navbar-collapse p-none" id="myNavbar">
              <ul class="nav navbar-nav">
                <li class="active"><a href="#">Studies</a></li>
                <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">repository <span><i class="fa fa-angle-down" aria-hidden="true"></i></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Reference Tables</a></li>
                    <li><a href="#">QA content</a></li>
                    <li><a href="#">Resources</a></li>
                    <li><a href="#">Gateway app level content</a></li>
                    <li><a href="#">Legal Text</a></li>
                  </ul>
                </li>
                <li id="notification"><a href="/fdahpStudyDesigner/adminNotificationView/viewNotificationList.do">Notifications</a></li>
                <li><a href="#">Users</a></li>
              </ul>
              <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Samuel Johnson <span><i class="fa fa-angle-down" aria-hidden="true"></i></span></a></li>
              </ul>
            </div>
          </div>
        </nav>   
         
     </div>
 </div>