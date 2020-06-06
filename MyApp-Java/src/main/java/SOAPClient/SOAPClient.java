package SOAPClient;

import Supplemets.Supplements;
import ge.bog.internship.java.SOAPServer;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class SOAPClient implements Supplements {

    private static JaxWsProxyFactoryBean jaxFactory;

    static {
        jaxFactory = new JaxWsProxyFactoryBean();

        jaxFactory.setServiceClass(SOAPServer.class);
        jaxFactory.setWsdlURL(SOAP_URL);
    }

    public static SOAPServer getWS() {
        SOAPServer soapServer = (SOAPServer) jaxFactory.create();

        return soapServer;
    }
}
