package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

public class RequestorInfoDO extends AmpPojo{
    private String requestorName;
    private String requestorType;
    private String phone;
    private String email;
    private String reasonForRequest;
    private String comments;
    private String dataChangeSource;
    private String preferredContact;

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorType(String requestorType) {
        this.requestorType = requestorType;
    }

    public String getRequestorType() {
        return requestorType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setReasonForRequest(String reasonForRequest) {
        this.reasonForRequest = reasonForRequest;
    }

    public String getReasonForRequest() {
        return reasonForRequest;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setDataChangeSource(String dataChangeSource) {
        this.dataChangeSource = dataChangeSource;
    }

    public String getDataChangeSource() {
        return dataChangeSource;
    }

    public void setPreferredContact(String preferredContact) {
        this.preferredContact = preferredContact;
    }

    public String getPreferredContact() {
        return preferredContact;
    }
}
