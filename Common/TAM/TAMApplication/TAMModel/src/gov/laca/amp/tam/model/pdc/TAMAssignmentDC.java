package gov.laca.amp.tam.model.pdc;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.ent.util.AmpLogUtil;
import gov.laca.amp.fwk.extn.dc.AmpPojoDC;

import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserType;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.FaultMessage;
import gov.laca.amp.proxy.soap.tam.unlockedainsmgmtsvc.client.gen.WorkflowAssignmentType;
import gov.laca.amp.tam.model.facade.TAMAssignmentFacade;
import gov.laca.amp.tam.model.facade.UnlockedAINsRespStructure;
import gov.laca.amp.tam.model.pojo.TAMAssignmentPojo;

import gov.laca.amp.tam.model.util.TAMModelUtils;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

public class TAMAssignmentDC extends AmpPojoDC {
    static AmpLogger AmpLogger = new AmpLogger(TAMAssignmentDC.class);
    @SuppressWarnings("compatibility:-2374982841208086043")
    private static final long serialVersionUID = 1L;

    public TAMAssignmentDC() {
        super();
    }

    public static void main(String[] args) throws Exception {
        TAMAssignmentDC dc = new TAMAssignmentDC();
        dc.districtL = "North District Office";
        // dc.assignedToL = "mock";
        // dc.currentBatchNumber = 1;
        dc.searchAINs("", false, "", dc.districtL, "", "", "", "","");
        dc.nextSearchAINs();
        dc.previousSearchAINs();
        dc.nextSearchAINs();
    }
    private String totalAssignedCount;
    private Boolean hasMoreRows = false;
    private String ainL;
    private Boolean isAssignedL;
    private String assignedToL;
    private String assignedToRoleL;
    private String districtL;
    private String regionL;
    private String clusterL;
    private Integer currentBatchNumber = 1;
    private String highestRoleL;
    private List<TAMAssignmentPojo> tamAssignmentPojoList =
        new ArrayList<TAMAssignmentPojo>();
    private List<TAMAssignmentPojo> tamAssignmentAccumulatedPojoList =
        new ArrayList<TAMAssignmentPojo>();
    private List<String> userIdNamesList = new ArrayList<String>();


    public TAMAssignmentDC(List<TAMAssignmentPojo> tamAssignmentPojoList) {
        super();
        this.tamAssignmentPojoList = tamAssignmentPojoList;
    }

    private List<TAMAssignmentPojo> tamSelectedAINsPojoList =
        new ArrayList<TAMAssignmentPojo>();

    public void setTamSelectedAINsPojoList(List<TAMAssignmentPojo> tamSelectedAINsPojoList) {
        this.tamSelectedAINsPojoList = tamSelectedAINsPojoList;
    }

    public List<TAMAssignmentPojo> getTamSelectedAINsPojoList() {
        return tamSelectedAINsPojoList;
    }

    public void selectAllRows() {

        for (TAMAssignmentPojo pojo : this.tamAssignmentPojoList) {
            if(pojo.getIsReassignAllowed())
            pojo.setSelected(true);
        }
    }

    public void deSelectAllRows() {

        for (TAMAssignmentPojo pojo : this.tamAssignmentPojoList) {
            pojo.setSelected(false);

        }
    }

    //Search Method

    /**
     *
     * @param ain
     * @param isAssigned
     * @param assignedTo
     * @param district
     * @param region
     * @param cluster
     * @return
     * @throws Exception
     */
    public List<TAMAssignmentPojo> searchAINs(String ain, Boolean isAssigned,
                                              String assignedTo,String assignedToRole,
                                              String district, String region,
                                              String cluster,
                                              String loggedInUserId,
                                              String highestRole) throws Exception {
        //String inputs="";
        this.setCurrentBatchNumber(1);
        UnlockedAINsRespStructure unlockedAINsRespStructure = null;
        String inputs =
            "ain:" + ain + ",district+:" + district + ", region+:" + region +
            ",cluster: " + cluster + ",ain: " + ain + ",isAssigned: " +
            isAssigned + ",assignedTo:" + assignedTo+ ",assignedToRole:" + assignedToRole;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        if (district == null) {
            throw new Exception("Enter a value for district");
        }
        //     if(isAssigned == true && assignedTo == null){
        //         throw new Exception("Enter a value for 'Assigned To' when 'Is Assigned' flag is checked");
        //     }
        //        String assignedToId = null;
        //        if(isAssigned && assignedTo != null) {
        //           assignedToId = (String)(assignedTo.split("-"))[0];
        //        }
        this.setDistrictL(district);
        this.setRegionL(region);
        this.setAinL(ain);
        this.setIsAssignedL(isAssigned);
        this.setAssignedToL(assignedTo);
        this.setAssignedToRoleL(assignedToRole);
        this.setClusterL(cluster);
        this.setHighestRoleL(highestRole); 
        System.out.println("==>DEBUG , In Search before calling getUnlocked()...assignedTo:" +
                           assignedTo + ",assignedToL:" + assignedToL);
        TAMAssignmentFacade facade = new TAMAssignmentFacade();

        unlockedAINsRespStructure =
                facade.getUnlockedAINs(ainL, isAssignedL, assignedToL, assignedToRoleL,
                                       districtL, regionL, clusterL,
                                       this.getCurrentBatchNumber(),this.highestRoleL);
        tamAssignmentPojoList = unlockedAINsRespStructure.getSoaAinList();
        if (unlockedAINsRespStructure.getTotalAssignedCount() != null)
            this.setTotalAssignedCount(unlockedAINsRespStructure.getTotalAssignedCount().toString());

        this.tamAssignmentAccumulatedPojoList = this.tamAssignmentPojoList;

        AmpLogger.warn("==> Exiting " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Exiting " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        //Reset preview table for every new search
        this.tamSelectedAINsPojoList = new ArrayList<TAMAssignmentPojo>();
        // Calculate whether hasMore rows
        if (unlockedAINsRespStructure.getEndRange() -
            unlockedAINsRespStructure.getStartRange() > 98) {
            this.setHasMoreRows(true);
        } else {
            this.setHasMoreRows(false);
        }
        return this.tamAssignmentPojoList;
    }
    //Next Search

    /**
     *
     * @return
     * @throws Exception
     */
    public List<TAMAssignmentPojo> nextSearchAINs() throws Exception {
        long accumulatedSize = tamAssignmentAccumulatedPojoList.size();
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           ",districtL:" + districtL + ",currentBatchNumber:" +
                           currentBatchNumber + ", accumulated.size:" +
                           accumulatedSize);
        UnlockedAINsRespStructure unlockedAINsRespStructure = null;
        if (this.districtL == null) {
            throw new Exception("ERROR: Search before clicking 'Next' button");
        }

        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        // this.tamAssignmentPojoList = new ArrayList<TAMAssignmentPojo>();
        if (tamAssignmentAccumulatedPojoList.size() >
            currentBatchNumber * 100) {
            ++currentBatchNumber;
            int nextBatchEndIdx = (int)((accumulatedSize > currentBatchNumber*100 -1) ? (currentBatchNumber*100) : accumulatedSize );
            tamAssignmentPojoList =
                 tamAssignmentAccumulatedPojoList.subList((currentBatchNumber - 1) * 100, nextBatchEndIdx);
            AmpLogger.warn("==> Buffered AINs NEXT, currentBatchNumber:" + currentBatchNumber+",TAMAssignmentDC.size():" +
                               tamAssignmentPojoList.size());
            this.setHasMoreRows(accumulatedSize > currentBatchNumber*100 -1 ? true : false);            
        } else {
            unlockedAINsRespStructure =
                    facade.getUnlockedAINs(ainL, isAssignedL, assignedToL,assignedToRoleL,
                                           districtL, regionL, clusterL,
                                           ++currentBatchNumber, this.highestRoleL);
            this.tamAssignmentPojoList =
                    unlockedAINsRespStructure.getSoaAinList();
            this.tamAssignmentAccumulatedPojoList.addAll(this.tamAssignmentPojoList);
            AmpLogger.warn("==>new Fetch for the NEXT from SOA currentBatchNumber:" + currentBatchNumber+",TAMAssignmentDC.size():" +
                               tamAssignmentPojoList.size());   
            this.setHasMoreRows(unlockedAINsRespStructure.getEndRange() == null ?
                            false : unlockedAINsRespStructure.getEndRange() - unlockedAINsRespStructure.getStartRange() > 98);
        }
        return this.tamAssignmentPojoList;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<TAMAssignmentPojo> previousSearchAINs() throws Exception {
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           ",districtL:" + districtL + ",currentBatchNumber:" +
                           currentBatchNumber + ", accumulated.size:" +
                           tamAssignmentAccumulatedPojoList.size());

        if (this.districtL == null) {
            throw new Exception("ERROR: Search before clicking 'Next' button");
        }
        if (this.getCurrentBatchNumber() > 1) {
            --currentBatchNumber;
            this.tamAssignmentPojoList = new ArrayList<TAMAssignmentPojo>();
            int currentBatchStartIdx = (TAMAssignmentFacade.BATCH_COUNT * (currentBatchNumber - 1));
            int currentBatchEndIdx = (TAMAssignmentFacade.BATCH_COUNT * currentBatchNumber) - 1;
            if (this.tamAssignmentAccumulatedPojoList.size() <= currentBatchEndIdx){
                currentBatchEndIdx = this.tamAssignmentAccumulatedPojoList.size() -1;
            }
            for (int i = currentBatchStartIdx; i <= currentBatchEndIdx; i++) {
                TAMAssignmentPojo pojoObj = null;
                if (this.tamAssignmentAccumulatedPojoList.size() > 0) {
                    pojoObj = this.tamAssignmentAccumulatedPojoList.get(i);
                    if (pojoObj != null) {
                        this.tamAssignmentPojoList.add(pojoObj);
                       // System.out.println("==>PREV action - added i:"+i+",ain:"+pojoObj.getAin()+",currentBatchEndIdx:"+currentBatchEndIdx);
                    }
                } else {
                    break;
                }

            }
        } else {
            throw new Exception("No more previous records !");
        }
        this.setHasMoreRows(true);
        return this.tamAssignmentPojoList;
    }


    /**
     *
     */
    public String selectAINs() throws Exception {
        int i = 0;
        String inputs =
            "tamAssignmentPojoList.size():" + tamAssignmentPojoList.size();
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        this.tamSelectedAINsPojoList = new ArrayList<TAMAssignmentPojo>();
        //      for( TAMAssignmentPojo pojo : this.tamAssignmentPojoList) {
        for (TAMAssignmentPojo pojo : this.tamAssignmentAccumulatedPojoList) {
            i++;
            if (null != pojo) {
                if (pojo.getSelected() != null)
                    if (pojo.getSelected() == true) {
//                        System.out.println("==>Selected record will be DISPLAYED in the selected table ain:" +
//                                           pojo.getAin() + ",index:" + i);
                        tamSelectedAINsPojoList.add(pojo);
                    }
            }
        }
        if (this.tamSelectedAINsPojoList.size() <= 0) {

            return " Select one or more rows to preview ";
        }
        // Get the selected rows and fill in to the wfList

        //            facade.upsertIntoWfMgmtTable(transactionType,
        //                                         workflowAssignmentList, district);
        AmpLogger.warn("==> Exiting " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Exiting " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        return null;
    }

    /**
     *
     * @param assignmentStatus
     * @param assignedTo
     * @param assignedToRole
     * @param assignedBy
     * @param assignedByRole
     * @param district
     * @throws Exception
     */
    public void assignAINsToUser(String assignmentStatus, String assignedTo,
                                 String assignedToRole, String assignedBy,
                                 String assignedByRole,
                                 String district) throws Exception {
        String inputs =
            "+assignmentStatus+:" + assignmentStatus + ", assignedTo+:" +
            assignedTo + ",assignedToRole: " + assignedToRole +
            ",assignedBy: " + assignedBy + ",assignedByRole: " +
            assignedByRole + ",district: " + district;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        //  String assignedToId = (String)(assignedTo.split("-")[0]);
        upsertSelectedUnlockedAINsInWfMgmtTable("C", assignmentStatus,
                                                assignedTo, assignedToRole,
                                                assignedBy, assignedByRole,
                                                "BVM", "TAM", district);
    }

    /**
     *
     * @param assignmentStatus
     * @param assignedTo
     * @param assignedToRole
     * @param assignedBy
     * @param assignedByRole
     * @param district
     * @throws Exception
     */
    public void reassignAINsToUser(String assignmentStatus, String assignedTo,
                                   String assignedToRole, String assignedBy,
                                   String assignedByRole,
                                   String district) throws Exception {
        String inputs =
            "+assignmentStatus+:" + assignmentStatus + ", assignedTo+:" +
            assignedTo + ",assignedToRole: " + assignedToRole +
            ",assignedBy: " + assignedBy + ",assignedByRole: " +
            assignedByRole + ",district: " + district;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        // String assignedToId = (String)(assignedTo.split("-")[0]);
        upsertSelectedUnlockedAINsInWfMgmtTable("U", assignmentStatus,
                                                assignedTo, assignedToRole,
                                                assignedBy, assignedByRole,
                                                "BVM", "TAM", district);
    }

    /**
     *
     * @param assignmentStatus
     * @param assignedTo
     * @param assignedToRole
     * @param assignedBy
     * @param assignedByRole
     * @param district
     * @throws Exception
     */
    public void unassignAINsToUser(String assignmentStatus, String assignedTo,
                                   String assignedToRole, String assignedBy,
                                   String assignedByRole,
                                   String district) throws Exception {
        String inputs =
            "+assignmentStatus+:" + assignmentStatus + ", assignedTo+:" +
            assignedTo + ",assignedToRole: " + assignedToRole +
            ",assignedBy: " + assignedBy + ",assignedByRole: " +
            assignedByRole + ",district: " + district;
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           inputs);
        upsertSelectedUnlockedAINsInWfMgmtTable("D", assignmentStatus,
                                                assignedTo, assignedToRole,
                                                assignedBy, assignedByRole,
                                                "BVM", "TAM", district);
    }

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<String> getDistrictsByUserId(String userId) throws Exception {
        List<String> districts = new ArrayList<String>();
        TAMAssignmentFacade facade = new TAMAssignmentFacade();

        try {
            districts = facade.getUserDistricts(userId);
        } catch (Exception e) {
            AmpLogger.error("==> Error getting districts by userId:" + userId,
                            e);
            throw new Exception(e.getLocalizedMessage());
        }
        return districts;
    }
    //retrieve List of Regions

    /**
     *
     * @param district
     * @return
     */
    public List<String> getRegionsByDistrict(String district) throws Exception {
        ArrayList<String> regions = new ArrayList<String>();
        TAMAssignmentFacade facade = new TAMAssignmentFacade();

        //        return regions;


        return facade.getRegionsByDistrict(district);

    }
    //retrieve List of Clusters

    public List<String> getClustersByRegion(String district, String region) {
        ArrayList<String> clusters = new ArrayList<String>();
        TAMAssignmentFacade facade = new TAMAssignmentFacade();

        return facade.getClustersByRegion(district, region);
        //        return clusters;

    }

    /**
     *
     * @param district
     * @param role
     * @param loggedInUserId
     * @return
     */
    public List<String> getUsersListByRole(String district, String role,
                                           String loggedInUserId,
                                           String loggedInUserName) {
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        List<String> userList = new ArrayList<String>();
        userList =
                facade.getReporteesAndDistrictUsersListByRole(district, role,
                                                              loggedInUserId,
                                                              loggedInUserName);
        return userList;
    }


    //Method from Base class

    protected Object loadData(Object object) throws AmpException {
        return null;
    }

    /**
     *
     * @param tamAssignmentPojoList
     */
    public void setTamAssignmentPojoList(List<TAMAssignmentPojo> tamAssignmentPojoList) {
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());
        this.tamAssignmentPojoList = tamAssignmentPojoList;
    }

    /**
     *
     * @return
     */
    public List<TAMAssignmentPojo> getTamAssignmentPojoList() {
        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName());
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName());
        return tamAssignmentPojoList;
    }


    private String upsertSelectedUnlockedAINsInWfMgmtTable(String transactionType,
                                                           String assignmentStatus,
                                                           String assignedTo,
                                                           String assignedToRole,
                                                           String assignedBy,
                                                           String assignedByRole,
                                                           String processType,
                                                           String source,
                                                           String district) throws Exception {
        String inputs =
            "+assignmentStatus+:" + assignmentStatus + ", assignedTo+:" +
            assignedTo + ",assignedToRole: " + assignedToRole +
            ",assignedBy: " + assignedBy + ",assignedByRole: " +
            assignedByRole + ",district: " + district;

        AmpLogger.warn("==> Entering " +
                       new Exception().getStackTrace()[0].getMethodName() +
                       ", inputs:" + inputs);
        System.out.println("==> Entering " +
                           new Exception().getStackTrace()[0].getMethodName() +
                           ", inputs:" + inputs);
        if (assignedTo == null) {
            throw new Exception("ERROR: Select an Assignee before assigning");
        }
        String msg = "";
        TAMAssignmentFacade facade = new TAMAssignmentFacade();
        // WorkflowAssignmentType ainRecord = new WorkflowAssignmentType();
        List<WorkflowAssignmentType> workflowAssignmentList =
            new ArrayList<WorkflowAssignmentType>();
        String derivedAssignedToRole = "";
            
            if(assignedToRole == null || assignedToRole.equals("")){
                derivedAssignedToRole = TAMModelUtils.deriveAssignedToRole(assignedByRole);
              }
            else {
                derivedAssignedToRole = assignedToRole;
             }
        System.out.println("==>tamSelectedAINsPojoList.count:" +
                           tamSelectedAINsPojoList.size());
        int ii = 0;
        for (TAMAssignmentPojo pojo : this.tamSelectedAINsPojoList) {
            ii++;
            //          if(null != pojo) {
            //        if(pojo.getSelected() != null )
            //            if( pojo.getSelected() == true){
            System.out.println("==>Selected record will be upserted ain:" +
                               pojo.getAin() + ",index:" + ii);
            WorkflowAssignmentType ainRecord = new WorkflowAssignmentType();
            ainRecord.setAssignedBy(assignedBy);
            ainRecord.setAssignedByRole(assignedByRole);

            ainRecord.setAssignedTo(assignedTo);
            ainRecord.setAssignedToRole(derivedAssignedToRole);
            ainRecord.setAssignmentStatus(assignmentStatus);
            ainRecord.setProcessType(processType);
            ainRecord.setSource(source);
            ainRecord.setAin(pojo.getAin());
            ainRecord.setAoid(pojo.getAoid());
            if (!transactionType.equals("C")) {
                ainRecord.setTamId(pojo.getTamId());
            }
            workflowAssignmentList.add(ainRecord);
            System.out.println("==>UPDATES/DELETES, provide, ii:" + ii +
                               ", tamId:" + pojo.getTamId());
            //           }
            //          }
        }
        // Get the selected rows and fill in to the wfList
        try {
            facade.upsertIntoWfMgmtTable(transactionType,
                                         workflowAssignmentList, district);
        } catch (Exception e) {
            System.out.println("==>exception msg:" + e.getMessage());
            e.printStackTrace();
            throw new Exception("Exception invoking web service exception message:" +
                                e.getMessage());
        }

        //Delete the assigned records
        int jj = 0;
        for (TAMAssignmentPojo selectedPojo : this.tamSelectedAINsPojoList) {
            jj++;
            System.out.println("==>Selected record was assigned and will be deleted from the table ain:" +
                               selectedPojo.getAin() + ",index:" + jj);
            tamAssignmentPojoList.remove(selectedPojo);
            tamAssignmentAccumulatedPojoList.remove(selectedPojo);
        }
        this.tamSelectedAINsPojoList = new ArrayList<TAMAssignmentPojo>();
        AmpLogger.warn("==>tamSelectedAINsPojoList.size():"+tamSelectedAINsPojoList.size());
        AmpLogger.warn("==>tamAssignmentAccumulatedPojoList.size():"+tamAssignmentAccumulatedPojoList.size());
        return msg;
    }


    public void setCurrentBatchNumber(Integer currentBatchNumber) {
        this.currentBatchNumber = currentBatchNumber;
    }

    public Integer getCurrentBatchNumber() {
        return currentBatchNumber;
    }

    public void setTamAssignmentAccumulatedPojoList(List<TAMAssignmentPojo> tamAssignmentAccumulatedPojoList) {
        this.tamAssignmentAccumulatedPojoList =
                tamAssignmentAccumulatedPojoList;
    }

    public List<TAMAssignmentPojo> getTamAssignmentAccumulatedPojoList() {
        return tamAssignmentAccumulatedPojoList;
    }

    public void setAinL(String ainL) {
        this.ainL = ainL;
    }

    public String getAinL() {
        return ainL;
    }

    public void setIsAssignedL(Boolean isAssignedL) {
        this.isAssignedL = isAssignedL;
    }

    public Boolean getIsAssignedL() {
        return isAssignedL;
    }

    public void setAssignedToL(String assignedToL) {
        this.assignedToL = assignedToL;
    }

    public String getAssignedToL() {
        return assignedToL;
    }

    public void setDistrictL(String districtL) {
        this.districtL = districtL;
    }

    public String getDistrictL() {
        return districtL;
    }

    public void setRegionL(String regionL) {
        this.regionL = regionL;
    }

    public String getRegionL() {
        return regionL;
    }

    public void setClusterL(String clusterL) {
        this.clusterL = clusterL;
    }

    public String getClusterL() {
        return clusterL;
    }

    public void setUserIdNamesList(List<String> userIdNamesList) {
        this.userIdNamesList = userIdNamesList;
    }

    public List<String> getUserIdNamesList() {
        return userIdNamesList;
    }

    public void setHasMoreRows(Boolean hasMoreRows) {
        this.hasMoreRows = hasMoreRows;
    }

    public Boolean getHasMoreRows() {
        return hasMoreRows;
    }

    public void setTotalAssignedCount(String totalAssignedCount) {
        this.totalAssignedCount = totalAssignedCount;
    }

    public String getTotalAssignedCount() {
        return totalAssignedCount;
    }

    public void setHighestRoleL(String highestRoleL) {
        this.highestRoleL = highestRoleL;
    }

    public String getHighestRoleL() {
        return highestRoleL;
    }

    public void setAssignedToRoleL(String assignedToRoleL) {
        this.assignedToRoleL = assignedToRoleL;
    }

    public String getAssignedToRoleL() {
        return assignedToRoleL;
    }
}
