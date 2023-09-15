package live.nerotv.projectsbase.modules.essentials.listener;

import live.nerotv.projectsbase.modules.essentials.EssentialsMain;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static live.nerotv.projectsbase.modules.essentials.manager.StaticManager.tablistFooter;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!StaticManager.roleplayModule) {
            e.setJoinMessage(null);
            Player p = e.getPlayer();
            for (Player all : Bukkit.getOnlinePlayers()) {
                EssentialsMain.setPrefix(all);
            }
            p.setPlayerListFooter(tablistFooter.replace("&", "§"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.getUniqueId() != p.getUniqueId()) {
                    all.sendMessage("§8» §a" + p.getName());
                }
            }
        }
    }
}