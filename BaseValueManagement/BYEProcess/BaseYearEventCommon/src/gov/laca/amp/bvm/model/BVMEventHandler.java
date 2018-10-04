package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.logger.AmpLogger;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;


/**
 * @author
 * @version
 */
public class BVMEventHandler {
    private static final AmpLogger LOGGER =
        new AmpLogger(BVMEventHandler.class);

    public BVMEventHandler() {
        super();
    }

    public void handleFireSubmit() {
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Submit Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton submitButton = (RichCommandToolbarButton)root.findComponent("submitBtn");
        ActionEvent actionEvent = new ActionEvent(submitButton);
        actionEvent.queue();
    }
    
    public void handleFireApprove() {
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Approve Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton approveBtn = (RichCommandToolbarButton)root.findComponent("approveBtn");
        ActionEvent actionEvent = new ActionEvent(approveBtn);
        actionEvent.queue();
    }
    
    public void handleFireReject() {
        LOGGER.info(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Reject Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton rejectBtn = (RichCommandToolbarButton)root.findComponent("rejectBtn");
        ActionEvent actionEvent = new ActionEvent(rejectBtn);
        actionEvent.queue();
    }    
    
    public void handleFireCancel() {
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Cancel Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton cancelBtn = (RichCommandToolbarButton)root.findComponent("cancelBtn");
        ActionEvent actionEvent = new ActionEvent(cancelBtn);
        actionEvent.queue();
    } 
    
    public void handleFireSave(Object payLoad){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Save Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton updateBtn = (RichCommandToolbarButton)root.findComponent("updateBtn");
        ActionEvent actionEvent = new ActionEvent(updateBtn);
        actionEvent.queue();        
//        OperationBinding update = ADFUtils.getOperationBinding("update");
//        update.execute();
//        if(payLoad instanceof FacesCtrlAttrsBinding){
//            FacesCtrlAttrsBinding  eventModfied = (FacesCtrlAttrsBinding )payLoad;
//            eventModfied.setInputValue(Boolean.TRUE);
//        }
//        if(payLoad instanceof Boolean){
//            Boolean eventModfied =(Boolean)payLoad;
//            eventModfied = Boolean.TRUE;
//        }

        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Perform BPM Save");
    }

    /**
     * Publihs method for firing Approve event
     * @param payload
     * @return
     */
    public Object fireApprove(Object payload){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Fire Approve Event");
        return payload;
    }
    
    /**
     * Publihs method for firing Reject event
     * @param payload
     * @return
     */
    public Object fireReject(Object payload){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Fire Reject Event");
        return payload;
    }
    
    /**
     * Publihs method for firing Cancel event
     * @param payload
     * @return
     */
    public Object fireCancel(Object payload){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Fire Cancel Event");
        return payload;
    }
    
    /**
     * Publihs method for firing Cancel event
     * @param payload
     * @return
     */
    public Object fireSubmit(Object payload){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Fire Submit Event");
        return payload;
    }
}
