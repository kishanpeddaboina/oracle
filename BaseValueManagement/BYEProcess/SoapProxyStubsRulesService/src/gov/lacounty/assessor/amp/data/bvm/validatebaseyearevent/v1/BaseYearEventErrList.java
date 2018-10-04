
package gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseYearEventErrList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseYearEventErrList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseYearEvent" type="{http://assessor.lacounty.gov/amp/data/bvm/ValidateBaseYearEvent/v1.0}baseYearEventErr" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseYearEventErrList", propOrder = {
    "baseYearEvent"
})
public class BaseYearEventErrList {

    protected List<BaseYearEventErr> baseYearEvent;

    /**
     * Gets the value of the baseYearEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseYearEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseYearEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BaseYearEventErr }
     * 
     * 
     */
    public List<BaseYearEventErr> getBaseYearEvent() {
        if (baseYearEvent == null) {
            baseYearEvent = new ArrayList<BaseYearEventErr>();
        }
        return this.baseYearEvent;
    }

}
