package gov.laca.amp.modules.am.model.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Ain {
    private ArrayList<AinObject> ain_g;
    
    @XmlElement(name="AIN") 
    public void setAin_g(ArrayList<AinObject> ain_g) {
        this.ain_g = ain_g;
    }

    public ArrayList<AinObject> getAin_g() {
        return ain_g;
    }
}
