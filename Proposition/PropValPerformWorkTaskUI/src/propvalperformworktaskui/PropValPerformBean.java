package propvalperformworktaskui;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.security.SecurityHelper;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import java.math.RoundingMode;

import java.text.DecimalFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;

public class PropValPerformBean extends AmpManagedBean {
    //private RichPanelGroupLayout panelGrplayoutBinding;

    public PropValPerformBean() {
        super();
    }
    
    public String saveAction() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding workUnitId = (AttributeBinding) bindings.getControlBinding("WorkUnitId");

        AttributeBinding isValueChangeReq = (AttributeBinding) bindings.getControlBinding("isValueChangedRequired");
        
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("updatePropsPayload");

        Boolean status = (Boolean) op.execute();
        //Adding for testing
        
       System.out.println("Valuation Save Success::");
        return null;
    }
    
    //private RichPanelGroupLayout panelGrpBinding;
    /**
     * @return
     */
    public String submitAction() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding workUnitId = (AttributeBinding) bindings.getControlBinding("WorkUnitId");
        AttributeBinding businessFunctionId = (AttributeBinding) bindings.getControlBinding("BusinessFunctionId");
        AttributeBinding isValueChangeReq = (AttributeBinding) bindings.getControlBinding("isValueChangedRequired");
        
        OperationBinding op = (OperationBinding) bindings.getOperationBinding("updatePropsPayload");

        Boolean status = (Boolean) op.execute();
        
        
//        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("upsertProps");
//
//        Boolean updatePropsstatus = (Boolean) op1.execute();
        
//        Integer workUnitId = null;
        Integer propsId = null;
        Integer wuid = null;
        if(workUnitId.getInputValue()!= null) {
            wuid =Integer.parseInt(workUnitId.getInputValue().toString());
        }
        if(businessFunctionId.getInputValue() !=null){
       propsId = Integer.parseInt(businessFunctionId.getInputValue().toString());
        }
       System.out.println("wuid---"+wuid+",propsId ::"+propsId);

        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("upsertProps"); //Uncomment this
        op1.getParamsMap().put("p0", wuid);
        op1.getParamsMap().put("p1", propsId);
        
        //Adding for testing
        
        if (op.getErrors().isEmpty()) {
            if (status != null) {
                if (status) { 
                    addEventParameters();
                    return "closeTaskFlow";
                }
            }
        }
        return null;
    }


    /**This method adds the Event params in the payload when situs address or mailing address investigation is needed.
     * @return
     */
    public String addEventParameters() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding paramIter = (DCIteratorBinding) bindings.findIteratorBinding("ParameterIterator");
        paramIter.executeQuery();

        AttributeBinding isCancelRecomm = (AttributeBinding) bindings.getControlBinding("isRecommendedToCancelPropClaim");
        Boolean isCancellationRecommendedFrmPage = (Boolean) isCancelRecomm.getInputValue();     
//        AttributeBinding cancellationReasonFrmBinding= (AttributeBinding)bindings.getControlBinding("propsClaimCancelReasonLov");
        AttributeBinding cancellationReasonFrmBinding= (AttributeBinding)bindings.getControlBinding("reason3");

        String cancellationReasonFrmPage = (String) cancellationReasonFrmBinding.getInputValue();   
        Boolean isCancellationRecParam = Boolean.FALSE; //Check if this IsCancellationRecommended element is already present
        Boolean cancelReason = Boolean.FALSE;
        Boolean isValuationRecParam = Boolean.FALSE;

        Row rows[] = paramIter.getAllRowsInRange();
        for (Row row : rows) {
            String name = (String) row.getAttribute("Name");
            if ("IsCancellationRecommended".equals(name)) {
                isCancellationRecParam = Boolean.TRUE;
            }
            if ("CancellationReason".equals(name)) {
                cancelReason = Boolean.TRUE;
            }
            if("IsValuationRequired".equals(name)){
                isValuationRecParam = Boolean.TRUE;
            }
        }
        if (!cancelReason) {
            OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("CreateInsert");
            op1.execute();
            Row rw3 = paramIter.getCurrentRow();           
            rw3.setAttribute("Name", "CancellationReason");
            rw3.setAttribute("Value", cancellationReasonFrmPage);           
            paramIter.executeQuery();
        }
        else{
                Row rows1[] = paramIter.getAllRowsInRange();
                for (Row row : rows1) {
                    String name = (String) row.getAttribute("Name");
                    if ("CancellationReason".equals(name)) {
                        row.setAttribute("Value", cancellationReasonFrmPage);
                    }
                }
//                OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("update");
//                op1.execute();
            }
        
        if (!isCancellationRecParam) {          
            if (isCancellationRecommendedFrmPage) {
                //Create IsCancellationRecommended
                OperationBinding op = (OperationBinding) bindings.getOperationBinding("CreateInsert");
                op.execute();
                Row rw1 = paramIter.getCurrentRow();
                rw1.setAttribute("Name", "IsCancellationRecommended");
                rw1.setAttribute("Value", "Yes");
                paramIter.executeQuery();
            } else {
                OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("CreateInsert");
                op1.execute();
                Row rw2 = paramIter.getCurrentRow();
                rw2.setAttribute("Name", "IsCancellationRecommended");
                rw2.setAttribute("Value", "No");
                paramIter.executeQuery();
            }
        }
        else{
                Row rows1[] = paramIter.getAllRowsInRange();
                for (Row row : rows1) {
                    String name = (String) row.getAttribute("Name");
                    if ("IsCancellationRecommended".equals(name)) {
                        if(isCancellationRecommendedFrmPage)
                            row.setAttribute("Value", "Yes");
                        else
                            row.setAttribute("Value", "No");
                    }
                }
//                OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("update");
//                op1.execute();
            }
        
        if (!isValuationRecParam) {
            if (isCancellationRecommendedFrmPage || cancellationReasonFrmPage!=null){
                OperationBinding op = (OperationBinding) bindings.getOperationBinding("CreateInsert");
                op.execute();
                Row rw1 = paramIter.getCurrentRow();
                rw1.setAttribute("Name", "IsValuationRequired");
                rw1.setAttribute("Value", "No");
                paramIter.executeQuery(); 
            }
        }else{
            if (isCancellationRecommendedFrmPage || cancellationReasonFrmPage!=null){
            Row rows1[] = paramIter.getAllRowsInRange();
            for (Row row : rows1) {
                String name = (String) row.getAttribute("Name");
                if ("IsValuationRequired".equals(name)) {
                    row.setAttribute("Value", "No");
                }
            }
//            OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("update");
//            op1.execute();
            }
        }
        OperationBinding op0 = (OperationBinding) bindings.getOperationBinding("update");
        op0.execute();
        //displaying all parameters
        paramIter.executeQuery();
        Row rows1[] = paramIter.getAllRowsInRange();
//        for (Row row : rows1) {
//            String name = (String) row.getAttribute("Name");
//            String value = (String) row.getAttribute("Value");
//        }
//System.out.println("before calling OOTB SubmitForApproval");
        OperationBinding op1 = (OperationBinding) bindings.getOperationBinding("SubmitForApproval");
        op1.execute();
        return "closeTaskFlow";
    }

    public void enrollLandValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
       
        Double totalFbv=0.0;
        Double impFBVDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();       
        Double enrolledLandValue=(Double)valueChangeEvent.getNewValue();      
        AttributeBinding enrolledImpValue = (AttributeBinding) bindings.getControlBinding("enrolledImpValue");         
        if(enrolledImpValue!=null)
            impFBVDbl = (Double) enrolledImpValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue");
    

        if(enrolledLandValue!=null && impFBVDbl!=null){    
            if(impFBVDbl!=null)
                totalFbv = enrolledLandValue + impFBVDbl;
            totalEnrolledValue.setInputValue(totalFbv);
        }
        else if(enrolledLandValue==null && impFBVDbl!=null){ 
            totalEnrolledValue.setInputValue(impFBVDbl); 
        }
        else if(enrolledLandValue!=null && impFBVDbl==null){      
            totalEnrolledValue.setInputValue(enrolledLandValue); 
        }else if(enrolledLandValue==null && impFBVDbl==null){
                totalEnrolledValue.setInputValue(null);      
        }
    }
    
    public void enrollImpValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double enrolledLandValueDbl=0.0;
        Double totalFbv=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double enrolledImpValue=(Double)valueChangeEvent.getNewValue();        
        AttributeBinding enrolledLandValue = (AttributeBinding) bindings.getControlBinding("enrolledLandValue");   
        if(enrolledLandValue!=null)
            enrolledLandValueDbl = (Double) enrolledLandValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue");
            
            
           
                
        if(enrolledLandValueDbl!=null && enrolledImpValue!=null){  
            totalFbv = enrolledImpValue + enrolledLandValueDbl;
            totalEnrolledValue.setInputValue(totalFbv);
        }
        else if(enrolledLandValueDbl==null && enrolledImpValue!=null){  
            enrolledImpValue=0.0;          
            //totalFbv = enrolledLandValueDbl + enrolledImpValue;
            totalEnrolledValue.setInputValue(enrolledImpValue); 
        }
        else if(enrolledLandValueDbl!=null && enrolledImpValue==null){  
            totalEnrolledValue.setInputValue(enrolledLandValueDbl); 
        }else if(enrolledLandValueDbl==null && enrolledImpValue==null)
                totalEnrolledValue.setInputValue(null);   
        }
    
    public void additionlLandValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double impFBVDbl=0.0;
        Double totalFbv=0.0;
        Double enrolledImpValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double enrolledLandValue=(Double)valueChangeEvent.getNewValue();  
        AttributeBinding enrolledImpValue = (AttributeBinding) bindings.getControlBinding("enrolledImpValue1");
        if(enrolledImpValue!=null)
            enrolledImpValueDbl = (Double) enrolledImpValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue1");
        if(enrolledLandValue!=null && enrolledImpValueDbl!=null){  
            impFBVDbl = (Double) enrolledImpValue.getInputValue();
            totalFbv = enrolledLandValue + impFBVDbl;
            totalEnrolledValue.setInputValue(totalFbv);
        }
        else if(enrolledLandValue==null && enrolledImpValueDbl!=null){  
            totalEnrolledValue.setInputValue(enrolledImpValueDbl);          
        }
        else if(enrolledLandValue!=null && enrolledImpValueDbl==null){                      
            totalEnrolledValue.setInputValue(enrolledLandValue); 
        }else if(enrolledLandValue==null && enrolledImpValueDbl==null)
                totalEnrolledValue.setInputValue(null);      
        }
    
    public void additionlImpValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double totalFbv=0.0;
        Double enrolledLandValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double enrolledImpValue=(Double)valueChangeEvent.getNewValue();
        AttributeBinding enrolledLandValue = (AttributeBinding) bindings.getControlBinding("enrolledLandValue1");   
        if(enrolledLandValue!=null)
              enrolledLandValueDbl = (Double) enrolledLandValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue1");
            if(enrolledLandValueDbl!=null && enrolledImpValue!=null){  
                enrolledLandValueDbl = (Double) enrolledLandValue.getInputValue();
                totalFbv = enrolledImpValue + enrolledLandValueDbl;
                totalEnrolledValue.setInputValue(totalFbv);
            }
            else if(enrolledLandValueDbl==null && enrolledImpValue!=null){               
                totalEnrolledValue.setInputValue(enrolledImpValue); 
            }
            else if(enrolledLandValueDbl!=null && enrolledImpValue==null){                       
                totalEnrolledValue.setInputValue(enrolledLandValueDbl); 
            }else if(enrolledLandValueDbl==null && enrolledImpValue==null)
                    totalEnrolledValue.setInputValue(null);                    
            }
            

         
    public void totalFBVCLsnr() {
        Double subtractAmount=0.0;
        Double divisionAmount=0.0;
        Double fbvExceedingExclusionDbl=0.0;
        Double totalFBV =0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        //Double totalFBV=(Double)valueChangeEvent.getNewValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalFBV");
        if(totalEnrolledValue!=null){
            totalFBV = (Double) totalEnrolledValue.getInputValue();
        }
        AttributeBinding exclusionAmount = (AttributeBinding) bindings.getControlBinding("exclusionAmount2");                
        AttributeBinding fbvExceedingExclusion = (AttributeBinding) bindings.getControlBinding("FBVExceedingExclusion");
        AttributeBinding percentExceedingExclusion = (AttributeBinding) bindings.getControlBinding("percentExceedingExclusion");
        if(exclusionAmount!=null && totalFBV!=null){    
            Double exclusionAmountdbl = (Double) exclusionAmount.getInputValue();
        if(exclusionAmountdbl!=null){
            if(totalFBV>0){
            subtractAmount = totalFBV - exclusionAmountdbl;   
            fbvExceedingExclusion.setInputValue(subtractAmount);
            }else {
                fbvExceedingExclusion.setInputValue(null);
            }
                }
        } else if(exclusionAmount!=null && totalFBV==null){
            fbvExceedingExclusion.setInputValue(null);            
        } else if(exclusionAmount==null && totalFBV!=null){
            fbvExceedingExclusion.setInputValue(totalFBV);            
        } else if(exclusionAmount==null && totalFBV==null)
           fbvExceedingExclusion.setInputValue(null);
   
        //percentExceedingExclusion
        if(fbvExceedingExclusion!=null && totalFBV!=null){             
             fbvExceedingExclusionDbl = (Double) fbvExceedingExclusion.getInputValue();
             if(fbvExceedingExclusionDbl!=null){
             if(totalFBV>0 && fbvExceedingExclusionDbl>0){
                divisionAmount = fbvExceedingExclusionDbl / totalFBV;
                percentExceedingExclusion.setInputValue(divisionAmount);
             }else {
                 percentExceedingExclusion.setInputValue(null);
             }
             }
             else
                 percentExceedingExclusion.setInputValue(divisionAmount);   
        }
        else if(fbvExceedingExclusion==null && totalFBV!=null){                                                      
             percentExceedingExclusion.setInputValue(0.0);
        }
        else if(fbvExceedingExclusion!=null && totalFBV==null){                                                          
             percentExceedingExclusion.setInputValue(null);
        }
        else if(fbvExceedingExclusion==null && totalFBV==null)
                 percentExceedingExclusion.setInputValue(null); 
        
    }
      
   
    public String postComment() {
                logger.info("PropValPerformBean : postComment");
                String comment = JSFUtils.resolveExpressionAsString("#{commentBean.value}");
                if(comment == null || comment.trim().isEmpty()) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("Empty Comment", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter comment", "Please enter comment"));
                    return null;
                } else {
                    DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
                    bindings.getOperationBinding("CreateInsert2").execute();
                }
                JSFUtils.resloveMethodExpression("#{commentBean.addComment}", Object.class, new Class[]{}, new Object[]{}); 
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("lstbpmaction", "addcomment");
    //          DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
    //          oracle.binding.OperationBinding op = bindings.getOperationBinding("update");          
    //          op.execute();    
    //           logger.info("PropValPerformBean : update Execute complete");
    //           if(!op.getErrors().isEmpty())
    //               logger.error("error saving comments.. "+op.getErrors().toString());
    ////           // bindings.getOperationBinding("getTaskDetails").execute();  
    //            
    //            saveAction();   /* Why Save is here ?*/
    //          return "refreshTaskFlow";
                return null;
            }
        
        public void onUnload(ClientEvent clientEvent) {
            // Add event code here...
            
            System.out.println("::INSIDE UPDATE::::");
            try {
                String action = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("lstbpmaction");
                logger.error("PropValPerformBean : onUnload..action.."+action);
                if("addcomment".equalsIgnoreCase(action))
                   updateBPMTask();
            } catch(Exception e) {
                e.printStackTrace();
                logger.error("onUnload", e);
            }
           
           // closeTaskFlow();

        }
    
    public String closeTaskFlow() {
        System.out.println("::closeTaskFlow::");
        
        return "closeTaskFlow";
    }

    private void updateBPMTask() {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        oracle.binding.OperationBinding op = bindings.getOperationBinding("update");
        op.execute();
        System.out.println("AFter Update:::");
        
    }
    
    public void prospLandValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double impFBVDbl=0.0;
        Double totalFbv=0.0;
        Double prospImpValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double prospLandValue=(Double)valueChangeEvent.getNewValue();  
        AttributeBinding prospImpValue = (AttributeBinding) bindings.getControlBinding("enrolledImpValue2");
        if(prospImpValue!=null)
            prospImpValueDbl = (Double) prospImpValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue2");
        if(prospLandValue!=null && prospImpValueDbl!=null){  
            impFBVDbl = (Double) prospImpValue.getInputValue();
            totalFbv = prospLandValue + impFBVDbl;
            totalEnrolledValue.setInputValue(totalFbv);
        }
        else if(prospLandValue==null && prospImpValueDbl!=null){  
            totalEnrolledValue.setInputValue(prospImpValueDbl);          
        }
        else if(prospLandValue!=null && prospImpValueDbl==null){                      
            totalEnrolledValue.setInputValue(prospLandValue); 
        }else if(prospLandValue==null && prospImpValueDbl==null)
                totalEnrolledValue.setInputValue(null);      
        }
    
    public void prospImpValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double totalFbv=0.0;
        Double prospLandValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double prospImpValue=(Double)valueChangeEvent.getNewValue();
        AttributeBinding prospLandValue = (AttributeBinding) bindings.getControlBinding("enrolledLandValue2");   
        if(prospLandValue!=null)
              prospLandValueDbl = (Double) prospLandValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalEnrolledValue2");
            if(prospLandValueDbl!=null && prospImpValue!=null){  
                prospLandValueDbl = (Double) prospLandValue.getInputValue();
                totalFbv = prospImpValue + prospLandValueDbl;
                totalEnrolledValue.setInputValue(totalFbv);
            }
            else if(prospLandValueDbl==null && prospImpValue!=null){               
                totalEnrolledValue.setInputValue(prospImpValue); 
            }
            else if(prospLandValueDbl!=null && prospImpValue==null){                       
                totalEnrolledValue.setInputValue(prospLandValueDbl); 
            }else if(prospLandValueDbl==null && prospImpValue==null)
                    totalEnrolledValue.setInputValue(null);                    
            }


    public String backAction() {
        // Add event code here...
        saveAction();
        
        return "Back";
    }
    
    public boolean getEnableSubmitbtn() {
        
        boolean returnvalue = false;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding paramIter = bindings.findIteratorBinding("assigneesIterator");
        paramIter.executeQuery();
        
        Row rows1[] = paramIter.getAllRowsInRange();
        for (Row row : rows1) {
            String id = (String) row.getAttribute("id");
            System.out.println("ID ==="+id);
            SecurityHelper shelper = new SecurityHelper();
           String[] userRoles= ADFContext.getCurrent().getSecurityContext().getUserRoles();
            System.out.println("Role Exists ::"+SecurityHelper.isUserInRole(id));

                for (String userrole : userRoles) {
                    System.out.println("userrole---"+userrole);
                            if (userrole.equalsIgnoreCase(id)) {
                                returnvalue = true;
                                break;                
                            }
                        }
        }
    System.out.println("returnvalue-----"+returnvalue);

    return returnvalue;
        
    }
    
    public void fbvLandValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double impFBVDbl=0.0;
        Double totalFbv=0.0;
        Double fbvImpValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double fbvLandValue=(Double)valueChangeEvent.getNewValue();  
        AttributeBinding fbvImpValue = (AttributeBinding) bindings.getControlBinding("impFBV");
        if(fbvImpValue!=null)
            fbvImpValueDbl = (Double) fbvImpValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalFBV");
        if(fbvLandValue!=null && fbvImpValueDbl!=null){  
            impFBVDbl = (Double) fbvImpValue.getInputValue();
            totalFbv = fbvLandValue + impFBVDbl;
            totalEnrolledValue.setInputValue(totalFbv);
        }
        else if(fbvLandValue==null && fbvImpValueDbl!=null){  
            totalEnrolledValue.setInputValue(fbvImpValueDbl);          
        }
        else if(fbvLandValue!=null && fbvImpValueDbl==null){                      
            totalEnrolledValue.setInputValue(fbvLandValue); 
        }else if(fbvLandValue==null && fbvImpValueDbl==null){
                totalEnrolledValue.setInputValue(null);
        }
               
            totalFBVCLsnr();
        }
    
    public void fbvImpValVC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Double totalFbv=0.0;
        Double fbvLandValueDbl=0.0;
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        Double fbvImpValue=(Double)valueChangeEvent.getNewValue();
        AttributeBinding fbvLandValue = (AttributeBinding) bindings.getControlBinding("landFBV");   
        if(fbvLandValue!=null)
              fbvLandValueDbl = (Double) fbvLandValue.getInputValue();
        AttributeBinding totalEnrolledValue = (AttributeBinding) bindings.getControlBinding("totalFBV");
            if(fbvLandValueDbl!=null && fbvImpValue!=null){  
                fbvLandValueDbl = (Double) fbvLandValue.getInputValue();
                totalFbv = fbvImpValue + fbvLandValueDbl;
                totalEnrolledValue.setInputValue(totalFbv);
            }
            else if(fbvLandValueDbl==null && fbvImpValue!=null){               
                totalEnrolledValue.setInputValue(fbvImpValue); 
            }
            else if(fbvLandValueDbl!=null && fbvImpValue==null){                       
                totalEnrolledValue.setInputValue(fbvLandValueDbl); 
            }else if(fbvLandValueDbl==null && fbvImpValue==null){
                    totalEnrolledValue.setInputValue(null); 
            }
                totalFBVCLsnr();
            }
    
    public void loadCancelationFlag(){
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("propspectiveEnrollmentIterator");
        iter.executeQuery();
        if (iter != null) {
            Row row = iter.getCurrentRow();
            if (row != null) {
                String cancelFlag = (String) row.getAttribute("isRecommendedToCancelPropClaim");
                if("Yes".equalsIgnoreCase(cancelFlag)) {
                    row.setAttribute("isRecommendedToCancelPropClaim", "Y");
                } if("No".equalsIgnoreCase(cancelFlag)) {
                    row.setAttribute("isRecommendedToCancelPropClaim", "N");
                }
            }
        }
    }

    public String checkmilestone() {
        // Add event code here...
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = (DCIteratorBinding) bindings.findIteratorBinding("systemAttributesIterator");
        iter.executeQuery();
        Row row = iter.getCurrentRow();

        String state = (String) row.getAttribute("state");

        logger.info("state :::" + state);
                
        if (state != null) {
            if ("WITHDRAWN".equalsIgnoreCase(state)) {
                ADFUtils.setPageFlowValue("withdrawntask", "Y");
            }
        }
          return "valperform";      
    }
}
