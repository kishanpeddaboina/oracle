
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AsmtRollList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AsmtRollList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="asmtRoll" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}AsmtRoll" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AsmtRollList", propOrder = {
    "asmtRoll"
})
public class AsmtRollList {

    protected List<AsmtRoll> asmtRoll;

    /**
     * Gets the value of the asmtRoll property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asmtRoll property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsmtRoll().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AsmtRoll }
     * 
     * 
     */
    public List<AsmtRoll> getAsmtRoll() {
        if (asmtRoll == null) {
            asmtRoll = new ArrayList<AsmtRoll>();
        }
        return this.asmtRoll;
    }

}
