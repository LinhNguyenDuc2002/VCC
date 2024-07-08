package com.example.vcc_task5;

import com.example.vcc_task5.config.DBCP2Source;
import com.example.vcc_task5.util.MyThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RandomDataTask6 {
    private static Logger logger = LogManager.getLogger(RandomDataTask6.class);
    public static void generateData(Integer number, String tableName) {
        MyThread thread1 = new MyThread(0, 625000, tableName);
        MyThread thread2 = new MyThread(625000, 625000, tableName);
        MyThread thread3 = new MyThread(1250000, 625000, tableName);
        MyThread thread4 = new MyThread(1875000, 625000, tableName);
        MyThread thread5 = new MyThread(2500000, 625000, tableName);
        MyThread thread6 = new MyThread(3125000, 625000, tableName);
        MyThread thread7 = new MyThread(3750000, 625000, tableName);
        MyThread thread8 = new MyThread(4375000, 625000, tableName);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
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
