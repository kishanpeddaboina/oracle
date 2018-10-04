package gov.laca.amp.propclaimperform.view.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.data.RichListView;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;

import oracle.sql.DATE;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class PropClaimPerformBean extends AmpManagedBean {
    private RichListView transferorListView;
    private RichListView transfereeListView;
    private RichListView directDescendantsListView;
    private RichListView relationShipListView;
    private RichListView prevTransferListView;
    private RichPanelGridLayout transferorAddQuesGrid;
    private RichPanelGridLayout transfereeAddQuesGrid;
    boolean showPropQuest2 = true;
    
    boolean prop58Ques1 = false;
    private RichSelectOneRadio prop58QAns1;
    private RichInputText assessorParcelNo1;
    private RichInputText assessorParcelNo2;
    private RichPanelGroupLayout commentsPgl;
    private RichInputText assessorParcelNo3;
    private RichInputText assessorParcelNo4;

    public PropClaimPerformBean() {
        super();
    }

    DATE currentdate;

    /**
     * @return
     */
    public String onNextAction() {
        //Call Upsert
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();
        DCIteratorBinding iter3 = bindings.findIteratorBinding("availableDocumentIterator");
        if (iter3 != null) {
            Row row = iter3.getCurrentRow();
            if (row != null) {
            }
        } 
        
        //else

        Integer workUnitId = null;
        Integer propsId = null;

        //This should work from the BPM UI Task.

        workUnitId = Integer.parseInt((String) ADFUtils.getPageFlowValue("wuid"));
        propsId = Integer.parseInt((String) ADFUtils.getPageFlowValue("propsid"));

        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("upsertProps"); 
        op1.getParamsMap().put("p0", workUnitId);
        op1.getParamsMap().put("p1", propsId);   

        String opStatus = (String) op1.execute(); 
       
        String checkOpa = checkClaimResult();
//        //Commenting for testing......PLEASE UNCOMMENT _ IF IT IS COMMENTED
        if (("callOpa").equals(checkOpa)) {
            ADFUtils.setPageFlowValue("CALLOPA", "YES");
        } else {
            ADFUtils.setPageFlowValue("CALLOPA", "NO");
        }
        //comment this
         //ADFUtils.setPageFlowValue("CALLOPA", "YES");
        return "gotoQual";
    }

    /**
     * @return
     */
    public String checkClaimResult() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1 = bindings.findIteratorBinding("conclusionIterator");
        iter1.executeQuery();
        String claimResultFromConc = null;
        if (iter1 != null) {
            Row row = iter1.getCurrentRow();
            if (row == null)
                return "callOpa";
            if (row != null) {
                claimResultFromConc = (String) row.getAttribute("claimResult");

                if (claimResultFromConc == null ||
                    ("").equals(claimResultFromConc)) //recheck this condition, if it can be present and empty
                {
                    return "callOpa";
                }
            }
        }
        return "noOpa";
    }

    public void populateConclusionTestData() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //   DCIteratorBinding iter = bindings.findIteratorBinding("conclusionIterator");
        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("addTestDataForConclusion");
        op1.execute();
        DCIteratorBinding iter1 = bindings.findIteratorBinding("propsPojoIterator");

        if (iter1 != null) {

        }
        DCIteratorBinding iter = bindings.findIteratorBinding("conclusionIterator");
        iter.executeQuery();
        if (iter != null) {
            Row row = iter.getCurrentRow();
        }
    }

    /**
     * @return
     */
    public String loadPropsLOV() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("loadLOVData");
        String wuid = (String) ADFUtils.getPageFlowValue("wuid");

        String propsid = (String) ADFUtils.getPageFlowValue("propsid");
        //This should work from the BPM UI Task.
        //workUnitId = (Integer) JSFUtils.resolveExpression("#{bindings.WorkUnitId.inputValue}");
        opBind.getParamsMap().put("wuid", wuid);
        opBind.getParamsMap().put("propsid", propsid);
        //opBind.getParamsMap().put("wuId", "");
        String opStatus = (String) opBind.execute();
        
//        OperationBinding opBind0 = (OperationBinding) bindings.getOperationBinding("loadAvailableDocs");
//        String opStatus1 = (String) opBind0.execute();
////        DCIteratorBinding iter9 = bindings.findIteratorBinding("propsPojoIterator");
////        iter9.executeQuery();
//        
        return "launchUI";
    }

    /**
     * @return
     */
    public String checkMileStone() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("AMPWorkTaskIterator");
        iter0.executeQuery();
        String wuid = null;
        String propsid = null;
        Row row = iter0.getCurrentRow();
        if (row.getAttribute("WorkUnitId") != null) {
            wuid = row.getAttribute("WorkUnitId").toString();
        }
System.out.println("Bussines Functional ID ::"+row.getAttribute("BusinessFunctionId"));
        if (row.getAttribute("BusinessFunctionId") != null) {
            propsid = row.getAttribute("BusinessFunctionId").toString();
        } else {
            propsid = "2433536"; //todo need to remove hard coded value.
        }

        String category = (String) row.getAttribute("Category");
        String subcategory = (String) row.getAttribute("Subcategory");
        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("PropertyIdentificationIterator");
        iter1.executeQuery();
        Row row1 = iter1.getCurrentRow();

        String ain = (String) row1.getAttribute("AIN");
System.out.println("ain ::: checkmilestone :"+ain);
        //

        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("WorkTaskDetailsIterator");
        iter2.executeQuery();
        Row row2 = iter2.getCurrentRow();

        String worktaskname = (String) row2.getAttribute("WorkTaskName");
        String milestone = (String) row2.getAttribute("Milestone");
        //                  String WorktaskName = (String)row2.getAttribute("WorkTaskName");

        DCIteratorBinding iter3 = (DCIteratorBinding) bindings.findIteratorBinding("systemAttributesIterator");
        iter3.executeQuery();
        Row row3 = iter3.getCurrentRow();

        String state = (String) row3.getAttribute("state");
                String substate = (String) row3.getAttribute("substate");

                logger.info("state :::" + state);
                if (state != null) {
                    if ("WITHDRAWN".equalsIgnoreCase(state)) {
                        ADFUtils.setPageFlowValue("withdrawntask", "Y");
                    } else {

                        if ("Perform Claim".equalsIgnoreCase(worktaskname) && "Assigned".equalsIgnoreCase(state) &&
                            ("Unassigned".equalsIgnoreCase(milestone) || "Suspended".equalsIgnoreCase(milestone))) {
                            String enableReassign = "Y";
                            ADFUtils.setPageFlowValue("enableReassign", enableReassign);
                        }
                    }
                }

        //enableReassign
        ////                  String wuid = "2899";
        //                   String wuid = "2898";
        ////                    String wuid = "3052";  //193
        ////                   String propsid = "2290659";
        //                  String propsid = "2433536";
        //                  String propsType = "58"; //get this from the claim info part and keep in pageflow
        //                  String category = "Exclusions";
        //                  String subcategory = "Prop58";
        //                String ain = "2004002017";
        //                    String parentId ="2290659";
        ADFUtils.setPageFlowValue("wuid", wuid);
        ADFUtils.setPageFlowValue("ain", ain);
        ADFUtils.setPageFlowValue("propsid", propsid);
        //                  ADFUtils.setPageFlowValue("propsType", propsType);
        ADFUtils.setPageFlowValue("maskData", true);
        ADFUtils.setPageFlowValue("parentId", propsid);
        ADFUtils.setPageFlowValue("category", category);
        ADFUtils.setPageFlowValue("subcategory", subcategory);

        List<SelectItem> ainList = new ArrayList<SelectItem>();
        SelectItem selectObj = new SelectItem();

        selectObj.setValue(ain);
        ainList.add(selectObj);

        ADFUtils.setPageFlowValue("ainList", ainList);

        return "gotoRouter";
    }

    /**
     * @return
     */
    public String loadPropsData() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("retrievePropsDetails");

        // Integer workUnitId = (Integer) ADFUtils.getPageFlowValue("wuid");

        //String wuid = "2711";

        // START LOCAL TESTING


                            /*
                             * PROP 58 :
                             *  String wuid1 = "3410";
                                String propsid = "2433768";
                                PROP 193 : 
                                wuid : 3435
                                propsid : 2433777
                             */
                            String wuid1 = "4351";
                            String propsid = "2433912";
////                            String wuid1 = "4118";
////                          
////                            String propsid = "2433872";
////                             String wuid = "2898";
////                              String wuid1 = "3052";  //193
////                             String propsid = "2290659";
//        
//                            String propsType = "58"; //get this from the claim info part and keep in pageflow
//                            String category ="Exclusions";
//                            String subcategory ="Prop 58";
//        
//                            //  String parentId ="2290659";
//                            //String parentId = "2433536";
//                            ADFUtils.setPageFlowValue("wuid", wuid1);
//                            ADFUtils.setPageFlowValue("propsid", propsid);
//                            ADFUtils.setPageFlowValue("propsType", propsType);
//                            ADFUtils.setPageFlowValue("maskData", true);
//                            ADFUtils.setPageFlowValue("parentId", propsid);
//                            ADFUtils.setPageFlowValue("category", category);
//                            ADFUtils.setPageFlowValue("subcategory", subcategory);
//                            ADFUtils.setPageFlowValue("maskData", false);
//        
//        
//                            List<SelectItem> ainList = new ArrayList<SelectItem>();
//                            SelectItem selectObj = new SelectItem();
//        
//                            selectObj.setValue("2004001009");
//                            ainList.add(selectObj);
//        
//                            ADFUtils.setPageFlowValue("ainList", ainList);
        // END LOCAL TESTING

//        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    
        
        //This should work from the BPM UI Task.
        //workUnitId = (Integer) JSFUtils.resolveExpression("#{bindings.WorkUnitId.inputValue}");
        String wuid = (String) ADFUtils.getPageFlowValue("wuid");
        opBind.getParamsMap().put("wuid", wuid);
        opBind.execute();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter0.executeQuery();
        if (iter0 != null) {
            Row row0 = iter0.getCurrentRow();
            if (row0 != null) {
                String propertyType = (String) row0.getAttribute("propertyType");
                ADFUtils.setPageFlowValue("propertyType", propertyType);
            }
        }
        DCIteratorBinding iter = bindings.findIteratorBinding("conclusionIterator");
        // iter.executeQuery();

        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransferorPojoInfoIterator");
        iter2.executeQuery();

        Row[] row = iter2.getAllRowsInRange();


        DCIteratorBinding iter3 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameIterator");
        iter3.executeQuery();
        Row[] row3 = iter3.getAllRowsInRange();
        if (iter != null) {
            Row row0 = iter.getCurrentRow();
            if (row0 != null) {
                String claimResult = (String) row0.getAttribute("claimResult");
                String approvalType = (String) row0.getAttribute("approvalType");
                if(("Approved".equalsIgnoreCase(claimResult)) && ("Partial Interest Approved".equalsIgnoreCase(approvalType))){
                    ADFUtils.setPageFlowValue("PartialShow", "YES");
                }
                ADFUtils.setPageFlowValue("claimResult", claimResult);
                ADFUtils.setPageFlowValue("approvalType", approvalType);
            }
        }
        SecurityContext secCntx = ADFContext.getCurrent().getSecurityContext();
        String[] userRoles = secCntx.getUserRoles();
        String isApprover = "true";
        if (Arrays.asList(userRoles).contains("ExclusionValuationApprover") || Arrays.asList(userRoles).contains("ExclusionValuationReviewer")) {
            isApprover = "false";
        }
        System.out.println("isApprover:"+isApprover);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("isApprover", isApprover);
        return "loadLov";
    }


    /**
     * @return
     */
    public String onInvokeOPA() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        Integer wuId = null;
        //This should work from the BPM UI Task.
        AttributeBinding claimType = (AttributeBinding) bindings.getControlBinding("claimType");
        wuId = Integer.parseInt((String) ADFUtils.getPageFlowValue("wuid"));
        String propsType = (String) ADFUtils.getPageFlowValue("propsType");
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("invokeOPA");
        opBind.getParamsMap().put("p0", wuId);
        opBind.getParamsMap().put("p1", claimType);
        opBind.execute();

        DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
        iter3.executeQuery();

        if (ADFUtils.getPageFlowValue("claimResult") == null) {
            DCIteratorBinding iter = bindings.findIteratorBinding("conclusionIterator");
            // iter.executeQuery();
            if (iter != null) {
                Row row = iter.getCurrentRow();
                if (row != null) {
                    String claimResult = (String) row.getAttribute("claimResult");
                    ADFUtils.setPageFlowValue("claimResult", claimResult);
                }
            }
        }

        DCIteratorBinding iter2 = bindings.findIteratorBinding("qualifyQuestionnaireListIterator");

        if (iter2 != null) {
        }
        return "gotoQualUI1";
    }


    /**
     * @return
     */
    public String onGetOtherPropFilings() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("getOtherPropFilings");
        //        /(Integer wuId, String propsType)
        Integer propsId = Integer.parseInt((String) ADFUtils.getPageFlowValue("propsid"));

        opBind.getParamsMap().put("p0", propsId);
        opBind.execute();
        DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
        iter3.executeQuery();
        //Remove this - adding this for debug
        
        DCIteratorBinding iter2 = bindings.findIteratorBinding("qualifyQuestionnaireListIterator");

        if (iter2 != null) {
        }
        DCIteratorBinding iter = bindings.findIteratorBinding("otherPropListIterator");
        //End debug
        return "gotoQualUI2";
    }


    public void setCurrentdate(DATE currentdate) {
        this.currentdate = currentdate;
    }

    public DATE getCurrentdate() {
        return oracle.jbo
                     .domain
                     .Date
                     .getCurrentDate();
    }

    public String AddTransferor() {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("addTransferor");
        opBind.execute();
        
//      RowSetIterator transferorRsi = (RowSetIterator) iter0.getRowSetIterator();
//        if(transferorRsi!= null) {
//            Row createRow = transferorRsi.createRow();
//            createRow.setAttribute("operationFlag", "C");
//            
//            Long rowCount = (Long)iter0.getEstimatedRowCount();
//            int rowindex = Integer.valueOf(rowCount.intValue());
//            transferorRsi.insertRowAtRangeIndex(rowindex, createRow);
//        }
//        
        
        
        
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameIterator");
        iter2.executeQuery();
        
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransferorPojoInfoIterator");
        iter0.executeQuery();
  


        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();

        OperationBinding opBind1 = (OperationBinding) bindings.getOperationBinding("loadTransferorNameList");
        opBind1.execute();

        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameListIterator");
        iter1.executeQuery();



        
          
        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);

        
        return null;
    }

    //

    public String addPrevTransfers() {
        // Add event code here...
        //        retrievePropsDetails

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("addPrevTransfers");
       // opBind.execute();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("transferorPrvTransferIterator");
       // iter2.executeQuery();
        
       RowSetIterator relRsi = (RowSetIterator) iter0.getRowSetIterator();
         if(relRsi!= null) {
             Row createRow = relRsi.createRow();
             createRow.setAttribute("operationFlag", "C");
             
             Long rowCount = (Long)iter0.getEstimatedRowCount();
             int rowindex = Integer.valueOf(rowCount.intValue());
             relRsi.insertRowAtRangeIndex(rowindex, createRow);
         }
         
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(prevTransferListView);
        return null;
    }

    public String AddTransferee() {
        // Add event code here...
        //        retrievePropsDetails

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("addTransferee");
        opBind.execute();
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameIterator");
        iter2.executeQuery();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransfereeInfoIterator");
        iter0.executeQuery();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeListView);
        return null;
    }


    public void setTransferorListView(RichListView transferorListView) {
        this.transferorListView = transferorListView;
    }

    public RichListView getTransferorListView() {
        return transferorListView;
    }

    public String deleteTransferor(ActionEvent actionEvent) {
        // Add event code here...
        Integer selectedIndex = (Integer) actionEvent.getComponent()
                                                     .getAttributes()
                                                     .get("selectedIndex");



        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("deleteTransferor");
        opBind.getParamsMap().put("selectedIndex", selectedIndex);
        opBind.execute();
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameIterator");
        iter2.executeQuery();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransferorPojoInfoIterator");
        iter0.executeQuery();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();


        OperationBinding opBind1 = (OperationBinding) bindings.getOperationBinding("loadTransferorNameList");
        opBind1.execute();
        DCIteratorBinding iter3 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameListIterator");
        iter3.executeQuery();

        DCIteratorBinding iter4 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameListIterator1");
        iter4.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorAddQuesGrid);


        return null;
    }

    public String deleteTransferee(ActionEvent actionEvent) {
        // Add event code here...
        Integer selectedIndex = (Integer) actionEvent.getComponent()
                                                     .getAttributes()
                                                     .get("selectedIndex");



        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("deleteTransferee");
        opBind.getParamsMap().put("selectedIndex", selectedIndex);
        opBind.execute();
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameIterator");
        iter2.executeQuery();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransfereeInfoIterator");
        iter0.executeQuery();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();


        OperationBinding opBind1 = (OperationBinding) bindings.getOperationBinding("loadTransfereeNameList");
        opBind1.execute();
        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameListIterator");
        iter1.executeQuery();

        DCIteratorBinding iter4 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameListIterator1");
        iter4.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeAddQuesGrid);

        return null;
    }


    public String deleteprevTransfers(ActionEvent actionEvent) {
        // Add event code here...
        Integer selectedIndex = (Integer) actionEvent.getComponent()
                                                     .getAttributes()
                                                     .get("selectedIndex");



        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("deletePrevTransfers");
//        opBind.getParamsMap().put("selectedIndex", selectedIndex);
//        opBind.execute();
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("transferorPrvTransferIterator");
//        iter2.executeQuery();
//        DCIteratorBinding iter0 =
//            (DCIteratorBinding) bindings.findIteratorBinding("transferorQuestionnarieContactInfoIterator");
//        iter0.executeQuery();
//        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
//        iter.executeQuery();


        Row r =  iter2.getRowAtRangeIndex(selectedIndex);
        r.remove();
        AdfFacesContext.getCurrentInstance().addPartialTarget(prevTransferListView);

        return null;
    }

    public void setTransfereeListView(RichListView transfereeListView) {
        this.transfereeListView = transfereeListView;
    }

    public RichListView getTransfereeListView() {
        return transfereeListView;
    }

    public String addDirectDesscendants() {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("addDirectDesscendants");
            opBind.execute();
            DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("directDescendantNameIterator");
            iter2.executeQuery();
            DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsDescendentsPojoInfoIterator");
            iter0.executeQuery();
            
            DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
            iter.executeQuery();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(directDescendantsListView);
            return null;
    }


    public String deleteDescendats(ActionEvent actionEvent) {
        // Add event code here...
        Integer selectedIndex = (Integer) actionEvent.getComponent()
                                                     .getAttributes()
                                                     .get("selectedIndex");



        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("deleteDirectDescendants");
        opBind.getParamsMap().put("selectedIndex", selectedIndex);
      //  opBind.execute();
//        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("directDescendantNameIterator");
//        iter2.executeQuery();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsDescendentsPojoInfoIterator");
//        iter0.executeQuery();
//        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
//        iter.executeQuery();
        
        
            Row r =  iter0.getRowAtRangeIndex(selectedIndex);
            r.setAttribute("operationFlag", "D");
//        r.remove();
        

        AdfFacesContext.getCurrentInstance().addPartialTarget(directDescendantsListView);

        return null;
    }


    public void setDirectDescendantsListView(RichListView directDescendantsListView) {
        this.directDescendantsListView = directDescendantsListView;
    }

    public RichListView getDirectDescendantsListView() {
        return directDescendantsListView;
    }

    public String AddRelationship() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("addRelationship");
      //  opBind.execute();
        
        
        //        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("directDescendantNameIterator");
        //               iter2.executeQuery();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("relationshipsIterator");
      //  iter0.executeQuery();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
      //  iter.executeQuery();

      RowSetIterator relRsi = (RowSetIterator) iter0.getRowSetIterator();
        if(relRsi!= null) {
//            Row createRow = relRsi.createRow();
//            createRow.setAttribute("operationFlag", "C");
//            
//            Long rowCount = (Long)iter0.getEstimatedRowCount();
//            int rowindex = Integer.valueOf(rowCount.intValue());
//            
//            relRsi.insertRowAtRangeIndex(rowindex, createRow);
         //   Row firstRow = relRsi.first();
            
            
            Row lastRow = relRsi.last();
            Row firstRow = relRsi.first();
            //obtain the index of the last row
            int lastRowIndex = relRsi.getRangeIndexOf(lastRow);

            
           
           
//           Row newRow = relRsi.createRow();
//            newRow.setAttribute("operationFlag", "C");
//            newRow.setNewRowState(Row.STATUS_INITIALIZED);
//            
//            if(firstRow == null) {
//                relRsi.insertRow(newRow); 
//                relRsi.setCurrentRow(newRow);
//            }else {
//                relRsi.insertRowAtRangeIndex(lastRowIndex +1, newRow); 
//                relRsi.setCurrentRow(newRow);
//            }
//            
//          
//            
//                DCIteratorBinding iterPropsRel = (DCIteratorBinding) bindings.findIteratorBinding("relationshipsIterator1");
//                iterPropsRel.executeQuery();
//                    
//                    //        Row r1 = iterPropsRel.get();
//                    
//                    Row[] r1 = iterPropsRel.getAllRowsInRange();
//                    
//                    for(int i=0;i<r1.length;i++) {
//                        Double doublePTval = (Double)r1[i].getAttribute("percentageTransfer");
//                    }
            
            //obtain the index of the last row
            //create a new row
            relRsi.reset();
            Row newRow = relRsi.createRow();
            newRow.setAttribute("operationFlag", "C");
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            relRsi.insertRowAtRangeIndex(lastRowIndex +1, newRow); 
            relRsi.setCurrentRow(newRow);
                    
            
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        return null;
    }

    public String deleteRelationship(ActionEvent actionEvent) {
        // Add event code here...
        Integer selectedIndex = (Integer) actionEvent.getComponent()
                                                     .getAttributes()
                                                     .get("selectedIndex");

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("deleteRelationShip");
//        opBind.getParamsMap().put("selectedIndex", selectedIndex);
//        opBind.execute();
        
        
        
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("relationshipsIterator");
       // iter0.executeQuery();
//       iter0.removeRowWithKey(arg0);
        
        
        Row r =  iter0.getRowAtRangeIndex(selectedIndex);
     
        r.remove();
            
//       RowSetIterator relRsi = (RowSetIterator) iter0.getRowSetIterator();
//         if(relRsi!= null) {
//             Row createRow = relRsi.createRow();
//             createRow.setAttribute("operationFlag", "C");
//             
//             Long rowCount = (Long)iter0.getEstimatedRowCount();
//             int rowindex = Integer.valueOf(rowCount.intValue());
//             relRsi.insertRowAtRangeIndex(rowindex, createRow);
//         }
//        
        
//        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
//        iter.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);

        return null;
    }


    public void setRelationShipListView(RichListView relationShipListView) {
        this.relationShipListView = relationShipListView;
    }

    public RichListView getRelationShipListView() {
        return relationShipListView;
    }

    public void TransferorVLC(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...



    }

    public void onTransferorValueChange(ValueChangeEvent valueChangeEvent) {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransferorPojoInfoIterator");
        //        iter0.executeQuery();

        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());


        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameIterator");
        iter0.executeQuery();
        OperationBinding opBind1 = (OperationBinding) bindings.getOperationBinding("loadTransferorNameList");
        opBind1.execute();
        DCIteratorBinding iter3 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameListIterator");
        iter3.executeQuery();

        DCIteratorBinding iter4 = (DCIteratorBinding) bindings.findIteratorBinding("transferorNameListIterator1");
        iter4.executeQuery();

//        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorAddQuesGrid);


    }


    public void onTransfereeValueChange(ValueChangeEvent valueChangeEvent) {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("propsTransferorPojoInfoIterator");
        //        iter0.executeQuery();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        OperationBinding opBind1 = (OperationBinding) bindings.getOperationBinding("loadTransfereeNameList");
        opBind1.execute();
        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameListIterator");
        iter1.executeQuery();

        DCIteratorBinding iter4 = (DCIteratorBinding) bindings.findIteratorBinding("transfereeNameListIterator1");
        iter4.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeAddQuesGrid);

    }

    public void validateEmail(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        // Add event code here...
        if (!object.toString().equalsIgnoreCase(null)) {
            if (!object.toString().equalsIgnoreCase("")) {
                String email_address = object.toString();
                String email_pattern =
                    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                Pattern patn = Pattern.compile(email_pattern);
                Matcher matcher = patn.matcher(email_address);

                String Error_Message = "You have entered an invalid email address. Please try again.";

                if (!matcher.matches()) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, Error_Message, null));

                }
            }
        }
    }


    public String saveProps(ActionEvent actionEvent) {
        onSave();

        return null;
    }

    /**
     * @return
     */
    public String onSave() {
        
 DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("upsertProps");
        String wuid = (String) ADFUtils.getPageFlowValue("wuid");
        String propsid = (String) ADFUtils.getPageFlowValue("propsid");
        System.out.println("propsid---"+propsid);
        opBind.getParamsMap().put("p0", Integer.valueOf(wuid));
        opBind.getParamsMap().put("p1", Integer.valueOf(propsid));
        String opStatus = (String) opBind.execute();
        
        
        DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
        iter3.executeQuery();
        

        DCIteratorBinding transferorMailingAddrIter = bindings.findIteratorBinding("mailingAddressIterator1");
        if (transferorMailingAddrIter != null) {
         //   transferorMailingAddrIter.executeQuery();
            Row row = transferorMailingAddrIter.getCurrentRow();
            if (row != null) {
                
            }
        }

        DCIteratorBinding transfereeMailingAddrIter = bindings.findIteratorBinding("mailingAddressIterator");

        if (transfereeMailingAddrIter != null) {
         //   transfereeMailingAddrIter.executeQuery();
            Row row1 = transfereeMailingAddrIter.getCurrentRow();
            if (row1 != null) {
               
            }
        }

        DCIteratorBinding iter0 = bindings.findIteratorBinding("claimInfoIterator");
        // iter0.executeQuery();
        Row rw = iter0.getCurrentRow();
        String pr = (String) rw.getAttribute("principalResidence");

        

       
        
      
        
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(relationShipListView);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(transferorListView);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transfereeListView);
        String wuidMessage = "Claim Info Data Saved Successfully";
        addFacesMessage(FacesMessage.SEVERITY_INFO, wuidMessage);

       


        return null;
    }

    protected void addFacesMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }


    public void setPrevTransferListView(RichListView prevTransferListView) {
        this.prevTransferListView = prevTransferListView;
    }

    public RichListView getPrevTransferListView() {
        return prevTransferListView;
    }

    public void setTransferorAddQuesGrid(RichPanelGridLayout transferorAddQuesGrid) {
        this.transferorAddQuesGrid = transferorAddQuesGrid;
    }

    public RichPanelGridLayout getTransferorAddQuesGrid() {
        return transferorAddQuesGrid;
    }

    public void setTransfereeAddQuesGrid(RichPanelGridLayout transfereeAddQuesGrid) {
        this.transfereeAddQuesGrid = transfereeAddQuesGrid;
    }

    public RichPanelGridLayout getTransfereeAddQuesGrid() {
        return transfereeAddQuesGrid;
    }

    public void correspondenceVLC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public String testMethod() {
        // Add event code here...
        DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding iter0 = (DCIteratorBinding) bindings1.findIteratorBinding("propsPojoIterator");
        iter0.executeQuery();

        DCIteratorBinding iter3 = bindings1.findIteratorBinding("correspondenceIterator");
        iter3.executeQuery();
        Row rw = iter3.getCurrentRow();
        String investigatorName = (String) rw.getAttribute("investigatorName");


        return null;
    }

    public void setProp58Ques1(boolean prop58Ques1) {
        this.prop58Ques1 = prop58Ques1;
    }

    public boolean isProp58Ques1() {
        return prop58Ques1;
    }



    public void onProp58AttributeChangeQuest1(AttributeChangeEvent valueChangeEvent) {
        prop58Ques1 = true;
        
        String newvalueQ1= null;
        if(valueChangeEvent.getNewValue() != null) {
            newvalueQ1 = valueChangeEvent.getNewValue().toString();
        }
        
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("transfereeQuestionnaireIterator");
       if (iter != null) {
        iter.executeQuery();
        Row[] row = iter.getAllRowsInRange();
        
        if(row.length>1) {
            if("N".equalsIgnoreCase(newvalueQ1)){
            Row row1 = iter.getRowAtRangeIndex(1);
            row1.setAttribute("showData", "N");
            }
        }
            for(int i=0;i<row.length;i++) {
                Row row0 = iter.getRowAtRangeIndex(1);
                if (row0 != null) {
                    
                }
            }
           
        }

    }
    
    public void onProp58Quest1(ValueChangeEvent valueChangeEvent) {
        prop58Ques1 = true;
        
      
        String newvalueQ1= null;
        if(valueChangeEvent.getNewValue() != null) {
            newvalueQ1 = valueChangeEvent.getNewValue().toString();
        }
       
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("transfereeQuestionnaireIterator");
       if (iter != null) {
       // iter.executeQuery();
        Row[] row = iter.getAllRowsInRange();

            //  This question should be populated only when 'No' is selected in the above field.
            if("PrntStepPrntMarriage".equalsIgnoreCase((String)iter.getRowAtRangeIndex(0).getAttribute("question")) &&  ("N".equalsIgnoreCase((String)iter.getRowAtRangeIndex(0).getAttribute("answer")))){
                iter.getRowAtRangeIndex(1).setAttribute("showData", "Y");
            }else {
                iter.getRowAtRangeIndex(1).setAttribute("showData", "N");
            }
            // This question should be populated only when 'Death' is selected in the above field.
            
            
            if(("PrntStepPrntMarriage".equalsIgnoreCase((String)iter.getRowAtRangeIndex(0).getAttribute("question")) &&  ("N".equalsIgnoreCase((String)iter.getRowAtRangeIndex(0).getAttribute("answer")))) && "PrntStepPrntMarriageTermRsn".equalsIgnoreCase((String)iter.getRowAtRangeIndex(1).getAttribute("question")) &&  ("Y".equalsIgnoreCase((String)iter.getRowAtRangeIndex(1).getAttribute("answer")))){
            
                iter.getRowAtRangeIndex(2).setAttribute("showData", "Y");
            }else {
                iter.getRowAtRangeIndex(2).setAttribute("showData", "N");
            }
            
            
            
            // NEXt Set 
            
            
            //  This question should be populated only when 'No' is selected in the above field.
            if("InlawMarriage".equalsIgnoreCase((String)iter.getRowAtRangeIndex(3).getAttribute("question")) &&  ("N".equalsIgnoreCase((String)iter.getRowAtRangeIndex(3).getAttribute("answer")))){
                iter.getRowAtRangeIndex(4).setAttribute("showData", "Y");
            }else {
                iter.getRowAtRangeIndex(4).setAttribute("showData", "N");
            }
            // This question should be populated only when 'Death' is selected in the above field.
            
            
            if(("InlawMarriage".equalsIgnoreCase((String)iter.getRowAtRangeIndex(3).getAttribute("question")) &&  ("N".equalsIgnoreCase((String)iter.getRowAtRangeIndex(3).getAttribute("answer")))) && "InlawMarriageTermRsn".equalsIgnoreCase((String)iter.getRowAtRangeIndex(4).getAttribute("question")) &&  ("Y".equalsIgnoreCase((String)iter.getRowAtRangeIndex(4).getAttribute("answer")))){
            
                iter.getRowAtRangeIndex(5).setAttribute("showData", "Y");
            }else {
                iter.getRowAtRangeIndex(5).setAttribute("showData", "N");
            }
            
            
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            
//            for(int i=0;i<row.length;i++) {
//                Row row0 = iter.getRowAtRangeIndex(i);
//                if (row0 != null) {
//                    
//                    
//                }
//            }
        }

    }
    
    
   

    public void setProp58QAns1(RichSelectOneRadio prop58QAns1) {
        this.prop58QAns1 = prop58QAns1;
    }

    public RichSelectOneRadio getProp58QAns1() {
        return prop58QAns1;
    }

    public void setShowPropQuest2(boolean showPropQuest2) {
        this.showPropQuest2 = showPropQuest2;
    }

    public boolean isShowPropQuest2() {
        return showPropQuest2;
    }
    
    public String postComment() {        
        logger.info("PropClaimPerformBean : postComment");
        String comment = JSFUtils.resolveExpressionAsString("#{commentBean.value}");
        if(comment == null || comment.trim().isEmpty()) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("Empty Comment", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter comment", "Please enter comment"));
            return null;
        } else {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            bindings.getOperationBinding("CreateInsert2").execute();
        }
          JSFUtils.resloveMethodExpression("#{commentBean.addComment}", Object.class, new Class[]{}, new Object[]{});
          AdfFacesContext.getCurrentInstance().getPageFlowScope().put("lstbpmaction", "addcomment");
//          DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//          oracle.binding.OperationBinding op = bindings.getOperationBinding("update");          
//          op.execute();    
//          
//          //AdfFacesContext.getCurrentInstance().addPartialTarget(commentsPgl);
//          if(!op.getErrors().isEmpty())
//             logger.error("error saving comments.. "+op.getErrors().toString());
//         // bindings.getOperationBinding("getTaskDetails").execute();         
//          return "refreshTaskFlow";
//          return "taskFlowComplete";
          return null;
      }
    
    public void reassign() {        
        logger.info("PropClaimPerformBean : Reassign");
        
    //      updateBPMTask();
          JSFUtils.resloveMethodExpression("#{invokeActionBean.action}", Object.class, new Class[]{}, new Object[]{});          
          DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    //          oracle.binding.OperationBinding op = bindings.getOperationBinding("update");
    //          op.execute();
          
    //          AdfFacesContext.getCurrentInstance().addPartialTarget(commentsPgl);
    //          if(!op.getErrors().isEmpty())
    //             logger.error("error saving comments.. "+op.getErrors().toString());
         // bindings.getOperationBinding("getTaskDetails").execute();         
          //return "refreshTaskFlow";
    //          return "taskFlowComplete";
          
    closeTaskFlow();
      }

    public void setAssessorParcelNo1(RichInputText assessorParcelNo1) {
        this.assessorParcelNo1 = assessorParcelNo1;
    }

    public RichInputText getAssessorParcelNo1() {
        return assessorParcelNo1;
    }

    public void setAssessorParcelNo2(RichInputText assessorParcelNo2) {
        this.assessorParcelNo2 = assessorParcelNo2;
    }

    public RichInputText getAssessorParcelNo2() {
        return assessorParcelNo2;
    }
    
    
    public void countyVLC(ValueChangeEvent valueChangeEvent) {
    
    
        assessorParcelNo1.setValue(null);
        assessorParcelNo2.setValue(null);
    AdfFacesContext.getCurrentInstance().addPartialTarget(assessorParcelNo1);
    AdfFacesContext.getCurrentInstance().addPartialTarget(assessorParcelNo2);
    
    }
    
    
    public void tfeAddQuuesCountyVLC(ValueChangeEvent valueChangeEvent) {
    
    
        assessorParcelNo3.setValue(null);
        assessorParcelNo4.setValue(null);
    AdfFacesContext.getCurrentInstance().addPartialTarget(assessorParcelNo3);
    AdfFacesContext.getCurrentInstance().addPartialTarget(assessorParcelNo4);
    
    }
    

    public void setCommentsPgl(RichPanelGroupLayout commentsPgl) {
        this.commentsPgl = commentsPgl;
    }

    public RichPanelGroupLayout getCommentsPgl() {
        return commentsPgl;
    }

    public void onUnload(ClientEvent clientEvent) {
        // Add event code here...
        
        try {
            String action = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("lstbpmaction");
            logger.error("onUnload..action.."+action);
            if("addcomment".equalsIgnoreCase(action))
            updateBPMTask();
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("onUnload", e);
        }
       
      //  closeTaskFlow();

    }
    
    public String closeTaskFlow() {
        
        return "closeTaskFlow";
    }

    private void updateBPMTask() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        oracle.binding.OperationBinding op = bindings.getOperationBinding("update");
        op.execute();
        
    }

    public void setAssessorParcelNo3(RichInputText assessorParcelNo3) {
        this.assessorParcelNo3 = assessorParcelNo3;
    }

    public RichInputText getAssessorParcelNo3() {
        return assessorParcelNo3;
    }

    public void setAssessorParcelNo4(RichInputText assessorParcelNo4) {
        this.assessorParcelNo4 = assessorParcelNo4;
    }

    public RichInputText getAssessorParcelNo4() {
        return assessorParcelNo4;
    }
}
