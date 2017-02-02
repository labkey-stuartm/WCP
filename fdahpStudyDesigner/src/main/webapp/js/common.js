 /* validate form function */
        function isFromValid(param){
        	$(param).validator('validate');
            if($(param).find(".has-danger").length > 0){
                return false;
            }else{
                return true;
            }
        }
        
        function resetValidation(param){
        	$(param).validator('destroy').validator();
        }
$(document).ready(function(){
	$('input[type = text]').keyup(function(e) {
		var wrappedString = $(this).val();
		if(wrappedString.indexOf('<script>') !== -1 || wrappedString.indexOf('</script>') !== -1){
//			if(wrappedString.indexOf('<script>') == -1){
//				wrappedString = '<script>'+wrappedString;
//			} else if(wrappedString.indexOf('</script>') == -1) {
//				wrappedString = wrappedString+'</script>';
//			}
//			var noScript = wrappedString.replace(/script/g, "THISISNOTASCRIPTREALLY");
//			var html1 = $(noScript);
//			html1.find('THISISNOTASCRIPTREALLY').remove();
//			$(this).val(html1.text());
			e.preventDefault();
			$(this).val('');
			$(this).parent().addClass("has-danger").addClass("has-error");
			$(this).parent().find(".help-block").html("<ul class='list-unstyled'><li>Special characters like <> are not allowed.</li></ul>");
		} else {
			$(this).parent().find(".help-block").html("");
		}
	})
	
	var isValidLoginForm = false;
	if($('.unitBox').length>1){
    	$(".remBtnDis").removeClass("hide");
    }else{
    	$(".remBtnDis").addClass("hide");
    }
    /*common script for selectpicker*/
    $('.selectpicker').selectpicker();
    /*common script for selectpicker ends*/
    
    /*common script for dropdown animation*/
    $('.navbar .dropdown').not('.notHover').hover(function() {
        $(this).find('.dropdown-menu').first().stop(true, true).slideToggle(400);
        }, function() {
        $(this).find('.dropdown-menu').first().stop(true, true).slideToggle(100)
    });
    /*common script for dropdown animation ends*/
    
   /*common script for footer and aq-wrapper*/
    if($(".aq-wrapper").length>0 && $(".acutyLoginBanner").length == 0){
        $(".aq-wrapper").css("min-height",$(window).height()-155)
        $(".footer").css({"position":"relative","margin-top":"30px"});
    }
    
    $(".acutyLoginBoxHeight").css("min-height",($(window).height()-40));
    
    $("#AppLogo").bind("change", function () {
		//$("#invalidImg,#validImg").hide();
        //Get reference of FileUpload.
    	$('#logoPath').val('');
        var fileUpload = $("#AppLogo")[0];
 
        //Check whether the file is valid Image.
        var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.jpg|.png|.gif)$");
        if (regex.test(fileUpload.value.toLowerCase())) {
            //Check whether HTML5 is supported.
            if (typeof (fileUpload.files) != "undefined") {
                //Initiate the FileReader object.
                var reader = new FileReader();
                //Read the contents of Image File.
                reader.readAsDataURL(fileUpload.files[0]);
                reader.onload = function (e) {
                    //Initiate the JavaScript Image object.
                    var image = new Image();
                    //Set the Base64 string return from FileReader as source.
                    image.src = e.target.result;
                    image.onload = function () {
                        //Determine the Height and Width.
                        var height = this.height;
                        var width = this.width;
                        /*if (height < 935 || width < 1558) {
                        	$("#invalidImg").text('Please upload 1558 X 935 size image').slideToggle();
                            $("#imgLogo").attr('src','');
                            $("#checkExitOrWhat").hide();
                            return false;
                        }*/
                        $("#checkExitOrWhat").show();
                        $("#validImg").text("Uploaded image has valid Height and Width.").slideToggle();
                        return true;
                    };
                }
            } else {
            	$("#invalidImg").text("This browser does not support HTML5.").slideToggle();
                return false;
            }
        } else {
        	$("#invalidImg").text("Please select a valid Image file.").slideToggle();
            return false;
        }
    });
    $(".validateUserEmail").change(function(){
		var email = $(this).val();
		var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
		var csrfToken = $('#csrfDet').attr('csrfToken');
		var thisAttr= this;
		$(thisAttr).parent().find(".help-block").html("<ul class='list-unstyled'><li></li></ul>");
			if(email != ''){
				$.ajax({
			        url: "/acuityLink/isEmailValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
			        type: "POST",
			        datatype: "json",
			        data: {
			        	email : email,
			        },
			        success:  function getResponse(data){
						var message = data.message;
						if('SUCCESS' != message){
							$(thisAttr).validator('validate');
							$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
							$(thisAttr).parent().find(".help-block").html("");
			        	}else{
			        		$(thisAttr).val('');
			        		$(thisAttr).parent().addClass("has-danger").addClass("has-error");
			        		$(thisAttr).parent().find(".help-block").empty();
							$(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + email + "' already exists. Please choose another email.</li></ul>");
			        	}
			        }
	          });
		  }
	});
	
	$(".validateHILicenseNumber").change(function(){
		var hiLicenseNumber = $(this).val();
		var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
		var csrfToken = $('#csrfDet').attr('csrfToken');
		var isCahnged = true;
		if($(this).attr('oldVal') && ($(this).attr('oldVal') == hiLicenseNumber)){
			isCahnged = false;
		}
		var thisAttr= this;
		if(isCahnged){
			$.ajax({
		        url: "/acuityLink/isHiLicenseNumberValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
		        type: "POST",
		        datatype: "json",
		        data: {
		        	hiLicenseNumber : hiLicenseNumber,
		        },
		        success:  function getResponse(data){
					var message = data.message;
					if('SUCCESS' != message){
						$(thisAttr).validator('validate');
						$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
						$(thisAttr).parent().find(".help-block").empty();
		        	}else{
		        		$(thisAttr).val('');
		        		$(thisAttr).parent().addClass("has-danger").addClass("has-error");
		        		$(thisAttr).parent().find(".help-block").empty();
						$(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + hiLicenseNumber + "' already exists. Please choose another license number.</li></ul>");
		        	}
		        }
          });
		} else {
			$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
			$(thisAttr).parent().find(".help-block").empty();
		}
	});
	
	$(".validateASPLicenseNumber").change(function(){
		var aspLicenseNumber = $(this).val();
		var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
		var csrfToken = $('#csrfDet').attr('csrfToken');
		var isCahnged = true;
		if($(this).attr('oldVal') && ($(this).attr('oldVal') == aspLicenseNumber)){
			isCahnged = false;
		}
		var thisAttr= this;
		if(isCahnged){
			$.ajax({
			        url: "/acuityLink/isASPLicenseNumberValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
			        type: "POST",
			        datatype: "json",
			        data: {
			        	aspLicenseNumber : aspLicenseNumber,
			        },
			        success:  function getResponse(data){
						var message = data.message;
						if('SUCCESS' != message){
							$(thisAttr).validator('validate');
							$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
							$(thisAttr).parent().find(".help-block").empty();
			        	}else{
			        		$(thisAttr).val('');
			        		$(thisAttr).parent().addClass("has-danger").addClass("has-error");
			        		$(thisAttr).parent().find(".help-block").empty();
							$(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + aspLicenseNumber + "' already exists. Please choose another license number.</li></ul>");
			        	}
			        }
	          });
		} else {
			$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
			$(thisAttr).parent().find(".help-block").empty();
		}
	});
	
	$(".validateZip").change(function(){
        var zip = $(this).val();
        var cityId = '#'+$(this).attr('cityId');
        var stateId = '#'+$(this).attr('stateId');
        var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
		var csrfToken = $('#csrfDet').attr('csrfToken');
		var streetAddress = $('.streetAddress').val();
		var thisAttr= this;
				if(zip){
					$.ajax({
				        url: "/acuityLink/isZipValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
				        type: "POST",
				        datatype: "json",
				        data: {
				        	zip : zip,
				        },
				        success:  function getResponse(data){
								var message = data.message;
								var city = data.city;
					        	var state = data.state;
								if('SUCCESS' === message){
									$(cityId).val(city);
					        		$(stateId).val(state);
									$(thisAttr).validator('validate');
									if($(thisAttr).parent().hasClass("has-danger")){
					        			$(cityId).val('');
					        			$(stateId).val('');
									}else{
										$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
									}
									$(".list-unstyled").html('');
									if(streetAddress){
										$(".streetAddress").trigger('change');
									}
					        	}else{
					        		$(thisAttr).val('');
					        		$(cityId).val('');
					        		$(stateId).val('');
					        		$(thisAttr).parent().addClass("has-danger").addClass("has-error");
					        		$(thisAttr).parent().find(".help-block").empty();
									$(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + zip + "' is not valid. Please choose different one.</li></ul>");
					        	}
				        	}
		          		});
				 } else {
					 $(thisAttr).val('');
		        		$(cityId).val('');
		        		$(stateId).val('');
				 }
		});
	
	$(".streetAddress,.Zipclas").on('change',function(){
		var streetAddress = $(this).val();
		var zipCode =  $("#hiZip").val();
		var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
		var csrfToken = $('#csrfDet').attr('csrfToken');
		var thisAttr= this;
		var hiState = $('#hiState').val();
		var hiCity = $('#hiCity').val();
		$(thisAttr).parent().find(".help-block").html("<ul class='list-unstyled'><li></li></ul>");
			if(streetAddress && zipCode && hiState && hiCity){
				$.ajax({
			        url: "/acuityLink/validateStreetAddressWithLocation.do?"+csrfDetcsrfParamName+"="+csrfToken,
			        type: "POST",
			        datatype: "json",
			        data: {
			        	streetAddress : streetAddress,
			        	zipCode : zipCode,
			        },
			        success:  function getResponse(data){
						var message = data.message;
						if('WRONGSTREETADDRESS' != message){
							$(thisAttr).validator('validate');
							$(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
							$(thisAttr).parent().find(".help-block").html("");
			        	}else{
			        		$(thisAttr).val('');
			        		$(thisAttr).parent().addClass("has-danger").addClass("has-error");
			        		$(thisAttr).parent().find(".help-block").empty();
							$(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + streetAddress + "' not valid .</li></ul>");
			        	}
			        }
	          });
		  }
	});
	$('.zipMask').mask('00000-0000');
	$('.phoneMask').mask('000-000-0000');
	$(".phoneMask").keyup(function(){
    	if($(this).val() == "000-000-0000" ){
    		$(this).val("");
    		$(this).parent().addClass("has-danger").addClass("has-error");
    		$(this).parent().find(".help-block").text("Invalid phone number");
    	}
    });
	
	$('.faxMask').mask('000-000-0000');
	$(".faxMask").keyup(function(){
    	if($(this).val() == "000-000-0000" ){
    		$(this).val("");
    		$(this).parent().addClass("has-danger").addClass("has-error");
    		$(this).parent().find(".help-block").text("Invalid fax number");
    	}
    });
	
	/*message UI Fixed*/
		$(".aq-wrapper").find('#sucMsg,#errMsg').find(".msg").css("width",$(".topContentHead").width());
		$(".aq-wrapper").find('#sucMsg,#errMsg').css("height","25px");
		
	 $('.table').on( 'page.dt', function () {
		 $('html,body').animate({scrollTop: 0},'fast');
	 });
		 $('#loginForm').on('validated.bs.validator', function (e) {
			  isValidLoginForm = true;
		});
		 $('#loginForm').on('invalid.bs.validator', function (e) {
			 isValidLoginForm = false;
		});
	 $('#loginBtnId').click(function() {
		$("#loginForm").validator('validate');
		if($("#loginForm").find(".has-danger").length > 0 ){
			isValidLoginForm = false;
        }else{
        	isValidLoginForm = true;
        }
		if(isValidLoginForm){
			$('#password').val($('#password').val()+$('#csrfDet').attr('csrfToken'));
			$('#hidePass').val($('#password').val());
			$('#password').val('');
			$('#password').attr("type", "text").css('-webkit-text-security','disc');
			$('#password').val('********************************************************************');
			/*$('#password').css('font','small-caption');
			$('#password').css('font-size','16px');*/
		    $('#loginForm').submit();
		}
		
	});
	 $('#loginForm').keypress(function (e) {
	  if (e.which == 13) {
		  $("#loginForm").validator('validate');
			if($("#loginForm").find(".has-danger").length > 0 ){
				isValidLoginForm = false;
	        }else{
	        	isValidLoginForm = true;
	        }
		  if(isValidLoginForm){
				$('#password').val($('#password').val()+$('#csrfDet').attr('csrfToken'));
				$('#hidePass').val($('#password').val());
				$('#password').val('');
				$('#password').attr("type", "text").css('-webkit-text-security','disc');
				$('#password').val('********************************************************************');
			    $('#loginForm').submit();
			}
	  }
	});
    $(document).on("contextmenu",function(e){
    	e.preventDefault();
    	alert("Right click has been disabled.");
    	return false;
     });
    document.onkeypress = function (event) {
        event = (event || window.event);
        if (event.keyCode == 123) {
        	alert("This action is disabled.")
            return false;
        }
    }
    document.onmousedown = function (event) {
        event = (event || window.event);
        if (event.keyCode == 123) {
        	alert("This actoin is disabled.")
            return false;
        }
    }
	document.onkeydown = function (event) {
	        event = (event || window.event);
	        if (event.keyCode == 123) {
	        	alert("This actoin is disabled.")
	            return false;
	        }
	    }
})
/* login page js functions*/
    function forgotPassword(){
            $("#acutyForgotBox").show();
            $("#logAq").hide();
            $("#loginForm input").val("");
            $("#forgotForm").trigger("reset").validator('destroy').validator();
        }
        function loginPage(){
            $("#acutyForgotBox").hide();
            $("#logAq").show();
            $("#forgotForm input").val("");
            $(".acutyLoginBanner").removeClass("asp-active").removeClass("hi-active");
            $("#loginForm").validator('destroy');
            $("#loginForm").validator();
            $("#loginForm input:first").focus();
        }
        function hiReg(){
        	$('.newUnit').remove();
        	if($('.unitBox').length>1){
            	$(".remBtnDis").removeClass("hide");
            }else{
            	$(".remBtnDis").addClass("hide");
            }
            $(".acutyLoginBanner").removeClass("asp-active");
            $(".acutyLoginBanner").addClass("hi-active");
            $("#hiRegistrationForm input:first").focus();
            $('#hiRegistrationForm').trigger("reset");
            $('#hitype').selectpicker('deselectAll');
            $("#hiRegistrationForm").validator('destroy');
            $("#hiRegistrationForm").validator();
            
        }
        function aspReg(){
            $(".acutyLoginBanner").removeClass("hi-active");
            $(".acutyLoginBanner").addClass("asp-active");
            $("#aspRegistrationForm input:first").focus();
            $('#aspRegistrationForm').trigger("reset");
            $('#capability').selectpicker('deselectAll');
            $("#aspRegistrationForm").validator('destroy');
            $("#aspRegistrationForm").validator();
        }
        
        var addUnit = function(){
		                var newUnit = '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 unitBox">'+
		                                '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">'+
		                                    '<lable class="aq-lbl">Unit Name</lable>'+
		                                    '<input type="text" class="form-control aq-inp" required  placeholder ="Unit Name." name="hiUnitName" maxlength="50">'+
		                                    '<div class="help-block with-errors"></div>'+
		                                '</div>'+
		                                '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">'+
		                                    '<lable class="aq-lbl">Floor Number</lable>'+
		                                    '<input type="text" class="form-control aq-inp" required placeholder ="Floor No." name="hiUnitFloor" maxlength="20">'+
		                                    '<div class="help-block with-errors"></div>'+
		                                '</div>'+
		                                '<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12  form-group">'+
		                                    '<button type="button" class="btn aq-btn-trans marT30 addBtnDis" onclick="addUnit()"><i class="fa fa-plus" aria-hidden="true"></i></button>'+
		                                    '<button type="button" class="btn aq-btn-trans marT30 remBtnDis" onclick="removeUnit(this)"><i class="fa fa-trash-o" aria-hidden="true"></i></button>'+
		                                '</div>'+
		                            '</div>';
		                $(".unitBox:last").after(newUnit);
		                $(".unitBox").parents("form").validator("destroy");
		                $(".unitBox").parents("form").validator();
		                if($('.unitBox').length>1){
		                	$(".remBtnDis").removeClass("hide");
		                }else{
		                	$(".remBtnDis").addClass("hide");
		                }
            			}
        var addUnitLoginPage = function(){
					            var newUnit = '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 unitBox newUnit">'+
					                            '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">'+
					                                '<input type="text" class="form-control aq-inp" placeholder ="Unit Name" name="hiUnitName" maxlength="50" required>'+
					                                '<div class="help-block with-errors"></div>'+
					                            '</div>'+
					                            '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">'+
					                                '<input type="text" class="form-control aq-inp" placeholder ="Floor No." name="hiUnitFloor" maxlength="20" required>'+
					                                '<div class="help-block with-errors"></div>'+
					                            '</div>'+
					                            '<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12  form-group">'+
					                                '<button type="button" class="btn aq-btn-trans addBtnDis" onclick="addUnitLoginPage()"><i class="fa fa-plus" aria-hidden="true"></i></button>'+
					                                '<button type="button" class="btn aq-btn-trans remBtnDis" onclick="removeUnit(this)"><i class="fa fa-trash-o" aria-hidden="true"></i></button>'+
					                            '</div>'+
					                        '</div>';
					            $(".unitBox:last").after(newUnit);
					            $(".unitBox").parents("form").validator("destroy");
				                $(".unitBox").parents("form").validator();
					                if($('.unitBox').length>1){
					                	$(".remBtnDis").removeClass("hide");
					                }else{
					                	$(".remBtnDis").addClass("hide");
					                }
								}
          var removeUnit = function(param){
				                $(param).parents(".unitBox").remove();
				                if($('.unitBox').length>1){
				                	$(".remBtnDis").removeClass("hide");
				                }else{
				                	$(".remBtnDis").addClass("hide");
				                }
				                $(".unitBox").parents("form").validator("destroy");
		               			$(".unitBox").parents("form").validator();
				                $('#updateBtn1').removeClass('disabled');
				            }
            
          var readURL = function(input) {
			                if (input.files && input.files[0]) {
			                    var reader = new FileReader();
			                    /* alert(reader); */
			                    reader.onload = function (e) {
			                        $('#imgLogo')
			                            .attr('src', e.target.result)
			                            .width(auto)
			                            .height(auto);
			                    };
			                    reader.readAsDataURL(input.files[0]);
			                }
            			}
   var top5ViewDropDown = function(queryType, type, toDate, fromDate, callback) {
	   var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
	   var csrfToken = $('#csrfDet').attr('csrfToken');
	   $.ajax({
			     url: '/acuityLink/dashboard/viewTop5Institutions.do?'+csrfDetcsrfParamName+'='+csrfToken,
			     type: 'POST',
			     datatype : 'json',
			     data: {
			    	 queryType : queryType,
			    	 toDate : toDate,
			    	 fromDate : fromDate
			     },
			     success: function getResponse(response) {
			    	var jsonobj = eval(response);
			    	var message = jsonobj.message;
			    	var htmlText = '';
			    	var isNotEmpty = false;
			    	if(message == "SUCCESS"){
			    		var topInstArray = jsonobj.topInstArray;
			    		if(topInstArray){
			    			$.each(topInstArray ,function(index, value) {
				    			var name = null;
						    	var totalRequest = null;
								if(type == 'asp'){
									name = value.aspName;
									totalRequest = value.totalAspRequest;
								} else if (type == 'hi') {
									name = value.hiName; 
									totalRequest = value.totalHiRequest;
								}
								if(name && name != ''){
									htmlText = htmlText + '<tr><td class="">'+name+'</td><td class="txtright">'+totalRequest+'</td></tr>'
									isNotEmpty = true;
								}
							});
			    		}
			    		if(!isNotEmpty) {
			    				htmlText ='<tr><td class="wid-50-per"><span class="floatright">No Data</span></td><td class="wid-50-per"></td></tr>'
			    		}
			    		if(type == 'asp'){
							$('#aspInstTable').find('tbody').html(htmlText);
						} else if (type == 'hi') {
							$('#hiInstTable').find('tbody').html(htmlText);
						}
			    	}
				},
				complete : function() {
					callback();
				}
				});
   						}
   var allParameters = function(queryType, type, hiOrAspName, toDate, fromDate, callback) {
	   var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
	   var csrfToken = $('#csrfDet').attr('csrfToken');
	   $.ajax({
			     url: '/acuityLink/dashboard/viewAllParameters.do?'+csrfDetcsrfParamName+'='+csrfToken,
			     type: 'POST',
			     datatype : 'json',
			     data: {
			    	 queryType : queryType,
			    	 toDate : toDate,
			    	 fromDate : fromDate,
			    	 type : type,
			    	 hiOrAspName : hiOrAspName
			     },
			     success: function getResponse(response) {
			    	var jsonobj = eval(response);
			    	var message = jsonobj.message;
			    	if(message == "SUCCESS"){
			    		var totalALS = jsonobj.totalALS;
			    		var totalBLS = jsonobj.totalBLS;
			    		var totalCCT = jsonobj.totalCCT;
			    		var totalWCT = jsonobj.totalWCT;
			    		var avgRspTime = jsonobj.avgRspTime;
			    		var avgAcceptlevel = jsonobj.avgAcceptlevel;
						if(type == 'asp' || type == 'hi'){
							addToHiParameter(totalALS, totalBLS, totalCCT, totalWCT, type);
						} else {
			    			$('#avgAcceptlevelId').text(avgAcceptlevel);
							$('#avgRspTimeId').text(avgRspTime);
							addToHiParameter(totalALS, totalBLS, totalCCT, totalWCT, 'hi');
							addToHiParameter(totalALS, totalBLS, totalCCT, totalWCT, 'asp');
						}
		    		}
				},
				complete : function() {
					callback();
				}
				});
   						}
   
var addToHiParameter = function(totalALS, totalBLS, totalCCT, totalWCT, type) {
	$('#'+type+'ALS').text(totalALS);
	$('#'+type+'BLS').text(totalBLS);
	$('#'+type+'CCT').text(totalCCT);
	$('#'+type+'WCT').text(totalWCT);
}
/**
 * Returns a random integer between min (inclusive) and max (inclusive)
 * Using Math.round() will give you a non-uniform distribution!
 */
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}