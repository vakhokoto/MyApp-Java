package SOAPClient;

import Supplemets.Supplements;
import ge.bog.internship.java.SOAPServer;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class SOAPClient implements Supplements {

    public static SOAPServer getWS() {
        JaxWsProxyFactoryBean jaxFactory = new JaxWsProxyFactoryBean();

        jaxFactory.setServiceClass(SOAPServer.class);
        jaxFactory.setWsdlURL(SOAP_URL);

        SOAPServer soapServer = (SOAPServer) jaxFactory.create();

        return soapServer;
    }
}
