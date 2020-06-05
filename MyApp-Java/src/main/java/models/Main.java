package models;

import DB.HibernateSessionConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("DB.HibernateSessionConnector");
        Session session = HibernateSessionConnector.getSession();
        Query query = session.createQuery("From Match as m inner join m.game as g inner join m.player as p where p.nick = :nick");
        query.setParameter("nick", "nana");
        List <Object[]> resultList = query.getResultList();
//        Transaction transaction = session.beginTransaction();
        for (Object[] objects : resultList) {
            System.out.println(objects.length);
            Match m = (Match) objects[0];
            Game g = (Game) objects[1];
            Player p = (Player) objects[2];
            System.out.println(g.getName());
        }
//        transaction.commit();
    }
}
