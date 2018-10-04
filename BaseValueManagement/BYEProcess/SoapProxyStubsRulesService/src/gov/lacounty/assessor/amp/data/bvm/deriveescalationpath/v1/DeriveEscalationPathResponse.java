
package gov.lacounty.assessor.amp.data.bvm.deriveescalationpath.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import gov.lacounty.assessor.amp.type.common.responseheader.v1.ResponseHeader;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="header" type="{http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0}responseHeader"/>
 *         &lt;element name="escalationPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="escalateTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modifiedTotalMinusOriginalTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="derivedPropertyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attribute1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attribute2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorBYEId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorReqId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "escalationPath",
    "escalateTo",
    "modifiedTotalMinusOriginalTotal",
    "derivedPropertyType",
    "attribute1",
    "attribute2",
    "errorBYEId",
    "errorMessage",
    "errorReqId"
})
@XmlRootElement(name = "deriveEscalationPathResponse")
public class DeriveEscalationPathResponse {

    @XmlElement(required = true)
    protected ResponseHeader header;
    @XmlElement(required = true)
    protected String escalationPath;
    @XmlElement(required = true)
    protected String escalateTo;
    @XmlElement(required = true)
    protected String modifiedTotalMinusOriginalTotal;
    protected String derivedPropertyType;
    protected String attribute1;
    protected String attribute2;
    protected String errorBYEId;
    protected String errorMessage;
    protected String errorReqId;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader }
     *     
     */
    public ResponseHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader }
     *     
     */
    public void setHeader(ResponseHeader value) {
        this.header = value;
    }

    /**
     * Gets the value of the escalationPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEscalationPath() {
        return escalationPath;
    }

    /**
     * Sets the value of the escalationPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEscalationPath(String value) {
        this.escalationPath = value;
    }

    /**
     * Gets the value of the escalateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEscalateTo() {
        return escalateTo;
    }

    /**
     * Sets the value of the escalateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEscalateTo(String value) {
        this.escalateTo = value;
    }

    /**
     * Gets the value of the modifiedTotalMinusOriginalTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedTotalMinusOriginalTotal() {
        return modifiedTotalMinusOriginalTotal;
    }

    /**
     * Sets the value of the modifiedTotalMinusOriginalTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedTotalMinusOriginalTotal(String value) {
        this.modifiedTotalMinusOriginalTotal = value;
    }

    /**
     * Gets the value of the derivedPropertyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDerivedPropertyType() {
        return derivedPropertyType;
    }

    /**
     * Sets the value of the derivedPropertyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedPropertyType(String value) {
        this.derivedPropertyType = value;
    }

    /**
     * Gets the value of the attribute1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute1() {
        return attribute1;
    }

    /**
     * Sets the value of the attribute1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute1(String value) {
        this.attribute1 = value;
    }

    /**
     * Gets the value of the attribute2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * Sets the value of the attribute2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute2(String value) {
        this.attribute2 = value;
    }

    /**
     * Gets the value of the errorBYEId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorBYEId() {
        return errorBYEId;
    }

    /**
     * Sets the value of the errorBYEId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorBYEId(String value) {
        this.errorBYEId = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the errorReqId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorReqId() {
        return errorReqId;
    }

    /**
     * Sets the value of the errorReqId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorReqId(String value) {
        this.errorReqId = value;
    }

}
