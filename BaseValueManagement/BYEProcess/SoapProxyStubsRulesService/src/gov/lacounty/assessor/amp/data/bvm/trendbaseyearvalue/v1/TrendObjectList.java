
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrendObjectList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrendObjectList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trendObject" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="byeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="trendBaseValueList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}TrendBaseValueList" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrendObjectList", propOrder = {
    "trendObject"
})
public class TrendObjectList {

    protected List<TrendObjectList.TrendObject> trendObject;

    /**
     * Gets the value of the trendObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trendObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrendObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrendObjectList.TrendObject }
     * 
     * 
     */
    public List<TrendObjectList.TrendObject> getTrendObject() {
        if (trendObject == null) {
            trendObject = new ArrayList<TrendObjectList.TrendObject>();
        }
        return this.trendObject;
    }


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
     *         &lt;element name="byeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="trendBaseValueList" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}TrendBaseValueList" minOccurs="0"/>
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
        "byeId",
        "trendBaseValueList"
    })
    public static class TrendObject {

        protected BigInteger byeId;
        protected TrendBaseValueList trendBaseValueList;

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
         * Gets the value of the trendBaseValueList property.
         * 
         * @return
         *     possible object is
         *     {@link TrendBaseValueList }
         *     
         */
        public TrendBaseValueList getTrendBaseValueList() {
            return trendBaseValueList;
        }

        /**
         * Sets the value of the trendBaseValueList property.
         * 
         * @param value
         *     allowed object is
         *     {@link TrendBaseValueList }
         *     
         */
        public void setTrendBaseValueList(TrendBaseValueList value) {
            this.trendBaseValueList = value;
        }

    }

}
