package gov.laca.amp.bvm.util;

import java.net.URL;

import javax.naming.Context;
import javax.naming.NamingException;

import oracle.adf.model.connection.webservice.api.WebServiceConnection;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

/**
 * @author Vijay Redla
 * @version 2.0
 */
public class WebServiceEndPoints {
    private static ADFLogger adfLogger =
        ADFLogger.createADFLogger(WebServiceEndPoints.class);

    public WebServiceEndPoints() {
        super();
    }

    public static URL getWsdlUrlFromConnections(String connectionName) {
        URL wsdlLocationURL = null;

        adfLogger.log(adfLogger.NOTIFICATION,
                      BaseYearEventUtils.LOGGER_PREFIX +
                      "Getting WSDL URL for connection name '" +
                      connectionName + "' from connections.xml (MDS)...");
        try {
            Context ctx = ADFContext.getCurrent().getConnectionsContext();
            WebServiceConnection wsc =
                (WebServiceConnection)ctx.lookup(connectionName);
            wsdlLocationURL = wsc.getWsdlUrl();
            adfLogger.log(adfLogger.NOTIFICATION,
                          BaseYearEventUtils.LOGGER_PREFIX +
                          "URL for connection name '" + connectionName +
                          "' is:  " + wsdlLocationURL);
        } catch (NamingException ne) {
            // TODO: Add catch code
            adfLogger.log(adfLogger.ERROR,
                          BaseYearEventUtils.LOGGER_PREFIX + "ERROR Getting WSDL URL for connection name '" +
                          connectionName + "' from connections.xml (MDS)...");
            ne.printStackTrace();
        }

        return wsdlLocationURL;
    }
}
