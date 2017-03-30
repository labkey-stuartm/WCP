<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="col-sm-10 col-rc white-bg p-none">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateResource.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" id="resourceForm" role="form" method="post" autocomplete="off" enctype="multipart/form-data">    
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f dis-line pull-left line34"><span class="pr-sm"><a href="javascript:void(0)" class="goToResourceListForm" id="goToResourceListForm"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span>
                    <c:if test="${studyProtocol ne 'studyProtocol'}">
                    <c:if test="${empty resourceBO}">Add Resource</c:if>
                    <c:if test="${not empty resourceBO && action ne 'view'}">Edit Resource</c:if>
                    <c:if test="${not empty resourceBO && action eq 'view'}">View Resource</c:if>
                    </c:if>
                    <c:if test="${studyProtocol eq 'studyProtocol'}">
                    <c:if test="${empty resourceBO}">Add Study Protocol</c:if>
                    <c:if test="${not empty resourceBO}">Edit Study Protocol</c:if>
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
            <c:if test="${studyProtocol eq 'studyProtocol'}">
            	<input type="hidden" name="studyProtocol" value="studyProtocol"/>
            </c:if>
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
             <div class="mt-lg">
                <!-- form- input-->
                <div>
                   <div class="gray-xs-f mb-xs">Title <c:if test="${studyProtocol ne 'studyProtocol'}"><small class="viewAct">(50 characters max)</small></c:if><span class="requiredStar"> *</span></div>
                   <div class="form-group">
                        <input type="text" class="form-control" id="resourceTitle" name="title" value="${resourceBO.title}" maxlength="50" required pattern="[a-zA-Z0-9\s]+" data-pattern-error="Special characters are not allowed." <c:if test="${studyProtocol eq 'studyProtocol'}">readonly</c:if>/>
                   		<div class="help-block with-errors red-txt"></div>
                   </div>
                </div>
             </div>
                
            <div class="clearfix"></div>
                
            <div class="mt-lg">
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
             
            <div id="richEditor" class="mt-lg form-group <c:if test="${resourceBO.textOrPdf}">dis-none</c:if>">
              <textarea class="remReqOnSave" id="editor" name="richText" required>${resourceBO.richText}</textarea>
               <div class="help-block with-errors red-txt"></div>      
            </div>
            
            
            <div id="pdf_file" class="mt-lg form-group <c:if test="${empty resourceBO || not resourceBO.textOrPdf}">dis-none</c:if>">
                <button id="uploadPdf" type="button" class="btn btn-default gray-btn uploadPdf viewAct">Upload PDF</button>
                <input id="uploadImg" class="dis-none remReqOnSave" type="file" name="pdfFile" accept=".pdf" data-native-error="Please select a pdf file" required>
                <input type="hidden" class="remReqOnSave" value="${resourceBO.pdfUrl}" required id="pdfUrl" name="pdfUrl">
                <input type="hidden" value="${resourceBO.pdfName}" id="pdfName" name="pdfName">
               <%--  <a href="/fdahpStudyDesigner/studyResources/${resourceBO.pdfUrl}"><span id="pdf_name" class="ml-sm" style="color: black">${resourceBO.pdfName}</span></a> --%>
<!--                 <span id="delete" class="sprites_icon delete vertical-align-middle ml-sm dis-none"></span> -->
			<!-- <span id="delete" class="blue-link dis-none viewAct">&nbsp;X<a href="javascript:void(0)" class="blue-link txt-decoration-underline pl-xs">Remove PDF</a></span> -->
             <span class="alert customalert pdfDiv">
               <%--  <a href="/fdahpStudyDesigner/studyResources/${resourceBO.pdfUrl}" id="pdfClk"> --%>
                <a href="/fdahpStudyDesigner/studyResources/${resourceBO.pdfUrl}" id="pdfClk"><img src="/fdahpStudyDesigner/images/icons/pdf.png"/>
                <span id="pdf_name" class="ml-sm" style="color: #000;"><span class="mr-sm">${resourceBO.pdfName}</span></span></a>
				<span id="delete" class="blue-link dis-none viewAct borr">&nbsp;X<a href="javascript:void(0)" class="blue-link pl-xs mr-sm">Remove PDF</a></span>
			</span>
            <div class="help-block with-errors red-txt"></div>  
            </div>
             
            <c:if test="${studyProtocol ne 'studyProtocol'}">   
            <div class="clearfix"></div>
                
            <div class="mt-xlg">
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
               
             <div class="mt-xlg">
                <div class="gray-xs-f mb-xs">Select Time Period <span class="requiredStar">*</span></div>
                 <span class="radio radio-info radio-inline pr-md">
                    <input type="radio" id="inlineRadio5" class="disRadBtn1" value="option1" name="radioInline2">
                    <label for="inlineRadio5">Anchor Date +</label><br/>
                    <!-- <span>&nbsp;</span> -->
                </span>
                 <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="xdays" type="text" class="form-control wid70 disRadBtn1 disBtn1 remReqOnSave daysMask mt-md" placeholder="x days" name="timePeriodFromDays" value="${resourceBO.timePeriodFromDays}" oldxDaysVal="${resourceBO.timePeriodFromDays}" maxlength="3" required pattern="[0-9]+" data-pattern-error="Please enter valid number."/>
                 	 <span class="help-block with-errors red-txt"></span>
                 </span>
                 <span class="mb-sm pr-md">
                    <span class="light-txt opacity06">to  Anchor Date + </span>                   
                    <!-- <span>&nbsp;</span> -->
                 </span>
                  <span class="form-group m-none dis-inline vertical-align-middle">
                     <input id="ydays" type="text" class="form-control wid70 disRadBtn1 disBtn1 remReqOnSave daysMask mt-md" placeholder="y days" name="timePeriodToDays" value="${resourceBO.timePeriodToDays}" oldyDaysVal="${resourceBO.timePeriodToDays}" maxlength="3" required pattern="[0-9]+"/>
                 	 <span class="help-block with-errors red-txt"></span>
                 </span> 
                <!--  <span id="anchorId" class="help-block with-errors red-txt"></span>   -->             
             </div>
                
             <div class="mt-xlg">
                 <div class="mb-sm">
                     <span class="radio radio-info radio-inline pr-md">
                        <input type="radio" class="disRadBtn1" id="inlineRadio6" value="option1" name="radioInline2">
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
                
             <div class="mt-xlg">
                <div class="gray-xs-f mb-xs">Text for notifying participants about the new resource being available<small class="viewAct">(250 characters max)</small> <span class="requiredStar">*</span></div>
                 
                 <div class="form-group">
                  <textarea class="form-control remReqOnSave" rows="4" id="comment" name="resourceText" maxlength="250" required>${resourceBO.resourceText}</textarea>
                  <div class="help-block with-errors red-txt"></div>
                 </div>
             </div>
            </c:if>
            
                
            </div>
            <!--  End body tab section -->
        </form:form>   
        </div>
        <!-- End right Content here -->

<form:form action="/fdahpStudyDesigner/adminStudies/getResourceList.do" name="resourceListForm" id="resourceListForm" method="post">
</form:form>

<%-- <form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="studyListForm" id="studyListForm" method="post">
</form:form> --%>
<script type="text/javascript">
$(document).ready(function(){
	<c:if test="${studyProtocol eq 'studyProtocol' && empty resourceBO.title}">
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
          if(isFromValid('#resourceForm')){
       	   	$('#buttonText').val('done');
 		   		$('#resourceForm').submit();
 		   }else{
 			  $('#doneResourceId').prop('disabled',false);
 		   }
	    });
	 
	 if($('#inlineRadio1').prop('checked')){
		 $('#uploadImg').removeAttr('required');
		 $('#pdfUrl').removeAttr('required');
		 $('#editor').attr('required','required');
		 resetValidation($('#resourceForm'));
	 }else{
		 $('#editor').removeAttr('required');
		  var file = $('#uploadImg').val();
          var pdfId = $('#pdfUrl').val();
          $('#editor').removeAttr('required');
		  if(pdfId){
			  $('#pdfUrl').attr('required','required');
			  $('#uploadImg').removeAttr('required');
		  }else{
			  $('#uploadImg').attr('required','required');
			  $('#pdfUrl').removeAttr('required');
		  }
		  resetValidation($('#resourceForm'));
	 }
	 
	 $('#inlineRadio1','#inlineRadio2').on('change',function(){
		 if($('#inlineRadio1').prop('checked')){
			 $('#uploadImg').removeAttr('required');
			 $('#pdfUrl').removeAttr('required');
		 }else{
			 $('#editor').removeAttr('required');
			  var file = $('#uploadImg').val();
	          var pdfId = $('#pdfUrl').val();
	          /* if(file || pdfId){
	        	  $('#uploadImg').removeAttr('required');
	          } */
	          $('#editor').removeAttr('required');
			  if(pdfId){
				  $('#pdfUrl').attr('required','required');
			  }else{
				  $('#uploadImg').attr('required','required');
			  }
		 }
		  resetValidation($('#resourceForm'));
	 });
	  
	$('#saveResourceId').click(function() {
		 $('#saveResourceId').prop('disabled',true);
			/* $('.remReqOnSave').removeAttr('required'); */
		   	$("#resourceTitle").parent().find(".help-block").empty();
	   		$('#resourceForm').validator('destroy').validator();
	   		var isValid = true;
	   if($('#inlineRadio5').prop('checked') && ($('#xdays').val() || $('#ydays').val())) {
		   isValid = chkDaysValid();
	   }
       if(!$('#resourceTitle')[0].checkValidity()){
    	  /*  $('.remReqOnSave').attr('required',true); */
    	if($("#resourceTitle").parent().addClass('has-error has-danger').find(".help-block").text() == ''){
    		$("#resourceTitle").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
    	}
       	$('#saveResourceId').prop('disabled',false);
    	  return false;
       }else if(isValid){	
	       	$('#resourceForm').validator('destroy');
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
 		$('#goToResourceListForm').addClass('cursor-none');
 		$('#goToStudyListPage').prop('disabled',true);
		$('#resourceListForm').submit();
	});
	
	/* $('#goToStudyListPage').on('click',function(){
		$('#studyListForm').submit();
	}); */
	
	 // File Upload    
    $(".uploadPdf,.changePdf").click(function(){               
       $("#uploadImg").click();
    });
	 
  //wysiwyg editor
    if($("#editor").length > 0){
    tinymce.init({
        selector: "#editor",
        theme: "modern",
        skin: "lightgray",
        height:150,
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
            	resetValidation($('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form'));
            });
     	  }
    });
	}
    //Toggling Rich editor and Upload Button    
    $(".addResource").click(function(){
        var a = $(this).val();
        if(a == '0'){
           /*  $("#richEditor").show(); */
            $("#richEditor").removeClass("dis-none");
            $("#pdf_file").addClass("dis-none");
            $('#editor').attr('required','required');
  		  	$('#uploadImg').removeAttr('required');
  		  	$('#pdfUrl').removeAttr('required');
        }else if(a == '1'){
           /*  $("#richEditor").hide(); */
            $("#richEditor").addClass("dis-none");
            $("#pdf_file").removeClass("dis-none");
            $('#editor').removeAttr('required');
            $('#uploadImg').attr('required','required');
            $('#pdfUrl').attr('required','required');
        }
        resetValidation($(this).parents('form'));
    });
    
    
  //Changing & Displaying upload button text & file name
  
    $('#uploadImg').on('change',function (){
    	var fileExtension = ['pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
        	/* $("#uploadImg").parent().find(".help-block").html('<ul class="list-unstyled"><li>Please select a pdf file</li></ul>'); */
        	$('#uploadImg').val('');
        }else if($('input[type=file]').val()){
        	$('#pdfClk').css('pointer-events','none');
        	$('.pdfDiv').show();
	        var filename = $('input[type=file]').val().replace(/C:\\fakepath\\/i, '');
	        $("#pdf_name").text(filename);
	       
	        var a = $("#uploadPdf").text();
	        if(a == "Upload PDF"){
	          $("#uploadPdf").text("Change PDF");
	        }
       		$("#delete").removeClass("dis-none");
    	}
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
       $("#pdf_name").text(""); 
       $(this).addClass("dis-none");
       $('input[type=file]').val('');
       $('#pdfUrl').val('');
       $('#pdfName').val('');
       $("#uploadImg").attr('required','required');
       $('.pdfDiv').hide();
    });
	
	<c:if test="${studyProtocol ne 'studyProtocol'}">
	<c:if test="${not empty resourceBO.timePeriodFromDays || not empty resourceBO.timePeriodToDays}">
	/* if($('#inlineRadio5').prop('checked') == true){*/
		$('.disBtn1').attr('required','required');
		$('.disBtn2').removeAttr('required');
		$('.disBtn2').prop('disabled',true);
		$('#inlineRadio5').prop('checked',true);
		$('#inlineRadio6').prop('checked',false);
		resetValidation($(this).parents('form'));
	</c:if>
	/* }else if($('#inlineRadio6').prop('checked') == true){ */
		<c:if test="${empty resourceBO || not empty resourceBO.startDate || not empty resourceBO.endDate}">
		$('.disBtn2').attr('required','required');
		$('.disBtn1').removeAttr('required');
		$('.disBtn1').prop('disabled',true);
		$('#inlineRadio6').prop('checked',true);
		$('#inlineRadio5').prop('checked',false);
		resetValidation($(this).parents('form'));
		</c:if>
	/* }  */
	
	$('.disRadBtn1').on('click',function(){
		if($('#inlineRadio5').prop('checked') == true){
			$('.disBtn1').attr('required','required');
			$('.disBtn2').removeAttr('required');
		}else if($('#inlineRadio6').prop('checked') == true){
			$('.disBtn2').attr('required','required');
			$('.disBtn1').removeAttr('required');
		}
		resetValidation($(this).parents('form'));
	});
	
		/* $("#ydays").blur(function(){
			var y = $("#ydays").val();
			var x = $("#xdays").val();
			if(y != '' && y < x){
			 	$('#ydays').next().text("Y days should be greater than X days.");
			}else{
				$('#ydays').next().text("");
			}
		}); */


		$("#xdays, #ydays").on('blur',function(){
			chkDaysValid();
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
         $('.datepicker').data("DateTimePicker").minDate(new Date(new Date().getFullYear(),new Date().getMonth(), new Date().getDate()));
     });
     
     $("#StartDate").on("dp.change", function (e) {
//            $("#StartDate").parent().find(".help-block").html("");
//            $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
//            $("#EndDate").parent().find(".help-block").html("");
//            var startDate = $("#StartDate").val();
//            var endDate = $("#EndDate").val();
//            if(startDate !='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
//                $("#StartDate").parent().addClass("has-danger").addClass("has-error");
//               $("#StartDate").parent().find(".help-block").html('<ul class="list-unstyled"><li>Start Date Should not be greater than End Date</li></ul>');
//            }else{
//             $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
//                $("#StartDate").parent().find(".help-block").html("");
//                $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
//                $("#EndDate").parent().find(".help-block").html("");
               
//            }
			if($("#EndDate").data("DateTimePicker").date() < $(this).data("DateTimePicker").date()) {
				$("#EndDate").val('');
			}
        	$("#EndDate").data("DateTimePicker").minDate(new Date(e.date._d));
        });
//         $("#EndDate").on("dp.change", function (e) {
//          $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
//             $("#EndDate").parent().find(".help-block").html("");
//             $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
//             $("#StartDate").parent().find(".help-block").html("");
//          	var startDate = $("#StartDate").val();
//             var endDate = $("#EndDate").val();
//             if(startDate!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
//                 $("#EndDate").parent().addClass("has-danger").addClass("has-error");
//                 $("#EndDate").parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date Should not be less than Start Date</li></ul>');
//             }else{
//              $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
//                 $("#EndDate").parent().find(".help-block").html("");
//                 $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
//                 $("#StartDate").parent().find(".help-block").html("");
//             }
//         });
        
        <c:if test="${not empty resourceBO}">
       /*  if($('#inlineRadio5').prop('checked') == false){
			$('.disBtn1').prop('disabled',true);			
		}
		
		if($('#inlineRadio6').prop('checked') == false){
			$('.disBtn2').prop('disabled',true);			
		} */
		</c:if>
	
		$('#inlineRadio5').on('click',function(){
			if($('#inlineRadio5').prop('checked') == true){
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
			resetValidation($(this).parents('form'));
			}
		});
		
		$('#inlineRadio6').on('click',function(){
			if($('#inlineRadio6').prop('checked') == true){
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
			resetValidation($(this).parents('form'));
			}
		});
		
	
		if($('#inlineRadio3').prop('checked') == false){
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
			/* if($('#inlineRadio5').prop('checked') == true){ */
				if($('#xdays').attr('oldxDaysVal') != ''){
					$('#inlineRadio5').prop('checked',true);
					$('#xdays').val($('#xdays').attr('oldxDaysVal'));
					$('.disBtn1').prop('disabled',false);
					$('.disBtn2').prop('disabled',true);
					$('.disBtn1').attr('required','required');
					$('.disBtn2').removeAttr('required');
					resetValidation($(this).parents('form'));
				}
				if($('#ydays').attr('oldyDaysVal') != ''){
					$('#inlineRadio5').prop('checked',true);
					$('#ydays').val($('#ydays').attr('oldyDaysVal'));
					$('.disBtn1').prop('disabled',false);
					$('.disBtn2').prop('disabled',true);
					$('.disBtn1').attr('required','required');
					$('.disBtn2').removeAttr('required');
					resetValidation($(this).parents('form'));
				}
			/* } */
			/* else if($('#inlineRadio6').prop('checked') == true){ */
				if($('#StartDate').attr('oldStartDateVal') != ''){
					$('#inlineRadio6').prop('checked',true);
					$('#StartDate').val($('#StartDate').attr('oldStartDateVal'));
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($(this).parents('form'));
				}
				if($('#EndDate').attr('oldEndDateVal') != ''){
					$('#inlineRadio6').prop('checked',true);
					$('#EndDate').val($('#EndDate').attr('oldEndDateVal'));
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($(this).parents('form'));
				}
				if($('#xdays').attr('oldxDaysVal') == '' && $('#ydays').attr('oldyDaysVal') == '' && $('#StartDate').attr('oldStartDateVal') == '' && $('#EndDate').attr('oldEndDateVal') == ''){
					$('#inlineRadio6').prop('checked',true);
					$('.disBtn2').prop('disabled',false);
					$('.disBtn1').prop('disabled',true);
					$('.disBtn2').attr('required','required');
					$('.disBtn1').removeAttr('required');
					resetValidation($(this).parents('form'));
				}
			/* } */
			}
			var a = $("#inlineRadio3").val();
			if(a ==0){
			   $(".light-txt").removeClass("opacity06");
			}else{
			  $(".light-txt").addClass("opacity06");
			}
			resetValidation($(this).parents('form'));
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
			$('.disBtn1').prop('disabled',false);
			$('.disBtn2').prop('disabled',true);
			$('.disBtn1').attr('required','required');
			$('.disBtn2').removeAttr('required');
		}else if($('#StartDate').attr('oldStartDateVal') || $('#EndDate').attr('oldEndDateVal')){
			$('#inlineRadio6').prop('checked',true);
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
			$('.disRadBtn1').prop('disabled',true);	
			$('.disRadBtn1').val('');	
			$('.disRadBtn1').prop('checked',false);
			$('.disBtn1').val('');
			$('.disBtn1').removeAttr('required');
			$('.disBtn2').removeAttr('required');
			resetValidation($(this).parents('form'));
			/* $(".disBtn2").parent().removeClass("has-danger").removeClass("has-error"); */
			}
			
			var a = $("#inlineRadio4").val();
			if(a ==1){
			   $(".light-txt").addClass("opacity06");
			}else{
			  $(".light-txt").removeClass("opacity06");
			}
		});
		
	</c:if>
	
	<c:if test="${action eq 'view'}">
	 	$('#resourceForm input,textarea').prop('disabled', true);
    	$('#resourceForm #richEditor').addClass('linkDis');
    	$('.viewAct').hide();
	</c:if>

});
function chkDaysValid(){
	var x = $("#xdays").val();
	var y = $("#ydays").val();
	var valid = true;
	if(y && x){
		if(x > y){
			$('#ydays').val('');
			$('#ydays').parent().addClass('has-error has-danger').find(".help-block").empty().append('<ul class="list-unstyled"><li>Y days should be greater than X days.</li></ul>');
			valid = false;
		}else{
			/* $('#ydays').parent().removeClass("has-danger").removeClass("has-error"); */
			$('#ydays').parent().removeClass('has-error has-danger').find(".help-block").html("");
		}
	}
	return valid;
}
<c:if test="${studyProtocol ne 'studyProtocol'}">
function toJSDate( dateTime ) {
    var dateTime = dateTime.split(" ");
    var date = dateTime[0].split("/");
    //var time = dateTime[1].split(":");
    return new Date(date[2], (date[0]-1), date[1]);
}
</c:if>
</script>

