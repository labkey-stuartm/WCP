<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!--footer-->
     <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 footer">
          <div class="col-lg-7 col-md-12 col-sm-12 col-xs-12 pad0 txtright termC">
        	Copyright <span class="fontC">&copy;</span> @ 2017 FDA.
          </div>
          <div class="col-lg-5 col-md-12 col-sm-12 col-xs-12 txtright termC pad0">
             <a href="javascript:void(0)" id="terms">Terms Of Use</a> | <a href="javascript:void(0)" id="privacy">Privacy Policy</a>
          </div>
       </div>
<form:form action="/acuityLink/termsAndCondition.do" id="termsForm" name="termsForm" method="post" target="_blank">
</form:form>

<form:form action="/acuityLink/privacyPolicy.do" id="privacyForm" name="privacyForm" method="post" target="_blank">
</form:form>
<script>
	$('#terms').on('click',function(){
		$('#termsForm').submit();
	});
	
	$('#privacy').on('click',function(){
		$('#privacyForm').submit();
	});
</script>
