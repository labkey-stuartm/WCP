<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
<div class="right-content">
	<!--  Start top tab section-->
	<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="cancelConsentReviewFormId" id="cancelConsentReviewFormId" method="POST" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<input type="hidden" id="consentId" name="consentId" value="${consentId}">
	</form:form>
	<form:form action="/fdahpStudyDesigner/adminStudies/saveConsentReviewAndEConsentInfo.do" name="consentReviewFormId" id="consentReviewFormId" method="post" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<input type="hidden" id="consentId" name="consentId" value="${consentId}">
		<input type="hidden" id="consentBo" name="consentBo" value="${consentBo}">
		<input type="hidden" id="typeOfCensent" name="typeOfCensent" value="${consentBo.consentDocType}">
		<!--  End body tab section -->
		<div class="right-content">
            <!--  Start top tab section-->
            <div class="right-content-head" style="z-index:999;">    
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Review and E-Consent </div>
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage();">Cancel</button>
                     </div>
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="saveId">Save</button>
                     </div>
                     <div class="dis-line form-group mb-none">
                        <button type="button" class="btn btn-primary blue-btn" id="doneId">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <!--  Start body tab section -->
            <div class="right-content-body pt-none pl-none">
             <ul class="nav nav-tabs review-tabs">
                <li><a data-toggle="tab" href="#menu1">Share Data Permissions</a></li>
                <li class="active"><a data-toggle="tab" href="#menu2">Consent Document for Review</a></li>
                <li><a data-toggle="tab" href="#menu3">E-Consent Form</a></li>               
              </ul>
              <div class="tab-content pl-xlg pr-xlg" id="consentValidatorDiv" data-toggle="validator">
                <input type="hidden" id="version" name="version" value="${consentBo.version}">
                <div id="menu1" class="tab-pane fade">
                  <!-- <h3>Share Data Permissions</h3> -->
                  <p style="text-align: center;">This feature is work in progress and coming soon.</p>
                </div>
	                <div id="menu2" class="tab-pane fade  in active">
	                    <div class="mt-xlg">
		                	<div class="form-group">
			                	<div id="consentDocTypeDivId">
			                         <span class="radio radio-info radio-inline p-45">
			                            <input type="radio" id="inlineRadio1" value="Auto" name="consentDocType" required data-error="Please choose consent document type" ${consentBo.consentDocType=='Auto'?'checked':''}>
			                            <label for="inlineRadio1">Use auto-created Consent Document</label>
			                        </span>
			                        <span class="radio radio-inline">
			                            <input type="radio" id="inlineRadio2" value="New" name="consentDocType" required data-error="Please choose consent document type" ${consentBo.consentDocType=='New'?'checked':''}>
			                            <label for="inlineRadio2">Create New Consent Doc</label>
			                        </span>
			                        <div class="help-block with-errors red-txt"></div>
			                    </div>
		                    </div>
	                    </div>
	                    <div class="italic-txt mt-lg">
	                        <div id="autoCreateHelpTextDiv" style="display:block;">
	                        	This is a preview of the Consent Document to depict how it gets created by the ResearchKit / ResearchStack frameworks on the mobile app. Consent Items (title and long description portions) are concatenated to automatically create the Consent Document. The mobile app also generates a Consent Document PDF with participant first name, last name, signature and date, time of providing consent, as captured on the app.
	                        </div>
	                         <div id="newDocumentHelpTextDiv" style="display:none;">
	                        	Choose this option if you wish to add your content for the Consent Document instead of using the auto-generated Consent Document. Note that in this case, the mobile app will not be able to add user-specific details such as first name, last name, signature and date/time of providing consent, to the PDF that it generates for the Consent Document.
	                        </div>
	                    </div>
	                   <div class="mt-xlg">
	                        <div class="blue-lg-f text-uppercase">CONSENT DOCUMENT <span id="requiredStarId" class="requiredStar">*</span></div>
							<div class="mt-lg">
	                        <div class="cont_doc" id="autoCreateDivId" style="display:block;">
	                           <div style="height:900px;">
									<div id="autoConsentDocumentDivId"></div>
							   </div>
	                        </div>
	                        <div class="cont_editor">
			                    <div id="newDivId" style="display:none;">
									<div class="form-group elaborateClass">
							            <textarea class="" rows="8" id="newDocumentDivId" name="newDocumentDivId">${consentBo.consentDocContent}</textarea>
							            <div class="help-block with-errors red-txt"></div>
							         </div>
								</div>
	                        </div>
	                    </div>
	                    </div>
	                    <div class="mt-xlg dis-inline" id="autoCreateDivId01" style="display:block;">
	                        <div class="sign">Participant's First Name</div>
	                        <div class="sign">Last Name</div>
	                        <div class="sign">Signature</div>
	                        <div class="sign">Date</div>
	                        <div class="sign">Time</div>
	                    </div>
	                </div>
                <div id="menu3" class="tab-pane fade">
                    <div class="mt-xlg text-weight-semibold" style="text-align: center;">This feature is work in progress and coming soon.</div>
                    <div style="display:none;">
                        <div class="mt-lg form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="agreementCB" value="No" name="eConsentAgree" ${consentBo.eConsentAgree=='Yes'?'checked':''}>
                                <label for="agreementCB"> Agreement to the content in the Consent Document</label>
                            </span>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="fNameCB" value="Yes" name="eConsentFirstName" checked disabled>
                                <label for="fNameCB"> First Name</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="lNameCB" value="Yes" name="eConsentLastName" checked disabled>
                                <label for="lNameCB"> Last Name</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="eSignCB" value="Yes" name="eConsentSignature" checked disabled>
                                <label for="eSignCB"> E-signature</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="dateTimeCB" value="Yes" name="eConsentDatetime" checked disabled>
                                <label for="dateTimeCB"> Date and Time of providing Consent</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>              
              </div>
            </div>
            <!--  End body tab section -->
        </div>
        <!-- End right Content here -->
	</form:form>
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){  
	//check the type of page action(view/edit)
	if('${permission}' == 'view'){
		$('input[name="consentDocType"]').attr('disabled', 'disabled');
	    $('#newDivId .elaborateClass').addClass('linkDis');
	    $('#saveId,#doneId').hide();
	}
	
	//auto select if consent Id is empty
	var consentId = "${consentId}";
	if( consentId == null || consentId == '' || consentId === undefined){
		$("#inlineRadio1").prop('checked', 'checked');
		$("#version").val('1.0');
	}
	
	//active li
    $(".menuNav li").removeClass('active');
    $(".fifthConsentReview").addClass('active');
	$("#createStudyId").show();
	consentDocumentDivType();
	//check the consent type
	$("#consentDocTypeDivId").on('change', function(){
		consentDocumentDivType();
    });
	
	/* var isChek = "${consentBo.consentDocType}";
	if(isChek != null && isChek !='' && typeof isChek !=undefined){
		if(isChek == 'New'){
			$("#newDivId").show();
			$("#autoCreateDivId").hide();
			$("#autoCreateDivId01").hide();
			$("#inlineRadio2").prop("checked", true);
			$("#typeOfCensent").val("New");
			createNewConsentDocument();
		}else{
			$("#autoCreateDivId").show();
			$("#autoCreateDivId01").show();
	        $("#newDivId").hide();
	        $("#inlineRadio1").prop("checked", true);
	        $("#typeOfCensent").val("Auto");
	        autoCreateConsentDocument();
		}
	} */
	//go back to consentList page
	$("#saveId,#doneId").on('click', function(){
		var id = this.id;
		if( id == "saveId"){
			saveConsentReviewAndEConsentInfo("saveId");	
		}else if(id == "doneId"){
			var consentDocumentType = $('input[name="consentDocType"]:checked').val();
	    	if(consentDocumentType == "Auto"){
	    		saveConsentReviewAndEConsentInfo("doneId");
	    	}else{
	    		var content = tinymce.get('newDocumentDivId').getContent();
	    		if(content != null && content !='' && typeof content != 'undefined'){
	    			saveConsentReviewAndEConsentInfo("doneId");
	    			
	    		}else{
	    			$("#newDocumentDivId").parent().find(".help-block").empty();
		    		$("#newDocumentDivId").parent().find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
	    		}
	    	}
		}
	});
	
	//consent doc type div
	function consentDocumentDivType(){
		fancyToolbar();
		if($("#inlineRadio1").is(":checked")){
    		$("#autoCreateDivId").show();
    		$("#autoCreateDivId01").show();
	        $("#newDivId").hide();
	        $("#typeOfCensent").val("Auto");
	        $("#autoCreateHelpTextDiv").show();
	        $("#newDocumentHelpTextDiv").hide();
	        $('#requiredStarId').hide();
	        autoCreateConsentDocument();
    	}else{
    		$("#newDivId").show();
    		$("#autoCreateDivId").hide();
    		$("#autoCreateDivId01").hide();
    		$("#typeOfCensent").val("New");
    		$("#autoCreateHelpTextDiv").hide();
	        $("#newDocumentHelpTextDiv").show();
	        $('#requiredStarId').show();
    		createNewConsentDocument();
    	}
	}
	
	// Fancy Scroll Bar
    function fancyToolbar(){
    	$(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
        $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
	}
    //check the consentinfo list
    function autoCreateConsentDocument(){
    	var consentDocumentDivContent = "";
        $("#autoConsentDocumentDivId").empty();
        if( null != "${consentInfoList}" && "${consentInfoList}" != '' && "${consentInfoList}" !== undefined){
        	if($("#inlineRadio1").is(":checked")){
        		<c:forEach items="${consentInfoList}" varStatus="i" var="consentInfo">
            	consentDocumentDivContent += '<span style="font-size:20px;"><strong>'
                							+'${consentInfo.displayTitle}'
                							+'</strong></span><br/>'
                							+'<span style="display: block; overflow-wrap: break-word; width: 100%;">'
                							+'${consentInfo.elaborated}'
                							+'</span><br/>';
            	</c:forEach>
        	}
        }
        $("#autoConsentDocumentDivId").append(consentDocumentDivContent);
        
        //apply custom scroll bar to the auto consent document type
        $("#autoCreateDivId").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    }
    //createNewConsentDocument
    function createNewConsentDocument(){
    	tinymce.init({
             selector: "#newDocumentDivId",
             theme: "modern",
             skin: "lightgray",
             height:180,
             plugins: [
                 "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
                 "save contextmenu directionality paste"
             ],
             toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link | hr removeformat | cut undo redo | fontsizeselect fontselect",
             menubar: false,
             toolbar_items_size: 'small',
             content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
             entity_encoding : "raw",
         });
    	
    	/* tinymce.get('newDocumentDivId').setContent('');
    	tinymce.get('newDocumentDivId').setContent('${consentBo.consentDocContent}'); */ 
    }
    
    //save review and E-consent data
    function saveConsentReviewAndEConsentInfo(item){
		if($('input[id="agreementCB"]:checked').is(":checked")){
			$("#agreementCB").val("Yes");
	   	}else{
	   		$("#agreementCB").val("No");
	   	}
		
		$("#consentValidatorDiv").validator('validate');
   	 	var customErrorLength = $("#consentValidatorDiv").find(".has-danger").length;
	   	if((customErrorLength == 1 && $("#agreementCB").val() == 'No')){
	   		resetValidation($("#consentValidatorDiv"));
	   		customErrorLength = 0;
	   	}
	   	
   	 	if(customErrorLength == 0){
	   		var consentInfo = new Object();
	    	var consentId = $("#consentId").val();
	    	var studyId = $("#studyId").val();
	    	var agreementCB = $("#agreementCB").val();
	    	var fNameCB = $("#fNameCB").val();
	    	var lNameCB = $("#lNameCB").val();
	    	var eSignCB = $("#eSignCB").val();
	    	var dateTimeCB = $("#dateTimeCB").val();
	    	var consentDocumentContent = "";
	    	var consentDocType = $('input[name="consentDocType"]:checked').val();
	    	if(consentDocType == "New"){
	    		consentDocumentContent = tinymce.get('newDocumentDivId').getContent({ format: 'raw' });
	    		consentDocumentContent = replaceSpecialCharacters(consentDocumentContent);
	    	}
	    	if(item == "doneId"){
	    		consentInfo.type="completed";
	    	}else{
	    		consentInfo.type="save";
	    	}
	    	if(null != consentId){consentInfo.id = consentId;}
	    	if(null != studyId){consentInfo.studyId = studyId;}
	    	if(null != consentDocType){consentInfo.consentDocType = consentDocType;}
	    	if(null != consentDocumentContent){consentInfo.consentDocContent = consentDocumentContent;}
	    	if(null != agreementCB){consentInfo.eConsentAgree = agreementCB;} 
	    	if(null != fNameCB){consentInfo.eConsentFirstName = fNameCB;}
	    	if(null != lNameCB){consentInfo.eConsentLastName = lNameCB;}
	    	if(null != eSignCB){consentInfo.eConsentSignature = eSignCB;}
	    	if(null != dateTimeCB){consentInfo.eConsentDatetime = dateTimeCB;}
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
		        	var jsonobj = eval(data);                 
					var message = jsonobj.message;
					$("#alertMsg").html('');
					if(message == "SUCCESS"){
						var consentId = jsonobj.consentId;
						var studyId = jsonobj.studyId;
						$("#consentId").val(consentId);
						$("#studyId").val(studyId);
						
						var consentDocumentType = $('input[name="consentDocType"]:checked').val();
						$("#newDocumentDivId").val('');
						if(consentDocumentType == "New"){
					    	$("#newDocumentDivId").val(consentDocumentContent);
					    	tinymce.get('newDocumentDivId').setContent('');
					    	tinymce.get('newDocumentDivId').setContent(consentDocumentContent);
						}
						if(item == "doneId"){
							bootbox.confirm({
								/* closeButton: false, */
								message : "You have a setting that allows study data to be retained /deleted even if the user withdraws from the Study. Please ensure you have worded Consent Terms in accordance with this. Click OK to proceed with completing this section or Cancel if you wish to make changes.",
								callback: function(result){
									if(result){
										var a = document.createElement('a');
										a.href = "/fdahpStudyDesigner/adminStudies/consentReviewMarkAsCompleted.do";
										document.body.appendChild(a).click();
									}
								}
				    		});
						}else{
							$("#alertMsg").removeClass('e-box').addClass('s-box').html("Content saved as draft.");
							$(item).prop('disabled', false);
							$('#alertMsg').show();
						}
					}else{
						$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
						$('#alertMsg').show();
					}
					setTimeout(hideDisplayMessage, 4000);
		          },
		          error: function(xhr, status, error) {
					alert("error : "+xhr);
		          }
		   });
	   	 }
    }
});

function goToBackPage(){
	//window.history.back();
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do";
	document.body.appendChild(a).click();
}

//replace the special characters (single and double quotes with HTML number)
/* function replaceSpecialCharacters(inputFormat){
	var replacedString = "";
	if( inputFormat != null && inputFormat != '' && inputFormat !== undefined){
		inputFormat = inputFormat.replace("'", '&#39;');
		inputFormat = inputFormat.replace('"', '&#34;');
		replacedString = inputFormat;
	}
	return replacedString;
} */
</script>