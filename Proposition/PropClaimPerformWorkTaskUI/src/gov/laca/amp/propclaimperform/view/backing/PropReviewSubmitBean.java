package gov.laca.amp.propclaimperform.view.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;

import gov.laca.amp.fwk.security.SecurityHelper;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.StringUtils;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelAccordion;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;

public class PropReviewSubmitBean extends AmpManagedBean {
    private RichPanelGroupLayout commentspgl;

    public PropReviewSubmitBean() {
        super();
    }
    private RichPanelAccordion reviewPanelAccordion;
    public Boolean expandAll = true;

    public void setExpandAll(Boolean expandAll) {
        this.expandAll = expandAll;
    }

    public Boolean getExpandAll() {
        return expandAll;
    }

    public String collapseAll() {
        // Add event code here...
        oracle.adf
              .view
              .rich
              .util
              .ResetUtils
              .reset(reviewPanelAccordion);
        for (int i = 0; i < reviewPanelAccordion.getChildren().size(); i++) {
            RichShowDetailItem showDetailItem = (RichShowDetailItem) reviewPanelAccordion.getChildren().get(i);
            showDetailItem.setDisclosed(false);
           // reviewPanelAccordion.getChildren().add(i, showDetailItem);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(reviewPanelAccordion);

        setExpandAll(false);
        return null;
    }

    public String expandAll() {
        // Add event code here...
        oracle.adf
              .view
              .rich
              .util
              .ResetUtils
              .reset(reviewPanelAccordion);
        for (int i = 0; i < reviewPanelAccordion.getChildren().size(); i++) {
            RichShowDetailItem showDetailItem = (RichShowDetailItem) reviewPanelAccordion.getChildren().get(i);
            showDetailItem.setDisclosed(true);
           // reviewPanelAccordion.getChildren().add(i, showDetailItem);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviewPanelAccordion);
        setExpandAll(true);
        return null;
    }

    public void setReviewPanelAccordion(RichPanelAccordion reviewPanelAccordion) {
        this.reviewPanelAccordion = reviewPanelAccordion;
    }

    public RichPanelAccordion getReviewPanelAccordion() {
        return reviewPanelAccordion;
    }


    public String saveAction() {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        // DCIteratorBinding itorBinding = bindings.findIteratorBinding("AMPWorkTaskIterator");
        // AttributeBinding workUnitId = (AttributeBinding) bindings.getControlBinding("WorkUnitId");
        Integer workUnitId = null;
        Integer propsId = null;

        workUnitId = Integer.parseInt((String) ADFUtils.getPageFlowValue("wuid"));
        propsId = Integer.parseInt((String) ADFUtils.getPageFlowValue("propsid"));

        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("upsertProps"); //Uncomment this
        op1.getParamsMap().put("p0", workUnitId);
        op1.getParamsMap().put("p1", propsId);
        //   OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("testUpsertProps"); //Uncomment this

        String opStatus = (String) op1.execute(); //Uncomment this
        System.out.println("PropReviewSubmitBean : opStatus : " + opStatus); //Uncomment this
        


        return null;
    }
    
    /**
     * @return
     */
    public String onSubmitForApproval() {

        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        // DCIteratorBinding itorBinding = bindings.findIteratorBinding("AMPWorkTaskIterator");
        // AttributeBinding workUnitId = (AttributeBinding) bindings.getControlBinding("WorkUnitId");
        Integer workUnitId = null;
        Integer propsId = null;

        workUnitId = Integer.parseInt((String) ADFUtils.getPageFlowValue("wuid"));
        propsId = Integer.parseInt((String) ADFUtils.getPageFlowValue("propsid"));

        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("upsertProps"); //Uncomment this
        op1.getParamsMap().put("p0", workUnitId);
        op1.getParamsMap().put("p1", propsId);
        //   OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("testUpsertProps"); //Uncomment this

        String opStatus = (String) op1.execute(); //Uncomment this
        System.out.println("PropReviewSubmitBean : opStatus : " + opStatus); //Uncomment this
        

        if (opStatus != null && opStatus.equalsIgnoreCase("SUCCESS")) {
            updatePayloadParameters();
            return "closeTaskFlow";
        }

        return null;
    }
    
    
//    private void updateBPMTask() {
//        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        oracle.binding.OperationBinding op = bindings.getOperationBinding("update");
//        op.execute();
//        System.out.println("AFter Update:::");
//        
//    }

    /**This method adds the Event params in the payload when valuation flag is set.
     * @return
     */
    public String updatePayloadParameters() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding paramIter = bindings.findIteratorBinding("ParameterIterator");
        paramIter.executeQuery();

        AttributeBinding isValuationReqdAttr = (AttributeBinding) bindings.getControlBinding("valuationFlag");

        String isValuationReqdFromPage = (String) isValuationReqdAttr.getInputValue(); //Uncomment ths
       // System.out.println("updatePayloadParameters : isValuationReqdFromPage-> " + isValuationReqdAttr.getInputValue());
       // Boolean isValuationReqdFromPage = Boolean.TRUE;
        //  System.out.println("addEventParameters : isValuationReqdFromPage->" + isValuationReqdFromPage);

        Boolean isValuationReqdParam =
            Boolean.FALSE; //Check if this IsValuationRequired element is already present
   if(paramIter == null)
   {
       System.out.println("Could be testing on local : paramIter is null");
        return null;
   }
        Row rows[] = paramIter.getAllRowsInRange();
        for (Row row : rows) {
            String name = (String) row.getAttribute("Name");
            if ("IsValuationRequired".equals(name)) {
                isValuationReqdParam = Boolean.TRUE;
            }
        }
        if (!isValuationReqdParam) {
            System.out.println("isValueChangeReq not exists. so creating a new one");
            if ((isValuationReqdFromPage != null) && (("YES").equalsIgnoreCase(isValuationReqdFromPage) || ("Y").equalsIgnoreCase(isValuationReqdFromPage))) {
                OperationBinding op = (OperationBinding) bindings.getOperationBinding("CreateInsert");
                op.execute();
                Row rw1 = paramIter.getCurrentRow();
                rw1.setAttribute("Name", "IsValuationRequired");
                rw1.setAttribute("Value", "Yes");
                System.out.println("After setting IsValuationRequired to YES");
                paramIter.executeQuery();
            } else {
                System.out.println("Settign IsValuationRequired to NO");
                OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("CreateInsert");
                op1.execute();
                Row rw2 = paramIter.getCurrentRow();
                rw2.setAttribute("Name", "IsValuationRequired");
                rw2.setAttribute("Value", "No");
                System.out.println("After setting IsValuationRequired to No");
                paramIter.executeQuery();
            }
        } else {
            System.out.println("Element already exists-- Updating now");
            Row rows1[] = paramIter.getAllRowsInRange();
            for (Row row : rows1) {
                String name = (String) row.getAttribute("Name");
                if ("IsValuationRequired".equals(name)) {
                    if ((isValuationReqdFromPage != null) && (("YES").equalsIgnoreCase(isValuationReqdFromPage) || ("Y").equalsIgnoreCase(isValuationReqdFromPage))) {
                        row.setAttribute("Value", "Yes");
                    }
                    else
                        row.setAttribute("Value", "No");
                }
            }
            OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("update");
            op1.execute();
        }
        //displaying all parameters
        paramIter.executeQuery();
        System.out.println("Rexecuting to get the updated parameters");
        Row rows1[] = paramIter.getAllRowsInRange();
        for (Row row : rows1) {
            String name = (String) row.getAttribute("Name");
            String value = (String) row.getAttribute("Value");
            System.out.println("Debug : Params and its values : name " + name);
            System.out.println("Debug : Params and its values : value " + value);
            //  System.out.println("1132----name:" + name + "----Value:" + value);
        }

        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("SubmitForClaimApproval");
        op1.execute();
        return "closeTaskFlow";
    }

    public void setCommentspgl(RichPanelGroupLayout commentspgl) {
        this.commentspgl = commentspgl;
    }

    public RichPanelGroupLayout getCommentspgl() {
        return commentspgl;
    }
    
    
    public void onUnload(ClientEvent clientEvent) {
        // Add event code here...
        
        System.out.println("::INSIDE UPDATE::::");
        updateBPMTask();
        closeTaskFlow();

    }
    
    public String closeTaskFlow() {
        System.out.println("::closeTaskFlow::");
        
        return "closeTaskFlow";
    }

    private void updateBPMTask() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        oracle.binding.OperationBinding op = bindings.getOperationBinding("update");
        op.execute();
        System.out.println("AFter Update:::");
        
    }
    
    public boolean getEnableSubmitbtn() {
        
        boolean returnvalue = false;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding paramIter = bindings.findIteratorBinding("assigneesIterator");
        paramIter.executeQuery();
        
        Row rows1[] = paramIter.getAllRowsInRange();
        if(rows1 != null){
        for (Row row : rows1) {
            String id = (String) row.getAttribute("id");
           String[] userRoles= ADFContext.getCurrent().getSecurityContext().getUserRoles();

                for (String userrole : userRoles) {
                            if (userrole.equalsIgnoreCase(id)) {
                                returnvalue = true;
                                break;                
                            }
                        }
        }
        }

return returnvalue;
        
    }
    
    
}
