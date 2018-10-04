
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrendBaseValueTotalList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrendBaseValueTotalList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trendBaseValueTotal" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="landImprovementsTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="totalFixturesValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="totalImprovementsValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="totalLandValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trendFactor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TrendBaseValueTotalList", propOrder = {
    "trendBaseValueTotal"
})
public class TrendBaseValueTotalList {

    protected List<TrendBaseValueTotalList.TrendBaseValueTotal> trendBaseValueTotal;

    /**
     * Gets the value of the trendBaseValueTotal property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trendBaseValueTotal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrendBaseValueTotal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrendBaseValueTotalList.TrendBaseValueTotal }
     * 
     * 
     */
    public List<TrendBaseValueTotalList.TrendBaseValueTotal> getTrendBaseValueTotal() {
        if (trendBaseValueTotal == null) {
            trendBaseValueTotal = new ArrayList<TrendBaseValueTotalList.TrendBaseValueTotal>();
        }
        return this.trendBaseValueTotal;
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
     *         &lt;element name="landImprovementsTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="totalFixturesValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="totalImprovementsValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="totalLandValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trendFactor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "landImprovementsTotal",
        "operation",
        "totalFixturesValue",
        "totalImprovementsValue",
        "totalLandValue",
        "year",
        "trendFactor"
    })
    public static class TrendBaseValueTotal {

        @XmlElement(required = true)
        protected String landImprovementsTotal;
        protected String operation;
        protected String totalFixturesValue;
        protected String totalImprovementsValue;
        protected String totalLandValue;
        protected String year;
        protected String trendFactor;

        /**
         * Gets the value of the landImprovementsTotal property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLandImprovementsTotal() {
            return landImprovementsTotal;
        }

        /**
         * Sets the value of the landImprovementsTotal property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLandImprovementsTotal(String value) {
            this.landImprovementsTotal = value;
        }

        /**
         * Gets the value of the operation property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperation() {
            return operation;
        }

        /**
         * Sets the value of the operation property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperation(String value) {
            this.operation = value;
        }

        /**
         * Gets the value of the totalFixturesValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalFixturesValue() {
            return totalFixturesValue;
        }

        /**
         * Sets the value of the totalFixturesValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalFixturesValue(String value) {
            this.totalFixturesValue = value;
        }

        /**
         * Gets the value of the totalImprovementsValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalImprovementsValue() {
            return totalImprovementsValue;
        }

        /**
         * Sets the value of the totalImprovementsValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalImprovementsValue(String value) {
            this.totalImprovementsValue = value;
        }

        /**
         * Gets the value of the totalLandValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalLandValue() {
            return totalLandValue;
        }

        /**
         * Sets the value of the totalLandValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalLandValue(String value) {
            this.totalLandValue = value;
        }

        /**
         * Gets the value of the year property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYear(String value) {
            this.year = value;
        }

        /**
         * Gets the value of the trendFactor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrendFactor() {
            return trendFactor;
        }

        /**
         * Sets the value of the trendFactor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrendFactor(String value) {
            this.trendFactor = value;
        }

    }

}
