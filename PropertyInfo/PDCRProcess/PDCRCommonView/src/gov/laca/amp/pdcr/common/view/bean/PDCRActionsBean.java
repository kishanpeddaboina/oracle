package gov.laca.amp.pdcr.common.view.bean;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;

import oracle.binding.OperationBinding;

import oracle.javatools.resourcebundle.BundleFactory;


public class PDCRActionsBean extends AmpManagedBean {
    private static final ADFLogger logger = ADFLogger.createADFLogger(PDCRActionsBean.class);

    private static final ResourceBundle pdcrBundle = BundleFactory.getBundle(ChangeRequestConstants.BUNDLE_VIEW_LOCATION);
    
    public PDCRActionsBean() {
        super();
    }

    public void PDCROnSubmitHandler(ActionEvent actionEvent) {
        // save the changes to the BPM payload
        Boolean isSubmitSucess = transformDataToBMPPayload();

        // set the return result used in the action for the command button
        if (isSubmitSucess) {
            logger.warning("Payload transformation for the submit action was successful");
            //Fire Event to Submit to BPM
            OperationBinding fireSubmitMethod = ADFUtils.findOperation("fireSubmit");
            fireSubmitMethod.execute();
        } else {
            logger.warning("Payload transformation for the submit action was NOT successful");
            //We have error messages to be displayed
            FacesMessage fm = new FacesMessage(getPdcrBundle().getString("BPM_TRANSFORMATION_ERROR"));
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
    }

    public void PDCROnSaveHandler(ActionEvent actionEvent) {
        // Save the changes to the BPM payload
        Boolean isSubmitSucess = transformDataToBMPPayload();

        // set the return result used in the action for the command button
        if (isSubmitSucess) {
            logger.warning("Payload transformation for the save action was successful");
            //Fire Event to Save to BPM
            OperationBinding fireSaveMethod = ADFUtils.findOperation("fireSave");
            fireSaveMethod.execute();
        } else {
            logger.warning("Payload transformation for the save action was NOT successful");
            //We have error messages to be displayed
            FacesMessage fm = new FacesMessage(getPdcrBundle().getString("BPM_TRANSFORMATION_ERROR"));
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
    }
    
    private Boolean transformDataToBMPPayload(){
        // Submit the PDCR payload to the BPM process
        // Get the AIN and the sourceType from pageFlow
        // AIN and sourceType were passed in during retrieval of the payload
        String ain = (String)ADFUtils.getBoundAttributeValue("ain");
        String sourceType = (String)ADFUtils.getBoundAttributeValue("sourceType");

        logger.warning("Submitting AIN " + ain + " to the BPM Process for the " + sourceType + " operation");

        // Initialize the Action return and the success flag
        Boolean isSubmitSucess = Boolean.FALSE;

        // Submit the PDCR process
        OperationBinding ob = ADFUtils.getOperationBinding("InitiatePropertyDataCR");
        ob.getParamsMap().put("ain", ain);
        ob.getParamsMap().put("userID", null);
        ob.getParamsMap().put("userType", null);
        ob.getParamsMap().put("sourceType", sourceType);
        ob.getParamsMap().put("dataSource", ChangeRequestConstants.SOURCE_BPM);
        ob.execute();
        if (ob.getErrors().isEmpty()) {
            // set the success flag used in the control flow case in the taskflow
            isSubmitSucess = (Boolean)ob.getResult();
        }    
        return isSubmitSucess;
    }

    public ResourceBundle getPdcrBundle() {
        return pdcrBundle;
    }
}
