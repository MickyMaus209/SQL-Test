package de.mickymaus209.api;

import de.mickymaus209.api.database.Data;
import de.mickymaus209.api.database.DataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class API extends JavaPlugin {
    public static final Logger LOGGER = Bukkit.getLogger();
    public static String prefix, noPermission, wrongUsage;
    private DataSource dataSource;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.dataSource = new DataSource(this);
        dataSource.createTable("CREATE TABLE IF NOT EXISTS Messages(path VARCHAR(64), value VARCHAR(256))");
        new Data(this, "Messages").set(new Object[]{"prefix", "prefix"}, "value", "path", "prefix");
       /*
        prefix = getRawMessage("messages", 1, "path", "value", "prefix");
        noPermission = getRawMessage("messages", 1, "path", "value", "noPermission");

        */
    }

    @Override
    public void onDisable() {
        if (dataSource != null && dataSource.getHikari() != null)
            dataSource.getHikari().close();
    }

    public String getRawMessage(String table, int parameterIndex, String pathName, String valueName, String path){
        return ChatColor.translateAlternateColorCodes('&', (String) new Data(this, table).get(parameterIndex, pathName, valueName, path).getValue());
    }

    public String getMessage(Player player, String messagePath){
        Data message = new Data(this, "messages" + new Data(this, "playerconfigurations").get(1, "uuid", "language", player.getUniqueId().toString()).getValue())
                .get(1, "path", "value", messagePath);
        if(message.getValue() == null){
            //message.set(new Object[]{messagePath}, "N/A");
            return "N/A";
        }
        return (String) message.getValue();
    }

    public String getUsage(Command command) {
        return wrongUsage = getRawMessage("message", 1, "path", "value", "wrongUsage").replaceAll("%command%", command.getUsage());
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
