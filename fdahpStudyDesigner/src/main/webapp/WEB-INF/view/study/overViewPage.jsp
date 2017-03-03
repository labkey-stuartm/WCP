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
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Overview</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="submit" class="btn btn-default gray-btn">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn">Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <input type="hidden" value="${studyBo.id}" name="studyId" />
            <input type="hidden" value="" id="buttonText" name="buttonText">
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
                
                <!-- Study Section-->
                <div class="overview_section">
                  <div class="panel-group overview-panel" id="accordion">
                      <c:if test="${empty studyPageBos}">     
                            <!-- Start panel-->
                            <div class="panel panel-default">
                             <input type="hidden" name="pageId">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold pageCount">Page - 1</div>
                                   <div class="studyCount">Study Title Name 01</div>
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
                                                 <button id="" type="button" class="btn btn-default gray-btn uploadImgbtn">Upload Image</button>
                                                 <input id="" class="dis-none uploadImg" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                                 <input type="hidden" name="imagePath" />
                                             </div>
                                          </div>
                                        </div>
                                    </div>
                                     <div class="mt-xlg">
                                       <div class="gray-xs-f mb-xs">Title</div>
                                       <div class="form-group">
                                            <input type="text" class="form-control updateInput" name="title"/>
                                            <input type="hidden"  value="default"/>
                                       </div>
                                    </div>
                                     <div class="mt-xlg">
                                        <div class="gray-xs-f mb-xs">Description</div>
                                        <textarea class="editor updateInput" name="area" id="editId1" name="description"></textarea>
                                        <input type="hidden"  value="default"/>
                                    </div>
                                 </div>
                              </div>
                            </div>
                             <!-- End panel-->
                           </c:if>  
                           <c:forEach items="${studyPageBos}" var="studyPageBo">
                           <!-- Start panel-->
                            <div class="panel panel-default">
                             <input type="hidden" value="${studyPageBo.pageId}" name="pageId">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold pageCount">Page - 1</div>
                                   <div class="studyCount">Study Title Name 01</div>
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
                                          <div class="thumb"><img src="/fdahpStudyDesigner/studypages/${studyPageBo.imagePath}" onerror="this.onerror=null;this.src='/fdahpStudyDesigner/images/dummy-img.jpg';" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="" type="button" class="btn btn-default gray-btn uploadImgbtn">Upload Image</button>
                                                 <input id="" class="dis-none uploadImg" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                                 <input type="hidden" name="imagePath" value="${studyPageBo.imagePath}"/>
                                             </div>
                                          </div>
                                        </div>
                                    </div>
                                     <div class="mt-xlg">
                                       <div class="gray-xs-f mb-xs">Title</div>
                                       <div class="form-group">
                                            <input type="text" class="form-control updateInput" name="title" value="${studyPageBo.title}"/>
                                            <input type="hidden"  value="${empty studyPageBo.title? default : studyPageBo.title}"/>
                                       </div>
                                    </div>
                                     <div class="mt-xlg">
                                        <div class="gray-xs-f mb-xs">Description</div>
                                        <textarea class="editor updateInput" name="description"" id="editId1">${studyPageBo.description}</textarea>
                                        <input type="hidden"  value="${empty studyPageBo.description? default : studyPageBo.description}"/>
                                    </div>
                                 </div>
                              </div>
                            </div>
                             <!-- End panel-->
                           </c:forEach>
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
       // File Upload    
       $(document).on("click",".uploadImgbtn", function(){
          $(this).next(".uploadImg").click();
       });
          
      // Removing selected file upload image
      $(document).on("click",".removeUrl", function(){
    	  $(this).parent().parent().find(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
    	  $(this).parent().parent().find(".uploadImg").val('');
       });
      
      $(document).on("change",".updateInput", function(e){
    	  if($(this).val()){
    		  $(this).next('input[type= "hidden"]').val($(this).val());
    	  } else {
    		  $(this).next('input[type= "hidden"]').val('default'); 
    	  }
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
              content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
              setup : function(ed) {
                  ed.on('change', function(ed) {
                	  if(tinyMCE.get(ed.target.id).getContent()){
                		  $('#'+ed.target.id).next('input[type= "hidden"]').val(tinyMCE.get(ed.target.id).getContent());
                	  } else {
                		  $('#'+ed.target.id).next('input[type= "hidden"]').val('default');
                	  }
                  });
           	  }
          });
      }
                 
      //toggle collapse
      $(document).on("click", ".vertical-align-sup", function(){
//     	$(this).parents(".panel-group").find("div[aria-expanded=true]").collapse('hide');
//         $(this).parents(".panel-default").find(".panel-collapse").collapse('show');
//         $(this).siblings().children(".panel-collapse").collapse('hide');
      });
          
      //deleting panel 
	      var b = $("#accordion").find(".overview-panel").length; 
	      if(b==1){            
	        $(".delete").hide();  
	      }else if(b > 4){
	         $("#addpage").hide(); 
	      }
       $(document).on("click", ".delete", function(){
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
	        var a = 1;
	        var b = 1;
	      	$('#accordion').find('.pageCount').each(function() {
				$(this).text('PAGE - '+ a++);	
			});
	      	$('#accordion').find('.studyCount').each(function() {
				$(this).text('Study Title Name 0'+ b++);	
			});
        });
          
      
       $("#addpage").click(function(){   
           
          $(".panel-collapse").collapse('hide');
          $(".delete").show();
          var count = $("#accordion").find('.panel-default').length +1;
          $("#accordion").append("<!-- Start panel-->"+
        		  "<div class='panel panel-default'> <input type='hidden' name='pageId'>"+
        		  "<div class='panel-heading'>"+
        		  "<div class='panel-title'>"+
        		  "<a href='#collapse"+count+"' data-parent=#accordion data-toggle=collapse>"+
        		  "<div class='dis-inline text-left'>"+
        		  "<div class='gray-xs-f mb-xs text-uppercase text-weight-bold pageCount'>PAGE - "+count+"</div>"+
        		  "<div class='studyCount'>Study Title Name 0"+count+"</div>"+
        		  "</div>"+
        		  "<div class='dis-inline pull-right text-right'>"+
        		  "<span class='mt-sm delete mr-lg sprites_icon'></span> "+
        		  "<span class=vertical-align-sup><img src='/fdahpStudyDesigner/images/icons/slide-down.png'></span>"+
        		  "</div>"+
        		  "</a>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class='collapse panel-collapse in' id='collapse"+count+"'>"+
        		  "<div class=panel-body>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-sm'>Image</div>"+
        		  "<div>"+
        		  "<div class=thumb><img src=/fdahpStudyDesigner/images/dummy-img.jpg class=wid100></div>"+
        		  "<div class=dis-inline>"+
        		  "<span class='blue-link removeUrl' >X<a href=# class='blue-link pl-xs txt-decoration-underline'>Remove Image</a></span>"+
        		  "<div class='form-group mb-none mt-sm'>"+
        		  "<button class='btn btn-default gray-btn uploadImgbtn' type=button>Upload Image</button>"+ 
        		  "<input class='dis-none uploadImg' accept='.png, .jpg, .jpeg' name='multipartFiles' onchange=readURL(this) type=file>"+
        		  "<input type='hidden' name='imagePath' />"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-xs'>Title</div>"+
        		  "<div class=form-group>"+
        		  "<input type='text' class='form-control updateInput' name='title'>"+
        		  "<input type='hidden'  value='default'/>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-xs'>Description</div>"+
        		  "<textarea class='editor updateInput' name='description' id='editId"+count+"'></textarea>"+
        		  "<input type='hidden'  value='default'/>"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "<!-- End panel-->");
          var c = $(".overview-panel > div").length;
          if(c==5){
              $("#addpage").hide();
          }

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
              content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
              setup : function(ed) {
                  ed.on('change', function(ed) {
                	  if(tinyMCE.get(ed.target.id).getContent()){
                		  $('#'+ed.target.id).next('input[type= "hidden"]').val(tinyMCE.get(ed.target.id).getContent());
                	  } else {
                		  $('#'+ed.target.id).next('input[type= "hidden"]').val('default');
                	  }   
                  });
           	  }
          });
       });
      
     });
      
      // Displaying images from file upload 
      function readURL(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();  
          
          
          reader.onload = function (e) {
        	  $(input).parent().parent().parent().find(".thumb img")
                  .attr('src', e.target.result)
                  .width(66)
                  .height(66); 
              var  sr = $(input).parent().parent().parent().find(".thumb img").attr('src');
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
              if(ht <= 255 && wds <=255){
                  alert("ok good Images... !!!!");
              }else{
                  alert("Big Images... !!!!");
              }
          };
          img.onerror = function() {
              alert( "not a valid file: " + file.type);
          };
          img.src = _URL.createObjectURL(file);


      }

  });
</script>     