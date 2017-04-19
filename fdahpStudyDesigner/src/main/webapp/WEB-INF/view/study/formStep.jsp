<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.cursonMove{
 cursor: move !important;
}
.tool-tip {
  display: inline-block;
}

.tool-tip [disabled] {
  pointer-events: none;
}
</style>
<!-- Start right Content here -->
<div class="col-sm-10 col-rc white-bg p-none">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f dis-line pull-left line34">
            <span class="mr-sm" onclick="goToBackPage(this);"><img src="../images/icons/back-b.png"/></span> 
            <c:if test="${actionTypeForQuestionPage == 'edit'}">Edit Form Step</c:if>
            <c:if test="${actionTypeForQuestionPage == 'add'}">Add Form Step</c:if>
         	<c:if test="${actionTypeForQuestionPage == 'view'}">View Form Step</c:if>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage(this);">Cancel</button>
         </div>
         <c:if test="${actionTypeForQuestionPage ne 'view'}">
	         <%-- <c:if test="${empty permission}"> --%>
	         <div class="dis-line form-group mb-none mr-sm">
	            <button type="button" class="btn btn-default gray-btn" onclick="saveFormStepQuestionnaire(this);">Save</button>
	         </div>
	         <div class="dis-line form-group mb-none">
	            <span class="tool-tip" data-toggle="tooltip" data-placement="top" <c:if test="${fn:length(questionnairesStepsBo.formQuestionMap) eq 0  || !questionnairesStepsBo.status}"> title="Please ensure individual list items are Marked as Completed before marking the section as Complete" </c:if> >
	            <button type="button" class="btn btn-primary blue-btn" id="doneId" <c:if test="${fn:length(questionnairesStepsBo.formQuestionMap) eq 0 || !questionnairesStepsBo.status}">disabled</c:if>>Done</button>
	            </span>
	         </div>
	         <%-- </c:if> --%>
         </c:if>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateFromStepQuestionnaire.do" name="formStepId" id="formStepId" method="post" data-toggle="validator" role="form">
   <div class="right-content-body pt-none pl-none pr-none">
      <ul class="nav nav-tabs review-tabs gray-bg" id="formTabConstiner">
         <li class="stepLevel active"><a data-toggle="tab" href="#sla">Step-level Attributes</a></li>
         <li class="formLevel"><a data-toggle="tab" href="#fla">Form-level Attributes</a></li>
      </ul>
      <div class="tab-content pl-xlg pr-xlg">
         <!-- Step-level Attributes--> 
         <input type="hidden" name="stepId" id="stepId" value="${questionnairesStepsBo.stepId}">
         <input type="hidden" name="questionnairesId" id="questionnairesId" value="${questionnaireId}">
         <input type="hidden" name="stepType" id="stepType" value="Form">
         <input type="hidden" name="instructionFormId" id="instructionFormId" value="${questionnairesStepsBo.instructionFormId}">
         <input type="hidden" id="type" name="type" value="complete" />
         <input type="hidden" id="questionId" name="questionId"  />
         <input type="hidden" id="actionTypeForFormStep" name="actionTypeForFormStep"  />
         
         <div id="sla" class="tab-pane fade in active mt-xlg">
            <div class="row">
               <div class="col-md-6 pl-none">
                  <div class="gray-xs-f mb-xs">Step title or Key (1 to 15 characters) <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="A human readable step identifier and must be unique across all steps of the questionnaire."></span></div>
                  <div class="form-group mb-none">
                     <input type="text" class="form-control" name="stepShortTitle" id="stepShortTitle" value="${questionnairesStepsBo.stepShortTitle}" required maxlength="15"/>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="gray-xs-f mb-xs">Step Type</div>
                  <div>Form Step</div>
               </div>
               <div class="clearfix"></div>
               <div>
                  <div class="gray-xs-f mb-xs">Is this a Skippable Step?</div>
                  <div>
                     <span class="radio radio-info radio-inline p-45">
                     <input type="radio" id="skiappableYes" value="Yes" name="skiappable"  ${empty questionnairesStepsBo.skiappable  || questionnairesStepsBo.skiappable=='Yes' ? 'checked':''}>
                     <label for="skiappableYes">Yes</label>
                     </span>
                     <span class="radio radio-inline">
                     <input type="radio" id="skiappableNo" value="No" name="skiappable" ${questionnairesStepsBo.skiappable=='No' ?'checked':''}>
                     <label for="skiappableNo">No</label>
                     </span>
                  </div>
               </div>
               <div class="clearfix"></div>
               <c:if test="${questionnaireBo.branching}">
               <div class="col-md-4 col-lg-3 p-none mt-md">
                  <div class="gray-xs-f mb-xs">Default Destination Step <span class="requiredStar">*</span> <span class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip" title="The step that the user must be directed to from this step."></span></div>
                  <div class="form-group">
                     <select  class="selectpicker" name="destinationStep" id="destinationStepId" value="${questionnairesStepsBo.destinationStep}" required>
                        <c:forEach items="${destinationStepList}" var="destinationStep">
				         	<option value="${destinationStep.stepId}" ${questionnairesStepsBo.destinationStep eq destinationStep.stepId ? 'selected' :''}>Step ${destinationStep.sequenceNo} : ${destinationStep.stepShortTitle}</option>
				        </c:forEach>
                        <option value="0" ${questionnairesStepsBo.destinationStep eq '0' ? 'selected' :''}>Completion Step</option>
                     </select>
                     <div class="help-block with-errors red-txt"></div>
                  </div>
               </div>
               </c:if>
            </div>
         </div>
         <!---  Form-level Attributes ---> 
         <div id="fla" class="tab-pane fade mt-xlg">
            <div>
               <div class="gray-xs-f mb-xs">Make form repeatable?</div>
               <div>
                  <span class="radio radio-info radio-inline p-45">
                  <input type="radio" id="repeatableYes" value="Yes" name="repeatable"  ${questionnairesStepsBo.repeatable=='Yes' ?'checked':''}>
                  <label for="repeatableYes">Yes</label>
                  </span>
                  <span class="radio radio-inline">
                  <input type="radio" id="repeatableNo" value="No" name="repeatable" ${empty questionnairesStepsBo.repeatable || questionnairesStepsBo.repeatable == 'No' ?'checked':''}>
                  <label for="repeatableNo">No</label>
                  </span>
               </div>
            </div>
            <div>
               <div class="gray-xs-f mb-xs mt-md">Repeatable Form Button text</div>
               <div class="gray-xs-f mb-xs"><small>Enter text the user should see and tap on, to repeat the form</small></div>
               <div class="form-group mb-none col-md-4 p-none">
                  <input type="text" class="form-control" placeholder="Eg: I have more medications to add" name="repeatableText" id="repeatableText" value="${questionnairesStepsBo.repeatableText}" />
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="clearfix"></div>
            <div class="row">
               <div class="col-md-6 p-none blue-md-f mb-md text-uppercase">
                  Questions in the Form
               </div>
               <div class="col-md-6 p-none">
                  <div class="dis-line form-group mb-md pull-right">
                     <button type="button" class="btn btn-default gray-btn hideButtonOnView" onclick="addNewQuestion('');">+  Add New Question</button>
                  </div>
               </div>
               <div class="clearfix"></div>
               <div class="mt-md mb-lg">
                  <table id="content" class="display" cellspacing="0" width="100%">
                  	<thead style="display: none"></thead>
                  	 <c:forEach items="${questionnairesStepsBo.formQuestionMap}" var="entry">
                  	 <tr>
                        <td>
                           <span id="${entry.key}">${entry.key}</span>
                        </td>
                        <td>
                           <div>
                              <div>${entry.value.title}</div>
                           </div>
                        </td>
                        <td>
                           <div>
                              <div class="text-right pos-relative">
                              	 <c:choose>
                              	 	<c:when test="${entry.value.responseTypeText eq 'Numeric ' && entry.value.lineChart eq 'Yes'}">
                              	 		<span class="sprites_v3 status-blue mr-md"></span>
                              	 	</c:when>
                         			<c:when test="${entry.value.responseTypeText eq 'Numeric ' && entry.value.lineChart eq 'No'}">
                              	 		<span class="sprites_v3 status-gray mr-md"></span>
                              	 	</c:when> 
                              	 	<c:when test="${entry.value.responseTypeText eq 'Date'}"><span class="sprites_v3 calender-gray mr-md"></span></c:when>
                              	 </c:choose>
                                 <span class="ellipse" onmouseenter="ellipseHover(this);"></span>
                                 <div class="ellipse-hover-icon" onmouseleave="ellipseUnHover(this);">
                                    <span class="sprites_icon preview-g mr-sm" onclick="viewQuestion(${entry.value.questionInstructionId});"></span>
                                    
                                    <span class="sprites_icon edit-g mr-sm <c:if test="${actionTypeForQuestionPage eq 'view'}"> cursor-none-without-event </c:if>"
		                         	<c:if test="${actionTypeForQuestionPage ne 'view'}">onclick="editQuestion(${entry.value.questionInstructionId});"</c:if>></span>
                                    
                                    <%-- <span class="sprites_icon edit-g mr-sm" onclick="editQuestion(${entry.value.questionInstructionId});"></span> --%>
                                    
                                     <span class="sprites_icon delete <c:if test="${actionTypeForQuestionPage eq 'view'}"> cursor-none-without-event </c:if>"
		                         		<c:if test="${actionTypeForQuestionPage ne 'view'}">onclick="deletQuestion(${entry.value.stepId},${entry.value.questionInstructionId})"</c:if>></span>
                                    
                                    <%-- <span class="sprites_icon delete" onclick="deletQuestion(${entry.value.stepId},${entry.value.questionInstructionId})"></span> --%>
                                 </div>
                              </div>
                           </div>
                        </td>
                     </tr>
                  	 </c:forEach>
                  </table>
               </div>
            </div>
         </div>
         
      </div>
   </div>
   </form:form>
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){
	
	<c:if test="${actionTypeForQuestionPage == 'view'}">
		$('#formStepId input[type="text"]').prop('disabled', true);
		$('#formStepId input[type="radio"]').prop('disabled', true);
		$('#formStepId select').addClass('linkDis');
		$('.hideButtonOnView').addClass('dis-none');
	</c:if>
	
	$(".menuNav li.active").removeClass('active');
	$(".sixthQuestionnaires").addClass('active');
     $("#doneId").click(function(){
    	 var table = $('#content').DataTable();
    	 var stepId =$("#stepId").val();
    	 if(isFromValid("#formStepId")){
    		 if(stepId != null && stepId!= '' && typeof stepId !='undefined'){
    		    if (!table.data().count() ) {
      				$('#alertMsg').show();
      				$("#alertMsg").removeClass('s-box').addClass('e-box').html("Add atleast one question");
      				setTimeout(hideDisplayMessage, 4000);
      	 			$('.formLevel a').tab('show');
 	     	 	}else{
 	     	 		document.formStepId.submit();	 
 	     	    } 
    		 }else{
    			// document.formStepId.submit();
    			 saveFormStepQuestionnaire(this, function(val) {
    	    	 if(val){
    	    		 if (!table.data().count() ) {
    	      				$('#alertMsg').show();
    	      				$("#alertMsg").removeClass('s-box').addClass('e-box').html("Add atleast one question");
    	      				setTimeout(hideDisplayMessage, 4000);
    	      	 			$('.formLevel a').tab('show');
    	 	     	 }
    	    	 }
    			});
    		 }
    		 
		}else{
		   $('.stepLevel a').tab('show');
		} 
     });
     $("#stepShortTitle").blur(function(){
     	var shortTitle = $(this).val();
     	var questionnaireId = $("#questionnairesId").val();
     	var stepType="Form";
     	var thisAttr= this;
     	var existedKey = '${questionnairesStepsBo.stepShortTitle}';
     	if(shortTitle != null && shortTitle !='' && typeof shortTitle!= 'undefined'){
     		if( existedKey !=shortTitle){
     			$.ajax({
                     url: "/fdahpStudyDesigner/adminStudies/validateQuestionnaireStepKey.do",
                     type: "POST",
                     datatype: "json",
                     data: {
                     	shortTitle : shortTitle,
                     	questionnaireId : questionnaireId,
                     	stepType : stepType
                     },
                     beforeSend: function(xhr, settings){
                         xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
                     },
                     success:  function getResponse(data){
                         var message = data.message;
                         console.log(message);
                         if('SUCCESS' != message){
                             $(thisAttr).validator('validate');
                             $(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
                             $(thisAttr).parent().find(".help-block").html("");
                         }else{
                             $(thisAttr).val('');
                             $(thisAttr).parent().addClass("has-danger").addClass("has-error");
                             $(thisAttr).parent().find(".help-block").empty();
                             $(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + shortTitle + "' already exists.</li></ul>");
                         }
                     }
               });
     		}
     	}
     });
     /* var viewPermission = "${permission}"; */
     var actionPage = "${actionTypeForQuestionPage}";
     var reorder = true;
     if(actionPage == 'view'){
         reorder = false;
     }else{
     	reorder = true;
     } 
     var table1 = $('#content').DataTable( {
 	    "paging":false,
 	    "info": false,
 	    "filter": false,
 	     rowReorder: reorder,
         "columnDefs": [ { orderable: false, targets: [0,1,2] } ],
 	     "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
 	    	 if(actionPage != 'view'){
 	    		 $('td:eq(0)', nRow).addClass("cursonMove dd_icon");
 	    	 } 
 	    	 $('td:eq(0)', nRow).addClass("qs-items");
 	    	 $('td:eq(1)', nRow).addClass("qs-items");
 	    	 $('td:eq(2)', nRow).addClass("qs-items");
 	      }
 	});
     table1.on( 'row-reorder', function ( e, diff, edit ) {
 		var oldOrderNumber = '', newOrderNumber = '';
 	    var result = 'Reorder started on row: '+edit.triggerRow.data()[1]+'<br>';
 		var formId = $("#instructionFormId").val();
 	    for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
 	        var rowData = table1.row( diff[i].node ).data();
 	        if(i==0){
 	        	oldOrderNumber = $(diff[i].oldData).attr('id');
	            newOrderNumber = $(diff[i].newData).attr('id');
 	        }
 	        result += rowData[1]+' updated to be in position '+
 	            diff[i].newData+' (was '+diff[i].oldData+')<br>';
 	    }

 	    console.log('oldOrderNumber:'+oldOrderNumber);
 	    console.log('newOrderNumber:'+newOrderNumber);
 	    console.log('formId:'+formId);
 	    if(oldOrderNumber !== undefined && oldOrderNumber != null && oldOrderNumber != "" 
 			&& newOrderNumber !== undefined && newOrderNumber != null && newOrderNumber != ""){
 	    	$.ajax({
 				url: "/fdahpStudyDesigner/adminStudies/reOrderFormQuestions.do",
 				type: "POST",
 				datatype: "json",
 				data:{
 					formId : formId,
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
    if(document.getElementById("doneId") != null && document.getElementById("doneId").disabled){
 		$('[data-toggle="tooltip"]').tooltip();
 	}
    $('[data-toggle="tooltip"]').tooltip();
});
function addNewQuestion(questionId){
	$("#questionId").val(questionId);
	$("#actionTypeForFormStep").val('add');
	document.formStepId.action="/fdahpStudyDesigner/adminStudies/formQuestion.do";	 
	document.formStepId.submit();	 
}

function viewQuestion(questionId){
	$("#questionId").val(questionId);
	$("#actionTypeForFormStep").val('view');
	document.formStepId.action="/fdahpStudyDesigner/adminStudies/formQuestion.do";	 
	document.formStepId.submit();	 
}

function editQuestion(questionId){
	$("#questionId").val(questionId);
	$("#actionTypeForFormStep").val('edit');
	document.formStepId.action="/fdahpStudyDesigner/adminStudies/formQuestion.do";	 
	document.formStepId.submit();	 
}

function saveFormStepQuestionnaire(item,callback){
	var stepId =$("#stepId").val();
	var quesstionnaireId=$("#questionnairesId").val();
	var formId = $("#instructionFormId").val();
	var shortTitle=$("#stepShortTitle").val();
	var skiappable=$('input[name="skiappable"]:checked').val();
	var destionationStep=$("#destinationStepId").val();
	var repeatable=$('input[name="repeatable"]:checked').val();
	var repeatableText=$("#repeatableText").val();
	var step_type=$("#stepType").val();
	var questionnaireStep = new Object();
	questionnaireStep.stepId=stepId;
	questionnaireStep.questionnairesId=quesstionnaireId;
	questionnaireStep.instructionFormId=formId;
	questionnaireStep.stepShortTitle=shortTitle;
	questionnaireStep.skiappable=skiappable;
	questionnaireStep.destinationStep=destionationStep;
	questionnaireStep.repeatable=repeatable;
	questionnaireStep.repeatableText=repeatableText;
	questionnaireStep.type="save";
	questionnaireStep.stepType=step_type;
	if(quesstionnaireId != null && quesstionnaireId!= '' && typeof quesstionnaireId !='undefined' && 
			shortTitle != null && shortTitle!= '' && typeof shortTitle !='undefined'){
		var data = JSON.stringify(questionnaireStep);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveFromStep.do",
	          type: "POST",
	          datatype: "json",
	          data: {questionnaireStepInfo:data},
	          beforeSend: function(xhr, settings){
	              xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
	          },
	          success:function(data){
	        	var jsonobject = eval(data);			                       
				var message = jsonobject.message;
				if(message == "SUCCESS"){
					var instructionId = jsonobject.instructionId;
					var stepId = jsonobject.stepId;
					$("#stepId").val(stepId);
					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Form Step saved successfully");
					$(item).prop('disabled', false);
					$('#alertMsg').show();
					if (callback)
						callback(true);
				}else{
					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
					$('#alertMsg').show();
					if (callback)
  						callback(false);
				}
				setTimeout(hideDisplayMessage, 4000);
	          },
	          error: function(xhr, status, error) {
    			  $(item).prop('disabled', false);
    			  $('#alertMsg').show();
    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
    			  setTimeout(hideDisplayMessage, 4000);
    		  }
	   });
	}
}
function ellipseHover(item){
	 $(item).hide();
    $(item).next().show();
}
function ellipseUnHover(item){
	$(item).hide();
   $(item).prev().show();
}
function deletQuestion(formId,questionId){
	bootbox.confirm("Are you sure you want to delete this questionnaire step?", function(result){ 
		if(result){
			if((formId != null && formId != '' && typeof formId != 'undefined') && 
					(questionId != null && questionId != '' && typeof questionId != 'undefined')){
				$.ajax({
	    			url: "/fdahpStudyDesigner/adminStudies/deleteFormQuestion.do",
	    			type: "POST",
	    			datatype: "json",
	    			data:{
	    				formId: formId,
	    				questionId: questionId,
	    				"${_csrf.parameterName}":"${_csrf.token}",
	    			},
	    			success: function deleteConsentInfo(data){
	    				 var jsonobject = eval(data);
	    				var status = jsonobject.message;
	    				if(status == "SUCCESS"){
	    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Questionnaire step deleted successfully");
	    					$('#alertMsg').show();
	    					console.log(jsonobject.questionnaireJsonObject);
	    					var questionnaireSteps = jsonobject.questionnaireJsonObject; 
	    					reloadQuestionsData(questionnaireSteps);
	    				}else{
	    					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Unable to delete questionnaire step");
	    					$('#alertMsg').show();
	    	            }
	    				setTimeout(hideDisplayMessage, 4000);
	    			},
	    			error: function(xhr, status, error) {
	    			  $("#alertMsg").removeClass('s-box').addClass('e-box').html(error);
	    			  setTimeout(hideDisplayMessage, 4000);
	    			}
	    		});
			}else{
				bootbox.alert("Ooops..! Something went worng. Try later");
			}
		}
	});
}
function reloadQuestionsData(questions){
	$('#content').DataTable().clear();
	 if(typeof questions != 'undefined' && questions != null && Object.keys(questions).length > 0){
		 $.each(questions, function(key, value) {
			 var datarow = [];
			 if(typeof key === "undefined"){
					datarow.push(' ');
				 }else{
					 datarow.push('<span id="'+key+'">'+key+'</span>');			
				 }	
			     if(typeof value.title == "undefined"){
			    	 datarow.push(' ');
			     }else{
			    	 datarow.push('<div>'+value.title+'</div>');
			     }
			     var dynamicAction ='<div>'+
			                  '<div class="text-right pos-relative">'+
			      				'<span class="sprites_v3 calender-blue mr-md"></span>'+
					              '<span class="ellipse" onmouseenter="ellipseHover(this);"></span>'+
					              '<div class="ellipse-hover-icon" onmouseleave="ellipseUnHover(this);">'+
					               '  <span class="sprites_icon preview-g mr-sm"></span>'+
					               '  <span class="sprites_icon edit-g mr-sm" onclick="addNewQuestion('+value.questionInstructionId+');"></span>'+
					               '  <span class="sprites_icon delete" onclick="deletQuestion('+value.stepId+','+value.questionInstructionId+')"></span>'+
					              '</div>'+
					           '</div></div>';
				datarow.push(dynamicAction);    	 
			$('#content').DataTable().row.add(datarow);
		 });
		 $('#content').DataTable().draw();
	 }else{
		 $('#content').DataTable().draw();
	 }
}
function goToBackPage(item){
	//window.history.back();
	$(item).prop('disabled', true);
	<c:if test="${actionTypeForQuestionPage ne 'view'}">
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
			        	a.href = "/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do";
			        	document.body.appendChild(a).click();
			        }else{
			        	$(item).prop('disabled', false);
			        }
			    }
		});
	</c:if>
	<c:if test="${actionTypeForQuestionPage eq 'view'}">
		var a = document.createElement('a');
		a.href = "/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do";
		document.body.appendChild(a).click();
	</c:if>
}
</script>