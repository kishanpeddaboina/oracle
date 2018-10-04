package gov.laca.amp.tam.model.proxy;

import gov.laca.amp.ent.util.AmpLogUtil;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.identitylookupservice.client.IdentityLookupService;
import gov.laca.amp.proxy.soap.identitylookupservice.client.IdentityLookupService_ep;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.AppraiserListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictRoleListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictRoleType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserQueryCriteriaType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.IdentityUserType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveDistrictUsersRequest;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveDistrictUsersResponse;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveUserDetailsRequest;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveUserDetailsResponse;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveUserReporteesRequest;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RetrieveUserReporteesResponse;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.RoleListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsQueryCriteriaType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDistrictRoleType;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserReporteeQueryCriteriaType;

import gov.laca.amp.proxy.soap.identitylookupservice.local.IdentityLookupServiceWrapper;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.WorkflowMgmtService;

import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import javax.naming.Context;

import javax.xml.soap.SOAPException;
import javax.xml.ws.BindingProvider;

import oracle.adf.model.connection.webservice.api.WebServiceConnection;
import oracle.adf.share.ADFContext;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;
//import gov.laca.amp.proxy.soap.identitylookupservice.*;
public class IdentityServiceWrapper {
    public IdentityServiceWrapper() {
        super();
    }
    private static final String SECURITY_POLICY = "oracle/wss10_saml_token_client_policy";
     private static final String SERVICE_CLASS_NAME = "gov.laca.amp.proxy.soap.identitylookupservice.client.IdentityLookupService_ep";
     private static final String SERVICE_PORT_CLASS_NAME = "gov.laca.amp.proxy.soap.identitylookupservice.client.IdentityLookupService";
     private static final String SERVICE_NAMESPACE_URI = "http://assessor.lacounty.gov/amp/wsdl/common/id/IdentityLookupService/v1.0";
     private static final String SERVICE_WSDL_URL = "http://campwtt101.assessor.lacounty.gov:7777/soa-infra/services/amp/IdentityLookupService/IdentityLookupService_ep?wsdl";
     
     private SecurityPoliciesFeature securityFeatures =
         new SecurityPoliciesFeature(new String[] { SECURITY_POLICY });    
    
//    public WorkflowMgmtService getPort (){
//    
//        Context ctx = null;
//         WebServiceConnection wsc = null;
//         WorkflowMgmtService proxy = null;
//         try {
//             ctx = ADFContext.getCurrent().getConnectionsContext();
//        
//         wsc =(WebServiceConnection) ctx.lookup("MyWfMgmtService_epConn");
//             URL url = wsc.getWsdlUrl();
//             System.out.println("==> got service url "+url);
//             Class arg0;
//             proxy = wsc.getJaxWSPort(WorkflowMgmtService.class);
//             
//         } catch (Exception e) {
//             e.printStackTrace();
//         }         
//         return proxy;
//    }
  /**
     *
     * @return
     */
  
    public IdentityLookupService getPort (){
        AmpLogUtil.warn("==>Entering IdentityServiceWrapper.getPort() ...");
        IdentityLookupService proxy = null;
        IdentityLookupService servicePort = null;  
        IdentityLookupServiceWrapper serviceWrapper =  new IdentityLookupServiceWrapper();
  
//         securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss10_saml_token_client_policy" });             
//          proxy = identityLookupService_ep.getIdentityLookupService_pt(securityFeatures);
      
       // servicePort = identityLookupService_ep.getIdentityLookupService_pt(securityFeatures);
        AmpLogUtil.warn("==>Invoking getADFWSConnectionSecurePort ###... ");
        servicePort = serviceWrapper.getADFWSConnectionSecurePort();
        System.out.println("=====> ATTACHING USER/PASSWORD");
//    ((BindingProvider)servicePort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"C133792");
//    ((BindingProvider)servicePort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"Password123");
//         
       
       // port = service.getPropertyDetailPPBizService_pt(securityFeatures);
       AmpLogUtil.warn("==>Exiting IdentityServiceWrapper.getPort() ...");
        return servicePort;
    }
   /**
     *
     * @return
     */
//    public IdentityLookupService getPort (){
//        Context ctx = null;
//         WebServiceConnection wsc = null;
//         IdentityLookupService proxy = null;
//         try {
//             ctx = ADFContext.getCurrent().getConnectionsContext();
//        
//         wsc =(WebServiceConnection) ctx.lookup("IdentityLookupService_ep");
//      
//             proxy = wsc.getJaxWSPort(IdentityLookupService.class);
//             
//         } catch (Exception e) {
//             e.printStackTrace();
//         }         
//         return proxy;
//    }
    
    public static void main(String[] args) throws Exception {
        
        //new IdentityServiceWrapper().getUserReportees();  
         IdentityServiceWrapper x = new IdentityServiceWrapper();
        List<IdentityUserType> reportees = x.getUserReportees("c9900015");
        System.out.println("==>reportess length:"+reportees.size());
        System.exit(0);
         System.out.println("==>highestRole :"+x.getHighestRole("C9900015"));
       IdentityServiceWrapper IdentityServiceWrapper = new IdentityServiceWrapper();
        List<String> distList =    IdentityServiceWrapper.getUserDistricts("C9900015");
       System.out.println("==>distList :"+distList.toString());
       List<DistrictUserType> DistrictUserList = IdentityServiceWrapper.getDistrictUsers("West","Appraiser");
       for(DistrictUserType user: DistrictUserList){
           AppraiserListType AppraiserListType = user.getAppraiserList();
           List<IdentityUserType> IdentityUserList = AppraiserListType.getIdentityUser();
           for(IdentityUserType IdentityUser : IdentityUserList){
               System.out.println("==>user :"+IdentityUser.getDisplayName());
           }         
       }
    }
        
    
    /**
     *
     * @return
     * @throws Exception
     */
 public List<String> getUserDistricts(String userId) throws Exception {
        List<String> districtsList = new ArrayList<String>();
        IdentityLookupService  port = new IdentityServiceWrapper().getPort();
        RetrieveUserDetailsRequest userDetailsRequest = new RetrieveUserDetailsRequest();
        UserDetailsQueryCriteriaType userDetailsQueryCriteria = new UserDetailsQueryCriteriaType();
        userDetailsQueryCriteria.setIdentityUserId(userId);
        userDetailsRequest.setUserDetailsQueryCriteriaType(userDetailsQueryCriteria);
    RetrieveUserDetailsResponse userDtlsResponse = port.retrieveUserDetails(userDetailsRequest);
    UserDetailsType userDtls = userDtlsResponse.getUserDetails();
    String highestRole = userDtls.getHighestRole();
    List<UserDistrictRoleType> userList = userDtls.getUserDistrictRoleList().getUserDistrictRole();
    for(UserDistrictRoleType user: userList){
     String dist = user.getDistrictName();
        System.out.println("==>Dist:"+dist);
        districtsList.add(dist);
    }
        System.out.println("==>highestRole:"+highestRole);
        
        return districtsList;
    }
    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<String> getUserDistrictsByUserDtls(UserDetailsType userDtls) throws Exception {
           List<String> districtsList = new ArrayList<String>();
    
       String highestRole = userDtls.getHighestRole();
       List<UserDistrictRoleType> userList = userDtls.getUserDistrictRoleList().getUserDistrictRole();
       for(UserDistrictRoleType user: userList){
        String dist = user.getDistrictName();
           System.out.println("==>Dist:"+dist);
           districtsList.add(dist);
       }
           System.out.println("==>highestRole:"+highestRole);
           
           return districtsList;
       }
       
    
    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public String getHighestRole(String userId) throws Exception {
        IdentityLookupService  port = new IdentityServiceWrapper().getPort();
        RetrieveUserDetailsRequest userDetailsRequest = new RetrieveUserDetailsRequest();
        UserDetailsQueryCriteriaType userDetailsQueryCriteria = new UserDetailsQueryCriteriaType();
        userDetailsQueryCriteria.setIdentityUserId(userId);
        userDetailsRequest.setUserDetailsQueryCriteriaType(userDetailsQueryCriteria);
        RetrieveUserDetailsResponse userDtlsResponse = port.retrieveUserDetails(userDetailsRequest);
        UserDetailsType userDtls = userDtlsResponse.getUserDetails();
        String highestRole = userDtls.getHighestRole();
       // userDtls.getDisplayName();
        
        
        
        return highestRole;
    }
    
    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public UserDetailsType getUserDetails(String userId) throws Exception {
        IdentityLookupService  port = new IdentityServiceWrapper().getPort();
        RetrieveUserDetailsRequest userDetailsRequest = new RetrieveUserDetailsRequest();
        UserDetailsQueryCriteriaType userDetailsQueryCriteria = new UserDetailsQueryCriteriaType();
        userDetailsQueryCriteria.setIdentityUserId(userId);
        userDetailsRequest.setUserDetailsQueryCriteriaType(userDetailsQueryCriteria);
        RetrieveUserDetailsResponse userDtlsResponse = port.retrieveUserDetails(userDetailsRequest);
        UserDetailsType userDtls = userDtlsResponse.getUserDetails();
       
        
        return userDtls;
    }
    
    /**
     *
     * @return
     * @throws FaultMessage
     */
    public List<IdentityUserType> getUserReportees(String userId) throws Exception {
        IdentityLookupService  port = new IdentityServiceWrapper().getPort();
        RetrieveUserReporteesRequest userReporteesRequest = new RetrieveUserReporteesRequest();
        UserReporteeQueryCriteriaType userReporteeQueryCriteria = new UserReporteeQueryCriteriaType();
        userReporteeQueryCriteria.setIdentityUserId(userId);
        userReporteeQueryCriteria.setUpToLevel("1");
        userReporteesRequest.setUserReporteeQueryCriteria(userReporteeQueryCriteria);
        RetrieveUserReporteesResponse userReporteesResponse = port.retrieveUserReportees(userReporteesRequest);
        List<IdentityUserType>  userReporteesList = userReporteesResponse.getIdentityUserList().getIdentityUser();
        System.out.println("==>userReportees count:"+userReporteesList.size());
        
        return userReporteesList;
        
    }
    
    public List<DistrictUserType> getDistrictUsers(String district, String role) throws javax.xml.ws.soap.SOAPFaultException, FaultMessage {
        String inputs="district:"+district+",role:"+role;
        AmpLogUtil.warn("==> Entering "+new Exception().getStackTrace()[0].getMethodName()+"inputs:"+inputs);
       // System.out.println("==> Entering getDistrictUsers, "+new Exception().getStackTrace()[0].getMethodName()+"inputs:"+inputs);

        IdentityLookupService  port = new IdentityServiceWrapper().getPort();
        RetrieveDistrictUsersRequest districtUsersRequest = new RetrieveDistrictUsersRequest();
     
        DistrictUserQueryCriteriaType districtUserQueryCriteria = new DistrictUserQueryCriteriaType();
        DistrictRoleListType districtRoleListType = new DistrictRoleListType();
        List<DistrictRoleType> districtRoleList  = districtRoleListType.getDistrictRole();
        DistrictRoleType districtRole = new DistrictRoleType();
        districtRole.setDistrictName(district);

        RoleListType roleListType = new RoleListType();
        districtRole.setRoleList(roleListType);
        //roleListType.set
        List<String> roleList =  roleListType.getRoleName();
        roleList.add(role);
        
        districtRole.setRoleList(roleListType);
        districtRoleList.add(districtRole);
        
        districtUserQueryCriteria.setDistrictRoleList(districtRoleListType);
       districtUsersRequest.setDistrictUserQueryCriteria(districtUserQueryCriteria);
        RetrieveDistrictUsersResponse districtUsersResponse = port.retrieveDistrictUsers(districtUsersRequest);
        DistrictUserListType DistrictUserListType = districtUsersResponse.getDistrictUserList();
        List<DistrictUserType> DistrictUserList = DistrictUserListType.getDistrictUser();
//        System.out.println("==>DistrictUserListType count:"+DistrictUserListType.getCount());
//        System.out.println("==>DistrictUserListType.getDistrictUser.size():"+DistrictUserListType.getDistrictUser().size());
        return DistrictUserList;
        
    }


   
}
