<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.checkBoxForm {
	
}
</style>
<!-- Start right Content here -->
<div class="col-sm-10 col-rc white-bg p-none">

	<!--  Start top tab section-->
	<div class="right-content-head">
		<div class="text-right">
			<!-- <div class="black-md-f dis-line pull-left line34">
				<span class="mr-sm" onclick="goToBackPage(this);"><img
						src="../images/icons/back-b.png" /></span> Add Property
			</div> -->

			<div class="black-md-f text-uppercase dis-line pull-left line34">
				<span class="pr-sm cur-pointer" onclick="goToBackPage(this);"><img
					src="../images/icons/back-b.png" class="pr-md" /></span>
				<c:if test="${actionType eq 'add'}">Add Property</c:if>
				<c:if test="${actionType eq 'edit'}">Edit Property</c:if>
				<c:if test="${actionType eq 'view'}">View Property</c:if>
			</div>

			<div class="dis-line form-group mb-none mr-sm">
				<button type="button" class="btn btn-default gray-btn"
					onclick="goToBackPage(this);">Cancel</button>
			</div>

			<c:if test="${actionType ne 'view'}">
				<div class="dis-line form-group mb-none mr-sm">
					<button type="button" class="btn btn-default gray-btn" id="saveId">Save</button>
				</div>

				<div class="dis-line form-group mb-none">
					<button type="button" class="btn btn-primary blue-btn" id="doneId">Done</button>
				</div>
			</c:if>
		</div>
	</div>
	<!--  End  top tab section-->

	<!--  Start body tab section -->
	<div class="right-content-body pt-none pl-none pr-none">


		<div class="tab-content pl-xlg pr-xlg">

			<!-- Step-level Attributes-->
			<div id="sla" class="tab-pane fade in active mt-xlg">
				<form:form
					action="/fdahpStudyDesigner/adminStudies/saveorUpdateParticipantProperties.do?_S=${param._S}"
					name="participantPropertiesFormId" id="participantPropertiesFormId"
					modelAttribute="participantProperties" method="post">
					<input type="hidden" name="${csrf.parameterName}"
						value="${csrf.token}">
					<form:hidden path="id" />
					<form:hidden path="anchorDateId" />
					<input type="hidden" id="actionType" name="actionType"
						value="${actionType}">
					<input type="hidden" id="actionButtonType" name="actionButtonType"
						value="">
					<input type="hidden" id="preShortTitleId"
						value="${fn:escapeXml(participantProperties.shortTitle)}" />
					<div class="row">
						<div class="col-md-4 pl-none mt-lg">
							<div class="gray-xs-f mb-xs">
								Short Title or Key(1 to 50 characters)<span class="requiredStar">*</span>
								<span class="ml-xs sprites_v3 filled-tooltip"
									data-toggle="tooltip"
									title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
							</div>
							<div
								class="form-group mb-none <c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:input autofocus="autofocus" type="text"
									class="form-control" name="shortTitle" id="shortTitleId"
									path="shortTitle" maxlength="50" required="required" />
								<div class="help-block with-errors red-txt"></div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="row">
						<div class="col-md-4 pl-none mt-lg">
							<div class="gray-xs-f mb-xs">
								Property Name(1 to 300 characters)<span class="requiredStar">*</span>
								<span class="ml-xs sprites_v3 filled-tooltip"
									data-toggle="tooltip"
									title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
							</div>
							<div class="form-group mb-none">
								<form:input type="text" class="form-control" name="propertyName"
									id="propertyName" path="propertyName" required="required"
									maxlength="300" />
								<div class="help-block with-errors red-txt"></div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div>
						<div class="gray-xs-f mb-xs mt-lg">
							Property Type<span class="requiredStar">*</span> <span
								class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip"
								title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
						</div>
						<div class="form-group">
							<span
								class="radio radio-info radio-inline p-45 propTypeCls
								<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:radiobutton class="enrollment-cls" id="inlineRadio1"
									value="preEnrollment" name="typeOfProperty" path="propertyType"
									required="required" /> <label for="inlineRadio1">Pre-Enrollment</label>
							</span> <span
								class="radio radio-inline propTypeCls
								<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:radiobutton class="enrollment-cls" id="inlineRadio2"
									required="required" value="postEnrollment"
									name="typeOfProperty" path="propertyType" /> <label
								for="inlineRadio2">Post-Enrollment</label>
							</span>
							<div class="help-block with-errors red-txt"></div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-4 col-lg-3 p-none">
							<div class="gray-xs-f mb-xs">
								Data Type<span class="requiredStar">*</span> <span
									class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip"
									title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
							</div>
							<div
								class="form-group 
							<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:select id="dataType" class="selectpicker required-attr"
									title="Select" required="required" path="dataType">
									<form:options items="${dataType}" />
								</form:select>
								<div class="help-block with-errors red-txt"></div>
							</div>
						</div>

						<div
							class="col-md-4 col-lg-3 mt-xlg mb-lg useAsAnchorDate 
						<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>"
							style="display: none;">
							<!-- <span class="checkbox checkbox-inline"> -->
							<form:checkbox id="inlineCheckbox1" value=""
								path="useAsAnchorDate" />
							<label for="inlineCheckbox1"> Use as Anchor Date </label>
							<!-- </span> -->
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-4 col-lg-3 p-none">
						<div class="gray-xs-f mb-xs">
							Data Source<span class="requiredStar">*</span> <span
								class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip"
								title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
						</div>
						<div class="form-group">
							<form:select id="dataSource" class="selectpicker required-attr"
								title="Select" required="required" path="dataSource">
								<form:options items="${dataSource}" />
							</form:select>
							<div class="help-block with-errors red-txt"></div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div>
						<div class="gray-xs-f mb-xs">
							Data to be populated at Enrollment?<span class="requiredStar">*</span>
							<span class="ml-xs sprites_v3 filled-tooltip"
								data-toggle="tooltip"
								title="The Tooltip plugin is small pop-up box that appears when the user moves."></span>
						</div>
						<div class="form-group">
							<span
								class="radio radio-info radio-inline p-45 
							<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:radiobutton id="inlineRadio3" value="true"
									class="required-attr" name="radioInline2"
									path="populatedAtEnrollment" required="required" /> <label
								for="inlineRadio3">Yes</label>
							</span> <span
								class="radio radio-inline
							<c:if test="${actionType eq 'edit' && participantProperties.live eq 1}">cursor-none</c:if>">
								<form:radiobutton id="inlineRadio4" value="false"
									class="required-attr" name="radioInline2"
									path="populatedAtEnrollment" required="required" /> <label
								for="inlineRadio4">No</label>
							</span>
							<div class="help-block with-errors red-txt"></div>
						</div>
					</div>
					<div class="clearfix"></div>

					<div class="mt-lg mb-lg refresh-value" style="display: none;">
						<!-- <span class="checkbox checkbox-inline">  -->
						<form:checkbox id="inlineCheckbox2" path="refreshedValue" />
						<label class="checkBoxForm" for="inlineCheckbox2"> Query
							for Refreshed Value </label>
						<!-- </span> -->
					</div>
					<div class="clearfix"></div>
				</form:form>
			</div>
			<!-- <div class="dis-line form-group mb-none">
				<button type="button" class="btn btn-primary blue-btn" id="doneId">Done</button>
			</div> -->
			<div class="dis-line form-group mb-none mr-sm">
				<c:choose>
					<c:when test="${participantProperties.live eq 1}">
						<div class="form-group mr-sm" style="white-space: normal;">
							<button type="button" class="btn btn-default red-btn-action"
								id="deactivateId"
								onclick="deactivateParticipantProperty(${participantProperties.id});">Deactivate</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group mr-sm" style="white-space: normal;">
							<button type="button"
								class="btn btn-default red-btn-action 
							<c:if test="${empty participantProperties.id}">cursor-none</c:if>"
								id="deleteId"
								onclick="deleteParticipantProperty(${participantProperties.id});">Delete</button>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<form:form
	action="/fdahpStudyDesigner/adminStudies/deactivateParticipantProperty.do?_S=${param._S}"
	name="deactivateParticipantPropertyForm"
	id="deactivateParticipantPropertyFormId" method="post">
	<input type="hidden" name="participantPropertyId"
		id="participantPropertyId" value="" />
</form:form>
<script>
	<c:if test="${actionType == 'view'}">
	$('#participantPropertiesFormId input[type="text"]').prop('disabled', true);
	$('#participantPropertiesFormId input[type="checkbox"]').prop('disabled',
			true);
	$('select').prop('disabled', true);
	$('#inlineRadio1,#inlineRadio2,#inlineRadio3,#inlineRadio4,#inlineRadio5')
			.prop('disabled', true);
	$('#deactivateId,#deleteId').prop('disabled', true);
	</c:if>

	var dataVal = "";
	var propType = "";
	$(document).ready(function() {
		$('#doneId').click(function(e) {
			$('.required-attr').prop('required',true);
			if (isFromValid("#participantPropertiesFormId")) {
				$("#actionButtonType").val('done');
				$('#participantPropertiesFormId').submit();
			}
		});
		$(".menuNav li.active").removeClass('active');
		$(".menuNav li.sixth").addClass('active');
		dataVal = $("#dataType").val();
		if (dataVal === 'Date') {
			$(".useAsAnchorDate").show();
		} else {
			$(".useAsAnchorDate").hide();
		}
		propType = $('.enrollment-cls:checked').val();

		if (propType === 'postEnrollment' && dataVal === 'Date') {
			if ($('#inlineCheckbox1').is(':checked')) {
				$('.refresh-value').show();
			} else {
				$('.refresh-value').hide();
			}
		} else {
			$('.refresh-value').hide();
		}
		if(${participantProperties.isUsedInQuestionnaire || participantProperties.isUsedInActiveTask || participantProperties.isUsedInResource}){
			$('#deactivateId').mouseenter(function(){
				  alert("Please Note. You are trying to deactivate a Participant Property which is already in use. Please delete the Activities or Resources which uses this Participant Property as Anchor based scheduling and try again");
			});
			
			$('#deleteId').mouseenter(function(){
				  alert("Please Note. You are trying to delete a Participant Property which is already in use. Please delete the Activities or Resources which uses this Participant Property as Anchor based scheduling and try again");
			});
		}
	});

	$('#inlineCheckbox1').change(function() {
		propType = $('.enrollment-cls:checked').val();
		dataVal = $("#dataType").val();
		if (propType === 'postEnrollment' && dataVal === 'Date') {
			if (this.checked) {
				$('.refresh-value').show();
			} else {
				$('.refresh-value').hide();
			}
		} else {
			$('.refresh-value').hide();
		}
	});
	
	$('.propTypeCls').change(function() {
		propType = $('.enrollment-cls:checked').val();
		dataVal = $("#dataType").val();
		if (propType === 'postEnrollment' && dataVal === 'Date') {
			if ($('#inlineCheckbox1').is(':checked')) {
				$('.refresh-value').show();
			} else {
				$('.refresh-value').hide();
			}
		} else {
			$('.refresh-value').hide();
		}
	});
	
	$('#dataType').change(function() {
		propType = $('.enrollment-cls:checked').val();
		dataVal = $("#dataType").val();
		alert(propType+"  "+dataVal);
		if (propType === 'postEnrollment' && dataVal === 'Date') {
			if ($('#inlineCheckbox1').is(':checked')) {
				$('.refresh-value').show();
			} else {
				$('.refresh-value').hide();
			}
		} else {
			$('.refresh-value').hide();
		}
	});

	$("#dataType").change(function() {
		dataVal = $("#dataType").val();
		if (dataVal === 'Date') {
			$(".useAsAnchorDate").show();
		} else {
			$(".useAsAnchorDate").hide();
		}
	});

	$("#shortTitleId").blur(function() {
		validateShortTitle('', function(val) {
		});
	});

	$("#saveId").click(function() {
		$('.required-attr').prop('required',false);
		if (isFromValid("#participantPropertiesFormId")) {
			$("#actionButtonType").val('save');
			$('#participantPropertiesFormId').submit();
			showSucMsg("Content saved as draft.");
		}
	});

	function validateShortTitle(item, callback) {
		var shortTitle = $("#shortTitleId").val();
		var thisAttr = $("#shortTitleId");
		var existedKey = $("#preShortTitleId").val();
		if (shortTitle != null && shortTitle != ''
				&& typeof shortTitle != 'undefined') {
			$(thisAttr).parent().removeClass("has-danger").removeClass(
					"has-error");
			$(thisAttr).parent().find(".help-block").html("");
			if (existedKey != shortTitle) {
				$
						.ajax({
							url : "/fdahpStudyDesigner/adminStudies/validateParticipantPropertyKey.do?_S=${param._S}",
							type : "POST",
							datatype : "json",
							data : {
								shortTitle : shortTitle
							},
							beforeSend : function(xhr, settings) {
								xhr.setRequestHeader("X-CSRF-TOKEN",
										"${_csrf.token}");
							},
							success : function getResponse(data) {
								var message = data.message;

								if ('SUCCESS' != message) {
									$(thisAttr).validator('validate');
									$(thisAttr).parent().removeClass(
											"has-danger").removeClass(
											"has-error");
									$(thisAttr).parent().find(".help-block")
											.html("");
									callback(true);
								} else {
									$(thisAttr).val('');
									$(thisAttr).parent().addClass("has-danger")
											.addClass("has-error");
									$(thisAttr).parent().find(".help-block")
											.empty();
									$(thisAttr)
											.parent()
											.find(".help-block")
											.append(
													"<ul class='list-unstyled'><li>'"
															+ shortTitle
															+ "' has already been used in the past.</li></ul>");
									callback(false);
								}
							},
							global : false
						});
			} else {
				callback(true);
				$(thisAttr).parent().removeClass("has-danger").removeClass(
						"has-error");
				$(thisAttr).parent().find(".help-block").html("");
			}
		} else {
			callback(false);
		}
	}

	function goToBackPage(item) {
		$(item).prop('disabled', true);
		<c:if test="${actionType ne 'view'}">
		bootbox
				.confirm({
					closeButton : false,
					message : 'You are about to leave the page and any unsaved changes will be lost. Are you sure you want to proceed?',
					buttons : {
						'cancel' : {
							label : 'Cancel',
						},
						'confirm' : {
							label : 'OK',
						},
					},
					callback : function(result) {
						if (result) {
							var a = document.createElement('a');
							a.href = "/fdahpStudyDesigner/adminStudies/participantPropertiesPage.do?_S=${param._S}";
							document.body.appendChild(a).click();
						} else {
							$(item).prop('disabled', false);
						}
					}
				});
		</c:if>
		<c:if test="${actionType eq 'view'}">
		var a = document.createElement('a');
		a.href = "/fdahpStudyDesigner/adminStudies/participantPropertiesPage.do?_S=${param._S}";
		document.body.appendChild(a).click();
		</c:if>
	}

	function deactivateParticipantProperty(participantPropertyId){
	  	 bootbox.confirm("Are you sure you want to deactivate this Participant Property?", function(result){
			 if(result){
				$("#participantPropertyId").val(participantPropertyId);
				$("#deactivateParticipantPropertyFormId").submit();
				showSucMsg('Participant Property successfully deactivated.');
		 	}
	 	});
	}
	
	function deleteParticipantProperty(participantPropertyId){
		  bootbox.confirm({
			    message: "Are you sure you want to delete this Participant Property item? This item will no longer appear on the mobile app or admin portal. Response data already gathered against this item, if any, will still be available on the response database.",
			    buttons: {
			        confirm: {
			            label: 'Yes',
			        },
			        cancel: {
			            label: 'No',
			        }
			    },
			    callback: function (result) {
			    	if(result){
						if(participantPropertyId != null && participantPropertyId != '' && typeof participantPropertyId !='undefined'){
							document.deactivateParticipantPropertyForm.action="/fdahpStudyDesigner/adminStudies/deleteParticipantProperty.do?_S=${param._S}";
							$("#participantPropertyId").val(participantPropertyId);
							document.deactivateParticipantPropertyForm.submit();
						}
					}
			    }
		  });
	  }
</script>