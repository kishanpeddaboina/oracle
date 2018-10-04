package gov.laca.amp.tam.model.pdc;

import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsType;
import gov.laca.amp.tam.model.facade.TAMAssignedFacade;
import gov.laca.amp.tam.model.facade.TAMAssignmentFacade;
import gov.laca.amp.tam.model.facade.UnlockedAINsRespStructure;
import gov.laca.amp.tam.model.pojo.TAMAssignmentPojo;

import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

public class TAMAssignedDC {
    static AmpLogger AmpLogger = new AmpLogger(TAMAssignedDC.class);

    public TAMAssignedDC() {
        super();
        //        for (int i=0;i<20; i++){
        //            TAMAssignmentPojo arg = new  TAMAssignmentPojo(false,new BigInteger("110000"+i),"ain"+i,"region"+i,"North","clstr123");
        //            tamAssigedPojoList.add(arg);
        //        }


    }

    private List<TAMAssignmentPojo> tamAssigedPojoList =
        new ArrayList<TAMAssignmentPojo>();
    private String userHighestRole;
    private String loggedInUserId;
    private String loggedInUserDisplayName;
    private Integer totalInitiatableAssignedCount;
    private List<String> userDistricts;
    private List<String> userDistrictsToInitiate;


    public TAMAssignedDC(List<TAMAssignmentPojo> tamAssigedPojoList) {
        super();
        this.tamAssigedPojoList = tamAssigedPojoList;
    }


    public void setTamAssigedPojoList(List<TAMAssignmentPojo> tamAssigedPojoList) {
        this.tamAssigedPojoList = tamAssigedPojoList;
    }

    public List<TAMAssignmentPojo> getTamAssigedPojoList() {
        return tamAssigedPojoList;
    }

    /**
     *
     * @param assignedTo
     * @param district
     * @param region
     * @param cluster
     * @return
     * @throws Exception
     */
    public List<TAMAssignmentPojo> retrieveAssignedAINs(String assignedTo,
                                                        String district,
                                                        String region,
                                                        String cluster, Integer batchNumber) throws Exception {
        String inputs =
            "assignedTo:" + assignedTo + ",district:" + district + ",region:" +
            region + ",cluster:" + cluster;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        if (district == null) {
            throw new Exception("Enter a value for district");
        }

        TAMAssignedFacade facade = new TAMAssignedFacade();
         UnlockedAINsRespStructure unlockedAINsRespStructure = facade.getAssignedAINs(assignedTo, district, region, cluster, batchNumber);
         this.tamAssigedPojoList = unlockedAINsRespStructure.getSoaAinList();
         this.setTotalInitiatableAssignedCount(unlockedAINsRespStructure.getTotalAssignedCount());
        return this.tamAssigedPojoList;
    }

    /**
     *
     * @param ain
     * @param aoid
     * @param userId
     * @param ownerId
     * @param processType
     * @param source
     * @return
     */
    public String initateBaseYearEventMgmtService(String ain, Long aoid,
                                                  String userId,
                                                  String ownerId,
                                                  String processType,
                                                  String source) {
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> initateBaseYearEventMgmtService- aoid:" +
                           aoid + ",ownerId:" + ownerId + ",userId:" + userId +
                           "processType:" + processType + ",source:" + source);

        return "";
    }

    /**
     *
     * @param userId
     * @throws Exception
     */
    public void executeInitializations(String userId) throws Exception {
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           ",userId:" + userId);
        this.setLoggedInUserId(userId);
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        UserDetailsType userDetails = facade.getUserDetailsByUserId(userId);
        this.setUserHighestRole(userDetails.getHighestRole());
        this.setLoggedInUserDisplayName(userDetails.getDisplayName());
        this.getUserDistrictsByUserDtls(userHighestRole, userId, userDetails);
        System.out.println("==>userhighestRole:" + this.getUserHighestRole() +
                           ",displayName:" + userDetails.getDisplayName());
        AmpLogger.warn("==>userhighestRole:" + this.getUserHighestRole() +
                       ",displayName:" + userDetails.getDisplayName());
        this.retrieveAssignedAINs(userId.toLowerCase() + "-" +
                                  this.getLoggedInUserDisplayName(), "", "",
                                  "",1);
    }

    /**
     *
     * @param userHighestRole
     */
    public List<String> getUserDistrictsByUserDtls(String userHighestRole,
                                                   String userId,
                                                   UserDetailsType userDetails) {
        String inputs = userHighestRole + "," + userId;
        AmpLogger.warn("===>Entering, TAMAssignedDC:getUserDistricts(" +
                       inputs + ")");


        if (userDistricts == null || userDistricts.size() < 1) {
            userDistricts = new ArrayList<String>();
            TAMAssignmentFacade facade = new TAMAssignmentFacade();
            try {
                System.out.println("==>userHighestRole:" + userHighestRole);
                ArrayList<String> ldapUserDistricts =
                    (ArrayList<String>)facade.getUserDistrictsByUserDtls(userId,
                                                                         userDetails);  
                this.userDistrictsToInitiate = new ArrayList<String>(ldapUserDistricts);
                if (userHighestRole.equals("Supervisor")) {
                    for (String ldapDst : ldapUserDistricts) {
                        userDistricts.add(TAMModelUtils.LDAP_TO_TAM_DISTRICTS_MAP.get(ldapDst));
                    }
                } else {
                    userDistricts = TAMModelUtils.ALL_TAM_DISTRICTS_LIST;
                }
            } catch (Exception e) {
                JSFUtils.addFacesErrorMessage("Error loading Districts, ERROR:" +
                                              e.getMessage());
                e.printStackTrace();
            }
            AmpLogger.warn("===>Exiting, TAMAssignedDC:getUserDistricts" +
                           userDistricts);
        }
        return userDistricts;
    }

    public String verifyBaseYearEvents(BigInteger tamId, String district,
                                       String ain, String assignedToId,
                                       String assignedById, String processType,
                                       String source) {
        TAMAssignedFacade facade = new TAMAssignedFacade();
        String wsResponse = "";
        wsResponse =
                facade.verifyBaseYearEvents(tamId, district, ain, assignedToId,
                                            assignedById, processType, source);
        return wsResponse;
    }

    public void setUserHighestRole(String userHighestRole) {
        this.userHighestRole = userHighestRole;
    }

    public String getUserHighestRole() {
        return userHighestRole;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserDisplayName(String loggedInUserDisplayName) {
        this.loggedInUserDisplayName = loggedInUserDisplayName;
    }

    public String getLoggedInUserDisplayName() {
        return loggedInUserDisplayName;
    }

    public void setUserDistricts(ArrayList<String> userDistricts) {
        this.userDistricts = userDistricts;
    }

    public List<String> getUserDistricts() {
        return userDistricts;
    }

    public void setUserDistrictsToInitiate(List<String> userDistrictsToInitiate) {
        this.userDistrictsToInitiate = userDistrictsToInitiate;
    }

    public List<String> getUserDistrictsToInitiate() {
        return userDistrictsToInitiate;
    }

    public void setTotalInitiatableAssignedCount(Integer totalInitiatableAssignedCount) {
        this.totalInitiatableAssignedCount = totalInitiatableAssignedCount;
    }

    public Integer getTotalInitiatableAssignedCount() {
        return totalInitiatableAssignedCount;
    }
}
