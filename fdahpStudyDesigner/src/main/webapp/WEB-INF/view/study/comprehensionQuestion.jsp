<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
/* .ans-opts:last-child .addBtnDis {
    display: initial !important;
}
.ans-opts .addBtnDis {
    display: none !important;
} */
.ans-opts .addBtnDis{
	display: none;
}
.ans-opts .remBtnDis{
	display: initial;
}
.ans-opts:last-child .addBtnDis{
	display: initial;
}
</style>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentInfo.do" name="basicInfoFormId" id="basicInfoFormId" method="post" role="form">
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="pr-sm"><img src="../images/icons/back-b.png"/></span> Add Question</div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Cancel</button>
         </div>
         <div class="dis-line form-group mb-none mr-sm">
            <button type="button" class="btn btn-default gray-btn">Save</button>
         </div>
         <div class="dis-line form-group mb-none">
            <button type="button" class="btn btn-primary blue-btn">Done</button>
         </div>
      </div>
   </div>
   <!--  End  top tab section-->
   <!--  Start body tab section -->
   <div class="right-content-body">
      <div>
         <div class="gray-xs-f mb-xs mt-md">Question Text</div>
         <div class="form-group">
            <input type="text" class="form-control" name="questionText" id="questionText" required value="${comprehensionQuestionBo.questionText}"/>
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- Answer option section-->
	      <div class="col-md-11 col-lg-12 p-none">
	         <!-- Bending Answer options -->
	         <div class="unitDivParent">  
	         	<c:if test="${fn:length(comprehensionQuestionBo.responseList) eq 0}">
	         	   <div class="col-md-12 p-none">
	         	   	<div class='col-md-6 pl-none'>
				         <div class="gray-xs-f mb-xs">Answer Options</div>
				       </div> 
				        <div class='col-md-2'>
					       	   <div class="gray-xs-f mb-xs">Correct Answer</div>
					    </div>
					    
					    <div class="col-md-4">
				       		<div class="gray-xs-f mb-xs">&nbsp;</div>
				       	</div>	
				       	<div class="clearfix"></div>
	         	   </div>
			       <div class="ans-opts col-md-12 p-none"> 
				       <div class='col-md-6 pl-none'>
				        	<div class='form-group'>
					      	 <input type='text' class='form-control' required/>
					       	 <div class='help-block with-errors red-txt'></div>
					       </div>
			           </div>
				       <div class='col-md-2'>
					     <div class="form-group">
							       <select class='selectpicker wid100' title='Select' required>
								       <option value=''>Select</option>
								       <option>Yes</option>
								       <option>No</option>
							       </select>
							       <div class='help-block with-errors red-txt'></div>
						       </div>  	   
				       </div>
				       <div class="col-md-4">
				       		<div class="clearfix"></div>
				       		<div class="mt-xs formgroup"> 
				       			<span class="addBtnDis study-addbtn ml-none" onclick='addAns();'>+</span>
				       			<span type="button" class="cur-pointer remBtnDis hide" onclick='removeAns(this);'><img id='del' src='../images/icons/delete-g.png'/></span>
				       			<!-- <span id="ans-btn" class="addBtnDis study-addbtn ml-none" onclick='addAns();'>+</span>
				       			<span class='cur-pointer remBtnDis hide'><img id='del' src='../images/icons/delete-g.png'/></span> -->
				       	     </div> 
				       </div>
			       </div>  
		         </c:if>          
				 <c:if test="${fn:length(comprehensionQuestionBo.responseList) gt 0}">
				 	<div class="col-md-12 p-none">
	         	   	<div class='col-md-6 pl-none'>
				         <div class="gray-xs-f mb-xs">Answer Options</div>
				       </div> 
				        <div class='col-md-2'>
					       	   <div class="gray-xs-f mb-xs">Correct Answer</div>
					    </div>
					    
					    <div class="col-md-4">
				       		<div class="gray-xs-f mb-xs">&nbsp;</div>
				       	</div>	
				       	<div class="clearfix"></div>
	         	   </div>
				    <c:forEach items="${comprehensionQuestionBo.responseList}" var="responseBo" varStatus="responseBoVar">
				        <div class="ans-opts col-md-12 p-none"> 
					       <div class='col-md-6 pl-none'>
					        	<div class='form-group'>
						      	 <input type='text' class='form-control' required/>
						       	 <div class='help-block with-errors red-txt'></div>
						       </div>
				           </div>
					       <div class='col-md-2'>
						     <div class="form-group">
								       <select class='selectpicker wid100' title='Select' required>
									       <option value=''>Select</option>
									       <option>Yes</option>
									       <option>No</option>
								       </select>
								       <div class='help-block with-errors red-txt'></div>
							       </div>  	   
					       </div>
					       <div class="col-md-4">
					       		<div class="clearfix"></div>
					       		<div class="mt-xs formgroup"> 
					       			<span class="addBtnDis study-addbtn ml-none" onclick='addAns();'>+</span>
					       			<span type="button" class="cur-pointer remBtnDis hide" onclick='removeAns(this);'><img id='del' src='../images/icons/delete-g.png'/></span>
					       			<!-- <span id="ans-btn" class="addBtnDis study-addbtn ml-none" onclick='addAns();'>+</span>
					       			<span class='cur-pointer remBtnDis hide'><img id='del' src='../images/icons/delete-g.png'/></span> -->
					       	     </div> 
					       </div>
				       </div>  
				    </c:forEach>
				 </c:if>       
	         </div>
	      </div>
	      <div class="clearfix"></div>
      <!-- -->
      <div>
         <div class="gray-xs-f mb-sm">Choose structure of the correct answer</div>
         <div class="form-group">
            <span class="radio radio-info radio-inline p-45">
	            <input type="radio" id="inlineRadio1" value="0" name="structureOfCorrectAns" ${consentInfoBo.structureOfCorrectAns == true?'checked':''}>
	            <label for="inlineRadio1">Any of the ones marked as Correct Answers</label>
	            </span>
            <span class="radio radio-inline p-45">
	            <input type="radio" id="inlineRadio2" value="1" name="structureOfCorrectAns" ${consentInfoBo.structureOfCorrectAns == false?'checked':''}>
	            <label for="inlineRadio2">All the ones marked as Correct Answers</label>
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
$(document).ready(function() {
    /* $('#ans-btn').click(function() {
        $('#ans-opts').append();
    }); */
 // Fancy Scroll Bar
   /*  $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
    $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"}); */
    //Removing answer option
    $("#del").click(function() {
        $(this).parents().find("#ans-opts").remove();
    });
    
    if($('.ans-opts').length > 1){
		$(".remBtnDis").removeClass("hide");
	}else{
		$('.unitDivParent').find(".remBtnDis").addClass("hide");
	}

});
function addAns(){
	var newAns = "<div class='ans-opts col-md-12 p-none'><div class='col-md-6 pl-none'>"
        +"<div class='form-group'>"
	        +"<input type='text' class='form-control' required/>"
	        +"<div class='help-block with-errors red-txt'></div>"
	        +"</div>"
        +"</div>"
        +"<div class='col-md-2'><div class='form-group'>"
	        +"<select class='selectpicker wid100' title='Select'>"
		        +"<option value=''>Select</option>"
	        	+"<option>Yes</option>"
		        +"<option>No</option>"
	        +"</select>"
	        +"<div class='help-block with-errors red-txt'></div>"
	        +"</div>"
        +"</div>"
        +"<div class='col-md-4'>"
        +"	<div class='clearfix'></div>"
        +"	<div class='mt-xs form-group'> "
        +"		<span id='ans-btn' class='addBtnDis study-addbtn ml-none' onclick='addAns();'>+</span>"
        +"		<span class='cur-pointer remBtnDis hide' onclick='removeAns(this);'><img id='del' src='../images/icons/delete-g.png'/></span>"
        +"    </div> "
	   +"</div>"
       +" </div>"
        +"</div></div>";
	$(".ans-opts:last").after(newAns);
	$(".ans-opts").parents("form").validator("destroy");
    $(".ans-opts").parents("form").validator();
	if($('.ans-opts').length > 1){
		$(".remBtnDis").removeClass("hide");
		console.log("ifffffff");
	}else{
		$('.unitDivParent').find(".remBtnDis").addClass("hide");
		console.log("else");
	}
}
function removeAns(param){
    $(param).parents(".ans-opts").remove();
    $(".ans-opts").parents("form").validator("destroy");
		$(".ans-opts").parents("form").validator();
		if($('.ans-opts').length > 1){
			$(".remBtnDis").removeClass("hide");
			console.log("ifffffff");
		}else{
			$(".remBtnDis").addClass("hide");
			
			console.log("else");
		}
}
</script>
