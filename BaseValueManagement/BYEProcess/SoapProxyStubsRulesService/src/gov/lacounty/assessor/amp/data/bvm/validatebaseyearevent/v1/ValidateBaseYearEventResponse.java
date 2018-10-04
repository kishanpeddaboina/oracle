
package gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1;

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
 *         &lt;element name="baseYearEventList" type="{http://assessor.lacounty.gov/amp/data/bvm/ValidateBaseYearEvent/v1.0}BaseYearEventList"/>
 *         &lt;element name="baseYearEventErrList" type="{http://assessor.lacounty.gov/amp/data/bvm/ValidateBaseYearEvent/v1.0}BaseYearEventErrList" minOccurs="0"/>
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
    "baseYearEventList",
    "baseYearEventErrList"
})
@XmlRootElement(name = "validateBaseYearEventResponse")
public class ValidateBaseYearEventResponse {

    @XmlElement(required = true)
    protected ResponseHeader header;
    @XmlElement(required = true)
    protected BaseYearEventList baseYearEventList;
    protected BaseYearEventErrList baseYearEventErrList;

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

    /**
     * Gets the value of the baseYearEventErrList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventErrList }
     *     
     */
    public BaseYearEventErrList getBaseYearEventErrList() {
        return baseYearEventErrList;
    }

    /**
     * Sets the value of the baseYearEventErrList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventErrList }
     *     
     */
    public void setBaseYearEventErrList(BaseYearEventErrList value) {
        this.baseYearEventErrList = value;
    }

}
