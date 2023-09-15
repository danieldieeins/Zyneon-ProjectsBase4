package live.nerotv.projectsbase.modules.roleplay.utils;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ConfigAPI;
import live.nerotv.projectsbase.modules.essentials.commands.Vanish;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.SkinManager;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.manager.PointsManager;
import live.nerotv.projectsbase.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class RoleplayUser {

    private final Player player;
    private final UUID uuid;
    private String roleplayName;
    private String roleplayJob;
    private int points;
    private int protectionTime;
    private final OfflinePlayer offlinePlayer;
    private boolean roleplay;
    private boolean autoRoleplay; //BENÖTIGT
    private Character activeChar;
    private ArrayList<String> chars;
    private String chatType = "CHAT";
    private int saergoQuestLevel;

    private void checkTables() {
        if (!MySQL.isConnected()) {
            MySQL.connect();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings_rpn (UUID TEXT,VALUE TEXT)");
            ps.executeUpdate();
        } catch (SQLException ignore) {
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings_rpj (UUID TEXT,VALUE TEXT)");
            ps.executeUpdate();
        } catch (SQLException ignore) {
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings_prt (UUID TEXT,VALUE TEXT)");
            ps.executeUpdate();
        } catch (SQLException ignore) {
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings_hdp (UUID TEXT,VALUE TEXT)");
            ps.executeUpdate();
        } catch (SQLException ignore) {
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings_arp (UUID TEXT,VALUE TEXT)");
            ps.executeUpdate();
        } catch (SQLException ignore) {
        }
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getChatType() {
        return chatType;
    }

    public RoleplayUser(UUID uuid) {
        checkTables();
        if (RoleplayAPI.roleplayUsers.containsKey(uuid)) {
            RoleplayUser u = RoleplayAPI.roleplayUsers.get(uuid);
            this.player = u.getPlayer();
            this.uuid = u.getUUID();
            this.roleplayName = u.getRoleplayName();
            this.roleplayJob = u.getRoleplayJob();
            this.points = u.getAdventurePoints();
            this.protectionTime = u.getProtectionTime();
            this.offlinePlayer = u.getOfflinePlayer();
            this.roleplay = u.isRoleplay();
            this.autoRoleplay = u.isAutomaticRoleplay();
        } else {
            if (Bukkit.getPlayer(uuid) != null) {
                this.player = Bukkit.getPlayer(uuid);
            } else {
                this.player = null;
            }
            this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            this.uuid = uuid;
            this.roleplay = RoleplayAPI.roleplayPlayers.contains(uuid);
            this.points = PointsManager.get(uuid);
            String nameData;
            if (MySQL.is("settings_rpn", uuid.toString())) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_rpn WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        nameData = rs.getString("VALUE");
                    } else {
                        nameData = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    nameData = null;
                }
            } else {
                nameData = null;
            }
            this.roleplayName = nameData;
            String jobData;
            if (MySQL.is("settings_rpj", uuid.toString())) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_rpj WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        jobData = rs.getString("VALUE");
                    } else {
                        jobData = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    jobData = null;
                }
            } else {
                jobData = null;
            }
            this.roleplayJob = jobData;
            int protectionData;
            if (MySQL.is("settings_prt", uuid.toString())) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_prt WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        protectionData = Integer.parseInt(rs.getString("VALUE"));
                    } else {
                        protectionData = 30;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    protectionData = 30;
                }
            } else {
                protectionData = 30;
            }
            this.protectionTime = protectionData;
            boolean autoData;
            if (MySQL.is("settings_arp", uuid.toString())) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_arp WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        autoData = rs.getString("VALUE").equalsIgnoreCase("true");
                    } else {
                        autoData = true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    autoData = true;
                }
            } else {
                autoData = true;
            }
            this.autoRoleplay = autoData;
            RoleplayAPI.roleplayUsers.put(uuid, this);
        }
    }

    public Character getActiveChar() {
        return activeChar;
    }

    public ArrayList<String> getChars() {
        if(chars==null) {
            chars = Characters.getChars(uuid);
        }
        return chars;
    }

    public void setChars(ArrayList<String> chars) {
        this.chars = chars;
        Characters.setChars(uuid,chars);
    }

    public Player getPlayer() {
        return this.player;
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getRoleplayName() {
        return this.roleplayName;
    }

    public String getRoleplayJob() {
        return this.roleplayJob;
    }

    public String getLastLogin() {
        File file = new File("plugins/ProjectsBase/Temp/" + this.uuid.toString() + "-loginouts.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.contains("User.lastLogin")) {
            return config.getString("User.lastLogin");
        } else {
            return null;
        }
    }

    public int getAdventurePoints() {
        return this.points;
    }

    public Location getLastLocation() {
        File file = new File("plugins/ProjectsBase/Temp/" + this.uuid.toString() + "-loginouts.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.contains("User.lastLocation.z")) {
            return new Location(Bukkit.getWorld(config.getString("User.lastLocation.w")), config.getDouble("User.lastLocation.x"), config.getDouble("User.lastLocation.y"), config.getDouble("User.lastLocation.z"));
        } else {
            return null;
        }
    }

    public void setLastLocation(Location lastloc) {
        File file = new File("plugins/ProjectsBase/Temp/" + this.uuid.toString() + "-loginouts.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("User.lastLocation.w", lastloc.getWorld().getName());
        config.set("User.lastLocation.x", lastloc.getX());
        config.set("User.lastLocation.y", lastloc.getY());
        config.set("User.lastLocation.z", lastloc.getZ());
        ConfigAPI.saveConfig(file, config);
        ConfigAPI.reloadConfig(file, config);
    }

    public void setActiveChar(Character activeChar) {
        this.activeChar = activeChar;
        try {
            SkinManager.setSkin(player,activeChar.getSkin(),activeChar.getVariant());
        } catch (NullPointerException ignore) {}
        Characters.setActiveChar(uuid,activeChar.getUUID());
    }

    public void setLastLogin() {
        File file = new File("plugins/ProjectsBase/Temp/" + this.uuid.toString() + "-loginouts.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("User.lastLogin", API.getDateTime());
        ConfigAPI.saveConfig(file, config);
        ConfigAPI.reloadConfig(file, config);
    }

    public void setRoleplay(boolean roleplay) {
        this.roleplay = roleplay;
        if (roleplay) {
            if (!RoleplayAPI.roleplayPlayers.contains(uuid)) {
                RoleplayAPI.roleplayPlayers.add(uuid);
            }
        } else {
            RoleplayAPI.roleplayPlayers.remove(uuid);
            if (this.player != null) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!Vanish.vP.contains(all) || !API.protectedPlayers.contains(all)) {
                        this.player.showPlayer(Main.getInstance(), all);
                    }
                }
            }
        }
    }

    public void setAdventurePoints(int points) {
        this.points = points;
        PointsManager.set(this.uuid, points);
    }

    public void addAdventurePoints(int points) {
        setAdventurePoints(this.points + points);
    }

    public void removeAdventurePoints(int points) {
        setAdventurePoints(this.points - points);
    }

    public void setRoleplayName(String roleplayName) {
        try {
            boolean is;
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_rpn WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ResultSet rs = ps.executeQuery();
                is = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                is = false;
            }
            if (is) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM settings_rpn WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ps.executeUpdate();
            }
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO settings_rpn (UUID,VALUE) VALUES (?,?)");
            ps.setString(1, this.uuid.toString());
            ps.setString(2, "" + roleplayName);
            ps.executeUpdate();
            this.roleplayName = roleplayName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRoleplayJob(String roleplayJob) {
        try {
            boolean is;
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_rpj WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ResultSet rs = ps.executeQuery();
                is = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                is = false;
            }
            if (is) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM settings_rpj WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ps.executeUpdate();
            }
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO settings_rpj (UUID,VALUE) VALUES (?,?)");
            ps.setString(1, this.uuid.toString());
            ps.setString(2, "" + roleplayJob);
            ps.executeUpdate();
            this.roleplayJob = roleplayJob;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAutomaticRoleplay(boolean autoRoleplay) {
        try {
            boolean is;
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_arp WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ResultSet rs = ps.executeQuery();
                is = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                is = false;
            }
            if (is) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM settings_arp WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ps.executeUpdate();
            }
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO settings_arp (UUID,VALUE) VALUES (?,?)");
            ps.setString(1, this.uuid.toString());
            ps.setString(2, "" + autoRoleplay);
            ps.executeUpdate();
            this.autoRoleplay = autoRoleplay;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProtectionTime(int protectionTime) {
        try {
            boolean is;
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT VALUE FROM settings_prt WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ResultSet rs = ps.executeQuery();
                is = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                is = false;
            }
            if (is) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM settings_prt WHERE UUID = ?");
                ps.setString(1, this.uuid.toString());
                ps.executeUpdate();
            }
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO settings_prt (UUID,VALUE) VALUES (?,?)");
            ps.setString(1, this.uuid.toString());
            ps.setString(2, "" + protectionTime);
            ps.executeUpdate();
            this.protectionTime = protectionTime;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendRawMessage(String message) {
        if (player != null) {
            player.sendMessage(message.replace("&&", "%AND%").replace("&", "§").replace("%AND%", "&"));
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(API.PN() + message);
        playSound(Sound.ENTITY_CHICKEN_EGG);
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c" + message);
        playSound(Sound.BLOCK_ANVIL_BREAK);
    }

    public void playSound(Sound sound) {
        if (player != null) {
            player.playSound(player.getLocation(), sound, 100, 100);
        }
    }

    public boolean isRoleplay() {
        return this.roleplay;
    }

    public boolean isAutomaticRoleplay() {
        return this.autoRoleplay;
    }

    public int getProtectionTime() {
        return this.protectionTime;
    }
}