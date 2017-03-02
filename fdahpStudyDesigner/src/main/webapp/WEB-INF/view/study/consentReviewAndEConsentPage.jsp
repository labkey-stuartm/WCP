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
	<form:form action="/fdahpStudyDesigner/adminStudies/saveConsentReviewAndEConsentInfo.do" name="consentReviewFormId" id="consentReviewFormId" method="post" data-toggle="validator" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<input type="hidden" id="consentId" name="consentId" value="${consentId}">
		<!--  End body tab section -->
		<div class="right-content">
            <!--  Start top tab section-->
            <div class="right-content-head">    
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Review and E-Consent </div>
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
                     </div>
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="saveId">Save</button>
                     </div>
                     <div class="dis-line form-group mb-none">
                        <button type="button" class="btn btn-primary blue-btn" id="DoneId">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <!--  Start body tab section -->
            <div class="right-content-body pt-none pl-none">
             <ul class="nav nav-tabs review-tabs">
                <li><a data-toggle="tab" href="#menu1">Share Data Permissions</a></li>
                <li class="active"><a data-toggle="tab" href="#menu2">Consent Document for Review</a></li>
                <li><a data-toggle="tab" href="#menu3">E-consent form</a></li>               
              </ul>
              <div class="tab-content pl-xlg pr-xlg">
                <div id="menu1" class="tab-pane fade">
                  <h3>Share Data Permissions</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                </div>
                <div id="menu2" class="tab-pane fade  in active">
                    <div class="mt-xlg">
	                	<div class="form-group">
		                	<div id="consentDocTypeDivId">
		                         <span class="radio radio-info radio-inline p-45">
		                            <input type="radio" id="inlineRadio1" value="Auto" name="consentDocType" required data-error="Please choose consent document type" ${consentBo.consentDocumentType=='Auto'?'checked':''}>
		                            <label for="inlineRadio1">Use auto-created Consent Document</label>
		                        </span>
		                        <span class="radio radio-inline">
		                            <input type="radio" id="inlineRadio2" value="New" name="consentDocType" required data-error="Please choose consent document type" ${consentBo.consentDocumentType=='New'?'checked':''}>
		                            <label for="inlineRadio2">Create New Consent Doc</label>
		                        </span>
		                        <div class="help-block with-errors red-txt"></div>
		                    </div>
	                    </div>
                    </div>
                    <div class="italic-txt mt-lg">
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries
                    </div>
                   <div class="mt-xlg">
                        <div class="blue-lg-f text-uppercase">CONSENT DOCUMENT</div>
						<div class="mt-lg">
                        <div class="cont_doc" id="autoCreateDivId" style="display:block;">
                           <div style="height:900px;">
								<div id="autoConsentDocumentDivId"></div>
						   </div>
                        </div>
                        <div class="cont_editor">
		                    <div id="newDivId" style="display:none;">
								<div class="form-group">
						            <textarea class="" rows="8" id="newDocumentDivId" name="newDocumentDivId" maxlength="1000">${consentBo.htmlConsent}</textarea>
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
                    <div class="mt-xlg text-weight-semibold">The mobile app captures the following from the user as part of Consent to the study:</div>
                    <div>
                        <div class="mt-lg form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="eConsentAgreeCheckboxId" value="" name="eConsentAgree"  data-error="Please choose agreement" checked>
                                <label for="eConsentAgreeCheckboxId"> Agreement to the content in the Consent Document</label>
                            </span>
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="eConsentFirstNameCheckboxId" value="Yes" name="eConsentFirstName"  data-error="Please choose first name" checked>
                                <label for="eConsentFirstNameCheckboxId"> First Name</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="eConsentLastNameCheckboxId" value="Yes" name="eConsentLastName"  data-error="Please choose last name" checked>
                                <label for="eConsentLastNameCheckboxId"> Last Name</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="eConsentSignatureCheckboxId" value="Yes" name="eConsentSignature"  data-error="Please choose e-signature" checked>
                                <label for="eConsentSignatureCheckboxId"> E-signature</label>
                            </span> 
                             <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="mt-md form-group">
                            <span class="checkbox checkbox-inline">
                                <input type="checkbox" id="dateTimeCheckboxId" value="Yes" name="dateTime"  data-error="Please choose date and time" checked>
                                <label for="dateTimeCheckboxId"> Date and Time of providing Consent</label>
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
    //active li
    $(".menuNav li").removeClass('active');
    $(".fifthConsentReview").addClass('active');
	$("#createStudyId").show();
	//check the consent type
	$("#consentDocTypeDivId").on('change', function(){
		fancyToolbar();
		if($("#inlineRadio1").is(":checked")){
    		$("#autoCreateDivId").show();
    		$("#autoCreateDivId01").show();
	        $("#newDivId").hide();
	        autoCreateConsentDocument();
    	}else{
    		$("#newDivId").show();
    		$("#autoCreateDivId").hide();
    		$("#autoCreateDivId01").hide();
    		createNewConsentDocument();
    	}
    });
	var isChek = "${consentBo.consentDocumentType}";
	console.log("isChek:"+isChek);
	if(isChek != null && isChek !='' && typeof isChek !=undefined){
		if(isChek == 'New'){
			$("#newDivId").show();
			$("#autoCreateDivId").hide();
			$("#autoCreateDivId01").hide();
			$("#inlineRadio2").prop("checked", true);
			createNewConsentDocument();
		}else{
			$("#autoCreateDivId").show();
			$("#autoCreateDivId01").show();
	        $("#newDivId").hide();
	        $("#inlineRadio1").prop("checked", true);
	        autoCreateConsentDocument();
		}
	}
	//go back to consentList page
	$("#saveId,#DoneId").on('click', function(){
		var id = this.id;
		if( id == "saveId"){
			saveConsentReviewAndEConsentInfo("saveId");	
		}else if(id == "DoneId"){
			var consentDocumentType = $('input[name="consentDocType"]:checked').val();
	    	if(consentDocumentType == "Auto"){
	    		saveConsentReviewAndEConsentInfo("DoneId");
	    	}else{
	    		var content = tinymce.get('newDocumentDivId').getContent();
	    		if(content != null && content !='' && typeof content != 'undefined'){
	    			saveConsentReviewAndEConsentInfo("DoneId");
	    			
	    		}else{
	    			$("#newDocumentDivId").parent().find(".help-block").empty();
		    		$("#newDocumentDivId").parent().find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
	    		}
	    	}
		}
	});
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
            <c:forEach items="${consentInfoList}" varStatus="i" var="consentInfo">
            consentDocumentDivContent += "<span style='font-size:20px;'><strong>"
            							 +"${consentInfo.displayTitle}"
            							 +"</strong></span><br/>"
            							 +"<span style='display: block; overflow-wrap: break-word; width: 100%;'>"
            							 +"${consentInfo.elaborated}"
            							 +"</span><br/>";
            </c:forEach>
        }
        $("#autoConsentDocumentDivId").append(consentDocumentDivContent);
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
             toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link image | hr removeformat | cut undo redo | fontsizeselect fontselect",
             menubar: false,
             toolbar_items_size: 'small',
             content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
         });
    	//tinymce.activeEditor.setContent('');
    }
    //save review and E-consent data
    function saveConsentReviewAndEConsentInfo(item){
    	var consentInfo = new Object();
    	var consentId = $("#consentId").val();
    	var studyId=$("#studyId").val();

    	//tab3 in review-E-Consent
    	var eConsentAgreeCheckboxId = $("#eConsentAgreeCheckboxId").val();
    	var eConsentFirstNameCheckboxId = $("#eConsentFirstNameCheckboxId").val();
    	var eConsentLastNameCheckboxId = $("#eConsentLastNameCheckboxId").val();
    	var eConsentSignatureCheckboxId = $("#eConsentSignatureCheckboxId").val();
    	var dateTimeCheckboxId = $("#dateTimeCheckboxId").val();
    	
    	var consentDocumentContent = "";
    	var consentDocumentType = $('input[name="consentDocType"]:checked').val();
    	console.log("consentDocumentType:"+consentDocumentType);
    	if(consentDocumentType == "Auto"){
    		//consentDocumentContent = $("#autoConsentDocumentDivId").html();
    	}else{
    		consentDocumentContent = tinymce.get('newDocumentDivId').getContent();
    	}
    	
    	if(null != consentId){consentInfo.id = consentId;}
    	if(null != studyId){consentInfo.studyId = studyId;}
    	if(null != consentDocumentType){consentInfo.consentDocumentType = consentDocumentType;}
    	if(null != consentDocumentContent){consentInfo.htmlConsent = consentDocumentContent;}
    	
    	//tab3 in review-E-Consent
    	if(null != consentId){consentInfo.eConsentAgree = eConsentAgreeCheckboxId;}
    	if(null != consentId){consentInfo.eConsentFirstName = eConsentFirstNameCheckboxId;}
    	if(null != consentId){consentInfo.eConsentLastName = eConsentLastNameCheckboxId;}
    	if(null != consentId){consentInfo.eConsentSignature = eConsentSignatureCheckboxId;}
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
				if(message == "SUCCESS"){
					var consentId = jsonobj.consentId;
					var studyId = jsonobj.studyId;
					$("#consentId").val(consentId);
					$("#studyId").val(studyId);
					if(item == "DoneId"){
						var a = document.createElement('a');
						a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
						document.body.appendChild(a).click();
					}
				}
	          },
	          error: function(xhr, status, error) {
				alert("error : "+error);
	          }
	   });
    }
});
</script>