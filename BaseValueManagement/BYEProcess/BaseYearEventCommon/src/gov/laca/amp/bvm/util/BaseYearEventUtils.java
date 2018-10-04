package gov.laca.amp.bvm.util;


import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.AINLockMgmtServiceWrapper;
import gov.laca.amp.bvm.model.BaseYearEventsMgmtWrapper;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.view.bean.CurrentPopupTaskflowHandler;
import gov.laca.amp.bvm.view.bean.HistoricalPopupTaskflowHandler;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.BaseYearEvent;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.adf.model.bean.DCDataRow;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.event.SelectionEvent;

/**
 * @author Vijay Redla
 * @version 2.0
 */
public class BaseYearEventUtils {
    public static final AmpLogger LOGGER =
        new AmpLogger(BaseYearEventUtils.class);
    public static final String LOGGER_PREFIX = "\tDEBUG >> BVM Log: ";

    public BaseYearEventUtils() {
        super();
    }


    /**
     * @param bye
     * @return
     */
    public static BaseYearEventDO createBaseYearEventDO(BaseYearEvent bye) {
        BaseYearEventDO byeDO = new BaseYearEventDO();
        copyFromSOAtoDO(bye, byeDO);
        return byeDO;
    }
    
    public static BaseYearEvent createBaseYearEvent(BaseYearEventDO byeDO) {
        BaseYearEvent bye = new BaseYearEvent();
        copyFromDOToSOA(bye, byeDO);
        return bye;
    }

    private static void copyFromSOAtoDO(BaseYearEvent bye,
                                        BaseYearEventDO byeDO) {
        Double zeroVal = new Double(0.0);
        
        byeDO.setAin(bye.getAin());
        byeDO.setBaseYear(bye.getBaseYear());
        byeDO.setBlendedValueFlag(bye.getBlendedValueFlag());
        byeDO.setByeId(bye.getByeId());
        byeDO.setByeLegacySourceSystem(bye.getByeLegacySourceSystem());
        byeDO.setCreateBy(bye.getCreateBy());
        byeDO.setCreatedData(covertXMLGregorianCalendarToDate(bye.getCreatedDate()));
        byeDO.setDocumentNumber(bye.getDocumentNumber());
        byeDO.setEffectiveBeginDate(covertXMLGregorianCalendarToDate(bye.getEffectiveBeginDate()));
        byeDO.setEffectiveEndDate(covertXMLGregorianCalendarToDate(bye.getEffectiveEndDate()));
        byeDO.setEventDate(covertXMLGregorianCalendarToDate(bye.getEventDate()));
        //        byeDO.setEventDescription(bye.getEventDescription());
        //        byeDO.setEventName(bye.getEventName());
        byeDO.setEventSource(bye.getEventSource());
        
        //12c change
        if(!zeroVal.equals(bye.getFixtureValue()))
            byeDO.setFixtureValue(bye.getFixtureValue());
        //        byeDO.setImprovementReasonCode(bye.getImprovementReasonCode());
        byeDO.setImprovementValue(bye.getImprovementValue());
        //        byeDO.setLandReasonCode(bye.getLandReasonCode());
        byeDO.setLandValue(bye.getLandValue());
        byeDO.setModifiedBy(bye.getModifiedBy());
        byeDO.setModifiedDate(covertXMLGregorianCalendarToDate(bye.getModifiedDate()));
        byeDO.setNewConstructionDate(covertXMLGregorianCalendarToDate(bye.getNewConstructionDate()));
        byeDO.setOriginalAIN(bye.getOriginalAIN());
        byeDO.setOriginalBYEId(bye.getOriginalBYEId());
        byeDO.setOwnershipCode(bye.getOwnershipCode());
        byeDO.setPartiallyCompleteNumber(bye.getPartiallyCompleteNumber());
        byeDO.setPercentOwnership(bye.getPercentOwnership());
        byeDO.setPercentageAppliedToValue(bye.getPercentageAppliedToValue());
        byeDO.setPercentageFixture(bye.getPercentageFixture());
        byeDO.setPersonalPropertyType(bye.getPersonalPropertyType());
        byeDO.setPpAssessmentNumber(bye.getPpAssessmentNumber());
        byeDO.setPpDescription(bye.getPpDescription());
        byeDO.setRecordingDate(covertXMLGregorianCalendarToDate(bye.getRecordingDate()));
        //12c change
        if(!zeroVal.equals(bye.getRetainedFixtureValue()))
           byeDO.setRetainedFixtureValue(bye.getRetainedFixtureValue());
        byeDO.setRetainedImprovementValue(bye.getRetainedImprovementValue());
        byeDO.setRetainedLandValue(bye.getRetainedLandValue());
        byeDO.setRollType(bye.getRollType());
        byeDO.setSequenceNumber(bye.getSequenceNumber());
        byeDO.setSubUnitDescription(bye.getSubUnitDescription());
        byeDO.setSubUnitNumber(bye.getSubUnitNumber());
        //12c change
        if(!zeroVal.equals(bye.getTotalAdjAcquisitionCost()))
           byeDO.setTotalAdjAcquisitionCost(bye.getTotalAdjAcquisitionCost());
        byeDO.setTransactionType(bye.getTransactionType());
        byeDO.setUserId(bye.getUserId());
        byeDO.setVerificationDate(covertXMLGregorianCalendarToDate(bye.getVerificationDate()));
        byeDO.setVerifiedBy(bye.getVerifiedBy());
        byeDO.setInactiveDate(covertXMLGregorianCalendarToDate(bye.getInactiveDate()));

        //New Attributes
        byeDO.setAoid(bye.getAoid());
        byeDO.setReviewRequired(bye.getReviewRequired());
        byeDO.setEventNameId(bye.getEventNameId());
        byeDO.setEventNameKey(bye.getEventNameKey());
        byeDO.setEventNameLegend(bye.getEventNameLegend());
        byeDO.setEventDescriptionId(bye.getEventDescriptionId());
        byeDO.setEventDescriptionKey(bye.getEventDescriptionKey());
        byeDO.setEventDescriptionLegend(bye.getEventDescriptionLegend());
        byeDO.setLandReasonCodeId(bye.getLandReasonCodeId());
        byeDO.setLandReasonCodeKey(bye.getLandReasonCodeKey());
        byeDO.setLandReasonCodeLegend(bye.getLandReasonCodeLegend());
        byeDO.setImprovementReasonCodeId(bye.getImprovementReasonCodeId());
        byeDO.setImprovementReasonCodeKey(bye.getImprovementReasonCodeKey());
        byeDO.setImprovementReasonCodeLegend(bye.getImprovementReasonCodeLegend());
        byeDO.setCurrentOnClose(bye.getCurrentOnClose());
        byeDO.setNotes(bye.getNotes());
    }

    private static void copyFromDOToSOA(BaseYearEvent bye,
                                        BaseYearEventDO byeDO) {
        bye.setAin(byeDO.getAin());
        bye.setBaseYear(byeDO.getBaseYear());
        bye.setBlendedValueFlag(byeDO.getBlendedValueFlag());
        bye.setByeId(byeDO.getByeId());
        bye.setByeLegacySourceSystem(byeDO.getByeLegacySourceSystem());
        bye.setCreateBy(byeDO.getCreateBy());
        bye.setDocumentNumber(byeDO.getDocumentNumber());
        //        bye.setEventDescription(byeDO.getEventDescription());
        //        bye.setEventName(byeDO.getEventName());
        bye.setEventSource(byeDO.getEventSource());
        bye.setFixtureValue(byeDO.getFixtureValue());
        //        bye.setImprovementReasonCode(byeDO.getImprovementReasonCode());
        bye.setImprovementValue(byeDO.getImprovementValue());
        //        bye.setLandReasonCode(byeDO.getLandReasonCode());
        bye.setLandValue(byeDO.getLandValue());
        bye.setModifiedBy(byeDO.getModifiedBy());
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
        bye.setVerifiedBy(byeDO.getVerifiedBy());

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

        try {
            bye.setCreatedDate(convertDateToXMLGregorianCalendar(byeDO.getCreatedData()));
            bye.setEffectiveBeginDate(convertDateToXMLGregorianCalendar(byeDO.getEffectiveBeginDate()));
            bye.setEffectiveEndDate(convertDateToXMLGregorianCalendar(byeDO.getEffectiveEndDate()));
            bye.setEventDate(convertDateToXMLGregorianCalendar(byeDO.getEventDate()));
            bye.setModifiedDate(convertDateToXMLGregorianCalendar(byeDO.getModifiedDate()));
            bye.setNewConstructionDate(convertDateToXMLGregorianCalendar(byeDO.getNewConstructionDate()));
            bye.setRecordingDate(convertDateToXMLGregorianCalendar(byeDO.getRecordingDate()));
            bye.setInactiveDate(convertDateToXMLGregorianCalendar(byeDO.getInactiveDate()));
        } catch (AmpBaseYearEventException e) {
            LOGGER.error(BaseYearEventUtils.class, "copyFromDOToSOA",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         " Date Conversion to XMLGregorianCalendar Failed",
                         null);
            e.printStackTrace();
        }
    }

    public static Date covertXMLGregorianCalendarToDate(XMLGregorianCalendar date) {
        if (date != null) {
            return (date.toGregorianCalendar().getTime());
        }
        return null;
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) throws AmpBaseYearEventException {
        if (date != null) {
            try {
                final GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                final DatatypeFactory datatypeFactory =
                    DatatypeFactory.newInstance();
                return datatypeFactory.newXMLGregorianCalendar(calendar);
            } catch (DatatypeConfigurationException e) {
                throw new AmpBaseYearEventException(e.getMessage(),
                                                    "Error Converting Date to XML Calendar");
            }
        }
        return null;
    }

    public static String getRequestID() {
        return UUID.randomUUID().toString();
    }

    public static BaseYearEventDO getDiffReviewChangeEvent(BaseYearEventDO originalEvent,
                                                           BaseYearEventDO currentEvent) {
        BaseYearEventDO diffEvent = new BaseYearEventDO();
        if (originalEvent != null && currentEvent != null) {

            if (originalEvent.getEventDescriptionKey() != null &&
                originalEvent.getEventDescriptionKey().equals(currentEvent.getEventDescriptionKey())) {
                diffEvent.setEventDescriptionKey(null);
            } else {
                diffEvent.setEventDescriptionKey(currentEvent.getEventDescriptionKey());
            }

            if (originalEvent.getEventDate() != null &&
                originalEvent.getEventDate().equals(currentEvent.getEventDate())) {
                diffEvent.setEventDate(null);
            } else {
                diffEvent.setEventDate(currentEvent.getEventDate());
            }

            if (originalEvent.getBaseYear() != null &&
                originalEvent.getBaseYear().equals(currentEvent.getBaseYear())) {
                diffEvent.setBaseYear(null);
            } else {
                diffEvent.setBaseYear(currentEvent.getBaseYear());
            }

            if (originalEvent.getSubUnitNumber() != null &&
                originalEvent.getSubUnitNumber().equals(currentEvent.getSubUnitNumber())) {
                diffEvent.setSubUnitNumber(null);
            } else {
                diffEvent.setSubUnitNumber(currentEvent.getSubUnitNumber());
            }

            if (originalEvent.getPercentOwnership() != null &&
                originalEvent.getPercentOwnership().equals(currentEvent.getPercentOwnership())) {
                diffEvent.setPercentOwnership(null);
            } else {
                diffEvent.setPercentOwnership(currentEvent.getPercentOwnership());
            }

            if (originalEvent.getEventNameKey() != null &&
                originalEvent.getEventNameKey().equals(currentEvent.getEventNameKey())) {
                diffEvent.setEventNameKey(null);
            } else {
                diffEvent.setEventNameKey(currentEvent.getEventNameKey());
            }

            if (originalEvent.getPercentageAppliedToValue() != null &&
                originalEvent.getPercentageAppliedToValue().equals(currentEvent.getPercentageAppliedToValue())) {
                diffEvent.setPercentageAppliedToValue(null);
            } else {
                diffEvent.setPercentageAppliedToValue(currentEvent.getPercentageAppliedToValue());
            }

            if (originalEvent.getLandValue() != null &&
                originalEvent.getLandValue().equals(currentEvent.getLandValue())) {
                diffEvent.setLandValue(null);
            } else {
                diffEvent.setLandValue(currentEvent.getLandValue());
            }

            if (originalEvent.getLandReasonCodeKey() != null &&
                originalEvent.getLandReasonCodeKey().equals(currentEvent.getLandReasonCodeKey())) {
                diffEvent.setLandReasonCodeKey(null);
            } else {
                diffEvent.setLandReasonCodeKey(currentEvent.getLandReasonCodeKey());
            }

            if (originalEvent.getImprovementValue() != null &&
                originalEvent.getImprovementValue().equals(currentEvent.getImprovementValue())) {
                diffEvent.setImprovementValue(null);
            } else {
                diffEvent.setImprovementValue(currentEvent.getImprovementValue());
            }

            if (originalEvent.getImprovementReasonCodeKey() != null &&
                originalEvent.getImprovementReasonCodeKey().equals(currentEvent.getImprovementReasonCodeKey())) {
                diffEvent.setImprovementReasonCodeKey(null);
            } else {
                diffEvent.setImprovementReasonCodeKey(currentEvent.getImprovementReasonCodeKey());
            }

            if (originalEvent.getTotalValue() != null &&
                originalEvent.getTotalValue().equals(currentEvent.getTotalValue())) {
                diffEvent.setTotalValue(null);
            } else {
                diffEvent.setTotalValue(currentEvent.getTotalValue());
            }

            if (originalEvent.getFixtureValue() != null &&
                originalEvent.getFixtureValue().equals(currentEvent.getFixtureValue())) {
                diffEvent.setFixtureValue(null);
            } else {
                diffEvent.setFixtureValue(currentEvent.getFixtureValue());
            }

            if (originalEvent.getEffectiveBeginDate() != null &&
                originalEvent.getEffectiveBeginDate().equals(currentEvent.getEffectiveBeginDate())) {
                diffEvent.setEffectiveBeginDate(null);
            } else {
                diffEvent.setEffectiveBeginDate(currentEvent.getEffectiveBeginDate());
            }

            if (originalEvent.getEffectiveEndDate() != null &&
                originalEvent.getEffectiveEndDate().equals(currentEvent.getEffectiveEndDate())) {
                diffEvent.setEffectiveEndDate(null);
            } else {
                diffEvent.setEffectiveEndDate(currentEvent.getEffectiveEndDate());
            }

            if (originalEvent.getVerificationDate() != null &&
                originalEvent.getVerificationDate().equals(currentEvent.getVerificationDate())) {
                diffEvent.setVerificationDate(null);
            } else {
                diffEvent.setVerificationDate(currentEvent.getVerificationDate());
            }

            if (originalEvent.getInactiveDate() != null &&
                originalEvent.getInactiveDate().equals(currentEvent.getInactiveDate())) {
                diffEvent.setInactiveDate(null);
            } else {
                diffEvent.setInactiveDate(currentEvent.getInactiveDate());
            }

            if (originalEvent.getBlendedValueFlag() != null &&
                originalEvent.getBlendedValueFlag().equals(currentEvent.getBlendedValueFlag())) {
                diffEvent.setBlendedValueFlag(null);
            } else {
                diffEvent.setBlendedValueFlag(currentEvent.getBlendedValueFlag());
            }

            if (originalEvent.getRecordingDate() != null &&
                originalEvent.getRecordingDate().equals(currentEvent.getRecordingDate())) {
                diffEvent.setRecordingDate(null);
            } else {
                diffEvent.setRecordingDate(currentEvent.getRecordingDate());
            }

            if (originalEvent.getSequenceNumber() != null &&
                originalEvent.getSequenceNumber().equals(currentEvent.getSequenceNumber())) {
                diffEvent.setSequenceNumber(null);
            } else {
                diffEvent.setSequenceNumber(currentEvent.getSequenceNumber());
            }

            if (originalEvent.getDocumentNumber() != null &&
                originalEvent.getDocumentNumber().equals(currentEvent.getDocumentNumber())) {
                diffEvent.setDocumentNumber(null);
            } else {
                diffEvent.setDocumentNumber(currentEvent.getDocumentNumber());
            }

            if (originalEvent.getOwnershipCode() != null &&
                originalEvent.getOwnershipCode().equals(currentEvent.getOwnershipCode())) {
                diffEvent.setOwnershipCode(null);
            } else {
                diffEvent.setOwnershipCode(currentEvent.getOwnershipCode());
            }

            if (originalEvent.getNewConstructionDate() != null &&
                originalEvent.getNewConstructionDate().equals(currentEvent.getNewConstructionDate())) {
                diffEvent.setNewConstructionDate(null);
            } else {
                diffEvent.setNewConstructionDate(currentEvent.getNewConstructionDate());
            }

            if (originalEvent.getPartiallyCompleteNumber() != null &&
                originalEvent.getPartiallyCompleteNumber().equals(currentEvent.getPartiallyCompleteNumber())) {
                diffEvent.setPartiallyCompleteNumber(null);
            } else {
                diffEvent.setPartiallyCompleteNumber(currentEvent.getPartiallyCompleteNumber());
            }

            if (originalEvent.getRetainedLandValue() != null &&
                originalEvent.getRetainedLandValue().equals(currentEvent.getRetainedLandValue())) {
                diffEvent.setRetainedLandValue(null);
            } else {
                diffEvent.setRetainedLandValue(currentEvent.getRetainedLandValue());
            }

            if (originalEvent.getRetainedImprovementValue() != null &&
                originalEvent.getRetainedImprovementValue().equals(currentEvent.getRetainedImprovementValue())) {
                diffEvent.setRetainedImprovementValue(null);
            } else {
                diffEvent.setRetainedImprovementValue(currentEvent.getRetainedImprovementValue());
            }

            if (originalEvent.getRetainedTotalValue() != null &&
                originalEvent.getRetainedTotalValue().equals(currentEvent.getRetainedTotalValue())) {
                diffEvent.setRetainedTotalValue(null);
            } else {
                diffEvent.setRetainedTotalValue(currentEvent.getRetainedTotalValue());
            }

            if (originalEvent.getRetainedFixtureValue() != null &&
                originalEvent.getRetainedFixtureValue().equals(currentEvent.getRetainedFixtureValue())) {
                diffEvent.setRetainedFixtureValue(null);
            } else {
                diffEvent.setRetainedFixtureValue(currentEvent.getRetainedFixtureValue());
            }

            if (originalEvent.getSubUnitDescription() != null &&
                originalEvent.getSubUnitDescription().equals(currentEvent.getSubUnitDescription())) {
                diffEvent.setSubUnitDescription(null);
            } else {
                diffEvent.setSubUnitDescription(currentEvent.getSubUnitDescription());
            }

            if (originalEvent.getPersonalPropertyType() != null &&
                originalEvent.getPersonalPropertyType().equals(currentEvent.getPersonalPropertyType())) {
                diffEvent.setPersonalPropertyType(null);
            } else {
                diffEvent.setPersonalPropertyType(currentEvent.getPersonalPropertyType());
            }

            if (originalEvent.getPpDescription() != null &&
                originalEvent.getPpDescription().equals(currentEvent.getPpDescription())) {
                diffEvent.setPpDescription(null);
            } else {
                diffEvent.setPpDescription(currentEvent.getPpDescription());
            }

            if (originalEvent.getTotalAdjAcquisitionCost() != null &&
                originalEvent.getTotalAdjAcquisitionCost().equals(currentEvent.getTotalAdjAcquisitionCost())) {
                diffEvent.setTotalAdjAcquisitionCost(null);
            } else {
                diffEvent.setTotalAdjAcquisitionCost(currentEvent.getTotalAdjAcquisitionCost());
            }

            if (originalEvent.getPercentageFixture() != null &&
                originalEvent.getPercentageFixture().equals(currentEvent.getPercentageFixture())) {
                diffEvent.setPercentageFixture(null);
            } else {
                diffEvent.setPercentageFixture(currentEvent.getPercentageFixture());
            }

            if (originalEvent.getOriginalAIN() != null &&
                originalEvent.getOriginalAIN().equals(currentEvent.getOriginalAIN())) {
                diffEvent.setOriginalAIN(null);
            } else {
                diffEvent.setOriginalAIN(currentEvent.getOriginalAIN());
            }

            if (originalEvent.getNotes() != null &&
                originalEvent.getNotes().equals(currentEvent.getNotes())) {
                diffEvent.setNotes(null);
            } else {
                diffEvent.setNotes(currentEvent.getNotes());
            }

            if (originalEvent.getPpAssessmentNumber() != null &&
                originalEvent.getPpAssessmentNumber().equals(currentEvent.getPpAssessmentNumber())) {
                diffEvent.setPpAssessmentNumber(null);
            } else {
                diffEvent.setPpAssessmentNumber(currentEvent.getPpAssessmentNumber());
            }

            if (originalEvent.getByeId() != null &&
                originalEvent.getByeId().equals(currentEvent.getByeId())) {
                diffEvent.setByeId(null);
            } else {
                diffEvent.setByeId(currentEvent.getByeId());
            }

            if (originalEvent.getVerifiedBy() != null &&
                originalEvent.getVerifiedBy().equals(currentEvent.getVerifiedBy())) {
                diffEvent.setVerifiedBy(null);
            } else {
                diffEvent.setVerifiedBy(currentEvent.getVerifiedBy());
            }

            if (originalEvent.getModifiedBy() != null &&
                originalEvent.getModifiedBy().equals(currentEvent.getModifiedBy())) {
                diffEvent.setModifiedBy(null);
            } else {
                diffEvent.setModifiedBy(currentEvent.getModifiedBy());
            }

            if (originalEvent.getModifiedDate() != null &&
                originalEvent.getModifiedDate().equals(currentEvent.getModifiedDate())) {
                diffEvent.setModifiedDate(null);
            } else {
                diffEvent.setModifiedDate(currentEvent.getModifiedDate());
            }

            if (originalEvent.getByeLegacySourceSystem() != null &&
                originalEvent.getByeLegacySourceSystem().equals(currentEvent.getByeLegacySourceSystem())) {
                diffEvent.setByeLegacySourceSystem(null);
            } else {
                diffEvent.setByeLegacySourceSystem(currentEvent.getByeLegacySourceSystem());
            }

            if (originalEvent.getEventSource() != null &&
                originalEvent.getEventSource().equals(currentEvent.getEventSource())) {
                diffEvent.setEventSource(null);
            } else {
                diffEvent.setEventSource(currentEvent.getEventSource());
            }

            if (originalEvent.getCreatedData() != null &&
                originalEvent.getCreatedData().equals(currentEvent.getCreatedData())) {
                diffEvent.setCreatedData(null);
            } else {
                diffEvent.setCreatedData(currentEvent.getCreatedData());
            }

            diffEvent.setTransactionType("U");
        } else {
            return null;
        }
        return diffEvent;
    }

    public static void setSelectedRow(RichTable eventsTbl,
                                      String selectionActionBindingName) {
        RowKeySet rks = eventsTbl.getSelectedRowKeys();
        if (!rks.isEmpty()) {
            for (Object facesRowKey : eventsTbl.getSelectedRowKeys()) {
                eventsTbl.setRowKey(facesRowKey);
                JUCtrlHierNodeBinding node =
                    (JUCtrlHierNodeBinding)eventsTbl.getRowData();
                if (node != null) {
                    Row row = node.getRow();
                    if (row != null) {
                        BaseYearEventDO event =
                            (BaseYearEventDO)((DCDataRow)row).getDataProvider();
                        OperationBinding setSelctedEvent =
                            ADFUtils.findOperation(selectionActionBindingName);
                        BigInteger id = event.getByeId();
                        setSelctedEvent.getParamsMap().put("byeId", id);
                        setSelctedEvent.execute();
                    }
                    // Added the byeId to session scope : 31-jan-2017
                    //JSFUtils.storeOnSession("byeId", id);
                    //JSFUtils.storeOnPageFlow("pfbyeId", id);
                    //LOGGER.fine(LOGGER_PREFIX + "Event Selection is set");
                }
            }
        }
    }
    
    public static void refreshCurrentEventsUI(RichTable eventSourceTable) {
        CurrentPopupTaskflowHandler handle =
            (CurrentPopupTaskflowHandler)JSFUtils.getManagedBeanValue("viewScope.currentPopupTaskflowHandler");
        if (handle != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(eventSourceTable);
            RichShowDetailHeader currentDetailHeader = handle.getCurrentDetailHeader();
            if(currentDetailHeader != null){
                AdfFacesContext.getCurrentInstance().addPartialTarget(currentDetailHeader);
            }
            RichToolbar currentToolbar = handle.getCurrentToolbar();
            if(currentToolbar != null){
                AdfFacesContext.getCurrentInstance().addPartialTarget(currentToolbar);
            }
        }
    }
    
    public static void refreshHistoricalEventsUI(RichTable eventSourceTable) {
        HistoricalPopupTaskflowHandler handle =
            (HistoricalPopupTaskflowHandler)JSFUtils.getManagedBeanValue("viewScope.historicalPopupTaskflowHandler");
        if (handle != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(eventSourceTable);
            RichShowDetailHeader detailHeader = handle.getHistoricalDetailHeader();
            if(detailHeader != null){
                AdfFacesContext.getCurrentInstance().addPartialTarget(detailHeader);
            }
            RichToolbar historicalToolbar = handle.getHistoricalToolbar();
            if(historicalToolbar != null){
                AdfFacesContext.getCurrentInstance().addPartialTarget(historicalToolbar);
            }
        }
    }
    
    public static void selectEventInTable(String eventSourceType, RichTable historicalEventsTable, RichTable currentEventsTable) {
        BigInteger byeId = (BigInteger)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.SELECTED_HISTORICAL_BYEID_BINDING);
        DCIteratorBinding historicalEventsIterator = ADFUtils.findIterator(BaseYearEventConstants.HISTORICAL_EVENTS_ITERATOR);
        DCIteratorBinding currentEventsIterator = ADFUtils.findIterator(BaseYearEventConstants.CURRENT_EVENTS_ITERATOR);
        //Event is in Historical Table
        if(byeId != null){
            LOGGER.fine(LOGGER_PREFIX + "Event found in Historical Table");
            historicalEventsIterator.executeQuery();
            refreshHistoricalEventsUI(historicalEventsTable);
            fireSelectEventInTable(historicalEventsIterator, byeId, historicalEventsTable);
            if("CURRENT".equals(eventSourceType)){
                LOGGER.fine(LOGGER_PREFIX + "Moved from Current to Historical Table");
                currentEventsIterator.executeQuery();
            }
            refreshCurrentEventsUI(currentEventsTable);
            fireSelectEventInTable(currentEventsIterator, null, currentEventsTable);
        } else {
            byeId =
                    (BigInteger)ADFUtils.getBoundAttributeValue(BaseYearEventConstants.SELECTED_CURRENT_BYEID_BINDING);
            //Event is in Current Table
            if (byeId != null) {
                LOGGER.fine(LOGGER_PREFIX + "Event found in Current Table");
                currentEventsIterator.executeQuery();
                refreshCurrentEventsUI(currentEventsTable);
                fireSelectEventInTable(currentEventsIterator, byeId, currentEventsTable);
                if ("HISTORICAL".equals(eventSourceType)) {
                    LOGGER.fine(LOGGER_PREFIX +
                                "Moved from Historical to Current Table");
                    historicalEventsIterator.executeQuery();
                }
                refreshHistoricalEventsUI(historicalEventsTable);
                fireSelectEventInTable(historicalEventsIterator, null, historicalEventsTable);
            }
        }
    }
    
    private static boolean selectEventInTable(DCIteratorBinding iter, BigInteger byeId){
        Key key = new Key(new Object[] { byeId });
        RowSetIterator rsi = iter.getRowSetIterator();
        Row[] rows = rsi.findByKey(key, 1);
        if (rows != null && rows.length > 0) {
            rsi.setCurrentRow(rows[0]);
            return true;
        }
        return false;
    }

    public static void fireSelectEventInTable(DCIteratorBinding iter, BigInteger byeId,
                                               RichTable eventSourceTable) {
        Row currentRow = iter.getCurrentRow();
        ArrayList rowKeyList = new ArrayList();
        Key jboKey = null;
        if(byeId != null){
            jboKey = new Key(new Object[] { byeId });
        }else if (currentRow != null) {
            //add primary key as jbo key
            jboKey =
                new Key(new Object[] { currentRow.getAttribute("byeId") });
        }else{
            if(iter.getEstimatedRowCount() > 0){
                Row firstRow = iter.getRowAtRangeIndex(0);
                jboKey =
                    new Key(new Object[] { firstRow.getAttribute("byeId") });
            }
        }
        if (jboKey != null) {
            rowKeyList.add(jboKey);
            RowKeySet newRowKeySet = new RowKeySetImpl();
            newRowKeySet.add(rowKeyList);
            SelectionEvent selectEvent =
                new SelectionEvent(eventSourceTable.getSelectedRowKeys(),
                                   newRowKeySet, eventSourceTable);
            selectEvent.queue();
        }else{
            LOGGER.warn(LOGGER_PREFIX + "No Event Key found in the table to select");
        }
    }
}
