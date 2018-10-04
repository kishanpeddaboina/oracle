package gov.laca.amp.bvm.model.data;


import java.math.BigInteger;


public class Year implements Comparable<Year> {
    private String tyear;
    private String land;
    private String imp;
    private String fixture;
    private String total;
    private boolean baseYear;

    public Year() {
        super();
    }

    public Year(String tyear, String land, String imp, String fixture, String total) {
        this.tyear = tyear;
        this.land = land;
        this.imp = imp;
        this.fixture = fixture;
        this.total = total;
    }

    public Year(String tyear, String land, String imp, String fixture,
                String total, boolean baseYear) {
        this.tyear = tyear;
        this.land = land;
        this.imp = imp;
        this.fixture = fixture;
        this.total = total;
        this.baseYear = baseYear;
    }

    public int compareTo(Year year1) {
        //ascending order
        if (this.getTyear() == null && year1.getTyear() == null)
            return 0;
        if (this.getTyear() == null)
            return 1;
        else if (year1.getTyear() == null)
            return -1;
        return this.getTyear().compareTo(year1.getTyear());

    }

    public void setTyear(String tyear) {
        this.tyear = tyear;
    }

    public String getTyear() {
        return tyear;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getLand() {
        return land;
    }

    public void setImp(String imp) {
        this.imp = imp;
    }

    public String getImp() {
        return imp;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getFixture() {
        return fixture;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setBaseYear(boolean baseYear) {
        this.baseYear = baseYear;
    }

    public boolean isBaseYear() {
        return baseYear;
    }
}