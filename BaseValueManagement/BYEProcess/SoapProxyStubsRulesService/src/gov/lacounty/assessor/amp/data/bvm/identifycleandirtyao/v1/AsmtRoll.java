
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AsmtRoll complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AsmtRoll">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billNumber" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="billType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="improvementReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="improvementValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="landReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="landValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="newConstructionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="percentPartialInterest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taxYear" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="seqNum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="impValuationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="landValuationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AsmtRoll", propOrder = {
    "billNumber",
    "billType",
    "improvementReasonCode",
    "improvementValue",
    "landReasonCode",
    "landValue",
    "newConstructionType",
    "billStatus",
    "percentPartialInterest",
    "taxYear",
    "seqNum",
    "impValuationDate",
    "landValuationDate",
    "id"
})
public class AsmtRoll {

    protected BigInteger billNumber;
    protected String billType;
    protected String improvementReasonCode;
    protected Double improvementValue;
    protected String landReasonCode;
    protected Double landValue;
    protected String newConstructionType;
    protected String billStatus;
    protected String percentPartialInterest;
    protected BigInteger taxYear;
    protected BigInteger seqNum;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar impValuationDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar landValuationDate;
    protected BigInteger id;

    /**
     * Gets the value of the billNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBillNumber() {
        return billNumber;
    }

    /**
     * Sets the value of the billNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBillNumber(BigInteger value) {
        this.billNumber = value;
    }

    /**
     * Gets the value of the billType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillType() {
        return billType;
    }

    /**
     * Sets the value of the billType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillType(String value) {
        this.billType = value;
    }

    /**
     * Gets the value of the improvementReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImprovementReasonCode() {
        return improvementReasonCode;
    }

    /**
     * Sets the value of the improvementReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImprovementReasonCode(String value) {
        this.improvementReasonCode = value;
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
     * Gets the value of the landReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandReasonCode() {
        return landReasonCode;
    }

    /**
     * Sets the value of the landReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandReasonCode(String value) {
        this.landReasonCode = value;
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
     * Gets the value of the newConstructionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewConstructionType() {
        return newConstructionType;
    }

    /**
     * Sets the value of the newConstructionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewConstructionType(String value) {
        this.newConstructionType = value;
    }

    /**
     * Gets the value of the billStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillStatus() {
        return billStatus;
    }

    /**
     * Sets the value of the billStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillStatus(String value) {
        this.billStatus = value;
    }

    /**
     * Gets the value of the percentPartialInterest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentPartialInterest() {
        return percentPartialInterest;
    }

    /**
     * Sets the value of the percentPartialInterest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentPartialInterest(String value) {
        this.percentPartialInterest = value;
    }

    /**
     * Gets the value of the taxYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaxYear() {
        return taxYear;
    }

    /**
     * Sets the value of the taxYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaxYear(BigInteger value) {
        this.taxYear = value;
    }

    /**
     * Gets the value of the seqNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSeqNum() {
        return seqNum;
    }

    /**
     * Sets the value of the seqNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSeqNum(BigInteger value) {
        this.seqNum = value;
    }

    /**
     * Gets the value of the impValuationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getImpValuationDate() {
        return impValuationDate;
    }

    /**
     * Sets the value of the impValuationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setImpValuationDate(XMLGregorianCalendar value) {
        this.impValuationDate = value;
    }

    /**
     * Gets the value of the landValuationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLandValuationDate() {
        return landValuationDate;
    }

    /**
     * Sets the value of the landValuationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLandValuationDate(XMLGregorianCalendar value) {
        this.landValuationDate = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

}
