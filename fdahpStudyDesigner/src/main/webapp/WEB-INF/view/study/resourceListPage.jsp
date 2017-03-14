<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.cursonMove{
 cursor: move !important;
}
.sepimgClass{
 position: relative;
}
/* .dd_icon:after{
    width: 9px;
    content: ' ';
    position: absolute;
    background-image: url(../images/icons/drag.png);
    height: 13px;
    background-repeat: no-repeat;
    display: inline-block;
    vertical-align: middle;
    margin-left: 10px;
} */
/* .dd_icon:after{
    background: url("../images/icons/drag.png");
    width: 9px;
    height: 13px;
    display: inline-block;
    vertical-align: middle;
    margin-left: 10px;
    content: ' ';
}  */
</style>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
	<!-- <div class="right-content-head">
	   <div class="text-left">
	      <div class="black-md-f text-uppercase dis-line line34">Consent / Educational Info</div>
	   </div>
	</div> -->
	<div class="right-content-head">        
       <div class="text-right">
          <div class="black-md-f text-uppercase dis-line pull-left line34">RESOURCES</div>
          <div class="dis-line form-group mb-none mr-sm">
              <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
          </div>
          <div class="dis-line form-group mb-none">
              <button type="button" class="btn btn-primary blue-btn" id="markAsComp" onclick="markAsCompleted();"
              	<c:if test="${not empty resourcesSavedList}">disabled</c:if>>Mark as Completed
          	  </button>
          </div> 		  
       </div>         
    </div>
	<!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div>
         <table id="resource_list" class="display bor-none tbl_rightalign" cellspacing="0" width="100%">
            <thead>
               <tr>
                  <th>RESOURCE TITLE</th>
                  <!-- <th>Consent Title</th>
                  <th>visual step</th> -->
                  <!-- <th>
                  	 
                  </th> -->
                  <th>
                     <div class="dis-line form-group mb-none mr-sm">
                        <button type="button" id="studyProtocolId" class="btn btn-primary blue-btn" onclick="addStudyProtocol(${studyProtocolResourceBO.id});">+ Study Protocol</button>
                     </div>
                     
                  	 <div class="dis-line form-group mb-none">
                        <button type="button" id="addResourceId" class="btn btn-primary blue-btn" onclick="addResource();">+ Add Resource</button>
                     </div>
                  </th>
               </tr>
            </thead>
            <tbody>
             <c:forEach items="${resourceBOList}" var="resourceInfo">
             <c:if test="${not resourceInfo.studyProtocol}">
             		<tr id="row${resourceInfo.id}">
	                  <td>${resourceInfo.title}</td>
	                  <td>
	                  	 <!-- <span class="sprites_icon preview-g mr-lg"></span> -->
	                     <span class="sprites_icon edit-g mr-lg" onclick="editResourceInfo(${resourceInfo.id});"></span>
	                     <span class="sprites_icon copy delete" onclick="deleteResourceInfo(${resourceInfo.id});"></span>
	                  </td>
	               </tr>
	         </c:if>
             </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<form:form action="/fdahpStudyDesigner/adminStudies/addOrEditResource.do" name="resourceInfoForm" id="resourceInfoForm" method="post">
<input type="hidden" name="resourceInfoId" id="resourceInfoId" value="">
<input type="hidden" name="studyProtocol" id="studyProtocol" value="">
<%-- <input type="hidden" name="studyId" id="studyId" value="${studyId}" /> --%>
</form:form>
<c:if test="${empty resourcesSavedList}">
<form:form action="/fdahpStudyDesigner/adminStudies/resourceMarkAsCompleted.do" name="resourceMarkAsCompletedForm" id="resourceMarkAsCompletedForm" method="post">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
</c:if>
<script type="text/javascript">
$(document).ready(function(){
	 // Fancy Scroll Bar
    $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
    $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".eighthResources").addClass('active'); 
	$("#createStudyId").show();
	
	$('#resource_list').DataTable({
	    "paging":   true,
	    "abColumns": [
	       { "bSortable": true }
	      ],
	    "info" : false, 
	    "lengthChange": false, 
	    "searching": false, 
	    "pageLength": 15,
	});
});
function deleteResourceInfo(resourceInfoId){
	bootbox.confirm("Are you sure want to delete resource!", function(result){ 
		if(result){
	    	if(resourceInfoId != '' && resourceInfoId != null && typeof resourceInfoId != 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteResourceInfo.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				resourceInfoId: resourceInfoId,
	    				studyId : '${studyId}',
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteConsentInfo(data){
	    				var status = data.message;
	    				var resourceSaved = data.resourceSaved;
	    				/* alert(resourceSaved); */
	    				if(status == "SUCCESS"){
	    					$('#row'+resourceInfoId).remove();
	    					if(resourceSaved){
	    						$('#markAsComp').prop('disabled',true);
	    					}else{
	    						$('#markAsComp').prop('disabled',false);
	    					}
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Resource deleted successfully");
	    					$('#alertMsg').show();
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete resource");
	    					$('#alertMsg').show();
	    	            }
	    				setTimeout(hideDisplayMessage, 4000);
	    			},
	    			error: function(xhr, status, error) {
	    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
	    			  setTimeout(hideDisplayMessage, 4000);
	    			}
	    		});
	    	}
		}
	});
}

function addStudyProtocol(studyProResId){
	$('#studyProtocolId').prop('disabled', true);
	$("#resourceInfoId").val(studyProResId);
	$("#studyProtocol").val('studyProtocol');
	$("#resourceInfoForm").submit();
} 

function addResource(){
	$('#addResourceId').prop('disabled', true);
	$("#resourceInfoId").val('');
	$("#resourceInfoForm").submit();
} 

function editResourceInfo(resourceInfoId){
	console.log("resourceInfoId:"+resourceInfoId);
	if(resourceInfoId != null && resourceInfoId != '' && typeof resourceInfoId !='undefined'){
		$("#resourceInfoId").val(resourceInfoId);
		$("#resourceInfoForm").submit();
	}
}

<c:if test="${empty resourcesSavedList}">
function markAsCompleted(){
	$('#resourceMarkAsCompletedForm').submit();
}
</c:if>


function hideDisplayMessage(){
	$('#alertMsg').hide();
}
</script>