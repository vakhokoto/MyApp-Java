package ge.bog.internship.java;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.Match;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;

@WebService
public class SOAPServer {
    private static final Logger logger = Logger.getLogger(SOAPServer.class.getName());

    @WebMethod
    public String classifyUserData(String jsonData){
        String response = null;
        logger.info("request:\n" + jsonData + "\n");
        try {
            Gson gson = new Gson();
            Match[] matches = gson.fromJson(jsonData, Match[].class);
            Arrays.sort(matches);
            JsonArray array = new JsonArray();
            for (Match match : matches) {
                JsonObject object = new JsonObject();
                object.addProperty("nick", match.getPlayer().getNick());
                object.addProperty("firstName", match.getPlayer().getFirstName());
                object.addProperty("lastName", match.getPlayer().getLastName());
                object.addProperty("game", match.getGame().getName());
                object.addProperty("score", match.getScore());
                array.add(object);
            }
            response = gson.toJson(array);
            logger.info("response:\n" + response + "\n");
        } catch (Throwable e){
            logger.error(e.getMessage() + "\n");
            return null;
        }
        return response;
    }
}
