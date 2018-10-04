package com.scg.ma.utility.logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Map;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;

public class LoggingFilter implements Filter {
    
    private ServletContext context;
    private static final String LOG_SWITCHER_FILE_PATH_ATTR = "LogSwitcherFilePath";
    private static final String ADF_LOGGER_DEFAULT_ATTR = "AdfLoggerDefault";
    private static final String LOG4J_LOGGER_DEFAULT_ATTR = "Log4jLoggerDefault";
    
    private static final String LOG_SWITCHER_DEFAULT_FILE_PATH = "C:\\loggingController.properties";
    private static final String ADF_LOGGER_DEFAULT_VAL = "N";
    private static final String LOG4J_LOGGER_DEFAULT_VAL = "Y";
    private static final Long   LOG_FILE_CHECK_DURATION = new Long(360);
    
    public LoggingFilter() {
        super();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException,
                                                         ServletException {
        
        ADFContext adfContext = ADFContext.getCurrent();
        Map applicationSCope = adfContext.getApplicationScope();
        Map sessionScope = adfContext.getSessionScope();
        boolean checkLoggerProperty = false;
        
        Long startTime = new Long(0);
        Object startTimefromSession = sessionScope.get("LogSwitchCheckStartTime");
        
        if(startTimefromSession != null){
            startTime = (Long)startTimefromSession;
            Long elapsedTime = System.nanoTime() - startTime;
            Long seconds = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            if(seconds.compareTo(LOG_FILE_CHECK_DURATION) == 1)
                checkLoggerProperty = true;
        }
        else {
            startTime = System.nanoTime();
            sessionScope.put("LogSwitchCheckStartTime",startTime);
            checkLoggerProperty = true;
        }
        
        if(checkLoggerProperty){
            String logSwitcherFile = context.getInitParameter(LOG_SWITCHER_FILE_PATH_ATTR);
            String adfLoggerDefault = context.getInitParameter(ADF_LOGGER_DEFAULT_ATTR);
            String log4jLoggerDefault = context.getInitParameter(LOG4J_LOGGER_DEFAULT_ATTR);
            
            if(logSwitcherFile == null)
                logSwitcherFile = LOG_SWITCHER_DEFAULT_FILE_PATH;
            if(adfLoggerDefault == null)
                adfLoggerDefault = ADF_LOGGER_DEFAULT_VAL;
            if(log4jLoggerDefault == null)
                log4jLoggerDefault = LOG4J_LOGGER_DEFAULT_VAL;
            
            Properties defaultProps = new Properties();
            
            FileInputStream in = null;
            try {
                in = new FileInputStream(logSwitcherFile);
                defaultProps.load(in);
            } 
            catch (FileNotFoundException e) {
                e.getCause();
            }
            catch (IOException e) {
                e.getCause();
            }
            finally{
                if(in != null)
                    in.close();
            }
            
            Boolean ADFLoggerOn = ("N".equals(defaultProps.getProperty("ADFLoggerOn", adfLoggerDefault)) ? false : true);
            Boolean Log4jLoggerOn = ("N".equals(defaultProps.getProperty("Log4jLoggerOn", log4jLoggerDefault)) ? false : true);
            
            if(applicationSCope != null){
                applicationSCope.put("ADFLoggerOn", ADFLoggerOn);
                applicationSCope.put("Log4jLoggerOn", Log4jLoggerOn);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
