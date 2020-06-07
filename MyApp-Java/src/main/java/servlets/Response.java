package servlets;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Response extends HttpServletResponseWrapper {
    public Response(HttpServletResponse response) {
        super(response);
    }
}
