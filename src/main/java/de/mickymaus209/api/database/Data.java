package de.mickymaus209.api.database;

import de.mickymaus209.api.API;
import org.bukkit.Bukkit;

import java.sql.*;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class Data {
    private API api;
    private Object value;
    private String table;

    public Data(API api, String table) {
        this.api = api;
        this.table = table;
    }

    public Data get(int parameterIndex, String pathName, String valueName, Object x) {
        Bukkit.getScheduler().runTaskAsynchronously(api, () -> {
            try (Connection connection = api.getDataSource().getHikari().getConnection();
                 PreparedStatement s = connection.prepareStatement("SELECT " + valueName + " FROM " + table + " WHERE " + pathName + "=?")) {

                s.setObject(parameterIndex, x);
                ResultSet r = s.executeQuery();

                if (r.next())
                    value = r.getObject(String.valueOf(valueName));
                r.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return this;
    }

    public void set(Object[] insertObjects, String valueName, String pathName, String pathValue) {
        final String select = exists(1, pathValue, pathName) ? "UPDATE " + table + " SET " + valueName + "= ? WHERE "+ pathName +" = ?" : "INSERT INTO " + table +" (" + pathName + "," + valueName + ") VALUES (?,?)";
        Bukkit.getScheduler().runTaskAsynchronously(api, () -> {
            try (Connection connection = api.getDataSource().getHikari().getConnection();
                 PreparedStatement i = connection.prepareStatement(select)) {

                for (int j = 0; j < insertObjects.length; j++) {
                    i.setObject(j + 1, insertObjects[j]);
                }
                i.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean exists(int parameterIndex, Object x, String pathName){
        AtomicBoolean b = new AtomicBoolean(false);
        Bukkit.getScheduler().runTaskAsynchronously(api, () -> {
            try (Connection connection =  api.getDataSource().getHikari().getConnection();
                 PreparedStatement s = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + pathName + "=?")) {

                s.setObject(parameterIndex, x);
                ResultSet r = s.executeQuery();
                if (r.next())
                b.set(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return b.get();
    }

    public Object getValue() {
        return value;
    }
}












