package gov.laca.amp.modules.am.model.pojo;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddressManagementPojo {
    public AddressManagementPojo() {
        super();
    }
    
    private String ain = null;
    private Boolean isAuthorizedAgent = Boolean.FALSE;
    private String agentName = null;
    private String propertyOwnerName = null;
    private String daytimePhone = null;
    private String email = null;
    private String digitalSignature = null;
    private Date enteredDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
    private List<AinPojo> AinsPojo = new ArrayList<AinPojo>();
    private AddressPojo requestedAddress=null;
    private String newMailingAddress=null;

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }


    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }


    public void setPropertyOwnerName(String propertyOwnerName) {
        this.propertyOwnerName = propertyOwnerName;
    }

    public String getPropertyOwnerName() {
        return propertyOwnerName;
    }

    public void setDaytimePhone(String daytimePhone) {
        this.daytimePhone = daytimePhone;
    }

    public String getDaytimePhone() {
        return daytimePhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setRequestedAddress(AddressPojo requestedAddress) {
        this.requestedAddress = requestedAddress;
    }

    public AddressPojo getRequestedAddress() {
        return requestedAddress;
    }


    public void setAinsPojo(List<AinPojo> AinsPojo) {
        this.AinsPojo = AinsPojo;
    }

    public List<AinPojo> getAinsPojo() {
        return AinsPojo;
    }

    public void setNewMailingAddress(String newMailingAddress) {
        this.newMailingAddress = newMailingAddress;
    }

    public String getNewMailingAddress() {
        return newMailingAddress;
    }

    public void setIsAuthorizedAgent(Boolean isAuthorizedAgent) {
        this.isAuthorizedAgent = isAuthorizedAgent;
    }

    public Boolean getIsAuthorizedAgent() {
        return isAuthorizedAgent;
    }
}
