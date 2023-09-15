package live.nerotv.projectsbase.modules.roleplay.characters;

import com.zyneonstudios.api.utils.sql.MySQL;
import com.zyneonstudios.api.utils.sql.SQLite;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.commands.CharacterCommand;
import live.nerotv.projectsbase.modules.roleplay.characters.listeners.CharactersInventoryListener;
import live.nerotv.projectsbase.modules.roleplay.characters.listeners.CharactersJoinListener;
import live.nerotv.projectsbase.modules.roleplay.characters.listeners.CharactersProtectionListener;
import live.nerotv.projectsbase.modules.roleplay.characters.listeners.CharactersQuitListener;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import net.skinsrestorer.api.SkinVariant;
import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Characters {

    public static boolean enableCharacters;
    public static Connection connection;
    private static String v = "(v1.1.0)";
    public static ArrayList<Player> locked = new ArrayList<>();

    public static void initCharacters() {
        enableCharacters = RoleplayAPI.roleplayConfig.getCFG().getBoolean("Roleplay.subModules.Characters.enable");
        if(enableCharacters) {
            ServerAPI.sendConsoleMessage("§f[Roleplay]§0    §f-> §eCharacters "+v+" §7wird geladen§8...");
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.enable",false);
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.host","localhost");
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.port","3306");
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.database","zyneon_characters");
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.username","root");
            RoleplayAPI.roleplayConfig.checkEntry("Roleplay.subModules.Characters.MySQL.password","password");
            if(RoleplayAPI.roleplayConfig.getCFG().getBoolean("Roleplay.subModules.Characters.MySQL.enable")) {
                connection = new MySQL(RoleplayAPI.roleplayConfig.getCFG().getString("Roleplay.subModules.Characters.MySQL.host"),RoleplayAPI.roleplayConfig.getCFG().getString("Roleplay.subModules.Characters.MySQL.port"),RoleplayAPI.roleplayConfig.getCFG().getString("Roleplay.subModules.Characters.MySQL.database"),RoleplayAPI.roleplayConfig.getCFG().getString("Roleplay.subModules.Characters.MySQL.username"),RoleplayAPI.roleplayConfig.getCFG().getString("Roleplay.subModules.Characters.MySQL.password")).getConnection();
            } else {
                connection = new SQLite("plugins/ProjectsBase/roleplay/chars.sql").getConnection();
            }
            checkTable();
            ServerAPI.sendConsoleMessage("§f[Roleplay]§0    §f-> §eCharacters "+v+" §7wurde geladen§8!");
            ServerAPI.sendConsoleMessage("§f[Roleplay]§0    §f-> §eCharacters "+v+" §7wird aktiviert§8...");
            API.initListeners(new CharactersInventoryListener());
            API.initListeners(new CharactersJoinListener());
            API.initListeners(new CharactersProtectionListener());
            API.initListeners(new CharactersQuitListener());
            API.initCommand("Character",new CharacterCommand());
            ServerAPI.sendConsoleMessage("§f[Roleplay]§0    §f-> §eCharacters "+v+" §7wurde §aaktiviert§8!");
        }
    }

    public static boolean checkTable() {
        boolean rtn;
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS characters (UUID TEXT,CHARS TEXT)");
            ps.executeUpdate();
            rtn = true;
        } catch (SQLException e) {
            rtn = false;
        }
        if(rtn) {
            try {
                PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS character_contents (CHARID TEXT,NAME TEXT,JOB TEXT,AGE TEXT,SKIN TEXT,VARIENT TEXT)");
                ps.executeUpdate();
            } catch (SQLException e) {
                rtn = false;
            }
        }
        if(rtn) {
            try {
                PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS character_outfits (CHARID TEXT,OUTFITS TEXT)");
                ps.executeUpdate();
            } catch (SQLException e) {
                rtn = false;
            }
        }
        if(rtn) {
            try {
                PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS active_chars (UUID TEXT,CHARID TEXT)");
                ps.executeUpdate();
            } catch (SQLException e) {
                rtn = false;
            }
        }
        return rtn;
    }

    public static boolean hasChars(UUID uuid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT CHARS FROM characters WHERE UUID = ?");
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

    public static boolean hasOutfits(UUID charid) {
        if(checkTable()) {
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT OUTFITS FROM character_outfits WHERE CHARID = ?");
                ps.setString(1, charid.toString());
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

    public static boolean setOutfits(UUID charid, ArrayList<String> outfitList) {
        String SID = charid.toString();
        String OID = outfitList.toString();
        if (checkTable()) {
            try {
                if (hasOutfits(charid)) {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM character_outfits WHERE CHARID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = connection.prepareStatement("INSERT INTO character_outfits (CHARID,OUTFITS) VALUES (?,?)");
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

    public static ArrayList<String> getOutfits(UUID charid) {
        if(checkTable()) {
            String data;
            if (hasOutfits(charid)) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT OUTFITS FROM character_outfits WHERE CHARID = ?");
                    ps.setString(1,charid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("OUTFITS");
                    } else {
                        return new ArrayList<String>();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new ArrayList<String>();
                }
            } else {
                return new ArrayList<String>();
            }
            return new ArrayList<String>(Arrays.asList(data.replace(" ","").replace("[","").replace("]","").split(",")));
        } else {
            return new ArrayList<String>();
        }
    }

    public static boolean hasActiveChar(UUID uuid) {
        return getActiveChar(uuid) != null;
    }

    public static boolean setChars(UUID uuid, ArrayList<String> charidList) {
        String SID = uuid.toString();
        String OID = charidList.toString();
        if (checkTable()) {
            try {
                if (hasChars(uuid)) {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM characters WHERE UUID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = connection.prepareStatement("INSERT INTO characters (UUID,CHARS) VALUES (?,?)");
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

    public static boolean setActiveChar(UUID uuid, UUID charid) {
        String SID = uuid.toString();
        String CID = charid.toString();
        if (checkTable()) {
            try {
                if (hasChars(uuid)) {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM active_chars WHERE UUID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = connection.prepareStatement("INSERT INTO active_chars (UUID,CHARID) VALUES (?,?)");
                ps.setString(1, SID);
                ps.setString(2, CID);
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

    public static boolean deleteChars(UUID uuid) {
        try {
            if (hasChars(uuid)) {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM characters WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Character getActiveChar(UUID uuid) {
        if(checkTable()) {
            String data;
            if (hasChars(uuid)) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT CHARID FROM active_chars WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("CHARID");
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            return new Character(UUID.fromString(data));
        } else {
            return null;
        }
    }

    public static ArrayList<String> getChars(UUID uuid) {
        if(checkTable()) {
            String data;
            if (hasChars(uuid)) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT CHARS FROM characters WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("CHARS");
                    } else {
                        return new ArrayList<String>();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new ArrayList<String>();
                }
            } else {
                return new ArrayList<String>();
            }
            return new ArrayList<String>(Arrays.asList(data.replace(" ","").replace("[","").replace("]","").split(",")));
        } else {
            return new ArrayList<String>();
        }
    }

    public static void createCharacter(UUID uuid) {
        UUID charid = UUID.randomUUID();
        Character c = new Character(charid);
        c.setName("§cKein Name");
        c.setJob("§cKein Beruf");
        c.setAge("§c-1");
        c.setVariant(SkinVariant.CLASSIC);
        c.setSkin(SkinsRestorerAPI.getApi().getSkinTextureUrl(Bukkit.getOfflinePlayer(uuid).getName()));
        RoleplayUser u = RoleplayAPI.getRoleplayUser(uuid);
        ArrayList<String> chars = u.getChars();
        chars.add(charid.toString());
        u.setChars(chars);
        u.setActiveChar(c);
    }

    public static void deleteCharacter(UUID uuid, UUID charid) {
        RoleplayUser u = RoleplayAPI.getRoleplayUser(uuid);
        UUID aID = u.getActiveChar().getUUID();
        ArrayList<String> list = getChars(uuid);
        if(list.size()<2) {
            if(Bukkit.getPlayer(uuid)!=null) {
                API.sendErrorMessage(Bukkit.getPlayer(uuid),"§cDu kannst zureit keine Charaktere löschen§8!");
                Bukkit.getPlayer(uuid).closeInventory();
            }
            return;
        }
        String CID = charid.toString().replace(" ","");
        list.remove(CID);
        u.setChars(list);
        if(CID.equals(aID.toString().replace(" ",""))) {
            u.setActiveChar(new Character(UUID.fromString(list.get(0))));
        }
        try {
            if (hasChars(uuid)) {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM active_chars WHERE CHARID = ?");
                ps.setString(1, CID);
                ps.executeUpdate();
            }
        } catch (SQLException ignore) {}
    }
}