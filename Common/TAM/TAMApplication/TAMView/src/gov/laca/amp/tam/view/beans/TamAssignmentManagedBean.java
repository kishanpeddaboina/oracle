package gov.laca.amp.tam.view.beans;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.tam.model.facade.TAMAssignmentFacade;

import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.util.ComponentReference;

public class TamAssignmentManagedBean extends AmpManagedBean {
    static AmpLogger logger = new AmpLogger(TamAssignmentManagedBean.class);
    protected static String TAM_ASSIGNMENT_TABLE_ITERATOR = "tamAssignmentPojoListIterator";
    protected static String TAM_ASSIGNMENT_SELECTED_TABLE_ITERATOR = "tamSelectedAINsPojoListIterator";

    private Boolean selectAllCheckbox;
    private ArrayList<String> regions;
    private ArrayList<String> clusters;
    private ArrayList<String> userIdList;
    private ArrayList<String> assigneeUserList;
    private String assignedTo;
    private String actionStatus = "";
    private String assigneeDistrict = "";
    private String assigneeRole = "";
    private String searchRole="";
    private ComponentReference userId_UI;
    private ComponentReference search_assignedTo_UI;
    private ArrayList<String> assigneeRoleList;
    private ArrayList<String> searchRoleList;
//    private RichSelectOneChoice search_Assignee_Role_UI;
//    private RichCommandButton search_UsersBtn_UI;
    private ComponentReference search_Assignee_Role_UI;
    private ComponentReference search_UsersBtn_UI;

    public TamAssignmentManagedBean() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    //

    public void selectAllCheckboxValueChange(ValueChangeEvent vce) {
        // Add event code here...
        Boolean selected = (Boolean)vce.getNewValue();
        logger.warn("===>Entering, selectAllCheckboxValueChange, value:" +
                    selected);
        DCIteratorBinding iter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_TABLE_ITERATOR);
        Row[] rows = iter.getAllRowsInRange();
        logger.warn("===>length:" + rows.length);
        for (Row r : rows) {
            r.setAttribute("selected", selected);

        }
        iter.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(vce.getComponent().getParent().getParent());

    }
    //

    public void onSearchIsAssignedChange(ValueChangeEvent vce) {
        // Add event code here...
        System.out.println("==>Entering onSearchIsAssignedChange");
        Boolean selected = (Boolean)vce.getNewValue();
        this.setSearchRole(null);    
        if (selected == true) {
            this.getSearch_assignedTo_UI().setDisabled(false);
            this.getSearch_Assignee_Role_UI().setDisabled(false);
            this.getSearch_UsersBtn_UI().setDisabled(false);
       //     AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSearch_assignedTo_UI());
        } else {
            System.out.println("==> onSearchIsAssignedChange - selected false. Resetting values");
             this.getSearch_assignedTo_UI().setDisabled(true);
            ADFUtils.setBoundAttributeValue("assignedTo", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSearch_assignedTo_UI());
            this.getSearch_Assignee_Role_UI().setDisabled(true);
            this.getSearch_UsersBtn_UI().setDisabled(true);
            String highestRole = (String)ADFUtils.getBoundAttributeValue("userHighestRole");        
        if(!highestRole.equals("Supervisor")){
            this.userIdList = new ArrayList<String>();
            }
              ADFUtils.setBoundAttributeValue("assignedTo", null);
        }


    }

    public String search_action() {
        BindingContainer bindings = getBindings();

        OperationBinding operationBinding =
            bindings.getOperationBinding("searchAINs");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.refreshAssignmentTables();
        this.setActionStatus("");
        this.setSelectAllCheckbox(false);
        return null;
    }

    /**
     *
     * @return
     */
    //    public List<String> getUserDistricts() {
    //        logger.warn("===>Entering, getUserDistricts");
    //       if (userDistricts == null || userDistricts.size() < 1) {
    //            String userHighestRole = (String)ADFUtils.getBoundAttributeValue("userHighestRole");
    //            userDistricts = new ArrayList<String>();
    //            //userDistricts.add("mock");
    //            String userId = (String)ADFUtils.getBoundAttributeValue("loggedInUserId");
    //            TAMAssignmentFacade facade = new TAMAssignmentFacade();
    //            try {
    //                String highestRole;
    //               highestRole = (String)ADFUtils.getBoundAttributeValue("userHighestRole");
    //               // highestRole = facade.getHighestRoleByUserId(userId);
    //                System.out.println("==>highestRole:"+highestRole);
    //                if(highestRole.equals("Supervisor")){
    //                 ArrayList<String>  ldapUserDistricts = (ArrayList<String>)facade.getUserDistricts(userId);
    //                   for(String ldapDst : ldapUserDistricts){
    //                     userDistricts.add(TAMModelUtils.LDAP_TO_TAM_DISTRICTS_MAP.get(ldapDst));
    //                   }
    //                }
    //                else {
    //                    userDistricts = TAMModelUtils.ALL_TAM_DISTRICTS_LIST;
    //                }
    //            } catch (Exception e) {
    //                JSFUtils.addFacesErrorMessage(
    //                           "Error loading Districts, ERROR:"+e.getMessage());
    //                e.printStackTrace();
    //            }
    //            logger.warn("===>Exiting, getUserDistricts, userDistricts:" +
    //                        userDistricts);
    //        }
    //     return userDistricts;
    //    }


    public void setAssignedTo(String assignedTo) {

        this.assignedTo = assignedTo;
        System.out.println("==>assignedTo:" + this.assignedTo);
    }

    public String getAssignedTo() {
        System.out.println("==>assignedTo:" + this.assignedTo);
        return assignedTo;
    }

    public void searchActionLsnr(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String assign_action() {
   
        return "";
    }

    public void reassignActionLsnr(ActionEvent actionEvent) {
        String clientId =
            this.getUserId_UI().getClientId(FacesContext.getCurrentInstance());
        System.out.println("==>clientId:" + clientId);
        if (this.getAssignedTo() == null || this.getAssignedTo().equals("")) {
            System.out.println("==>UserId is required field");
            JSFUtils.addFacesErrorMessage(clientId, "UserId is required field",
                                          "UserId is required field");
            return;
        }
        upsertWorkMgmt(actionEvent, "reassignAINsToUser", clientId);
    }

    public void assignActionLsnr(ActionEvent actionEvent) {
        // Add event code here...
        String clientId =
            this.getUserId_UI().getClientId(FacesContext.getCurrentInstance());
        System.out.println("==>clientId:" + clientId);
        if (this.getAssignedTo() == null || this.getAssignedTo().equals("")) {
            System.out.println("==>UserId is required field");
            JSFUtils.addFacesErrorMessage(clientId, "UserId is required field",
                                          "UserId is required field");
            return;
        }
        upsertWorkMgmt(actionEvent, "assignAINsToUser", "");

    }

    public void unassignActionLsnr(ActionEvent actionEvent) {
        //   String clientId = this.getUserId_UI().getClientId(FacesContext.getCurrentInstance());
        upsertWorkMgmt(actionEvent, "unassignAINsToUser", "");
    }

    //Execute Upsert actions

    private void upsertWorkMgmt(ActionEvent ae, String methodToInvoke,
                                String clientId) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding(methodToInvoke);
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            JSFUtils.addFacesErrorMessage(clientId,
                                          "Error executing " + methodToInvoke);
            reportError(operationBinding);
            System.out.println("==>Error executing " + methodToInvoke +
                               ", error details: " +
                               operationBinding.getErrors().toArray());
            return;
        }
        this.refreshAssignmentTables();
        this.setActionStatus("SUCCESS");
    }


    public void setSearch_assignedTo_UI(RichSelectOneChoice search_assignedTo_UI) {
        this.search_assignedTo_UI =
                ComponentReference.newUIComponentReference(search_assignedTo_UI);
    }

    public RichSelectOneChoice getSearch_assignedTo_UI() {
        if (search_assignedTo_UI != null) {
            return (RichSelectOneChoice)search_assignedTo_UI.getComponent();
        } else {
            return null;
        }
    }

    public void setUserId_UI(RichSelectOneChoice userId_UI) {
        this.userId_UI = ComponentReference.newUIComponentReference(userId_UI);
    }
 

    public RichSelectOneChoice getUserId_UI() {
        if (userId_UI != null) {
            return (RichSelectOneChoice)userId_UI.getComponent();
        } else {
            return null;
        }
    }

    public String next_action() {
        logger.warn("==> Entering " +
                    new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("nextSearchAINs");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("==>ERRORS in nextt_action:" +
                               operationBinding.getErrors().toArray());
            return null;
        }
        DCIteratorBinding iter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_TABLE_ITERATOR);
        iter.executeQuery();
        return "";
    }

    /**
     *
     * @return
     */
    public String prev_action() {
        logger.warn("==> Entering " +
                    new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("previousSearchAINs");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("==>ERRORS in prev_action:" +
                               operationBinding.getErrors().toArray());
            return null;
        }
        DCIteratorBinding iter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_TABLE_ITERATOR);
        iter.executeQuery();
        return "";
    }

    /**
     *
     * @return
     */
    public void select_action(ActionEvent evt) {
        logger.warn("==> Entering " +
                    new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =
            bindings.getOperationBinding("selectAINs");
        String result = (String)operationBinding.execute();

        if (result != null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage fm =
                new FacesMessage(FacesMessage.SEVERITY_INFO, result, "");
            ctx.addMessage(null, fm);
        }

        if (!operationBinding.getErrors().isEmpty()) {
            System.out.println("==>ERRORS in select_action:" +
                               operationBinding.getErrors().toArray());
            return;
        } 
        this.setActionStatus("");
        DCIteratorBinding iter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_SELECTED_TABLE_ITERATOR);
        iter.executeQuery();

    }


    //    public void setSearch_District_UI(RichSelectOneChoice search_District_UI) {
    //        this.search_District_UI = search_District_UI;
    //    }
    //
    //    public RichSelectOneChoice getSearch_District_UI() {
    //        return search_District_UI;
    //    }

    public void setRegions(ArrayList<String> regions) {
        this.regions = regions;
    }

    public ArrayList<String> getRegions() {
        //         if ((regions == null || regions.size() < 1)) {
        //             String district = (String)ADFUtils.getBoundAttributeValue("district");
        //             logger.warn("===>Entering, getRegions");
        //             regions = new ArrayList<String>();
        //             String userId = (String)ADFUtils.getBoundAttributeValue("loggedInUserId");
        //             TAMAssignmentFacade facade = new TAMAssignmentFacade();
        //
        //             try {
        //
        //                   regions = (ArrayList<String>)facade.getRegionsByDistrict(district);
        //
        //
        //             } catch (Exception e) {
        //                 e.printStackTrace();
        //             }
        //             logger.warn("===>Exiting, getregions, regions:" +
        //                         regions);
        //         }
        //
        return regions;
    }

    /**
     *
     * @param valueChangeEvent
     */
    public void onDistrictChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String district = (String)valueChangeEvent.getNewValue();
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        try {
          this.userIdList = new ArrayList<String>();
            this.assigneeUserList = new ArrayList<String>();
        // populateSearchUsersList();           
            //Use the following for better debugging
//            this.userIdList =
//                   (ArrayList<String>)facade.getUsersListByRole(district, role, userId,userName);            
            this.setAssigneeDistrict(district);
            ArrayList<String> assigneeRoles = new ArrayList<String>();
            String highestRole =
                (String)ADFUtils.getBoundAttributeValue("userHighestRole");
            if(!highestRole.equals("Supervisor")){
                 assigneeRoles.add(highestRole);
            }
            assigneeRoles.add(TAMModelUtils.LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP.get(highestRole));
            this.setAssigneeRoleList(assigneeRoles);
            this.setSearchRoleList(assigneeRoles);

           // this.assigneeUserList = new ArrayList<String>(this.userIdList);
//            System.out.println("==>In Bean, userIdList.size:" +
//                               userIdList.size() +
//                               ", assigneeUserList.length:" +
//                               assigneeUserList.size());
            OperationBinding getRegionsByDistrictOper =
                ADFUtils.findOperation("getRegionsByDistrict");
            Map params1 = getRegionsByDistrictOper.getParamsMap();
            params1.put("district", district);
            this.regions =
                    (ArrayList<String>)getRegionsByDistrictOper.execute();
            this.assigneeDistrict = district;
            this.assigneeRole =
                    TAMModelUtils.LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP.get(highestRole);
            if(highestRole.equals("Supervisor")){
              this.searchRole   =
                    TAMModelUtils.LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP.get(highestRole);
                populateSearchUsersList(district);
                this.assigneeUserList = new ArrayList<String>( this.userIdList);
            }
    

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("Error on district change, district:" +
                                          district + ", Error:" +
                                          e.getMessage());
        }
    }

    public void onAssigneeRoleChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        this.assigneeUserList = null;
        //        this.assigneeRole = (String)valueChangeEvent.getNewValue();
        //        System.out.println("==>Entering onAssigneeRoleChange... selectedRole:"+assigneeRole);
        //        populateAssigneeUsersList();
        //        System.out.println("==>In Bean,  assigneeUserList.length:"+assigneeUserList.size());
    }
    public void onSearchRoleChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        this.userIdList = null;
        //        this.assigneeRole = (String)valueChangeEvent.getNewValue();
        //        System.out.println("==>Entering onAssigneeRoleChange... selectedRole:"+assigneeRole);
        //        populateAssigneeUsersList();
        //        System.out.println("==>In Bean,  assigneeUserList.length:"+assigneeUserList.size());
    }
    /**
     *onAssigneeDistrictChange()
     * @param valueChangeEvent
     */
    public void onAssigneeDistrictChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        this.assigneeUserList = null;
        //        this.assigneeDistrict = (String)valueChangeEvent.getNewValue();
        //        System.out.println("==>Entering onAssigneeDistrictChange... assigneeDistrict:"+assigneeDistrict);
        //        populateAssigneeUsersList();
        //
    }

    /**
     *searchAssigneeUsers()
     * @return
     */
    public void searchAssigneeUsers(ActionEvent evt) {
        // Add event code here...
        // this.assigneeRole = (String)valueChangeEvent.getNewValue();
        System.out.println("==>Entering onAssigneeRoleChange... selectedRole:" +
                           assigneeRole);
        populateAssigneeUsersList();
        System.out.println("==>In Bean,  assigneeUserList.size:" +
                           assigneeUserList.size());
        return ;
    }
    
    public void searchUsers(ActionEvent evt) {
        // Add event code here...
 
        if(this.getSearchRole() == null){
            JSFUtils.addFacesInformationMessage("Select an assignee role to search users");
            return;
        }
        System.out.println("==>Entering onSearchRoleChange... selectedRole:" +
                           this.searchRole);
        populateSearchUsersList();
        System.out.println("==>In Bean,  search userIdList.size:" +
                           userIdList.size());
        return;
    }

    /**
     *populateAssigneeUsersList
     */
    private void populateAssigneeUsersList() {
        try {
            //   String role = (String)ADFUtils.getBoundAttributeValue("userHighestRole");
            String userId =
                (String)ADFUtils.getBoundAttributeValue("loggedInUserId");
            String userName =
                (String)ADFUtils.getBoundAttributeValue("loggedInUserDisplayName");
            OperationBinding getUsersListByRoleOper =
                ADFUtils.findOperation("getUsersListByRole");
            Map params = getUsersListByRoleOper.getParamsMap();
            params.put("district", this.assigneeDistrict);
            params.put("role", this.assigneeRole);
            params.put("loggedInUserId", userId);
            params.put("userName", userName);
            this.assigneeUserList =
                    (ArrayList<String>)getUsersListByRoleOper.execute();
            if (assigneeUserList != null)
                System.out.println("==>In Bean,  assigneeUserList.length:" +
                                   assigneeUserList.size());

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("Error on Assignee District change, assigneeDistrict:" +
                                          this.assigneeDistrict + ", Error:" +
                                          e.getMessage());
        }
    }
    
    private void populateSearchUsersList(String district) {
        ADFUtils.setBoundAttributeValue("district",district );
        populateSearchUsersList();
    }
    /**
     * populateSearchUsersList
     */
    private void populateSearchUsersList() {
        logger.warn("==>Entering populateSearchUsersList ...");
        try {
            //   String role = (String)ADFUtils.getBoundAttributeValue("userHighestRole");
            String district =
                (String)ADFUtils.getBoundAttributeValue("district");
            String userId =
                (String)ADFUtils.getBoundAttributeValue("loggedInUserId");
            String userName =
                (String)ADFUtils.getBoundAttributeValue("loggedInUserDisplayName");
            OperationBinding getUsersListByRoleOper =
                ADFUtils.findOperation("getUsersListByRole");
            Map params = getUsersListByRoleOper.getParamsMap();
            params.put("district",district );
            params.put("role", this.searchRole);
            params.put("loggedInUserId", userId);
            params.put("userName", userName);
            this.userIdList =
                    (ArrayList<String>)getUsersListByRoleOper.execute();
            if (userIdList != null)
                System.out.println("==>In Bean,  userIdList.length:" +
                                   userIdList.size());

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("Error on Search Role  change, Role:" +
                                          this.searchRole + ", Error:" +
                                          e.getMessage());
        }
    }
/**
 * refreshAssignmentTables
 */
    public void refreshAssignmentTables(){
        DCIteratorBinding iter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_TABLE_ITERATOR);
        iter.executeQuery();
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(this.getTamAssignmentTableBinding());
        DCIteratorBinding selectedTableiter =
            ADFUtils.findIterator(TAM_ASSIGNMENT_SELECTED_TABLE_ITERATOR);
        selectedTableiter.executeQuery();
    }
    /**
     *
     * @param clusters
     */
    public void setClusters(ArrayList<String> clusters) {
        this.clusters = clusters;
    }

    public ArrayList<String> getClusters() {
        return clusters;
    }

    public void setUserIdList(ArrayList<String> userIdList) {
        this.userIdList = userIdList;
    }

    public ArrayList<String> getUserIdList() {
        return userIdList;
    }


    public void onRegionChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    private void reportError(OperationBinding opBinding) {
        FacesContext context = FacesContext.getCurrentInstance();
        List errors = opBinding.getErrors();
        for (Object object : errors) {
            context.addMessage("Error msg:",
                               new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Error", object.toString()));
        }
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setSelectAllCheckbox(Boolean selectAllCheckbox) {
        this.selectAllCheckbox = selectAllCheckbox;
    }

    public Boolean getSelectAllCheckbox() {
        return selectAllCheckbox;
    }

    public void setAssigneeUserList(List<String> assigneeUserList) {
        this.assigneeUserList = (ArrayList<String>)assigneeUserList;
    }

    public List<String> getAssigneeUserList() {
        return assigneeUserList;
    }

    public void setAssigneeDistrict(String assigneeDistrict) {
        this.assigneeDistrict = assigneeDistrict;
    }

    public String getAssigneeDistrict() {
        return assigneeDistrict;
    }

    public void setAssigneeRole(String assigneeRole) {
        this.assigneeRole = assigneeRole;
    }

    public String getAssigneeRole() {
        return assigneeRole;
    }

    public void setAssigneeRoleList(ArrayList<String> assigneeRoleList) {
        this.assigneeRoleList = assigneeRoleList;
    }

    public ArrayList<String> getAssigneeRoleList() {
        return assigneeRoleList;
    }


    public void setSearchRole(String searchRole) {
        this.searchRole = searchRole;
    }

    public String getSearchRole() {
        return searchRole;
    }

    public void setSearchRoleList(ArrayList<String> searchRoleList) {
        this.searchRoleList = searchRoleList;
    }

    public ArrayList<String> getSearchRoleList() {
        return searchRoleList;
    }

    public void setSearch_Assignee_Role_UI(RichSelectOneChoice search_Assignee_Role_UI) {
        this.search_Assignee_Role_UI = ComponentReference.newUIComponentReference(search_Assignee_Role_UI);
    }

    public RichSelectOneChoice getSearch_Assignee_Role_UI() {
        if (search_Assignee_Role_UI != null) {
            return (RichSelectOneChoice)search_Assignee_Role_UI.getComponent();
        } else {
            return null;
        }
    }

    public void setSearch_UsersBtn_UI(RichCommandButton search_UsersBtn_UI) {
        this.search_UsersBtn_UI = ComponentReference.newUIComponentReference(search_UsersBtn_UI);
    }

    public RichCommandButton getSearch_UsersBtn_UI() {
        if (search_UsersBtn_UI != null) {
            return (RichCommandButton)search_UsersBtn_UI.getComponent();
        } else {
            return null;
        }
    }
}
