package gov.laca.amp.bvm.model.data;

import java.io.Serializable;

import java.math.BigInteger;

public class Header implements Comparable <Header>,Serializable{
    @SuppressWarnings("compatibility:-2834472977725941231")
    private static final long serialVersionUID = 1L;
    private String hyear;
    private String trendFactor;
    private boolean hbaseYear;
    
    public Header() {
        super();
    }

    public Header(String hyear, String trendFactor, boolean hbaseYear) {
        this.hyear = hyear;
        this.trendFactor = trendFactor;
        this.hbaseYear = hbaseYear;
    }

    public Header(String hyear, String trendFactor) {
        this.hyear = hyear;
        this.trendFactor = trendFactor;
    }

  
    public void setHbaseYear(boolean hbaseYear) {
        this.hbaseYear = hbaseYear;
    }

    public boolean isHbaseYear() {
        return hbaseYear;
    }

    public void setHyear(String hyear) {
        this.hyear = hyear;
    }

    public String getHyear() {
        return hyear;
    }

    public void setTrendFactor(String trendFactor) {
        this.trendFactor = trendFactor;
    }

    public String getTrendFactor() {
        return trendFactor;
    }

    public int compareTo(Header header1) {               
        if (this.getHyear() == null && header1.getHyear() == null)
            return 0;
        if (this.getHyear() == null)
            return 1;
        else if (header1.getHyear() == null)
            return -1;
        return this.getHyear().compareTo(header1.getHyear());
    }
}