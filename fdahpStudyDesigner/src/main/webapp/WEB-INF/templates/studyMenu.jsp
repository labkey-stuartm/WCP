<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.fdahpStudyDesigner.util.SessionObject"%>

<!-- Start left Content here -->
         <!-- ============================================================== -->        
         <div class="col-sm-2 col-lc p-none">
            <div class="left-content-container">
                <ul class="menuNav">
                <li>Create Study</li>
                    <li class="first active">
                    	1.  Basic Information 
	                    <c:if test="${studyBo.studySequenceBo.basicInfo}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="second commonCls">
                    	2.  Settings
                    	<c:if test="${studyBo.studySequenceBo.settingAdmins}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="third commonCls">
                    	3.  Overview
                    	<c:if test="${studyBo.studySequenceBo.overView}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="fourth commonCls">
                    	4.  Eligibility
                    	<c:if test="${studyBo.studySequenceBo.eligibility}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="fifth commonCls">
                    	5.  Consent
                    	 <c:if test="${studyBo.studySequenceBo.consentEduInfo && studyBo.studySequenceBo.comprehensionTest && studyBo.studySequenceBo.eConsent}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="sub fifthConsent commonCls"><span class="dot"></span> Consent / Edu. Info
                    	<c:if test="${studyBo.studySequenceBo.consentEduInfo}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
	                </li>
                    <li class="sub fifthComre commonCls cursor-none-without-event"><span class="dot"></span> Comprehension Test
                    	<c:if test="${studyBo.studySequenceBo.comprehensionTest}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="sub fifthConsentReview commonCls"><span class="dot"></span> Review and E-Consent
                    	<c:if test="${studyBo.studySequenceBo.eConsent}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="sixth commonCls cursor-none-without-event">
                    	6.  Study Exercises
                    	<c:if test="${studyBo.studySequenceBo.studyExcQuestionnaries}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if> 
                    </li>
                    <li class="sub sixthQuestionnaires commonCls slideUp cursor-none-without-event"><span class="dot"></span> Questionnaires</li>
                    <li class="sub sixthTask commonCls slideUp cursor-none-without-event"><span class="dot"></span>Active Tasks</li>
                    <%-- <li class="seventh commonCls slideUp">
                    	7.  Study Dashboard
                    	<c:if test="${studyBo.studySequenceBo.eConsent}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    <li class="eight commonCls slideUp">
                    	8.  Miscellaneous
                    	<c:if test="${studyBo.studySequenceBo.miscellaneousResources && studyBo.studySequenceBo.miscellaneousNotification}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if>
                    </li>
                    
                    <li class="sub eighthResources commonCls slideUp"><span class="dot"></span> Resources 
                    <c:if test="${studyBo.studySequenceBo.miscellaneousResources}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
                    </c:if>
                    </li>
                    
                    <li class="sub eigthNotification commonCls  slideUp"><span class="dot"></span> Notifications 
                    	<c:if test="${studyBo.studySequenceBo.miscellaneousNotification}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
                    	</c:if>
                    </li> --%>
                    <li class="eighthResources commonCls slideUp">
                    	7. Resources 
                    <c:if test="${studyBo.studySequenceBo.miscellaneousResources}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
                    </c:if>
                    </li>
                    <li class="eigthNotification commonCls  slideUp cursor-none-without-event">
                    	8. Notifications 
                    	<c:if test="${studyBo.studySequenceBo.miscellaneousNotification}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
                    	</c:if>
                    </li>
                    <li class="nine commonCls slideUp cursor-none-without-event">
                    	9.  Checklist
                    	<%-- <c:if test="${studyBo.studySequenceBo.studyExcActiveTask}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if> --%>
                    </li>
                    <li class="ten commonCls slideUp cursor-none-without-event">
                    	10.  Actions
                    	<%-- <c:if test="${studyBo.studySequenceBo.basicInfo}">
	                    	<span class="sprites-icons-2 tick pull-right mt-xs" ></span>
	                    </c:if> --%>
                    </li>                 
                </ul>
            </div>
        </div>
        
        <!-- End left Content here -->
<script type="text/javascript">
$(document).ready(function(){
$("#rowId").addClass('lc-gray-bg');	
   $('#createStudyId').show();
   // Fancy Scroll Bar
   // $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
   // $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
   $("#myNavbar li.studyClass").addClass('active');
   
   $('.cancelBut').click(function() {
	   $('.cancelBut').prop('disabled', true);
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
		        	var a = document.createElement('a');
		    		a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
		    		document.body.appendChild(a).click();
		        }else{
		        	$('.cancelBut').prop('disabled', false);
		        }
		    }
			});
// 	    var a = document.createElement('a');
// 		a.href = "/fdahpStudyDesigner/adminStudies/studyList.do";
// 		document.body.appendChild(a).click();
	});
   
   var a = document.createElement('a');
   $('.first').click(function() {
		a.href = "/fdahpStudyDesigner/adminStudies/viewBasicInfo.do";
		document.body.appendChild(a).click();
	});
   
   <c:if test="${not empty studyBo.studySequenceBo && studyBo.studySequenceBo.basicInfo}">
	   $('.second').click(function() {
			a.href = "/fdahpStudyDesigner/adminStudies/viewSettingAndAdmins.do";
			document.body.appendChild(a).click();
		});
	   <c:if test="${studyBo.studySequenceBo.settingAdmins}">
		   $('.third').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/overviewStudyPages.do";
				document.body.appendChild(a).click();
			});
		   $('.fourth').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/viewStudyEligibilty.do";
				document.body.appendChild(a).click();
			});
			$('.fifth').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do";
				document.body.appendChild(a).click();
			});
			$('.fifthConsent').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do";
				document.body.appendChild(a).click();
			});
// 			$('.fifthComre').click(function() {
// 				a.href = "/fdahpStudyDesigner/adminStudies/comprehensionQuestionList.do";
// 				document.body.appendChild(a).click();
// 			});
			$('.fifthConsentReview').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/consentReview.do";
				document.body.appendChild(a).click();
			});
// 			$('.sixth , .sixthQuestionnaires').click(function() {
// 				a.href = "/fdahpStudyDesigner/adminStudies/viewStudyQuestionnaires.do";
// 				document.body.appendChild(a).click();
// 			});
// 			$('.sixthTask').click(function() {
// 				a.href = "/fdahpStudyDesigner/adminStudies/viewStudyActiveTasks.do";
// 				document.body.appendChild(a).click();
// 			});
			/* $('.eight').click(function() {
				a.href = "/fdahpStudyDesigner/adminStudies/getResourceList.do";
				document.body.appendChild(a).click();
			}); */
			$('.eighthResources').click(function() {
				$('.eighthResources').addClass('cursor-none');
				a.href = "/fdahpStudyDesigner/adminStudies/getResourceList.do";
				document.body.appendChild(a).click();
			});
			/* $('.eigthNotification').click(function() {
				$('.eigthNotification').addClass('cursor-none');
				a.href = "/fdahpStudyDesigner/adminStudies/viewStudyNotificationList.do";
				document.body.appendChild(a).click();
			}); */
		</c:if>
   </c:if>
   <c:if test="${(empty studyBo.studySequenceBo) || not studyBo.studySequenceBo.basicInfo}">
   		$('.commonCls').addClass('cursor-none-without-event');
   </c:if>
   <c:if test="${studyBo.studySequenceBo.basicInfo && not studyBo.studySequenceBo.settingAdmins}">
		$('.commonCls').not('.second').addClass('cursor-none-without-event');
   </c:if>
   $(window).on('load resize', function(){    
	   
		rtime1 = new Date();
	    if (timeout1 === false) {
	        timeout1 = true;
	        setTimeout(resizeend1, delta1);
	    }
	    
	});
});
//Internet Explorer 6-11
var isIE = /*@cc_on!@*/false || !!document.documentMode;

// Edge 20+
var isEdge = !isIE && !!window.StyleMedia;
if(isIE || isEdge) {
	$(window).on('load resize', function(){    
		   
		rtime1 = new Date();
	    if (timeout1 === false) {
	        timeout1 = true;
	        setTimeout(resizeend1, delta1);
	    }
	    
	});
}
var rtime1;
var timeout1 = false;
var delta1 = 200;

function resizeend1() {
    if (new Date() - rtime1 < delta1) {
        setTimeout(resizeend1, delta1);
    } else {
        timeout1 = false;
        slideUpStudyMenu();
    }               
}
function slideUpStudyMenu() {
	$(".slideUp.active").ScrollTo();
}
</script>
