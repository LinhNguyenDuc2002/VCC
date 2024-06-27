package com.example.vcc_task4.config;

import com.example.vcc_task4.service.UserServiceImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Create connection pooling with JDBC
 */
public class DBCP2Source {
    private static Logger logger = LogManager.getLogger(DBCP2Source.class);

    private static BasicDataSource dataSource = new BasicDataSource();

    // run only once
    static {
        dataSource.setUrl(DBConfiguration.DB_URL);
        dataSource.setUsername(DBConfiguration.USER_NAME);
        dataSource.setPassword(DBConfiguration.PASSWORD);

        dataSource.setMinIdle(1); //duy trì tối thiểu
        dataSource.setMaxIdle(2); //duy trì tối đa
        dataSource.setMaxTotal(2); //tối đa có thể có
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();

        logger.info("-----------------------");
        logger.info("+ Num of Idle Connections:: " + dataSource.getNumIdle()); //free
        logger.info("+ Num of Busy Connections: " + dataSource.getNumActive()); //busy
        return connection;
    }
}
