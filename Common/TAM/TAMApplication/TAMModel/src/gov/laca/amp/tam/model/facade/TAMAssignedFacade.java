package gov.laca.amp.tam.model.facade;

//import com.thoughtworks.xstream.XStream;

import gov.laca.amp.fwk.extn.facade.AmpProxyFacade;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.BaseYearEventMgmtService;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.VerifyBaseYearEventRequest;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.client.gen.VerifyBaseYearEventResponse;
import gov.laca.amp.proxy.soap.baseyeareventmgmtservice.local.BaseYearEventMgmtServiceWrapper;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.WorkflowMgmtService;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveUnlockedAINRequest;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.RetrieveUnlockedAINResponse;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WFUnlockedAINListType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WFUnlockedAINType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WorkflowAssignmentType;
import gov.laca.amp.tam.model.pojo.TAMAssignmentPojo;

import gov.laca.amp.tam.model.proxy.UnverifiedAINsMgmtServiceWrapper;

import java.io.Serializable;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPFault;

public class TAMAssignedFacade  extends AmpProxyFacade {
    static AmpLogger AmpLogger = new AmpLogger(TAMAssignedFacade.class);
    public static int ASSIGNED_BATCH_COUNT = 300;    
    public TAMAssignedFacade() {
        super();
    }
    public static void main(String[] args){
        TAMAssignedFacade fac = new TAMAssignedFacade();
        try {
            fac.verifyBaseYearEvents(BigInteger.valueOf(1),"North","5022010027", "C9900036", "C9900036", "", "TAM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public UnlockedAINsRespStructure getAssignedAINs(String assignedTo, String district, String region, String cluster, Integer batchNumber) throws Exception{
        List<TAMAssignmentPojo> asssignedAINs = new ArrayList<TAMAssignmentPojo>();
        //List<TAMAssignmentPojo>
        String inputs = "assignedTo:"+assignedTo;
        AmpLogger.warn("===>Entering getAssignedAINs - inputs - "+inputs);
        UnlockedAINsRespStructure unlockedAINsRespStructure = new UnlockedAINsRespStructure();
        TAMAssignmentPojo arg =null;
        if(district.equals("mock")) {
               for(int i=0;i<20;i++){
                   if(i <3)
                   arg =  new TAMAssignmentPojo(false,new BigInteger("110000"+i),"5022010027","region"+i,"North","clstr123");                                      
                   else
                   arg = new TAMAssignmentPojo(false,new BigInteger("110000"+i),"ain"+i,"region"+i,"North","clstr123");               
                   asssignedAINs.add(arg);
               }
            unlockedAINsRespStructure.setSoaAinList(asssignedAINs);
               System.out.println("==>debug, ain:"+unlockedAINsRespStructure.getSoaAinList().get(0).getAin()+",district:"+district+",pojoList[1].aoid:"+unlockedAINsRespStructure.getSoaAinList().get(1).getAoid());
        }
        else {
                try {
                  unlockedAINsRespStructure =   getUnlockedAINs("", true, assignedTo, district, region, cluster, batchNumber,"Appraiser");
                    //asssignedAINs = unlockedAINsRespStructure.getSoaAinList();
                    AmpLogger.warn("==> assigned AINs count:"+unlockedAINsRespStructure.getSoaAinList().size());
                } catch (Exception e) {
                    AmpLogger.warn("===>Error retrieving Assigned AINs for Initiation");
                    e.printStackTrace();
                    throw new Exception("Error retrieving Assigned AINs for Initiation");
                }
        }
        return unlockedAINsRespStructure;
    }
    
    public String verifyBaseYearEvents(BigInteger tamId, String district,String ain,String assignedToId,String assignedBy,String processType,String source){
            
        //Check whether the record is still assigned to the user.
        BaseYearEventMgmtServiceWrapper wrapper = new BaseYearEventMgmtServiceWrapper();
        BaseYearEventMgmtService baseYearEventMgmtService = wrapper.getPort("BaseYearEventMgmtService_ep");
        VerifyBaseYearEventRequest VerifyBaseYearEventRequest = new VerifyBaseYearEventRequest();
        VerifyBaseYearEventRequest.setAin(ain);
        VerifyBaseYearEventRequest.setOwnerId(assignedBy);
        VerifyBaseYearEventRequest.setUserId(assignedToId);
        VerifyBaseYearEventRequest.setSource(source);
        //VerifyBaseYearEventRequest.set
        //        }
        VerifyBaseYearEventResponse resp = null;
        try {
        
         resp =    baseYearEventMgmtService.verifyBaseYearEvents(VerifyBaseYearEventRequest);
            System.out.println("==>:"+resp.getBpmStatus()+",instance id:"+resp.getBpmInstanceId());
        } catch (javax.xml.ws.soap.SOAPFaultException e ) {
            String msg = ain + ", No BPM Instance created ,ERROR -"+e.getFault().getDetail().getTextContent();
           AmpLogger.warn("===>fault Initiating:"+msg);
           AmpLogger.warn("===>fault Initiating:code:"+ e.getFault().getFaultCode()+", actor"+e.getFault().getFaultActor());
           e.printStackTrace();
            return msg;
        }
        catch(Exception e){
            String msg =ain + ", No BPM Instance created ,ERROR -"+e.getMessage();
            AmpLogger.warn("===>fault Initiating :"+msg);
            e.printStackTrace();
            return msg;
        }
          
          
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        List<WorkflowAssignmentType> workflowAssignmentList = new ArrayList<WorkflowAssignmentType>();
        WorkflowAssignmentType ainRecord = new WorkflowAssignmentType();       
           ainRecord.setTamId(tamId);
           ainRecord.setAssignmentStatus("CLAIMED");
           workflowAssignmentList.add(ainRecord);
        
        try {
            facade.upsertIntoWfMgmtTable("U",workflowAssignmentList, district);
        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            String msg =ain + ", Could not update the record in WFMGMT Table ,ERROR -"+e.getMessage();
            AmpLogger.warn("===>fault :"+msg);
            AmpLogger.warn("===>fault updating record in WFMGMT Table:code:"+ e.getFault().getFaultCode()+", actor"+e.getFault().getFaultActor());
            e.printStackTrace();
        } catch (Exception e) {
            String msg =ain + ", Could not update the record in WFMGMT Table ,ERROR -"+e.getMessage();
            AmpLogger.warn("===>fault :"+msg);
                    e.printStackTrace();
        }
        
        String displayMessage =resp.getBpmStatus();
        if(resp.getBpmStatus().toLowerCase().equals("Submitted".toLowerCase())){
            displayMessage = " Initiated " ;
        }
        if(resp.getBpmStatus().toLowerCase().equals("Not Submitted".toLowerCase())){
            displayMessage = "Failed to initiate as the AIN is locked (Process might be already running for this AIN). " ;
        }
        return resp.getAin()+","+resp.getBpmInstanceId()+","+displayMessage;
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
    public UnlockedAINsRespStructure getUnlockedAINs(String ain, Boolean isAssigned,
                                                   String assignedTo, 
                        String district, String region, String cluster,Integer batchNumber, String highestRole) throws Exception{
        String inputs = "ain:"+ain+",isAssigned:"+  isAssigned+",assignedTo:"+ assignedTo+",district:"+   district+",region:"+  region+",cluster"+  cluster+", batchNumber:"+batchNumber;
        AmpLogger.warn("==> Entering AssignedFacde:getUnlockedAINs........"+inputs);
        System.out.println("==> Entering "+new Exception().getStackTrace()[0].getMethodName()+inputs);
        UnlockedAINsRespStructure unlockedAINsRespStructure = new UnlockedAINsRespStructure();
        List<TAMAssignmentPojo> unlockedAINs = new ArrayList<TAMAssignmentPojo>();
        RetrieveUnlockedAINResponse response = null;
        
        if(district != null && district.equals("mock")){
    
    //           TAMAssignmentPojo arg1 = new TAMAssignmentPojo(false,new BigInteger("112234"),"ain1","region1","dt","clstr");
    //           TAMAssignmentPojo arg2 = new TAMAssignmentPojo(false,new BigInteger("112235"),"ain1","region1","dt","clstr");
    //           unlockedAINs.add(arg0);
    //           unlockedAINs.add(arg1);
    //           unlockedAINs.add(arg2);
           for(int i=0;i<100;i++){
               TAMAssignmentPojo arg = new TAMAssignmentPojo(false,new BigInteger("110000"+i),"ain"+i,"region"+i,"North","clstr123");               
               unlockedAINs.add(arg);
           }
           System.out.println("==>debug, ain:"+unlockedAINs.get(0).getAin()+",district:"+district+",pojoList[1].aoid:"+unlockedAINs.get(1).getAoid());
         }
        else {
           // XStream xstream = new XStream();
            UnverifiedAINsMgmtServiceWrapper wrapper = new UnverifiedAINsMgmtServiceWrapper();
            WorkflowMgmtService port = wrapper.getPort();
            RetrieveUnlockedAINRequest request = new RetrieveUnlockedAINRequest();
            request.setAin(ain);
            //Portal login Id is upper case, and LDAP retrieval Id is in lowercase. convert to lower case while storing and retrieving to be consistent
            if(assignedTo != null)
            request.setAssignedTo(assignedTo/*.toLowerCase()*/);
            request.setDistrict(district);
            request.setRegion(region);
            request.setCluster(cluster);
            BigInteger startIdx = BigInteger.valueOf(1); //BigInteger.valueOf((batchNumber - 1)*ASSIGNED_BATCH_COUNT +1);
            
            BigInteger endIdx   = BigInteger.valueOf(ASSIGNED_BATCH_COUNT + (batchNumber - 1)*100);
            request.setRangeStart(startIdx);
            request.setRangeEnd(endIdx);
            request.setAssignedFlag(isAssigned == true ? "Y" : "N");
            
            response = port.retrieveUnlockedAIN(request);
            String ainOut = response.getAin();
          //  System.out.println("==>XML resp:"+ xstream.toXML(response));
          System.out.println("==>response -AssignedFacde:getUnlockedAINs, Range:"+response.getRangeStart()+","+response.getRangeEnd());
           WFUnlockedAINListType WFUnlockedAINListType = response.getUnlockedAINList();
            List<WFUnlockedAINType> ainList = WFUnlockedAINListType.getUnlockedAIN();
            TAMAssignmentFacade assignmentFacade = new TAMAssignmentFacade();
            for(WFUnlockedAINType ainRow: ainList){
                //TAMAssignmentPojo arg = new TAMAssignmentPojo(false,ainRow.getAoid(),ainRow.getAin(),ainRow.getRegion(),ainRow.getDistrictName(),ainRow.getClusterRouteName());               
                TAMAssignmentPojo arg = assignmentFacade.transformTAMAssignmentPojo(ainRow,highestRole);     
                //Fixed 10796 OATS(Solution:remove BYE=0 AINs) - Undoing this fix . This should be fixed in DB/PLSQL
               // if(arg.getByeCount().longValue() > 0)
                  unlockedAINs.add(arg);
                
               }
            System.out.println("==>unlockedAINs.size():"+unlockedAINs.size()+",currentBatch:"+startIdx+","+endIdx);
        }
        unlockedAINsRespStructure.setSoaAinList(unlockedAINs);
        unlockedAINsRespStructure.setStartRange(response.getRangeStart().intValue());
        if(response.getRangeEnd() != null)
          unlockedAINsRespStructure.setEndRange(response.getRangeEnd().intValue());
        else unlockedAINsRespStructure.setEndRange(null);
        
        if(response.getTotalCount() != null) {
            unlockedAINsRespStructure.setTotalAssignedCount(response.getTotalCount().intValue()); 
        }
       
        return unlockedAINsRespStructure;
    }
}
