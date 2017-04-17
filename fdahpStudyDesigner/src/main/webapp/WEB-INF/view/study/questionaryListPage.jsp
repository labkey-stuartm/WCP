<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
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
            <input type="hidden" name="studyId" value="${studyBo.id}" id="studyId">
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">QUESTIONNAIRES</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Cancel</button>
                     </div>
                    
                     <!-- <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Save</button>
                     </div> -->

                     <div class="dis-line form-group mb-none">
                      <span class="tool-tip" data-toggle="tooltip" data-placement="top" <c:if test="${fn:length(questionnaires) eq 0 || !markAsComplete }"> title="Please ensure individual list items are Marked as Completed before marking the section as Complete" </c:if> >
                         <button type="button" class="btn btn-primary blue-btn" id="markAsCompleteBtnId" onclick="markAsCompleted();" <c:if test="${fn:length(questionnaires) eq 0 || !markAsComplete }"> disabled </c:if> >Mark as Completed</button>
                       </span>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                <div>
                    <table id="questionnaire_list" class="display bor-none dragtbl" cellspacing="0" width="100%">
                         <thead>
                            <tr>
                                <th>TITLE<span class="sort"></span></th>
                                <th>FREQUENCY<span class="sort"></span></th>                                
                                <th>
                                    <div class="dis-line form-group mb-none">
                                         <button type="button" class="btn btn-primary blue-btn" onclick="addQuestionnaires();">+ Add Questionnaire</button>
                                     </div>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${questionnaires}" var="questionnaryInfo">
		             	    <tr id="row${questionnaryInfo.id}">
			                  <td>${questionnaryInfo.title}</td>
			                  <td>${questionnaryInfo.frequency}</td>
			                  <td>
			                     <span class="sprites_icon edit-g mr-lg" onclick="editQuestionnaires(${questionnaryInfo.id});"></span>
			                     <span class="sprites_icon copy delete" onclick="deleteQuestionnaire(${questionnaryInfo.id});"></span>
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
        <form:form action="/fdahpStudyDesigner/adminStudies/viewQuestionnaire.do" name="questionnaireInfoForm" id="questionnaireInfoForm" method="post">
			<input type="hidden" name="questionnaireId" id="questionnaireId" value="">
			<!-- <input type="hidden" name="actionType" id="actionType"> -->
			<input type="hidden" name="studyId" id="studyId" value="${studyId}" />
		</form:form>
<script>
$(document).ready(function(){  
			$(".menuNav li.active").removeClass('active');
			$(".sixthQuestionnaires").addClass('active');
	
            // Fancy Scroll Bar
           // $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
          //  $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
            
             $('#questionnaire_list').DataTable( {
                 "paging":   true,
                 "abColumns": [
                   { "bSortable": true },
                    { "bSortable": true }
                   ],
                   "order": [[ 0, "desc" ]],
                 "info" : false, 
                 "lengthChange": false, 
                 "searching": false, 
                 "pageLength": 10 
             } );  
             
            if(document.getElementById("markAsCompleteBtnId") != null && document.getElementById("markAsCompleteBtnId").disabled){
         		$('[data-toggle="tooltip"]').tooltip();
         	}

  });
  function editQuestionnaires(questionnaryId){
	console.log("consentInfoId:"+questionnaryId);
	if(questionnaryId != null && questionnaryId != '' && typeof questionnaryId !='undefined'){
		$("#questionnaireId").val(questionnaryId);
		$("#questionnaireInfoForm").submit();
    }
  }    
  function addQuestionnaires(){
	$("#questionnaireId").val('');
	$("#questionnaireInfoForm").submit();
  }   
  function deleteQuestionnaire(questionnaireId){
	  var studyId = $("#studyId").val();
	  bootbox.confirm("Are you sure you want to delete this questionnaire item?", function(result){ 
			if(result){
				if(questionnaireId != null && questionnaireId != '' && typeof questionnaireId !='undefined'){
					$.ajax({
		    			url: "/fdahpStudyDesigner/adminStudies/deleteQuestionnaire.do",
		    			type: "POST",
		    			datatype: "json",
		    			data:{
		    				questionnaireId: questionnaireId,
		    				studyId : studyId,
		    				"${_csrf.parameterName}":"${_csrf.token}",
		    			},
		    			success: function deleteConsentInfo(data){
		    				var status = data.message;
		    				if(status == "SUCCESS"){
		    					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Questionnaire deleted successfully");
		    					$('#alertMsg').show();
		    					$("#row"+questionnaireId).remove();
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
  function markAsCompleted(){
		var table = $('#questionnaire_list').DataTable();
		if (!table.data().count() ) {
		    console.log( 'Add atleast one consent !' );
		    $(".tool-tip").attr("title","Please ensure individual list items are marked Done, before marking the section as Complete");
		    $('#markAsCompleteBtnId').prop('disabled',true);
		    $('[data-toggle="tooltip"]').tooltip();
		}else{
			document.questionnaireInfoForm.action="/fdahpStudyDesigner/adminStudies/questionnaireMarkAsCompleted.do";	 
			document.questionnaireInfoForm.submit();
		}
	}
</script>     
        
        