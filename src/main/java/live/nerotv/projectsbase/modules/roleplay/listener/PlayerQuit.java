package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        API.protectedPlayers.remove(p);
        if(ServerAPI.isMaintenance()) {
            if(p.hasPermission("zyneon.bypassmaintenance")) {
                u.setLastLocation(p.getLocation());
                e.setQuitMessage("§8« §c" + p.getName());
            } else {
                e.setQuitMessage("");
            }
        } else {
            if(!(p.isInvisible())) {
                e.setQuitMessage("§8« §c" + p.getName());
            } else {
                e.setQuitMessage("");
            }
            u.setLastLocation(p.getLocation());
        }
        API.users.remove(p.getUniqueId());
        RoleplayAPI.roleplayUsers.remove(p.getUniqueId());
        System.gc();
    }
}