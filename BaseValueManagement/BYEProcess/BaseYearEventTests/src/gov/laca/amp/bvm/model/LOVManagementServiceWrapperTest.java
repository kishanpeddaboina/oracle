package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.util.BaseYearEventUtils;

import java.util.ArrayList;

import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LOVManagementServiceWrapperTest {
    public LOVManagementServiceWrapperTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see LOVManagementServiceWrapper#retrieveLovListFor(String,java.util.List<gov.laca.amp.bvm.model.LookupItem>)
     */
    @Test
    public void testRetrieveLovListFor() {
        List<LookupItem> eventDescriptionLov = new ArrayList<LookupItem>();
        LOVManagementServiceWrapper lovService =
            new LOVManagementServiceWrapper();
        try {
            lovService.retrieveLovListFor("EVENT DESCRIPTION",
                                          eventDescriptionLov);
            for (LookupItem lookupItem : eventDescriptionLov) {
                assertNotNull(lookupItem.getLegend());
            }
        } catch (AmpBaseYearEventException e) {
            fail();
        }
    }
}
