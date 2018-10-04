package gov.laca.amp.datacapturewccuiapp.view;



import gov.laca.amp.datacapturewccuiapp.view.util.ADFUtils;
import gov.laca.amp.datacapturewccuiapp.view.util.JSFUtils;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;


public class TaskDetailsBean {
    
    private static ADFLogger _logger = ADFLogger.createADFLogger(TaskDetailsBean.class);
    
    
    private RichInputText dsf;

    public TaskDetailsBean() {
    }

    public void setDsf(RichInputText dsf) {
        this.dsf = dsf;
    }

    public RichInputText getDsf() {
        return dsf;
    }
    
    public String payLoadDtl(){
        _logger.entering(TaskDetailsBean.class.getName(), "payLoadDtl");
        ADFUtils.logAccess("Entering task init.. payLoadDtl");
        DCBindingContainer bindings = (DCBindingContainer)(BindingContext.getCurrent().getCurrentBindingsEntry());
//        OperationBinding operationBinding =bindings.getOperationBinding("getTaskDetails");
//          operationBinding.execute();
     
      
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("documentId", ((AttributeBinding)bindings.getControlBinding("DocumentId")).getInputValue());
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("category", ((AttributeBinding)bindings.getControlBinding("Category")).getInputValue());
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("subCategory", ((AttributeBinding)bindings.getControlBinding("Subcategory")).getInputValue());
        String isValidationReq = (String) ((AttributeBinding) bindings.getControlBinding("IsValidationRequired")).getInputValue();
        if("true".equalsIgnoreCase(isValidationReq)) {
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("isValidationReq", Boolean.TRUE);//
        } 
        
       
        FacesCtrlListBinding listBinding =
            (FacesCtrlListBinding) JSFUtils.resolveExpression("#{bindings.customActions}");
        Boolean isSubmitEnable = Boolean.FALSE;
        Row[] rows = listBinding.getAllRowsInRange();
        if (rows != null) {
            for (int i = 0; i < rows.length; i++)
                {
                  String rowAction = (String)listBinding.getAttributeFromRow(rows[i], 0);
               
                  if ("Submit".equalsIgnoreCase(rowAction)) {
                    isSubmitEnable = Boolean.TRUE;
                    break;
                  }
                }
        }
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("isSubmitVis", isSubmitEnable);
        
       
        DCIteratorBinding iter = bindings.findIteratorBinding("PropertyIdentificationIterator");
        StringBuffer ainListBuf = new StringBuffer();
        for(int i = 0; i < iter.getEstimatedRowCount(); i++) {
            Row r = iter.getRowAtRangeIndex(i);
            
            if(Boolean.TRUE.equals((Boolean)r.getAttribute("IsPrimary"))) {
                
                if(ainListBuf.length() == 0) {
                    ainListBuf.append((String)r.getAttribute("AIN"));
                } else {
                    ainListBuf.append(","+(String)r.getAttribute("AIN"));
                }
            }
        }
       
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("ainList",ainListBuf.toString());
       
        _logger.exiting(TaskDetailsBean.class.getName(), "payLoadDtl");
        return null;
    }
    
    
    
    public String onSubmit(){
        _logger.entering(TaskDetailsBean.class.getName(), "onSubmit");
        ADFUtils.logAccess("Entering submit init.. onSubmit");
        DCBindingContainer bindings = (DCBindingContainer)(BindingContext.getCurrent().getCurrentBindingsEntry());

      
        Object docIdVal = ((AttributeBinding)bindings.getControlBinding("DocumentId")).getInputValue();
        ((AttributeBinding)bindings.getControlBinding("WCCId")).setInputValue(docIdVal);
        OperationBinding operationBinding =bindings.getOperationBinding("Submit");
          operationBinding.execute();
          if(!operationBinding.getErrors().isEmpty()) {
              _logger.severe(TaskDetailsBean.class.getName(), "onSubmit","Submit error.."+operationBinding.getErrors().toString());
              JSFUtils.addFacesErrorMessage("Error Submitting Task.");
              _logger.exiting(TaskDetailsBean.class.getName(), "onSubmit");
              return null;
          }
        ADFUtils.logAccess("Exiting onSubmit.. calling close");
        _logger.exiting(TaskDetailsBean.class.getName(), "onSubmit");
        return "closeTaskFlow";
    }
}
