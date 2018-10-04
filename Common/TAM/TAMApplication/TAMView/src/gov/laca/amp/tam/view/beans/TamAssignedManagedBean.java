package gov.laca.amp.tam.view.beans;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.BaseYearEventMgmtService;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.BaseYearEventMgmtService_ptClient;

import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.VerifyBaseYearEventRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.local.BaseYearEventMgmtServiceWrapper;

import gov.laca.amp.tam.model.facade.TAMAssignedFacade;

import gov.laca.amp.tam.model.facade.TAMAssignmentFacade;

import gov.laca.amp.tam.model.facade.UnlockedAINsRespStructure;
import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Date;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.util.ComponentReference;

public class TamAssignedManagedBean extends AmpManagedBean {
    static AmpLogger AmpLogger = new AmpLogger(TamAssignedManagedBean.class);
    public static String LOGGED_IN_USER_DISTRICTS_ITER =
        "userDistrictsIterator";
    public static String LOGGED_IN_USER_INITIATABLE_DISTRICTS_ITER =
        "userDistrictsToInitiateIterator";
    public static String TAM_ASSIGNED_LIST_ITERATOR ="tamAssigedPojoListIterator";
    private Row btnClickedRow = null;
    private String ain;
    private String bmpStatus;
    private String bpmInstanceId;
    private ComponentReference bpmStatus_UI;
    private Boolean manageBVMCheckPassed = true;
    private String loggedInUserDistrictsList = "";
    private String loggedInUserInitiatableDistrictsList = "";
    private Integer currentAssignedBatchNumber =1;

    public TamAssignedManagedBean() {
        DCIteratorBinding iter =
            ADFUtils.findIterator("" + LOGGED_IN_USER_DISTRICTS_ITER);
        DCIteratorBinding iter1 =
            ADFUtils.findIterator("" + LOGGED_IN_USER_INITIATABLE_DISTRICTS_ITER);        
        Row[] rows = iter.getAllRowsInRange();
        for (Row r : rows) {
            String item = (String)r.getAttribute(0);
            AmpLogger.warn("==>Ctor, distItem:" + item);
            this.loggedInUserDistrictsList += "," + item;
        }
        
        Row[] rows1 = iter1.getAllRowsInRange();
        for (Row r1 : rows1) {
            String item1 = (String)r1.getAttribute(0);
            AmpLogger.warn("==>Ctor, distItem:" + item1);
            this.loggedInUserInitiatableDistrictsList += "," + item1;

        }
    }

    public static Object resolveMethodExpression(String expression,
                                                 Class returnType,
                                                 Class[] argTypes,
                                                 Object[] argValues) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        MethodExpression methodExpression =
            elFactory.createMethodExpression(elContext, expression, returnType,
                                             argTypes);
        return methodExpression.invoke(elContext, argValues);
    }

    public void byeButtonActionListener(ActionEvent actionEvent) {
        //        for(int i=0;i<2;i++)
        //        {
        //        System.out.println("==>iter attr."+ r.getAttribute(i));
        Row r = btnClickedRow;
        String ain = (String)r.getAttribute("ain");
        String assignedTo = (String)r.getAttribute("tamAssignedTo");
        String assignedBy = (String)r.getAttribute("tamAssignedBy");
        String processType = (String)r.getAttribute("tamProcessType");
        String source = (String)r.getAttribute("tamSource");
        BigInteger tamId = (BigInteger)r.getAttribute("tamId");
        String district = (String)r.getAttribute("districtName");
        Date tamModifiedDateTime = (Date)r.getAttribute("tamModifiedDateTime");
        TAMAssignedFacade facade = new TAMAssignedFacade();
        String assignedToId = assignedTo.split("-")[0];
        String assignedById = assignedBy.split("-")[0];
        String inputs =
            ain + "," + assignedToId + "," + assignedById + "," + processType +
            "," + source;
        //Check whether the record is still assigned to the user.
        UnlockedAINsRespStructure unlockedAINsRespStructure = new UnlockedAINsRespStructure();
           try {
             unlockedAINsRespStructure = facade.getUnlockedAINs(ain, true, assignedTo, district, "", "", 1, "");
           } catch (Exception e) {
               e.printStackTrace();
           }
           System.out.println("==>unlockedAINsRespStructure:"+unlockedAINsRespStructure.getStartRange()+","+unlockedAINsRespStructure.getEndRange());
           if(unlockedAINsRespStructure.getEndRange() ==  null || unlockedAINsRespStructure.getEndRange() <1 ){
               JSFUtils.addFacesErrorMessage("ERROR: Can not initiate. Currently the AIN is not assigned to you. Refresh your screen.");
               return;
               
           }
        DCIteratorBinding userInitiatableDistrictsIter =
            ADFUtils.findIterator("" + LOGGED_IN_USER_INITIATABLE_DISTRICTS_ITER);
        this.setManageBVMCheckPassed(true);
        if (!isAinDistrictInUserDistricts(district, userInitiatableDistrictsIter)) {
            JSFUtils.addFacesErrorMessage("You are not authorized to work on an AIN in this district.");
            this.setManageBVMCheckPassed(false);
            return;
        }
        AmpLogger.warn("==>Invoking verifyBaseYearEvents (" + inputs + ")");
        //  String wsResponse= facade.verifyBaseYearEvents(tamId,district,ain, assignedToId, assignedById, processType, source);
        OperationBinding verifyBaseYearEventsOper =
            ADFUtils.findOperation("verifyBaseYearEvents");
        Map params = verifyBaseYearEventsOper.getParamsMap();
        params.put("tamId", tamId);
        params.put("district", district);
        params.put("ain", ain);
        params.put("assignedToId", assignedToId);
        params.put("assignedById", assignedById);
        params.put("processType", processType);
        params.put("source", source);

        String wsResponse = (String)verifyBaseYearEventsOper.execute();
        AmpLogger.warn("wsResponse::" + wsResponse );
        
        if(wsResponse != null) {
            String[] status = wsResponse.split(",");
            
            this.setAin(status[0]);
            if (status[1].equals("")) {
                this.setBpmInstanceId("Not created, Most likely locked");
            } else {
                this.setBpmInstanceId(status[1]);
            }
            this.setBmpStatus(status[2]);
        }
       
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBpmStatus_UI());

        // Add event code here...
    }
/**
     *"manage BVM" btnSelectionListener
     * @param selectionEvent
     */
    public void btnSelectionListener(SelectionEvent selectionEvent) {
        // Add event code here...

        resolveMethodExpression("#{bindings.tamAssigedPojoList.collectionModel.makeCurrent}",
                                null, new Class[] { SelectionEvent.class },
                                new Object[] { selectionEvent });
        DCIteratorBinding iter =
            ADFUtils.findIterator("tamAssigedPojoListIterator");
        btnClickedRow = iter.getCurrentRow();

    }
    /**
         *assignedDiscloseLsnr
         * @param disclosureEvent
         */
        public void assignedDiscloseLsnr(DisclosureEvent disclosureEvent) {
            // Add event code here...
        this.invokeRetrieveAssignedAINs(this.getCurrentAssignedBatchNumber());
            DCIteratorBinding assignedTableIter =
                ADFUtils.findIterator(TAM_ASSIGNED_LIST_ITERATOR);
            assignedTableIter.executeQuery();
            
        }

 
    
    public String refresh_action() {
        // Add event code here...
        this.invokeRetrieveAssignedAINs(this.getCurrentAssignedBatchNumber());
        DCIteratorBinding assignedTableIter =
            ADFUtils.findIterator(TAM_ASSIGNED_LIST_ITERATOR);
        assignedTableIter.executeQuery();
        return null;
    }
    public void setAin(String ain) {
        this.ain = ain;
    }

/**
 * invokeRetrieveAssignedAINs
 */
    private void invokeRetrieveAssignedAINs(Integer batchNumber){
        String assignedTo = ((String)ADFUtils.getBoundAttributeValue("loggedInUserId")).toLowerCase() 
                            +"-"+(String)ADFUtils.getBoundAttributeValue("loggedInUserDisplayName");
        AmpLogger.warn("==>assignedDiscloseLsnr-assignedTo:"+assignedTo);

        OperationBinding retrieveAssignedAINsOper =
            ADFUtils.findOperation("retrieveAssignedAINs");
        Map params = retrieveAssignedAINsOper.getParamsMap();
        params.put("assignedTo", assignedTo);
        params.put("district", "");
        params.put("region","");
        params.put("cluster", "");
        params.put("batchNumber", batchNumber);
        retrieveAssignedAINsOper.execute();
    }
    public String getAin() {
        return ain;
    }

    public void setBmpStatus(String bmpStatus) {
        this.bmpStatus = bmpStatus;
    }

    public String getBmpStatus() {
        return bmpStatus;
    }

    public void setBpmInstanceId(String bpmInstanceId) {
        this.bpmInstanceId = bpmInstanceId;
    }

    public String getBpmInstanceId() {
        return bpmInstanceId;
    }

    public void setBpmStatus_UI(RichPanelGroupLayout bpmStatus_UI) {
        this.bpmStatus_UI =
                ComponentReference.newUIComponentReference(bpmStatus_UI);
    }

    public RichPanelGroupLayout getBpmStatus_UI() {
        if (bpmStatus_UI != null) {
            return (RichPanelGroupLayout)bpmStatus_UI.getComponent();
        } else {
            return null;
        }
    }

    private boolean isAinDistrictInUserDistricts(String ainDistrict,
                                                 DCIteratorBinding districtsIter) {
        Row[] rows = districtsIter.getAllRowsInRange();
        for (Row r : rows) {
            String distItem = (String)r.getAttribute(0);
            if (ainDistrict.equals(distItem))
                return true;
        }
        return false;
    }
    public boolean isDisableManageBVMButton(boolean selected, String ainDistrict){
        if(selected && loggedInUserInitiatableDistrictsList.toString().contains(ainDistrict))
            return true;
        
        return false;
    }
    public void setManageBVMCheckPassed(Boolean manageBVMCheckPassed) {
        this.manageBVMCheckPassed = manageBVMCheckPassed;
    }

    public Boolean getManageBVMCheckPassed() {
        return manageBVMCheckPassed;
    }

    public void setLoggedInUserDistrictsList(String loggedInUserDistrictsList) {
        this.loggedInUserDistrictsList = loggedInUserDistrictsList;
    }

    public String getLoggedInUserDistrictsList() {
        return loggedInUserDistrictsList;
    }

    public void setLoggedInUserInitiatableDistrictsList(String loggedInUserInitiatableDistrictsList) {
        this.loggedInUserInitiatableDistrictsList = loggedInUserInitiatableDistrictsList;
    }

    public String getLoggedInUserInitiatableDistrictsList() {
        return loggedInUserInitiatableDistrictsList;
    }

    public String loadMore_action() {
        // Add event code here...
        this.setCurrentAssignedBatchNumber(++currentAssignedBatchNumber);
        this.invokeRetrieveAssignedAINs(this.getCurrentAssignedBatchNumber());
        DCIteratorBinding assignedTableIter =
            ADFUtils.findIterator(TAM_ASSIGNED_LIST_ITERATOR);
        assignedTableIter.executeQuery();
        return null;
    }

    public void setCurrentAssignedBatchNumber(Integer currentAssignedBatchNumber) {
        this.currentAssignedBatchNumber = currentAssignedBatchNumber;
    }

    public Integer getCurrentAssignedBatchNumber() {
        return currentAssignedBatchNumber;
    }
}
