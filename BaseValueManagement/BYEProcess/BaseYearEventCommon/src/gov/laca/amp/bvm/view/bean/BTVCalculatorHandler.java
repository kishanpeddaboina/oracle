package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import org.apache.myfaces.trinidad.util.ComponentReference;

public class BTVCalculatorHandler extends AmpManagedBean{

    @SuppressWarnings("compatibility:3611052701840715359")
    private static final long serialVersionUID = 1L;
    private ComponentReference tbvCalculatorPopup;
    private ComponentReference targetYear;
    private ComponentReference btvPanelGroup;
    private ComponentReference tbvTrendHeader;
    private ComponentReference tbvDetrendHeader;
    private boolean selectedRowForTrend;
    private boolean selectedEventListSize = Boolean.TRUE;
    public static final AmpLogger LOGGER = new AmpLogger(BTVCalculatorHandler.class);

    public BTVCalculatorHandler() {
        
    }

    public void generateBaseYearEvent(ActionEvent actionEvent) {
        String operationType = null;
               
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("operationType") !=
            null) {
            operationType = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("operationType").toString();
        }
        
        DCIteratorBinding trendEventIter = ADFUtils.findIterator("trendEventListIterator");
        DCIteratorBinding headerIterator = ADFUtils.findIterator("trendHeaderListIterator");
        DCIteratorBinding footerIterator = ADFUtils.findIterator("footerTrendEventListIterator");

        DCIteratorBinding detrendEventIter = ADFUtils.findIterator("deTrendEventListIterator");
        DCIteratorBinding deheaderIterator = ADFUtils.findIterator("detrendHeaderListIterator");
        DCIteratorBinding defooterIterator = ADFUtils.findIterator("footerDeTrendEventListIterator");
        
        OperationBinding tbvCalculatorEvent = ADFUtils.getOperationBinding("generateBaseYearEventScratch");
        tbvCalculatorEvent.getParamsMap().put("targetYear", getTargetYear().getValue().toString());
        tbvCalculatorEvent.getParamsMap().put("operationType", operationType);
        tbvCalculatorEvent.execute();

        trendEventIter.executeQuery();
        headerIterator.executeQuery();
        footerIterator.executeQuery();

        detrendEventIter.executeQuery();
        deheaderIterator.executeQuery();
        defooterIterator.executeQuery();
        
    }

    public void calculateTrend(ActionEvent actionEvent) {

        DCIteratorBinding trendEventIter = ADFUtils.findIterator("trendEventListIterator");
        DCIteratorBinding headerIterator = ADFUtils.findIterator("trendHeaderListIterator");
        DCIteratorBinding footerIterator = ADFUtils.findIterator("footerTrendEventListIterator");

        OperationBinding tbvCalculatorEvent = ADFUtils.getOperationBinding("calculateTrendBaseValues");
        tbvCalculatorEvent.execute();

        trendEventIter.executeQuery();
        headerIterator.executeQuery();
        footerIterator.executeQuery();
        
        getTbvTrendHeader().setDisclosed(Boolean.TRUE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTbvTrendHeader());
        
    }

    public void calculateDeTrend(ActionEvent actionEvent) {
        DCIteratorBinding detrendEventIter = ADFUtils.findIterator("deTrendEventListIterator");
        DCIteratorBinding deheaderIterator = ADFUtils.findIterator("detrendHeaderListIterator");
        DCIteratorBinding defooterIterator = ADFUtils.findIterator("footerDeTrendEventListIterator");

        OperationBinding tbvCalculatorEvent = ADFUtils.getOperationBinding("calculateDeTrendBaseValues");

        tbvCalculatorEvent.execute();

        detrendEventIter.executeQuery();
        deheaderIterator.executeQuery();
        defooterIterator.executeQuery();

        getTbvDetrendHeader().setDisclosed(Boolean.TRUE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTbvDetrendHeader());

    }
    
    public void resetTBVCalculator(ActionEvent actionEvent) {

        DCIteratorBinding trendEventIter = ADFUtils.findIterator("trendEventListIterator");
        DCIteratorBinding headerIterator = ADFUtils.findIterator("trendHeaderListIterator");
        DCIteratorBinding footerIterator = ADFUtils.findIterator("footerTrendEventListIterator");

        DCIteratorBinding deTrendEventIter = ADFUtils.findIterator("deTrendEventListIterator");
        DCIteratorBinding deTrendHeaderIterator = ADFUtils.findIterator("detrendHeaderListIterator");
        DCIteratorBinding deTrendFooterIterator = ADFUtils.findIterator("footerDeTrendEventListIterator");
        OperationBinding tbvCalculatorEvent = ADFUtils.getOperationBinding("clearTBVCalutatorData");
        tbvCalculatorEvent.execute();
        trendEventIter.executeQuery();
        headerIterator.executeQuery();
        footerIterator.executeQuery();

        deTrendEventIter.executeQuery();
        deTrendHeaderIterator.executeQuery();
        deTrendFooterIterator.executeQuery();

        //headerIterator.refresh(arg0);
        RichInputText targegYear = (RichInputText)JSFUtils.findComponentInRoot("targetYear");
        // Defect # 29656
        getTargetYear().setValue(null);
        getTargetYear().resetValue();
        getTargetYear().setSubmittedValue(null);
        getTargetYear().setValue("");
        if(targegYear != null) {
        targegYear.resetValue(); 
        } 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTargetYear());
        getTbvTrendHeader().setDisclosed(Boolean.FALSE);
        getTbvDetrendHeader().setDisclosed(Boolean.FALSE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTbvTrendHeader());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getTbvDetrendHeader());
    }

    public void setSelectedRowForTrend(boolean selectedRowForTrend) {
        this.selectedRowForTrend = selectedRowForTrend;
    }

    public boolean isSelectedRowForTrend() {

        Boolean selectedRow = true;
        DCIteratorBinding EventsListt = ADFUtils.findIterator("filteredHistoricalEventsListIterator");

        Row[] rows = EventsListt.getAllRowsInRange();
        for (Row row : rows) {

            if (row.getAttribute("selected").equals(true))
                selectedRow = false;
        }

        selectedRowForTrend = selectedRow;

        return selectedRowForTrend;
    }
    
    public void setSelectedEventListSize(boolean selectedEventListSize) {
        this.selectedEventListSize = selectedEventListSize;
    }

    public boolean isSelectedEventListSize() {

        OperationBinding tbvCalculatorEvent = ADFUtils.getOperationBinding("selectedEventListSize");
        Integer size = (Integer)tbvCalculatorEvent.execute();
        selectedEventListSize = ((size > 0 ) ? Boolean.FALSE : Boolean.TRUE);

        return selectedEventListSize;
    }

    public void setTbvTrendHeader(RichShowDetailHeader tbvTrendPGL) {
        this.tbvTrendHeader = ComponentReference.newUIComponentReference(tbvTrendPGL);
    }

    public RichShowDetailHeader getTbvTrendHeader() {
        if (tbvTrendHeader != null) {
            return (RichShowDetailHeader)tbvTrendHeader.getComponent();
        }
        return null;
    }

    public void setTbvDetrendHeader(RichShowDetailHeader tbvDetrendPGL) {
        this.tbvDetrendHeader = ComponentReference.newUIComponentReference(tbvDetrendPGL);
    }

    public RichShowDetailHeader getTbvDetrendHeader() {
        if (tbvDetrendHeader != null) {
            return (RichShowDetailHeader)tbvDetrendHeader.getComponent();
        }
        return null;
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

}
