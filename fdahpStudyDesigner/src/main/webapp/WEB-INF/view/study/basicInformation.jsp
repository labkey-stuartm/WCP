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
            <form:form action="/adminStudies/saveOrUpdateBasicInfo.do" name="basicInfoFormId" id="basicInfoFormId" method="post" data-toggle="validator" role="form" enctype="multipart/form-data">
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
                         <button type="button" class="btn btn-primary blue-btn" id="completedId">Mark as Completed</button>
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
                            <input type="text" class="form-control aq-inp" maxlength="20" name="customStudyId" id="customStudyId" value="${studyBo.customStudyId}" <c:if test="${not empty studyBo.customStudyId}"> disabled </c:if> onblur="validateStudyId();" required />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study Name</div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="name" value="${studyBo.name}" maxlength="50" required />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="gray-xs-f mb-xs">Study full name</div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="fullName" value="${studyBo.fullName}" maxlength="50" required />
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study Category</div>
                        <div class="form-group">
                           <select class="selectpicker aq-select aq-select-form" id="category" name="category" required="" title="Select">
                              <c:forEach items="${categoryList}" var="category">
                                 <option value="${category.id}" ${studyBo.category eq category ?'selected':''}>${category.value}</option>
                              </c:forEach>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Research Sponsor</div>
                        <div class="form-group">
                           <select class="selectpicker aq-select aq-select-form" required title="Select" name="researchSponsor">
                              <c:forEach items="${researchSponserList}" var="research">
                                 <option value="${research.id}" ${studyBo.researchSponsor eq researchSponsor ?'selected':''} >${research.value}</option>
                              </c:forEach>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Data Partner</div>
                        <div class="form-group">
                           <select class="selectpicker" multiple required="" title="Select" name="dataPartner">
                              <c:forEach items="${dataPartnerList}" var="datapartner">
                                 <option value="${datapartner.id}" <%-- ${fn:contains(studyBo.dataPartner , dataPartner ) ? 'selected' : ''} --%>>${datapartner.value}</option>
                              </c:forEach>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Tentative Duration in weeks/months</div>
                        <div class="form-group col-md-4 p-none mr-md mb-none">
                            <input type="text" class="form-control" name="tentativeDuration" value="${studyBo.tentativeDuration}" required pattern="^([1-9]*)$" data-error="Please enter only number"/>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        <div class="form-group col-md-4 p-none mb-none">
                           <select class="selectpicker" required="" title="Select" name="tentativeDurationWeekmonth">
                              <option value="Days" ${studyBo.tentativeDurationWeekmonth eq 'Days'?'selected':''}>Days</option>
                              <option value="Weeks" ${studyBo.tentativeDurationWeekmonth eq 'Weeks'?'selected':''}>Weeks</option>
                              <option value="Months" ${studyBo.tentativeDurationWeekmonth eq 'Months'?'selected':''}>Months</option>
                              <option value="Years" ${studyBo.tentativeDurationWeekmonth eq 'Years'?'selected':''}>Years</option>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                 <div class="col-md-12 p-none">
                     <div class="gray-xs-f mb-xs">Description</div>
                     <div>
                        <textarea id="editor" name="description">${studyBo.description}</textarea>
                     </div>
                </div>
                
                <div class="col-md-12 p-none pt-xlg">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study website</div>
                        <div class="form-group">
                           <input type="text" class="form-control" name="mediaLink" value="${studyBo.mediaLink}" pattern="https?://.+" title="Include http://" required />
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study feedback destination inbox email address</div>
                        <div class="form-group">
                          <input type="text" class="form-control" name="inboxEmailAddress" value="${studyBo.inboxEmailAddress}" required maxlength="100" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autocomplete="off"/>
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study type</div>
                        <div class="form-group">
                            <span class="radio radio-info radio-inline p-45">
                                <input class="studyType" type="radio" id="inlineRadio5" name="type" value="GT" ${studyBo.type eq 'GT'?'checked':""} required>
                                <label for="inlineRadio5">Gateway</label>
                            </span>
                            <span class="radio radio-inline">
                                <input class="studyType" type="radio" id="inlineRadio6" name="type" value="SD" ${studyBo.type eq 'SD'?'checked':""} required>
                                <label for="inlineRadio6">Standalone</label>
                            </span>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-sm">Study Thumbnail Image</div>
                        <div>
                          <div class="thumb"><img src="/fdahpStudyDesigner/images/dummy-img.jpg" class="wid100"/></div>
                          <div class="dis-inline">
                            <span id="removeUrl" class="blue-link">X<a href="#" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                            <div class="form-group mb-none mt-sm">
                                 <button id="uploadImgbtn" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                 <input id="uploadImg" class="dis-none" type="file" name="pic" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                 <div class="help-block with-errors red-txt"></div>
                             </div>
                          </div>
                        </div>
                    </div>
                </div>
                
                 
                
                
            </div>
            <!--  End body tab section -->
            </form:form>
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
            $(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
         });
        
        
        $("#completedId").click(function(){
            $("#basicInfoFormId").submit();
            var type = $("input[name='type']:checked").val();
            
            if(null != type && type !='' && typeof type != 'undefined' && type == 'GT'){
               var file = $('#uploadImg').val();
               if(null == file && file =='' && typeof file == 'undefined')
               $("#uploadImg").parent().find(".help-block").append('<ul class="list-unstyled"><li>Need to upload image</li></ul>');
            }
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
        
     //Added for image height and width
      var _URL = window.URL || window.webkitURL;

    $("#uploadImg").change(function(e) {
        var file, img;


        if ((file = this.files[0])) {
            img = new Image();
            img.onload = function() {
                var ht = this.height;
                var wds = this.width;
                if(ht <= 255 && wds <=255){
                	$("#uploadImg").parent().find(".help-block").append('');
                }else{
                	$("#uploadImg").parent().find(".help-block").append('<ul class="list-unstyled"><li>Need to upload image of less than 255</li></ul>');
                	$(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
                }
            };
            img.onerror = function() {
                alert( "not a valid file: " + file.type);
            };
            img.src = _URL.createObjectURL(file);
        }
    });
        
        function validateStudyId(){
        	var customStudyId = $("#customStudyId").val();
        	if((null != customStudyId && customStudyId !='' && typeof customStudyId != 'undefined')){
        		//alert("1");
        		$.ajax({
                    url: "/fdahpStudyDesigner/adminStudies/validateStudyId.do",
                    type: "POST",
                    datatype: "json",
                    data: {
                    	customStudyId:customStudyId,
                        "${_csrf.parameterName}":"${_csrf.token}",
                    },
                    success: function emailValid(data, status) {
                        var jsonobject = eval(data);
                        var message = jsonobject.message;
                        //$("#customStudyId").parent().removeClass("has-danger").removeClass("has-error");
                    	$("#customStudyId").parent().find(".help-block").html("");
                        if (message == "SUCCESS") {
                        	//$("#unitNum").parent().addClass("has-error").addClass("has-danger");
                        	$("#customStudyId").parent().find(".help-block").empty();
                        	$("#customStudyId").parent().find(".help-block").append('<ul class="list-unstyled"><li>StudyId : '+customStudyId+' already exist.</li></ul>');
                        } else {
                        	
                        }
                    },
                    error:function status(data, status) {
                    	$("body").removeClass("loading");
                    },
                    global:false
                });
        	}
        }    
        
                 
</script>