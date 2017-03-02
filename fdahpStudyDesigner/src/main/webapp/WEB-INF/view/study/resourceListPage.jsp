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
              <button type="button" class="btn btn-primary blue-btn" onclick="markAsCompleted();">Mark as Completed</button>
          </div> 		  
       </div>         
    </div>
	<!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div>
         <table id="resource_list" class="display bor-none" cellspacing="0" width="100%">
            <thead>
               <tr>
                  <th>RESOURCE TITLE</th>
                  <!-- <th>Consent Title</th>
                  <th>visual step</th> -->
                  <th>
                  	 <div class="dis-line form-group mb-none" style="float: right;">
                        <button type="button" class="btn btn-primary blue-btn" onclick="addResource();">+ Add Resource</button>
                     </div>
                  </th>
               </tr>
            </thead>
            <tbody>
             <c:forEach items="${resourceBOList}" var="resourceInfo">
	                  <td>${resourceInfo.title}</td>
	                  <td style="float: right;">
	                     <span class="sprites_icon edit-g mr-lg" onclick="editConsentInfo(${resourceInfo.id});"></span>
	                     <span class="sprites_icon copy delete" onclick="deleteResourceInfo(${resourceInfo.id});"></span>
	                  </td>
	               </tr>
             </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<form:form action="/fdahpStudyDesigner/adminStudies/resourceInfo.do" name="resourceInfoForm" id="resourceInfoForm" method="post">
<input type="hidden" name="resourceInfoId" id="resourceInfoId" value="">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
<%-- <form:form action="/fdahpStudyDesigner/adminStudies/consentReview.do" name="comprehensionInfoForm" id="comprehensionInfoForm" method="post">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form> --%>
<script type="text/javascript">
$(document).ready(function(){
	 // Fancy Scroll Bar
    $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
    $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".eigthResources").addClass('active'); 
    /* $("li.first").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>").nextUntil("li.fifth").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>"); */
	$("#createStudyId").show();
	var table1 = $('#resource_list').DataTable( {
	    "paging":false,
	    "info":     false,
	    "filter": false,
	    /*  rowReorder: true, */
	    /*  "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
	          $('td:eq(0)', nRow).addClass("cursonMove dd_icon");
	      } */
	});
});
function deleteResourceInfo(resourceInfoId){
	bootbox.confirm("Are you sure want to delete resource!", function(result){ 
		if(result){
	    	if(resourceInfoId != '' && resourceInfoId != null && typeof resourceInfoId != 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteConsentInfo.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				resourceInfoId: resourceInfoId,
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteConsentInfo(data){
	    				var status = data.message;
	    				if(status == "SUCCESS"){
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Consent deleted successfully");
	    					$('#alertMsg').show();
	    					reloadData(studyId);
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete consent");
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
function addResource(){
	$("#resourceInfo").val('');
	$("#resourceInfoForm").submit();
} 
/* function cancelPage(){
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
	document.body.appendChild(a).click();
} */
function markAsCompleted(){
	var table = $('#consent_list').DataTable();
	if (!table.data().count() ) {
	    alert( 'Add atleast one consent !' );
	}else{
		$("#comprehensionInfoForm").submit();
		//alert( 'NOT Empty table' );
	}
}
/* function editConsentInfo(consentInfoId){
	console.log("consentInfoId:"+consentInfoId);
	if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId !='undefined'){
		$("#consentInfoId").val(consentInfoId);
		$("#consentInfoForm").submit();
	}
} */
</script>