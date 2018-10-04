package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.io.Serializable;

public class UserInfoDO extends AmpPojo{
    private String userDisplayName;
    private String userFullName;
    private String userId;
    private String userEmail;
    private String userPhone;
    private String[] userRoles;
    private String userType;

    public void setUserDisplayName(String userName) {
        this.userDisplayName = userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public String[] getUserRoles() {
        return userRoles;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
