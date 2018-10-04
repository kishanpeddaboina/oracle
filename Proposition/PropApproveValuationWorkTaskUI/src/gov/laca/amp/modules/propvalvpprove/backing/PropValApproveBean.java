package gov.laca.amp.modules.propvalvpprove.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;

import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;


public class PropValApproveBean extends AmpManagedBean {
    public PropValApproveBean() {
        
    }


    /** This method is called on opening the Valuation Approver task
     * @return
     */
    public String loadPropositions() {
        System.out.println("inside loadPropositions ::");
        logger.info(PropValApproveBean.class, "PropValApproveBean()", "Start loadPropositions", null);
        Integer pgWUId = null;
        //This should work from the BPM UI Task.
        pgWUId = (Integer) JSFUtils.resolveExpression("#{bindings.WorkUnitId.inputValue}");
       
        //This is for testing on local
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("workUnitId")!= null) {         
            pgWUId = Integer.parseInt(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("workUnitId").toString());
       }   
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("PropertyIdentificationIterator");
        iter1.executeQuery();
        Row row1 = iter1.getCurrentRow();

        String ain = (String) row1.getAttribute("AIN");
        
        System.out.println("ain -----"+ain);
        ADFUtils.setPageFlowValue("ain", ain);
        logger.info(PropValApproveBean.class, "PropValApproveBean()", "End loadPropositions", null);
        return "loadProp";
    }
    
    public String postComment() {
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
//          OperationBinding op = bindings.getOperationBinding("update");          
//          op.execute();         
//          if(!op.getErrors().isEmpty())
//             logger.error("error saving comments.. "+op.getErrors().toString());
//         // bindings.getOperationBinding("getTaskDetails").execute();         
//          return "refreshTaskFlow";
          return null;
   }
    
    public void onUnload(ClientEvent clientEvent) {
        // Add event code here...
        
        try {
            String action = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("lstbpmaction");
            logger.error("PropValApproveBean : onUnload..action.."+action);
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
    
}
