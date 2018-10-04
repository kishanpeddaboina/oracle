package gov.laca.amp.bvm.view.bean;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.change.ComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.util.ComponentReference;


public class InitiateBVMBean extends AmpManagedBean{
    public static final AmpLogger LOGGER =
        new AmpLogger(InitiateBVMBean.class);
    private static final String EFFECTIVE_END_DATE_ATTRIBUTE =
        "effectiveEndDate";
    private static final String EFFECTIVE_BEGIN_DATE_ATTRIBUTE =
        "effectiveBeginDate";
    private static final String VERIFICATION_DATE_ATTRIBUTE =
        "verificationDate";
    private static final String INACTIVE_DATE_ATTRIBUTE = "inactiveDate";
    private static final String LOGGER_PREFIX = "BVM Log: ";
    @SuppressWarnings("compatibility:8697112914763514741")
    private static final long serialVersionUID = 1L;
    private Boolean filtered;
    private ComponentReference manageBVMBtn;
    private ComponentReference lockLabel;


    public InitiateBVMBean() {
        super();
    }

    public void onQuery(QueryEvent queryEvent) {
        // pre-processing;code here;
        boolean invokeQuery = true;
        /*
        * Method called by the Query Listener. This method checks if
        * the Current Event List filter criteria
        */
        FilterableQueryDescriptor fqd =
            (FilterableQueryDescriptor)queryEvent.getDescriptor();
        Map map = fqd.getFilterCriteria();
        DCIteratorBinding itrBinding = ADFUtils.findIterator("currentEventsListIterator");
        ViewObject vo = itrBinding.getViewObject();
        ViewCriteria criteria = vo.createViewCriteria();
        criteria.setCriteriaMode(ViewCriteria.CRITERIA_MODE_CACHE);
        ViewCriteriaRow vcRow = criteria.createViewCriteriaRow();

        ViewCriteriaItem effectiveBeginDateItem = vcRow.ensureCriteriaItem(EFFECTIVE_BEGIN_DATE_ATTRIBUTE);
        effectiveBeginDateItem.setOperator("!=");
        effectiveBeginDateItem.getValues().get(0).setValue(null);
        criteria.add(effectiveBeginDateItem);
        
        ViewCriteriaItem effectiveEndDateItem = vcRow.ensureCriteriaItem(EFFECTIVE_END_DATE_ATTRIBUTE);
        effectiveEndDateItem.setOperator("=");
        effectiveEndDateItem.getValues().get(0).setValue(null);
        criteria.add(effectiveEndDateItem);        
        vo.applyViewCriteria(criteria);
        
        String effectiveBeginDate =
            (String)map.get(EFFECTIVE_BEGIN_DATE_ATTRIBUTE);
        if (effectiveBeginDate != null) {
            map.put(EFFECTIVE_BEGIN_DATE_ATTRIBUTE, null);
        }
        String effectiveEndDate =
            (String)map.get(EFFECTIVE_END_DATE_ATTRIBUTE);
        if (effectiveEndDate == null) {
            map.put(EFFECTIVE_END_DATE_ATTRIBUTE, null);
        }

        String verificationDate = (String)map.get(VERIFICATION_DATE_ATTRIBUTE);
        if (verificationDate != null) {
            map.put(VERIFICATION_DATE_ATTRIBUTE, null);
        }
        String inactiveDate = (String)map.get(INACTIVE_DATE_ATTRIBUTE);
        if (inactiveDate == null) {
            map.put(INACTIVE_DATE_ATTRIBUTE, null);
        }
        invokeQuery(queryEvent, invokeQuery);
        // post processing code here
    }

    private void invokeQuery(QueryEvent queryEvent, boolean invokeQuery) {
        if (invokeQuery) {
            // execute the defaul QueryListener code added by JDeveloper;
            //when creating the table
            String expression =
                "#{bindings." + "currentEventsListQuery" + ".processQuery}";
            JSFUtils.resloveMethodExpression(expression, Object.class,
                                             new Class[] { (QueryEvent.class) },
                                             new Object[] { queryEvent });
        }
    }

    public void applyLstnr(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding filterEvents = ADFUtils.getOperationBinding("filterBaseYearEvents");
        Map params = filterEvents.getParamsMap();
        params.put("eventDateBegin", ADFUtils.getBoundAttributeValue("eventDateBegin1"));
        params.put("eventDateEnd", ADFUtils.getBoundAttributeValue("eventDateEnd1"));
        params.put("effectiveDateBegin", ADFUtils.getBoundAttributeValue("effectiveDateBegin1"));
        params.put("effectiveDateEnd", ADFUtils.getBoundAttributeValue("effectiveDateEnd1"));
	params.put("effectiveEndDateBegin", ADFUtils.getBoundAttributeValue("effectiveEndDateBegin1"));
        params.put("effectiveEndDateEnd", ADFUtils.getBoundAttributeValue("effectiveEndDateEnd1"));
        params.put("rollYear", ADFUtils.getBoundAttributeValue("rollOver1"));
        params.put("verified", ADFUtils.getBoundAttributeValue("verified1"));
        params.put("unverified", ADFUtils.getBoundAttributeValue("unverified1"));
        params.put("active", ADFUtils.getBoundAttributeValue("active1"));
        params.put("inactive", ADFUtils.getBoundAttributeValue("inactive1"));
        Boolean result = (Boolean)filterEvents.execute();
        if(!result){
            LOGGER.warn(this, "loadData",
                         LOGGER_PREFIX + "Apply Filter operatiion returned false", null);  
            OperationBinding allEvents = ADFUtils.getOperationBinding("allBaseYearEvents");
            result = (Boolean)allEvents.execute();
            if (result != null) {
                if (!result) {
                    LOGGER.warn(this, "applyLstnr",
                                BaseYearEventUtils.LOGGER_PREFIX + " Remove Filter operatiion returned false",
                                null);
                } else {
                    setFiltered(false);
                    refreshTables();
                }
            }else{
                LOGGER.error(this, "applyLstnr",
                            BaseYearEventUtils.LOGGER_PREFIX + "Remove Filter operatiion returned NULL",
                            null);
            }
        }else{
            setFiltered(true);
            refreshTables();
        }
    }

    private void refreshTables() {
        DCIteratorBinding currentEvents = ADFUtils.findIterator("filteredCurrentEventsListIterator");
        if(currentEvents != null){
            currentEvents.executeQuery();
        }
        DCIteratorBinding historicEvents = ADFUtils.findIterator("filteredHistoricalEventsListIterator");
        historicEvents.executeQuery();
    }

    public void showAllLstnr(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding allEvents = ADFUtils.getOperationBinding("allBaseYearEvents");
        Boolean result = (Boolean)allEvents.execute();
        if(!result){
            LOGGER.warn(this, "loadData",
                         LOGGER_PREFIX + " Remove Filter operatiion returned false", null);            
        }else{
            setFiltered(false);
            refreshTables();
        }
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public Boolean getFiltered() {
        return filtered;
    }

    public void manageBVMLstnr(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding inititateBVM = ADFUtils.findOperation("initiateBaseYearEvent");
        String result = (String)inititateBVM.execute();
        if(result != null){
            getManageBVMBtn().setVisible(false);
            getLockLabel().setVisible(true);
            AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
            ctx.addPartialTarget(getManageBVMBtn());
            ctx.addPartialTarget(getLockLabel());
        }
    }

    public void setManageBVMBtn(RichCommandToolbarButton manageBVMBtn) {
        this.manageBVMBtn = ComponentReference.newUIComponentReference(manageBVMBtn);
    }

    public RichCommandToolbarButton getManageBVMBtn() {
        if (manageBVMBtn != null) {
            return (RichCommandToolbarButton)manageBVMBtn.getComponent();
        }
        return null;
    }

    public void setLockLabel(RichOutputText lockLabel) {
        this.lockLabel = ComponentReference.newUIComponentReference(lockLabel);
    }

    public RichOutputText getLockLabel() {
        if (lockLabel != null) {
            return (RichOutputText)lockLabel.getComponent();
        }
        return null;
    }
    
    public void EditHistoricalHandler(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deleteLstnr(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding deleteEvent = ADFUtils.getOperationBinding("deleteEvent");
        deleteEvent.getParamsMap().put("eventType", "historical");
        deleteEvent.execute();
        DCIteratorBinding iterator = ADFUtils.findIterator("filteredHistoricalEventsListIterator");
        iterator.executeQuery();
    }

    public void showTab(DisclosureEvent disclosureEvent) {
        // Add event code here...
        RichShowDetailItem tab = (RichShowDetailItem) disclosureEvent.getComponent();
        System.out.println("INSIDE showTab");
        //30296 fix
        if(disclosureEvent.isExpanded()) {
            DCBindingContainer dbc = ADFUtils.getDCBindingContainer();
            OperationBinding op = dbc.getOperationBinding("loadData");
             Map opm = op.getParamsMap();
             Map<String,Object> pfMap= AdfFacesContext.getCurrentInstance().getPageFlowScope();
             opm.put("ain", pfMap.get("temp_init_ain"));
             opm.put("userId", pfMap.get("temp_init_userId"));
             opm.put("aoid", pfMap.get("temp_init_aoid"));
             dbc.getOperationBinding("loadData").execute();
            DCIteratorBinding targetHisItr =(DCIteratorBinding) dbc.findIteratorBinding("filteredHistoricalEventsListIterator");
            if(targetHisItr != null)
                 targetHisItr.executeQuery();
    
         }
        FacesContext fc = FacesContext.getCurrentInstance();
        ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();
        ComponentChange cc = new AttributeComponentChange("disclosed",Boolean.FALSE);
        System.out.println(":CC:"+cc);
        cm.addComponentChange(fc,tab,cc);
        
    }
    public void showTCTab(DisclosureEvent disclosureEvent) {
        // Add event code here...
            RichShowDetailItem tab = (RichShowDetailItem) disclosureEvent.getComponent();
            System.out.println("INSIDE showTCTab");
            FacesContext fc = FacesContext.getCurrentInstance();
            ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();
            System.out.println("cm="+cm);
            ComponentChange cc = new AttributeComponentChange("disclosed",Boolean.FALSE);
            System.out.println("::CC::"+cc);
            cm.addComponentChange(fc,tab,cc);
    }
    
    //12c refresh issue
    public String initBVMPgLoad() {
        DCBindingContainer dbc = ADFUtils.getDCBindingContainer();
        Map<String,Object> pfMap= AdfFacesContext.getCurrentInstance().getPageFlowScope();
        pfMap.put("temp_init_ain", JSFUtils.resolveExpression("#{param.ain}"));
        pfMap.put("temp_init_userId", JSFUtils.resolveExpression("#{param.userId}"));
        pfMap.put("temp_init_aoid", JSFUtils.resolveExpression("#{param.aoid}"));
        dbc.getOperationBinding("loadData").execute();
        DCIteratorBinding targetHisItr =(DCIteratorBinding) dbc.findIteratorBinding("filteredHistoricalEventsListIterator");
        if(targetHisItr != null)
          targetHisItr.executeQuery();
        
        return null;
    }
}

