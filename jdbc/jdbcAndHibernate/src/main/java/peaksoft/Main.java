package peaksoft;


import org.hibernate.HibernateException;
import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.model.User;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.Util;

import java.util.List;


public class Main {
    public static void main(String[] args) throws HibernateException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        User user1 = new User("Artur", "lord", (byte) 27);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        User user2 = new User("Lina", "Nejin", (byte) 32);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        User user3 = new User("Dina", "Clim", (byte) 18);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.shutDown();
    }
}
