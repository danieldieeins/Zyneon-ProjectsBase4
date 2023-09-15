package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.api.WarpAPI;
import live.nerotv.projectsbase.modules.roleplay.manager.ItemManager;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.api.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static org.bukkit.Material.*;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if(e.getPlayer().getItemInHand().getItemMeta()!=null) {
            if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("angeltvlive")) {
                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setCustomModelData(2);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            } else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("angeltastico")) {
                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setCustomModelData(1);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(p.isOp()) {
            if(p.getInventory().getItemInMainHand().getType().equals(DEBUG_STICK)) {
                e.getRightClicked().remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        if(e.getPlayer().getItemInHand().getItemMeta()!=null) {
            if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("angeltvlive")) {
                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setCustomModelData(2);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            } else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("angeltastico")) {
                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.setCustomModelData(1);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            }
        }
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        Player p = e.getPlayer();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND) {
            e.setCancelled(true);
        }
        if (e.getItem() != null) {
            ItemStack Item = e.getItem();
            if (Item.getItemMeta() != null) {
                if (Item.getItemMeta().getDisplayName().equals(ItemManager.AcceptRules.getItemMeta().getDisplayName())) {
                    Item.setAmount(0);
                } else if (Item.getItemMeta().getDisplayName().equals(ItemManager.RulesPlaceHolder.getItemMeta().getDisplayName())) {
                    Item.setAmount(0);
                }
            }
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block b = e.getClickedBlock();
            if (b.getType().equals(OAK_WALL_SIGN) || b.getType().equals(OAK_SIGN)) {
                Sign sign = (Sign) b.getState();
                if (ChatColor.stripColor(sign.getLine(1)).toLowerCase().contains("zug nach")||ChatColor.stripColor(sign.getLine(1)).toLowerCase().contains("kutsche nach")) {
                    sign.setLine(1, "§8"+sign.getLine(1));
                    sign.setLine(2, "§e" + sign.getLine(2));
                    String name = ChatColor.stripColor(sign.getLine(2)).replace("§e", "");
                    if (WarpAPI.ifWarpExists(name)) {
                        p.teleport(WarpAPI.getWarp(name));
                        API.sendMessage(p, "Du bist nun in §e" + name + "§7.");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(e.getItemDrop().getItemStack().getItemMeta()!=null) {
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
                e.setCancelled(true);
            } else {
                ItemStack item = e.getItemDrop().getItemStack();
                ItemMeta itemMeta = item.getItemMeta();
                Material itemType = item.getType();
                String itemName = itemMeta.getDisplayName();
                if (itemType == PIG_SPAWN_EGG) {
                    if(itemName.contains("Chair")) {
                        itemMeta.setCustomModelData(0);
                        e.getItemDrop().getItemStack().setItemMeta(itemMeta);
                    }
                } else if(itemType == COW_SPAWN_EGG) {
                    if(itemName.contains("Stuhl1")) {
                        itemMeta.setCustomModelData(0);
                        e.getItemDrop().getItemStack().setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRename(PrepareAnvilEvent e) {
        if(e.getResult()!=null) {
            if(e.getResult().getItemMeta()!=null) {
                ItemStack item = e.getResult();
                ItemMeta itemMeta = item.getItemMeta();
                if(item.getType().equals(FISHING_ROD)) {
                    if(itemMeta.getDisplayName().toLowerCase().contains("angeltastico")) {
                        itemMeta.setCustomModelData(1);
                        item.setItemMeta(itemMeta);
                    } else if(itemMeta.getDisplayName().toLowerCase().contains("angeltvlive")) {
                        itemMeta.setCustomModelData(2);
                        item.setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickup(PlayerPickupItemEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(e.getItem().getItemStack().getItemMeta()!=null) {
            if(e.getItem().getItemStack().getItemMeta().getDisplayName().equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
                e.setCancelled(true);
            } else {
                ItemStack item = e.getItem().getItemStack();
                ItemMeta itemMeta = item.getItemMeta();
                Material itemType = item.getType();
                String itemName = itemMeta.getDisplayName();
                if(itemType==PIG_SPAWN_EGG) {
                    if(itemName.contains("WeaponStand")) {
                        itemMeta.setCustomModelData(11);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("Tent #1")) {
                        itemMeta.setCustomModelData(222);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("Tent #2")) {
                        itemMeta.setCustomModelData(3333);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("Tent #3")) {
                        itemMeta.setCustomModelData(44444);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("Table")) {
                        itemMeta.setCustomModelData(77777777);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("MailBox")) {
                        itemMeta.setCustomModelData(555555);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    } else if(itemName.contains("Chair")) {
                        itemMeta.setCustomModelData(6666666);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    }
                } else if(itemType==COW_SPAWN_EGG) {
                    if(itemName.contains("Stuhl1")) {
                        itemMeta.setCustomModelData(1);
                        e.getItem().getItemStack().setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerPortal(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            if(StaticManager.restrictedNether) {
                e.setCancelled(true);
            if(p.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                if (WarpAPI.isWarpEnabled("spawn")) {
                    p.teleport(WarpAPI.getWarp("spawn"));
                } else {
                    p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                }
            } else {
                    if (WarpAPI.isWarpEnabled("nether")) {
                        p.teleport(WarpAPI.getWarp("nether"));
                    } else {
                        p.teleport(Bukkit.getWorld("FWN").getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if(API.protectedPlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
            API.dispatchConsoleCommand("title "+e.getPlayer().getName()+" subtitle \"§8...§7warte bitte§8,§7 bis die Spawnprotection abgelaufen ist§8!\"");
            API.dispatchConsoleCommand("title "+e.getPlayer().getName()+" title \"§7Ladevorgang§8...\"");
        }
    }
}