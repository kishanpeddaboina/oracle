package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.WebServiceEndPoints;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.BaseYearEventMgmtService;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.BaseYearEventMgmtService_ep;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.BaseYearEvent;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.ChangedBaseYearEventsRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.ChangedBaseYearEventsResponse;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.Fault;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.InitiateBaseYearEventRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.InitiateBaseYearEventResponse;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.RequestHeader;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.RetrieveBaseYearEventsRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.RetrieveBaseYearEventsResponse;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.UpsertBaseYearEventList;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.UpsertBaseYearEventsRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.UpsertBaseYearEventsResponse;

import java.math.BigInteger;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


/**
 * @author Vijay Redla
 * @version 2.0
 */
public class BaseYearEventsMgmtWrapper {
    public static final AmpLogger LOGGER =
        new AmpLogger(BaseYearEventsMgmtWrapper.class);
    private BaseYearEventMgmtService_ep baseYearEventMgmtService_ep = null;
    private List<BaseYearEvent> changedEvents;

    public BaseYearEventsMgmtWrapper() {
        super();
    }

    /**
     * @param input
     * @param ain
     * @param userId
     * @throws AmpBaseYearEventException
     */
    public Boolean retrieveEventsFromService(ArrayList<BaseYearEventDO> input,
                                             AinDO ainDO, String ain,
                                             String userId,
                                             BigInteger aoid) throws AmpBaseYearEventException {
        BaseYearEventMgmtService baseYearEventMgmtService = getServiceHandle();
        // Add your code to call the desired methods.
        RetrieveBaseYearEventsRequest baseYearEventsRequest =
            new RetrieveBaseYearEventsRequest();
        RequestHeader header = new RequestHeader();
        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        header.setRequestId(BaseYearEventUtils.getRequestID());
        baseYearEventsRequest.setHeader(header);
        baseYearEventsRequest.setAin(ain);
        baseYearEventsRequest.setAoid(aoid);
        baseYearEventsRequest.setUserId(userId);
        RetrieveBaseYearEventsResponse byeResponse;
        try {
            byeResponse =
                    baseYearEventMgmtService.retrieveBaseYearEvents(baseYearEventsRequest);
            ainDO.setAin(byeResponse.getAin());
            ainDO.setAoid(byeResponse.getAoid());
            ainDO.setClusterRoute(byeResponse.getClusterRoute());
            ainDO.setClusterRouteId(byeResponse.getClusterRouteId());
            ainDO.setDbLockStatus(byeResponse.getDbLockStatus());
            ainDO.setDistrictId(byeResponse.getDistrictId());
            ainDO.setDistrictName(byeResponse.getDistrictName());
            ainDO.setEcid(byeResponse.getEcid());
            ainDO.setLockedByUserId(byeResponse.getLockedByUserId());
            ainDO.setProcessStatus(byeResponse.getProcessStatus());
            ainDO.setRegion(byeResponse.getRegion());
            ainDO.setRegionName(byeResponse.getRegionName());
            ainDO.setAinType(byeResponse.getAinType());
            String reviewRequired = byeResponse.getReviewRequired();
            readReviewRequired(ainDO, reviewRequired);
            List<BaseYearEvent> events =
                byeResponse.getBaseYearEventList().getBaseYearEvent();
            if (events.isEmpty()) {
                LOGGER.warn(this, "retrieveEventsFromService",
                            BaseYearEventUtils.LOGGER_PREFIX +
                            "No Events returned for this AIN " + ain, null);
            }
            for (BaseYearEvent baseYearEvent : events) {
                input.add(BaseYearEventUtils.createBaseYearEventDO(baseYearEvent));
            }
        } catch (gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            if (fault.getFaultMessage().contains("no data found")) {
                return false;
            } else {
                throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                    fault.getFaultType());
            }
        } catch (SOAPFaultException exception) {
            SOAPFault soapFault = exception.getFault();
            LOGGER.error(this, "retrieveEventsFromService",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "SOAP Fault Occured Retrieve Events: " +
                         soapFault.getFaultCode(), null);
            return false;
        }

        return true;
    }

    private void readReviewRequired(AinDO ainDO, String reviewRequired) {
        if("Y".equalsIgnoreCase(reviewRequired)){
            ainDO.setReviewRequired(Boolean.TRUE);
        }else{
            ainDO.setReviewRequired(Boolean.FALSE);
        }
    }

    private BaseYearEventMgmtService getServiceHandle() {
        if (baseYearEventMgmtService_ep == null) {
            URL wsdlLocationURL =
                WebServiceEndPoints.getWsdlUrlFromConnections(BaseYearEventConstants.getBYEMgmtWebServiceConnectionName());
            baseYearEventMgmtService_ep =
                    new BaseYearEventMgmtService_ep(wsdlLocationURL,
                                                    new QName("http://assessor.lacounty.gov/amp/wsdl/bvm/BaseYearEventMgmtService/v1.0",
                                                              "BaseYearEventMgmtService_ep"));

        }
        //        if(baseYearEventMgmtService_ep == null){
        //            baseYearEventMgmtService_ep = new BaseYearEventMgmtService_ep();
        //        }
        SecurityPoliciesFeature securityFeatures =
            new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });
        return baseYearEventMgmtService_ep.getBaseYearEventMgmtService_pt(securityFeatures);
    }
    
    public BigInteger checkForBYEChanges(String ain, BigInteger aoid) throws AmpBaseYearEventException{
        BaseYearEventMgmtService baseYearEventMgmtService = getServiceHandle();
        ChangedBaseYearEventsRequest request = new ChangedBaseYearEventsRequest();
        RequestHeader header =new RequestHeader();
        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        header.setRequestId(BaseYearEventUtils.getRequestID());
        request.setHeader(header);
        request.setAin(ain);
        request.setAoid(aoid);
        request.setProcessType(BaseYearEventConstants.getAPPLICATION_ID());
        try {
            ChangedBaseYearEventsResponse baseYearEvents = baseYearEventMgmtService.changedBaseYearEvents(request);
            BigInteger changeCount = baseYearEvents.getChangedBYECount();
            if(changeCount.intValue() > 0){
                changedEvents = baseYearEvents.getChangedBaseYearEventList().getBaseYearEvent();
            }
            return changeCount;
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
                throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                    fault.getFaultType());
        }
//        catch (SOAPFaultException exception) {
//            SOAPFault soapFault = exception.getFault();
//            LOGGER.error(this, "checkForBYEChanges",
//                         BaseYearEventUtils.LOGGER_PREFIX +
//                         "SOAP Fault Occured Changed BaseYear Events: " +
//                         soapFault.getFaultCode(), null);
//            return null;
//        }
    }

    public String initiateBaseYearEvent(String ain, String userId,
                                        BigInteger aoid,
                                        String source) throws AmpBaseYearEventException {
        if (ain == null || userId == null) {
            LOGGER.error(this, "initiateBaseYearEvent",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "BVM Process Initiation Call Will Fail. AIN: " + ain +
                         " UserId: " + userId, null);
        }
        BaseYearEventMgmtService baseYearEventMgmtService = getServiceHandle();
        InitiateBaseYearEventRequest baseYearEventsRequest =
            new InitiateBaseYearEventRequest();
        RequestHeader reqHeader = new RequestHeader();
        reqHeader.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        reqHeader.setRequestId(BaseYearEventUtils.getRequestID());
        baseYearEventsRequest.setHeader(reqHeader);
        baseYearEventsRequest.setAin(ain);
        baseYearEventsRequest.setUserId(userId);
        baseYearEventsRequest.setAoid(aoid);
        baseYearEventsRequest.setSource(source);
        try {
            InitiateBaseYearEventResponse bpmTask =
                baseYearEventMgmtService.initiateBaseYearEvent(baseYearEventsRequest);
            String bpmInstanceId = bpmTask.getBpmInstanceId();
            if (bpmInstanceId == null || bpmInstanceId.equals("")) {
                LOGGER.error(this, "initiateBaseYearEvent",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "BVM Process Initiation Call Failed. AIN: " +
                             ain + " UserId: " + userId, null);
            }
            return bpmInstanceId;
        } catch (gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                fault.getFaultType());
        }
    }

    public String updateReviewRequired(ArrayList<BaseYearEventDO> input,
                                       String ain,
                                       String userId) throws AmpBaseYearEventException {
        BaseYearEventMgmtService baseYearEventMgmtService = getServiceHandle();
        
        RequestHeader header = new RequestHeader();
        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        header.setRequestId(BaseYearEventUtils.getRequestID());
        
        UpsertBaseYearEventsRequest baseYearEventsRequest =
            new UpsertBaseYearEventsRequest();
        baseYearEventsRequest.setAin(ain);
        baseYearEventsRequest.setUserId(userId);
        UpsertBaseYearEventList baseYearEventList = new UpsertBaseYearEventList();
        List<BaseYearEvent> updateList = baseYearEventList.getBaseYearEvent();
        baseYearEventsRequest.setUpsertBaseYearEventList(baseYearEventList);
        
        if (input.size() > 0) {
            BaseYearEventDO baseYearEventDO = input.get(0);
            baseYearEventDO.setReviewRequired("Y");
            baseYearEventDO.setTransactionType("U");
            BaseYearEvent baseYearEvent =
                BaseYearEventUtils.createBaseYearEvent(baseYearEventDO);
            updateList.add(baseYearEvent);
            try {
                UpsertBaseYearEventsResponse baseYearEvents =
                    baseYearEventMgmtService.upsertBaseYearEvents(baseYearEventsRequest);
                return baseYearEvents.getMessage();
            } catch (FaultMessage msg) {
                Fault fault = msg.getFaultInfo();
                throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                    fault.getFaultType());
            } catch (SOAPFaultException exception) {
                SOAPFault soapFault = exception.getFault();
                LOGGER.error(this, "updateReviewRequired",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "SOAP Fault Occured Review Required: " +
                             soapFault.getFaultCode(), null);
            }
        }
        return null;
    }
}
