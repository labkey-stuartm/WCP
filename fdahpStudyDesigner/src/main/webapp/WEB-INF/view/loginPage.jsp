<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
 <head>
     	<meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <meta http-equiv="x-ua-compatible" content="ie=edge">
	    <link rel="shortcut icon" href="images/fav-icon-acuity.png" type="image/x-icon">
	   <!--  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon"> -->
	
	    <title>fdahpStudyDesigner</title>
	    
	    <link href="https://fonts.googleapis.com/css?family=Titillium+Web:200,200i,300,300i,400,400i,600,600i,700,700i,900" rel="stylesheet">
	    
	    <!-- fontawesome CSS -->
    	<link href="css/font-awesome.min.css" rel="stylesheet">
	    
	    <!-- Bootstrap core CSS -->
	    <link href="css/bootstrap.min.css" rel="stylesheet">
	
	    <!--bootstarp select -->
	    <link href="css/bootstrap-select.min.css" rel="stylesheet">
	    
	    <!-- Material Design Bootstrap -->
	    <link href="css/jquery.dataTables.min.css" rel="stylesheet">
	    
	     <link href="css/validation.css" rel="stylesheet">
	    
	    <!-- Your custom styles (optional) -->
	    <link href="css/style.css" rel="stylesheet">
	    
	    <link href="/acuityLink/css/loader.css" rel="stylesheet">
	    
        <!-- SCRIPTS -->

<script type="text/javascript">
  /* function noBack() { 
	  history.pushState(null, null, 'login.do');
	   window.addEventListener('popstate', function(event) {
	     history.pushState(null, null, 'login.do');
	  }); 
  }
  if (typeof(Storage) != "undefined" ) {
	  localStorage.clear();
  } */
  window.onload = function () {
    if (typeof history.pushState === "function") {
        history.pushState("jibberish", null, null);
        window.onpopstate = function () {
            history.pushState('newjibberish', null, null);
            // Handle the back (or forward) buttons here
            // Will NOT handle refresh, use onbeforeunload for this.
        };
    }
    else {
        var ignoreHashChange = true;
        window.onhashchange = function () {
            if (!ignoreHashChange) {
                ignoreHashChange = true;
                window.location.hash = Math.random();
                // Detect and redirect change here
                // Works in older FF and IE9
                // * it does mess with your hash symbol (anchor?) pound sign
                // delimiter on the end of the URL
            }
            else {
                ignoreHashChange = false;   
            }
        };
    }
}
</script>

</head>
<body onload="noBack();">
<div id="loader"><span></span></div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBanner">
    <div class="acutyBannerColor"></div>
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBoxHeight">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBoxParent">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 acutyLoginBox">
        	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 txtcenter">
            	<img src="images/acutyLogo.png">
            </div>
            <div id="displayMessage" class="${messageClass} col-md-12 pad0 txtcenter marT20">
		      		<div  class="bg-success" ></div>
		      		<div id="sucMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-success">
            			<div class="msg" style="padding-top: 4px !important;">${sucMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
        			<div id="errMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-danger">
            			<div class="msg" style="padding-top: 4px !important;">${errMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
        			</div>
			 </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  pad0" id="logAq">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 loginFormBox marT50">
            <c:url value='/j_spring_security_check' var="actionLink"/>
                <form:form id="loginForm" data-toggle="validator" role="form" action="${actionLink}"  name="loginForm" method="post" autocomplete="off">
                <input type="hidden" name="loginBackUrl" value="${sessionScope.loginBackUrl}"/>
                <c:if test="${not empty sessionScope.loginBackUrl}">
                	<% request.getSession().removeAttribute("loginBackUrl"); %>
                </c:if>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="text" class="form-control aq-inp" id="email" name="username" data-error="E-mail address is invalid" placeholder="E-mail Address" required maxlength="100" 
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" autocomplete="off">
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="password" class="form-control aq-inp" id="password"  placeholder="Password"  required maxlength="20" data-error="This field shouldn't be empty" autocomplete="off">
                        <div class="help-block with-errors"></div>
                        <input type="hidden" name="password" id="hidePass" />
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">

                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pad0 float-R">
                            <button type="button" class="btn aq-btn" id="loginBtnId">Log In</button>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pad0">
                            <a href="javascript:void(0)" class="forgot-link" onclick="forgotPassword()">Forgot Password ? </a>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 marT15">
            	<span class="lLine"></span><span class="orTxt">OR</span><span class="rLine"></span>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 marT15">
            	<button type="button" class="btn aq-btn-yellow" id="" onclick="hiReg()">Register as Health Institution</button>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 marT15 marB50">
            	<button type="button" class="btn aq-btn-yellow" id="" onclick="aspReg()">Register as Ambulance Service Provider</button>
            </div>
            </div>
        <!-- forgot password box-->
         
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0" id="acutyForgotBox">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 loginFormBox marT50 marB50">
                <p>Enter your registered E-mail Address to get the Password Reset Link</p>
                <form:form id="forgotForm" data-toggle="validator" role="form" action="forgotPassword.do" method="post" autocomplete="off">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 form-group">
                        <input type="text" class="form-control aq-inp" id="emailReg" name="email" maxlength="100" placeholder="E-mail Address" data-pattern-error = "Please match the requested format and use all lowercase letters."  required maxlength="100" 
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$">
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">
                            <button type="submit" class="btn aq-btn" id="ResetPassword">Reset Password</button>
                    </div>
                </form:form>
            </div>
              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 txtcenter marT20 backToLogin">
                    <a class="backToLogin" onclick="loginPage()">Back To Login</a>
              </div>
        </div>
         <!-- forgot password box ends-->
       </div>
    </div>
        
        
        
        <!--HI  registration page -->
        <div class="aq-wrapper marT30" id="Hi-registraion">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 marB50">
            <div class="backBtn backBtnWhite"><img src="images/leftArrow-white.png" onclick="loginPage()"></div>
        <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 pad0 innerWrapper">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">
                <h2 class="font26 fontW400 marT5 marB25 txtwhite top40">Health Institution Registration</h2>
            </div>
            <div id="displayMessage" class="${messageClass} col-md-12 pad0 txtcenter">
	          <div  class="bg-success" ></div>
	          <div id="sucMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-success">
	               <div class="msg" style="padding-top: 4px !important;">${sucMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
	           </div>
	           <div id="errMsg" style="display:none;" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0  aq-danger">
	               <div class="msg" style="padding-top: 4px !important;">${errMsg}<i class="fa fa-times-circle" aria-hidden="true"></i></div>
	           </div>
	    </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 bgWhite box-shadowNone ">
                <form:form id="hiRegistrationForm" role="form" action="/acuityLink/registrationAdmin/registrarHIAdmin.do" data-toggle="validator" autocomplete="off" method="post" >
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 boxWhite padB10 padT40">
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp" name="hiName" maxlength="100" placeholder="HI Name *" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 form-group">
                            <select class="selectpicker aq-select aq-select-form" required id="hitype" name="type">
                               		<!-- <option  selected="selected" disabled="disabled">-- Select Type --</option> -->
                               		<option class="disabled" value="" selected="selected">-- Select Type -- *</option>
				        			<option value="Hospital">Hospital</option>
									<option value="Rehabilitation Facility">Rehabilitation Facility</option>
									<option value="Skilled Nursing Facility">Skilled Nursing Facility</option>
									<option value="Combined Rehabilitation & Skilled Nursing Facility">Combined Rehabilitation & Skilled Nursing Facility</option>
									<option value="Inpatient Behavioral Health Facility">Inpatient Behavioral Health Facility</option>
									<option value="Assisted Living / Elderly Living Facility">Assisted Living / Elderly Living Facility</option>
									<option value="Hospice Facility">Hospice Facility</option>
									<option value="Urgent Care Clinic">Urgent Care Clinic</option>
									<option value="Outpatient Care Center">Outpatient Care Center</option>
									<option value="Surgical Center (Ambulatory / Outpatient)">Surgical Center (Ambulatory / Outpatient)</option>
									<option value="Primary Care Physicians Office">Primary Care Physician's Office</option>
									<option value="Diagnostic Center">Diagnostic Center</option>
									<option value="Occupational Medicine / Therapy">Occupational Medicine / Therapy</option>
									<option value="Wound Care Center">Wound Care Center</option>
									<option value="Dialysis Center">Dialysis Center</option>
									<option value="Other">Other</option>
                            </select>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp streetAddress" placeholder="Street Address *" name="streetAddress" maxlength="100" required >
                            <div><span class="lightgrey">Example: 11 Ridgewood Drive </span> <div class="help-block with-errors"></div></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp zipMask validateZip Zipclas" placeholder="Zipcode *" id="hiZip" name="zipCode" maxlength="10" cityId="hiCity" stateId="hiState" required >
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp" placeholder="City" id="hiCity" name="city" maxlength="50" required readonly="readonly">
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp" placeholder="State" id="hiState" name="state" maxlength="50" required readonly="readonly">
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp validateHILicenseNumber" placeholder="HI License Number *" id="hiLicenseNumber" name="hiLicenseNumber" maxlength="15" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <!-- <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                          <select class="selectpicker aq-select aq-select-form" required id="timeZone" name="timeZone">
		                        		<option class="disabled" value="" selected="selected">-- Select Time Zone --</option>
					        			<option value="UTC -8">Pacific Standard Time (PST)</option>
				                        <option value="UTC -7">Mountain Standard Time (MST)</option>
				                        <option value="UTC -6">Central Standard Time (CST)</option>
				                        <option value="UTC -5">Eastern Standard Time (EST)</option>
				                        <option value="UTC -10">Hawaii-Aleutian Standard Time (HAST)</option>
				                        <option value="UTC -9">Alaska Standard Time (AKST)</option>
		                    	</select>
		                    	<div class="help-block with-errors"></div>
                        </div> -->
                </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 dvdLine pad0"></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 boxWhite padB10 ">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 ">
                         <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label>UNITS AND FLOORS</label>
                        </div>
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 unitBox">
	                        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">
	                            <input type="text" class="form-control aq-inp" placeholder="Unit Name *" id="hiUnitName" name="hiUnitName" maxlength="50" required>
	                            <div class="help-block with-errors"></div>
	                        </div>
	                        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12  form-group">
	                            <input type="text" class="form-control aq-inp" placeholder="Floor No. *" id="hiUnitFloor" name="hiUnitFloor" maxlength="20" required>
	                            <div class="help-block with-errors"></div>
	                        </div>
	                        <div class="col-lg-3 col-md-4 col-sm-12 col-xs-12  form-group">
	                            <button type="button" class="btn aq-btn-trans addBtnDis" onclick="addUnitLoginPage()"><i class="fa fa-plus" aria-hidden="true"></i></button>
	                            <button type="button" class="btn aq-btn-trans remBtnDis hide" onclick="removeUnit(this)"><i class="fa fa-trash-o" aria-hidden="true"></i></button>
	                        </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 dvdLine pad0"></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 boxWhite padB10 ">
                   
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 ">
                         <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label>HI ADMIN CONTACT DETAILS</label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp" type="text" id="hiAdminFirstName" name="hiAdminFirstName" maxlength="50" placeholder="First Name *"  required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp" type="text" id="hiAdminLastName" name="hiAdminLastName" placeholder="Last Name *" maxlength="50"  required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp validateUserEmail" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" id="userEmail" name="userEmail" placeholder="E-mail Address *" maxlength="100" data-pattern-error="Please match the requested format and use all lowercase letters." required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp phoneMask" id="hiAdminPhoneNo" name="hiAdminPhoneNo" placeholder="Phone Number *" data-minlength="12" maxlength="12" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp faxMask" id="faxNumber" name="faxNumber" placeholder="Fax Number" data-minlength="12" maxlength="12">
                            <div class="help-block with-errors"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 dvdLine pad0"></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 marT20 marB10">
               	 	<div class="col-lg-7 col-md-6 col-sm-12 col-xs-12 marT5 padL35">
                		<span>By clicking submit you accept the<a href="javascript:void(0)" class="termsClk"> Terms Of Use</a>
                 		and<a href="javascript:void(0)" class="privacyClk"> Privacy Policy  </a></span>
                    </div>
                    <div class="col-lg-5 col-md-6 col-sm-12 col-xs-12 float-R">
                        <div class="col-lg-7 col-md-6 col-sm-12 col-xs-12  float-R marB20">
                            <button type="submit" class="btn aq-btn" id="hiSubmit">Submit</button>
                        </div>
                    </div>
                </div>
                </form:form>
            </div>
            
        </div>
        </div>
        
        </div>
        
        <!-- HI registration page ends -->
        
        <!--ASP  registration page -->
        <div class="aq-wrapper marT30" id="Asp-registraion">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 marB50">
            <div class="backBtn backBtnWhite"><img src="images/leftArrow-white.png" onclick="loginPage()"></div>
        <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12 pad0 innerWrapper">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0">
                <h2 class="font26 fontW400 marT5 marB25 txtwhite top40">Ambulance Service Provider Registration</h2>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 bgWhite box-shadowNone ">
                <form:form id="aspRegistrationForm" data-toggle="validator" role="form" action="/acuityLink/registrationAdmin/registrarASPAdmin.do" autocomplete="off">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 boxWhite padB10 padT40">
                     <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp" placeholder="ASP Name *" required name="aspName" maxlength="50">
                            <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                         <select class="selectpicker aq-select aq-select-form" id="capability" name="capability" multiple="multiple" required data-none-selected-text="Nothing Selected *">
	                 		 <option value="WCT">WCT (Wheelchair Transit)</option>
	                  		 <option value="BLS">BLS (Basic Life Support)</option>
	                  		 <option value="ALS">ALS (Advanced Life Support)</option>
	                  		 <option value="CCT">CCT (Critical Care Transport)</option>
              			 </select>
              			 <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp streetAddress" required id="streetAddress" name="streetAddress" maxlength="250" placeholder="Street Address *">
                            <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp zipMask validateZip zipCodeCls" placeholder="Zipcode *" cityId="aspCity" stateId="aspState" required maxlength="10" id="zipCode" name="zipCode">
                            <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp aspCityCls" placeholder="City" name="city"  maxlength="50" id="aspCity" required readonly="readonly">
                            <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp aspStateCls" placeholder="State" maxlength="50" name="state" id="aspState" required readonly="readonly">
                            <div class="help-block with-errors"></div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        <input type="text" class="form-control aq-inp validateASPLicenseNumber" placeholder="ASP License Number *" required name="aspLicenseNumber" maxlength="50">
                            <div class="help-block with-errors"></div>
                    </div>
                   <!--  <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
		                        <select class="selectpicker aq-select aq-select-form" id="timeZone" name="timeZone" required>
		                        		<option class="disabled" value="" selected="selected">-- Select Time Zone --</option>
					        			<option value="UTC -8">Pacific Standard Time (PST)</option>
				                        <option value="UTC -7">Mountain Standard Time (MST)</option>
				                        <option value="UTC -6">Central Standard Time (CST)</option>
				                        <option value="UTC -5">Eastern Standard Time (EST)</option>
				                        <option value="UTC -10">Hawaii-Aleutian Standard Time (HAST)</option>
				                        <option value="UTC -9">Alaska Standard Time (AKST)</option>
		                    	</select>
		                    	<div class="help-block with-errors"></div>
                        </div> -->
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 dvdLine pad0"></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 boxWhite padB10 ">
                   
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 ">
                         <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label>ASP ADMIN CONTACTS DETAILS</label>
                        </div>
                         <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        	<input type="text" name="firstName" class="form-control aq-inp" required  maxlength="50" placeholder="First Name *">
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                        	<input type="text" name="lastName" class="form-control aq-inp" required maxlength="50" placeholder="Last Name *">
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp validateUserEmail" placeholder="E-mail Address *" required name="userEmail" maxlength="100" data-pattern-error="Please match the requested format and use all lowercase letters." 
                            pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$">
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp phoneMask" placeholder="Phone Number *" name="phoneNumber" data-minlength="12" maxlength="12" required>
                            <div class="help-block with-errors"></div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12  form-group">
                            <input type="text" class="form-control aq-inp faxMask" placeholder="Fax Number" name="faxNumber" data-minlength="12" maxlength="12">
                            <div class="help-block with-errors"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 dvdLine pad0"></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 marT20 marB10">
                	<div class="col-lg-7 col-md-6 col-sm-12 col-xs-12 marT5 padL35">
                		<span>By clicking submit you accept the<a href="javascript:void(0)" class="termsClk"> Terms Of Use</a>
                 		and<a href="javascript:void(0)" class="privacyClk"> Privacy Policy  </a></span>
                    </div>
                    <div class="col-lg-5 col-md-6 col-sm-12 col-xs-12 float-R">
                        <div class="col-lg-7 col-md-6 col-sm-12 col-xs-12  float-R marB20">
                            <button type="submit" class="btn aq-btn" id="aspSubmit">Submit</button>
                        </div>
                    </div>
                </div>
                </form:form>
            </div>
            
        </div>
        </div>
        
        </div>
      </div>  
        <!-- HI registration page ends -->
        
        
    <!--  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 footer">
        	© 2016 AcuityLink. All rights reserved.
        </div> -->
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad0 footer">
          <div class="col-lg-7 col-md-12 col-sm-12 col-xs-12 pad0 txtright termC">
        	Copyright <span class="fontC">&copy;</span> @ 2017 FDA.
          </div>
          <div class="col-lg-5 col-md-12 col-sm-12 col-xs-12 txtright  pad0 termC">
             <a href="javascript:void(0)" class="termsClk">Terms Of Use</a> | <a href="javascript:void(0)" class="privacyClk">Privacy Policy</a>
          </div>
       </div>
    </div>
    <input type="hidden" id="csrfDet" csrfParamName="${_csrf.parameterName}" csrfToken="${_csrf.token}" />
    <!-- /login-->
     <!-- JQuery -->
     
     <form:form action="/acuityLink/privacyPolicy.do" id="privacyForm" name="privacyForm" method="post" target="_blank">
	</form:form>
	
	<form:form action="/acuityLink/termsAndCondition.do" id="termsForm" name="termsForm" method="post" target="_blank">
	</form:form>
     
    <script type="text/javascript" src="js/jquery-2.2.3.min.js" ></script>

    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js" ></script>
    
    <!-- bootstrap select -->
    <script type="text/javascript" src="js/bootstrap-select.min.js"></script>
    
    <!-- Bootstrap validation JavaScript -->
    <script type="text/javascript" src="js/validator.min.js" ></script>
    <script type="text/javascript" src="js/jquery.mask.min.js"></script>
    <script type="text/javascript" src="/acuityLink/js/loader.js"></script>
    <!--common js-->
	<script type="text/javascript" src="js/common.js"></script>

    
    <script>
    	$(document).ready(function(e) {
			$("#hiRegistrationForm").validator();
			$("#loginForm input:first").focus();
			var sucMsg = '${sucMsg}';
			if(sucMsg.length > 0){
				$("#sucMsg .msg").html(sucMsg);
		    	$("#sucMsg").show("fast");
		    	$("#errMsg").hide("fast");
		    	setTimeout(hideDisplayMessage, 4000);
			}
			var errMsg = '${errMsg}';
			if(errMsg.length > 0){
				$("#errMsg .msg").html(errMsg);
			   	$("#errMsg").show("fast");
			   	$("#sucMsg").hide("fast");
			   	setTimeout(hideDisplayMessage, 4000);
			}
			
			$('.privacyClk').on('click',function(){
				$('#privacyForm').submit();
			});
			
			$('.termsClk').on('click',function(){
				$('#termsForm').submit();
			});
			
			
    	});
    	 window.onload = function () {
		    if (typeof history.pushState === "function") {
		        history.pushState("jibberish", null, null);
		        window.onpopstate = function () {
		            history.pushState('newjibberish', null, null);
		            // Handle the back (or forward) buttons here
		            // Will NOT handle refresh, use onbeforeunload for this.
		        };
		    }
		    else {
		        var ignoreHashChange = true;
		        window.onhashchange = function () {
		            if (!ignoreHashChange) {
		                ignoreHashChange = true;
		                window.location.hash = Math.random();
		                // Detect and redirect change here
		                // Works in older FF and IE9
		                // * it does mess with your hash symbol (anchor?) pound sign
		                // delimiter on the end of the URL
		            }
		            else {
		                ignoreHashChange = false;   
		            }
		        };
		    }
		}
    	function hideDisplayMessage(){
			$('.msg').parent().hide();
		}
    </script>
</body>
</html>