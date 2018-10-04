package gov.laca.amp.pdcr.common.model.util;

import java.net.URL;

import javax.naming.Context;
import javax.naming.NamingException;

import oracle.adf.model.connection.webservice.api.WebServiceConnection;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;


public class WebServiceEndPoints {
    public static final ADFLogger logger = ADFLogger.createADFLogger(WebServiceEndPoints.class);

    public WebServiceEndPoints() {
        super();
    }

    public static URL getWsdlUrlFromConnections(String connectionName) {
        URL wsdlLocationURL = null;

        logger.warning("Getting WSDL URL for connection name '" + connectionName + "' from connections.xml (MDS)...");
        try {
            Context ctx = ADFContext.getCurrent().getConnectionsContext();
            WebServiceConnection wsc = (WebServiceConnection)ctx.lookup(connectionName);
            wsdlLocationURL = wsc.getWsdlUrl();

            logger.warning("URL for connection name '" + connectionName + "' is:  " + wsdlLocationURL);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return wsdlLocationURL;
    }

}
