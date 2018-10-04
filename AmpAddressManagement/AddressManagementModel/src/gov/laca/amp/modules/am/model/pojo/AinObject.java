package gov.laca.amp.modules.am.model.pojo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class AinObject {
private String mapbook;
private String page;
private String parcel;
private String ain;

    @XmlElement(name="MAPBOOK")
    public void setMapbook(String mapbook) {
        this.mapbook = mapbook;
    }

    public String getMapbook() {
        return mapbook;
    }

    @XmlElement(name="PAGE")
    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
    
    @XmlElement(name="PARCEL")
    public void setParcel(String parcel) {
        this.parcel = parcel;
    }

    public String getParcel() {
        return parcel;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    @XmlElement(name="AIN")
    public String getAin() {
        return ain;
    }
}
