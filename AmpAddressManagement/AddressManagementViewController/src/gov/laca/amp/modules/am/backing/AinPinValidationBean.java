package gov.laca.amp.modules.am.backing;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.util.WCUtils;
import gov.laca.amp.modules.am.model.util.AddressManagementConstants;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.javatools.resourcebundle.BundleFactory;

import oracle.mds.persistence.ResourceNotFoundException;

public class AinPinValidationBean {
    private RichInputText ain;
    private RichInputText pin;
    private static final ResourceBundle addressManagementBundle =
        BundleFactory.getBundle(AddressManagementConstants.BUNDLE_VIEW_LOCATION);
    public AinPinValidationBean() {
        super();
    }

    public String onAinPinValidationSubmit() throws AmpException {
        String ain = (String) getAin().getValue().toString().trim();
        String pin = (String) getPin().getValue().toString().trim();
        if (ain == null) {
            String errorDetail = getAddressManagementBundle().getString("AIN_EMPTY");
            FacesMessage errorMessage = new FacesMessage(errorDetail);
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc1 = FacesContext.getCurrentInstance();
            fc1.addMessage(null, errorMessage);
            return null;
        } else if (!ain.matches("\\d{10}|\\d{4}(?:-\\d{3}){2}")) {
            FacesMessage errorMessage = new FacesMessage("AIN is invalid. Please enter a valid AIN.");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc1 = FacesContext.getCurrentInstance();
            fc1.addMessage(null, errorMessage);
            return null;
        }
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("validateAinPin");
        op.getParamsMap().put("ain", ain);
        op.getParamsMap().put("pin", pin);
        String msg = (String) op.execute();
        if(msg == null){
            String wuidMessage = "Please enter valid AIN and PIN combination";
            FacesMessage errorMessage = new FacesMessage(wuidMessage);
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc1 = FacesContext.getCurrentInstance();
            fc1.addMessage(null, errorMessage);
            return null;
        }
        else if(msg != null && msg.equalsIgnoreCase("SUCCESS")){
            StringBuffer sb = new StringBuffer();
            sb.append("primaryAin=").append(ain);
            String pageId = "pages_onlinemailchangeexternal";
            WCUtils.redirectToPageLinkId(pageId, sb.toString());
        }
        return null;
    }
    public void setAin(RichInputText ain) {
        this.ain = ain;
    }

    public RichInputText getAin() {
        return ain;
    }

    public void setPin(RichInputText pin) {
        this.pin = pin;
    }

    public RichInputText getPin() {
        return pin;
    }
    
    protected void addFacesMessage(FacesMessage.Severity severity, String msg) {
        FacesMessage fm = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }
    
    public ResourceBundle getAddressManagementBundle() {
        return addressManagementBundle;
    }


}
