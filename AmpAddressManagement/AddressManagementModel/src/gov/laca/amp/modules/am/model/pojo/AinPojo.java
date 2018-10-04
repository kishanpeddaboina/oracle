package gov.laca.amp.modules.am.model.pojo;

import java.math.BigInteger;

import java.util.Date;

public class AinPojo {
    public AinPojo() {
        super();
    }
    private String ain = null;
    private AddressPojo currentSitusAddress= new AddressPojo();
    private AddressPojo currentMailingAddress= new AddressPojo();
    private String PropertyAddress=null;
    private java.sql.Date recordingDate;
    private BigInteger aoid=null;
    private String district=null;
    private String region=null;
    private String cluster=null;
    private String assesseeName=null;
    private String documentSeq=null;
    private String documentID=null;
    private String parcelStatus=null;
    private String communityName=null;
    private String situsAddrInvestigationNotes = null;
    private String remarks = null;
    private BigInteger ohid;
    private String ainDisplay;
    private String inCareOf;
    private String primary = null;
    

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setPropertyAddress(String PropertyAddress) {
        this.PropertyAddress = PropertyAddress;
    }

    public String getPropertyAddress() {
        return PropertyAddress;
    }

    public void setRecordingDate(java.sql.Date recordingDate) {
        this.recordingDate = recordingDate;
    }

    public java.sql.Date getRecordingDate() {
        return recordingDate;
    }

    public void setAoid(BigInteger aoid) {
        this.aoid = aoid;
    }

    public BigInteger getAoid() {
        return aoid;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getCluster() {
        return cluster;
    }

    public void setAssesseeName(String assesseeName) {
        this.assesseeName = assesseeName;
    }

    public String getAssesseeName() {
        return assesseeName;
    }

    public void setDocumentSeq(String documentSeq) {
        this.documentSeq = documentSeq;
    }

    public String getDocumentSeq() {
        return documentSeq;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    public String getParcelStatus() {
        return parcelStatus;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setSitusAddrInvestigationNotes(String situsAddrInvestigationNotes) {
        this.situsAddrInvestigationNotes = situsAddrInvestigationNotes;
    }

    public String getSitusAddrInvestigationNotes() {
        return situsAddrInvestigationNotes;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setCurrentSitusAddress(AddressPojo currentSitusAddress) {
        this.currentSitusAddress = currentSitusAddress;
    }

    public AddressPojo getCurrentSitusAddress() {
        return currentSitusAddress;
    }

    public void setCurrentMailingAddress(AddressPojo currentMailingAddress) {
        this.currentMailingAddress = currentMailingAddress;
    }

    public AddressPojo getCurrentMailingAddress() {
        return currentMailingAddress;
    }


    public void setOhid(BigInteger ohid) {
        this.ohid = ohid;
    }

    public BigInteger getOhid() {
        return ohid;
    }


    public void setAinDisplay(String ainDisplay) {
        this.ainDisplay = ainDisplay;
    }

    public String getAinDisplay() {
        return ainDisplay;
    }


    public void setInCareOf(String inCareOf) {
        this.inCareOf = inCareOf;
    }

    public String getInCareOf() {
        return inCareOf;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getPrimary() {
        return primary;
    }
}
