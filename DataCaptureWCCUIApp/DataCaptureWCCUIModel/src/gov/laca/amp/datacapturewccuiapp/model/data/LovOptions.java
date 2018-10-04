package gov.laca.amp.datacapturewccuiapp.model.data;

import java.util.HashMap;
import java.util.Map;

public class LovOptions{
    public LovOptions() {
        super();
    }
    
    public LovOptions(String valDesc) {
        super();
       // put(valDesc,valDesc);
        value = valDesc;
        description = valDesc;
    }
    
    public LovOptions(String val, String desc) {
        super();
      //  put(val, desc);
        value = val;
        description = desc;
    }
    
    private String  value;
    private String description;


    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
