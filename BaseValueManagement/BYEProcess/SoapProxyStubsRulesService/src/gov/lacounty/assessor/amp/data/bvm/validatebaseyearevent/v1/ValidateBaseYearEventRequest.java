
package gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import gov.lacounty.assessor.amp.type.common.requestheader.v1.RequestHeader;


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
 *         &lt;element name="header" type="{http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0}requestHeader"/>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="baseYearEventList" type="{http://assessor.lacounty.gov/amp/data/bvm/ValidateBaseYearEvent/v1.0}BaseYearEventList" minOccurs="0"/>
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
    "validationType",
    "baseYearEventList"
})
@XmlRootElement(name = "validateBaseYearEventRequest")
public class ValidateBaseYearEventRequest {

    @XmlElement(required = true)
    protected RequestHeader header;
    @XmlElement(required = true)
    protected String validationType;
    protected BaseYearEventList baseYearEventList;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader }
     *     
     */
    public RequestHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader }
     *     
     */
    public void setHeader(RequestHeader value) {
        this.header = value;
    }

    /**
     * Gets the value of the validationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets the value of the validationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationType(String value) {
        this.validationType = value;
    }

    /**
     * Gets the value of the baseYearEventList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventList }
     *     
     */
    public BaseYearEventList getBaseYearEventList() {
        return baseYearEventList;
    }

    /**
     * Sets the value of the baseYearEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventList }
     *     
     */
    public void setBaseYearEventList(BaseYearEventList value) {
        this.baseYearEventList = value;
    }

}
