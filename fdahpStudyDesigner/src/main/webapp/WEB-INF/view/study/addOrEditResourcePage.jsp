<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<body>
  <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f dis-line pull-left line34"><span class="pr-sm"><a href="javascript:void(0)" id="goToResourceListForm"><img src="/fdahpStudyDesigner/images/icons/back-b.png"/></a></span> Add Resource</div>
                     
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn" id="goToStudyListPage">Cancel</button>
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
                
             <div class="mt-lg">
                <!-- form- input-->
                <div>
                   <div class="gray-xs-f mb-xs">Title</div>
                   <div class="form-group">
                        <input type="text" class="form-control"/>
                   </div>
                </div>
             </div>
                
            <div class="clearfix"></div>
                
            <div class="mt-lg">
                 <span class="radio radio-info radio-inline p-45">
                    <input type="radio" id="inlineRadio1" value="option1" name="radioInline1">
                    <label for="inlineRadio1">Rich Text editor</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio2" value="option1" name="radioInline1">
                    <label for="inlineRadio2">Upload PDF</label>
                </span>    
            </div>
                
            <div class="clearfix"></div>
                
            <div class="mt-lg">
              <textarea id="editor" name="area"></textarea>      
            </div>
                
            <div class="clearfix"></div>
                
            <div class="mt-xlg">
                <div class="gray-xs-f mb-sm">Set a Period of Visibility for this resource?</div>
                 <span class="radio radio-info radio-inline p-45">
                    <input type="radio" id="inlineRadio3" value="option1" name="radioInline2">
                    <label for="inlineRadio3">Yes</label>
                </span>
                <span class="radio radio-inline">
                    <input type="radio" id="inlineRadio4" value="option1" name="radioInline2">
                    <label for="inlineRadio4">No</label>
                </span>    
            </div>
                
            <div class="clearfix"></div>
               
             <div class="mt-xlg">
                <div class="gray-xs-f mb-xs">Select Time Period</div>
                 <span class="radio radio-info radio-inline pr-md">
                    <input type="radio" id="inlineRadio3" value="option1" name="radioInline2">
                    <label for="inlineRadio3">Anchor Date +</label>
                </span>
                 <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                     <input type="text" class="form-control wid70" placeholder="x days"/>
                 </span>
                 <span class="gray-xs-f mb-sm pr-md">
                    to  Anchor Date +
                 </span>
                  <span class="form-group m-none dis-inline vertical-align-middle">
                     <input type="text" class="form-control wid70" placeholder="y days"/>
                 </span>                
             </div>
                
             <div class="mt-xlg">
                 <div class="mb-sm">
                     <span class="radio radio-info radio-inline pr-md">
                        <input type="radio" id="inlineRadio3" value="option1" name="radioInline2">
                        <label for="inlineRadio3">Custom</label>
                    </span>
                </div>
                 <div>
                     <span class="form-group m-none dis-inline vertical-align-middle pr-md">
                         <input id="StartDate" type="text" class="form-control" placeholder="Start Date"/>
                     </span>
                     <span class="gray-xs-f mb-sm pr-md">
                        to 
                     </span>
                      <span class="form-group m-none dis-inline vertical-align-middle">
                         <input id="EndDate" type="text" class="form-control" placeholder="End Date"/>
                     </span>
                 </div>
             </div>
            
              <div class="clearfix"></div>
                
             <div class="mt-xlg">
                <div class="gray-xs-f mb-xs">Text for resource appearance in-app notifications</div>
                 
                 <div class="form-group">
                  <textarea class="form-control" rows="4" id="comment"></textarea>
                 </div>
             </div>
                
            <div class="mt-sm">
                <div class="italic-txt gray-xs-f text-weight-light">You can choose to use the variable <x> in your text, as appropriate, where x = number of days elapsed from Anchor Date.</div>
                <div class="italic-txt gray-xs-f text-weight-light mb-xlg">For example, "Congrats! You are now <x> days pregnant. Check out new articles relevant to this phase of pregnancy, in the Resources section!</div>    
            </div>
            
            
                
            </div>
            <!--  End body tab section -->
            
            
            
            
        </div>
        <!-- End right Content here -->
</body>

<form:form action="/fdahpStudyDesigner/adminStudies/getResourceList.do" name="resourceListForm" id="resourceListForm" method="post">
</form:form>

<form:form action="/fdahpStudyDesigner/adminStudies/studyList.do" name="studyListForm" id="studyListForm" method="post">
</form:form>
<script type="text/javascript">
$(document).ready(function(){
	$('#goToResourceListForm').on('click',function(){
		$('#resourceListForm').submit();
	});
	
	$('#goToStudyListPage').on('click',function(){
		$('#studyListForm').submit();
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
        content_style: "div, p { font-size: 13px;letter-spacing: 1px;}"
    });
}
});

$(function () {
    $('#StartDate').datetimepicker({
        format: 'DD/MM/YYYY'            
    });
});
    
$(function () {
    $('#EndDate').datetimepicker({
        format: 'DD/MM/YYYY'            
    });
});
</script>