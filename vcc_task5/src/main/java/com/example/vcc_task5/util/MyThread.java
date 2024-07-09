package com.example.vcc_task5.util;

import com.example.vcc_task5.config.DBCP2Source;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class MyThread extends Thread {
    private int start;

    private int number;

    private String tableName;

    public MyThread(int start, int number, String tableName) {
        this.start = start;
        this.number = number;
        this.tableName = tableName;
    }

    public int getStart() {
        return start;
    }

    public int getNumber() {
        return number;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public void run() {
        String sql = "INSERT INTO " + tableName +" VALUES (?, ?, ?, ?)";

        try (Connection connection = DBCP2Source.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Random random = new Random();
            int count = 10000;
            while(count > 0 && number > 0) {
                connection.setAutoCommit(false); //start a transaction
                if(count > number) {
                    count = number;
                }

                for (int i = start; i < (start + count); i++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, RandomStringUtils.randomAlphabetic(random.nextInt(8) + 2));
                    preparedStatement.setString(3, RandomStringUtils.randomAlphabetic(random.nextInt(8) + 2));
                    preparedStatement.setInt(4, random.nextInt(100 - 1 + 1) + 1);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();

                number -= count;
                start += count;
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
