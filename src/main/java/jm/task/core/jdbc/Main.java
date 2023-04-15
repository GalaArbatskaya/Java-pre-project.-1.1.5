package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        userService.createUsersTable();
        userService.saveUser("Галина", "Арбатская", (byte) 42);
        userService.saveUser("Денис ", "Руднев", (byte) 48);
        userService.saveUser("Майя", "Кощке", (byte) 8);
        userService.saveUser("Тигра", "Собащке", (byte) 3);
        userService.removeUserById(3);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeConnection();
    }
}
