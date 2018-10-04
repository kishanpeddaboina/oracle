package gov.laca.amp.common.assign.view;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.AmpRolesConstants;
import gov.laca.amp.fwk.util.JSFUtils;

import gov.laca.amp.portal.extension.AmpBusinessUserProfile;

import java.util.ArrayList;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;

/**
 * @author Vijay Redla
 * @version
 */
public class AssignmentLookupBeanPdcr extends AssignmentLookupBean {
   
    @SuppressWarnings("compatibility:-6384750310576266599")
    private static final long serialVersionUID = 1L;
    
    private List<SelectItem> userDistricts = new ArrayList<SelectItem>();

    public AssignmentLookupBeanPdcr() {
        super();
    }

    @Override
    public ArrayList<SelectItem> getRoles() {
        ArrayList<SelectItem> roles = new ArrayList<SelectItem>();
        roles.add(new SelectItem(AmpRolesConstants.APPRAISER,
                                 AmpRolesConstants.APPRAISER));
        return roles;
    }
    
    @Override
    public void searchUsers(ActionEvent actionEvent) {
        LOGGER.warn("Overide the search user");
     
        if (getCurrentDistrict() == null) {
            JSFUtils.addFacesErrorMessage("District is Required");
        }
        if (getCurrentRole() == null) {
            JSFUtils.addFacesErrorMessage("Roles is Required");
        }
        try {
            if (getCurrentRole() != null) {
                retrieveAppraisersList(getCurrentDistrict(), getCurrentRole());
            }
        } catch (AmpException e) {
            ADFUtils.addFacesInformationMessage(e.getErrCode());
        }
    }
    
    @Override
    public List<SelectItem> getUserDistricts() {
        LOGGER.warn("Getting user districts");
        try {
            ADFContext adfCtx = ADFContext.getCurrent();
            SecurityContext secCntx = adfCtx.getSecurityContext();
            String userName = secCntx.getUserName();
            if (userName == null) {
                LOGGER.error(this, "getCurrentUserType",
                             "Current Login UserId is Null",
                             null);
            }
            getCurrentUserDetails(userName);
            ADFUtils.setBoundAttributeValue("assignmentBox_GroupId", null);
        } catch (AmpException e) {
            ADFUtils.addFacesInformationMessage(e.getErrCode());
        }
        
        AmpBusinessUserProfile ampBusinessUserProfile =
            getBusinessUserProfile();
        if (ampBusinessUserProfile != null) {
            if (AmpRolesConstants.PRINCIPAL_APPRAISER.equals(ampBusinessUserProfile.getHighestRole()) ||
                AmpRolesConstants.CHIEF_APPRAISER.equals(ampBusinessUserProfile.getHighestRole())) {
                LOGGER.info(this, "getUserDistricts",
                            "Return All Districts List", null);
                return this.getAllDistricts();
            }else{
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
}
