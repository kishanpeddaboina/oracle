package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.WebServiceEndPoints;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.portal.extension.AmpBusinessUserProfile;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.AINLockMgmtService_ep;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockRequest;
import gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.CheckLockResponse;
import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;

import java.math.BigInteger;

import java.net.URL;

import javax.xml.namespace.QName;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


/**
 * @author Vijay Redla
 * @version 2.0
 */
public class AINLockMgmtServiceWrapper {
    public static final AmpLogger LOGGER =
        new AmpLogger(AINLockMgmtServiceWrapper.class);
    private static final String PROCESS_STATUS_LOCKED = "LOCKED";
    private AINLockMgmtService_ep aINLockMgmtService_ep = null;
    
    private String processId = null;
    private String initiatedBy = null;
    private String lockStatus = null;

    public AINLockMgmtServiceWrapper() {
        super();
    }

    public String checkLock(String ain, BigInteger aoid) throws AmpBaseYearEventException {
        AINLockMgmtService aINLockMgmtService = getServiceHandle();
        CheckLockRequest check = new CheckLockRequest();
        check.setAin(ain);
        check.setAoid(aoid);
        check.setProcessType(BaseYearEventConstants.getPROCESS_ID());
        gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.RequestHeader header =
            new gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.RequestHeader();
        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        header.setRequestId(BaseYearEventUtils.getRequestID());
        check.setHeader(header);
        CheckLockResponse checkLockResponse;
        try {
            checkLockResponse = aINLockMgmtService.checkLock(check);
            lockStatus = checkLockResponse.getLockStatus();
            if(PROCESS_STATUS_LOCKED.equals(lockStatus)){
                String initiatedById = checkLockResponse.getInitiatedBy();
                lookupDisplayName(initiatedById);
                if(initiatedBy == null){
                    initiatedBy = initiatedById;
                }
            }
            if(initiatedBy == null || lockStatus == null){
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                             "BVM Process Lock call returned Initiated By: " +
                             initiatedBy + "Lock Status:" + lockStatus +
                             " For AIN: " + ain);
            }
            return initiatedBy;
        } catch (gov.laca.amp.proxy.soap.ainlockmgmtservice.client.FaultMessage msg) {
            gov.laca.amp.proxy.soap.ainlockmgmtservice.client.gen.Fault fault =
                msg.getFaultInfo();
            throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                fault.getFaultType());
        }
    }

    public String lookupDisplayName(String initiatedById) {
        AmpBusinessUserProfile ampBusinessUserProfile =
            (AmpBusinessUserProfile)JSFUtils.getManagedBeanValue(BaseYearEventConstants.AMP_PROFILEBEAN_NAME);
        if (ampBusinessUserProfile != null) {
            try {
                return initiatedBy =
                        ampBusinessUserProfile.retrieveUserName(initiatedById);
            } catch (FaultMessage fm) {
                LOGGER.error(this, "checkLock",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "BVM Process Lock retrieve user details failed: " +
                             initiatedBy + " Lock Status:" + lockStatus, null);                        
            }
        }
        return null;
    }

    private AINLockMgmtService getServiceHandle() {
        if (aINLockMgmtService_ep == null) {
            URL wsdlLocationURL =
                WebServiceEndPoints.getWsdlUrlFromConnections(BaseYearEventConstants.getAinWebServiceConnectionName());
            aINLockMgmtService_ep =
                    new AINLockMgmtService_ep(wsdlLocationURL, new QName("http://assessor.lacounty.gov/amp/wsdl/ao/AINLockMgmtService/v1.0",
                                                                         "AINLockMgmtService_ep"));
//            if(aINLockMgmtService_ep == null){
//                aINLockMgmtService_ep = new AINLockMgmtService_ep();
//            }
        }
        SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });             
        AINLockMgmtService aINLockMgmtService =
            aINLockMgmtService_ep.getAINLockMgmtService_pt(securityFeatures);
        return aINLockMgmtService;
    }

    public String getProcessId() {
        return processId;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public String getLockStatus() {
        return lockStatus;
    }
}
