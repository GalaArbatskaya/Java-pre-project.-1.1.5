
//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import java.util.ArrayList;
//import java.util.List;
//
//import static jm.task.core.jdbc.util.Util.getSessionFactory;
//
//public class UserDaoHibernateImpl implements UserDao {
//    public UserDaoHibernateImpl() {
//
//    }
//
//
//    @Override
//    public void createUsersTable() {
//        Session session = null;
//        try (SessionFactory factory = getSessionFactory()) {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
//                    "(id MEDIUMINT not null auto_increment," +
//                    "name VARCHAR(50)," +
//                    "lastname VARCHAR(50)," +
//                    "age TINYINT,"
//                    + "PRIMARY KEY (id))").executeUpdate();
//            session.getTransaction().commit();
//            System.out.println("Таблица создана");
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void dropUsersTable() {
//        Session session = null;
//        try (SessionFactory factory = getSessionFactory()) {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
//            session.getTransaction().commit();
//            System.out.println("Таблица удалена");
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//
//    }
////    @Override
////    public void saveUser(String name, String lastName, byte age) {
////        Session session = null;
////        try (SessionFactory factory = getSessionFactory()) {
////            session = factory.getCurrentSession();
////            session.beginTransaction();
////            User users = new User(name, lastName, age);
////            session.save(users);
////            session.getTransaction().commit();
////            System.out.println("User с именем – " + name + " добавлен в базу данных");
////        } catch (HibernateException e) {
////            session.getTransaction().rollback();
////            e.printStackTrace();
////        }
////    }
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        SessionFactory factory = getSessionFactory();
//        Session session = null;
//        try  {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            User users = new User(name, lastName, age);
//            session.save(users);
//            session.getTransaction().commit();
//            System.out.println("User с именем – " + name + " добавлен в базу данных");
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//        factory.close();
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        Session session = null;
//        try (SessionFactory factory = getSessionFactory()) {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.delete(session.get(User.class, id));
//            session.getTransaction().commit();
//            System.out.println("User с ID – " + id + " удален из базы данных");
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public List<User> getAllUsers() {
//        Session session = null;
//        List<User> users = new ArrayList<>();
//        try (SessionFactory factory = getSessionFactory()) {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            users = session.createQuery("from User").getResultList();
//            session.getTransaction().commit();
//
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//        return users;
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        Session session = null;
//        try (SessionFactory factory = getSessionFactory()) {
//            session = factory.getCurrentSession();
//            session.beginTransaction();
//            session.createQuery("delete User").executeUpdate();
//            session.getTransaction().commit();
//            System.out.println("Таблица очищена");
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
//
//    }
//}
package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }
public void createUsersTable() {
    try (Session session = getSessionFactory().openSession()) {
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null){
            transaction.rollback();
        }
    }
}

@Override
public void dropUsersTable() {
    try (Session session = getSessionFactory().openSession()) {
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS person";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
    } catch (Exception e){
        if (transaction != null) {
            transaction.rollback();
        }
        e.getStackTrace();
    }
}

@Override
public void saveUser(String name, String lastName, byte age) {
    try (Session session = Util.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();
        session.save(new User(name,lastName,age));
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
 @Override
 public void removeUserById(long id) {
     try (Session session = Util.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();
         User user = session.get(User.class, id);
         if (user != null) {
             session.remove(user);
         }
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         e.printStackTrace();
     }
 }

@Override
public List<User> getAllUsers() {
    List<User> users = null;
    try (Session session = Util.getSessionFactory().getCurrentSession()) {
        transaction = session.beginTransaction();
        users = session.createQuery("from User ", User.class)
                .getResultList();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    return users;
}

@Override
public void cleanUsersTable() {
    try  (Session session = Util.getSessionFactory().getCurrentSession()){
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.getStackTrace();
    }
}
}