package live.nerotv.projectsbase.modules.sit.listener;

import live.nerotv.projectsbase.modules.sit.api.SitAPI;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class SitDismount implements Listener {

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if(e.getDismounted() instanceof ArmorStand) {
                ArmorStand chair = (ArmorStand)e.getDismounted();
                chair.eject();
                chair.remove();
                p.teleport(p.getLocation().add(0,1,0));
                SitAPI.sittingPlayers.remove(p);
            }
        }
    }
}