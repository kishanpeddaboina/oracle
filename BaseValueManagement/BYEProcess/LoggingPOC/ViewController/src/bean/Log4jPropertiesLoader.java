package bean;

import java.io.IOException;

import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class Log4jPropertiesLoader implements Servlet {
    
    private ServletConfig _config;
    private static final String LOG4J_CONFIG_FILE_NAME = "log4j.properties";
    

    public Log4jPropertiesLoader() {
        super();
    }

    public void init(ServletConfig servletConfig) throws ServletException {
        _config = servletConfig;
        // Load the log4j property file
        initializeLog4j(servletConfig);
    }
    
    private void initializeLog4j(ServletConfig config) {
                    try {
                                    // Find the log4j configuration properties file path
                                    String propertyFilePath  = getFilePath();
                                    
                                    if (propertyFilePath != null) {
                                                    // Use property configurator to initialize log4j
                                                    PropertyConfigurator.configureAndWatch(propertyFilePath, 300000);
                                                    System.out.println("Initiated the Log4j using config at "+ propertyFilePath);
                                                    LogFactory.getLog(Log4jPropertiesLoader.class).info("\n Log4j properties initialized ");
                                    }
                    } catch (Throwable thr) {
                                    logToConsole("initializeLog4j()","SEVERE WARNING : Unable to initialize LOG4J Properties :"
                                                                    + thr.getLocalizedMessage());
                    }
    }

    public String getFilePath() throws java.io.IOException 
    {
                    String filePath = null;
                    String path = System.getProperty("java.class.path");
                    String pathSeparator = System.getProperty("path.separator"); 
                    logToConsole("getFilePath()","File Path " + path + " " +  pathSeparator);

                    StringTokenizer st = new StringTokenizer(path,pathSeparator);
                    while(st.hasMoreTokens()){
                                    filePath = st.nextToken();
                                    if(filePath.indexOf(LOG4J_CONFIG_FILE_NAME) != -1){
                                                    logToConsole("getFilePath()","log4j.properties file found at " + filePath);
                                                    return filePath;
                                    }
                    }
        return null;
    }

    private void logToConsole(String methodName,String message){
                    System.out.print(new Date(System.currentTimeMillis()) 
                    + "com.edocs.ps.sempra.common.Log4jPropertiesLoader:" + methodName + ":" + message);
    }


    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest,
                        ServletResponse servletResponse) throws ServletException,
                                                                IOException {
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {
    }
}
