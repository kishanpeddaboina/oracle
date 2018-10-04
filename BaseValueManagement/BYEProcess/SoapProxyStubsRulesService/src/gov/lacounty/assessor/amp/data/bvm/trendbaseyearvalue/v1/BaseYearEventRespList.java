
package gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseYearEventRespList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseYearEventRespList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseYearEvent" type="{http://assessor.lacounty.gov/amp/data/bvm/TrendBaseYearValue/v1.0}BaseYearEventResp" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseYearEventRespList", propOrder = {
    "baseYearEvent"
})
public class BaseYearEventRespList {

    protected List<BaseYearEventResp> baseYearEvent;

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
     * {@link BaseYearEventResp }
     * 
     * 
     */
    public List<BaseYearEventResp> getBaseYearEvent() {
        if (baseYearEvent == null) {
            baseYearEvent = new ArrayList<BaseYearEventResp>();
        }
        return this.baseYearEvent;
    }

}
