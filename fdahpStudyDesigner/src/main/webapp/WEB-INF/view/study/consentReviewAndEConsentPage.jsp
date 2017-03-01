<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
<div class="right-content">
	<!--  Start top tab section-->
	<form:form action="/fdahpStudyDesigner/adminStudies/consentListPage.do" name="cancelConsentReviewFormId" id="cancelConsentReviewFormId" method="POST" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<input type="hidden" id="consentId" name="consentId" value="${consentId}">
	</form:form>
	<form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentReviewAndEConsentInfo.do" name="consentReviewFormId" id="consentReviewFormId" method="post" data-toggle="validator" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<input type="hidden" id="consentId" name="consentId" value="${consentId}">
		<div class="right-content-head">
			<div class="text-right">
				<div class="black-md-f dis-line pull-left line34">REVIEW AND E-CONSENT</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn" id="cancelId">Cancel</button>
				</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn" id="saveId">Save</button>
				</div>
				<div class="dis-line form-group mb-none">
					<button type="submit" class="btn btn-primary blue-btn">Done</button>
				</div>
			</div>
		</div>
		<!--  End  top tab section-->
		<!--  Start body tab section -->
		<div class="right-content-body">
			<div class="form-group">
				<div id="consentDocType">
					<span class="radio radio-info radio-inline p-45"> 
						<input class="" type="radio" id="inlineRadio1" value="Auto" name="consentDocType" required data-error="Please choose consent document type" checked> 
						<label for="inlineRadio1">Use auto-created Consent Document</label>
					</span> 
					<span class="radio radio-inline p-45"> 
						<input class="" type="radio" id="inlineRadio2" value="New" name="consentDocType" required data-error="Please choose consent document type"> 
						<label for="inlineRadio2">Create New Consent Document</label>
					</span>
				</div>
				<div class="help-block with-errors red-txt"></div>
			</div>
			<div class="col-md-12 p-none">
			<div class="mb-xs"><strong>CONSENT DOCUMENT</strong></div>
				<div id="autoCreateDivId" style="display:block;">
					<div class="form-group" style="border:1px solid #d3d3d3;padding:20px !important;border-radius:3px;">
						<div id="autoConsentDocumentDivId"></div>
					</div>
				</div>
				<div id="newDivId" style="display:none;">
					<div class="form-group">
			            <textarea class="" rows="8" id="newDocumentDivId" name="newDocumentDivId" required maxlength="1000"></textarea>
			            <div class="help-block with-errors red-txt"></div>
			         </div>
				</div>
			</div>
		</div>
		<!--  End body tab section -->
	</form:form>
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){  
    //active li
    $(".menuNav li").removeClass('active');
    $(".fifthConsentReview").addClass('active');
	$("#createStudyId").show();
    
	autoCreateConsentDocument();
	//check the consent type
	$("#consentDocType").on('change', function(){
		fancyToolbar();
		if($("#inlineRadio1").is(":checked")){
    		$("#autoCreateDivId").show();
	        $("#newDivId").hide();
	        autoCreateConsentDocument();
    	}else{
    		$("#newDivId").show();
    		$("#autoCreateDivId").hide();
    		createNewConsentDocument();
    	}
    });
	
	//go back to consentList page
	$("#cancelId,#saveId").on('click', function(){
		var id = this.id;
		if(id == "cancelId"){
			document.cancelConsentReviewFormId.submit();			
		}else if( id == "saveId"){
			saveConsentReviewAndEConsentInfo();
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
    	tinymce.activeEditor.setContent('');
    }
    
    //save review and E-consent data
    function saveConsentReviewAndEConsentInfo(){
    	var consentInfo = new Object();
    	var consentId = $("#consentId").val();
    	var studyId=$("#studyId").val();
    	var consentDocumentContent = "";
    	var consentDocumentType = $('input[name="consentDocType"]:checked').val();
    	if(consentDocumentType == "Auto"){
    		consentDocumentContent = $("#autoConsentDocumentDivId").html();
    	}else{
    		consentDocumentContent = tinymce.get('newDocumentDivId').getContent();
    	}
    	if(null != consentId){consentInfo.id = consentId;}
    	if(null != studyId){consentInfo.studyId = studyId;}
    	if(null != consentDocumentType){consentInfo.consentDocumentType = consentDocumentType;}
    	if(null != consentDocumentContent){consentInfo.htmlConsent = consentDocumentContent;}
    	
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
					alert("consentId : "+consentId+" studyId : "+studyId);
				}
	          },
	          error: function(xhr, status, error) {
				alert("error : "+error);
	          }
	   });
    }
});
</script>