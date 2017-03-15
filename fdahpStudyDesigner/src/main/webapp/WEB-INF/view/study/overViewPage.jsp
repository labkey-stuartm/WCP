<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
        
        <!-- ============================================================== -->
         <!-- Start right Content here -->
         <!-- ============================================================== --> 
        <div class="right-content">
          <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyOverviewPage.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" role="form" id="overViewFormId"  method="post" autocomplete="off" enctype="multipart/form-data">
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Overview</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn submitEle" actType="save">Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn submitEle" id="completedId" actType="completed" >Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <input type="hidden" value="${studyBo.id}" name="studyId" />
            <input type="hidden" value="" id="buttonText" name="buttonText">
            
            
            <!--  Start body tab section -->
            <div class="right-content-body">
                
             <div class="mt-md">
                 <div class="gray-xs-f mb-xs">Study Video URL (if available <span>e.g: http://www.google.com</span>)</div>
                 <div class="form-group">
                      <input type="text" class="form-control" id="studyMediaLinkId" name="mediaLink" value="${studyBo.mediaLink}"  maxlength="50" pattern="https?://.+" title="Include http://" onfocus="moveCursorToEnd(this)" onclick="moveCursorToEnd(this)">
                      <div class="help-block with-errors red-txt"></div>
                 </div>
              </div>
                
                <!-- Study Section-->
                <div class="overview_section">
                  <div class="panel-group overview-panel" id="accordion">
                      <c:if test="${empty studyPageBos}">     
                            <!-- Start panel-->
                            <div class="panel panel-default">
                             <input type="hidden" name="pageId">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="true">
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold pageCount">Page - 1</div>
                                   <div class="studyCount">${studyBo.name}</div>
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
                                        <div class="gray-xs-f mb-sm">Image <span><img data-toggle="tooltip" data-placement="top" data-html="true" title="" src="/fdahpStudyDesigner/images/icons/tooltip.png" data-original-title="<span class='font24'>.</span> JPEG/PNG<br><span class='font24'>.</span> 255 x 255"></span></div>
                                        <div>
                                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="" class="blue-link removeUrl">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="" type="button" class="btn btn-default gray-btn uploadImgbtn">Upload Image</button>
                                                 <input id="" class="dis-none uploadImg" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL(this);" required data-error="Please select an image.">
                                                 <input type="hidden" class="imagePathCls" name="imagePath" />
                                                 <div class="help-block with-errors red-txt"></div>
                                             </div>
                                          </div>
                                        </div>
                                    </div>
                                     <div class="mt-xlg">
                                       <div class="gray-xs-f mb-xs">Title</div>
                                       <div class="form-group">
                                            <input type="text" class="form-control updateInput" name="title" required maxlength="50" value="${studyBo.name}"/>
                                            <div class="help-block with-errors red-txt"></div>
                                       </div>
                                    </div>
                                     <div class="mt-xlg">
                                        <div class="gray-xs-f mb-xs">Description</div>
                                        <div class="form-group">
                                        <textarea class="editor updateInput"  id="editor1" name="description" required maxlength="1000"></textarea>
                                        	<div class="help-block with-errors red-txt"></div>
                                        </div>
                                    </div>
                                 </div>
                              </div>
                            </div>
                             <!-- End panel-->
                           </c:if>  
                           <c:forEach items="${studyPageBos}" var="studyPageBo" varStatus="spbSt">
                           <!-- Start panel-->
                            <div class="panel panel-default">
                             <input type="hidden" value="${studyPageBo.pageId}" name="pageId">
                              <div class="panel-heading">
                                <div class="panel-title">
                                  <a data-toggle="collapse" data-parent="#accordion" href="#collapse${spbSt.count}" aria-expanded=<c:if test='${spbSt.last}'>"true"</c:if><c:if test='${not spbSt.last}'>"false"</c:if>>
                                    <div class="text-left dis-inline">    
                                   <div class="gray-xs-f mb-xs text-uppercase text-weight-bold pageCount">Page - ${spbSt.count}</div>
                                   <div class="studyCount">${studyPageBo.title}</div>
                                   </div>
                                    <div class="text-right dis-inline pull-right">
                                        <span class="sprites_icon delete mt-sm"></span>
                                        <span class="vertical-align-sup ml-lg "><img class="arrow" src="/fdahpStudyDesigner/images/icons/slide-down.png" /></span>
                                    </div>                                    
                                  </a>
                                </div>
                              </div>
                              <div id="collapse${spbSt.count}" class="panel-collapse collapse <c:if test='${spbSt.last}'>in</c:if>">
                                <div class="panel-body">
                                   <div class="mt-xlg">
                                        <div class="gray-xs-f mb-sm">Image <span><img data-toggle="tooltip" data-placement="top" data-html="true" title="" src="/fdahpStudyDesigner/images/icons/tooltip.png" data-original-title="<span class='font24'>.</span> JPEG/PNG<br><span class='font24'>.</span> 255 x 255"></span></div>
                                        <div>
                                          <div class="thumb"><img src="<spring:message code="fda.imgDisplaydPath"/>studypages/${studyPageBo.imagePath}" onerror="this.onerror=null;this.src='/fdahpStudyDesigner/images/dummy-img.jpg';" class="wid100"/></div>
                                          <div class="dis-inline">
                                            <span id="" class="blue-link removeUrl">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                                            <div class="form-group mb-none mt-sm">
                                                 <button id="" type="button" class="btn btn-default gray-btn uploadImgbtn">Upload Image</button>
                                                 <input id="" class="dis-none uploadImg" type="file" name="multipartFiles" accept=".png, .jpg, .jpeg" onchange="readURL(this);" <c:if test="${empty studyPageBo.imagePath}">required</c:if> data-error="Please select an image.">
                                                 <input type="hidden" class="imagePathCls" name="imagePath" value="${studyPageBo.imagePath}"/>
                                                 <div class="help-block with-errors red-txt"></div>
                                             </div>
                                          </div>
                                        </div>
                                    </div>
                                     <div class="mt-xlg">
                                       <div class="gray-xs-f mb-xs">Title</div>
                                       <div class="form-group">
                                            <input type="text" class="form-control updateInput" name="title" value="${studyPageBo.title}" required maxlength="50"/>
                                            <div class="help-block with-errors red-txt"></div>
                                       </div>
                                    </div>
                                     <div class="mt-xlg">
                                        <div class="gray-xs-f mb-xs">Description</div>
                                        <div class="form-group">
	                                        <textarea class="editor updateInput" name="description" id="editor${spbSt.count}" required maxlength="1000" >${studyPageBo.description}</textarea>
	                                        <div class="help-block with-errors red-txt"></div>
                                        </div>
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
      	$(".menuNav li.active").removeClass('active');
	   	$(".menuNav li.third").addClass('active');
	   	
      	$("[data-toggle=tooltip]").tooltip();
		$("#studyMediaLinkId").focus(function(){
			var str = $(this).val().toString();
			if(!str)
			$(this).val("http://"+str);
		}).focusout(function(){
			var str = $(this).val().toString().replace(/\s/g, '');
			if(str == "http://" || str == "https://" || str.length < 7)
			$(this).val("");
		}); 
		     	
        function moveCursorToEnd(obj) {
		  if (!(obj.updating)) {
		    obj.updating = true;
		    var oldValue = obj.value;
		    obj.value = '';
		    setTimeout(function(){ obj.value = oldValue; obj.updating = false; }, 100);
		  }
		}
      	var countId = ${fn:length(studyPageBos)+ 2};
       	// File Upload    
		$(document).on("click",".uploadImgbtn", function(){
		   $(this).parent().find(".uploadImg").click();
		});
          
		// Removing selected file upload image
		$(document).on("click",".removeUrl", function(){
    	  $(this).parent().parent().find(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
    	  $(this).parent().parent().find(".uploadImg").val('').attr('required', 'required');
    	  $(this).parent().parent().find(".imagePathCls").val('');
       	});
      
//       $(document).on("change",".updateInput", function(e){
//     	  if($(this).val()){
//     		  $(this).next('input[type= "hidden"]').val($(this).val());
//     	  } else {
//     		  $(this).next('input[type= "hidden"]').val('default'); 
//     	  }
//       });
      
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
                		  resetValidation($('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form'));
                  });
           	  }
          });
      }
                 
      //deleting panel 
	      var b = $("#accordion").find(".panel-default").length; 
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
// 	      	$('#accordion').find('.studyCount').each(function() {
// 				$(this).text('${studyBo.name} 0'+ b++);	
// 			});
			resetValidation($("#accordion").parents('form'));
			if($('body').find('.panel-collapse.in').length == 0)
				$('body').find('.panel-collapse:last').collapse('show');
        });
          
      
       $("#addpage").click(function(){   
           
          $(".panel-collapse").removeClass('in').collapse('hide');
          $(".delete").show();
          var count = $("#accordion").find('.panel-default').length +1;
          $("#accordion").append("<!-- Start panel-->"+
        		  "<div class='panel panel-default'> <input type='hidden' name='pageId'>"+
        		  "<div class='panel-heading'>"+
        		  "<div class='panel-title'>"+
        		  "<a href='#collapse"+count+"' data-parent=#accordion data-toggle=collapse aria-expanded='true'>"+
        		  "<div class='dis-inline text-left'>"+
        		  "<div class='gray-xs-f mb-xs text-uppercase text-weight-bold pageCount'>PAGE - "+count+"</div>"+
        		  "<div class='studyCount'></div>"+
        		  "</div>"+
        		  "<div class='dis-inline pull-right text-right'>"+
        		  "<span class='mt-sm delete mr-lg sprites_icon'></span> "+
        		  "<span class=vertical-align-sup><img src='/fdahpStudyDesigner/images/icons/slide-down.png'></span>"+
        		  "</div>"+
        		  "</a>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class='collapse panel-collapse' id='collapse"+count+"'>"+
        		  "<div class=panel-body>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-sm'>Image <span><img data-toggle='tooltip' data-placement='top' data-html='true' title='' src='/fdahpStudyDesigner/images/icons/tooltip.png' data-original-title='<span class= font24>.</span> JPEG/PNG<br><span class=font24>.</span> 255 x 255'></span></div>"+
        		  "<div>"+
        		  "<div class=thumb><img src=/fdahpStudyDesigner/images/dummy-img.jpg class=wid100></div>"+
        		  "<div class=dis-inline>"+
        		  "<span class='blue-link removeUrl' >X<a href=# class='blue-link pl-xs txt-decoration-underline'>Remove Image</a></span>"+
        		  "<div class='form-group mb-none mt-sm'>"+
        		  "<button class='btn btn-default gray-btn uploadImgbtn' type=button>Upload Image</button>"+ 
        		  "<input class='dis-none uploadImg' accept='.png, .jpg, .jpeg' name='multipartFiles' onchange=readURL(this) type=file required data-error='Please select an image.'>"+
        		  "<input type='hidden' class='imagePathCls' name='imagePath' /><div class='help-block with-errors red-txt'></div>"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-xs'>Title</div>"+
        		  "<div class=form-group>"+
        		  "<input type='text' class='form-control updateInput' name='title' required maxlength='50'>"+
        		  "<div class='help-block with-errors red-txt'></div>"+
        		  "</div>"+
        		  "</div>"+
        		  "<div class=mt-xlg>"+
        		  "<div class='gray-xs-f mb-xs'>Description</div>"+
        		  "<div class='form-group'><textarea class='editor updateInput' name='description' id='editor"+countId+"' required maxlength='1000'></textarea>"+
        		  "<div class='help-block with-errors red-txt'></div></div>"+
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
                		  resetValidation($('#'+ed.target.id).val(tinyMCE.get(ed.target.id).getContent()).parents('form'));
                  });
           	  }
          });
          resetValidation($("#accordion").parents('form'));
          countId++;
          $("[data-toggle=tooltip]").tooltip();
          $('body').find('.panel-collapse:last').collapse('show').addClass('in');
       });
       $(document).on('show.bs.collapse','.panel-collapse', function(){
       		$('.panel-collapse').not(this).collapse('hide').removeClass('in');
       });
       $(document).on('shown.bs.collapse','.panel-collapse', function(){
       		var $panel = $(this).parent().ScrollTo();
       });
       $('.submitEle').click(function(e) {
// 		   e.preventDefault();
		   $('#actTy').remove();
		   $('<input />').attr('type', 'hidden').attr('name', "actionType").attr('value', $(this).attr('actType')).attr('id', 'actTy') .appendTo('#overViewFormId');
	   		if($(this).attr('actType') == 'save'){
	   			 e.preventDefault();
	   			$('#overViewFormId').validator('destroy');
	   			$('#overViewFormId').submit();
	   		}
		});
		$("#completedId").on('click', function(e){
			e.preventDefault();
			var formValid = true;
      		$('#accordion').find('.panel-default').each(function() {
				var file = $(this).find('input[type=file]').val();
	            var thumbnailImageId = $(this).find('input[type=file]').parent().find('input[name="imagePath"]').val();
	            if(file || thumbnailImageId){
// 	               $(this).find('input[type=file]').parents('.form-group').removeClass('has-error has-danger');
// 	         	   $(this).find('input[type=file]').parents().find(".help-block").empty();
				   $(this).find('input[type=file]').removeAttr('required');
	            } else {
// 	               $(this).find('input[type=file]').parents('.form-group').addClass('has-error has-danger');
// 	         	   $(this).find('input[type=file]').parent().find(".help-block").empty().append('<ul class="list-unstyled"><li>Need to upload image</li></ul>');
// 	         	   if(isFromValid($(this).parents('form'))){
// 	         	  	 e.preventDefault();
// 	         	   }
					formValid = false;
	            }
			});
			if(!isFromValid($(this).parents('form'))) {
				if(!($(this).parents('body').find('.panel-collapse.in').find('.has-error:first').length > 0)){
					$(this).parents('body').find('.panel-collapse.in').collapse('hide').removeClass('in');
				} 
			    $(this).parents('body').find(".has-error:first").parents('.panel-collapse').not('.in').collapse('show');
			}
			if(isFromValid($(this).parents('form')) && formValid){
				$(this).attr('disabled','disabled')
		   		$(this).parents('form').submit();
		    } else {
		    	e.preventDefault();
		    }
//         	$("#buttonText").val('completed');
        });
        /* $(".uploadImg").on('change', function(e){
           var file = $(this).val();
           var thumbnailImageId = $(this).find('input[type=file]').parent().find('input[name="imagePath"]').val();
           if(file || thumbnailImageId){
        	   $(".uploadImg").parent().find(".help-block").empty();
           }
       	}); */
       	
		var _URL = window.URL || window.webkitURL;
		
		  $(document).on('change','.uploadImg',function(e) {
		      var file, img;
		      var thisAttr = this;
		      if ((file = this.files[0])) {
		          img = new Image();
		          img.onload = function() {
		              var ht = this.height;
		              var wds = this.width;
		              if(ht == 255 && wds == 255){
		                  //alert("ok good Images... !!!!");
		                  $(thisAttr).parent().parent().parent().find(".thumb img")
		                  .attr('src', img.src)
		                  .width(66)
		                  .height(66);
		                  $(thisAttr).parent().find('.form-group').removeClass('has-error has-danger');
		                  $(thisAttr).parent().find(".help-block").empty();
		              }else{
		//                   alert("Big Images... !!!!");
		                  $(thisAttr).val();
		                  $(thisAttr).parent().find('.form-group').addClass('has-error has-danger');
		                  $(thisAttr).parent().find(".help-block").empty().append('<ul class="list-unstyled"><li>Failed to upload. Please follow the format specified in info to upload correct thumbnail image</li></ul>');
		                  $(thisAttr).parent().parent().parent().find(".removeUrl").click();
		              }
		          };
		          img.onerror = function() {
		              alert( "not a valid file: " + file.type);
		          };
		          img.src = _URL.createObjectURL(file);
		
		
		      }
				var file = $(this).find('input[type=file]').val();
	            var thumbnailImageId = $(this).find('input[type=file]').parent().find('input[name="imagePath"]').val();
	            if(file || thumbnailImageId){
				   $(this).removeAttr('required');
	            } else {
					$(this).removeAttr('required','required');
	            }
		  });
     });
      
      // Displaying images from file upload 
      function readURL(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();  
          
          
          reader.onload = function (e) {
        	   
//               var  sr = $(input).parent().parent().parent().find(".thumb img").attr('src');
//               alert
          };

          reader.readAsDataURL(input.files[0]);
      }
  }
</script>     