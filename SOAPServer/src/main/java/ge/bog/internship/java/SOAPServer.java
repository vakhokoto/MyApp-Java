package ge.bog.internship.java;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SOAPServer {

    @WebMethod
    public String helloWorld(){
        return "Hello world!";
    }
}
