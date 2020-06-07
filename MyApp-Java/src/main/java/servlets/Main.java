package servlets;

import DB.HibernateSessionConnector;
import org.hibernate.Session;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateSessionConnector.getSession();
    }
}
