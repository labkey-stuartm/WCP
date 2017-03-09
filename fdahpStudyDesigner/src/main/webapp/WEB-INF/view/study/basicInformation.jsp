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
        
            <form:form action="/fdahpStudyDesigner/adminStudies/saveOrUpdateBasicInfo.do?${_csrf.parameterName}=${_csrf.token}" data-toggle="validator" role="form" id="basicInfoFormId"  method="post" autocomplete="off" enctype="multipart/form-data">
            <!--  Start top tab section-->
            <div class="right-content-head">        
                <div class="text-right">
                    <div class="black-md-f text-uppercase dis-line pull-left line34">Basic Information</div>
                    
                    <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
                     </div>
                    
                     <div class="dis-line form-group mb-none mr-sm">
                         <button type="button" class="btn btn-default gray-btn actBut" id="saveId" <c:if test="${not studyBo.viewPermission }">disabled</c:if> >Save</button>
                     </div>

                     <div class="dis-line form-group mb-none">
                         <button type="submit" class="btn btn-primary blue-btn actBut" id="completedId" <c:if test="${not studyBo.viewPermission }">disabled</c:if>>Mark as Completed</button>
                     </div>
                 </div>
            </div>
            <!--  End  top tab section-->
            <input type="hidden" id="sId" value="${studyBo.id}" name="id" />
            <input type="hidden" value="" id="buttonText" name="buttonText"> 
            <!--  Start body tab section -->
            <div class="right-content-body">
                
                <div class="col-md-12 p-none pt-md">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study ID</div>
                        <div class="form-group">
                            <input type="text" class="form-control aq-inp studyIdCls<c:if test="${studyBo.studySequenceBo.actions}"> cursor-none </c:if>" maxlength="20"  name="customStudyId"  id="customStudyId" value="${studyBo.customStudyId}"
                             <c:if test="${studyBo.studySequenceBo.actions}"> readonly</c:if>  required pattern="[a-zA-Z0-9]+" data-pattern-error="Space and special characters are not allowed."/>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study Name</div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="name" value="${studyBo.name}" maxlength="50" required pattern="[a-zA-Z0-9\s]+" data-pattern-error="Special characters are not allowed." />
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="gray-xs-f mb-xs">Study full name</div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="fullName" value="${studyBo.fullName}" maxlength="50" required pattern="[a-zA-Z0-9\s]+" data-pattern-error="Special characters are not allowed." />
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study Category</div>
                        <div class="form-group">
                           <select class="selectpicker aq-select aq-select-form" id="category" name="category" required title="Select">
                              <c:forEach items="${categoryList}" var="category">
                                 <option value="${category.id}" ${studyBo.category eq category.id ?'selected':''}>${category.value}</option>
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
                                 <option value="${research.id}" ${studyBo.researchSponsor eq research.id ?'selected':''} >${research.value}</option>
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
                           <select class="selectpicker" id="dataPartnerId" multiple="multiple" title="Select"  data-none-selected-text="Select"  name="dataPartner" required>
                              <c:forEach items="${dataPartnerList}" var="datapartner">
                                 <option value="${datapartner.id}"  ${fn:contains(studyBo.dataPartner , datapartner.id ) ? 'selected' : ''} >${datapartner.value}</option>
                              </c:forEach>
                            </select>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                        
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Tentative Duration</div>
                        <div class="form-group col-md-4 p-none mr-md mb-none">
                            <input type="text" class="form-control" name="tentativeDuration" value="${studyBo.tentativeDuration}" maxlength="3" required pattern="^(0{0,2}[1-9]|0?[1-9][0-9]|[1-9][0-9][0-9])$" data-pattern-error="Please enter valid number."/>
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
                        <div class="gray-xs-f mb-xs">Study website <span>(e.g: http://www.google.com)</span></div>
                        <div class="form-group">
                           <input type="text" class="form-control" id="studyWebsiteId" name="studyWebsite" value="${studyBo.studyWebsite}" pattern="https?://.+" title="Include http://" onfocus="this.value = this.value;" required />
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-xs">Study feedback destination inbox email address</div>
                        <div class="form-group">
                          <input type="text" class="form-control" name="inboxEmailAddress" value="${studyBo.inboxEmailAddress}" required maxlength="100" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autocomplete="off" data-pattern-error="E-mail address is invalid" />
                           <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-12 p-none">
                    <div class="col-md-6 pl-none">
                        <div class="gray-xs-f mb-xs">Study type</div>
                        <div class="form-group">
                            <span class="radio radio-info radio-inline p-45">
                                <input type="radio" id="inlineRadio5" name="type" value="GT" ${studyBo.type eq 'GT'?'checked':""} required>
                                <label for="inlineRadio5">Gateway</label>
                            </span>
                            <span class="radio radio-inline">
                                <input type="radio" id="inlineRadio6" name="type" value="SD" ${studyBo.type eq 'SD'?'checked':""} required>
                                <label for="inlineRadio6">Standalone</label>
                            </span>
                            <div class="help-block with-errors red-txt"></div>
                        </div>
                    </div>
                    <div class="col-md-6 pr-none">
                        <div class="gray-xs-f mb-sm">Study Thumbnail Image <span><img data-toggle="tooltip" data-placement="top" data-html="true" title="<span class='font24'>.</span> JPEG/PNG<br><span class='font24'>.</span> 255 x 255" src="/fdahpStudyDesigner/images/icons/tooltip.png"/></span></div>
                        <div>
                          <div class="thumb"><img src="<spring:message code="fda.imgDisplaydPath"/>studylogo/${studyBo.thumbnailImage}" onerror="this.onerror=null;this.src='/fdahpStudyDesigner/images/dummy-img.jpg';" class="wid100"/></div>
                          <div class="dis-inline">
                            <span id="removeUrl" class="blue-link">X<a href="javascript:void(0)" class="blue-link txt-decoration-underline pl-xs">Remove Image</a></span>
                            <div class="form-group mb-none mt-sm">
                                 <button id="uploadImgbtn" type="button" class="btn btn-default gray-btn">Upload Image</button>
                                 <input id="uploadImg" class="dis-none" type="file" name="file" accept=".png, .jpg, .jpeg" onchange="readURL(this);">
                                 <input type="hidden" value="${studyBo.thumbnailImage}" id="thumbnailImageId" name="thumbnailImage"/>
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
        	
        	$("#studyWebsiteId").focus(function(){
				var str = $(this).val().toString();
				if(!str)
				$(this).val("http://"+str);
				var strLength = $(this).val().length * 2;
				$(this)[0].setSelectionRange(strLength, strLength);
			}).focusout(function(){
				var str = $(this).val().toString().replace(/\s/g, '');
				if(str == "http://" || str.length < 7)
				$(this).val("");
			});        	
        	
        	$("[data-toggle=tooltip]").tooltip();

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
            $('#uploadImg').val('');
         });
        
        
        $("#completedId").on('click', function(e){
        		e.preventDefault();
        		var type = $("input[name='type']:checked").val();
                if(null != type && type !='' && typeof type != 'undefined' && type == 'GT'){
                   var file = $('#uploadImg').val();
                   var thumbnailImageId = $('#thumbnailImageId').val();
                   if(file || thumbnailImageId){
                	   $("#uploadImg").parent().find(".help-block").empty();
                	   validateStudyId(e, function(st,e){
                       	if(st){
                       		if(isFromValid("#basicInfoFormId")){
                       			 $("#buttonText").val('completed');
                        	  	 $("#basicInfoFormId").submit();
                        	  }
                          }
                  		});
                   } else {
                	   $("#uploadImg").parent().find(".help-block").empty().append('<ul class="list-unstyled"><li>Need to upload image</li></ul>');
                	   if(isFromValid("#basicInfoFormId")){
                	  	 e.preventDefault();
                	   }
                   }
                } else {
                	$("#uploadImg").parent().find(".help-block").empty();
                	validateStudyId(e, function(st,e){
                   	if(st){
                   		if(isFromValid("#basicInfoFormId")){
                   			 $("#buttonText").val('completed');
                    	  	 $("#basicInfoFormId").submit();
                    	  }
                      }
              		});
                }
         });
        $("#uploadImg").on('change', function(e){
        	var type = $("input[name='type']:checked").val();
            if(null != type && type !='' && typeof type != 'undefined' && type == 'GT'){
               var file = $('#uploadImg').val();
               var thumbnailImageId = $('#thumbnailImageId').val();
               if(file || thumbnailImageId){
            	   $("#uploadImg").parent().find(".help-block").empty();
               }
            } else {
            	$("#uploadImg").parent().find(".help-block").empty();
            }
        });
        $('#saveId').click(function(e) {
        	$("#customStudyId").parent().find(".help-block").empty();
        	$('#basicInfoFormId').validator('destroy').validator();
            if(!$('#customStudyId')[0].checkValidity()){
            	$("#customStudyId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>Please fill out this field.</li></ul>');
                return false;
            } else {
            	validateStudyId(e, function(st,event){
            		if(st){
            			$('#basicInfoFormId').validator('destroy');
                    	$("#buttonText").val('save');
                    	$('#basicInfoFormId').submit();
            		}
            	});
            }
		});
        $('.studyIdCls').on('blur',function(){
        	validateStudyId('', function(st, event){
        		
        	});
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
                if(ht == 255 && wds ==255){
                	$("#uploadImg").parent().find(".help-block").append('');
                }else{
                	$("#uploadImg").parent().find(".help-block").append('<ul class="list-unstyled"><li>Failed to upload. Please follow the format specified in info to upload correct thumbnail image.</li></ul>');
                	$(".thumb img").attr("src","/fdahpStudyDesigner/images/dummy-img.jpg");
                }
            };
            img.onerror = function() {
                alert( "not a valid file: " + file.type);
            };
            img.src = _URL.createObjectURL(file);
        }
    });
        
        function validateStudyId(event, cb){
        	var customStudyId = $("#customStudyId").val();
        	var dbcustomStudyId = '${studyBo.customStudyId}';
        	if(customStudyId && (dbcustomStudyId !=customStudyId)){
        		$('.actBut').attr('disabled','disabled');
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
                    	$("#customStudyId").parent().find(".help-block").html("");
                    	var chk = true;
                        if (message == "SUCCESS") {
                        	$("#customStudyId").parent().find(".help-block").empty();
                            	$("#customStudyId").parent().addClass('has-error has-danger').find(".help-block").append('<ul class="list-unstyled"><li>'+customStudyId+' already exist.</li></ul>');
                            	chk = false;
                        }
                        cb(chk,event);
                    },
                    error:function status(data, status) {
                    	$("body").removeClass("loading");
                    	cb(false, event);
                    },
                    global:false,
                    complete : function(){ $('.actBut').removeAttr('disabled'); }
                });
          } else {
        	  cb(true, event);
          }
        }    
        
                 
</script>