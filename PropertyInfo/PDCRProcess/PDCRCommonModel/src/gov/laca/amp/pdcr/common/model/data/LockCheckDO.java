package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.io.Serializable;


public class LockCheckDO extends AmpPojo{
    private String ain;
    private String aoid;
    private String lockStatus;
    private String processType;
    private String initiatedBy;

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setAoid(String aoid) {
        this.aoid = aoid;
    }

    public String getAoid() {
        return aoid;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }
}
