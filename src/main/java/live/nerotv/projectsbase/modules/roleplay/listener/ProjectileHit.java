package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit implements Listener {

    @EventHandler
    public void onProjectile(ProjectileHitEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        if(StaticManager.projectileGlassBreak) {
            try {
                if(e.getEntity().getType().equals(EntityType.ARROW) || e.getEntity().getType().equals(EntityType.SPECTRAL_ARROW)) {
                    if(e.getEntity().getShooter() instanceof Player) {
                        if(e.getHitBlock() != null) {
                            if(e.getHitBlock().getType().toString().toLowerCase().contains("glass")) {
                                e.getHitBlock().breakNaturally();
                                e.getHitBlock().getWorld().spawnParticle(Particle.BLOCK_CRACK,e.getHitBlock().getLocation(),1);
                            }
                        }
                    }
                }
            } catch(IllegalArgumentException ignored) {}
        }
    }
}