package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventConstants;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AuthorizationBeanTest {
    private static final String ReviewBaseYearEvent = "ReviewBaseYearEvent";
    private static final String APPRAISER = "Appraiser";
    private static final String SubmitBaseYearEvent = "SubmitBaseYearEvent";
    private static final String CHIEFAPPRAISER = "Chief Appraiser";
    private static final String CHIEFAPPRAISERREJECT = "Chief Appraiser Reject";

    private static final String SELFASSIGN = "SelfAssign";
    private static final String SUPERVISOR = "Supervisor";
    private static final String Submit = "Submit";
    private static final String Cancel = "Cancel";
    private static final String Approve = "Approve";
    private static final String Reject = "Reject";
    private static final String Assign = "Assign";
    private static final String Save = "Save";
    private static final String Create = "Create";
    private static final String PRINCIPALAPPRAISER = "Principal Appraiser";
    private static final String PRINCIPALAPPRAISERREJECT = "Principal Appraiser Reject";
    public static final String ASSISTANT_ASSESSOR = "AssistantAssessor";
    public static final String DIRECTOR = "Director";
    
    private static final String ManageBaseYearEvent = "ManageBaseYearEvent";
    private static final String ApproveBaseYearEvent = "ApproveBaseYearEvent";
    private static final String APPRAISER_WITHDRAWN = "Appraiser Withdrawn";
    private static final String SUPERVISOR_WITHDRAWN = "Supervisor Withdrawn";
    private static final String CHIEFAPPRAISER_WITHDRAWN = "Chief Appraiser Withdrawn";
    private static final String DIRECTOR_WITHDRAWN = "Director Withdrawn";
    private static final String ASSISTANTASSESSOR_WITHDRAWN = "AssistantAssessor Withdrawn";
    public static final String DIRECTOR_REJECT = "Director Reject";
    public static final String ASSISTNATASSESSOR_REJECT = "AssistantAssessor Reject";
    public static final String ASSISTANT_ASSESSOR_WITHDRAWN ="AssistantAssessor Withdrawn";
    public static final String PRINCIPAL_APPRAISER_WITHDRAWN ="Principal Appraiser Withdrawn";
    
    AuthorizationBean bean = new AuthorizationBean();
    String _role = ", Role: ";
    String _assigneBy = ", Assigned BY: ";
    String _htName = ", HumanTaskName: ";
    private String _action =  "Action: ";

    public AuthorizationBeanTest() {
    }

    @Before
    public void setUp() throws Exception {
        BaseYearEventConstants.setTEST(true);
    }

    @After
    public void tearDown() throws Exception {
        BaseYearEventConstants.setTEST(false);
    }

    /**
     * @see AuthorizationBean#getEnableSubmit()
     */
    @Test
    public void testGetEnableSubmit() {
        
        //Self Assign SubmitBaseYearEvent Tests
        runTest(true, APPRAISER, SubmitBaseYearEvent, SELFASSIGN, Submit);
        runTest(true, SUPERVISOR, SubmitBaseYearEvent, SELFASSIGN, Submit);
        runTest(true, PRINCIPALAPPRAISER, SubmitBaseYearEvent, SELFASSIGN, Submit);
        runTest(true, APPRAISER, SubmitBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(false, SUPERVISOR, SubmitBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, SUPERVISOR, SubmitBaseYearEvent, PRINCIPALAPPRAISERREJECT, Submit);
        runTest(false, PRINCIPALAPPRAISER, SubmitBaseYearEvent, CHIEFAPPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, SubmitBaseYearEvent, CHIEFAPPRAISERREJECT, Submit);
        runTest(false, CHIEFAPPRAISER, SubmitBaseYearEvent, SELFASSIGN, Submit);
        
        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, APPRAISER, Submit);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, APPRAISER, Submit);
        
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISERREJECT, Submit);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Submit);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Submit);

        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Submit);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISER, Submit);


        runTest(true, SUPERVISOR, ReviewBaseYearEvent, APPRAISER, Submit);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISERREJECT, Submit);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, SUPERVISOR, Submit);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISER, Submit);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISERREJECT, Submit);
          
        //Self Assign Not SubmitBaseYearEvent Tests
        runTest(false, APPRAISER, ReviewBaseYearEvent, SELFASSIGN, Submit);
        runTest(false, SUPERVISOR, ManageBaseYearEvent, SELFASSIGN, Submit);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SELFASSIGN, Submit);
        runTest(false, PRINCIPALAPPRAISER, ApproveBaseYearEvent, SELFASSIGN, Submit);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SELFASSIGN, Submit);   
        
        runTest(false, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Submit);
        runTest(false, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR, Submit);
        runTest(false, DIRECTOR, ApproveBaseYearEvent, ASSISTANT_ASSESSOR, Submit);
    }

    private void runTest(boolean expectedResult, String role,
                            String eventName, String assignBy, String action) {

        bean.setRole(role);
        bean.setHumantaskName(eventName);
        bean.setAssignedBY(assignBy);
        Boolean result = false;
        if (action.equals(Submit)) {
            result = bean.getEnableSubmit();
        }
        if (action.equals(Cancel)) {
            result = bean.getEnableCancel();
        }
        if (action.equals(Approve)) {
            result = bean.getEnableApprove();
        }
        if (action.equals(Reject)) {
            result = bean.getEnableReject();
        }
        if (action.equals(Assign)) {
            result = bean.getEnableAssign();
        }
        if (action.equals(Save)) {
            result = bean.getIsReadOnly();
        }
        if (action.equals(Create)) {
            result = bean.getCanCreate();
        }
        if (expectedResult) {
            assertTrue(_action + action + _role + role + _htName + eventName +
                       _assigneBy + assignBy, result);
        } else {
            assertFalse(_action + action + _role + role + _htName + eventName +
                        _assigneBy + assignBy, result);
        }
    }

    /**
     * @see AuthorizationBean#getEnableApprove()
     */
    @Test
    public void testGetEnableApprove() {
        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER, Approve);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Approve);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Approve);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, APPRAISER, Approve);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Approve);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, APPRAISER, Approve);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, SUPERVISOR, Approve);

        runTest(true, SUPERVISOR, ReviewBaseYearEvent, SUPERVISOR, Approve);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, APPRAISER, Approve);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Approve);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, SUPERVISOR, Approve);      
        //Not Self Assign Tests
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Approve);
        runTest(false, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISER, Approve);     
        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Approve);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR, Approve);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER, Approve);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR_REJECT, Approve);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, ASSISTNATASSESSOR_REJECT, Approve);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Approve);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR_WITHDRAWN, Approve);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, ASSISTANT_ASSESSOR_WITHDRAWN, Approve);
    }

    /**
     * @see AuthorizationBean#getEnableReject()
     */
    @Test
    public void testGetEnableReject() {
        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER, Reject);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Reject);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Reject);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Reject);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, APPRAISER, Reject);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, APPRAISER, Reject);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, SUPERVISOR, Reject);
        
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, SUPERVISOR, Reject);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, APPRAISER, Reject);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Reject);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, SUPERVISOR, Reject);      
        //Not Self Assign Tests
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Reject);
        runTest(false, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISER, Reject);  
        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Reject);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR, Reject);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER, Reject);        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR_REJECT, Reject);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, ASSISTNATASSESSOR_REJECT, Reject);
        
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Reject);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR_WITHDRAWN, Reject);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, ASSISTANT_ASSESSOR_WITHDRAWN, Reject);
    }

    /**
     * @see AuthorizationBean#getEnableAssign()
     */
    @Test
    public void testGetEnableAssign() {
        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER, Assign);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISER, Assign);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISERREJECT, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, APPRAISER, Assign);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, APPRAISER, Assign);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, SUPERVISOR, Assign);

        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISER, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Assign);

        runTest(true, SUPERVISOR, ReviewBaseYearEvent, APPRAISER, Assign);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, SUPERVISOR, Assign);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Assign);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISERREJECT, Assign);
        
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, SUPERVISOR, Assign);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Assign);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISER, Assign);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISERREJECT, Assign);
           
        //Not Self Assign Tests
        runTest(false, SUPERVISOR, ReviewBaseYearEvent, CHIEFAPPRAISER, Assign);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISER, Assign);
        runTest(false, PRINCIPALAPPRAISER, SubmitBaseYearEvent, CHIEFAPPRAISER, Assign);   
        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Assign);
        
        //WithDraw Tests
        runTest(true, SUPERVISOR, SubmitBaseYearEvent, APPRAISER_WITHDRAWN, Assign);
        runTest(true, PRINCIPALAPPRAISER, SubmitBaseYearEvent, SUPERVISOR_WITHDRAWN, Assign);
        runTest(true, CHIEFAPPRAISER, SubmitBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Assign);
        runTest(false, DIRECTOR, SubmitBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, SubmitBaseYearEvent, DIRECTOR_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, SubmitBaseYearEvent, ASSISTANTASSESSOR_WITHDRAWN, Assign);

        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER_WITHDRAWN, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR_WITHDRAWN, Assign);
        runTest(true, CHIEFAPPRAISER, ManageBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Assign);
        runTest(false, DIRECTOR, ManageBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, ManageBaseYearEvent, DIRECTOR_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, ManageBaseYearEvent, ASSISTANTASSESSOR_WITHDRAWN, Assign);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Assign);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Assign);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Assign);

        runTest(true, SUPERVISOR, ApproveBaseYearEvent, APPRAISER_WITHDRAWN, Assign);
        runTest(true, PRINCIPALAPPRAISER, ApproveBaseYearEvent, SUPERVISOR_WITHDRAWN, Assign);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Assign);
        runTest(false, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR_WITHDRAWN, Assign);
        runTest(false, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, ASSISTANTASSESSOR_WITHDRAWN, Assign);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR_REJECT, Assign);

    }

    /**
     * @see AuthorizationBean#getIsReadOnly()
     */
    @Test
    public void testGetEnableSave() {
        runTest(true, SUPERVISOR, ApproveBaseYearEvent, APPRAISER_WITHDRAWN, Save);
        runTest(true, PRINCIPALAPPRAISER, ApproveBaseYearEvent, SUPERVISOR_WITHDRAWN, Save);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Save);
    }

    /**
     * @see AuthorizationBean#getCanCreate()
     */
    @Test
    public void testGetCanCreate() {
        runTest(false, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, ASSISTANT_ASSESSOR_WITHDRAWN, Create);
        runTest(false, SUPERVISOR, ApproveBaseYearEvent, APPRAISER_WITHDRAWN, Create);
        runTest(false, PRINCIPALAPPRAISER, ApproveBaseYearEvent, SUPERVISOR_WITHDRAWN, Create);
        runTest(false, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Create);
        runTest(false, SUPERVISOR, ApproveBaseYearEvent, APPRAISER_WITHDRAWN, Create);
        runTest(false, SUPERVISOR, ManageBaseYearEvent, APPRAISER_WITHDRAWN, Create);
        runTest(false, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR_WITHDRAWN, Create);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Create);

        runTest(false, SUPERVISOR, SubmitBaseYearEvent, APPRAISER_WITHDRAWN, Create);
        runTest(false, PRINCIPALAPPRAISER, SubmitBaseYearEvent, SUPERVISOR_WITHDRAWN, Create);
        runTest(false, CHIEFAPPRAISER, SubmitBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Create);
        runTest(false, DIRECTOR, SubmitBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, SubmitBaseYearEvent, DIRECTOR_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, SubmitBaseYearEvent, ASSISTANTASSESSOR_WITHDRAWN, Create);

        runTest(false, SUPERVISOR, ManageBaseYearEvent, APPRAISER_WITHDRAWN, Create);
        runTest(false, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR_WITHDRAWN, Create);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, PRINCIPAL_APPRAISER_WITHDRAWN, Create);
        runTest(false, DIRECTOR, ManageBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, ManageBaseYearEvent, DIRECTOR_WITHDRAWN, Create);
        runTest(false, ASSISTANT_ASSESSOR, ManageBaseYearEvent, ASSISTANTASSESSOR_WITHDRAWN, Create);
    }

    /**
     * @see AuthorizationBean#getEnableCancel()
     */
    @Test
    public void testGetEnableCancel() {
        //Self Assign SubmitBaseYearEvent Tests
        runTest(true, APPRAISER, SubmitBaseYearEvent, SELFASSIGN, Cancel);
        runTest(true, SUPERVISOR, SubmitBaseYearEvent, SELFASSIGN, Cancel);
        runTest(true, PRINCIPALAPPRAISER, SubmitBaseYearEvent, SELFASSIGN, Cancel);
        runTest(true, APPRAISER, SubmitBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(false, SUPERVISOR, SubmitBaseYearEvent, SUPERVISOR, Cancel);
        runTest(true, SUPERVISOR, SubmitBaseYearEvent, PRINCIPALAPPRAISERREJECT, Cancel);
        runTest(false, PRINCIPALAPPRAISER, SubmitBaseYearEvent, CHIEFAPPRAISER, Cancel);
        runTest(true, PRINCIPALAPPRAISER, SubmitBaseYearEvent, CHIEFAPPRAISERREJECT, Cancel);
        runTest(false, CHIEFAPPRAISER, SubmitBaseYearEvent, SELFASSIGN, Cancel);
        
        runTest(true, SUPERVISOR, ManageBaseYearEvent, APPRAISER, Cancel);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, PRINCIPALAPPRAISERREJECT, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, SUPERVISOR, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISER, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, APPRAISER, Cancel);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, APPRAISER, Cancel);

        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISERREJECT, Cancel);
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, SUPERVISOR, Cancel);
        runTest(true, SUPERVISOR, ManageBaseYearEvent, CHIEFAPPRAISER, Cancel);        
        runTest(true, PRINCIPALAPPRAISER, ManageBaseYearEvent, PRINCIPALAPPRAISER, Cancel);

        runTest(true, SUPERVISOR, ReviewBaseYearEvent, APPRAISER, Cancel);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, SUPERVISOR, Cancel);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(true, SUPERVISOR, ReviewBaseYearEvent, PRINCIPALAPPRAISERREJECT, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, SUPERVISOR, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISER, Cancel);
        runTest(true, PRINCIPALAPPRAISER, ReviewBaseYearEvent, CHIEFAPPRAISERREJECT, Cancel);
          
        //Self Assign Not SubmitBaseYearEvent Tests
        runTest(false, APPRAISER, ReviewBaseYearEvent, SELFASSIGN, Cancel);
        runTest(false, SUPERVISOR, ManageBaseYearEvent, SELFASSIGN, Cancel);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SELFASSIGN, Cancel);
        runTest(false, PRINCIPALAPPRAISER, ApproveBaseYearEvent, SELFASSIGN, Cancel);
        runTest(false, CHIEFAPPRAISER, ManageBaseYearEvent, SELFASSIGN, Cancel);  
        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, PRINCIPALAPPRAISER, Cancel);
        runTest(false, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR, Cancel);
        runTest(false, DIRECTOR, ApproveBaseYearEvent, ASSISTANT_ASSESSOR, Cancel);
        
        runTest(true, CHIEFAPPRAISER, ApproveBaseYearEvent, DIRECTOR_REJECT, Cancel);
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR, Cancel);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER, Cancel);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, ASSISTNATASSESSOR_REJECT, Cancel);
        
        runTest(true, ASSISTANT_ASSESSOR, ApproveBaseYearEvent, DIRECTOR_WITHDRAWN, Cancel);
        runTest(true, DIRECTOR, ApproveBaseYearEvent, CHIEFAPPRAISER_WITHDRAWN, Cancel);



    }
}
