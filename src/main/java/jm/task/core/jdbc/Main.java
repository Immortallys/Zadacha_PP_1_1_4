package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        //   UserDao userDao = new UserDaoJDBCImpl();
        UserDao userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Marina", "Zolotova", (byte) 27);
        userDaoHibernate.saveUser("Maxim", "Chernov", (byte) 25);
        userDaoHibernate.saveUser("Elena", "Ivanova", (byte) 31);
        userDaoHibernate.saveUser("Stepan", "Razin", (byte) 38);

        userDaoHibernate.removeUserById(2);
        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}
