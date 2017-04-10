<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-sm-10 col-rc white-bg p-none">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">ACTIONS</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                     </div>
					<div class="dis-line form-group mb-none">
					</div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <!--  Start body tab section -->
            <div class="right-content-body">
               <div> 
	                <div class="form-group mb-none mr-sm">
	                         <button type="button" class="btn btn-primary blue-btn">Publish as Upcoming Study</button>
	                </div>
	                     
	                <div class="form-group mb-none mr-sm">
	                         <button type="button" class="btn btn-default gray-btn cancelBut">Launch Study</button>
	                </div> 
	                
	                <div class="form-group mb-none mr-sm">
	                         <button type="button" class="btn btn-default gray-btn cancelBut">Publish Updates</button>
	                </div>  
	                
			       <div class="form-group mb-none mr-sm">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Pause</button>
			       </div>
			       
			       <div class="form-group mb-none mr-sm">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Resume</button>
			       </div>
			       
			       <div class="form-group mb-none mr-sm">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Deactivate</button>
			       </div>
            </div>
            </div>
</div>