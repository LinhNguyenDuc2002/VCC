package com.example.vcc_task5;

import com.example.vcc_task5.config.DBCP2Source;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


public class RandomDataTask6 {
    private static Logger logger = LogManager.getLogger(RandomDataTask6.class);
    public static void generateData(Integer number, String tableName) {
        String sql = "INSERT INTO " + tableName +" VALUES (?, ?, ?, ?)";

        try (Connection connection = DBCP2Source.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false); //start a transaction

            Random random = new Random();
            for (int i = 0; i < number; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, RandomStringUtils.randomAlphabetic(random.nextInt(10) + 2).toLowerCase());
                preparedStatement.setString(3, RandomStringUtils.randomAlphabetic(random.nextInt(10) + 2).toLowerCase());
                preparedStatement.setInt(4, random.nextInt(100 - 1 + 1) + 1);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startWith (String name, String tableName) {
        logger.info("start");
        String sql = "SELECT * FROM " + tableName +" WHERE name LIKE \'" + name + "%\'";

        try (Connection connection = DBCP2Source.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                System.out.println(
                        resultSet.getInt(1) + " " +
                        resultSet.getString(2) + " " +
                        resultSet.getString(3) + " " +
                        resultSet.getInt(4) + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("end");
    }

    public static void containWith (String name, String tableName) {
        logger.info("start");
        String sql = "SELECT * FROM " + tableName +" WHERE name LIKE \'%" + name + "%\'";

        try (Connection connection = DBCP2Source.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                System.out.println(
                        resultSet.getInt(1) + " " +
                                resultSet.getString(2) + " " +
                                resultSet.getString(3) + " " +
                                resultSet.getInt(4) + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("end");
    }

    public static void withName (String name, String tableName) {
        logger.info("start");
        String sql = "SELECT * FROM " + tableName +" WHERE name = \'" + name + "\'";

        try (Connection connection = DBCP2Source.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                System.out.println(
                        resultSet.getInt(1) + " " +
                                resultSet.getString(2) + " " +
                                resultSet.getString(3) + " " +
                                resultSet.getInt(4) + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("end");
    }
}
