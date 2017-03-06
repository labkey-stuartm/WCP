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
      <div class="mb-xlg">
         <table id="comprehension_list" class="display bor-none" cellspacing="0" width="100%">
           <thead>
			   <tr>
			      <th>#</th>
			      <th>Question</th>
			      <th>
			         <div class="dis-line form-group mb-none">
			            <button type="button" class="btn btn-primary blue-btn">+ Add Question</button>
			         </div>
			      </th>
			   </tr>
			</thead>
            <tbody>
             <c:forEach items="${comprehensionTestQuestionList}" var="comprehensionTestQuestion">
	              <tr id="${comprehensionTestQuestion.id}">
	                  <td>${comprehensionTestQuestion.sequenceNo}</td>
	                  <td>${comprehensionTestQuestion.questionText}</td>
	                  <td>
	                     <span class="sprites_icon edit-g mr-lg" onclick="editConsentInfo(${comprehensionTestQuestion.id});"></span>
	                     <span class="sprites_icon copy delete" onclick="deleteComprehensionQuestion(${comprehensionTestQuestion.id});"></span>
	                  </td>
	               </tr>
             </c:forEach>
            </tbody>
         </table>
      </div>
      <div class="clearfix"></div>
      
      <div class="mb-xlg" id="displayTitleId">
         <div class="gray-xs-f mb-xs">Minimum score needed to pass</div>
         <div class="form-group col-md-5 p-none">
            <input type= "text" id="comprehensionTestMinimumScore" class="form-control" name="comprehensionTestMinimumScore" required value="${comprehensionTestMinimumScore}" maxlength="3" onkeypress="return isNumber(event)">
            <div class="help-block with-errors red-txt"></div>
         </div>
         <input type="hidden"name="consentId" id="consentId" value="${consentId}" />
      </div>
   </div>
   <!--  End body tab section -->
</div>
<form:form action="/fdahpStudyDesigner/adminStudies/consentReview.do" name="comprehensionInfoForm" id="comprehensionInfoForm" method="post">
<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
</form:form>
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
	$("#comprehensionTestMinimumScore").keyup(function(){
		$("#comprehensionTestMinimumScore").parent().find(".help-block").empty();
	});
});
function isNumber(evt) {
	evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode < 48 && charCode > 57) || (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)){
    	 return false;
    }
    return true;
}
function deleteComprehensionQuestion(questionId){
	bootbox.confirm("Are you sure want to delete consent!", function(result){ 
		if(result){
			var studyId = $("#studyId").val();
	    	if(questionId != '' && questionId != null && typeof questionId!= 'undefined'){
	    		$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteComprehensionQuestion.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				comprehensionQuestionId: questionId,
	    				studyId : studyId,
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteConsentInfo(data){
	    				var status = data.message;
	    				if(status == "SUCCESS"){
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Question deleted successfully");
	    					$('#alertMsg').show();
	    					reloadData(studyId);
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete Question");
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
		url: "/fdahpStudyDesigner/adminStudies/reloadComprehensionQuestionListPage.do",
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
	        	 reloadComprehensionQuestionDataTable(jsonobject.comprehensionTestQuestionList);
	         }
	    },
	    error:function status(data, status) {
	    	
	    },
	});
}
function reloadComprehensionQuestionDataTable(comprehensionTestQuestionList){
	$('#comprehension_list').DataTable().clear();
	 if (typeof comprehensionTestQuestionList != 'undefined' && comprehensionTestQuestionList != null && comprehensionTestQuestionList.length >0){
		 $.each(comprehensionTestQuestionList, function(i, obj) {
			 var datarow = [];
			 if(typeof obj.sequenceNo === "undefined" && typeof obj.sequenceNo === "undefined" ){
					datarow.push(' ');
			 }else{
					datarow.push(obj.sequenceNo);
			 }	
			 if(typeof obj.questionText === "undefined" && typeof obj.questionText === "undefined" ){
					datarow.push(' ');
			 }else{
					datarow.push(obj.questionText);
			 }	
			 var actions = "<span class='sprites_icon edit-g mr-lg' onclick='editConsentInfo("+obj.id+");'></span><span class='sprites_icon copy delete' onclick='deleteComprehensionQuestion("+obj.id+");'></span>";
			 datarow.push(actions);
			 $('#comprehension_list').DataTable().row.add(datarow);
		 });
		 $('#comprehension_list').DataTable().draw();
	 }else{
		 $('#comprehension_list').DataTable().draw();
	 }
}
/* function cancelPage(){
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
	document.body.appendChild(a).click();
} */
function markAsCompleted(){
	var table = $('#comprehension_list').DataTable();
	var minimumScore = $("#comprehensionTestMinimumScore").val();
	if (!table.data().count() ) {
	    bootbox.alert( 'Add atleast one question !' );
	}else if(minimumScore == null || minimumScore == '' || typeof minimumScore == 'undefined'){
		$("#comprehensionTestMinimumScore").parent().find(".help-block").empty();
		$("#comprehensionTestMinimumScore").parent().find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
	}else{
		saveConsent();
		//$("#comprehensionInfoForm").submit();
	}
}
function saveConsent(){
	var consentId = $("#consentId").val();
	var minimumScore = $("#comprehensionTestMinimumScore").val();
	var studyId = $("#studyId").val();
	if((minimumScore != null && minimumScore != '' && typeof minimumScore != 'undefined') && (studyId != null && studyId != '' && typeof studyId != 'undefined')){
		var consentInfo =  new Object();
		if(consentId != null && consentId != '' && typeof consentId != 'undefined'){
			consentInfo.id=consentId;
		}
		consentInfo.studyId=studyId;
		consentInfo.comprehensionTestMinimumScore=minimumScore;
		var data = JSON.stringify(consentInfo);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveConsentReviewAndEConsentInfo.do",
	          type: "POST",
	          datatype: "json",
	          data: {consentInfo:data},
	          beforeSend: function(xhr, settings){
	              xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	          },
	          success:function(data){
	        	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					var consentInfoId = jsonobject.consentId;
					$("#consentId").val(consentId);
					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Minimum score saved successfully");
					$('#alertMsg').show();
					var a = document.createElement('a');
					a.href = "/fdahpStudyDesigner/adminStudies/consentReview.do";
					document.body.appendChild(a).click();
				}else{
					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
					$('#alertMsg').show();
				}
				setTimeout(hideDisplayMessage, 4000);
	          },
	          error: function(xhr, status, error) {
    			  $('#alertMsg').show();
    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
    			  setTimeout(hideDisplayMessage, 4000);
    		  }
	   });
	}
}
</script>