package gov.laca.amp.tam.model.pojo;

import java.math.BigInteger;

import java.util.Date;

public class TAMAssignmentPojo extends UnverifiedAINPojo {
    private Boolean selected;
    private Boolean isReassignAllowed;
    private String message;
    public TAMAssignmentPojo(Boolean selected, Boolean isReassignAllowed, String message,
                             BigInteger aoid, String ain, String useType,
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
        super( aoid,  ain,  useType, useCodeRef,  useCodeKey, useCodeDescription,  regionRef,  region,  regionName,  districtName,  
 clusterRouteRef,  clusterRoute, clusterRouteName, tamId,   tamProcessType,  tamAssignedStatus,   tamAssignedTo,  tamAssignedToRole,
  tamAssignedBy,  tamAssignedByRole,  tamSource,  tamCreatedBy,  tamCreatedDateTime,  tamModifiedBy, tamModifiedDateTime,  byeCount);
        this.selected          = selected;
        this.isReassignAllowed = isReassignAllowed;
        this.message           = message;
                         }

    public TAMAssignmentPojo(Boolean selected, BigInteger aoid, String ain,  String region,
                        String districtName, String clusterRouteName) {
        super( aoid,  ain,  "", new BigInteger("0"),  "", "",  new BigInteger("0"),  region,  "",  districtName,  
    new BigInteger("0"),  "", clusterRouteName, new BigInteger("123"),   "",  "",   "Sagar",  "Appraiser",
    "Sagar",  "Supervisor",  "TAM",  "",  null,  "", null,  new BigInteger("1"));
        this.selected = selected;
                         }


    public TAMAssignmentPojo() {
        super();
    }

    public TAMAssignmentPojo(Boolean selected) {
        super();
        this.selected = selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setIsReassignAllowed(Boolean isReassignAllowed) {
        this.isReassignAllowed = isReassignAllowed;
    }

    public Boolean getIsReassignAllowed() {
        return isReassignAllowed;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
