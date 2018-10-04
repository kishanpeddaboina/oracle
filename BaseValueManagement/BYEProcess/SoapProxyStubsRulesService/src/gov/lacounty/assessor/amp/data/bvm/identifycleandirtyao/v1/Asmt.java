
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Asmt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Asmt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="operationInput" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sysdate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="byeList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}BaseYearEventList" minOccurs="0"/>
 *         &lt;element name="asmtRollList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}AsmtRollList" minOccurs="0"/>
 *         &lt;element name="conflictList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}ConflictList" minOccurs="0"/>
 *         &lt;element name="landList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}LandList" minOccurs="0"/>
 *         &lt;element name="histList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}HistList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Asmt", propOrder = {
    "id",
    "operationInput",
    "sysdate",
    "byeList",
    "asmtRollList",
    "conflictList",
    "landList",
    "histList"
})
public class Asmt {

    protected BigInteger id;
    protected String operationInput;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sysdate;
    protected BaseYearEventList byeList;
    protected AsmtRollList asmtRollList;
    protected ConflictList conflictList;
    protected LandList landList;
    protected HistList histList;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the operationInput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationInput() {
        return operationInput;
    }

    /**
     * Sets the value of the operationInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationInput(String value) {
        this.operationInput = value;
    }

    /**
     * Gets the value of the sysdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSysdate() {
        return sysdate;
    }

    /**
     * Sets the value of the sysdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSysdate(XMLGregorianCalendar value) {
        this.sysdate = value;
    }

    /**
     * Gets the value of the byeList property.
     * 
     * @return
     *     possible object is
     *     {@link BaseYearEventList }
     *     
     */
    public BaseYearEventList getByeList() {
        return byeList;
    }

    /**
     * Sets the value of the byeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseYearEventList }
     *     
     */
    public void setByeList(BaseYearEventList value) {
        this.byeList = value;
    }

    /**
     * Gets the value of the asmtRollList property.
     * 
     * @return
     *     possible object is
     *     {@link AsmtRollList }
     *     
     */
    public AsmtRollList getAsmtRollList() {
        return asmtRollList;
    }

    /**
     * Sets the value of the asmtRollList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsmtRollList }
     *     
     */
    public void setAsmtRollList(AsmtRollList value) {
        this.asmtRollList = value;
    }

    /**
     * Gets the value of the conflictList property.
     * 
     * @return
     *     possible object is
     *     {@link ConflictList }
     *     
     */
    public ConflictList getConflictList() {
        return conflictList;
    }

    /**
     * Sets the value of the conflictList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConflictList }
     *     
     */
    public void setConflictList(ConflictList value) {
        this.conflictList = value;
    }

    /**
     * Gets the value of the landList property.
     * 
     * @return
     *     possible object is
     *     {@link LandList }
     *     
     */
    public LandList getLandList() {
        return landList;
    }

    /**
     * Sets the value of the landList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LandList }
     *     
     */
    public void setLandList(LandList value) {
        this.landList = value;
    }

    /**
     * Gets the value of the histList property.
     * 
     * @return
     *     possible object is
     *     {@link HistList }
     *     
     */
    public HistList getHistList() {
        return histList;
    }

    /**
     * Sets the value of the histList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HistList }
     *     
     */
    public void setHistList(HistList value) {
        this.histList = value;
    }

}
