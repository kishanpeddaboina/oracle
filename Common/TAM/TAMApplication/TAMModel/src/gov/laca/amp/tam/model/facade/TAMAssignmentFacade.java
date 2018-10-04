package gov.laca.amp.tam.model.facade;

//import com.thoughtworks.xstream.XStream;

import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.fwk.extn.facade.AmpProxyFacade;

import gov.laca.amp.fwk.logger.AmpLogger;


import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.identitylookupservice.client.IdentityLookupService;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.AppraiserListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.IdentityUserType;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.PrincipalListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.SupervisorListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.WorkflowMgmtService;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.LOVRegionType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveUnlockedAINRequest;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveUnlockedAINResponse;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.UpsertWorkflowAssignmentRequest;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WFUnlockedAINListType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WFUnlockedAINType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WorkflowAssignmentListType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WorkflowAssignmentType;
import gov.laca.amp.tam.model.pojo.TAMAssignmentPojo;
import gov.laca.amp.tam.model.pojo.UnverifiedAINPojo;

import gov.laca.amp.tam.model.proxy.IdentityServiceWrapper;


import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.ObjectFactory;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveRegionByDistrictRequest;

import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveRegionByDistrictResponse;

import gov.laca.amp.tam.model.pdc.TAMAssignmentDC;
import gov.laca.amp.tam.model.proxy.UnverifiedAINsMgmtServiceWrapper;

import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.math.BigInteger;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import utils.system;

public class TAMAssignmentFacade extends AmpProxyFacade {
    static AmpLogger AmpLogger = new AmpLogger(TAMAssignmentFacade.class);
    public static int BATCH_COUNT = 100;

    public TAMAssignmentFacade() {
        super();
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        TAMAssignmentDC dc = new TAMAssignmentDC();
        String districtL = "North District Office";
        // dc.assignedToL = "mock";
        // dc.currentBatchNumber = 1;
        dc.searchAINs("", false, "", "",districtL, "", "", "", "");
        dc.nextSearchAINs();
        dc.previousSearchAINs();
        dc.nextSearchAINs();
        System.exit(0);

        String assignedTo = "c9900028-VivekAchariya APPRAISER North";
        String assignedToId = (String)(assignedTo.split("-")[0]);
        System.out.println("==>:" + assignedToId);
        ArrayList<String> list = new ArrayList<String>();
        list.add("c123-Tbdrc");
        list.add("c345-Zbdrc");
        list.add("c623-Abdrc");
        list.add("c123-Gbdrc");
        Collections.sort(list, new UserCompare());
        System.out.println("==>after sorting:" + list);
        //  System.exit(0);
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        //      testXMLGregCalendar();
        facade.getUserDetailsByUserId("C144135");
        System.exit(0);

        ArrayList<String> userList =
            (ArrayList<String>)facade.getUsersListByRole("West", "Appraiser",
                                                         "C9900015",
                                                         "SomeName");
        System.out.println("==>userListByRole:" + userList);
        //testInsert();
        // System.exit(0);
    }

    /**
     *
     */
    public static void testXMLGregCalendar() {
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        WFUnlockedAINType soa = new WFUnlockedAINType();

        GregorianCalendar gcal = new GregorianCalendar();
        XMLGregorianCalendar xgcal = null;
        try {
            xgcal =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        soa.setTamCreatedDateTime(xgcal);
        soa.setAin("ain");
        soa.setAoid(new BigInteger("1111111"));
        soa.setClusterRouteName("Cluster1");

        TAMAssignmentPojo pojo = transformTAMAssignmentPojo(soa, "Chief Appraiser");
        System.out.println("==>AIN:" + pojo.getAin());
    }

    /**
     *
     */
    public static void testInsert() {
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        WFUnlockedAINType soa = new WFUnlockedAINType();
        List<WorkflowAssignmentType> workflowAssignmentList =
            new ArrayList<WorkflowAssignmentType>();
        WorkflowAssignmentType arg0 = new WorkflowAssignmentType();

        arg0.setAin("ain124");
        arg0.setAoid(new BigInteger("112"));
        arg0.setAssignedTo("User1");
        arg0.setAssignmentStatus("ASSIGNED");
        arg0.setCreatedBy("Sagar");
        arg0.setAssignedBy("Sagar");
        arg0.setAssignedByRole("Supervisor");
        arg0.setProcessType("TEST102");
        arg0.setSource("TAM");


        WorkflowAssignmentType arg1 = new WorkflowAssignmentType();
        arg1.setAin("ain124");
        arg1.setAoid(new BigInteger("113"));
        arg1.setAssignedTo("User1");
        arg1.setAssignmentStatus("ASSIGNED");
        arg1.setCreatedBy("Sagar");
        arg1.setAssignedBy("Sagar");
        arg1.setAssignedByRole("Supervisor");
        arg1.setProcessType("TEST102");
        arg1.setSource("TAM");


        workflowAssignmentList.add(arg0);
        workflowAssignmentList.add(arg1);
        //List<WorkflowAssignmentType> workflowAssignmentListArg;
        // facade.upsertIntoWfMgmtTable("C", workflowAssignmentList, "North District Office");
        try {
            facade.upsertIntoWfMgmtTable("C", workflowAssignmentList, "South");

        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            System.out.println(e.getFault().getDetail().getTextContent());
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("=========>exception msg:" + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     *
     * @param ain
     * @param isAssigned
     * @param assignedTo
     * @param district
     * @param region
     * @param cluster
     * @return
     */
    public UnlockedAINsRespStructure getUnlockedAINs(String ain,
                                                     Boolean isAssigned,
                                                     String assignedTo,
                                                     String assignedToRole,
                                                     String district,
                                                     String region,
                                                     String cluster,
                                                     Integer batchNumber, String highestRole) throws Exception {
        String inputs =
            "ain:" + ain + ",isAssigned:" + isAssigned + ",assignedTo:" +
            assignedTo + ",assignedToRole:" +
            assignedToRole+ ",district:" + district + ",region:" + region +
            ",cluster" + cluster + ", batchNumber:" + batchNumber+",highestRole:"+highestRole;
        AmpLogger.warn("==> Entering AssignmentFacde:getUnlockedAINs........" +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        UnlockedAINsRespStructure unlockedAINsRespStructure =
            new UnlockedAINsRespStructure();
        List<TAMAssignmentPojo> unlockedAINs =
            new ArrayList<TAMAssignmentPojo>();
        RetrieveUnlockedAINResponse response = null;

        if (district != null && district.equals("mock")) {

            //           TAMAssignmentPojo arg1 = new TAMAssignmentPojo(false,new BigInteger("112234"),"ain1","region1","dt","clstr");
            //           TAMAssignmentPojo arg2 = new TAMAssignmentPojo(false,new BigInteger("112235"),"ain1","region1","dt","clstr");
            //           unlockedAINs.add(arg0);
            //           unlockedAINs.add(arg1);
            //           unlockedAINs.add(arg2);
            for (int i = 0; i < 100; i++) {
                TAMAssignmentPojo arg =
                    new TAMAssignmentPojo(false, new BigInteger("110000" + i),
                                          "ain" + i, "region" + i, "North",
                                          "clstr123");
                unlockedAINs.add(arg);
            }
            System.out.println("==>debug, ain:" +
                               unlockedAINs.get(0).getAin() + ",district:" +
                               district + ",pojoList[1].aoid:" +
                               unlockedAINs.get(1).getAoid());
        } else {
            //XStream xstream = new XStream();
            UnverifiedAINsMgmtServiceWrapper wrapper =
                new UnverifiedAINsMgmtServiceWrapper();
            WorkflowMgmtService port = wrapper.getPort();
            RetrieveUnlockedAINRequest request =
                new RetrieveUnlockedAINRequest();
            request.setAin(ain);
          
            //Portal login Id is upper case, and LDAP retrieval Id is in lowercase. convert to lower case while storing and retrieving to be consistent
            if (assignedTo != null && isAssigned == true)
                request.setAssignedTo(assignedTo); /*.toLowerCase()*/            
            if (assignedToRole != null && Boolean.TRUE.equals(isAssigned))
            request.setAssignedToRole(assignedToRole);
            request.setDistrict(district);
            request.setRegion(region);
            request.setCluster(cluster);
            BigInteger startIdx =
                BigInteger.valueOf((batchNumber - 1) * BATCH_COUNT + 1);

            BigInteger endIdx = BigInteger.valueOf(batchNumber * BATCH_COUNT);
            request.setRangeStart(startIdx);
            request.setRangeEnd(endIdx);
            request.setAssignedFlag(Boolean.TRUE.equals(isAssigned) ? "Y" : "N");

            response = port.retrieveUnlockedAIN(request);
            String ainOut = response.getAin();
            //  System.out.println("==>XML resp:"+ xstream.toXML(response));
            System.out.println("==>response-AssignmentFacde:getUnlockedAINs, Range:" +
                               response.getRangeStart() + "," +
                               response.getRangeEnd());
            WFUnlockedAINListType WFUnlockedAINListType =
                response.getUnlockedAINList();
            List<WFUnlockedAINType> ainList =
                WFUnlockedAINListType.getUnlockedAIN();

            for (WFUnlockedAINType ainRow : ainList) {
                //TAMAssignmentPojo arg = new TAMAssignmentPojo(false,ainRow.getAoid(),ainRow.getAin(),ainRow.getRegion(),ainRow.getDistrictName(),ainRow.getClusterRouteName());
                TAMAssignmentPojo arg = transformTAMAssignmentPojo(ainRow,highestRole);
                //Fixed 10796 OATS(Solution:remove BYE=0 AINs) - Undoing this fix . This should be fixed in DB/PLSQL
                // if(arg.getByeCount().longValue() > 0)
                unlockedAINs.add(arg);

            }
            System.out.println("==>unlockedAINs.size():" +
                               unlockedAINs.size() + ",currentBatch:" +
                               startIdx + "," + endIdx);
        }
        unlockedAINsRespStructure.setSoaAinList(unlockedAINs);
        unlockedAINsRespStructure.setStartRange(response.getRangeStart().intValue());
        if (response.getRangeEnd() != null)
            unlockedAINsRespStructure.setEndRange(response.getRangeEnd().intValue());
        else
            unlockedAINsRespStructure.setEndRange(response.getRangeStart().intValue());

        if (response.getTotalCount() != null) {
            unlockedAINsRespStructure.setTotalAssignedCount(response.getTotalCount().intValue());
        }

        return unlockedAINsRespStructure;
    }

    /**
     *
     * @param soa
     * @return
     */
    public static TAMAssignmentPojo transformTAMAssignmentPojo(WFUnlockedAINType soa, String highestRole) {
        // Transform the SOA data object to the POJO for the Data model used in the UI
        String inputs = " soa:" + soa.toString();
        //        AmpLogger.warn("==> Entering transformTAMAssignmentPojo........"+inputs);
        //            System.out.println("==> Entering "+new Exception().getStackTrace()[0].getMethodName()+inputs);
        //    System.out.println("==>JSON out:"+JSONUtils.toJson(soa));
        TAMAssignmentPojo pojo = new TAMAssignmentPojo();
        //        pojo = (TAMAssignmentPojo)JSONUtils.fromJson(JSONUtils.toJson(soa), TAMAssignmentPojo.class);
        pojo.setAin(soa.getAin());
        pojo.setAoid(soa.getAoid());
        pojo.setByeCount(soa.getByeCount());
        pojo.setClusterRoute(soa.getClusterRoute());
        pojo.setClusterRouteName(soa.getClusterRouteName());
        pojo.setClusterRouteRef(soa.getClusterRouteRef());
        pojo.setDistrictName(soa.getDistrictName());
        pojo.setRegion(soa.getRegion());
        pojo.setRegionName(soa.getRegionName());
        pojo.setRegionRef(soa.getRegionRef());
        if (soa.getTamAssignedBy().contains("-")) {
            pojo.setTamAssignedBy(soa.getTamAssignedBy());
        } else {
            pojo.setTamAssignedBy(soa.getTamAssignedBy() + "-" +
                                  soa.getTamAssignedBy());
        }
        pojo.setTamAssignedByRole(soa.getTamAssignedByRole());
        pojo.setTamAssignedStatus(soa.getTamAssignedStatus());
        if (soa.getTamAssignedTo().contains("-")) {
            pojo.setTamAssignedTo(soa.getTamAssignedTo());
        } else {
            pojo.setTamAssignedTo(soa.getTamAssignedTo() + "-" +
                                  soa.getTamAssignedTo());
        }
        pojo.setTamAssignedToRole(soa.getTamAssignedToRole());
        pojo.setTamCreatedBy(soa.getTamCreatedBy());
        if (soa.getTamCreatedDateTime() != null) {
            pojo.setTamCreatedDateTime(soa.getTamCreatedDateTime().toGregorianCalendar().getTime());
            pojo.setTamCreatedDateTimeStr(new SimpleDateFormat("MM/dd/yyyy").format(pojo.getTamCreatedDateTime()));
            
        }
        pojo.setTamId(soa.getTamId());
        pojo.setTamModifiedBy(soa.getTamModifiedBy());
        if (soa.getTamModifiedDateTime() != null)
            pojo.setTamModifiedDateTime(soa.getTamModifiedDateTime().toGregorianCalendar().getTime());
        pojo.setTamProcessType(soa.getTamProcessType());
        pojo.setTamSource(soa.getTamSource());
        pojo.setUseCodeDescription(soa.getUseCodeDescription());
        pojo.setUseCodeKey(soa.getUseCodeKey());
        pojo.setUseCodeRef(soa.getUseCodeRef());
        pojo.setUseType(soa.getUseType());
        if(soa.getTamAssignedToRole()== null || soa.getTamAssignedToRole().equals(""))
        {
            pojo.setIsReassignAllowed(true);
        }
        else if( (soa.getTamAssignedToRole().startsWith("Chief") && highestRole.startsWith("Principal"))
          ||(soa.getTamAssignedToRole().startsWith("Chief") && highestRole.startsWith("Supervisor")) 
          ||(soa.getTamAssignedToRole().startsWith("Principal") && highestRole.startsWith("Supervisor")))
        {
            pojo.setIsReassignAllowed(false);
            pojo.setMessage("You are not authorized to reassign as this AIN is currently assigned to a higher Role");
        }
        else
        {
            pojo.setIsReassignAllowed(true);   
        }
        
        return pojo;
    }


    public String upsertIntoWfMgmtTable(String transactionType,
                                        List<WorkflowAssignmentType> workflowAssignmentListArg,
                                        String district) throws gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.FaultMessage,
                                                                javax.xml.ws.soap.SOAPFaultException {
        String msg = "";
        String inputs =
            " -- transactionType:" + transactionType + ",workflowAssignmentListArg:" +
            workflowAssignmentListArg.size() + ",district:" + district;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);

        ObjectFactory objFac = new ObjectFactory();
        UnverifiedAINsMgmtServiceWrapper wrapper =
            new UnverifiedAINsMgmtServiceWrapper();
        WorkflowMgmtService port = null;

        UpsertWorkflowAssignmentRequest request =
            objFac.createUpsertWorkflowAssignmentRequest();
        try {
            request.setTransactionType(transactionType);
            WorkflowAssignmentListType workflowAssignmentListType =
                objFac.createWorkflowAssignmentListType();

            BigInteger aoid = BigInteger.valueOf(14);
            WorkflowAssignmentListType assignList =
                objFac.createWorkflowAssignmentListType();
            for (WorkflowAssignmentType arg0 : workflowAssignmentListArg) {
                WorkflowAssignmentType assign2 =
                    objFac.createWorkflowAssignmentType();
                System.out.println("==> rows found");
                assign2.setAoid(arg0.getAoid());
                assign2.setAin(arg0.getAin());
                assign2.setAssignedBy(arg0.getAssignedBy());
                assign2.setAssignedByRole(arg0.getAssignedByRole());
                if (arg0.getAssignedTo() != null)
                    assign2.setAssignedTo(arg0.getAssignedTo()); 
                assign2.setAssignedToRole(arg0.getAssignedToRole());
                assign2.setAssignmentStatus(arg0.getAssignmentStatus());
                assign2.setTamId(arg0.getTamId());
                if (arg0.getProcessType() == null)
                    assign2.setProcessType("BVM");
                else
                    assign2.setProcessType(arg0.getProcessType());
                assign2.setSource("TAM");
                assignList.getAssignment().add(assign2);
            }
            // request.setHeader(header);
            request.setWorkflowAssignmentList(assignList);

            //   XStream xstr = new XStream();
            //    System.out.println("==>upsert req xml:"+xstr.toXML(request));
            if (!district.equals("mock")) {
                port = wrapper.getPort();
                port.upsertWorkflowAssignment(request);
            }
        } catch (gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.FaultMessage e) {
            System.out.println("==>faultMsg from upsert:" +
                               e.getFaultInfo().getFaultCode() + ",msg:" +
                               e.getFaultInfo().getFaultMessage());
            e.printStackTrace();
            throw e;
        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            System.out.println("======>Fault Message -- " +
                               e.getFault().getDetail().getTextContent());
            e.printStackTrace();
            throw e;
        }
        return msg;
    }


    /**
     *getRegionsByDistrict
     * @param district
     * @return
     */
    public List<String> getRegionsByDistrict(String district) throws Exception {
        String inputs = "district:" + district;
        AmpLogger.warn("==> Entering getRegionsByDistrict........" + inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        ArrayList<String> regions = new ArrayList<String>();

        if (district != null)
            if (district.equals("mock")) {
                regions.add("01 - Mock");
                regions.add("02 - Mock");
                regions.add("04 - Mock");
            } else {

                UnverifiedAINsMgmtServiceWrapper wrapper =
                    new UnverifiedAINsMgmtServiceWrapper();
                WorkflowMgmtService port = wrapper.getPort();
                RetrieveRegionByDistrictRequest RetrieveRegionByDistrictRequest =
                    new RetrieveRegionByDistrictRequest();

                RetrieveRegionByDistrictRequest.setDistrict(district);
                RetrieveRegionByDistrictResponse RetrieveRegionByDistrictResponse =
                    new RetrieveRegionByDistrictResponse();

                try {
                    RetrieveRegionByDistrictResponse =
                            port.retrieveRegionByDistrict(RetrieveRegionByDistrictRequest);
                } catch (javax.xml.ws.soap.SOAPFaultException e) {
                    System.out.println("======>Fault Message -- " +
                                       e.getFault().getDetail().getTextContent());
                    e.printStackTrace();
                    throw new Exception("Web Service Error: " +
                                        e.getFault().getDetail().getTextContent());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                List<LOVRegionType> soaRegions =
                    RetrieveRegionByDistrictResponse.getRegionLov().getRegion();
                //LOVRegionType Lov =  soaRegions.get(0);
                for (LOVRegionType Lov : soaRegions) {
                    regions.add(Lov.getRegion() + "-" + Lov.getRegionName());
                }
            }
        return regions;
    }

    public List<String> getClustersByRegion(String district, String region) {
        String inputs = " region:" + region;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        ArrayList<String> clusters = new ArrayList<String>();
        if (district.equals("mock")) {
            clusters.add("Cluster11");
            clusters.add("Cluster22");
            clusters.add("Cluster33");
        } else {
            //TODO .. retrieve from SOA Proxy

        }

        return clusters;
    }

    /**
     *
     * @param district
     * @param role
     * @param userId
     * @return
     */
    public List<String> getUsersListByRole(String district, String role,
                                           String userId,
                                           String userDisplayName) {
        String derivedRole =
            TAMModelUtils.LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP.get(role);
        String inputs =
            "district:" + district + ",role:" + role + ",userId:" + userId +
            ", derived role:" + derivedRole;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       "inputs:" + inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           "inputs:" + inputs);
        ArrayList<String> userIdList = new ArrayList<String>();
        ArrayList<DistrictUserType> districtUserList =
            new ArrayList<DistrictUserType>();
        ArrayList<String> userListTotal = new ArrayList<String>();
        List<IdentityUserType> userList = null;

        IdentityServiceWrapper identityServiceWrapper =
            new IdentityServiceWrapper();
        ArrayList<String> UserAndReporteeIdList = new ArrayList<String>();
        UserAndReporteeIdList.add(userId.toLowerCase() + "-" +
                                  userDisplayName);
        try {
            List<IdentityUserType> reporteeUsers =
                identityServiceWrapper.getUserReportees(userId);
            for (IdentityUserType uu2 : reporteeUsers) {
                UserAndReporteeIdList.add(uu2.getUid() + "-" +
                                          uu2.getDisplayName());
                System.out.println("==>user from getUserReportees:" +
                                   uu2.getUid() + "--" + uu2.getDisplayName());
            }

        } catch (Exception e) {
            AmpLogger.error("==>TAM - Unable to retrieve getUserReportees for userId:" +
                            userId + ", Error Message:" + e.getMessage());
            e.printStackTrace();
        }


        try {
            districtUserList =
                    (ArrayList<DistrictUserType>)identityServiceWrapper.getDistrictUsers(TAMModelUtils.TAM_TO_LDAP_DISTRICTS_MAP.get(district),
                                                                                         derivedRole);
            System.out.println("==>districtUserList.length:" +
                               districtUserList.size());
            System.out.println("==>derivedRole:" + derivedRole);
            for (DistrictUserType u : districtUserList) {
                if (derivedRole.startsWith("Appraiser")) {
                    userList = u.getAppraiserList().getIdentityUser();

                }
                if (derivedRole.startsWith("Supervisor")) {
                    userList = u.getSupervisorList().getIdentityUser();
                }
                if (derivedRole.startsWith("Principal")) {
                    userList = u.getPrincipalList().getIdentityUser();
                }
                System.out.println("==>userList.length:" + userList.size());
                for (IdentityUserType uu : userList) {
                    if (!UserAndReporteeIdList.contains(uu.getUid() + "-" +
                                                        uu.getDisplayName()))
                        userListTotal.add(uu.getUid() + "-" +
                                          uu.getDisplayName());
                    System.out.println("==>user from dist & role:" +
                                       uu.getUid() + "-" +
                                       uu.getDisplayName());
                }

            }
        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            AmpLogger.error("==>TAM - Unable to retrieve getUserReportees for district , role:" +
                            district + "," + derivedRole + ", Error Message:" +
                            e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AmpLogger.error("==>TAM - Unable to retrieve getUserReportees for district , role:" +
                            district + "," + derivedRole + ", Error Message:" +
                            e.getMessage());
            e.printStackTrace();
        }        
        AmpLogger.warn("==>userListTotal.size:" + userListTotal.size() +
                       ",userIdList.size:" + userIdList.size());
        System.out.println("==>userListTotal.size:" + userListTotal.size() +
                           ",userIdList.size:" + userIdList.size());
        Collections.sort(userListTotal, new UserCompare());
        userIdList.addAll(UserAndReporteeIdList);
        userIdList.addAll(userListTotal);
        return userIdList;
    }

    /**
     *getReporteesAndDistrictUsersListByRole
     * @param district
     * @param role
     * @param userId
     * @return
     */
    public List<String> getReporteesAndDistrictUsersListByRole(String district,
                                                               String role,
                                                               String userId,
                                                               String userDisplayName) {
        // String derivedRole = TAMModelUtils.LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP.get(role);
        String inputs =
            "district:" + district + ",role:" + role + ",userId:" + userId +
            ",  role:" + role;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       "inputs:" + inputs);

        ArrayList<String> userIdList = new ArrayList<String>();
        ArrayList<DistrictUserType> districtUserList =
            new ArrayList<DistrictUserType>();
        ArrayList<String> userListTotal = new ArrayList<String>();
        List<IdentityUserType> userList = null;

        IdentityServiceWrapper identityServiceWrapper =
            new IdentityServiceWrapper();
        ArrayList<String> UserAndReporteeIdList = new ArrayList<String>();
        UserAndReporteeIdList.add(userId.toLowerCase() + "-" +
                                  userDisplayName);

        try {
            districtUserList =
                    (ArrayList<DistrictUserType>)identityServiceWrapper.getDistrictUsers(TAMModelUtils.TAM_TO_LDAP_DISTRICTS_MAP.get(district),
                                                                                         role);
            AmpLogger.warn("==>districtUserList.length:" +
                           districtUserList.size());
            AmpLogger.warn("==>derivedRole:" + role);
            for (DistrictUserType u : districtUserList) {
                if (role.startsWith("Appraiser")) {
                    userList = u.getAppraiserList().getIdentityUser();
                }
                if (role.startsWith("Supervisor")) {
                    userList = u.getSupervisorList().getIdentityUser();
                }
                if (role.startsWith("Principal")) {
                    userList = u.getPrincipalList().getIdentityUser();
                }
                if (role.startsWith("Chief")) {
                    userList = u.getChiefAppraiserList().getIdentityUser();
                }                
                System.out.println("==>userList.length:" + userList.size());
                //Modified to improve performance
                List<IdentityUserType> reporteeUsers =
                    this.retrieveUserReporteesFromDistrictUsers(userList,
                                                                userId);
                for (IdentityUserType uu2 : reporteeUsers) {
                    UserAndReporteeIdList.add(uu2.getUid() + "-" +
                                              uu2.getDisplayName());
                    System.out.println("==>user from getUserReportees:" +
                                       uu2.getUid() + "--" +
                                       uu2.getDisplayName());
                }
                //Following removes the duplicates of Reportees occuring in the district user list
                for (IdentityUserType uu : userList) {
                    if (!UserAndReporteeIdList.contains(uu.getUid() + "-" +
                                                        uu.getDisplayName()))
                        userListTotal.add(uu.getUid() + "-" +
                                          uu.getDisplayName());
                    System.out.println("==>user from dist & role:" +
                                       uu.getUid() + "-" +
                                       uu.getDisplayName());
                }
            }
        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            AmpLogger.error("==>TAM - Unable to retrieve getUserReportees for district , role:" +
                            district + "," + role + ", Error Message:" +
                            e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AmpLogger.error("==>TAM - Unable to retrieve getUserReportees for district , role:" +
                            district + "," + role + ", Error Message:" +
                            e.getMessage());
            e.printStackTrace();
        }
        AmpLogger.warn("==>userListTotal.size:" + userListTotal.size() +
                       ",userIdList.size:" + userIdList.size());
        System.out.println("==>userListTotal.size:" + userListTotal.size() +
                           ",userIdList.size:" + userIdList.size());
        Collections.sort(userListTotal, new UserCompare());
        userIdList.addAll(UserAndReporteeIdList); //Reportees on the top
        userIdList.addAll(userListTotal); // district users next
        return userIdList;
    }

    /**
     *retrieveUserReporteesFromDistrictUsers
     * @param districtUsers
     * @return
     * @throws FaultMessage
     */
    public List<IdentityUserType> retrieveUserReporteesFromDistrictUsers(List<IdentityUserType> districtUsers,
                                                                         String managerId) {
        List<IdentityUserType> userReporteeList =
            new ArrayList<IdentityUserType>(districtUsers);
        for (IdentityUserType user : districtUsers) {
            if (!managerId.equalsIgnoreCase(user.getManager())) {
                userReporteeList.remove(user);
            }
        }
        return userReporteeList;
    }
 

    /**
     *getUserDetailsByUserId
     * @param userId
     * @return
     */
    public UserDetailsType getUserDetailsByUserId(String userId) {
        String inputs = " userId:" + userId;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        // TODO...retriev from SOA Proxy - Identity Service
        //Mock for now
        IdentityServiceWrapper IdentityServiceWrapper =
            new IdentityServiceWrapper();
        try {
            //Mock return value

            UserDetailsType UserDetails = new UserDetailsType();
            //  String highestRole = "Appraiser";
            if (userId.equals("mock")) {
                UserDetails.setDisplayName("Mock Name");
                UserDetails.setHighestRole("Supervisor");
                return UserDetails;
            }
            UserDetails = IdentityServiceWrapper.getUserDetails(userId);
            return UserDetails;
        } catch (Exception e) {
            AmpLogger.error("==>TAM - Unable to getUserDetailsByUserId for userId:" +
                            userId + ", Error Message:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     *getUserDistricts
     * @param userId
     * @return
     * @throws Exception
     */
    public List<String> getUserDistricts(String userId) throws Exception {
        String inputs = ",userId:" + userId;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);

        ArrayList<String> districts = new ArrayList<String>();
        IdentityServiceWrapper IdentityServiceWrapper =
            new IdentityServiceWrapper();
        if (!userId.equals("mock")) {
            return IdentityServiceWrapper.getUserDistricts(userId);
        }
        districts.add("North");
        districts.add("South");
        districts.add("HOA");

        return districts;

    }

    public List<String> getUserDistrictsByUserDtls(String userId,
                                                   UserDetailsType userDtls) throws Exception {
        IdentityServiceWrapper IdentityServiceWrapper =
            new IdentityServiceWrapper();
        return IdentityServiceWrapper.getUserDistrictsByUserDtls(userDtls);
    }
}
