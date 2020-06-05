package ge.bog.internship.java;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class SOAPServer {

    @WebMethod
    public String classifyUserData(List<Object[]> resultList){
        return "Hello world!";
    }
}
