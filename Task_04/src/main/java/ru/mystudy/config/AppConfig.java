package ru.mystudy.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        Properties props = new Properties();

        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("dataSource.databaseName", "postgres");
        props.setProperty("dataSource.serverName", "localhost");
        props.setProperty("dataSource.portNumber", "5432");

        return new HikariDataSource(new HikariConfig(props));
    }

    @Bean
    public Connection connection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
