package gov.laca.amp.modules.am.backing;

import gov.laca.amp.common.declcomp.smartaddresssearch.SmartAddressSearchCompComponent;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;

import gov.laca.amp.modules.am.model.util.AddressManagementConstants;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;

import java.net.URISyntaxException;

import java.net.URL;

import java.util.ResourceBundle;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.ServletContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.identitymanagement.UserProfile;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;

import oracle.javatools.resourcebundle.BundleFactory;

public class OnlineAddressChgReqBean {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private RichInputText inputAin;
    private Boolean isSelected = Boolean.FALSE;
    private SmartAddressSearchCompComponent inputNewMailingAddr;
    private RichInputText propOwnerName;
    private Boolean isFormValidated;
    private RichInputText agentName;
    private RichInputText digitalSignature;
    // private RichInputDate date;
    private static final ResourceBundle addressManagementBundle =
        BundleFactory.getBundle(AddressManagementConstants.BUNDLE_VIEW_LOCATION);
    private RichInputText dayPhone;
    private RichTable ainTable;
    private static transient ADFLogger logger = ADFLogger.createADFLogger(OnlineAddressChgReqBean.class);

    public OnlineAddressChgReqBean() {
        super();
    }

    public String addAIN() {
        logger.entering("OnlineAddressChgReqBean.class", "addAIN()");
        //RichInputText ainInput = (RichInputText)JSFUtils.findComponentInRoot("ainInput");
        if (getInputAin().getValue() == null) {
            String errorDetail = getAddressManagementBundle().getString("AIN_EMPTY");
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            return null;
        } else if (!getInputAin().getValue()
                                 .toString()
                                 .matches("\\d{10}|\\d{4}(?:-\\d{3}){2}")) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "AIN is invalid. Please enter a valid AIN.");
            return null;
        }
        String primaryAin = (String) AdfFacesContext.getCurrentInstance()
                                                    .getPageFlowScope()
                                                    .get("primaryAin");
        String pageNavigation = (String) AdfFacesContext.getCurrentInstance()
                                                    .getPageFlowScope()
                                                    .get("pageNav");
        System.out.println("Add AIN 85============="+primaryAin+"===="+pageNavigation);

        String ain = (String) getInputAin().getValue()
                                           .toString()
                                           .trim();
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("duplicateAinCheck");
        op.getParamsMap().put("ain", ain);
        String msg = (String) op.execute();
        if ("failure".equals(msg)) {
            String errorDetail = getAddressManagementBundle().getString("AIN_DUPLICATE");
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
        } else {
            DCBindingContainer bindings1 = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding op1 = (OperationBinding) bindings1.getOperationBinding("fetchCurrentOwnerShipInfo");
            op1.getParamsMap().put("ain", ain);
            op1.getParamsMap().put("primaryAin", primaryAin);
            op1.getParamsMap().put("pageNavigation", pageNavigation);
            //  op1.getParamsMap().put("aoid", "11326042");
            msg = (String) op1.execute();
            if (msg != null && msg != "") {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, msg);
                return null;
            }

            DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("ainsPojoIterator");
            iter.executeQuery();
            DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("addressManagementPojoIterator");
            iter1.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(ainTable);
        }

        logger.exiting("OnlineAddressChgReqBean.class", "addAIN()");
        return null;
    }

    public void setInputAin(RichInputText inputAin) {
        this.inputAin = inputAin;
    }

    public RichInputText getInputAin() {
        return inputAin;
    }

    public String onClose() {
        logger.entering("OnlineAddressChgReqBean.class", "onClose()");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("resetData");
        op.execute();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("addressManagementPojoIterator");
        iter.executeQuery();
        logger.exiting("OnlineAddressChgReqBean.class", "onClose()");
        return null;
    }

    public String onPrint() {
        // Add event code here...
        return null;
    }

    public String onSubmit() {
        logger.entering("OnlineAddressChgReqBean.class", "onSubmit()");
        if (getIsFormValidated()) {
            String newMailingAddress = inputNewMailingAddr.getValue();
            newMailingAddress = newMailingAddress.toUpperCase();
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding op = (OperationBinding) bindings.getOperationBinding("saveAddressWorkUnit");
            op.getParamsMap().put("newMailingAddr", newMailingAddress);
            String docSetId = (String) op.execute();
            logger.fine("docSetId=====" + docSetId);
            if (docSetId != null && docSetId != "") {

                AdfFacesContext.getCurrentInstance()
                               .getPageFlowScope()
                               .put("docSetId", docSetId);
                return "confirm";
            } else {
                logger.severe("saveAddressWorkUnit returned no docSetId... show error" + docSetId);
            }

            String wuidMessage = "Errors submitting Mailing Address Change Request";
            addFacesMessage(FacesMessage.SEVERITY_ERROR, wuidMessage);
            logger.exiting("OnlineAddressChgReqBean.class", "onSubmit()");
        }
        return null;
    }

    public String removeAin() {
        logger.entering("OnlineAddressChgReqBean.class", "removeAin()");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("ainsPojoIterator");
        int currentRowIndex = iter.getCurrentRowIndexInRange();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("removeAin");
        op.getParamsMap().put("currentRowIndex", currentRowIndex);
        op.execute();
        DCIteratorBinding iter1 = (DCIteratorBinding) bindings.findIteratorBinding("ainsPojoIterator");
        iter1.executeQuery();
        DCIteratorBinding iter2 = (DCIteratorBinding) bindings.findIteratorBinding("addressManagementPojoIterator");
        iter2.executeQuery();
        logger.exiting("OnlineAddressChgReqBean.class", "removeAin()");
        return null;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void isAuthorizedAgent(ValueChangeEvent valueChangeEvent) {
        isSelected = ((Boolean) valueChangeEvent.getNewValue()).booleanValue();
    }

    public void setInputNewMailingAddr(SmartAddressSearchCompComponent inputNewMailingAddr) {
        this.inputNewMailingAddr = inputNewMailingAddr;
    }

    public SmartAddressSearchCompComponent getInputNewMailingAddr() {
        return inputNewMailingAddr;
    }

    public void initilize() {
        logger.entering("OnlineAddressChgReqBean.class", "initilize()");
        String primaryAin = (String) ADFContext.getCurrent()
                                        .getPageFlowScope()
                                        .get("primaryAin");
        System.out.println("On initilize=====212-===="+primaryAin);
        ADFContext.getCurrent().getPageFlowScope().put("primaryAin",primaryAin);
        String pageNav = (String) ADFContext.getCurrent()
                                        .getPageFlowScope()
                                        .get("pageNav");
        System.out.println("On initilize=====217-===="+pageNav);
        ADFContext.getCurrent().getPageFlowScope().put("pageNav",pageNav);
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("onPageLoad");
        op.execute();
        bindings.findIteratorBinding("addressManagementPojoIterator").executeQuery();
        logger.exiting("OnlineAddressChgReqBean.class", "initilize()");
    }

    public void validateAddressChange(ActionEvent actionEvent) {
        logger.entering("OnlineAddressChgReqBean.class", "validateAddressChange()");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("validateAinInForm");
        String msg = (String) op.execute();
        if ("failure".equals(msg)) {
            String errorDetail = getAddressManagementBundle().getString("AIN_REQ");
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            setIsFormValidated(Boolean.FALSE);
        } else {
            setIsFormValidated(Boolean.TRUE);
            if (inputNewMailingAddr.getValue() == null) {
                String errorDetail = getAddressManagementBundle().getString("NEWMAILINGADDRESS_REQ");
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getPropOwnerName().getValue() == null) {
                String errorDetail = getAddressManagementBundle().getString("PROPERTOWNERNAME_REQ");
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getDayPhone().getValue() != null) {
                if (!getDayPhone().getValue()
                                  .toString()
                                  .trim()
                                  .matches("^\\+(?:[0-9] ?){6,14}[0-9]$|^((?:\\+|00)[17](?: |\\-)?|(?:\\+|00)[1-9]\\d{0,2}(?: |\\-)?|(?:\\+|00)1\\-\\d{3}(?: |\\-)?)?(0\\d|\\([0-9]{3}\\)|[0-9]{0,3})(?:((?: |\\-)[0-9]{2}){4}|((?:[0-9]{2}){4})|((?: |\\-)[0-9]{3}(?: |\\-)[0-9]{4})|([0-9]{7}))$")) {
                    addFacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Enter Valid Phone Number. Eg: 123 456 7890, 4081231234, +1 1234567890, +12 123456789");
                    setIsFormValidated(Boolean.FALSE);
                }
            }
            //        if(getDayPhone().getValue()==null){
            //                String errorDetail = getAddressManagementBundle().getString("DAYPHONE_REQ");
            //                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            //                setIsFormValidated(Boolean.FALSE);
            //            }
            //        if(getAgentName().getValue()==null && !getAgentName().isDisabled()){
            //            String errorDetail = getAddressManagementBundle().getString("AGENTNAME_REQ");
            //            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            //            setIsFormValidated(Boolean.FALSE);
            //        }
            //        if(getDigitalSignature().getValue()==null){
            //            String errorDetail = getAddressManagementBundle().getString("DIGITALSIGNATURE_REQ");
            //            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            //            setIsFormValidated(Boolean.FALSE);
            //        }

            UserProfile upf = ADFContext.getCurrent()
                                        .getSecurityContext()
                                        .getUserProfile();

            String fn = upf.getFirstName();
            if (fn == null || fn == "") {
                addFacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Logged In user does not have First Name. Please update user profile.");
                setIsFormValidated(Boolean.FALSE);
            }

            String email = (String) ((AttributeBinding) bindings.getControlBinding("email")).getInputValue();
            if (email == null || email == "") {
                addFacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Logged In user does not have Email. Please update user profile.");
                setIsFormValidated(Boolean.FALSE);
            }

        }
        logger.exiting("OnlineAddressChgReqBean.class", "validateAddressChange()");
    }

    public void setPropOwnerName(RichInputText propOwnerName) {
        this.propOwnerName = propOwnerName;
    }

    public RichInputText getPropOwnerName() {
        return propOwnerName;
    }

    protected void addFacesMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void setIsFormValidated(Boolean isFormValidated) {
        this.isFormValidated = isFormValidated;
    }

    public Boolean getIsFormValidated() {
        return isFormValidated;
    }

    public void setAgentName(RichInputText agentName) {
        this.agentName = agentName;
    }

    public RichInputText getAgentName() {
        return agentName;
    }

    public void setDigitalSignature(RichInputText digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public RichInputText getDigitalSignature() {
        return digitalSignature;
    }

    //    public void setDate(RichInputDate date) {
    //        this.date = date;
    //    }
    //
    //    public RichInputDate getDate() {
    //        return date;
    //    }
    public ResourceBundle getAddressManagementBundle() {
        return addressManagementBundle;
    }

    public void setDayPhone(RichInputText dayPhone) {
        this.dayPhone = dayPhone;
    }

    public RichInputText getDayPhone() {
        return dayPhone;
    }

    public void setAinTable(RichTable ainTable) {
        this.ainTable = ainTable;
    }

    public RichTable getAinTable() {
        return ainTable;
    }

    public String accessLink() {
        logger.exiting("OnlineAddressChgReqBean.class", "accessLink()");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("fetchAccessLink");
        String accessLinkUrl = (String) op.execute();
        logger.info("accessLinkUrl from service  " + accessLinkUrl);
        if (accessLinkUrl != null) {
            if (!accessLinkUrl.contains("?")) {
                accessLinkUrl = accessLinkUrl + "?";
            }

            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            String serverName = ext.getRequestServerName();
            String scheme = ext.getRequestScheme();
            int port = ext.getRequestServerPort();
            String contextPath = ext.getRequestContextPath();
            try {
                URI uri = new URI(scheme, null, serverName, port, contextPath, null, null);
                accessLinkUrl = accessLinkUrl + "&landingPage=" + uri.toASCIIString() + "/portal/assessorportal";

                URL u = new URL(accessLinkUrl);
                logger.info("URL params " + u.getFile() + "===" + u.getPath());

                u = new URL(scheme, serverName, port, "/signix" + u.getFile());
                logger.info("new url... " + u.toString());
                accessLinkUrl = u.toString();
            } catch (Exception e) {
                logger.severe(e);
                e.printStackTrace();
            }


        }
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
                                                                     .getExternalContext()
                                                                     .getContext();
        String v = servletContext.getInitParameter("org.apache.myfaces.trinidad.security.FRAME_BUSTING");
        String m = servletContext.getInitParameter("oracle.adf.view.ALLOWED_ORIGINS");
        String n = servletContext.getInitParameter("oracle.adf.view.rich.automation.ENABLED");
        String t = servletContext.getInitParameter("org.apache.myfaces.trinidad.CLIENT_STATE_MAX_TOKENS");
        logger.info("FRAME_BUSTING:ALLOWED_ORIGINS:AUTOMATION:TOKEN.. " + v + ":" + m + ":" + n + ":" + t);
        logger.info("accessLinkUrl iframe url  " + accessLinkUrl);
        AdfFacesContext.getCurrentInstance()
                       .getPageFlowScope()
                       .put("accessLinkUrl", accessLinkUrl);
        logger.exiting("OnlineAddressChgReqBean.class", "accessLink()");
        return null;
    }

    //        public String getApplicationUri() {
    //          try {
    //
    //            URI uri = new URI(ext.getRequestScheme(),
    //                  null, ext.getRequestServerName(), ext.getRequestServerPort(),
    //                  ext.getRequestContextPath(), null, null);
    //            return uri.toASCIIString();
    //          } catch (URISyntaxException e) {
    //            throw new FacesException(e);
    //          }
    //        }
    public String onHome() {
        FacesContext ctxt = FacesContext.getCurrentInstance();
        ExternalContext ectx = ctxt.getExternalContext();
        String url = ectx.getRequestContextPath() + "/portal/assessorportal/pages_assessorhome";
        try {
            ectx.redirect(url);
            ctxt.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
