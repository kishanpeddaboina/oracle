
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BYEResponseList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BYEResponseList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="byeResponse" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}BYEResponse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BYEResponseList", propOrder = {
    "byeResponse"
})
public class BYEResponseList {

    protected List<BYEResponse> byeResponse;

    /**
     * Gets the value of the byeResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the byeResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getByeResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BYEResponse }
     * 
     * 
     */
    public List<BYEResponse> getByeResponse() {
        if (byeResponse == null) {
            byeResponse = new ArrayList<BYEResponse>();
        }
        return this.byeResponse;
    }

}
