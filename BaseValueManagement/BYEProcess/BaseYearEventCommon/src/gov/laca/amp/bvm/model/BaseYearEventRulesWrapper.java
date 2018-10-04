package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.WebServiceEndPoints;
import gov.laca.amp.fwk.logger.AmpLogger;

import gov.laca.amp.fwk.util.ADFUtils;

import gov.lacounty.assessor.amp.data.bvm.factoredbaseyearvalue.v1.FactoredBaseYearValueRequest;
import gov.lacounty.assessor.amp.data.bvm.factoredbaseyearvalue.v1.FactoredBaseYearValueResponse;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventResp;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventRespList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueTotalList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseYearValueRequest;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseYearValueResponse;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendObjectList;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.BaseYearEventErr;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.BaseYearEventErrList;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.BaseYearEventList;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.ValidateBaseYearEventRequest;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.ValidateBaseYearEventResponse;
import gov.lacounty.assessor.amp.type.bvm.baseyearevent.v1.BaseYearEvent;
import gov.lacounty.assessor.amp.type.common.fault.v1.Fault;
import gov.lacounty.assessor.amp.type.common.requestheader.v1.RequestHeader;
import gov.lacounty.assessor.amp.wsdl.bvm.baseyeareventrulesservice.v1.BaseYearEventRulesService;
import gov.lacounty.assessor.amp.wsdl.bvm.baseyeareventrulesservice.v1.BaseYearEventRulesService_ep;
import gov.lacounty.assessor.amp.wsdl.bvm.baseyeareventrulesservice.v1.FaultMessage;

import java.math.BigInteger;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import javax.xml.namespace.QName;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

/**
 * @author Vijay Redla
 * @version 2.0
 */
public class BaseYearEventRulesWrapper {
    public static final AmpLogger LOGGER =
        new AmpLogger(BaseYearEventRulesWrapper.class);
    private BaseYearEventRulesService_ep baseYearEventRulesService_ep;
    private java.util.List<BaseYearEventErr> errorEvents = null;

    public BaseYearEventRulesWrapper() {
        super();
    }
    
    public TrendBaseYearValueResponse trendBaseYearValues(BigInteger rollYear, ArrayList<BaseYearEventDO> opaInput) throws AmpBaseYearEventException {
        final BaseYearEventRulesService baseYearEventRulesService =
            getServiceHandle();
        if(baseYearEventRulesService == null){
            LOGGER.error(this, "OPA validate",
                        BaseYearEventUtils.LOGGER_PREFIX +
                        "OPA Service Handler is NULL", null); 
            return null;
        }
        TrendBaseYearValueRequest trendBaseYearValueRequest =
            new TrendBaseYearValueRequest();
        RequestHeader header = trendBaseYearValueRequest.getHeader();
//        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
//        header.setRequestId(BaseYearEventUtils.getRequestID());
        
        trendBaseYearValueRequest.setTargetRollYear(rollYear);
        List<gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventList> baseYearEventList = trendBaseYearValueRequest.getBaseYearEventList();
        gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventList eventList = new gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventList();
        List<BaseYearEvent> baseYearEvents = eventList.getBaseYearEvent();

        for (BaseYearEventDO baseYearEventDO : opaInput) {
            BaseYearEvent event = new BaseYearEvent();
            copyDOtoOPA(event, baseYearEventDO);
            baseYearEvents.add(event);
        }
        baseYearEventList.add(eventList);
        try {
            return baseYearEventRulesService.trendBaseYearValue(trendBaseYearValueRequest);
        } catch (FaultMessage msg) {
            LOGGER.error(this, "trendBaseYearValues",
                        BaseYearEventUtils.LOGGER_PREFIX +
                        "Fault calling OPA Service for TrendBaseYearValue", null);            
            Fault fault = msg.getFaultInfo();
            throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                fault.getFaultType());
        }
    }

    public Boolean validate(ArrayList<BaseYearEventDO> opaInput,
                                               String type) throws AmpBaseYearEventException {
        final BaseYearEventRulesService baseYearEventRulesService =
            getServiceHandle();
        if(baseYearEventRulesService == null){
            LOGGER.error(this, "OPA validate",
                        BaseYearEventUtils.LOGGER_PREFIX +
                        "OPA Service Handler is NULL", null); 
            return null;
        }
        try {   
            ValidateBaseYearEventRequest request =
                new ValidateBaseYearEventRequest();
            final BaseYearEventList baseYearEventList = new BaseYearEventList();
            java.util.List<BaseYearEvent> baseYearEvents =
                baseYearEventList.getBaseYearEvent();
            String tg= ADFUtils.getPageFlowStringValue("sPageAction");
            for (BaseYearEventDO baseYearEventDO : opaInput) {
                if("page".equalsIgnoreCase(tg)){
                    if(baseYearEventDO.getAin()==null || baseYearEventDO.getAin()==""){
                        BaseYearEvent event = new BaseYearEvent();
                        copyDOtoOPA(event, baseYearEventDO);
                        baseYearEvents.add(event); 
                    }
                
                }
                else{
                    BaseYearEvent event = new BaseYearEvent();
                    copyDOtoOPA(event, baseYearEventDO);
                    baseYearEvents.add(event);
                }
            }
            request.setBaseYearEventList(baseYearEventList);
            request.setValidationType(type);
            //TODO Setting application ID is causing NPE after refactoring --vredla 05/16/2016
//            RequestHeader header = request.getHeader();
//            header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
//            header.setRequestId(BaseYearEventUtils.getRequestID());
            ValidateBaseYearEventResponse response =
                baseYearEventRulesService.validateBaseYearEevent(request);
            if (response != null) {
                BaseYearEventErrList baseYearEventErrList =
                    response.getBaseYearEventErrList();
                if (baseYearEventErrList != null) {
                    errorEvents = baseYearEventErrList.getBaseYearEvent();
                    if (errorEvents.size() > 0) {
                        // Null means there are errors
                        return Boolean.FALSE;
                    } else {
                        //Validation passed copy back event changes
                        if (BaseYearEventConstants.OPA_CALL_ONCLOSE.equals(type)) {
                            BaseYearEventList eventList =
                                response.getBaseYearEventList();
                            java.util.List<BaseYearEvent> events =
                                eventList.getBaseYearEvent();
                            for (BaseYearEvent event : events) {
                                final BigInteger id = event.getByeId();
                                for (BaseYearEventDO eventDO : opaInput) {
                                    if (eventDO.getByeId().equals(id)) {
                                        copyOPAtoDO(eventDO, event);
                                        break;
                                    }
                                }
                            }
                        }
                        return Boolean.TRUE;
                    }
                } else {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                                "OPA Service call Returns NULL");
                }
            }else{
                LOGGER.error(this, "OPA validate",
                            BaseYearEventUtils.LOGGER_PREFIX +
                            "Response is null for OPA Service", null);
            }
        } catch (FaultMessage msg) {
            LOGGER.error(this, "OPA validate",
                        BaseYearEventUtils.LOGGER_PREFIX +
                        "Fault calling OPA Service", null);            
            Fault fault = msg.getFaultInfo();
            throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                fault.getFaultType());
        }
        return Boolean.FALSE;
    }

    private BaseYearEventRulesService getServiceHandle() {
        if (baseYearEventRulesService_ep == null) {
            URL wsdlLocationURL =
                WebServiceEndPoints.getWsdlUrlFromConnections(BaseYearEventConstants.getBYERulesWebServiceConnectionName());
            baseYearEventRulesService_ep =
                    new BaseYearEventRulesService_ep(wsdlLocationURL,
                                                     new QName("http://assessor.lacounty.gov/amp/wsdl/bvm/BaseYearEventRulesService/v1.0",
                                                               "BaseYearEventRulesService_ep"));
        }
        SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });             
        return baseYearEventRulesService_ep.getBaseYearEventRulesService_pt(securityFeatures);
    }

    private void copyDOtoOPA(BaseYearEvent bye,
                             BaseYearEventDO byeDO) throws AmpBaseYearEventException {
        bye.setAin(byeDO.getAin());
        bye.setBaseYear(byeDO.getBaseYear());
        bye.setBlendedValueFlag(byeDO.getBlendedValueFlag());
        bye.setByeId(byeDO.getByeId());
        bye.setByeLegacySourceSystem(byeDO.getByeLegacySourceSystem());
        bye.setCreateBy(byeDO.getCreateBy());
        bye.setCreatedDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getCreatedData()));
        bye.setDocumentNumber(byeDO.getDocumentNumber());
        bye.setEffectiveBeginDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getEffectiveBeginDate()));
        bye.setEffectiveEndDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getEffectiveEndDate()));
        bye.setEventDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getEventDate()));
//        bye.setEventDescription(byeDO.getEventDescription());
//        bye.setEventName(byeDO.getEventName());
        bye.setEventSource(byeDO.getEventSource());
        bye.setFixtureValue(byeDO.getFixtureValue());
//        bye.setImprovementReasonCode(byeDO.getImprovementReasonCode());
        bye.setImprovementValue(byeDO.getImprovementValue());
//        bye.setLandReasonCode(byeDO.getLandReasonCode());
        bye.setLandValue(byeDO.getLandValue());
        bye.setModifiedBy(byeDO.getModifiedBy());
        bye.setModifiedDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getModifiedDate()));
        bye.setNewConstructionDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getNewConstructionDate()));
        bye.setOriginalAIN(byeDO.getOriginalAIN());
        bye.setOriginalBYEId(byeDO.getOriginalBYEId());
        bye.setOwnershipCode(byeDO.getOwnershipCode());
        bye.setPartiallyCompleteNumber(byeDO.getPartiallyCompleteNumber());
        bye.setPercentOwnership(byeDO.getPercentOwnership());
        bye.setPercentageAppliedToValue(byeDO.getPercentageAppliedToValue());
        bye.setPercentageFixture(byeDO.getPercentageFixture());
        bye.setPersonalPropertyType(byeDO.getPersonalPropertyType());
        bye.setPpAssessmentNumber(byeDO.getPpAssessmentNumber());
        bye.setPpDescription(byeDO.getPpDescription());
        bye.setRecordingDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getRecordingDate()));
        bye.setRetainedFixtureValue(byeDO.getRetainedFixtureValue());
        bye.setRetainedImprovementValue(byeDO.getRetainedImprovementValue());
        bye.setRetainedLandValue(byeDO.getRetainedLandValue());
        bye.setRollType(byeDO.getRollType());
        bye.setSequenceNumber(byeDO.getSequenceNumber());
        bye.setSubUnitDescription(byeDO.getSubUnitDescription());
        bye.setSubUnitNumber(byeDO.getSubUnitNumber());
        bye.setTotalAdjAcquisitionCost(byeDO.getTotalAdjAcquisitionCost());
        bye.setTransactionType(byeDO.getTransactionType());
        bye.setUserId(byeDO.getUserId());
        bye.setVerificationDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getVerificationDate()));
        bye.setVerifiedBy(byeDO.getVerifiedBy());
        bye.setInactiveDate(BaseYearEventUtils.convertDateToXMLGregorianCalendar(byeDO.getInactiveDate()));
        
        //New Attributes
        bye.setAoid(byeDO.getAoid());
        bye.setReviewRequired(byeDO.getReviewRequired());
        bye.setEventNameId(byeDO.getEventNameId());
        bye.setEventNameKey(byeDO.getEventNameKey());
        bye.setEventNameLegend(byeDO.getEventNameLegend());
        bye.setEventDescriptionId(byeDO.getEventDescriptionId());
        bye.setEventDescriptionKey(byeDO.getEventDescriptionKey());
        bye.setEventDescriptionLegend(byeDO.getEventDescriptionLegend());
        bye.setLandReasonCodeId(byeDO.getLandReasonCodeId());
        bye.setLandReasonCodeKey(byeDO.getLandReasonCodeKey());
        bye.setLandReasonCodeLegend(byeDO.getLandReasonCodeLegend());
        bye.setImprovementReasonCodeId(byeDO.getImprovementReasonCodeId());
        bye.setImprovementReasonCodeKey(byeDO.getImprovementReasonCodeKey());
        bye.setImprovementReasonCodeLegend(byeDO.getImprovementReasonCodeLegend());
        bye.setCurrentOnClose(byeDO.getCurrentOnClose());
        bye.setNotes(byeDO.getNotes());
    }

    private void copyOPAtoDO(BaseYearEventDO byeDO, BaseYearEvent bye) {
//        if(bye.getAin() != null){
//            byeDO.setAin(bye.getAin());
//        }
        if(bye.getBaseYear() != null && !"".equals(bye.getBaseYear())){
            byeDO.setBaseYear(bye.getBaseYear());
        }
        if(bye.getBlendedValueFlag() != null && !"".equals(bye.getBlendedValueFlag())){
            byeDO.setBlendedValueFlag(bye.getBlendedValueFlag());
        }
//        if(bye.getByeId() != null){
//            byeDO.setByeId(bye.getByeId());
//        }
        if(bye.getByeLegacySourceSystem() != null && !"".equals(bye.getByeLegacySourceSystem())){
            byeDO.setByeLegacySourceSystem(bye.getByeLegacySourceSystem());
        }
        if(bye.getCreateBy() != null && !"".equals(bye.getCreateBy())){
            byeDO.setCreateBy(bye.getCreateBy());
        }
        if(bye.getCreatedDate() != null && !"".equals(bye.getCreatedDate())){
            byeDO.setCreatedData(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getCreatedDate()));
        }
        if(bye.getDocumentNumber() != null && !"".equals(bye.getDocumentNumber())){
            byeDO.setDocumentNumber(bye.getDocumentNumber());
        }
        if(bye.getEffectiveBeginDate() != null && !"".equals(bye.getEffectiveBeginDate())){
            byeDO.setEffectiveBeginDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getEffectiveBeginDate()));
        }
        if(bye.getEffectiveEndDate() != null && !"".equals(bye.getEffectiveEndDate())){
            byeDO.setEffectiveEndDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getEffectiveEndDate()));
        }
        if(bye.getEventDate() != null && !"".equals(bye.getEventDate())){
            byeDO.setEventDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getEventDate()));
        }
//        byeDO.setEventDescription(bye.getEventDescription());
//        byeDO.setEventName(bye.getEventName());
        if(bye.getEventSource() != null && !"".equals(bye.getEventSource())){
            byeDO.setEventSource(bye.getEventSource());
        }
        // Defect # 31896 fix
        Double zeroVal=new Double(0.0);
//        if(bye.getFixtureValue() != null && !"".equals(bye.getFixtureValue())){
//            byeDO.setFixtureValue(bye.getFixtureValue());
//        }
        if(bye.getFixtureValue() != null && !"".equals(bye.getFixtureValue()) && !zeroVal.equals(bye.getFixtureValue())){
            byeDO.setFixtureValue(bye.getFixtureValue());
        }
//        byeDO.setImprovementReasonCode(bye.getImprovementReasonCode());
        /*Issue 30836   Changes*if(bye.getImprovementValue() != null && !"".equals(bye.getImprovementValue())){
            byeDO.setImprovementValue(bye.getImprovementValue());

        if(bye.getLandValue() != null && !"".equals(bye.getLandValue())){
            byeDO.setLandValue(bye.getLandValue());
        }
        }*/
        // Issue 30836 Start
        
        if(bye.getImprovementValue() != null && !"".equals(bye.getImprovementValue()) && !zeroVal.equals(bye.getImprovementValue())){
                byeDO.setImprovementValue(bye.getImprovementValue());
            }
        if(bye.getLandValue() != null && !"".equals(bye.getLandValue()) && !zeroVal.equals(bye.getLandValue())){
                    byeDO.setLandValue(bye.getLandValue());
        }
        // Issue 30836 End
                    
                    
//        byeDO.setLandReasonCode(bye.getLandReasonCode());
      
        if(bye.getModifiedBy() != null && !"".equals(bye.getModifiedBy())){
            byeDO.setModifiedBy(bye.getModifiedBy());
        }
        if(bye.getModifiedDate() != null && !"".equals(bye.getModifiedDate())){
            byeDO.setModifiedDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getModifiedDate()));
        }
        if(bye.getNewConstructionDate() != null && !"".equals(bye.getNewConstructionDate())){
            byeDO.setNewConstructionDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getNewConstructionDate()));
        }
        if(bye.getOriginalAIN() != null && !"".equals(bye.getOriginalAIN())){
            byeDO.setOriginalAIN(bye.getOriginalAIN());
        }
        if(bye.getOriginalBYEId() != null && !"".equals(bye.getOriginalBYEId())){
            byeDO.setOriginalBYEId(bye.getOriginalBYEId());
        }
        if(bye.getOwnershipCode() != null && !"".equals(bye.getOwnershipCode())){
//                        LOGGER.error(this, "Copy",
//                                    BaseYearEventUtils.LOGGER_PREFIX +
//                                    "HERE HERE Ownership Code: " + bye.getOwnershipCode(), null); 
            byeDO.setOwnershipCode(bye.getOwnershipCode());
        }
        if(bye.getPartiallyCompleteNumber() != null && !"".equals(bye.getPartiallyCompleteNumber())){
            byeDO.setPartiallyCompleteNumber(bye.getPartiallyCompleteNumber());
        }
        if(bye.getPercentOwnership() != null && !"".equals(bye.getPercentOwnership())){
            byeDO.setPercentOwnership(bye.getPercentOwnership());
        }
        if(bye.getPercentageAppliedToValue() != null && !"".equals(bye.getPercentageAppliedToValue())){
            byeDO.setPercentageAppliedToValue(bye.getPercentageAppliedToValue());
        }
        // Defect # 31896 fix
//        if(bye.getPercentageFixture() != null && !"".equals(bye.getPercentageFixture())){
//            byeDO.setPercentageFixture(bye.getPercentageFixture());
//        }
        if(bye.getPercentageFixture() != null && !"".equals(bye.getPercentageFixture()) && !zeroVal.equals(bye.getPercentageFixture())){
            byeDO.setPercentageFixture(bye.getPercentageFixture());
        }
        if(bye.getPersonalPropertyType() != null && !"".equals(bye.getPersonalPropertyType())){
            byeDO.setPersonalPropertyType(bye.getPersonalPropertyType());
        }
        if(bye.getPpAssessmentNumber() != null && !"".equals(bye.getPpAssessmentNumber())){
            byeDO.setPpAssessmentNumber(bye.getPpAssessmentNumber());
        }
        if(bye.getPpDescription() != null && !"".equals(bye.getPpDescription())){
            byeDO.setPpDescription(bye.getPpDescription());
        }
        if(bye.getRecordingDate() != null && !"".equals(bye.getRecordingDate())){
            byeDO.setRecordingDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getRecordingDate()));
        }
        //Defect 14540 change -Vijay 09/19/2016
        byeDO.setRetainedFixtureValue(bye.getRetainedFixtureValue());
        byeDO.setRetainedImprovementValue(bye.getRetainedImprovementValue());
        byeDO.setRetainedLandValue(bye.getRetainedLandValue());
        
        if(bye.getRollType() != null && !"".equals(bye.getRollType())){
            byeDO.setRollType(bye.getRollType());
        }
        if(bye.getSequenceNumber() != null && !"".equals(bye.getSequenceNumber())){
            byeDO.setSequenceNumber(bye.getSequenceNumber());
        }
        if(bye.getSubUnitDescription() != null && !"".equals(bye.getSubUnitDescription())){
            byeDO.setSubUnitDescription(bye.getSubUnitDescription());
        }
        if(bye.getSubUnitNumber() != null && !"".equals(bye.getSubUnitNumber())){
            byeDO.setSubUnitNumber(bye.getSubUnitNumber());
        }
        // Defect # 31896 fix
//        if(bye.getTotalAdjAcquisitionCost() != null && !"".equals(bye.getTotalAdjAcquisitionCost())){
//            byeDO.setTotalAdjAcquisitionCost(bye.getTotalAdjAcquisitionCost());
//        }
        if(bye.getTotalAdjAcquisitionCost() != null && !"".equals(bye.getTotalAdjAcquisitionCost())&& !zeroVal.equals(bye.getTotalAdjAcquisitionCost())){
            byeDO.setTotalAdjAcquisitionCost(bye.getTotalAdjAcquisitionCost());
        }
//        if(bye.getTransactionType() != null){
//            byeDO.setTransactionType(bye.getTransactionType());
//        }
        if(bye.getUserId() != null && !"".equals(bye.getUserId())){
            byeDO.setUserId(bye.getUserId());
        }
        if(bye.getVerificationDate() != null && !"".equals(bye.getVerificationDate())){
            byeDO.setVerificationDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getVerificationDate()));
        }
        if(bye.getVerifiedBy() != null && !"".equals(bye.getVerifiedBy())){
            byeDO.setVerifiedBy(bye.getVerifiedBy());
        }
        if(bye.getInactiveDate() != null  && !"".equals(bye.getInactiveDate())){
            byeDO.setInactiveDate(BaseYearEventUtils.covertXMLGregorianCalendarToDate(bye.getInactiveDate()));
        }
        
        //New Attributes
        if(bye.getAoid() != null && !"".equals(bye.getAoid())){
            byeDO.setAoid(bye.getAoid());
        }
        if(bye.getReviewRequired() != null && !"".equals(bye.getReviewRequired())){
            byeDO.setReviewRequired(bye.getReviewRequired());
        }
        if(bye.getEventNameId() != null && !"".equals(bye.getEventNameId())){
            byeDO.setEventNameId(bye.getEventNameId());
        }
        if(bye.getEventNameKey() != null && !"".equals(bye.getEventNameKey())){
            byeDO.setEventNameKey(bye.getEventNameKey());
        }
        if(bye.getEventNameLegend() != null && !"".equals(bye.getEventNameLegend())){
            byeDO.setEventNameLegend(bye.getEventNameLegend());
        }
        if(bye.getEventDescriptionId() != null && !"".equals(bye.getEventDescriptionId())){
            byeDO.setEventDescriptionId(bye.getEventDescriptionId());
        }
        if(bye.getEventDescriptionKey() != null && !"".equals(bye.getEventDescriptionKey())){
            byeDO.setEventDescriptionKey(bye.getEventDescriptionKey());
        }
        if(bye.getEventDescriptionLegend() != null && !"".equals(bye.getEventDescriptionLegend())){
            byeDO.setEventDescriptionLegend(bye.getEventDescriptionLegend());
        }
        if(bye.getLandReasonCodeId() != null  && !"".equals(bye.getLandReasonCodeId())){
            byeDO.setLandReasonCodeId(bye.getLandReasonCodeId());
        }
        if(bye.getLandReasonCodeKey() != null && !"".equals(bye.getLandReasonCodeKey())){
            byeDO.setLandReasonCodeKey(bye.getLandReasonCodeKey());
        }
        if(bye.getLandReasonCodeLegend() != null && !"".equals(bye.getLandReasonCodeLegend())){
            byeDO.setLandReasonCodeLegend(bye.getLandReasonCodeLegend());
        }
        if(bye.getImprovementReasonCodeId() != null && !"".equals(bye.getImprovementReasonCodeId())){
            byeDO.setImprovementReasonCodeId(bye.getImprovementReasonCodeId());
        }
        if(bye.getImprovementReasonCodeKey() != null && !"".equals(bye.getImprovementReasonCodeKey())){
            byeDO.setImprovementReasonCodeKey(bye.getImprovementReasonCodeKey());
        }
        if(bye.getImprovementReasonCodeLegend() != null && !"".equals(bye.getImprovementReasonCodeLegend())){
            byeDO.setImprovementReasonCodeLegend(bye.getImprovementReasonCodeLegend());
        }
        if(bye.getCurrentOnClose() != null && !"".equals(bye.getCurrentOnClose())){
            byeDO.setCurrentOnClose(bye.getCurrentOnClose());
        }
        if(bye.getNotes() != null && !"".equals(bye.getNotes())){
            byeDO.setNotes(bye.getNotes());
        }
    }

    public List<BaseYearEventErr> getErrorEvents() {
        return errorEvents;
    }
}
