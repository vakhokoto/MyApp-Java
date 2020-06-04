package servlets;

import DB.HibernateSessionConnector;
import com.google.gson.JsonObject;
import models.Player;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nick = null;
        PrintWriter writer = resp.getWriter();
        nick = req.getParameter("nick");
        logger.info("getting user info:\nnick: " + nick + "\n");
        JsonObject respObject = new JsonObject();
        if (nick == null){
            writer.print("Error: provide nick to find user");
            return;
        }
        Session session = HibernateSessionConnector.getSession();
        Query query = session.createQuery("From Player p where p.nick = :nick");
        query.setParameter("nick", nick);
        List<Player> players = query.getResultList();
        logger.info("Result:" + players + "\n");

        if(players.size() == 0){
            writer.print("No players found.");
        }

        for (Player player : players) {
            writer.println(player.getNick() + "|" + player.getFirstName() + "|" + player.getLastName());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String nick, firstName, lastName;
        nick = lastName = firstName = null;
        nick = req.getParameter("nick");
        firstName = req.getParameter("first_name");
        lastName = req.getParameter("last_name");
        logger.info("Adding user:\n\tnick: " + nick + "\n\tfirst_name: " + firstName + "\n\tlast_name: " + lastName + "\n");

        if (nick == null || firstName == null || lastName == null){
            writer.print("Error: please fill in all the fields");
            logger.info("Response:\n\t" + "Error: please fill in all the fields");
            return;
        }

        Player player = new Player(-1, nick, firstName, lastName);
        Session session = HibernateSessionConnector.getSession();
        session.save(player);
    }
}
