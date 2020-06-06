package servlets;

import DB.HibernateSessionConnector;
import models.Game;
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

public class MatchServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MatchServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String userNick = null, gameName = null;
        PrintWriter writer;
        try {
            userNick = req.getParameter("user_nick");
            gameName = req.getParameter("game_name");
            logger.info("getting match score: " + userNick + " | " + gameName);
            writer = resp.getWriter();
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        try {
            getScore(userNick, gameName, resp);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void getScore(String userNick, String gameName, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        try (Session session = HibernateSessionConnector.getSession()) {
            long playerId = getPlayerId(userNick, session);
            long gameId = getGameId(gameName, session);
            if (playerId == -1 || gameId == -1){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.print("bad data provided");
                logger.info("bad data: " + playerId + " | " + gameId);
                return;
            }
            Query query = session.createQuery("From Match as m where m.player = :player_id and m.gameId = :game_id");
            query.setParameter("player_id", playerId);
            query.setParameter("game_id", gameId);
            List resultList = query.getResultList();
            for (Object o : resultList) {
                logger.info(o);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Throwable e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getGameId(String gameName, Session session) {
        Query query = session.createQuery("From Game as g where g.name = :name");
        query.setParameter("name", gameName);
        List resultList = query.getResultList();
        if (resultList.size() == 0) {
            return -1;
        }
        Game game = (Game) resultList.get(0);
        return game.getId();
    }

    private long getPlayerId(String userNick, Session session) {
        Query query = session.createQuery("From Player as p where p.nick = :nick");
        query.setParameter("nick", userNick);
        List resultList = query.getResultList();
        if (resultList.size() == 0) {
            return -1;
        }
        Player player = (Player) resultList.get(0);
        return player.getId();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
