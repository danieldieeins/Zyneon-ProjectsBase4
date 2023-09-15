package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if(e.getEntity() instanceof ArmorStand) {
            ArmorStand a = (ArmorStand)e.getEntity();
            a.setGravity(true);
            a.setArms(true);
        }
        if(!RoleplayAPI.isStarted) {
            if(!(e.getEntity() instanceof Player)) {
                e.setCancelled(true);
            }
            return;
        }
        if(e.getEntity().getWorld().equals(Bukkit.getWorlds().get(0))) {
        if(e.getEntityType().equals(EntityType.CREEPER)||e.getEntityType().equals(EntityType.PHANTOM)||e.getEntityType().equals(EntityType.HUSK)||e.getEntityType().equals(EntityType.SPIDER)||e.getEntityType().equals(EntityType.SKELETON)) {
                e.setCancelled(true);
            }
        }
    }
}