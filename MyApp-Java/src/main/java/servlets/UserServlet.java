package servlets;

import DB.HibernateSessionConnector;
import Supplemets.Supplements;
import SOAPClient.SOAPClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ge.bog.internship.java.SOAPServer;
import models.Match;
import models.Player;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.omg.CORBA.WrongTransaction;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet implements Supplements {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String nick = null;
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }
        nick = req.getParameter("nick");
        if (nick == null) {
            logger.info("Error: no nick provided\n");
            writer.print("Error: provide nick to find user");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        getUserInfo(nick, writer, resp);
    }

    private void getUserInfo(String nick, PrintWriter writer, HttpServletResponse resp) {
        try (Session session = HibernateSessionConnector.getSession()) {
            logger.info("getting user info:\nnick: " + nick + "\n");
            JsonObject respObject = new JsonObject();
            Query query = session.createQuery("From Match as m " +
                    "inner join m.player as p " +
                    "inner join m.game as g " +
                    "where p.nick = :nick");
            query.setParameter("nick", nick);
            List<Object[]> players = query.getResultList();

            String response = clasifyUserData(players);
            writer.print(response);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Throwable e) {
            logger.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String clasifyUserData(List<Object[]> players) {
        Gson gson = new Gson();

        String response;
        SOAPServer soapServer = SOAPClient.getWS();
        int len = players.size();
        Match arr[] = new Match[len];
        int curElementNumber = 0;
        for (Object[] player : players) {
            for (Object o : player) {
                if (o instanceof Match) {
                    arr[curElementNumber++] = (Match) o;
                }
            }
        }
        response = soapServer.classifyUserData(gson.toJson(arr));
        return response;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }
        String nick, firstName, lastName;
        nick = lastName = firstName = null;

        nick = req.getParameter("nick");
        firstName = req.getParameter("first_name");
        lastName = req.getParameter("last_name");

        if (nick == null || firstName == null || lastName == null) {
            writer.print("Error: please fill in all the fields");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.info("Response:\n\t" + "Error: please fill in all the fields");
            return;
        }

        logger.info("Adding user:\n\tnick: " + nick + "\n\tfirst_name: " + firstName + "\n\tlast_name: " + lastName + "\n");

        addUser(resp, writer, nick, firstName, lastName);
    }

    private void addUser(HttpServletResponse resp, PrintWriter writer, String nick, String firstName, String lastName) {
        try (Session session = HibernateSessionConnector.getSession()) {
            Player player = new Player(-1, nick, firstName, lastName);
            Transaction t = session.beginTransaction();
            session.save(player);
            t.commit();
            addSuccesfully(writer, nick, firstName, lastName);
        } catch (Throwable e) {
            logger.error(e.getMessage());
            writer.print("Error: user can't be registered because such nick already exists.\n\tPlease choose another nick.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addSuccesfully(PrintWriter writer, String nick, String firstName, String lastName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nick", nick);
        jsonObject.addProperty("firstName", firstName);
        jsonObject.addProperty("lastName", lastName);
        jsonObject.addProperty("status", "success");
        writer.print(jsonObject.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateSessionConnector.getSession()) {
            Transaction t = session.beginTransaction();
            PrintWriter writer;
            try {
                writer = resp.getWriter();
            } catch (IOException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                logger.error(e.getMessage());
                return;
            }

            String nick = req.getParameter("nick");
            logger.info("deleting user: " + nick + "\n");
            if (nick == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.print("Please provide nick!");
            }

            Query query = session.createQuery("From Player as p where p.nick = :nick");
            query.setParameter("nick", nick);
            List resultList = query.getResultList();
            if (resultList.size() == 0) {
                logger.info("no user found with nick: " + nick + "\n");
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            Player player = (Player) resultList.get(0);

            int status = makeRemoveUser(player.getId(), session);
            if (status == -1) {
                t.rollback();
                throw new WrongTransaction("Wrong transaction parameters!");
            }
            t.commit();
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print("User deleted successfully");
        } catch (Throwable e) {
            logger.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private int makeRemoveUser(long id, Session session) {
        try {
            Query query = session.createQuery("delete From Match as m where m.playerId = :id");
            query.setParameter("id", id);
            query.executeUpdate();

            query = session.createQuery("delete From Player as p where p.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            return -1;
        }
        return 0;
    }
}
