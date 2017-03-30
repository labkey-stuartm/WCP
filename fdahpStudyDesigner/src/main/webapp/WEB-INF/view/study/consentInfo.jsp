<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
 <div class="col-sm-10 col-rc white-bg p-none">
	<!--  Start top tab section-->
	<form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentInfo.do" name="consentInfoFormId" id="consentInfoFormId" method="post" data-toggle="validator" role="form">
		<input type="hidden" id="id" name="id" value="${consentInfoBo.id}">
		<c:if test="${not empty consentInfoBo.id}">
			<input type="hidden" id="studyId" name="studyId" value="${consentInfoBo.studyId}">
		</c:if>
		<c:if test="${empty consentInfoBo.id}">
			<input type="hidden" id="studyId" name="studyId" value="${studyId}">
		</c:if>
		<input type="hidden" id="elaborated" name="elaborated" value="" />
		<input type="hidden" id="type" name="type" value="complete" />
		<div class="right-content-head" style="z-index: 999;">
			<div class="text-right">
				<div class="black-md-f dis-line pull-left line34">
					<span class="pr-sm cur-pointer" onclick="goToBackPage();">
						<img src="../images/icons/back-b.png" /></span>
					<c:if test="${empty consentInfoBo.id}"> Add Consent</c:if>
					<c:if test="${not empty consentInfoBo.id && actionPage eq 'addEdit'}">Edit Consent</c:if>
					<c:if test="${not empty consentInfoBo.id && actionPage eq 'view'}">View Consent</c:if>
				</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn" onclick="goToBackPage();">Cancel</button>
				</div>
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn ConsentButtonHide" onclick="saveConsentInfo(this);">Save</button>
				</div>
				<div class="dis-line form-group mb-none">
					<button type="button" class="btn btn-primary blue-btn ConsentButtonHide" id="doneId">Done</button>
				</div>
			</div>
		</div>
		<!--  End  top tab section-->
		<!--  Start body tab section -->
		<div class="right-content-body">
			<div class="mb-xlg mt-md form-group">
				<span class="radio radio-info radio-inline p-45"> 
					<input type="radio" id="inlineRadio1" value="ResearchKit/ResearchStack" name="consentItemType" required data-error="Please choose type"
					 ${empty consentInfoBo.consentItemType  || consentInfoBo.consentItemType=='ResearchKit/ResearchStack' ?'checked':''}>
					<label for="inlineRadio1">ResearchKit/ResearchStack</label>
				</span> 
				<span class="radio radio-inline p-45"> 
					<input type="radio" id="inlineRadio2" value="Custom" name="consentItemType" required data-error="Please choose type" ${consentInfoBo.consentItemType=='Custom'?'checked':''}> 
					<label for="inlineRadio2">Custom</label>
				</span>
				<div class="help-block with-errors red-txt"></div>
			</div>
			<div id="titleContainer">
				<div class="gray-xs-f mb-xs">Title <span class="requiredStar">*</span></div>
				<div class="col-md-5 p-none mb-xlg form-group elaborateClass">
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
			<div class="mb-xlg" id="displayTitleId">
				<div class="gray-xs-f mb-xs">Display Title  <small>(50 characters max)</small><span class="requiredStar">*</span></div>
				<div class="form-group">
					<input type="text" id="displayTitle" class="form-control" name="displayTitle" required value="${consentInfoBo.displayTitle}" maxlength="50">
					<div class="help-block with-errors red-txt"></div>
				</div>
			</div>
			<div class="mb-xlg">
				<div class="gray-xs-f mb-xs">Brief summary <span class="requiredStar">*</span></div>
				<div class="form-group">
					<textarea class="form-control" rows="4" id="briefSummary" name="briefSummary" required>${consentInfoBo.briefSummary}</textarea>
					<div class="help-block with-errors red-txt"></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="mb-xlg">
				<div class="gray-xs-f mb-xs">Elaborated version of content <span class="requiredStar">*</span></div>
				<div class="form-group elaborateClass">
					<textarea class="" rows="8" id="elaboratedRTE" name="elaboratedRTE" required>${consentInfoBo.elaborated}</textarea>
					<div class="help-block with-errors red-txt"></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div>
				<div class="gray-xs-f mb-xs">Show as a visual step in the Consent Info section? Yes / No <span class="requiredStar">*</span></div>
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
    
    <c:if test="${actionPage eq 'view'}">
	    $('#consentInfoFormId input,textarea').prop('disabled', true);
	    $('#consentInfoFormId .elaborateClass').addClass('linkDis');
	    $('.ConsentButtonHide').hide();
    </c:if>
    
    if('${consentInfoBo.id}' == ''){
    	 $("#displayTitleId").hide();
    }
   // $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
   // $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
    $(".menuNav li").removeClass('active');
    $(".fifthConsent").addClass('active');
   /*  $("li.first").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>").nextUntil("li.fifth").append("<span class='sprites-icons-2 tick pull-right mt-xs'></span>"); */
	$("#createStudyId").show();
   
    //load the list of titles when the page loads
	consentInfoDetails();
	initTinyMCEEditor();
    
    //get the selected consent type on change
    $('input[name="consentItemType"]').change(function(){
    	resetValidation($("#consentInfoFormId"));
    	//resetTitle();
    	if (this.value == 'Custom') {
    		$("#displayTitleId").show();
    		$("#titleContainer").hide();
    		$("#title").prop('required', false);
    	}else{
    		/* setTimeout(function(){ consentInfoDetails(); }, 1000); */
    		consentInfoDetails();
    		$("#title").prop('required', true);
    		$("#titleContainer").show();
    	}
    	addDefaultData();
    });
   
    $("#title").change(function(){
    	var titleText = $("#title").val();
    	resetValidation($("#consentInfoFormId"));
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
    

    //submit the form
    $("#doneId").on('click', function(){
    	var elaboratedContent = tinymce.get('elaboratedRTE').getContent({ format: 'raw' });
    	elaboratedContent = replaceSpecialCharacters(elaboratedContent);
    	var briefSummaryText = replaceSpecialCharacters($("#briefSummary").val());
    	$("#elaborated").val(elaboratedContent);
    	$("#briefSummary").val(briefSummaryText);
    	$("#doneId").prop('disabled', true);
    	tinyMCE.triggerSave();
    	if(isFromValid("#consentInfoFormId")){
    		var retainTxt = '${studyBo.retainParticipant}';
    		if(retainTxt != null && retainTxt != '' && typeof retainTxt != 'undefined' && retainTxt == 'Yes'){
    			bootbox.alert({
    				closeButton: false,
    				message : "You have a setting that allows study data to be retained /deleted even if the user withdraws from the Study. Please ensure you have worded Consent Terms in accordance with this.",
					callback: function(){
						$("#consentInfoFormId").submit();
					}
        		});
    		}else{
    			$("#consentInfoFormId").submit();
    		}
    	}else{
    		$("#doneId").prop('disabled', false);
    	}
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
	briefSummaryText = replaceSpecialCharacters(briefSummaryText);
	var elaboratedText = tinymce.get('elaboratedRTE').getContent({ format: 'raw' });
	elaboratedText = replaceSpecialCharacters(elaboratedText);
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
		consentInfo.type="save";
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
    				$("#alertMsg").removeClass('e-box').addClass('s-box').html("Content saved as draft.");
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

//remove the default vallues from the fields when the consent type is changed
function addDefaultData(){
	var consentInfoId = $("#id").val();
	$("#displayTitle").val('');
	$("#briefSummary").val('');
	$("#elaboratedRTE").val('');
	$("#elaborated").val('');
	tinymce.get('elaboratedRTE').setContent('');
	$("#inlineRadio3").prop('checked', false);
	$("#inlineRadio4").prop('checked', false);
	if(consentInfoId != null && consentInfoId != '' && typeof consentInfoId != 'undefined'){
		var consentType = "${consentInfoBo.consentItemType}";
		var actualValue = $("input[name='consentItemType']:checked").val();
		if( consentType == actualValue){
			tinymce.get('elaboratedRTE').setContent('${consentInfoBo.elaborated}');
			$("#displayTitle").val("${consentInfoBo.displayTitle}");
    		$("#briefSummary").val("${consentInfoBo.briefSummary}");
    		var visualStep = "${consentInfoBo.visualStep}";
    		if( visualStep == "Yes"){
    			$("#inlineRadio3").prop('checked', true);
    		}else if(visualStep == "No"){
    			$("#inlineRadio4").prop('checked', true);
    		}
    		initTinyMCEEditor();
		}
	}
}

function consentInfoDetails(){
	if(typeof "${consentInfoList}" !='undefined'){
   	 var selectedTitle = document.getElementById('title');
   	 var actualOption = "${consentInfoBo.title}";
   	 for(var i=0; i < selectedTitle.length; i++){
   		 if( actualOption == selectedTitle.options[i].value){
   			 $('#title :nth-child('+(i+1)+')').prop('selected', true).trigger('change');
   		 }
   		
   		 <c:forEach items="${consentInfoList}" var="consentInfo">
	    		 if('${consentInfo.title}' == selectedTitle.options[i].value && '${consentInfo.title}' != '${consentInfoBo.title}'){
			     	selectedTitle.options[i].disabled = true;
	    		 }
   		 </c:forEach>
		}
   }
}

//initialize the tinymce editor
function initTinyMCEEditor(){
	/* if($("#elaboratedRTE").length > 0){ */
    //$("#elaboratedRTE").val('');
    //$("#elaboratedRTE").val('${consentInfoBo.elaborated}');
	tinymce.init({
         selector: "#elaboratedRTE",
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
         setup : function(ed) {
             ed.on('change', function(ed) {
           		  //$('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form').validator('validate');
           		  if(tinyMCE.get(ed.target.id).getContent() != ''){
           			$('#elaboratedRTE').parent().removeClass("has-danger").removeClass("has-error");
           	        $('#elaboratedRTE').parent().find(".help-block").html("");
           		  }
             });
    	  	} 
     });
	 //alert('${consentInfoBo.elaborated}');
     /* tinymce.get('elaboratedRTE').setContent('');
     setTimeout(function(){ tinymce.get('elaboratedRTE').setContent('${consentInfoBo.elaborated}'); }, 1000); */
   /*  } */
}
</script>