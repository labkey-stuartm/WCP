
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpstudydesigner.util.SessionObject"%>

 <div class="col-sm-10 col-rc white-bg p-none">
  <!--  Start top tab section-->
  <form:form data-toggle="validator" action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyEligibilty.do?_S=${param._S}" id="eleFormId">
	  <div class="right-content-head">        
	      <div class="text-right">
	          <div class="black-md-f text-uppercase dis-line pull-left line34">Eligibility <c:set var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive] ? '<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}</div>
	          
	          <div class="dis-line form-group mb-none mr-sm">
	               <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
	           </div>
	          <c:if test="${empty permission}">
	           <div class="dis-line form-group mb-none mr-sm">
	               <button type="button" class="btn btn-default gray-btn submitEle" actType="save">Save</button>
	           </div>
	
	           <div class="dis-line form-group mb-none">
	               <button type="button" class="btn btn-primary blue-btn submitEle" actType="mark">Mark as Completed</button>
	           </div>
	           </c:if>
	       </div>
	  </div>
	        <!--  End  top tab section-->
	      <input type="hidden" value="${eligibility.studyId}" name="studyId" /> 
	      <input type="hidden" value="${eligibility.id}" name="id" /> 
	    <!--  Start body tab section -->
	    <div class="right-content-body">
	       <div class="mb-xlg form-group" id="eligibilityOptDivId">
	            <div class="gray-xs-f mb-sm">Choose the method to be used for ascertaining participant eligibility</div>
	            <span class="radio radio-info radio-inline p-45">
	               <input type="radio" id="inlineRadio1" value="1" name="eligibilityMechanism"  required <c:if test="${eligibility.eligibilityMechanism eq 1}">checked</c:if>>
	               <label for="inlineRadio1">Token Validation Only</label>
	           </span>
	           <span class="radio radio-inline p-45">
	               <input type="radio" id="inlineRadio2" value="2" name="eligibilityMechanism"  required <c:if test="${eligibility.eligibilityMechanism eq 2}">checked</c:if>>
	               <label for="inlineRadio2">Token Validation and Eligibility Test</label>
	           </span>
	             <span class="radio radio-inline">
	               <input type="radio" id="inlineRadio3" value="3" name="eligibilityMechanism"  required <c:if test="${eligibility.eligibilityMechanism eq 3}">checked</c:if>>
	               <label for="inlineRadio3">Eligibility Test Only</label>
	           </span>
	           <div class="help-block with-errors red-txt"></div>
	       </div>
	       <div id="instructionTextDivId"> 
		       <div class="blue-md-f mb-md text-uppercase">
		           Token Validation 
		       </div>
		       <div>
		           <div class="gray-xs-f mb-xs">Instruction Text <small>(230 characters max) </small><span class="requiredStar">*</span></div>
		           <div class="form-group elaborateClass">
		               <textarea class="form-control" rows="5" id="comment" maxlength="230" required name="instructionalText" >${eligibility.instructionalText}</textarea>
		               <div class="help-block with-errors red-txt"></div>
		           </div>
		       </div>
	       </div>
	    </div>
	    <div id="eligibilityQusDivId" <c:if test="${eligibility.eligibilityMechanism eq 1}">style="display: none;"</c:if>>
		    <div class="right-content-head">        
		      	<div class="text-right">
		          <div class="black-md-f  dis-line pull-left line34">Eligibility Test</div>
		          <div class="dis-line form-group mb-none mr-sm">
		          	<c:if test="${empty permission}">
		               <button type="button" class="btn btn-default gray-btn cancelBut">+ Add QA</button>
		          	</c:if>
		          </div>
				</div>
		  	</div>
		    <div class="right-content-body  pt-none pb-none">
	    		<table id="consent_list" class="display bor-none" cellspacing="0" width="100%">
			         <thead>
			            <tr>
			               <th><span class="marL10">#</span></th>
			               <th>QA</th>
			               <th>Actions</th>
			            </tr>
			         </thead>
			         <tbody>
			            <tr id="">
			                <td></td>
			                <td></td>
			                <td>
			                	<span class="sprites_icon preview-g mr-lg" data-toggle="tooltip" data-placement="top" title="View" onclick="viewConsentInfo();"></span>
			                    <span class="${true?'edit-inc':'edit-inc-draft mr-md'} mr-lg <c:if test="${not empty permission}"> cursor-none </c:if>" data-toggle="tooltip" data-placement="top" title="Edit" onclick="editConsentInfo();"></span>
			                    <span class="sprites_icon copy delete <c:if test="${not empty permission}"> cursor-none </c:if>" data-toggle="tooltip" data-placement="top" title="Delete" onclick="deleteConsentInfo();"></span>
			                </td>
			        	</tr>
	        		</tbody>
	   			</table>
		    </div>
	    </div>
    </form:form>
</div>
<script type="text/javascript">
	var viewPermission = "${permission}";
	var permission = "${permission}";
	console.log("viewPermission:"+viewPermission);
	var reorder = true;
	$(document).ready(function(){
	   $(".menuNav li.active").removeClass('active');
	   $(".menuNav li.fourth").addClass('active');
	   
	   <c:if test="${not empty permission}">
       $('#eleFormId input,textarea,select').prop('disabled', true);
       $('#eleFormId').find('.elaborateClass').addClass('linkDis');
      </c:if>
	   
	   $('.submitEle').click(function(e) {
		   e.preventDefault();
		   $('#actTy').remove();
		   $('<input />').attr('type', 'hidden').attr('name', "actionType").attr('value', $(this).attr('actType')).attr('id', 'actTy') .appendTo('#eleFormId');
	   		if($(this).attr('actType') == 'save'){
	   			$('#eleFormId').validator('destroy');
	   			$('#eleFormId').submit();
	   		} else{
	   			if(isFromValid('#eleFormId'))
	   				$('#eleFormId').submit();
	   		}
		});
	   
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
		      }
		});
		
		table1.on( 'row-reorder', function ( e, diff, edit ) {
			var oldOrderNumber = '', newOrderNumber = '';
		    var result = 'Reorder started on row: '+edit.triggerRow.data()[1]+'<br>';
			var studyId = $("#studyId").val();
		    for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
		        var rowData = table1.row( diff[i].node ).data();
		        var r1;
		        if(i==0){
		           r1 = rowData[0];
		        }	               
		        if(i==1){
		        	if(r1 > rowData[0]){
		               oldOrderNumber = diff[0].oldData;
		           	   newOrderNumber = diff[0].newData;
		        	}else{
		        		oldOrderNumber = diff[diff.length-1].oldData;
		           	    newOrderNumber = diff[diff.length-1].newData;
		        	}
				 	
		        }
		        result += rowData[1]+' updated to be in position '+
		            diff[i].newData+' (was '+diff[i].oldData+')<br>';
		    }
		    
		    if (oldOrderNumber && newOrderNumber) {
		    	$.ajax({
					url: "/fdahpStudyDesigner/adminStudies/reOrderConsentInfo.do?_S=${param._S}",
					type: "POST",
					datatype: "json",
					data:{
						studyId : studyId,
						oldOrderNumber: oldOrderNumber,
						newOrderNumber : newOrderNumber,
						"${_csrf.parameterName}":"${_csrf.token}",
					},
					success: function consentInfo(data){
						var jsonobject = eval(data);
		         		var message = jsonobject.message;
						if(message == "SUCCESS"){
						    reloadConsentInfoDataTable(jsonobject.consentInfoList);
							$('#alertMsg').show();
							$("#alertMsg").removeClass('e-box').addClass('s-box').html("Reorder done successfully");
							if ($('.fifthConsent').find('span').hasClass('sprites-icons-2 tick pull-right mt-xs')) {
							   $('.fifthConsent').find('span').removeClass('sprites-icons-2 tick pull-right mt-xs');
							}
							if ($('.fifthConsentReview').find('span').hasClass('sprites-icons-2 tick pull-right mt-xs')) {
							   $('.fifthConsentReview').find('span').removeClass('sprites-icons-2 tick pull-right mt-xs');
							}
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
		
		$('#eligibilityOptDivId input[type=radio]').change(function() {
			if($('#inlineRadio1:checked').length > 0 ) {
				$('#eligibilityQusDivId').hide();
				$('#instructionTextDivId').show();
			} else if($('#inlineRadio3:checked').length > 0 ) {
				$('#instructionTextDivId').hide();
				$('#eligibilityQusDivId').show();
			} else {
				$('#eligibilityQusDivId').show();
				$('#instructionTextDivId').show();
			}
		})
	});
	function deleteConsentInfo(consentInfoId){
		bootbox.confirm("Are you sure you want to delete this consent item?", function(result){ 
			if(result){
				var studyId = $("#studyId").val();
		    	if(consentInfoId != '' && consentInfoId != null && typeof consentInfoId!= 'undefined'){
		    		$.ajax({
		    			url: "/fdahpStudyDesigner/adminStudies/deleteConsentInfo.do?_S=${param._S}",
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
// 		    					reloadData(studyId);
		    					if ($('.fifthConsent').find('span').hasClass('sprites-icons-2 tick pull-right mt-xs')) {
								    $('.fifthConsent').find('span').removeClass('sprites-icons-2 tick pull-right mt-xs');
								}
								if ($('.fifthConsentReview').find('span').hasClass('sprites-icons-2 tick pull-right mt-xs')) {
								    $('.fifthConsentReview').find('span').removeClass('sprites-icons-2 tick pull-right mt-xs');
								}
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
			url: "/fdahpStudyDesigner/adminStudies/reloadConsentListPage.do?_S=${param._S}",
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
</script>