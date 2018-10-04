package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.util.ComponentReference;

public class BTVCalculatorHandler extends AmpManagedBean {

    @SuppressWarnings("compatibility:3611052701840715359")
    private static final long serialVersionUID = 1L;
    private ComponentReference tbvCalculatorPopup;
    private ComponentReference targetYear;
    private ComponentReference btvPanelGroup;

    public BTVCalculatorHandler() {

    }

    public void generateBaseYearEvent(ActionEvent actionEvent) {
        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        DCIteratorBinding trendEventIter =
            ADFUtils.findIterator("trendEventListIterator");
        DCIteratorBinding headerIterator =
            ADFUtils.findIterator("trendHeaderListIterator");
        DCIteratorBinding footerIterator =
            ADFUtils.findIterator("footerTrendEventListIterator");

        OperationBinding tbvCalculatorEvent =
            ADFUtils.getOperationBinding("generateBaseYearEventData");
        tbvCalculatorEvent.getParamsMap().put("targetYear",
                                              getTargetYear().getValue().toString());
        tbvCalculatorEvent.execute();

        trendEventIter.executeQuery();
        headerIterator.executeQuery();
        footerIterator.executeQuery();
        
        adfctx.addPartialTarget(getBtvPanelGroup());
        getBtvPanelGroup().setVisible(Boolean.TRUE);
        
    }

    public void setBtvPanelGroup(RichPanelGroupLayout btvPanelGroup) {
        this.btvPanelGroup =
                ComponentReference.newUIComponentReference(btvPanelGroup);
    }

    public RichPanelGroupLayout getBtvPanelGroup() {
        if (btvPanelGroup != null) {
            return (RichPanelGroupLayout)btvPanelGroup.getComponent();
        }
        return null;
    }

    public RichPopup getTbvCalculatorPopup() {
        if (tbvCalculatorPopup != null) {
            return (RichPopup)tbvCalculatorPopup.getComponent();
        }
        return null;
    }

    public void setTbvCalculatorPopup(RichPopup tbvCalculatorPopup) {
        this.tbvCalculatorPopup =
                ComponentReference.newUIComponentReference(tbvCalculatorPopup);
    }

    public void setTargetYear(RichInputText targetYear) {
        this.targetYear =
                ComponentReference.newUIComponentReference(targetYear);
    }

    public RichInputText getTargetYear() {
        if (targetYear != null) {
            return (RichInputText)targetYear.getComponent();
        }
        return null;
    }

    public void resetTBVCalculator(ActionEvent actionEvent) {

        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        DCIteratorBinding trendEventIter =
            ADFUtils.findIterator("trendEventListIterator");
        DCIteratorBinding headerIterator =
            ADFUtils.findIterator("trendHeaderListIterator");
        DCIteratorBinding footerIterator =
            ADFUtils.findIterator("footerTrendEventListIterator");

        clearTBVCalculatorData();

        trendEventIter.executeQuery();
        headerIterator.executeQuery();
        footerIterator.executeQuery();
        
        adfctx.addPartialTarget(getBtvPanelGroup());
        getBtvPanelGroup().setVisible(Boolean.FALSE);

    }

    private void clearTBVCalculatorData() {
        OperationBinding tbvCalculatorEvent =
            ADFUtils.getOperationBinding("clearTBVCalutatorData");        
        tbvCalculatorEvent.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBtvPanelGroup());
        getBtvPanelGroup().setVisible(Boolean.FALSE);
    }
    
    public void cancelTbvPopup(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        clearTBVCalculatorData();
    }
}
