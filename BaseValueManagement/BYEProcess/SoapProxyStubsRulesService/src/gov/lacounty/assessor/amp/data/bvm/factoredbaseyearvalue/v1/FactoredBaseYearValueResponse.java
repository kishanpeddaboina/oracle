
package gov.lacounty.assessor.amp.data.bvm.factoredbaseyearvalue.v1;

import java.math.BigInteger;
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
 *         &lt;element name="prop13ImprovementValue" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="prop13LandImprovementSum" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="prop13LandValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
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
    "prop13ImprovementValue",
    "prop13LandImprovementSum",
    "prop13LandValue",
    "errorBYEId",
    "errorMessage",
    "errorReqId"
})
@XmlRootElement(name = "factoredBaseYearValueResponse")
public class FactoredBaseYearValueResponse {

    @XmlElement(required = true)
    protected ResponseHeader header;
    @XmlElement(required = true)
    protected BigInteger prop13ImprovementValue;
    @XmlElement(required = true)
    protected BigInteger prop13LandImprovementSum;
    protected Double prop13LandValue;
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
     * Gets the value of the prop13ImprovementValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProp13ImprovementValue() {
        return prop13ImprovementValue;
    }

    /**
     * Sets the value of the prop13ImprovementValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProp13ImprovementValue(BigInteger value) {
        this.prop13ImprovementValue = value;
    }

    /**
     * Gets the value of the prop13LandImprovementSum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProp13LandImprovementSum() {
        return prop13LandImprovementSum;
    }

    /**
     * Sets the value of the prop13LandImprovementSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProp13LandImprovementSum(BigInteger value) {
        this.prop13LandImprovementSum = value;
    }

    /**
     * Gets the value of the prop13LandValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProp13LandValue() {
        return prop13LandValue;
    }

    /**
     * Sets the value of the prop13LandValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProp13LandValue(Double value) {
        this.prop13LandValue = value;
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
