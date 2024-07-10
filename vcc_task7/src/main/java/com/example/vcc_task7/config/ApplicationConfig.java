package com.example.vcc_task4.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class ApplicationConfig {
    private Logger logger = LogManager.getLogger(ApplicationConfig.class);

//    @Bean
//    public AsyncTaskExecutor initTaskExecutor() {
//        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
//        pool.setCorePoolSize(corePoolSize);
//        pool.setMaxPoolSize(maxPoolSize);
//        pool.setQueueCapacity(queueCapacity);
//        pool.setWaitForTasksToCompleteOnShutdown(true);
//        pool.setThreadNamePrefix("task-");
//
//        return pool;
//    }

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
}
