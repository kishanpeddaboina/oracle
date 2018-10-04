package gov.laca.amp.proxy.soap.identitylookupservice.local;
import gov.laca.amp.proxy.soap.identitylookupservice.IdentityUserWrapper;
import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.DistrictUserListType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.IdentityGroupType;
import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.IdentityUserType;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IdentityLookupServiceWrapperTest {
    private IdentityLookupServiceWrapper serviceWrapper = new IdentityLookupServiceWrapper();
    
    public IdentityLookupServiceWrapperTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveUserDetails(String)
     */
    @Test
    public void testRetrieveUserDetails() throws FaultMessage {
        String userId = "C9900015";
        UserDetailsType userDetails =
            serviceWrapper.retrieveUserDetails(userId);
        System.out.println("User Details Title: " + userDetails.getTitle());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveUserReportees(String,String)
     */
    @Test
    public void testRetrieveUserReportees() throws FaultMessage {
        String userId = "C9900015";
        String upToLevel = "1";
        
        List<IdentityUserType> userReportees = serviceWrapper.retrieveUserReportees(userId, upToLevel);
        System.out.println("User Reportees Count: " + userReportees.size());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveIdentityGroupsByUserId(String)
     */
    @Test
    public void testRetrieveIdentityGroupsByUserId() throws FaultMessage {
        String userId = "C9900015";
        List<IdentityGroupType> userGroups =
            serviceWrapper.retrieveIdentityGroupsByUserId(userId);
        System.out.println("User Groups by UserId: " + userGroups.size());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveIdentityGroupsByGroupName(String)
     */
    @Test
    public void testRetrieveIdentityGroupsByGroupName() throws FaultMessage {
        String groupName = "SupervisingAppraiserWest";
        List<IdentityGroupType> userGroups =
                serviceWrapper.retrieveIdentityGroupsByGroupName(groupName);
        System.out.println("User Groups by GroupName: " + userGroups.size());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveIdentityGroupsByUserUniqueName(String)
     */
    @Test
    public void testRetrieveIdentityGroupsByUserUniqueName() throws FaultMessage {
        String userUniqueName =
            "cn=VivekAchariya SUPERVISING APPRAISER West,ou=Assessor,ou=LA County,cn=Users,dc=amp,dc=lacounty,dc=gov";
        List<IdentityGroupType> userGroups =
                serviceWrapper.retrieveIdentityGroupsByUserUniqueName(userUniqueName);
        System.out.println("User Groups by UniqueName: " + userGroups.size());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveIdentityGroupsByRole(String)
     */
    @Test
    public void testRetrieveIdentityGroupsByRole() throws FaultMessage {
        String roleName = "Supervisor";
        List<IdentityGroupType> userGroups = serviceWrapper.retrieveIdentityGroupsByRole(roleName);
        System.out.println("User Groups by RoleName: " + userGroups.size());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveDistrictUsers(String,String)
     */
    @Test
    public void testRetrieveDistrictUsers() throws FaultMessage {
        String districtName = "West";
        String districtUserRoleName = "Appraiser";
        DistrictUserListType districtUserList =
            serviceWrapper.retrieveDistrictUsers(districtName,
                                                 districtUserRoleName);
        System.out.println("District Users: " + districtUserList.getCount());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveDistrictUsers(String,java.util.List<java.lang.String>)
     */
    @Test
    public void testRetrieveDistrictUsers1() throws FaultMessage {
        String districtName = "West";
        List<String> roleNameList = new ArrayList<String>();
        roleNameList.add("Appraiser");
        roleNameList.add("Principal Appraiser");
        DistrictUserListType districtUserList =
                serviceWrapper.retrieveDistrictUsers(districtName, roleNameList);
        System.out.println("District Users: " + districtUserList.getCount());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveDistrictUsers(java.util.List<java.lang.String>,java.util.List<java.lang.String>)
     */
    @Test
    public void testRetrieveDistrictUsers2() throws FaultMessage {
        List<String> districtNameList = new ArrayList<String>();
        districtNameList.add("East");
        districtNameList.add("West");

        List<String> roleNameList = new ArrayList<String>();
        roleNameList.add("Appraiser");
        roleNameList.add("Principal Appraiser");

        DistrictUserListType districtUserList =
                serviceWrapper.retrieveDistrictUsers(districtNameList,
                                                     roleNameList);
        System.out.println("District Users: " + districtUserList.getCount());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveDistrictUsers(java.util.HashMap<java.lang.String, java.util.List<java.lang.String>>)
     */
    @Test
    public void testRetrieveDistrictUsers3() throws FaultMessage {
        HashMap<String, List<String>> districtRoleMap =
            new HashMap<String, List<String>>();

        List<String> roleNameList = new ArrayList<String>();
        roleNameList.add("Appraiser");
        roleNameList.add("Principal Appraiser");

        districtRoleMap.put("East", roleNameList);
        districtRoleMap.put("West", roleNameList);

        DistrictUserListType districtUserList =
            serviceWrapper.retrieveDistrictUsers(districtRoleMap);
        System.out.println("District Users: " + districtUserList.getCount());
    }

    /**
     * @see IdentityLookupServiceWrapper#retrieveDistrictUsersAndReportees(String,String,java.util.HashMap<java.lang.String, java.util.List<java.lang.String>>)
     */
    @Test
    public void testRetrieveDistrictUsersAndReportees() throws FaultMessage {
        String userId = "C9900015";
        String upToLevel = "1";

        HashMap<String, List<String>> districtRoleMap =
            new HashMap<String, List<String>>();

        List<String> roleNameList = new ArrayList<String>();
        roleNameList.add("Appraiser");

        districtRoleMap.put("West", roleNameList);

        List<IdentityUserWrapper> mergedUserList =
            serviceWrapper.retrieveDistrictUsersAndReportees(userId, upToLevel,
                                                             districtRoleMap);
        System.out.println("Merged District Users: " + mergedUserList.size());
    }
}
