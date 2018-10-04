package gov.laca.amp.bvm.test;

import gov.laca.amp.bvm.model.AINLockMgmtServiceWrapperTest;
import gov.laca.amp.bvm.model.BaseYearEventsMgmtWrapperTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { BaseYearEventsMgmtWrapperTest.class,
                       AINLockMgmtServiceWrapperTest.class })
public class BaseYearEventTestSuite {
}
