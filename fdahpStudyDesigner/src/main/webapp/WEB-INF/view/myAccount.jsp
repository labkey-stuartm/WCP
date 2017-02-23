<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md">
     <div class="md-container">
         <!-- widgets section-->
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              My Account
            </div>
            
            <div class="dis-line pull-right ml-md line34">
                <a href="#" class="blue-link text-weight-normal text-uppercase"><span>Log Out</span> <span class="ml-xs"><img src="images/icons/logout.png"/></span></a>  
           </div>
         </div>   
    </div>
</div>
   
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mb-lg">
     <div class="md-container white-bg box-space">
         
         <div class="b-bor">
              <div class="ed-user-layout row">               
                    <div class="col-md-6 p-none">
                       <div class="gray-xs-f line34">First Name</div>
                    </div>
                    <div class="col-md-6 p-none">
                        <div class="form-group">
                            <input type="text" class="form-control edit-field bor-trans" value="${userBO.firstName}" readonly/>
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
                            <input type="text" class="form-control edit-field bor-trans" value="${userBO.lastName}" readonly/>
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
                            <input type="text" class="form-control edit-field bor-trans" value="${userBO.userEmail}" readonly/>
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
                            <input type="text" class="form-control edit-field bor-trans" value="${userBO.phoneNumber}" readonly/>
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
                            <input type="text" class="form-control edit-field bor-trans" value="${userBO.roleName}" readonly/>
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
                        <a href="#" class="blue-link txt-decoration-underline pl-sm">Change Password</a>
                    </div>                
             </div>
        </div>
         
         <div class="mt-xlg">
              <div class="text-right"> 
                   <div class="dis-line form-group mb-none">
                        <button id="editable" type="button" class="btn btn-primary blue-btn">Edit</button>
                        <button id="ed-cancel" type="button" class="btn btn-default gray-btn dis-none">Cancel</button>
                        <button id="ed-update" type="button" class="btn btn-primary blue-btn dis-none">Update</button>
                    </div>
             </div>
         </div>
        
    </div>
</div>
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mb-md">
     <div class="md-container white-bg box-space">
         
         <div class="ed-user-layout row"> 
             <div class="blue-md-f text-uppercase mb-md">Assigned Permissions</div>
             
             <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget mb-xs">
                 <span>Manage Users</span>
                 <span class="gray-xs-f pull-right">View Only</span>
             </div>
             
             <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget mb-xs">
                 <span>Manage Repository</span>
                 <span class="gray-xs-f pull-right">View & Edit</span>
             </div>
             
              <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget mb-xs">
                 <span>Manage App-Wide Notifications</span>
                 <span class="gray-xs-f pull-right">View Only</span>
             </div>
             
             <!-- Assigned Permissions List-->
             <div class="edit-user-list-widget">
                 <span>Manage Studies</span>
                 
                 <div class="mt-lg pl-md">
                    <div class="pb-md bor-dashed">
                        <span class="dot">Adding a New Study</span> 
                    </div>
                     
                     <div class="pl-sm pt-md">
                        <span class="gray-xs-f text-weight-semibold text-uppercase">Existing Studies</span>
                     </div>
                     
                     <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">Medication Survey</span>
                            <span class="gray-xs-f pull-right">View & Edit</span>
                     </div>
                    
                     <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">A Study for Pregnant Women</span>
                            <span class="gray-xs-f pull-right">View Only</span>
                     </div> 
                     
                      <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">Medication Survey</span>
                            <span class="gray-xs-f pull-right">View & Edit</span>
                     </div> 
                     
                      <div class="pt-sm pb-sm pl-sm">
                            <span class="dot">A Study for Pregnant Women</span>
                            <span class="gray-xs-f pull-right">View Only</span>
                     </div> 
                     
                      <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">Medication Survey</span>
                            <span class="gray-xs-f pull-right">View & Edit</span>
                     </div>
                    
                     <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">A Study for Pregnant Women</span>
                            <span class="gray-xs-f pull-right">View Only</span>
                     </div> 
                     
                      <div class="pt-sm pb-sm pl-sm b-bor-dark">
                            <span class="dot">Medication Survey</span>
                            <span class="gray-xs-f pull-right">View & Edit</span>
                     </div> 
                     
                      <div class="pt-sm pb-sm pl-sm">
                            <span class="dot">A Study for Pregnant Women</span>
                            <span class="gray-xs-f pull-right">View Only</span>
                     </div> 
                     
                     
                 </div>
                 
             </div>
             
         </div>
    </div>
</div>

    

<div class="clearfix"></div>
<!--     
<div class="md-container">
     <div class="foot">
        <span>Copyright © 2016 FDA</span><span><a href="#">Terms</a></span><span><a href="#">Privacy Policy</a></span>
    </div>
</div> -->


    <!-- Vendor -->
    <!-- <script src="vendor/jquery/jquery-3.1.1.min.js"></script>
    <script src="vendor/boostrap/bootstrap.min.js"></script>
    <script src="vendor/animation/wow.min.js"></script>
    <script src="vendor/datatable/js/jquery.dataTables.min.js"></script>
    <script src="vendor/select2/bootstrap-select.min.js"></script>
    <script src="vendor/dragula/react-dragula.min.js"></script>
    <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>    
    <script src="vendor/slimscroll/jquery.slimscroll.min.js"></script> -->
    
    <!-- Theme Custom JS -->
    <!-- <script src="js/theme.js"></script>
    <script src="js/common.js"></script> -->
    
    <script>
       
        $(document).ready(function(){   
            
            // Edit & Update button toggling
            $("#editable").click(function(){
              $(".edit-field").prop('readonly', false).removeClass("bor-trans");
              $("#ed-cancel,#ed-update").removeClass("dis-none");
              $("#editable").addClass("dis-none");
            });
            
            //Cancel editing
            $("#ed-cancel").click(function(){
              $(".edit-field").prop('readonly', true).addClass("bor-trans");
              $("#ed-cancel,#ed-update").addClass("dis-none");
              $("#editable").removeClass("dis-none");
            });
            
            
        });
    </script>
