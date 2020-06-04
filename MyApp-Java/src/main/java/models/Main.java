package models;

import DB.HibernateSessionConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("DB.HibernateSessionConnector");
        Session session = HibernateSessionConnector.getSession();
        Transaction transaction = session.beginTransaction();
        Player player = new Player(-1, "nana", "Nana", "Chakvetadze");
        session.save(player);
        transaction.commit();
    }
}
