package gov.laca.amp.pdcr.common.model.facade;

import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.fwk.extn.facade.AmpProxyFacade;

import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.pdcr.common.model.AmpChangeRequestException;

import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;
import gov.laca.amp.pdcr.common.model.service.ChangeRequestServiceWrapper;
import gov.laca.amp.pdcr.common.model.service.AINLockCheckServiceWrapper;

import gov.laca.amp.pdcr.common.model.data.RealPropertyInfoDO;
import gov.laca.amp.pdcr.common.model.data.RequestorInfoDO;
import gov.laca.amp.pdcr.common.model.data.LockCheckDO;

import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.RealPropertyInfo;

import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.InitiatePropertyDataCRRequest.RequestorInfo;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.SourceType;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.UserType;

import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockResponse;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;


public class ChangeRequestFacade extends AmpProxyFacade {

    // The privae syncronized variables for the services
    private static ChangeRequestServiceWrapper pdcrService = null;
    private static AINLockCheckServiceWrapper checkLockervice = null;

    private static synchronized ChangeRequestServiceWrapper getPDCRService() {
        if (null == pdcrService) {
            pdcrService = new ChangeRequestServiceWrapper();
        }
        return pdcrService;
    }

    private static synchronized AINLockCheckServiceWrapper getAinLockService() {
        if (null == checkLockervice) {
            checkLockervice = new AINLockCheckServiceWrapper();
        }
        return checkLockervice;
    }

    public static LockCheckDO CheckLock(String ain, BigInteger aoid, String userId, String clientId, String processType) throws AmpChangeRequestException {
        // Transform the lock check response to the LockCheckDO used in the PDCR data model
        String applicationId = ChangeRequestConstants.HEADER_APPLICATION_ID;

        // initialize the data model object
        LockCheckDO lockCheckDO = new LockCheckDO();

        // Initieate the SOA service to get the lock status for the BPM process
        CheckLockResponse checkLockResponse = getAinLockService().CheckLock(applicationId, ain, aoid, userId, clientId, processType);

        // Populate the LockCheckDO
        lockCheckDO.setAin(checkLockResponse.getAin());
        lockCheckDO.setAoid(checkLockResponse.getAoid());
        lockCheckDO.setInitiatedBy(checkLockResponse.getInitiatedBy());
        lockCheckDO.setLockStatus(checkLockResponse.getLockStatus());
        lockCheckDO.setProcessType(checkLockResponse.getProcessType());

        return lockCheckDO;
    }

    public static RealPropertyInfoDO RetrieveRealPropertyInfo(String ain, String dataSource, String realPropertyType) throws AmpChangeRequestException {
        // Transform the real property information retrieved from the data source passed
        String applicationId = ChangeRequestConstants.HEADER_APPLICATION_ID;

        // Initialize the real property information
        RealPropertyInfoDO rpiDO = new RealPropertyInfoDO();
        RealPropertyInfo rpiSO = null;

        // Check to see where we are getting the real propery information SOA or BPM
        if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_SOA)) {
            // Retrieve the real propery information from SOA
            // This is a list, so if there are more than one then just convert the first one retrieved
            rpiSO = getPDCRService().RetrieveRealPropertyInfo(applicationId, ain, null).getRealPropertyInfoList().getRealPropertyInfo().get(0);
            // Convert the SOA objects to the POJO DO
            rpiDO = transformRealPropertyInfoDO(rpiSO);
        } else if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
            // Retrieve the real propery information from BPM
            // Make sure we have the proper real property type info
            rpiDO = copyFromBPM(realPropertyType);
        }

        return rpiDO;
    }

    public static String InitiatePropertyDataCR(String ain, BigInteger aoid, String userID, String userType, String sourceType, String dataSource,
                                                String actionType, String processType, RequestorInfoDO requestorInfoDO, RealPropertyInfoDO originalRPIDO,
                                                RealPropertyInfoDO userEnteredRPIDO, RealPropertyInfoDO finalRPIDO) throws AmpChangeRequestException {
        // Transform the real property information into the data used to initiatetiate the PDCR process
        String bpmStatus = null;

        // Check to see where we are getting the real propery information SOA or BPM
        if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_SOA)) {
            String applicationId = ChangeRequestConstants.HEADER_APPLICATION_ID;

            // Convert the strings being passed in, to an enum for the service
            SourceType sourceTypeEnum = SourceType.fromValue(sourceType);
            UserType userTypeEnum = UserType.fromValue(userType);

            // Initialize the data objects to be sent to the SOA service
            RealPropertyInfo originalRPISO = new RealPropertyInfo();
            RealPropertyInfo userEnteredRPISO = new RealPropertyInfo();
            RealPropertyInfo finalRPISO = new RealPropertyInfo();
            RequestorInfo requestorInfoSO = new RequestorInfo();

            // Convert the POJO DO to the objects sent to the SOA service
            originalRPISO = transformRealPropertyInfoSO(originalRPIDO);
            userEnteredRPISO = transformRealPropertyInfoSO(userEnteredRPIDO);
            finalRPISO = transformRealPropertyInfoSO(finalRPIDO);
            requestorInfoSO = transformRequestorInfoSO(requestorInfoDO);

            bpmStatus =
                    getPDCRService().InitiatePropertyDataCR(applicationId, ain, aoid, userID, userTypeEnum, sourceTypeEnum, actionType, processType, requestorInfoSO,
                                                            originalRPISO, userEnteredRPISO, finalRPISO).getBpmStatus();
        } else if (dataSource.equalsIgnoreCase(ChangeRequestConstants.SOURCE_BPM)) {
            copyToBPM(finalRPIDO);
            bpmStatus = ChangeRequestConstants.BPM_STATUS_SUBMITTED;
        }

        return bpmStatus;
    }

    private static RealPropertyInfoDO transformRealPropertyInfoDO(RealPropertyInfo rpiSO) {
        // Transform the SOA data object to the POJO for the Data model used in the UI
        RealPropertyInfoDO rpiDO = (RealPropertyInfoDO)JSONUtils.fromJson(JSONUtils.toJson(rpiSO), RealPropertyInfoDO.class);
        return rpiDO;
    }

    private static RealPropertyInfo transformRealPropertyInfoSO(RealPropertyInfoDO rpiDO) {
        // Transform the POJO from the Data model to the SOA data object to send to the service
        RealPropertyInfo rpiSO = (RealPropertyInfo)JSONUtils.fromJson(JSONUtils.toJson(rpiDO), RealPropertyInfo.class);
        return rpiSO;
    }

    private static RequestorInfo transformRequestorInfoSO(RequestorInfoDO requestorInfoDO) {
        // Transform the POJO from the Data model to the SOA data object to send to the service
        RequestorInfo requestorInfoSO = (RequestorInfo)JSONUtils.fromJson(JSONUtils.toJson(requestorInfoDO), RequestorInfo.class);
        return requestorInfoSO;
    }

    private static RealPropertyInfoDO copyFromBPM(String realPropertyType) {
        // Set the variables for getting the proper real property inform
        DCIteratorBinding rpiIterBinding = null;
        DCIteratorBinding spIterBinding = null;
        DCIteratorBinding compIterBinding = null;
        DCIteratorBinding landIterBinding = null;

        RealPropertyInfoDO rpiDO = new RealPropertyInfoDO();

        if (realPropertyType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_ORIGINAL)) {
            rpiIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_ORIGINAL_ITERATOR);
            spIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_ORIGINAL_SUBPART_ITERATOR);
            compIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_ORIGINAL_COMPOSITE_ITERATOR);
            landIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_ORIGINAL_LAND_ITERATOR);
        } else if (realPropertyType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_USER_ENTERED)) {
            rpiIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_USER_ENTERED_ITERATOR);
            spIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_USER_ENTERED_SUBPART_ITERATOR);
            compIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_USER_ENTERED_COMPOSITE_ITERATOR);
            landIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_USER_ENTERED_LAND_ITERATOR);
        } else if (realPropertyType.equalsIgnoreCase(ChangeRequestConstants.REALPROPERTY_TYPE_FINAL)) {
            rpiIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_ITERATOR);
            spIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_SUBPART_ITERATOR);
            compIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_COMPOSITE_ITERATOR);
            landIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_LAND_ITERATOR);
        }

        Row rpiRow = rpiIterBinding.getRowAtRangeIndex(0);
        if (null != rpiRow) {
            Row compRow = compIterBinding.getRowAtRangeIndex(0);
            Row landRow = landIterBinding.getRowAtRangeIndex(0);
            transformRealPropertyDO(rpiDO, rpiRow, spIterBinding, compRow, landRow);
        }

        return rpiDO;
    }

    private static void transformRealPropertyDO(RealPropertyInfoDO rpiDO, Row rpiRow, DCIteratorBinding spIterBinding, Row compRow, Row landRow) {
        // Transform the property info
        rpiDO.setAin((String)rpiRow.getAttribute("ain"));
        rpiDO.setAoid((BigInteger)rpiRow.getAttribute("aoid"));
        rpiDO.setCluster((String)rpiRow.getAttribute("cluster"));
        rpiDO.setDistrict((String)rpiRow.getAttribute("district"));
        rpiDO.setPropertyAddress((String)rpiRow.getAttribute("propertyAddress"));
        rpiDO.setRegion((String)rpiRow.getAttribute("region"));

        // Transform the Subpart data
        RealPropertyInfoDO.SubPartList subPartListDO = new RealPropertyInfoDO.SubPartList();
        for (int i = 0; i < spIterBinding.getViewObject().getEstimatedRowCount(); i++) {
            RealPropertyInfoDO.SubPartList.SubPart subPartDO = new RealPropertyInfoDO.SubPartList.SubPart();
            Row row = spIterBinding.getRowAtRangeIndex(i);
            if (row != null) {
                subPartDO.setAddedDepreciationPercent((BigDecimal)row.getAttribute("addedDepreciationPercent"));
                subPartDO.setAddedDepreciationTypeKey((String)row.getAttribute("addedDepreciationTypeKey"));
                subPartDO.setAddedDepreciationTypeLegend((String)row.getAttribute("addedDepreciationTypeLegend"));
                subPartDO.setAddedDepreciationTypeRef((BigInteger)row.getAttribute("addedDepreciationTypeRef"));
                subPartDO.setAin((String)row.getAttribute("ain"));
                subPartDO.setAoid((BigInteger)row.getAttribute("aoid"));
                subPartDO.setBathroomCount((BigInteger)row.getAttribute("bathroomCount"));
                subPartDO.setBedroomCount((BigInteger)row.getAttribute("bedroomCount"));
                subPartDO.setCostFactor((Double)row.getAttribute("costFactor"));
                subPartDO.setDbUniqueId((String)row.getAttribute("dbUniqueId"));
                subPartDO.setDepreciationTable((String)row.getAttribute("depreciationTable"));
                subPartDO.setDesignTypeKey((String)row.getAttribute("designTypeKey"));
                subPartDO.setDesignTypeLegend((String)row.getAttribute("designTypeLegend"));
                subPartDO.setDesignTypeRef((BigInteger)row.getAttribute("designTypeRef"));
                subPartDO.setEffectiveYearBuilt((BigInteger)row.getAttribute("effectiveYearBuilt"));
                subPartDO.setImprovementNumber((BigInteger)row.getAttribute("improvementNumber"));
                subPartDO.setImprovementSizeSqft((Double)row.getAttribute("improvementSizeSqft"));
                subPartDO.setNumberOfUnits((BigInteger)row.getAttribute("numberOfUnits"));
                subPartDO.setPercentGood((BigDecimal)row.getAttribute("percentGood"));
                subPartDO.setQualityClass((String)row.getAttribute("qualityClass"));
                subPartDO.setRcnLessDepreciation((Double)row.getAttribute("rcnLessDepreciation"));
                subPartDO.setRcnMain((Double)row.getAttribute("rcnMain"));
                subPartDO.setRcnOtherTotal((Double)row.getAttribute("rcnOtherTotal"));
                subPartDO.setRcnYearChange((BigInteger)row.getAttribute("rcnYearChange"));
                subPartDO.setSubPartNumber((String)row.getAttribute("subPartNumber"));
                subPartDO.setTransactionType((String)row.getAttribute("transactionType"));
                subPartDO.setYearBuilt((BigInteger)row.getAttribute("yearBuilt"));
                subPartListDO.getSubPart().add(subPartDO);
            }
        }
        rpiDO.setSubPartList(subPartListDO);

        // Transform the composite Data
        if (null != compRow) {
            RealPropertyInfoDO.Composite compositeDO = new RealPropertyInfoDO.Composite();
            compositeDO.setAin((String)compRow.getAttribute("ain"));
            compositeDO.setAoid((BigInteger)compRow.getAttribute("aoid"));
            compositeDO.setDbUniqueId((String)compRow.getAttribute("dbUniqueId"));
            compositeDO.setSqft((Double)compRow.getAttribute("sqft"));
            compositeDO.setTransactionType((String)compRow.getAttribute("transactionType"));
            rpiDO.setComposite(compositeDO);
        }

        // Transform the land data
        if (null != landRow) {
            RealPropertyInfoDO.Land landDO = new RealPropertyInfoDO.Land();
            landDO.setAin((String)landRow.getAttribute("ain"));
            landDO.setAoid((BigInteger)landRow.getAttribute("aoid"));
            landDO.setCodeSplit((String)landRow.getAttribute("codeSplit"));
            landDO.setCorner((String)landRow.getAttribute("corner"));
            landDO.setDbUniqueId((String)landRow.getAttribute("dbUniqueId"));
            landDO.setDepth((Double)landRow.getAttribute("depth"));
            landDO.setFlightPath((String)landRow.getAttribute("flightPath"));
            landDO.setFreewayProximity((String)landRow.getAttribute("freewayProximity"));
            landDO.setGolfFront((String)landRow.getAttribute("golfFront"));
            landDO.setHorses((String)landRow.getAttribute("horses"));
            landDO.setSewer((String)landRow.getAttribute("sewer"));
            landDO.setSizeAcreAge((Double)landRow.getAttribute("sizeAcreAge"));
            landDO.setSizeSqft((Double)landRow.getAttribute("sizeSqft"));
            landDO.setSizeUsableSqft((Double)landRow.getAttribute("sizeUsableSqft"));
            landDO.setTraffic((String)landRow.getAttribute("traffic"));
            landDO.setTransactionType((String)landRow.getAttribute("transactionType"));
            landDO.setUseCode((String)landRow.getAttribute("useCode"));
            landDO.setUseType((String)landRow.getAttribute("useType"));
            landDO.setViewCodeKey((String)landRow.getAttribute("viewCodeKey"));
            landDO.setViewCodeLegend((String)landRow.getAttribute("viewCodeLegend"));
            landDO.setViewCodeRef((BigInteger)landRow.getAttribute("viewCodeRef"));
            landDO.setWidth((Double)landRow.getAttribute("width"));
            rpiDO.setLand(landDO);
        }
    }

    private static void copyToBPM(RealPropertyInfoDO rpiDO) {
        // Set the variables for setting the proper real property information
        DCIteratorBinding spIterBinding = null;
        DCIteratorBinding compIterBinding = null;
        DCIteratorBinding landIterBinding = null;

        spIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_SUBPART_ITERATOR);
        compIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_COMPOSITE_ITERATOR);
        landIterBinding = ADFUtils.findIterator(ChangeRequestConstants.BPM_FINAL_LAND_ITERATOR);

        Row compRow = compIterBinding.getRowAtRangeIndex(0);
        Row landRow = landIterBinding.getRowAtRangeIndex(0);
        transformRealPropertyBPM(rpiDO, spIterBinding, compRow, landRow);
    }

    private static void transformRealPropertyBPM(RealPropertyInfoDO rpiDO, DCIteratorBinding spIterBinding, Row compRow, Row landRow) {
        // reset the iterator
        resetSubParts(spIterBinding);

        // loop throught the supbarts in the finalRPI, and add the current rows
        if (null != rpiDO.getSubPartList()) {
            if (null != rpiDO.getSubPartList().getSubPart()) {
                for (RealPropertyInfoDO.SubPartList.SubPart sp : rpiDO.getSubPartList().getSubPart()) {
                    Row spRow = spIterBinding.getNavigatableRowIterator().createRow();
                    spRow.setAttribute("addedDepreciationPercent", sp.getAddedDepreciationPercent());
                    spRow.setAttribute("addedDepreciationTypeKey", sp.getAddedDepreciationTypeKey());
                    spRow.setAttribute("addedDepreciationTypeLegend", sp.getAddedDepreciationTypeLegend());
                    spRow.setAttribute("addedDepreciationTypeRef", sp.getAddedDepreciationTypeRef());
                    spRow.setAttribute("ain", sp.getAin());
                    spRow.setAttribute("aoid", sp.getAoid());
                    spRow.setAttribute("bathroomCount", sp.getBathroomCount());
                    spRow.setAttribute("bedroomCount", sp.getBedroomCount());
                    spRow.setAttribute("costFactor", sp.getCostFactor());
                    spRow.setAttribute("dbUniqueId", sp.getDbUniqueId());
                    spRow.setAttribute("depreciationTable", sp.getDepreciationTable());
                    spRow.setAttribute("designTypeKey", sp.getDesignTypeKey());
                    spRow.setAttribute("designTypeLegend", sp.getDesignTypeLegend());
                    spRow.setAttribute("designTypeRef", sp.getDesignTypeRef());
                    spRow.setAttribute("effectiveYearBuilt", sp.getEffectiveYearBuilt());
                    spRow.setAttribute("improvementNumber", sp.getImprovementNumber());
                    spRow.setAttribute("improvementSizeSqft", sp.getImprovementSizeSqft());
                    spRow.setAttribute("numberOfUnits", sp.getNumberOfUnits());
                    spRow.setAttribute("percentGood", sp.getPercentGood());
                    spRow.setAttribute("qualityClass", sp.getQualityClass());
                    spRow.setAttribute("rcnLessDepreciation", sp.getRcnLessDepreciation());
                    spRow.setAttribute("rcnMain", sp.getRcnMain());
                    spRow.setAttribute("rcnOtherTotal", sp.getRcnOtherTotal());
                    spRow.setAttribute("rcnYearChange", sp.getRcnYearChange());
                    spRow.setAttribute("subPartNumber", sp.getSubPartNumber());
                    spRow.setAttribute("transactionType", sp.getTransactionType());
                    spRow.setAttribute("yearBuilt", sp.getYearBuilt());
                }
            }
        }

        // Transform the composite Data
        if (null != rpiDO.getComposite()) {
            compRow.setAttribute("ain", rpiDO.getComposite().getAin());
            compRow.setAttribute("aoid", rpiDO.getComposite().getAoid());
            compRow.setAttribute("dbUniqueId", rpiDO.getComposite().getDbUniqueId());
            compRow.setAttribute("sqft", rpiDO.getComposite().getSqft());
            compRow.setAttribute("transactionType", rpiDO.getComposite().getTransactionType());
        }

        // Transform the land data
        if (null != rpiDO.getLand()) {
            landRow.setAttribute("ain", rpiDO.getLand().getAin());
            landRow.setAttribute("aoid", rpiDO.getLand().getAoid());
            landRow.setAttribute("codeSplit", rpiDO.getLand().getCodeSplit());
            landRow.setAttribute("corner", rpiDO.getLand().getCorner());
            landRow.setAttribute("dbUniqueId", rpiDO.getLand().getDbUniqueId());
            landRow.setAttribute("depth", rpiDO.getLand().getDepth());
            landRow.setAttribute("flightPath", rpiDO.getLand().getFlightPath());
            landRow.setAttribute("freewayProximity", rpiDO.getLand().getFreewayProximity());
            landRow.setAttribute("golfFront", rpiDO.getLand().getGolfFront());
            landRow.setAttribute("horses", rpiDO.getLand().getHorses());
            landRow.setAttribute("sewer", rpiDO.getLand().getSewer());
            landRow.setAttribute("sizeAcreAge", rpiDO.getLand().getSizeAcreAge());
            landRow.setAttribute("sizeSqft", rpiDO.getLand().getSizeSqft());
            landRow.setAttribute("sizeUsableSqft", rpiDO.getLand().getSizeUsableSqft());
            landRow.setAttribute("traffic", rpiDO.getLand().getTraffic());
            landRow.setAttribute("transactionType", rpiDO.getLand().getTransactionType());
            landRow.setAttribute("useCode", rpiDO.getLand().getUseCode());
            landRow.setAttribute("useType", rpiDO.getLand().getUseType());
            landRow.setAttribute("viewCodeKey", rpiDO.getLand().getViewCodeKey());
            landRow.setAttribute("viewCodeLegend", rpiDO.getLand().getViewCodeLegend());
            landRow.setAttribute("viewCodeRef", rpiDO.getLand().getViewCodeRef());
            landRow.setAttribute("width", rpiDO.getLand().getWidth());
        }
    }

    private static void resetSubParts(DCIteratorBinding spIterBinding) {
        // Get the RowSetIterator Object
        RowSetIterator rsi = spIterBinding.getViewObject().createRowSetIterator(null);
        rsi.reset();

        //delete all rows
        while (rsi.hasNext()) {
            Row current = rsi.next();
            rsi.setCurrentRow(current);
            current.remove();
        }
        rsi.closeRowSetIterator();
    }

}

