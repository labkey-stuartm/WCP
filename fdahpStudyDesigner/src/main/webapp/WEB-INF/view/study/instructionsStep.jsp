<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="mr-xs"><a href="#"><img src="images/icons/back-b.png"/></a></span> Add Instruction Step</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <!-- form- input-->
      <div class="mt-lg">
         <div class="gray-xs-f mb-xs">Title</div>
         <div class="form-group">
            <input type="text" class="form-control" required />
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- form- input-->
      <div class="mt-lg">
         <div class="gray-xs-f mb-xs">Instruction Text</div>
         <div class="form-group m-none">
            <textarea class="form-control" rows="5" id="comment"></textarea>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- form- input-->
      <div class="col-md-4 col-lg-4 mt-sm p-none">
         <div class="gray-xs-f mb-xs">Button Text</div>
         <div class="form-group">
            <input type="text" class="form-control" required />
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->