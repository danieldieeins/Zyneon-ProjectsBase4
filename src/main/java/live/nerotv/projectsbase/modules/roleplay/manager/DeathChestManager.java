package live.nerotv.projectsbase.modules.roleplay.manager;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import static live.nerotv.projectsbase.modules.essentials.manager.StaticManager.deathChest;

public class DeathChestManager implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Location l = p.getLocation();
        String ls = "§4 X§c" + l.getBlockX() + "§4 Y§c" + l.getBlockY() + "§4 Z§c" + l.getBlockZ() + " §8(§7" + l.getWorld().getName() + "§8) §7";
        if (deathChest) {
            //DEATHCHEST
        } else {
            if (e.getDrops().size() == 0) {
                API.sendErrorMessage(p,"§7Du bist bei" + ls + "§cgestorben§8! §7Da du aber keine Items im Inventar gehabt hast§8,§7 ist dies nicht so wichtig§8!");
            } else {
                API.sendErrorMessage(p,"§7Du bist bei" + ls + "§cgestorben§8! §7Deine Items liegen frei herum und verschwinden bald. §eBeeil dich§8!");
            }
        }
    }

    @EventHandler
    public void onChest(PlayerInteractEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(deathChest) {
            //DEATHCHEST
        }
    }

    @EventHandler
    public void onChestDestroy(BlockBreakEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(deathChest) {
            //DEATHCHEST
        }
    }
}