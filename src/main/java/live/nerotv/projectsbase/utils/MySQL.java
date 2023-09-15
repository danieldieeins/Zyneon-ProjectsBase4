package live.nerotv.projectsbase.utils;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.sql.*;
import java.util.UUID;
import static live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI.getRoleplayUser;

public class MySQL {

    public static boolean SQLite = !ConfigAPI.CFG.getBoolean("Core.Settings.MySQL.enable");
    public static String host = ConfigAPI.CFG.getString("Core.Settings.MySQL.host");
    public static String port = ConfigAPI.CFG.getString("Core.Settings.MySQL.port");
    public static String database = ConfigAPI.CFG.getString("Core.Settings.MySQL.database");
    public static String username = ConfigAPI.CFG.getString("Core.Settings.MySQL.username");
    public static String password = ConfigAPI.CFG.getString("Core.Settings.MySQL.password");
    public static boolean isConnected() {
        return (con != null);
    }
    public static Connection getConnection() {
        return con;
    }
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                if(SQLite) {
                    Class.forName("org.sqlite.JDBC");
                    con = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance().getDataFolder() + "/sql.db");
                } else {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
    }

    public static boolean is(String table, String check) {
        boolean is;
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM "+table+" WHERE UUID = ?");
            ps.setString(1, check);
            ResultSet rs = ps.executeQuery();
            is = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }

    public static void transfer(Player p) {
        UUID uuid = p.getUniqueId();
        File file = new File("plugins/ProjectsBase/User/"+uuid.toString()+".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigAPI.reloadConfig(file,config);
        if(config.contains("Spieler.AdventurePoints")) {
            int points = config.getInt("Spieler.AdventurePoints");
            RoleplayUser user = getRoleplayUser(uuid);
            user.addAdventurePoints(points);
            config.set("Spieler.AdventurePoints",null);
            ConfigAPI.saveConfig(file,config);
            ConfigAPI.reloadConfig(file,config);
        }
        if(config.contains("Spieler.protectionTime")) {
            int protectionTime = config.getInt("Spieler.protectionTime");
            RoleplayUser user = getRoleplayUser(uuid);
            user.setProtectionTime(protectionTime);
            config.set("Spieler.protectionTime",null);
            ConfigAPI.saveConfig(file,config);
            ConfigAPI.reloadConfig(file,config);
        }
        if(config.contains("Spieler.RPName")) {
            String protectionTime = config.getString("Spieler.RPName");
            RoleplayUser user = getRoleplayUser(uuid);
            user.setRoleplayName(protectionTime);
            config.set("Spieler.RPName",null);
            ConfigAPI.saveConfig(file,config);
            ConfigAPI.reloadConfig(file,config);
        }
        if(config.contains("Spieler.RPJob")) {
            String protectionTime = config.getString("Spieler.RPJob");
            RoleplayUser user = getRoleplayUser(uuid);
            user.setRoleplayJob(protectionTime);
            config.set("Spieler.RPJob",null);
            ConfigAPI.saveConfig(file,config);
            ConfigAPI.reloadConfig(file,config);
        }
        if(config.contains("Spieler.automaticRoleplay")) {
            boolean protectionTime = config.getBoolean("Spieler.automaticRoleplay");
            RoleplayUser user = getRoleplayUser(uuid);
            user.setAutomaticRoleplay(protectionTime);
            config.set("Spieler.automaticRoleplay",null);
            ConfigAPI.saveConfig(file,config);
            ConfigAPI.reloadConfig(file,config);
        }
    }
}