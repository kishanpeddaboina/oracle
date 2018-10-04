package gov.laca.amp.pdcr.common.view.bean;

import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;
import gov.laca.amp.portal.PortalExtensionConstants;

import java.io.Serializable;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.javatools.resourcebundle.BundleFactory;


public class PDCRValidateSubPart implements Serializable, Validator {
    public static final ADFLogger logger = ADFLogger.createADFLogger(PDCRValidateSubPart.class);

    private static final ResourceBundle pdcrBundle = BundleFactory.getBundle(ChangeRequestConstants.BUNDLE_VIEW_LOCATION);


    public PDCRValidateSubPart() {
        super();
    }

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) {

        //logger.warning("SubPart entered:" + object);
        String evaluatValue = (String)object;
        if ((evaluatValue == null) || evaluatValue.length() == 0) {
            return;
        }

        // set the result values
        Boolean validateResult = Boolean.TRUE;
        Boolean duplicateResults = Boolean.FALSE;
        Boolean validateError = Boolean.FALSE;
        
        // Run a few checks on the number being passed
        String expression = PortalExtensionConstants.SUBPART_REGEX;

        CharSequence inputStr = evaluatValue;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        // Check the rules for validation
        if (!matcher.matches()) {
            validateResult = Boolean.FALSE;
        } else if (Arrays.asList(PortalExtensionConstants.INVALID_SUBPART_IDS).contains(evaluatValue)) {
            validateResult = Boolean.FALSE;
        } else {
            int wholeNumber = Integer.parseInt(evaluatValue);
            int first2 = Integer.parseInt(evaluatValue.substring(0, 2));
            int second2 = Integer.parseInt(evaluatValue.substring(2));
            if (wholeNumber < 0101) {
                validateResult = Boolean.FALSE;
            } else if (first2 > second2) {
                validateResult = Boolean.FALSE;
            }
        }

        // Check for duplication if all the previous validations passed
        if (validateResult) {
            // Get the attributes set for the SubPart
            String subPartId = (null != uiComponent.getAttributes().get("subPartId") ? (String)uiComponent.getAttributes().get("subPartId") : "");
            String rpiType = (null != uiComponent.getAttributes().get("rpiType") ? (String)uiComponent.getAttributes().get("rpiType") : "");

            OperationBinding ob = ADFUtils.getOperationBinding("ValidateDuplicateSubPart");
            ob.getParamsMap().put("subPartId", subPartId);
            ob.getParamsMap().put("rpiType", rpiType);
            ob.getParamsMap().put("subPartNumber", evaluatValue);
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                duplicateResults = (Boolean)ob.getResult();
            } else {
                validateError = Boolean.TRUE;
            }
        }

        // if any of the validation rules appy above then display a message to the user
        if (!validateResult) {
            RichInputText it = (RichInputText)uiComponent;
            String textLabel = it.getLabel();
            String errorDetail = textLabel + ": " + evaluatValue + " - " + getPdcrBundle().getString("SUBPART_NOT_VALID_ERROR_MSG_DETAIL");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, getPdcrBundle().getString("SUBPART_NOT_VALID_ERROR_MSG_TITLE"),
                                                          errorDetail));
        } else if (duplicateResults) {
            RichInputText it = (RichInputText)uiComponent;
            String textLabel = it.getLabel();
            String errorDetail = textLabel + ": " + evaluatValue + " - " + getPdcrBundle().getString("SUBPART_IS_DUPLICATE_MSG_DETAIL");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, getPdcrBundle().getString("SUBPART_IS_DUPLICATE_MSG_TITLE"), errorDetail));
        } else if (validateError) {
            RichInputText it = (RichInputText)uiComponent;
            String textLabel = it.getLabel();
            String errorDetail = textLabel + ": " + evaluatValue + " - " + getPdcrBundle().getString("SUBPART_DC_FAILURE_MSG_DETAIL");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, getPdcrBundle().getString("SUBPART_DC_FAILURE_MSG_TITLE"),
                                                          errorDetail));
        }
    }

    public ResourceBundle getPdcrBundle() {
        return pdcrBundle;
    }

}

