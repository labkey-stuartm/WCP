<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="col-sm-10 col-rc white-bg p-none">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateResource.do?${_csrf.parameterName}=${_csrf.token}&_S=${param._S}" data-toggle="validator" id="resourceForm" role="form" method="post" autocomplete="off" enctype="multipart/form-data">    
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f dis-line pull-left line34"><span class="pr-sm"><a href="javascript:void(0)" class="goToResourceListForm" id="goToResourceListForm"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span>
                    <c:if test="${isstudyProtocol ne 'isstudyProtocol'}">
                    <c:if test="${actionOn eq 'add'}">Add Resource</c:if>
                    <c:if test="${actionOn eq 'edit'}">Edit Resource</c:if>
                    <c:if test="${not empty resourceBO && actionOn eq 'view'}">View Resource <c:set var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive]?'<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}</c:if>
                    </c:if>
                    <c:if test="${isstudyProtocol eq 'isstudyProtocol'}">
                    <c:if test="${actionOn eq 'add'}">Add Study Protocol</c:if>
                    <c:if test="${actionOn eq 'edit'}">Edit Study Protocol</c:if>
                    </c:if></div>
                     
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn goToResourceListForm" id="goToStudyListPage">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn viewAct" id="saveResourceId">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn viewAct" id="doneResourceId">Done</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <input type="hidden" name="id" value="${resourceBO.id}"/>
            <input type="hidden" id="buttonText" name="buttonText">
            <input type="hidden" id="actionOn" name="actionOn">
            <c:if test="${isstudyProtocol eq 'isstudyProtocol'}">
            	<input type="hidden" name="isstudyProtocol" value="isstudyProtocol"/>
            </c:if>
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
             <div class="mt-none">
                <!-- form- input-->
                <div>
                   <div class="gray-xs-f mb-xs">Title <c:if test="${isstudyProtocol ne 'isstudyProtocol'}">&nbsp;<small class="viewAct">(50 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                   <div class="form-group">
                        <input autofocus="autofocus" type="text" class="form-control" id="resourceTitle" name="title" value="${fn:escapeXml(resourceBO.title)}" maxlength="50" required  <c:if test="${isstudyProtocol eq 'isstudyProtocol'}">readonly</c:if>/>
                   		<div class="help-block with-errors red-txt"></div>
                   </div>
                </div>
             </div>
                
            <div class="clearfix"></div>
                
            <div>
            <div class="gray-xs-f mb-xs">Content Type<span class="requiredStar"> *</span></div>
                 <span class="radio radio-info radio-inline p-45">
                    <input type="radio" class="addResource" id="inlineRadio1"  name="textOrPdfParam" value="0" <c:if test="${not resourceBO.textOrPdf}">checked</c:if>>
                    <label for="inlineRadio1">Rich Text editor</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio2" class="addResource"  name="textOrPdfParam" value="1" <c:if test="${resourceBO.textOrPdf}">checked</c:if>>
                    <label for="inlineRadio2">Upload PDF</label>
                </span>  
                <!-- <div class="help-block with-errors red-txt"></div>   -->
            </div>
                
            <div class="clearfix"></div>
             
            <div id="richEditor" class="mt-lg form-group resetContentType <c:if test="${resourceBO.textOrPdf}">dis-none</c:if>">
              <textarea class="remReqOnSave" id="richText" name="richText" required>${resourceBO.richText}</textarea>
               <div class="help-block with-errors red-txt"></div>      
            </div>
            
            
            <div id="pdf_file" class="mt-lg form-group resetContentType <c:if test="${empty resourceBO || not resourceBO.textOrPdf}">dis-none</c:if>">
                <button id="uploadPdf" type="button" class="btn btn-default gray-btn uploadPdf viewAct">Upload PDF</button>
                <input id="uploadImg" class="dis-none remReqOnSave" type="file" name="pdfFile" accept=".pdf" data-error="Please select a pdf file" required>
                <input type="hidden" class="remReqOnSave" value="${resourceBO.pdfUrl}" required id="pdfUrl" name="pdfUrl">
                <input type="hidden" value="${resourceBO.pdfName}" id="pdfName" name="pdfName">
               <%--  <a href="/fdahpStudyDesigner/studyResources/${resourceBO.pdfUrl}"><span id="pdf_name" class="ml-sm" style="color: black">${resourceBO.pdfName}</span></a> --%>
<!--                 <span id="delete" class="sprites_icon delete vertical-align-middle ml-sm dis-none"></span> -->
			<!-- <span id="delete" class="blue-link dis-none viewAct">&nbsp;X<a href="javascript:void(0)" class="blue-link txt-decoration-underline pl-xs">Remove PDF</a></span> -->
             <span class="alert customalert pdfDiv">
               <%--  <a href="/fdahpStudyDesigner/studyResources/${resourceBO.pdfUrl}" id="pdfClk"> --%>
                <a id="pdfClk" target="_blank" href="<spring:eval expression="@propertyConfigurer.getProperty('fda.imgDisplaydPath')" />studyResources/${resourceBO.pdfUrl}">
	                <img src="/fdahpStudyDesigner/images/icons/pdf.png"/>
	                <span id="pdf_name" class="ml-sm dis-ellipsis" title="${resourceBO.pdfName}">${resourceBO.pdfName}</span>
                </a>
				<span id="delete" class="blue-link dis-none viewAct borr">&nbsp;X<a href="javascript:void(0)" class="blue-link pl-xs mr-sm">Remove PDF</a></span>
			</span>
            <div class="help-block with-errors red-txt"></div>  
            </div>
             
            <c:if test="${isstudyProtocol ne 'isstudyProtocol'}">   
            <div class="clearfix"></div>
                
            <div class="mt-xs">
                <div class="gray-xs-f mb-sm">Set a Period of Visibility for this resource? <span class="requiredStar">*</span></div>
                 <span class="radio radio-info radio-inline p-45">
                    <input type="radio" id="inlineRadio3" name="resourceVisibilityParam" value="0" <c:if test="${not resourceBO.resourceVisibility}">checked</c:if>>
                    <label for="inlineRadio3">Yes</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio4" name="resourceVisibilityParam" value="1" <c:if test="${resourceBO.resourceVisibility  || empty resourceBO}">checked</c:if>>
                    <label for="inlineRadio4">No</label>
                </span>    
                <div class="help-block with-errors red-txt"></div>
            </div>
                
            <div class="clearfix"></div>
               
             <div class="mt-lg resetDate">
                <div class="gray-xs-f mb-xs">Select Time Period <span class="requiredStar">*</span></div>
                <div>
                 <span class="radio radio-info radio-inline pr-md">
                    <input type="radio" id="inlineRadio5" class="disRadBtn1" value="1" name="resourceTypeParm">
                    <label for="inlineRadio5">Anchor Date +</label><br/>
                    <!-- <span>&nbsp;</span> -->
                </span>
                <!--  selectpicker -->
                 <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                 	 <select class="signDropDown" title="Select" name="xDaysSign" id="xSign" style="display: none;">
                              <option value="0" ${not resourceBO.xDaysSign ?'selected':''}>+</option>
                              <option value="1" ${resourceBO.xDaysSign ?'selected':''}>-</option>
                     </select>
                     <input id="xdays" type="text" class="form-control wid70 disRadBtn1 disBtn1 remReqOnSave daysMask mt-md resetAncDate" 
                     placeholder="x days" name="timePeriodFromDays" value="${resourceBO.timePeriodFromDays}" oldxDaysVal="${resourceBO.timePeriodFromDays}" 
                     maxlength="3" required pattern="[0-9]+" data-pattern-error="Please enter valid number."/>
                 	 <span class="help-block with-errors red-txt"></span>
                 </span>
                 <span class="mb-sm pr-md">
                    <span class="light-txt opacity06">to  Anchor Date + </span>                   
                    <!-- <span>&nbsp;</span> -->
                 </span>
                  <span class="form-group m-none dis-inline vertical-align-middle">
                     <select class="signDropDown" title="Select" name="yDaysSign" id="ySign" style="display: none;">
                              <option value="0" ${not resourceBO.yDaysSign ?'selected':''}>+</option>
                              <option value="1" ${resourceBO.yDaysSign ?'selected':''}>-</option>
                     </select>
                     <input id="ydays" type="text" class="form-control wid70 disRadBtn1 disBtn1 remReqOnSave daysMask mt-md resetAncDate" placeholder="y days" name="timePeriodToDays" value="${resourceBO.timePeriodToDays}" oldyDaysVal="${resourceBO.timePeriodToDays}" maxlength="3" required />
                 	 <span class="help-block with-errors red-txt"></span>
                 </span> 
                 </div>
             </div>
                
             <div class="mt-lg resetDate">
                 <div class="mb-none">
                     <span class="radio radio-info radio-inline pr-md">
                        <input type="radio" class="disRadBtn1" id="inlineRadio6" value="0" name="resourceTypeParm">
                        <label for="inlineRadio6">Custom</label>
                    </span>
                </div>
                 <div>
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                         <input id="StartDate" type="text" class="form-control disRadBtn1 disBtn2 datepicker remReqOnSave mt-md" placeholder="Start Date" name="startDate" value="${resourceBO.startDate}" oldStartDateVal="${resourceBO.startDate}" required/>
                         <span class="help-block with-errors red-txt"></span>
                     </span>
                     <span class="gray-xs-f mb-sm pr-md">
                        to 
                     </span>
                      <span class="form-group m-none dis-inline vertical-align-middle">
                         <input id="EndDate" type="text" class="form-control disRadBtn1 disBtn2 datepicker remReqOnSave mt-md" placeholder="End Date" name="endDate" value="${resourceBO.endDate}" oldEndDateVal="${resourceBO.endDate}" required/>
                    	 <span class="help-block with-errors red-txt"></span>
                     </span>
                     <div class="help-block with-errors red-txt"></div>
                 </div>
             </div>
            
              <div class="clearfix"></div>
                
             <div class="mt-sm">
                <div class="gray-xs-f mb-xs">Text for notifying participants about the new resource being available&nbsp;<small class="viewAct">(250 characters max)</small> <span class="requiredStar">*</span></div>
                 
                 <div class="form-group">
                  <textarea class="form-control remReqOnSave" rows="4" id="comment" name="resourceText" data-error="Please enter plain text of up to 250 characters max." maxlength="250" required>${resourceBO.resourceText}</textarea>
                  <div class="help-block with-errors red-txt"></div>
                 </div>
             </div>
            </c:if>
            
                
            </div>
            <!--  End body tab section -->
        </form:form>   
        </div>
        <!-- End right Content here -->

<form:form action="/fdahpStudyDesigner/adminStudies/getResourceList.do?_S=${param._S}" name="resourceListForm" id="resourceListForm" method="post">
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	<c:if test="${isstudyProtocol eq 'isstudyProtocol' && empty resourceBO.title}">
		$('#resourceTitle').val('Study Protocol');
	</c:if>
	
	/* $('#uploadImg').change(
            function () {
                var fileExtension = ['pdf'];
                if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                    $("#uploadImg").parent().find(".help-block").html('<ul class="list-unstyled"><li>Please select only pdf file</li></ul>');
                    }
	}); */
	
	    $('.daysMask').mask('000');
	
	   // $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
	  //  $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
	    $(".menuNav li").removeClass('active');
	    $(".eighthResources").addClass('active'); 
		$("#createStudyId").show();
        
	 $("#doneResourceId").on('click', function(){
		 $('#doneResourceId').prop('disabled',true);
		// alert($('#richText').text());
          if( chkDaysValid(true) && isFromValid('#resourceForm')){
       	   	$('#buttonText').val('done');
 		   		$('#resourceForm').submit();
 		   }else{
 			  $('#doneResourceId').prop('disabled',false);
 		   }
	    });
	 
	 if($('#inlineRadio1').prop('checked')){
		 $('#uploadImg').removeAttr('required');
		 $('#pdfUrl').removeAttr('required');
		 $('#richText').attr('required','required');
		 resetValidation('#resourceForm');
	 }else{
		 $('#richText').removeAttr('required');
		  var file = $('#uploadImg').val();
          var pdfId = $('#pdfUrl').val();
          $('#richText').removeAttr('required');
		  if(pdfId){
			  $('#pdfUrl').attr('required','required');
			  $('#uploadImg').removeAttr('required');
		  }else{
			  $('#uploadImg').attr('required','required');
			  $('#pdfUrl').removeAttr('required');
		  }
		  resetValidation('#resourceForm');
	 }
	 
	 $('#inlineRadio1','#inlineRadio2').on('change',function(){
		 if($('#inlineRadio1').prop('checked')){
			 $('#uploadImg').removeAttr('required');
			 $('#pdfUrl').removeAttr('required');
		 }else{
			 $('#richText').removeAttr('required');
			  var file = $('#uploadImg').val();
	          var pdfId = $('#pdfUrl').val();
	          /* if(file || pdfId){
	        	  $('#uploadImg').removeAttr('required');
	          } */
	          $('#richText').removeAttr('required');
			  if(pdfId){
				  $('#pdfUrl').attr('required','required');
			  }else{
				  $('#uploadImg').attr('required','required');
			  }
		 }
		  resetValidation('#resourceForm');
	 });
	  
	$('#saveResourceId').click(function() {
		 $('#saveResourceId').prop('disabled',true);
			/* $('.remReqOnSave').removeAttr('required'); */
		   	$("#resourceTitle").parent().find(".help-block").empty();
	   		$('#resourceForm').validator('destroy').validator();
	   		var isValid = true;
	   if($('#inlineRadio5').prop('checked') && ($('#xdays').val() || $('#ydays').val())) {
		   isValid = chkDaysValid(false);
	   }
       if(!$('#resourceTitle')[0].checkValidity()){
    	  /*  $('.remReqOnSave').attr('required',true); */
    	if($("#resourceTitle").parent().addClass('has-error has-danger').find(".help-block").text() == ''){
    		$("#resourceTitle").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
    	}
       	$('#saveResourceId').prop('disabled',false);
    	  return false;
       }else if(isValid){
    	   	var actionOn = '${actionOn}';
	       	$('#resourceForm').validator('destroy');
	       	$("#actionOn").val(actionOn);
	       	$("#buttonText").val('save');
	       	$('#resourceForm').submit();
       }
      $('#saveResourceId').prop('disabled',false);
	});
	
	 /* var filename = $('input[type=file]').val().replace(/C:\\fakepath\\/i, ''); */
	 pdfUrlName = $('#pdfUrl').val();
     if(pdfUrlName != ""){
       $("#uploadPdf").text("Change PDF");
       $("#delete").removeClass("dis-none");
     }else{
    	 $('.pdfDiv').hide();
     }
     
     
 	$('.goToResourceListForm').on('click',function(){
 		//$('#goToResourceListForm').addClass('cursor-none');
        <c:if test="${actionOn ne 'view'}">
 		//$('#goToStudyListPage').prop('disabled',true);
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
		        	$('#resourceListForm').submit();
		        }
		    }
	    });
 		</c:if>
 		<c:if test="${actionOn eq 'view'}">
 			$('#resourceListForm').submit();
 		</c:if>
	});
	
	/* $('#goToStudyListPage').on('click',function(){
		$('#studyListForm').submit();
	}); */
	
	 // File Upload    
    $(".uploadPdf,.changePdf").click(function(){               
       $("#uploadImg").click();
    });
	 
  //wysiwyg richText
    if($("#richText").length > 0){
    tinymce.init({
        selector: "#richText",
        theme: "modern",
        skin: "lightgray",
        height:150,
        plugins: [
            "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
            "save contextmenu directionality paste"
        ],
        toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link | hr removeformat | cut undo redo | fontsizeselect fontselect",
        menubar: false,
        toolbar_items_size: 'small',
        content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
        setup : function(ed) {
            ed.on('keypress change', function(ed) {
            	resetValidation('.resetContentType');
            	resetValidation($('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form #richText'));
            });
     	  },
     	 <c:if test="${actionOn eq 'view'}">readonly:1</c:if>
    });
	}
  
    //Toggling Rich richText and Upload Button    
    $(".addResource").click(function(){
        var a = $(this).val();
        if(a == '0'){
           /*  $("#richEditor").show(); */
            $("#richEditor").removeClass("dis-none");
            $("#pdf_file").addClass("dis-none");
            $('#richText').attr('required','required');
  		  	$('#uploadImg').removeAttr('required');
  		  	$('#pdfUrl').removeAttr('required');
        }else if(a == '1'){
           /*  $("#richEditor").hide(); */
            $("#richEditor").addClass("dis-none");
            $("#pdf_file").removeClass("dis-none");
            $('#richText').removeAttr('required');
            $('#uploadImg').attr('required','required');
            $('#pdfUrl').attr('required','required');
        }
        resetValidation($(this).parents('form'));
    });
    
    
  //Changing & Displaying upload button text & file name
  
    $('#uploadImg').on('change',function (){
    
    	var fileExtension = ['pdf'];
		var file, reader;
		var thisAttr = this;
		var thisId = $(this).attr("data-imageId");
		if ((file = this.files[0])) {
			reader = new FileReader();
			reader.onload = function() {
		        if ($.inArray($(thisAttr).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
		        	$("#uploadImg").parent().addClass('has-error has-danger').find(".help-block").html('<ul class="list-unstyled"><li>Please select a pdf file</li></ul>');
		        	$("#delete").click();
		        }else if($('input[type=file]').val()){
		        	$('#pdfClk').attr('href','javascript:void(0)').css('cursor', 'default');
		        	$('.pdfDiv').show();
			        var filename = $(thisAttr).val().replace(/C:\\fakepath\\/i, '').split(/\\/i)[$(thisAttr).val().replace(/C:\\fakepath\\/i, '').split(/\\/i).length - 1];
			        $("#pdf_name").prop('title',filename).text(filename);
			        
			        var a = $("#uploadPdf").text();
			        if(a == "Upload PDF"){
			          $("#uploadPdf").text("Change PDF");
			        }
		       		$("#delete").removeClass("dis-none");
		       		$("#uploadImg").parent().removeClass('has-error has-danger').find(".help-block").html('');
		    	}
    		};
    		reader.onerror = function() {
    			$("#uploadImg").parent().addClass('has-error has-danger').find(".help-block").html('<ul class="list-unstyled"><li>Please select a pdf file</li></ul>');
		        $("#delete").click();
    		}
    		reader.readAsDataURL(file)
    	}
        resetValidation($("#uploadImg").parents('form'));
   });
  
   /*  $('#uploadImg').change(
            function () {
                var fileExtension = ['pdf'];
                if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                    alert("Only '.pdf' formats are allowed.");
                    return false; 
                    }
	}); */
  
  //Deleting Uploaded pdf
    $("#delete").click(function(){
       $("#uploadPdf").text("Upload PDF");
       $("#pdf_name").prop('title','').text(""); 
       $(this).addClass("dis-none");
       $('input[type=file]').val('');
       $('#pdfUrl').val('');
       $('#pdfName').val('');
       $("#uploadImg").attr('required','required');
       $('.pdfDiv').hide();
       resetValidation($("#uploadImg").parents('form'));
    });
	
	<c:if test="${isstudyProtocol ne 'isstudyProtocol'}">
	<c:if test="${not empty resourceBO.timePeriodFromDays || not empty resourceBO.timePeriodToDays}">
		//$('.signDropDown').show();
		$('.disBtn1').attr('required','required');
		$('.disBtn2').removeAttr('required');
		$('.disBtn2').prop('disabled',true);
		$('#inlineRadio5').prop('checked',true);
		$('#inlineRadio6').prop('checked',false);
		resetValidation($(this).parents('form'));
	</c:if>
		<c:if test="${empty resourceBO || not empty resourceBO.startDate || not empty resourceBO.endDate}">
		//$('.signDropDown').hide();
		$('.disBtn2').attr('required','required');
		$('.disBtn1').removeAttr('required');
		$('.disBtn1').prop('disabled',true);
		$('#inlineRadio6').prop('checked',true);
		$('#inlineRadio5').prop('checked',false);
		resetValidation($(this).parents('form'));
		</c:if>
	


		$("#xdays, #ydays").on('blur',function(){
			chkDaysValid(false);
		});
	 $('#StartDate').datetimepicker({
        format: 'MM/DD/YYYY',
        ignoreReadonly: true,
        useCurrent :false,
        /* minDate:new Date(), */
     });
    $('#EndDate').datetimepicker({
         format: 'MM/DD/YYYY',
         ignoreReadonly: true,
         useCurrent: false,
        /*  minDate:new Date(), */
     }); 
     
     $(".datepicker").on("click", function (e) {
         $('#StartDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()));
         var startDate = $("#StartDate").data("DateTimePicker").date();
         if(startDate != null && startDate != '' && typeof startDate != 'undefined'){
        	 $('#EndDate').data("DateTimePicker").minDate(new Date(startDate));
         }else{
        	 $('#EndDate').data("DateTimePicker").minDate(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()));
         }
     });
     
     $("#StartDate").on("dp.change", function (e) {
			if($("#EndDate").data("DateTimePicker").date() < $(this).data("DateTimePicker").date()) {
				$("#EndDate").val('');
			}
        	$("#EndDate").data("DateTimePicker").minDate(new Date(e.date._d));
     });
        
		$('#inlineRadio5').on('click',function(){
			if($('#inlineRadio5').prop('checked') == true){
			//$('.signDropDown').show();
			$('.disBtn1').prop('disabled',false);
			$('.disBtn2').prop('disabled',true);
			$('.disBtn2').val('');
			$('.disBtn1').attr('required','required');
			$('.disBtn2').removeAttr('required');
			if($('#xdays').attr('oldxDaysVal') != ''){
				$('#inlineRadio5').prop('checked',true);
				$('#xdays').val($('#xdays').attr('oldxDaysVal'));
				$('.disBtn1').prop('disabled',false);
				$('.disBtn2').prop('disabled',true);
			}
			if($('#ydays').attr('oldyDaysVal') != ''){
				$('#inlineRadio5').prop('checked',true);
				$('#ydays').val($('#ydays').attr('oldyDaysVal'));
				$('.disBtn1').prop('disabled',false);
				$('.disBtn2').prop('disabled',true);
			}
			resetValidation('.resetDate');
			}
		});
		
		$('#inlineRadio6').on('click',function(){
			if($('#inlineRadio6').prop('checked') == true){
			//$('.signDropDown').hide();
			$('.disBtn2').prop('disabled',false);
			$('.disBtn1').prop('disabled',true);
			$('.disBtn1').val('');
			$('.disBtn2').attr('required','required');
			$('.disBtn1').removeAttr('required');
			$('#ydays').parent().removeClass('has-error has-danger').find(".help-block").html("");
			if($('#StartDate').attr('oldStartDateVal') != ''){
				$('#inlineRadio6').prop('checked',true);
				$('#StartDate').val($('#StartDate').attr('oldStartDateVal'));
				$('.disBtn1').prop('disabled',true);
				$('.disBtn2').prop('disabled',false);
			}
			if($('#EndDate').attr('oldEndDateVal') != ''){
				$('#inlineRadio6').prop('checked',true);
				$('#EndDate').val($('#EndDate').attr('oldEndDateVal'));
				$('.disBtn1').prop('disabled',true);
				$('.disBtn2').prop('disabled',false);
			}
			resetValidation('.resetDate');
			}
		});
		
	
		if($('#inlineRadio3').prop('checked') == false){
			//$('.signDropDown').hide();
			$('#inlineRadio5').prop('checked',false);
			$('#inlineRadio6').prop('checked',false);
			$('.disRadBtn1').prop('disabled',true);
			$('.disBtn1').removeAttr('required');
			$('.disBtn2').removeAttr('required');
			resetValidation($(this).parents('form'));
		}
		
		
		
		$('#inlineRadio3').on('click',function(){
			if($('#inlineRadio3').prop('checked') == true){
			$('.disBtn1').prop('disabled',false);
			$('.disBtn2').prop('disabled',true);
			$('#inlineRadio5,#inlineRadio6').prop('disabled',false);
			$('.disBtn2').val('');
				if($('#xdays').attr('oldxDaysVal') != ''){
					$('#inlineRadio5').prop('checked',true);
					//$('.signDropDown').show();
					$('#xdays').val($('#xdays').attr('oldxDaysVal'));
					$('.disBtn1').prop('disabled',false);
					$('.disBtn2').prop('disabled',true);
					$('.disBtn1').attr('required','required');
					$('.disBtn2').removeAttr('required');
					resetValidation($('.resetDate'));
				}
				if($('#ydays').attr('oldyDaysVal') != ''){
					$('#inlineRadio5').prop('checked',true);
					//$('.signDropDown').show();
					$('#ydays').val($('#ydays').attr('oldyDaysVal'));
					$('.disBtn1').prop('disabled',false);
					$('.disBtn2').prop('disabled',true);
					$('.disBtn1').attr('required','required');
					$('.disBtn2').removeAttr('required');
					resetValidation($('.resetDate'));
				}
				if($('#StartDate').attr('oldStartDateVal') != ''){
					$('#inlineRadio6').prop('checked',true);
					$('#StartDate').val($('#StartDate').attr('oldStartDateVal'));
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($('.resetDate'));
				}
				if($('#EndDate').attr('oldEndDateVal') != ''){
					$('#inlineRadio6').prop('checked',true);
					$('#EndDate').val($('#EndDate').attr('oldEndDateVal'));
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($('.resetDate'));
				}
				if($('#xdays').attr('oldxDaysVal') == '' && $('#ydays').attr('oldyDaysVal') == '' && $('#StartDate').attr('oldStartDateVal') == '' && $('#EndDate').attr('oldEndDateVal') == ''){
					$('#inlineRadio6').prop('checked',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($('.resetDate'));
				}
			/* } */
			}
			var a = $("#inlineRadio3").val();
			if(a ==0){
			   $(".light-txt").removeClass("opacity06");
			}else{
			  $(".light-txt").addClass("opacity06");
			}
			resetValidation($('.resetDate'));
		});
		
		if($('#inlineRadio3').prop('checked') == true){
		if($('#xdays').attr('oldxDaysVal') == '' && $('#ydays').attr('oldyDaysVal') == '' && $('#StartDate').attr('oldStartDateVal') == '' && $('#EndDate').attr('oldEndDateVal') == ''){
			$('#inlineRadio6').prop('checked',true);
			$('.disBtn2').prop('disabled',false);
			$('.disBtn1').prop('disabled',true);
			$('.disBtn2').attr('required','required');
			$('.disBtn1').removeAttr('required');
		}else if($('#xdays').attr('oldxDaysVal') || $('#ydays').attr('oldyDaysVal')){
			$('#inlineRadio5').prop('checked',true);
			//$('.signDropDown').show();
			$('.disBtn1').prop('disabled',false);
			$('.disBtn2').prop('disabled',true);
			$('.disBtn1').attr('required','required');
			$('.disBtn2').removeAttr('required');
		}else if($('#StartDate').attr('oldStartDateVal') || $('#EndDate').attr('oldEndDateVal')){
			$('#inlineRadio6').prop('checked',true);
			//$('.signDropDown').hide();
			$('.disBtn2').prop('disabled',false);
			$('.disBtn1').prop('disabled',true);
			$('.disBtn2').attr('required','required');
			$('.disBtn1').removeAttr('required');
		}
		var a = $("#inlineRadio3").val();
		if(a ==0){
		   $(".light-txt").removeClass("opacity06");
		}else{
		  $(".light-txt").addClass("opacity06");
		}
		resetValidation($(this).parents('form'));
		}
		
		$('#inlineRadio4').on('click',function(){
			if($('#inlineRadio4').prop('checked') == true){
			//$('.signDropDown').hide();
			$('.disRadBtn1').prop('disabled',true);	
			$('.disRadBtn1').val('');	
			$('.disRadBtn1').prop('checked',false);
			$('.disBtn1').val('');
			$('.disBtn1').removeAttr('required');
			$('.disBtn2').removeAttr('required');
			resetValidation($('.resetDate'));
			}
			
			var a = $("#inlineRadio4").val();
			if(a ==1){
			   $(".light-txt").addClass("opacity06");
			}else{
			  $(".light-txt").removeClass("opacity06");
			}
		});
		
	</c:if>
	
	<c:if test="${actionOn eq 'view'}">
	 	$('#resourceForm input,textarea').prop('disabled', true);
    	$('.viewAct').hide();
	</c:if>
	
	$('.signDropDown').on('change',function(){
		chkDaysValid(false);
	});

});
function chkDaysValid(clickDone){
	var x = $("#xdays").val();
	var y = $("#ydays").val();
	var xSign = $('#xSign').val();
	var ySign = $('#ySign').val();
	if(xSign === '0'){
		x = "+"+x;
	}else if(xSign === '1'){
		x = "-"+x;
	}
	if(ySign === '0'){
		y = "+"+y;
	}else if(ySign === '1'){
		y = "-"+y;
	}
	var valid = true;
	if(y && x){
		if(parseInt(x) > parseInt(y)){
// 			$('#ydays').val('');
			//$('#ydays').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Y days should be greater than X days.</li></ul>');
			if(clickDone && isFromValid($('#ydays').parents('form')))
				$('#ydays').focus();
			$('#ydays').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Y days should be greater than X days.</li></ul>');
			valid = false;
		}else{
			$('#ydays').parent().removeClass('has-error has-danger').find(".help-block").html("");
			resetValidation($('#ydays').parents('form'));
		}
	}
	return valid;
}
<c:if test="${isstudyProtocol ne 'isstudyProtocol'}">
function toJSDate( dateTime ) {
    var dateTime = dateTime.split(" ");
    var date = dateTime[0].split("/");
    return new Date(date[2], (date[0]-1), date[1]);
}
</c:if>
</script>
