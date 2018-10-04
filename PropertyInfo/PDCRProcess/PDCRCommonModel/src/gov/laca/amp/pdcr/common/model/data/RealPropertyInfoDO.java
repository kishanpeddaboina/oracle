package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


public class RealPropertyInfoDO extends AmpPojo{
    private String ain;
    private BigInteger aoid;
    private String region;
    private String cluster;
    private String propertyAddress;
    private String district;
    private RealPropertyInfoDO.SubPartList subPartList;
    private RealPropertyInfoDO.Composite composite;
    private RealPropertyInfoDO.Land land;

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

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getCluster() {
        return cluster;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setSubPartList(RealPropertyInfoDO.SubPartList subPartList) {
        this.subPartList = subPartList;
    }

    public RealPropertyInfoDO.SubPartList getSubPartList() {
        return subPartList;
    }

    public void setComposite(RealPropertyInfoDO.Composite composite) {
        this.composite = composite;
    }

    public RealPropertyInfoDO.Composite getComposite() {
        return composite;
    }

    public void setLand(RealPropertyInfoDO.Land land) {
        this.land = land;
    }

    public RealPropertyInfoDO.Land getLand() {
        return land;
    }
    
    public static class Composite {
        private String dbUniqueId;
        private String ain;
        private BigInteger aoid;
        private String transactionType;
        private Double sqft;

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

        public void setSqft(Double sqft) {
            this.sqft = sqft;
        }

        public Double getSqft() {
            return sqft;
        }
    }

    public static class Land {
        private String dbUniqueId;
        private String ain;
        private BigInteger aoid;
        private String transactionType;
        private Double width;
        private Double depth;
        private Double sizeSqft;
        private Double sizeUsableSqft;
        private Double sizeAcreAge;
        private String corner;
        private String sewer;
        private String traffic;
        private String freewayProximity;
        private String flightPath;
        private String golfFront;
        private String horses;
        private String viewCodeKey;
        private BigInteger viewCodeRef;
        private String viewCodeLegend;
        private String useCode;
        private String useType;
        private String codeSplit;

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

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getWidth() {
            return width;
        }

        public void setDepth(Double depth) {
            this.depth = depth;
        }

        public Double getDepth() {
            return depth;
        }

        public void setSizeSqft(Double sizeSqft) {
            this.sizeSqft = sizeSqft;
        }

        public Double getSizeSqft() {
            return sizeSqft;
        }

        public void setSizeUsableSqft(Double sizeUsableSqft) {
            this.sizeUsableSqft = sizeUsableSqft;
        }

        public Double getSizeUsableSqft() {
            return sizeUsableSqft;
        }

        public void setSizeAcreAge(Double sizeAcreAge) {
            this.sizeAcreAge = sizeAcreAge;
        }

        public Double getSizeAcreAge() {
            return sizeAcreAge;
        }

        public void setCorner(String corner) {
            this.corner = corner;
        }

        public String getCorner() {
            return corner;
        }

        public void setSewer(String sewer) {
            this.sewer = sewer;
        }

        public String getSewer() {
            return sewer;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setFreewayProximity(String freewayProximity) {
            this.freewayProximity = freewayProximity;
        }

        public String getFreewayProximity() {
            return freewayProximity;
        }

        public void setFlightPath(String flightPath) {
            this.flightPath = flightPath;
        }

        public String getFlightPath() {
            return flightPath;
        }

        public void setGolfFront(String golfFront) {
            this.golfFront = golfFront;
        }

        public String getGolfFront() {
            return golfFront;
        }

        public void setHorses(String horses) {
            this.horses = horses;
        }

        public String getHorses() {
            return horses;
        }

        public void setViewCodeKey(String viewCodeKey) {
            this.viewCodeKey = viewCodeKey;
        }

        public String getViewCodeKey() {
            return viewCodeKey;
        }

        public void setViewCodeRef(BigInteger viewCodeRef) {
            this.viewCodeRef = viewCodeRef;
        }

        public BigInteger getViewCodeRef() {
            return viewCodeRef;
        }

        public void setViewCodeLegend(String viewCodeLegend) {
            this.viewCodeLegend = viewCodeLegend;
        }

        public String getViewCodeLegend() {
            return viewCodeLegend;
        }

        public void setUseCode(String useCode) {
            this.useCode = useCode;
        }

        public String getUseCode() {
            return useCode;
        }

        public void setUseType(String useType) {
            this.useType = useType;
        }

        public String getUseType() {
            return useType;
        }

        public void setCodeSplit(String codeSplit) {
            this.codeSplit = codeSplit;
        }

        public String getCodeSplit() {
            return codeSplit;
        }
    }

    public static class SubPartList {
        private List<RealPropertyInfoDO.SubPartList.SubPart> subPart;

        public List<RealPropertyInfoDO.SubPartList.SubPart> getSubPart() {
            if (subPart == null) {
                subPart = new ArrayList<RealPropertyInfoDO.SubPartList.SubPart>();
            }
            return this.subPart;
        }

        public static class SubPart {
            private String dbUniqueId;
            private String ain;
            private BigInteger aoid;
            private String transactionType;
            private BigInteger improvementNumber;
            private String subPartNumber;
            private BigInteger rcnYearChange;
            private BigInteger designTypeRef;
            private String designTypeKey;
            private String designTypeLegend;
            private String qualityClass;
            private BigInteger yearBuilt;
            private BigInteger effectiveYearBuilt;
            private String depreciationTable;
            private BigInteger numberOfUnits;
            private BigInteger bedroomCount;
            private BigInteger bathroomCount;
            private Double improvementSizeSqft;
            private Double rcnOtherTotal;
            private Double costFactor;
            private Double rcnMain;
            private BigDecimal percentGood;
            private BigInteger addedDepreciationTypeRef;
            private String addedDepreciationTypeKey;
            private String addedDepreciationTypeLegend;
            private BigDecimal addedDepreciationPercent;
            private Double rcnLessDepreciation;

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

            public void setRcnYearChange(BigInteger rcnYearChange) {
                this.rcnYearChange = rcnYearChange;
            }

            public BigInteger getRcnYearChange() {
                return rcnYearChange;
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

            public void setYearBuilt(BigInteger yearBuilt) {
                this.yearBuilt = yearBuilt;
            }

            public BigInteger getYearBuilt() {
                return yearBuilt;
            }

            public void setEffectiveYearBuilt(BigInteger effectiveYearBuilt) {
                this.effectiveYearBuilt = effectiveYearBuilt;
            }

            public BigInteger getEffectiveYearBuilt() {
                return effectiveYearBuilt;
            }

            public void setDepreciationTable(String depreciationTable) {
                this.depreciationTable = depreciationTable;
            }

            public String getDepreciationTable() {
                return depreciationTable;
            }

            public void setNumberOfUnits(BigInteger numberOfUnits) {
                this.numberOfUnits = numberOfUnits;
            }

            public BigInteger getNumberOfUnits() {
                return numberOfUnits;
            }

            public void setBedroomCount(BigInteger bedroomCount) {
                this.bedroomCount = bedroomCount;
            }

            public BigInteger getBedroomCount() {
                return bedroomCount;
            }

            public void setBathroomCount(BigInteger bathroomCount) {
                this.bathroomCount = bathroomCount;
            }

            public BigInteger getBathroomCount() {
                return bathroomCount;
            }

            public void setImprovementSizeSqft(Double improvementSizeSqft) {
                this.improvementSizeSqft = improvementSizeSqft;
            }

            public Double getImprovementSizeSqft() {
                return improvementSizeSqft;
            }

            public void setRcnOtherTotal(Double rcnOtherTotal) {
                this.rcnOtherTotal = rcnOtherTotal;
            }

            public Double getRcnOtherTotal() {
                return rcnOtherTotal;
            }

            public void setCostFactor(Double costFactor) {
                this.costFactor = costFactor;
            }

            public Double getCostFactor() {
                return costFactor;
            }

            public void setRcnMain(Double rcnMain) {
                this.rcnMain = rcnMain;
            }

            public Double getRcnMain() {
                return rcnMain;
            }

            public void setPercentGood(BigDecimal percentGood) {
                this.percentGood = percentGood;
            }

            public BigDecimal getPercentGood() {
                return percentGood;
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

            public void setRcnLessDepreciation(Double rcnLessDepreciation) {
                this.rcnLessDepreciation = rcnLessDepreciation;
            }

            public Double getRcnLessDepreciation() {
                return rcnLessDepreciation;
            }
        }
    }
    
}
