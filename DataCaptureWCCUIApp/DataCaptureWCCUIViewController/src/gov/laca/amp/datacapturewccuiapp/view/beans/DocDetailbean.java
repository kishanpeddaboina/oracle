package gov.laca.amp.datacapturewccuiapp.view.beans;


import gov.laca.amp.datacapturewccuiapp.view.util.ADFUtils;
import gov.laca.amp.datacapturewccuiapp.view.util.JSFUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

public class DocDetailbean {
    
    private static ADFLogger _logger = ADFLogger.createADFLogger(DocDetailbean.class);
    private RichPopup canConfirmPopup;

    public DocDetailbean() {
    }

    public void onSave(ActionEvent actionEvent) {
        _logger.entering(DocDetailbean.class.getName(), "onSave");
        if(saveDocData()) {
            JSFUtils.addFacesInformationMessage("Data Saved Successfully.");
        }
        _logger.exiting(DocDetailbean.class.getName(), "onSave");
    }

    public String onSubmit() {
        _logger.entering(DocDetailbean.class.getName(), "onSubmit");
        ADFUtils.logAccess("Entering Submit");
        if(!saveDocData()) {
            _logger.exiting(DocDetailbean.class.getName(), "onSubmit");
            return null;
        }
//        DCBindingContainer bindings = (DCBindingContainer)(BindingContext.getCurrent().getCurrentBindingsEntry());
//        OperationBinding operationBinding =bindings.getOperationBinding("Submit");
//          operationBinding.execute();
//          if(!operationBinding.getErrors().isEmpty()) {
//              _logger.severe(DocDetailbean.class.getName(), "onSubmit","Submit error.."+operationBinding.getErrors().toString());
//              JSFUtils.addFacesErrorMessage("Error Submitting Task.");
//              _logger.exiting(DocDetailbean.class.getName(), "onSubmit");
//              return null;
//          }
        _logger.exiting(DocDetailbean.class.getName(), "onSubmit");
        ADFUtils.logAccess("Calling Parent Submit");
        return "onSubmit";
    }
    
    public boolean saveDocData() {
        _logger.entering(DocDetailbean.class.getName(), "saveDocData");
        ADFUtils.logAccess("Entering save");
        DCBindingContainer bindings = (DCBindingContainer)(BindingContext.getCurrent().getCurrentBindingsEntry());
                OperationBinding operationBinding =bindings.getOperationBinding("updateWCCDocData");
                  operationBinding.execute();
                  if(!operationBinding.getErrors().isEmpty()) {
                      _logger.severe(DocDetailbean.class.getName(), "saveDocData","updateWCCDocData error.."+operationBinding.getErrors().toString());
                      JSFUtils.addFacesErrorMessage("Error Saving Data.");
                      _logger.exiting(DocDetailbean.class.getName(), "saveDocData");
                      return false;
                  }
        _logger.exiting(DocDetailbean.class.getName(), "saveDocData");    
        ADFUtils.logAccess("Exiting save successfull");
        return true;
    }
    
    public String populateAttrModel() {
        _logger.entering(DocDetailbean.class.getName(), "populateAttrModel");
        ADFUtils.logAccess("Entering subtask init.. populateAttrModel");
        DCBindingContainer bindings = (DCBindingContainer)(BindingContext.getCurrent().getCurrentBindingsEntry());
        OperationBinding operationBinding =bindings.getOperationBinding("populateAttributeModel");
        operationBinding.execute();
        if(!operationBinding.getErrors().isEmpty()) {
            _logger.severe(DocDetailbean.class.getName(), "populateAttrModel","populateAttributeModel error.."+operationBinding.getErrors().toString());
            _logger.exiting(DocDetailbean.class.getName(), "populateAttrModel");
             return "error";
        }
        
        operationBinding =bindings.getOperationBinding("fetchOptions");
         Map<String, LinkedHashMap<String,String>> optionsMap = (Map<String, LinkedHashMap<String, String>>) operationBinding.execute();
        Map pfMap =  AdfFacesContext.getCurrentInstance().getPageFlowScope();
         for(String vmNm : optionsMap.keySet()) {
             List<SelectItem> selItems = new ArrayList<SelectItem>();
             for(String key : optionsMap.get(vmNm).keySet()) {
                 selItems.add(new SelectItem(key,optionsMap.get(vmNm).get(key)));
             }
             pfMap.put(vmNm, selItems);
         }
        
        _logger.exiting(DocDetailbean.class.getName(), "populateAttrModel");
        return "continue";
    }

    public void setCanConfirmPopup(RichPopup canConfirmPopup) {
        this.canConfirmPopup = canConfirmPopup;
    }

    public RichPopup getCanConfirmPopup() {
        return canConfirmPopup;
    }

    public void onCancel(ActionEvent actionEvent) {
        getCanConfirmPopup().hide();
    }
}
