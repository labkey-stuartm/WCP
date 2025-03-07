<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
    <meta charset="UTF-8">
</head>
<style>
  .langSpecific{
    position: relative;
  }

  .studyLanguage > button::before{
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

  .studyLanguage > button{
    padding-left: 30px;
  }
</style>

<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<div class="col-sm-10 col-rc white-bg p-none">

    <form:form
            action="/fdahpStudyDesigner/adminStudies/saveOrUpdateBasicInfo.do?${_csrf.parameterName}=${_csrf.token}&_S=${param._S}"
            data-toggle="validator" role="form" id="basicInfoFormId" method="post"
            autocomplete="off" enctype="multipart/form-data">
        <!-- Start top tab section-->
        <div class="right-content-head">
            <div class="text-right">
                <div class="black-md-f text-uppercase dis-line pull-left line34">
                    Basic Information
                    <c:set var="isLive">${_S}isLive</c:set>
                        ${not empty  sessionScope[isLive]?'<span class="eye-inc ml-sm vertical-align-text-top"></span>':''}
                </div>

                <c:if test="${studyBo.multiLanguageFlag eq true}">
                    <div class="dis-line form-group mb-none mr-sm" style="width: 150px;">
                        <select
                                class="selectpicker aq-select aq-select-form studyLanguage langSpecific"
                                id="studyLanguage" name="studyLanguage" title="Select">
                            <option value="en" ${((currLanguage eq null) or (currLanguage eq '') or (currLanguage eq 'en')) ?'selected':''}>
                                English
                            </option>
                            <c:forEach items="${languageList}" var="language">
                                <option value="${language.key}"
                                    ${currLanguage eq language.key ?'selected':''}>${language.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <div class="dis-line form-group mb-none mr-sm">
                    <button type="button" class="btn btn-default gray-btn cancelBut">Cancel</button>
                </div>
                <c:if test="${empty permission}">
                    <div class="dis-line form-group mb-none mr-sm">
                        <button type="button" class="btn btn-default gray-btn actBut"
                                id="saveId"
                                <c:if test="${not studyBo.viewPermission }">disabled</c:if>>Save
                        </button>
                    </div>

                    <div class="dis-line form-group mb-none">
                        <button type="button" class="btn btn-primary blue-btn actBut"
                                id="completedId"
                                <c:if test="${not studyBo.viewPermission }">disabled</c:if>>Mark
                            as Completed
                        </button>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- End top tab section-->
        <input type="hidden" id="sId" value="${studyBo.id}" name="id"/>
        <input type="hidden" value="" id="buttonText" name="buttonText">
        <input type="hidden" id="mlName" value="${studyLanguageBO.name}"/>
        <input type="hidden" id="mlFullName" value="${studyLanguageBO.fullName}"/>
        <input type="hidden" id="mlStudyTagline" value="${studyLanguageBO.studyTagline}"/>
        <input type="hidden" id="mlDescription" value="${studyLanguageBO.description}"/>
        <input type="hidden" id="mlResearchSponsor" value="${studyLanguageBO.researchSponsor}"/>
        <input type="hidden" id="currentLanguage" name="currentLanguage" value="${currLanguage}"/>
        <!-- Start body tab section -->
        <div class="right-content-body col-xs-12">
            <div class="col-md-12 p-none">
                <div class="col-md-6 pl-none">
                    <div class="gray-xs-f mb-xs">
                        Study ID <small>(15 characters max)</small><span
                            class="requiredStar"> *</span>
                    </div>
                    <div class="form-group customStudyClass">
                        <input type="text" custAttType="cust" autofocus="autofocus"
                               class="form-control aq-inp studyIdCls" name="customStudyId"
                               id="customStudyId" maxlength="15"
                               value="${fn:escapeXml(studyBo.customStudyId)}"
                            <%-- <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated' || studyBo.status == 'Pre-launch(Published)') && studyMode eq 'liveMode'}"> disabled</c:if> --%>
                                <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated' || studyBo.status == 'Pre-launch(Published)') }"> disabled</c:if>
                            <%-- <c:if test="${studyMode eq 'testMode' && not empty studyBo.status && studyBo.status == 'Paused'}"> disabled</c:if> --%>
                               required/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <div class="col-md-6 pr-none">
                    <div class="gray-xs-f mb-xs">
                        App ID <small>(15 characters max)</small><span
                            class="requiredStar"> *</span><span><span
                            data-toggle="tooltip" data-placement="top"
                            title="Enter a unique human-readable identifier corresponding to the app that this study must belong to."
                            class="filled-tooltip"></span></span>
                    </div>
                    <div class="form-group">
                        <input type="text" custAttType="cust" autofocus="autofocus"
                               class="form-control aq-inp appIdCls" name="appId" id="appId"
                               maxlength="15" value="${studyBo.appId}"
                            <%-- <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated' || studyBo.status == 'Pre-launch(Published)') && studyMode eq 'liveMode'}"> disabled</c:if> --%>
                                <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated' || studyBo.status == 'Pre-launch(Published)') }"> disabled</c:if>
                            <%-- <c:if test="${studyMode eq 'testMode' && not empty studyBo.status && studyBo.status == 'Paused'}"> disabled</c:if> --%>
                               required/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                    <%--				<div class="col-md-6 pl-none">--%>
                    <%--					<div class="gray-xs-f mb-xs">--%>
                    <%--						Study Language<span class="requiredStar"> *</span><span><span--%>
                    <%--							data-toggle="tooltip" data-placement="top"--%>
                    <%--							title="Select the language that the study should be made available in on the mobile app. Ensure you create all study content in the same language."--%>
                    <%--							class="filled-tooltip"></span></span>--%>
                    <%--					</div>--%>
                    <%--					<div class="form-group">--%>
                    <%--						<select--%>
                    <%--							class="selectpicker aq-select aq-select-form studyLanguage"--%>
                    <%--							id="studyLanguage" name="studyLanguage" required title="Select"--%>
                    <%--							<c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated' || studyBo.status == 'Pre-launch(Published)') }"> disabled</c:if>>--%>
                    <%--							<c:forEach items="${languageList}" var="language">--%>
                    <%--								<option value="${language}"--%>
                    <%--									${studyBo.studyLanguage eq language ?'selected':''}>${language}</option>--%>
                    <%--							</c:forEach>--%>
                    <%--						</select>--%>
                    <%--						<div class="help-block with-errors red-txt"></div>--%>
                    <%--					</div>--%>
                    <%--				</div>--%>
                <div class="col-md-12 p-none">
                    <div class="gray-xs-f mb-xs">
                        Study Name <small>(50 characters max)</small><span
                            class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control langSpecific" name="name"
                               id="customStudyName" value="${fn:escapeXml(studyBo.name)}"
                               maxlength="50" required/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-12 p-none">
                <div class="gray-xs-f mb-xs">
                    Study full name <small>(150 characters max)</small><span
                        class="requiredStar"> *</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control langSpecific" name="fullName"
                           value="${fn:escapeXml(studyBo.fullName)}" maxlength="150" required/>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>

            <div class="col-md-12 p-none">
                <div class="col-md-6 pl-none">
                    <div class="gray-xs-f mb-xs">
                        Study Category<span class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
                        <select
                                class="selectpicker aq-select aq-select-form elaborateClass"
                                id="category" name="category" required title="Select">
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category.id}"
                                    ${studyBo.category eq category.id ?'selected':''}>${category.value}</option>
                            </c:forEach>
                        </select>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <div class="col-md-6 pr-none">
                    <div class="gray-xs-f mb-xs">
                        Research Sponsor <small>(100 characters max)</small><span
                            class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
                        <input type="text" id="researchSponsor" class="form-control langSpecific"
                               name="researchSponsor"
                               value="${fn:escapeXml(studyBo.researchSponsor)}" maxlength="100"
                               required/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-12 p-none">
                <div class="col-md-6 pl-none">
                    <div class="gray-xs-f mb-xs">
                        Data Partner<span class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
                        <select class="selectpicker elaborateClass" id="dataPartnerId"
                                multiple="multiple" title="Select"
                                data-none-selected-text="Select" name="dataPartner" required>
                            <c:forEach items="${dataPartnerList}" var="datapartner">
                                <option value="${datapartner.id}"
                                    ${fn:contains(studyBo.dataPartner , datapartner.id ) ? 'selected' : ''}>${datapartner.value}</option>
                            </c:forEach>
                        </select>
                        <div class="help-block with-errors red-txt"></div>
                    </div>

                </div>
                <div class="col-md-6 pr-none">
                    <div class="gray-xs-f mb-xs">
                        Tentative Duration <small>(3 numbers max)</small><span
                            class="requiredStar"> *</span>
                    </div>
                    <div class="form-group col-md-4 p-none mr-md mb-none">
                        <input type="text" class="form-control" name="tentativeDuration"
                               value="${studyBo.tentativeDuration}" maxlength="3" required
                               pattern="^(0{0,2}[1-9]|0?[1-9][0-9]|[1-9][0-9][0-9])$"
                               data-pattern-error="Please enter valid number."/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                    <div class="form-group col-md-4 p-none mb-none">
                        <select class="selectpicker elaborateClass" required
                                title="Select" name="tentativeDurationWeekmonth"
                                id="tentativeDurationWeekmonth">
                            <option value="Days"
                                ${studyBo.tentativeDurationWeekmonth eq 'Days'?'selected':''}>Days
                            </option>
                            <option value="Weeks"
                                ${studyBo.tentativeDurationWeekmonth eq 'Weeks'?'selected':''}>Weeks
                            </option>
                            <option value="Months"
                                ${studyBo.tentativeDurationWeekmonth eq 'Months'?'selected':''}>
                                Months
                            </option>
                            <option value="Years"
                                ${studyBo.tentativeDurationWeekmonth eq 'Years'?'selected':''}>Years
                            </option>
                        </select>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-12 p-none">
                <div class="gray-xs-f mb-xs">
                    Study Tagline <small>(100 characters max) </small><span
                        class="requiredStar"> *</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control langSpecific" name="studyTagLine"
                           value="${fn:escapeXml(studyBo.studyTagLine)}" maxlength="100"
                           required/>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>

            <div class="col-md-12 p-none">
                <div class="gray-xs-f mb-xs">
                    Description<span class="requiredStar"> *</span>
                </div>
                <div class="form-group">
					<textarea class="form-control langSpecific" id="editor" name="description"
                              required>${studyBo.description}</textarea>
                    <div class="help-block with-errors red-txt"></div>
                </div>
            </div>
            <div class="col-md-12 p-none">
                <div class="col-md-6 pl-none">
                    <div class="gray-xs-f mb-xs">
                        Study website <span>(e.g: http://www.google.com) </span> <small>(100
                        characters max)</small>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="studyWebsiteId"
                               name="studyWebsite" value="${fn:escapeXml(studyBo.studyWebsite)}"
                               pattern="^(http:\/\/|https:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$"
                               title="Include http://" maxlength="100"
                               data-pattern-error="Please enter a valid URL"/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <div class="col-md-6 pr-none">
                    <div class="gray-xs-f mb-xs">
                        Study feedback destination inbox email address <small>(100
                        characters max) </small><span class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="inboxEmailAddress"
                               value="${fn:escapeXml(studyBo.inboxEmailAddress)}" required
                               maxlength="100"
                               pattern="[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                               autocomplete="off" data-pattern-error="Email address is invalid"/>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-12 p-none mb-xxlg">
                <div class="col-md-6 pl-none">
                    <div class="gray-xs-f mb-xs">
                        Study type<span class="requiredStar"> *</span>
                    </div>
                    <div class="form-group">
						<span class="radio radio-info radio-inline p-45"> <input
                                type="radio" id="inlineRadio5"
                                class="rejoin_radio studyTypeClass" name="type" value="GT"
                            ${studyBo.type eq 'GT'?'checked':""} required
                        <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated')}">
                                disabled </c:if>>
							<label for="inlineRadio5">Gateway</label>
						</span> <span class="radio radio-inline"> <input type="radio"
                                                                         id="inlineRadio6"
                                                                         class="rejoin_radio studyTypeClass"
                                                                         name="type"
                                                                         value="SD" ${studyBo.type eq 'SD'?'checked':""}
                                                                         required
                    <c:if test="${not empty studyBo.status && (studyBo.status == 'Active' || studyBo.status == 'Published' || studyBo.status == 'Paused' || studyBo.status == 'Deactivated')}">
                                                                         disabled </c:if>>
							<label for="inlineRadio6">Standalone</label>
						</span>
                        <div class="help-block with-errors red-txt"></div>
                    </div>
                </div>
                <div class="col-md-6 pr-none thumbImageDIv">
                    <div class="gray-xs-f mb-sm">
                        Study Thumbnail Image <span> <span class="filled-tooltip"
                                                           data-toggle="tooltip"
                                                           data-placement="top" data-html="true"
                                                           title="<span class='font24 text-weight-light pull-left'></span> JPEG / PNG<br><span class='font20'></span> Recommended Size: 225x225 pixels"/></span></span>
                        <span class="requiredStar thumbDivClass"
                              style="color: red; display: none"> *</span>
                    </div>
                    <div>
                        <div class="thumb">
                            <img
                                    <c:if test="${not empty studyBo.thumbnailImage}">src="<spring:eval expression="@propertyConfigurer.getProperty('fda.imgDisplaydPath')" />studylogo/${fn:escapeXml(studyBo.thumbnailImage)}"
                            </c:if>
                                    <c:if test="${empty studyBo.thumbnailImage}">src="/fdahpStudyDesigner/images/dummy-img.jpg" </c:if>
                                    onerror="this.src='/fdahpStudyDesigner/images/dummy-img.jpg';"
                                    class="wid100"/>
                        </div>
                        <div class="dis-inline ">
							<span id="removeUrl" class="blue-link elaborateHide">X<a
                                    href="javascript:void(0)"
                                    class="blue-link txt-decoration-underline pl-xs">Remove
									Image</a></span>
                            <div class="form-group mb-none mt-sm">
                                <button id="uploadImgbtn" type="button"
                                        class="btn btn-default gray-btn imageButtonDis">Upload
                                    Image
                                </button>
                                <span><span
                                        class="help-block with-errors red-txt pos-inherit"></span></span>
                                <input
                                        id="uploadImg" class="dis-none" type="file" name="file"
                                        accept=".png, .jpg, .jpeg" onchange="readURL(this);"
                                        required>
                                <input type="hidden" value="${studyBo.thumbnailImage}"
                                       id="thumbnailImageId" name="thumbnailImage"/>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End body tab section -->
    </form:form>
</div>
<!-- End right Content here -->

<script>
  $(document).ready(function () {
    $('#removeUrl').css("visibility", "hidden");
    var file = $('#uploadImg').val();
    var thumbnailImageId = $('#thumbnailImageId').val();
    if (file || thumbnailImageId) {
      $('#removeUrl').css("visibility", "visible");
    }

    let currLang = $('#studyLanguage').val();
    if (currLang !== undefined && currLang !== null && currLang !== '' && currLang !== 'en') {
      $('#currentLanguage').val(currLang);
      refreshAndFetchLanguageData(currLang);
    }

    <c:if test="${not empty permission}">
    $('#basicInfoFormId input,textarea,select').prop('disabled', true);
    $('#basicInfoFormId').find('.elaborateClass').addClass('linkDis');
    $('.elaborateHide').css('visibility', 'hidden');
    $('.imageButtonDis').prop('disabled', true);
    $('#studyLanguage').removeAttr('disabled');
    </c:if>

    var studyType = '${fn:escapeXml(studyBo.type)}';
    if (studyType) {
      if (studyType === 'GT') {
        $('.thumbDivClass').show();
      } else {
        $('.thumbDivClass').hide();
      }
    }

    checkRadioRequired();
    $(".rejoin_radio").click(function () {
      checkRadioRequired();
    })

    $("[data-toggle=tooltip]").tooltip();

//             //wysiwyg editor
    if ($("#editor").length > 0) {
      tinymce.init({
        selector: "#editor",
        theme: "modern",
        skin: "lightgray",
        height: 180,
        plugins: [
          "advlist autolink link image lists charmap hr anchor pagebreak spellchecker",
          "save contextmenu directionality paste"
        ],
        toolbar: "anchor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | underline link | hr removeformat | cut undo redo",
        menubar: false,
        toolbar_items_size: 'small',
        content_style: "div, p { font-size: 13px;letter-spacing: 1px;}",
        <c:if test="${not empty permission}">readonly: 1, </c:if>
        setup: function (ed) {
          ed.on('keypress change', function (ed) {
            resetValidation($('#editor').val(tinyMCE.get("editor").getContent()).parents('form'));
          });
        }
      });
    }
    // File Upload
    $("#uploadImgbtn").click(function () {
      $("#uploadImg").click();
    });

    // Removing selected file upload image
    $("#removeUrl").click(function () {
      $(".thumb img").attr("src", "/fdahpStudyDesigner/images/dummy-img.jpg");
      $('#uploadImg').val('');
      $('#thumbnailImageId').val('');
      var file = $('#uploadImg').val();
      var thumbnailImageId = $('#thumbnailImageId').val();
      if (file || thumbnailImageId) {
        $("#uploadImg").removeAttr('required');
        resetValidation($("#uploadImg").parents('form'));
        $('#removeUrl').css("visibility", "visible");
      } else {
        $("#uploadImg").attr('required', 'required');
        resetValidation($("#uploadImg").parents('form'));
        $('#removeUrl').css("visibility", "hidden");
      }
    });

    $("#completedId").on('click', function (e) {
      e.preventDefault();
      var type = $("input[name='type']:checked").val();
      if (type && type == 'GT') {
        validateStudyId('', function (st) {
          if (st) {
            var studyCount = $('.customStudyClass').find('.help-block').children().length;
            if (parseInt(studyCount) >= 1) {
              return false;
            } else {
              $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop('disabled',
                  false);
              if (isFromValid("#basicInfoFormId"))
                validateAppId('', function (valid) {
                  if (valid) {
                    var appCount = $('.appIdClass').find('.help-block').children().length;
                    if (parseInt(appCount) >= 1) {
                      return false;
                    } else {
                      $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop(
                          'disabled', false);
                      if (isFromValid("#basicInfoFormId"))
                        var file = $('#uploadImg').val();
                      var thumbnailImageId = $('#thumbnailImageId').val();
                      if (file || thumbnailImageId) {
                        $("#uploadImg").parent().find(".help-block").empty();
                        $("#uploadImg").removeAttr('required');
                        validateStudyId('', function (st) {
                          if (st) {
                            var studyCount = $('.customStudyClass').find(
                                '.help-block').children().length;
                            if (parseInt(studyCount) >= 1) {
                              return false;
                            } else {
                              validateAppId('', function (valid) {
                                if (valid) {
                                  var appCount = $('.appIdClass').find(
                                      '.help-block').children().length;
                                  if (parseInt(appCount) >= 1) {
                                    return false;
                                  } else {
                                    $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop(
                                        'disabled', false);
                                    if (isFromValid("#basicInfoFormId")) {

                                      var richText = tinymce.get('editor').getContent(
                                          {format: 'raw'});
                                      var escaped = $('#editor').text(richText).html();
                                      tinymce.get('editor').setContent(escaped);

                                      $("#buttonText").val('completed');
                                      $("#basicInfoFormId").submit();
                                    }
                                  }
                                }
                              });
                            }
                          }
                        });
                      }
                    }
                  }
                });
            }
          } else {
            isFromValid("#basicInfoFormId");
          }
        });
      } else {
        $("#uploadImg").parent().find(".help-block").empty();
        validateStudyId('', function (st) {
          if (st) {
            var studyCount = $('.customStudyClass').find('.help-block').children().length;
            if (parseInt(studyCount) >= 1) {
              return false;
            } else {
              $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop('disabled',
                  false);
              if (isFromValid("#basicInfoFormId")) ;
              validateAppId('', function (valid) {
                if (valid) {
                  var appCount = $('.appIdClass').find('.help-block').children().length;
                  if (parseInt(appCount) >= 1) {
                    return false;
                  } else {
                    $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop(
                        'disabled', false);
                    if (isFromValid("#basicInfoFormId")) {

                      var richText = tinymce.get('editor').getContent({format: 'raw'});
                      var escaped = $('#editor').text(richText).html();
                      tinymce.get('editor').setContent(escaped);

                      $("#buttonText").val('completed');
                      $("#basicInfoFormId").submit();
                    }
                  }
                }
              });
            }
          } else {
            isFromValid("#basicInfoFormId");
          }
        });
      }
    });
    $("#uploadImg").on('change', function (e) {
      var type = $("input[name='type']:checked").val();
      if (null != type && type != '' && typeof type != 'undefined' && type == 'GT') {
        var file = $('#uploadImg').val();
        var thumbnailImageId = $('#thumbnailImageId').val();
        if (file || thumbnailImageId) {
          $('#removeUrl').css("visibility", "visible");
          $("#uploadImg").parent().find(".help-block").empty();
        }
      } else {
        $('#removeUrl').css("visibility", "visible");
        $("#uploadImg").parent().find(".help-block").empty();
      }
    });
    $('#saveId').click(function (e) {
      $('#basicInfoFormId').validator('destroy').validator();
      validateStudyId('', function (st) {
        if (st) {
          var studyCount = $('.customStudyClass').find('.help-block').children().length;
          if (parseInt(studyCount) >= 1) {
            return false;
          } else if (!$('#customStudyName')[0].checkValidity()) {
            $("#customStudyName").parent().addClass('has-error has-danger').find(
                ".help-block").empty().append(
                $("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                    "This is a required field."));
            return false;
          } else {
            var appId = $('#appId').val();
            if (null != appId && appId != '' && typeof appId != 'undefined') {
              validateAppId('', function (valid) {
                if (valid) {
                  $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop(
                      'disabled', false);
                  $('#basicInfoFormId').validator('destroy');
                  $("#buttonText").val('save');

                  var richText = tinymce.get('editor').getContent({format: 'raw'});
                  if (null != richText && richText != '' && typeof richText != 'undefined'
                      && richText != '<p><br data-mce-bogus="1"></p>') {
                    var escaped = $('#editor').text(richText).html();
                    tinymce.get('editor').setContent(escaped);
                  }
                  $('#basicInfoFormId').submit();
                } else {
                  $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop(
                      'disabled', false);
                  $('#basicInfoFormId').validator('destroy');
                  $("#buttonText").val('save');

                  var richText = tinymce.get('editor').getContent({format: 'raw'});
                  if (null != richText && richText != '' && typeof richText != 'undefined'
                      && richText != '<p><br data-mce-bogus="1"></p>') {
                    var escaped = $('#editor').text(richText).html();
                    tinymce.get('editor').setContent(escaped);
                  }

                  $('#basicInfoFormId').submit();
                }
              });
            } else {
              $('.studyTypeClass,.studyIdCls,.appIdCls,.orgIdCls,.studyLanguage').prop('disabled',
                  false);
              $('#basicInfoFormId').validator('destroy');
              $("#buttonText").val('save');

              var richText = tinymce.get('editor').getContent({format: 'raw'});
              if (null != richText && richText != '' && typeof richText != 'undefined' && richText
                  != '<p><br data-mce-bogus="1"></p>') {
                var escaped = $('#editor').text(richText).html();
                tinymce.get('editor').setContent(escaped);
              }

              $('#basicInfoFormId').submit();
            }
          }
        } else {
          var studyCount = $('.customStudyClass').find('.help-block').children().length;
          if (parseInt(studyCount) >= 1) {
            return false;
          } else {
            $('#basicInfoFormId').validator('destroy').validator();
            if (!$('#customStudyId')[0].checkValidity()) {
              $("#customStudyId").parent().addClass('has-error has-danger').find(
                  ".help-block").empty().append(
                  $("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                      "This is a required field."));
              return false;
            }
          }
        }
      });
    });
    $(".studyIdCls").blur(function () {
      validateStudyId('', function (val) {
      });
    });
    $(".appIdCls").blur(function () {
      validateAppId('', function (val) {
      });
    });
    $("#inlineRadio6").change(function () {
      validateAppId('', function (val) {
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

  $("#uploadImg").change(function (e) {
    var file, img;
    if ((file = this.files[0])) {
      img = new Image();
      img.onload = function () {
        var ht = this.height;
        var wds = this.width;
        if (ht == 225 && wds == 225) {
          $("#uploadImg").parent().find(".help-block").append('');
          $('#removeUrl').css("visibility", "visible");
        } else {
          $("#uploadImg").parent().find(".help-block").empty()
          .append($("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
              "Please upload image as per provided guidelines."));
          $(".thumb img").attr("src", "/fdahpStudyDesigner/images/dummy-img.jpg");
          $('#uploadImg, #thumbnailImageId').val('');
          $('#removeUrl').css("visibility", "hidden");
        }
        var file = $('#uploadImg').val();
        var thumbnailImageId = $('#thumbnailImageId').val();
        if (file || thumbnailImageId) {
          $("#uploadImg").removeAttr('required');
          resetValidation($("#uploadImg").parents('form'));
        } else {
          $("#uploadImg").attr('required', 'required');
          resetValidation($("#uploadImg").parents('form'));
        }
      };
      img.onerror = function () {
        $("#uploadImg").parent().find(".help-block").empty()
        .append($("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
            "Please upload image as per provided guidelines."));
        $('#removeUrl').css("visibility", "hidden");
        $(".thumb img").attr("src", "/fdahpStudyDesigner/images/dummy-img.jpg");
        $('#uploadImg, #thumbnailImageId').val('');
        var file = $('#uploadImg').val();
        var thumbnailImageId = $('#thumbnailImageId').val();
        if (file || thumbnailImageId) {
          $("#uploadImg").removeAttr('required');
          resetValidation($("#uploadImg").parents('form'));
        } else {
          $("#uploadImg").attr('required', 'required');
          resetValidation($("#uploadImg").parents('form'));
        }
      };
      img.src = _URL.createObjectURL(file);
    }
  });
  $("#uploadImg, #thumbnailImageId").change(function () {
    var file = $('#uploadImg').val();
    var thumbnailImageId = $('#thumbnailImageId').val();
    if (file || thumbnailImageId) {
      $("#uploadImg").removeAttr('required');
      resetValidation($("#uploadImg").parents('form'));
    } else {
      $("#uploadImg").attr('required', 'required');
      resetValidation($("#uploadImg").parents('form'));
    }
  });

  function validateStudyId(item, callback) {
    var customStudyId = $("#customStudyId").val();
    var thisAttr = $("#customStudyId");
    var dbcustomStudyId = '${fn:escapeXml(studyBo.customStudyId)}';
    if (customStudyId != null && customStudyId != '' && typeof customStudyId != 'undefined') {
      if (dbcustomStudyId != customStudyId) {
        $.ajax({
          url: "/fdahpStudyDesigner/adminStudies/validateStudyId.do?_S=${param._S}",
          type: "POST",
          datatype: "json",
          data: {
            customStudyId: customStudyId,
            "${_csrf.parameterName}": "${_csrf.token}",
          },
          success: function getResponse(data) {
            var message = data.message;
            if ('SUCCESS' != message) {
              $(thisAttr).validator('validate');
              $(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
              $(thisAttr).parent().find(".help-block").empty();
              callback(true);
            } else {
              $(thisAttr).val('');
              $(thisAttr).parent().addClass("has-danger").addClass("has-error");
              $(thisAttr).parent().find(".help-block").empty();
              $(thisAttr).parent().find(".help-block").empty()
              .append($("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                  customStudyId
                  + " has already been used in the past."));
              callback(false);
            }
          },
          global: false
        });
      } else {
        callback(true);
      }
    } else {
      callback(false);
    }
  }

  function checkRadioRequired() {
    var rejoinRadioVal = $('input[name=type]:checked').val();
    if (rejoinRadioVal == 'GT') {
      $('.thumbDivClass').show();
      $('.thumbImageDIv').show();
      $('#uploadImg').attr('required', 'required');
      var file = $('#uploadImg').val();
      var thumbnailImageId = $('#thumbnailImageId').val();
      if (file || thumbnailImageId) {
        $("#uploadImg").removeAttr('required');
        resetValidation($("#uploadImg").parents('form'));
      } else {
        $("#uploadImg").attr('required', 'required');
        resetValidation($("#uploadImg").parents('form'));
      }
    } else {
      $('.thumbDivClass').hide();
      $('.thumbImageDIv').hide();
      $('#uploadImg').removeAttr('required', '');
      resetValidation($("#uploadImg").parents('form'));
    }
  }

  function validateAppId(item, callback) {
    var appId = $("#appId").val();
    var studyType = $('input[name=type]:checked').val();
    var thisAttr = $("#appId");
    var customStudyId = $("#customStudyId").val();
    if (appId != null && appId != '' && typeof appId != 'undefined') {
      $.ajax({
        url: "/fdahpStudyDesigner/adminStudies/validateAppId.do?_S=${param._S}",
        type: "POST",
        datatype: "json",
        data: {
          customStudyId: customStudyId,
          appId: appId,
          studyType: studyType,
          "${_csrf.parameterName}": "${_csrf.token}",
        },
        success: function getResponse(data) {
          var message = data.message;
          if ('SUCCESS' != message) {
            $(thisAttr).validator('validate');
            $(thisAttr).parent().removeClass("has-danger").removeClass("has-error");
            $(thisAttr).parent().find(".help-block").empty();
            callback(true);
          } else {
            $(thisAttr).val('');
            $(thisAttr).parent().addClass("has-danger").addClass("has-error");
            $(thisAttr).parent().find(".help-block").empty();
            $(thisAttr).parent().find(".help-block").empty()
            .append($("<ul><li> </li></ul>").attr("class", "list-unstyled").text(
                appId
                + " has already been used in the past.</li></ul>"));
            callback(false);
          }
        },
        global: false
      });
    } else {
      callback(false);
    }
  }

  $('#studyLanguage').on('change', function () {
    let currLang = $('#studyLanguage').val();
    $('#currentLanguage').val(currLang);
    refreshAndFetchLanguageData($('#studyLanguage').val());
  })

  function refreshAndFetchLanguageData(language) {
    $.ajax({
      url: '/fdahpStudyDesigner/adminStudies/viewBasicInfo.do?_S=${param._S}',
      type: "GET",
      data: {
        language: language
      },
      success: function (data) {
        let htmlData = document.createElement('html');
        htmlData.innerHTML = data;
        if (language !== 'en') {
          $('.tit_wrapper').text($('#mlName', htmlData).val());
          updateCompletionTicks(htmlData);
          $('select, input[type!=hidden]').each(function () {
            if (!$(this).hasClass('langSpecific')) {
              $(this).attr('disabled', true);
              if (this.nodeName.toLowerCase() === 'select') {
                let id = this.id;
                if (id !== undefined && id !== '') {
                  $('[data-id=' + id + ']').addClass('cursor-none');
                }
              }
            }
          });
          $('#customStudyName').val($('input#mlName', htmlData).val());
          $('input[name="fullName"]').val($('input#mlFullName', htmlData).val());
          $('input[name="studyTagLine"]').val($('input#mlStudyTagline', htmlData).val());
          $('#researchSponsor').val($('input#mlResearchSponsor', htmlData).val());
          $('#editor').val($('input#mlDescription', htmlData).val());
          let tinyMce = tinymce.activeEditor;
          if (tinyMce !== undefined) {
            tinyMce.setContent($('input#mlDescription', htmlData).val());
          }
          $('#removeUrl').addClass('cursor-none');
          $('[data-id="tentativeDurationWeekmonth"], [data-id="dataPartnerId"]').css(
              'background-color', '#eee').css('opacity', '1').addClass('cursor-none');
          $('#uploadImgbtn').css('background-color', '#eee').css('opacity', '1').addClass(
              'cursor-none');
        } else {
          $('.tit_wrapper').text($('#customStudyName', htmlData).val());
          updateCompletionTicksForEnglish();
          $('select, input[type!=hidden]').each(function () {
            if (!$(this).hasClass('langSpecific')) {
              $(this).attr('disabled', false);
              if (this.nodeName.toLowerCase() === 'select') {
                let id = this.id;
                if (id !== undefined && id !== '') {
                  $('[data-id=' + id + ']').removeClass('cursor-none');
                }
              }
            }
          });
          $('#customStudyName').val($('input#customStudyName', htmlData).val());
          $('input[name="fullName"]').val($('input[name="fullName"]', htmlData).val());
          $('input[name="studyTagLine"]').val($('input[name="studyTagLine"]', htmlData).val());
          $('#researchSponsor').val($('#researchSponsor', htmlData).val());
          $('#editor').val($('#editor', htmlData).val());
          tinymce.activeEditor.setContent($('#editor', htmlData).val());
          $('#removeUrl').removeClass('cursor-none');
          $('[data-id="tentativeDurationWeekmonth"], [data-id="dataPartnerId"]').removeAttr(
              'style').removeClass('cursor-none');
          $('#uploadImgbtn').removeAttr('style').removeClass('cursor-none');
          
          <c:if test="${permission == 'view' }">
          $('#basicInfoFormId input,textarea').prop('disabled', true);
          $('#removeUrl').addClass('cursor-none');
          </c:if>
        }
      }
    });
  }
</script>