package gov.laca.amp.tam.model.pojo;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.io.Serializable;
import java.sql.Timestamp;

import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService_ep;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockRequest;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockResponse;

import java.math.BigInteger;

import java.text.DateFormat;

import java.text.FieldPosition;
import java.text.NumberFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;


public class UnverifiedAINPojo extends AmpPojo implements Serializable {

    @SuppressWarnings("compatibility:8275180597811125517")
    private static final long serialVersionUID = 1L;
    public static final AmpLogger LOGGER =
        new AmpLogger(UnverifiedAINPojo.class);
    public UnverifiedAINPojo() {
        super();
    }
    public static void main(String[] args){
        java.util.Date d1 = new Date();
       // SimpleDateFormat dtFormatter = 
       ;
        FieldPosition a;
       NumberFormat nf;
        String d1Str = d1.toString();
        System.out.println("d1:"+new SimpleDateFormat("MM/dd/yyyy").format(d1));
    }
            
    protected BigInteger aoid;
    protected String ain;
    protected String useType;
    protected BigInteger useCodeRef;
    protected String useCodeKey;
    protected String useCodeDescription;
    protected BigInteger regionRef;
    protected String region;
    protected String regionName;
    protected String districtName;
    protected BigInteger clusterRouteRef;
    protected String clusterRoute;
    protected String clusterRouteName;
    protected BigInteger tamId;
    protected String tamProcessType;
    protected String tamAssignedStatus;
    protected String tamAssignedTo;
    protected String tamAssignedToRole;
    protected String tamAssignedBy;
    protected String tamAssignedByRole;
    protected String tamSource;
    protected String tamCreatedBy;
  
    protected Date tamCreatedDateTime;
    protected String tamCreatedDateTimeStr;
    protected String tamModifiedBy;
  
    protected Date tamModifiedDateTime;
    protected BigInteger byeCount;

    public UnverifiedAINPojo(BigInteger aoid, String ain, String useType,
                         BigInteger useCodeRef, String useCodeKey,
                         String useCodeDescription, BigInteger regionRef,
                         String region, String regionName, String districtName,
                         BigInteger clusterRouteRef, String clusterRoute,
                         String clusterRouteName, BigInteger tamId,
                         String tamProcessType, String tamAssignedStatus,
                         String tamAssignedTo, String tamAssignedToRole,
                         String tamAssignedBy, String tamAssignedByRole,
                         String tamSource, String tamCreatedBy,
                         Date tamCreatedDateTime, String tamModifiedBy,
                         Date tamModifiedDateTime, BigInteger byeCount) {
        super();
        this.aoid = aoid;
        this.ain = ain;
        this.useType = useType;
        this.useCodeRef = useCodeRef;
        this.useCodeKey = useCodeKey;
        this.useCodeDescription = useCodeDescription;
        this.regionRef = regionRef;
        this.region = region;
        this.regionName = regionName;
        this.districtName = districtName;
        this.clusterRouteRef = clusterRouteRef;
        this.clusterRoute = clusterRoute;
        this.clusterRouteName = clusterRouteName;
        this.tamId = tamId;
        this.tamProcessType = tamProcessType;
        this.tamAssignedStatus = tamAssignedStatus;
        this.tamAssignedTo = tamAssignedTo;
        this.tamAssignedToRole = tamAssignedToRole;
        this.tamAssignedBy = tamAssignedBy;
        this.tamAssignedByRole = tamAssignedByRole;
        this.tamSource = tamSource;
        this.tamCreatedBy = tamCreatedBy;
        this.tamCreatedDateTime = tamCreatedDateTime;
        this.tamCreatedDateTimeStr = new SimpleDateFormat("MM/dd/yyyy").format(tamCreatedDateTime);
        this.tamModifiedBy = tamModifiedBy;
        this.tamModifiedDateTime = tamModifiedDateTime;
        this.byeCount = byeCount;
    }


    public void setAoid(BigInteger aoid) {
        this.aoid = aoid;
    }

    public BigInteger getAoid() {
        return aoid;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseCodeRef(BigInteger useCodeRef) {
        this.useCodeRef = useCodeRef;
    }

    public BigInteger getUseCodeRef() {
        return useCodeRef;
    }

    public void setUseCodeKey(String useCodeKey) {
        this.useCodeKey = useCodeKey;
    }

    public String getUseCodeKey() {
        return useCodeKey;
    }

    public void setUseCodeDescription(String useCodeDescription) {
        this.useCodeDescription = useCodeDescription;
    }

    public String getUseCodeDescription() {
        return useCodeDescription;
    }

    public void setRegionRef(BigInteger regionRef) {
        this.regionRef = regionRef;
    }

    public BigInteger getRegionRef() {
        return regionRef;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setClusterRouteRef(BigInteger clusterRouteRef) {
        this.clusterRouteRef = clusterRouteRef;
    }

    public BigInteger getClusterRouteRef() {
        return clusterRouteRef;
    }

    public void setClusterRoute(String clusterRoute) {
        this.clusterRoute = clusterRoute;
    }

    public String getClusterRoute() {
        return clusterRoute;
    }

    public void setClusterRouteName(String clusterRouteName) {
        this.clusterRouteName = clusterRouteName;
    }

    public String getClusterRouteName() {
        return clusterRouteName;
    }

    public void setTamId(BigInteger tamId) {
        this.tamId = tamId;
    }

    public BigInteger getTamId() {
        return tamId;
    }

    public void setTamProcessType(String tamProcessType) {
        this.tamProcessType = tamProcessType;
    }

    public String getTamProcessType() {
        return tamProcessType;
    }

    public void setTamAssignedStatus(String tamAssignedStatus) {
        this.tamAssignedStatus = tamAssignedStatus;
    }

    public String getTamAssignedStatus() {
        return tamAssignedStatus;
    }

    public void setTamAssignedTo(String tamAssignedTo) {
        this.tamAssignedTo = tamAssignedTo;
    }

    public String getTamAssignedTo() {
        return tamAssignedTo;
    }

    public void setTamAssignedToRole(String tamAssignedToRole) {
        this.tamAssignedToRole = tamAssignedToRole;
    }

    public String getTamAssignedToRole() {
        return tamAssignedToRole;
    }

    public void setTamAssignedBy(String tamAssignedBy) {
        this.tamAssignedBy = tamAssignedBy;
    }

    public String getTamAssignedBy() {
        return tamAssignedBy;
    }

    public void setTamAssignedByRole(String tamAssignedByRole) {
        this.tamAssignedByRole = tamAssignedByRole;
    }

    public String getTamAssignedByRole() {
        return tamAssignedByRole;
    }

    public void setTamSource(String tamSource) {
        this.tamSource = tamSource;
    }

    public String getTamSource() {
        return tamSource;
    }

    public void setTamCreatedBy(String tamCreatedBy) {
        this.tamCreatedBy = tamCreatedBy;
    }

    public String getTamCreatedBy() {
        return tamCreatedBy;
    }

    public void setTamCreatedDateTime(Date tamCreatedDateTime) {
        this.tamCreatedDateTime = tamCreatedDateTime;
    }

    public Date getTamCreatedDateTime() {
        return tamCreatedDateTime;
    }

    public void setTamModifiedBy(String tamModifiedBy) {
        this.tamModifiedBy = tamModifiedBy;
    }

    public String getTamModifiedBy() {
        return tamModifiedBy;
    }

    public void setTamModifiedDateTime(Date tamModifiedDateTime) {
        this.tamModifiedDateTime = tamModifiedDateTime;
    }

    public Date getTamModifiedDateTime() {
        return tamModifiedDateTime;
    }

    public void setByeCount(BigInteger byeCount) {
        this.byeCount = byeCount;
    }

    public BigInteger getByeCount() {
        return byeCount;
    }

    public void setTamCreatedDateTimeStr(String tamCreatedDateTimeStr) {
        this.tamCreatedDateTimeStr = tamCreatedDateTimeStr;
    }

    public String getTamCreatedDateTimeStr() {
        return tamCreatedDateTimeStr;
    }
}
