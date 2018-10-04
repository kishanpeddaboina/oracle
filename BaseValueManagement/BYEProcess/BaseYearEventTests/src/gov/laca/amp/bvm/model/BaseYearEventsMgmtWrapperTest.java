package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;

import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;

import java.math.BigInteger;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;

public class BaseYearEventsMgmtWrapperTest {
    public BaseYearEventsMgmtWrapperTest() {
    }

    /**
     * @see BaseYearEventsMgmtWrapper#retrieveEventsFromService(java.util.ArrayList<gov.laca.amp.bvm.model.data.BaseYearEventDO>,String,String)
     */
    @Test
    public void testRetrieveEventsFromService() {
        String ain = "3322006052";
        String userId = "C9900030";
        BigInteger aoid = BigInteger.valueOf(85867404);
        BaseYearEventsMgmtWrapper baseYearEventsMgmtService =
            new BaseYearEventsMgmtWrapper();
        ArrayList<BaseYearEventDO> input = new ArrayList<BaseYearEventDO>();
        try {
            AinDO ainDO = new AinDO();
            baseYearEventsMgmtService.retrieveEventsFromService(input, ainDO, ain,
                                                                userId, aoid);
            assertEquals(ainDO.getAin(), ain);
            for (BaseYearEventDO bye : input) {
                assertNotNull(bye.getAin());
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Test");
        }
    }

    /**
     * @see BaseYearEventsMgmtWrapper#initiateBaseYearEvent(String,String)
     */
    @Test
    public void testInitiateBaseYearEvent() {
        BaseYearEventsMgmtWrapper baseYearEventsMgmtService =
            new BaseYearEventsMgmtWrapper();
        String ain = "3322006052";
        String userId = "C9900030";
        BigInteger aoid = BigInteger.valueOf(85867404);
        try {
            String result =
                baseYearEventsMgmtService.initiateBaseYearEvent(ain, userId, aoid, "BYE");
            assertNotNull(result);
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Test");
        }
    }
}
