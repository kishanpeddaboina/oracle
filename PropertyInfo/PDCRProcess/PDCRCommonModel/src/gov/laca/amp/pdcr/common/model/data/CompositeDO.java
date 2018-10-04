package gov.laca.amp.pdcr.common.model.data;

import gov.laca.amp.fwk.extn.pojo.AmpPojo;

import java.math.BigInteger;

public class CompositeDO extends AmpPojo{
    private String ain;
    private BigInteger aoid;
    private String dbUniqueId;
    private String dbUniqueIdUserEntered;
    private String dbUniqueIdFinal;
    private String transactionType;
    private String transactionTypeUserEntered;
    private String transactionTypeFinal;
    private Double sqft;
    private Double sqftUserEntered;
    private Double sqftFinal;
    
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

    public void setSqftUserEntered(Double sqftUserEntered) {
        this.sqftUserEntered = sqftUserEntered;
    }

    public Double getSqftUserEntered() {
        return sqftUserEntered;
    }

    public void setSqftFinal(Double sqftFinal) {
        this.sqftFinal = sqftFinal;
    }

    public Double getSqftFinal() {
        return sqftFinal;
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
}
