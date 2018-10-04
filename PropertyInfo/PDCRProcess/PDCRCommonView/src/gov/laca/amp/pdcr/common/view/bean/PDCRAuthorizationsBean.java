package gov.laca.amp.pdcr.common.view.bean;

import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;

import oracle.jbo.Row;


public class PDCRAuthorizationsBean extends AmpManagedBean {

    // Private local variables
    private Boolean isAssignAvailable;
    private Boolean isSaveSubmitAvailable;
    private Boolean isApproveRejectAvailable;

    private static final String ASSIGNEE_ITERATOR = "assigneesIterator";
    private static final String ASSIGNEE_TYPE_GROUP = "group";
    private static final String ASSIGNEE_TYPE_USER = "user";

    public PDCRAuthorizationsBean() {
        super();
    }

    private static SecurityContext getSecurityContext() {
        return ADFContext.getCurrent().getSecurityContext();
    }

    private Boolean isUserAssignee() {
        // Get the userID and the userRoles from LDAP
        String userID = getSecurityContext().getUserProfile().getUserID();
        String userRoles = JSONUtils.toJson(getSecurityContext().getUserRoles());

        // Get the binding iterator for the assignees of the task
        DCIteratorBinding assigneeIterBinding = ADFUtils.findIterator(ASSIGNEE_ITERATOR);
        Boolean isUserInAssignee = Boolean.FALSE;

        // loop through the assignees to see if they are part of the assignee either by userId or group
        for (int i = 0; i < assigneeIterBinding.getViewObject().getEstimatedRowCount(); i++) {
            Row row = assigneeIterBinding.getRowAtRangeIndex(i);
            if (row != null) {
                String assigneeType = (String)row.getAttribute("type");
                if (assigneeType.equalsIgnoreCase(ASSIGNEE_TYPE_USER)) {
                    if (userID.equalsIgnoreCase((String)row.getAttribute("id"))) {
                        isUserInAssignee = Boolean.TRUE;
                        break;
                    }
                } else if (assigneeType.equalsIgnoreCase(ASSIGNEE_TYPE_GROUP)) {
                    // modified for 33983 from row.getAttribute("displayName") to  row.getAttribute("id")
                    if (userRoles.contains((String)row.getAttribute("id"))) {
                        isUserInAssignee = Boolean.TRUE;
                        break;
                    }
                }
            }
        }
        return isUserInAssignee;
    }
    
    public void setIsAssignAvailable(Boolean isAssignAvailable) {
        this.isAssignAvailable = isAssignAvailable;
    }

    public Boolean getIsAssignAvailable() {
        isAssignAvailable = Boolean.FALSE;
        if (null != ADFUtils.getBoundAttributeValue("assignedBy")) {
            String asignedBy = (String)ADFUtils.getBoundAttributeValue("assignedBy");
            isAssignAvailable =
                    ((asignedBy.equalsIgnoreCase(ChangeRequestConstants.BPM_ASIIGNBY_APPRAISER) || asignedBy.contains(ChangeRequestConstants.BPM_ASIIGNBY_WITHDRAW)) &&
                     isUserAssignee()) ? Boolean.TRUE : Boolean.FALSE;
        }
        return isAssignAvailable;
    }

    public void setIsSaveSubmitAvailable(Boolean isSaveSubmitAvailable) {
        this.isSaveSubmitAvailable = isSaveSubmitAvailable;
    }

    public Boolean getIsSaveSubmitAvailable() {
        isSaveSubmitAvailable = Boolean.FALSE;
        String userID = getSecurityContext().getUserProfile().getUserID();
        if (null != ADFUtils.getBoundAttributeValue("assignTo_UserID")) {
            String assignTo_UserID = (String)ADFUtils.getBoundAttributeValue("assignTo_UserID");
            isSaveSubmitAvailable = assignTo_UserID.equalsIgnoreCase(userID) ? Boolean.TRUE : Boolean.FALSE;
        }
        return isSaveSubmitAvailable;
    }

    public void setIsApproveRejectAvailable(Boolean isApproveRejectAvailable) {
        this.isApproveRejectAvailable = isApproveRejectAvailable;
    }

    public Boolean getIsApproveRejectAvailable() {
        return isApproveRejectAvailable = isUserAssignee();
    }
}
