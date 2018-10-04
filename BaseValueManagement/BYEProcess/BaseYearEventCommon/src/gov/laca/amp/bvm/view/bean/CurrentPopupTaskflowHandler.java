package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;

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

import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * @author
 * @version
 */
public class CurrentPopupTaskflowHandler implements Serializable {
    @SuppressWarnings("compatibility:-831467757206784087")
    private static final long serialVersionUID = 1L;
    private String currentTaskFlowId =
        "/WEB-INF/gov/laca/amp/bvm/view/bye-details-btf/bye-details-btf.xml#bye-details-btf";
    private String emptyTaskFlowId = "";
    private String taskflowId = currentTaskFlowId;
    private ComponentReference currentPopup;
    private ComponentReference createCurrentPopup;
    private ComponentReference currentEventsPanel;
    private ComponentReference currentDetailHeader;
    private ComponentReference currentToolbar;


    public CurrentPopupTaskflowHandler() {
    }

    public String getDynamicTaskFlowId() {
        return taskflowId;
    }

    public void cancelPopup(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        taskflowId = emptyTaskFlowId;
        BVMActionsBean bean = (BVMActionsBean)JSFUtils.getManagedBeanValue("backingBeanScope.bvmActionsBean");
        BaseYearEventUtils.selectEventInTable("CURRENT",bean.getHistoricalEventsTable(), bean.getCurrentEventsTable());
        BaseYearEventUtils.fireSelectEventInTable(ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_LIST_ITERATOR), null, bean.getHistoricalEventsTable());
    }

    public void hearCurrentNavigation(RegionNavigationEvent regionNavigationEvent) {
        // Add event code here...
        String newId = regionNavigationEvent.getNewViewId();
        if (newId == null) {
            // reset the taskflow
            taskflowId = emptyTaskFlowId;
            // close the popup
            getCurrentPopup().cancel();
        }
    }

    public void hearCreateCurrentNavigation(RegionNavigationEvent regionNavigationEvent) {
        // Add event code here...
        String newId = regionNavigationEvent.getNewViewId();
        if (newId == null) {
            // reset the taskflow
            taskflowId = emptyTaskFlowId;
            // close the popup
            RichPopup popup = getCreateCurrentPopup();
            popup.cancel();
            AdfFacesContext.getCurrentInstance().addPartialTarget(popup);
        }
    }

    public void showCurrentPopup(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        taskflowId = currentTaskFlowId;
    }

    public void setCurrentPopup(RichPopup currentPopup) {
        this.currentPopup =
                ComponentReference.newUIComponentReference(currentPopup);
    }

    public RichPopup getCurrentPopup() {
        if (currentPopup != null) {
            return (RichPopup)currentPopup.getComponent();
        }
        return null;
    }

    public void setCurrentEventsPanel(RichPanelCollection currentEventsPanel) {
        this.currentEventsPanel =
                ComponentReference.newUIComponentReference(currentEventsPanel);
    }

    public RichPanelCollection getCurrentEventsPanel() {
        if (currentEventsPanel != null) {
            return (RichPanelCollection)currentEventsPanel.getComponent();
        }
        return null;
    }

    public void setCurrentDetailHeader(RichShowDetailHeader currentDetailHeader) {
        this.currentDetailHeader =
                ComponentReference.newUIComponentReference(currentDetailHeader);
    }

    public RichShowDetailHeader getCurrentDetailHeader() {
        if (currentDetailHeader != null) {
            return (RichShowDetailHeader)currentDetailHeader.getComponent();
        }
        return null;
    }

    public void setCreateCurrentPopup(RichPopup createCurrentPopup) {
        this.createCurrentPopup =
                ComponentReference.newUIComponentReference(createCurrentPopup);
    }

    public RichPopup getCreateCurrentPopup() {
        if (createCurrentPopup != null) {
            return (RichPopup)createCurrentPopup.getComponent();
        }
        return null;
    }

    public void setCurrentToolbar(RichToolbar currentToolbar) {
        this.currentToolbar = ComponentReference.newUIComponentReference(currentToolbar);
    }

    public RichToolbar getCurrentToolbar() {
        if(currentToolbar != null){
            return (RichToolbar)currentToolbar.getComponent();
        }
        return null;
    }
}
