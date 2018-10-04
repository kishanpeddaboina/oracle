package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;

import java.math.BigInteger;

import static org.junit.Assert.fail;
import org.junit.Test;
import static org.junit.Assert.*;


public class AINLockMgmtServiceWrapperTest {
    public AINLockMgmtServiceWrapperTest() {
    }

    /**
     * @see AINLockMgmtServiceWrapper#checkLock(String)
     */
    @Test
    public void testCheckLock() {
        AINLockMgmtServiceWrapper ainLockService =
            new AINLockMgmtServiceWrapper();
        String ain = "2004012022";
        BigInteger aoid = BigInteger.valueOf(49734163);
        try {
            String checkLock = ainLockService.checkLock(ain, aoid);
            String lockStatus = ainLockService.getLockStatus();
            if(lockStatus.equals("")){
                fail("Lock status is empty should be LOCKED Or UNLOCKED");
            }
        } catch (AmpBaseYearEventException e) {
            fail("Exception Running the Test");
        }
    }
}
