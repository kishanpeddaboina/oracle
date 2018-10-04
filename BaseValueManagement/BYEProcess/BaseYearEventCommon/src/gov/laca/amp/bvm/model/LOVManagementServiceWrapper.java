package gov.laca.amp.bvm.model;


import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.WebServiceEndPoints;
import gov.laca.amp.fwk.logger.AmpLogger;

import gov.laca.amp.proxy.soap.lovmgmtservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.lovmgmtservice.client.LOVMgmtService;
import gov.laca.amp.proxy.soap.lovmgmtservice.client.LOVMgmtService_ep;

import gov.laca.amp.proxy.soap.lovmgmtservice.client.gen.Fault;
import gov.laca.amp.proxy.soap.lovmgmtservice.client.gen.Lov;
import gov.laca.amp.proxy.soap.lovmgmtservice.client.gen.RequestHeader;
import gov.laca.amp.proxy.soap.lovmgmtservice.client.gen.RetrieveLOVRequest;

import gov.laca.amp.proxy.soap.lovmgmtservice.client.gen.RetrieveLOVResponse;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import javax.xml.namespace.QName;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

public class LOVManagementServiceWrapper {
    public static final AmpLogger LOGGER =
        new AmpLogger(LOVManagementServiceWrapper.class);
    
    public LOVManagementServiceWrapper() {
        super();
    }
    private LOVMgmtService_ep lOVMgmtService_ep;

    public void retrieveLovListFor(String key, List<LookupItem> lookupList) throws AmpBaseYearEventException{
        LOVMgmtService lovService = this.getServiceHandle();
        RetrieveLOVRequest request = new RetrieveLOVRequest();
        RequestHeader header = new RequestHeader();
        header.setApplicationId(BaseYearEventConstants.getAPPLICATION_ID());
        header.setRequestId(BaseYearEventUtils.getRequestID());
        request.setHeader(header);
        request.setCodeName(key);
        try {
            RetrieveLOVResponse lOV = lovService.retrieveLOV(request);
            java.util.List<Lov> list = lOV.getLovList().getLov();
            for (Lov lov : list) {
                LookupItem item =
                    new LookupItem(lov.getId(), lov.getKey(), lov.getLegend());
                lookupList.add(item); 
            }
        } catch (FaultMessage msg) {
            LOGGER.error(this, "OPA validate",
                        BaseYearEventUtils.LOGGER_PREFIX +
                        "Fault calling LOV Management Service", null);            
            Fault fault = msg.getFaultInfo();
            throw new AmpBaseYearEventException(fault.getFaultMessage(),
                                                fault.getFaultType());            
        }
    }

    private LOVMgmtService getServiceHandle() {
        if (lOVMgmtService_ep == null) {
            URL wsdlLocationURL =
                WebServiceEndPoints.getWsdlUrlFromConnections(BaseYearEventConstants.getLOV_MGMT_SERTVICE_CONNECTION_NAME());
            lOVMgmtService_ep =
                    new LOVMgmtService_ep(wsdlLocationURL, new QName("http://assessor.lacounty.gov/amp/wsdl/common/lov/LOVMgmtService/v1.0",
                                                                     "LOVMgmtService_ep"));
        }
        SecurityPoliciesFeature securityFeatures =
            new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });
        return lOVMgmtService_ep.getLOVMgmtService_pt(securityFeatures);
    }
}
