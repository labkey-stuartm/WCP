<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.cursonMove{
 cursor: move !important;
}
</style>
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
          <div class="black-md-f text-uppercase dis-line pull-left line34">COMPREHENSION TEST</div>
             <div class="dis-line form-group mb-none">
              <button type="button" class="btn btn-primary blue-btn" onclick="addConsentPage();">+ Add Question</button>
          	</div>
       </div>         
    </div>
	<!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div>
         <table id="comprehension_list" class="display bor-none" cellspacing="0" width="100%">
            <thead>
               <tr>
                  <th>#</th>
                  <th>Question</th>
                  <th>Actions</th>
               </tr>
            </thead>
            <tbody>
             <c:forEach items="${comprehensionTestQuestionList}" var="comprehensionTestQuestion">
	              <tr id="${comprehensionTestQuestion.id}">
	                  <td>${comprehensionTestQuestion.sequenceNo}</td>
	                  <td>${comprehensionTestQuestion.questionText}</td>
	                  <td>
	                     <span class="sprites_icon edit-g mr-lg" onclick="editConsentInfo(${comprehensionTestQuestion.id});"></span>
	                     <span class="sprites_icon copy delete" onclick="deleteConsentInfo(${comprehensionTestQuestion.id});"></span>
	                  </td>
	               </tr>
             </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){
	$(".menuNav li").removeClass('active');
    $(".fifthComre").addClass('active'); 
	$("#createStudyId").show();
	var table1 = $('#comprehension_list').DataTable( {
	    "paging":false,
	    "info":     false,
	    "filter": false,
	     rowReorder: true,
	     "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
	          $('td:eq(0)', nRow).addClass("cursonMove dd_icon");
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
				url: "/fdahpStudyDesigner/adminStudies/reOrderComprehensionTestQuestion.do",
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
						alert("hoory sucess.....")
					}else{
	                    //  bootbox.alert("<div style='color:red'>Fail to add asp</div>");
		            }
				},
				error: function(xhr, status, error) {
				  alert(xhr.responseText);
				  alert("Error : "+error);
				}
			}); 
	    }
	});
});
</script>