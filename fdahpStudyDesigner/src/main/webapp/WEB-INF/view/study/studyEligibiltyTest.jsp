<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            action="/fdahpStudyDesigner/adminStudies/saveOrUpdateStudyEligibiltyTestQusAns.do?_S=${param._S}"
            name="studyEligibiltyTestFormId" id="studyEligibiltyTestFormId"
            method="post" data-toggle="validator" role="form">
        <div class="right-content-head">
            <div class="text-right">
                <div class="black-md-f text-uppercase dis-line pull-left line34">
					<span class="mr-xs cur-pointer" onclick="goToBackPage(this);"><img
                            src="../images/icons/back-b.png"/></span>
                    <c:if test="${actionTypeForQuestionPage == 'edit'}">Edit Eligibility Question</c:if>
                    <c:if test="${actionTypeForQuestionPage == 'view'}">View Eligibility Question
                        <c:set
                                var="isLive">${_S}isLive</c:set>${not empty  sessionScope[isLive]?'<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}
                    </c:if>
                    <c:if test="${actionTypeForQuestionPage == 'add'}">Add Eligibility Question</c:if>
                </div>
                <input type="hidden" value="${actionTypeForQuestionPage}"
                       name="actionTypeForQuestionPage" id="actionTypeForQuestionPage">

                <c:if test="${studyBo.multiLanguageFlag eq true and actionTypeForQuestionPage != 'add'}">
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

                <c:if test="${studyBo.multiLanguageFlag eq true and actionTypeForQuestionPage == 'add'}">
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

                <div class="dis-line form-group mb-none mr-sm">
                    <button type="button" class="btn btn-default gray-btn"
                            onclick="goToBackPage(this);">Cancel
                    </button>
                </div>
                <c:if test="${actionTypeForQuestionPage ne 'view'}">
                    <div class="dis-line form-group mb-none mr-sm">
                        <button type="button" class="btn btn-default gray-btn" id="saveId">Save
                        </button>
                    </div>
                    <div class="dis-line form-group mb-none">
                        <button type="button" class="btn btn-primary blue-btn" id="doneId">Done
                        </button>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- End top tab section-->
        <!-- Start body tab section -->
        <div class="right-content-body">
            <!-- form- input-->

            <input type="hidden" id="mlQuestion" value="${eligibilityTestLangBo.question}">
            <input type="hidden" id="currentLanguage" name="currentLanguage"
                   value="${currLanguage}">
            <input type="hidden" id="type" name="type" value="complete"/>
            <input type="hidden" name="id" id="id" value="${eligibilityTest.id}"/>
            <input type="hidden" id="eligibilityId" name="eligibilityId" value="${eligibilityId}"/>
            <input type="hidden" id="sequenceNo" name="sequenceNo"
                   value="${eligibilityTest.sequenceNo}"/>
            <input type="hidden" id="mlName" value="${studyLanguageBO.name}"/>
            <input type="hidden" id="customStudyName" value="${fn:escapeXml(studyBo.name)}"/>
                <%-- <input type="hidden" id="lastEligibilityOptId" name="lastEligibilityOpt" value="${lastEligibilityOpt}" /> --%>
            <div class=" col-lg-4 col-md-5 pl-none">
                <div class="gray-xs-f mb-xs">
                    Short title (1 to 15 characters)<span class="requiredStar">
						*</span><span class="ml-xs sprites_v3 filled-tooltip"
                                      data-toggle="tooltip"
                                      title="This must be a human-readable activity identifier and unique across all activities of the study.Note that this field cannot be edited once the study is Launched."></span>
                </div>
                <div class="form-group">
                    <input autofocus="autofocus" type="text" custAttType="cust"
                           class="form-control ${eligibilityTest.used ? 'cursor-none-disabled-event' : ''}"
                           name="shortTitle" id="shortTitleId"
                           value="${fn:escapeXml(eligibilityTest.shortTitle)}"
                           required="required" maxlength="15"
                        ${eligibilityTest.used ? 'readonly' : ''} />
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="gray-xs-f mb-xs">
                Question (1 to 250 characters)<span class="requiredStar"> *</span>
            </div>
            <div class="form-group">
                <input type="text" class="form-control"
                       name="question" id="question" required
                       value="${fn:escapeXml(eligibilityTest.question)}" maxlength="250"/>
                <div class="help-block with-errors red-txt"></div>
            </div>
            <div class="clearfix"></div>
            <div class="col-lg-5 col-md-5 p-none">
                <div class="form-group col-md-12 p-none mr-md mb-none">
                    <div class="gray-xs-f mb-xs col-md-6 pl-none ">Response
                        Options
                    </div>
                    <div class="gray-xs-f mb-xs col-md-6 pr-none">
                        Pass / Fail<span class="requiredStar"> *</span>
                    </div>
                </div>
                <div class="col-md-12 p-none mr-md mb-none">
                    <div class="col-md-6 pl-none">
                        <input type="text" class="form-control" name="tentativeDuration"
                               value="Yes" disabled/>
                    </div>
                    <div class="form-group col-md-6 pr-none">
                        <select class="selectpicker elaborateClass" required
                                title="Select" name="responseYesOption" id="resYesOptId"
                                onchange="chkValidChoosedOption()">
                            <option value="true"
                                ${eligibilityTest.responseYesOption ? 'selected':''}>Pass
                            </option>
                            <option value="false"
                                ${not empty eligibilityTest.responseYesOption && not eligibilityTest.responseYesOption ? 'selected':''}>
                                Fail
                            </option>
                        </select>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <div class="col-md-12 p-none mr-md mb-none">
                    <div class="col-md-6 pl-none ">
                        <input type="text" class="form-control" name="tentativeDuration"
                               value="No" disabled/>
                    </div>
                    <div class="form-group col-md-6 pr-none">
                        <select class="selectpicker elaborateClass form-control" required
                                title="Select" name="responseNoOption" id="resNoOptId"
                                onchange="chkValidChoosedOption()">
                            <option value="true"
                                ${eligibilityTest.responseNoOption ? 'selected':''}>Pass
                            </option>
                            <option value="false"
                                ${not empty eligibilityTest.responseNoOption && not eligibilityTest.responseNoOption ? 'selected':''}>
                                Fail
                            </option>
                        </select>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </form:form>
    <!--  End body tab section -->
</div>
<!-- End right Content here -->
<script type="text/javascript">
  var isValid = false;
  var oldShortTitle = "${fn:escapeXml(eligibilityTest.shortTitle)}";
  $(document)
  .ready(
      function () {

        $(".menuNav li.active").removeClass('active');
        $(".menuNav li.fourth").addClass('active');

        let currLang = $('#studyLanguage').val();
        if (currLang !== undefined && currLang !== null && currLang !== '' && currLang
            !== 'en') {
          $('#currentLanguage').val(currLang);
          refreshAndFetchLanguageData(currLang);
        }

        <c:if test="${actionTypeForQuestionPage eq 'view'}">
        $('#studyEligibiltyTestFormId input,textarea,select')
        .prop('disabled', true);
        $('#studyEligibiltyTestFormId').find('.elaborateClass')
        .addClass('linkDis');
        $('#studyLanguage').prop('disabled', false);
        </c:if>

        $("#shortTitleId").blur(function () {
          if ($(this).val() != oldShortTitle)
            validateShortTitle(this, function (val) {

            });
        });
        $('[data-toggle="tooltip"]').tooltip();
        $("#doneId")
        .click(
            function () {
              $(this).prop("disabled", true);
              validateShortTitle(
                  "#shortTitleId",
                  function (val) {
                    if (val) {
                      $('#shortTitleId')
                      .prop(
                          'disabled',
                          false);
                      if (isFromValid("#studyEligibiltyTestFormId")
                          && chkValidChoosedOption()) {
                        document.studyEligibiltyTestFormId
                        .submit();
                      } else {
                        $("#doneId")
                        .prop(
                            "disabled",
                            false);
                      }
                    } else {
                      $("#doneId").prop(
                          "disabled",
                          false);
                    }
                  });
            });
        $("#saveId")
        .click(function () {
              $(this).prop("disabled", true);
              validateShortTitle(
                  "#shortTitleId",
                  function (val) {
                    if (val) {
                      if (chkValidChoosedOption()) {
                        $(
                            '#studyEligibiltyTestFormId')
                        .validator(
                            'destroy');
                        $('#type').val(
                            'save');
                        $(
                            '#studyEligibiltyTestFormId')
                        .submit();
                      } else {
                        $('#saveId')
                        .prop(
                            "disabled",
                            false);
                      }
                    } else {
                      if ($(
                          '#shortTitleId')
                      .val()) {
                        $(
                            '#shortTitleId')
                        .parent()
                        .addClass(
                            'has-error has-danger')
                        .find(
                            ".help-block")
                        .empty()
                        .append(
                            $(
                                "<ul><li> </li></ul>")
                            .attr(
                                "class",
                                "list-unstyled")
                            .text(
                                "This is a required field."));
                      }
                      $('#saveId').prop(
                          "disabled",
                          false);
                      return false;
                    }
                  });
          $('.fourth').find('span').remove();
            });
      });

  function validateShortTitle(item, callback) {
    var thisAttr = item;
    var shortTitle = $("#shortTitleId").val();
    if (!$('#shortTitleId').is('[readonly]')) {
      if (shortTitle) {
        $('#shortTitleId').prop('disabled', true);
        $
        .ajax({
          url: "/fdahpStudyDesigner/adminStudies/validateEligibilityTestKey.do?_S=${param._S}",
          type: "POST",
          datatype: "json",
          data: {
            shortTitle: shortTitle,
            eligibilityTestId: '${eligibilityTest.id}',
            eligibilityId: '${eligibilityId}'
          },
          beforeSend: function (xhr, settings) {
            xhr.setRequestHeader("X-CSRF-TOKEN",
                "${_csrf.token}");
          },
          success: function (data) {
            var message = data.message;
            $('#shortTitleId').prop('disabled', false);
            if ('SUCCESS' == message) {
              $(thisAttr).validator('validate');
              $(thisAttr).parent().removeClass(
                  "has-danger").removeClass(
                  "has-error");
              $(thisAttr).parent().find(".help-block")
              .empty();
              oldShortTitle = shortTitle;
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
                  $("<ul><li> </li></ul>")
                  .attr("class",
                      "list-unstyled")
                  .text(
                      shortTitle
                      + " has already been used in the past."));
              callback(false);
            }
          },
          error: function () {
            $('#shortTitleId').prop('disabled', false);
          },
          global: false
        });
      } else {
        callback(false);
      }
    } else {
      callback(true);
    }
  }

  function goToBackPage(item) {
    $(item).prop('disabled', true);
    <c:if test="${actionTypeForQuestionPage ne 'view'}">
    bootbox
    .confirm({
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
          let lang = ($('#studyLanguage').val()!==undefined)?$('#studyLanguage').val():'';
          a.href = "/fdahpStudyDesigner/adminStudies/viewStudyEligibilty.do?_S=${param._S}&language="
              + lang;
          document.body.appendChild(a).click();
        } else {
          $(item).prop('disabled', false);
        }
      }
    });
    </c:if>
    <c:if test="${actionTypeForQuestionPage eq 'view'}">
    var a = document.createElement('a');
    a.href = "/fdahpStudyDesigner/adminStudies/viewStudyEligibilty.do?_S=${param._S}";
    document.body.appendChild(a).click();
    </c:if>
  }

  var chkValidChoosedOption = function () {
    let resYesOptVal = $('#resYesOptId').val();
    let resNoOptVal = $('#resNoOptId').val();

    if (resYesOptVal == 'false' && resNoOptVal == 'false') {
      showErrMsg("Both answer options cannot have Fail attribute");
      $("#resYesOptId").parents(".form-group").addClass(
          "has-error has-danger");
      $("#resNoOptId").parents(".form-group").addClass(
          "has-error has-danger");
      return false;
    } else {
      $("#resYesOptId").parents(".form-group").removeClass(
          "has-error has-danger");
      $("#resNoOptId").parents(".form-group").removeClass(
          "has-error has-danger");
      return true;
    }

  }

  //multi language feature enable
  $('#studyLanguage').on('change', function () {
    let currLang = $('#studyLanguage').val();
    $('#currentLanguage').val(currLang);
    refreshAndFetchLanguageData($('#studyLanguage').val());
  })

  function refreshAndFetchLanguageData(language) {
    $.ajax({
      url: '/fdahpStudyDesigner/adminStudies/viewStudyEligibiltyTestQusAns.do?_S=${param._S}',
      type: "GET",
      data: {
        language: language,
        actionTypeForQuestionPage: $('#actionTypeForQuestionPage').val(),
        eligibilityTestId: $('#id').val()
      },
      success: function (data) {
        let htmlData = document.createElement('html');
        htmlData.innerHTML = data;
        if (language !== 'en') {
          updateCompletionTicks(htmlData);
          $('.tit_wrapper').text($('#mlName', htmlData).val());
          $('#shortTitleId').attr('disabled', true);
          $('[data-id="resYesOptId"]').attr('disabled', true).css('background-color', '#eee').css(
              'opacity', '1');
          $('[data-id="resNoOptId"]').attr('disabled', true).css('background-color', '#eee').css(
              'opacity', '1');
          $('#question').val($('#mlQuestion', htmlData).val());
        } else {
          updateCompletionTicksForEnglish();
          $('.tit_wrapper').text($('#customStudyName', htmlData).val());
          $('#shortTitleId').attr('disabled', false);
          $('[data-id="resYesOptId"]').attr('disabled', false).removeAttr('style');
          $('[data-id="resNoOptId"]').attr('disabled', false).removeAttr('style');
          $('#question').val($('#question', htmlData).val());
          <c:if test="${actionTypeForQuestionPage eq 'view'}">
          $('#studyEligibiltyTestFormId input,textarea').prop('disabled', true);  
          </c:if>
        }
      }
    });
  }
</script>