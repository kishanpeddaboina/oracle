package gov.laca.amp.pdcr.common.model.dc;

import gov.laca.amp.ent.util.AmpStringUtils;
import gov.laca.amp.ent.util.JSONUtils;

import gov.laca.amp.fwk.extn.dc.AmpPojoDC;
import gov.laca.amp.pdcr.common.model.AmpChangeRequestException;
import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;

import gov.laca.amp.pdcr.common.model.facade.ChangeRequestFacade;

import gov.laca.amp.pdcr.common.model.data.RealPropertyInfoDO;
import gov.laca.amp.pdcr.common.model.data.RequestorInfoDO;
import gov.laca.amp.pdcr.common.model.data.LockCheckDO;
import gov.laca.amp.pdcr.common.model.data.SubPartDO;
import gov.laca.amp.pdcr.common.model.data.CompositeDO;
import gov.laca.amp.pdcr.common.model.data.LandDO;

import java.math.BigInteger;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.WebServiceException;

import javax.xml.ws.soap.SOAPFaultException;

import oracle.adf.share.logging.ADFLogger;

import oracle.javatools.resourcebundle.BundleFactory;


public class ChangeRequestDC extends AmpPojoDC {
    public static final ADFLogger logger = ADFLogger.createADFLogger(ChangeRequestDC.class);

    public static final ResourceBundle pdcrBundle = BundleFactory.getBundle(ChangeRequestConstants.BUNDLE_MODEL_LOCATION);

    // initialization attributes
    private String ain;
    private String ainDisplay;
    private BigInteger aoid;
    private String sourceType;
    private String dataSource;
    private String actionType;
    private String useTypeIcon;

    // Conditional variables for control flow
    private Boolean isPDCRInitiated;
    private Boolean isRetrieveSuccess;
    private Boolean isInitiateSuccess;
    private Boolean isPDCREmpty;
    private Boolean isValidDistrict;
    private Boolean isNoLand;
    private Boolean isShowDisaplyMessage;

    // Requester properties
    private RequestorInfoDO requestorInfoDO;

    // Real property objects
    private RealPropertyInfoDO originalRPI;
    private RealPropertyInfoDO userEnteredRPI;
    private RealPropertyInfoDO finalRPI;

    // individual objects used in Create, Update, and Delete transactions.
    private CompositeDO compositeDO = null;
    private CompositeDO tempCompositeDO = null;
    private LandDO landDO = null;
    private LandDO tempLandDO = null;
    private List<SubPartDO> subPartListDO;
    private SubPartDO tempSubPartDO = null;

    // sequesnces used during the addition items
    private Integer originalSubPartCount;
    private Integer subPartCount;

    // the transaction type being used
    private String transactionType;

    // the messaging  for PDCR
    private String transactionMessage;


    public ChangeRequestDC() {
        super();
    }

    private String generateUniquieID(int length) {
        return AmpStringUtils.randomString(length);
    }

    public Boolean CheckLock(String ain) throws AmpChangeRequestException {
        // execute the check lock to determin if we can initiate a PDCR
        logger.warning("CheckLock executing for: " + ain);

        // Set the ain into pageflowscope used during the execution of the taskflow
        String ainClean;
        if (null == ain)
            ainClean = ain;
        else
            ainClean = ain.replaceAll("\\-", "");
        setAin(ainClean);

        // initialize the return value
        setIsPDCRInitiated(Boolean.FALSE);

        if (null != getAin()) {
            // Call the check lock service to see if there is a lock on the AIN
            LockCheckDO lockCheckDO = ChangeRequestFacade.CheckLock(getAin(), null, null, null, ChangeRequestConstants.PROCESS_TYPE_CHECKLOCK);
            if (lockCheckDO.getLockStatus().equalsIgnoreCase(ChangeRequestConstants.AIN_LOCK_STATUS_LOCKED)) {
                logger.warning("The lock status for AIN " + ain + " was " + lockCheckDO.getLockStatus() + " for PDCR.");
                // Format the ainDisplay if the AIN is locked
                if (null != getAin()) {
                    if (!getAin().isEmpty()) {
                        setAinDisplay(getAin().substring(0, 4) + "-" + getAin().substring(4, 7) + "-" + getAin().substring(7, 10));
                    }
                }
                setIsPDCRInitiated(Boolean.TRUE);
            } else {
                logger.warning("The lock status for AIN " + ain + " is not locked, or unknown.");
                setIsPDCRInitiated(Boolean.FALSE);
            }
        }

        // The return value is set when this method is called from the UI when there is no AIN passed into the taskflow
        return getIsPDCRInitiated();
    }

    public void RetrieveRealPropertyInfo(String ain, String dataSource, String sourceType, Boolean isExternalUser, Boolean isClericalUser, Boolean isCheckLock,
                                         String userGroups) throws AmpChangeRequestException {
        // Retrieve the real property information based on the ain, and the datasource
        setIsRetrieveSuccess(Boolean.FALSE);
        setIsPDCREmpty(Boolean.FALSE);
        setIsValidDistrict(Boolean.TRUE);
        setIsPDCRInitiated(Boolean.FALSE);
        setIsNoLand(Boolean.FALSE);

        // clear the trasaction message
        ClearTransactionMessage();

        // Set the ain into pageflowscope used during the execution of the taskflow
        String ainClean;
        if (null == ain)
            ainClean = getAin();
        else
            ainClean = ain.replaceAll("\\-", "");
        setAin(ainClean);

        // Set the initial value for the ain in the case the display cannot be transformed
        setAinDisplay(getAin());

        // Set the sourceType and dataSource. This is passecd in via the task flow input value,
        // and from the databinding when this method is called from the UI.
        // This value determines if the source is CreatePDCR or EditPDCR
        setSourceType(sourceType);
        setDataSource(dataSource);

        // If the value is not passed in then default sourceType is 'CreatePDCR'
        if (null == getSourceType()) {
            setSourceType(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE);
        }

        logger.warning("The data being retrieved is based on the '" + getDataSource() + "' data source, and used for the '" + getSourceType() +
                       "' operation.");
        logger.warning("The logged user is: " + (null != isExternalUser ? (isExternalUser ? "External" : "Internal") : "Not Supplied"));

        // Initialize the data controls used in the UI for Create and Edit
        this.originalRPI = new RealPropertyInfoDO();
        this.userEnteredRPI = new RealPropertyInfoDO();
        this.finalRPI = new RealPropertyInfoDO();

        // initialize the requestor object
        if (getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE)) {
            this.requestorInfoDO = new RequestorInfoDO();
            this.requestorInfoDO.setPreferredContact(ChangeRequestConstants.PREFERED_METHOD_OF_CONTACT_EMAIL);
            if (null != isExternalUser) {
                if (!isExternalUser) {
                    this.requestorInfoDO.setDataChangeSource(ChangeRequestConstants.PUBLIC_SERVICE_RELATED);
                }
            }
            // Set the Data Source to 'Public Service Related' when the user is in the clerical role
            if (null != isClericalUser) {
                if (isClericalUser) {
                    this.requestorInfoDO.setDataChangeSource(ChangeRequestConstants.PUBLIC_SERVICE_RELATED);
                }
            }
        } else {
            this.requestorInfoDO = null;
        }

        // create a new object for the UI when there is no ain.
        if (null == getAin() && !getDataSource().equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
            // Create empty AIN objects for external users, and internal users creating PDCR on behalf
            CreateEmptyRealPropertyInfo();
        } else {
            // Retrieve the real propery information, the dataSource will tell the facade where to pull the information
            try {
                if (getDataSource().equalsIgnoreCase(ChangeRequestConstants.SOURCE_SOA)) {

                    // set the prefered method to email for default
                    setActionType(ChangeRequestConstants.ACTION_TYPE_UPDATE);
                    this.originalRPI = ChangeRequestFacade.RetrieveRealPropertyInfo(getAin(), getDataSource(), null);

                    if (null != this.originalRPI) {
                        logger.warning("Sucessfully retrieved the real property information for AIN: " + getAin() + " from " + getDataSource());
                        // Set the variables for AIN valid district, AIN lock, and no Land
                        // Check to see if we retrieved an ain with no land
                        if (null != this.originalRPI.getLand()) {
                            if (null == this.originalRPI.getLand().getDbUniqueId()) {
                                setIsNoLand(Boolean.TRUE);
                            }
                        } else {
                            setIsNoLand(Boolean.TRUE);
                        }

                        // Set the conditions of the AIN
                        // look at the user roles to see if the proper access is set to work on this AIN
                        if (null != this.originalRPI.getDistrict())
                            setIsValidDistrict(getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE) ? Boolean.TRUE :
                                               userGroups.contains(this.originalRPI.getDistrict()));
                        if (getIsValidDistrict()) {
                            // See if the AIN is locked
                            if (isCheckLock)
                                CheckLock(getAin());
                        }

                        // set the value to let the view know there was some invalid information
                        setIsShowDisaplyMessage(!getIsValidDistrict() || getIsPDCRInitiated());

                        // Convert the original real property information to the user entered real property informaiton
                        // the user entered object is what the UI will interact with on edits.
                        ConvertOriginaltoUserEnteredRPI();
                    } else {
                        // If there is no Real Property retrieved then create an empty RPI object
                        CreateEmptyRealPropertyInfo();
                    }
                } else if (getDataSource().equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {

                    if (null != this.originalRPI) {
                        this.originalRPI =
                                ChangeRequestFacade.RetrieveRealPropertyInfo(getAin(), getDataSource(), ChangeRequestConstants.REALPROPERTY_TYPE_ORIGINAL);
                        this.userEnteredRPI =
                                ChangeRequestFacade.RetrieveRealPropertyInfo(getAin(), getDataSource(), ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED);
                        this.finalRPI =
                                ChangeRequestFacade.RetrieveRealPropertyInfo(getAin(), getDataSource(), ChangeRequestConstants.REALPROPERTY_TYPE_FINAL);
                        // Set the variables for AIN valid district, AIN lock, and no Land
                        // Check to see if we retrieved an ain with no land
                        if (null != this.originalRPI.getLand()) {
                            if (null == this.originalRPI.getLand().getDbUniqueId()) {
                                setIsNoLand(Boolean.TRUE);
                            }
                        } else {
                            setIsNoLand(Boolean.TRUE);
                        }
                        // Concatanate the information from all three real property collections
                        AggregateRealPropertyInfo();
                    }
                }
                // Format the ain
                if (null != this.originalRPI.getAin()) {
                    if (!this.originalRPI.getAin().isEmpty()) {
                        setAinDisplay(this.originalRPI.getAin().substring(0, 4) + "-" + this.originalRPI.getAin().substring(4, 7) + "-" +
                                      this.originalRPI.getAin().substring(7, 10));
                    }
                }
                logger.warning("Displayed AIN: " + getAinDisplay());
                // Convert the property Address from a single line to a double line
                // This is aworkaround in phase 1. In phase 2 it would be suggested that the address parts come
                // in seperate elements
                ConvertPropertyAddress();

                // set the pageflowscope variable to use in the router activity for the Create and Edit taskflows
                setIsRetrieveSuccess(Boolean.TRUE);
            } catch (AmpChangeRequestException ampe) {
                setAinDisplay(getAin());
                CreateEmptyRealPropertyInfo();
                logger.severe("AmpChangeRequestException - Error in RetrieveRealPropertyInfo Operation:", ampe);
                // Check lock is only set on the task flow, and then should only throw the error from the DC
                if (isCheckLock)
                    throw new AmpChangeRequestException(ampe.getMessage(), ampe.getErrCode());
            } catch (SOAPFaultException sfe) {
                setAinDisplay(getAin());
                CreateEmptyRealPropertyInfo();
                logger.severe("SOAPFaultException - Error in RetrieveRealPropertyInfo Operation:", sfe);
                throw new AmpChangeRequestException(sfe.getFault().getFaultString(), sfe.getFault().getFaultCode());
            } catch (WebServiceException wse) {
                setAinDisplay(getAin());
                CreateEmptyRealPropertyInfo();
                logger.severe("WebServiceException - Error in RetrieveRealPropertyInfo Operation:", wse);
                throw new AmpChangeRequestException(wse.getMessage(), "WebServiceException");
            }
        }
        logger.warning("Original Real Property Info being used: " + JSONUtils.toJson(this.originalRPI));
        logger.warning("User Entered Real Property Info being used: " + JSONUtils.toJson(this.userEnteredRPI));
        logger.warning("Final Real Property Info being used: " + JSONUtils.toJson(this.finalRPI));

        logger.warning("SubPart list being used: " + JSONUtils.toJson(this.subPartListDO));
        logger.warning("TempSubPartInfo created: " + JSONUtils.toJson(this.tempSubPartDO));
        logger.warning("Composite being used: " + JSONUtils.toJson(this.compositeDO));
        logger.warning("Land data being used: " + JSONUtils.toJson(this.landDO));
    }

    private void CreateEmptyRealPropertyInfo() {
        // Set the counters during each conversion since this only happend upon retrieves
        setSubPartCount(0);
        setOriginalSubPartCount(0);

        // set the ain and aoid
        setAin(null);
        setAoid(new BigInteger("1"));

        setIsPDCREmpty(Boolean.TRUE);
    }

    public void PopulateRequestorInfo(String requestorName, String email, String phone) {
        this.requestorInfoDO.setRequestorName(requestorName);
        this.requestorInfoDO.setEmail(email);
        this.requestorInfoDO.setPhone(phone);
        if (null != this.requestorInfoDO.getEmail()) {
            this.requestorInfoDO.setPreferredContact(ChangeRequestConstants.PREFERED_METHOD_OF_CONTACT_EMAIL);
        } else if (null != this.requestorInfoDO.getPhone()) {
            this.requestorInfoDO.setPreferredContact(ChangeRequestConstants.PREFERED_METHOD_OF_CONTACT_PHONE);
        }
    }

    private String getUseTypeIconName() {
        String returnValue = null;
        if (null != this.originalRPI.getLand().getUseType()) {
            String useType = this.originalRPI.getLand().getUseType().toLowerCase();
            if (getDataSource().equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
                returnValue = ChangeRequestConstants.BPM_ICONS_RELATIVE_PATH;
            } else {
                returnValue = ChangeRequestConstants.CONTENT_SERVER_ICONS_RELATIVE_PATH;
            }
            returnValue = returnValue.concat("amp-icon_" + useType.replace(' ', '-') + ".png");
        }
        return returnValue;
    }

    private void ConvertPropertyAddress() {
        // Converts the address elemnt into 2 parts. On 10/3/2016 SOA is seperating
        // the lines by a '||'. They are also formatting the City, State, and ZipCode
        String returnValue = this.originalRPI.getPropertyAddress();
        if (null != returnValue) {
            if (!returnValue.isEmpty()) {
                returnValue = returnValue.replace("||", "<br/>");
                this.originalRPI.setPropertyAddress(returnValue);
            }
        }
        logger.warning("New formatted address: " + this.originalRPI.getPropertyAddress());
    }

    private void ConvertOriginaltoUserEnteredRPI() {
        // Set the counters during each conversion since this only happend upon retrieves
        setSubPartCount(0);
        setOriginalSubPartCount(0);
        Integer count = 0;

        // set the ain and aoid
        setAin(this.originalRPI.getAin());
        setAoid(this.originalRPI.getAoid());

        // get the use type iceon for the AIN summary
        setUseTypeIcon(getUseTypeIconName());

        // Create the user entered object, and the temp object for subparts, composite, and land
        this.userEnteredRPI.setAin(this.originalRPI.getAin());
        this.userEnteredRPI.setAoid(this.originalRPI.getAoid());
        this.userEnteredRPI.setCluster(this.originalRPI.getCluster());
        this.userEnteredRPI.setDistrict(this.originalRPI.getDistrict());
        this.userEnteredRPI.setRegion(this.originalRPI.getRegion());
        this.userEnteredRPI.setPropertyAddress(this.originalRPI.getPropertyAddress());

        // Create the subpart list used for inititation
        this.subPartListDO = new ArrayList<SubPartDO>();
        if (null != this.originalRPI.getSubPartList()) {
            if (!this.originalRPI.getSubPartList().getSubPart().isEmpty()) {
                for (RealPropertyInfoDO.SubPartList.SubPart sp : this.originalRPI.getSubPartList().getSubPart()) {
                    SubPartDO spDO = new SubPartDO();
                    spDO.setDbUniqueId(sp.getDbUniqueId());
                    spDO.setDbUniqueIdUserEntered(spDO.getDbUniqueId());
                    // When the user is in CreatePDCR then we need to set the first Subpart in the list to be displayed
                    if (count == 0 && getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE)) {
                        spDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_UPDATE);
                    }
                    spDO.setAin(sp.getAin());
                    spDO.setAoid(sp.getAoid());
                    spDO.setTransactionType(sp.getTransactionType());
                    spDO.setSubPartNumberDisplay(sp.getSubPartNumber());
                    spDO.setSubPartNumber(sp.getSubPartNumber());
                    spDO.setAddedDepreciationPercent(sp.getAddedDepreciationPercent());
                    spDO.setAddedDepreciationTypeKey(sp.getAddedDepreciationTypeKey());
                    spDO.setAddedDepreciationTypeLegend(sp.getAddedDepreciationTypeLegend());
                    spDO.setAddedDepreciationTypeRef(sp.getAddedDepreciationTypeRef());
                    spDO.setBathroomCount(sp.getBathroomCount());
                    spDO.setBedroomCount(sp.getBedroomCount());
                    spDO.setCostFactor(sp.getCostFactor());
                    spDO.setDepreciationTable(sp.getDepreciationTable());
                    spDO.setDesignTypeKey(sp.getDesignTypeKey());
                    spDO.setDesignTypeLegend(sp.getDesignTypeLegend());
                    spDO.setDesignTypeRef(sp.getDesignTypeRef());
                    spDO.setEffectiveYearBuilt(sp.getEffectiveYearBuilt());
                    spDO.setImprovementNumber(sp.getImprovementNumber());
                    spDO.setImprovementSizeSqft(sp.getImprovementSizeSqft());
                    spDO.setNumberOfUnits(sp.getNumberOfUnits());
                    spDO.setPercentGood(sp.getPercentGood());
                    spDO.setQualityClass(sp.getQualityClass());
                    spDO.setRcnLessDepreciation(sp.getRcnLessDepreciation());
                    spDO.setRcnMain(sp.getRcnMain());
                    spDO.setRcnOtherTotal(sp.getRcnOtherTotal());
                    spDO.setRcnYearChange(sp.getRcnYearChange());
                    spDO.setYearBuilt(sp.getYearBuilt());
                    this.subPartCount++;
                    this.originalSubPartCount++;
                    if (count == 0) {
                        this.tempSubPartDO = spDO;
                        count++;
                    }
                    this.subPartListDO.add(spDO);
                }
            } else {
                if (getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE)) {
                    this.tempSubPartDO = new SubPartDO();
                    this.tempSubPartDO.setDbUniqueId(ChangeRequestConstants.CREATE_SUBPART_U_PREFIX + generateUniquieID(8));
                    this.tempSubPartDO.setDbUniqueIdUserEntered(this.tempSubPartDO.getDbUniqueId());
                    this.tempSubPartDO.setAin(this.originalRPI.getAin());
                    this.tempSubPartDO.setAoid(this.originalRPI.getAoid());
                    this.tempSubPartDO.setTransactionType(ChangeRequestConstants.SUBPART_CREATE);
                    this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_CREATE);
                    this.tempSubPartDO.setSubPartNumber(ChangeRequestConstants.SUBPART_NUMBER_SEED);
                    this.tempSubPartDO.setSubPartNumberUserEntered(ChangeRequestConstants.SUBPART_NUMBER_SEED);
                    this.subPartListDO.add(this.tempSubPartDO);
                } else {
                    // This case will hit if there was no subpart list, and the source is edit
                    //this.tempSubPartDO = new SubPartDO();
                    //this.subPartListDO.add(this.tempSubPartDO);
                }
            }
        } else if (getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_CREATE)) {
            this.tempSubPartDO = new SubPartDO();
            this.tempSubPartDO.setDbUniqueId(ChangeRequestConstants.CREATE_SUBPART_U_PREFIX + generateUniquieID(8));
            this.tempSubPartDO.setDbUniqueIdUserEntered(this.tempSubPartDO.getDbUniqueId());
            this.tempSubPartDO.setAin(this.originalRPI.getAin());
            this.tempSubPartDO.setAoid(this.originalRPI.getAoid());
            this.tempSubPartDO.setTransactionType(ChangeRequestConstants.SUBPART_CREATE);
            this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_CREATE);
            this.tempSubPartDO.setSubPartNumber(ChangeRequestConstants.SUBPART_NUMBER_SEED);
            this.tempSubPartDO.setSubPartNumberUserEntered(ChangeRequestConstants.SUBPART_NUMBER_SEED);
            this.subPartListDO.add(this.tempSubPartDO);
        } else {
            // This case will hit if there was no subpart list, and the source is edit
            //this.tempSubPartDO = new SubPartDO();
            //this.subPartListDO.add(this.tempSubPartDO);
        }

        // Create the composite object used for inititation
        if (null != this.originalRPI.getComposite()) {
            this.compositeDO = new CompositeDO();
            this.compositeDO.setDbUniqueId(this.originalRPI.getComposite().getDbUniqueId());
            this.compositeDO.setDbUniqueIdUserEntered(this.compositeDO.getDbUniqueId());
            this.compositeDO.setAin(this.originalRPI.getComposite().getAin());
            this.compositeDO.setAoid(this.originalRPI.getComposite().getAoid());
            this.compositeDO.setTransactionType(this.originalRPI.getComposite().getTransactionType());
            this.compositeDO.setSqft(this.originalRPI.getComposite().getSqft());
        }

        // Create the land object used for inititation
        if (null != this.originalRPI.getLand()) {
            this.landDO = new LandDO();
            this.landDO.setDbUniqueId(this.originalRPI.getLand().getDbUniqueId());
            this.landDO.setDbUniqueIdUserEntered(this.landDO.getDbUniqueId());
            this.landDO.setAin(this.originalRPI.getLand().getAin());
            this.landDO.setAoid(this.originalRPI.getLand().getAoid());
            this.landDO.setTransactionType(this.originalRPI.getLand().getTransactionType());
            this.landDO.setCodeSplit(this.originalRPI.getLand().getCodeSplit());
            this.landDO.setCorner(this.originalRPI.getLand().getCorner());
            this.landDO.setDepth(this.originalRPI.getLand().getDepth());
            this.landDO.setFlightPath(this.originalRPI.getLand().getFlightPath());
            this.landDO.setFreewayProximity(this.originalRPI.getLand().getFreewayProximity());
            this.landDO.setGolfFront(this.originalRPI.getLand().getGolfFront());
            this.landDO.setHorses(this.originalRPI.getLand().getHorses());
            this.landDO.setSewer(this.originalRPI.getLand().getSewer());
            this.landDO.setSizeAcreAge(this.originalRPI.getLand().getSizeAcreAge());
            this.landDO.setSizeSqft(this.originalRPI.getLand().getSizeSqft());
            this.landDO.setSizeUsableSqft(this.originalRPI.getLand().getSizeUsableSqft());
            this.landDO.setTraffic(this.originalRPI.getLand().getTraffic());
            this.landDO.setUseCode(this.originalRPI.getLand().getUseCode());
            this.landDO.setUseType(this.originalRPI.getLand().getUseType());
            //if (null != this.originalRPI.getLand().getViewCodeKey())
            this.landDO.setViewCodeDisplay(this.originalRPI.getLand().getViewCodeLegend());
            this.landDO.setViewCodeKey(this.originalRPI.getLand().getViewCodeKey());
            this.landDO.setViewCodeLegend(this.originalRPI.getLand().getViewCodeLegend());
            this.landDO.setViewCodeRef(this.originalRPI.getLand().getViewCodeRef());
            this.landDO.setWidth(this.originalRPI.getLand().getWidth());
        }
    }

    private void AggregateRealPropertyInfo() {
        // Aggregate the SubPart, Land and Composite information from Original User Entered and final
        // This method is being used from the BPM UI's

        // Set the counters during each conversion since this only happend upon retrieves
        setSubPartCount(0);
        setOriginalSubPartCount(0);

        // set the ain and aoid
        setAin(this.originalRPI.getAin());
        setAoid(this.originalRPI.getAoid());

        // get the use type iceon for the AIN summary
        setUseTypeIcon(getUseTypeIconName());

        // Create the subpart list used for inititation
        this.subPartListDO = new ArrayList<SubPartDO>();

        SubPartDO spDO = null;
        // Set the original subpart information
        if (null != this.originalRPI.getSubPartList()) {
            if (!this.originalRPI.getSubPartList().getSubPart().isEmpty()) {
                logger.warning("Original Subpart List: " + JSONUtils.toJson(this.originalRPI.getSubPartList().getSubPart()));
                for (Iterator<RealPropertyInfoDO.SubPartList.SubPart> iteratorsp = this.originalRPI.getSubPartList().getSubPart().iterator();
                     iteratorsp.hasNext(); ) {
                    RealPropertyInfoDO.SubPartList.SubPart sp1 = iteratorsp.next();
                    spDO = new SubPartDO();
                    spDO.setDbUniqueId(sp1.getDbUniqueId());
                    spDO.setAin(sp1.getAin());
                    spDO.setAoid(sp1.getAoid());
                    spDO.setTransactionType(sp1.getTransactionType());
                    spDO.setSubPartNumber(sp1.getSubPartNumber());
                    spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                    spDO.setAddedDepreciationPercent(sp1.getAddedDepreciationPercent());
                    spDO.setAddedDepreciationTypeKey(sp1.getAddedDepreciationTypeKey());
                    spDO.setAddedDepreciationTypeLegend(sp1.getAddedDepreciationTypeLegend());
                    spDO.setAddedDepreciationTypeRef(sp1.getAddedDepreciationTypeRef());
                    spDO.setBathroomCount(sp1.getBathroomCount());
                    spDO.setBedroomCount(sp1.getBedroomCount());
                    spDO.setCostFactor(sp1.getCostFactor());
                    spDO.setDepreciationTable(sp1.getDepreciationTable());
                    spDO.setDesignTypeKey(sp1.getDesignTypeKey());
                    spDO.setDesignTypeLegend(sp1.getDesignTypeLegend());
                    spDO.setDesignTypeRef(sp1.getDesignTypeRef());
                    spDO.setEffectiveYearBuilt(sp1.getEffectiveYearBuilt());
                    spDO.setImprovementNumber(sp1.getImprovementNumber());
                    spDO.setImprovementSizeSqft(sp1.getImprovementSizeSqft());
                    spDO.setNumberOfUnits(sp1.getNumberOfUnits());
                    spDO.setPercentGood(sp1.getPercentGood());
                    spDO.setQualityClass(sp1.getQualityClass());
                    spDO.setRcnLessDepreciation(sp1.getRcnLessDepreciation());
                    spDO.setRcnMain(sp1.getRcnMain());
                    spDO.setRcnOtherTotal(sp1.getRcnOtherTotal());
                    spDO.setRcnYearChange(sp1.getRcnYearChange());
                    spDO.setYearBuilt(sp1.getYearBuilt());
                    this.subPartCount++;
                    this.originalSubPartCount++;
                    this.subPartListDO.add(spDO);
                }
            }
        }

        if (null != this.userEnteredRPI.getSubPartList()) {
            if (!this.userEnteredRPI.getSubPartList().getSubPart().isEmpty()) {
                logger.warning("User Entered Subpart List: " + JSONUtils.toJson(this.userEnteredRPI.getSubPartList().getSubPart()));
                if (!this.subPartListDO.isEmpty()) {
                    for (Iterator<RealPropertyInfoDO.SubPartList.SubPart> iteratorsp1 = this.userEnteredRPI.getSubPartList().getSubPart().iterator();
                         iteratorsp1.hasNext(); ) {
                        RealPropertyInfoDO.SubPartList.SubPart sp1 = iteratorsp1.next();
                        Boolean isAdded = Boolean.FALSE;
                        for (Iterator<SubPartDO> iterator = this.subPartListDO.iterator(); iterator.hasNext(); ) {
                            SubPartDO sp = iterator.next();
                            if (sp.getDbUniqueId().equalsIgnoreCase(sp1.getDbUniqueId())) {
                                sp.setDbUniqueIdUserEntered(sp1.getDbUniqueId());
                                sp.setTransactionTypeUserEntered(sp1.getTransactionType());
                                sp.setSubPartNumberUserEntered(sp1.getSubPartNumber());
                                sp.setAddedDepreciationPercentUserEntered(sp1.getAddedDepreciationPercent());
                                sp.setAddedDepreciationTypeKeyUserEntered(sp1.getAddedDepreciationTypeKey());
                                sp.setAddedDepreciationTypeLegendUserEntered(sp1.getAddedDepreciationTypeLegend());
                                sp.setAddedDepreciationTypeRefUserEntered(sp1.getAddedDepreciationTypeRef());
                                sp.setBathroomCountUserEntered(sp1.getBathroomCount());
                                sp.setBedroomCountUserEntered(sp1.getBedroomCount());
                                sp.setCostFactorUserEntered(sp1.getCostFactor());
                                sp.setDepreciationTableUserEntered(sp1.getDepreciationTable());
                                sp.setDesignTypeKeyUserEntered(sp1.getDesignTypeKey());
                                sp.setDesignTypeLegendUserEntered(sp1.getDesignTypeLegend());
                                sp.setDesignTypeRefUserEntered(sp1.getDesignTypeRef());
                                sp.setEffectiveYearBuiltUserEntered(sp1.getEffectiveYearBuilt());
                                sp.setImprovementNumberUserEntered(sp1.getImprovementNumber());
                                sp.setImprovementSizeSqftUserEntered(sp1.getImprovementSizeSqft());
                                sp.setNumberOfUnitsUserEntered(sp1.getNumberOfUnits());
                                sp.setPercentGoodUserEntered(sp1.getPercentGood());
                                sp.setQualityClassUserEntered(sp1.getQualityClass());
                                sp.setRcnLessDepreciationUserEntered(sp1.getRcnLessDepreciation());
                                sp.setRcnMainUserEntered(sp1.getRcnMain());
                                sp.setRcnOtherTotalUserEntered(sp1.getRcnOtherTotal());
                                sp.setRcnYearChangeUserEntered(sp1.getRcnYearChange());
                                sp.setYearBuiltUserEntered(sp1.getYearBuilt());
                                if (null != sp1.getTransactionType()) {
                                    if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                        if (this.subPartCount > 0) {
                                            this.subPartCount--;
                                        }
                                        sp.setSubPartNumberDisplay("");
                                    } else {
                                        if (null != sp1.getSubPartNumber()) {
                                            sp.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                        }
                                    }
                                } else {
                                    if (null != sp1.getSubPartNumber()) {
                                        sp.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                    }
                                }
                                isAdded = Boolean.TRUE;
                            }
                        }
                        if (!isAdded) {
                            spDO = new SubPartDO();
                            spDO.setAin(sp1.getAin());
                            spDO.setAoid(sp1.getAoid());
                            spDO.setDbUniqueId(sp1.getDbUniqueId());
                            spDO.setDbUniqueIdUserEntered(sp1.getDbUniqueId());
                            spDO.setTransactionTypeUserEntered(sp1.getTransactionType());
                            spDO.setSubPartNumberUserEntered(sp1.getSubPartNumber());
                            spDO.setAddedDepreciationPercentUserEntered(sp1.getAddedDepreciationPercent());
                            spDO.setAddedDepreciationTypeKeyUserEntered(sp1.getAddedDepreciationTypeKey());
                            spDO.setAddedDepreciationTypeLegendUserEntered(sp1.getAddedDepreciationTypeLegend());
                            spDO.setAddedDepreciationTypeRefUserEntered(sp1.getAddedDepreciationTypeRef());
                            spDO.setBathroomCountUserEntered(sp1.getBathroomCount());
                            spDO.setBedroomCountUserEntered(sp1.getBedroomCount());
                            spDO.setCostFactorUserEntered(sp1.getCostFactor());
                            spDO.setDepreciationTableUserEntered(sp1.getDepreciationTable());
                            spDO.setDesignTypeKeyUserEntered(sp1.getDesignTypeKey());
                            spDO.setDesignTypeLegendUserEntered(sp1.getDesignTypeLegend());
                            spDO.setDesignTypeRefUserEntered(sp1.getDesignTypeRef());
                            spDO.setEffectiveYearBuiltUserEntered(sp1.getEffectiveYearBuilt());
                            spDO.setImprovementNumberUserEntered(sp1.getImprovementNumber());
                            spDO.setImprovementSizeSqftUserEntered(sp1.getImprovementSizeSqft());
                            spDO.setNumberOfUnitsUserEntered(sp1.getNumberOfUnits());
                            spDO.setPercentGoodUserEntered(sp1.getPercentGood());
                            spDO.setQualityClassUserEntered(sp1.getQualityClass());
                            spDO.setRcnLessDepreciationUserEntered(sp1.getRcnLessDepreciation());
                            spDO.setRcnMainUserEntered(sp1.getRcnMain());
                            spDO.setRcnOtherTotalUserEntered(sp1.getRcnOtherTotal());
                            spDO.setRcnYearChangeUserEntered(sp1.getRcnYearChange());
                            spDO.setYearBuiltUserEntered(sp1.getYearBuilt());
                            if (null != sp1.getTransactionType()) {
                                if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                    if (this.subPartCount > 0) {
                                        this.subPartCount--;
                                    }
                                    spDO.setSubPartNumberDisplay("");
                                } else {
                                    if (null != sp1.getSubPartNumber()) {
                                        spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                    }
                                    this.subPartCount++;
                                }
                            } else {
                                if (null != sp1.getSubPartNumber()) {
                                    spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                }
                            }
                            this.subPartListDO.add(spDO);
                        }
                    }
                } else {
                    for (RealPropertyInfoDO.SubPartList.SubPart sp1 : this.userEnteredRPI.getSubPartList().getSubPart()) {
                        spDO = new SubPartDO();
                        spDO.setAin(sp1.getAin());
                        spDO.setAoid(sp1.getAoid());
                        spDO.setDbUniqueId(sp1.getDbUniqueId());
                        spDO.setDbUniqueIdUserEntered(sp1.getDbUniqueId());
                        spDO.setTransactionTypeUserEntered(sp1.getTransactionType());
                        spDO.setSubPartNumberUserEntered(sp1.getSubPartNumber());
                        spDO.setAddedDepreciationPercentUserEntered(sp1.getAddedDepreciationPercent());
                        spDO.setAddedDepreciationTypeKeyUserEntered(sp1.getAddedDepreciationTypeKey());
                        spDO.setAddedDepreciationTypeLegendUserEntered(sp1.getAddedDepreciationTypeLegend());
                        spDO.setAddedDepreciationTypeRefUserEntered(sp1.getAddedDepreciationTypeRef());
                        spDO.setBathroomCountUserEntered(sp1.getBathroomCount());
                        spDO.setBedroomCountUserEntered(sp1.getBedroomCount());
                        spDO.setCostFactorUserEntered(sp1.getCostFactor());
                        spDO.setDepreciationTableUserEntered(sp1.getDepreciationTable());
                        spDO.setDesignTypeKeyUserEntered(sp1.getDesignTypeKey());
                        spDO.setDesignTypeLegendUserEntered(sp1.getDesignTypeLegend());
                        spDO.setDesignTypeRefUserEntered(sp1.getDesignTypeRef());
                        spDO.setEffectiveYearBuiltUserEntered(sp1.getEffectiveYearBuilt());
                        spDO.setImprovementNumberUserEntered(sp1.getImprovementNumber());
                        spDO.setImprovementSizeSqftUserEntered(sp1.getImprovementSizeSqft());
                        spDO.setNumberOfUnitsUserEntered(sp1.getNumberOfUnits());
                        spDO.setPercentGoodUserEntered(sp1.getPercentGood());
                        spDO.setQualityClassUserEntered(sp1.getQualityClass());
                        spDO.setRcnLessDepreciationUserEntered(sp1.getRcnLessDepreciation());
                        spDO.setRcnMainUserEntered(sp1.getRcnMain());
                        spDO.setRcnOtherTotalUserEntered(sp1.getRcnOtherTotal());
                        spDO.setRcnYearChangeUserEntered(sp1.getRcnYearChange());
                        spDO.setYearBuiltUserEntered(sp1.getYearBuilt());
                        if (null != sp1.getTransactionType()) {
                            if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                if (this.subPartCount > 0) {
                                    this.subPartCount--;
                                }
                                spDO.setSubPartNumberDisplay("");
                            } else {
                                if (null != sp1.getSubPartNumber()) {
                                    spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                }
                                this.subPartCount++;
                            }
                        } else {
                            if (null != sp1.getSubPartNumber()) {
                                spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                            }
                        }
                        this.subPartListDO.add(spDO);
                    }
                }
            }
        }

        if (null != this.finalRPI.getSubPartList()) {
            if (!this.finalRPI.getSubPartList().getSubPart().isEmpty()) {
                logger.warning("Final Subpart List: " + JSONUtils.toJson(this.finalRPI.getSubPartList().getSubPart()));
                if (!this.subPartListDO.isEmpty()) {
                    for (Iterator<RealPropertyInfoDO.SubPartList.SubPart> iteratorsp1 = this.finalRPI.getSubPartList().getSubPart().iterator();
                         iteratorsp1.hasNext(); ) {
                        RealPropertyInfoDO.SubPartList.SubPart sp1 = iteratorsp1.next();
                        Boolean isAdded = Boolean.FALSE;
                        for (Iterator<SubPartDO> iterator = this.subPartListDO.iterator(); iterator.hasNext(); ) {
                            SubPartDO sp = iterator.next();
                            if (sp.getDbUniqueId().equalsIgnoreCase(sp1.getDbUniqueId())) {
                                sp.setDbUniqueIdFinal(sp1.getDbUniqueId());
                                sp.setTransactionTypeFinal(sp1.getTransactionType());
                                sp.setSubPartNumberFinal(sp1.getSubPartNumber());
                                sp.setAddedDepreciationPercentFinal(sp1.getAddedDepreciationPercent());
                                sp.setAddedDepreciationTypeKeyFinal(sp1.getAddedDepreciationTypeKey());
                                sp.setAddedDepreciationTypeLegendFinal(sp1.getAddedDepreciationTypeLegend());
                                sp.setAddedDepreciationTypeRefFinal(sp1.getAddedDepreciationTypeRef());
                                sp.setBathroomCountFinal(sp1.getBathroomCount());
                                sp.setBedroomCountFinal(sp1.getBedroomCount());
                                sp.setCostFactorFinal(sp1.getCostFactor());
                                sp.setDepreciationTableFinal(sp1.getDepreciationTable());
                                sp.setDesignTypeKeyFinal(sp1.getDesignTypeKey());
                                sp.setDesignTypeLegendFinal(sp1.getDesignTypeLegend());
                                sp.setDesignTypeRefFinal(sp1.getDesignTypeRef());
                                sp.setEffectiveYearBuiltFinal(sp1.getEffectiveYearBuilt());
                                sp.setImprovementNumberFinal(sp1.getImprovementNumber());
                                sp.setImprovementSizeSqftFinal(sp1.getImprovementSizeSqft());
                                sp.setNumberOfUnitsFinal(sp1.getNumberOfUnits());
                                sp.setPercentGoodFinal(sp1.getPercentGood());
                                sp.setQualityClassFinal(sp1.getQualityClass());
                                sp.setRcnLessDepreciationFinal(sp1.getRcnLessDepreciation());
                                sp.setRcnMainFinal(sp1.getRcnMain());
                                sp.setRcnOtherTotalFinal(sp1.getRcnOtherTotal());
                                sp.setRcnYearChangeFinal(sp1.getRcnYearChange());
                                sp.setYearBuiltFinal(sp1.getYearBuilt());
                                if (null != sp1.getTransactionType()) {
                                    if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                        if (this.subPartCount > 0) {
                                            this.subPartCount--;
                                        }
                                        sp.setSubPartNumberDisplay("");
                                    } else {
                                        if (null != sp1.getSubPartNumber()) {
                                            sp.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                        }
                                    }
                                } else {
                                    if (null != sp1.getSubPartNumber()) {
                                        sp.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                    }
                                }
                                isAdded = Boolean.TRUE;
                            }
                        }
                        if (!isAdded) {
                            spDO = new SubPartDO();
                            spDO.setAin(sp1.getAin());
                            spDO.setAoid(sp1.getAoid());
                            spDO.setDbUniqueId(sp1.getDbUniqueId());
                            spDO.setDbUniqueIdFinal(sp1.getDbUniqueId());
                            spDO.setTransactionTypeFinal(sp1.getTransactionType());
                            spDO.setSubPartNumberFinal(sp1.getSubPartNumber());
                            spDO.setAddedDepreciationPercentFinal(sp1.getAddedDepreciationPercent());
                            spDO.setAddedDepreciationTypeKeyFinal(sp1.getAddedDepreciationTypeKey());
                            spDO.setAddedDepreciationTypeLegendFinal(sp1.getAddedDepreciationTypeLegend());
                            spDO.setAddedDepreciationTypeRefFinal(sp1.getAddedDepreciationTypeRef());
                            spDO.setBathroomCountFinal(sp1.getBathroomCount());
                            spDO.setBedroomCountFinal(sp1.getBedroomCount());
                            spDO.setCostFactorFinal(sp1.getCostFactor());
                            spDO.setDepreciationTableFinal(sp1.getDepreciationTable());
                            spDO.setDesignTypeKeyFinal(sp1.getDesignTypeKey());
                            spDO.setDesignTypeLegendFinal(sp1.getDesignTypeLegend());
                            spDO.setDesignTypeRefFinal(sp1.getDesignTypeRef());
                            spDO.setEffectiveYearBuiltFinal(sp1.getEffectiveYearBuilt());
                            spDO.setImprovementNumberFinal(sp1.getImprovementNumber());
                            spDO.setImprovementSizeSqftFinal(sp1.getImprovementSizeSqft());
                            spDO.setNumberOfUnitsFinal(sp1.getNumberOfUnits());
                            spDO.setPercentGoodFinal(sp1.getPercentGood());
                            spDO.setQualityClassFinal(sp1.getQualityClass());
                            spDO.setRcnLessDepreciationFinal(sp1.getRcnLessDepreciation());
                            spDO.setRcnMainFinal(sp1.getRcnMain());
                            spDO.setRcnOtherTotalFinal(sp1.getRcnOtherTotal());
                            spDO.setRcnYearChangeFinal(sp1.getRcnYearChange());
                            spDO.setYearBuiltFinal(sp1.getYearBuilt());
                            if (null != sp1.getTransactionType()) {
                                if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                    if (this.subPartCount > 0) {
                                        this.subPartCount--;
                                    }
                                    spDO.setSubPartNumberDisplay("");
                                } else {
                                    if (null != sp1.getSubPartNumber()) {
                                        spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                    }
                                    this.subPartCount++;
                                }
                            } else {
                                if (null != sp1.getSubPartNumber()) {
                                    spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                }
                            }
                            this.subPartListDO.add(spDO);
                        }
                    }
                } else {
                    for (RealPropertyInfoDO.SubPartList.SubPart sp1 : this.finalRPI.getSubPartList().getSubPart()) {
                        spDO = new SubPartDO();
                        spDO.setAin(sp1.getAin());
                        spDO.setAoid(sp1.getAoid());
                        spDO.setDbUniqueId(sp1.getDbUniqueId());
                        spDO.setDbUniqueIdFinal(sp1.getDbUniqueId());
                        spDO.setTransactionTypeFinal(sp1.getTransactionType());
                        spDO.setSubPartNumberFinal(sp1.getSubPartNumber());
                        spDO.setAddedDepreciationPercentFinal(sp1.getAddedDepreciationPercent());
                        spDO.setAddedDepreciationTypeKeyFinal(sp1.getAddedDepreciationTypeKey());
                        spDO.setAddedDepreciationTypeLegendFinal(sp1.getAddedDepreciationTypeLegend());
                        spDO.setAddedDepreciationTypeRefFinal(sp1.getAddedDepreciationTypeRef());
                        spDO.setBathroomCountFinal(sp1.getBathroomCount());
                        spDO.setBedroomCountFinal(sp1.getBedroomCount());
                        spDO.setCostFactorFinal(sp1.getCostFactor());
                        spDO.setDepreciationTableFinal(sp1.getDepreciationTable());
                        spDO.setDesignTypeKeyFinal(sp1.getDesignTypeKey());
                        spDO.setDesignTypeLegendFinal(sp1.getDesignTypeLegend());
                        spDO.setDesignTypeRefFinal(sp1.getDesignTypeRef());
                        spDO.setEffectiveYearBuiltFinal(sp1.getEffectiveYearBuilt());
                        spDO.setImprovementNumberFinal(sp1.getImprovementNumber());
                        spDO.setImprovementSizeSqftFinal(sp1.getImprovementSizeSqft());
                        spDO.setNumberOfUnitsFinal(sp1.getNumberOfUnits());
                        spDO.setPercentGoodFinal(sp1.getPercentGood());
                        spDO.setQualityClassFinal(sp1.getQualityClass());
                        spDO.setRcnLessDepreciationFinal(sp1.getRcnLessDepreciation());
                        spDO.setRcnMainFinal(sp1.getRcnMain());
                        spDO.setRcnOtherTotalFinal(sp1.getRcnOtherTotal());
                        spDO.setRcnYearChangeFinal(sp1.getRcnYearChange());
                        spDO.setYearBuiltFinal(sp1.getYearBuilt());
                        if (null != sp1.getTransactionType()) {
                            if (sp1.getTransactionType().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                if (this.subPartCount > 0) {
                                    this.subPartCount--;
                                }
                                spDO.setSubPartNumberDisplay("");
                            } else {
                                if (null != sp1.getSubPartNumber()) {
                                    spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                                }
                                this.subPartCount++;
                            }
                        } else {
                            if (null != sp1.getSubPartNumber()) {
                                spDO.setSubPartNumberDisplay(sp1.getSubPartNumber());
                            }
                        }
                        this.subPartListDO.add(spDO);
                    }
                }
            }
        }

        // Create the composite object used for inititation
        this.compositeDO = null;
        if (null != this.originalRPI.getComposite()) {
            this.compositeDO = new CompositeDO();
            this.compositeDO.setDbUniqueId(this.originalRPI.getComposite().getDbUniqueId());
            this.compositeDO.setAin(this.originalRPI.getAin());
            this.compositeDO.setAoid(this.originalRPI.getAoid());
            this.compositeDO.setTransactionType(this.originalRPI.getComposite().getTransactionType());
            this.compositeDO.setSqft(this.originalRPI.getComposite().getSqft());
        }
        if (null != this.userEnteredRPI.getComposite()) {
            if (null == this.compositeDO)
                this.compositeDO = new CompositeDO();
            this.compositeDO.setDbUniqueId(this.userEnteredRPI.getComposite().getDbUniqueId());
            this.compositeDO.setDbUniqueIdUserEntered(this.userEnteredRPI.getComposite().getDbUniqueId());
            this.compositeDO.setAin(this.userEnteredRPI.getAin());
            this.compositeDO.setAoid(this.userEnteredRPI.getAoid());
            this.compositeDO.setTransactionTypeUserEntered(this.userEnteredRPI.getComposite().getTransactionType());
            this.compositeDO.setSqftUserEntered(this.userEnteredRPI.getComposite().getSqft());
        }
        if (null != this.finalRPI.getComposite()) {
            if (null == this.compositeDO)
                this.compositeDO = new CompositeDO();
            this.compositeDO.setDbUniqueId(this.finalRPI.getComposite().getDbUniqueId());
            this.compositeDO.setDbUniqueIdFinal(this.finalRPI.getComposite().getDbUniqueId());
            this.compositeDO.setAin(this.finalRPI.getAin());
            this.compositeDO.setAoid(this.finalRPI.getAoid());
            this.compositeDO.setTransactionTypeFinal(this.finalRPI.getComposite().getTransactionType());
            this.compositeDO.setSqftFinal(this.finalRPI.getComposite().getSqft());
        }

        // Create the land object used for inititation for all three real property sets of information
        this.landDO = null;
        if (null != this.originalRPI.getLand()) {
            this.landDO = new LandDO();
            this.landDO.setDbUniqueId(this.originalRPI.getLand().getDbUniqueId());
            this.landDO.setAin(this.originalRPI.getAin());
            this.landDO.setAoid(this.originalRPI.getAoid());
            this.landDO.setTransactionType(this.originalRPI.getLand().getTransactionType());
            this.landDO.setCodeSplit(this.originalRPI.getLand().getCodeSplit());
            this.landDO.setCorner(this.originalRPI.getLand().getCorner());
            this.landDO.setDepth(this.originalRPI.getLand().getDepth());
            this.landDO.setFlightPath(this.originalRPI.getLand().getFlightPath());
            this.landDO.setFreewayProximity(this.originalRPI.getLand().getFreewayProximity());
            this.landDO.setGolfFront(this.originalRPI.getLand().getGolfFront());
            this.landDO.setHorses(this.originalRPI.getLand().getHorses());
            this.landDO.setSewer(this.originalRPI.getLand().getSewer());
            this.landDO.setSizeAcreAge(this.originalRPI.getLand().getSizeAcreAge());
            this.landDO.setSizeSqft(this.originalRPI.getLand().getSizeSqft());
            this.landDO.setSizeUsableSqft(this.originalRPI.getLand().getSizeUsableSqft());
            this.landDO.setTraffic(this.originalRPI.getLand().getTraffic());
            this.landDO.setUseCode(this.originalRPI.getLand().getUseCode());
            this.landDO.setUseType(this.originalRPI.getLand().getUseType());
            //if (null != this.originalRPI.getLand().getViewCodeKey())
            this.landDO.setViewCodeDisplay(this.originalRPI.getLand().getViewCodeLegend());
            this.landDO.setViewCodeKey(this.originalRPI.getLand().getViewCodeKey());
            this.landDO.setViewCodeLegend(this.originalRPI.getLand().getViewCodeLegend());
            this.landDO.setViewCodeRef(this.originalRPI.getLand().getViewCodeRef());
            this.landDO.setWidth(this.originalRPI.getLand().getWidth());
        }
        if (null != this.userEnteredRPI.getLand()) {
            if (null == this.landDO)
                this.landDO = new LandDO();
            this.landDO.setDbUniqueId(this.userEnteredRPI.getLand().getDbUniqueId());
            this.landDO.setDbUniqueIdUserEntered(this.userEnteredRPI.getLand().getDbUniqueId());
            this.landDO.setAin(this.userEnteredRPI.getAin());
            this.landDO.setAoid(this.userEnteredRPI.getAoid());
            this.landDO.setTransactionTypeUserEntered(this.userEnteredRPI.getLand().getTransactionType());
            this.landDO.setCodeSplitUserEntered(this.userEnteredRPI.getLand().getCodeSplit());
            this.landDO.setCornerUserEntered(this.userEnteredRPI.getLand().getCorner());
            this.landDO.setDepthUserEntered(this.userEnteredRPI.getLand().getDepth());
            this.landDO.setFlightPathUserEntered(this.userEnteredRPI.getLand().getFlightPath());
            this.landDO.setFreewayProximityUserEntered(this.userEnteredRPI.getLand().getFreewayProximity());
            this.landDO.setGolfFrontUserEntered(this.userEnteredRPI.getLand().getGolfFront());
            this.landDO.setHorsesUserEntered(this.userEnteredRPI.getLand().getHorses());
            this.landDO.setSewerUserEntered(this.userEnteredRPI.getLand().getSewer());
            this.landDO.setSizeAcreAgeUserEntered(this.userEnteredRPI.getLand().getSizeAcreAge());
            this.landDO.setSizeSqftUserEntered(this.userEnteredRPI.getLand().getSizeSqft());
            this.landDO.setSizeUsableSqftUserEntered(this.userEnteredRPI.getLand().getSizeUsableSqft());
            this.landDO.setTrafficUserEntered(this.userEnteredRPI.getLand().getTraffic());
            this.landDO.setUseCodeUserEntered(this.userEnteredRPI.getLand().getUseCode());
            this.landDO.setUseTypeUserEntered(this.userEnteredRPI.getLand().getUseType());
            if (null != this.userEnteredRPI.getLand().getViewCodeKey())
                this.landDO.setViewCodeDisplayUserEntered(translateViewCodeDisplay(this.userEnteredRPI));
            this.landDO.setViewCodeKeyUserEntered(this.userEnteredRPI.getLand().getViewCodeKey());
            this.landDO.setViewCodeLegendUserEntered(translateViewCodeDisplay(this.userEnteredRPI));
            this.landDO.setViewCodeRefUserEntered(this.userEnteredRPI.getLand().getViewCodeRef());
            this.landDO.setWidthUserEntered(this.userEnteredRPI.getLand().getWidth());
        }
        if (null != this.finalRPI.getLand()) {
            if (null == this.landDO)
                this.landDO = new LandDO();
            this.landDO.setDbUniqueId(this.finalRPI.getLand().getDbUniqueId());
            this.landDO.setDbUniqueIdFinal(this.finalRPI.getLand().getDbUniqueId());
            this.landDO.setAin(this.finalRPI.getAin());
            this.landDO.setAoid(this.finalRPI.getAoid());
            this.landDO.setTransactionTypeFinal(this.finalRPI.getLand().getTransactionType());
            this.landDO.setCodeSplitFinal(this.finalRPI.getLand().getCodeSplit());
            this.landDO.setCornerFinal(this.finalRPI.getLand().getCorner());
            this.landDO.setDepthFinal(this.finalRPI.getLand().getDepth());
            this.landDO.setFlightPathFinal(this.finalRPI.getLand().getFlightPath());
            this.landDO.setFreewayProximityFinal(this.finalRPI.getLand().getFreewayProximity());
            this.landDO.setGolfFrontFinal(this.finalRPI.getLand().getGolfFront());
            this.landDO.setHorsesFinal(this.finalRPI.getLand().getHorses());
            this.landDO.setSewerFinal(this.finalRPI.getLand().getSewer());
            this.landDO.setSizeAcreAgeFinal(this.finalRPI.getLand().getSizeAcreAge());
            this.landDO.setSizeSqftFinal(this.finalRPI.getLand().getSizeSqft());
            this.landDO.setSizeUsableSqftFinal(this.finalRPI.getLand().getSizeUsableSqft());
            this.landDO.setTrafficFinal(this.finalRPI.getLand().getTraffic());
            this.landDO.setUseCodeFinal(this.finalRPI.getLand().getUseCode());
            this.landDO.setUseTypeFinal(this.finalRPI.getLand().getUseType());
            if (null != this.finalRPI.getLand().getViewCodeKey())
                this.landDO.setViewCodeDisplayFinal(translateViewCodeDisplay(this.finalRPI));
            this.landDO.setViewCodeKeyFinal(this.finalRPI.getLand().getViewCodeKey());
            this.landDO.setViewCodeLegendFinal(translateViewCodeDisplay(this.finalRPI));
            this.landDO.setViewCodeRefFinal(this.finalRPI.getLand().getViewCodeRef());
            this.landDO.setWidthFinal(this.finalRPI.getLand().getWidth());
        }
    }

    public Boolean InitiatePropertyDataCR(String ain, String userID, String userType, String sourceType, String dataSource) throws AmpChangeRequestException {
        // Initiate the BPM Process
        logger.warning("Initiating '" + sourceType + "' request for AIN: " + ain);

        String processType = ChangeRequestConstants.PROCESS_TYPE_INITIATE;
        String bpmStatus = ChangeRequestConstants.BPM_STATUS_COMPLETED;

        setIsInitiateSuccess(Boolean.FALSE);
        if (getSourceType().equalsIgnoreCase(ChangeRequestConstants.RERALPROPERTYINFO_SOURCE_EDIT)) {
            this.tempSubPartDO = new SubPartDO();
            this.tempSubPartDO.setDbUniqueId("0");
        } else {
            // convert the phone numner to store into the DB in a consistant manner
            // this only happens when the source is CreatePDCR
            convertPhoneNumber();
        }

        // Update the user entered real property information with the UI entered information
        if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_SOA)) {
            // Remove the temp subpart if the user is in EditPDCR
            UpdateUserEnteredRPI();
        } else if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
            UdateFinalRPI();
        }

        logger.warning("Original Real Property Info: " + JSONUtils.toJson(this.originalRPI));
        logger.warning("User Updated Real Property Info: " + JSONUtils.toJson(this.userEnteredRPI));
        logger.warning("Final Real Property Info: " + JSONUtils.toJson(this.finalRPI));
        logger.warning("Requestor information: " + JSONUtils.toJson(this.requestorInfoDO));

        // Call the facade to initiate the BPM action, or submit to BPM based on the datasource being passed in
        bpmStatus =
                ChangeRequestFacade.InitiatePropertyDataCR(ain, this.originalRPI.getAoid(), userID, userType, sourceType, dataSource, getActionType(), processType,
                                                           this.requestorInfoDO, this.originalRPI, this.userEnteredRPI, this.finalRPI);

        logger.warning("Change request initiation status for AIN " + ain + " is: " + bpmStatus);

        if (null != bpmStatus) {
            if (bpmStatus.equalsIgnoreCase(ChangeRequestConstants.BPM_STATUS_SUBMITTED)) {
                setIsInitiateSuccess(Boolean.TRUE);
            }
        }

        // The return value is set when this method is called from the UI when there is no AIN passed into the taskflow
        return getIsInitiateSuccess();
    }

    private void UpdateUserEnteredRPI() {

        // Update the subpart information from temp to the userEntered real property info
        Boolean isPartListEmpty = Boolean.TRUE;

        RealPropertyInfoDO.SubPartList subPartList = null;
        if (null != this.subPartListDO) {
            subPartList = new RealPropertyInfoDO.SubPartList();
            for (SubPartDO spDO : this.subPartListDO) {
                RealPropertyInfoDO.SubPartList.SubPart sp = new RealPropertyInfoDO.SubPartList.SubPart();
                // This condition will only apply for create PDCR process
                if (spDO.getDbUniqueId().equalsIgnoreCase(this.tempSubPartDO.getDbUniqueId())) {
                    // Check to see if any data was entered during CreatePDCR
                    // The TempSubpartlist will only be populated for CreatePDCR
                    sp.setDbUniqueId(this.tempSubPartDO.getDbUniqueId());
                    sp.setAin(this.tempSubPartDO.getAin());
                    sp.setAoid(this.tempSubPartDO.getAoid());
                    if ((null == this.tempSubPartDO.getNumberOfUnitsUserEntered()) && (null == this.tempSubPartDO.getImprovementSizeSqftUserEntered()) &&
                        (null == this.tempSubPartDO.getBedroomCountUserEntered()) && (null == this.tempSubPartDO.getBathroomCountUserEntered()) &&
                        (null == this.tempSubPartDO.getYearBuiltUserEntered())) {
                        sp.setTransactionType(null);
                    } else {
                        sp.setTransactionType(this.tempSubPartDO.getTransactionTypeUserEntered());
                    }
                    // If nothing new was added the payload will only have the minimal information
                    if (null != sp.getTransactionType()) {
                        sp.setAddedDepreciationPercent(this.tempSubPartDO.getAddedDepreciationPercentUserEntered());
                        sp.setAddedDepreciationTypeKey(this.tempSubPartDO.getAddedDepreciationTypeKeyUserEntered());
                        sp.setAddedDepreciationTypeLegend(this.tempSubPartDO.getAddedDepreciationTypeLegendUserEntered());
                        sp.setAddedDepreciationTypeRef(this.tempSubPartDO.getAddedDepreciationTypeRefUserEntered());
                        sp.setBathroomCount(this.tempSubPartDO.getBathroomCountUserEntered());
                        sp.setBedroomCount(this.tempSubPartDO.getBedroomCountUserEntered());
                        sp.setCostFactor(this.tempSubPartDO.getCostFactorUserEntered());
                        sp.setDepreciationTable(this.tempSubPartDO.getDepreciationTableUserEntered());
                        sp.setDesignTypeKey(this.tempSubPartDO.getDesignTypeKeyUserEntered());
                        sp.setDesignTypeLegend(this.tempSubPartDO.getDesignTypeLegendUserEntered());
                        sp.setDesignTypeRef(this.tempSubPartDO.getDesignTypeRefUserEntered());
                        sp.setEffectiveYearBuilt(this.tempSubPartDO.getEffectiveYearBuiltUserEntered());
                        sp.setImprovementNumber(this.tempSubPartDO.getImprovementNumberUserEntered());
                        sp.setImprovementSizeSqft(this.tempSubPartDO.getImprovementSizeSqftUserEntered());
                        sp.setNumberOfUnits(this.tempSubPartDO.getNumberOfUnitsUserEntered());
                        sp.setPercentGood(this.tempSubPartDO.getPercentGoodUserEntered());
                        sp.setQualityClass(this.tempSubPartDO.getQualityClassUserEntered());
                        sp.setRcnLessDepreciation(this.tempSubPartDO.getRcnLessDepreciationUserEntered());
                        sp.setRcnMain(this.tempSubPartDO.getRcnMainUserEntered());
                        sp.setRcnOtherTotal(this.tempSubPartDO.getRcnOtherTotalUserEntered());
                        sp.setRcnYearChange(this.tempSubPartDO.getRcnYearChangeUserEntered());
                        sp.setSubPartNumber(this.tempSubPartDO.getSubPartNumberUserEntered());
                        sp.setYearBuilt(this.tempSubPartDO.getYearBuiltUserEntered());
                    }
                    subPartList.getSubPart().add(sp);
                    isPartListEmpty = Boolean.FALSE;
                } else {
                    sp.setDbUniqueId(spDO.getDbUniqueId());
                    sp.setAin(spDO.getAin());
                    sp.setAoid(spDO.getAoid());
                    sp.setTransactionType(spDO.getTransactionTypeUserEntered());
                    sp.setAddedDepreciationPercent(spDO.getAddedDepreciationPercentUserEntered());
                    sp.setAddedDepreciationTypeKey(spDO.getAddedDepreciationTypeKeyUserEntered());
                    sp.setAddedDepreciationTypeLegend(spDO.getAddedDepreciationTypeLegendUserEntered());
                    sp.setAddedDepreciationTypeRef(spDO.getAddedDepreciationTypeRefUserEntered());
                    sp.setBathroomCount(spDO.getBathroomCountUserEntered());
                    sp.setBedroomCount(spDO.getBedroomCountUserEntered());
                    sp.setCostFactor(spDO.getCostFactorUserEntered());
                    sp.setDepreciationTable(spDO.getDepreciationTableUserEntered());
                    sp.setDesignTypeKey(spDO.getDesignTypeKeyUserEntered());
                    sp.setDesignTypeLegend(spDO.getDesignTypeLegendUserEntered());
                    sp.setDesignTypeRef(spDO.getDesignTypeRefUserEntered());
                    sp.setEffectiveYearBuilt(spDO.getEffectiveYearBuiltUserEntered());
                    sp.setImprovementNumber(spDO.getImprovementNumberUserEntered());
                    sp.setImprovementSizeSqft(spDO.getImprovementSizeSqftUserEntered());
                    sp.setNumberOfUnits(spDO.getNumberOfUnitsUserEntered());
                    sp.setPercentGood(spDO.getPercentGoodUserEntered());
                    sp.setQualityClass(spDO.getQualityClassUserEntered());
                    sp.setRcnLessDepreciation(spDO.getRcnLessDepreciationUserEntered());
                    sp.setRcnMain(spDO.getRcnMainUserEntered());
                    sp.setRcnOtherTotal(spDO.getRcnOtherTotalUserEntered());
                    sp.setRcnYearChange(spDO.getRcnYearChangeUserEntered());
                    sp.setSubPartNumber(spDO.getSubPartNumberUserEntered());
                    sp.setYearBuilt(spDO.getYearBuiltUserEntered());
                    subPartList.getSubPart().add(sp);
                    isPartListEmpty = Boolean.FALSE;
                }
            }
        }
        this.userEnteredRPI.setSubPartList(subPartList);

        // Update the composite from temp to the userEntered real property info
        RealPropertyInfoDO.Composite composite = null;
        if (null != this.compositeDO) {
            composite = new RealPropertyInfoDO.Composite();
            composite.setDbUniqueId(this.compositeDO.getDbUniqueId());
            composite.setAin(this.compositeDO.getAin());
            composite.setAoid(this.compositeDO.getAoid());
            composite.setTransactionType(this.compositeDO.getTransactionTypeUserEntered());
            composite.setSqft(this.compositeDO.getSqftUserEntered());
        }
        this.userEnteredRPI.setComposite(composite);

        // Update the land from temp to the userEntered real property info
        RealPropertyInfoDO.Land land = null;
        if (null != this.landDO) {
            land = new RealPropertyInfoDO.Land();
            land.setDbUniqueId(this.landDO.getDbUniqueId());
            land.setAin(this.landDO.getAin());
            land.setAoid(this.landDO.getAoid());
            land.setTransactionType(this.landDO.getTransactionTypeUserEntered());
            land.setCodeSplit(this.landDO.getCodeSplitUserEntered());
            land.setCorner(this.landDO.getCornerUserEntered());
            land.setDepth(this.landDO.getDepthUserEntered());
            land.setFlightPath(this.landDO.getFlightPathUserEntered());
            land.setFreewayProximity(this.landDO.getFreewayProximityUserEntered());
            land.setGolfFront(this.landDO.getGolfFrontUserEntered());
            land.setHorses(this.landDO.getHorsesUserEntered());
            land.setSewer(this.landDO.getSewerUserEntered());
            land.setSizeAcreAge(this.landDO.getSizeAcreAgeUserEntered());
            land.setSizeSqft(this.landDO.getSizeSqftUserEntered());
            land.setSizeUsableSqft(this.landDO.getSizeUsableSqftUserEntered());
            land.setTraffic(this.landDO.getTrafficUserEntered());
            land.setUseCode(this.landDO.getUseCodeUserEntered());
            land.setUseType(this.landDO.getUseTypeUserEntered());
            land.setViewCodeKey(this.landDO.getViewCodeKeyUserEntered());
            land.setViewCodeRef(this.landDO.getViewCodeRefUserEntered());
            land.setViewCodeLegend(this.landDO.getViewCodeLegendUserEntered());
            land.setWidth(this.landDO.getWidthUserEntered());
        }
        this.userEnteredRPI.setLand(land);
    }

    private void UdateFinalRPI() {

        // Update the subpart information from temp to the userEntered real property info
        RealPropertyInfoDO.SubPartList subPartList = new RealPropertyInfoDO.SubPartList();
        for (SubPartDO spDO : this.subPartListDO) {
            RealPropertyInfoDO.SubPartList.SubPart sp = new RealPropertyInfoDO.SubPartList.SubPart();
            sp.setDbUniqueId(spDO.getDbUniqueId());
            sp.setAin(spDO.getAin());
            sp.setAoid(spDO.getAoid());
            sp.setTransactionType(spDO.getTransactionTypeFinal());
            sp.setAddedDepreciationPercent(spDO.getAddedDepreciationPercentFinal());
            sp.setAddedDepreciationTypeKey(spDO.getAddedDepreciationTypeKeyFinal());
            sp.setAddedDepreciationTypeLegend(spDO.getAddedDepreciationTypeLegendFinal());
            sp.setAddedDepreciationTypeRef(spDO.getAddedDepreciationTypeRefFinal());
            sp.setBathroomCount(spDO.getBathroomCountFinal());
            sp.setBedroomCount(spDO.getBedroomCountFinal());
            sp.setCostFactor(spDO.getCostFactorFinal());
            sp.setDepreciationTable(spDO.getDepreciationTableFinal());
            sp.setDesignTypeKey(spDO.getDesignTypeKeyFinal());
            sp.setDesignTypeLegend(spDO.getDesignTypeLegendFinal());
            sp.setDesignTypeRef(spDO.getDesignTypeRefFinal());
            sp.setEffectiveYearBuilt(spDO.getEffectiveYearBuiltFinal());
            sp.setImprovementNumber(spDO.getImprovementNumberFinal());
            sp.setImprovementSizeSqft(spDO.getImprovementSizeSqftFinal());
            sp.setNumberOfUnits(spDO.getNumberOfUnitsFinal());
            sp.setPercentGood(spDO.getPercentGoodFinal());
            sp.setQualityClass(spDO.getQualityClassFinal());
            sp.setRcnLessDepreciation(spDO.getRcnLessDepreciationFinal());
            sp.setRcnMain(spDO.getRcnMainFinal());
            sp.setRcnOtherTotal(spDO.getRcnOtherTotalFinal());
            sp.setRcnYearChange(spDO.getRcnYearChangeFinal());
            sp.setSubPartNumber(spDO.getSubPartNumberFinal());
            sp.setYearBuilt(spDO.getYearBuiltFinal());
            subPartList.getSubPart().add(sp);
        }
        this.finalRPI.setSubPartList(subPartList);

        // Update the composite from temp to the Final real property info
        RealPropertyInfoDO.Composite composite = null;
        if (null != this.compositeDO) {
            composite = new RealPropertyInfoDO.Composite();
            composite.setDbUniqueId(this.compositeDO.getDbUniqueId());
            composite.setAin(this.compositeDO.getAin());
            composite.setAoid(this.compositeDO.getAoid());
            composite.setTransactionType(this.compositeDO.getTransactionTypeFinal());
            composite.setSqft(this.compositeDO.getSqftFinal());
        }
        this.finalRPI.setComposite(composite);

        // Update the land from temp to the Final real property info
        RealPropertyInfoDO.Land land = null;
        if (null != this.landDO) {
            land = new RealPropertyInfoDO.Land();
            land.setDbUniqueId(this.landDO.getDbUniqueId());
            land.setAin(this.landDO.getAin());
            land.setAoid(this.landDO.getAoid());
            land.setTransactionType(this.landDO.getTransactionTypeFinal());
            land.setCodeSplit(this.landDO.getCodeSplitFinal());
            land.setCorner(this.landDO.getCornerFinal());
            land.setDepth(this.landDO.getDepthFinal());
            land.setFlightPath(this.landDO.getFlightPathFinal());
            land.setFreewayProximity(this.landDO.getFreewayProximityFinal());
            land.setGolfFront(this.landDO.getGolfFrontFinal());
            land.setHorses(this.landDO.getHorsesFinal());
            land.setSewer(this.landDO.getSewerFinal());
            land.setSizeAcreAge(this.landDO.getSizeAcreAgeFinal());
            land.setSizeSqft(this.landDO.getSizeSqftFinal());
            land.setSizeUsableSqft(this.landDO.getSizeUsableSqftFinal());
            land.setTraffic(this.landDO.getTrafficFinal());
            land.setUseCode(this.landDO.getUseCodeFinal());
            land.setUseType(this.landDO.getUseTypeFinal());
            land.setViewCodeKey(this.landDO.getViewCodeKeyFinal());
            land.setViewCodeRef(this.landDO.getViewCodeRefFinal());
            land.setViewCodeLegend(this.landDO.getViewCodeLegendFinal());
            land.setWidth(this.landDO.getWidthFinal());
        }
        this.finalRPI.setLand(land);
    }

    private void convertPhoneNumber() {
        if (null != this.requestorInfoDO) {
            if (null != this.requestorInfoDO.getPhone()) {
                String phone = this.requestorInfoDO.getPhone();
                String regex = ChangeRequestConstants.PHONE_REGEX_CONVERSION;

                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(phone);
                //If phone number is not correct then format it to 123-456-7890
                if (matcher.matches()) {
                    this.requestorInfoDO.setPhone(matcher.replaceFirst(ChangeRequestConstants.PHONE_CONVERSION_PATTERN));
                }
                logger.warning("Formated phone number: " + this.requestorInfoDO.getPhone());
            }
        }
    }

    private void ClearTransactionMessage() {
        setTransactionMessage("");
    }

    private void CreateTransactionMessage(String rpiType) {
        String msg = "";
        String subPartNumber = "";

        // Need to determine the real property type so we add the correct transaction message to display
        if (null != rpiType) {
            if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                if (null != this.tempSubPartDO)
                    subPartNumber =
                            (null == this.tempSubPartDO.getSubPartNumberUserEntered() ? this.tempSubPartDO.getSubPartNumber() : this.tempSubPartDO.getSubPartNumberUserEntered());
            } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                if (null != this.tempSubPartDO)
                    subPartNumber =
                            (null == this.tempSubPartDO.getSubPartNumberFinal() ? this.tempSubPartDO.getSubPartNumber() : this.tempSubPartDO.getSubPartNumberFinal());
            }
        }

        // look at the transaction type, and display the proper message to the user
        if (null != getTransactionType()) {
            if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_SUBPART)) {
                msg = getPdcrBundle().getString("TRANSACTION_PREXIF") + " " + subPartNumber + " " + getPdcrBundle().getString("TRANSACTION_SP_ADDED");
            } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_EDIT_SUBPART)) {
                msg = getPdcrBundle().getString("TRANSACTION_PREXIF") + " " + subPartNumber + " " + getPdcrBundle().getString("TRANSACTION_SP_UPDATED");
            } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_DELETE_SUBPART)) {
                msg = getPdcrBundle().getString("TRANSACTION_PREXIF") + " " + subPartNumber + " " + getPdcrBundle().getString("TRANSACTION_SP_DELETED");
            } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_COMPOSITE)) {
                msg = getPdcrBundle().getString("TRANSACTION_COMP_ADDED");
            } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_EDIT_COMPOSITE)) {
                msg = getPdcrBundle().getString("TRANSACTION_COMP_UPDATED");
            } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_DELETE_COMPOSITE)) {
                msg = getPdcrBundle().getString("TRANSACTION_COMP_DELETED");
            }
        }
        setTransactionMessage(msg);
    }

    public String AddSubPart() {
        //logger.warning("Adding new SubPart");
        ClearTransactionMessage();

        // check to see if we already have 5 subparts. If this is the case then we need to add a composite
        if (getSubPartCount() == 5) {
            logger.warning("Already 5 SubParts, adding Composite Data Line");
            AddComposite();
        } else {
            setTransactionType(ChangeRequestConstants.TRANSACTION_CREATE_SUBPART);
            // Create a new object to use for the SubPart addition
            this.tempSubPartDO = new SubPartDO();
            this.tempSubPartDO.setDbUniqueId(ChangeRequestConstants.CREATE_SUBPART_U_PREFIX + generateUniquieID(8));
            this.tempSubPartDO.setDbUniqueIdUserEntered(this.tempSubPartDO.getDbUniqueId());
            // This is used for adding from BPM, It does no harm for the portal UI where we work with UserEntered information
            this.tempSubPartDO.setDbUniqueIdFinal(this.tempSubPartDO.getDbUniqueId());
            this.tempSubPartDO.setAin(this.originalRPI.getAin());
            this.tempSubPartDO.setAoid(this.originalRPI.getAoid());

            logger.warning("SubPart staged to add: " + JSONUtils.toJson(this.tempSubPartDO));
        }
        return getTransactionType();
    }

    public void EditSubPart(String subPartID, String rpiType) {
        //logger.warning("Editing subpart with ID: " + subPartID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_EDIT_SUBPART);
        ClearTransactionMessage();

        this.tempSubPartDO = new SubPartDO();

        Integer count = 0;
        if (null != this.subPartListDO) {
            // find the the SubPart to edit
            for (SubPartDO spDO : this.subPartListDO) {
                if (spDO.getDbUniqueId().equalsIgnoreCase(subPartID)) {
                    this.tempSubPartDO = (SubPartDO)JSONUtils.fromJson(JSONUtils.toJson(spDO), SubPartDO.class);
                    if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                        if (null == this.tempSubPartDO.getTransactionTypeUserEntered())
                            this.tempSubPartDO.setSubPartNumberUserEntered(this.tempSubPartDO.getSubPartNumber());
                    } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                        if (null == this.tempSubPartDO.getTransactionTypeFinal())
                            this.tempSubPartDO.setSubPartNumberFinal(this.tempSubPartDO.getSubPartNumber());
                    }
                    count++;
                    break;
                }
            }
        } else {
            logger.warning("Something went wrong! There were no sub parts found for this Real Property");
        }
        if (count == 0) {
            logger.warning("Something went wrong! There was were no sub parts found for this Real Property");
        }
        logger.warning("Subpart staged to edited: " + JSONUtils.toJson(this.tempSubPartDO));
    }

    public void ReviewSubPart(String subPartID) {
        //logger.warning("Editing subpart with ID: " + subPartID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_REVIEW_SUBPART);
        ClearTransactionMessage();

        this.tempSubPartDO = new SubPartDO();

        Integer count = 0;
        if (null != this.subPartListDO) {
            // find the the SubPart to edit
            for (SubPartDO spDO : this.subPartListDO) {
                if (spDO.getDbUniqueId().equalsIgnoreCase(subPartID)) {
                    this.tempSubPartDO = (SubPartDO)JSONUtils.fromJson(JSONUtils.toJson(spDO), SubPartDO.class);
                    count++;
                    break;
                }
            }
        } else {
            logger.warning("Something went wrong! There were no sub parts found for this Real Property");
        }
        if (count == 0) {
            logger.warning("Something went wrong! There was were no sub parts found for this Real Property");
        }
        logger.warning("Subpart being reviewed: " + JSONUtils.toJson(this.tempSubPartDO));
    }

    public void DeleteSubPart(String subPartID) {
        //logger.warning("Deleting subpart with ID: " + subPartID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_DELETE_SUBPART);
        ClearTransactionMessage();

        this.tempSubPartDO = new SubPartDO();

        Integer count = 0;
        if (null != this.subPartListDO) {
            // find the the SubPart to delete
            for (SubPartDO spDO : this.subPartListDO) {
                if (spDO.getDbUniqueId().equalsIgnoreCase(subPartID)) {
                    this.tempSubPartDO = (SubPartDO)JSONUtils.fromJson(JSONUtils.toJson(spDO), SubPartDO.class);
                    count--;
                    break;
                }
            }
        } else {
            logger.warning("Something went wrong! There were no sub parts found for this Real Property");
        }
        if (count == 0) {
            logger.warning("Something went wrong! There was were no sub parts found for this Real Property");
        }
        logger.warning("Subpart staged to deleted: " + JSONUtils.toJson(this.tempSubPartDO));
    }

    public String SaveSubPart(String rpiType) {
        //logger.warning("Saving the subpart with ID: " + subPartID + " With transaction type: " + getTransactionType());
        String returnValue = ChangeRequestConstants.SAVE_SUCESS;

        // Check the transaction type to save appopriatly
        if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_SUBPART)) {
            // Need to determine the subpart type so we add the correct transaction, and sub part number to display
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_CREATE);
                    this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumberUserEntered());
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    this.tempSubPartDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_CREATE);
                    this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumberFinal());
                } else {
                    this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumber());
                }
            } else {
                this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumber());
            }
            if (null == this.subPartListDO)
                this.subPartListDO = new ArrayList<SubPartDO>();
            this.subPartListDO.add(this.tempSubPartDO);
            this.subPartCount++;
        } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_EDIT_SUBPART)) {
            // build the subpart list with the new value
            List<SubPartDO> subPartList = new ArrayList<SubPartDO>();
            for (SubPartDO sp : this.subPartListDO) {
                if (sp.getDbUniqueId().equalsIgnoreCase(this.tempSubPartDO.getDbUniqueId())) {
                    // Check to see if we have previously worked on this subpart
                    if (null != rpiType) {
                        if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                            if (null != sp.getTransactionTypeUserEntered()) {
                                // if we are editing the subpart that was originally added then do not override the transaction type
                                if (!this.tempSubPartDO.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.SUBPART_CREATE)) {
                                    this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_UPDATE);
                                }
                            } else {
                                this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_UPDATE);
                            }
                            if (null != this.tempSubPartDO.getSubPartNumberUserEntered()) {
                                this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumberUserEntered());
                            }
                            // For now we are just maintaining the improvement number
                            this.tempSubPartDO.setImprovementNumberUserEntered(this.tempSubPartDO.getImprovementNumber());
                        } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                            if (null != sp.getTransactionTypeFinal()) {
                                // if we are editing the subpart that was originally added then do not override the transaction type
                                if (!this.tempSubPartDO.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.SUBPART_CREATE)) {
                                    this.tempSubPartDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_UPDATE);
                                }
                            } else {
                                this.tempSubPartDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_UPDATE);
                            }
                            if (null != this.tempSubPartDO.getSubPartNumberFinal()) {
                                this.tempSubPartDO.setSubPartNumberDisplay(this.tempSubPartDO.getSubPartNumberFinal());
                            }
                            // For now we are just maintaining the improvement number
                            this.tempSubPartDO.setImprovementNumberFinal(this.tempSubPartDO.getImprovementNumber());
                        }
                        subPartList.add(this.tempSubPartDO);
                    }
                } else {
                    subPartList.add(sp);
                }
            }
            this.subPartListDO = subPartList;
        } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_DELETE_SUBPART)) {
            // build the subpart list with the new value
            List<SubPartDO> subPartList = new ArrayList<SubPartDO>();
            for (SubPartDO sp : this.subPartListDO) {
                if (sp.getDbUniqueId().equalsIgnoreCase(this.tempSubPartDO.getDbUniqueId())) {
                    // Check to see if we have previously worked on this subpart
                    if (null != rpiType) {
                        if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                            if (null != this.tempSubPartDO.getTransactionTypeUserEntered()) {
                                if (this.tempSubPartDO.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.SUBPART_UPDATE)) {
                                    this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_DELETE);
                                    subPartList.add(this.tempSubPartDO);
                                }
                            } else {
                                this.tempSubPartDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_DELETE);
                                subPartList.add(this.tempSubPartDO);
                            }
                            if (this.subPartCount > 0) {
                                this.subPartCount--;
                            }
                            //if (null != this.tempSubPartDO.getSubPartNumberUserEntered()) {
                            this.tempSubPartDO.setSubPartNumberDisplay(null);
                            //}
                            // For now we are just maintaining the improvement number
                            this.tempSubPartDO.setImprovementNumberUserEntered(this.tempSubPartDO.getImprovementNumber());
                        } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                            if (null != this.tempSubPartDO.getTransactionTypeFinal()) {
                                if (this.tempSubPartDO.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.SUBPART_UPDATE)) {
                                    this.tempSubPartDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_DELETE);
                                    subPartList.add(this.tempSubPartDO);
                                }
                            } else {
                                this.tempSubPartDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_DELETE);
                                subPartList.add(this.tempSubPartDO);
                            }
                            if (this.subPartCount > 0) {
                                this.subPartCount--;
                            }
                            //if (null != this.tempSubPartDO.getSubPartNumberFinal()) {
                            this.tempSubPartDO.setSubPartNumberDisplay(null);
                            //}
                            // For now we are just maintaining the improvement number
                            this.tempSubPartDO.setImprovementNumberFinal(this.tempSubPartDO.getImprovementNumber());
                        }
                    }
                } else {
                    subPartList.add(sp);
                }
            }
            this.subPartListDO = subPartList;
        }

        // Create th mesage to display to the user
        CreateTransactionMessage(rpiType);

        logger.warning("SubPart being saved: " + JSONUtils.toJson(this.tempSubPartDO));
        logger.warning("SubPart list after save: " + JSONUtils.toJson(this.subPartListDO));

        return returnValue;
    }

    public void CancelSubPart() {
        //logger.warning("Canceling subpart with transaction type: " + getTransactionType());

        // If we are canceling the subpart then we need to reset the values
        // This is a saftey instantiation, but not neccessary
        this.tempSubPartDO = new SubPartDO();
    }

    public Boolean ValidateDuplicateSubPart(String subPartId, String subPartNumber, String rpiType) {
        logger.warning("Validating subpart Number: " + subPartNumber + " with transaction type: " + getTransactionType() + " for " + rpiType + " data");
        logger.warning("Validating subpart with UniqueID: " + subPartId);

        // check the existing subpart list to see if the subpart number is already in use
        // return true is already in use, but do not include it as duplicate if marked for deletion
        Boolean returnValue = Boolean.FALSE;

        // go through the current list to look for duplicates
        for (SubPartDO sp : this.subPartListDO) {
            logger.warning("SubPart being validated: " + JSONUtils.toJson(sp));
            // Check the type of data that needs to be validated
            if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                if (null != sp.getSubPartNumberUserEntered()) {
                    if (!sp.getDbUniqueIdUserEntered().equalsIgnoreCase(subPartId)) {
                        if (sp.getSubPartNumberUserEntered().equalsIgnoreCase(subPartNumber)) {
                            if (null != sp.getTransactionTypeUserEntered()) {
                                if (!sp.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                    returnValue = Boolean.TRUE;
                                    break;
                                }
                            } else {
                                returnValue = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                } else {
                    if (!sp.getDbUniqueId().equalsIgnoreCase(subPartId)) {
                        if (null != sp.getSubPartNumber()) {
                            if (sp.getSubPartNumber().equalsIgnoreCase(subPartNumber)) {
                                returnValue = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                }
            } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                if (null != sp.getSubPartNumberFinal()) {
                    if (!sp.getDbUniqueIdFinal().equalsIgnoreCase(subPartId)) {
                        if (sp.getSubPartNumberFinal().equalsIgnoreCase(subPartNumber)) {
                            if (null != sp.getTransactionTypeFinal()) {
                                if (!sp.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.SUBPART_DELETE)) {
                                    returnValue = Boolean.TRUE;
                                    break;
                                }
                            } else {
                                returnValue = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                } else {
                    if (!sp.getDbUniqueId().equalsIgnoreCase(subPartId)) {
                        if (null != sp.getSubPartNumber()) {
                            if (sp.getSubPartNumber().equalsIgnoreCase(subPartNumber)) {
                                returnValue = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                }
            }
        }
        logger.warning("SubPart Duplication Validation: " + subPartNumber + (returnValue ? " is a duplicate." : " is not a duplication."));

        return returnValue;
    }

    public void AddComposite() {
        //logger.warning("Adding new Composite");
        setTransactionType(ChangeRequestConstants.TRANSACTION_CREATE_COMPOSITE);
        ClearTransactionMessage();

        // Create a new object to use for the Composite addition
        // There was no original composite so we don't need to worry about preserving anything
        this.tempCompositeDO = new CompositeDO();
        this.tempCompositeDO.setDbUniqueId(ChangeRequestConstants.CREATE_COMPOSITE_U_PREFIX + generateUniquieID(8));
        this.tempCompositeDO.setDbUniqueIdUserEntered(this.tempCompositeDO.getDbUniqueId());
        // This is used for adding from BPM, It does no harm for the portal UI where we work with UserEntered information
        this.tempCompositeDO.setDbUniqueIdFinal(this.tempCompositeDO.getDbUniqueId());
        this.tempCompositeDO.setAin(this.originalRPI.getAin());
        this.tempCompositeDO.setAoid(this.originalRPI.getAoid());

        logger.warning("Composite staged to add: " + JSONUtils.toJson(this.tempCompositeDO));
    }

    public void EditComposite(String compositeID) {
        //logger.warning("Editing composite with ID: " + compositeID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_EDIT_COMPOSITE);
        ClearTransactionMessage();

        if (null != this.compositeDO) {
            // get the the composit to edit
            if (null != this.compositeDO.getDbUniqueId()) {
                if (this.compositeDO.getDbUniqueId().equalsIgnoreCase(compositeID)) {
                    // if we have a valid composite so make a copy for the edit
                    this.tempCompositeDO = (CompositeDO)JSONUtils.fromJson(JSONUtils.toJson(this.compositeDO), CompositeDO.class);
                } else {
                    logger.warning("Something went wrong! The compositeID did not match what we have in the DB");
                }
            } else {
                logger.warning("Something went wrong! There was no composite found for this Real Property");
            }
        } else {
            logger.warning("Something went wrong! There was no composite found for this Real Property");
        }
        logger.warning("Composite staged to edited: " + JSONUtils.toJson(this.compositeDO));
    }

    public void DeleteComposite(String compositeID) {
        //logger.warning("Deleting composite with ID: " + compositeID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_DELETE_COMPOSITE);
        ClearTransactionMessage();

        if (null != this.compositeDO) {
            // get the the composit to delete
            if (this.compositeDO.getDbUniqueId().equalsIgnoreCase(compositeID)) {
                // if we have a valid composite so make a copy for the delete
                this.tempCompositeDO = (CompositeDO)JSONUtils.fromJson(JSONUtils.toJson(this.compositeDO), CompositeDO.class);
            } else {
                logger.warning("Something went wrong! The compositeID did not match what we have in the DB");
            }
        } else {
            logger.warning("Something went wrong! There was no composite found for this Real Property");
        }
        logger.warning("Composite staged to delete: " + JSONUtils.toJson(this.compositeDO));
    }

    public String SaveComposite(String rpiType) {
        //logger.warning("Saving composite with ID: " + compositeID + " With transaction type: " + getTransactionType());
        String returnValue = ChangeRequestConstants.SAVE_SUCESS;

        if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_COMPOSITE)) {
            // Need to determine the subpart type so we add the correct transaction, and sub part number to display
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    this.tempCompositeDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_CREATE);
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    this.tempCompositeDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_CREATE);
                }
            }
            this.compositeDO = (CompositeDO)JSONUtils.fromJson(JSONUtils.toJson(this.tempCompositeDO), CompositeDO.class);
        } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_EDIT_COMPOSITE)) {
            // Check to see if we have previously worked on this composite
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    if (null != this.compositeDO.getTransactionTypeUserEntered()) {
                        // if we are editing the composite that was originally added then do not override the transaction type
                        if (!this.compositeDO.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.COMPOSITE_CREATE)) {
                            this.tempCompositeDO.setTransactionTypeUserEntered(ChangeRequestConstants.COMPOSITE_UPDATE);
                        }
                    } else {
                        this.tempCompositeDO.setTransactionTypeUserEntered(ChangeRequestConstants.COMPOSITE_UPDATE);
                    }
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    if (null != this.compositeDO.getTransactionTypeFinal()) {
                        // if we are editing the composite that was originally added then do not override the transaction type
                        if (!this.compositeDO.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.COMPOSITE_CREATE)) {
                            this.tempCompositeDO.setTransactionTypeFinal(ChangeRequestConstants.COMPOSITE_UPDATE);
                        }
                    } else {
                        this.tempCompositeDO.setTransactionTypeFinal(ChangeRequestConstants.COMPOSITE_UPDATE);
                    }
                }
            }
            this.compositeDO = (CompositeDO)JSONUtils.fromJson(JSONUtils.toJson(this.tempCompositeDO), CompositeDO.class);
        } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_DELETE_COMPOSITE)) {
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    if (null != this.compositeDO.getTransactionTypeUserEntered()) {
                        if (this.compositeDO.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.COMPOSITE_CREATE)) {
                            this.tempCompositeDO = new CompositeDO();
                        } else {
                            this.tempCompositeDO.setTransactionTypeUserEntered(ChangeRequestConstants.COMPOSITE_DELETE);
                        }
                    } else {
                        this.tempCompositeDO.setTransactionTypeUserEntered(ChangeRequestConstants.COMPOSITE_DELETE);
                    }
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    if (null != this.compositeDO.getTransactionTypeFinal()) {
                        if (this.compositeDO.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.COMPOSITE_CREATE)) {
                            this.tempCompositeDO = new CompositeDO();
                        } else {
                            this.tempCompositeDO.setTransactionTypeFinal(ChangeRequestConstants.COMPOSITE_DELETE);
                        }
                    } else {
                        this.tempCompositeDO.setTransactionTypeFinal(ChangeRequestConstants.COMPOSITE_DELETE);
                    }
                }
            }
            this.compositeDO = (CompositeDO)JSONUtils.fromJson(JSONUtils.toJson(this.tempCompositeDO), CompositeDO.class);
        }

        // Create th mesage to display to the user
        CreateTransactionMessage(rpiType);

        logger.warning("Composite after save: " + JSONUtils.toJson(this.compositeDO));

        return returnValue;
    }

    public void CancelComposite() {
        //logger.warning("Canceling composite with transaction type: " + getTransactionType());

        // If we are canceling the composite then we need to reset the values
        // This is a saftey instantiation, but not neccessary
        this.tempCompositeDO = new CompositeDO();
    }

    public void AddLand() {
        //logger.warning("Adding new land data");
        setTransactionType(ChangeRequestConstants.TRANSACTION_CREATE_LAND);
        ClearTransactionMessage();

        // Create a new object to use for the land addition when there isn't one on the real property info
        // There was no original land data so we don't need to worry about preserving anything
        this.tempLandDO = new LandDO();
        this.tempLandDO.setDbUniqueId(ChangeRequestConstants.CREATE_LAND_PREFIX + generateUniquieID(8));
        this.tempLandDO.setDbUniqueIdUserEntered(this.tempLandDO.getDbUniqueId());
        // This is used for adding from BPM, It does no harm for the portal UI where we work with UserEntered information
        this.tempLandDO.setDbUniqueIdFinal(this.tempLandDO.getDbUniqueId());
        this.tempLandDO.setAin(this.originalRPI.getAin());
        this.tempLandDO.setAoid(this.originalRPI.getAoid());

        logger.warning("Temp Land data staged to add: " + JSONUtils.toJson(this.tempLandDO));
    }

    public void EditLand(String landID) {
        //logger.warning("Editing land data with ID: " + landID);
        setTransactionType(ChangeRequestConstants.TRANSACTION_EDIT_LAND);
        ClearTransactionMessage();

        if (null != this.landDO) {
            // get the the land to edit
            if (null != this.landDO.getDbUniqueId()) {
                if (this.landDO.getDbUniqueId().equalsIgnoreCase(landID)) {
                    // if we have valid land data then make a copy for the edit
                    this.tempLandDO = (LandDO)JSONUtils.fromJson(JSONUtils.toJson(this.landDO), LandDO.class);
                } else {
                    logger.warning("Something went wrong! The landID did not match what we have in the DB");
                }
            } else {
                logger.warning("No land data, adding new land data line ");
                AddLand();
            }
        } else {
            logger.warning("No land data, adding new land data line ");
            AddLand();
        }
        logger.warning("Temp Land data staged to edited: " + JSONUtils.toJson(this.tempLandDO));
    }

    public String SaveLand(String rpiType) {
        //logger.warning("Saving land data with ID: " + landID + " With transaction type: " + getTransactionType());
        String returnValue = ChangeRequestConstants.SAVE_SUCESS;

        if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_CREATE_LAND)) {
            // Need to determine the subpart type so we add the correct transaction, and sub part number to display
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    this.tempLandDO.setTransactionTypeUserEntered(ChangeRequestConstants.SUBPART_CREATE);
                    if (null != this.tempLandDO.getViewCodeKeyUserEntered()) {
                        this.tempLandDO.setViewCodeDisplayUserEntered(setViewCodeDisplay(rpiType));
                        this.tempLandDO.setViewCodeRefUserEntered(setViewCodeRef(rpiType));
                        this.tempLandDO.setViewCodeLegendUserEntered(this.tempLandDO.getViewCodeDisplayUserEntered());
                    }
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    this.tempLandDO.setTransactionTypeFinal(ChangeRequestConstants.SUBPART_CREATE);
                    if (null != this.tempLandDO.getViewCodeKeyFinal()) {
                        this.tempLandDO.setViewCodeDisplayFinal(setViewCodeDisplay(rpiType));
                        this.tempLandDO.setViewCodeRefFinal(setViewCodeRef(rpiType));
                        this.tempLandDO.setViewCodeLegendFinal(this.tempLandDO.getViewCodeDisplayFinal());
                    }
                }
            }
            this.landDO = (LandDO)JSONUtils.fromJson(JSONUtils.toJson(this.tempLandDO), LandDO.class);
        } else if (getTransactionType().equalsIgnoreCase(ChangeRequestConstants.TRANSACTION_EDIT_LAND)) {
            // Check to see if we have previously worked on this land data
            if (null != rpiType) {
                if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
                    if (null != this.landDO.getTransactionTypeUserEntered()) {
                        // if we are editing land data that was originally added then do not override the transaction type
                        if (!this.landDO.getTransactionTypeUserEntered().equalsIgnoreCase(ChangeRequestConstants.LAND_CREATE)) {
                            this.tempLandDO.setTransactionTypeUserEntered(ChangeRequestConstants.LAND_UPDATE);
                        }
                    } else {
                        this.tempLandDO.setTransactionTypeUserEntered(ChangeRequestConstants.LAND_UPDATE);
                    }
                    if (null != this.tempLandDO.getViewCodeKeyUserEntered()) {
                        this.tempLandDO.setViewCodeDisplayUserEntered(setViewCodeDisplay(rpiType));
                        this.tempLandDO.setViewCodeRefUserEntered(setViewCodeRef(rpiType));
                        this.tempLandDO.setViewCodeLegendUserEntered(this.tempLandDO.getViewCodeDisplayUserEntered());
                    }
                } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
                    if (null != this.landDO.getTransactionTypeFinal()) {
                        // if we are editing land data that was originally added then do not override the transaction type
                        if (!this.landDO.getTransactionTypeFinal().equalsIgnoreCase(ChangeRequestConstants.LAND_CREATE)) {
                            this.tempLandDO.setTransactionTypeFinal(ChangeRequestConstants.LAND_UPDATE);
                        }
                    } else {
                        this.tempLandDO.setTransactionTypeFinal(ChangeRequestConstants.LAND_UPDATE);
                    }
                    if (null != this.tempLandDO.getViewCodeKeyFinal()) {
                        this.tempLandDO.setViewCodeDisplayFinal(setViewCodeDisplay(rpiType));
                        this.tempLandDO.setViewCodeRefFinal(setViewCodeRef(rpiType));
                        this.tempLandDO.setViewCodeLegendFinal(this.tempLandDO.getViewCodeDisplayFinal());
                    }
                }
            }
            this.landDO = (LandDO)JSONUtils.fromJson(JSONUtils.toJson(this.tempLandDO), LandDO.class);
        }

        logger.warning("Land data after save: " + JSONUtils.toJson(this.landDO));

        return returnValue;
    }

    private String translateViewCodeDisplay(RealPropertyInfoDO rpi) {
        //translate the ViewCode retrieved to the proper value for UI
        String returnViewCodeDisplay = "";
        // Get all the possible values of None
        String viewCodes = JSONUtils.toJson(ChangeRequestConstants.VIEW_CODE_NONE);

        if (null != rpi.getLand().getViewCodeKey()) {
            if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CANYON)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CANYON_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY_CANYON)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_CANYON_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CANYON)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CANYON_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON_DISPLAY;
            } else if (rpi.getLand().getViewCodeKey().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_YES)) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_YES_DISPLAY;
            } else if (viewCodes.contains(rpi.getLand().getViewCodeKey())) {
                returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_NONE_DISPLAY;
            }
        }
        return returnViewCodeDisplay;
    }

    private String setViewCodeDisplay(String rpiType) {
        //translate the ViewCode retrieved to the proper value for UI
        String returnViewCodeDisplay = "";
        // Get all the possible values of None
        String viewCodes = JSONUtils.toJson(ChangeRequestConstants.VIEW_CODE_NONE);

        if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
            if (null != this.tempLandDO.getViewCodeKeyUserEntered()) {
                if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_YES)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_YES_DISPLAY;
                } else if (viewCodes.contains(this.tempLandDO.getViewCodeKeyUserEntered())) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_NONE_DISPLAY;
                }
            }
        } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
            if (null != this.tempLandDO.getViewCodeKeyFinal()) {
                if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_CITY_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON_DISPLAY;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_YES)) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_YES_DISPLAY;
                } else if (viewCodes.contains(this.tempLandDO.getViewCodeKeyFinal())) {
                    returnViewCodeDisplay = ChangeRequestConstants.VIEW_CODE_NONE_DISPLAY;
                }
            }
        }
        return returnViewCodeDisplay;
    }

    private BigInteger setViewCodeRef(String rpiType) {
        //translate the ViewCode retrieved to the proper value for UI
        String returnViewCodeRef = "";
        // Get all the possible values of None
        String viewCodes = JSONUtils.toJson(ChangeRequestConstants.VIEW_CODE_NONE);

        if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
            if (null != this.tempLandDO.getViewCodeKeyUserEntered()) {
                if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CITY_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CITY_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CITY_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyUserEntered().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_YES)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_YES_REF;
                } else if (viewCodes.contains(this.tempLandDO.getViewCodeKeyUserEntered())) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_NONE_REF;
                }
            }
        } else if (rpiType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
            if (null != this.tempLandDO.getViewCodeKeyFinal()) {
                if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CITY_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_CITY_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_CITY_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CITY_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_WATER_CITY_CANYON_REF;
                } else if (this.tempLandDO.getViewCodeKeyFinal().equalsIgnoreCase(ChangeRequestConstants.VIEW_CODE_YES)) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_YES_REF;
                } else if (viewCodes.contains(this.tempLandDO.getViewCodeKeyFinal())) {
                    returnViewCodeRef = ChangeRequestConstants.VIEW_CODE_NONE_REF;
                }
            }
        }
        if ("".equalsIgnoreCase(returnViewCodeRef))
            return null;
        else
            return new BigInteger(returnViewCodeRef);
    }

    public void CancelLand() {
        //logger.warning("Canceling land data with transaction type: " + getTransactionType());

        // If we are canceling the land data then we need to reset the values
        // This is a saftey instantiation, but not neccessary
        this.tempLandDO = new LandDO();
    }

    public void CancelTrasaction() {
        // Reset the objects when the edit or create are canceled
        //logger.warning("Canceling EditPDCR transaction");

        // Remove the temporary objects when the user selects to cancel the transaction
        this.subPartListDO = new ArrayList<SubPartDO>();
        this.tempSubPartDO = new SubPartDO();
        this.subPartListDO.add(this.tempSubPartDO);
        this.compositeDO = new CompositeDO();
        this.tempCompositeDO = new CompositeDO();
        this.landDO = new LandDO();
        this.tempLandDO = new LandDO();
    }

    public void setOriginalRPI(RealPropertyInfoDO originalRPI) {
        this.originalRPI = originalRPI;
    }

    public RealPropertyInfoDO getOriginalRPI() {
        return originalRPI;
    }

    public void setUserEnteredRPI(RealPropertyInfoDO userRPI) {
        this.userEnteredRPI = userRPI;
    }

    public RealPropertyInfoDO getUserEnteredRPI() {
        return userEnteredRPI;
    }

    public void setRequestorInfoDO(RequestorInfoDO requesterInfoDO) {
        this.requestorInfoDO = requesterInfoDO;
    }

    public RequestorInfoDO getRequestorInfoDO() {
        return requestorInfoDO;
    }

    public void setCompositeDO(CompositeDO compositeDO) {
        this.compositeDO = compositeDO;
    }

    public CompositeDO getCompositeDO() {
        return compositeDO;
    }

    public void setLandDO(LandDO landDO) {
        this.landDO = landDO;
    }

    public LandDO getLandDO() {
        return landDO;
    }

    public void setSubPartListDO(List<SubPartDO> subPartListDO) {
        this.subPartListDO = subPartListDO;
    }

    public List<SubPartDO> getSubPartListDO() {
        return subPartListDO;
    }

    public void setSubPartCount(Integer subPartCount) {
        this.subPartCount = subPartCount;
    }

    public Integer getSubPartCount() {
        return subPartCount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setFinalRPI(RealPropertyInfoDO finalRPI) {
        this.finalRPI = finalRPI;
    }

    public RealPropertyInfoDO getFinalRPI() {
        return finalRPI;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public String getAin() {
        return ain;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setTempCompositeDO(CompositeDO tempCompositeDO) {
        this.tempCompositeDO = tempCompositeDO;
    }

    public CompositeDO getTempCompositeDO() {
        return tempCompositeDO;
    }

    public void setTempLandDO(LandDO tempLandDO) {
        this.tempLandDO = tempLandDO;
    }

    public LandDO getTempLandDO() {
        return tempLandDO;
    }

    public void setTempSubPartDO(SubPartDO tempSubPartDO) {
        this.tempSubPartDO = tempSubPartDO;
    }

    public SubPartDO getTempSubPartDO() {
        return tempSubPartDO;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setIsPDCRInitiated(Boolean isPDCRInitiated) {
        this.isPDCRInitiated = isPDCRInitiated;
    }

    public Boolean getIsPDCRInitiated() {
        return null == isPDCRInitiated ? Boolean.FALSE : isPDCRInitiated;
    }

    public void setIsRetrieveSuccess(Boolean isRetrieveSuccess) {
        this.isRetrieveSuccess = isRetrieveSuccess;
    }

    public Boolean getIsRetrieveSuccess() {
        return null == isRetrieveSuccess ? Boolean.FALSE : isRetrieveSuccess;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setIsInitiateSuccess(Boolean isInitiateSuccess) {
        this.isInitiateSuccess = isInitiateSuccess;
    }

    public Boolean getIsInitiateSuccess() {
        return null == isInitiateSuccess ? Boolean.FALSE : isInitiateSuccess;
    }

    protected Object loadData(Object obj) {
        return null;
    }

    public void setIsPDCREmpty(Boolean isPDCREmpty) {
        this.isPDCREmpty = isPDCREmpty;
    }

    public Boolean getIsPDCREmpty() {
        return isPDCREmpty;
    }

    public void setOriginalSubPartCount(Integer originalSubPartCount) {
        this.originalSubPartCount = originalSubPartCount;
    }

    public Integer getOriginalSubPartCount() {
        return originalSubPartCount;
    }

    public void setAoid(BigInteger aoid) {
        this.aoid = aoid;
    }

    public BigInteger getAoid() {
        return aoid;
    }

    public void setAinDisplay(String ainDisplay) {
        this.ainDisplay = ainDisplay;
    }

    public String getAinDisplay() {
        return ainDisplay;
    }

    public void setUseTypeIcon(String useTypeIcon) {
        this.useTypeIcon = useTypeIcon;
    }

    public String getUseTypeIcon() {
        return useTypeIcon;
    }

    public ResourceBundle getPdcrBundle() {
        return pdcrBundle;
    }

    public void setIsValidDistrict(Boolean isValidDistrict) {
        this.isValidDistrict = isValidDistrict;
    }

    public Boolean getIsValidDistrict() {
        return isValidDistrict;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setIsShowDisaplyMessage(Boolean isShowDisaplyMessage) {
        this.isShowDisaplyMessage = isShowDisaplyMessage;
    }

    public Boolean getIsShowDisaplyMessage() {
        return isShowDisaplyMessage;
    }

    public void setIsNoLand(Boolean isNoLand) {
        this.isNoLand = isNoLand;
    }

    public Boolean getIsNoLand() {
        return isNoLand;
    }
}
