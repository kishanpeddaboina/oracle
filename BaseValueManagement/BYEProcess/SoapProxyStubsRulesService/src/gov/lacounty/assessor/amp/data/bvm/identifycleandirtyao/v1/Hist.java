
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Hist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Hist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="histAIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hist", propOrder = {
    "histAIN"
})
public class Hist {

    protected String histAIN;

    /**
     * Gets the value of the histAIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistAIN() {
        return histAIN;
    }

    /**
     * Sets the value of the histAIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistAIN(String value) {
        this.histAIN = value;
    }

}
