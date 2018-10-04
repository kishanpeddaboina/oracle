package gov.laca.amp.pdcr.common.view.bean;

import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;
import gov.laca.amp.pdcr.common.model.data.UserInfoDO;
import gov.laca.amp.pdcr.common.view.util.LOVManager;

import java.io.IOException;

import java.math.BigInteger;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.event.DialogEvent;
import org.apache.myfaces.trinidad.component.UIXValue;

import oracle.binding.OperationBinding;

import oracle.javatools.resourcebundle.BundleFactory;

public class PDCRBean extends AmpManagedBean {
    private static final ADFLogger logger = ADFLogger.createADFLogger(PDCRBean.class);

    private static final ResourceBundle pdcrBundle =
        BundleFactory.getBundle(ChangeRequestConstants.BUNDLE_VIEW_LOCATION);

    // local variable for the user type passed into the BPM Process
    private UserInfoDO userInfoDO;

    // Infromation for the AIN and the PDCR flow
    private String ain;
    private String aoid;
    private String situsAddressIcon;
    private String printIcon;
    private String userGroups;
    private String sourceType;
    private String dataSource;
    private String rpiType;

    // parameters for the edit and delete of subpart and composite, and edit for land
    private String subPartID;
    private String compositeID;
    private String landID;

    // local variables used conditionally enable/disable controls on the create page
    private Boolean isClericalUser;
    private Boolean isExternalUser;
    private Boolean disablePublicServiceRelated;
    private Boolean isCreateValidated;
    private Boolean isDataError = Boolean.FALSE;
    private Boolean isInvalidWidthDepth = Boolean.FALSE;
    private Boolean isInvalidSqftAcres = Boolean.FALSE;
    private Boolean isDimensionRequired = Boolean.FALSE;
    private Boolean isInvalidWidthDepthSqftAcres = Boolean.FALSE;

    private Boolean isInvalidYearBuilt = Boolean.FALSE;
    private Boolean isInvalidEffYearBuilt = Boolean.FALSE;
    private Boolean isSubPartDataError = Boolean.FALSE;

    private String displayMessage;

    public PDCRBean() {
        super();
    }

    /**
     * @param severity
     * @param msg
     */
    protected void addFacesMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    /**
     * @param iterator
     * @param recreate
     */
    protected void refreshIterator(String iterator, Boolean recreate) {
        DCIteratorBinding iter = ADFUtils.findIterator(iterator);
        if (recreate) {
            iter.clearForRecreate();
        } else {
            iter.executeQuery();
            iter.refresh(DCIteratorBinding.RANGESIZE_UNLIMITED);
        }
    }

    private static SecurityContext getSecurityContext() {
        return ADFContext.getCurrent().getSecurityContext();
    }

    private static String getRequestHeader(String paramName) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> headerMap = context.getExternalContext().getRequestHeaderMap();
        String returnValue = headerMap.get(paramName);
        return returnValue;
    }

    private static void ShowPopup(String popupId) {
        // get the UIComponent of the popup to show
        RichPopup popup = popup = (RichPopup) JSFUtils.findComponentInRoot(popupId);

        // Show the popup
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        ph.add(RichPopup.PopupHints
                        .HintTypes
                        .HINT_LAUNCH_ID, popup);
        popup.show(ph);
    }

    private static void HidePopup(String popupId) {
        // get the UIComponent of the popup to hide
        RichPopup popup = (RichPopup) JSFUtils.findComponentInRoot(popupId);

        // Hide the popup
        popup.hide();
    }

    public void getUserInformation() {
        this.userInfoDO = new UserInfoDO();

        // user information for external users, and use to prepopulate the Create PDCR
        getUserInfoDO().setUserId(getSecurityContext().getUserProfile().getUserID());
        getUserInfoDO().setUserEmail(getSecurityContext().getUserProfile().getBusinessEmail());
        getUserInfoDO().setUserDisplayName(getSecurityContext().getUserProfile().getDisplayName());
        getUserInfoDO().setUserPhone(getSecurityContext().getUserProfile().getBusinessPhone());
        getUserInfoDO()
            .setUserFullName(getSecurityContext().getUserProfile().getFirstName() + " " +
                             getSecurityContext().getUserProfile().getLastName());

        setDisablePublicServiceRelated(Boolean.TRUE);

        // Get the roles for user from the security context
        getUserInfoDO().setUserRoles(getSecurityContext().getUserRoles());
        setUserGroups(JSONUtils.toJson(getUserInfoDO().getUserRoles()));
        setDisablePublicServiceRelated(Boolean.FALSE);

        // see if the user is in the ExternalPublic group
        if (getUserGroups().contains(ChangeRequestConstants.EXTERNAL_GROUP)) {
            setIsExternalUser(Boolean.TRUE);
            getUserInfoDO().setUserType(ChangeRequestConstants.USER_TYPE_EXTERNAL);
        } else {
            setIsExternalUser(Boolean.FALSE);
            getUserInfoDO().setUserType(ChangeRequestConstants.USER_TYPE_INTERNAL);
        }

        logger.warning("Logged in user information: " + JSONUtils.toJson(getUserInfoDO()));
    }

    /**
     * @return
     */
    public String RetrievePDCRData() {
        RichInputText ainInput = (RichInputText) JSFUtils.findComponentInRoot("ainInput");
        String ain = null;
        //12c add null check
        if (ainInput.getValue() != null)
            ain = ainInput.getValue().toString();
        ADFUtils.setBoundAttributeValue("sourceType", ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE);
        RichPanelGroupLayout changeRequestBody =
            (RichPanelGroupLayout) JSFUtils.findComponentInRoot("changeRequestBody");

        logger.warning("AIN captured: " + ain);

        if (null != ain) {
            try {
                ADFUtils.setBoundAttributeValue("ain", ain);

                // Need to check the lock status of the AIN the lock is not set wheh the UI opens,
                // it may be possible that one was initiated while filling out the form
                Boolean isPDCRInitiated = checkAINLock(ain);

                // If the PDCR is initiated, and we are trying to force then display a warning message to the user
                if (isPDCRInitiated) {
                    // If there are errors on the retrieve then set the ain to null
                    ADFUtils.setBoundAttributeValue("ain", null);
                } else {
                    OperationBinding ob = ADFUtils.getOperationBinding("RetrieveRealPropertyInfo");
                    ob.getParamsMap().put("ain", ain);
                    ob.getParamsMap().put("dataSource", ChangeRequestConstants.SOURCE_SOA);
                    ob.getParamsMap().put("isClericalUser", getIsClericalUser());
                    ob.getParamsMap().put("sourceType", ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE);
                    ob.getParamsMap().put("isExternalUser", getIsExternalUser());
                    ob.getParamsMap().put("isCheckLock", Boolean.FALSE);
                    ob.getParamsMap().put("userGroups", getUserGroups());
                    ob.execute();
                    if (!ob.getErrors().isEmpty()) {
                        // If there are errors on the retrieve then set the ain to null
                        ADFUtils.setBoundAttributeValue("ain", null);
                    }
                }

                // check the ain from the DC
                String newAIN = (String) ADFUtils.getBoundAttributeValue("ain");
                // Check the operation of the service call
                logger.warning("The retrieve operation was " + (null != newAIN ? "successful" : "not successfull"));

                String ainDisplay = (String) ADFUtils.getBoundAttributeValue("ainDisplay");
                String msg =
                    (null != ainDisplay ? getPdcrBundle().getString("MESSAGE_AIN_LABEL").concat(ainDisplay) :
                     getPdcrBundle().getString("PDCR_MISSING_AIN"));

                if (isPDCRInitiated) {
                    msg = msg + " " + getPdcrBundle().getString("AIN_LOCKED_MESSAGE");
                    clearInputComponents(changeRequestBody);
                    addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
                } else if (null == newAIN) {
                    msg = msg + " " + getPdcrBundle().getString("INVALID_AIN_MESSAGE");
                    clearInputComponents(changeRequestBody);
                    addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
                } else {
                    // Populate the requestor information when the user is external, and there was no error
                    if (getUserInfoDO().getUserType().equalsIgnoreCase(ChangeRequestConstants.USER_TYPE_EXTERNAL)) {
                        OperationBinding ob = ADFUtils.getOperationBinding("PopulateRequestorInfo");
                        ob.getParamsMap().put("requestorName", getUserInfoDO().getUserFullName());
                        ob.getParamsMap().put("email", getUserInfoDO().getUserEmail());
                        ob.getParamsMap().put("phone", getUserInfoDO().getUserPhone());
                        ob.execute();
                    }
                }

                // reset the ain input text in the event that there weas a failure
                ainInput.setValue(newAIN);

                // Refresh the iterator binding for change request
                refreshIterator(ChangeRequestConstants.ORIGINAL_ITERATOR, Boolean.TRUE);
                refreshIterator(ChangeRequestConstants.REQUESTOR_ITERATOR, Boolean.TRUE);
                refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.TRUE);
                refreshIterator(ChangeRequestConstants.LAND_ITERATOR, Boolean.TRUE);
            } catch (Exception e) {
                logger.severe("Unable to retrieve AIN: ", e);
                String msg = getPdcrBundle().getString("PDCR_FAILURE_MESSAGE");
                addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
            } finally {
                // refresh the UI controls by making a partil trigger on  the parent panel group
                ADFUtils.refreshComponent(changeRequestBody);
                return null;
            }
        }
        return null;
    }

    /**
     * @param ain
     * @return
     */
    public Boolean checkAINLock(String ain) {
        // This method is only called when the AIN is entered from the UI.
        // The taskflow will directly execute the method in the data control
        Boolean results = Boolean.FALSE;

        // If there was no AIN paramater set then do not perform the check
        if (null != ain) {
            // Check the lock for this AIN
            OperationBinding ob = ADFUtils.getOperationBinding("CheckLock");
            ob.getParamsMap().put("ain", ain);
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                results = ((Boolean) ob.getResult());
            }
        }
        return results;
    }

    /**
     * @return
     */
    public String initiatePropertyDataCR() {

        //Check to make sure that the PDCR is validated before initiating
        if (getIsCreateValidated()) {
            // Initiate the PDCR for the ain retrieved
            // Get the AIN and the sourceType from pageFlow
            // AIN was passed in during the checklock, or from the UI on Create
            // sourceType was set during the retrieval of Real Property Infromation
            if (null != ADFUtils.getBoundAttributeValue("ain"))
                setAin((String) ADFUtils.getBoundAttributeValue("ain"));
            if (null != ADFUtils.getBoundAttributeValue("aoid")) {
                BigInteger aoidDC = (BigInteger) ADFUtils.getBoundAttributeValue("aoid");
                setAoid(String.valueOf(aoidDC));
            }
            if (null != ADFUtils.getBoundAttributeValue("sourceType"))
                setSourceType((String) ADFUtils.getBoundAttributeValue("sourceType"));

            // Initialize the Action return and the success flag
            String submitResult = "notSubmitted";

            // Need to check the lock status of the AIN the lock is not set wheh the UI opens,
            // it may be possible that one was initiated while filling out the form
            // If the PDCR is initiated we need to display a warning message to the user
            if (checkAINLock(getAin())) {
                String msg = getPdcrBundle().getString("AIN_INPROCESS_MESSAGE") + " " + getAin();
                addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
            } else {
                // Initiate the PDCR process
                OperationBinding ob = ADFUtils.getOperationBinding("InitiatePropertyDataCR");
                ob.getParamsMap().put("ain", getAin());
                ob.getParamsMap().put("userID", getUserInfoDO().getUserId());
                ob.getParamsMap().put("userType", getUserInfoDO().getUserType());
                ob.getParamsMap().put("sourceType", getSourceType());
                ob.getParamsMap().put("dataSource", getDataSource());
                ob.execute();
                if (ob.getErrors().isEmpty()) {
                    submitResult = "submitted";
                    if (getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE)) {
                        RichPanelGroupLayout changeRequestBody =
                            (RichPanelGroupLayout) JSFUtils.findComponentInRoot("changeRequestBody");
                        clearInputComponents(changeRequestBody);
                    }
                } else {
                    String msg = getPdcrBundle().getString("PDCR_SUBMITT_FAILURE");
                    addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
                }
            }
            return submitResult;
        } else {
            return null;
        }
    }

    /**
     * @param actionEvent
     */
    public void validatePDCRSubmit(ActionEvent actionEvent) {
        logger.finest("Validate PDCR before submitting");
        if (getUserInfoDO().getUserType().equalsIgnoreCase(ChangeRequestConstants.USER_TYPE_EXTERNAL)) {
            setIsCreateValidated(Boolean.FALSE);

            //get component id by get("AttributeName");
            Map<String, Object> attributes = actionEvent.getComponent().getAttributes();
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                String validateControls = JSONUtils.toJson(ChangeRequestConstants.VALIDATE_CONTROLS);
                // Check to see if the control in the attribute map should be validated
                if (validateControls.contains(entry.getValue().toString())) {
                    RichInputText it = (RichInputText) JSFUtils.findComponentInRoot(entry.getValue().toString());
                    if (null != it.getValue()) {
                        setIsCreateValidated(Boolean.TRUE);
                        break;
                    }
                }
            }
        } else if (getUserInfoDO().getUserType().equalsIgnoreCase(ChangeRequestConstants.USER_TYPE_INTERNAL)) {
            setIsCreateValidated(Boolean.TRUE);
        }

        //throw error if valiation fails
        if (!getIsCreateValidated()) {
            String errorDetail =
                getPdcrBundle().getString("CREATE_PDCR_VALID_ERROR_MSG")
                .concat(getPdcrBundle().getString("MISSING_INFO_FOR_CREATE"));
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
        }
    }

    /**
     * @param dialogEvent
     */
    public void CancelPDCRDialogListener(DialogEvent dialogEvent) {
        // Build the URL for returning back to the portal when canceling based on the user type
        logger.warning("Canceling the " + getSourceType() + " operation");

        // Clean up the objects from the trasaction
        OperationBinding ob = ADFUtils.getOperationBinding("CancelTrasaction");
        ob.execute();

        // Build the proper URL, and redirect the user
        RedirectURL(buildReturnURL(Boolean.FALSE));
    }

    /**
     * @param actionEvent
     */
    public void ReturnToAMPHome(ActionEvent actionEvent) {
        // Build the URL for returning to home directly
        logger.warning("Retruning home from the " + getSourceType() + " operation");

        // Build the proper URL, and redirect the user
        RedirectURL(buildReturnURL(Boolean.TRUE));
    }

    /**
     * @param actionEvent
     */
    public void ReturnFromPDCR(ActionEvent actionEvent) {
        // Build the URL for returning back to the portal after submitting based on the user type
        logger.warning("Retruning from the " + getSourceType() + " operation");

        // Build the proper URL, and redirect the user
        RedirectURL(buildReturnURL(Boolean.FALSE));
    }

    private String buildReturnURL(Boolean forceHome) {
        // Get the referer url from the request header
        String referer = getRequestHeader("Referer");

        // Check to see if the ain was in the URL
        Boolean doesURLContainAIN = referer.contains("ain=");

        // Set the default URL to redirect the user to
        String baseUrl = getBaseURL();
        String returnUrl = baseUrl;

        // Get the params from the referer
        int paramStart = referer.indexOf("?");
        String urlParams = "";
        if (paramStart > -1) {
            urlParams = referer.substring(paramStart, referer.length());
        }

        logger.warning("Params: " + urlParams);

        // If the user is an external user then go back to the portal home, other wise go back to the property detail page
        if (getIsExternalUser() || !doesURLContainAIN || forceHome) {
            returnUrl = baseUrl.concat(urlParams);
        } else {
            // Get the property detail URL from the navigation
            String propertyDetailPage =
                "/" + JSFUtils.resolveExpressionAsString(ChangeRequestConstants.AMP_PORTAL_NAV_MODEL_PDTL);
            String returnPage = propertyDetailPage.concat(urlParams);
            returnUrl = baseUrl.concat(returnPage);
        }
        return returnUrl;
    }

    private void RedirectURL(String url) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        ControllerContext controllerCtx;
        controllerCtx = ControllerContext.getInstance();
        try {
            logger.warning("Redirecting to ... " + url);
            ectx.redirect(url);
        } catch (IOException e) {
            logger.severe("Unable to redirect to : " + url, e);
        }
    }

    private String getBaseURL() {
        // Set the default URL to redirect the user to
        String defaultHost = JSFUtils.resolveExpressionAsString(ChangeRequestConstants.AMP_WEBCENTER_PORTAL_APP_URL);
        String ampPortal = ChangeRequestConstants.AMP_PORTAL;
        String baseUrl = defaultHost.concat(ampPortal);
        logger.warning("Base URL: " + baseUrl);
        return baseUrl;
    }

    /**
     * @param valueChangeEvent
     */
    public void pmocValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (!valueChangeEvent.getNewValue().equals(valueChangeEvent.getOldValue())) {
            refreshIterator(ChangeRequestConstants.REQUESTOR_ITERATOR, Boolean.FALSE);

            // refresh the UI controls by making a partil trigger on  the parent panel group
            RichPanelGroupLayout changeRequestBody =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("changeRequestBody");
            ADFUtils.refreshComponent(changeRequestBody);
        }
    }

    /**
     * @param valueChangeEvent
     */
    public void DataChangeSourceValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (!valueChangeEvent.getNewValue().equals(valueChangeEvent.getOldValue())) {
            if (valueChangeEvent.getNewValue()
                                .toString()
                                .equalsIgnoreCase(ChangeRequestConstants.PUBLIC_SERVICE_RELATED)) {
                setDisablePublicServiceRelated(Boolean.FALSE);
            } else {
                setDisablePublicServiceRelated(Boolean.TRUE);
            }

            // refresh the UI controls by making a partil trigger on  the parent panel group
            RichPanelGroupLayout changeRequestBody =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("changeRequestBody");
            ADFUtils.refreshComponent(changeRequestBody);
        }
    }

    /**
     * @param actionEvent
     */
    public void CancelCreate(ActionEvent actionEvent) {
        // Cancel the Edit
        ShowPopup("confirm_cancel");
    }

    /**
     * @param actionEvent
     */
    public void CancelEdit(ActionEvent actionEvent) {
        // Cancel the Edit
        ShowPopup("confirm_cancel");
    }

    /**
     * @param actionEvent
     */
    public void AddSubPart(ActionEvent actionEvent) {
        String returnType = "";

        setIsSubPartDataError(Boolean.FALSE);
        setIsInvalidYearBuilt(Boolean.FALSE);
//        setIsInvalidEffYearBuilt(Boolean.FALSE);

        // Add SubPart execution
        OperationBinding ob = ADFUtils.getOperationBinding("AddSubPart");
        ob.execute();
        if (ob.getErrors().isEmpty()) {
            returnType = (String) ob.getResult();
        }

        // Refresh the iterator binding for change request
        String popupId = null;
        if (returnType.equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_SUBPART)) {
            popupId = "addSubPart_Popup";
            refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
            refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);
        } else {
            popupId = "addComposite_Popup";
            refreshIterator(ChangeRequestConstants.COMPOSITE_ITERATOR, Boolean.FALSE);
            refreshIterator(ChangeRequestConstants.TEMP_COMPOSITE_ITERATOR, Boolean.FALSE);
        }

        // Show the add or composite add popup
        ShowPopup(popupId);
    }

    /**
     * @param actionEvent
     */
    public void EditSubPart(ActionEvent actionEvent) {

        setIsSubPartDataError(Boolean.FALSE);
        setIsInvalidYearBuilt(Boolean.FALSE);

        // Edit subpart execution
        OperationBinding ob = ADFUtils.getOperationBinding("EditSubPart");
        ob.getParamsMap().put("subPartID", getSubPartID());
        ob.getParamsMap().put("rpiType", getRpiType());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);

        // Show the subpart add popup
        ShowPopup("editSubPart_Popup");
    }

    /**
     * @param actionEvent
     */
    public void ReviewSubPart(ActionEvent actionEvent) {
        // Review subpart execution
        OperationBinding ob = ADFUtils.getOperationBinding("ReviewSubPart");
        ob.getParamsMap().put("subPartID", getSubPartID());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);

        // Show the subpart add popup
        ShowPopup("reviewSubPart_Popup");
    }

    /**
     * @param actionEvent
     */
    public void DeleteSubPart(ActionEvent actionEvent) {
        // Delete subpart execution
        OperationBinding ob = ADFUtils.getOperationBinding("DeleteSubPart");
        ob.getParamsMap().put("subPartID", getSubPartID());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);

        // Show the subpart delete popup
        ShowPopup("deleteSubPart_Popup");
    }

    /**
     * @param actionEvent
     */

    public void SaveSubPart(ActionEvent actionEvent) {
        
        boolean enableDebug = false;

        // Get the id of the button that raised this event to properly close the correct popup
        String buttonId = actionEvent.getComponent().getId();
        String result = ChangeRequestConstants.SAVE_FAILURE;

        // initialize check settings
        boolean validYearBuilt          = true;
        boolean validEffectiveYearBuilt = true;
        
        // DEBUG
        if (enableDebug) {
            logger.warning("1 buttonId = '" + buttonId + "'");
        }

        //
        // handle add and edit subpart
        //
        if (buttonId.equalsIgnoreCase("saveAddSubPart") || buttonId.equalsIgnoreCase("saveEditSubPart")) {
            
            Integer yearBuiltValue          = null;
            Integer effectiveYearBuiltValue = null;
            
            //
            // set yearBuiltValue and effectiveYearBuiltValue
            //
            if (buttonId.equalsIgnoreCase("saveAddSubPart")) {
                yearBuiltValue          = getUIXValueAsInteger("it220", enableDebug, "2.1 yearBuilt inputText");
                effectiveYearBuiltValue = getUIXValueAsInteger("it240", enableDebug, "2.2 effectiveYearBuiltValue inputText");
                
            } else if (buttonId.equalsIgnoreCase("saveEditSubPart")){

                yearBuiltValue = getUIXValueAsInteger("it1100", enableDebug, "3.1 yearBuilt inputText");
                if (yearBuiltValue == null) {
                        yearBuiltValue = getUIXValueAsInteger("ot85", enableDebug, "3.2 previousYearBuilt outputText");
                }
                
                effectiveYearBuiltValue = getUIXValueAsInteger("it1200", enableDebug, "3.3 effectiveYearBuiltValue inputText");
                if (effectiveYearBuiltValue == null) {
                        effectiveYearBuiltValue = getUIXValueAsInteger("ot86", enableDebug, "3.4 previouseffectiveYearBuiltValue outputText");
                }
            }
            
            // DEBUG
            if (enableDebug) {
                logger.warning("4 yearBuiltValue = " + yearBuiltValue + "; effectiveYearBuiltValue = " + effectiveYearBuiltValue);
            }

            //
            // set validYearBuilt
            //
            if (yearBuiltValue != null) {
                int minYearBuilt = 1801;
                int maxYearBuilt = effectiveYearBuiltValue == null ? getEndingYear() : effectiveYearBuiltValue;
                
                validYearBuilt = (yearBuiltValue >= minYearBuilt) && (yearBuiltValue <= maxYearBuilt);
                
                // DEBUG
                if (enableDebug) {
                    logger.warning("5 minYearBuilt = " + minYearBuilt + "; maxYearBuilt = " + maxYearBuilt + "; validYearBuilt = " + validYearBuilt);
                }
            }
            
            //
            // set validEffectiveYearBuilt
            //
            if (effectiveYearBuiltValue != null) {
                int minEffectiveYear = yearBuiltValue == null ? 1801 : yearBuiltValue;
                int maxEffectiveYear = getEndingYear();
                
                validEffectiveYearBuilt = (effectiveYearBuiltValue >= minEffectiveYear) && (effectiveYearBuiltValue <= maxEffectiveYear);
                
                // DEBUG
                if (enableDebug) {
                    logger.warning("6 minEffectiveYear = " + minEffectiveYear + "; maxEffectiveYear = " + maxEffectiveYear + "; validEffectiveYearBuilt = " + validEffectiveYearBuilt);
                }
            }
        }
        
        //
        // handle validation error
        //
        if (validYearBuilt && validEffectiveYearBuilt) {
            // DEBUG
            if (enableDebug) {
                logger.warning("7.1 All validation passed");
            }

            // Save subpart execution
            OperationBinding ob = ADFUtils.getOperationBinding("SaveSubPart");
            ob.getParamsMap().put("rpiType", getRpiType());
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                result = (String) ob.getResult();
            }

            // Refresh the grid with the new saved items
            if (result.equalsIgnoreCase(ChangeRequestConstants.SAVE_SUCESS)) {
                // Refresh the iterator binding for change request
                refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
                refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);

                // refresh the UI controls by making a partial trigger on the parent panel group
                RichPanelGroupLayout subpartData = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("subpartData");
                ADFUtils.refreshComponent(subpartData);
            }

            // Hide the subpart popup
            if (result.equalsIgnoreCase(ChangeRequestConstants.SAVE_SUCESS)) {
                String popupId = null;
                if (buttonId.equalsIgnoreCase("saveAddSubPart")) {
                    popupId = "addSubPart_Popup";
                } else if (buttonId.equalsIgnoreCase("saveEditSubPart")) {
                    popupId = "editSubPart_Popup";
                } else if (buttonId.equalsIgnoreCase("saveDeleteSubPart")) {
                    popupId = "deleteSubPart_Popup";
                }
                HidePopup(popupId);
            }
            
        } else {
            // DEBUG
            if (enableDebug) {
                logger.warning("7.2 Validation failed");
            }

            setIsSubPartDataError(Boolean.TRUE);
            setIsInvalidYearBuilt(Boolean.TRUE);

            String messageComponentID = "pgl26"; // default for saveEditSubPart
            if (buttonId.equalsIgnoreCase("saveAddSubPart")) {
                messageComponentID = "pgl27";
            }
            
            RichPanelGroupLayout messageComponent = (RichPanelGroupLayout) JSFUtils.findComponentInRoot(messageComponentID);
            ADFUtils.refreshComponent(messageComponent);
        }
    }

    /**
     * @param actionEvent
     */

    public void CancelSubPart(ActionEvent actionEvent) {

        setIsSubPartDataError(Boolean.FALSE);
        setIsInvalidYearBuilt(Boolean.FALSE);
        setIsInvalidEffYearBuilt(Boolean.FALSE);

        // Get the id of the button that raised this event to properly close the correct popup
        String buttonId = actionEvent.getComponent().getId();

        // Cancel subpart execution
        OperationBinding ob = ADFUtils.getOperationBinding("CancelSubPart");
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_SUB_PART_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.SUB_PART_LIST_ITERATOR, Boolean.FALSE);

        // Refresh the UI controls and hide the composite popup
        String popupId = null;
        if (buttonId.equalsIgnoreCase("cancelAddSubPart")) {
            RichPanelGroupLayout subpart_add_body =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("subpart_add_body");
            clearInputComponents(subpart_add_body);
            popupId = "addSubPart_Popup";
        } else if (buttonId.equalsIgnoreCase("cancelEditSubPart")) {
            RichPanelGroupLayout subpart_edit_body =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("subpart_edit_body");
            clearInputComponents(subpart_edit_body);
            popupId = "editSubPart_Popup";
        } else if (buttonId.equalsIgnoreCase("closeReviewSubPart")) {
            RichPanelGroupLayout subpart_edit_body =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("subpart_review_body");
            clearInputComponents(subpart_edit_body);
            popupId = "reviewSubPart_Popup";
        } else if (buttonId.equalsIgnoreCase("cancelDeleteSubPart")) {
            popupId = "deleteSubPart_Popup";
        }
        HidePopup(popupId);
    }

    /**
     * @param actionEvent
     */
    public void EditComposite(ActionEvent actionEvent) {
        // Edit composite execution
        OperationBinding ob = ADFUtils.getOperationBinding("EditComposite");
        ob.getParamsMap().put("compositeID", getCompositeID());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.COMPOSITE_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.TEMP_COMPOSITE_ITERATOR, Boolean.FALSE);

        // Show the composite edit popup
        ShowPopup("editComposite_Popup");
    }

    /**
     * @param actionEvent
     */
    public void DeleteComposite(ActionEvent actionEvent) {
        // Delete composite execution
        OperationBinding ob = ADFUtils.getOperationBinding("DeleteComposite");
        ob.getParamsMap().put("compositeID", getCompositeID());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.COMPOSITE_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.TEMP_COMPOSITE_ITERATOR, Boolean.FALSE);

        // Show the composite delete popup
        ShowPopup("deleteComposite_Popup");
    }

    /**
     * @param actionEvent
     */
    public void SaveComposite(ActionEvent actionEvent) {
        // Get the id of the button that raised this event to properly close the correct popup
        String buttonId = actionEvent.getComponent().getId();
        String result = ChangeRequestConstants.SAVE_FAILURE;

        // Save composite execution
        OperationBinding ob = ADFUtils.getOperationBinding("SaveComposite");
        ob.getParamsMap().put("rpiType", getRpiType());
        ob.execute();
        if (ob.getErrors().isEmpty()) {
            result = (String) ob.getResult();
        }

        // Refresh the grid with the new saved items
        if (result.equalsIgnoreCase(ChangeRequestConstants.SAVE_SUCESS)) {
            // Refresh the iterator binding for change request
            refreshIterator(ChangeRequestConstants.COMPOSITE_ITERATOR, Boolean.FALSE);
            refreshIterator(ChangeRequestConstants.TEMP_COMPOSITE_ITERATOR, Boolean.FALSE);

            // refresh the UI controls by making a partil trigger on  the parent panel group
            RichPanelGroupLayout compositeData = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("compositeData");
            ADFUtils.refreshComponent(compositeData);
        }

        // Hide the composite popup
        if (result.equalsIgnoreCase(ChangeRequestConstants.SAVE_SUCESS)) {
            String popupId = null;
            if (buttonId.equalsIgnoreCase("saveAddComposite")) {
                popupId = "addComposite_Popup";
            } else if (buttonId.equalsIgnoreCase("saveEditComposite")) {
                popupId = "editComposite_Popup";
            } else if (buttonId.equalsIgnoreCase("saveDeleteComposite")) {
                popupId = "deleteComposite_Popup";
            }
            HidePopup(popupId);
        }
    }

    /**
     * @param actionEvent
     */
    public void CancelComposite(ActionEvent actionEvent) {
        // Get the id of the button that raised this event to properly close the correct popup
        String buttonId = actionEvent.getComponent().getId();

        // Cancel composite execution
        OperationBinding ob = ADFUtils.getOperationBinding("CancelComposite");
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_COMPOSITE_ITERATOR, Boolean.FALSE);

        // Refresh the UI controls and hide the composite popup
        String popupId = null;
        if (buttonId.equalsIgnoreCase("cancelAddComposite")) {
            RichPanelGroupLayout composite_add_body =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("composite_add_body");
            clearInputComponents(composite_add_body);
            popupId = "addComposite_Popup";
        } else if (buttonId.equalsIgnoreCase("cancelEditComposite")) {
            RichPanelGroupLayout composite_edit_body =
                (RichPanelGroupLayout) JSFUtils.findComponentInRoot("composite_edit_body");
            clearInputComponents(composite_edit_body);
            popupId = "editComposite_Popup";
        } else if (buttonId.equalsIgnoreCase("cancelDeleteComposite")) {
            popupId = "deleteComposite_Popup";
        }
        HidePopup(popupId);
    }

    /**
     * @param actionEvent
     */
    public void EditLand(ActionEvent actionEvent) {
        // Edit land data execution
        OperationBinding ob = ADFUtils.getOperationBinding("EditLand");
        ob.getParamsMap().put("landID", getLandID());
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.LAND_ITERATOR, Boolean.FALSE);
        refreshIterator(ChangeRequestConstants.TEMP_LAND_ITERATOR, Boolean.FALSE);

        // Show the land data edit popup
        ShowPopup("editLand_Popup");
    }

    public void SaveLand(ActionEvent actionEvent) {
        
        boolean enableDebug = false;
        
        Double width = getUIXValueAsDouble("it390", enableDebug, "1.1 width inputText");
        Double depth = getUIXValueAsDouble("it400", enableDebug, "1.2 depth inputText");
        Double sqft  = getUIXValueAsDouble("it420", enableDebug, "1.3 sqft inputText");
        Double acres = getUIXValueAsDouble("it430", enableDebug, "1.4 acres inputText");
        
        Double old_width = getUIXValueAsDouble("ot300", enableDebug, "1.5 oldWidth inputText");
        Double old_depth = getUIXValueAsDouble("ot302", enableDebug, "1.6 oldDepth inputText");
        Double old_sqft  = getUIXValueAsDouble("ot181", enableDebug, "1.7 oldSqft inputText");
        Double old_acres = getUIXValueAsDouble("ot183", enableDebug, "1.8 oldAcres inputText");

        String useCodeValue = getUIXValueAsStringr("ot9rp", enableDebug, "1.9 useCode outputText");

        //
        // set dimension status
        //
        boolean width_GreaterThanZero = (width != null) ? (width > 0.0) : ((old_width != null) && (old_width > 0.0));
        boolean depth_GreaterThanZero = (depth != null) ? (depth > 0.0) : ((old_depth != null) && (old_depth > 0.0));
        boolean sqft_GreaterThanZero  = (sqft  != null) ? (sqft  > 0.0) : ((old_sqft  != null) && (old_sqft  > 0.0));
        boolean acres_GreaterThanZero = (acres != null) ? (acres > 0.0) : ((old_acres != null) && (old_acres > 0.0));
        
        //
        // check if condo
        //
        boolean useCodeIsCondo = (useCodeValue != null) &&
                                 (useCodeValue.length() >= 4) &&
                                 ((useCodeValue.charAt(3) == 'c') ||
                                  (useCodeValue.charAt(3) == 'C') ||
                                  (useCodeValue.charAt(3) == 'e') ||
                                  (useCodeValue.charAt(3) == 'E')   );   

        // DEBUG
        if (enableDebug) {
            logger.warning("2.1 width_GreaterThanZero = " + width_GreaterThanZero);
            logger.warning("2.2 depth_GreaterThanZero = " + depth_GreaterThanZero);
            logger.warning("2.3 sqft_GreaterThanZero  = " + sqft_GreaterThanZero );
            logger.warning("2.4 acres_GreaterThanZero = " + acres_GreaterThanZero);
            logger.warning("2.5 useCodeIsCondo = " + useCodeIsCondo);
        }

        //
        // reset flags
        //
        setIsDataError                  (Boolean.FALSE);
        setIsInvalidWidthDepth          (Boolean.FALSE);
        setIsInvalidSqftAcres           (Boolean.FALSE);
        setIsInvalidWidthDepthSqftAcres (Boolean.FALSE);
        setIsDimensionRequired          (Boolean.FALSE);
        
        //
        // check validation
        //
        if (width_GreaterThanZero ^ depth_GreaterThanZero) {
            setIsDataError(Boolean.TRUE);
            setIsInvalidWidthDepth(Boolean.TRUE);

            // DEBUG
            if (enableDebug) {
                logger.warning("3.1 bad width and depth encountered");
            }

        } else if (!width_GreaterThanZero && !depth_GreaterThanZero && !sqft_GreaterThanZero && !acres_GreaterThanZero && !useCodeIsCondo) {
            setIsDataError(Boolean.TRUE);
            setIsDimensionRequired(Boolean.TRUE);

            // DEBUG
            if (enableDebug) {
                logger.warning("3.2 bad empty dimension for non-condo encountered");
            }

        // NOTE: This is allowed now.
        // } else if ((width_GreaterThanZero || depth_GreaterThanZero || sqft_GreaterThanZero) && acres_GreaterThanZero) {
        //     setIsDataError(Boolean.TRUE);
        //     setIsInvalidWidthDepthSqftAcres(Boolean.TRUE);
        }

        //
        // handle error
        //
        if (isDataError) {
            RichPanelGroupLayout messageComponent = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("plam1");
            ADFUtils.refreshComponent(messageComponent);
        }

        else {
            String result = ChangeRequestConstants.SAVE_SUCESS;
            // Save land data execution
            OperationBinding ob = ADFUtils.getOperationBinding("SaveLand");
            ob.getParamsMap().put("rpiType", getRpiType());
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                result = (String) ob.getResult();
            }

            // Refresh the grid with the new saved items
            if (result.equalsIgnoreCase(ChangeRequestConstants.SAVE_SUCESS)) {

                // Hide the land edit popup
                HidePopup("editLand_Popup");

                // Refresh the iterator binding for change request
                refreshIterator(ChangeRequestConstants.LAND_ITERATOR, Boolean.FALSE);
                refreshIterator(ChangeRequestConstants.TEMP_LAND_ITERATOR, Boolean.FALSE);

                // refresh the UI controls by making a partil trigger on  the parent panel group
                RichPanelGroupLayout landData = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("landData");
                ADFUtils.refreshComponent(landData);
            }

        }

    }

    public void CancelLand(ActionEvent actionEvent) {
        // Cancel land data execution
        setIsDataError(Boolean.FALSE);
        setIsInvalidWidthDepth(Boolean.FALSE);
        setIsInvalidSqftAcres(Boolean.FALSE);
        setIsInvalidWidthDepthSqftAcres(Boolean.FALSE);
        setIsDimensionRequired(Boolean.FALSE);

        OperationBinding ob = ADFUtils.getOperationBinding("CancelLand");
        ob.execute();

        // Refresh the iterator binding for change request
        refreshIterator(ChangeRequestConstants.TEMP_LAND_ITERATOR, Boolean.FALSE);

        // Refresh the UI controls and hide the land data popup
        RichPanelGroupLayout land_edit_body = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("land_edit_body");
        clearInputComponents(land_edit_body);
        HidePopup("editLand_Popup");
    }

    private void clearInputComponents(UIComponent uiComponent) {
        // user recursion to look through all children to remove the values in the
        // input components of the parent passed into this method
        List<UIComponent> components = uiComponent.getChildren();
        for (UIComponent component : components) {
            if (component instanceof RichInputText) {
                RichInputText it = (RichInputText) component;
                it.resetValue();
            } else if (component instanceof RichSelectOneRadio) {
                RichSelectOneRadio sor = (RichSelectOneRadio) component;
                sor.resetValue();
            }
            clearInputComponents(component);
        }
    }
    
    private Integer getUIXValueAsInteger(String componentName, boolean enableDebug, String debugString) {
    
        Integer value = null;

        UIXValue richText = (UIXValue) JSFUtils.findComponentInRoot(componentName);
        if ((richText != null) &&
            (richText.getValue() != null) &&
            (richText.getValue().toString() != null)) {

            String richTextValue = richText.getValue().toString();
            
            // DEBUG
            if (enableDebug) {
                logger.warning(debugString + " = '" + richTextValue + "'");
            }
            
            if (!richTextValue.trim().isEmpty()) {
                try {
                   value = Integer.parseInt(richTextValue);
                   
                   // DEBUG
                    if (enableDebug) {
                        logger.warning(debugString + " = (Integer) " + richTextValue);
                    }
                } catch (NumberFormatException e) {
                    // DEBUG
                    if (enableDebug) {
                        logger.warning(debugString + " cannot be parsed to Integer");
                    }
                }
                
            } else if (enableDebug) {
                // DEBUG
                logger.warning(debugString + " = EMPTY");
            }
            
        } else if (enableDebug) {
            // DEBUG
            logger.warning(debugString + " = NULL");
        }
        
        return value;
    }
    
    private Double getUIXValueAsDouble(String componentName, boolean enableDebug, String debugString) {
    
        Double value = null;

        UIXValue richText = (UIXValue) JSFUtils.findComponentInRoot(componentName);
        if ((richText != null) &&
            (richText.getValue() != null) &&
            (richText.getValue().toString() != null)) {

            String richTextValue = richText.getValue().toString();
            
            // DEBUG
            if (enableDebug) {
                logger.warning(debugString + " = '" + richTextValue + "'");
            }
            
            if (!richTextValue.trim().isEmpty()) {
                try {
                   value = Double.parseDouble(richTextValue);
                   
                   // DEBUG
                    if (enableDebug) {
                        logger.warning(debugString + " = (Double) " + richTextValue);
                    }
                } catch (NumberFormatException e) {
                    // DEBUG
                    if (enableDebug) {
                        logger.warning(debugString + " cannot be parsed to Double");
                    }
                }
                
            } else if (enableDebug) {
                // DEBUG
                logger.warning(debugString + " = EMPTY");
            }
            
        } else if (enableDebug) {
            // DEBUG
            logger.warning(debugString + " = NULL");
        }
        
        return value;
    }
    
    private String getUIXValueAsStringr(String componentName, boolean enableDebug, String debugString) {
    
        String value = null;

        UIXValue richText = (UIXValue) JSFUtils.findComponentInRoot(componentName);
        if ((richText != null) &&
            (richText.getValue() != null) &&
            (richText.getValue().toString() != null)) {

            value = richText.getValue().toString();
            
            // DEBUG
            if (enableDebug) {
                logger.warning(debugString + " = '" + value + "'");
            }
            
        } else if (enableDebug) {
            // DEBUG
            logger.warning(debugString + " = NULL");
        }
        
        return value;
    }
    


    public List getRequesterType() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_REQUESTER_TYPE);
    }

    public List getReasonForRequest() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_REASON_FOR_REQUEST);
    }

    public List getPropertyType() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_PROPERTY_TYPE);
    }

    public List getDataChangeSource() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_DATA_CHANGE_SOURCE);
    }

    public List getViewCode() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_VIEW_CODE);
    }

    public List getBpmUserType() {
        return LOVManager.getLov(ChangeRequestConstants.LOV_BPM_USER_TYPE);
    }

    public void setDisablePublicServiceRelated(Boolean showControls) {
        this.disablePublicServiceRelated = showControls;
    }

    public Boolean getDisablePublicServiceRelated() {
        return disablePublicServiceRelated;
    }

    public void setIsClericalUser(Boolean isClericalUser) {
        this.isClericalUser = isClericalUser;
    }

    public Boolean getIsClericalUser() {
        return isClericalUser;
    }

    public void setUserInfoDO(UserInfoDO userInfoDO) {
        this.userInfoDO = userInfoDO;
    }

    public UserInfoDO getUserInfoDO() {
        return userInfoDO;
    }

    public ResourceBundle getPdcrBundle() {
        return pdcrBundle;
    }

    public void setSubPartID(String subPartID) {
        this.subPartID = subPartID;
    }

    public String getSubPartID() {
        return subPartID;
    }

    public void setCompositeID(String compositeID) {
        this.compositeID = compositeID;
    }

    public String getCompositeID() {
        return compositeID;
    }

    public void setLandID(String landID) {
        this.landID = landID;
    }

    public String getLandID() {
        return landID;
    }

    public void setIsExternalUser(Boolean isExternalUser) {
        this.isExternalUser = isExternalUser;
    }

    public Boolean getIsExternalUser() {
        return isExternalUser;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setRpiType(String subPartType) {
        this.rpiType = subPartType;
    }

    public String getRpiType() {
        return rpiType;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setAoid(String aoid) {
        this.aoid = aoid;
    }

    public String getAoid() {
        return aoid;
    }

    public void setIsCreateValidated(Boolean isCreateValidated) {
        this.isCreateValidated = isCreateValidated;
    }

    public Boolean getIsCreateValidated() {
        return isCreateValidated;
    }

    public void setSitusAddressIcon(String situsAddressIcon) {
        this.situsAddressIcon = situsAddressIcon;
    }

    public String getSitusAddressIcon() {
        String returnValue = null;
        String dataSource = (String) ADFUtils.getBoundAttributeValue("dataSource");
        if (null != dataSource) {
            if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
                returnValue = ChangeRequestConstants.BPM_ICONS_RELATIVE_PATH;
            } else {
                returnValue = ChangeRequestConstants.CONTENT_SERVER_ICONS_RELATIVE_PATH;
            }
            returnValue = returnValue.concat(ChangeRequestConstants.SITUS_ADDRESS_IMAGE);
        }
        return returnValue;
    }

    public void setUserGroups(String userGroups) {
        this.userGroups = userGroups;
    }

    public String getUserGroups() {
        return userGroups;
    }

    public String getDisplayMessage() {
        Boolean isPDCRInitiated = (Boolean) ADFUtils.getBoundAttributeValue("isPDCRInitiated");
        Boolean isValidDistrict = (Boolean) ADFUtils.getBoundAttributeValue("isValidDistrict");
        String ainDisplay = (String) ADFUtils.getBoundAttributeValue("ainDisplay");
        displayMessage = getPdcrBundle().getString("MESSAGE_AIN_LABEL").concat(ainDisplay);

        if (!isValidDistrict) {
            displayMessage = displayMessage + " " + getPdcrBundle().getString("INVALID_DISTRICT_MESSAGE");
        } else if (isPDCRInitiated) {
            displayMessage = displayMessage + " " + getPdcrBundle().getString("AIN_LOCKED_MESSAGE");
        }

        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getRpiTypeUserEntered() {
        return ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED;
    }

    public String getRpiTypeFinal() {
        return ChangeRequestConstants.REALPROPERTY_TYPE_FINAL;
    }

    public void setPrintIcon(String printIcon) {
        this.printIcon = printIcon;
    }

    public String getPrintIcon() {
        String returnValue = ChangeRequestConstants.CONTENT_SERVER_ICONS_RELATIVE_PATH;
        this.printIcon = returnValue.concat(ChangeRequestConstants.PRINT_ICON);
        return printIcon;
    }

    public int getEndingYear() {
        // The default ending year is the current year, the UI can override the ending year by passing in the
        // the appropriate override value
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + 1;
    }

    public void setIsInvalidWidthDepth(Boolean isInvalidWidthDepth) {
        this.isInvalidWidthDepth = isInvalidWidthDepth;
    }

    public Boolean getIsInvalidWidthDepth() {
        return isInvalidWidthDepth;
    }

    public void setIsInvalidSqftAcres(Boolean isInvalidSqftAcres) {
        this.isInvalidSqftAcres = isInvalidSqftAcres;
    }

    public Boolean getIsInvalidSqftAcres() {
        return isInvalidSqftAcres;
    }

    public void setIsInvalidWidthDepthSqftAcres(Boolean isInvalidWidthDepthSqftAcres) {
        this.isInvalidWidthDepthSqftAcres = isInvalidWidthDepthSqftAcres;
    }

    public Boolean getIsInvalidWidthDepthSqftAcres() {
        return isInvalidWidthDepthSqftAcres;
    }

    public void setIsDimensionRequired(Boolean isDimensionRequired) {
        this.isDimensionRequired = isDimensionRequired;
    }

    public Boolean getIsDimensionRequired() {
        return isDimensionRequired;
    }

    public void setIsDataError(Boolean isDataError) {
        this.isDataError = isDataError;
    }

    public Boolean getIsDataError() {
        return isDataError;
    }

    public void setIsInvalidYearBuilt(Boolean isInvalidYearBuilt) {
        this.isInvalidYearBuilt = isInvalidYearBuilt;
    }

    public Boolean getIsInvalidYearBuilt() {
        return isInvalidYearBuilt;
    }

    public void setIsSubPartDataError(Boolean isSubPartDataError) {
        this.isSubPartDataError = isSubPartDataError;
    }

    public Boolean getIsSubPartDataError() {
        return isSubPartDataError;
    }

    public void setIsInvalidEffYearBuilt(Boolean isInvalidEffYearBuilt) {
        this.isInvalidEffYearBuilt = isInvalidEffYearBuilt;
    }

    public Boolean getIsInvalidEffYearBuilt() {
        return isInvalidEffYearBuilt;
    }
}
