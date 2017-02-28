<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentInfo.do" name="basicInfoFormId" id="basicInfoFormId" method="post" data-toggle="validator" role="form">
   <input type="hidden" id="id" name="id" value="${consentInfoBo.id}">
   <c:if test="${not empty consentInfoBo.id}"><input type="hidden" id="studyId" name="studyId" value="${consentInfoBo.studyId}"></c:if>
   <c:if test="${empty consentInfoBo.id}"><input type="hidden" id="studyId" name="studyId" value="${studyId}"></c:if>
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f dis-line pull-left line34"><span class="pr-sm cur-pointer" onclick="goToBackPage();"><img src="../images/icons/back-b.png"/></span> Add Consent</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage();">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="saveConsentInfo();">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="submit" class="btn btn-primary blue-btn">Mark as Completed</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div class="mb-xlg mt-md form-group">
         <span class="radio radio-info radio-inline p-45">
         <input type="radio" id="inlineRadio1" value="ResearchKit" name="consentItemType" required data-error="Please choose type" ${consentInfoBo.consentItemType=='ResearchKit'?'checked':''}>
         <label for="inlineRadio1">ResearchKit</label>
         </span>
         <span class="radio radio-inline p-45">
         <input type="radio" id="inlineRadio2" value="Custom" name="consentItemType" required data-error="Please choose type" ${consentInfoBo.consentItemType=='Custom'?'checked':''}>
         <label for="inlineRadio2">Custom</label>
         </span> 
         <div class="help-block with-errors red-txt"></div>               
      </div>
      <div>
         <div class="gray-xs-f mb-xs">Title</div>
         <div class="col-md-5 p-none mb-xlg form-group">
            <select class="selectpicker" id="title" name="title" required data-error="Please choose one title">
               <option value="">Select</option>
               <option value="Medication Survey 1" ${consentInfoBo.title eq 'Medication Survey 1'  ? 'selected' : ''}>Medication Survey 1</option>
               <option value="Medication Survey 2" ${consentInfoBo.title eq 'Medication Survey 2'  ? 'selected' : ''}>Medication Survey 2</option>
               <option value="Medication Survey 3" ${consentInfoBo.title eq 'Medication Survey 3'  ? 'selected' : ''}>Medication Survey 3</option>
            </select>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="clearfix"></div>
      <!-- <div class="mb-lg">
         <div class="gray-xs-f mb-xs">Content Type</div>
         <div class="col-md-5 p-none mb-xlg form-group">
            <select class="form-control selectpicker" id="contentType" required data-error="Please choose one content type">
               <option value="">Select</option>
               <option>Medication Survey 1</option>
               <option>A Study for Pregnant Women</option>
               <option>Medication Survey 2</option>
            </select>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="clearfix"></div> -->
      <div class="mb-xlg" id="displayTitleId">
         <div class="gray-xs-f mb-xs">Display Title</div>
         <div class="form-group">
            <input type= "text" id="displayTitle" class="form-control" name="displayTitle" required value="${consentInfoBo.displayTitle}">
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="mb-xlg">
         <div class="gray-xs-f mb-xs">Brief summary</div>
         <div class="form-group">
            <textarea class="form-control" rows="4" id="briefSummary" name="briefSummary" required>${consentInfoBo.briefSummary}</textarea>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="clearfix"></div>
      <div class="mb-xlg">
         <div class="gray-xs-f mb-xs">Elaborated version of content </div>
         <div class="form-group">
            <textarea class="" rows="8" id="elaborated" name="elaborated" required>${consentInfoBo.elaborated}</textarea>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="clearfix"></div>
      <div>
         <div class="gray-xs-f mb-xs">Show as a visual step in the Consent Info section? Yes / No</div>
         <div class="form-group">
            <span class="radio radio-info radio-inline p-45">
            <input class="" type="radio" id="inlineRadio3" value="Yes" name="visualStep" required data-error="Please choose one visual step" ${consentInfoBo.visualStep=='Yes'?'checked':''}>
            <label for="inlineRadio3">Yes</label>
            </span>
            <span class="radio radio-inline p-45">
            <input class="" type="radio" id="inlineRadio4" value="No" name="visualStep" required data-error="Please choose one visual step" ${consentInfoBo.visualStep=='No'?'checked':''}>
            <label for="inlineRadio4">No</label>
            </span>   
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
   </div>
   </form:form>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function(){  
    // Fancy Scroll Bar
    if('${consentInfoBo.id}' == ''){
    	 $("#displayTitleId").hide();
    }
    $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
    $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".fifth").addClass('active');
   /*  $("li.first").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>").nextUntil("li.fifth").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>"); */
	$("#createStudyId").show();
    if($("#elaborated").length > 0){
        tinymce.init({
            selector: "#elaborated",
            theme: "modern",
            skin: "lightgray",
            height:180,
            plugins: [
                "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
                "save contextmenu directionality paste"
            ],
            toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link image | hr removeformat | cut undo redo",
            menubar: false,
            toolbar_items_size: 'small',
            content_style: "div, p { font-size: 13px;letter-spacing: 1px;}"
        });
    }
    $('input[name="consentItemType"]').change(function(){
    	console.log(this.value);
    	if (this.value == 'Custom') {
    		$("#displayTitleId").show();
    		$("#displayTitle").val('');
    		$("#title").val('');
    		$("#title").prop('required',false);
    		$("#title").prop('disabled', true);
    	}else{
    		$("#title").prop('disabled', false);
    		$("#title").prop('required',true);
    	}
    });
    $("#title").change(function(){
    	var titleText = $("#title").val();
    	if(titleText != null && titleText != '' && typeof titleText != 'undefined'){
    		$("#displayTitleId").show();
    		$("#displayTitle").val(titleText);
    	}
    });
});
function saveConsentInfo(){
	var consentInfo = new Object();
	var consentInfoId = $("#id").val();
	var study_id=$("#studyId").val();
	var consentType = $('input[name="consentItemType"]:checked').val();
	console.log("consentType:"+consentType);
	var titleText = $("#title").val();
	var displayTitleText = $("#displayTitle").val();
	var briefSummaryText = $("#briefSummary").val();
	var elaboratedText = $("#elaborated").val();
	var visual_step= $('input[name="visualStep"]:checked').val();;
	if(study_id != null && study_id != '' && typeof study_id != 'undefined'){
		if(null != consentInfoId){
			consentInfo.id=consentInfoId;
		}
		consentInfo.studyId=study_id;
		if(null !=  consentType){
			consentInfo.consentItemType=consentType;
		}
		if(null != titleText){
			consentInfo.title=titleText;
		}
		if(null != briefSummaryText){
			consentInfo.briefSummary=briefSummaryText;
		}
		if(null != elaboratedText){
			consentInfo.elaborated=elaboratedText;
		}
		if(null != visual_step){
			consentInfo.visualStep=visual_step;
		}
		if(null != displayTitleText){
			consentInfo.displayTitle = displayTitleText;
		}
		var data = JSON.stringify(consentInfo);
		$.ajax({ 
	          url: "/fdahpStudyDesigner/adminStudies/saveConsentInfo.do",
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
					var consentInfoId = jsonobject.consentInfoId;
					$("#id").val(consentInfoId);
				}
	          },
	   }); 
	}
}
function goToBackPage(){
	window.history.back();
}
</script>