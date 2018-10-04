package gov.laca.amp.tam.model.proxy;

import gov.laca.amp.ent.util.AmpLogUtil;
import gov.laca.amp.proxy.fwk.CommonSoapService;
import gov.laca.amp.proxy.soap.propertysearchrpservice.local.PropertySearchRpServiceWrapper;
import gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService;
import gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService_ep;
import gov.laca.amp.proxy.soap.rpeventhistory.local.PropertyDtlsRPEvntHstWrapper;

import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.WorkflowMgmtService;

import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.WorkflowMgmtService_ep;

import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.local.WorkflowMgmtServiceWrapper;

import java.net.URL;

import javax.naming.Context;

import javax.xml.namespace.QName;

import oracle.adf.model.connection.webservice.api.WebServiceConnection;
import oracle.adf.share.ADFContext;

public class UnverifiedAINsMgmtServiceWrapper  {


    public static final String SERVICE_LOCALPART = "PropertySearchService_ep";
    public static final String SERVICE_CLASSNAME =
        "gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService_ep";
    public static final String PORT_CLASSNAME =
        "gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService";
    public static final String NAMESPACE_URI =
        "http://assessor.lacounty.gov/amp/wsdl/ao/EventHistoryRPService/v1.0";
    public static final String WSDL_URL =
        "http://campwtt101.assessor.lacounty.gov:7777/soa-infra/services/amp/EventHistoryRPService/EventHistoryRPService_ep?wsdl";
    public static final int WS_RETRIES = 5;
    public static final String DEFAULT_RECORD_NUMBER_BEGIN = "1";
    public static final String DEFAULT_RECORD_NUMBER_END = "1000";
    static String serviceClassFullName = SERVICE_LOCALPART;
    static String portClassFullName = PORT_CLASSNAME;
    static String namespaceURI = NAMESPACE_URI;
    static String wsdlURI = WSDL_URL;

   
    public UnverifiedAINsMgmtServiceWrapper() {
//        super("gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService_ep", 
//              "gov.laca.amp.proxy.soap.rpeventhistory.client.EventHistoryRPService", 
//              "http://assessor.lacounty.gov/amp/wsdl/ao/EventHistoryRPService/v1.0",
//              "http://campwtt101.assessor.lacounty.gov:7777/soa-infra/services/amp/EventHistoryRPService/EventHistoryRPService_ep?wsdl");
    }
    

    public UnverifiedAINsMgmtServiceWrapper(String serviceClassFullName,
                                                      String portClassFullName,
                                                      String namespaceURI,
                                                      String wsdlURLStr) {
//        super(serviceClassFullName, portClassFullName, namespaceURI,
//              wsdlURLStr);
    }


    public static void main(String[] args){
        UnverifiedAINsMgmtServiceWrapper UnverifiedAINsMgmtServiceWrapper = new UnverifiedAINsMgmtServiceWrapper();
        UnverifiedAINsMgmtServiceWrapper.getPort();
    }

    public WorkflowMgmtService getPort (){
        WorkflowMgmtService port = null; //service.getWorkflowMgmtService_pt();
        WorkflowMgmtServiceWrapper wrapper = new WorkflowMgmtServiceWrapper();
        wrapper.getPort ("WorkflowMgmtService_ep"); 
      port =  wrapper.getServicePort();
       return port;
    }
//        public WorkflowMgmtService getPort (){
//        
//            Context ctx = null;
//             WebServiceConnection wsc = null;
//             WorkflowMgmtService proxy = null;
//             try {
//                 ctx = ADFContext.getCurrent().getConnectionsContext();
//                 Object obj = ctx.lookup("aa");
//                 if(obj ==null){
//                     System.out.println("==> Null conn");
//                 }
//                 else{
//                     System.out.println("==>  conn:"+obj); 
//                 }
//            
//             wsc =(WebServiceConnection) ctx.lookup("UnlockedAINsMgmtService_ep");
//                 URL url = wsc.getWsdlUrl();
//                 System.out.println("==> got service url "+url);
//                 Class arg0;
//                 proxy = wsc.getJaxWSPort(WorkflowMgmtService.class);
//                 
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }         
//             return proxy;
//        }  
    
//    public UnverifiedAINsMgmtService getPort() throws Exception{
//        EventHistoryRPService_ep service = null;
//        java.net.URL wsUrl = null;
//        String epName = "RPEventHistory";
//        try {
//           WebServiceConnection wsConn = (WebServiceConnection)(ADFContext.getCurrent().getADFConfig().getConnectionsContext().lookup(epName)); 
//           wsUrl = wsConn.getWsdlUrl();
//            
////            wsUrl = new URL("http://campwtt101.assessor.lacounty.gov:7777/soa-infra/services/amp/EventHistoryRPService/EventHistoryRPService_ep?wsdl"); //TODO Change this back to get from EM
//            
//           // service = new EventHistoryRPService_ep();
//        } catch (Exception e) {
//            AmpLogUtil.warn(PropertyDtlsRPEvntHstWrapper.class.getName(), "-----------warn-- getPort() ADF Webservice Connection is not found." );
//            e.printStackTrace();
//            throw new RuntimeException("Exception Occured EventHistoryRPService getPort : ADF Webservice Connection is not found");
//        }
//        AmpLogUtil.warn(PropertyDtlsRPEvntHstWrapper.class.getName(), "-----------warn--  getPort() -- wsUrl=" + wsUrl);
//
//        if(wsUrl == null ) {
//            AmpLogUtil.warn(PropertySearchRpServiceWrapper.class.getName(), "-----------warn-- getPort() ADF Webservice Connection does not exist, using the default webservice url");
//            service = new EventHistoryRPService_ep(); 
//        } 
//        return service.getEventHistoryRPService_pt();
//    }
//    
    
}
