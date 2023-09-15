package live.nerotv.projectsbase.modules.roleplay.characters.listeners;

import live.nerotv.projectsbase.Main;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.InventoryManager;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.ItemManager;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.SkinManager;
import live.nerotv.projectsbase.modules.roleplay.characters.objects.Character;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.Countdown;
import net.skinsrestorer.api.SkinVariant;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.UUID;

public class CharactersInventoryListener implements Listener {

    private final ArrayList<UUID> cooldownChange = new ArrayList<>();
    private final ArrayList<UUID> cooldownCreate = new ArrayList<>();

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player)e.getWhoClicked();
            if(e.getView().getTitle().contains("Charakter")||e.getView().getTitle().contains("Outfits")) {
                e.setCancelled(true);
            }
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    ItemStack item = e.getCurrentItem();
                    ItemMeta itemMeta = item.getItemMeta();
                    String itemName = itemMeta.getDisplayName();
                    RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
                    if (u.getActiveChar()!=null) {
                        if (itemName.contains(ItemManager.skinVariant(u.getActiveChar()).getItemMeta().getDisplayName())) {
                            if (cooldownCreate.contains(p.getUniqueId())) {
                                p.closeInventory();
                                u.sendErrorMessage("§4Fehler§8: §cWarte bitte etwas, bis du erneut deinen Charaktertyp wechselst§8.");
                            } else {
                                if (!p.hasPermission("zyneon.team")) {
                                    cooldownCreate.add(p.getUniqueId());
                                    new Countdown(15, Main.getInstance()) {
                                        @Override
                                        public void count(int current) {
                                            if (current == 0) {
                                                cooldownCreate.remove(p.getUniqueId());
                                            }
                                        }
                                    }.start();
                                }

                                e.setCancelled(true);
                                if (u.getActiveChar().getVariant().equals(SkinVariant.SLIM)) {
                                    u.getActiveChar().setVariant(SkinVariant.CLASSIC);
                                } else {
                                    u.getActiveChar().setVariant(SkinVariant.SLIM);
                                }
                                try {
                                    SkinManager.setSkin(p, u.getActiveChar().getSkin(), u.getActiveChar().getVariant());
                                } catch (NullPointerException ignore) {}
                                InventoryManager.openOutfitsInventory(p);
                                return;
                            }
                        }
                    }
                    if (itemName.contains(ItemManager.changeName(p).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        u.setChatType("CHAR_NAMECHANGE");
                        p.closeInventory();
                        u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                        u.sendMessage("§7Gebe deinen neuen Namen in den Chat ein §8(Das siehst nur du):");
                    } else if (itemName.contains(ItemManager.changeJob(p).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        u.setChatType("CHAR_JOBCHANGE");
                        p.closeInventory();
                        u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                        u.sendMessage("§7Gebe deinen neuen Beruf in den Chat ein §8(Das siehst nur du):");
                    } else if (itemName.contains(ItemManager.changeAge(p).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        u.setChatType("CHAR_AGECHANGE");
                        p.closeInventory();
                        u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                        u.sendMessage("§7Gebe dein neues Alter in den Chat ein §8(Das siehst nur du):");
                    } else if (itemName.contains(ItemManager.changeSkin(p).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        u.setChatType("CHAR_SKINCHANGE");
                        p.closeInventory();
                        u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                        u.sendMessage("§7Schreibe für dünne Arme §fSLIM§7 in den Chat und für breite Arme §fCLASSIC §8(Das siehst nur du):");
                    } else if (itemName.contains(ItemManager.closeMenu().getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_CLOSE, 100, 100);
                    } else if (itemName.contains(ItemManager.characterSettings(u).getItemMeta().getDisplayName())) {
                        if (u.getActiveChar() != null) {
                            e.setCancelled(true);
                            InventoryManager.openCharacterSettings(p);
                        } else {
                            e.setCancelled(true);
                            p.closeInventory();
                            u.sendErrorMessage("§4Fehler: §cWähle dazu erst einen Charakter aus§8!");
                        }
                    } else if (itemName.contains(ItemManager.outfitMenu().getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        InventoryManager.openOutfitsInventory(p);
                    } else if (itemName.contains(ItemManager.characters2().getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        InventoryManager.openCharacterSettings(p);
                    } else if (itemName.contains(ItemManager.addOutfit().getItemMeta().getDisplayName())) {
                        if (cooldownCreate.contains(p.getUniqueId())) {
                            p.closeInventory();
                            u.sendErrorMessage("§4Fehler§8: §cWarte bitte etwas, bis du erneut ein weiteres Outfit erstellst§8.");
                        } else {
                            if (!p.hasPermission("zyneon.team")) {
                                cooldownCreate.add(p.getUniqueId());
                                new Countdown(45, Main.getInstance()) {
                                    @Override
                                    public void count(int current) {
                                        if (current == 0) {
                                            cooldownCreate.remove(p.getUniqueId());
                                        }
                                    }
                                }.start();
                            }
                        /*if(Characters.getOutfits(u.getActiveChar().getUUID()).size()>1) {
                            if(!p.hasPermission("zyneon.premium")) {
                                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                                p.sendMessage("§cAchtung§8! §7Um mehr Outfits speichern zu können§8, §7musst du den §6Premium§8-§7Rang §7erwerben§8.");
                                e.setCancelled(true);
                                return;
                            }
                        }*/
                            e.setCancelled(true);
                            u.setChatType("CHAR_SKINCHANGE");
                            p.closeInventory();
                            u.sendMessage("§7Gibt zum Abbrechen §ccancel§7 ein§8.");
                            u.sendMessage("§7Schreibe für dünne Arme §fSLIM§7 in den Chat und für breite Arme §fCLASSIC §8(Das siehst nur du):");
                        }
                    } else if (itemName.contains(ItemManager.description(p).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    } else if (itemName.contains(ItemManager.characters().getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        InventoryManager.openCharactersMenu(p);
                    } else if (itemName.contains(ItemManager.createCharacter().getItemMeta().getDisplayName())) {
                        /*if(u.getChars().size()>1) {
                            if(!p.hasPermission("zyneon.premium")) {
                                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                                p.sendMessage("§cAchtung§8! §7Um mehr Charaktere speichern zu können§8, §7musst du den §6Premium§8-§7Rang §7erwerben§8.");
                                e.setCancelled(true);
                                return;
                            }
                        }*/
                        if (cooldownCreate.contains(p.getUniqueId())) {
                            p.closeInventory();
                            u.sendErrorMessage("§4Fehler§8: §cWarte bitte etwas, bis du erneut einen Charakter erstellst§8.");
                        } else {
                            if(!p.hasPermission("zyneon.team")) {
                                cooldownCreate.add(p.getUniqueId());
                                new Countdown(45, Main.getInstance()) {
                                    @Override
                                    public void count(int current) {
                                        if (current == 0) {
                                            cooldownCreate.remove(p.getUniqueId());
                                        }
                                    }
                                }.start();
                                cooldownChange.add(p.getUniqueId());
                                new Countdown(15, Main.getInstance()) {
                                    @Override
                                    public void count(int current) {
                                        if (current == 0) {
                                            cooldownChange.remove(p.getUniqueId());
                                        }
                                    }
                                }.start();
                            }
                            e.setCancelled(true);
                            Characters.createCharacter(p.getUniqueId());
                            p.closeInventory();
                        }
                    } else if (e.getView().getTitle().contains("Outfits")) {
                        e.setCancelled(true);
                        if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                            if(!itemName.contains(ItemManager.addOutfit().getItemMeta().getDisplayName())) {
                                if (cooldownCreate.contains(p.getUniqueId())) {
                                    p.closeInventory();
                                    u.sendErrorMessage("§4Fehler§8: §cWarte bitte etwas, bis du erneut ein Outfit änderst§8.");
                                } else {
                                    if (!p.hasPermission("zyneon.team")) {
                                        cooldownCreate.add(p.getUniqueId());
                                        new Countdown(45, Main.getInstance()) {
                                            @Override
                                            public void count(int current) {
                                                if (current == 0) {
                                                    cooldownCreate.remove(p.getUniqueId());
                                                }
                                            }
                                        }.start();
                                        cooldownChange.add(p.getUniqueId());
                                        new Countdown(15, Main.getInstance()) {
                                            @Override
                                            public void count(int current) {
                                                if (current == 0) {
                                                    cooldownChange.remove(p.getUniqueId());
                                                }
                                            }
                                        }.start();
                                    }
                                    e.setCancelled(true);
                                    InventoryManager.openCharactersMenu(p);
                                    ArrayList<String> list = u.getActiveChar().getOutfits();
                                    if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                                        list.remove(e.getSlot());
                                        u.getActiveChar().setOutfits(list);
                                        InventoryManager.openOutfitsInventory(p);
                                        return;
                                    }
                                    String outfit = list.get(e.getSlot());
                                    String name = outfit.substring(outfit.indexOf("-=-OUTFITNAME=")).replace("-=-OUTFITNAME=", "");
                                    outfit = outfit.replace(name, "").replace("-=-OUTFITNAME=", "");
                                    SkinVariant variant;
                                    if (outfit.contains("__SKINVARIANTSLIM0012__")) {
                                        variant = SkinVariant.SLIM;
                                    } else {
                                        variant = SkinVariant.CLASSIC;
                                    }
                                    outfit = outfit.replace("__SKINVARIANTSLIM0012__", "");
                                    u.getActiveChar().setSkin(outfit);
                                    try {
                                        SkinManager.setSkin(p, outfit, variant);
                                    } catch (NullPointerException ignore) {}
                                    API.sendMessage(p, "§7Du hast dein §eOutfit§a erfolgreich§7 geändert§8!");
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else if (e.getView().getTitle().contains("Charakterauswahl")) {
                        e.setCancelled(true);
                        if (e.getCurrentItem().getItemMeta().hasLore() && e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                            if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
                                InventoryManager.openDeleteCharacterMenu(p,new Character(UUID.fromString(itemMeta.getLore().get(0).replace("§8", ""))));
                                return;
                            }
                            if(u.getActiveChar().getUUID().equals(UUID.fromString(itemMeta.getLore().get(0).replace("§8", "")))) {
                                if (u.getActiveChar() != null) {
                                    e.setCancelled(true);
                                    InventoryManager.openCharacterSettings(p);
                                    return;
                                }
                            }
                            if (cooldownChange.contains(p.getUniqueId())) {
                                p.closeInventory();
                                u.sendErrorMessage("§4Fehler§8: §cWarte bitte etwas, bis du erneut deinen Charakter wechselst§8.");
                                return;
                            } else {
                                if (!p.hasPermission("zyneon.team")) {
                                    cooldownChange.add(p.getUniqueId());
                                    new Countdown(15, Main.getInstance()) {
                                        @Override
                                        public void count(int current) {
                                            if (current == 0) {
                                                cooldownChange.remove(p.getUniqueId());
                                            }
                                        }
                                    }.start();
                                }
                            }
                            p.closeInventory();
                            u.setActiveChar(new Character(UUID.fromString(itemMeta.getLore().get(0).replace("§8", ""))));
                            u.sendMessage("§7Du hast erfolgreich §e" + u.getActiveChar().getName() + "§7 als deinen §faktiven Charakter§7 ausgewählt§8!");
                            p.playSound(p.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,100,100);
                        }
                    } else if (e.getView().getTitle().contains(" löschen")) {
                        if(itemMeta.hasLore()) {
                            e.setCancelled(true);
                            Character c = new Character(UUID.fromString(itemMeta.getLore().get(0).replace("§8", "")));
                            if (itemName.contains(ItemManager.deleteChar(c,c.getUUID().toString()).getItemMeta().getDisplayName())) {
                                Characters.deleteCharacter(p.getUniqueId(),c.getUUID());
                                InventoryManager.openCharactersMenu(p);
                            }
                        }
                    }
                }
            }
        }
    }
}