package gov.laca.amp.propclaimperform.view.backing;

import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.jbo.Row;

public class PropQualConclBean {
    private RichSelectOneChoice approvalType;
    private RichInputText impFBV;
    private RichInputText landFBV;
    private Boolean isFormValidated;
    private RichSelectOneChoice claimResultBind;
    private RichSelectOneChoice typeApprovalBind;
    private RichInputDate reliefDateBind;
    private RichSelectOneChoice reliefTypeBind;
    private RichInputText exclusionAmountBind;
    private RichSelectOneChoice transferStatusBind;
    private RichInputDate transferDate;
    private RichInputText totalFBV;
    private RichInputText partialInterest;
    private RichSelectBooleanCheckbox valFlag;
    private RichInputText valInstructions;


    public PropQualConclBean() {
        super();
    }


    public String onSave() {
        if (getIsFormValidated()) {

            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

         /*   DCIteratorBinding iter1 = bindings.findIteratorBinding("propsPojoIterator");
            iter1.executeQuery();
            DCIteratorBinding iter3 = bindings.findIteratorBinding("conclusionIterator");
            if (iter3 != null) {
                Row row = iter3.getCurrentRow();
                if (row != null) {
                    
                }
            } else
                System.out.println("Conclusion iterator is null");*/

            OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("upsertProps");
            String wuid = (String) ADFUtils.getPageFlowValue("wuid");
            String propsid = (String) ADFUtils.getPageFlowValue("propsid");
            opBind.getParamsMap().put("p0", Integer.valueOf(wuid));
            opBind.getParamsMap().put("p1", Integer.valueOf(propsid));
            String opStatus = (String) opBind.execute();
           /* DCIteratorBinding iter1 = bindings.findIteratorBinding("qualifyQuestionnaireListIterator");
            iter1.executeQuery();
            DCIteratorBinding iter2 = bindings.findIteratorBinding("qualificationIterator");
            iter2.executeQuery();
            DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
            iter3.executeQuery();*/
            String wuidMessage = "Qualification and Conclusion Data Saved Successfully";
            addFacesMessage(FacesMessage.SEVERITY_INFO, wuidMessage);
        }
        return null;
    }


    public void setApprovalType(RichSelectOneChoice approvalType) {
        this.approvalType = approvalType;
    }

    public RichSelectOneChoice getApprovalType() {
        return approvalType;
    }

    public void onApprovalTypeValueChange(ValueChangeEvent valueChangeEvent) {
        Integer val = (Integer) valueChangeEvent.getNewValue();
        if (val == 1) {
            ADFUtils.setPageFlowValue("PartialShow", "YES");
        } else {
            ADFUtils.setPageFlowValue("PartialShow", "NO");
        }
       
        
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding partialInterestApproved = (AttributeBinding) bindings.getControlBinding("partialInterestApproved");
        
        System.out.println("BEFORE partialInterestApproved value --"+partialInterestApproved.getInputValue());
        
        partialInterestApproved.setInputValue(null);
        partialInterest.resetValue();
        
        System.out.println("AFTER partialInterestApproved value --"+partialInterestApproved.getInputValue());
    }


    public void setImpFBV(RichInputText impFBV) {
        this.impFBV = impFBV;
    }

    public RichInputText getImpFBV() {
        return impFBV;
    }

    public void setLandFBV(RichInputText landFBV) {
        this.landFBV = landFBV;
    }

    public RichInputText getLandFBV() {
        return landFBV;
    }

    public void calculateTotalonLandFBV(ValueChangeEvent valueChangeEvent) {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("totalCalcualtion");
        //        opBind.getParamsMap().put("p0", getImpFBV());
        //        opBind.getParamsMap().put("p1", getLandFBV());
        //        String opStatus = (String) opBind.execute();
        //        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        //        iter.executeQuery();


        Double totalFbv = 0.0;
        Double impFBVDbl = 0.0;
        Double landValue = (Double) valueChangeEvent.getNewValue();
        AttributeBinding impValue = (AttributeBinding) bindings.getControlBinding("impFBV");
        if (impValue != null)
            impFBVDbl = (Double) impValue.getInputValue();
        AttributeBinding totalValue = (AttributeBinding) bindings.getControlBinding("totalFBV");
        if (landValue != null && impFBVDbl != null) {
            if (impFBVDbl != null)
                totalFbv = landValue + impFBVDbl;
            totalValue.setInputValue(totalFbv);
        } else if (landValue == null && impFBVDbl != null) {
            totalValue.setInputValue(impFBVDbl);
        } else if (landValue != null && impFBVDbl == null) {
            totalValue.setInputValue(landValue);
        } else if (landValue == null && impFBVDbl == null)
            totalValue.setInputValue(0.0);
    }

    public void calculateTotalOnImpFBV(ValueChangeEvent valueChangeEvent) {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //        OperationBinding opBind = (OperationBinding) bindings.getOperationBinding("totalCalcualtion");
        //        opBind.getParamsMap().put("p0", getImpFBV());
        //        opBind.getParamsMap().put("p1", getLandFBV());
        //        String opStatus = (String) opBind.execute();
        //        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propsPojoIterator");
        //        iter.executeQuery();


        Double landValueDbl = 0.0;
        Double totalFbv = 0.0;
        Double impValue = (Double) valueChangeEvent.getNewValue();
        AttributeBinding landValue = (AttributeBinding) bindings.getControlBinding("landFBV");
        if (landValue != null)
            landValueDbl = (Double) landValue.getInputValue();
        AttributeBinding totalValue = (AttributeBinding) bindings.getControlBinding("totalFBV");

        if (landValueDbl != null && impValue != null) {
            totalFbv = impValue + landValueDbl;
            totalValue.setInputValue(totalFbv);
        } else if (landValueDbl == null && impValue != null) {
            impValue = 0.0;
            //totalFbv = enrolledLandValueDbl + enrolledImpValue;
            totalValue.setInputValue(impValue);
        } else if (landValueDbl != null && impValue == null) {
            totalValue.setInputValue(landValueDbl);
        } else if (landValueDbl == null && impValue == null)
            totalValue.setInputValue(0.0);
    }

    public void onClaimResultValChange(ValueChangeEvent valueChangeEvent) {
        
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding claimResult1 = (AttributeBinding) bindings.getControlBinding("claimResult1");
        System.out.println("claimResult1:" + claimResult1);
        System.out.println("\n******** Selected Value11111: " +
                           resolveExpression("#{bindings.claimResult1.inputValue}"));
        this.setValueToEL("#{bindings.claimResult1.inputValue}", valueChangeEvent.getNewValue()); //Updates the model
        System.out.println("\n******** Selected Value: " +
                           resolveExpression("#{bindings.claimResult1.attributeValue}"));
        String value = (String) resolveExpression("#{bindings.claimResult1.attributeValue}");
        ADFUtils.setPageFlowValue("claimResult", value);
        if(!"Approved".equalsIgnoreCase(value)){
            AttributeBinding approvalType = (AttributeBinding) bindings.getControlBinding("approvalType");
            approvalType.setInputValue(null);
            AttributeBinding partialInterestApproved = (AttributeBinding) bindings.getControlBinding("partialInterestApproved");
            partialInterestApproved.setInputValue(null);
            AttributeBinding reliefDate = (AttributeBinding) bindings.getControlBinding("reliefDate");
            reliefDate.setInputValue(null);
            AttributeBinding reliefType = (AttributeBinding) bindings.getControlBinding("reliefType");
            reliefType.setInputValue(null);
            AttributeBinding exclusionAmount = (AttributeBinding) bindings.getControlBinding("exclusionAmount");
            exclusionAmount.setInputValue(null);
            AttributeBinding transferStatus = (AttributeBinding) bindings.getControlBinding("transferStatus");
            transferStatus.setInputValue(null);
            AttributeBinding landFBV = (AttributeBinding) bindings.getControlBinding("landFBV");
            landFBV.setInputValue(null);
            AttributeBinding impFBV = (AttributeBinding) bindings.getControlBinding("impFBV");
            impFBV.setInputValue(null);
            AttributeBinding totalFBV = (AttributeBinding) bindings.getControlBinding("totalFBV");
            totalFBV.setInputValue(null);
            AttributeBinding valuationFlag = (AttributeBinding) bindings.getControlBinding("valuationFlag");
            valuationFlag.setInputValue(null);
            AttributeBinding valuationInstructions = (AttributeBinding) bindings.getControlBinding("valuationInstructions");
            valuationInstructions.setInputValue(null);
            reset();
            ADFUtils.setPageFlowValue("PartialShow", "NO"); 
            
            System.out.println("--valuationInstructions--"+valuationInstructions.getInputValue());
        }

//        DCIteratorBinding iter3 = bindings.findIteratorBinding("propsPojoIterator");
//        iter3.executeQuery();

//        DCIteratorBinding iter2 = bindings.findIteratorBinding("conclusionIterator");
//        iter2.executeQuery();

    }

    public Object resolveExpression(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression valueExp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setValueToEL(String el, Object val) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        exp.setValue(elContext, val);
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


    public void validateConclusion(ActionEvent actionEvent) {
        setIsFormValidated(Boolean.TRUE);
        String claiResultPF = (String) ADFUtils.getPageFlowValue("claimResult");
        if (getClaimResultBind().getValue() == null) {
            String errorDetail = "Please Select Claim Result";
            addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
            setIsFormValidated(Boolean.FALSE);
        }
        if ("Approved".equalsIgnoreCase(claiResultPF)) {
            if (getTypeApprovalBind().getValue() == null) {
                String errorDetail = "Please Select Approval Type";
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getReliefDateBind().getValue() == null) {
                String errorDetail = "Please enter Effective Date of Releif";
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getReliefTypeBind().getValue() == null) {
                String errorDetail = "Please Select Relief Type";
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getExclusionAmountBind().getValue() == null) {
                String errorDetail = "Please Enter Exclusion Amount";
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
            if (getTransferStatusBind().getValue() == null) {
                String errorDetail = "Please select Transfer Status";
                addFacesMessage(FacesMessage.SEVERITY_ERROR, errorDetail);
                setIsFormValidated(Boolean.FALSE);
            }
        }

    }

    public void setClaimResultBind(RichSelectOneChoice claimResultBind) {
        this.claimResultBind = claimResultBind;
    }

    public RichSelectOneChoice getClaimResultBind() {
        return claimResultBind;
    }

    public void setTypeApprovalBind(RichSelectOneChoice typeApprovalBind) {
        this.typeApprovalBind = typeApprovalBind;
    }

    public RichSelectOneChoice getTypeApprovalBind() {
        return typeApprovalBind;
    }

    public void setReliefDateBind(RichInputDate reliefDateBind) {
        this.reliefDateBind = reliefDateBind;
    }

    public RichInputDate getReliefDateBind() {
        return reliefDateBind;
    }

    public void setReliefTypeBind(RichSelectOneChoice reliefTypeBind) {
        this.reliefTypeBind = reliefTypeBind;
    }

    public RichSelectOneChoice getReliefTypeBind() {
        return reliefTypeBind;
    }

    public void setExclusionAmountBind(RichInputText exclusionAmountBind) {
        this.exclusionAmountBind = exclusionAmountBind;
    }

    public RichInputText getExclusionAmountBind() {
        return exclusionAmountBind;
    }

    public void setTransferStatusBind(RichSelectOneChoice transferStatusBind) {
        this.transferStatusBind = transferStatusBind;
    }

    public RichSelectOneChoice getTransferStatusBind() {
        return transferStatusBind;
    }

    public String validateOnNext() {
        if (getIsFormValidated()) {
            return "gotoReviewSubmit";
        }
        return null;
    }
    
    public void reset(){
        typeApprovalBind.setValue(null);
        reliefDateBind.setValue(null);
        reliefTypeBind.setValue(null);
        exclusionAmountBind.setValue(null);
        transferStatusBind.setValue(null);
        impFBV.setValue(null);
        landFBV.setValue(null);
       // transferDate.setValue(null);
        totalFBV.setValue(null);
        partialInterest.setValue(null);
        valFlag.setValue(null);
        valInstructions.setValue(null);
        typeApprovalBind.resetValue();
        reliefDateBind.resetValue();
        reliefTypeBind.resetValue();
        exclusionAmountBind.resetValue();
        transferStatusBind.resetValue();
        impFBV.resetValue();
        landFBV.resetValue();
        //transferDate.resetValue();
        totalFBV.resetValue();
        partialInterest.resetValue();
        valFlag.resetValue();
        typeApprovalBind.setValue(null);
        reliefDateBind.setValue(null);
        reliefTypeBind.setValue(null);
        exclusionAmountBind.setValue(null);
        transferStatusBind.setValue(null);
        impFBV.setValue(null);
        landFBV.setValue(null);
       // transferDate.setValue(null);
        totalFBV.setValue(null);
        partialInterest.setValue(null);
        valFlag.setValue(null);
        valInstructions.setValue(null);
        
        
    }

    public void setTransferDate(RichInputDate transferDate) {
        this.transferDate = transferDate;
    }

    public RichInputDate getTransferDate() {
        return transferDate;
    }

    public void setTotalFBV(RichInputText totalFBV) {
        this.totalFBV = totalFBV;
    }

    public RichInputText getTotalFBV() {
        return totalFBV;
    }

    public void setPartialInterest(RichInputText partialInterest) {
        this.partialInterest = partialInterest;
    }

    public RichInputText getPartialInterest() {
        return partialInterest;
    }


    public void setValFlag(RichSelectBooleanCheckbox valFlag) {
        this.valFlag = valFlag;
    }

    public RichSelectBooleanCheckbox getValFlag() {
        return valFlag;
    }

    public void setValInstructions(RichInputText valInstructions) {
        this.valInstructions = valInstructions;
    }

    public RichInputText getValInstructions() {
        return valInstructions;
    }
}
