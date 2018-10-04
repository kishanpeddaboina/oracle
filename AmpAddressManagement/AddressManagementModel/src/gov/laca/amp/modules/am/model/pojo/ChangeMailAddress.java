package gov.laca.amp.modules.am.model.pojo;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="CHANGEMAILADDRESS")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name","date","phone","email","ain","propertyAddressGroup","newAddress"})
public class ChangeMailAddress {
    @XmlTransient private String name;
    @XmlTransient private Date date;
    @XmlTransient private String phone;
    @XmlTransient private String email;
    @XmlTransient private Ain ain;
    @XmlTransient private NewMailingAddress newAddress;
    @XmlTransient private PropertyAddress propertyAddressGroup;
    
    @XmlElement(name="NAME")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name="DATE")
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @XmlElement(name="PHONE")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    
    @XmlElement(name="EMAIL")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement(name="AIN_G")
    public void setAin(Ain ain) {
        this.ain = ain;
    }

    public Ain getAin() {
        return ain;
    }

    @XmlElement(name="NEWMAILINGADDRESS")
    public void setNewAddress(NewMailingAddress newAddress) {
        this.newAddress = newAddress;
    }

    public NewMailingAddress getNewAddress() {
        return newAddress;
    }

    @XmlElement(name="PROPERTY_ADDRESS_G")
    public void setPropertyAddressGroup(PropertyAddress PropertyAddressGroup) {
        this.propertyAddressGroup = PropertyAddressGroup;
    }

    public PropertyAddress getPropertyAddressGroup() {
        return propertyAddressGroup;
    }
}
