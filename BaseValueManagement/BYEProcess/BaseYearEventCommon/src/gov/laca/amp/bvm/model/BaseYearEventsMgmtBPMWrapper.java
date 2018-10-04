package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Timestamp;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


/**
 * @author Vijay Redla
 * @version 1.1
 * 
 * Revised by: Mark Piller - version 1.1
 * OATS #: 22496 revisions
 *         Change logic to support conversion of String to Boolean for Review Required
 * 
 */
public class BaseYearEventsMgmtBPMWrapper {
    public static final AmpLogger LOGGER =
        new AmpLogger(BaseYearEventsMgmtBPMWrapper.class);
    private static final String BPM_USER_MODIFIED_ITERATOR_NAME = "baseYearEventIterator1";


    public BaseYearEventsMgmtBPMWrapper() {
        super();
    }


    /**
     *
     * @param ainDo
     * @param input
     * @param bpmIterator
     * @return
     * 
     * Note: AinDO has reviewRequired field type as Boolean
     *       Called from InitiateBaseYearEvent class
     * 
     */
    public int loadFromBPM(AinDO ainDo , ArrayList<BaseYearEventDO> input,
                            String bpmIterator) {
        int id = 0;
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + " Initiated Load from BPM");
        DCIteratorBinding iterBinding =
            ADFUtils.findIterator(bpmIterator);
        ViewObject vo = iterBinding.getViewObject();
        long count = vo.getEstimatedRowCount();
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + " Number of Event in User Modified Iterator: " + count); 
        if(ainDo != null){
            ainDo.setAinType((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.AIN_TYPE));
            ainDo.setAin((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.AIN));
            ainDo.setAoid((BigInteger)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.AOID));
            ainDo.setDbLockStatus((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.DB_LOCK_STATUS));
            ainDo.setClusterRoute((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.CLUSTER_ROUTE));
            ainDo.setClusterRouteId((BigInteger)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.CLUSTER_ROUTE_ID));
            ainDo.setClusterRouteName((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.CLUSTER_ROUTE_NAME));
            //Type Mismatch string to BigInteger
//            ainDo.setDistrictId((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.DISTRICTID));
            ainDo.setDistrictName((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.DISTRICT_NAME));
            ainDo.setLockedByUserId((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.LOCKED_BY_USERID));
            ainDo.setRegion((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.REGION));
            ainDo.setRegionName((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.REGION_NAME));

            // OATS issue 22496
            //22496 ainDo.setReviewRequired(Boolean.valueOf((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.REVIEW_REQUIRED)));
            ainDo.setReviewRequired((Boolean)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.REVIEW_REQUIRED));

            ainDo.setEcid((String)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.ECID));
            ainDo.setIsDirty((Boolean)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.ISDIRTY));

//            LOGGER.info(this, "loadFromBPM", BaseYearEventUtils.LOGGER_PREFIX + " Set AINType: " + ainDo.getAinType(), null); 
        }
        for (int i = 0; i < count; i++) {
            BaseYearEventDO event = new BaseYearEventDO();
            Row r = iterBinding.getRowAtRangeIndex(i);
            if (r != null) {
                copyFromBPM(event, r);
                if("C".equals(event.getTransactionType())){
                    int byeId = event.getByeId().intValue();
                    if(id > byeId){
                        id = byeId;
                    }
                }
                input.add(event);
            }
        }
        return id;
    }
    
    /**
     *
     * @param event
     * @param row
     * 
     * Note:  BaseYearEventDO has field reviewRequire as Type String
     *        Called from ChangeRequestFacade class
     * 
     */
    private void copyFromBPM(BaseYearEventDO event, Row row) { 
        event.setAin((String)row.getAttribute(BaseYearEventConstants.AIN));
        event.setByeId((BigInteger)row.getAttribute(BaseYearEventConstants.BYEID));
        event.setOwnershipCode((String)row.getAttribute(BaseYearEventConstants.OWNERSHIP_CODE));
        event.setPercentOwnership((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENT_OWNERSHIP));
//        event.setEventName((String)row.getAttribute(BaseYearEventConstants.EVENT_NAME));
//        event.setEventDescription((String)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION));
        event.setDocumentNumber((BigInteger)row.getAttribute(BaseYearEventConstants.DOCUMENT_NUMBER));
        event.setSequenceNumber((BigInteger)row.getAttribute(BaseYearEventConstants.SEQUENCE_NUMBER));
        event.setBaseYear((BigInteger)row.getAttribute(BaseYearEventConstants.BASE_YEAR));
        event.setSubUnitNumber((BigInteger)row.getAttribute(BaseYearEventConstants.SUB_UNIT_NUMBER));
        event.setSubUnitDescription((String)row.getAttribute(BaseYearEventConstants.SUB_UNIT_DESCRIPTION));
        event.setPartiallyCompleteNumber((String)row.getAttribute(BaseYearEventConstants.PARTIALLY_COMPLETE_NUMBER));
        event.setBlendedValueFlag((String)row.getAttribute(BaseYearEventConstants.BLENDED_VALUE_FLAG));
        event.setPercentageAppliedToValue((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENTAGE_APPLIED_VALUE));
        event.setLandValue((Double)row.getAttribute(BaseYearEventConstants.LAND_VALUE));
        event.setImprovementValue((Double)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_VALUE));
//        event.setLandReasonCode((BigInteger)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE));
//        event.setImprovementReasonCode((BigInteger)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE));
        event.setFixtureValue((Double)row.getAttribute(BaseYearEventConstants.FIXTURE_VALUE));
        event.setRetainedLandValue((Double)row.getAttribute(BaseYearEventConstants.RETAINED_LAND_VALUE));
        event.setRetainedImprovementValue((Double)row.getAttribute(BaseYearEventConstants.RETAINED_IMPROVEMETN_VALUE));
        event.setRetainedFixtureValue((Double)row.getAttribute(BaseYearEventConstants.RETAINED_FIXTURE_VALUE));
        event.setPpAssessmentNumber((String)row.getAttribute(BaseYearEventConstants.PP_ASSESSMENT_NUMBER));
        event.setPersonalPropertyType((String)row.getAttribute(BaseYearEventConstants.PERSONAL_PROPERTY_TYPE));
        event.setPpDescription((String)row.getAttribute(BaseYearEventConstants.PPDESCRIPTION));
        event.setTotalAdjAcquisitionCost((Double)row.getAttribute(BaseYearEventConstants.TOTAL_ADJ_ACQUISITION_COST));
        event.setPercentageFixture((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENTAGE_FIXTURE));
        event.setEventSource((String)row.getAttribute(BaseYearEventConstants.EVENT_SOURCE));
        event.setVerifiedBy((String)row.getAttribute(BaseYearEventConstants.VERIFIED_BY));
        event.setByeLegacySourceSystem((String)row.getAttribute(BaseYearEventConstants.BYE_LEGACY_SOURCE_SYSTEM));
        event.setOriginalAIN((String)row.getAttribute(BaseYearEventConstants.ORIGINAL_AIN));
        event.setOriginalBYEId((BigInteger)row.getAttribute(BaseYearEventConstants.ORIGINAL_BYEID));
        event.setRollType((String)row.getAttribute(BaseYearEventConstants.ROLLTYPE));
        event.setTransactionType((String)row.getAttribute(BaseYearEventConstants.TRANSACTION_TYPE));
        event.setUserId((String)row.getAttribute(BaseYearEventConstants.USERID));
        event.setCreateBy((String)row.getAttribute(BaseYearEventConstants.CREATE_BY));
        event.setModifiedBy((String)row.getAttribute(BaseYearEventConstants.MODIFIED_BY));
        try {
            event.setEventDate(dateConveter(row, BaseYearEventConstants.EVENT_DATE));
            event.setRecordingDate(dateConveter(row, BaseYearEventConstants.RECORDING_DATE));
            event.setEffectiveBeginDate(dateConveter(row, BaseYearEventConstants.EFFECTIVE_BEGIN_DATE));
            event.setEffectiveEndDate(dateConveter(row, BaseYearEventConstants.EFFECTIVE_END_DATE));
            event.setVerificationDate(dateConveter(row, BaseYearEventConstants.VERIFICATION_DATE));
            event.setNewConstructionDate(dateConveter(row, BaseYearEventConstants.NEW_COONSTRUCTION_DATE));
            event.setCreatedData(dateConveter(row, BaseYearEventConstants.CREATED_DATE));
            event.setModifiedDate(dateConveter(row, BaseYearEventConstants.MODIFIED_DATE));
            event.setInactiveDate(dateConveter(row, BaseYearEventConstants.INACTIVEDATE));
        } catch (ParseException e) {
            LOGGER.error(this, "copyFromBPM", BaseYearEventUtils.LOGGER_PREFIX + " Date Conversion Failed", null); 
            e.printStackTrace();
        }
        
        //New Attributes
        event.setReviewRequired((String)row.getAttribute(BaseYearEventConstants.REVIEW_REQUIRED));
        event.setEventNameId((BigInteger)row.getAttribute(BaseYearEventConstants.EVENT_NAME_ID));
        event.setEventNameKey((String)row.getAttribute(BaseYearEventConstants.EVENT_NAME_KEY));
        event.setEventNameLegend((String)row.getAttribute(BaseYearEventConstants.EVENT_NAME_LEGEND));
        event.setEventDescriptionId((BigInteger)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_ID));
        event.setEventDescriptionKey((String)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_KEY));
        event.setEventDescriptionLegend((String)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_LEGEND));
        event.setLandReasonCodeId((BigInteger)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_ID));
        event.setLandReasonCodeKey((String)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_KEY));
        event.setLandReasonCodeLegend((String)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_LEGEND));
        event.setImprovementReasonCodeId((BigInteger)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_ID));
        event.setImprovementReasonCodeKey((String)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_KEY));
        event.setImprovementReasonCodeLegend((String)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_LEGEND));
        event.setCurrentOnClose((String)row.getAttribute(BaseYearEventConstants.CURRENT_ON_CLOSE));
        event.setNotes((String)row.getAttribute(BaseYearEventConstants.NOTES));
        if(event.getVerificationDate() != null){
            event.setVerified(true);
        }else{
            event.setVerified(false);
        }
    }

    private Date dateConveter(Row row,
                              String attribute) throws ParseException {
        Timestamp val = (Timestamp)row.getAttribute(attribute);
        if (val != null) {
            return new Date(val.getTime());
        }
        return null;
    }

    /**
     *
     * @param updatedList
     * @param newEvents
     * @param deletetedEvents
     * 
     * Notes: Called from InitiateBaseYearEvent class
     * 
     */
    public void loadToBPM(ArrayList<BaseYearEventDO> updatedList, ArrayList<BigInteger> newEvents, ArrayList<BigInteger> deletetedEvents) {
        DCIteratorBinding iterBinding =
            ADFUtils.findIterator(BPM_USER_MODIFIED_ITERATOR_NAME);
        Row[] rows = iterBinding.getAllRowsInRange();
        for (Row row : rows) {
            BigInteger byeid = ((BigInteger)row.getAttribute(BaseYearEventConstants.BYEID));
//            LOGGER.info(this, "loadToBPM", BaseYearEventUtils.LOGGER_PREFIX + "Found ByeID: " + byeid, null); 
            for (BaseYearEventDO event : updatedList) {
                if (byeid.equals(event.getByeId())) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Found ByeID Match for Update to BPM"); 
                    copyToBPM(row, event);
                    break;
                }
            }
        }
        //Any any New events add
        for (BigInteger newEventId : newEvents) {
            BaseYearEventDO newEvent = null;
            for (BaseYearEventDO event : updatedList) {
                if(event.getByeId().equals(newEventId)){
                    newEvent = event;
                }
            }
            if (newEvent != null) {
                String tType = newEvent.getTransactionType();
                if (tType != null && tType.equals("C")) {
                    OperationBinding createInsertNewEvent =
                        ADFUtils.findOperation("CreateInsert1");
                    createInsertNewEvent.execute();
                    rows = iterBinding.getAllRowsInRange();
                    for (Row row : rows) {
                        BigInteger byeid =
                            ((BigInteger)row.getAttribute(BaseYearEventConstants.BYEID));
                        if (byeid == null) {
                            //Found the new created Row
                            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                                        "Adding a new BYE: " +
                                        newEvent.getByeId());
                            row.setAttribute(BaseYearEventConstants.BYEID,
                                             newEvent.getByeId());
                            row.setAttribute(BaseYearEventConstants.AIN,
                                             newEvent.getAin());                              
                            row.setAttribute(BaseYearEventConstants.AOID,
                                             newEvent.getAoid());
                            copyToBPM(row, newEvent);
//                            debug(BigInteger.valueOf(-1));
                        }
                    }
                }
            }
        }

        //Any events deleted update payload
        for (BigInteger deleteEvent : deletetedEvents) {
            rows = iterBinding.getAllRowsInRange();
            int index = 0;
            for (Row row : rows) {
                BigInteger byeid =
                    ((BigInteger)row.getAttribute(BaseYearEventConstants.BYEID));
                if (byeid.equals(deleteEvent)) {
                    iterBinding.setCurrentRowIndexInRange(index);
                    iterBinding.removeCurrentRow();
                }
                index++;
            }
        }
    }

    private void debug(BigInteger id){
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Debug Info for BYE ID: " +id); 
        DCIteratorBinding iterBinding =
            ADFUtils.findIterator(BPM_USER_MODIFIED_ITERATOR_NAME);
        Row[] rows = iterBinding.getAllRowsInRange();
        for (Row row : rows) {
            BigInteger byeId = ((BigInteger)row.getAttribute(BaseYearEventConstants.BYEID));
            if(byeId.equals(id)){
                String Ain = (String)row.getAttribute(BaseYearEventConstants.AIN);
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.AIN + ": "+ Ain); 
                BigInteger aoid = (BigInteger)row.getAttribute(BaseYearEventConstants.AOID);
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.AOID + ": "+ aoid); 
                String OwnershipCode = ((String)row.getAttribute(BaseYearEventConstants.OWNERSHIP_CODE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.OWNERSHIP_CODE + ": "+ OwnershipCode); 
                BigDecimal PercentOwnership= ((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENT_OWNERSHIP));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PERCENT_OWNERSHIP + ": "+ PercentOwnership); 
                BigInteger DocumentNumber= ((BigInteger)row.getAttribute(BaseYearEventConstants.DOCUMENT_NUMBER));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.DOCUMENT_NUMBER + ": "+ DocumentNumber); 
                BigInteger SequenceNumber =((BigInteger)row.getAttribute(BaseYearEventConstants.SEQUENCE_NUMBER));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.SEQUENCE_NUMBER + ": "+ SequenceNumber); 
                BigInteger BaseYear = ((BigInteger)row.getAttribute(BaseYearEventConstants.BASE_YEAR));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.BASE_YEAR + ": "+ BaseYear); 
                BigInteger SubUnitNumber =((BigInteger)row.getAttribute(BaseYearEventConstants.SUB_UNIT_NUMBER));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.SUB_UNIT_NUMBER + ": "+ SubUnitNumber); 
                String SubUnitDescription = ((String)row.getAttribute(BaseYearEventConstants.SUB_UNIT_DESCRIPTION));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.SUB_UNIT_DESCRIPTION + ": "+ SubUnitDescription); 
                String PartiallyCompleteNumber = ((String)row.getAttribute(BaseYearEventConstants.PARTIALLY_COMPLETE_NUMBER));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PARTIALLY_COMPLETE_NUMBER + ": "+ PartiallyCompleteNumber); 
                String BlendedValueFlag =((String)row.getAttribute(BaseYearEventConstants.BLENDED_VALUE_FLAG));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.BLENDED_VALUE_FLAG + ": "+ BlendedValueFlag); 
                BigDecimal PercentageAppliedToValue = ((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENTAGE_APPLIED_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PERCENTAGE_APPLIED_VALUE + ": "+ PercentageAppliedToValue); 
                Double LandValue = ((Double)row.getAttribute(BaseYearEventConstants.LAND_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.LAND_VALUE + ": "+ LandValue); 
                Double ImprovementValue = ((Double)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.IMPROVEMENT_VALUE + ": "+ ImprovementValue); 
                Double FixtureValue = ((Double)row.getAttribute(BaseYearEventConstants.FIXTURE_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.FIXTURE_VALUE + ": "+ FixtureValue); 
                Double RetainedLandValue = ((Double)row.getAttribute(BaseYearEventConstants.RETAINED_LAND_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.RETAINED_LAND_VALUE + ": "+ RetainedLandValue); 
                Double RetainedImprovementValue = ((Double)row.getAttribute(BaseYearEventConstants.RETAINED_IMPROVEMETN_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.RETAINED_IMPROVEMETN_VALUE + ": "+ RetainedImprovementValue); 
                Double RetainedFixtureValue = ((Double)row.getAttribute(BaseYearEventConstants.RETAINED_FIXTURE_VALUE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.RETAINED_FIXTURE_VALUE + ": "+ RetainedFixtureValue); 
                String PpAssessmentNumber = ((String)row.getAttribute(BaseYearEventConstants.PP_ASSESSMENT_NUMBER));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PP_ASSESSMENT_NUMBER + ": "+ PpAssessmentNumber); 
                String PersonalPropertyType = ((String)row.getAttribute(BaseYearEventConstants.PERSONAL_PROPERTY_TYPE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PERSONAL_PROPERTY_TYPE + ": "+ PersonalPropertyType); 
                String PpDescription = ((String)row.getAttribute(BaseYearEventConstants.PPDESCRIPTION));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PPDESCRIPTION + ": "+ PpDescription); 
                Double TotalAdjAcquisitionCost = ((Double)row.getAttribute(BaseYearEventConstants.TOTAL_ADJ_ACQUISITION_COST));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.TOTAL_ADJ_ACQUISITION_COST + ": "+ TotalAdjAcquisitionCost); 
                BigDecimal PercentageFixture = ((BigDecimal)row.getAttribute(BaseYearEventConstants.PERCENTAGE_FIXTURE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.PERCENTAGE_FIXTURE + ": "+ PercentageFixture); 
                String EventSource = ((String)row.getAttribute(BaseYearEventConstants.EVENT_SOURCE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_SOURCE + ": "+ EventSource); 
                String VerifiedBy = ((String)row.getAttribute(BaseYearEventConstants.VERIFIED_BY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.VERIFIED_BY + ": "+ VerifiedBy); 
                String ByeLegacySourceSystem = ((String)row.getAttribute(BaseYearEventConstants.BYE_LEGACY_SOURCE_SYSTEM));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.BYE_LEGACY_SOURCE_SYSTEM + ": "+ ByeLegacySourceSystem); 
                String OriginalAIN = ((String)row.getAttribute(BaseYearEventConstants.ORIGINAL_AIN));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.ORIGINAL_AIN + ": "+ OriginalAIN); 
                BigInteger OriginalBYEId = ((BigInteger)row.getAttribute(BaseYearEventConstants.ORIGINAL_BYEID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.ORIGINAL_AIN + ": "+ OriginalBYEId); 
                String RollType =((String)row.getAttribute(BaseYearEventConstants.ROLLTYPE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.ORIGINAL_AIN + ": "+ RollType); 
                String TransactionType = ((String)row.getAttribute(BaseYearEventConstants.TRANSACTION_TYPE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.ORIGINAL_AIN + ": "+ TransactionType); 
                String UserId = ((String)row.getAttribute(BaseYearEventConstants.USERID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.USERID + ": "+ UserId); 
                String CreatedBy = ((String)row.getAttribute(BaseYearEventConstants.CREATE_BY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.CREATE_BY + ": "+ CreatedBy); 
                String ModifiedBy = ((String)row.getAttribute(BaseYearEventConstants.MODIFIED_BY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.MODIFIED_BY + ": "+ ModifiedBy); 
                try {
                    Date EventDate =(dateConveter(row, BaseYearEventConstants.EVENT_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_DATE + ": "+ EventDate); 
                    Date RecordingDate = (dateConveter(row, BaseYearEventConstants.RECORDING_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.RECORDING_DATE + ": "+ RecordingDate); 
                    Date EffectiveBeginDate = (dateConveter(row, BaseYearEventConstants.EFFECTIVE_BEGIN_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EFFECTIVE_BEGIN_DATE + ": "+ EffectiveBeginDate); 
                    Date EffectiveEndDate = (dateConveter(row, BaseYearEventConstants.EFFECTIVE_END_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EFFECTIVE_END_DATE + ": "+ EffectiveEndDate); 
                    Date VerificationDate = (dateConveter(row, BaseYearEventConstants.VERIFICATION_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.VERIFICATION_DATE + ": "+ VerificationDate); 
                    Date NewConstructionDate = (dateConveter(row, BaseYearEventConstants.NEW_COONSTRUCTION_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.NEW_COONSTRUCTION_DATE + ": "+ NewConstructionDate); 
                    Date CreatedData = (dateConveter(row, BaseYearEventConstants.CREATED_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.CREATED_DATE + ": "+ CreatedData); 
                    Date ModifiedDate = (dateConveter(row, BaseYearEventConstants.MODIFIED_DATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.MODIFIED_DATE + ": "+ ModifiedDate); 
                    Date InactiveDate = (dateConveter(row, BaseYearEventConstants.INACTIVEDATE));
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.INACTIVEDATE + ": "+ InactiveDate); 
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                //New Attributes
                String ReviewRequired = ((String)row.getAttribute(BaseYearEventConstants.REVIEW_REQUIRED));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.REVIEW_REQUIRED + ": "+ ReviewRequired); 
                BigInteger EventNameId = ((BigInteger)row.getAttribute(BaseYearEventConstants.EVENT_NAME_ID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_NAME_ID + ": "+ EventNameId); 
                String EventNameKey = ((String)row.getAttribute(BaseYearEventConstants.EVENT_NAME_KEY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_NAME_KEY + ": "+ EventNameKey); 
                String EventNameLegend = ((String)row.getAttribute(BaseYearEventConstants.EVENT_NAME_LEGEND));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_NAME_LEGEND + ": "+ EventNameLegend); 
                BigInteger EventDescriptionId = ((BigInteger)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_ID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_DESCRIPTION_ID + ": "+ EventDescriptionId); 
                String EventDescriptionKey = ((String)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_KEY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_DESCRIPTION_KEY + ": "+ EventDescriptionKey); 
                String EventDescriptionLegend = ((String)row.getAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_LEGEND));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.EVENT_DESCRIPTION_LEGEND + ": "+ EventDescriptionLegend); 
                BigInteger LandReasonCodeId = ((BigInteger)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_ID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.LAND_REASON_CODE_ID + ": "+ LandReasonCodeId); 
                String LandReasonCodeKey = ((String)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_KEY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.LAND_REASON_CODE_KEY + ": "+ LandReasonCodeKey); 
                String LandReasonCodeLegend = ((String)row.getAttribute(BaseYearEventConstants.LAND_REASON_CODE_LEGEND));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.LAND_REASON_CODE_LEGEND + ": "+ LandReasonCodeLegend); 
                BigInteger ImprovementReasonCodeId = ((BigInteger)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_ID));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.IMPROVEMENT_REASON_CODE_ID + ": "+ ImprovementReasonCodeId); 
                String ImprovementReasonCodeKey = ((String)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_KEY));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.IMPROVEMENT_REASON_CODE_KEY + ": "+ ImprovementReasonCodeKey); 
                String ImprovementReasonCodeLegend = ((String)row.getAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_LEGEND));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.IMPROVEMENT_REASON_CODE_LEGEND + ": "+ ImprovementReasonCodeLegend); 
                String CurrentOnClose = ((String)row.getAttribute(BaseYearEventConstants.CURRENT_ON_CLOSE));
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.CURRENT_ON_CLOSE + ": "+ CurrentOnClose); 
                String Notes = ((String)row.getAttribute(BaseYearEventConstants.NOTES));                
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + BaseYearEventConstants.NOTES + ": "+ Notes); 
            }
        }
    }
    /**
     * Copy to the modified iterator BPM_USER_MODIFIED_ITERATOR_NAME
     * 
     * 
     * 
     */
    private void copyToBPM(Row row, BaseYearEventDO event) {
        row.setAttribute(BaseYearEventConstants.OWNERSHIP_CODE, event.getOwnershipCode());
        row.setAttribute(BaseYearEventConstants.PERCENT_OWNERSHIP, event.getPercentOwnership());
//        row.setAttribute(BaseYearEventConstants.EVENT_NAME, event.getEventName());
//        row.setAttribute(BaseYearEventConstants.EVENT_DESCRIPTION, event.getEventDescription());
        row.setAttribute(BaseYearEventConstants.EVENT_DATE, event.getEventDate());
        row.setAttribute(BaseYearEventConstants.RECORDING_DATE, event.getRecordingDate());
        row.setAttribute(BaseYearEventConstants.DOCUMENT_NUMBER, event.getDocumentNumber());
        row.setAttribute(BaseYearEventConstants.SEQUENCE_NUMBER, event.getSequenceNumber());
        row.setAttribute(BaseYearEventConstants.BASE_YEAR, event.getBaseYear());
        row.setAttribute(BaseYearEventConstants.SUB_UNIT_NUMBER, event.getSubUnitNumber());
        row.setAttribute(BaseYearEventConstants.SUB_UNIT_DESCRIPTION,
                           event.getSubUnitDescription());
        row.setAttribute(BaseYearEventConstants.PARTIALLY_COMPLETE_NUMBER,
                           event.getPartiallyCompleteNumber());
        row.setAttribute(BaseYearEventConstants.BLENDED_VALUE_FLAG, event.getBlendedValueFlag());
        row.setAttribute(BaseYearEventConstants.PERCENTAGE_APPLIED_VALUE,
                           event.getPercentageAppliedToValue());
        row.setAttribute(BaseYearEventConstants.LAND_VALUE, event.getLandValue());
        row.setAttribute(BaseYearEventConstants.IMPROVEMENT_VALUE, event.getImprovementValue());
//        row.setAttribute(BaseYearEventConstants.LAND_REASON_CODE, event.getLandReasonCode());
//        row.setAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE,
//                           event.getImprovementReasonCode());
        row.setAttribute(BaseYearEventConstants.FIXTURE_VALUE, event.getFixtureValue());
        row.setAttribute(BaseYearEventConstants.RETAINED_LAND_VALUE, event.getRetainedLandValue());
        row.setAttribute(BaseYearEventConstants.RETAINED_IMPROVEMETN_VALUE,
                           event.getRetainedImprovementValue());
        row.setAttribute(BaseYearEventConstants.RETAINED_FIXTURE_VALUE,
                           event.getRetainedFixtureValue());
        row.setAttribute(BaseYearEventConstants.PP_ASSESSMENT_NUMBER,
                           event.getPpAssessmentNumber());
        row.setAttribute(BaseYearEventConstants.PERSONAL_PROPERTY_TYPE,
                           event.getPersonalPropertyType());
        row.setAttribute(BaseYearEventConstants.PPDESCRIPTION, event.getPpDescription());
        row.setAttribute(BaseYearEventConstants.TOTAL_ADJ_ACQUISITION_COST,
                           event.getTotalAdjAcquisitionCost());
        row.setAttribute(BaseYearEventConstants.PERCENTAGE_FIXTURE, event.getPercentageFixture());
        row.setAttribute(BaseYearEventConstants.EFFECTIVE_BEGIN_DATE,
                           event.getEffectiveBeginDate());
        row.setAttribute(BaseYearEventConstants.EFFECTIVE_END_DATE, event.getEffectiveEndDate());
        row.setAttribute(BaseYearEventConstants.VERIFICATION_DATE, event.getVerificationDate());
        row.setAttribute(BaseYearEventConstants.EVENT_SOURCE, event.getEventSource());
        row.setAttribute(BaseYearEventConstants.VERIFIED_BY, event.getVerifiedBy());
        row.setAttribute(BaseYearEventConstants.BYE_LEGACY_SOURCE_SYSTEM,
                           event.getByeLegacySourceSystem());
        row.setAttribute(BaseYearEventConstants.ORIGINAL_AIN, event.getOriginalAIN());
        row.setAttribute(BaseYearEventConstants.ORIGINAL_BYEID, event.getOriginalBYEId());
        row.setAttribute(BaseYearEventConstants.ROLLTYPE, event.getRollType());
        row.setAttribute(BaseYearEventConstants.TRANSACTION_TYPE, event.getTransactionType());
        row.setAttribute(BaseYearEventConstants.USERID, event.getUserId());
        row.setAttribute(BaseYearEventConstants.NEW_COONSTRUCTION_DATE,
                           event.getNewConstructionDate());
        row.setAttribute(BaseYearEventConstants.CREATE_BY, event.getCreateBy());
        row.setAttribute(BaseYearEventConstants.CREATED_DATE, event.getCreatedData());
        row.setAttribute(BaseYearEventConstants.MODIFIED_BY, event.getModifiedBy());
        row.setAttribute(BaseYearEventConstants.MODIFIED_DATE, event.getModifiedDate());
        row.setAttribute(BaseYearEventConstants.INACTIVEDATE, event.getInactiveDate());
        
        //New Attributes
        row.setAttribute(BaseYearEventConstants.REVIEW_REQUIRED, event.getReviewRequired());
        row.setAttribute(BaseYearEventConstants.EVENT_NAME_ID, event.getEventNameId());
        row.setAttribute(BaseYearEventConstants.EVENT_NAME_KEY,  event.getEventNameKey());
        row.setAttribute(BaseYearEventConstants.EVENT_NAME_LEGEND, event.getEventNameLegend());
        row.setAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_ID, event.getEventDescriptionId());
        row.setAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_KEY, event.getEventDescriptionKey());
        row.setAttribute(BaseYearEventConstants.EVENT_DESCRIPTION_LEGEND, event.getEventDescriptionLegend());
        row.setAttribute(BaseYearEventConstants.LAND_REASON_CODE_ID, event.getLandReasonCodeId());
        row.setAttribute(BaseYearEventConstants.LAND_REASON_CODE_KEY, event.getLandReasonCodeKey());
        row.setAttribute(BaseYearEventConstants.LAND_REASON_CODE_LEGEND, event.getLandReasonCodeLegend());
        row.setAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_ID, event.getImprovementReasonCodeId());
        row.setAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_KEY, event.getImprovementReasonCodeKey());
        row.setAttribute(BaseYearEventConstants.IMPROVEMENT_REASON_CODE_LEGEND, event.getImprovementReasonCodeLegend());
        row.setAttribute(BaseYearEventConstants.CURRENT_ON_CLOSE, event.getCurrentOnClose());
        row.setAttribute(BaseYearEventConstants.NOTES, event.getNotes());
    }

}