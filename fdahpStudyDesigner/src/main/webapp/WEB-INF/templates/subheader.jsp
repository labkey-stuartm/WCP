<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>

<!-- create Study Section Start -->
<div id="createStudyId" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md tit_con" style="display: none;">
     <div class="md-container">
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
              <span class="mr-xs"><a href="#"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span> Create Study
            </div>
         </div>
         <div class="text-center"> 
       		<div class="" id="alertMsg"></div>
        </div>
    </div>
</div>
<!-- create Study Section End -->

<!-- StudyList Section Start-->

<div id="studyListId" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 p-none mt-md mb-md" style="display: none;">
     <div class="md-container">
         <div class="col-sm-12 col-md-12 col-lg-12 p-none">
            <div class="black-lg-f">
                Manage Studies
            </div>          
             <c:if test="${fn:contains(sessionObject.userPermissions,'ROLE_CREATE_MANAGE_STUDIES')}">
             <div class="dis-line pull-right ml-md">
                 <div class="form-group mb-none">
                     <button type="button" class="btn btn-primary blue-btn addEditStudy"><span class="mr-xs">+</span> Create Study</button>
                 </div>
			</div>
            </c:if>
</div>
</div>
</div>
<!-- StudyList Section End-->


<form:form action="/fdahpStudyDesigner/adminStudies/viewBasicInfo.do" id="addEditStudyForm" name="addEditStudyForm" method="post">
</form:form> 
<script type="text/javascript">
$(document).ready(function(){
$('.addEditStudy').on('click',function(){
	$('#addEditStudyForm').submit();
 });
<c:if test="${studyListId eq true}">
   $('#studyListId').show();
</c:if>
// 	<c:if test="${createStudyId eq true}">
// 	$('#createStudyId').show();
// 	</c:if>
	var sucMsg = '${sucMsg}';
	if(sucMsg.length > 0){
		$("#alertMsg").removeClass('e-box').addClass('s-box').html(sucMsg);
		setTimeout(hideDisplayMessage, 4000);
	}
	var errMsg = '${errMsg}';
	if(errMsg.length > 0){
		$("#alertMsg").removeClass('s-box').addClass('e-box').html(errMsg);
	   	setTimeout(hideDisplayMessage, 4000);
	}
});
function hideDisplayMessage(){
	$('#alertMsg').hide();
}
</script>
