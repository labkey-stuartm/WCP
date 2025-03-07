<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <meta charset="UTF-8">
</head>
<style>
  .langSpecific{
    position: relative;
  }

  .langSpecific > button::before{
    content: '';
    display: block;
    background-image: url("../images/global_icon.png");
    width: 16px;
    height: 14px;
    position: absolute;
    top: 9px;
    left: 9px;
    background-repeat: no-repeat;
  }

  .langSpecific > button{
    padding-left: 30px;
  }
</style>
<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
<div class="col-sm-10 col-rc white-bg p-none">
    <!--  Start top tab section-->
    <form:form
            action="/fdahpStudyDesigner/adminStudies/saveOrUpdateConsentInfo.do?_S=${param._S}&${_csrf.parameterName}=${_csrf.token}"
            name="consentInfoFormId" id="consentInfoFormId" method="post"
            data-toggle="validator" role="form" autocomplete="off">
        <input type="hidden" id="id" name="id" value="${consentInfoBo.id}">
        <input type="hidden" id="mlName" value="${studyLanguageBO.name}"/>
        <input type="hidden" id="customStudyName" value="${fn:escapeXml(studyBo.name)}"/>
        <c:if test="${not empty consentInfoBo.id}">
            <input type="hidden" id="studyId" name="studyId"
                   value="${consentInfoBo.studyId}">
        </c:if>
        <c:if test="${empty consentInfoBo.id}">
            <input type="hidden" id="studyId" name="studyId" value="${studyId}">
        </c:if>
        <input type="hidden" id="elaborated" name="elaborated" value=""/>
        <input type="hidden" id="type" name="type" value="complete"/>
        <input type="hidden" id="currentLanguage" name="currentLanguage" value="${currLanguage}">
        <input type="hidden" id="briefSummaryLang" value="${consentInfoLangBO.briefSummary}">
        <input type="hidden" id="elaboratedLang" value="${consentInfoLangBO.elaborated}">
        <input type="hidden" id="displayTitleLang" value="${consentInfoLangBO.displayTitle}">
        <div class="right-content-head" style="z-index: 999;">
            <div class="text-right">
                <div class="black-md-f dis-line pull-left line34">
					<span class="pr-sm cur-pointer" onclick="goToBackPage(this);">
						<img src="../images/icons/back-b.png"/>
					</span>
                    <c:if test="${empty consentInfoBo.id}"> Add Consent Section</c:if>
                    <c:if
                            test="${not empty consentInfoBo.id && actionPage eq 'addEdit'}">Edit Consent Section</c:if>
                    <c:if test="${not empty consentInfoBo.id && actionPage eq 'view'}">View Consent Section
                        <c:set
                                var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive]?'<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}
                    </c:if>
                </div>

                <c:if test="${studyBo.multiLanguageFlag eq true and not empty consentInfoBo.id}">
                    <div class="dis-line form-group mb-none mr-sm" style="width: 150px;">
                        <select
                                class="selectpicker aq-select aq-select-form studyLanguage langSpecific"
                                id="studyLanguage" name="studyLanguage" title="Select">
                            <option value="en" ${((currLanguage eq null) or (currLanguage eq '') or  (currLanguage eq 'undefined') or (currLanguage eq 'en')) ?'selected':''}>
                                English
                            </option>
                            <c:forEach items="${languageList}" var="language">
                                <option value="${language.key}"
                                    ${currLanguage eq language.key ?'selected':''}>${language.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <c:if test="${studyBo.multiLanguageFlag eq true and empty consentInfoBo.id}">
                    <div class="dis-line form-group mb-none mr-sm" style="width: 150px;">
                    <span class="tool-tip" id="markAsTooltipId" data-toggle="tooltip"
                          data-placement="bottom"
                          title="Language selection is available in edit screen only">
						<select class="selectpicker aq-select aq-select-form studyLanguage langSpecific"
                                title="Select" disabled>
                        <option selected>English</option>
                    </select>
					</span>
                    </div>
                </c:if>

                <div class="dis-line form-group mb-none">
                    <button type="button" class="btn btn-default gray-btn"
                            onclick="goToBackPage(this);">Cancel
                    </button>
                </div>
                <div class="dis-line form-group mb-none">
                    <button type="button"
                            class="btn btn-default gray-btn ConsentButtonHide ml-sm mr-sm"
                            onclick="saveConsentInfo(this);">Save
                    </button>
                </div>
                <div class="dis-line form-group mb-none">
                    <button type="button"
                            class="btn btn-primary blue-btn ConsentButtonHide" id="doneId">Done
                    </button>
                </div>
            </div>
        </div>
        <!-- End top tab section-->
        <!-- Start body tab section -->
        <div class="right-content-body">
            <div class="gray-xs-f mb-xs">
                Select Consent Section type <span class="requiredStar">*</span>
            </div>
            <div class="mt-sm form-group" id="consentSectionDiv">
				<span class="radio radio-info radio-inline p-45"> <input
                        type="radio" id="inlineRadio1" value="ResearchKit/ResearchStack"
                        name="consentItemType" required data-error="Please choose type"
                    ${empty consentInfoBo.consentItemType  || consentInfoBo.consentItemType=='ResearchKit/ResearchStack' ?'checked':''}>
					<label for="inlineRadio1">ResearchKit/ResearchStack</label>
				</span> <span class="radio radio-inline p-45"> <input type="radio"
                                                                      id="inlineRadio2"
                                                                      value="Custom"
                                                                      name="consentItemType"
                                                                      required
                                                                      data-error="Please choose type"
                ${consentInfoBo.consentItemType=='Custom'?'checked':''}> <label
                    for="inlineRadio2">Custom</label>
				</span>
                <div class="help-block with-errors red-txt"></div>
            </div>
            <div id="titleContainer">
                <div class="gray-xs-f mb-xs">
                    Title <span class="requiredStar">*</span>
                </div>
                <div class="col-md-5 p-none form-group elaborateClass consentTitle">
                    <select class="selectpicker" id="consentItemTitleId"
                            name="consentItemTitleId" required
                            data-error="Please choose one title">
                        <option value="">Select</option>
                        <c:forEach items="${consentMasterInfoList}" var="consentMaster">
                            <option value="${consentMaster.id}"
                                ${consentInfoBo.consentItemTitleId eq consentMaster.id  ? 'selected' : ''}>${consentMaster.title}</option>
                        </c:forEach>
                    </select>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <input type="hidden" id="displayTitleTemp" name="displayTitleTemp"
                   value="${consentInfoBo.displayTitle}"> <input type="hidden"
                                                                 id="briefSummaryTemp"
                                                                 name="briefSummaryTemp"
                                                                 value="${consentInfoBo.briefSummary}">
            <textarea name="hide" id="elaboratedTemp"
                      style="display: none;">${consentInfoBo.elaborated}</textarea>
            <div id="displayTitleId">
                <div class="gray-xs-f mb-xs">
                    Display Title <small>(75 characters max)</small><span
                        class="requiredStar">*</span>
                </div>
                <div class="form-group">
                    <input autofocus="autofocus" type="text" id="displayTitle"
                           class="form-control" name="displayTitle" required
                           value="${fn:escapeXml(consentInfoBo.displayTitle)}" maxlength="75">
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div>
                <div class="gray-xs-f mb-xs">
                    Brief Summary <small>(500 characters max)</small><span
                        class="requiredStar">*</span>
                </div>
                <div class="form-group">
					<textarea class="form-control" rows="7" id="briefSummary"
                              name="briefSummary" required
                              maxlength="500">${consentInfoBo.briefSummary}</textarea>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div>
                <div class="gray-xs-f mb-xs">
                    Elaborated Content <span class="requiredStar">*</span>
                </div>
                <div class="form-group">
					<textarea class="" rows="8" id="elaboratedRTE" name="elaboratedRTE"
                              required>${consentInfoBo.elaborated}</textarea>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div>
                <div class="gray-xs-f mb-xs">
                    Show as a visual step in the Consent Info section? <span
                        class="requiredStar">*</span><span
                        class="ml-xs sprites_v3 filled-tooltip" data-toggle="tooltip"
                        title="Choose Yes if you wish this section to appear as a standalone Visual Step in the app prior to the full Consent Document. A Visual Step screen shows the section Title, and the Brief Summary with a link to the elaborated version of the content."></span>
                </div>
                <div class="form-group visualStepDiv">
					<span class="radio radio-info radio-inline p-45"> <input
                            class="" type="radio" id="inlineRadio3" value="Yes"
                            name="visualStep" required
                            data-error="Please choose one visual step"
                        ${consentInfoBo.visualStep=='Yes'?'checked':''}> <label
                            for="inlineRadio3">Yes</label>
					</span> <span class="radio radio-inline p-45"> <input class=""
                                                                          type="radio"
                                                                          id="inlineRadio4"
                                                                          value="No"
                                                                          name="visualStep"
                                                                          required
                                                                          data-error="Please choose one visual step"
                    ${consentInfoBo.visualStep=='No'?'checked':''}> <label
                        for="inlineRadio4">No</label>
					</span>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
        </div>
    </form:form>
    <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
  $(document).ready(function () {
    // Fancy Scroll Bar

    let currLang = $('#studyLanguage').val();
    if (currLang !== undefined && currLang !== null && currLang !== '' && currLang !== 'en') {
      $('#currentLanguage').val(currLang);
      refreshAndFetchLanguageData(currLang);
    }

    <c:if test="${actionPage eq 'view'}">
    $('#consentInfoFormId input,textarea').prop('disabled', true);
    $('#consentInfoFormId .elaborateClass').addClass('linkDis');
    $('.ConsentButtonHide').hide();
    </c:if>

    if ('${consentInfoBo.id}' == '') {
      $("#displayTitleId").hide();
    }
    $(".menuNav li").removeClass('active');
    $(".fifthConsent").addClass('active');
    $("#createStudyId").show();
    //load the list of titles when the page loads
    consentInfoDetails();
    initTinyMCEEditor();
    //get the selected consent type on change
    $('input[name="consentItemType"]').change(function () {
      $('.visualStepDiv').find(".help-block").empty();
      resetValidation($("#consentInfoFormId"));

      if (this.value == 'Custom') {
        $("#displayTitleId").show();
        $("#titleContainer").hide();
        $("#consentItemTitleId").prop('required', false);
      } else {

        consentInfoDetails();
        $("#consentItemTitleId").prop('required', true);
        $("#titleContainer").show();
      }
      addDefaultData();
    });

    $("#consentItemTitleId").change(function () {
      var titleText = this.options[this.selectedIndex].text;
      resetValidation($("#consentInfoFormId"));

      $(".consentTitle").parent().removeClass('has-error has-danger');
      $(".consentTitle").parent().find(".help-block").empty();
      $("#displayTitle").parent().removeClass('has-error has-danger');
      $("#displayTitle").parent().find(".help-block").empty();
      if (titleText != null && titleText != '' && typeof titleText != 'undefined') {
        $("#displayTitleId").show();
        if (titleText != 'Select') {
          $("#displayTitle").val(titleText);
        } else {
          $("#displayTitle").val('');
        }
      }
    });

    if ('${consentInfoBo.consentItemType}' == 'Custom') {
      $("#titleContainer").hide();
      $("#consentItemTitleId").prop('required', false);
    } else {
      $("#titleContainer").show();
      $("#consentItemTitleId").prop('required', true);
    }
    //submit the form
    $("#doneId").on('click', function () {
      $("#doneId").prop('disabled', true);
      tinyMCE.triggerSave();
      valid = maxLenValEditor();
      if (valid && isFromValid("#consentInfoFormId")) {
        var visualStepData = '';

        visualStepData = $('input[name=visualStep]:checked').val();
        if (visualStepData != '' && visualStepData != null && typeof visualStepData
            != 'undefined') {

          var elaboratedContent = tinymce.get('elaboratedRTE').getContent({format: 'raw'});
          elaboratedContent = replaceSpecialCharacters(elaboratedContent);
          var briefSummaryText = replaceSpecialCharacters($("#briefSummary").val());
          elaboratedContent = $('#elaboratedRTE').text(elaboratedContent).html();
          $("#elaborated").val(elaboratedContent);
          $("#briefSummary").val(briefSummaryText);
          var displayTitleText = $("#displayTitle").val();
          displayTitleText = replaceSpecialCharacters(displayTitleText);
          $("#displayTitle").val(displayTitleText);
          $("#consentInfoFormId").submit();

        } else {
          $('.visualStepDiv').addClass('has-error has-danger');
          $('.visualStepDiv').find(".help-block").empty().append(
              $("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                  "Please choose one visual step"));
          $("#doneId").prop('disabled', false);
        }
      } else {
        $("#doneId").prop('disabled', false);
      }
    });
  });

  function saveConsentInfo(item) {
    var consentInfo = new Object();
    var consentInfoId = $("#id").val();
    var study_id = $("#studyId").val();
    var consentType = $('input[name="consentItemType"]:checked').val();
    var consentitemtitleid = $("#consentItemTitleId").val();
    var displayTitleText = $("#displayTitle").val();
    displayTitleText = replaceSpecialCharacters(displayTitleText);
    var briefSummaryText = $("#briefSummary").val();
    briefSummaryText = replaceSpecialCharacters(briefSummaryText);
    var elaboratedText = tinymce.get('elaboratedRTE').getContent({format: 'raw'});
    elaboratedText = replaceSpecialCharacters(elaboratedText);

    var visual_step = $('input[name="visualStep"]:checked').val();

    var valid = maxLenValEditor();

    if (valid && (study_id != null && study_id != '' && typeof study_id != 'undefined')
        && (displayTitleText != null && displayTitleText != '' && typeof displayTitleText
            != 'undefined')) {
      $(item).prop('disabled', true);
      if (null != consentInfoId) {
        consentInfo.id = consentInfoId;
      }
      consentInfo.studyId = study_id;
      if (null != consentType) {
        consentInfo.consentItemType = consentType;
      }
      if (null != consentitemtitleid) {
        consentInfo.consentItemTitleId = consentitemtitleid;
      }
      if (null != briefSummaryText) {
        consentInfo.briefSummary = briefSummaryText;
      }
      if (null != elaboratedText) {
        consentInfo.elaborated = elaboratedText;
      }
      if (null != visual_step) {
        consentInfo.visualStep = visual_step;
      }
      if (null != displayTitleText) {
        consentInfo.displayTitle = displayTitleText;
      }
      consentInfo.type = "save";

      var data = JSON.stringify(consentInfo);
      $.ajax({
        url: "/fdahpStudyDesigner/adminStudies/saveConsentInfo.do?_S=${param._S}",
        type: "POST",
        datatype: "json",
        data: {
          consentInfo: data,
          language: $('#currentLanguage').val()
        },
        beforeSend: function (xhr, settings) {
          xhr.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
        },
        success: function (data) {
          var message = data.message;
          if (message == "SUCCESS") {
            $('.fifthConsent').find('span').remove();
            var consentInfoId = data.consentInfoId;
            $("#id").val(consentInfoId);
            $("#alertMsg").removeClass('e-box').addClass('s-box').text("Content saved as draft.");
            $(item).prop('disabled', false);
            $('#alertMsg').show();
          } else {
            $("#alertMsg").removeClass('s-box').addClass('e-box').text("Something went Wrong");
            $('#alertMsg').show();
          }
          setTimeout(hideDisplayMessage, 4000);
        },
        error: function (xhr, status, error) {
          $(item).prop('disabled', false);
          $('#alertMsg').show();
          $("#alertMsg").removeClass('s-box').addClass('e-box').text("Something went Wrong");
          setTimeout(hideDisplayMessage, 4000);
        }
      });
    } else {
      $(item).prop('disabled', false);
      if (valid) {
        $(".consentTitle").parent().addClass('has-error has-danger');
        $(".consentTitle").parent().find(".help-block").empty().append(
            $("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                "This is a required field."));
        setTimeout(hideDisplayMessage, 4000);
      }

    }
  }

  function goToBackPage(item) {
    let lang = ($('#studyLanguage').val()!==undefined)?$('#studyLanguage').val():'';
    <c:if test="${actionPage ne 'view'}">
    $(item).prop('disabled', true);
    bootbox.confirm({
      closeButton: false,
      message: 'You are about to leave the page and any unsaved changes will be lost. Are you sure you want to proceed?',
      buttons: {
        'cancel': {
          label: 'Cancel',
        },
        'confirm': {
          label: 'OK',
        },
      },
      callback: function (result) {
        if (result) {
          var a = document.createElement('a');
          a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do?_S=${param._S}&language="
              + lang;
          document.body.appendChild(a).click();
        } else {
          $(item).prop('disabled', false);
        }
      }
    });
    </c:if>
    <c:if test="${actionPage eq 'view'}">
    var a = document.createElement('a');
    a.href = "/fdahpStudyDesigner/adminStudies/consentListPage.do?_S=${param._S}&language="
        + lang;
    document.body.appendChild(a).click();
    </c:if>
  }

  //remove the default vallues from the fields when the consent type is changed
  function addDefaultData() {

    var consentInfoId = $("#id").val();
    $("#displayTitle").val('');
    $("#briefSummary").val('');
    $("#elaboratedRTE").val('');
    $("#elaborated").val('');
    tinymce.get('elaboratedRTE').setContent('');
    $("#inlineRadio3").prop('checked', false);
    $("#inlineRadio4").prop('checked', false);
    if (consentInfoId != null && consentInfoId != '' && typeof consentInfoId != 'undefined') {
      var consentType = "${consentInfoBo.consentItemType}";
      var actualValue = $("input[name='consentItemType']:checked").val();
      if (consentType == actualValue) {

        var elaboratedText = $("#elaboratedTemp").val();
        tinymce.get('elaboratedRTE').setContent(elaboratedText);
        var displayTitle = $("#displayTitleTemp").val();
        var briefSummary = $("#briefSummaryTemp").val();
        $("#displayTitle").val(displayTitle);
        $("#briefSummary").val(briefSummary);
        var visualStep = "${consentInfoBo.visualStep}";
        if (visualStep == "Yes") {
          $("#inlineRadio3").prop('checked', true);
        } else if (visualStep == "No") {
          $("#inlineRadio4").prop('checked', true);
        }
        initTinyMCEEditor();
      }
    }
  }

  function consentInfoDetails() {
    if (typeof "${consentInfoList}" != 'undefined') {
      var selectedTitle = document.getElementById('consentItemTitleId');
      var actualOption = "${consentInfoBo.consentItemTitleId}";
      for (var i = 0; i < selectedTitle.length; i++) {
        if (actualOption == selectedTitle.options[i].value) {
          $('#consentItemTitleId :nth-child(' + (i + 1) + ')').prop('selected', true).trigger(
              'change');
        }
        <c:forEach items="${consentInfoList}" var="consentInfo">
        if ('${consentInfo.consentItemTitleId}' != '' && '${consentInfo.consentItemTitleId}'
            != null) {
          if ('${consentInfo.consentItemTitleId}' == selectedTitle.options[i].value
              && '${consentInfo.consentItemTitleId}' != '${consentInfoBo.consentItemTitleId}') {
            $("select option[value=" + selectedTitle.options[i].value + "]").attr("disabled",
                "disabled");
            $('.selectpicker').selectpicker('refresh');
          }
        }

        </c:forEach>
      }
    }
  }

  //initialize the tinymce editor
  function initTinyMCEEditor() {
    tinymce.init({
      selector: "#elaboratedRTE",
      theme: "modern",
      skin: "lightgray",
      height: 180,
      plugins: [
        "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
        "save contextmenu directionality paste"
      ],
      toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link | hr removeformat | cut undo redo | fontsizeselect fontselect",
      menubar: false,
      toolbar_items_size: 'small',
      content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
      setup: function (ed) {
        ed.on('change', function (ed) {
          if (tinyMCE.get(ed.target.id).getContent() != '') {
            $('#elaboratedRTE').parent().removeClass("has-danger").removeClass("has-error");
            $('#elaboratedRTE').parent().find(".help-block").empty();
          }
        });
      },
      <c:if test="${actionPage eq 'view'}">readonly: 1</c:if>
    });
  }

  function maxLenValEditor() {
    var isValid = true;
    var value = tinymce.get('elaboratedRTE').getContent({format: 'raw'});

    if (value != '' && $.trim(value.replace(/(<([^>]+)>)/ig, "")).length > 15000) {
      if (isValid) {
        isValid = false;
      }
      $('#elaboratedRTE').parent().addClass('has-error-cust').find(".help-block").empty().append(
          $("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
              "Maximum 15000 characters are allowed."));
    } else {
      $('#elaboratedRTE').parent().removeClass("has-danger").removeClass("has-error");
      $('#elaboratedRTE').parent().find(".help-block").empty();
    }
    return isValid;
  }

  $('#studyLanguage').on('change', function () {
    let currLang = $('#studyLanguage').val();
    $('#currentLanguage').val(currLang);
    refreshAndFetchLanguageData($('#studyLanguage').val());
  })

  function refreshAndFetchLanguageData(language) {
    $.ajax({
      url: '/fdahpStudyDesigner/adminStudies/consentInfo.do?_S=${param._S}',
      type: "GET",
      data: {
        language: language,
        consentInfoId: $("#id").val()
      },
      success: function (data) {
        let htmlData = document.createElement('html');
        htmlData.innerHTML = data;
        if (language !== 'en') {
          updateCompletionTicks(data);
          $('.tit_wrapper').text($('#mlName', htmlData).val());
          $('#inlineRadio1').attr('disabled', true);
          $('#inlineRadio2').attr('disabled', true);
          $('#inlineRadio3').attr('disabled', true);
          $('#inlineRadio4').attr('disabled', true);
          $('.visualStepDiv').attr('disabled', true);
          $('#briefSummary').val($('#briefSummaryLang', htmlData).val());
          $('#elaboratedRTE').val($('#elaboratedLang', htmlData).val());
          $('#displayTitle').val($('#displayTitleLang', htmlData).val());
          let editor = tinymce.activeEditor;
          if (editor!==undefined) {
            editor.setContent($('#elaboratedLang', htmlData).val());
          }
          if ($('#inlineRadio1').prop('checked') === true) {
            let title = $('[data-id="consentItemTitleId"]');
            title.attr('disabled', true);
            title.css('background-color', '#eee');
            title.css('opacity', '1');
          } else {
            $('#displayTitle').val($('#displayTitleLang', htmlData).val());
          }
        } else {
          updateCompletionTicksForEnglish();
          $('.tit_wrapper').text($('#customStudyName', htmlData).val());
          $('#inlineRadio1').attr('disabled', false);
          $('#inlineRadio2').attr('disabled', false);
          $('#inlineRadio3').attr('disabled', false);
          $('#inlineRadio4').attr('disabled', false);
          $('.visualStepDiv').attr('disabled', false);
          let title = $('[data-id="consentItemTitleId"]');
          title.attr('disabled', false);
          title.removeAttr('style');
          $('#displayTitle').attr('disabled', false);
          $('#briefSummary').val($('#briefSummary', htmlData).val());
          $('#elaboratedRTE').val($('#elaboratedRTE', htmlData).val());
          $('#displayTitle').val($('#displayTitle', htmlData).val());
          let editor = tinymce.activeEditor;
          if (editor!==undefined) {
            editor.setContent($('#elaboratedRTE', htmlData).val());
          }
          
          <c:if test="${actionPage eq 'view'}">
          $('#consentInfoFormId input,textarea').prop('disabled', true);
          </c:if>
        }
      }
    })
  }
</script>