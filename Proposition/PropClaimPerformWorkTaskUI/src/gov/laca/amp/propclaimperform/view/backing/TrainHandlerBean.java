package gov.laca.amp.propclaimperform.view.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;

import gov.laca.amp.fwk.util.ADFUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import oracle.adf.controller.TaskFlowTrainStopModel;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;

import oracle.jbo.Row;

public class TrainHandlerBean extends AmpManagedBean{
    @SuppressWarnings("compatibility:-4088140705765372235")
    private static final long serialVersionUID = -8548679646529273631L;

    /**
     * Managed bean property reference to a helper bean in view scope
     */
    private TrainHandlerBeanHelper trainHandlerBeanHelper = null;
    private String nodeOutcome = null;
    

    public TrainHandlerBean() {
        super();
    }
    

    
        
    public void onClaimTrainStopNext(ActionEvent actionEvent) {
        //Call Upsert
        
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("loadAvailableDocs");
        String opStatus = (String) opBind.execute();
        DCIteratorBinding iter = bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();
        
        RichCommandNavigationItem rni = (RichCommandNavigationItem)actionEvent.getSource();
        TaskFlowTrainStopModel selectedTrainStop = (TaskFlowTrainStopModel)rni.getAttributes().get("trainStopNode");
        String outcome = selectedTrainStop.getOutcome();
        System.out.println("Outcome value"+outcome);
        setNodeOutcome(outcome);
    }
    
    
    public void onSuppTrainStopNext(ActionEvent actionEvent) {
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
        System.out.println("checkOpa result::"+checkOpa);
        if (("callOpa").equals(checkOpa)) {
            ADFUtils.setPageFlowValue("CALLOPA", "YES");
            onInvokeOPA();
        } else {
            ADFUtils.setPageFlowValue("CALLOPA", "NO");
            onGetOtherPropFilings();
        }
        RichCommandNavigationItem rni = (RichCommandNavigationItem)actionEvent.getSource();
        TaskFlowTrainStopModel selectedTrainStop = (TaskFlowTrainStopModel)rni.getAttributes().get("trainStopNode");
        String outcome = selectedTrainStop.getOutcome();
        System.out.println("Outcome value"+outcome);
        setNodeOutcome(outcome);
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
    
    /**
     * @return
     */
    public void onGetOtherPropFilings() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("getOtherPropFilings");
        //        /(Integer wuId, String propsType)
        Integer propsId = Integer.parseInt((String) ADFUtils.getPageFlowValue("propsid"));

        opBind.getParamsMap().put("p0", propsId);
        opBind.execute();
        DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
        iter3.executeQuery();
        //Remove this - adding this for debug
    }
    
    /**
     * @return
     */
    public void onInvokeOPA() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        Integer wuId = null;
        //This should work from the BPM UI Task.
        AttributeBinding claimType = (AttributeBinding) bindings.getControlBinding("claimType");
        wuId = Integer.parseInt((String) ADFUtils.getPageFlowValue("wuid"));
        String propsType = (String) ADFUtils.getPageFlowValue("propsType");
        System.out.println("onInvokeOPA : WUID in Bean " + wuId);
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
                    System.out.println(" Invoke OPA row  claim result--->" + row.getAttribute("claimResult"));
                    String claimResult = (String) row.getAttribute("claimResult");
                    ADFUtils.setPageFlowValue("claimResult", claimResult);
                }
            }
        }
    }

    
    public void setTrainHandlerBeanHelper(TrainHandlerBeanHelper trainHandlerBeanHelper) {
        this.trainHandlerBeanHelper = trainHandlerBeanHelper;
    }

    public TrainHandlerBeanHelper getTrainHandlerBeanHelper() {
        return trainHandlerBeanHelper;
    }


    public void setNodeOutcome(String nodeOutcome) {
        this.nodeOutcome = nodeOutcome;
    }

    public String getNodeOutcome() {
        return nodeOutcome;
    }

    public String getOutcomeAction() {
        // Add event code here...
        return nodeOutcome;
    }
}
