package live.nerotv.projectsbase.modules.roleplay.listener;

import live.nerotv.projectsbase.api.ChatAPI;
import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldSignChange implements Listener {

    @EventHandler
    public void onSignChange(org.bukkit.event.block.SignChangeEvent e) {
        if(!RoleplayAPI.isStarted) {
            e.setCancelled(true);
            return;
        }
        for (int i = 0; i < 4; i++) {
            String line = e.getLine(i);
            if (line != null && !line.equals("")) {
                e.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
                if(ChatAPI.isStringBlocked(line)) {
                    e.getPlayer().sendMessage("§cAchte auf deine Wortwahl!");
                    e.setCancelled(true);
                }
            }
            if(e.getLine(1)!=null && !e.getLine(1).equals("")) {
                if (e.getLine(1).contains("Zug nach")||e.getLine(1).contains("Kutsche nach")) {
                    if(!e.getPlayer().hasPermission("zyneon.team.trainssigns")) {
                        e.setLine(0,"");
                        e.setLine(1,"§cKeine");
                        e.setLine(2,"§cBerechtigung");
                        e.setLine(3,"");
                        e.getPlayer().sendMessage("§cDas darfst du nicht!");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}