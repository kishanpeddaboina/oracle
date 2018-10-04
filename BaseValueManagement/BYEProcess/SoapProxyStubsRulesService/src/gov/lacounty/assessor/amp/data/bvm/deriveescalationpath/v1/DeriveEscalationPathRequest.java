
package gov.lacounty.assessor.amp.data.bvm.deriveescalationpath.v1;

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
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="approvedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modifiedBaseYearEventList" type="{http://assessor.lacounty.gov/amp/data/bvm/DeriveEscalationPath/v1.0}BaseYearEventList" minOccurs="0"/>
 *         &lt;element name="originalBaseYearEventList" type="{http://assessor.lacounty.gov/amp/data/bvm/DeriveEscalationPath/v1.0}BaseYearEventList" minOccurs="0"/>
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
    "region",
    "approvedBy",
    "modifiedBaseYearEventList",
    "originalBaseYearEventList"
})
@XmlRootElement(name = "deriveEscalationPathRequest")
public class DeriveEscalationPathRequest {

    @XmlElement(required = true)
    protected RequestHeader header;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String approvedBy;
    protected BaseYearEventList modifiedBaseYearEventList;
    protected BaseYearEventList originalBaseYearEventList;

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
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the approvedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * Sets the value of the approvedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprovedBy(String value) {
        this.approvedBy = value;
    }

    /**
     * Gets the value of the modifiedBaseYearEventList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventList }
     *     
     */
    public BaseYearEventList getModifiedBaseYearEventList() {
        return modifiedBaseYearEventList;
    }

    /**
     * Sets the value of the modifiedBaseYearEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventList }
     *     
     */
    public void setModifiedBaseYearEventList(BaseYearEventList value) {
        this.modifiedBaseYearEventList = value;
    }

    /**
     * Gets the value of the originalBaseYearEventList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventList }
     *     
     */
    public BaseYearEventList getOriginalBaseYearEventList() {
        return originalBaseYearEventList;
    }

    /**
     * Sets the value of the originalBaseYearEventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventList }
     *     
     */
    public void setOriginalBaseYearEventList(BaseYearEventList value) {
        this.originalBaseYearEventList = value;
    }

}
