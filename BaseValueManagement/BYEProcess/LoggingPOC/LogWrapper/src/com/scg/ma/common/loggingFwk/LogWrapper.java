package com.scg.ma.common.loggingFwk;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class LogWrapper {
    
    private ADFLogger _adfLogger;
    private Logger _log4jLogger;
    
    private boolean _adfLoggerOn;
    private boolean _log4jLoggerOn;
    
    public LogWrapper() {
        super();
        _adfLogger = ADFLogger.getAnonymousLogger();
        _log4jLogger = Logger.getRootLogger();
    }
    
    public LogWrapper(Class loggerClass){
        _adfLogger = ADFLogger.createADFLogger(loggerClass);
        _log4jLogger = Logger.getLogger(loggerClass);
    }
    
    public LogWrapper(String wrapperName) {
        _adfLogger = ADFLogger.createADFLogger(wrapperName);
        _log4jLogger = Logger.getLogger(wrapperName);
    }
    
    
    private void setLogParameters(){
        ADFContext adfContext = ADFContext.getCurrent();
        Map applicationSCope = adfContext.getApplicationScope();
        HttpServletRequest request = (HttpServletRequest)adfContext.getEnvironment().getRequest();
        SecurityContext securityContext = adfContext.getSecurityContext();
        
        String appName = adfContext.getApplicationName();
        _adfLoggerOn = (applicationSCope.get("ADFLoggerOn") != null ? 
                        (Boolean)applicationSCope.get("ADFLoggerOn") : Boolean.TRUE);
        _log4jLoggerOn = (applicationSCope.get("Log4jLoggerOn") != null ?
                        (Boolean)applicationSCope.get("Log4jLoggerOn") : Boolean.TRUE);
        
        StackTraceElement stkTrc = Thread.currentThread().getStackTrace()[3];
            
        MDC.put("method", stkTrc.getMethodName());
        MDC.put("userid", securityContext.getUserName().toUpperCase());
        MDC.put("wacid", "TestWACID");
        MDC.put("reqid", request.hashCode());
        MDC.put("baid", "TestBAID");
        MDC.put("appName", appName);
        MDC.put("sessionid", request.getSession(false).getId());
        // csr userid, csr wacid,
        
    }
    
    public void fatal(Object msg){
        
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.fatal(msg);
                
        if(_adfLoggerOn)
        _adfLogger.internal(msg.toString(), msg);

    }

    public void fatal(Object msg, Throwable t){
        
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.fatal(msg,t);
                
        if(_adfLoggerOn)
        _adfLogger.internal(msg.toString(), t);

    }


    public void error(Object msg){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.error(msg);
        
        if(_adfLoggerOn)
        _adfLogger.severe(msg.toString());
            
    }

    public void error(Object msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.error(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.severe(msg.toString(), t);
            
    }

    public void warn(Object msg){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.warn(msg);
        
        if(_adfLoggerOn)
        _adfLogger.warning(msg.toString());
            
    }

    public void warn(Object msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.warn(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.warning(msg.toString(), t);
            
    }

    public void info(String msg){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.info(msg);
        
        if(_adfLoggerOn)
        _adfLogger.info(msg.toString());
            
    }

    public void info(String msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.info(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.info(msg.toString(), t);
            
    }

    public void debug(String msg){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.debug(msg);
        
        if(_adfLoggerOn)
        _adfLogger.fine(msg.toString());
            
    }

    public void debug(String msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.debug(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.fine(msg.toString(), t);
            
    }

    public void trace(String msg){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.trace(msg);
        
        if(_adfLoggerOn)
        _adfLogger.finer(msg.toString());
            
    }

    public void trace(String msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.trace(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.finer(msg.toString(), t);
            
    }

    public void entering(String msg, Throwable t){
        setLogParameters();
        
        if(_log4jLoggerOn)
        _log4jLogger.trace(msg, t);
        
        if(_adfLoggerOn)
        _adfLogger.finer(msg.toString(), t);
            
    }
    
//    public boolean isLog4jFatalEnabled(){
//        return _log4jLogger.isEnabledFor(Level.FATAL);
//    }
//
//    public boolean isLog4jErrorEnabled(){
//        return _log4jLogger.isEnabledFor(Level.ERROR);
//    }
//
//    public boolean isLog4jWarnEnabled(){
//        return _log4jLogger.isEnabledFor(Level.WARN);
//    }
//
//    public boolean isLog4jInfoEnabled(){
//        return _log4jLogger.isInfoEnabled();
//    }
//
//    public boolean isLog4jDebugEnabled(){
//        return _log4jLogger.isDebugEnabled();
//    }
//
//    public boolean isLog4jTraceEnabled(){
//        return _log4jLogger.isTraceEnabled();
//    }
//    
//    public boolean isADFLogFatalEnabled(){
//        return _adfLogger.isInternal();
//    }
//
//    public boolean isADFLogErrorEnabled(){
//        return _adfLogger.isSevere();
//    }
//
//    public boolean isADFLogWarnEnabled(){
//        return _adfLogger.isWarning();
//    }
//
//    public boolean isADFLogInfoEnabled(){
//        return _adfLogger.isInfo();
//    }
//
//    public boolean isADFLogDebugEnabled(){
//        return _adfLogger.isFine();
//    }
//
//    public boolean isADFLogTraceEnabled(){
//        return _adfLogger.isFiner();
//    }
//
    public boolean isFatalEnabled(){
        return _log4jLogger.isEnabledFor(Level.FATAL) || _adfLogger.isInternal();
    }

    public boolean isErrorEnabled(){
        return _log4jLogger.isEnabledFor(Level.ERROR) || _adfLogger.isSevere();
    }

    public boolean isWarnEnabled(){
        return _log4jLogger.isEnabledFor(Level.WARN) || _adfLogger.isWarning();
    }

    public boolean isInfoEnabled(){
        return _log4jLogger.isInfoEnabled() || _adfLogger.isInfo();
    }

    public boolean isDebugEnabled(){
        return _log4jLogger.isDebugEnabled() || _adfLogger.isFine();
    }

    public boolean isTraceEnabled(){
        return _log4jLogger.isTraceEnabled() || _adfLogger.isFiner();
    }
    
    public boolean isADFLoggerOn(){
        return _adfLoggerOn;
    }
    
    public boolean isLog4jOn(){
        return _log4jLoggerOn;
    }
    
}

