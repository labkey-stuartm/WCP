<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== --> 
<div class="right-content">
   <!--  Start top tab section-->
   <div class="right-content-head">
      <div class="text-right">
         <div class="black-md-f text-uppercase dis-line pull-left line34"><span class="pr-sm"><img src="images/icons/back-b.png"/></span> Add Question</div>
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
            <input type="text" class="form-control" required />
            <div class="help-block with-errors red-txt"></div>
         </div>
      </div>
      <!-- Answer option section-->
      <div class="col-md-10 col-lg-7 p-none">
         <!-- Bending Answer options -->
         <div id="ans-opts">                    
         </div>
         <div>
            <div class="col-md-7 pl-none">
               <div class="gray-xs-f mb-xs">Answer Options</div>
               <div class="form-group">
                  <input type="text" class="form-control" required />
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="col-md-4">
               <div class="gray-xs-f mb-xs">Correct Answer</div>
               <div>
                  <select class="selectpicker" title="Select" required>
                     <option>Yes</option>
                     <option>No</option>
                  </select>
                  <div class="help-block with-errors red-txt"></div>
               </div>
            </div>
            <div class="col-md-1">
               <div class="gray-xs-f mb-xs">&nbsp;</div>
               <div class="clearfix"></div>
               <div class="mt-xs">
                  <span id="ans-btn" class="study-addbtn ml-none">+</span>
               </div>
            </div>
         </div>
      </div>
      <div class="clearfix"></div>
      <!-- -->
      <div>
         <div class="gray-xs-f mb-sm">Choose structure of the correct answer</div>
         <div>
            <span class="radio radio-info radio-inline p-45">
            <input type="radio" id="inlineRadio1" value="option1" name="radioInline1">
            <label for="inlineRadio1">Any of the ones marked as Correct Answers</label>
            </span>
            <span class="radio radio-inline p-45">
            <input type="radio" id="inlineRadio2" value="option1" name="radioInline1">
            <label for="inlineRadio2">All the ones marked as Correct Answers</label>
            </span>
         </div>
      </div>
   </div>
   <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
$(document).ready(function() {
    // Fancy Scroll Bar
    $(".left-content").niceScroll({
        cursorcolor: "#95a2ab",
        cursorborder: "1px solid #95a2ab"
    });
    $(".right-content-body").niceScroll({
        cursorcolor: "#d5dee3",
        cursorborder: "1px solid #d5dee3"
    });
    //Adding Answers option
    $('#ans-btn').click(function() {
        $('#ans-opts').append("<div class='col-md-7 pl-none'>"
        +"<div class='form-group'>"
	        +"<input type='text' class='form-control' required/>"
	        +"<div class='help-block with-errors red-txt'></div>"
	        +"</div>"
        +"</div>"
        +"<div class='col-md-4'><div>"
	        +"<select class='selectpicker wid100' title='Select' required>"
		        +"<option>Yes</option>"
		        +"<option>No</option>"
	        +"</select>"
	        +"<div class='help-block with-errors red-txt'></div>"
	        +"</div>"
        +"</div>"
        +"<div class='col-md-1'>"
        +"<div class='clearfix'></div>"
        +"<div class='mt-xs'>"
	        +"<span class='cur-pointer'>"
	        +"<img id='del' src='images/icons/delete-g.png'/>"
	        +"</span>"
        +"</div>"
        +"</div>");
    });

    //Removing answer option
    $("#del").click(function() {
        $(this).parents().find("#ans-opts").remove();
    });

});
</script>