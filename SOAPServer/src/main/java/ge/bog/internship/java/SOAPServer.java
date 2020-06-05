package ge.bog.internship.java;

import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class SOAPServer {
    private static final Logger logger = Logger.getLogger(SOAPServer.class.getName());

    @WebMethod
    public String classifyUserData(List<Object[]> resultList){
        return "Hello world!";
    }
}
