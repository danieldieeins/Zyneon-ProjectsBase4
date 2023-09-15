package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.*;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.*;
import live.nerotv.projectsbase.modules.essentials.manager.CustomItemManager;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventory implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        Player p = (Player) e.getWhoClicked();
        User user = API.getUser(p);
        RoleplayUser roleplayUser = RoleplayAPI.getRoleplayUser(p);
        if (e.getClickedInventory() != null) {
            Inventory inv = e.getClickedInventory();
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    ItemStack Item = e.getCurrentItem();
                    ItemMeta ItemMeta = Item.getItemMeta();
                    String ItemName = ItemMeta.getDisplayName();
                    ItemMeta.setDisplayName(ItemName);
                    Item.setItemMeta(ItemMeta);
                    if (ItemName.equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                    } else if (ItemName.equals("§bSpawn")) {
                        e.setCancelled(true);
                        if (WarpAPI.isWarpEnabled("Spawn")) {
                            p.teleport(WarpAPI.getWarp("Spawn"));
                        } else {
                            p.teleport(Bukkit.getWorld("Welt").getSpawnLocation());
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                    } else if (ItemName.equals("§eFarmwelt")) {
                        e.setCancelled(true);
                        if (StaticManager.farmworld) {
                            if (WarpAPI.isWarpEnabled("Farmwelt")) {
                                p.teleport(WarpAPI.getWarp("Farmwelt"));
                            } else {
                                p.teleport(Bukkit.getWorld(ConfigAPI.CFG.getString("Core.Settings.Farmwelt.Name")).getSpawnLocation());
                            }
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        } else {
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        }
                    } else if(ItemName.equals("§4Nether §7und §eEnd")) {
                        e.setCancelled(true);
                        p.teleport(WarpAPI.getWarp("netherundend"));
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                    } else if (ItemName.equals("§cNether")) {
                        e.setCancelled(true);
                        p.teleport(WarpAPI.getWarp("warp_06"));
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                    } else if (ItemName.equals(ItemManager.Placeholder.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    } else if (ItemName.equals(ItemManager.ColorBack.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals(ItemManager.Close.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 100, 100);
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.ElytraReward.getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 15000)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.ELYTRA);
                                ItemMeta RewardMeta = Reward.getItemMeta();
                                RewardMeta.setDisplayName("Elytren");
                                RewardMeta.setUnbreakable(true);
                                Reward.setItemMeta(RewardMeta);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 15000);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendErrorMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.blackRevolverReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 10000)) {
                            if (API.hasAvailableSlot(p)) {
                                p.getInventory().addItem(CustomItemManager.BLACK_REVOLVER);
                                roleplayUser.setAdventurePoints(Money - 10000);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendErrorMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.shotgunReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 40000)) {
                            if (API.hasAvailableSlot(p)) {
                                p.getInventory().addItem(CustomItemManager.GOLDEN_SHOTGUN);
                                roleplayUser.setAdventurePoints(Money - 40000);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendErrorMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.marksmanReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 80000)) {
                            if (API.hasAvailableSlot(p)) {
                                p.getInventory().addItem(CustomItemManager.GOLDEN_MARKSMAN_PISTOL);
                                roleplayUser.setAdventurePoints(Money - 80000);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendErrorMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.RevolverReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 25000)) {
                            if (API.hasAvailableSlot(p)) {
                                p.getInventory().addItem(CustomItemManager.GOLDEN_REVOLVER);
                                roleplayUser.setAdventurePoints(Money - 25000);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendErrorMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.BottleReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 500)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.EXPERIENCE_BOTTLE);
                                Reward.setAmount(32);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 500);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.CoalReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 500)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.COAL);
                                Reward.setAmount(32);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 500);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.GoldReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 500)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.GOLD_INGOT);
                                Reward.setAmount(8);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 500);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.IronReward().getItemMeta().getDisplayName())) {
                        int Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 600)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.IRON_INGOT);
                                Reward.setAmount(8);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 600);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.LapisReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 700)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.LAPIS_LAZULI);
                                Reward.setAmount(16);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 700);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.DiamondReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 900)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.DIAMOND);
                                Reward.setAmount(2);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 900);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.NetherReward.getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 2500)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.NETHERITE_INGOT);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 2500);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.RocketReward().getItemMeta().getDisplayName())) {
                        Integer Money = roleplayUser.getAdventurePoints();
                        if (PointsManager.checkPayment(Money, 100)) {
                            if (API.hasAvailableSlot(p)) {
                                ItemStack Reward = new ItemStack(Material.FIREWORK_ROCKET);
                                Reward.setAmount(64);
                                p.getInventory().addItem(Reward);
                                roleplayUser.setAdventurePoints(Money - 100);
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            } else {
                                API.sendErrorMessage(p, "§cDu hast nicht genug Platz im Inventar!");
                            }
                        } else {
                            API.sendMessage(p, API.noMoney);
                        }
                        e.setCancelled(true);
                    } else if (ItemName.equals("§cSpawnschutzzeit §8(0 Sekunden)")) {
                        e.setCancelled(true);
                        roleplayUser.setProtectionTime(30);
                        roleplayUser.sendRawMessage(API.PN() + "§7Du hast die Zeit deiner §eSpawnprotection§c §7auf §a30 Sekunden §7gesetzt§8!");
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals("§eSpawnschutzzeit §8(15 Sekunden)")) {
                        e.setCancelled(true);
                        roleplayUser.setProtectionTime(0);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        roleplayUser.sendRawMessage(API.PN() + "§7Du hast die Zeit deiner §eSpawnprotection§c §7auf §c0 Sekunden §7gesetzt§8!");
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals("§aSpawnschutzzeit §8(30 Sekunden)")) {
                        e.setCancelled(true);
                        roleplayUser.setProtectionTime(15);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        roleplayUser.sendRawMessage(API.PN() + "§7Du hast die Zeit deiner §aSpawnprotection§c §7auf §e15 Sekunden §7gesetzt§8!");
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals("§aAutomatisches Roleplay")) {
                        e.setCancelled(true);
                        roleplayUser.setAutomaticRoleplay(false);
                        roleplayUser.sendRawMessage(API.PN() + "§7Du hast §cdeaktiviert§8, §7dass du beim Betreten des Servers automatisch in den §eRoleplay-Modus§7 gesetzt wirst§8!");
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals("§cAutomatisches Roleplay")) {
                        e.setCancelled(true);
                        roleplayUser.setAutomaticRoleplay(true);
                        roleplayUser.sendRawMessage(API.PN() + "§7Du hast §aaktiviert§8, §7dass du beim Betreten des Servers automatisch in den §eRoleplay-Modus§7 gesetzt wirst§8!");
                        InventoryManager.openSettingsInventory(p);
                    } else if (ItemName.equals("§cPlatzhalter§4")) {
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.WarpBack.getItemMeta().getDisplayName())) {
                        InventoryManager.openInv002(p);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.WarpC1.getItemMeta().getDisplayName())) {
                        InventoryManager.openInv_WarpC1(p);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.WarpC2.getItemMeta().getDisplayName())) {
                        InventoryManager.openInv_WarpC2(p);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        e.setCancelled(true);
                    } else if (ItemName.equals(ItemManager.WarpETC.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        InventoryManager.openInv_WarpETC(p);
                    } else if (ItemName.equals(ItemManager.WarpC101.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub1Menu01")));
                    } else if (ItemName.equals(ItemManager.WarpC102.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub1Menu02")));
                    } else if (ItemName.equals(ItemManager.WarpC201.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub2Menu01")));
                    } else if (ItemName.equals(ItemManager.WarpC202.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub2Menu02")));
                    } else if (ItemName.equals(ItemManager.WarpETC01.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub3Menu01")));
                    } else if (ItemName.equals(ItemManager.WarpETC02.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.teleport(WarpAPI.getWarp(ConfigAPI.CFG.getString("Core.WarpGUI.Warps.Sub3Menu02")));
                    } else if (ItemName.equals(ItemManager.Cancel.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.StopYes.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (p.hasPermission("zyneon.leading.stop")) {
                            ServerAPI.scheduledShutdown();
                            API.sendMessage(p, "§7Du hast den §eStopvorgang§7 gestartet§8.");
                        } else {
                            API.sendErrorMessage(p, API.noPerms);
                        }
                    } else if (ItemName.equals(ItemManager.RulesPlaceHolder.getItemMeta().getDisplayName())) {
                        if (e.getClickedInventory().equals(InventoryManager.confirmRulesInventory)) {
                            e.setCancelled(true);
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                        } else if (e.getClickedInventory().equals(p.getInventory())) {
                            e.getCurrentItem().setAmount(0);
                        }
                    } else if (ItemName.equals(ItemManager.AcceptRules.getItemMeta().getDisplayName())) {
                        if (e.getClickedInventory().equals(InventoryManager.confirmRulesInventory)) {
                            e.setCancelled(true);
                            p.closeInventory();
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
                        } else if (e.getClickedInventory().equals(p.getInventory())) {
                            e.getCurrentItem().setAmount(0);
                        }
                    } else if (ItemName.equals(ItemManager.GameModeAdventure.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.performCommand("gamemode 2");
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.GameModeCreative.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.performCommand("gamemode 1");
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.GameModeSpectator.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.performCommand("gamemode 3");
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.GameModeSurvival.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.performCommand("gamemode 0");
                        p.closeInventory();
                    } else if (ItemName.equals(ItemManager.ReloadYes.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if (p.hasPermission("zyneon.team")) {
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                API.switchServer(all, "Lobby-1");
                            }

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");
                        } else {
                            API.sendErrorMessage(p, API.noPerms);
                        }
                    } else if(Item.getItemMeta().hasLore()) {
                        if (ItemName.equals(ItemManager.RewardMark001.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 2)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(1);
                                    roleplayUser.setAdventurePoints(Money - 2);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark001);
                                        roleplayUser.setAdventurePoints(Money - 2);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark002.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 4)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(2);
                                    roleplayUser.setAdventurePoints(Money - 4);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark002);
                                        roleplayUser.setAdventurePoints(Money - 4);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark005.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 10)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(5);
                                    roleplayUser.setAdventurePoints(Money - 10);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark005);
                                        roleplayUser.setAdventurePoints(Money - 10);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark010.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 20)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(10);
                                    roleplayUser.setAdventurePoints(Money - 20);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark010);
                                        roleplayUser.setAdventurePoints(Money - 20);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark020.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 40)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(20);
                                    roleplayUser.setAdventurePoints(Money - 40);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark020);
                                        roleplayUser.setAdventurePoints(Money - 40);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark050.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 100)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(50);
                                    roleplayUser.setAdventurePoints(Money - 100);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark050);
                                        roleplayUser.setAdventurePoints(Money - 100);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark100.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 200)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(100);
                                    roleplayUser.setAdventurePoints(Money - 200);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark100);
                                        roleplayUser.setAdventurePoints(Money - 200);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark200.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 400)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(200);
                                    roleplayUser.setAdventurePoints(Money - 400);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark200);
                                        roleplayUser.setAdventurePoints(Money - 400);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        } else if (ItemName.equals(ItemManager.RewardMark500.getItemMeta().getDisplayName())) {
                            Integer Money = roleplayUser.getAdventurePoints();
                            if (PointsManager.checkPayment(Money, 1000)) {
                                if(StaticManager.ecoModule) {
                                    user.getEconomyUser().addBalance(500);
                                    roleplayUser.setAdventurePoints(Money - 1000);
                                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                } else {
                                    if(API.hasAvailableSlot(p)) {
                                        p.getInventory().addItem(ItemManager.Mark500);
                                        roleplayUser.setAdventurePoints(Money - 1000);
                                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                    } else {
                                        API.sendErrorMessage(p,"§cDu hast nicht genug Platz im Inventar!");
                                    }
                                }
                            } else {
                                API.sendMessage(p, API.noMoney);
                            }
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventory(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            p.closeInventory();
            return;
        }
        Inventory pInv = p.getInventory();
        Inventory eInv = e.getInventory();
        if (eInv.getType().equals(InventoryType.CHEST)) {
            if (!e.getView().getTitle().contains("Chest") || e.getView().getTitle().equalsIgnoreCase("Kiste") || e.getView().getTitle().equalsIgnoreCase("Truhe") || e.getView().getTitle().equalsIgnoreCase("") || e.getView().getTitle().equalsIgnoreCase(" ") || e.getView().getTitle().equalsIgnoreCase("Item Hopper")) {
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 80, 80);
            }
        }
        if(e.getInventory().getSize()>48) {
            if(e.getInventory().getItem(47)!=null) {
                ItemStack item = e.getInventory().getItem(47);
                if(item.getItemMeta()!=null) {
                    if(item.getItemMeta().hasLore()) {
                        if(item.getItemMeta().getLore().get(0).contains("1 Erfolg")) {
                            e.getInventory().setItem(47,null);
                        }
                    }
                }
            }
            if(e.getInventory().getItem(48)!=null) {
                ItemStack item = e.getInventory().getItem(48);
                if(item.getItemMeta()!=null) {
                    if(item.getItemMeta().hasLore()) {
                        if(item.getItemMeta().getLore().get(0).contains("1 Erfolg")) {
                            e.getInventory().setItem(48,null);
                        }
                    }
                }
            }
        }
    }
}