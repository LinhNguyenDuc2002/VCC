package com.example.vcc_task4.service;

import com.example.vcc_task4.config.ApplicationConfig;
import com.example.vcc_task4.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private Connection connection;

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try {
            // create statement
            Statement statement = connection.createStatement();

            // get data from table 'user'
            ResultSet rs = statement.executeQuery("select * from user");

            // show data
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4))
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User add(User user) throws Exception {
        String sql = "INSERT INTO user VALUES(?, ?, ? ,?)";

        if(checkExistUser(user.getId())) {
            logger.info("User {} existed!", user.getId());
            throw new Exception("User existed!");
        }

        if(StringUtils.isEmpty(user.getName()) ||
            StringUtils.isEmpty(user.getAddress()) ||
            (user.getAge() < 1 || user.getAge() > 100)) {
            logger.error("Invalid user");
            throw new Exception("Invalid user");
        }

        try {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setInt(4, user.getAge());

            preparedStatement.executeUpdate();

            // close connection
            logger.info("Add user successfully!");

            return user;
        } catch (Exception ex) {
            logger.error("Add user failed!");
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";

        if(!checkExistUser(id)) {
            logger.info("User {} isn't existed!", id);
            throw new Exception("User is not existed!");
        }

        try {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            // close connection
            logger.info("Delete user successfully!");
        } catch (Exception ex) {
            logger.error("Add user failed!");
            ex.printStackTrace();
        }
    }

    @Override
    public User update(Integer id, User user) throws Exception {
        String sql = "UPDATE user SET name = ?, address = ?, age = ? WHERE id = ?";

        if(!checkExistUser(id)) {
            logger.info("User {} isn't existed!", id);
            throw new Exception("User is not existed!");
        }

        if(StringUtils.isEmpty(user.getName()) ||
                StringUtils.isEmpty(user.getAddress()) ||
                (user.getAge() < 1 || user.getAge() > 100)) {
            logger.error("Invalid user");
            throw new Exception("Invalid user");
        }

        try {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getAddress());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

            // close connection
            logger.info("Delete user successfully!");
            user.setId(id);
            return user;
        } catch (Exception ex) {
            logger.error("Add user failed!");
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> sortByName() {
        String sql = "SELECT * FROM user ORDER BY name ASC";
        List<User> users = new ArrayList<>();

        try {
            // create statement
            Statement statement = connection.createStatement();

            // get data from table 'user'
            ResultSet rs = statement.executeQuery(sql);

            // show data
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4))
                );
            }
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> findByName(String name) {
        String sql = "SELECT * FROM user WHERE LOWER(name) LIKE LOWER(?)";
        String searchTerm = "%" + name + "%";
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchTerm);

            ResultSet rs = preparedStatement.executeQuery();

            // show data
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4))
                );
            }
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> findById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            // show data
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4))
                );
            }
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> findByAddress(String address) {
        String sql = "SELECT * FROM user WHERE LOWER(address) LIKE LOWER(?)";
        String searchTerm = "%" + address + "%";
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchTerm);

            ResultSet rs = preparedStatement.executeQuery();

            // show data
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4))
                );
            }
            return users;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    private boolean checkExistUser(Integer id) {
        String sql = "SELECT 1 FROM user WHERE id = ? LIMIT 1";
        try {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
