package propclaimapproveworktaskui.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.OperationBinding;
import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;

public class PropClaimApproveBean extends AmpManagedBean {
   

    public PropClaimApproveBean() {
    }
    ADFLogger _log = ADFLogger.createADFLogger(PropClaimApproveBean.class);

    public void loadData() {
        // Add event code here...
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter0 = (DCIteratorBinding) bindings.findIteratorBinding("AMPWorkTaskIterator");
        iter0.executeQuery();
        String wuid = null;
        String propsid = null;
        Row row = iter0.getCurrentRow();
        if(row.getAttribute("WorkUnitId") != null){
            wuid = row.getAttribute("WorkUnitId").toString();
        }
        ADFUtils.setPageFlowValue("wuid", wuid);
    }
    
    public String  postComment() {
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
//         if(!op.getErrors().isEmpty())
//              _log.severe("error saving comments.. "+op.getErrors().toString());
////         // bindings.getOperationBinding("getTaskDetails").execute();         
//          return "refreshTaskFlow";
          return null;
      }
    
    
    public void onUnload(ClientEvent clientEvent) {
        // Add event code here...
        
        try {
            String action = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("lstbpmaction");
            _log.severe("onUnload..action.."+action);
            if("addcomment".equalsIgnoreCase(action))
              updateBPMTask();
        } catch(Exception e) {
            e.printStackTrace();
            _log.severe(e);
        }
       
     //   closeTaskFlow();

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
