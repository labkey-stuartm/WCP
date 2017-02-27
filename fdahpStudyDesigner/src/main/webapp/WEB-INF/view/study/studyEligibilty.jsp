
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>

<div class="right-content">
  <!--  Start top tab section-->
  <form:form data-toggle="validator" action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyEligibilty.do" id="eleFormId">
	  <div class="right-content-head">        
	      <div class="text-right">
	          <div class="black-md-f text-uppercase dis-line pull-left line34">Eligibility</div>
	          
	          <div class="dis-line form-group mb-none mr-sm">
	               <button type="button" class="btn btn-default gray-btn">Cancel</button>
	           </div>
	          
	           <div class="dis-line form-group mb-none mr-sm">
	               <button type="submit" class="btn btn-default gray-btn submitEle" actType="save">Save</button>
	           </div>
	
	           <div class="dis-line form-group mb-none">
	               <button type="submit" class="btn btn-primary blue-btn submitEle" actType="mark">Mark as Completed</button>
	           </div>
	       </div>
	  </div>
	        <!--  End  top tab section-->
	      <input type="hidden" value="${eligibility.studyId}" name="studyId" /> 
	      <input type="hidden" value="${eligibility.id}" name="id" /> 
	    <!--  Start body tab section -->
	    <div class="right-content-body">
	       <div class="mb-xlg form-group">
	            <span class="radio radio-info radio-inline p-45">
	               <input type="radio" id="inlineRadio1" value="1" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 1}">checked</c:if>>
	               <label for="inlineRadio1">ID validation only</label>
	           </span>
	           <span class="radio radio-inline p-45">
	               <input type="radio" id="inlineRadio2" value="2" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 2}">checked</c:if>>
	               <label for="inlineRadio2">ID validation & Eligibility Test</label>
	           </span>
	             <span class="radio radio-inline">
	               <input type="radio" id="inlineRadio3" value="3" name="eligibilityMechanism" disabled required <c:if test="${eligibility.eligibilityMechanism eq 3}">checked</c:if>>
	               <label for="inlineRadio3">Eligibility Test only</label>
	           </span>
	           <div class="help-block with-errors red-txt"></div>
	       </div>
	        
	       <div class="blue-md-f mb-md text-uppercase">
	           ID validation 
	       </div>
	       
	       <div>
	           <div class="gray-xs-f mb-xs">Instruction Text</div>
	           <div class="form-group">
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
	   
	   $('.submitEle').click(function() {
	   		$('<input />').attr('type', 'hidden').attr('name', "actionType").attr('value', $(this).attr('actType')) .appendTo('#eleFormId');
			$('#eleFormId').submit();
		});
	});
</script>