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
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-primary blue-btn">Publish as Upcoming Study</button>
	                </div>
	                     
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-default gray-btn launchBut">Launch Study</button>
	                </div> 
	                
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-default gray-btn cancelBut">Publish Updates</button>
	                </div>  
	                
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Pause</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Resume</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn cancelBut">Deactivate</button>
			       </div>
			       
			       <c:if test="${empty permission}">
					<div class="dis-line form-group mb-none">
						<span class="tool-tip" data-toggle="tooltip" data-placement="top"
							<c:if test="${fn:length(activeTasks) eq 0 || !markAsComplete}"> title="Please ensure individual list items are Marked as Completed before marking the section as Complete" </c:if> >
							<button type="button" class="btn btn-primary blue-btn"
								id="markAsComp" onclick="markAsCompleted();"
								<c:if test="${fn:length(activeTasks) eq 0 || !markAsComplete}">disabled</c:if>>
								Launch Study</button>
						</span>
					</div>
				  </c:if>
            </div>
            </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	 $('.launchBut').click(function() {
		   <c:if test="${empty permission}">
		   $('.cancelBut').prop('disabled', true);
		   bootbox.confirm({
				closeButton: false,
				message : 'You are about to leave the page and any unsaved changes will be lost. Are you sure you want to proceed?',	
			    buttons: {
			        'cancel': {
			            label: 'Cancel',
			        },
			        'confirm': {
			            label: 'OK',
			        },
			    },
			    callback: function(result) {
			        if (result) {
			        	var a = document.createElement('a');
			    		a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
			    		document.body.appendChild(a).click();
			        }else{
			        	$('.cancelBut').prop('disabled', false);
			        }
			    }
				});
		   </c:if>
		   <c:if test="${not empty permission}">
			   	var a = document.createElement('a');
				a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
				document.body.appendChild(a).click();
		   </c:if>
		});
});
</script>