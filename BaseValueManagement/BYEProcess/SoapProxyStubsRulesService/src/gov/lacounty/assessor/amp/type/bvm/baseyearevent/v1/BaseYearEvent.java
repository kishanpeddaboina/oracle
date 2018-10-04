
package gov.lacounty.assessor.amp.type.bvm.baseyearevent.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This is a canonical schema for Base Year Event.
 * 
 * <p>Java class for baseYearEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseYearEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aoid" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="reviewRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="byeId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ownershipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="percentOwnership" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="eventNameId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="eventNameKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventNameLegend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventDescriptionId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="eventDescriptionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventDescriptionLegend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="recordingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="documentNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="sequenceNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="baseYear" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="subUnitNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="subUnitDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partiallyCompleteNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blendedValueFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="percentageAppliedToValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="landValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="improvementValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="landReasonCodeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="landReasonCodeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="landReasonCodeLegend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="improvementReasonCodeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="improvementReasonCodeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="improvementReasonCodeLegend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fixtureValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="retainedLandValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="retainedImprovementValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="retainedFixtureValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ppAssessmentNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personalPropertyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ppDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalAdjAcquisitionCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="percentageFixture" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="effectiveBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="effectiveEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="verificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="eventSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="byeLegacySourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalAIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalBYEId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="rollType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newConstructionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="createBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inactiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="currentOnClose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asmtRollId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseYearEvent", propOrder = {
    "ain",
    "aoid",
    "reviewRequired",
    "byeId",
    "ownershipCode",
    "percentOwnership",
    "eventNameId",
    "eventNameKey",
    "eventNameLegend",
    "eventDescriptionId",
    "eventDescriptionKey",
    "eventDescriptionLegend",
    "eventDate",
    "recordingDate",
    "documentNumber",
    "sequenceNumber",
    "baseYear",
    "subUnitNumber",
    "subUnitDescription",
    "partiallyCompleteNumber",
    "blendedValueFlag",
    "percentageAppliedToValue",
    "landValue",
    "improvementValue",
    "landReasonCodeId",
    "landReasonCodeKey",
    "landReasonCodeLegend",
    "improvementReasonCodeId",
    "improvementReasonCodeKey",
    "improvementReasonCodeLegend",
    "fixtureValue",
    "retainedLandValue",
    "retainedImprovementValue",
    "retainedFixtureValue",
    "ppAssessmentNumber",
    "personalPropertyType",
    "ppDescription",
    "totalAdjAcquisitionCost",
    "percentageFixture",
    "effectiveBeginDate",
    "effectiveEndDate",
    "verificationDate",
    "eventSource",
    "verifiedBy",
    "byeLegacySourceSystem",
    "originalAIN",
    "originalBYEId",
    "rollType",
    "transactionType",
    "userId",
    "newConstructionDate",
    "createBy",
    "createdDate",
    "modifiedBy",
    "inactiveDate",
    "modifiedDate",
    "currentOnClose",
    "notes",
    "asmtRollId"
})
public class BaseYearEvent {

    @XmlElement(required = true)
    protected String ain;
    protected BigInteger aoid;
    protected String reviewRequired;
    @XmlElement(required = true)
    protected BigInteger byeId;
    protected String ownershipCode;
    protected BigDecimal percentOwnership;
    protected BigInteger eventNameId;
    protected String eventNameKey;
    protected String eventNameLegend;
    protected BigInteger eventDescriptionId;
    protected String eventDescriptionKey;
    protected String eventDescriptionLegend;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eventDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar recordingDate;
    protected BigInteger documentNumber;
    protected BigInteger sequenceNumber;
    protected BigInteger baseYear;
    protected BigInteger subUnitNumber;
    protected String subUnitDescription;
    protected String partiallyCompleteNumber;
    protected String blendedValueFlag;
    protected BigDecimal percentageAppliedToValue;
    protected Double landValue;
    protected Double improvementValue;
    protected BigInteger landReasonCodeId;
    protected String landReasonCodeKey;
    protected String landReasonCodeLegend;
    protected BigInteger improvementReasonCodeId;
    protected String improvementReasonCodeKey;
    protected String improvementReasonCodeLegend;
    protected Double fixtureValue;
    protected Double retainedLandValue;
    protected Double retainedImprovementValue;
    protected Double retainedFixtureValue;
    protected String ppAssessmentNumber;
    protected String personalPropertyType;
    protected String ppDescription;
    protected Double totalAdjAcquisitionCost;
    protected BigDecimal percentageFixture;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveBeginDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveEndDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar verificationDate;
    protected String eventSource;
    protected String verifiedBy;
    protected String byeLegacySourceSystem;
    protected String originalAIN;
    protected BigInteger originalBYEId;
    protected String rollType;
    protected String transactionType;
    protected String userId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar newConstructionDate;
    protected String createBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected String modifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inactiveDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String currentOnClose;
    protected String notes;
    protected String asmtRollId;

    /**
     * Gets the value of the ain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAin() {
        return ain;
    }

    /**
     * Sets the value of the ain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAin(String value) {
        this.ain = value;
    }

    /**
     * Gets the value of the aoid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAoid() {
        return aoid;
    }

    /**
     * Sets the value of the aoid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAoid(BigInteger value) {
        this.aoid = value;
    }

    /**
     * Gets the value of the reviewRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewRequired() {
        return reviewRequired;
    }

    /**
     * Sets the value of the reviewRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewRequired(String value) {
        this.reviewRequired = value;
    }

    /**
     * Gets the value of the byeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getByeId() {
        return byeId;
    }

    /**
     * Sets the value of the byeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setByeId(BigInteger value) {
        this.byeId = value;
    }

    /**
     * Gets the value of the ownershipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnershipCode() {
        return ownershipCode;
    }

    /**
     * Sets the value of the ownershipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnershipCode(String value) {
        this.ownershipCode = value;
    }

    /**
     * Gets the value of the percentOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentOwnership() {
        return percentOwnership;
    }

    /**
     * Sets the value of the percentOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentOwnership(BigDecimal value) {
        this.percentOwnership = value;
    }

    /**
     * Gets the value of the eventNameId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEventNameId() {
        return eventNameId;
    }

    /**
     * Sets the value of the eventNameId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEventNameId(BigInteger value) {
        this.eventNameId = value;
    }

    /**
     * Gets the value of the eventNameKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventNameKey() {
        return eventNameKey;
    }

    /**
     * Sets the value of the eventNameKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventNameKey(String value) {
        this.eventNameKey = value;
    }

    /**
     * Gets the value of the eventNameLegend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventNameLegend() {
        return eventNameLegend;
    }

    /**
     * Sets the value of the eventNameLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventNameLegend(String value) {
        this.eventNameLegend = value;
    }

    /**
     * Gets the value of the eventDescriptionId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEventDescriptionId() {
        return eventDescriptionId;
    }

    /**
     * Sets the value of the eventDescriptionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEventDescriptionId(BigInteger value) {
        this.eventDescriptionId = value;
    }

    /**
     * Gets the value of the eventDescriptionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventDescriptionKey() {
        return eventDescriptionKey;
    }

    /**
     * Sets the value of the eventDescriptionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventDescriptionKey(String value) {
        this.eventDescriptionKey = value;
    }

    /**
     * Gets the value of the eventDescriptionLegend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventDescriptionLegend() {
        return eventDescriptionLegend;
    }

    /**
     * Sets the value of the eventDescriptionLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventDescriptionLegend(String value) {
        this.eventDescriptionLegend = value;
    }

    /**
     * Gets the value of the eventDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEventDate() {
        return eventDate;
    }

    /**
     * Sets the value of the eventDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEventDate(XMLGregorianCalendar value) {
        this.eventDate = value;
    }

    /**
     * Gets the value of the recordingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecordingDate() {
        return recordingDate;
    }

    /**
     * Sets the value of the recordingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecordingDate(XMLGregorianCalendar value) {
        this.recordingDate = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDocumentNumber(BigInteger value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNumber(BigInteger value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the baseYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBaseYear() {
        return baseYear;
    }

    /**
     * Sets the value of the baseYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBaseYear(BigInteger value) {
        this.baseYear = value;
    }

    /**
     * Gets the value of the subUnitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSubUnitNumber() {
        return subUnitNumber;
    }

    /**
     * Sets the value of the subUnitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSubUnitNumber(BigInteger value) {
        this.subUnitNumber = value;
    }

    /**
     * Gets the value of the subUnitDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubUnitDescription() {
        return subUnitDescription;
    }

    /**
     * Sets the value of the subUnitDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubUnitDescription(String value) {
        this.subUnitDescription = value;
    }

    /**
     * Gets the value of the partiallyCompleteNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartiallyCompleteNumber() {
        return partiallyCompleteNumber;
    }

    /**
     * Sets the value of the partiallyCompleteNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartiallyCompleteNumber(String value) {
        this.partiallyCompleteNumber = value;
    }

    /**
     * Gets the value of the blendedValueFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlendedValueFlag() {
        return blendedValueFlag;
    }

    /**
     * Sets the value of the blendedValueFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlendedValueFlag(String value) {
        this.blendedValueFlag = value;
    }

    /**
     * Gets the value of the percentageAppliedToValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentageAppliedToValue() {
        return percentageAppliedToValue;
    }

    /**
     * Sets the value of the percentageAppliedToValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentageAppliedToValue(BigDecimal value) {
        this.percentageAppliedToValue = value;
    }

    /**
     * Gets the value of the landValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLandValue() {
        return landValue;
    }

    /**
     * Sets the value of the landValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLandValue(Double value) {
        this.landValue = value;
    }

    /**
     * Gets the value of the improvementValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getImprovementValue() {
        return improvementValue;
    }

    /**
     * Sets the value of the improvementValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setImprovementValue(Double value) {
        this.improvementValue = value;
    }

    /**
     * Gets the value of the landReasonCodeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLandReasonCodeId() {
        return landReasonCodeId;
    }

    /**
     * Sets the value of the landReasonCodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLandReasonCodeId(BigInteger value) {
        this.landReasonCodeId = value;
    }

    /**
     * Gets the value of the landReasonCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandReasonCodeKey() {
        return landReasonCodeKey;
    }

    /**
     * Sets the value of the landReasonCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandReasonCodeKey(String value) {
        this.landReasonCodeKey = value;
    }

    /**
     * Gets the value of the landReasonCodeLegend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandReasonCodeLegend() {
        return landReasonCodeLegend;
    }

    /**
     * Sets the value of the landReasonCodeLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandReasonCodeLegend(String value) {
        this.landReasonCodeLegend = value;
    }

    /**
     * Gets the value of the improvementReasonCodeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getImprovementReasonCodeId() {
        return improvementReasonCodeId;
    }

    /**
     * Sets the value of the improvementReasonCodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setImprovementReasonCodeId(BigInteger value) {
        this.improvementReasonCodeId = value;
    }

    /**
     * Gets the value of the improvementReasonCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImprovementReasonCodeKey() {
        return improvementReasonCodeKey;
    }

    /**
     * Sets the value of the improvementReasonCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImprovementReasonCodeKey(String value) {
        this.improvementReasonCodeKey = value;
    }

    /**
     * Gets the value of the improvementReasonCodeLegend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImprovementReasonCodeLegend() {
        return improvementReasonCodeLegend;
    }

    /**
     * Sets the value of the improvementReasonCodeLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImprovementReasonCodeLegend(String value) {
        this.improvementReasonCodeLegend = value;
    }

    /**
     * Gets the value of the fixtureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFixtureValue() {
        return fixtureValue;
    }

    /**
     * Sets the value of the fixtureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFixtureValue(Double value) {
        this.fixtureValue = value;
    }

    /**
     * Gets the value of the retainedLandValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRetainedLandValue() {
        return retainedLandValue;
    }

    /**
     * Sets the value of the retainedLandValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRetainedLandValue(Double value) {
        this.retainedLandValue = value;
    }

    /**
     * Gets the value of the retainedImprovementValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRetainedImprovementValue() {
        return retainedImprovementValue;
    }

    /**
     * Sets the value of the retainedImprovementValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRetainedImprovementValue(Double value) {
        this.retainedImprovementValue = value;
    }

    /**
     * Gets the value of the retainedFixtureValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRetainedFixtureValue() {
        return retainedFixtureValue;
    }

    /**
     * Sets the value of the retainedFixtureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRetainedFixtureValue(Double value) {
        this.retainedFixtureValue = value;
    }

    /**
     * Gets the value of the ppAssessmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPpAssessmentNumber() {
        return ppAssessmentNumber;
    }

    /**
     * Sets the value of the ppAssessmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPpAssessmentNumber(String value) {
        this.ppAssessmentNumber = value;
    }

    /**
     * Gets the value of the personalPropertyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalPropertyType() {
        return personalPropertyType;
    }

    /**
     * Sets the value of the personalPropertyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalPropertyType(String value) {
        this.personalPropertyType = value;
    }

    /**
     * Gets the value of the ppDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPpDescription() {
        return ppDescription;
    }

    /**
     * Sets the value of the ppDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPpDescription(String value) {
        this.ppDescription = value;
    }

    /**
     * Gets the value of the totalAdjAcquisitionCost property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalAdjAcquisitionCost() {
        return totalAdjAcquisitionCost;
    }

    /**
     * Sets the value of the totalAdjAcquisitionCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalAdjAcquisitionCost(Double value) {
        this.totalAdjAcquisitionCost = value;
    }

    /**
     * Gets the value of the percentageFixture property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentageFixture() {
        return percentageFixture;
    }

    /**
     * Sets the value of the percentageFixture property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentageFixture(BigDecimal value) {
        this.percentageFixture = value;
    }

    /**
     * Gets the value of the effectiveBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveBeginDate() {
        return effectiveBeginDate;
    }

    /**
     * Sets the value of the effectiveBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveBeginDate(XMLGregorianCalendar value) {
        this.effectiveBeginDate = value;
    }

    /**
     * Gets the value of the effectiveEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveEndDate() {
        return effectiveEndDate;
    }

    /**
     * Sets the value of the effectiveEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveEndDate(XMLGregorianCalendar value) {
        this.effectiveEndDate = value;
    }

    /**
     * Gets the value of the verificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerificationDate() {
        return verificationDate;
    }

    /**
     * Sets the value of the verificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerificationDate(XMLGregorianCalendar value) {
        this.verificationDate = value;
    }

    /**
     * Gets the value of the eventSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventSource() {
        return eventSource;
    }

    /**
     * Sets the value of the eventSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventSource(String value) {
        this.eventSource = value;
    }

    /**
     * Gets the value of the verifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedBy() {
        return verifiedBy;
    }

    /**
     * Sets the value of the verifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedBy(String value) {
        this.verifiedBy = value;
    }

    /**
     * Gets the value of the byeLegacySourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getByeLegacySourceSystem() {
        return byeLegacySourceSystem;
    }

    /**
     * Sets the value of the byeLegacySourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByeLegacySourceSystem(String value) {
        this.byeLegacySourceSystem = value;
    }

    /**
     * Gets the value of the originalAIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalAIN() {
        return originalAIN;
    }

    /**
     * Sets the value of the originalAIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalAIN(String value) {
        this.originalAIN = value;
    }

    /**
     * Gets the value of the originalBYEId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOriginalBYEId() {
        return originalBYEId;
    }

    /**
     * Sets the value of the originalBYEId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOriginalBYEId(BigInteger value) {
        this.originalBYEId = value;
    }

    /**
     * Gets the value of the rollType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRollType() {
        return rollType;
    }

    /**
     * Sets the value of the rollType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRollType(String value) {
        this.rollType = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionType(String value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the newConstructionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNewConstructionDate() {
        return newConstructionDate;
    }

    /**
     * Sets the value of the newConstructionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNewConstructionDate(XMLGregorianCalendar value) {
        this.newConstructionDate = value;
    }

    /**
     * Gets the value of the createBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Sets the value of the createBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateBy(String value) {
        this.createBy = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the inactiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInactiveDate() {
        return inactiveDate;
    }

    /**
     * Sets the value of the inactiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInactiveDate(XMLGregorianCalendar value) {
        this.inactiveDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the currentOnClose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentOnClose() {
        return currentOnClose;
    }

    /**
     * Sets the value of the currentOnClose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentOnClose(String value) {
        this.currentOnClose = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the asmtRollId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsmtRollId() {
        return asmtRollId;
    }

    /**
     * Sets the value of the asmtRollId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsmtRollId(String value) {
        this.asmtRollId = value;
    }

}
