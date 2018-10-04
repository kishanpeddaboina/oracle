package gov.laca.amp.bvm.util;

public class BaseYearEventConstants {
    private static final String APPLICATION_ID = "BVM";
    //Process ID defined in the database for BVM process is 1
    private static final String PROCESS_ID = "BVM";
    public static final String TAM_SOURCE_TYPE = "TAM";
    public static final String BVM_SOURCE_TYPE = "BVM";

    private static final String AIN_WEB_SERVICE_CONNECTION_NAME = "AINLockMgmtService";
    private static final String BYE_MGMT_WEB_SERVICE_CONNECTION_NAME = "BaseYearEventMgmtService";
    private static final String BYE_RULES_WEB_SERVICE_CONNECTION_NAME = "BaseYearEventRulesService";
    private static final String LOV_MGMT_SERTVICE_CONNECTION_NAME  = "LOVMgmtService";
    public static Boolean _TEST = false;
    public static final String CURRENT_EVENT_TYPE = "current";
    public static final String HISTORICAL_EVENT_TYPE = "historical";
    public static final String OPA_CALL_ONCLOSE = "onclose";
    public static final String OPA_CALL_ONSUBMIT = "onsubmit";
    public static final String HISTORICAL_EVENTS_ITERATOR ="filteredHistoricalEventsListIterator";
    public static final String CURRENT_EVENTS_ITERATOR = "filteredCurrentEventsListIterator";
    public static final String RESET_BINDING = "resetEvent";
    public static final String EVENT_TYPE = "eventType";
    public static final String DELETE_EVENT = "deleteEvent";
    public static final String HISTORICAL_EVENTS_LIST_ITERATOR = "filteredHistoricalEventsListIterator";
    public static final String CURRENT_EVENTS_LIST_ITERATOR = "filteredCurrentEventsListIterator";
    public static final String HISTORICAL = "historical";
    public static final String CURRENT = "current";
    public static String getAPPLICATION_ID() {
        return APPLICATION_ID;
    }

    public static String getPROCESS_ID() {
        return PROCESS_ID;
    }
    
    //Attribute strings
    public static final String INACTIVEDATE = "inactiveDate";
    public static final String MODIFIED_DATE = "modifiedDate";
    public static final String MODIFIED_BY = "modifiedBy";
    public static final String CREATED_DATE = "createdDate";
    public static final String CREATE_BY = "createBy";
    public static final String NEW_COONSTRUCTION_DATE = "newConstructionDate";
    public static final String USERID = "userId";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String ROLLTYPE = "rollType";
    public static final String ORIGINAL_BYEID = "originalBYEId";
    public static final String ORIGINAL_AIN = "originalAIN";
    public static final String BYE_LEGACY_SOURCE_SYSTEM =
        "byeLegacySourceSystem";
    public static final String VERIFIED_BY = "verifiedBy";
    public static final String EVENT_SOURCE = "eventSource";
    public static final String VERIFICATION_DATE = "verificationDate";
    public static final String EFFECTIVE_END_DATE = "effectiveEndDate";
    public static final String EFFECTIVE_BEGIN_DATE = "effectiveBeginDate";
    public static final String PERCENTAGE_FIXTURE = "percentageFixture";
    public static final String TOTAL_ADJ_ACQUISITION_COST =
        "totalAdjAcquisitionCost";
    public static final String PPDESCRIPTION = "ppDescription";
    public static final String PERSONAL_PROPERTY_TYPE =
        "personalPropertyType";
    public static final String PP_ASSESSMENT_NUMBER = "ppAssessmentNumber";
    public static final String RETAINED_FIXTURE_VALUE =
        "retainedFixtureValue";
    public static final String RETAINED_IMPROVEMETN_VALUE =
        "retainedImprovementValue";
    public static final String RETAINED_LAND_VALUE = "retainedLandValue";
    public static final String FIXTURE_VALUE = "fixtureValue";
    public static final String IMPROVEMENT_REASON_CODE =
        "improvementReasonCode";
    public static final String LAND_REASON_CODE = "landReasonCode";
    public static final String IMPROVEMENT_VALUE = "improvementValue";
    public static final String LAND_VALUE = "landValue";
    public static final String PERCENTAGE_APPLIED_VALUE =
        "percentageAppliedToValue";
    public static final String BLENDED_VALUE_FLAG = "blendedValueFlag";
    public static final String PARTIALLY_COMPLETE_NUMBER =
        "partiallyCompleteNumber";
    public static final String SUB_UNIT_DESCRIPTION = "subUnitDescription";
    public static final String SUB_UNIT_NUMBER = "subUnitNumber";
    public static final String BASE_YEAR = "baseYear";
    public static final String SEQUENCE_NUMBER = "sequenceNumber";
    public static final String DOCUMENT_NUMBER = "documentNumber";
    public static final String RECORDING_DATE = "recordingDate";
    public static final String EVENT_DATE = "eventDate";
    public static final String EVENT_DESCRIPTION = "eventDescription";
    public static final String EVENT_NAME = "eventName";
    public static final String PERCENT_OWNERSHIP = "percentOwnership";
    public static final String OWNERSHIP_CODE = "ownershipCode";
    public static final String BYEID = "byeId";
    public static final String AIN = "ain";
    
    //New Attributes
    public static final String AOID = "aoid";
    public static final String EVENT_NAME_ID = "eventNameId";
    public static final String EVENT_NAME_KEY = "eventNameKey";
    public static final String EVENT_NAME_LEGEND = "eventNameLegend";
    public static final String EVENT_DESCRIPTION_ID = "eventDescriptionId";
    public static final String EVENT_DESCRIPTION_KEY= "eventDescriptionKey";
    public static final String EVENT_DESCRIPTION_LEGEND = "eventDescriptionLegend";
    public static final String LAND_REASON_CODE_ID = "landReasonCodeId";
    public static final String LAND_REASON_CODE_KEY = "landReasonCodeKey";
    public static final String LAND_REASON_CODE_LEGEND = "landReasonCodeLegend";
    public static final String IMPROVEMENT_REASON_CODE_ID = "improvementReasonCodeId";
    public static final String IMPROVEMENT_REASON_CODE_KEY = "improvementReasonCodeKey";
    public static final String IMPROVEMENT_REASON_CODE_LEGEND = "improvementReasonCodeLegend";
    public static final String REVIEW_REQUIRED = "reviewRequired";
    public static final String CURRENT_ON_CLOSE = "currentOnClose";
    public static final String DB_LOCK_STATUS = "dbLockStatus";
    public static final String LOCKED_BY_USERID = "lockedByUserId";
    public static final String DISTRICTID = "districtId";
    public static final String DISTRICT_NAME = "districtName";
    public static final String REGION = "region";
    public static final String REGION_NAME = "regionName";
    public static final String CLUSTER_ROUTE_ID = "clusterRouteId";
    public static final String CLUSTER_ROUTE = "clusterRoute";
    public static final String CLUSTER_ROUTE_NAME = "clusterRouteName";
    public static final String AIN_TYPE = "ainType";
    public static final String ECID = "ecid";
    public static final String NOTES = "notes";
    public static final String ISDIRTY = "isDirty";
    public static final String AMP_PROFILEBEAN_NAME = "ampBusinessUserProfile";
    public static final String EVENTS_CHANGED_MESSAGE = "<p>Additional base year event activity has been identified outside of this process.</p><p>Any changes submitted and approved could potentially overwrite the latest activity.</p> <p>Please verify that you wish to proceed with this overwrite and select Yes to continue.</p> <p>Select No to return to the current process for further editing, or to cancel the current process if available.</p>";
    public static final String APPROVE_MESSAGE = "<p>Please select Save before selecting Approve if additional changes have been made.</p> <p>All changes made by approver will be lost without saving and submitting for additional approval.</p><p>Please select Yes to continue with Approve.</p>";
    public static final String SELECTED_HISTORICAL_BYEID_BINDING = "selectedHistoricalByeID";
    public static final String SELECTED_CURRENT_BYEID_BINDING = "selectedCurrentByeId";
    public static final String SELECT_CURRENT_EVENT_METHOD_BINDING = "setSelectedCurrentEvent";
    public static final String SELECT_HISTORICAL_EVENT_METHOD_BINDING = "setSelectedHistoricalEvent";

    public static String getAinWebServiceConnectionName() {
        return AIN_WEB_SERVICE_CONNECTION_NAME;
    }

    public static String getBYEMgmtWebServiceConnectionName() {
        return BYE_MGMT_WEB_SERVICE_CONNECTION_NAME;
    }

    public static String getBYERulesWebServiceConnectionName() {
        return BYE_RULES_WEB_SERVICE_CONNECTION_NAME;
    }

    public static void setTEST(Boolean _TEST) {
        BaseYearEventConstants._TEST = _TEST;
    }

    public static Boolean getTEST() {
        return _TEST;
    }

    public static String getLOV_MGMT_SERTVICE_CONNECTION_NAME() {
        return LOV_MGMT_SERTVICE_CONNECTION_NAME;
    }
}
