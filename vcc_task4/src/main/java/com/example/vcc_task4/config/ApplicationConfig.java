package com.example.vcc_task4.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class ApplicationConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vcc_user";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "16042002";

    private Logger logger = LogManager.getLogger(ApplicationConfig.class);

    @Bean
    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            logger.log(Level.INFO, "Connect successfully!");
            return connection;
        } catch (Exception e) {
            logger.log(Level.ERROR, "Connect failure!");
            e.printStackTrace();
            return null;
        }
    }
}
