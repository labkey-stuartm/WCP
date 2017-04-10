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
	                         <button type="button" class="btn btn-primary blue-btn" id="publishId" onclick="validateStudyStatus(this);">Publish as Upcoming Study</button>
	                </div>
	                     
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-default gray-btn " id="lunchId" onclick="validateStudyStatus(this);">Launch Study</button>
	                </div> 
	                
	                <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
	                         <button type="button" class="btn btn-default gray-btn" id="updatesId" onclick="validateStudyStatus(this);">Publish Updates</button>
	                </div>  
	                
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn " id="pauseId" onclick="validateStudyStatus(this);">Pause</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn " id="resumeId" onclick="validateStudyStatus(this);">Resume</button>
			       </div>
			       
			       <div class="form-group mr-sm" style="white-space: normal;width: 100px;">
			             <button id="addpage" type="button" class="btn btn-default gray-btn " id="deactivateId" onclick="validateStudyStatus(this);">Deactivate</button>
			       </div>
            </div>
            </div>
</div>
<script type="text/javascript">
function validateStudyStatus(obj){
	var buttonText = obj.id;
	//alert("id Name"+id);
// 	if(id){
// 		if(id == 'publishId'){
// 			$('#lunchId,#updatesId,#resumeId,#pauseId,#deactivateId').prop('disabled',true);
// 		}
// 		if(id == 'lunchId'){
// 			$('#publishId,#updatesId,#resumeId,#pauseId,#deactivateId').prop('disabled',true);	
// 		}
// 		if(id == 'updatesId'){
// 			$('#publishId,#lunchId,#resumeId,#pauseId,#deactivateId').prop('disabled',true);	
// 		}
// 		if(id == 'updatesId'){
// 			$('#publishId,#lunchId,#resumeId,#pauseId,#deactivateId').prop('disabled',true);	
// 		}
// 		if(id == 'updatesId'){
// 			$('#publishId,#lunchId,#resumeId,#pauseId,#deactivateId').prop('disabled',true);	
// 		}
// 		if(id == 'deactivateId'){
// 			$('#publishId,#lunchId,#resumeId,#pauseId').prop('disabled',true);	
// 		}
// 	}
     if(buttonText){
    	 $.ajax({
             url: "/fdahpStudyDesigner/adminStudies/validateStudyId.do",
             type: "POST",
             datatype: "json",
             data: {
            	 buttonText:buttonText,
                 "${_csrf.parameterName}":"${_csrf.token}",
             },
             success: function emailValid(data, status) {
                 var jsonobject = eval(data);
                 var message = jsonobject.message;
                 if (message == "SUCCESS") {
                	 
                 }
             },
             error:function status(data, status) {
             	$("body").removeClass("loading");
             },
             global:false,
             complete : function(){ $('.actBut').removeAttr('disabled'); }
         });
     } 

}
</script>