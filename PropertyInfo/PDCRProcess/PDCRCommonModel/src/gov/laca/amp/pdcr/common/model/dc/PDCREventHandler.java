package gov.laca.amp.pdcr.common.model.dc;

import gov.laca.amp.fwk.extn.dc.AmpPojoDC;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;


public class PDCREventHandler extends AmpPojoDC {
    public static final ADFLogger logger = ADFLogger.createADFLogger(PDCREventHandler.class);


    public PDCREventHandler() {
        super();
    }

    public void handleFireSubmit() {
        logger.warning("Perform PDCR Submit Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton submitButton = (RichCommandToolbarButton)root.findComponent("submitBtn");
        ActionEvent actionEvent = new ActionEvent(submitButton);
        actionEvent.queue();
    }

    public void handleFireSave() {
        logger.warning("Perform PDCR Save Action");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        /**Pass ctb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
        RichCommandToolbarButton updateBtn = (RichCommandToolbarButton)root.findComponent("save");
        ActionEvent actionEvent = new ActionEvent(updateBtn);
        actionEvent.queue();
    }

    /**
     * Publihs method for firing Submit event
     * @param payload
     * @return
     */
    public Object fireSubmit(Object payload) {
        logger.warning("Fire Submit Event");
        handleFireSubmit();
        return payload;
    }

    /**
     * Publihs method for firing Save event
     * @param payload
     * @return
     */
    public Object fireSave(Object payload) {
        logger.warning("Fire Save Event");
        handleFireSave();
        return payload;
    }

    protected Object loadData(Object obj) {
        return null;
    }

}
