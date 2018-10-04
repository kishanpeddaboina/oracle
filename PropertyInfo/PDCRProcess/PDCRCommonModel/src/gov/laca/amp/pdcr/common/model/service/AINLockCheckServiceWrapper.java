package gov.laca.amp.pdcr.common.model.service;

import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.ent.util.AmpStringUtils;

import gov.laca.amp.fwk.proxy.soap.AmpSoapProxyHelper;
import gov.laca.amp.pdcr.common.model.AmpChangeRequestException;
import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;
import gov.laca.amp.pdcr.common.model.util.WebServiceEndPoints;

import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService_ep;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.FaultMessage;

import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.Fault;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.RequestHeader;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockRequest;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockResponse;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.AssignLockRequest;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.AssignLockResponse;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.ReleaseLockRequest;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.ReleaseLockResponse;

import java.math.BigInteger;

import java.net.URL;

import javax.xml.namespace.QName;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import oracle.adf.share.logging.ADFLogger;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class AINLockCheckServiceWrapper {
    public static final ADFLogger logger = ADFLogger.createADFLogger(AINLockCheckServiceWrapper.class);

    public AINLockCheckServiceWrapper() {
        super();
    }

    private AINLockMgmtService getPort() {
        AINLockMgmtService retPort = null;
        AINLockMgmtService_ep service = null;
        SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });
        if (null == service) {
            URL endPointUrl = WebServiceEndPoints.getWsdlUrlFromConnections(ChangeRequestConstants.AINLOCK_SERVICE_CONNECTION_NAME);
            if (endPointUrl != null) {
                QName qname = new QName(ChangeRequestConstants.AINLOCK_QNAME, ChangeRequestConstants.AINLOCK_SERVICE_CLASS);
                service = new AINLockMgmtService_ep(endPointUrl, qname);
                retPort = service.getAINLockMgmtService_pt(securityFeatures);
            } else {
                service = new AINLockMgmtService_ep();
                retPort = service.getAINLockMgmtService_pt(securityFeatures);
            }
        }
        return retPort;
    }

    public CheckLockResponse CheckLock(String applicationID, String ain, BigInteger aoid, String userId, String clientId,
                                       String processType) throws AmpChangeRequestException {
        CheckLockResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            CheckLockRequest request = new CheckLockRequest();
            request.setHeader(requestHeader);
            request.setAin(ain);
            request.setProcessType(processType);

            // These are not set in PDCR to check the lock
            request.setAoid(aoid);
            request.setClientId(clientId);
            request.setUserId(userId);

            logger.warning("CheckLock Request: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().checkLock(request);

            logger.warning("CheckLock Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in CheckLock Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in CheckLock Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

    public AssignLockResponse AssignLock(String applicationID, String ain, BigInteger aoid, String userId, String clientId,
                                         String processType) throws AmpChangeRequestException {
        AssignLockResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            AssignLockRequest request = new AssignLockRequest();
            request.setHeader(requestHeader);
            request.setAin(ain);
            request.setProcessType(processType);

            // These are not set in PDCR to check the lock
            request.setAoid(aoid);
            request.setClientId(clientId);
            request.setUserId(userId);

            logger.warning("AssignLock Request: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().assignLock(request);

            logger.warning("AssignLock Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in AssignLock Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in AssignLock Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

    public ReleaseLockResponse ReleaseLock(String applicationID, String ain, BigInteger aoid, String userId, String clientId,
                                           String processType) throws AmpChangeRequestException {
        ReleaseLockResponse response = null;
        try {
            // Create the request header
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setApplicationId(applicationID);
            requestHeader.setRequestId("REQ_" + AmpStringUtils.randomString(8));

            // Create the request object
            ReleaseLockRequest request = new ReleaseLockRequest();
            request.setHeader(requestHeader);
            request.setAin(ain);
            request.setProcessType(processType);

            // These are not set in PDCR to check the lock
            request.setAoid(aoid);
            request.setClientId(clientId);
            request.setUserId(userId);

            logger.warning("ReleaseLock Request: " + JSONUtils.toJson(request));

            // Call the service with the users request
            response = getPort().releaseLock(request);

            logger.warning("ReleaseLock Response: " + JSONUtils.toJson(response));
        } catch (FaultMessage msg) {
            Fault fault = msg.getFaultInfo();
            logger.severe("Error in ReleaseLock Operation:", msg);
            throw new AmpChangeRequestException(fault.getFaultMessage(), fault.getFaultType());
        } catch (SOAPFaultException sfe) {
            SOAPFault fault = sfe.getFault();
            logger.severe("Error in ReleaseLock Operation:", sfe);
            throw new AmpChangeRequestException(fault.getFaultString(), fault.getFaultCode());
        }
        return response;
    }

}
