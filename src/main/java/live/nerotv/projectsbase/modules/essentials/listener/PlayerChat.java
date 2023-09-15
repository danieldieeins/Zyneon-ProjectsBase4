package live.nerotv.projectsbase.modules.essentials.listener;

import com.zyneonstudios.api.paper.events.ZyneonChatEvent;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.api.ChatAPI;
import live.nerotv.projectsbase.api.ServerAPI;
import live.nerotv.projectsbase.modules.essentials.EssentialsMain;
import live.nerotv.projectsbase.modules.essentials.manager.StaticManager;
import live.nerotv.projectsbase.utils.User;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(ZyneonChatEvent e) {
        if(!StaticManager.roleplayModule) {
            if (ChatAPI.isStringBlocked(e.getMessage())) {
                e.setCancelled(true);
                Player p = e.getPlayer();
                User u = API.getUser(p);
                p.sendMessage("§4Achtung:§c Achte auf deine Wortwahl, oder es wird eine Strafe mit sich führen.");
                u.playSound(Sound.ENTITY_BAT_DEATH);
                u.playSound(Sound.ENTITY_BLAZE_DEATH);
                u.playSound(Sound.BLOCK_ANVIL_BREAK);
                ServerAPI.sendConsoleMessage("§4" + p.getName() + "§c hat versucht §4\"" + e.getMessage() + "§4\"§c zu schreiben, die Nachricht wurde aber blockiert!");
            }
        }
    }
}