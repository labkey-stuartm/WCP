/*

Name: 			common.js
Written by: 	BTC - Maari Vanaraj
Version: 		1.0

*/


$(window).on('load', function(){
     
});

/**
 * Check the given form is valid or not
 * 
 * @author Kanchana
 * @param param , form id
 * @returns {Boolean}
 */
function isFromValid(param){
	$(param).validator('validate');
    if($(param).find(".has-danger").length > 0){
        return false;
    }else{
        return true;
    }
}

/**
 * reset validation js from the form 
 * 
 * @author Vivek
 * @param param , form id,
 * @returns void
 */
function resetValidation(param){
	$(param).validator('destroy').validator();
}

function checkboxValidate(name){
    var min = 1 //minumum number of boxes to be checked for this form-group
    if($('input[name="'+name+'"]:checked').length<min){
        $('input[name="'+name+'"]').prop('required',true);
    }
    else{
        $('input[name="'+name+'"]').prop('required',false);
    }
}
$(document).ready(function(){
	checkboxValidate($('.form-group input:checkbox').attr('name'));
	$('.form-group').on("click load",'input:checkbox',function(){          
	    checkboxValidate($(this).attr('name'));
	});
	$('.phoneMask').mask('000-000-0000');
	$(".phoneMask").keyup(function(){
    	if($(this).val() == "000-000-0000" ){
    		$(this).val("");
    		$(this).parent().addClass("has-danger").addClass("has-error");
    		$(this).parent().find(".help-block").text("Invalid phone number");
    	}else{
    		$(this).parent().find(".help-block").text("");
    	}
    });
	
	$(".validateUserEmail").blur(function(){
        var email = $(this).val();
        var oldEmail = $(this).attr('oldVal');
        var isEmail = false;
        var regEX = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
        isEmail = regEX.test(email);
        
        if(isEmail && ('' == oldEmail || ('' != oldEmail && oldEmail != email))){
        	var csrfDetcsrfParamName = $('#csrfDet').attr('csrfParamName');
            var csrfToken = $('#csrfDet').attr('csrfToken');
            var thisAttr= this;
            $(thisAttr).parent().find(".help-block").html("<ul class='list-unstyled'><li></li></ul>");
                if(email != ''){
                    $.ajax({
                        url: "/fdahpStudyDesigner/isEmailValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
                        type: "POST",
                        datatype: "json",
                        data: {
                            email : email,
                        },
                        success:  function getResponse(data){
                            var message = data.message;
                            console.log(message);
                            if('SUCCESS' != message){
                                $(thisAttr).validator('validate');
                                $(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
                                $(thisAttr).parent().find(".help-block").html("");
                            }else{
                                $(thisAttr).val('');
                                $(thisAttr).parent().addClass("has-danger").addClass("has-error");
                                $(thisAttr).parent().find(".help-block").empty();
                                $(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + email + "' already exists.</li></ul>");
                            }
                        }
                  });
              }
        }else{
        	$("#removeText .help-block ul").remove();
        }
    });
    
});









