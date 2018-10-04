package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.util.ComponentReference;
import org.apache.myfaces.trinidad.event.PollEvent;

/**
 * @author Vijay Redla
 * @version
 */
public class BVMActionsBean extends AmpManagedBean {
    @SuppressWarnings("compatibility:8314895732836748510")
    private static final long serialVersionUID = 1L;
    public static final AmpLogger LOGGER = new AmpLogger(BVMActionsBean.class);

    private ComponentReference retrunBtn;
    private ComponentReference currentEventsTable;
    private ComponentReference historicalEventsTable;
    private ComponentReference submitWarnPopup;
    private ComponentReference submitWarnText;
    private ComponentReference approveWarnPopup;
    private ComponentReference approveWarnText;
    private ComponentReference rejectWarnText;
    private ComponentReference rejectWarnPopup;
    
    private String popupIdValue;

    public BVMActionsBean() {
        super();
    }

    /**
     * @param actionEvent
     */
    public void BvmOnSubmitHandler(ActionEvent actionEvent) {
        //Custom Code for OPA Submit rule check
        OperationBinding onSubmit = ADFUtils.findOperation("onSubmit");
        if (onSubmit != null) {
            String message = (String)onSubmit.execute();
            if (message != null) {
                if ("TRUE".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation successful");
                    getSubmitWarnText().setValue("<p>Do you want to submit for approval?</p>");
                    final RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    getSubmitWarnPopup().show(hints);
                } else if ("MODIFIED".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Events changed in the database");
                    getSubmitWarnText().setValue(BaseYearEventConstants.EVENTS_CHANGED_MESSAGE);
                    final RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    getSubmitWarnPopup().show(hints);
                } else if (!"FALSE".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation returns Errors");
                    //We have error messages to be displayed
                    FacesMessage fm = new FacesMessage("Validation Errors", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                }
            } else {
                LOGGER.error(this, "BVMActionsBean: OnSubmit", BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation returns null", null);
            }
        } else {
            LOGGER.error(this, "BVMActionsBean: OnSubmit", BaseYearEventUtils.LOGGER_PREFIX + "Could not find OnSubmit binding", null);
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnCloseDialogHandler(ActionEvent actionEvent) {
        //Custom Code for OPA Submit rule check
        System.out.println("::START BvmOnCloseDialogHandler:::");
        DCIteratorBinding historicalIterator = ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_ITERATOR);
        OperationBinding onClose = ADFUtils.findOperation("onClose");
        String type = "eventType";
        String eventType = (String)ADFUtils.getPageFlowValue(type);
        onClose.getParamsMap().put(type, eventType);
        if (onClose != null) {
            String message = (String)onClose.execute();
            if (message != null) {
                if ("TRUE".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation successful");
                    // return Action updated :09Feb
                    returnAction();
                    DCIteratorBinding currentIterator = ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_ITERATOR);
                    if (currentIterator != null) {
                        currentIterator.executeQuery();
                    }
                    if (historicalIterator != null) {
                        historicalIterator.executeQuery();
                    }
//                    System.out.println("NKKK::::");
//                    returnAction(); // MOved the return action after executing the iterators : NK 3 Feb 
                } else if (!"FALSE".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation returns Errors");
                    //We have error messages to be displayed
                    FacesMessage fm = new FacesMessage("Validation Errors", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                }
            } else {
                LOGGER.error(this, "BVMActionsBean: OnClose", BaseYearEventUtils.LOGGER_PREFIX + "OPA Validation returns null", null);
            }
        } else {
            LOGGER.error(this, "BVMActionsBean: OnClose", BaseYearEventUtils.LOGGER_PREFIX + "Could not find OnClose binding", null);
        }
    }

    public void BvmOnCancelDialogHandler(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding cancel = ADFUtils.findOperation("cancel");
        String eventType = "eventType";
        cancel.getParamsMap().put(eventType, ADFUtils.getPageFlowValue(eventType));
        String isNew = "isNew";
        cancel.getParamsMap().put(isNew, ADFUtils.getPageFlowValue(isNew));
        cancel.execute();

        // Clear the input fields when canceled
        RichPanelGroupLayout pgl5 = (RichPanelGroupLayout)JSFUtils.findComponentInRoot("pgl5");
        clearInputComponents(pgl5);
        
        // Get the ID for the popup, and hide
        String popupId = (String)JSFUtils.getFromPageFlow("popupId");
        LOGGER.warn("PopupId:>> " + popupId);
        RichPopup popup = (RichPopup)JSFUtils.findComponentInRoot(popupId);
        if (null != popup)
                popup.cancel();
        LOGGER.warn("PopupId CANCEL:");
        returnAction();
    }

    private void clearInputComponents(UIComponent uiComponent) {
        // use recursion to look through all children to remove the values in the
        // input components of the parent passed into this method
        List<UIComponent> components = uiComponent.getChildren();
        for (UIComponent component : components) {
            if (component instanceof RichInputText) {
                RichInputText it = (RichInputText)component;
                it.resetValue();
            } else if (component instanceof RichSelectOneRadio) {
                RichSelectOneRadio sor = (RichSelectOneRadio)component;
                sor.resetValue();
            } else if (component instanceof RichInputDate) {
                RichInputDate ipd = (RichInputDate)component;
                ipd.resetValue();
            } else if (component instanceof RichSelectBooleanCheckbox) {
                RichSelectBooleanCheckbox sbc = (RichSelectBooleanCheckbox)component;
                sbc.resetValue();
            } else if (component instanceof RichSelectOneChoice) {
                RichSelectOneChoice soc = (RichSelectOneChoice)component;
                soc.resetValue();
            }
            clearInputComponents(component);
        }
    }    

    private void returnAction() {
        ActionEvent actionEvent = new ActionEvent(getRetrunBtn());
        actionEvent.queue();
    }

    public void setRetrunBtn(RichCommandButton retrunBtn) {
        this.retrunBtn = ComponentReference.newUIComponentReference(retrunBtn);
    }

    public RichCommandButton getRetrunBtn() {
        if (retrunBtn != null) {
            return (RichCommandButton)retrunBtn.getComponent();
        }
        return null;
    }
    
    /**
     * The name of the atteribute name for usermodified element has 1 in the end
     * @param valueChangeEvent
     */
    public void setVerifiedHandler(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean newValue = (Boolean)valueChangeEvent.getNewValue();
        if (newValue) {
            ADFUtils.setBoundAttributeValue(BaseYearEventConstants.VERIFICATION_DATE + 1, Calendar.getInstance().getTime());
            ADFUtils.setBoundAttributeValue(BaseYearEventConstants.VERIFIED_BY + 1, ADFUtils.getPageFlowValue("userId"));
        } else {
            ADFUtils.setBoundAttributeValue(BaseYearEventConstants.VERIFICATION_DATE + 1, null);
            ADFUtils.setBoundAttributeValue(BaseYearEventConstants.VERIFIED_BY + 1, null);
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnApproveHandler(ActionEvent actionEvent) {
        OperationBinding checkForChangedEvents = ADFUtils.findOperation("checkForChangedEvents");
        Map params = checkForChangedEvents.getParamsMap();
        params.put("ain", ADFUtils.getBoundAttributeValue("ain"));
        params.put("aoid", ADFUtils.getBoundAttributeValue("aoid"));
        if (checkForChangedEvents != null) {
            String message = (String)checkForChangedEvents.execute();
            if (message != null) {
                final RichPopup.PopupHints hints = new RichPopup.PopupHints();
                hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN,
                         RichPopup.PopupHints.AlignTypes.ALIGN_OVERLAP);
                if ("MODIFIED".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Events changed in the database");
                    getApproveWarnText().setValue(BaseYearEventConstants.EVENTS_CHANGED_MESSAGE);
                    getApproveWarnPopup().show(hints);
                } else if ("FALSE".equalsIgnoreCase(message)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "No Changes approve");
                    getApproveWarnText().setValue(BaseYearEventConstants.APPROVE_MESSAGE);
                    getApproveWarnPopup().show(hints);
                }
            }
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnRejectHandler(ActionEvent actionEvent) {
        //Fire Event to Reject to BPM
        OperationBinding fireRejectMethod = ADFUtils.findOperation("fireReject");
        fireRejectMethod.execute();
    }

    public String customRouter() {
        return ADFUtils.getPageFlowStringValue(BaseYearEventConstants.EVENT_TYPE);
    }

    /**
     * @param dialogEvent
     */
    public void BvmOnCancelHandler(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            // write your custom code for ok event
            OperationBinding fireCancelMethod = ADFUtils.findOperation("fireCancel");
            fireCancelMethod.execute();
        } else {
            // write your custom code for cancel event
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnCancelHandler_action(ActionEvent actionEvent) {
        // cancel the event
        OperationBinding fireCancelMethod = ADFUtils.findOperation("fireCancel");
        fireCancelMethod.execute();
    }

    /**
     * @param dialogEvent
     */
    public void BvmOnSubmitDlgHandler(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            // write your custom code for ok event
            //Fire Event to Submit to BPM
            OperationBinding fireSubmitMethod = ADFUtils.findOperation("fireSubmit");
            fireSubmitMethod.execute();
        } else {
            // write your custom code for cancel event
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnSubmitDlgHandler_action(ActionEvent actionEvent) {
        //Fire Event to Submit to BPM
        OperationBinding fireSubmitMethod = ADFUtils.findOperation("fireSubmit");
        fireSubmitMethod.execute();
    }

    /**
     * @param dialogEvent
     */
    public void BvmOnApproveDlgHandler(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            // write your custom code for ok event
            //Fire Event to Appove to BPM
            OperationBinding fireSubmitMethod = ADFUtils.findOperation("fireApprove");
            fireSubmitMethod.execute();
        } else {
            // write your custom code for cancel event
        }
    }

    /**
     * @param actionEvent
     */
    public void BvmOnApproveDlgHandler_action(ActionEvent actionEvent) {
        //Fire Event to Appove to BPM
        OperationBinding fireSubmitMethod = ADFUtils.findOperation("fireApprove");
        fireSubmitMethod.execute();
    }

    private void deleteEventCall(DCIteratorBinding iterator, RichTable table, String type) {
        RowKeySet rks = table.getSelectedRowKeys();
        Iterator<Object> keys = rks.iterator();
        while (keys.hasNext()) {
            List key = (List)keys.next();
            JUCtrlHierBinding treeBinding = null;
            CollectionModel collectionModel = (CollectionModel)table.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row row = nodeBinding.getRow();
            JUCtrlHierNodeBinding parentNode = nodeBinding.getParent();
            JUCtrlHierNodeBinding nextNode = null;

            String rowKeyString = iterator.getCurrentRow().getAttribute("byeId").toString();
            iterator.setCurrentRowWithKeyValue(rowKeyString);

            int currentRowIndex = iterator.getRowSetIterator().getCurrentRowIndex();
            if (iterator.getRowSetIterator().hasNext()) {
                nextNode = (JUCtrlHierNodeBinding)parentNode.getChildren().get(currentRowIndex + 1);
            } else if (iterator.getRowSetIterator().hasPrevious()) {
                nextNode = (JUCtrlHierNodeBinding)parentNode.getChildren().get(currentRowIndex - 1);
            } else {
                nextNode = parentNode;
            }

            //Current Delete operation
            OperationBinding deleteEvent = ADFUtils.getOperationBinding(BaseYearEventConstants.DELETE_EVENT);
            deleteEvent.getParamsMap().put(BaseYearEventConstants.EVENT_TYPE, type);
            deleteEvent.execute();
            iterator.executeQuery();

            // Set the trees selected row to the keyPath of the nextNode
            if (nextNode != null) {
                ArrayList nextNodeKeyList = nextNode.getKeyPath();
                RowKeySet keyset = new RowKeySetImpl();
              //  keyset.setCollectionModel((CollectionModel)(table.getValue()));
                keyset.add(nextNodeKeyList);
              //  table.setSelectedRowKeys(keyset);
                SelectionEvent selectEvent =
                    new SelectionEvent(rks,keyset, table);
                selectEvent.queue();
            }
        }
    }

    /**
     * @param actionEvent
     */
    public void ResetHistoricalEventHandler(ActionEvent actionEvent) {
        OperationBinding resetEvent = ADFUtils.getOperationBinding(BaseYearEventConstants.RESET_BINDING);
        resetEvent.getParamsMap().put(BaseYearEventConstants.EVENT_TYPE, BaseYearEventConstants.HISTORICAL);
        resetEvent.execute();
        BaseYearEventUtils.selectEventInTable("HISTORICAL", getHistoricalEventsTable(), getCurrentEventsTable());
        DCIteratorBinding iterator = ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_LIST_ITERATOR);
        iterator.executeQuery();
        BaseYearEventUtils.fireSelectEventInTable(iterator, null, getCurrentEventsTable());
    }

    /**
     * @param actionEvent
     */
    public void ResetCurrentEventHandler(ActionEvent actionEvent) {
        OperationBinding resetEvent = ADFUtils.getOperationBinding(BaseYearEventConstants.RESET_BINDING);
        resetEvent.getParamsMap().put(BaseYearEventConstants.EVENT_TYPE, BaseYearEventConstants.CURRENT);
        resetEvent.execute();
        BaseYearEventUtils.selectEventInTable("CURRENT", getHistoricalEventsTable(), getCurrentEventsTable());    
        DCIteratorBinding iterator = ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_LIST_ITERATOR);
        iterator.executeQuery();
        BaseYearEventUtils.fireSelectEventInTable(iterator, null, getHistoricalEventsTable());
    }

    /**
     * @param actionEvent
     */
    public void DeleteCurrentEventHandler(ActionEvent actionEvent) {
        DCIteratorBinding iterator = ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_LIST_ITERATOR);
        deleteEventCall(iterator, getCurrentEventsTable(), BaseYearEventConstants.CURRENT);
        BaseYearEventUtils.refreshCurrentEventsUI(getCurrentEventsTable());
    }


    /**
     * @param actionEvent
     */
    public void DeleteHistoricalEventHandler(ActionEvent actionEvent) {
        DCIteratorBinding iterator = ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_LIST_ITERATOR);
        deleteEventCall(iterator, getHistoricalEventsTable(), BaseYearEventConstants.HISTORICAL);
        BaseYearEventUtils.refreshHistoricalEventsUI(getHistoricalEventsTable());
    }

    /**
     * @param actionEvent
     */
    public void editCurrentEventHandler(ActionEvent actionEvent) {
        if (getCurrentEventsTable().getSelectedRowKeys().size() > 0) {
            BaseYearEventUtils.setSelectedRow(getCurrentEventsTable(),
                                              BaseYearEventConstants.SELECT_CURRENT_EVENT_METHOD_BINDING);
            CurrentPopupTaskflowHandler handle =
                (CurrentPopupTaskflowHandler)JSFUtils.getManagedBeanValue("viewScope.currentPopupTaskflowHandler");
            if (handle != null) {
                ADFUtils.showPopup(handle.getCurrentPopup());            
            }
        } else {
            FacesMessage fm = new FacesMessage("Current event not selected");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
    }

    /**
     * @param actionEvent
     */
    public void editHistoricalEventHandler(ActionEvent actionEvent) {
        if (getHistoricalEventsTable().getSelectedRowKeys().size() > 0) {
            BaseYearEventUtils.setSelectedRow(getHistoricalEventsTable(),
                                              BaseYearEventConstants.SELECT_HISTORICAL_EVENT_METHOD_BINDING);
            HistoricalPopupTaskflowHandler handle =
                (HistoricalPopupTaskflowHandler)JSFUtils.getManagedBeanValue("viewScope.historicalPopupTaskflowHandler");
            if (handle != null) {
                ADFUtils.showPopup(handle.getHistoricalPopup());            
            }
        } else {
            FacesMessage fm = new FacesMessage("Historical event not selected");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
    }

    /**
     * @param valueChangeEvent
     */
    public void currentEventSelectionListnr(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getSource();
        Object newValue = valueChangeEvent.getNewValue();
        RowKeySet selectedEmps = getCurrentEventsTable().getSelectedRowKeys();
        updateSelection(valueChangeEvent, newValue, selectedEmps);
    }

    /**
     * @param valueChangeEvent
     */
    public void histroicalEventSelectionListnr(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //getTableBind is binding of table on page.
        valueChangeEvent.getSource();
        Object newValue = valueChangeEvent.getNewValue();
        RowKeySet selectedEmps = getHistoricalEventsTable().getSelectedRowKeys();
        updateSelection(valueChangeEvent, newValue, selectedEmps);
    }

    private void updateSelection(ValueChangeEvent valueChangeEvent, Object newValue, RowKeySet selectedEmps) {
        //Create iterator from RowKeySet
        Iterator selectedEmpIter = selectedEmps.iterator();

        while (selectedEmpIter.hasNext()) {
            List<Object> list = (List<Object>)selectedEmpIter.next();
            Key keys = (Key)list.get(0);
            Object[] keyValues = keys.getKeyValues();
            for (Object key : keyValues) {
                OperationBinding updateSelection = ADFUtils.getOperationBinding("updateSelection");
                updateSelection.getParamsMap().put("byeId", key);
                updateSelection.getParamsMap().put("newValue", newValue);
                updateSelection.execute();
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Selection Value Changed: " + valueChangeEvent.getNewValue());
            }
        }
    }

    /**
     * @param valueChangeEvent
     */
    public void reviewRequiredSelectionListnr(ValueChangeEvent valueChangeEvent) {
        OperationBinding updateReviewRequired = ADFUtils.getOperationBinding("updateReviewRequired");
        updateReviewRequired.getParamsMap().put("flag", valueChangeEvent.getNewValue());
        updateReviewRequired.execute();
    }

    public void setCurrentEventsTable(RichTable currentEventsTable) {
        this.currentEventsTable = ComponentReference.newUIComponentReference(currentEventsTable);
    }

    public RichTable getCurrentEventsTable() {
        if (currentEventsTable != null) {
            return (RichTable)currentEventsTable.getComponent();
        }
        return null;
    }

    public void setHistoricalEventsTable(RichTable historicalEventsTable) {
        this.historicalEventsTable = ComponentReference.newUIComponentReference(historicalEventsTable);
    }

    public RichTable getHistoricalEventsTable() {
        if (historicalEventsTable != null) {
            RichTable HistEventsRichTable =
                (RichTable)historicalEventsTable.getComponent();
            return HistEventsRichTable;
        }
        return null;
    }

    public void setSubmitWarnPopup(RichPopup submitWarnPopup) {
        this.submitWarnPopup = ComponentReference.newUIComponentReference(submitWarnPopup);
    }

    public RichPopup getSubmitWarnPopup() {
        if (submitWarnPopup != null) {
            return (RichPopup)submitWarnPopup.getComponent();
        }
        return null;
    }

    public void setSubmitWarnText(RichOutputFormatted submitWarnText) {
        this.submitWarnText = ComponentReference.newUIComponentReference(submitWarnText);
    }

    public RichOutputFormatted getSubmitWarnText() {
        if (submitWarnText != null) {
            return (RichOutputFormatted)submitWarnText.getComponent();
        }
        return null;
    }

    public void setApproveWarnPopup(RichPopup approveWarnPopup) {
        this.approveWarnPopup = ComponentReference.newUIComponentReference(approveWarnPopup);
    }

    public RichPopup getApproveWarnPopup() {
        if (approveWarnPopup != null) {
            return (RichPopup)approveWarnPopup.getComponent();
        }
        return null;
    }

    public void setApproveWarnText(RichOutputFormatted approveWarnText) {
        this.approveWarnText = ComponentReference.newUIComponentReference(approveWarnText);
    }

    public RichOutputFormatted getApproveWarnText() {
        if (approveWarnText != null) {
            return (RichOutputFormatted)approveWarnText.getComponent();
        }
        return null;
    }

    public void setRejectWarnPopup(RichPopup rejectWarnPopup) {
        this.rejectWarnPopup = ComponentReference.newUIComponentReference(rejectWarnPopup);
    }

    public RichPopup getRejectWarnPopup() {
        if (rejectWarnPopup != null) {
            return (RichPopup)rejectWarnPopup.getComponent();
        }
        return null;
    }

    public void setRejectWarnText(RichOutputFormatted rejectWarnText) {
        this.rejectWarnText = ComponentReference.newUIComponentReference(rejectWarnText);
    }

    public RichOutputFormatted getRejectWarnText() {
        if (rejectWarnText != null) {
            return (RichOutputFormatted)rejectWarnText.getComponent();
        }
        return null;
    }

    public void setPopupIdValue(String popupIdValue) {
        this.popupIdValue = popupIdValue;
    }

    public String getPopupIdValue() {
        return popupIdValue;
    }

    public void onLoadSelectionEvent(PollEvent poll) {
        BaseYearEventUtils.fireSelectEventInTable(ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_LIST_ITERATOR), null,
                                                  getCurrentEventsTable());
        BaseYearEventUtils.fireSelectEventInTable(ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_LIST_ITERATOR), null,
                                                  getHistoricalEventsTable());
    }
    
    
    //12 refresh issue
       public String initTrendCalcPg() {
           DCBindingContainer dbc = ADFUtils.getDCBindingContainer();
           dbc.getOperationBinding("loadDataForTBV").execute();
           DCIteratorBinding targetHisItr =(DCIteratorBinding) dbc.findIteratorBinding("filteredHistoricalEventsListIterator");
           if(targetHisItr != null)
             targetHisItr.executeQuery();
           return null;
       }
}
