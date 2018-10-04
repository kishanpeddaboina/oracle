package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

/**
 * @author Vijay Redla
 * @version 2.1
 */
public class FilterBean extends AmpManagedBean{
    @SuppressWarnings("compatibility:5922585695745245797")
    private static final long serialVersionUID = 1L;
    
    public static final AmpLogger LOGGER =
        new AmpLogger(FilterBean.class);
    private static final String EFFECTIVE_END_DATE_ATTRIBUTE =
        "effectiveEndDate";
    private static final String EFFECTIVE_BEGIN_DATE_ATTRIBUTE =
        "effectiveBeginDate";
    private static final String VERIFICATION_DATE_ATTRIBUTE =
        "verificationDate";
    private static final String INACTIVE_DATE_ATTRIBUTE = "inactiveDate";

    private Boolean filtered;


    public FilterBean() {
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
        if (result != null) {
            if (!result) {
                LOGGER.warn(this, "applyLstnr",
                            BaseYearEventUtils.LOGGER_PREFIX + "Apply Filter operatiion returned false",
                            null);
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
            } else {
                setFiltered(true);
                refreshTables();
            }
        }else{
            LOGGER.error(this, "applyLstnr",
                        BaseYearEventUtils.LOGGER_PREFIX + "Apply Filter operatiion returned NULL",
                        null);
        }
    }

    private void refreshTables() {
        DCIteratorBinding currentEvents = ADFUtils.findIterator("filteredCurrentEventsListIterator");
        currentEvents.executeQuery();
        DCIteratorBinding historicEvents = ADFUtils.findIterator("filteredHistoricalEventsListIterator");
        historicEvents.executeQuery();
    }

    public void showAllLstnr(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding allEvents = ADFUtils.getOperationBinding("allBaseYearEvents");
        Boolean result = (Boolean)allEvents.execute();
        if (result != null) {
            if (!result) {
                LOGGER.warn(this, "showAllLstnr",
                            BaseYearEventUtils.LOGGER_PREFIX + " Remove Filter operatiion returned false",
                            null);
            } else {
                setFiltered(false);
                refreshTables();
            }
        }else{
            LOGGER.error(this, "showAllLstnr",
                        BaseYearEventUtils.LOGGER_PREFIX + "Remove Filter operatiion returned NULL",
                        null);
        }
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public Boolean getFiltered() {
        return filtered;
    }
}

