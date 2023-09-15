package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.modules.roleplay.RoleplayMain;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        User u = API.getUser(p);
        RoleplayUser rU = u.getRoleplayUser();
        World w = e.getPlayer().getWorld();
        //World oW = e.getFrom();
        if(w.getName().equalsIgnoreCase(StaticManager.farmworldName)) {
            if(rU.isRoleplay()) {
                rU.setRoleplay(false);
            }
        } else if(w.getEnvironment().equals(World.Environment.NETHER)) {
            if(StaticManager.restrictedNether) {
                if(rU.isRoleplay()) {
                    rU.setRoleplay(false);
                }
            }
        } else if(w.getEnvironment().equals(World.Environment.THE_END)) {
            if(rU.isRoleplay()) {
                rU.setRoleplay(false);
            }
        }
        RoleplayMain.setState(p);
    }
}