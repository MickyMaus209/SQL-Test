package de.mickymaus209.api.database;

import com.zaxxer.hikari.HikariDataSource;
import de.mickymaus209.api.API;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
    private HikariDataSource hikari;

    public DataSource(API api) {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", api.getConfig().getString("host"));
        hikari.addDataSourceProperty("port", api.getConfig().getString("port"));
        hikari.addDataSourceProperty("databaseName", api.getConfig().getString("database"));
        hikari.addDataSourceProperty("user", api.getConfig().getString("user"));
        hikari.addDataSourceProperty("password", api.getConfig().getString("password"));
    }

    public void createTable(String sqlCommand) {
        try (Connection connection = hikari.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HikariDataSource getHikari() {
        return hikari;
    }
}
