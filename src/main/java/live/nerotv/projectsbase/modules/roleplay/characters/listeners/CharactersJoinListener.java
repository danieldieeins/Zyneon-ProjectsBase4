package live.nerotv.projectsbase.modules.roleplay.characters.listeners;

import live.nerotv.projectsbase.modules.roleplay.api.RoleplayAPI;
import live.nerotv.projectsbase.modules.roleplay.characters.Characters;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.InventoryManager;
import live.nerotv.projectsbase.modules.roleplay.utils.RoleplayUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CharactersJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        RoleplayUser u = RoleplayAPI.getRoleplayUser(p);
        if(Characters.hasActiveChar(u.getUUID())) {
            u.setActiveChar(Characters.getActiveChar(u.getUUID()));
        } else {
            Characters.createCharacter(u.getUUID());
            InventoryManager.openCharacterSettings(p);
        }
    }
}