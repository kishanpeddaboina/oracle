package gov.laca.amp.modules.am.model.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class PropertyAddress {
    private ArrayList<NewMailingAddress> PropertyMailingAddress;

    @XmlElement(name="PROPERTY_ADDRESS") 
    public void setPropertyMailingAddress(ArrayList<NewMailingAddress> PropertyMailingAddress) {
        this.PropertyMailingAddress = PropertyMailingAddress;
    }

    public ArrayList<NewMailingAddress> getPropertyMailingAddress() {
        return PropertyMailingAddress;
    }
}
