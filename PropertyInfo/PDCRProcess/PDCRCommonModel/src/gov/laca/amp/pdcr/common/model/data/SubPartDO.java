package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SubPartDO extends AmpPojo{
    private String ain;
    private BigInteger aoid;
    private String subPartNumberDisplay;
    private String dbUniqueId;
    private String dbUniqueIdUserEntered;
    private String dbUniqueIdFinal;
    private String transactionType;
    private String transactionTypeUserEntered;
    private String transactionTypeFinal;
    private BigInteger improvementNumber;
    private BigInteger improvementNumberUserEntered;   
    private BigInteger improvementNumberFinal;  
    private String subPartNumber;
    private String subPartNumberUserEntered;
    private String subPartNumberFinal;
    private BigInteger rcnYearChange;
    private BigInteger rcnYearChangeUserEntered;
    private BigInteger rcnYearChangeFinal;
    private BigInteger designTypeRef;
    private BigInteger designTypeRefUserEntered;
    private BigInteger designTypeRefFinal;
    private String designTypeKey;
    private String designTypeKeyUserEntered;
    private String designTypeKeyFinal;
    private String designTypeLegend; 
    private String designTypeLegendUserEntered; 
    private String designTypeLegendFinal; 
    private String qualityClass;
    private String qualityClassUserEntered;
    private String qualityClassFinal;
    private BigInteger yearBuilt;
    private BigInteger yearBuiltUserEntered;
    private BigInteger yearBuiltFinal;
    private BigInteger effectiveYearBuilt;
    private BigInteger effectiveYearBuiltUserEntered;
    private BigInteger effectiveYearBuiltFinal;
    private String depreciationTable;
    private String depreciationTableUserEntered;
    private String depreciationTableFinal;
    private BigInteger numberOfUnits;
    private BigInteger numberOfUnitsUserEntered;
    private BigInteger numberOfUnitsFinal;
    private BigInteger bedroomCount;
    private BigInteger bedroomCountUserEntered;
    private BigInteger bedroomCountFinal;
    private BigInteger bathroomCount;
    private BigInteger bathroomCountUserEntered;
    private BigInteger bathroomCountFinal;
    private Double improvementSizeSqft;
    private Double improvementSizeSqftUserEntered;
    private Double improvementSizeSqftFinal;
    private Double rcnOtherTotal;
    private Double rcnOtherTotalUserEntered;
    private Double rcnOtherTotalFinal;
    private Double costFactor;
    private Double costFactorUserEntered;
    private Double costFactorFinal;
    private Double rcnMain;
    private Double rcnMainUserEntered;
    private Double rcnMainFinal;
    private BigDecimal percentGood;
    private BigDecimal percentGoodUserEntered;
    private BigDecimal percentGoodFinal;
    private BigInteger addedDepreciationTypeRef;
    private BigInteger addedDepreciationTypeRefUserEntered;
    private BigInteger addedDepreciationTypeRefFinal;
    private String addedDepreciationTypeKey;
    private String addedDepreciationTypeKeyUserEntered;
    private String addedDepreciationTypeKeyFinal;
    private String addedDepreciationTypeLegend;
    private String addedDepreciationTypeLegendUserEntered;
    private String addedDepreciationTypeLegendFinal;
    private BigDecimal addedDepreciationPercent;
    private BigDecimal addedDepreciationPercentUserEntered;
    private BigDecimal addedDepreciationPercentFinal;
    private Double rcnLessDepreciation;
    private Double rcnLessDepreciationUserEntered;
    private Double rcnLessDepreciationFinal;

    public void setDbUniqueId(String dbUniqueId) {
        this.dbUniqueId = dbUniqueId;
    }

    public String getDbUniqueId() {
        return dbUniqueId;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setAoid(BigInteger aoid) {
        this.aoid = aoid;
    }

    public BigInteger getAoid() {
        return aoid;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setImprovementNumber(BigInteger improvementNumber) {
        this.improvementNumber = improvementNumber;
    }

    public BigInteger getImprovementNumber() {
        return improvementNumber;
    }

    public void setSubPartNumber(String subPartNumber) {
        this.subPartNumber = subPartNumber;
    }

    public String getSubPartNumber() {
        return subPartNumber;
    }

    public void setSubPartNumberUserEntered(String subPartNumberUserEntered) {
        this.subPartNumberUserEntered = subPartNumberUserEntered;
    }

    public String getSubPartNumberUserEntered() {
        return subPartNumberUserEntered;
    }

    public void setRcnYearChange(BigInteger rcnYearChange) {
        this.rcnYearChange = rcnYearChange;
    }

    public BigInteger getRcnYearChange() {
        return rcnYearChange;
    }

    public void setRcnYearChangeUserEntered(BigInteger rcnYearChangeUserEntered) {
        this.rcnYearChangeUserEntered = rcnYearChangeUserEntered;
    }

    public BigInteger getRcnYearChangeUserEntered() {
        return rcnYearChangeUserEntered;
    }

    public void setDesignTypeRef(BigInteger designTypeRef) {
        this.designTypeRef = designTypeRef;
    }

    public BigInteger getDesignTypeRef() {
        return designTypeRef;
    }

    public void setDesignTypeKey(String designTypeKey) {
        this.designTypeKey = designTypeKey;
    }

    public String getDesignTypeKey() {
        return designTypeKey;
    }

    public void setDesignTypeKeyUserEntered(String designTypeKeyUserEntered) {
        this.designTypeKeyUserEntered = designTypeKeyUserEntered;
    }

    public String getDesignTypeKeyUserEntered() {
        return designTypeKeyUserEntered;
    }

    public void setDesignTypeLegend(String designTypeLegend) {
        this.designTypeLegend = designTypeLegend;
    }

    public String getDesignTypeLegend() {
        return designTypeLegend;
    }

    public void setQualityClass(String qualityClass) {
        this.qualityClass = qualityClass;
    }

    public String getQualityClass() {
        return qualityClass;
    }

    public void setQualityClassUserEntered(String qualityClassUserEntered) {
        this.qualityClassUserEntered = qualityClassUserEntered;
    }

    public String getQualityClassUserEntered() {
        return qualityClassUserEntered;
    }

    public void setYearBuilt(BigInteger yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public BigInteger getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuiltUserEntered(BigInteger yearBuiltUserEntered) {
        this.yearBuiltUserEntered = yearBuiltUserEntered;
    }

    public BigInteger getYearBuiltUserEntered() {
        return yearBuiltUserEntered;
    }

    public void setEffectiveYearBuilt(BigInteger effectiveYearBuilt) {
        this.effectiveYearBuilt = effectiveYearBuilt;
    }

    public BigInteger getEffectiveYearBuilt() {
        return effectiveYearBuilt;
    }

    public void setEffectiveYearBuiltUserEntered(BigInteger effectiveYearBuiltUserEntered) {
        this.effectiveYearBuiltUserEntered = effectiveYearBuiltUserEntered;
    }

    public BigInteger getEffectiveYearBuiltUserEntered() {
        return effectiveYearBuiltUserEntered;
    }

    public void setDepreciationTable(String depreciationTable) {
        this.depreciationTable = depreciationTable;
    }

    public String getDepreciationTable() {
        return depreciationTable;
    }

    public void setDepreciationTableUserEntered(String depreciationTableUserEntered) {
        this.depreciationTableUserEntered = depreciationTableUserEntered;
    }

    public String getDepreciationTableUserEntered() {
        return depreciationTableUserEntered;
    }

    public void setNumberOfUnits(BigInteger numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public BigInteger getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnitsUserEntered(BigInteger numberOfUnitsUserEntered) {
        this.numberOfUnitsUserEntered = numberOfUnitsUserEntered;
    }

    public BigInteger getNumberOfUnitsUserEntered() {
        return numberOfUnitsUserEntered;
    }

    public void setBedroomCount(BigInteger bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public BigInteger getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCountUserEntered(BigInteger bedroomCountUserEntered) {
        this.bedroomCountUserEntered = bedroomCountUserEntered;
    }

    public BigInteger getBedroomCountUserEntered() {
        return bedroomCountUserEntered;
    }

    public void setBathroomCount(BigInteger bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public BigInteger getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCountUserEntered(BigInteger bathroomCountUserEntered) {
        this.bathroomCountUserEntered = bathroomCountUserEntered;
    }

    public BigInteger getBathroomCountUserEntered() {
        return bathroomCountUserEntered;
    }

    public void setImprovementSizeSqft(Double improvementSizeSqft) {
        this.improvementSizeSqft = improvementSizeSqft;
    }

    public Double getImprovementSizeSqft() {
        return improvementSizeSqft;
    }

    public void setImprovementSizeSqftUserEntered(Double improvementSizeSqftUserEntered) {
        this.improvementSizeSqftUserEntered = improvementSizeSqftUserEntered;
    }

    public Double getImprovementSizeSqftUserEntered() {
        return improvementSizeSqftUserEntered;
    }

    public void setRcnOtherTotal(Double rcnOtherTotal) {
        this.rcnOtherTotal = rcnOtherTotal;
    }

    public Double getRcnOtherTotal() {
        return rcnOtherTotal;
    }

    public void setRcnOtherTotalUserEntered(Double rcnOtherTotalUserEntered) {
        this.rcnOtherTotalUserEntered = rcnOtherTotalUserEntered;
    }

    public Double getRcnOtherTotalUserEntered() {
        return rcnOtherTotalUserEntered;
    }

    public void setCostFactor(Double costFactor) {
        this.costFactor = costFactor;
    }

    public Double getCostFactor() {
        return costFactor;
    }

    public void setCostFactorUserEntered(Double costFactorUserEntered) {
        this.costFactorUserEntered = costFactorUserEntered;
    }

    public Double getCostFactorUserEntered() {
        return costFactorUserEntered;
    }

    public void setRcnMain(Double rcnMain) {
        this.rcnMain = rcnMain;
    }

    public Double getRcnMain() {
        return rcnMain;
    }

    public void setRcnMainUserEntered(Double rcnMainUserEntered) {
        this.rcnMainUserEntered = rcnMainUserEntered;
    }

    public Double getRcnMainUserEntered() {
        return rcnMainUserEntered;
    }

    public void setPercentGood(BigDecimal percentGood) {
        this.percentGood = percentGood;
    }

    public BigDecimal getPercentGood() {
        return percentGood;
    }

    public void setPercentGoodUserEntered(BigDecimal percentGoodUserEntered) {
        this.percentGoodUserEntered = percentGoodUserEntered;
    }

    public BigDecimal getPercentGoodUserEntered() {
        return percentGoodUserEntered;
    }

    public void setAddedDepreciationTypeRef(BigInteger addedDepreciationTypeRef) {
        this.addedDepreciationTypeRef = addedDepreciationTypeRef;
    }

    public BigInteger getAddedDepreciationTypeRef() {
        return addedDepreciationTypeRef;
    }

    public void setAddedDepreciationTypeKey(String addedDepreciationTypeKey) {
        this.addedDepreciationTypeKey = addedDepreciationTypeKey;
    }

    public String getAddedDepreciationTypeKey() {
        return addedDepreciationTypeKey;
    }

    public void setAddedDepreciationTypeKeyUserEntered(String addedDepreciationTypeKeyUserEntered) {
        this.addedDepreciationTypeKeyUserEntered = addedDepreciationTypeKeyUserEntered;
    }

    public String getAddedDepreciationTypeKeyUserEntered() {
        return addedDepreciationTypeKeyUserEntered;
    }

    public void setAddedDepreciationTypeLegend(String addedDepreciationTypeLegend) {
        this.addedDepreciationTypeLegend = addedDepreciationTypeLegend;
    }

    public String getAddedDepreciationTypeLegend() {
        return addedDepreciationTypeLegend;
    }

    public void setAddedDepreciationPercent(BigDecimal addedDepreciationPercent) {
        this.addedDepreciationPercent = addedDepreciationPercent;
    }

    public BigDecimal getAddedDepreciationPercent() {
        return addedDepreciationPercent;
    }

    public void setAddedDepreciationPercentUserEntered(BigDecimal addedDepreciationPercentUserEntered) {
        this.addedDepreciationPercentUserEntered = addedDepreciationPercentUserEntered;
    }

    public BigDecimal getAddedDepreciationPercentUserEntered() {
        return addedDepreciationPercentUserEntered;
    }

    public void setRcnLessDepreciation(Double rcnLessDepreciation) {
        this.rcnLessDepreciation = rcnLessDepreciation;
    }

    public Double getRcnLessDepreciation() {
        return rcnLessDepreciation;
    }

    public void setRcnLessDepreciationUserEntered(Double rcnLessDepreciationUserEntered) {
        this.rcnLessDepreciationUserEntered = rcnLessDepreciationUserEntered;
    }

    public Double getRcnLessDepreciationUserEntered() {
        return rcnLessDepreciationUserEntered;
    }

    public void setImprovementNumberUserEntered(BigInteger improvementNumberUserEntered) {
        this.improvementNumberUserEntered = improvementNumberUserEntered;
    }

    public BigInteger getImprovementNumberUserEntered() {
        return improvementNumberUserEntered;
    }

    public void setDesignTypeRefUserEntered(BigInteger designTypeRefUserEntered) {
        this.designTypeRefUserEntered = designTypeRefUserEntered;
    }

    public BigInteger getDesignTypeRefUserEntered() {
        return designTypeRefUserEntered;
    }

    public void setDesignTypeLegendUserEntered(String designTypeLegendUserEntered) {
        this.designTypeLegendUserEntered = designTypeLegendUserEntered;
    }

    public String getDesignTypeLegendUserEntered() {
        return designTypeLegendUserEntered;
    }

    public void setAddedDepreciationTypeRefUserEntered(BigInteger addedDepreciationTypeRefUserEntered) {
        this.addedDepreciationTypeRefUserEntered = addedDepreciationTypeRefUserEntered;
    }

    public BigInteger getAddedDepreciationTypeRefUserEntered() {
        return addedDepreciationTypeRefUserEntered;
    }

    public void setAddedDepreciationTypeLegendUserEntered(String addedDepreciationTypeLegendUserEntered) {
        this.addedDepreciationTypeLegendUserEntered = addedDepreciationTypeLegendUserEntered;
    }

    public String getAddedDepreciationTypeLegendUserEntered() {
        return addedDepreciationTypeLegendUserEntered;
    }

    public void setImprovementNumberFinal(BigInteger improvementNumberFinal) {
        this.improvementNumberFinal = improvementNumberFinal;
    }

    public BigInteger getImprovementNumberFinal() {
        return improvementNumberFinal;
    }

    public void setSubPartNumberFinal(String subPartNumberFinal) {
        this.subPartNumberFinal = subPartNumberFinal;
    }

    public String getSubPartNumberFinal() {
        return subPartNumberFinal;
    }

    public void setRcnYearChangeFinal(BigInteger rcnYearChangeFinal) {
        this.rcnYearChangeFinal = rcnYearChangeFinal;
    }

    public BigInteger getRcnYearChangeFinal() {
        return rcnYearChangeFinal;
    }

    public void setDesignTypeRefFinal(BigInteger designTypeRefFinal) {
        this.designTypeRefFinal = designTypeRefFinal;
    }

    public BigInteger getDesignTypeRefFinal() {
        return designTypeRefFinal;
    }

    public void setDesignTypeKeyFinal(String designTypeKeyFinal) {
        this.designTypeKeyFinal = designTypeKeyFinal;
    }

    public String getDesignTypeKeyFinal() {
        return designTypeKeyFinal;
    }

    public void setDesignTypeLegendFinal(String designTypeLegendFinal) {
        this.designTypeLegendFinal = designTypeLegendFinal;
    }

    public String getDesignTypeLegendFinal() {
        return designTypeLegendFinal;
    }

    public void setQualityClassFinal(String qualityClassFinal) {
        this.qualityClassFinal = qualityClassFinal;
    }

    public String getQualityClassFinal() {
        return qualityClassFinal;
    }

    public void setYearBuiltFinal(BigInteger yearBuiltFinal) {
        this.yearBuiltFinal = yearBuiltFinal;
    }

    public BigInteger getYearBuiltFinal() {
        return yearBuiltFinal;
    }

    public void setEffectiveYearBuiltFinal(BigInteger effectiveYearBuiltFinal) {
        this.effectiveYearBuiltFinal = effectiveYearBuiltFinal;
    }

    public BigInteger getEffectiveYearBuiltFinal() {
        return effectiveYearBuiltFinal;
    }

    public void setDepreciationTableFinal(String depreciationTableFinal) {
        this.depreciationTableFinal = depreciationTableFinal;
    }

    public String getDepreciationTableFinal() {
        return depreciationTableFinal;
    }

    public void setNumberOfUnitsFinal(BigInteger numberOfUnitsFinal) {
        this.numberOfUnitsFinal = numberOfUnitsFinal;
    }

    public BigInteger getNumberOfUnitsFinal() {
        return numberOfUnitsFinal;
    }

    public void setBedroomCountFinal(BigInteger bedroomCountFinal) {
        this.bedroomCountFinal = bedroomCountFinal;
    }

    public BigInteger getBedroomCountFinal() {
        return bedroomCountFinal;
    }

    public void setBathroomCountFinal(BigInteger bathroomCountFinal) {
        this.bathroomCountFinal = bathroomCountFinal;
    }

    public BigInteger getBathroomCountFinal() {
        return bathroomCountFinal;
    }

    public void setImprovementSizeSqftFinal(Double improvementSizeSqftFinal) {
        this.improvementSizeSqftFinal = improvementSizeSqftFinal;
    }

    public Double getImprovementSizeSqftFinal() {
        return improvementSizeSqftFinal;
    }

    public void setRcnOtherTotalFinal(Double rcnOtherTotalFinal) {
        this.rcnOtherTotalFinal = rcnOtherTotalFinal;
    }

    public Double getRcnOtherTotalFinal() {
        return rcnOtherTotalFinal;
    }

    public void setCostFactorFinal(Double costFactorFinal) {
        this.costFactorFinal = costFactorFinal;
    }

    public Double getCostFactorFinal() {
        return costFactorFinal;
    }

    public void setRcnMainFinal(Double rcnMainFinal) {
        this.rcnMainFinal = rcnMainFinal;
    }

    public Double getRcnMainFinal() {
        return rcnMainFinal;
    }

    public void setPercentGoodFinal(BigDecimal percentGoodFinal) {
        this.percentGoodFinal = percentGoodFinal;
    }

    public BigDecimal getPercentGoodFinal() {
        return percentGoodFinal;
    }

    public void setAddedDepreciationTypeRefFinal(BigInteger addedDepreciationTypeRefFinal) {
        this.addedDepreciationTypeRefFinal = addedDepreciationTypeRefFinal;
    }

    public BigInteger getAddedDepreciationTypeRefFinal() {
        return addedDepreciationTypeRefFinal;
    }

    public void setAddedDepreciationTypeKeyFinal(String addedDepreciationTypeKeyFinal) {
        this.addedDepreciationTypeKeyFinal = addedDepreciationTypeKeyFinal;
    }

    public String getAddedDepreciationTypeKeyFinal() {
        return addedDepreciationTypeKeyFinal;
    }

    public void setAddedDepreciationTypeLegendFinal(String addedDepreciationTypeLegendFinal) {
        this.addedDepreciationTypeLegendFinal = addedDepreciationTypeLegendFinal;
    }

    public String getAddedDepreciationTypeLegendFinal() {
        return addedDepreciationTypeLegendFinal;
    }

    public void setAddedDepreciationPercentFinal(BigDecimal addedDepreciationPercentFinal) {
        this.addedDepreciationPercentFinal = addedDepreciationPercentFinal;
    }

    public BigDecimal getAddedDepreciationPercentFinal() {
        return addedDepreciationPercentFinal;
    }

    public void setRcnLessDepreciationFinal(Double rcnLessDepreciationFinal) {
        this.rcnLessDepreciationFinal = rcnLessDepreciationFinal;
    }

    public Double getRcnLessDepreciationFinal() {
        return rcnLessDepreciationFinal;
    }

    public void setTransactionTypeFinal(String transactionTypeFinal) {
        this.transactionTypeFinal = transactionTypeFinal;
    }

    public String getTransactionTypeFinal() {
        return transactionTypeFinal;
    }

    public void setTransactionTypeUserEntered(String transactionTypeUserEntered) {
        this.transactionTypeUserEntered = transactionTypeUserEntered;
    }

    public String getTransactionTypeUserEntered() {
        return transactionTypeUserEntered;
    }

    public void setDbUniqueIdUserEntered(String dbUniqueIdUserEntered) {
        this.dbUniqueIdUserEntered = dbUniqueIdUserEntered;
    }

    public String getDbUniqueIdUserEntered() {
        return dbUniqueIdUserEntered;
    }

    public void setDbUniqueIdFinal(String dbUniqueIdFinal) {
        this.dbUniqueIdFinal = dbUniqueIdFinal;
    }

    public String getDbUniqueIdFinal() {
        return dbUniqueIdFinal;
    }

    public void setSubPartNumberDisplay(String subPartNumberDisplay) {
        this.subPartNumberDisplay = subPartNumberDisplay;
    }

    public String getSubPartNumberDisplay() {
        return subPartNumberDisplay;
    }
}
