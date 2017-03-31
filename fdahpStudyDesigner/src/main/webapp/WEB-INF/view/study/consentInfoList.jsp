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
          <div class="black-md-f text-uppercase dis-line pull-left line34">Consent / Educational Info</div>
          <div class="dis-line form-group mb-none mr-sm">
              <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
          </div>
          <div class="dis-line form-group mb-none">
	          <c:if test="${empty permission}">
		          <span class="tool-tip" data-toggle="tooltip" data-placement="top" <c:if test="${fn:length(consentInfoList) eq 0 || !markAsComplete}"> title="Please ensure individual list items are Marked as Completed before marking the section as Complete" </c:if> >
				    <button type="button" class="btn btn-primary blue-btn" id="markAsCompleteBtnId" onclick="markAsCompleted();"  <c:if test="${fn:length(consentInfoList) eq 0 || !markAsComplete}">disabled</c:if>  >Mark as Completed</button>
				  </span>
	         </c:if>
          </div> 		  
       </div>         
    </div>
	<!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div>
         <table id="consent_list" class="display bor-none" cellspacing="0" width="100%">
            <thead>
               <tr>
                  <th>#</th>
                  <th>Consent Title</th>
                  <th>visual step</th>
                  <th>
                  	 <div class="dis-line form-group mb-none">
                  	 <c:if test="${empty permission}">
                        <button type="button" class="btn btn-primary blue-btn" onclick="addConsentPage();">+ Add Consent</button>
                     </c:if>
                     </div>
                  </th>
               </tr>
            </thead>
            <tbody>
             <c:forEach items="${consentInfoList}" var="consentInfo">
	              <tr id="${consentInfo.id}">
	                  <td>${consentInfo.sequenceNo}</td>
	                  <td>${consentInfo.displayTitle}</td>
	                  <td>${consentInfo.visualStep}</td>
	                  <td>
	                  	 <span class="sprites_icon preview-g mr-lg" onclick="viewConsentInfo(${consentInfo.id});"></span>
		                     <span class="sprites_icon edit-g mr-lg<c:if test="${not empty permission}"> cursor-none </c:if>" onclick="editConsentInfo(${consentInfo.id});"></span>
		                     <span class="sprites_icon copy delete<c:if test="${not empty permission}"> cursor-none </c:if>" onclick="deleteConsentInfo(${consentInfo.id});"></span>
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
<form:form action="/fdahpStudyDesigner/adminStudies/consentInfo.do" name="consentInfoForm" id="consentInfoForm" method="post">
<input type="hidden" name="consentInfoId" id="consentInfoId" value="">
<input type="hidden" name="actionType" id="actionType">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
<form:form action="/fdahpStudyDesigner/adminStudies/consentMarkAsCompleted.do" name="comprehensionInfoForm" id="comprehensionInfoForm" method="post">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	 // Fancy Scroll Bar
  //  $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
   // $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".fifthConsent").addClass('active'); 
    /* $("li.first").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>").nextUntil("li.fifth").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>"); */
	$("#createStudyId").show();
    var viewPermission = "${permission}";
    var permission = "${permission}";
    console.log("viewPermission:"+viewPermission);
    var reorder = true;
    if(viewPermission == 'view'){
        reorder = false;
    }else{
    	reorder = true;
    } 
	var table1 = $('#consent_list').DataTable( {
	    "paging":false,
	    "info": false,
	    "filter": false,
	     rowReorder: reorder,
         "columnDefs": [ { orderable: false, targets: [0,1,2] } ],
	     "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
	    	 if(viewPermission != 'view'){
	    		 $('td:eq(0)', nRow).addClass("cursonMove dd_icon");
	    	 } 
	    	// $('td:eq(0)', nRow).addClass("cursonMove dd_icon");
	      }
	});
	
	table1.on( 'row-reorder', function ( e, diff, edit ) {
		var oldOrderNumber = '', newOrderNumber = '';
	    var result = 'Reorder started on row: '+edit.triggerRow.data()[1]+'<br>';
		var studyId = $("#studyId").val();
	    for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
	        var rowData = table1.row( diff[i].node ).data();
	        if(i==0){
	        	oldOrderNumber = diff[i].oldData;
	            newOrderNumber = diff[i].newData;
	        }
	        result += rowData[1]+' updated to be in position '+
	            diff[i].newData+' (was '+diff[i].oldData+')<br>';
	    }

	    console.log('oldOrderNumber:'+oldOrderNumber);
	    console.log('newOrderNumber:'+newOrderNumber);
	    console.log('studyId:'+studyId);
	    
	    if(oldOrderNumber !== undefined && oldOrderNumber != null && oldOrderNumber != "" 
			&& newOrderNumber !== undefined && newOrderNumber != null && newOrderNumber != ""){
	    	$.ajax({
				url: "/fdahpStudyDesigner/adminStudies/reOrderConsentInfo.do",
				type: "POST",
				datatype: "json",
				data:{
					studyId : studyId,
					oldOrderNumber: oldOrderNumber,
					newOrderNumber : newOrderNumber,
					"${_csrf.parameterName}":"${_csrf.token}",
				},
				success: function consentInfo(data){
					var status = data.message;
					if(status == "SUCCESS"){
						$('#alertMsg').show();
						$("#alertMsg").removeClass('e-box').addClass('s-box').html("Reorder done successfully");
					}else{
						$('#alertMsg').show();
						$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to reorder consent");
		            }
					setTimeout(hideDisplayMessage, 4000);
				},
				error: function(xhr, status, error) {
				  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
				  setTimeout(hideDisplayMessage, 4000);
				}
			});
	    }
	});
	
	
	if(document.getElementById("markAsCompleteBtnId") != null && document.getElementById("markAsCompleteBtnId").disabled){
		$('[data-toggle="tooltip"]').tooltip();
	}
});
function deleteConsentInfo(consentInfoId){
	bootbox.confirm("Are you sure you want to delete this consent item?", function(result){ 
		if(result){
			var studyId = $("#studyId").val();
	    	if(consentInfoId != '' && consentInfoId != null && typeof consentInfoId!= 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteConsentInfo.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				consentInfoId: consentInfoId,
	    				studyId : studyId,
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
function reloadData(studyId){
	$.ajax({
		url: "/fdahpStudyDesigner/adminStudies/reloadConsentListPage.do",
	    type: "POST",
	    datatype: "json",
	    data: {
	    	studyId: studyId,
	    	"${_csrf.parameterName}":"${_csrf.token}",
	    },
	    success: function status(data, status) {
	    	 var jsonobject = eval(data);
	         var message = jsonobject.message;
	         if(message == "SUCCESS"){
	        	 reloadConsentInfoDataTable(jsonobject.consentInfoList);
	         }
	    },
	    error:function status(data, status) {
	    	
	    },
	});
}
function  reloadConsentInfoDataTable(consentInfoList){
	 $('#consent_list').DataTable().clear();
	 if (typeof consentInfoList != 'undefined' && consentInfoList != null && consentInfoList.length >0){
		 $.each(consentInfoList, function(i, obj) {
			 var datarow = [];
			 if(typeof obj.sequenceNo === "undefined" && typeof obj.sequenceNo === "undefined" ){
					datarow.push(' ');
			 }else{
					datarow.push(obj.sequenceNo);
			 }	
			 if(typeof obj.displayTitle === "undefined" && typeof obj.displayTitle === "undefined" ){
					datarow.push(' ');
			 }else{
					datarow.push(obj.displayTitle);
			 }	
			 if(typeof obj.visualStep === "undefined" && typeof obj.visualStep === "undefined" ){
					datarow.push(' ');
			 }else{
					datarow.push(obj.visualStep);
			 }	
			 var actions = "<span class='sprites_icon edit-g mr-lg' onclick='editConsentInfo("+obj.id+");'></span><span class='sprites_icon copy delete' onclick='deleteConsentInfo("+obj.id+");'></span>";
			 datarow.push(actions);
			 $('#consent_list').DataTable().row.add(datarow);
		 });
		 $('#consent_list').DataTable().draw();
	 }else{
		 $('#consent_list').DataTable().draw();
	 }
}
function addConsentPage(){
	$("#consentInfoId").val('');
	$("#actionType").val('addEdit');
	$("#consentInfoForm").submit();
}
/* function cancelPage(){
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
	document.body.appendChild(a).click();
} */
function markAsCompleted(){
	var table = $('#consent_list').DataTable();
	if (!table.data().count() ) {
	    console.log( 'Add atleast one consent !' );
	    $(".tool-tip").attr("title","Please ensure individual list items are marked Done, before marking the section as Complete");
	    $('#markAsCompleteBtnId').prop('disabled',true);
	    $('[data-toggle="tooltip"]').tooltip();
	}else{
		$("#comprehensionInfoForm").submit();
		//alert( 'NOT Empty table' );
	}
}
function editConsentInfo(consentInfoId){
	console.log("consentInfoId:"+consentInfoId);
	if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId !='undefined'){
		$("#consentInfoId").val(consentInfoId);
		$("#actionType").val('addEdit');
		$("#consentInfoForm").submit();
	}
}

function viewConsentInfo(consentInfoId){
	console.log("consentInfoId:"+consentInfoId);
	if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId !='undefined'){
		$("#actionType").val('view');
		$("#consentInfoId").val(consentInfoId);
		$("#consentInfoForm").submit();
	}
}
</script>

 <script>
    // Fancy Scroll Bar
		(function($){
			$(window).on("load",function(){
				
				$(".scrollbars").mCustomScrollbar({					
					theme:"minimal-dark"
				});
			});
		})(jQuery);
    </script>