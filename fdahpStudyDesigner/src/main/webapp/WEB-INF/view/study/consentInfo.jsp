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
   <input type="hidden" id="elaborated" name="elaborated" value="" />
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f dis-line pull-left line34"><span class="pr-sm cur-pointer" onclick="goToBackPage();"><img src="../images/icons/back-b.png"/></span><c:if test="${empty consentInfoBo.id}"> Add Consent</c:if><c:if test="${not empty consentInfoBo.id}">Edit Consent</c:if></div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="goToBackPage();">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn" onclick="saveConsentInfo(this);">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn" id="doneId">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div class="mb-xlg mt-md form-group">
         <span class="radio radio-info radio-inline p-45">
         <input type="radio" id="inlineRadio1" value="ResearchKit/ResearchStack" name="consentItemType" required data-error="Please choose type" ${empty consentInfoBo.consentItemType  || consentInfoBo.consentItemType=='ResearchKit/ResearchStack' ?'checked':''}>
         <label for="inlineRadio1">ResearchKit/ResearchStack</label>
         </span>
         <span class="radio radio-inline p-45">
         <input type="radio" id="inlineRadio2" value="Custom" name="consentItemType" required data-error="Please choose type" ${consentInfoBo.consentItemType=='Custom'?'checked':''}>
         <label for="inlineRadio2">Custom</label>
         </span> 
         <div class="help-block with-errors red-txt"></div>               
      </div>
      <div id="titleContainer">
         <div class="gray-xs-f mb-xs">Title</div>
         <div class="col-md-5 p-none mb-xlg form-group">
            <select class="selectpicker" id="title" name="title" required data-error="Please choose one title">
               <option value="">Select</option>
               <c:forEach items="${consentMasterInfoList}" var="consentMaster">
                <option value="${consentMaster.title}" ${consentInfoBo.title eq consentMaster.title  ? 'selected' : ''}>${consentMaster.title}</option>
               </c:forEach>
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
            <input type= "text" id="displayTitle" class="form-control" name="displayTitle" required value="${consentInfoBo.displayTitle}" maxlength="50">
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="mb-xlg">
         <div class="gray-xs-f mb-xs">Brief summary</div>
         <div class="form-group">
            <textarea class="form-control" rows="4" id="briefSummary" name="briefSummary" required maxlength="1000">${consentInfoBo.briefSummary}</textarea>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <div class="clearfix"></div>
      <div class="mb-xlg">
         <div class="gray-xs-f mb-xs">Elaborated version of content </div>
         <div class="form-group">
            <textarea class="" rows="8" id="elaboratedRTE" name="elaboratedRTE" required maxlength="1000">${consentInfoBo.elaborated}</textarea>
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
    $(".fifthConsent").addClass('active');
   /*  $("li.first").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>").nextUntil("li.fifth").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>"); */
	$("#createStudyId").show();
    if($("#elaboratedRTE").length > 0){
        tinymce.init({
            selector: "#elaboratedRTE",
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
            setup : function(ed) {
                ed.on('change', function(ed) {
              		  $('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form').validator('validate');
                });
       	  	}
        });
    }
    $('input[name="consentItemType"]').change(function(){
    	console.log(this.value);
    	if (this.value == 'Custom') {
    		$("#displayTitleId").show();
    		var consentInfoId = $("#id").val();
    		if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId != 'undefined'){
    			
    		}else{
    			$("#displayTitle").val('');
    			$("#briefSummary").val('');
    	    	$("#elaboratedRTE").val('');
    	    	$("#elaborated").val('');
    	    	$("#inlineRadio3").prop('checked', false);
    	    	$("#inlineRadio4").prop('checked', false);
    		}
    		$("#titleContainer").hide();
    		$("#title").prop('required',false);
    	}else{
			if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId != 'undefined'){
    		}else{
    			$("#displayTitle").val('');
    			$("#briefSummary").val('');
    	    	$("#elaboratedRTE").val('');
    	    	$("#elaborated").val('');
    	    	$("#inlineRadio3").prop('checked', false);
    	    	$("#inlineRadio4").prop('checked', false);
    		}
    		$("#title").prop('required',true);
    		$("#titleContainer").show();
    	}
    });
    $("#title").change(function(){
    	var titleText = $("#title").val();
    	if(titleText != null && titleText != '' && typeof titleText != 'undefined'){
    		$("#displayTitleId").show();
    		$("#displayTitle").val(titleText);
    	}
    });
    if('${consentInfoBo.consentItemType}' == 'Custom'){
    	$("#titleContainer").hide();
    	$("#title").prop('required',false);
    }else{
    	$("#titleContainer").show();
    	$("#title").prop('required',true);
    }
    if(typeof "${consentInfoList}" !='undefined'){
    	 var selectedTitle = document.getElementById('title');
    	 for(var i=0; i < selectedTitle.length; i++)
		 {
    		 <c:forEach items="${consentInfoList}" var="consentInfo">
	    		 if('${consentInfo.title}' == selectedTitle.options[i].value && '${consentInfo.title}' != '${consentInfoBo.title}'){
			       selectedTitle.options[i].disabled = true;
	    		 } 		    
    		 </c:forEach>
		 }
    }

    //submit the form
    $("#doneId").on('click', function(){
    	var elaboratedContent = tinymce.get('elaboratedRTE').getContent({ format: 'raw' });
    	$("#elaborated").val(elaboratedContent);
    	if(isFromValid("#basicInfoFormId")){
    		$("#basicInfoFormId").submit();
    	};
    });
});
function saveConsentInfo(item){
	var consentInfo = new Object();
	var consentInfoId = $("#id").val();
	var study_id=$("#studyId").val();
	var consentType = $('input[name="consentItemType"]:checked').val();
	var titleText = $("#title").val();
	var displayTitleText = $("#displayTitle").val();
	var briefSummaryText = $("#briefSummary").val();
	var elaboratedText = tinymce.get('elaboratedRTE').getContent({ format: 'raw' });
	console.log("elaboratedText:"+elaboratedText);
	var visual_step= $('input[name="visualStep"]:checked').val();
	if((study_id != null && study_id != '' && typeof study_id != 'undefined') && (displayTitleText != null && displayTitleText != '' && typeof displayTitleText != 'undefined')){
		$(item).prop('disabled', true);
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
		
		/* if(elaboratedText.length > 1000){
    		alert("Maximum character limit is 1000. Try again.");
    		return;
    	} */
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
					$("#alertMsg").removeClass('e-box').addClass('s-box').html("Consent saved successfully");
					$(item).prop('disabled', false);
					$('#alertMsg').show();
				}else{
					$("#alertMsg").removeClass('s-box').addClass('e-box').html("Something went Wrong");
					$('#alertMsg').show();
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
	}else{
		$(item).prop('disabled', false);
	}
}
function goToBackPage(){
	//window.history.back();
	var a = document.createElement('a');
	a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do";
	document.body.appendChild(a).click();
}
</script>