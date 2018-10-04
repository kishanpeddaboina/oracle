package gov.laca.amp.common.assign.view;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.extn.exception.AmpBusinessException;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.AmpRolesConstants;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.portal.extension.AmpBusinessUserProfile;
import gov.laca.amp.proxy.soap.identitylookupservice.IdentityUserWrapper;
import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.Fault;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.IdentityGroupType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;


/**
 * @author Vijay Redla
 * @version
 */
public class AssignmentLookupBean extends AmpManagedBean {
    @SuppressWarnings("compatibility:1298281457282267580")
    private static final long serialVersionUID = 1L;
    public static final AmpLogger LOGGER =
        new AmpLogger(AssignmentLookupBean.class);
    private static final String LOGGER_PREFIX = "DEBUG";
    private AmpBusinessUserProfile businessUserProfile;
    private ArrayList<SelectItem> users = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> roles = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> groups = null;
    private ArrayList<SelectItem> allDistricts = new ArrayList<SelectItem>();
    private String currentDistrict = null;
    private String currentRole = null;
    private String currentType = null;
    private ArrayList<SelectItem> type = null;
    private List<SelectItem> userDistricts = new ArrayList<SelectItem>();
    private String displayName;

    public AssignmentLookupBean() {
        super();
    }

    public AmpBusinessUserProfile getBusinessUserProfile() {
        if (businessUserProfile == null) {
            businessUserProfile =
                    (AmpBusinessUserProfile)JSFUtils.getManagedBeanValue("ampBusinessUserProfile");
        }
        return businessUserProfile;
    }

    public ArrayList<SelectItem> getUsers() {
        return users;
    }

    public ArrayList<SelectItem> getGroups() {
        return groups;
    }

    /**
     * @param userId
     * @return
     * @throws AmpBusinessException
     */
    public void getCurrentUserDetails(String userId) throws AmpException {
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            if (ampBusinessUserProfile.getUserId() == null) {
                ampBusinessUserProfile.setUserId(userId);
                try {
                    ampBusinessUserProfile.retrieveUserDetails();
                } catch (FaultMessage e) {
                    LOGGER.info(this, "getCurrentUserType",
                                LOGGER_PREFIX + "Current User Lookup Failed",
                                null);
                    Fault fault = e.getFaultInfo();
                    throw new AmpException(fault.getFaultCode(),
                                           fault.getFaultMessage());
                }
            }
        }
    }

    public void retrieveAppraisersList(String district,
                                       String role) throws AmpException {
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            ampBusinessUserProfile.setSelectedRoleName(role);
            users.clear();
            try {
                ampBusinessUserProfile.setSelectedDistrictName(district);
                ampBusinessUserProfile.retrieveDistrictUsersAndReportees();
                List<IdentityUserWrapper> consolidatedUserList =
                    ampBusinessUserProfile.getConsolidatedUserList();
                for (IdentityUserWrapper identityUserWrapper :
                     consolidatedUserList) {
                    SelectItem item = new SelectItem();
                    String uid = identityUserWrapper.getUid();
                    item.setValue(uid);
                    item.setLabel(identityUserWrapper.getDisplayName() + " (" +
                                  uid + ")");
                    users.add(item);
                }
            } catch (FaultMessage e) {
                String log = "District: " + district + " Role: " + role;
                LOGGER.info(this, "retrievePDCRAssignmentDetails",
                            LOGGER_PREFIX + "Lookup Failed for Users in " +
                            log, null);
                Fault fault = e.getFaultInfo();
                throw new AmpException(fault.getFaultCode(),
                                       fault.getFaultMessage());
            }
        }
    }

    public void retrieveGroupsList() throws AmpException {
        groups = new ArrayList<SelectItem>();
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            try {
                //Defect 13296 to take care of different roles for groups
                if (AmpRolesConstants.CHIEF_APPRAISER.equals(ampBusinessUserProfile.getHighestRole())) {
                    ampBusinessUserProfile.retrieveIdentityGroupsByRole(AmpRolesConstants.PRINCIPAL_APPRAISER);
                } else if (AmpRolesConstants.PRINCIPAL_APPRAISER.equals(ampBusinessUserProfile.getHighestRole())) {
                    ampBusinessUserProfile.retrieveIdentityGroupsByRole(AmpRolesConstants.SUPERVISOR);
                } else {
                    ampBusinessUserProfile.retrieveIdentityGroupsByUserId();
                }
                List<IdentityGroupType> groupList =
                    ampBusinessUserProfile.getUserGroupList();
                for (IdentityGroupType identityGroupType : groupList) {
                    SelectItem item = new SelectItem();
                    item.setValue(identityGroupType.getDisplayName());
                    item.setLabel(identityGroupType.getDisplayName());
                    groups.add(item);
                }
            } catch (FaultMessage e) {
                LOGGER.info(this, "retrievePDCRAssignmentDetails",
                            LOGGER_PREFIX +
                            "Lookup Failed for Principal Appraisr Grups",
                            null);
                Fault fault = e.getFaultInfo();
                throw new AmpException(fault.getFaultCode(),
                                       fault.getFaultMessage());
            }
        }
    }

    public ArrayList<SelectItem> getRoles() {
        roles.clear();
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            if (AmpRolesConstants.SUPERVISOR.equals(ampBusinessUserProfile.getHighestRole())) {
                roles.add(new SelectItem(AmpRolesConstants.APPRAISER,
                                         AmpRolesConstants.APPRAISER));
            } else if (AmpRolesConstants.CHIEF_APPRAISER.equals(ampBusinessUserProfile.getHighestRole()) ||
                       AmpRolesConstants.PRINCIPAL_APPRAISER.equals(ampBusinessUserProfile.getHighestRole())) {
                roles.add(new SelectItem(AmpRolesConstants.PRINCIPAL_APPRAISER,
                                         AmpRolesConstants.PRINCIPAL_APPRAISER));
                roles.add(new SelectItem(AmpRolesConstants.SUPERVISOR,
                                         AmpRolesConstants.SUPERVISOR));
                roles.add(new SelectItem(AmpRolesConstants.APPRAISER,
                                         AmpRolesConstants.APPRAISER));
            }
        }
        return roles;
    }

    public ArrayList<SelectItem> getAllDistricts() {
        if (allDistricts.size() <= 0) {
            AmpBusinessUserProfile ampBusinessUserProfile =
                getBusinessUserProfile();
            if (ampBusinessUserProfile != null) {
                List<String> districts =
                    ampBusinessUserProfile.getAllDistricts();
                for (String district : districts) {
                    allDistricts.add(new SelectItem(district, district));
                }
            }
        }
        return allDistricts;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void searchUsers(ActionEvent actionEvent) {
        if (getCurrentDistrict() == null) {
            JSFUtils.addFacesErrorMessage("District is Required");
        }
        if (getCurrentRole() == null) {
            JSFUtils.addFacesErrorMessage("Roles is Required");
        }
        try {
            if ("U".equals(currentType) && getCurrentDistrict() != null &&
                getCurrentRole() != null) {
                retrieveAppraisersList(getCurrentDistrict(), getCurrentRole());
            }
        } catch (AmpException e) {
            ADFUtils.addFacesInformationMessage(e.getErrCode());
        }
    }

    public void typeValueChange(ValueChangeEvent vce) {
        try {
            getUserFromSecurity();
            if ("G".equals(vce.getNewValue())) {
                retrieveGroupsList();
                ADFUtils.setBoundAttributeValue("assignmentBox_UserId", null);
                ADFUtils.setBoundAttributeValue("userType", null);
            }
            if ("U".equals(vce.getNewValue())) {
                ADFUtils.setBoundAttributeValue("assignmentBox_GroupId", null);
            }
        } catch (AmpException e) {
            ADFUtils.addFacesInformationMessage(e.getErrCode());
        }
    }

    private void getUserFromSecurity() throws AmpException {
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        String userName = secCntx.getUserName();
        if (userName == null) {
            LOGGER.error(this, "getCurrentUserType",
                         LOGGER_PREFIX + "Current Login UserId is Null", null);
        }
        getCurrentUserDetails(userName);
    }

    public void roleValueChange(ValueChangeEvent vce) {
        ADFUtils.setBoundAttributeValue("userType", vce.getNewValue());
    }

    public void setCurrentDistrict(String currentDistrict) {
        this.currentDistrict = currentDistrict;
    }

    public String getCurrentDistrict() {
        return currentDistrict;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public String getCurrentType() {
        return currentType;
    }

    public ArrayList<SelectItem> getType() {
        if (type == null) {
            type = new ArrayList<SelectItem>();
            type.add(new SelectItem("G", "Groups"));
            type.add(new SelectItem("U", "Users"));
        }
        return type;
    }

    public List<SelectItem> getUserDistricts() {
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            if (AmpRolesConstants.PRINCIPAL_APPRAISER.equals(ampBusinessUserProfile.getHighestRole()) ||
                AmpRolesConstants.CHIEF_APPRAISER.equals(ampBusinessUserProfile.getHighestRole())) {
                LOGGER.info(this, "getUserDistricts",
                            LOGGER_PREFIX + "Return All Districts List", null);
                return this.getAllDistricts();
            } else {
                List<String> districts =
                    ampBusinessUserProfile.getUserDistrictList();
                userDistricts.clear();
                for (String s : districts) {
                    SelectItem item = new SelectItem(s, s);
                    userDistricts.add(item);
                }
            }
        }
        return userDistricts;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        if (displayName == null) {
            try {
                getUserFromSecurity();
                AmpBusinessUserProfile ampBusinessUserProfile =
                    getBusinessUserProfile();
                if (ampBusinessUserProfile != null) {
                }
            } catch (AmpException e) {
                LOGGER.error(this, "getDisplayName",
                             LOGGER_PREFIX + "Failed to get user details",
                             null);
            }
        }
        return displayName;
    }
}
