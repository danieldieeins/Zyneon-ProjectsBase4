package live.nerotv.projectsbase.modules.lock.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.lock.api.LockAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class HorseTameEvent implements Listener {
    
    @EventHandler
    public void onTame(EntityTameEvent e) {
        if(e.getOwner() instanceof Player) {
            Player p = (Player)e.getOwner();
            if(e.getEntity().getType().equals(EntityType.HORSE)||e.getEntity().getType().equals(EntityType.SKELETON_HORSE)||e.getEntity().getType().equals(EntityType.ZOMBIE_HORSE)||e.getEntity().getType().equals(EntityType.DONKEY)||e.getEntity().getType().equals(EntityType.MULE)) {
                LockAPI.setOwner(e.getEntity().getUniqueId(),p.getUniqueId());
                API.sendMessage(p,"§7Du hast das Entity §aerfolgreich§7 gesichert§8!");
            }
        }
    }
}