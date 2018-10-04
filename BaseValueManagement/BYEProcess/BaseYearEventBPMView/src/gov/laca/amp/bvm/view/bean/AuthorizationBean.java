package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.HumanTaskNames;
import gov.laca.amp.bvm.util.Roles;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

public class AuthorizationBean extends AmpManagedBean {
    @SuppressWarnings("compatibility:-2511638802831107673")
    private static final long serialVersionUID = 1L;

    public static final AmpLogger LOGGER =
        new AmpLogger(AuthorizationBean.class);
    private Boolean enableSubmit = false;
    private Boolean enableApprove = false;
    private Boolean enableReject = false;
    private Boolean enableAssign = false;
    private Boolean enableCancel = false;
    private Boolean isReadOnly = true;
    private Boolean canCreate = true;

    private String role = null;
    private String humantaskName = null;
    private String assignedBY = null;
    private String source = null;

    public AuthorizationBean() {
        super();
    }

    public void setEnableSubmit(Boolean enableSubmit) {
        this.enableSubmit = enableSubmit;
    }

    public Boolean getEnableSubmit() {
        enableSubmit = checkSubmit();
        return enableSubmit;
    }

    public Boolean getEnableApprove() {
        enableApprove = checkApprove();
        return enableApprove;
    }

    public Boolean getEnableReject() {
        enableReject = checkReject();
        return enableReject;
    }

    public Boolean getEnableAssign() {
        enableAssign = checkAssign();
        return enableAssign;
    }

    public Boolean getEnableCancel() {
        enableCancel = checkCancel();
        return enableCancel;
    }

    public Boolean getIsReadOnly() {
        isReadOnly = checkReadOnly();
        return isReadOnly;
    }

    public Boolean getCanCreate() {
        canCreate = checkCreate();
        return canCreate;
    }
    
    private String getRole() {
        if (role == null && !BaseYearEventConstants._TEST) {
            role = (String)ADFUtils.getBoundAttributeValue(Roles.ROLE_BINDING_NAME);
        }
        return role;
    }

    private String getHumantaskName() {
        if (humantaskName == null && !BaseYearEventConstants._TEST) {
            humantaskName =
                    (String)ADFUtils.getBoundAttributeValue(Roles.TITLE_BINDING_NAME);
        }
        return humantaskName;
    }

    private String getAssignedBY() {
        //Disable this condition to run Unit Tests
        if (assignedBY == null && !BaseYearEventConstants._TEST) {
            assignedBY =
                    (String)ADFUtils.getBoundAttributeValue(Roles.ASSIGNEDBY_BINDING_NAME);
        }
        return assignedBY;
    }

    private Boolean checkSubmit() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Submit Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        //SubmitBaseYearEvent Task
        if (humanTaskName.equals(HumanTaskNames.SUBMITBASEYEAREVENT)) {

            if (Roles.APPRAISER.equals(role) ||
                Roles.SUPERVISOR.equals(role) ||
                Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SELF_ASSIGN_BY_VALUE.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.APPRAISER.equals(role)) {
                if (assignedBy.equals(Roles.SUPERVISOR) ||
                    assignedBy.equals(Roles.PRINCIPAL_APPRAISER) ||
                    assignedBy.equals(Roles.CHIEF_APPRAISER)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.APPRAISER.equals(role)) {
                if (assignedBy.equals(Roles.SUPERVISOR)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.SUPERVISOR.equals(role)) {
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (assignedBy.equals(Roles.CHIEF_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        //ManageBaseYearEvent  Task
        if (humanTaskName.equals(HumanTaskNames.MANAGEBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (assignedBy.equals(Roles.APPRAISER)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.SUPERVISOR)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
                
                if (assignedBy.equals(Roles.CHIEF_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
                
                if (assignedBy.equals(Roles.CHIEF_APPRAISER)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (assignedBy.equals(Roles.APPRAISER)) {
                    return Boolean.TRUE;
                }
                
                if (assignedBy.equals(Roles.SUPERVISOR)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.CHIEF_APPRAISER)) {
                    return Boolean.TRUE;
                }

                if (assignedBy.equals(Roles.CHIEF_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
            }
           
            return Boolean.FALSE;
        }

        //ApproveBaseYearEvent Task
        if (humanTaskName.equals(HumanTaskNames.REVIEWBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER_REJECT)) {
                    return Boolean.TRUE;
                }
                if (assignedBy.equals(Roles.APPRAISER) ||
                    assignedBy.equals(Roles.SUPERVISOR) ||
                    assignedBy.equals(Roles.PRINCIPAL_APPRAISER)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER) ||
                    assignedBy.equals(Roles.CHIEF_APPRAISER_REJECT) ||
                    assignedBy.equals(Roles.SUPERVISOR) ||
                    assignedBy.equals(Roles.CHIEF_APPRAISER)) {
                    return Boolean.TRUE;
                }
            }            
            return Boolean.FALSE;
        }

        //APPROVE BASE YEAR EVENT Task
        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {
//            if (Roles.CHIEF_APPRAISER.equals(role)) {
//                if (assignedBy.equals(Roles.PRINCIPAL_APPRAISER) || 
//                    assignedBy.equals(Roles.DIRECTOR)){
//                    return Boolean.TRUE;
//                }
//            }
//            if (Roles.DIRECTOR.equals(role)) {
//                if (assignedBy.equals(Roles.ASSISTANT_ASSESSOR)){
//                    return Boolean.TRUE;
//                }
//            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    private Boolean checkApprove() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Approve Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if (humanTaskName.equals(HumanTaskNames.MANAGEBASEYEAREVENT)) {
//            if (Roles.CHIEF_APPRAISER.equals(role)) {
//                if (assignedBy.equals(Roles.APPRAISER)) {
//                    return Boolean.TRUE;
//                }
//            }
            
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        if (humanTaskName.equals(HumanTaskNames.REVIEWBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {
            if (Roles.CHIEF_APPRAISER.equals(role)) {
                if (Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy) ||
                    Roles.DIRECTOR_REJECT.equals(assignedBy) ||                    
                    Roles.SUPERVISOR.equals(assignedBy)){
                        return Boolean.TRUE;
                    }
            }
            if (Roles.DIRECTOR.equals(role)) {
                if (Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEFAPPRAISER_WITHDRAWN.equals(assignedBy) ||
                    Roles.ASSISTNATASSESSOR_REJECT.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.ASSISTANT_ASSESSOR.equals(role)) {
                if (Roles.DIRECTOR.equals(assignedBy) ||
                    Roles.DIRECTOR_WITHDRAWN.equals(assignedBy) ||
                    Roles.ASSISTANT_ASSESSOR_WITHDRAWN.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    private Boolean checkReject() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Reject Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if (humanTaskName.equals(HumanTaskNames.MANAGEBASEYEAREVENT)) {
//            if (Roles.CHIEF_APPRAISER.equals(role)) {
//                if (assignedBy.equals(Roles.APPRAISER)) {
//                    return Boolean.TRUE;
//                }
//            }
            
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        if (humanTaskName.equals(HumanTaskNames.REVIEWBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {
            if (Roles.CHIEF_APPRAISER.equals(role)) {
                if (Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy) ||
                    Roles.DIRECTOR_REJECT.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy)
                    ){
                        return Boolean.TRUE;
                    }
            }
            if (Roles.DIRECTOR.equals(role)) {
                if (Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEFAPPRAISER_WITHDRAWN.equals(assignedBy) ||                    
                    Roles.ASSISTNATASSESSOR_REJECT.equals(assignedBy))
                    return Boolean.TRUE;
            }
            if (Roles.ASSISTANT_ASSESSOR.equals(role)) {
                if (Roles.DIRECTOR.equals(assignedBy) ||
                    Roles.DIRECTOR_WITHDRAWN.equals(assignedBy) ||
                    Roles.ASSISTANT_ASSESSOR_WITHDRAWN.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    private Boolean checkAssign() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Assign Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if(assignedBy.contains(Roles.WITHDRAWN)){
            if(Roles.SUPERVISOR.equals(role) ||
               Roles.PRINCIPAL_APPRAISER.equals(role) ||
               Roles.CHIEF_APPRAISER.equals(role)){
                return Boolean.TRUE;
            }
        }
        
        if (humanTaskName.equals(HumanTaskNames.SUBMITBASEYEAREVENT)) {
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.MANAGEBASEYEAREVENT)) {
//            if (Roles.CHIEF_APPRAISER.equals(role)) {
//                if (assignedBy.equals(Roles.APPRAISER)) {
//                    return Boolean.TRUE;
//                }
//            }            
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.REVIEWBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {            
            if (Roles.CHIEF_APPRAISER.equals(role)) {
                if (Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy) ||
                    Roles.DIRECTOR_REJECT.equals(assignedBy) ||                    
                    Roles.SUPERVISOR.equals(assignedBy)){
                        return Boolean.TRUE;
                    }
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    private Boolean checkCancel() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Cancel Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if (humanTaskName.equals(HumanTaskNames.SUBMITBASEYEAREVENT)) {
            if (Roles.APPRAISER.equals(role) ||
                Roles.SUPERVISOR.equals(role) ||
                Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SELF_ASSIGN_BY_VALUE.equals(assignedBy)) {
                    if(BaseYearEventConstants.TAM_SOURCE_TYPE.equalsIgnoreCase(getSource())){
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }
            }
            if (Roles.APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy)) {
                    if(BaseYearEventConstants.TAM_SOURCE_TYPE.equalsIgnoreCase(getSource())){
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }
            }

            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.PRINCIPAL_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }

            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.MANAGEBASEYEAREVENT)) {
//            if (Roles.CHIEF_APPRAISER.equals(role)) {
//                if (assignedBy.equals(Roles.APPRAISER)) {
//                    return Boolean.TRUE;
//                }
//            } 
            
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy) ||
                    Roles.APPRAISER.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.REVIEWBASEYEAREVENT)) {
            if (Roles.SUPERVISOR.equals(role)) {
                if (Roles.APPRAISER.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.PRINCIPAL_APPRAISER.equals(role)) {
                if (Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.CHIEF_APPRAISER_REJECT.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {
            if (Roles.CHIEF_APPRAISER.equals(role)) {
                if (Roles.PRINCIPAL_APPRAISER.equals(assignedBy) ||
                    Roles.SUPERVISOR.equals(assignedBy) ||
                    Roles.DIRECTOR_REJECT.equals(assignedBy) ||                    
                    Roles.APPRAISER.equals(assignedBy)) {
                    return Boolean.TRUE;
                }
            }
            if (Roles.DIRECTOR.equals(role)) {
                if (Roles.CHIEF_APPRAISER.equals(assignedBy) ||
                    Roles.ASSISTNATASSESSOR_REJECT.equals(assignedBy) ||
                    Roles.CHIEFAPPRAISER_WITHDRAWN.equals(assignedBy))
                    return Boolean.TRUE;
            }
            
            if (Roles.ASSISTANT_ASSESSOR.equals(role)) {
                if (Roles.DIRECTOR.equals(assignedBy) ||
                    Roles.DIRECTOR_WITHDRAWN.equals(assignedBy))
                    return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * @return
     */
    private Boolean checkReadOnly() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Save: Cancel Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if(Roles.SUPERVISOR.equals(role)){
            if(Roles.APPRAISER_WITHDRAWN.equals(assignedBy)){
                return Boolean.TRUE;
            }
        }
        if(Roles.PRINCIPAL_APPRAISER.equals(role)){
            if(Roles.SUPERVISOR_WITHDRAWN.equals(assignedBy)){
                return Boolean.TRUE;
            }
        }
        if(Roles.CHIEF_APPRAISER.equals(role)){
            if(Roles.PRINCIPAL_APPRAISER_WITHDRAWN.equals(assignedBy)){
                return Boolean.TRUE;
            }
        }        
        Boolean withdrawAvailable = (Boolean)ADFUtils.getPageFlowValue("withdrawAvailable");
        if(withdrawAvailable != null && withdrawAvailable){
            return Boolean.TRUE;
        }        
        return Boolean.FALSE;
    }
    
    private Boolean checkCreate() {
        String assignedBy = getAssignedBY();
        String humanTaskName = getHumantaskName();
        String role = getRole();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Action: Create Task Name: " +
                    humanTaskName + " Role: " + role + "  AssignedBy: " +
                    assignedBy);
        if (humanTaskName.equals(HumanTaskNames.APPROVEBASEYEAREVENT)) {
            if(Roles.DIRECTOR.equals(role) ||
               Roles.CHIEF_APPRAISER.equals(role)){
                    return Boolean.FALSE;
            }
            if(Roles.ASSISTANT_ASSESSOR.equals(role)){
                    return Boolean.FALSE;
                }
        }
        if(assignedBy != null  && assignedBy.contains(Roles.WITHDRAWN)){
            return Boolean.FALSE;
        }
        Boolean withdrawAvailable = (Boolean)ADFUtils.getPageFlowValue("withdrawAvailable");
        if(withdrawAvailable != null && withdrawAvailable){
            return Boolean.FALSE;
        } 
        return Boolean.TRUE;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setHumantaskName(String humantaskName) {
        this.humantaskName = humantaskName;
    }

    public void setAssignedBY(String assignedBY) {
        this.assignedBY = assignedBY;
    }

    public String getSource() {
        if (source == null && !BaseYearEventConstants._TEST) {
            source =
                    (String)ADFUtils.getBoundAttributeValue(Roles.SOURCE_BINDING_NAME);
        }
        return source;
    }
}
