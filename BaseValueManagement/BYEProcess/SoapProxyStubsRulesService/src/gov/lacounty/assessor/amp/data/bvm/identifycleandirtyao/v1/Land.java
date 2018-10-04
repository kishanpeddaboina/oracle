
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Land complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Land">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="impairmentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="landId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Land", propOrder = {
    "impairmentKey",
    "landId"
})
public class Land {

    protected String impairmentKey;
    protected String landId;

    /**
     * Gets the value of the impairmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpairmentKey() {
        return impairmentKey;
    }

    /**
     * Sets the value of the impairmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpairmentKey(String value) {
        this.impairmentKey = value;
    }

    /**
     * Gets the value of the landId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandId() {
        return landId;
    }

    /**
     * Sets the value of the landId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandId(String value) {
        this.landId = value;
    }

}
