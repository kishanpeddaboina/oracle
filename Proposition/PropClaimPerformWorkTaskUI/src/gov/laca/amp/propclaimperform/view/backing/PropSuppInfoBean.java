package gov.laca.amp.propclaimperform.view.backing;


import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import java.util.Calendar;

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Key;
import oracle.jbo.Row;

import org.apache.myfaces.trinidad.model.RowKeySet;


public class PropSuppInfoBean {
    private RichInputText documentName;
    private RichPanelGroupLayout availableDocsGrp;
    private String docNameString;
    private RichOutputText requestedDateOutPut;
    private RichSelectBooleanCheckbox requestedCheckBox;
    private RichTable suppDocTable;

    public PropSuppInfoBean() {
        super();
    }


    /**
     * @return
     */
    public String initAvailableDocs() {
        System.out.println("INSIDE initAvailableDocs");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("loadAvailableDocs");
        String opStatus = (String) opBind.execute();
        DCIteratorBinding iter = bindings.findIteratorBinding("propsPojoIterator");
        iter.executeQuery();
        return "gotoSuppInfoUI";
    }


    public void setDocumentName(RichInputText documentName) {
        this.documentName = documentName;
    }

    public RichInputText getDocumentName() {
        return documentName;
    }

    /**
     * @return
     */
    public void addDocument(ActionEvent actionEvent) {
        String opStatus = null;
        String docName = null; // (String) ADFUtils.getBoundAttributeValue("docName");

        if (getDocumentName().getValue() != null) {

            docName = getDocumentName().getValue().toString();
        }
        else
        {
            addFacesMessage(FacesMessage.SEVERITY_INFO, "Please enter a valid name for the document");
            return;
        }
      

        OperationBinding opBind = null;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        if (bindings != null) {
            opBind = (OperationBinding) bindings.getOperationBinding("addNewDocument");
            if (opBind != null) {
                System.out.println("documentName ()" + docName);
                if (docName != null) {
                    opBind.getParamsMap().put("p0", docName);
                    opStatus = (String) opBind.execute();
                    System.out.println("opStatus after add New execute" + opStatus);
                }
            } else
                System.out.println("opBind is null!");
        } else
            System.out.println("bindings is null!");
        DCIteratorBinding iter = bindings.findIteratorBinding("availableDocumentIterator");
        if (iter != null) {
            iter.executeQuery();
            if (iter.getAllRowsInRange() != null)
                System.out.println("availableDocumentIterator : iter size after adding->" + iter.getAllRowsInRange().length);
        } else
            System.out.println("availableDocumentIterator is null");
        AdfFacesContext.getCurrentInstance().addPartialTarget(availableDocsGrp);
        closeAddDocListPopUp(actionEvent);      
    }

    /**
     * @param actionEvent
     */
    public void closeAddDocListPopUp(ActionEvent actionEvent) {

        //        this.setAauRemarks(null);
        //        this.setAauNamee(null);
        //        this.setRelationType(null);
        UIComponent comp = actionEvent.getComponent();
         oracle.adf.view.rich.util.ResetUtils.reset(comp);
        UIComponent tmpComponent;
        tmpComponent = actionEvent.getComponent().getParent();
        while (!(tmpComponent instanceof RichPopup)) {
            tmpComponent = tmpComponent.getParent();
        }
        RichPopup popup = (RichPopup) tmpComponent;
        popup.hide();
    }

    /**
     * @return
     */
    public String deleteDocument() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("availableDocumentIterator");
        iter.getCurrentRow().remove();
        iter.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(availableDocsGrp);
        return null;
    }

    /** @param valueChangeEvent
     */
    public void onChangeRequested(ValueChangeEvent valueChangeEvent) {
        
         
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Row selectedRow = null;
        DCIteratorBinding iter = bindings.findIteratorBinding("availableDocumentIterator");
        
        Integer i = (Integer) valueChangeEvent.getComponent().getAttributes().get("idx");

        System.out.println("Selected row index"+i);
        selectedRow  = iter.getRowAtRangeIndex(i.intValue());

        Boolean requested = (Boolean) valueChangeEvent.getNewValue();
               
        if(Boolean.TRUE.equals(requested))
        {
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());                      
        
           selectedRow.setAttribute("requestedDate",date);
          //  selectedRow.setAttribute("requested", requested);
               
        } else 
      
        {                     
            selectedRow.setAttribute("requestedDate",null);           
        }           
           ADFUtils.refreshComponent(suppDocTable);      
    }
    
    public void onChangeReceived(ValueChangeEvent valueChangeEvent) {
     
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Row selectedRow = null;
        DCIteratorBinding iter = bindings.findIteratorBinding("availableDocumentIterator");
        Integer i = (Integer) valueChangeEvent.getComponent().getAttributes().get("idx");
        selectedRow  = iter.getRowAtRangeIndex(i.intValue());
       
      
        Boolean received = (Boolean) valueChangeEvent.getNewValue();
               
        if(Boolean.TRUE.equals(received))
        {
          
        
           selectedRow.setAttribute("received","true");
        
               
        } else
        {                     
            selectedRow.setAttribute("received","false");           
        }   
        
        ADFUtils.refreshComponent(suppDocTable);     
    }

    public void setAvailableDocsGrp(RichPanelGroupLayout availableDocsGrp) {
        this.availableDocsGrp = availableDocsGrp;
    }

    public RichPanelGroupLayout getAvailableDocsGrp() {
        return availableDocsGrp;
    }

    public void setDocNameString(String docNameString) {
        this.docNameString = docNameString;
    }

    public String getDocNameString() {
        return docNameString;
    }

    /** This method is invoked to call the upsert, when the user clicks Next button on Supp Info page
     * @return
     */
    public String onSuppInfoSave() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
       
//        DCIteratorBinding propIter = bindings.findIteratorBinding("propsPojoIterator");
//        propIter.executeQuery();        
        
        DCIteratorBinding iter3 = bindings.findIteratorBinding("availableDocumentIterator");
        
       
        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("upsertProps");
        String wuid = (String) ADFUtils.getPageFlowValue("wuid");
        String propsid = (String) ADFUtils.getPageFlowValue("propsid");
      
        opBind.getParamsMap().put("p0", Integer.valueOf(wuid));
        opBind.getParamsMap().put("p1", Integer.valueOf(propsid));
        String opStatus = (String) opBind.execute();

        String wuidMessage = "Supporting Info Saved Successfully";
        addFacesMessage(FacesMessage.SEVERITY_INFO, wuidMessage);

       
        DCIteratorBinding iter1 = bindings.findIteratorBinding("propsPojoIterator");
        iter1.executeQuery();
        ADFUtils.refreshComponent(suppDocTable);     
        // Add event code here...
        return null;
    }

    protected void addFacesMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void setRequestedDateOutPut(RichOutputText requestedDateOutPut) {
        this.requestedDateOutPut = requestedDateOutPut;
    }

    public RichOutputText getRequestedDateOutPut() {
        return requestedDateOutPut;
    }

    public void setRequestedCheckBox(RichSelectBooleanCheckbox requestedCheckBox) {
        this.requestedCheckBox = requestedCheckBox;
    }

    public RichSelectBooleanCheckbox getRequestedCheckBox() {
        return requestedCheckBox;
    }
    
   
  
    public void setSuppDocTable(RichTable suppDocTable) {
        this.suppDocTable = suppDocTable;
    }

    public RichTable getSuppDocTable() {
        return suppDocTable;
    }
    
    public String deleteDocumentList(ActionEvent a) {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        
        DCIteratorBinding iter = bindings.findIteratorBinding("availableDocumentIterator");
        Integer i = (Integer) a.getComponent().getAttributes().get("idx");
        oracle.binding.OperationBinding op = (oracle.binding.OperationBinding) bindings.getOperationBinding("deleteAvailableDocuments");
        op.getParamsMap().put("p0", i.intValue());
        op.execute();
        iter.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(availableDocsGrp);
        return null;
    }
}
