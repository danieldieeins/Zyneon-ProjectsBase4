package live.nerotv.projectsbase.modules.lock.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.lock.api.LockAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.popcraft.lwctrust.LWCTrust;

import java.util.List;
import java.util.UUID;

public class HorseAccessEvent implements Listener {

    @EventHandler
    public void onAccess(PlayerInteractEntityEvent e) {
        if(LockAPI.isLocked(e.getRightClicked().getUniqueId())) {
            if(!checkAccess(e.getPlayer(),e.getRightClicked().getUniqueId())) {
                API.sendErrorMessage(e.getPlayer(),"§cDas gehört dir nicht§8!");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAccess(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            if (LockAPI.isLocked(e.getEntity().getUniqueId())) {
                if (!checkAccess(p, e.getEntity().getUniqueId())) {
                    API.sendErrorMessage(p, "§cDas gehört dir nicht§8!");
                    e.setCancelled(true);
                }
            }
        }
    }

    private boolean checkAccess(Player player, UUID horse) {
        if(player.hasPermission("ZySys.Locks.Bypass")) {
            return true;
        }
        if(!LockAPI.getOwner(horse).getUniqueId().equals(player.getUniqueId())) {
            UUID owner = LockAPI.getOwner(horse).getUniqueId();
            List<UUID> trusted = LWCTrust.getInstance().getTrustCache().load(owner);
            return trusted.contains(player.getUniqueId());
        } else {
            return true;
        }
    }
}