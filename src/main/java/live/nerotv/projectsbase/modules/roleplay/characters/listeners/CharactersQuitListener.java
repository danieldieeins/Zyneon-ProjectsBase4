package live.nerotv.projectsbase.modules.roleplay.characters.listeners;

import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CharactersQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Characters.locked.remove(e.getPlayer());
    }
}