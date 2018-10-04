package propvalperformworktaskui;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowTrainStopModel;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
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
  
    

    public TrainHandlerBean() {
        super();
    }

    public String saveAction() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding workUnitId = (AttributeBinding) bindings.getControlBinding("WorkUnitId");

        AttributeBinding isValueChangeReq = (AttributeBinding) bindings.getControlBinding("isValueChangedRequired");
        
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("updatePropsPayload");

        Boolean status = (Boolean) op.execute();
        //Adding for testing
        
       System.out.println("Valuation Save Success::");
        return null;
    }
    

    public void onReviewInvestigator(ActionEvent actionEvent) {
        //Call Upsert
        
        System.out.println("HIIII--");
        
        
        saveAction();
        
        
        
        RichCommandNavigationItem rni = (RichCommandNavigationItem)actionEvent.getSource();
        TaskFlowTrainStopModel selectedTrainStop = (TaskFlowTrainStopModel)rni.getAttributes().get("trainStopNode");
        String outcome = selectedTrainStop.getOutcome();
        System.out.println("Outcome value"+outcome);
        setNodeOutcome(outcome);
       
    }
    
    public String getOutcomeAction() {
        // Add event code here...
        return nodeOutcome;
    }
    
}
