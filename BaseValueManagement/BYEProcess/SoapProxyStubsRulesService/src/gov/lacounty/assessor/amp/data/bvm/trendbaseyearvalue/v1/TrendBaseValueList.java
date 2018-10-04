
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrendBaseValueList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrendBaseValueList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="trendBaseValue" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="byeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="landImprovementsTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="fixtures" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="improvements" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="landValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TrendBaseValueList", propOrder = {
    "trendBaseValue"
})
public class TrendBaseValueList {

    protected List<TrendBaseValueList.TrendBaseValue> trendBaseValue;

    /**
     * Gets the value of the trendBaseValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trendBaseValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrendBaseValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrendBaseValueList.TrendBaseValue }
     * 
     * 
     */
    public List<TrendBaseValueList.TrendBaseValue> getTrendBaseValue() {
        if (trendBaseValue == null) {
            trendBaseValue = new ArrayList<TrendBaseValueList.TrendBaseValue>();
        }
        return this.trendBaseValue;
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
     *         &lt;element name="landImprovementsTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="fixtures" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="improvements" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="landValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="operation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "landImprovementsTotal",
        "fixtures",
        "improvements",
        "landValue",
        "year",
        "operation"
    })
    public static class TrendBaseValue {

        protected BigInteger byeId;
        @XmlElement(required = true)
        protected String landImprovementsTotal;
        protected String fixtures;
        protected String improvements;
        protected String landValue;
        protected String year;
        protected String operation;

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
         * Gets the value of the fixtures property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFixtures() {
            return fixtures;
        }

        /**
         * Sets the value of the fixtures property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFixtures(String value) {
            this.fixtures = value;
        }

        /**
         * Gets the value of the improvements property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getImprovements() {
            return improvements;
        }

        /**
         * Sets the value of the improvements property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setImprovements(String value) {
            this.improvements = value;
        }

        /**
         * Gets the value of the landValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLandValue() {
            return landValue;
        }

        /**
         * Sets the value of the landValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLandValue(String value) {
            this.landValue = value;
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

    }

}
