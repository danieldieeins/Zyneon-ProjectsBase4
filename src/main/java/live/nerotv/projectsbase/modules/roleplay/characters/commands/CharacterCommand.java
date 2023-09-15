package live.nerotv.projectsbase.modules.roleplay.characters.commands;

import com.zyneonstudios.api.utils.Strings;
import live.nerotv.projectsbase.api.API;
import live.nerotv.projectsbase.modules.roleplay.characters.managers.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CharacterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            InventoryManager.openCharactersMenu((Player)s);
        } else {
            API.sendErrorMessage(s, Strings.needPlayer());
        }
        return false;
    }
}
