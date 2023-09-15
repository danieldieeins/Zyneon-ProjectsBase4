package live.nerotv.projectsbase.modules.roleplay.manager;

import com.zyneonstudios.api.paper.events.ZyneonChatEvent;
import com.zyneonstudios.api.waterfall.bungeebase.api.ConfigAPI;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ChatAPI;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.InventoryManager;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.SkinManager;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import net.skinsrestorer.api.SkinVariant;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ChatManager implements Listener {

    @EventHandler
    public void onChat(ZyneonChatEvent e) {
        String MSG = e.getMessage();
        Player p = e.getPlayer();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        if (!u.getChatType().equals("CHAT")) {
            if(MSG.equalsIgnoreCase("cancel")) {
                u.setChatType("CHAT");
                u.sendMessage("§7Eingabe abgebrochen§8.");
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if (u.getChatType().equals("CHAR_NAMECHANGE")) {
                if (ChatAPI.isStringBlocked(MSG) || ChatAPI.isNameBlocked(MSG)) {
                    p.sendMessage("§4Achtung:§c Dieser Name ist nicht zulässig§8! §8(Geblocktes Wort)");
                    p.sendMessage("§7Versuche es erneut§8...");
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    e.setCancelled(true);
                    return;
                }
                int length = MSG.split(" ").length;
                if (length > 3) {
                    p.sendMessage("§4Achtung:§c Dieser Name ist nicht zulässig§8! §8(Zu lang)");
                    p.sendMessage("§7Versuche es erneut§8...");
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    e.setCancelled(true);
                    return;
                }
                u.getActiveChar().setName(MSG);
                u.sendMessage("§7Dein §eName§7 wurde §aerfolgreich§7 auf §e\"" + MSG + "\"§7 gesetzt§8!");
                InventoryManager.openCharacterSettings(p);
                u.setChatType("CHAT");
            } else if (u.getChatType().equals("CHAR_SKINCHANGE")) {
                if (MSG.equalsIgnoreCase("SLIM")) {
                    u.getActiveChar().setVariant(SkinVariant.SLIM);
                    u.setChatType("CHAR_SKINCHANGE_NAME_SLIM");
                } else if (MSG.equalsIgnoreCase("CLASSIC")) {
                    u.getActiveChar().setVariant(SkinVariant.CLASSIC);
                    u.setChatType("CHAR_SKINCHANGE_NAME_CLASSIC");
                } else {
                    p.sendMessage("§4Achtung:§c Dieser Skin-Typ ist ungültig§8! §8(SLIM oder CLASSIC)");
                    p.sendMessage("§7Versuche es erneut§8...");
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    e.setCancelled(true);
                    return;
                }
                u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                u.sendMessage("§7Gebe den Namen deines neuen Outfits ein§8 (Das siehst nur du):");
            } else if (u.getChatType().equals("CHAR_SKINCHANGE_NAME_SLIM")) {
                if(ChatAPI.isStringBlocked(MSG)) {
                    p.sendMessage("§4Achtung:§c Dieser Skin§8-§cName ist nicht zulässig§8! §8(Geblocktes Wort)");
                    p.sendMessage("§7Versuche es erneut§8...");
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    e.setCancelled(true);
                    return;
                }
                u.getActiveChar().setOutfitName(MSG);
                u.setChatType("CHAR_SKINCHANGE_SLIM");
                u.sendErrorMessage("§eBeachte bitte§8, §edass die URL direkt zu der Skin-Datei führen muss§8,§e also auf §8.§epng enden sollte§8.");
                u.sendErrorMessage("§eBeispiel§8: §ehttps://skin.url/skin.png");
                u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                u.sendMessage("§7Gebe die URL die zu deinem Skin führt ein§8 (Das siehst nur du):");
            } else if (u.getChatType().equals("CHAR_SKINCHANGE_NAME_CLASSIC")) {
                if(ChatAPI.isStringBlocked(MSG)) {
                    p.sendMessage("§4Achtung:§c Dieser Skin§8-§cName ist nicht zulässig§8! §8(Geblocktes Wort)");
                    p.sendMessage("§7Versuche es erneut§8...");
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    e.setCancelled(true);
                    return;
                }
                u.getActiveChar().setOutfitName(MSG);
                u.setChatType("CHAR_SKINCHANGE_CLASSIC");
                u.sendErrorMessage("§eBeachte bitte§8, §edass die URL direkt zu der Skin-Datei führen muss§8,§e also auf §8.§epng enden sollte§8.");
                u.sendErrorMessage("§eBeispiel§8: §ehttps://skin.url/skin.png");
                u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                u.sendMessage("§7Gebe die URL die zu deinem Skin führt ein§8 (Das siehst nur du):");
            } else {
                boolean http = MSG.contains("http") && MSG.contains("://") && MSG.contains(".png");
                if (u.getChatType().equals("CHAR_SKINCHANGE_SLIM")) {
                    try {
                        SkinManager.setSkin(p, MSG, SkinVariant.SLIM);
                    } catch (NullPointerException x) {
                        p.sendMessage("§4Achtung:§c Diese URL ist ungültig§8.!");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    }
                    if(http) {
                        u.getActiveChar().setSkin(MSG);
                        u.sendMessage("§7Versuche deinen Skin zu ändern§8...");
                        InventoryManager.openCharacterSettings(p);
                        ArrayList<String> list = u.getActiveChar().getOutfits();
                        list.add(MSG+"__SKINVARIANTSLIM0012__"+"-=-OUTFITNAME="+u.getActiveChar().getOutfitName());
                        u.getActiveChar().setOutfits(list);
                        u.setChatType("CHAT");
                        e.setCancelled(true);
                    } else {
                        p.sendMessage("§4Achtung:§c Diese URL ist ungültig§8.!");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    }
                } else if (u.getChatType().equals("CHAR_SKINCHANGE_CLASSIC")) {
                    try {
                        SkinManager.setSkin(p, MSG, SkinVariant.CLASSIC);
                    } catch (NullPointerException x) {
                        p.sendMessage("§4Achtung:§c Diese URL ist ungültig§8.!");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    }
                    if(http) {
                        u.getActiveChar().setSkin(MSG);
                        u.sendMessage("§7Versuche deinen Skin zu ändern§8...");
                        ArrayList<String> list = u.getActiveChar().getOutfits();
                        list.add(MSG+"-=-OUTFITNAME="+u.getActiveChar().getOutfitName());
                        u.getActiveChar().setOutfits(list);
                        u.setChatType("CHAT");
                        e.setCancelled(true);
                    } else {
                        p.sendMessage("§4Achtung:§c Diese URL ist ungültig§8.!");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    }
                } else if (u.getChatType().equals("CHAR_JOBCHANGE")) {
                    if (ChatAPI.isStringBlocked(MSG) || ChatAPI.isJobBlocked(MSG)) {
                        p.sendMessage("§4Achtung:§c Dieser Job ist nicht zulässig§8! §8(Geblocktes Wort)");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                        return;
                    }
                    int length = MSG.split(" ").length;
                    if (length > 3) {
                        p.sendMessage("§4Achtung:§c Dieser Name ist nicht zulässig§8! §8(Zu lang)");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                        return;
                    }
                    u.getActiveChar().setJob(MSG);
                    u.sendMessage("§7Dein §eBeruf§7 wurde §aerfolgreich§7 auf §e\"" + MSG + "\"§7 gesetzt§8!");
                    InventoryManager.openCharacterSettings(p);
                    u.setChatType("CHAT");
                } else if (u.getChatType().equals("CHAR_AGECHANGE")) {
                    if (!ConfigAPI.isNumeric(MSG)) {
                        p.sendMessage("§4Achtung:§c Dieses Alter ist nicht zulässig§8! §8(Keine Zahl)");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                        return;
                    }
                    int age = Integer.parseInt(MSG);
                    if (age > 100 || age < 15) {
                        p.sendMessage("§4Achtung:§c Dieses Alter ist nicht zulässig§8! §8(Unrealistische Zahl)");
                        p.sendMessage("§7Versuche es erneut§8...");
                        p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                        return;
                    }
                    u.getActiveChar().setAge("" + age);
                    u.sendMessage("§7Dein §eAlter§7 wurde §aerfolgreich§7 auf §e\"" + MSG + "\"§7 gesetzt§8!");
                    InventoryManager.openCharacterSettings(p);
                    u.setChatType("CHAT");
                }
            }
        } else {
            MSG = MSG.replace("./", "§7/");
            String SID = p.getUniqueId().toString();
            e.setCancelled(true);
            if (ChatAPI.isStringBlocked(e.getMessage())) {
                p.sendMessage("§4Achtung:§c Achte auf deine Wortwahl, oder es wird eine Strafe mit sich führen.");
                p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 100);
                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 100, 100);
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
            } else {
                if (u.isRoleplay()) {
                    if (Characters.enableCharacters) {
                        if (u.getActiveChar() == null) {
                            u.sendErrorMessage("§cDu musst deine Charaktereinrichtung abschließen§8!");
                            InventoryManager.openCharactersMenu(p);
                            return;
                        } else {
                            Character c = u.getActiveChar();
                            if (c.getName() == null || c.getName().contains("§c")) {
                                API.sendErrorMessage(p, "§cBitte setze dir zuerst deinen Roleplaynamen mit §c/char und dann unten unter \"Charakter anpassen\".");
                            } else {
                                String Job;
                                if (c.getJob() == null || c.getJob().contains("§c")) {
                                    Job = "Arbeitslos";
                                } else {
                                    Job = c.getJob();
                                }
                                String RPM = "§6RP §8● §e" + Job + "§8 ● §6" + c.getName() + "§8 » §f" + MSG;
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (p.getLocation().getWorld().equals(all.getLocation().getWorld()) && p.getLocation().distance(all.getLocation()) <= 30) {
                                        all.sendMessage(RPM);
                                    }
                                }
                                Bukkit.getConsoleSender().sendMessage(RPM);
                            }
                        }
                    } else {
                        if (u.getRoleplayName() == null) {
                            API.sendErrorMessage(p, "§cBitte setze dir zuerst deinen Roleplaynamen mit §c/name [Vorname] §7[Zwischenname] &c[Nachname].");
                        } else {
                            String Job;
                            if (u.getRoleplayJob() == null) {
                                Job = "Arbeitslos";
                            } else {
                                Job = u.getRoleplayJob();
                            }
                            String RPM = "§6RP §8● §e" + Job + "§8 ● §6" + u.getRoleplayName() + "§8 » §f" + MSG;
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (p.getLocation().getWorld().equals(all.getLocation().getWorld()) && p.getLocation().distance(all.getLocation()) <= 30) {
                                    all.sendMessage(RPM);
                                }
                            }
                            Bukkit.getConsoleSender().sendMessage(RPM);
                        }
                    }
                } else {
                    String orp = "Off-RP";
                    if (p.getWorld().getName().equalsIgnoreCase(StaticManager.farmworldName)) {
                        orp = "Farmwelt";
                    } else if (p.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        orp = "Farmwelt";
                    } else if (p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                        if (StaticManager.restrictedNether) {
                            orp = "Farmwelt";
                        }
                    }
                    String Name;
                    if (API.hasINF(p)) {
                        Name = "§l" + p.getName();
                    } else {
                        Name = p.getName();
                    }
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (RoleplayAPI.getRoleplayUser(all).isRoleplay()) {
                            all.sendMessage("§8" + orp + " ● " + API.getPrefix(p) + " ● " + Name + " » " + MSG);
                        } else {
                            all.sendMessage("§7" + orp + " §8● §e" + API.getPrefix(p) + " §8● §6" + Name + "§8 » §7" + MSG);
                        }
                    }
                    Bukkit.getServer().getConsoleSender().sendMessage("§7" + orp + " §8● §e" + API.getPrefix(p) + " §8● §6" + Name + "§8 » §7" + MSG);
                }
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                RoleplayMain.setState(all);
            }
        }
    }
}