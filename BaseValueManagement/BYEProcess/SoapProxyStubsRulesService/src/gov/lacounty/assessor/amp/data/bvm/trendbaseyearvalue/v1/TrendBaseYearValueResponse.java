
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

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
 *         &lt;element name="baseYearEventRespList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}BaseYearEventRespList" minOccurs="0"/>
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
    "baseYearEventRespList"
})
@XmlRootElement(name = "trendBaseYearValueResponse")
public class TrendBaseYearValueResponse {

    @XmlElement(required = true)
    protected ResponseHeader header;
    protected BaseYearEventRespList baseYearEventRespList;

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
     * Gets the value of the baseYearEventRespList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventRespList }
     *     
     */
    public BaseYearEventRespList getBaseYearEventRespList() {
        return baseYearEventRespList;
    }

    /**
     * Sets the value of the baseYearEventRespList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventRespList }
     *     
     */
    public void setBaseYearEventRespList(BaseYearEventRespList value) {
        this.baseYearEventRespList = value;
    }

}
