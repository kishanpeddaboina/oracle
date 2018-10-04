package gov.laca.amp.datacapturewccuiapp.model.data;

import java.io.Serializable;

import java.math.BigDecimal;

import java.math.BigInteger;

import java.util.Date;
import java.util.Map;

public class Attribute implements Serializable{
    
    private String id;
    private String name;
    private Boolean visible;
    private Boolean required;
    private String value;
    private String validationMessage;
    private String attributeType;
    private String component;
    private Integer order;
    private String dataType;
    private Integer maxLength;
    private String optionKey;
    private BigInteger intValue;
    private BigDecimal decValue;
    private Date dateValue;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponent() {
        return component;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }


    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }


    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }


    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionKey() {
        return optionKey;
    }


    public void setIntValue(BigInteger intValue) {
        this.intValue = intValue;
    }

    public BigInteger getIntValue() {
        return intValue;
    }

    public void setDecValue(BigDecimal decValue) {
        this.decValue = decValue;
    }

    public BigDecimal getDecValue() {
        return decValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

}
