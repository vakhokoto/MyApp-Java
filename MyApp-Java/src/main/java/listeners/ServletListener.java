package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class ServletListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(ServletListener.class.getName());

    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("DB.HibernateSessionConnector");
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            System.exit(-1);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
