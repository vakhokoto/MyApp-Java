package servlets;

import DB.HibernateSessionConnector;
import models.Game;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GameServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(GameServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer;
        String name = null;
        try {
            writer = resp.getWriter();
            name = req.getParameter("name");
            logger.info("adding game: " + name);
            if (name == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.print("Provide name of game!");
                return;
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error(e.getMessage());
            return;
        }

        try(Session session = HibernateSessionConnector.getSession()) {
            Game game = new Game();
            game.setName(name);
            Transaction t = session.beginTransaction();
            session.save(game);
            t.commit();
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print("Game added successfully!");
        } catch (Throwable e){
            logger.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print("Game is already in the list!");
        }
    }
}
