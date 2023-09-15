package live.nerotv.projectsbase.modules.lock.api;

import com.zyneonstudios.api.utils.sql.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LockAPI {

    static SQLite sql = new SQLite("plugins/ProjectsBase/lock/database.sql");

    public static boolean checkTable() {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS horsedatabase (UUID TEXT,OWNER TEXT)");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean isLocked(UUID uuid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = sql.getConnection().prepareStatement("SELECT OWNER FROM horsedatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }


    public static boolean unlock(UUID uuid) {
        try {
            if (isLocked(uuid)) {
                PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM horsedatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setOwner(UUID uuid, UUID owner) {
        String SID = uuid.toString();
        String OID = owner.toString();
        if (checkTable()) {
            try {
                if (isLocked(uuid)) {
                    PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM horsedatabase WHERE UUID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO horsedatabase (UUID,OWNER) VALUES (?,?)");
                ps.setString(1, SID);
                ps.setString(2, OID);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static OfflinePlayer getOwner(UUID uuid) {
        if(checkTable()) {
            String data;
            if (isLocked(uuid)) {
                try {
                    PreparedStatement ps = sql.getConnection().prepareStatement("SELECT OWNER FROM horsedatabase WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("OWNER");
                    } else {
                        data = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    data = null;
                }
            } else {
                data = null;
            }
            return Bukkit.getOfflinePlayer(UUID.fromString(data));
        } else {
            return null;
        }
    }
}