package live.nerotv.projectsbase.modules.roleplay.characters.objects;

import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import net.skinsrestorer.api.SkinVariant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Character {

    private UUID uuid;
    private String skin;
    private String name;
    private String job;
    private String age;
    private String outfitName;
    private SkinVariant variant;
    private ArrayList<String> outfits;

    public Character(UUID uuid) {
        this.uuid = uuid;
        variant = getVariantSQL();
        skin = getSkinSQL();
        name = getNameSQL();
        job = getJobSQL();
        age = getAgeSQL();
        outfits = Characters.getOutfits(uuid);
        outfitName = null;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName.replace("/","").replace("https","").replace("http","").replace(":","").replace("-","");
    }

    public ArrayList<String> getOutfits() {
        return outfits;
    }

    public void setOutfits(ArrayList<String> outfits) {
        this.outfits = outfits;
        Characters.setOutfits(uuid,outfits);
    }

    public boolean hasSkin() {
        if(Characters.checkTable()) {
            try {
                PreparedStatement ps = Characters.connection.prepareStatement("SELECT SKIN FROM character_contents WHERE CHARID = ?");
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

    public boolean hasName() {
        if(Characters.checkTable()) {
            try {
                PreparedStatement ps = Characters.connection.prepareStatement("SELECT NAME FROM character_contents WHERE CHARID = ?");
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

    public boolean hasJob() {
        if(Characters.checkTable()) {
            try {
                PreparedStatement ps = Characters.connection.prepareStatement("SELECT JOB FROM character_contents WHERE CHARID = ?");
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

    public boolean hasAge() {
        if(Characters.checkTable()) {
            try {
                PreparedStatement ps = Characters.connection.prepareStatement("SELECT AGE FROM character_contents WHERE CHARID = ?");
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

    public SkinVariant getVariant() {
        return variant;
    }

    public String getSkin() {
        return skin;
    }

    public String getName() {
        if(name == null) {
            return "§4NICHTS";
        }
        return name;
    }

    public String getJob() {
        if(job == null) {
            return "§4NICHTS";
        }
        return job;
    }

    public String getAge() {
        if(age == null) {
            return "-1";
        }
        return age;
    }

    public UUID getUUID() {
        return uuid;
    }

    public SkinVariant getVariantSQL() {
        if(Characters.checkTable()) {
            String data;
            if (hasSkin()) {
                try {
                    PreparedStatement ps = Characters.connection.prepareStatement("SELECT VARIENT FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("VARIENT");
                    } else {
                        return SkinVariant.CLASSIC;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return SkinVariant.CLASSIC;
                }
            } else {
                return SkinVariant.CLASSIC;
            }
            try {
                return SkinVariant.valueOf(data);
            } catch(NullPointerException ex) {
                return SkinVariant.CLASSIC;
            }

        } else {
            return SkinVariant.CLASSIC;
        }
    }

    public String getSkinSQL() {
        if(Characters.checkTable()) {
            String data;
            if (hasSkin()) {
                try {
                    PreparedStatement ps = Characters.connection.prepareStatement("SELECT SKIN FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("SKIN");
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
            return data;
        } else {
            return null;
        }
    }

    public String getNameSQL() {
        if(Characters.checkTable()) {
            String data;
            if (hasName()) {
                try {
                    PreparedStatement ps = Characters.connection.prepareStatement("SELECT NAME FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("NAME");
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
            return data;
        } else {
            return null;
        }
    }

    public String getJobSQL() {
        if(Characters.checkTable()) {
            String data;
            if (hasJob()) {
                try {
                    PreparedStatement ps = Characters.connection.prepareStatement("SELECT JOB FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("JOB");
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
            return data;
        } else {
            return null;
        }
    }

    public String getAgeSQL() {
        if(Characters.checkTable()) {
            String data;
            if (hasAge()) {
                try {
                    PreparedStatement ps = Characters.connection.prepareStatement("SELECT AGE FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("AGE");
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
            return data;
        } else {
            return null;
        }
    }

    public void setVariant(SkinVariant variant) {
        this.variant = variant;
        updateContents();
    }

    public boolean setSkin(String skinURL) {
        skin = skinURL;
        return updateContents();
    }

    public boolean setName(String name) {
        this.name = name;
        return updateContents();
    }

    public boolean setJob(String job) {
        this.job = job;
        return updateContents();
    }

    public boolean setAge(String age) {
        this.age = age;
        return updateContents();
    }

    public boolean updateContents() {
        String SID = uuid.toString();
        if (Characters.checkTable()) {
            try {
                if (hasSkin()) {
                    PreparedStatement ps = Characters.connection.prepareStatement("DELETE FROM character_contents WHERE CHARID = ?");
                    ps.setString(1, SID);
                    ps.executeUpdate();
                }
                PreparedStatement ps = Characters.connection.prepareStatement("INSERT INTO character_contents (CHARID,NAME,JOB,AGE,SKIN,VARIENT) VALUES (?,?,?,?,?,?)");
                ps.setString(1, SID);
                ps.setString(2, name);
                ps.setString(3, job);
                ps.setString(4, age);
                ps.setString(5, skin);
                ps.setString(6, variant.toString());
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
}