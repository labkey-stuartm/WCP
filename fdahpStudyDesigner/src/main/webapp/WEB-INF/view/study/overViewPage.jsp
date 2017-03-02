<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
            <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyOverviewPage.do" data-toggle="validator" role="form" id="settingfoFormId"  method="post" autocomplete="off">
            <input type="hidden" name="buttonText" id="buttonText">
            <input type="hidden" name="id" value="${studyBo.id}">
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Overview</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
                
                <!-- Study Section-->
                <div class="overview_section">
                   <div class="hd_panel cur-pointer">
                       <div class="text-left dis-inline">    
                           <div class="gray-xs-f mb-xs text-uppercase">Page-1</div>
                           <div>Study Title Name 01</div>
                       </div>
                        <div class="text-right dis-inline pull-right">
                            <span class="sprites_icon delete mr-lg mt-sm"></span>
                            <span class="vertical-align-sup"><img src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                        </div>
                    </div>
                    
                    <div class="ct_panel">
                        <div class="mt-xlg">
                            <div>
                                <div class="gray-xs-f mb-sm">Image</div>
                                <div>
                                  <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                  <div class="dis-inline">
                                    <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                    <div class="form-group mb-none mt-sm">
                                         <button type="button" class="btn btn-default gray-btn uploadImgbtn">Upload Image</button>
                                         <input class="dis-none uploadImg" type="file" name="pic" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                     </div>
                                  </div>
                                </div>
                            </div>                        
                        </div>
                        <div class="mt-xlg">
                            <div>
                                <div class="gray-xs-f mb-sm">Image</div>
                                <div>
                                  <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                  <div class="dis-inline">
                                    <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                    <div class="form-group mb-none mt-sm">
                                         <button type="button" class="btn btn-default gray-btn uploadImg">Upload Image</button>
                                         <input class="dis-none uploadImg" type="file" name="pic" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                     </div>
                                  </div>
                                </div>
                            </div>                        
                        </div>
                        

                        <div class="mt-xlg">
                           <div class="gray-xs-f mb-xs">Title</div>
                           <div class="form-group">
                                <input type="text" class="form-control"/>
                           </div>
                        </div>

                        <div class="mt-xlg">
                            <div class="gray-xs-f mb-xs">Description</div>
                            <textarea class="editor" name="area"></textarea>
                        </div>
                    </div>
                    
                </div>
                <!-- End Study Section-->
                <div class="dis-line mt-xlg">
                     <div class="form-group mb-none">
                         <button type="button" class="btn btn-primary blue-btn"><span class="mr-xs">+</span> Add page</button>
                     </div>
                </div>
                
            </div>
            <!--  End body tab section -->
            </form:form>
            
            
            
        </div>
        <!-- End right Content here -->
            <script>
$(document).ready(function(){  
	$(".menuNav li.active").removeClass('active');
    $(".menuNav li.second").addClass('active');
    // File Upload    
       $(".uploadImgbtn").click(function(){
          $(".uploadImg").click();
       });
          
      // Removing selected file upload image
      $("#removeUrl").click(function(){
          $(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
       });
          
      //wysiwyg editor
          if($(".editor").length > 0){
          tinymce.init({
              selector: ".editor",
              theme: "modern",
              skin: "lightgray",
              height:180,
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
          
      //slide toggle
      $(".hd_panel").click(function(){
          $(this).next(".ct_panel").slideToggle("slow");            
       });
         
});
      
      // Displaying images from file upload 
      function readURL(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();  
          
          
          reader.onload = function (e) {
              $('.thumb img')
                  .attr('src', e.target.result)
                  .width(66)
                  .height(66); 
              var  sr = $('.thumb img').attr('src');
              alert
          };

          reader.readAsDataURL(input.files[0]);
      }
  }
      
  var _URL = window.URL || window.webkitURL;

  $(".uploadImg").change(function(e) {
      var file, img;


      if ((file = this.files[0])) {
          img = new Image();
          img.onload = function() {
              var ht = this.height;
              var wds = this.width;
          };
          img.onerror = function() {
              alert( "not a valid file: " + file.type);
          };
          img.src = _URL.createObjectURL(file);


      }

  });
</script>