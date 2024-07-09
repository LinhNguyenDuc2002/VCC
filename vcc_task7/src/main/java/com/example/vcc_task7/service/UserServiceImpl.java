package com.example.vcc_task7.service;

import com.example.vcc_task7.config.DBCP2Source;
import com.example.vcc_task7.entity.User;
import com.example.vcc_task7.exception.CommonException;
import com.example.vcc_task7.util.StatusCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private Lock lock = new ReentrantLock();

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DBCP2Source.getConnection()) { // auto close connection without using finally block to close
            // create statement
            Statement statement = connection.createStatement();

            // get data from table 'user'
            ResultSet rs = statement.executeQuery("select * from user");
            Thread.sleep(2000);
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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
        return users;
    }

    @Override
    public User add(User user) throws Exception {
        String sql = "INSERT INTO user VALUES(?, ?, ? ,?)";

        if (checkExistUser(user.getId())) {
            logger.info("User {} existed!", user.getId());
            throw new CommonException(true, "User existed!", StatusCode.OBJECT_EXISTED);
        }

        if (StringUtils.isEmpty(user.getName()) ||
                StringUtils.isEmpty(user.getAddress()) ||
                (user.getAge() < 1 || user.getAge() > 100)) {
            logger.error("Invalid user");
            throw new CommonException(true, user, "Invalid data!", StatusCode.INVALID_DATA);
        }

        try (Connection connection = DBCP2Source.getConnection()) {
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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";

        if (!checkExistUser(id)) {
            logger.info("User {} isn't existed!", id);
            throw new CommonException(true, "User is not existed!", HttpStatus.NOT_FOUND.value());
        }

        try (Connection connection = DBCP2Source.getConnection()) {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            // close connection
            logger.info("Delete user successfully!");
        } catch (Exception ex) {
            logger.error("Add user failed!");
            ex.printStackTrace();
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public User update(Integer id, User user) throws Exception {
        String sql = "UPDATE user SET name = ?, address = ?, age = ? WHERE id = ?";

        if (!checkExistUser(id)) {
            logger.info("User {} isn't existed!", id);
            throw new CommonException(true, "User is not existed!", HttpStatus.NOT_FOUND.value());
        }

        if (StringUtils.isEmpty(user.getName()) ||
                StringUtils.isEmpty(user.getAddress()) ||
                (user.getAge() < 1 || user.getAge() > 100)) {
            logger.error("Invalid user");
            throw new CommonException(true, user, "Invalid data!", StatusCode.INVALID_DATA);
        }

        try (Connection connection = DBCP2Source.getConnection()) {
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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public List<User> sortByName() {
        String sql = "SELECT * FROM user ORDER BY name ASC";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBCP2Source.getConnection()) {
            // create statement
            Statement statement = connection.createStatement();

            // get data from table 'user'
            ResultSet rs = statement.executeQuery(sql);
            Thread.sleep(2000);

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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public List<User> findByName(String name) {
        String sql = "SELECT * FROM user WHERE LOWER(name) LIKE LOWER(?)";
        String searchTerm = "%" + name + "%";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBCP2Source.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchTerm);

            ResultSet rs = preparedStatement.executeQuery();
            Thread.sleep(2000);

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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public List<User> findById(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBCP2Source.getConnection()) {
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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public List<User> findByAddress(String address) {
        String sql = "SELECT * FROM user WHERE LOWER(address) LIKE LOWER(?)";
        String searchTerm = "%" + address + "%";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBCP2Source.getConnection()) {
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
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
    }

    @Override
    public User addMoney(Integer id, Long money) throws Exception {
        lock.lock();
        try {
            String moneySql = "SELECT money FROM user WHERE id = ?";
            String updateSql = "UPDATE user SET money = ? WHERE id = ?";
            String sql = "SELECT * FROM user WHERE id = ?";

            if (!checkExistUser(id)) {
                logger.info("User {} isn't existed!", id);
                throw new Exception("User is not existed!");
            }

            if (money < 0) {
                logger.error("Invalid money");
                throw new Exception("Invalid money");
            }

            try (Connection connection = DBCP2Source.getConnection()) {
                Long total = 0L;

                // get current money
                PreparedStatement preparedStatement = connection.prepareStatement(moneySql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    total = rs.getLong(1);
                }

                // update money after adding
                preparedStatement = connection.prepareStatement(updateSql);
                preparedStatement.setLong(1, total + money);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                // get user after updating
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getLong(5));
                }
            } catch (Exception ex) {
                logger.error("Add money for user failed!");
                ex.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public void sendMoney(Integer userA, Integer userB, Long money) throws Exception {
        lock.lock();
        try {
            String checkSql = "SELECT money FROM user WHERE id = ?";
            String sql1 = "UPDATE user SET money = money - ? WHERE id = ?";
            String sql2 = "UPDATE user SET money = money + ? WHERE id = ?";

            if (!checkExistUser(userA) || !checkExistUser(userB)) {
                logger.info("User isn't existed!");
                throw new Exception("User is not existed!");
            }

            if (money < 0) {
                logger.error("Invalid money");
                throw new Exception("Invalid money");
            }

            try (Connection connection = DBCP2Source.getConnection()) {
                // create statement
                PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
                preparedStatement.setInt(1, userA);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    if (rs.getLong(1) < money) {
                        throw new Exception("You don't enough money!");
                    }
                }

                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setLong(1, money);
                preparedStatement.setInt(2, userA);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setLong(1, money);
                preparedStatement.setInt(2, userB);
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                logger.error("Add user failed!");
                ex.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean checkExistUser(Integer id) {
        String sql = "SELECT 1 FROM user WHERE id = ? LIMIT 1";

        try (Connection connection = DBCP2Source.getConnection()) {
            // create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CommonException(true, "Server error", StatusCode.ERROR);
        }
        return false;
    }
}
