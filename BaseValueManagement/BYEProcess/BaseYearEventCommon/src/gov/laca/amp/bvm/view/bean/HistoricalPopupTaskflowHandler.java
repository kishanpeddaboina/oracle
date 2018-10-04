package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import java.io.Serializable;

import java.math.BigInteger;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.RegionNavigationEvent;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * @author
 * @version
 */
public class HistoricalPopupTaskflowHandler implements Serializable {
    @SuppressWarnings("compatibility:3685880399741039196")
    private static final long serialVersionUID = 1L;
    private String historicalTaskFlowId =
        "/WEB-INF/gov/laca/amp/bvm/view/bye-details-btf/bye-details-btf.xml#bye-details-btf";
    private ComponentReference historicalPopup;
    private ComponentReference createHistoricalPopup;
    private String emptyTaskFlowId = "";
    private String taskflowId = historicalTaskFlowId;
    private ComponentReference historicalEventsPanel;
    public static final AmpLogger LOGGER =
        new AmpLogger(HistoricalPopupTaskflowHandler.class);
    private ComponentReference historicalDetailHeader;
    private ComponentReference historicalToolbar;

    public HistoricalPopupTaskflowHandler() {
    }

    public String getDynamicTaskFlowId() {
        return taskflowId;
    }

    public void cancelPopup(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...        
        taskflowId = emptyTaskFlowId;
        BVMActionsBean bean = (BVMActionsBean)JSFUtils.getManagedBeanValue("backingBeanScope.bvmActionsBean");
        BaseYearEventUtils.selectEventInTable("HISTORICAL", bean.getHistoricalEventsTable(), bean.getCurrentEventsTable());
        BaseYearEventUtils.fireSelectEventInTable(ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_ITERATOR), null, bean.getCurrentEventsTable());
    }

    public void showHistoricalPopup(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        taskflowId = historicalTaskFlowId;
    }

    public void hearHistoricalNavigation(RegionNavigationEvent regionNavigationEvent) {
        // Add event code here...
        String newId = regionNavigationEvent.getNewViewId();
        if (newId == null) {
            // reset the taskflow
            taskflowId = emptyTaskFlowId;
            // close the popup
            getHistoricalPopup().cancel();
        }
    }

    public void hearCreateHistoricalNavigation(RegionNavigationEvent regionNavigationEvent) {
        // Add event code here...
        
        System.out.println("INSIDE hearCreateHistoricalNavigation::");
        String newId = regionNavigationEvent.getNewViewId();
        if (newId == null) {
            // reset the taskflow
            taskflowId = emptyTaskFlowId;
            // close the popup
            getCreateHistoricalPopup().cancel();            
        }
    }


    public void setHistoricalPopup(RichPopup historicalPopup) {
        this.historicalPopup =
                ComponentReference.newUIComponentReference(historicalPopup);
    }

    public RichPopup getHistoricalPopup() {
        if (historicalPopup != null) {
            return (RichPopup)historicalPopup.getComponent();
        }
        return null;
    }


    public void setHistoricalEventsPanel(RichPanelCollection historicalEventsPanel) {
        this.historicalEventsPanel =
                ComponentReference.newUIComponentReference(historicalEventsPanel);
          
    }

    public RichPanelCollection getHistoricalEventsPanel() {
        if (historicalEventsPanel != null) {
            return (RichPanelCollection)historicalEventsPanel.getComponent();
        }
        return null;
    }

    public void setHistoricalDetailHeader(RichShowDetailHeader historicalDetailHeader) {
        this.historicalDetailHeader =
                ComponentReference.newUIComponentReference(historicalDetailHeader);
    }

    public RichShowDetailHeader getHistoricalDetailHeader() {
        if (historicalDetailHeader != null) {
            return (RichShowDetailHeader)historicalDetailHeader.getComponent();
        }
        return null;
    }

    public void setCreateHistoricalPopup(RichPopup createHistoricalPopup) {
        this.createHistoricalPopup =
                ComponentReference.newUIComponentReference(createHistoricalPopup);
    }

    public RichPopup getCreateHistoricalPopup() {
        if (createHistoricalPopup != null) {
            return (RichPopup)createHistoricalPopup.getComponent();
        }
        return null;
    }

    public void setHistoricalToolbar(RichToolbar historicalToolbar) {
        this.historicalToolbar = ComponentReference.newUIComponentReference(historicalToolbar);
    }

    public RichToolbar getHistoricalToolbar() {
        if(historicalToolbar != null){
            return (RichToolbar)historicalToolbar.getComponent();
        }
        return null;
    }

   
}
