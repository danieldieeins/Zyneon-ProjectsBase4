package live.nerotv.projectsbase.modules.essentials.listener;

import live.nerotv.projectsbase.modules.essentials.EssentialsMain;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!StaticManager.roleplayModule) {
            Player p = e.getPlayer();
            for (Player all : Bukkit.getOnlinePlayers()) {
                EssentialsMain.setPrefix(all);
            }
            e.setQuitMessage("§8« §c" + p.getName());

        }
    }
}