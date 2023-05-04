//package jm.task.core.jdbc;
//
//import jm.task.core.jdbc.service.UserService;
//import jm.task.core.jdbc.service.UserServiceImpl;
//import jm.task.core.jdbc.util.Util;
//
//public class Main {
//    private final static UserService userService = new UserServiceImpl();
//    public static void main(String[] args) {
//        // реализуйте алгоритм здесь
//        userService.createUsersTable();
//        userService.saveUser("Галина", "Арбатская", (byte) 42);
//        userService.saveUser("Денис ", "Руднев", (byte) 48);
//        userService.saveUser("Майя", "Кощке", (byte) 8);
//        userService.saveUser("Тигра", "Собащке", (byte) 3);
//        userService.removeUserById(3);
//        userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
//        Util.closeConnection();
//    }
//}
package jm.task.core.jdbc;
        import jm.task.core.jdbc.service.UserService;
        import jm.task.core.jdbc.service.UserServiceImpl;
        import jm.task.core.jdbc.util.Util;

        import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService userDao = new UserServiceImpl();

        userDao.createUsersTable();

        userDao.saveUser("Галина", "Арбатская", (byte) 42);
        userDao.saveUser("Денис ", "Руднев", (byte) 48);
        userDao.saveUser("Майя", "Кощке", (byte) 8);
        userDao.saveUser("Тигра", "Собащке", (byte) 3);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
       // Util.closeConnection();
    }
}
