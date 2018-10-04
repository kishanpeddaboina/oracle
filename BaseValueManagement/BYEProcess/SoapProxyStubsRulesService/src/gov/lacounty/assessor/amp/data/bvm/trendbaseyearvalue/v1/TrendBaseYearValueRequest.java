
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="targetRollYear" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="baseYearEventList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}BaseYearEventList" maxOccurs="unbounded" minOccurs="0"/>
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
    "targetRollYear",
    "baseYearEventList"
})
@XmlRootElement(name = "trendBaseYearValueRequest")
public class TrendBaseYearValueRequest {

    @XmlElement(required = true)
    protected RequestHeader header;
    protected BigInteger targetRollYear;
    protected List<BaseYearEventList> baseYearEventList;

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
     * Gets the value of the targetRollYear property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetRollYear() {
        return targetRollYear;
    }

    /**
     * Sets the value of the targetRollYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetRollYear(BigInteger value) {
        this.targetRollYear = value;
    }

    /**
     * Gets the value of the baseYearEventList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseYearEventList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseYearEventList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaseYearEventList }
     * 
     * 
     */
    public List<BaseYearEventList> getBaseYearEventList() {
        if (baseYearEventList == null) {
            baseYearEventList = new ArrayList<BaseYearEventList>();
        }
        return this.baseYearEventList;
    }

}
