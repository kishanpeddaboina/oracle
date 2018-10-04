package gov.laca.amp.propclaimperform.view.backing;


import gov.laca.amp.common.model.triliumaddressvalidation.data.AddressPojo;
import gov.laca.amp.common.model.triliumaddressvalidation.dc.AddressValidationDC;
import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.proxy.soap.addressvalidation.client.FaultMessage;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.JboException;

public class PropClaimAddressValidatnBean {
    public PropClaimAddressValidatnBean() {
        super();
    }
    private RichPanelGroupLayout addressValidnForm;
    private RichInputText streetAddr;
    private RichInputText city;
    private RichInputText state;
    private RichInputText zipCode;
    private RichInputText country;
    List<SelectItem> addressList = null;
    private String matchLevelDesc;
    private String buttonLabel;
    private String styleClass;
    List<String> options = new ArrayList<String>();
    private String addressData;

    public void setStreetAddr(RichInputText streetAddr) {
        this.streetAddr = streetAddr;
    }

    public RichInputText getStreetAddr() {
        return streetAddr;
    }

    public void setCity(RichInputText city) {
        this.city = city;
    }

    public RichInputText getCity() {
        return city;
    }

    public void setState(RichInputText state) {
        this.state = state;
    }

    public RichInputText getState() {
        return state;
    }

    public void setZipCode(RichInputText zipCode) {
        this.zipCode = zipCode;
    }

    public RichInputText getZipCode() {
        return zipCode;
    }

    public void setCountry(RichInputText country) {
        this.country = country;
    }

    public RichInputText getCountry() {
        return country;
    }

    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public List<SelectItem> getAddressList() {
        addressList = new ArrayList<SelectItem>();
        //options = addrPojo.getAddressOptions();
        options = (List<String>) AdfFacesContext.getCurrentInstance()
                                                .getPageFlowScope()
                                                .get("ADDR_OPTION");
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                addressList.add(new SelectItem(options.get(i), options.get(i)));

            }
        }
        //  }
        //Adding for testin
        SelectItem selIt = null;
        for (int i = 0; i < addressList.size(); i++) {
            selIt = addressList.get(i);
        }
        return addressList;
    }

    public void setMatchLevelDesc(String matchLevelDesc) {
        this.matchLevelDesc = matchLevelDesc;
    }

    public String getMatchLevelDesc() {
        return matchLevelDesc;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setOptions(List options) {
        this.options = options;
    }

    public List getOptions() {
        return options;
    }

    public void setAddressData(String addressData) {
        this.addressData = addressData;
    }

    public String getAddressData() {
        return addressData;
    }


    /**
     * @param actionEvent
     * @throws JboException
     */
    public void validateAddressForm(ActionEvent actionEvent) throws JboException {
        // Add event code here...
        String buttonText = "Select";
        String streetAddr = (String) ADFContext.getCurrent()
                                               .getPageFlowScope()
                                               .get("streetAddress");
        String city = (String) ADFContext.getCurrent()
                                         .getPageFlowScope()
                                         .get("city");
        String zipCode = (String) ADFContext.getCurrent()
                                            .getPageFlowScope()
                                            .get("zipCode");
        String state = (String) ADFContext.getCurrent()
                                          .getPageFlowScope()
                                          .get("state");
        String country = (String) ADFContext.getCurrent()
                                            .getPageFlowScope()
                                            .get("country");
        AddressPojo addrPojo = new AddressPojo();
        addrPojo.setCity(city);
        addrPojo.setState(state);
        addrPojo.setStreetAddress(streetAddr);
        addrPojo.setZipCode(zipCode);
        addrPojo.setCountry(country);

        AddressValidationDC addrValidnDC = new AddressValidationDC();
        String descMarker = "DESC:";
        try {
            options = addrValidnDC.fetchValidAddress(addrPojo);
            if (options != null) {
                for (int i = 0; i < options.size(); i++) {
                    if (options.get(i).startsWith("DESC:")) {
                        String levelDesc = options.get(i).substring(descMarker.length(), options.get(i).length());
                        System.out.println("Actual Match level description is ->" + levelDesc);
                        //               ADFContext.getCurrent().getPageFlowScope().put("MATCH_LEVEL_DESC",options.get(i).substring(0,descMarker.length()-1));
                        setMatchLevelDesc(levelDesc);
                        options.remove(i);
                    }
                }

                if (options.size() < 1) {
                    buttonText = "Ok";
                    System.out.println("Options is < 1 so setting as OK");

                } else {
                    System.out.println("Options size not less than 1 so shud be select");
                    //setButtonLabel("Select");
                }
                setButtonLabel(buttonText);
                System.out.println("Final data from Service : Debug Before setting it on the list on Select option popp up:");
                for (int i = 0; i < options.size(); i++) {
                    System.out.println("options " + i + "->" + options.get(i));
                }
                AdfFacesContext.getCurrentInstance()
                               .getPageFlowScope()
                               .put("ADDR_OPTION", options);
            }
        } catch (AmpException e) {
            String code = e.getErrCode();
            System.out.println("Error code " + code);
            System.out.println("Error message " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            JboException ex = new JboException("Cannot validate address at this time");
            BindingContext bctx = BindingContext.getCurrent();
            ((DCBindingContainer) bctx.getCurrentBindingsEntry()).reportException(ex);
            //e.printStackTrace();
        } catch (FaultMessage e) {
            e.printStackTrace();
        }
        // addrValidnDC.
        System.out.println("Address options from service" + options.size());
        //options.

    }

    public static void addFacesMessage(String componentId, String summary, String detail) {
        UIComponent root = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent component = root.findComponent(componentId);
        FacesContext.getCurrentInstance()
            .addMessage(component.getClientId(FacesContext.getCurrentInstance()),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }


    /**Method to get value from Expression (EL)
     * @param data
     * @return
     */
    public String getValueFrmExpression(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = null;
        Object obj = valueExp.getValue(elContext);
        if (obj != null) {
            Message = obj.toString();
        }
        return Message;
    }


    /**Method to set value in Expression (EL)
     * @param el
     * @param val
     */
    public void setvalueToExpression(String el, Object val) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        exp.setValue(elContext, val);
    }


    public void setAddressValidnForm(RichPanelGroupLayout addressValidnForm) {
        this.addressValidnForm = addressValidnForm;
    }

    public RichPanelGroupLayout getAddressValidnForm() {
        return addressValidnForm;
    }


}
