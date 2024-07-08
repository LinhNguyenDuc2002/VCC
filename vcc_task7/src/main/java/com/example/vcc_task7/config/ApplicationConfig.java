//package com.example.vcc_task7.config;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//@Configuration
//public class ApplicationConfig {
//    private Logger logger = LogManager.getLogger(ApplicationConfig.class);
//
//    @Bean
//    public Connection getConnection() {
//        Connection connection = null;
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection(DBConfiguration.DB_URL, DBConfiguration.USER_NAME, DBConfiguration.PASSWORD);
//
//            logger.log(Level.INFO, "Connect successfully!");
//            return connection;
//        } catch (Exception e) {
//            logger.log(Level.ERROR, "Connect failure!");
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
