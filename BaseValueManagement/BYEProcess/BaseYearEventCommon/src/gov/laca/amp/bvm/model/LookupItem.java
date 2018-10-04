package gov.laca.amp.bvm.model;

import java.math.BigInteger;

public class LookupItem {
    public LookupItem() {
        super();
    }
    
    public LookupItem(BigInteger id, String key, String legend) {
        super();
        this.id = id;
        this.key = key;
        this.legend = legend;
    }
    
    private BigInteger id;
    private String key;
    private String legend;

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setLegend(String value) {
        this.legend = value;
    }

    public String getLegend() {
        return legend;
    }
}
