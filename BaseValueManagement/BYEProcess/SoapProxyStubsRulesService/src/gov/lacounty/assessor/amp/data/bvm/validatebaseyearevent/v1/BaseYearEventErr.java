
package gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseYearEventErr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseYearEventErr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorBYEId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorReqId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseYearEventErr", propOrder = {
    "errorBYEId",
    "errorMessage",
    "errorReqId"
})
public class BaseYearEventErr {

    protected String errorBYEId;
    protected String errorMessage;
    protected String errorReqId;

    /**
     * Gets the value of the errorBYEId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorBYEId() {
        return errorBYEId;
    }

    /**
     * Sets the value of the errorBYEId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorBYEId(String value) {
        this.errorBYEId = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the errorReqId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorReqId() {
        return errorReqId;
    }

    /**
     * Sets the value of the errorReqId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorReqId(String value) {
        this.errorReqId = value;
    }

}
