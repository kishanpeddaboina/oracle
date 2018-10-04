
package gov.lacounty.assessor.amp.data.bvm.deriveescalationpath.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import gov.lacounty.assessor.amp.type.bvm.baseyearevent.v1.BaseYearEvent;


/**
 * <p>Java class for BaseYearEventList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseYearEventList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseYearEvent" type="{http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0}baseYearEvent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseYearEventList", propOrder = {
    "baseYearEvent"
})
public class BaseYearEventList {

    protected List<BaseYearEvent> baseYearEvent;

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
     * {@link BaseYearEvent }
     * 
     * 
     */
    public List<BaseYearEvent> getBaseYearEvent() {
        if (baseYearEvent == null) {
            baseYearEvent = new ArrayList<BaseYearEvent>();
        }
        return this.baseYearEvent;
    }

}
