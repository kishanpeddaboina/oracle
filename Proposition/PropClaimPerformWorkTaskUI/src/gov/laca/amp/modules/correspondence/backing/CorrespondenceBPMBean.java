package gov.laca.amp.modules.correspondence.backing;


import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;

public class CorrespondenceBPMBean {
    private RichPopup correspondencePopup;
    private RichButton apprvBtn;

    String propsId = null;
    String readOnlyMode = null;
    private RichButton submitForCorrespondenceApproval;
    private RichButton submitForCorrespondenceRelease;
    private RichButton closeBtn;
    private RichPopup correspondencePopUp1;
    
    boolean dispcorrAppr = false;
    boolean dispcorrRel = false;
    private RichPanelGroupLayout parentPgl;
    private RichButton cancelBtn;

    public void setDispcorrAppr(boolean dispcorrAppr) {
        this.dispcorrAppr = dispcorrAppr;
    }

    public boolean isDispcorrAppr() {
        showcorrApprv();
        return dispcorrAppr;
    }

    public void setDispcorrRel(boolean dispcorrRel) {
        this.dispcorrRel = dispcorrRel;
    }

    public boolean isDispcorrRel() {
        showcorrRel();
        
        return dispcorrRel;
    }

    public void setSbmtRelValue(String sbmtRelValue) {
        this.sbmtRelValue = sbmtRelValue;
    }

    public String getSbmtRelValue() {
        return sbmtRelValue;
    }


    public void setPropsId(String propsId) {
        this.propsId = propsId;
    }

    public String getPropsId() {
        return propsId;
    }

    public void setReadOnlyMode(String readOnlyMode) {
        this.readOnlyMode = readOnlyMode;
    }

    public String getReadOnlyMode() {
        return readOnlyMode;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setWorkUnitId(String WorkUnitId) {
        this.WorkUnitId = WorkUnitId;
    }

    public String getWorkUnitId() {
        return WorkUnitId;
    }
    String category = null;
    String subcategory = null;
    String WorkUnitId = null;
    String sbmtRelValue = null;

    public String onclickCorrespondence() {
        // Add event code here...
        
        


        if (AdfFacesContext.getCurrentInstance().getViewScope().get("propsId") != null) {

            propsId =AdfFacesContext.getCurrentInstance().getViewScope().get("propsId") .toString();
        }

        if (AdfFacesContext.getCurrentInstance().getViewScope().get("readOnlyMode") != null) {

            readOnlyMode =AdfFacesContext.getCurrentInstance().getViewScope().get("readOnlyMode") .toString();
        }
        if (AdfFacesContext.getCurrentInstance().getViewScope().get("category") != null) {

            category =AdfFacesContext.getCurrentInstance().getViewScope().get("category") .toString();
        }
        if (AdfFacesContext.getCurrentInstance().getViewScope().get("subcategory") != null) {

            subcategory =AdfFacesContext.getCurrentInstance().getViewScope().get("subcategory") .toString();
        }
        if (AdfFacesContext.getCurrentInstance().getViewScope().get("WorkUnitId") != null) {

            WorkUnitId =AdfFacesContext.getCurrentInstance().getViewScope().get("WorkUnitId") .toString();
        }
        
        if (AdfFacesContext.getCurrentInstance().getViewScope().get("sbmtRelValue") != null) {

            sbmtRelValue =AdfFacesContext.getCurrentInstance().getViewScope().get("sbmtRelValue") .toString();
        }
        
        

        showcorrApprv();
        showcorrRel();
        JSFUtils.setExpressionValue("#{requestScope.sbmtRelValue}", dispcorrRel);
        JSFUtils.setExpressionValue("#{requestScope.sbmtApprValue}", dispcorrAppr); 
        
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getCorrespondencePopUp1().show(hints);
        
        return null;
    }

    public void setCorrespondencePopup(RichPopup correspondencePopup) {
        this.correspondencePopup = correspondencePopup;
    }

    public RichPopup getCorrespondencePopup() {
        return correspondencePopup;
    }
    
    public void closePopup() {
           // Add event code here...
           UIComponent tmpComponent;
           tmpComponent = this.getCorrespondencePopUp1();
           while (!(tmpComponent instanceof RichPopup)) {
               tmpComponent = tmpComponent.getParent();
           }
           RichPopup popup = (RichPopup) tmpComponent;
           popup.hide();
       }

    public String cancelPopUP() {
        // Add event code here...   
        closePopup();
        
        
          
        return "canel";
    }

    public void closepopupLstnr(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setApprvBtn(RichButton apprvBtn) {
        this.apprvBtn = apprvBtn;
    }

    public RichButton getApprvBtn() {
        return apprvBtn;
    }

   

    public String sbmtApprValue() {
        // Add event code here...
        
//        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("SubmitForCorrespondenceApproval");
//        op1.execute();
        
        RichButton sbmttForCorrApp = this.getSubmitForCorrespondenceApproval();
        ActionEvent actionEvent = new ActionEvent(sbmttForCorrApp);
        actionEvent.queue();
        
        RichButton closeBtn = this.getCloseBtn();
          ActionEvent actionEvent1 = new ActionEvent(closeBtn);
          actionEvent1.queue();
        
//        closePopup();
        return "closeTaskFlow";
    }

    public String SbmtRelvalue() {
        // Add event code here...
//        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("SubmitForCorrespondenceRelease");
//        op1.execute();
        
      RichButton sbmttForCorrRel = this.getSubmitForCorrespondenceRelease();
        ActionEvent actionEvent = new ActionEvent(sbmttForCorrRel);
        actionEvent.queue();
        
        RichButton closeBtn = this.getCloseBtn();
          ActionEvent actionEvent1 = new ActionEvent(closeBtn);
          actionEvent1.queue();
          
//        closePopup();
        return "closeTaskFlow";
    }

    public void setSubmitForCorrespondenceApproval(RichButton submitForCorrespondenceApproval) {
        this.submitForCorrespondenceApproval = submitForCorrespondenceApproval;
    }

    public RichButton getSubmitForCorrespondenceApproval() {
        return submitForCorrespondenceApproval;
    }

    public void setSubmitForCorrespondenceRelease(RichButton submitForCorrespondenceRelease) {
        this.submitForCorrespondenceRelease = submitForCorrespondenceRelease;
    }

    public RichButton getSubmitForCorrespondenceRelease() {
        return submitForCorrespondenceRelease;
    }

    public void setCloseBtn(RichButton closeBtn) {
        this.closeBtn = closeBtn;
    }

    public RichButton getCloseBtn() {
        return closeBtn;
    }

    public void setCorrespondencePopUp1(RichPopup correspondencePopUp1) {
        this.correspondencePopUp1 = correspondencePopUp1;
    }

    public RichPopup getCorrespondencePopUp1() {
        return correspondencePopUp1;
    }
    
    public boolean showcorrApprv() {
         
        DCBindingContainer dcbbinding = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) dcbbinding.findIteratorBinding("correspondencesPojoListIterator");
        iter.executeQuery();
        
        if(iter != null){
        Row corespondencsesRows[] = iter.getAllRowsInRange();

        if (corespondencsesRows != null) {
            for (int i = 0; i < iter.getEstimatedRowCount(); i++) {
                Row row = iter.getRowAtRangeIndex(i);
                String dispSubFrCorrRel =(String)row.getAttribute("dispSubFrCorrRel");
                String dispSubFrCorrApprv = (String) row.getAttribute("dispSubFrCorrApprv");
                   if("true".equalsIgnoreCase(dispSubFrCorrApprv)) {
                   dispcorrAppr = true;
                   return  true;
               }
               
            }
        }
        }
        if (parentPgl != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(parentPgl);
        }          
        return dispcorrAppr;
    }
    
    
    public boolean showcorrRel() {
        
        DCBindingContainer dcbbinding = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) dcbbinding.findIteratorBinding("correspondencesPojoListIterator");
       // iter.executeQuery();
      if(iter != null) {
        Row corespondencsesRows[] = iter.getAllRowsInRange();
        if (corespondencsesRows != null) {
            for (int i = 0; i < iter.getEstimatedRowCount(); i++) {
                Row row = iter.getRowAtRangeIndex(i);
                String dispSubFrCorrRel =(String)row.getAttribute("dispSubFrCorrRel");
                String dispSubFrCorrApprv = (String) row.getAttribute("dispSubFrCorrApprv");
              if("true".equalsIgnoreCase(dispSubFrCorrRel)) {
                   dispcorrRel = true;
                   return  true;
               }else {
                  dispcorrRel = false;
              }
            }
        }
        }
        if (parentPgl != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(parentPgl);
        }
        return dispcorrRel;
    }

    public void setParentPgl(RichPanelGroupLayout parentPgl) {
        this.parentPgl = parentPgl;
    }

    public RichPanelGroupLayout getParentPgl() {
        showcorrApprv();
        showcorrRel();
        
        
        return parentPgl;
    }

    public void setCancelBtn(RichButton cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public RichButton getCancelBtn() {
      
        return cancelBtn;
    }
}
