
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConflictList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConflictList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conflict" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}Conflict" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConflictList", propOrder = {
    "conflict"
})
public class ConflictList {

    protected List<Conflict> conflict;

    /**
     * Gets the value of the conflict property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conflict property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConflict().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Conflict }
     * 
     * 
     */
    public List<Conflict> getConflict() {
        if (conflict == null) {
            conflict = new ArrayList<Conflict>();
        }
        return this.conflict;
    }

}
