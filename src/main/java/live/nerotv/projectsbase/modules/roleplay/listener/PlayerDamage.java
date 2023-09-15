package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.BOW;
import static org.bukkit.Material.CROSSBOW;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player p = e.getEntity();
        Location l = p.getLocation();
        String ls = "§4 X§c" + l.getBlockX() + "§4 Y§c" + l.getBlockY() + "§4 Z§c" + l.getBlockZ() + " §8(§7" + l.getWorld().getName() + "§8) §7";
        Bukkit.getConsoleSender().sendMessage("§4Zyneon §8| §c" + p.getName() + "§7 starb bei" + ls);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if (StaticManager.projectileGlassBreak) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player)e.getEntity();
                Entity projectile = e.getEntity();
                if (p.getItemInHand().getType().equals(CROSSBOW)) {
                    ItemStack gun = p.getItemInHand();
                    ItemMeta gunMeta = gun.getItemMeta();
                    if (gunMeta.hasCustomModelData()) {
                        if (gunMeta.getCustomModelData() == 1 || gunMeta.getCustomModelData() == 3) {
                            projectile.setCustomName("SHOTGUN");
                        } else if (gunMeta.getCustomModelData() == 4 || gunMeta.getCustomModelData() == 5) {
                            projectile.setCustomName("MARKSMAN");
                        } else {
                            projectile.setCustomName("REVOLVER");
                        }
                    } else {
                        projectile.setCustomName("REVOLVER");
                    }
                } else if (p.getItemInHand().getType().equals(BOW)) {
                    ItemStack gun = p.getItemInHand();
                    ItemMeta gunMeta = gun.getItemMeta();
                    projectile.setCustomName("SLINGSHOT");
                }
            }
        }
    }

    @EventHandler
    public void onProjectile(ProjectileHitEvent e) {
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if (StaticManager.projectileGlassBreak) {
            if (e.getHitEntity() != null) {
                if (e.getHitEntity() instanceof Player) {
                    Player t = (Player) e.getHitEntity();
                    if (e.getEntity().getCustomName() != null) {
                        if (e.getEntity().getCustomName().equalsIgnoreCase("SHOTGUN")) {
                            t.damage(11);
                        } else if (e.getEntity().getCustomName().equalsIgnoreCase("MARKSMAN")) {
                            if(t.getInventory().getChestplate()!=null) {
                                t.damage(13);
                            } else {
                                t.damage(25);
                            }
                        } else if (e.getEntity().getCustomName().equalsIgnoreCase("REVOLVER")) {
                            t.damage(6);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity().getCustomName()!=null){
             if(e.getEntity().getCustomName().equalsIgnoreCase("§0")) {
                 e.setCancelled(true);
             }
        }
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (API.protectedPlayers.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (API.protectedPlayers.contains(p)) {
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (API.protectedPlayers.contains(p)) {
                e.setCancelled(true);
            }
        }
    }
}