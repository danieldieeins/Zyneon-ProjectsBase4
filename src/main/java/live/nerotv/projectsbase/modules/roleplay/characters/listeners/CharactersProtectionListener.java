package live.nerotv.projectsbase.modules.roleplay.characters.listeners;

import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CharactersProtectionListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(Characters.locked.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerInteractEvent e) {
        if(Characters.locked.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerInteractAtEntityEvent e) {
        if(Characters.locked.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if (Characters.locked.contains((Player)e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
}