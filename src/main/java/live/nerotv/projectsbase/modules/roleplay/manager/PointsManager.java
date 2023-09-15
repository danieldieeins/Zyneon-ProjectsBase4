package live.nerotv.projectsbase.modules.roleplay.manager;

import live.nerotv.projectsbase.utils.MySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PointsManager {

    public static Boolean checkPayment(Integer AP, Integer Price) {
        int Value = AP+1-Price;
        return Value >= 1;
    }

    public static boolean checkTable() {
        if(!MySQL.isConnected()) {
            MySQL.connect();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS apdatabase (UUID TEXT,Points DOUBLE(30,2))");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean hasAccount(UUID uuid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Points FROM apdatabase WHERE UUID = ?");
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

    public static boolean delete(UUID uuid) {
        try {
            if (hasAccount(uuid)) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM apdatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean set(UUID uuid, int points) {
        String SID = uuid.toString();
        if(checkTable()) {
            if ((double)points < 0.0D) {
                return false;
            } else {
                try {
                    if (hasAccount(uuid)) {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM apdatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO apdatabase (UUID,Points) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, (double)points);
                    ps.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static int get(UUID uuid) {
        if(checkTable()) {
            double data;
            if (hasAccount(uuid)) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Points FROM apdatabase WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getDouble("Points");
                    } else {
                        data = 0.0;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    data = 0.0;
                }
            } else {
                data = 0.0;
            }
            return (int)data;
        } else {
            return 0;
        }
    }
}