package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author BTC The persistent class for the response_type_value database table.
 */
@Entity
@Table(name = "response_type_value")
@NamedQueries({ @NamedQuery(name = "getQuestionResponse", query = "from QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId=:questionsResponseTypeId order by QRBO.responseTypeId DESC"), })
public class QuestionReponseTypeBo implements Serializable {

	private static final long serialVersionUID = 2659206312696342901L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_type_id")
	private Integer responseTypeId;

	@Column(name = "questions_response_type_id")
	private Integer questionsResponseTypeId;

	@Column(name = "max_value")
	private String maxValue;

	@Column(name = "min_value")
	private String minValue;

	@Column(name = "default_value")
	private String defaultValue;

	@Column(name = "step")
	private Integer step;

	@Column(name = "vertical")
	private Boolean vertical;

	@Column(name = "max_desc")
	private String maxDescription;

	@Column(name = "min_desc")
	private String minDescription;

	@Column(name = "min_image")
	private String minImage;

	@Column(name = "max_image")
	private String maxImage;

	@Column(name = "max_fraction_digits")
	private Integer maxFractionDigits;

	@Column(name = "text_choices")
	private String textChoices;

	@Column(name = "selection_style")
	private String selectionStyle;

	@Column(name = "image_size")
	private String imageSize;

	@Column(name = "style")
	private String style;

	@Column(name = "unit")
	private String unit;

	@Column(name = "placeholder")
	private String placeholder;

	@Column(name = "min_date")
	private String minDate;

	@Column(name = "max_date")
	private String maxDate;

	@Column(name = "default_date")
	private String defaultDate;

	@Column(name = "max_length")
	private Integer maxLength;

	@Column(name = "validation_regex")
	private String validationRegex;

	@Column(name = "invalid_message")
	private String invalidMessage;

	@Column(name = "multiple_lines")
	private Boolean multipleLines;

	@Column(name = "measurement_system")
	private String measurementSystem;

	@Column(name = "use_current_location")
	private Boolean useCurrentLocation;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "defalut_time")
	private String defaultTime;

	@Column(name = "validation_condition")
	private String validationCondition;

	@Column(name = "validation_characters")
	private String validationCharacters;

	@Column(name = "validation_except_text")
	private String validationExceptText;

	@Column(name = "formula_based_logic")
	private String formulaBasedLogic = "No";

	@Column(name = "condition_formula")
	private String conditionFormula;

	@Transient
	private MultipartFile minImageFile;

	@Transient
	private MultipartFile maxImageFile;

	public Boolean getActive() {
		return active;
	}

	public String getConditionFormula() {
		return conditionFormula;
	}

	public String getDefaultDate() {
		return defaultDate;
	}

	public String getDefaultTime() {
		return defaultTime;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getFormulaBasedLogic() {
		return formulaBasedLogic;
	}

	public String getImageSize() {
		return imageSize;
	}

	public String getInvalidMessage() {
		return invalidMessage;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public String getMaxDescription() {
		return maxDescription;
	}

	public Integer getMaxFractionDigits() {
		return maxFractionDigits;
	}

	public String getMaxImage() {
		return maxImage;
	}

	public MultipartFile getMaxImageFile() {
		return maxImageFile;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public String getMeasurementSystem() {
		return measurementSystem;
	}

	public String getMinDate() {
		return minDate;
	}

	public String getMinDescription() {
		return minDescription;
	}

	public String getMinImage() {
		return minImage;
	}

	public MultipartFile getMinImageFile() {
		return minImageFile;
	}

	public String getMinValue() {
		return minValue;
	}

	public Boolean getMultipleLines() {
		return multipleLines;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public Integer getQuestionsResponseTypeId() {
		return questionsResponseTypeId;
	}

	public Integer getResponseTypeId() {
		return responseTypeId;
	}

	public String getSelectionStyle() {
		return selectionStyle;
	}

	public Integer getStep() {
		return step;
	}

	public String getStyle() {
		return style;
	}

	public String getTextChoices() {
		return textChoices;
	}

	public String getUnit() {
		return unit;
	}

	public Boolean getUseCurrentLocation() {
		return useCurrentLocation;
	}

	public String getValidationCharacters() {
		return validationCharacters;
	}

	public String getValidationCondition() {
		return validationCondition;
	}

	public String getValidationExceptText() {
		return validationExceptText;
	}

	public String getValidationRegex() {
		return validationRegex;
	}

	public Boolean getVertical() {
		return vertical;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setConditionFormula(String conditionFormula) {
		this.conditionFormula = conditionFormula;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setFormulaBasedLogic(String formulaBasedLogic) {
		this.formulaBasedLogic = formulaBasedLogic;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public void setMaxDescription(String maxDescription) {
		this.maxDescription = maxDescription;
	}

	public void setMaxFractionDigits(Integer maxFractionDigits) {
		this.maxFractionDigits = maxFractionDigits;
	}

	public void setMaxImage(String maxImage) {
		this.maxImage = maxImage;
	}

	public void setMaxImageFile(MultipartFile maxImageFile) {
		this.maxImageFile = maxImageFile;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public void setMeasurementSystem(String measurementSystem) {
		this.measurementSystem = measurementSystem;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public void setMinDescription(String minDescription) {
		this.minDescription = minDescription;
	}

	public void setMinImage(String minImage) {
		this.minImage = minImage;
	}

	public void setMinImageFile(MultipartFile minImageFile) {
		this.minImageFile = minImageFile;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public void setMultipleLines(Boolean multipleLines) {
		this.multipleLines = multipleLines;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void setQuestionsResponseTypeId(Integer questionsResponseTypeId) {
		this.questionsResponseTypeId = questionsResponseTypeId;
	}

	public void setResponseTypeId(Integer responseTypeId) {
		this.responseTypeId = responseTypeId;
	}

	public void setSelectionStyle(String selectionStyle) {
		this.selectionStyle = selectionStyle;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setTextChoices(String textChoices) {
		this.textChoices = textChoices;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUseCurrentLocation(Boolean useCurrentLocation) {
		this.useCurrentLocation = useCurrentLocation;
	}

	public void setValidationCharacters(String validationCharacters) {
		this.validationCharacters = validationCharacters;
	}

	public void setValidationCondition(String validationCondition) {
		this.validationCondition = validationCondition;
	}

	public void setValidationExceptText(String validationExceptText) {
		this.validationExceptText = validationExceptText;
	}

	public void setValidationRegex(String validationRegex) {
		this.validationRegex = validationRegex;
	}

	public void setVertical(Boolean vertical) {
		this.vertical = vertical;
	}

}
