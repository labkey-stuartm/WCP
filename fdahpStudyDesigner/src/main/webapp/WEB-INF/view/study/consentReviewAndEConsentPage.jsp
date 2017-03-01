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
	</form:form>
	<form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentReviewAndEConsentInfo.do" name="consentReviewFormId" id="consentReviewFormId" method="post" data-toggle="validator" role="form">
		<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		<div class="right-content-head">
			<div class="text-right">
				<div class="black-md-f dis-line pull-left line34">REVIEW AND E-CONSENT</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn" id="cancelId">Cancel</button>
				</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn">Save</button>
				</div>
				<div class="dis-line form-group mb-none">
					<button type="submit" class="btn btn-primary blue-btn">Mark as Completed</button>
				</div>
			</div>
		</div>
		<!--  End  top tab section-->
		<!--  Start body tab section -->
			<div class="right-content-body">
			<div class="form-group">
				<div id="consentDocType">
					<span class="radio radio-info radio-inline p-45"> 
						<input class="" type="radio" id="inlineRadio3" value="Yes" name="shareDataPermission" required data-error="Please choose consent document type" checked> 
						<label for="inlineRadio3">Use auto-created Consent Document</label>
					</span> 
					<span class="radio radio-inline p-45"> 
						<input class="" type="radio" id="inlineRadio4" value="No" name="shareDataPermission" required data-error="Please choose consent document type"> 
						<label for="inlineRadio4">Create New Consent Document</label>
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
			            <textarea class="" rows="8" id="newDocumentDivId" name="newDocumentDivId" required></textarea>
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
		if($("#inlineRadio3").is(":checked")){
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
	$("#cancelId").on('click', function(){
		document.cancelConsentReviewFormId.submit();
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
});
</script>