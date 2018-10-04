package bean;

import com.scg.ma.common.loggingFwk.LogWrapper;

import com.scg.ma.common.loggingFwk.LogWrapper;

import javax.faces.event.ActionEvent;

import org.apache.log4j.MDC;

public class LoggingBean {
    
    private static LogWrapper _myLogger = new LogWrapper(LoggingBean.class);
    
    public LoggingBean() {
    }

    public void testmyAccountLogger(ActionEvent actionEvent){
       
       testTrace(actionEvent);
       testDebug(actionEvent);
       testInfo(actionEvent);
       testWarn(actionEvent);
       testError(actionEvent);
       testFatal(actionEvent);
        
    }
    
    public void testTrace(ActionEvent actionEvent){
        _myLogger.trace("Trace Message from myAccountLogger");
    }
    
    public void testDebug(ActionEvent actionEvent){
        _myLogger.debug("Debug Message from myAccountLogger");
    }
    public void testInfo(ActionEvent actionEvent){
        _myLogger.info("Info Message from myAccountLogger");
    }
    public void testWarn(ActionEvent actionEvent){
        _myLogger.warn("Warning Message from myAccountLogger");
    }
    public void testError(ActionEvent actionEvent){
        _myLogger.error("Error Message from myAccountLogger");
    }
    public void testFatal(ActionEvent actionEvent){
        _myLogger.fatal("Fatal Message from myAccountLogger");
    }

}
