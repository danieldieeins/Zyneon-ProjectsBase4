package live.nerotv.projectsbase.modules.economy.utils;

import live.nerotv.projectsbase.modules.economy.EconomyMain;
import live.nerotv.projectsbase.utils.MySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Ecosystem implements Economy {

    public static boolean checkTable() {
        if(!MySQL.isConnected()) {
            MySQL.connect();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ecodatabase (UUID TEXT,Eco DOUBLE(30,2))");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean checkPayment(UUID uuid, double price) {
        if(EconomyMain.getEco().hasAccount(uuid)) {
            double balance = EconomyMain.getEco().getBalance(uuid).getBalance();
            return balance - price >= 0;
        } else {
            return false;
        }
    }

    public boolean createAccount(UUID uuid) {
        set(uuid,0);
        return true;
    }

    public boolean hasAccount(UUID uuid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
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


    public boolean delete(UUID uuid) {
        try {
            if (hasAccount(uuid)) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() - amount);
    }

    public boolean deposit(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() + amount);
    }

    public boolean set(UUID uuid, double amount) {
        String SID = uuid.toString();
        if(checkTable()) {
            if (amount < 0.0D) {
                return false;
            } else {
                try {
                    if (hasAccount(uuid)) {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, amount);
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

    public boolean has(UUID uuid, double amount) {
        return (getBalance(uuid).getBalance() >= amount);
    }

    public PlayerBalance getBalance(UUID uuid) {
        if(checkTable()) {
            double data;
            if (hasAccount(uuid)) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getDouble("Eco");
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
            return new PlayerBalance(uuid, data);
        } else {
            return null;
        }
    }

    public List<PlayerBalance> getPlayers() {
        return null;
    }
}