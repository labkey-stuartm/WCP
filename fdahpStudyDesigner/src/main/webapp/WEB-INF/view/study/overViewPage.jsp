<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
        <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
          <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyOverviewPage.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" role="form" id="basicInfoFormId"  method="post" autocomplete="off" enctype="multipart/form-data">  
            <input type="hidden" value="${studyBo.id}" name="id" />
            <input type="hidden" value="${studyPageBean.studyId}" id="studyId" name="studyId">
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
                         <button type="submit" class="btn btn-primary blue-btn">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
                
                <!-- Study Section-->
                <div class="overview_section">
                  <div class="panel-group overview-panel" id="accordion">
                             
                            <!-- Start panel-->
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold">Page-1</div>
                                   <div>Study Title Name 01</div>
                                   </div>
                                    <div class="text-right dis-inline pull-right">
                                        <span class="sprites_icon delete mt-sm"></span>
                                        <span class="vertical-align-sup ml-lg "><img class="arrow" src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                                    </div>                                    
                                  </a>
                                </div>
                              </div>
                              <div id="collapse1" class="panel-collapse collapse in">
                                <div class="panel-body">
                                   <div class="mt-xlg">
                                        <div class="gray-xs-f mb-sm">Image</div>
                                        <div>
                                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="uploadImgbtn1" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                                 <input id="uploadImg1" class="dis-none" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL1(this);">
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
                            </div>
                             <!-- End panel-->
                      
                            <!-- Start panel-->
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold">Page-2</div>
                                   <div>Study Title Name 01</div>
                                   </div>
                                    <div class="text-right dis-inline pull-right">
                                        <span class="sprites_icon delete mr-lg mt-sm"></span>
                                        <span class="vertical-align-sup"><img src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                                    </div>                                    
                                  </a>
                                </div>
                              </div>
                              <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body">
                                   <div class="mt-xlg">
                                        <div class="gray-xs-f mb-sm">Image</div>
                                        <div>
                                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="uploadImgbtn2" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                                 <input id="" class="dis-none" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" >
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
                            </div>
                             <!-- End panel-->
                             
                    </div> 
                </div>
                <!-- End Study Section-->
                
                <!-- Study Section-->
                <div class="overview_section">
                  <div class="panel-group overview-panel" id="accordion">
                             
                            <!-- Start panel-->
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold">Page-3</div>
                                   <div>Study Title Name 01</div>
                                   </div>
                                    <div class="text-right dis-inline pull-right">
                                        <span class="sprites_icon delete mt-sm"></span>
                                        <span class="vertical-align-sup ml-lg "><img class="arrow" src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                                    </div>                                    
                                  </a>
                                </div>
                              </div>
                              <div id="collapse1" class="panel-collapse collapse in">
                                <div class="panel-body">
                                   <div class="mt-xlg">
                                        <div class="gray-xs-f mb-sm">Image</div>
                                        <div>
                                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="uploadImgbtn1" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                                 <input id="uploadImg1" class="dis-none" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL1(this);">
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
                            </div>
                             <!-- End panel-->
                      
                            <!-- Start panel-->
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold">Page-1</div>
                                   <div>Study Title Name 01</div>
                                   </div>
                                    <div class="text-right dis-inline pull-right">
                                        <span class="sprites_icon delete mr-lg mt-sm"></span>
                                        <span class="vertical-align-sup"><img src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                                    </div>                                    
                                  </a>
                                </div>
                              </div>
                              <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body">
                                   <div class="mt-xlg">
                                        <div class="gray-xs-f mb-sm">Image</div>
                                        <div>
                                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="uploadImgbtn2" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                                 <input id="uploadImg2" class="dis-none" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL2(this);">
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
                            </div>
                             <!-- End panel-->
                             
                    </div> 
                </div>
                <!-- End Study Section-->
    
                <div class="dis-line mt-xlg">
                     <div class="form-group mb-none">
                         <button id="addpage" type="button" class="btn btn-primary blue-btn"><span class="mr-xs">+</span> Add page</button>
                     </div>
                </div>
                
            </div>
            <!--  End body tab section -->
            </form:form>
             
            
            
        </div>
 <!-- End right Content here -->
     <script>
        $(document).ready(function(){  
            
            // Fancy Scroll Bar
            $(".left-content").niceScroll({cursorcolor:"#95a2ab",cursorborder:"1px solid #95a2ab"});
            $(".right-content-body").niceScroll({cursorcolor:"#d5dee3",cursorborder:"1px solid #d5dee3"});
            
         // File Upload    
         $("#uploadImgbtn1").click(function(){
            $("#uploadImg1").click();
         });
         
         $("#uploadImgbtn2").click(function(){
             $("#uploadImg2").click();
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
                   
        //toggle collapse
        $(".vertical-align-sup").click(function(){
          $(this).children(".panel-collapse").collapse('show');
          $(this).siblings().children(".panel-collapse").collapse('hide');
        });
            
        //deleting panel 
              
         $(".delete").click(function(){
          var a = $(".overview-panel > div").length;           
          if(a > 1){
              $(".delete").show();
              $(this).parents(".panel-default").remove();
          }
          var b = $(".overview-panel > div").length; 
          if(b==1){            
            $(".delete").hide();  
          }else if(b>=4){
             $("#addpage").show(); 
          }
        
        });
            
        
         $("#addpage").click(function(){   

            $("#accordion").append("<!-- Start panel--><div class='panel panel-default'><div class=panel-heading><div class=panel-title><a href=#collapse2 data-parent=#accordion data-toggle=collapse><div class='dis-inline text-left'><div class='gray-xs-f mb-xs text-uppercase text-weight-bold'>Page-1</div><div>Study Title Name 01</div></div><div class='dis-inline pull-right text-right'><span class='mt-sm delete mr-lg sprites_icon'></span> <span class=vertical-align-sup><img src=/fdahpStudyDesigner/images/icons/slide-down.png></span></div></a></div></div><div class='collapse panel-collapse'id=collapse2><div class=panel-body><div class=mt-xlg><div class='gray-xs-f mb-sm'>Image</div><div><div class=thumb><img src=/fdahpStudyDesigner/images/dummy-img.jpg class=wid100></div><div class=dis-inline><span class=blue-link id=removeUrl>X<a href=# class='blue-link pl-xs txt-decoration-underline'>Remove Image</a></span><div class='form-group mb-none mt-sm'><button class='btn btn-default gray-btn'id=uploadImgbtn type=button>Upload Image</button> <input class=dis-none accept='.png, .jpg, .jpeg'id=uploadImg name=pic onchange=readURL(this) type=file></div></div></div></div><div class=mt-xlg><div class='gray-xs-f mb-xs'>Title</div><div class=form-group><input class=form-control></div></div><div class=mt-xlg><div class='gray-xs-f mb-xs'>Description</div><textarea class=editor name=area></textarea></div></div></div></div><!-- End panel-->");

            var c = $(".overview-panel > div").length;
            if(c==5){
                $("#addpage").hide();
            }


         });
        
       
           
       });
        
        // Displaying images from file upload 
        function readURL1(input) {
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
        
    function readURL2(input) {
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

    $("#uploadImg1").change(function(e) {
        var file, img;


        if ((file = this.files[0])) {
            img = new Image();
            img.onload = function() {
                var ht = this.height;
                var wds = this.width;
                /* if(ht <= 255 && wds <=255){
                    alert("ok good Images... !!!!");
                }else{
                    alert("Big Images... !!!!");
                } */
            };
            img.onerror = function() {
                alert( "not a valid file: " + file.type);
            };
            img.src = _URL.createObjectURL(file);


        }

    });
    
    $("#uploadImg2").change(function(e) {
        var file, img;


        if ((file = this.files[0])) {
            img = new Image();
            img.onload = function() {
                var ht = this.height;
                var wds = this.width;
                /* if(ht <= 255 && wds <=255){
                    alert("ok good Images... !!!!");
                }else{
                    alert("Big Images... !!!!");
                } */
            };
            img.onerror = function() {
                alert( "not a valid file: " + file.type);
            };
            img.src = _URL.createObjectURL(file);


        }

    });
        
                 
 </script>