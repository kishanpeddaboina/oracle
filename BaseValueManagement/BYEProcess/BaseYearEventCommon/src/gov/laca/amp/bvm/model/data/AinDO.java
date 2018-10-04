package gov.laca.amp.bvm.model.data;


import java.math.BigInteger;

public class AinDO {
    String[] attributes =
    { "ain", "aoid", "processStatus", "ecid", "dbLockStatus", "districtId",
      "districtName", "region", "regionName", "clusterRouteId", "clusterRoute",
      "clusterRouteName" };

    protected String ain;
    protected BigInteger aoid;
    protected String processStatus;
    protected String ecid;
    protected String dbLockStatus;
    protected String lockedByUserId;
    protected BigInteger districtId;
    protected String districtName;
    protected String region;
    protected String regionName;
    protected BigInteger clusterRouteId;
    protected String clusterRoute;
    protected String clusterRouteName;
    protected Boolean reviewRequired;
    protected String ainType;
    protected Boolean isDirty = Boolean.FALSE;

    /**
     * Gets the legend of the ain property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getAin() {
        return ain;
    }

    /**
     * Sets the legend of the ain property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setAin(String value) {
        this.ain = value;
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
     * Gets the legend of the processStatus property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getProcessStatus() {
        return processStatus;
    }

    /**
     * Sets the legend of the processStatus property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setProcessStatus(String value) {
        this.processStatus = value;
    }

    /**
     * Gets the legend of the ecid property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getEcid() {
        return ecid;
    }

    /**
     * Sets the legend of the ecid property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setEcid(String value) {
        this.ecid = value;
    }

    /**
     * Gets the legend of the dbLockStatus property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getDbLockStatus() {
        return dbLockStatus;
    }

    /**
     * Sets the legend of the dbLockStatus property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setDbLockStatus(String value) {
        this.dbLockStatus = value;
    }

    /**
     * Gets the legend of the lockedByUserId property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getLockedByUserId() {
        return lockedByUserId;
    }

    /**
     * Sets the legend of the lockedByUserId property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setLockedByUserId(String value) {
        this.lockedByUserId = value;
    }

    /**
     * Gets the legend of the districtId property.
     *
     * @return
     * possible object is
     * {@link BigInteger}
     *
     */
    public BigInteger getDistrictId() {
        return districtId;
    }

    /**
     * Sets the legend of the districtId property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger}
     *
     */
    public void setDistrictId(BigInteger value) {
        this.districtId = value;
    }

    /**
     * Gets the legend of the districtName property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Sets the legend of the districtName property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setDistrictName(String value) {
        this.districtName = value;
    }

    /**
     * Gets the legend of the region property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the legend of the region property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the legend of the regionName property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Sets the legend of the regionName property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setRegionName(String value) {
        this.regionName = value;
    }

    /**
     * Gets the legend of the clusterRouteId property.
     *
     * @return
     * possible object is
     * {@link BigInteger}
     *
     */
    public BigInteger getClusterRouteId() {
        return clusterRouteId;
    }

    /**
     * Sets the legend of the clusterRouteId property.
     *
     * @param value
     * allowed object is
     * {@link BigInteger}
     *
     */
    public void setClusterRouteId(BigInteger value) {
        this.clusterRouteId = value;
    }

    /**
     * Gets the legend of the clusterRoute property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getClusterRoute() {
        return clusterRoute;
    }

    /**
     * Sets the legend of the clusterRoute property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setClusterRoute(String value) {
        this.clusterRoute = value;
    }

    /**
     * Gets the legend of the clusterRouteName property.
     *
     * @return
     * possible object is
     * {@link String}
     *
     */
    public String getClusterRouteName() {
        return clusterRouteName;
    }

    /**
     * Sets the legend of the clusterRouteName property.
     *
     * @param value
     * allowed object is
     * {@link String}
     *
     */
    public void setClusterRouteName(String value) {
        this.clusterRouteName = value;
    }

    public void setReviewRequired(Boolean reviewRequired) {
        this.reviewRequired = reviewRequired;
    }

    public Boolean getReviewRequired() {
        return reviewRequired;
    }

    public void setAinType(String ainType) {
        this.ainType = ainType;
    }

    public String getAinType() {
        return ainType;
    }

    public void setIsDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Boolean getIsDirty() {
        return isDirty;
    }
}
