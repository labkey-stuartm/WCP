<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
        <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateResource.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" id="resourceForm" role="form" method="post" autocomplete="off" enctype="multipart/form-data">    
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f dis-line pull-left line34"><span class="pr-sm"><a href="javascript:void(0)" id="goToResourceListForm"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span> Add Resource</div>
                     
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="goToStudyListPage">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="submit" class="btn btn-default gray-btn" id="saveResourceId">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn" id="doneResourceId">Done</button>
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
                   <div class="gray-xs-f mb-xs">Title</div>
                   <div class="form-group">
                        <input type="text" class="form-control" id="resourceTitle" name="title" value="${resourceBO.title}" maxlength="50" required/>
                   		<div class="help-block with-errors red-txt"></div>
                   </div>
                </div>
             </div>
                
            <div class="clearfix"></div>
                
            <div class="mt-lg">
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
              <textarea id="editor" name="richText" required>${resourceBO.richText}</textarea>
               <div class="help-block with-errors red-txt"></div>      
            </div>
            
            
            <div id="pdf_file" class="mt-lg form-group <c:if test="${empty resourceBO || not resourceBO.textOrPdf}">dis-none</c:if>">
                <button id="uploadPdf" type="button" class="btn btn-default gray-btn uploadPdf">Upload PDF</button>
                <input id="uploadImg" class="dis-none" type="file" name="pdfFile" accept=".pdf" required>
                <input type="hidden" value="${resourceBO.pdfUrl}" required id="pdfUrl" name="pdfUrl"> 
                <span id="pdf_name" class="ml-sm">${resourceBO.pdfUrl}</span>
                <span id="delete" class="sprites_icon delete vertical-align-middle ml-sm dis-none"></span>
                <div class="help-block with-errors red-txt"></div> 
            </div>
             
            <c:if test="${studyProtocol ne 'studyProtocol'}">   
            <div class="clearfix"></div>
                
            <div class="mt-xlg">
                <div class="gray-xs-f mb-sm">Set a Period of Visibility for this resource?</div>
                 <span class="radio radio-info radio-inline p-45">
                    <input type="radio" id="inlineRadio3" name="resourceVisibilityParam" value="0" <c:if test="${not resourceBO.resourceVisibility || empty resourceBO}">checked</c:if>>
                    <label for="inlineRadio3">Yes</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio4" name="resourceVisibilityParam" value="1" <c:if test="${resourceBO.resourceVisibility}">checked</c:if>>
                    <label for="inlineRadio4">No</label>
                </span>    
                <div class="help-block with-errors red-txt"></div>
            </div>
                
            <div class="clearfix"></div>
               
             <div class="mt-xlg form-group">
                <div class="gray-xs-f mb-xs">Select Time Period</div>
                 <span class="radio radio-info radio-inline pr-md">
                    <input type="radio" id="inlineRadio5" class="disRadBtn1" value="option1" name="radioInline2">
                    <label for="inlineRadio5">Anchor Date +</label><br/>
                    <!-- <span>&nbsp;</span> -->
                </span>
                 <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input id="xdays" type="text" class="form-control wid70 disRadBtn1 disBtn1" placeholder="x days" name="timePeriodFromDays" value="${resourceBO.timePeriodFromDays}" maxlength="3" required/>
                 	 <!-- <span class="help-block with-errors red-txt"></span> -->
                 </span>
                 <span class="gray-xs-f mb-sm pr-md">
                    to  Anchor Date +                    
                    <!-- <span>&nbsp;</span> -->
                 </span>
                  <span class="form-group m-none dis-inline vertical-align-middle">
                     <input id="ydays" type="text" class="form-control wid70 disRadBtn1 disBtn1" placeholder="y days" name="timePeriodToDays" value="${resourceBO.timePeriodToDays}" maxlength="3" required/>
                 	<!-- <span class="help-block with-errors red-txt"></span> -->
                 </span> 
                 <span id="anchorId" class="help-block with-errors red-txt"></span>               
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
                         <input id="StartDate" type="text" class="form-control disRadBtn1 disBtn2" placeholder="Start Date" name="startDate" value="${resourceBO.startDate}" required/>
                         <span class="help-block with-errors red-txt"></span>
                     </span>
                     <span class="gray-xs-f mb-sm pr-md">
                        to 
                     </span>
                      <span class="form-group m-none dis-inline vertical-align-middle">
                         <input id="EndDate" type="text" class="form-control disRadBtn1 disBtn2" placeholder="End Date" name="endDate" value="${resourceBO.endDate}" required/>
                    	 <span class="help-block with-errors red-txt"></span>
                     </span>
                     <div class="help-block with-errors red-txt"></div>
                 </div>
             </div>
            
              <div class="clearfix"></div>
                
             <div class="mt-xlg">
                <div class="gray-xs-f mb-xs">Text for resource appearance in-app notifications</div>
                 
                 <div class="form-group">
                  <textarea class="form-control" rows="4" id="comment" name="resourceText" maxlength="250" required>${resourceBO.resourceText}</textarea>
                  <div class="help-block with-errors red-txt"></div>
                 </div>
             </div>
                
            <div class="mt-sm">
                <div class="italic-txt gray-xs-f text-weight-light">You can choose to use the variable <x> in your text, as appropriate, where x = number of days elapsed from Anchor Date.</div>
                <div class="italic-txt gray-xs-f text-weight-light mb-xlg">For example, "Congrats! You are now <x> days pregnant. Check out new articles relevant to this phase of pregnancy, in the Resources section!</div>    
            </div>
            </c:if>
            
                
            </div>
            <!--  End body tab section -->
        </form:form>   
        </div>
        <!-- End right Content here -->

<form:form action="/fdahpStudyDesigner/adminStudies/getResourceList.do" name="resourceListForm" id="resourceListForm" method="post">
</form:form>

<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="studyListForm" id="studyListForm" method="post">
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	
	    $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
	    $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
	    $(".menuNav li").removeClass('active');
	    $(".eighthResources").addClass('active'); 
		$("#createStudyId").show();
        
	 $("#doneResourceId").on('click', function(){
		  if($('#inlineRadio1').prop('checked') == true){
			  $('#uploadImg').removeAttr('required');
			  $('#pdfUrl').removeAttr('required');
		  }else if($('#inlineRadio2').prop('checked') == true){
			  $('#editor').removeAttr('required');
			  var file = $('#uploadImg').val();
	          var pdfId = $('#pdfUrl').val();
	          if(file || pdfId){
	        	  $('#uploadImg').removeAttr('required');
	          }
		  }
          if(isFromValid('#resourceForm')){
       	   	$('#buttonText').val('done');
 		   		$('#resourceForm').submit();
 		   }
	    });
	  
	$('#saveResourceId').click(function() {
   	$("#resourceTitle").parent().find(".help-block").empty();
   	$('#resourceForm').validator('destroy').validator();
       if(!$('#resourceTitle')[0].checkValidity()){
       	$("#resourceTitle").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
           return false;
       }else{
       	$('#resourceForm').validator('destroy');
       	$("#buttonText").val('save');
       	$('#resourceForm').submit();
       }
	});
	
	 /* var filename = $('input[type=file]').val().replace(/C:\\fakepath\\/i, ''); */
	 pdfUrlName = $('#pdfUrl').val();
     if(pdfUrlName != ""){
       $("#uploadPdf").text("Change PDF");
       $("#delete").removeClass("dis-none");
     }
     
     
 	$('#goToResourceListForm').on('click',function(){
		$('#resourceListForm').submit();
	});
	
	$('#goToStudyListPage').on('click',function(){
		$('#studyListForm').submit();
	});
	
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
          		  $('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form').validator('validate');
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
    });
    
    
  //Changing & Displaying upload button text & file name
  
    $('#uploadImg').on('change',function (){
    	if($('input[type=file]').val()){
	        var filename = $('input[type=file]').val().replace(/C:\\fakepath\\/i, '');
	        $("#pdf_name").text(filename);
	       
	        var a = $("#uploadPdf").text();
	        if(a == "Upload PDF"){
	          $("#uploadPdf").text("Change PDF");
	        }
       		$("#delete").removeClass("dis-none");
    	}
   });
  
  //Deleting Uploaded pdf
    $("#delete").click(function(){
       $("#uploadPdf").text("Upload PDF");
       $("#pdf_name").text(""); 
       $(this).addClass("dis-none");
       $('input[type=file]').val('');
       $('#pdfUrl').val('');
    });
	
	<c:if test="${studyProtocol ne 'studyProtocol'}">
	<c:if test="${empty resourceBO || not empty resourceBO.timePeriodFromDays}">
	/* if($('#inlineRadio5').prop('checked') == true){*/
		$('.disBtn1').attr('required','required');
		$('.disBtn2').removeAttr('required');
		$('.disBtn2').prop('disabled',true);
		$('#inlineRadio5').prop('checked',true);
		$('#inlineRadio6').prop('checked',false);
	</c:if>
	/* }else if($('#inlineRadio6').prop('checked') == true){ */
		<c:if test="${not empty resourceBO.startDate}">
		$('.disBtn2').attr('required','required');
		$('.disBtn1').removeAttr('required');
		$('.disBtn1').prop('disabled',true);
		$('#inlineRadio6').prop('checked',true);
		$('#inlineRadio5').prop('checked',false);
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
	});
	
		$("#ydays").blur(function(){
			var y = $("#ydays").val();
			var x = $("#xdays").val();
			if(y < x){
			 	$("#anchorId").text("Y days should be greater than X days.");
			}else{
				$("#anchorId").text("");
			}
		});


		$("#xdays").blur(function(){
			var x = $("#xdays").val();
			var y = $("#ydays").val();
				if(x==0){
					$("#anchorId").text("x days should be greater than 1.");
				}else if(y != 0 && x > y){
				    $("#anchorId").text("X days should be less than y days.");
				}else{
					$("#anchorId").text("");
				}
		});
		
		 /* $("#xdays").on("dp.change", function (e) {
		        $("#xdays").parent().removeClass("has-danger").removeClass("has-error");
		           $("#xdays").parent().find(".help-block").html("");
		           $("#ydays").parent().removeClass("has-danger").removeClass("has-error");
		           $("#ydays").parent().find(".help-block").html("");
		           var xdays = $("#xdays").val();
		           var ydays = $("#ydays").val();
		           if(xdays !='' && ydays !='' && xdays > ydays){
		               $("#xdays").parent().addClass("has-danger").addClass("has-error");
		              $("#xdays").parent().find(".help-block").html('<ul class="list-unstyled"><li>x days should not be greater than y days</li></ul>');
		           }else{
		            $("#xdays").parent().removeClass("has-danger").removeClass("has-error");
		               $("#xdays").parent().find(".help-block").html("");
		               $("#ydays").parent().removeClass("has-danger").removeClass("has-error");
		               $("#ydays").parent().find(".help-block").html("");
		               
		           }
		        });
		        $("#ydays").on("dp.change", function (e) {
		         $("#ydays").parent().removeClass("has-danger").removeClass("has-error");
		            $("#ydays").parent().find(".help-block").html("");
		            $("#xdays").parent().removeClass("has-danger").removeClass("has-error");
		            $("#xdays").parent().find(".help-block").html("");
		         	var startDate = $("#StartDate").val();
		            var endDate = $("#EndDate").val();
		            if(xdays!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
		                $("#ydays").parent().addClass("has-danger").addClass("has-error");
		                $("#ydays").parent().find(".help-block").html('<ul class="list-unstyled"><li>y days should not be less than x days</li></ul>');
		            }else{
		             $("#ydays").parent().removeClass("has-danger").removeClass("has-error");
		                $("#ydays").parent().find(".help-block").html("");
		                $("#xdays").parent().removeClass("has-danger").removeClass("has-error");
		                $("#xdays").parent().find(".help-block").html("");
		            }
		        }); */
	
	 $('#StartDate').datetimepicker({
        format: 'DD/MM/YYYY',
        minDate:new Date(),
     });
     $('#EndDate').datetimepicker({
         format: 'DD/MM/YYYY',
         minDate:new Date(),
         useCurrent: false,
     });  
     $("#StartDate").on("dp.change", function (e) {
        $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
           $("#StartDate").parent().find(".help-block").html("");
           $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
           $("#EndDate").parent().find(".help-block").html("");
           var startDate = $("#StartDate").val();
           var endDate = $("#EndDate").val();
           if(startDate !='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
               $("#StartDate").parent().addClass("has-danger").addClass("has-error");
              $("#StartDate").parent().find(".help-block").html('<ul class="list-unstyled"><li>Start Date Should not be greater than End Date</li></ul>');
           }else{
            $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
               $("#StartDate").parent().find(".help-block").html("");
               $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
               $("#EndDate").parent().find(".help-block").html("");
               
           }
        });
        $("#EndDate").on("dp.change", function (e) {
         $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
            $("#EndDate").parent().find(".help-block").html("");
            $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
            $("#StartDate").parent().find(".help-block").html("");
         	var startDate = $("#StartDate").val();
            var endDate = $("#EndDate").val();
            if(startDate!='' && endDate!='' && toJSDate(startDate) > toJSDate(endDate)){
                $("#EndDate").parent().addClass("has-danger").addClass("has-error");
                $("#EndDate").parent().find(".help-block").html('<ul class="list-unstyled"><li>End Date Should not be less than Start Date</li></ul>');
            }else{
             $("#EndDate").parent().removeClass("has-danger").removeClass("has-error");
                $("#EndDate").parent().find(".help-block").html("");
                $("#StartDate").parent().removeClass("has-danger").removeClass("has-error");
                $("#StartDate").parent().find(".help-block").html("");
            }
        });
        
        <c:if test="${not empty resourceBO}">
       /*  if($('#inlineRadio5').prop('checked') == false){
			$('.disBtn1').prop('disabled',true);			
		}
		
		if($('#inlineRadio6').prop('checked') == false){
			$('.disBtn2').prop('disabled',true);			
		} */
		</c:if>
	
		$('#inlineRadio5').on('click',function(){
			$('.disBtn1').prop('disabled',false);
			$('.disBtn2').prop('disabled',true);
			/* $('.disBtn2').val(''); */
		});
		
		$('#inlineRadio6').on('click',function(){
			$('.disBtn2').prop('disabled',false);
			$('.disBtn1').prop('disabled',true);
			/* $('.disBtn1').val(''); */
		});
		
	
		if($('#inlineRadio3').prop('checked') == false){
			$('.disRadBtn1').prop('disabled',true);			
		}
		
		$('#inlineRadio3').on('click',function(){
			$('.disRadBtn1').prop('disabled',false);	
		});
		
		$('#inlineRadio4').on('click',function(){
			$('.disRadBtn1').prop('disabled',true);	
			$('.disRadBtn1').val('');	
			$('.disRadBtn1').prop('checked',false);
		});
		
	</c:if>
});
<c:if test="${studyProtocol ne 'studyProtocol'}">
function toJSDate( dateTime ) {
    var dateTime = dateTime.split(" ");
    var date = dateTime[0].split("/");
    //var time = dateTime[1].split(":");
    return new Date(date[2], (date[0]-1), date[1]);
}
</c:if>
</script>

