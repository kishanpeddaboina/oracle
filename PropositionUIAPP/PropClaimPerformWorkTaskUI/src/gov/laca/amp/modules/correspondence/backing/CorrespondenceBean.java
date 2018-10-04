package gov.laca.amp.modules.correspondence.backing;

import gov.laca.amp.common.view.backing.bean.RefreshParentComponentBean;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import javax.faces.component.UIComponent;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

public class CorrespondenceBean {
    private RichPopup correspondencePopup;
    private RichButton apprvBtn;

    public CorrespondenceBean() {
    }
    String propsId = null;
    String readOnlyMode = null;

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
        
        System.out.println("INDIDE BPM onclickCorrespondence");
       


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
        
        
        String RSvalue1 = (String)JSFUtils.resolveExpression("#{requestScope.sbmtRelValue}");

        System.out.println("RSvalue1:"+RSvalue1);
        
        String RSvalue2 = (String)JSFUtils.resolveExpression("#{viewScope.sbmtRelValue}");

        System.out.println("RSvalue2:"+RSvalue2);
        
        System.out.println("BPM --> propsId :: =="+propsId +", readOnlyMode :"+readOnlyMode+",category :"+category+", WorkUnitId:"+WorkUnitId);


//        ADFUtils.setPageFlowValue("propsId", propsId);
//        ADFUtils.setPageFlowValue("readOnlyMode", readOnlyMode);
//        ADFUtils.setPageFlowValue("category", category);
//        ADFUtils.setPageFlowValue("subcategory", subcategory);
//        ADFUtils.setPageFlowValue("WorkUnitId", WorkUnitId);
        
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getCorrespondencePopup().show(hints);
        
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
           //  System.out.println("IN closePopup");
           UIComponent tmpComponent;
           tmpComponent = this.getCorrespondencePopup();
           while (!(tmpComponent instanceof RichPopup)) {
               tmpComponent = tmpComponent.getParent();
           }
           RichPopup popup = (RichPopup) tmpComponent;
           popup.hide();
       }

    public String cancelPopUP() {
        // Add event code here...   
        closePopup();
        return "closeTaskFlow";
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
}
