package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class
UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS myfirstsql_test.users1" +
                    "(id mediumint not null auto_increment," +
                    " name VARCHAR(100), " +
                    "lastname VARCHAR(100), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))");
            System.out.println("Таблица создана");
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS myfirstsql_test.users1");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO myfirstsql_test.users1(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу");
        } catch (SQLException e) {

        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM myfirstsql_test.users1 where id");
            System.out.println("User с id " + id + " удален");
        } catch (SQLException e) {

        }
    }


    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from myfirstsql_test.users1";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (Exception e) {

        }
        return allUser;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM myfirstsql_test.users1");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {

        }
    }
}
