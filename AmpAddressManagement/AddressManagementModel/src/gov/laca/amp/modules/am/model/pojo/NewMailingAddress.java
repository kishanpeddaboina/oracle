package gov.laca.amp.modules.am.model.pojo;
import javax.xml.bind.annotation.XmlElement;

public class NewMailingAddress {
    private String StreetNumber;
    private String StreetAddress;
    private String Street;
    private String unit;
    private String city;
    private String state;
    private String zipCode;

    @XmlElement(name="NUMBER")
    public void setStreetNumber(String StreetNumber) {
        this.StreetNumber = StreetNumber;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    @XmlElement(name="STREET")
    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getStreet() {
        return Street;
    }

    @XmlElement(name="UNIT")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    @XmlElement(name="CITY")
    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @XmlElement(name="STATE")
    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    
    @XmlElement(name="ZIP")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }
 
    @XmlElement(name="STREETADDRESS")
    public void setStreetAddress(String StreetAddress) {
        this.StreetAddress = StreetAddress;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }
}
