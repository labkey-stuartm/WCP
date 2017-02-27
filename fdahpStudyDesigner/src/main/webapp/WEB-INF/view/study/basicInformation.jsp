<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<body>
<!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
            
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Basic Information</div>
                    
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
                
                <div class="col-md-12 p-none pt-md">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study ID</div>
                        <div class="form-group">
                            <input type="text" class="form-control" required />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study Name</div>
                        <div class="form-group">
                            <input type="text" class="form-control" required />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="gray-xs-f mb-xs">Study full name</div>
                    <div class="form-group">
                        <input type="text" class="form-control" required />
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study Category</div>
                        <div class="form-group">
                           <select class="selectpicker" required>
                              <option>Medication Survey 1</option>
                              <option>A Study for Pregnant Women</option>
                              <option>Medication Survey 2</option>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Research Sponsor</div>
                        <div class="form-group">
                           <select class="selectpicker" required>
                              <option>Medication Survey 1</option>
                              <option>A Study for Pregnant Women</option>
                              <option>Medication Survey 2</option>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Data Partner</div>
                        <div class="form-group">
                           <select class="selectpicker" required>
                              <option>Medication Survey 1</option>
                              <option>A Study for Pregnant Women</option>
                              <option>Medication Survey 2</option>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Tentative Duration in weeks/months</div>
                        <div class="form-group col-md-3 p-none mr-md mb-none">
                            <input type="text" class="form-control" required />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="form-group col-md-3 p-none mb-none">
                           <select class="selectpicker" required>
                              <option>Weeks</option>
                              <option>Weeks</option>
                              <option>Weeks</option>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                 <div class="col-md-12 p-none">
                     <div class="gray-xs-f mb-xs">Data Partner</div>
                     <div>
                        <textarea id="editor" name="area"></textarea>
                     </div>
                </div>
                
                <div class="col-md-12 p-none pt-xlg">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study website</div>
                        <div class="form-group">
                           <input type="text" class="form-control" required />
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study feedback destination inbox email address</div>
                        <div class="form-group">
                          <input type="text" class="form-control" required />
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study type</div>
                        <div class="form-group">
                            <span class="radio radio-info radio-inline p-45">
                                <input type="radio" id="inlineRadio5" value="option1" name="radioInline3">
                                <label for="inlineRadio5">Gateway</label>
                            </span>
                            <span class="radio radio-inline">
                                <input type="radio" id="inlineRadio6" value="option1" name="radioInline3">
                                <label for="inlineRadio6">Standalone</label>
                            </span>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-sm">Study Thumbnail Image</div>
                        <div>
                          <div class="thumb"><img src="images/dummy-img.jpg" class="wid100"/></div>
                          <div class="dis-inline">
                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                            <div class="form-group mb-none mt-sm">
                                 <button id="uploadImgbtn" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                 <input id="uploadImg" class="dis-none" type="file" name="pic" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                             </div>
                          </div>
                        </div>
                    </div>
                </div>
                
                 
                
                
            </div>
            <!--  End body tab section -->
            
        </div>
        <!-- End right Content here -->
</body>
   <script>
        $(document).ready(function(){  
            
            
            // Fancy Scroll Bar
            $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
            
            //wysiwyg editor
            if($("#editor").length > 0){
            tinymce.init({
                selector: "#editor",
                theme: "modern",
                skin: "lightgray",
                height:180,
                plugins: [
                    "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
                    "save contextmenu directionality paste"
                ],
                toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link image | hr removeformat | cut undo redo",
                menubar: false,
                toolbar_items_size: 'small',
                content_style: "div, p { font-size: 13px;letter-spacing: 1px;}"
            });
        }
            
        // File Upload    
         $("#uploadImgbtn").click(function(){
            $("#uploadImg").click();
         });
            
        // Removing selected file upload image
        $("#removeUrl").click(function(){
            $(".thumb img").attr("src","images/dummy-img.jpg");
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
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
        
                 
</script>