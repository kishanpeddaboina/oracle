
package gov.lacounty.assessor.amp.data.bvm.identifycleandirtyao.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import gov.lacounty.assessor.amp.type.common.responseheader.v1.ResponseHeader;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="header" type="{http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0}responseHeader"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="byeId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="reviewedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cleanDirtyLastRunDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="byeResponseList" type="{http://assessor.lacounty.gov/amp/data/bvm/IdentifyCleanDirtyAO/v1.0}BYEResponseList" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "id",
    "byeId",
    "reviewedFlag",
    "comments",
    "cleanDirtyLastRunDate",
    "byeResponseList"
})
@XmlRootElement(name = "identifyCleanDirtyAOResponse")
public class IdentifyCleanDirtyAOResponse {

    @XmlElement(required = true)
    protected ResponseHeader header;
    @XmlElement(required = true)
    protected BigInteger id;
    protected BigInteger byeId;
    protected String reviewedFlag;
    protected String comments;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar cleanDirtyLastRunDate;
    protected List<BYEResponseList> byeResponseList;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeader }
     *     
     */
    public ResponseHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeader }
     *     
     */
    public void setHeader(ResponseHeader value) {
        this.header = value;
    }

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
     * Gets the value of the byeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getByeId() {
        return byeId;
    }

    /**
     * Sets the value of the byeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setByeId(BigInteger value) {
        this.byeId = value;
    }

    /**
     * Gets the value of the reviewedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewedFlag() {
        return reviewedFlag;
    }

    /**
     * Sets the value of the reviewedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewedFlag(String value) {
        this.reviewedFlag = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the cleanDirtyLastRunDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCleanDirtyLastRunDate() {
        return cleanDirtyLastRunDate;
    }

    /**
     * Sets the value of the cleanDirtyLastRunDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCleanDirtyLastRunDate(XMLGregorianCalendar value) {
        this.cleanDirtyLastRunDate = value;
    }

    /**
     * Gets the value of the byeResponseList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the byeResponseList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getByeResponseList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BYEResponseList }
     * 
     * 
     */
    public List<BYEResponseList> getByeResponseList() {
        if (byeResponseList == null) {
            byeResponseList = new ArrayList<BYEResponseList>();
        }
        return this.byeResponseList;
    }

}
