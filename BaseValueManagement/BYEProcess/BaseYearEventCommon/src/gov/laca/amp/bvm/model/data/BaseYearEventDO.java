
package gov.laca.amp.bvm.model.data;

import gov.laca.amp.bvm.util.BaseYearEventUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * Java class for baseYearEvent complex type.
 *
 */

public class BaseYearEventDO implements Comparable<BaseYearEventDO>{
    String[] attributes =
    { "ain", "byeId", "ownershipCode", "percentOwnership", "eventName",
      "eventDescription", "eventDate", "recordingDate", "documentNumber",
      "sequenceNumber", "baseYear", "subUnitNumber", "subUnitDescription",
      "partiallyCompleteNumber", "blendedValueFlag",
      "percentageAppliedToValue", "landValue", "improvementValue",
      "landReasonCode", "improvementReasonCode", "fixtureValue",
      "retainedLandValue", "retainedImprovementValue", "retainedFixtureValue",
      "ppAssessmentNumber", "personalPropertyType", "ppDescription",
      "totalAdjAcquisitionCost", "percentageFixture", "effectiveBeginDate",
      "effectiveEndDate", "verificationDate", "eventSource", "verifiedBy",
      "byeLegacySourceSystem", "originalAIN", "originalBYEId", "rollType",
      "transactionType", "userId", "newConstructionDate", "createBy",
      "createdData", "modifiedBy", "inactiveDate", "modifiedDate", "aoid",
      "eventNameLegend", "eventDescriptionId", "eventDescriptionKey",
      "eventDescriptionLegend", "landReasonCodeId", "landReasonCodeKey",
      "landReasonCodeLegend", "improvementReasonCodeId",
      "improvementReasonCodeKey", "improvementReasonCodeLegend",
      "reviewRequired", "currentOnClose", "notes", "eventNameId",
      "eventNameKey" };

    protected Boolean selected = false;
    //    protected String eventName;
    //    protected String eventDescription;
    protected Date eventDate;
    protected BigInteger baseYear;
    protected BigInteger subUnitNumber;
    protected BigDecimal percentOwnership;
    protected BigDecimal percentageAppliedToValue;
    protected Double landValue;
    //    protected BigInteger landReasonCode;
    protected Double improvementValue;
    //    protected BigInteger improvementReasonCode;
    protected Double totalValue;
    protected Double fixtureValue;
    protected Date effectiveBeginDate;
    protected Date effectiveEndDate;
    protected Date verificationDate;
    protected Date inactiveDate;
    protected String blendedValueFlag;
    protected Date recordingDate;
    protected BigInteger sequenceNumber;
    protected BigInteger documentNumber;
    protected String ownershipCode;
    protected Date newConstructionDate;
    protected String partiallyCompleteNumber;
    protected Double retainedLandValue;
    protected Double retainedImprovementValue;
    protected Double retainedTotalValue;
    protected Double retainedFixtureValue;
    protected String subUnitDescription;
    protected String personalPropertyType;
    protected String ppDescription;
    protected Double totalAdjAcquisitionCost;
    protected BigDecimal percentageFixture;
    protected String originalAIN;
    protected String ppAssessmentNumber;
    protected BigInteger byeId;
    protected String verifiedBy;
    protected String modifiedBy;
    protected Date modifiedDate;

    protected String byeLegacySourceSystem;
    protected String eventSource;
    protected Date createdData;

    protected String ain;
    protected BigInteger originalBYEId;
    protected String rollType;
    protected String transactionType;
    protected String userId;
    protected String createBy;

    //New Attributes
    protected BigInteger aoid;
    protected String reviewRequired;
    protected String eventNameLegend;
    protected BigInteger eventDescriptionId;
    protected String eventDescriptionKey;
    protected String eventDescriptionLegend;
    protected BigInteger landReasonCodeId;
    protected String landReasonCodeKey;
    protected String landReasonCodeLegend;
    protected BigInteger improvementReasonCodeId;
    protected String improvementReasonCodeKey;
    protected String improvementReasonCodeLegend;
    protected String currentOnClose;
    protected String notes;
    protected java.math.BigInteger eventNameId;
    protected java.lang.String eventNameKey;

    //Custom Attribute
    protected Boolean verified;

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    //    public void setEventName(String eventName) {
    //        this.eventName = eventName;
    //    }
    //
    //    public String getEventName() {
    //        return eventName;
    //    }

    //    public void setEventDescription(String eventDescription) {
    //        this.eventDescription = eventDescription;
    //    }
    //
    //    public String getEventDescription() {
    //        return eventDescription;
    //    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setBaseYear(BigInteger baseYear) {
        this.baseYear = baseYear;
    }

    public BigInteger getBaseYear() {
        return baseYear;
    }

    public void setSubUnitNumber(BigInteger subUnitNumber) {
        this.subUnitNumber = subUnitNumber;
    }

    public BigInteger getSubUnitNumber() {
        return subUnitNumber;
    }

    public void setPercentOwnership(BigDecimal percentOwnership) {
        this.percentOwnership = percentOwnership;
    }

    public BigDecimal getPercentOwnership() {
        return percentOwnership;
    }

    public void setPercentageAppliedToValue(BigDecimal percentageAppliedToValue) {
        this.percentageAppliedToValue = percentageAppliedToValue;
    }

    public BigDecimal getPercentageAppliedToValue() {
        return percentageAppliedToValue;
    }

    public void setLandValue(Double landValue) {
        this.landValue = landValue;
    }

    public Double getLandValue() {
        return landValue;
    }

    //    public void setLandReasonCode(BigInteger landReasonCode) {
    //        this.landReasonCode = landReasonCode;
    //    }
    //
    //    public BigInteger getLandReasonCode() {
    //        return landReasonCode;
    //    }

    public void setImprovementValue(Double improvementValue) {
        this.improvementValue = improvementValue;
    }

    public Double getImprovementValue() {
        return improvementValue;
    }

    //    public void setImprovementReasonCode(BigInteger improvementReasonCode) {
    //        this.improvementReasonCode = improvementReasonCode;
    //    }
    //
    //    public BigInteger getImprovementReasonCode() {
    //        return improvementReasonCode;
    //    }

    public void setTotalAdjAcquisitionCost(Double totalAdjAcquisitionCost) {
        this.totalAdjAcquisitionCost = totalAdjAcquisitionCost;
    }

    public Double getTotalAdjAcquisitionCost() {
        return totalAdjAcquisitionCost;
    }

    public void setFixtureValue(Double fixtureValue) {
        
        this.fixtureValue = fixtureValue;
    }

    public Double getFixtureValue() {
        return fixtureValue;
    }

    public void setEffectiveBeginDate(Date effectiveBeginDate) {
        this.effectiveBeginDate = effectiveBeginDate;
    }

    public Date getEffectiveBeginDate() {
        return effectiveBeginDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setInactiveDate(Date inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setBlendedValueFlag(String blendedValueFlag) {
        this.blendedValueFlag = blendedValueFlag;
    }

    public String getBlendedValueFlag() {
        return blendedValueFlag;
    }

    public void setRecordingDate(Date recordingDate) {
        this.recordingDate = recordingDate;
    }

    public Date getRecordingDate() {
        return recordingDate;
    }

    public void setSequenceNumber(BigInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    public void setDocumentNumber(BigInteger documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigInteger getDocumentNumber() {
        return documentNumber;
    }

    public void setOwnershipCode(String ownershipCode) {
        this.ownershipCode = ownershipCode;
    }

    public String getOwnershipCode() {
        return ownershipCode;
    }

    public void setNewConstructionDate(Date newConstructionDate) {
        this.newConstructionDate = newConstructionDate;
    }

    public Date getNewConstructionDate() {
        return newConstructionDate;
    }

    public void setPartiallyCompleteNumber(String partiallyCompleteNumber) {
        this.partiallyCompleteNumber = partiallyCompleteNumber;
    }

    public String getPartiallyCompleteNumber() {
        return partiallyCompleteNumber;
    }

    public void setRetainedLandValue(Double retainedLandValue) {
        this.retainedLandValue = retainedLandValue;
    }

    public Double getRetainedLandValue() {
        return retainedLandValue;
    }

    public void setRetainedImprovementValue(Double retainedImprovementValue) {
        this.retainedImprovementValue = retainedImprovementValue;
    }

    public Double getRetainedImprovementValue() {
        return retainedImprovementValue;
    }

    public void setRetainedFixtureValue(Double retainedFixtureValue) {
        this.retainedFixtureValue = retainedFixtureValue;
    }

    public Double getRetainedFixtureValue() {
        return retainedFixtureValue;
    }

    public void setSubUnitDescription(String subUnitDescription) {
        this.subUnitDescription = subUnitDescription;
    }

    public String getSubUnitDescription() {
        return subUnitDescription;
    }

    public void setPersonalPropertyType(String personalPropertyType) {
        this.personalPropertyType = personalPropertyType;
    }

    public String getPersonalPropertyType() {
        return personalPropertyType;
    }

    public void setPpDescription(String ppDescription) {
        this.ppDescription = ppDescription;
    }

    public String getPpDescription() {
        return ppDescription;
    }

    public void setPercentageFixture(BigDecimal percentageFixture) {

        this.percentageFixture = percentageFixture;
    }

    public BigDecimal getPercentageFixture() {
        return percentageFixture;
    }


  

    

    public void setOriginalAIN(String originalAIN) {
        this.originalAIN = originalAIN;
    }

    public String getOriginalAIN() {
        return originalAIN;
    }

    public void setPpAssessmentNumber(String ppAssessmentNumber) {
        this.ppAssessmentNumber = ppAssessmentNumber;
    }

    public String getPpAssessmentNumber() {
        return ppAssessmentNumber;
    }

    public void setByeId(BigInteger byeId) {
        this.byeId = byeId;
    }

    public BigInteger getByeId() {
        return byeId;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setByeLegacySourceSystem(String byeLegacySourceSystem) {
        this.byeLegacySourceSystem = byeLegacySourceSystem;
    }

    public String getByeLegacySourceSystem() {
        return byeLegacySourceSystem;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setCreatedData(Date createdData) {
        this.createdData = createdData;
    }

    public Date getCreatedData() {
        return createdData;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setOriginalBYEId(BigInteger originalBYEId) {
        this.originalBYEId = originalBYEId;
    }

    public BigInteger getOriginalBYEId() {
        return originalBYEId;
    }

    public void setRollType(String rollType) {
        this.rollType = rollType;
    }

    public String getRollType() {
        return rollType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setRetainedTotalValue(Double retainedTotalValue) {
        this.retainedTotalValue = retainedTotalValue;
    }

    public Double getRetainedTotalValue() {
        return retainedTotalValue;
    }


    /**
     * Gets the legend of the aoid property.
     *
     * @return
     * possible object is
     * {@link BigInteger}
     *
     */
    public BigInteger getAoid() {
        return aoid;
    }

    /**
     * Sets the legend of the aoid property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger}
     *
     */
    public void setAoid(BigInteger value) {
        this.aoid = value;
    }

    /**
     * Gets the legend of the reviewRequired property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getReviewRequired() {
        return reviewRequired;
    }

    /**
     * Sets the legend of the reviewRequired property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setReviewRequired(String value) {
        this.reviewRequired = value;
    }

    public void setEventNameLegend(String eventNameLegend) {
        this.eventNameLegend = eventNameLegend;
    }

    public String getEventNameLegend() {
        return eventNameLegend;
    }

    public void setEventDescriptionId(BigInteger eventDescriptionId) {
        this.eventDescriptionId = eventDescriptionId;
    }

    public BigInteger getEventDescriptionId() {
        return eventDescriptionId;
    }

    public void setEventDescriptionKey(String eventDescriptionKey) {
        this.eventDescriptionKey = eventDescriptionKey;
    }

    public String getEventDescriptionKey() {
        return eventDescriptionKey;
    }

    public void setEventDescriptionLegend(String eventDescriptionLegend) {
        this.eventDescriptionLegend = eventDescriptionLegend;
    }

    public String getEventDescriptionLegend() {
        return eventDescriptionLegend;
    }

    public void setLandReasonCodeId(BigInteger landReasonCodeId) {
        this.landReasonCodeId = landReasonCodeId;
    }

    public BigInteger getLandReasonCodeId() {
        return landReasonCodeId;
    }

    public void setLandReasonCodeKey(String landReasonCodeKey) {
        this.landReasonCodeKey = landReasonCodeKey;
    }

    public String getLandReasonCodeKey() {
        return landReasonCodeKey;
    }

    public void setLandReasonCodeLegend(String landReasonCodeLegend) {
        this.landReasonCodeLegend = landReasonCodeLegend;
    }

    public String getLandReasonCodeLegend() {
        return landReasonCodeLegend;
    }

    public void setImprovementReasonCodeId(BigInteger improvementReasonCodeId) {
        this.improvementReasonCodeId = improvementReasonCodeId;
    }

    public BigInteger getImprovementReasonCodeId() {
        return improvementReasonCodeId;
    }

    public void setImprovementReasonCodeKey(String improvementReasonCodeKey) {
        this.improvementReasonCodeKey = improvementReasonCodeKey;
    }

    public String getImprovementReasonCodeKey() {
        return improvementReasonCodeKey;
    }

    public void setImprovementReasonCodeLegend(String improvementReasonCodeLegend) {
        this.improvementReasonCodeLegend = improvementReasonCodeLegend;
    }

    public String getImprovementReasonCodeLegend() {
        return improvementReasonCodeLegend;
    }

    public void setCurrentOnClose(String currentOnClose) {
        this.currentOnClose = currentOnClose;
    }

    public String getCurrentOnClose() {
        return currentOnClose;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setEventNameId(BigInteger eventNameId) {
        this.eventNameId = eventNameId;
    }

    public BigInteger getEventNameId() {
        return eventNameId;
    }

    public void setEventNameKey(String eventNameKey) {
        this.eventNameKey = eventNameKey;
    }

    public String getEventNameKey() {
        return eventNameKey;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void copy(BaseYearEventDO bye) {
        if (bye != null) {
            ain = bye.getAin();
            aoid = bye.getAoid();
            baseYear = bye.getBaseYear();
            blendedValueFlag = bye.getBlendedValueFlag();
            byeId = bye.getByeId();
            byeLegacySourceSystem = bye.getByeLegacySourceSystem();
            createdData = bye.getCreatedData();
            createBy = bye.getCreateBy();
            currentOnClose = bye.getCurrentOnClose();
            documentNumber = bye.getDocumentNumber();
            effectiveBeginDate = bye.getEffectiveBeginDate();
            effectiveEndDate = bye.getEffectiveEndDate();
            eventDate = bye.getEventDate();
            eventNameId = bye.getEventNameId();
            eventNameKey = bye.getEventNameKey();
            eventNameLegend = bye.getEventNameLegend();
            eventDescriptionId = bye.getEventDescriptionId();
            eventDescriptionKey = bye.getEventDescriptionKey();
            eventDescriptionLegend = bye.getEventDescriptionLegend();
            eventSource = bye.getEventSource();
            fixtureValue = bye.getFixtureValue();
            improvementValue = bye.getImprovementValue();
            improvementReasonCodeId = bye.getImprovementReasonCodeId();
            improvementReasonCodeKey = bye.getImprovementReasonCodeKey();
            improvementReasonCodeLegend = bye.getImprovementReasonCodeLegend();
            inactiveDate = bye.getInactiveDate();
            landValue = bye.getLandValue();
            landReasonCodeId = bye.getLandReasonCodeId();
            landReasonCodeKey = bye.getLandReasonCodeKey();
            landReasonCodeLegend = bye.getLandReasonCodeLegend();
            modifiedBy = bye.getModifiedBy();
            modifiedDate = bye.getModifiedDate();
            newConstructionDate = bye.getNewConstructionDate();
            notes = bye.getNotes();
            originalAIN = bye.getOriginalAIN();
            originalBYEId = bye.getOriginalBYEId();
            ownershipCode = bye.getOwnershipCode();
            partiallyCompleteNumber = bye.getPartiallyCompleteNumber();
            percentOwnership = bye.getPercentOwnership();
            percentageAppliedToValue = bye.getPercentageAppliedToValue();
            personalPropertyType = bye.getPersonalPropertyType();
            ppDescription = bye.getPpDescription();
            percentageFixture = bye.getPercentageFixture();
            ppAssessmentNumber = bye.getPpAssessmentNumber();
            recordingDate = bye.getRecordingDate();
            retainedFixtureValue = bye.getRetainedFixtureValue();
            retainedImprovementValue = bye.getRetainedImprovementValue();
            retainedLandValue = bye.getRetainedLandValue();
            retainedTotalValue = bye.getRetainedTotalValue();
            reviewRequired = bye.getReviewRequired();
            rollType = bye.getRollType();
             //selected = bye.getSelected();
            sequenceNumber = bye.getSequenceNumber();
            subUnitDescription = bye.getSubUnitDescription();
            subUnitNumber = bye.getSubUnitNumber();
            totalValue = bye.getTotalValue();
            totalAdjAcquisitionCost = bye.getTotalAdjAcquisitionCost();
            transactionType = bye.getTransactionType();
            userId = bye.getUserId();
            verificationDate = bye.getVerificationDate();
            verifiedBy = bye.getVerifiedBy();
            verified = bye.getVerified();
        }
    }

    public void clear() {
        ain = null;
        aoid = null;
        baseYear = null;
        blendedValueFlag = null;
        byeId = null;
        byeLegacySourceSystem = null;
        createdData = null;
        createBy = null;
        currentOnClose = null;
        documentNumber = null;
        effectiveBeginDate = null;
        effectiveEndDate = null;
        eventDate = null;
        eventNameId = null;
        eventNameKey = null;
        eventNameLegend = null;
        eventDescriptionId = null;
        eventDescriptionKey = null;
        eventDescriptionLegend = null;
        eventSource = null;
        fixtureValue = null;
        improvementValue = null;
        improvementReasonCodeId = null;
        improvementReasonCodeKey = null;
        improvementReasonCodeLegend = null;
        inactiveDate = null;
        landValue = null;
        landReasonCodeId = null;
        landReasonCodeKey = null;
        landReasonCodeLegend = null;
        modifiedBy = null;
        modifiedDate = null;
        newConstructionDate = null;
        notes = null;
        originalAIN = null;
        originalBYEId = null;
        ownershipCode = null;
        partiallyCompleteNumber = null;
        percentOwnership = null;
        percentageAppliedToValue = null;
        personalPropertyType = null;
        ppDescription = null;
        percentageFixture = null;
        ppAssessmentNumber = null;
        recordingDate = null;
        retainedFixtureValue = null;
        retainedImprovementValue = null;
        retainedLandValue = null;
        retainedTotalValue = null;
        reviewRequired = null;
        rollType = null;
        selected = null;
        sequenceNumber = null;
        subUnitDescription = null;
        subUnitNumber = null;
        totalValue = null;
        totalAdjAcquisitionCost = null;
        transactionType = null;
        userId = null;
        verificationDate = null;
        verifiedBy = null;
        verified = null;
    }

   public int compareTo(BaseYearEventDO baseYearEvent) {                
            System.out.println(this+ "BaseYearEventDO"+
                        BaseYearEventUtils.LOGGER_PREFIX + "CCCC getEffectiveEndDate Value: " + this.getEffectiveEndDate());
            System.out.println(this+ "BaseYearEventDO"+
                        BaseYearEventUtils.LOGGER_PREFIX + "CCCC baseYearEvent.getEffectiveEndDate() Value: " + baseYearEvent.getEffectiveEndDate());
            int  result=0;
            
            result = customCompareDates( this.getEffectiveEndDate(), baseYearEvent.getEffectiveEndDate());         
            if(result != 0) return result;

            result = customCompareDates( this.getEventDate(), baseYearEvent.getEventDate());         
              if(result != 0) return result;
            
            result =  customCompareNumbers(this.getSequenceNumber(), baseYearEvent.getSequenceNumber());
                     
            return result;
        }
  
    /**
         *
         * @param thisDate
         * @param nextDate
         * @return
         */
        private int customCompareDates(Date thisDate, Date nextDate) {
            int result =0;
            if (thisDate == null && nextDate != null){
               result = -1;
            }
            else if (thisDate != null && nextDate == null){
                result = 1;
            }
            else if (thisDate != null && nextDate != null) {
                 result = nextDate.compareTo(thisDate);
            }
            else {
                result = 0;
            }
            return result;
        }
  
    public  static void main(String[] args){
        ArrayList<BaseYearEventDO> events = new ArrayList<BaseYearEventDO>();
        BaseYearEventDO arg0 = new BaseYearEventDO();
       
        BaseYearEventDO arg1 = new BaseYearEventDO();
        BaseYearEventDO arg2 = new BaseYearEventDO();
        BaseYearEventDO arg3 = new BaseYearEventDO();
        BaseYearEventDO arg4 = new BaseYearEventDO();
        BaseYearEventDO arg5= new BaseYearEventDO();
        arg5.setSequenceNumber(new BigInteger("1"));
       // arg0.setSequenceNumber(new BigInteger("1"));
        
        events.add(arg0);
        events.add(arg1);
        events.add(arg2);
        events.add(arg3);
        events.add(arg4);
        events.add(arg5);
        Date effectiveEndDate = new Date();
        Date effectiveEndDate3 = new Date();
        effectiveEndDate3.setYear(2000);
        arg0.setEffectiveEndDate(effectiveEndDate);
        Date effectiveEndDate2 = new Date();
        effectiveEndDate2.setYear(2010);
        
        arg1.setEffectiveEndDate(effectiveEndDate2);
        arg3.setEffectiveEndDate(effectiveEndDate3);
        System.out.println("======>before sort"+events.get(0).getEffectiveEndDate()+events.get(0).getSequenceNumber());
        System.out.println("======>before sort"+events.get(1).getEffectiveEndDate()+events.get(1).getSequenceNumber());
       
        System.out.println("======>before sort"+events.get(2).getEffectiveEndDate());
        System.out.println("======>before sort"+events.get(3).getEffectiveEndDate());
        System.out.println("======>before sort"+events.get(4).getEffectiveEndDate());
        
        Collections.sort(events);
        Collections.sort(events);
        
        System.out.println("======>after sort"+events.get(0).getEffectiveEndDate()+events.get(0).getSequenceNumber());
        System.out.println("======>after sort"+events.get(1).getEffectiveEndDate()+events.get(1).getSequenceNumber());
        System.out.println("======>after sort"+events.get(2).getEffectiveEndDate()+events.get(2).getSequenceNumber());
        System.out.println("======>after sort"+events.get(3).getEffectiveEndDate()+events.get(3).getSequenceNumber());
        System.out.println("======>after sort"+events.get(4).getEffectiveEndDate()+events.get(4).getSequenceNumber());
        System.out.println("======>after sort"+events.get(5).getEffectiveEndDate()+events.get(5).getSequenceNumber());        
    }


    private int customCompareNumbers(BigInteger thisNumber,
                                     BigInteger nextNumber) {
        int result=0;
                  if (thisNumber == null && nextNumber != null){
                      result = 1;
                   }
                   else if (thisNumber != null && nextNumber == null){
                       result = -1;
                   }
                   else if (thisNumber != null && nextNumber != null) {
                        result = nextNumber.compareTo(thisNumber);
                   }
                   else {
                       result = 0;
                   }
        return result;
    }
}
