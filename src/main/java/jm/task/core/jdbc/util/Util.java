package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hibernatetable";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public Util() {

    }

//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName(DB_DRIVER);
//            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//            connection.setAutoCommit(false);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Connection OK");
//        return connection;

   // }
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory () {
        if (sessionFactory == null) {
            try {
                Configuration conf = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, DB_DRIVER);
                properties.put(Environment.URL, DB_URL);
                properties.put(Environment.USER, DB_USERNAME);
                properties.put(Environment.PASS, DB_PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "");
                conf.setProperties(properties);
                conf.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(conf.getProperties()).build();

                sessionFactory = conf.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.printStackTrace(); // throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }
}

