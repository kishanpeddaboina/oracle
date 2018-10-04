
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseYearEventResp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseYearEventResp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trendObjectList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}TrendObjectList"/>
 *         &lt;element name="trendBaseValueTotalList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}TrendBaseValueTotalList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseYearEventResp", propOrder = {
    "trendObjectList",
    "trendBaseValueTotalList"
})
public class BaseYearEventResp {

    @XmlElement(required = true)
    protected TrendObjectList trendObjectList;
    @XmlElement(required = true)
    protected TrendBaseValueTotalList trendBaseValueTotalList;

    /**
     * Gets the value of the trendObjectList property.
     * 
     * @return
     *     possible object is
     *     {@link TrendObjectList }
     *     
     */
    public TrendObjectList getTrendObjectList() {
        return trendObjectList;
    }

    /**
     * Sets the value of the trendObjectList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrendObjectList }
     *     
     */
    public void setTrendObjectList(TrendObjectList value) {
        this.trendObjectList = value;
    }

    /**
     * Gets the value of the trendBaseValueTotalList property.
     * 
     * @return
     *     possible object is
     *     {@link TrendBaseValueTotalList }
     *     
     */
    public TrendBaseValueTotalList getTrendBaseValueTotalList() {
        return trendBaseValueTotalList;
    }

    /**
     * Sets the value of the trendBaseValueTotalList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrendBaseValueTotalList }
     *     
     */
    public void setTrendBaseValueTotalList(TrendBaseValueTotalList value) {
        this.trendBaseValueTotalList = value;
    }

}
