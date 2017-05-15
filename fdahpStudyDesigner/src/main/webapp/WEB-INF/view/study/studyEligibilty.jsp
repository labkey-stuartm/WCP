
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpstudydesigner.util.SessionObject"%>

 <div class="col-sm-10 col-rc white-bg p-none">
  <!--  Start top tab section-->
  <form:form data-toggle="validator" action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyEligibilty.do?_S=${param._S}" id="eleFormId">
	  <div class="right-content-head">        
	      <div class="text-right">
	          <div class="black-md-f text-uppercase dis-line pull-left line34">Eligibility <c:set var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive] ? '<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}</div>
	          
	          <div class="dis-line form-group mb-none mr-sm">
	               <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
	           </div>
	          <c:if test="${empty permission}">
	           <div class="dis-line form-group mb-none mr-sm">
	               <button type="button" class="btn btn-default gray-btn submitEle" actType="save">Save</button>
	           </div>
	
	           <div class="dis-line form-group mb-none">
	               <button type="button" class="btn btn-primary blue-btn submitEle" actType="mark">Mark as Completed</button>
	           </div>
	           </c:if>
	       </div>
	  </div>
	        <!--  End  top tab section-->
	      <input type="hidden" value="${eligibility.studyId}" name="studyId" /> 
	      <input type="hidden" value="${eligibility.id}" name="id" /> 
	    <!--  Start body tab section -->
	    <div class="right-content-body">
	       <div class="mb-xlg form-group">
	            <div class="gray-xs-f mb-sm">Choose the method to be used for ascertaining participant eligibility</div>
	            <span class="radio radio-info radio-inline p-45">
	               <input type="radio" id="inlineRadio1" value="1" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 1}">checked</c:if>>
	               <label for="inlineRadio1">Token Validation Only</label>
	           </span>
	           <span class="radio radio-inline p-45">
	               <input type="radio" id="inlineRadio2" value="2" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 2}">checked</c:if>>
	               <label for="inlineRadio2">Token Validation and Eligibility Test</label>
	           </span>
	             <span class="radio radio-inline">
	               <input type="radio" id="inlineRadio3" value="3" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 3}">checked</c:if>>
	               <label for="inlineRadio3">Eligibility Test Only</label>
	           </span>
	           <div class="help-block with-errors red-txt"></div>
	       </div>
	        
	       <div class="blue-md-f mb-md text-uppercase">
	           Token Validation 
	       </div>
	       
	       <div>
	           <div class="gray-xs-f mb-xs">Instruction Text <small>(250 characters max) </small><span class="requiredStar">*</span></div>
	           <div class="form-group elaborateClass">
	               <textarea class="form-control" rows="5" id="comment" maxlength="250" required name="instructionalText" >${eligibility.instructionalText}</textarea>
	               <div class="help-block with-errors red-txt"></div>
	           </div>
	       </div>
	    </div>
    </form:form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
	   $(".menuNav li.active").removeClass('active');
	   $(".menuNav li.fourth").addClass('active');
	   
	   <c:if test="${not empty permission}">
       $('#eleFormId input,textarea,select').prop('disabled', true);
       $('#eleFormId').find('.elaborateClass').addClass('linkDis');
      </c:if>
	   
	   $('.submitEle').click(function(e) {
		   e.preventDefault();
		   $('#actTy').remove();
		   $('<input />').attr('type', 'hidden').attr('name', "actionType").attr('value', $(this).attr('actType')).attr('id', 'actTy') .appendTo('#eleFormId');
	   		if($(this).attr('actType') == 'save'){
	   			$('#eleFormId').validator('destroy');
	   			$('#eleFormId').submit();
	   		} else{
	   			if(isFromValid('#eleFormId'))
	   				$('#eleFormId').submit();
	   		}
		});
	});
</script>