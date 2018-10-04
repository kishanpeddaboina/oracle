package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.math.BigInteger;

public class LandDO extends AmpPojo{
    private String ain;
    private BigInteger aoid;
    private String dbUniqueId;
    private String dbUniqueIdUserEntered;
    private String dbUniqueIdFinal;
    private String transactionType;
    private String transactionTypeUserEntered;
    private String transactionTypeFinal;
    private Double width;
    private Double widthUserEntered;
    private Double widthFinal;
    private Double depth;
    private Double depthUserEntered;
    private Double depthFinal;
    private Double sizeSqft;
    private Double sizeSqftUserEntered;
    private Double sizeSqftFinal;
    private Double sizeUsableSqft;
    private Double sizeUsableSqftUserEntered;
    private Double sizeUsableSqftFinal;
    private Double sizeAcreAge;
    private Double sizeAcreAgeUserEntered;
    private Double sizeAcreAgeFinal;
    private String corner;
    private String cornerUserEntered;
    private String cornerFinal;
    private String sewer;
    private String sewerUserEntered;
    private String sewerFinal;
    private String traffic;
    private String trafficUserEntered;
    private String trafficFinal;
    private String freewayProximity;
    private String freewayProximityUserEntered;
    private String freewayProximityFinal;
    private String flightPath;
    private String flightPathUserEntered;
    private String flightPathFinal;
    private String golfFront;
    private String golfFrontUserEntered;
    private String golfFrontFinal;
    private String horses;
    private String horsesUserEntered; 
    private String horsesFinal; 
    private String viewCodeKey;
    private String viewCodeKeyUserEntered;    
    private String viewCodeKeyFinal;
    private String viewCodeDisplay;
    private String viewCodeDisplayUserEntered;
    private String viewCodeDisplayFinal;
    private BigInteger viewCodeRef;
    private BigInteger viewCodeRefUserEntered;
    private BigInteger viewCodeRefFinal;
    private String viewCodeLegend;
    private String viewCodeLegendUserEntered;
    private String viewCodeLegendFinal;
    private String useCode;
    private String useCodeUserEntered;
    private String useCodeFinal;
    private String useType;
    private String useTypeUserEntered;
    private String useTypeFinal;
    private String codeSplit;
    private String codeSplitUserEntered;
    private String codeSplitFinal;

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

    public void setWidthUserEntered(Double widthUserEntered) {
        this.widthUserEntered = widthUserEntered;
    }

    public Double getWidthUserEntered() {
        return widthUserEntered;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepthUserEntered(Double depthUserEntered) {
        this.depthUserEntered = depthUserEntered;
    }

    public Double getDepthUserEntered() {
        return depthUserEntered;
    }

    public void setSizeSqft(Double sizeSqft) {
        this.sizeSqft = sizeSqft;
    }

    public Double getSizeSqft() {
        return sizeSqft;
    }

    public void setSizeSqftUserEntered(Double sizeSqftUserEntered) {
        this.sizeSqftUserEntered = sizeSqftUserEntered;
    }

    public Double getSizeSqftUserEntered() {
        return sizeSqftUserEntered;
    }

    public void setSizeUsableSqft(Double sizeUsableSqft) {
        this.sizeUsableSqft = sizeUsableSqft;
    }

    public Double getSizeUsableSqft() {
        return sizeUsableSqft;
    }

    public void setSizeUsableSqftUserEntered(Double sizeUsableSqftUserEntered) {
        this.sizeUsableSqftUserEntered = sizeUsableSqftUserEntered;
    }

    public Double getSizeUsableSqftUserEntered() {
        return sizeUsableSqftUserEntered;
    }

    public void setSizeAcreAge(Double sizeAcreAge) {
        this.sizeAcreAge = sizeAcreAge;
    }

    public Double getSizeAcreAge() {
        return sizeAcreAge;
    }

    public void setSizeAcreAgeUserEntered(Double sizeAcreAgeUserEntered) {
        this.sizeAcreAgeUserEntered = sizeAcreAgeUserEntered;
    }

    public Double getSizeAcreAgeUserEntered() {
        return sizeAcreAgeUserEntered;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getCorner() {
        return corner;
    }

    public void setCornerUserEntered(String cornerUserEntered) {
        this.cornerUserEntered = cornerUserEntered;
    }

    public String getCornerUserEntered() {
        return cornerUserEntered;
    }

    public void setSewer(String sewer) {
        this.sewer = sewer;
    }

    public String getSewer() {
        return sewer;
    }

    public void setSewerUserEntered(String sewerUserEntered) {
        this.sewerUserEntered = sewerUserEntered;
    }

    public String getSewerUserEntered() {
        return sewerUserEntered;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTrafficUserEntered(String trafficUserEntered) {
        this.trafficUserEntered = trafficUserEntered;
    }

    public String getTrafficUserEntered() {
        return trafficUserEntered;
    }

    public void setFreewayProximity(String freewayProximity) {
        this.freewayProximity = freewayProximity;
    }

    public String getFreewayProximity() {
        return freewayProximity;
    }

    public void setFreewayProximityUserEntered(String freewayProximityUserEntered) {
        this.freewayProximityUserEntered = freewayProximityUserEntered;
    }

    public String getFreewayProximityUserEntered() {
        return freewayProximityUserEntered;
    }

    public void setFlightPath(String flightPath) {
        this.flightPath = flightPath;
    }

    public String getFlightPath() {
        return flightPath;
    }

    public void setFlightPathUserEntered(String flightPathUserEntered) {
        this.flightPathUserEntered = flightPathUserEntered;
    }

    public String getFlightPathUserEntered() {
        return flightPathUserEntered;
    }

    public void setGolfFront(String golfFront) {
        this.golfFront = golfFront;
    }

    public String getGolfFront() {
        return golfFront;
    }

    public void setGolfFrontUserEntered(String golfFrontUserEntered) {
        this.golfFrontUserEntered = golfFrontUserEntered;
    }

    public String getGolfFrontUserEntered() {
        return golfFrontUserEntered;
    }

    public void setHorses(String horses) {
        this.horses = horses;
    }

    public String getHorses() {
        return horses;
    }

    public void setHorsesUserEntered(String horsesUserEntered) {
        this.horsesUserEntered = horsesUserEntered;
    }

    public String getHorsesUserEntered() {
        return horsesUserEntered;
    }

    public void setViewCodeKey(String viewCodeKey) {
        this.viewCodeKey = viewCodeKey;
    }

    public String getViewCodeKey() {
        return viewCodeKey;
    }

    public void setViewCodeKeyUserEntered(String viewCodeKeyUserEntered) {
        this.viewCodeKeyUserEntered = viewCodeKeyUserEntered;
    }

    public String getViewCodeKeyUserEntered() {
        return viewCodeKeyUserEntered;
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

    public void setUseCodeUserEntered(String useCodeUserEntered) {
        this.useCodeUserEntered = useCodeUserEntered;
    }

    public String getUseCodeUserEntered() {
        return useCodeUserEntered;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseTypeUserEntered(String useTypeUserEntered) {
        this.useTypeUserEntered = useTypeUserEntered;
    }

    public String getUseTypeUserEntered() {
        return useTypeUserEntered;
    }

    public void setCodeSplit(String codeSplit) {
        this.codeSplit = codeSplit;
    }

    public String getCodeSplit() {
        return codeSplit;
    }

    public void setCodeSplitUserEntered(String codeSplitUserEntered) {
        this.codeSplitUserEntered = codeSplitUserEntered;
    }

    public String getCodeSplitUserEntered() {
        return codeSplitUserEntered;
    }

    public void setViewCodeRefUserEntered(BigInteger viewCodeRefUserEntered) {
        this.viewCodeRefUserEntered = viewCodeRefUserEntered;
    }

    public BigInteger getViewCodeRefUserEntered() {
        return viewCodeRefUserEntered;
    }

    public void setViewCodeLegendUserEntered(String viewCodeLegendUserEntered) {
        this.viewCodeLegendUserEntered = viewCodeLegendUserEntered;
    }

    public String getViewCodeLegendUserEntered() {
        return viewCodeLegendUserEntered;
    }

    public void setWidthFinal(Double widthFinal) {
        this.widthFinal = widthFinal;
    }

    public Double getWidthFinal() {
        return widthFinal;
    }

    public void setDepthFinal(Double depthFinal) {
        this.depthFinal = depthFinal;
    }

    public Double getDepthFinal() {
        return depthFinal;
    }

    public void setSizeSqftFinal(Double sizeSqftFinal) {
        this.sizeSqftFinal = sizeSqftFinal;
    }

    public Double getSizeSqftFinal() {
        return sizeSqftFinal;
    }

    public void setSizeUsableSqftFinal(Double sizeUsableSqftFinal) {
        this.sizeUsableSqftFinal = sizeUsableSqftFinal;
    }

    public Double getSizeUsableSqftFinal() {
        return sizeUsableSqftFinal;
    }

    public void setSizeAcreAgeFinal(Double sizeAcreAgeFinal) {
        this.sizeAcreAgeFinal = sizeAcreAgeFinal;
    }

    public Double getSizeAcreAgeFinal() {
        return sizeAcreAgeFinal;
    }

    public void setCornerFinal(String cornerFinal) {
        this.cornerFinal = cornerFinal;
    }

    public String getCornerFinal() {
        return cornerFinal;
    }

    public void setSewerFinal(String sewerFinal) {
        this.sewerFinal = sewerFinal;
    }

    public String getSewerFinal() {
        return sewerFinal;
    }

    public void setTrafficFinal(String trafficFinal) {
        this.trafficFinal = trafficFinal;
    }

    public String getTrafficFinal() {
        return trafficFinal;
    }

    public void setFreewayProximityFinal(String freewayProximityFinal) {
        this.freewayProximityFinal = freewayProximityFinal;
    }

    public String getFreewayProximityFinal() {
        return freewayProximityFinal;
    }

    public void setFlightPathFinal(String flightPathFinal) {
        this.flightPathFinal = flightPathFinal;
    }

    public String getFlightPathFinal() {
        return flightPathFinal;
    }

    public void setGolfFrontFinal(String golfFrontFinal) {
        this.golfFrontFinal = golfFrontFinal;
    }

    public String getGolfFrontFinal() {
        return golfFrontFinal;
    }

    public void setHorsesFinal(String horsesFinal) {
        this.horsesFinal = horsesFinal;
    }

    public String getHorsesFinal() {
        return horsesFinal;
    }

    public void setViewCodeKeyFinal(String viewCodeKeyFinal) {
        this.viewCodeKeyFinal = viewCodeKeyFinal;
    }

    public String getViewCodeKeyFinal() {
        return viewCodeKeyFinal;
    }

    public void setViewCodeRefFinal(BigInteger viewCodeRefFinal) {
        this.viewCodeRefFinal = viewCodeRefFinal;
    }

    public BigInteger getViewCodeRefFinal() {
        return viewCodeRefFinal;
    }

    public void setViewCodeLegendFinal(String viewCodeLegendFinal) {
        this.viewCodeLegendFinal = viewCodeLegendFinal;
    }

    public String getViewCodeLegendFinal() {
        return viewCodeLegendFinal;
    }

    public void setUseCodeFinal(String useCodeFinal) {
        this.useCodeFinal = useCodeFinal;
    }

    public String getUseCodeFinal() {
        return useCodeFinal;
    }

    public void setUseTypeFinal(String useTypeFinal) {
        this.useTypeFinal = useTypeFinal;
    }

    public String getUseTypeFinal() {
        return useTypeFinal;
    }

    public void setCodeSplitFinal(String codeSplitFinal) {
        this.codeSplitFinal = codeSplitFinal;
    }

    public String getCodeSplitFinal() {
        return codeSplitFinal;
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

    public void setViewCodeDisplay(String viewCodeDisplay) {
        this.viewCodeDisplay = viewCodeDisplay;
    }

    public String getViewCodeDisplay() {
        return viewCodeDisplay;
    }

    public void setViewCodeDisplayUserEntered(String viewCodeDisplayUserEntered) {
        this.viewCodeDisplayUserEntered = viewCodeDisplayUserEntered;
    }

    public String getViewCodeDisplayUserEntered() {
        return viewCodeDisplayUserEntered;
    }

    public void setViewCodeDisplayFinal(String viewCodeDisplayFinal) {
        this.viewCodeDisplayFinal = viewCodeDisplayFinal;
    }

    public String getViewCodeDisplayFinal() {
        return viewCodeDisplayFinal;
    }
}
