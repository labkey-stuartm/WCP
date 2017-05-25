<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.cursonMove {
	cursor: move !important;
}

.sepimgClass {
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
.tool-tip {
	display: inline-block;
}

.tool-tip [disabled] {
	pointer-events: none;
}
</style>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
<div class="col-sm-10 col-rc white-bg p-none">
	<!--  Start top tab section-->
	<!-- <div class="right-content-head">
	   <div class="text-left">
	      <div class="black-md-f text-uppercase dis-line line34">Consent / Educational Info</div>
	   </div>
	</div> -->
	<div class="right-content-head">
		<div class="text-right">
			<div class="black-md-f text-uppercase dis-line pull-left line34">RESOURCES <c:set var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive]?'<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}</div>
			<div class="dis-line form-group mb-none mr-sm">
				<button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
			</div>
			<c:if test="${empty permission}">
				<div class="dis-line form-group mb-none">
					<span class="tool-tip" data-toggle="tooltip" data-placement="bottom"
						<c:if test="${not empty resourcesSavedList}">title="Please ensure individual list items are marked Done, before marking the section as Complete" </c:if>>
						<button type="button" class="btn btn-primary blue-btn"
							id="markAsComp" onclick="markAsCompleted();"
							<c:if test="${fn:length(resourcesSavedList) ne 0}">disabled</c:if>>
							Mark as Completed</button>
					</span>
				</div>
			</c:if>
		</div>
	</div>
	<!--  End  top tab section-->
	<!--  Start body tab section -->
	<div class="right-content-body pt-none pb-none">
		<div>
			<table id="resource_list" class="display bor-none tbl_rightalign"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>RESOURCE TITLE</th>
						<!-- <th>Consent Title</th>
                  <th>visual step</th> -->
						<!-- <th>
                  	 
                  </th> -->

						<th><c:if test="${empty permission}">
								<div class="dis-line form-group mb-none mr-sm">
									<button type="button" id="studyProtocolId"
										class="btn btn-primary blue-btn"
										onclick="addStudyProtocol(${studyProtocolResourceBO.id});">
										Study Protocol</button>
								</div>

								<div class="dis-line form-group mb-none">
									<button type="button" id="addResourceId"
										class="btn btn-primary blue-btn" onclick="addResource();">+
										Add Resource</button>
								</div>
							</c:if></th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resourceBOList}" var="resourceInfo">
						<c:if test="${not resourceInfo.studyProtocol}">
							<tr id="row${resourceInfo.id}">
								<td>${resourceInfo.title}</td>
								<td><span class="sprites_icon preview-g mr-lg" data-toggle="tooltip" data-placement="top" title="View"   id="viewRes"
									onclick="viewResourceInfo(${resourceInfo.id});"></span> <span
									class="${resourceInfo.action?'edit-inc':'edit-inc-draft mr-md'} mr-lg <c:if test="${not empty permission}"> cursor-none </c:if>"
									data-toggle="tooltip" data-placement="top" title="Edit" id="editRes" onclick="editResourceInfo(${resourceInfo.id});"></span>
									<span
									class="sprites_icon copy delete <c:if test="${not empty permission}"> cursor-none </c:if>"
									data-toggle="tooltip" data-placement="top" title="Delete" id="delRes" onclick="deleteResourceInfo(${resourceInfo.id});"></span>
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
<form:form
	action="/fdahpStudyDesigner/adminStudies/addOrEditResource.do?_S=${param._S}"
	name="resourceInfoForm" id="resourceInfoForm" method="post">
	<input type="hidden" name="resourceInfoId" id="resourceInfoId" value="">
	<input type="hidden" name="isstudyProtocol" id="isstudyProtocol" value="">
	<input type="hidden" name="actionOn" id="actionOn" value="">
	<%-- <input type="hidden" name="studyId" id="studyId" value="${studyId}" /> --%>
</form:form>
<form:form
	action="/fdahpStudyDesigner/adminStudies/resourceMarkAsCompleted.do?_S=${param._S}"
	name="resourceMarkAsCompletedForm" id="resourceMarkAsCompletedForm"
	method="post">
	<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
<script type="text/javascript">
var dataTable;
$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	/* <c:if test="${not empty resourcesSavedList}"> */
	/* if(document.getElementById("markAsComp").disabled){ */
		$('[data-toggle="tooltip"]').tooltip();
/* 	} */
	/* </c:if> */
	
	 // Fancy Scroll Bar
   // $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
 //   $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".eighthResources").addClass('active'); 
	$("#createStudyId").show();
	$('.eighthResources').removeClass('cursor-none');
	
	dataTable = $('#resource_list').DataTable({
	    "paging":   false	,
	    "order": [],
		"columnDefs": [ { orderable: false, targets: [0] } ],
	    /* "abColumns": [
	       { "bSortable": true }
	      ], */
	    "info" : false, 
	    "lengthChange": false, 
	    "searching": false, 
	   /*  "pageLength": 15, */
	});
});
function deleteResourceInfo(resourceInfoId){
	$('#delRes').addClass('cursor-none');
	bootbox.confirm("Are you sure you want to delete this resource?", function(result){ 
		if(result){
	    	if(resourceInfoId != '' && resourceInfoId != null && typeof resourceInfoId != 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteResourceInfo.do?_S=${param._S}",
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
	    				if(status == "SUCCESS"){
							dataTable
	    			        .row($('#row'+resourceInfoId))
	    			        .remove()
	    			        .draw();
	    					if(resourceSaved){
	    						$('#markAsComp').prop('disabled',true);
	    						$('[data-toggle="tooltip"]').tooltip();
	    					}else{
	    						$('#markAsComp').prop('disabled',false);
	    						$('[data-toggle="tooltip"]').tooltip('destroy');
	    					}
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Resource deleted successfully");
	    					$('#alertMsg').show();
	    					/* reloadData(studyId); */
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
	$('#delRes').removeClass('cursor-none');
}

function addStudyProtocol(studyProResId){
	$('#studyProtocolId').prop('disabled', true);
	if(studyProResId != null && studyProResId != '' && typeof studyProResId !='undefined'){
		$("#resourceInfoId").val(studyProResId);
		$("#actionOn").val("edit");
	}else{
		$("#resourceInfoId").val('');
		$("#actionOn").val("add");
	}
	$("#isstudyProtocol").val('isstudyProtocol');
	$("#resourceInfoForm").submit();
} 

function addResource(){
	$('#addResourceId').prop('disabled', true);
	$("#resourceInfoId").val('');
	$("#actionOn").val('add');
	$("#resourceInfoForm").submit();
} 

function editResourceInfo(resourceInfoId){
	/* console.log("resourceInfoId:"+resourceInfoId); */
	if(resourceInfoId != null && resourceInfoId != '' && typeof resourceInfoId !='undefined'){
		$('#editRes').addClass('cursor-none');
		$("#resourceInfoId").val(resourceInfoId);
		$("#actionOn").val('edit');
		$("#resourceInfoForm").submit();
	}
}
function viewResourceInfo(resourceInfoId){
	/* console.log("resourceInfoId:"+resourceInfoId); */
	if(resourceInfoId != null && resourceInfoId != '' && typeof resourceInfoId !='undefined'){
		$('#viewRes').addClass('cursor-none');
		$("#resourceInfoId").val(resourceInfoId);
		$("#actionOn").val('view');
		$("#resourceInfoForm").submit();
	}
}

function markAsCompleted(){
	$('#resourceMarkAsCompletedForm').submit();
}


function hideDisplayMessage(){
	$('#alertMsg').hide();
}
</script>