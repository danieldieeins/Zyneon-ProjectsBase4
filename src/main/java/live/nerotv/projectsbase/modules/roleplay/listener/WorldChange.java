package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class WorldChange implements Listener {

    World mainWorld = Bukkit.getWorlds().get(0);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplosion(EntityExplodeEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(e.getEntity().getWorld().getName().equals(mainWorld.getName())) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockExplosion(BlockExplodeEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(e.getBlock().getWorld().getName().equals(mainWorld.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosion(ExplosionPrimeEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntity().getWorld().getName().equals(mainWorld.getName())) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }
}