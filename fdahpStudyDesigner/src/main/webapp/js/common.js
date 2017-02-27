/*

Name: 			common.js
Written by: 	BTC - Maari Vanaraj
Version: 		1.0

*/


$(window).on('load', function(){
     
});

$(document).ready(function(){
	$('.phoneMask').mask('000-000-0000');
	$(".phoneMask").keyup(function(){
    	if($(this).val() == "000-000-0000" ){
    		$(this).val("");
    		$(this).parent().addClass("has-danger").addClass("has-error");
    		$(this).parent().find(".help-block").text("Invalid phone number");
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
                    url: "/fdahpStudyDesigner/isEmailValid.do?"+csrfDetcsrfParamName+"="+csrfToken,
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
                            $(thisAttr).parent().find(".help-block").append("<ul class='list-unstyled'><li>'" + email + "' already exists.</li></ul>");
                        }
                    }
              });
          }
    });
    
});









