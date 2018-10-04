package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;

import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventResp;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventRespList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueTotalList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseYearValueResponse;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendObjectList;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.BaseYearEventErr;

import java.math.BigInteger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BaseYearEventRulesWrapperTest {
    public BaseYearEventRulesWrapperTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see BaseYearEventRulesWrapper#validate(java.util.ArrayList<gov.laca.amp.bvm.model.data.BaseYearEventDO>)
     */
    @Test
    public void testValidateOnSubmit() {
        //        String ain = "2004012022";
        String ain = "3322006052";
        String userId = "C9900030";
        BigInteger aoid = BigInteger.valueOf(101538106);

        BaseYearEventsMgmtWrapper baseYearEventsMgmtService =
            new BaseYearEventsMgmtWrapper();
        ArrayList<BaseYearEventDO> input = new ArrayList<BaseYearEventDO>();
        try {
            AinDO ainDO = new AinDO();
            baseYearEventsMgmtService.retrieveEventsFromService(input, ainDO,
                                                                ain, userId,
                                                                aoid);
            for (BaseYearEventDO bye : input) {
                assertNotNull(bye.getAin());
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Retrieve BYE's Call");
        }

        BaseYearEventRulesWrapper rules = new BaseYearEventRulesWrapper();
        try {
            Boolean result = rules.validate(input, "onsubmit");
            if (!result) {
                java.util.List<BaseYearEventErr> errorEvents =
                    rules.getErrorEvents();
                for (BaseYearEventErr baseYearEventErr : errorEvents) {
                    assertNotNull(baseYearEventErr.getErrorMessage());
                    assertNotNull(baseYearEventErr.getErrorBYEId());
                }
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Calling OPA");
        }
    }

    @Test
    public void testTrendBaseYearValue() {
        String ain = "3322006052";
        String userId = "C9900030";
        BigInteger aoid = BigInteger.valueOf(101538106);

        BaseYearEventsMgmtWrapper baseYearEventsMgmtService =
            new BaseYearEventsMgmtWrapper();
        ArrayList<BaseYearEventDO> input = new ArrayList<BaseYearEventDO>();
        try {
            AinDO ainDO = new AinDO();
            baseYearEventsMgmtService.retrieveEventsFromService(input, ainDO,
                                                                ain, userId,
                                                                aoid);
            for (BaseYearEventDO bye : input) {
                assertNotNull(bye.getAin());
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Retrieve BYE's Call");
        }
        BaseYearEventRulesWrapper rules = new BaseYearEventRulesWrapper();

        try {
            TrendBaseYearValueResponse baseYearValueResponse =
                rules.trendBaseYearValues(BigInteger.valueOf(2016), input);
            BaseYearEventRespList baseYearEventRespList =
                baseYearValueResponse.getBaseYearEventRespList();
            List<BaseYearEventResp> baseYearEventResp =
                baseYearEventRespList.getBaseYearEvent();
            for (BaseYearEventResp baseYearEventResp1 : baseYearEventResp) {
                TrendBaseValueTotalList baseValueTotalList =
                    baseYearEventResp1.getTrendBaseValueTotalList();
                TrendObjectList list = baseYearEventResp1.getTrendObjectList();
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Calling OPA for Trend Base Year Event");

        }
    }

    @Test
    public void testValidateOnClose() {
        //        String ain = "2004012022";
        String ain = "3322006052";
        String userId = "C9900030";
        BigInteger aoid = BigInteger.valueOf(101538106);

        BaseYearEventsMgmtWrapper baseYearEventsMgmtService =
            new BaseYearEventsMgmtWrapper();
        ArrayList<BaseYearEventDO> input = new ArrayList<BaseYearEventDO>();
        try {
            AinDO ainDO = new AinDO();
            baseYearEventsMgmtService.retrieveEventsFromService(input, ainDO,
                                                                ain, userId,
                                                                aoid);
            for (BaseYearEventDO bye : input) {
                assertNotNull(bye.getAin());
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Retrieve BYE's Call");
        }
        BaseYearEventDO baseYearEventDO = input.get(0);
        baseYearEventDO.setTransactionType("C");
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Date effectiveBeginDate = null;
        try {
            effectiveBeginDate = dateformat.parse("10/9/1974");
        } catch (ParseException e) {
            fail("Unable to Set Date");
            e.printStackTrace();
        }
        baseYearEventDO.setEffectiveBeginDate(effectiveBeginDate);
        baseYearEventDO.setInactiveDate(null);
        BaseYearEventRulesWrapper rules = new BaseYearEventRulesWrapper();
        try {
            Boolean result = rules.validate(input, "onclose");
            if (!result) {
                java.util.List<BaseYearEventErr> errorEvents =
                    rules.getErrorEvents();
                if (errorEvents.size() <= 0) {
                    fail("Empty Error Messages");
                }
                for (BaseYearEventErr baseYearEventErr : errorEvents) {
                    assertNotNull(baseYearEventErr.getErrorMessage());
                    assertNotNull(baseYearEventErr.getErrorBYEId());
                }
            } else {
                for (BaseYearEventDO event : input) {
                    Double landValue = event.getRetainedLandValue();
                }
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Calling OPA");
        }
    }
}
