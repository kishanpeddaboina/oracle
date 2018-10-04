package gov.laca.amp.pdcr.common.model.service;

import gov.laca.amp.ent.util.AmpStringUtils;
import gov.laca.amp.ent.util.JSONUtils;

import gov.laca.amp.fwk.proxy.soap.AmpSoapProxyHelper;
import gov.laca.amp.pdcr.common.model.AmpChangeRequestException;
import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;

import gov.laca.amp.pdcr.common.model.util.WebServiceEndPoints;

import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.PDCRMgmtService;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.PDCRMgmtService_ep;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.FaultMessage;

import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.Fault;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.RequestHeader;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.RealPropertyInfo;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.RetrieveRealPropertyInfoRequest;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.RetrieveRealPropertyInfoResponse;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.InitiatePropertyDataCRRequest;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.InitiatePropertyDataCRRequest.RequestorInfo;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.InitiatePropertyDataCRResponse;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.UpdatePropertyDataCRRequest;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.UpdatePropertyDataCRResponse;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.SourceType;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.UserType;

import java.math.BigInteger;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import oracle.adf.share.logging.ADFLogger;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class ChangeRequestServiceWrapper extends AmpSoapProxyHelper {
    public static final ADFLogger logger = ADFLogger.createADFLogger(ChangeRequestServiceWrapper.class);

    public ChangeRequestServiceWrapper() {
        super();
    }

    private PDCRMgmtService getPort() {
        PDCRMgmtService retPort = null;
        PDCRMgmtService_ep service = null;
        SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });
        if (null == service) {
            URL endPointUrl = WebServiceEndPoints.getWsdlUrlFromConnections(ChangeRequestConstants.PDCRMGMT_SERVICE_CONNECTION_NAME);
            if (endPointUrl != null) {
                QName qname = new QName(ChangeRequestConstants.PDCRMGMT_QNAME, ChangeRequestConstants.PDCRMGMT_SERVICE_CLASS);
                service = new PDCRMgmtService_ep(endPointUrl, qname);
                retPort = service.getPDCRMgmtService_pt(securityFeatures);
            } else {
                service = new PDCRMgmtService_ep();
                retPort = service.getPDCRMgmtService_pt(securityFeatures);
            }
        }
        return retPort;
    }

    public RetrieveRealPropertyInfoResponse RetrieveRealPropertyInfo(String applicationID, String ain, String aoid) throws AmpChangeRequestException {
        RetrieveRealPropertyInfoResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            RetrieveRealPropertyInfoRequest request = new RetrieveRealPropertyInfoRequest();
            request.setHeader(requestHeader);
            request.setAin(ain);
            request.setAoid(aoid);

            logger.warning("RetrieveRealPropertyInfo Request: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().retrieveRealPropertyInfo(request);

            logger.warning("RetrieveRealPropertyInfo Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in RetrieveRealPropertyInfo Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in RetrieveRealPropertyInfo Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

    public InitiatePropertyDataCRResponse InitiatePropertyDataCR(String applicationID, String ain, BigInteger aoid, String userID, UserType userType,
                                                                 SourceType sourceType, String actionType, String processType, RequestorInfo requestorInfo,
                                                                 RealPropertyInfo originalRPI, RealPropertyInfo userEnteredRPI,
                                                                 RealPropertyInfo finalRPI) throws AmpChangeRequestException {
        InitiatePropertyDataCRResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            InitiatePropertyDataCRRequest request = new InitiatePropertyDataCRRequest();
            request.setHeader(requestHeader);
            request.setAin(ain);
            request.setAoid(aoid);
            request.setUserId(userID);
            request.setProcessType(processType);
            request.setSource(sourceType);
            request.setUserType(userType);
            request.setRequestorInfo(requestorInfo);
            request.setOriginalRealPropertyInfo(originalRPI);
            request.setUserEnteredRealPropertyInfo(userEnteredRPI);
            request.setFinalRealPropertyInfo(finalRPI);
            request.setActionType(actionType);

            logger.warning("InitiatePropertyDataCR Request: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().initiatePropertyDataCR(request);

            logger.warning("InitiatePropertyDataCR Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in InitiatePropertyDataCR Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in InitiatePropertyDataCR Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

    public UpdatePropertyDataCRResponse UpdatePropertyDataCR(String applicationID, String userID, String sourceType,
                                                             RealPropertyInfo realPropertyInfo) throws AmpChangeRequestException {
        UpdatePropertyDataCRResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            UpdatePropertyDataCRRequest request = new UpdatePropertyDataCRRequest();
            request.setHeader(requestHeader);
            request.setUserId(userID);
            request.setSource(sourceType);
            request.setRealPropertyInfo(realPropertyInfo);

            logger.warning("UpdatePropertyDataCR payload being sent: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().updatePropertyDataCR(request);

            logger.warning("UpdatePropertyDataCR Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in UpdatePropertyDataCR Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in UpdatePropertyDataCR Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

}

